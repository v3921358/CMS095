package server.life;

import client.inventory.MapleInventoryType;
import constants.GameConstants;
import database.DBConPool;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import provider.MapleData;
import provider.MapleDataProvider;
import provider.MapleDataProviderFactory;
import provider.MapleDataTool;
import server.MapleItemInformationProvider;
import server.StructFamiliar;
import tools.FileoutputUtil;

public class MapleMonsterInformationProvider {

    private static final MapleMonsterInformationProvider instance = new MapleMonsterInformationProvider();
    private final Map<Integer, String> mobCache = new HashMap<>();
    private final Map<Integer, ArrayList<MonsterDropEntry>> drops = new HashMap<Integer, ArrayList<MonsterDropEntry>>();
    private final List<MonsterGlobalDropEntry> globaldrops = new ArrayList<MonsterGlobalDropEntry>();
    private static final MapleDataProvider stringDataWZ = MapleDataProviderFactory.getDataProvider(new File((System.getProperty("wzpath") != null ? System.getProperty("wzpath") : "") + "wz/String.wz"));
    private static final MapleData mobStringData = stringDataWZ.getData("MonsterBook.img");

    public static MapleMonsterInformationProvider getInstance() {
        return instance;
    }

    public List<MonsterGlobalDropEntry> getGlobalDrop() {
        return globaldrops;
    }

    public void load() {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            ps = con.prepareStatement("SELECT * FROM drop_data_global WHERE chance > 0");
            rs = ps.executeQuery();

            while (rs.next()) {
                globaldrops.add(
                        new MonsterGlobalDropEntry(
                                rs.getInt("itemid"),
                                rs.getInt("chance"),
                                rs.getInt("continent"),
                                rs.getByte("dropType"),
                                rs.getInt("minimum_quantity"),
                                rs.getInt("maximum_quantity"),
                                rs.getInt("questid")));
            }
            rs.close();
            ps.close();

            ps = con.prepareStatement("SELECT dropperid FROM drop_data");
            List<Integer> mobIds = new ArrayList<Integer>();
            rs = ps.executeQuery();
            while (rs.next()) {
                if (!mobIds.contains(rs.getInt("dropperid"))) {
                    loadDrop(rs.getInt("dropperid"));
                    mobIds.add(rs.getInt("dropperid"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving drop" + e);
            FileoutputUtil.outputFileError("logs/数据库异常.txt", e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ignore) {
            }
        }
    }

    public ArrayList<MonsterDropEntry> retrieveDrop(final int monsterId) {
        return drops.get(Integer.valueOf(monsterId));
    }

    private void loadDrop(final int monsterId) {
        final ArrayList<MonsterDropEntry> ret = new ArrayList<MonsterDropEntry>();

        PreparedStatement ps = null;
        ResultSet rs = null;
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            final MapleMonsterStats mons = MapleLifeFactory.getMonsterStats(monsterId);
            if (mons == null) {
                return;
            }
            ps = con.prepareStatement("SELECT * FROM drop_data WHERE dropperid = ?");
            ps.setInt(1, monsterId);
            rs = ps.executeQuery();
            int itemid;
            int chance;
            boolean doneMesos = false;
            while (rs.next()) {
                itemid = rs.getInt("itemid");
                chance = rs.getInt("chance");
                if (GameConstants.getInventoryType(itemid) == MapleInventoryType.EQUIP) {
                    chance *= 1; //in GMS/SEA it was raised
                }

                if (itemid / 1000 == 1352) {
                    chance = 0;
                }
                if (itemid / 1000 == 1362) {
                    chance = 0;
                }

                ret.add(new MonsterDropEntry(
                        itemid,
                        chance,
                        rs.getInt("minimum_quantity"),
                        rs.getInt("maximum_quantity"),
                        rs.getInt("questid")));
                if (itemid == 0) {
                    doneMesos = true;
                }
            }
            if (!doneMesos) {
                addMeso(mons, ret);
            }

        } catch (SQLException e) {
            FileoutputUtil.outputFileError("logs/数据库异常.txt", e);
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ignore) {
                FileoutputUtil.outputFileError("logs/数据库异常.txt", ignore);
                return;
            }
        }
        drops.put(monsterId, ret);
    }

    public void addExtra() {
        final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        for (Entry<Integer, ArrayList<MonsterDropEntry>> e : drops.entrySet()) {
            for (int i = 0; i < e.getValue().size(); i++) {
                if (e.getValue().get(i).itemId != 0 && !ii.itemExists(e.getValue().get(i).itemId)) {
                    e.getValue().remove(i);
                }
            }
            final MapleMonsterStats mons = MapleLifeFactory.getMonsterStats(e.getKey());
            Integer item = ii.getItemIdByMob(e.getKey());
            if (item != null && item.intValue() > 0) {
                e.getValue().add(new MonsterDropEntry(item.intValue(), mons.isBoss() ? 1000000 : 10000, 1, 1, 0));
            }
            StructFamiliar f = ii.getFamiliarByMob(e.getKey().intValue());
            if (f != null) {
                e.getValue().add(new MonsterDropEntry(f.itemid, mons.isBoss() ? 10000 : 100, 1, 1, 0));
            }
        }
        for (Entry<Integer, Integer> i : ii.getMonsterBook().entrySet()) {
            if (!drops.containsKey(i.getKey())) {
                final MapleMonsterStats mons = MapleLifeFactory.getMonsterStats(i.getKey());
                ArrayList<MonsterDropEntry> e = new ArrayList<MonsterDropEntry>();
                e.add(new MonsterDropEntry(i.getValue().intValue(), mons.isBoss() ? 1000000 : 10000, 1, 1, 0));
                StructFamiliar f = ii.getFamiliarByMob(i.getKey().intValue());
                if (f != null) {
                    e.add(new MonsterDropEntry(f.itemid, mons.isBoss() ? 10000 : 100, 1, 1, 0));
                }
                addMeso(mons, e);

                drops.put(i.getKey(), e);
            }
        }
        for (StructFamiliar f : ii.getFamiliars().values()) {
            if (!drops.containsKey(f.mob)) {
                MapleMonsterStats mons = MapleLifeFactory.getMonsterStats(f.mob);
                ArrayList<MonsterDropEntry> e = new ArrayList<MonsterDropEntry>();
                e.add(new MonsterDropEntry(f.itemid, mons.isBoss() ? 10000 : 100, 1, 1, 0));
                addMeso(mons, e);
                drops.put(f.mob, e);
            }
        }
        if (GameConstants.GMS) { //kinda costly, i advise against !reloaddrops often
            for (Entry<Integer, ArrayList<MonsterDropEntry>> e : drops.entrySet()) { //yes, we're going through it twice
                if (e.getKey() != 9400408 && mobStringData.getChildByPath(String.valueOf(e.getKey())) != null) {
                    for (MapleData d : mobStringData.getChildByPath(e.getKey() + "/reward")) {
                        final int toAdd = MapleDataTool.getInt(d, 0);
                        if (toAdd > 0 && !contains(e.getValue(), toAdd) && ii.itemExists(toAdd)) {
                            e.getValue().add(new MonsterDropEntry(toAdd, chanceLogic(toAdd), 1, 1, 0));
                        }
                    }
                }
            }
        }
    }

    public void addMeso(MapleMonsterStats mons, ArrayList<MonsterDropEntry> ret) {
        final double divided = (mons.getLevel() < 100 ? (mons.getLevel() < 10 ? (double) mons.getLevel() : 10.0) : (mons.getLevel() / 10.0));
        final int max = mons.isBoss() && !mons.isPartyBonus() ? (mons.getLevel() * mons.getLevel()) : (mons.getLevel() * (int) Math.ceil(mons.getLevel() / divided));
        for (int i = 0; i < mons.dropsMeso(); i++) {
            ret.add(new MonsterDropEntry(0, mons.isBoss() && !mons.isPartyBonus() ? 1000000 : (mons.isPartyBonus() ? 100000 : 200000), (int) Math.floor(0.66 * max), max, 0));
        }
    }

    public void clearDrops() {
        drops.clear();
        globaldrops.clear();
        load();
        addExtra();
    }

    public boolean contains(ArrayList<MonsterDropEntry> e, int toAdd) {
        for (MonsterDropEntry f : e) {
            if (f.itemId == toAdd) {
                return true;
            }
        }
        return false;
    }

    public int chanceLogic(int itemId) { //not much logic in here. most of the drops should already be there anyway.
        if (GameConstants.getInventoryType(itemId) == MapleInventoryType.EQUIP) {
            return 50000; //with *10
        } else if (GameConstants.getInventoryType(itemId) == MapleInventoryType.SETUP || GameConstants.getInventoryType(itemId) == MapleInventoryType.CASH) {
            return 500;
        } else {
            switch (itemId / 10000) {
                case 204:
                case 207:
                case 233:
                case 229:
                    return 500;
                case 401:
                case 402:
                    return 5000;
                case 403:
                    return 5000; //lol
            }
            return 20000;
        }
    }

    public Map<Integer, String> getAllMonsters() {
        if (mobCache.isEmpty()) {
            final MapleDataProvider stringData = MapleDataProviderFactory.getDataProvider(new File((System.getProperty("wzpath") != null ? System.getProperty("wzpath") : "") + "wz/String.wz"));
            MapleData mobsData = stringData.getData("Mob.img");
            for (MapleData itemFolder : mobsData.getChildren()) {
                mobCache.put(Integer.parseInt(itemFolder.getName()), MapleDataTool.getString("name", itemFolder, "NO-NAME"));
            }
        }
        return mobCache;
    }

    public void UpdateDropChance(int chance, int dropperid, int itemid) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection(); PreparedStatement ps = con.prepareStatement("UPDATE drop_data SET chance = ? WHERE dropperid = ? AND itemid = ?")) {
            ps.setInt(1, chance);
            ps.setInt(2, dropperid);
            ps.setInt(3, itemid);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error UpdateDropChance" + ex);
            FileoutputUtil.outputFileError("logs/数据库异常.txt", ex);
        }
    }

    public void AddDropData(int chance, int dropperid, int itemid, int questid) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection(); PreparedStatement ps = con.prepareStatement("INSERT INTO drop_data SET chance = ? , dropperid = ? , itemid = ?, questid = ?")) {
            ps.setInt(1, chance);
            ps.setInt(2, dropperid);
            ps.setInt(3, itemid);
            ps.setInt(4, questid);
            //   System.out.println(chance+" "+dropperid+" "+itemid+" "+questid);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error AddDropData" + ex);
            FileoutputUtil.outputFileError("logs/数据库异常.txt", ex);
        }
    }

    public int getDropQuest(int monsterId) {
        int quest = 0;
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT questid FROM drop_data where dropperid = ?");
            ps.setInt(1, monsterId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                quest = rs.getInt("questid");
            }
        } catch (SQLException e) {
            System.out.println("Error getDropQuest" + e);
            FileoutputUtil.outputFileError("logs/数据库异常.txt", e);
        }
        return quest;
    }

    public int getDropChance(int monsterId) {
        int chance = 0;
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT chance FROM drop_data where dropperid = ?");
            ps.setInt(1, monsterId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                chance = rs.getInt("chance");

            }
        } catch (SQLException e) {
            System.out.println("Error getDropChance" + e);
            FileoutputUtil.outputFileError("logs/数据库异常.txt", e);
        }
        return chance;
    }

    public List<Integer> getMobByItem(final int itemId) {
        final List<Integer> mobs = new LinkedList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            ps = con.prepareStatement("SELECT * FROM drop_data WHERE itemid = ?");
            ps.setInt(1, itemId);
            rs = ps.executeQuery();
            int mobid;
            while (rs.next()) {
                mobid = rs.getInt("dropperid");
                if (!mobs.contains(mobid)) {
                    mobs.add(mobid);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error getMobByItem" + e);
            FileoutputUtil.outputFileError("logs/数据库异常.txt", e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ignore) {
                System.out.println("Error getMobByItem" + ignore);
                FileoutputUtil.outputFileError("logs/数据库异常.txt", ignore);
                return null;
            }
        }
        return mobs;
    }
}
