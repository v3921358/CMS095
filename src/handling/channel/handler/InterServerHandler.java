package handling.channel.handler;

import client.MapleBuffStat;
import client.MapleBuffStatValueHolder;
import client.MapleCharacter;
import client.MapleClient;
import client.MapleQuestStatus;
import client.SkillFactory;
import constants.GameConstants;
import constants.MTSCSConstants;
import constants.ServerConstants;
import handling.cashshop.CashShopServer;
import handling.channel.ChannelServer;
import handling.login.LoginServer;
import handling.world.CharacterIdChannelPair;
import handling.world.CharacterTransfer;
import handling.world.MapleMessenger;
import handling.world.MapleMessengerCharacter;
import handling.world.MapleParty;
import handling.world.MaplePartyCharacter;
import handling.world.PartyOperation;
import handling.world.PlayerBuffStorage;
import handling.world.World;
import handling.world.exped.MapleExpedition;
import handling.world.guild.MapleGuild;
import java.net.InetAddress;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import scripting.NPCScriptManager;
import server.MapleItemInformationProvider;
import server.maps.FieldLimitType;
import server.quest.MapleQuest;
import tools.FileoutputUtil;
import tools.packet.MaplePacketCreator;
import tools.Pair;
import tools.data.LittleEndianAccessor;
import tools.packet.CSPacket;
import tools.packet.FamilyPacket;

public class InterServerHandler {

    public static final void EnterCS(final MapleClient c, final MapleCharacter chr) {
        if ((chr == null) || (chr.hasBlockedInventory()) || (chr.getMap() == null) || (chr.getEventInstance() != null) || (c.getChannelServer() == null)) {
            c.getSession().write(MaplePacketCreator.serverBlocked(2));
            c.getSession().write(MaplePacketCreator.enableActions());
            c.getPlayer().marriage();
            return;
        }

        if (LoginServer.isAdminOnly() && !c.isGm()) {
            c.getPlayer().dropMessage(1, "无法进入商城，请稍后再試。");
            c.getSession().write(MaplePacketCreator.enableActions());
            c.getPlayer().marriage();
            return;
        }

        if (World.isShutDown && !c.isGm()) {
            c.getPlayer().dropMessage(1, "无法进入商城，请稍后再試。");
            c.getSession().write(MaplePacketCreator.enableActions());
            c.getPlayer().marriage();
            return;
        }

        if (chr.getAntiMacro().inProgress()) {
            c.getPlayer().dropMessage(1, "被使用测谎仪时无法操作。");
            c.getSession().write(MaplePacketCreator.enableActions());
            c.getPlayer().marriage();
            return;
        }
        if (World.getPendingCharacterSize() >= 10) {
            chr.dropMessage(1, "服务器繁忙，请稍后再試。");
            c.getSession().write(MaplePacketCreator.enableActions());
            c.getPlayer().marriage();
            return;
        }
        final ChannelServer ch = ChannelServer.getInstance(c.getChannel());

        chr.changeRemoval();

        if (c.getPlayer().getBuffedValue(MapleBuffStat.SUMMON) != null) {
            c.getPlayer().cancelEffectFromBuffStat(MapleBuffStat.SUMMON, -1);
        }

        chr.dispelBuff();
        /*if (chr.getBuffTimeCout(2450000) > 0) {
            long startTime = chr.getBuffTimeStartTime(2450000);
            int length = chr.getBuffTimeLength(2450000);
            if (startTime + length - System.currentTimeMillis() > 3000) {
                chr.updateBuffTime(2450000, (int) (startTime + length - System.currentTimeMillis()));
            } else {
                chr.updateBuffTime(2450000, 0);
            }
        }
        if (chr.getBuffTimeCout(2450010) > 0) {
            long startTime = chr.getBuffTimeStartTime(2450010);
            int length = chr.getBuffTimeLength(2450010);
            if (startTime + length - System.currentTimeMillis() > 3000) {
                chr.updateBuffTime(2450010, (int) (startTime + length - System.currentTimeMillis()));
            } else {
                chr.updateBuffTime(2450010, 0);
            }
        }*/

        if (chr.getMessenger() != null) {
            MapleMessengerCharacter messengerplayer = new MapleMessengerCharacter(chr);
            World.Messenger.leaveMessenger(chr.getMessenger().getId(), messengerplayer);
        }
        PlayerBuffStorage.addBuffsToStorage(chr.getId(), chr.getAllBuffs());
        PlayerBuffStorage.addCooldownsToStorage(chr.getId(), chr.getCooldowns());
        PlayerBuffStorage.addDiseaseToStorage(chr.getId(), chr.getAllDiseases());
        World.ChannelChange_Data(new CharacterTransfer(chr), chr.getId(), -10);
        try {
            ch.removePlayer(chr);
        } catch (Exception ignore) {
            FileoutputUtil.outError("logs/移除角色容器异常.txt", ignore);
        }
        c.updateLoginState(MapleClient.CHANGE_CHANNEL, c.getSessionIPAddress());
        chr.saveToDB(false, false);
        chr.getMap().removePlayer(chr);
        byte[] ip = ServerConstants.Gateway_IP;
        //try {
        //    ip = InetAddress.getByName(ChannelServer.getInstance(c.getChannel()).getIP().split(":")[0]).getAddress();
        //} catch (Exception ex) {
        //}
        c.getSession().write(MaplePacketCreator.getChannelChange(c, ip, Integer.parseInt(CashShopServer.getIP().split(":")[1])));
        c.setPlayer(null);
        c.setReceiving(false);
    }

    public static final void EnterMTS(final MapleClient c, final MapleCharacter chr) {
//      no choice since client don't support the way i did earlier, this is the other way to do.       
        //chr.dropMessage(5, "Maple Trade System is not available at the moment, please try again later.");
        c.getSession().write(MaplePacketCreator.enableActions());
        c.getPlayer().marriage();
        NPCScriptManager.getInstance().start(c, 9900004);
    }

    public static final void Loggedin(final int playerid, final MapleClient c) {
        try {
            final ChannelServer channelServer = c.getChannelServer();
            MapleCharacter player;
            final CharacterTransfer transfer = channelServer.getPlayerStorage().getPendingCharacter(playerid);

            if (transfer == null) { // Player isn't in storage, probably isn't CC
                if (System.getProperty(String.valueOf(playerid)) == null || !System.getProperty(String.valueOf(playerid)).equals("1")) {
                    World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 聊天系統] 非法登录 账号 " + c.getAccountName()));
                    World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 聊天系統] 非法登录 账号 " + c.getAccountName()));
                    World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 聊天系統] 非法登录 账号 " + c.getAccountName()));
                    FileoutputUtil.logToFile("logs/Data/非法登录.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSessionIPAddress() + " 账号 " + c.getAccountName());
                    c.getSession().close();
                    return;
                } else {
                    System.setProperty(String.valueOf(playerid), String.valueOf(0));
                }
                player = MapleCharacter.loadCharFromDB(playerid, c, true);
                Pair<String, String> ip = LoginServer.getLoginAuth(playerid);
                String s = c.getSessionIPAddress();
                if (ip == null || !s.substring(s.indexOf('/') + 1, s.length()).equals(ip.left)) {
                    if (ip != null) {
                        LoginServer.putLoginAuth(playerid, ip.left, ip.right);
                    }
                    c.getSession().close();
                    return;
                }
                c.setTempIP(ip.right);
                player.setMrqdTime(System.currentTimeMillis());
            } else {
                player = MapleCharacter.ReconstructChr(transfer, c, true);
            }

            if (!LoginServer.CanLoginKey(player.getLoginKey(), player.getAccountID()) || (LoginServer.getLoginKey(player.getAccountID()) == null && !player.getLoginKey().isEmpty())) {
                FileoutputUtil.logToFile("logs/Data/客戶端登录KEY异常.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSessionIPAddress() + " 账号: " + c.getAccountName() + " 客戶端key：" + LoginServer.getLoginKey(player.getAccountID()) + " 伺服端key：" + player.getLoginKey() + " 进入游戏1");
                World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 聊天系統] 非法登录 账号 " + c.getAccountName()));
                c.getSession().close();
                return;
            }
            if (!LoginServer.CanServerKey(player.getServerKey(), player.getAccountID()) || (LoginServer.getServerKey(player.getAccountID()) == null && !player.getServerKey().isEmpty())) {
                FileoutputUtil.logToFile("logs/Data/客戶端頻道KEY异常.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSessionIPAddress() + " 账号: " + c.getAccountName() + " 客戶端key：" + LoginServer.getServerKey(player.getAccountID()) + " 伺服端key：" + player.getServerKey() + " 进入游戏2");
                World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 聊天系統] 非法登录 账号 " + c.getAccountName()));
                c.getSession().close();
                return;
            }
            if (!LoginServer.CanClientKey(player.getClientKey(), player.getAccountID()) || (LoginServer.getClientKey(player.getAccountID()) == null && !player.getClientKey().isEmpty())) {
                FileoutputUtil.logToFile("logs/Data/客戶端进入KEY异常.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSessionIPAddress() + " 账号: " + c.getAccountName() + " 客戶端key：" + LoginServer.getClientKey(player.getAccountID()) + " 伺服端key：" + player.getClientKey() + " 进入游戏3");
                World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 聊天系統] 非法登录 账号 " + c.getAccountName()));
                c.getSession().close();
                return;
            }
            c.setPlayer(player);
            c.setAccID(player.getAccountID());
            c.setSecondPassword(player.getAccountSecondPassword());

            if (!c.CheckIPAddress()) { // Remote hack
                c.getSession().close();
                FileoutputUtil.logToFile("logs/登录异常.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSessionIPAddress() + " 账号: " + c.getAccountName() + " CheckIPAddress");
                return;
            }

            final int state = c.getLoginState();
            boolean allowLogin = false;

            if (state == MapleClient.LOGIN_SERVER_TRANSITION || state == MapleClient.CHANGE_CHANNEL || state == MapleClient.LOGIN_NOTLOGGEDIN) {
                allowLogin = !World.isCharacterListConnected(c.loadCharacterNames(c.getWorld()));
            }
            if (!allowLogin) {
                c.setPlayer(null);
                c.getSession().close();
                FileoutputUtil.logToFile("logs/登录异常.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSessionIPAddress() + " 账号: " + c.getAccountName() + " !allowLogin");
                return;
            }
            player.getMrqdTime();
            c.updateLoginState(MapleClient.LOGIN_LOGGEDIN, c.getSessionIPAddress());
            channelServer.addPlayer(player);
            player.gainVip();//更新VIP等級
            player.giveCoolDowns(PlayerBuffStorage.getCooldownsFromStorage(player.getId()));
            player.silentGiveBuffs(PlayerBuffStorage.getBuffsFromStorage(player.getId()));
            player.giveSilentDebuff(PlayerBuffStorage.getDiseaseFromStorage(player.getId()));

            if (player.getAcLogS("离线挂机") > 0) {
                player.deleteAcLog("离线挂机");
                if (LoginServer.getChrPos() != null) {
                    List<String> charNames = c.loadCharacterNamesByCharId(playerid);
                    for (final String name : charNames) {
                        MapleCharacter chr = MapleCharacter.getCharacterByName(name);
                        if (LoginServer.getChrPos().containsKey(chr.getId())) {
                            LoginServer.RemoveChrPos(chr.getId());
                        }

                    }

                }
            }
            c.getSession().write(MaplePacketCreator.getCharInfo(player));
            if (player.getCharacterNameById2(playerid) == null) {
                FileoutputUtil.logToFile("logs/Data/角色不存在.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSessionIPAddress() + " 账号 " + c.getAccountName() + "登录");
                World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 聊天系統] 非法登录不存在角色 账号 " + c.getAccountName()));
                c.getSession().close();
                return;
            }

            if (!LoginServer.CanLoginKey(player.getLoginKey(), player.getAccountID()) || (LoginServer.getLoginKey(player.getAccountID()) == null && !player.getLoginKey().isEmpty())) {
                FileoutputUtil.logToFile("logs/Data/客戶端登录KEY异常.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSessionIPAddress() + " 账号: " + c.getAccountName() + " 客戶端key：" + LoginServer.getLoginKey(player.getAccountID()) + " 伺服端key：" + player.getLoginKey() + " 进入游戏4");
                World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 聊天系統] 非法登录 账号 " + c.getAccountName()));
                c.getSession().close();
                return;
            }
            if (!LoginServer.CanServerKey(player.getServerKey(), player.getAccountID()) || (LoginServer.getServerKey(player.getAccountID()) == null && !player.getServerKey().isEmpty())) {
                FileoutputUtil.logToFile("logs/Data/客戶端頻道KEY异常.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSessionIPAddress() + " 账号: " + c.getAccountName() + " 客戶端key：" + LoginServer.getServerKey(player.getAccountID()) + " 伺服端key：" + player.getServerKey() + " 进入游戏5");
                World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 聊天系統] 非法登录 账号 " + c.getAccountName()));
                c.getSession().close();
                return;
            }
            if (!LoginServer.CanClientKey(player.getClientKey(), player.getAccountID()) || (LoginServer.getClientKey(player.getAccountID()) == null && !player.getClientKey().isEmpty())) {
                FileoutputUtil.logToFile("logs/Data/客戶端进入KEY异常.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSessionIPAddress() + " 账号: " + c.getAccountName() + " 客戶端key：" + LoginServer.getClientKey(player.getAccountID()) + " 伺服端key：" + player.getClientKey() + " 进入游戏6");
                World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 聊天系統] 非法登录 账号 " + c.getAccountName()));
                c.getSession().close();
                return;
            }
            c.getSession().write(CSPacket.enableCSUse());
            c.getSession().write(MaplePacketCreator.temporaryStats_Reset()); // .
            if (player.isIntern()) {
                SkillFactory.getSkill(9001004).getEffect(1).applyTo(player);
                if (GameConstants.isKOC(player.getJob())) {
                    SkillFactory.getSkill(10001010).getEffect(1).applyTo(player, 2100000000);
                } else if (GameConstants.isAran(player.getJob())) {
                    SkillFactory.getSkill(20001010).getEffect(1).applyTo(player, 2100000000);
                } else if (GameConstants.isEvan(player.getJob())) {
                    SkillFactory.getSkill(20011010).getEffect(1).applyTo(player, 2100000000);
                } else if (GameConstants.isResist(player.getJob())) {
                    SkillFactory.getSkill(30001010).getEffect(1).applyTo(player, 2100000000);
                } else {
                    SkillFactory.getSkill(1010).getEffect(1).applyTo(player, 2100000000);
                }
            }

            /*player.dispelBuff(2450000);
            if (player.getBuffTimeCout(2450000) > 0) {
                long startTime = player.getBuffTimeStartTime(2450000);
                int length = player.getBuffTimeLength(2450000);
                if (startTime + length - System.currentTimeMillis() > 3000) {
                    MapleItemInformationProvider.getInstance().getItemEffect(2450000).applyTo(player, length);
                } else {
                    player.updateBuffTime(2450000, 0);
                }
            }
            player.dispelBuff(2450010);
            if (player.getBuffTimeCout(2450010) > 0) {
                long startTime = player.getBuffTimeStartTime(2450010);
                int length = player.getBuffTimeLength(2450010);
                if (startTime + length - System.currentTimeMillis() > 3000) {
                    MapleItemInformationProvider.getInstance().getItemEffect(2450010).applyTo(player, length);
                } else {
                    player.updateBuffTime(2450010, 0);
                }
            }*/
            player.getMap().addPlayer(player);

            try {
                // Start of buddylist
                final int buddyIds[] = player.getBuddylist().getBuddyIds();
                World.Buddy.loggedOn(player.getName(), player.getId(), c.getChannel(), buddyIds);
                if (player.getParty() != null) {
                    final MapleParty party = player.getParty();
                    World.Party.updateParty(party.getId(), PartyOperation.LOG_ONOFF, new MaplePartyCharacter(player));

                    if (party != null && party.getExpeditionId() > 0) {
                        final MapleExpedition me = World.Party.getExped(party.getExpeditionId());
                        if (me != null) {
                            c.getSession().write(MaplePacketCreator.expeditionStatus(me, false));
                        }
                    }
                }

                if (player.getSidekick() == null) {
                    player.setSidekick(World.Sidekick.getSidekickByChr(player.getId()));
                }
                if (player.getSidekick() != null) {
                    c.getSession().write(MaplePacketCreator.updateSidekick(player, player.getSidekick(), false));
                }
                final CharacterIdChannelPair[] onlineBuddies = World.Find.multiBuddyFind(player.getId(), buddyIds);
                for (CharacterIdChannelPair onlineBuddy : onlineBuddies) {
                    player.getBuddylist().get(onlineBuddy.getCharacterId()).setChannel(onlineBuddy.getChannel());
                }
                c.getSession().write(MaplePacketCreator.updateBuddylist(player.getBuddylist().getBuddies()));

                // Start of Messenger
                final MapleMessenger messenger = player.getMessenger();
                if (messenger != null) {
                    World.Messenger.silentJoinMessenger(messenger.getId(), new MapleMessengerCharacter(c.getPlayer()));
                    World.Messenger.updateMessenger(messenger.getId(), c.getPlayer().getName(), c.getChannel());
                }

                // Start of Guild and alliance
                if (player.getGuildId() > 0) {
                    World.Guild.setGuildMemberOnline(player.getMGC(), true, c.getChannel());
                    c.getSession().write(MaplePacketCreator.showGuildInfo(player));
                    final MapleGuild gs = World.Guild.getGuild(player.getGuildId());
                    if (gs != null) {
                        final List<byte[]> packetList = World.Alliance.getAllianceInfo(gs.getAllianceId(), true);
                        if (packetList != null) {
                            for (byte[] pack : packetList) {
                                if (pack != null) {
                                    c.getSession().write(pack);
                                }
                            }
                        }
                    } else { //guild not found, change guild id
                        player.setGuildId(0);
                        player.setGuildRank((byte) 5);
                        player.setAllianceRank((byte) 5);
                        player.saveGuildStatus();
                    }
                } else {
                    c.getSession().write(MaplePacketCreator.showGuildInfoa(player));
                }

                if (player.getFamilyId() > 0) {
                    World.Family.setFamilyMemberOnline(player.getMFC(), true, c.getChannel());
                }
                c.getSession().write(FamilyPacket.getFamilyData());
                c.getSession().write(FamilyPacket.getFamilyInfo(player));
            } catch (Exception e) {
                FileoutputUtil.outputFileError(FileoutputUtil.Login_Error, e);
            }
            player.getClient().getSession().write(MaplePacketCreator.serverMessage(channelServer.getServerMessage()));
            player.sendMacros();
            player.showNote();
            player.sendImp();
            player.updatePartyMemberHP();
            player.startFairySchedule(false);
            player.baseSkills(); //fix people who've lost skills.
            player.updateSkills();
            c.getSession().write(MaplePacketCreator.getKeymap(player.getKeyLayout()));
            c.getSession().write(MaplePacketCreator.showCharCash(player));
            c.getSession().write(MaplePacketCreator.updateBeans(player));
            player.updatePetAuto();
            player.expirationTask(true, transfer == null);
            if (player.getJob() == 132) { // DARKKNIGHT
                player.checkBerserk();
            }
            player.spawnClones();
            player.spawnSavedPets();
            if (player.getStat().equippedSummon > 0) {
                SkillFactory.getSkill(player.getStat().equippedSummon).getEffect(1).applyTo(player);
            }
            MapleQuestStatus stat = player.getQuestNoAdd(MapleQuest.getInstance(GameConstants.PENDANT_SLOT));
            c.getSession().write(MaplePacketCreator.pendantSlot(stat != null && stat.getCustomData() != null && Long.parseLong(stat.getCustomData()) > System.currentTimeMillis()));
            //stat = player.getQuestNoAdd(MapleQuest.getInstance(GameConstants.QUICK_SLOT));
            //c.getSession().write(MaplePacketCreator.quickSlot(stat != null && stat.getCustomData() != null && stat.getCustomData().length() == 8 ? stat.getCustomData() : null));
            if (GameConstants.GMS) {
                c.getSession().write(MaplePacketCreator.getFamiliarInfo(player));
            }
            String sb = "[登陆公告] " + (player.getGender() == 1 ? "【美女】" : "【帅哥】") + player.getName() + " : " + LoginServer.getLoginMessage();
            World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(14, c.getChannel(), sb, false));
            boolean ChrdangerousIp = player.chrdangerousIp(c.getSessionIPAddress());
            if (ChrdangerousIp) {
                World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 聊天系統] 危險IP上线" + " IP: " + c.getSessionIPAddress() + " 账号 " + c.getAccountName() + " 账号ID " + c.getAccID() + " 角色名 " + player.getName() + " 角色ID " + player.getId()));
                World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 聊天系統] 危險IP上线" + " IP: " + c.getSessionIPAddress() + " 账号 " + c.getAccountName() + " 账号ID " + c.getAccID() + " 角色名 " + player.getName() + " 角色ID " + player.getId()));
                World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 聊天系統] 危險IP上线" + " IP: " + c.getSessionIPAddress() + " 账号 " + c.getAccountName() + " 账号ID " + c.getAccID() + " 角色名 " + player.getName() + " 角色ID " + player.getId()));
                FileoutputUtil.logToFile("logs/Data/危險IP登录.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSessionIPAddress() + " 账号 " + c.getAccountName() + " 账号ID " + c.getAccID() + " 角色名 " + player.getName() + " 角色ID " + player.getId());
            }

            boolean ChrdangerousName = player.ChrDangerousAcc(player.getClient().getAccountName());
            if (ChrdangerousName) {
                World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 聊天系統] 危險角色上线" + " IP: " + c.getSessionIPAddress() + " 账号 " + c.getAccountName() + " 账号ID " + c.getAccID() + " 角色名 " + player.getName() + " 角色ID " + player.getId()));
                World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 聊天系統] 危險角色上线" + " IP: " + c.getSessionIPAddress() + " 账号 " + c.getAccountName() + " 账号ID " + c.getAccID() + " 角色名 " + player.getName() + " 角色ID " + player.getId()));
                World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 聊天系統] 危險角色上线" + " IP: " + c.getSessionIPAddress() + " 账号 " + c.getAccountName() + " 账号ID " + c.getAccID() + " 角色名 " + player.getName() + " 角色ID " + player.getId()));
                FileoutputUtil.logToFile("logs/Data/危險账号登录.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSessionIPAddress() + " 账号 " + c.getAccountName() + " 账号ID " + c.getAccID() + " 角色名 " + player.getName() + " 角色ID " + player.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
            FileoutputUtil.outError("logs/进入游戏异常.txt", e);
        }
    }

    public static final void ChangeChannel(final LittleEndianAccessor slea, final MapleClient c, final MapleCharacter chr) {
        if (chr == null || chr.hasBlockedInventory() || chr.getEventInstance() != null || chr.getMap() == null || chr.isInBlockedMap() || FieldLimitType.ChannelSwitch.check(chr.getMap().getFieldLimit())) {
            c.getSession().write(MaplePacketCreator.enableActions());
            c.getPlayer().marriage();
            return;
        }

        if (LoginServer.isAdminOnly() && !c.isGm()) {
            c.getPlayer().dropMessage(1, "无法更换頻道，请稍后再試。");
            c.getSession().write(MaplePacketCreator.enableActions());
            c.getPlayer().marriage();
            return;
        }

        if (World.isShutDown && !c.isGm()) {
            c.getPlayer().dropMessage(1, "无法更换頻道，请稍后再試。");
            c.getSession().write(MaplePacketCreator.enableActions());
            c.getPlayer().marriage();
            return;
        }
        if (chr.getAntiMacro().inProgress()) {
            chr.dropMessage(5, "被使用测谎仪时无法操作。");
            c.getSession().write(MaplePacketCreator.enableActions());
            c.getPlayer().marriage();
            return;
        }
        if (World.getPendingCharacterSize() >= 10) {
            chr.dropMessage(1, "The server is busy at the moment. Please try again in a less than a minute.");
            c.getSession().write(MaplePacketCreator.enableActions());
            c.getPlayer().marriage();
            return;
        }
        final int chc = slea.readByte() + 1;
        if (!World.isChannelAvailable(chc)) {
            chr.dropMessage(1, "The channel is full at the moment.");
            c.getSession().write(MaplePacketCreator.enableActions());
            c.getPlayer().marriage();
            return;
        }
        chr.changeChannel(chc);
    }
}
