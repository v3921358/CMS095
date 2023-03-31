package client.inventory;

import constants.GameConstants;
import database.DBConPool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import server.MapleItemInformationProvider;
import tools.FileoutputUtil;
import tools.Pair;

public enum ItemLoader {
    INVENTORY("inventoryitems", "inventoryequipment", 0, "characterid"),
    STORAGE("inventoryitems", "inventoryequipment", 1, "accountid"),
    CASHSHOP("csitems", "csequipment", 2, "accountid"),
    HIRED_MERCHANT("hiredmerchitems", "hiredmerchequipment", 5, "packageid"),
    DUEY("dueyitems", "dueyequipment", 6, "packageid"),
    MTS("mtsitems", "mtsequipment", 8, "packageid"),
    MTS_TRANSFER("mtstransfer", "mtstransferequipment", 9, "characterid");
    private int value;
    private String table, table_equip, arg;

    private ItemLoader(String table, String table_equip, int value, String arg) {
        this.table = table;
        this.table_equip = table_equip;
        this.value = value;
        this.arg = arg;
    }

    public int getValue() {
        return value;
    }

    //does not need connection con to be auto commit
    public Map<Long, Pair<Item, MapleInventoryType>> loadItems(boolean login, int id) throws SQLException {
        Map<Long, Pair<Item, MapleInventoryType>> items = new LinkedHashMap<Long, Pair<Item, MapleInventoryType>>();
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM `");
        query.append(table);
        query.append("` LEFT JOIN `");
        query.append(table_equip);
        query.append("` USING(`inventoryitemid`) WHERE `type` = ?");
        query.append(" AND `");
        query.append(arg);
        query.append("` = ?");

        if (login) {
            query.append(" AND `inventorytype` = ");
            query.append(MapleInventoryType.EQUIPPED.getType());
        }
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps = con.prepareStatement(query.toString());
            ps.setInt(1, value);
            ps.setInt(2, id);
            ResultSet rs = ps.executeQuery();
            final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
            while (rs.next()) {
                if (!ii.itemExists(rs.getInt("itemid"))) { //EXPENSIVE
                    continue;
                }
                MapleInventoryType mit = MapleInventoryType.getByType(rs.getByte("inventorytype"));

                if (mit.equals(MapleInventoryType.EQUIP) || mit.equals(MapleInventoryType.EQUIPPED)) {
                    Equip equip = new Equip(rs.getInt("itemid"), rs.getShort("position"), rs.getInt("uniqueid"), rs.getShort("flag"));
                    if (!login && equip.getPosition() != -55) { //monsterbook
                        equip.setQuantity((short) 1);
                        equip.setInventoryId(rs.getLong("inventoryitemid"));
                        equip.setOwner(rs.getString("owner"));
                        equip.setExpiration(rs.getLong("expiredate"));
                        equip.setUpgradeSlots(rs.getByte("upgradeslots"));
                        equip.setLevel(rs.getByte("level"));
                        equip.setStr(rs.getShort("str"));
                        equip.setDex(rs.getShort("dex"));
                        equip.setInt(rs.getShort("int"));
                        equip.setLuk(rs.getShort("luk"));
                        equip.setHp(rs.getShort("hp"));
                        equip.setMp(rs.getShort("mp"));
                        equip.setWatk(rs.getShort("watk"));
                        equip.setMatk(rs.getShort("matk"));
                        equip.setWdef(rs.getShort("wdef"));
                        equip.setMdef(rs.getShort("mdef"));
                        equip.setAcc(rs.getShort("acc"));
                        equip.setAvoid(rs.getShort("avoid"));
                        equip.setHands(rs.getShort("hands"));
                        equip.setSpeed(rs.getShort("speed"));
                        equip.setJump(rs.getShort("jump"));
                        equip.setViciousHammer(rs.getByte("ViciousHammer"));
                        equip.setItemEXP(rs.getInt("itemEXP"));
                        equip.setGMLog(rs.getString("GM_Log"));
                        equip.setDurability(rs.getInt("durability"));
                        equip.setEnhance(rs.getByte("enhance"));
                        equip.setPotential1(rs.getInt("potential1"));
                        equip.setPotential2(rs.getInt("potential2"));
                        equip.setPotential3(rs.getInt("potential3"));
                        equip.setPotential4(rs.getInt("potential4"));
                        equip.setPotential5(rs.getInt("potential5"));
                        equip.setHpR(rs.getShort("hpR"));
                        equip.setMpR(rs.getShort("mpR"));
                        equip.setGiftFrom(rs.getString("sender"));
                        equip.setIncSkill(rs.getInt("incSkill"));
                        equip.setPVPDamage(rs.getShort("pvpDamage"));
                        equip.setCharmEXP(rs.getShort("charmEXP"));
                        if (equip.getCharmEXP() < 0) { //has not been initialized yet
                            equip.setCharmEXP(((Equip) ii.getEquipById(equip.getItemId())).getCharmEXP());
                        }
                        if (equip.getUniqueId() > -1) {
                            if (GameConstants.isEffectRing(rs.getInt("itemid"))) {
                                MapleRing ring = MapleRing.loadFromDb(equip.getUniqueId(), mit.equals(MapleInventoryType.EQUIPPED));
                                if (ring != null) {
                                    equip.setRing(ring);
                                }
                            } else if (equip.getItemId() / 10000 == 166) {
                                MapleAndroid and = MapleAndroid.loadFromDb(equip.getItemId(), equip.getUniqueId());
                                if (and != null) {
                                    equip.setAndroid(and);
                                }
                            }
                        }
                    }
                    items.put(rs.getLong("inventoryitemid"), new Pair<Item, MapleInventoryType>(equip.copy(), mit));
                } else {
                    Item item = new Item(rs.getInt("itemid"), rs.getShort("position"), rs.getShort("quantity"), rs.getShort("flag"), rs.getInt("uniqueid"));
                    item.setOwner(rs.getString("owner"));
                    item.setInventoryId(rs.getLong("inventoryitemid"));
                    item.setExpiration(rs.getLong("expiredate"));
                    item.setGMLog(rs.getString("GM_Log"));
                    item.setGiftFrom(rs.getString("sender"));
                    if (GameConstants.isPet(item.getItemId())) {
                        if (item.getUniqueId() > -1) {
                            MaplePet pet = MaplePet.loadFromDb(item.getItemId(), item.getUniqueId(), item.getPosition());
                            if (pet != null) {
                                item.setPet(pet);
                            }
                        } else {
                            //O_O hackish fix
                            item.setPet(MaplePet.createPet(item.getItemId(), MapleInventoryIdentifier.getInstance()));
                        }
                    }
                    items.put(rs.getLong("inventoryitemid"), new Pair<Item, MapleInventoryType>(item.copy(), mit));
                }
            }

            rs.close();
            ps.close();
        } catch (SQLException ex) {
            FileoutputUtil.outputFileError("logs/数据库异常.txt", ex);
        }
        return items;
    }

    public void saveItems(List<Pair<Item, MapleInventoryType>> items, int id) throws SQLException {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            saveItems(items, con, id);
        } catch (SQLException ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", ex);
        }
    }

    public void saveItems(List<Pair<Item, MapleInventoryType>> items, final Connection con, int id) throws SQLException {
        try {
            StringBuilder query = new StringBuilder();
            query.append("DELETE FROM `");
            query.append(table);
            query.append("` WHERE `type` = ? AND `");
            query.append(arg);
            query.append("` = ?");

            PreparedStatement ps = con.prepareStatement(query.toString());
            ps.setInt(1, value);
            ps.setInt(2, id);
            ps.executeUpdate();
            ps.close();
            if (items == null) {
                return;
            }
            StringBuilder query_2 = new StringBuilder("INSERT INTO `");
            query_2.append(table);
            query_2.append("` (");
            query_2.append(arg);
            query_2.append(", itemid, inventorytype, position, quantity, owner, GM_Log, uniqueid, expiredate, flag, `type`, sender) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps = con.prepareStatement(query_2.toString(), Statement.RETURN_GENERATED_KEYS);
            PreparedStatement pse = con.prepareStatement("INSERT INTO " + table_equip + " VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            final Iterator<Pair<Item, MapleInventoryType>> iter = items.iterator();
            Pair<Item, MapleInventoryType> pair;
            while (iter.hasNext()) {
                pair = iter.next();
                Item item = pair.getLeft();
                MapleInventoryType mit = pair.getRight();
                if (item.getPosition() == -55 && GameConstants.GMS) {
                    continue;
                }
                ps.setInt(1, id);
                ps.setInt(2, item.getItemId());
                ps.setInt(3, mit.getType());
                ps.setInt(4, item.getPosition());
                ps.setInt(5, item.getQuantity());
                ps.setString(6, item.getOwner());
                ps.setString(7, item.getGMLog());
                if (item.getPet() != null) { //expensif?
                    ps.setInt(8, Math.max(item.getUniqueId(), item.getPet().getUniqueId()));
                } else {
                    ps.setInt(8, item.getUniqueId());
                }
                ps.setLong(9, item.getExpiration());
                ps.setShort(10, item.getFlag());
                ps.setByte(11, (byte) value);
                ps.setString(12, item.getGiftFrom());

                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();

                if (!rs.next()) {
                    rs.close();
                    continue;
                }
                final long iid = rs.getLong(1);
                rs.close();

                item.setInventoryId(iid);
                if (mit.equals(MapleInventoryType.EQUIP) || mit.equals(MapleInventoryType.EQUIPPED)) {
                    Equip equip = (Equip) item;
                    pse.setLong(1, iid);
                    pse.setInt(2, equip.getUpgradeSlots());
                    pse.setInt(3, equip.getLevel());
                    pse.setInt(4, equip.getStr());
                    pse.setInt(5, equip.getDex());
                    pse.setInt(6, equip.getInt());
                    pse.setInt(7, equip.getLuk());
                    pse.setInt(8, equip.getHp());
                    pse.setInt(9, equip.getMp());
                    pse.setInt(10, equip.getWatk());
                    pse.setInt(11, equip.getMatk());
                    pse.setInt(12, equip.getWdef());
                    pse.setInt(13, equip.getMdef());
                    pse.setInt(14, equip.getAcc());
                    pse.setInt(15, equip.getAvoid());
                    pse.setInt(16, equip.getHands());
                    pse.setInt(17, equip.getSpeed());
                    pse.setInt(18, equip.getJump());
                    pse.setInt(19, equip.getViciousHammer());
                    pse.setInt(20, equip.getItemEXP());
                    pse.setInt(21, equip.getDurability());
                    pse.setByte(22, equip.getEnhance());
                    pse.setInt(23, equip.getPotential1());
                    pse.setInt(24, equip.getPotential2());
                    pse.setInt(25, equip.getPotential3());
                    pse.setInt(26, equip.getPotential4());
                    pse.setInt(27, equip.getPotential5());
                    pse.setInt(28, equip.getHpR());
                    pse.setInt(29, equip.getMpR());
                    pse.setInt(30, equip.getIncSkill());
                    pse.setShort(31, equip.getCharmEXP());
                    pse.setShort(32, equip.getPVPDamage());
                    pse.executeUpdate();
                }
            }
            pse.close();
            ps.close();
        } catch (SQLException ex) {
            System.out.println(ex);
            FileoutputUtil.outError("logs/数据库异常.txt", ex);
        }
    }
}
