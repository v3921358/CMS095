package client;

import client.MapleFriendship.MapleFriendshipType;
import client.MapleTrait.MapleTraitType;
import client.anticheat.CheatTracker;
import client.anticheat.ReportType;
import client.inventory.Equip;
import client.inventory.Item;
import client.inventory.ItemFlag;
import client.inventory.ItemLoader;
import client.inventory.MapleAndroid;
import client.inventory.MapleImp;
import client.inventory.MapleImp.ImpFlag;
import client.inventory.MapleInventory;
import client.inventory.MapleInventoryIdentifier;
import client.inventory.MapleInventoryType;
import client.inventory.MapleMount;
import client.inventory.MaplePet;
import client.inventory.MapleRing;
import client.status.MonsterStatus;
import client.status.MonsterStatusEffect;
import constants.GameConstants;
import constants.ServerConstants;
import constants.ServerConstants.PlayerGMRank;
import database.DBConPool;
import database.DatabaseException;
import handling.cashshop.CashShopServer;
import handling.channel.ChannelServer;
import handling.channel.handler.HiredMerchantHandler;
import handling.login.LoginInformationProvider.JobType;
import handling.login.LoginServer;
import handling.netty.MapleSession;
import handling.world.CharacterTransfer;
import handling.world.MapleMessenger;
import handling.world.MapleMessengerCharacter;
import handling.world.MapleParty;
import handling.world.MaplePartyCharacter;
import handling.world.PartyOperation;
import handling.world.PlayerBuffStorage;
import handling.world.PlayerBuffValueHolder;
import handling.world.World;
import handling.world.family.MapleFamily;
import handling.world.family.MapleFamilyBuff;
import handling.world.family.MapleFamilyCharacter;
import handling.world.guild.MapleGuild;
import handling.world.guild.MapleGuildCharacter;
import handling.world.sidekick.MapleSidekick;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Deque;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import scripting.EventInstanceManager;
import scripting.EventManager;
import scripting.NPCScriptManager;
import server.CashShop;
import server.MapleCarnivalChallenge;
import server.MapleCarnivalParty;
import server.MapleInventoryManipulator;
import server.MapleItemInformationProvider;
import server.MaplePortal;
import server.MapleShop;
import server.MapleStatEffect;
import server.MapleStatEffect.CancelEffectAction;
import server.MapleStorage;
import server.MapleTrade;
import server.RandomRewards;
import server.Timer;
import server.Timer.BuffTimer;
import server.Timer.MapTimer;
import server.custom.bossrank.BossRankInfo;
import server.custom.bossrank.BossRankManager;
import server.custom.bossrank1.BossRankInfo1;
import server.custom.bossrank1.BossRankManager1;
import server.custom.bossrank10.BossRankInfo10;
import server.custom.bossrank10.BossRankManager10;
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
import server.life.MapleMonster;
import server.life.MobSkill;
import server.life.MobSkillFactory;
import server.life.PlayerNPC;
import server.maps.AnimatedMapleMapObject;
import server.maps.Event_PyramidSubway;
import server.maps.FieldLimitType;
import server.maps.MapleDoor;
import server.maps.MapleDragon;
import server.maps.MapleExtractor;
import server.maps.MapleFoothold;
import server.maps.MapleMap;
import server.maps.MapleMapEffect;
import server.maps.MapleMapFactory;
import server.maps.MapleMapObject;
import server.maps.MapleMapObjectType;
import server.maps.MapleSummon;
import server.maps.MechDoor;
import server.maps.SavedLocationType;
import server.movement.LifeMovementFragment;
import server.quest.MapleQuest;
import server.shops.IMaplePlayerShop;
import tools.ConcurrentEnumMap;
import tools.FileoutputUtil;
import tools.MockIOSession;
import tools.Pair;
import tools.Randomizer;
import tools.StringUtil;
import tools.Triple;
import tools.packet.CSPacket;
import tools.packet.MaplePacketCreator;
import tools.packet.MobPacket;
import tools.packet.MonsterCarnivalPacket;
import tools.packet.PetPacket;
import tools.packet.PlayerShopPacket;
import tools.packet.UIPacket;

public class MapleCharacter extends AnimatedMapleMapObject implements Serializable {

    private static final long serialVersionUID = 845748950829L;
    private String name, chalktext, BlessOfFairy_Origin, BlessOfEmpress_Origin, teleportname, accountsecondPassword, loginkey, serverkey, clientkey;
    private long lastCombo, lastfametime, keydown_skill, nextConsume, pqStartTime, lastDragonBloodTime,
            lastBerserkTime, lastRecoveryTime, lastSummonTime, mapChangeTime, lastFishingTime, lastFairyTime,
            lastHPTime, lastMPTime, lastFamiliarEffectTime, lastDOTTime, lastStorageTime, startTime, lastExpirationTime, mrqdTime;
    private byte gmLevel, gender, initialSpawnPoint, skinColor, guildrank = 5, allianceRank = 5,
            world, fairyExp, numClones, subcategory;
    private short level, mulung_energy, combo, force, availableCP, fatigue, totalCP, hpApUsed, job, remainingAp, scrolledPosition;
    private int accountid, id, meso, exp, hair, face, demonMarking, mapid, fame, pvpExp, pvpPoints, totalWins, totalLosses,
            guildid = 0, fallcounter/*, maplepoints*//*, acash*/, chair, itemEffect/*, points*/, vpoints, vip,
            rank = 1, rankMove = 0, jobRank = 1, jobRankMove = 0, marriageId, marriageItemId, dotHP,
            currentrep, totalrep, coconutteam, followid, battleshipHP, gachexp, challenge, guildContribution = 0, friendshipPoints = 0, MobVac_X = 0, MobVac = 0, CsMod = 0, reviveCount = -1, isVip, isZs, beansNum, beansRange;
    private Point old;
    private MonsterFamiliar summonedFamiliar;
    private int[] wishlist, rocks, savedLocations, regrocks, hyperrocks, remainingSp = new int[10];
    private transient AtomicInteger inst, insd;
    private transient List<LifeMovementFragment> lastres;
    private List<Integer> lastmonthfameids, lastmonthbattleids, extendedSlots;
    private List<MapleDoor> doors;
    private List<MechDoor> mechDoors;
    private List<MaplePet> pets;
    private List<Item> rebuy;
    private MapleImp[] imps;
    private transient WeakReference<MapleCharacter>[] clones;
    private transient Set<MapleMonster> controlled;
    private transient Set<MapleMapObject> visibleMapObjects;
    //private transient ReentrantReadWriteLock visibleMapObjectsLock;
    //private transient ReentrantReadWriteLock summonsLock;
    //private transient ReentrantReadWriteLock controlledLock;
    private transient MapleAndroid android;
    private Map<MapleQuest, MapleQuestStatus> quests;
    private Map<Integer, String> questinfo;
    private Map<Skill, SkillEntry> skills;
    private transient Map<MapleBuffStat, MapleBuffStatValueHolder> effects;
    private transient List<MapleSummon> summons;
    private transient Map<Integer, MapleCoolDownValueHolder> coolDowns;
    private transient Map<MapleDisease, MapleDiseaseValueHolder> diseases;
    private final transient Map<Integer, MapleBuffStatValueHolder> skillID = new LinkedHashMap<>();
    private Map<ReportType, Integer> reports;
    private CashShop cs;
    private transient Deque<MapleCarnivalChallenge> pendingCarnivalRequests;
    private transient MapleCarnivalParty carnivalParty;
    private BuddyList buddylist;
    private MonsterBook monsterbook;
    private transient CheatTracker anticheat;
    private MapleClient client;
    private transient MapleParty party;
    private PlayerStats stats;
    private transient MapleMap map;
    private transient MapleShop shop;
    private transient MapleDragon dragon;
    private transient MapleExtractor extractor;
    private transient RockPaperScissors rps;
    private MapleSidekick sidekick;
    private Map<Integer, MonsterFamiliar> familiars;
    private MapleStorage storage;
    private transient MapleTrade trade;
    private MapleMount mount;
    private MapleMessenger messenger;
    private transient MapleLieDetector antiMacro;
    private byte[] petStore;
    private transient IMaplePlayerShop playerShop;
    private boolean invincible, canTalk, clone, followinitiator, followon, smega, hasSummon;
    private MapleGuildCharacter mgc;
    private MapleFamilyCharacter mfc;
    private transient EventInstanceManager eventInstance;
    private MapleInventory[] inventory;
    private SkillMacro[] skillMacros = new SkillMacro[5];
    private EnumMap<MapleTraitType, MapleTrait> traits;
    private EnumMap<MapleFriendshipType, MapleFriendship> friendships;
    private MapleKeyLayout keylayout;
    private transient ScheduledFuture<?> mapTimeLimitTask;
    private transient Event_PyramidSubway pyramidSubway = null;
    private transient List<Integer> pendingExpiration = null, pendingSkills = null;
    private transient Map<Integer, Integer> linkMobs;
    private boolean changed_wishlist, changed_trocklocations, changed_regrocklocations, changed_hyperrocklocations, changed_skillmacros,
            changed_savedlocations, changed_questinfo, changed_skills, changed_reports, changed_extendedSlots, isReincarnation, canSetBeansNum = false, beansStart = false;
    public int matchCardVal = 0;
    private int todayOnlineTime;//在线时间
    private int characterid;

    private MapleCharacter(final boolean ChannelServer) {
        setStance(0);
        setPosition(new Point(0, 0));

        inventory = new MapleInventory[MapleInventoryType.values().length];
        for (MapleInventoryType type : MapleInventoryType.values()) {
            inventory[type.ordinal()] = new MapleInventory(type);
        }
        quests = new LinkedHashMap<MapleQuest, MapleQuestStatus>(); // Stupid erev quest.
        skills = new LinkedHashMap<Skill, SkillEntry>(); //Stupid UAs.
        stats = new PlayerStats();
        for (int i = 0; i < remainingSp.length; i++) {
            remainingSp[i] = 0;
        }
        traits = new EnumMap<MapleTraitType, MapleTrait>(MapleTraitType.class);
        for (MapleTraitType t : MapleTraitType.values()) {
            traits.put(t, new MapleTrait(t));
        }
        friendships = new EnumMap<MapleFriendshipType, MapleFriendship>(MapleFriendshipType.class);
        for (MapleFriendshipType t : MapleFriendshipType.values()) {
            friendships.put(t, new MapleFriendship(t));
        }
        if (ChannelServer) {
            changed_reports = false;
            changed_skills = false;
            changed_wishlist = false;
            changed_trocklocations = false;
            changed_regrocklocations = false;
            changed_hyperrocklocations = false;
            changed_skillmacros = false;
            changed_savedlocations = false;
            changed_extendedSlots = false;
            changed_questinfo = false;
            scrolledPosition = 0;
            lastCombo = 0;
            mulung_energy = 0;
            combo = 0;
            force = 0;
            keydown_skill = 0;
            nextConsume = 0;
            pqStartTime = 0;
            fairyExp = 0;
            mapChangeTime = 0;
            lastRecoveryTime = 0;
            lastDragonBloodTime = 0;
            lastBerserkTime = 0;
            lastFishingTime = 0;
            lastFairyTime = 0;
            lastHPTime = 0;
            lastMPTime = 0;
            lastFamiliarEffectTime = 0;
            lastExpirationTime = 0;
            old = new Point(0, 0);
            coconutteam = 0;
            followid = 0;
            battleshipHP = 0;
            marriageItemId = 0;
            fallcounter = 0;
            challenge = 0;
            dotHP = 0;
            lastSummonTime = 0;
            hasSummon = false;
            invincible = false;
            canTalk = true;
            clone = false;
            followinitiator = false;
            followon = false;
            rebuy = new ArrayList<Item>();
            linkMobs = new HashMap<Integer, Integer>();
            reports = new EnumMap<ReportType, Integer>(ReportType.class);
            teleportname = "";
            smega = true;
            petStore = new byte[3];
            for (int i = 0; i < petStore.length; i++) {
                petStore[i] = (byte) -1;
            }
            wishlist = new int[10];
            rocks = new int[10];
            regrocks = new int[5];
            hyperrocks = new int[13];
            imps = new MapleImp[3];
            clones = new WeakReference[5]; //for now
            for (int i = 0; i < clones.length; i++) {
                clones[i] = new WeakReference<MapleCharacter>(null);
            }
            familiars = new LinkedHashMap<Integer, MonsterFamiliar>();
            extendedSlots = new ArrayList<Integer>();
            effects = new ConcurrentEnumMap<MapleBuffStat, MapleBuffStatValueHolder>(MapleBuffStat.class);
            coolDowns = new LinkedHashMap<Integer, MapleCoolDownValueHolder>();
            diseases = new ConcurrentEnumMap<MapleDisease, MapleDiseaseValueHolder>(MapleDisease.class);
            inst = new AtomicInteger(0);// 1 = NPC/ Quest, 2 = Duey, 3 = Hired Merch store, 4 = Storage
            insd = new AtomicInteger(-1);
            keylayout = new MapleKeyLayout();
            doors = new ArrayList<MapleDoor>();
            mechDoors = new ArrayList<MechDoor>();
            controlled = new LinkedHashSet<MapleMonster>();
            //controlledLock = new ReentrantReadWriteLock();
            summons = new LinkedList<MapleSummon>();
            //summonsLock = new ReentrantReadWriteLock();
            visibleMapObjects = new LinkedHashSet<MapleMapObject>();
            //visibleMapObjectsLock = new ReentrantReadWriteLock();
            pendingCarnivalRequests = new LinkedList<MapleCarnivalChallenge>();

            savedLocations = new int[SavedLocationType.values().length];
            for (int i = 0; i < SavedLocationType.values().length; i++) {
                savedLocations[i] = -1;
            }
            questinfo = new LinkedHashMap<Integer, String>();
            pets = new ArrayList<MaplePet>();
        }
    }

    public static MapleCharacter getDefault(final MapleClient client, final JobType type) {
        MapleCharacter ret = new MapleCharacter(false);
        ret.client = client;
        ret.map = null;
        ret.exp = 0;
        ret.gmLevel = 0;
        ret.job = (short) type.id;
        ret.todayOnlineTime = 0;
        ret.meso = 0;
        ret.level = 1;
        ret.remainingAp = 0;
        ret.fame = 0;
        ret.accountid = client.getAccID();
        ret.buddylist = new BuddyList((byte) 20);

        ret.stats.str = 12;
        ret.stats.dex = 5;
        ret.stats.int_ = 4;
        ret.stats.luk = 4;
        ret.stats.maxhp = 50;
        ret.stats.hp = 50;
        ret.stats.maxmp = 50;
        ret.stats.mp = 50;
        ret.gachexp = 0;

        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps;
            ps = con.prepareStatement("SELECT * FROM accounts WHERE id = ?");
            ps.setInt(1, ret.accountid);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                ret.client.setAccountName(rs.getString("name"));
                ret.accountsecondPassword = rs.getString("2ndpassword");
                //ret.acash = rs.getInt("ACash");
                //ret.maplepoints = rs.getInt("mPoints");
                //ret.points = rs.getInt("points");
                ret.vpoints = rs.getInt("vpoints");
                ret.vip = rs.getInt("VIP");
                ret.loginkey = rs.getString("loginkey");
                ret.serverkey = rs.getString("serverkey");
                ret.clientkey = rs.getString("clientkey");
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.err.println("Error getting character default" + e);
            FileoutputUtil.outError("logs/数据库异常.txt", e);
        }
        return ret;
    }

    public final static MapleCharacter ReconstructChr(final CharacterTransfer ct, final MapleClient client, final boolean isChannel) {
        final MapleCharacter ret = new MapleCharacter(true); // Always true, it's change channel
        ret.client = client;
        if (!isChannel) {
            ret.client.setChannel(ct.channel);
        }
        ret.id = ct.characterid;
        ret.name = ct.name;
        ret.level = ct.level;
        ret.fame = ct.fame;

        ret.stats.str = ct.str;
        ret.stats.dex = ct.dex;
        ret.stats.int_ = ct.int_;
        ret.stats.luk = ct.luk;
        ret.stats.maxhp = ct.maxhp;
        ret.stats.maxmp = ct.maxmp;
        ret.stats.hp = ct.hp;
        ret.stats.mp = ct.mp;

        ret.chalktext = ct.chalkboard;
        ret.gmLevel = ct.gmLevel;
        ret.exp = (ret.level >= LoginServer.getMaxLevel() || (GameConstants.isKOC(ret.job) && ret.level >= LoginServer.getKocMaxLevel())) && !ret.isIntern() ? 0 : ct.exp;
        ret.hpApUsed = ct.hpApUsed;
        ret.remainingSp = ct.remainingSp;
        ret.remainingAp = ct.remainingAp;
        ret.todayOnlineTime = ct.todayOnlineTime;
        ret.meso = ct.meso;
        ret.skinColor = ct.skinColor;
        ret.gender = ct.gender;
        ret.job = ct.job;
        ret.hair = ct.hair;
        ret.face = ct.face;
        ret.demonMarking = ct.demonMarking;
        ret.accountid = ct.accountid;
        ret.totalWins = ct.totalWins;
        ret.totalLosses = ct.totalLosses;
        client.setAccID(ct.accountid);
        ret.mapid = ct.mapid;
        ret.initialSpawnPoint = ct.initialSpawnPoint;
        ret.world = ct.world;
        ret.guildid = ct.guildid;
        ret.guildrank = ct.guildrank;
        ret.guildContribution = ct.guildContribution;
        ret.allianceRank = ct.alliancerank;
        //ret.points = ct.points;
        ret.vpoints = ct.vpoints;
        ret.vip = ct.vip;
        ret.mrqdTime = ct.mrqdTime;
        ret.isVip = ct.isVip;
        ret.isZs = ct.isZs;
        ret.fairyExp = ct.fairyExp;
        ret.marriageId = ct.marriageId;
        ret.currentrep = ct.currentrep;
        ret.totalrep = ct.totalrep;
        ret.gachexp = ct.gachexp;
        ret.pvpExp = ct.pvpExp;
        ret.pvpPoints = ct.pvpPoints;
        ret.makeMFC(ct.familyid, ct.seniorid, ct.junior1, ct.junior2);
        if (ret.guildid > 0) {
            ret.mgc = new MapleGuildCharacter(ret);
        }
        ret.fatigue = ct.fatigue;
        ret.buddylist = new BuddyList(ct.buddysize);
        ret.subcategory = ct.subcategory;

        if (ct.sidekick > 0) {
            ret.sidekick = World.Sidekick.getSidekick(ct.sidekick);
        }

        if (isChannel) {
            final MapleMapFactory mapFactory = ChannelServer.getInstance(client.getChannel()).getMapFactory();
            ret.map = mapFactory.getMap(ret.mapid);
            if (ret.map == null) { //char is on a map that doesn't exist warp it to henesys
                ret.map = mapFactory.getMap(100000000);
            } else if (ret.map.getForcedReturnId() != 999999999 && ret.map.getForcedReturnMap() != null) {
                ret.map = ret.map.getForcedReturnMap();
            }
            MaplePortal portal = ret.map.getPortal(ret.initialSpawnPoint);
            if (portal == null) {
                portal = ret.map.getPortal(0); // char is on a spawnpoint that doesn't exist - select the first spawnpoint instead
                ret.initialSpawnPoint = 0;
            }
            ret.setPosition(portal.getPosition());

            final int messengerid = ct.messengerid;
            if (messengerid > 0) {
                ret.messenger = World.Messenger.getMessenger(messengerid);
            }
        } else {

            ret.messenger = null;
        }
        int partyid = ct.partyid;
        if (partyid >= 0) {
            MapleParty party = World.Party.getParty(partyid);
            if (party != null && party.getMemberById(ret.id) != null) {
                ret.party = party;
            }
        }

        MapleQuestStatus queststatus_from;
        for (final Map.Entry<Integer, Object> qs : ct.Quest.entrySet()) {
            queststatus_from = (MapleQuestStatus) qs.getValue();
            queststatus_from.setQuest(qs.getKey());
            ret.quests.put(queststatus_from.getQuest(), queststatus_from);
        }
        for (final Map.Entry<Integer, SkillEntry> qs : ct.Skills.entrySet()) {
            ret.skills.put(SkillFactory.getSkill(qs.getKey()), qs.getValue());
        }
        for (Entry<MapleTraitType, Integer> t : ct.traits.entrySet()) {
            ret.traits.get(t.getKey()).setExp(t.getValue());
        }
        for (Entry<MapleFriendshipType, Integer> t : ct.friendships.entrySet()) {
            ret.friendships.get(t.getKey()).setPoints(t.getValue());
        }
        for (final Map.Entry<Byte, Integer> qs : ct.reports.entrySet()) {
            ret.reports.put(ReportType.getById(qs.getKey()), qs.getValue());
        }
        ret.monsterbook = new MonsterBook(ct.mbook, ret);
        ret.inventory = (MapleInventory[]) ct.inventorys;
        ret.BlessOfFairy_Origin = ct.BlessOfFairy;
        ret.BlessOfEmpress_Origin = ct.BlessOfEmpress;
        ret.skillMacros = (SkillMacro[]) ct.skillmacro;
        ret.petStore = ct.petStore;
        ret.keylayout = new MapleKeyLayout(ct.keymap);
        ret.questinfo = ct.InfoQuest;
        ret.familiars = ct.familiars;
        ret.savedLocations = ct.savedlocation;
        ret.wishlist = ct.wishlist;
        ret.rocks = ct.rocks;
        ret.regrocks = ct.regrocks;
        ret.hyperrocks = ct.hyperrocks;
        ret.buddylist.loadFromTransfer(ct.buddies);
        ret.keydown_skill = 0; // Keydown skill can't be brought over
        ret.lastfametime = ct.lastfametime;
        ret.lastmonthfameids = ct.famedcharacters;
        ret.lastmonthbattleids = ct.battledaccs;
        ret.extendedSlots = ct.extendedSlots;
        ret.storage = (MapleStorage) ct.storage;
        ret.cs = (CashShop) ct.cs;
        client.setAccountName(ct.accountname);
        //ret.acash = ct.ACash;
        //ret.maplepoints = ct.MaplePoints;
        ret.accountsecondPassword = ct.accountsecondPassword;
        ret.CsMod = ct.CsMod;
        ret.loginkey = ct.loginkey;
        ret.serverkey = ct.serverkey;
        ret.clientkey = ct.clientkey;
        ret.antiMacro = (MapleLieDetector) ct.antiMacro;
        ret.numClones = ct.clonez;
        ret.imps = ct.imps;
        ret.anticheat = (CheatTracker) ct.anticheat;
        ret.anticheat.start(ret);
        ret.rebuy = ct.rebuy;
        ret.mount = new MapleMount(ret, ct.mount_itemid, ret.stats.getSkillByJob(1004, ret.job), ct.mount_Fatigue, ct.mount_level, ct.mount_exp);
        ret.expirationTask(false, false);
        ret.stats.recalcLocalStats(true, ret);
        client.setTempIP(ct.tempIP);

        return ret;
    }

    public static MapleCharacter loadCharFromDB(int charid, MapleClient client, boolean channelserver) {
        final MapleCharacter ret = new MapleCharacter(channelserver);
        ret.client = client;
        ret.id = charid;

        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps = null;
            PreparedStatement pse = null;
            ResultSet rs = null;

            try {
                ps = con.prepareStatement("SELECT * FROM characters WHERE id = ?");
                ps.setInt(1, charid);
                rs = ps.executeQuery();
                if (!rs.next()) {
                    rs.close();
                    ps.close();
                    throw new RuntimeException("Loading the Char Failed (char not found)");
                }
                ret.name = rs.getString("name");
                ret.level = rs.getShort("level");
                ret.fame = rs.getInt("fame");

                ret.stats.str = rs.getShort("str");
                ret.stats.dex = rs.getShort("dex");
                ret.stats.int_ = rs.getShort("int");
                ret.stats.luk = rs.getShort("luk");
                ret.stats.maxhp = rs.getInt("maxhp");
                ret.stats.maxmp = rs.getInt("maxmp");
                ret.stats.hp = rs.getInt("hp");
                ret.stats.mp = rs.getInt("mp");
                ret.job = rs.getShort("job");
                ret.gmLevel = rs.getByte("gm");
                ret.exp = (ret.level >= LoginServer.getMaxLevel() || (GameConstants.isKOC(ret.job) && ret.level >= LoginServer.getKocMaxLevel())) && !ret.isIntern() ? 0 : rs.getInt("exp");
                ret.hpApUsed = rs.getShort("hpApUsed");
                final String[] sp = rs.getString("sp").split(",");
                for (int i = 0; i < ret.remainingSp.length; i++) {
                    ret.remainingSp[i] = Integer.parseInt(sp[i]);
                }
                ret.remainingAp = rs.getShort("ap");
                ret.todayOnlineTime = rs.getInt("todayOnlineTime");
                ret.meso = rs.getInt("meso");
                ret.skinColor = rs.getByte("skincolor");
                ret.gender = rs.getByte("gender");

                ret.hair = rs.getInt("hair");
                ret.face = rs.getInt("face");
                ret.demonMarking = rs.getInt("demonMarking");
                ret.accountid = rs.getInt("accountid");
                client.setAccID(ret.accountid);
                ret.mapid = rs.getInt("map");
                ret.initialSpawnPoint = rs.getByte("spawnpoint");
                ret.world = rs.getByte("world");
                ret.guildid = rs.getInt("guildid");
                ret.guildrank = rs.getByte("guildrank");
                ret.allianceRank = rs.getByte("allianceRank");
                ret.guildContribution = rs.getInt("guildContribution");
                ret.totalWins = rs.getInt("totalWins");
                ret.totalLosses = rs.getInt("totalLosses");
                ret.currentrep = rs.getInt("currentrep");
                ret.totalrep = rs.getInt("totalrep");
                ret.makeMFC(rs.getInt("familyid"), rs.getInt("seniorid"), rs.getInt("junior1"), rs.getInt("junior2"));
                if (ret.guildid > 0) {
                    ret.mgc = new MapleGuildCharacter(ret);
                }
                ret.gachexp = rs.getInt("gachexp");
                ret.buddylist = new BuddyList(rs.getByte("buddyCapacity"));
                ret.subcategory = rs.getByte("subcategory");
                ret.mount = new MapleMount(ret, 0, ret.stats.getSkillByJob(1004, ret.job), (byte) 0, (byte) 1, 0);
                ret.rank = rs.getInt("rank");
                ret.rankMove = rs.getInt("rankMove");
                ret.jobRank = rs.getInt("jobRank");
                ret.jobRankMove = rs.getInt("jobRankMove");
                ret.marriageId = rs.getInt("marriageId");
                ret.fatigue = rs.getShort("fatigue");
                ret.pvpExp = rs.getInt("pvpExp");
                ret.pvpPoints = rs.getInt("pvpPoints");
                for (MapleTrait t : ret.traits.values()) {
                    t.setExp(rs.getInt(t.getType().name()));
                }
                for (MapleFriendship t : ret.friendships.values()) {
                    t.setPoints(rs.getInt(t.getType().name()));
                }
                if (channelserver) {
                    ret.anticheat = new CheatTracker(ret);
                    MapleMapFactory mapFactory = ChannelServer.getInstance(client.getChannel()).getMapFactory();
                    ret.antiMacro = new MapleLieDetector(ret.id);
                    ret.map = mapFactory.getMap(ret.mapid);
                    if (ret.map == null) { //char is on a map that doesn't exist warp it to henesys
                        ret.map = mapFactory.getMap(100000000);
                    }
                    MaplePortal portal = ret.map.getPortal(ret.initialSpawnPoint);
                    if (portal == null) {
                        portal = ret.map.getPortal(0); // char is on a spawnpoint that doesn't exist - select the first spawnpoint instead
                        ret.initialSpawnPoint = 0;
                    }
                    ret.setPosition(portal.getPosition());

                    int partyid = rs.getInt("party");
                    if (partyid >= 0) {
                        MapleParty party = World.Party.getParty(partyid);
                        if (party != null && party.getMemberById(ret.id) != null) {
                            ret.party = party;
                        }
                    }
                    final String[] pets = rs.getString("pets").split(",");
                    for (int i = 0; i < ret.petStore.length; i++) {
                        ret.petStore[i] = Byte.parseByte(pets[i]);
                    }
                    rs.close();
                    ps.close();

                    ps = con.prepareStatement("SELECT * FROM reports WHERE characterid = ?");
                    ps.setInt(1, charid);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        if (ReportType.getById(rs.getByte("type")) != null) {
                            ret.reports.put(ReportType.getById(rs.getByte("type")), rs.getInt("count"));
                        }
                    }

                }
                rs.close();
                ps.close();

                ps = con.prepareStatement("SELECT * FROM queststatus WHERE characterid = ?");
                ps.setInt(1, charid);
                rs = ps.executeQuery();
                pse = con.prepareStatement("SELECT * FROM queststatusmobs WHERE queststatusid = ?");

                while (rs.next()) {
                    final int id = rs.getInt("quest");
                    final MapleQuest q = MapleQuest.getInstance(id);
                    final byte stat = rs.getByte("status");
                    if ((stat == 1 || stat == 2) && channelserver && (q == null || q.isBlocked())) { //bigbang
                        continue;
                    }
                    if (stat == 1 && channelserver && !q.canStart(ret, null)) { //bigbang
                        continue;
                    }
                    final MapleQuestStatus status = new MapleQuestStatus(q, stat);
                    final long cTime = rs.getLong("time");
                    if (cTime > -1) {
                        status.setCompletionTime(cTime * 1000);
                    }
                    status.setForfeited(rs.getInt("forfeited"));
                    status.setCustomData(rs.getString("customData"));
                    ret.quests.put(q, status);
                    pse.setLong(1, rs.getLong("queststatusid"));
                    final ResultSet rsMobs = pse.executeQuery();

                    while (rsMobs.next()) {
                        status.setMobKills(rsMobs.getInt("mob"), rsMobs.getInt("count"));
                    }
                    rsMobs.close();
                }
                rs.close();
                ps.close();
                pse.close();

                if (channelserver) {
                    ret.monsterbook = MonsterBook.loadCards(ret.accountid, ret);

                    ps = con.prepareStatement("SELECT * FROM inventoryslot where characterid = ?");
                    ps.setInt(1, charid);
                    rs = ps.executeQuery();

                    if (!rs.next()) {
                        rs.close();
                        ps.close();
                        throw new RuntimeException("No Inventory slot column found in SQL. [inventoryslot]");
                    } else {
                        ret.getInventory(MapleInventoryType.EQUIP).setSlotLimit(rs.getByte("equip"));
                        ret.getInventory(MapleInventoryType.USE).setSlotLimit(rs.getByte("use"));
                        ret.getInventory(MapleInventoryType.SETUP).setSlotLimit(rs.getByte("setup"));
                        ret.getInventory(MapleInventoryType.ETC).setSlotLimit(rs.getByte("etc"));
                        ret.getInventory(MapleInventoryType.CASH).setSlotLimit(rs.getByte("cash"));
                    }
                    ps.close();
                    rs.close();

                    for (Pair<Item, MapleInventoryType> mit : ItemLoader.INVENTORY.loadItems(false, charid).values()) {
                        ret.getInventory(mit.getRight()).addFromDB(mit.getLeft());
                        if (mit.getLeft().getPet() != null) {
                            ret.pets.add(mit.getLeft().getPet());
                        }
                    }

                    ps = con.prepareStatement("SELECT * FROM accounts WHERE id = ?");
                    ps.setInt(1, ret.accountid);
                    rs = ps.executeQuery();
                    if (rs.next()) {
                        ret.getClient().setAccountName(rs.getString("name"));
                        ret.accountsecondPassword = rs.getString("2ndpassword");
                        //ret.acash = rs.getInt("ACash");
                        //ret.maplepoints = rs.getInt("mPoints");
                        //ret.points = rs.getInt("points");
                        ret.vpoints = rs.getInt("vpoints");
                        ret.vip = rs.getInt("VIP");
                        ret.loginkey = rs.getString("loginkey");
                        ret.serverkey = rs.getString("serverkey");
                        ret.clientkey = rs.getString("clientkey");

                        if (rs.getTimestamp("lastlogon") != null) {
                            final Calendar cal = Calendar.getInstance();
                            cal.setTimeInMillis(rs.getTimestamp("lastlogon").getTime());
                            //if (cal.get(Calendar.DAY_OF_WEEK) + 1 == Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
                            //    ret.acash += GameConstants.GMS ? 375 : 500;
                            //}
                        }
                        /*if (rs.getInt("banned") > 0) {
                            rs.close();
                            ps.close();
                            ret.getClient().getSession().close();
                            throw new RuntimeException("Loading a banned character");
                        }*/
                        rs.close();
                        ps.close();

                        ps = con.prepareStatement("UPDATE accounts SET lastlogon = CURRENT_TIMESTAMP() WHERE id = ?");
                        ps.setInt(1, ret.accountid);
                        ps.executeUpdate();
                    } else {
                        rs.close();
                    }
                    ps.close();

                    ps = con.prepareStatement("SELECT * FROM questinfo WHERE characterid = ?");
                    ps.setInt(1, charid);
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        ret.questinfo.put(rs.getInt("quest"), rs.getString("customData"));
                    }
                    rs.close();
                    ps.close();

                    ps = con.prepareStatement("SELECT skillid, skilllevel, masterlevel, expiration FROM skills WHERE characterid = ?");
                    ps.setInt(1, charid);
                    rs = ps.executeQuery();
                    Skill skil;
                    while (rs.next()) {
                        final int skid = rs.getInt("skillid");
                        skil = SkillFactory.getSkill(skid);
                        int skl = rs.getInt("skilllevel");
                        byte msl = rs.getByte("masterlevel");
                        if (skil != null && GameConstants.isApplicableSkill(skid)) {
                            if (skl > skil.getMaxLevel() && skid < 92000000) {
                                if (!skil.isBeginnerSkill() && skil.canBeLearnedBy(ret.job) && !skil.isSpecialSkill()) {
                                    ret.remainingSp[GameConstants.getSkillBookForSkill(skid)] += (skl - skil.getMaxLevel());
                                }
                                skl = (byte) skil.getMaxLevel();
                            }
                            if (msl > skil.getMaxLevel()) {
                                msl = (byte) skil.getMaxLevel();
                            }
                            ret.skills.put(skil, new SkillEntry(skl, msl, rs.getLong("expiration")));
                        } else if (skil == null) { //doesnt. exist. e.g. bb
                            if (!GameConstants.isBeginnerJob(skid / 10000) && skid / 10000 != 900 && skid / 10000 != 800 && skid / 10000 != 9000) {
                                ret.remainingSp[GameConstants.getSkillBookForSkill(skid)] += skl;
                            }
                        }
                    }
                    rs.close();
                    ps.close();

                    ret.expirationTask(false, true); //do it now

                    // Bless of Fairy handling
                    ps = con.prepareStatement("SELECT * FROM characters WHERE accountid = ? ORDER BY level DESC");
                    ps.setInt(1, ret.accountid);
                    rs = ps.executeQuery();
                    int maxlevel_ = 0, maxlevel_2 = 0;
                    while (rs.next()) {
                        if (rs.getInt("id") != charid) { // Not this character
                            if (GameConstants.isKOC(rs.getShort("job"))) {
                                int maxlevel = (rs.getShort("level") / 5);

                                if (maxlevel > 24) {
                                    maxlevel = 24;
                                }
                                if (maxlevel > maxlevel_2 || maxlevel_2 == 0) {
                                    maxlevel_2 = maxlevel;
                                    ret.BlessOfEmpress_Origin = rs.getString("name");
                                }
                            }
                            int maxlevel = (rs.getShort("level") / 10);
                            if (maxlevel > 20) {
                                maxlevel = 20;
                            }
                            if (maxlevel > maxlevel_ || maxlevel_ == 0) {
                                maxlevel_ = maxlevel;
                                ret.BlessOfFairy_Origin = rs.getString("name");
                            }

                        }
                    }
                    if (ret.BlessOfFairy_Origin == null) {
                        ret.BlessOfFairy_Origin = ret.name;
                    }
                    ret.skills.put(SkillFactory.getSkill(GameConstants.getBOF_ForJob(ret.job)), new SkillEntry(maxlevel_, (byte) 0, -1));
                    if (SkillFactory.getSkill(GameConstants.getEmpress_ForJob(ret.job)) != null) {
                        if (ret.BlessOfEmpress_Origin == null) {
                            ret.BlessOfEmpress_Origin = ret.BlessOfFairy_Origin;
                        }
                        ret.skills.put(SkillFactory.getSkill(GameConstants.getEmpress_ForJob(ret.job)), new SkillEntry(maxlevel_2, (byte) 0, -1));
                    }
                    ps.close();
                    rs.close();

                    ps = con.prepareStatement("SELECT * FROM skillmacros WHERE characterid = ?");
                    ps.setInt(1, charid);
                    rs = ps.executeQuery();
                    int position;
                    while (rs.next()) {
                        position = rs.getInt("position");
                        SkillMacro macro = new SkillMacro(rs.getInt("skill1"), rs.getInt("skill2"), rs.getInt("skill3"), rs.getString("name"), rs.getInt("shout"), position);
                        ret.skillMacros[position] = macro;
                    }
                    rs.close();
                    ps.close();

                    ps = con.prepareStatement("SELECT * FROM familiars WHERE characterid = ?");
                    ps.setInt(1, charid);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        if (rs.getLong("expiry") <= System.currentTimeMillis()) {
                            continue;
                        }
                        ret.familiars.put(rs.getInt("familiar"), new MonsterFamiliar(charid, rs.getInt("id"), rs.getInt("familiar"), rs.getLong("expiry"), rs.getString("name"), rs.getInt("fatigue"), rs.getByte("vitality")));
                    }
                    rs.close();
                    ps.close();

                    ps = con.prepareStatement("SELECT `key`,`type`,`action` FROM keymap WHERE characterid = ?");
                    ps.setInt(1, charid);
                    rs = ps.executeQuery();

                    final Map<Integer, Pair<Byte, Integer>> keyb = ret.keylayout.Layout();
                    while (rs.next()) {
                        keyb.put(Integer.valueOf(rs.getInt("key")), new Pair<Byte, Integer>(rs.getByte("type"), rs.getInt("action")));
                    }
                    rs.close();
                    ps.close();
                    ret.keylayout.unchanged();

                    ps = con.prepareStatement("SELECT `locationtype`,`map` FROM savedlocations WHERE characterid = ?");
                    ps.setInt(1, charid);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        ret.savedLocations[rs.getInt("locationtype")] = rs.getInt("map");
                    }
                    rs.close();
                    ps.close();

                    ps = con.prepareStatement("SELECT `characterid_to`,`when` FROM famelog WHERE characterid = ? AND DATEDIFF(NOW(),`when`) < 30");
                    ps.setInt(1, charid);
                    rs = ps.executeQuery();
                    ret.lastfametime = 0;
                    ret.lastmonthfameids = new ArrayList<Integer>(31);
                    while (rs.next()) {
                        ret.lastfametime = Math.max(ret.lastfametime, rs.getTimestamp("when").getTime());
                        ret.lastmonthfameids.add(Integer.valueOf(rs.getInt("characterid_to")));
                    }
                    rs.close();
                    ps.close();

                    ps = con.prepareStatement("SELECT `accid_to`,`when` FROM battlelog WHERE accid = ? AND DATEDIFF(NOW(),`when`) < 30");
                    ps.setInt(1, ret.accountid);
                    rs = ps.executeQuery();
                    ret.lastmonthbattleids = new ArrayList<Integer>();
                    while (rs.next()) {
                        ret.lastmonthbattleids.add(Integer.valueOf(rs.getInt("accid_to")));
                    }
                    rs.close();
                    ps.close();

                    ps = con.prepareStatement("SELECT `itemId` FROM extendedSlots WHERE characterid = ?");
                    ps.setInt(1, charid);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        ret.extendedSlots.add(Integer.valueOf(rs.getInt("itemId")));
                    }
                    rs.close();
                    ps.close();

                    ret.buddylist.loadFromDb(charid);
                    ret.storage = MapleStorage.loadStorage(ret.accountid);
                    ret.cs = new CashShop(ret.accountid, charid, ret.getJob());

                    ps = con.prepareStatement("SELECT sn FROM wishlist WHERE characterid = ?");
                    ps.setInt(1, charid);
                    rs = ps.executeQuery();
                    int i = 0;
                    while (rs.next()) {
                        ret.wishlist[i] = rs.getInt("sn");
                        i++;
                    }
                    while (i < 10) {
                        ret.wishlist[i] = 0;
                        i++;
                    }
                    rs.close();
                    ps.close();

                    ps = con.prepareStatement("SELECT mapid FROM trocklocations WHERE characterid = ?");
                    ps.setInt(1, charid);
                    rs = ps.executeQuery();
                    int r = 0;
                    while (rs.next()) {
                        ret.rocks[r] = rs.getInt("mapid");
                        r++;
                    }
                    while (r < 10) {
                        ret.rocks[r] = 999999999;
                        r++;
                    }
                    rs.close();
                    ps.close();

                    ps = con.prepareStatement("SELECT mapid FROM regrocklocations WHERE characterid = ?");
                    ps.setInt(1, charid);
                    rs = ps.executeQuery();
                    r = 0;
                    while (rs.next()) {
                        ret.regrocks[r] = rs.getInt("mapid");
                        r++;
                    }
                    while (r < 5) {
                        ret.regrocks[r] = 999999999;
                        r++;
                    }
                    rs.close();
                    ps.close();

                    ps = con.prepareStatement("SELECT mapid FROM hyperrocklocations WHERE characterid = ?");
                    ps.setInt(1, charid);
                    rs = ps.executeQuery();
                    r = 0;
                    while (rs.next()) {
                        ret.hyperrocks[r] = rs.getInt("mapid");
                        r++;
                    }
                    while (r < 13) {
                        ret.hyperrocks[r] = 999999999;
                        r++;
                    }
                    rs.close();
                    ps.close();

                    ps = con.prepareStatement("SELECT * FROM imps WHERE characterid = ?");
                    ps.setInt(1, charid);
                    rs = ps.executeQuery();
                    r = 0;
                    while (rs.next()) {
                        ret.imps[r] = new MapleImp(rs.getInt("itemid"));
                        ret.imps[r].setLevel(rs.getByte("level"));
                        ret.imps[r].setState(rs.getByte("state"));
                        ret.imps[r].setCloseness(rs.getShort("closeness"));
                        ret.imps[r].setFullness(rs.getShort("fullness"));
                        r++;
                    }
                    rs.close();
                    ps.close();

                    ps = con.prepareStatement("SELECT * FROM mountdata WHERE characterid = ?");
                    ps.setInt(1, charid);
                    rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new RuntimeException("No mount data found on SQL column");
                    }
                    final Item mount = ret.getInventory(MapleInventoryType.EQUIPPED).getItem((byte) -18);
                    ret.mount = new MapleMount(ret, mount != null ? mount.getItemId() : 0, GameConstants.GMS ? 80001000 : ret.stats.getSkillByJob(1004, ret.job), rs.getByte("Fatigue"), rs.getByte("Level"), rs.getInt("Exp"));
                    ps.close();
                    rs.close();

                    ret.stats.recalcLocalStats(true, ret);
                } else { // Not channel server
                    for (Pair<Item, MapleInventoryType> mit : ItemLoader.INVENTORY.loadItems(true, charid).values()) {
                        ret.getInventory(mit.getRight()).addFromDB(mit.getLeft());
                    }
                    ret.stats.recalcPVPRank(ret);
                }
            } catch (SQLException ess) {
                ess.printStackTrace();
                System.out.println("Failed to load character..");
                FileoutputUtil.outputFileError(FileoutputUtil.PacketEx_Log, ess);
            } finally {
                try {
                    if (ps != null) {
                        ps.close();
                    }
                    if (rs != null) {
                        rs.close();
                    }
                } catch (SQLException ignore) {
                    FileoutputUtil.outError("logs/数据库异常.txt", ignore);
                }
            }
        } catch (SQLException exxx) {
            FileoutputUtil.outError("logs/数据库异常.txt", exxx);
        }
        return ret;
    }

    public static void saveNewCharToDB(final MapleCharacter chr, final JobType type, short db) {
        Connection con = null;
        PreparedStatement ps = null;
        PreparedStatement pse = null;
        ResultSet rs = null;
        try {
            con = DBConPool.getInstance().getDataSource().getConnection();
            con.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
            con.setAutoCommit(false);

            ps = con.prepareStatement("INSERT INTO characters (level, str, dex, luk, `int`, hp, mp, maxhp, maxmp, sp, ap, skincolor, gender, job, hair, face, demonMarking, map, meso, party, buddyCapacity, pets, subcategory, accountid, name, world) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", DBConPool.RETURN_GENERATED_KEYS);
            ps.setInt(1, chr.level); // Level
            final PlayerStats stat = chr.stats;
            ps.setShort(2, stat.getStr()); // Str
            ps.setShort(3, stat.getDex()); // Dex
            ps.setShort(4, stat.getInt()); // Int
            ps.setShort(5, stat.getLuk()); // Luk
            ps.setInt(6, stat.getHp()); // HP
            ps.setInt(7, stat.getMp());
            ps.setInt(8, stat.getMaxHp()); // MP
            ps.setInt(9, stat.getMaxMp());
            final StringBuilder sps = new StringBuilder();
            for (int i = 0; i < chr.remainingSp.length; i++) {
                sps.append(chr.remainingSp[i]);
                sps.append(",");
            }
            final String sp = sps.toString();
            ps.setString(10, sp.substring(0, sp.length() - 1));
            ps.setShort(11, (short) chr.remainingAp); // Remaining AP
            ps.setByte(12, chr.skinColor);
            ps.setByte(13, chr.gender);
            ps.setShort(14, chr.job);
            ps.setInt(15, chr.hair);
            ps.setInt(16, chr.face);
            ps.setInt(17, chr.demonMarking);
            if (db < 0 || db > 2) {
                db = 0;
            }
            ps.setInt(18, db == 2 ? 3000600 : type.map);
            ps.setInt(19, chr.meso); // Meso
            ps.setInt(20, -1); // Party
            ps.setByte(21, chr.buddylist.getCapacity()); // Buddylist
            ps.setString(22, "-1,-1,-1");
            ps.setInt(23, db); //for now
            ps.setInt(24, chr.getAccountID());
            ps.setString(25, chr.name);
            ps.setByte(26, chr.world);
            ps.executeUpdate();

            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                chr.id = rs.getInt(1);
            } else {
                ps.close();
                rs.close();
                throw new DatabaseException("Inserting char failed.");
            }
            ps.close();
            rs.close();
            ps = con.prepareStatement("INSERT INTO queststatus (`queststatusid`, `characterid`, `quest`, `status`, `time`, `forfeited`, `customData`) VALUES (DEFAULT, ?, ?, ?, ?, ?, ?)", DBConPool.RETURN_GENERATED_KEYS);
            pse = con.prepareStatement("INSERT INTO queststatusmobs VALUES (DEFAULT, ?, ?, ?)");
            ps.setInt(1, chr.id);
            for (final MapleQuestStatus q : chr.quests.values()) {
                ps.setInt(2, q.getQuest().getId());
                ps.setInt(3, q.getStatus());
                ps.setInt(4, (int) (q.getCompletionTime() / 1000));
                ps.setInt(5, q.getForfeited());
                ps.setString(6, q.getCustomData());
                ps.execute();
                rs = ps.getGeneratedKeys();
                if (q.hasMobKills()) {
                    rs.next();
                    for (int mob : q.getMobKills().keySet()) {
                        pse.setLong(1, rs.getLong(1));
                        pse.setInt(2, mob);
                        pse.setInt(3, q.getMobKills(mob));
                        pse.execute();
                    }
                }
                rs.close();
            }
            ps.close();
            pse.close();

            ps = con.prepareStatement("INSERT INTO skills (characterid, skillid, skilllevel, masterlevel, expiration) VALUES (?, ?, ?, ?, ?)");
            ps.setInt(1, chr.id);

            for (final Entry<Skill, SkillEntry> skill : chr.skills.entrySet()) {
                if (GameConstants.isApplicableSkill(skill.getKey().getId())) { //do not save additional skills
                    ps.setInt(2, skill.getKey().getId());
                    ps.setInt(3, skill.getValue().skillevel);
                    ps.setByte(4, skill.getValue().masterlevel);
                    ps.setLong(5, skill.getValue().expiration);
                    ps.execute();
                }
            }
            ps.close();

            ps = con.prepareStatement("INSERT INTO inventoryslot (characterid, `equip`, `use`, `setup`, `etc`, `cash`) VALUES (?, ?, ?, ?, ?, ?)");
            ps.setInt(1, chr.id);
            ps.setByte(2, (byte) 32); // Eq
            ps.setByte(3, (byte) 32); // Use
            ps.setByte(4, (byte) 32); // Setup
            ps.setByte(5, (byte) 32); // ETC
            ps.setByte(6, (byte) 60); // Cash
            ps.execute();
            ps.close();

            ps = con.prepareStatement("INSERT INTO mountdata (characterid, `Level`, `Exp`, `Fatigue`) VALUES (?, ?, ?, ?)");
            ps.setInt(1, chr.id);
            ps.setByte(2, (byte) 1);
            ps.setInt(3, 0);
            ps.setByte(4, (byte) 0);
            ps.execute();
            ps.close();

            final int[] array1 = {
                2, 3, 64, 4, 65, 5, 6, 7, 17, 16,
                19, 18, 21, 20, 23, 25, 24, 27, 26, 29,
                31, 34, 35, 33, 38, 39, 37, 43, 40, 41,
                46, 44, 45, 50, 48, 59, 57, 56, 63, 62,
                61, 60};
            final int[] array2 = {
                4, 4, 6, 4, 6, 4, 4, 4, 4, 4,
                4, 4, 4, 4, 4, 4, 4, 4, 4, 5,
                4, 4, 4, 4, 4, 4, 4, 4, 4, 4,
                4, 5, 5, 4, 4, 6, 5, 5, 6, 6,
                6, 6
            };
            final int[] array3 = {
                10, 12, 105, 13, 106, 18, 23, 28, 5, 8,
                4, 0, 30, 27, 1, 19, 24, 15, 14, 52,
                2, 17, 11, 25, 20, 26, 3, 9, 16, 22,
                6, 50, 51, 7, 29, 100, 54, 53, 104, 103,
                102, 101
            };

            ps = con.prepareStatement("INSERT INTO keymap (characterid, `key`, `type`, `action`) VALUES (?, ?, ?, ?)");
            ps.setInt(1, chr.id);
            for (int i = 0; i < array1.length; i++) {
                ps.setInt(2, array1[i]);
                ps.setInt(3, array2[i]);
                ps.setInt(4, array3[i]);
                ps.execute();
            }
            ps.close();

            List<Pair<Item, MapleInventoryType>> listing = new ArrayList<Pair<Item, MapleInventoryType>>();
            for (final MapleInventory iv : chr.inventory) {
                for (final Item item : iv.list()) {
                    listing.add(new Pair<Item, MapleInventoryType>(item, iv.getType()));
                }
            }
            ItemLoader.INVENTORY.saveItems(listing, con, chr.id);

            con.commit();
        } catch (SQLException e) {
            FileoutputUtil.outputFileError(FileoutputUtil.PacketEx_Log, e);
            e.printStackTrace();
            System.err.println("[charsave] Error saving character data");
            try {
                con.rollback();
            } catch (SQLException ex) {
                FileoutputUtil.outputFileError(FileoutputUtil.PacketEx_Log, ex);
                ex.printStackTrace();
                System.err.println("[charsave] Error Rolling Back");
            }
        } finally {
            try {
                if (pse != null) {
                    pse.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
                con.setAutoCommit(true);
                con.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                FileoutputUtil.outputFileError(FileoutputUtil.PacketEx_Log, e);
                e.printStackTrace();
                System.err.println("[charsave] Error going back to autocommit mode");
                FileoutputUtil.outError("logs/数据库异常.txt", e);
            }
        }
    }

    public void saveToDB(boolean dc, boolean fromcs) {
        if (isClone()) {
            return;
        }
        Connection con = null;
        PreparedStatement ps = null;
        PreparedStatement pse = null;
        ResultSet rs = null;

        try {
            con = DBConPool.getInstance().getDataSource().getConnection();
            con.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
            con.setAutoCommit(false);

            ps = con.prepareStatement("UPDATE characters SET level = ?, fame = ?, str = ?, dex = ?, luk = ?, `int` = ?, exp = ?, hp = ?, mp = ?, maxhp = ?, maxmp = ?, sp = ?, ap = ?, gm = ?, skincolor = ?, gender = ?, job = ?, hair = ?, face = ?, demonMarking = ?, map = ?, meso = ?, hpApUsed = ?, spawnpoint = ?, party = ?, buddyCapacity = ?, pets = ?, subcategory = ?, marriageId = ?, currentrep = ?, totalrep = ?, gachexp = ?, fatigue = ?, charm = ?, charisma = ?, craft = ?, insight = ?, sense = ?, will = ?, totalwins = ?, totallosses = ?, pvpExp = ?, pvpPoints = ?,friendshipPoints = ?, JoeJoe = ?, Hermonniny = ?, LittleDragon = ?, Ika = ?, todayOnlineTime = ?, name = ? WHERE id = ?", DBConPool.RETURN_GENERATED_KEYS);
            ps.setInt(1, level);
            ps.setInt(2, fame);
            ps.setShort(3, stats.getStr());
            ps.setShort(4, stats.getDex());
            ps.setShort(5, stats.getLuk());
            ps.setShort(6, stats.getInt());
            ps.setInt(7, (level >= LoginServer.getMaxLevel() || (GameConstants.isKOC(job) && level >= LoginServer.getKocMaxLevel())) && !isIntern() ? 0 : exp);
            ps.setInt(8, stats.getHp() < 1 ? 50 : stats.getHp());
            ps.setInt(9, stats.getMp());
            ps.setInt(10, stats.getMaxHp());
            ps.setInt(11, stats.getMaxMp());
            final StringBuilder sps = new StringBuilder();
            for (int i = 0; i < remainingSp.length; i++) {
                sps.append(remainingSp[i]);
                sps.append(",");
            }
            final String sp = sps.toString();
            ps.setString(12, sp.substring(0, sp.length() - 1));
            ps.setShort(13, remainingAp);
            ps.setByte(14, gmLevel);
            ps.setByte(15, skinColor);
            ps.setByte(16, gender);
            ps.setShort(17, job);
            ps.setInt(18, hair);
            ps.setInt(19, face);
            ps.setInt(20, demonMarking);
            if (!fromcs && map != null) {
                if (map.getForcedReturnId() != 999999999 && map.getForcedReturnMap() != null) {
                    ps.setInt(21, map.getForcedReturnId());
                } else {
                    ps.setInt(21, stats.getHp() < 1 ? map.getReturnMapId() : map.getId());
                }
            } else {
                ps.setInt(21, mapid);
            }
            ps.setInt(22, meso);
            ps.setShort(23, hpApUsed);
            if (map == null) {
                ps.setByte(24, (byte) 0);
            } else {
                final MaplePortal closest = map.findClosestSpawnpoint(getTruePosition());
                ps.setByte(24, (byte) (closest != null ? closest.getId() : 0));
            }
            ps.setInt(25, party == null ? -1 : party.getId());
            ps.setShort(26, buddylist.getCapacity());
            final StringBuilder petz = new StringBuilder();
            int petLength = 0;
            for (final MaplePet pet : pets) {
                if (pet.getSummoned()) {
                    pet.saveToDb(con);
                    petz.append(pet.getInventoryPosition());
                    petz.append(",");
                    petLength++;
                }
            }
            while (petLength < 3) {
                petz.append("-1,");
                petLength++;
            }
            final String petstring = petz.toString();
            ps.setString(27, petstring.substring(0, petstring.length() - 1));
            ps.setByte(28, subcategory);
            ps.setInt(29, marriageId);
            ps.setInt(30, currentrep);
            ps.setInt(31, totalrep);
            ps.setInt(32, gachexp);
            ps.setShort(33, fatigue);
            ps.setInt(34, traits.get(MapleTraitType.charm).getTotalExp());
            ps.setInt(35, traits.get(MapleTraitType.charisma).getTotalExp());
            ps.setInt(36, traits.get(MapleTraitType.craft).getTotalExp());
            ps.setInt(37, traits.get(MapleTraitType.insight).getTotalExp());
            ps.setInt(38, traits.get(MapleTraitType.sense).getTotalExp());
            ps.setInt(39, traits.get(MapleTraitType.will).getTotalExp());
            ps.setInt(40, totalWins);
            ps.setInt(41, totalLosses);
            ps.setInt(42, pvpExp);
            ps.setInt(43, pvpPoints);
            ps.setInt(44, friendshipPoints);
            ps.setInt(45, friendships.get(MapleFriendshipType.JoeJoe).getPoints());
            ps.setInt(46, friendships.get(MapleFriendshipType.Hermonniny).getPoints());
            ps.setInt(47, friendships.get(MapleFriendshipType.LittleDragon).getPoints());
            ps.setInt(48, friendships.get(MapleFriendshipType.Ika).getPoints());
            ps.setInt(49, todayOnlineTime);
            ps.setString(50, name);
            ps.setInt(51, id);
            if (ps.executeUpdate() < 1) {
                ps.close();
                throw new DatabaseException("Character not in database (" + id + ")");
            }
            ps.close();
            if (changed_skillmacros) {
                deleteWhereCharacterId(con, "DELETE FROM skillmacros WHERE characterid = ?");
                for (int i = 0; i < 5; i++) {
                    final SkillMacro macro = skillMacros[i];
                    if (macro != null) {
                        ps = con.prepareStatement("INSERT INTO skillmacros (characterid, skill1, skill2, skill3, name, shout, position) VALUES (?, ?, ?, ?, ?, ?, ?)");
                        ps.setInt(1, id);
                        ps.setInt(2, macro.getSkill1());
                        ps.setInt(3, macro.getSkill2());
                        ps.setInt(4, macro.getSkill3());
                        ps.setString(5, macro.getName());
                        ps.setInt(6, macro.getShout());
                        ps.setInt(7, i);
                        ps.execute();
                        ps.close();
                    }
                }
            }

            deleteWhereCharacterId(con, "DELETE FROM inventoryslot WHERE characterid = ?");
            ps = con.prepareStatement("INSERT INTO inventoryslot (characterid, `equip`, `use`, `setup`, `etc`, `cash`) VALUES (?, ?, ?, ?, ?, ?)");
            ps.setInt(1, id);
            ps.setByte(2, getInventory(MapleInventoryType.EQUIP).getSlotLimit());
            ps.setByte(3, getInventory(MapleInventoryType.USE).getSlotLimit());
            ps.setByte(4, getInventory(MapleInventoryType.SETUP).getSlotLimit());
            ps.setByte(5, getInventory(MapleInventoryType.ETC).getSlotLimit());
            ps.setByte(6, getInventory(MapleInventoryType.CASH).getSlotLimit());
            ps.execute();
            ps.close();
            saveInventory(con);
            if (changed_questinfo) {
                deleteWhereCharacterId(con, "DELETE FROM questinfo WHERE characterid = ?");
                ps = con.prepareStatement("INSERT INTO questinfo (`characterid`, `quest`, `customData`) VALUES (?, ?, ?)");
                ps.setInt(1, id);
                for (final Entry<Integer, String> q : questinfo.entrySet()) {
                    ps.setInt(2, q.getKey());
                    ps.setString(3, q.getValue());
                    ps.execute();
                }
                ps.close();
            }
            deleteWhereCharacterId(con, "DELETE FROM queststatus WHERE characterid = ?");
            ps = con.prepareStatement("INSERT INTO queststatus (`queststatusid`, `characterid`, `quest`, `status`, `time`, `forfeited`, `customData`) VALUES (DEFAULT, ?, ?, ?, ?, ?, ?)", DBConPool.RETURN_GENERATED_KEYS);
            pse = con.prepareStatement("INSERT INTO queststatusmobs VALUES (DEFAULT, ?, ?, ?)");
            ps.setInt(1, id);
            for (final MapleQuestStatus q : quests.values()) {
                ps.setInt(2, q.getQuest().getId());
                ps.setInt(3, q.getStatus());
                ps.setInt(4, (int) (q.getCompletionTime() / 1000));
                ps.setInt(5, q.getForfeited());
                ps.setString(6, q.getCustomData());
                ps.execute();
                rs = ps.getGeneratedKeys();
                if (q.hasMobKills()) {
                    rs.next();
                    for (int mob : q.getMobKills().keySet()) {
                        pse.setLong(1, rs.getLong(1));
                        pse.setInt(2, mob);
                        pse.setInt(3, q.getMobKills(mob));
                        pse.execute();
                    }
                }
                rs.close();
            }
            ps.close();
            pse.close();

            if (changed_skills) {
                deleteWhereCharacterId(con, "DELETE FROM skills WHERE characterid = ?");
                ps = con.prepareStatement("INSERT INTO skills (characterid, skillid, skilllevel, masterlevel, expiration) VALUES (?, ?, ?, ?, ?)");
                ps.setInt(1, id);

                for (final Entry<Skill, SkillEntry> skill : skills.entrySet()) {
                    if (GameConstants.isApplicableSkill(skill.getKey().getId())) { //do not save additional skills
                        ps.setInt(2, skill.getKey().getId());
                        ps.setInt(3, skill.getValue().skillevel);
                        ps.setByte(4, skill.getValue().masterlevel);
                        ps.setLong(5, skill.getValue().expiration);
                        ps.execute();
                    }
                }
                ps.close();
            }

            List<MapleCoolDownValueHolder> cd = getCooldowns();
            if (dc && cd.size() > 0) {
                ps = con.prepareStatement("INSERT INTO skills_cooldowns (charid, SkillID, StartTime, length) VALUES (?, ?, ?, ?)");
                ps.setInt(1, getId());
                for (final MapleCoolDownValueHolder cooling : cd) {
                    ps.setInt(2, cooling.skillId);
                    ps.setLong(3, cooling.startTime);
                    ps.setLong(4, cooling.length);
                    ps.execute();
                }
                ps.close();
            }

            if (changed_savedlocations) {
                deleteWhereCharacterId(con, "DELETE FROM savedlocations WHERE characterid = ?");
                ps = con.prepareStatement("INSERT INTO savedlocations (characterid, `locationtype`, `map`) VALUES (?, ?, ?)");
                ps.setInt(1, id);
                for (final SavedLocationType savedLocationType : SavedLocationType.values()) {
                    if (savedLocations[savedLocationType.getValue()] != -1) {
                        ps.setInt(2, savedLocationType.getValue());
                        ps.setInt(3, savedLocations[savedLocationType.getValue()]);
                        ps.execute();
                    }
                }
                ps.close();
            }

            if (changed_reports) {
                deleteWhereCharacterId(con, "DELETE FROM reports WHERE characterid = ?");
                ps = con.prepareStatement("INSERT INTO reports VALUES(DEFAULT, ?, ?, ?)");
                for (Entry<ReportType, Integer> achid : reports.entrySet()) {
                    ps.setInt(1, id);
                    ps.setByte(2, achid.getKey().i);
                    ps.setInt(3, achid.getValue());
                    ps.execute();
                }
                ps.close();
            }

            if (buddylist.changed()) {
                deleteWhereCharacterId(con, "DELETE FROM buddies WHERE characterid = ?");
                ps = con.prepareStatement("INSERT INTO buddies (characterid, `buddyid`, `pending`) VALUES (?, ?, ?)");
                ps.setInt(1, id);
                for (BuddylistEntry entry : buddylist.getBuddies()) {
                    ps.setInt(2, entry.getCharacterId());
                    ps.setInt(3, entry.isVisible() ? 0 : 1);
                    ps.execute();
                }
                ps.close();
                buddylist.setChanged(false);
            }

            ps = con.prepareStatement("UPDATE accounts SET "/*+"`ACash` = ?, "*/ /*+ "`mPoints` = ?,"*//*+" `points` = ?,"*/ + " `vpoints` = ?, `VIP` = ? WHERE id = ?");
            //ps.setInt(1, acash);
            //ps.setInt(1, maplepoints);
            //ps.setInt(2, points);
            ps.setInt(1, vpoints);
            ps.setInt(2, vip);
            ps.setInt(3, client.getAccID());
            ps.executeUpdate();
            ps.close();

            if (storage != null) {
                storage.saveToDB(con);
            }
            if (cs != null) {
                cs.save(con);
            }
            PlayerNPC.updateByCharId(this, con);
            keylayout.saveKeys(id, con);
            mount.saveMount(id, con);
            monsterbook.saveCards(accountid, con);

            deleteWhereCharacterId(con, "DELETE FROM familiars WHERE characterid = ?");
            ps = con.prepareStatement("INSERT INTO familiars (characterid, expiry, name, fatigue, vitality, familiar) VALUES (?, ?, ?, ?, ?, ?)");
            ps.setInt(1, id);
            for (MonsterFamiliar f : familiars.values()) {
                ps.setLong(2, f.getExpiry());
                ps.setString(3, f.getName());
                ps.setInt(4, f.getFatigue());
                ps.setByte(5, f.getVitality());
                ps.setInt(6, f.getFamiliar());
                ps.executeUpdate();
            }
            ps.close();

            deleteWhereCharacterId(con, "DELETE FROM imps WHERE characterid = ?");
            ps = con.prepareStatement("INSERT INTO imps (characterid, itemid, closeness, fullness, state, level) VALUES (?, ?, ?, ?, ?, ?)");
            ps.setInt(1, id);
            for (int i = 0; i < imps.length; i++) {
                if (imps[i] != null) {
                    ps.setInt(2, imps[i].getItemId());
                    ps.setShort(3, imps[i].getCloseness());
                    ps.setShort(4, imps[i].getFullness());
                    ps.setByte(5, imps[i].getState());
                    ps.setByte(6, imps[i].getLevel());
                    ps.executeUpdate();
                }
            }
            ps.close();
            if (changed_wishlist) {
                deleteWhereCharacterId(con, "DELETE FROM wishlist WHERE characterid = ?");
                for (int i = 0; i < getWishlistSize(); i++) {
                    ps = con.prepareStatement("INSERT INTO wishlist(characterid, sn) VALUES(?, ?) ");
                    ps.setInt(1, getId());
                    ps.setInt(2, wishlist[i]);
                    ps.execute();
                    ps.close();
                }
            }
            if (changed_trocklocations) {
                deleteWhereCharacterId(con, "DELETE FROM trocklocations WHERE characterid = ?");
                for (int i = 0; i < rocks.length; i++) {
                    if (rocks[i] != 999999999) {
                        ps = con.prepareStatement("INSERT INTO trocklocations(characterid, mapid) VALUES(?, ?) ");
                        ps.setInt(1, getId());
                        ps.setInt(2, rocks[i]);
                        ps.execute();
                        ps.close();
                    }
                }
            }

            if (changed_regrocklocations) {
                deleteWhereCharacterId(con, "DELETE FROM regrocklocations WHERE characterid = ?");
                for (int i = 0; i < regrocks.length; i++) {
                    if (regrocks[i] != 999999999) {
                        ps = con.prepareStatement("INSERT INTO regrocklocations(characterid, mapid) VALUES(?, ?) ");
                        ps.setInt(1, getId());
                        ps.setInt(2, regrocks[i]);
                        ps.execute();
                        ps.close();
                    }
                }
            }
            if (changed_hyperrocklocations) {
                deleteWhereCharacterId(con, "DELETE FROM hyperrocklocations WHERE characterid = ?");
                for (int i = 0; i < hyperrocks.length; i++) {
                    if (hyperrocks[i] != 999999999) {
                        ps = con.prepareStatement("INSERT INTO hyperrocklocations(characterid, mapid) VALUES(?, ?) ");
                        ps.setInt(1, getId());
                        ps.setInt(2, hyperrocks[i]);
                        ps.execute();
                        ps.close();
                    }
                }
            }
            if (changed_extendedSlots) {
                deleteWhereCharacterId(con, "DELETE FROM extendedSlots WHERE characterid = ?");
                for (int i : extendedSlots) {
                    if (getInventory(MapleInventoryType.ETC).findById(i) != null) { //just in case
                        ps = con.prepareStatement("INSERT INTO extendedSlots(characterid, itemId) VALUES(?, ?) ");
                        ps.setInt(1, getId());
                        ps.setInt(2, i);
                        ps.execute();
                        ps.close();
                    }
                }
            }
            changed_wishlist = false;
            changed_trocklocations = false;
            changed_regrocklocations = false;
            changed_hyperrocklocations = false;
            changed_skillmacros = false;
            changed_savedlocations = false;
            changed_questinfo = false;
            changed_extendedSlots = false;
            changed_skills = false;
            changed_reports = false;
            con.commit();
        } catch (SQLException e) {
            FileoutputUtil.outputFileError(FileoutputUtil.PacketEx_Log, e);
            e.printStackTrace();
            System.err.println(MapleClient.getLogMessage(this, "[charsave] Error saving character data") + e);
            try {
                con.rollback();
            } catch (SQLException ex) {
                FileoutputUtil.outputFileError(FileoutputUtil.PacketEx_Log, ex);
                System.err.println(MapleClient.getLogMessage(this, "[charsave] Error Rolling Back") + e);
            }
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (pse != null) {
                    pse.close();
                }
                if (rs != null) {
                    rs.close();
                }
                con.setAutoCommit(true);
                con.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                FileoutputUtil.outputFileError(FileoutputUtil.PacketEx_Log, e);
                System.err.println(MapleClient.getLogMessage(this, "[charsave] Error going back to autocommit mode") + e);
                FileoutputUtil.outError("logs/数据库异常.txt", e);
            }
        }
    }

    private void deleteWhereCharacterId(Connection con, String sql) throws SQLException {
        deleteWhereCharacterId(con, sql, id);
    }

    public static void deleteWhereCharacterId(Connection con, String sql, int id) throws SQLException {
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();
    }

    public static void deleteWhereCharacterId_NoLock(Connection con, String sql, int id) throws SQLException {
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ps.execute();
        ps.close();
    }

    public void saveInventory(final Connection con) throws SQLException {
        List<Pair<Item, MapleInventoryType>> listing = new ArrayList<Pair<Item, MapleInventoryType>>();
        for (final MapleInventory iv : inventory) {
            for (final Item item : iv.list()) {
                listing.add(new Pair<Item, MapleInventoryType>(item, iv.getType()));
            }
        }
        if (con != null) {
            ItemLoader.INVENTORY.saveItems(listing, con, id);
        } else {
            ItemLoader.INVENTORY.saveItems(listing, id);
        }
    }

    public final PlayerStats getStat() {
        return stats;
    }

    public final void QuestInfoPacket(final tools.data.MaplePacketLittleEndianWriter mplew) {
        mplew.writeShort(questinfo.size());

        for (final Entry<Integer, String> q : questinfo.entrySet()) {
            mplew.writeShort(q.getKey());
            mplew.writeMapleAsciiString(q.getValue() == null ? "" : q.getValue());
        }
    }

    public final void updateInfoQuest(final int questid, final String data) {
        questinfo.put(questid, data);
        changed_questinfo = true;
        client.getSession().write(MaplePacketCreator.updateInfoQuest(questid, data));
    }

    public final String getInfoQuest(final int questid) {
        if (questinfo.containsKey(questid)) {
            return questinfo.get(questid);
        }
        return "";
    }

    public final int getNumQuest() {
        int i = 0;
        for (final MapleQuestStatus q : quests.values()) {
            if (q.getStatus() == 2 && !(q.isCustom())) {
                i++;
            }
        }
        return i;
    }

    public final byte getQuestStatus(final int quest) {
        final MapleQuest qq = MapleQuest.getInstance(quest);
        if (getQuestNoAdd(qq) == null) {
            return 0;
        }
        return getQuestNoAdd(qq).getStatus();
    }

    public final MapleQuestStatus getQuest(final MapleQuest quest) {
        if (!quests.containsKey(quest)) {
            return new MapleQuestStatus(quest, (byte) 0);
        }
        return quests.get(quest);
    }

    public final void setQuestAdd(final MapleQuest quest, final byte status, final String customData) {
        if (!quests.containsKey(quest)) {
            final MapleQuestStatus stat = new MapleQuestStatus(quest, status);
            stat.setCustomData(customData);
            quests.put(quest, stat);
        }
    }

    public final MapleQuestStatus getQuestNAdd(final MapleQuest quest) {
        if (!quests.containsKey(quest)) {
            final MapleQuestStatus status = new MapleQuestStatus(quest, (byte) 0);
            quests.put(quest, status);
            return status;
        }
        return quests.get(quest);
    }

    public final MapleQuestStatus getQuestNoAdd(final MapleQuest quest) {
        return quests.get(quest);
    }

    public final MapleQuestStatus getQuestRemove(final MapleQuest quest) {
        return quests.remove(quest);
    }

    public final void updateQuest(final MapleQuestStatus quest) {
        updateQuest(quest, false);
    }

    public final void updateQuest(final MapleQuestStatus quest, final boolean update) {
        quests.put(quest.getQuest(), quest);
        if (!(quest.isCustom())) {
            client.getSession().write(MaplePacketCreator.updateQuest(quest));
            if (quest.getStatus() == 1 && !update) {
                client.getSession().write(MaplePacketCreator.updateQuestInfo(this, quest.getQuest().getId(), quest.getNpc(), (byte) 10));
            }
        }
    }

    public final Map<Integer, String> getInfoQuest_Map() {
        return questinfo;
    }

    public final Map<MapleQuest, MapleQuestStatus> getQuest_Map() {
        return quests;
    }

    public Integer getBuffedValue(MapleBuffStat effect) {
        final MapleBuffStatValueHolder mbsvh = effects.get(effect);
        return mbsvh == null ? null : mbsvh.value;
    }

    public final Integer getBuffedSkill_X(final MapleBuffStat effect) {
        final MapleBuffStatValueHolder mbsvh = effects.get(effect);
        if (mbsvh == null) {
            return null;
        }
        return mbsvh.effect.getX();
    }

    public final Integer getBuffedSkill_Y(final MapleBuffStat effect) {
        final MapleBuffStatValueHolder mbsvh = effects.get(effect);
        if (mbsvh == null) {
            return null;
        }
        return mbsvh.effect.getY();
    }

    public boolean isBuffFrom(MapleBuffStat stat, Skill skill) {
        final MapleBuffStatValueHolder mbsvh = effects.get(stat);
        if (mbsvh == null || mbsvh.effect == null) {
            return false;
        }
        return mbsvh.effect.isSkill() && mbsvh.effect.getSourceId() == skill.getId();
    }

    public int getBuffSource(MapleBuffStat stat) {
        final MapleBuffStatValueHolder mbsvh = effects.get(stat);
        return mbsvh == null ? -1 : mbsvh.effect.getSourceId();
    }

    public int getTrueBuffSource(MapleBuffStat stat) {
        final MapleBuffStatValueHolder mbsvh = effects.get(stat);
        return mbsvh == null ? -1 : (mbsvh.effect.isSkill() ? mbsvh.effect.getSourceId() : -mbsvh.effect.getSourceId());
    }

    public int getItemQuantity(int itemid, boolean checkEquipped) {
        int possesed = inventory[GameConstants.getInventoryType(itemid).ordinal()].countById(itemid);
        if (checkEquipped) {
            possesed += inventory[MapleInventoryType.EQUIPPED.ordinal()].countById(itemid);
        }
        return possesed;
    }

    public void setBuffedValue(MapleBuffStat effect, int value) {
        final MapleBuffStatValueHolder mbsvh = effects.get(effect);
        if (mbsvh == null) {
            return;
        }
        mbsvh.value = value;
    }

    public void setSchedule(MapleBuffStat effect, ScheduledFuture<?> sched) {
        final MapleBuffStatValueHolder mbsvh = effects.get(effect);
        if (mbsvh == null) {
            return;
        }
        mbsvh.schedule.cancel(false);
        mbsvh.schedule = sched;
    }

    public Long getBuffedStarttime(MapleBuffStat effect) {
        final MapleBuffStatValueHolder mbsvh = effects.get(effect);
        return mbsvh == null ? null : Long.valueOf(mbsvh.startTime);
    }

    public MapleStatEffect getStatForBuff(MapleBuffStat effect) {
        final MapleBuffStatValueHolder mbsvh = effects.get(effect);
        return mbsvh == null ? null : mbsvh.effect;
    }

    public void doDragonBlood() {
        final MapleStatEffect bloodEffect = getStatForBuff(MapleBuffStat.DRAGONBLOOD);
        if (bloodEffect == null) {
            lastDragonBloodTime = 0;
            return;
        }
        prepareDragonBlood();
        if (stats.getHp() - bloodEffect.getX() <= 1) {
            cancelBuffStats(MapleBuffStat.DRAGONBLOOD);
        } else {
            addHP(-bloodEffect.getX());
            client.getSession().write(MaplePacketCreator.showOwnBuffEffect(bloodEffect.getSourceId(), 7, getLevel(), bloodEffect.getLevel()));
            map.broadcastMessage(MapleCharacter.this, MaplePacketCreator.showBuffeffect(getId(), bloodEffect.getSourceId(), 7, getLevel(), bloodEffect.getLevel()), false);
        }
    }

    public final boolean canBlood(long now) {
        return lastDragonBloodTime > 0 && lastDragonBloodTime + 4000 < now;
    }

    private void prepareDragonBlood() {
        lastDragonBloodTime = System.currentTimeMillis();
    }

    public void doRecovery() {
        MapleStatEffect bloodEffect = getStatForBuff(MapleBuffStat.RECOVERY);
        if (bloodEffect == null) {
            bloodEffect = getStatForBuff(MapleBuffStat.MECH_CHANGE);
            if (bloodEffect == null) {
                lastRecoveryTime = 0;
                return;
            } else if (bloodEffect.getSourceId() == 35121005) {
                prepareRecovery();
                if (stats.getMp() < bloodEffect.getU()) {
                    cancelEffectFromBuffStat(MapleBuffStat.MONSTER_RIDING);
                    cancelEffectFromBuffStat(MapleBuffStat.MECH_CHANGE);
                } else {
                    addMP(-bloodEffect.getU());
                }
            }
        } else {
            prepareRecovery();
            if (stats.getHp() >= stats.getCurrentMaxHp()) {
                cancelEffectFromBuffStat(MapleBuffStat.RECOVERY);
            } else {
                healHP(bloodEffect.getX());
            }
        }
    }

    public final boolean canRecover(long now) {
        return lastRecoveryTime > 0 && lastRecoveryTime + 5000 < now;
    }

    private void prepareRecovery() {
        lastRecoveryTime = System.currentTimeMillis();
    }

    public void startMapTimeLimitTask(int time, final MapleMap to) {
        if (time <= 0) { //jail
            time = 1;
        }
        client.getSession().write(MaplePacketCreator.getClock(time));
        final MapleMap ourMap = getMap();
        time *= 1000;
        mapTimeLimitTask = MapTimer.getInstance().register(new Runnable() {

            @Override
            public void run() {
                if (ourMap.getId() == GameConstants.JAIL) {
                    getQuestNAdd(MapleQuest.getInstance(GameConstants.JAIL_TIME)).setCustomData(String.valueOf(System.currentTimeMillis()));
                    getQuestNAdd(MapleQuest.getInstance(GameConstants.JAIL_QUEST)).setCustomData("0"); //release them!
                }
                changeMap(to, to.getPortal(0));
            }
        }, time, time);
    }

    public boolean canDOT(long now) {
        return lastDOTTime > 0 && lastDOTTime + 8000 < now;
    }

    public boolean hasDOT() {
        return dotHP > 0;
    }

    public void doDOT() {
        addHP(-(dotHP * 4));
        dotHP = 0;
        lastDOTTime = 0;
    }

    public void setDOT(int d, int source, int sourceLevel) {
        this.dotHP = d;
        addHP(-(dotHP * 4));
        map.broadcastMessage(MaplePacketCreator.getPVPMist(id, source, sourceLevel, d));
        lastDOTTime = System.currentTimeMillis();
    }

    public void startFishingTask() {
        cancelFishingTask();
        lastFishingTime = System.currentTimeMillis();
    }

    public boolean canFish(long now) {
        return lastFishingTime > 0 && lastFishingTime + GameConstants.getFishingTime(stats.canFishVIP, isGM()) < now;
    }

    public void doFish(long now) {
        lastFishingTime = now;
        final boolean expMulti = haveItem(2300001, 1, false, true);
        if (client == null || client.getPlayer() == null || !client.isReceiving() || (!expMulti && !haveItem(GameConstants.GMS ? 2270008 : 2300000, 1, false, true)) || !GameConstants.isFishingMap(getMapId()) || !stats.canFish || chair <= 0) {
            cancelFishingTask();
            return;
        }
        MapleInventoryManipulator.removeById(client, MapleInventoryType.USE, expMulti ? 2300001 : (GameConstants.GMS ? 2270008 : 2300000), 1, false, false);
        boolean passed = false;
        while (!passed) {
            int randval = RandomRewards.getFishingReward();
            switch (randval) {
                case 0: // Meso
                    final int money = Randomizer.rand(expMulti ? 15 : 10, expMulti ? 3000 : 1000);
                    gainMeso(money, true);
                    passed = true;
                    break;
                case 1: // EXP
                    //final int experi = Math.min(Randomizer.nextInt(Math.abs(getNeededExp() / 200) + 1), 500000);
                    //gainExp(expMulti ? (experi * 3 / 2) : experi, true, false, true);
                    final int experi = Randomizer.nextInt(Math.abs(GameConstants.getExpNeededForLevel(level) / 800) + 1);
                    int beilu = (getLevel() <= 50) ? 1 : (getLevel() > 50 && getLevel() <= 100) ? 2 : (getLevel() > 100 && getLevel() <= 150) ? 4 : (getLevel() > 150 && getLevel() <= LoginServer.getMaxLevel()) ? 8 : 1;
                    final int experi2 = ((int) Math.ceil(experi / beilu));
                    gainExp(expMulti ? (experi2 * 3 / 2) : experi2, true, false, true);
                    passed = true;
                    break;
                case 4350000: //
                    int quantity = Randomizer.rand(1, 3);
                    modifyCSPoints(2, quantity, true);
                    passed = true;
                    break;
                case 4350001: //
                    modifyCSPoints(3, 5, true);
                    passed = true;
                    break;
                default:
                    if (MapleItemInformationProvider.getInstance().itemExists(randval)) {
                        MapleInventoryManipulator.addById(client, randval, (short) 1, "Fishing" + " on " + FileoutputUtil.CurrentReadable_Date());
                        passed = true;
                    }
                    break;
            }
        }
        map.broadcastMessage(UIPacket.fishingCaught(id, passed ? 1 : 0));

    }

    public void cancelMapTimeLimitTask() {
        if (mapTimeLimitTask != null) {
            mapTimeLimitTask.cancel(false);
            mapTimeLimitTask = null;
        }
    }

    public int getNeededExp() {
        return GameConstants.getExpNeededForLevel(level);
    }

    public void cancelFishingTask() {
        lastFishingTime = 0;
    }

    public void registerEffect(MapleStatEffect effect, long starttime, ScheduledFuture<?> schedule, int from) {
        registerEffect(effect, starttime, schedule, effect.getStatups(), false, effect.getDuration(), from);
    }

    public void registerEffect(MapleStatEffect effect, long starttime, ScheduledFuture<?> schedule, Map<MapleBuffStat, Integer> statups, boolean silent, final int localDuration, final int cid) {
        if (effect.isHide()) {
            map.broadcastMessage(this, MaplePacketCreator.removePlayerFromMap(getId()), false);
        } else if (effect.isDragonBlood()) {
            prepareDragonBlood();
        } else if (effect.isRecovery()) {
            prepareRecovery();
        } else if (effect.isBerserk()) {
            checkBerserk();
        } else if (effect.isMonsterRiding_()) {
            getMount().startSchedule();
        }
        int clonez = 0;
        for (Entry<MapleBuffStat, Integer> statup : statups.entrySet()) {
            if (statup.getKey() == MapleBuffStat.ILLUSION) {
                clonez = statup.getValue();
            }
            int value = statup.getValue().intValue();
            if (statup.getKey() == MapleBuffStat.MONSTER_RIDING) {
                if (effect.getSourceId() == 5221006 && battleshipHP <= 0) {
                    battleshipHP = maxBattleshipHP(effect.getSourceId()); //copy this as well
                }
                removeFamiliar();
            }
            effects.put(statup.getKey(), new MapleBuffStatValueHolder(effect, starttime, schedule, value, localDuration, cid, effect.getSourceId()));
            skillID.put(effect.getSourceId(), new MapleBuffStatValueHolder(effect, starttime, schedule, value, localDuration, cid, effect.getSourceId()));
        }
        if (clonez > 0) {
            int cloneSize = Math.max(getNumClones(), getCloneSize());
            if (clonez > cloneSize) { //how many clones to summon
                for (int i = 0; i < clonez - cloneSize; i++) { //1-1=0
                    cloneLook();
                }
            }
        }
        if (!silent) {
            stats.recalcLocalStats(this);
        }
    }

    public List<MapleBuffStat> getBuffStats(final MapleStatEffect effect, final long startTime) {
        final List<MapleBuffStat> bstats = new ArrayList<MapleBuffStat>();
        final Map<MapleBuffStat, MapleBuffStatValueHolder> allBuffs = new EnumMap<MapleBuffStat, MapleBuffStatValueHolder>(effects);
        for (Entry<MapleBuffStat, MapleBuffStatValueHolder> stateffect : allBuffs.entrySet()) {
            final MapleBuffStatValueHolder mbsvh = stateffect.getValue();
            if (mbsvh.effect.sameSource(effect) && (startTime == -1 || startTime == mbsvh.startTime || stateffect.getKey().canStack())) {
                bstats.add(stateffect.getKey());
            }
        }
        return bstats;
    }

    public List<MapleBuffStat> getBuffStatsFromStatEffect(final MapleStatEffect effect, final long startTime) {
        final List<MapleBuffStat> bstats = new ArrayList<MapleBuffStat>();
        final Map<MapleBuffStat, MapleBuffStatValueHolder> allBuffs = new EnumMap<MapleBuffStat, MapleBuffStatValueHolder>(effects);
        for (Entry<MapleBuffStat, MapleBuffStatValueHolder> stateffect : allBuffs.entrySet()) {
            final MapleBuffStatValueHolder mbsvh = stateffect.getValue();
            if (mbsvh.effect.sameSource(effect) && (startTime == -1 || startTime == mbsvh.startTime || stateffect.getKey().canStack())) {
                bstats.add(stateffect.getKey());
                skillID.put(effect.getSourceId(), mbsvh);
            }
        }
        return bstats;
    }

    private boolean deregisterBuffStats(List<MapleBuffStat> stats) {
        boolean clonez = false;
        List<MapleBuffStatValueHolder> effectsToCancel = new ArrayList<MapleBuffStatValueHolder>(stats.size());
        for (MapleBuffStat stat : stats) {
            final MapleBuffStatValueHolder mbsvh = effects.remove(stat);
            if (mbsvh != null) {
                skillID.remove(mbsvh.effect.getSourceId());
                boolean addMbsvh = true;
                for (MapleBuffStatValueHolder contained : effectsToCancel) {
                    if (mbsvh.startTime == contained.startTime && contained.effect == mbsvh.effect) {
                        addMbsvh = false;
                    }
                }
                if (addMbsvh) {
                    effectsToCancel.add(mbsvh);
                }
                if (stat == MapleBuffStat.SUMMON || stat == MapleBuffStat.PUPPET || stat == MapleBuffStat.REAPER || stat == MapleBuffStat.BEHOLDER || stat == MapleBuffStat.DAMAGE_BUFF || stat == MapleBuffStat.RAINING_MINES || stat == MapleBuffStat.ANGEL_ATK) {
                    final int summonId = mbsvh.effect.getSourceId();
                    final List<MapleSummon> toRemove = new ArrayList<MapleSummon>();
                    //visibleMapObjectsLock.writeLock().lock(); //We need to lock this later on anyway so do it now to prevent deadlocks.
                    //summonsLock.writeLock().lock();
                    try {
                        try {
                            for (MapleSummon summon : summons) {
                                if (summon.getSkill() == summonId || (stat == MapleBuffStat.RAINING_MINES && summonId == 33101008) || (summonId == 35121009 && summon.getSkill() == 35121011) || ((summonId == 86 || summonId == 88 || summonId == 91) && summon.getSkill() == summonId + 999) || ((summonId == 1085 || summonId == 1087 || summonId == 1090 || summonId == 1179) && summon.getSkill() == summonId - 999)) { //removes bots n tots
                                    map.broadcastMessage(MaplePacketCreator.removeSummon(summon, true));
                                    map.removeMapObject(summon);
                                    visibleMapObjects.remove(summon);
                                    toRemove.add(summon);
                                }
                            }
                            for (MapleSummon s : toRemove) {
                                summons.remove(s);
                            }
                        } catch (Exception ex) {
                            System.err.println("deregisterBuffStats失敗" + ex);
                            FileoutputUtil.outputFileError("logs/deregisterBuffStats失敗.txt", ex);
                        }
                    } finally {
                        //summonsLock.writeLock().unlock();
                        //visibleMapObjectsLock.writeLock().unlock(); //lolwut
                    }
                    if (summonId == 3111005 || summonId == 3211005) {
                        cancelEffectFromBuffStat(MapleBuffStat.SPIRIT_LINK);
                    }
                } else if (stat == MapleBuffStat.DRAGONBLOOD) {
                    lastDragonBloodTime = 0;
                } else if (stat == MapleBuffStat.RECOVERY || mbsvh.effect.getSourceId() == 35121005) {
                    lastRecoveryTime = 0;
                } else if (stat == MapleBuffStat.HOMING_BEACON || stat == MapleBuffStat.ARCANE_AIM) {
                    linkMobs.clear();
                } else if (stat == MapleBuffStat.ILLUSION) {
                    disposeClones();
                    clonez = true;
                }
            }
        }
        for (MapleBuffStatValueHolder cancelEffectCancelTasks : effectsToCancel) {
            //if (getBuffStatsFromStatEffect(cancelEffectCancelTasks.effect, cancelEffectCancelTasks.startTime).size() == 0) {
            if (getBuffStats(cancelEffectCancelTasks.effect, cancelEffectCancelTasks.startTime).size() == 0) {
                if (cancelEffectCancelTasks.schedule != null) {
                    cancelEffectCancelTasks.schedule.cancel(false);
                }
            }
        }
        return clonez;
    }

    /**
     * @param effect
     * @param overwrite when overwrite is set no data is sent and all the
     * Buffstats in the StatEffect are deregistered
     * @param startTime
     */
    public void cancelEffect(final MapleStatEffect effect, final boolean overwrite, final long startTime) {
        if (effect == null) {
            return;
        }
        cancelEffect(effect, overwrite, startTime, effect.getStatups());
    }

    public void cancelEffect(final MapleStatEffect effect, final boolean overwrite, final long startTime, Map<MapleBuffStat, Integer> statups) {
        if (effect == null) {
            return;
        }
        List<MapleBuffStat> buffstats;
        if (!overwrite) {
            buffstats = getBuffStats(effect, startTime);
        } else {
            buffstats = new ArrayList<MapleBuffStat>(statups.keySet());
        }
        if (buffstats.size() <= 0) {
            return;
        }
        if (effect.isInfinity() && getBuffedValue(MapleBuffStat.INFINITY) != null) { //before
            int duration = Math.max(effect.getDuration(), effect.alchemistModifyVal(this, effect.getDuration(), false));
            final long start = getBuffedStarttime(MapleBuffStat.INFINITY);
            duration += (int) ((start - System.currentTimeMillis()));
            if (duration > 0) {
                final int neworbcount = getBuffedValue(MapleBuffStat.INFINITY) + effect.getDamage();
                final Map<MapleBuffStat, Integer> stat = new EnumMap<MapleBuffStat, Integer>(MapleBuffStat.class);
                stat.put(MapleBuffStat.INFINITY, neworbcount);
                setBuffedValue(MapleBuffStat.INFINITY, neworbcount);
                //client.getSession().write(MaplePacketCreator.giveBuff(effect.getSourceId(), duration, stat, effect));
                addHP((int) (effect.getHpR() * this.stats.getCurrentMaxHp()));
                addMP((int) (effect.getMpR() * this.stats.getCurrentMaxMp()));
                setSchedule(MapleBuffStat.INFINITY, BuffTimer.getInstance().schedule(new CancelEffectAction(this, effect, start, stat), effect.alchemistModifyVal(this, 4000, false)));
                return;
            }
        }
        if (buffstats.contains(MapleBuffStat.MONSTER_RIDING)) {
            if (getBuffedValue(MapleBuffStat.SOARING) != null) {
                cancelEffectFromBuffStat(MapleBuffStat.SOARING);
            }
        }

        final boolean clonez = deregisterBuffStats(buffstats);
        if (effect.isMagicDoor()) {
            // remove for all on maps
            if (!getDoors().isEmpty()) {
                removeDoor();
                silentPartyUpdate();
            }
        } else if (effect.isMechDoor()) {
            if (!getMechDoors().isEmpty()) {
                removeMechDoor();
            }
        } else if (effect.isMonsterRiding_()) {
            getMount().cancelSchedule();
        } else if (effect.isMonsterRiding()) {
            cancelEffectFromBuffStat(MapleBuffStat.MECH_CHANGE);
        } else if (effect.isAranCombo()) {
            combo = 0;
        }// else if (effect.isForce()) {
        //    force = 0;
        //}
        // check if we are still logged in o.o
        cancelPlayerBuffs(buffstats, overwrite);
        if (!overwrite) {
            if (effect.isHide() && client.getChannelServer().getPlayerStorage().getCharacterById(this.getId()) != null) { //Wow this is so fking hacky...
                map.broadcastMessage(this, MaplePacketCreator.spawnPlayerMapobject(this), false);

                for (final MaplePet pet : pets) {
                    if (pet.getSummoned()) {
                        map.broadcastMessage(this, PetPacket.showPet(this, pet, false, false), false);
                    }
                }
                for (final WeakReference<MapleCharacter> chr : clones) {
                    if (chr.get() != null) {
                        map.broadcastMessage(chr.get(), MaplePacketCreator.spawnPlayerMapobject(chr.get()), false);
                    }
                }
            }
        }
        if (effect.getSourceId() == 35121013 && !overwrite) { //when siege 2 deactivates, missile re-activates
            SkillFactory.getSkill(35121005).getEffect(getTotalSkillLevel(35121005)).applyTo(this);
        }
        if (!clonez) {
            for (WeakReference<MapleCharacter> chr : clones) {
                if (chr.get() != null) {
                    chr.get().cancelEffect(effect, overwrite, startTime);
                }
            }
        }
        //System.out.println("Effect deregistered. Effect: " + effect.getSourceId());
    }

    public void cancelBuffStats(MapleBuffStat... stat) {
        List<MapleBuffStat> buffStatList = Arrays.asList(stat);
        deregisterBuffStats(buffStatList);
        cancelPlayerBuffs(buffStatList, false);
    }

    public void cancelEffectFromBuffStat(MapleBuffStat stat) {
        if (effects.get(stat) != null) {
            cancelEffect(effects.get(stat).effect, false, -1);
        }
    }

    public void cancelEffectFromBuffStat(MapleBuffStat stat, int from) {
        if (effects.get(stat) != null && effects.get(stat).cid == from) {
            cancelEffect(effects.get(stat).effect, false, -1);
        }
    }

    private void cancelPlayerBuffs(List<MapleBuffStat> buffstats, boolean overwrite) {
        boolean write = client != null && client.getChannelServer() != null && client.getChannelServer().getPlayerStorage().getCharacterById(getId()) != null;
        if (buffstats.contains(MapleBuffStat.HOMING_BEACON)) {
            client.getSession().write(MaplePacketCreator.cancelHoming());
        } else {
            if (overwrite) {
                List<MapleBuffStat> z = new ArrayList<MapleBuffStat>();
                for (MapleBuffStat s : buffstats) {
                    if (s.canStack()) {
                        z.add(s);
                    }
                }
                if (z.size() > 0) {
                    buffstats = z;
                } else {
                    return; //don't write anything
                }
            } else if (write) {
                stats.recalcLocalStats(this);
            }
            client.getSession().write(MaplePacketCreator.cancelBuff(buffstats));
            map.broadcastMessage(this, MaplePacketCreator.cancelForeignBuff(getId(), buffstats), false);
        }
    }

    public void dispel() {
        if (!isHidden()) {
            final LinkedList<MapleBuffStatValueHolder> allBuffs = new LinkedList<MapleBuffStatValueHolder>(effects.values());
            for (MapleBuffStatValueHolder mbsvh : allBuffs) {
                if (mbsvh.effect.isSkill() && mbsvh.schedule != null && !mbsvh.effect.isMorph() && !mbsvh.effect.isGmBuff() && !mbsvh.effect.isMonsterRiding() && !mbsvh.effect.isMechChange() && !mbsvh.effect.isEnergyCharge() && !mbsvh.effect.isAranCombo()) {
                    cancelEffect(mbsvh.effect, false, mbsvh.startTime);
                }
            }
        }
    }

    public void dispelSkill(int skillid) {
        final LinkedList<MapleBuffStatValueHolder> allBuffs = new LinkedList<MapleBuffStatValueHolder>(effects.values());

        for (MapleBuffStatValueHolder mbsvh : allBuffs) {
            if (mbsvh.effect.isSkill() && mbsvh.effect.getSourceId() == skillid) {
                cancelEffect(mbsvh.effect, false, mbsvh.startTime);
                break;
            }
        }
    }

    public void dispelSummons() {
        final LinkedList<MapleBuffStatValueHolder> allBuffs = new LinkedList<MapleBuffStatValueHolder>(effects.values());

        for (MapleBuffStatValueHolder mbsvh : allBuffs) {
            if (mbsvh.effect.getSummonMovementType() != null) {
                cancelEffect(mbsvh.effect, false, mbsvh.startTime);
            }
        }
    }

    public void dispelBuff(int skillid) {
        final LinkedList<MapleBuffStatValueHolder> allBuffs = new LinkedList<MapleBuffStatValueHolder>(effects.values());

        for (MapleBuffStatValueHolder mbsvh : allBuffs) {
            if (mbsvh.effect.getSourceId() == skillid) {
                cancelEffect(mbsvh.effect, false, mbsvh.startTime);
                break;
            }
        }
    }

    public void cancelAllBuffs_() {
        effects.clear();
    }

    public void cancelAllSkillID() {
        skillID.clear();
    }

    public void cancelAllBuffs() {
        final LinkedList<MapleBuffStatValueHolder> allBuffs = new LinkedList<MapleBuffStatValueHolder>(effects.values());

        for (MapleBuffStatValueHolder mbsvh : allBuffs) {
            cancelEffect(mbsvh.effect, false, mbsvh.startTime);
        }
    }

    public void cancelMorphs() {
        final LinkedList<MapleBuffStatValueHolder> allBuffs = new LinkedList<MapleBuffStatValueHolder>(effects.values());

        for (MapleBuffStatValueHolder mbsvh : allBuffs) {
            switch (mbsvh.effect.getSourceId()) {
                case 5111005:
                case 5121003:
                case 15111002:
                case 13111005:
                    return; // Since we can't have more than 1, save up on loops
                default:
                    if (mbsvh.effect.isMorph()) {
                        cancelEffect(mbsvh.effect, false, mbsvh.startTime);
                        continue;
                    }
            }
        }
    }

    public int getMorphState() {
        LinkedList<MapleBuffStatValueHolder> allBuffs = new LinkedList<MapleBuffStatValueHolder>(effects.values());
        for (MapleBuffStatValueHolder mbsvh : allBuffs) {
            if (mbsvh.effect.isMorph()) {
                return mbsvh.effect.getSourceId();
            }
        }
        return -1;
    }

    public void silentGiveBuffs(List<PlayerBuffValueHolder> buffs) {
        if (buffs == null) {
            return;
        }
        for (PlayerBuffValueHolder mbsvh : buffs) {
            mbsvh.effect.silentApplyBuff(this, mbsvh.startTime, mbsvh.localDuration, mbsvh.statup, mbsvh.cid);
        }
    }

    public List<PlayerBuffValueHolder> getAllBuffs() {
        final List<PlayerBuffValueHolder> ret = new ArrayList<PlayerBuffValueHolder>();
        final Map<Pair<Integer, Byte>, Integer> alreadyDone = new HashMap<Pair<Integer, Byte>, Integer>();
        final LinkedList<Entry<MapleBuffStat, MapleBuffStatValueHolder>> allBuffs = new LinkedList<Entry<MapleBuffStat, MapleBuffStatValueHolder>>(effects.entrySet());
        for (Entry<MapleBuffStat, MapleBuffStatValueHolder> mbsvh : allBuffs) {
            final Pair<Integer, Byte> key = new Pair<Integer, Byte>(mbsvh.getValue().effect.getSourceId(), mbsvh.getValue().effect.getLevel());
            if (alreadyDone.containsKey(key)) {
                ret.get(alreadyDone.get(key)).statup.put(mbsvh.getKey(), mbsvh.getValue().value);
            } else {
                alreadyDone.put(key, ret.size());
                final EnumMap<MapleBuffStat, Integer> list = new EnumMap<MapleBuffStat, Integer>(MapleBuffStat.class);
                list.put(mbsvh.getKey(), mbsvh.getValue().value);
                ret.add(new PlayerBuffValueHolder(mbsvh.getValue().startTime, mbsvh.getValue().effect, list, mbsvh.getValue().localDuration, mbsvh.getValue().cid));
            }
        }
        return ret;
    }

    public void cancelMagicDoor() {
        final LinkedList<MapleBuffStatValueHolder> allBuffs = new LinkedList<MapleBuffStatValueHolder>(effects.values());

        for (MapleBuffStatValueHolder mbsvh : allBuffs) {
            if (mbsvh.effect.isMagicDoor()) {
                cancelEffect(mbsvh.effect, false, mbsvh.startTime);
                break;
            }
        }
    }

    public int getSkillLevel(int skillid) {
        return getSkillLevel(SkillFactory.getSkill(skillid));
    }

    public int getTotalSkillLevel(int skillid) {
        return getTotalSkillLevel(SkillFactory.getSkill(skillid));
    }

    public final void handleEnergyCharge(final int skillid, final int targets) {
        final Skill echskill = SkillFactory.getSkill(skillid);
        final int skilllevel = getTotalSkillLevel(echskill);
        if (skilllevel > 0) {
            final MapleStatEffect echeff = echskill.getEffect(skilllevel);
            if (targets > 0) {
                if (getBuffedValue(MapleBuffStat.ENERGY_CHARGE) == null) {
                    echeff.applyEnergyBuff(this, true); // Infinity time
                } else {
                    Integer energyLevel = getBuffedValue(MapleBuffStat.ENERGY_CHARGE);
                    //TODO: bar going down
                    if (energyLevel < 10000) {
                        energyLevel += (echeff.getX() * targets);

                        client.getSession().write(MaplePacketCreator.showOwnBuffEffect(skillid, 2, getLevel(), skilllevel));
                        map.broadcastMessage(this, MaplePacketCreator.showBuffeffect(id, skillid, 2, getLevel(), skilllevel), false);

                        if (energyLevel >= 10000) {
                            energyLevel = 10000;
                        }
                        client.getSession().write(MaplePacketCreator.giveEnergyChargeTest(energyLevel, echeff.getDuration() / 1000));
                        setBuffedValue(MapleBuffStat.ENERGY_CHARGE, Integer.valueOf(energyLevel));
                    } else if (energyLevel == 10000) {
                        echeff.applyEnergyBuff(this, false); // One with time
                        setBuffedValue(MapleBuffStat.ENERGY_CHARGE, Integer.valueOf(10001));
                    }
                }
            }
        }
    }

    public final void handleBattleshipHP(int damage) {
        if (damage < 0) {
            final MapleStatEffect effect = getStatForBuff(MapleBuffStat.MONSTER_RIDING);
            if (effect != null && effect.getSourceId() == 5221006) {
                battleshipHP += damage;
                client.getSession().write(MaplePacketCreator.skillCooldown(5221999, battleshipHP / 10));
                if (battleshipHP <= 0) {
                    battleshipHP = 0;
                    client.getSession().write(MaplePacketCreator.skillCooldown(5221006, effect.getCooldown()));
                    addCooldown(5221006, System.currentTimeMillis(), effect.getCooldown() * 1000);
                    cancelEffectFromBuffStat(MapleBuffStat.MONSTER_RIDING);
                }
            }
        }
    }

    public final void handleOrbgain() {
        int orbcount = getBuffedValue(MapleBuffStat.COMBO);
        Skill combo;
        Skill advcombo;

        switch (getJob()) {
            case 1110:
            case 1111:
            case 1112:
                combo = SkillFactory.getSkill(11111001);
                advcombo = SkillFactory.getSkill(11110005);
                break;
            default:
                combo = SkillFactory.getSkill(1111002);
                advcombo = SkillFactory.getSkill(1120003);
                break;
        }

        MapleStatEffect ceffect = null;
        int advComboSkillLevel = getTotalSkillLevel(advcombo);
        if (advComboSkillLevel > 0) {
            ceffect = advcombo.getEffect(advComboSkillLevel);
        } else if (getSkillLevel(combo) > 0) {
            ceffect = combo.getEffect(getTotalSkillLevel(combo));
        } else {
            return;
        }

        if (orbcount < ceffect.getX() + 1) {
            int neworbcount = orbcount + 1;
            if (advComboSkillLevel > 0 && ceffect.makeChanceResult()) {
                if (neworbcount < ceffect.getX() + 1) {
                    neworbcount++;
                }
            }
            EnumMap<MapleBuffStat, Integer> stat = new EnumMap<MapleBuffStat, Integer>(MapleBuffStat.class);
            stat.put(MapleBuffStat.COMBO, neworbcount);
            setBuffedValue(MapleBuffStat.COMBO, neworbcount);
            int duration = ceffect.getDuration();
            duration += (int) ((getBuffedStarttime(MapleBuffStat.COMBO) - System.currentTimeMillis()));

            client.getSession().write(MaplePacketCreator.giveBuff(combo.getId(), duration, stat, ceffect));
            map.broadcastMessage(this, MaplePacketCreator.giveForeignBuff(getId(), stat, ceffect), false);
        }
    }

    public void handleOrbconsume(int howmany) {
        Skill combo;

        switch (getJob()) {
            case 1110:
            case 1111:
            case 1112:
                combo = SkillFactory.getSkill(11111001);
                break;
            default:
                combo = SkillFactory.getSkill(1111002);
                break;
        }
        if (getSkillLevel(combo) <= 0) {
            return;
        }
        MapleStatEffect ceffect = getStatForBuff(MapleBuffStat.COMBO);
        if (ceffect == null) {
            return;
        }
        EnumMap<MapleBuffStat, Integer> stat = new EnumMap<MapleBuffStat, Integer>(MapleBuffStat.class);
        stat.put(MapleBuffStat.COMBO, Math.max(1, getBuffedValue(MapleBuffStat.COMBO) - howmany));
        setBuffedValue(MapleBuffStat.COMBO, Math.max(1, getBuffedValue(MapleBuffStat.COMBO) - howmany));
        int duration = ceffect.getDuration();
        duration += (int) ((getBuffedStarttime(MapleBuffStat.COMBO) - System.currentTimeMillis()));

        client.getSession().write(MaplePacketCreator.giveBuff(combo.getId(), duration, stat, ceffect));
        map.broadcastMessage(this, MaplePacketCreator.giveForeignBuff(getId(), stat, ceffect), false);
    }

    public void silentEnforceMaxHpMp() {
        stats.setMp(stats.getMp(), this);
        stats.setHp(stats.getHp(), true, this);
    }

    public void enforceMaxHpMp() {
        Map<MapleStat, Integer> statups = new EnumMap<MapleStat, Integer>(MapleStat.class);
        if (stats.getMp() > stats.getCurrentMaxMp()) {
            stats.setMp(stats.getMp(), this);
            statups.put(MapleStat.MP, Integer.valueOf(stats.getMp()));
        }
        if (stats.getHp() > stats.getCurrentMaxHp()) {
            stats.setHp(stats.getHp(), this);
            statups.put(MapleStat.HP, Integer.valueOf(stats.getHp()));
        }
        if (statups.size() > 0) {
            client.getSession().write(MaplePacketCreator.updatePlayerStats(statups, getJob()));
            marriage();
        }
    }

    public MapleMap getMap() {
        return map;
    }

    public MonsterBook getMonsterBook() {
        return monsterbook;
    }

    public void setMap(MapleMap newmap) {
        this.map = newmap;
    }

    public void setMap(int PmapId) {
        this.mapid = PmapId;
    }

    public int getMapId() {
        if (map != null) {
            return map.getId();
        }
        return mapid;
    }

    public byte getInitialSpawnpoint() {
        return initialSpawnPoint;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public final String getBlessOfFairyOrigin() {
        return this.BlessOfFairy_Origin;
    }

    public final String getBlessOfEmpressOrigin() {
        return this.BlessOfEmpress_Origin;
    }

    public final short getLevel() {
        return level;
    }

    public final int getFame() {
        return fame;
    }

    public final int getFallCounter() {
        return fallcounter;
    }

    public final MapleClient getClient() {
        return client;
    }

    public final void setClient(final MapleClient client) {
        this.client = client;
    }

    public int getExp() {
        return exp;
    }

    public short getRemainingAp() {
        return remainingAp;
    }

    public int getRemainingSp() {
        return remainingSp[GameConstants.getSkillBook(job)]; //default
    }

    public int getRemainingSp(final int skillbook) {
        return remainingSp[skillbook];
    }

    public int[] getRemainingSps() {
        return remainingSp;
    }

    public int getRemainingSpSize() {
        int ret = 0;
        for (int i = 0; i < remainingSp.length; i++) {
            if (remainingSp[i] > 0) {
                ret++;
            }
        }
        return ret;
    }

    public short getHpApUsed() {
        return hpApUsed;
    }

    public boolean isHidden() {
        return getBuffSource(MapleBuffStat.DARKSIGHT) / 1000000 == 9;
    }

    public void setHpApUsed(short hpApUsed) {
        this.hpApUsed = hpApUsed;
    }

    public byte getSkinColor() {
        return skinColor;
    }

    public void setSkinColor(byte skinColor) {
        this.skinColor = skinColor;
    }

    public short getJob() {
        return job;
    }

    public byte getGender() {
        return gender;
    }

    public int getHair() {
        return hair;
    }

    public int getFace() {
        return face;
    }

    public int getDemonMarking() {
        return demonMarking;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public void setHair(int hair) {
        this.hair = hair;
    }

    public void setFace(int face) {
        this.face = face;
    }

    public void setFame(int fame) {
        this.fame = fame;
    }

    public void setDemonMarking(int mark) {
        this.demonMarking = mark;
    }

    public void setFallCounter(int fallcounter) {
        this.fallcounter = fallcounter;
    }

    public Point getOldPosition() {
        return old;
    }

    public void setOldPosition(Point x) {
        this.old = x;
    }

    public void setRemainingAp(short remainingAp) {
        this.remainingAp = remainingAp;
    }

    public void setRemainingSp(int remainingSp) {
        this.remainingSp[GameConstants.getSkillBook(job)] = remainingSp; //default
    }

    public void setRemainingSp(int remainingSp, final int skillbook) {
        this.remainingSp[skillbook] = remainingSp;
    }

    public void setGender(byte gender) {
        this.gender = gender;
    }

    public void setInvincible(boolean invinc) {
        invincible = invinc;
    }

    public boolean isInvincible() {
        return invincible;
    }

    public CheatTracker getCheatTracker() {
        return anticheat;
    }

    public MapleLieDetector getAntiMacro() {
        return antiMacro;
    }

    public BuddyList getBuddylist() {
        return buddylist;
    }

    public void addFame(int famechange) {
        this.fame += famechange;
        getTrait(MapleTraitType.charm).addLocalExp(famechange);
    }

    public void updateFame() {
        updateSingleStat(MapleStat.FAME, this.fame);
    }

    public void changeMapBanish(final int mapid, final String portal, final String msg) {
        dropMessage(5, msg);
        final MapleMap map = client.getChannelServer().getMapFactory().getMap(mapid);
        changeMap(map, map.getPortal(portal));
    }

    public void changeMap(final MapleMap to, final Point pos) {
        changeMapInternal(to, pos, MaplePacketCreator.getWarpToMap(to, 0x80, this), null);
    }

    public void changeMap(final MapleMap to) {
        changeMapInternal(to, to.getPortal(0).getPosition(), MaplePacketCreator.getWarpToMap(to, 0, this), to.getPortal(0));
    }

    public void changeMap(final MapleMap to, final MaplePortal pto) {
        changeMapInternal(to, pto.getPosition(), MaplePacketCreator.getWarpToMap(to, pto.getId(), this), null);
    }

    public void changeMapPortal(final MapleMap to, final MaplePortal pto) {
        changeMapInternal(to, pto.getPosition(), MaplePacketCreator.getWarpToMap(to, pto.getId(), this), pto);
    }

    private void changeMapInternal(final MapleMap to, final Point pos, byte[] warpPacket, final MaplePortal pto) {
        if (to == null) {
            return;
        }
        if (getAntiMacro().inProgress()) {
            dropMessage(5, "被使用测谎仪时无法操作。");
            client.getSession().write(MaplePacketCreator.enableActions());
            marriage();
            return;
        }

        final int nowmapid = map.getId();
        if (eventInstance != null) {
            eventInstance.changedMap(this, to.getId());
        }
        final boolean pyramid = pyramidSubway != null;
        if (map.getId() == nowmapid) {
            client.getSession().write(warpPacket);
            final boolean shouldChange = !isClone() && client.getChannelServer().getPlayerStorage().getCharacterById(getId()) != null;
            final boolean shouldState = map.getId() == to.getId();
            if (shouldChange && shouldState) {
                to.setCheckStates(false);
            }
            map.removePlayer(this);
            if (shouldChange) {
                map = to;
                setPosition(pos);
                to.addPlayer(this);
                stats.relocHeal(this);
                if (shouldState) {
                    to.setCheckStates(true);
                }
            }
        }
        if (pyramid && pyramidSubway != null) { //checks if they had pyramid before AND after changing
            pyramidSubway.onChangeMap(this, to.getId());
        }
    }

    public void cancelChallenge() {
        if (challenge != 0 && client.getChannelServer() != null) {
            final MapleCharacter chr = client.getChannelServer().getPlayerStorage().getCharacterById(challenge);
            if (chr != null) {
                chr.dropMessage(6, getName() + " has denied your request.");
                chr.setChallenge(0);
            }
            dropMessage(6, "Denied the challenge.");
            challenge = 0;
        }
    }

    public void leaveMap(MapleMap map) {
        if (isReincarnation()) {
            setReincarnation(false);
            map.killMonster(9801002);
        }

        //controlledLock.writeLock().lock();
        //visibleMapObjectsLock.writeLock().lock();
        try {
            try {
                for (MapleMonster mons : controlled) {
                    if (mons != null) {
                        mons.setController(null);
                        mons.setControllerHasAggro(false);
                        mons.setControllerKnowsAboutAggro(false);
                        map.updateMonsterController(mons);
                    }
                }
                controlled.clear();
                visibleMapObjects.clear();
            } catch (Exception ex) {
                System.err.println("leaveMap失敗" + ex);
                FileoutputUtil.outputFileError("logs/leaveMap失敗.txt", ex);
            }
        } finally {
            //controlledLock.writeLock().unlock();
            //visibleMapObjectsLock.writeLock().unlock();
        }
        if (chair != 0) {
            chair = 0;
        }
        clearLinkMid();
        cancelFishingTask();
        cancelChallenge();
        if (!getMechDoors().isEmpty()) {
            removeMechDoor();
        }
        cancelMapTimeLimitTask();
        if (getTrade() != null) {
            MapleTrade.cancelTrade(getTrade(), client, this);
        }
    }

    public void changeJob(int newJob) {
        try {
            cancelEffectFromBuffStat(MapleBuffStat.SHADOWPARTNER);
            this.job = (short) newJob;
            updateSingleStat(MapleStat.JOB, newJob);
            if (!GameConstants.isBeginnerJob(newJob)) {
                if (GameConstants.isEvan(newJob) || GameConstants.isResist(newJob) || GameConstants.isMercedes(newJob)) {
                    int changeSp = (newJob == 2200 || newJob == 2210 || newJob == 2211 || newJob == 2213 ? 3 : 5);
                    if (GameConstants.isResist(job) && newJob != 3100 && newJob != 3200 && newJob != 3300 && newJob != 3500) {
                        changeSp = 3;
                    }
                    remainingSp[GameConstants.getSkillBook(newJob)] += changeSp;
                    client.getSession().write(UIPacket.getSPMsg((byte) changeSp, (short) newJob));
                } else {
                    remainingSp[GameConstants.getSkillBook(newJob)]++;
                    if (newJob % 10 >= 2) {
                        remainingSp[GameConstants.getSkillBook(newJob)] += 2;
                    }
                }

                if (newJob == 2210) {//修復少兩点技能点的問題
                    remainingSp[GameConstants.getSkillBook(newJob)] += 2;
                }
                if (newJob == 2211) {//修復少兩点技能点的問題
                    remainingSp[GameConstants.getSkillBook(newJob)] += 2;
                }

                if (newJob % 10 >= 1 && level >= 70) { //3rd job or higher. lucky for evans who get 80, 100, 120, 160 ap...
                    remainingAp += 5;
                    updateSingleStat(MapleStat.AVAILABLEAP, remainingAp);
                    //MAKER STILL EXISTS
                    final Skill skil = SkillFactory.getSkill(stats.getSkillByJob(1007, getJob()));
                    if (skil != null && getSkillLevel(skil) <= 0) {
                        //dropMessage(-1, "You have gained the Maker skill.");
                        changeSkillLevel(skil, skil.getMaxLevel(), (byte) skil.getMaxLevel());
                    }
                }
                if (!isGM()) {
                    resetStatsByJob(true);
                    if (!GameConstants.isEvan(newJob)) {
                        if (getLevel() > (newJob == 200 ? 8 : 10) && newJob % 100 == 0 && (newJob % 1000) / 100 > 0) { //first job
                            remainingSp[GameConstants.getSkillBook(newJob)] += 3 * (getLevel() - (newJob == 200 ? 8 : 10));
                        }
                    } else if (newJob == 2200) {
                        MapleQuest.getInstance(22100).forceStart(this, 0, null);
                        MapleQuest.getInstance(22100).forceComplete(this, 0);
                        expandInventory((byte) 1, 4);
                        expandInventory((byte) 2, 4);
                        expandInventory((byte) 3, 4);
                        expandInventory((byte) 4, 4);
                        client.getSession().write(MaplePacketCreator.getEvanTutorial("UI/tutorial/evan/14/0"));
                        //dropMessage(5, "The baby Dragon hatched and appears to have something to tell you. Click the baby Dragon to start a conversation.");
                    }
                }
                client.getSession().write(MaplePacketCreator.updateSp(this, false, false));
                marriage();
            }

            int maxhp = stats.getMaxHp(), maxmp = stats.getMaxMp();
            if (newJob == 2112) {
                final Skill skilsss = SkillFactory.getSkill(21121000);
                changeSkillLevel(skilsss, 0, (byte) 10);
            }
            switch (job) {
                case 100: // Warrior
                case 1100: // Soul Master
                case 2100: // Aran
                case 3200:
                    maxhp += Randomizer.rand(200, 250);
                    break;
                case 3100:
                    maxhp += Randomizer.rand(200, 250);
                    maxmp = 30;
                    break;
                case 3110:
                    maxhp += Randomizer.rand(300, 350);
                    maxmp = 50;
                    break;
                case 3111:
                    maxmp = 100;
                    break;
                case 3112:
                    maxmp = 120;
                    break;
                case 200: // Magician
                case 2200: //evan
                case 2210: //evan
                    maxmp += Randomizer.rand(100, 150);
                    break;
                case 300: // Bowman
                case 400: // Thief
                case 500: // Pirate
                case 2300:
                case 3300:
                case 3500:
                    maxhp += Randomizer.rand(100, 150);
                    maxmp += Randomizer.rand(25, 50);
                    break;
                case 110: // Fighter
                case 120: // Page
                case 130: // Spearman
                case 1110: // Soul Master
                case 2110: // Aran
                case 3210:
                    maxhp += Randomizer.rand(300, 350);
                    break;
                case 210: // FP
                case 220: // IL
                case 230: // Cleric
                    maxmp += Randomizer.rand(400, 450);
                    break;
                case 310: // Bowman
                case 320: // Crossbowman
                case 410: // Assasin
                case 420: // Bandit
                case 430: // Semi Dualer
                case 510:
                case 520:
                case 530:
                case 2310:
                case 1310: // Wind Breaker
                case 1410: // Night Walker
                case 3310:
                case 3510:
                    maxhp += Randomizer.rand(200, 250);
                    maxhp += Randomizer.rand(150, 200);
                    break;
                case 900: // GM
                case 800: // Manager
                    maxhp += 99999;
                    maxmp += 99999;
                    break;
            }
            if (maxhp >= 99999) {
                maxhp = 99999;
            }
            if (maxmp >= 99999) {
                maxmp = 99999;
            }
            stats.setInfo(maxhp, maxmp, maxhp, maxmp);
            Map<MapleStat, Integer> statup = new EnumMap<MapleStat, Integer>(MapleStat.class);
            statup.put(MapleStat.MAXHP, Integer.valueOf(maxhp));
            statup.put(MapleStat.MAXMP, Integer.valueOf(maxmp));
            statup.put(MapleStat.HP, Integer.valueOf(maxhp));
            statup.put(MapleStat.MP, Integer.valueOf(maxmp));
            stats.recalcLocalStats(this);
            client.getSession().write(MaplePacketCreator.updatePlayerStats(statup, getJob()));
            marriage();
            map.broadcastMessage(this, MaplePacketCreator.showForeignEffect(getId(), 10), false);
            silentPartyUpdate();
            guildUpdate();
            familyUpdate();
            sidekickUpdate();
            doDemon();
            if (dragon != null) {
                map.broadcastMessage(MaplePacketCreator.removeDragon(this.id));
                dragon = null;
            }
            baseSkills();
            if (newJob >= 2200 && newJob <= 2218) { //make new
                if (getBuffedValue(MapleBuffStat.MONSTER_RIDING) != null) {
                    cancelBuffStats(MapleBuffStat.MONSTER_RIDING);
                }
                makeDragon();
            }
        } catch (Exception e) {
            FileoutputUtil.outputFileError(FileoutputUtil.ScriptEx_Log, e); //all jobs throw errors :(
        }
    }

    public void baseSkills() {
        Map<Skill, SkillEntry> list = new HashMap<>();
        if (GameConstants.getJobNumber(job) >= 3) { //third job.
            List<Integer> skills = SkillFactory.getSkillsByJob(job);
            if (skills != null) {
                for (int i : skills) {
                    final Skill skil = SkillFactory.getSkill(i);
                    if (skil != null && !skil.isInvisible() && skil.isFourthJob() && getSkillLevel(skil) <= 0 && getMasterLevel(skil) <= 0 && skil.getMasterLevel() > 0) {
                        changeSkillLevel(skil, (byte) 0, (byte) skil.getMasterLevel()); //usually 10 master
                    } else if (skil != null && skil.getName() != null && skil.getName().contains("Maple Warrior") && getSkillLevel(skil) <= 0 && getMasterLevel(skil) <= 0) {
                        changeSkillLevel(skil, (byte) 0, (byte) 10); //hackish
                    }
                }
            }
        }
        Skill skil;
        /*if (job == 3000 || job == 3200 || job == 3212 || job == 3210) { // whatever as long it bam
            final int[] ss = {32120009};
            for (int i : ss) {
                skil = SkillFactory.getSkill(i);
                if (skil != null) {
                    if (getSkillLevel(skil) <= 0) { // no total
                        changeSkillLevel(skil, (byte) 0, (byte) 10);
                    }
                }
            }
        }*/
        if (GameConstants.isDualBlade(job)) {
            final int[] ss = {4311003, 4331005, 4331002, 4321000, 4341004};
            for (int i : ss) {
                skil = SkillFactory.getSkill(i);
                if (skil != null) {
                    if (getSkillLevel(skil) <= 0) { // no total
                        changeSkillLevel(skil, (byte) 0, (byte) 10);
                    }
                }
            }
        } // based on official server, it should only have 20020109, 20021110, 20020111 & 20020112
        if (GameConstants.isMercedes(job)) {
//          final int[] ss = {20021000, 20021001, 20020002, 20020022, 20020109, 20021110, 20020111, 20020112};
            final int[] ss = {20020109, 20021110, 20020111, 20020112};
            for (int i : ss) {
                skil = SkillFactory.getSkill(i);
                if (skil != null) {
                    if (getSkillLevel(skil) <= 0) { // no total
                        changeSkillLevel(skil, (byte) 1, (byte) 1);
                    }
                }
            }
            skil = SkillFactory.getSkill(20021181);
            if (skil != null) {
                if (getSkillLevel(skil) <= 0) { // no total
                    changeSkillLevel(skil, (byte) 1, (byte) 1);
                }
            }
            skil = SkillFactory.getSkill(20021166);
            if (skil != null) {
                if (getSkillLevel(skil) <= 0) { // no total
                    changeSkillLevel(skil, (byte) 1, (byte) 1);
                }
            }
        }
        if (GameConstants.isDemon(job)) {
            final int[] ss1 = {30011000, 30011001, 30010002, 30010185, 30010112, 30010111, 30010110, 30010022, 30011109};
            for (int i : ss1) {
                skil = SkillFactory.getSkill(i);
                if (skil != null) {
                    if (getSkillLevel(skil) <= 0) { // no total
                        changeSkillLevel(skil, (byte) 1, (byte) 1);
                    }
                }
            }
            final int[] ss2 = {30011170, 30011169, 30011168, 30011167, 30010166, 30010184, 30010183, 30010186};
            for (int i : ss2) {
                skil = SkillFactory.getSkill(i);
                if (skil != null) {
                    if (getSkillLevel(skil) <= 0) { // no total
                        changeSkillLevel(skil, (byte) 1, (byte) 1);
                    }
                }
            }
        }
    }

    public void makeDragon() {
        dragon = new MapleDragon(this);
        map.broadcastMessage(MaplePacketCreator.spawnDragon(dragon));
    }

    public MapleDragon getDragon() {
        return dragon;
    }

    public void gainAp(short ap) {
        this.remainingAp += ap;
        updateSingleStat(MapleStat.AVAILABLEAP, this.remainingAp);
    }

    public void gainSP(int sp) {
        this.remainingSp[GameConstants.getSkillBook(job)] += sp; //default
        client.getSession().write(MaplePacketCreator.updateSp(this, false));
        client.getSession().write(UIPacket.getSPMsg((byte) sp, (short) job));
        marriage();

    }

    public void gainSP(int sp, final int skillbook) {
        this.remainingSp[skillbook] += sp; //default
        client.getSession().write(MaplePacketCreator.updateSp(this, false));
        client.getSession().write(UIPacket.getSPMsg((byte) sp, (short) 0));
        marriage();
    }

    public void resetSP(int sp) {
        for (int i = 0; i < remainingSp.length; i++) {
            this.remainingSp[i] = sp;
        }
        client.getSession().write(MaplePacketCreator.updateSp(this, false));
        marriage();
    }

    public void resetAPSP() {
        resetSP(0);
        gainAp((short) -this.remainingAp);
    }

    public List<Integer> getProfessions() {
        List<Integer> prof = new ArrayList<Integer>();
        for (int i = 9200; i <= 9204; i++) {
            if (getProfessionLevel(i * 10000) > 0) {
                prof.add(i);
            }
        }
        return prof;
    }

    public byte getProfessionLevel(int id) {
        int ret = getSkillLevel(id);
        if (ret <= 0) {
            return 0;
        }
        return (byte) ((ret >>> 24) & 0xFF); //the last byte
    }

    public short getProfessionExp(int id) {
        int ret = getSkillLevel(id);
        if (ret <= 0) {
            return 0;
        }
        return (short) (ret & 0xFFFF); //the first two byte
    }

    public boolean addProfessionExp(int id, int expGain) {
        int ret = getProfessionLevel(id);
        if (ret <= 0 || ret >= 10) {
            return false;
        }
        int newExp = getProfessionExp(id) + expGain;
        if (newExp >= GameConstants.getProfessionEXP(ret)) {
            //gain level
            changeProfessionLevelExp(id, ret + 1, newExp - GameConstants.getProfessionEXP(ret));
            int traitGain = (int) Math.pow(2, ret + 1);
            switch (id) {
                case 92000000:
                    traits.get(MapleTraitType.sense).addExp(traitGain, this);
                    break;
                case 92010000:
                    traits.get(MapleTraitType.will).addExp(traitGain, this);
                    break;
                case 92020000:
                case 92030000:
                case 92040000:
                    traits.get(MapleTraitType.craft).addExp(traitGain, this);
                    break;
            }
            return true;
        } else {
            changeProfessionLevelExp(id, ret, newExp);
            return false;
        }
    }

    public void changeProfessionLevelExp(int id, int level, int exp) {
        changeSkillLevel(SkillFactory.getSkill(id), ((level & 0xFF) << 24) + (exp & 0xFFFF), (byte) 10);
    }

    public void changeSkillLevel(final Skill skill, int newLevel, byte newMasterlevel) { //1 month
        if (skill == null) {
            return;
        }
        changeSkillLevel(skill, newLevel, newMasterlevel, skill.isTimeLimited() ? (System.currentTimeMillis() + (long) (30L * 24L * 60L * 60L * 1000L)) : -1);
    }

    public void changeSkillLevel(final Skill skill, int newLevel, byte newMasterlevel, long expiration) {
        if (skill == null || (!GameConstants.isApplicableSkill(skill.getId()) && !GameConstants.isApplicableSkill_(skill.getId()))) {
            return;
        }
        client.getSession().write(MaplePacketCreator.updateSkill(skill.getId(), newLevel, newMasterlevel, expiration));
        if (newLevel == 0 && newMasterlevel == 0) {
            if (skills.containsKey(skill)) {
                skills.remove(skill);
            } else {
                return; //nothing happen
            }
        } else {
            skills.put(skill, new SkillEntry(newLevel, newMasterlevel, expiration));
        }
        changed_skills = true;
        if (GameConstants.isRecoveryIncSkill(skill.getId())) {
            stats.relocHeal(this);
        }
        if (skill.getId() < 80000000) {
            stats.recalcLocalStats(this);
        }
    }

    public void changeSkillLevel_Skip(final Skill skill, int newLevel, byte newMasterlevel) {
        changeSkillLevel_Skip(skill, newLevel, newMasterlevel, false);
    }

    public void changeSkillLevel_Skip(final Skill skill, int newLevel, byte newMasterlevel, boolean write) {
        if (skill == null) {
            return;
        }
        if (write) {
            client.getSession().write(MaplePacketCreator.updateSkill(skill.getId(), newLevel, newMasterlevel, -1L));
        }
        if (newLevel == 0 && newMasterlevel == 0) {
            if (skills.containsKey(skill)) {
                skills.remove(skill);
            } else {
                return; //nothing happen
            }
        } else {
            skills.put(skill, new SkillEntry(newLevel, newMasterlevel, -1L));
        }

    }

    public void playerDead() {
        final MapleStatEffect statss = getStatForBuff(MapleBuffStat.SOUL_STONE);
        if (statss != null) {
            dropMessage(5, "你已经透过灵魂之石復活了。");
            getStat().setHp(((getStat().getMaxHp() / 100) * statss.getX()), this);
            setStance(0);
            isSquadPlayerID();
            changeMap(getMap(), getMap().getPortal(0));
            return;
        }
        if (getEventInstance() != null) {
            getClient().getSession().write(MaplePacketCreator.enableActions());
            marriage();
            getEventInstance().playerKilled(this);
            getClient().getSession().write(MaplePacketCreator.enableActions());
            marriage();
        }
        cancelEffectFromBuffStat(MapleBuffStat.SHADOWPARTNER);
        cancelEffectFromBuffStat(MapleBuffStat.MORPH);
        cancelEffectFromBuffStat(MapleBuffStat.SOARING);
        cancelEffectFromBuffStat(MapleBuffStat.MONSTER_RIDING);
        cancelEffectFromBuffStat(MapleBuffStat.MECH_CHANGE);
        cancelEffectFromBuffStat(MapleBuffStat.RECOVERY);
        cancelEffectFromBuffStat(MapleBuffStat.HP_BOOST);
        cancelEffectFromBuffStat(MapleBuffStat.MP_BOOST);
        cancelEffectFromBuffStat(MapleBuffStat.ENHANCED_MAXHP);
        cancelEffectFromBuffStat(MapleBuffStat.ENHANCED_MAXMP);
        cancelEffectFromBuffStat(MapleBuffStat.MAXHP);
        cancelEffectFromBuffStat(MapleBuffStat.MAXMP);
        dispelSummons();
        checkFollow();
        dotHP = 0;
        lastDOTTime = 0;
        if (!GameConstants.isBeginnerJob(job) && !inPVP()) {
            int charms = getItemQuantity(5130000, false);
            if (charms > 0) {
                MapleInventoryManipulator.removeById(client, MapleInventoryType.CASH, 5130000, 1, true, false);

                charms--;
                if (charms > 0xFF) {
                    charms = 0xFF;
                }
                client.getSession().write(CSPacket.useCharm((byte) charms, (byte) 0));
            } else {
                float diepercentage = 0.0f;
                int expforlevel = getNeededExp();
                if (map.isTown() || FieldLimitType.RegularExpLoss.check(map.getFieldLimit())) {
                    diepercentage = 0.01f;
                } else {
                    diepercentage = (float) (0.1f - ((traits.get(MapleTraitType.charisma).getLevel() / 20) / 100f));
                }
                int v10 = (int) (exp - (long) ((double) expforlevel * diepercentage));
                if (v10 < 0) {
                    v10 = 0;
                }
                this.exp = v10;
            }
            this.updateSingleStat(MapleStat.EXP, this.exp);
        }
        if (!stats.checkEquipDurabilitys(this, -100)) { //i guess this is how it works ?
            dropMessage(5, "An item has run out of durability but has no inventory room to go to.");
        } //lol
        if (pyramidSubway != null) {
            stats.setHp((short) 50, this);
            pyramidSubway.fail(this);
        }
    }

    public void updatePartyMemberHP() {
        if (party != null && client.getChannelServer() != null) {
            final int channel = client.getChannel();
            for (MaplePartyCharacter partychar : party.getMembers()) {
                if (partychar != null && partychar.getMapid() == getMapId() && partychar.getChannel() == channel) {
                    final MapleCharacter other = client.getChannelServer().getPlayerStorage().getCharacterByName(partychar.getName());
                    if (other != null) {
                        other.getClient().getSession().write(MaplePacketCreator.updatePartyMemberHP(getId(), stats.getHp(), stats.getCurrentMaxHp()));
                    }
                }
            }
        }
    }

    public void receivePartyMemberHP() {
        if (party == null) {
            return;
        }
        int channel = client.getChannel();
        for (MaplePartyCharacter partychar : party.getMembers()) {
            if (partychar != null && partychar.getMapid() == getMapId() && partychar.getChannel() == channel) {
                MapleCharacter other = client.getChannelServer().getPlayerStorage().getCharacterByName(partychar.getName());
                if (other != null) {
                    client.getSession().write(MaplePacketCreator.updatePartyMemberHP(other.getId(), other.getStat().getHp(), other.getStat().getCurrentMaxHp()));
                }
            }
        }
    }

    public void healHP(int delta) {
        addHP(delta);
        client.getSession().write(MaplePacketCreator.showOwnHpHealed(delta));
        getMap().broadcastMessage(this, MaplePacketCreator.showHpHealed(getId(), delta), false);
    }

    public void healMP(int delta) {
        addMP(delta);
        client.getSession().write(MaplePacketCreator.showOwnHpHealed(delta));
        getMap().broadcastMessage(this, MaplePacketCreator.showHpHealed(getId(), delta), false);
    }

    /**
     * Convenience function which adds the supplied parameter to the current hp
     * then directly does a updateSingleStat.
     *
     * @see MapleCharacter#setHp(int)
     * @param delta
     */
    public void addHP(int delta) {
        if (stats.setHp(stats.getHp() + delta, this)) {
            updateSingleStat(MapleStat.HP, stats.getHp());
        }
    }

    /**
     * Convenience function which adds the supplied parameter to the current mp
     * then directly does a updateSingleStat.
     *
     * @see MapleCharacter#setMp(int)
     * @param delta
     */
    public void addMP(int delta) {
        if (stats.setMp(stats.getMp() + delta, this)) {
            updateSingleStat(MapleStat.MP, stats.getMp());
        }
    }

    public void addMPHP(int hpDiff, int mpDiff) {
        Map<MapleStat, Integer> statups = new EnumMap<MapleStat, Integer>(MapleStat.class);

        if (stats.setHp(stats.getHp() + hpDiff, this)) {
            statups.put(MapleStat.HP, Integer.valueOf(stats.getHp()));
        }
        if (stats.setMp(stats.getMp() + mpDiff, this)) {
            statups.put(MapleStat.MP, Integer.valueOf(stats.getMp()));
        }
        if (statups.size() > 0) {
            client.getSession().write(MaplePacketCreator.updatePlayerStats(statups, getJob()));
            marriage();
        }
    }

    public void updateSingleStat(MapleStat stat, int newval) {
        updateSingleStat(stat, newval, false);
    }

    /**
     * Updates a single stat of this MapleCharacter for the client. This method
     * only creates and sends an update packet, it does not update the stat
     * stored in this MapleCharacter instance.
     *
     * @param stat
     * @param newval
     * @param itemReaction
     */
    public void updateSingleStat(MapleStat stat, int newval, boolean itemReaction) {
        if (stat == MapleStat.AVAILABLESP) {
            client.getSession().write(MaplePacketCreator.updateSp(this, itemReaction, false));
            marriage();
            return;
        }
        Map<MapleStat, Integer> statup = new EnumMap<MapleStat, Integer>(MapleStat.class);
        statup.put(stat, newval);
        client.getSession().write(MaplePacketCreator.updatePlayerStats(statup, itemReaction, getJob()));
        marriage();
    }

    public void gainExp(final int total, final boolean show, final boolean inChat, final boolean white) {
        try {
            int prevexp = getExp();
            int needed = getNeededExp();
            if (total > 0) {
                stats.checkEquipLevels(this, total); //gms like
            }

            if ((level >= LoginServer.getMaxLevel() || (GameConstants.isKOC(job) && level >= LoginServer.getKocMaxLevel()))) {
                setExp(0);
            } else {
                boolean leveled = false;
                long tot = exp + total;
                if (tot >= needed) {
                    exp += total;
                    levelUp();
                    leveled = true;
                    if ((level >= LoginServer.getMaxLevel() || (GameConstants.isKOC(job) && level >= LoginServer.getKocMaxLevel()))) {
                        setExp(0);
                    } else {
                        needed = getNeededExp();
                        if (exp >= needed) {
                            setExp(needed - 1);
                        }
                    }
                } else {
                    exp += total;
                }
                if (total > 0) {
                    familyRep(prevexp, needed, leveled);
                }
            }
            if (total != 0) {
                if (exp < 0) { // After adding, and negative
                    if (total > 0) {
                        setExp(needed);
                    } else if (total < 0) {
                        setExp(0);
                    }
                }
                updateSingleStat(MapleStat.EXP, getExp());
                if (show) { // still show the expgain even if it's not there
                    client.getSession().write(MaplePacketCreator.GainEXP_Others(total, inChat, white));
                }
            }
        } catch (Exception e) {
            FileoutputUtil.outputFileError(FileoutputUtil.ScriptEx_Log, e); //all jobs throw errors :(
        }
    }

    public void familyRep(int prevexp, int needed, boolean leveled) {
        if (mfc != null) {
            int onepercent = needed / 100;
            if (onepercent <= 0) {
                return;
            }
            int percentrep = (getExp() / onepercent - prevexp / onepercent);
            if (leveled) {
                percentrep = 100 - percentrep + (level / 2);
            }
            if (percentrep > 0) {
                int sensen = World.Family.setRep(mfc.getFamilyId(), mfc.getSeniorId(), percentrep * 10, level, name);
                if (sensen > 0) {
                    World.Family.setRep(mfc.getFamilyId(), sensen, percentrep * 5, level, name); //and we stop here
                }
            }
        }
    }
    


    public void gainExpMonster(final int gain, final boolean show, final boolean white, final byte pty, int Class_Bonus_EXP, int Equipment_Bonus_EXP, int Premium_Bonus_EXP, boolean partyBonusMob, final int partyBonusRate) {
        int Sidekick_Bonus_EXP = 0;
        if (sidekick != null) {
            MapleCharacter side = map.getCharacterById(sidekick.getCharacter(sidekick.getCharacter(0).getId() == getId() ? 1 : 0).getId());
            if (side != null) {
                Sidekick_Bonus_EXP = (gain / 2);
            }
        }
        int Marriage_EXP = 0;
        if (getMarriageId() > 0) {
            Marriage_EXP = (int) Math.ceil(gain * 0.15D);
        }

        int total = gain + Class_Bonus_EXP + Equipment_Bonus_EXP + Premium_Bonus_EXP + Sidekick_Bonus_EXP + Marriage_EXP;
        int partyinc = 0;
        int prevexp = getExp();
        
        boolean merch = World.hasMerchant(getAccountID(),getCharacterId());//检测店铺是否开启
		if (merch) {
			Premium_Bonus_EXP = (int) (gain / 100.0D * 10.0D);//开店经验
			total += Premium_Bonus_EXP;
		}
        if (pty > 1) {
            final double rate = (partyBonusRate > 0 ? (partyBonusRate / 100.0) : (map == null || !partyBonusMob || map.getPartyBonusRate() <= 0 ? 0.05 : (map.getPartyBonusRate() / 100.0)));
            partyinc = (int) (((float) (gain * rate)) * (pty + (rate > 0.05 ? -1 : 1)));
            total += partyinc;
        }
        
    //    if(this.level >= 1 && this.level < 70){//分段经验
	//		Premium_Bonus_EXP = (int)(gain * 2);//阶段经验
      //      total += Premium_Bonus_EXP;
	//	}
        
    //    if(this.level >= 70 && this.level < 120){//分段经验
	//		Premium_Bonus_EXP = (int)(gain * 1);//阶段经验
      //      total += Premium_Bonus_EXP;
	//	}

        if (gain > 0 && total < gain) { //just in case
            total = Integer.MAX_VALUE;
        }
        if (total > 0) {
            stats.checkEquipLevels(this, total); //gms like
        }
        int needed = getNeededExp();
        if ((level >= LoginServer.getMaxLevel() || (GameConstants.isKOC(job) && level >= LoginServer.getKocMaxLevel()))) {
            setExp(0);
        } else {
            boolean leveled = false;
            if (exp + total >= needed || exp >= needed) {
                exp += total;
                levelUp();
                if (ServerConstants.levelx2) {
                    if (getTotalWins() < 1) {
                        if (level > 150 && level < 200) {
                            levelUp();
                            levelUp();
                            setExp(0);
                        }
                    }
                }
                leveled = true;
                if ((level >= LoginServer.getMaxLevel() || (GameConstants.isKOC(job) && level >= LoginServer.getKocMaxLevel()))) {
                    setExp(0);
                } else {
                    needed = getNeededExp();
                    if (exp >= needed) {
                        setExp(needed);
                    }
                }
            } else {
                exp += total;
            }
            if (total > 0) {
                familyRep(prevexp, needed, leveled);
            }
        }
        if (gain != 0) {
            if (exp < 0) { // After adding, and negative
                if (gain > 0) {
                    setExp(getNeededExp());
                } else if (gain < 0) {
                    setExp(0);
                }
            }
            updateSingleStat(MapleStat.EXP, getExp());
            if (show) {
                client.getSession().write(MaplePacketCreator.GainEXP_Monster(gain, white, partyinc, Class_Bonus_EXP, Equipment_Bonus_EXP, Premium_Bonus_EXP, Sidekick_Bonus_EXP, Marriage_EXP));
            }
        }
    }

    public void forceReAddItem_NoUpdate(Item item, MapleInventoryType type) {
        getInventory(type).removeSlot(item.getPosition());
        getInventory(type).addFromDB(item);
    }

    public void forceReAddItem(Item item, MapleInventoryType type) { //used for stuff like durability, item exp/level, probably owner?
        forceReAddItem_NoUpdate(item, type);
        if (type != MapleInventoryType.UNDEFINED) {
            client.getSession().write(MaplePacketCreator.updateSpecialItemUse(item, type == MapleInventoryType.EQUIPPED ? (byte) 1 : type.getType(), this));
        }
        marriage();
    }

    public void forceReAddItem_Flag(Item item, MapleInventoryType type) { //used for flags
        forceReAddItem_NoUpdate(item, type);
        if (type != MapleInventoryType.UNDEFINED) {
            client.getSession().write(MaplePacketCreator.updateSpecialItemUse_(item, type == MapleInventoryType.EQUIPPED ? (byte) 1 : type.getType(), this));
        }
        marriage();
    }

    public void forceReAddItem_Book(Item item, MapleInventoryType type) { //used for mbook
        forceReAddItem_NoUpdate(item, type);
        if (type != MapleInventoryType.UNDEFINED) {
            client.getSession().write(MaplePacketCreator.upgradeBook(item, this));
        }
    }

    public void silentPartyUpdate() {
        if (party != null) {
            World.Party.updateParty(party.getId(), PartyOperation.SILENT_UPDATE, new MaplePartyCharacter(this));
        }
    }

    public boolean isSuperGM() {
        return gmLevel >= PlayerGMRank.SUPERGM.getLevel();
    }

    public boolean isIntern() {
        return gmLevel >= PlayerGMRank.INTERN.getLevel();
    }

    public boolean isGM() {
        return gmLevel >= PlayerGMRank.GM.getLevel();
    }

    public boolean isAdmin() {
        return gmLevel >= PlayerGMRank.ADMIN.getLevel();
    }

    public int getGMLevel() {
        return gmLevel;
    }

    public boolean hasGmLevel(int level) {
        return gmLevel >= level;
    }

    public final MapleInventory getInventory(MapleInventoryType type) {
        return inventory[type.ordinal()];
    }

    public final MapleInventory[] getInventorys() {
        return inventory;
    }

    public final void expirationTask(boolean pending, boolean firstLoad) {
        final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        if (pending) {
            if (pendingExpiration != null) {
                for (Integer z : pendingExpiration) {
                    if (MapleItemInformationProvider.getInstance().isCash(z.intValue())) {
                        client.getSession().write(CSPacket.itemExpiredForCash(z.intValue()));
                    } else {
                        client.getSession().write(CSPacket.itemExpiredForEquip(z.intValue()));
                    }
                    if (!firstLoad) {
                        final Pair<Integer, String> replace = ii.replaceItemInfo(z.intValue());
                        if (replace != null && replace.left > 0 && replace.right.length() > 0) {
                            dropMessage(5, replace.right);
                        }
                    }
                }
            }
            pendingExpiration = null;
            if (pendingSkills != null) {
                for (Integer z : pendingSkills) {
                    client.getSession().write(MaplePacketCreator.updateSkill(z, 0, 0, -1));
                    client.getSession().write(MaplePacketCreator.skillExpired(z));
                }
            }
            pendingSkills = null;
            return;
        }
        final MapleQuestStatus stat = getQuestNoAdd(MapleQuest.getInstance(GameConstants.PENDANT_SLOT));
        long expiration;
        final List<Integer> ret = new ArrayList<Integer>();
        final long currenttime = System.currentTimeMillis();
        final List<Triple<MapleInventoryType, Item, Boolean>> toberemove = new ArrayList<Triple<MapleInventoryType, Item, Boolean>>(); // This is here to prevent deadlock.
        final List<Item> tobeunlock = new ArrayList<Item>(); // This is here to prevent deadlock.

        for (final MapleInventoryType inv : MapleInventoryType.values()) {
            for (final Item item : getInventory(inv)) {
                expiration = item.getExpiration();

                if ((expiration != -1 && !GameConstants.isPet(item.getItemId()) && currenttime > expiration) || (firstLoad && ii.isLogoutExpire(item.getItemId()))) {
                    if (ItemFlag.LOCK.check(item.getFlag())) {
                        tobeunlock.add(item);
                    } else if (currenttime > expiration) {
                        toberemove.add(new Triple<MapleInventoryType, Item, Boolean>(inv, item, false));
                    }
                } else if (item.getItemId() == 5000054 && item.getPet() != null && item.getPet().getSecondsLeft() <= 0) {
                    toberemove.add(new Triple<MapleInventoryType, Item, Boolean>(inv, item, false));
                } else if (item.getPosition() == -59) {
                    if (stat == null || stat.getCustomData() == null || Long.parseLong(stat.getCustomData()) < currenttime) {
                        toberemove.add(new Triple<MapleInventoryType, Item, Boolean>(inv, item, true));
                    }
                }
            }
        }
        Item item;
        for (final Triple<MapleInventoryType, Item, Boolean> itemz : toberemove) {
            item = itemz.getMid();
            MapleInventoryManipulator.removeFromSlot(this.client, (MapleInventoryType) itemz.getLeft(), item.getPosition(), item.getQuantity(), false);
            getInventory(itemz.getLeft()).removeItem(item.getPosition(), item.getQuantity(), false);

            if (itemz.getRight() && getInventory(GameConstants.getInventoryType(item.getItemId())).getNextFreeSlot() > -1) {
                item.setPosition(getInventory(GameConstants.getInventoryType(item.getItemId())).getNextFreeSlot());
                getInventory(GameConstants.getInventoryType(item.getItemId())).addFromDB(item);
            } else {
                ret.add(item.getItemId());
            }
            if (!firstLoad) {
                final Pair<Integer, String> replace = ii.replaceItemInfo(item.getItemId());
                if (replace != null && replace.left > 0) {
                    Item theNewItem = null;
                    if (GameConstants.getInventoryType(replace.left) == MapleInventoryType.EQUIP) {
                        theNewItem = ii.getEquipById(replace.left);
                        theNewItem.setPosition(item.getPosition());
                    } else {
                        theNewItem = new Item(replace.left, item.getPosition(), (short) 1, (byte) 0);
                    }
                    getInventory(itemz.getLeft()).addFromDB(theNewItem);
                }
            }
        }
        for (final Item itemz : tobeunlock) {
            itemz.setExpiration(-1);
            itemz.setFlag((byte) (itemz.getFlag() - ItemFlag.LOCK.getValue()));
        }
        this.pendingExpiration = ret;

        final List<Integer> skilz = new ArrayList<Integer>();
        final List<Skill> toberem = new ArrayList<Skill>();
        for (Entry<Skill, SkillEntry> skil : skills.entrySet()) {
            if (skil.getValue().expiration != -1 && currenttime > skil.getValue().expiration) {
                toberem.add(skil.getKey());
            }
        }
        for (Skill skil : toberem) {
            skilz.add(skil.getId());
            this.skills.remove(skil);
            changed_skills = true;
        }
        this.pendingSkills = skilz;
        if (stat != null && stat.getCustomData() != null && Long.parseLong(stat.getCustomData()) < currenttime) { //expired bro
            quests.remove(MapleQuest.getInstance(7830));
            quests.remove(MapleQuest.getInstance(GameConstants.PENDANT_SLOT));
        }
        lastExpirationTime = System.currentTimeMillis();
    }

    public MapleShop getShop() {
        return shop;
    }

    public void setShop(MapleShop shop) {
        this.shop = shop;
    }

    public int getMeso() {
        return meso;
    }

    public final int[] getSavedLocations() {
        return savedLocations;
    }

    public int getSavedLocation(SavedLocationType type) {
        return savedLocations[type.getValue()];
    }

    public void saveLocation(SavedLocationType type) {
        savedLocations[type.getValue()] = getMapId();
        changed_savedlocations = true;
    }

    public void saveLocation(SavedLocationType type, int mapz) {
        savedLocations[type.getValue()] = mapz;
        changed_savedlocations = true;
    }

    public void clearSavedLocation(SavedLocationType type) {
        savedLocations[type.getValue()] = -1;
        changed_savedlocations = true;
    }

    public void gainMeso(int gain, boolean show) {
        gainMeso(gain, show, false, true);
    }

    public void gainMeso(int gain, boolean show, boolean inChat) {
        gainMeso(gain, show, false, true);
    }

    public void gainMeso(int gain, boolean show, boolean inChat, boolean itemReaction) {
        if (meso + gain < 0) {
            client.getSession().write(MaplePacketCreator.enableActions());
            marriage();
            return;
        }
        meso += gain;
        updateSingleStat(MapleStat.MESO, meso, itemReaction);
        //client.getSession().write(MaplePacketCreator.enableActions());
        if (show) {
            client.getSession().write(MaplePacketCreator.showMesoGain(gain, inChat));
        }
    }

    public void controlMonster(MapleMonster monster, boolean aggro) {
        if (clone || monster == null) {
            return;
        }
        monster.setController(this);
        //controlledLock.writeLock().lock();
        try {
            controlled.add(monster);
        } finally {
            //controlledLock.writeLock().unlock();
        }
        client.getSession().write(MobPacket.controlMonster(monster, false, aggro));
        monster.sendStatus(client);
    }

    public void stopControllingMonster(MapleMonster monster) {
        if (clone || monster == null) {
            return;
        }
        //controlledLock.writeLock().lock();
        try {
            if (controlled.contains(monster)) {
                controlled.remove(monster);
            }
        } finally {
            //controlledLock.writeLock().unlock();
        }
    }

    public void checkMonsterAggro(MapleMonster monster) {
        if (clone || monster == null) {
            return;
        }
        if (monster.getController() == this) {
            monster.setControllerHasAggro(true);
        } else {
            monster.switchController(this, true);
        }
    }

    public int getControlledSize() {
        return controlled.size();
    }

    public int getAccountID() {
        return accountid;
    }
    
    public int getCharacterId() {
        return characterid;
    }

    public void mobKilled(final int id, final int skillID) {
        for (MapleQuestStatus q : quests.values()) {
            if (q.getStatus() != 1 || !q.hasMobKills()) {
                continue;
            }
            if (q.mobKilled(id, skillID)) {
                client.getSession().write(MaplePacketCreator.updateQuestMobKills(q));
                if (q.getQuest().canComplete(this, null)) {
                    client.getSession().write(MaplePacketCreator.getShowQuestCompletion(q.getQuest().getId()));
                }
            }
        }
    }

    public final List<MapleQuestStatus> getStartedQuests() {
        List<MapleQuestStatus> ret = new LinkedList<MapleQuestStatus>();
        for (MapleQuestStatus q : quests.values()) {
            if (q.getStatus() == 1 && !q.isCustom() && !q.getQuest().isBlocked()) {
                ret.add(q);
            }
        }
        return ret;
    }

    public final List<MapleQuestStatus> getCompletedQuests() {
        List<MapleQuestStatus> ret = new LinkedList<MapleQuestStatus>();
        for (MapleQuestStatus q : quests.values()) {
            if (q.getStatus() == 2 && !q.isCustom() && !q.getQuest().isBlocked()) {
                ret.add(q);
            }
        }
        return ret;
    }

    public final List<Pair<Integer, Long>> getCompletedMedals() {
        List<Pair<Integer, Long>> ret = new ArrayList<Pair<Integer, Long>>();
        for (MapleQuestStatus q : quests.values()) {
            if (q.getStatus() == 2 && !q.isCustom() && !q.getQuest().isBlocked() && q.getQuest().getMedalItem() > 0 && GameConstants.getInventoryType(q.getQuest().getMedalItem()) == MapleInventoryType.EQUIP) {
                ret.add(new Pair<Integer, Long>(q.getQuest().getId(), q.getCompletionTime()));
            }
        }
        return ret;
    }

    public Map<Skill, SkillEntry> getSkills() {
        return Collections.unmodifiableMap(skills);
    }

    public int getTotalSkillLevel(final Skill skill) {
        if (skill == null) {
            return 0;
        }
        final SkillEntry ret = skills.get(skill);
        if (ret == null || ret.skillevel <= 0) {
            return 0;
        }
        return Math.min(skill.getTrueMax(), ret.skillevel + (skill.isBeginnerSkill() ? 0 : (stats.combatOrders + (skill.getMaxLevel() > 10 ? stats.incAllskill : 0) + stats.getSkillIncrement(skill.getId()))));
    }

    public int getAllSkillLevels() {
        int rett = 0;
        for (Entry<Skill, SkillEntry> ret : skills.entrySet()) {
            if (!ret.getKey().isBeginnerSkill() && !ret.getKey().isSpecialSkill() && ret.getValue().skillevel > 0) {
                rett += ret.getValue().skillevel;
            }
        }
        return rett;
    }

    public long getSkillExpiry(final Skill skill) {
        if (skill == null) {
            return 0;
        }
        final SkillEntry ret = skills.get(skill);
        if (ret == null || ret.skillevel <= 0) {
            return 0;
        }
        return ret.expiration;
    }

    public int getSkillLevel(final Skill skill) {
        if (skill == null) {
            return 0;
        }
        final SkillEntry ret = skills.get(skill);
        if (ret == null || ret.skillevel <= 0) {
            return 0;
        }
        return ret.skillevel;
    }

    public byte getMasterLevel(final int skill) {
        return getMasterLevel(SkillFactory.getSkill(skill));
    }

    public byte getMasterLevel(final Skill skill) {
        final SkillEntry ret = skills.get(skill);
        if (ret == null) {
            return 0;
        }
        return ret.masterlevel;
    }

    public void levelUp() {
        if (GameConstants.isKOC(job)) {
            if (level <= 70) {
                remainingAp += 6;
            } else {
                remainingAp += 5;
            }
        } else {
            remainingAp += 5;
        }
        int maxhp = stats.getMaxHp();
        int maxmp = stats.getMaxMp();

        if (GameConstants.isBeginnerJob(job)) { // Beginner
            maxhp += Randomizer.rand(12, 16);
            maxmp += Randomizer.rand(10, 12);
        } else if (job >= 3100 && job <= 3112) { // Warrior
            maxhp += Randomizer.rand(48, 52);
        } else if ((job >= 100 && job <= 132) || (job >= 1100 && job <= 1111)) { // Warrior
            maxhp += Randomizer.rand(48, 52);
            maxmp += Randomizer.rand(4, 6);
        } else if ((job >= 200 && job <= 232) || (job >= 1200 && job <= 1211)) { // Magician
            maxhp += Randomizer.rand(10, 14);
            maxmp += Randomizer.rand(48, 52);
        } else if (job >= 3200 && job <= 3212) { //battle mages get their own little neat thing
            maxhp += Randomizer.rand(20, 24);
            maxmp += Randomizer.rand(42, 44);
        } else if ((job >= 300 && job <= 322) || (job >= 400 && job <= 434) || (job >= 1300 && job <= 1311) || (job >= 1400 && job <= 1411) || (job >= 3300 && job <= 3312) || (job >= 2300 && job <= 2312)) { // Bowman, Thief, Wind Breaker and Night Walker
            maxhp += Randomizer.rand(20, 24);
            maxmp += Randomizer.rand(14, 16);
        } else if ((job >= 510 && job <= 512) || (job >= 1510 && job <= 1512)) { // Pirate
            maxhp += Randomizer.rand(37, 41);
            maxmp += Randomizer.rand(18, 22);
        } else if ((job >= 500 && job <= 532) || (job >= 3500 && job <= 3512) || job == 1500) { // Pirate
            maxhp += Randomizer.rand(20, 24);
            maxmp += Randomizer.rand(18, 22);
        } else if (job >= 2100 && job <= 2112) { // Aran
            maxhp += Randomizer.rand(50, 52);
            maxmp += Randomizer.rand(4, 6);
        } else if (job >= 2200 && job <= 2218) { // Evan
            maxhp += Randomizer.rand(12, 16);
            maxmp += Randomizer.rand(50, 52);
        } else { // GameMaster
            maxhp += Randomizer.rand(50, 100);
            maxmp += Randomizer.rand(50, 100);
        }
        maxmp += stats.getTotalInt() / 10;
        exp -= getNeededExp();
        level += 1;
        if (GameConstants.isKOC(job) && level < 120 && level > 10) {
            exp += getNeededExp() / 10;
        }
        if (level == 200 && !isGM()) {
            final StringBuilder sb = new StringBuilder("[恭喜] ");
            final Item medal = getInventory(MapleInventoryType.EQUIPPED).getItem((byte) -46);
            if (medal != null) { // Medal
                sb.append("<");
                sb.append(MapleItemInformationProvider.getInstance().getName(medal.getItemId()));
                sb.append("> ");
            }
            sb.append(getName());
            sb.append(" 达到了等級200級！请大家一起恭喜他!");
            World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, sb.toString()));
        }
        if (level > 200 && !isGM()) {
            final StringBuilder sb = new StringBuilder("[恭喜] ");
            final Item medal = getInventory(MapleInventoryType.EQUIPPED).getItem((byte) -46);
            if (medal != null) { // Medal
                sb.append("<");
                sb.append(MapleItemInformationProvider.getInstance().getName(medal.getItemId()));
                sb.append("> ");
            }
            sb.append(getName());
            sb.append(" 达到了等級" + level + "級！请大家一起恭喜他!");
            World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, sb.toString()));
        }

        if (level == 70 && !isGM()) {
            final StringBuilder sb = new StringBuilder("[恭喜] ");
            final Item medal = getInventory(MapleInventoryType.EQUIPPED).getItem((byte) -46);
            if (medal != null) { // Medal
                sb.append("<");
                sb.append(MapleItemInformationProvider.getInstance().getName(medal.getItemId()));
                sb.append("> ");
            }
            sb.append(getName());
            sb.append(" 达到了等級70級！请大家一起恭喜他!");
            World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, sb.toString()));
        }

        if (level == 120 && !isGM()) {
            final StringBuilder sb = new StringBuilder("[恭喜] ");
            final Item medal = getInventory(MapleInventoryType.EQUIPPED).getItem((byte) -46);
            if (medal != null) { // Medal
                sb.append("<");
                sb.append(MapleItemInformationProvider.getInstance().getName(medal.getItemId()));
                sb.append("> ");
            }
            sb.append(getName());
            sb.append(" 达到了等級120級！请大家一起恭喜他!");
            World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, sb.toString()));
        }

        maxhp = Math.min(99999, Math.abs(maxhp));
        maxmp = Math.min(99999, Math.abs(maxmp));

        final Map<MapleStat, Integer> statup = new EnumMap<MapleStat, Integer>(MapleStat.class);

        statup.put(MapleStat.MAXHP, maxhp);
        statup.put(MapleStat.MAXMP, maxmp);
        statup.put(MapleStat.HP, maxhp);
        statup.put(MapleStat.MP, maxmp);
        statup.put(MapleStat.EXP, exp);
        statup.put(MapleStat.LEVEL, (int) level);

        if (!GameConstants.isBeginnerJob(job)) { // Not Beginner, Nobless and Legend
            if (GameConstants.isResist(this.job) || GameConstants.isMercedes(this.job)) {
                remainingSp[GameConstants.getSkillBook(this.job, this.level)] += 3;
            } else {
                remainingSp[GameConstants.getSkillBook(this.job)] += 3;
            }
            client.getSession().write(MaplePacketCreator.updateSp(this, false));
            marriage();
        } else if (level <= 10) {
            stats.str += remainingAp;
            remainingAp = 0;

            statup.put(MapleStat.STR, (int) stats.getStr());
        } else if (level == 11) {
            resetStats(4, 4, 4, 4);
        }

        statup.put(MapleStat.AVAILABLEAP, (int) remainingAp);
        stats.setInfo(maxhp, maxmp, maxhp, maxmp);
        client.getSession().write(MaplePacketCreator.updatePlayerStats(statup, getJob()));
        marriage();
        map.broadcastMessage(this, MaplePacketCreator.showForeignEffect(getId(), 0), false);
        stats.recalcLocalStats(this);
        silentPartyUpdate();
        guildUpdate();
        sidekickUpdate();
        familyUpdate();

        FileoutputUtil.logToFile("logs/Data/角色升等.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + client.getSessionIPAddress() + " 账号: " + client.getAccountName() + " 玩家: " + client.getPlayer().getName() + " 升級到" + level + " 級");
        //if (!isGM()) {
        final StringBuilder sb = new StringBuilder("[恭喜] ");
        final Item medal = getInventory(MapleInventoryType.EQUIPPED).getItem((byte) -46);
        if (medal != null) { // Medal
            sb.append("<");
            sb.append(MapleItemInformationProvider.getInstance().getName(medal.getItemId()));
            sb.append("> ");
        }
        sb.append(getName());
        sb.append(" ");
        sb.append(MapleJob.getName(MapleJob.getById(getJob())));
        sb.append("在");
        sb.append(getMap().getMapName());
        sb.append("地图");
        sb.append("等级达到了");
        sb.append(getLevel());
        sb.append("级请大家一起恭喜他!");
        World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, sb.toString()));
        //}

        /*if (level == 70) {
            if (getStLog() >= 1) {
                int stjfid = getStLogid(id);
                if (getStjfLog(stjfid) >= 1) {
                    updateStjfLog(stjfid, getStjf(stjfid) + 1);
                } else {
                    setStjfLog(stjfid, 1);
                }

                modifyCSPoints(2, 666, true);
                dropMessage(5, "恭喜你达到70等，順利出師，获得666枫叶点數。");
            }
        }*/
        if (GameConstants.isAdventurer_(job)) {
            // well, removed   
        }
        // autojob for dual blade is only temporary until it tested

        /*if (job == 400 && getSubcategory() > 0) {
            switch (getLevel()) {
                case 20:
                    changeJob(430);
                    MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
                    Item item;
                    item = ii.randomizeStats((Equip) ii.getEquipById(1342000));
                    MapleInventoryManipulator.addbyItem(client, item);
                    MapleQuest.getInstance(2351).forceComplete(this, 1057006);
                    break;
            }
        }*/
 /*if (job >= 430 && job <= 434 && getSubcategory() > 0) {
            switch (getLevel()) {
                case 30:
                    changeJob(431);
                    break;
                case 55:
                    changeJob(432);
                    break;
                case 70:
                    changeJob(433);
                    break;
                case 120:
                    changeJob(434);
                    break;
            }
        }*/
        // autojob for aran is only temporary until it tested
        if (GameConstants.isAran(job)) { // Only one class
            switch (getLevel()) {
                case 10:
                    changeJob(2100);
                    break;
                case 30:
                    changeJob(2110);
                    break;
                case 70:
                    changeJob(2111);
                    break;
                case 120:
                    changeJob(2112);
                    break;
            }
        }

        //if (GameConstants.isEvan(job)) {
        //     if (level == 10) {
        //         changeJob(2200); //automatic
        //          resetStats(4, 4, 4, 4);
        //      }
        //  }
        if (GameConstants.isEvan(job)) {
            switch (level) {
                case 9:
                    //dropMessage(5, "Make sure you finish all the Required quests before reaching level 10, or you will not be able to continue.");
                    break;
                case 10:
                case 20:
                case 30:
                case 40:
                case 50:
                case 60:
                case 80:
                case 100:
                case 120:
                case 160:
                    if (job < 2218) {
                        changeJob(job == 2001 ? 2200 : (job == 2200 ? 2210 : (job + 1))); //automatic
                    }
                    break;
            }
        }
    }

    public void changeKeybinding(int key, byte type, int action) {
        if (type != 0) {
            keylayout.Layout().put(Integer.valueOf(key), new Pair<Byte, Integer>(type, action));
        } else {
            keylayout.Layout().remove(Integer.valueOf(key));
        }
    }

    public void sendMacros() {
        for (int i = 0; i < 5; i++) {
            if (skillMacros[i] != null) {
                client.getSession().write(MaplePacketCreator.getMacros(skillMacros));
                break;
            }
        }
    }

    public void updateMacros(int position, SkillMacro updateMacro) {
        skillMacros[position] = updateMacro;
        changed_skillmacros = true;
    }

    public final SkillMacro[] getMacros() {
        return skillMacros;
    }

    public void tempban(String reason, Calendar duration, int greason, boolean IPMac) {
        if (IPMac) {
            client.banMacs();
        }
        client.getSession().write(MaplePacketCreator.GMPoliceMessage());
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps;
            if (IPMac) {
                ps = con.prepareStatement("INSERT INTO ipbans VALUES (DEFAULT, ?)");
                ps.setString(1, client.getSession().getRemoteAddress().toString().split(":")[0]);
                ps.execute();
                ps.close();
            }

            client.getSession().close();

            ps = con.prepareStatement("UPDATE accounts SET tempban = ?, banreason = ?, greason = ? WHERE id = ?");
            Timestamp TS = new Timestamp(duration.getTimeInMillis());
            ps.setTimestamp(1, TS);
            ps.setString(2, reason);
            ps.setInt(3, greason);
            ps.setInt(4, accountid);
            ps.execute();
            ps.close();
        } catch (SQLException ex) {
            System.err.println("Error while tempbanning" + ex);
            FileoutputUtil.outError("logs/数据库异常.txt", ex);
        }

    }

    public final boolean ban(String reason, boolean IPMac, boolean autoban, boolean hellban) {
        if (lastmonthfameids == null) {
            throw new RuntimeException("Trying to ban a non-loaded character (testhack)");
        }
        client.getSession().write(MaplePacketCreator.GMPoliceMessage());
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps = con.prepareStatement("UPDATE accounts SET banned = ?, banreason = ? WHERE id = ?");
            ps.setInt(1, autoban ? 2 : 1);
            ps.setString(2, reason);
            ps.setInt(3, accountid);
            ps.execute();
            ps.close();

            if (IPMac) {
                client.banMacs();
                ps = con.prepareStatement("INSERT INTO ipbans VALUES (DEFAULT, ?)");
                ps.setString(1, client.getSessionIPAddress());
                ps.execute();
                ps.close();

                if (hellban) {
                    PreparedStatement psa = con.prepareStatement("SELECT * FROM accounts WHERE id = ?");
                    psa.setInt(1, accountid);
                    ResultSet rsa = psa.executeQuery();
                    if (rsa.next()) {
                        PreparedStatement pss = con.prepareStatement("UPDATE accounts SET banned = ?, banreason = ? WHERE email = ? OR SessionIP = ?");
                        pss.setInt(1, autoban ? 2 : 1);
                        pss.setString(2, reason);
                        pss.setString(3, rsa.getString("email"));
                        pss.setString(4, client.getSessionIPAddress());
                        pss.execute();
                        pss.close();
                    }
                    rsa.close();
                    psa.close();
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error while banning" + ex);
            FileoutputUtil.outError("logs/数据库异常.txt", ex);
            return false;
        }
        client.getSession().close();
        return true;
    }

    public static boolean ban(String id, String reason, boolean accountId, int gmlevel, boolean hellban) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps;
            if (id.matches("/[0-9]{1,3}\\..*")) {
                ps = con.prepareStatement("INSERT INTO ipbans VALUES (DEFAULT, ?)");
                ps.setString(1, id);
                ps.execute();
                ps.close();
                return true;
            }
            if (accountId) {
                ps = con.prepareStatement("SELECT id FROM accounts WHERE name = ?");
            } else {
                ps = con.prepareStatement("SELECT accountid FROM characters WHERE name = ?");
            }
            boolean ret = false;
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int z = rs.getInt(1);
                PreparedStatement psb = con.prepareStatement("UPDATE accounts SET banned = 1, banreason = ? WHERE id = ? AND gm < ?");
                psb.setString(1, reason);
                psb.setInt(2, z);
                psb.setInt(3, gmlevel);
                psb.execute();
                psb.close();

                if (gmlevel > 100) { //admin ban
                    PreparedStatement psa = con.prepareStatement("SELECT * FROM accounts WHERE id = ?");
                    psa.setInt(1, z);
                    ResultSet rsa = psa.executeQuery();
                    if (rsa.next()) {
                        String sessionIP = rsa.getString("sessionIP");
                        if (sessionIP != null && sessionIP.matches("/[0-9]{1,3}\\..*")) {
                            PreparedStatement psz = con.prepareStatement("INSERT INTO ipbans VALUES (DEFAULT, ?)");
                            psz.setString(1, sessionIP);
                            psz.execute();
                            psz.close();
                        }
                        if (rsa.getString("macs") != null) {
                            String[] macData = rsa.getString("macs").split(", ");
                            if (macData.length > 0) {
                                MapleClient.banMacs(macData);
                            }
                        }
                        if (hellban) {
                            PreparedStatement pss = con.prepareStatement("UPDATE accounts SET banned = 1, banreason = ? WHERE email = ?" + (sessionIP == null ? "" : " OR SessionIP = ?"));
                            pss.setString(1, reason);
                            pss.setString(2, rsa.getString("email"));
                            if (sessionIP != null) {
                                pss.setString(3, sessionIP);
                            }
                            pss.execute();
                            pss.close();
                        }
                    }
                    rsa.close();
                    psa.close();
                }
                ret = true;
            }
            rs.close();
            ps.close();
            return ret;
        } catch (SQLException ex) {
            System.err.println("Error while banning" + ex);
            FileoutputUtil.outError("logs/数据库异常.txt", ex);
        }
        return false;
    }

    @Override
    public int getObjectId() {
        return getId();
    }

    @Override
    public void setObjectId(int id) {
        throw new UnsupportedOperationException();
    }

    public MapleStorage getStorage() {
        return storage;
    }

    public void addVisibleMapObject(MapleMapObject mo) {
        if (clone) {
            return;
        }
        //visibleMapObjectsLock.writeLock().lock();
        try {
            visibleMapObjects.add(mo);
        } finally {
            //visibleMapObjectsLock.writeLock().unlock();
        }
    }

    public void removeVisibleMapObject(MapleMapObject mo) {
        if (clone) {
            return;
        }

        //visibleMapObjectsLock.writeLock().lock();
        try {
            visibleMapObjects.remove(mo);
        } finally {
            //visibleMapObjectsLock.writeLock().unlock();
        }
    }

    public boolean isMapObjectVisible(MapleMapObject mo) {
        //visibleMapObjectsLock.readLock().lock();
        try {
            return !clone && visibleMapObjects.contains(mo);
        } finally {
            //visibleMapObjectsLock.readLock().unlock();
        }
    }

    public Collection<MapleMapObject> getAndWriteLockVisibleMapObjects() {
        //visibleMapObjectsLock.writeLock().lock();
        return visibleMapObjects;
    }

    public void unlockWriteVisibleMapObjects() {
        //visibleMapObjectsLock.writeLock().unlock();
    }

    public boolean isAlive() {
        return stats.getHp() > 0;
    }

    @Override
    public void sendDestroyData(MapleClient client) {
        client.getSession().write(MaplePacketCreator.removePlayerFromMap(this.getObjectId()));
        for (final WeakReference<MapleCharacter> chr : clones) {
            if (chr.get() != null) {
                chr.get().sendDestroyData(client);
            }
        }
    }

    @Override
    public void sendSpawnData(MapleClient client) {
        if (client.getPlayer().allowedToTarget(this)) {
            client.getSession().write(MaplePacketCreator.spawnPlayerMapobject(this));
            //map.broadcastMessage(this, MaplePacketCreator.updateCharLook(this), false);
            if (getParty() != null && !isClone()) {
                updatePartyMemberHP();
                receivePartyMemberHP();
            }

            /*for (final MaplePet pet : pets) {
                if (pet.getSummoned()) {
                    client.getSession().write(PetPacket.showPet(this, pet, false, false));

                }
            }*/
            for (final WeakReference<MapleCharacter> chr : clones) {
                if (chr.get() != null) {
                    chr.get().sendSpawnData(client);
                }
            }
            if (dragon != null) {
                client.getSession().write(MaplePacketCreator.spawnDragon(dragon));
            }
            if (android != null && getInventory(MapleInventoryType.EQUIPPED).getItem((byte) -35) != null) {
                client.getSession().write(MaplePacketCreator.spawnAndroid(this, android));
            }
            if (summonedFamiliar != null) {
                client.getSession().write(MaplePacketCreator.spawnFamiliar(summonedFamiliar, true));
            }
            if (summons != null && summons.size() > 0) {
                //summonsLock.readLock().lock();
                try {
                    try {
                        for (final MapleSummon summon : summons) {
                            client.getSession().write(MaplePacketCreator.spawnSummon(summon, false));
                        }
                    } catch (Exception ex) {
                        System.err.println("sendSpawnData失敗" + ex);
                        FileoutputUtil.outputFileError("logs/sendSpawnData失敗.txt", ex);
                    }
                } finally {
                    //summonsLock.readLock().unlock();
                }
            }
            if (followid > 0 && followon) {
                client.getSession().write(MaplePacketCreator.followEffect(followinitiator ? followid : id, followinitiator ? id : followid, null));
            }
        }
    }

    public final void equipChanged() {
        if (map == null) {
            return;
        }
        map.broadcastMessage(this, MaplePacketCreator.updateCharLook(this), false);
        stats.recalcLocalStats(this);
        if (getMessenger() != null) {
            World.Messenger.updateMessenger(getMessenger().getId(), getName(), client.getChannel());
        }
    }

    public final void marriage() {
        /*if (getMarriageId() != 0) {
            int ch = World.Find.findChannel(getMarriageId());
            if (ch > 0) {
                MapleCharacter victim = client.getChannelServer().getPlayerStorage().getCharacterById(getMarriageId());
                if (victim != null) {
                    if (victim.getMapId() == getMapId()) {
                        client.getSession().write(MaplePacketCreator.updateCharLook(victim));
                    }
                }
            }
        }*/
    }

    public final MaplePet getPet(final int index) {
        byte count = 0;
        for (final MaplePet pet : pets) {
            if (pet.getSummoned()) {
                if (count == index) {
                    return pet;
                }
                count++;
            }
        }
        return null;
    }

    public void removePetCS(MaplePet pet) {
        pets.remove(pet);
    }

    public void addPet(final MaplePet pet) {
        if (pets.contains(pet)) {
            pets.remove(pet);
        }
        pets.add(pet);
        // So that the pet will be at the last
        // Pet index logic :(
    }

    public void removePet(MaplePet pet) {
        pet.setSummoned(0);
        pets.remove(pet);
    }

    public final List<MaplePet> getSummonedPets() {
        List<MaplePet> ret = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ret.add(null);
        }
        for (int i = 0; i < 3; i++) {
            for (final MaplePet pet : pets) {
                if (pet != null && pet.getSummoned()) {
                    int index = pet.getSummonedValue() - 1;
                    if (index == i) {
                        ret.remove(index);
                        ret.add(index, pet);
                        break;
                    }
                }
            }
        }
        List<Integer> nullArr = new ArrayList();
        nullArr.add(null);
        ret.removeAll(nullArr);
        return ret;
    }

    public final MaplePet getSummonedPet(final int index) {
        for (final MaplePet pet : getSummonedPets()) {
            if (pet.getSummonedValue() - 1 == index) {
                return pet;
            }
        }
        return null;
    }

    public final void shiftPetsRight() {
        List<MaplePet> petsz = getSummonedPets();
        if (petsz.size() >= 3 || petsz.size() < 1) {
            return;
        } else {
            boolean[] indexBool = new boolean[]{false, false, false};
            for (int i = 0; i < 3; i++) {
                for (MaplePet p : petsz) {
                    if (p.getSummonedValue() == i + 1) {
                        indexBool[i] = true;
                    }
                }
            }
            if (petsz.size() > 1) {
                if (!indexBool[2]) {
                    petsz.get(0).setSummoned(2);
                    petsz.get(1).setSummoned(3);
                } else if (!indexBool[1]) {
                    petsz.get(0).setSummoned(2);
                }
            } else if (indexBool[0]) {
                petsz.get(0).setSummoned(2);
            }
        }
    }

    public final int getPetSlotNext() {
        List<MaplePet> petsz = getSummonedPets();
        int index = 0;
        if (petsz.size() >= 3) {
            //unequipPet(getSummonedPet(0), false);
            unequipAllPets();
        } else {
            boolean[] indexBool = new boolean[]{false, false, false};
            for (int i = 0; i < 3; i++) {
                for (MaplePet p : petsz) {
                    if (p.getSummonedValue() == i + 1) {
                        indexBool[i] = true;
                        break;
                    }
                }
            }
            for (boolean b : indexBool) {
                if (!b) {
                    break;
                }
                index++;
            }
            index = Math.min(index, 2);
            for (MaplePet p : petsz) {
                if (p.getSummonedValue() == index + 1) {
                    //unequipPet(p, false);
                    unequipAllPets();
                }
            }
        }
        return index;
    }

    public final byte getPetIndex(final MaplePet petz) {
        return (byte) Math.max(-1, petz.getSummonedValue() - 1);
    }

    public final byte getPetIndex(final int petId) {
        for (final MaplePet pet : getSummonedPets()) {
            if (pet.getUniqueId() == petId) {
                return (byte) Math.max(-1, pet.getSummonedValue() - 1);
            }
        }
        return -1;
    }

    public final byte getPetById(final int petId) {
        byte count = 0;
        for (final MaplePet pet : pets) {
            if (pet.getSummoned()) {
                if (pet.getPetItemId() == petId) {
                    return count;
                }
                count++;
            }
        }
        return -1;
    }

    public final List<MaplePet> getPets() {
        return pets;
    }

    public final void unequipAllPets() {
        int index = 0;
        for (final MaplePet pet : getSummonedPets()) {
            if (pet != null) {
                unequipPet(pet, false, index);
            }
            index++;
        }
    }

    public void unequipPet(MaplePet pet, boolean hunger, int index) {
        if (pet.getSummoned()) {
            pet.saveToDb();

            List<MaplePet> summonedPets = getSummonedPets();
            if (summonedPets.contains(pet)) {
                summonedPets.remove(pet);
                int i = 1;
                for (MaplePet p : summonedPets) {
                    if (p == null) {
                        continue;
                    }
                    p.setSummoned(i);
                    i++;
                }
            }

            client.getSession().write(PetPacket.updatePet(pet, getInventory(MapleInventoryType.CASH).getItem((byte) pet.getInventoryPosition()), false));
            //client.getPlayer().marriage();
            if (map != null) {
                //map.broadcastMessage(this, PetPacket.showPet(this, pet, true, hunger), true);
                map.broadcastMessage(this, PetPacket.removePet(this.getId(), index), true);
            }
            pet.setSummoned(0);
            client.getSession().write(PetPacket.petStatUpdate(this));
            client.getSession().write(MaplePacketCreator.enableActions());
            marriage();
        }
    }

    public final long getLastFameTime() {
        return lastfametime;
    }

    public final List<Integer> getFamedCharacters() {
        return lastmonthfameids;
    }

    public final List<Integer> getBattledCharacters() {
        return lastmonthbattleids;
    }

    public FameStatus canGiveFame(MapleCharacter from) {
        if (lastfametime >= System.currentTimeMillis() - 60 * 60 * 24 * 1000) {
            return FameStatus.NOT_TODAY;
        } else if (from == null || lastmonthfameids == null || lastmonthfameids.contains(Integer.valueOf(from.getId()))) {
            return FameStatus.NOT_THIS_MONTH;
        }
        return FameStatus.OK;
    }

    public void hasGivenFame(MapleCharacter to) {
        lastfametime = System.currentTimeMillis();
        lastmonthfameids.add(Integer.valueOf(to.getId()));
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO famelog (characterid, characterid_to) VALUES (?, ?)");
            ps.setInt(1, getId());
            ps.setInt(2, to.getId());
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            System.err.println("ERROR writing famelog for char " + getName() + " to " + to.getName() + e);
            FileoutputUtil.outError("logs/数据库异常.txt", e);
        }
    }

    public boolean canBattle(MapleCharacter to) {
        if (to == null || lastmonthbattleids == null || lastmonthbattleids.contains(Integer.valueOf(to.getAccountID()))) {
            return false;
        }
        return true;
    }

    public void hasBattled(MapleCharacter to) {
        lastmonthbattleids.add(Integer.valueOf(to.getAccountID()));
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO battlelog (accid, accid_to) VALUES (?, ?)");
            ps.setInt(1, getAccountID());
            ps.setInt(2, to.getAccountID());
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            System.err.println("ERROR writing battlelog for char " + getName() + " to " + to.getName() + e);
            FileoutputUtil.outError("logs/数据库异常.txt", e);
        }
    }

    public final MapleKeyLayout getKeyLayout() {
        return this.keylayout;
    }

    public MapleParty getParty() {
        if (party == null) {
            return null;
        } else if (party.isDisbanded()) {
            party = null;
        }
        return party;
    }

    public byte getWorld() {
        return world;
    }

    public void setWorld(byte world) {
        this.world = world;
    }

    public void setParty(MapleParty party) {
        this.party = party;
    }

    public MapleTrade getTrade() {
        return trade;
    }

    public void setTrade(MapleTrade trade) {
        this.trade = trade;
    }

    public EventInstanceManager getEventInstance() {
        return eventInstance;
    }

    public void setEventInstance(EventInstanceManager eventInstance) {
        this.eventInstance = eventInstance;
    }

    public void addDoor(MapleDoor door) {
        doors.add(door);
    }

    public void clearDoors() {
        doors.clear();
    }

    public List<MapleDoor> getDoors() {
        return new ArrayList<MapleDoor>(doors);
    }

    public void addMechDoor(MechDoor door) {
        mechDoors.add(door);
    }

    public void clearMechDoors() {
        mechDoors.clear();
    }

    public List<MechDoor> getMechDoors() {
        return new ArrayList<MechDoor>(mechDoors);
    }

    public void setSmega() {
        if (smega) {
            smega = false;
            dropMessage(5, "由于您关闭了显示广播，所以您看不見任何的广播，如果要打开请打@TSmega。");
        } else {
            smega = true;
            dropMessage(5, "目前已经打开显示广播，若要再次关闭请打@TSmega。");
        }
    }

    public boolean getSmega() {
        return smega;
    }

    public List<MapleSummon> getSummonsReadLock() {
        //summonsLock.readLock().lock();
        return summons;
    }

    public int getSummonsSize() {
        return summons.size();
    }

    //public void unlockSummonsReadLock() {
    //    summonsLock.readLock().unlock();
    //}
    public void addSummon(MapleSummon s) {
        //summonsLock.writeLock().lock();
        try {
            summons.add(s);
        } finally {
            //summonsLock.writeLock().unlock();
        }
    }

    public void removeSummon(MapleSummon s) {
        //summonsLock.writeLock().lock();
        try {
            summons.remove(s);
        } finally {
            //summonsLock.writeLock().unlock();
        }
    }

    public int getChair() {
        return chair;
    }

    public int getItemEffect() {
        return itemEffect;
    }

    public void setChair(int chair) {
        this.chair = chair;
        stats.relocHeal(this);
    }

    public void setItemEffect(int itemEffect) {
        this.itemEffect = itemEffect;
    }

    @Override
    public MapleMapObjectType getType() {
        return MapleMapObjectType.PLAYER;
    }

    public int getFamilyId() {
        if (mfc == null) {
            return 0;
        }
        return mfc.getFamilyId();
    }

    public int getSeniorId() {
        if (mfc == null) {
            return 0;
        }
        return mfc.getSeniorId();
    }

    public int getJunior1() {
        if (mfc == null) {
            return 0;
        }
        return mfc.getJunior1();
    }

    public int getJunior2() {
        if (mfc == null) {
            return 0;
        }
        return mfc.getJunior2();
    }

    public int getCurrentRep() {
        return currentrep;
    }

    public int getTotalRep() {
        return totalrep;
    }

    public void setCurrentRep(int _rank) {
        currentrep = _rank;
        if (mfc != null) {
            mfc.setCurrentRep(_rank);
        }
    }

    public void setTotalRep(int _rank) {
        totalrep = _rank;
        if (mfc != null) {
            mfc.setTotalRep(_rank);
        }
    }

    public int getTotalWins() {
        return totalWins;
    }

    public int getTotalLosses() {
        return totalLosses;
    }

    public void increaseTotalWins() {
        totalWins++;
    }

    public void increaseTotalLosses() {
        totalLosses++;
    }

    public int getGuildId() {
        return guildid;
    }

    public byte getGuildRank() {
        return guildrank;
    }

    public int getGuildContribution() {
        return guildContribution;
    }

    public void setGuildId(int _id) {
        guildid = _id;
        if (guildid > 0) {
            if (mgc == null) {
                mgc = new MapleGuildCharacter(this);
            } else {
                mgc.setGuildId(guildid);
            }
        } else {
            mgc = null;
            guildContribution = 0;
        }
    }

    public void setGuildRank(byte _rank) {
        guildrank = _rank;
        if (mgc != null) {
            mgc.setGuildRank(_rank);
        }
    }

    public void setGuildContribution(int _c) {
        this.guildContribution = _c;
        if (mgc != null) {
            mgc.setGuildContribution(_c);
        }
    }

    public MapleGuildCharacter getMGC() {
        return mgc;
    }

    public void setAllianceRank(byte rank) {
        allianceRank = rank;
        if (mgc != null) {
            mgc.setAllianceRank(rank);
        }
    }

    public byte getAllianceRank() {
        return allianceRank;
    }

    public MapleGuild getGuild() {
        if (getGuildId() <= 0) {
            return null;
        }
        return World.Guild.getGuild(getGuildId());
    }

    public void setJob(int j) {
        this.job = (short) j;
    }

    public void sidekickUpdate() {
        if (sidekick == null) {
            return;
        }
        sidekick.getCharacter(sidekick.getCharacter(0).getId() == getId() ? 0 : 1).update(this);

        if (!sidekick.checkLevels(getLevel(), sidekick.getCharacter(sidekick.getCharacter(0).getId() == getId() ? 1 : 0).getLevel())) {
            sidekick.eraseToDB();
        }
    }

    public void guildUpdate() {
        if (guildid <= 0) {
            return;
        }
        mgc.setLevel((short) level);
        mgc.setJobId(job);
        World.Guild.memberLevelJobUpdate(mgc);
    }

    public void saveGuildStatus() {
        MapleGuild.setOfflineGuildStatus(guildid, guildrank, guildContribution, allianceRank, id);
    }

    public void familyUpdate() {
        if (mfc == null) {
            return;
        }
        World.Family.memberFamilyUpdate(mfc, this);
    }

    public void saveFamilyStatus() {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps = con.prepareStatement("UPDATE characters SET familyid = ?, seniorid = ?, junior1 = ?, junior2 = ? WHERE id = ?");
            if (mfc == null) {
                ps.setInt(1, 0);
                ps.setInt(2, 0);
                ps.setInt(3, 0);
                ps.setInt(4, 0);
            } else {
                ps.setInt(1, mfc.getFamilyId());
                ps.setInt(2, mfc.getSeniorId());
                ps.setInt(3, mfc.getJunior1());
                ps.setInt(4, mfc.getJunior2());
            }
            ps.setInt(5, id);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException se) {
            System.out.println("SQLException: " + se.getLocalizedMessage());
            FileoutputUtil.outError("logs/数据库异常.txt", se);
            se.printStackTrace();
        }
    }

    public static void gainMeso(Map<MapleCharacter, Integer> GiveList) {
        Iterator<MapleCharacter> iter = GiveList.keySet().iterator();
        while (iter.hasNext()) {
            MapleCharacter chr = iter.next();
            int quantity = GiveList.get(chr);
            if (quantity > 0) {
                chr.gainMeso(quantity, true);
                chr.dropMessage(-5, "[泡点系統] 恭喜您获得" + quantity + "金币。");
            }
        }
    }

    public static void gainExp(Map<MapleCharacter, Integer> GiveList) {
        Iterator<MapleCharacter> iter = GiveList.keySet().iterator();
        while (iter.hasNext()) {
            MapleCharacter chr = iter.next();
            int quantity = GiveList.get(chr);
            if (quantity > 0) {
                chr.gainExp(quantity, true, false, true);
                chr.dropMessage(-5, "[泡点系統] 恭喜您获得" + quantity + "经验。");
            }
        }
    }

    public static void modifyCSPoints(int type, Map<MapleCharacter, Integer> GiveList) {
        Iterator<MapleCharacter> iter = GiveList.keySet().iterator();
        while (iter.hasNext()) {
            MapleCharacter chr = iter.next();
            int quantity = GiveList.get(chr);
            if (quantity > 0) {
                chr.modifyCSPoints(type, quantity, true);
                if (type == 1) {
                    chr.dropMessage(-5, "[泡点系統] 恭喜您获得" + quantity + "GASH。");
                }
                if (type == 2) {
                    chr.dropMessage(-5, "[泡点系統] 恭喜您获得" + quantity + "枫叶点數。");
                }
            }
        }
    }

    public void modifyCSPoints(int type, int quantity) {
        modifyCSPoints(type, quantity, false);
    }

    public void modifyCSPoints(int type, int quantity, boolean show) {
        switch (type) {
            case 1:
                //case 4:
                /*if (acash + quantity < 0) {
                    if (show) {
                        dropMessage(-1, "目前GASH点數已满，无法获得更多的GASH点數.");
                    }
                    return;
                }
                acash += quantity;*/
                int Acash = getAcash();
                if (Acash + quantity < 0) {
                    if (show) {
                        dropMessage(-1, "目前GASH点數已满，无法获得更多的GASH点數");
                    }
                    return;
                }
                setAcash(Acash + quantity);
                break;
            case 2:
                /*if (maplepoints + quantity < 0) {
                    if (show) {
                        dropMessage(-1, "目前枫叶点數已满，无法获得更多的枫叶点數.");
                    }
                    return;
                }
                maplepoints += quantity;*/
                int MPoints = getMPoints();
                if (MPoints + quantity < 0) {
                    if (show) {
                        dropMessage(-1, "你的抵用卷已满，无法获得更多抵用卷");
                    }
                    return;
                }
                setMPoints(MPoints + quantity);
                break;
            case 3:
                int Points = getPoints();
                if (Points + quantity < 0) {
                    if (show) {
                        dropMessage(-1, "目前紅利点數已满，无法获得更多的GASH点數");
                    }
                    return;
                }
                setPoints(Points + quantity);
                break;
            default:
                break;
        }
        if (show && quantity != 0) {
            dropMessage(-1, "您已经 " + (quantity > 0 ? "获得 " : "花費 ") + quantity + (type == 1 ? " GASH点數." : " 枫叶点數."));
            dropMessage(6, "您已经 " + (quantity > 0 ? "获得 " : "花費 ") + quantity + (type == 1 ? " GASH点數." : " 枫叶点數."));
        }
        client.getSession().write(MaplePacketCreator.showCharCash(this));
    }

    public int getCSPoints(int type) {
        switch (type) {
            case 1:
                //case 4:
                //return acash;
                return getAcash();
            case 2:
                return getMPoints();
            case 3:
                return getPoints();
            default:
                return 0;
        }
    }

    public int getOfflineAcash(MapleCharacter victim) {
        return getAcash(victim);
    }

    public int getAcash() {
        return getAcash(this);
    }

    public int getAcash(MapleCharacter chr) {
        int maxtimes = 10;
        int nowtime = 0;
        int delay = 500;
        boolean error = false;
        int x = 0;
        do {
            nowtime++;
            try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("Select Acash from Accounts Where id = " + chr.getClient().getAccID());
                while (rs.next()) {
                    int debug = -1;
                    try {
                        debug = rs.getInt("Acash");
                    } catch (SQLException ex) {
                    }
                    if (debug != -1) {
                        x = rs.getInt("Acash");
                        error = false;
                    } else {
                        error = true;
                    }
                }
                rs.close();
            } catch (SQLException SQL) {
                System.err.println("[getAcash]无法连接数据库");
                FileoutputUtil.outError("logs/数据库异常.txt", SQL);
            } catch (Exception ex) {
                System.err.println("[getAcash]" + ex);
                FileoutputUtil.outError("logs/数据库异常.txt", ex);
            }
            if (error) {
                try {
                    Thread.sleep(delay);
                } catch (Exception ex) {
                    FileoutputUtil.outError("logs/数据库异常.txt", ex);
                }
            }
        } while (error && (nowtime < maxtimes));
        return x;
    }

    public void setAcash(int x) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps;
            ps = con.prepareStatement("Update Accounts set Acash = ? Where id = ?");
            ps.setInt(1, x);
            ps.setInt(2, getClient().getAccID());
            ps.execute();
            ps.close();
        } catch (SQLException ex) {
            System.err.println("[Acash]无法连接数据库");
            FileoutputUtil.outError("logs/数据库异常.txt", ex);
        } catch (Exception ex) {
            System.err.println("[setAcash]" + ex);
            FileoutputUtil.outError("logs/数据库异常.txt", ex);
        }
    }

    public int getMPoints() {
        return getMPoints(this);
    }

    public int getMPoints(MapleCharacter chr) {
        int maxtimes = 10;
        int nowtime = 0;
        int delay = 500;
        boolean error = false;
        int x = 0;
        do {
            nowtime++;
            try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("Select mPoints from Accounts Where id = " + chr.getClient().getAccID());
                while (rs.next()) {
                    int debug = -1;
                    try {
                        debug = rs.getInt("mPoints");
                    } catch (SQLException ex) {
                    }
                    if (debug != -1) {
                        x = rs.getInt("mPoints");
                        error = false;
                    } else {
                        error = true;
                    }
                }
                rs.close();
            } catch (SQLException SQL) {
                System.err.println("[getMPoints]无法连接数据库");
                FileoutputUtil.outError("logs/数据库异常.txt", SQL);
            } catch (Exception ex) {
                System.err.println("[getMPoints]" + ex);
                FileoutputUtil.outError("logs/数据库异常.txt", ex);
            }
            if (error) {
                try {
                    Thread.sleep(delay);
                } catch (Exception ex) {
                    FileoutputUtil.outError("logs/数据库异常.txt", ex);
                }
            }
        } while (error && (nowtime < maxtimes));
        return x;
    }

    public void setMPoints(int x) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps;
            ps = con.prepareStatement("Update Accounts set mPoints = ? Where id = ?");
            ps.setInt(1, x);
            ps.setInt(2, getClient().getAccID());
            ps.execute();
            ps.close();
        } catch (SQLException ex) {
            System.err.println("[MPoints]无法连接数据库");
            FileoutputUtil.outError("logs/数据库异常.txt", ex);
        } catch (Exception ex) {
            System.err.println("[setMPoints]" + ex);
            FileoutputUtil.outError("logs/数据库异常.txt", ex);
        }
    }

    public void setLxExp(int x) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps;
            ps = con.prepareStatement("Update characters set exp = ? Where id = ?");
            ps.setInt(1, x);
            ps.setInt(2, getId());
            ps.execute();
            ps.close();
        } catch (SQLException ex) {
            System.err.println("[LxExp]无法连接数据库");
            FileoutputUtil.outError("logs/数据库异常.txt", ex);
        } catch (Exception ex) {
            System.err.println("[LxExp]" + ex);
            FileoutputUtil.outError("logs/数据库异常.txt", ex);
        }
    }

    public void setLxMeso(int x) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps;
            ps = con.prepareStatement("Update characters set meso = ? Where id = ?");
            ps.setInt(1, x);
            ps.setInt(2, getId());
            ps.execute();
            ps.close();
        } catch (SQLException ex) {
            System.err.println("[LxMeso]无法连接数据库");
            FileoutputUtil.outError("logs/数据库异常.txt", ex);
        } catch (Exception ex) {
            System.err.println("[LxMeso]" + ex);
            FileoutputUtil.outError("logs/数据库异常.txt", ex);
        }
    }

    public void setLxmPoints(int x) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps;
            ps = con.prepareStatement("Update Accounts set mPoints = ? Where id = ?");
            ps.setInt(1, x);
            ps.setInt(2, getId());
            ps.execute();
            ps.close();
        } catch (SQLException ex) {
            System.err.println("[LxMeso]无法连接数据库");
            FileoutputUtil.outError("logs/数据库异常.txt", ex);
        } catch (Exception ex) {
            System.err.println("[LxMeso]" + ex);
            FileoutputUtil.outError("logs/数据库异常.txt", ex);
        }
    }

    public int getOfflinePoints(MapleCharacter victim) {
        return getPoints(victim);
    }

    public int getPoints() {
        return getPoints(this);
    }

    public int getPoints(MapleCharacter chr) {
        int maxtimes = 10;
        int nowtime = 0;
        int delay = 500;
        boolean error = false;
        int x = 0;
        do {
            nowtime++;
            try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("Select points from Accounts Where id = " + chr.getClient().getAccID());
                while (rs.next()) {
                    int debug = -1;
                    try {
                        debug = rs.getInt("points");
                    } catch (SQLException ex) {
                    }
                    if (debug != -1) {
                        x = rs.getInt("points");
                        error = false;
                    } else {
                        error = true;
                    }
                }
                rs.close();
            } catch (SQLException SQL) {
                System.err.println("[getPoints]无法连接数据库");
                FileoutputUtil.outError("logs/数据库异常.txt", SQL);
            } catch (Exception ex) {
                System.err.println("[getPoints]" + ex);
                FileoutputUtil.outError("logs/数据库异常.txt", ex);
            }
            if (error) {
                try {
                    Thread.sleep(delay);
                } catch (Exception ex) {
                    FileoutputUtil.outError("logs/数据库异常.txt", ex);
                }
            }
        } while (error && (nowtime < maxtimes));
        return x;
    }

    public void setPoints(int x) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps;
            ps = con.prepareStatement("Update Accounts set points = ? Where id = ?");
            ps.setInt(1, x);
            ps.setInt(2, getClient().getAccID());
            ps.execute();
            ps.close();
        } catch (SQLException ex) {
            System.err.println("[Points]无法连接数据库");
            FileoutputUtil.outError("logs/数据库异常.txt", ex);
        } catch (Exception ex) {
            System.err.println("[setPoints]" + ex);
            FileoutputUtil.outError("logs/数据库异常.txt", ex);
        }
    }

    public final boolean hasEquipped(int itemid) {
        return inventory[MapleInventoryType.EQUIPPED.ordinal()].countById(itemid) >= 1;
    }

    public final boolean haveItem(int itemid, int quantity, boolean checkEquipped, boolean greaterOrEquals) {
        final MapleInventoryType type = GameConstants.getInventoryType(itemid);
        int possesed = inventory[type.ordinal()].countById(itemid);
        if (checkEquipped && type == MapleInventoryType.EQUIP) {
            possesed += inventory[MapleInventoryType.EQUIPPED.ordinal()].countById(itemid);
        }
        if (greaterOrEquals) {
            return possesed >= quantity;
        } else {
            return possesed == quantity;
        }
    }

    public final boolean haveItem(int itemid, int quantity) {
        return haveItem(itemid, quantity, true, true);
    }

    public final boolean haveItem(int itemid) {
        return haveItem(itemid, 1, true, true);
    }

    public static enum FameStatus {
        OK, NOT_TODAY, NOT_THIS_MONTH
    }

    public byte getBuddyCapacity() {
        return buddylist.getCapacity();
    }

    public void setBuddyCapacity(byte capacity) {
        buddylist.setCapacity(capacity);
        client.getSession().write(MaplePacketCreator.updateBuddyCapacity(capacity));
    }

    public MapleMessenger getMessenger() {
        return messenger;
    }

    public void setMessenger(MapleMessenger messenger) {
        this.messenger = messenger;
    }

    public void addCooldown(int skillId, long startTime, long length) {
        coolDowns.put(Integer.valueOf(skillId), new MapleCoolDownValueHolder(skillId, startTime, length));
    }

    public void removeCooldown(int skillId) {
        if (coolDowns.containsKey(Integer.valueOf(skillId))) {
            coolDowns.remove(Integer.valueOf(skillId));
        }
    }

    public boolean skillisCooling(int skillId) {
        return coolDowns.containsKey(Integer.valueOf(skillId));
    }

    public void giveCoolDowns(final int skillid, long starttime, long length) {
        addCooldown(skillid, starttime, length);
    }

    public void giveCoolDowns(final List<MapleCoolDownValueHolder> cooldowns) {
        int time;
        if (cooldowns != null) {
            for (MapleCoolDownValueHolder cooldown : cooldowns) {
                coolDowns.put(cooldown.skillId, cooldown);
            }
        } else {
            try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
                PreparedStatement ps = con.prepareStatement("SELECT SkillID,StartTime,length FROM skills_cooldowns WHERE charid = ?");
                ps.setInt(1, getId());
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    if (rs.getLong("length") + rs.getLong("StartTime") - System.currentTimeMillis() <= 0) {
                        continue;
                    }
                    giveCoolDowns(rs.getInt("SkillID"), rs.getLong("StartTime"), rs.getLong("length"));
                }
                ps.close();
                rs.close();
                deleteWhereCharacterId(con, "DELETE FROM skills_cooldowns WHERE charid = ?");

            } catch (SQLException e) {
                System.err.println("Error while retriving cooldown from SQL storage");
                FileoutputUtil.outError("logs/数据库异常.txt", e);
            }
        }
    }

    public int getCooldownSize() {
        return coolDowns.size();
    }

    public int getDiseaseSize() {
        return diseases.size();
    }

    public List<MapleCoolDownValueHolder> getCooldowns() {
        List<MapleCoolDownValueHolder> ret = new ArrayList<MapleCoolDownValueHolder>();
        for (MapleCoolDownValueHolder mc : coolDowns.values()) {
            if (mc != null) {
                ret.add(mc);
            }
        }
        return ret;
    }

    public final List<MapleDiseaseValueHolder> getAllDiseases() {
        return new ArrayList<MapleDiseaseValueHolder>(diseases.values());
    }

    public final boolean hasDisease(final MapleDisease dis) {
        return diseases.containsKey(dis);
    }

    public void giveDebuff(final MapleDisease disease, MobSkill skill) {
        giveDebuff(disease, skill.getX(), skill.getDuration(), skill.getSkillId(), skill.getSkillLevel());
    }

    public void giveDebuff(final MapleDisease disease, int x, long duration, int skillid, int level) {
        if (map != null && !hasDisease(disease)) {
            if (!(disease == MapleDisease.SEDUCE || disease == MapleDisease.STUN || disease == MapleDisease.FLAG)) {
                if (getBuffedValue(MapleBuffStat.HOLY_SHIELD) != null) {
                    return;
                }
            }
            final int mC = getBuffSource(MapleBuffStat.MECH_CHANGE);
            if (mC > 0 && mC != 35121005) { //missile tank can have debuffs
                return; //flamethrower and siege can't
            }
            if (stats.ASR > 0 && Randomizer.nextInt(100) < stats.ASR) {
                return;
            }

            diseases.put(disease, new MapleDiseaseValueHolder(disease, System.currentTimeMillis(), duration - stats.decreaseDebuff));
            client.getSession().write(MaplePacketCreator.giveDebuff(disease, x, skillid, level, (int) duration));
            map.broadcastMessage(this, MaplePacketCreator.giveForeignDebuff(id, disease, skillid, level, x), false);

            //if (x > 0 && disease == MapleDisease.POISON) { //poison, subtract all HP
            //    addHP((int) -(x * ((duration - stats.decreaseDebuff) / 1000)));
            //}
        }
    }

    public final void giveSilentDebuff(final List<MapleDiseaseValueHolder> ld) {
        if (ld != null) {
            for (final MapleDiseaseValueHolder disease : ld) {
                diseases.put(disease.disease, disease);
            }
        }
    }

    public void dispelDebuff(MapleDisease debuff) {
        if (hasDisease(debuff)) {
            client.getSession().write(MaplePacketCreator.cancelDebuff(debuff));
            map.broadcastMessage(this, MaplePacketCreator.cancelForeignDebuff(id, debuff), false);

            diseases.remove(debuff);
        }
    }

    public void dispelDebuffs() {
        List<MapleDisease> diseasess = new ArrayList<MapleDisease>(diseases.keySet());
        for (MapleDisease d : diseasess) {
            dispelDebuff(d);
        }
    }

    public void cancelAllDebuffs() {
        diseases.clear();
    }

    public void setLevel(final short level) {
        this.level = (short) (level - 1);
    }

    public void sendNote(String to, String msg) {
        sendNote(to, msg, 0);
    }

    public void sendNote(String to, String msg, int fame) {
        MapleCharacterUtil.sendNote(to, getName(), msg, fame);
    }

    public void showNote() {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM notes WHERE `to`=?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ps.setString(1, getName());
            ResultSet rs = ps.executeQuery();
            rs.last();
            int count = rs.getRow();
            rs.first();
            client.getSession().write(CSPacket.showNotes(rs, count));
            rs.close();
            ps.close();
        } catch (SQLException e) {
            FileoutputUtil.outError("logs/数据库异常.txt", e);
            System.err.println("Unable to show note" + e);
        }
    }

    public void deleteNote(int id, int fame) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT gift FROM notes WHERE `id`=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getInt("gift") == fame && fame > 0) { //not exploited! hurray
                    addFame(fame);
                    updateSingleStat(MapleStat.FAME, getFame());
                    client.getSession().write(MaplePacketCreator.getShowFameGain(fame));
                }
            }
            rs.close();
            ps.close();
            ps = con.prepareStatement("DELETE FROM notes WHERE `id`=?");
            ps.setInt(1, id);
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            FileoutputUtil.outError("logs/数据库异常.txt", e);
            System.err.println("Unable to delete note" + e);
        }
    }

    public int getMulungEnergy() {
        return mulung_energy;
    }

    public void mulung_EnergyModify(boolean inc) {
        if (inc) {
            if (mulung_energy + 100 > 10000) {
                mulung_energy = 10000;
            } else {
                mulung_energy += 100;
            }
        } else {
            mulung_energy = 0;
        }
        client.getSession().write(MaplePacketCreator.MulungEnergy(mulung_energy));
    }

    public void writeMulungEnergy() {
        client.getSession().write(MaplePacketCreator.MulungEnergy(mulung_energy));
    }

    public void writeEnergy(String type, String inc) {
        client.getSession().write(MaplePacketCreator.sendPyramidEnergy(type, inc));
    }

    public void writeStatus(String type, String inc) {
        client.getSession().write(MaplePacketCreator.sendGhostStatus(type, inc));
    }

    public void writePoint(String type, String inc) {
        client.getSession().write(MaplePacketCreator.sendGhostPoint(type, inc));
    }

    public final short getCombo() {
        return combo;
    }

    public void setCombo(final short combo) {
        this.combo = combo;
    }

    public final long getLastCombo() {
        return lastCombo;
    }

    public void setLastCombo(final long combo) {
        this.lastCombo = combo;
    }

    public final long getKeyDownSkill_Time() {
        return keydown_skill;
    }

    public void setKeyDownSkill_Time(final long keydown_skill) {
        this.keydown_skill = keydown_skill;
    }

    public void checkBerserk() { //berserk is special in that it doesn't use worldtimer :)
        if (job != 132 || lastBerserkTime < 0 || lastBerserkTime + 10000 > System.currentTimeMillis()) {
            return;
        }
        final Skill BerserkX = SkillFactory.getSkill(1320006);
        final int skilllevel = getTotalSkillLevel(BerserkX);
        if (skilllevel >= 1 && map != null) {
            lastBerserkTime = System.currentTimeMillis();
            final MapleStatEffect ampStat = BerserkX.getEffect(skilllevel);
            stats.Berserk = stats.getHp() * 100 / stats.getCurrentMaxHp() >= ampStat.getX();
            client.getSession().write(MaplePacketCreator.showOwnBuffEffect(1320006, 1, getLevel(), skilllevel, (byte) (stats.Berserk ? 1 : 0)));
            map.broadcastMessage(this, MaplePacketCreator.showBuffeffect(getId(), 1320006, 1, getLevel(), skilllevel, (byte) (stats.Berserk ? 1 : 0)), false);
        } else {
            lastBerserkTime = -1;
        }
    }

    public void setChalkboard(String text) {
        this.chalktext = text;
        if (map != null) {
            map.broadcastMessage(CSPacket.useChalkboard(getId(), text));
        }
    }

    public String getChalkboard() {
        return chalktext;
    }

    public MapleMount getMount() {
        return mount;
    }

    public int[] getWishlist() {
        return wishlist;
    }

    public void clearWishlist() {
        for (int i = 0; i < 10; i++) {
            wishlist[i] = 0;
        }
        changed_wishlist = true;
    }

    public int getWishlistSize() {
        int ret = 0;
        for (int i = 0; i < 10; i++) {
            if (wishlist[i] > 0) {
                ret++;
            }
        }
        return ret;
    }

    public void setWishlist(int[] wl) {
        this.wishlist = wl;
        changed_wishlist = true;
    }

    public int[] getRocks() {
        return rocks;
    }

    public int getRockSize() {
        int ret = 0;
        for (int i = 0; i < 10; i++) {
            if (rocks[i] != 999999999) {
                ret++;
            }
        }
        return ret;
    }

    public void deleteFromRocks(int map) {
        for (int i = 0; i < 10; i++) {
            if (rocks[i] == map) {
                rocks[i] = 999999999;
                changed_trocklocations = true;
                break;
            }
        }
    }

    public void addRockMap() {
        if (getRockSize() >= 10) {
            return;
        }
        rocks[getRockSize()] = getMapId();
        changed_trocklocations = true;
    }

    public boolean isRockMap(int id) {
        for (int i = 0; i < 10; i++) {
            if (rocks[i] == id) {
                return true;
            }
        }
        return false;
    }

    public int[] getRegRocks() {
        return regrocks;
    }

    public int getRegRockSize() {
        int ret = 0;
        for (int i = 0; i < 5; i++) {
            if (regrocks[i] != 999999999) {
                ret++;
            }
        }
        return ret;
    }

    public void deleteFromRegRocks(int map) {
        for (int i = 0; i < 5; i++) {
            if (regrocks[i] == map) {
                regrocks[i] = 999999999;
                changed_regrocklocations = true;
                break;
            }
        }
    }

    public void addRegRockMap() {
        if (getRegRockSize() >= 5) {
            return;
        }
        regrocks[getRegRockSize()] = getMapId();
        changed_regrocklocations = true;
    }

    public boolean isRegRockMap(int id) {
        for (int i = 0; i < 5; i++) {
            if (regrocks[i] == id) {
                return true;
            }
        }
        return false;
    }

    public int[] getHyperRocks() {
        return hyperrocks;
    }

    public int getHyperRockSize() {
        int ret = 0;
        for (int i = 0; i < 13; i++) {
            if (hyperrocks[i] != 999999999) {
                ret++;
            }
        }
        return ret;
    }

    public void deleteFromHyperRocks(int map) {
        for (int i = 0; i < 13; i++) {
            if (hyperrocks[i] == map) {
                hyperrocks[i] = 999999999;
                changed_hyperrocklocations = true;
                break;
            }
        }
    }

    public void addHyperRockMap() {
        if (getRegRockSize() >= 13) {
            return;
        }
        hyperrocks[getHyperRockSize()] = getMapId();
        changed_hyperrocklocations = true;
    }

    public boolean isHyperRockMap(int id) {
        for (int i = 0; i < 13; i++) {
            if (hyperrocks[i] == id) {
                return true;
            }
        }
        return false;
    }

    public List<LifeMovementFragment> getLastRes() {
        return lastres;
    }

    public void setLastRes(List<LifeMovementFragment> lastres) {
        this.lastres = lastres;
    }

    public void dropMessage(int type, String message) {
        if (type == -1) {
            client.getSession().write(UIPacket.getTopMsg(message));
        } else if (type == -2) {
            client.getSession().write(PlayerShopPacket.shopChat(message, 0)); //0 or what
        } else if (type == -3) {
            client.getSession().write(MaplePacketCreator.getChatText(getId(), message, isSuperGM(), 0)); //1 = hide
        } else if (type == -4) {
            client.getSession().write(MaplePacketCreator.getChatText(getId(), message, isSuperGM(), 1)); //1 = hide
        } else if (type == -5) {
            client.getSession().write(MaplePacketCreator.spouseMessage(message, false)); //pink
        } else if (type == -6) {
            client.getSession().write(MaplePacketCreator.spouseMessage(message, true)); //white bg
        } else if (type == -7) {
            client.getSession().write(UIPacket.getMidMsg(message, false, 0));
        } else if (type == -8) {
            client.getSession().write(UIPacket.getMidMsg(message, true, 0));
        } else {
            client.getSession().write(MaplePacketCreator.serverNotice(type, message));
        }
    }

    public IMaplePlayerShop getPlayerShop() {
        return playerShop;
    }

    public void setPlayerShop(IMaplePlayerShop playerShop) {
        this.playerShop = playerShop;
    }

    public int getConversation() {
        return inst.get();
    }

    public void setConversation(int inst) {
        this.inst.set(inst);
    }

    public int getDirection() {
        return insd.get();
    }

    public void setDirection(int inst) {
        this.insd.set(inst);
    }

    public MapleCarnivalParty getCarnivalParty() {
        return carnivalParty;
    }

    public void setCarnivalParty(MapleCarnivalParty party) {
        carnivalParty = party;
    }

    public void addCP(int ammount) {
        totalCP += ammount;
        availableCP += ammount;
    }

    public void useCP(int ammount) {
        availableCP -= ammount;
    }

    public int getAvailableCP() {
        return availableCP;
    }

    public int getTotalCP() {
        return totalCP;
    }

    public void resetCP() {
        totalCP = 0;
        availableCP = 0;
    }

    public void addCarnivalRequest(MapleCarnivalChallenge request) {
        pendingCarnivalRequests.add(request);
    }

    public final MapleCarnivalChallenge getNextCarnivalRequest() {
        return pendingCarnivalRequests.pollLast();
    }

    public void clearCarnivalRequests() {
        pendingCarnivalRequests = new LinkedList<MapleCarnivalChallenge>();
    }

    public void startMonsterCarnival(final int enemyavailable, final int enemytotal) {
        client.getSession().write(MonsterCarnivalPacket.startMonsterCarnival(this, enemyavailable, enemytotal));
    }

    public void CPUpdate(final boolean party, final int available, final int total, final int team) {
        client.getSession().write(MonsterCarnivalPacket.CPUpdate(party, available, total, team));
    }

    public void playerDiedCPQ(final String name, final int lostCP, final int team) {
        client.getSession().write(MonsterCarnivalPacket.playerDiedMessage(name, lostCP, team));
    }

    public boolean getCanTalk() {
        return this.canTalk;
    }

    public void canTalk(boolean talk) {
        this.canTalk = talk;
    }

    public double getEXPMod() {
        return stats.expMod;
    }

    public int getDropMod() {
        return stats.dropMod;
    }

    public double getDropm() {
        return stats.dropm;
    }

    public int getCashMod() {
        return stats.cashMod;
    }

    //public void setPoints(int p) {
    //    this.points = p;
    //}
    //public int getPoints() {
    //    return points;
    //}
    public void setVPoints(int p) {
        this.vpoints = p;
    }

    public int getVPoints() {
        return vpoints;
    }

    public CashShop getCashInventory() {
        return cs;
    }

    public void removeItem(int id, int quantity) {
        MapleInventoryManipulator.removeById(client, GameConstants.getInventoryType(id), id, -quantity, true, false);
        client.getSession().write(MaplePacketCreator.getShowItemGain(id, (short) quantity, true));
    }

    public void removeAll(int id) {
        removeAll(id, true);
    }

    public void removeAll(int id, boolean show) {
        MapleInventoryType type = GameConstants.getInventoryType(id);
        int possessed = getInventory(type).countById(id);

        if (possessed > 0) {
            MapleInventoryManipulator.removeById(getClient(), type, id, possessed, true, false);
            if (show) {
                getClient().getSession().write(MaplePacketCreator.getShowItemGain(id, (short) -possessed, true));
            }
        }
    }

    public Triple<List<MapleRing>, List<MapleRing>, List<MapleRing>> getRings(boolean equip) {
        MapleInventory iv = getInventory(MapleInventoryType.EQUIPPED);
        List<Item> equipped = iv.newList();
        Collections.sort(equipped);
        List<MapleRing> crings = new ArrayList<>(), frings = new ArrayList<>(), mrings = new ArrayList<>();
        MapleRing ring;
        for (Item ite : equipped) {
            Equip item = (Equip) ite;
            if (item.getRing() != null) {
                ring = item.getRing();
                ring.setEquipped(true);
                if (GameConstants.isEffectRing(item.getItemId())) {
                    if (equip) {
                        if (GameConstants.isCrushRing(item.getItemId())) {
                            crings.add(ring);
                        } else if (GameConstants.isFriendshipRing(item.getItemId())) {
                            frings.add(ring);
                        } else if (GameConstants.isMarriageRing(item.getItemId())) {
                            mrings.add(ring);
                        }
                    } else if (crings.isEmpty() && GameConstants.isCrushRing(item.getItemId())) {
                        crings.add(ring);
                    } else if (frings.isEmpty() && GameConstants.isFriendshipRing(item.getItemId())) {
                        frings.add(ring);
                    } else if (mrings.isEmpty() && GameConstants.isMarriageRing(item.getItemId())) {
                        mrings.add(ring);
                    } //for 3rd person the actual slot doesnt matter, so we'll use this to have both shirt/ring same?
                    //however there seems to be something else behind this, will have to sniff someone with shirt and ring, or more conveniently 3-4 of those
                }
            }
        }
        if (equip) {
            iv = getInventory(MapleInventoryType.EQUIP);
            for (Item ite : iv.list()) {
                Equip item = (Equip) ite;
                //if (item.getRing() != null && GameConstants.isCrushRing(item.getItemId())) {
                if (item.getRing() != null && GameConstants.isEffectRing(item.getItemId())) {
                    ring = item.getRing();
                    ring.setEquipped(false);
                    if (GameConstants.isFriendshipRing(item.getItemId())) {
                        frings.add(ring);
                    } else if (GameConstants.isCrushRing(item.getItemId())) {
                        crings.add(ring);
                    } else if (GameConstants.isMarriageRing(item.getItemId())) {
                        mrings.add(ring);
                    }
                }
            }
        }
        Collections.sort(frings, new MapleRing.RingComparator());
        Collections.sort(crings, new MapleRing.RingComparator());
        Collections.sort(mrings, new MapleRing.RingComparator());
        return new Triple<List<MapleRing>, List<MapleRing>, List<MapleRing>>(crings, frings, mrings);
    }

    public int getFH() {
        MapleFoothold fh = getMap().getFootholds().findBelow(getTruePosition());
        if (fh != null) {
            return fh.getId();
        }
        return 0;
    }

    public void startFairySchedule(boolean exp) {
        startFairySchedule(exp, false);
    }

    public void startFairySchedule(boolean exp, boolean equipped) {
        cancelFairySchedule(exp || stats.equippedFairy == 0);
        if (fairyExp <= 0) {
            fairyExp = (byte) stats.equippedFairy;
        }
        if (equipped && fairyExp < stats.equippedFairy * 1/*3*/ && stats.equippedFairy > 0) {
            client.getSession().write(MaplePacketCreator.fairyPendantMessage(0, stats.equippedFairy));
        }
        lastFairyTime = System.currentTimeMillis();
    }

    public final boolean canFairy(long now) {
        return lastFairyTime > 0 && lastFairyTime + (60 * 60 * 1000) < now;
    }

    public final boolean canHP(long now) {
        if (lastHPTime + 5000 < now) {
            lastHPTime = now;
            return true;
        }
        return false;
    }

    public final boolean canMP(long now) {
        if (lastMPTime + 5000 < now) {
            lastMPTime = now;
            return true;
        }
        return false;
    }

    public final boolean canHPRecover(long now) {
        if (stats.hpRecoverTime > 0 && lastHPTime + stats.hpRecoverTime < now) {
            lastHPTime = now;
            return true;
        }
        return false;
    }

    public final boolean canMPRecover(long now) {
        if (stats.mpRecoverTime > 0 && lastMPTime + stats.mpRecoverTime < now) {
            lastMPTime = now;
            return true;
        }
        return false;
    }

    public void cancelFairySchedule(boolean exp) {
        lastFairyTime = 0;
        if (exp) {
            this.fairyExp = 0;
        }
    }

    public void doFairy() {
        if (fairyExp < stats.equippedFairy * 1/*3*/ && stats.equippedFairy > 0) {
            fairyExp += stats.equippedFairy;
            client.getSession().write(MaplePacketCreator.fairyPendantMessage((fairyExp -= stats.equippedFairy), fairyExp));
        }
        if (getGuildId() > 0) {
            World.Guild.gainGP(getGuildId(), 20, id);
            client.getSession().write(UIPacket.getGPContribution(20));
        }
        traits.get(MapleTraitType.will).addExp(5, this); //willpower every hour
        startFairySchedule(false, true);
    }

    public byte getFairyExp() {
        return fairyExp;
    }

    public int getTeam() {
        return coconutteam;
    }

    public void setTeam(int v) {
        this.coconutteam = v;
    }

    public void spawnPet(byte slot) {
        spawnPet(slot, false, true);
    }

    public void spawnPet(byte slot, boolean lead) {
        spawnPet(slot, lead, true);
    }

    public void spawnPet(byte slot, boolean lead, boolean broadcast) {
        final Item item = getInventory(MapleInventoryType.CASH).getItem(slot);
        if (item == null || item.getItemId() > 5010000 || item.getItemId() < 5000000) {
            return;
        }
        switch (item.getItemId()) {
            case 5000047:
            case 5000028: {
                final MaplePet pet = MaplePet.createPet(item.getItemId() + 1, MapleInventoryIdentifier.getInstance());
                if (pet != null) {
                    MapleInventoryManipulator.addById(client, item.getItemId() + 1, (short) 1, item.getOwner(), pet, 90, "Evolved from pet " + item.getItemId() + " on " + FileoutputUtil.CurrentReadable_Date());
                    MapleInventoryManipulator.removeFromSlot(client, MapleInventoryType.CASH, slot, (short) 1, false);
                }
                break;
            }
            default: {
                final MaplePet pet = item.getPet();
                if (pet != null && (item.getItemId() != 5000054 || pet.getSecondsLeft() > 0) && (item.getExpiration() == -1 || item.getExpiration() > System.currentTimeMillis())) {
                    if (pet.getSummoned()) { // Already summoned, let's keep it
                        unequipAllPets();
                    } else {
                        int leadid = 8;
                        if (GameConstants.isKOC(getJob())) {
                            leadid = 10000018;
                        } else if (GameConstants.isAran(getJob())) {
                            leadid = 20000024;
                        } else if (GameConstants.isEvan(getJob())) {
                            leadid = 20011024;
                        } else if (GameConstants.isMercedes(getJob())) {
                            leadid = 20021024;
                        } else if (GameConstants.isDemon(getJob())) {
                            leadid = 30011024;
                        } else if (GameConstants.isResist(getJob())) {
                            leadid = 30001024;
                            leadid = 8;
                            //} else if (GameConstants.isCannon(getJob())) {
                            //    leadid = 10008; //idk, TODO JUMP
                        }
                        if (getSkillLevel(SkillFactory.getSkill(leadid)) == 0 && getPet(0) != null) {
                            unequipAllPets();
                        } else if (lead && getSkillLevel(SkillFactory.getSkill(leadid)) > 0) { // Follow the Lead
                            //			    shiftPetsRight();
                        }
                        final Point pos = getPosition();
                        pet.setPos(pos);
                        try {
                            pet.setFh(getMap().getFootholds().findBelow(pos).getId());
                        } catch (NullPointerException e) {
                            pet.setFh(0); //lol, it can be fixed by movement
                        }
                        pet.setStance(0);
                        pet.setSummoned(getPetSlotNext() + 1);
                        if (getSkillLevel(pet.getBuffSkill()) == 0) {
                            pet.setBuffSkill(0);
                        }
                        addPet(pet);
                        if (/*broadcast && */getMap() != null) {

                            //getMap().broadcastMessage(this, PetPacket.showPet(this, pet, false, false), true);    
                            client.getSession().write(PetPacket.showPet(this, pet, false, false));
                            client.getSession().write(PetPacket.showPetUpdate(this, pet));
                            client.getSession().write(PetPacket.petStatUpdate(this));
                            client.getSession().write(PetPacket.updatePet(pet, getInventory(MapleInventoryType.CASH).getItem((short) (byte) pet.getInventoryPosition()), true));
                            marriage();
                            map.broadcastMessage(this, MaplePacketCreator.removePlayerFromMap(getId()), false);
                            map.broadcastMessage(this, MaplePacketCreator.spawnPlayerMapobject(this), false);
                        }
                    }
                }
                break;
            }
        }
        client.getSession().write(MaplePacketCreator.enableActions());
        marriage();
    }

    public void clearLinkMid() {
        linkMobs.clear();
        cancelEffectFromBuffStat(MapleBuffStat.HOMING_BEACON);
        cancelEffectFromBuffStat(MapleBuffStat.ARCANE_AIM);
    }

    public int getFirstLinkMid() {
        for (Integer lm : linkMobs.keySet()) {
            return lm.intValue();
        }
        return 0;
    }

    public Map<Integer, Integer> getAllLinkMid() {
        return linkMobs;
    }

    public void setLinkMid(int lm, int x) {
        linkMobs.put(lm, x);
    }

    public int getDamageIncrease(int lm) {
        if (linkMobs.containsKey(lm)) {
            return linkMobs.get(lm);
        }
        return 0;
    }

    public boolean isClone() {
        return clone;
    }

    public void setClone(boolean c) {
        this.clone = c;
    }

    public WeakReference<MapleCharacter>[] getClones() {
        return clones;
    }

    public MapleCharacter cloneLooks() {
        MapleClient cs = new MapleClient(null, null, new MapleSession(new MockIOSession()));

        final int minus = (getId() + Randomizer.nextInt(Integer.MAX_VALUE - getId())); // really randomize it, dont want it to fail

        MapleCharacter ret = new MapleCharacter(true);
        ret.id = minus;
        ret.client = cs;
        ret.exp = 0;
        ret.meso = 0;
        ret.todayOnlineTime = todayOnlineTime;
        ret.remainingAp = 0;
        ret.fame = 0;
        ret.accountid = client.getAccID();
        ret.anticheat = anticheat;
        ret.name = name;
        ret.level = level;
        ret.fame = fame;
        ret.job = job;
        ret.hair = hair;
        ret.face = face;
        ret.demonMarking = demonMarking;
        ret.skinColor = skinColor;
        ret.monsterbook = monsterbook;
        ret.mount = mount;
        ret.gmLevel = gmLevel;
        ret.gender = gender;
        ret.mapid = map.getId();
        ret.map = map;
        ret.setStance(getStance());
        ret.chair = chair;
        ret.itemEffect = itemEffect;
        ret.guildid = guildid;
        ret.currentrep = currentrep;
        ret.totalrep = totalrep;
        ret.stats = stats;
        ret.effects.putAll(effects);
        ret.dispelSummons();
        ret.guildrank = guildrank;
        ret.guildContribution = guildContribution;
        ret.allianceRank = allianceRank;
        ret.setPosition(getTruePosition());
        for (Item equip : getInventory(MapleInventoryType.EQUIPPED).newList()) {
            ret.getInventory(MapleInventoryType.EQUIPPED).addFromDB(equip.copy());
        }
        ret.skillMacros = skillMacros;
        ret.keylayout = keylayout;
        ret.questinfo = questinfo;
        ret.savedLocations = savedLocations;
        ret.wishlist = wishlist;
        ret.buddylist = buddylist;
        ret.keydown_skill = 0;
        ret.lastmonthfameids = lastmonthfameids;
        ret.lastfametime = lastfametime;
        ret.storage = storage;
        ret.cs = this.cs;
        ret.client.setAccountName(client.getAccountName());
        //ret.acash = acash;
        //ret.maplepoints = maplepoints;
        ret.clone = true;
        ret.client.setChannel(this.client.getChannel());
        while (map.getCharacterById(ret.id) != null || client.getChannelServer().getPlayerStorage().getCharacterById(ret.id) != null) {
            ret.id++;
        }
        ret.client.setPlayer(ret);
        return ret;
    }

    public final void cloneLook() {
        if (clone || inPVP()) {
            return;
        }
        for (int i = 0; i < clones.length; i++) {
            if (clones[i].get() == null) {
                final MapleCharacter newp = cloneLooks();
                map.addPlayer(newp);
                map.broadcastMessage(MaplePacketCreator.updateCharLook(newp));
                map.movePlayer(newp, getTruePosition());
                clones[i] = new WeakReference<>(newp);
                return;
            }
        }
    }

    public final void disposeClones() {
        numClones = 0;
        for (int i = 0; i < clones.length; i++) {
            if (clones[i].get() != null) {
                map.removePlayer(clones[i].get());
                if (clones[i].get().getClient() != null) {
                    clones[i].get().getClient().setPlayer(null);
                    clones[i].get().client = null;
                }
                clones[i] = new WeakReference<MapleCharacter>(null);
                numClones++;
            }
        }
    }

    public final int getCloneSize() {
        int z = 0;
        for (int i = 0; i < clones.length; i++) {
            if (clones[i].get() != null) {
                z++;
            }
        }
        return z;
    }

    public void spawnClones() {
        if (!isGM()) { //removed tetris piece likely, expired or whatever
            numClones = (byte) (stats.hasClone ? 1 : 0);
        }
        for (int i = 0; i < numClones; i++) {
            cloneLook();
        }
        numClones = 0;
    }

    public byte getNumClones() {
        return numClones;
    }

    public void setDragon(MapleDragon d) {
        this.dragon = d;
    }

    public MapleExtractor getExtractor() {
        return extractor;
    }

    public void setExtractor(MapleExtractor me) {
        removeExtractor();
        this.extractor = me;
    }

    public void removeExtractor() {
        if (extractor != null) {
            map.broadcastMessage(MaplePacketCreator.removeExtractor(this.id));
            map.removeMapObject(extractor);
            extractor = null;
        }
    }

    public final void spawnSavedPets() {
        for (int i = 0; i < petStore.length; i++) {
            if (petStore[i] > -1) {
                spawnPet(petStore[i], false, false);
            }
        }
        client.getSession().write(PetPacket.petStatUpdate(this));
        marriage();
        petStore = new byte[]{-1, -1, -1};
    }

    public final byte[] getPetStores() {
        return petStore;
    }

    public void resetStats(final int str, final int dex, final int int_, final int luk) {
        Map<MapleStat, Integer> stat = new EnumMap<MapleStat, Integer>(MapleStat.class);
        int total = stats.getStr() + stats.getDex() + stats.getLuk() + stats.getInt() + getRemainingAp();
        total -= str;
        stats.str = (short) str;

        total -= dex;
        stats.dex = (short) dex;

        total -= int_;
        stats.int_ = (short) int_;

        total -= luk;
        stats.luk = (short) luk;

        setRemainingAp((short) total);
        stats.recalcLocalStats(this);
        stat.put(MapleStat.STR, str);
        stat.put(MapleStat.DEX, dex);
        stat.put(MapleStat.INT, int_);
        stat.put(MapleStat.LUK, luk);
        stat.put(MapleStat.AVAILABLEAP, total);
        client.getSession().write(MaplePacketCreator.updatePlayerStats(stat, false, getJob()));
        marriage();
    }

    public Event_PyramidSubway getPyramidSubway() {
        return pyramidSubway;
    }

    public void setPyramidSubway(Event_PyramidSubway ps) {
        this.pyramidSubway = ps;
    }

    public byte getSubcategory() {
        if (job >= 430 && job <= 434) {
            return 1; //dont set it
        }
        if (GameConstants.isCannon(job) || job == 1) {
            return 2;
        }
        if (job != 0 && job != 400) {
            return 0;
        }
        return subcategory;
    }

    public void setSubcategory(int z) {
        this.subcategory = (byte) z;
    }

    public int itemQuantity(final int itemid) {
        return getInventory(GameConstants.getInventoryType(itemid)).countById(itemid);
    }

    public void setRPS(RockPaperScissors rps) {
        this.rps = rps;
    }

    public RockPaperScissors getRPS() {
        return rps;
    }

    public long getNextConsume() {
        return nextConsume;
    }

    public void setNextConsume(long nc) {
        this.nextConsume = nc;
    }

    public int getRank() {
        return rank;
    }

    public int getRankMove() {
        return rankMove;
    }

    public int getJobRank() {
        return jobRank;
    }

    public int getJobRankMove() {
        return jobRankMove;
    }

    public void dispelBuff() {
        final LinkedList<Entry<MapleBuffStat, MapleBuffStatValueHolder>> allBuffs = new LinkedList<>(effects.entrySet());
        for (Entry<MapleBuffStat, MapleBuffStatValueHolder> mbsvh : allBuffs) {
            if (((mbsvh.getValue().startTime + mbsvh.getValue().localDuration) - System.currentTimeMillis()) < 8000) {
                dispelBuff(mbsvh.getValue().skillid);
            }
        }
    }

    public void changeChannel(final int channel) {
        final ChannelServer toch = ChannelServer.getInstance(channel);

        if (channel == client.getChannel() || toch == null || toch.isShutdown()) {
            client.getSession().write(MaplePacketCreator.serverBlocked(1));
            return;
        }
        changeRemoval();
        dispelBuff();
        /*if (getBuffTimeCout(2450000) > 0) {
            long startTime = getBuffTimeStartTime(2450000);
            int length = getBuffTimeLength(2450000);
            if (startTime + length - System.currentTimeMillis() > 3000) {
                updateBuffTime(2450000, (int) (startTime + length - System.currentTimeMillis()));
            } else {
                updateBuffTime(2450000, 0);
            }
        }
        if (getBuffTimeCout(2450010) > 0) {
            long startTime = getBuffTimeStartTime(2450010);
            int length = getBuffTimeLength(2450010);
            if (startTime + length - System.currentTimeMillis() > 3000) {
                updateBuffTime(2450010, (int) (startTime + length - System.currentTimeMillis()));
            } else {
                updateBuffTime(2450010, 0);
            }
        }*/

        final ChannelServer ch = ChannelServer.getInstance(client.getChannel());
        if (getMessenger() != null) {
            World.Messenger.silentLeaveMessenger(getMessenger().getId(), new MapleMessengerCharacter(this));
        }
        PlayerBuffStorage.addBuffsToStorage(getId(), getAllBuffs());
        PlayerBuffStorage.addCooldownsToStorage(getId(), getCooldowns());
        PlayerBuffStorage.addDiseaseToStorage(getId(), getAllDiseases());
        World.ChannelChange_Data(new CharacterTransfer(this), getId(), channel);
        try {
            ch.removePlayer(this);
        } catch (Exception ignore) {
            FileoutputUtil.outError("logs/移除角色容器异常.txt", ignore);
        }
        if (!LoginServer.CanLoginKey(this.getLoginKey(), this.getAccountID()) || (LoginServer.getLoginKey(this.getAccountID()) == null && !this.getLoginKey().isEmpty())) {
            FileoutputUtil.logToFile("logs/Data/客戶端登录KEY异常.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + client.getSession().getRemoteAddress().toString().split(":")[0] + " 账号: " + client.getAccountName() + " 客戶端key：" + LoginServer.getLoginKey(this.getAccountID()) + " 伺服端key：" + this.getLoginKey() + " 更换頻道10");
            World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 聊天系統] 非法更换頻道 账号 " + client.getAccountName()));
            client.getSession().close();
            return;
        }
        if (!LoginServer.CanServerKey(this.getServerKey(), this.getAccountID()) || (LoginServer.getServerKey(this.getAccountID()) == null && !this.getServerKey().isEmpty())) {
            FileoutputUtil.logToFile("logs/Data/客戶端頻道KEY异常.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + client.getSession().getRemoteAddress().toString().split(":")[0] + " 账号: " + client.getAccountName() + " 客戶端key：" + LoginServer.getServerKey(this.getAccountID()) + " 伺服端key：" + this.getServerKey() + " 更换頻道11");
            World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 聊天系統] 非法更换頻道 账号 " + client.getAccountName()));
            client.getSession().close();
            return;
        }
        if (!LoginServer.CanClientKey(this.getClientKey(), this.getAccountID()) || (LoginServer.getClientKey(this.getAccountID()) == null && !this.getClientKey().isEmpty())) {
            FileoutputUtil.logToFile("logs/Data/客戶端进入KEY异常.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + client.getSession().getRemoteAddress().toString().split(":")[0] + " 账号: " + client.getAccountName() + " 客戶端key：" + LoginServer.getClientKey(this.getAccountID()) + " 伺服端key：" + this.getClientKey() + " 更换頻道12");
            World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 聊天系統] 非法更换頻道 账号 " + client.getAccountName()));
            client.getSession().close();
            return;
        }

        client.updateLoginState(MapleClient.CHANGE_CHANNEL, client.getSessionIPAddress());
        final String s = client.getSessionIPAddress();
        byte[] ip = ServerConstants.Gateway_IP;
        //try {
        //    ip = InetAddress.getByName(ChannelServer.getInstance(client.getChannel()).getIP().split(":")[0]).getAddress();
        //} catch (Exception ex) {
        //}
        LoginServer.addIPAuth(s.substring(s.indexOf('/') + 1, s.length()));
        client.getSession().write(MaplePacketCreator.getChannelChange(client, ip, Integer.parseInt(toch.getIP().split(":")[1])));
        if (!LoginServer.CanLoginKey(this.getLoginKey(), this.getAccountID()) || (LoginServer.getLoginKey(this.getAccountID()) == null && !this.getLoginKey().isEmpty())) {
            FileoutputUtil.logToFile("logs/Data/客戶端登录KEY异常.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + client.getSession().getRemoteAddress().toString().split(":")[0] + " 账号: " + client.getAccountName() + " 客戶端key：" + LoginServer.getLoginKey(this.getAccountID()) + " 伺服端key：" + this.getLoginKey() + " 更换頻道13");
            World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 聊天系統] 非法更换頻道 账号 " + client.getAccountName()));
            client.getSession().close();
            return;
        }
        if (!LoginServer.CanServerKey(this.getServerKey(), this.getAccountID()) || (LoginServer.getServerKey(this.getAccountID()) == null && !this.getServerKey().isEmpty())) {
            FileoutputUtil.logToFile("logs/Data/客戶端頻道KEY异常.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + client.getSession().getRemoteAddress().toString().split(":")[0] + " 账号: " + client.getAccountName() + " 客戶端key：" + LoginServer.getServerKey(this.getAccountID()) + " 伺服端key：" + this.getServerKey() + " 更换頻道14");
            World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 聊天系統] 非法更换頻道 账号 " + client.getAccountName()));
            client.getSession().close();
            return;
        }
        if (!LoginServer.CanClientKey(this.getClientKey(), this.getAccountID()) || (LoginServer.getClientKey(this.getAccountID()) == null && !this.getClientKey().isEmpty())) {
            FileoutputUtil.logToFile("logs/Data/客戶端进入KEY异常.txt", "\r\n " + FileoutputUtil.NowTime() + " IP: " + client.getSession().getRemoteAddress().toString().split(":")[0] + " 账号: " + client.getAccountName() + " 客戶端key：" + LoginServer.getClientKey(this.getAccountID()) + " 伺服端key：" + this.getClientKey() + " 更换頻道15");
            World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 聊天系統] 非法更换頻道 账号 " + client.getAccountName()));
            client.getSession().close();
            return;
        }
        saveToDB(false, false);
        getMap().removePlayer(this);
        client.setPlayer(null);
        client.setReceiving(false);
    }

    public void expandInventory(byte type, int amount) {
        final MapleInventory inv = getInventory(MapleInventoryType.getByType(type));
        inv.addSlot((byte) amount);
        client.getSession().write(MaplePacketCreator.getSlotUpdate(type, (byte) inv.getSlotLimit()));
    }

    public boolean allowedToTarget(MapleCharacter other) {
        return other != null && (!other.isHidden() || getGMLevel() >= other.getGMLevel());
    }

    public int getFollowId() {
        return followid;
    }

    public void setFollowId(int fi) {
        this.followid = fi;
        if (fi == 0) {
            this.followinitiator = false;
            this.followon = false;
        }
    }

    public void setFollowInitiator(boolean fi) {
        this.followinitiator = fi;
    }

    public void setFollowOn(boolean fi) {
        this.followon = fi;
    }

    public boolean isFollowOn() {
        return followon;
    }

    public boolean isFollowInitiator() {
        return followinitiator;
    }

    public void checkFollow() {
        if (followid <= 0) {
            return;
        }
        if (followon) {
            map.broadcastMessage(MaplePacketCreator.followEffect(id, 0, null));
            map.broadcastMessage(MaplePacketCreator.followEffect(followid, 0, null));
        }
        MapleCharacter tt = map.getCharacterById(followid);
        client.getSession().write(MaplePacketCreator.getFollowMessage("跟隨取消."));
        if (tt != null) {
            tt.setFollowId(0);
            tt.getClient().getSession().write(MaplePacketCreator.getFollowMessage("跟隨取消."));
        }
        setFollowId(0);
    }

    public int getMarriageId() {
        return marriageId;
    }

    public void setMarriageId(final int mi) {
        this.marriageId = mi;
    }

    public int getMarriageItemId() {
        return marriageItemId;
    }

    public void setMarriageItemId(final int mi) {
        this.marriageItemId = mi;
    }

    public boolean isStaff() {
        return this.gmLevel >= ServerConstants.PlayerGMRank.INTERN.getLevel();
    }

    public boolean isDonator() {
        return this.gmLevel >= ServerConstants.PlayerGMRank.DONATOR.getLevel();
    }

    public boolean startPartyQuest(final int questid) {
        boolean ret = false;
        MapleQuest q = MapleQuest.getInstance(questid);
        if (q == null || !q.isPartyQuest()) {
            return false;
        }
        if (!quests.containsKey(q) || !questinfo.containsKey(questid)) {
            final MapleQuestStatus status = getQuestNAdd(q);
            status.setStatus((byte) 1);
            updateQuest(status);
            switch (questid) {
                case 1300:
                case 1301:
                case 1302: //carnival, ariants.
                    updateInfoQuest(questid, "min=0;sec=0;date=0000-00-00;have=0;rank=F;try=0;cmp=0;CR=0;VR=0;gvup=0;vic=0;lose=0;draw=0");
                    break;
                case 1303: //ghost pq
                    updateInfoQuest(questid, "min=0;sec=0;date=0000-00-00;have=0;have1=0;rank=F;try=0;cmp=0;CR=0;VR=0;vic=0;lose=0");
                    break;
                case 1204: //herb town pq
                    updateInfoQuest(questid, "min=0;sec=0;date=0000-00-00;have0=0;have1=0;have2=0;have3=0;rank=F;try=0;cmp=0;CR=0;VR=0");
                    break;
                case 1206: //ellin pq
                    updateInfoQuest(questid, "min=0;sec=0;date=0000-00-00;have0=0;have1=0;rank=F;try=0;cmp=0;CR=0;VR=0");
                    break;
                default:
                    updateInfoQuest(questid, "min=0;sec=0;date=0000-00-00;have=0;rank=F;try=0;cmp=0;CR=0;VR=0");
                    break;
            }
            ret = true;
        } //started the quest.
        return ret;
    }

    public String getOneInfo(final int questid, final String key) {
        if (!questinfo.containsKey(questid) || key == null || MapleQuest.getInstance(questid) == null || !MapleQuest.getInstance(questid).isPartyQuest()) {
            return null;
        }
        final String[] split = questinfo.get(questid).split(";");
        for (String x : split) {
            final String[] split2 = x.split("="); //should be only 2
            if (split2.length == 2 && split2[0].equals(key)) {
                return split2[1];
            }
        }
        return null;
    }

    public void updateOneInfo(final int questid, final String key, final String value) {
        if (!questinfo.containsKey(questid) || key == null || value == null || MapleQuest.getInstance(questid) == null || !MapleQuest.getInstance(questid).isPartyQuest()) {
            return;
        }
        final String[] split = questinfo.get(questid).split(";");
        boolean changed = false;
        final StringBuilder newQuest = new StringBuilder();
        for (String x : split) {
            final String[] split2 = x.split("="); //should be only 2
            if (split2.length != 2) {
                continue;
            }
            if (split2[0].equals(key)) {
                newQuest.append(key).append("=").append(value);
            } else {
                newQuest.append(x);
            }
            newQuest.append(";");
            changed = true;
        }

        updateInfoQuest(questid, changed ? newQuest.toString().substring(0, newQuest.toString().length() - 1) : newQuest.toString());
    }

    public void recalcPartyQuestRank(final int questid) {
        if (MapleQuest.getInstance(questid) == null || !MapleQuest.getInstance(questid).isPartyQuest()) {
            return;
        }
        if (!startPartyQuest(questid)) {
            final String oldRank = getOneInfo(questid, "rank");
            if (oldRank == null || oldRank.equals("S")) {
                return;
            }
            String newRank = null;
            if (oldRank.equals("A")) {
                newRank = "S";
            } else if (oldRank.equals("B")) {
                newRank = "A";
            } else if (oldRank.equals("C")) {
                newRank = "B";
            } else if (oldRank.equals("D")) {
                newRank = "C";
            } else if (oldRank.equals("F")) {
                newRank = "D";
            } else {
                return;
            }
            final List<Pair<String, Pair<String, Integer>>> questInfo = MapleQuest.getInstance(questid).getInfoByRank(newRank);
            if (questInfo == null) {
                return;
            }
            for (Pair<String, Pair<String, Integer>> q : questInfo) {
                boolean found = false;
                final String val = getOneInfo(questid, q.right.left);
                if (val == null) {
                    return;
                }
                int vall = 0;
                try {
                    vall = Integer.parseInt(val);
                } catch (NumberFormatException e) {
                    return;
                }
                if (q.left.equals("less")) {
                    found = vall < q.right.right;
                } else if (q.left.equals("more")) {
                    found = vall > q.right.right;
                } else if (q.left.equals("equal")) {
                    found = vall == q.right.right;
                }
                if (!found) {
                    return;
                }
            }
            updateOneInfo(questid, "rank", newRank);
        }
    }

    public void tryPartyQuest(final int questid) {
        if (MapleQuest.getInstance(questid) == null || !MapleQuest.getInstance(questid).isPartyQuest()) {
            return;
        }
        try {
            startPartyQuest(questid);
            pqStartTime = System.currentTimeMillis();
            updateOneInfo(questid, "try", String.valueOf(Integer.parseInt(getOneInfo(questid, "try")) + 1));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("tryPartyQuest error");
        }
    }

    public void endPartyQuest(final int questid) {
        if (MapleQuest.getInstance(questid) == null || !MapleQuest.getInstance(questid).isPartyQuest()) {
            return;
        }
        try {
            startPartyQuest(questid);
            if (pqStartTime > 0) {
                final long changeTime = System.currentTimeMillis() - pqStartTime;
                final int mins = (int) (changeTime / 1000 / 60), secs = (int) (changeTime / 1000 % 60);
                final int mins2 = Integer.parseInt(getOneInfo(questid, "min"));
                if (mins2 <= 0 || mins < mins2) {
                    updateOneInfo(questid, "min", String.valueOf(mins));
                    updateOneInfo(questid, "sec", String.valueOf(secs));
                    updateOneInfo(questid, "date", FileoutputUtil.CurrentReadable_Date());
                }
                final int newCmp = Integer.parseInt(getOneInfo(questid, "cmp")) + 1;
                updateOneInfo(questid, "cmp", String.valueOf(newCmp));
                updateOneInfo(questid, "CR", String.valueOf((int) Math.ceil((newCmp * 100.0) / Integer.parseInt(getOneInfo(questid, "try")))));
                recalcPartyQuestRank(questid);
                pqStartTime = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("endPartyQuest error");
        }

    }

    public void havePartyQuest(final int itemId) {
        int questid = 0, index = -1;
        switch (itemId) {
            case 1002798:
                questid = 1200; //henesys
                break;
            case 1072369:
                questid = 1201; //kerning
                break;
            case 1022073:
                questid = 1202; //ludi
                break;
            case 1082232:
                questid = 1203; //orbis
                break;
            case 1002571:
            case 1002572:
            case 1002573:
            case 1002574:
                questid = 1204; //herbtown
                index = itemId - 1002571;
                break;
            case 1102226:
                questid = 1303; //ghost
                break;
            case 1102227:
                questid = 1303; //ghost
                index = 0;
                break;
            case 1122010:
                questid = 1205; //magatia
                break;
            case 1032061:
            case 1032060:
                questid = 1206; //ellin
                index = itemId - 1032060;
                break;
            case 3010018:
                questid = 1300; //ariant
                break;
            case 1122007:
                questid = 1301; //carnival
                break;
            case 1122058:
                questid = 1302; //carnival2
                break;
            default:
                return;
        }
        if (MapleQuest.getInstance(questid) == null || !MapleQuest.getInstance(questid).isPartyQuest()) {
            return;
        }
        startPartyQuest(questid);
        updateOneInfo(questid, "have" + (index == -1 ? "" : index), "1");
    }

    public void resetStatsByJob(boolean beginnerJob) {
        int baseJob = (beginnerJob ? (job % 1000) : (((job % 1000) / 100) * 100)); //1112 -> 112 -> 1 -> 100
        boolean UA = getQuestNoAdd(MapleQuest.getInstance(GameConstants.ULT_EXPLORER)) != null;
        if (baseJob == 100) { //first job = warrior
            resetStats(UA ? 4 : 35, 4, 4, 4);
        } else if (baseJob == 200) {
            resetStats(4, 4, UA ? 4 : 20, 4);
        } else if (baseJob == 300 || baseJob == 400) {
            resetStats(4, UA ? 4 : 25, 4, 4);
        } else if (baseJob == 500) {
            resetStats(4, UA ? 4 : 20, 4, 4);
        } else if (baseJob == 0) {
            resetStats(4, 4, 4, 4);
        }
    }

    public boolean hasSummon() {
        return hasSummon;
    }

    public void setHasSummon(boolean summ) {
        this.hasSummon = summ;
    }

    public void removeDoor() {
        final MapleDoor door = getDoors().iterator().next();
        for (final MapleCharacter chr : door.getTarget().getCharactersThreadsafe()) {
            door.sendDestroyData(chr.getClient());
        }
        for (final MapleCharacter chr : door.getTown().getCharactersThreadsafe()) {
            door.sendDestroyData(chr.getClient());
        }
        for (final MapleDoor destroyDoor : getDoors()) {
            door.getTarget().removeMapObject(destroyDoor);
            door.getTown().removeMapObject(destroyDoor);
        }
        clearDoors();
    }

    public void removeMechDoor() {
        for (final MechDoor destroyDoor : getMechDoors()) {
            for (final MapleCharacter chr : getMap().getCharactersThreadsafe()) {
                destroyDoor.sendDestroyData(chr.getClient());
            }
            getMap().removeMapObject(destroyDoor);
        }
        clearMechDoors();
    }

    public void changeRemoval() {
        changeRemoval(false);
    }

    public void changeRemoval(boolean dc) {
        if (getCheatTracker() != null && dc) {
            getCheatTracker().dispose();
        }
        removeFamiliar();
        dispelSummons();
        if (!dc) {
            cancelEffectFromBuffStat(MapleBuffStat.CONVERSION);
            cancelEffectFromBuffStat(MapleBuffStat.SOARING);
            cancelEffectFromBuffStat(MapleBuffStat.MONSTER_RIDING);
            cancelEffectFromBuffStat(MapleBuffStat.MECH_CHANGE);
            cancelEffectFromBuffStat(MapleBuffStat.RECOVERY);
        }
        if (getPyramidSubway() != null) {
            getPyramidSubway().dispose(this);
        }
        if (playerShop != null && !dc) {
            playerShop.removeVisitor(this);
            if (playerShop.isOwner(this)) {
                playerShop.setOpen(true);
            }
        }
        if (!getDoors().isEmpty()) {
            removeDoor();
        }
        if (!getMechDoors().isEmpty()) {
            removeMechDoor();
        }
        disposeClones();
        NPCScriptManager.getInstance().dispose(client);
        cancelFairySchedule(false);
    }

    public void updateTick(int newTick) {
        anticheat.updateTick(newTick);
    }

    public boolean canUseFamilyBuff(MapleFamilyBuff buff) {
        final MapleQuestStatus stat = getQuestNoAdd(MapleQuest.getInstance(buff.questID));
        if (stat == null) {
            return true;
        }
        if (stat.getCustomData() == null) {
            stat.setCustomData("0");
        }
        return Long.parseLong(stat.getCustomData()) + (24 * 3600000) < System.currentTimeMillis();
    }

    public void useFamilyBuff(MapleFamilyBuff buff) {
        final MapleQuestStatus stat = getQuestNAdd(MapleQuest.getInstance(buff.questID));
        stat.setCustomData(String.valueOf(System.currentTimeMillis()));
    }

    public List<Integer> usedBuffs() {
        List<Integer> used = new ArrayList<Integer>();
        MapleFamilyBuff[] z = MapleFamilyBuff.values();
        for (int i = 0; i < z.length; i++) {
            if (!canUseFamilyBuff(z[i])) {
                used.add(i);
            }
        }
        return used;
    }

    public String getTeleportName() {
        return teleportname;
    }

    public void setTeleportName(final String tname) {
        teleportname = tname;
    }

    public int getNoJuniors() {
        if (mfc == null) {
            return 0;
        }
        return mfc.getNoJuniors();
    }

    public MapleFamilyCharacter getMFC() {
        return mfc;
    }

    public void makeMFC(final int familyid, final int seniorid, final int junior1, final int junior2) {
        if (familyid > 0) {
            MapleFamily f = World.Family.getFamily(familyid);
            if (f == null) {
                mfc = null;
            } else {
                mfc = f.getMFC(id);
                if (mfc == null) {
                    mfc = f.addFamilyMemberInfo(this, seniorid, junior1, junior2);
                }
                if (mfc.getSeniorId() != seniorid) {
                    mfc.setSeniorId(seniorid);
                }
                if (mfc.getJunior1() != junior1) {
                    mfc.setJunior1(junior1);
                }
                if (mfc.getJunior2() != junior2) {
                    mfc.setJunior2(junior2);
                }
            }
        } else {
            mfc = null;
        }
    }

    public void setFamily(final int newf, final int news, final int newj1, final int newj2) {
        if (mfc == null || newf != mfc.getFamilyId() || news != mfc.getSeniorId() || newj1 != mfc.getJunior1() || newj2 != mfc.getJunior2()) {
            makeMFC(newf, news, newj1, newj2);
        }
    }

    public int maxBattleshipHP(int skillid) {
        return (getTotalSkillLevel(skillid) * 5000) + ((getLevel() - 120) * 3000);
    }

    public int currentBattleshipHP() {
        return battleshipHP;
    }

    public void setBattleshipHP(int v) {
        this.battleshipHP = v;
    }

    public void decreaseBattleshipHP() {
        this.battleshipHP--;
    }

    public int getGachExp() {
        return gachexp;
    }

    public void setGachExp(int ge) {
        this.gachexp = ge;
    }

    public boolean isInBlockedMap() {
        if (!isAlive() || getPyramidSubway() != null || getMap().getSquadByMap() != null || getEventInstance() != null || getMap().getEMByMap() != null) {
            return true;
        }
        if ((getMapId() >= 680000210 && getMapId() <= 680000502) || (getMapId() / 10000 == 92502 && getMapId() >= 925020100) || (getMapId() / 10000 == 92503) || getMapId() == GameConstants.JAIL) {
            return true;
        }
        for (int i : GameConstants.blockedMaps) {
            if (getMapId() == i) {
                return true;
            }
        }
        return false;
    }

    public boolean isInTownMap() {
        if (hasBlockedInventory() || !getMap().isTown() || FieldLimitType.VipRock.check(getMap().getFieldLimit()) || getEventInstance() != null) {
            return false;
        }
        for (int i : GameConstants.blockedMaps) {
            if (getMapId() == i) {
                return false;
            }
        }
        return true;
    }

    public boolean hasBlockedInventory() {
        return !isAlive() || getTrade() != null || getConversation() > 0 || getDirection() >= 0 || getPlayerShop() != null || map == null;
    }

    public void startPartySearch(final List<Integer> jobs, final int maxLevel, final int minLevel, final int membersNeeded) {
        for (MapleCharacter chr : map.getCharacters()) {
            if (chr.getId() != id && chr.getParty() == null && chr.getLevel() >= minLevel && chr.getLevel() <= maxLevel && (jobs.isEmpty() || jobs.contains(Integer.valueOf(chr.getJob()))) && (isGM() || !chr.isGM())) {
                if (party != null && party.getMembers().size() < 6 && party.getMembers().size() < membersNeeded) {
                    chr.setParty(party);
                    World.Party.updateParty(party.getId(), PartyOperation.JOIN, new MaplePartyCharacter(chr));
                    chr.receivePartyMemberHP();
                    chr.updatePartyMemberHP();
                } else {
                    break;
                }
            }
        }
    }

    public int getChallenge() {
        return challenge;
    }

    public void setChallenge(int c) {
        this.challenge = c;
    }

    public short getFatigue() {
        if (this.fatigue < 0) {
            fatigue = 0;
        }
        if (this.fatigue > 100) {
            fatigue = 100;
        }
        return fatigue;
    }

    public void setFatigue(int j) {
        if (j > 100) {
            j = 100;
        }
        if (j < 0) {
            j = 0;
        }
        fatigue = (short) j;
        if (fatigue < 0) {
            fatigue = 0;
        }

        if (fatigue > 100) {
            fatigue = 100;
        }

        updateSingleStat(MapleStat.FATIGUE, fatigue);
    }

    public void fakeRelog() {
        client.getSession().write(MaplePacketCreator.getCharInfo(this));
        final MapleMap mapp = getMap();
        mapp.setCheckStates(false);
        mapp.removePlayer(this);
        mapp.addPlayer(this);
        mapp.setCheckStates(true);

        if (GameConstants.GMS) {
            client.getSession().write(MaplePacketCreator.getFamiliarInfo(this));
        }
    }

    public boolean canSummon() {
        return canSummon(5000);
    }

    public boolean canSummon(int g) {
        if (lastSummonTime + g < System.currentTimeMillis()) {
            lastSummonTime = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public int getIntNoRecord(int questID) {
        final MapleQuestStatus stat = getQuestNoAdd(MapleQuest.getInstance(questID));
        if (stat == null || stat.getCustomData() == null) {
            return 0;
        }
        return Integer.parseInt(stat.getCustomData());
    }

    public int getIntRecord(int questID) {
        final MapleQuestStatus stat = getQuestNAdd(MapleQuest.getInstance(questID));
        if (stat.getCustomData() == null) {
            stat.setCustomData("0");
        }
        return Integer.parseInt(stat.getCustomData());
    }

    public void updatePetAuto() {
        if (getIntNoRecord(GameConstants.HP_ITEM) > 0) {
            client.getSession().write(MaplePacketCreator.petAutoHP(getIntRecord(GameConstants.HP_ITEM)));
        }
        if (getIntNoRecord(GameConstants.MP_ITEM) > 0) {
            client.getSession().write(MaplePacketCreator.petAutoMP(getIntRecord(GameConstants.MP_ITEM)));
        }
        if (getIntNoRecord(GameConstants.BUFF_ITEM) > 0) {
            client.getSession().write(MaplePacketCreator.petAutoBuff(getIntRecord(GameConstants.BUFF_ITEM)));
        }
    }

    public void sendEnglishQuiz(String msg) {
        client.getSession().write(MaplePacketCreator.englishQuizMsg(msg));
    }

    public void setChangeTime(boolean changeMap) {
        this.mapChangeTime = System.currentTimeMillis();
        if (changeMap) {
            getCheatTracker().resetInMapIimeCount();
        }
    }

    public long getChangeTime() {
        return mapChangeTime;
    }

    public Map<ReportType, Integer> getReports() {
        return reports;
    }

    public void addReport(ReportType type) {
        Integer value = reports.get(type);
        reports.put(type, value == null ? 1 : (value + 1));
        changed_reports = true;
    }

    public void clearReports(ReportType type) {
        reports.remove(type);
        changed_reports = true;
    }

    public void clearReports() {
        reports.clear();
        changed_reports = true;
    }

    public final int getReportPoints() {
        int ret = 0;
        for (Integer entry : reports.values()) {
            ret += entry;
        }
        return ret;
    }

    public final String getReportSummary() {
        final StringBuilder ret = new StringBuilder();
        final List<Pair<ReportType, Integer>> offenseList = new ArrayList<Pair<ReportType, Integer>>();
        for (final Entry<ReportType, Integer> entry : reports.entrySet()) {
            offenseList.add(new Pair<ReportType, Integer>(entry.getKey(), entry.getValue()));
        }
        Collections.sort(offenseList, new Comparator<Pair<ReportType, Integer>>() {

            @Override
            public final int compare(final Pair<ReportType, Integer> o1, final Pair<ReportType, Integer> o2) {
                final int thisVal = o1.getRight();
                final int anotherVal = o2.getRight();
                return (thisVal < anotherVal ? 1 : (thisVal == anotherVal ? 0 : -1));
            }
        });
        for (int x = 0; x < offenseList.size(); x++) {
            ret.append(StringUtil.makeEnumHumanReadable(offenseList.get(x).left.name()));
            ret.append(": ");
            ret.append(offenseList.get(x).right);
            ret.append(" ");
        }
        return ret.toString();
    }

    public short getScrolledPosition() {
        return scrolledPosition;
    }

    public void setScrolledPosition(short s) {
        this.scrolledPosition = s;
    }

    public MapleTrait getTrait(MapleTraitType t) {
        return traits.get(t);
    }

    public void forceCompleteQuest(int id) {
        MapleQuest.getInstance(id).forceComplete(this, 9270035); //troll
    }

    public List<Integer> getExtendedSlots() {
        return extendedSlots;
    }

    public int getExtendedSlot(int index) {
        if (extendedSlots.size() <= index || index < 0) {
            return -1;
        }
        return extendedSlots.get(index);
    }

    public void changedExtended() {
        changed_extendedSlots = true;
    }

    public MapleAndroid getAndroid() {
        return android;
    }

    public void removeAndroid() {
        if (map != null) {
            map.broadcastMessage(MaplePacketCreator.deactivateAndroid(this.id));
        }
        android = null;
    }

    /*public void updateAndroid(int size, int itemId) {
        if (map != null) {
            map.broadcastMessage(MaplePacketCreator.updateAndroidLook(this.getId(), size, itemId));
        }
    }*/
    public void updateAndroid() {
        if (map != null) {
            map.broadcastMessage(MaplePacketCreator.updateAndroidLook(true, this, getAndroid()));
        }
    }

    public boolean checkHearts() { // should check for android and hearts
        if (getInventory(MapleInventoryType.EQUIPPED).getItem((byte) -35) != null) {
            return true;
        }
        return false;
    }

    public void setAndroid(MapleAndroid a) {
        if (checkHearts()) {
            this.android = a;
            if (map != null && a != null) {
                map.broadcastMessage(MaplePacketCreator.spawnAndroid(this, a));
                map.broadcastMessage(MaplePacketCreator.showAndroidEmotion(this.getId(), Randomizer.nextInt(17) + 1));
            }
        }
    }

    public void setSidekick(MapleSidekick s) {
        this.sidekick = s;
    }

    public MapleSidekick getSidekick() {
        return sidekick;
    }

    public List<Item> getRebuy() {
        return rebuy;
    }

    public Map<Integer, MonsterFamiliar> getFamiliars() {
        return familiars;
    }

    public MonsterFamiliar getSummonedFamiliar() {
        return summonedFamiliar;
    }

    public void removeFamiliar() {
        if (summonedFamiliar != null && map != null) {
            removeVisibleFamiliar();
        }
        summonedFamiliar = null;
    }

    public void removeVisibleFamiliar() {
        getMap().removeMapObject(summonedFamiliar);
        removeVisibleMapObject(summonedFamiliar);
        getMap().broadcastMessage(MaplePacketCreator.removeFamiliar(this.getId()));
        anticheat.resetFamiliarAttack();
        final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        cancelEffect(ii.getItemEffect(ii.getFamiliar(summonedFamiliar.getFamiliar()).passive), false, System.currentTimeMillis());
    }

    public void spawnFamiliar(MonsterFamiliar mf) {
        summonedFamiliar = mf;

        mf.setStance(0);
        mf.setPosition(getPosition());
        mf.setFh(getFH());
        addVisibleMapObject(mf);
        getMap().spawnFamiliar(mf);

        final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
        final MapleStatEffect eff = ii.getItemEffect(ii.getFamiliar(summonedFamiliar.getFamiliar()).passive);
        if (eff != null && eff.getInterval() <= 0 && eff.makeChanceResult()) { //i think this is actually done through a recv, which is ATTACK_FAMILIAR +1
            eff.applyTo(this);
        }
        lastFamiliarEffectTime = System.currentTimeMillis();
    }

    public final boolean canFamiliarEffect(long now, MapleStatEffect eff) {
        return lastFamiliarEffectTime > 0 && lastFamiliarEffectTime + eff.getInterval() < now;
    }

    public void doFamiliarSchedule(long now) {
        for (MonsterFamiliar mf : familiars.values()) {
            if (summonedFamiliar != null && summonedFamiliar.getId() == mf.getId()) {
                mf.addFatigue(this, 5);
                final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
                final MapleStatEffect eff = ii.getItemEffect(ii.getFamiliar(summonedFamiliar.getFamiliar()).passive);
                if (eff != null && eff.getInterval() > 0 && canFamiliarEffect(now, eff) && eff.makeChanceResult()) {
                    eff.applyTo(this);
                }
            } else if (mf.getFatigue() > 0) {
                mf.setFatigue(Math.max(0, mf.getFatigue() - 5));
            }
        }
    }

    public MapleImp[] getImps() {
        return imps;
    }

    public void sendImp() {
        for (int i = 0; i < imps.length; i++) {
            if (imps[i] != null) {
                client.getSession().write(MaplePacketCreator.updateImp(imps[i], ImpFlag.SUMMONED.getValue(), i, true));
            }
        }
    }

    public int getBattlePoints() {
        return pvpPoints;
    }

    public int getTotalBattleExp() {
        return pvpExp;
    }

    public void setBattlePoints(int p) {
        if (p != pvpPoints) {
            client.getSession().write(UIPacket.getBPMsg(p - pvpPoints));
            updateSingleStat(MapleStat.BATTLE_POINTS, p);
        }
        this.pvpPoints = p;
    }

    public void setTotalBattleExp(int p) {
        final int previous = pvpExp;
        this.pvpExp = p;
        if (p != previous) {
            stats.recalcPVPRank(this);

            updateSingleStat(MapleStat.BATTLE_EXP, stats.pvpExp);
            updateSingleStat(MapleStat.BATTLE_RANK, stats.pvpRank);
        }
    }

    public void changeTeam(int newTeam) {
        this.coconutteam = newTeam;

        if (inPVP()) {
            client.getSession().write(MaplePacketCreator.getPVPTransform(newTeam + 1));
            map.broadcastMessage(MaplePacketCreator.changeTeam(id, newTeam + 1));
        } else {
            client.getSession().write(MaplePacketCreator.showEquipEffect(newTeam));
        }
    }

    public void disease(int type, int level) {
        if (MapleDisease.getBySkill(type) == null) {
            return;
        }
        chair = 0;
        client.getSession().write(MaplePacketCreator.cancelChair(-1));
        map.broadcastMessage(this, MaplePacketCreator.showChair(id, 0), false);
        giveDebuff(MapleDisease.getBySkill(type), MobSkillFactory.getMobSkill(type, level));
    }

    public boolean inPVP() {
        return eventInstance != null && eventInstance.getName().startsWith("PVP");
    }

    public void clearAllCooldowns() {
        for (MapleCoolDownValueHolder m : getCooldowns()) {
            final int skil = m.skillId;
            removeCooldown(skil);
            client.getSession().write(MaplePacketCreator.skillCooldown(skil, 0));
        }
    }

    public Pair<Double, Boolean> modifyDamageTaken(double damage, MapleMapObject attacke) {
        Pair<Double, Boolean> ret = new Pair<Double, Boolean>(damage, false);
        if (damage <= 0) {
            return ret;
        }
        final Integer div = getBuffedValue(MapleBuffStat.DIVINE_SHIELD);
        final Integer div2 = getBuffedValue(MapleBuffStat.HOLY_MAGIC_SHELL);
        if (div2 != null) {
            if (div2 <= 0) {
                cancelEffectFromBuffStat(MapleBuffStat.HOLY_MAGIC_SHELL);
            } else {
                setBuffedValue(MapleBuffStat.HOLY_MAGIC_SHELL, div2 - 1);
                damage = 0;
            }
        } else if (div != null) {
            if (div <= 0) {
                cancelEffectFromBuffStat(MapleBuffStat.DIVINE_SHIELD);
            } else {
                setBuffedValue(MapleBuffStat.DIVINE_SHIELD, div - 1);
                damage = 0;
            }
        }
        MapleStatEffect barrier = getStatForBuff(MapleBuffStat.COMBO_BARRIER);
        if (barrier != null) {
            damage = ((barrier.getX() / 1000.0) * damage);
        }
        barrier = getStatForBuff(MapleBuffStat.MAGIC_SHIELD);
        if (barrier != null) {
            damage = ((barrier.getX() / 1000.0) * damage);
        }
        barrier = getStatForBuff(MapleBuffStat.WATER_SHIELD);
        if (barrier != null) {
            damage = ((barrier.getX() / 1000.0) * damage);
        }
        List<Integer> attack = attacke instanceof MapleMonster || attacke == null ? null : (new ArrayList<Integer>());
        if (damage > 0) {
            if (getJob() == 122 && !skillisCooling(1220013)) {
                final Skill divine = SkillFactory.getSkill(1220013);
                if (getTotalSkillLevel(divine) > 0) {
                    final MapleStatEffect divineShield = divine.getEffect(getTotalSkillLevel(divine));
                    if (divineShield.makeChanceResult()) {
                        divineShield.applyTo(this);
                        client.getSession().write(MaplePacketCreator.skillCooldown(1220013, divineShield.getCooldown()));
                        addCooldown(1220013, System.currentTimeMillis(), divineShield.getCooldown() * 1000);
                    }
                }
            } else if (getBuffedValue(MapleBuffStat.SATELLITESAFE_PROC) != null && getBuffedValue(MapleBuffStat.SATELLITESAFE_ABSORB) != null && getBuffedValue(MapleBuffStat.PUPPET) != null) {
                double buff = getBuffedValue(MapleBuffStat.SATELLITESAFE_PROC).doubleValue();
                double buffz = getBuffedValue(MapleBuffStat.SATELLITESAFE_ABSORB).doubleValue();
                if ((int) ((buff / 100.0) * getStat().getMaxHp()) <= damage) {
                    damage -= ((buffz / 100.0) * damage);
                    cancelEffectFromBuffStat(MapleBuffStat.PUPPET);
                }
            } else if (getJob() == 433 || getJob() == 434) {
                final Skill divine = SkillFactory.getSkill(4330001);
                if (getTotalSkillLevel(divine) > 0 && getBuffedValue(MapleBuffStat.DARKSIGHT) == null && !skillisCooling(divine.getId())) {
                    final MapleStatEffect divineShield = divine.getEffect(getTotalSkillLevel(divine));
                    if (Randomizer.nextInt(100) < divineShield.getX()) {
                        divineShield.applyTo(this);
                    }
                }
            } else if ((getJob() == 512 || getJob() == 522) && getBuffedValue(MapleBuffStat.PIRATES_REVENGE) == null) {
                final Skill divine = SkillFactory.getSkill(getJob() == 512 ? 5120011 : 5220012);
                if (getTotalSkillLevel(divine) > 0 && !skillisCooling(divine.getId())) {
                    final MapleStatEffect divineShield = divine.getEffect(getTotalSkillLevel(divine));
                    if (divineShield.makeChanceResult()) {
                        divineShield.applyTo(this);
                        client.getSession().write(MaplePacketCreator.skillCooldown(divine.getId(), divineShield.getX()));
                        addCooldown(divine.getId(), System.currentTimeMillis(), divineShield.getX() * 1000);
                    }
                }
            } else if (getJob() == 312 && attacke != null) {
                final Skill divine = SkillFactory.getSkill(3120010);
                if (getTotalSkillLevel(divine) > 0) {
                    final MapleStatEffect divineShield = divine.getEffect(getTotalSkillLevel(divine));
                    if (divineShield.makeChanceResult()) {
                        if (attacke instanceof MapleMonster) {
                            final Rectangle bounds = divineShield.calculateBoundingBox(getTruePosition(), isFacingLeft());
                            final List<MapleMapObject> affected = getMap().getMapObjectsInRect(bounds, Arrays.asList(attacke.getType()));
                            int i = 0;

                            for (final MapleMapObject mo : affected) {
                                MapleMonster mons = (MapleMonster) mo;
                                if (mons.getStats().isFriendly() || mons.isFake()) {
                                    continue;
                                }
                                mons.applyStatus(this, new MonsterStatusEffect(MonsterStatus.STUN, 1, divineShield.getSourceId(), null, false), false, divineShield.getDuration(), true, divineShield);
                                final int theDmg = (int) (divineShield.getDamage() * getStat().getCurrentMaxBaseDamage() / 100.0);
                                mons.damage(this, theDmg, true);
                                getMap().broadcastMessage(MobPacket.damageMonster(mons.getObjectId(), theDmg));
                                i++;
                                if (i >= divineShield.getMobCount()) {
                                    break;
                                }
                            }
                        } else {
                            MapleCharacter chr = (MapleCharacter) attacke;
                            chr.addHP(-divineShield.getDamage());
                            attack.add((int) divineShield.getDamage());
                        }
                    }
                }
            } else if ((getJob() == 531 || getJob() == 532) && attacke != null) {
                final Skill divine = SkillFactory.getSkill(5310009); //slea.readInt() = 5310009, then slea.readInt() = damage. (175000)
                if (getTotalSkillLevel(divine) > 0) {
                    final MapleStatEffect divineShield = divine.getEffect(getTotalSkillLevel(divine));
                    if (divineShield.makeChanceResult()) {
                        if (attacke instanceof MapleMonster) {
                            final MapleMonster attacker = (MapleMonster) attacke;
                            final int theDmg = (int) (divineShield.getDamage() * getStat().getCurrentMaxBaseDamage() / 100.0);
                            attacker.damage(this, theDmg, true);
                            getMap().broadcastMessage(MobPacket.damageMonster(attacker.getObjectId(), theDmg));
                        } else {
                            final MapleCharacter attacker = (MapleCharacter) attacke;
                            attacker.addHP(-divineShield.getDamage());
                            attack.add((int) divineShield.getDamage());
                        }
                    }
                }
            } else if (getJob() == 132 && attacke != null) {
                final Skill divine = SkillFactory.getSkill(1320011);
                if (getTotalSkillLevel(divine) > 0 && !skillisCooling(divine.getId()) && getBuffSource(MapleBuffStat.BEHOLDER) == 1321007) {
                    final MapleStatEffect divineShield = divine.getEffect(getTotalSkillLevel(divine));
                    if (divineShield.makeChanceResult()) {
                        client.getSession().write(MaplePacketCreator.skillCooldown(divine.getId(), divineShield.getCooldown()));
                        addCooldown(divine.getId(), System.currentTimeMillis(), divineShield.getCooldown() * 1000);
                        if (attacke instanceof MapleMonster) {
                            final MapleMonster attacker = (MapleMonster) attacke;
                            final int theDmg = (int) (divineShield.getDamage() * getStat().getCurrentMaxBaseDamage() / 100.0);
                            attacker.damage(this, theDmg, true);
                            getMap().broadcastMessage(MobPacket.damageMonster(attacker.getObjectId(), theDmg));
                        } else {
                            final MapleCharacter attacker = (MapleCharacter) attacke;
                            attacker.addHP(-divineShield.getDamage());
                            attack.add((int) divineShield.getDamage());
                        }
                    }
                }
            }
            if (attacke != null) {
                final int damr = (Randomizer.nextInt(100) < getStat().DAMreflect_rate ? getStat().DAMreflect : 0) + (getBuffedValue(MapleBuffStat.POWERGUARD) != null ? getBuffedValue(MapleBuffStat.POWERGUARD) : 0);
                final int bouncedam_ = damr + (getBuffedValue(MapleBuffStat.PERFECT_ARMOR) != null ? getBuffedValue(MapleBuffStat.PERFECT_ARMOR) : 0);
                if (bouncedam_ > 0) {
                    long bouncedamage = (long) (damage * bouncedam_ / 100);
                    long bouncer = (long) (damage * damr / 100);
                    damage -= bouncer;
                    if (attacke instanceof MapleMonster) {
                        final MapleMonster attacker = (MapleMonster) attacke;
                        bouncedamage = Math.min(bouncedamage, attacker.getMobMaxHp() / 10);
                        attacker.damage(this, bouncedamage, true);
                        getMap().broadcastMessage(this, MobPacket.damageMonster(attacker.getObjectId(), bouncedamage), getTruePosition());
                        if (getBuffSource(MapleBuffStat.PERFECT_ARMOR) == 31101003) {
                            MapleStatEffect eff = this.getStatForBuff(MapleBuffStat.PERFECT_ARMOR);
                            attacker.applyStatus(this, new MonsterStatusEffect(MonsterStatus.STUN, 1, eff.getSourceId(), null, false), false, eff.getDuration(), true, eff);
                        }
                    } else {
                        final MapleCharacter attacker = (MapleCharacter) attacke;
                        bouncedamage = Math.min(bouncedamage, attacker.getStat().getCurrentMaxHp() / 10);
                        attacker.addHP(-((int) bouncedamage));
                        attack.add((int) bouncedamage);
                        if (getBuffSource(MapleBuffStat.PERFECT_ARMOR) == 31101003) {
                            attacker.disease(MapleDisease.STUN.getDisease(), 1);
                        }
                    }
                    ret.right = true;
                }
                if ((getJob() == 411 || getJob() == 412 || getJob() == 421 || getJob() == 422) && getBuffedValue(MapleBuffStat.SUMMON) != null && attacke != null) {
                    final List<MapleSummon> ss = getSummonsReadLock();
                    try {
                        for (MapleSummon sum : ss) {
                            if (sum.getTruePosition().distanceSq(getTruePosition()) < 400000.0 && (sum.getSkill() == 4111007 || sum.getSkill() == 4211007)) {
                                final List<Pair<Integer, Integer>> allDamage = new ArrayList<Pair<Integer, Integer>>();
                                if (attacke instanceof MapleMonster) {
                                    final MapleMonster attacker = (MapleMonster) attacke;
                                    final int theDmg = (int) (SkillFactory.getSkill(sum.getSkill()).getEffect(sum.getSkillLevel()).getX() * damage / 100.0);
                                    allDamage.add(new Pair<Integer, Integer>(attacker.getObjectId(), theDmg));
                                    getMap().broadcastMessage(MaplePacketCreator.summonAttack(sum.getOwnerId(), sum.getObjectId(), (byte) 0x84, allDamage, getLevel(), true));
                                    attacker.damage(this, theDmg, true);
                                    checkMonsterAggro(attacker);
                                    if (!attacker.isAlive()) {
                                        getClient().getSession().write(MobPacket.killMonster(attacker.getObjectId(), 1));
                                    }
                                } else {
                                    final MapleCharacter chr = (MapleCharacter) attacke;
                                    final int dmg = SkillFactory.getSkill(sum.getSkill()).getEffect(sum.getSkillLevel()).getX();
                                    chr.addHP(-dmg);
                                    attack.add(dmg);
                                }
                            }
                        }
                    } finally {
                        //unlockSummonsReadLock();
                    }
                }
            }
        }
        if (attack != null && attack.size() > 0 && attacke != null) {
            getMap().broadcastMessage(MaplePacketCreator.pvpCool(attacke.getObjectId(), attack));
        }
        ret.left = damage;
        return ret;
    }

    public void onAttack(long maxhp, int maxmp, int skillid, int oid, int totDamage) {
        if (stats.hpRecoverProp > 0) {
            if (Randomizer.nextInt(100) <= stats.hpRecoverProp) {//i think its out of 100, anyway
                if (stats.hpRecover > 0) {
                    healHP(stats.hpRecover);
                }
                if (stats.hpRecoverPercent > 0) {
                    addHP(((int) Math.min(maxhp, Math.min(((int) ((double) totDamage * (double) stats.hpRecoverPercent / 100.0)), stats.getMaxHp() / 2))));
                }
            }
        }
        if (stats.mpRecoverProp > 0 && !GameConstants.isDemon(getJob())) {
            healMP(stats.mpRecover);
        }
        if (getBuffedValue(MapleBuffStat.COMBO_DRAIN) != null) {
            addHP(((int) Math.min(maxhp, Math.min(((int) ((double) totDamage * (double) getStatForBuff(MapleBuffStat.COMBO_DRAIN).getX() / 100.0)), stats.getMaxHp() / 2))));
        }
        if (getBuffSource(MapleBuffStat.COMBO_DRAIN) == 23101003) {
            addMP(((int) Math.min(maxmp, Math.min(((int) ((double) totDamage * (double) getStatForBuff(MapleBuffStat.COMBO_DRAIN).getX() / 100.0)), stats.getMaxMp() / 2))));
        }
        if (getBuffedValue(MapleBuffStat.REAPER) != null && getBuffedValue(MapleBuffStat.SUMMON) == null && getSummonsSize() < 4 && canSummon()) {
            final MapleStatEffect eff = getStatForBuff(MapleBuffStat.REAPER);
            if (eff.makeChanceResult()) {
                eff.applyTo(this, this, false, null, eff.getDuration());
            }
        }
        if (getJob() == 212 || getJob() == 222 || getJob() == 232) {
            int[] skills = {2120010, 2220010, 2320011};
            for (int i : skills) {
                final Skill skill = SkillFactory.getSkill(i);
                if (getTotalSkillLevel(skill) > 0) {
                    final MapleStatEffect venomEffect = skill.getEffect(getTotalSkillLevel(skill));
                    if (venomEffect.makeChanceResult() && getAllLinkMid().size() < venomEffect.getY()) {
                        setLinkMid(oid, venomEffect.getX());
                        venomEffect.applyTo(this);
                    }
                    break;
                }
            }
        }
        int[] skills = {4110011, 4120005, 4210010, 4220005, 4320005, 4340001, 14110004};
        for (int i : skills) {
            if (i == 4110011) {
                if (getTotalSkillLevel(4120011) > 0) {
                    i = 4120011;
                }
            } else if (i == 4210010) {
                if (getTotalSkillLevel(4220011) > 0) {
                    i = 4220011;
                }
            }
            final Skill skill = SkillFactory.getSkill(i);
            if (getTotalSkillLevel(skill) > 0) {
                final MapleStatEffect venomEffect = skill.getEffect(getTotalSkillLevel(skill));
                final MapleMonster monster = map.getMonsterByOid(oid);
                if (venomEffect.makeChanceResult() && monster != null) {
                    monster.applyStatus(this, new MonsterStatusEffect(MonsterStatus.POISON, 1, i, null, false), true, venomEffect.getDuration(), true, venomEffect);
                }
                break;
            }
        }
        // effects
        if (skillid > 0) {
            final Skill skil = SkillFactory.getSkill(skillid);
            final MapleStatEffect effect = skil.getEffect(getTotalSkillLevel(skil));
            switch (skillid) {
                case 15111001:
                case 3111008:
                case 1078:
                case 31111003:
                case 11078:
                case 14101006:
                case 33111006: //swipe
                case 4101005: //drain
                case 5111004: { // Energy Drain
                    addHP(((int) Math.min(maxhp, Math.min(((int) ((double) totDamage * (double) effect.getX() / 100.0)), stats.getMaxHp() / 2))));
                    break;
                }
                case 5211006:
                case 22151002: //killer wing
                case 5220011: {//homing
                    setLinkMid(oid, effect.getX());
                    break;
                }
                case 33101007: { //jaguar
                    clearLinkMid();
                    break;
                }
            }
        }
    }

    public void afterAttack(int mobCount, int attackCount, int skillid) {
        switch (getJob()) {
            case 511:
            case 512: {
                handleEnergyCharge(5110001, mobCount * attackCount);
                break;
            }
            case 1510:
            case 1511:
            case 1512: {
                handleEnergyCharge(15100004, mobCount * attackCount);
                break;
            }
            case 111:
            case 112:
            case 1111:
            case 1112:
                if (skillid != 1111008 & getBuffedValue(MapleBuffStat.COMBO) != null) { // shout should not give orbs
                    handleOrbgain();
                }
                break;
        }
        if (getBuffedValue(MapleBuffStat.OWL_SPIRIT) != null) {
            if (currentBattleshipHP() > 0) {
                decreaseBattleshipHP();
            }
            if (currentBattleshipHP() <= 0) {
                cancelEffectFromBuffStat(MapleBuffStat.OWL_SPIRIT);
            }
        }
        if (!isIntern()) {
            cancelEffectFromBuffStat(MapleBuffStat.WIND_WALK);
            cancelEffectFromBuffStat(MapleBuffStat.INFILTRATE);
            final MapleStatEffect ds = getStatForBuff(MapleBuffStat.DARKSIGHT);
            if (ds != null) {
                if (ds.getSourceId() != 4330001 || !ds.makeChanceResult()) {
                    cancelEffectFromBuffStat(MapleBuffStat.DARKSIGHT);
                }
            }
        }
    }

    public void applyIceGage(int x) {
        updateSingleStat(MapleStat.ICE_GAGE, x);
    }

    public Rectangle getBounds() {
        return new Rectangle(getTruePosition().x - 25, getTruePosition().y - 75, 50, 75);
    }

    public void handleForceGain(int oid, int skillid) {
        handleForceGain(oid, skillid, 0);
    }

    public void handleForceGain(int oid, int skillid, int extraForce) {
        if (!GameConstants.isForceIncrease(skillid) && extraForce <= 0) {
            return;
        }
        int forceGain = 1;
        if (getLevel() >= 30 && getLevel() < 70) {
            forceGain = 2;
        } else if (getLevel() >= 70 && getLevel() < 120) {
            forceGain = 3;
        } else if (getLevel() >= 120) {
            forceGain = 4;
        }
        force++; // counter
        addMP(extraForce > 0 ? extraForce : forceGain);
        getClient().getSession().write(MaplePacketCreator.gainForce(oid, force, forceGain));

        if (stats.mpRecoverProp > 0 && extraForce <= 0) {
            if (Randomizer.nextInt(100) <= stats.mpRecoverProp) {//i think its out of 100, anyway
                force++; // counter
                addMP(stats.mpRecover);
                getClient().getSession().write(MaplePacketCreator.gainForce(oid, force, stats.mpRecover));
            }
        }
    }

    public void doDemon() {
        int toAdd = 0;
        final MapleItemInformationProvider li = MapleItemInformationProvider.getInstance();
        final MapleInventory equip = getInventory(MapleInventoryType.EQUIPPED);
        if (GameConstants.isDemon(job)) { // adding check on level 
            if (job == 3110 && level >= 30) {
                toAdd = 1099002;
            } else if (job == 3111 && level >= 70) {
                toAdd = 1099003;
            } else if (job == 3112 && level >= 120) {
                toAdd = 1099004;
            }
            Item item = li.getEquipById(toAdd);
            getInventory(MapleInventoryType.EQUIPPED).removeItem((byte) -10);
            item.setPosition((byte) -10);
            equip.addFromDB(item);
        }
    }

    public MapleFriendship getFriendship(MapleFriendshipType t) {
        return friendships.get(t); // friendships.get(MapleFriendshipType.JoeJoe).addPoints(g, this);
    }

    public int getFriendshipPoints() {
        return friendshipPoints;
    }

    public void setFriendshipPoints(int pts) {
        this.friendshipPoints += pts;
    }

    public static String getCharacterNameById(int id) {
        String name = null;
        MapleCharacter chr = getOnlineCharacterById(id);
        if (chr != null) {
            return chr.getName();
        }
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps = null;
            Statement stmt = con.createStatement();
            ResultSet rs;
            ps = con.prepareStatement("select name from characters where id = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                name = rs.getString("name");
            }
            ps.close();
            rs.close();
        } catch (SQLException ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", ex);
        }
        return name;
    }

    public static MapleCharacter getOnlineCharacterById(int cid) {
        MapleCharacter chr = null;
        if (World.Find.findChannel(cid) >= 1) {
            chr = ChannelServer.getInstance(World.Find.findChannel(cid)).getPlayerStorage().getCharacterById(cid);
            if (chr != null) {
                return chr;
            }
        }

        return null;
    }

    public static MapleCharacter getOnlineCharacterByName(String name) {
        MapleCharacter chr = null;
        if (World.Find.findChannel(name) >= 1) {
            chr = ChannelServer.getInstance(World.Find.findChannel(name)).getPlayerStorage().getCharacterByName(name);
            if (chr != null) {
                return chr;
            }
        }
        return null;
    }

    public static MapleCharacter getCharacterByName(String name) {
        MapleCharacter chr = getOnlineCharacterByName(name);
        if (chr != null) {
            return chr;
        }
        int cid = getCharacterIdByName(name);
        return cid == -1 ? null : MapleCharacter.loadCharFromDB(cid, new MapleClient(null, null, new MapleSession(new MockIOSession())), true);
    }

    public static int getCharacterIdByName(String name) {
        int id = -1;
        MapleCharacter chr = getOnlineCharacterByName(name);
        if (chr != null) {
            return chr.getId();
        }
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps = null;
            Statement stmt = con.createStatement();
            ResultSet rs;
            ps = con.prepareStatement("select id from characters where name = ?");
            ps.setString(1, name);
            rs = ps.executeQuery();
            while (rs.next()) {
                id = rs.getInt("id");
            }
            ps.close();
            rs.close();
        } catch (SQLException ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", ex);
        }
        return id;
    }

    public void dropNPC(String message) {
        client.getSession().write(MaplePacketCreator.getNPCTalk(9010000, (byte) 0, message, "00 00", (byte) 0));
    }

    public void dropNPC(int npc, String message) {
        client.getSession().write(MaplePacketCreator.getNPCTalk(npc, (byte) 0, message, "00 00", (byte) 0));
    }

    public boolean OfflineBanByName(String name, String reason) {
        int id = 0;
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps = null;
            Statement stmt = con.createStatement();
            ResultSet rs;
            ps = con.prepareStatement("select id from characters where name = ?");
            ps.setString(1, name);
            rs = ps.executeQuery();
            while (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", ex);
        }
        if (id == 0) {
            return false;
        }
        return OfflineBanById(id, reason);
    }

    public boolean OfflineBanById(int id, String reason) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            Statement stmt = con.createStatement();
            PreparedStatement ps;
            ResultSet rs;
            int z = id;
            int acid = 0;
            String ip = "";
            //String mac = "";
            rs = stmt.executeQuery("select accountid from characters where id = " + id);
            while (rs.next()) {
                acid = rs.getInt("accountid");
            }
            if (acid == 0) {
                return false;
            }
            try (PreparedStatement psb = con.prepareStatement("UPDATE accounts SET banned = 1, banreason = ? WHERE id = ?")) {
                psb.setString(1, reason);
                psb.setInt(2, acid);
                psb.execute();
                psb.close();
            }

            //rs = stmt.executeQuery("select SessionIP, lastmac from accounts where id = " + acid);
            rs = stmt.executeQuery("select SessionIP from accounts where id = " + acid);
            while (rs.next()) {
                ip = rs.getString("SessionIP");
                //mac = rs.getString("lastmac");
            }
            if (!isVpn(ip)) {
                FileoutputUtil.logToFile("logs/Hack/Ban/MySql_input.txt", "\r\n[offlineBan] " + FileoutputUtil.NowTime() + " IP: " + ip + " 理由: " + reason, false, false);
                ps = con.prepareStatement("INSERT INTO ipbans (ip) VALUES (?)");
                ps.setString(1, ip);
                ps.executeUpdate();
                ps.close();
                try {
                    for (ChannelServer cs : ChannelServer.getAllInstances()) {
                        for (MapleCharacter chr : cs.getPlayerStorage().getAllCharacters()) {
                            if (chr.getClient().getSessionIPAddress().equals(ip)) {
                                if (!chr.getClient().isGm()) {
                                    chr.getClient().disconnect(true, false);
                                    chr.getClient().getSession().close();
                                }
                            }
                        }
                    }

                    for (MapleCharacter chr : CashShopServer.getPlayerStorage().getAllCharacters()) {
                        if (chr.getClient().getSessionIPAddress().equals(ip)) {
                            if (!chr.getClient().isGm()) {
                                chr.getClient().disconnect(true, false);
                                chr.getClient().getSession().close();
                            }
                        }
                    }

                } catch (Exception ex) {
                }
            }
            //MapleClient.banSingleMacs(mac);
            rs.close();
            stmt.close();
            return true;
        } catch (SQLException ex) {
            System.err.println("封锁出現错误 " + ex);
            FileoutputUtil.outError("logs/数据库异常.txt", ex);
        }
        return false;
    }

    public static boolean isVpn(String ip) {
        switch (ip) {
            case "/1.34.145.220":
            case "/59.125.5.52":
            case "/59.126.97.123": // 巴哈姆特提供免費VPN
            case "/60.251.73.100":
            case "/61.219.216.173":
            case "/61.219.216.174":
            case "/61.227.252.169":
            case "/61.228.228.128":
                return true;
        }
        return false;
    }

    public void setStr(int str) {
        stats.str = (short) str;
        stats.recalcLocalStats(this);
    }

    public void setLuk(int luk) {
        stats.luk = (short) luk;
        stats.recalcLocalStats(this);
    }

    public void setDex(int dex) {
        stats.dex = (short) dex;
        stats.recalcLocalStats(this);
    }

    public void setInt(int int_) {
        stats.int_ = (short) int_;
        stats.recalcLocalStats(this);
    }

    public void setMeso(int mesos) {
        meso = mesos;
    }

    public final void maxSkills() {
        for (Skill skil : SkillFactory.getAllSkills()) {
            changeSkillLevel(skil, skil.getMaxLevel(), (byte) skil.getMaxLevel());
        }
    }

    public final void maxSkillsByJob() {
        for (Skill skil : SkillFactory.getAllSkills()) {
            if (skil.canBeLearnedBy(job)) {
                changeSkillLevel(skil, skil.getMaxLevel(), (byte) skil.getMaxLevel());
            }
        }
    }

    public final void clearSkills() {
        for (Skill skil : SkillFactory.getAllSkills()) {
            changeSkillLevel(skil, (byte) 0, (byte) 0);
        }
    }

    public int getStr() {
        return getStat().getStr();
    }

    public int getInt() {
        return getStat().getInt();
    }

    public int getLuk() {
        return getStat().getLuk();
    }

    public int getDex() {
        return getStat().getDex();
    }

    public int getHp() {
        return getStat().getHp();
    }

    public int getMp() {
        return getStat().getMp();
    }

    public int getMaxHp() {
        return getStat().getMaxHp();
    }

    public int getMaxMp() {
        return getStat().getMaxMp();
    }

    public void setHp(int amount) {
        getStat().setHp(amount, this);
    }

    public void setMp(int amount) {
        getStat().setMp(amount, this);
    }

    public void setMaxHp(int amount) {
        getStat().setMaxHp(amount, this);
    }

    public void setMaxMp(int amount) {
        getStat().setMaxMp(amount, this);
    }

    public final void LearnSameSkill(MapleCharacter victim) {
        for (Skill skil : SkillFactory.getAllSkills()) {
            if (victim.getSkillLevel(skil) > 0) {
                changeSkillLevel(skil, victim.getSkillLevel(skil), victim.getMasterLevel(skil));
            }
        }
    }

    public final boolean canHold(final int itemid) {
        return getInventory(GameConstants.getInventoryType(itemid)).getNextFreeSlot() > -1;
    }

    public String getAccountSecondPassword() {
        return accountsecondPassword;
    }

    public int getBossLog(String bossid) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            int ret_count;
            PreparedStatement ps;
            ps = con.prepareStatement("select count(*) from bosslog where characterid = ? and bossid = ? and lastattempt >= subtime(current_timestamp, '1 0:0:0.0')");
            ps.setInt(1, id);
            ps.setString(2, bossid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ret_count = rs.getInt(1);
                } else {
                    ret_count = -1;
                }
            }
            ps.close();
            return ret_count;
        } catch (SQLException Ex) {
            //log.error("Error while read bosslog.", Ex);
            FileoutputUtil.outError("logs/数据库异常.txt", Ex);
            return -1;
        }
    }

    public void setBossLog(String bossid) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps;
            ps = con.prepareStatement("insert into bosslog (characterid, bossid) values (?,?)");
            ps.setInt(1, id);
            ps.setString(2, bossid);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException Ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", Ex);
            //   log.error("Error while insert bosslog.", Ex);
        }
    }

    public void deleteBossLog(String bossid) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps = con.prepareStatement("DELETE FROM bosslog WHERE characterid = ? and bossid = ?");
            ps.setInt(1, id);
            ps.setString(2, bossid);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException Wx) {
            FileoutputUtil.outError("logs/数据库异常.txt", Wx);
        }
    }

    public int getBossLogD(String bossid) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            int ret_count;
            PreparedStatement ps;
            ps = con.prepareStatement("select count(*) from bosslog where characterid = ? and bossid = ? and lastattempt >= DATE_SUB(curdate(),INTERVAL 0 DAY)");
            ps.setInt(1, id);
            ps.setString(2, bossid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ret_count = rs.getInt(1);
                } else {
                    ret_count = -1;
                }
            }
            ps.close();
            return ret_count;
        } catch (SQLException Ex) {
            //log.error("Error while read bosslog.", Ex);
            FileoutputUtil.outError("logs/数据库异常.txt", Ex);
            return -1;
        }
    }

    public int getBossLogY(String bossid) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            int ret_count;
            PreparedStatement ps;
            ps = con.prepareStatement("select count(*) from bosslog where characterid = ? and bossid = ? and DATE_FORMAT(lastattempt, '%Y%m') = DATE_FORMAT(CURDATE( ), '%Y%m')");
            ps.setInt(1, id);
            ps.setString(2, bossid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ret_count = rs.getInt(1);
                } else {
                    ret_count = -1;
                }
            }
            ps.close();
            return ret_count;
        } catch (SQLException Ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", Ex);
            return -1;
        }
    }

    public String getLoginKey() {
        return loginkey;
    }

    public String getServerKey() {
        return serverkey;
    }

    public String getClientKey() {
        return clientkey;
    }

    public String getCharacterNameById2(int id) {
        String name = null;
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps = null;
            ResultSet rs;
            ps = con.prepareStatement("select name from characters where id = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                name = rs.getString("name");
            }
            ps.close();
            rs.close();
        } catch (SQLException ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", ex);
        }
        return name;
    }

    public void startLieDetector(boolean isItem) {
        if (!(((getMapId() >= 910000000)) && ((getMapId() <= 910000022)))) {
            if (!(getMapId() == 800040410)) {
                if (!GameConstants.isFishingMap(getMapId())) {
                    if (!(getMap().getReturnMapId() == getMapId())) {
                        if (getAntiMacro().isPassed()) {
                            getAntiMacro().setPassed(false);
                        }
                        if (!getAntiMacro().inProgress()) {
                            getAntiMacro().startLieDetector(getName(), isItem, false);
                        }
                    }
                }
            }
        }
    }

    public void addMobVac_X() {
        MobVac_X++;
    }

    public int getMobVac_X() {
        return MobVac_X;
    }

    private long mobDirection = 0, lastresetTime = 0;

    public long getMobDirection() {
        return mobDirection;
    }

    public void addMobDirection(int distance) {
        if (lastresetTime + 60000 < System.currentTimeMillis()) {
            resetMobDirection();
            lastresetTime = System.currentTimeMillis();
        }
        mobDirection += distance;
    }

    public void resetMobDirection() {
        mobDirection = 0;
    }

    public void addMobVac() {
        MobVac++;
    }

    public int getMobVac() {
        return MobVac;
    }

    public int getCZJF() {
        return getCZJF(this);
    }

    public int getCZJF(MapleCharacter chr) {
        int maxtimes = 10;
        int nowtime = 0;
        int delay = 500;
        boolean error = false;
        int x = 0;
        do {
            nowtime++;
            try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("Select CZJF from Accounts Where id = " + chr.getClient().getAccID());
                while (rs.next()) {
                    int debug = -1;
                    try {
                        debug = rs.getInt("CZJF");
                    } catch (SQLException ex) {
                    }
                    if (debug != -1) {
                        x = rs.getInt("CZJF");
                        error = false;
                    } else {
                        error = true;
                    }
                }
                rs.close();
            } catch (SQLException SQL) {
                System.err.println("[getCZJF]无法连接数据库");
                FileoutputUtil.outError("logs/数据库异常.txt", SQL);
            } catch (Exception ex) {
                System.err.println("[getCZJF]" + ex);
                FileoutputUtil.outError("logs/数据库异常.txt", ex);
            }
            if (error) {
                try {
                    Thread.sleep(delay);
                } catch (Exception ex) {
                    FileoutputUtil.outError("logs/数据库异常.txt", ex);
                }
            }
        } while (error && (nowtime < maxtimes));
        return x;
    }

    public void setCZJF(int x) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps;
            ps = con.prepareStatement("Update Accounts set CZJF = ? Where id = ?");
            ps.setInt(1, x);
            ps.setInt(2, getClient().getAccID());
            ps.execute();
            ps.close();
        } catch (SQLException ex) {
            System.err.println("[CZJF]无法连接数据库");
            FileoutputUtil.outError("logs/数据库异常.txt", ex);
        } catch (Exception ex) {
            System.err.println("[setCZJF]" + ex);
            FileoutputUtil.outError("logs/数据库异常.txt", ex);
        }
    }

    public void setAcLog(String bossid) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps;
            ps = con.prepareStatement("insert into Aclog (accid, bossid) values (?,?)");
            ps.setInt(1, getClient().getAccID());
            ps.setString(2, bossid);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException Wx) {
            FileoutputUtil.outError("logs/数据库异常.txt", Wx);
        }
    }

    public void setAcLogS(String bossid) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps;
            ps = con.prepareStatement("insert into Aclog (accid, bossid) values (?,?)");
            ps.setInt(1, getAccountID());
            ps.setString(2, bossid);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException Wx) {
            FileoutputUtil.outError("logs/数据库异常.txt", Wx);
        }
    }

    public int getAcLog(String bossid) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            int ret_count = 0;
            PreparedStatement ps;
            ps = con.prepareStatement("select count(*) from Aclog where accid = ? and bossid = ? and lastattempt >= subtime(current_timestamp, '1 0:0:0.0')");
            ps.setInt(1, getClient().getAccID());
            ps.setString(2, bossid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ret_count = rs.getInt(1);
            } else {
                ret_count = -1;
            }
            rs.close();
            ps.close();
            return ret_count;
        } catch (SQLException Wx) {
            FileoutputUtil.outError("logs/数据库异常.txt", Wx);
            return -1;
        }
    }

    public int getAcLogS(String bossid) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            int ret_count = 0;
            PreparedStatement ps;
            ps = con.prepareStatement("select count(*) from Aclog where accid = ? and bossid = ?");
            ps.setInt(1, getClient().getAccID());
            ps.setString(2, bossid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ret_count = rs.getInt(1);
            } else {
                ret_count = -1;
            }
            rs.close();
            ps.close();
            return ret_count;
        } catch (SQLException Wx) {
            FileoutputUtil.outError("logs/数据库异常.txt", Wx);
            return -1;
        }
    }

    public void deleteAcLog(String bossid) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps = con.prepareStatement("DELETE FROM Aclog WHERE accid = ? and bossid = ?");
            ps.setInt(1, getClient().getAccID());
            ps.setString(2, bossid);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException Wx) {
            FileoutputUtil.outError("logs/数据库异常.txt", Wx);
        }
    }

    public int getAcLogD(String bossid) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            int ret_count;
            PreparedStatement ps;
            ps = con.prepareStatement("select count(*) from Aclog where accid = ? and bossid = ? and lastattempt >= DATE_SUB(curdate(),INTERVAL 0 DAY)");
            ps.setInt(1, getClient().getAccID());
            ps.setString(2, bossid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ret_count = rs.getInt(1);
                } else {
                    ret_count = -1;
                }
            }
            ps.close();
            return ret_count;
        } catch (SQLException Ex) {
            //log.error("Error while read bosslog.", Ex);
            FileoutputUtil.outError("logs/数据库异常.txt", Ex);
            return -1;
        }
    }

    public int getAclogY(String bossid) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            int ret_count;
            PreparedStatement ps;
            ps = con.prepareStatement("select count(*) from Aclog where accid = ? and bossid = ? and DATE_FORMAT(lastattempt, '%Y%m') = DATE_FORMAT(CURDATE( ), '%Y%m')");
            ps.setInt(1, getClient().getAccID());
            ps.setString(2, bossid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ret_count = rs.getInt(1);
                } else {
                    ret_count = -1;
                }
            }
            ps.close();
            return ret_count;
        } catch (SQLException Ex) {
            //log.error("Error while read bosslog.", Ex);
            FileoutputUtil.outError("logs/数据库异常.txt", Ex);
            return -1;
        }
    }

    public int getBossLogS(String bossid) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            int ret_count;
            PreparedStatement ps;
            ps = con.prepareStatement("select count(*) from bosslog where characterid = ? and bossid = ?");
            ps.setInt(1, id);
            ps.setString(2, bossid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ret_count = rs.getInt(1);
                } else {
                    ret_count = -1;
                }
            }
            ps.close();
            return ret_count;
        } catch (SQLException Ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", Ex);
            return -1;
        }
    }

    public int getBossLogC(String bossid) {
        int ret_count = 0;
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps;
            ps = con.prepareStatement("select count(*) from bosslog where characterid = ? and bossid = ?");
            ps.setInt(1, id);
            ps.setString(2, bossid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ret_count += rs.getInt(1);
            }
            ps.close();
            rs.close();
            return ret_count;
        } catch (SQLException Ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", Ex);
            return -1;
        }
    }

    public int getAcLogC(String bossid) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            int ret_count = 0;
            PreparedStatement ps;
            ps = con.prepareStatement("select count(*) from Aclog where accid = ? and bossid = ?");
            ps.setInt(1, getClient().getAccID());
            ps.setString(2, bossid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ret_count += rs.getInt(1);
            }
            rs.close();
            ps.close();
            return ret_count;
        } catch (SQLException Wx) {
            FileoutputUtil.outError("logs/数据库异常.txt", Wx);
            return -1;
        }
    }

    public int getBeans() {
        return getBeans(this);
    }

    public int getBeans(MapleCharacter chr) {
        int maxtimes = 10;
        int nowtime = 0;
        int delay = 500;
        boolean error = false;
        int x = 0;
        do {
            nowtime++;
            try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("Select beans from Accounts Where id = " + chr.getClient().getAccID());
                while (rs.next()) {
                    int debug = -1;
                    try {
                        debug = rs.getInt("beans");
                    } catch (SQLException ex) {
                    }
                    if (debug != -1) {
                        x = rs.getInt("beans");
                        error = false;
                    } else {
                        error = true;
                    }
                }
                rs.close();
            } catch (SQLException SQL) {
                System.err.println("[getbeans]无法连接数据库");
                FileoutputUtil.outError("logs/数据库异常.txt", SQL);
            } catch (Exception ex) {
                System.err.println("[getbeans]" + ex);
                FileoutputUtil.outError("logs/数据库异常.txt", ex);
            }
            if (error) {
                try {
                    Thread.sleep(delay);
                } catch (Exception ex) {
                    FileoutputUtil.outError("logs/数据库异常.txt", ex);
                }
            }
        } while (error && (nowtime < maxtimes));
        return x;
    }

    public void setBeans(int x) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps;
            ps = con.prepareStatement("Update Accounts set beans = ? Where id = ?");
            ps.setInt(1, x);
            ps.setInt(2, getClient().getAccID());
            ps.execute();
            ps.close();
        } catch (SQLException ex) {
            System.err.println("[beans]无法连接数据库");
            FileoutputUtil.outError("logs/数据库异常.txt", ex);
        } catch (Exception ex) {
            System.err.println("[setbeans]" + ex);
            FileoutputUtil.outError("logs/数据库异常.txt", ex);
        }
    }

    public int getAddLog() {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            int money = 0;
            PreparedStatement ps = con.prepareStatement("SELECT money FROM addlog WHERE accid = ?");
            ps.setInt(1, getClient().getAccID());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                money += rs.getInt("money");
            }
            rs.close();
            ps.close();
            return money;
        } catch (SQLException e) {
            FileoutputUtil.outError("logs/数据库异常.txt", e);
            return -1;
        }
    }

    public boolean chrdangerousIp(String lip) {
        boolean ret = false;
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection(); PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM dangerousip WHERE ? LIKE CONCAT(ip, '%')")) {
            ps.setString(1, lip);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                if (rs.getInt(1) > 0) {
                    ret = true;
                }
            }

        } catch (SQLException ex) {
            System.err.println("Error dangerousIp " + ex);
            FileoutputUtil.outError("logs/数据库异常.txt", ex);
        }
        return ret;
    }

    public void setChrDangerousIp(String lip) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO dangerousip (ip) VALUES (?)");
            ps.setString(1, lip);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException Wx) {
            FileoutputUtil.outError("logs/数据库异常.txt", Wx);
        }
    }

    public boolean ChrDangerousAcc(String acc) {
        boolean ret = false;
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection(); PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM dangerousacc WHERE ? LIKE CONCAT(acc, '%')")) {
            ps.setString(1, acc);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                if (rs.getInt(1) > 0) {
                    ret = true;
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error dangerousname " + ex);
            FileoutputUtil.outError("logs/数据库异常.txt", ex);
        }
        return ret;
    }

    public void setChrDangerousAcc(String acc) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO dangerousacc (acc) VALUES (?)");
            ps.setString(1, acc);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException Wx) {
            FileoutputUtil.outError("logs/数据库异常.txt", Wx);
        }
    }

    public void updateSkills() {
        Map list = new HashMap();
        final Map<Skill, SkillEntry> skillss = getSkills();
        for (final Entry<Skill, SkillEntry> skilla : skillss.entrySet()) {
            if ((skilla.getKey().getId() >= 92020031 && skilla.getKey().getId() <= 92020498) || (skilla.getKey().getId() >= 92030003 && skilla.getKey().getId() <= 92030206) || (skilla.getKey().getId() >= 92040043 && skilla.getKey().getId() <= 92040349)) {
                continue;
            }
            list.put(skilla.getKey(), skilla.getValue());
        }
        if (list.isEmpty()) {
            return;
        }
        client.getSession().write(MaplePacketCreator.updateSkills(list));
    }

    public void setCsMod(int mod) {
        CsMod = mod;
    }

    public int getCsMod() {
        return CsMod;
    }

    public static MapleCharacter getCharacterById(int cid) {
        MapleCharacter chr = getOnlineCharacterById(cid);
        if (chr != null) {
            return chr;
        }
        String name = getCharacterNameById(cid);
        return name == null ? null : MapleCharacter.loadCharFromDB(cid, new MapleClient(null, null, new MapleSession(new MockIOSession())), true);
    }

    public final void updateNewState(int newstate, int accountId) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection(); PreparedStatement ps = con.prepareStatement("UPDATE `accounts` SET `loggedin` = ? WHERE id = ?")) {
            ps.setInt(1, newstate);
            ps.setInt(2, accountId);
            ps.executeUpdate();
        } catch (SQLException ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", ex);
        }
    }

    public int getAccNewTime(String time) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            int ret_count;
            PreparedStatement ps;
            ps = con.prepareStatement("select count(*) from accounts where id = ? and createdat <= " + "'" + time + "'");
            ps.setInt(1, accountid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ret_count = rs.getInt(1);
                } else {
                    ret_count = -1;
                }
            }
            ps.close();
            return ret_count;
        } catch (SQLException Ex) {
            //log.error("Error while read bosslog.", Ex);
            FileoutputUtil.outError("logs/数据库异常.txt", Ex);
            return -1;
        }
    }

    public int getChrNewTime(String time) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            int ret_count;
            PreparedStatement ps;
            ps = con.prepareStatement("select count(*) from characters where id = ? and createdate <= " + "'" + time + "'");
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ret_count = rs.getInt(1);
                } else {
                    ret_count = -1;
                }
            }
            ps.close();
            return ret_count;
        } catch (SQLException Ex) {
            //log.error("Error while read bosslog.", Ex);
            FileoutputUtil.outError("logs/数据库异常.txt", Ex);
            return -1;
        }
    }

    public void startMapEffect(String msg, int itemId) {
        startMapEffect(msg, itemId, 10000);
    }

    public void startMapEffect(String msg, int itemId, int duration) {
        final MapleMapEffect mapEffect = new MapleMapEffect(msg, itemId);
        getClient().getSession().write(mapEffect.makeStartData());
        Timer.EventTimer.getInstance().schedule(new Runnable() {

            @Override
            public void run() {
                getClient().getSession().write(mapEffect.makeDestroyData());
            }
        }, duration);
    }

    public final boolean CanStorage() {
        if (lastStorageTime + 1000 > System.currentTimeMillis()) {
            return false;
        }
        lastStorageTime = System.currentTimeMillis();
        return true;
    }

    public Map<Integer, MapleBuffStatValueHolder> getSkillID() {
        return skillID;
    }

    public void isSquadPlayerID() {
        if (getMapId() == 240060000 || getMapId() == 240060100 || getMapId() == 240060200) {
            final EventManager em = getClient().getChannelServer().getEventSM().getEventManager("HorntailBattle");
            EventInstanceManager eim = em.getInstance("HorntailBattle");
            String propsa = eim.getProperty("isSquadPlayerID_" + getId());
            if ((eim != null) && (propsa != null) && propsa.equals("0")) {
                eim.setProperty("isSquadPlayerID_" + getId(), "1");
            }
        }
        if (getMapId() == 240060001 || getMapId() == 240060101 || getMapId() == 240060201) {
            final EventManager em = getClient().getChannelServer().getEventSM().getEventManager("ChaosHorntail");
            EventInstanceManager eim = em.getInstance("ChaosHorntail");
            String propsa = eim.getProperty("isSquadPlayerID_" + getId());
            if ((eim != null) && (propsa != null) && propsa.equals("0")) {
                eim.setProperty("isSquadPlayerID_" + getId(), "1");
            }
        }

        if (getMapId() == 280030000) {
            final EventManager em = getClient().getChannelServer().getEventSM().getEventManager("ZakumBattle");
            EventInstanceManager eim = em.getInstance("ZakumBattle");
            String propsa = eim.getProperty("isSquadPlayerID_" + getId());
            if ((eim != null) && (propsa != null) && propsa.equals("0")) {
                eim.setProperty("isSquadPlayerID_" + getId(), "1");
            }
        }

        if (getMapId() == 280030001) {
            final EventManager em = getClient().getChannelServer().getEventSM().getEventManager("ChaosZakum");
            EventInstanceManager eim = em.getInstance("ChaosZakum");
            String propsa = eim.getProperty("isSquadPlayerID_" + getId());
            if ((eim != null) && (propsa != null) && propsa.equals("0")) {
                eim.setProperty("isSquadPlayerID_" + getId(), "1");
            }
        }
        if (getMapId() == 551030200) {
            final EventManager em = getClient().getChannelServer().getEventSM().getEventManager("ScarTarBattle");
            EventInstanceManager eim = em.getInstance("ScarTarBattle");
            String propsa = eim.getProperty("isSquadPlayerID_" + getId());
            if ((eim != null) && (propsa != null) && propsa.equals("0")) {
                eim.setProperty("isSquadPlayerID_" + getId(), "1");
            }
        }

        if (getMapId() == 270050100) {
            final EventManager em = getClient().getChannelServer().getEventSM().getEventManager("PinkBeanBattle");
            EventInstanceManager eim = em.getInstance("PinkBeanBattle");
            String propsa = eim.getProperty("isSquadPlayerID_" + getId());
            if ((eim != null) && (propsa != null) && propsa.equals("0")) {
                eim.setProperty("isSquadPlayerID_" + getId(), "1");
            }
        }

        if (getMapId() == 271040100) {
            final EventManager em = getClient().getChannelServer().getEventSM().getEventManager("CygnusBattle");
            EventInstanceManager eim = em.getInstance("CygnusBattle");
            String propsa = eim.getProperty("isSquadPlayerID_" + getId());
            if ((eim != null) && (propsa != null) && propsa.equals("0")) {
                eim.setProperty("isSquadPlayerID_" + getId(), "1");
            }
        }

        if (getMapId() == 262000200) {
            final EventManager em = getClient().getChannelServer().getEventSM().getEventManager("ZHillaBattle");
            EventInstanceManager eim = em.getInstance("ZHillaBattle");
            String propsa = eim.getProperty("isSquadPlayerID_" + getId());
            if ((eim != null) && (propsa != null) && propsa.equals("0")) {
                eim.setProperty("isSquadPlayerID_" + getId(), "1");
            }
        }

        if (getMapId() == 262030300) {
            final EventManager em = getClient().getChannelServer().getEventSM().getEventManager("HillaBattle");
            EventInstanceManager eim = em.getInstance("HillaBattle");
            String propsa = eim.getProperty("isSquadPlayerID_" + getId());
            if ((eim != null) && (propsa != null) && propsa.equals("0")) {
                eim.setProperty("isSquadPlayerID_" + getId(), "1");
            }
        }
        if (getMapId() == 272020200) {
            final EventManager em = getClient().getChannelServer().getEventSM().getEventManager("ArkariumBattle");
            EventInstanceManager eim = em.getInstance("ArkariumBattle");
            String propsa = eim.getProperty("isSquadPlayerID_" + getId());
            if ((eim != null) && (propsa != null) && propsa.equals("0")) {
                eim.setProperty("isSquadPlayerID_" + getId(), "1");
            }
        }
        if (getMapId() == 105200710) {
            final EventManager em = getClient().getChannelServer().getEventSM().getEventManager("BossBloody_CHAOS");
            EventInstanceManager eim = em.getInstance("BossBloody_CHAOS");
            String propsa = eim.getProperty("isSquadPlayerID_" + getId());
            if ((eim != null) && (propsa != null) && propsa.equals("0")) {
                eim.setProperty("isSquadPlayerID_" + getId(), "1");
            }
        }

        if (getMapId() == 105200610) {
            final EventManager em = getClient().getChannelServer().getEventSM().getEventManager("BossBanban_CHAOS");
            EventInstanceManager eim = em.getInstance("BossBanban_CHAOS");
            String propsa = eim.getProperty("isSquadPlayerID_" + getId());
            if ((eim != null) && (propsa != null) && propsa.equals("0")) {
                eim.setProperty("isSquadPlayerID_" + getId(), "1");
            }
        }

        if (getMapId() == 105200810) {
            final EventManager em = getClient().getChannelServer().getEventSM().getEventManager("BossBelen_CHAOS");
            EventInstanceManager eim = em.getInstance("BossBelen_CHAOS");
            String propsa = eim.getProperty("isSquadPlayerID_" + getId());
            if ((eim != null) && (propsa != null) && propsa.equals("0")) {
                eim.setProperty("isSquadPlayerID_" + getId(), "1");
            }
        }

        if (getMapId() == 401060100) {
            final EventManager em = getClient().getChannelServer().getEventSM().getEventManager("BossMagnus_HARD");
            EventInstanceManager eim = em.getInstance("BossMagnus_HARD");
            String propsa = eim.getProperty("isSquadPlayerID_" + getId());
            if ((eim != null) && (propsa != null) && propsa.equals("0")) {
                eim.setProperty("isSquadPlayerID_" + getId(), "1");
            }
        }

    }

    public void SpReset() {
        Map<String, Integer> oldList = new HashMap(getSkills());
        Map newList = new HashMap();
        int AdventurerSp = 0;
        int ResistSp1 = 0;
        int ResistSp2 = 0;
        int ResistSp3 = 0;
        int ResistSp4 = 0;
        int EvanSp1 = 0;
        int EvanSp2 = 0;
        int EvanSp3 = 0;
        int EvanSp4 = 0;
        int EvanSp5 = 0;
        int EvanSp6 = 0;
        int EvanSp7 = 0;
        int EvanSp8 = 0;
        int EvanSp9 = 0;
        int EvanSp10 = 0;
        for (Map.Entry toRemove : oldList.entrySet()) {
            if ((!((Skill) toRemove.getKey()).isBeginnerSkill()) && (!((Skill) toRemove.getKey()).isSpecialSkill()) /*&& (!((Skill) toRemove.getKey()).isHyperSkill())*/) {
                int skillLevel = getSkillLevel((Skill) toRemove.getKey());
                if (skillLevel > 0) {
                    if (GameConstants.isAdventurer(getJob()) || GameConstants.isAran(getJob()) || GameConstants.isKOC(getJob())) {
                        AdventurerSp += ((SkillEntry) toRemove.getValue()).skillevel;
                    } else if (GameConstants.isResist(getJob())) {
                        if (((Skill) toRemove.getKey()).getId() / 10000 == 3200 || ((Skill) toRemove.getKey()).getId() / 10000 == 3300 || ((Skill) toRemove.getKey()).getId() / 10000 == 3500) {
                            ResistSp1 += ((SkillEntry) toRemove.getValue()).skillevel;
                        }
                        if (((Skill) toRemove.getKey()).getId() / 10000 == 3210 || ((Skill) toRemove.getKey()).getId() / 10000 == 3310 || ((Skill) toRemove.getKey()).getId() / 10000 == 3510) {
                            ResistSp2 += ((SkillEntry) toRemove.getValue()).skillevel;
                        }
                        if (((Skill) toRemove.getKey()).getId() / 10000 == 3211 || ((Skill) toRemove.getKey()).getId() / 10000 == 3311 || ((Skill) toRemove.getKey()).getId() / 10000 == 3511) {
                            ResistSp3 += ((SkillEntry) toRemove.getValue()).skillevel;
                        }
                        if (((Skill) toRemove.getKey()).getId() / 10000 == 3212 || ((Skill) toRemove.getKey()).getId() / 10000 == 3312 || ((Skill) toRemove.getKey()).getId() / 10000 == 3512) {
                            ResistSp4 += ((SkillEntry) toRemove.getValue()).skillevel;
                        }
                    } else if (GameConstants.isEvan(getJob())) {
                        if (((Skill) toRemove.getKey()).getId() / 10000 == 2200) {
                            EvanSp1 += ((SkillEntry) toRemove.getValue()).skillevel;
                        }
                        if (((Skill) toRemove.getKey()).getId() / 10000 == 2210) {
                            EvanSp2 += ((SkillEntry) toRemove.getValue()).skillevel;
                        }
                        if (((Skill) toRemove.getKey()).getId() / 10000 == 2211) {
                            EvanSp3 += ((SkillEntry) toRemove.getValue()).skillevel;
                        }
                        if (((Skill) toRemove.getKey()).getId() / 10000 == 2212) {
                            EvanSp4 += ((SkillEntry) toRemove.getValue()).skillevel;
                        }
                        if (((Skill) toRemove.getKey()).getId() / 10000 == 2213) {
                            EvanSp5 += ((SkillEntry) toRemove.getValue()).skillevel;
                        }
                        if (((Skill) toRemove.getKey()).getId() / 10000 == 2214) {
                            EvanSp6 += ((SkillEntry) toRemove.getValue()).skillevel;
                        }
                        if (((Skill) toRemove.getKey()).getId() / 10000 == 2215) {
                            EvanSp7 += ((SkillEntry) toRemove.getValue()).skillevel;
                        }
                        if (((Skill) toRemove.getKey()).getId() / 10000 == 2216) {
                            EvanSp8 += ((SkillEntry) toRemove.getValue()).skillevel;
                        }
                        if (((Skill) toRemove.getKey()).getId() / 10000 == 2217) {
                            EvanSp9 += ((SkillEntry) toRemove.getValue()).skillevel;
                        }
                        if (((Skill) toRemove.getKey()).getId() / 10000 == 2218) {
                            EvanSp10 += ((SkillEntry) toRemove.getValue()).skillevel;
                        }
                    }

                    if (((Skill) toRemove.getKey()).canBeLearnedBy(getJob())) {
                        newList.put(toRemove.getKey(), new SkillEntry(0, ((SkillEntry) toRemove.getValue()).masterlevel, ((SkillEntry) toRemove.getValue()).expiration));
                    } else {
                        newList.put(toRemove.getKey(), new SkillEntry(0, (byte) 0, -1L));
                    }
                }
            }
        }
        if (!newList.isEmpty()) {
            changeSkillsLevel(newList);
        }
        oldList.clear();
        newList.clear();
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            MapleCharacter.deleteWhereCharacterId(con, "DELETE FROM skills WHERE characterid = ?", this.getId());
        } catch (SQLException e) {
            FileoutputUtil.outError("logs/数据库异常.txt", e);
        }
        //int[] spToGive[10];
        int[] spToGive;

        if (GameConstants.isAdventurer(getJob()) || GameConstants.isAran(getJob()) || GameConstants.isKOC(getJob())) {
            spToGive = new int[1];
            spToGive[0] = AdventurerSp + getRemainingSp(0);
            for (int i = 0; i < spToGive.length; i++) {
                setRemainingSp(spToGive[i], i);
            }

        } else if (GameConstants.isResist(getJob())) {
            spToGive = new int[4];
            spToGive[3] = ResistSp4 + getRemainingSp(3);
            spToGive[2] = ResistSp3 + getRemainingSp(2);
            spToGive[1] = ResistSp2 + getRemainingSp(1);
            spToGive[0] = ResistSp1 + getRemainingSp(0);
            for (int i = 0; i < spToGive.length; i++) {
                setRemainingSp(spToGive[i], i);
            }
        } else if (GameConstants.isEvan(getJob())) {
            spToGive = new int[10];
            spToGive[9] = EvanSp10 + getRemainingSp(9);
            spToGive[8] = EvanSp9 + getRemainingSp(8);
            spToGive[7] = EvanSp8 + getRemainingSp(7);
            spToGive[6] = EvanSp7 + getRemainingSp(6);
            spToGive[5] = EvanSp6 + getRemainingSp(5);
            spToGive[4] = EvanSp5 + getRemainingSp(4);
            spToGive[3] = EvanSp4 + getRemainingSp(3);
            spToGive[2] = EvanSp3 + getRemainingSp(2);
            spToGive[1] = EvanSp2 + getRemainingSp(1);
            spToGive[0] = EvanSp1 + getRemainingSp(0);
            for (int i = 0; i < spToGive.length; i++) {
                setRemainingSp(spToGive[i], i);
            }
        } else {
            dropMessage(1, "该职业暂时无法使用该道具。");
            client.getSession().write(MaplePacketCreator.enableActions());
            marriage();
            return;
        }

        updateSingleStat(MapleStat.AVAILABLESP, 0);
        client.getSession().write(MaplePacketCreator.enableActions());
        marriage();
    }

    public void changeSkillsLevel(Map<Skill, SkillEntry> skills) {
        if (skills.isEmpty()) {
            return;
        }
        Map list = new HashMap();
        boolean hasRecovery = false;
        boolean recalculate = false;
        for (Map.Entry data : skills.entrySet()) {
            if (changeSkillData((Skill) data.getKey(), ((SkillEntry) data.getValue()).skillevel, ((SkillEntry) data.getValue()).masterlevel, ((SkillEntry) data.getValue()).expiration)) {
                list.put(data.getKey(), data.getValue());
                if (GameConstants.isRecoveryIncSkill(((Skill) data.getKey()).getId())) {
                    hasRecovery = true;
                }
                if (((Skill) data.getKey()).getId() < 80000000) {
                    recalculate = true;
                }
            }
        }
        if (list.isEmpty()) {
            return;
        }
        this.client.getSession().write(MaplePacketCreator.updateSkills(list));
        reUpdateStat(hasRecovery, recalculate);
    }

    private void reUpdateStat(boolean hasRecovery, boolean recalculate) {
        this.changed_skills = true;
        if (hasRecovery) {
            this.stats.relocHeal(this);
        }
        if (recalculate) {
            this.stats.recalcLocalStats(this);
        }
    }

    public boolean changeSkillData(Skill skill, int newLevel, byte newMasterlevel, long expiration) {
        if ((skill == null) || ((!GameConstants.isApplicableSkill(skill.getId())) && (!GameConstants.isApplicableSkill_(skill.getId())))) {
            return false;
        }
        if ((newLevel == 0) && (newMasterlevel == 0)) {
            if (this.skills.containsKey(skill)) {
                this.skills.remove(skill);
            } else {
                return false;
            }
        } else {
            this.skills.put(skill, new SkillEntry(newLevel, newMasterlevel, expiration/*, getSkillTeachId(skill), getSkillPosition(skill)*/));
        }
        return true;
    }

    public int getMoneyAll() {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            int money = 0;
            PreparedStatement ps = con.prepareStatement("SELECT amount FROM donate WHERE username = ?");
            ps.setString(1, getClient().getAccountName());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                money += rs.getInt("amount");
            }
            rs.close();
            ps.close();
            return money;
        } catch (SQLException e) {
            FileoutputUtil.outError("logs/数据库异常.txt", e);
            return -1;
        }
    }

    public boolean canHoldByType(byte bytype, int num) {
        if ((getInventory(MapleInventoryType.getByType(bytype)).getSlotLimit() - (getInventory(MapleInventoryType.getByType(bytype)).getNumSlotLimit() + 1)) <= num) {
            return false;
        }
        return true;
    }

    public void gainItem(int code, int amount) {
        MapleInventoryManipulator.addById(client, code, (short) amount, null);
        dropMessage(5, "您已获得物品:" + MapleItemInformationProvider.getInstance().getName(code) + amount + "个");

    }

    public int getStChrLog() {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            int ret_count;
            PreparedStatement ps;
            ps = con.prepareStatement("select count(*) from characterid where stlog = ?");
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ret_count = rs.getInt(1);
                } else {
                    ret_count = -1;
                }
            }
            ps.close();
            return ret_count;
        } catch (SQLException Ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", Ex);
            return -1;
        }
    }

    public String getStChrNameLog(int id) {
        int maxtimes = 10;
        int nowtime = 0;
        int delay = 500;
        boolean error = false;
        int x = 0;
        String name = "";
        do {
            nowtime++;
            try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("Select characterid from stlog Where stid = " + id);
                while (rs.next()) {
                    int debug = -1;
                    try {
                        debug = rs.getInt("characterid");
                    } catch (SQLException ex) {
                    }
                    if (debug != -1) {
                        x = rs.getInt("characterid");

                        name += getCharacterNameById(x) + ",";

                        error = false;
                    } else {
                        error = true;
                    }
                }
                rs.close();
            } catch (SQLException SQL) {
                FileoutputUtil.outError("logs/数据库异常.txt", SQL);
            } catch (Exception ex) {
                FileoutputUtil.outError("logs/数据库异常.txt", ex);
            }
            if (error) {
                try {
                    Thread.sleep(delay);
                } catch (Exception ex) {
                    FileoutputUtil.outError("logs/数据库异常.txt", ex);
                }
            }
        } while (error && (nowtime < maxtimes));
        return name;
    }

    public int getStLog() {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            int ret_count;
            PreparedStatement ps;
            ps = con.prepareStatement("select count(*) from stlog where characterid = ?");
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ret_count = rs.getInt(1);
                } else {
                    ret_count = -1;
                }
            }
            ps.close();
            return ret_count;
        } catch (SQLException Ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", Ex);
            return -1;
        }
    }

    public int getStjfLog(int id) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            int ret_count;
            PreparedStatement ps;
            ps = con.prepareStatement("select count(*) from stjflog where characterid = ?");
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ret_count = rs.getInt(1);
                } else {
                    ret_count = -1;
                }
            }
            ps.close();
            return ret_count;
        } catch (SQLException Ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", Ex);
            return -1;
        }
    }

    public int getStLogid(int id) {
        int maxtimes = 10;
        int nowtime = 0;
        int delay = 500;
        boolean error = false;
        int x = 0;
        do {
            nowtime++;
            try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("Select stid from stlog Where characterid = " + id);
                while (rs.next()) {
                    int debug = -1;
                    try {
                        debug = rs.getInt("stid");
                    } catch (SQLException ex) {
                    }
                    if (debug != -1) {
                        x = rs.getInt("stid");
                        error = false;
                    } else {
                        error = true;
                    }
                }
                rs.close();
            } catch (SQLException SQL) {
                FileoutputUtil.outError("logs/数据库异常.txt", SQL);
            } catch (Exception ex) {
                FileoutputUtil.outError("logs/数据库异常.txt", ex);
            }
            if (error) {
                try {
                    Thread.sleep(delay);
                } catch (Exception ex) {
                    FileoutputUtil.outError("logs/数据库异常.txt", ex);
                }
            }
        } while (error && (nowtime < maxtimes));
        return x;
    }

    public void setStLog(int stid) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps;
            ps = con.prepareStatement("insert into stlog (characterid, stid) values (?,?)");
            ps.setInt(1, id);
            ps.setInt(2, stid);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException Ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", Ex);
            //   log.error("Error while insert bosslog.", Ex);
        }
    }

    public void setStjfLog(int id, int stid) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps;
            ps = con.prepareStatement("insert into stjflog (characterid, stjf) values (?,?)");
            ps.setInt(1, id);
            ps.setInt(2, stid);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException Ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", Ex);
            //   log.error("Error while insert bosslog.", Ex);
        }
    }

    public void updateStjfLog(int id, int stid) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps;
            ps = con.prepareStatement("Update stjflog SET stjf = ? WHERE characterid = ?");
            ps.setInt(1, stid);
            ps.setInt(2, id);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException Ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", Ex);
            //   log.error("Error while insert bosslog.", Ex);
        }
    }

    public int getStjf(int id) {
        int maxtimes = 10;
        int nowtime = 0;
        int delay = 500;
        boolean error = false;
        int x = 0;
        do {
            nowtime++;
            try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("Select stjf from stjflog Where characterid = " + id);
                while (rs.next()) {
                    int debug = -1;
                    try {
                        debug = rs.getInt("stjf");
                    } catch (SQLException ex) {
                    }
                    if (debug != -1) {
                        x = rs.getInt("stjf");
                        error = false;
                    } else {
                        error = true;
                    }
                }
                rs.close();
            } catch (SQLException SQL) {
                FileoutputUtil.outError("logs/数据库异常.txt", SQL);
            } catch (Exception ex) {
                FileoutputUtil.outError("logs/数据库异常.txt", ex);
            }
            if (error) {
                try {
                    Thread.sleep(delay);
                } catch (Exception ex) {
                    FileoutputUtil.outError("logs/数据库异常.txt", ex);
                }
            }
        } while (error && (nowtime < maxtimes));
        return x;
    }

    public int getGuildschrlog(int id) {
        int maxtimes = 10;
        int nowtime = 0;
        int delay = 500;
        boolean error = false;
        int x = 0;
        do {
            nowtime++;
            try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("Select guildsjf from guildschrlog Where cid = " + id);
                while (rs.next()) {
                    int debug = -1;
                    try {
                        debug = rs.getInt("guildsjf");
                    } catch (SQLException ex) {
                    }
                    if (debug != -1) {
                        x = rs.getInt("guildsjf");
                        error = false;
                    } else {
                        error = true;
                    }
                }
                rs.close();
            } catch (SQLException SQL) {
                FileoutputUtil.outError("logs/数据库异常.txt", SQL);
            } catch (Exception ex) {
                FileoutputUtil.outError("logs/数据库异常.txt", ex);
            }
            if (error) {
                try {
                    Thread.sleep(delay);
                } catch (Exception ex) {
                    FileoutputUtil.outError("logs/数据库异常.txt", ex);
                }
            }
        } while (error && (nowtime < maxtimes));
        return x;
    }

    public void setGuildschrlog(int stid) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps;
            ps = con.prepareStatement("insert into guildschrlog (cid, guildsjf) values (?,?)");
            ps.setInt(1, id);
            ps.setInt(2, stid);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException Ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", Ex);
            //   log.error("Error while insert bosslog.", Ex);
        }
    }

    public int getGuildschrlogCout(int id) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            int ret_count;
            PreparedStatement ps;
            ps = con.prepareStatement("select count(*) from guildschrlog where cid = ?");
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ret_count = rs.getInt(1);
                } else {
                    ret_count = -1;
                }
            }
            ps.close();
            return ret_count;
        } catch (SQLException Ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", Ex);
            return -1;
        }
    }

    public void updateGuildschrlog(int id, int stid) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps;
            ps = con.prepareStatement("Update guildschrlog SET guildsjf = ? WHERE cid = ?");
            ps.setInt(1, stid);
            ps.setInt(2, id);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException Ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", Ex);
            //   log.error("Error while insert bosslog.", Ex);
        }
    }

    public int getGuildslog(int id) {
        int maxtimes = 10;
        int nowtime = 0;
        int delay = 500;
        boolean error = false;
        int x = 0;
        do {
            nowtime++;
            try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("Select guildsjf from guildslog Where guildsid = " + id);
                while (rs.next()) {
                    int debug = -1;
                    try {
                        debug = rs.getInt("guildsjf");
                    } catch (SQLException ex) {
                    }
                    if (debug != -1) {
                        x = rs.getInt("guildsjf");
                        error = false;
                    } else {
                        error = true;
                    }
                }
                rs.close();
            } catch (SQLException SQL) {
                FileoutputUtil.outError("logs/数据库异常.txt", SQL);
            } catch (Exception ex) {
                FileoutputUtil.outError("logs/数据库异常.txt", ex);
            }
            if (error) {
                try {
                    Thread.sleep(delay);
                } catch (Exception ex) {
                    FileoutputUtil.outError("logs/数据库异常.txt", ex);
                }
            }
        } while (error && (nowtime < maxtimes));
        return x;
    }

    public void setGuildslog(int id, int stid) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps;
            ps = con.prepareStatement("insert into guildslog (guildsid, guildsjf) values (?,?)");
            ps.setInt(1, id);
            ps.setInt(2, stid);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException Ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", Ex);
            //   log.error("Error while insert bosslog.", Ex);
        }
    }

    public int getGuildslogCout(int id) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            int ret_count;
            PreparedStatement ps;
            ps = con.prepareStatement("select count(*) from guildslog where guildsid = ?");
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ret_count = rs.getInt(1);
                } else {
                    ret_count = -1;
                }
            }
            ps.close();
            return ret_count;
        } catch (SQLException Ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", Ex);
            return -1;
        }
    }

    public void updateGuildslog(int id, int stid) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps;
            ps = con.prepareStatement("Update guildslog SET guildsjf = ? WHERE guildsid = ?");
            ps.setInt(1, stid);
            ps.setInt(2, id);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException Ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", Ex);
            //   log.error("Error while insert bosslog.", Ex);
        }
    }

    public int getGuildsloglevel(int id) {
        int maxtimes = 10;
        int nowtime = 0;
        int delay = 500;
        boolean error = false;
        int x = 0;
        do {
            nowtime++;
            try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("Select guildlevel from guildslog Where guildsid = " + id);
                while (rs.next()) {
                    int debug = -1;
                    try {
                        debug = rs.getInt("guildlevel");
                    } catch (SQLException ex) {
                    }
                    if (debug != -1) {
                        x = rs.getInt("guildlevel");
                        error = false;
                    } else {
                        error = true;
                    }
                }
                rs.close();
            } catch (SQLException SQL) {
                FileoutputUtil.outError("logs/数据库异常.txt", SQL);
            } catch (Exception ex) {
                FileoutputUtil.outError("logs/数据库异常.txt", ex);
            }
            if (error) {
                try {
                    Thread.sleep(delay);
                } catch (Exception ex) {
                    FileoutputUtil.outError("logs/数据库异常.txt", ex);
                }
            }
        } while (error && (nowtime < maxtimes));
        return x;
    }

    public void updateGuildsloglevel(int id, int stid) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps;
            ps = con.prepareStatement("Update guildslog SET guildlevel = ? WHERE guildsid = ?");
            ps.setInt(1, stid);
            ps.setInt(2, id);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException Ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", Ex);
            //   log.error("Error while insert bosslog.", Ex);
        }
    }

    public void setEveryDaylog(String quest, int pos) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps;
            ps = con.prepareStatement("insert into everydaylog (everydayid, everydayquest, everydaypos) values (?,?,?)");
            ps.setInt(1, id);
            ps.setString(2, quest);
            ps.setInt(3, pos);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException Ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", Ex);
        }
    }

    public int getEveryDaylogPos() {
        int maxtimes = 10;
        int nowtime = 0;
        int delay = 500;
        boolean error = false;
        int x = 0;
        do {
            nowtime++;
            try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("Select everydaypos from everydaylog Where everydayid = " + id);
                while (rs.next()) {
                    int debug = -1;
                    try {
                        debug = rs.getInt("everydaypos");
                    } catch (SQLException ex) {
                    }
                    if (debug != -1) {
                        x = rs.getInt("everydaypos");
                        error = false;
                    } else {
                        error = true;
                    }
                }
                rs.close();
            } catch (SQLException SQL) {
                FileoutputUtil.outError("logs/数据库异常.txt", SQL);
            } catch (Exception ex) {
                FileoutputUtil.outError("logs/数据库异常.txt", ex);
            }
            if (error) {
                try {
                    Thread.sleep(delay);
                } catch (Exception ex) {
                    FileoutputUtil.outError("logs/数据库异常.txt", ex);
                }
            }
        } while (error && (nowtime < maxtimes));
        return x;
    }

    public int getEveryDaylogCout(String bossid) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            int ret_count;
            PreparedStatement ps;
            ps = con.prepareStatement("select count(*) from everydaylog where everydayid = ? and everydayquest = ?");
            ps.setInt(1, id);
            ps.setString(2, bossid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ret_count = rs.getInt(1);
                } else {
                    ret_count = -1;
                }
            }
            ps.close();
            return ret_count;
        } catch (SQLException Ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", Ex);
            return -1;
        }
    }

    public void deleteEveryDaylog(String bossid) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps = con.prepareStatement("DELETE FROM everydaylog WHERE everydayid = ? and everydayquest = ?");
            ps.setInt(1, id);
            ps.setString(2, bossid);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException Wx) {
            FileoutputUtil.outError("logs/数据库异常.txt", Wx);
        }
    }

    public void setBuLingZanZu(int bossid) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps;
            ps = con.prepareStatement("insert into donate (username, amount, paymentMethod, date) values (?,?,?,?)");
            ps.setString(1, getClient().getAccountName());
            ps.setString(2, String.valueOf(bossid));
            ps.setString(3, "補領贊助");
            ps.setString(4, FileoutputUtil.NowTime());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException Wx) {
            FileoutputUtil.outError("logs/数据库异常.txt", Wx);
        }
    }

    /*public void setBuffTime(int skill, int time) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps;
            ps = con.prepareStatement("insert into skills_bufftimes (charid, SkillID, length, StartTime) values (?,?,?,?)");
            ps.setInt(1, getId());
            ps.setInt(2, skill);
            ps.setInt(3, time);
            ps.setLong(4, System.currentTimeMillis());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException Ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", Ex);
        }
    }

    public void updateBuffTime(int skill, int length) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps;
            ps = con.prepareStatement("Update skills_bufftimes set length = ?, StartTime = ? Where charid = ? and SkillID = ?");
            ps.setInt(1, length);
            ps.setLong(2, System.currentTimeMillis());
            ps.setInt(3, getId());
            ps.setInt(4, skill);
            ps.execute();
            ps.close();
        } catch (SQLException ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", ex);
        } catch (Exception ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", ex);
        }
    }

    public int getBuffTimeCout(int skill) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            int ret_count;
            PreparedStatement ps;
            ps = con.prepareStatement("select count(*) from skills_bufftimes where charid = ? and SkillID = ?");
            ps.setInt(1, getId());
            ps.setInt(2, skill);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ret_count = rs.getInt(1);
                } else {
                    ret_count = -1;
                }
            }
            ps.close();
            return ret_count;
        } catch (SQLException Ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", Ex);
            return -1;
        }
    }

    public int getBuffTimeLength(int skill) {
        int nx = 0;
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            ResultSet rs;
            try (PreparedStatement limitCheck = con.prepareStatement("SELECT * FROM skills_bufftimes WHERE charid=? and SkillID=?")) {
                limitCheck.setInt(1, getId());
                limitCheck.setInt(2, skill);
                rs = limitCheck.executeQuery();
                if (rs.next()) {
                    nx = rs.getInt("length");
                }
            }
            rs.close();
        } catch (SQLException ex) {
            FileoutputUtil.outputFileError("logs/数据库异常.txt", ex);
            ex.getStackTrace();
        }
        return nx;
    }

    public long getBuffTimeStartTime(int skill) {
        long nx = 0;
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            ResultSet rs;
            try (PreparedStatement limitCheck = con.prepareStatement("SELECT * FROM skills_bufftimes WHERE charid=? AND SkillID=?")) {
                limitCheck.setInt(1, getId());
                limitCheck.setInt(2, skill);
                rs = limitCheck.executeQuery();
                if (rs.next()) {
                    nx = rs.getLong("StartTime");
                }
            }
            rs.close();
        } catch (SQLException ex) {
            FileoutputUtil.outputFileError("logs/数据库异常.txt", ex);
            ex.getStackTrace();
        }
        return nx;
    }*/
    public void setReincarnation(boolean r) {
        isReincarnation = r;
    }

    public boolean isReincarnation() {
        return isReincarnation;
    }

    public void setStartTime(long r) {
        startTime = r;
    }

    public long getStartTime() {
        return startTime;
    }

    public String getNick() {
        String name = isZs() >= 1 ? "" : getTotalWins() >= 1 ? ("<轉生" + getTotalWins() + "次>") : "";
        return name;
    }

    public String getNick2() {
        String name = isVip() >= 1 ? "" : getVip() >= 1 ? ("<超級至尊VIP" + getVip() + ">") : "";
        return name;
    }

    public int getVip() {
        return vip;
    }

    public void setVip(int r) {
        vip = r;
    }

    public void gainVip() {
        int rmb = getMoneyAll();
        if (rmb >= 4000 && rmb < 8000) {
            setVip(1);
        } else if (rmb >= 8000 && rmb < 12000) {
            setVip(2);
        } else if (rmb >= 12000 && rmb < 16000) {
            setVip(3);
        } else if (rmb >= 16000 && rmb < 20000) {
            setVip(4);
        } else if (rmb >= 20000) {
            setVip(5);
        }
    }

    public boolean canExpiration(long now) {
        return (lastExpirationTime > 0) && (lastExpirationTime + 60000 < now);
    }

    public int getReviveCount() {
        return reviveCount;
    }

    public void setReviveCount(int reviveCount) {
        this.reviveCount = reviveCount;
    }

    public void reduceReviveCount() {
        --this.reviveCount;
    }

    public void restReviveCount() {
        this.reviveCount = -1;
    }

    public void eventRevive() {
        heal();
        this.reduceReviveCount();
    }

    public void heal() {
        this.stats.heal(this);
    }

    public long getMrqdTime() {
        return mrqdTime;
    }

    public void setMrqdTime(long r) {
        mrqdTime = r;
    }

    public int isVip() {
        return isVip;
    }

    public void setisVip(int r) {
        isVip = r;
    }

    public int isZs() {
        return isZs;
    }

    public void setisZs(int r) {
        isZs = r;
    }

    public boolean getCygnusBless() {
        int jobid = getJob();
        return ((getSkillLevel(12) > 0) && (jobid >= 0) && (jobid < 1000)) || ((getSkillLevel(10000012) > 0) && (jobid >= 1000) && (jobid < 2000)) || ((getSkillLevel(20000012) > 0) && ((jobid == 2000) || ((jobid >= 2100) && (jobid <= 2112)))) || ((getSkillLevel(20010012) > 0) && ((jobid == 2001) || ((jobid >= 2200) && (jobid <= 2218)))) || ((getSkillLevel(20020012) > 0) && ((jobid == 2002) || ((jobid >= 2300) && (jobid <= 2312)))) || ((getSkillLevel(20030012) > 0) && ((jobid == 2003) || ((jobid >= 2400) && (jobid <= 2412)))) || ((getSkillLevel(20040012) > 0) && ((jobid == 2004) || ((jobid >= 2700) && (jobid <= 2712)))) || ((getSkillLevel(30000012) > 0) && ((jobid == 3000) || ((jobid >= 3200) && (jobid <= 3512)))) || ((getSkillLevel(30010012) > 0) && ((jobid == 3002) || ((jobid >= 3600) && (jobid <= 3612)))) || ((getSkillLevel(50000012) > 0) && ((jobid == 5000) || ((jobid >= 5100) && (jobid <= 5112)))) || ((getSkillLevel(60000012) > 0) && ((jobid == 6000) || ((jobid >= 6100) && (jobid <= 6112)))) || ((getSkillLevel(60010012) > 0) && ((jobid == 6001) || ((jobid >= 6500) && (jobid <= 6512)))) || ((getSkillLevel(100000012) > 0) && ((jobid == 10000) || ((jobid >= 10100) && (jobid <= 10112))));
    }

    public int getMatchCardVal() {
        return matchCardVal;
    }

    public void setMatchCardVal(int val) {
        matchCardVal = val;
    }

    public int getBeansNum() {
        return beansNum;
    }

    /**
     * @param beansNum the beansNum to set
     */
    public void setBeansNum(int beansNum) {
        this.beansNum = beansNum;
    }

    /**
     * @return the beansRange
     */
    public int getBeansRange() {
        return beansRange;
    }

    /**
     * @param beansRange the beansRange to set
     */
    public void setBeansRange(int beansRange) {
        this.beansRange = beansRange;
    }

    /**
     * @return the canSetBeansNum
     */
    public boolean isCanSetBeansNum() {
        return canSetBeansNum;
    }

    /**
     * @param canSetBeansNum the canSetBeansNum to set
     */
    public void setCanSetBeansNum(boolean canSetBeansNum) {
        this.canSetBeansNum = canSetBeansNum;
    }

    public boolean getBeansStart() {
        return beansStart;
    }

    public void setBeansStart(boolean beansStart) {
        this.beansStart = beansStart;
    }

    public void modifyBeans(int quantity) {
        int bean = getBeans();
        if (bean + quantity < 0) {
            return;
        }
        setBeans(bean + quantity);
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
        return getBossRank(getId(), bossname, type);
    }

    public int getBossRank1(String bossname, byte type) {
        return getBossRank1(getId(), bossname, type);
    }

    public int getBossRank2(String bossname, byte type) {
        return getBossRank2(getId(), bossname, type);
    }

    public int getBossRank3(String bossname, byte type) {
        return getBossRank3(getId(), bossname, type);
    }

    public int getBossRank4(String bossname, byte type) {
        return getBossRank4(getId(), bossname, type);
    }

    public int getBossRank5(String bossname, byte type) {
        return getBossRank5(getId(), bossname, type);
    }

    public int getBossRank6(String bossname, byte type) {
        return getBossRank6(getId(), bossname, type);
    }

    public int getBossRank7(String bossname, byte type) {
        return getBossRank7(getId(), bossname, type);
    }

    public int getBossRank8(String bossname, byte type) {
        return getBossRank8(getId(), bossname, type);
    }

    public int getBossRank9(String bossname, byte type) {
        return getBossRank9(getId(), bossname, type);
    }

    public int getBossRank10(String bossname, byte type) {
        return getBossRank10(getId(), bossname, type);
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
        return setBossRank(getId(), getName(), bossname, type, add);
    }

    public int setBossRank1(String bossname, byte type, int add) {
        return setBossRank1(getId(), getName(), bossname, type, add);
    }

    public int setBossRank2(String bossname, byte type, int add) {
        return setBossRank2(getId(), getName(), bossname, type, add);
    }

    public int setBossRank3(String bossname, byte type, int add) {
        return setBossRank3(getId(), getName(), bossname, type, add);
    }

    public int setBossRank4(String bossname, byte type, int add) {
        return setBossRank4(getId(), getName(), bossname, type, add);
    }

    public int setBossRank5(String bossname, byte type, int add) {
        return setBossRank5(getId(), getName(), bossname, type, add);
    }

    public int setBossRank6(String bossname, byte type, int add) {
        return setBossRank6(getId(), getName(), bossname, type, add);
    }

    public int setBossRank7(String bossname, byte type, int add) {
        return setBossRank7(getId(), getName(), bossname, type, add);
    }

    public int setBossRank8(String bossname, byte type, int add) {
        return setBossRank8(getId(), getName(), bossname, type, add);
    }

    public int setBossRank9(String bossname, byte type, int add) {
        return setBossRank9(getId(), getName(), bossname, type, add);
    }

    public int setBossRank10(String bossname, byte type, int add) {
        return setBossRank10(getId(), getName(), bossname, type, add);
    }

    public int setBossRankCount(String bossname, int add) {
        return setBossRank(getId(), getName(), bossname, (byte) 2, add);
    }

    public int setBossRankCount1(String bossname, int add) {
        return setBossRank1(getId(), getName(), bossname, (byte) 2, add);
    }

    public int setBossRankCount2(String bossname, int add) {
        return setBossRank2(getId(), getName(), bossname, (byte) 2, add);
    }

    public int setBossRankCount3(String bossname, int add) {
        return setBossRank3(getId(), getName(), bossname, (byte) 2, add);
    }

    public int setBossRankCount4(String bossname, int add) {
        return setBossRank4(getId(), getName(), bossname, (byte) 2, add);
    }

    public int setBossRankCount5(String bossname, int add) {
        return setBossRank5(getId(), getName(), bossname, (byte) 2, add);
    }

    public int setBossRankCount6(String bossname, int add) {
        return setBossRank6(getId(), getName(), bossname, (byte) 2, add);
    }

    public int setBossRankCount7(String bossname, int add) {
        return setBossRank7(getId(), getName(), bossname, (byte) 2, add);
    }

    public int setBossRankCount8(String bossname, int add) {
        return setBossRank8(getId(), getName(), bossname, (byte) 2, add);
    }

    public int setBossRankCount9(String bossname, int add) {
        return setBossRank9(getId(), getName(), bossname, (byte) 2, add);
    }

    public int setBossRankCount10(String bossname, int add) {
        return setBossRank10(getId(), getName(), bossname, (byte) 2, add);
    }

    public int setBossRankPoints(String bossname, int add) {
        return setBossRank(getId(), getName(), bossname, (byte) 1, add);
    }

    public int setBossRankPoints1(String bossname, int add) {
        return setBossRank1(getId(), getName(), bossname, (byte) 1, add);
    }

    public int setBossRankPoints2(String bossname, int add) {
        return setBossRank2(getId(), getName(), bossname, (byte) 1, add);
    }

    public int setBossRankPoints3(String bossname, int add) {
        return setBossRank3(getId(), getName(), bossname, (byte) 1, add);
    }

    public int setBossRankPoints4(String bossname, int add) {
        return setBossRank4(getId(), getName(), bossname, (byte) 1, add);
    }

    public int setBossRankPoints5(String bossname, int add) {
        return setBossRank5(getId(), getName(), bossname, (byte) 1, add);
    }

    public int setBossRankPoints6(String bossname, int add) {
        return setBossRank6(getId(), getName(), bossname, (byte) 1, add);
    }

    public int setBossRankPoints7(String bossname, int add) {
        return setBossRank7(getId(), getName(), bossname, (byte) 1, add);
    }

    public int setBossRankPoints8(String bossname, int add) {
        return setBossRank8(getId(), getName(), bossname, (byte) 1, add);
    }

    public int setBossRankPoints9(String bossname, int add) {
        return setBossRank9(getId(), getName(), bossname, (byte) 1, add);
    }

    public int setBossRankPoints10(String bossname, int add) {
        return setBossRank10(getId(), getName(), bossname, (byte) 1, add);
    }

    public int setBossRankCount(String bossname) {
        return setBossRank(getId(), getName(), bossname, (byte) 2, 1);
    }

    public int setBossRankCount1(String bossname) {
        return setBossRank1(getId(), getName(), bossname, (byte) 2, 1);
    }

    public int setBossRankCount2(String bossname) {
        return setBossRank2(getId(), getName(), bossname, (byte) 2, 1);
    }

    public int setBossRankCount3(String bossname) {
        return setBossRank3(getId(), getName(), bossname, (byte) 2, 1);
    }

    public int setBossRankCount4(String bossname) {
        return setBossRank4(getId(), getName(), bossname, (byte) 2, 1);
    }

    public int setBossRankCount5(String bossname) {
        return setBossRank5(getId(), getName(), bossname, (byte) 2, 1);
    }

    public int setBossRankCount6(String bossname) {
        return setBossRank6(getId(), getName(), bossname, (byte) 2, 1);
    }

    public int setBossRankCount7(String bossname) {
        return setBossRank7(getId(), getName(), bossname, (byte) 2, 1);
    }

    public int setBossRankCount8(String bossname) {
        return setBossRank8(getId(), getName(), bossname, (byte) 2, 1);
    }

    public int setBossRankCount9(String bossname) {
        return setBossRank9(getId(), getName(), bossname, (byte) 2, 1);
    }

    public int setBossRankCount10(String bossname) {
        return setBossRank10(getId(), getName(), bossname, (byte) 2, 1);
    }

    public int setBossRankPoints(String bossname) {
        return setBossRank(getId(), getName(), bossname, (byte) 1, 1);
    }

    public int setBossRankPoints1(String bossname) {
        return setBossRank1(getId(), getName(), bossname, (byte) 1, 1);
    }

    public int setBossRankPoints2(String bossname) {
        return setBossRank2(getId(), getName(), bossname, (byte) 1, 1);
    }

    public int setBossRankPoints3(String bossname) {
        return setBossRank3(getId(), getName(), bossname, (byte) 1, 1);
    }

    public int setBossRankPoints4(String bossname) {
        return setBossRank4(getId(), getName(), bossname, (byte) 1, 1);
    }

    public int setBossRankPoints5(String bossname) {
        return setBossRank5(getId(), getName(), bossname, (byte) 1, 1);
    }

    public int setBossRankPoints6(String bossname) {
        return setBossRank6(getId(), getName(), bossname, (byte) 1, 1);
    }

    public int setBossRankPoints7(String bossname) {
        return setBossRank7(getId(), getName(), bossname, (byte) 1, 1);
    }

    public int setBossRankPoints8(String bossname) {
        return setBossRank8(getId(), getName(), bossname, (byte) 1, 1);
    }

    public int setBossRankPoints9(String bossname) {
        return setBossRank9(getId(), getName(), bossname, (byte) 1, 1);
    }

    public int setBossRankPoints10(String bossname) {
        return setBossRank10(getId(), getName(), bossname, (byte) 1, 1);
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
        List<Integer> ret_count = new ArrayList<>();
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps;
            ps = con.prepareStatement("select characterid from bosslog where bossid = ? and lastattempt >= DATE_SUB(curdate(),INTERVAL 0 DAY)");
            ps.setString(1, bossname);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int cid = rs.getInt("characterid");
                    ret_count.add(cid);
                }
            }
            ps.close();
            return ret_count;
        } catch (SQLException Ex) {
            //log.error("Error while read bosslog.", Ex);
            FileoutputUtil.outError("logs/数据库异常.txt", Ex);
            return ret_count;
        }
    }

    public int getPQLog(String key, boolean reset) {
        int value = 0;
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM characters_pqlog WHERE characters_id = ? AND pqlog = ?");
            ps.setInt(1, this.id);
            ps.setString(2, key);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                if (!reset) {
                    value = rs.getInt("value");
                } else {
                    Timestamp bossTime = rs.getTimestamp("resettime");
                    Calendar calNow = Calendar.getInstance();
                    Calendar sqlcal = Calendar.getInstance();
                    int days = 0;
                    if (bossTime != null) {
                        sqlcal.setTimeInMillis(bossTime.getTime());
                        sqlcal.set(Calendar.HOUR_OF_DAY, days);
                        sqlcal.set(Calendar.MINUTE, 0);
                        sqlcal.set(Calendar.SECOND, 0);
                        sqlcal.set(Calendar.MILLISECOND, 0);
                    }
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(new Date());
                    calendar.set(Calendar.HOUR_OF_DAY, 0);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.MILLISECOND, 0);
                    Date zero = calendar.getTime();
                    calNow.setTime(zero);
                    System.out.println(sqlcal.getTimeInMillis());
                    System.out.println(calNow.getTimeInMillis());
                    int pqtime = (int) (sqlcal.getTimeInMillis() - calNow.getTimeInMillis()) / 86400000;
                    if (pqtime == 1) {
                        value = rs.getInt("value");
                    }
                    if (pqtime <= 0) {
                        deletePQLog(key);
                    }
                }
            }
        } catch (SQLException Ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", Ex);
            return value;
        }
        return value;
    }

    public void deletePQLog(String bossid) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps = con.prepareStatement("DELETE FROM characters_pqlog WHERE characters_id = ? and pqlog = ?");
            ps.setInt(1, id);
            ps.setString(2, bossid);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException Wx) {
            FileoutputUtil.outError("logs/数据库异常.txt", Wx);
        }
    }

    public int getPQLogCount(String key) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            int ret_count;
            PreparedStatement ps;
            ps = con.prepareStatement("select count(*) from characters_pqlog where characters_id = ? and pqlog = ?");
            ps.setInt(1, id);
            ps.setString(2, key);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ret_count = rs.getInt(1);
                } else {
                    ret_count = -1;
                }
            }
            ps.close();
            return ret_count;
        } catch (SQLException Ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", Ex);
            return -1;
        }
    }

    public void setPQLog0(String key) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps;
            ps = con.prepareStatement("insert into characters_pqlog (characters_id, pqlog,value,resettime) values (?,?,?,DATE_SUB(curdate(),INTERVAL -1 DAY))");
            ps.setInt(1, id);
            ps.setString(2, key);
            ps.setInt(3, 0);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException Ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", Ex);
            //   log.error("Error while insert bosslog.", Ex);
        }
    }

    public void setPQLog1(String key) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps;
            ps = con.prepareStatement("insert into characters_pqlog (characters_id, pqlog,value,resettime) values (?,?,?,DATE_SUB(curdate(),INTERVAL -1 DAY))");
            ps.setInt(1, id);
            ps.setString(2, key);
            ps.setInt(3, 1);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException Ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", Ex);
            //   log.error("Error while insert bosslog.", Ex);
        }
    }

    public void addPQLog(String key) {
        if (getPQLogCount(key) > 0) {
            try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
                PreparedStatement ps;
                ps = con.prepareStatement("Update characters_pqlog set value = ?, resettime = DATE_SUB(curdate(),INTERVAL -1 DAY) Where characters_id = ? and pqlog = ?");
                ps.setInt(1, getPQLog(key, false) + 1);
                ps.setInt(2, getId());
                ps.setString(3, key);
                ps.execute();
                ps.close();
            } catch (SQLException ex) {
                FileoutputUtil.outError("logs/数据库异常.txt", ex);
            }
        } else {
            setPQLog1(key);
        }

    }

    public void addPQLog(String key, int value) {
        if (getPQLogCount(key) <= 0) {
            setPQLog0(key);
        }

        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps;
            ps = con.prepareStatement("Update characters_pqlog set value = ?, resettime = DATE_SUB(curdate(),INTERVAL -1 DAY) Where characters_id = ? and pqlog = ?");
            ps.setInt(1, getPQLog(key, false) + value);
            ps.setInt(2, getId());
            ps.setString(3, key);
            ps.execute();
            ps.close();
        } catch (SQLException ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", ex);
        }
    }

    public int getTodayOnlineTime() {
        return todayOnlineTime;
    }

    public void gainTodayOnlineTime(int s) {
        todayOnlineTime += s;
    }

    public void setTodayOnlineTime(int s) {
        todayOnlineTime = s;
    }
    public int getxmweekloga(final String log) {
        return this.getxmweeklog(log, this.getClient().getAccountName(), "" + this.accountid);
    }
    
    public int getxmweeklogc(final String log) {
        return this.getxmweeklog(log, this.name, "" + this.id);
    }
    
    public int getxmweeklog(final String log, final String type, final String logid) {
        int count = 0;
        try {
            final Connection con = DBConPool.getInstance().getDataSource().getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT * FROM xmweeklog WHERE characterid = ? AND type = ? AND log = ?");
            ps.setString(1, logid);
            ps.setString(2, type);
            ps.setString(3, log);
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt("cs");
                rs.close();
                ps.close();
            }
            return count;
        }
        catch (SQLException Ex) {
            System.out.println(FileoutputUtil.CurrentReadable_Time() + "  [xmweeklog] 数据库获取失败请检查代码！" + Ex);
            return count;
        }
    }
    
    public void setxmweekloga(final String log, final int cs) {
        this.setxmweeklog(log, this.getClient().getAccountName(), "" + this.accountid, cs);
    }
    
    public void gainxmweekloga(final String log, final int cs) {
        this.setxmweeklog(log, this.getClient().getAccountName(), "" + this.accountid, this.getxmweekloga(log) + cs);
    }
    
    public void setxmweeklogc(final String log, final int cs) {
        this.setxmweeklog(log, this.name, "" + this.id, cs);
    }
    
    public void gainxmweeklogc(final String log, final int cs) {
        this.setxmweeklog(log, this.name, "" + this.id, this.getxmweeklogc(log) + cs);
    }
    
    public void setxmweeklog(final String log, final String type, final String logid, final int cs) {
        try {
            final Connection con = DBConPool.getInstance().getDataSource().getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT * FROM xmweeklog WHERE characterid = ? AND type = ? AND log = ?");
            ps.setString(1, logid);
            ps.setString(2, type);
            ps.setString(3, log);
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                try (final PreparedStatement psu = con.prepareStatement("UPDATE xmweeklog SET cs = ? WHERE characterid = ? AND type = ? AND log = ?")) {
                    psu.setInt(1, cs);
                    psu.setString(2, logid);
                    psu.setString(3, type);
                    psu.setString(4, log);
                    psu.executeUpdate();
                    psu.close();
                }
            }
            else {
                try (final PreparedStatement psu = con.prepareStatement("INSERT INTO xmweeklog (type, characterid, log, cs) VALUES (?, ?, ?, ?)")) {
                    psu.setString(1, type);
                    psu.setString(2, logid);
                    psu.setString(3, log);
                    psu.setInt(4, cs);
                    psu.executeUpdate();
                    psu.close();
                }
            }
            rs.close();
        }
        catch (SQLException Ex) {
            System.out.println(FileoutputUtil.CurrentReadable_Time() + "  [xmweeklog] 数据库写入失败，请检查！" + Ex);
        }
    }
    
    public int getxmdailyloga(final String log) {
        return this.getxmdailylog(log, this.getClient().getAccountName(), "" + this.accountid);
    }
    
    public int getxmdailylogc(final String log) {
        return this.getxmdailylog(log, this.name, "" + this.id);
    }
    
    public int getxmdailylog(final String log, final String type, final String logid) {
        int count = 0;
        try {
            final Connection con = DBConPool.getInstance().getDataSource().getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT * FROM xmdailylog WHERE characterid = ? AND type = ? AND log = ?");
            ps.setString(1, logid);
            ps.setString(2, type);
            ps.setString(3, log);
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt("cs");
                rs.close();
                ps.close();
            }
            return count;
        }
        catch (SQLException Ex) {
            System.out.println(FileoutputUtil.CurrentReadable_Time() + "  [xmdailylog] 数据库获取失败请检查代码！" + Ex);
            return count;
        }
    }
    
        public int getOneTimeLog(final String bossid)  {
        try {
            final Connection con1 = DBConPool.getInstance().getDataSource().getConnection();

            int ret_count = 0;
            final PreparedStatement ps = con1.prepareStatement("select count(*) from onetimelog where characterid = ? and log = ?");
            ps.setInt(1, this.id);
            ps.setString(2, bossid);
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ret_count = rs.getInt(1);
            }
            else {
                ret_count = -1;
            }
            rs.close();
            ps.close();
            return ret_count;
        }
        catch (SQLException Ex) {
            return -1;
        }
    }
    
    public void setOneTimeLog(final String bossid) {
        try {
        final Connection con1 = DBConPool.getInstance().getDataSource().getConnection();

            final PreparedStatement ps = con1.prepareStatement("insert into onetimelog (characterid, log) values (?,?)");
            ps.setInt(1, this.id);
            ps.setString(2, bossid);
            ps.executeUpdate();
            ps.close();
        }
        catch (Exception ex) {}
    }
    
    public int getOneTimeLogcs(final String log) {
        int count = -1;
        try {
            final Connection con = DBConPool.getInstance().getDataSource().getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT * FROM onetimelog WHERE characterid = ?  AND log = ?");
            ps.setInt(1, this.id);
            ps.setString(2, log);
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt("cs");
                rs.close();
                ps.close();
            }
            return count;
        }
        catch (SQLException Ex) {
            System.out.println(FileoutputUtil.CurrentReadable_Time() + "  [onetimelog] 数据库获取失败请检查代码！" + Ex);
            return count;
        }
    }
    
    public void setOneTimeLogcs(final String log, final int cs) {
        try {
            final Connection con = DBConPool.getInstance().getDataSource().getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT * FROM onetimelog WHERE characterid = ?  AND log = ?");
            ps.setInt(1, this.id);
            ps.setString(2, log);
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                try (final PreparedStatement psu = con.prepareStatement("UPDATE onetimelog SET cs = ? WHERE characterid = ?  AND log = ?")) {
                    psu.setInt(1, cs);
                    psu.setInt(2, this.id);
                    psu.setString(3, log);
                    psu.executeUpdate();
                    psu.close();
                }
                catch (SQLException Ex) {
                    System.out.println(FileoutputUtil.CurrentReadable_Time() + "  [onetimelog] 数据库更新错误" + Ex);
                }
            }
            else {
                try (final PreparedStatement psu = con.prepareStatement("INSERT INTO onetimelog (characterid, log, cs) VALUES (?, ?, ?)")) {
                    psu.setInt(1, this.id);
                    psu.setString(2, log);
                    psu.setInt(3, cs);
                    psu.executeUpdate();
                    psu.close();
                }
            }
            rs.close();
        }
        catch (SQLException Ex2) {
            System.out.println(FileoutputUtil.CurrentReadable_Time() + "  [onetimelog] 数据库写入失败，请检查！" + Ex2);
        }
    }
    
    public void gainOneTimeLogcs(final String log, final int cs) {
        this.setOneTimeLogcs(log, this.getOneTimeLogcs(log) + cs);
    }
    
    public void resetOneTimeLog(final String bossid) {
        try {
            final Connection con = DBConPool.getInstance().getDataSource().getConnection();
            try (final PreparedStatement ps = con.prepareStatement("delete from onetimelog where characterid = ? and log = ?")) {
                ps.setInt(1, this.id);
                ps.setString(2, bossid);
                ps.executeUpdate();
            }
        }
        catch (Exception ex) {}
    }
    
    public void setxmdailyloga(final String log, final int cs) {
        this.setxmdailylog(log, this.getClient().getAccountName(), "" + this.accountid, cs);
    }
    
    public void gainxmdailyloga(final String log, final int cs) {
        this.setxmdailylog(log, this.getClient().getAccountName(), "" + this.accountid, this.getxmdailyloga(log) + cs);
    }
    
    public void setxmdailylogc(final String log, final int cs) {
        this.setxmdailylog(log, this.name, "" + this.id, cs);
    }
    
    public void gainxmdailylogc(final String log, final int cs) {
        this.setxmdailylog(log, this.name, "" + this.id, this.getxmdailylogc(log) + cs);
    }
    
    public void setxmdailylog(final String log, final String type, final String logid, final int cs) {
        try {
            final Connection con = DBConPool.getInstance().getDataSource().getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT * FROM xmdailylog WHERE characterid = ? AND type = ? AND log = ?");
            ps.setString(1, logid);
            ps.setString(2, type);
            ps.setString(3, log);
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                try (final PreparedStatement psu = con.prepareStatement("UPDATE xmdailylog SET cs = ? WHERE characterid = ? AND type = ? AND log = ?")) {
                    psu.setInt(1, cs);
                    psu.setString(2, logid);
                    psu.setString(3, type);
                    psu.setString(4, log);
                    psu.executeUpdate();
                    psu.close();
                }
                catch (SQLException Ex) {
                    System.out.println(FileoutputUtil.CurrentReadable_Time() + "  [xmdailylog] 数据库更新错误" + Ex);
                }
            }
            else {
                try (final PreparedStatement psu = con.prepareStatement("INSERT INTO xmdailylog (type, characterid, log, cs) VALUES (?, ?, ?, ?)")) {
                    psu.setString(1, type);
                    psu.setString(2, logid);
                    psu.setString(3, log);
                    psu.setInt(4, cs);
                    psu.executeUpdate();
                    psu.close();
                }
            }
            rs.close();
        }
        catch (SQLException Ex2) {
            System.out.println(FileoutputUtil.CurrentReadable_Time() + "  [xmdailylog] 数据库写入失败，请检查！" + Ex2);
        }
    }
    
        public int getAccYjLog(final String boss) {
        return this.getAccYjLog(boss, 0);
    }
    
    public int getAccYjLog(final String boss, int type) {
        try {
            int count = 0;
            final Connection con = DBConPool.getInstance().getDataSource().getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM AccYjLog WHERE characterid = ? AND bossid = ?");
            ps.setInt(1, this.accountid);
            ps.setString(2, boss);
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt("count");
                final Timestamp bossTime = rs.getTimestamp("time");
                rs.close();
                ps.close();
        }
            return count;
        }
        catch (SQLException Ex) {
            System.err.println("读取数据表：AccYjLog 错误 " + Ex);
            return -1;
        }
    }
    
    public int getAccYjLogmr(final String boss) {
        try {
            int count = 0;
            final int type = 1;
            final Connection con = DBConPool.getInstance().getDataSource().getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM AccYjLog WHERE characterid = ? AND bossid = ?");
            ps.setInt(1, this.accountid);
            ps.setString(2, boss);
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt("count");
                final Timestamp bossTime = rs.getTimestamp("time");
                rs.close();
                ps.close();
         }
            return count;
        }
        catch (SQLException Ex) {
            System.err.println("读取数据表：AccYjLog 错误 " + Ex);
            return -1;
        }
    }
    
    public void setAccYjLog(final String boss) {
        this.setAccYjLog(boss, 0, 1);
    }
    
    public void setAccYjLog(final String boss, final int count) {
        try {
            final Connection con = DBConPool.getInstance().getDataSource().getConnection();
            try (final PreparedStatement ps = con.prepareStatement("UPDATE AccYjLog SET count = ?, time = CURRENT_TIMESTAMP() WHERE characterid = ? AND bossid = ?")) {
                ps.setInt(1, count);
                ps.setInt(2, this.accountid);
                ps.setString(3, boss);
                ps.executeUpdate();
            }
        }
        catch (SQLException Ex) {
            System.err.println("写入数据表：AccYjLog 错误9105  " + Ex);
        }
    }
        public void gainAccYjLog(final String boss, final int count) {
        this.setAccYjLog(boss, this.getAccYjLog(boss, 0) + count);
    }
    
    public void setAccYjLog(final String boss, final int type, final int count) {
        final int bossCount = this.getAccYjLog(boss, type);
        try {
            final Connection con = DBConPool.getInstance().getDataSource().getConnection();
            try (final PreparedStatement ps = con.prepareStatement("UPDATE AccYjLog SET count = ?, type = ?, time = CURRENT_TIMESTAMP() WHERE characterid = ? AND bossid = ?")) {
                ps.setInt(1, bossCount + count);
                ps.setInt(2, type);
                ps.setInt(3, this.accountid);
                ps.setString(4, boss);
                ps.executeUpdate();
            }
        }
        catch (SQLException Ex) {
            System.err.println("写入数据表：AccYjLog 错误9121  " + Ex);
        }
    }
    
    public int getOneTimeLoga(final String log) {
        return this.getAccYjLog(log);
    }
    
    public int getOneTimeLogacs(final String log) {
        return this.getAccYjLog(log);
    }
    
    public void setOneTimeLoga(final String log) {
        this.setAccYjLog(log, this.getOneTimeLoga(log) + 1);
    }
    
    public void setOneTimeLoga(final String log, final int cs) {
        this.setAccYjLog(log, cs);
    }
    
    public void setOneTimeLacs(final String log, final int cs) {
        this.setAccYjLog(log, cs);
    }
    
    public void gainOneTimeLogacs(final String log, final int cs) {
        this.gainAccYjLog(log, cs);
    }
    
    public int getBossLogacs(final String log) {
        return this.getxmdailyloga(log);
    }
    
    public int getBossLoga(final String log) {
        return this.getxmdailyloga(log);
    }
    
    public void setBossLoga(final String log, final int cs) {
        this.setxmdailyloga(log, cs);
    }
    
    public void setBossLogacs(final String log, final int cs) {
        this.setxmdailyloga(log, cs);
    }
    
    public void gainBossLoga(final String log, final int cs) {
        this.gainxmdailyloga(log, cs);
    }
    
    public void gainBossLogacs(final String log, final int cs) {
        this.gainxmdailyloga(log, cs);
    }
    
    public int getweekLog(final String log) {
        return this.getxmweeklogc(log);
    }
    
    public void setweekLog(final String log, final int cs) {
        this.setxmweeklogc(log, cs);
    }
    
    public int getweekLoga(final String log) {
        return this.getxmweekloga(log);
    }
    
    public void setweekLoga(final String log, final int cs) {
        this.setxmweekloga(log, cs);
    }
    public void dropMessage(String message) {
        dropMessage(6, message);
    }
}
