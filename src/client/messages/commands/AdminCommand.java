package client.messages.commands;

import client.MapleCharacter;
import client.MapleClient;
import client.Skill;
import client.SkillFactory;
import client.inventory.Equip;
import client.inventory.Item;
import client.inventory.MapleInventory;
import client.inventory.MapleInventoryType;
import client.messages.CommandProcessorUtil;
import constants.GameConstants;
import constants.ServerConstants;
import constants.ServerConstants.PlayerGMRank;
import database.DBConPool;
import handling.channel.ChannelServer;
import handling.login.LoginServer;
import handling.world.World;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import scripting.NPCScriptManager;
import server.CashItemFactory;
import server.CashItemInfo;
import server.CashItemInfo.CashModInfo;
import server.MapleInventoryManipulator;
import server.MapleItemInformationProvider;
import server.ShutdownServer;
import server.Timer.EventTimer;
import server.gashapon.GashaponFactory;
import server.maps.MapleMap;
import tools.CPUSampler;
import tools.FileoutputUtil;
import tools.LoadPacket;
import tools.packet.MaplePacketCreator;

public class AdminCommand {

    public static PlayerGMRank getPlayerLevelRequired() {
        return PlayerGMRank.ADMIN;
    }

    public static class 商城打折 extends discounied {
    }

    public static class discounied extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            LoginServer.setDiscounied(!LoginServer.getDiscounied());
            c.getPlayer().dropMessage(0, "[商城打折] " + (LoginServer.getDiscounied() ? "开启" : "关闭"));
            System.out.println("[商城打折] " + (LoginServer.getDiscounied() ? "开启" : "关闭"));
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!商城打折  - 商城打折开关").toString();
        }
    }

    public static class onpacket extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            LoginServer.setLogPackets(!LoginServer.isLogPackets());
            c.getPlayer().dropMessage(0, "[封包输出] " + (LoginServer.isLogPackets() ? "开启" : "关闭"));
            System.out.println("[onpacket] " + (LoginServer.isLogPackets() ? "开启" : "关闭"));
            return true;
        }

        public String getMessage() {
            return new StringBuilder().append("!onpacket  - 输出游戏封包").toString();
        }
    }

    public static class ProItem extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String[] splitted) {
            if (splitted.length < 3) {
                return false;
            }
            int itemId = 0;
            int quantity = 1;
            int Str = 0;
            int Dex = 0;
            int Int = 0;
            int Luk = 0;
            int HP = 0;
            int MP = 0;
            int Watk = 0;
            int Matk = 0;
            int Wdef = 0;
            int Mdef = 0;
            int Scroll = 0;
            int Upg = 0;
            int Acc = 0;
            int Avoid = 0;
            int jump = 0;
            int speed = 0;
            int day = 0;
            try {
                int splitted_count = 1;
                itemId = Integer.parseInt(splitted[splitted_count++]);
                Str = Integer.parseInt(splitted[splitted_count++]);
                Dex = Integer.parseInt(splitted[splitted_count++]);
                Int = Integer.parseInt(splitted[splitted_count++]);
                Luk = Integer.parseInt(splitted[splitted_count++]);
                HP = Integer.parseInt(splitted[splitted_count++]);
                MP = Integer.parseInt(splitted[splitted_count++]);
                Watk = Integer.parseInt(splitted[splitted_count++]);
                Matk = Integer.parseInt(splitted[splitted_count++]);
                Wdef = Integer.parseInt(splitted[splitted_count++]);
                Mdef = Integer.parseInt(splitted[splitted_count++]);
                Upg = Integer.parseInt(splitted[splitted_count++]);
                Acc = Integer.parseInt(splitted[splitted_count++]);
                Avoid = Integer.parseInt(splitted[splitted_count++]);
                speed = Integer.parseInt(splitted[splitted_count++]);
                jump = Integer.parseInt(splitted[splitted_count++]);
                Scroll = Integer.parseInt(splitted[splitted_count++]);
                day = Integer.parseInt(splitted[splitted_count++]);
            } catch (Exception ex) {
                //   ex.printStackTrace();
            }
            boolean Str_check = Str != 0;
            boolean Int_check = Int != 0;
            boolean Dex_check = Dex != 0;
            boolean Luk_check = Luk != 0;
            boolean HP_check = HP != 0;
            boolean MP_check = MP != 0;
            boolean WATK_check = Watk != 0;
            boolean MATK_check = Matk != 0;
            boolean WDEF_check = Wdef != 0;
            boolean MDEF_check = Mdef != 0;
            boolean SCROLL_check = true;
            boolean UPG_check = Upg != 0;
            boolean ACC_check = Acc != 0;
            boolean AVOID_check = Avoid != 0;
            boolean JUMP_check = jump != 0;
            boolean SPEED_check = speed != 0;
            boolean DAY_check = day != 0;
            MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
            if (GameConstants.isPet(itemId)) {
                c.getPlayer().dropMessage(5, "请从商城购买宠物.");
                return true;
            } else if (!ii.itemExists(itemId)) {
                c.getPlayer().dropMessage(5, itemId + " 不存在");
                return true;
            }
            Item toDrop;
            Equip equip;
            if (GameConstants.getInventoryType(itemId) == MapleInventoryType.EQUIP) {// 如果道具为装备
                equip = ii.randomizeStats((Equip) ii.getEquipById(itemId));
                equip.setGMLog(c.getPlayer().getName() + " 使用 !Proitem");
                if (Str_check) {
                    equip.setStr((short) Str);
                }
                if (Luk_check) {
                    equip.setLuk((short) Luk);
                }
                if (Dex_check) {
                    equip.setDex((short) Dex);
                }
                if (Int_check) {
                    equip.setInt((short) Int);
                }
                if (HP_check) {
                    equip.setHp((short) HP);
                }
                if (MP_check) {
                    equip.setMp((short) MP);
                }
                if (WATK_check) {
                    equip.setWatk((short) Watk);
                }
                if (MATK_check) {
                    equip.setMatk((short) Matk);
                }
                if (WDEF_check) {
                    equip.setWdef((short) Wdef);
                }
                if (MDEF_check) {
                    equip.setMdef((short) Mdef);
                }
                if (ACC_check) {
                    equip.setAcc((short) Acc);
                }
                if (AVOID_check) {
                    equip.setAvoid((short) Avoid);
                }
                if (SCROLL_check) {
                    equip.setUpgradeSlots((byte) Scroll);
                }
                if (UPG_check) {
                    equip.setLevel((byte) Upg);
                }
                if (JUMP_check) {
                    equip.setJump((short) jump);
                }
                if (SPEED_check) {
                    equip.setSpeed((short) speed);
                }
                if (DAY_check) {
                    equip.setExpiration(System.currentTimeMillis() + (day * 24 * 60 * 60 * 1000));
                }
                MapleInventoryManipulator.addbyItem(c, equip);
            } else {
                toDrop = new client.inventory.Item(itemId, (byte) 0, (short) quantity, (byte) 0);
                toDrop.setGMLog(c.getPlayer().getName() + " 使用 !ProItem");
                MapleInventoryManipulator.addbyItem(c, toDrop);
            }
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!ProItem <物品代码> (<力量> <敏捷> <智力> <幸运> <HP> <MP> <物攻> <魔攻> <物防> <魔防> <武器+x> <命中> <迴避> <移动> <跳跃> <衝捲数> <天数>)").toString();
        }
    }

    public static class 文件封包 extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String[] splitted) {
            c.getSession().write(LoadPacket.getPacket());
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!文件封包 - 文件封包").toString();
        }
    }

    public static class CloseAllMerchant extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String[] splitted) {
            int ret = 0;
            for (ChannelServer cs : ChannelServer.getAllInstances()) {
                ret += cs.closeAllMerchant();
            }
            c.getPlayer().dropMessage(6, "Hired merchants saved: " + ret);
            System.out.println("Hired merchants saved: " + ret);
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!CloseAllMerchant - 关闭所有精灵商店").toString();
        }
    }

    public static class Shutdown extends CommandExecute {

        protected static Thread t = null;

        @Override
        public boolean execute(MapleClient c, String[] splitted) {
            c.getPlayer().dropMessage(6, "关闭服务器中......");
            if (t == null || !t.isAlive()) {
                t = new Thread(ShutdownServer.getInstance());
                ShutdownServer.getInstance().shutdown();
                t.start();
            } else {
                c.getPlayer().dropMessage(6, "已在执行中.");
            }
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!shutdown - 关闭服务器").toString();
        }
    }

    public static class ShutdownTime extends Shutdown {

        private static ScheduledFuture<?> ts = null;
        private int minutesLeft = 0;

        @Override
        public boolean execute(MapleClient c, String[] splitted) {
            minutesLeft = Integer.parseInt(splitted[1]);
            c.getPlayer().dropMessage(6, "服务器将在 " + minutesLeft + " 分钟后关闭. 请尽速关闭精灵商人 并下线.");
            World.isShopShutDown = true;

            if (ts == null && (t == null || !t.isAlive())) {
                t = new Thread(ShutdownServer.getInstance());
                ts = EventTimer.getInstance().register(new Runnable() {

                    public void run() {
                        if (minutesLeft == 0) {
                            ShutdownServer.getInstance().shutdown();
                            t.start();
                            ts.cancel(false);
                            return;
                        }
                        World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(0, "服务器将于 " + minutesLeft + " 分钟后关机."));
                        minutesLeft--;
                    }
                }, 60000);
            } else {
                c.getPlayer().dropMessage(6, "A shutdown thread is already in progress or shutdown has not been done. Please wait.");
            }
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!shutdowntime <分钟数> - 关闭服务器").toString();
        }
    }

    public static class 测谎所有人 extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            int ret = 0;
            for (ChannelServer cserv : ChannelServer.getAllInstances()) {
                for (MapleCharacter mch : cserv.getPlayerStorage().getAllCharacters()) {
                    mch.startLieDetector(false);
                    ret++;
                }
            }

            c.getPlayer().dropMessage(6, new StringBuilder().append("命令使用成功，当前共有: ").append(ret).append(" 个玩家被测谎").toString());
            return true;

        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!测谎所有人 - [测谎所有人]").toString();
        }
    }

    public static class getAcLogS extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            if (splitted.length < 3) {
                return false;
            }

            String neirong = splitted[1];
            String name = splitted[2];

            byte ret;
            ret = MapleClient.getAcLogS(name, neirong);

            if (ret == -2) {
                c.getPlayer().dropMessage(6, "[" + "getAcLogS" + "] SQL 错误");
            } else if (ret == -1) {
                c.getPlayer().dropMessage(6, "[" + "getAcLogS" + "] 目标玩家不存在");
            } else {
                c.getPlayer().dropMessage(6, "[" + "getAcLogS" + "] 成功给予FB积分");
            }

            c.getPlayer().dropMessage(6, "已经给予帐号[" + name + "] 写入getAcLogS" + neirong);
            FileoutputUtil.logToFile("logs/Data/getAcLogS.txt", "\r\n " + FileoutputUtil.NowTime() + " GM " + c.getPlayer().getName() + " 已经给予帐号[" + name + "] 写入getAcLogS" + neirong);

            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!getAcLogS 内容 帐号").toString();
        }
    }

    public static class getBossLogS extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            if (splitted.length < 3) {
                return false;
            }

            String neirong = splitted[1];
            String name = splitted[2];
            int num = Math.min(CommandProcessorUtil.getOptionalIntArg(splitted, 3, 1), 100);
            if (num > 100) {
                num = 100;
            }
            byte ret = -2;

            for (int i = 0; i < num; i++) {
                ret = MapleClient.getBossLogS(name, neirong);
            }
            if (ret == -2) {
                c.getPlayer().dropMessage(6, "[" + "getBossLogS" + "] SQL 错误");
            } else if (ret == -1) {
                c.getPlayer().dropMessage(6, "[" + "getBossLogS" + "] 目标玩家不存在");
            } else {
                c.getPlayer().dropMessage(6, "[" + "getBossLogS" + "] 成功给予FB积分");
            }

            c.getPlayer().dropMessage(6, "已经给予角色[" + name + "] 写入getBossLogS" + neirong);
            FileoutputUtil.logToFile("logs/Data/getBossLogS.txt", "\r\n " + FileoutputUtil.NowTime() + " GM " + c.getPlayer().getName() + " 已经给予角色[" + name + "] 写入getBossLogS" + neirong);

            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!getAcLogS 内容 帐号 写入数量").toString();
        }
    }

    public static class ReloadGashapon extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            GashaponFactory.getInstance().reloadGashapons();
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!reloadGashapon - 重新载入转蛋机").toString();
        }
    }

    public static class 怪物爆率 extends CommandExecute {

        public boolean execute(MapleClient c, String[] splitted) {
            c.removeClickedNPC();
            NPCScriptManager.getInstance().start(c, 9010000, "怪物掉宝查询");
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!怪物爆率 - 怪物掉宝查询").toString();
        }
    }

    public static class Itemmap extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            if (splitted.length < 2) {
                return false;
            }
            int item = Integer.parseInt(splitted[1]);
            int amount = (int) CommandProcessorUtil.getOptionalIntArg(splitted, 2, 1);
            for (MapleCharacter map : c.getPlayer().getMap().getCharactersThreadsafe()) {
                map.gainItem(item, amount);
            }

            String msg = "[GM 聊天] GM " + c.getPlayer().getName() + " 给了当前地图玩家  " + item + amount + "个";
            FileoutputUtil.logToFile("logs/GM_LOG/给全地图物品.txt", "\r\n " + FileoutputUtil.NowTime() + " GM " + c.getPlayer().getName() + " 给了 当前地图:" + c.getPlayer().getMapId() + " 玩家 物品: " + MapleItemInformationProvider.getInstance().getName(item) + " " + amount + "个");
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!Itemmap <道具> <数量> - 给所有玩家道具").toString();
        }
    }

    public static class ItemEveryone extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            if (splitted.length < 2) {
                return false;
            }
            int item = Integer.parseInt(splitted[1]);
            int amount = (int) CommandProcessorUtil.getOptionalIntArg(splitted, 2, 1);
            for (ChannelServer cserv : ChannelServer.getAllInstances()) {
                for (MapleCharacter mch : cserv.getPlayerStorage().getAllCharacters()) {
                    mch.gainItem(item, amount);
                }
            }
            World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[奖励公告]: " + c.getPlayer().getName() + " 给了 所有玩家  " + item + " " + amount + "个"));
//            String msg = "[GM 聊天] GM " + c.getPlayer().getName() + " 给了 所有玩家  " + item + amount + "个";
            FileoutputUtil.logToFile("logs/GM_LOG/给全服物品.txt", "\r\n " + FileoutputUtil.NowTime() + " GM " + c.getPlayer().getName() + " 给了 所有玩家 物品: " + MapleItemInformationProvider.getInstance().getName(item) + " " + amount + "个");
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!itemeveryone <道具> <数量> - 给所有玩家道具").toString();
        }
    }

    /*public static class CashShopModifiedItems extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {

            try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
                PreparedStatement ps = con.prepareStatement("SELECT * FROM cashshop_modified_items");
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int sn = rs.getInt("serial");
                    final CashItemInfo itemsn = CashItemFactory.getInstance().getItem(sn);
                    int itemid = itemsn.getId();
                    if (itemid / 100000 >= 10 && itemid / 100000 <= 17) {
                        try (Connection con1 = DBConPool.getInstance().getDataSource().getConnection()) {
                            PreparedStatement ps1;
                            ps1 = con1.prepareStatement("Update cashshop_modified_items set discount_price = ? Where serial = ?");
                            ps1.setInt(1, 100);
                            ps1.setInt(2, sn);
                            ps1.execute();
                            ps1.close();
                            System.out.println("sn:" + sn + " item:" + itemid);
                        } catch (SQLException exw) {
                            FileoutputUtil.outputFileError("logs/资料库异常.txt", exw);
                            exw.printStackTrace();
                        }
                    }
                      
                }
                 System.out.println("完成。");
            } catch (SQLException ex) {
                FileoutputUtil.outputFileError("logs/资料库异常.txt", ex);
                ex.printStackTrace();
            }
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!CashShopModifiedItems - 修改商城价格").toString();
        }
    }*/

 /*public static class StripEveryone extends CommandExecute {

        @Override
        public int execute(MapleClient c, String[] splitted) {
            ChannelServer cs = c.getChannelServer();
            for (MapleCharacter mchr : cs.getPlayerStorage().getAllCharacters()) {
                if (mchr.isGM()) {
                    continue;
                }
                MapleInventory equipped = mchr.getInventory(MapleInventoryType.EQUIPPED);
                MapleInventory equip = mchr.getInventory(MapleInventoryType.EQUIP);
                List<Short> ids = new ArrayList<Short>();
                for (Item item : equipped.newList()) {
                    ids.add(item.getPosition());
                }
                for (short id : ids) {
                    MapleInventoryManipulator.unequip(mchr.getClient(), id, equip.getNextFreeSlot());
                }
            }
            return 1;
        }
    }

    public static class MesoEveryone extends CommandExecute {

        @Override
        public int execute(MapleClient c, String[] splitted) {
            for (ChannelServer cserv : ChannelServer.getAllInstances()) {
                for (MapleCharacter mch : cserv.getPlayerStorage().getAllCharacters()) {
                    mch.gainMeso(Integer.parseInt(splitted[1]), true);
                }
            }
            return 1;
        }
    }

    public static class ExpRate extends CommandExecute {

        @Override
        public int execute(MapleClient c, String[] splitted) {
            if (splitted.length > 1) {
                final int rate = Integer.parseInt(splitted[1]);
                if (splitted.length > 2 && splitted[2].equalsIgnoreCase("all")) {
                    for (ChannelServer cserv : ChannelServer.getAllInstances()) {
                        cserv.setExpRate(rate);
                    }
                } else {
                    c.getChannelServer().setExpRate(rate);
                }
                c.getPlayer().dropMessage(6, "Exprate has been changed to " + rate + "x");
            } else {
                c.getPlayer().dropMessage(6, "Syntax: !exprate <number> [all]");
            }
            return 1;
        }
    }

    public static class MesoRate extends CommandExecute {

        @Override
        public int execute(MapleClient c, String[] splitted) {
            if (splitted.length > 1) {
                final int rate = Integer.parseInt(splitted[1]);
                if (splitted.length > 2 && splitted[2].equalsIgnoreCase("all")) {
                    for (ChannelServer cserv : ChannelServer.getAllInstances()) {
                        cserv.setMesoRate(rate);
                    }
                } else {
                    c.getChannelServer().setMesoRate(rate);
                }
                c.getPlayer().dropMessage(6, "Meso Rate has been changed to " + rate + "x");
            } else {
                c.getPlayer().dropMessage(6, "Syntax: !mesorate <number> [all]");
            }
            return 1;
        }
    }

    public static class DCAll extends CommandExecute {

        @Override
        public int execute(MapleClient c, String[] splitted) {
            int range = -1;
            if (splitted[1].equals("m")) {
                range = 0;
            } else if (splitted[1].equals("c")) {
                range = 1;
            } else if (splitted[1].equals("w")) {
                range = 2;
            }
            if (range == -1) {
                range = 1;
            }
            if (range == 0) {
                c.getPlayer().getMap().disconnectAll();
            } else if (range == 1) {
                c.getChannelServer().getPlayerStorage().disconnectAll(true);
            } else if (range == 2) {
                for (ChannelServer cserv : ChannelServer.getAllInstances()) {
                    cserv.getPlayerStorage().disconnectAll(true);
                }
            }
            return 1;
        }
    }

    public static class Shutdown extends CommandExecute {

        protected static Thread t = null;

        @Override
        public int execute(MapleClient c, String[] splitted) {
            c.getPlayer().dropMessage(6, "Shutting down...");
            if (t == null || !t.isAlive()) {
                t = new Thread(ShutdownServer.getInstance());
                ShutdownServer.getInstance().shutdown();
                t.start();
            } else {
                c.getPlayer().dropMessage(6, "A shutdown thread is already in progress or shutdown has not been done. Please wait.");
            }
            return 1;
        }
    }

    public static class ShutdownTime extends Shutdown {

        private static ScheduledFuture<?> ts = null;
        private int minutesLeft = 0;

        @Override
        public int execute(MapleClient c, String[] splitted) {
            minutesLeft = Integer.parseInt(splitted[1]);
            c.getPlayer().dropMessage(6, "Shutting down... in " + minutesLeft + " minutes");
            if (ts == null && (t == null || !t.isAlive())) {
                t = new Thread(ShutdownServer.getInstance());
                ts = EventTimer.getInstance().register(new Runnable() {

                    public void run() {
                        if (minutesLeft == 0) {
                            ShutdownServer.getInstance().shutdown();
                            t.start();
                            ts.cancel(false);
                            return;
                        }
                        World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(0, "The server will shutdown in " + minutesLeft + " minutes. Please log off safely."));
                        minutesLeft--;
                    }
                }, 60000);
            } else {
                c.getPlayer().dropMessage(6, "A shutdown thread is already in progress or shutdown has not been done. Please wait.");
            }
            return 1;
        }
    }

    public static class StartProfiling extends CommandExecute {

        @Override
        public int execute(MapleClient c, String[] splitted) {
            CPUSampler sampler = CPUSampler.getInstance();
            sampler.addIncluded("client");
            sampler.addIncluded("constants"); //or should we do Packages.constants etc.?
            sampler.addIncluded("database");
            sampler.addIncluded("handling");
            sampler.addIncluded("provider");
            sampler.addIncluded("scripting");
            sampler.addIncluded("server");
            sampler.addIncluded("tools");
            sampler.start();
            return 1;
        }
    }

    public static class StopProfiling extends CommandExecute {

        @Override
        public int execute(MapleClient c, String[] splitted) {
            CPUSampler sampler = CPUSampler.getInstance();
            try {
                String filename = "odinprofile.txt";
                if (splitted.length > 1) {
                    filename = splitted[1];
                }
                File file = new File(filename);
                if (file.exists()) {
                    c.getPlayer().dropMessage(6, "The entered filename already exists, choose a different one");
                    return 0;
                }
                sampler.stop();
                FileWriter fw = new FileWriter(file);
                sampler.save(fw, 1, 10);
                fw.close();
            } catch (IOException e) {
                System.err.println("Error saving profile" + e);
            }
            sampler.reset();
            return 1;
        }
    }
    public static class maxskills extends CommandExecute {

        @Override
        public int execute(MapleClient c, String splitted[]) {
            MapleCharacter player = c.getPlayer();
            for (Skill skil : SkillFactory.getAllSkills()) {
                if (GameConstants.isApplicableSkill(skil.getId()) && skil.canBeLearnedBy(player.getJob())) { //no db/additionals/resistance skills
                    player.changeSkillLevel(SkillFactory.getSkill(skil.getId()), skil.getMaxLevel(), (byte) skil.getMaxLevel());
                }
            }
            return 1;
        }
    }*/
}
