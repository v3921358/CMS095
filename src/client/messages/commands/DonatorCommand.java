package client.messages.commands;

import client.MapleCharacter;
import client.MapleClient;
import client.MapleStat;
import client.SkillFactory;
import client.inventory.MapleInventory;
import client.inventory.MapleInventoryType;
import client.messages.CommandProcessorUtil;
import constants.ServerConstants;
import constants.ServerConstants.PlayerGMRank;
import handling.channel.ChannelServer;
import handling.login.LoginServer;
import handling.world.World;
import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import scripting.NPCScriptManager;
import server.MapleInventoryManipulator;
import server.MaplePortal;
import server.life.MapleNPC;
import server.maps.MapleMapObject;
import server.maps.MapleReactor;
import tools.FileoutputUtil;
import tools.StringUtil;
import tools.packet.MaplePacketCreator;

public class DonatorCommand {

    public static PlayerGMRank getPlayerLevelRequired() {
        return PlayerGMRank.DONATOR;
    }

    public static class adminmode extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            LoginServer.setAdminOnly(!LoginServer.isAdminOnly());
            c.getPlayer().dropMessage(0, "[adminmode] " + (LoginServer.isAdminOnly() ? "开启" : "关闭"));
            System.out.println("[adminmode] " + (LoginServer.isAdminOnly() ? "开启" : "关闭"));
            return true;
        }

        public String getMessage() {
            return new StringBuilder().append("!adminmode  - 开关管理员模式").toString();
        }
    }

    public static class CC extends ChangeChannel {

        public String getMessage() {
            return new StringBuilder().append("!cc <频道> - 更换频道").toString();
        }
    }

    public static class ChangeChannel extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String[] splitted) {
            if (splitted.length < 2) {
                return false;
            }
            int cc = Integer.parseInt(splitted[1]);
            if (c.getChannel() != cc) {
                c.getPlayer().changeChannel(cc);
            } else {
                c.getPlayer().dropMessage(5, "请输入正确的频道。");
            }
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!changechannel <频道> - 更换频道").toString();
        }
    }

    public static class 角色讯息 extends spy {

        public String getMessage() {
            return new StringBuilder().append("!角色讯息 <玩家名字> - 观察玩家").toString();
        }
    }

    public static class spy extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String[] splitted) {
            if (splitted.length < 2) {
                return false;
            } else {
                String name = splitted[1];
                MapleCharacter victim = MapleCharacter.getCharacterByName(name);
                int ch = World.Find.findChannel(name);
                if (victim != null) {
                    if (victim.getGMLevel() > c.getPlayer().getGMLevel()) {
                        c.getPlayer().dropMessage(5, "你不能查看比你高权限的人!");
                    } else {
                        int mesoInStorage = 0;
                        try {
                            if (victim.getStorage() != null) {
                                mesoInStorage = victim.getStorage().getMeso();
                            }
                        } catch (Exception ex) {
                        }
                        c.getPlayer().dropMessage(5, "此玩家状态:");
                        c.getPlayer().dropMessage(5, "玩家名称: " + victim.getName() + " 玩家编号: " + victim.getId() + " 帐号: " + victim.getClient().getAccountName() + " 帐号ID: " + victim.getAccountID());
                        c.getPlayer().dropMessage(5, "玩家权限: " + victim.getGMLevel() + " 等级: " + victim.getLevel() + " 职业: " + victim.getJob() + " 名声: " + victim.getFame());
                        c.getPlayer().dropMessage(5, "地图: " + victim.getMapId() + " - " + victim.getMap().getMapName());
                        c.getPlayer().dropMessage(5, "目前HP: " + victim.getStat().getHp() + " 目前MP: " + victim.getStat().getMp());
                        c.getPlayer().dropMessage(5, "最大HP: " + victim.getStat().getMaxHp() + " 最大MP: " + victim.getStat().getMaxMp());
                        c.getPlayer().dropMessage(5, "力量: " + victim.getStat().getStr() + "  ||  敏捷: " + victim.getStat().getDex() + "  ||  智力: " + victim.getStat().getInt() + "  ||  幸运: " + victim.getStat().getLuk());
                        c.getPlayer().dropMessage(5, "物理攻击: " + victim.getStat().getTotalWatk() + "  ||  魔法攻击: " + victim.getStat().getTotalMagic());
                        c.getPlayer().dropMessage(5, "经验倍率: " + victim.getStat().expBuff + " 金钱倍率: " + victim.getStat().mesoBuff + " 掉宝倍率: " + victim.getStat().dropBuff);
                        c.getPlayer().dropMessage(5, "GASH: " + victim.getCSPoints(1) + " 枫叶点数: " + victim.getCSPoints(2) + " 枫币: " + victim.getMeso() + " 仓库枫币 " + mesoInStorage);
                        c.getPlayer().dropMessage(5, "赞助总额： " + victim.getMoneyAll() + " 红利点数： " + victim.getCSPoints(3));
                        if (ch <= 0) {
                            c.getPlayer().dropMessage(5, "该角色为离线状态");
                        } else {
                            c.getPlayer().dropMessage(5, "IP:" + victim.getClient().getSessionIPAddress());
                            c.getPlayer().dropMessage(5, "对服务器延迟: " + victim.getClient().getLatency());
                        }
                    }
                } else {
                    c.getPlayer().dropMessage(5, "找不到此玩家.");
                }
            }
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!spy <玩家名字> - 观察玩家").toString();
        }
    }

    public static class Clock extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            if (splitted.length < 2) {
                return false;
            }
            c.getPlayer().getMap().broadcastMessage(MaplePacketCreator.getClock(CommandProcessorUtil.getOptionalIntArg(splitted, 1, 60)));
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!Clock <time> 时钟").toString();
        }
    }

    public static class Heal extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            c.getPlayer().getStat().setHp(c.getPlayer().getStat().getCurrentMaxHp(), c.getPlayer());
            c.getPlayer().getStat().setMp(c.getPlayer().getStat().getCurrentMaxMp(), c.getPlayer());
            c.getPlayer().updateSingleStat(MapleStat.HP, c.getPlayer().getStat().getCurrentMaxHp());
            c.getPlayer().updateSingleStat(MapleStat.MP, c.getPlayer().getStat().getCurrentMaxMp());
            c.getPlayer().dispelDebuffs();
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!heal - 恢复目前HPMP/消除负面状态BUFF").toString();
        }
    }

    public static class 开启隐身 extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String[] splitted) {
            SkillFactory.getSkill(9001004).getEffect(1).applyTo(c.getPlayer());
            c.getPlayer().dropMessage(6, "管理员隐藏 = 开启 \r\n 解除请输入!unhide");
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!开启隐身 - 开启隐身").toString();
        }
    }

    public static class 解除隐身 extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String[] splitted) {
            c.getPlayer().dispelBuff(9001004);
            c.getPlayer().dropMessage(6, "管理员隐藏 = 关闭 \r\n 开启请输入!hide");
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!unhide - 解除隐身").toString();
        }
    }

    public static class ItemCheck extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            if (splitted.length < 3 || splitted[1] == null || splitted[1].equals("") || splitted[2] == null || splitted[2].equals("")) {
                return false;
            } else {
                int item = Integer.parseInt(splitted[2]);
                MapleCharacter chr;
                String name = splitted[1];
                int ch = World.Find.findChannel(name);
                if (ch <= 0) {
                    c.getPlayer().dropMessage(6, "玩家必须上线");
                    return true;
                }
                chr = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(name);

                int itemamount = chr.getItemQuantity(item, true);
                if (itemamount > 0) {
                    c.getPlayer().dropMessage(6, chr.getName() + " 有 " + itemamount + " (" + item + ").");
                } else {
                    c.getPlayer().dropMessage(6, chr.getName() + " 并没有 (" + item + ")");
                }
            }
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!itemcheck <playername> <itemid> - 检查物品").toString();
        }
    }

    public static class Letter extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            if (splitted.length < 3) {
                c.getPlayer().dropMessage(6, "指令规则: ");
                return false;
            }
            int start, nstart;
            if (splitted[1].equalsIgnoreCase("green")) {
                start = 3991026;
                nstart = 3990019;
            } else if (splitted[1].equalsIgnoreCase("red")) {
                start = 3991000;
                nstart = 3990009;
            } else {
                c.getPlayer().dropMessage(6, "未知的颜色!");
                return true;
            }
            String splitString = StringUtil.joinStringFrom(splitted, 2);
            List<Integer> chars = new ArrayList<>();
            splitString = splitString.toUpperCase();
            // System.out.println(splitString);
            for (int i = 0; i < splitString.length(); i++) {
                char chr = splitString.charAt(i);
                if (chr == ' ') {
                    chars.add(-1);
                } else if ((int) (chr) >= (int) 'A' && (int) (chr) <= (int) 'Z') {
                    chars.add((int) (chr));
                } else if ((int) (chr) >= (int) '0' && (int) (chr) <= (int) ('9')) {
                    chars.add((int) (chr) + 200);
                }
            }
            final int w = 32;
            int dStart = c.getPlayer().getPosition().x - (splitString.length() / 2 * w);
            for (Integer i : chars) {
                if (i == -1) {
                    dStart += w;
                } else if (i < 200) {
                    int val = start + i - (int) ('A');
                    client.inventory.Item item = new client.inventory.Item(val, (byte) 0, (short) 1);
                    c.getPlayer().getMap().spawnItemDrop(c.getPlayer(), c.getPlayer(), item, new Point(dStart, c.getPlayer().getPosition().y), false, false);
                    dStart += w;
                } else if (i >= 200 && i <= 300) {
                    int val = nstart + i - (int) ('0') - 200;
                    client.inventory.Item item = new client.inventory.Item(val, (byte) 0, (short) 1);
                    c.getPlayer().getMap().spawnItemDrop(c.getPlayer(), c.getPlayer(), item, new Point(dStart, c.getPlayer().getPosition().y), false, false);
                    dStart += w;
                }
            }
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!letter <color (green/red)> <word> - 送信").toString();
        }

    }

    public static class LookNPC extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            for (MapleMapObject reactor1l : c.getPlayer().getMap().getAllNPCsThreadsafe()) {
                MapleNPC reactor2l = (MapleNPC) reactor1l;
                c.getPlayer().dropMessage(5, "NPC: oID: " + reactor2l.getObjectId() + " npcID: " + reactor2l.getId() + " Position: " + reactor2l.getPosition().toString() + " Name: " + reactor2l.getName());
            }
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!looknpc - 查看所有NPC").toString();
        }
    }

    public static class LookPortal extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            for (MaplePortal portal : c.getPlayer().getMap().getPortals()) {
                c.getPlayer().dropMessage(5, "Portal: ID: " + portal.getId() + " script: " + portal.getScriptName() + " name: " + portal.getName() + " pos: " + portal.getPosition().x + "," + portal.getPosition().y + " target: " + portal.getTargetMapId() + " / " + portal.getTarget());
            }
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!lookportal - 查看所有传送点").toString();
        }
    }

    public static class LookReactor extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            for (MapleMapObject reactor1l : c.getPlayer().getMap().getAllReactorsThreadsafe()) {
                MapleReactor reactor2l = (MapleReactor) reactor1l;
                c.getPlayer().dropMessage(5, "Reactor: oID: " + reactor2l.getObjectId() + " reactorID: " + reactor2l.getReactorId() + " Position: " + reactor2l.getPosition().toString() + " State: " + reactor2l.getState() + " Name: " + reactor2l.getName());
            }
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!lookreactor - 查看所有反应堆").toString();
        }
    }

    public static class LowHP extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            c.getPlayer().getStat().setHp(1, c.getPlayer());
            c.getPlayer().updateSingleStat(MapleStat.HP, 1);
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!lowhp - 将HP归1ㄧ").toString();
        }
    }

    public static class LowMP extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            c.getPlayer().getStat().setMp(1, c.getPlayer());
            c.getPlayer().updateSingleStat(MapleStat.MP, 1);
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!lowmp - 将MP归1ㄧ").toString();
        }
    }

    public static class 地图代码 extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            c.getPlayer().dropMessage(5, "目前地图 " + c.getPlayer().getMap().getId() + "座标 (" + String.valueOf(c.getPlayer().getPosition().x) + " , " + String.valueOf(c.getPlayer().getPosition().y) + ")");
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!地图代码 - 目前地图信息").toString();
        }
    }

    public static class NearestPortal extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            MaplePortal portal = c.getPlayer().getMap().findClosestSpawnpoint(c.getPlayer().getPosition());
            c.getPlayer().dropMessage(6, portal.getName() + " id: " + portal.getId() + " script: " + portal.getScriptName());

            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!nearestportal - 查看最近的传送点").toString();

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
            return new StringBuilder().append("!在线人数 - 查看线上人数").toString();
        }
    }

    public static class onlineGM extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String[] splitted) {
            int channelOnline = 0;
            int totalOnline = 0;
            int GmInChannel = 0;
            List<MapleCharacter> chrs = new LinkedList<>();

            /*当前频道总GM数*/
            for (MapleCharacter chr : c.getChannelServer().getPlayerStorage().getAllCharacters()) {
                if (chr.getGMLevel() > 0) {
                    channelOnline++;
                }
            }
            /*服务器总GM数*/
            for (ChannelServer cserv : ChannelServer.getAllInstances()) {
                for (MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                    if (chr != null && chr.getGMLevel() > 0) {
                        totalOnline++;
                    }
                }
            }
            c.getPlayer().dropMessage(6, "-------------------------------------------------------------------------------------");
            for (ChannelServer cserv : ChannelServer.getAllInstances()) {
                for (MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                    if (chr != null && chr.getGMLevel() > 0) {
                        chrs.add(chr);
                    }
                }
                GmInChannel = chrs.size();
                if (GmInChannel > 0) {
                    c.getPlayer().dropMessage(6, new StringBuilder().append("频道: ").append(cserv.getChannel()).append(" 线上GM人数: ").append(GmInChannel).toString());
                    for (MapleCharacter chr : chrs) {
                        if (chr != null) {
                            StringBuilder ret = new StringBuilder();
                            ret.append(" GM名称 ");
                            ret.append(StringUtil.getRightPaddedStr(chr.getName(), ' ', 13));
                            ret.append(" ID: ");
                            ret.append(StringUtil.getRightPaddedStr(chr.getId() + "", ' ', 5));
                            ret.append(" 权限: ");
                            ret.append(StringUtil.getRightPaddedStr(String.valueOf(chr.getGMLevel()), ' ', 3));
                            c.getPlayer().dropMessage(6, ret.toString());
                        }
                    }
                }
                chrs = new LinkedList<>();
            }
            c.getPlayer().dropMessage(6, new StringBuilder().append("当前频道总计GM线上人数: ").append(channelOnline).toString());
            c.getPlayer().dropMessage(6, "-------------------------------------------------------------------------------------");

            c.getPlayer().dropMessage(6, new StringBuilder().append("当前服务器GM总计线上人数: ").append(totalOnline).append("个").toString());
            c.getPlayer().dropMessage(6, "-------------------------------------------------------------------------------------");
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!onlineGM - 查看线上人数GM").toString();
        }
    }

    public static class SpawnDebug extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            c.getPlayer().dropMessage(6, c.getPlayer().getMap().spawnDebug());
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!spawndebug - debug怪物出生").toString();

        }
    }

    public static class 清理道具 extends CommandExecute {

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
                c.getPlayer().dropMessage(6, "!清理道具 <装备栏/消耗栏/装饰栏/其他栏/特殊栏> <开始格数> <结束格数>");
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
                c.getPlayer().dropMessage(6, "!清理道具 <装备栏/消耗栏/装饰栏/其他栏/特殊栏> <开始格数> <结束格数>");
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
            return new StringBuilder().append("!清理道具 <装备栏/消耗栏/装饰栏/其他栏/特殊栏> <开始格数> <结束格数>").toString();
        }
    }

    public static class 燃烧阶段 extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            ServerConstants.levelx2 = !ServerConstants.levelx2;
            c.getPlayer().dropMessage(0, "[燃烧阶段] " + (ServerConstants.levelx2 ? "开启" : "关闭"));
            System.out.println("[燃烧阶段] " + (ServerConstants.levelx2 ? "开启" : "关闭"));
            return true;
        }

        public String getMessage() {
            return new StringBuilder().append("!燃烧阶段  - 开关燃烧阶段").toString();
        }
    }

    public static class 仓库输出 extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            ServerConstants.message_storage = !ServerConstants.message_storage;
            c.getPlayer().dropMessage(0, "[仓库输出] " + (ServerConstants.message_storage ? "开启" : "关闭"));
            System.out.println("[仓库输出] " + (ServerConstants.message_storage ? "开启" : "关闭"));
            return true;
        }

        public String getMessage() {
            return new StringBuilder().append("!仓库输出  - 开关仓库输出").toString();
        }
    }

    public static class 普通聊天 extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            ServerConstants.message_generalchat = !ServerConstants.message_generalchat;
            c.getPlayer().dropMessage(0, "[普通聊天输出] " + (ServerConstants.message_generalchat ? "开启" : "关闭"));
            System.out.println("[普通聊天输出] " + (ServerConstants.message_generalchat ? "开启" : "关闭"));
            return true;
        }

        public String getMessage() {
            return new StringBuilder().append("!普通聊天  - 开关普通聊天输出").toString();
        }
    }

    public static class 好友聊天 extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            ServerConstants.message_buddychat = !ServerConstants.message_buddychat;
            c.getPlayer().dropMessage(0, "[好友聊天输出] " + (ServerConstants.message_buddychat ? "开启" : "关闭"));
            System.out.println("[好友聊天输出] " + (ServerConstants.message_buddychat ? "开启" : "关闭"));
            return true;
        }

        public String getMessage() {
            return new StringBuilder().append("!好友聊天  - 开关好友聊天输出").toString();
        }
    }

    public static class 组队聊天 extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            ServerConstants.message_partychat = !ServerConstants.message_partychat;
            c.getPlayer().dropMessage(0, "[组队聊天输出] " + (ServerConstants.message_partychat ? "开启" : "关闭"));
            System.out.println("[组队聊天输出] " + (ServerConstants.message_partychat ? "开启" : "关闭"));
            return true;
        }

        public String getMessage() {
            return new StringBuilder().append("!组队聊天  - 开关组队聊天输出").toString();
        }
    }

    public static class 公会聊天 extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            ServerConstants.message_guildchat = !ServerConstants.message_guildchat;
            c.getPlayer().dropMessage(0, "[公会聊天输出] " + (ServerConstants.message_guildchat ? "开启" : "关闭"));
            System.out.println("[公会聊天输出] " + (ServerConstants.message_guildchat ? "开启" : "关闭"));
            return true;
        }

        public String getMessage() {
            return new StringBuilder().append("!公会聊天  - 开关公会聊天输出").toString();
        }
    }

    public static class 联盟聊天 extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            ServerConstants.message_alliancechat = !ServerConstants.message_alliancechat;
            c.getPlayer().dropMessage(0, "[联盟聊天输出] " + (ServerConstants.message_alliancechat ? "开启" : "关闭"));
            System.out.println("[联盟聊天输出] " + (ServerConstants.message_alliancechat ? "开启" : "关闭"));
            return true;
        }

        public String getMessage() {
            return new StringBuilder().append("!联盟聊天  - 开关联盟聊天输出").toString();
        }
    }

    public static class 玩家聊天 extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            ServerConstants.message_whisperchat = !ServerConstants.message_whisperchat;
            c.getPlayer().dropMessage(0, "[玩家聊天输出] " + (ServerConstants.message_whisperchat ? "开启" : "关闭"));
            System.out.println("[玩家聊天输出] " + (ServerConstants.message_whisperchat ? "开启" : "关闭"));
            return true;
        }

        public String getMessage() {
            return new StringBuilder().append("!玩家聊天  - 开关玩家聊天输出").toString();
        }
    }

    public static class 交易聊天 extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            ServerConstants.message_tradechat = !ServerConstants.message_tradechat;
            c.getPlayer().dropMessage(0, "[交易聊天输出] " + (ServerConstants.message_tradechat ? "开启" : "关闭"));
            System.out.println("[交易聊天输出] " + (ServerConstants.message_tradechat ? "开启" : "关闭"));
            return true;
        }

        public String getMessage() {
            return new StringBuilder().append("!交易聊天  - 开关交易聊天输出").toString();
        }
    }
}
