package client.messages.commands;

import client.MapleCharacter;
import client.MapleCharacterUtil;
import client.MapleClient;
import client.MapleStat;
import client.Skill;
import client.SkillFactory;
import client.inventory.Equip;
import client.inventory.Item;
import client.inventory.ItemFlag;
import client.inventory.MapleInventory;
import client.inventory.MapleInventoryType;
import client.messages.CommandProcessorUtil;
//import client.messages.commands.InternCommand.Ban;
//import client.messages.commands.InternCommand.TempBan;
import constants.GameConstants;
import constants.ServerConstants;
import constants.ServerConstants.PlayerGMRank;
import constants.WorldConstants;
import database.DBConPool;
import handling.cashshop.CashShopServer;
import handling.channel.ChannelServer;
import handling.channel.handler.InventoryHandler;
import handling.login.LoginServer;
import handling.netty.MapleSession;
import handling.world.World;
import java.awt.Point;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import scripting.EventInstanceManager;
import scripting.EventManager;
import scripting.NPCScriptManager;
import server.*;
import server.events.MapleEvent;
import server.events.MapleEventType;
import server.life.MapleLifeFactory;
import server.life.MapleMonster;
import server.life.MapleNPC;
import server.life.OverrideMonsterStats;
import server.life.PlayerNPC;
import server.maps.MapleMap;
import server.maps.MapleMapItem;
import server.maps.MapleMapObject;
import server.maps.MapleMapObjectType;
import server.maps.MapleReactor;
import tools.FileoutputUtil;
import tools.MockIOSession;
import tools.packet.MaplePacketCreator;
import tools.StringUtil;
import tools.packet.MobPacket;

public class GMCommand {

    public static PlayerGMRank getPlayerLevelRequired() {
        return PlayerGMRank.GM;
    }

    public static class 禁止使用精灵商店 extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            World.isShopShutDown = !World.isShopShutDown;
            c.getPlayer().dropMessage(0, "[禁止使用精灵商店] " + (World.isShopShutDown ? "开启" : "关闭"));
            System.out.println("[禁止使用精灵商店] " + (World.isShopShutDown ? "开启" : "关闭"));
            return true;
        }

        public String getMessage() {
            return new StringBuilder().append("!禁止使用精灵商店  - 禁止使用精灵商店").toString();
        }
    }

    public static class RemoveDrops extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            c.getPlayer().dropMessage(5, "清除了 " + c.getPlayer().getMap().getNumItems() + " 个掉落物");
            c.getPlayer().getMap().removeDrops();
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!removedrops - 移除地上的物品").toString();

        }
    }

    public static class clonechr extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String[] splitted) {
            MapleCharacter player = c.getPlayer();
            MapleCharacter victim;
            if (splitted.length < 2) {
                return false;
            }
            String name = splitted[1];
            int ch = World.Find.findChannel(name);
            if (ch <= 0) {
                c.getPlayer().dropMessage(6, "玩家必须上线");
                return true;
            }
            victim = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(name);
            if (victim == null) {
                player.dropMessage(6, "找不到该玩家");
                return true;
            }
            MapleInventory equipped = c.getPlayer().getInventory(MapleInventoryType.EQUIPPED);
            MapleInventory equip = c.getPlayer().getInventory(MapleInventoryType.EQUIP);
            List<Short> ids = new LinkedList<>();
            for (Item item : equipped.list()) {
                ids.add(item.getPosition());
            }
            for (short id : ids) {
                MapleInventoryManipulator.unequip(c, id, equip.getNextFreeSlot());
            }
            c.getPlayer().clearSkills();
            c.getPlayer().setStr(victim.getStr());
            c.getPlayer().setDex(victim.getDex());
            c.getPlayer().setInt(victim.getInt());
            c.getPlayer().setLuk(victim.getLuk());

            c.getPlayer().setMeso(victim.getMeso());
            c.getPlayer().setLevel(victim.getLevel());
            c.getPlayer().changeJob(victim.getJob());

            c.getPlayer().setHp(victim.getHp());
            c.getPlayer().setMp(victim.getMp());
            c.getPlayer().setMaxHp(victim.getMaxHp());
            c.getPlayer().setMaxMp(victim.getMaxMp());

            String normal = victim.getName();
            String after = (normal + "x2");
            if (after.length() <= 12) {
                c.getPlayer().setName(victim.getName() + "x2");
            }
            c.getPlayer().setRemainingAp(victim.getRemainingAp());
            c.getPlayer().setRemainingSp(victim.getRemainingSp());
            c.getPlayer().LearnSameSkill(victim);

            c.getPlayer().setFame(victim.getFame());
            c.getPlayer().setHair(victim.getHair());
            c.getPlayer().setFace(victim.getFace());

            c.getPlayer().setSkinColor(victim.getSkinColor() == 0 ? c.getPlayer().getSkinColor() : victim.getSkinColor());

            c.getPlayer().setGender(victim.getGender());

            for (Item ii : victim.getInventory(MapleInventoryType.EQUIPPED).list()) {
                Item eq = ii.copy();
                eq.setPosition(eq.getPosition());
                eq.setQuantity((short) 1);
                c.getPlayer().forceReAddItem_NoUpdate(eq, MapleInventoryType.EQUIPPED);
            }
            c.getPlayer().fakeRelog();
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!clonechr 玩家名称 - 複制玩家").toString();
        }
    }

    public static class closemap extends CommandExecute {

        public boolean execute(MapleClient c, String[] splitted) {
            int mapid = 0;
            String input = null;
            MapleMap map = null;
            if (splitted.length < 2) {
                return false;
            }
            try {
                input = splitted[1];
                mapid = Integer.parseInt(input);
            } catch (Exception ex) {
            }
            if (c.getChannelServer().getMapFactory().getMap(mapid) == null) {
                c.getPlayer().dropMessage(6, "地图不存在");
                return true;
            }
            for (ChannelServer cserv : ChannelServer.getAllInstances()) {
                cserv.getMapFactory().destroyMap(mapid, true);
            }
            return true;

        }

        public String getMessage() {
            return new StringBuilder().append("!closemap - 关闭地图").toString();
        }
    }

    public static class 添加aclog extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String[] splitted) {
            if (splitted.length < 3) {
                return false;
            }
            String playername = splitted[1];
            String mod = splitted[2];
            int playerid = MapleCharacter.getCharacterIdByName(playername);
            if (playerid == -1) {
                c.getPlayer().dropMessage(6, "玩家[" + playername + "]不存在于资料库内。");
                return true;
            }
            MapleCharacter victim = MapleCharacter.getCharacterById(playerid);
            if (victim != null) {
                victim.setAcLog(mod);
                c.getPlayer().dropMessage(6, "添加玩家[" + playername + "] aclog [" + mod + "]成功。");
            } else {
                c.getPlayer().dropMessage(6, "玩家[" + playername + "]不存在于资料库内。");
            }
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!添加aclog <玩家名字> - aclog类型").toString();
        }
    }

    public static class 补领赞助 extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String[] splitted) {
            if (splitted.length < 3) {
                return false;
            }
            String playername = splitted[1];
            int mod = Integer.parseInt(splitted[2]);
            int playerid = MapleCharacter.getCharacterIdByName(playername);
            if (playerid == -1) {
                c.getPlayer().dropMessage(6, "玩家[" + playername + "]不存在于资料库内。");
                return true;
            }
            MapleCharacter victim = MapleCharacter.getCharacterById(playerid);
            if (victim != null) {
                victim.setBuLingZanZu(mod);
                victim.modifyCSPoints(1, mod * 5, true);
                victim.modifyCSPoints(3, mod * 2);
                victim.gainVip();//更新vip等级
                c.getPlayer().dropMessage(6, "玩家[" + playername + "] 补领赞助 [" + mod + "] 台币。");
            } else {
                c.getPlayer().dropMessage(6, "玩家[" + playername + "]不存在于资料库内。");
            }
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!补领赞助 <玩家名字> - 赞助台币数量").toString();
        }
    }

    public static class copyInv extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String[] splitted) {
            MapleCharacter player = c.getPlayer();
            MapleCharacter victim;
            int type = 1;
            if (splitted.length < 2) {
                return false;
            }

            String name = splitted[1];
            int ch = World.Find.findChannel(name);
            if (ch <= 0) {
                c.getPlayer().dropMessage(6, "玩家必须上线");
                return false;
            }
            victim = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(name);
            if (victim == null) {
                player.dropMessage(6, "找不到该玩家");
                return true;
            }
            try {
                type = Integer.parseInt(splitted[2]);
            } catch (Exception ex) {
            }
            if (type == 0) {
                for (client.inventory.Item ii : victim.getInventory(MapleInventoryType.EQUIPPED).list()) {
                    client.inventory.Item n = ii.copy();
                    player.getInventory(MapleInventoryType.EQUIP).addItem(n);
                }
                player.fakeRelog();
            } else {
                MapleInventoryType types;
                if (type == 1) {
                    types = MapleInventoryType.EQUIP;
                } else if (type == 2) {
                    types = MapleInventoryType.USE;
                } else if (type == 3) {
                    types = MapleInventoryType.ETC;
                } else if (type == 4) {
                    types = MapleInventoryType.SETUP;
                } else if (type == 5) {
                    types = MapleInventoryType.CASH;
                } else {
                    types = null;
                }
                if (types == null) {
                    c.getPlayer().dropMessage(6, "发生错误");
                    return true;
                }
                int[] equip = new int[97];
                for (int i = 1; i < 97; i++) {
                    if (victim.getInventory(types).getItem((short) i) != null) {
                        equip[i] = i;
                    }
                }
                for (int i = 0; i < equip.length; i++) {
                    if (equip[i] != 0) {
                        Item n = victim.getInventory(types).getItem((short) equip[i]).copy();
                        player.getInventory(types).addItem(n);
                        c.getSession().write(MaplePacketCreator.addInventorySlot(types, n));
                        c.getPlayer().marriage();
                    }
                }
            }
            return true;
        }

        public String getMessage() {
            return new StringBuilder().append("!copyinv 玩家名称 装备栏位(0 = 装备中 1=装备栏 2=消耗栏 3=其他栏 4=装饰栏 5=点数栏)(预设装备栏) - 複制玩家道具").toString();
        }
    }

    public static class DC extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String[] splitted) {
            if (splitted.length < 2) {
                return false;
            }
            MapleCharacter victim;
            String name = splitted[1];
            int ch = World.Find.findChannel(name);
            if (ch <= 0) {
                c.getPlayer().dropMessage(6, "该玩家不在线上1");
                return true;
            }
            victim = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(name);
            if (victim != null) {
                victim.getClient().disconnect(true, false);
                victim.getClient().getSession().close();
            } else {
                c.getPlayer().dropMessage(6, "该玩家不在线上2");
            }
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!dc <玩家> - 让玩家断线").toString();
        }
    }

    public static class 商城DC extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String[] splitted) {
            if (splitted.length < 2) {
                return false;
            }
            String name = splitted[1];
            int ch = World.Find.findChannel(name);
            c.getPlayer().dropMessage(6, "玩家在 " + ch);
            try {
                MapleCharacter victim = CashShopServer.getPlayerStorage().getCharacterByName(name);
                if (victim != null) {
                    victim.getClient().disconnect(true, true);
                    victim.getClient().getSession().close();
                    c.getPlayer().dropMessage(6, " 玩家 " + name + "商城解卡成功");
                } else {
                    c.getPlayer().dropMessage(6, "该玩家不在商城线上");
                }
            } catch (Exception e) {
                c.getPlayer().dropMessage(6, "异常抛出：玩家解卡失败");
            }
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!商城DC <玩家> - 让玩家断线").toString();
        }
    }

    public static class 商城DC2 extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String[] splitted) {
            if (splitted.length < 2) {
                return false;
            }
            String name = splitted[1];
            int ch = World.Find.findChannel(name);
            c.getPlayer().dropMessage(6, "玩家在 " + ch);
            try {
                MapleCharacter victim1 = MapleCharacter.getCharacterByName(name);
                if (victim1 != null) {
                    victim1.getClient().disconnect(true, true);
                    victim1.getClient().getSession().close();
                    c.getPlayer().dropMessage(6, " 玩家 " + name + "商城解卡成功");
                } else {
                    c.getPlayer().dropMessage(6, "该玩家不在商城线上");
                }
            } catch (Exception e) {
                c.getPlayer().dropMessage(6, "异常抛出：玩家解卡失败");
            }
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!商城DC2 <玩家> - 让玩家断线").toString();
        }
    }

    public static class 特殊商城DC extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String[] splitted) {
            if (splitted.length < 2) {
                return false;
            }
            String name = splitted[1];
            int ch = World.Find.findChannel(name);
            c.getPlayer().dropMessage(6, "玩家在 " + ch);
            try {
                MapleCharacter victim = MapleCharacter.getCharacterByName(name);
                if (victim != null) {
                    CashShopServer.getPlayerStorage().deregisterPlayer(victim.getId(), victim.getName());
                    c.getPlayer().dropMessage(6, " 玩家 " + name + "商城解卡成功");
                } else {
                    c.getPlayer().dropMessage(5, "找不到此玩家.");
                }
            } catch (Exception e) {
                c.getPlayer().dropMessage(6, "异常抛出：玩家解卡失败");
            }
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!特殊商城DC <玩家> - 让玩家断线").toString();
        }
    }

    public static class 特殊商城DC2 extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String[] splitted) {
            if (splitted.length < 2) {
                return false;
            }
            String name = splitted[1];
            int ch = World.Find.findChannel(name);
            c.getPlayer().dropMessage(6, "玩家在 " + ch);
            try {
                MapleCharacter victim = CashShopServer.getPlayerStorage().getCharacterByName(name);
                if (victim != null) {
                    CashShopServer.getPlayerStorage().deregisterPlayer(victim.getId(), victim.getName());
                    c.getPlayer().dropMessage(6, " 玩家 " + name + "商城解卡成功");
                } else {
                    c.getPlayer().dropMessage(6, "该玩家不在商城线上");
                }
            } catch (Exception e) {
                c.getPlayer().dropMessage(6, "异常抛出：玩家解卡失败");
            }
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!商城DC2 <玩家> - 让玩家断线").toString();
        }
    }

    public static class 频道DC extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String[] splitted) {
            if (splitted.length < 2) {
                return false;
            }

            String name = splitted[1];
            int ch = World.Find.findChannel(name);
            c.getPlayer().dropMessage(6, "玩家在 " + ch);
            for (ChannelServer cs : ChannelServer.getAllInstances()) {
                MapleCharacter character = cs.getPlayerStorage().getCharacterByName(name);
                if (character != null) {
                    character.getClient().disconnect(true, false);
                    character.getClient().getSession().close();
                    c.getPlayer().dropMessage(6, " 玩家 " + name + "频道解卡成功");
                } else {
                    c.getPlayer().dropMessage(6, "该玩家不在频道线上");
                }
            }
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!频道DC <玩家> - 让玩家断线").toString();
        }
    }

    public static class 特殊频道DC extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String[] splitted) {
            if (splitted.length < 2) {
                return false;
            }
            String name = splitted[1];
            int ch = World.Find.findChannel(name);
            c.getPlayer().dropMessage(6, "玩家在 " + ch);
            for (ChannelServer cs : ChannelServer.getAllInstances()) {
                MapleCharacter character = cs.getPlayerStorage().getCharacterByName(name);
                if (character != null) {
                    character.getClient().disconnect(true, false);
                    cs.removePlayer(character.getId(), character.getName());
                    character.getClient().getSession().close();
                    c.getPlayer().dropMessage(6, " 玩家 " + name + "频道解卡成功");
                } else {
                    c.getPlayer().dropMessage(6, "该玩家不在频道线上");
                }
            }
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!特殊频道DC <玩家> - 让玩家断线").toString();
        }
    }

    public static class 特殊频道DC2 extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String[] splitted) {
            if (splitted.length < 2) {
                return false;
            }
            String name = splitted[1];
            int ch = World.Find.findChannel(name);
            c.getPlayer().dropMessage(6, "玩家在 " + ch);
            for (ChannelServer cs : ChannelServer.getAllInstances()) {
                MapleCharacter character = cs.getPlayerStorage().getCharacterByName(name);
                cs.removePlayer(character.getId(), character.getName());
                if (character != null) {
                    cs.removePlayer(character.getId(), character.getName());
                    character.getClient().disconnect(true, false);
                    character.getClient().getSession().close();
                    c.getPlayer().dropMessage(6, " 玩家 " + name + "频道解卡成功");
                } else {
                    c.getPlayer().dropMessage(6, "该玩家不在频道线上");
                }
            }
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!特殊频道DC <玩家> - 让玩家断线").toString();
        }
    }

    public static class 特殊DC extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String[] splitted) {
            if (splitted.length < 2) {
                return false;
            }
            String name = splitted[1];
            int ch = World.Find.findChannel(name);
            c.getPlayer().dropMessage(6, "玩家在 " + ch);
            for (ChannelServer cs : ChannelServer.getAllInstances()) {
                MapleCharacter character = cs.getPlayerStorage().getCharacterByName(name);
                if (character != null) {
                    character.getClient().disconnect(true, false);
                    character.getClient().getSession().close();
                    c.getPlayer().dropMessage(6, " 玩家 " + name + "频道解卡成功");
                } else {
                    c.getPlayer().dropMessage(6, "该玩家不在频道线上");
                }
            }

            MapleCharacter charactercs = CashShopServer.getPlayerStorage().getCharacterByName(name);
            if (charactercs != null) {
                charactercs.getClient().disconnect(true, true);
                charactercs.getClient().getSession().close();
                c.getPlayer().dropMessage(6, " 玩家 " + name + "商城解卡成功2");
            } else {
                c.getPlayer().dropMessage(6, "该玩家不在商城线上2");
            }
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!特殊DC <玩家> - 让玩家断线").toString();
        }
    }

    public static class 卡登处理 extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String[] splitted) {
            if (splitted.length < 2) {
                return false;
            }
            String playername = splitted[1];
            int playerid = MapleCharacter.getCharacterIdByName(playername);
            if (playerid == -1) {
                c.getPlayer().dropMessage(6, "玩家[" + playername + "]不存在于资料库内。");
                return true;
            }
            MapleCharacter victim = MapleCharacter.getCharacterById(playerid);

            /*List<String> charName = c.loadCharacterNamesByCharId(playerid);
            for (ChannelServer cs : ChannelServer.getAllInstances()) {
                for (final String names : charName) {
                    MapleCharacter character = cs.getPlayerStorage().getCharacterByName(names);
                    if (character != null) {
                        character.getClient().disconnect(true, false);
                        character.getClient().getSession().close();
                        c.getPlayer().dropMessage(" 玩家 " + names + "频道解卡成功");
                    } else {
                        c.getPlayer().dropMessage("该玩家不在频道线上");
                    }
                }
            }
            for (final String namesa : charName) {
                MapleCharacter charactercs = CashShopServer.getPlayerStorage().getCharacterByName(namesa);
                if (charactercs != null) {
                    charactercs.getClient().disconnect(true, true);
                    charactercs.getClient().getSession().close();
                    c.getPlayer().dropMessage(" 玩家 " + namesa + "商城解卡成功");
                } else {
                    c.getPlayer().dropMessage("该玩家不在商城线上");
                }
            }*/
            if (victim != null) {
                victim.updateNewState(0, victim.getAccountID());
                c.getPlayer().dropMessage(6, "修改玩家[" + playername + "]登录状态成功。");
            } else {
                c.getPlayer().dropMessage(6, "玩家[" + playername + "]不存在于资料库内。");
            }
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!卡登处理 <玩家名字> - 修改资料库玩家登录状态").toString();
        }
    }

    public static class DCAll extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            int range = -1;
            if (splitted.length < 2) {
                return false;
            }
            String input = null;
            try {
                input = splitted[1];
            } catch (Exception ex) {

            }
            switch (splitted[1]) {
                case "m":
                    range = 0;
                    break;
                case "c":
                    range = 1;
                    break;
                case "w":
                default:
                    range = 2;
                    break;
            }
            if (range == -1) {
                range = 1;
            }
            if (range == 0) {
                c.getPlayer().getMap().disconnectAll();
            } else if (range == 1) {
                c.getChannelServer().getPlayerStorage().disconnectAll();
            } else if (range == 2) {
                for (ChannelServer cserv : ChannelServer.getAllInstances()) {
                    cserv.getPlayerStorage().disconnectAll(true);
                }
            }
            String show = "";
            switch (range) {
                case 0:
                    show = "地图";
                    break;
                case 1:
                    show = "频道";
                    break;
                case 2:
                    show = "世界";
                    break;
            }
            String msg = "[GM 聊天] GM " + c.getPlayer().getName() + "  DC 了 " + show + "玩家";
            World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, msg));
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!dcall [m|c|w] - 所有玩家断线").toString();

        }
    }

    public static class DCID extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String[] splitted) {
            if (splitted.length < 2) {
                return false;
            }
            MapleCharacter victim;
            int id = Integer.parseInt(splitted[1]);
            int ch = World.Find.findChannel(id);
            if (ch <= 0) {
                c.getPlayer().dropMessage(6, "该玩家不在线上");
                return true;
            }
            victim = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterById(id);
            if (victim != null) {
                victim.getClient().disconnect(true, false);
                victim.getClient().getSession().close();
            } else {
                c.getPlayer().dropMessage(6, "该玩家不在线上");
            }
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!DCID <玩家ID> - 让玩家断线").toString();
        }
    }

    public static class DestroyPNPC extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            try {
                c.getPlayer().dropMessage(6, "Destroying playerNPC...");
                final MapleNPC npc = c.getPlayer().getMap().getNPCByOid(Integer.parseInt(splitted[1]));
                if (npc instanceof PlayerNPC) {
                    ((PlayerNPC) npc).destroy(true);
                    c.getPlayer().dropMessage(6, "Done");
                } else {
                    c.getPlayer().dropMessage(6, "!destroypnpc [objectid]");
                }
            } catch (Exception e) {
                c.getPlayer().dropMessage(6, "NPC failed... : " + e.getMessage());
            }
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!destroypnpc [objectid] - 删除PNPC").toString();
        }

    }

    public static class CloneMe extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            c.getPlayer().cloneLook();
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!cloneme - 产生克龙体").toString();
        }
    }

    public static class DisposeClones extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            c.getPlayer().dropMessage(6, c.getPlayer().getCloneSize() + "个克龙体消失了.");
            c.getPlayer().disposeClones();
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!disposeclones - 摧毁克龙体").toString();
        }
    }

    public static class GiveAllCash extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String[] splitted) {
            if (splitted.length < 2) {
                return false;
            }
            int gash = 0, size = 0;
            try {
                gash = Integer.parseInt(splitted[1]);
            } catch (NumberFormatException ex) {

            }
            for (ChannelServer cs : ChannelServer.getAllInstances()) {
                for (MapleCharacter chr : cs.getPlayerStorage().getAllCharacters()) {
                    size++;
                    chr.modifyCSPoints(1, gash, true);
                    c.getPlayer().dropMessage(6, "[在线] 玩家<" + chr.getName() + "> Lv." + chr.getLevel() + " 地图<" + chr.getMap().getMapName() + "> 发放点数[" + gash + "] 发放后点数[" + (chr.getCSPoints(1)) + "]");
                }
            }

            c.getPlayer().dropMessage(6, "共发放了" + size + "个帐号, 一共为" + size * gash + "点");
            FileoutputUtil.logToFile("Logs/Data/点数指令.txt", "\r\n " + FileoutputUtil.CurrentReadable_Time() + " <" + c.getPlayer().getName() + "> 使用了" + splitted[0] + " 共发放了" + size + "个帐号, 一共为" + size * gash + " GASH点");

            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!GiveAllCash <数量> - 给予离线及线上的人Gash").toString();
        }
    }

    public static class GiveAllMP extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String[] splitted) {
            if (splitted.length < 2) {
                return false;
            }
            int mp = 0, size = 0;
            try {
                mp = Integer.parseInt(splitted[1]);
            } catch (NumberFormatException ex) {

            }
            for (ChannelServer cs : ChannelServer.getAllInstances()) {
                for (MapleCharacter chr : cs.getPlayerStorage().getAllCharacters()) {
                    size++;
                    chr.modifyCSPoints(2, mp, true);
                    c.getPlayer().dropMessage(6, "[在线] 玩家<" + chr.getName() + "> Lv." + chr.getLevel() + " 地图<" + chr.getMap().getMapName() + "> 发放点数[" + mp + "] 发放后点数[" + (chr.getCSPoints(2)) + "]");
                }
            }
            c.getPlayer().dropMessage(6, "共发放了" + size + "个帐号, 一共为" + size * mp + "点");
            FileoutputUtil.logToFile("logs/Data/点数指令.txt", "\r\n " + FileoutputUtil.CurrentReadable_Time() + " <" + c.getPlayer().getName() + "> 使用了" + splitted[0] + " 共发放了" + size + "个帐号, 一共为" + size * mp + " 枫点");

            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!GiveAllMP <数量> - 给予离线及线上的人枫叶点数").toString();
        }
    }

    public static class 给玩家金币 extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            if (splitted.length < 2) {
                return false;
            }
            MapleCharacter victim;
            String name = splitted[1];
            int gain = Integer.parseInt(splitted[2]);
            int ch = World.Find.findChannel(name);
            if (ch <= 0) {
                c.getPlayer().dropMessage(6, "玩家必须上线");
                return true;
            }
            victim = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(name);
            if (victim == null) {
                c.getPlayer().dropMessage(5, "找不到 '" + name);
            } else {
                victim.gainMeso(gain, false);
                String msg = "[GM 聊天] GM " + c.getPlayer().getName() + " 给了 " + victim.getName() + " 枫币 " + gain + "点";
                World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, msg));
            }
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!给所有玩家金币 <名字> <数量> - 给玩家枫币").toString();
        }
    }

    public static class GiveSkill extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            if (splitted.length < 3) {
                return false;
            }
            MapleCharacter victim;
            String name = splitted[1];
            int ch = World.Find.findChannel(name);
            if (ch <= 0) {
                return false;
            }
            victim = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(name);

            Skill skill = SkillFactory.getSkill(Integer.parseInt(splitted[2]));
            byte level = (byte) CommandProcessorUtil.getOptionalIntArg(splitted, 3, 1);
            byte masterlevel = (byte) CommandProcessorUtil.getOptionalIntArg(splitted, 4, 1);

            if (level > skill.getMaxLevel()) {
                level = (byte) skill.getMaxLevel();
            }
            victim.changeSkillLevel(skill, level, masterlevel);
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!giveskill <玩家名称> <技能ID> [技能等级] [技能最大等级] - 给予技能").toString();
        }
    }

    public static class HealHere extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String[] splitted) {
            MapleCharacter player = c.getPlayer();
            for (MapleCharacter mch : player.getMap().getCharacters()) {
                if (mch != null) {
                    mch.getStat().setHp(mch.getStat().getMaxHp(), mch);
                    mch.updateSingleStat(MapleStat.HP, mch.getStat().getMaxHp());
                    mch.getStat().setMp(mch.getStat().getMaxMp(), mch);
                    mch.updateSingleStat(MapleStat.MP, mch.getStat().getMaxMp());
                    mch.dispelDebuffs();
                }
            }
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!HealHere - 恢复当前地图上玩家的HP/MP").toString();
        }
    }

    public static class Kill extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            MapleCharacter player = c.getPlayer();
            if (splitted.length < 2) {
                return false;
            }
            MapleCharacter victim;
            String name = splitted[1];
            int ch = World.Find.findChannel(name);
            if (ch <= 0) {
                c.getPlayer().dropMessage(6, "玩家必须上线");
                return true;
            }
            victim = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(name);
            if (victim == null) {
                c.getPlayer().dropMessage(6, "[kill] 玩家 " + name + " 不存在.");
            } else if (player.allowedToTarget(victim)) {
                victim.getStat().setHp((short) 0, victim);
                victim.getStat().setMp((short) 0, victim);
                victim.updateSingleStat(MapleStat.HP, 0);
                victim.updateSingleStat(MapleStat.MP, 0);
            }
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!kill <玩家名称> - 杀掉玩家").toString();
        }
    }

    public static class KillAll extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            MapleMap map = c.getPlayer().getMap();
            double range = Double.POSITIVE_INFINITY;
            boolean withdrop = false;
            if (splitted.length > 1) {
                int mapid = Integer.parseInt(splitted[1]);
                int irange = 9999;
                if (splitted.length <= 2) {
                    range = irange * irange;
                } else {
                    map = c.getChannelServer().getMapFactory().getMap(Integer.parseInt(splitted[1]));
                    irange = Integer.parseInt(splitted[2]);
                    range = irange * irange;
                }
                if (splitted.length >= 3) {
                    withdrop = splitted[3].equalsIgnoreCase("true");
                }
            }

            MapleMonster mob;
            if (map == null) {
                c.getPlayer().dropMessage(6, "地图[" + splitted[2] + "] 不存在。");
                return true;
            }
            List<MapleMapObject> monsters = map.getMapObjectsInRange(c.getPlayer().getPosition(), range, Arrays.asList(MapleMapObjectType.MONSTER));
            for (MapleMapObject monstermo : map.getMapObjectsInRange(c.getPlayer().getPosition(), range, Arrays.asList(MapleMapObjectType.MONSTER))) {
                mob = (MapleMonster) monstermo;
                map.killMonster(mob, c.getPlayer(), withdrop, false, (byte) 1);
            }

            c.getPlayer().dropMessage(6, "您总共杀了 " + monsters.size() + " 怪物");

            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!killall [range] [mapid] - 杀掉所有玩家").toString();

        }
    }

    public static class 清怪爆物 extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String[] splitted) {
            MapleMap map = c.getPlayer().getMap();
            double range = Double.POSITIVE_INFINITY;

            if (splitted.length > 1) {
                //&& !splitted[0].equals("!killmonster") && !splitted[0].equals("!hitmonster") && !splitted[0].equals("!hitmonsterbyoid") && !splitted[0].equals("!killmonsterbyoid")) {
                int irange = Integer.parseInt(splitted[1]);
                if (splitted.length <= 2) {
                    range = irange * irange;
                } else {
                    map = c.getChannelServer().getMapFactory().getMap(Integer.parseInt(splitted[2]));
                }
            }
            if (map == null) {
                c.getPlayer().dropMessage(6, "Map does not exist");
                return false;
            }
            MapleMonster mob;
            for (MapleMapObject monstermo : map.getMapObjectsInRange(c.getPlayer().getPosition(), range, Arrays.asList(MapleMapObjectType.MONSTER))) {
                mob = (MapleMonster) monstermo;
                map.killMonster(mob, c.getPlayer(), true, false, (byte) 1);
            }
            return true;
        }

        public String getMessage() {
            return new StringBuilder().append("!清怪爆物  - 杀掉所有怪物并掉宝").toString();

        }
    }

    public static class KillMap extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            for (MapleCharacter map : c.getPlayer().getMap().getCharactersThreadsafe()) {
                if (map != null && !map.isGM()) {
                    map.getStat().setHp((short) 0, map);
                    map.getStat().setMp((short) 0, map);
                    map.updateSingleStat(MapleStat.HP, 0);
                    map.updateSingleStat(MapleStat.MP, 0);
                }
            }
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!killmap - 杀掉所有玩家").toString();
        }
    }

    public static class KillMonster extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            if (splitted.length < 2) {
                return false;
            }
            MapleMap map = c.getPlayer().getMap();
            double range = Double.POSITIVE_INFINITY;
            MapleMonster mob;
            for (MapleMapObject monstermo : map.getMapObjectsInRange(c.getPlayer().getPosition(), range, Arrays.asList(MapleMapObjectType.MONSTER))) {
                mob = (MapleMonster) monstermo;
                if (mob.getId() == Integer.parseInt(splitted[1])) {
                    mob.damage(c.getPlayer(), mob.getHp(), false);
                }
            }
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!killmonster <mobid> - 杀掉地图上某个怪物").toString();

        }
    }

    public static class MakeOfflineP extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            try {
                c.getPlayer().dropMessage(6, "Making playerNPC...");
                MapleClient cs = new MapleClient(null, null, new MapleSession(new MockIOSession()));
                MapleCharacter chhr = MapleCharacter.loadCharFromDB(MapleCharacterUtil.getIdByName(splitted[1]), cs, false);
                if (chhr == null) {
                    c.getPlayer().dropMessage(6, splitted[1] + " does not exist");

                } else {
                    int npcId = Integer.parseInt(splitted[2]);
                    if (!(npcId >= 9901500 && npcId <= 9901551) || !(npcId >= 9901000 && npcId >= 9901319)) {
                        c.getPlayer().dropMessage(6, "NPC不存在");
                        return true;
                    }
                    PlayerNPC npc = new PlayerNPC(chhr, npcId, c.getPlayer().getMap(), c.getPlayer());
                    npc.addToServer();
                    c.getPlayer().dropMessage(6, "Done");
                }
            } catch (Exception e) {
                c.getPlayer().dropMessage(6, "NPC failed... : " + e.getMessage());
            }
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!deletepnpc <charname> <npcid> - 创造离线PNPC").toString();
        }
    }

    public static class MakePNPC extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            if (splitted.length < 3) {
                return false;
            }
            try {
                c.getPlayer().dropMessage(6, "Making playerNPC...");
                MapleCharacter chhr;
                String name = splitted[1];
                int ch = World.Find.findChannel(name);
                if (ch <= 0) {
                    c.getPlayer().dropMessage(6, "玩家必须上线");
                    return true;
                }
                chhr = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(name);

                if (chhr == null) {
                    c.getPlayer().dropMessage(6, splitted[1] + " is not online");
                } else {
                    int npcId = Integer.parseInt(splitted[2]);
                    MapleNPC npc_c = MapleLifeFactory.getNPC(npcId);
                    //if (npc_c == null || npc_c.getName().equals("MISSINGNO")) {
                    //c.getPlayer().dropMessage(6, "NPC不存在");
                    //     return true;
                    //  }
                    if (!(npcId >= 9901500 && npcId <= 9901551) || !(npcId >= 9901000 && npcId >= 9901319)) {
                        c.getPlayer().dropMessage(6, "NPC不存在");
                        return true;
                    }
                    PlayerNPC npc = new PlayerNPC(chhr, npcId, c.getPlayer().getMap(), c.getPlayer());
                    npc.addToServer();
                    c.getPlayer().dropMessage(6, "Done");
                }
            } catch (Exception e) {
                c.getPlayer().dropMessage(6, "NPC failed... : " + e.getMessage());

            }
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!makepnpc <playername> <npcid> - 创造玩家NPC").toString();
        }
    }

    public static class 给所有玩家金币 extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            if (splitted.length < 2) {
                return false;
            }
            int gain = Integer.parseInt(splitted[1]);
            for (ChannelServer cserv : ChannelServer.getAllInstances()) {
                for (MapleCharacter mch : cserv.getPlayerStorage().getAllCharacters()) {
                    mch.gainMeso(gain, true);
                }
            }
            String msg = "[GM 聊天] GM " + c.getPlayer().getName() + " 给了 所有玩家 枫币 " + gain + "点";
            World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, msg));
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!给所有玩家金币 <数量> - 给所有玩家枫币").toString();
        }
    }

    public static class 吸怪 extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            for (final MapleMapObject mmo : c.getPlayer().getMap().getAllMonstersThreadsafe()) {
                final MapleMonster monster = (MapleMonster) mmo;
                c.getPlayer().getMap().broadcastMessage(MobPacket.moveMonster(false, 0, 0, monster.getObjectId(), monster.getPosition(), c.getPlayer().getLastRes()));
                monster.setPosition(c.getPlayer().getPosition());
            }
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!吸怪 - 全图吸怪").toString();
        }
    }
    
    /**
     * 全屏捡取GM命令
     */
    public static class 吸物 extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String[] splitted) {
            for (final MapleMapItem item : c.getPlayer().getMap().getAllItemsThreadsafe()) {
                InventoryHandler.pickUpItem(c, c.getPlayer(), item.getPosition(), item.getObjectId(), item);
            }

            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!吸物 - 全图吸物").toString();
        }
    }


    public static class NPC extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            int npcId = 0;
            try {
                npcId = Integer.parseInt(splitted[1]);
            } catch (Exception ex) {

            }
            MapleNPC npc = MapleLifeFactory.getNPC(npcId);
            if (npc != null && !npc.getName().equals("MISSINGNO")) {
                npc.setPosition(c.getPlayer().getPosition());
                npc.setCy(c.getPlayer().getPosition().y);
                npc.setRx0(c.getPlayer().getPosition().x + 50);
                npc.setRx1(c.getPlayer().getPosition().x - 50);
                npc.setFh(c.getPlayer().getMap().getFootholds().findBelow(c.getPlayer().getPosition()).getId());
                npc.setCustom(true);
                c.getPlayer().getMap().addMapObject(npc);
                c.getPlayer().getMap().broadcastMessage(MaplePacketCreator.spawnNPC(npc, true));
            } else {
                c.getPlayer().dropMessage(6, "找不到此代码为" + npcId + "的Npc");

            }
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!npc <npcid> - 呼叫出NPC").toString();
        }
    }

    public static class openmap extends CommandExecute {

        public boolean execute(MapleClient c, String[] splitted) {
            int mapid = 0;
            String input = null;
            MapleMap map = null;
            if (splitted.length < 2) {
                return false;
            }
            try {
                input = splitted[1];
                mapid = Integer.parseInt(input);
            } catch (Exception ex) {
            }
            for (ChannelServer cserv : ChannelServer.getAllInstances()) {
                cserv.getMapFactory().HealMap(mapid);
            }
            return true;

        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!openmap - 开放地图").toString();
        }
    }

    public static class 永久npc extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String[] splitted) {

            int npcId = Integer.parseInt(splitted[1]);
            MapleNPC npc = MapleLifeFactory.getNPC(npcId);
            if (npc != null && !npc.getName().equals("MISSINGNO")) {
                final int xpos = c.getPlayer().getPosition().x;
                final int ypos = c.getPlayer().getPosition().y;
                final int fh = c.getPlayer().getMap().getFootholds().findBelow(c.getPlayer().getPosition()).getId();
                npc.setPosition(c.getPlayer().getPosition());
                npc.setCy(ypos);
                npc.setRx0(xpos);
                npc.setRx1(xpos);
                npc.setFh(fh);
                npc.setCustom(true);
                try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
                    try (PreparedStatement ps = con.prepareStatement("INSERT INTO wz_customlife (dataid, f, hide, fh, cy, rx0, rx1, type, x, y, mid) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
                        ps.setInt(1, npcId);
                        ps.setInt(2, 0); // 1 = right , 0 = left
                        ps.setInt(3, 0); // 1 = hide, 0 = show
                        ps.setInt(4, fh);
                        ps.setInt(5, ypos);
                        ps.setInt(6, xpos);
                        ps.setInt(7, xpos);
                        ps.setString(8, "n");
                        ps.setInt(9, xpos);
                        ps.setInt(10, ypos);
                        ps.setInt(11, c.getPlayer().getMapId());
                        ps.executeUpdate();
                    }
                } catch (SQLException e) {
                    c.getPlayer().dropMessage(6, "Failed to save NPC to the database");
                    FileoutputUtil.outError("logs/资料库异常.txt", e);
                }
                for (ChannelServer cserv : ChannelServer.getAllInstances()) {
                    cserv.getMapFactory().getMap(c.getPlayer().getMapId()).addMapObject(npc);
                    cserv.getMapFactory().getMap(c.getPlayer().getMapId()).broadcastMessage(MaplePacketCreator.spawnNPC(npc, true));
//                    c.getPlayer().getMap().addMapObject(npc);
//                    c.getPlayer().getMap().broadcastMessage(MaplePacketCreator.spawnNPC(npc, true));
                }
                c.getPlayer().dropMessage(6, "Please do not reload this map or else the NPC will disappear till the next restart.");
            } else {
                c.getPlayer().dropMessage(6, "查无此 Npc ");
            }
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!永久npc - 建立永久NPC").toString();
        }
    }

    public static class ProDrop extends CommandExecute {

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
                equip.setGMLog(c.getPlayer().getName() + " 使用 !Prodrop");
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
                c.getPlayer().getMap().spawnItemDrop(c.getPlayer(), c.getPlayer(), equip, c.getPlayer().getPosition(), true, true);
            } else {
                toDrop = new client.inventory.Item(itemId, (byte) 0, (short) quantity, (byte) 0);
                toDrop.setGMLog(c.getPlayer().getName() + " 使用 !Prodrop");
                if (DAY_check) {
                    toDrop.setExpiration(System.currentTimeMillis() + (day * 24 * 60 * 60 * 1000));
                }
                c.getPlayer().getMap().spawnItemDrop(c.getPlayer(), c.getPlayer(), toDrop, c.getPlayer().getPosition(), true, true);
            }
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!ProDrop <物品代码> (<力量> <敏捷> <智力> <幸运> <HP> <MP> <物攻> <魔攻> <物防> <魔防> <武器+x> <命中> <迴避> <移动> <跳跃> <衝捲数> <天数>)").toString();
        }
    }

    public static class 刷新地图 extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            if (splitted.length < 2) {
                return false;
            }
            final int mapId = Integer.parseInt(splitted[1]);
            for (ChannelServer cserv : ChannelServer.getAllInstances()) {
                if (cserv.getMapFactory().isMapLoaded(mapId) && cserv.getMapFactory().getMap(mapId).getCharactersSize() > 0) {
                    c.getPlayer().dropMessage(5, "There exists characters on channel " + cserv.getChannel());
                    return true;
                }
            }
            for (ChannelServer cserv : ChannelServer.getAllInstances()) {
                if (cserv.getMapFactory().isMapLoaded(mapId)) {
                    cserv.getMapFactory().removeMap(mapId);
                }
            }
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!刷新地图 <maipid> - 重置某个地图").toString();
        }
    }

    public static class RemoveItem extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            if (splitted.length < 3) {
                return false;
            }
            MapleCharacter chr;
            String name = splitted[1];
            int id = Integer.parseInt(splitted[2]);
            int ch = World.Find.findChannel(name);
            if (ch <= 0) {
                c.getPlayer().dropMessage(6, "玩家必须上线");
                return true;
            }
            chr = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(name);

            if (chr == null) {
                c.getPlayer().dropMessage(6, "此玩家并不存在");
            } else {
                chr.removeAll(id, false);
                c.getPlayer().dropMessage(6, "所有ID为 " + id + " 的道具已经从 " + name + " 身上被移除了");
            }
            return true;

        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!RemoveItem <角色名称> <物品ID> - 移除玩家身上的道具").toString();
        }
    }

    public static class RemoveNPCs extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            c.getPlayer().getMap().resetNPCs();
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!removenpcs - 删除所有NPC").toString();
        }
    }

    public static class 刷新当前地图 extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            c.getPlayer().getMap().resetFully();
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!刷新当前地图 - 重置这个地图").toString();
        }
    }

    public static class ResetMobs extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            c.getPlayer().getMap().killAllMonsters(false);
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!resetmobs - 重置地图上所有怪物").toString();
        }
    }

    public static class Respawn extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            c.getPlayer().getMap().respawn(true);
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!respawn - 地图怪物重生").toString();
        }
    }

    public static class SendAllNote extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {

            if (splitted.length >= 1) {
                String text = StringUtil.joinStringFrom(splitted, 1);
                for (MapleCharacter mch : c.getChannelServer().getPlayerStorage().getAllCharacters()) {
                    c.getPlayer().sendNote(mch.getName(), text);
                }
            } else {
                return false;
            }
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!sendallnote <文字> 传送Note给目前频道的所有人").toString();
        }
    }

    public static class 改名 extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            if (splitted.length < 2) {
                return false;
            }
            String after = splitted[1];
            if (after.length() <= 12) {
                c.getPlayer().setName(splitted[1]);
                c.getPlayer().fakeRelog();
            }
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!改名 新名字 - 改角色名字").toString();
        }
    }

    public static class 召唤怪物 extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            if (splitted.length < 2) {
                return false;
            }
            int mid = 0;
            try {
                mid = Integer.parseInt(splitted[1]);
            } catch (Exception ex) {

            }
            int num = Math.min(CommandProcessorUtil.getOptionalIntArg(splitted, 2, 1), 500);
            if (num > 1000) {
                num = 1000;
            }
            Long hp = CommandProcessorUtil.getNamedLongArg(splitted, 1, "hp");
            Integer mp = CommandProcessorUtil.getNamedIntArg(splitted, 1, "mp");
            Integer exp = CommandProcessorUtil.getNamedIntArg(splitted, 1, "exp");
            Double php = CommandProcessorUtil.getNamedDoubleArg(splitted, 1, "php");
            Double pmp = CommandProcessorUtil.getNamedDoubleArg(splitted, 1, "pmp");
            Double pexp = CommandProcessorUtil.getNamedDoubleArg(splitted, 1, "pexp");
            MapleMonster onemob;
            try {
                onemob = MapleLifeFactory.getMonster(mid);
            } catch (RuntimeException e) {
                c.getPlayer().dropMessage(5, "错误: " + e.getMessage());
                return true;
            }

            long newhp;
            int newexp, newmp;
            if (hp != null) {
                newhp = hp;
            } else if (php != null) {
                newhp = (long) (onemob.getMobMaxHp() * (php / 100));
            } else {
                newhp = onemob.getMobMaxHp();
            }
            if (mp != null) {
                newmp = mp;
            } else if (pmp != null) {
                newmp = (int) (onemob.getMobMaxMp() * (pmp / 100));
            } else {
                newmp = onemob.getMobMaxMp();
            }
            if (exp != null) {
                newexp = exp;
            } else if (pexp != null) {
                newexp = (int) (onemob.getMobExp() * (pexp / 100));
            } else {
                newexp = onemob.getMobExp();
            }
            if (newhp < 1) {
                newhp = 1;
            }

            final OverrideMonsterStats overrideStats = new OverrideMonsterStats(newhp, onemob.getMobMaxMp(), newexp, false);
            for (int i = 0; i < num; i++) {
                MapleMonster mob = MapleLifeFactory.getMonster(mid);
                mob.setHp(newhp);
                mob.setOverrideStats(overrideStats);
                c.getPlayer().getMap().spawnMonsterOnGroundBelow(mob, c.getPlayer().getPosition());
            }
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!召唤怪物 <怪物ID> <hp|exp|php||pexp = ?> - 召唤怪物").toString();
        }
    }

    public static class 拉全部 extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            for (ChannelServer CS : ChannelServer.getAllInstances()) {
                for (MapleCharacter mch : CS.getPlayerStorage().getAllCharacters()) {
                    if (mch.getMapId() != c.getPlayer().getMapId()) {
                        mch.changeMap(c.getPlayer().getMap(), c.getPlayer().getPosition());
                    }
                    if (mch.getClient().getChannel() != c.getPlayer().getClient().getChannel()) {
                        mch.changeChannel(c.getPlayer().getClient().getChannel());
                    }
                }
            }
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!拉全部 把所有玩家传送到这里").toString();
        }
    }

    public static class 传送所有人 extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            try {
                final MapleMap target = c.getChannelServer().getMapFactory().getMap(Integer.parseInt(splitted[1]));
                if (target == null) {
                    c.getPlayer().dropMessage(6, "地图不存在。");
                    return false;
                }
                final MapleMap from = c.getPlayer().getMap();
                for (MapleCharacter chr : from.getCharactersThreadsafe()) {
                    chr.changeMap(target, target.getPortal(0));
                }
            } catch (Exception e) {
                return false;
            }
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!传送所有人 地图代码 - 把地图上的人全部传到那张地图").toString();
        }
    }

    public static class 给所有人点数 extends CashEveryone {
    }

    public static class CashEveryone extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            if (splitted.length > 2) {
                int type = Integer.parseInt(splitted[1]);
                int quantity = Integer.parseInt(splitted[2]);
                if (type == 1) {
                    type = 1;
                } else if (type == 2) {
                    type = 2;
                } else {
                    c.getPlayer().dropMessage(6, "用法: !给所有人点数 [点数类型1-2] [点数数量] 1是GASH.2是枫叶点数");
                    return true;
                }
                if (quantity > 10000) {
                    quantity = 10000;
                }
                int ret = 0;
                for (ChannelServer cserv : ChannelServer.getAllInstances()) {
                    for (MapleCharacter mch : cserv.getPlayerStorage().getAllCharacters()) {
                        mch.modifyCSPoints(type, quantity);
                        ret++;
                    }
                }
                String show = type == 1 ? "GASH" : "枫叶点数";
                for (ChannelServer cserv1 : ChannelServer.getAllInstances()) {
                    for (MapleCharacter mch : cserv1.getPlayerStorage().getAllCharacters()) {
                        mch.startMapEffect("管理员发放" + quantity + show + "给在线的所有玩家！祝您玩得开心快乐", 5121009);
                    }
                }
                c.getPlayer().dropMessage(6, new StringBuilder().append("命令使用成功，当前共有: ").append(ret).append(" 个玩家获得: ").append(quantity).append(" 点的").append(type == 1 ? "GASH " : " 枫叶点数 ").append(" 共计: ").append(ret * quantity).toString());
            } else {
                c.getPlayer().dropMessage(6, "用法: !给所有人点数 [点数类型1-2] [点数数量] 1是GASH.2是枫叶点数");
            }
            return true;

        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!给所有人点数 - [点数类型1-2] [点数数量]").toString();
        }
    }

    public static class 给地图人点数 extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            if (splitted.length > 2) {
                int type = Integer.parseInt(splitted[1]);
                int quantity = Integer.parseInt(splitted[2]);
                if (type == 1) {
                    type = 1;
                } else if (type == 2) {
                    type = 2;
                } else {
                    c.getPlayer().dropMessage(6, "用法: !给所有人点数 [点数类型1-2] [点数数量] 1是GASH.2是枫叶点数");
                    return true;
                }
                if (quantity > 10000) {
                    quantity = 10000;
                }
                int ret = 0;

                final MapleMap from = c.getPlayer().getMap();
                for (MapleCharacter chrr : from.getCharactersThreadsafe()) {
                    chrr.modifyCSPoints(type, quantity);
                    ret++;
                }

                String show = type == 1 ? "GASH" : "枫叶点数";
                final MapleMap froma = c.getPlayer().getMap();
                for (MapleCharacter chrrr : froma.getCharactersThreadsafe()) {
                    chrrr.getMap().startMapEffect("管理员发放" + quantity + show + "给在线的所有玩家！祝您玩得开心快乐", 5121009);
                }

                c.getPlayer().dropMessage(6, new StringBuilder().append("命令使用成功，当前共有: ").append(ret).append(" 个玩家获得: ").append(quantity).append(" 点的").append(type == 1 ? "GASH " : " 枫叶点数 ").append(" 共计: ").append(ret * quantity).toString());
            } else {
                c.getPlayer().dropMessage(6, "用法: !给地图人点数 [点数类型1-2] [点数数量] 1是GASH.2是枫叶点数");
            }
            return true;

        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!给地图人点数 - [点数类型1-2] [点数数量]").toString();
        }
    }

    public static class 给地图人枫币 extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            if (splitted.length > 1) {
                int quantity = Integer.parseInt(splitted[1]);
                int ret = 0;
                final MapleMap from = c.getPlayer().getMap();
                for (MapleCharacter chrr : from.getCharactersThreadsafe()) {
                    chrr.gainMeso(quantity, true);
                    ret++;
                }

                final MapleMap froma = c.getPlayer().getMap();
                for (MapleCharacter chrrr : froma.getCharactersThreadsafe()) {
                    chrrr.getMap().startMapEffect("管理员发放" + quantity + "枫币给在线的所有玩家！祝您玩得开心快乐", 5121009);
                }

                c.getPlayer().dropMessage(6, new StringBuilder().append("命令使用成功，当前共有: ").append(ret).append(" 个玩家获得: ").append(quantity).append(" 枫币").append(" 共计: ").append(ret * quantity).toString());
            } else {
                c.getPlayer().dropMessage(6, "用法: !给地图人枫币 [数量]");
            }
            return true;

        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!给地图人枫币 - [数量]").toString();
        }
    }

    public static class 给点券 extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            if (splitted.length < 4) {
                return false;
            }
            String error = null;
            String input = splitted[1];
            String name = splitted[2];
            int nx = 0;
            int gain = 0;
            try {
                switch (input) {
                    case "点券":
                        nx = 1;
                        break;
                    case "抵用":
                        nx = 2;
                        break;
                    default:
                        error = "输入的文字不是[点券]和[抵用] 而是[" + input + "]";
                        break;
                }
                gain = Integer.parseInt(splitted[3]);
            } catch (Exception ex) {
                error = "请输入数字以及不能给予超过2147483647的 " + input + " 错误为: " + ex.toString();
            }
            if (error != null) {
                c.getPlayer().dropMessage(6, error);
                return true;
            }

            int ch = World.Find.findChannel(name);
            if (ch <= 0) {
                c.getPlayer().dropMessage(6, "玩家必须上线");
                return true;
            }
            MapleCharacter victim = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(name);
            if (victim == null) {
                c.getPlayer().dropMessage(6, "找不到此玩家");
            } else {
                c.getPlayer().dropMessage(6, "已经给予玩家[" + name + "] " + input + " " + gain);
                FileoutputUtil.logToFile("logs/Data/给点数.txt", "\r\n " + FileoutputUtil.NowTime() + " GM " + c.getPlayer().getName() + " 给了 " + victim.getName() + " " + input + " " + gain + "点");
                victim.modifyCSPoints(nx, gain, true);
            }
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!给点券 点券/抵用 玩家名称 数量").toString();
        }
    }

    public static class 高级检索 extends CommandExecute {

        public boolean execute(MapleClient c, String[] splitted) {
            c.removeClickedNPC();
            NPCScriptManager.getInstance().start(c, 9010000, "高级检索");
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!高级检索 - 各种功能检索功能").toString();
        }
    }

    public static class Buff extends CommandExecute {

        public boolean execute(MapleClient c, String[] splitted) {
            int[] array = {9001000, 9001002, 9001003, 9001008, 2001002, 1101007, 1005, 2301003, 5121009, 1111002, 4111001, 4111002, 4211003, 4211005, 1321000, 2321004, 3121002, 5121009, 5221010};
            for (int i : array) {
                SkillFactory.getSkill(i).getEffect(c.getPlayer().getSkillLevel(i)).applyTo(c.getPlayer());
            }
            return true;
        }

        public String getMessage() {
            return new StringBuilder().append("!buff - 开启补助技能").toString();
        }
    }

    public static class 定时测谎 extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            ServerConstants.LieDetector = !ServerConstants.LieDetector;
            c.getPlayer().dropMessage(0, "[定时测谎] " + (ServerConstants.LieDetector ? "开启" : "关闭"));
            System.out.println("[定时测谎] " + (ServerConstants.LieDetector ? "开启" : "关闭"));
            return true;
        }

        public String getMessage() {
            return new StringBuilder().append("!定时测谎  - 定时测谎开关").toString();
        }
    }

    public static class 离婚 extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String splitted[]) {
            if (splitted.length < 2) {
                return false;
            }
            MapleCharacter victim;
            String name = splitted[1];
            int ch = World.Find.findChannel(name);
            if (ch <= 0) {
                c.getPlayer().dropMessage(6, "玩家必须上线");
                return true;
            }
            victim = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(name);
            if (victim == null) {
                c.getPlayer().dropMessage(6, "玩家必须上线");
                return true;
            }
            victim.setMarriageId(0);
            victim.fakeRelog();
            victim.dropMessage(5, "离婚成功！");
            victim.saveToDB(false, false);
            c.getPlayer().dropMessage(6, victim.getName() + "离婚成功！");
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!离婚 <玩家名称> - 离婚").toString();
        }
    }

    public static class 危险IP extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String[] splitted) {
            if (splitted.length < 2) {
                return false;
            } else {
                String name = splitted[1];
                MapleCharacter victim = MapleCharacter.getCharacterByName(name);
                int ch = World.Find.findChannel(name);
                if (victim != null) {
                    if (ch <= 0) {
                        c.getPlayer().dropMessage(5, "该角色为离线状态");
                    } else {
                        victim.setChrDangerousIp(victim.getClient().getSessionIPAddress());
                    }
                } else {
                    c.getPlayer().dropMessage(5, "找不到此玩家.");
                }
            }
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!危险IP <角色名字> - 添加危险IP").toString();
        }
    }

    public static class 危险ID extends CommandExecute {

        @Override
        public boolean execute(MapleClient c, String[] splitted) {
            if (splitted.length < 2) {
                return false;
            } else {
                String name = splitted[1];
                MapleCharacter victim = MapleCharacter.getCharacterByName(name);
                int ch = World.Find.findChannel(name);
                if (victim != null) {
                    if (ch <= 0) {
                        //c.getPlayer().dropMessage(5, "该角色为离线状态");
                        victim.setChrDangerousAcc(victim.getClient().getAccountName());
                    } else {
                        victim.setChrDangerousAcc(victim.getClient().getAccountName());
                    }
                } else {
                    c.getPlayer().dropMessage(5, "找不到此玩家.");
                }
            }
            return true;
        }

        @Override
        public String getMessage() {
            return new StringBuilder().append("!危险帐号 <角色名字> - 添加危险帐号").toString();
        }
    }

    /* public static class GetSkill extends CommandExecute {

        @Override
        public int execute(MapleClient c, String[] splitted) {
            Skill skill = SkillFactory.getSkill(Integer.parseInt(splitted[1]));
            byte level = (byte) CommandProcessorUtil.getOptionalIntArg(splitted, 2, 1);
            byte masterlevel = (byte) CommandProcessorUtil.getOptionalIntArg(splitted, 3, 1);

            if (level > skill.getMaxLevel()) {
                level = (byte) skill.getMaxLevel();
            }
            if (masterlevel > skill.getMaxLevel()) {
                masterlevel = (byte) skill.getMaxLevel();
            }
            c.getPlayer().changeSkillLevel(skill, level, masterlevel);
            return 1;
        }
    }

    public static class Fame extends CommandExecute {

        @Override
        public int execute(MapleClient c, String[] splitted) {
            MapleCharacter player = c.getPlayer();
            if (splitted.length < 2) {
                c.getPlayer().dropMessage(6, "Syntax: !fame <player> <amount>");
                return 0;
            }
            MapleCharacter victim = c.getChannelServer().getPlayerStorage().getCharacterByName(splitted[1]);
            int fame = 0;
            try {
                fame = Integer.parseInt(splitted[2]);
            } catch (NumberFormatException nfe) {
                c.getPlayer().dropMessage(6, "Invalid Number...");
                return 0;
            }
            if (victim != null && player.allowedToTarget(victim)) {
                victim.addFame(fame);
                victim.updateSingleStat(MapleStat.FAME, victim.getFame());
            }
            return 1;
        }
    }

    public static class Invincible extends CommandExecute {

        @Override
        public int execute(MapleClient c, String[] splitted) {
            MapleCharacter player = c.getPlayer();
            if (player.isInvincible()) {
                player.setInvincible(false);
                player.dropMessage(6, "Invincibility deactivated.");
            } else {
                player.setInvincible(true);
                player.dropMessage(6, "Invincibility activated.");
            }
            return 1;
        }
    }

    public static class SP extends CommandExecute {

        @Override
        public int execute(MapleClient c, String[] splitted) {
            c.getPlayer().setRemainingSp(CommandProcessorUtil.getOptionalIntArg(splitted, 1, 1));
            c.getSession().write(MaplePacketCreator.updateSp(c.getPlayer(), false));
            return 1;
        }
    }

    public static class Job extends CommandExecute {

        @Override
        public int execute(MapleClient c, String[] splitted) {
            if (MapleCarnivalChallenge.getJobNameById(Integer.parseInt(splitted[1])).length() == 0) {
                c.getPlayer().dropMessage(5, "Invalid Job");
                return 0;
            }
            c.getPlayer().changeJob(Integer.parseInt(splitted[1]));
            return 1;
        }
    }

    public static class Shop extends CommandExecute {

        @Override
        public int execute(MapleClient c, String[] splitted) {
            MapleShopFactory shop = MapleShopFactory.getInstance();
            int shopId = Integer.parseInt(splitted[1]);
            if (shop.getShop(shopId) != null) {
                shop.getShop(shopId).sendShop(c);
            }
            return 1;
        }
    }

    public static class LevelUp extends CommandExecute {

        @Override
        public int execute(MapleClient c, String[] splitted) {
            if (c.getPlayer().getLevel() < 200) {
                c.getPlayer().gainExp(500000000, true, false, true);
            }
            return 1;
        }
    }

    public static class GetItem extends CommandExecute {

        @Override
        public int execute(MapleClient c, String[] splitted) {
            final int itemId = Integer.parseInt(splitted[1]);
            final short quantity = (short) CommandProcessorUtil.getOptionalIntArg(splitted, 2, 1);

            if (!c.getPlayer().isAdmin()) {
                for (int i : GameConstants.itemBlock) {
                    if (itemId == i) {
                        c.getPlayer().dropMessage(5, "Sorry but this item is blocked for your GM level.");
                        return 0;
                    }
                }
            }
            MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
            if (GameConstants.isPet(itemId)) {
                c.getPlayer().dropMessage(5, "Please purchase a pet from the cash shop instead.");
            } else if (!ii.itemExists(itemId)) {
                c.getPlayer().dropMessage(5, itemId + " does not exist");
            } else {
                Item item;
                short flag = (short) ItemFlag.LOCK.getValue();

                if (GameConstants.getInventoryType(itemId) == MapleInventoryType.EQUIP) {
                    item = ii.randomizeStats((Equip) ii.getEquipById(itemId));
                } else {
                    item = new client.inventory.Item(itemId, (byte) 0, quantity, (byte) 0);

                }
                if (!c.getPlayer().isSuperGM()) {
                    item.setFlag(flag);
                }
                if (!c.getPlayer().isAdmin()) {
                    item.setOwner(c.getPlayer().getName());
                    item.setGMLog(c.getPlayer().getName() + " used !getitem");
                }

                MapleInventoryManipulator.addbyItem(c, item);
            }
            return 1;
        }
    }

    public static class Level extends CommandExecute {

        @Override
        public int execute(MapleClient c, String[] splitted) {
            c.getPlayer().setLevel(Short.parseShort(splitted[1]));
            c.getPlayer().levelUp();
            if (c.getPlayer().getExp() < 0) {
                c.getPlayer().gainExp(-c.getPlayer().getExp(), false, false, true);
            }
            return 1;
        }
    }

    public static class StartAutoEvent extends CommandExecute {

        @Override
        public int execute(MapleClient c, String[] splitted) {
            final EventManager em = c.getChannelServer().getEventSM().getEventManager("AutomatedEvent");
            if (em != null) {
                em.scheduleRandomEvent();
            }
            return 1;
        }
    }

    public static class SetEvent extends CommandExecute {

        @Override
        public int execute(MapleClient c, String[] splitted) {
            MapleEvent.onStartEvent(c.getPlayer());
            return 1;
        }
    }

    public static class StartEvent extends CommandExecute {

        @Override
        public int execute(MapleClient c, String[] splitted) {
            if (c.getChannelServer().getEvent() == c.getPlayer().getMapId()) {
                MapleEvent.setEvent(c.getChannelServer(), false);
                c.getPlayer().dropMessage(5, "Started the event and closed off");
                return 1;
            } else {
                c.getPlayer().dropMessage(5, "!scheduleevent must've been done first, and you must be in the event map.");
                return 0;
            }
        }
    }

    public static class ScheduleEvent extends CommandExecute {

        @Override
        public int execute(MapleClient c, String[] splitted) {
            final MapleEventType type = MapleEventType.getByString(splitted[1]);
            if (type == null) {
                final StringBuilder sb = new StringBuilder("Wrong syntax: ");
                for (MapleEventType t : MapleEventType.values()) {
                    sb.append(t.name()).append(",");
                }
                c.getPlayer().dropMessage(5, sb.toString().substring(0, sb.toString().length() - 1));
                return 0;
            }
            final String msg = MapleEvent.scheduleEvent(type, c.getChannelServer());
            if (msg.length() > 0) {
                c.getPlayer().dropMessage(5, msg);
                return 0;
            }
            return 1;
        }
    }

    public static class RemoveItem extends CommandExecute {

        @Override
        public int execute(MapleClient c, String[] splitted) {
            if (splitted.length < 3) {
                c.getPlayer().dropMessage(6, "Need <name> <itemid>");
                return 0;
            }
            MapleCharacter chr = c.getChannelServer().getPlayerStorage().getCharacterByName(splitted[1]);
            if (chr == null) {
                c.getPlayer().dropMessage(6, "This player does not exist");
                return 0;
            }
            chr.removeAll(Integer.parseInt(splitted[2]), false);
            c.getPlayer().dropMessage(6, "All items with the ID " + splitted[2] + " has been removed from the inventory of " + splitted[1] + ".");
            return 1;

        }
    }

    public static class LockItem extends CommandExecute {

        @Override
        public int execute(MapleClient c, String[] splitted) {
            if (splitted.length < 3) {
                c.getPlayer().dropMessage(6, "Need <name> <itemid>");
                return 0;
            }
            MapleCharacter chr = c.getChannelServer().getPlayerStorage().getCharacterByName(splitted[1]);
            if (chr == null) {
                c.getPlayer().dropMessage(6, "This player does not exist");
                return 0;
            }
            int itemid = Integer.parseInt(splitted[2]);
            MapleInventoryType type = GameConstants.getInventoryType(itemid);
            for (Item item : chr.getInventory(type).listById(itemid)) {
                item.setFlag((byte) (item.getFlag() | ItemFlag.LOCK.getValue()));
                chr.getClient().getSession().write(MaplePacketCreator.updateSpecialItemUse(item, type.getType(), item.getPosition(), true, chr));
            }
            if (type == MapleInventoryType.EQUIP) {
                type = MapleInventoryType.EQUIPPED;
                for (Item item : chr.getInventory(type).listById(itemid)) {
                    item.setFlag((byte) (item.getFlag() | ItemFlag.LOCK.getValue()));
                    //chr.getClient().getSession().write(MaplePacketCreator.updateSpecialItemUse(item, type.getType()));
                }
            }
            c.getPlayer().dropMessage(6, "All items with the ID " + splitted[2] + " has been locked from the inventory of " + splitted[1] + ".");
            return 1;
        }
    }

    public static class KillMap extends CommandExecute {

        @Override
        public int execute(MapleClient c, String[] splitted) {
            for (MapleCharacter map : c.getPlayer().getMap().getCharactersThreadsafe()) {
                if (map != null && !map.isGM()) {
                    map.getStat().setHp((short) 0, map);
                    map.getStat().setMp((short) 0, map);
                    map.updateSingleStat(MapleStat.HP, 0);
                    map.updateSingleStat(MapleStat.MP, 0);
                }
            }
            return 1;
        }
    }

    public static class SpeakMega extends CommandExecute {

        @Override
        public int execute(MapleClient c, String[] splitted) {
            MapleCharacter victim = c.getChannelServer().getPlayerStorage().getCharacterByName(splitted[1]);
            World.Broadcast.broadcastSmega(MaplePacketCreator.serverNotice(3, victim == null ? c.getChannel() : victim.getClient().getChannel(), victim == null ? splitted[1] : victim.getName() + " : " + StringUtil.joinStringFrom(splitted, 2), true));
            return 1;
        }
    }

    public static class Speak extends CommandExecute {

        @Override
        public int execute(MapleClient c, String[] splitted) {
            MapleCharacter victim = c.getChannelServer().getPlayerStorage().getCharacterByName(splitted[1]);
            if (victim == null) {
                c.getPlayer().dropMessage(5, "unable to find '" + splitted[1]);
                return 0;
            } else {
                victim.getMap().broadcastMessage(MaplePacketCreator.getChatText(victim.getId(), StringUtil.joinStringFrom(splitted, 2), victim.isGM(), 0));
            }
            return 1;
        }
    }

    public static class Disease extends CommandExecute {

        @Override
        public int execute(MapleClient c, String[] splitted) {
            if (splitted.length < 3) {
                c.getPlayer().dropMessage(6, "!disease <type> [charname] <level> where type = SEAL/DARKNESS/WEAKEN/STUN/CURSE/POISON/SLOW/SEDUCE/REVERSE/ZOMBIFY/POTION/SHADOW/BLIND/FREEZE/POTENTIAL");
                return 0;
            }
            int type = 0;
            if (splitted[1].equalsIgnoreCase("SEAL")) {
                type = 120;
            } else if (splitted[1].equalsIgnoreCase("DARKNESS")) {
                type = 121;
            } else if (splitted[1].equalsIgnoreCase("WEAKEN")) {
                type = 122;
            } else if (splitted[1].equalsIgnoreCase("STUN")) {
                type = 123;
            } else if (splitted[1].equalsIgnoreCase("CURSE")) {
                type = 124;
            } else if (splitted[1].equalsIgnoreCase("POISON")) {
                type = 125;
            } else if (splitted[1].equalsIgnoreCase("SLOW")) {
                type = 126;
            } else if (splitted[1].equalsIgnoreCase("SEDUCE")) {
                type = 128;
            } else if (splitted[1].equalsIgnoreCase("REVERSE")) {
                type = 132;
            } else if (splitted[1].equalsIgnoreCase("ZOMBIFY")) {
                type = 133;
            } else if (splitted[1].equalsIgnoreCase("POTION")) {
                type = 134;
            } else if (splitted[1].equalsIgnoreCase("SHADOW")) {
                type = 135;
            } else if (splitted[1].equalsIgnoreCase("BLIND")) {
                type = 136;
            } else if (splitted[1].equalsIgnoreCase("FREEZE")) {
                type = 137;
            } else if (splitted[1].equalsIgnoreCase("POTENTIAL")) {
                type = 138;
            } else {
                c.getPlayer().dropMessage(6, "!disease <type> [charname] <level> where type = SEAL/DARKNESS/WEAKEN/STUN/CURSE/POISON/SLOW/SEDUCE/REVERSE/ZOMBIFY/POTION/SHADOW/BLIND/FREEZE/POTENTIAL");
                return 0;
            }
            if (splitted.length == 4) {
                MapleCharacter victim = c.getChannelServer().getPlayerStorage().getCharacterByName(splitted[2]);
                if (victim == null) {
                    c.getPlayer().dropMessage(5, "Not found.");
                    return 0;
                }
                victim.disease(type, CommandProcessorUtil.getOptionalIntArg(splitted, 3, 1));
            } else {
                for (MapleCharacter victim : c.getPlayer().getMap().getCharactersThreadsafe()) {
                    victim.disease(type, CommandProcessorUtil.getOptionalIntArg(splitted, 3, 1));
                }
            }
            return 1;
        }
    }

    public static class CloneMe extends CommandExecute {

        @Override
        public int execute(MapleClient c, String[] splitted) {
            c.getPlayer().cloneLook();
            return 1;
        }
    }

    public static class DisposeClones extends CommandExecute {

        @Override
        public int execute(MapleClient c, String[] splitted) {
            c.getPlayer().dropMessage(6, c.getPlayer().getCloneSize() + " clones disposed.");
            c.getPlayer().disposeClones();
            return 1;
        }
    }

    public static class SetInstanceProperty extends CommandExecute {

        @Override
        public int execute(MapleClient c, String[] splitted) {
            EventManager em = c.getChannelServer().getEventSM().getEventManager(splitted[1]);
            if (em == null || em.getInstances().size() <= 0) {
                c.getPlayer().dropMessage(5, "none");
            } else {
                em.setProperty(splitted[2], splitted[3]);
                for (EventInstanceManager eim : em.getInstances()) {
                    eim.setProperty(splitted[2], splitted[3]);
                }
            }
            return 1;
        }
    }

    public static class ListInstanceProperty extends CommandExecute {

        @Override
        public int execute(MapleClient c, String[] splitted) {
            EventManager em = c.getChannelServer().getEventSM().getEventManager(splitted[1]);
            if (em == null || em.getInstances().size() <= 0) {
                c.getPlayer().dropMessage(5, "none");
            } else {
                for (EventInstanceManager eim : em.getInstances()) {
                    c.getPlayer().dropMessage(5, "Event " + eim.getName() + ", eventManager: " + em.getName() + " iprops: " + eim.getProperty(splitted[2]) + ", eprops: " + em.getProperty(splitted[2]));
                }
            }
            return 0;
        }
    }

    public static class LeaveInstance extends CommandExecute {

        @Override
        public int execute(MapleClient c, String[] splitted) {
            if (c.getPlayer().getEventInstance() == null) {
                c.getPlayer().dropMessage(5, "You are not in one");
            } else {
                c.getPlayer().getEventInstance().unregisterPlayer(c.getPlayer());
            }
            return 1;
        }
    }

    public static class WhosThere extends CommandExecute {

        @Override
        public int execute(MapleClient c, String[] splitted) {
            StringBuilder builder = new StringBuilder("Players on Map: ").append(c.getPlayer().getMap().getCharactersThreadsafe().size()).append(", ");
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
            return 1;
        }
    }

    public static class StartInstance extends CommandExecute {

        @Override
        public int execute(MapleClient c, String[] splitted) {
            if (c.getPlayer().getEventInstance() != null) {
                c.getPlayer().dropMessage(5, "You are in one");
            } else if (splitted.length > 2) {
                EventManager em = c.getChannelServer().getEventSM().getEventManager(splitted[1]);
                if (em == null || em.getInstance(splitted[2]) == null) {
                    c.getPlayer().dropMessage(5, "Not exist");
                } else {
                    em.getInstance(splitted[2]).registerPlayer(c.getPlayer());
                }
            } else {
                c.getPlayer().dropMessage(5, "!startinstance [eventmanager] [eventinstance]");
            }
            return 1;

        }
    }

    public static class ResetMobs extends CommandExecute {

        @Override
        public int execute(MapleClient c, String[] splitted) {
            c.getPlayer().getMap().killAllMonsters(false);
            return 1;
        }
    }

    public static class KillMonsterByOID extends CommandExecute {

        @Override
        public int execute(MapleClient c, String[] splitted) {
            MapleMap map = c.getPlayer().getMap();
            int targetId = Integer.parseInt(splitted[1]);
            MapleMonster monster = map.getMonsterByOid(targetId);
            if (monster != null) {
                map.killMonster(monster, c.getPlayer(), false, false, (byte) 1);
            }
            return 1;
        }
    }

    public static class RemoveNPCs extends CommandExecute {

        @Override
        public int execute(MapleClient c, String[] splitted) {
            c.getPlayer().getMap().resetNPCs();
            return 1;
        }
    }

    public static class Notice extends CommandExecute {

        protected static int getNoticeType(String typestring) {
            if (typestring.equals("n")) {
                return 0;
            } else if (typestring.equals("p")) {
                return 1;
            } else if (typestring.equals("l")) {
                return 2;
            } else if (typestring.equals("nv")) {
                return 5;
            } else if (typestring.equals("v")) {
                return 5;
            } else if (typestring.equals("b")) {
                return 6;
            }
            return -1;
        }

        @Override
        public int execute(MapleClient c, String[] splitted) {
            int joinmod = 1;
            int range = -1;
            if (splitted[1].equals("m")) {
                range = 0;
            } else if (splitted[1].equals("c")) {
                range = 1;
            } else if (splitted[1].equals("w")) {
                range = 2;
            }

            int tfrom = 2;
            if (range == -1) {
                range = 2;
                tfrom = 1;
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
            sb.append(StringUtil.joinStringFrom(splitted, joinmod));

            byte[] packet = MaplePacketCreator.serverNotice(type, sb.toString());
            if (range == 0) {
                c.getPlayer().getMap().broadcastMessage(packet);
            } else if (range == 1) {
                ChannelServer.getInstance(c.getChannel()).broadcastPacket(packet);
            } else if (range == 2) {
                World.Broadcast.broadcastMessage(packet);
            }
            return 1;
        }
    }

    public static class Yellow extends CommandExecute {

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
            return 1;
        }
    }

    public static class Y extends Yellow {
    }

    public static class WhatsMyIP extends CommandExecute {

        @Override
        public int execute(MapleClient c, String[] splitted) {
            c.getPlayer().dropMessage(5, "IP: " + c.getSession().getRemoteAddress().toString().split(":")[0]);
            return 1;
        }
    }

    public static class TempBanIP extends TempBan {

        public TempBanIP() {
            ipBan = true;
        }
    }

    public static class BanIP extends Ban {

        public BanIP() {
            ipBan = true;
        }
    }

    public static class TDrops extends CommandExecute {

        @Override
        public int execute(MapleClient c, String[] splitted) {
            c.getPlayer().getMap().toggleDrops();
            return 1;
        }
    }

    public static class Warp extends CommandExecute {

        @Override
        public int execute(MapleClient c, String[] splitted) {
            MapleCharacter victim = c.getChannelServer().getPlayerStorage().getCharacterByName(splitted[1]);
            if (victim != null && c.getPlayer().getGMLevel() >= victim.getGMLevel() && !victim.inPVP() && !c.getPlayer().inPVP()) {
                if (splitted.length == 2) {
                    c.getPlayer().changeMap(victim.getMap(), victim.getMap().findClosestSpawnpoint(victim.getTruePosition()));
                } else {
                    MapleMap target = ChannelServer.getInstance(c.getChannel()).getMapFactory().getMap(Integer.parseInt(splitted[2]));
                    if (target == null) {
                        c.getPlayer().dropMessage(6, "Map does not exist");
                        return 0;
                    }
                    MaplePortal targetPortal = null;
                    if (splitted.length > 3) {
                        try {
                            targetPortal = target.getPortal(Integer.parseInt(splitted[3]));
                        } catch (IndexOutOfBoundsException e) {
                            // noop, assume the gm didn't know how many portals there are
                            c.getPlayer().dropMessage(5, "Invalid portal selected.");
                        } catch (NumberFormatException a) {
                            // noop, assume that the gm is drunk
                        }
                    }
                    if (targetPortal == null) {
                        targetPortal = target.getPortal(0);
                    }
                    victim.changeMap(target, targetPortal);
                }
            } else {
                try {
                    victim = c.getPlayer();
                    int ch = World.Find.findChannel(splitted[1]);
                    if (ch < 0) {
                        MapleMap target = c.getChannelServer().getMapFactory().getMap(Integer.parseInt(splitted[1]));
                        if (target == null) {
                            c.getPlayer().dropMessage(6, "Map does not exist");
                            return 0;
                        }
                        MaplePortal targetPortal = null;
                        if (splitted.length > 2) {
                            try {
                                targetPortal = target.getPortal(Integer.parseInt(splitted[2]));
                            } catch (IndexOutOfBoundsException e) {
                                // noop, assume the gm didn't know how many portals there are
                                c.getPlayer().dropMessage(5, "Invalid portal selected.");
                            } catch (NumberFormatException a) {
                                // noop, assume that the gm is drunk
                            }
                        }
                        if (targetPortal == null) {
                            targetPortal = target.getPortal(0);
                        }
                        c.getPlayer().changeMap(target, targetPortal);
                    } else {
                        victim = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(splitted[1]);
                        c.getPlayer().dropMessage(6, "Cross changing channel. Please wait.");
                        if (victim.getMapId() != c.getPlayer().getMapId()) {
                            final MapleMap mapp = c.getChannelServer().getMapFactory().getMap(victim.getMapId());
                            c.getPlayer().changeMap(mapp, mapp.findClosestPortal(victim.getTruePosition()));
                        }
                        c.getPlayer().changeChannel(ch);
                    }
                } catch (Exception e) {
                    c.getPlayer().dropMessage(6, "Something went wrong " + e.getMessage());
                    return 0;
                }
            }
            return 1;
        }
    }*/
}
