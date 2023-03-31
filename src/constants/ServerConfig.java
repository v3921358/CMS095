package constants;

import server.ServerProperties;

public class ServerConfig {

    public static String 
            SQL_IP = "127.0.0.1",
            SQL_PORT = "3306",
            SQL_DATABASE = "v079",
            SQL_USER = "zeroms",
            SQL_PASSWORD = "sa!@#456"; //8df97d5582af803
    public static boolean Danji_Guanggao = false;//单机广告
    public static boolean autoLieDetector = false;
    public static boolean adminOnly = false;
    public static boolean logPackets = false;//OK
    public static int expRate = 1, mesoRate = 1, dropRate = 1, bossRate = 1, petRate = 1;
    public static int Weakengold = 1;//削弱金币
    public static int expRate69 = 1, expRate89 = 1, expRate109 = 1, expRate129 = 1, expRate139 = 1, expRate149 = 1, expRate159 = 1, expRate169 = 1, expRate179 = 1, expRate189 = 1, expRate200 = 1, expRate250 = 1;
    public static int personalPvpMap = 999999999, partyPvpMap = 999999999, guildPvpMap = 999999999;
    public static short createMobInterval = 1000;//地图怪物重生时间毫秒级 1000=1秒
    public static int maxlevel = 250;
    public static int kocmaxlevel = 200;
    public static final int flags = 3;
    public static String serverName = "冒险岛";
    public static String eventMessage = "Welcome to CMS_v079.1";
    public static String version = "095 补丁版本:2 更新日期:2022.3.2【典藏版】";
    public static int flag = 3;
    public static int monsterSpawn =  2;//几倍怪物
    public static int maxCharacters = 15;
    public static String ServerMessage = "Welcome to CMS_v079.1";
    public static int userLimit = 1500;
    public static String IP = "127.0.0.1";
    public static String wzpath = "wz";
    public static boolean Openmultiplayermap = true;/*开启多倍地图*/
    public static boolean adventurer = true;//冒险家
    public static boolean GodofWar = true;//战神
    public static boolean Knights = true;//骑士团
    public static boolean 初始化 = false;
    public static String Custommultiplayermap = "104040000|104040001|100020000|240040510|104010001|100040001|100040002|100040003|100040004|105050000|105050100|105070001|200010000|200020000|200040000|701010000|261020500|300010100|300010200|300020100|300020200|103010001|541010000|541010010|551030100|550000100|550000200|100000003";
    public static double Marriageexperience = 0.5,Employmentexperience = 0.5;//结婚经验 雇佣经验
    public static String key = "caonima";
    public static int Stackquantity = 3000;//最大叠加数量
    public static int channelCount = 2;
    public static boolean Use_Localhost = false;//单机模式
    public static boolean logs_chat = true;//聊天日志
    public static boolean logs_mrechant = true;//雇佣商人日志
    public static boolean logs_trade = true;//玩家交易日志
    public static boolean logs_storage = true;//仓库日志
    public static boolean logs_csbuy = true;//商城购买日志
    public static boolean logs_npcshop_buy = true;//商店日志
    public static boolean logs_DAMAGE = false;//伤害日志
    public static boolean logs_PACKETS = false;//封包日志
    public static boolean AUTO_REGISTER = true;//自动注册
    public static int CashPort = 8600;
    public static int LoginPort = 8484;
    public static int ChannelPort = 7575;
    public static int RSGS = 0;//人物灌水百分比
    public static boolean banallskill = false;//禁用buff
    public static boolean bangainexp = false;//禁止获得经验
    public static boolean bandropitem = false;//禁止爆物
    public static boolean enablepointsbuy = true;//是否开启商城道具可用抵用券购买
    public static boolean Forgingsystem = true;//锻造系统
    public static boolean CollegeSystem = true;//学院系统
    public static boolean Petsarenothungry = false;//宠物不饿
    public static boolean wirelessbuff = false;//开启无限BUFF
    public static int statLimit = 32767; //最大属性
    public static byte defaultInventorySlot = 48; //默认角色栏位
    
    
    public static boolean 捉鬼任务启动 = false;//#此处时间乃是重启端之后多久召唤第一个npc
    public static String 捉鬼任务初始召唤时间 = "1";//此处时间乃是多久召唤一次新的npc
    public static String 捉鬼任务刷新时间 = "100";//此处时间乃是多久召唤一次新的npc
    public static String 捉鬼任务NPC = "9900001";//捉鬼任务NPC
    
    public static String 捉鬼任务所在地图 = "261040000";//捉鬼任务NPC
    public static boolean 捉鬼任务全图掉血开关 = true;//#此处时间乃是重启端之后多久召唤第一个npc
    public static boolean 捉鬼任务全图掉蓝开关 = true;//#此处时间乃是重启端之后多久召唤第一个npc
    public static boolean 捉鬼任务减少血量开关 = true;//#此处时间乃是重启端之后多久召唤第一个npc
    public static boolean 捉鬼任务减少蓝量开关 = true;//#此处时间乃是重启端之后多久召唤第一个npc
    public static boolean 捉鬼任务全图封锁开关 = true;//#此处时间乃是重启端之后多久召唤第一个npc
    public static boolean 捉鬼任务全图黑暗开关 = true;//#此处时间乃是重启端之后多久召唤第一个npc
    public static boolean 捉鬼任务全图虚弱开关 = true;//#此处时间乃是重启端之后多久召唤第一个npc
    public static boolean 捉鬼任务全图诅咒开关 = true;//#此处时间乃是重启端之后多久召唤第一个npc
    public static boolean 捉鬼任务全图诱导开关 = false;//#此处时间乃是重启端之后多久召唤第一个npc
    public static boolean 捉鬼任务全图死亡开关 = false;//#此处时间乃是重启端之后多久召唤第一个npc
    public static boolean 捉鬼任务全图驱散开关 = false;//#此处时间乃是重启端之后多久召唤第一个npc
    public static boolean 互相残杀开关 = false;//#此处时间乃是重启端之后多久召唤第一个npc
    
    public static boolean 捉鬼任务直接死亡开关= false;
    public static boolean 捉鬼任务直接驱散开关= false;
    
    public static int 捉鬼任务伤害间隔时间 = 8;//捉鬼任务NPC
    public static int 捉鬼任务技能间隔时间 = 40;//捉鬼任务NPC
    public static int 捉鬼任务战士职业伤害回避率 = 5;//捉鬼任务NPC
    public static int 捉鬼任务法师职业伤害回避率 = 5;//捉鬼任务NPC
    public static int 捉鬼任务射手职业伤害回避率 = 5;//捉鬼任务NPC
    public static int 捉鬼任务飞侠职业伤害回避率 = 7;//捉鬼任务NPC
    public static int 捉鬼任务海盗职业伤害回避率 = 5;//捉鬼任务NPC
    public static int 捉鬼任务战士职业技能回避率 = 5;//捉鬼任务NPC
    public static int 捉鬼任务法师职业技能回避率 = 5;//捉鬼任务NPC
    public static int 捉鬼任务射手职业技能回避率 = 5;//捉鬼任务NPC
    public static int 捉鬼任务飞侠职业技能回避率 = 7;//捉鬼任务NPC
    public static int 捉鬼任务海盗职业技能回避率 = 5;//捉鬼任务NPC
    public static int 捉鬼任务伤害最大扣血数值 = 10000;//捉鬼任务NPC
    public static int 捉鬼任务伤害最大扣蓝数值 = 22000;//捉鬼任务NPC
    public static int 捉鬼任务伤害最小扣血数值 = 3000;//捉鬼任务NPC
    public static int 捉鬼任务伤害最小扣蓝数值 = 12000;//捉鬼任务NPC
    
    public static int petsuction = 4001239; //全屏宠吸现需要的道具
    public static boolean Droptheitem = false;//物落脚下开关
    public static boolean Fullscreenpetsuction = false;//全屏宠吸开关
    public static boolean Fullscreengoldcoin = false;//全屏吸金币开关
    public static boolean Fullscreensuction = false;//全屏吸物品开关
    
    
    public static int Doubleexplosionchannel = 5220002; //双爆频道道具
    public static boolean Doubleburstchannel = true;//双爆频道开关
    public static int Channelnumber = 2;//双爆频道
    
    public static boolean Strangeexplosionresistance = true;//打怪爆抵用开关
    public static boolean Strangeexplosivepointvolume = true;//打怪爆点卷开关
    public static int Monsterdroproll = 2; //怪物掉落点卷数量
    public static int Monsterdropoffset = 3; //怪物掉落抵用卷数量
    public static boolean marketUseBoard = true;//市场说话黑板
    private static String MapLoginType = "MapLogin";
    private static String loginWorldSelect = "MapLogin1";
    public static int bankHandlingFee = 10000; //金币存取手续费
    public static int takeOutHandlingFee = 10000; //寄存取物服务费
    public static int BEGINNER_SPAWN_MAP = 0;//新手出生地图
    public static boolean Freelydisableskills = true;//废弃
    public static int CreateGuildCost = 10000000;//创建家族费用
    public static int createEmblemMoney = 500000;//家族徽章费用
    public static int Fishinggoldcoinsseveral = 1;//钓鱼金币几倍
    public static int Fishingexperienceratio = 1;//钓鱼经验几倍
    public static int pdexp = 10;//获得经验等级X倍率
    public static int dyjy = 0;//默认经验
    public static int dyjb = 0;//默认金币
    public static boolean pdkg = true;//泡点开关
    public static int pdmap = 910000000;//泡点地图
    public static int pdzsjb = 1;//获得金币最少量
    public static int pdzdjb = 2;//获得金币最大量
    public static int pdzsdy = 1;//获得抵用券最少量
    public static int pdzddy = 2;//获得抵用券最大量
    public static int pdzsdj = 1;//获得点券最少量
    public static int pdzddj = 2;//获得点券最大量
    public static int pdzsdd = 1;//获得豆豆最少量
    public static int pdzddd = 2;//获得豆豆最大量
    
    public static boolean Upgradeconsultation = true;//升级公告
    public static boolean Injurytips = true;//受伤提示
    public static boolean debugMode = false;//封包提示
    public static boolean Playerchat = true;//玩家聊天
    public static boolean Throwoutgoldcoins = true;//丢出金币
    public static boolean Throwoutitems = true;//丢出物品
    public static boolean Gameinstructions = true;//游戏指令
    public static boolean Gamehorn = true;//游戏喇叭开关
    public static boolean Managestealth = true;//管理隐身
    public static boolean Managementacceleration = true;//管理加速
    public static boolean Playertransaction = true;//玩家交易
    public static boolean Hiredbusinessman = true;//雇佣商人开关
    public static boolean Loginhelp = true;//游戏帮助
    public static boolean Monsterstate = true;//怪物状态
    public static boolean Mapname = true;//显示地图名字
    public static boolean Onlineannouncement = true;//上线公告
    public static boolean Overdrawingarchiving = true;//过图存档
    public static boolean Auctionswitch = true;//拍卖行开关
    public static int Automaticcleaning = 300;//清理地板 满300个自动清理
    
    public static boolean Offlinehangup = true;//离线挂机
    public static int offlinemap = 910000000;//离线地图
    public static int Offlinecouponcounting = 1;//离线泡点点券
    public static int Offlineoffset = 1;//离线泡点抵用
    public static int OfflineDoudou = 1;//离线泡点豆豆
    public static int Offlinegoldcoins = 1;//离线泡点金币
    public static int Offlineexperience = 20;//离线泡点经验
   
    public static boolean applAttackRange = true;//技能范围检测
    public static boolean applAttackNumber = true;//技能段数检测
    public static boolean applAttackmobcount = true;//攻击数量检测
    public static boolean applAttackMP = true;//蓝耗检测
    
    public static String Servermaintenanceprompt = "游戏正在维护中.";//服务器维护提示语
    public static String Turnoffserverprompt = "游戏服务器将关闭维护，请玩家安全下线...";//关闭服务器提示语
    public static String Accountnumberprohibitionprompt = "你的账号已经被封禁.";//登陆时账号封禁提示语
  //ServerConfig
    

 
    
    public static void loadSetting() {

        
        }
         }
            

