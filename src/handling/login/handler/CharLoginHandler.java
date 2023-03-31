package handling.login.handler;

import client.LoginCrypto;
import client.MapleCharacter;
import client.MapleCharacterUtil;
import client.MapleClient;
import client.SkillFactory;
import client.inventory.Item;
import client.inventory.MapleInventory;
import client.inventory.MapleInventoryType;
import constants.GameConstants;
import constants.ServerConstants;
import constants.WorldConstants;
import handling.cashshop.CashShopServer;
import handling.channel.ChannelServer;
import handling.login.LoginInformationProvider;
import handling.login.LoginInformationProvider.JobType;
import handling.login.LoginServer;
import handling.login.LoginWorker;
import handling.world.World;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.regex.Pattern;
import server.MapleItemInformationProvider;
import server.quest.MapleQuest;
import tools.FileoutputUtil;
import tools.packet.MaplePacketCreator;
import tools.data.LittleEndianAccessor;
import tools.packet.LoginPacket;
import tools.packet.PacketHelper;

public class CharLoginHandler {

    private static final boolean loginFailCount(final MapleClient c) {
        c.loginAttempt++;
        if (c.loginAttempt > 5) {
            return true;
        }
        return false;
    }

    public static final void ClientHello(final LittleEndianAccessor slea, final MapleClient c) {
        if (slea.readByte() != 4 || slea.readShort() != ServerConstants.MAPLE_VERSION || !String.valueOf(slea.readShort()).equals(ServerConstants.MAPLE_PATCH)) {
            c.getSession().close();
        }
    }

    public static void LicenseRequest(LittleEndianAccessor slea, MapleClient c) {
        if (slea.readByte() == 1) {
            c.getSession().write(LoginPacket.licenseResult());
            c.updateLoginState(MapleClient.LOGIN_NOTLOGGEDIN, c.getSessionIPAddress());
        } else {
            c.getSession().close();
        }
    }

    public static final void SetGenderRequest(final LittleEndianAccessor slea, final MapleClient c) {
        byte gender = slea.readByte();
        String username = slea.readMapleAsciiString();
        //String password = slea.readMapleAsciiString();

        if (gender != 0 && gender != 1) {
            c.getSession().close();
            return;
        }
        if (c.getAccountName().equals(username)) {
            c.setGender(gender);
            c.setSecondPassword("123456");
            c.updateSecondPassword();
            c.updateGender();
            c.getSession().write(LoginPacket.getGenderChanged(c));
            c.getSession().write(LoginPacket.getLoginFailed(22));
            c.updateLoginState(MapleClient.LOGIN_NOTLOGGEDIN, c.getSessionIPAddress());
        } else {
            c.getSession().close();
        }
    }

    public static String RandomString() {
        Random random = new Random();
        String sRand = "";
        for (int i = 0; i < 6; i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand += rand;
        }
        return sRand;
    }

    public static final void login(final LittleEndianAccessor slea, final MapleClient c) {
        String login = slea.readMapleAsciiString();
        String pwd = slea.readMapleAsciiString();

        if (!Pattern.compile("^(?!_)(?!.*?_$)[a-zA-Z0-9_一-龥]+$").matcher(login).matches()) {
            c.getSession().write(MaplePacketCreator.serverNotice(1, "账号有非法字符，请重新输入。"));
            c.getSession().write(LoginPacket.getLoginFailed(7));
            return;
        }

        if (!Pattern.compile("^(?!_)(?!.*?_$)[a-zA-Z0-9_一-龥]+$").matcher(pwd).matches()) {
            c.getSession().write(MaplePacketCreator.serverNotice(1, "密码有非法字符，请重新输入。"));
            c.getSession().write(LoginPacket.getLoginFailed(7));
            return;
        }

        final boolean ipBan = c.hasBannedIP();
        final boolean macBan = c.hasBannedMac();
        String macData = "";

        int loginok = c.login(login, pwd, ipBan || macBan);
        final Calendar tempbannedTill = c.getTempBanCalendar();
        String errorInfo = null;

        if (loginok == 0 && (ipBan || macBan) && !c.isGm()) {
            loginok = 3;
            if (macBan) {
                // this is only an ipban o.O" - maybe we should refactor this a bit so it's more readable
                //MapleCharacter.ban(c.getSession().getRemoteAddress().toString().split(":")[0], "Enforcing account ban, account " + login, false, 4, false);
            }
        } else if (loginok == 0 && (c.getGender() == 10 || c.getSecondPassword() == null)) {
            // 防止第一次按取消后卡角問題
            //c.updateLoginState(MapleClient.LOGIN_SERVER_TRANSITION, c.getSessionIPAddress());
            c.getSession().write(LoginPacket.getGenderNeeded(c));
            return;
        } else if (loginok == 5) {
            //账号不存在
            if (AutoRegister.autoRegister) {
                if (AutoRegister.createAccount(login, pwd, c.getSession().getRemoteAddress().toString())) {
                    errorInfo = "账号创建成功,\r\n您的密码为:" + pwd + "\r\n请重新登入!";
                } else {
                    errorInfo = "註冊賬號失敗。";
                }
            } else {
                errorInfo = "賬號註冊失敗，未开启自動註冊功能，请到網站註冊賬號。";
            }
            loginok = 1;
        }

        if (loginok != 0) {
            if (!loginFailCount(c)) {
                c.clearInformation();
                c.getSession().write(LoginPacket.getLoginFailed(loginok));
                if (errorInfo != null) {
                    c.getSession().write(MaplePacketCreator.serverNotice(1, errorInfo));
                }
            } else {
                c.getSession().close();
            }

        } else if (tempbannedTill.getTimeInMillis() != 0) {
            if (!loginFailCount(c)) {
                c.clearInformation();
                c.getSession().write(LoginPacket.getTempBan(PacketHelper.getTime(tempbannedTill.getTimeInMillis()), c.getBanReason()));
            } else {
                c.getSession().close();
            }
        } else {
            String loginkey = RandomString();
            c.loginAttempt = 0;
            LoginServer.RemoveLoginKey(c.getAccID());
            c.setLoginKey(loginkey);
            c.updateLoginKey(loginkey);
            LoginServer.addLoginKey(loginkey, c.getAccID());
            FileoutputUtil.logToFile("logs/data/登入账号.txt", "\r\n 時间　[" + FileoutputUtil.NowTime() + "]" + " IP 地址 : " + c.getSessionIPAddress() + " MAC: " + macData + " 账号：　" + login + " 密码：" + pwd);
            LoginWorker.registerClient(c);
        }
    }

    public static final void ServerListRequest(final MapleClient c) {
        for (WorldConstants.WorldOption servers : WorldConstants.WorldOption.values()) {
            if (WorldConstants.WorldOption.getById(servers.getWorld()).show() && servers != null) {
                c.getSession().write(LoginPacket.getServerList(servers.getWorld(), LoginServer.getLoad()));
            }
        }
        c.getSession().write(LoginPacket.getEndOfServerList());
    }

    public static final void ServerStatusRequest(final MapleClient c) {
        final int numPlayer = LoginServer.getUsersOn();
        final int userLimit = LoginServer.getUserLimit();
        if (numPlayer >= userLimit) {
            c.getSession().write(LoginPacket.getServerStatus(2));
        } else if (numPlayer * 2 >= userLimit) {
            c.getSession().write(LoginPacket.getServerStatus(1));
        } else {
            c.getSession().write(LoginPacket.getServerStatus(0));
        }
    }

    public static final void CharlistRequest(final LittleEndianAccessor slea, final MapleClient c) {
        int totalOnline = 0;
        /*服务器总人数*/
        for (ChannelServer cserv : ChannelServer.getAllInstances()) {
            totalOnline += cserv.getConnectedClients();
        }
        //if (totalOnline > ServerConstants.userlimit) {
        //    c.getSession().write(MaplePacketCreator.serverNotice(1, "当前服务端为单機版.\r\n已达到人數上线.\r\n如需购买商业版.\r\n聯繫QQ65343371"));
        //   c.getSession().write(LoginPacket.getLoginFailed(7));
        //    return;
        //}
        if (!c.isLoggedIn()) {
            c.getSession().close();
            return;
        }
        String serverkey = RandomString();
        if (!LoginServer.CanLoginKey(c.getLoginKey(), c.getAccID()) || (LoginServer.getLoginKey(c.getAccID()) == null && !c.getLoginKey().isEmpty())) {
            FileoutputUtil.logToFile("logs/Data/客戶端登录KEY异常.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSessionIPAddress() + " 账号: " + c.getAccountName() + " 客戶端key：" + LoginServer.getLoginKey(c.getAccID()) + " 伺服端key：" + c.getLoginKey() + " 角色列表");
            return;
        }
        //slea.readByte(); //2?
        final int server = slea.readByte();
        final int channel = slea.readByte() + 1;
        if (!World.isChannelAvailable(channel) || !WorldConstants.WorldOption.isExists(server)) { //TODOO: MULTI WORLDS
            c.getSession().write(LoginPacket.getLoginFailed(10)); //cannot process so many
            return;
        }
        if (!WorldConstants.WorldOption.getById(server).isAvailable() && !(c.isGm() && server == WorldConstants.gmserver)) {
            c.getSession().write(MaplePacketCreator.serverNotice(1, "很抱歉, " + WorldConstants.getNameById(server) + "暂時未开放。\r\n请尝试连接其他服务器。"));
            c.getSession().write(LoginPacket.getLoginFailed(1)); //Shows no message, but it is used to unstuck
            return;
        }

        LoginServer.RemoveServerKey(c.getAccID());
        c.setServerKey(serverkey);
        c.updateServerKey(serverkey);
        LoginServer.addServerKey(serverkey, c.getAccID());

        //System.out.println("[连接信息] " + c.getSession().getRemoteAddress().toString().split(":")[0] + " 连接到世界服务器: " + server + " 頻道: " + channel);
        final List<MapleCharacter> chars = c.loadCharacters(server);
        if (chars != null && ChannelServer.getInstance(channel) != null) {
            c.setWorld(server);
            c.setChannel(channel);
            c.getSession().write(LoginPacket.getCharList(c.getSecondPassword(), chars, c.getCharacterSlots()));
        } else {
            c.getSession().close();
        }
    }

    public static final void CheckCharName(final String name, final MapleClient c) {
        c.getSession().write(LoginPacket.charNameResponse(name, !(MapleCharacterUtil.canCreateChar(name, c.isGm()) && (!LoginInformationProvider.getInstance().isForbiddenName(name) || c.isGm()))));
    }

    public static final void CreateChar(final LittleEndianAccessor slea, final MapleClient c) {
        if (!c.isLoggedIn()) {
            c.getSession().close();
            return;
        }
        final String name = slea.readMapleAsciiString();
        final int JobTypea = slea.readInt();
        final JobType jobType = JobType.getByType(JobTypea); // BIGBANG: 0 = Resistance, 1 = Adventurer, 2 = Cygnus, 3 = Aran, 4 = Evan
        if (JobTypea != 0 && JobTypea != 1 && JobTypea != 2 && JobTypea != 3 && JobTypea != 4) { //职业开关
            World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 聊天系統] 非法创建 账号 " + c.getAccountName() + " 职业类型 " + JobTypea));
            World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 聊天系統] 非法创建 账号 " + c.getAccountName() + " 职业类型 " + JobTypea));
            World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 聊天系統] 非法创建 账号 " + c.getAccountName() + " 职业类型 " + JobTypea));
            FileoutputUtil.logToFile("logs/Data/非法创建.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSessionIPAddress() + " 账号 " + c.getAccountName() + " 职业类型 " + JobTypea);
            return;
        }
        final short db = slea.readShort(); //whether dual blade = 1 or adventurer = 0
        final byte gender = c.getGender(); //??idk corresponds with the thing in addCharStats
        if (gender != 0 && gender != 1) {
            FileoutputUtil.logToFile("logs/Hack/Ban/修改封包.txt", "\r\n " + FileoutputUtil.NowTime() + " 账号 " + c.getAccountName() + " 性別类型 " + gender);
          //  World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[封锁系統] " + c.getPlayer().getName() + " 因为性別异常而被管理員永久停權。"));
            World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 聊天系統] 性別异常 账号 " + c.getAccountName() + " 性別类型 " + gender));
          //  c.getPlayer().ban("性別异常", true, true, false);
         //   c.getSession().close();
            return;
        }
        byte skinColor = 0;
        int hairColor = 0;
        final int face = slea.readInt();
        final int hair = slea.readInt();
        final int top = slea.readInt();
        final int bottom = slea.readInt();
        final int shoes = slea.readInt();
        final int weapon = slea.readInt();

        if (gender == 0 && JobTypea == 0) {
            if (face != 20100 && face != 20401 && face != 20402) {
                return;
            }
            if (hair != 30030 && hair != 30027 && hair != 30000) {
                return;
            }
            if (top != 1050173 && top != 1050174 && top != 1050175) {
                return;
            }
            if (bottom != 0) {
                return;
            }
            if (shoes != 1072001 && shoes != 1072005 && shoes != 1072037 && shoes != 1072038) {
                return;
            }
            if (weapon != 1302000 && weapon != 1322005 && weapon != 1312004) {
                return;
            }

        } else if (gender == 1 && JobTypea == 0) {
            if (face != 21002 && face != 21700 && face != 21201) {
                return;
            }
            if (hair != 31002 && hair != 31047 && hair != 31057) {
                return;
            }
            if (top != 1051214 && top != 1051215 && top != 1051216) {
                return;
            }
            if (bottom != 0) {
                return;
            }
            if (shoes != 1072001 && shoes != 1072005 && shoes != 1072037 && shoes != 1072038) {
                return;
            }
            if (weapon != 1302000 && weapon != 1322005 && weapon != 1312004) {
                return;
            }

        } else if (gender == 0 && (JobTypea == 1 || JobTypea == 2)) {
            if (face != 20100 && face != 20401 && face != 20402) {
                return;
            }
            if (hair != 30030 && hair != 30027 && hair != 30000) {
                return;
            }
            if (top != 1040002 && top != 1040006 && top != 1040010) {
                return;
            }
            if (bottom != 1060002 && bottom != 1060006) {
                return;
            }
            if (shoes != 1072001 && shoes != 1072005 && shoes != 1072037 && shoes != 1072038) {
                return;
            }
            if (weapon != 1302000 && weapon != 1322005 && weapon != 1312004) {
                return;
            }

        } else if (gender == 1 && (JobTypea == 1 || JobTypea == 2)) {
            if (face != 21002 && face != 21700 && face != 21201) {
                return;
            }
            if (hair != 31002 && hair != 31047 && hair != 31057) {
                return;
            }
            if (top != 1041002 && top != 1041006 && top != 1041010 && top != 1041011) {
                return;
            }
            if (bottom != 1061002 && bottom != 1061008) {
                return;
            }
            if (shoes != 1072001 && shoes != 1072005 && shoes != 1072037 && shoes != 1072038) {
                return;
            }
            if (weapon != 1302000 && weapon != 1322005 && weapon != 1312004) {
                return;
            }

        } else if (JobTypea == 3) {
            if (gender == 0) {
                if (face != 20100 && face != 20401 && face != 20402) {
                    return;
                }
                if (hair != 30030 && hair != 30027 && hair != 30000) {
                    return;
                }
            } else if (gender == 1) {
                if (face != 21002 && face != 21700 && face != 21201) {
                    return;
                }
                if (hair != 31002 && hair != 31047 && hair != 31057) {
                    return;
                }
            }
            if (top != 1042167) {
                return;
            }
            if (bottom != 1062115) {
                return;
            }
            if (shoes != 1072383) {
                return;
            }
            if (weapon != 1442079) {
                return;
            }
        } else if (JobTypea == 4) {
            if (gender == 0) {
                if (face != 20100 && face != 20401 && face != 20402) {
                    return;
                }
                if (hair != 30030 && hair != 30027 && hair != 30000) {
                    return;
                }
                if (bottom != 1060138) {
                    return;
                }
            } else if (gender == 1) {
                if (face != 21002 && face != 21700 && face != 21201) {
                    return;
                }
                if (hair != 31002 && hair != 31047 && hair != 31057) {
                    return;
                }
                if (bottom != 1061160) {
                    return;
                }
            }
            if (top != 1042180) {
                return;
            }

            if (shoes != 1072418) {
                return;
            }
            if (weapon != 1302132) {
                return;
            }
        }

        MapleCharacter newchar = MapleCharacter.getDefault(c, jobType);
        newchar.setWorld((byte) c.getWorld());
        newchar.setFace(face);
        newchar.setHair(hair + hairColor);
        newchar.setGender(gender);
        newchar.setName(name);
        newchar.setSkinColor(skinColor);

        final MapleItemInformationProvider li = MapleItemInformationProvider.getInstance();
        final MapleInventory equip = newchar.getInventory(MapleInventoryType.EQUIPPED);
        Item item = li.getEquipById(top);
        item.setPosition((byte) -5);
        equip.addFromDB(item);

        if (bottom > 0) { //resistance have overall
            item = li.getEquipById(bottom);
            item.setPosition((byte) -6);
            equip.addFromDB(item);
        }

        item = li.getEquipById(shoes);
        item.setPosition((byte) -7);
        equip.addFromDB(item);

        item = li.getEquipById(weapon);
        item.setPosition((byte) -11);
        equip.addFromDB(item);

        newchar.getInventory(MapleInventoryType.USE).addItem(new Item(2000013, (byte) 0, (short) 100, (byte) 0));
        newchar.getInventory(MapleInventoryType.USE).addItem(new Item(2000014, (byte) 0, (short) 100, (byte) 0));
        switch (jobType) {
            case Resistance: // Resistance
                newchar.getInventory(MapleInventoryType.ETC).addItem(new Item(4161001, (byte) 0, (short) 1, (byte) 0));
                break;
            case Adventurer: // Adventurer
                newchar.getInventory(MapleInventoryType.ETC).addItem(new Item(4161001, (byte) 0, (short) 1, (byte) 0));
                break;
            case Cygnus: // Cygnus
                newchar.setQuestAdd(MapleQuest.getInstance(20022), (byte) 1, "1");
                newchar.setQuestAdd(MapleQuest.getInstance(20010), (byte) 1, null); //>_>_>_> ugh

                newchar.getInventory(MapleInventoryType.ETC).addItem(new Item(4161047, (byte) 0, (short) 1, (byte) 0));
                break;
            case Aran: // Aran
                newchar.getInventory(MapleInventoryType.ETC).addItem(new Item(4161048, (byte) 0, (short) 1, (byte) 0));
                break;
            case Evan: //Evan
                newchar.getInventory(MapleInventoryType.ETC).addItem(new Item(4161052, (byte) 0, (short) 1, (byte) 0));
                break;
            case Mercedes: // Mercedes
                newchar.getInventory(MapleInventoryType.ETC).addItem(new Item(4161079, (byte) 0, (short) 1, (byte) 0));
                break;
            case Demon: //Demon
                newchar.getInventory(MapleInventoryType.ETC).addItem(new Item(4161054, (byte) 0, (short) 1, (byte) 0));
                break;
        }
        FileoutputUtil.logToFile("logs/Data/创建角色.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSessionIPAddress() + " 账号 " + c.getAccountName() + " 名字 " + name + " 职业类型 " + JobTypea + " 臉型 " + face + " 髮型 " + hair + " 衣服 " + top + " 褲子 " + bottom + " 鞋子 " + shoes + " 武器 " + weapon + " 性別 " + gender + " db " + db);
        if (MapleCharacterUtil.canCreateChar(name, c.isGm()) && (!LoginInformationProvider.getInstance().isForbiddenName(name) || c.isGm()) && (c.isGm() || c.canMakeCharacter(c.getWorld()))) {
            MapleCharacter.saveNewCharToDB(newchar, jobType, jobType.type == 1 ? db : 0);
            c.getSession().write(LoginPacket.addNewCharEntry(newchar, true));
            c.createdChar(newchar.getId());

        } else {
            c.getSession().write(LoginPacket.addNewCharEntry(newchar, false));
        }
    }

    public static final void CreateUltimate(final LittleEndianAccessor slea, final MapleClient c) {
        if (!c.isLoggedIn() || c.getPlayer() == null || c.getPlayer().getLevel() < 120 || c.getPlayer().getMapId() != 130000000 || c.getPlayer().getQuestStatus(20734) != 0 || c.getPlayer().getQuestStatus(20616) != 2 || !GameConstants.isKOC(c.getPlayer().getJob()) || !c.canMakeCharacter(c.getPlayer().getWorld())) {
            c.getPlayer().dropMessage(1, "You have no character slots.");
            c.getSession().write(MaplePacketCreator.createUltimate(0));
            return;
        }
        final String name = slea.readMapleAsciiString();
        final int job = slea.readInt(); //job ID
        if (job < 110 || job > 520 || job % 10 > 0 || (job % 100 != 10 && job % 100 != 20 && job % 100 != 30) || job == 430) {
            c.getPlayer().dropMessage(1, "An error has occurred.");
            c.getSession().write(MaplePacketCreator.createUltimate(0));
            return;
        }

        final int face = slea.readInt();
        final int hair = slea.readInt();

        final int hat = slea.readInt();
        if (hat != 1003159) {
            c.getPlayer().dropMessage(1, "发生错误请聯繫管理員。");
            c.getSession().write(MaplePacketCreator.createUltimate(0));
            return;
        }
        final int top = slea.readInt();
        if (top != 1052304) {
            c.getPlayer().dropMessage(1, "发生错误请聯繫管理員。");
            c.getSession().write(MaplePacketCreator.createUltimate(0));
            return;
        }
        final int glove = slea.readInt();
        if (glove != 1082290) {
            c.getPlayer().dropMessage(1, "发生错误请聯繫管理員。");
            c.getSession().write(MaplePacketCreator.createUltimate(0));
            return;
        }
        final int shoes = slea.readInt();
        if (shoes != 1072476) {
            c.getPlayer().dropMessage(1, "发生错误请聯繫管理員。");
            c.getSession().write(MaplePacketCreator.createUltimate(0));
            return;
        }
        final int weapon = slea.readInt();
        if (weapon != 1402092 && weapon != 1432082 && weapon != 1372081 && weapon != 1452108 && weapon != 1462095 && weapon != 1472119 && weapon != 1332127 && weapon != 1482081 && weapon != 1492082) {
            c.getPlayer().dropMessage(1, "发生错误请聯繫管理員。");
            c.getSession().write(MaplePacketCreator.createUltimate(0));
            return;
        }

        final byte gender = c.getPlayer().getGender();
        JobType jobType = JobType.Adventurer;
        if (!LoginInformationProvider.getInstance().isEligibleItem(gender, 0, jobType.type, face) || !LoginInformationProvider.getInstance().isEligibleItem(gender, 1, jobType.type, hair)) {
            c.getPlayer().dropMessage(1, "An error occurred.");
            c.getSession().write(MaplePacketCreator.createUltimate(0));
            return;
        }

        jobType = JobType.UltimateAdventurer;
        if (!LoginInformationProvider.getInstance().isEligibleItem(-1, job, jobType.type, hat) || !LoginInformationProvider.getInstance().isEligibleItem(-1, job, jobType.type, top)
                || !LoginInformationProvider.getInstance().isEligibleItem(-1, job, jobType.type, glove) || !LoginInformationProvider.getInstance().isEligibleItem(-1, job, jobType.type, shoes)
                || !LoginInformationProvider.getInstance().isEligibleItem(-1, job, jobType.type, weapon)) {
            c.getPlayer().dropMessage(1, "An error occured.");
            c.getSession().write(MaplePacketCreator.createUltimate(0));
            return;
        }

        MapleCharacter newchar = MapleCharacter.getDefault(c, jobType);
        newchar.setJob(job);
        newchar.setWorld((byte) c.getPlayer().getWorld());
        newchar.setFace(face);
        newchar.setHair(hair);
        newchar.setGender(gender);
        newchar.setName(name);
        newchar.setSkinColor((byte) 3); //troll
        newchar.setLevel((short) 51);
        newchar.getStat().str = (short) 4;
        newchar.getStat().dex = (short) 4;
        newchar.getStat().int_ = (short) 4;
        newchar.getStat().luk = (short) 4;
        newchar.setRemainingAp((short) 254); //49*5 + 25 - 16
        newchar.setRemainingSp(job / 100 == 2 ? 128 : 122); //2 from job advancements. 120 from leveling. (mages get +6)
        newchar.getStat().maxhp += 150; //Beginner 10 levels
        newchar.getStat().maxmp += 125;
        switch (job) {
            case 110:
            case 120:
            case 130:
                newchar.getStat().maxhp += 600; //Job Advancement
                newchar.getStat().maxhp += 2000; //Levelup 40 times
                newchar.getStat().maxmp += 200;
                break;
            case 210:
            case 220:
            case 230:
                newchar.getStat().maxmp += 600;
                newchar.getStat().maxhp += 500; //Levelup 40 times
                newchar.getStat().maxmp += 2000;
                break;
            case 310:
            case 320:
            case 410:
            case 420:
            case 520:
                newchar.getStat().maxhp += 500;
                newchar.getStat().maxmp += 250;
                newchar.getStat().maxhp += 900; //Levelup 40 times
                newchar.getStat().maxmp += 600;
                break;
            case 510:
                newchar.getStat().maxhp += 500;
                newchar.getStat().maxmp += 250;
                newchar.getStat().maxhp += 450; //Levelup 20 times
                newchar.getStat().maxmp += 300;
                newchar.getStat().maxhp += 800; //Levelup 20 times
                newchar.getStat().maxmp += 400;
                break;
            default:
                return;
        }
        for (int i = 2490; i < 2507; i++) {
            newchar.setQuestAdd(MapleQuest.getInstance(i), (byte) 2, null);
        }
        newchar.setQuestAdd(MapleQuest.getInstance(29947), (byte) 2, null);
        newchar.setQuestAdd(MapleQuest.getInstance(GameConstants.ULT_EXPLORER), (byte) 0, c.getPlayer().getName());
        newchar.changeSkillLevel_Skip(SkillFactory.getSkill(1074 + (job / 100)), (byte) 5, (byte) 5);
        newchar.changeSkillLevel_Skip(SkillFactory.getSkill(80), (byte) 1, (byte) 1);
        final MapleItemInformationProvider li = MapleItemInformationProvider.getInstance();
        int[] items = new int[]{1142257, hat, top, shoes, glove, weapon, hat + 1, top + 1, shoes + 1, glove + 1, weapon + 1}; //brilliant = fine+1
        for (byte i = 0; i < items.length; i++) {
            Item item = li.getEquipById(items[i]);
            item.setPosition((byte) (i + 1));
            newchar.getInventory(MapleInventoryType.EQUIP).addFromDB(item);
        }
        newchar.getInventory(MapleInventoryType.USE).addItem(new Item(2000004, (byte) 0, (short) 100, (byte) 0));
        newchar.getInventory(MapleInventoryType.USE).addItem(new Item(2000004, (byte) 0, (short) 100, (byte) 0));
        c.getPlayer().fakeRelog();
        if (MapleCharacterUtil.canCreateChar(name, c.isGm()) && (!LoginInformationProvider.getInstance().isForbiddenName(name) || c.isGm())) {
            MapleCharacter.saveNewCharToDB(newchar, jobType, (short) 0);
            MapleQuest.getInstance(20734).forceComplete(c.getPlayer(), 1101000);
            c.getSession().write(MaplePacketCreator.createUltimate(1));
        } else {
            c.getSession().write(MaplePacketCreator.createUltimate(0));
        }
    }

    public static final void DeleteChar(final LittleEndianAccessor slea, final MapleClient c) {
        if (!LoginServer.CanLoginKey(c.getLoginKey(), c.getAccID()) || (LoginServer.getLoginKey(c.getAccID()) == null && !c.getLoginKey().isEmpty())) {
            FileoutputUtil.logToFile("logs/Data/客戶端登录KEY异常.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSessionIPAddress() + " 账号: " + c.getAccountName() + " 客戶端key：" + LoginServer.getLoginKey(c.getAccID()) + " 伺服端key：" + c.getLoginKey() + " 刪除角色");
            return;
        }
        if (!LoginServer.CanServerKey(c.getServerKey(), c.getAccID()) || (LoginServer.getServerKey(c.getAccID()) == null && !c.getServerKey().isEmpty())) {
            FileoutputUtil.logToFile("logs/Data/客戶端頻道KEY异常.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSessionIPAddress() + " 账号: " + c.getAccountName() + " 客戶端key：" + LoginServer.getServerKey(c.getAccID()) + " 伺服端key：" + c.getServerKey() + " 刪除角色");
            return;
        }
        if (slea.available() < 7) {
            return;
        }
        slea.readByte();
        String Secondpw_Client = slea.readMapleAsciiString();

        final int Character_ID = slea.readInt();

        List<String> charNames = c.loadCharacterNamesByCharId(Character_ID);
        for (ChannelServer cs : ChannelServer.getAllInstances()) {
            for (final String name : charNames) {
                MapleCharacter character = cs.getPlayerStorage().getCharacterByName(name);
                if (character != null) {
                    //cs.getPlayerStorage().deregisterPlayer(character);
                    //character.getClient().disconnect(false, false, true);
                    FileoutputUtil.logToFile("logs/Data/非法刪除角色.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSessionIPAddress() + " 账号 " + c.getAccountName() + " 角色 " + name);
                    c.getSession().close();
                    character.getClient().getSession().close();
                }
            }
        }
        for (final String name : charNames) {
            MapleCharacter charactercs = CashShopServer.getPlayerStorage().getCharacterByName(name);
            if (charactercs != null) {
                //CashShopServer.getPlayerStorage().deregisterPlayer(charactercs);
                //charactercs.getClient().disconnect(false, true, true);
                FileoutputUtil.logToFile("logs/Data/非法刪除角色.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSessionIPAddress() + " 账号 " + c.getAccountName() + " 角色 " + name);
                c.getSession().close();
                charactercs.getClient().getSession().close();
            }
        }

        if (!c.login_Auth(Character_ID) || !c.isLoggedIn() || loginFailCount(c)) {
            c.getSession().close();
            return; // Attempting to delete other character
        }
        byte state = 0;

        if (c.getSecondPassword() != null) { // On the server, there's a second password
            if (Secondpw_Client == null) { // Client's hacking
                c.getSession().close();
                return;
            } else if (!c.CheckSecondPassword(Secondpw_Client)) { // Wrong Password
                state = 0x10;
            }
        }

        if (state == 0) {
            state = (byte) c.deleteCharacter(Character_ID);
        }
        c.getSession().write(LoginPacket.deleteCharResponse(Character_ID, state));
    }

    public static final void Character_WithoutSecondPassword(final LittleEndianAccessor slea, final MapleClient c, final boolean haspic, final boolean view) {
        if (!LoginServer.CanLoginKey(c.getLoginKey(), c.getAccID()) || (LoginServer.getLoginKey(c.getAccID()) == null && !c.getLoginKey().isEmpty())) {
            FileoutputUtil.logToFile("logs/Data/客戶端登录KEY异常.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSessionIPAddress() + " 账号: " + c.getAccountName() + " 客戶端key：" + LoginServer.getLoginKey(c.getAccID()) + " 伺服端key：" + c.getLoginKey() + " 开始游戏");
            return;
        }

        if (!LoginServer.CanServerKey(c.getServerKey(), c.getAccID()) || (LoginServer.getServerKey(c.getAccID()) == null && !c.getServerKey().isEmpty())) {
            FileoutputUtil.logToFile("logs/Data/客戶端頻道KEY异常.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSessionIPAddress() + " 账号: " + c.getAccountName() + " 客戶端key：" + LoginServer.getServerKey(c.getAccID()) + " 伺服端key：" + c.getServerKey() + " 开始游戏");
            return;
        }
        int totalOnline = 0;
        /*服务器总人数*/
        for (ChannelServer cserv : ChannelServer.getAllInstances()) {
            totalOnline += cserv.getConnectedClients();
        }
        //if (totalOnline > ServerConstants.userlimit) {
        //     c.getSession().write(MaplePacketCreator.serverNotice(1, "当前服务端为单機版.\r\n已达到人數上线.\r\n如需购买商业版.\r\n聯繫QQ65343371"));
        //     c.getSession().write(LoginPacket.getLoginFailed(7));
        //     return;
        // }

        LoginServer.RemoveClientKey(c.getAccID());
        String clientkey = RandomString();
        c.updateClientKey(clientkey);
        LoginServer.addClientKey(clientkey, c.getAccID());

        final int charId = slea.readInt();
        if (!c.isLoggedIn() || loginFailCount(c) || !c.login_Auth(charId) || ChannelServer.getInstance(c.getChannel()) == null || !WorldConstants.WorldOption.isExists(c.getWorld())) { // TODOO: MULTI WORLDS
            //c.getSession().close();
            return;
        }

        if (c.getIdleTask() != null) {
            c.getIdleTask().cancel(true);
        }
        final String s = c.getSessionIPAddress();
        byte[] ip = ServerConstants.Gateway_IP;
        //try {
        //    ip = InetAddress.getByName(ChannelServer.getInstance(c.getChannel()).getIP().split(":")[0]).getAddress();
        //} catch (Exception ex) {
        //}
        LoginServer.putLoginAuth(charId, s.substring(s.indexOf('/') + 1, s.length()), c.getTempIP());
        c.updateLoginState(MapleClient.LOGIN_SERVER_TRANSITION, s);
        c.getSession().write(MaplePacketCreator.getServerIP(c, ip, Integer.parseInt(ChannelServer.getInstance(c.getChannel()).getIP().split(":")[1]), charId));
        System.setProperty(String.valueOf(charId), "1");
    }

    public static final void Character_WithSecondPassword(final LittleEndianAccessor slea, final MapleClient c, final boolean view) {
        final String password = slea.readMapleAsciiString();
        final int charId = slea.readInt();
        if (view) {
            c.setChannel(1);
            c.setWorld(slea.readInt());
        }
        if (!c.isLoggedIn() || loginFailCount(c) || c.getSecondPassword() == null || !c.login_Auth(charId) || ChannelServer.getInstance(c.getChannel()) == null || c.getWorld() != 0) { // TODOO: MULTI WORLDS
            c.getSession().close();
            return;
        }
        if (c.CheckSecondPassword(password) && password.length() >= 6 && password.length() <= 16) {
            if (c.getIdleTask() != null) {
                c.getIdleTask().cancel(true);
            }
            final String s = c.getSessionIPAddress();
            byte[] ip = ServerConstants.Gateway_IP;
            //try {
            //    ip = InetAddress.getByName(ChannelServer.getInstance(c.getChannel()).getIP().split(":")[0]).getAddress();
            //} catch (Exception ex) {
            //}
            LoginServer.putLoginAuth(charId, s.substring(s.indexOf('/') + 1, s.length()), c.getTempIP());
            c.updateLoginState(MapleClient.LOGIN_SERVER_TRANSITION, s);
            c.getSession().write(MaplePacketCreator.getServerIP(c, ip, Integer.parseInt(ChannelServer.getInstance(c.getChannel()).getIP().split(":")[1]), charId));
        } else {
            c.getSession().write(LoginPacket.secondPwError((byte) 0x14));
        }
    }

    public static void ViewChar(LittleEndianAccessor slea, MapleClient c) {
        Map<Byte, ArrayList<MapleCharacter>> worlds = new HashMap<Byte, ArrayList<MapleCharacter>>();
        List<MapleCharacter> chars = c.loadCharacters(0); //TODO multi world
        c.getSession().write(LoginPacket.showAllCharacter(chars.size()));
        for (MapleCharacter chr : chars) {
            if (chr != null) {
                ArrayList<MapleCharacter> chrr;
                if (!worlds.containsKey(chr.getWorld())) {
                    chrr = new ArrayList<MapleCharacter>();
                    worlds.put(chr.getWorld(), chrr);
                } else {
                    chrr = worlds.get(chr.getWorld());
                }
                chrr.add(chr);
            }
        }
        for (Entry<Byte, ArrayList<MapleCharacter>> w : worlds.entrySet()) {
            c.getSession().write(LoginPacket.showAllCharacterInfo(w.getKey(), w.getValue(), c.getSecondPassword()));
        }
    }
}
