package handling.cashshop.handler;

import client.MapleCharacter;
import client.MapleCharacterUtil;
import client.MapleClient;
import client.MapleQuestStatus;
import client.inventory.Equip;
import client.inventory.Item;
import client.inventory.ItemFlag;
import client.inventory.MapleInventoryIdentifier;
import client.inventory.MapleInventoryType;
import client.inventory.MapleRing;
import constants.GameConstants;
import constants.MTSCSConstants;
import constants.ServerConstants;
import handling.cashshop.CashShopServer;
import handling.channel.ChannelServer;
import handling.login.LoginServer;
import handling.world.CharacterTransfer;
import handling.world.World;
import java.net.InetAddress;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import server.CashItemFactory;
import server.CashItemInfo;
import server.MTSCart;
import server.MTSStorage;
import server.MapleInventoryManipulator;
import server.MapleItemInformationProvider;
import server.quest.MapleQuest;
import tools.FileoutputUtil;
import tools.packet.MaplePacketCreator;
import tools.Triple;
import tools.data.LittleEndianAccessor;
import tools.packet.CSPacket;
import tools.packet.MTSPacket;

public class CashShopOperation {

    public static void LeaveCS(final LittleEndianAccessor slea, final MapleClient c, final MapleCharacter chr) {
        if (World.isShutDown && !c.isGm()) {
            c.getPlayer().dropMessage(1, "服务器繁忙，请稍后再試。");
            doCSPackets(c);
            return;
        }

        try {
            CashShopServer.getPlayerStorage().deregisterPlayer(chr);
        } catch (Exception ignore) {
            FileoutputUtil.outError("logs/移除角色容器异常.txt", ignore);
        }
        c.updateLoginState(MapleClient.LOGIN_SERVER_TRANSITION, c.getSessionIPAddress());
        try {
            final String s = c.getSessionIPAddress();
            byte[] ip = ServerConstants.Gateway_IP;
            //try {
            //    ip = InetAddress.getByName(ChannelServer.getInstance(c.getChannel()).getIP().split(":")[0]).getAddress();
            //} catch (Exception ex) {
            //}
            LoginServer.addIPAuth(s.substring(s.indexOf('/') + 1, s.length()));
            World.ChannelChange_Data(new CharacterTransfer(chr), chr.getId(), c.getChannel());
            c.getSession().write(MaplePacketCreator.getChannelChange(c, ip, Integer.parseInt(ChannelServer.getInstance(c.getChannel()).getIP().split(":")[1])));
        } finally {
            chr.saveToDB(false, true);
            c.setPlayer(null);
            c.setReceiving(false);
            //c.getSession().close();
        }
    }

    public static void EnterCS(final int playerid, final MapleClient c) {
        CharacterTransfer transfer = CashShopServer.getPlayerStorage().getPendingCharacter(playerid);
        if (transfer == null) {
            c.getSession().close();
            return;
        }
        MapleCharacter chr = MapleCharacter.ReconstructChr(transfer, c, false);
        c.setPlayer(chr);
        c.setAccID(chr.getAccountID());
        c.setSecondPassword(chr.getAccountSecondPassword());

        if (!c.CheckIPAddress()) { // Remote hack
            c.getSession().close();
            return;
        }

        final int state = c.getLoginState();
        boolean allowLogin = false;
        if (state == MapleClient.LOGIN_SERVER_TRANSITION || state == MapleClient.CHANGE_CHANNEL) {
            if (!World.isCharacterListConnected(c.loadCharacterNames(c.getWorld()))) {
                allowLogin = true;
            }
        }
        if (!allowLogin) {
            c.setPlayer(null);
            c.getSession().close();
            return;
        }
        c.getPlayer().saveToDB(false, false);

        if (!LoginServer.CanLoginKey(c.getPlayer().getLoginKey(), c.getPlayer().getAccountID()) || (LoginServer.getLoginKey(c.getPlayer().getAccountID()) == null && !c.getPlayer().getLoginKey().isEmpty())) {
            FileoutputUtil.logToFile("logs/Data/客戶端登录KEY异常.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSessionIPAddress() + " 账号: " + c.getAccountName() + " 客戶端key：" + LoginServer.getLoginKey(c.getPlayer().getAccountID()) + " 伺服端key：" + c.getPlayer().getLoginKey() + " 进入商城1");
            World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 聊天系統] 非法登录 账号 " + c.getAccountName()));
            c.getSession().close();
            return;
        }
        if (!LoginServer.CanServerKey(c.getPlayer().getServerKey(), c.getPlayer().getAccountID()) || (LoginServer.getServerKey(c.getPlayer().getAccountID()) == null && !c.getPlayer().getServerKey().isEmpty())) {
            FileoutputUtil.logToFile("logs/Data/客戶端頻道KEY异常.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSessionIPAddress() + " 账号: " + c.getAccountName() + " 客戶端key：" + LoginServer.getServerKey(c.getPlayer().getAccountID()) + " 伺服端key：" + c.getPlayer().getServerKey() + " 进入商城2");
            World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 聊天系統] 非法登录 账号 " + c.getAccountName()));
            c.getSession().close();
            return;
        }
        if (!LoginServer.CanClientKey(c.getPlayer().getClientKey(), c.getPlayer().getAccountID()) || (LoginServer.getClientKey(c.getPlayer().getAccountID()) == null && !c.getPlayer().getClientKey().isEmpty())) {
            FileoutputUtil.logToFile("logs/Data/客戶端进入KEY异常.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSessionIPAddress() + " 账号: " + c.getAccountName() + " 客戶端key：" + LoginServer.getClientKey(c.getPlayer().getAccountID()) + " 伺服端key：" + c.getPlayer().getClientKey() + " 进入商城3");
            World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 聊天系統] 非法登录 账号 " + c.getAccountName()));
            c.getSession().close();
            return;
        }

        c.updateLoginState(MapleClient.LOGIN_LOGGEDIN, c.getSessionIPAddress());

        CashShopServer.getPlayerStorage().registerPlayer(chr);
        c.getSession().write(CSPacket.warpCS(c));
        c.getSession().write(CSPacket.intoCs(c));
        CSUpdate(c);
        if (c.getPlayer().getCharacterNameById2(playerid) == null) {
            FileoutputUtil.logToFile("logs/Data/角色不存在.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSessionIPAddress() + " 账号 " + c.getAccountName() + "进入商城");
            World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 聊天系統] 非法登录不存在角色 账号 " + c.getAccountName()));
            c.getSession().close();
            return;
        }

        if (!LoginServer.CanLoginKey(c.getPlayer().getLoginKey(), c.getPlayer().getAccountID()) || (LoginServer.getLoginKey(c.getPlayer().getAccountID()) == null && !c.getPlayer().getLoginKey().isEmpty())) {
            FileoutputUtil.logToFile("logs/Data/客戶端登录KEY异常.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSessionIPAddress() + " 账号: " + c.getAccountName() + " 客戶端key：" + LoginServer.getLoginKey(c.getPlayer().getAccountID()) + " 伺服端key：" + c.getPlayer().getLoginKey() + " 进入商城1");
            World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 聊天系統] 非法登录 账号 " + c.getAccountName()));
            c.getSession().close();
            return;
        }
        if (!LoginServer.CanServerKey(c.getPlayer().getServerKey(), c.getPlayer().getAccountID()) || (LoginServer.getServerKey(c.getPlayer().getAccountID()) == null && !c.getPlayer().getServerKey().isEmpty())) {
            FileoutputUtil.logToFile("logs/Data/客戶端頻道KEY异常.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSessionIPAddress() + " 账号: " + c.getAccountName() + " 客戶端key：" + LoginServer.getServerKey(c.getPlayer().getAccountID()) + " 伺服端key：" + c.getPlayer().getServerKey() + " 进入商城2");
            World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 聊天系統] 非法登录 账号 " + c.getAccountName()));
            c.getSession().close();
            return;
        }
        if (!LoginServer.CanClientKey(c.getPlayer().getClientKey(), c.getPlayer().getAccountID()) || (LoginServer.getClientKey(c.getPlayer().getAccountID()) == null && !c.getPlayer().getClientKey().isEmpty())) {
            FileoutputUtil.logToFile("logs/Data/客戶端进入KEY异常.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSessionIPAddress() + " 账号: " + c.getAccountName() + " 客戶端key：" + LoginServer.getClientKey(c.getPlayer().getAccountID()) + " 伺服端key：" + c.getPlayer().getClientKey() + " 进入商城3");
            World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 聊天系統] 非法登录 账号 " + c.getAccountName()));
            c.getSession().close();
            return;
        }
    }

    public static void CSUpdate(final MapleClient c) {
        c.getSession().write(CSPacket.showCashShopAcc(c));
        c.getSession().write(CSPacket.getCSGifts(c));
        doCSPackets(c);
        c.getSession().write(CSPacket.sendWishList(c.getPlayer(), false));
    }

    public static void CouponCode(final String code, final MapleClient c) {
        if (code.length() <= 0) {
            return;
        }
        Triple<Boolean, Integer, Integer> info = null;
        try {
            info = MapleCharacterUtil.getNXCodeInfo(code);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (info != null && info.left) {
            int type = info.mid, item = info.right;
            try {
                MapleCharacterUtil.setNXCodeUsed(c.getPlayer().getName(), code);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            /*
             * Explanation of type!
             * Basically, this makes coupon codes do
             * different things!
             *
             * Type 1: A-Cash,
             * Type 2: Maple Points
             * Type 3: Item.. use SN
             * Type 4: Mesos
             */
            Map<Integer, Item> itemz = new HashMap<Integer, Item>();
            int maplePoints = 0, mesos = 0;
            switch (type) {
                case 1:
                case 2:
                    c.getPlayer().modifyCSPoints(type, item, false);
                    maplePoints = item;
                    break;
                case 3:
                    CashItemInfo itez = CashItemFactory.getInstance().getItem(item);
                    if (itez == null) {
                        c.getSession().write(CSPacket.sendCSFail(0));
                        return;
                    }
                    byte slot = MapleInventoryManipulator.addId(c, itez.getId(), (short) 1, "", "Cash shop: coupon code" + " on " + FileoutputUtil.CurrentReadable_Date());
                    if (slot <= -1) {
                        c.getSession().write(CSPacket.sendCSFail(0));
                        return;
                    } else {
                        itemz.put(item, c.getPlayer().getInventory(GameConstants.getInventoryType(item)).getItem(slot));
                    }
                    break;
                case 4:
                    c.getPlayer().gainMeso(item, false);
                    mesos = item;
                    break;
            }
            c.getSession().write(CSPacket.showCouponRedeemedItem(itemz, mesos, maplePoints, c));
        } else {
            c.getSession().write(CSPacket.sendCSFail(info == null ? 0xA7 : 0xA5)); //A1, 9F
        }
    }

    public static void SendGift(final LittleEndianAccessor slea, final MapleClient c, final MapleCharacter chr) {
        //final String secondPassword = slea.readMapleAsciiString();
        final CashItemInfo item = CashItemFactory.getInstance().getItem(slea.readInt());
        int type = slea.readByte() + 1;
        String partnerName = slea.readMapleAsciiString();
        String msg = slea.readMapleAsciiString();
        if (item == null || c.getPlayer().getCSPoints(type) < item.getPrice() || msg.length() > 73 || msg.length() < 1) { //dont want packet editors gifting random stuff =P
            c.getSession().write(CSPacket.sendCSFail(0));
            doCSPackets(c);
            return;
        }
        //if (item.getId() == 5220010) {
        //    chr.dropMessage(1, "该道具不能贈送");
        //    doCSPackets(c);
        //    return;
        //}

        Triple<Integer, Integer, Integer> info = MapleCharacterUtil.getInfoByName(partnerName, c.getPlayer().getWorld());
        /*if (c.getSecondPassword() != null) {
            if (secondPassword == null) { // Client's hacking
                c.getPlayer().dropMessage(1, "请输入密码。");
                doCSPackets(c);
                return;
            } else if (!c.CheckSecondPassword(secondPassword)) { // 第二密码错误

                c.getPlayer().dropMessage(1, "密码错误。");
                doCSPackets(c);
                return;
            }*/

        if (info == null || info.getLeft().intValue() <= 0 || info.getLeft().intValue() == c.getPlayer().getId() || info.getMid().intValue() == c.getAccID()) {
            c.getPlayer().dropMessage(1, "发生未知错误。");
            doCSPackets(c);
            return;
        } else if (!item.genderEquals(info.getRight())) {
            c.getPlayer().dropMessage(1, "发生未知错误。");
            doCSPackets(c);
            return;
        } else {
            for (int i : MTSCSConstants.cashBlock) {
                if (item.getId() == i) {
                    c.getPlayer().dropMessage(1, MTSCSConstants.getCashBlockedMsg(item.getId()));
                    doCSPackets(c);
                    return;
                }
            }
            if (ServerConstants.logs_csbuy) {
                FileoutputUtil.logToFile("logs/Data/商城送禮.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSessionIPAddress() + " 账号: " + c.getAccountName() + " 玩家: " + c.getPlayer().getName() + " 使用了点券" + item.getPrice() + "点 贈送了" + item.getId() + "x" + item.getCount() + " 給" + partnerName + " 贈言：" + msg);
            }
            if (ServerConstants.message_csbuy) {
                World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 聊天]『" + chr.getName() + "』(" + chr.getId() + ")地图『" + chr.getMapId() + "』使用了点券" + item.getPrice() + "点 贈送了" + item.getId() + "x" + item.getCount() + " 給" + partnerName + " 贈言：" + msg));
            }
            c.getPlayer().getCashInventory().gift(info.getLeft().intValue(), c.getPlayer().getName(), msg, item.getSN(), MapleInventoryIdentifier.getInstance());
            c.getPlayer().modifyCSPoints(1, -item.getPrice(), false);
            chr.sendNote(partnerName, chr.getName() + " 送了你禮物! 趕快去商城确认看看.", (byte) 0); //fame or not
            c.getSession().write(CSPacket.sendGift(item.getPrice(), item.getId(), item.getCount(), partnerName));
            MapleCharacter receiver = c.getChannelServer().getPlayerStorage().getCharacterByName(partnerName);
            if (receiver != null) {
                receiver.showNote();
            }
        }
        //}
        doCSPackets(c);
    }

    public static final void BuyCashItem(final LittleEndianAccessor slea, final MapleClient c, final MapleCharacter chr) {
        final int action = slea.readByte();
        //System.out.println("商城操作ID: " + action);
        if (action == 0) {
            slea.skip(2);
            CouponCode(slea.readMapleAsciiString(), c);
        } else if (action == 3) {
            final int toCharge = slea.readByte() + 1;
            final CashItemInfo item = CashItemFactory.getInstance().getItem(slea.readInt());
            if (c.getPlayer().isAdmin() || LoginServer.isLogPackets()) {
                System.out.println("购买事件ID: " + action + " 物品代码: " + item.getId() + " 物品SN:" + item.getSN());
            }
            if (item != null) {
                //System.out.println("购买事件ID: " + action + " 物品代码: " + item.getId() + " 物品SN:" + item.getSN());
            }
            if (item != null && chr.getCSPoints(toCharge) >= item.getPrice()) {
                if (!item.genderEquals(c.getPlayer().getGender())) {
                    c.getSession().write(CSPacket.sendCSFail(0xA6));
                    doCSPackets(c);
                    return;
                } else if (c.getPlayer().getCashInventory().getItemsSize() >= 100) {
                    c.getSession().write(CSPacket.sendCSFail(0xB1));
                    doCSPackets(c);
                    return;
                }

                if (toCharge == 2 && (/*item.getId() == 5062000 ||*/item.getId() == 1112127 || item.getId() == 1112127 || item.getId() == 5570000 || item.getId() == 5251015 || item.getId() == 5220010 || item.getId() == 5064000 || item.getId() == 5220010 || item.getId() == 5220000 || item.getId() == 5062000 || item.getId() == 5062001 || item.getId() == 5064000 || item.getId() == 5050000)) {
                    chr.dropMessage(1, "该道具只能通过点卷购买。");
                    doCSPackets(c);
                    return;
                }
                /*if (item.getId() == 5062001) {
                    if (toCharge == 2) {
                        if (chr.getAcLogD("超方购买") >= 1) {
                            chr.dropMessage(1, "你今天已经使用枫叶点數购买超級方块，无法繼續购买。");
                            doCSPackets(c);
                            return;
                        }
                        chr.setAcLog("超方购买");
                    }
                    if (chr.getLevel() < 120) {
                        chr.dropMessage(1, "你的等級不足120，无法购买。");
                        doCSPackets(c);
                        return;
                    }
                }*/

                for (int i : MTSCSConstants.cashBlock) {
                    if (item.getId() == i) {
                        c.getPlayer().dropMessage(1, MTSCSConstants.getCashBlockedMsg(item.getId()));
                        doCSPackets(c);
                        return;
                    }
                }
                chr.modifyCSPoints(toCharge, -item.getPrice(), false);
                Item itemz = chr.getCashInventory().toItem(item);
                if (itemz != null && itemz.getUniqueId() > 0 && itemz.getItemId() == item.getId() && itemz.getQuantity() == item.getCount()) {
                    chr.getCashInventory().addToInventory(itemz);
                    c.getSession().write(CSPacket.showBoughtCSItem(itemz, item.getSN(), c.getAccID()));
                    if (ServerConstants.logs_csbuy) {
                        FileoutputUtil.logToFile("logs/Data/商城购买.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSessionIPAddress() + " 账号: " + c.getAccountName() + " 玩家: " + c.getPlayer().getName() + " 使用了" + (toCharge == 1 ? "点券" : "枫叶点數") + item.getPrice() + "点 來购买" + item.getId() + "x" + item.getCount());
                    }
                    if (ServerConstants.message_csbuy) {
                        World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 聊天]『" + chr.getName() + "』(" + chr.getId() + ")地图『" + chr.getMapId() + "』使用了" + (toCharge == 1 ? "点券" : "枫叶点數") + item.getPrice() + "点 來购买" + item.getId() + "x" + item.getCount()));
                    }
                } else {
                    c.getSession().write(CSPacket.sendCSFail(0));
                }
            } else {
                c.getSession().write(CSPacket.sendCSFail(0));
            }
            doCSPackets(c);
        } else if (action == 4 || action == (GameConstants.GMS ? 34 : 33)) { //gift, package
            //final String secondPassword = slea.readMapleAsciiString();
            final CashItemInfo item = CashItemFactory.getInstance().getItem(slea.readInt());
            int type = slea.readByte() + 1;
            String partnerName = slea.readMapleAsciiString();
            String msg = slea.readMapleAsciiString();
            if (item == null || c.getPlayer().getCSPoints(type) < item.getPrice() || msg.length() > 73 || msg.length() < 1) { //dont want packet editors gifting random stuff =P
                c.getSession().write(CSPacket.sendCSFail(0));
                doCSPackets(c);
                return;
            }
            //if (item.getId() == 5220010) {
            //    chr.dropMessage(1, "该道具不能贈送");
            //    doCSPackets(c);
            //    return;
            //}

            Triple<Integer, Integer, Integer> info = MapleCharacterUtil.getInfoByName(partnerName, c.getPlayer().getWorld());
            /*if (c.getSecondPassword() != null) {
            if (secondPassword == null) { // Client's hacking
                c.getPlayer().dropMessage(1, "请输入密码。");
                doCSPackets(c);
                return;
            } else if (!c.CheckSecondPassword(secondPassword)) { // 第二密码错误

                c.getPlayer().dropMessage(1, "密码错误。");
                doCSPackets(c);
                return;
            }*/

            if (info == null || info.getLeft().intValue() <= 0 || info.getLeft().intValue() == c.getPlayer().getId() || info.getMid().intValue() == c.getAccID()) {
                c.getPlayer().dropMessage(1, "发生未知错误。");
                doCSPackets(c);
                return;
            } else if (!item.genderEquals(info.getRight())) {
                c.getPlayer().dropMessage(1, "发生未知错误。");
                doCSPackets(c);
                return;
            } else {
                for (int i : MTSCSConstants.cashBlock) {
                    if (item.getId() == i) {
                        c.getPlayer().dropMessage(1, MTSCSConstants.getCashBlockedMsg(item.getId()));
                        doCSPackets(c);
                        return;
                    }
                }
                if (ServerConstants.logs_csbuy) {
                    FileoutputUtil.logToFile("logs/Data/商城送禮.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSessionIPAddress() + " 账号: " + c.getAccountName() + " 玩家: " + c.getPlayer().getName() + " 使用了点券" + item.getPrice() + "点 贈送了" + item.getId() + "x" + item.getCount() + " 給" + partnerName + " 贈言：" + msg);
                }
                if (ServerConstants.message_csbuy) {
                    World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 聊天]『" + chr.getName() + "』(" + chr.getId() + ")地图『" + chr.getMapId() + "』使用了点券" + item.getPrice() + "点 贈送了" + item.getId() + "x" + item.getCount() + " 給" + partnerName + " 贈言：" + msg));
                }
                c.getPlayer().getCashInventory().gift(info.getLeft().intValue(), c.getPlayer().getName(), msg, item.getSN(), MapleInventoryIdentifier.getInstance());
                c.getPlayer().modifyCSPoints(1, -item.getPrice(), false);
                chr.sendNote(partnerName, chr.getName() + " 送了你禮物! 趕快去商城确认看看.", (byte) 0); //fame or not
                c.getSession().write(CSPacket.sendGift(item.getPrice(), item.getId(), item.getCount(), partnerName));
                MapleCharacter receiver = c.getChannelServer().getPlayerStorage().getCharacterByName(partnerName);
                if (receiver != null) {
                    receiver.showNote();
                }
            }

        } else if (action == 5) { // Wishlist
            chr.clearWishlist();
            if (slea.available() < 40) {
                c.getSession().write(CSPacket.sendCSFail(0));
                doCSPackets(c);
                return;
            }
            int[] wishlist = new int[10];
            for (int i = 0; i < 10; i++) {
                wishlist[i] = slea.readInt();
            }
            chr.setWishlist(wishlist);
            c.getSession().write(CSPacket.sendWishList(chr, true));

        } else if (action == 0x6) { // Increase inv
            final int toCharge = slea.readByte() + 1;
            final boolean coupon = slea.readByte() > 0;
            if (coupon) {
                final MapleInventoryType type = getInventoryType(slea.readInt());

                if (chr.getCSPoints(toCharge) >= 600 && chr.getInventory(type).getSlotLimit() < 89) {
                    chr.modifyCSPoints(toCharge, -600, false);
                    chr.getInventory(type).addSlot((byte) 8);
                    chr.dropMessage(1, "栏位已经扩充到 " + chr.getInventory(type).getSlotLimit());
                    if (ServerConstants.logs_csbuy) {
                        FileoutputUtil.logToFile("logs/Data/商城扩充.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSessionIPAddress() + " 账号: " + c.getAccountName() + " 玩家: " + c.getPlayer().getName() + " 使用了" + (toCharge == 1 ? "点券" : "枫叶点數") + "600点 來购买扩充栏位" + type.name() + "8格 目前共有" + chr.getInventory(type).getSlotLimit() + "格");
                    }
                    if (ServerConstants.message_csbuy) {
                        World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 聊天]『" + chr.getName() + "』(" + chr.getId() + ")地图『" + chr.getMapId() + "』使用了" + (toCharge == 1 ? "点券" : "枫叶点數") + "600点 來购买扩充栏位" + type.name() + "8格 目前共有" + chr.getInventory(type).getSlotLimit() + "格"));
                    }
                } else {
                    c.getSession().write(CSPacket.sendCSFail(0xA4));
                }
            } else {
                final MapleInventoryType type = MapleInventoryType.getByType(slea.readByte());

                if (chr.getCSPoints(toCharge) >= 600 && chr.getInventory(type).getSlotLimit() < 93) {
                    chr.modifyCSPoints(toCharge, -600, false);
                    chr.getInventory(type).addSlot((byte) 4);
                    chr.dropMessage(1, "栏位已经扩充到 " + chr.getInventory(type).getSlotLimit());
                    if (ServerConstants.logs_csbuy) {
                        FileoutputUtil.logToFile("logs/Data/商城扩充.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSessionIPAddress() + " 账号: " + c.getAccountName() + " 玩家: " + c.getPlayer().getName() + " 使用了" + (toCharge == 1 ? "点券" : "枫叶点數") + "600点 來购买扩充栏位" + type.name() + "4格 目前共有" + chr.getInventory(type).getSlotLimit() + "格");
                    }
                    if (ServerConstants.message_csbuy) {
                        World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 聊天]『" + chr.getName() + "』(" + chr.getId() + ")地图『" + chr.getMapId() + "』使用了" + (toCharge == 1 ? "点券" : "枫叶点數") + "600点 來购买扩充栏位" + type.name() + "4格 目前共有" + chr.getInventory(type).getSlotLimit() + "格"));
                    }
                } else {
                    c.getSession().write(CSPacket.sendCSFail(0xA4));
                }
            }

        } else if (action == 7) { // Increase slot space
            final int toCharge = slea.readByte() + 1;
            if (chr.getCSPoints(toCharge) >= 600 && chr.getStorage().getSlots() < 45) {
                chr.modifyCSPoints(toCharge, -600, false);
                chr.getStorage().increaseSlots((byte) 4);
                chr.getStorage().saveToDB();
                chr.dropMessage(1, "仓库栏位已经扩充到: " + chr.getStorage().getSlots());
                if (ServerConstants.logs_csbuy) {
                    FileoutputUtil.logToFile("logs/Data/商城扩充.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSessionIPAddress() + " 账号: " + c.getAccountName() + " 玩家: " + c.getPlayer().getName() + " 使用了" + (toCharge == 1 ? "点券" : "枫叶点數") + "600点 來购买扩充栏位仓库4格 目前共有" + chr.getStorage().getSlots() + "格");
                }
                if (ServerConstants.message_csbuy) {
                    World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 聊天]『" + chr.getName() + "』(" + chr.getId() + ")地图『" + chr.getMapId() + "』使用了" + (toCharge == 1 ? "点券" : "枫叶点數") + "600点 來购买扩充栏位仓库4格 目前共有" + chr.getStorage().getSlots() + "格"));
                }
            } else {
                c.getSession().write(CSPacket.sendCSFail(0xA4));
            }
        } else if (action == 8) { //...9 = pendant slot expansion
            final int toCharge = slea.readByte() + 1;
            CashItemInfo item = CashItemFactory.getInstance().getItem(slea.readInt());
            int slots = c.getCharacterSlots();
            if (item == null || c.getPlayer().getCSPoints(toCharge) < item.getPrice() || slots > 15 || item.getId() != 5430000) {
                c.getSession().write(CSPacket.sendCSFail(0));
                doCSPackets(c);
                return;
            }
            if (c.gainCharacterSlot()) {
                c.getPlayer().modifyCSPoints(toCharge, -item.getPrice(), false);
                chr.dropMessage(1, "角色栏位已经扩充到:  " + (slots + 1));
                if (ServerConstants.logs_csbuy) {
                    FileoutputUtil.logToFile("logs/Data/商城扩充.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSessionIPAddress() + " 账号: " + c.getAccountName() + " 玩家: " + c.getPlayer().getName() + " 使用了" + (toCharge == 1 ? "点券" : "枫叶点數") + item.getPrice() + "点 來购买扩充角色栏位 目前共有" + c.getCharacterSlots() + "格");
                }
                if (ServerConstants.message_csbuy) {
                    World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 聊天]『" + chr.getName() + "』(" + chr.getId() + ")地图『" + chr.getMapId() + "』使用了" + (toCharge == 1 ? "点券" : "枫叶点數") + item.getPrice() + "点 來购买扩充角色栏位 目前共有" + c.getCharacterSlots() + "格"));
                }
            } else {
                c.getSession().write(CSPacket.sendCSFail(0));
            }
        } else if (action == 10) { //...9 = pendant slot expansion
            final int toCharge = slea.readByte() + 1;
            int sn = slea.readInt();
            CashItemInfo cItem = CashItemFactory.getInstance().getItem(sn);
            if ((cItem == null) || (chr.getCSPoints(toCharge) < cItem.getPrice()) || (cItem.getId() / 10000 != 555)) {
                chr.dropMessage(1, "项链扩充失敗，CASH或枫叶点數不足或者出現其他错误。");
                doCSPackets(c);
                return;
            }
            MapleQuestStatus marr = chr.getQuestNoAdd(MapleQuest.getInstance(GameConstants.PENDANT_SLOT));
            if ((marr != null) && (marr.getCustomData() != null) && (Long.parseLong(marr.getCustomData()) >= System.currentTimeMillis())) {
                chr.dropMessage(1, "项链扩充失敗，你已经进行过项链扩充了。");
            } else {
                long days = 0;
                if (cItem.getId() == 5550000) {
                    days = 30;
                } else if (cItem.getId() == 5550001) {
                    days = 7;
                }
                String customData = String.valueOf(System.currentTimeMillis() + days * 24L * 60L * 60L * 1000L);
                chr.getQuestNAdd(MapleQuest.getInstance(GameConstants.PENDANT_SLOT)).setCustomData(customData);
                chr.modifyCSPoints(toCharge, -cItem.getPrice(), false);
                if (ServerConstants.logs_csbuy) {
                    FileoutputUtil.logToFile("logs/Data/商城扩充.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSessionIPAddress() + " 账号: " + c.getAccountName() + " 玩家: " + c.getPlayer().getName() + " 使用了" + (toCharge == 1 ? "点券" : "枫叶点數") + cItem.getPrice() + "点 來购买项链扩充");
                }
                if (ServerConstants.message_csbuy) {
                    World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 聊天]『" + chr.getName() + "』(" + chr.getId() + ")地图『" + chr.getMapId() + "』使用了" + (toCharge == 1 ? "点券" : "枫叶点數") + cItem.getPrice() + "点 來购买项链扩充"));
                }
                chr.dropMessage(1, new StringBuilder().append("项链扩充成功，本次扩充花費:\r\n").append(toCharge == 1 ? "CASH" : "枫叶点數").append(cItem.getPrice()).append(" ，持續時间为: ").append(days).append(" 天。").toString());
            }
            doCSPackets(c);
        } else if (action == 0x0E) { //get item from csinventory
            Item item = c.getPlayer().getCashInventory().findByCashId((int) slea.readLong());
            if (item != null && item.getQuantity() > 0 && MapleInventoryManipulator.checkSpace(c, item.getItemId(), item.getQuantity(), item.getOwner())) {
                Item item_ = item.copy();
                short pos = MapleInventoryManipulator.addbyItem(c, item_, true);
                if (pos >= 0) {
                    if (item_.getPet() != null) {
                        item_.getPet().setInventoryPosition(pos);
                        c.getPlayer().addPet(item_.getPet());
                    }
                    c.getPlayer().getCashInventory().removeFromInventory(item);
                    c.getSession().write(CSPacket.confirmFromCSInventory(item_, pos));
                    if (ServerConstants.logs_csbuy) {
                        FileoutputUtil.logToFile("logs/Data/商城拿出.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSessionIPAddress() + " 账号: " + c.getAccountName() + " 玩家: " + c.getPlayer().getName() + " 從商城拿出 " + item_.getItemId() + "x" + item_.getQuantity());
                    }
                    if (ServerConstants.message_csbuy) {
                        World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 聊天]『" + chr.getName() + "』(" + chr.getId() + ")地图『" + chr.getMapId() + "』從商城拿出 " + item_.getItemId() + "x" + item_.getQuantity()));
                    }
                } else {
                    c.getSession().write(CSPacket.sendCSFail(0xB1));
                }
                doCSPackets(c);
            } else {
                c.getSession().write(CSPacket.sendCSFail(0xB1));
            }
        } else if (action == 0x0F) { //put item in cash inventory
            int uniqueid = (int) slea.readLong();
            MapleInventoryType type = MapleInventoryType.getByType(slea.readByte());
            Item item = c.getPlayer().getInventory(type).findByUniqueId(uniqueid);
            if (item != null && item.getQuantity() > 0 && item.getUniqueId() > 0 && c.getPlayer().getCashInventory().getItemsSize() < 100) {
                Item item_ = item.copy();
                if (ItemFlag.LOCK.check(item_.getFlag())) {
                    chr.dropMessage(1, "上锁道具不能存入商城。");
                    doCSPackets(c);
                    return;
                }
                /*if (GameConstants.getInventoryType(item_.getItemId()) == MapleInventoryType.EQUIP) {
                    Equip source = (Equip) chr.getInventory(MapleInventoryType.EQUIP).getItem(item_.getPosition());
                    if (source != null) {
                        if (ItemFlag.LOCK.check(item.getFlag())) {
                            chr.dropMessage(1, "上锁道具不能存入商城。");
                            doCSPackets(c);
                            return;
                        }
                    }
                }*/
                //MapleInventoryManipulator.removeFromSlot(c, type, item.getPosition(), item.getQuantity(), false);
                c.getPlayer().getInventory(type).removeItem(item.getPosition(), item.getQuantity(), false);
                int sn = CashItemFactory.getInstance().getItemSN(item_.getItemId());
                if (item_.getPet() != null) {
                    c.getPlayer().removePetCS(item_.getPet());
                }
                item_.setPosition((byte) 0);
                //item_.setGMLog("購物商城购买 時间: " + FileoutputUtil.CurrentReadable_Time());
                c.getPlayer().getCashInventory().addToInventory(item_);
                c.getSession().write(CSPacket.confirmToCSInventory(item, c.getAccID(), sn));
                if (ServerConstants.logs_csbuy) {
                    FileoutputUtil.logToFile("logs/Data/商城存入.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSessionIPAddress() + " 账号: " + c.getAccountName() + " 玩家: " + c.getPlayer().getName() + " 從商城存入 " + item_.getItemId() + "x" + item_.getQuantity());
                }
                if (ServerConstants.message_csbuy) {
                    World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 聊天]『" + chr.getName() + "』(" + chr.getId() + ")地图『" + chr.getMapId() + "』從商城存入 " + item_.getItemId() + "x" + item_.getQuantity()));
                }
            } else {
                c.getSession().write(CSPacket.sendCSFail(0xB1));
            }
            doCSPackets(c);
        } else if (action == 0x1F || action == 0x26) { //37 = friendship, 31 = crush
            //final String secondPassword = slea.readMapleAsciiString();
            final int toCharge = 1;
            final CashItemInfo item = CashItemFactory.getInstance().getItem(slea.readInt());
            final String partnerName = slea.readMapleAsciiString();
            final String msg = slea.readMapleAsciiString();
            if (item == null || !GameConstants.isEffectRing(item.getId()) || c.getPlayer().getCSPoints(toCharge) < item.getPrice() || msg.length() > 73 || msg.length() < 1) {
                c.getSession().write(CSPacket.sendCSFail(0));
                doCSPackets(c);
                return;
            } else if (!item.genderEquals(c.getPlayer().getGender())) {
                c.getPlayer().dropMessage(1, "性別不符。");
                doCSPackets(c);
                return;
            } else if (c.getPlayer().getCashInventory().getItemsSize() >= 100) {
                c.getPlayer().dropMessage(1, "您的商城道具栏已满。");
                doCSPackets(c);
                return;
            } else if (chr.getCSPoints(1) < item.getPrice()) {
                c.getPlayer().dropMessage(1, "您的点卷不足。");
                doCSPackets(c);
                return;
                //} else if (!c.CheckSecondPassword(secondPassword)) {
                //    c.getPlayer().dropMessage(1, "密码错误。");
                //    doCSPackets(c);
                //    return;
            }

            for (int i : MTSCSConstants.cashBlock) { //just incase hacker
                if (item.getId() == i) {
                    c.getPlayer().dropMessage(1, MTSCSConstants.getCashBlockedMsg(item.getId()));
                    doCSPackets(c);
                    return;
                }
            }
            Triple<Integer, Integer, Integer> info = MapleCharacterUtil.getInfoByName(partnerName, c.getPlayer().getWorld());
            if (info == null || info.getLeft() <= 0 || info.getLeft() == c.getPlayer().getId()) {
                c.getPlayer().dropMessage(1, "发生未知错误。");
                doCSPackets(c);
                return;
            } else if (info.getMid() == c.getAccID()) {
                c.getPlayer().dropMessage(1, "发生未知错误。");
                doCSPackets(c);
                return;
            } else {
                if (info.getRight() == c.getPlayer().getGender() && action == 0x1F) {
                    c.getPlayer().dropMessage(1, "性別不符。");
                    doCSPackets(c);
                    return;
                }
                int err = MapleRing.createRing(item.getId(), c.getPlayer(), partnerName, msg, info.getLeft(), item.getSN());
                if (err != 1) {
                    c.getSession().write(CSPacket.sendCSFail(0)); //9E v75
                    doCSPackets(c);
                    return;
                }
                c.getPlayer().modifyCSPoints(toCharge, -item.getPrice(), false);
                chr.sendNote(partnerName, chr.getName() + " 送了你禮物! 趕快去商城确认看看.", (byte) 0); //fame or not
                c.getSession().write(CSPacket.sendGift(item.getPrice(), item.getId(), item.getCount(), partnerName));
                MapleCharacter receiver = c.getChannelServer().getPlayerStorage().getCharacterByName(partnerName);
                if (receiver != null) {
                    receiver.showNote();
                }
                if (ServerConstants.logs_csbuy) {
                    FileoutputUtil.logToFile("logs/Data/商城送禮.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSessionIPAddress() + " 账号: " + c.getAccountName() + " 玩家: " + c.getPlayer().getName() + " 使用了点券" + item.getPrice() + "点 贈送了" + item.getId() + "x" + item.getCount() + " 給" + partnerName + " 贈言：" + msg);
                }

                if (ServerConstants.message_csbuy) {
                    World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 聊天]『" + chr.getName() + "』(" + chr.getId() + ")地图『" + chr.getMapId() + "』使用了点券" + item.getPrice() + "点 贈送了" + item.getId() + "x" + item.getCount() + " 給" + partnerName + " 贈言：" + msg));
                }
            }
        } else if (action == (GameConstants.GMS ? 33 : 32)) {
            slea.skip(1);
            final int toCharge = GameConstants.GMS ? slea.readInt() : 1;
            final CashItemInfo item = CashItemFactory.getInstance().getItem(slea.readInt());
            List<Integer> ccc = null;
            if (item != null) {
                ccc = CashItemFactory.getInstance().getPackageItems(item.getId());
            }
            if (item == null || ccc == null || c.getPlayer().getCSPoints(toCharge) < item.getPrice()) {
                c.getSession().write(CSPacket.sendCSFail(0));
                doCSPackets(c);
                return;
            } else if (!item.genderEquals(c.getPlayer().getGender())) {
                c.getSession().write(CSPacket.sendCSFail(0xA6));
                doCSPackets(c);
                return;
            } else if (c.getPlayer().getCashInventory().getItemsSize() >= (100 - ccc.size())) {
                c.getSession().write(CSPacket.sendCSFail(0xB1));
                doCSPackets(c);
                return;
            }
            for (int iz : MTSCSConstants.cashBlock) {
                if (item.getId() == iz) {
                    c.getPlayer().dropMessage(1, MTSCSConstants.getCashBlockedMsg(item.getId()));
                    doCSPackets(c);
                    return;
                }
            }
            Map<Integer, Item> ccz = new HashMap<Integer, Item>();
            for (int i : ccc) {
                final CashItemInfo cii = CashItemFactory.getInstance().getSimpleItem(i);
                if (cii == null) {
                    continue;
                }
                Item itemz = c.getPlayer().getCashInventory().toItem(cii);
                if (itemz == null || itemz.getUniqueId() <= 0) {
                    continue;
                }
                for (int iz : MTSCSConstants.cashBlock) {
                    if (itemz.getItemId() == iz) {
                        continue;
                    }
                }
                ccz.put(i, itemz);
                c.getPlayer().getCashInventory().addToInventory(itemz);
            }
            chr.modifyCSPoints(toCharge, -item.getPrice(), false);
            c.getSession().write(CSPacket.showBoughtCSPackage(ccz, c.getAccID()));
        } else if (action == (GameConstants.GMS ? 35 : 34)) {
            final CashItemInfo item = CashItemFactory.getInstance().getItem(slea.readInt());
            if (item == null || !MapleItemInformationProvider.getInstance().isQuestItem(item.getId())) {
                c.getSession().write(CSPacket.sendCSFail(0));
                doCSPackets(c);
                return;
            } else if (c.getPlayer().getMeso() < item.getPrice()) {
                c.getSession().write(CSPacket.sendCSFail(0xB8));
                doCSPackets(c);
                return;
            } else if (c.getPlayer().getInventory(GameConstants.getInventoryType(item.getId())).getNextFreeSlot() < 0) {
                c.getSession().write(CSPacket.sendCSFail(0xB1));
                doCSPackets(c);
                return;
            }
            for (int iz : MTSCSConstants.cashBlock) {
                if (item.getId() == iz) {
                    c.getPlayer().dropMessage(1, MTSCSConstants.getCashBlockedMsg(item.getId()));
                    doCSPackets(c);
                    return;
                }
            }
            byte pos = MapleInventoryManipulator.addId(c, item.getId(), (short) item.getCount(), null, "Cash shop: quest item" + " on " + FileoutputUtil.CurrentReadable_Date());
            if (pos < 0) {
                c.getSession().write(CSPacket.sendCSFail(0xB1));
                doCSPackets(c);
                return;
            }
            chr.gainMeso(-item.getPrice(), false);
            c.getSession().write(CSPacket.showBoughtCSQuestItem(item.getPrice(), (short) item.getCount(), pos, item.getId()));
        } else if (action == (GameConstants.GMS ? 46 : 45)) { //idk
            c.getSession().write(CSPacket.redeemResponse());
        } else if (action == 44) { //idk
        } else if (action == 57) { //idk
        } else {
            c.getSession().write(CSPacket.sendCSFail(0));
        }
        doCSPackets(c);
    }

    private static final MapleInventoryType getInventoryType(final int id) {
        switch (id) {
            case 50200075:
                return MapleInventoryType.EQUIP;
            case 50200074:
                return MapleInventoryType.USE;
            case 50200073:
                return MapleInventoryType.ETC;
            default:
                return MapleInventoryType.UNDEFINED;
        }
    }

    public static final void doCSPackets(MapleClient c) {
        c.getSession().write(CSPacket.getCSInventory(c));
        c.getSession().write(CSPacket.showNXMapleTokens(c.getPlayer()));
        c.getPlayer().getCashInventory().checkExpire(c);
        c.getSession().write(CSPacket.enableCSUse());
    }
}
