package scripting;

import client.MapleCharacter;
import client.MapleClient;
import client.MapleQuestStatus;
import client.MapleStat;
import client.MapleTrait.MapleTraitType;
import client.PlayerStats;
import client.Skill;
import client.SkillFactory;
import client.inventory.Equip;
import client.inventory.Item;
import client.inventory.MapleInventory;
import client.inventory.MapleInventoryIdentifier;
import client.inventory.MapleInventoryType;
import client.inventory.MaplePet;
import client.inventory.MapleRing;
import client.messages.CommandProcessor;
import com.alibaba.druid.pool.DruidDataSource;
import constants.GameConstants;
import constants.ServerConstants;
import database.DBConPool;
import handling.channel.ChannelServer;
import handling.channel.handler.InventoryHandler;
import handling.world.MapleParty;
import handling.world.MaplePartyCharacter;
import handling.world.World;
import handling.world.guild.MapleGuild;
import java.awt.Point;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import server.MapleInventoryManipulator;
import server.MapleItemInformationProvider;
import server.StructPotentialItem;
import server.custom.bossrank.BossRankInfo;
import server.custom.bossrank.BossRankManager;
import server.custom.bossrank1.BossRankInfo1;
import server.custom.bossrank1.BossRankManager1;
import server.custom.bossrank2.BossRankInfo2;
import server.custom.bossrank2.BossRankManager2;
import server.custom.bossrank3.BossRankInfo3;
import server.custom.bossrank3.BossRankManager3;
import server.custom.bossrank4.BossRankInfo4;
import server.custom.bossrank4.BossRankManager4;
import server.custom.bossrank5.BossRankInfo5;
import server.custom.bossrank5.BossRankManager5;
import server.custom.bossrank6.BossRankInfo6;
import server.custom.bossrank6.BossRankManager6;
import server.custom.bossrank7.BossRankInfo7;
import server.custom.bossrank7.BossRankManager7;
import server.custom.bossrank8.BossRankInfo8;
import server.custom.bossrank8.BossRankManager8;
import server.custom.bossrank9.BossRankInfo9;
import server.custom.bossrank9.BossRankManager9;
import server.custom.bossrank10.BossRankInfo10;
import server.custom.bossrank10.BossRankManager10;
import server.events.MapleEvent;
import server.events.MapleEventType;
import server.life.MapleLifeFactory;
import server.life.MapleMonster;
import server.life.OverrideMonsterStats;
import server.maps.Event_DojoAgent;
import server.maps.MapleMap;
import server.maps.MapleMapFactory;
import server.maps.MapleMapObject;
import server.maps.MapleReactor;
import server.maps.SavedLocationType;
import server.quest.MapleQuest;
import tools.FileoutputUtil;
import tools.packet.MaplePacketCreator;
import tools.Randomizer;
import tools.packet.PetPacket;
import tools.packet.UIPacket;

public abstract class AbstractPlayerInteraction {

    protected MapleClient c;
    protected int id, id2, mode;
    protected String script;

    public AbstractPlayerInteraction(final MapleClient c, final int id, final int id2, final String script, int mode) {
        this.c = c;
        this.id = id;
        this.id2 = id2;
        this.script = script;
        this.mode = mode;

    }
    
    
     //获取在线时间
    public int getTodayOnlineTime() {
        return this.c.getPlayer().getTodayOnlineTime();
    }

    public final MapleClient getClient() {
        return c;
    }

    public final MapleClient getC() {
        return c;
    }

    public MapleCharacter getChar() {
        return c.getPlayer();
    }

    public final ChannelServer getChannelServer() {
        return c.getChannelServer();
    }

    public final MapleCharacter getPlayer() {
        return c.getPlayer();
    }

    public final EventManager getEventManager(final String event) {
        return c.getChannelServer().getEventSM().getEventManager(event);
    }

    public final EventInstanceManager getEventInstance() {
        return c.getPlayer().getEventInstance();
    }

    public final void warp(final int map) {
        final MapleMap mapz = getWarpMap(map);
        try {
            c.getPlayer().changeMap(mapz, mapz.getPortal(Randomizer.nextInt(mapz.getPortals().size())));
        } catch (Exception e) {
            c.getPlayer().changeMap(mapz, mapz.getPortal(0));
        }
    }

    public final void warp_Instanced(final int map) {
        final MapleMap mapz = getMap_Instanced(map);
        try {
            c.getPlayer().changeMap(mapz, mapz.getPortal(Randomizer.nextInt(mapz.getPortals().size())));
        } catch (Exception e) {
            c.getPlayer().changeMap(mapz, mapz.getPortal(0));
        }
    }

    public final void instantMapWarp(final int map, final int portal) {
        final MapleMap mapz = getWarpMap(map);
        if (portal != 0 && map == c.getPlayer().getMapId()) { //test
            final Point portalPos = new Point(c.getPlayer().getMap().getPortal(portal).getPosition());
            c.getSession().write(MaplePacketCreator.instantMapWarp((byte) portal)); //until we get packet for far movement, this will do
            c.getPlayer().checkFollow();
            c.getPlayer().getMap().movePlayer(c.getPlayer(), portalPos);

        } else {
            c.getPlayer().changeMap(mapz, mapz.getPortal(portal));
        }
    }

    public final void warp(final int map, final int portal) {
        final MapleMap mapz = getWarpMap(map);
        if (portal != 0 && map == c.getPlayer().getMapId()) { //test
            final Point portalPos = new Point(c.getPlayer().getMap().getPortal(portal).getPosition());
            if (portalPos.distanceSq(getPlayer().getTruePosition()) < 90000.0) { //estimation
                c.getSession().write(MaplePacketCreator.instantMapWarp((byte) portal)); //until we get packet for far movement, this will do
                c.getPlayer().checkFollow();
                c.getPlayer().getMap().movePlayer(c.getPlayer(), portalPos);
            } else {
                c.getPlayer().changeMap(mapz, mapz.getPortal(portal));
            }
        } else {
            c.getPlayer().changeMap(mapz, mapz.getPortal(portal));
        }
    }

    public final void warpS(final int map, final int portal) {
        final MapleMap mapz = getWarpMap(map);
        c.getPlayer().changeMap(mapz, mapz.getPortal(portal));
    }

    public final void warp(final int map, String portal) {
        final MapleMap mapz = getWarpMap(map);
        if (map == 109060000 || map == 109060002 || map == 109060004) {
            portal = mapz.getSnowballPortal();
        }
        if (map == c.getPlayer().getMapId()) { //test
            final Point portalPos = new Point(c.getPlayer().getMap().getPortal(portal).getPosition());
            if (portalPos.distanceSq(getPlayer().getTruePosition()) < 90000.0) { //estimation
                c.getPlayer().checkFollow();
                c.getSession().write(MaplePacketCreator.instantMapWarp((byte) c.getPlayer().getMap().getPortal(portal).getId()));
                c.getPlayer().getMap().movePlayer(c.getPlayer(), new Point(c.getPlayer().getMap().getPortal(portal).getPosition()));
            } else {
                c.getPlayer().changeMap(mapz, mapz.getPortal(portal));
            }
        } else {
            c.getPlayer().changeMap(mapz, mapz.getPortal(portal));
        }
    }

    public final void warpS(final int map, String portal) {
        final MapleMap mapz = getWarpMap(map);
        if (map == 109060000 || map == 109060002 || map == 109060004) {
            portal = mapz.getSnowballPortal();
        }
        c.getPlayer().changeMap(mapz, mapz.getPortal(portal));
    }

    public final void warpMap(final int mapid, final int portal) {
        final MapleMap map = getMap(mapid);
        for (MapleCharacter chr : c.getPlayer().getMap().getCharactersThreadsafe()) {
            chr.changeMap(map, map.getPortal(portal));
        }
    }

    public final void playPortalSE() {
        c.getSession().write(MaplePacketCreator.showOwnBuffEffect(0, 7, 1, 1));
    }

    private final MapleMap getWarpMap(final int map) {
        return ChannelServer.getInstance(c.getChannel()).getMapFactory().getMap(map);
    }

    public final MapleMap getMap() {
        return c.getPlayer().getMap();
    }

    public final MapleMap getMap(final int map) {
        return getWarpMap(map);
    }

    public final MapleMap getMap_Instanced(final int map) {
        return c.getPlayer().getEventInstance() == null ? getMap(map) : c.getPlayer().getEventInstance().getMapInstance(map);
    }

    public final MapleMap getMapGuilds_Instanced(final int map) {
        return getMapFactory().getInstanceMap(map);
    }

    public final MapleMap createInstanceMap(final int mapid, int assignedid) {
        return this.getMapFactory().CreateInstanceMap(mapid, true, true, true, assignedid);
    }

    public void spawnMonster(final int id, final int qty) {
        spawnMob(id, qty, c.getPlayer().getTruePosition());
    }

    public final void spawnMobOnMap(final int id, final int qty, final int x, final int y, final int map) {
        for (int i = 0; i < qty; i++) {
            getMap(map).spawnMonsterOnGroundBelow(MapleLifeFactory.getMonster(id), new Point(x, y));
        }
    }

    public final void spawnMob(final int id, final int qty, final int x, final int y) {
        spawnMob(id, qty, new Point(x, y));
    }

    public final void spawnMob(final int id, final int x, final int y) {
        spawnMob(id, 1, new Point(x, y));
    }

    private final void spawnMob(final int id, final int qty, final Point pos) {
        for (int i = 0; i < qty; i++) {
            c.getPlayer().getMap().spawnMonsterOnGroundBelow(MapleLifeFactory.getMonster(id), pos);
        }
    }

    public final void killMob(int ids) {
        c.getPlayer().getMap().killMonster(ids);
    }

    public final void killAllMob() {
        c.getPlayer().getMap().killAllMonsters(true);
    }

    public final void addHP(final int delta) {
        c.getPlayer().addHP(delta);
    }

    public final int getPlayerStat(final String type) {
        if (type.equals("LVL")) {
            return c.getPlayer().getLevel();
        } else if (type.equals("STR")) {
            return c.getPlayer().getStat().getStr();
        } else if (type.equals("DEX")) {
            return c.getPlayer().getStat().getDex();
        } else if (type.equals("INT")) {
            return c.getPlayer().getStat().getInt();
        } else if (type.equals("LUK")) {
            return c.getPlayer().getStat().getLuk();
        } else if (type.equals("HP")) {
            return c.getPlayer().getStat().getHp();
        } else if (type.equals("MP")) {
            return c.getPlayer().getStat().getMp();
        } else if (type.equals("MAXHP")) {
            return c.getPlayer().getStat().getMaxHp();
        } else if (type.equals("MAXMP")) {
            return c.getPlayer().getStat().getMaxMp();
        } else if (type.equals("RAP")) {
            return c.getPlayer().getRemainingAp();
        } else if (type.equals("RSP")) {
            return c.getPlayer().getRemainingSp();
        } else if (type.equals("GID")) {
            return c.getPlayer().getGuildId();
        } else if (type.equals("GRANK")) {
            return c.getPlayer().getGuildRank();
        } else if (type.equals("ARANK")) {
            return c.getPlayer().getAllianceRank();
        } else if (type.equals("GM")) {
            return c.getPlayer().isGM() ? 1 : 0;
        } else if (type.equals("ADMIN")) {
            return c.getPlayer().isAdmin() ? 1 : 0;
        } else if (type.equals("GENDER")) {
            return c.getPlayer().getGender();
        } else if (type.equals("FACE")) {
            return c.getPlayer().getFace();
        } else if (type.equals("HAIR")) {
            return c.getPlayer().getHair();
        }
        return -1;
    }

    public final String getName() {
        return c.getPlayer().getName();
    }

    public final boolean haveItem(final int itemid) {
        return haveItem(itemid, 1);
    }

    public final boolean haveItem(final int itemid, final int quantity) {
        return haveItem(itemid, quantity, false, true);
    }

    public final boolean haveItem(final int itemid, final int quantity, final boolean checkEquipped, final boolean greaterOrEquals) {
        return c.getPlayer().haveItem(itemid, quantity, checkEquipped, greaterOrEquals);
    }

    public final boolean canHold() {
        for (int i = 1; i <= 5; i++) {
            if (c.getPlayer().getInventory(MapleInventoryType.getByType((byte) i)).getNextFreeSlot() <= -1) {
                return false;
            }
        }
        return true;
    }

    public final boolean canHoldSlots(final int slot) {
        for (int i = 1; i <= 5; i++) {
            if (c.getPlayer().getInventory(MapleInventoryType.getByType((byte) i)).isFull(slot)) {
                return false;
            }
        }
        return true;
    }

    public final boolean canHold(final int itemid) {
        return c.getPlayer().getInventory(GameConstants.getInventoryType(itemid)).getNextFreeSlot() > -1;
    }

    public final boolean canHold(final int itemid, final int quantity) {
        return MapleInventoryManipulator.checkSpace(c, itemid, quantity, "");
    }

    public final MapleQuestStatus getQuestRecord(final int id) {
        return c.getPlayer().getQuestNAdd(MapleQuest.getInstance(id));
    }

    public final MapleQuestStatus getQuestNoRecord(final int id) {
        return c.getPlayer().getQuestNoAdd(MapleQuest.getInstance(id));
    }

    public final byte getQuestStatus(final int id) {
        return c.getPlayer().getQuestStatus(id);
    }

    public final boolean isQuestActive(final int id) {
        return getQuestStatus(id) == 1;
    }

    public final boolean isQuestFinished(final int id) {
        return getQuestStatus(id) == 2;
    }

    public final void showQuestMsg(final String msg) {
        c.getSession().write(MaplePacketCreator.showQuestMsg(msg));
    }

    public final void forceStartQuest(final int id, final String data) {
        MapleQuest.getInstance(id).forceStart(c.getPlayer(), 0, data);
    }

    public final void forceStartQuest(final int id, final int data, final boolean filler) {
        MapleQuest.getInstance(id).forceStart(c.getPlayer(), 0, filler ? String.valueOf(data) : null);
    }

    public void forceStartQuest(final int id) {
        MapleQuest.getInstance(id).forceStart(c.getPlayer(), 0, null);
    }

    public void forceCompleteQuest(final int id) {
        MapleQuest.getInstance(id).forceComplete(getPlayer(), 0);
    }

    public void spawnNpc(final int npcId) {
        c.getPlayer().getMap().spawnNpc(npcId, c.getPlayer().getPosition());
    }

    public final void spawnNpc(final int npcId, final int x, final int y) {
        c.getPlayer().getMap().spawnNpc(npcId, new Point(x, y));
    }

    public final void spawnNpc(final int npcId, final Point pos) {
        c.getPlayer().getMap().spawnNpc(npcId, pos);
    }

    public final void removeNpc(final int mapid, final int npcId) {
        c.getChannelServer().getMapFactory().getMap(mapid).removeNpc(npcId);
    }

    public final void removeNpc(final int npcId) {
        c.getPlayer().getMap().removeNpc(npcId);
    }

    public final void forceStartReactor(final int mapid, final int id) {
        MapleMap map = c.getChannelServer().getMapFactory().getMap(mapid);
        MapleReactor react;

        for (final MapleMapObject remo : map.getAllReactorsThreadsafe()) {
            react = (MapleReactor) remo;
            if (react.getReactorId() == id) {
                react.forceStartReactor(c);
                break;
            }
        }
    }

    public final void destroyReactor(final int mapid, final int id) {
        MapleMap map = c.getChannelServer().getMapFactory().getMap(mapid);
        MapleReactor react;

        for (final MapleMapObject remo : map.getAllReactorsThreadsafe()) {
            react = (MapleReactor) remo;
            if (react.getReactorId() == id) {
                react.hitReactor(c);
                break;
            }
        }
    }

    public final void hitReactor(final int mapid, final int id) {
        MapleMap map = c.getChannelServer().getMapFactory().getMap(mapid);
        MapleReactor react;

        for (final MapleMapObject remo : map.getAllReactorsThreadsafe()) {
            react = (MapleReactor) remo;
            if (react.getReactorId() == id) {
                react.hitReactor(c);
                break;
            }
        }
    }

    public final int getJob() {
        return c.getPlayer().getJob();
    }

    public final void gainNX(final int amount) {
        c.getPlayer().modifyCSPoints(1, amount, true);
    }

    public final void gainNX2(final int amount) {
        c.getPlayer().modifyCSPoints(2, amount, true);
    }

    public final void gainItemPeriod(final int id, final short quantity, final int period) { //period is in days
        gainItem(id, quantity, false, period, -1, "");
    }

    public final void gainItemPeriod(final int id, final short quantity, final long period, final String owner) { //period is in days
        gainItem(id, quantity, false, period, -1, owner);
    }

    public final void gainItem(final int id, final short quantity) {
        gainItem(id, quantity, false, 0, -1, "");
    }

    public final void gainItem(final int id, final short quantity, final boolean randomStats) {
        gainItem(id, quantity, randomStats, 0, -1, "");
    }

    public final void gainItem(final int id, final short quantity, final boolean randomStats, final int slots) {
        gainItem(id, quantity, randomStats, 0, slots, "");
    }

    public final void gainItem(final int id, final short quantity, final long period) {
        gainItem(id, quantity, false, period, -1, "");
    }

    public final void gainItemA(final int id, final short quantity, final long period) {
        gainItemA(id, quantity, false, period, -1, "");
    }

    public final void gainItem(final int id, final short quantity, final boolean randomStats, final long period, final int slots) {
        gainItem(id, quantity, randomStats, period, slots, "");
    }

    public final void gainItem(final int id, final short quantity, final boolean randomStats, final long period, final int slots, final String owner) {
        gainItem(id, quantity, randomStats, period, slots, owner, c);
    }

    public final void gainItemA(final int id, final short quantity, final boolean randomStats, final long period, final int slots, final String owner) {
        gainItemA(id, quantity, randomStats, period, slots, owner, c);
    }

    public final void gainItemA(final int id, final short quantity, final boolean randomStats, final long period, final int slots, final String owner, final MapleClient cg) {
        if (quantity >= 0) {
            final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
            final MapleInventoryType type = GameConstants.getInventoryType(id);

            if (!MapleInventoryManipulator.checkSpace(cg, id, quantity, "")) {
                return;
            }
            if (type.equals(MapleInventoryType.EQUIP) && !GameConstants.isThrowingStar(id) && !GameConstants.isBullet(id)) {
                final Equip item = (Equip) (randomStats ? ii.randomizeStats((Equip) ii.getEquipById(id)) : ii.getEquipById(id));
                if (period > 0) {
                    item.setExpiration(System.currentTimeMillis() + (period * 1 * 60 * 60 * 1000));
                }
                if (slots > 0) {
                    item.setUpgradeSlots((byte) (item.getUpgradeSlots() + slots));
                }
                if (owner != null) {
                    item.setOwner(owner);
                }
                item.setGMLog("Received from interaction " + this.id + " (" + id2 + ") on " + FileoutputUtil.CurrentReadable_Time());
                final String name = ii.getName(id);
                if (id / 10000 == 114 && name != null && name.length() > 0) { //medal
                    final String msg = "你已获得稱號 <" + name + ">";
                    cg.getPlayer().dropMessage(-1, msg);
                    cg.getPlayer().dropMessage(5, msg);
                }
                MapleInventoryManipulator.addbyItem(cg, item.copy());
            } else {
                MapleInventoryManipulator.addById(cg, id, quantity, owner == null ? "" : owner, null, period, "Received from interaction " + this.id + " (" + id2 + ") on " + FileoutputUtil.CurrentReadable_Date());
            }
        } else {
            MapleInventoryManipulator.removeById(cg, GameConstants.getInventoryType(id), id, -quantity, true, false);
        }
        cg.getSession().write(MaplePacketCreator.getShowItemGain(id, quantity, true));
    }

    public final void gainItem(final int id, final short quantity, final boolean randomStats, final long period, final int slots, final String owner, final MapleClient cg) {
        if (quantity >= 0) {
            final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
            final MapleInventoryType type = GameConstants.getInventoryType(id);

            if (!MapleInventoryManipulator.checkSpace(cg, id, quantity, "")) {
                return;
            }
            if (type.equals(MapleInventoryType.EQUIP) && !GameConstants.isThrowingStar(id) && !GameConstants.isBullet(id)) {
                final Equip item = (Equip) (randomStats ? ii.randomizeStats((Equip) ii.getEquipById(id)) : ii.getEquipById(id));
                if (period > 0) {
                    item.setExpiration(System.currentTimeMillis() + (period * 24 * 60 * 60 * 1000));
                }
                if (slots > 0) {
                    item.setUpgradeSlots((byte) (item.getUpgradeSlots() + slots));
                }
                if (owner != null) {
                    item.setOwner(owner);
                }
                item.setGMLog("Received from interaction " + this.id + " (" + id2 + ") on " + FileoutputUtil.CurrentReadable_Time());
                final String name = ii.getName(id);
                if (id / 10000 == 114 && name != null && name.length() > 0) { //medal
                    final String msg = "你已获得稱號 <" + name + ">";
                    cg.getPlayer().dropMessage(-1, msg);
                    cg.getPlayer().dropMessage(5, msg);
                }
                MapleInventoryManipulator.addbyItem(cg, item.copy());
            } else {
                final MaplePet pet;
                if (GameConstants.isPet(id)) {
                    pet = MaplePet.createPet(id, MapleInventoryIdentifier.getInstance());
                } else {
                    pet = null;
                }

                MapleInventoryManipulator.addById(cg, id, quantity, owner == null ? "" : owner, pet, period, "Received from interaction " + this.id + " (" + id2 + ") on " + FileoutputUtil.CurrentReadable_Date());
            }
        } else {
            MapleInventoryManipulator.removeById(cg, GameConstants.getInventoryType(id), id, -quantity, true, false);
        }
        cg.getSession().write(MaplePacketCreator.getShowItemGain(id, quantity, true));
    }

    public final void gainItemPeriodB(final int id, final short quantity, final int period, int flag) { //period is in days
        gainItemB(id, quantity, false, period, -1, "", 0, 0, 0, 0, 0, 0, flag);
    }

    public final void gainItemPeriodB(final int id, final short quantity, final int period, int watk, int matk, int str, int luk, int dex, int int_, int flag) { //period is in days
        gainItemB(id, quantity, false, period, -1, "", watk, matk, str, luk, dex, int_, flag);
    }

    public final void gainItemB(final int id, final short quantity, final boolean randomStats, final long period, final int slots, final String owner, int watk, int matk, int str, int luk, int dex, int int_, int flag) {
        gainItemB(id, quantity, randomStats, period, slots, owner, c, watk, matk, str, luk, dex, int_, flag);
    }

    public final void gainItemB(final int id, final short quantity, final boolean randomStats, final long period, final int slots, final String owner, final MapleClient cg, int watk, int matk, int str, int luk, int dex, int int_, int flag) {
        if (quantity >= 0) {
            final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
            final MapleInventoryType type = GameConstants.getInventoryType(id);

            if (!MapleInventoryManipulator.checkSpace(cg, id, quantity, "")) {
                return;
            }
            if (type.equals(MapleInventoryType.EQUIP) && !GameConstants.isThrowingStar(id) && !GameConstants.isBullet(id)) {
                final Equip item = (Equip) (randomStats ? ii.randomizeStats((Equip) ii.getEquipById(id)) : ii.getEquipById(id));
                if (period > 0) {
                    item.setExpiration(System.currentTimeMillis() + (period * 24 * 60 * 60 * 1000));
                }
                if (slots > 0) {
                    item.setUpgradeSlots((byte) (item.getUpgradeSlots() + slots));
                }
                if (owner != null) {
                    item.setOwner(owner);
                }
                if (watk > 0) {
                    item.setWatk((short) watk);
                }
                if (matk > 0) {
                    item.setMatk((short) matk);
                }
                if (str > 0) {
                    item.setStr((short) str);
                }
                if (str > 0) {
                    item.setStr((short) str);
                }
                if (luk > 0) {
                    item.setLuk((short) luk);
                }
                if (dex > 0) {
                    item.setDex((short) dex);
                }
                if (int_ > 0) {
                    item.setInt((short) int_);
                }
                if (flag > 0) {
                    item.setFlag((short) flag);
                }
                item.setGMLog("Received from interaction " + this.id + " (" + id2 + ") on " + FileoutputUtil.CurrentReadable_Time());
                final String name = ii.getName(id);
                if (id / 10000 == 114 && name != null && name.length() > 0) { //medal
                    final String msg = "你已获得稱號 <" + name + ">";
                    cg.getPlayer().dropMessage(-1, msg);
                    cg.getPlayer().dropMessage(5, msg);
                }
                MapleInventoryManipulator.addbyItem(cg, item.copy());
            } else {
                final MaplePet pet;
                if (GameConstants.isPet(id)) {
                    pet = MaplePet.createPet(id, MapleInventoryIdentifier.getInstance());
                } else {
                    pet = null;
                }

                MapleInventoryManipulator.addById(cg, id, quantity, owner == null ? "" : owner, pet, period, "Received from interaction " + this.id + " (" + id2 + ") on " + FileoutputUtil.CurrentReadable_Date());
            }
        } else {
            MapleInventoryManipulator.removeById(cg, GameConstants.getInventoryType(id), id, -quantity, true, false);
        }
        cg.getSession().write(MaplePacketCreator.getShowItemGain(id, quantity, true));
    }

    public final boolean removeItem(final int id) { //quantity 1
        if (MapleInventoryManipulator.removeById_Lock(c, GameConstants.getInventoryType(id), id)) {
            c.getSession().write(MaplePacketCreator.getShowItemGain(id, (short) -1, true));
            return true;
        }
        return false;
    }

    public final void changeMusic(final String songName) {
        getPlayer().getMap().broadcastMessage(MaplePacketCreator.musicChange(songName));
    }

    public final void worldMessage(final int type, final String message) {
        World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(type, message));
    }

    // default playerMessage and mapMessage to use type 5
    public final void playerMessage(final String message) {
        playerMessage(5, message);
    }

    public final void mapMessage(final String message) {
        mapMessage(5, message);
    }

    public final void guildMessage(final String message) {
        guildMessage(5, message);
    }

    public final void playerMessage(final int type, final String message) {
        c.getPlayer().dropMessage(type, message);
    }

    public final void mapMessage(final int type, final String message) {
        c.getPlayer().getMap().broadcastMessage(MaplePacketCreator.serverNotice(type, message));
    }

    public final void guildMessage(final int type, final String message) {
        if (getPlayer().getGuildId() > 0) {
            World.Guild.guildPacket(getPlayer().getGuildId(), MaplePacketCreator.serverNotice(type, message));
        }
    }

    public final MapleGuild getGuild() {
        return getGuild(getPlayer().getGuildId());
    }

    public final MapleGuild getGuild(int guildid) {
        return World.Guild.getGuild(guildid);
    }

    public final MapleParty getParty() {
        return c.getPlayer().getParty();
    }

    public final int getCurrentPartyId(int mapid) {
        return getMap(mapid).getCurrentPartyId();
    }

    public final boolean isLeader() {
        if (getPlayer().getParty() == null) {
            return false;
        }
        return getParty().getLeader().getId() == c.getPlayer().getId();
    }

    public final boolean isAllPartyMembersAllowedJob(final int job) {
        if (c.getPlayer().getParty() == null) {
            return false;
        }
        for (final MaplePartyCharacter mem : c.getPlayer().getParty().getMembers()) {
            if (mem.getJobId() / 100 != job) {
                return false;
            }
        }
        return true;
    }

    public final boolean allMembersHere() {
        if (c.getPlayer().getParty() == null) {
            return false;
        }
        for (final MaplePartyCharacter mem : c.getPlayer().getParty().getMembers()) {
            final MapleCharacter chr = c.getPlayer().getMap().getCharacterById(mem.getId());
            if (chr == null) {
                return false;
            }
        }
        return true;
    }
    
    /*
     * 自定义改变怪物的血和经验
     */
    public void spawnMobStats(int mobId, long newhp, int newExp) {
        spawnMobStats(mobId, 1, newhp, newExp, c.getPlayer().getTruePosition());
    }

    public void spawnMobStats(int mobId, int quantity, long newhp, int newExp) {
        spawnMobStats(mobId, quantity, newhp, newExp, c.getPlayer().getTruePosition());
    }

    public void spawnMobStats(int mobId, int quantity, long newhp, int newExp, int x, int y) {
        spawnMobStats(mobId, quantity, newhp, newExp, new Point(x, y));
    }

    public void spawnMobStats(int mobId, int quantity, long newhp, int newExp, Point pos) {
        for (int i = 0; i < quantity; i++) {
            MapleMonster mob = MapleLifeFactory.getMonster(mobId);
            if (mob == null) {
                if (c.getPlayer().isAdmin()) {
                    c.getPlayer().dropMessage(6, "[系统提示] spawnMobStats召唤怪物出错，ID为: " + mobId + " 怪物不存在！");
                }
                continue;
            }
            OverrideMonsterStats overrideStats = new OverrideMonsterStats(newhp, mob.getMobMaxMp(), newExp <= 0 ? mob.getMobExp() : newExp, false);
            mob.setOverrideStats(overrideStats);
            c.getPlayer().getMap().spawnMonsterOnGroundBelow(mob, pos);
        }
    }

    public final void warpParty(final int mapId) {
        if (getPlayer().getParty() == null || getPlayer().getParty().getMembers().size() == 1) {
            warp(mapId, 0);
            return;
        }
        final MapleMap target = getMap(mapId);
        final int cMap = getPlayer().getMapId();
        for (final MaplePartyCharacter chr : getPlayer().getParty().getMembers()) {
            final MapleCharacter curChar = getChannelServer().getPlayerStorage().getCharacterById(chr.getId());
            if (curChar != null && (curChar.getMapId() == cMap || curChar.getEventInstance() == getPlayer().getEventInstance())) {
                curChar.changeMap(target, target.getPortal(0));
            }
        }
    }

    public final void warpParty(final int mapId, final int portal) {
        if (getPlayer().getParty() == null || getPlayer().getParty().getMembers().size() == 1) {
            if (portal < 0) {
                warp(mapId);
            } else {
                warp(mapId, portal);
            }
            return;
        }
        final boolean rand = portal < 0;
        final MapleMap target = getMap(mapId);
        final int cMap = getPlayer().getMapId();
        for (final MaplePartyCharacter chr : getPlayer().getParty().getMembers()) {
            final MapleCharacter curChar = getChannelServer().getPlayerStorage().getCharacterById(chr.getId());
            if (curChar != null && (curChar.getMapId() == cMap || curChar.getEventInstance() == getPlayer().getEventInstance())) {
                if (rand) {
                    try {
                        curChar.changeMap(target, target.getPortal(Randomizer.nextInt(target.getPortals().size())));
                    } catch (Exception e) {
                        curChar.changeMap(target, target.getPortal(0));
                    }
                } else {
                    curChar.changeMap(target, target.getPortal(portal));
                }
            }
        }
    }

    public final void warpParty_Instanced(final int mapId) {
        if (getPlayer().getParty() == null || getPlayer().getParty().getMembers().size() == 1) {
            warp_Instanced(mapId);
            return;
        }
        final MapleMap target = getMap_Instanced(mapId);

        final int cMap = getPlayer().getMapId();
        for (final MaplePartyCharacter chr : getPlayer().getParty().getMembers()) {
            final MapleCharacter curChar = getChannelServer().getPlayerStorage().getCharacterById(chr.getId());
            if (curChar != null && (curChar.getMapId() == cMap || curChar.getEventInstance() == getPlayer().getEventInstance())) {
                curChar.changeMap(target, target.getPortal(0));
            }
        }
    }

    public void gainMeso(int gain) {
        c.getPlayer().gainMeso(gain, true, true);
    }

    public void gainExp(int gain) {
        c.getPlayer().gainExp(gain, true, true, true);
    }

    public void gainExpR(int gain) {
        c.getPlayer().gainExp(gain * c.getChannelServer().getExpRate(), true, true, true);
    }

    public final void givePartyItems(final int id, final short quantity, final List<MapleCharacter> party) {
        for (MapleCharacter chr : party) {
            if (quantity >= 0) {
                MapleInventoryManipulator.addById(chr.getClient(), id, quantity, "Received from party interaction " + id + " (" + id2 + ")");
            } else {
                MapleInventoryManipulator.removeById(chr.getClient(), GameConstants.getInventoryType(id), id, -quantity, true, false);
            }
            chr.getClient().getSession().write(MaplePacketCreator.getShowItemGain(id, quantity, true));
        }
    }

    public void addPartyTrait(String t, int e, final List<MapleCharacter> party) {
        for (final MapleCharacter chr : party) {
            chr.getTrait(MapleTraitType.valueOf(t)).addExp(e, chr);
        }
    }

    public void addPartyTrait(String t, int e) {
        if (getPlayer().getParty() == null || getPlayer().getParty().getMembers().size() == 1) {
            addTrait(t, e);
            return;
        }
        final int cMap = getPlayer().getMapId();
        for (final MaplePartyCharacter chr : getPlayer().getParty().getMembers()) {
            final MapleCharacter curChar = getChannelServer().getPlayerStorage().getCharacterById(chr.getId());
            if (curChar != null && (curChar.getMapId() == cMap || curChar.getEventInstance() == getPlayer().getEventInstance())) {
                curChar.getTrait(MapleTraitType.valueOf(t)).addExp(e, curChar);
            }
        }
    }

    public void addTrait(String t, int e) {
        getPlayer().getTrait(MapleTraitType.valueOf(t)).addExp(e, getPlayer());
    }

    public final void givePartyItems(final int id, final short quantity) {
        givePartyItems(id, quantity, false);
    }

    public final void givePartyItems(final int id, final short quantity, final boolean removeAll) {
        if (getPlayer().getParty() == null || getPlayer().getParty().getMembers().size() == 1) {
            gainItem(id, (short) (removeAll ? -getPlayer().itemQuantity(id) : quantity));
            return;
        }

        final int cMap = getPlayer().getMapId();
        for (final MaplePartyCharacter chr : getPlayer().getParty().getMembers()) {
            final MapleCharacter curChar = getChannelServer().getPlayerStorage().getCharacterById(chr.getId());
            if (curChar != null && (curChar.getMapId() == cMap || curChar.getEventInstance() == getPlayer().getEventInstance())) {
                gainItem(id, (short) (removeAll ? -curChar.itemQuantity(id) : quantity), false, 0, 0, "", curChar.getClient());
            }
        }
    }

    public final void givePartyExp_PQ(final int maxLevel, final double mod, final List<MapleCharacter> party) {
        for (final MapleCharacter chr : party) {
            final int amount = (int) Math.round(GameConstants.getExpNeededForLevel(chr.getLevel() > maxLevel ? (maxLevel + ((maxLevel - chr.getLevel()) / 10)) : chr.getLevel()) / (Math.min(chr.getLevel(), maxLevel) / 5.0) / (mod * 2.0));
            chr.gainExp(amount * c.getChannelServer().getExpRate(), true, true, true);
        }
    }

    public final void gainExp_PQ(final int maxLevel, final double mod) {
        final int amount = (int) Math.round(GameConstants.getExpNeededForLevel(getPlayer().getLevel() > maxLevel ? (maxLevel + (getPlayer().getLevel() / 10)) : getPlayer().getLevel()) / (Math.min(getPlayer().getLevel(), maxLevel) / 10.0) / mod);
        gainExp(amount * c.getChannelServer().getExpRate());
    }

    public final void givePartyExp_PQ(final int maxLevel, final double mod) {
        if (getPlayer().getParty() == null || getPlayer().getParty().getMembers().size() == 1) {
            final int amount = (int) Math.round(GameConstants.getExpNeededForLevel(getPlayer().getLevel() > maxLevel ? (maxLevel + (getPlayer().getLevel() / 10)) : getPlayer().getLevel()) / (Math.min(getPlayer().getLevel(), maxLevel) / 10.0) / mod);
            gainExp(amount * c.getChannelServer().getExpRate());
            return;
        }
        final int cMap = getPlayer().getMapId();
        for (final MaplePartyCharacter chr : getPlayer().getParty().getMembers()) {
            final MapleCharacter curChar = getChannelServer().getPlayerStorage().getCharacterById(chr.getId());
            if (curChar != null && (curChar.getMapId() == cMap || curChar.getEventInstance() == getPlayer().getEventInstance())) {
                final int amount = (int) Math.round(GameConstants.getExpNeededForLevel(curChar.getLevel() > maxLevel ? (maxLevel + (curChar.getLevel() / 10)) : curChar.getLevel()) / (Math.min(curChar.getLevel(), maxLevel) / 10.0) / mod);
                curChar.gainExp(amount * c.getChannelServer().getExpRate(), true, true, true);
            }
        }
    }

    public final void givePartyExp(final int amount, final List<MapleCharacter> party) {
        for (final MapleCharacter chr : party) {
            chr.gainExp(amount * c.getChannelServer().getExpRate(), true, true, true);
        }
    }

    public final void givePartyExp(final int amount) {
        if (getPlayer().getParty() == null || getPlayer().getParty().getMembers().size() == 1) {
            gainExp(amount * c.getChannelServer().getExpRate());
            return;
        }
        final int cMap = getPlayer().getMapId();
        for (final MaplePartyCharacter chr : getPlayer().getParty().getMembers()) {
            final MapleCharacter curChar = getChannelServer().getPlayerStorage().getCharacterById(chr.getId());
            if (curChar != null && (curChar.getMapId() == cMap || curChar.getEventInstance() == getPlayer().getEventInstance())) {
                curChar.gainExp(amount * c.getChannelServer().getExpRate(), true, true, true);
            }
        }
    }

    public final void givePartyNX(final int amount, final List<MapleCharacter> party) {
        for (final MapleCharacter chr : party) {
            chr.modifyCSPoints(1, amount, true);
        }
    }

    public final void givePartyNX2(final int amount, final List<MapleCharacter> party) {
        for (final MapleCharacter chr : party) {
            chr.modifyCSPoints(2, amount, true);
        }
    }

    public final void givePartyNX2(final int amount) {
        if (getPlayer().getParty() == null || getPlayer().getParty().getMembers().size() == 1) {
            gainNX2(amount);
            return;
        }
        final int cMap = getPlayer().getMapId();
        for (final MaplePartyCharacter chr : getPlayer().getParty().getMembers()) {
            final MapleCharacter curChar = getChannelServer().getPlayerStorage().getCharacterById(chr.getId());
            if (curChar != null && (curChar.getMapId() == cMap || curChar.getEventInstance() == getPlayer().getEventInstance())) {
                curChar.modifyCSPoints(2, amount, true);
            }
        }
    }

    public final void givePartyNX(final int amount) {
        if (getPlayer().getParty() == null || getPlayer().getParty().getMembers().size() == 1) {
            gainNX(amount);
            return;
        }
        final int cMap = getPlayer().getMapId();
        for (final MaplePartyCharacter chr : getPlayer().getParty().getMembers()) {
            final MapleCharacter curChar = getChannelServer().getPlayerStorage().getCharacterById(chr.getId());
            if (curChar != null && (curChar.getMapId() == cMap || curChar.getEventInstance() == getPlayer().getEventInstance())) {
                curChar.modifyCSPoints(1, amount, true);
            }
        }
    }

    public final void endPartyQuest(final int amount, final List<MapleCharacter> party) {
        for (final MapleCharacter chr : party) {
            chr.endPartyQuest(amount);
        }
    }

    public final void endPartyQuest(final int amount) {
        if (getPlayer().getParty() == null || getPlayer().getParty().getMembers().size() == 1) {
            getPlayer().endPartyQuest(amount);
            return;
        }
        final int cMap = getPlayer().getMapId();
        for (final MaplePartyCharacter chr : getPlayer().getParty().getMembers()) {
            final MapleCharacter curChar = getChannelServer().getPlayerStorage().getCharacterById(chr.getId());
            if (curChar != null && (curChar.getMapId() == cMap || curChar.getEventInstance() == getPlayer().getEventInstance())) {
                curChar.endPartyQuest(amount);
            }
        }
    }

    public final void removeFromParty(final int id, final List<MapleCharacter> party) {
        for (final MapleCharacter chr : party) {
            final int possesed = chr.getInventory(GameConstants.getInventoryType(id)).countById(id);
            if (possesed > 0) {
                MapleInventoryManipulator.removeById(c, GameConstants.getInventoryType(id), id, possesed, true, false);
                chr.getClient().getSession().write(MaplePacketCreator.getShowItemGain(id, (short) -possesed, true));
            }
        }
    }

    public final void removeFromParty(final int id) {
        givePartyItems(id, (short) 0, true);
    }

    public final void useSkill(final int skill, final int level) {
        if (level <= 0) {
            return;
        }
        SkillFactory.getSkill(skill).getEffect(level).applyTo(c.getPlayer());
    }

    public final void useItem(final int id) {
        MapleItemInformationProvider.getInstance().getItemEffect(id).applyTo(c.getPlayer());
        c.getSession().write(UIPacket.getStatusMsg(id));
    }

    public final void cancelItem(final int id) {
        c.getPlayer().cancelEffect(MapleItemInformationProvider.getInstance().getItemEffect(id), false, -1);
    }

    public final int getMorphState() {
        return c.getPlayer().getMorphState();
    }

    public final void removeAll(final int id) {
        c.getPlayer().removeAll(id);
    }

    public final void gainCloseness(final int closeness, final int index) {
        final MaplePet pet = getPlayer().getPet(index);
        if (pet != null) {
            pet.setCloseness(pet.getCloseness() + (closeness * getChannelServer().getTraitRate()));
            getClient().getSession().write(PetPacket.updatePet(pet, getPlayer().getInventory(MapleInventoryType.CASH).getItem((byte) pet.getInventoryPosition()), true));
        }
        c.getPlayer().marriage();
    }

    public final void gainClosenessAll(final int closeness) {
        for (final MaplePet pet : getPlayer().getPets()) {
            if (pet != null && pet.getSummoned()) {
                pet.setCloseness(pet.getCloseness() + closeness);
                getClient().getSession().write(PetPacket.updatePet(pet, getPlayer().getInventory(MapleInventoryType.CASH).getItem((byte) pet.getInventoryPosition()), true));
            }
        }
        c.getPlayer().marriage();
    }

    public final void resetMap(final int mapid) {
        getMap(mapid).resetFully();
    }

    public final void openNpc(final int id) {
        getClient().removeClickedNPC();
        NPCScriptManager.getInstance().start(getClient(), id, null);
    }

    public final void openNpc(final String filename) {
        openNpc(c, filename);
    }

    public final void openNpc(final MapleClient client, final String filename) {
        openNpc(client, 0, filename);
    }

    public final void openNpc(final MapleClient cg, final int id) {
        cg.removeClickedNPC();
        NPCScriptManager.getInstance().start(cg, id, null);
    }

    public final void openNpc(int npc, String filename) {
        c.removeClickedNPC();
        NPCScriptManager.getInstance().start(c, npc, filename);
    }

    public final void openNpc(MapleClient client, int npc, String filename) {
        c.removeClickedNPC();
        NPCScriptManager.getInstance().start(client, npc, filename);
    }

    public final int getMapId() {
        return c.getPlayer().getMap().getId();
    }

    public final boolean haveMonster(final int mobid) {
        for (MapleMapObject obj : c.getPlayer().getMap().getAllMonstersThreadsafe()) {
            final MapleMonster mob = (MapleMonster) obj;
            if (mob.getId() == mobid) {
                return true;
            }
        }
        return false;
    }

    public final int getChannelNumber() {
        return c.getChannel();
    }

    public final int getMonsterCount(final int mapid) {
        return c.getChannelServer().getMapFactory().getMap(mapid).getNumMonsters();
    }

    public final void teachSkill(final int id, final int level, final byte masterlevel) {
        getPlayer().changeSkillLevel(SkillFactory.getSkill(id), level, masterlevel);
    }

    public final void teachSkill(final int id, int level) {
        final Skill skil = SkillFactory.getSkill(id);
        if (getPlayer().getSkillLevel(skil) > level) {
            level = getPlayer().getSkillLevel(skil);
        }
        getPlayer().changeSkillLevel(skil, level, (byte) skil.getMaxLevel());
    }

    public final int getPlayerCount(final int mapid) {
        return c.getChannelServer().getMapFactory().getMap(mapid).getCharactersSize();
    }

    public final void dojo_getUp() {
        c.getSession().write(MaplePacketCreator.updateInfoQuest(1207, "pt=1;min=4;belt=1;tuto=1")); //todo
        c.getSession().write(MaplePacketCreator.Mulung_DojoUp2());
        c.getSession().write(MaplePacketCreator.instantMapWarp((byte) 6));
    }

    public final boolean dojoAgent_NextMap(final boolean dojo, final boolean fromresting) {
        if (dojo) {
            return Event_DojoAgent.warpNextMap(c.getPlayer(), fromresting, c.getPlayer().getMap());
        }
        return Event_DojoAgent.warpNextMap_Agent(c.getPlayer(), fromresting);
    }

    public final boolean dojoAgent_NextMap(final boolean dojo, final boolean fromresting, final int mapid) {
        if (dojo) {
            return Event_DojoAgent.warpNextMap(c.getPlayer(), fromresting, getMap(mapid));
        }
        return Event_DojoAgent.warpNextMap_Agent(c.getPlayer(), fromresting);
    }

    public final int dojo_getPts() {
        return c.getPlayer().getIntNoRecord(GameConstants.DOJO);
    }

    public final MapleEvent getEvent(final String loc) {
        return c.getChannelServer().getEvent(MapleEventType.valueOf(loc));
    }

    public final int getSavedLocation(final String loc) {
        final Integer ret = c.getPlayer().getSavedLocation(SavedLocationType.fromString(loc));
        //if (ret == null || ret == -1) {
        //    return 100000000;
        //}
        return ret;
    }

    public final void saveLocation(final String loc) {
        c.getPlayer().saveLocation(SavedLocationType.fromString(loc));
    }

    public final void saveReturnLocation(final String loc) {
        c.getPlayer().saveLocation(SavedLocationType.fromString(loc), c.getPlayer().getMap().getReturnMap().getId());
    }

    public final void clearSavedLocation(final String loc) {
        c.getPlayer().clearSavedLocation(SavedLocationType.fromString(loc));
    }

    public final void summonMsg(final String msg) {
        if (!c.getPlayer().hasSummon()) {
            playerSummonHint(true);
        }
        c.getSession().write(UIPacket.summonMessage(msg));
    }

    public final void summonMsg(final int type) {
        if (!c.getPlayer().hasSummon()) {
            playerSummonHint(true);
        }
        c.getSession().write(UIPacket.summonMessage(type));
    }

    public final void showInstruction(final String msg, final int width, final int height) {
        c.getSession().write(MaplePacketCreator.sendHint(msg, width, height));
    }

    public final void playerSummonHint(final boolean summon) {
        c.getPlayer().setHasSummon(summon);
        c.getSession().write(UIPacket.summonHelper(summon));
    }

    public final String getInfoQuest(final int id) {
        return c.getPlayer().getInfoQuest(id);
    }

    public final void updateInfoQuest(final int id, final String data) {
        c.getPlayer().updateInfoQuest(id, data);
    }

    public final boolean getEvanIntroState(final String data) {
        return getInfoQuest(22013).equals(data);
    }

    public final void updateEvanIntroState(final String data) {
        updateInfoQuest(22013, data);
    }

    public final void Aran_Start() {
        c.getSession().write(UIPacket.Aran_Start());
    }

    public final void evanTutorial(final String data, final int v1) {
        c.getSession().write(MaplePacketCreator.getEvanTutorial(data));
    }

    public final void AranTutInstructionalBubble(final String data) {
        c.getSession().write(UIPacket.AranTutInstructionalBalloon(data));
    }

    public final void ShowWZEffect(final String data) {
        c.getSession().write(UIPacket.AranTutInstructionalBalloon(data));
    }

    public final void showWZEffect(final String data) {
        c.getSession().write(UIPacket.ShowWZEffect(data));
    }

    public final void EarnTitleMsg(final String data) {
        c.getSession().write(UIPacket.EarnTitleMsg(data));
    }

    public final void EnableUI(final short i) {
        c.getSession().write(UIPacket.IntroEnableUI(i));
    }

    public final void DisableUI(final boolean enabled) {
        c.getSession().write(UIPacket.IntroDisableUI(enabled));
    }

    public final void MovieClipIntroUI(final boolean enabled) {
        c.getSession().write(UIPacket.IntroDisableUI(enabled));
        c.getSession().write(UIPacket.IntroLock(enabled));
    }

    public MapleInventoryType getInvType(int i) {
        return MapleInventoryType.getByType((byte) i);
    }

    public String getItemName(final int id) {
        return MapleItemInformationProvider.getInstance().getName(id);
    }

    public void gainPet(int id, String name, int level, int closeness, int fullness) {
        MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        gainPet(id, name, level, closeness, fullness, ii.getPetLife(id), ii.getPetFlagInfo(id));
    }

    public void gainPet(int id, String name, int level, int closeness, int fullness, int period) {
        MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        gainPet(id, name, level, closeness, fullness, period, ii.getPetFlagInfo(id));
    }

    public void gainPet(int id, String name, int level, int closeness, int fullness, long period, short flags) {
        if (id > 5002000 || id < 5000000) {
            id = 5000000;
        }
        if (level > 30) {
            level = 30;
        }
        if (closeness > 30000) {
            closeness = 30000;
        }
        if (fullness > 100) {
            fullness = 100;
        }
        try {
            MapleInventoryManipulator.addById(c, id, (short) 1, "", MaplePet.createPet(id, name, level, closeness, fullness, MapleInventoryIdentifier.getInstance(), id == 5000054 ? (int) period : 0, flags, 0), 45, "Pet from interaction " + id + " (" + id2 + ")" + " on " + FileoutputUtil.CurrentReadable_Date());
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }

    public void removeSlot(int invType, byte slot, short quantity) {
        MapleInventoryManipulator.removeFromSlot(c, getInvType(invType), slot, quantity, true);
    }

    public void gainGP(final int gp) {
        if (getPlayer().getGuildId() <= 0) {
            return;
        }
        World.Guild.gainGP(getPlayer().getGuildId(), gp); //1 for
    }

    public int getGP() {
        if (getPlayer().getGuildId() <= 0) {
            return 0;
        }
        return World.Guild.getGP(getPlayer().getGuildId()); //1 for
    }

    public void showMapEffect(String path) {
        getClient().getSession().write(UIPacket.MapEff(path));
    }

    public int itemQuantity(int itemid) {
        return getPlayer().itemQuantity(itemid);
    }

    public EventInstanceManager getDisconnected(String event) {
        EventManager em = getEventManager(event);
        if (em == null) {
            return null;
        }
        for (EventInstanceManager eim : em.getInstances()) {
            if (eim.isDisconnected(c.getPlayer()) && eim.getPlayerCount() > 0) {
                return eim;
            }
        }
        return null;
    }

    public boolean isAllReactorState(final int reactorId, final int state) {
        boolean ret = false;
        for (MapleReactor r : getMap().getAllReactorsThreadsafe()) {
            if (r.getReactorId() == reactorId) {
                ret = r.getState() == state;
            }
        }
        return ret;
    }

    public long getCurrentTime() {
        return System.currentTimeMillis();
    }

    public void spawnMonster(int id) {
        spawnMonster(id, 1, getPlayer().getTruePosition());
    }

    // summon one monster, remote location
    public void spawnMonster(int id, int x, int y) {
        spawnMonster(id, 1, new Point(x, y));
    }

    // multiple monsters, remote location
    public void spawnMonster(int id, int qty, int x, int y) {
        spawnMonster(id, qty, new Point(x, y));
    }

    // handler for all spawnMonster
    public void spawnMonster(int id, int qty, Point pos) {
        for (int i = 0; i < qty; i++) {
            getMap().spawnMonsterOnGroundBelow(MapleLifeFactory.getMonster(id), pos);
        }
    }

    public void sendNPCText(final String text, final int npc) {
        getMap().broadcastMessage(MaplePacketCreator.getNPCTalk(npc, (byte) 0, text, "00 00", (byte) 0));
    }

    public boolean getTempFlag(final int flag) {
        return (c.getChannelServer().getTempFlag() & flag) == flag;
    }

    public void logPQ(String text) {

    }

    public void outputFileError(Throwable t) {
        FileoutputUtil.outputFileError(FileoutputUtil.ScriptEx_Log, t);
    }

    public void trembleEffect(int type, int delay) {
        c.getSession().write(MaplePacketCreator.trembleEffect(type, delay));
    }

    public int nextInt(int arg0) {
        return Randomizer.nextInt(arg0);
    }

    public int rand(final int lbound, final int ubound) {
        return Randomizer.rand(lbound, ubound);
    }

    public MapleQuest getQuest(int arg0) {
        return MapleQuest.getInstance(arg0);
    }

    public void achievement(int a) {
        c.getPlayer().getMap().broadcastMessage(MaplePacketCreator.achievementRatio(a));
    }

    public final MapleInventory getInventory(int type) {
        return c.getPlayer().getInventory(MapleInventoryType.getByType((byte) type));
    }

    public boolean isGMS() {
        return GameConstants.GMS;
    }

    public int randInt(int arg0) {
        return Randomizer.nextInt(arg0);
    }

    public void sendDirectionStatus(int key, int value) {
        c.getSession().write(UIPacket.getDirectionInfo(key, value));
        c.getSession().write(UIPacket.getDirectionStatus(true));
    }

    public void sendDirectionInfo(String data) {
        c.getSession().write(UIPacket.getDirectionInfo(data, 2000, 0, -100, 0));
        c.getSession().write(UIPacket.getDirectionInfo(1, 2000));
    }

    public void witchTowerKey(byte key) {
        c.getSession().write(MaplePacketCreator.witchTowerKey(key));
    }

    public int getBossLog(String bossid) {
        return getPlayer().getBossLog(bossid);
    }

    public int getBossLogD(String bossid) {
        return getPlayer().getBossLogD(bossid);
    }

    public void setBossLog(String bossid) {
        getPlayer().setBossLog(bossid);
    }

    public final int getSkillLevel(MapleCharacter chr, int skillId, int job) {
        return chr.getSkillLevel(chr.getStat().getSkillByJob(skillId, job));
    }

    public final int getSkillByJob(MapleCharacter chr, int skillId, int job) {
        return chr.getStat().getSkillByJob(skillId, job);
    }

    public int getProfessions() {
        int ii = 0;
        for (int i = 0; i < 5; i++) {
            int skillId = 92000000 + i * 10000;
            if (this.c.getPlayer().getProfessionLevel(skillId) > 0) {
                ii++;
            }
        }
        return ii;
    }

    public int getTotalOnline() {
        int totalOnline = 0;
        for (ChannelServer cserv : ChannelServer.getAllInstances()) {
            totalOnline += cserv.getConnectedClients();
        }
        return totalOnline;
    }

    public List<Item> getEquippedlist() {
        MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        MapleInventory Equip = c.getPlayer().getInventory(MapleInventoryType.EQUIPPED);
        List<Item> ret = new ArrayList();
        for (Item tep : Equip) {
            final Equip equip = (Equip) tep;
            if (equip.getUpgradeSlots() <= 0) {
                if (!(equip.getEnhance() >= GameConstants.getEnchantSstarts(ii.getReqLevel(equip.getItemId()), ii.isSuperiorEquip(equip.getItemId())))) {
                    ret.add(tep);
                }
            }
        }
        return ret;
    }

    public boolean isEquippedItem(int id) {
        MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        MapleInventory Equip = c.getPlayer().getInventory(MapleInventoryType.EQUIPPED);
        for (Item tep : Equip) {
            final Equip equip = (Equip) tep;
            if (equip.getItemId() == id) {
                return true;
            }
        }
        return false;
    }

    public List<Item> getEquipScrolllist() {
        MapleInventory Equip = c.getPlayer().getInventory(MapleInventoryType.USE);
        List<Item> ret = new ArrayList();
        for (Item tep : Equip) {
            if (GameConstants.isEquipScroll(tep.getItemId())) {
                ret.add(tep);
            }
        }
        return ret;
    }

    public final boolean UseUpgradeScroll(Item equip, Item scroll, int jl) {
        short slot = scroll.getPosition();
        short dst = equip.getPosition();
        short ws = 0;
        return InventoryHandler.UseUpgradeScroll(slot, dst, ws, c, c.getPlayer(), 0, jl, false, true);
    }

    public MapleMapFactory getMapFactory() {
        return getChannelServer().getMapFactory();
    }

    public void getItemLog(String mob, String itemmob) {
        FileoutputUtil.logToFile("logs/Data/" + mob + ".txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSessionIPAddress() + " 账号 " + c.getAccountName() + " 账号ID " + c.getAccID() + " 角色名 " + c.getPlayer().getName() + " 角色ID " + c.getPlayer().getId() + " " + itemmob);
    }

    public boolean hasSquadByMap() {
        return getPlayer().getMap().getSquadByMap() != null;
    }

    public boolean hasEventInstance() {
        return getPlayer().getEventInstance() != null;
    }

    public boolean hasEMByMap() {
        return getPlayer().getMap().getEMByMap() != null;
    }

    public final boolean canHoldByType(byte bytype, int num) {
        if ((c.getPlayer().getInventory(MapleInventoryType.getByType(bytype)).getSlotLimit() - (c.getPlayer().getInventory(MapleInventoryType.getByType(bytype)).getNumSlotLimit() + 1)) <= num) {
            return false;
        }
        return true;
    }

    public final boolean canHoldByTypea(byte bytype, int num) {
        if (c.getPlayer().getInventory(MapleInventoryType.getByType(bytype)).getSlotLimit() - (c.getPlayer().getInventory(MapleInventoryType.getByType(bytype)).getNextFreeSlot() - 1) <= num) {
            return false;
        }
        return true;
    }

    public void processCommand(String line) {
        CommandProcessor.processCommand(getClient(), line, ServerConstants.CommandType.NORMAL);
    }

    public void fm() {
        for (int i : GameConstants.blockedMaps) {
            if (c.getPlayer().getMapId() == i) {
                c.getPlayer().dropMessage(5, "当前地图无法使用.");
                return;
            }
        }

        if (c.getPlayer().getMapId() == 749060605 || c.getPlayer().getMapId() == 229010000) {
            c.getPlayer().dropMessage(5, "当前地图无法使用.");
            return;
        }

        if (c.getPlayer().getLevel() < 10 && c.getPlayer().getJob() != 200) {
            c.getPlayer().dropMessage(5, "你的等級不足10級无法使用.");
            return;
        }
        if (c.getPlayer().hasBlockedInventory() || c.getPlayer().getMap().getSquadByMap() != null || c.getPlayer().getEventInstance() != null || c.getPlayer().getMap().getEMByMap() != null || c.getPlayer().getMapId() >= 990000000/* || FieldLimitType.VipRock.check(c.getPlayer().getMap().getFieldLimit())*/) {
            c.getPlayer().dropMessage(5, "请稍后再試");
            return;
        }
        if ((c.getPlayer().getMapId() >= 680000210 && c.getPlayer().getMapId() <= 680000502) || (c.getPlayer().getMapId() / 1000 == 980000 && c.getPlayer().getMapId() != 980000000) || (c.getPlayer().getMapId() / 100 == 1030008) || (c.getPlayer().getMapId() / 100 == 922010) || (c.getPlayer().getMapId() / 10 == 13003000)) {
            c.getPlayer().dropMessage(5, "请稍后再試.");
            return;
        }
        c.getPlayer().saveLocation(SavedLocationType.FREE_MARKET, c.getPlayer().getMap().getId()/*getReturnMap().getId()*/);
        MapleMap map = c.getChannelServer().getMapFactory().getMap(910000000);
        c.getPlayer().changeMap(map, map.getPortal(0));
    }

    public boolean MarrageChecking3() {
        for (MaplePartyCharacter chr : getPlayer().getParty().getMembers()) {
            if (chr.getId() == getPlayer().getId()) {
                continue;
            }
            MapleCharacter curChar = getChannelServer().getPlayerStorage().getCharacterById(chr.getId());
            if (curChar.getMarriageId() != getPlayer().getId()) {
                return true;
            }
        }
        return false;
    }

    public boolean MarrageChecking4() {
        for (MaplePartyCharacter chr : getPlayer().getParty().getMembers()) {
            if (chr.getId() == getPlayer().getId()) {
                continue;
            }
            MapleCharacter curChar = getChannelServer().getPlayerStorage().getCharacterById(chr.getId());
            if (curChar.getGender() == getPlayer().getGender()) {
                return true;
            }
        }
        return false;
    }

    public boolean MarrageChecking6() {
        for (MaplePartyCharacter chr : getPlayer().getParty().getMembers()) {
            if (chr.getId() == getPlayer().getId()) {
                continue;
            }
            MapleCharacter curChar = getChannelServer().getPlayerStorage().getCharacterById(chr.getId());
            if (!curChar.canHold(1112314)) {
                return true;
            }
        }
        return false;
    }

    public void YinMarry() {
        int itemId = 1112314;
        MapleCharacter fff;
        String name = "管理員ABC";
        for (MaplePartyCharacter parchr : getPlayer().getParty().getMembers()) {
            if (parchr.getId() == getPlayer().getId()) {
                continue;
            }
            name = parchr.getName();
        }
        int ch = World.Find.findChannel(name);
        if (ch <= 0) {
            c.getPlayer().dropMessage(6, "玩家必須上线");
            return;
        }
        fff = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(name);
        if (fff == null) {
            c.getPlayer().dropMessage(6, "玩家必須上线");
        } else {
            int[] ringID = {MapleInventoryIdentifier.getInstance(), MapleInventoryIdentifier.getInstance()};
            try {
                MapleCharacter[] chrz = {fff, c.getPlayer()};
                for (int i = 0; i < chrz.length; i++) {
                    Equip eq = (Equip) MapleItemInformationProvider.getInstance().getEquipById(itemId);
                    if (eq == null) {
                        c.getPlayer().dropMessage(6, "错误的戒指ID.");
                        return;
                    } else {
                        eq.setUniqueId(ringID[i]);
                        MapleInventoryManipulator.addbyItem(chrz[i].getClient(), eq.copy());
                        chrz[i].dropMessage(6, "成功与  " + chrz[i == 0 ? 1 : 0].getName() + " 銀婚");
                        worldMessage(6, "祝福新人 " + chrz[0].getName() + " 成功与  " + chrz[1].getName() + " 完成高級銀婚式 讓我們一起祝福他們~");
                    }
                }
                MapleRing.addToDB(itemId, c.getPlayer(), fff.getName(), fff.getId(), ringID);
            } catch (SQLException e) {
            }
        }
    }

    public List<Item> getCsEquipList() {
        MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        MapleInventory Equip = c.getPlayer().getInventory(MapleInventoryType.EQUIP);
        List<Item> ret = new ArrayList();
        for (Item tep : Equip) {
            if (ii.isCash(tep.getItemId())) {
                ret.add(tep);
            }
        }
        return ret;
    }

    public final boolean UseCsFm(Item equip) {
        short dst = equip.getPosition();

        Equip toScroll = (Equip) c.getPlayer().getInventory(MapleInventoryType.EQUIP).getItem(dst);

        /*if (Randomizer.nextInt(100) > 60) {
            return false;
        }*/
        int getwatk = 6 - toScroll.getWatk();
        if (getwatk <= 0) {
            getwatk = 100;
        }
        int getmatk = 6 - toScroll.getMatk();
        if (getmatk <= 0) {
            getmatk = 100;
        }

        short watk = (short) ((int) (Math.random() * getwatk));
        short matk = (short) ((int) (Math.random() * getmatk));
        if (watk == 0) {
            watk = 1;
        }
        if (matk == 0) {
            matk = 1;
        }
        if (getwatk == 100) {
            watk = 0;
        }
        if (getmatk == 100) {
            matk = 0;
        }

        toScroll.setWatk((short) (toScroll.getWatk() + watk));
        toScroll.setMatk((short) (toScroll.getMatk() + matk));
        c.getPlayer().forceReAddItem_NoUpdate(toScroll, MapleInventoryType.EQUIP);
        c.getPlayer().equipChanged();
        c.getPlayer().fakeRelog();
        return true;
    }

    public List<Item> getEquipList() {
        MapleInventory Equip = c.getPlayer().getInventory(MapleInventoryType.EQUIP);
        List<Item> ret = new ArrayList();
        for (Item tep : Equip) {
            ret.add(tep);
        }
        return ret;
    }

    public Equip getEquipStat(byte slot) {
        Equip sel = (Equip) c.getPlayer().getInventory(MapleInventoryType.EQUIP).getItem(slot);

        return sel;
    }
    
    
    public List<Item> getUseList() {
        MapleInventory Equip = c.getPlayer().getInventory(MapleInventoryType.USE);
        List<Item> ret = new ArrayList();
        for (Item tep : Equip) {
            ret.add(tep);
        }
        return ret;
    }

    public Equip getUseStat(byte slot) {
        Equip sel = (Equip) c.getPlayer().getInventory(MapleInventoryType.USE).getItem(slot);

        return sel;
    }
    
    public List<Item> getSetupList() {
        MapleInventory Equip = c.getPlayer().getInventory(MapleInventoryType.SETUP);
        List<Item> ret = new ArrayList();
        for (Item tep : Equip) {
            ret.add(tep);
        }
        return ret;
    }

    public Equip getSetupStat(byte slot) {
        Equip sel = (Equip) c.getPlayer().getInventory(MapleInventoryType.SETUP).getItem(slot);

        return sel;
    }
    
    public List<Item> getEtcList() {
        MapleInventory Equip = c.getPlayer().getInventory(MapleInventoryType.ETC);
        List<Item> ret = new ArrayList();
        for (Item tep : Equip) {
            ret.add(tep);
        }
        return ret;
    }

    public Equip getEtcStat(byte slot) {
        Equip sel = (Equip) c.getPlayer().getInventory(MapleInventoryType.ETC).getItem(slot);

        return sel;
    }
    
    public List<Item> getCashList() {
        MapleInventory Equip = c.getPlayer().getInventory(MapleInventoryType.CASH);
        List<Item> ret = new ArrayList();
        for (Item tep : Equip) {
            ret.add(tep);
        }
        return ret;
    }

    public Equip getCashStat(byte slot) {
        Equip sel = (Equip) c.getPlayer().getInventory(MapleInventoryType.CASH).getItem(slot);

        return sel;
    }

    public List<Item> getPotentiallist() {
        MapleInventory Equip = c.getPlayer().getInventory(MapleInventoryType.CASH);
        List<Item> ret = new ArrayList();
        for (Item tep : Equip) {
            if ((tep.getItemId() == 5062000) || (tep.getItemId() == 5062001)) {
                ret.add(tep);
            }
        }
        return ret;
    }

    public List<Item> getEquipFyzxList() {
        MapleInventory Equip = c.getPlayer().getInventory(MapleInventoryType.EQUIP);
        List<Item> ret = new ArrayList();
        for (Item tep : Equip) {
            if (tep.getItemId() == 1122024 || tep.getItemId() == 1122025 || tep.getItemId() == 1122026 || tep.getItemId() == 1122027 || tep.getItemId() == 1122028 || tep.getItemId() == 1122029 || tep.getItemId() == 1122030 || tep.getItemId() == 1122031 || tep.getItemId() == 1122032 || tep.getItemId() == 1122033 || tep.getItemId() == 1122034 || tep.getItemId() == 1122035 || tep.getItemId() == 1122036 || tep.getItemId() == 1122037 || tep.getItemId() == 1122038) {
                ret.add(tep);
            }
        }
        return ret;
    }

    public List<Item> getPotenttalFyzxlist() {
        MapleInventory Equip = c.getPlayer().getInventory(MapleInventoryType.USE);
        List<Item> ret = new ArrayList();
        for (Item tep : Equip) {
            if (tep.getItemId() == 2049405) {
                ret.add(tep);
            }
        }
        return ret;
    }

    public List<Item> getMagnifylist() {
        MapleInventory Equip = c.getPlayer().getInventory(MapleInventoryType.USE);
        List<Item> ret = new ArrayList();
        for (Item tep : Equip) {
            if ((tep.getItemId() == 2460000) || (tep.getItemId() == 2460001) || (tep.getItemId() == 2460002) || (tep.getItemId() == 2460003)) {
                ret.add(tep);
            }
        }
        return ret;
    }

    public int UseCube(Item equip, Item scroll) {
        if (scroll == null || scroll.getQuantity() < 1) {
            c.getSession().write(MaplePacketCreator.enableActions());
            c.getPlayer().marriage();
            return 1;
        }

        boolean fail = false;
        int moba = 100;
        if (scroll.getItemId() == 5062000) {
            if (c.getPlayer().getLevel() < 10) {
                //c.getPlayer().dropMessage(1, "你的等級不足10等，无法使用。");
                return 2;
            } else {
                final Item item = c.getPlayer().getInventory(MapleInventoryType.EQUIP).getItem((byte) equip.getPosition());
                if (item != null && c.getPlayer().getInventory(MapleInventoryType.USE).getNumFreeSlot() >= 1) {
                    final Equip eq = (Equip) item;
                    if (eq.getState() >= 5 && eq.getState() != 8) {
                        if (!haveItem(5062000, 1)) {
                            return 3;
                        }

                        gainItem(5062000, (short) -1);
                        eq.renewPotential(false);
                        //c.getSession().write(MaplePacketCreator.scrolledItem(scroll, item, false, true));
                        c.getPlayer().marriage();
                        c.getPlayer().forceReAddItem_NoUpdate(item, MapleInventoryType.EQUIP);
                        MapleInventoryManipulator.addById(c, 2430112, (short) 1, "Cube" + " on " + FileoutputUtil.CurrentReadable_Date());
                        if (ServerConstants.logs_cube) {
                            FileoutputUtil.logToFile("logs/Data/使用方块.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSessionIPAddress() + " 账号: " + c.getAccountName() + " 玩家: " + c.getPlayer().getName() + " 使用了魔方道具: 5062000");
                        }
                        if (ServerConstants.message_cube) {
                            World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 聊天]『" + c.getPlayer().getName() + "』(" + c.getPlayer().getId() + ")地图『" + c.getPlayer().getMapId() + "』使用了魔方道具: 5062000"));
                        }
                        return 0;
                    } else {
                        //c.getPlayer().dropMessage(5, "该装备沒有潜能或已经超过该方块能改变的潜能等級");
                        fail = true;
                        moba = 4;
                    }
                } else {
                    //c.getPlayer().dropMessage(5, "请检查你的背包是否已满。");
                    fail = true;
                    moba = 5;
                }
                c.getPlayer().getMap().broadcastMessage(MaplePacketCreator.getPotentialReset(fail, c.getPlayer().getId(), scroll.getItemId()));
                return moba;
            }

        } else if (scroll.getItemId() == 5062001) {
            if (c.getPlayer().getLevel() < 70) {
                //c.getPlayer().dropMessage(1, "You may not use this until level 70.");
                return 12;
            } else {
                final Item item = c.getPlayer().getInventory(MapleInventoryType.EQUIP).getItem((byte) equip.getPosition());
                if (item != null && c.getPlayer().getInventory(MapleInventoryType.USE).getNumFreeSlot() >= 1) {
                    final Equip eq = (Equip) item;
                    if (eq.getState() >= 5 && eq.getState() != 8) {
                        if (!haveItem(5062001, 1)) {
                            return 3;
                        }

                        gainItem(5062001, (short) -1);
                        eq.renewPotential1(eq.getPotential3() <= 0);
                        //c.getSession().write(MaplePacketCreator.scrolledItem(scroll, item, false, true));
                        c.getPlayer().marriage();
                        c.getPlayer().forceReAddItem_NoUpdate(item, MapleInventoryType.EQUIP);
                        MapleInventoryManipulator.addById(c, 2430112, (short) 5, "Cube" + " on " + FileoutputUtil.CurrentReadable_Date());
                        if (ServerConstants.logs_cube) {
                            FileoutputUtil.logToFile("logs/Data/使用方块.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + c.getSessionIPAddress() + " 账号: " + c.getAccountName() + " 玩家: " + c.getPlayer().getName() + " 使用了魔方道具: 5062001");
                        }
                        if (ServerConstants.message_cube) {
                            World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 聊天]『" + c.getPlayer().getName() + "』(" + c.getPlayer().getId() + ")地图『" + c.getPlayer().getMapId() + "』使用了魔方道具: 5062001"));
                        }
                        return 0;
                    } else {
                        //c.getPlayer().dropMessage(5, "Make sure your equipment has a potential.");
                        fail = true;
                        moba = 4;
                    }
                } else {
                    //c.getPlayer().dropMessage(5, "Make sure you have room for a Fragment.");
                    fail = true;
                    moba = 5;
                }
                c.getPlayer().getMap().broadcastMessage(MaplePacketCreator.getPotentialReset(fail, c.getPlayer().getId(), scroll.getItemId()));
                return moba;
            }
        }
        return 6;
    }

    public int UseMagnify(Item equip, Item scroll) {
        c.getPlayer().setScrolledPosition((short) 0);
        final byte src = (byte) scroll.getPosition();
        final byte dst = (byte) equip.getPosition();
        final boolean insight = src == 127 && c.getPlayer().getTrait(MapleTraitType.sense).getLevel() >= 30;
        final Item magnify = c.getPlayer().getInventory(MapleInventoryType.USE).getItem(src);
        final Item toReveal = (dst < 0) ? c.getPlayer().getInventory(MapleInventoryType.EQUIPPED).getItem(dst) : c.getPlayer().getInventory(MapleInventoryType.EQUIP).getItem(dst);

        if ((magnify == null && !insight) || toReveal == null) {
            c.getSession().write(MaplePacketCreator.getInventoryFull());
            c.getPlayer().marriage();
            return 1;
        }
        final Equip eqq = (Equip) toReveal;
        final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        final int reqLevel = ii.getReqLevel(eqq.getItemId()) / 10;
        if (eqq.getState() == 1 && (insight || magnify.getItemId() == 2460003 || (magnify.getItemId() == 2460002 && reqLevel <= 12) || (magnify.getItemId() == 2460001 && reqLevel <= 7) || (magnify.getItemId() == 2460000 && reqLevel <= 3))) {
            final List<List<StructPotentialItem>> pots = new LinkedList<List<StructPotentialItem>>(ii.getAllPotentialInfo().values());
            int new_state = Math.abs(eqq.getPotential1());
            if (new_state > 8 || new_state < 5) { //luls tooo legend
                new_state = 5;
            }
            final int lines = (eqq.getPotential2() != 0 ? 3 : 2);
            while (eqq.getState() != new_state) {
                //31001 = haste, 31002 = door, 31003 = se, 31004 = hb
                for (int i = 0; i < lines; i++) { //2 or 3 line
                    boolean rewarded = false;
                    while (!rewarded) {
                        StructPotentialItem pot = pots.get(Randomizer.nextInt(pots.size())).get(reqLevel);
                        if (pot != null && pot.reqLevel / 10 <= reqLevel && GameConstants.optionTypeFits(pot.optionType, eqq.getItemId()) && GameConstants.potentialIDFits(pot.potentialID, new_state, i)) { //optionType
                            //have to research optionType before making this truely sea-like
                            if (i == 0) {
                                eqq.setPotential1(pot.potentialID);
                            } else if (i == 1) {
                                eqq.setPotential2(pot.potentialID);
                            } else if (i == 2) {
                                eqq.setPotential3(pot.potentialID);
                            }
                            rewarded = true;
                        }
                    }
                }
            }
            c.getPlayer().getTrait(MapleTraitType.insight).addExp((insight ? 10 : ((magnify.getItemId() + 2) - 2460000)) * 2, c.getPlayer());
            c.getSession().write(MaplePacketCreator.getMagnifyingGlass(c.getPlayer().getId(), eqq.getPosition()));
            if (!insight) {
                c.getSession().write(MaplePacketCreator.scrolledItem(magnify, toReveal, false, true, true));
                MapleInventoryManipulator.removeFromSlot(c, MapleInventoryType.USE, magnify.getPosition(), (short) 1, false);
                c.getPlayer().marriage();
            } else {
                c.getPlayer().forceReAddItem(toReveal, MapleInventoryType.EQUIP);
            }
            return 0;
        } else {
            c.getSession().write(MaplePacketCreator.getInventoryFull());
            c.getPlayer().marriage();
            return 1;
        }
    }

    public final boolean canwncs() {
        for (int i : GameConstants.blockedMaps) {
            if (c.getPlayer().getMapId() == i) {
                c.getPlayer().dropMessage(5, "当前地图无法使用.");
                return false;
            }
        }

        if (c.getPlayer().getMapId() == 749060605 || c.getPlayer().getMapId() == 229010000 || c.getPlayer().getMapId() == 910000000) {
            c.getPlayer().dropMessage(5, "当前地图无法使用.");
            return false;
        }

        if (c.getPlayer().getLevel() < 10 && c.getPlayer().getJob() != 200) {
            c.getPlayer().dropMessage(5, "你的等級不足10級无法使用.");
            return false;
        }
        if (c.getPlayer().hasBlockedInventory() || c.getPlayer().getMap().getSquadByMap() != null || c.getPlayer().getEventInstance() != null || c.getPlayer().getMap().getEMByMap() != null || c.getPlayer().getMapId() >= 990000000/* || FieldLimitType.VipRock.check(c.getPlayer().getMap().getFieldLimit())*/) {
            c.getPlayer().dropMessage(5, "请稍后再試");
            return false;
        }
        if ((c.getPlayer().getMapId() >= 680000210 && c.getPlayer().getMapId() <= 680000502) || (c.getPlayer().getMapId() / 1000 == 980000 && c.getPlayer().getMapId() != 980000000) || (c.getPlayer().getMapId() / 100 == 1030008) || (c.getPlayer().getMapId() / 100 == 922010) || (c.getPlayer().getMapId() / 10 == 13003000)) {
            c.getPlayer().dropMessage(5, "请稍后再試.");
            return false;
        }
        return true;
    }

    public final boolean nlczj(int apto, int apfrom) {
        Map<MapleStat, Integer> statupdate = new EnumMap<MapleStat, Integer>(MapleStat.class);
        boolean used = true;

        final int job = c.getPlayer().getJob();
        final PlayerStats playerst = c.getPlayer().getStat();

        if (apto == apfrom) {
            used = false;
        }
        switch (apto) { // AP to
            case 64: // str
                if (playerst.getStr() >= 30000) {
                    used = false;
                }
                break;
            case 128: // dex
                if (playerst.getDex() >= 30000) {
                    used = false;
                }
                break;
            case 256: // int
                if (playerst.getInt() >= 30000) {
                    used = false;
                }
                break;
            case 512: // luk
                if (playerst.getLuk() >= 30000) {
                    used = false;
                }
                break;
            case 2048: // hp
                if (playerst.getMaxHp() >= 99999) {
                    used = false;
                }
                break;
            case 8192: // mp
                if (playerst.getMaxMp() >= 99999) {
                    used = false;
                }
                break;
        }
        switch (apfrom) { // AP to
            case 64: // str
                if (playerst.getStr() <= 4 || (c.getPlayer().getJob() % 1000 / 100 == 1 && playerst.getStr() <= 35)) {
                    used = false;
                }
                break;
            case 128: // dex
                if (playerst.getDex() <= 4 || (c.getPlayer().getJob() % 1000 / 100 == 3 && playerst.getDex() <= 25) || (c.getPlayer().getJob() % 1000 / 100 == 4 && playerst.getDex() <= 25) || (c.getPlayer().getJob() % 1000 / 100 == 5 && playerst.getDex() <= 20)) {
                    used = false;
                }
                break;
            case 256: // int
                if (playerst.getInt() <= 4 || (c.getPlayer().getJob() % 1000 / 100 == 2 && playerst.getInt() <= 20)) {
                    used = false;
                }
                break;
            case 512: // luk
                if (playerst.getLuk() <= 4) {
                    used = false;
                }
                break;
            case 2048: // hp
                if (/*playerst.getMaxMp() < ((c.getPlayer().getLevel() * 14) + 134) || */playerst.getMaxMp() < ((c.getPlayer().getLevel() * 22) + 143) || c.getPlayer().getHpApUsed() <= 0 || c.getPlayer().getHpApUsed() >= 10000) {
                    used = false;
                    c.getPlayer().dropMessage(1, "你需要增加过HP或者MP才能重置。");
                }
                break;
            case 8192: // mp
                if (/*playerst.getMaxMp() < ((c.getPlayer().getLevel() * 14) + 134) ||*/playerst.getMaxMp() < ((c.getPlayer().getLevel() * 4) + 56) || c.getPlayer().getHpApUsed() <= 0 || c.getPlayer().getHpApUsed() >= 10000) {
                    used = false;
                    c.getPlayer().dropMessage(1, "你需要增加过HP或者MP才能重置。");
                }
                break;
        }
        if (used) {
            switch (apto) { // AP to
                case 64: { // str
                    final int toSet = playerst.getStr() + 1;
                    playerst.setStr((short) toSet, c.getPlayer());
                    statupdate.put(MapleStat.STR, toSet);
                    break;
                }
                case 128: { // dex
                    final int toSet = playerst.getDex() + 1;
                    playerst.setDex((short) toSet, c.getPlayer());
                    statupdate.put(MapleStat.DEX, toSet);
                    break;
                }
                case 256: { // int
                    final int toSet = playerst.getInt() + 1;
                    playerst.setInt((short) toSet, c.getPlayer());
                    statupdate.put(MapleStat.INT, toSet);
                    break;
                }
                case 512: { // luk
                    final int toSet = playerst.getLuk() + 1;
                    playerst.setLuk((short) toSet, c.getPlayer());
                    statupdate.put(MapleStat.LUK, toSet);
                    break;
                }
                case 2048: // hp
                    int maxhp = playerst.getMaxHp();
                    if (GameConstants.isBeginnerJob(job)) { // Beginner
                        maxhp += 8;
                    } else if ((job >= 100 && job <= 132) || (job >= 1100 && job <= 1112) || (job >= 2100 && job <= 2112) || (job >= 3200 && job <= 3212)) {
                        maxhp += 20;
                    } else if ((job >= 200 && job <= 232) || (job >= 1200 && job <= 1212)) {
                        maxhp += 6;
                    } else if ((job >= 300 && job <= 332) || (job >= 400 && job <= 434) || (job >= 1300 && job <= 1312) || (job >= 1400 && job <= 1412) || (job >= 3300 && job <= 3312)) {
                        maxhp += 16;
                    } else if ((job >= 500 && job <= 522) || (job >= 1500 && job <= 1512) || (job >= 3500 && job <= 3512)) {
                        maxhp += 18;
                    } else if ((job >= 2200 && job <= 2218)) {
                        maxhp += 12;
                    } else { // GameMaster
                        maxhp += 8;
                    }
                    maxhp = Math.min(99999, Math.abs(maxhp));
                    c.getPlayer().setHpApUsed((short) (c.getPlayer().getHpApUsed() + 1));
                    playerst.setMaxHp(maxhp, c.getPlayer());
                    statupdate.put(MapleStat.MAXHP, (int) maxhp);
                    break;

                case 8192: // mp
                    int maxmp = playerst.getMaxMp();

                    if (GameConstants.isBeginnerJob(job)) { // Beginner
                        maxmp += 6;
                    } else if ((job >= 100 && job <= 132) || (job >= 1100 && job <= 1112) || (job >= 2100 && job <= 2112)) {
                        maxmp += 2;
                    } else if ((job >= 200 && job <= 232) || (job >= 1200 && job <= 1212) || (job >= 2200 && job <= 2218) || (job >= 3200 && job <= 3212)) {
                        maxmp += 18;
                    } else if ((job >= 300 && job <= 332) || (job >= 400 && job <= 434) || (job >= 1300 && job <= 1312) || (job >= 1400 && job <= 1412) || (job >= 3200 && job <= 3212)) {
                        maxmp += 10;
                    } else if ((job >= 500 && job <= 522) || (job >= 1500 && job <= 1512) || (job >= 3500 && job <= 3512)) {
                        maxmp += 14;
                    } else if (job >= 3100 && job <= 3112) {
                        break;
                    } else { // GameMaster
                        maxmp += 6;
                    }
                    maxmp = Math.min(99999, Math.abs(maxmp));
                    c.getPlayer().setHpApUsed((short) (c.getPlayer().getHpApUsed() + 1));
                    playerst.setMaxMp(maxmp, c.getPlayer());
                    statupdate.put(MapleStat.MAXMP, (int) maxmp);
                    break;
            }
            switch (apfrom) { // AP from
                case 64: { // str
                    final int toSet = playerst.getStr() - 1;
                    playerst.setStr((short) toSet, c.getPlayer());
                    statupdate.put(MapleStat.STR, toSet);
                    break;
                }
                case 128: { // dex
                    final int toSet = playerst.getDex() - 1;
                    playerst.setDex((short) toSet, c.getPlayer());
                    statupdate.put(MapleStat.DEX, toSet);
                    break;
                }
                case 256: { // int
                    final int toSet = playerst.getInt() - 1;
                    playerst.setInt((short) toSet, c.getPlayer());
                    statupdate.put(MapleStat.INT, toSet);
                    break;
                }
                case 512: { // luk
                    final int toSet = playerst.getLuk() - 1;
                    playerst.setLuk((short) toSet, c.getPlayer());
                    statupdate.put(MapleStat.LUK, toSet);
                    break;
                }
                case 2048: // HP
                    int maxhp = playerst.getMaxHp();
                    if (GameConstants.isBeginnerJob(job)) { // Beginner
                        maxhp -= 12;
                    } else if ((job >= 100 && job <= 132) || (job >= 1100 && job <= 1112) || (job >= 2100 && job <= 2112)) {
                        maxhp -= 54;
                    } else if ((job >= 200 && job <= 232) || (job >= 1200 && job <= 1212)) {
                        maxhp -= 10;
                    } else if ((job >= 300 && job <= 332) || (job >= 400 && job <= 434) || (job >= 1300 && job <= 1312) || (job >= 1400 && job <= 1412) || (job >= 3200 && job <= 3212)) {
                        maxhp -= 20;
                    } else if ((job >= 500 && job <= 522) || (job >= 1500 && job <= 1512) || (job >= 3500 && job <= 3512)) {
                        maxhp -= 42;
                    } else if ((job >= 2200 && job <= 2218)) {
                        maxhp -= 16;
                    } else { // GameMaster
                        maxhp -= 12;
                    }
                    c.getPlayer().setHpApUsed((short) (c.getPlayer().getHpApUsed() - 1));
                    playerst.setMaxHp(maxhp, c.getPlayer());
                    statupdate.put(MapleStat.MAXHP, (int) maxhp);
                    break;
                case 8192: // MP
                    int maxmp = playerst.getMaxMp();
                    if (GameConstants.isBeginnerJob(job)) { // Beginner
                        maxmp -= 8;
                    } else if ((job >= 100 && job <= 132) || (job >= 1100 && job <= 1112) || (job >= 2100 && job <= 2112)) {
                        maxmp -= 4;
                    } else if ((job >= 200 && job <= 232) || (job >= 1200 && job <= 1212) || (job >= 2200 && job <= 2218) || (job >= 3200 && job <= 3212)) {
                        maxmp -= 30;
                    } else if ((job >= 300 && job <= 332) || (job >= 400 && job <= 434) || (job >= 1300 && job <= 1312) || (job >= 1400 && job <= 1412) || (job >= 3200 && job <= 3212)) {
                        maxmp -= 12;
                    } else if ((job >= 500 && job <= 522) || (job >= 1500 && job <= 1512) || (job >= 3500 && job <= 3512)) {
                        maxmp -= 16;
                    } else if (job >= 3100 && job <= 3112) {
                        break;
                    } else { // GameMaster
                        maxmp -= 8;
                    }
                    c.getPlayer().setHpApUsed((short) (c.getPlayer().getHpApUsed() - 1));
                    playerst.setMaxMp(maxmp, c.getPlayer());
                    statupdate.put(MapleStat.MAXMP, (int) maxmp);
                    break;
            }
            c.getSession().write(MaplePacketCreator.updatePlayerStats(statupdate, true, c.getPlayer().getJob()));
            c.getPlayer().marriage();
        }
        return used;
    }

    public void itemMegaphone(String name, String nr, boolean ear, Item item) {
        String sb = name + " : " + nr;
        World.Broadcast.broadcastSmega(MaplePacketCreator.itemMegaphone(sb, ear, c.getChannel(), item));
    }

    public void isVipMedalName() {
        if (c.getPlayer().getBossLogS("关闭VIP星星數显示") < 1) {
            c.getPlayer().setBossLog("关闭VIP星星數显示");
            c.getPlayer().dropMessage(5, "关闭VIP星星數显示。");
        } else {
            c.getPlayer().deleteBossLog("关闭VIP星星數显示");
            c.getPlayer().dropMessage(5, "开启VIP星星數显示。");
        }
    }

    public void SystemOutPrintln(String text) {
        if (c.getPlayer().isGM()) {
            this.c.getPlayer().dropMessage(6, "[Npc_Debug]" + text);
        }
        System.out.println("[NPC_DEBUG]:" + text);
    }

    public final int getCharacterIdByName(String name) {
        int id = c.getPlayer().getCharacterIdByName(name);
        return id;
    }

    public int getCharacterByNameLevel(String name) {
        int level = c.getPlayer().getCharacterByName(name).getLevel();
        return level;
    }

    public String getCharacterNameById(int id) {
        String name = c.getPlayer().getCharacterNameById(id);
        return name;
    }

    public final boolean givePartyHaveItem(final int id, final short quantity) {
        if (getPlayer().getParty() == null || getPlayer().getParty().getMembers().size() == 1) {
            return haveItem(id, (short) quantity);

        }

        final int cMap = getPlayer().getMapId();
        for (final MaplePartyCharacter chr : getPlayer().getParty().getMembers()) {
            final MapleCharacter curChar = getChannelServer().getPlayerStorage().getCharacterById(chr.getId());
            if (curChar != null && (curChar.getMapId() == cMap || curChar.getEventInstance() == getPlayer().getEventInstance())) {
                return curChar.haveItem(id, quantity, false, true);
            }
        }
        return false;
    }

    public Item getItem(byte type, Item scroll) {
        final byte src = (byte) scroll.getPosition();
        Item item = c.getPlayer().getInventory(MapleInventoryType.getByType(type)).getItem(src);
        return item;
    }

    public void dropCs(byte type, final short src, final short quantity) {
        MapleInventoryManipulator.dropCs(c, MapleInventoryType.getByType(type), src, quantity);
    }

    public int MarrageChecking() {
        if (getPlayer().getParty() == null) {
            return -1;
        }
        if (getPlayer().getMarriageId() > 0) {
            return 0;
        }
        if (getPlayer().getParty().getMembers().size() != 2) {
            return 1;
        }
        if ((getPlayer().getGender() == 0) && (!getPlayer().haveItem(1050121)) && (!getPlayer().haveItem(1050122)) && (!getPlayer().haveItem(1050113))) {
            return 5;
        }
        if ((getPlayer().getGender() == 1) && (!getPlayer().haveItem(1051129)) && (!getPlayer().haveItem(1051130)) && (!getPlayer().haveItem(1051114))) {
            return 5;
        }
        if (!getPlayer().haveItem(1112001) && (!getPlayer().haveItem(1112012)) && (!getPlayer().haveItem(1112002)) && (!getPlayer().haveItem(1112007))) {
            return 6;
        }
        for (MaplePartyCharacter chr : getPlayer().getParty().getMembers()) {
            if (chr.getId() == getPlayer().getId()) {
                continue;
            }
            MapleCharacter curChar = getChannelServer().getPlayerStorage().getCharacterById(chr.getId());
            if (curChar == null) {
                return 2;
            }
            if (curChar.getMarriageId() > 0) {
                return 3;
            }
            if (curChar.getGender() == getPlayer().getGender()) {
                return 4;
            }
            if ((curChar.getGender() == 0) && (!curChar.haveItem(1050121)) && (!curChar.haveItem(1050122)) && (!curChar.haveItem(1050113))) {
                return 5;
            }
            if ((curChar.getGender() == 1) && (!curChar.haveItem(1051129)) && (!curChar.haveItem(1051130)) && (!curChar.haveItem(1051114))) {
                return 5;
            }
            if (!curChar.haveItem(1112001) && (!curChar.haveItem(1112012)) && (!curChar.haveItem(1112002)) && (!curChar.haveItem(1112007))) {
                return 6;
            }
        }
        return 9;
    }

    public void makeRing(int itemID, String Name) {
        int itemId = itemID;
        if (!GameConstants.isEffectRing(itemId)) {
            c.getPlayer().dropMessage(6, "Invalid itemID.");
        } else {
            MapleCharacter fff = c.getChannelServer().getPlayerStorage().getCharacterByName(Name);
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
                            return;
                        }
                        MapleInventoryManipulator.addbyItem(chrz[i].getClient(), eq.copy());
                        chrz[i].dropMessage(6, "Successfully married with " + chrz[i == 0 ? 1 : 0].getName());
                    }
                    MapleRing.addToDB(itemId, c.getPlayer(), fff.getName(), fff.getId(), ringID);
                } catch (SQLException e) {
                }
            }
        }
    }

    public void ShowMarrageEffect() {
        c.getPlayer().getMap().broadcastMessage(MaplePacketCreator.sendMarrageEffect());
    }

    public String EquipList(MapleClient c) {
        StringBuilder str = new StringBuilder();
        MapleInventory equip = c.getPlayer().getInventory(MapleInventoryType.EQUIP);
        List<String> stra = new LinkedList<>();
        for (Item item : equip.list()) {
            stra.add("#L" + item.getPosition() + "##v" + item.getItemId() + "##l");
        }
        for (String strb : stra) {
            str.append(strb);
        }
        return str.toString();
    }

    public String UseList(MapleClient c) {
        StringBuilder str = new StringBuilder();
        MapleInventory use = c.getPlayer().getInventory(MapleInventoryType.USE);
        List<String> stra = new LinkedList<>();
        for (Item item : use.list()) {
            stra.add("#L" + item.getPosition() + "##v" + item.getItemId() + "##l");
        }
        for (String strb : stra) {
            str.append(strb);
        }
        return str.toString();
    }

    public String CashList(MapleClient c) {
        StringBuilder str = new StringBuilder();
        MapleInventory cash = c.getPlayer().getInventory(MapleInventoryType.CASH);
        List<String> stra = new LinkedList<>();
        for (Item item : cash.list()) {
            stra.add("#L" + item.getPosition() + "##v" + item.getItemId() + "##l");
        }
        for (String strb : stra) {
            str.append(strb);
        }
        return str.toString();
    }

    public String ETCList(MapleClient c) {
        StringBuilder str = new StringBuilder();
        MapleInventory etc = c.getPlayer().getInventory(MapleInventoryType.ETC);
        List<String> stra = new LinkedList<>();
        for (Item item : etc.list()) {
            stra.add("#L" + item.getPosition() + "##v" + item.getItemId() + "##l");
        }
        for (String strb : stra) {
            str.append(strb);
        }
        return str.toString();
    }

    public String SetupList(MapleClient c) {
        StringBuilder str = new StringBuilder();
        MapleInventory setup = c.getPlayer().getInventory(MapleInventoryType.SETUP);
        List<String> stra = new LinkedList<>();
        for (Item item : setup.list()) {
            stra.add("#L" + item.getPosition() + "##v" + item.getItemId() + "##l");
        }
        for (String strb : stra) {
            str.append(strb);
        }
        return str.toString();
    }

    public int getEquipId(byte slot) {
        MapleInventory equip = getPlayer().getInventory(MapleInventoryType.EQUIP);
        Equip eu = (Equip) equip.getItem(slot);
        return equip.getItem(slot).getItemId();
    }

    public int getUseId(byte slot) {
        MapleInventory use = getPlayer().getInventory(MapleInventoryType.USE);
        return use.getItem(slot).getItemId();
    }

    public int getSetupId(byte slot) {
        MapleInventory setup = getPlayer().getInventory(MapleInventoryType.SETUP);
        return setup.getItem(slot).getItemId();
    }

    public int getCashId(byte slot) {
        MapleInventory cash = getPlayer().getInventory(MapleInventoryType.CASH);
        return cash.getItem(slot).getItemId();
    }

    public int getETCId(byte slot) {
        MapleInventory etc = getPlayer().getInventory(MapleInventoryType.ETC);
        return etc.getItem(slot).getItemId();
    }

    public void deleteAll(int itemId) {
        MapleInventoryManipulator.removeAllById(getClient(), itemId, true);
    }

    public void setExpRate(int rate) {
        for (ChannelServer cserv : ChannelServer.getAllInstances()) {
            cserv.setExpRate(rate);
        }
    }

    public void setDropRate(int rate) {
        for (ChannelServer cserv : ChannelServer.getAllInstances()) {
            cserv.setDropRate(rate);
        }
    }

    public void setMesoRate(int rate) {
        for (ChannelServer cserv : ChannelServer.getAllInstances()) {
            cserv.setMesoRate(rate);
        }
    }

    public final void gainItemF(final int id, final short quantity, final boolean randomStats, final long period, final int slots, final String owner, final MapleClient cg) {
        if (quantity >= 0) {
            final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
            final MapleInventoryType type = GameConstants.getInventoryType(id);

            if (!MapleInventoryManipulator.checkSpace(cg, id, quantity, "")) {
                return;
            }
            if (type.equals(MapleInventoryType.EQUIP) && !GameConstants.isThrowingStar(id) && !GameConstants.isBullet(id)) {
                final Equip item = (Equip) (randomStats ? ii.randomizeStats((Equip) ii.getEquipById(id)) : ii.getEquipById(id));
                if (period > 0) {
                    item.setExpiration(System.currentTimeMillis() + (period * 60 * 1000));
                }
                if (slots > 0) {
                    item.setUpgradeSlots((byte) (item.getUpgradeSlots() + slots));
                }
                if (owner != null) {
                    item.setOwner(owner);
                }
                item.setGMLog("Received from interaction " + this.id + " (" + id2 + ") on " + FileoutputUtil.CurrentReadable_Time());
                final String name = ii.getName(id);
                if (id / 10000 == 114 && name != null && name.length() > 0) { //medal
                    final String msg = "你已获得稱號 <" + name + ">";
                    cg.getPlayer().dropMessage(-1, msg);
                    cg.getPlayer().dropMessage(5, msg);
                }
                MapleInventoryManipulator.addbyItem(cg, item.copy());
            } else {
                final MaplePet pet;
                if (GameConstants.isPet(id)) {
                    pet = MaplePet.createPet(id, MapleInventoryIdentifier.getInstance());
                } else {
                    pet = null;
                }

                MapleInventoryManipulator.addByIdF(cg, id, quantity, owner == null ? "" : owner, pet, period, "Received from interaction " + this.id + " (" + id2 + ") on " + FileoutputUtil.CurrentReadable_Date());
            }
        } else {
            MapleInventoryManipulator.removeById(cg, GameConstants.getInventoryType(id), id, -quantity, true, false);
        }
        cg.getSession().write(MaplePacketCreator.getShowItemGain(id, quantity, true));
    }

    public final void gainItemPeriodF(final int id, final short quantity, final int period) { //period is in days
        gainItemF(id, quantity, false, period, -1, "");
    }

    public final void gainItemPeriodF(final int id, final short quantity, final long period, final String owner) { //period is in days
        gainItemF(id, quantity, false, period, -1, owner);
    }

    public final void gainItemF(final int id, final short quantity) {
        gainItemF(id, quantity, false, 0, -1, "");
    }

    public final void gainItemF(final int id, final short quantity, final boolean randomStats) {
        gainItemF(id, quantity, randomStats, 0, -1, "");
    }

    public final void gainItemF(final int id, final short quantity, final boolean randomStats, final int slots) {
        gainItemF(id, quantity, randomStats, 0, slots, "");
    }

    public final void gainItemF(final int id, final short quantity, final long period) {
        gainItemF(id, quantity, false, period, -1, "");
    }

    public final void gainItemF(final int id, final short quantity, final boolean randomStats, final long period, final int slots) {
        gainItemF(id, quantity, randomStats, period, slots, "");
    }

    public final void gainItemF(final int id, final short quantity, final boolean randomStats, final long period, final int slots, final String owner) {
        gainItemF(id, quantity, randomStats, period, slots, owner, c);
    }

    public int getBossRank(int cid, String bossname, byte type) {
        int ret = -1;
        BossRankInfo info = BossRankManager.getInstance().getInfo(cid, bossname);
        if (null == info) {
            return ret;
        }
        switch (type) {
            case 1://积分
                ret = info.getPoints();
                break;
            case 2://次数
                ret = info.getCount();
                break;
        }
        return ret;
    }

    public int getBossRank1(int cid, String bossname, byte type) {
        int ret = -1;
        BossRankInfo1 info = BossRankManager1.getInstance().getInfo(cid, bossname);
        if (null == info) {
            return ret;
        }
        switch (type) {
            case 1://积分
                ret = info.getPoints();
                break;
            case 2://次数
                ret = info.getCount();
                break;
        }
        return ret;
    }

    public int getBossRank2(int cid, String bossname, byte type) {
        int ret = -1;
        BossRankInfo2 info = BossRankManager2.getInstance().getInfo(cid, bossname);
        if (null == info) {
            return ret;
        }
        switch (type) {
            case 1://积分
                ret = info.getPoints();
                break;
            case 2://次数
                ret = info.getCount();
                break;
        }
        return ret;
    }

    public int getBossRank3(int cid, String bossname, byte type) {
        int ret = -1;
        BossRankInfo3 info = BossRankManager3.getInstance().getInfo(cid, bossname);
        if (null == info) {
            return ret;
        }
        switch (type) {
            case 1://积分
                ret = info.getPoints();
                break;
            case 2://次数
                ret = info.getCount();
                break;
        }
        return ret;
    }

    public int getBossRank4(int cid, String bossname, byte type) {
        int ret = -1;
        BossRankInfo4 info = BossRankManager4.getInstance().getInfo(cid, bossname);
        if (null == info) {
            return ret;
        }
        switch (type) {
            case 1://积分
                ret = info.getPoints();
                break;
            case 2://次数
                ret = info.getCount();
                break;
        }
        return ret;
    }

    public int getBossRank5(int cid, String bossname, byte type) {
        int ret = -1;
        BossRankInfo5 info = BossRankManager5.getInstance().getInfo(cid, bossname);
        if (null == info) {
            return ret;
        }
        switch (type) {
            case 1://积分
                ret = info.getPoints();
                break;
            case 2://次数
                ret = info.getCount();
                break;
        }
        return ret;
    }

    public int getBossRank6(int cid, String bossname, byte type) {
        int ret = -1;
        BossRankInfo6 info = BossRankManager6.getInstance().getInfo(cid, bossname);
        if (null == info) {
            return ret;
        }
        switch (type) {
            case 1://积分
                ret = info.getPoints();
                break;
            case 2://次数
                ret = info.getCount();
                break;
        }
        return ret;
    }

    public int getBossRank7(int cid, String bossname, byte type) {
        int ret = -1;
        BossRankInfo7 info = BossRankManager7.getInstance().getInfo(cid, bossname);
        if (null == info) {
            return ret;
        }
        switch (type) {
            case 1://积分
                ret = info.getPoints();
                break;
            case 2://次数
                ret = info.getCount();
                break;
        }
        return ret;
    }

    public int getBossRank8(int cid, String bossname, byte type) {
        int ret = -1;
        BossRankInfo8 info = BossRankManager8.getInstance().getInfo(cid, bossname);
        if (null == info) {
            return ret;
        }
        switch (type) {
            case 1://积分
                ret = info.getPoints();
                break;
            case 2://次数
                ret = info.getCount();
                break;
        }
        return ret;
    }

    public int getBossRank9(int cid, String bossname, byte type) {
        int ret = -1;
        BossRankInfo9 info = BossRankManager9.getInstance().getInfo(cid, bossname);
        if (null == info) {
            return ret;
        }
        switch (type) {
            case 1://积分
                ret = info.getPoints();
                break;
            case 2://次数
                ret = info.getCount();
                break;
        }
        return ret;
    }

    public int getBossRank10(int cid, String bossname, byte type) {
        int ret = -1;
        BossRankInfo10 info = BossRankManager10.getInstance().getInfo(cid, bossname);
        if (null == info) {
            return ret;
        }
        switch (type) {
            case 1://积分
                ret = info.getPoints();
                break;
            case 2://次数
                ret = info.getCount();
                break;
        }
        return ret;
    }

    public int getBossRank(String bossname, byte type) {
        return getBossRank(getPlayer().getId(), bossname, type);
    }

    public int getBossRank1(String bossname, byte type) {
        return getBossRank1(getPlayer().getId(), bossname, type);
    }

    public int getBossRank2(String bossname, byte type) {
        return getBossRank2(getPlayer().getId(), bossname, type);
    }

    public int getBossRank3(String bossname, byte type) {
        return getBossRank3(getPlayer().getId(), bossname, type);
    }

    public int getBossRank4(String bossname, byte type) {
        return getBossRank4(getPlayer().getId(), bossname, type);
    }

    public int getBossRank5(String bossname, byte type) {
        return getBossRank5(getPlayer().getId(), bossname, type);
    }

    public int getBossRank6(String bossname, byte type) {
        return getBossRank6(getPlayer().getId(), bossname, type);
    }

    public int getBossRank7(String bossname, byte type) {
        return getBossRank7(getPlayer().getId(), bossname, type);
    }

    public int getBossRank8(String bossname, byte type) {
        return getBossRank8(getPlayer().getId(), bossname, type);
    }

    public int getBossRank9(String bossname, byte type) {
        return getBossRank9(getPlayer().getId(), bossname, type);
    }

    public int getBossRank10(String bossname, byte type) {
        return getBossRank10(getPlayer().getId(), bossname, type);
    }

    public int getBossRankCount(String bossname) {
        return getBossRank(bossname, (byte) 2);
    }

    public int getBossRankCount1(String bossname) {
        return getBossRank1(bossname, (byte) 2);
    }

    public int getBossRankCount2(String bossname) {
        return getBossRank2(bossname, (byte) 2);
    }

    public int getBossRankCount3(String bossname) {
        return getBossRank3(bossname, (byte) 2);
    }

    public int getBossRankCount4(String bossname) {
        return getBossRank4(bossname, (byte) 2);
    }

    public int getBossRankCount5(String bossname) {
        return getBossRank5(bossname, (byte) 2);
    }

    public int getBossRankCount6(String bossname) {
        return getBossRank6(bossname, (byte) 2);
    }

    public int getBossRankCount7(String bossname) {
        return getBossRank7(bossname, (byte) 2);
    }

    public int getBossRankCount8(String bossname) {
        return getBossRank8(bossname, (byte) 2);
    }

    public int getBossRankCount9(String bossname) {
        return getBossRank9(bossname, (byte) 2);
    }

    public int getBossRankCount10(String bossname) {
        return getBossRank10(bossname, (byte) 2);
    }

    public int getBossRankPoints(String bossname) {
        return getBossRank(bossname, (byte) 1);
    }

    public int getBossRankPoints1(String bossname) {
        return getBossRank1(bossname, (byte) 1);
    }

    public int getBossRankPoints2(String bossname) {
        return getBossRank2(bossname, (byte) 1);
    }

    public int getBossRankPoints3(String bossname) {
        return getBossRank3(bossname, (byte) 1);
    }

    public int getBossRankPoints4(String bossname) {
        return getBossRank4(bossname, (byte) 1);
    }

    public int getBossRankPoints5(String bossname) {
        return getBossRank5(bossname, (byte) 1);
    }

    public int getBossRankPoints6(String bossname) {
        return getBossRank6(bossname, (byte) 1);
    }

    public int getBossRankPoints7(String bossname) {
        return getBossRank7(bossname, (byte) 1);
    }

    public int getBossRankPoints8(String bossname) {
        return getBossRank8(bossname, (byte) 1);
    }

    public int getBossRankPoints9(String bossname) {
        return getBossRank9(bossname, (byte) 1);
    }

    public int getBossRankPoints10(String bossname) {
        return getBossRank10(bossname, (byte) 1);
    }

    public int setBossRank(int cid, String cname, String bossname, byte type, int add) {
        return BossRankManager.getInstance().setLog(cid, cname, bossname, type, add);
    }

    public int setBossRank1(int cid, String cname, String bossname, byte type, int add) {
        return BossRankManager1.getInstance().setLog(cid, cname, bossname, type, add);
    }

    public int setBossRank2(int cid, String cname, String bossname, byte type, int add) {
        return BossRankManager2.getInstance().setLog(cid, cname, bossname, type, add);
    }

    public int setBossRank3(int cid, String cname, String bossname, byte type, int add) {
        return BossRankManager3.getInstance().setLog(cid, cname, bossname, type, add);
    }

    public int setBossRank4(int cid, String cname, String bossname, byte type, int add) {
        return BossRankManager4.getInstance().setLog(cid, cname, bossname, type, add);
    }

    public int setBossRank5(int cid, String cname, String bossname, byte type, int add) {
        return BossRankManager5.getInstance().setLog(cid, cname, bossname, type, add);
    }

    public int setBossRank6(int cid, String cname, String bossname, byte type, int add) {
        return BossRankManager6.getInstance().setLog(cid, cname, bossname, type, add);
    }

    public int setBossRank7(int cid, String cname, String bossname, byte type, int add) {
        return BossRankManager7.getInstance().setLog(cid, cname, bossname, type, add);
    }

    public int setBossRank8(int cid, String cname, String bossname, byte type, int add) {
        return BossRankManager8.getInstance().setLog(cid, cname, bossname, type, add);
    }

    public int setBossRank9(int cid, String cname, String bossname, byte type, int add) {
        return BossRankManager9.getInstance().setLog(cid, cname, bossname, type, add);
    }

    public int setBossRank10(int cid, String cname, String bossname, byte type, int add) {
        return BossRankManager10.getInstance().setLog(cid, cname, bossname, type, add);
    }

    public int setBossRank(String bossname, byte type, int add) {
        return setBossRank(getPlayer().getId(), getPlayer().getName(), bossname, type, add);
    }

    public int setBossRank1(String bossname, byte type, int add) {
        return setBossRank1(getPlayer().getId(), getPlayer().getName(), bossname, type, add);
    }

    public int setBossRank2(String bossname, byte type, int add) {
        return setBossRank2(getPlayer().getId(), getPlayer().getName(), bossname, type, add);
    }

    public int setBossRank3(String bossname, byte type, int add) {
        return setBossRank3(getPlayer().getId(), getPlayer().getName(), bossname, type, add);
    }

    public int setBossRank4(String bossname, byte type, int add) {
        return setBossRank4(getPlayer().getId(), getPlayer().getName(), bossname, type, add);
    }

    public int setBossRank5(String bossname, byte type, int add) {
        return setBossRank5(getPlayer().getId(), getPlayer().getName(), bossname, type, add);
    }

    public int setBossRank6(String bossname, byte type, int add) {
        return setBossRank6(getPlayer().getId(), getPlayer().getName(), bossname, type, add);
    }

    public int setBossRank7(String bossname, byte type, int add) {
        return setBossRank7(getPlayer().getId(), getPlayer().getName(), bossname, type, add);
    }

    public int setBossRank8(String bossname, byte type, int add) {
        return setBossRank8(getPlayer().getId(), getPlayer().getName(), bossname, type, add);
    }

    public int setBossRank9(String bossname, byte type, int add) {
        return setBossRank9(getPlayer().getId(), getPlayer().getName(), bossname, type, add);
    }

    public int setBossRank10(String bossname, byte type, int add) {
        return setBossRank10(getPlayer().getId(), getPlayer().getName(), bossname, type, add);
    }

    public int setBossRankCount(String bossname, int add) {
        return setBossRank(getPlayer().getId(), getPlayer().getName(), bossname, (byte) 2, add);
    }

    public int setBossRankCount1(String bossname, int add) {
        return setBossRank1(getPlayer().getId(), getPlayer().getName(), bossname, (byte) 2, add);
    }

    public int setBossRankCount2(String bossname, int add) {
        return setBossRank2(getPlayer().getId(), getPlayer().getName(), bossname, (byte) 2, add);
    }

    public int setBossRankCount3(String bossname, int add) {
        return setBossRank3(getPlayer().getId(), getPlayer().getName(), bossname, (byte) 2, add);
    }

    public int setBossRankCount4(String bossname, int add) {
        return setBossRank4(getPlayer().getId(), getPlayer().getName(), bossname, (byte) 2, add);
    }

    public int setBossRankCount5(String bossname, int add) {
        return setBossRank5(getPlayer().getId(), getPlayer().getName(), bossname, (byte) 2, add);
    }

    public int setBossRankCount6(String bossname, int add) {
        return setBossRank6(getPlayer().getId(), getPlayer().getName(), bossname, (byte) 2, add);
    }

    public int setBossRankCount7(String bossname, int add) {
        return setBossRank7(getPlayer().getId(), getPlayer().getName(), bossname, (byte) 2, add);
    }

    public int setBossRankCount8(String bossname, int add) {
        return setBossRank8(getPlayer().getId(), getPlayer().getName(), bossname, (byte) 2, add);
    }

    public int setBossRankCount9(String bossname, int add) {
        return setBossRank9(getPlayer().getId(), getPlayer().getName(), bossname, (byte) 2, add);
    }

    public int setBossRankCount10(String bossname, int add) {
        return setBossRank10(getPlayer().getId(), getPlayer().getName(), bossname, (byte) 2, add);
    }

    public int setBossRankPoints(String bossname, int add) {
        return setBossRank(getPlayer().getId(), getPlayer().getName(), bossname, (byte) 1, add);
    }

    public int setBossRankPoints1(String bossname, int add) {
        return setBossRank1(getPlayer().getId(), getPlayer().getName(), bossname, (byte) 1, add);
    }

    public int setBossRankPoints2(String bossname, int add) {
        return setBossRank2(getPlayer().getId(), getPlayer().getName(), bossname, (byte) 1, add);
    }

    public int setBossRankPoints3(String bossname, int add) {
        return setBossRank3(getPlayer().getId(), getPlayer().getName(), bossname, (byte) 1, add);
    }

    public int setBossRankPoints4(String bossname, int add) {
        return setBossRank4(getPlayer().getId(), getPlayer().getName(), bossname, (byte) 1, add);
    }

    public int setBossRankPoints5(String bossname, int add) {
        return setBossRank5(getPlayer().getId(), getPlayer().getName(), bossname, (byte) 1, add);
    }

    public int setBossRankPoints6(String bossname, int add) {
        return setBossRank6(getPlayer().getId(), getPlayer().getName(), bossname, (byte) 1, add);
    }

    public int setBossRankPoints7(String bossname, int add) {
        return setBossRank7(getPlayer().getId(), getPlayer().getName(), bossname, (byte) 1, add);
    }

    public int setBossRankPoints8(String bossname, int add) {
        return setBossRank8(getPlayer().getId(), getPlayer().getName(), bossname, (byte) 1, add);
    }

    public int setBossRankPoints9(String bossname, int add) {
        return setBossRank9(getPlayer().getId(), getPlayer().getName(), bossname, (byte) 1, add);
    }

    public int setBossRankPoints10(String bossname, int add) {
        return setBossRank10(getPlayer().getId(), getPlayer().getName(), bossname, (byte) 1, add);
    }

    public int setBossRankCount(String bossname) {
        return setBossRank(getPlayer().getId(), getPlayer().getName(), bossname, (byte) 2, 1);
    }

    public int setBossRankCount1(String bossname) {
        return setBossRank1(getPlayer().getId(), getPlayer().getName(), bossname, (byte) 2, 1);
    }

    public int setBossRankCount2(String bossname) {
        return setBossRank2(getPlayer().getId(), getPlayer().getName(), bossname, (byte) 2, 1);
    }

    public int setBossRankCount3(String bossname) {
        return setBossRank3(getPlayer().getId(), getPlayer().getName(), bossname, (byte) 2, 1);
    }

    public int setBossRankCount4(String bossname) {
        return setBossRank4(getPlayer().getId(), getPlayer().getName(), bossname, (byte) 2, 1);
    }

    public int setBossRankCount5(String bossname) {
        return setBossRank5(getPlayer().getId(), getPlayer().getName(), bossname, (byte) 2, 1);
    }

    public int setBossRankCount6(String bossname) {
        return setBossRank6(getPlayer().getId(), getPlayer().getName(), bossname, (byte) 2, 1);
    }

    public int setBossRankCount7(String bossname) {
        return setBossRank7(getPlayer().getId(), getPlayer().getName(), bossname, (byte) 2, 1);
    }

    public int setBossRankCount8(String bossname) {
        return setBossRank8(getPlayer().getId(), getPlayer().getName(), bossname, (byte) 2, 1);
    }

    public int setBossRankCount9(String bossname) {
        return setBossRank9(getPlayer().getId(), getPlayer().getName(), bossname, (byte) 2, 1);
    }

    public int setBossRankCount10(String bossname) {
        return setBossRank10(getPlayer().getId(), getPlayer().getName(), bossname, (byte) 2, 1);
    }

    public int setBossRankPoints(String bossname) {
        return setBossRank(getPlayer().getId(), getPlayer().getName(), bossname, (byte) 1, 1);
    }

    public int setBossRankPoints1(String bossname) {
        return setBossRank1(getPlayer().getId(), getPlayer().getName(), bossname, (byte) 1, 1);
    }

    public int setBossRankPoints2(String bossname) {
        return setBossRank2(getPlayer().getId(), getPlayer().getName(), bossname, (byte) 1, 1);
    }

    public int setBossRankPoints3(String bossname) {
        return setBossRank3(getPlayer().getId(), getPlayer().getName(), bossname, (byte) 1, 1);
    }

    public int setBossRankPoints4(String bossname) {
        return setBossRank4(getPlayer().getId(), getPlayer().getName(), bossname, (byte) 1, 1);
    }

    public int setBossRankPoints5(String bossname) {
        return setBossRank5(getPlayer().getId(), getPlayer().getName(), bossname, (byte) 1, 1);
    }

    public int setBossRankPoints6(String bossname) {
        return setBossRank6(getPlayer().getId(), getPlayer().getName(), bossname, (byte) 1, 1);
    }

    public int setBossRankPoints7(String bossname) {
        return setBossRank7(getPlayer().getId(), getPlayer().getName(), bossname, (byte) 1, 1);
    }

    public int setBossRankPoints8(String bossname) {
        return setBossRank8(getPlayer().getId(), getPlayer().getName(), bossname, (byte) 1, 1);
    }

    public int setBossRankPoints9(String bossname) {
        return setBossRank9(getPlayer().getId(), getPlayer().getName(), bossname, (byte) 1, 1);
    }

    public int setBossRankPoints10(String bossname) {
        return setBossRank10(getPlayer().getId(), getPlayer().getName(), bossname, (byte) 1, 1);
    }

    public List<BossRankInfo> getBossRankTop(String bossname, byte type) {
        return BossRankManager.getInstance().getRank(bossname, type);
    }

    public List<BossRankInfo1> getBossRankTop1(String bossname, byte type) {
        return BossRankManager1.getInstance().getRank(bossname, type);
    }

    public List<BossRankInfo2> getBossRankTop2(String bossname, byte type) {
        return BossRankManager2.getInstance().getRank(bossname, type);
    }

    public List<BossRankInfo3> getBossRankTop3(String bossname, byte type) {
        return BossRankManager3.getInstance().getRank(bossname, type);
    }

    public List<BossRankInfo4> getBossRankTop4(String bossname, byte type) {
        return BossRankManager4.getInstance().getRank(bossname, type);
    }

    public List<BossRankInfo5> getBossRankTop5(String bossname, byte type) {
        return BossRankManager5.getInstance().getRank(bossname, type);
    }

    public List<BossRankInfo6> getBossRankTop6(String bossname, byte type) {
        return BossRankManager6.getInstance().getRank(bossname, type);
    }

    public List<BossRankInfo7> getBossRankTop7(String bossname, byte type) {
        return BossRankManager7.getInstance().getRank(bossname, type);
    }

    public List<BossRankInfo8> getBossRankTop8(String bossname, byte type) {
        return BossRankManager8.getInstance().getRank(bossname, type);
    }

    public List<BossRankInfo9> getBossRankTop9(String bossname, byte type) {
        return BossRankManager9.getInstance().getRank(bossname, type);
    }

    public List<BossRankInfo10> getBossRankTop10(String bossname, byte type) {
        return BossRankManager10.getInstance().getRank(bossname, type);
    }

    public List<BossRankInfo> getBossRankCountTop(String bossname) {
        return BossRankManager.getInstance().getRank(bossname, 2);
    }

    public List<BossRankInfo1> getBossRankCountTop1(String bossname) {
        return BossRankManager1.getInstance().getRank(bossname, 2);
    }

    public List<BossRankInfo2> getBossRankCountTop2(String bossname) {
        return BossRankManager2.getInstance().getRank(bossname, 2);
    }

    public List<BossRankInfo3> getBossRankCountTop3(String bossname) {
        return BossRankManager3.getInstance().getRank(bossname, 2);
    }

    public List<BossRankInfo4> getBossRankCountTop4(String bossname) {
        return BossRankManager4.getInstance().getRank(bossname, 2);
    }

    public List<BossRankInfo5> getBossRankCountTop5(String bossname) {
        return BossRankManager5.getInstance().getRank(bossname, 2);
    }

    public List<BossRankInfo6> getBossRankCountTop6(String bossname) {
        return BossRankManager6.getInstance().getRank(bossname, 2);
    }

    public List<BossRankInfo7> getBossRankCountTop7(String bossname) {
        return BossRankManager7.getInstance().getRank(bossname, 2);
    }

    public List<BossRankInfo8> getBossRankCountTop8(String bossname) {
        return BossRankManager8.getInstance().getRank(bossname, 2);
    }

    public List<BossRankInfo9> getBossRankCountTop9(String bossname) {
        return BossRankManager9.getInstance().getRank(bossname, 2);
    }

    public List<BossRankInfo10> getBossRankCountTop10(String bossname) {
        return BossRankManager10.getInstance().getRank(bossname, 2);
    }

    public List<BossRankInfo> getBossRankPointsTop(String bossname) {
        return BossRankManager.getInstance().getRank(bossname, 1);
    }

    public List<BossRankInfo1> getBossRankPointsTop1(String bossname) {
        return BossRankManager1.getInstance().getRank(bossname, 1);
    }

    public List<BossRankInfo2> getBossRankPointsTop2(String bossname) {
        return BossRankManager2.getInstance().getRank(bossname, 1);
    }

    public List<BossRankInfo3> getBossRankPointsTop3(String bossname) {
        return BossRankManager3.getInstance().getRank(bossname, 1);
    }

    public List<BossRankInfo4> getBossRankPointsTop4(String bossname) {
        return BossRankManager4.getInstance().getRank(bossname, 1);
    }

    public List<BossRankInfo5> getBossRankPointsTop5(String bossname) {
        return BossRankManager5.getInstance().getRank(bossname, 1);
    }

    public List<BossRankInfo6> getBossRankPointsTop6(String bossname) {
        return BossRankManager6.getInstance().getRank(bossname, 1);
    }

    public List<BossRankInfo7> getBossRankPointsTop7(String bossname) {
        return BossRankManager7.getInstance().getRank(bossname, 1);
    }

    public List<BossRankInfo8> getBossRankPointsTop8(String bossname) {
        return BossRankManager8.getInstance().getRank(bossname, 1);
    }

    public List<BossRankInfo9> getBossRankPointsTop9(String bossname) {
        return BossRankManager9.getInstance().getRank(bossname, 1);
    }

    public List<BossRankInfo10> getBossRankPointsTop10(String bossname) {
        return BossRankManager10.getInstance().getRank(bossname, 1);
    }

    public List<Integer> getBosslogDCidTop(String bossname) {
        return c.getPlayer().getBosslogDCidTop(bossname);
    }

    public DruidDataSource getDataSource() {
        return DBConPool.getInstance().getDataSource();
    }

    public double getDoubleMin(double x, double y) {
        return java.lang.Math.min(x, y);
    }

    public double getDoubleMax(double x, double y) {
        return java.lang.Math.max(x, y);
    }

    public double getDoubleFloor(double x) {
        return java.lang.Math.floor(x);
    }

    public double getDoubleRandom() {
        return java.lang.Math.random();
    }

    public double getDoublePow(double d, double d1) {
        return java.lang.Math.pow(d, d1);
    }

    public double getDoubleCeil(double x) {
        return java.lang.Math.ceil(x);
    }

    public double getDoubleAbs(double x) {
        return java.lang.Math.abs(x);
    }

    public List<Map<String, String>> customSqlResult(String sql, Object o1) {
        List<Map<String, String>> customSqlResult2 = new LinkedList<>();
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(o1));
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData data = rs.getMetaData();
            while (rs.next()) {
                Map<String, String> customSql2 = new HashMap<>();
                for (int i = 1; i <= data.getColumnCount(); i++) {
                    String columnName = data.getColumnName(i);
                    String vi = "";
                    try {
                        vi = rs.getString(columnName);
                        customSql2.put(columnName, vi == null ? "" : vi);
                    } catch (Exception Ex) {

                    }

                }
                customSqlResult2.add(customSql2);
            }
            rs.close();
            ps.close();
        } catch (SQLException Ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", Ex);

        }
        return customSqlResult2;
    }

    public List<Map<String, String>> customSqlResult(String sql, Object o1, Object o2) {
        List<Map<String, String>> customSqlResult2 = new LinkedList<>();
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(o1));
            ps.setString(2, String.valueOf(o2));
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData data = rs.getMetaData();
            while (rs.next()) {
                Map<String, String> customSql2 = new HashMap<>();
                for (int i = 1; i <= data.getColumnCount(); i++) {
                    String columnName = data.getColumnName(i);
                    String vi = "";
                    try {
                        vi = rs.getString(columnName);
                        customSql2.put(columnName, vi == null ? "" : vi);
                    } catch (Exception Ex) {

                    }

                }
                customSqlResult2.add(customSql2);
            }
            rs.close();
            ps.close();
        } catch (SQLException Ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", Ex);

        }
        return customSqlResult2;
    }

    public List<Map<String, String>> customSqlResult(String sql, Object o1, Object o2, Object o3) {
        List<Map<String, String>> customSqlResult2 = new LinkedList<>();
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(o1));
            ps.setString(2, String.valueOf(o2));
            ps.setString(3, String.valueOf(o3));
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData data = rs.getMetaData();
            while (rs.next()) {
                Map<String, String> customSql2 = new HashMap<>();
                for (int i = 1; i <= data.getColumnCount(); i++) {
                    String columnName = data.getColumnName(i);
                    String vi = "";
                    try {
                        vi = rs.getString(columnName);
                        customSql2.put(columnName, vi == null ? "" : vi);
                    } catch (Exception Ex) {

                    }

                }
                customSqlResult2.add(customSql2);
            }
            rs.close();
            ps.close();
        } catch (SQLException Ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", Ex);

        }
        return customSqlResult2;
    }

    public List<Map<String, String>> customSqlResult(String sql, Object o1, Object o2, Object o3, Object o4) {
        List<Map<String, String>> customSqlResult2 = new LinkedList<>();
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(o1));
            ps.setString(2, String.valueOf(o2));
            ps.setString(3, String.valueOf(o3));
            ps.setString(4, String.valueOf(o4));
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData data = rs.getMetaData();
            while (rs.next()) {
                Map<String, String> customSql2 = new HashMap<>();
                for (int i = 1; i <= data.getColumnCount(); i++) {
                    String columnName = data.getColumnName(i);
                    String vi = "";
                    try {
                        vi = rs.getString(columnName);
                        customSql2.put(columnName, vi == null ? "" : vi);
                    } catch (Exception Ex) {

                    }

                }
                customSqlResult2.add(customSql2);
            }
            rs.close();
            ps.close();
        } catch (SQLException Ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", Ex);

        }
        return customSqlResult2;
    }

    public List<Map<String, String>> customSqlResult(String sql, Object o1, Object o2, Object o3, Object o4, Object o5) {
        List<Map<String, String>> customSqlResult2 = new LinkedList<>();
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(o1));
            ps.setString(2, String.valueOf(o2));
            ps.setString(3, String.valueOf(o3));
            ps.setString(4, String.valueOf(o4));
            ps.setString(5, String.valueOf(o5));
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData data = rs.getMetaData();
            while (rs.next()) {
                Map<String, String> customSql2 = new HashMap<>();
                for (int i = 1; i <= data.getColumnCount(); i++) {
                    String columnName = data.getColumnName(i);
                    String vi = "";
                    try {
                        vi = rs.getString(columnName);
                        customSql2.put(columnName, vi == null ? "" : vi);
                    } catch (Exception Ex) {

                    }

                }
                customSqlResult2.add(customSql2);
            }
            rs.close();
            ps.close();
        } catch (SQLException Ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", Ex);

        }
        return customSqlResult2;
    }

    public List<Map<String, String>> customSqlResult(String sql, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6) {
        List<Map<String, String>> customSqlResult2 = new LinkedList<>();
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(o1));
            ps.setString(2, String.valueOf(o2));
            ps.setString(3, String.valueOf(o3));
            ps.setString(4, String.valueOf(o4));
            ps.setString(5, String.valueOf(o5));
            ps.setString(6, String.valueOf(o6));
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData data = rs.getMetaData();
            while (rs.next()) {
                Map<String, String> customSql2 = new HashMap<>();
                for (int i = 1; i <= data.getColumnCount(); i++) {
                    String columnName = data.getColumnName(i);
                    String vi = "";
                    try {
                        vi = rs.getString(columnName);
                        customSql2.put(columnName, vi == null ? "" : vi);
                    } catch (Exception Ex) {

                    }

                }
                customSqlResult2.add(customSql2);
            }
            rs.close();
            ps.close();
        } catch (SQLException Ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", Ex);

        }
        return customSqlResult2;
    }

    public List<Map<String, String>> customSqlResult(String sql, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7) {
        List<Map<String, String>> customSqlResult2 = new LinkedList<>();
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(o1));
            ps.setString(2, String.valueOf(o2));
            ps.setString(3, String.valueOf(o3));
            ps.setString(4, String.valueOf(o4));
            ps.setString(5, String.valueOf(o5));
            ps.setString(6, String.valueOf(o6));
            ps.setString(7, String.valueOf(o7));
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData data = rs.getMetaData();
            while (rs.next()) {
                Map<String, String> customSql2 = new HashMap<>();
                for (int i = 1; i <= data.getColumnCount(); i++) {
                    String columnName = data.getColumnName(i);
                    String vi = "";
                    try {
                        vi = rs.getString(columnName);
                        customSql2.put(columnName, vi == null ? "" : vi);
                    } catch (Exception Ex) {

                    }

                }
                customSqlResult2.add(customSql2);
            }
            rs.close();
            ps.close();
        } catch (SQLException Ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", Ex);

        }
        return customSqlResult2;
    }

    public List<Map<String, String>> customSqlResult(String sql, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8) {
        List<Map<String, String>> customSqlResult2 = new LinkedList<>();
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(o1));
            ps.setString(2, String.valueOf(o2));
            ps.setString(3, String.valueOf(o3));
            ps.setString(4, String.valueOf(o4));
            ps.setString(5, String.valueOf(o5));
            ps.setString(6, String.valueOf(o6));
            ps.setString(7, String.valueOf(o7));
            ps.setString(8, String.valueOf(o8));
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData data = rs.getMetaData();
            while (rs.next()) {
                Map<String, String> customSql2 = new HashMap<>();
                for (int i = 1; i <= data.getColumnCount(); i++) {
                    String columnName = data.getColumnName(i);
                    String vi = "";
                    try {
                        vi = rs.getString(columnName);
                        customSql2.put(columnName, vi == null ? "" : vi);
                    } catch (Exception Ex) {

                    }

                }
                customSqlResult2.add(customSql2);
            }
            rs.close();
            ps.close();
        } catch (SQLException Ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", Ex);

        }
        return customSqlResult2;
    }

    public List<Map<String, String>> customSqlResult(String sql, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8, Object o9) {
        List<Map<String, String>> customSqlResult2 = new LinkedList<>();
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(o1));
            ps.setString(2, String.valueOf(o2));
            ps.setString(3, String.valueOf(o3));
            ps.setString(4, String.valueOf(o4));
            ps.setString(5, String.valueOf(o5));
            ps.setString(6, String.valueOf(o6));
            ps.setString(7, String.valueOf(o7));
            ps.setString(8, String.valueOf(o8));
            ps.setString(9, String.valueOf(o9));
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData data = rs.getMetaData();
            while (rs.next()) {
                Map<String, String> customSql2 = new HashMap<>();
                for (int i = 1; i <= data.getColumnCount(); i++) {
                    String columnName = data.getColumnName(i);
                    String vi = "";
                    try {
                        vi = rs.getString(columnName);
                        customSql2.put(columnName, vi == null ? "" : vi);
                    } catch (Exception Ex) {

                    }

                }
                customSqlResult2.add(customSql2);
            }
            rs.close();
            ps.close();
        } catch (SQLException Ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", Ex);

        }
        return customSqlResult2;
    }

    public List<Map<String, String>> customSqlResult(String sql, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8, Object o9, Object o10) {
        List<Map<String, String>> customSqlResult2 = new LinkedList<>();
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(o1));
            ps.setString(2, String.valueOf(o2));
            ps.setString(3, String.valueOf(o3));
            ps.setString(4, String.valueOf(o4));
            ps.setString(5, String.valueOf(o5));
            ps.setString(6, String.valueOf(o6));
            ps.setString(7, String.valueOf(o7));
            ps.setString(8, String.valueOf(o8));
            ps.setString(9, String.valueOf(o9));
            ps.setString(10, String.valueOf(o10));
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData data = rs.getMetaData();
            while (rs.next()) {
                Map<String, String> customSql2 = new HashMap<>();
                for (int i = 1; i <= data.getColumnCount(); i++) {
                    String columnName = data.getColumnName(i);
                    String vi = "";
                    try {
                        vi = rs.getString(columnName);
                        customSql2.put(columnName, vi == null ? "" : vi);
                    } catch (Exception Ex) {

                    }

                }
                customSqlResult2.add(customSql2);
            }
            rs.close();
            ps.close();
        } catch (SQLException Ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", Ex);

        }
        return customSqlResult2;
    }

    public void customSqlUpdate(String sql, Object o1) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps;
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(o1));
            ps.execute();
            ps.close();
        } catch (SQLException ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", ex);
        }
    }

    public void customSqlUpdate(String sql, Object o1, Object o2) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps;
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(o1));
            ps.setString(2, String.valueOf(o2));
            ps.execute();
            ps.close();
        } catch (SQLException ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", ex);
        }
    }

    public void customSqlUpdate(String sql, Object o1, Object o2, Object o3) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps;
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(o1));
            ps.setString(2, String.valueOf(o2));
            ps.setString(3, String.valueOf(o3));
            ps.execute();
            ps.close();
        } catch (SQLException ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", ex);
        }
    }

    public void customSqlUpdate(String sql, Object o1, Object o2, Object o3, Object o4) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps;
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(o1));
            ps.setString(2, String.valueOf(o2));
            ps.setString(3, String.valueOf(o3));
            ps.setString(4, String.valueOf(o4));
            ps.execute();
            ps.close();
        } catch (SQLException ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", ex);
        }
    }

    public void customSqlUpdate(String sql, Object o1, Object o2, Object o3, Object o4, Object o5) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps;
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(o1));
            ps.setString(2, String.valueOf(o2));
            ps.setString(3, String.valueOf(o3));
            ps.setString(4, String.valueOf(o4));
            ps.setString(5, String.valueOf(o5));
            ps.execute();
            ps.close();
        } catch (SQLException ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", ex);
        }
    }

    public void customSqlUpdate(String sql, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps;
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(o1));
            ps.setString(2, String.valueOf(o2));
            ps.setString(3, String.valueOf(o3));
            ps.setString(4, String.valueOf(o4));
            ps.setString(5, String.valueOf(o5));
            ps.setString(6, String.valueOf(o6));
            ps.execute();
            ps.close();
        } catch (SQLException ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", ex);
        }
    }

    public void customSqlUpdate(String sql, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps;
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(o1));
            ps.setString(2, String.valueOf(o2));
            ps.setString(3, String.valueOf(o3));
            ps.setString(4, String.valueOf(o4));
            ps.setString(5, String.valueOf(o5));
            ps.setString(6, String.valueOf(o6));
            ps.setString(7, String.valueOf(o7));
            ps.execute();
            ps.close();
        } catch (SQLException ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", ex);
        }
    }

    public void customSqlUpdate(String sql, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps;
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(o1));
            ps.setString(2, String.valueOf(o2));
            ps.setString(3, String.valueOf(o3));
            ps.setString(4, String.valueOf(o4));
            ps.setString(5, String.valueOf(o5));
            ps.setString(6, String.valueOf(o6));
            ps.setString(7, String.valueOf(o7));
            ps.setString(8, String.valueOf(o8));
            ps.execute();
            ps.close();
        } catch (SQLException ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", ex);
        }
    }

    public void customSqlUpdate(String sql, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8, Object o9) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps;
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(o1));
            ps.setString(2, String.valueOf(o2));
            ps.setString(3, String.valueOf(o3));
            ps.setString(4, String.valueOf(o4));
            ps.setString(5, String.valueOf(o5));
            ps.setString(6, String.valueOf(o6));
            ps.setString(7, String.valueOf(o7));
            ps.setString(8, String.valueOf(o8));
            ps.setString(9, String.valueOf(o9));
            ps.execute();
            ps.close();
        } catch (SQLException ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", ex);
        }
    }

    public void customSqlUpdate(String sql, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8, Object o9, Object o10) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps;
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(o1));
            ps.setString(2, String.valueOf(o2));
            ps.setString(3, String.valueOf(o3));
            ps.setString(4, String.valueOf(o4));
            ps.setString(5, String.valueOf(o5));
            ps.setString(6, String.valueOf(o6));
            ps.setString(7, String.valueOf(o7));
            ps.setString(8, String.valueOf(o8));
            ps.setString(9, String.valueOf(o9));
            ps.setString(10, String.valueOf(o10));
            ps.execute();
            ps.close();
        } catch (SQLException ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", ex);
        }
    }

    public void customSqlUpdate(String sql, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8, Object o9, Object o10, Object o11) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps;
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(o1));
            ps.setString(2, String.valueOf(o2));
            ps.setString(3, String.valueOf(o3));
            ps.setString(4, String.valueOf(o4));
            ps.setString(5, String.valueOf(o5));
            ps.setString(6, String.valueOf(o6));
            ps.setString(7, String.valueOf(o7));
            ps.setString(8, String.valueOf(o8));
            ps.setString(9, String.valueOf(o9));
            ps.setString(10, String.valueOf(o10));
            ps.setString(11, String.valueOf(o11));
            ps.execute();
            ps.close();
        } catch (SQLException ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", ex);
        }
    }

    public void customSqlUpdate(String sql, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8, Object o9, Object o10, Object o11, Object o12) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps;
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(o1));
            ps.setString(2, String.valueOf(o2));
            ps.setString(3, String.valueOf(o3));
            ps.setString(4, String.valueOf(o4));
            ps.setString(5, String.valueOf(o5));
            ps.setString(6, String.valueOf(o6));
            ps.setString(7, String.valueOf(o7));
            ps.setString(8, String.valueOf(o8));
            ps.setString(9, String.valueOf(o9));
            ps.setString(10, String.valueOf(o10));
            ps.setString(11, String.valueOf(o11));
            ps.setString(12, String.valueOf(o12));
            ps.execute();
            ps.close();
        } catch (SQLException ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", ex);
        }
    }

    public void customSqlUpdate(String sql, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8, Object o9, Object o10, Object o11, Object o12, Object o13) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps;
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(o1));
            ps.setString(2, String.valueOf(o2));
            ps.setString(3, String.valueOf(o3));
            ps.setString(4, String.valueOf(o4));
            ps.setString(5, String.valueOf(o5));
            ps.setString(6, String.valueOf(o6));
            ps.setString(7, String.valueOf(o7));
            ps.setString(8, String.valueOf(o8));
            ps.setString(9, String.valueOf(o9));
            ps.setString(10, String.valueOf(o10));
            ps.setString(11, String.valueOf(o11));
            ps.setString(12, String.valueOf(o12));
            ps.setString(13, String.valueOf(o13));
            ps.execute();
            ps.close();
        } catch (SQLException ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", ex);
        }
    }

    public void customSqlInsert(String sql, Object o1) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps;
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(o1));
            ps.executeUpdate();
            ps.close();
        } catch (SQLException Ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", Ex);
            //   log.error("Error while insert bosslog.", Ex);
        }
    }

    public void customSqlInsert(String sql, Object o1, Object o2) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps;
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(o1));
            ps.setString(2, String.valueOf(o2));
            ps.executeUpdate();
            ps.close();
        } catch (SQLException Ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", Ex);
            //   log.error("Error while insert bosslog.", Ex);
        }
    }

    public void customSqlInsert(String sql, Object o1, Object o2, Object o3) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps;
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(o1));
            ps.setString(2, String.valueOf(o2));
            ps.setString(3, String.valueOf(o3));
            ps.executeUpdate();
            ps.close();
        } catch (SQLException Ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", Ex);
            //   log.error("Error while insert bosslog.", Ex);
        }
    }

    public void customSqlInsert(String sql, Object o1, Object o2, Object o3, Object o4) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps;
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(o1));
            ps.setString(2, String.valueOf(o2));
            ps.setString(3, String.valueOf(o3));
            ps.setString(4, String.valueOf(o4));
            ps.executeUpdate();
            ps.close();
        } catch (SQLException Ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", Ex);
            //   log.error("Error while insert bosslog.", Ex);
        }
    }

    public void customSqlInsert(String sql, Object o1, Object o2, Object o3, Object o4, Object o5) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps;
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(o1));
            ps.setString(2, String.valueOf(o2));
            ps.setString(3, String.valueOf(o3));
            ps.setString(4, String.valueOf(o4));
            ps.setString(5, String.valueOf(o5));
            ps.executeUpdate();
            ps.close();
        } catch (SQLException Ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", Ex);
            //   log.error("Error while insert bosslog.", Ex);
        }
    }

    public void customSqlInsert(String sql, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps;
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(o1));
            ps.setString(2, String.valueOf(o2));
            ps.setString(3, String.valueOf(o3));
            ps.setString(4, String.valueOf(o4));
            ps.setString(5, String.valueOf(o5));
            ps.setString(6, String.valueOf(o6));
            ps.executeUpdate();
            ps.close();
        } catch (SQLException Ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", Ex);
            //   log.error("Error while insert bosslog.", Ex);
        }
    }

    public void customSqlInsert(String sql, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps;
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(o1));
            ps.setString(2, String.valueOf(o2));
            ps.setString(3, String.valueOf(o3));
            ps.setString(4, String.valueOf(o4));
            ps.setString(5, String.valueOf(o5));
            ps.setString(6, String.valueOf(o6));
            ps.setString(7, String.valueOf(o7));
            ps.executeUpdate();
            ps.close();
        } catch (SQLException Ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", Ex);
            //   log.error("Error while insert bosslog.", Ex);
        }
    }

    public void customSqlInsert(String sql, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps;
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(o1));
            ps.setString(2, String.valueOf(o2));
            ps.setString(3, String.valueOf(o3));
            ps.setString(4, String.valueOf(o4));
            ps.setString(5, String.valueOf(o5));
            ps.setString(6, String.valueOf(o6));
            ps.setString(7, String.valueOf(o7));
            ps.setString(8, String.valueOf(o8));
            ps.executeUpdate();
            ps.close();
        } catch (SQLException Ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", Ex);
            //   log.error("Error while insert bosslog.", Ex);
        }
    }

    public void customSqlInsert(String sql, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8, Object o9) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps;
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(o1));
            ps.setString(2, String.valueOf(o2));
            ps.setString(3, String.valueOf(o3));
            ps.setString(4, String.valueOf(o4));
            ps.setString(5, String.valueOf(o5));
            ps.setString(6, String.valueOf(o6));
            ps.setString(7, String.valueOf(o7));
            ps.setString(8, String.valueOf(o8));
            ps.setString(9, String.valueOf(o9));
            ps.executeUpdate();
            ps.close();
        } catch (SQLException Ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", Ex);
            //   log.error("Error while insert bosslog.", Ex);
        }
    }

    public void customSqlInsert(String sql, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8, Object o9, Object o10) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps;
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(o1));
            ps.setString(2, String.valueOf(o2));
            ps.setString(3, String.valueOf(o3));
            ps.setString(4, String.valueOf(o4));
            ps.setString(5, String.valueOf(o5));
            ps.setString(6, String.valueOf(o6));
            ps.setString(7, String.valueOf(o7));
            ps.setString(8, String.valueOf(o8));
            ps.setString(9, String.valueOf(o9));
            ps.setString(10, String.valueOf(o10));
            ps.executeUpdate();
            ps.close();
        } catch (SQLException Ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", Ex);
            //   log.error("Error while insert bosslog.", Ex);
        }
    }

    public void customSqlInsert(String sql, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8, Object o9, Object o10, Object o11) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps;
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(o1));
            ps.setString(2, String.valueOf(o2));
            ps.setString(3, String.valueOf(o3));
            ps.setString(4, String.valueOf(o4));
            ps.setString(5, String.valueOf(o5));
            ps.setString(6, String.valueOf(o6));
            ps.setString(7, String.valueOf(o7));
            ps.setString(8, String.valueOf(o8));
            ps.setString(9, String.valueOf(o9));
            ps.setString(10, String.valueOf(o10));
            ps.setString(11, String.valueOf(o11));
            ps.executeUpdate();
            ps.close();
        } catch (SQLException Ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", Ex);
            //   log.error("Error while insert bosslog.", Ex);
        }
    }

    public void customSqlInsert(String sql, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8, Object o9, Object o10, Object o11, Object o12) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps;
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(o1));
            ps.setString(2, String.valueOf(o2));
            ps.setString(3, String.valueOf(o3));
            ps.setString(4, String.valueOf(o4));
            ps.setString(5, String.valueOf(o5));
            ps.setString(6, String.valueOf(o6));
            ps.setString(7, String.valueOf(o7));
            ps.setString(8, String.valueOf(o8));
            ps.setString(9, String.valueOf(o9));
            ps.setString(10, String.valueOf(o10));
            ps.setString(11, String.valueOf(o11));
            ps.setString(12, String.valueOf(o12));
            ps.executeUpdate();
            ps.close();
        } catch (SQLException Ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", Ex);
            //   log.error("Error while insert bosslog.", Ex);
        }
    }

    public void customSqlInsert(String sql, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8, Object o9, Object o10, Object o11, Object o12, Object o13) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps;
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(o1));
            ps.setString(2, String.valueOf(o2));
            ps.setString(3, String.valueOf(o3));
            ps.setString(4, String.valueOf(o4));
            ps.setString(5, String.valueOf(o5));
            ps.setString(6, String.valueOf(o6));
            ps.setString(7, String.valueOf(o7));
            ps.setString(8, String.valueOf(o8));
            ps.setString(9, String.valueOf(o9));
            ps.setString(10, String.valueOf(o10));
            ps.setString(11, String.valueOf(o11));
            ps.setString(12, String.valueOf(o12));
            ps.setString(13, String.valueOf(o13));
            ps.executeUpdate();
            ps.close();
        } catch (SQLException Ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", Ex);
            //   log.error("Error while insert bosslog.", Ex);
        }
    }

    public int getPQLog(String key, boolean reset) {
        return c.getPlayer().getPQLog(key, reset);
    }

    public void addPQLog(String key) {
        c.getPlayer().addPQLog(key);
    }

    public void addPQLog(String key, int value) {
        c.getPlayer().addPQLog(key, value);
    }
    public int getOneTimeLog(final String bossid) {
        return this.getPlayer().getOneTimeLog(bossid);
    }
    
    public void setOneTimeLog(final String bossid) {
        this.getPlayer().setOneTimeLog(bossid);
    }
    
     public int getAccYjLog(final String bossid) {
        return this.getPlayer().getAccYjLog(bossid);
    }
    
    public void setAccYjLog(final String bossid) {
        this.getPlayer().setAccYjLog(bossid);
    }
}
