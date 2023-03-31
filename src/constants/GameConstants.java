package constants;

import client.MapleCharacter;
import client.MapleClient;
import client.PlayerStats;
import client.Skill;
import client.SkillFactory;
import client.inventory.Item;
import client.inventory.MapleInventoryType;
import client.inventory.MapleWeaponType;
import client.status.MonsterStatus;
import handling.channel.handler.AttackInfo;
import java.awt.Point;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import server.MapleItemInformationProvider;
import server.MapleStatEffect;
import server.maps.MapleMapObjectType;
import tools.FileoutputUtil;
import tools.packet.MaplePacketCreator;
import tools.Randomizer;

public class GameConstants {

    public static boolean GMS = false; //true = GMS
    public static final List<MapleMapObjectType> rangedMapobjectTypes = Collections.unmodifiableList(Arrays.asList(
            MapleMapObjectType.ITEM,
            MapleMapObjectType.MONSTER,
            MapleMapObjectType.DOOR,
            MapleMapObjectType.REACTOR,
            MapleMapObjectType.SUMMON,
            MapleMapObjectType.NPC,
            MapleMapObjectType.MIST,
            MapleMapObjectType.FAMILIAR,
            MapleMapObjectType.EXTRACTOR));
    private static final int[] exp = {
        0, 15, 35, 57, 92, 135, 372, 560, 840, 1242, 1242,
        1242, 1242, 1242, 1242, 1490, 1788, 2146, 2575, 3090, 3708,
        4450, 5340, 6408, 7690, 9228, 11074, 13289, 15947, 19136, 19136,
        19136, 19136, 19136, 19136, 22963, 27556, 33067, 39681, 47616, 51425,
        55539, 59982, 64781, 69963, 75560, 81605, 88133, 95184, 102799, 111023,
        119905, 129497, 139857, 151046, 163129, 176180, 190274, 205496, 221936, 239691,
        258866, 279575, 301941, 326097, 352184, 380359, 410788, 443651, 479143, 479143,
        479143, 479143, 479143, 479143, 512683, 548571, 586971, 628059, 672024, 719065,
        769400, 823258, 880886, 942548, 1008526, 1079123, 1154662, 1235488, 1321972, 1414511,
        1513526, 1619473, 1732836, 1854135, 1983924, 2122799, 2271395, 2430393, 2600520, 2782557,
        2977336, 3185749, 3408752, 3647365, 3902680, 4175868, 4468179, 4780951, 5115618, 5473711,
        5856871, 6266852, 6705531, 7174919, 7677163, 8214565, 8789584, 9404855, 10063195, 10063195,
        10063195, 10063195, 10063195, 10063195, 10767619, 11521352, 12327847, 13190796, 14114152, 15102142,
        16159292, 17290443, 18500774, 19795828, 21181536, 22664244, 24250741, 25948292, 27764673, 29708200,
        31787774, 34012918, 36393823, 38941390, 41667288, 44583998, 47704878, 51044219, 54617315, 58440527,
        62531364, 66908559, 71592158, 76603609, 81965862, 87703472, 93842715, 100411706, 107440525, 113895024,
        120728726, 127972450, 135650797, 143789844, 152417235, 161562269, 171256005, 181531366, 192423248, 203968643,
        216206761, 229179167, 242929917, 257505712, 272956055, 289333418, 306693423, 325095029, 344600730, 365276774,
        387193381, 410424983, 435050483, 461153512, 488822722, 518152086, 549241211, 582195683, 617127424, 654155070,
        693404374, 735008637, 779109155, 825855704, 875407047, 927931469, 983607358, 1042623799, 1105181227, 1171492101};
    private static final int[] closeness = {0, 1, 3, 6, 14, 31, 60, 108, 181, 287, 434, 632, 891, 1224, 1642, 2161, 2793,
        3557, 4467, 5542, 6801, 8263, 9950, 11882, 14084, 16578, 19391, 22547, 26074,
        30000};
    private static final int[] setScore = {0, 10, 100, 300, 600, 1000, 2000, 4000, 7000, 10000};
    private static final int[] cumulativeTraitExp = {0, 20, 46, 80, 124, 181, 255, 351, 476, 639, 851, 1084,
        1340, 1622, 1932, 2273, 2648, 3061, 3515, 4014, 4563, 5128,
        5710, 6309, 6926, 7562, 8217, 8892, 9587, 10303, 11040, 11788,
        12547, 13307, 14089, 14883, 15689, 16507, 17337, 18179, 19034, 19902,
        20783, 21677, 22584, 23505, 24440, 25399, 26362, 27339, 28331, 29338,
        30360, 31397, 32450, 33519, 34604, 35705, 36823, 37958, 39110, 40279,
        41466, 32671, 43894, 45135, 46395, 47674, 48972, 50289, 51626, 52967,
        54312, 55661, 57014, 58371, 59732, 61097, 62466, 63839, 65216, 66597,
        67982, 69371, 70764, 72161, 73562, 74967, 76376, 77789, 79206, 80627,
        82052, 83481, 84914, 86351, 87792, 89237, 90686, 92139, 93596, 96000};
    private static final int[] mobHpVal = {0, 15, 20, 25, 35, 50, 65, 80, 95, 110, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350,
        375, 405, 435, 465, 495, 525, 580, 650, 720, 790, 900, 990, 1100, 1200, 1300, 1400, 1500, 1600, 1700, 1800,
        1900, 2000, 2100, 2200, 2300, 2400, 2520, 2640, 2760, 2880, 3000, 3200, 3400, 3600, 3800, 4000, 4300, 4600, 4900, 5200,
        5500, 5900, 6300, 6700, 7100, 7500, 8000, 8500, 9000, 9500, 10000, 11000, 12000, 13000, 14000, 15000, 17000, 19000, 21000, 23000,
        25000, 27000, 29000, 31000, 33000, 35000, 37000, 39000, 41000, 43000, 45000, 47000, 49000, 51000, 53000, 55000, 57000, 59000, 61000, 63000,
        65000, 67000, 69000, 71000, 73000, 75000, 77000, 79000, 81000, 83000, 85000, 89000, 91000, 93000, 95000, 97000, 99000, 101000, 103000,
        105000, 107000, 109000, 111000, 113000, 115000, 118000, 120000, 125000, 130000, 135000, 140000, 145000, 150000, 155000, 160000, 165000, 170000, 175000, 180000,
        185000, 190000, 195000, 200000, 205000, 210000, 215000, 220000, 225000, 230000, 235000, 240000, 250000, 260000, 270000, 280000, 290000, 300000, 310000, 320000,
        330000, 340000, 350000, 360000, 370000, 380000, 390000, 400000, 410000, 420000, 430000, 440000, 450000, 460000, 470000, 480000, 490000, 500000, 510000, 520000,
        530000, 550000, 570000, 590000, 610000, 630000, 650000, 670000, 690000, 710000, 730000, 750000, 770000, 790000, 810000, 830000, 850000, 870000, 890000, 910000};
    private static final int[] pvpExp = {0, 3000, 6000, 12000, 24000, 48000, 960000, 192000, 384000, 768000};
    private static final int[] guildexp = {0, 20000, 160000, 540000, 1280000, 2500000, 4320000, 6860000, 10240000, 14580000};
    private static final int[] mountexp = {0, 6, 25, 50, 105, 134, 196, 254, 263, 315, 367, 430, 543, 587, 679, 725, 897, 1146, 1394, 1701, 2247,
        2543, 2898, 3156, 3313, 3584, 3923, 4150, 4305, 4550};
    public static final int[] itemBlock = {4001168, 5220013, 3993003, 2340000, 2049100, 4001129, 2040037, 2040006, 2040007, 2040303, 2040403, 2040506, 2040507, 2040603, 2040709, 2040710, 2040711, 2040806, 2040903, 2041024, 2041025, 2043003, 2043103, 2043203, 2043303, 2043703, 2043803, 2044003, 2044103, 2044203, 2044303, 2044403, 2044503, 2044603, 2044908, 2044815, 2044019, 2044703};
    public static final int JAIL = 180000002, MAX_BUFFSTAT = 8;
//  public static final int[] blockedSkills = {23101003, 4321000, 3120006, 3110005, 13101006, 3211005, 4120010, 2220010, 2320011, 2120010};
    public static final String[] RESERVED = {"Rental", "Donor"};
    public static final String[] stats = {"tuc", "reqLevel", "reqJob", "reqSTR", "reqDEX", "reqINT", "reqLUK", "reqPOP", "cash", "cursed", "success", "setItemID", "equipTradeBlock", "durability", "randOption", "randStat", "masterLevel", "reqSkillLevel", "elemDefault", "incRMAS", "incRMAF", "incRMAI", "incRMAL", "canLevel", "skill", "charmEXP"};

    public static final int[] hyperTele = {310000000, 220000000, 100000000, 250000000, 240000000, 104000000, 103000000, 102000000, 101000000, 120000000, 260000000, 200000000, 230000000};

    public static int getExpNeededForLevel(final int level) {
        if (level < 0 || level >= exp.length) {
            return Integer.MAX_VALUE;
        }
        return exp[level];
    }

    public static int getGuildExpNeededForLevel(final int level) {
        if (level < 0 || level >= guildexp.length) {
            return Integer.MAX_VALUE;
        }
        return guildexp[level];
    }

    public static int getPVPExpNeededForLevel(final int level) {
        if (level < 0 || level >= pvpExp.length) {
            return Integer.MAX_VALUE;
        }
        return pvpExp[level];
    }

    public static int getClosenessNeededForLevel(final int level) {
        return closeness[level - 1];
    }

    public static int getMountExpNeededForLevel(final int level) {
        return mountexp[level - 1];
    }

    public static int getTraitExpNeededForLevel(final int level) {
        if (level < 0 || level >= cumulativeTraitExp.length) {
            return Integer.MAX_VALUE;
        }
        return cumulativeTraitExp[level];
    }

    public static int getSetExpNeededForLevel(final int level) {
        if (level < 0 || level >= setScore.length) {
            return Integer.MAX_VALUE;
        }
        return setScore[level];
    }

    public static int getMonsterHP(final int level) {
        if (level < 0 || level >= mobHpVal.length) {
            return Integer.MAX_VALUE;
        }
        return mobHpVal[level];
    }

    public static int getBookLevel(final int level) {
        return (int) ((5 * level) * (level + 1));
    }

    public static int getTimelessRequiredEXP(final int level) {
        return 70 + (level * 10);
    }

    public static int getReverseRequiredEXP(final int level) {
        return 60 + (level * 5);
    }

    public static int getProfessionEXP(final int level) {
        return ((100 * level * level) + (level * 400)) / (GameConstants.GMS ? 2 : 1);
    }

    public static boolean isHarvesting(final int itemId) {
        return itemId >= 1500000 && itemId < 1520000;
    }

    public static int maxViewRangeSq() {
        return Integer.MAX_VALUE;
    }

    public static int maxViewRangeSq_Half() {
        return Integer.MAX_VALUE;
    }

    public static boolean isJobFamily(final int baseJob, final int currentJob) {
        return currentJob >= baseJob && currentJob / 100 == baseJob / 100;
    }

    public static boolean isKOC(final int job) {
        return job >= 1000 && job < 2000;
    }

    public static boolean isEvan(final int job) {
        return job == 2001 || (job >= 2200 && job <= 2218);
    }

    public static boolean isMercedes(final int job) {
        return job == 2002 || (job >= 2300 && job <= 2312);
    }

    public static boolean isDemon(final int job) {
        return job == 3001 || (job >= 3100 && job <= 3112);
    }

    public static boolean isAran(final int job) {
        return job >= 2000 && job <= 2112 && job != 2001 && job != 2002;
    }

    public static boolean isResist(final int job) {
        return job >= 3000 && job <= 3512;
    }

    public static boolean isAdventurer(final int job) {
        return job >= 0 && job < 1000;
    }

    public static boolean isCannon(final int job) {
        return job == 1 || job == 501 || (job >= 530 && job <= 532);
    }

    public static boolean isDualBlade(final int job) {
        return job >= 430 && job <= 434;
    }

    public static boolean isAdventurer_(final int job) { // not included legends/dual blade
        switch (job) {
            case 0: // beginner

            case 100: // start of warrior
            case 110:
            case 111:
            case 112:
            case 120:
            case 121:
            case 122:
            case 130:
            case 131:
            case 132: // end of warrior

            case 200: // start of magician
            case 210:
            case 211:
            case 212:
            case 220:
            case 221:
            case 222:
            case 230:
            case 231:
            case 232: // end of magician

            case 300: // start of bowman
            case 310:
            case 311:
            case 312:
            case 320:
            case 321:
            case 322: // end of bowman

            case 400: // start of thief
            case 410:
            case 411:
            case 412:
            case 420:
            case 421:
            case 422: // end of thief

            case 500: // start of pirate
            case 510:
            case 511:
            case 512:
            case 520:
            case 521:
            case 522: // end of pirate 
                return true;
        }
        return false;
    }

    public static boolean isRecoveryIncSkill(final int id) {
        switch (id) {
            case 1110000:
            case 2000000:
            case 1210000:
            case 11110000:
            case 4100002:
            case 4200001:
                return true;
        }
        return false;
    }

    public static boolean isLinkedAranSkill(final int id) {
        return getLinkedAranSkill(id) != id;
    }

    public static int getLinkedAranSkill(final int id) {
        switch (id) {
            case 30010183:
            case 30010184:
            case 30010186:
                return 30010110; //dark winds
            case 21110007:
            case 21110008:
                return 21110002;
            case 21120009:
            case 21120010:
                return 21120002;
            case 4321001:
                return 4321000;
            case 33101006:
            case 33101007:
                return 33101005;
            case 33101008:
                return 33101004;
            case 35101009:
            case 35101010:
                return 35100008;
            case 35111009:
            case 35111010:
                return 35111001;
            case 35121013:
                return 35111004;
            case 35121011:
                return 35121009;
            case 32001007:
            case 32001008:
            case 32001009:
            case 32001010:
            case 32001011:
                return 32001001;
            case 5300007:
                return 5301001;
            case 5320011:
                return 5321004;
            case 23101007:
                return 23101001;
            case 23111010:
            case 23111009:
                return 23111008;
            case 31001006:
            case 31001007:
            case 31001008:
                return 31000004;
        }
        return id;
    }

    public final static boolean isForceIncrease(int skillid) {
        switch (skillid) {
            case 31000004:
            case 31001006:
            case 31001007:
            case 31001008:

            case 30010166:
            case 30011167:
            case 30011168:
            case 30011169:
            case 30011170:
                return true;
        }
        return false;
    }

    public static int getBOF_ForJob(final int job) {
        return PlayerStats.getSkillByJob(12, job);
    }

    public static int getEmpress_ForJob(final int job) {
        return PlayerStats.getSkillByJob(73, job);
    }

    public static boolean isElementAmp_Skill(final int skill) {
        switch (skill) {
            case 2110001:
            case 2210001:
            case 12110001:
            case 22150000:
                return true;
        }
        return false;
    }

    public static int getMPEaterForJob(final int job) {
        switch (job) {
            case 210:
            case 211:
            case 212:
                return 2100000;
            case 220:
            case 221:
            case 222:
                return 2200000;
            case 230:
            case 231:
            case 232:
                return 2300000;
        }
        return 2100000; // Default, in case GM
    }

    public static int getJobShortValue(int job) {
        if (job >= 1000) {
            job -= (job / 1000) * 1000;
        }
        job /= 100;
        if (job == 4) { // For some reason dagger/ claw is 8.. IDK
            job *= 2;
        } else if (job == 3) {
            job += 1;
        } else if (job == 5) {
            job += 11; // 16
        }
        return job;
    }

    public static boolean isPyramidSkill(final int skill) {
        return isBeginnerJob(skill / 10000) && skill % 10000 == 1020;
    }

    public static boolean isInflationSkill(final int skill) {
        return isBeginnerJob(skill / 10000) && (skill % 10000 == 1092 || skill % 10000 == 1094 || skill % 10000 == 1095);
    }

    public static boolean isMulungSkill(final int skill) {
        return isBeginnerJob(skill / 10000) && (skill % 10000 == 1009 || skill % 10000 == 1010 || skill % 10000 == 1011);
    }

    public static boolean isIceKnightSkill(final int skill) {
        return isBeginnerJob(skill / 10000) && (skill % 10000 == 1098 || skill % 10000 == 97 || skill % 10000 == 99 || skill % 10000 == 100 || skill % 10000 == 103 || skill % 10000 == 104 || skill % 10000 == 1105);
    }

    public static boolean isThrowingStar(final int itemId) {
        return itemId / 10000 == 207;
    }

    public static boolean isBullet(final int itemId) {
        return itemId / 10000 == 233;
    }

    public static boolean isRechargable(final int itemId) {
        return isThrowingStar(itemId) || isBullet(itemId);
    }

    public static boolean isOverall(final int itemId) {
        return itemId / 10000 == 105;
    }

    public static boolean isPet(final int itemId) {
        return itemId / 10000 == 500;
    }

    public static boolean isArrowForCrossBow(final int itemId) {
        return itemId >= 2061000 && itemId < 2062000;
    }

    public static boolean isArrowForBow(final int itemId) {
        return itemId >= 2060000 && itemId < 2061000;
    }

    public static boolean isMagicWeapon(final int itemId) {
        final int s = itemId / 10000;
        return s == 137 || s == 138;
    }

    public static boolean isWeapon(final int itemId) {
        return itemId >= 1300000 && itemId < 1533000;
    }

    public static MapleInventoryType getInventoryType(final int itemId) {
        final byte type = (byte) (itemId / 1000000);
        if (type < 1 || type > 5) {
            return MapleInventoryType.UNDEFINED;
        }
        return MapleInventoryType.getByType(type);
    }

    public static MapleWeaponType getWeaponType(final int itemId) {
        int cat = itemId / 10000;
        cat = cat % 100;
        switch (cat) {
            case 30:
                return MapleWeaponType.单手劍;
            case 31:
                return MapleWeaponType.AXE1H;
            case 32:
                return MapleWeaponType.BLUNT1H;
            case 33:
                return MapleWeaponType.DAGGER;
            case 34:
                return MapleWeaponType.KATARA;
            case 35:
                return MapleWeaponType.MAGIC_ARROW;
            case 37:
                return MapleWeaponType.WAND;
            case 38:
                return MapleWeaponType.STAFF;
            case 40:
                return MapleWeaponType.SWORD2H;
            case 41:
                return MapleWeaponType.AXE2H;
            case 42:
                return MapleWeaponType.BLUNT2H;
            case 43:
                return MapleWeaponType.SPEAR;
            case 44:
                return MapleWeaponType.POLE_ARM;
            case 45:
                return MapleWeaponType.BOW;
            case 46:
                return MapleWeaponType.CROSSBOW;
            case 47:
                return MapleWeaponType.CLAW;
            case 48:
                return MapleWeaponType.KNUCKLE;
            case 49:
                return MapleWeaponType.GUN;
            case 52:
                return MapleWeaponType.DUAL_BOW;
            case 53:
                return MapleWeaponType.CANNON;
        }
        return MapleWeaponType.NOT_A_WEAPON;
    }

    public static boolean isShield(final int itemId) {
        int cat = itemId / 10000;
        cat = cat % 100;
        return cat == 9;
    }

    public static boolean isEquip(final int itemId) {
        return itemId / 1000000 == 1;
    }

    public static boolean isCleanSlate(int itemId) {
        return itemId / 100 == 20490;
    }

    public static boolean isAccessoryScroll(int itemId) {
        return itemId / 100 == 20492;
    }

    public static boolean isChaosScroll(int itemId) {
        if (itemId >= 2049105 && itemId <= 2049110) {
            return false;
        }
        return itemId / 100 == 20491 || itemId == 2040126;
    }

    public static int getChaosNumber(int itemId) {
        return itemId == 2049116 ? 10 : 5;
    }

    public static boolean isEquipScroll(int scrollId) {
        return scrollId / 100 == 20493;
    }

    public static boolean isPotentialScroll(int scrollId) {
        return scrollId / 100 == 20494 || scrollId == 5534000;
    }

    public static boolean isStampScroll(int scrollId) {
        return scrollId == 2049500 || scrollId == 2049501;
    }

    public static int getStampRate(int scrollId) {
        switch (scrollId) {
            case 2049500:
                return 80;
            case 2049501:
                return 50;
        }
        return 0;
    }

    public static boolean isSpecialScroll(final int scrollId) {
        return is幸運卷軸(scrollId) || is防暴卷軸(scrollId) || is安全卷軸(scrollId) || is卷軸防護卷軸(scrollId) || is帶成功率特殊卷軸(scrollId);
    }

    public static boolean is帶成功率特殊卷軸(int scrollId) {
        switch (scrollId) {
            case 2040727://防滑卷轴10%
            case 2041058://防寒卷轴10%
                return true;
            default:
                return false;
        }
    }

    public static boolean is幸運卷軸(int scrollId) {
        switch (scrollId) {
            case 5063100:
            case 5068000:
                return true;
            default:
                return scrollId / 1000 == 2530;
        }
    }

    public static boolean is防暴卷軸(int scrollId) {
        switch (scrollId) {
            case 5063100:
            case 5064000:
            case 5064003:
                return true;
            default:
                return scrollId / 1000 == 2531;
        }
    }

    public static boolean is安全卷軸(int scrollId) {
        switch (scrollId) {
            case 5064100:
            case 5064101:
            case 5068100:
                return true;
            default:
                return scrollId / 1000 == 2532;
        }
    }

    public static boolean is卷軸防護卷軸(int scrollId) {
        switch (scrollId) {
            case 5064300:
            case 5068200:
                return true;
        }
        return false;
    }

    public static boolean isTwoHanded(final int itemId) {
        switch (getWeaponType(itemId)) {
            case AXE2H:
            case GUN:
            case KNUCKLE:
            case BLUNT2H:
            case BOW:
            case CLAW:
            case CROSSBOW:
            case POLE_ARM:
            case SPEAR:
            case SWORD2H:
            case CANNON:
                //case DUAL_BOW: //magic arrow
                return true;
            default:
                return false;
        }
    }

    public static boolean isTownScroll(final int id) {
        return id >= 2030000 && id < 2040000;
    }

    public static boolean isUpgradeScroll(final int id) {
        return id >= 2040000 && id < 2050000;
    }

    public static boolean isGun(final int id) {
        return id >= 1492000 && id < 1500000;
    }

    public static boolean isUse(final int id) {
        return id >= 2000000 && id < 3000000;
    }

    public static boolean isSummonSack(final int id) {
        return id / 10000 == 210;
    }

    public static boolean isMonsterCard(final int id) {
        return id / 10000 == 238;
    }

    public static boolean isSpecialCard(final int id) {
        return id / 1000 >= 2388;
    }

    public static int getCardShortId(final int id) {
        return id % 10000;
    }

    public static boolean isGem(final int id) {
        return id >= 4250000 && id <= 4251402;
    }

    public static boolean isOtherGem(final int id) {
        switch (id) {
            case 4001174:
            case 4001175:
            case 4001176:
            case 4001177:
            case 4001178:
            case 4001179:
            case 4001180:
            case 4001181:
            case 4001182:
            case 4001183:
            case 4001184:
            case 4001185:
            case 4001186:
            case 4031980:
            case 2041058:
            case 2040727:
            case 1032062:
            case 4032334:
            case 4032312:
            case 1142156:
            case 1142157:
                return true; //mostly quest items
        }
        return false;
    }

    public static boolean isCustomQuest(final int id) {
        return id > 99999;
    }

    public static int getTaxAmount(final int meso) {
        if (meso >= 100000000) {
            return (int) Math.round(0.06 * meso);
        } else if (meso >= 25000000) {
            return (int) Math.round(0.05 * meso);
        } else if (meso >= 10000000) {
            return (int) Math.round(0.04 * meso);
        } else if (meso >= 5000000) {
            return (int) Math.round(0.03 * meso);
        } else if (meso >= 1000000) {
            return (int) Math.round(0.018 * meso);
        } else if (meso >= 100000) {
            return (int) Math.round(0.008 * meso);
        }
        return 0;
    }

    public static int EntrustedStoreTax(final int meso) {
        if (meso >= 100000000) {
            return (int) Math.round(0.03 * meso);
        } else if (meso >= 25000000) {
            return (int) Math.round(0.025 * meso);
        } else if (meso >= 10000000) {
            return (int) Math.round(0.02 * meso);
        } else if (meso >= 5000000) {
            return (int) Math.round(0.015 * meso);
        } else if (meso >= 1000000) {
            return (int) Math.round(0.009 * meso);
        } else if (meso >= 100000) {
            return (int) Math.round(0.004 * meso);
        }
        return 0;
    }

    public static int getAttackDelay(final int id, final Skill skill) {
        switch (id) { // Assume it's faster(2)
            case 3121004: // Storm of Arrow
            case 23121000:
            case 33121009:
            case 13111002: // Storm of Arrow
            case 5221004: // Rapidfire
            case 5201006: // Recoil shot/ Back stab shot
            case 35121005:
            case 35111004:
            case 35121013:
                return 40; //reason being you can spam with final assaulter
            case 14111005:
            case 4121007:
            case 5221007:
                return 99; //skip duh chek
            case 0: // Normal Attack, TODO delay for each weapon type
                return 570;
        }
        if (skill != null && skill.getSkillType() == 3) {
            return 0; //final attack
        }
        if (skill != null && skill.getDelay() > 0 && !isNoDelaySkill(id)) {
            return skill.getDelay();
        }
        // TODO delay for final attack, weapon type, swing,stab etc
        return 330; // Default usually
    }

    public static byte gachaponRareItem(final int id) {
        switch (id) {
            case 2340000:
            case 5570000:
            case 2049400:
            case 2049300:
            case 1082149:
            case 1102041:
            case 1102248:
            case 1102084:
            case 1022117:
            case 1022067:
            case 1012070:
            case 1132013:
            case 1132145:
            case 1132133:
            case 1132134:
            case 1112401:
            case 1113036:
            case 1032129:
            case 1122185:
            case 1152075:
            case 1152076:
            case 1152077:
            case 1302145:
            case 1303146:
            case 1332118:
            case 1332119:
            case 1342031:
            case 1342032:
            case 1452104:
            case 1452105:
            case 1462089:
            case 1462090:
            case 1472115:
            case 1472116:
            case 1482077:
            case 1482078:
            case 1492077:
            case 1492078:
            case 1432079:
            case 1432080:
            case 1442109:
            case 1442110:
            case 1372076:
            case 1372077:
            case 1142249:
            case 1142758:
            case 1142664:
            case 1142665:
            case 1142006:
            case 1032205:
            case 4032654:
            case 4032655:
            case 1132211:
            case 1152120:
            case 4032177:
            case 4001220:
            case 4001226:
            case 4001227:
            case 4001228:
            case 4001229:
            case 4001230:
            case 1012168:
            case 1012169:
            case 3016203:
            case 3010161:
            case 3010183:
            case 3010703:
            case 3010014:
            case 1022190://獨眼巨人眼:进化I
            case 1082179://黄擊中手套
            case 1032171://葡萄耳環	
            case 1032182://白色情人節酸酸甜甜的耳環	
            case 1032134://酸酸甜甜的耳環	
            case 1032167://音符耳環	
            case 1032028://紅祖母耳環
            case 2049401: //潜在能力賦予卷軸
            case 2049301: //装备強化卷軸
            case 2049100: //混沌卷軸60%
            case 1302146: //Last 不速之客 单手劍
            case 1382097: //3rd 不速之客 長杖
            case 1382098: //Last 不速之客 長杖		
            case 1142538: //青銅等級
            case 1142539: //白銀等級
            case 1142540: //黄金等級
            case 1142541: //最強等級
            case 3015821: //圓潤櫻桃椅
            case 3010002: //綠色設計師椅
            case 3010003: //紅色設計師椅
            case 3010006: //黄色設計師椅
            case 3010056: //充電器椅
            case 3010073: //品克缤椅
            case 3010043: //女巫掃把

            case 3010007: //粉海豹
            case 3010008: //藍海豹
            case 3010016: //白海豹
            case 3010017: //黑海豹
            case 3010029: //藍五環
            case 3010030: //黑五環
            case 3010031: //紅五環
            case 3010032: //黄五環
            case 3010033: //綠五環
            case 1302147: //The VIP 单手劍
            case 1332120: //The VIP 短劍(Luk)
            case 1342033: //The VIP 短劍
            case 1372078: //The VIP 杖
            case 1382099: //The VIP 長杖
            case 1432081: //The VIP 枪
            case 1442111: //The VIP 矛
            case 1452106: //The VIP 弓
            case 1462091: //The VIP 弩
            case 1472117: //The VIP 拳套
            case 1482079: //The VIP 指虎
            case 1492079: //The VIP 火枪
            case 1312062: //The VIP 单手斧
            case 1322090: //The VIP 单手鈍器
            case 1402090: //The VIP 雙手劍
            case 1412062: //The VIP 雙手斧
            case 1422063: //The VIP 雙手鈍器
            case 1312060: //3斧
            case 1312061: //L斧
            case 1322088: //3鈍
            case 1322089: //L鈍
            case 1402088: //3雙手劍
            case 1402089: //L雙手劍
            case 1412060: //3雙手斧
            case 1412061: //L雙手斧
            case 1422061: //3雙手鈍器
            case 1422062: //L雙手鈍器
            case 1112748: //风暴戒指
            case 1112750: //友情戒指

            case 1032148: //风暴耳饰
            case 1082438: //风暴手套
            case 1102467: //风暴披风
            case 1122200: //风暴項链
            case 1132161: //风暴腰帶
            case 1152099: //风暴肩章
            case 1012170: //鬼娃恰吉的伤口
            case 1012171: //鬼娃恰吉的伤口
            case 1012172: //鬼娃恰吉的伤口

            case 2022463: //卡珊德拉的奖励5
            case 1022162: //旅行者的翅膀造型眼鏡
            case 1022163: //書呆子玻璃眼鏡
            case 1012081: //蓋福克斯面具
            case 3015703: //馬卡龍甜甜抱枕
            case 3015823: //彩虹旋轉木馬椅子
            case 3015991: //木造浴缸溫泉椅子
            case 3015523: //神木村椅子
            case 3015813: //冰晶城堡
            case 3015887: //核心寶石的椅子
            case 3015174: //偽裝弓箭手村
            case 3015763: //艾格和的回憶椅子
            case 3015468: //超越石椅子
            case 3015759: //超新星魔導椅子
            case 3015408: //玩具國家的主角
            case 3015357: //阿里山日出椅子
            case 3015803: //灌籃高手椅子
            case 3015916: //与貓咪一起休息椅子
            case 3015918: //森林休憩椅
            case 3015842: //宇宙光波枪椅子
            case 3015915: //貓咪老師椅子
            case 3015295: //紳士藍鳥服裝
            case 3015109: //花之幻想曲
            case 3015404: //綻放吧！櫻花處
            case 3015545: //开花的玫瑰天使
            case 3015637: //露希妲之花
            case 3015817: //花之风椅子
            case 3010747: //綠水灵放风箏
            case 3015939: //放天燈椅子
            case 3015238: //泡泡綠水灵椅子
            case 3015864: //綠水灵樂園的鼓舞
            case 3015938: //与水豚一起泡溫泉椅子
            case 3015333: //通灵的姆勒姆勒
            case 3015339: //幽灵船椅子
            case 3015639: //精灵椅子
            case 3015936: //阿爾卡娜的精灵椅子
            case 3015867: //阿爾卡娜精灵樹椅
            case 3015816: //火之精髓椅子
            case 3015181: //夜空中的月亮椅子
            case 3015325: //月圓之夜椅 
            case 3015652: //快樂回家之路椅子
            case 3015757: //重力歪曲傑特椅子
            case 3015815: //半夜不睡覺
            case 3010590: //酸酸甜甜好滋味的甜点椅子
            case 3010038: //透明椅子
            case 3010751: //可愛鯨魚
            case 3015015: //牡羊座椅子
            case 3015016: //金牛座椅子
            case 3015017: //雙魚座椅子
            case 3015018: //雙子座椅子
            case 3015019: //巨蟹座椅子
            case 3015020: //獅子座椅子
            case 3015021: //天秤座椅子
            case 3015022: //天蠍座椅子
            case 3015023: //處女座椅子
            case 3015024: //射手座椅子
            case 3015025: //魔羯座椅子
            case 3015026: //水瓶座椅子
            case 3012019: //愛情海椅子
            case 3012012: //櫻花椅子
            case 3012016: //甜蜜糖果椅子
            case 3012010: //巧克力蛋糕

            case 2049000: // Reverse Scroll
            case 2049001: // Reverse Scroll
            case 2049002: // Reverse Scroll
            case 2040006: // Miracle
            case 2040007: // Miracle
            case 2040303: // Miracle
            case 2040403: // Miracle
            case 2040506: // Miracle
            case 2040507: // Miracle
            case 2040603: // Miracle
            case 2040709: // Miracle
            case 2040710: // Miracle
            case 2040711: // Miracle
            case 2040806: // Miracle
            case 2040903: // Miracle
            case 2041024: // Miracle
            case 2041025: // Miracle
            case 2043003: // Miracle
            case 2043103: // Miracle
            case 2043203: // Miracle
            case 2043303: // Miracle
            case 2043703: // Miracle
            case 2043803: // Miracle
            case 2044003: // Miracle
            case 2044103: // Miracle
            case 2044203: // Miracle
            case 2044303: // Miracle
            case 2044403: // Miracle
            case 2044503: // Miracle
            case 2044603: // Miracle
            case 2044908: // Miracle
            case 2044815: // Miracle
            case 2044019: // Miracle
            case 2044703: // Miracle
                return 1;
            //1 = wedding msg o.o
        }
        return 0;
    }

    public static byte gachaponRareItemS(final int id) {
        switch (id) {
            case 2049100: //混沌卷軸60%
            case 2340000: //祝福卷軸
            case 1102041: //粉紅冒险家披风
            case 1102248: //神龍披风
            case 1102084: //粉紅蓋亞披风
            case 5570000: //黄金鐵鎚
            case 2022463: //卡珊德拉的奖励5
            case 1022117: //歐尼斯龍的眼鏡
            case 1022162: //旅行者的翅膀造型眼鏡
            case 1022163: //書呆子玻璃眼鏡
            case 1022067: //黑狐狸猴
            case 1022190: //獨眼巨人眼:进化I	
            case 1113036: //高級枫叶紫色戒指
            case 1112748: //风暴戒指
            case 1112750: //友情戒指
            case 1032148: //风暴耳饰
            case 1082438: //风暴手套
            case 1102467: //风暴披风
            case 1122200: //风暴項链
            case 1132161: //风暴腰帶
            case 1152099: //风暴肩章
            case 1012081: //蓋福克斯面具
            case 1302147: //The VIP 单手劍
            case 1332120: //The VIP 短劍(Luk)
            case 1342033: //The VIP 短劍
            case 1372078: //The VIP 杖
            case 1382099: //The VIP 長杖
            case 1432081: //The VIP 枪
            case 1442111: //The VIP 矛
            case 1452106: //The VIP 弓
            case 1462091: //The VIP 弩
            case 1472117: //The VIP 拳套
            case 1482079: //The VIP 指虎
            case 1492079: //The VIP 火枪
            case 1312062: //The VIP 单手斧
            case 1322090: //The VIP 单手鈍器
            case 1402090: //The VIP 雙手劍
            case 1412062: //The VIP 雙手斧
            case 1422063: //The VIP 雙手鈍器	       
            case 1302146: //Last 不速之客 单手劍	
            case 1332119: //Last 不速之客 短劍(Luk)
            case 1342032: //Last 不速之客 短劍
            case 1452105: //Last 不速之客 弓
            case 1462090: //Last 不速之客 弩
            case 1472116: //Last 不速之客 拳套
            case 1482078: //Last 不速之客 指虎
            case 1492078: //Last 不速之客 火枪
            case 1432080: //Last 不速之客 枪
            case 1442110: //Last 不速之客 矛
            case 1382098: //Last 不速之客 長杖		
            case 1372077: //Last 不速之客 杖	
            case 1312061: //L斧
            case 1322089: //L鈍
            case 1412061: //L雙手斧
            case 1402089: //L雙手劍
            case 1422062: //L雙手鈍器
            case 1012168: //鬼娃恰吉的伤口
            case 1012169: //鬼娃恰吉的伤口
            case 1012170: //鬼娃恰吉的伤口
            case 1012171: //鬼娃恰吉的伤口
            case 1012172: //鬼娃恰吉的伤口
            case 1032205: //神话耳環
            case 4032654: //女神的眼淚
            case 4032655: //女神的血
            case 1132211: //枫之谷強韧意志黄色腰帶
            case 1152120: //枫之谷強韧意志黄色肩膀装饰
            case 4032177: //枫叶之心的項链
            case 4001220: //項鍊
            case 4001226: //勇氣之心
            case 4001227: //智慧之心
            case 4001228: //命中之心
            case 4001229: //敏捷之心
            case 4001230: //自由之心
            case 1142249: //我是幸運兒
            case 1142758: //初音勋章
            case 1142664: //SAO一般副本攻略組
            case 1142665: //SAO高級副本攻略組
            case 1142006: //枫之谷超級偶像勋章
            case 1142538: //青銅等級
            case 1142539: //白銀等級
            case 1142540: //黄金等級
            case 1142541: //最強等級
            case 3015703: //馬卡龍甜甜抱枕
            case 3015823: //彩虹旋轉木馬椅子
            case 3015991: //木造浴缸溫泉椅子
            case 3015523: //神木村椅子
            case 3015813: //冰晶城堡
            case 3015887: //核心寶石的椅子
            case 3015174: //偽裝弓箭手村
            case 3015763: //艾格和的回憶椅子
            case 3015468: //超越石椅子
            case 3015759: //超新星魔導椅子
            case 3015408: //玩具國家的主角
            case 3015357: //阿里山日出椅子
            case 3015803: //灌籃高手椅子
            case 3015916: //与貓咪一起休息椅子
            case 3015918: //森林休憩椅
            case 3015842: //宇宙光波枪椅子
            case 3015915: //貓咪老師椅子
            case 3015295: //紳士藍鳥服裝
            case 3015109: //花之幻想曲
            case 3015404: //綻放吧！櫻花處
            case 3015545: //开花的玫瑰天使
            case 3015637: //露希妲之花
            case 3015817: //花之风椅子
            case 3010747: //綠水灵放风箏
            case 3015939: //放天燈椅子
            case 3015238: //泡泡綠水灵椅子
            case 3015864: //綠水灵樂園的鼓舞
            case 3015938: //与水豚一起泡溫泉椅子
            case 3015333: //通灵的姆勒姆勒
            case 3015339: //幽灵船椅子
            case 3015639: //精灵椅子
            case 3015936: //阿爾卡娜的精灵椅子
            case 3015802: //阿爾卡娜精灵樹椅
            case 3015867: //灵魂的重量椅子
            case 3015816: //火之精髓椅子
            case 3015181: //夜空中的月亮椅子
            case 3015325: //月圓之夜椅 
            case 3015652: //快樂回家之路椅子
            case 3015821: //圓潤櫻桃椅 
            case 3015757: //重力歪曲傑特椅子
            case 3015815: //半夜不睡覺
            case 3010590: //酸酸甜甜好滋味的甜点椅子
                return 1;
            //1 = wedding msg o.o
        }
        return 0;
    }

    public final static int[] goldrewards = {
        2049400, 1,
        2049401, 2,
        2049301, 2,
        2340000, 1, // white scroll
        2070007, 2,
        2070016, 1,
        2330007, 1,
        2070018, 1, // balance fury
        1402037, 1, // Rigbol Sword
        2290096, 1, // Maple Warrior 20
        2290049, 1, // Genesis 30
        2290041, 1, // Meteo 30
        2290047, 1, // Blizzard 30
        2290095, 1, // Smoke 30
        2290017, 1, // Enrage 30
        2290075, 1, // Snipe 30
        2290085, 1, // Triple Throw 30
        2290116, 1, // Areal Strike
        1302059, 3, // Dragon Carabella
        2049100, 1, // Chaos Scroll
        1092049, 1, // Dragon Kanjar
        1102041, 1, // Pink Cape
        1432018, 3, // Sky Ski
        1022047, 3, // Owl Mask
        3010051, 1, // Chair
        3010020, 1, // Portable meal table
        2040914, 1, // Shield for Weapon Atk

        1432011, 3, // Fair Frozen
        1442020, 3, // HellSlayer
        1382035, 3, // Blue Marine
        1372010, 3, // Dimon Wand
        1332027, 3, // Varkit
        1302056, 3, // Sparta
        1402005, 3, // Bezerker
        1472053, 3, // Red Craven
        1462018, 3, // Casa Crow
        1452017, 3, // Metus
        1422013, 3, // Lemonite
        1322029, 3, // Ruin Hammer
        1412010, 3, // Colonian Axe

        1472051, 1, // Green Dragon Sleeve
        1482013, 1, // Emperor's Claw
        1492013, 1, // Dragon fire Revlover

        1382049, 1,
        1382050, 1, // Blue Dragon Staff
        1382051, 1,
        1382052, 1,
        1382045, 1, // Fire Staff, Level 105
        1382047, 1, // Ice Staff, Level 105
        1382048, 1, // Thunder Staff
        1382046, 1, // Poison Staff

        1372035, 1,
        1372036, 1,
        1372037, 1,
        1372038, 1,
        1372039, 1,
        1372040, 1,
        1372041, 1,
        1372042, 1,
        1332032, 8, // Christmas Tree
        1482025, 7, // Flowery Tube

        4001011, 8, // Lupin Eraser
        4001010, 8, // Mushmom Eraser
        4001009, 8, // Stump Eraser

        2047000, 1,
        2047001, 1,
        2047002, 1,
        2047100, 1,
        2047101, 1,
        2047102, 1,
        2047200, 1,
        2047201, 1,
        2047202, 1,
        2047203, 1,
        2047204, 1,
        2047205, 1,
        2047206, 1,
        2047207, 1,
        2047208, 1,
        2047300, 1,
        2047301, 1,
        2047302, 1,
        2047303, 1,
        2047304, 1,
        2047305, 1,
        2047306, 1,
        2047307, 1,
        2047308, 1,
        2047309, 1,
        2046004, 1,
        2046005, 1,
        2046104, 1,
        2046105, 1,
        2046208, 1,
        2046209, 1,
        2046210, 1,
        2046211, 1,
        2046212, 1,
        //list
        1132014, 3,
        1132015, 2,
        1132016, 1,
        1002801, 2,
        1102205, 2,
        1332079, 2,
        1332080, 2,
        1402048, 2,
        1402049, 2,
        1402050, 2,
        1402051, 2,
        1462052, 2,
        1462054, 2,
        1462055, 2,
        1472074, 2,
        1472075, 2,
        //pro raven
        1332077, 1,
        1382082, 1,
        1432063, 1,
        1452087, 1,
        1462053, 1,
        1472072, 1,
        1482048, 1,
        1492047, 1,
        2030008, 5, // Bottle, return scroll
        1442018, 3, // Frozen Tuna
        2040900, 4, // Shield for DEF
        2049100, 10,
        2000005, 10, // Power Elixir
        2000004, 10, // Elixir
        4280000, 8,
        2430144, 10,
        2290285, 10,
        2028061, 10,
        2028062, 10,
        2530000, 5,
        2531000, 5}; // Gold Box
    public final static int[] silverrewards = {
        2049401, 2,
        2049301, 2,
        3010041, 1, // skull throne
        1002452, 6, // Starry Bandana
        1002455, 6, // Starry Bandana
        2290084, 1, // Triple Throw 20
        2290048, 1, // Genesis 20
        2290040, 1, // Meteo 20
        2290046, 1, // Blizzard 20
        2290074, 1, // Sniping 20
        2290064, 1, // Concentration 20
        2290094, 1, // Smoke 20
        2290022, 1, // Berserk 20
        2290056, 1, // Bow Expert 30
        2290066, 1, // xBow Expert 30
        2290020, 1, // Sanc 20
        1102082, 1, // Black Raggdey Cape
        1302049, 1, // Glowing Whip
        2340000, 1, // White Scroll
        1102041, 1, // Pink Cape
        1452019, 2, // White Nisrock
        4001116, 3, // Hexagon Pend
        4001012, 3, // Wraith Eraser
        1022060, 2, // Foxy Racoon Eye
        2430144, 5,
        2290285, 5,
        2028062, 5,
        2028061, 5,
        2530000, 1,
        2531000, 1,
        2041100, 1,
        2041101, 1,
        2041102, 1,
        2041103, 1,
        2041104, 1,
        2041105, 1,
        2041106, 1,
        2041107, 1,
        2041108, 1,
        2041109, 1,
        2041110, 1,
        2041111, 1,
        2041112, 1,
        2041113, 1,
        2041114, 1,
        2041115, 1,
        2041116, 1,
        2041117, 1,
        2041118, 1,
        2041119, 1,
        2041300, 1,
        2041301, 1,
        2041302, 1,
        2041303, 1,
        2041304, 1,
        2041305, 1,
        2041306, 1,
        2041307, 1,
        2041308, 1,
        2041309, 1,
        2041310, 1,
        2041311, 1,
        2041312, 1,
        2041313, 1,
        2041314, 1,
        2041315, 1,
        2041316, 1,
        2041317, 1,
        2041318, 1,
        2041319, 1,
        2049200, 1,
        2049201, 1,
        2049202, 1,
        2049203, 1,
        2049204, 1,
        2049205, 1,
        2049206, 1,
        2049207, 1,
        2049208, 1,
        2049209, 1,
        2049210, 1,
        2049211, 1,
        1432011, 3, // Fair Frozen
        1442020, 3, // HellSlayer
        1382035, 3, // Blue Marine
        1372010, 3, // Dimon Wand
        1332027, 3, // Varkit
        1302056, 3, // Sparta
        1402005, 3, // Bezerker
        1472053, 3, // Red Craven
        1462018, 3, // Casa Crow
        1452017, 3, // Metus
        1422013, 3, // Lemonite
        1322029, 3, // Ruin Hammer
        1412010, 3, // Colonian Axe

        1002587, 3, // Black Wisconsin
        1402044, 1, // Pumpkin lantern
        2101013, 4, // Summoning Showa boss
        1442046, 1, // Super Snowboard
        1422031, 1, // Blue Seal Cushion
        1332054, 3, // Lonzege Dagger
        1012056, 3, // Dog Nose
        1022047, 3, // Owl Mask
        3012002, 1, // Bathtub
        1442012, 3, // Sky snowboard
        1442018, 3, // Frozen Tuna
        1432010, 3, // Omega Spear
        1432036, 1, // Fishing Pole
        2000005, 10, // Power Elixir
        2049100, 10,
        2000004, 10, // Elixir
        4280001, 8}; // Silver Box
    public final static int[] peanuts = {2430091, 200, 2430092, 200, 2430093, 200, 2430101, 200, 2430102, 200, 2430136, 200, 2430149, 200,//mounts 
        2340000, 1, //rares
        1152000, 5, 1152001, 5, 1152004, 5, 1152005, 5, 1152006, 5, 1152007, 5, 1152008, 5, //toenail only comes when db is out.
        1152064, 5, 1152065, 5, 1152066, 5, 1152067, 5, 1152070, 5, 1152071, 5, 1152072, 5, 1152073, 5,
        3010019, 2, //chairs
        1001060, 10, 1002391, 10, 1102004, 10, 1050039, 10, 1102040, 10, 1102041, 10, 1102042, 10, 1102043, 10, //equips
        1082145, 5, 1082146, 5, 1082147, 5, 1082148, 5, 1082149, 5, 1082150, 5, //wg
        2043704, 10, 2040904, 10, 2040409, 10, 2040307, 10, 2041030, 10, 2040015, 10, 2040109, 10, 2041035, 10, 2041036, 10, 2040009, 10, 2040511, 10, 2040408, 10, 2043804, 10, 2044105, 10, 2044903, 10, 2044804, 10, 2043009, 10, 2043305, 10, 2040610, 10, 2040716, 10, 2041037, 10, 2043005, 10, 2041032, 10, 2040305, 10, //scrolls
        2040211, 5, 2040212, 5, 1022097, 10, //dragon glasses
        2049000, 10, 2049001, 10, 2049002, 10, 2049003, 10, //clean slate
        1012058, 5, 1012059, 5, 1012060, 5, 1012061, 5,//pinocchio nose msea only.
        1332100, 10, 1382058, 10, 1402073, 10, 1432066, 10, 1442090, 10, 1452058, 10, 1462076, 10, 1472069, 10, 1482051, 10, 1492024, 10, 1342009, 10, //durability weapons level 105
        2049400, 1, 2049401, 2, 2049301, 2,
        2049100, 10,
        2430144, 10,
        2290285, 10,
        2028062, 10,
        2028061, 10,
        2530000, 5,
        2531000, 5,
        1032080, 5,
        1032081, 4,
        1032082, 3,
        1032083, 2,
        1032084, 1,
        1112435, 5,
        1112436, 4,
        1112437, 3,
        1112438, 2,
        1112439, 1,
        1122081, 5,
        1122082, 4,
        1122083, 3,
        1122084, 2,
        1122085, 1,
        1132036, 5,
        1132037, 4,
        1132038, 3,
        1132039, 2,
        1132040, 1,
        //source
        1092070, 5,
        1092071, 4,
        1092072, 3,
        1092073, 2,
        1092074, 1,
        1092075, 5,
        1092076, 4,
        1092077, 3,
        1092078, 2,
        1092079, 1,
        1092080, 5,
        1092081, 4,
        1092082, 3,
        1092083, 2,
        1092084, 1,
        1092087, 1,
        1092088, 1,
        1092089, 1,
        1302143, 5,
        1302144, 4,
        1302145, 3,
        1302146, 2,
        1302147, 1,
        1312058, 5,
        1312059, 4,
        1312060, 3,
        1312061, 2,
        1312062, 1,
        1322086, 5,
        1322087, 4,
        1322088, 3,
        1322089, 2,
        1322090, 1,
        1332116, 5,
        1332117, 4,
        1332118, 3,
        1332119, 2,
        1332120, 1,
        1332121, 5,
        1332122, 4,
        1332123, 3,
        1332124, 2,
        1332125, 1,
        1342029, 5,
        1342030, 4,
        1342031, 3,
        1342032, 2,
        1342033, 1,
        1372074, 5,
        1372075, 4,
        1372076, 3,
        1372077, 2,
        1372078, 1,
        1382095, 5,
        1382096, 4,
        1382097, 3,
        1382098, 2,
        1392099, 1,
        1402086, 5,
        1402087, 4,
        1402088, 3,
        1402089, 2,
        1402090, 1,
        1412058, 5,
        1412059, 4,
        1412060, 3,
        1412061, 2,
        1412062, 1,
        1422059, 5,
        1422060, 4,
        1422061, 3,
        1422062, 2,
        1422063, 1,
        1432077, 5,
        1432078, 4,
        1432079, 3,
        1432080, 2,
        1432081, 1,
        1442107, 5,
        1442108, 4,
        1442109, 3,
        1442110, 2,
        1442111, 1,
        1452102, 5,
        1452103, 4,
        1452104, 3,
        1452105, 2,
        1452106, 1,
        1462087, 5,
        1462088, 4,
        1462089, 3,
        1462090, 2,
        1462091, 1,
        1472113, 5,
        1472114, 4,
        1472115, 3,
        1472116, 2,
        1472117, 1,
        1482075, 5,
        1482076, 4,
        1482077, 3,
        1482078, 2,
        1482079, 1,
        1492075, 5,
        1492076, 4,
        1492077, 3,
        1492078, 2,
        1492079, 1,
        1132012, 2,
        1132013, 1,
        1942002, 2,
        1952002, 2,
        1962002, 2,
        1972002, 2,
        1612004, 2,
        1622004, 2,
        1632004, 2,
        1642004, 2,
        1652004, 2,
        2047000, 1,
        2047001, 1,
        2047002, 1,
        2047100, 1,
        2047101, 1,
        2047102, 1,
        2047200, 1,
        2047201, 1,
        2047202, 1,
        2047203, 1,
        2047204, 1,
        2047205, 1,
        2047206, 1,
        2047207, 1,
        2047208, 1,
        2047300, 1,
        2047301, 1,
        2047302, 1,
        2047303, 1,
        2047304, 1,
        2047305, 1,
        2047306, 1,
        2047307, 1,
        2047308, 1,
        2047309, 1,
        2046004, 1,
        2046005, 1,
        2046104, 1,
        2046105, 1,
        2046208, 1,
        2046209, 1,
        2046210, 1,
        2046211, 1,
        2046212, 1,
        2049200, 1,
        2049201, 1,
        2049202, 1,
        2049203, 1,
        2049204, 1,
        2049205, 1,
        2049206, 1,
        2049207, 1,
        2049208, 1,
        2049209, 1,
        2049210, 1,
        2049211, 1,
        //ele wand
        1372035, 1,
        1372036, 1,
        1372037, 1,
        1372038, 1,
        //ele staff
        1382045, 1,
        1382046, 1,
        1382047, 1,
        1382048, 1,
        1382049, 1,
        1382050, 1, // Blue Dragon Staff
        1382051, 1,
        1382052, 1,
        1372039, 1,
        1372040, 1,
        1372041, 1,
        1372042, 1,
        2070016, 1,
        2070007, 2,
        2330007, 1,
        2070018, 1,
        2330008, 1,
        2070023, 1,
        2070024, 1,
        2028062, 5,
        2028061, 5};
    public static int[] eventCommonReward = {
        0, 10,
        1, 10,
        4, 5,
        5060004, 25,
        4170024, 25,
        4280000, 5,
        4280001, 6,
        5490000, 5,
        5490001, 6
    };
    public static int[] eventUncommonReward = {
        1, 4,
        2, 8,
        3, 8,
        2022179, 5,
        5062000, 20,
        2430082, 20,
        2430092, 20,
        2022459, 2,
        2022460, 1,
        2022462, 1,
        2430103, 2,
        2430117, 2,
        2430118, 2,
        2430201, 4,
        2430228, 4,
        2430229, 4,
        2430283, 4,
        2430136, 4,
        2430476, 4,
        2430511, 4,
        2430206, 4,
        2430199, 1,
        1032062, 5,
        5220000, 28,
        2022459, 5,
        2022460, 5,
        2022461, 5,
        2022462, 5,
        2022463, 5,
        5050000, 2,
        4080100, 10,
        4080000, 10,
        2049100, 10,
        2430144, 10,
        2290285, 10,
        2028062, 10,
        2028061, 10,
        2530000, 5,
        2531000, 5,
        2041100, 1,
        2041101, 1,
        2041102, 1,
        2041103, 1,
        2041104, 1,
        2041105, 1,
        2041106, 1,
        2041107, 1,
        2041108, 1,
        2041109, 1,
        2041110, 1,
        2041111, 1,
        2041112, 1,
        2041113, 1,
        2041114, 1,
        2041115, 1,
        2041116, 1,
        2041117, 1,
        2041118, 1,
        2041119, 1,
        2041300, 1,
        2041301, 1,
        2041302, 1,
        2041303, 1,
        2041304, 1,
        2041305, 1,
        2041306, 1,
        2041307, 1,
        2041308, 1,
        2041309, 1,
        2041310, 1,
        2041311, 1,
        2041312, 1,
        2041313, 1,
        2041314, 1,
        2041315, 1,
        2041316, 1,
        2041317, 1,
        2041318, 1,
        2041319, 1,
        2049200, 1,
        2049201, 1,
        2049202, 1,
        2049203, 1,
        2049204, 1,
        2049205, 1,
        2049206, 1,
        2049207, 1,
        2049208, 1,
        2049209, 1,
        2049210, 1,
        2049211, 1
    };
    public static int[] eventRareReward = {
        2049100, 5,
        2430144, 5,
        2290285, 5,
        2028062, 5,
        2028061, 5,
        2530000, 2,
        2531000, 2,
        2049116, 1,
        2049401, 10,
        2049301, 20,
        2049400, 3,
        2340000, 1,
        3010130, 5,
        3010131, 5,
        3010132, 5,
        3010133, 5,
        3010136, 5,
        3010116, 5,
        3010117, 5,
        3010118, 5,
        1112405, 1,
        1112445, 1,
        1022097, 1,
        2040211, 1,
        2040212, 1,
        2049000, 2,
        2049001, 2,
        2049002, 2,
        2049003, 2,
        1012058, 2,
        1012059, 2,
        1012060, 2,
        1012061, 2,
        2022460, 4,
        2022461, 3,
        2022462, 4,
        2022463, 3,
        2040041, 1,
        2040042, 1,
        2040334, 1,
        2040430, 1,
        2040538, 1,
        2040539, 1,
        2040630, 1,
        2040740, 1,
        2040741, 1,
        2040742, 1,
        2040829, 1,
        2040830, 1,
        2040936, 1,
        2041066, 1,
        2041067, 1,
        2043023, 1,
        2043117, 1,
        2043217, 1,
        2043312, 1,
        2043712, 1,
        2043812, 1,
        2044025, 1,
        2044117, 1,
        2044217, 1,
        2044317, 1,
        2044417, 1,
        2044512, 1,
        2044612, 1,
        2044712, 1,
        2046000, 1,
        2046001, 1,
        2046004, 1,
        2046005, 1,
        2046100, 1,
        2046101, 1,
        2046104, 1,
        2046105, 1,
        2046200, 1,
        2046201, 1,
        2046202, 1,
        2046203, 1,
        2046208, 1,
        2046209, 1,
        2046210, 1,
        2046211, 1,
        2046212, 1,
        2046300, 1,
        2046301, 1,
        2046302, 1,
        2046303, 1,
        2047000, 1,
        2047001, 1,
        2047002, 1,
        2047100, 1,
        2047101, 1,
        2047102, 1,
        2047200, 1,
        2047201, 1,
        2047202, 1,
        2047203, 1,
        2047204, 1,
        2047205, 1,
        2047206, 1,
        2047207, 1,
        2047208, 1,
        2047300, 1,
        2047301, 1,
        2047302, 1,
        2047303, 1,
        2047304, 1,
        2047305, 1,
        2047306, 1,
        2047307, 1,
        2047308, 1,
        2047309, 1,
        1112427, 5,
        1112428, 5,
        1112429, 5,
        1012240, 10,
        1022117, 10,
        1032095, 10,
        1112659, 10,
        2070007, 10,
        2330007, 5,
        2070016, 5,
        2070018, 5,
        1152038, 1,
        1152039, 1,
        1152040, 1,
        1152041, 1,
        1122090, 1,
        1122094, 1,
        1122098, 1,
        1122102, 1,
        1012213, 1,
        1012219, 1,
        1012225, 1,
        1012231, 1,
        1012237, 1,
        2070023, 5,
        2070024, 5,
        2330008, 5,
        2003516, 5,
        2003517, 1,
        1132052, 1,
        1132062, 1,
        1132072, 1,
        1132082, 1,
        1112585, 1,
        //walker
        1072502, 1,
        1072503, 1,
        1072504, 1,
        1072505, 1,
        1072506, 1,
        1052333, 1,
        1052334, 1,
        1052335, 1,
        1052336, 1,
        1052337, 1,
        1082305, 1,
        1082306, 1,
        1082307, 1,
        1082308, 1,
        1082309, 1,
        1003197, 1,
        1003198, 1,
        1003199, 1,
        1003200, 1,
        1003201, 1,
        1662000, 1,
        1662001, 1,
        1672000, 1,
        1672001, 1,
        1672002, 1,
        //crescent moon
        1112583, 1,
        1032092, 1,
        1132084, 1,
        //mounts, 90 day
        2430290, 1,
        2430292, 1,
        2430294, 1,
        2430296, 1,
        2430298, 1,
        2430300, 1,
        2430302, 1,
        2430304, 1,
        2430306, 1,
        2430308, 1,
        2430310, 1,
        2430312, 1,
        2430314, 1,
        2430316, 1,
        2430318, 1,
        2430320, 1,
        2430322, 1,
        2430324, 1,
        2430326, 1,
        2430328, 1,
        2430330, 1,
        2430332, 1,
        2430334, 1,
        2430336, 1,
        2430338, 1,
        2430340, 1,
        2430342, 1,
        2430344, 1,
        2430347, 1,
        2430349, 1,
        2430351, 1,
        2430353, 1,
        2430355, 1,
        2430357, 1,
        2430359, 1,
        2430361, 1,
        2430392, 1,
        2430512, 1,
        2430536, 1,
        2430477, 1,
        2430146, 1,
        2430148, 1,
        2430137, 1,};
    public static int[] eventSuperReward = {
        2022121, 10,
        4031307, 50,
        3010127, 10,
        3010128, 10,
        3010137, 10,
        3010157, 10,
        2049300, 10,
        2040758, 10,
        1442057, 10,
        2049402, 10,
        2049304, 1,
        2049305, 1,
        2040759, 7,
        2040760, 5,
        2040125, 10,
        2040126, 10,
        1012191, 5,
        1112514, 1, //untradable/tradable
        1112531, 1,
        1112629, 1,
        1112646, 1,
        1112515, 1, //untradable/tradable
        1112532, 1,
        1112630, 1,
        1112647, 1,
        1112516, 1, //untradable/tradable
        1112533, 1,
        1112631, 1,
        1112648, 1,
        2040045, 10,
        2040046, 10,
        2040333, 10,
        2040429, 10,
        2040542, 10,
        2040543, 10,
        2040629, 10,
        2040755, 10,
        2040756, 10,
        2040757, 10,
        2040833, 10,
        2040834, 10,
        2041068, 10,
        2041069, 10,
        2043022, 12,
        2043120, 12,
        2043220, 12,
        2043313, 12,
        2043713, 12,
        2043813, 12,
        2044028, 12,
        2044120, 12,
        2044220, 12,
        2044320, 12,
        2044520, 12,
        2044513, 12,
        2044613, 12,
        2044713, 12,
        2044817, 12,
        2044910, 12,
        2046002, 5,
        2046003, 5,
        2046102, 5,
        2046103, 5,
        2046204, 10,
        2046205, 10,
        2046206, 10,
        2046207, 10,
        2046304, 10,
        2046305, 10,
        2046306, 10,
        2046307, 10,
        2040006, 2,
        2040007, 2,
        2040303, 2,
        2040403, 2,
        2040506, 2,
        2040507, 2,
        2040603, 2,
        2040709, 2,
        2040710, 2,
        2040711, 2,
        2040806, 2,
        2040903, 2,
        2040913, 2,
        2041024, 2,
        2041025, 2,
        2044815, 2,
        2044908, 2,
        1152046, 1,
        1152047, 1,
        1152048, 1,
        1152049, 1,
        1122091, 1,
        1122095, 1,
        1122099, 1,
        1122103, 1,
        1012214, 1,
        1012220, 1,
        1012226, 1,
        1012232, 1,
        1012238, 1,
        1032088, 1,
        1032089, 1,
        1032090, 1,
        1032091, 1,
        1132053, 1,
        1132063, 1,
        1132073, 1,
        1132083, 1,
        1112586, 1,
        1112593, 1,
        1112597, 1,
        1662002, 1,
        1662003, 1,
        1672003, 1,
        1672004, 1,
        1672005, 1,
        //130, 140 weapons
        1092088, 1,
        1092089, 1,
        1092087, 1,
        1102275, 1,
        1102276, 1,
        1102277, 1,
        1102278, 1,
        1102279, 1,
        1102280, 1,
        1102281, 1,
        1102282, 1,
        1102283, 1,
        1102284, 1,
        1082295, 1,
        1082296, 1,
        1082297, 1,
        1082298, 1,
        1082299, 1,
        1082300, 1,
        1082301, 1,
        1082302, 1,
        1082303, 1,
        1082304, 1,
        1072485, 1,
        1072486, 1,
        1072487, 1,
        1072488, 1,
        1072489, 1,
        1072490, 1,
        1072491, 1,
        1072492, 1,
        1072493, 1,
        1072494, 1,
        1052314, 1,
        1052315, 1,
        1052316, 1,
        1052317, 1,
        1052318, 1,
        1052319, 1,
        1052329, 1,
        1052321, 1,
        1052322, 1,
        1052323, 1,
        1003172, 1,
        1003173, 1,
        1003174, 1,
        1003175, 1,
        1003176, 1,
        1003177, 1,
        1003178, 1,
        1003179, 1,
        1003180, 1,
        1003181, 1,
        1302152, 1,
        1302153, 1,
        1312065, 1,
        1312066, 1,
        1322096, 1,
        1322097, 1,
        1332130, 1,
        1332131, 1,
        1342035, 1,
        1342036, 1,
        1372084, 1,
        1372085, 1,
        1382104, 1,
        1382105, 1,
        1402095, 1,
        1402096, 1,
        1412065, 1,
        1412066, 1,
        1422066, 1,
        1422067, 1,
        1432086, 1,
        1432087, 1,
        1442116, 1,
        1442117, 1,
        1452111, 1,
        1452112, 1,
        1462099, 1,
        1462100, 1,
        1472122, 1,
        1472123, 1,
        1482084, 1,
        1482085, 1,
        1492085, 1,
        1492086, 1,
        1532017, 1,
        1532018, 1,
        //mounts
        2430291, 1,
        2430293, 1,
        2430295, 1,
        2430297, 1,
        2430299, 1,
        2430301, 1,
        2430303, 1,
        2430305, 1,
        2430307, 1,
        2430309, 1,
        2430311, 1,
        2430313, 1,
        2430315, 1,
        2430317, 1,
        2430319, 1,
        2430321, 1,
        2430323, 1,
        2430325, 1,
        2430327, 1,
        2430329, 1,
        2430331, 1,
        2430333, 1,
        2430335, 1,
        2430337, 1,
        2430339, 1,
        2430341, 1,
        2430343, 1,
        2430345, 1,
        2430348, 1,
        2430350, 1,
        2430352, 1,
        2430354, 1,
        2430356, 1,
        2430358, 1,
        2430360, 1,
        2430362, 1,
        //rising sun
        1012239, 1,
        1122104, 1,
        1112584, 1,
        1032093, 1,
        1132085, 1
    };
    public static int[] tenPercent = {
        //10% scrolls
        2040002,
        2040005,
        2040026,
        2040031,
        2040100,
        2040105,
        2040200,
        2040205,
        2040302,
        2040310,
        2040318,
        2040323,
        2040328,
        2040329,
        2040330,
        2040331,
        2040402,
        2040412,
        2040419,
        2040422,
        2040427,
        2040502,
        2040505,
        2040514,
        2040517,
        2040534,
        2040602,
        2040612,
        2040619,
        2040622,
        2040627,
        2040702,
        2040705,
        2040708,
        2040727,
        2040802,
        2040805,
        2040816,
        2040825,
        2040902,
        2040915,
        2040920,
        2040925,
        2040928,
        2040933,
        2041002,
        2041005,
        2041008,
        2041011,
        2041014,
        2041017,
        2041020,
        2041023,
        2041058,
        2041102,
        2041105,
        2041108,
        2041111,
        2041302,
        2041305,
        2041308,
        2041311,
        2043002,
        2043008,
        2043019,
        2043102,
        2043114,
        2043202,
        2043214,
        2043302,
        2043402,
        2043702,
        2043802,
        2044002,
        2044014,
        2044015,
        2044102,
        2044114,
        2044202,
        2044214,
        2044302,
        2044314,
        2044402,
        2044414,
        2044502,
        2044602,
        2044702,
        2044802,
        2044809,
        2044902,
        2045302,
        2048002,
        2048005
    };
    public static int[] fishingReward = {
        0, 100, // Meso
        1, 100, // EXP
        4350000, 80, //枫叶点數
        4350001, 5, //紅利点數
        4031408, 10,//圖章道具
        4031627, 30,
        4031628, 30,
        4031630, 30,
        4031631, 30,
        4031633, 30,
        4031634, 30,
        4031635, 30,
        4031636, 30,
        4031637, 30,
        4031638, 30,
        4031639, 30,
        4031640, 30,
        4031641, 30,
        4031642, 30,
        4031643, 30,
        4031644, 30,
        4031645, 30,
        4031646, 30,
        4031647, 30,
        4031648, 30,
        2043001, 3,
        2043101, 3,
        2043201, 3,
        2043301, 3,
        2043401, 3,
        2043701, 3,
        2043801, 3,
        2044001, 3,
        2044101, 3,
        2044201, 3,
        2044301, 3,
        2044401, 3,
        2044501, 3,
        2044601, 3,
        2044701, 3,
        2044801, 3,
        2044901, 3,
        2101120, 3,
        1102041, 1,
        1102042, 1,
        3015544, 1,
        4031629, 5,
        1102030, 5,
        1102029, 5,
        1102026, 5,
        1102120, 5,
        1002603, 5,
        1002602, 5,
        1002601, 5,
        1002600, 5,
        1102060, 5,
        1102115, 5, //4001200, 30, 
    /*2022179, 1, // Onyx Apple
        1302021, 5, // Pico Pico Hammer
        1072238, 1, // Voilet Snowshoe
        1072239, 1, // Yellow Snowshoe
        2049100, 2, // Chaos Scroll
        5062000, 1,
        4000188, 1,
        5072000, 3,
        5076000, 3,
        2430144, 1,
        2290285, 1,
        2028062, 1,
        2028061, 1,
        2049301, 1, // Equip Enhancer Scroll
        5062001, 1,
        5062002, 1,
        1302000, 3, // Sword
        1442011, 1, // Surfboard
        4000517, 8, // Golden Fish
        4000518, 10, // Golden Fish Egg
        4031627, 2, // White Bait (3cm)
        4031628, 1, // Sailfish (120cm)
        4031630, 1, // Carp (30cm)
        4031631, 1, // Salmon(150cm)
        4031632, 1, // Shovel
        4031633, 2, // Whitebait (3.6cm)
        4031634, 1, // Whitebait (5cm)
        4031635, 1, // Whitebait (6.5cm)
        4031636, 1, // Whitebait (10cm)
        4031637, 2, // Carp (53cm)
        4031638, 2, // Carp (60cm)
        4031639, 1, // Carp (100cm)
        4031640, 1, // Carp (113cm)
        4031641, 2, // Sailfish (128cm)
        4031642, 2, // Sailfish (131cm)
        4031643, 1, // Sailfish (140cm)
        4031644, 1, // Sailfish (148cm)
        4031645, 2, // Salmon (166cm)
        4031646, 2, // Salmon (183cm)
        4031647, 1, // Salmon (227cm)
        4031648, 1, // Salmon (288cm)
        4001187, 20,
        4001188, 20,
        4001189, 20,
        4031629, 1  */ // Pot
    };

    public static boolean isReverseItem(int itemId) {
        switch (itemId) {
            case 1002790:
            case 1002791:
            case 1002792:
            case 1002793:
            case 1002794:
            case 1082239:
            case 1082240:
            case 1082241:
            case 1082242:
            case 1082243:
            case 1052160:
            case 1052161:
            case 1052162:
            case 1052163:
            case 1052164:
            case 1072361:
            case 1072362:
            case 1072363:
            case 1072364:
            case 1072365:

            case 1302086:
            case 1312038:
            case 1322061:
            case 1332075:
            case 1332076:
            case 1372045:
            case 1382059:
            case 1402047:
            case 1412034:
            case 1422038:
            case 1432049:
            case 1442067:
            case 1452059:
            case 1462051:
            case 1472071:
            case 1482024:
            case 1492025:

            case 1342012:
            case 1942002:
            case 1952002:
            case 1962002:
            case 1972002:
            case 1532016:
            case 1522017:
                return true;
            default:
                return false;
        }
    }

    public static boolean isTimelessItem(int itemId) {
        switch (itemId) {
            case 1032031: //shield earring, but technically
            case 1102172:
            case 1002776:
            case 1002777:
            case 1002778:
            case 1002779:
            case 1002780:
            case 1082234:
            case 1082235:
            case 1082236:
            case 1082237:
            case 1082238:
            case 1052155:
            case 1052156:
            case 1052157:
            case 1052158:
            case 1052159:
            case 1072355:
            case 1072356:
            case 1072357:
            case 1072358:
            case 1072359:
            case 1092057:
            case 1092058:
            case 1092059:

            case 1122011:
            case 1122012:

            case 1302081:
            case 1312037:
            case 1322060:
            case 1332073:
            case 1332074:
            case 1372044:
            case 1382057:
            case 1402046:
            case 1412033:
            case 1422037:
            case 1432047:
            case 1442063:
            case 1452057:
            case 1462050:
            case 1472068:
            case 1482023:
            case 1492023:
            case 1342011:
            case 1532015:
            case 1522016:
                //raven.
                return true;
            default:
                return false;
        }
    }

    public static boolean isRing(int itemId) {
        return itemId >= 1112000 && itemId < 1113000;
    }// 112xxxx - pendants, 113xxxx - belts

    //if only there was a way to find in wz files -.-
    public static boolean isEffectRing(int itemid) {
        return isFriendshipRing(itemid) || isCrushRing(itemid) || isMarriageRing(itemid);
    }

    public static boolean isMarriageRing(int itemId) {
        switch (itemId) {
            case 1112300:
            case 1112301:
            case 1112302:
            case 1112303:
            case 1112304:
            case 1112305:
            case 1112306:
            case 1112307:
            case 1112308:
            case 1112309:
            case 1112310:
            case 1112311:
            case 1112315:
            case 1112316:
            case 1112317:
            case 1112318:
            case 1112319:
            case 1112320:
            case 1112803:

            case 1112806:
            case 1112807:
            case 1112808:
            case 1112809:
            case 1112313:
            case 1112314:
                return true;
        }
        return false;
    }

    public static boolean isFriendshipRing(int itemId) {
        switch (itemId) {
            case 1112800:
            case 1112801:
            case 1112802:
            case 1112804:
            case 1112810: //new
            case 1112811: //new, doesnt work in friendship?
            case 1112812: //new, im ASSUMING it's friendship cuz of itemID, not sure.
            case 1112015:
            case 1112816:
            case 1112817:
            case 1112822:
            case 1049000:
            case 1112820:
                return true;
        }
        return false;
    }

    public static boolean isCrushRing(int itemId) {
        switch (itemId) {
            case 1112001:
            case 1112002:
            case 1112003:
            case 1112005:
            case 1112006:
            case 1112007:
            case 1112012:
            case 1112013: // 愛情紅线戒指
            case 1112015:
            case 1048000:
            case 1048001:
            case 1048002:
            case 1112016:
                return true;
        }
        return false;
    }
    public static int[] Equipments_Bonus = {1122017, 1112918};

    public static int Equipment_Bonus_EXP(final int itemid) { // TODO : Add Time for more exp increase
        switch (itemid) {
            case 1122017:
            case 1112918:
                return 30;
        }
        return 0;
    }
    public static int[] blockedMaps = {180000001, 180000002, 109050000, 280030000, 240060200, 280090000, 280030001, 240060201, 950101100, 950101010, 222010401, 109050000, 200000112, 200090020, 240060200, 280030000, 280090000, 280030001, 240060201, 900090021, 950101100, 950101010, 109050000, 200000112, 200090020, 240060200, 280030000, 280090000, 280030001, 240060201, 900090021, 950101100, 950101010};
    //If you can think of more maps that could be exploitable via npc,block nao pliz!

    public static int getExpForLevel(int i, int itemId) {
        if (isReverseItem(itemId)) {
            return getReverseRequiredEXP(i);
        } else if (getMaxLevel(itemId) > 0) {
            return getTimelessRequiredEXP(i);
        }
        return 0;
    }

    public static int getMaxLevel(final int itemId) {
        Map<Integer, Map<String, Integer>> inc = MapleItemInformationProvider.getInstance().getEquipIncrements(itemId);
        return inc != null ? (inc.size()) : 0;
    }

    public static int getStatChance() {
        return 25;
    }

    public static MonsterStatus getStatFromWeapon(final int itemid) {
        switch (itemid) {
            case 1302109:
            case 1312041:
            case 1322067:
            case 1332083:
            case 1372048:
            case 1382064:
            case 1402055:
            case 1412037:
            case 1422041:
            case 1432052:
            case 1442073:
            case 1452064:
            case 1462058:
            case 1472079:
            case 1482035:
                return MonsterStatus.DARKNESS;
            case 1302108:
            case 1312040:
            case 1322066:
            case 1332082:
            case 1372047:
            case 1382063:
            case 1402054:
            case 1412036:
            case 1422040:
            case 1432051:
            case 1442072:
            case 1452063:
            case 1462057:
            case 1472078:
            case 1482036:
                return MonsterStatus.SPEED;
        }
        return null;
    }

    public static int getXForStat(MonsterStatus stat) {
        switch (stat) {
            case DARKNESS:
                return -70;
            case SPEED:
                return -50;
        }
        return 0;
    }

    public static int getSkillForStat(MonsterStatus stat) {
        switch (stat) {
            case DARKNESS:
                return 1111003;
            case SPEED:
                return 3121007;
        }
        return 0;
    }
    public final static int[] normalDrops = {
        4001009, //real
        4001010,
        4001011,
        4001012,
        4001013,
        4001014, //real
        4001021,
        4001038, //fake
        4001039,
        4001040,
        4001041,
        4001042,
        4001043, //fake
        4001038, //fake
        4001039,
        4001040,
        4001041,
        4001042,
        4001043, //fake
        4001038, //fake
        4001039,
        4001040,
        4001041,
        4001042,
        4001043, //fake
        4000164, //start
        2000000,
        2000003,
        2000004,
        2000005,
        4000019,
        4000000,
        4000016,
        4000006,
        2100121,
        4000029,
        4000064,
        5110000,
        4000306,
        4032181,
        4006001,
        4006000,
        2050004,
        3994102,
        3994103,
        3994104,
        3994105,
        2430007, //end
        4000164, //start
        2000000,
        2000003,
        2000004,
        2000005,
        4000019,
        4000000,
        4000016,
        4000006,
        2100121,
        4000029,
        4000064,
        5110000,
        4000306,
        4032181,
        4006001,
        4006000,
        2050004,
        3994102,
        3994103,
        3994104,
        3994105,
        2430007, //end
        4000164, //start
        2000000,
        2000003,
        2000004,
        2000005,
        4000019,
        4000000,
        4000016,
        4000006,
        2100121,
        4000029,
        4000064,
        5110000,
        4000306,
        4032181,
        4006001,
        4006000,
        2050004,
        3994102,
        3994103,
        3994104,
        3994105,
        2430007}; //end
    public final static int[] rareDrops = {
        2022179,
        2049100,
        2049100,
        5062002,
        5062001,
        2430144,
        2028062,
        2028061,
        2290285,
        2049301,
        2049401,
        2022326,
        2022193,
        2049000,
        2049001,
        2049002};
    public final static int[] superDrops = {
        2040804,
        2049400,
        2028062,
        2028061,
        2430144,
        2430144,
        2430144,
        2430144,
        2290285,
        2049100,
        2049100,
        2049100,
        2049100};

    public static int getSkillBook(final int job) {
        if (job >= 2210 && job <= 2218) {
            return job - 2209;
        }
        switch (job) {
            case 2310:
            case 3110:
            case 3210:
            case 3310:
            case 3510:
                return 1;
            case 2311:
            case 3111:
            case 3211:
            case 3311:
            case 3511:
                return 2;
            case 2312:
            case 3112:
            case 3212:
            case 3312:
            case 3512:
                return 3;
        }
        return 0;
    }

    public static int getSkillBook(final int job, final int level) {
        if (job >= 2210 && job <= 2218) {
            return job - 2209;
        }
        switch (job) {
            case 2300:
            case 2310:
            case 2311:
            case 2312:
            case 3100:
            case 3200:
            case 3300:
            case 3500:
            case 3110:
            case 3210:
            case 3310:
            case 3510:
            case 3111:
            case 3211:
            case 3311:
            case 3511:
            case 3112:
            case 3212:
            case 3312:
            case 3512:
                return (level <= 30 ? 0 : (level >= 31 && level <= 70 ? 1 : (level >= 71 && level <= 120 ? 2 : (level >= 120 ? 3 : 0))));
        }
        return 0;
    }

    public static int getSkillBookForSkill(final int skillid) {
        return getSkillBook(skillid / 10000);
    }

    public static int getLinkedMountItem(final int sourceid) {
        switch (sourceid % 1000) {
            case 1:
            case 24:
            case 25:
                return 1018;
            case 2:
            case 26:
                return 1019;
            case 3:
                return 1025;
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                return (sourceid % 1000) + 1023;
            case 9:
            case 10:
            case 11:
                return (sourceid % 1000) + 1024;
            case 12:
                return 1042;
            case 13:
                return 1044;
            case 14:
                return 1049;
            case 15:
            case 16:
            case 17:
                return (sourceid % 1000) + 1036;
            case 18:
            case 19:
                return (sourceid % 1000) + 1045;
            case 20:
                return 1072;
            case 21:
                return 1084;
            case 22:
                return 1089;
            case 23:
                return 1106;
            case 29:
                return 1151;
            case 30:
            case 50:
                return 1054;
            case 31:
            case 51:
                return 1069;
            case 32:
                return 1138;
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
                return (sourceid % 1000) + 1009;
            case 52:
                return 1070;
            case 53:
                return 1071;
            case 54:
                return 1096;
            case 55:
                return 1101;
            case 56:
                return 1102;
            case 58:
                return 1118;
            case 59:
                return 1121;
            case 60:
                return 1122;
            case 61:
                return 1129;
            case 62:
                return 1139;
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
            case 68:
            case 69:
            case 70:
            case 71:
            case 72:
            case 73:
            case 74:
            case 75:
            case 76:
            case 77:
            case 78:
                return (sourceid % 1000) + 1080;
            case 85:
            case 86:
            case 87:
                return (sourceid % 1000) + 928;
            case 88:
                return 1065;

            case 27:
                return 1932049; //airplane
            case 28:
                return 1932050; //airplane
            case 114:
                return 1932099; //bunny buddy
            //33 = hot air
            //37 = bjorn
            //38 = speedy chariot
            //57 = law officer
            //they all have in wz so its ok
        }
        return 0;
    }

    public static int getMountItem(final int sourceid, final MapleCharacter chr) {
        switch (sourceid) {
            case 5221006:
                return 1932000;
            case 33001001: //temp.
                if (chr == null) {
                    return 1932015;
                }
                switch (chr.getIntNoRecord(JAGUAR)) {
                    case 20:
                        return 1932030;
                    case 30:
                        return 1932031;
                    case 40:
                        return 1932032;
                    case 50:
                        return 1932033;
                    case 60:
                        return 1932036;
                }
                return 1932015;
            case 35001002:
            case 35120000:
                return 1932016;
        }
        if (!isBeginnerJob(sourceid / 10000)) {
            if (sourceid / 10000 == 8000 && sourceid != 80001000) { //todoo clean up
                final Skill skil = SkillFactory.getSkill(sourceid);
                if (skil != null && skil.getTamingMob() > 0) {
                    return skil.getTamingMob();
                } else {
                    final int link = getLinkedMountItem(sourceid);
                    if (link > 0) {
                        if (link < 10000) {
                            return getMountItem(link, chr);
                        } else {
                            return link;
                        }
                    }
                }
            }
            return 0;
        }
        switch (sourceid % 10000) {
            case 1013:
            case 1046:
                return 1932001;
            case 1015:
            case 1048:
                return 1932002;
            case 1016:
            case 1017:
            case 1027:
                return 1932007;
            case 1018:
                return 1932003;
            case 1019:
                return 1932005;
            case 1025:
                return 1932006;
            case 1028:
                return 1932008;
            case 1029:
                return 1932009;
            case 1030:
                return 1932011;
            case 1031:
                return 1932010;
            case 1033:
                return 1932013;
            case 1034:
                return 1932014;
            case 1035:
                return 1932012;
            case 1036:
                return 1932017;
            case 1037:
                return 1932018;
            case 1038:
                return 1932019;
            case 1039:
                return 1932020;
            case 1040:
                return 1932021;
            case 1042:
                return 1932022;
            case 1044:
                return 1932023;
            //case 1045:
            //return 1932030; //wth? helicopter? i didnt see one, so we use hog
            case 1049:
                return 1932025;
            case 1050:
                return 1932004;
            case 1051:
                return 1932026;
            case 1052:
                return 1932027;
            case 1053:
                return 1932028;
            case 1054:
                return 1932029;
            case 1063:
                return 1932034;
            case 1064:
                return 1932035;
            case 1065:
                return 1932037;
            case 1069:
                return 1932038;
            case 1070:
                return 1932039;
            case 1071:
                return 1932040;
            case 1072:
                return 1932041;
            case 1084:
                return 1932043;
            case 1089:
                return 1932044;
            case 1096:
                return 1932045;
            case 1101:
                return 1932046;
            case 1102:
                return GMS ? 1932061 : 1932047;
            case 1106:
                return 1932048;
            case 1118:
                return 1932060;
            case 1115:
                return 1932052;
            case 1121:
                return 1932063;
            case 1122:
                return 1932064;
            case 1123:
                return 1932065;
            case 1124:
                return 1932066;
            case 1128:
                return 1932066;
            case 1129:
                return 1932071;
            case 1130:
                return 1932072;
            case 1136:
                return 1932078;
            case 1138:
                return 1932080;
            case 1139:
                return 1932081;
            //FLYING
            case 1143:
            case 1144:
            case 1145:
            case 1146:
            case 1147:
            case 1148:
            case 1149:
            case 1150:
            case 1151:
            case 1152:
            case 1153:
            case 1154:
            case 1155:
            case 1156:
            case 1157:
                return 1992000 + (sourceid % 10000) - 1143;
            default:
                return 0;
        }
    }

    public static boolean isKatara(int itemId) {
        return itemId / 10000 == 134;
    }

    public static boolean isDagger(int itemId) {
        return itemId / 10000 == 133;
    }

    public static boolean isApplicableSkill(int skil) {
        return (skil < 40000000 && (skil % 10000 < 8000 || skil % 10000 > 8006) && !isAngel(skil)) || skil >= 92000000 || (skil >= 80000000 && skil < 80010000); //no additional/decent skills
    }

    public static boolean isApplicableSkill_(int skil) { //not applicable to saving but is more of temporary
        for (int i : PlayerStats.pvpSkills) {
            if (skil == i) {
                return true;
            }
        }
        return (skil >= 90000000 && skil < 92000000) || (skil % 10000 >= 8000 && skil % 10000 <= 8003) || isAngel(skil);
    }

    public static boolean isTablet(int itemId) {
        return itemId / 1000 == 2047;
    }

    public static boolean isGeneralScroll(int itemId) {
        return itemId / 1000 == 2046;
    }

    public static int getSuccessTablet(final int scrollId, final int level) {
        if (scrollId % 1000 / 100 == 2) { //2047_2_00 = armor, 2047_3_00 = accessory
            switch (level) {
                case 0:
                    return 70;
                case 1:
                    return 55;
                case 2:
                    return 43;
                case 3:
                    return 33;
                case 4:
                    return 26;
                case 5:
                    return 20;
                case 6:
                    return 16;
                case 7:
                    return 12;
                case 8:
                    return 10;
                default:
                    return 7;
            }
        } else if (scrollId % 1000 / 100 == 3) {
            switch (level) {
                case 0:
                    return 70;
                case 1:
                    return 35;
                case 2:
                    return 18;
                case 3:
                    return 12;
                default:
                    return 7;
            }
        } else {
            switch (level) {
                case 0:
                    return 70;
                case 1:
                    return 50; //-20
                case 2:
                    return 36; //-14
                case 3:
                    return 26; //-10
                case 4:
                    return 19; //-7
                case 5:
                    return 14; //-5
                case 6:
                    return 10; //-4
                default:
                    return 7;  //-3
            }
        }
    }

    public static int getCurseTablet(final int scrollId, final int level) {
        if (scrollId % 1000 / 100 == 2) { //2047_2_00 = armor, 2047_3_00 = accessory
            switch (level) {
                case 0:
                    return 10;
                case 1:
                    return 12;
                case 2:
                    return 16;
                case 3:
                    return 20;
                case 4:
                    return 26;
                case 5:
                    return 33;
                case 6:
                    return 43;
                case 7:
                    return 55;
                case 8:
                    return 70;
                default:
                    return 100;
            }
        } else if (scrollId % 1000 / 100 == 3) {
            switch (level) {
                case 0:
                    return 12;
                case 1:
                    return 18;
                case 2:
                    return 35;
                case 3:
                    return 70;
                default:
                    return 100;
            }
        } else {
            switch (level) {
                case 0:
                    return 10;
                case 1:
                    return 14; //+4
                case 2:
                    return 19; //+5
                case 3:
                    return 26; //+7
                case 4:
                    return 36; //+10
                case 5:
                    return 50; //+14
                case 6:
                    return 70; //+20
                default:
                    return 100;  //+30
            }
        }
    }

    public static boolean isAccessory(final int itemId) {
        return (itemId >= 1010000 && itemId < 1040000) || (itemId >= 1122000 && itemId < 1153000) || (itemId >= 1112000 && itemId < 1113000);
    }

    public static boolean potentialIDFits(final int potentialID, final int newstate, final int i) {
        //first line is always the best
        //but, sometimes it is possible to get second/third line as well
        //may seem like big chance, but it's not as it grabs random potential ID anyway
        if (newstate == 8) {
            return (i == 0 || Randomizer.nextInt(10) == 0 ? potentialID >= 40000 : potentialID >= 30000 && potentialID < 41008); // xml say so
        } else if (newstate == 7) {
            return (i == 0 || Randomizer.nextInt(10) == 0 ? potentialID >= 30000 : potentialID >= 20000 && potentialID < 30000);
        } else if (newstate == 6) {
            return (i == 0 || Randomizer.nextInt(10) == 0 ? potentialID >= 20000 && potentialID < 30000 : potentialID >= 10000 && potentialID < 20000);
        } else if (newstate == 5) {
            return (i == 0 || Randomizer.nextInt(10) == 0 ? potentialID >= 10000 && potentialID < 20000 : potentialID < 10000);
        } else {
            return false;
        }
    }

    public static boolean optionTypeFits(final int optionType, final int itemId) {
        switch (optionType) {
            case 10: //weapon
                return isWeapon(itemId);
            case 11: //any armor
                return !isWeapon(itemId);
            case 20: //shield??????????
                return itemId / 10000 == 109; //just a gues
            case 21: //pet equip?????????
                return itemId / 10000 == 180; //???LOL
            case 40: //face accessory
                return isAccessory(itemId);
            case 51: //hat
                return itemId / 10000 == 100;
            case 52: //cape
                return itemId / 10000 == 110;
            case 53: //top/bottom/overall
                return itemId / 10000 == 104 || itemId / 10000 == 105 || itemId / 10000 == 106;
            case 54: //glove
                return itemId / 10000 == 108;
            case 55: //shoe
                return itemId / 10000 == 107;
            case 90:
                return false; //half this stuff doesnt even work
            default:
                return true;
        }
    }

    public static final boolean isMountItemAvailable(final int mountid, final int jobid) {
        if (jobid != 900 && mountid / 10000 == 190) {
            switch (mountid) {
                case 1902000:
                case 1902001:
                case 1902002:
                    return isAdventurer(jobid);
                case 1902005:
                case 1902006:
                case 1902007:
                    return isKOC(jobid);
                case 1902015:
                case 1902016:
                case 1902017:
                case 1902018:
                    return isAran(jobid);
                case 1902040:
                case 1902041:
                case 1902042:
                    return isEvan(jobid);
            }

            if (isResist(jobid)) {
                return false; //none lolol
            }
        }
        if (mountid / 10000 != 190) {
            return false;
        }
        return true;
    }

    public static boolean isMechanicItem(final int itemId) {
        return itemId >= 1610000 && itemId < 1660000;
    }

    public static boolean isEvanDragonItem(final int itemId) {
        return itemId >= 1940000 && itemId < 1980000; //194 = mask, 195 = pendant, 196 = wings, 197 = tail
    }

    public static boolean canScroll(final int itemId) {
        return ((itemId / 100000 != 19) && (itemId / 100000 != 16)) || ((itemId / 1000 == 1672) && (itemId != 1672030) && (itemId != 1672031) && (itemId != 1672032));
    }

    public static boolean is機械心臟(int itemId) {
        return (itemId / 1000 == 1672) && (itemId != 1672030) && (itemId != 1672031) && (itemId != 1672032);
    }

    public static boolean canHammer(final int itemId) {
        switch (itemId) {
            case 1122000:
            case 1122076: //ht, chaos ht
                return false;
        }
        if (!canScroll(itemId)) {
            return false;
        }
        return true;
    }
    public static int[] owlItems = new int[]{
        1082002, // work gloves
        2070005,
        2070006,
        1022047,
        1102041,
        2044705,
        2340000, // white scroll
        2040017,
        1092030,
        2040804};

    public static int getMasterySkill(final int job) {
        if (job >= 1410 && job <= 1412) {
            return 14100000;
        } else if (job >= 410 && job <= 412) {
            return 4100000;
        } else if (job >= 520 && job <= 522) {
            return 5200000;
        }
        return 0;
    }

    public static int getExpRate_Below10(final int job) {
        if (GameConstants.isEvan(job)) {
            return 1;
        } else if (GameConstants.isAran(job) || GameConstants.isKOC(job) || GameConstants.isResist(job)) {
            return 1;
        }
        return 1;
    }

    public static int getExpRate_Quest(final int level) {
        return (level >= 30 ? (level >= 70 ? (level >= 120 ? 10 : 5) : 2) : 1);
    }

    public static int getCustomReactItem(final int rid, final int original) {
        if (rid == 2008006) { //orbis pq LOL
            return (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) + 4001055);
            //4001056 = sunday. 4001062 = saturday
        } else {
            return original;
        }
    }

    public static int getJobNumber(int jobz) {
        int job = (jobz % 1000);
        if (job / 100 == 0 || isBeginnerJob(jobz)) {
            return 0; //beginner
        } else if ((job / 10) % 10 == 0 || job == 501) {
            return 1;
        } else {
            return 2 + (job % 10);
        }
    }

    public static boolean isBeginnerJob(final int job) {
        return job == 0 || job == 1 || job == 1000 || job == 2000 || job == 2001 || job == 3000 || job == 3001 || job == 2002;
    }

    

    public static boolean isForceRespawn(int mapid) {//多倍怪物地图
        switch (mapid) {
            
          case 910000009:
         //   case 103000800: //kerning PQ crocs
         //   case 925100100: //crocs and stuff
        //    case 100010000:
                return true;
            default:
                return mapid / 100000 == 9800 && (mapid % 10 == 1 || mapid % 1000 == 100);
        }
    }


    public static int getFishingTime(boolean vip, boolean gm) {
        return gm ? 1000 : (vip ? 120000 : 120000);
    }

    public static int getCustomSpawnID(int summoner, int def) {
        switch (summoner) {
            case 9400589:
            case 9400748: //MV
                return 9400706; //jr
            default:
                return def;
        }
    }

    public static boolean canForfeit(int questid) {
        switch (questid) {
            case 20000:
            case 20010:
            case 20015: //cygnus quests
            case 20020:
                return false;
            default:
                return true;
        }
    }

    public static double getAttackRange(MapleStatEffect def, int rangeInc) {
        double defRange = ((400.0 + rangeInc) * (400.0 + rangeInc));
        if (def != null) {
            defRange += def.getMaxDistanceSq() + (def.getRange() * def.getRange());
        }
        //rangeInc adds to X
        //400 is approximate, screen is 600.. may be too much
        //200 for y is also too much
        //default 200000
        return defRange + 120000.0;
    }

    public static double getAttackRange(Point lt, Point rb) {
        double defRange = (400.0 * 400.0);
        final int maxX = Math.max(Math.abs(lt == null ? 0 : lt.x), Math.abs(rb == null ? 0 : rb.x));
        final int maxY = Math.max(Math.abs(lt == null ? 0 : lt.y), Math.abs(rb == null ? 0 : rb.y));
        defRange += (maxX * maxX) + (maxY * maxY);
        //rangeInc adds to X
        //400 is approximate, screen is 600.. may be too much
        //200 for y is also too much
        //default 200000
        return defRange + 120000.0;
    }

    public static int getLowestPrice(int itemId) {
        switch (itemId) {
            case 2340000: //ws
            case 2531000:
            case 2530000:
                return 50000000;
        }
        return -1;
    }

    public static boolean isNoDelaySkill(int skillId) {
        return skillId == 5110001 || skillId == 21101003 || skillId == 15100004 || skillId == 33101004 || skillId == 32111010 || skillId == 2111007 || skillId == 2211007 || skillId == 2311007 || skillId == 32121003 || skillId == 35121005 || skillId == 35111004 || skillId == 35121013 || skillId == 35121003 || skillId == 22150004 || skillId == 22181004 || skillId == 11101002 || skillId == 13101002 || skillId == 4341003;
    }

    public static boolean isNoSpawn(int mapID) {
        return mapID == 809040100 || mapID == 925020010 || mapID == 925020011 || mapID == 925020012 || mapID == 925020013 || mapID == 925020014 || mapID == 980010000 || mapID == 980010100 || mapID == 980010200 || mapID == 980010300 || mapID == 980010020;
    }

    public static int getExpRate(int job, int def) {
        return def;
    }

    public static int getModifier(int itemId, int up) {
        if (up <= 0) {
            return 0;
        }
        switch (itemId) {
            case 2022459:
            case 2860179:
            case 2860193:
            case 2860207:
                return 130;
            case 2022460:
            case 2022462:
            case 2022730:
                return 150;
            case 2860181:
            case 2860195:
            case 2860209:
                return 200;
        }
        if (itemId / 10000 == 286) { //familiars
            return 150;
        }
        return 200;
    }

    public static short getSlotMax(int itemId) {
        switch (itemId) {
            case 4330000:
            case 4330001:
            case 4330002:
            case 4330003:
            case 4330004:
            case 4330005:
            case 4330006:
            case 4330007:
            case 4330008:
            case 4330009:
                return 1;
        }
        if (GameConstants.getInventoryType(itemId) == MapleInventoryType.USE) {
            return 1000;
        }
        if (GameConstants.getInventoryType(itemId) == MapleInventoryType.ETC) {
            return 10000;
        }
        switch (itemId) {
            case 4030003:
            case 4030004:
            case 4030005:
                return 1;
            case 4001168:
            case 4031306:
            case 4031307:
            case 3993000:
            case 3993002:
            case 3993003:
                return 100;
            case 5220010:
            case 5220013:
                return 1000;
            case 5220020:
                return 2000;
            case 2000005:
            case 2000019:
            case 2001505:
                return 1000;
        }
        return 0;
    }

    public static boolean isDropRestricted(int itemId) {
        return itemId == 3012000 || itemId == 4030004 || itemId == 1052098 || itemId == 1052202;
    }

    public static boolean isPickupRestricted(int itemId) {
        return itemId == 4030003 || itemId == 4030004;
    }

    public static short getStat(int itemId, int def) {
        switch (itemId) {
            case 1002419:
                return 5;
            case 1002959:
                return 25;
            case 1142002:
                return 10;
            case 1122121:
                return 7;
        }
        return (short) def;
    }

    public static short getHpMp(int itemId, int def) {
        switch (itemId) {
            case 1122121:
                return 500;
            case 1142002:
            case 1002959:
                return 1000;
        }
        return (short) def;
    }

    public static short getATK(int itemId, int def) {
        switch (itemId) {
            case 1122121:
                return 3;
            case 1002959:
                return 4;
            case 1142002:
                return 9;
        }
        return (short) def;
    }

    public static short getDEF(int itemId, int def) {
        switch (itemId) {
            case 1122121:
                return 250;
            case 1002959:
                return 500;
        }
        return (short) def;
    }

    public static boolean isDojo(int mapId) {
        return mapId >= 925020100 && mapId <= 925023814;
    }

    public static int getPartyPlayHP(int mobID) {
        switch (mobID) {
            case 4250000:
                return 836000;
            case 4250001:
                return 924000;
            case 5250000:
                return 1100000;
            case 5250001:
                return 1276000;
            case 5250002:
                return 1452000;

            case 9400661:
                return 15000000;
            case 9400660:
                return 30000000;
            case 9400659:
                return 45000000;
            case 9400658:
                return 20000000;
        }
        return 0;
    }

    public static int getPartyPlayEXP(int mobID) {
        switch (mobID) {
            case 4250000:
                return 5770;
            case 4250001:
                return 6160;
            case 5250000:
                return 7100;
            case 5250001:
                return 7975;
            case 5250002:
                return 8800;

            case 9400661:
                return 40000;
            case 9400660:
                return 70000;
            case 9400659:
                return 90000;
            case 9400658:
                return 50000;
        }
        return 0;
    }

    public static int getPartyPlay(int mapId) {
        switch (mapId) {
            case 300010000:
            case 300010100:
            case 300010200:
            case 300010300:
            case 300010400:
            case 300020000:
            case 300020100:
            case 300020200:
            case 300030000:

            case 683070400:
            case 683070401:
            case 683070402:
                return 25;
        }
        return 0;
    }

    public static int getPartyPlay(int mapId, int def) {
        int dd = getPartyPlay(mapId);
        if (dd > 0) {
            return dd;
        }
        return def / 2;
    }

    public static boolean isHyperTeleMap(int mapId) {
        for (int i : hyperTele) {
            if (i == mapId) {
                return true;
            }
        }
        return false;
    }

    public static int getCurrentDate() {
        final String time = FileoutputUtil.CurrentReadable_Time();
        return Integer.parseInt(new StringBuilder(time.substring(0, 4)).append(time.substring(5, 7)).append(time.substring(8, 10)).append(time.substring(11, 13)).toString());
    }

    public static int getCurrentDate_NoTime() {
        final String time = FileoutputUtil.CurrentReadable_Time();
        return Integer.parseInt(new StringBuilder(time.substring(0, 4)).append(time.substring(5, 7)).append(time.substring(8, 10)).toString());
    }

    public static void achievementRatio(MapleClient c) {
        //PQs not affected: Amoria, MV, CWK, English, Zakum, Horntail(?), Carnival, Ghost, Guild, LudiMaze, Elnath(?) 
        switch (c.getPlayer().getMapId()) {
            case 240080600:
            case 920010000:
            case 930000000:
            case 930000100:
            case 910010000:
            case 922010100:
            case 910340100:
            case 925100000:
            case 926100000:
            case 926110000:
            case 921120005:
            case 932000100:
            case 923040100:
            case 921160100:
                c.getSession().write(MaplePacketCreator.achievementRatio(0));
                break;
            case 930000200:
            case 922010200:
            case 922010300:
            case 922010400:
            case 922010401:
            case 922010402:
            case 922010403:
            case 922010404:
            case 922010405:
            case 925100100:
            case 926100001:
            case 926110001:
            case 921160200:
                c.getSession().write(MaplePacketCreator.achievementRatio(10));
                break;
            case 930000300:
            case 910340200:
            case 922010500:
            case 922010600:
            case 925100200:
            case 925100201:
            case 925100202:
            case 926100100:
            case 926110100:
            case 921120100:
            case 932000200:
            case 923040200:
            case 921160300:
            case 921160310:
            case 921160320:
            case 921160330:
            case 921160340:
            case 921160350:
                c.getSession().write(MaplePacketCreator.achievementRatio(25));
                break;
            case 930000400:
            case 926100200:
            case 926110200:
            case 926100201:
            case 926110201:
            case 926100202:
            case 926110202:
            case 921160400:
                c.getSession().write(MaplePacketCreator.achievementRatio(35));
                break;
            case 910340300:
            case 922010700:
            case 930000500:
            case 925100300:
            case 925100301:
            case 925100302:
            case 926100203:
            case 926110203:
            case 921120200:
            case 932000300:
            case 240080700:
            case 240080800:
            case 923040300:
            case 921160500:
                c.getSession().write(MaplePacketCreator.achievementRatio(50));
                break;
            case 910340400:
            case 922010800:
            case 930000600:
            case 925100400:
            case 926100300:
            case 926110300:
            case 926100301:
            case 926110301:
            case 926100302:
            case 926110302:
            case 926100303:
            case 926110303:
            case 926100304:
            case 926110304:
            case 921120300:
            case 932000400:
            case 923040400:
            case 921160600:
                c.getSession().write(MaplePacketCreator.achievementRatio(70));
                break;
            case 910340500:
            case 922010900:
            case 930000700:
            case 920010800:
            case 925100500:
            case 926100400:
            case 926110400:
            case 926100401:
            case 926110401:
            case 921120400:
            case 921160700:
                c.getSession().write(MaplePacketCreator.achievementRatio(85));
                break;
            case 922011000:
            case 922011100:
            case 930000800:
            case 920011000:
            case 920011100:
            case 920011200:
            case 920011300:
            case 925100600:
            case 926100500:
            case 926110500:
            case 926100600:
            case 926110600:
            case 921120500:
            case 921120600:
                c.getSession().write(MaplePacketCreator.achievementRatio(100));
                break;
        }
    }

    public static boolean isAngel(int sourceid) {
        return isBeginnerJob(sourceid / 10000) && (sourceid % 10000 == 1085 || sourceid % 10000 == 1087 || sourceid % 10000 == 1090 || sourceid % 10000 == 1179);
    }

    public static boolean isFishingMap(int mapid) {
        return mapid == 749050500 || mapid == 749050501 || mapid == 749050502 || mapid == 970020000 || mapid == 970020005 /*|| mapid == 910000000*/ || mapid == 999990010 || mapid == 741000200 || mapid == 741000201 || mapid == 741000202 || mapid == 741000203 || mapid == 741000204 || mapid == 741000205 || mapid == 741000206 || mapid == 741000207 || mapid == 741000208;
    }

    public static int getRewardPot(int itemid, int closeness) {
        switch (itemid) {
            case 2440000:
                switch (closeness / 10) {
                    case 0:
                    case 1:
                    case 2:
                        return 2028041 + (closeness / 10);
                    case 3:
                    case 4:
                    case 5:
                        return 2028046 + (closeness / 10);
                    case 6:
                    case 7:
                    case 8:
                        return 2028049 + (closeness / 10);
                }
                return 2028057;
            case 2440001:
                switch (closeness / 10) {
                    case 0:
                    case 1:
                    case 2:
                        return 2028044 + (closeness / 10);
                    case 3:
                    case 4:
                    case 5:
                        return 2028049 + (closeness / 10);
                    case 6:
                    case 7:
                    case 8:
                        return 2028052 + (closeness / 10);
                }
                return 2028060;
            case 2440002:
                return 2028069;
            case 2440003:
                return 2430278;
            case 2440004:
                return 2430381;
            case 2440005:
                return 2430393;
        }
        return 0;
    }

    public static boolean isEventMap(final int mapid) {
        return (mapid >= 109010000 && mapid < 109050000) || (mapid > 109050001 && mapid < 109090000) || (mapid >= 809040000 && mapid <= 809040100);
    }

    public static boolean isMagicChargeSkill(final int skillid) {
        switch (skillid) {
            case 2121001: // Big Bang
            case 2221001:
            case 2321001:
            case 22121000:
            case 22151001:
                return true;
        }
        return false;
    }

    public static boolean isTeamMap(final int mapid) {
        return mapid == 109080000 || mapid == 109080001 || mapid == 109080002 || mapid == 109080003 || mapid == 109080010 || mapid == 109080011 || mapid == 109080012 || mapid == 109090300 || mapid == 109090301 || mapid == 109090302 || mapid == 109090303 || mapid == 109090304 || mapid == 910040100 || mapid == 960020100 || mapid == 960020101 || mapid == 960020102 || mapid == 960020103 || mapid == 960030100 || mapid == 689000000 || mapid == 689000010;
    }

    public static int getStatDice(int stat) {
        switch (stat) {
            case 2:
                return 30;
            case 3:
                return 20;
            case 4:
                return 15;
            case 5:
                return 20;
            case 6:
                return 30;
        }
        return 0;
    }

    public static int getDiceStat(int buffid, int stat) {
        if (buffid == stat || buffid % 10 == stat || buffid / 10 == stat) {
            return getStatDice(stat);
        } else if (buffid == (stat * 100)) {
            return getStatDice(stat) + 10;
        }
        return 0;
    }

    public static final int[] publicNpcIds = {9270035, 9070004, 9010022, 9071003, 9000087, 9000088, 9010000, 9000085, 9000018, 9000000};
    public static final String[] publicNpcs = {"#cUniversal NPC#", "Move to the #cBattle Square# to fight other players", "Move to a variety of #cparty quests#.", "Move to #cMonster Park# to team up to defeat monsters.", "Move to #cFree Market# to trade items with players.", "Move to #cArdentmill#, the crafting town.",
        "Check #cdrops# of any monster in the map.", "Review #cPokedex#.", "Review #cPokemon#.", "Join an #cevent# in progress."};
    //questID; FAMILY USES 19000x, MARRIAGE USES 16000x, EXPED USES 16010x
    //dojo = 150000, bpq = 150001, master monster portals: 122600
    //compensate evan = 170000, compensate sp = 170001
    public static final int OMOK_SCORE = 122200;
    public static final int MATCH_SCORE = 122210;
    public static final int HP_ITEM = 122221;
    public static final int MP_ITEM = 122222;
    public static final int BUFF_ITEM = 122223;
    public static final int JAIL_TIME = 123455;
    public static final int JAIL_QUEST = 123456;
    public static final int REPORT_QUEST = 123457;
    public static final int ULT_EXPLORER = 111111;
    //codex = -55 slot
    //crafting/gathering are designated as skills(short exp then byte 0 then byte level), same with recipes(integer.max_value skill level)
    public static final int ENERGY_DRINK = 122500;
    public static final int HARVEST_TIME = 122501;
    public static final int PENDANT_SLOT = 122700;
    public static final int CURRENT_SET = 122800;
    public static final int BOSS_PQ = 150001;
    public static final int JAGUAR = 111112;
    public static final int DOJO = 150100;
    public static final int DOJO_RECORD = 150101;
    public static final int PARTY_REQUEST = 122900;
    public static final int PARTY_INVITE = 122901;
    public static final int QUICK_SLOT = 123000;

    public static int getEnchantSstarts(int itemReqLevel, boolean isSuperiorEquip) {
        if (itemReqLevel >= 0 && itemReqLevel <= 94) {
            return isSuperiorEquip ? 3 : 5;
        } else if (itemReqLevel > 94 && itemReqLevel <= 107) {
            return isSuperiorEquip ? 5 : 8;
        } else if (itemReqLevel > 107 && itemReqLevel <= 117) {
            return isSuperiorEquip ? 8 : 10;
        } else if (itemReqLevel > 117 && itemReqLevel <= 127) {
            return isSuperiorEquip ? 10 : 15;
        } else if (itemReqLevel > 127 && itemReqLevel <= 137) {
            return isSuperiorEquip ? 12 : 20;
        } else if (itemReqLevel > 137 && itemReqLevel <= 147) {
            return isSuperiorEquip ? 15 : 25;
        } else if (itemReqLevel > 147) {
            return isSuperiorEquip ? 15 : 25;
        }
        return 0;
    }

    public static boolean isPotentialAddScroll(int scrollId) {
        return scrollId / 100 == 20483 && !(scrollId >= 2048200 && scrollId <= 2048304);
    }

    public static boolean isResetScroll(int scrollId) {
        return scrollId / 100 == 20496;
    }

    public static boolean isPetEquip(final int itemid) {
        return itemid / 10000 == 180;
    }

    public static boolean isChaosForGoodness(int itemId) {
        if (itemId == 2049116) {
            return false;
        }
        if (!isChaosScroll(itemId)) {
            return false;
        }
        switch (itemId) {
            case 2049122:
            case 2049124:
            case 2049127:
            case 2049129:
            case 2049130:
            case 2049131:
            case 2049135:
            case 2049136:
            case 2049137:
            case 2049140:
            case 2049155:
                return true;
            case 2049123:
            case 2049125:
            case 2049126:
            case 2049128:
            case 2049132:
            case 2049133:
            case 2049134:
            case 2049138:
            case 2049139:
            case 2049141:
            case 2049142:
            case 2049143:
            case 2049144:
            case 2049145:
            case 2049146:
            case 2049147:
            case 2049148:
            case 2049149:
            case 2049150:
            case 2049151:
            case 2049152:
            case 2049153:
            case 2049154:
        }
        return false;
    }

    public static boolean isAzwanScroll(int scrollId) {
        switch (scrollId) {
            //case 2046060:
            //case 2046061:
            case 2046062:
            case 2046063:
            case 2046064:
            case 2046065:
            case 2046066:
            case 2046067:
            case 2046068:
            case 2046069:
            //case 2046141:
            //case 2046142:
            //case 2046143:
            //case 2046144:
            //case 2046145:
            case 2046519:
            case 2046520:
            case 2046521:
            case 2046522:
            case 2046523:
            case 2046524:
            case 2046525:
            case 2046526:
            case 2046527:
            case 2046528:
            case 2046529:
            case 2046530:
            case 2046701:
            case 2046702:
            case 2046703:
            case 2046704:
            case 2046705:
            case 2046706:
            case 2046707:
            case 2046708:
            case 2046709:
            case 2046710:
            case 2046711:
            case 2046712:
                return true;
        }
        return false;
    }

    //TODO 添加連結攻击技能
    public static int getLinkedAttackSkill(final int id) {
        switch (id) {
            case 11101220: // 皇家衝擊
                return 11101120; // 潜行突襲
            case 11101221: // 焚影
                return 11101121; // 殘像追擊
            case 11111120: // 月影
                return 11111220; // 光芒四射
            case 11111121: // 月光十字架
                return 11111221; // 日光十字架
            case 11121201: // 疾速黄昏
            case 11121102: // 月光之舞（空中）
            case 11121202: // 疾速黄昏（空中
                return 11121101; // 月光之舞
            case 11121103: // 新月分裂
                return 11121203; // 太陽穿刺
            case 21110007:
            case 21110008:
            case 21110015:
                return 21110002;
            case 21120009:
            case 21120010:
            case 21120015:
                return 21120002;
            case 4321001:
                return 4321000;
            case 32120055: // 鬥王杖擊(2擊)
                return 32120052; // 鬥王杖擊
            case 33101006:
            case 33101007:
                return 33101005;
            case 33101008:
                return 33101004;
            case 35101009:
            case 35101010:
                return 35100008;
            case 35111009:
            case 35111010:
                return 35111001;
            case 35121013:
                return 35111004;
            case 35121011:
                return 35121009;
            case 32001007:
            case 32001008:
            case 32001009:
            case 32001010:
            case 32001011:
                return 32001001;
            case 5300007:
                return 5301001;
            case 5320011:
                return 5321004;
            case 23101007:
                return 23101001;
            case 23111010:
            case 23111009:
                return 23111008;
            case 31001006:
            case 31001007:
            case 31001008:
                return 31000004;
            case 30010183:
            case 30010184:
            case 30010186:
                return 30010110;
            case 31010004:
            case 31010005:
            case 31010006:
            case 31010007:
                return 31011000; //超越 : 十文字斬
            case 31201007:
            case 31201008:
            case 31201009:
            case 31201010:
                return 31201000; //超越：惡魔风暴
            case 31211007:
            case 31211008:
            case 31211009:
            case 31211010:
                return 31211000; //超越：月光斬
            case 31221009:
            case 31221010:
            case 31221011:
            case 31221012:
                return 31221000; //超越 : 逆十文字斬
            case 5710012:
                return 5711002;
            case 5701012:
            case 5710020:
                return 5701011;
            case 31121010:
                return 31121000;
            case 5211015:
            case 5211016:
                return 5211011;
            case 24111008:
                return 24111006;
            case 24121010:
                return 24121003;
            case 5001008:
                return 5001003;
            case 5001009:
                return 5101004;
            case 41001005:
            case 41001004:
                return 41001000;
            case 41001006:
            case 41001007:
            case 41001008:
                return 41001002;
            case 41101009:
            case 41101008:
                return 41101000;
            case 41111012:
            case 41111011:
                return 41111000;
            case 41120013:
            case 41120012:
            case 41120011:
                return 41121000;
            case 42001006:
            case 42001005:
                return 42001000;
            case 42001007:
                return 42001002;
            case 42100010:
                return 42101001;
            //Zero:
            case 101000102:
                return 101000101;
            case 101000202:
                return 101000201;
            case 101100202:
                return 101100201;
            case 101110201:
                return 101110200;
            case 101110204:
                return 101110203;
            case 101120101:
                return 101120100;
            case 101120103:
                return 101120102;
            case 101120105:
            case 101120106:
                return 101120104;
            case 101120203:
                return 101120202;
            case 101120205:
            case 101120206:
                return 101120204;
            case 101120200:
                return 101121200;
            case 112000001:
            case 112000002:
                return 112000000;
            case 112120001:
            case 112120002:
            case 112120003:
                return 112120000;
            case 33111212://- 狂野射击
                return 33111112;
            case 33101213://连环三箭
                return 33101113;
            case 33001205://二连射
                return 33001105;
            case 33101215://十字攻
            case 33100016:
                return 33101115;
            case 2321055: //天堂之门
                return 2321052;
            case 61111217:
                return 61101101;
            case 61111219:
                return 61111101;
            case 61111220:
                return 61111002;
            case 61111215:
                return 61001101;
            case 112000019://巨熊3.5击
                return 112000015;
            case 61121221:
            case 61121223:
                return 61121104;
            case 61120018:
            case 61121222:
                return 61121105;
            case 10001253:
            case 10001254:
                return 10000252;
            case 5710023:
            case 5710024:
            case 5710025:
            case 5710026:
                return 5710020;
            case 11121055:
                return 11121052;
            case 5101014:
                return 5101012;
            case 2120013:
                return 2121007;
            case 2220014:
                return 2221007;
            case 12001028:
                return 12000023;
            case 12000026: //轨道烈焰
                return 12001020;
            case 12101028:
            case 12100028:
                return 12100020;
            case 12111028:
            case 12110028:
                return 12110020;
            case 12120010: //轨道烈焰
                return 12120006;
            case 12121055:
                return 12121054;
            case 12120012:
                return 12121003;
            case 12120013://火焰化身
            case 12120014:
                return 12121004;
            case 21110003://终极投掷
                return 21111013;
            case 21110006: //旋风
                return 21111014;
            case 21120005://巨熊咆哮
                return 21121013;
            case 5310011:
                return 5311010;
            case 12120011: // - 灭绝之焰
                return 12121001;
            case 112100001:
                return 112100000;
            case 21100002://战神突进
                return 21101011;
            case 21121055://比昂德
            case 21121056:
                return 21120052;
            case 24120055: //玫瑰卡片终结
                return 24121052;
            case 65111007:
                return 65111100;
            case 2121055:
                return 2121052;
            case 3120017:
                return 3121016;
            case 95001000://箭矢炮盘
                return 3111013;
            case 4210014:
                return 4211006;
            case 1320019:
                return 1320016;
            case 1120017:
                return 1121008;
            case 4100012:
                return 4100011;
            case 4120019:
                return 4120018;
            case 4221016:
                return 4221014;
            case 3000008:
            case 3000009:
            case 3000010:
                return 3001007;
            case 3120019:
                return 3111009;
            case 5111013:
                return 5111002;
            case 5111015:
                return 5111012;
            case 5121019:
                return 5121001;
            case 5121020:
                return 5121007;
            case 5121017:
                return 5121016;
            case 5201005:
                return 5201011;
            case 5201013:
            case 5201014:
                return 5201012;
            case 5210016:
            case 5210017:
            case 5210018:
                return 5210015;
            case 5220023:
            case 5221022:
                return 5221004;
            case 21000006:
                return 21000002;
            case 30011159:
                return 30011109;
            case 20010022:
                return 80001000;
            case 27120211:
                return 27121201;
            case 61001004:
            case 61001005:
            case 61110212:
            case 61120219:
                return 61001000;
            case 61111111:
                return 61111100;
            case 61110009:
                return 61111003;
            case 61121116:
                return 61121104;
            case 61120008:
                return 61111008;
            case 61121203:
                return 61121102;
            case 61121201:
                return 61121100;
            case 61110211:
                return 61101002;
            case 61121217:
                return 61120007;
            case 65121007:
            case 65121008:
                return 65121101;
            case 31011004:
            case 31011005:
            case 31011006:
            case 31011007:
                return 31011000;
            case 31221014:
                return 31221001;
            case 36101008:
            case 36101009:
                return 36101000;
            case 36111009:
            case 36111010:
                return 36111000;
            case 36121013:
            case 36121014:
                return 36121002;
            case 36121011:
            case 36121012:
                return 36121001;
            case 11120010:
                return 11120009;
            case 11121011:
            case 11121012:
                return 11121005;
            case 11121013:
                return 11121004;
            case 13100027:
                return 13100022;
            case 13110027:
                return 13110022;
            case 13120010:
                return 13120003;
            case 13121009:
                return 13121002;
            case 100001269:
                return 100001266;
            case 101110104:
                return 101110102;
            //林之灵
            case 112000000:
                return 110001501;
            case 112100000:
                return 110001502;
            case 112111003:
                return 110001503;
            case 112120000:
                return 110001504;
            //隐月
            case 25000003:
                return 25001002;
            case 25000001:
                return 25001000;
            case 25100001:
            case 25100002:
                return 25101000;
            case 25110001:
            case 25110002:
            case 25110003:
                return 25111000;
            case 25120001:
            case 25120002:
            case 25120003:
                return 25121000;
            case 25100010:
                return 25100009;
            case 25120115:
                return 25120110;
            case 14101021:
                return 14101020;
            case 14111021:
                return 14111020;
            case 14111023:
                return 14111022;
            case 14121002:
                return 14121001;
            case 131001001:
            case 131001002:
            case 131001003:
            case 131001101:
            case 131001102:
            case 131001103:
            case 131002000:
                return 131001000;
            case 131001013:
            case 131001113:
            case 131001213:
            case 131001313:
                return 131001013;
            case 131001104:
            case 131002004:
                return 131001004;
            case 131001106:
            case 131001206:
            case 131001306:
            case 131001406:
            case 131001506:
                return 131001006;
            case 131001107:
            case 131001207:
            case 131001307:
                return 131001007;
            case 131001108:
            case 131001208:
                return 131001008;
            case 131001011:
            case 131002010:
                return 131001010;
            case 131002012:
                return 131001012;
            case 131002014:
                return 131000014;
            case 131002015:
                return 131001015;
            case 131002016:
                return 131000016;
        }
        return id;
    }

    public static boolean isBossMap(int id) {
        switch (id) {
            case 220080001:// 動力室
            case 280030000:// 殘暴炎魔祭壇
            case 551030200:// 夢幻主題公園
            case 240060000:// 試煉之穴I
            case 240060100:// 試煉之穴II
            case 240060200:// 闇黑龍王洞穴
            case 800040208:// 城堡走廊：武器仓库
            case 801040003:// 迎賓室
            case 801040100:// 基地內部
            case 105100300:
            case 105100400:
            case 211070100:
            case 211070101:
            case 211070110:
            case 240040700:
            case 240060201:
            case 262031300:
            case 262031310:
            case 270050100:
            case 271040100:
            case 271040200:
            case 272030400:
            case 272030420:
            case 280030001:
            case 280030100:
            case 300030310:
            case 802000111:
            case 802000211:
            case 802000311:
            case 802000411:
            case 802000611:
            case 802000711:
            case 802000801:
            case 802000802:
            case 802000803:
            case 802000821:
            case 802000823:
            case 240060001:// 試煉之穴I
            case 240060101:// 試煉之穴II
                return true;
        }
        return false;
    }

    public static short getSummonAttackDelay(final int id) {
        switch (id) {
            case 15001004: // Lightning
            case 14001005: // Darkness
            case 13001004: // Storm
            case 12001004: // Flame
            case 11001004: // Soul
            case 3221005: // Freezer
            case 3211005: // Golden Eagle
            case 3121006: // Phoenix
            case 3111005: // Silver Hawk
            case 2321003: // Bahamut
            case 2311006: // Summon Dragon
            case 2221005: // Infrit
            case 2121005: // Elquines
                return 3030;
            case 5211001: // Octopus
            case 5211002: // Gaviota
            case 5220002: // Support Octopus
                return 1230;
            case 3211002: // Puppet
            case 3111002: // Puppet
            case 1321007: // Beholder
                return 0;
        }
        return 0;
    }

    public static double getAttackRange(MapleCharacter chr, MapleStatEffect def, AttackInfo attack) {
        int rangeInc = chr.getStat().defRange;// 處理遠程职业
        double base = 450.0;// 基礎
        double defRange = ((base + rangeInc) * (base + rangeInc));// 基礎范围
        if (def != null) {
            // 計算范围((maxX * maxX) + (maxY * maxY)) + (技能范围 * 技能范围))
            defRange += def.getMaxDistanceSq() + (def.getRange() * def.getRange());
            if (getAttackRangeBySkill(attack) != 0) {// 直接指定技能范围
                defRange = getAttackRangeBySkill(attack);
            }
        } else {// 普通攻击
            defRange = getAttackRangeByWeapon(chr);// 從武器获取范围
        }
        return defRange;
    }

    private static double getAttackRangeByWeapon(MapleCharacter chr) {
        Item weapon_item = chr.getInventory(MapleInventoryType.EQUIPPED).getItem((byte) -11);
        MapleWeaponType weapon = weapon_item == null ? MapleWeaponType.NOT_A_WEAPON : GameConstants.getWeaponType(weapon_item.getItemId());
        switch (weapon) {
            case POLE_ARM:       // 矛
                return 200000;
            case CLAW:     // 拳套
                return 250000;
            case GUN:     // 火枪
            case CROSSBOW:       // 弩
            case BOW:       // 弓
                return 220000;
            case SPEAR:
                return 180000;
            default:
                return 100000;
        }
    }

    private static double getAttackRangeBySkill(AttackInfo attack) {
        double defRange = 0;
        switch (attack.skill) {
            case 21120006: // 極冰暴风
                defRange = 800000.0;
                break;
            case 2121007: // 火流星
            case 12111003: // 火流星
            case 2221007: // 暴风雪
            case 2321008: // 天怒
                defRange = 750000.0;
                break;
            case 2221006: // 閃電連擊
            case 3101005: // 炸彈箭
            case 21101003:// 強化連擊
                defRange = 600000.0;
                break;
            case 15111006:// 閃光擊
                defRange = 800000.0; //原50000修正
                break;
            case 12111006:// 火风暴
                defRange = 525000.0;
                break;
            case 2111003: // 致命毒
                defRange = 400000.0;
                break;
            case 5221004:// 迅雷
            case 4001344: // 雙飞斬
                defRange = 400000.0;
                break;
            case 2101004: // 火焰箭 
            case 1121008: // 无雙劍舞
                defRange = 350000.0;
                break;
            case 2211002: // 冰风暴
                defRange = 300000.0;
                break;
            case 5110001: // 蓄能激发
            case 2311004: // 聖光
            case 2211003: // 落雷凝聚
            case 2001005: // 魔力爪
                defRange = 250000.0;
                break;
            case 2321007: // 天使之箭
                defRange = 200000.0;
                break;
            case 20001000: // 蝸牛投擲術
            case 1000: // 蝸牛投擲術
                defRange = 180000.0;
                break;
            default:
                break;
        }
        return defRange;
    }

    public static boolean isHorntailMap(int id) {
        switch (id) {
            case 240060000:// 試煉之穴I
            case 240060100:// 試煉之穴II
            case 240060200:// 闇黑龍王洞穴
            case 240060001:// 試煉之穴I
            case 240060101:// 試煉之穴II
            case 240060201:// 闇黑龍王洞穴
                return true;
        }
        return false;
    }

    public static boolean Novice_Skill(int skill) {
        switch (skill) {
            case 1000://新手 蝸牛殼
            case 10001000://新手 蝸牛殼
            case 20001000://狂郎  蝸牛殼
            case 20011000:
            case 30001000:
                return true;
        }
        return false;
    }

    public static boolean isElseSkill(int id) {
        switch (id) {
            case 10001009:
            case 20001009:
            case 1009:   // 武陵道場技能
            case 1020:   // 金字塔技能
            case 10001020:
            case 20001020:
            case 3221001:// 光速神弩
            case 4211006:// 枫币炸彈
                return true;
        }
        return false;
    }

    public static int getJobType(final int jobType, MapleClient c) {

        if ((jobType == 0) && (jobType >= 100 && jobType <= 132) && (jobType >= 200 && jobType <= 232) && (jobType >= 300 && jobType <= 322) && (jobType >= 400 && jobType <= 422 && c.getPlayer().getSubcategory() == 0) && (jobType >= 500 && jobType <= 522) && (jobType >= 800 && jobType <= 900)) {
            return 1;
        } else if (jobType >= 0 && jobType <= 434 && c.getPlayer().getSubcategory() == 1) {
            return 2;
        } else if (jobType >= 1000 && jobType <= 1512) {
            return 3;
        } else if ((jobType == 2000) && (jobType >= 2100 && jobType <= 2112)) {
            return 4;
        } else if ((jobType == 2001) && (jobType >= 2200 && jobType <= 2218)) {
            return 5;
        } else if ((jobType == 3000) && (jobType >= 3200 && jobType <= 3512)) {
            return 6;
        }
        return 0;
    }

    public static boolean isBoss(int mobid) {
        switch (mobid) {
            case 8800100:
            case 8800101:
            case 8800102:
            case 8800103:
            case 8800104:
            case 8800105:
            case 8800106:
            case 8800107:
            case 8800108:
            case 8800109:
            case 8800110:
            case 8810100:
            case 8810101:
            case 8810102:
            case 8810103:
            case 8810104:
            case 8810105:
            case 8810106:
            case 8810107:
            case 8810108:
            case 8810109:
            case 8810118:
            case 8810119:
            case 8810120:
            case 8810121:
            case 8810122:
            case 8520000:
            case 8510000:
            case 8500001:
            case 8500002:
            case 8860000:
            case 8870000:
            case 8850000:
            case 8850001:
            case 8850002:
            case 8850003:
            case 8850004:
            case 8850011:
            case 8920000:
            case 8900000:
            case 8900001:
            case 8900002:
            case 8910000:
            case 8930000:
            case 8880000:
                return true;
        }

        return false;
    }
}
