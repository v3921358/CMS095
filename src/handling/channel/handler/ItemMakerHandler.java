package handling.channel.handler;

import client.MapleCharacter;
import client.MapleClient;
import client.MapleQuestStatus;
import client.MapleTrait.MapleTraitType;
import client.Skill;
import client.SkillEntry;
import client.SkillFactory;
import client.SkillFactory.CraftingEntry;
import client.inventory.Equip;
import client.inventory.Item;
import client.inventory.ItemFlag;
import client.inventory.MapleImp;
import client.inventory.MapleImp.ImpFlag;
import client.inventory.MapleInventoryType;
import constants.GameConstants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import server.ItemMakerFactory;
import server.ItemMakerFactory.GemCreateEntry;
import server.ItemMakerFactory.ItemMakerCreateEntry;
import server.MapleInventoryManipulator;
import server.MapleItemInformationProvider;
import server.MapleStatEffect;
import server.maps.MapleExtractor;
import server.maps.MapleReactor;
import server.quest.MapleQuest;
import tools.FileoutputUtil;
import tools.packet.MaplePacketCreator;
import tools.Pair;
import tools.Randomizer;
import tools.Triple;
import tools.data.LittleEndianAccessor;

public class ItemMakerHandler {

    private static final Map<String, Integer> craftingEffects = new HashMap<String, Integer>();

    static {
        craftingEffects.put("Effect/BasicEff.img/professions/herbalism", 92000000);
        craftingEffects.put("Effect/BasicEff.img/professions/mining", 92010000);
        craftingEffects.put("Effect/BasicEff.img/professions/herbalismExtract", 92000000);
        craftingEffects.put("Effect/BasicEff.img/professions/miningExtract", 92010000);
        craftingEffects.put("Effect/BasicEff.img/professions/equip_product", 92020000);
        craftingEffects.put("Effect/BasicEff.img/professions/acc_product", 92030000);
        craftingEffects.put("Effect/BasicEff.img/professions/alchemy", 92040000);
    }

    public static enum CraftRanking {

        SOSO(19, 30),
        GOOD(20, 40),
        COOL(21, 50);
        public int i, craft;

        private CraftRanking(int i, int craft) {
            this.i = i;
            this.craft = craft;
        }
    }

    public static final void ItemMaker(final LittleEndianAccessor slea, final MapleClient c) {
        //System.out.println(slea.toString()); //change?
        final int makerType = slea.readInt();

        switch (makerType) {
            case 1: { // Gem
                final int toCreate = slea.readInt();

                if (GameConstants.isGem(toCreate)) {
                    final GemCreateEntry gem = ItemMakerFactory.getInstance().getGemInfo(toCreate);
                    if (gem == null) {
                        return;
                    }
                    if (!hasSkill(c, gem.getReqSkillLevel())) {
                        return; // H4x
                    }
                    if (c.getPlayer().getMeso() < gem.getCost()) {
                        return; // H4x
                    }
                    final int randGemGiven = getRandomGem(gem.getRandomReward());

                    if (c.getPlayer().getInventory(GameConstants.getInventoryType(randGemGiven)).isFull()) {
                        return; // We'll do handling for this later
                    }
                    final int taken = checkRequiredNRemove(c, gem.getReqRecipes());
                    if (taken == 0) {
                        return; // We'll do handling for this later
                    }
                    c.getPlayer().gainMeso(-gem.getCost(), false);
                    MapleInventoryManipulator.addById(c, randGemGiven, (byte) (taken == randGemGiven ? 9 : 1), "Made by Gem " + toCreate + " on " + FileoutputUtil.CurrentReadable_Date()); // Gem is always 1

                    c.getSession().write(MaplePacketCreator.ItemMaker_Success());
                    c.getPlayer().getMap().broadcastMessage(c.getPlayer(), MaplePacketCreator.ItemMaker_Success_3rdParty(c.getPlayer().getId()), false);
                } else if (GameConstants.isOtherGem(toCreate)) {
                    //non-gems that are gems
                    //stim and numEnchanter always 0
                    final GemCreateEntry gem = ItemMakerFactory.getInstance().getGemInfo(toCreate);
                    if (gem == null) {
                        return;
                    }
                    if (!hasSkill(c, gem.getReqSkillLevel())) {
                        return; // H4x
                    }
                    if (c.getPlayer().getMeso() < gem.getCost()) {
                        return; // H4x
                    }

                    if (c.getPlayer().getInventory(GameConstants.getInventoryType(toCreate)).isFull()) {
                        return; // We'll do handling for this later
                    }
                    if (checkRequiredNRemove(c, gem.getReqRecipes()) == 0) {
                        return; // We'll do handling for this later
                    }
                    c.getPlayer().gainMeso(-gem.getCost(), false);
                    if (GameConstants.getInventoryType(toCreate) == MapleInventoryType.EQUIP) {
                        MapleInventoryManipulator.addbyItem(c, MapleItemInformationProvider.getInstance().getEquipById(toCreate));
                    } else {
                        MapleInventoryManipulator.addById(c, toCreate, (byte) 1, "Made by Gem " + toCreate + " on " + FileoutputUtil.CurrentReadable_Date()); // Gem is always 1
                    }

                    c.getSession().write(MaplePacketCreator.ItemMaker_Success());
                    c.getPlayer().getMap().broadcastMessage(c.getPlayer(), MaplePacketCreator.ItemMaker_Success_3rdParty(c.getPlayer().getId()), false);
                } else {
                    final boolean stimulator = slea.readByte() > 0;
                    final int numEnchanter = slea.readInt();

                    final ItemMakerCreateEntry create = ItemMakerFactory.getInstance().getCreateInfo(toCreate);
                    if (create == null) {
                        return;
                    }
                    if (numEnchanter > create.getTUC()) {
                        return; // h4x
                    }
                    if (!hasSkill(c, create.getReqSkillLevel())) {
                        return; // H4x
                    }
                    if (c.getPlayer().getMeso() < create.getCost()) {
                        return; // H4x
                    }
                    if (c.getPlayer().getInventory(GameConstants.getInventoryType(toCreate)).isFull()) {
                        return; // We'll do handling for this later
                    }
                    if (checkRequiredNRemove(c, create.getReqItems()) == 0) {
                        return; // We'll do handling for this later
                    }
                    c.getPlayer().gainMeso(-create.getCost(), false);

                    final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
                    final Equip toGive = (Equip) ii.getEquipById(toCreate);

                    if (stimulator || numEnchanter > 0) {
                        if (c.getPlayer().haveItem(create.getStimulator(), 1, false, true)) {
                            ii.randomizeStats_Above(toGive);
                            MapleInventoryManipulator.removeById(c, MapleInventoryType.ETC, create.getStimulator(), 1, false, false);
                        }
                        for (int i = 0; i < numEnchanter; i++) {
                            final int enchant = slea.readInt();
                            if (c.getPlayer().haveItem(enchant, 1, false, true)) {
                                final Map<String, Integer> stats = ii.getEquipStats(enchant);
                                if (stats != null) {
                                    addEnchantStats(stats, toGive);
                                    MapleInventoryManipulator.removeById(c, MapleInventoryType.ETC, enchant, 1, false, false);
                                }
                            }
                        }
                    }
                    if (!stimulator || Randomizer.nextInt(10) != 0) {
                        MapleInventoryManipulator.addbyItem(c, toGive);
                        c.getPlayer().getMap().broadcastMessage(c.getPlayer(), MaplePacketCreator.ItemMaker_Success_3rdParty(c.getPlayer().getId()), false);
                    } else {
                        c.getPlayer().dropMessage(5, "The item was overwhelmed by the stimulator.");
                    }
                    c.getSession().write(MaplePacketCreator.ItemMaker_Success());

                }
                break;
            }
            case 3: { // Making Crystals
                final int etc = slea.readInt();
                if (c.getPlayer().haveItem(etc, 100, false, true)) {
                    MapleInventoryManipulator.addById(c, getCreateCrystal(etc), (short) 1, "Made by Maker " + etc + " on " + FileoutputUtil.CurrentReadable_Date());
                    MapleInventoryManipulator.removeById(c, MapleInventoryType.ETC, etc, 100, false, false);

                    c.getSession().write(MaplePacketCreator.ItemMaker_Success());
                    c.getPlayer().getMap().broadcastMessage(c.getPlayer(), MaplePacketCreator.ItemMaker_Success_3rdParty(c.getPlayer().getId()), false);
                }
                break;
            }
            case 4: { // Disassembling EQ.
                final int itemId = slea.readInt();
                //c.getPlayer().updateTick(slea.readInt());
                slea.readInt();
                final byte slot = (byte) slea.readInt();

                final Item toUse = c.getPlayer().getInventory(MapleInventoryType.EQUIP).getItem(slot);
                if (toUse == null || toUse.getItemId() != itemId || toUse.getQuantity() < 1) {
                    return;
                }
                final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();

                if (!ii.isDropRestricted(itemId) && !ii.isAccountShared(itemId)) {
                    final int[] toGive = getCrystal(itemId, ii.getReqLevel(itemId));
                    MapleInventoryManipulator.addById(c, toGive[0], (byte) toGive[1], "Made by disassemble " + itemId + " on " + FileoutputUtil.CurrentReadable_Date());
                    MapleInventoryManipulator.removeFromSlot(c, MapleInventoryType.EQUIP, slot, (byte) 1, false);
                }
                c.getSession().write(MaplePacketCreator.ItemMaker_Success());
                c.getPlayer().getMap().broadcastMessage(c.getPlayer(), MaplePacketCreator.ItemMaker_Success_3rdParty(c.getPlayer().getId()), false);
                break;
            }
        }
    }

    private static final int getCreateCrystal(final int etc) {
        int itemid;
        final short level = MapleItemInformationProvider.getInstance().getItemMakeLevel(etc);

        if (level >= 31 && level <= 50) {
            itemid = 4260000;
        } else if (level >= 51 && level <= 60) {
            itemid = 4260001;
        } else if (level >= 61 && level <= 70) {
            itemid = 4260002;
        } else if (level >= 71 && level <= 80) {
            itemid = 4260003;
        } else if (level >= 81 && level <= 90) {
            itemid = 4260004;
        } else if (level >= 91 && level <= 100) {
            itemid = 4260005;
        } else if (level >= 101 && level <= 110) {
            itemid = 4260006;
        } else if (level >= 111 && level <= 120) {
            itemid = 4260007;
        } else if (level >= 121) {
            itemid = 4260008;
        } else {
            throw new RuntimeException("Invalid Item Maker id");
        }
        return itemid;
    }

    private static final int[] getCrystal(final int itemid, final int level) {
        int[] all = new int[2];
        all[0] = -1;
        if (level >= 31 && level <= 50) {
            all[0] = 4260000;
        } else if (level >= 51 && level <= 60) {
            all[0] = 4260001;
        } else if (level >= 61 && level <= 70) {
            all[0] = 4260002;
        } else if (level >= 71 && level <= 80) {
            all[0] = 4260003;
        } else if (level >= 81 && level <= 90) {
            all[0] = 4260004;
        } else if (level >= 91 && level <= 100) {
            all[0] = 4260005;
        } else if (level >= 101 && level <= 110) {
            all[0] = 4260006;
        } else if (level >= 111 && level <= 120) {
            all[0] = 4260007;
        } else if (level >= 121 && level <= 200) {
            all[0] = 4260008;
        } else {
            throw new RuntimeException("Invalid Item Maker type" + level);
        }
        if (GameConstants.isWeapon(itemid) || GameConstants.isOverall(itemid)) {
            all[1] = Randomizer.rand(5, 11);
        } else {
            all[1] = Randomizer.rand(3, 7);
        }
        return all;
    }

    private static final void addEnchantStats(final Map<String, Integer> stats, final Equip item) {
        Integer s = stats.get("PAD");
        if (s != null && s != 0) {
            item.setWatk((short) (item.getWatk() + s));
        }
        s = stats.get("MAD");
        if (s != null && s != 0) {
            item.setMatk((short) (item.getMatk() + s));
        }
        s = stats.get("ACC");
        if (s != null && s != 0) {
            item.setAcc((short) (item.getAcc() + s));
        }
        s = stats.get("EVA");
        if (s != null && s != 0) {
            item.setAvoid((short) (item.getAvoid() + s));
        }
        s = stats.get("Speed");
        if (s != null && s != 0) {
            item.setSpeed((short) (item.getSpeed() + s));
        }
        s = stats.get("Jump");
        if (s != null && s != 0) {
            item.setJump((short) (item.getJump() + s));
        }
        s = stats.get("MaxHP");
        if (s != null && s != 0) {
            item.setHp((short) (item.getHp() + s));
        }
        s = stats.get("MaxMP");
        if (s != null && s != 0) {
            item.setMp((short) (item.getMp() + s));
        }
        s = stats.get("STR");
        if (s != null && s != 0) {
            item.setStr((short) (item.getStr() + s));
        }
        s = stats.get("DEX");
        if (s != null && s != 0) {
            item.setDex((short) (item.getDex() + s));
        }
        s = stats.get("INT");
        if (s != null && s != 0) {
            item.setInt((short) (item.getInt() + s));
        }
        s = stats.get("LUK");
        if (s != null && s != 0) {
            item.setLuk((short) (item.getLuk() + s));
        }
        s = stats.get("randOption");
        if (s != null && s != 0) {
            final int ma = item.getMatk(), wa = item.getWatk();
            if (wa > 0) {
                item.setWatk((short) (Randomizer.nextBoolean() ? (wa + s) : (wa - s)));
            }
            if (ma > 0) {
                item.setMatk((short) (Randomizer.nextBoolean() ? (ma + s) : (ma - s)));
            }
        }
        s = stats.get("randStat");
        if (s != null && s != 0) {
            final int str = item.getStr(), dex = item.getDex(), luk = item.getLuk(), int_ = item.getInt();
            if (str > 0) {
                item.setStr((short) (Randomizer.nextBoolean() ? (str + s) : (str - s)));
            }
            if (dex > 0) {
                item.setDex((short) (Randomizer.nextBoolean() ? (dex + s) : (dex - s)));
            }
            if (int_ > 0) {
                item.setInt((short) (Randomizer.nextBoolean() ? (int_ + s) : (int_ - s)));
            }
            if (luk > 0) {
                item.setLuk((short) (Randomizer.nextBoolean() ? (luk + s) : (luk - s)));
            }
        }
    }

    private static final int getRandomGem(final List<Pair<Integer, Integer>> rewards) {
        int itemid;
        final List<Integer> items = new ArrayList<Integer>();

        for (final Pair p : rewards) {
            itemid = (Integer) p.getLeft();
            for (int i = 0; i < (Integer) p.getRight(); i++) {
                items.add(itemid);
            }
        }
        return items.get(Randomizer.nextInt(items.size()));
    }

    private static final int checkRequiredNRemove(final MapleClient c, final List<Pair<Integer, Integer>> recipe) {
        int itemid = 0;
        for (final Pair<Integer, Integer> p : recipe) {
            if (!c.getPlayer().haveItem(p.getLeft(), p.getRight(), false, true)) {
                return 0;
            }
        }
        for (final Pair<Integer, Integer> p : recipe) {
            itemid = p.getLeft();
            MapleInventoryManipulator.removeById(c, GameConstants.getInventoryType(itemid), itemid, p.getRight(), false, false);
        }
        return itemid;
    }

    private static final boolean hasSkill(final MapleClient c, final int reqlvl) {
        return c.getPlayer().getSkillLevel(SkillFactory.getSkill(c.getPlayer().getStat().getSkillByJob(1007, c.getPlayer().getJob()))) >= reqlvl;
    }

    public static final void UseRecipe(final LittleEndianAccessor slea, final MapleClient c, final MapleCharacter chr) {
        if (chr == null || !chr.isAlive() || chr.getMap() == null || chr.hasBlockedInventory()) {
            c.getSession().write(MaplePacketCreator.enableActions());
            c.getPlayer().marriage();
            return;
        }
        //c.getPlayer().updateTick(slea.readInt());
        slea.readInt();
        final byte slot = (byte) slea.readShort();
        final int itemId = slea.readInt();
        final Item toUse = chr.getInventory(MapleInventoryType.USE).getItem(slot);

        if (toUse == null || toUse.getQuantity() < 1 || toUse.getItemId() != itemId || itemId / 10000 != 251) {
            c.getSession().write(MaplePacketCreator.enableActions());
            c.getPlayer().marriage();
            return;
        }
        if (MapleItemInformationProvider.getInstance().getItemEffect(toUse.getItemId()).applyTo(chr)) {
            MapleInventoryManipulator.removeFromSlot(c, MapleInventoryType.USE, slot, (short) 1, false);
        }
    }

    public static final void MakeExtractor(final LittleEndianAccessor slea, final MapleClient c, final MapleCharacter chr) {
        if (chr == null || !chr.isAlive() || chr.getMap() == null || chr.hasBlockedInventory()) {
            c.getSession().write(MaplePacketCreator.enableActions());
            c.getPlayer().marriage();
            return;
        }
        final int itemId = slea.readInt();
        if (itemId == -1) {
            c.getSession().write(MaplePacketCreator.enableActions());
            c.getPlayer().marriage();
            return;
        }
        final int fee = slea.readInt();
        final Item toUse = chr.getInventory(MapleInventoryType.SETUP).findById(itemId);
        if (toUse == null || toUse.getQuantity() < 1 || itemId / 10000 != 304 || fee <= 0 || chr.getExtractor() != null || !chr.getMap().isTown()) {
            c.getSession().write(MaplePacketCreator.enableActions());
            c.getPlayer().marriage();
            return;
        }
        chr.setExtractor(new MapleExtractor(chr, itemId, fee, chr.getFH())); //no clue about time left
        chr.getMap().spawnExtractor(chr.getExtractor());

        //expiry date ..
        MapleInventoryManipulator.removeFromSlot(c, MapleInventoryType.SETUP, toUse.getPosition(), (short) 1, false);
    }

    public static final void UseBag(final LittleEndianAccessor slea, final MapleClient c, final MapleCharacter chr) {
        if (chr == null || !chr.isAlive() || chr.getMap() == null || chr.hasBlockedInventory()) {
            c.getSession().write(MaplePacketCreator.enableActions());
            c.getPlayer().marriage();
            return;
        }
        //c.getPlayer().updateTick(slea.readInt());
        slea.readInt();
        final byte slot = (byte) slea.readShort();
        final int itemId = slea.readInt();
        final Item toUse = chr.getInventory(MapleInventoryType.ETC).getItem(slot);

        if (toUse == null || toUse.getQuantity() < 1 || toUse.getItemId() != itemId || itemId / 10000 != 433) {
            c.getSession().write(MaplePacketCreator.enableActions());
            c.getPlayer().marriage();
            return;
        }
        boolean firstTime = !chr.getExtendedSlots().contains(itemId);
        if (firstTime) {
            chr.getExtendedSlots().add(itemId);
            chr.changedExtended();
            short flag = toUse.getFlag();
            flag |= ItemFlag.LOCK.getValue();
            flag |= ItemFlag.UNTRADEABLE.getValue();
            toUse.setFlag(flag);
            c.getSession().write(MaplePacketCreator.updateSpecialItemUse(toUse, (byte) 4, toUse.getPosition(), true, chr));
        }
        c.getPlayer().marriage();
        c.getSession().write(MaplePacketCreator.openBag(chr.getExtendedSlots().indexOf(itemId), itemId, firstTime));
        c.getSession().write(MaplePacketCreator.enableActions());
        c.getPlayer().marriage();
    }

    public static final void StartHarvest(final LittleEndianAccessor slea, final MapleClient c, final MapleCharacter chr) {
        //its ok if a hacker bypasses this as we do everything in the reactor anyway
        final MapleReactor reactor = c.getPlayer().getMap().getReactorByOid(slea.readInt());
        if (reactor == null || !reactor.isAlive() || reactor.getReactorId() > 200011 || chr.getStat().harvestingTool <= 0 || reactor.getTruePosition().distanceSq(chr.getTruePosition()) > 10000 || c.getPlayer().getFatigue() >= 200) {
            return;
        }
        Item item = c.getPlayer().getInventory(MapleInventoryType.EQUIP).getItem((short) c.getPlayer().getStat().harvestingTool);
        if (item == null || ((Equip) item).getDurability() == 0) {
            c.getPlayer().getStat().handleProfessionTool(c.getPlayer());
            return;
        }
        MapleQuestStatus marr = c.getPlayer().getQuestNAdd(MapleQuest.getInstance(GameConstants.HARVEST_TIME));
        if (marr.getCustomData() == null) {
            marr.setCustomData("0");
        }
        long lastTime = Long.parseLong(marr.getCustomData());
        if (lastTime + (5000) > System.currentTimeMillis()) {
            c.getSession().write(MaplePacketCreator.harvestMessage(reactor.getObjectId(), 9));
        } else {
            marr.setCustomData(String.valueOf(System.currentTimeMillis()));
            c.getSession().write(MaplePacketCreator.harvestMessage(reactor.getObjectId(), 11)); //ok to harvest, gogo
            c.getPlayer().getMap().broadcastMessage(chr, MaplePacketCreator.showHarvesting(chr.getId(), item.getItemId()), false);
        }
    }

    public static final void StopHarvest(final LittleEndianAccessor slea, final MapleClient c, final MapleCharacter chr) {

    }

    public static final void ProfessionInfo(final LittleEndianAccessor slea, final MapleClient c) { //so pointless
        try {
            String asdf = slea.readMapleAsciiString();
            int level1 = slea.readInt();
            int level2 = slea.readInt();
            if (asdf.equalsIgnoreCase("honorLeveling")) {
                c.getSession().write(MaplePacketCreator.professionInfo(asdf, level1, level2, 500));
            } else {
                c.getSession().write(MaplePacketCreator.professionInfo(asdf, level1, level2, Math.max(0, 100 - (level1 + 1 - c.getPlayer().getProfessionLevel(Integer.parseInt(asdf))) * 20)));
            }
        } catch (NumberFormatException nfe) {
        } //idc
    }

    public static final void CraftEffect(final LittleEndianAccessor slea, final MapleClient c, final MapleCharacter chr) {
        if (chr.getMapId() != 910001000 && chr.getMap().getExtractorSize() <= 0) {
            return; //ardent mill
        }
        final String effect = slea.readMapleAsciiString();
        final Integer profession = craftingEffects.get(effect);
        if (profession != null && (c.getPlayer().getProfessionLevel(profession.intValue()) > 0 || (profession == 92040000 && chr.getMap().getExtractorSize() > 0))) {
            int time = slea.readInt();
            if (time > 6000 || time < 3000) {
                time = 4000;
            }
            c.getSession().write(MaplePacketCreator.showOwnCraftingEffect(effect, time, effect.endsWith("Extract") ? 1 : 0));
            chr.getMap().broadcastMessage(chr, MaplePacketCreator.showCraftingEffect(chr.getId(), effect, time, effect.endsWith("Extract") ? 1 : 0), false);
        }
    }

    public static final void CraftMake(final LittleEndianAccessor slea, final MapleClient c, final MapleCharacter chr) {
        if (chr.getMapId() != 910001000 && chr.getMap().getExtractorSize() <= 0) {
            return; //ardent mill
        }
        final int something = slea.readInt(); //no clue what it is, but its between 288 and 305..
        //if (something >= 280 && something <= 310) {
        int time = slea.readInt();
        if (time > 6000 || time < 3000) {
            time = 4000;
        }
        chr.getMap().broadcastMessage(MaplePacketCreator.craftMake(chr.getId(), something, time));
        //}
    }

    public static final void CraftComplete(final LittleEndianAccessor slea, final MapleClient c, final MapleCharacter chr) {
        if (chr.getFatigue() >= 200) {
            chr.dropMessage(-5, "疲勞度已满。");
            c.getSession().write(MaplePacketCreator.enableActions());
            c.getPlayer().marriage();
            return;
        }

        final int craftID = slea.readInt();
        final CraftingEntry ce = SkillFactory.getCraft(craftID);
        final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();

        if ((chr.getMapId() != 910001000 && (craftID != 92049000 || chr.getMap().getExtractorSize() <= 0)) || ce == null || chr.getFatigue() >= 200) {
            c.getSession().write(MaplePacketCreator.enableActions());
            c.getPlayer().marriage();
            return;
        }
        final int theLevl = c.getPlayer().getProfessionLevel((craftID / 10000) * 10000);
        if (theLevl <= 0 && craftID != 92049000) {
            c.getSession().write(MaplePacketCreator.enableActions());
            c.getPlayer().marriage();
            return;
        }
        int toGet = 0, expGain = 0, fatigue = 0;
        short quantity = 1;
        CraftRanking cr = CraftRanking.GOOD;
        if (c.getPlayer().getInventory(MapleInventoryType.EQUIP).getNumFreeSlot() < 1 || c.getPlayer().getInventory(MapleInventoryType.USE).getNumFreeSlot() < 1 || c.getPlayer().getInventory(MapleInventoryType.SETUP).getNumFreeSlot() < 1 || c.getPlayer().getInventory(MapleInventoryType.ETC).getNumFreeSlot() < 1 || c.getPlayer().getInventory(MapleInventoryType.CASH).getNumFreeSlot() < 1) {
            c.getPlayer().dropMessage(1, "请检查你的背包是否已满。");
            c.getSession().write(MaplePacketCreator.enableActions());
            c.getPlayer().marriage();
            //chr.getMap().broadcastMessage(MaplePacketCreator.craftFinished(chr.getId(), craftID, 19, 0, 0, 0));
            return;
        }

        if (craftID == 92049000) { //disassembling
            final int extractorId = slea.readInt();
            final int itemId = slea.readInt();
            final long invId = slea.readLong();
            final int reqLevel = ii.getReqLevel(itemId);
            //if (invId < 0) {
            //    chr.dropMessage(1, "请在栏位按向上整理后再試。");
           //     c.getSession().write(MaplePacketCreator.enableActions());
           //     c.getPlayer().marriage();
           //     return;
           // }
            final Item item = chr.getInventory(MapleInventoryType.EQUIP).findById(itemId);//chr.getInventory(MapleInventoryType.EQUIP).findByInventoryId(invId, itemId);
            if (item == null || chr.getInventory(MapleInventoryType.ETC).isFull()) {
                c.getSession().write(MaplePacketCreator.enableActions());
                c.getPlayer().marriage();
                return;
            }
            if (extractorId <= 0 && (theLevl == 0 || theLevl < (reqLevel > 130 ? 6 : ((reqLevel - 30) / 20)))) {
                c.getSession().write(MaplePacketCreator.enableActions());
                c.getPlayer().marriage();
                return;
            } else if (extractorId > 0) {
                final MapleCharacter extract = chr.getMap().getCharacterById(extractorId);
                if (extract == null || extract.getExtractor() == null) {
                    c.getSession().write(MaplePacketCreator.enableActions());
                    c.getPlayer().marriage();
                    return;
                }
                final MapleExtractor extractor = extract.getExtractor();
                if (extractor.owner != chr.getId()) { //fee
                    if (chr.getMeso() < extractor.fee) {
                        c.getSession().write(MaplePacketCreator.enableActions());
                        c.getPlayer().marriage();
                        return;
                    }
                    final MapleStatEffect eff = ii.getItemEffect(extractor.itemId);
                    if (eff != null && eff.getUseLevel() < reqLevel) {
                        c.getSession().write(MaplePacketCreator.enableActions());
                        c.getPlayer().marriage();
                        return;
                    }
                    chr.gainMeso(-extractor.fee, true);
                    final MapleCharacter owner = chr.getMap().getCharacterById(extractor.owner);
                    if (owner != null && owner.getMeso() < (Integer.MAX_VALUE - extractor.fee)) {
                        owner.gainMeso(extractor.fee, false);
                    }
                }
            }
            toGet = 4021016;
            quantity = (short) Randomizer.rand(3, GameConstants.isWeapon(itemId) || GameConstants.isOverall(itemId) ? 11 : 7);
            if (reqLevel <= 60) {
                toGet = 4021013;
            } else if (reqLevel <= 90) {
                toGet = 4021014;
            } else if (reqLevel <= 120) {
                toGet = 4021015;
            }
            if (quantity <= 5) {
                cr = CraftRanking.SOSO;
            }
            if (Randomizer.nextInt(5) == 0 && toGet != 4021016) {
                toGet++;
                quantity = 1;
                cr = CraftRanking.COOL;
            }
            fatigue = 3;
            MapleInventoryManipulator.addById(c, toGet, quantity, "Made by disassemble " + itemId + " on " + FileoutputUtil.CurrentReadable_Date());
            MapleInventoryManipulator.removeFromSlot(c, MapleInventoryType.EQUIP, item.getPosition(), (byte) 1, false);
        } else if (craftID == 92049001) { //fusing. /mindfuck
            final int itemId = slea.readInt();
            final long invId1 = slea.readLong();
            final long invId2 = slea.readLong();
            final int reqLevel = ii.getReqLevel(itemId);
            if (invId1 < 0) {
                chr.dropMessage(1, "请在栏位按向上整理后再試。");
                c.getSession().write(MaplePacketCreator.enableActions());
                c.getPlayer().marriage();
                return;
            }
            if (invId2 < 0) {
                chr.dropMessage(1, "请在栏位按向上整理后再試。");
                c.getSession().write(MaplePacketCreator.enableActions());
                c.getPlayer().marriage();
                return;
            }
            Equip item1 = (Equip) chr.getInventory(MapleInventoryType.EQUIP).findByInventoryIdOnly(invId1, itemId);
            Equip item2 = (Equip) chr.getInventory(MapleInventoryType.EQUIP).findByInventoryIdOnly(invId2, itemId);
            for (short i = 0; i < chr.getInventory(MapleInventoryType.EQUIP).getSlotLimit(); i++) {
                Item item = chr.getInventory(MapleInventoryType.EQUIP).getItem(i);
                if (item != null && item.getItemId() == itemId && item != item1 && item != item2) {
                    if (item1 == null) {
                        item1 = (Equip) item;
                    } else if (item2 == null) {
                        item2 = (Equip) item;
                        break;
                    }
                }
            }
            if (item1 == null || item2 == null) {
                c.getSession().write(MaplePacketCreator.enableActions());
                c.getPlayer().marriage();
                return;
            }
            if (theLevl < (reqLevel > 130 ? 6 : ((reqLevel - 30) / 20))) {
                c.getSession().write(MaplePacketCreator.enableActions());
                c.getPlayer().marriage();
                return;
            }
            int potentialState = 5, potentialChance = (theLevl * 2);
            int toRemove;
            if (reqLevel <= 30) {
                toRemove = 1;
            } else if (reqLevel <= 70) {
                toRemove = 2;
            } else if (reqLevel <= 120) {
                toRemove = 3;
            } else {
                toRemove = 4;
            }
            if (!chr.haveItem(4021017, toRemove)) {
                chr.dropMessage(5, "合成装备需要的煉金術師之石不够，当前需要" + toRemove + "个。");
                c.getSession().write(MaplePacketCreator.enableActions());
                c.getPlayer().marriage();
                return;
            }

            if (item1.getState() > 0 && item2.getState() > 0) {
                potentialChance = 100;
            } else if (item1.getState() > 0 || item2.getState() > 0) {
                potentialChance *= 2;
            } else if (item1.getState() == 0 && item2.getState() == 0) {
                potentialChance = 0;
            }
            if (item1.getState() == item2.getState() && item1.getState() > 5) {
                potentialState = item1.getState();
            }
            //use average stats if scrolled.
            Equip newEquip = ii.fuse(item1.getLevel() > 0 ? (Equip) ii.getEquipById(itemId) : item1, item2.getLevel() > 0 ? (Equip) ii.getEquipById(itemId) : item2);
            final int newStat = ii.getTotalStat(newEquip);
            if (newStat > ii.getTotalStat(item1) || newStat > ii.getTotalStat(item2)) {
                cr = CraftRanking.COOL;
            } else if (newStat < ii.getTotalStat(item1) || newStat < ii.getTotalStat(item2)) {
                cr = CraftRanking.SOSO;
            }
            if (potentialChance != 0) {
                if (Randomizer.nextInt(100) < (newEquip.getUpgradeSlots() > 0 || potentialChance >= 100 ? potentialChance : (potentialChance / 2))) {
                    newEquip.resetPotential_Fuse(theLevl > 5, potentialState);
                }
            }

            newEquip.setFlag((short) ItemFlag.CRAFTED.getValue());
            newEquip.setOwner(chr.getName());
            toGet = newEquip.getItemId();
            expGain = (60 - ((theLevl - 1) * 2)) * 2;
            fatigue = 3;
            MapleInventoryManipulator.removeFromSlot(c, MapleInventoryType.EQUIP, item1.getPosition(), (byte) 1, false);
            MapleInventoryManipulator.removeFromSlot(c, MapleInventoryType.EQUIP, item2.getPosition(), (byte) 1, false);
            MapleInventoryManipulator.removeById(c, MapleInventoryType.ETC, 4021017, toRemove, false, false);
            MapleInventoryManipulator.addbyItem(c, newEquip);
        } else {
            if (ce.needOpenItem && chr.getSkillLevel(craftID) <= 0) {
                c.getSession().write(MaplePacketCreator.enableActions());
                c.getPlayer().marriage();
                return;
            }

            for (Entry<Integer, Integer> e : ce.reqItems.entrySet()) {
                if (!chr.haveItem(e.getKey(), e.getValue())) {
                    c.getSession().write(MaplePacketCreator.enableActions());
                    c.getPlayer().marriage();
                    return;
                }
            }
            for (Triple<Integer, Integer, Integer> i : ce.targetItems) {
                if (!MapleInventoryManipulator.checkSpace(c, i.left, i.mid, "")) {
                    c.getSession().write(MaplePacketCreator.enableActions());
                    c.getPlayer().marriage();
                    return;
                }
            }
            for (Entry<Integer, Integer> e : ce.reqItems.entrySet()) {
                MapleInventoryManipulator.removeById(c, GameConstants.getInventoryType(e.getKey()), e.getKey(), e.getValue(), false, false);
            }
            if (Randomizer.nextInt(100) < (100 - (ce.reqSkillLevel - theLevl) * 20) || (craftID / 10000 <= 9201)) {
                final Map<Skill, SkillEntry> sa = new HashMap<>();
                while (true) {
                    boolean passed = false;
                    for (Triple<Integer, Integer, Integer> i : ce.targetItems) {
                        if (Randomizer.nextInt(100) < i.right) {
                            toGet = i.left;
                            quantity = i.mid.shortValue();
                            Item receive = null;
                            if (GameConstants.getInventoryType(toGet) == MapleInventoryType.EQUIP) {
                                Equip first = (Equip) ii.getEquipById(toGet);
                                if (Randomizer.nextInt(100) < (theLevl * 2)) {
                                    first = (Equip) ii.randomizeStats(first);
                                    cr = CraftRanking.COOL;
                                }
                                if (Randomizer.nextInt(100) < (theLevl * (first.getUpgradeSlots() > 0 ? 2 : 1))) {
                                    first.resetPotential();
                                    cr = CraftRanking.COOL;
                                }
                                receive = first;
                                receive.setFlag((short) ItemFlag.CRAFTED.getValue());
                            } else {
                                receive = new Item(toGet, (short) 0, quantity, (short) (ItemFlag.CRAFTED_USE.getValue()));
                            }
                            if (ce.period > 0) {
                                receive.setExpiration(System.currentTimeMillis() + (ce.period * 60000)); //period is in minutes
                            }
                            receive.setOwner(chr.getName());
                            receive.setGMLog("Crafted from " + craftID + " on " + FileoutputUtil.CurrentReadable_Date());
                            if (craftID != 92020401 && craftID != 92020402 && craftID != 92020403 && craftID != 92020404) {
                                chr.changeSkillLevel(SkillFactory.getCraft(craftID), 0, (byte) 0, -1);
                            }
                            MapleInventoryManipulator.addFromDrop(c, receive, true);
                            if (ce.needOpenItem) {
                                byte mLevel = chr.getMasterLevel(craftID);
                                if (mLevel == 1) {
                                    sa.put(ce, new SkillEntry(0, (byte) 0, SkillFactory.getDefaultSExpiry(ce)));
                                    //chr.changeSkillLevel(ce, (short) 0, (byte) 0);                            chr.changeSkillLevel(ce, 0, (byte) 0);
                                } else if (mLevel > 1) {
                                    sa.put(ce, new SkillEntry(Integer.MAX_VALUE, (byte) (chr.getMasterLevel(craftID) - 1), SkillFactory.getDefaultSExpiry(ce)));
                                    //chr.changeSkillLevel(ce, Integer.MAX_VALUE, (byte) (chr.getMasterLevel(craftID) - 1));
                                }
                            }
                            fatigue = ce.incFatigability;
                            expGain = ce.incSkillProficiency == 0 ? (((fatigue * 20) - (ce.reqSkillLevel - theLevl) * 2) /* * 2*/) : ce.incSkillProficiency;
                            chr.getTrait(MapleTraitType.craft).addExp(cr.craft, chr);
                            passed = true;
                            break;
                        }
                    }
                    if (passed) {
                        break;
                    }
                }

            } else {
                quantity = 0;
                cr = CraftRanking.SOSO;
            }
        }
        if (expGain > 0 && theLevl < 10) {
            expGain *= chr.getClient().getChannelServer().getTraitRate();
            if (Randomizer.nextInt(100) < chr.getTrait(MapleTraitType.craft).getLevel() / 5) {
                expGain *= 2;
            }
            String s = "煉金術";
            switch (craftID / 10000) {
                case 9200:
                    s = "採藥";
                    break;
                case 9201:
                    s = "採礦";
                    break;
                case 9202:
                    s = "装备製作";
                    break;
                case 9203:
                    s = "饰品製作";
                    break;
            }
            chr.dropMessage(-5, s + "的熟練度提高了。 (+" + expGain + ")");
            if (chr.addProfessionExp((craftID / 10000) * 10000, expGain)) {
                chr.dropMessage(-5, s + "的等級提升了。");
            }
        } else {
            expGain = 0;
        }
        MapleQuest.getInstance(2550).forceStart(c.getPlayer(), 9031000, "1"); //removes tutorial stuff
        chr.setFatigue(Math.min((byte) (chr.getFatigue() + fatigue), 100)); //设置疲劳
        chr.getMap().broadcastMessage(MaplePacketCreator.craftFinished(chr.getId(), craftID, cr.i, toGet, quantity, expGain));
    }

    public static final void UsePot(final LittleEndianAccessor slea, final MapleClient c) {
        final int itemid = slea.readInt();
        final Item slot = c.getPlayer().getInventory(MapleInventoryType.USE).getItem(slea.readShort());
        if (slot == null || slot.getQuantity() <= 0 || slot.getItemId() != itemid || itemid / 10000 != 244 || MapleItemInformationProvider.getInstance().getPot(itemid) == null) {
            c.getSession().write(MaplePacketCreator.enableActions());
            c.getPlayer().marriage();
            return;
        }
        c.getSession().write(MaplePacketCreator.enableActions());
        c.getPlayer().marriage();
        for (int i = 0; i < c.getPlayer().getImps().length; i++) {
            if (c.getPlayer().getImps()[i] == null) {
                c.getPlayer().getImps()[i] = new MapleImp(itemid);
                c.getSession().write(MaplePacketCreator.updateImp(c.getPlayer().getImps()[i], ImpFlag.SUMMONED.getValue(), i, false));
                MapleInventoryManipulator.removeFromSlot(c, MapleInventoryType.USE, slot.getPosition(), (short) 1, false, false);
                return;
            }
        }

    }

    public static final void ClearPot(final LittleEndianAccessor slea, final MapleClient c) {
        final int index = slea.readInt() - 1;
        if (index < 0 || index >= c.getPlayer().getImps().length || c.getPlayer().getImps()[index] == null) {
            c.getSession().write(MaplePacketCreator.enableActions());
            c.getPlayer().marriage();
            return;
        }
        c.getSession().write(MaplePacketCreator.updateImp(c.getPlayer().getImps()[index], ImpFlag.REMOVED.getValue(), index, false));
        c.getPlayer().getImps()[index] = null;
    }

    public static final void FeedPot(final LittleEndianAccessor slea, final MapleClient c) {
        final int itemid = slea.readInt();
        final Item slot = c.getPlayer().getInventory(GameConstants.getInventoryType(itemid)).getItem((short) slea.readInt());
        if (slot == null || slot.getQuantity() <= 0 || slot.getItemId() != itemid) {
            c.getSession().write(MaplePacketCreator.enableActions());
            c.getPlayer().marriage();
            return;
        }
        final int level = GameConstants.getInventoryType(itemid) == MapleInventoryType.ETC ? MapleItemInformationProvider.getInstance().getItemMakeLevel(itemid) : MapleItemInformationProvider.getInstance().getReqLevel(itemid);
        if (level <= 0 || level < (Math.min(120, c.getPlayer().getLevel()) - 50) || (GameConstants.getInventoryType(itemid) != MapleInventoryType.ETC && GameConstants.getInventoryType(itemid) != MapleInventoryType.EQUIP)) {
            c.getPlayer().dropMessage(1, "这个項目必須在妳的50个等級之內。");
            c.getSession().write(MaplePacketCreator.enableActions());
            c.getPlayer().marriage();
            return;
        }
        final int index = slea.readInt() - 1;
        if (index < 0 || index >= c.getPlayer().getImps().length || c.getPlayer().getImps()[index] == null || c.getPlayer().getImps()[index].getLevel() >= (MapleItemInformationProvider.getInstance().getPot(c.getPlayer().getImps()[index].getItemId()).right - 1) || c.getPlayer().getImps()[index].getState() != 1) {
            c.getSession().write(MaplePacketCreator.enableActions());
            c.getPlayer().marriage();
            return;
        }
        int mask = ImpFlag.FULLNESS.getValue();
        mask |= ImpFlag.FULLNESS_2.getValue();
        mask |= ImpFlag.UPDATE_TIME.getValue();
        mask |= ImpFlag.AWAKE_TIME.getValue();
        //this is where the magic happens
        c.getPlayer().getImps()[index].setFullness(c.getPlayer().getImps()[index].getFullness() + (100 * (GameConstants.getInventoryType(itemid) == MapleInventoryType.EQUIP ? 2 : 1)));
        if (Randomizer.nextBoolean()) {
            mask |= ImpFlag.CLOSENESS.getValue();
            c.getPlayer().getImps()[index].setCloseness(c.getPlayer().getImps()[index].getCloseness() + 1 + (Randomizer.nextInt(5 * (GameConstants.getInventoryType(itemid) == MapleInventoryType.EQUIP ? 2 : 1))));
        } else if (Randomizer.nextInt(5) == 0) { //1/10 chance of sickness
            c.getPlayer().getImps()[index].setState(4); //sick
            mask |= ImpFlag.STATE.getValue();
        }
        if (c.getPlayer().getImps()[index].getFullness() >= 1000) {
            c.getPlayer().getImps()[index].setState(1);
            c.getPlayer().getImps()[index].setFullness(0);
            c.getPlayer().getImps()[index].setLevel(c.getPlayer().getImps()[index].getLevel() + 1);
            mask |= ImpFlag.SUMMONED.getValue();
            if (c.getPlayer().getImps()[index].getLevel() >= (MapleItemInformationProvider.getInstance().getPot(c.getPlayer().getImps()[index].getItemId()).right - 1)) {
                c.getPlayer().getImps()[index].setState(5);
            }
        }
        MapleInventoryManipulator.removeFromSlot(c, GameConstants.getInventoryType(itemid), slot.getPosition(), (short) 1, false, false);
        c.getSession().write(MaplePacketCreator.updateImp(c.getPlayer().getImps()[index], mask, index, false));
    }

    public static final void CurePot(final LittleEndianAccessor slea, final MapleClient c) {
        final int itemid = slea.readInt();
        final Item slot = c.getPlayer().getInventory(MapleInventoryType.ETC).getItem((short) slea.readInt());
        if (slot == null || slot.getQuantity() <= 0 || slot.getItemId() != itemid || itemid / 10000 != 434) {
            c.getSession().write(MaplePacketCreator.enableActions());
            c.getPlayer().marriage();
            return;
        }
        final int index = slea.readInt() - 1;
        if (index < 0 || index >= c.getPlayer().getImps().length || c.getPlayer().getImps()[index] == null || c.getPlayer().getImps()[index].getState() != 4) {
            c.getSession().write(MaplePacketCreator.enableActions());
            c.getPlayer().marriage();
            return;
        }
        c.getPlayer().getImps()[index].setState(1);
        c.getSession().write(MaplePacketCreator.updateImp(c.getPlayer().getImps()[index], ImpFlag.STATE.getValue(), index, false));
        MapleInventoryManipulator.removeFromSlot(c, MapleInventoryType.ETC, slot.getPosition(), (short) 1, false, false);
    }

    public static final void RewardPot(final LittleEndianAccessor slea, final MapleClient c) {
        final int index = slea.readInt() - 1;
        if (index < 0 || index >= c.getPlayer().getImps().length || c.getPlayer().getImps()[index] == null || c.getPlayer().getImps()[index].getLevel() < (MapleItemInformationProvider.getInstance().getPot(c.getPlayer().getImps()[index].getItemId()).right - 1)) {
            c.getSession().write(MaplePacketCreator.enableActions());
            c.getPlayer().marriage();
            return;
        }
        final int itemid = GameConstants.getRewardPot(c.getPlayer().getImps()[index].getItemId(), c.getPlayer().getImps()[index].getCloseness());
        if (itemid <= 0 || !MapleInventoryManipulator.checkSpace(c, itemid, (short) 1, "")) {
            c.getPlayer().dropMessage(1, "Please make some space.");
            c.getSession().write(MaplePacketCreator.enableActions());
            c.getPlayer().marriage();
            return;
        }
        MapleInventoryManipulator.addById(c, itemid, (short) 1, "Item Pot from " + c.getPlayer().getImps()[index].getItemId() + " on " + FileoutputUtil.CurrentReadable_Date());
        c.getSession().write(MaplePacketCreator.updateImp(c.getPlayer().getImps()[index], ImpFlag.REMOVED.getValue(), index, false));
        c.getPlayer().getImps()[index] = null;
    }
}
