package handling.channel.handler;

import client.MapleCharacter;
import client.MapleClient;
import client.inventory.Item;
import client.inventory.ItemFlag;
import client.inventory.MapleInventory;
import client.inventory.MapleInventoryType;
import constants.GameConstants;
import constants.ServerConstants;
import handling.world.World;
import java.util.Arrays;
import server.MapleInventoryManipulator;
import server.MapleItemInformationProvider;
import server.MapleTrade;
import server.maps.FieldLimitType;
import server.maps.MapleMapObject;
import server.maps.MapleMapObjectType;
import server.shops.HiredMerchant;
import server.shops.IMaplePlayerShop;
import server.shops.MapleMiniGame;
import server.shops.MaplePlayerShop;
import server.shops.MaplePlayerShopItem;
import tools.FileoutputUtil;
import tools.packet.MaplePacketCreator;
import tools.Pair;
import tools.Randomizer;
import tools.StringUtil;
import tools.data.LittleEndianAccessor;
import tools.packet.PlayerShopPacket;

public class PlayerInteractionHandler {

    private enum Interaction {
        CREATE(0),
        INVITE_TRADE(0x2),
        DENY_TRADE(0x03),
        VISIT(0x04),
        CHAT(0x06),
        EXIT(0x0A),
        OPEN(0x0B),
        MAINTAIN(0x0D),
        SET_ITEMS(0x0E),
        SET_MESO(0x0F),
        CONFIRM_TRADE(0x10),
        PLAYER_SHOP_ADD_ITEM(0x14),
        BUY_ITEM_PLAYER_SHOP(0x15),
        REMOVE_ITEM_PS(0x19),
        KICK_Player(0x1A),
        ADD_ITEM(0x1F),
        BUY_ITEM_STORE(0x20),
        BUY_ITEM_HIREDMERCHANT(0x22),
        REMOVE_ITEM(0x24),
        MAINTANCE_OFF(0x25), //This is misspelled...
        MAINTANCE_ORGANISE(0x26),
        CLOSE_MERCHANT(0x27),
        ADMIN_STORE_NAMECHANGE(0x2B),
        VIEW_MERCHANT_VISITOR(0x2C),
        VIEW_MERCHANT_BLACKLIST(0x2D),
        MERCHANT_BLACKLIST_ADD(0x2E),
        MERCHANT_BLACKLIST_REMOVE(0x2F),
        REQUEST_TIE(0x30),
        ANSWER_TIE(0x31),
        GIVE_UP(0x32),
        REQUEST_REDO(0x34),
        ANSWER_REDO(0x35),
        EXIT_AFTER_GAME(0x36),
        CANCEL_EXIT(0x37),
        READY(0x38),
        UN_READY(0x39),
        EXPEL(0x3A),
        START(0x3B),
        SKIP(0x3D),
        MOVE_OMOK(0x3E),
        SELECT_CARD(0x42);

        public int action;

        private Interaction(int action) {
            this.action = action;
        }

        public static Interaction getByAction(int i) {
            for (Interaction s : Interaction.values()) {
                if (s.action == i) {
                    return s;
                }
            }
            return null;
        }
    }

    public static final void PlayerInteraction(final LittleEndianAccessor slea, final MapleClient c, final MapleCharacter chr) {
        //System.out.println(slea.toString());
        final Interaction action = Interaction.getByAction(slea.readByte());
        if (chr == null || action == null) {
            return;
        }
        if (chr.getAntiMacro().inProgress()) {
            chr.dropMessage(5, "被使用测谎仪时无法操作。");
            c.getSession().write(MaplePacketCreator.enableActions());
            c.getPlayer().marriage();
            return;
        }
        c.getPlayer().setScrolledPosition((short) 0);

        switch (action) { // Mode
            case KICK_Player: {
                final IMaplePlayerShop ips = chr.getPlayerShop();
                if (ips != null) {
                    ips.removeAllVisitors(5, 1); //no msg
                }
                break;
            }
            case CREATE: {
                if (chr.getPlayerShop() != null || c.getChannelServer().isShutdown() || chr.hasBlockedInventory()) {
                    c.getSession().write(MaplePacketCreator.enableActions());
                    c.getPlayer().marriage();
                    return;
                }
                final byte createType = slea.readByte();
                if (createType == 3) { // trade
                    MapleTrade.startTrade(chr);
                } else if (createType == 1 || createType == 2 || createType == 4 || createType == 5) { // shop
                    if (chr.getTrade() != null) {
                        chr.dropMessage(1, "目前状态无法操作。");
                        c.getSession().write(MaplePacketCreator.enableActions());
                        c.getPlayer().marriage();
                        return;
                    } else if (chr.getMap().getMapObjectsInRange(chr.getTruePosition(), 20000, Arrays.asList(MapleMapObjectType.SHOP, MapleMapObjectType.HIRED_MERCHANT)).size() != 0 || chr.getMap().getPortalsInRange(chr.getTruePosition(), 20000).size() != 0) {
                        chr.dropMessage(1, "此處无法建立商店或小游戏。");
                        c.getSession().write(MaplePacketCreator.enableActions());
                        c.getPlayer().marriage();
                        return;
                    } else if (createType == 1 || createType == 2) {
                        if (FieldLimitType.Minigames.check(chr.getMap().getFieldLimit()) || chr.getMap().allowPersonalShop()) {
                            chr.dropMessage(1, "此處无法使用小游戏。");
                            c.getSession().write(MaplePacketCreator.enableActions());
                            c.getPlayer().marriage();
                            return;
                        }
                    }
                    final String desc = slea.readMapleAsciiString();
                    String pass = "";
                    if (slea.readByte() > 0) {
                        pass = slea.readMapleAsciiString();
                    }
                    if (createType == 1 || createType == 2) {
                        final int piece = slea.readByte();
                        final int itemId = createType == 1 ? (4080000 + piece) : 4080100;

                        if (!chr.haveItem(itemId) || (c.getPlayer().getMapId() >= 910000001 && c.getPlayer().getMapId() <= 910000022)) {
                            return;
                        }
                        if (createType == 1 || createType == 2) {
                            chr.dropMessage(1, "目前状态无法操作。");
                            c.getSession().write(MaplePacketCreator.enableActions());
                            c.getPlayer().marriage();
                            return;
                        }
                        MapleMiniGame game = new MapleMiniGame(chr, itemId, desc, pass, createType); //itemid
                        game.setPieceType(piece);
                        chr.setPlayerShop(game);
                        game.setAvailable(true);
                        game.setOpen(true);
                        game.send(c);
                        chr.getMap().addMapObject(game);
                        game.update();
                    } else if (chr.getMap().allowPersonalShop()) {
                        Item shop = c.getPlayer().getInventory(MapleInventoryType.CASH).getItem((byte) slea.readShort());
                        if (shop == null || shop.getQuantity() <= 0 || shop.getItemId() != slea.readInt() || c.getPlayer().getMapId() < 910000001 || c.getPlayer().getMapId() > 910000022) {
                            return;
                        }
                        //for (int i = 5; i >= 1; i--) {
                       //     chr.dropMessage(1, "使用合約書请詳細閱讀\r\n由于精灵商人、个人商店BUG太多 请玩家使用時请抱著此種心態\r\n1.東西不想要了 2.沒錢拿也沒差\r\n因为很重要所以请閱讀" + i + "/5次");
                       // }
                        if (World.isShopShutDown) {
                            c.getPlayer().dropMessage(1, "服务器即將关闭所以不能使用商店。");
                            c.getSession().write(MaplePacketCreator.enableActions());
                            c.getPlayer().marriage();
                            return;
                        }
                        if (createType == 4) {
                            MaplePlayerShop mps = new MaplePlayerShop(chr, shop.getItemId(), desc);
                            chr.setPlayerShop(mps);
                            chr.getMap().addMapObject(mps);
                            c.getSession().write(PlayerShopPacket.getPlayerStore(chr, true));
                        } else if (HiredMerchantHandler.UseHiredMerchant(chr.getClient(), false)) {
                            final HiredMerchant merch = new HiredMerchant(chr, shop.getItemId(), desc);
                            chr.setPlayerShop(merch);
                            chr.getMap().addMapObject(merch);
                            c.getSession().write(PlayerShopPacket.getHiredMerch(chr, merch, true));
                        }
                    }
                }
                break;
            }
            case INVITE_TRADE: {
                if (chr.getMap() == null) {
                    return;
                }
                MapleCharacter chrr = chr.getMap().getCharacterById(slea.readInt());
                if (chrr == null || c.getChannelServer().isShutdown() || chrr.hasBlockedInventory()) {
                    c.getSession().write(MaplePacketCreator.enableActions());
                    c.getPlayer().marriage();
                    return;
                }
                MapleTrade.inviteTrade(chr, chrr);
                break;
            }
            case DENY_TRADE: {
                MapleTrade.declineTrade(chr);
                break;
            }
            case MAINTAIN: {
                slea.readInt();
                slea.readShort();
                if (c.getChannelServer().isShutdown()) {
                    c.getSession().write(MaplePacketCreator.enableActions());
                    c.getPlayer().marriage();
                    return;
                }
                if (World.isShopShutDown) {
                    chr.dropMessage(1, "服务器即將关闭所以不能使用商店.");
                    c.getSession().write(MaplePacketCreator.enableActions());
                    c.getPlayer().marriage();
                    return;
                }
                if (chr.getTrade() != null && chr.getTrade().getPartner() != null && !chr.getTrade().inTrade()) {
                    MapleTrade.visitTrade(chr, chr.getTrade().getPartner().getChr());
                } else if (chr.getMap() != null && chr.getTrade() == null) {
                    final int obid = slea.readInt();
                    MapleMapObject ob = chr.getMap().getMapObject(obid, MapleMapObjectType.HIRED_MERCHANT);
                    if (ob == null) {
                        ob = chr.getMap().getMapObject(obid, MapleMapObjectType.SHOP);
                    }

                    if (ob instanceof IMaplePlayerShop && chr.getPlayerShop() == null) {
                        final IMaplePlayerShop ips = (IMaplePlayerShop) ob;

                        if (ob instanceof HiredMerchant) {
                            final HiredMerchant merchant = (HiredMerchant) ips;
                            if (merchant.isOwner(chr) && merchant.isOpen() && merchant.isAvailable()) {
                                merchant.setOpen(false);
                                merchant.removeAllVisitors((byte) 18, (byte) 1);
                                chr.setPlayerShop(ips);
                                c.getSession().write(PlayerShopPacket.getHiredMerch(chr, merchant, false));
                            } else if (!merchant.isOpen() || !merchant.isAvailable()) {
                                chr.dropMessage(1, "这个商店在整理或者是沒在販賣東西。");
                            } else if (ips.getFreeSlot() == -1) {
                                chr.dropMessage(1, "商店人數已经满了，请稍后再进入。");
                            } else if (merchant.isInBlackList(chr.getName())) {
                                chr.dropMessage(1, "被加入黑名单了，所以不能进入。");
                            } else {
                                chr.setPlayerShop(ips);
                                merchant.addVisitor(chr);
                                c.getSession().write(PlayerShopPacket.getHiredMerch(chr, merchant, false));
                            }
                        } else if (ips instanceof MaplePlayerShop && ((MaplePlayerShop) ips).isBanned(chr.getName())) {
                            chr.dropMessage(1, "被加入黑名单了，所以不能进入。");
                            return;
                        } else if (ips.getFreeSlot() < 0 || ips.getVisitorSlot(chr) > -1 || !ips.isOpen() || !ips.isAvailable()) {
                            c.getSession().write(PlayerShopPacket.getMiniGameFull());
                        } else {
                            /*if (slea.available() > 0 && slea.readByte() > 0) { //a password has been entered
                                String pass = slea.readMapleAsciiString();
                                if (!pass.equals(ips.getPassword())) {
                                    c.getPlayer().dropMessage(1, "你输入的密码错误,请重新再試一次.");
                                    return;
                                }
                            } else if (ips.getPassword().length() > 0) {
                                c.getPlayer().dropMessage(1, "你输入的密码错误,请重新再試一次.");
                                return;
                            }*/
                            chr.setPlayerShop(ips);
                            ips.addVisitor(chr);
                            if (ips instanceof MapleMiniGame) {
                                ((MapleMiniGame) ips).send(c);
                            } else {
                                c.getSession().write(PlayerShopPacket.getPlayerStore(chr, false));
                            }
                        }
                    }
                }
                break;
            }
            case VISIT: {
                if (c.getChannelServer().isShutdown()) {
                    c.getSession().write(MaplePacketCreator.enableActions());
                    c.getPlayer().marriage();
                    return;
                }
                if (World.isShopShutDown) {
                    chr.dropMessage(1, "服务器即將关闭所以不能使用商店.");
                    c.getSession().write(MaplePacketCreator.enableActions());
                    c.getPlayer().marriage();
                    return;
                }
                if (chr.getTrade() != null && chr.getTrade().getPartner() != null && !chr.getTrade().inTrade()) {
                    MapleTrade.visitTrade(chr, chr.getTrade().getPartner().getChr());
                } else if (chr.getMap() != null && chr.getTrade() == null) {
                    final int obid = slea.readInt();
                    MapleMapObject ob = chr.getMap().getMapObject(obid, MapleMapObjectType.HIRED_MERCHANT);
                    if (ob == null) {
                        ob = chr.getMap().getMapObject(obid, MapleMapObjectType.SHOP);
                    }

                    if (ob instanceof IMaplePlayerShop && chr.getPlayerShop() == null) {
                        final IMaplePlayerShop ips = (IMaplePlayerShop) ob;

                        if (ob instanceof HiredMerchant) {
                            final HiredMerchant merchant = (HiredMerchant) ips;
                            if (merchant.isOwner(chr) && merchant.isOpen() && merchant.isAvailable()) {
                                merchant.setOpen(false);
                                merchant.removeAllVisitors((byte) 18, (byte) 1);
                                chr.setPlayerShop(ips);
                                c.getSession().write(PlayerShopPacket.getHiredMerch(chr, merchant, false));
                            } else if (!merchant.isOpen() || !merchant.isAvailable()) {
                                chr.dropMessage(1, "这个商店在整理或者是沒在販賣東西。");
                            } else if (ips.getFreeSlot() == -1) {
                                chr.dropMessage(1, "商店人數已经满了，请稍后再进入。");
                            } else if (merchant.isInBlackList(chr.getName())) {
                                chr.dropMessage(1, "被加入黑名单了，所以不能进入。");
                            } else {
                                chr.setPlayerShop(ips);
                                merchant.addVisitor(chr);
                                c.getSession().write(PlayerShopPacket.getHiredMerch(chr, merchant, false));
                            }
                        } else if (ips instanceof MaplePlayerShop && ((MaplePlayerShop) ips).isBanned(chr.getName())) {
                            chr.dropMessage(1, "被加入黑名单了，所以不能进入。");
                            return;
                        } else if (ips.getFreeSlot() < 0 || ips.getVisitorSlot(chr) > -1 || !ips.isOpen() || !ips.isAvailable()) {
                            c.getSession().write(PlayerShopPacket.getMiniGameFull());
                        } else {
                            /*if (slea.available() > 0 && slea.readByte() > 0) { //a password has been entered
                                String pass = slea.readMapleAsciiString();
                                if (!pass.equals(ips.getPassword())) {
                                    c.getPlayer().dropMessage(1, "你输入的密码错误,请重新再試一次.");
                                    return;
                                }
                            } else if (ips.getPassword().length() > 0) {
                                c.getPlayer().dropMessage(1, "你输入的密码错误,请重新再試一次.");
                                return;
                            }*/
                            chr.setPlayerShop(ips);
                            ips.addVisitor(chr);
                            if (ips instanceof MapleMiniGame) {
                                ((MapleMiniGame) ips).send(c);
                            } else {
                                c.getSession().write(PlayerShopPacket.getPlayerStore(chr, false));
                            }
                        }
                    }
                }
                break;
            }
            case CHAT: {
                //chr.updateTick(slea.readInt());
                slea.readInt();
                final String message = slea.readMapleAsciiString();
                if (StringUtil.countCharacters(message) == 1) {
                    return;
                }
                if (chr.getTrade() != null) {
                    chr.getTrade().chat(message);
                } else if (chr.getPlayerShop() != null) {
                    final IMaplePlayerShop ips = chr.getPlayerShop();
                    ips.getMessages().add(new Pair<>(chr.getName() + " : " + message, ips.getVisitorSlot(chr)));
                    ips.broadcastToVisitors(PlayerShopPacket.shopChat(chr.getName() + " : " + message, ips.getVisitorSlot(chr)));
                    if (chr.getClient().isMonitored()) { //Broadcast info even if it was a command.
                        World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, chr.getName() + " said in " + ips.getOwnerName() + " shop : " + message));
                    }
                }
                break;
            }
            case EXIT: {
                if (chr.getTrade() != null) {
                    // MapleTrade.cancelTrade(chr.getTrade(), chr.getClient(), chr);
                    if (chr.getTrade().getPartner() != null) {
                        final MapleTrade local = c.getPlayer().getTrade();
                        final MapleTrade partners = local.getPartner();
                        if (local.isLocked() && partners.isLocked()) {
                            c.getSession().write(MaplePacketCreator.enableActions());
                            c.getPlayer().marriage();
                            //chr.dropMessage(5, "雙方确认交易状态下，无法取消交易。");
                        } else {
                            MapleTrade.cancelTrade(chr.getTrade(), chr.getClient(), chr);
                        }
                    } else {
                        MapleTrade.cancelTrade(chr.getTrade(), chr.getClient(), chr);
                    }
                } else {
                    final IMaplePlayerShop ips = chr.getPlayerShop();
                    if (ips == null) { //should be null anyway for owners of hired merchants (maintenance_off)
                        return;
                    }
                    if (ips.isOwner(chr) && ips.getShopType() != 1) {
                        ips.closeShop(false, ips.isAvailable()); //how to return the items?
                    } else {
                        ips.removeVisitor(chr);
                    }
                    chr.setPlayerShop(null);
                }
                break;
            }
            case OPEN: {
                // c.getPlayer().haveItem(mode, 1, false, true)
                final IMaplePlayerShop shop = chr.getPlayerShop();
                if (shop != null && shop.isOwner(chr) && shop.getShopType() < 3 && !shop.isAvailable()) {
                    if (chr.getMap().allowPersonalShop()) {
                        if (c.getChannelServer().isShutdown()) {
                            chr.dropMessage(1, "服务器即將关闭所以不能使用商店.");
                            c.getSession().write(MaplePacketCreator.enableActions());
                            c.getPlayer().marriage();
                            shop.closeShop(shop.getShopType() == 1, false);
                            return;
                        }
                        if (World.isShopShutDown) {
                            chr.dropMessage(1, "服务器即將关闭所以不能使用商店.");
                            c.getSession().write(MaplePacketCreator.enableActions());
                            c.getPlayer().marriage();
                            shop.closeShop(shop.getShopType() == 1, false);
                            return;
                        }

                        if (shop.getShopType() == 1 && HiredMerchantHandler.UseHiredMerchant(chr.getClient(), false)) {
                            final HiredMerchant merchant = (HiredMerchant) shop;
                            merchant.setStoreid(c.getChannelServer().addMerchant(merchant));
                            merchant.setOpen(true);
                            merchant.setAvailable(true);
                            chr.getMap().broadcastMessage(PlayerShopPacket.spawnHiredMerchant(merchant));
                            chr.setPlayerShop(null);

                        } else if (shop.getShopType() == 2) {
                            shop.setOpen(true);
                            shop.setAvailable(true);
                            shop.update();
                        }
                    } else {
                        c.getSession().close();
                    }
                }

                break;
            }
            case SET_ITEMS: {
                final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
                final MapleInventoryType ivType = MapleInventoryType.getByType(slea.readByte());
                final Item item = chr.getInventory(ivType).getItem((byte) slea.readShort());
                final short quantity = slea.readShort();
                final byte targetSlot = slea.readByte();

                if (chr.getTrade() != null && item != null) {
                    if ((quantity <= item.getQuantity() && quantity >= 0) || GameConstants.isThrowingStar(item.getItemId()) || GameConstants.isBullet(item.getItemId())) {
                        chr.getTrade().setItems(c, item, targetSlot, quantity);
                    }
                }
                break;
            }
            case SET_MESO: {
                final MapleTrade trade = chr.getTrade();
                if (trade != null) {
                    trade.setMeso(slea.readInt());
                }
                break;
            }
            case PLAYER_SHOP_ADD_ITEM:
            case ADD_ITEM: {
                final MapleInventoryType type = MapleInventoryType.getByType(slea.readByte());
                final byte slot = (byte) slea.readShort();
                final short bundles = slea.readShort(); // How many in a bundle
                final short perBundle = slea.readShort(); // Price per bundle
                final int price = slea.readInt();

                if (price <= 0 || bundles <= 0 || perBundle <= 0) {
                    return;
                }
                final IMaplePlayerShop shop = chr.getPlayerShop();

                if (shop == null || !shop.isOwner(chr) || shop instanceof MapleMiniGame) {
                    return;
                }
                final Item ivItem = chr.getInventory(type).getItem(slot);
                final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
                if (ivItem != null) {
                    long check = bundles * perBundle;
                    if (check > 32767 || check <= 0) { //This is the better way to check.
                        return;
                    }
                    final short bundles_perbundle = (short) (bundles * perBundle);
//                    if (bundles_perbundle < 0) { // int_16 overflow
//                        return;
//                    }
                    if (GameConstants.isThrowingStar(ivItem.getItemId()) || GameConstants.isBullet(ivItem.getItemId())) {
                        if (ivItem.getQuantity() < 1) {
                            c.getSession().write(MaplePacketCreator.enableActions());
                            c.getPlayer().marriage();
                            return;
                        }
                    }
                    if (ivItem.getQuantity() >= bundles_perbundle) {
                        final short flag = ivItem.getFlag();
                        if (ItemFlag.UNTRADEABLE.check(flag) || ItemFlag.LOCK.check(flag)) {
                            c.getSession().write(MaplePacketCreator.enableActions());
                            c.getPlayer().marriage();
                            return;
                        }
                        if (ii.isDropRestricted(ivItem.getItemId()) || ii.isAccountShared(ivItem.getItemId())) {
                            if (!(ItemFlag.KARMA_EQ.check(flag) || ItemFlag.KARMA_USE.check(flag))) {
                                c.getSession().write(MaplePacketCreator.enableActions());
                                c.getPlayer().marriage();
                                return;
                            }
                        }
                        if (bundles_perbundle >= 50 && ivItem.getItemId() == 2340000) {
                            c.setMonitored(true); //hack check
                        }
                        //if (GameConstants.getLowestPrice(ivItem.getItemId()) > price) {
                        //    c.getPlayer().dropMessage(1, "这个道具能賣的最低價格是 " + GameConstants.getLowestPrice(ivItem.getItemId()));
                        //    c.getSession().write(MaplePacketCreator.enableActions());
                        //    return;
                        // }
                        if (GameConstants.isThrowingStar(ivItem.getItemId()) || GameConstants.isBullet(ivItem.getItemId())) {
                            // Ignore the bundles
                            MapleInventoryManipulator.removeFromSlot(c, type, slot, ivItem.getQuantity(), true);

                            final Item sellItem = ivItem.copy();
                            shop.addItem(new MaplePlayerShopItem(sellItem, (short) 1, price));
                            if (ServerConstants.logs_mrechant) {
                                FileoutputUtil.logToFile("logs/Data/精灵商人放入道具.txt", FileoutputUtil.NowTime() + " 账号: " + c.getAccountName() + " 玩家: " + c.getPlayer().getName() + " 使用精灵商人放入道具 " + " 道具： " + ivItem.getItemId() + " 數量:  " + bundles + "\r\n");
                            }
                            if (ServerConstants.message_mrechant) {
                                World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM聊天] " + " 账号: " + c.getAccountName() + " 玩家: " + c.getPlayer().getName() + " 使用精灵商人放入道具 " + " 道具： " + ivItem.getItemId() + " 數量:  " + bundles));
                            }
                        } else {
                            MapleInventoryManipulator.removeFromSlot(c, type, slot, bundles_perbundle, true);

                            final Item sellItem = ivItem.copy();
                            sellItem.setQuantity(perBundle);
                            shop.addItem(new MaplePlayerShopItem(sellItem, bundles, price));
                            if (ServerConstants.logs_mrechant) {
                                FileoutputUtil.logToFile("logs/Data/精灵商人放入道具.txt", FileoutputUtil.NowTime() + " 账号: " + c.getAccountName() + " 玩家: " + c.getPlayer().getName() + " 使用精灵商人放入道具 " + " 道具： " + ivItem.getItemId() + " 數量:  " + bundles + "\r\n");
                            }
                            if (ServerConstants.message_mrechant) {
                                World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM聊天] " + " 账号: " + c.getAccountName() + " 玩家: " + c.getPlayer().getName() + " 使用精灵商人放入道具 " + " 道具： " + ivItem.getItemId() + " 數量:  " + bundles));
                            }
                        }
                        c.getSession().write(PlayerShopPacket.shopItemUpdate(shop));
                    }
                }
                break;
            }
            case CONFIRM_TRADE:
            case BUY_ITEM_PLAYER_SHOP:
            case BUY_ITEM_STORE:
            case BUY_ITEM_HIREDMERCHANT: { // Buy and Merchant buy
                if (chr.getTrade() != null) {
                    MapleTrade.completeTrade(chr);
                    break;
                }
                final int item = slea.readByte();
                final short quantity = slea.readShort();
                //slea.skip(4);
                final IMaplePlayerShop shop = chr.getPlayerShop();

                if (shop == null || shop.isOwner(chr) || shop instanceof MapleMiniGame || item >= shop.getItems().size()) {
                    return;
                }
                final MaplePlayerShopItem tobuy = shop.getItems().get(item);
                if (tobuy == null) {
                    return;
                }
                long check = tobuy.bundles * quantity;
                long check2 = tobuy.price * quantity;
                long check3 = tobuy.item.getQuantity() * quantity;
                if (check <= 0 || check2 > 2147483647 || check2 <= 0 || check3 > 32767 || check3 < 0) { //This is the better way to check.
                    return;
                }
                if (tobuy.bundles < quantity || (tobuy.bundles % quantity != 0 && GameConstants.isEquip(tobuy.item.getItemId())) // Buying
                        || chr.getMeso() - (check2) < 0 || chr.getMeso() - (check2) > 2147483647 || shop.getMeso() + (check2) < 0 || shop.getMeso() + (check2) > 2147483647) {
                    c.getPlayer().dropMessage(1, "您购买的商店營业額已经超標，请通知店主來收錢。");
                    c.getSession().write(MaplePacketCreator.enableActions());
                    c.getPlayer().marriage();
                    return;
                }
                if (quantity >= 50 && tobuy.item.getItemId() == 2340000) {
                    c.setMonitored(true); //hack check
                }
                shop.buy(c, item, quantity);
                shop.broadcastToVisitors(PlayerShopPacket.shopItemUpdate(shop));
                break;
            }
            case REMOVE_ITEM_PS:
            case REMOVE_ITEM: {
                int slot = slea.readShort(); //0
                final IMaplePlayerShop shop = chr.getPlayerShop();

                if (shop == null || !shop.isOwner(chr) || shop instanceof MapleMiniGame || shop.getItems().size() <= 0 || shop.getItems().size() <= slot || slot < 0) {
                    return;
                }
                final MaplePlayerShopItem item = shop.getItems().get(slot);

                if (item != null) {
                    if (item.bundles > 0) {
                        Item item_get = item.item.copy();
                        long check = item.bundles * item.item.getQuantity();
                        if (check < 0 || check > 32767) {
                            return;
                        }
                        item_get.setQuantity((short) check);
                        if (item_get.getQuantity() >= 50 && item.item.getItemId() == 2340000) {
                            c.setMonitored(true); //hack check
                        }
                        if (MapleInventoryManipulator.checkSpace(c, item_get.getItemId(), item_get.getQuantity(), item_get.getOwner())) {
                            MapleInventoryManipulator.addFromDrop(c, item_get, false);
                            item.bundles = 0;
                            shop.removeFromSlot(slot);
                        }
                        if (ServerConstants.logs_mrechant) {
                            FileoutputUtil.logToFile("logs/Data/精灵商人道具拿出.txt", FileoutputUtil.NowTime() + " 账号: " + c.getAccountName() + " 玩家: " + c.getPlayer().getName() + " 精灵商人道具拿出 " + " 道具： " + item_get.getItemId() + " 數量:  " + item_get.getQuantity() + "\r\n");
                        }
                        if (ServerConstants.message_mrechant) {
                            World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM聊天] " + " 账号: " + c.getAccountName() + " 玩家: " + c.getPlayer().getName() + " 精灵商人道具拿出 " + " 道具： " + item_get.getItemId() + " 數量:  " + item_get.getQuantity()));
                        }
                    }
                }
                c.getSession().write(PlayerShopPacket.shopItemUpdate(shop));
                break;
            }
            case MAINTANCE_OFF: {
                final IMaplePlayerShop shop = chr.getPlayerShop();
                if (shop != null && shop instanceof HiredMerchant && shop.isOwner(chr) && shop.isAvailable()) {
                    shop.setOpen(true);
                    shop.removeAllVisitors(-1, -1);
                }
                break;
            }
            case MAINTANCE_ORGANISE: {
                final IMaplePlayerShop imps = chr.getPlayerShop();
                if (chr.getMeso() + imps.getMeso() < 0) {
                    c.getPlayer().dropMessage(1, "妳背包裏的金币已满。");
                    c.getSession().write(MaplePacketCreator.enableActions());
                    c.getPlayer().marriage();
                    return;
                }
                if (chr.getMeso() + imps.getMeso() > 2147483647) {
                    c.getPlayer().dropMessage(1, "妳背包裏的金币已满。");
                    c.getSession().write(MaplePacketCreator.enableActions());
                    c.getPlayer().marriage();
                    return;
                }
                if (imps != null && imps.isOwner(chr) && !(imps instanceof MapleMiniGame)) {
                    for (int i = 0; i < imps.getItems().size(); i++) {
                        if (imps.getItems().get(i).bundles == 0) {
                            imps.getItems().remove(i);
                        }
                    }
                    if (chr.getMeso() + imps.getMeso() > 0) {
                        chr.gainMeso(imps.getMeso(), false);
                        imps.setMeso(0);
                    }
                    if (ServerConstants.logs_mrechant) {
                        FileoutputUtil.logToFile("logs/Data/精灵商人整理道具.txt", FileoutputUtil.NowTime() + " 账号: " + c.getAccountName() + " 玩家: " + c.getPlayer().getName() + "使用精灵商人整理道具" + " 获得金币： " + imps.getMeso() + "\r\n");
                    }
                    if (ServerConstants.message_mrechant) {
                        World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM聊天] " + " 账号: " + c.getAccountName() + " 玩家: " + c.getPlayer().getName() + "使用精灵商人整理道具" + " 获得金币： " + imps.getMeso()));
                    }
                    c.getSession().write(PlayerShopPacket.shopItemUpdate(imps));
                }
                break;
            }
            case CLOSE_MERCHANT: {
                final IMaplePlayerShop merchant = chr.getPlayerShop();
                if (merchant != null && merchant.getShopType() == 1 && merchant.isOwner(chr) && merchant.isAvailable()) {
                    c.getSession().write(PlayerShopPacket.shopErrorMessage(0x15, 0));// 关闭窗口
                    c.getSession().write(MaplePacketCreator.serverNotice(1, "请去找富蘭德里領取你的装备和枫币。"));
                    merchant.removeAllVisitors(-1, -1);
                    chr.setPlayerShop(null);
                    merchant.closeShop(true, true);
                    c.getSession().write(MaplePacketCreator.enableActions());
                    c.getPlayer().marriage();
                }
                break;
            }
            case ADMIN_STORE_NAMECHANGE: { // Changing store name, only Admin
                // 01 00 00 00
                break;
            }
            case VIEW_MERCHANT_VISITOR: {
                final IMaplePlayerShop merchant = chr.getPlayerShop();
                if (merchant != null && merchant.getShopType() == 1 && merchant.isOwner(chr)) {
                    ((HiredMerchant) merchant).sendVisitor(c);
                }
                break;
            }
            case VIEW_MERCHANT_BLACKLIST: {
                final IMaplePlayerShop merchant = chr.getPlayerShop();
                if (merchant != null && merchant.getShopType() == 1 && merchant.isOwner(chr)) {
                    ((HiredMerchant) merchant).sendBlackList(c);
                }
                break;
            }
            case MERCHANT_BLACKLIST_ADD: {
                final IMaplePlayerShop merchant = chr.getPlayerShop();
                if (merchant != null && merchant.getShopType() == 1 && merchant.isOwner(chr)) {
                    ((HiredMerchant) merchant).addBlackList(slea.readMapleAsciiString());
                }
                break;
            }
            case MERCHANT_BLACKLIST_REMOVE: {
                final IMaplePlayerShop merchant = chr.getPlayerShop();
                if (merchant != null && merchant.getShopType() == 1 && merchant.isOwner(chr)) {
                    ((HiredMerchant) merchant).removeBlackList(slea.readMapleAsciiString());
                }
                break;
            }
            case GIVE_UP: {
                final IMaplePlayerShop ips = chr.getPlayerShop();
                if (ips != null && ips instanceof MapleMiniGame) {
                    MapleMiniGame game = (MapleMiniGame) ips;
                    if (game.isOpen()) {
                        break;
                    }
                    game.broadcastToVisitors(PlayerShopPacket.getMiniGameResult(game, 0, game.getVisitorSlot(chr)));
                    game.nextLoser();
                    game.setOpen(true);
                    game.update();
                    game.checkExitAfterGame();
                }
                break;
            }
            case EXPEL: {
                final IMaplePlayerShop ips = chr.getPlayerShop();
                if (ips != null && ips instanceof MapleMiniGame) {
                    if (!((MapleMiniGame) ips).isOpen()) {
                        break;
                    }
                    ips.removeAllVisitors(5, 1); //no msg
                }
                break;
            }
            case READY:
            case UN_READY: {
                final IMaplePlayerShop ips = chr.getPlayerShop();
                if (ips != null && ips instanceof MapleMiniGame) {
                    MapleMiniGame game = (MapleMiniGame) ips;
                    if (!game.isOwner(chr) && game.isOpen()) {
                        game.setReady(game.getVisitorSlot(chr));
                        game.broadcastToVisitors(PlayerShopPacket.getMiniGameReady(game.isReady(game.getVisitorSlot(chr))));
                    }
                }
                break;
            }
            case START: {
                final IMaplePlayerShop ips = chr.getPlayerShop();
                if (ips != null && ips instanceof MapleMiniGame) {
                    MapleMiniGame game = (MapleMiniGame) ips;
                    if (game.isOwner(chr) && game.isOpen()) {
                        for (int i = 1; i < ips.getSize(); i++) {
                            if (!game.isReady(i)) {
                                return;
                            }
                        }
                        game.setGameType();
                        game.shuffleList();
                        if (game.getGameType() == 1) {
                            game.broadcastToVisitors(PlayerShopPacket.getMiniGameStart(game.getLoser()));
                        } else {
                            game.broadcastToVisitors(PlayerShopPacket.getMatchCardStart(game, game.getLoser()));
                        }
                        game.setOpen(false);
                        game.update();
                    }
                }
                break;
            }
            case REQUEST_TIE: {
                final IMaplePlayerShop ips = chr.getPlayerShop();
                if (ips != null && ips instanceof MapleMiniGame) {
                    MapleMiniGame game = (MapleMiniGame) ips;
                    if (game.isOpen()) {
                        break;
                    }
                    if (game.isOwner(chr)) {
                        game.broadcastToVisitors(PlayerShopPacket.getMiniGameRequestTie(), false);
                    } else {
                        game.getMCOwner().getClient().getSession().write(PlayerShopPacket.getMiniGameRequestTie());
                    }
                    game.setRequestedTie(game.getVisitorSlot(chr));
                }
                break;
            }
            case ANSWER_TIE: {
                final IMaplePlayerShop ips = chr.getPlayerShop();
                if (ips != null && ips instanceof MapleMiniGame) {
                    MapleMiniGame game = (MapleMiniGame) ips;
                    if (game.isOpen()) {
                        break;
                    }
                    if (game.getRequestedTie() > -1 && game.getRequestedTie() != game.getVisitorSlot(chr)) {
                        if (slea.readByte() > 0) {
                            game.broadcastToVisitors(PlayerShopPacket.getMiniGameResult(game, 1, game.getRequestedTie()));
                            game.nextLoser();
                            game.setOpen(true);
                            game.update();
                            game.checkExitAfterGame();
                        } else {
                            game.broadcastToVisitors(PlayerShopPacket.getMiniGameDenyTie());
                        }
                        game.setRequestedTie(-1);
                    }
                }
                break;
            }

            case REQUEST_REDO: {
                final IMaplePlayerShop ips = chr.getPlayerShop();
                if (ips != null && ips instanceof MapleMiniGame) {
                    MapleMiniGame game = (MapleMiniGame) ips;
                    if (game.isOpen()) {
                        break;
                    }
                    if (game.isOwner(chr)) {
                        game.broadcastToVisitors(PlayerShopPacket.getMiniGameRequestREDO(), false);
                    } else {
                        game.getMCOwner().getClient().getSession().write(PlayerShopPacket.getMiniGameRequestREDO());
                    }
                    game.setRequestedTie(game.getVisitorSlot(chr));
                }
                break;
            }

            case ANSWER_REDO: {
                final IMaplePlayerShop ips = chr.getPlayerShop();
                if (ips != null && ips instanceof MapleMiniGame) {
                    MapleMiniGame game = (MapleMiniGame) ips;
                    if (game.isOpen()) {
                        break;
                    }
                    //     if (game.getRequestedTie() > -1 && game.getRequestedTie() != game.getVisitorSlot(chr)) {
                    if (slea.readByte() > 0) {
                        ips.broadcastToVisitors(PlayerShopPacket.getMiniGameSkip1(ips.getVisitorSlot(chr)));
                        game.nextLoser();
                    } else {
                        game.broadcastToVisitors(PlayerShopPacket.getMiniGameDenyTie());
                    }
                    game.setRequestedTie(-1);
                }
                //}
                break;
            }
            case SKIP: {
                final IMaplePlayerShop ips = chr.getPlayerShop();
                if (ips != null && ips instanceof MapleMiniGame) {
                    MapleMiniGame game = (MapleMiniGame) ips;
                    if (game.isOpen()) {
                        break;
                    }
                    /*if (game.getLoser() != ips.getVisitorSlot(chr)) {
                        ips.broadcastToVisitors(PlayerShopPacket.shopChat("Turn could not be skipped by " + chr.getName() + ". Loser: " + game.getLoser() + " Visitor: " + ips.getVisitorSlot(chr), ips.getVisitorSlot(chr)));
                        return;
                    }*/
                    ips.broadcastToVisitors(PlayerShopPacket.getMiniGameSkip(ips.getVisitorSlot(chr)));
                    game.nextLoser();
                }
                break;
            }
            case MOVE_OMOK: {
                final IMaplePlayerShop ips = chr.getPlayerShop();
                if (ips != null && ips instanceof MapleMiniGame) {
                    MapleMiniGame game = (MapleMiniGame) ips;
                    if (game.isOpen()) {
                        break;
                    }
                    /*if (game.getLoser() != game.getVisitorSlot(chr)) {
                        game.broadcastToVisitors(PlayerShopPacket.shopChat("Omok could not be placed by " + chr.getName() + ". Loser: " + game.getLoser() + " Visitor: " + game.getVisitorSlot(chr), game.getVisitorSlot(chr)));
                        return;
                    }*/
                    game.setPiece(slea.readInt(), slea.readInt(), slea.readByte(), chr);
                }
                break;
            }
            case SELECT_CARD: {
                final IMaplePlayerShop ips = chr.getPlayerShop();
                if (ips != null && ips instanceof MapleMiniGame) {
                    MapleMiniGame game = (MapleMiniGame) ips;
                    if (game.isOpen()) {
                        break;
                    }
                    /*if (game.getLoser() != game.getVisitorSlot(chr)) {
                        game.broadcastToVisitors(PlayerShopPacket.shopChat("Card could not be placed by " + chr.getName() + ". Loser: " + game.getLoser() + " Visitor: " + game.getVisitorSlot(chr), game.getVisitorSlot(chr)));
                        return;
                    }*/
                    if (slea.readByte() != game.getTurn()) {
                        game.broadcastToVisitors(PlayerShopPacket.shopChat("不能放在通过 " + chr.getName() + ". 失敗者: " + game.getLoser() + " 遊客: " + game.getVisitorSlot(chr) + " 是否为真: " + game.getTurn(), game.getVisitorSlot(chr)));
                        return;
                    }
                    final int slot = slea.readByte();
                    final int turn = game.getTurn();
                    final int fs = game.getFirstSlot();
                    if (turn == 1) {
                        game.setFirstSlot(slot);
                        if (game.isOwner(chr)) {
                            game.broadcastToVisitors(PlayerShopPacket.getMatchCardSelect(turn, slot, fs, turn), false);
                        } else {
                            game.getMCOwner().getClient().getSession().write(PlayerShopPacket.getMatchCardSelect(turn, slot, fs, turn));
                        }
                        game.setTurn(0); //2nd turn nao
                        return;
                    } else if (fs > 0 && game.getCardId(fs + 1) == game.getCardId(slot + 1)) {
                        game.broadcastToVisitors(PlayerShopPacket.getMatchCardSelect(turn, slot, fs, game.isOwner(chr) ? 2 : 3));
                        game.setPoints(game.getVisitorSlot(chr)); //correct.. so still same loser. diff turn tho
                    } else {
                        game.broadcastToVisitors(PlayerShopPacket.getMatchCardSelect(turn, slot, fs, game.isOwner(chr) ? 0 : 1));
                        game.nextLoser();//wrong haha

                    }
                    game.setTurn(1);
                    game.setFirstSlot(0);

                }
                break;
            }
            case EXIT_AFTER_GAME:
            /*{
                final IMaplePlayerShop ips = chr.getPlayerShop();
                if (ips != null && ips instanceof MapleMiniGame) {
                    MapleMiniGame game = (MapleMiniGame) ips;
                    if (game.isOpen()) {
                        break;
                    }
                    game.broadcastToVisitors(PlayerShopPacket.getMiniGameResult(game, 0, game.getVisitorSlot(chr)));
                    game.nextLoser();
                    game.setOpen(true);
                    game.update();
                    game.checkExitAfterGame();
                }
                break;
            }*/
            case CANCEL_EXIT: {
                final IMaplePlayerShop ips = chr.getPlayerShop();
                if (ips != null && ips instanceof MapleMiniGame) {
                    MapleMiniGame game = (MapleMiniGame) ips;
                    if (game.isOpen()) {
                        break;
                    }
                    game.setExitAfter(chr);
                    game.broadcastToVisitors(PlayerShopPacket.getMiniGameExitAfter(game.isExitAfter(chr)));
                }
                break;
            }
            default: {
                break;
            }
        }
    }

    public static int generatePieceType(MapleCharacter chr) {
        // to randomly generate the type of matchcard because i don't think it reads the byte of the matchcard layout anymore
        int type = Randomizer.rand(0, 2); //0=4x3,1=5x4,2=6x5
        if (type != chr.getMatchCardVal()) {
            chr.setMatchCardVal(type); // update it for the character each time.. (yes i know this is sloppy) :/
            return type;
        }
        return 2;
    }
}
