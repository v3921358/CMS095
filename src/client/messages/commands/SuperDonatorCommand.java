package client.messages.commands;

import client.MapleCharacter;
import client.MapleCharacterUtil;
import client.MapleClient;
import constants.ServerConstants;
import constants.ServerConstants.PlayerGMRank;
import database.DBConPool;
import handling.cashshop.CashShopServer;
import handling.channel.ChannelServer;
import handling.world.World;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import server.maps.MapleMap;
import tools.FileoutputUtil;
import tools.StringUtil;
import tools.packet.MaplePacketCreator;

public class SuperDonatorCommand {

    public static PlayerGMRank getPlayerLevelRequired() {
        return PlayerGMRank.SUPERDONATOR;
    }

    public static class Ban extends CommandExecute {

        protected boolean hellban = false;

        private String getCommand() {
            return "Ban";
        }

        @Override
        public boolean execute(MapleClient c, String[] splitted) {
            if (splitted.length < 2) {
                return false;
            }
            StringBuilder sb = new StringBuilder(c.getPlayer().getName());
            sb.append(" 封锁 ").append(splitted[1]).append(": ").append(StringUtil.joinStringFrom(splitted, 2));
            boolean offline = false;
            MapleCharacter target = null;
            String name = "";
            String input = "null";
            try {
                name = splitted[1];
                input = splitted[2];
            } catch (Exception ex) {

            }
            int ch = World.Find.findChannel(name);
            if (ch >= 1) {
                target = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(name);
            } else {
                target = CashShopServer.getPlayerStorage().getCharacterByName(name);
            }
            if (target == null) {
                if (c.getPlayer().OfflineBanByName(name, sb.toString())) {
                    c.getPlayer().dropMessage(6, "[" + getCommand() + "] 成功离线封锁 " + splitted[1] + ".");
                    offline = true;
                } else {
                    c.getPlayer().dropMessage(6, "[" + getCommand() + "] 封锁失败 " + splitted[1]);
                    return true;
                }
            } else if (Ban(c, target, sb) != 1) {
                return true;
            }
            FileoutputUtil.logToFile("logs/Hack/指令封锁名单.txt", "\r\n " + FileoutputUtil.NowTime() + " " + c.getPlayer().getName() + " 封锁了 " + splitted[1] + " 原因: " + sb.toString() + " 是否离线封锁: " + offline);
            String reason = "null".equals(input) ? "使用违法程式练功" : StringUtil.joinStringFrom(splitted, 2);
            World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[封锁系统] " + splitted[1] + " 因为" + reason + "而被管理员永久停权。"));

            String msg = "[GM 聊天] GM " + c.getPlayer().getName() + "  封锁了 " + splitted[1] + " 是否离线封锁 " + offline + " 原因：" + reason;
            World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, msg));
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append(ServerConstants.PlayerGMRank.SUPERDONATOR.getCommandPrefix()).append("ban <玩家> <原因> - 封锁玩家").toString();
        }

        public int Ban(MapleClient c, MapleCharacter target, StringBuilder sb) {
            if (c.getPlayer().getGMLevel() >= target.getGMLevel()) {
                sb.append(" (IP: ").append(target.getClient().getSessionIPAddress()).append(")");
                if (target.ban(sb.toString(), c.getPlayer().hasGmLevel(5), false, hellban)) {
                    target.getClient().getSession().close();
                    target.getClient().disconnect(true, target.getClient().getChannel() == -10);
                    c.getPlayer().dropMessage(6, "[" + getCommand() + "] 成功封锁 " + target.getName() + ".");
                    return 1;
                } else {
                    c.getPlayer().dropMessage(6, "[" + getCommand() + "] 封锁失败.");
                    return 0;
                }
            } else {
                c.getPlayer().dropMessage(6, "[" + getCommand() + "] 无法封锁GMs...");
                return 0;
            }
        }
    }

    public static class BanIP extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String[] splitted) {
            if (splitted.length < 2) {
                return false;
            }
            boolean error = false;
            String IP = splitted[1];
            if (!IP.contains("/") || !IP.contains(".")) {
                c.getPlayer().dropMessage(6, "输入IP必须包含 '/' 以及 '.' 例如: !banIP /127.0.0.1");
                return true;
            }
            try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
                PreparedStatement ps;
                ps = con.prepareStatement("INSERT INTO ipbans (ip) VALUES (?)");
                ps.setString(1, IP);
                ps.executeUpdate();
                ps.close();
            } catch (SQLException ex) {
                error = true;
                FileoutputUtil.outputFileError("logs/资料库异常.txt", ex);
            }
            try {
                for (ChannelServer cs : ChannelServer.getAllInstances()) {
                    for (MapleCharacter chr : cs.getPlayerStorage().getAllCharacters()) {
                        if (chr.getClient().getSessionIPAddress().equals(IP)) {
                            if (!chr.getClient().isGm()) {
                                chr.getClient().disconnect(true, chr.getClient().getChannel() == -10);
                                chr.getClient().getSession().close();
                            }
                        }
                    }
                }
            } catch (Exception ex) {

            }
            c.getPlayer().dropMessage(6, "封锁IP [" + IP + "] " + (error ? "成功 " : "失败"));
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append(ServerConstants.PlayerGMRank.SUPERDONATOR.getCommandPrefix()).append("BanIP <IP> - 封锁IP ").toString();
        }
    }

    public static class Notice extends CommandExecute {

        private static int getNoticeType(String typestring) {
            switch (typestring) {
                case "n":
                    return 0;
                case "p":
                    return 1;
                case "l":
                    return 2;
                case "nv":
                    return 5;
                case "v":
                    return 5;
                case "b":
                    return 6;
            }
            return -1;
        }

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            int joinmod = 1;
            int range = -1;
            if (splitted.length < 2) {
                return false;
            }
            switch (splitted[1]) {
                case "m":
                    range = 0;
                    break;
                case "c":
                    range = 1;
                    break;
                case "w":
                    range = 2;
                    break;
            }

            int tfrom = 2;
            if (range == -1) {
                range = 2;
                tfrom = 1;
            }
            if (splitted.length < tfrom + 1) {
                return false;
            }
            int type = getNoticeType(splitted[tfrom]);
            if (type == -1) {
                type = 0;
                joinmod = 0;
            }
            StringBuilder sb = new StringBuilder();
            if (splitted[tfrom].equals("nv")) {
                sb.append("[Notice]");
            } else {
                sb.append("");
            }
            joinmod += tfrom;
            if (splitted.length < joinmod + 1) {
                return false;
            }
            sb.append(StringUtil.joinStringFrom(splitted, joinmod));

            byte[] packet = MaplePacketCreator.serverNotice(type, sb.toString());
            if (range == 0) {
                c.getPlayer().getMap().broadcastMessage(packet);
            } else if (range == 1) {
                ChannelServer.getInstance(c.getChannel()).broadcastPacket(packet);
            } else if (range == 2) {
                World.Broadcast.broadcastMessage(packet);
            }
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!notice <n|p|l|nv|v|b> <m|c|w> <message> - 公告").toString();
        }
    }

    public static class Say extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            if (splitted.length > 1) {
                StringBuilder sb = new StringBuilder();
                sb.append("[");
                sb.append(c.getPlayer().getName());
                sb.append("] ");
                sb.append(StringUtil.joinStringFrom(splitted, 1));
                World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, sb.toString()));
            } else {
                return false;
            }
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!say 讯息 - 服务器公告").toString();
        }
    }

    public static class UnBan extends CommandExecute {

        protected boolean hellban = false;

        private String getCommand() {
            return "UnBan";
        }

        @Override
        public boolean execute(MapleClient c, String[] splitted) {
            if (splitted.length < 2) {
                return false;
            }
            byte ret;
            if (hellban) {
                ret = MapleClient.unHellban(splitted[1]);
            } else {
                ret = MapleClient.unban(splitted[1]);
            }
            if (ret == -2) {
                c.getPlayer().dropMessage(6, "[" + getCommand() + "] SQL 错误");
            } else if (ret == -1) {
                c.getPlayer().dropMessage(6, "[" + getCommand() + "] 目标玩家不存在");
            } else {
                c.getPlayer().dropMessage(6, "[" + getCommand() + "] 成功解除锁定");
            }
            byte ret_ = MapleClient.unbanIPMacs(splitted[1]);
            if (ret_ == -2) {
                c.getPlayer().dropMessage(6, "[" + getCommand() + "] SQL 错误.");
            } else if (ret_ == -1) {
                c.getPlayer().dropMessage(6, "[" + getCommand() + "] 角色不存在.");
            } else if (ret_ == 0) {
                c.getPlayer().dropMessage(6, "[" + getCommand() + "] No IP or Mac with that character exists!");
            } else if (ret_ == 1) {
                c.getPlayer().dropMessage(6, "[" + getCommand() + "] IP或Mac已解锁其中一个.");
            } else if (ret_ == 2) {
                c.getPlayer().dropMessage(6, "[" + getCommand() + "] IP以及Mac已成功解锁.");
            }
            if (ret_ == 1 || ret_ == 2) {
                FileoutputUtil.logToFile("logs/Hack/ban/解除封锁名单.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSessionIPAddress() + " " + c.getPlayer().getName() + " 解锁了 " + splitted[1]
                );
            }
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!unban <玩家> - 解锁玩家").toString();
        }
    }

    public static class UnbanIP extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            if (splitted.length < 2) {
                return false;
            }
            byte ret_ = MapleClient.unbanIP(splitted[1]);
            if (ret_ == -2) {
                c.getPlayer().dropMessage(6, "[unbanip] SQL 错误.");
            } else if (ret_ == -1) {
                c.getPlayer().dropMessage(6, "[unbanip] 角色不存在.");
            } else if (ret_ == 0) {
                c.getPlayer().dropMessage(6, "[unbanip] No IP or Mac with that character exists!");
            } else if (ret_ == 1) {
                c.getPlayer().dropMessage(6, "[unbanip] IP或Mac已解锁其中一个.");
            } else if (ret_ == 2) {
                c.getPlayer().dropMessage(6, "[unbanip] IP以及Mac已成功解锁.");
            }
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!unbanip <玩家名称> - 解锁玩家").toString();
        }
    }

    public static class 传送 extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String[] splitted) {
            if (splitted.length < 2) {
                return false;
            }
            String input = "";
            try {
                input = splitted[1];
            } catch (Exception ex) {
            }
            MapleCharacter victim = c.getChannelServer().getPlayerStorage().getCharacterByName(input);

            if (victim != null) {
                if (splitted.length == 2) {
                    c.getPlayer().changeMap(victim.getMap(), victim.getMap().findClosestSpawnpoint(victim.getPosition()));
                } else {
                    MapleMap target = null;
                    try {
                        target = ChannelServer.getInstance(c.getChannel()).getMapFactory().getMap(Integer.parseInt(splitted[2]));
                    } catch (Exception ex) {

                    }
                    if (target == null) {
                        c.getPlayer().dropMessage(6, "地图不存在");
                    } else {
                        victim.changeMap(target, target.getPortal(0));
                    }

                }
            } else {
                int ch = World.Find.findChannel(input);
                if (ch < 0) {
                    Integer map = null;
                    MapleMap target = null;
                    try {
                        map = Integer.parseInt(input);
                        target = c.getChannelServer().getMapFactory().getMap(map);
                    } catch (Exception ex) {
                        if (map == null || target == null) {
                            c.getPlayer().dropMessage(6, "地图不存在");
                            return true;
                        }
                    }
                    if (target == null) {
                        c.getPlayer().dropMessage(6, "地图不存在");
                    } else {
                        c.getPlayer().changeMap(target, target.getPortal(0));
                    }
                } else {
                    victim = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(input);
                    if (victim != null) {
                        if (victim.getMapId() != c.getPlayer().getMapId()) {
                            final MapleMap mapp = c.getChannelServer().getMapFactory().getMap(victim.getMapId());
                            c.getPlayer().changeMap(mapp, mapp.getPortal(0));
                        }
                        c.getPlayer().dropMessage(6, "正在改变频道请等待");
                        c.getPlayer().changeChannel(ch);

                    } else {
                        c.getPlayer().dropMessage(6, "角色不存在");
                    }
                }

            }
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!传送 [玩家名称] <地图ID> - 移动到某个地图或某个玩家所在的地方").toString();
        }
    }

    public static class WarpHere extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            if (splitted.length < 2) {
                return false;
            }
            MapleCharacter victim = c.getChannelServer().getPlayerStorage().getCharacterByName(splitted[1]);
            if (victim != null) {
                victim.changeMap(c.getPlayer().getMap(), c.getPlayer().getMap().findClosestSpawnpoint(c.getPlayer().getPosition()));
            } else {
                int ch = World.Find.findChannel(splitted[1]);
                if (ch < 0) {
                    c.getPlayer().dropMessage(5, "找不到");

                } else {
                    victim = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(splitted[1]);
                    c.getPlayer().dropMessage(5, "正在把玩家传到这来");
                    victim.dropMessage(5, "正在传送到GM那边");
                    if (victim.getMapId() != c.getPlayer().getMapId()) {
                        final MapleMap mapp = victim.getClient().getChannelServer().getMapFactory().getMap(c.getPlayer().getMapId());
                        victim.changeMap(mapp, mapp.getPortal(0));
                    }
                    victim.changeChannel(c.getChannel());
                }
            }
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!warphere 把玩家传送到这里").toString();
        }
    }

    public static class WarpID extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String[] splitted) {
            if (splitted.length < 2) {
                return false;
            }
            int input = 0;
            try {
                input = Integer.parseInt(splitted[1]);
            } catch (Exception ex) {

            }
            int ch = World.Find.findChannel(input);
            if (ch < 0) {
                c.getPlayer().dropMessage(6, "玩家编号[" + input + "] 不在线上");
                return true;
            }
            MapleCharacter victim = c.getChannelServer().getPlayerStorage().getCharacterById(input);
            if (victim != null) {
                if (splitted.length == 2) {
                    c.getPlayer().changeMap(victim.getMap(), victim.getMap().findClosestSpawnpoint(victim.getPosition()));
                } else {
                    MapleMap target = ChannelServer.getInstance(c.getChannel()).getMapFactory().getMap(Integer.parseInt(splitted[2]));
                    if (target == null) {
                        c.getPlayer().dropMessage(6, "地图不存在");
                    } else {
                        victim.changeMap(target, target.getPortal(0));
                    }
                }
            } else {
                try {
                    victim = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterById(Integer.parseInt(splitted[1]));
                    if (victim != null) {
                        if (victim.getMapId() != c.getPlayer().getMapId()) {
                            final MapleMap mapp = c.getChannelServer().getMapFactory().getMap(victim.getMapId());
                            c.getPlayer().changeMap(mapp, mapp.getPortal(0));
                        }
                        c.getPlayer().dropMessage(6, "正在改变频道请等待");
                        c.getPlayer().changeChannel(ch);

                    } else {
                        c.getPlayer().dropMessage(6, "角色不存在");
                    }

                } catch (Exception e) {
                    c.getPlayer().dropMessage(6, "出问题了 " + e.getMessage());
                }
            }
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!warpID [玩家编号] - 移动到某个玩家所在的地方").toString();
        }
    }

    public static class WarpT extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String[] splitted) {
            if (splitted.length < 2) {
                return false;
            }
            List<MapleCharacter> chrs = new LinkedList<>();
            String input = splitted[1].toLowerCase();
            MapleCharacter smart_victim = null;
            StringBuilder sb = new StringBuilder();
            for (ChannelServer cserv : ChannelServer.getAllInstances()) {
                for (MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                    String name = chr.getName().toLowerCase();
                    if (name.contains(input)) {
                        if (smart_victim == null) {
                            smart_victim = chr;
                        }
                        chrs.add(chr);
                    }
                }
            }
            if (chrs.size() > 1) {
                sb.append("寻找到的玩家共").append(chrs.size()).append("位 名单如下 : ");
                c.getPlayer().dropMessage(5, sb.toString());
                for (MapleCharacter list : chrs) {
                    c.getPlayer().dropMessage(5, "频道" + list.getClient().getChannel() + ": " + list.getName() + "(" + list.getId() + ") -- " + list.getMapId() + "(" + list.getMap().getMapName() + ")");
                }
                return true;
            } else if (chrs.isEmpty()) {
                c.getPlayer().dropMessage(6, "没有搜寻到名称含有 '" + input + "' 的角色");
            } else if (smart_victim != null) {
                c.getPlayer().changeMap(smart_victim.getMap(), smart_victim.getMap().findClosestSpawnpoint(smart_victim.getTruePosition()));
            } else {
                c.getPlayer().dropMessage(6, "角色不存在或是不在线上");
            }
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!WarpT [玩家名称片段] - 移动到某个地图或某个玩家所在的地方").toString();
        }
    }

    public static class Whoshere extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String[] splitted) {
            StringBuilder builder = new StringBuilder("在地图上的玩家 : ").append(c.getPlayer().getMap().getCharactersThreadsafe().size()).append(", ");
            for (MapleCharacter chr : c.getPlayer().getMap().getCharactersThreadsafe()) {
                if (builder.length() > 150) { // wild guess :o
                    builder.setLength(builder.length() - 2);
                    c.getPlayer().dropMessage(6, builder.toString());
                    builder = new StringBuilder();
                }
                builder.append(MapleCharacterUtil.makeMapleReadable(chr.getName()));
                builder.append(", ");
            }
            builder.setLength(builder.length() - 2);
            c.getPlayer().dropMessage(6, builder.toString());
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!Whoshere - 查地图上玩家").toString();
        }
    }

    public static class Yellow extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            int range = -1;
            switch (splitted[1]) {
                case "m":
                    range = 0;
                    break;
                case "c":
                    range = 1;
                    break;
                case "w":
                    range = 2;
                    break;
            }
            if (range == -1) {
                range = 2;
            }
            byte[] packet = MaplePacketCreator.yellowChat((splitted[0].equals("!y") ? ("[" + c.getPlayer().getName() + "] ") : "") + StringUtil.joinStringFrom(splitted, 2));
            if (range == 0) {
                c.getPlayer().getMap().broadcastMessage(packet);
            } else if (range == 1) {
                ChannelServer.getInstance(c.getChannel()).broadcastPacket(packet);
            } else if (range == 2) {
                World.Broadcast.broadcastMessage(packet);
            }
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!yellow <m|c|w> <message> - 黄色公告").toString();
        }
    }

}
