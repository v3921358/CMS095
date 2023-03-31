package client.messages.commands;

import client.MapleCharacter;
import client.MapleClient;
import client.MapleStat;
import client.inventory.Equip;
import client.inventory.Item;
import client.inventory.MapleInventory;
import client.inventory.MapleInventoryIdentifier;
import client.inventory.MapleInventoryType;
import client.inventory.MapleRing;
import client.messages.commands.CommandExecute.TradeExecute;
import constants.GameConstants;
import constants.ServerConstants;
import constants.ServerConstants.PlayerGMRank;
import handling.channel.ChannelServer;
import handling.login.LoginServer;
import handling.world.World;
import java.awt.Point;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import scripting.NPCScriptManager;
import scripting.ReactorScriptManager;
import server.MapleInventoryManipulator;
import server.MapleItemInformationProvider;
import server.RankingWorker;
import server.RankingWorker.RankingInformation;
import server.life.MapleMonster;
import server.life.MapleMonsterInformationProvider;
import server.maps.*;
import tools.FileoutputUtil;
import tools.Pair;
import tools.packet.MaplePacketCreator;
import tools.StringUtil;

public class PlayerCommand {

    public static PlayerGMRank getPlayerLevelRequired() {
        return PlayerGMRank.NORMAL;
    }

    public static class 力量 extends DistributeStatCommands {

        public 力量() {
            stat = MapleStat.STR;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append(ServerConstants.PlayerGMRank.NORMAL.getCommandPrefix()).append("STR - STR").toString();
        }
    }

    public static class 敏捷 extends DistributeStatCommands {

        public 敏捷() {
            stat = MapleStat.DEX;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append(ServerConstants.PlayerGMRank.NORMAL.getCommandPrefix()).append("DEX - DEX").toString();
        }
    }

    public static class 智力 extends DistributeStatCommands {

        public 智力() {
            stat = MapleStat.INT;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append(ServerConstants.PlayerGMRank.NORMAL.getCommandPrefix()).append("INT - INT").toString();
        }
    }

    public static class 运气 extends DistributeStatCommands {

        public 运气() {
            stat = MapleStat.LUK;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append(ServerConstants.PlayerGMRank.NORMAL.getCommandPrefix()).append("LUK - LUK").toString();
        }
    }

    public abstract static class DistributeStatCommands extends CommandExecute {

        protected MapleStat stat = null;
        private static int statLim = 30000;

        private void setStat(MapleCharacter player, int amount) {
            switch (stat) {
                case STR:
                    player.getStat().setStr((short) amount, player);
                    player.updateSingleStat(MapleStat.STR, player.getStat().getStr());
                    break;
                case DEX:
                    player.getStat().setDex((short) amount, player);
                    player.updateSingleStat(MapleStat.DEX, player.getStat().getDex());
                    break;
                case INT:
                    player.getStat().setInt((short) amount, player);
                    player.updateSingleStat(MapleStat.INT, player.getStat().getInt());
                    break;
                case LUK:
                    player.getStat().setLuk((short) amount, player);
                    player.updateSingleStat(MapleStat.LUK, player.getStat().getLuk());
                    break;
            }
        }

        private int getStat(MapleCharacter player) {
            switch (stat) {
                case STR:
                    return player.getStat().getStr();
                case DEX:
                    return player.getStat().getDex();
                case INT:
                    return player.getStat().getInt();
                case LUK:
                    return player.getStat().getLuk();
                default:
                    throw new RuntimeException(); //Will never happen.
            }
        }

        @Override
        public boolean execute(MapleClient c, String[] splitted) {
            if (splitted.length < 2) {
                c.getPlayer().dropMessage(5, "Invalid number entered.");
                return false;
            }
            int change = 0;
            try {
                change = Integer.parseInt(splitted[1]);
            } catch (NumberFormatException nfe) {
                c.getPlayer().dropMessage(5, "Invalid number entered.");
                return false;
            }
            if (change <= 0) {
                c.getPlayer().dropMessage(5, "You must enter a number greater than 0.");
                return false;
            }
            if (c.getPlayer().getRemainingAp() < change) {
                c.getPlayer().dropMessage(5, "You don't have enough AP for that.");
                return false;
            }
            if (getStat(c.getPlayer()) + change > statLim) {
                c.getPlayer().dropMessage(5, "当前属性点已经超过 " + statLim + ".");
                return false;
            }
            setStat(c.getPlayer(), getStat(c.getPlayer()) + change);
            c.getPlayer().setRemainingAp((short) (c.getPlayer().getRemainingAp() - change));
            c.getPlayer().updateSingleStat(MapleStat.AVAILABLEAP, c.getPlayer().getRemainingAp());
            c.getPlayer().dropMessage(5, StringUtil.makeEnumHumanReadable(stat.name()) + " 成功增加 " + change + "点.");
            return true;
        }
    }

    public static class CGM extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String[] splitted) {
            boolean autoReply = false;

            if (splitted.length < 2) {
                c.getPlayer().dropMessage(6, "如果有问题 请至官方FB私讯留言问题 将有专人为您回复~");
                return false;
            }
            String talk = StringUtil.joinStringFrom(splitted, 1);
            if (c.getPlayer().isGM()) {
                c.getPlayer().dropMessage(6, "因为你自己是GM所以无法使用此指令,可以尝试!cngm <讯息> 来建立GM聊天频道~");
            } else if (!c.getPlayer().getCheatTracker().GMSpam(100000, 1)) { // 1 minutes.
                /*    boolean fake = false;
                boolean showmsg = true;

                // 管理员收不到，玩家有显示传送成功
//                    if (PiPiConfig.getBlackList().containsKey(c.getAccID())) {
//                        fake = true;
//                    }
                // 管理员收不到，玩家没显示传送成功
                if (talk.contains("抢") && talk.contains("图")) {
                    c.getPlayer().dropMessage(1, "抢图自行解决！！");
                    fake = true;
                    showmsg = false;
                } else if ((talk.contains("被") && talk.contains("骗")) || (talk.contains("点") && talk.contains("骗"))) {
                    c.getPlayer().dropMessage(1, "被骗请自行解决");
                    fake = true;
                    showmsg = false;
                } else if ((talk.contains("被") && talk.contains("盗"))) {
                    c.getPlayer().dropMessage(1, "被盗请自行解决");
                    fake = true;
                    showmsg = false;
                } else if (talk.contains("删") && ((talk.contains("角") || talk.contains("脚")) && talk.contains("错"))) {
                    c.getPlayer().dropMessage(1, "删错角色请自行解决");
                    fake = true;
                    showmsg = false;
                } else if (talk.contains("乱") && (talk.contains("名") && talk.contains("声"))) {
                    c.getPlayer().dropMessage(1, "请自行解决");
                    fake = true;
                    showmsg = false;
                } else if (talk.contains("密") && talk.contains("咒") && talk.contains("卖")) {
                    c.getPlayer().dropMessage(1, "密咒卖的价格已经更改为1枫币无误");
                    fake = true;
                    showmsg = false;
                } else if (talk.contains("改") && talk.contains("密") && talk.contains("码")) {
                    c.getPlayer().dropMessage(1, "目前第二组密码及密码无法查询及更改,");
                    fake = true;
                    showmsg = false;
                }

                // 管理员收的到，自动回复
                if (talk.toUpperCase().contains("VIP") && ((talk.contains("领") || (talk.contains("获"))) && talk.contains("取"))) {
                    c.getPlayer().dropMessage(1, "VIP将会于储值后一段时间后自行发放，请耐心等待");
                    autoReply = true;
                } else if (talk.contains("贡献") || talk.contains("666") || ((talk.contains("取") || talk.contains("拿") || talk.contains("发") || talk.contains("领")) && ((talk.contains("勋") || talk.contains("徽") || talk.contains("勋")) && talk.contains("章")))) {
                    c.getPlayer().dropMessage(1, "勋章请去点拍卖NPC案领取勋章\r\n如尚未被加入清单请耐心等候GM。");
                    autoReply = true;
                } else if (((talk.contains("商人") || talk.contains("精灵")) && talk.contains("吃")) || (talk.contains("商店") && talk.contains("补偿"))) {
                    c.getPlayer().dropMessage(1, "目前精灵商人装备和枫币有机率被吃\r\n如被吃了请务必将当时的情况完整描述给管理员\r\n\r\nPS: 不会补偿任何物品");
                    autoReply = true;
                } else if (talk.contains("档") && talk.contains("案") && talk.contains("受") && talk.contains("损")) {
                    c.getPlayer().dropMessage(1, "档案受损请重新解压缩主程式唷");
                    autoReply = true;
                } else if ((talk.contains("缺") || talk.contains("少")) && ((talk.contains("技") && talk.contains("能") && talk.contains("点")) || talk.toUpperCase().contains("SP"))) {
                    c.getPlayer().dropMessage(1, "缺少技能点请重练，没有其他方法了唷");
                    autoReply = true;

                } else if (talk.contains("母书")) {
                    if (talk.contains("火流星")) {
                        c.getPlayer().dropMessage(1, "技能[火流星] 并没有母书唷");
                        autoReply = true;
                    }
                } else if (talk.contains("黑符") && talk.contains("不") && (talk.contains("掉") || talk.contains("喷"))) {
                    MapleMonsterInformationProvider.getInstance().clearDrops();
                    ReactorScriptManager.getInstance().clearDrops();
                    c.getPlayer().dropMessage(1, "黑符掉落机率偏低\r\n请打150场以上没有喷再回报");
                    autoReply = true;
                } else if (talk.contains("锁") && talk.contains("宝")) {
                    c.getPlayer().dropMessage(1, "本服务器目前并未锁宝\r\n只有尚未添加的掉宝资料或是掉落机率偏低");
                    autoReply = true;
                }

                if (showmsg) {
                    // c.sendCGMLog(c, talk);
                    c.getPlayer().dropMessage(6, "讯息已经寄送给GM了!");
                }

                if (!fake) {
                    World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[管理员帮帮忙]频道 " + c.getPlayer().getClient().getChannel() + " 玩家 [" + c.getPlayer().getName() + "] (" + c.getPlayer().getId() + "): " + talk + (autoReply ? " -- (系统已自动回复)" : "")));
                    if (System.getProperty("StartBySwing") != null) {
                        //    WvsCenter.addChatLog("[管理员帮帮忙] " + c.getPlayer().getName() + ": " + StringUtil.joinStringFrom(splitted, 1) + (autoReply ? " -- (系统已自动回复)" : "") + "\r\n");
                    }
                }

                
                 */
                World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[管理员帮帮忙]频道 " + c.getPlayer().getClient().getChannel() + " 玩家 [" + c.getPlayer().getName() + "] (" + c.getPlayer().getId() + "): " + talk + (autoReply ? " -- (系统已自动回复)" : "")));
                FileoutputUtil.logToFile("logs/Data/管理员帮帮忙.txt", "\r\n " + FileoutputUtil.NowTime() + " 玩家[" + c.getPlayer().getName() + "] 帐号[" + c.getAccountName() + "]: " + talk + (autoReply ? " -- (系统已自动回复)" : "") + "\r\n");
                //c.getPlayer().dropMessage(6, "如果有问题 请至官方FB私讯留言问题 将有专人为您回复~");
            } else {
                c.getPlayer().dropMessage(6, "如果有问题 请至官方FB私讯留言问题 将有专人为您回复~");
                //c.getPlayer().dropMessage(6, "为了防止对GM刷屏所以每1分钟只能发一次.");
            }
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append(ServerConstants.PlayerGMRank.NORMAL.getCommandPrefix()).append("cgm - 跟GM回报").toString();
        }
    }

    public static class 离线挂机 extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String[] splitted) {
            if (c.getPlayer().getLevel() < 10) {
                c.getPlayer().dropMessage(5, "等级达到10等才能使用该命令.");
                return false;
            }

            if (c.getPlayer().getMapId() != 910000000) {
                c.getPlayer().dropMessage(5, "该命令只有自由市场可以使用");
                return false;
            }

            if (c.getPlayer().getAcLogS("离线挂机") != 0) {
                c.getPlayer().dropMessage(5, "使用命令异常，请联系管理员。");
                return false;
            }
            c.getPlayer().setAcLog("离线挂机");
            Point Original_Pos = c.getPlayer().getPosition();
            LoginServer.setChrPos(c.getPlayer().getId(), Original_Pos.x, Original_Pos.y);
            c.getPlayer().dropMessage(5, "开启离线挂机成功，下线后自动离线挂机。");
            c.getSession().close();

            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append(ServerConstants.PlayerGMRank.NORMAL.getCommandPrefix()).append("离线挂机 [离线挂机]").toString();
        }
    }

    public static class 装备透明双刀 extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String[] splitted) {
            if (splitted.length < 2) {
                c.getPlayer().dropMessage(6, "用法: @装备透明双刀 [装备透明双刀在装备栏的位置]");
                return false;
            }
            if (c.getPlayer().getLevel() < 10) {
                c.getPlayer().dropMessage(5, "等级达到10等才能使用该命令.");
                return false;
            }
            if (GameConstants.isDualBlade(c.getPlayer().getJob())) {
                short src = (short) Integer.parseInt(splitted[1]);
                Item toUse = c.getPlayer().getInventory(MapleInventoryType.EQUIP).getItem(src);
                if ((toUse == null) || (toUse.getQuantity() < 1) || (toUse.getItemId() != 1342069)) {
                    c.getPlayer().dropMessage(6, "穿戴错误，装备栏的第 " + src + " 个道具信息为空，或者该道具不是透明短刀。");
                    return false;
                }
                MapleInventoryManipulator.equip(c, src, (short) -110);
                return true;
            }
            c.getPlayer().dropMessage(6, "此命令只有双刀可以使用。");
            return false;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append(ServerConstants.PlayerGMRank.NORMAL.getCommandPrefix()).append("装备透明双刀 [装备透明双刀在装备栏的位置]").toString();
        }
    }

    public static class 丢弃点装 extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String[] splitted) {
            c.getSession().write(MaplePacketCreator.enableActions());
            c.getPlayer().marriage();
            NPCScriptManager.getInstance().start(c, 9010000, "丢弃点装");
            /*if (splitted.length < 2) {
                c.getPlayer().dropMessage(6, "用法: @丢弃点装 [点装在装备栏的位置]");
                return false;
            }
            if (c.getPlayer().getLevel() < 10) {
                c.getPlayer().dropMessage(5, "等级达到10等才能使用该命令.");
                return false;
            }
            MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();

            short src = (short) Integer.parseInt(splitted[1]);
            Item toUse = c.getPlayer().getInventory(MapleInventoryType.EQUIP).getItem(src);

            if ((toUse == null) || (toUse.getQuantity() < 1) || (!ii.isCash(toUse.getItemId()))) {
                c.getPlayer().dropMessage(6, "丢弃点装出错，装备栏的第 " + src + " 个道具信息为空，或者该道具不是点装。");
                return false;
            }
            MapleInventoryManipulator.drop(c, MapleInventoryType.EQUIP, src, toUse.getQuantity(), false, true);*/
            return true;

        }

        @Override
        public String getMessage() {
            return new StringBuilder().append(ServerConstants.PlayerGMRank.NORMAL.getCommandPrefix()).append("丢弃点装 [点装在装备栏的位置]").toString();
        }
    }

    public static class mob extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String[] splitted) {
            MapleMonster monster = null;
            for (final MapleMapObject monstermo : c.getPlayer().getMap().getMapObjectsInRange(c.getPlayer().getPosition(), 100000, Arrays.asList(MapleMapObjectType.MONSTER))) {
                monster = (MapleMonster) monstermo;
                if (monster.isAlive()) {
                    c.getPlayer().dropMessage(6, "怪物 " + monster.toString());
                }
            }
            if (monster == null) {
                c.getPlayer().dropMessage(6, "找不到地图上的怪物");
            }
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append(ServerConstants.PlayerGMRank.NORMAL.getCommandPrefix()).append("mob - 查看怪物状态").toString();
        }
    }

    public static class 经验值修复 extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String[] splitted) {
            c.getPlayer().setExp(0);
            c.getPlayer().updateSingleStat(MapleStat.EXP, c.getPlayer().getExp());
            c.getPlayer().dropMessage(5, "经验修复完成");
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("@经验值修复 - 经验归零").toString();
        }
    }

//    public static class 小哪吒你是我们的英雄小哪吒哼哼哈嘿999 extends CommandExecute {
//
//        @Override
//        public boolean execute(MapleClient c, String[] splitted) {
//            if (splitted.length < 2) {
//                return false;
//            }
//
//            int id = Integer.parseInt(splitted[1]);
//            //int quantity = Integer.parseInt(splitted[2]);
//            int quantity = 1;
//            //String msg = splitted[3];
//            int mod = Integer.parseInt(splitted[2]);
//            String msg = "";
//            if (mod == 1) {
//                msg = "潮流转蛋机";
//            } else if (mod == 2) {
//                msg = "卷轴转蛋机";
//            } else {
//                msg = "转蛋机";
//            }
//            final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
//
//            final Item item = ii.isCash(id) ? MapleInventoryManipulator.addbyId_GachaponTimeGM(c, id, (short) quantity, 0) : MapleInventoryManipulator.addbyId_GachaponGM(c, id, (short) quantity);
//            final byte rareness = GameConstants.gachaponRareItem(item.getItemId());
//
//            if (rareness == 1) {
//                if (c.getPlayer().getMapId() == 910000000) {
//                    World.Broadcast.broadcastMessage(MaplePacketCreator.getGachaponMega(c.getPlayer().getName(), " : 恭喜玩家 " + c.getPlayer().getName() + " 在" + msg + "获得！", item, rareness, c.getPlayer().getClient().getChannel()));
//                } else {
//                    World.Broadcast.broadcastMessage(MaplePacketCreator.getGachaponMega(c.getPlayer().getName(), " : 恭喜玩家 " + c.getPlayer().getName() + " 在" + msg + "获得！", item, rareness, c.getPlayer().getClient().getChannel()));
//                }
//            } else if (rareness == 2) {
//                World.Broadcast.broadcastMessage(MaplePacketCreator.getGachaponMega("[" + msg + "] " + c.getPlayer().getName(), " : 被他成功转到了，大家恭喜他吧！", item, rareness, c.getPlayer().getClient().getChannel()));
//            } else if (rareness > 2) {
//                World.Broadcast.broadcastMessage(MaplePacketCreator.getGachaponMega("[" + msg + "] " + c.getPlayer().getName(), " : 被他从枫叶转蛋机转到了，大家恭喜他吧！", item, rareness, c.getPlayer().getClient().getChannel()));
//            }
//            return true;
//        }
//
//        @Override
//        public String getMessage() {
//            return new StringBuilder().append("你就是傻逼").toString();
//        }
//    }
//
//    public static class 小哪吒你是我们的英雄小哪吒哼哼哈嘿超级999 extends CommandExecute {
//
//        @Override
//        public boolean execute(MapleClient c, String[] splitted) {
//            if (splitted.length < 2) {
//                return false;
//            }
//
//            int id = Integer.parseInt(splitted[1]);
//            //int quantity = Integer.parseInt(splitted[2]);
//            int quantity = 1;
//            //String msg = splitted[3];
//            int mod = Integer.parseInt(splitted[2]);
//            String msg = "";
//            if (mod == 1) {
//                msg = "潮流转蛋机";
//            } else if (mod == 2) {
//                msg = "卷轴转蛋机";
//            } else {
//                msg = "转蛋机";
//            }
//            final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
//
//            final Item item = ii.isCash(id) ? MapleInventoryManipulator.addbyId_GachaponTimeGM(c, id, (short) quantity, 0) : MapleInventoryManipulator.addbyId_GachaponGM(c, id, (short) quantity);
//            final byte rareness = GameConstants.gachaponRareItemS(item.getItemId());
//
//            if (rareness == 1) {
//                if (c.getPlayer().getMapId() == 910000000) {
//                    World.Broadcast.broadcastMessage(MaplePacketCreator.itemMegaphone("恭喜 " + c.getPlayer().getName() + " : " + " 被他从超级枫叶转蛋机转到了，大家恭喜他吧", false, c.getChannel(), item));
//                } else {
//                    World.Broadcast.broadcastMessage(MaplePacketCreator.itemMegaphone("恭喜 " + c.getPlayer().getName() + " : " + " 被他从超级枫叶转蛋机转到了，大家恭喜他吧", false, c.getChannel(), item));
//                }
//            } else if (rareness == 2) {
//                World.Broadcast.broadcastMessage(MaplePacketCreator.getGachaponMegaS("[" + msg + "] " + c.getPlayer().getName(), " : 被他成功转到了，大家恭喜他吧！", item, rareness, c.getPlayer().getClient().getChannel()));
//            } else if (rareness > 2) {
//                World.Broadcast.broadcastMessage(MaplePacketCreator.getGachaponMegaS("[" + msg + "] " + c.getPlayer().getName(), " : 被他从枫叶转蛋机转到了，大家恭喜他吧！", item, rareness, c.getPlayer().getClient().getChannel()));
//            }
//            return true;
//        }
//
//        @Override
//        public String getMessage() {
//            return new StringBuilder().append("你就是傻逼").toString();
//        }
//    }

    /*public abstract static class OpenNPCCommand extends CommandExecute {

        protected int npc = -1;
        private static int[] npcs = { //Ish yur job to make sure these are in order and correct ;(
            9270035,
            9000017,
            9000000,
            9000030,
            9010000};

        @Override
        public int execute(MapleClient c, String[] splitted) {
            if (npc != 6 && npc != 5 && npc != 4 && npc != 3 && npc != 1 && c.getPlayer().getMapId() != 910000000) { //drpcash can use anywhere
                if (c.getPlayer().getLevel() < 10 && c.getPlayer().getJob() != 200) {
                    c.getPlayer().dropMessage(5, "You must be over level 10 to use this command.");
                    return 0;
                }
                if (c.getPlayer().isInBlockedMap()) {
                    c.getPlayer().dropMessage(5, "You may not use this command here.");
                    return 0;
                }
            } else if (npc == 1) {
                if (c.getPlayer().getLevel() < 70) {
                    c.getPlayer().dropMessage(5, "You must be over level 70 to use this command.");
                    return 0;
                }
            }
            if (c.getPlayer().hasBlockedInventory()) {
                c.getPlayer().dropMessage(5, "You may not use this command here.");
                return 0;
            }
            NPCScriptManager.getInstance().start(c, npcs[npc], null);
            return 1;
        }
    }*/

 /*public static class Npc extends OpenNPCCommand {

        public Npc() {
            npc = 0;
        }
    }

    public static class DCash extends OpenNPCCommand {

        public DCash() {
            npc = 1;
        }
    }

    public static class Event extends OpenNPCCommand {

        public Event() {
            npc = 2;
        }
    }*/
 /*public static class CheckDrop extends OpenNPCCommand {

        public CheckDrop() {
            npc = 4;
        }
    }*/
    public static class 爆率查询 extends CommandExecute {

        public boolean execute(MapleClient c, String[] splitted) {
            /*if (c.getPlayer().isInBlockedMap()) {
                c.getPlayer().dropMessage(5, "你当前状态无法使用该命令。");
                return false;
            }*/

            if (c.getPlayer().hasBlockedInventory()) {
                c.getPlayer().dropMessage(5, "你当前状态无法使用该命令。");
                return false;
            }
            NPCScriptManager.getInstance().start(c, 9010000, "怪物爆率");
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append(ServerConstants.PlayerGMRank.NORMAL.getCommandPrefix()).append("mobdrop - 查看怪物掉落").toString();
        }
    }

    public static class FM extends CommandExecute {

        public boolean execute(MapleClient c, String[] splitted) {
            for (int i : GameConstants.blockedMaps) {
                if (c.getPlayer().getMapId() == i) {
                    c.getPlayer().dropMessage(5, "当前地图无法使用.");
                    return false;
                }
            }
            if (c.getPlayer().getLevel() < 10 && c.getPlayer().getJob() != 200) {
                c.getPlayer().dropMessage(5, "你的等级不足10级无法使用.");
                return false;
            }
            if (c.getPlayer().hasBlockedInventory() || c.getPlayer().getMap().getSquadByMap() != null || c.getPlayer().getEventInstance() != null || c.getPlayer().getMap().getEMByMap() != null || c.getPlayer().getMapId() >= 990000000/* || FieldLimitType.VipRock.check(c.getPlayer().getMap().getFieldLimit())*/) {
                c.getPlayer().dropMessage(5, "请稍后再试");
                return false;
            }
            if ((c.getPlayer().getMapId() >= 680000210 && c.getPlayer().getMapId() <= 680000502) || (c.getPlayer().getMapId() / 1000 == 980000 && c.getPlayer().getMapId() != 980000000) || (c.getPlayer().getMapId() / 100 == 1030008) || (c.getPlayer().getMapId() / 100 == 922010) || (c.getPlayer().getMapId() / 10 == 13003000)) {
                c.getPlayer().dropMessage(5, "请稍后再试.");
                return false;
            }
            c.getPlayer().saveLocation(SavedLocationType.FREE_MARKET, c.getPlayer().getMap().getReturnMap().getId());
            MapleMap map = c.getChannelServer().getMapFactory().getMap(910000000);
            c.getPlayer().changeMap(map, map.getPortal(0));
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append(ServerConstants.PlayerGMRank.NORMAL.getCommandPrefix()).append("FM - 回自由").toString();
        }
    }

    public static String getDayOfWeek() {
        int dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;
        String dd = String.valueOf(dayOfWeek);
        switch (dayOfWeek) {
            case 0:
                dd = "日";
                break;
            case 1:
                dd = "一";
                break;
            case 2:
                dd = "二";
                break;
            case 3:
                dd = "三";
                break;
            case 4:
                dd = "四";
                break;
            case 5:
                dd = "五";
                break;
            case 6:
                dd = "六";
                break;
        }
        return dd;
    }

    public static class 解卡 extends ea {
    }

    public static class ea extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String[] splitted) {
            c.removeClickedNPC();
            NPCScriptManager.getInstance().dispose(c);
            c.getSession().write(MaplePacketCreator.enableActions());
            c.getPlayer().marriage();

            c.getSession().write(MaplePacketCreator.sendHint(
                    "解卡完毕..\r\n"
                    + "当前系统时间" + FileoutputUtil.getLocalDateString() + " 星期" + getDayOfWeek() + "\r\n"
                    + "经验值倍率 " + ((Math.round(c.getPlayer().getEXPMod()) * 100 * c.getChannelServer().getExpRate()) * Math.round(c.getPlayer().getStat().expBuff / 100.0) + c.getPlayer().getFairyExp())
                    + "%, 怪物爆率 " + Math.round(c.getPlayer().getDropMod() * c.getPlayer().getDropm() * (c.getPlayer().getStat().dropBuff / 100.0) * 100 * c.getChannelServer().getDropRate()) 
                    + "%, 金币倍率 " + Math.round((c.getPlayer().getStat().mesoBuff / 100.0) * 100 * c.getChannelServer().getMesoRate()) /*+ "% VIP经验加成：" + c.getPlayer().getVipExpRate()*/ + "%\r\n"
                    + "目前剩馀 " + c.getPlayer().getCSPoints(1) + " 点券 " + c.getPlayer().getCSPoints(2) + " 抵用券\r\n"
                    + "当前延迟 " + c.getPlayer().getClient().getLatency() + " 毫秒\r\n"
                    + "", 350, 5));
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append(ServerConstants.PlayerGMRank.NORMAL.getCommandPrefix()).append("ea - 解卡").toString();
        }
    }

    public static class TSmega extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String[] splitted) {
            c.getPlayer().setSmega();
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("@TSmega - 开/关闭广播").toString();
        }
    }

    public static class 隐藏转生 extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String[] splitted) {
            if (c.getPlayer().isZs() >= 1) {
                c.getPlayer().setisZs(0);
                c.getPlayer().dropMessage(5, "显示转生称号，换频生效");
            } else {
                c.getPlayer().setisZs(1);
                c.getPlayer().dropMessage(5, "隐藏转生称号，换频生效");
            }

            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("@隐藏转生 - 开/关闭隐藏转生").toString();
        }
    }

    public static class 隐藏VIP extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String[] splitted) {
            if (c.getPlayer().isVip() >= 1) {
                c.getPlayer().setisVip(0);
                c.getPlayer().dropMessage(5, "显示VIP称号，换频生效");
            } else {
                c.getPlayer().setisVip(1);
                c.getPlayer().dropMessage(5, "隐藏VIP称号，换频生效");
            }

            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("@隐藏VIP - 开/关闭隐藏VIP").toString();
        }
    }

    
    public static class 自由 extends CommandExecute
  {
    public boolean execute(MapleClient c, String[] splitted)
    {
      NPCScriptManager.getInstance().dispose(c);
      c.getSession().write(MaplePacketCreator.enableActions());
      NPCScriptManager npc = NPCScriptManager.getInstance();
      npc.start(c, 9900007, 999);
      return true;
    }

        @Override
        public String getMessage() {
            return new StringBuilder().append("@返回自由 - 返回自由").toString();
        }
    }
    
   public static class 拍卖 extends CommandExecute
  {
    public boolean execute(MapleClient c, String[] splitted)
    {
      NPCScriptManager.getInstance().dispose(c);
      c.getSession().write(MaplePacketCreator.enableActions());
      NPCScriptManager npc = NPCScriptManager.getInstance();
      npc.start(c, 9900007, 9999);
      return true;
    }

        @Override
        public String getMessage() {
            return new StringBuilder().append("@拍卖 - 打开拍卖").toString();
        }
    }
    
    public static class 爆率 extends CommandExecute
  {
    public boolean execute(MapleClient c, String[] splitted)
    {
      NPCScriptManager.getInstance().dispose(c);
      c.getSession().write(MaplePacketCreator.enableActions());
      NPCScriptManager npc = NPCScriptManager.getInstance();
      npc.start(c, 2000, 999);
      return true;
    }

      @Override
        public String getMessage() {
            return new StringBuilder().append("@爆率 - 查看当前地图爆率").toString();
        }
    }
    
    
    public static class 在线人数 extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String[] splitted) {
            int total = 0;
            int curConnected = c.getChannelServer().getConnectedClients();
            c.getPlayer().dropMessage(6, "-------------------------------------------------------------------------------------");
            c.getPlayer().dropMessage(6, new StringBuilder().append("频道: ").append(c.getChannelServer().getChannel()).append(" 线上人数: ").append(curConnected).toString());
            total += curConnected;
            for (MapleCharacter chr : c.getChannelServer().getPlayerStorage().getAllCharacters()) {
                if (chr != null && c.getPlayer().getGMLevel() >= chr.getGMLevel()) {
                    StringBuilder ret = new StringBuilder();
                    ret.append(" 角色名称 ");
                    ret.append(StringUtil.getRightPaddedStr(chr.getName(), ' ', 13));
                    ret.append(" ID: ");
                    ret.append(StringUtil.getRightPaddedStr(chr.getId() + "", ' ', 5));
                    ret.append(" 等级: ");
                    ret.append(StringUtil.getRightPaddedStr(String.valueOf(chr.getLevel()), ' ', 3));
                    ret.append(" 职业: ");
                    ret.append(StringUtil.getRightPaddedStr(String.valueOf(chr.getJob()), ' ', 4));
                    if (chr.getMap() != null) {
                        ret.append(" 地图: ");
                        ret.append(chr.getMapId()).append("(").append(chr.getMap().getMapName()).append(")");
                        c.getPlayer().dropMessage(6, ret.toString());
                    }
                }
            }
            c.getPlayer().dropMessage(6, new StringBuilder().append("当前频道总计线上人数: ").append(total).toString());
            c.getPlayer().dropMessage(6, "-------------------------------------------------------------------------------------");
            int channelOnline = c.getChannelServer().getConnectedClients();
            int totalOnline = 0;
            /*服务器总人数*/
            for (ChannelServer cserv : ChannelServer.getAllInstances()) {
                totalOnline += cserv.getConnectedClients();
            }
            c.getPlayer().dropMessage(6, new StringBuilder().append("当前服务器总计线上人数: ").append(totalOnline).append("个").toString());
            c.getPlayer().dropMessage(6, "-------------------------------------------------------------------------------------");

            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("@在线人数 - 查看线上人数").toString();
        }
    }

    /*public static class TSmega extends CommandExecute {

        public int execute(MapleClient c, String[] splitted) {
            c.getPlayer().setSmega();
            return 1;
        }
    }

    public static class Ranking extends CommandExecute {

        public int execute(MapleClient c, String[] splitted) {
            if (splitted.length < 4) { //job start end
                c.getPlayer().dropMessage(5, "Use @ranking [job] [start number] [end number] where start and end are ranks of the players");
                final StringBuilder builder = new StringBuilder("JOBS: ");
                for (String b : RankingWorker.getJobCommands().keySet()) {
                    builder.append(b);
                    builder.append(" ");
                }
                c.getPlayer().dropMessage(5, builder.toString());
            } else {
                int start = 1, end = 20;
                try {
                    start = Integer.parseInt(splitted[2]);
                    end = Integer.parseInt(splitted[3]);
                } catch (NumberFormatException e) {
                    c.getPlayer().dropMessage(5, "You didn't specify start and end number correctly, the default values of 1 and 20 will be used.");
                }
                if (end < start || end - start > 20) {
                    c.getPlayer().dropMessage(5, "End number must be greater, and end number must be within a range of 20 from the start number.");
                } else {
                    final Integer job = RankingWorker.getJobCommand(splitted[1]);
                    if (job == null) {
                        c.getPlayer().dropMessage(5, "Please use @ranking to check the job names.");
                    } else {
                        final List<RankingInformation> ranks = RankingWorker.getRankingInfo(job.intValue());
                        if (ranks == null || ranks.size() <= 0) {
                            c.getPlayer().dropMessage(5, "Please try again later.");
                        } else {
                            int num = 0;
                            for (RankingInformation rank : ranks) {
                                if (rank.rank >= start && rank.rank <= end) {
                                    if (num == 0) {
                                        c.getPlayer().dropMessage(6, "Rankings for " + splitted[1] + " - from " + start + " to " + end);
                                        c.getPlayer().dropMessage(6, "--------------------------------------");
                                    }
                                    c.getPlayer().dropMessage(6, rank.toString());
                                    num++;
                                }
                            }
                            if (num == 0) {
                                c.getPlayer().dropMessage(5, "No ranking was returned.");
                            }
                        }
                    }
                }
            }
            return 1;
        }
    }*/

 /*public static class Check extends CommandExecute {

        public int execute(MapleClient c, String[] splitted) {
            c.getPlayer().dropMessage(6, "You currently have " + c.getPlayer().getCSPoints(1) + " Cash.");
            c.getPlayer().dropMessage(6, "You currently have " + c.getPlayer().getPoints() + " donation points.");
            c.getPlayer().dropMessage(6, "You currently have " + c.getPlayer().getVPoints() + " voting points.");
            c.getPlayer().dropMessage(6, "You currently have " + c.getPlayer().getIntNoRecord(GameConstants.BOSS_PQ) + " Boss Party Quest points.");
            c.getPlayer().dropMessage(6, "The time is currently " + FileoutputUtil.CurrentReadable_TimeGMT() + " GMT.");
            return 1;
        }
    }*/
 /*public static class CheckRingID extends CommandExecute {

        public int execute(MapleClient c, String[] splitted) {
            c.getPlayer().dropMessage(5, "Each Ring will cost 20,000 A-Cash");
            c.getPlayer().dropMessage(5, "Please do not purchase more than 1 ring");
            c.getPlayer().dropMessage(5, "Here the Ring ID for Friendship Ring");
            c.getPlayer().dropMessage(5, "----------------");
            c.getPlayer().dropMessage(5, "Friendship Ring Clover RingID = 1112800");
            c.getPlayer().dropMessage(5, "Friendship Ring Flower Petal RingID = 1112801");
            c.getPlayer().dropMessage(5, "Friendship Ring Star RingID = 1112802");
            c.getPlayer().dropMessage(5, "----------------");
            c.getPlayer().dropMessage(5, "Here the Ring ID for Crush Ring");
            c.getPlayer().dropMessage(5, "----------------");
            c.getPlayer().dropMessage(5, "Couple Ring RingID = 1112001");
            c.getPlayer().dropMessage(5, "Heart Couple Couple Ring RingID = 1112002");
            c.getPlayer().dropMessage(5, "Cupid's Ring RingID = 1112003");
            c.getPlayer().dropMessage(5, "Venus Firework RingID = 1112005");
            c.getPlayer().dropMessage(5, "Crossed Hearts RingID = 1112006");
            c.getPlayer().dropMessage(5, "----------------");
            c.getPlayer().dropMessage(5, "Please do report to any GM if the ring id is invaild");
            return 1;
        }
    }*/
 /*public static class BuyRing extends CommandExecute {
        
         @Override
        public int execute(MapleClient c, String[] splitted) {
            if (splitted.length < 3) {
                c.getPlayer().dropMessage(6, "Use @BuyRing <Partner IGN> <ringID>");
                c.getPlayer().dropMessage(6, "Use @checkRingID for more information");
                return 0;
            }
            if (c.getPlayer().getCSPoints(1) < 20000) {
                c.getPlayer().dropMessage(6, "Lack of A-Cash, please come back again later.");
                return 0;
            }
            int itemId = Integer.parseInt(splitted[2]);
            if (!GameConstants.isEffectRing(itemId)) {
                c.getPlayer().dropMessage(6, "Invalid itemID.");
            } else {
                MapleCharacter fff = c.getChannelServer().getPlayerStorage().getCharacterByName(splitted[1]);
                if (fff == null) {
                    c.getPlayer().dropMessage(6, "Player must be online");
                } else {
                    int[] ringID = {MapleInventoryIdentifier.getInstance(), MapleInventoryIdentifier.getInstance()};
                    try {
                        MapleCharacter[] chrz = {fff, c.getPlayer()};
                        for (int i = 0; i < chrz.length; i++) {
                            Equip eq = (Equip) MapleItemInformationProvider.getInstance().getEquipById(itemId, ringID[i]);
                            if (eq == null) {
                                c.getPlayer().dropMessage(6, "Invalid itemID.");
                                return 0;
                            }
                            MapleInventoryManipulator.addbyItem(chrz[i].getClient(), eq.copy());
                            chrz[i].dropMessage(6, "Successfully ring with " + chrz[i == 0 ? 1 : 0].getName());
                            c.getPlayer().modifyCSPoints(1, -20000);
                        }
                        MapleRing.addToDB(itemId, c.getPlayer(), fff.getName(), fff.getId(), ringID);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            return 1;
        }
    }*/
//    public static class Help extends CommandExecute {
//
//        public boolean execute(MapleClient c, String[] splitted) {
//            c.getPlayer().dropMessage(5, "@str, @dex, @int, @luk <分配属性点>");
//            c.getPlayer().dropMessage(5, "@mob < 查询怪物状态 >");
//            //    c.getPlayer().dropMessage(5, "@loot < Loot items that drop from the mobs >");
//            //    c.getPlayer().dropMessage(5, "@check < Displays various information >");
//            //   c.getPlayer().dropMessage(5, "@fm < Warp to FM >");
//            //   c.getPlayer().dropMessage(5, "@npc < Universal Town Warp / Event NPC>");
//            //  c.getPlayer().dropMessage(5, "@tsmega < Toggle super megaphone on/off >");
//            c.getPlayer().dropMessage(5, "@ea 或 @解卡< 在不能攻击或不能跟npc对话,请在对话框打 @解卡/@ea 来解除异常状态。 >");
//            //  c.getPlayer().dropMessage(5, "@eventrewards < Use this command to go to the Rewards Map >");
//            // c.getPlayer().dropMessage(5, "@ranking < Use @ranking for more details >");
//            c.getPlayer().dropMessage(5, "@checkdrop < 查询怪物爆率 >");
//            //  c.getPlayer().dropMessage(5, "@expedition < Warp you to the Expedition Map. Minimum level : 50 >");
//            // c.getPlayer().dropMessage(5, "@christmas < Warp you to Christmap Map Minimum level : 10 >");
//            // c.getPlayer().dropMessage(5, "@warptodcash < Warp you to Cash Dropper Map. Minimum level : 10 >");
//            //c.getPlayer().dropMessage(5, "@quest < Warp you to Quest Map. Minimum level : 30 >");
//            //c.getPlayer().dropMessage(5, "@CheckRingID < Check Ring ID and available Ring >");
//            //c.getPlayer().dropMessage(5, "@BuyRing < Purchase Ring @BuyRing <Ringid> <Partner's IGN> >");
//            return true;
//        }
//    }
    public static class help extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String[] splitted) {
            /*c.getPlayer().dropNPC(""
                    + "\t  #r               玩家指令区#k       \r\n"
                    + "\t\t#b@清除道具 <装备栏/消耗栏/装饰栏/其他栏/特殊栏> <开始格数> <结束格数>#k - #r<清除背包道具>#k\r\n"
                    + "\t\t#b@ea#k - #r<解除异常+查看当前状态>#k\r\n"
                    + "\t\t#b@mob#k - #r<查看身边怪物讯息>#k\r\n"
                    + "\t\t#b@expfix#k - #r<经验归零(修复假死)>#k\r\n"
                    + "\t\t#b@CGM <讯息>#k - #r<传送讯息给GM>#k\r\n"
                    + "\t\t#b@str, @dex, @int, @luk <分配属性点>#k\r\n"
                    + "\t\t#b@mobdrop < 查询怪物掉落物 >#k\r\n"
            );*/
            c.getPlayer().dropMessage(6, "玩家指令:");
            c.getPlayer().dropMessage(6, "@清除道具 - <装备栏/消耗栏/装饰栏/其他栏/特殊栏> <开始格数> <结束格数> - <清除背包道具>");
            c.getPlayer().dropMessage(6, "@解卡 - <解除异常+查看当前状态>");
            //c.getPlayer().dropMessage(5, "@万能  <打开拍卖界面>");
            //c.getPlayer().dropMessage(5, "@自由/@zy  <立即回到自于市场>");
            //c.getPlayer().dropMessage(5, "@online  <查看频道在线玩家>");
           // c.getPlayer().dropMessage(6, "@mob - <查看身边怪物讯息>");
            c.getPlayer().dropMessage(6, "@经验修复 - <经验归零(修复假死)>");
            c.getPlayer().dropMessage(6, "@CGM <讯息> - <传送讯息给GM>");
            c.getPlayer().dropMessage(6, "@力量, @敏捷, @智力, @运气 <分配属性点>");
            c.getPlayer().dropMessage(6, "@drop < 查询怪物掉落物 >");
            c.getPlayer().dropMessage(6, "@TSmega < 开/关闭广播 >");
           // c.getPlayer().dropMessage(6, "@装备透明双刀 < 装备透明双刀在装备栏的位置 >");
            //c.getPlayer().dropMessage(6, "@丢弃点装 < 丢弃点装在装备栏的位置 >");
           // c.getPlayer().dropMessage(6, "@隐藏转生 < 隐藏转生 >");
            c.getPlayer().dropMessage(6, "@隐藏VIP < 隐藏VIP >");
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append(ServerConstants.PlayerGMRank.NORMAL.getCommandPrefix()).append("help - 帮助").toString();
        }
    }

    /*public static class 高级检索 extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String[] splitted) {
            c.removeClickedNPC();
            NPCScriptManager.getInstance().start(c, 9010000, "高级检索");
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append(ServerConstants.PlayerGMRank.NORMAL.getCommandPrefix()).append("高级检索 - 高级检索").toString();
        }
    }*/
    public static class 清除道具 extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String[] splitted) {
            if (splitted.length < 4) {
                return false;
            }
            MapleInventory inv;
            MapleInventoryType type;
            String Column = "null";
            int start = -1;
            int end = -1;
            try {
                Column = splitted[1];
                start = Integer.parseInt(splitted[2]);
                end = Integer.parseInt(splitted[3]);
            } catch (Exception ex) {
            }
            if (start == -1 || end == -1) {
                c.getPlayer().dropMessage(6, "@清除道具 <装备栏/消耗栏/装饰栏/其他栏/特殊栏> <开始格数> <结束格数>");
                return true;
            }
            if (start < 1) {
                start = 1;
            }
            if (end > 96) {
                end = 96;
            }

            switch (Column) {
                case "装备栏":
                    type = MapleInventoryType.EQUIP;
                    break;
                case "消耗栏":
                    type = MapleInventoryType.USE;
                    break;
                case "装饰栏":
                    type = MapleInventoryType.SETUP;
                    break;
                case "其他栏":
                    type = MapleInventoryType.ETC;
                    break;
                case "特殊栏":
                    type = MapleInventoryType.CASH;
                    break;
                default:
                    type = null;
                    break;
            }
            if (type == null) {
                c.getPlayer().dropMessage(6, "@清除道具 <装备栏/消耗栏/装饰栏/其他栏/特殊栏> <开始格数> <结束格数>");
                return true;
            }
            inv = c.getPlayer().getInventory(type);

            for (int i = start; i <= end; i++) {
                if (inv.getItem((short) i) != null) {
                    MapleInventoryManipulator.removeFromSlot(c, type, (short) i, inv.getItem((short) i).getQuantity(), true);
                }
            }
            FileoutputUtil.logToFile("logs/Data/玩家指令.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSessionIPAddress() + " 帐号: " + c.getAccountName() + " 玩家: " + c.getPlayer().getName() + " 使用了指令 " + StringUtil.joinStringFrom(splitted, 0));
            c.getPlayer().dropMessage(6, "您已经清除了第 " + start + " 格到 " + end + "格的" + Column + "道具");
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append(ServerConstants.PlayerGMRank.NORMAL.getCommandPrefix()).append("清除道具 <装备栏/消耗栏/装饰栏/其他栏/特殊栏> <开始格数> <结束格数>").toString();
        }
    }


    /*public static class TradeHelp extends TradeExecute {

        public int execute(MapleClient c, String[] splitted) {
            c.getPlayer().dropMessage(-2, "[System] : <@offerequip, @offeruse, @offersetup, @offeretc, @offercash> <quantity> <name of the item>");
            return 1;
        }
    }

    public abstract static class OfferCommand extends TradeExecute {

        protected int invType = -1;

        public int execute(MapleClient c, String[] splitted) {
            if (splitted.length < 3) {
                c.getPlayer().dropMessage(-2, "[Error] : <quantity> <name of item>");
            } else if (c.getPlayer().getLevel() < 70) {
                c.getPlayer().dropMessage(-2, "[Error] : Only level 70+ may use this command");
            } else {
                int quantity = 1;
                try {
                    quantity = Integer.parseInt(splitted[1]);
                } catch (Exception e) { //swallow and just use 1
                }
                String search = StringUtil.joinStringFrom(splitted, 2).toLowerCase();
                Item found = null;
                final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
                for (Item inv : c.getPlayer().getInventory(MapleInventoryType.getByType((byte) invType))) {
                    if (ii.getName(inv.getItemId()) != null && ii.getName(inv.getItemId()).toLowerCase().contains(search)) {
                        found = inv;
                        break;
                    }
                }
                if (found == null) {
                    c.getPlayer().dropMessage(-2, "[Error] : No such item was found (" + search + ")");
                    return 0;
                }
                if (GameConstants.isPet(found.getItemId()) || GameConstants.isRechargable(found.getItemId())) {
                    c.getPlayer().dropMessage(-2, "[Error] : You may not trade this item using this command");
                    return 0;
                }
                if (quantity > found.getQuantity() || quantity <= 0 || quantity > ii.getSlotMax(found.getItemId())) {
                    c.getPlayer().dropMessage(-2, "[Error] : Invalid quantity");
                    return 0;
                }
                if (!c.getPlayer().getTrade().setItems(c, found, (byte) -1, quantity)) {
                    c.getPlayer().dropMessage(-2, "[Error] : This item could not be placed");
                    return 0;
                } else {
                    c.getPlayer().getTrade().chatAuto("[System] : " + c.getPlayer().getName() + " offered " + ii.getName(found.getItemId()) + " x " + quantity);
                }
            }
            return 1;
        }
    }

    public static class OfferEquip extends OfferCommand {

        public OfferEquip() {
            invType = 1;
        }
    }

    public static class OfferUse extends OfferCommand {

        public OfferUse() {
            invType = 2;
        }
    }

    public static class OfferSetup extends OfferCommand {

        public OfferSetup() {
            invType = 3;
        }
    }

    public static class OfferEtc extends OfferCommand {

        public OfferEtc() {
            invType = 4;
        }
    }

    public static class OfferCash extends OfferCommand {

        public OfferCash() {
            invType = 5;
        }
    }
   
    public static class eventrewards extends CommandExecute {

        public int execute(MapleClient c, String[] splitted) {
	    if (c.getPlayer().getLevel() >= 10) {
		final MapleMap map = c.getChannelServer().getMapFactory().getMap(220000304);
		c.getPlayer().changeMap(map);
	    } else {
		c.getPlayer().dropMessage(-3, "You're not level 10.");
	    }
            return 1;
        }
    }

    
    public static class quest extends CommandExecute {

        public int execute(MapleClient c, String[] splitted) {
	    if (c.getPlayer().getLevel() >= 30) {
		final MapleMap map = c.getChannelServer().getMapFactory().getMap(240070000);
		c.getPlayer().changeMap(map);
	    } else {
		c.getPlayer().dropMessage(-3, "You're not level 30.");
	    }
            return 1;
        }
    }

    public static class warptodcash extends CommandExecute {

        public int execute(MapleClient c, String[] splitted) {
	    if (c.getPlayer().getLevel() >= 10) {
		final MapleMap map = c.getChannelServer().getMapFactory().getMap(910020100);
		c.getPlayer().changeMap(map);
	    } else {
		c.getPlayer().dropMessage(-3, "You're not level 10.");
	    }
            return 1;
        }
    }

    public static class expedition extends CommandExecute {

        public int execute(MapleClient c, String[] splitted) {
	    if (c.getPlayer().getLevel() >= 50) {
		final MapleMap map = c.getChannelServer().getMapFactory().getMap(910000013);
		c.getPlayer().changeMap(map);
	    } else {
		c.getPlayer().dropMessage(-3, "You're not level 50.");
	    }
            return 1;
        }
    }

    public static class christmas extends CommandExecute {

        public int execute(MapleClient c, String[] splitted) {
	    if (c.getPlayer().getLevel() >= 10) {
		final MapleMap map = c.getChannelServer().getMapFactory().getMap(209000000);
		c.getPlayer().changeMap(map);
	    } else {
		c.getPlayer().dropMessage(-3, "You're not level 10.");
	    }
            return 1;
        }
    }

    public static class Loot extends CommandExecute {

        public int execute(MapleClient c, String[] splitted) {
	    final int[] map = {280030001,280030000,240060201,240060200,551030200,271040100};
	    for (int i : map) {
		if (c.getPlayer().getMapId() != i) {
		    if (c.getPlayer().getPets() != null) {
			List<MapleMapObject> items = c.getPlayer().getMap().getMapObjectsInRange(c.getPlayer().getTruePosition(), c.getPlayer().getRange(), Arrays.asList(MapleMapObjectType.ITEM));
			for (MapleMapObject item : items) {
			    MapleMapItem mapItem = (MapleMapItem) item;
			    if (mapItem.isPickedUp()) {
				c.getSession().write(MaplePacketCreator.getInventoryFull());
				continue;
			    }
			    if (mapItem.getOwner() != c.getPlayer().getId() && mapItem.isPlayerDrop()) {
				continue;
			    }
			    if (mapItem.getOwner() != c.getPlayer().getId() && ((!mapItem.isPlayerDrop() && mapItem.getDropType() == 0) || (mapItem.isPlayerDrop() && c.getPlayer().getMap().getEverlast()))) {
				c.getSession().write(MaplePacketCreator.enableActions());
				continue;
			    }
			    if (!mapItem.isPlayerDrop() && mapItem.getDropType() == 1 && mapItem.getOwner() != c.getPlayer().getId()) {
				c.getSession().write(MaplePacketCreator.enableActions());
				continue;
			    }
	
			    if (mapItem.getMeso() > 0) {
				c.getPlayer().gainMeso(mapItem.getMeso(), true);
				mapItem.setPickedUp(true);
				c.getPlayer().getMap().broadcastMessage(MaplePacketCreator.removeItemFromMap(mapItem.getObjectId(), 5, c.getPlayer().getId(), 0));
				c.getPlayer().getMap().removeMapObject(mapItem);
			    } else {
				if (MapleItemInformationProvider.getInstance().isPickupBlocked(mapItem.getItemId()) || mapItem.getItemId() / 10000 == 291) {
				    c.getSession().write(MaplePacketCreator.enableActions());
				} else if (MapleInventoryManipulator.checkSpace(c, mapItem.getItemId(), mapItem.getItem().getQuantity(), mapItem.getItem().getOwner())) {
				    if (mapItem.getItem().getQuantity() >= 50 && mapItem.getItemId() == 2340000) {
					c.setMonitored(true); //hack check
				    }
				    MapleInventoryManipulator.addFromDrop(c, mapItem.getItem(), true, mapItem.getDropper() instanceof MapleMonster);
				}
				mapItem.setPickedUp(true);
				c.getPlayer().getMap().broadcastMessage(MaplePacketCreator.removeItemFromMap(mapItem.getObjectId(), 5, c.getPlayer().getId(), 0));
				c.getPlayer().getMap().removeMapObject(mapItem);
			    }
			}
		    } else {
			c.getPlayer().dropMessage(-5, "You need to have at least 1 pet to use this.");
		    }
		} else {
		    c.getPlayer().dropMessage(-5, "Sorry, this command is not available in boss map");
		}
	    }
            return 1;
	}
    }*/
}
