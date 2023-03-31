package server;

import client.MapleCharacter;
import client.MapleTrait.MapleTraitType;
import client.inventory.Equip;
import client.inventory.EquipAdditions;
import client.inventory.Item;
import client.inventory.ItemFlag;
import client.inventory.MapleInventoryIdentifier;
import client.inventory.MapleInventoryType;
import client.inventory.MaplePet;
import constants.GameConstants;
import database.DBConPool;
import java.awt.Point;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import provider.MapleData;
import provider.MapleDataDirectoryEntry;
import provider.MapleDataEntry;
import provider.MapleDataFileEntry;
import provider.MapleDataProvider;
import provider.MapleDataProviderFactory;
import provider.MapleDataTool;
import provider.MapleDataType;
import server.StructSetItem.SetItem;
import tools.FileoutputUtil;
import tools.Pair;
import tools.Randomizer;
import tools.Triple;
import tools.packet.MaplePacketCreator;

public class MapleItemInformationProvider {

    private final static MapleItemInformationProvider instance = new MapleItemInformationProvider();
    protected final MapleDataProvider chrData = MapleDataProviderFactory.getDataProvider(new File((System.getProperty("wzpath") != null ? System.getProperty("wzpath") : "") + "wz/Character.wz"));
    protected final MapleDataProvider etcData = MapleDataProviderFactory.getDataProvider(new File((System.getProperty("wzpath") != null ? System.getProperty("wzpath") : "") + "wz/Etc.wz"));
    protected final MapleDataProvider itemData = MapleDataProviderFactory.getDataProvider(new File((System.getProperty("wzpath") != null ? System.getProperty("wzpath") : "") + "wz/Item.wz"));
    protected final Map<Integer, ItemInformation> dataCache = new HashMap<Integer, ItemInformation>();
    protected final Map<String, List<Triple<String, Point, Point>>> afterImage = new HashMap<String, List<Triple<String, Point, Point>>>();
    protected final Map<Integer, List<StructPotentialItem>> potentialCache = new HashMap<Integer, List<StructPotentialItem>>();
    protected final Map<Integer, MapleStatEffect> itemEffects = new HashMap<Integer, MapleStatEffect>();
    protected final Map<Integer, MapleStatEffect> itemEffectsEx = new HashMap<Integer, MapleStatEffect>();
    protected final Map<Integer, Integer> mobIds = new HashMap<Integer, Integer>();
    protected final Map<Integer, Pair<Integer, Integer>> potLife = new HashMap<Integer, Pair<Integer, Integer>>(); //itemid to lifeid, levels
    protected final Map<Integer, StructFamiliar> familiars = new HashMap<Integer, StructFamiliar>(); //by familiarID
    protected final Map<Integer, StructFamiliar> familiars_Item = new HashMap<Integer, StructFamiliar>(); //by cardID
    protected final Map<Integer, StructFamiliar> familiars_Mob = new HashMap<Integer, StructFamiliar>(); //by mobID
    protected final Map<Integer, Triple<List<Integer>, List<Integer>, List<Integer>>> androids = new HashMap<Integer, Triple<List<Integer>, List<Integer>, List<Integer>>>();
    protected final Map<Integer, Triple<Integer, List<Integer>, List<Integer>>> monsterBookSets = new HashMap<Integer, Triple<Integer, List<Integer>, List<Integer>>>();
    protected final Map<Short, StructSetItem> setItems = new HashMap<Short, StructSetItem>();
    protected Map<Integer, MapleInventoryType> inventoryTypeCache = new HashMap<>();
    protected final Map<Integer, Short> petFlagInfo = new HashMap();
    protected Map<Integer, Boolean> noCursedScroll = new HashMap();
    protected Map<Integer, Integer> forceUpgrade = new HashMap();
    protected Map<Integer, Integer> successRates = new HashMap();
    protected Map<Integer, Boolean> noNegativeScroll = new HashMap();
    protected final Map<Integer, Integer> petLifeInfo = new HashMap<>();
    protected Map<Integer, Integer> androidType = new HashMap<>();

    public void runEtc() {
        if (!setItems.isEmpty() || !potentialCache.isEmpty()) {
            return;
        }
        final MapleData setsData = etcData.getData("SetItemInfo.img");
        StructSetItem itemz;
        SetItem itez;
        for (MapleData dat : setsData) {
            itemz = new StructSetItem();
            itemz.setItemID = Short.parseShort(dat.getName());
            itemz.completeCount = (byte) MapleDataTool.getIntConvert("completeCount", dat, 0);
            for (MapleData level : dat.getChildByPath("ItemID")) {
                if (level.getType() != MapleDataType.INT) {
                    for (MapleData leve : level) {
                        if (!leve.getName().equals("representName") && !leve.getName().equals("typeName")) {
                            if (Short.parseShort(dat.getName()) != 478) {
                                itemz.itemIDs.add(MapleDataTool.getInt(leve));
                            }
                        }
                    }
                } else {
                    itemz.itemIDs.add(MapleDataTool.getInt(level));
                }
            }
            for (MapleData level : dat.getChildByPath("Effect")) {
                itez = new SetItem();
                itez.incPDD = MapleDataTool.getIntConvert("incPDD", level, 0);
                itez.incMDD = MapleDataTool.getIntConvert("incMDD", level, 0);
                itez.incSTR = MapleDataTool.getIntConvert("incSTR", level, 0);
                itez.incDEX = MapleDataTool.getIntConvert("incDEX", level, 0);
                itez.incINT = MapleDataTool.getIntConvert("incINT", level, 0);
                itez.incLUK = MapleDataTool.getIntConvert("incLUK", level, 0);
                itez.incACC = MapleDataTool.getIntConvert("incACC", level, 0);
                itez.incPAD = MapleDataTool.getIntConvert("incPAD", level, 0);
                itez.incMAD = MapleDataTool.getIntConvert("incMAD", level, 0);
                itez.incSpeed = MapleDataTool.getIntConvert("incSpeed", level, 0);
                itez.incMHP = MapleDataTool.getIntConvert("incMHP", level, 0);
                itez.incMMP = MapleDataTool.getIntConvert("incMMP", level, 0);
                itez.incMHPr = MapleDataTool.getIntConvert("incMHPr", level, 0);
                itez.incMMPr = MapleDataTool.getIntConvert("incMMPr", level, 0);
                itez.incAllStat = MapleDataTool.getIntConvert("incAllStat", level, 0);
                itez.option1 = MapleDataTool.getIntConvert("Option/1/option", level, 0);
                itez.option2 = MapleDataTool.getIntConvert("Option/2/option", level, 0);
                itez.option1Level = MapleDataTool.getIntConvert("Option/1/level", level, 0);
                itez.option2Level = MapleDataTool.getIntConvert("Option/2/level", level, 0);
                itemz.items.put(Integer.parseInt(level.getName()), itez);
            }
            setItems.put(itemz.setItemID, itemz);
        }
        final MapleData potsData = itemData.getData("ItemOption.img");
        StructPotentialItem item;
        List<StructPotentialItem> items;
        for (MapleData dat : potsData) {
            items = new LinkedList<StructPotentialItem>();
            for (MapleData level : dat.getChildByPath("level")) {
                item = new StructPotentialItem();
                item.optionType = MapleDataTool.getIntConvert("info/optionType", dat, 0);
                item.reqLevel = MapleDataTool.getIntConvert("info/reqLevel", dat, 0);
                item.face = MapleDataTool.getString("face", level, "");
                item.boss = MapleDataTool.getIntConvert("boss", level, 0) > 0;
                item.potentialID = Integer.parseInt(dat.getName());
                item.attackType = (short) MapleDataTool.getIntConvert("attackType", level, 0);
                item.incMHP = (short) MapleDataTool.getIntConvert("incMHP", level, 0);
                item.incMMP = (short) MapleDataTool.getIntConvert("incMMP", level, 0);
                item.incSTR = (byte) MapleDataTool.getIntConvert("incSTR", level, 0);
                item.incDEX = (byte) MapleDataTool.getIntConvert("incDEX", level, 0);
                item.incINT = (byte) MapleDataTool.getIntConvert("incINT", level, 0);
                item.incLUK = (byte) MapleDataTool.getIntConvert("incLUK", level, 0);
                item.incACC = (byte) MapleDataTool.getIntConvert("incACC", level, 0);
                item.incEVA = (byte) MapleDataTool.getIntConvert("incEVA", level, 0);
                item.incSpeed = (byte) MapleDataTool.getIntConvert("incSpeed", level, 0);
                item.incJump = (byte) MapleDataTool.getIntConvert("incJump", level, 0);
                item.incPAD = (byte) MapleDataTool.getIntConvert("incPAD", level, 0);
                item.incMAD = (byte) MapleDataTool.getIntConvert("incMAD", level, 0);
                item.incPDD = (byte) MapleDataTool.getIntConvert("incPDD", level, 0);
                item.incMDD = (byte) MapleDataTool.getIntConvert("incMDD", level, 0);
                item.prop = (byte) MapleDataTool.getIntConvert("prop", level, 0);
                item.time = (byte) MapleDataTool.getIntConvert("time", level, 0);
                item.incSTRr = (byte) MapleDataTool.getIntConvert("incSTRr", level, 0);
                item.incDEXr = (byte) MapleDataTool.getIntConvert("incDEXr", level, 0);
                item.incINTr = (byte) MapleDataTool.getIntConvert("incINTr", level, 0);
                item.incLUKr = (byte) MapleDataTool.getIntConvert("incLUKr", level, 0);
                item.incMHPr = (byte) MapleDataTool.getIntConvert("incMHPr", level, 0);
                item.incMMPr = (byte) MapleDataTool.getIntConvert("incMMPr", level, 0);
                item.incACCr = (byte) MapleDataTool.getIntConvert("incACCr", level, 0);
                item.incEVAr = (byte) MapleDataTool.getIntConvert("incEVAr", level, 0);
                item.incPADr = (byte) MapleDataTool.getIntConvert("incPADr", level, 0);
                item.incMADr = (byte) MapleDataTool.getIntConvert("incMADr", level, 0);
                item.incPDDr = (byte) MapleDataTool.getIntConvert("incPDDr", level, 0);
                item.incMDDr = (byte) MapleDataTool.getIntConvert("incMDDr", level, 0);
                item.incCr = (byte) MapleDataTool.getIntConvert("incCr", level, 0);
                item.incDAMr = (byte) MapleDataTool.getIntConvert("incDAMr", level, 0);
                item.RecoveryHP = (byte) MapleDataTool.getIntConvert("RecoveryHP", level, 0);
                item.RecoveryMP = (byte) MapleDataTool.getIntConvert("RecoveryMP", level, 0);
                item.HP = (byte) MapleDataTool.getIntConvert("HP", level, 0);
                item.MP = (byte) MapleDataTool.getIntConvert("MP", level, 0);
                item.level = (byte) MapleDataTool.getIntConvert("level", level, 0);
                item.ignoreTargetDEF = (byte) MapleDataTool.getIntConvert("ignoreTargetDEF", level, 0);
                item.ignoreDAM = (byte) MapleDataTool.getIntConvert("ignoreDAM", level, 0);
                item.DAMreflect = (byte) MapleDataTool.getIntConvert("DAMreflect", level, 0);
                item.mpconReduce = (byte) MapleDataTool.getIntConvert("mpconReduce", level, 0);
                item.mpRestore = (byte) MapleDataTool.getIntConvert("mpRestore", level, 0);
                item.incMesoProp = (byte) MapleDataTool.getIntConvert("incMesoProp", level, 0);
                item.incRewardProp = (byte) MapleDataTool.getIntConvert("incRewardProp", level, 0);
                item.incAllskill = (byte) MapleDataTool.getIntConvert("incAllskill", level, 0);
                item.ignoreDAMr = (byte) MapleDataTool.getIntConvert("ignoreDAMr", level, 0);
                item.RecoveryUP = (byte) MapleDataTool.getIntConvert("RecoveryUP", level, 0);
                switch (item.potentialID) {
                    case 31001:
                    case 31002:
                    case 31003:
                    case 31004:
                        item.skillID = (short) (item.potentialID - 23001);
                        break;
                    case 41005:
                    case 41006:
                    case 41007:
                        item.skillID = (short) (item.potentialID - 33001);
                        break;
                    case 60001:
                    case 60002:
                    case 60003:
                        return;
                    default:
                        item.skillID = 0;
                        break;
                }
                items.add(item);
            }
            potentialCache.put(Integer.parseInt(dat.getName()), items);
        }

        final MapleDataDirectoryEntry e = (MapleDataDirectoryEntry) etcData.getRoot().getEntry("Android");
        for (MapleDataEntry d : e.getFiles()) {
            final MapleData iz = etcData.getData("Android/" + d.getName());
            final List<Integer> skin = new ArrayList<>(), hair = new ArrayList<>(), face = new ArrayList<>();
            for (MapleData ds : iz.getChildByPath("costume/skin")) {
                skin.add(MapleDataTool.getInt(ds, 2000));
            }
            for (MapleData ds : iz.getChildByPath("costume/hair")) {
                hair.add(MapleDataTool.getInt(ds, 30000));
            }
            for (MapleData ds : iz.getChildByPath("costume/face")) {
                face.add(MapleDataTool.getInt(ds, 20000));
            }
            androids.put(Integer.parseInt(d.getName().substring(0, 4)), new Triple<>(skin, hair, face));
        }
        final MapleData lifesData = etcData.getData("ItemPotLifeInfo.img");
        for (MapleData d : lifesData) {
            if (d.getChildByPath("info") != null && MapleDataTool.getInt("type", d.getChildByPath("info"), 0) == 1) {
                potLife.put(MapleDataTool.getInt("counsumeItem", d.getChildByPath("info"), 0), new Pair<Integer, Integer>(Integer.parseInt(d.getName()), d.getChildByPath("level").getChildren().size()));
            }
        }
        List<Triple<String, Point, Point>> thePointK = new ArrayList<Triple<String, Point, Point>>();
        List<Triple<String, Point, Point>> thePointA = new ArrayList<Triple<String, Point, Point>>();

        final MapleDataDirectoryEntry a = (MapleDataDirectoryEntry) chrData.getRoot().getEntry("Afterimage");
        for (MapleDataEntry b : a.getFiles()) {
            final MapleData iz = chrData.getData("Afterimage/" + b.getName());
            List<Triple<String, Point, Point>> thePoint = new ArrayList<Triple<String, Point, Point>>();
            Map<String, Pair<Point, Point>> dummy = new HashMap<String, Pair<Point, Point>>();
            for (MapleData i : iz) {
                for (MapleData xD : i) {
                    if (xD.getName().contains("prone") || xD.getName().contains("double") || xD.getName().contains("triple")) {
                        continue;
                    }
                    if ((b.getName().contains("bow") || b.getName().contains("Bow")) && !xD.getName().contains("shoot")) {
                        continue;
                    }
                    if ((b.getName().contains("gun") || b.getName().contains("cannon")) && !xD.getName().contains("shot")) {
                        continue;
                    }
                    if (dummy.containsKey(xD.getName())) {
                        if (xD.getChildByPath("lt") != null) {
                            Point lt = (Point) xD.getChildByPath("lt").getData();
                            Point ourLt = dummy.get(xD.getName()).left;
                            if (lt.x < ourLt.x) {
                                ourLt.x = lt.x;
                            }
                            if (lt.y < ourLt.y) {
                                ourLt.y = lt.y;
                            }
                        }
                        if (xD.getChildByPath("rb") != null) {
                            Point rb = (Point) xD.getChildByPath("rb").getData();
                            Point ourRb = dummy.get(xD.getName()).right;
                            if (rb.x > ourRb.x) {
                                ourRb.x = rb.x;
                            }
                            if (rb.y > ourRb.y) {
                                ourRb.y = rb.y;
                            }
                        }
                    } else {
                        Point lt = null, rb = null;
                        if (xD.getChildByPath("lt") != null) {
                            lt = (Point) xD.getChildByPath("lt").getData();
                        }
                        if (xD.getChildByPath("rb") != null) {
                            rb = (Point) xD.getChildByPath("rb").getData();
                        }
                        dummy.put(xD.getName(), new Pair<Point, Point>(lt, rb));
                    }
                }
            }
            for (Entry<String, Pair<Point, Point>> ez : dummy.entrySet()) {
                if (ez.getKey().length() > 2 && ez.getKey().substring(ez.getKey().length() - 2, ez.getKey().length() - 1).equals("D")) { //D = double weapon
                    thePointK.add(new Triple<String, Point, Point>(ez.getKey(), ez.getValue().left, ez.getValue().right));
                } else if (ez.getKey().contains("PoleArm")) { //D = double weapon
                    thePointA.add(new Triple<String, Point, Point>(ez.getKey(), ez.getValue().left, ez.getValue().right));
                } else {
                    thePoint.add(new Triple<String, Point, Point>(ez.getKey(), ez.getValue().left, ez.getValue().right));
                }
            }
            afterImage.put(b.getName().substring(0, b.getName().length() - 4), thePoint);
        }
        afterImage.put("katara", thePointK); //hackish
        afterImage.put("aran", thePointA); //hackish
    }

    public void runItems() {
        if (GameConstants.GMS) { //these must be loaded before items..
            final MapleData fData = etcData.getData("FamiliarInfo.img");
            for (MapleData d : fData) {
                StructFamiliar f = new StructFamiliar();
                f.grade = 0;
                f.mob = MapleDataTool.getInt("mob", d, 0);
                f.passive = MapleDataTool.getInt("passive", d, 0);
                f.itemid = MapleDataTool.getInt("consume", d, 0);
                f.familiar = Integer.parseInt(d.getName());
                familiars.put(f.familiar, f);
                familiars_Item.put(f.itemid, f);
                familiars_Mob.put(f.mob, f);
            }
            final MapleDataDirectoryEntry e = (MapleDataDirectoryEntry) chrData.getRoot().getEntry("Familiar");
            for (MapleDataEntry d : e.getFiles()) {
                final int id = Integer.parseInt(d.getName().substring(0, d.getName().length() - 4));
                if (familiars.containsKey(id)) {
                    familiars.get(id).grade = (byte) MapleDataTool.getInt("grade", chrData.getData("Familiar/" + d.getName()).getChildByPath("info"), 0);
                }
            }

            final MapleData mSetsData = etcData.getData("MonsterBookSet.img");
            for (MapleData d : mSetsData.getChildByPath("setList")) {
                if (MapleDataTool.getInt("deactivated", d, 0) > 0) {
                    continue;
                }
                final List<Integer> set = new ArrayList<Integer>(), potential = new ArrayList<Integer>(3);
                for (MapleData ds : d.getChildByPath("stats/potential")) {
                    if (ds.getType() != MapleDataType.STRING && MapleDataTool.getInt(ds, 0) > 0) {
                        potential.add(MapleDataTool.getInt(ds, 0));
                        if (potential.size() >= 3) {
                            break;
                        }
                    }
                }
                for (MapleData ds : d.getChildByPath("cardList")) {
                    set.add(MapleDataTool.getInt(ds, 0));
                }
                monsterBookSets.put(Integer.parseInt(d.getName()), new Triple<Integer, List<Integer>, List<Integer>>(MapleDataTool.getInt("setScore", d, 0), set, potential));
            }
        }

        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {

            PreparedStatement ps = con.prepareStatement("SELECT * FROM wz_itemdata");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                initItemInformation(rs);
            }
            rs.close();
            ps.close();

            ps = con.prepareStatement("SELECT * FROM wz_itemequipdata ORDER BY itemid");
            rs = ps.executeQuery();
            while (rs.next()) {
                initItemEquipData(rs);
            }
            rs.close();
            ps.close();

            ps = con.prepareStatement("SELECT * FROM wz_itemadddata ORDER BY itemid");
            rs = ps.executeQuery();
            while (rs.next()) {
                initItemAddData(rs);
            }
            rs.close();
            ps.close();

            ps = con.prepareStatement("SELECT * FROM wz_itemrewarddata ORDER BY itemid");
            rs = ps.executeQuery();
            while (rs.next()) {
                initItemRewardData(rs);
            }
            rs.close();
            ps.close();

            for (Entry<Integer, ItemInformation> entry : dataCache.entrySet()) {
                if (GameConstants.getInventoryType(entry.getKey()) == MapleInventoryType.EQUIP) {
                    finalizeEquipData(entry.getValue());
                }
            }

        } catch (SQLException ex) {
            FileoutputUtil.outputFileError("logs/数据库异常.txt", ex);
            ex.printStackTrace();
        }
        //System.out.println(dataCache.size() + " items loaded.");
    }

    public final List<StructPotentialItem> getPotentialInfo(final int potId) {
        return potentialCache.get(potId);
    }

    public final Map<Integer, List<StructPotentialItem>> getAllPotentialInfo() {
        return potentialCache;
    }

    public final Collection<Integer> getMonsterBookList() {
        return mobIds.values();
    }

    public final Map<Integer, Integer> getMonsterBook() {
        return mobIds;
    }

    public final Pair<Integer, Integer> getPot(int f) {
        return potLife.get(f);
    }

    public final StructFamiliar getFamiliar(int f) {
        return familiars.get(f);
    }

    public final Map<Integer, StructFamiliar> getFamiliars() {
        return familiars;
    }

    public final StructFamiliar getFamiliarByItem(int f) {
        return familiars_Item.get(f);
    }

    public final StructFamiliar getFamiliarByMob(int f) {
        return familiars_Mob.get(f);
    }

    public static final MapleItemInformationProvider getInstance() {
        return instance;
    }

    public final Collection<ItemInformation> getAllItems() {
        return dataCache.values();
    }

    public final Triple<List<Integer>, List<Integer>, List<Integer>> getAndroidInfo(int i) {
        return androids.get(i);
    }

    public final Triple<Integer, List<Integer>, List<Integer>> getMonsterBookInfo(int i) {
        return monsterBookSets.get(i);
    }

    public final Map<Integer, Triple<Integer, List<Integer>, List<Integer>>> getAllMonsterBookInfo() {
        return monsterBookSets;
    }

    protected final MapleData getItemData(final int itemId) {
        MapleData ret = null;
        final String idStr = "0" + String.valueOf(itemId);
        MapleDataDirectoryEntry root = itemData.getRoot();
        for (final MapleDataDirectoryEntry topDir : root.getSubdirectories()) {
            // we should have .img files here beginning with the first 4 IID
            for (final MapleDataFileEntry iFile : topDir.getFiles()) {
                if (iFile.getName().equals(idStr.substring(0, 4) + ".img")) {
                    ret = itemData.getData(topDir.getName() + "/" + iFile.getName());
                    if (ret == null) {
                        return null;
                    }
                    ret = ret.getChildByPath(idStr);
                    return ret;
                } else if (iFile.getName().equals(idStr.substring(1) + ".img")) {
                    ret = itemData.getData(topDir.getName() + "/" + iFile.getName());
                    return ret;
                }
            }
        }
        return ret;
    }

    public Integer getItemIdByMob(int mobId) {
        return mobIds.get(mobId);
    }

    public Integer getSetId(int itemId) {
        final ItemInformation i = getItemInformation(itemId);
        if (i == null) {
            return null;
        }
        return Integer.valueOf(i.cardSet);
    }

    /**
     * returns the maximum of items in one slot
     */
    public final short getSlotMax(final int itemId) {
        final ItemInformation i = getItemInformation(itemId);
        if (i == null) {
            return 0;
        }
        return i.slotMax;
    }

    public final int getWholePrice(final int itemId) {
        final ItemInformation i = getItemInformation(itemId);
        if (i == null) {
            return 0;
        }
        return i.wholePrice;
    }

    public final double getPrice(final int itemId) {
        final ItemInformation i = getItemInformation(itemId);
        if (i == null) {
            return -1.0;
        }
        return i.price;
    }

    protected int rand(int min, int max) {
        return Math.abs((int) Randomizer.rand(min, max));
    }

    public Equip levelUpEquip(Equip equip, Map<String, Integer> sta) {
        Equip nEquip = (Equip) equip.copy();
        //is this all the stats?
        try {
            for (Entry<String, Integer> stat : sta.entrySet()) {
                if (stat.getKey().equals("STRMin")) {
                    nEquip.setStr((short) (nEquip.getStr() + rand(stat.getValue().intValue(), sta.get("STRMax").intValue())));
                } else if (stat.getKey().equals("DEXMin")) {
                    nEquip.setDex((short) (nEquip.getDex() + rand(stat.getValue().intValue(), sta.get("DEXMax").intValue())));
                } else if (stat.getKey().equals("INTMin")) {
                    nEquip.setInt((short) (nEquip.getInt() + rand(stat.getValue().intValue(), sta.get("INTMax").intValue())));
                } else if (stat.getKey().equals("LUKMin")) {
                    nEquip.setLuk((short) (nEquip.getLuk() + rand(stat.getValue().intValue(), sta.get("LUKMax").intValue())));
                } else if (stat.getKey().equals("PADMin")) {
                    nEquip.setWatk((short) (nEquip.getWatk() + rand(stat.getValue().intValue(), sta.get("PADMax").intValue())));
                } else if (stat.getKey().equals("PDDMin")) {
                    nEquip.setWdef((short) (nEquip.getWdef() + rand(stat.getValue().intValue(), sta.get("PDDMax").intValue())));
                } else if (stat.getKey().equals("MADMin")) {
                    nEquip.setMatk((short) (nEquip.getMatk() + rand(stat.getValue().intValue(), sta.get("MADMax").intValue())));
                } else if (stat.getKey().equals("MDDMin")) {
                    nEquip.setMdef((short) (nEquip.getMdef() + rand(stat.getValue().intValue(), sta.get("MDDMax").intValue())));
                } else if (stat.getKey().equals("ACCMin")) {
                    nEquip.setAcc((short) (nEquip.getAcc() + rand(stat.getValue().intValue(), sta.get("ACCMax").intValue())));
                } else if (stat.getKey().equals("EVAMin")) {
                    nEquip.setAvoid((short) (nEquip.getAvoid() + rand(stat.getValue().intValue(), sta.get("EVAMax").intValue())));
                } else if (stat.getKey().equals("SpeedMin")) {
                    nEquip.setSpeed((short) (nEquip.getSpeed() + rand(stat.getValue().intValue(), sta.get("SpeedMax").intValue())));
                } else if (stat.getKey().equals("JumpMin")) {
                    nEquip.setJump((short) (nEquip.getJump() + rand(stat.getValue().intValue(), sta.get("JumpMax").intValue())));
                } else if (stat.getKey().equals("MHPMin")) {
                    nEquip.setHp((short) (nEquip.getHp() + rand(stat.getValue().intValue(), sta.get("MHPMax").intValue())));
                } else if (stat.getKey().equals("MMPMin")) {
                    nEquip.setMp((short) (nEquip.getMp() + rand(stat.getValue().intValue(), sta.get("MMPMax").intValue())));
                } else if (stat.getKey().equals("MaxHPMin")) {
                    nEquip.setHp((short) (nEquip.getHp() + rand(stat.getValue().intValue(), sta.get("MaxHPMax").intValue())));
                } else if (stat.getKey().equals("MaxMPMin")) {
                    nEquip.setMp((short) (nEquip.getMp() + rand(stat.getValue().intValue(), sta.get("MaxMPMax").intValue())));
                }
            }
        } catch (NullPointerException e) {
            //catch npe because obviously the wz have some error XD
            e.printStackTrace();
        }
        return nEquip;
    }

    public final EnumMap<EquipAdditions, Pair<Integer, Integer>> getEquipAdditions(final int itemId) {
        final ItemInformation i = getItemInformation(itemId);
        if (i == null) {
            return null;
        }
        return i.equipAdditions;
    }

    public final Map<Integer, Map<String, Integer>> getEquipIncrements(final int itemId) {
        final ItemInformation i = getItemInformation(itemId);
        if (i == null) {
            return null;
        }
        return i.equipIncs;
    }

    public final List<Integer> getEquipSkills(final int itemId) {
        final ItemInformation i = getItemInformation(itemId);
        if (i == null) {
            return null;
        }
        return i.incSkill;
    }

    public final Map<String, Integer> getEquipStats(final int itemId) {
        final ItemInformation i = getItemInformation(itemId);
        if (i == null) {
            return null;
        }
        return i.equipStats;
    }

    public final boolean canEquip(final Map<String, Integer> stats, final int itemid, final int level, final int job, final int fame, final int str, final int dex, final int luk, final int int_, final int supremacy) {
        if ((level + supremacy) >= (stats.containsKey("reqLevel") ? stats.get("reqLevel") : 0) && str >= (stats.containsKey("reqSTR") ? stats.get("reqSTR") : 0) && dex >= (stats.containsKey("reqDEX") ? stats.get("reqDEX") : 0) && luk >= (stats.containsKey("reqLUK") ? stats.get("reqLUK") : 0) && int_ >= (stats.containsKey("reqINT") ? stats.get("reqINT") : 0)) {
            final Integer fameReq = stats.get("reqPOP");
            if (fameReq != null && fame < fameReq) {
                return false;
            }
            return true;
        }
        return false;
    }

    public final int getReqLevel(final int itemId) {
        if (getEquipStats(itemId) == null || !getEquipStats(itemId).containsKey("reqLevel")) {
            return 0;
        }
        return getEquipStats(itemId).get("reqLevel");
    }

    public final int getSlots(final int itemId) {
        if (getEquipStats(itemId) == null || !getEquipStats(itemId).containsKey("tuc")) {
            return 0;
        }
        return getEquipStats(itemId).get("tuc");
    }

    public final Integer getSetItemID(final int itemId) {
        if (getEquipStats(itemId) == null || !getEquipStats(itemId).containsKey("setItemID")) {
            return 0;
        }
        return getEquipStats(itemId).get("setItemID");
    }

    public final StructSetItem getSetItem(final int setItemId) {
        return setItems.get((short) setItemId);
    }

    public final List<Integer> getScrollReqs(final int itemId) {
        final ItemInformation i = getItemInformation(itemId);
        if (i == null) {
            return null;
        }
        return i.scrollReqs;
    }

    public Item scrollEnhance(Item equip, Item scroll, MapleCharacter chr, int jl) {
        if (equip.getType() != 1) {
            return equip;
        }
        Equip nEquip = (Equip) equip;
        if (nEquip.getEnhance() >= GameConstants.getEnchantSstarts(getReqLevel(nEquip.getItemId()), isSuperiorEquip(nEquip.getItemId()))) {
            return equip;
        }
        int scrollId = scroll.getItemId();
        Map scrollStats = getEquipStats(scrollId);
        boolean noCursed = isNoCursedScroll(scrollId);
        int scrollForceUpgrade = getForceUpgrade(scrollId);
        int succ = scrollStats == null || !scrollStats.containsKey("success") ? 0 : (Integer) scrollStats.get("success");
        int curse = noCursed ? 0 : scrollStats == null || !scrollStats.containsKey("cursed") ? 100 : ((Integer) scrollStats.get("cursed"));
        int craft = chr.getTrait(MapleTraitType.craft).getLevel() / 10;
        if ((scrollForceUpgrade == 1) && (succ == 0)) {
            succ = Math.max(((scroll.getItemId() == 2049301) || (scroll.getItemId() == 2049307) ? 80 : 100) - nEquip.getEnhance() * 10, 5);
        }
        int success = succ + succ * craft / 100 + jl;
        succ = scroll.getItemId() == 2049302 ? 100 : succ;
        success = scroll.getItemId() == 2049302 ? 100 : success;
        curse = scroll.getItemId() == 2049302 ? 0 : curse;
        if (chr.isAdmin()) {
            chr.dropMessage(-5, "装备強化卷軸 - 默認几率: " + succ + "% 傾向加成: " + craft + "% NPC加成: " + jl + "% 最終几率: " + success + "% 失敗消失几率: " + curse + "%" + " 卷軸是否失敗不消失装备: " + noCursed);
        }

        if (nEquip.getEnhance() >= 1 && scrollForceUpgrade > 1) {
            return equip;
        }

        if (Randomizer.nextInt(100) > success) {
            return Randomizer.nextInt(99) < curse ? null : nEquip;
        }
        int mixStats = isSuperiorEquip(nEquip.getItemId()) ? 3 : 0;
        int maxStats = isSuperiorEquip(nEquip.getItemId()) ? 8 : 5;
        for (int i = 0; i < scrollForceUpgrade; i++) {
            if (nEquip.getStr() > 0 || Randomizer.nextInt(50) == 1) {
                nEquip.setStr((short) (nEquip.getStr() + Randomizer.rand(mixStats, maxStats)));
            }
            if (nEquip.getDex() > 0 || Randomizer.nextInt(50) == 1) {
                nEquip.setDex((short) (nEquip.getDex() + Randomizer.rand(mixStats, maxStats)));
            }
            if (nEquip.getInt() > 0 || Randomizer.nextInt(50) == 1) {
                nEquip.setInt((short) (nEquip.getInt() + Randomizer.rand(mixStats, maxStats)));
            }
            if (nEquip.getLuk() > 0 || Randomizer.nextInt(50) == 1) {
                nEquip.setLuk((short) (nEquip.getLuk() + Randomizer.rand(mixStats, maxStats)));
            }
            if (nEquip.getWatk() > 0 && (GameConstants.isWeapon(nEquip.getItemId()))) {
                if (nEquip.getWatk() < 150) {
                    nEquip.setWatk((short) (nEquip.getWatk() + 3));
                } else if (nEquip.getWatk() < 200) {
                    nEquip.setWatk((short) (nEquip.getWatk() + 4));
                } else if (nEquip.getWatk() < 250) {
                    nEquip.setWatk((short) (nEquip.getWatk() + 5));
                } else {
                    nEquip.setWatk((short) (nEquip.getWatk() + 5 + (Randomizer.nextBoolean() ? 1 : 0)));
                }
            }

            if (nEquip.getMatk() > 0 && GameConstants.isWeapon(nEquip.getItemId())) {
                if (nEquip.getMatk() < 50) {
                    nEquip.setMatk((short) (nEquip.getMatk() + 1));
                } else if (nEquip.getMatk() < 100) {
                    nEquip.setMatk((short) (nEquip.getMatk() + 2));
                } else if (nEquip.getMatk() < 150) {
                    nEquip.setMatk((short) (nEquip.getMatk() + 3));
                } else if (nEquip.getMatk() < 200) {
                    nEquip.setMatk((short) (nEquip.getMatk() + 4));
                } else if (nEquip.getMatk() < 250) {
                    nEquip.setMatk((short) (nEquip.getMatk() + 5));
                } else {
                    nEquip.setMatk((short) (nEquip.getMatk() + 5 + (Randomizer.nextBoolean() ? 1 : 0)));
                }
            }
            if (nEquip.getWdef() > 0 || Randomizer.nextInt(40) == 1) {
                nEquip.setWdef((short) (nEquip.getWdef() + Randomizer.nextInt(5)));
            }
            if (nEquip.getMdef() > 0 || Randomizer.nextInt(40) == 1) {
                nEquip.setMdef((short) (nEquip.getMdef() + Randomizer.nextInt(5)));
            }
            if (nEquip.getAcc() > 0 || Randomizer.nextInt(20) == 1) {
                nEquip.setAcc((short) (nEquip.getAcc() + Randomizer.nextInt(5)));
            }
            if (nEquip.getAvoid() > 0 || Randomizer.nextInt(20) == 1) {
                nEquip.setAvoid((short) (nEquip.getAvoid() + Randomizer.nextInt(5)));
            }
            if (nEquip.getSpeed() > 0 || Randomizer.nextInt(10) == 1) {
                nEquip.setSpeed((short) (nEquip.getSpeed() + Randomizer.nextInt(5)));
            }
            if (nEquip.getJump() > 0 || Randomizer.nextInt(10) == 1) {
                nEquip.setJump((short) (nEquip.getJump() + Randomizer.nextInt(5)));
            }
            if (nEquip.getHp() > 0 || Randomizer.nextInt(5) == 1) {
                nEquip.setHp((short) (nEquip.getHp() + Randomizer.rand(mixStats, maxStats)));
            }
            if (nEquip.getMp() > 0 || Randomizer.nextInt(5) == 1) {
                nEquip.setMp((short) (nEquip.getMp() + Randomizer.rand(mixStats, maxStats)));
            }
            nEquip.setEnhance((byte) (nEquip.getEnhance() + 1));
        }
        return nEquip;
    }

    public Item scrollPotential(Item equip, Item scroll, MapleCharacter chr) {
        if (equip.getType() != 1) {
            return equip;
        }
        Equip nEquip = (Equip) equip;
        int scrollId = scroll.getItemId();
        Map scrollStats = getEquipStats(scrollId);
        boolean noCursed = isNoCursedScroll(scrollId);
        int success = 0;
        int curse = 100;
        String scrollType;
        if (scrollId / 100 == 20494) {
            scrollType = "普通";
            curse = noCursed ? 0 : 100;
            switch (scrollId) {//普通卷轴成功率判断
                case 2049402://超級潜在能力賦予卷軸
                case 2049404://[6週年]潜在能力賦予卷軸
                case 2049405://真. 枫叶之心項鍊專用潜在能力卷軸
                case 2049406://特殊賦予潜在能力卷軸
                case 2049414://紫色灵魂戒指專用潜在能力賦予卷軸
                case 2049415://藍色灵魂戒指專用潜在能力賦予卷軸
                case 2049417://特殊潜在能力賦予 卷軸
                case 2049419://特別潜在能力賦予捲軸
                case 2049423://恰吉面具潜能賦予卷軸
                case 2049413:
                    success = 100;
                    break;
                case 2049400://高級潜在能力賦予卷軸
                    success = 90;
                    break;
                case 2049421://心之項鍊潜在能力賦予卷軸
                case 2049424://賦予卡爾頓的鬍子潜在能力卷軸
                    success = 80;
                    break;
                case 2049401://潜在能力賦予卷軸
                case 2049408://潜在能力賦予卷軸
                case 2049416://潜在能力賦予卷軸
                case 2049412:
                    success = 70;
                    break;
                case 2049407:
                    success = 100;
                    break;
                default:
                    if (scroll.getItemId() >= 2049427 && scroll.getItemId() <= 2049446) {//賞金獵人
                        success = 100;
                    }
            }
            switch (scrollId) {//专用卷轴失败不爆物判断
                case 2049404://[6週年]潜在能力賦予卷軸
                case 2049405://真. 枫叶之心項鍊專用潜在能力卷軸
                case 2049414://紫色灵魂戒指專用潜在能力賦予卷軸
                case 2049415://藍色灵魂戒指專用潜在能力賦予卷軸
                case 2049421://心之項鍊潜在能力賦予卷軸
                case 2049423://恰吉面具潜能賦予卷軸
                case 2049424://賦予卡爾頓的鬍子潜在能力卷軸
                case 2049426://10週年武器專用潜在能力附加卷軸
                case 2049427://賞金獵人
                    scrollType = "專用";
                    curse = 0;
                default:
                    if (scroll.getItemId() >= 2049427 && scroll.getItemId() <= 2049446) {//賞金獵人
                        scrollType = "專用";
                        curse = 0;
                    }
            }
        } else if (scrollId / 100 == 20497) {
            success = scrollStats == null || !scrollStats.containsKey("success") ? 0 : (Integer) scrollStats.get("success");
            curse = noCursed ? 0 : scrollStats == null || !scrollStats.containsKey("cursed") ? 0 : (Integer) scrollStats.get("cursed");
            if ((scrollId >= 2049700 && scrollId < 2049750) || scrollId == 2049406) {
                scrollType = "特殊";
            } else if ((scrollId >= 2049750 && scrollId < 2049759)) {
                scrollType = "罕見";
            } else if (scrollId == 2049780 || scrollId == 2049782) {
                scrollType = "傳說";
            } else {
                success = 0;
                scrollType = "未知";
            }
        } else {
            scrollType = "未知";
        }

        if (chr.isAdmin()) {
            chr.dropMessage(-5, scrollType + "潜能附加捲軸 - 成功几率: " + success + "% 失敗消失几率: " + curse + "%" + " 卷軸是否失敗不消失装备: " + noCursed);
        }
        if (success <= 0) {
            chr.dropMessage(1, "卷軸道具: " + scroll.getItemId() + " - " + getName(scroll.getItemId()) + " 成功几率为: " + success + " 该卷軸可能還未修復。");
            chr.getClient().getSession().write(MaplePacketCreator.enableActions());
            chr.marriage();
            return nEquip;
        }

        if (nEquip.getState() != 0) {
            return nEquip;
        }
        if (Randomizer.nextInt(100) > success) {
            return Randomizer.nextInt(99) < curse ? null : nEquip;
        }
        if ((scrollId >= 2049700 && scrollId < 2049750) || (scrollId == 2049406)) {
            nEquip.resetPotentialA();
        } else if (scrollId >= 2049750 && scrollId < 2049759 || (scrollId == 2049407 || scrollId == 2049408 || scrollId == 2049412 || scrollId == 2049413)) {
            nEquip.resetPotentialS();
        } else if (scrollId == 2049419) {//附加3条潜能
            int rank = Randomizer.nextInt(100) < 10 ? (Randomizer.nextInt(100) < 5 ? -19 : -18) : -17;
            nEquip.setPotential1(rank);
            nEquip.setPotential2(rank);
            nEquip.setPotential3(rank);
        } else {
            nEquip.resetPotential();
        }
        return nEquip;
    }

    public Item scrollPotentialAdd(Item equip, Item scroll, MapleCharacter chr) {
        if (equip.getType() != 1) {
            return equip;
        }
        Equip nEquip = (Equip) equip;
        if (nEquip.getAddState() != 0) {
            return nEquip;
        }
        int scrollId = scroll.getItemId();
        int success = 0;
        switch (scrollId) {
            case 2048306://3行附加潜能捲軸
            case 2048307:
            case 2048315:
                success = 100;
                break;
            case 2048313:
                success = 80;
                break;
            case 2048305:
                success = 70;
                break;
            case 2048309:
            case 2048310:
            case 2048314:
                success = 60;
                break;
            case 2048308:
            case 2048311:
                success = 50;
                break;
            case 2048312:
                success = 1;
                break;
        }
        int curse = 0;
        switch (scrollId) {
            case 2048305:
            case 2048310:
                curse = 100;
                break;
            case 2048308:
            case 2048311:
                curse = 50;
                break;
        }
        if (success <= 0) {
            chr.dropMessage(1, "卷軸道具: " + scroll.getItemId() + " - " + getName(scroll.getItemId()) + " 成功几率为: " + success + " 该卷軸可能還未修復。");
            chr.getClient().getSession().write(MaplePacketCreator.enableActions());
            chr.marriage();
            return nEquip;
        }
        if (chr.isAdmin()) {
            chr.dropMessage(-5, "附加潜能卷軸 - 成功几率: " + success + "% 失敗消失几率: " + curse + "%" + " 卷軸是否失敗不消失装备: " + (curse <= 0));
        }
        if (Randomizer.nextInt(100) <= success) {
            int rank = Randomizer.nextInt(100) < 10 ? (Randomizer.nextInt(100) < 5 ? -19 : -18) : -17;
            nEquip.setPotential4(rank);
            if (scrollId == 2048306) {
                nEquip.setPotential5(rank);
                //nEquip.setPotential6(rank);
            }
        } else if (Randomizer.nextInt(99) < curse) {
            return null;
        }
        return nEquip;
    }

    public Item scrollResetEquip(Item equip, Item scroll, MapleCharacter chr) {
        if (equip.getType() != 1) {
            return equip;
        }
        Equip nEquip = (Equip) equip;
        int scrollId = scroll.getItemId();
        Map scrollStats = getEquipStats(scrollId);
        int succe = (scrollStats == null) || (!scrollStats.containsKey("success")) ? 0 : ((Integer) scrollStats.get("success"));
        int curse = (scrollStats == null) || (!scrollStats.containsKey("cursed")) ? 0 : ((Integer) scrollStats.get("cursed"));
        int craft = chr.getTrait(MapleTraitType.craft).getLevel() / 10;
        int lucksKey = ItemFlag.LUCKS_KEY.check(equip.getFlag()) ? 10 : 0;
        if (ItemFlag.LUCKS_KEY.check(equip.getFlag())) {
            equip.setFlag((short) (equip.getFlag() - ItemFlag.LUCKS_KEY.getValue()));
        }
        int success = succe + succe * (craft + lucksKey) / 100;
        if (chr.isAdmin()) {
            chr.dropMessage(-5, "還原卷軸 - 默認几率: " + succe + "% 傾向加成: " + craft + "% 幸運卷軸状态加成: " + lucksKey + "% 最終几率: " + success + "% 失敗消失几率: " + curse + "%");
        }
        if (Randomizer.nextInt(100) <= success) {
            return resetEquipStats(nEquip);
        }
        if (Randomizer.nextInt(99) < curse) {
            return null;
        }
        return nEquip;
    }

    public final Item scrollEquipWithId(final Item equip, final Item scroll, final boolean ws, final MapleCharacter chr, final int vegas, final int jl) {
        if (equip.getType() == 1) {
            int scrollId = scroll.getItemId();
            if (GameConstants.isEquipScroll(scrollId)) {
                return scrollEnhance(equip, scroll, chr, jl);
            }
            if (GameConstants.isPotentialScroll(scrollId)) {
                return scrollPotential(equip, scroll, chr);
            }
            if (GameConstants.isPotentialAddScroll(scrollId)) {
                return scrollPotentialAdd(equip, scroll, chr);
            }
            if (GameConstants.isResetScroll(scrollId)) {
                return scrollResetEquip(equip, scroll, chr);
            }
            Equip nEquip = (Equip) equip;
            Map<String, Integer> scrollStats = getEquipStats(scrollId);
            Map<String, Integer> equipStats = getEquipStats(equip.getItemId());
            int succ = scrollStats == null || !scrollStats.containsKey("success") ? 0 : GameConstants.isTablet(scrollId) ? GameConstants.getSuccessTablet(scrollId, nEquip.getLevel()) : scrollStats.get("success");

            int curse = scrollStats == null || !scrollStats.containsKey("cursed") ? 0 : GameConstants.isTablet(scrollId) ? GameConstants.getCurseTablet(scrollId, nEquip.getLevel()) : scrollStats.get("cursed");

            int limitedLv = scrollStats == null || !scrollStats.containsKey("limitedLv") ? 0 : scrollStats.get("limitedLv");

            if (limitedLv > 0 && nEquip.getLevel() < limitedLv) {
                return nEquip;
            }
            if (GameConstants.isSpecialScroll(scrollId)) {
                switch (scrollId) {
                    case 2530003:
                    case 5068000:
                    case 2532001:
                    case 5068100:
                    case 5068200:
                        if (GameConstants.isPetEquip(nEquip.getItemId())) {
                            break;
                        } else {
                            return nEquip;
                        }
                    default:
                        if (GameConstants.isPetEquip(nEquip.getItemId())) {
                            return nEquip;
                        }
                }
            }

            int craft = GameConstants.isCleanSlate(scrollId) ? 0 : chr.getTrait(MapleTraitType.craft).getLevel() / 10;
            int lucksKey = ItemFlag.LUCKS_KEY.check(equip.getFlag()) ? 10 : 0;
            int success = succ + getSuccessRates(scroll.getItemId()) + succ * (lucksKey + craft) / 100;
            if (chr.isAdmin()) {
                chr.dropMessage(-5, "普通卷軸 - 默認几率: " + succ + "% 傾向加成: " + craft + "% 幸運状态加成: " + lucksKey + "% 最終概率: " + success + "% 失敗消失几率: " + curse + "%");
            }

            if (ItemFlag.LUCKS_KEY.check(equip.getFlag()) && (!GameConstants.isSpecialScroll(scrollId) || GameConstants.is帶成功率特殊卷軸(scrollId))) {
                equip.setFlag((short) (equip.getFlag() - ItemFlag.LUCKS_KEY.getValue()));
            }
            if ((GameConstants.isSpecialScroll(scrollId) && !GameConstants.is帶成功率特殊卷軸(scrollId)) || Randomizer.nextInt(100) <= success) {
                switch (scrollId) {
                    case 2040727://防滑卷轴10%
                        short flag = nEquip.getFlag();
                        flag = (short) (flag | ItemFlag.SPIKES.getValue());
                        nEquip.setFlag(flag);
                        break;
                    case 2041058://防寒卷轴10%
                        flag = nEquip.getFlag();
                        flag = (short) (flag | ItemFlag.COLD.getValue());
                        nEquip.setFlag(flag);
                        break;
                    default:
                        if (GameConstants.is幸運卷軸(scrollId)) {
                            flag = nEquip.getFlag();
                            flag = (short) (flag | ItemFlag.LUCKS_KEY.getValue());
                            nEquip.setFlag(flag);
                            if (!GameConstants.is防暴卷軸(scrollId)) {
                                break;
                            }
                        }
                        if (GameConstants.is防暴卷軸(scrollId)) {
                            int maxEqp = scrollStats != null && scrollStats.containsKey("maxSuperiorEqp") ? scrollStats.get("maxSuperiorEqp") : 12;
                            if (maxEqp <= nEquip.getEnhance() || (scrollStats != null && scrollStats.containsKey("maxSuperiorEqp") && !isSuperiorEquip(nEquip.getItemId()))) {
                                if (chr.isAdmin()) {
                                    chr.dropMessage(-5, "砸卷错误：武器強化次數超过或等于卷軸限制 " + (maxEqp <= nEquip.getEnhance()) + " 只能砸極真道具而装备不是極真道具 " + ((scrollStats != null && scrollStats.containsKey("maxSuperiorEqp") && !isSuperiorEquip(nEquip.getItemId())) || isSuperiorEquip(nEquip.getItemId())));
                                }
                                break;
                            }
                            flag = nEquip.getFlag();
                            flag = (short) (flag | ItemFlag.SHIELD_WARD.getValue());
                            nEquip.setFlag(flag);
                            break;
                        } else if (GameConstants.is安全卷軸(scrollId)) {
                            flag = nEquip.getFlag();
                            flag = (short) (flag | ItemFlag.SLOTS_PROTECT.getValue());
                            nEquip.setFlag(flag);
                            break;
                        } else if (GameConstants.is卷軸防護卷軸(scrollId)) {
                            flag = nEquip.getFlag();
                            flag = (short) (flag | ItemFlag.SCROLL_PROTECT.getValue());
                            nEquip.setFlag(flag);
                            break;
                        } else if (GameConstants.isCleanSlate(scrollId)) {//白醫卷軸
                            /*if (scrollStats == null || !scrollStats.containsKey("recover") || !equipStats.containsKey("tuc") || nEquip.getLevel() + nEquip.getUpgradeSlots() >= equipStats.get("tuc") + nEquip.getViciousHammer()) {
                             if (chr.isAdmin()) {
                             chr.dropMessage(-9, "砸卷错误：不存在卷轴升级次数 " + (scrollStats != null ? !scrollStats.containsKey("recover") : true) + " 超过可恢复次数上限 " + (nEquip.getLevel() + nEquip.getUpgradeSlots() >= equipStats.get("tuc") + nEquip.getViciousHammer()));
                             }
                             break;
                             }
                             nEquip.setUpgradeSlots((byte) (nEquip.getUpgradeSlots() + scrollStats.get("recover")));*/
                            switch (scrollId) {
                                case 2049000:
                                case 2049001:
                                case 2049002:
                                case 2049003:
                                case 2049004:
                                case 2049005:
                                case 2049009:
                                case 2049010:
                                case 2049011:
                                case 2049013:
                                case 2049014:
                                case 2049015:
                                case 2049016:
                                case 2049018:
                                case 2049019:
                                case 2049024:
                                case 2049025:
                                    if ((equipStats.containsKey("tuc")) && (nEquip.getLevel() + nEquip.getUpgradeSlots() < (equipStats.get("tuc")) + nEquip.getViciousHammer())) {
                                        nEquip.setUpgradeSlots((byte) (nEquip.getUpgradeSlots() + 1));
                                    }
                                    break;
                                case 2049006:
                                case 2049007:
                                case 2049008:
                                case 2049012:
                                    if ((equipStats.containsKey("tuc")) && (nEquip.getLevel() + nEquip.getUpgradeSlots() < (equipStats.get("tuc")) + nEquip.getViciousHammer())) {
                                        nEquip.setUpgradeSlots((byte) (nEquip.getUpgradeSlots() + 2));
                                    }
                                    break;
                            }
                            break;
                        } else if (GameConstants.isChaosScroll(scrollId)) {
                            int stat = GameConstants.getChaosNumber(scrollId);
                            int increase = Randomizer.nextBoolean() ? 1 : (GameConstants.isChaosForGoodness(scrollId)) || (isNegativeScroll(scrollId)) ? 1 : -1;
                            if (nEquip.getStr() > 0) {
                                nEquip.setStr((short) (nEquip.getStr() + (Randomizer.nextInt(stat) + 1) * increase));
                            }
                            if (nEquip.getDex() > 0) {
                                nEquip.setDex((short) (nEquip.getDex() + (Randomizer.nextInt(stat) + 1) * increase));
                            }
                            if (nEquip.getInt() > 0) {
                                nEquip.setInt((short) (nEquip.getInt() + (Randomizer.nextInt(stat) + 1) * increase));
                            }
                            if (nEquip.getLuk() > 0) {
                                nEquip.setLuk((short) (nEquip.getLuk() + (Randomizer.nextInt(stat) + 1) * increase));
                            }
                            if (nEquip.getWatk() > 0) {
                                nEquip.setWatk((short) (nEquip.getWatk() + (Randomizer.nextInt(stat) + 1) * increase));
                            }
                            if (nEquip.getWdef() > 0) {
                                nEquip.setWdef((short) (nEquip.getWdef() + (Randomizer.nextInt(stat) + 1) * increase));
                            }
                            if (nEquip.getMatk() > 0) {
                                nEquip.setMatk((short) (nEquip.getMatk() + (Randomizer.nextInt(stat) + 1) * increase));
                            }
                            if (nEquip.getMdef() > 0) {
                                nEquip.setMdef((short) (nEquip.getMdef() + (Randomizer.nextInt(stat) + 1) * increase));
                            }
                            if (nEquip.getAcc() > 0) {
                                nEquip.setAcc((short) (nEquip.getAcc() + (Randomizer.nextInt(stat) + 1) * increase));
                            }
                            if (nEquip.getAvoid() > 0) {
                                nEquip.setAvoid((short) (nEquip.getAvoid() + (Randomizer.nextInt(stat) + 1) * increase));
                            }
                            if (nEquip.getSpeed() > 0) {
                                nEquip.setSpeed((short) (nEquip.getSpeed() + (Randomizer.nextInt(stat) + 1) * increase));
                            }
                            if (nEquip.getJump() > 0) {
                                nEquip.setJump((short) (nEquip.getJump() + (Randomizer.nextInt(stat) + 1) * increase));
                            }
                            if (nEquip.getHp() > 0) {
                                nEquip.setHp((short) (nEquip.getHp() + (Randomizer.nextInt(stat) + 1) * increase));
                            }
                            if (nEquip.getMp() <= 0) {
                                break;
                            }
                            nEquip.setMp((short) (nEquip.getMp() + (Randomizer.nextInt(stat) + 1) * increase));
                        } else {
                            for (Map.Entry stat : scrollStats.entrySet()) {
                                String key = (String) stat.getKey();
                                switch (key) {
                                    case "STR":
                                        nEquip.setStr((short) (nEquip.getStr() + ((Integer) stat.getValue())));
                                        break;
                                    case "DEX":
                                        nEquip.setDex((short) (nEquip.getDex() + ((Integer) stat.getValue())));
                                        break;
                                    case "INT":
                                        nEquip.setInt((short) (nEquip.getInt() + ((Integer) stat.getValue())));
                                        break;
                                    case "LUK":
                                        nEquip.setLuk((short) (nEquip.getLuk() + ((Integer) stat.getValue())));
                                        break;
                                    case "PAD":
                                        nEquip.setWatk((short) (nEquip.getWatk() + ((Integer) stat.getValue())));
                                        break;
                                    case "PDD":
                                        nEquip.setWdef((short) (nEquip.getWdef() + ((Integer) stat.getValue())));
                                        break;
                                    case "MAD":
                                        nEquip.setMatk((short) (nEquip.getMatk() + ((Integer) stat.getValue())));
                                        break;
                                    case "MDD":
                                        nEquip.setMdef((short) (nEquip.getMdef() + ((Integer) stat.getValue())));
                                        break;
                                    case "ACC":
                                        nEquip.setAcc((short) (nEquip.getAcc() + ((Integer) stat.getValue())));
                                        break;
                                    case "EVA":
                                        nEquip.setAvoid((short) (nEquip.getAvoid() + ((Integer) stat.getValue())));
                                        break;
                                    case "Speed":
                                        nEquip.setSpeed((short) (nEquip.getSpeed() + ((Integer) stat.getValue())));
                                        break;
                                    case "Jump":
                                        nEquip.setJump((short) (nEquip.getJump() + ((Integer) stat.getValue())));
                                        break;
                                    case "MHP":
                                        nEquip.setHp((short) (nEquip.getHp() + ((Integer) stat.getValue())));
                                        break;
                                    case "MMP":
                                        nEquip.setMp((short) (nEquip.getMp() + ((Integer) stat.getValue())));
                                        break;
                                }
                            }
                        }
                }
                if (!GameConstants.isCleanSlate(scrollId) && !GameConstants.isSpecialScroll(scrollId)) {
                    short oldFlag = nEquip.getFlag();
                    if (ItemFlag.SLOTS_PROTECT.check(oldFlag)) {
                        nEquip.setFlag((short) (oldFlag - ItemFlag.SLOTS_PROTECT.getValue()));
                    }
                    int scrollUseSlots = GameConstants.isAzwanScroll(scrollId) ? getSlots(scrollId) : 1;
                    nEquip.setUpgradeSlots((byte) (nEquip.getUpgradeSlots() - scrollUseSlots));
                    nEquip.setLevel((byte) (nEquip.getLevel() + scrollUseSlots));
                }
            } else {
                if (!ws && !GameConstants.isCleanSlate(scrollId) && !GameConstants.isSpecialScroll(scrollId)) {
                    short oldFlag = nEquip.getFlag();
                    if (ItemFlag.SLOTS_PROTECT.check(oldFlag)) {
                        nEquip.setFlag((short) (oldFlag - ItemFlag.SLOTS_PROTECT.getValue()));
                        chr.dropMessage(5, "由于卷軸效果，升級次數沒有減少。");
                    } else {
                        int scrollUseSlots = GameConstants.isAzwanScroll(scrollId) ? getSlots(scrollId) : 1;
                        nEquip.setUpgradeSlots((byte) (nEquip.getUpgradeSlots() - scrollUseSlots));
                    }
                }
                if (Randomizer.nextInt(99) < curse) {
                    return null;
                }
            }
        }
        return equip;
    }

    /*public final Item scrollEquipWithId(final Item equip, final Item scrollId, final boolean ws, final MapleCharacter chr, final int vegas) {
        if (equip.getType() == 1) { // See Item.java
            final Equip nEquip = (Equip) equip;
            final Map<String, Integer> stats = getEquipStats(scrollId.getItemId());
            final Map<String, Integer> eqstats = getEquipStats(equip.getItemId());
            final int succ = (GameConstants.isTablet(scrollId.getItemId()) ? GameConstants.getSuccessTablet(scrollId.getItemId(), nEquip.getLevel()) : ((GameConstants.isEquipScroll(scrollId.getItemId()) || GameConstants.isPotentialScroll(scrollId.getItemId()) || !stats.containsKey("success") ? 0 : stats.get("success"))));
            final int curse = (GameConstants.isTablet(scrollId.getItemId()) ? GameConstants.getCurseTablet(scrollId.getItemId(), nEquip.getLevel()) : ((GameConstants.isEquipScroll(scrollId.getItemId()) || GameConstants.isPotentialScroll(scrollId.getItemId()) || !stats.containsKey("cursed") ? 0 : stats.get("cursed"))));
            final int added = (ItemFlag.LUCKS_KEY.check(equip.getFlag()) ? 10 : 0) + (chr.getTrait(MapleTraitType.craft).getLevel() / 10);
            int success = succ + (vegas == 5610000 && succ == 10 ? 20 : (vegas == 5610001 && succ == 60 ? 30 : 0)) + added;
            if (ItemFlag.LUCKS_KEY.check(equip.getFlag()) && !GameConstants.isPotentialScroll(scrollId.getItemId()) && !GameConstants.isEquipScroll(scrollId.getItemId()) && !GameConstants.isSpecialScroll(scrollId.getItemId())) {
                equip.setFlag((short) (equip.getFlag() - ItemFlag.LUCKS_KEY.getValue()));
            }
            if (GameConstants.isPotentialScroll(scrollId.getItemId()) || GameConstants.isEquipScroll(scrollId.getItemId()) || GameConstants.isSpecialScroll(scrollId.getItemId()) || Randomizer.nextInt(100) <= success) {
                switch (scrollId.getItemId()) {
                    case 2049000:
                    case 2049001:
                    case 2049002:
                    case 2049003:
                    case 2049004:
                    case 2049005: {
                        if (eqstats.containsKey("tuc") && nEquip.getLevel() + nEquip.getUpgradeSlots() < eqstats.get("tuc")) {
                            nEquip.setUpgradeSlots((byte) (nEquip.getUpgradeSlots() + 1));
                        }
                        break;
                    }
                    case 2049006:
                    case 2049007:
                    case 2049008: {
                        if (eqstats.containsKey("tuc") && nEquip.getLevel() + nEquip.getUpgradeSlots() < eqstats.get("tuc")) {
                            nEquip.setUpgradeSlots((byte) (nEquip.getUpgradeSlots() + 2));
                        }
                        break;
                    }
                    case 2040727: { // Spikes on shoe, prevents slip
                        short flag = nEquip.getFlag();
                        flag |= ItemFlag.SPIKES.getValue();
                        nEquip.setFlag(flag);
                        break;
                    }
                    case 2041058: { // Cape for Cold protection
                        short flag = nEquip.getFlag();
                        flag |= ItemFlag.COLD.getValue();
                        nEquip.setFlag(flag);
                        break;
                    }
                    case 5063000:
                    case 2530000:
                    case 2530001: {
                        short flag = nEquip.getFlag();
                        flag |= ItemFlag.LUCKS_KEY.getValue();
                        nEquip.setFlag(flag);
                        break;
                    }
                    case 5064000:
                    case 2531000: {
                        short flag = nEquip.getFlag();
                        flag |= ItemFlag.SHIELD_WARD.getValue();
                        nEquip.setFlag(flag);
                        break;
                    }
                    default: {
                        if (GameConstants.isChaosScroll(scrollId.getItemId())) {
                            final int z = GameConstants.getChaosNumber(scrollId.getItemId());
                            if (nEquip.getStr() > 0) {
                                nEquip.setStr((short) (nEquip.getStr() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : -1)));
                            }
                            if (nEquip.getDex() > 0) {
                                nEquip.setDex((short) (nEquip.getDex() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : -1)));
                            }
                            if (nEquip.getInt() > 0) {
                                nEquip.setInt((short) (nEquip.getInt() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : -1)));
                            }
                            if (nEquip.getLuk() > 0) {
                                nEquip.setLuk((short) (nEquip.getLuk() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : -1)));
                            }
                            if (nEquip.getWatk() > 0) {
                                nEquip.setWatk((short) (nEquip.getWatk() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : -1)));
                            }
                            if (nEquip.getWdef() > 0) {
                                nEquip.setWdef((short) (nEquip.getWdef() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : -1)));
                            }
                            if (nEquip.getMatk() > 0) {
                                nEquip.setMatk((short) (nEquip.getMatk() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : -1)));
                            }
                            if (nEquip.getMdef() > 0) {
                                nEquip.setMdef((short) (nEquip.getMdef() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : -1)));
                            }
                            if (nEquip.getAcc() > 0) {
                                nEquip.setAcc((short) (nEquip.getAcc() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : -1)));
                            }
                            if (nEquip.getAvoid() > 0) {
                                nEquip.setAvoid((short) (nEquip.getAvoid() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : -1)));
                            }
                            if (nEquip.getSpeed() > 0) {
                                nEquip.setSpeed((short) (nEquip.getSpeed() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : -1)));
                            }
                            if (nEquip.getJump() > 0) {
                                nEquip.setJump((short) (nEquip.getJump() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : -1)));
                            }
                            if (nEquip.getHp() > 0) {
                                nEquip.setHp((short) (nEquip.getHp() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : -1)));
                            }
                            if (nEquip.getMp() > 0) {
                                nEquip.setMp((short) (nEquip.getMp() + Randomizer.nextInt(z) * (Randomizer.nextBoolean() ? 1 : -1)));
                            }
                            break;
                        } else if (GameConstants.isEquipScroll(scrollId.getItemId())) {
                            final int chanc = Math.max((scrollId.getItemId() == 2049300 || scrollId.getItemId() == 2049303 ? 100 : (scrollId.getItemId() == 2049305 ? 60 : 80)) - (nEquip.getEnhance() * 10), 10) + added;
                            if (Randomizer.nextInt(100) > chanc) {
                                return null; //destroyed, nib
                            }
                            for (int i = 0; i < (scrollId.getItemId() == 2049305 ? 4 : (scrollId.getItemId() == 2049304 ? 3 : 1)); i++) {
                                if (nEquip.getStr() > 0 || Randomizer.nextInt(50) == 1) { //1/50
                                    nEquip.setStr((short) (nEquip.getStr() + Randomizer.nextInt(5)));
                                }
                                if (nEquip.getDex() > 0 || Randomizer.nextInt(50) == 1) { //1/50
                                    nEquip.setDex((short) (nEquip.getDex() + Randomizer.nextInt(5)));
                                }
                                if (nEquip.getInt() > 0 || Randomizer.nextInt(50) == 1) { //1/50
                                    nEquip.setInt((short) (nEquip.getInt() + Randomizer.nextInt(5)));
                                }
                                if (nEquip.getLuk() > 0 || Randomizer.nextInt(50) == 1) { //1/50
                                    nEquip.setLuk((short) (nEquip.getLuk() + Randomizer.nextInt(5)));
                                }
                                if (nEquip.getWatk() > 0 && GameConstants.isWeapon(nEquip.getItemId())) {
                                    nEquip.setWatk((short) (nEquip.getWatk() + Randomizer.nextInt(5)));
                                }
                                if (nEquip.getWdef() > 0 || Randomizer.nextInt(40) == 1) { //1/40
                                    nEquip.setWdef((short) (nEquip.getWdef() + Randomizer.nextInt(5)));
                                }
                                if (nEquip.getMatk() > 0 && GameConstants.isWeapon(nEquip.getItemId())) {
                                    nEquip.setMatk((short) (nEquip.getMatk() + Randomizer.nextInt(5)));
                                }
                                if (nEquip.getMdef() > 0 || Randomizer.nextInt(40) == 1) { //1/40
                                    nEquip.setMdef((short) (nEquip.getMdef() + Randomizer.nextInt(5)));
                                }
                                if (nEquip.getAcc() > 0 || Randomizer.nextInt(20) == 1) { //1/20
                                    nEquip.setAcc((short) (nEquip.getAcc() + Randomizer.nextInt(5)));
                                }
                                if (nEquip.getAvoid() > 0 || Randomizer.nextInt(20) == 1) { //1/20
                                    nEquip.setAvoid((short) (nEquip.getAvoid() + Randomizer.nextInt(5)));
                                }
                                if (nEquip.getSpeed() > 0 || Randomizer.nextInt(10) == 1) { //1/10
                                    nEquip.setSpeed((short) (nEquip.getSpeed() + Randomizer.nextInt(5)));
                                }
                                if (nEquip.getJump() > 0 || Randomizer.nextInt(10) == 1) { //1/10
                                    nEquip.setJump((short) (nEquip.getJump() + Randomizer.nextInt(5)));
                                }
                                if (nEquip.getHp() > 0 || Randomizer.nextInt(5) == 1) { //1/5
                                    nEquip.setHp((short) (nEquip.getHp() + Randomizer.nextInt(5)));
                                }
                                if (nEquip.getMp() > 0 || Randomizer.nextInt(5) == 1) { //1/5
                                    nEquip.setMp((short) (nEquip.getMp() + Randomizer.nextInt(5)));
                                }
                                nEquip.setEnhance((byte) (nEquip.getEnhance() + 1));
                            }
                            break;
                        } else if (GameConstants.isPotentialScroll(scrollId.getItemId())) {
                            if (nEquip.getState() == 0) {
                                final int chanc = (scrollId.getItemId() == 5534000 || scrollId.getItemId() == 2049402 || scrollId.getItemId() == 2049406 ? 100 : (scrollId.getItemId() == 2049400 ? 90 : 70)) + added;
                                if (Randomizer.nextInt(100) > chanc) {
                                    return null; //destroyed, nib
                                }
                                nEquip.resetPotential();
                            }
                            break;
                        } else {
                            for (Entry<String, Integer> stat : stats.entrySet()) {
                                final String key = stat.getKey();

                                if (key.equals("STR")) {
                                    nEquip.setStr((short) (nEquip.getStr() + stat.getValue().intValue()));
                                } else if (key.equals("DEX")) {
                                    nEquip.setDex((short) (nEquip.getDex() + stat.getValue().intValue()));
                                } else if (key.equals("INT")) {
                                    nEquip.setInt((short) (nEquip.getInt() + stat.getValue().intValue()));
                                } else if (key.equals("LUK")) {
                                    nEquip.setLuk((short) (nEquip.getLuk() + stat.getValue().intValue()));
                                } else if (key.equals("PAD")) {
                                    nEquip.setWatk((short) (nEquip.getWatk() + stat.getValue().intValue()));
                                } else if (key.equals("PDD")) {
                                    nEquip.setWdef((short) (nEquip.getWdef() + stat.getValue().intValue()));
                                } else if (key.equals("MAD")) {
                                    nEquip.setMatk((short) (nEquip.getMatk() + stat.getValue().intValue()));
                                } else if (key.equals("MDD")) {
                                    nEquip.setMdef((short) (nEquip.getMdef() + stat.getValue().intValue()));
                                } else if (key.equals("ACC")) {
                                    nEquip.setAcc((short) (nEquip.getAcc() + stat.getValue().intValue()));
                                } else if (key.equals("EVA")) {
                                    nEquip.setAvoid((short) (nEquip.getAvoid() + stat.getValue().intValue()));
                                } else if (key.equals("Speed")) {
                                    nEquip.setSpeed((short) (nEquip.getSpeed() + stat.getValue().intValue()));
                                } else if (key.equals("Jump")) {
                                    nEquip.setJump((short) (nEquip.getJump() + stat.getValue().intValue()));
                                } else if (key.equals("MHP")) {
                                    nEquip.setHp((short) (nEquip.getHp() + stat.getValue().intValue()));
                                } else if (key.equals("MMP")) {
                                    nEquip.setMp((short) (nEquip.getMp() + stat.getValue().intValue()));
                                } else if (key.equals("MHPr")) {
                                    nEquip.setHpR((short) (nEquip.getHpR() + stat.getValue().intValue()));
                                } else if (key.equals("MMPr")) {
                                    nEquip.setMpR((short) (nEquip.getMpR() + stat.getValue().intValue()));
                                }
                            }
                            break;
                        }
                    }
                }
                if (!GameConstants.isCleanSlate(scrollId.getItemId()) && !GameConstants.isSpecialScroll(scrollId.getItemId()) && !GameConstants.isEquipScroll(scrollId.getItemId()) && !GameConstants.isPotentialScroll(scrollId.getItemId())) {
                    nEquip.setUpgradeSlots((byte) (nEquip.getUpgradeSlots() - 1));
                    nEquip.setLevel((byte) (nEquip.getLevel() + 1));
                }
            } else {
                if (!ws && !GameConstants.isCleanSlate(scrollId.getItemId()) && !GameConstants.isSpecialScroll(scrollId.getItemId()) && !GameConstants.isEquipScroll(scrollId.getItemId()) && !GameConstants.isPotentialScroll(scrollId.getItemId())) {
                    nEquip.setUpgradeSlots((byte) (nEquip.getUpgradeSlots() - 1));
                }
                if (Randomizer.nextInt(99) < curse) {
                    return null;
                }
            }
        }
        return equip;
    }*/
    public final Item getEquipById(final int equipId) {
        return getEquipById(equipId, -1);
    }

    public final Item getEquipById(final int equipId, final int ringId) {
        final ItemInformation i = getItemInformation(equipId);
        if (i == null) {
            return new Equip(equipId, (short) 0, ringId, (byte) 0);
        }
        final Item eq = i.eq.copy();
        eq.setUniqueId(ringId);
        return eq;
    }

    protected final short getRandStatFusion(final short defaultValue, final int value1, final int value2) {
        if (defaultValue == 0) {
            return 0;
        }
        final int range = ((value1 + value2) / 2) - defaultValue;
        final int rand = Randomizer.nextInt(Math.abs(range) + 1);
        return (short) (defaultValue + (range < 0 ? -rand : rand));
    }

    protected final short getRandStat(final short defaultValue, final int maxRange) {
        if (defaultValue == 0) {
            return 0;
        }
        // vary no more than ceil of 10% of stat
        final int lMaxRange = (int) Math.min(Math.ceil(defaultValue * 0.1), maxRange);

        return (short) ((defaultValue - lMaxRange) + Randomizer.nextInt(lMaxRange * 2 + 1));
    }

    protected final short getRandStatAbove(final short defaultValue, final int maxRange) {
        if (defaultValue <= 0) {
            return 0;
        }
        final int lMaxRange = (int) Math.min(Math.ceil(defaultValue * 0.1), maxRange);

        return (short) ((defaultValue) + Randomizer.nextInt(lMaxRange + 1));
    }

    public final Equip randomizeStats(final Equip equip) {
        equip.setStr(getRandStat(equip.getStr(), 5));
        equip.setDex(getRandStat(equip.getDex(), 5));
        equip.setInt(getRandStat(equip.getInt(), 5));
        equip.setLuk(getRandStat(equip.getLuk(), 5));
        equip.setMatk(getRandStat(equip.getMatk(), 5));
        equip.setWatk(getRandStat(equip.getWatk(), 5));
        equip.setAcc(getRandStat(equip.getAcc(), 5));
        equip.setAvoid(getRandStat(equip.getAvoid(), 5));
        equip.setJump(getRandStat(equip.getJump(), 5));
        equip.setHands(getRandStat(equip.getHands(), 5));
        equip.setSpeed(getRandStat(equip.getSpeed(), 5));
        equip.setWdef(getRandStat(equip.getWdef(), 10));
        equip.setMdef(getRandStat(equip.getMdef(), 10));
        equip.setHp(getRandStat(equip.getHp(), 10));
        equip.setMp(getRandStat(equip.getMp(), 10));
        return equip;
    }

    public final Equip randomizeStats_Above(final Equip equip) {
        equip.setStr(getRandStatAbove(equip.getStr(), 5));
        equip.setDex(getRandStatAbove(equip.getDex(), 5));
        equip.setInt(getRandStatAbove(equip.getInt(), 5));
        equip.setLuk(getRandStatAbove(equip.getLuk(), 5));
        equip.setMatk(getRandStatAbove(equip.getMatk(), 5));
        equip.setWatk(getRandStatAbove(equip.getWatk(), 5));
        equip.setAcc(getRandStatAbove(equip.getAcc(), 5));
        equip.setAvoid(getRandStatAbove(equip.getAvoid(), 5));
        equip.setJump(getRandStatAbove(equip.getJump(), 5));
        equip.setHands(getRandStatAbove(equip.getHands(), 5));
        equip.setSpeed(getRandStatAbove(equip.getSpeed(), 5));
        equip.setWdef(getRandStatAbove(equip.getWdef(), 10));
        equip.setMdef(getRandStatAbove(equip.getMdef(), 10));
        equip.setHp(getRandStatAbove(equip.getHp(), 10));
        equip.setMp(getRandStatAbove(equip.getMp(), 10));
        return equip;
    }

    public final Equip fuse(final Equip equip1, final Equip equip2) {
        if (equip1.getItemId() != equip2.getItemId()) {
            return equip1;
        }
        final Equip equip = (Equip) getEquipById(equip1.getItemId());
        equip.setStr(getRandStatFusion(equip.getStr(), equip1.getStr(), equip2.getStr()));
        equip.setDex(getRandStatFusion(equip.getDex(), equip1.getDex(), equip2.getDex()));
        equip.setInt(getRandStatFusion(equip.getInt(), equip1.getInt(), equip2.getInt()));
        equip.setLuk(getRandStatFusion(equip.getLuk(), equip1.getLuk(), equip2.getLuk()));
        equip.setMatk(getRandStatFusion(equip.getMatk(), equip1.getMatk(), equip2.getMatk()));
        equip.setWatk(getRandStatFusion(equip.getWatk(), equip1.getWatk(), equip2.getWatk()));
        equip.setAcc(getRandStatFusion(equip.getAcc(), equip1.getAcc(), equip2.getAcc()));
        equip.setAvoid(getRandStatFusion(equip.getAvoid(), equip1.getAvoid(), equip2.getAvoid()));
        equip.setJump(getRandStatFusion(equip.getJump(), equip1.getJump(), equip2.getJump()));
        equip.setHands(getRandStatFusion(equip.getHands(), equip1.getHands(), equip2.getHands()));
        equip.setSpeed(getRandStatFusion(equip.getSpeed(), equip1.getSpeed(), equip2.getSpeed()));
        equip.setWdef(getRandStatFusion(equip.getWdef(), equip1.getWdef(), equip2.getWdef()));
        equip.setMdef(getRandStatFusion(equip.getMdef(), equip1.getMdef(), equip2.getMdef()));
        equip.setHp(getRandStatFusion(equip.getHp(), equip1.getHp(), equip2.getHp()));
        equip.setMp(getRandStatFusion(equip.getMp(), equip1.getMp(), equip2.getMp()));
        return equip;
    }

    public final int getTotalStat(final Equip equip) { //i get COOL when my defense is higher on gms...
        return equip.getStr() + equip.getDex() + equip.getInt() + equip.getLuk() + equip.getMatk() + equip.getWatk() + equip.getAcc() + equip.getAvoid() + equip.getJump()
                + equip.getHands() + equip.getSpeed() + equip.getHp() + equip.getMp() + equip.getWdef() + equip.getMdef();
    }

    public final MapleStatEffect getItemEffect(final int itemId) {
        MapleStatEffect ret = itemEffects.get(Integer.valueOf(itemId));
        if (ret == null) {
            final MapleData item = getItemData(itemId);
            if (item == null || item.getChildByPath("spec") == null) {
                return null;
            }
            ret = MapleStatEffect.loadItemEffectFromData(item.getChildByPath("spec"), itemId);
            itemEffects.put(Integer.valueOf(itemId), ret);
        }
        return ret;
    }

    public final MapleStatEffect getItemEffectEX(final int itemId) {
        MapleStatEffect ret = itemEffectsEx.get(Integer.valueOf(itemId));
        if (ret == null) {
            final MapleData item = getItemData(itemId);
            if (item == null || item.getChildByPath("specEx") == null) {
                return null;
            }
            ret = MapleStatEffect.loadItemEffectFromData(item.getChildByPath("specEx"), itemId);
            itemEffectsEx.put(Integer.valueOf(itemId), ret);
        }
        return ret;
    }

    public final int getCreateId(final int id) {
        final ItemInformation i = getItemInformation(id);
        if (i == null) {
            return 0;
        }
        return i.create;
    }

    public final int getCardMobId(final int id) {
        final ItemInformation i = getItemInformation(id);
        if (i == null) {
            return 0;
        }
        return i.monsterBook;
    }

    public final int getBagType(final int id) {
        final ItemInformation i = getItemInformation(id);
        if (i == null) {
            return 0;
        }
        return i.flag & 0xF;
    }

    public final int getWatkForProjectile(final int itemId) {
        final ItemInformation i = getItemInformation(itemId);
        if (i == null || i.equipStats == null || i.equipStats.get("incPAD") == null) {
            return 0;
        }
        return i.equipStats.get("incPAD");
    }

    public final boolean canScroll(final int scrollid, final int itemid) {
        return (scrollid / 100 % 100 == itemid / 10000 % 100) || ((itemid >= 1672000) && (itemid <= 1672010));
    }

    public final String getName(final int itemId) {
        final ItemInformation i = getItemInformation(itemId);
        if (i == null) {
            return null;
        }
        return i.name;
    }

    public final String getDesc(final int itemId) {
        final ItemInformation i = getItemInformation(itemId);
        if (i == null) {
            return null;
        }
        return i.desc;
    }

    public final String getMsg(final int itemId) {
        final ItemInformation i = getItemInformation(itemId);
        if (i == null) {
            return null;
        }
        return i.msg;
    }

    public final short getItemMakeLevel(final int itemId) {
        final ItemInformation i = getItemInformation(itemId);
        if (i == null) {
            return 0;
        }
        return i.itemMakeLevel;
    }

    public final boolean isDropRestricted(final int itemId) {
        final ItemInformation i = getItemInformation(itemId);
        if (i == null) {
            return false;
        }
        return ((i.flag & 0x200) != 0) || ((i.flag & 0x400) != 0) || GameConstants.isDropRestricted(itemId);
        //return ((i.flag & 0x200) != 0 || (i.flag & 0x400) != 0 || GameConstants.isDropRestricted(itemId)) && (itemId == 3012000 || itemId == 3012015 || itemId / 10000 != 301) && itemId != 2041200 && itemId != 5640000 && itemId != 4170023 && itemId != 2040124 && itemId != 2040125 && itemId != 2040126 && itemId != 2040211 && itemId != 2040212 && itemId != 2040227 && itemId != 2040228 && itemId != 2040229 && itemId != 2040230 && itemId != 1002926 && itemId != 1002906 && itemId != 1002927;
    }

    public final boolean isPickupRestricted(final int itemId) {
        final ItemInformation i = getItemInformation(itemId);
        if (i == null) {
            return false;
        }
        return ((i.flag & 0x80) != 0 || GameConstants.isPickupRestricted(itemId)) && itemId != 4001168 && itemId != 4031306 && itemId != 4031307;
    }

    public final boolean isAccountShared(final int itemId) {
        final ItemInformation i = getItemInformation(itemId);
        if (i == null) {
            return false;
        }
        return (i.flag & 0x100) != 0;
    }

    public final int getStateChangeItem(final int itemId) {
        final ItemInformation i = getItemInformation(itemId);
        if (i == null) {
            return 0;
        }
        return i.stateChange;
    }

    public final int getMeso(final int itemId) {
        final ItemInformation i = getItemInformation(itemId);
        if (i == null) {
            return 0;
        }
        return i.meso;
    }

    public final boolean isShareTagEnabled(final int itemId) {
        final ItemInformation i = getItemInformation(itemId);
        if (i == null) {
            return false;
        }
        return (i.flag & 0x800) != 0;
    }

    public final boolean isKarmaEnabled(final int itemId) {
        final ItemInformation i = getItemInformation(itemId);
        if (i == null) {
            return false;
        }
        return i.karmaEnabled == 1;
    }

    public final boolean isPKarmaEnabled(final int itemId) {
        final ItemInformation i = getItemInformation(itemId);
        if (i == null) {
            return false;
        }
        return i.karmaEnabled == 2;
    }

    public final boolean isPickupBlocked(final int itemId) {
        final ItemInformation i = getItemInformation(itemId);
        if (i == null) {
            return false;
        }
        return (i.flag & 0x40) != 0;
    }

    public final boolean isLogoutExpire(final int itemId) {
        final ItemInformation i = getItemInformation(itemId);
        if (i == null) {
            return false;
        }
        return (i.flag & 0x20) != 0;
    }

    public final boolean cantSell(final int itemId) { //true = cant sell, false = can sell
        final ItemInformation i = getItemInformation(itemId);
        if (i == null) {
            return false;
        }
        return (i.flag & 0x10) != 0;
    }

    public final Pair<Integer, List<StructRewardItem>> getRewardItem(final int itemid) {
        final ItemInformation i = getItemInformation(itemid);
        if (i == null) {
            return null;
        }
        return new Pair<Integer, List<StructRewardItem>>(i.totalprob, i.rewardItems);
    }

    public final boolean isMobHP(final int itemId) {
        final ItemInformation i = getItemInformation(itemId);
        if (i == null) {
            return false;
        }
        return (i.flag & 0x1000) != 0;
    }

    public final boolean isQuestItem(final int itemId) {
        final ItemInformation i = getItemInformation(itemId);
        if (i == null) {
            return false;
        }
        return (i.flag & 0x200) != 0 && itemId / 10000 != 301;
    }

    public final Pair<Integer, List<Integer>> questItemInfo(final int itemId) {
        final ItemInformation i = getItemInformation(itemId);
        if (i == null) {
            return null;
        }
        return new Pair<Integer, List<Integer>>(i.questId, i.questItems);
    }

    public final Pair<Integer, String> replaceItemInfo(final int itemId) {
        final ItemInformation i = getItemInformation(itemId);
        if (i == null) {
            return null;
        }
        return new Pair<Integer, String>(i.replaceItem, i.replaceMsg);
    }

    public final List<Triple<String, Point, Point>> getAfterImage(final String after) {
        return afterImage.get(after);
    }

    public final String getAfterImage(final int itemId) {
        final ItemInformation i = getItemInformation(itemId);
        if (i == null) {
            return null;
        }
        return i.afterImage;
    }

    public final boolean itemExists(final int itemId) {
        if (GameConstants.getInventoryType(itemId) == MapleInventoryType.UNDEFINED) {
            return false;
        }
        return getItemInformation(itemId) != null;
    }

    public final boolean isCash(final int itemId) {
        if (getEquipStats(itemId) == null) {
            return GameConstants.getInventoryType(itemId) == MapleInventoryType.CASH;
        }
        return GameConstants.getInventoryType(itemId) == MapleInventoryType.CASH || getEquipStats(itemId).get("cash") != null;
    }

    public final ItemInformation getItemInformation(final int itemId) {
        if (itemId <= 0) {
            return null;
        }
        return dataCache.get(itemId);
    }

    private ItemInformation tmpInfo = null;

    public void initItemRewardData(ResultSet sqlRewardData) throws SQLException {
        final int itemID = sqlRewardData.getInt("itemid");
        if (tmpInfo == null || tmpInfo.itemId != itemID) {
            if (!dataCache.containsKey(itemID)) {
                System.out.println("[initItemRewardData] Tried to load an item while this is not in the cache: " + itemID);
                return;
            }
            tmpInfo = dataCache.get(itemID);
        }

        if (tmpInfo.rewardItems == null) {
            tmpInfo.rewardItems = new ArrayList<StructRewardItem>();
        }

        StructRewardItem add = new StructRewardItem();
        add.itemid = sqlRewardData.getInt("item");
        add.period = (add.itemid == 1122017 ? Math.max(sqlRewardData.getInt("period"), 7200) : sqlRewardData.getInt("period"));
        add.prob = sqlRewardData.getInt("prob");
        add.quantity = sqlRewardData.getShort("quantity");
        add.worldmsg = sqlRewardData.getString("worldMsg").length() <= 0 ? null : sqlRewardData.getString("worldMsg");
        add.effect = sqlRewardData.getString("effect");

        tmpInfo.rewardItems.add(add);
    }

    public void initItemAddData(ResultSet sqlAddData) throws SQLException {
        final int itemID = sqlAddData.getInt("itemid");
        if (tmpInfo == null || tmpInfo.itemId != itemID) {
            if (!dataCache.containsKey(itemID)) {
                System.out.println("[initItemAddData] Tried to load an item while this is not in the cache: " + itemID);
                return;
            }
            tmpInfo = dataCache.get(itemID);
        }

        if (tmpInfo.equipAdditions == null) {
            tmpInfo.equipAdditions = new EnumMap<EquipAdditions, Pair<Integer, Integer>>(EquipAdditions.class);
        }

        EquipAdditions z = EquipAdditions.fromString(sqlAddData.getString("key"));
        if (z != null) {
            tmpInfo.equipAdditions.put(z, new Pair<Integer, Integer>(sqlAddData.getInt("value1"), sqlAddData.getInt("value2")));
        }
    }

    public void initItemEquipData(ResultSet sqlEquipData) throws SQLException {
        final int itemID = sqlEquipData.getInt("itemid");
        if (tmpInfo == null || tmpInfo.itemId != itemID) {
            if (!dataCache.containsKey(itemID)) {
                System.out.println("[initItemEquipData] Tried to load an item while this is not in the cache: " + itemID);
                return;
            }
            tmpInfo = dataCache.get(itemID);
        }

        if (tmpInfo.equipStats == null) {
            tmpInfo.equipStats = new HashMap<String, Integer>();
        }

        final int itemLevel = sqlEquipData.getInt("itemLevel");
        if (itemLevel == -1) {
            tmpInfo.equipStats.put(sqlEquipData.getString("key"), sqlEquipData.getInt("value"));
        } else {
            if (tmpInfo.equipIncs == null) {
                tmpInfo.equipIncs = new HashMap<Integer, Map<String, Integer>>();
            }

            Map<String, Integer> toAdd = tmpInfo.equipIncs.get(itemLevel);
            if (toAdd == null) {
                toAdd = new HashMap<String, Integer>();
                tmpInfo.equipIncs.put(itemLevel, toAdd);
            }
            toAdd.put(sqlEquipData.getString("key"), sqlEquipData.getInt("value"));
        }
    }

    public void finalizeEquipData(ItemInformation item) {
        int itemId = item.itemId;
        if (item.equipStats == null) {
            item.equipStats = new HashMap<String, Integer>();
        }

        item.eq = new Equip(itemId, (byte) 0, -1, (byte) 0);
        short stats = GameConstants.getStat(itemId, 0);
        if (stats > 0) {
            item.eq.setStr(stats);
            item.eq.setDex(stats);
            item.eq.setInt(stats);
            item.eq.setLuk(stats);
        }
        stats = GameConstants.getATK(itemId, 0);
        if (stats > 0) {
            item.eq.setWatk(stats);
            item.eq.setMatk(stats);
        }
        stats = GameConstants.getHpMp(itemId, 0);
        if (stats > 0) {
            item.eq.setHp(stats);
            item.eq.setMp(stats);
        }
        stats = GameConstants.getDEF(itemId, 0);
        if (stats > 0) {
            item.eq.setWdef(stats);
            item.eq.setMdef(stats);
        }
        if (item.equipStats.size() > 0) {
            for (Entry<String, Integer> stat : item.equipStats.entrySet()) {
                final String key = stat.getKey();

                if (key.equals("STR")) {
                    item.eq.setStr(GameConstants.getStat(itemId, stat.getValue().intValue()));
                } else if (key.equals("DEX")) {
                    item.eq.setDex(GameConstants.getStat(itemId, stat.getValue().intValue()));
                } else if (key.equals("INT")) {
                    item.eq.setInt(GameConstants.getStat(itemId, stat.getValue().intValue()));
                } else if (key.equals("LUK")) {
                    item.eq.setLuk(GameConstants.getStat(itemId, stat.getValue().intValue()));
                } else if (key.equals("PAD")) {
                    item.eq.setWatk(GameConstants.getATK(itemId, stat.getValue().intValue()));
                } else if (key.equals("PDD")) {
                    item.eq.setWdef(GameConstants.getDEF(itemId, stat.getValue().intValue()));
                } else if (key.equals("MAD")) {
                    item.eq.setMatk(GameConstants.getATK(itemId, stat.getValue().intValue()));
                } else if (key.equals("MDD")) {
                    item.eq.setMdef(GameConstants.getDEF(itemId, stat.getValue().intValue()));
                } else if (key.equals("ACC")) {
                    item.eq.setAcc((short) stat.getValue().intValue());
                } else if (key.equals("EVA")) {
                    item.eq.setAvoid((short) stat.getValue().intValue());
                } else if (key.equals("Speed")) {
                    item.eq.setSpeed((short) stat.getValue().intValue());
                } else if (key.equals("Jump")) {
                    item.eq.setJump((short) stat.getValue().intValue());
                } else if (key.equals("MHP")) {
                    item.eq.setHp(GameConstants.getHpMp(itemId, stat.getValue().intValue()));
                } else if (key.equals("MMP")) {
                    item.eq.setMp(GameConstants.getHpMp(itemId, stat.getValue().intValue()));
                } else if (key.equals("MHPr")) {
                    item.eq.setHpR((short) stat.getValue().intValue());
                } else if (key.equals("MMPr")) {
                    item.eq.setMpR((short) stat.getValue().intValue());
                } else if (key.equals("tuc")) {
                    item.eq.setUpgradeSlots(stat.getValue().byteValue());
                } else if (key.equals("Craft")) {
                    item.eq.setHands(stat.getValue().shortValue());
                } else if (key.equals("durability")) {
                    item.eq.setDurability(stat.getValue().intValue());
                } else if (key.equals("charmEXP")) {
                    item.eq.setCharmEXP(stat.getValue().shortValue());
                } else if (key.equals("PVPDamage")) {
                    item.eq.setPVPDamage(stat.getValue().shortValue());
                }
            }
            if (item.equipStats.get("cash") != null && item.eq.getCharmEXP() <= 0) { //set the exp
                short exp = 0;
                int identifier = itemId / 10000;
                if (GameConstants.isWeapon(itemId) || identifier == 106) { //weapon overall
                    exp = 60;
                } else if (identifier == 100) { //hats
                    exp = 50;
                } else if (GameConstants.isAccessory(itemId) || identifier == 102 || identifier == 108 || identifier == 107) { //gloves shoes accessory
                    exp = 40;
                } else if (identifier == 104 || identifier == 105 || identifier == 110) { //top bottom cape
                    exp = 30;
                }
                item.eq.setCharmEXP(exp);
            }
        }
    }

    public void initItemInformation(ResultSet sqlItemData) throws SQLException {
        final ItemInformation ret = new ItemInformation();
        final int itemId = sqlItemData.getInt("itemid");
        ret.itemId = itemId;
        ret.slotMax = GameConstants.getSlotMax(itemId) > 0 ? GameConstants.getSlotMax(itemId) : sqlItemData.getShort("slotMax");
        ret.price = Double.parseDouble(sqlItemData.getString("price"));
        ret.wholePrice = sqlItemData.getInt("wholePrice");
        ret.stateChange = sqlItemData.getInt("stateChange");
        ret.name = sqlItemData.getString("name");
        ret.desc = sqlItemData.getString("desc");
        ret.msg = sqlItemData.getString("msg");

        ret.flag = sqlItemData.getInt("flags");

        ret.karmaEnabled = sqlItemData.getByte("karma");
        ret.meso = sqlItemData.getInt("meso");
        ret.monsterBook = sqlItemData.getInt("monsterBook");
        ret.itemMakeLevel = sqlItemData.getShort("itemMakeLevel");
        ret.questId = sqlItemData.getInt("questId");
        ret.create = sqlItemData.getInt("create");
        ret.replaceItem = sqlItemData.getInt("replaceId");
        ret.replaceMsg = sqlItemData.getString("replaceMsg");
        ret.afterImage = sqlItemData.getString("afterImage");
        ret.cardSet = 0;
        /*if (ret.monsterBook > 0 && itemId / 10000 == 238) {
            mobIds.put(ret.monsterBook, itemId);
            for (Entry<Integer, Triple<Integer, List<Integer>, List<Integer>>> set : monsterBookSets.entrySet()) {
                if (set.getValue().mid.contains(itemId)) {
                    ret.cardSet = set.getKey();
                    break;
                }
            }
        }*/

        final String scrollRq = sqlItemData.getString("scrollReqs");
        if (scrollRq.length() > 0) {
            ret.scrollReqs = new ArrayList<Integer>();
            final String[] scroll = scrollRq.split(",");
            for (String s : scroll) {
                if (s.length() > 1) {
                    ret.scrollReqs.add(Integer.parseInt(s));
                }
            }
        }
        final String consumeItem = sqlItemData.getString("consumeItem");
        if (consumeItem.length() > 0) {
            ret.questItems = new ArrayList<Integer>();
            final String[] scroll = scrollRq.split(",");
            for (String s : scroll) {
                if (s.length() > 1) {
                    ret.questItems.add(Integer.parseInt(s));
                }
            }
        }

        ret.totalprob = sqlItemData.getInt("totalprob");
        final String incRq = sqlItemData.getString("incSkill");
        if (incRq.length() > 0) {
            ret.incSkill = new ArrayList<Integer>();
            final String[] scroll = incRq.split(",");
            for (String s : scroll) {
                if (s.length() > 1) {
                    ret.incSkill.add(Integer.parseInt(s));
                }
            }
        }
        dataCache.put(itemId, ret);
    }

    public MapleInventoryType getInventoryTypeCS(int itemId) {
        if (inventoryTypeCache.containsKey(itemId)) {
            return inventoryTypeCache.get(itemId);
        }
        MapleInventoryType ret;
        String idStr = "0" + String.valueOf(itemId);
        MapleDataDirectoryEntry root = itemData.getRoot();
        for (MapleDataDirectoryEntry topDir : root.getSubdirectories()) {
            for (MapleDataFileEntry iFile : topDir.getFiles()) {
                if (iFile.getName().equals(idStr.substring(0, 4) + ".img")) {
                    ret = MapleInventoryType.getByWZName(topDir.getName());
                    inventoryTypeCache.put(itemId, ret);
                    return ret;
                } else if (iFile.getName().equals(idStr.substring(1) + ".img")) {
                    ret = MapleInventoryType.getByWZName(topDir.getName());
                    inventoryTypeCache.put(itemId, ret);
                    return ret;
                }
            }
        }
        root = chrData.getRoot();
        for (MapleDataDirectoryEntry topDir : root.getSubdirectories()) {
            for (MapleDataFileEntry iFile : topDir.getFiles()) {
                if (iFile.getName().equals(idStr + ".img")) {
                    ret = MapleInventoryType.EQUIP;
                    inventoryTypeCache.put(itemId, ret);
                    return ret;
                }
            }
        }
        ret = MapleInventoryType.UNDEFINED;
        inventoryTypeCache.put(itemId, ret);
        return ret;
    }

    public boolean isCashItem(int itemId) {

        MapleData item = getItemDataCS(itemId);
        if (item == null) {
            return false;
        }
        int pEntry;
        MapleData pData = item.getChildByPath("info/cash");
        if (pData == null) {
            return false;
        }
        try {
            pEntry = MapleDataTool.getInt(pData);
        } catch (Exception e) {
            return false;
        }
        return pEntry == 1;
    }

    protected final MapleData getItemDataCS(final int itemId) {
        MapleData ret = null;
        final String idStr = "0" + String.valueOf(itemId);
        MapleDataDirectoryEntry root = itemData.getRoot();
        for (final MapleDataDirectoryEntry topDir : root.getSubdirectories()) {
            // we should have .img files here beginning with the first 4 IID
            for (final MapleDataFileEntry iFile : topDir.getFiles()) {
                if (iFile.getName().equals(idStr.substring(0, 4) + ".img")) {
                    ret = itemData.getData(topDir.getName() + "/" + iFile.getName());
                    if (ret == null) {
                        return null;
                    }
                    ret = ret.getChildByPath(idStr);
                    return ret;
                } else if (iFile.getName().equals(idStr.substring(1) + ".img")) {
                    return itemData.getData(topDir.getName() + "/" + iFile.getName());
                }
            }
        }
        root = chrData.getRoot();
        for (final MapleDataDirectoryEntry topDir : root.getSubdirectories()) {
            for (final MapleDataFileEntry iFile : topDir.getFiles()) {
                if (iFile.getName().equals(idStr + ".img")) {
                    return chrData.getData(topDir.getName() + "/" + iFile.getName());
                }
            }
        }
        return ret;
    }

    public short getPetFlagInfo(int itemId) {
        if (this.petFlagInfo.containsKey(itemId)) {
            return (this.petFlagInfo.get(itemId));
        }
        short flag = 0;
        if (itemId / 10000 != 500) {
            return flag;
        }
        MapleData item = getItemData(itemId);
        if (item == null) {
            return flag;
        }
        if (MapleDataTool.getIntConvert("info/pickupItem", item, 0) > 0) {
            flag = (short) (flag | 0x1);
        }
        if (MapleDataTool.getIntConvert("info/longRange", item, 0) > 0) {
            flag = (short) (flag | 0x2);
        }
        if (MapleDataTool.getIntConvert("info/pickupAll", item, 0) > 0) {
            flag = (short) (flag | 0x4);
        }

        if (MapleDataTool.getIntConvert("info/sweepForDrop", item, 0) > 0) {
            flag = (short) (flag | 0x10);
        }
        if (MapleDataTool.getIntConvert("info/consumeHP", item, 0) > 0) {
            flag = (short) (flag | 0x20);
        }
        if (MapleDataTool.getIntConvert("info/consumeMP", item, 0) > 0) {
            flag = (short) (flag | 0x40);
        }
        if (MapleDataTool.getIntConvert("info/autoBuff", item, 0) > 0) {
            flag = (short) (flag | 0x200);
        }
        this.petFlagInfo.put(itemId, flag);
        return flag;
    }

    public boolean isNoCursedScroll(int itemId) {
        if (this.noCursedScroll.containsKey(itemId)) {
            return (this.noCursedScroll.get(itemId));
        }
        if (itemId / 10000 != 204) {
            return false;
        }
        boolean noCursed = MapleDataTool.getIntConvert("info/noCursed", getItemData(itemId), 0) > 0;
        this.noCursedScroll.put(itemId, noCursed);
        return noCursed;
    }

    public int getForceUpgrade(int itemId) {
        if (this.forceUpgrade.containsKey(itemId)) {
            return (this.forceUpgrade.get(itemId));
        }
        int upgrade = 0;
        if (itemId / 100 != 20493) {
            return upgrade;
        }
        upgrade = MapleDataTool.getIntConvert("info/forceUpgrade", getItemData(itemId), 1);
        this.forceUpgrade.put(itemId, upgrade);
        return upgrade;
    }

    public int getSuccessRates(int itemId) {
        if (this.successRates.containsKey(itemId)) {
            return (this.successRates.get(itemId));
        }
        int success = 0;
        if (itemId / 10000 != 204) {
            return success;
        }
        success = MapleDataTool.getIntConvert("info/successRates/0", getItemData(itemId), 0);
        this.successRates.put(itemId, success);
        return success;
    }

    public boolean isNegativeScroll(int itemId) {
        if (itemId == 2049116) {
            return false;
        }
        if (this.noNegativeScroll.containsKey(itemId)) {
            return (this.noNegativeScroll.get(itemId));
        }
        if (itemId / 10000 != 204) {
            return false;
        }
        boolean noNegative = MapleDataTool.getIntConvert("info/noNegative", getItemData(itemId), 0) > 0;
        this.noNegativeScroll.put(itemId, noNegative);
        return noNegative;
    }

    public boolean isSuperiorEquip(int itemId) {
        Map<String, Integer> equipStats = getEquipStats(itemId);
        if (equipStats == null) {
            return false;
        }
        return equipStats.containsKey("superiorEqp") && equipStats.get("superiorEqp") == 1;
    }

    public Equip resetEquipStats(Equip oldEquip) {
        Equip newEquip = (Equip) getEquipById(oldEquip.getItemId());
        oldEquip.reset(newEquip);
        return newEquip;
    }

    public int getPetLife(int itemid) {
        if (this.petLifeInfo.containsKey(itemid)) {
            return (this.petLifeInfo.get(itemid));
        }
        if (!GameConstants.isPet(itemid)) {
            return 0;
        }
        MapleData item = getItemData(itemid);
        int life = MapleDataTool.getIntConvert("info/life", item, 0);
        this.petLifeInfo.put(itemid, life);
        return life;
    }

    public int getAndroidType(int itemId) {
        if (this.androidType.containsKey(itemId)) {
            return this.androidType.get(itemId);
        }
        int type = 0;
        if (itemId / 10000 != 166) {
            return type;
        }
        type = MapleDataTool.getIntConvert("info/android", getItemData(itemId), 1);
        this.androidType.put(itemId, type);
        return type;
    }

    public static int getUniqueId(int itemId, MaplePet pet) {
        int uniqueid = -1;
        if (GameConstants.isPet(itemId)) {
            if (pet != null) {
                uniqueid = pet.getUniqueId();
            } else {
                uniqueid = MapleInventoryIdentifier.getInstance();
            }
        } else if (GameConstants.getInventoryType(itemId) == MapleInventoryType.CASH || MapleItemInformationProvider.getInstance().isCash(itemId)) { //less work to do
            uniqueid = MapleInventoryIdentifier.getInstance(); //shouldnt be generated yet, so put it here
        }
        return uniqueid;
    }
}
