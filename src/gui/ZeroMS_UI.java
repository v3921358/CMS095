package gui;
import javax.swing.UIManager;
import client.LoginCrypto;
import constants.GameConstants;
import handling.channel.ChannelServer;
import handling.login.handler.AutoRegister;
import client.MapleCharacter;
import client.MapleStat;
import client.inventory.Equip;
import client.inventory.ItemFlag;
import client.inventory.MapleInventoryType;
import gui.tools.*;
import server.CashItemFactory;
import server.quest.MapleQuest;
import server.*;
import server.life.MapleMonsterInformationProvider;
import constants.ServerConfig;
import constants.ServerConstants;
import database.DBConPool;
import handling.login.LoginServer;
import handling.world.World;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import scripting.ReactorScriptManager;
import scripting.PortalScriptManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.NotBoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import provider.MapleData;
import provider.MapleDataProvider;
import static server.Start.returnSerialNumber;
import server.MapleShopFactory;
import tools.wztosql.*;
import tools.FileoutputUtil;
import static tools.FileoutputUtil.CurrentReadable_Time;
import tools.HexTool;
import tools.LoadPacket;
import tools.MacAddressTool;
import tools.Pair;
import tools.packet.MaplePacketCreator;

/**
 * @author 疯神
 */
public class ZeroMS_UI extends javax.swing.JFrame {
    protected static Thread t;
    private static ScheduledFuture<?> ts;
        
    private static final long serialVersionUID = 6858557462052039707L;
   // private static ZeroMS_UI instance;
    private static ZeroMS_UI instance = new ZeroMS_UI(); 
    private ScheduledFuture<?> shutdownServer, startRunTime, updateplayer;;
    public static Map<String, Integer> ConfigValuesMap = new HashMap<>();
    private Map<String, Pair<String, String>> worldProperties = new HashMap<>();
    private Map<String, Boolean> eventsStatus = new HashMap<>();
    private Map<Windows, javax.swing.JFrame> windows = new HashMap<>();
    private final ReentrantReadWriteLock mutex = new ReentrantReadWriteLock();
    private ImageIcon icon = new ImageIcon(this.getClass().getClassLoader().getResource("Image/Icon.png"));
    private final Lock writeLock = mutex.writeLock();
    private Vector<Vector<String>> playerTableRom = new Vector<>();
    private int buffline = 0;
    private ArrayList<Tools> tools = new ArrayList();
    private boolean searchServer = false;
    private boolean writeChatLog = false;
    String mima = "123456";//账户中心引用
    int accid = 0;//账户和角色修改引用
    
    public static final ZeroMS_UI getInstance() {
    return ZeroMS_UI.instance;
    }
    
    public ZeroMS_UI() {
        setIconImage(icon.getImage());
        setTitle("[ZeroMS]后台管理工具 当前版本:" + ServerConfig.version + " 区名:" + ServerConfig.serverName);
        skin.初始化皮肤(0);//加载皮肤
        initComponents();
        startRunTime();//运行时常
        updateThreadNum(); // 开始统计线程
        //玩家检测相关开始
        Vector<String> columnName = new Vector<>();
        columnName.add("id");
        columnName.add("名称");
        columnName.add("等级");
        columnName.add("职业");
        columnName.add("当前所在地图");
        columnName.add("点券");
        columnName.add("抵用券");
        columnName.add("金币");                   
        playerTable.setModel(new DefaultTableModel(playerTableRom, columnName));
        //玩家检测相关结束
        inivalue.初始化账号表(0);
        inivalue.初始化角色表(0);
        账号表捕捉();
        角色表捕捉();
        在线角色表捕捉();
        String mac = MacAddressTool.getMacAddress(false);
        String num = returnSerialNumber();
        本机授权码.setText(LoginCrypto.hexSha1(num + mac));

        Timer.GuiTimer.getInstance().start();//计时器
        InputStream is = null;
        Properties p = new Properties();
        BufferedReader bf = null;
        
        // 设置当前进度值
        内存条.setValue(0);
        内存条.setStringPainted(true);
        // 绘制百分比文本（进度条中间显示的百分数）
        内存条.setMinimum(0);
        内存条.setMaximum(100);
    }
        //输出窗口
        private void printChatLog(String str) {
        output_notice_jTextPane.setText(output_notice_jTextPane.getText() + str + "\r\n");
    }
    
    //玩家监测
    public void updatePlayerList(MapleCharacter player, boolean add) {
        writeLock.lock();
        try {
            if (add) {
                Vector<String> column = new Vector<>();
                column.add(String.valueOf(player.getId()));
                column.add(player.getName());//玩家名字
             //   column.add(Integer.valueOf(chr.getClient().getChannel()));
                column.add(String.valueOf(player.getLevel()));//等级
                column.add(String.valueOf(MapleCarnivalChallenge.getJobNameById(player.getJob())));//职业
                column.add(player.getMap().getMapName() + " [ID:"+ player.getMap().getReturnMapId() +  "]");//地图
                column.add(String.valueOf(player.getCSPoints(1)));//点券
                column.add(String.valueOf(player.getCSPoints(2)));//抵用
                column.add(String.valueOf(player.getCSPoints(4)));//金币
                
                playerTableRom.add(column);
            } else {
                Iterator<Vector<String>> it = playerTableRom.iterator();
                while (it.hasNext()) {
                    Vector<String> v = it.next();
                    if (Integer.valueOf(v.get(0)) == player.getId()) {
                        playerTableRom.removeElement(v);
                        break;
                    }
                }
            }
        } finally {
            writeLock.unlock();
        }
        playerTable.updateUI();
        在线人数.setText("<html>在线人数:<span style='color:red;'>" + playerTableRom.size() + "</span>");
    }
    
    
            public void runTool(final Tools tool) {
        if (tools.contains(tool)) {
            JOptionPane.showMessageDialog(null, "工具已在运行。");
        } else {
            tools.add(tool);
            Thread t = new Thread() {
                @Override
                public void run() {
                    switch (tool) {
                     //   case DumpCashShop:
                       //     DumpCashShop.main(new String[0]);
                         //   break;
                        case DumpItems:
                            DumpItems.main(new String[0]);
                            break;
                     /*   case WzStringDumper装备数据:
                    {
                        try {
                            WzStringDumper装备数据.main(new String[0]);
                        } catch (IOException ex) {
                            Logger.getLogger(ZeroMS_UI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                            break;*/
                        case DumpMobSkills:
                            DumpMobSkills.main(new String[0]);
                            break;
                   /*     case DumpNpcNames:
                    {
                        try {
                            DumpNpcNames.main(new String[0]);
                        } catch (SQLException ex) {
                            Logger.getLogger(ZeroMS_UI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                            break;
                        case DumpOxQuizData:
                    {
                        try {
                            DumpOxQuizData.main(new String[0]);
                        } catch (IOException ex) {
                            Logger.getLogger(ZeroMS_UI.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (SQLException ex) {
                            Logger.getLogger(ZeroMS_UI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                            break;*/
                        case DumpQuests:
                            DumpQuests.main(new String[0]);
                            break;
                     /*   case MonsterDropCreator:
                    {
                        try {
                            MonsterDropCreator.main(new String[0]);
                        } catch (IOException ex) {
                            Logger.getLogger(ZeroMS_UI.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (NotBoundException ex) {
                            Logger.getLogger(ZeroMS_UI.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (InstanceAlreadyExistsException ex) {
                            Logger.getLogger(ZeroMS_UI.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (MBeanRegistrationException ex) {
                            Logger.getLogger(ZeroMS_UI.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (NotCompliantMBeanException ex) {
                            Logger.getLogger(ZeroMS_UI.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (MalformedObjectNameException ex) {
                            Logger.getLogger(ZeroMS_UI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                            break;*/
                            
                    }
                    tools.remove(tool);
                }
            };
            t.start();
        }
    }
    
        public void openWindow(final Windows w) {
        if (!windows.containsKey(w)) {
            switch (w) {
                    case 广播系统:
                    windows.put(w, new 广播系统());
                    break;
                    case 基址计算工具:
                    windows.put(w, new 基址计算工具());
                    break;
                    case 卡密制作工具:
                    windows.put(w, new 卡密制作工具());
                    break;
                    case 包头转换工具:
                    windows.put(w, new 包头转换工具());
                    break;
                case 代码查询工具:
                    windows.put(w, new 代码查询工具());
                    break;
                    case 一键还原:
                    windows.put(w, new 一键还原());
                    break;
                    case 商城管理控制台:
                    windows.put(w, new 商城管理控制台());
                    break;
                    case 商店管理控制台:
                    windows.put(w, new 商店管理控制台());
                    break;
                    case 怪物爆率控制台:
                    windows.put(w, new 怪物爆率控制台());
                    break;
                    case 箱子爆率控制台:
                    windows.put(w, new 箱子爆率控制台());
                    break;
                    case 删除自添加NPC工具:
                    windows.put(w, new 删除自添加NPC工具());
                    break;
                    case 游戏抽奖工具:
                    windows.put(w, new 游戏抽奖工具());
                    break;
                    case OX答题控制台:
                    windows.put(w, new OX答题控制台());
                    break;
                    case 批量删除工具:
                    windows.put(w, new 批量删除工具());
                    break;
                    case 游戏家族控制台:
                    windows.put(w, new 游戏家族控制台());
                    break;
                    case MACip封禁:
                    windows.put(w, new MACip封禁());
                    break;
                default:
                    return;
            }
            windows.get(w).setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        }
        windows.get(w).setVisible(true);
    }

    class MyTableCellRenderer implements TableCellRenderer {

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            ImageIcon ker = new ImageIcon("null");
            JLabel label = new JLabel(ker);
            label.setOpaque(false);
            return label;
        }
    }

    public enum Tools {

      //  DumpCashShop,
        DumpItems,
        FixCharSets,
        DumpMobSkills,
        DumpNpcNames,
        DumpOxQuizData,
        DumpQuests,
        MonsterDropCreator;
    }

    public enum Windows {
        广播系统,
        基址计算工具,
        角色转移工具,
        一键还原,
        商城管理控制台,
        商店管理控制台,
        怪物爆率控制台,
        箱子爆率控制台,
        锻造控制台,
        副本控制台,
        卡密制作工具,
        包头转换工具,
        代码查询工具,
        删除自添加NPC工具,
        游戏抽奖工具,
        药水冷却时间控制台,
        拍卖行控制台,
        金锤子成功率控制台,
        永恒重生装备控制台,
        野外BOSS刷新时间,
        椅子控制台,
        鱼来鱼往,
        钓鱼控制台,
        OX答题控制台,
        批量删除工具,
        游戏家族控制台,
        MACip封禁,
        CashShopItemEditor,
        CashShopItemAdder,
        DropDataAdder,
        DropDataEditor,;

    }
    
        private javax.swing.ComboBoxModel getMapleTypeModel() {
        Vector mapleTypeModel = new Vector();
        mapleTypeModel.add("大陆(CMS)");
        mapleTypeModel.add("国际(GMS)");
        mapleTypeModel.add("韩国(KMS)");
        mapleTypeModel.add("台湾(TMS)");
        mapleTypeModel.add("日本(JMS)");
       /* for (ServerConstants.MapleType e : ServerConstants.MapleType.values()) {
            if (e == ServerConstants.MapleType.UNKNOWN) {
                continue;
            }
            mapleTypeModel.add(e.name());
        }*/
        return new DefaultComboBoxModel(mapleTypeModel);
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings({"unchecked", "serial"})
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane = new javax.swing.JTabbedPane();
        jPanel20 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        重载副本按钮 = new javax.swing.JButton();
        重载爆率按钮 = new javax.swing.JButton();
        重载传送门按钮 = new javax.swing.JButton();
        重载商店按钮 = new javax.swing.JButton();
        重载包头按钮 = new javax.swing.JButton();
        重载任务 = new javax.swing.JButton();
        重载反应堆按钮 = new javax.swing.JButton();
        重载商城按钮 = new javax.swing.JButton();
        jPanel15 = new javax.swing.JPanel();
        保存雇佣按钮 = new javax.swing.JButton();
        保存数据按钮 = new javax.swing.JButton();
        查询在线玩家人数按钮 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton21 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel28 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        output_jTextPane = new javax.swing.JTextPane();
        jPanel29 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        output_packet_jTextPane = new javax.swing.JTextPane();
        jPanel30 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        output_notice_jTextPane = new javax.swing.JTextPane();
        jPanel31 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        output_err_jTextPane = new javax.swing.JTextPane();
        jPanel32 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        output_out_jTextPane = new javax.swing.JTextPane();
        jPanel41 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        本机授权码 = new javax.swing.JTextField();
        jPanel17 = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        playerTable = new javax.swing.JTable();
        jPanel59 = new javax.swing.JPanel();
        jButton71 = new javax.swing.JButton();
        jButton77 = new javax.swing.JButton();
        jButton78 = new javax.swing.JButton();
        jButton79 = new javax.swing.JButton();
        jButton80 = new javax.swing.JButton();
        jButton81 = new javax.swing.JButton();
        jButton82 = new javax.swing.JButton();
        jButton83 = new javax.swing.JButton();
        jButton84 = new javax.swing.JButton();
        jButton85 = new javax.swing.JButton();
        jButton86 = new javax.swing.JButton();
        在线人数 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        角色在线 = new javax.swing.JTextField();
        jPanel21 = new javax.swing.JPanel();
        jTabbedPane6 = new javax.swing.JTabbedPane();
        jPanel12 = new javax.swing.JPanel();
        jPanel49 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        serverName = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        IP = new javax.swing.JTextField();
        jLabel52 = new javax.swing.JLabel();
        jTextField22 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel54 = new javax.swing.JLabel();
        logPackets = new javax.swing.JCheckBox();
        jLabel31 = new javax.swing.JLabel();
        wzpath = new javax.swing.JTextField();
        adminOnly = new javax.swing.JCheckBox();
        USE_FIXED_IV = new javax.swing.JCheckBox();
        autoLieDetector = new javax.swing.JCheckBox();
        Use_Localhost = new javax.swing.JCheckBox();
        AUTO_REGISTER = new javax.swing.JCheckBox();
        banallskill = new javax.swing.JCheckBox();
        bangainexp = new javax.swing.JCheckBox();
        bandropitem = new javax.swing.JCheckBox();
        CollegeSystem = new javax.swing.JCheckBox();
        Forgingsystem = new javax.swing.JCheckBox();
        enablepointsbuy = new javax.swing.JCheckBox();
        marketUseBoard = new javax.swing.JCheckBox();
        Upgradeconsultation = new javax.swing.JCheckBox();
        Injurytips = new javax.swing.JCheckBox();
        debugMode = new javax.swing.JCheckBox();
        Playerchat = new javax.swing.JCheckBox();
        Throwoutgoldcoins = new javax.swing.JCheckBox();
        Throwoutitems = new javax.swing.JCheckBox();
        Overdrawingarchiving = new javax.swing.JCheckBox();
        Onlineannouncement = new javax.swing.JCheckBox();
        Mapname = new javax.swing.JCheckBox();
        Gameinstructions = new javax.swing.JCheckBox();
        Gamehorn = new javax.swing.JCheckBox();
        Managestealth = new javax.swing.JCheckBox();
        Managementacceleration = new javax.swing.JCheckBox();
        Playertransaction = new javax.swing.JCheckBox();
        Hiredbusinessman = new javax.swing.JCheckBox();
        Loginhelp = new javax.swing.JCheckBox();
        Monsterstate = new javax.swing.JCheckBox();
        Auctionswitch = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        Petsarenothungry = new javax.swing.JCheckBox();
        wirelessbuff = new javax.swing.JCheckBox();
        jPanel16 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        SQL_USER = new javax.swing.JTextField();
        SQL_PASSWORD = new javax.swing.JTextField();
        SQL_PORT = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        SQL_DATABASE = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        SQL_IP = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        logs_storage = new javax.swing.JCheckBox();
        logs_npcshop_buy = new javax.swing.JCheckBox();
        logs_mrechant = new javax.swing.JCheckBox();
        logs_csbuy = new javax.swing.JCheckBox();
        logs_trade = new javax.swing.JCheckBox();
        logs_chat = new javax.swing.JCheckBox();
        logs_DAMAGE = new javax.swing.JCheckBox();
        logs_PACKETS = new javax.swing.JCheckBox();
        jPanel5 = new javax.swing.JPanel();
        adventurer = new javax.swing.JCheckBox();
        GodofWar = new javax.swing.JCheckBox();
        Knights = new javax.swing.JCheckBox();
        jPanel8 = new javax.swing.JPanel();
        物品叠加开关6 = new javax.swing.JCheckBox();
        物品叠加开关7 = new javax.swing.JCheckBox();
        jPanel14 = new javax.swing.JPanel();
        物品叠加开关8 = new javax.swing.JCheckBox();
        物品叠加开关9 = new javax.swing.JCheckBox();
        jPanel60 = new javax.swing.JPanel();
        Fullscreenpetsuction = new javax.swing.JCheckBox();
        Fullscreengoldcoin = new javax.swing.JCheckBox();
        Fullscreensuction = new javax.swing.JCheckBox();
        Droptheitem = new javax.swing.JCheckBox();
        petsuction = new javax.swing.JTextField();
        jLabel78 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        jLabel80 = new javax.swing.JLabel();
        jLabel102 = new javax.swing.JLabel();
        jLabel103 = new javax.swing.JLabel();
        jPanel44 = new javax.swing.JPanel();
        a13 = new javax.swing.JLabel();
        a14 = new javax.swing.JLabel();
        Strangeexplosivepointvolume = new javax.swing.JCheckBox();
        Strangeexplosionresistance = new javax.swing.JCheckBox();
        Monsterdroproll = new javax.swing.JTextField();
        Monsterdropoffset = new javax.swing.JTextField();
        jPanel79 = new javax.swing.JPanel();
        applAttackRange = new javax.swing.JCheckBox();
        applAttackNumber = new javax.swing.JCheckBox();
        applAttackmobcount = new javax.swing.JCheckBox();
        applAttackMP = new javax.swing.JCheckBox();
        jPanel51 = new javax.swing.JPanel();
        jPanel52 = new javax.swing.JPanel();
        expRate = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        dropRate = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        mesoRate = new javax.swing.JTextField();
        events = new javax.swing.JTextField();
        ServerMessage = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        maxlevel = new javax.swing.JTextField();
        kocmaxlevel = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        Stackquantity = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        flag = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        maxCharacters = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        channelCount = new javax.swing.JTextField();
        jLabel55 = new javax.swing.JLabel();
        LoginPort = new javax.swing.JTextField();
        jLabel58 = new javax.swing.JLabel();
        ChannelPort = new javax.swing.JTextField();
        jLabel60 = new javax.swing.JLabel();
        CashPort = new javax.swing.JTextField();
        jLabel63 = new javax.swing.JLabel();
        RSGS = new javax.swing.JTextField();
        jLabel87 = new javax.swing.JLabel();
        bossRate = new javax.swing.JTextField();
        petRate = new javax.swing.JTextField();
        jLabel99 = new javax.swing.JLabel();
        jLabel100 = new javax.swing.JLabel();
        statLimit = new javax.swing.JTextField();
        defaultInventorySlot = new javax.swing.JTextField();
        jLabel101 = new javax.swing.JLabel();
        bankHandlingFee = new javax.swing.JTextField();
        jLabel146 = new javax.swing.JLabel();
        takeOutHandlingFee = new javax.swing.JTextField();
        jLabel147 = new javax.swing.JLabel();
        jLabel164 = new javax.swing.JLabel();
        Fishingexperienceratio = new javax.swing.JTextField();
        jLabel165 = new javax.swing.JLabel();
        Fishinggoldcoinsseveral = new javax.swing.JTextField();
        jLabel166 = new javax.swing.JLabel();
        CreateGuildCost = new javax.swing.JTextField();
        createEmblemMoney = new javax.swing.JTextField();
        jLabel167 = new javax.swing.JLabel();
        jLabel168 = new javax.swing.JLabel();
        Automaticcleaning = new javax.swing.JTextField();
        eventMessage = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jLabel177 = new javax.swing.JLabel();
        BEGINNER_SPAWN_MAP = new javax.swing.JTextField();
        jLabel148 = new javax.swing.JLabel();
        Servermaintenanceprompt = new javax.swing.JTextField();
        jLabel178 = new javax.swing.JLabel();
        Turnoffserverprompt = new javax.swing.JTextField();
        jLabel179 = new javax.swing.JLabel();
        Accountnumberprohibitionprompt = new javax.swing.JTextField();
        jPanel83 = new javax.swing.JPanel();
        jLabel88 = new javax.swing.JLabel();
        jButton10 = new javax.swing.JButton();
        Openmultiplayermap = new javax.swing.JCheckBox();
        monsterSpawn = new javax.swing.JTextField();
        jLabel98 = new javax.swing.JLabel();
        createMobInterval = new javax.swing.JTextField();
        jScrollPane9 = new javax.swing.JScrollPane();
        Custommultiplayermap = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        expRate69 = new javax.swing.JTextField();
        jLabel51 = new javax.swing.JLabel();
        expRate139 = new javax.swing.JTextField();
        jLabel57 = new javax.swing.JLabel();
        expRate179 = new javax.swing.JTextField();
        jLabel62 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        expRate149 = new javax.swing.JTextField();
        jLabel65 = new javax.swing.JLabel();
        expRate189 = new javax.swing.JTextField();
        expRate89 = new javax.swing.JTextField();
        expRate159 = new javax.swing.JTextField();
        expRate200 = new javax.swing.JTextField();
        expRate109 = new javax.swing.JTextField();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        expRate129 = new javax.swing.JTextField();
        expRate169 = new javax.swing.JTextField();
        expRate250 = new javax.swing.JTextField();
        jLabel69 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        jPanel78 = new javax.swing.JPanel();
        jLabel265 = new javax.swing.JLabel();
        jLabel274 = new javax.swing.JLabel();
        Doubleburstchannel = new javax.swing.JCheckBox();
        Channelnumber = new javax.swing.JTextField();
        Doubleexplosionchannel = new javax.swing.JTextField();
        jPanel64 = new javax.swing.JPanel();
        jPanel42 = new javax.swing.JPanel();
        jLabel48 = new javax.swing.JLabel();
        personalPvpMap = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        partyPvpMap = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        guildPvpMap = new javax.swing.JTextField();
        jPanel63 = new javax.swing.JPanel();
        Employmentexperience = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        Marriageexperience = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        Weakengold = new javax.swing.JTextField();
        jLabel53 = new javax.swing.JLabel();
        jPanel66 = new javax.swing.JPanel();
        jPanel72 = new javax.swing.JPanel();
        飞侠攻击力 = new javax.swing.JTextField();
        jLabel104 = new javax.swing.JLabel();
        海盗敏捷 = new javax.swing.JTextField();
        jLabel105 = new javax.swing.JLabel();
        端内破功管理员破功值 = new javax.swing.JTextField();
        海盗智力 = new javax.swing.JTextField();
        jLabel107 = new javax.swing.JLabel();
        jLabel108 = new javax.swing.JLabel();
        破功石增加伤害 = new javax.swing.JTextField();
        海盗运气 = new javax.swing.JTextField();
        jLabel109 = new javax.swing.JLabel();
        jLabel110 = new javax.swing.JLabel();
        破功伤害浮动值 = new javax.swing.JTextField();
        海盗攻击力 = new javax.swing.JTextField();
        jLabel111 = new javax.swing.JLabel();
        jLabel112 = new javax.swing.JLabel();
        战士力量 = new javax.swing.JTextField();
        jLabel113 = new javax.swing.JLabel();
        法师力量 = new javax.swing.JTextField();
        jLabel114 = new javax.swing.JLabel();
        战神敏捷 = new javax.swing.JTextField();
        jLabel115 = new javax.swing.JLabel();
        战神智力 = new javax.swing.JTextField();
        jLabel116 = new javax.swing.JLabel();
        飞侠力量 = new javax.swing.JTextField();
        战神运气 = new javax.swing.JTextField();
        jLabel120 = new javax.swing.JLabel();
        jLabel121 = new javax.swing.JLabel();
        弓手力量 = new javax.swing.JTextField();
        战神攻击力 = new javax.swing.JTextField();
        jLabel122 = new javax.swing.JLabel();
        jLabel123 = new javax.swing.JLabel();
        海盗力量 = new javax.swing.JTextField();
        法师敏捷 = new javax.swing.JTextField();
        jLabel124 = new javax.swing.JLabel();
        jLabel125 = new javax.swing.JLabel();
        战神力量 = new javax.swing.JTextField();
        jLabel126 = new javax.swing.JLabel();
        战士敏捷 = new javax.swing.JTextField();
        jLabel127 = new javax.swing.JLabel();
        法师智力 = new javax.swing.JTextField();
        jLabel128 = new javax.swing.JLabel();
        法师运气 = new javax.swing.JTextField();
        jLabel129 = new javax.swing.JLabel();
        战士智力 = new javax.swing.JTextField();
        法师魔法力 = new javax.swing.JTextField();
        jLabel130 = new javax.swing.JLabel();
        jLabel131 = new javax.swing.JLabel();
        战士运气 = new javax.swing.JTextField();
        jLabel132 = new javax.swing.JLabel();
        jLabel133 = new javax.swing.JLabel();
        战士攻击力 = new javax.swing.JTextField();
        jLabel134 = new javax.swing.JLabel();
        弓手敏捷 = new javax.swing.JTextField();
        jLabel135 = new javax.swing.JLabel();
        弓手智力 = new javax.swing.JTextField();
        jLabel136 = new javax.swing.JLabel();
        弓手运气 = new javax.swing.JTextField();
        jLabel137 = new javax.swing.JLabel();
        弓手攻击力 = new javax.swing.JTextField();
        jLabel138 = new javax.swing.JLabel();
        飞侠敏捷 = new javax.swing.JTextField();
        jLabel139 = new javax.swing.JLabel();
        飞侠智力 = new javax.swing.JTextField();
        端内破功最高伤害上限 = new javax.swing.JTextField();
        jLabel140 = new javax.swing.JLabel();
        jLabel141 = new javax.swing.JLabel();
        飞侠运气 = new javax.swing.JTextField();
        端内物理职业破功开关 = new javax.swing.JButton();
        jLabel142 = new javax.swing.JLabel();
        端内魔法职业破功开关 = new javax.swing.JButton();
        jLabel143 = new javax.swing.JLabel();
        jLabel144 = new javax.swing.JLabel();
        jLabel145 = new javax.swing.JLabel();
        jPanel23 = new javax.swing.JPanel();
        jPanel68 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        开启双倍经验 = new javax.swing.JButton();
        双倍经验持续时间 = new javax.swing.JTextField();
        jLabel359 = new javax.swing.JLabel();
        开启双倍爆率 = new javax.swing.JButton();
        双倍爆率持续时间 = new javax.swing.JTextField();
        jLabel360 = new javax.swing.JLabel();
        开启双倍金币 = new javax.swing.JButton();
        双倍金币持续时间 = new javax.swing.JTextField();
        jLabel361 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        开启三倍经验 = new javax.swing.JButton();
        三倍经验持续时间 = new javax.swing.JTextField();
        jLabel362 = new javax.swing.JLabel();
        开启三倍爆率 = new javax.swing.JButton();
        三倍爆率持续时间 = new javax.swing.JTextField();
        jLabel348 = new javax.swing.JLabel();
        开启三倍金币 = new javax.swing.JButton();
        三倍金币持续时间 = new javax.swing.JTextField();
        jLabel349 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel61 = new javax.swing.JPanel();
        pdkg = new javax.swing.JCheckBox();
        pdzsdj = new javax.swing.JTextField();
        jLabel149 = new javax.swing.JLabel();
        jLabel150 = new javax.swing.JLabel();
        pdzddj = new javax.swing.JTextField();
        pdzsjb = new javax.swing.JTextField();
        jLabel151 = new javax.swing.JLabel();
        pdzdjb = new javax.swing.JTextField();
        jLabel152 = new javax.swing.JLabel();
        pdzsdd = new javax.swing.JTextField();
        jLabel153 = new javax.swing.JLabel();
        pdzddd = new javax.swing.JTextField();
        jLabel154 = new javax.swing.JLabel();
        pdzddy = new javax.swing.JTextField();
        jLabel155 = new javax.swing.JLabel();
        jLabel156 = new javax.swing.JLabel();
        pdzsdy = new javax.swing.JTextField();
        pdmap = new javax.swing.JTextField();
        jLabel157 = new javax.swing.JLabel();
        jLabel158 = new javax.swing.JLabel();
        pdexp = new javax.swing.JTextField();
        jLabel159 = new javax.swing.JLabel();
        jLabel160 = new javax.swing.JLabel();
        jLabel161 = new javax.swing.JLabel();
        dyjy = new javax.swing.JTextField();
        jLabel162 = new javax.swing.JLabel();
        jLabel163 = new javax.swing.JLabel();
        dyjb = new javax.swing.JTextField();
        jPanel62 = new javax.swing.JPanel();
        jLabel169 = new javax.swing.JLabel();
        Offlinehangup = new javax.swing.JCheckBox();
        jLabel170 = new javax.swing.JLabel();
        offlinemap = new javax.swing.JTextField();
        jLabel171 = new javax.swing.JLabel();
        Offlinecouponcounting = new javax.swing.JTextField();
        jLabel172 = new javax.swing.JLabel();
        Offlineoffset = new javax.swing.JTextField();
        jLabel173 = new javax.swing.JLabel();
        Offlinegoldcoins = new javax.swing.JTextField();
        jLabel174 = new javax.swing.JLabel();
        Offlineexperience = new javax.swing.JTextField();
        jLabel175 = new javax.swing.JLabel();
        OfflineDoudou = new javax.swing.JTextField();
        jLabel176 = new javax.swing.JLabel();
        jPanel40 = new javax.swing.JPanel();
        jButton16 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jPanel22 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        sendNotice = new javax.swing.JButton();
        sendWinNotice = new javax.swing.JButton();
        sendMsgNotice = new javax.swing.JButton();
        sendNpcTalkNotice = new javax.swing.JButton();
        noticeText = new javax.swing.JTextField();
        jLabel117 = new javax.swing.JLabel();
        jLabel118 = new javax.swing.JLabel();
        jLabel119 = new javax.swing.JLabel();
        jLabel106 = new javax.swing.JLabel();
        公告发布喇叭代码 = new javax.swing.JTextField();
        jButton45 = new javax.swing.JButton();
        jLabel259 = new javax.swing.JLabel();
        jPanel25 = new javax.swing.JPanel();
        显示在线账号 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        accountstable = new javax.swing.JTable();
        账号搜索 = new javax.swing.JButton();
        jTextField23 = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jButton11 = new javax.swing.JButton();
        输入0 = new javax.swing.JTextField();
        输入2 = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        输入3 = new javax.swing.JTextField();
        输入1 = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        输入4 = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jButton19 = new javax.swing.JButton();
        jButton20 = new javax.swing.JButton();
        jButton24 = new javax.swing.JButton();
        jLabel43 = new javax.swing.JLabel();
        账号总数标签 = new javax.swing.JLabel();
        acct = new javax.swing.JLabel();
        jButton56 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jPanel26 = new javax.swing.JPanel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel43 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        characterstable = new javax.swing.JTable();
        jButton26 = new javax.swing.JButton();
        jButton27 = new javax.swing.JButton();
        jTextField24 = new javax.swing.JTextField();
        jLabel75 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        角色总数标签 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel24 = new javax.swing.JPanel();
        角色10 = new javax.swing.JTextField();
        角色11 = new javax.swing.JTextField();
        jLabel91 = new javax.swing.JLabel();
        角色12 = new javax.swing.JTextField();
        jLabel92 = new javax.swing.JLabel();
        角色13 = new javax.swing.JTextField();
        jLabel93 = new javax.swing.JLabel();
        角色ID = new javax.swing.JTextField();
        jLabel94 = new javax.swing.JLabel();
        jLabel97 = new javax.swing.JLabel();
        jLabel95 = new javax.swing.JLabel();
        jLabel96 = new javax.swing.JLabel();
        角色0 = new javax.swing.JTextField();
        角色1 = new javax.swing.JTextField();
        角色2 = new javax.swing.JTextField();
        角色3 = new javax.swing.JTextField();
        jLabel81 = new javax.swing.JLabel();
        角色4 = new javax.swing.JTextField();
        jLabel82 = new javax.swing.JLabel();
        角色5 = new javax.swing.JTextField();
        jLabel83 = new javax.swing.JLabel();
        角色6 = new javax.swing.JTextField();
        jLabel84 = new javax.swing.JLabel();
        角色7 = new javax.swing.JTextField();
        jLabel85 = new javax.swing.JLabel();
        角色8 = new javax.swing.JTextField();
        jLabel86 = new javax.swing.JLabel();
        角色9 = new javax.swing.JTextField();
        jLabel89 = new javax.swing.JLabel();
        jLabel90 = new javax.swing.JLabel();
        jButton30 = new javax.swing.JButton();
        jButton35 = new javax.swing.JButton();
        jPanel46 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel47 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jPanel48 = new javax.swing.JPanel();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jPanel50 = new javax.swing.JPanel();
        jButton14 = new javax.swing.JButton();
        jButton28 = new javax.swing.JButton();
        jPanel53 = new javax.swing.JPanel();
        jPanel56 = new javax.swing.JPanel();
        jButton33 = new javax.swing.JButton();
        jButton38 = new javax.swing.JButton();
        jButton40 = new javax.swing.JButton();
        jButton48 = new javax.swing.JButton();
        jButton55 = new javax.swing.JButton();
        jButton57 = new javax.swing.JButton();
        jButton58 = new javax.swing.JButton();
        jButton59 = new javax.swing.JButton();
        jButton60 = new javax.swing.JButton();
        jButton63 = new javax.swing.JButton();
        jButton64 = new javax.swing.JButton();
        jPanel33 = new javax.swing.JPanel();
        jScrollPane14 = new javax.swing.JScrollPane();
        jTextArea5 = new javax.swing.JTextArea();
        jButton61 = new javax.swing.JButton();
        jButton62 = new javax.swing.JButton();
        jTextField25 = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        jPanel45 = new javax.swing.JPanel();
        资源页签 = new javax.swing.JTabbedPane();
        jPanel57 = new javax.swing.JPanel();
        jScrollPane16 = new javax.swing.JScrollPane();
        身上 = new javax.swing.JTable();
        jPanel69 = new javax.swing.JPanel();
        jScrollPane15 = new javax.swing.JScrollPane();
        装备 = new javax.swing.JTable();
        jPanel70 = new javax.swing.JPanel();
        jScrollPane17 = new javax.swing.JScrollPane();
        消耗 = new javax.swing.JTable();
        jPanel71 = new javax.swing.JPanel();
        jScrollPane18 = new javax.swing.JScrollPane();
        设置 = new javax.swing.JTable();
        jPanel73 = new javax.swing.JPanel();
        jScrollPane19 = new javax.swing.JScrollPane();
        其他 = new javax.swing.JTable();
        jPanel74 = new javax.swing.JPanel();
        jScrollPane20 = new javax.swing.JScrollPane();
        特殊 = new javax.swing.JTable();
        jPanel75 = new javax.swing.JPanel();
        jScrollPane21 = new javax.swing.JScrollPane();
        仓库 = new javax.swing.JTable();
        jPanel76 = new javax.swing.JPanel();
        jScrollPane22 = new javax.swing.JScrollPane();
        商城 = new javax.swing.JTable();
        jPanel65 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jButton34 = new javax.swing.JButton();
        jButton65 = new javax.swing.JButton();
        jPanel58 = new javax.swing.JPanel();
        jScrollPane23 = new javax.swing.JScrollPane();
        技能信息 = new javax.swing.JTable();
        jPanel77 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        jButton66 = new javax.swing.JButton();
        jButton67 = new javax.swing.JButton();
        jPanel27 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        全服发送物品代码 = new javax.swing.JTextField();
        全服发送物品数量 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        给予物品1 = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jLabel241 = new javax.swing.JLabel();
        个人发送物品玩家名字 = new javax.swing.JTextField();
        jLabel242 = new javax.swing.JLabel();
        个人发送物品代码 = new javax.swing.JTextField();
        jLabel240 = new javax.swing.JLabel();
        个人发送物品数量 = new javax.swing.JTextField();
        给予物品 = new javax.swing.JButton();
        jPanel34 = new javax.swing.JPanel();
        z7 = new javax.swing.JButton();
        z8 = new javax.swing.JButton();
        z9 = new javax.swing.JButton();
        z10 = new javax.swing.JButton();
        z11 = new javax.swing.JButton();
        z12 = new javax.swing.JButton();
        a2 = new javax.swing.JTextField();
        jLabel236 = new javax.swing.JLabel();
        个人发送物品玩家名字1 = new javax.swing.JTextField();
        jLabel59 = new javax.swing.JLabel();
        jPanel38 = new javax.swing.JPanel();
        z2 = new javax.swing.JButton();
        z3 = new javax.swing.JButton();
        z1 = new javax.swing.JButton();
        z4 = new javax.swing.JButton();
        z5 = new javax.swing.JButton();
        z6 = new javax.swing.JButton();
        a1 = new javax.swing.JTextField();
        jLabel235 = new javax.swing.JLabel();
        jPanel39 = new javax.swing.JPanel();
        全服发送装备装备加卷 = new javax.swing.JTextField();
        全服发送装备装备制作人 = new javax.swing.JTextField();
        全服发送装备装备力量 = new javax.swing.JTextField();
        全服发送装备装备MP = new javax.swing.JTextField();
        全服发送装备装备智力 = new javax.swing.JTextField();
        全服发送装备装备运气 = new javax.swing.JTextField();
        全服发送装备装备HP = new javax.swing.JTextField();
        全服发送装备装备攻击力 = new javax.swing.JTextField();
        全服发送装备装备给予时间 = new javax.swing.JTextField();
        全服发送装备装备可否交易 = new javax.swing.JTextField();
        全服发送装备装备敏捷 = new javax.swing.JTextField();
        全服发送装备物品ID = new javax.swing.JTextField();
        全服发送装备装备魔法力 = new javax.swing.JTextField();
        全服发送装备装备魔法防御 = new javax.swing.JTextField();
        全服发送装备装备物理防御 = new javax.swing.JTextField();
        给予装备1 = new javax.swing.JButton();
        jLabel219 = new javax.swing.JLabel();
        jLabel220 = new javax.swing.JLabel();
        jLabel221 = new javax.swing.JLabel();
        jLabel222 = new javax.swing.JLabel();
        jLabel223 = new javax.swing.JLabel();
        jLabel224 = new javax.swing.JLabel();
        jLabel225 = new javax.swing.JLabel();
        jLabel226 = new javax.swing.JLabel();
        jLabel227 = new javax.swing.JLabel();
        jLabel228 = new javax.swing.JLabel();
        jLabel229 = new javax.swing.JLabel();
        jLabel230 = new javax.swing.JLabel();
        jLabel231 = new javax.swing.JLabel();
        jLabel232 = new javax.swing.JLabel();
        jLabel233 = new javax.swing.JLabel();
        发送装备玩家姓名 = new javax.swing.JTextField();
        给予装备2 = new javax.swing.JButton();
        jLabel246 = new javax.swing.JLabel();
        全服发送装备装备潜能3 = new javax.swing.JTextField();
        全服发送装备装备潜能1 = new javax.swing.JTextField();
        全服发送装备装备潜能2 = new javax.swing.JTextField();
        jLabel72 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        qiannengdaima = new javax.swing.JButton();
        装备星星1 = new javax.swing.JTextField();
        jLabel77 = new javax.swing.JLabel();
        jPanel35 = new javax.swing.JPanel();
        jPanel54 = new javax.swing.JPanel();
        jButton69 = new javax.swing.JButton();
        jButton70 = new javax.swing.JButton();
        jButton72 = new javax.swing.JButton();
        jButton73 = new javax.swing.JButton();
        jButton74 = new javax.swing.JButton();
        jButton75 = new javax.swing.JButton();
        jButton76 = new javax.swing.JButton();
        jPanel55 = new javax.swing.JPanel();
        jButton31 = new javax.swing.JButton();
        jButton22 = new javax.swing.JButton();
        jButton23 = new javax.swing.JButton();
        jButton29 = new javax.swing.JButton();
        jButton39 = new javax.swing.JButton();
        jButton44 = new javax.swing.JButton();
        jButton51 = new javax.swing.JButton();
        jButton53 = new javax.swing.JButton();
        jPanel67 = new javax.swing.JPanel();
        jButton25 = new javax.swing.JButton();
        jButton32 = new javax.swing.JButton();
        jButton46 = new javax.swing.JButton();
        jButton49 = new javax.swing.JButton();
        jButton36 = new javax.swing.JButton();
        jButton37 = new javax.swing.JButton();
        jButton50 = new javax.swing.JButton();
        jButton52 = new javax.swing.JButton();
        jButton54 = new javax.swing.JButton();
        jButton68 = new javax.swing.JButton();
        jPanel36 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jPanel37 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        ActiveThread = new javax.swing.JLabel();
        RunStats = new javax.swing.JLabel();
        RunTime = new javax.swing.JLabel();
        内存使用 = new javax.swing.JLabel();
        内存条 = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jTabbedPane.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        jTabbedPane.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTabbedPane.setFont(new java.awt.Font("宋体", 1, 18)); // NOI18N

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        重载副本按钮.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/3801309.png"))); // NOI18N
        重载副本按钮.setText("重载副本");
        重载副本按钮.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                重载副本按钮ActionPerformed(evt);
            }
        });

        重载爆率按钮.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/3801310.png"))); // NOI18N
        重载爆率按钮.setText("重载爆率");
        重载爆率按钮.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                重载爆率按钮ActionPerformed(evt);
            }
        });

        重载传送门按钮.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/3801312.png"))); // NOI18N
        重载传送门按钮.setText("重载传送");
        重载传送门按钮.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                重载传送门按钮ActionPerformed(evt);
            }
        });

        重载商店按钮.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/3801315.png"))); // NOI18N
        重载商店按钮.setText("重载商店");
        重载商店按钮.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                重载商店按钮ActionPerformed(evt);
            }
        });

        重载包头按钮.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/3801314.png"))); // NOI18N
        重载包头按钮.setText("重载包头");
        重载包头按钮.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                重载包头按钮ActionPerformed(evt);
            }
        });

        重载任务.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/3801313.png"))); // NOI18N
        重载任务.setText("重载任务");
        重载任务.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                重载任务ActionPerformed(evt);
            }
        });

        重载反应堆按钮.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/3801462.png"))); // NOI18N
        重载反应堆按钮.setText("重载反应堆");
        重载反应堆按钮.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                重载反应堆按钮ActionPerformed(evt);
            }
        });

        重载商城按钮.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/3801316.png"))); // NOI18N
        重载商城按钮.setText("重载商城");
        重载商城按钮.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                重载商城按钮ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(重载副本按钮, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(重载爆率按钮)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(重载传送门按钮)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(重载任务)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(重载包头按钮)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(重载商店按钮)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(重载商城按钮)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(重载反应堆按钮)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(重载商店按钮, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                    .addComponent(重载反应堆按钮, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(重载任务, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(重载包头按钮, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(重载传送门按钮, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(重载爆率按钮, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(重载副本按钮, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(重载商城按钮, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        保存雇佣按钮.setText("保存雇佣");
        保存雇佣按钮.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                保存雇佣按钮ActionPerformed(evt);
            }
        });

        保存数据按钮.setText("保存数据");
        保存数据按钮.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                保存数据按钮ActionPerformed(evt);
            }
        });

        查询在线玩家人数按钮.setText("在线人数");
        查询在线玩家人数按钮.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                查询在线玩家人数按钮ActionPerformed(evt);
            }
        });

        jButton1.setForeground(new java.awt.Color(255, 0, 0));
        jButton1.setText("开启服务端");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton21.setText("关闭服务端");
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });

        jButton17.setText("回收内存");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(保存雇佣按钮, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(保存数据按钮, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(查询在线玩家人数按钮, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton21, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(302, 302, 302))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(查询在线玩家人数按钮, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(保存数据按钮, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(保存雇佣按钮, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(jButton21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        output_jTextPane.setBackground(new java.awt.Color(0, 0, 0));
        output_jTextPane.setForeground(new java.awt.Color(255, 255, 255));
        output_jTextPane.setToolTipText("");
        output_jTextPane.setCaretColor(new java.awt.Color(0, 255, 0));
        output_jTextPane.setDisabledTextColor(new java.awt.Color(255, 153, 51));
        output_jTextPane.setFocusTraversalPolicyProvider(true);
        output_jTextPane.setInheritsPopupMenu(true);
        jScrollPane2.setViewportView(output_jTextPane);

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 975, Short.MAX_VALUE)
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 456, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("全部", jPanel28);

        output_packet_jTextPane.setEditable(false);
        output_packet_jTextPane.setBackground(new java.awt.Color(51, 0, 51));
        output_packet_jTextPane.setFont(new java.awt.Font("新宋体", 1, 14)); // NOI18N
        output_packet_jTextPane.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        output_packet_jTextPane.setFocusTraversalPolicyProvider(true);
        jScrollPane5.setViewportView(output_packet_jTextPane);

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 975, Short.MAX_VALUE)
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 456, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("数据包", jPanel29);

        output_notice_jTextPane.setEditable(false);
        output_notice_jTextPane.setBackground(new java.awt.Color(51, 0, 51));
        output_notice_jTextPane.setForeground(new java.awt.Color(51, 255, 204));
        output_notice_jTextPane.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        output_notice_jTextPane.setFocusTraversalPolicyProvider(true);
        output_notice_jTextPane.setInheritsPopupMenu(true);
        output_notice_jTextPane.setSelectedTextColor(new java.awt.Color(51, 0, 51));
        jScrollPane7.setViewportView(output_notice_jTextPane);

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 975, Short.MAX_VALUE)
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 456, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("提示", jPanel30);

        output_err_jTextPane.setEditable(false);
        output_err_jTextPane.setBackground(new java.awt.Color(51, 0, 51));
        output_err_jTextPane.setForeground(new java.awt.Color(255, 255, 255));
        output_err_jTextPane.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        output_err_jTextPane.setInheritsPopupMenu(true);
        output_err_jTextPane.setSelectedTextColor(new java.awt.Color(51, 0, 51));
        jScrollPane6.setViewportView(output_err_jTextPane);

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 975, Short.MAX_VALUE)
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 456, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("报错", jPanel31);

        output_out_jTextPane.setBackground(new java.awt.Color(51, 0, 51));
        output_out_jTextPane.setForeground(new java.awt.Color(0, 204, 204));
        output_out_jTextPane.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        output_out_jTextPane.setFocusCycleRoot(false);
        output_out_jTextPane.setFocusTraversalPolicyProvider(true);
        output_out_jTextPane.setInheritsPopupMenu(true);
        jScrollPane8.setViewportView(output_out_jTextPane);

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 975, Short.MAX_VALUE)
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 456, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("其他", jPanel32);

        jPanel41.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel4.setText("本机授权码:");

        javax.swing.GroupLayout jPanel41Layout = new javax.swing.GroupLayout(jPanel41);
        jPanel41.setLayout(jPanel41Layout);
        jPanel41Layout.setHorizontalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addGap(0, 0, 0)
                .addComponent(本机授权码))
        );
        jPanel41Layout.setVerticalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(本机授权码, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(12, 12, 12))
        );

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel41, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 485, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane.addTab("启动服务", new javax.swing.ImageIcon(getClass().getResource("/Image/Icon.png")), jPanel20); // NOI18N

        jPanel17.setBorder(javax.swing.BorderFactory.createTitledBorder("玩家在线监测"));

        jScrollPane12.setBackground(new java.awt.Color(255, 255, 255));

        playerTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "id", "名称", "等级", "职业", "当前所在地图", "点券", "抵用券", "金币"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        playerTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        playerTable.setGridColor(javax.swing.UIManager.getDefaults().getColor("Button.light"));
        playerTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane12.setViewportView(playerTable);
        if (playerTable.getColumnModel().getColumnCount() > 0) {
            playerTable.getColumnModel().getColumn(0).setHeaderValue("id");
            playerTable.getColumnModel().getColumn(1).setHeaderValue("名称");
            playerTable.getColumnModel().getColumn(2).setHeaderValue("等级");
            playerTable.getColumnModel().getColumn(3).setHeaderValue("职业");
            playerTable.getColumnModel().getColumn(4).setHeaderValue("当前所在地图");
            playerTable.getColumnModel().getColumn(5).setHeaderValue("点券");
            playerTable.getColumnModel().getColumn(6).setHeaderValue("抵用券");
            playerTable.getColumnModel().getColumn(7).setHeaderValue("金币");
        }

        jPanel59.setBorder(javax.swing.BorderFactory.createTitledBorder("便捷功能"));

        jButton71.setText("强制下线");
        jButton71.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton71ActionPerformed(evt);
            }
        });

        jButton77.setText("封号下线");
        jButton77.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton77ActionPerformed(evt);
            }
        });

        jButton78.setText("传送自由");
        jButton78.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton78ActionPerformed(evt);
            }
        });

        jButton79.setText("传送地图");
        jButton79.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton79ActionPerformed(evt);
            }
        });

        jButton80.setText("修改脸型");
        jButton80.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton80ActionPerformed(evt);
            }
        });

        jButton81.setText("修改发型");
        jButton81.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton81ActionPerformed(evt);
            }
        });

        jButton82.setText("加满技能");
        jButton82.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton82ActionPerformed(evt);
            }
        });

        jButton83.setText("开技能栏");
        jButton83.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton83ActionPerformed(evt);
            }
        });

        jButton84.setText("杀死玩家");
        jButton84.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton84ActionPerformed(evt);
            }
        });

        jButton85.setText("关小黑屋");
        jButton85.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton85ActionPerformed(evt);
            }
        });

        jButton86.setText("全员下线");
        jButton86.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton86ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel59Layout = new javax.swing.GroupLayout(jPanel59);
        jPanel59.setLayout(jPanel59Layout);
        jPanel59Layout.setHorizontalGroup(
            jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel59Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton71)
                    .addComponent(jButton86))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel59Layout.createSequentialGroup()
                        .addComponent(jButton82)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton83)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton84)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton85))
                    .addGroup(jPanel59Layout.createSequentialGroup()
                        .addComponent(jButton77)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton78)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton79)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton80)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton81)))
                .addContainerGap(418, Short.MAX_VALUE))
        );
        jPanel59Layout.setVerticalGroup(
            jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel59Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton78, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                    .addComponent(jButton77, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton79, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton81, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton80, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton71, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton84, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                    .addComponent(jButton83, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton82, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton86, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton85, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        在线人数.setText("在线人数:0");

        jLabel64.setText("当前选中玩家:");

        角色在线.setEditable(false);

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel59, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(285, 285, 285)
                .addComponent(jLabel64)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(角色在线, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(在线人数, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 968, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(475, 475, 475)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(在线人数)
                    .addComponent(jLabel64)
                    .addComponent(角色在线, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel59, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel17Layout.createSequentialGroup()
                    .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 464, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 166, Short.MAX_VALUE)))
        );

        jTabbedPane.addTab("在线监测", new javax.swing.ImageIcon(getClass().getResource("/Image/4030011.png")), jPanel17); // NOI18N

        jPanel21.setFont(new java.awt.Font("微软雅黑", 0, 18)); // NOI18N

        jPanel49.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "系统设置", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("宋体", 0, 12), new java.awt.Color(0, 153, 0))); // NOI18N

        jLabel23.setText("IP地址");

        jLabel19.setText("游戏名字");

        IP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IPActionPerformed(evt);
            }
        });

        jLabel52.setText("国服版本");

        jTextField22.setEditable(false);
        jTextField22.setText(String.valueOf(ServerConstants.MAPLE_VERSION) + "." + ServerConstants.MAPLE_PATCH);
        jTextField22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField22ActionPerformed(evt);
            }
        });

        jComboBox1.setModel(getMapleTypeModel());

        jLabel54.setText("所在国家/地区");

        logPackets.setText("封包模式");
        logPackets.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>开启后自动打印数据封包到Logs目录下<br> <br> <br> ");

        jLabel31.setText("服务端WZ路径");

        adminOnly.setText("管理模式");
        adminOnly.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>开启后只能管理员账号可以进入游戏<br> <br> <br> ");

        USE_FIXED_IV.setSelected(true);
        USE_FIXED_IV.setText("封包加密");
        USE_FIXED_IV.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>传送游戏数据封包是否加密<br> <br> <br> ");

        autoLieDetector.setText("自动测慌");
        autoLieDetector.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>用于控制游戏玩家长时间挂机打怪对其自动测谎<br> <br> <br> ");

        Use_Localhost.setText("单机模式");
        Use_Localhost.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>开启后服务端ip只能是127.0.0.1<br> <br> <br> ");

        AUTO_REGISTER.setText("自动注册");
        AUTO_REGISTER.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>用于控制游戏登录页填写账号密码即可注册<br> <br> <br> ");

        banallskill.setText("禁止BUFF");
        banallskill.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>用于控制游戏玩家释放BUFF类的技能<br> <br> <br> ");

        bangainexp.setText("禁止经验");
        bangainexp.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>用于控制游戏打怪是否给经验<br> <br> <br> ");

        bandropitem.setText("禁止爆物");

        CollegeSystem.setText("学院系统");
        CollegeSystem.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>用于控制游戏学院系统是否开启<br> <br> <br> ");
        CollegeSystem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CollegeSystemActionPerformed(evt);
            }
        });

        Forgingsystem.setText("锻造系统");
        Forgingsystem.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>用于控制游戏是否开启锻造系统<br> <br> <br> ");
        Forgingsystem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ForgingsystemActionPerformed(evt);
            }
        });

        enablepointsbuy.setText("游戏抵用");
        enablepointsbuy.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>用于控制游戏商城是否能用抵用券购买商品<br> <br> <br> ");

        marketUseBoard.setText("说话黑板");
        marketUseBoard.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>用于控制游戏玩家在自由说话默认的聊天框界面<br> <br> <br> ");
        marketUseBoard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                marketUseBoardActionPerformed(evt);
            }
        });

        Upgradeconsultation.setText("升级公告");
        Upgradeconsultation.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>用于控制游戏玩家升级了有公告提示<br> <br> <br> ");
        Upgradeconsultation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpgradeconsultationActionPerformed(evt);
            }
        });

        Injurytips.setText("受伤提示");
        Injurytips.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>用于控制游戏玩家受到伤害提示<br> <br> <br> ");
        Injurytips.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InjurytipsActionPerformed(evt);
            }
        });

        debugMode.setText("封包提示");
        debugMode.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>用于控制游戏所有封包打印到面板上<br> <br> <br> ");
        debugMode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                debugModeActionPerformed(evt);
            }
        });

        Playerchat.setText("玩家聊天");
        Playerchat.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>用于控制游戏玩家聊天功能开关<br> <br> <br> ");
        Playerchat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PlayerchatActionPerformed(evt);
            }
        });

        Throwoutgoldcoins.setText("丢出金币");
        Throwoutgoldcoins.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>用于控制游戏玩家是否能丢弃金币<br> <br> <br> ");
        Throwoutgoldcoins.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ThrowoutgoldcoinsActionPerformed(evt);
            }
        });

        Throwoutitems.setText("丢出物品");
        Throwoutitems.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>用于控制游戏玩家是否能丢弃物品<br> <br> <br> ");
        Throwoutitems.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ThrowoutitemsActionPerformed(evt);
            }
        });

        Overdrawingarchiving.setText("过图存档");
        Overdrawingarchiving.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>用于控制游戏玩家在自由说话默认的聊天框界面<br> <br> <br> ");
        Overdrawingarchiving.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OverdrawingarchivingActionPerformed(evt);
            }
        });

        Onlineannouncement.setText("上线公告");
        Onlineannouncement.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>用于控制游戏玩家上线是否公告提示<br> <br> <br> ");
        Onlineannouncement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OnlineannouncementActionPerformed(evt);
            }
        });

        Mapname.setText("地图名字");
        Mapname.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>用于控制游戏是否显示地图名称<br> <br> <br> ");
        Mapname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MapnameActionPerformed(evt);
            }
        });

        Gameinstructions.setText("游戏指令");
        Gameinstructions.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>用于控制游戏GM能不能使用命令<br> <br> <br> ");
        Gameinstructions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GameinstructionsActionPerformed(evt);
            }
        });

        Gamehorn.setText("游戏喇叭");
        Gamehorn.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>用于控制游戏喇叭道具开关<br> <br> <br> ");
        Gamehorn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GamehornActionPerformed(evt);
            }
        });

        Managestealth.setText("管理隐身");
        Managestealth.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>用于控制游戏GM上线是否拥有隐身BUFF<br> <br> <br> ");
        Managestealth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ManagestealthActionPerformed(evt);
            }
        });

        Managementacceleration.setText("管理加速");
        Managementacceleration.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>用于控制游戏GM上线是否拥有加速BUFF<br> <br> <br> ");
        Managementacceleration.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ManagementaccelerationActionPerformed(evt);
            }
        });

        Playertransaction.setText("玩家交易");
        Playertransaction.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>用于控制游戏玩家是否可以交易<br> <br> <br> ");
        Playertransaction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PlayertransactionActionPerformed(evt);
            }
        });

        Hiredbusinessman.setText("雇佣商人");
        Hiredbusinessman.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>用于控制游戏玩家是否可以在自由市场摆摊<br> <br> <br> ");
        Hiredbusinessman.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HiredbusinessmanActionPerformed(evt);
            }
        });

        Loginhelp.setText("游戏帮助");
        Loginhelp.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>用于控制游戏玩家进入游戏提示命令代码<br> <br> <br> ");
        Loginhelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoginhelpActionPerformed(evt);
            }
        });

        Monsterstate.setText("怪物状态");
        Monsterstate.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>用于提示玩家BOSS释放技能以及状态<br> <br> <br> ");
        Monsterstate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MonsterstateActionPerformed(evt);
            }
        });

        Auctionswitch.setText("游戏拍卖");
        Auctionswitch.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>用于控制拍卖功能是否打开<br> <br> <br> ");
        Auctionswitch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AuctionswitchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel49Layout = new javax.swing.GroupLayout(jPanel49);
        jPanel49.setLayout(jPanel49Layout);
        jPanel49Layout.setHorizontalGroup(
            jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel49Layout.createSequentialGroup()
                .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel49Layout.createSequentialGroup()
                        .addComponent(jLabel52)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel54)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(serverName, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(IP, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel31)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(wzpath, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel49Layout.createSequentialGroup()
                        .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel49Layout.createSequentialGroup()
                                .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(enablepointsbuy, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(AUTO_REGISTER, javax.swing.GroupLayout.Alignment.LEADING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(adminOnly, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(Forgingsystem)))
                            .addGroup(jPanel49Layout.createSequentialGroup()
                                .addComponent(Auctionswitch)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(Gamehorn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(USE_FIXED_IV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(CollegeSystem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Managestealth, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(autoLieDetector, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(marketUseBoard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Managementacceleration, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(Use_Localhost, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(Upgradeconsultation))
                            .addComponent(Playertransaction, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(logPackets, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Injurytips, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Hiredbusinessman, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(banallskill, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(debugMode))
                            .addComponent(Monsterstate))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Mapname, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(bangainexp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Playerchat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel49Layout.createSequentialGroup()
                                .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(bandropitem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(Throwoutgoldcoins, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel49Layout.createSequentialGroup()
                                        .addComponent(Overdrawingarchiving)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(Gameinstructions))
                                    .addGroup(jPanel49Layout.createSequentialGroup()
                                        .addComponent(Throwoutitems)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(Loginhelp))))
                            .addComponent(Onlineannouncement))))
                .addGap(0, 0, 0))
        );
        jPanel49Layout.setVerticalGroup(
            jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel49Layout.createSequentialGroup()
                .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel52)
                    .addComponent(jTextField22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel54)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(serverName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19)
                    .addComponent(jLabel23)
                    .addComponent(IP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31)
                    .addComponent(wzpath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AUTO_REGISTER)
                    .addComponent(adminOnly)
                    .addComponent(USE_FIXED_IV)
                    .addComponent(autoLieDetector)
                    .addComponent(Use_Localhost)
                    .addComponent(logPackets)
                    .addComponent(banallskill)
                    .addComponent(bangainexp)
                    .addComponent(bandropitem)
                    .addComponent(Overdrawingarchiving)
                    .addComponent(Gameinstructions))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(enablepointsbuy, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Forgingsystem)
                    .addComponent(CollegeSystem)
                    .addComponent(marketUseBoard)
                    .addComponent(Upgradeconsultation)
                    .addComponent(Injurytips)
                    .addComponent(debugMode)
                    .addComponent(Playerchat)
                    .addComponent(Throwoutgoldcoins)
                    .addComponent(Throwoutitems)
                    .addComponent(Loginhelp))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Gamehorn)
                    .addComponent(Managestealth)
                    .addComponent(Managementacceleration)
                    .addComponent(Playertransaction)
                    .addComponent(Hiredbusinessman)
                    .addComponent(Monsterstate)
                    .addComponent(Mapname)
                    .addComponent(Onlineannouncement)
                    .addComponent(Auctionswitch))
                .addContainerGap(59, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("常用功能"));

        Petsarenothungry.setText("宠物不饿");
        Petsarenothungry.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>用于控制游戏玩家宠物会不会饥饿<br> <br> <br> ");
        Petsarenothungry.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PetsarenothungryActionPerformed(evt);
            }
        });

        wirelessbuff.setText("无限buff");
        wirelessbuff.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>用于控制游戏玩家释放BUFF的时间<br> <br> <br> ");
        wirelessbuff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wirelessbuffActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Petsarenothungry)
                    .addComponent(wirelessbuff))
                .addGap(0, 0, 0))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(Petsarenothungry, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(wirelessbuff, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel16.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "数据库设置", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("宋体", 0, 12), new java.awt.Color(255, 153, 51))); // NOI18N

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("用戶名");

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("密码");

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("端口");

        jLabel41.setText("数据库");

        jLabel18.setText("IP");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(98, 98, 98)
                .addComponent(jLabel18)
                .addGap(18, 18, 18)
                .addComponent(SQL_IP, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(SQL_PORT, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel41)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(SQL_DATABASE, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SQL_USER, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SQL_PASSWORD, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel18)
                        .addComponent(SQL_IP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel17)
                        .addComponent(SQL_PORT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel15)
                        .addComponent(SQL_USER, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel16)
                        .addComponent(SQL_PASSWORD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel41)
                        .addComponent(SQL_DATABASE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "日志输出", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("宋体", 0, 12), new java.awt.Color(153, 153, 255))); // NOI18N

        logs_storage.setText("仓库日志");

        logs_npcshop_buy.setText("商店日志");
        logs_npcshop_buy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logs_npcshop_buyActionPerformed(evt);
            }
        });

        logs_mrechant.setText("雇佣日志");

        logs_csbuy.setText("商城日志");
        logs_csbuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logs_csbuyActionPerformed(evt);
            }
        });

        logs_trade.setText("交易日志");

        logs_chat.setText("聊天日志");

        logs_DAMAGE.setText("伤害日志");

        logs_PACKETS.setText("封包日志");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(logs_chat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(logs_csbuy, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(logs_npcshop_buy, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(logs_mrechant))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(logs_DAMAGE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(logs_PACKETS))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(logs_trade)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(logs_storage)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(logs_chat)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(logs_csbuy, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(logs_npcshop_buy, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(logs_DAMAGE)
                            .addComponent(logs_PACKETS)))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(logs_mrechant)
                        .addComponent(logs_trade)
                        .addComponent(logs_storage)))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "职业开放创建设置", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("宋体", 0, 12), new java.awt.Color(0, 153, 255))); // NOI18N

        adventurer.setText("冒险家");
        adventurer.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>用于控制游戏冒险家职业是否开启<br> <br> <br> ");
        adventurer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adventurerActionPerformed(evt);
            }
        });

        GodofWar.setText("战神");
        GodofWar.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>用于控制游戏战神职业是否开启<br> <br> <br> ");

        Knights.setText("骑士团");
        Knights.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>用于控制游戏骑士团职业是否开启<br> <br> <br> ");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(adventurer)
                .addGap(85, 85, 85)
                .addComponent(GodofWar)
                .addGap(77, 77, 77)
                .addComponent(Knights)
                .addGap(0, 0, 0))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Knights)
                            .addComponent(GodofWar)))
                    .addComponent(adventurer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "职业创建顺序界面", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("宋体", 0, 12), new java.awt.Color(0, 153, 153))); // NOI18N

        物品叠加开关6.setSelected(true);
        物品叠加开关6.setText("仿官顺序界面");

        物品叠加开关7.setText("特殊顺序界面");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(物品叠加开关6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(物品叠加开关7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(物品叠加开关6)
                    .addComponent(物品叠加开关7))
                .addGap(0, 0, 0))
        );

        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "切换游戏模式", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("宋体", 0, 12), new java.awt.Color(255, 51, 255))); // NOI18N

        物品叠加开关8.setSelected(true);
        物品叠加开关8.setText("自由模式");

        物品叠加开关9.setText("故事模式");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(物品叠加开关8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(物品叠加开关9)
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(物品叠加开关8)
                    .addComponent(物品叠加开关9))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel60.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "全屏宠吸配置", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("宋体", 0, 12), new java.awt.Color(255, 51, 204))); // NOI18N

        Fullscreenpetsuction.setText("全屏宠吸");

        Fullscreengoldcoin.setText("全屏吸金");

        Fullscreensuction.setText("全屏吸物");
        Fullscreensuction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FullscreensuctionActionPerformed(evt);
            }
        });

        Droptheitem.setText("物落脚下");
        Droptheitem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DroptheitemActionPerformed(evt);
            }
        });

        jLabel78.setForeground(new java.awt.Color(255, 51, 51));
        jLabel78.setText("全屏宠吸特别吃服务器配置,谨慎使用(占用资源多)");

        jLabel79.setForeground(new java.awt.Color(51, 255, 51));
        jLabel79.setText("开启后宠物全屏自动吸金币到背包");

        jLabel80.setForeground(new java.awt.Color(255, 51, 204));
        jLabel80.setText("开启后宠物全屏自动吸物品到背包");

        jLabel102.setForeground(new java.awt.Color(51, 204, 255));
        jLabel102.setText("推荐使用物落脚下功能（稳定，占用资源少）");

        jLabel103.setForeground(new java.awt.Color(0, 204, 204));
        jLabel103.setText("全屏宠吸道具/物落脚下功能开启需要道具ID");

        javax.swing.GroupLayout jPanel60Layout = new javax.swing.GroupLayout(jPanel60);
        jPanel60.setLayout(jPanel60Layout);
        jPanel60Layout.setHorizontalGroup(
            jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel60Layout.createSequentialGroup()
                .addComponent(petsuction, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel103))
            .addGroup(jPanel60Layout.createSequentialGroup()
                .addComponent(Droptheitem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel102, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel60Layout.createSequentialGroup()
                    .addComponent(Fullscreensuction)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jLabel80, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel60Layout.createSequentialGroup()
                    .addComponent(Fullscreengoldcoin)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jLabel79, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel60Layout.createSequentialGroup()
                    .addComponent(Fullscreenpetsuction)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jLabel78)))
        );
        jPanel60Layout.setVerticalGroup(
            jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel60Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Fullscreenpetsuction)
                    .addComponent(jLabel78))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Fullscreengoldcoin)
                    .addComponent(jLabel79))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Fullscreensuction)
                    .addComponent(jLabel80))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Droptheitem)
                    .addComponent(jLabel102))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(petsuction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel103))
                .addGap(0, 0, 0))
        );

        jPanel44.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "怪物掉落点卷", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("宋体", 0, 12), new java.awt.Color(0, 204, 204))); // NOI18N

        a13.setFont(new java.awt.Font("宋体", 0, 14)); // NOI18N
        a13.setText("怪物掉落点卷数量");

        a14.setFont(new java.awt.Font("宋体", 0, 14)); // NOI18N
        a14.setText("怪物掉落抵用数量");

        Strangeexplosivepointvolume.setText("打怪爆点券");

        Strangeexplosionresistance.setText("打怪爆抵用劵");

        javax.swing.GroupLayout jPanel44Layout = new javax.swing.GroupLayout(jPanel44);
        jPanel44.setLayout(jPanel44Layout);
        jPanel44Layout.setHorizontalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel44Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Strangeexplosionresistance)
                    .addComponent(Strangeexplosivepointvolume)
                    .addGroup(jPanel44Layout.createSequentialGroup()
                        .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(a13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(a14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Monsterdropoffset, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                            .addComponent(Monsterdroproll))))
                .addContainerGap())
        );
        jPanel44Layout.setVerticalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel44Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Strangeexplosivepointvolume)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Strangeexplosionresistance)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(a13, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Monsterdroproll, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(a14, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Monsterdropoffset, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel79.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "技能检测&用于防挂", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("宋体", 0, 12), new java.awt.Color(255, 0, 0))); // NOI18N

        applAttackRange.setForeground(new java.awt.Color(255, 51, 255));
        applAttackRange.setText("技能范围检测");
        applAttackRange.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>用于控制游戏玩家在游戏释放技能范围检测，如果范围与服务端不一致会攻击失效<br> <br> <br> ");

        applAttackNumber.setForeground(new java.awt.Color(255, 51, 51));
        applAttackNumber.setText("技能段数检测");
        applAttackNumber.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>用于控制游戏玩家在游戏释放技能段数检测，如果范围与服务端不一致会攻击失效<br> <br> <br> ");

        applAttackmobcount.setForeground(new java.awt.Color(0, 255, 0));
        applAttackmobcount.setText("攻击数量检测");
        applAttackmobcount.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>用于控制游戏玩家在游戏释放技能攻击怪物数量，如果范围与服务端不一致会攻击失效<br> <br> <br> ");

        applAttackMP.setForeground(new java.awt.Color(0, 204, 255));
        applAttackMP.setText("技能蓝耗检测");
        applAttackMP.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>用于控制游戏玩家在游戏释放技能耗蓝，如果范围与服务端不一致会攻击失效,主要针对一些外挂 无限蓝辅助<br> <br> <br> ");

        javax.swing.GroupLayout jPanel79Layout = new javax.swing.GroupLayout(jPanel79);
        jPanel79.setLayout(jPanel79Layout);
        jPanel79Layout.setHorizontalGroup(
            jPanel79Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel79Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(applAttackRange)
                .addGap(0, 0, 0)
                .addComponent(applAttackNumber)
                .addGap(0, 0, 0)
                .addComponent(applAttackmobcount)
                .addGap(0, 0, 0)
                .addComponent(applAttackMP)
                .addGap(0, 0, 0))
        );
        jPanel79Layout.setVerticalGroup(
            jPanel79Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel79Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel79Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(applAttackRange)
                    .addComponent(applAttackNumber)
                    .addComponent(applAttackmobcount)
                    .addComponent(applAttackMP)))
        );

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel49, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel12Layout.createSequentialGroup()
                                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel79, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, 0)
                        .addComponent(jPanel60, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(2, 2, 2))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(jPanel49, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel44, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel60, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addComponent(jPanel79, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );

        jTabbedPane6.addTab("系统设置", new javax.swing.ImageIcon(getClass().getResource("/Image/101.png")), jPanel12); // NOI18N

        jPanel52.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "游戏设置", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("宋体", 0, 12), new java.awt.Color(51, 204, 255))); // NOI18N

        jLabel5.setText("经验倍数");

        jLabel11.setText("爆率倍数");

        jLabel20.setText("金币倍数");

        jLabel22.setText("加载事件");

        jLabel21.setText("顶部公告");

        jLabel24.setText("最高等级");

        jLabel25.setText("骑士等级");

        Stackquantity.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>用于控制游戏物品最大的叠加数量<br> <br> <br> ");

        jLabel44.setText("叠加数量");

        jLabel45.setText("服务状态");

        jLabel46.setText("最大角色");

        jLabel47.setText("频道数量");

        jLabel55.setText("登录端口");

        jLabel58.setText("频道端口");

        jLabel60.setText("商城端口");

        jLabel63.setText("虚拟人数");

        jLabel87.setText("最大属性");

        jLabel99.setText("BOSS爆率");

        jLabel100.setText("宠物经验");

        statLimit.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>用于控制游戏能力值最大为多少<br> <br> <br> ");

        defaultInventorySlot.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>用于控制游戏创建角色的默认栏位<br> <br> <br> ");

        jLabel101.setText("默认栏位");

        bankHandlingFee.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>用于控制游戏玩家在仓库存取金币所收的手续费<br> <br> <br> ");

        jLabel146.setText("金币存取");

        takeOutHandlingFee.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>用于控制游戏玩家在仓库存放物品手续费<br> <br> <br> ");

        jLabel147.setText("寄存取物");

        jLabel164.setText("钓鱼经验");

        Fishingexperienceratio.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>用于控制游戏创建角色钓鱼的经验倍数<br> <br> <br> ");

        jLabel165.setText("钓鱼金币");

        Fishinggoldcoinsseveral.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>用于控制游戏创建角色钓鱼的金币倍数<br> <br> <br> ");

        jLabel166.setText("家族费用");

        CreateGuildCost.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>用于控制游戏创建家族需要的冒险币数量<br> <br> <br> ");

        createEmblemMoney.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>用于控制游戏创建和修改家族徽章需要的冒险币数量<br> <br> <br> ");

        jLabel167.setText("徽章费用");

        jLabel168.setText("清理地上");

        Automaticcleaning.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>用于控制游戏地上物品的最大数量 超过数值服务端自动清除<br> <br> <br> ");

        jLabel26.setText("选区公告");

        jLabel177.setText("出生地图");

        BEGINNER_SPAWN_MAP.setToolTipText("<html>\n<strong><font color=\"#FF0000\">功能说明</font></strong><br> \n<strong>用于控制游戏地上物品的最大数量 超过数值服务端自动清除<br> <br> <br> ");

        jLabel148.setText("维护公告");

        jLabel178.setText("关闭公告");

        jLabel179.setText("封号公告");

        javax.swing.GroupLayout jPanel52Layout = new javax.swing.GroupLayout(jPanel52);
        jPanel52.setLayout(jPanel52Layout);
        jPanel52Layout.setHorizontalGroup(
            jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel52Layout.createSequentialGroup()
                .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel52Layout.createSequentialGroup()
                        .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel52Layout.createSequentialGroup()
                                .addComponent(jLabel146)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bankHandlingFee, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel52Layout.createSequentialGroup()
                                    .addComponent(jLabel44)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(Stackquantity, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel52Layout.createSequentialGroup()
                                    .addComponent(jLabel24)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(maxlevel, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel52Layout.createSequentialGroup()
                                .addComponent(jLabel87)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(statLimit, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel101)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(defaultInventorySlot, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel164)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Fishingexperienceratio))
                            .addGroup(jPanel52Layout.createSequentialGroup()
                                .addComponent(jLabel147)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(takeOutHandlingFee, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel168)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Automaticcleaning, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel165)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Fishinggoldcoinsseveral))
                            .addGroup(jPanel52Layout.createSequentialGroup()
                                .addComponent(jLabel25)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(kocmaxlevel, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel63)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(RSGS, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel177)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BEGINNER_SPAWN_MAP))))
                    .addGroup(jPanel52Layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(events))
                    .addGroup(jPanel52Layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ServerMessage))
                    .addGroup(jPanel52Layout.createSequentialGroup()
                        .addComponent(jLabel45)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(flag, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel46)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(maxCharacters, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel47)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(channelCount, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel166)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(CreateGuildCost))
                    .addGroup(jPanel52Layout.createSequentialGroup()
                        .addComponent(jLabel55)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LoginPort, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel58)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ChannelPort, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel60)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(CashPort, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel167)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(createEmblemMoney))
                    .addGroup(jPanel52Layout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(eventMessage))
                    .addGroup(jPanel52Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(expRate, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dropRate, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel20)
                        .addGap(2, 2, 2)
                        .addComponent(mesoRate, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(jLabel99)
                        .addGap(2, 2, 2)
                        .addComponent(bossRate, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 5, Short.MAX_VALUE)
                        .addComponent(jLabel100, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(petRate, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel52Layout.createSequentialGroup()
                        .addComponent(jLabel148)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Servermaintenanceprompt))
                    .addGroup(jPanel52Layout.createSequentialGroup()
                        .addComponent(jLabel178)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Turnoffserverprompt))
                    .addGroup(jPanel52Layout.createSequentialGroup()
                        .addComponent(jLabel179)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Accountnumberprohibitionprompt)))
                .addContainerGap())
        );
        jPanel52Layout.setVerticalGroup(
            jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel52Layout.createSequentialGroup()
                .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(expRate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(dropRate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(mesoRate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20)
                    .addComponent(jLabel99)
                    .addComponent(bossRate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(petRate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel100))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(events))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(eventMessage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26))
                .addGap(4, 4, 4)
                .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ServerMessage)
                    .addComponent(jLabel21))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Servermaintenanceprompt)
                    .addComponent(jLabel148))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Turnoffserverprompt)
                    .addComponent(jLabel178))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Accountnumberprohibitionprompt)
                    .addComponent(jLabel179))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(channelCount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel47)
                        .addComponent(jLabel166)
                        .addComponent(CreateGuildCost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(flag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel45)
                        .addComponent(maxCharacters, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel46)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LoginPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel55)
                    .addComponent(ChannelPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel58)
                    .addComponent(CashPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel60)
                    .addComponent(jLabel167)
                    .addComponent(createEmblemMoney, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(maxlevel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24)
                    .addComponent(kocmaxlevel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25)
                    .addComponent(RSGS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel63)
                    .addComponent(jLabel177)
                    .addComponent(BEGINNER_SPAWN_MAP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel101)
                        .addComponent(defaultInventorySlot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel164)
                        .addComponent(Fishingexperienceratio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(Stackquantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel44)
                        .addComponent(jLabel87)
                        .addComponent(statLimit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel168)
                        .addComponent(Automaticcleaning, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel165)
                        .addComponent(Fishinggoldcoinsseveral, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel147)
                        .addComponent(takeOutHandlingFee, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel52Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel146)
                        .addComponent(bankHandlingFee, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jPanel83.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "自定义地图刷怪设置[地图列表id( | 隔开)]", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("宋体", 0, 12), new java.awt.Color(153, 204, 0))); // NOI18N

        jLabel88.setFont(new java.awt.Font("宋体", 0, 14)); // NOI18N
        jLabel88.setText("自定义地图刷怪倍数:");

        jButton10.setText("功能说明");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        Openmultiplayermap.setText("倍怪开关");

        jLabel98.setFont(new java.awt.Font("宋体", 0, 14)); // NOI18N
        jLabel98.setText("怪物重生时间毫秒级 1000=1秒");

        Custommultiplayermap.setColumns(20);
        Custommultiplayermap.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        Custommultiplayermap.setForeground(new java.awt.Color(153, 153, 153));
        Custommultiplayermap.setLineWrap(true);
        Custommultiplayermap.setToolTipText("");
        Custommultiplayermap.setDoubleBuffered(true);
        Custommultiplayermap.setInheritsPopupMenu(true);
        Custommultiplayermap.setSelectedTextColor(new java.awt.Color(51, 0, 51));
        jScrollPane9.setViewportView(Custommultiplayermap);

        javax.swing.GroupLayout jPanel83Layout = new javax.swing.GroupLayout(jPanel83);
        jPanel83.setLayout(jPanel83Layout);
        jPanel83Layout.setHorizontalGroup(
            jPanel83Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel83Layout.createSequentialGroup()
                .addGroup(jPanel83Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel83Layout.createSequentialGroup()
                        .addGap(162, 162, 162)
                        .addComponent(jButton10))
                    .addGroup(jPanel83Layout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addGroup(jPanel83Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel83Layout.createSequentialGroup()
                                .addComponent(Openmultiplayermap)
                                .addGap(26, 26, 26)
                                .addComponent(jLabel88)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(monsterSpawn, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel83Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel98)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(createMobInterval, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel83Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 438, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel83Layout.setVerticalGroup(
            jPanel83Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel83Layout.createSequentialGroup()
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel83Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Openmultiplayermap, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(monsterSpawn)
                    .addComponent(jLabel88, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel83Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(createMobInterval, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel98, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton10)
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "分段式经验倍率", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("宋体", 0, 12), new java.awt.Color(51, 255, 153))); // NOI18N

        jLabel51.setText("010-069级");

        jLabel57.setText("129-139级");

        jLabel62.setText("169-179级");

        jLabel56.setText("069-089级");

        jLabel61.setText("139-149级");

        jLabel65.setText("179-189级");

        jLabel66.setText("189-200级");

        jLabel67.setText("089-109级");

        jLabel68.setText("149-159级");

        jLabel69.setText("109-129级");

        jLabel70.setText("159-169级");

        jLabel71.setText("200-255级");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel51)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(expRate69, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel57)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(expRate139, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel62)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(expRate179, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel56)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(expRate89, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel61)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(expRate149, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel65)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(expRate189, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel67)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(expRate109, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel68)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(expRate159, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel66)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(expRate200, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel69)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(expRate129, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel70)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(expRate169, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel71)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(expRate250, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(expRate69, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel51))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(expRate139, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel57))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel62)
                            .addComponent(expRate179, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(expRate89, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel56))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(expRate149, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel61))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel65)
                            .addComponent(expRate189, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(expRate109, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel67))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(expRate159, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel68))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel66)
                            .addComponent(expRate200, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(expRate129, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel69))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(expRate169, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel70))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel71)
                            .addComponent(expRate250, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 0, 0))
        );

        jPanel78.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "双倍爆率频道事件管理", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("宋体", 0, 12), new java.awt.Color(0, 153, 153))); // NOI18N

        jLabel265.setFont(new java.awt.Font("宋体", 0, 14)); // NOI18N
        jLabel265.setText("双倍爆率频道/默认2频道");
        jLabel265.setMaximumSize(new java.awt.Dimension(190, 16));
        jLabel265.setMinimumSize(new java.awt.Dimension(190, 16));
        jLabel265.setPreferredSize(new java.awt.Dimension(190, 16));

        jLabel274.setFont(new java.awt.Font("宋体", 0, 14)); // NOI18N
        jLabel274.setText("双倍爆率频道进入道具");
        jLabel274.setMaximumSize(new java.awt.Dimension(190, 16));
        jLabel274.setMinimumSize(new java.awt.Dimension(190, 16));
        jLabel274.setPreferredSize(new java.awt.Dimension(190, 16));

        Doubleburstchannel.setText("双倍爆率频道开关");

        javax.swing.GroupLayout jPanel78Layout = new javax.swing.GroupLayout(jPanel78);
        jPanel78.setLayout(jPanel78Layout);
        jPanel78Layout.setHorizontalGroup(
            jPanel78Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel78Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel78Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel274, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                    .addComponent(jLabel265, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel78Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Channelnumber, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
                    .addComponent(Doubleexplosionchannel))
                .addGap(15, 15, 15)
                .addComponent(Doubleburstchannel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel78Layout.setVerticalGroup(
            jPanel78Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel78Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel78Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel265, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Doubleburstchannel)
                    .addComponent(Channelnumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel78Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel274, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Doubleexplosionchannel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel51Layout = new javax.swing.GroupLayout(jPanel51);
        jPanel51.setLayout(jPanel51Layout);
        jPanel51Layout.setHorizontalGroup(
            jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel51Layout.createSequentialGroup()
                .addGroup(jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel52, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel83, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel78, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel51Layout.setVerticalGroup(
            jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel51Layout.createSequentialGroup()
                .addGroup(jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel52, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel83, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addGroup(jPanel51Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel78, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 98, Short.MAX_VALUE))
        );

        jTabbedPane6.addTab("游戏设置", new javax.swing.ImageIcon(getClass().getResource("/Image/3801297.png")), jPanel51); // NOI18N

        jPanel64.setBorder(javax.swing.BorderFactory.createTitledBorder("进阶设置"));

        jPanel42.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "PvP功能", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("宋体", 0, 12), new java.awt.Color(102, 102, 255))); // NOI18N

        jLabel48.setText("个人PVP地图");

        jLabel49.setText("团队PVP地图");

        jLabel50.setText("家族PVP地图");

        javax.swing.GroupLayout jPanel42Layout = new javax.swing.GroupLayout(jPanel42);
        jPanel42.setLayout(jPanel42Layout);
        jPanel42Layout.setHorizontalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel42Layout.createSequentialGroup()
                .addComponent(jLabel48)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(personalPvpMap, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel49)
                .addGap(2, 2, 2)
                .addComponent(partyPvpMap, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel50, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(guildPvpMap, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );
        jPanel42Layout.setVerticalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel42Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(personalPvpMap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel48)
                    .addComponent(partyPvpMap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel49)
                    .addComponent(guildPvpMap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel50))
                .addGap(0, 0, 0))
        );

        jPanel63.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "经验加成设置& 1/1 不消弱 1/2 消弱2分之1 ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("宋体", 0, 12), new java.awt.Color(204, 0, 255))); // NOI18N

        jLabel28.setText("结婚经验加成 + %");

        jLabel27.setText("雇佣经验加成 + %");

        jLabel53.setText("消弱金币爆率 1/");

        javax.swing.GroupLayout jPanel63Layout = new javax.swing.GroupLayout(jPanel63);
        jPanel63.setLayout(jPanel63Layout);
        jPanel63Layout.setHorizontalGroup(
            jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel63Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel28)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Employmentexperience, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel27)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Marriageexperience, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel53)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Weakengold, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(107, 107, 107))
        );
        jPanel63Layout.setVerticalGroup(
            jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel63Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(jLabel27)
                    .addComponent(Weakengold, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel53)
                    .addComponent(Marriageexperience, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Employmentexperience, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout jPanel64Layout = new javax.swing.GroupLayout(jPanel64);
        jPanel64.setLayout(jPanel64Layout);
        jPanel64Layout.setHorizontalGroup(
            jPanel64Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel64Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel64Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel63, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(449, Short.MAX_VALUE))
        );
        jPanel64Layout.setVerticalGroup(
            jPanel64Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel64Layout.createSequentialGroup()
                .addComponent(jPanel42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel63, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(427, Short.MAX_VALUE))
        );

        jTabbedPane6.addTab("进阶设置", new javax.swing.ImageIcon(getClass().getResource("/Image/3801288.png")), jPanel64); // NOI18N

        jPanel66.setBorder(javax.swing.BorderFactory.createTitledBorder("特殊功能"));

        jPanel72.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "破功功能", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("宋体", 0, 12), new java.awt.Color(255, 0, 0))); // NOI18N

        飞侠攻击力.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                飞侠攻击力ActionPerformed(evt);
            }
        });

        jLabel104.setText("海盗敏捷比例");

        海盗敏捷.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                海盗敏捷ActionPerformed(evt);
            }
        });

        jLabel105.setText("海盗智力比例");

        端内破功管理员破功值.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                端内破功管理员破功值ActionPerformed(evt);
            }
        });

        海盗智力.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                海盗智力ActionPerformed(evt);
            }
        });

        jLabel107.setText("破功石增加伤害/万");

        jLabel108.setText("海盗运气比例");

        破功石增加伤害.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                破功石增加伤害ActionPerformed(evt);
            }
        });

        海盗运气.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                海盗运气ActionPerformed(evt);
            }
        });

        jLabel109.setText("破功伤害浮动值/万");

        jLabel110.setText("海盗攻击力比例");

        破功伤害浮动值.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                破功伤害浮动值ActionPerformed(evt);
            }
        });

        海盗攻击力.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                海盗攻击力ActionPerformed(evt);
            }
        });

        jLabel111.setText("战士力量比例");

        jLabel112.setText("战神敏捷比例");

        战士力量.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                战士力量ActionPerformed(evt);
            }
        });

        jLabel113.setText("法师力量比例");

        法师力量.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                法师力量ActionPerformed(evt);
            }
        });

        jLabel114.setText("飞侠力量比例");

        战神敏捷.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                战神敏捷ActionPerformed(evt);
            }
        });

        jLabel115.setText("战神智力比例");

        战神智力.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                战神智力ActionPerformed(evt);
            }
        });

        jLabel116.setText("战神运气比例");

        飞侠力量.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                飞侠力量ActionPerformed(evt);
            }
        });

        战神运气.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                战神运气ActionPerformed(evt);
            }
        });

        jLabel120.setText("弓手力量比例");

        jLabel121.setText("战神攻击力比例");

        弓手力量.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                弓手力量ActionPerformed(evt);
            }
        });

        战神攻击力.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                战神攻击力ActionPerformed(evt);
            }
        });

        jLabel122.setText("海盗力量比例");

        jLabel123.setText("法师敏捷比例");

        海盗力量.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                海盗力量ActionPerformed(evt);
            }
        });

        法师敏捷.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                法师敏捷ActionPerformed(evt);
            }
        });

        jLabel124.setText("战神力量比例");

        jLabel125.setText("法师智力比例");

        战神力量.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                战神力量ActionPerformed(evt);
            }
        });

        jLabel126.setText("战士敏捷比例");

        战士敏捷.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                战士敏捷ActionPerformed(evt);
            }
        });

        jLabel127.setText("战士智力比例");

        法师智力.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                法师智力ActionPerformed(evt);
            }
        });

        jLabel128.setText("法师运气比例");

        法师运气.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                法师运气ActionPerformed(evt);
            }
        });

        jLabel129.setText("法师魔法力比例");

        战士智力.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                战士智力ActionPerformed(evt);
            }
        });

        法师魔法力.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                法师魔法力ActionPerformed(evt);
            }
        });

        jLabel130.setText("战士运气比例");

        jLabel131.setForeground(new java.awt.Color(0, 0, 204));
        jLabel131.setText("*******************************以上破功配置均为端内破功模式配置，使用登陆器破功的，以上配置无效*****************************************");

        战士运气.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                战士运气ActionPerformed(evt);
            }
        });

        jLabel132.setText("破功公式：全部力量 * 力量比例 + 全部敏捷 * 敏捷比例 + 全部运气 * 运气比例 + 全部智力 * 智力比例 + 全部攻击力 * 攻击力比例   + 随机伤害浮动值");

        jLabel133.setText("战士攻击力比例");

        战士攻击力.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                战士攻击力ActionPerformed(evt);
            }
        });

        jLabel134.setText("弓手敏捷比例");

        弓手敏捷.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                弓手敏捷ActionPerformed(evt);
            }
        });

        jLabel135.setText("弓手智力比例");

        弓手智力.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                弓手智力ActionPerformed(evt);
            }
        });

        jLabel136.setText("弓手运气比例");

        弓手运气.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                弓手运气ActionPerformed(evt);
            }
        });

        jLabel137.setText("弓手攻击力比例");

        弓手攻击力.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                弓手攻击力ActionPerformed(evt);
            }
        });

        jLabel138.setText("飞侠敏捷比例");

        飞侠敏捷.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                飞侠敏捷ActionPerformed(evt);
            }
        });

        jLabel139.setText("飞侠智力比例");

        飞侠智力.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                飞侠智力ActionPerformed(evt);
            }
        });

        端内破功最高伤害上限.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                端内破功最高伤害上限ActionPerformed(evt);
            }
        });

        jLabel140.setText("飞侠运气比例");

        jLabel141.setText("最高伤害上限值/万");

        飞侠运气.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                飞侠运气ActionPerformed(evt);
            }
        });

        端内物理职业破功开关.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        端内物理职业破功开关.setForeground(new java.awt.Color(255, 0, 51));
        端内物理职业破功开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/3801313.png"))); // NOI18N
        端内物理职业破功开关.setText("端内物理攻击职业破功");
        端内物理职业破功开关.setToolTipText("");
        端内物理职业破功开关.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                端内物理职业破功开关ActionPerformed(evt);
            }
        });

        jLabel142.setText("飞侠攻击力比例");

        端内魔法职业破功开关.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
        端内魔法职业破功开关.setForeground(new java.awt.Color(255, 0, 51));
        端内魔法职业破功开关.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/3801313.png"))); // NOI18N
        端内魔法职业破功开关.setText("端内魔法攻击职业破功");
        端内魔法职业破功开关.setToolTipText("");
        端内魔法职业破功开关.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                端内魔法职业破功开关ActionPerformed(evt);
            }
        });

        jLabel143.setFont(new java.awt.Font("宋体", 0, 14)); // NOI18N
        jLabel143.setText("伤害直显需要配合指定破功登陆器使用（破功字段角色表PGSXDJ）不可与端内破功同时启用");

        jLabel144.setText("端内破功字段角色表 vipczz");

        jLabel145.setText("管理员破功数值/万");

        javax.swing.GroupLayout jPanel72Layout = new javax.swing.GroupLayout(jPanel72);
        jPanel72.setLayout(jPanel72Layout);
        jPanel72Layout.setHorizontalGroup(
            jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel72Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel131, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel72Layout.createSequentialGroup()
                        .addGroup(jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel72Layout.createSequentialGroup()
                                .addGroup(jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel72Layout.createSequentialGroup()
                                        .addGroup(jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel141, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel145, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel72Layout.createSequentialGroup()
                                                .addComponent(端内破功最高伤害上限, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel111))
                                            .addGroup(jPanel72Layout.createSequentialGroup()
                                                .addComponent(端内破功管理员破功值, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel120)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(弓手力量, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(战士力量, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel72Layout.createSequentialGroup()
                                                .addComponent(jLabel126)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(战士敏捷, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jLabel127)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(战士智力, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel72Layout.createSequentialGroup()
                                                .addComponent(jLabel134)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(弓手敏捷, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jLabel135)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(弓手智力, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(10, 10, 10)
                                        .addGroup(jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel72Layout.createSequentialGroup()
                                                .addComponent(jLabel130)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(战士运气, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel133, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel72Layout.createSequentialGroup()
                                                .addComponent(jLabel136)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(弓手运气, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel137, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(jPanel72Layout.createSequentialGroup()
                                        .addComponent(jLabel107)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(破功石增加伤害, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel114)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(飞侠力量, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel138)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(飞侠敏捷, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel139)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(飞侠智力, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel140)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(飞侠运气, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel142, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel72Layout.createSequentialGroup()
                                        .addComponent(jLabel109)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(破功伤害浮动值, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel72Layout.createSequentialGroup()
                                                .addComponent(jLabel122)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(海盗力量, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jLabel104)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(海盗敏捷, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jLabel105)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(海盗智力, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jLabel108)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(海盗运气, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel110, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel72Layout.createSequentialGroup()
                                                .addComponent(jLabel124)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(战神力量, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jLabel112)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(战神敏捷, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jLabel115)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(战神智力, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jLabel116)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(战神运气, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel121, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel72Layout.createSequentialGroup()
                                                .addComponent(jLabel113)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(法师力量, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jLabel123)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(法师敏捷, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(10, 10, 10)
                                                .addComponent(jLabel125)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(法师智力, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jLabel128)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(法师运气, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel129, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(法师魔法力, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(战神攻击力, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(海盗攻击力, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(飞侠攻击力, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(弓手攻击力, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(战士攻击力, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel72Layout.createSequentialGroup()
                                .addComponent(端内物理职业破功开关, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(端内魔法职业破功开关, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel144, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel132, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel72Layout.createSequentialGroup()
                        .addComponent(jLabel143, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel72Layout.setVerticalGroup(
            jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel72Layout.createSequentialGroup()
                .addGroup(jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(端内破功最高伤害上限, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel141)
                    .addComponent(jLabel111)
                    .addComponent(战士力量, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel126)
                    .addComponent(战士敏捷, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel127)
                    .addComponent(战士智力, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel130)
                    .addComponent(战士运气, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel133)
                    .addComponent(战士攻击力, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel145)
                    .addComponent(端内破功管理员破功值, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel120)
                    .addComponent(弓手力量, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel134)
                    .addComponent(弓手敏捷, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel135)
                    .addComponent(弓手智力, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel136)
                    .addComponent(弓手运气, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel137)
                    .addComponent(弓手攻击力, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel107)
                    .addComponent(破功石增加伤害, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel114)
                    .addComponent(飞侠力量, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel138)
                    .addComponent(飞侠敏捷, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel139)
                    .addComponent(飞侠智力, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel140)
                    .addComponent(飞侠运气, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel142)
                    .addComponent(飞侠攻击力, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel109)
                        .addComponent(破功伤害浮动值, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel122)
                        .addComponent(海盗力量, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel104)
                        .addComponent(海盗敏捷, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel105)
                        .addComponent(海盗智力, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel108)
                        .addComponent(海盗运气, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel110)
                        .addComponent(海盗攻击力, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(战神力量, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel112)
                    .addComponent(战神敏捷, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel115)
                    .addComponent(战神智力, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel116)
                    .addComponent(战神运气, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel121)
                    .addComponent(战神攻击力, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel124))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(法师力量, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel123)
                    .addComponent(jLabel113)
                    .addComponent(法师敏捷, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel125)
                    .addComponent(法师智力, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel128)
                    .addComponent(法师运气, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel129)
                    .addComponent(法师魔法力, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(端内物理职业破功开关)
                    .addComponent(端内魔法职业破功开关)
                    .addComponent(jLabel144))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel132)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel131)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel143)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel66Layout = new javax.swing.GroupLayout(jPanel66);
        jPanel66.setLayout(jPanel66Layout);
        jPanel66Layout.setHorizontalGroup(
            jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel72, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel66Layout.setVerticalGroup(
            jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel66Layout.createSequentialGroup()
                .addComponent(jPanel72, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane6.addTab("特殊功能", new javax.swing.ImageIcon(getClass().getResource("/Image/3801287.png")), jPanel66); // NOI18N

        jPanel23.setFont(new java.awt.Font("微软雅黑", 0, 12)); // NOI18N

        jPanel68.setBorder(javax.swing.BorderFactory.createTitledBorder("活动经验"));

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("2倍率活动"));
        jPanel9.setAlignmentX(0.0F);
        jPanel9.setAlignmentY(0.0F);

        开启双倍经验.setText("开启双倍经验");
        开启双倍经验.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                开启双倍经验ActionPerformed(evt);
            }
        });

        jLabel359.setFont(new java.awt.Font("宋体", 0, 14)); // NOI18N
        jLabel359.setText("持续时间/h；");

        开启双倍爆率.setText("开启双倍爆率");
        开启双倍爆率.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                开启双倍爆率ActionPerformed(evt);
            }
        });

        jLabel360.setFont(new java.awt.Font("宋体", 0, 14)); // NOI18N
        jLabel360.setText("持续时间/h；");

        开启双倍金币.setText("开启双倍金币");
        开启双倍金币.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                开启双倍金币ActionPerformed(evt);
            }
        });

        jLabel361.setFont(new java.awt.Font("宋体", 0, 14)); // NOI18N
        jLabel361.setText("持续时间/h；");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel9Layout.createSequentialGroup()
                    .addGap(0, 86, Short.MAX_VALUE)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel9Layout.createSequentialGroup()
                            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel359)
                                .addComponent(双倍经验持续时间, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(30, 30, 30)
                            .addComponent(开启双倍经验, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel9Layout.createSequentialGroup()
                            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel360)
                                .addComponent(双倍爆率持续时间, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(30, 30, 30)
                            .addComponent(开启双倍爆率, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel9Layout.createSequentialGroup()
                            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel361)
                                .addComponent(双倍金币持续时间, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(30, 30, 30)
                            .addComponent(开启双倍金币, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGap(0, 87, Short.MAX_VALUE)))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 306, Short.MAX_VALUE)
            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel9Layout.createSequentialGroup()
                    .addGap(0, 47, Short.MAX_VALUE)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel9Layout.createSequentialGroup()
                            .addComponent(jLabel359, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, 0)
                            .addComponent(双倍经验持续时间, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel9Layout.createSequentialGroup()
                            .addGap(11, 11, 11)
                            .addComponent(开启双倍经验, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGap(29, 29, 29)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel9Layout.createSequentialGroup()
                            .addComponent(jLabel360, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, 0)
                            .addComponent(双倍爆率持续时间, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel9Layout.createSequentialGroup()
                            .addGap(11, 11, 11)
                            .addComponent(开启双倍爆率, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGap(29, 29, 29)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel9Layout.createSequentialGroup()
                            .addComponent(jLabel361, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, 0)
                            .addComponent(双倍金币持续时间, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel9Layout.createSequentialGroup()
                            .addGap(11, 11, 11)
                            .addComponent(开启双倍金币, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGap(0, 48, Short.MAX_VALUE)))
        );

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder("3倍率活动"));

        开启三倍经验.setText("开启三倍经验");
        开启三倍经验.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                开启三倍经验ActionPerformed(evt);
            }
        });

        jLabel362.setFont(new java.awt.Font("宋体", 0, 14)); // NOI18N
        jLabel362.setText("持续时间/h；");

        开启三倍爆率.setText("开启三倍爆率");
        开启三倍爆率.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                开启三倍爆率ActionPerformed(evt);
            }
        });

        jLabel348.setFont(new java.awt.Font("宋体", 0, 14)); // NOI18N
        jLabel348.setText("持续时间/h；");

        开启三倍金币.setText("开启三倍金币");
        开启三倍金币.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                开启三倍金币ActionPerformed(evt);
            }
        });

        jLabel349.setFont(new java.awt.Font("宋体", 0, 14)); // NOI18N
        jLabel349.setText("持续时间/h；");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 443, Short.MAX_VALUE)
            .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel13Layout.createSequentialGroup()
                    .addGap(0, 76, Short.MAX_VALUE)
                    .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel13Layout.createSequentialGroup()
                            .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel362)
                                .addComponent(三倍经验持续时间, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(30, 30, 30)
                            .addComponent(开启三倍经验, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel13Layout.createSequentialGroup()
                            .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel348)
                                .addComponent(三倍爆率持续时间, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(30, 30, 30)
                            .addComponent(开启三倍爆率, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel13Layout.createSequentialGroup()
                            .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel349)
                                .addComponent(三倍金币持续时间, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(30, 30, 30)
                            .addComponent(开启三倍金币, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGap(0, 77, Short.MAX_VALUE)))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 306, Short.MAX_VALUE)
            .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel13Layout.createSequentialGroup()
                    .addGap(0, 47, Short.MAX_VALUE)
                    .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel13Layout.createSequentialGroup()
                            .addComponent(jLabel362, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, 0)
                            .addComponent(三倍经验持续时间, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel13Layout.createSequentialGroup()
                            .addGap(11, 11, 11)
                            .addComponent(开启三倍经验, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGap(29, 29, 29)
                    .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel13Layout.createSequentialGroup()
                            .addComponent(jLabel348, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, 0)
                            .addComponent(三倍爆率持续时间, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel13Layout.createSequentialGroup()
                            .addGap(11, 11, 11)
                            .addComponent(开启三倍爆率, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGap(29, 29, 29)
                    .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel13Layout.createSequentialGroup()
                            .addComponent(jLabel349, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, 0)
                            .addComponent(三倍金币持续时间, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel13Layout.createSequentialGroup()
                            .addGap(11, 11, 11)
                            .addComponent(开启三倍金币, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGap(0, 48, Short.MAX_VALUE)))
        );

        jLabel1.setFont(new java.awt.Font("宋体", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 51, 51));
        jLabel1.setText("功能说明：本功能无需重启服务端立即生效");

        jLabel3.setFont(new java.awt.Font("宋体", 1, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 0, 51));
        jLabel3.setText("单位换算 h=小时 时间到期自动解除倍率");

        javax.swing.GroupLayout jPanel68Layout = new javax.swing.GroupLayout(jPanel68);
        jPanel68.setLayout(jPanel68Layout);
        jPanel68Layout.setHorizontalGroup(
            jPanel68Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel68Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel68Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addGroup(jPanel68Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 732, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel68Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel3)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel68Layout.setVerticalGroup(
            jPanel68Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel68Layout.createSequentialGroup()
                .addGroup(jPanel68Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel68, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel68, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane6.addTab("活动功能", new javax.swing.ImageIcon(getClass().getResource("/Image/100.png")), jPanel23); // NOI18N

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jPanel61.setBorder(javax.swing.BorderFactory.createTitledBorder("在线泡点"));

        pdkg.setText("泡点开关");

        jLabel149.setText("点券最小数量");

        jLabel150.setText("点券最大数量");

        jLabel151.setText("金币最小数量");

        jLabel152.setText("金币最大数量");

        jLabel153.setText("豆豆最大数量");

        jLabel154.setText("豆豆最小数量");

        jLabel155.setText("抵用最大数量");

        jLabel156.setText("抵用最小数量");

        jLabel157.setText("指定泡点地图");

        jLabel158.setText("等级经验成倍");

        jLabel159.setForeground(new java.awt.Color(255, 0, 51));
        jLabel159.setText("提示：最小值与最大值数值一样时，为固定值泡点， 数值不一样时，为随机值泡点");

        jLabel160.setForeground(new java.awt.Color(255, 0, 255));
        jLabel160.setText("提示：最大值或者等级经验倍数 == 0 时关闭该值的泡点");

        jLabel161.setForeground(new java.awt.Color(0, 102, 255));
        jLabel161.setText("提示：角色等级 X 最小最大数量 X 等级经验成倍 X 10=最终获得的经验 ");

        jLabel162.setText("基础经验数量");

        jLabel163.setText("基础金币数量");

        javax.swing.GroupLayout jPanel61Layout = new javax.swing.GroupLayout(jPanel61);
        jPanel61.setLayout(jPanel61Layout);
        jPanel61Layout.setHorizontalGroup(
            jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel61Layout.createSequentialGroup()
                .addGroup(jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel61Layout.createSequentialGroup()
                        .addGap(107, 107, 107)
                        .addGroup(jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel61Layout.createSequentialGroup()
                                .addComponent(jLabel160)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel161))
                            .addComponent(jLabel159)))
                    .addGroup(jPanel61Layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addGroup(jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel61Layout.createSequentialGroup()
                                .addGroup(jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(jPanel61Layout.createSequentialGroup()
                                        .addComponent(jLabel162, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(dyjy, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel61Layout.createSequentialGroup()
                                        .addComponent(jLabel156)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(pdzsdy, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel61Layout.createSequentialGroup()
                                        .addComponent(jLabel155)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(pdzddy, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel61Layout.createSequentialGroup()
                                        .addComponent(jLabel163)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(dyjb, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel61Layout.createSequentialGroup()
                                .addComponent(pdkg)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel149)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pdzsdj, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel150)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pdzddj, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel61Layout.createSequentialGroup()
                                .addComponent(jLabel151)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pdzsjb, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel152)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pdzdjb, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel61Layout.createSequentialGroup()
                                .addComponent(jLabel154)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pdzsdd, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel153)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pdzddd, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel61Layout.createSequentialGroup()
                        .addGap(244, 244, 244)
                        .addComponent(jLabel157)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pdmap, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel158, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pdexp, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, 0))
        );
        jPanel61Layout.setVerticalGroup(
            jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel61Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pdkg)
                    .addComponent(pdzsdj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel149)
                    .addComponent(pdzddj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel150)
                    .addComponent(pdzsjb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel151)
                    .addComponent(pdzdjb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel152))
                .addGap(18, 18, 18)
                .addGroup(jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pdzsdy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel156)
                    .addComponent(pdzddy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel155)
                    .addComponent(pdzsdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel154)
                    .addComponent(pdzddd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel153))
                .addGap(18, 18, 18)
                .addGroup(jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dyjy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel162)
                    .addComponent(dyjb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel163))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(pdexp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel158))
                    .addGroup(jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(pdmap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel157)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel160)
                    .addComponent(jLabel161))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel159)
                .addContainerGap())
        );

        jPanel62.setBorder(javax.swing.BorderFactory.createTitledBorder("离线泡点"));

        jLabel169.setFont(new java.awt.Font("微软雅黑", 0, 18)); // NOI18N
        jLabel169.setForeground(new java.awt.Color(255, 51, 51));
        jLabel169.setText("玩家在自由市场下线后服务端自动启动离线挂机功能,角色将继续在自由市场并统计离线时间.");

        Offlinehangup.setText("泡点开关");

        jLabel170.setText("离线泡点地图");

        jLabel171.setText("离线泡点点券");

        jLabel172.setText("离线泡点抵用");

        jLabel173.setText("离线泡点金币");

        jLabel174.setText("离线泡点经验");

        jLabel175.setText("离线泡点豆豆");

        jLabel176.setFont(new java.awt.Font("宋体", 0, 24)); // NOI18N
        jLabel176.setForeground(new java.awt.Color(255, 51, 255));
        jLabel176.setText("离线泡点提示:每一分钟=设置的奖励数值");

        javax.swing.GroupLayout jPanel62Layout = new javax.swing.GroupLayout(jPanel62);
        jPanel62.setLayout(jPanel62Layout);
        jPanel62Layout.setHorizontalGroup(
            jPanel62Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel62Layout.createSequentialGroup()
                .addGroup(jPanel62Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel62Layout.createSequentialGroup()
                        .addGap(92, 92, 92)
                        .addComponent(jLabel169, javax.swing.GroupLayout.PREFERRED_SIZE, 740, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel62Layout.createSequentialGroup()
                        .addGap(316, 316, 316)
                        .addGroup(jPanel62Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel62Layout.createSequentialGroup()
                                .addComponent(jLabel175)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(OfflineDoudou, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel62Layout.createSequentialGroup()
                                .addComponent(jLabel174)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Offlineexperience, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel173)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Offlinegoldcoins, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel62Layout.createSequentialGroup()
                                .addComponent(jLabel171)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Offlinecouponcounting, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel172)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Offlineoffset, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel62Layout.createSequentialGroup()
                                .addComponent(Offlinehangup)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel170)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(offlinemap, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel62Layout.createSequentialGroup()
                        .addGap(244, 244, 244)
                        .addComponent(jLabel176)))
                .addGap(0, 0, 0))
        );
        jPanel62Layout.setVerticalGroup(
            jPanel62Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel62Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel62Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Offlinehangup)
                    .addComponent(offlinemap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel170))
                .addGap(18, 18, 18)
                .addGroup(jPanel62Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Offlinecouponcounting, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel171)
                    .addComponent(Offlineoffset, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel172))
                .addGap(18, 18, 18)
                .addGroup(jPanel62Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Offlineexperience, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel174)
                    .addComponent(Offlinegoldcoins, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel173))
                .addGap(18, 18, 18)
                .addGroup(jPanel62Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(OfflineDoudou, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel175))
                .addGap(41, 41, 41)
                .addComponent(jLabel176)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel169)
                .addGap(30, 30, 30))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel61, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel62, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel61, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel62, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane6.addTab("挂机功能", new javax.swing.ImageIcon(getClass().getResource("/Image/99.png")), jPanel4); // NOI18N

        jPanel40.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "配置保存", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("宋体", 0, 12), new java.awt.Color(0, 204, 102))); // NOI18N

        jButton16.setText("放弃修改");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        jButton15.setText("保存并马上生效");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        jButton18.setText("读取配置文件");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel40Layout = new javax.swing.GroupLayout(jPanel40);
        jPanel40.setLayout(jPanel40Layout);
        jPanel40Layout.setHorizontalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(249, 249, 249))
        );
        jPanel40Layout.setVerticalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 1, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane6)
            .addComponent(jPanel40, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addComponent(jTabbedPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 583, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel40, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane.addTab("游戏配置", new javax.swing.ImageIcon(getClass().getResource("/Image/3994610.png")), jPanel21); // NOI18N

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("游戏公告"));

        sendNotice.setText("红色提示公告");
        sendNotice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendNoticeActionPerformed(evt);
            }
        });

        sendWinNotice.setText("顶部滚动公告");
        sendWinNotice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendWinNoticeActionPerformed(evt);
            }
        });

        sendMsgNotice.setText("弹窗公告");
        sendMsgNotice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendMsgNoticeActionPerformed(evt);
            }
        });

        sendNpcTalkNotice.setText("蓝色公告事项");
        sendNpcTalkNotice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendNpcTalkNoticeActionPerformed(evt);
            }
        });

        noticeText.setFont(new java.awt.Font("宋体", 0, 24)); // NOI18N
        noticeText.setText("游戏即将维护,请安全下线！造成不便请谅解！");

        jLabel117.setFont(new java.awt.Font("宋体", 0, 24)); // NOI18N
        jLabel117.setText("1、不得散布谣言，扰乱社会秩序，破坏社会稳定的信息 ");

        jLabel118.setFont(new java.awt.Font("宋体", 0, 24)); // NOI18N
        jLabel118.setText("2、不得散布赌博、暴力、凶杀、恐怖或者教唆犯罪的信息");

        jLabel119.setFont(new java.awt.Font("宋体", 0, 24)); // NOI18N
        jLabel119.setText("3、不得侮辱或者诽谤他人，侵害他人合法权益");

        jLabel106.setFont(new java.awt.Font("宋体", 0, 24)); // NOI18N
        jLabel106.setText("4、不得含有法律、行政法规禁止的其他内容");

        公告发布喇叭代码.setText("5120027");
        公告发布喇叭代码.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                公告发布喇叭代码ActionPerformed(evt);
            }
        });

        jButton45.setFont(new java.awt.Font("宋体", 0, 14)); // NOI18N
        jButton45.setText("屏幕正中公告");
        jButton45.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton45ActionPerformed(evt);
            }
        });

        jLabel259.setFont(new java.awt.Font("宋体", 0, 14)); // NOI18N
        jLabel259.setText("喇叭代码");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(sendNotice, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel117, javax.swing.GroupLayout.PREFERRED_SIZE, 680, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel118, javax.swing.GroupLayout.PREFERRED_SIZE, 680, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel119, javax.swing.GroupLayout.PREFERRED_SIZE, 680, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel106, javax.swing.GroupLayout.PREFERRED_SIZE, 680, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(sendWinNotice, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(sendMsgNotice, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(sendNpcTalkNotice, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton45, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel259)
                            .addComponent(公告发布喇叭代码, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(126, Short.MAX_VALUE))
            .addComponent(noticeText, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(noticeText, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel259)
                        .addGap(0, 0, 0)
                        .addComponent(公告发布喇叭代码, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jButton45, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(sendNpcTalkNotice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(sendWinNotice, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sendMsgNotice, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(sendNotice, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(59, 59, 59)
                .addComponent(jLabel117, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel118, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel119, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel106, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72))
        );

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane.addTab("游戏公告", new javax.swing.ImageIcon(getClass().getResource("/Image/2630205.png")), jPanel22); // NOI18N

        jPanel25.setBorder(javax.swing.BorderFactory.createTitledBorder("账号中心"));

        显示在线账号.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N

        accountstable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "账号", "密码", "QQ", "点卷", "抵用卷", "封号", "状态"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(accountstable);

        账号搜索.setText("搜索");
        账号搜索.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                账号搜索ActionPerformed(evt);
            }
        });

        jLabel29.setText("账号");

        jButton11.setText("封停账号");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jLabel33.setText("账号");

        jLabel34.setText("QQ号");

        jLabel36.setText("点券");

        jLabel37.setText("密码");

        jLabel38.setText("抵用");

        jLabel40.setFont(new java.awt.Font("宋体", 1, 12)); // NOI18N
        jLabel40.setText("修改账号信息");

        jLabel42.setForeground(new java.awt.Color(153, 51, 255));
        jLabel42.setText("*（如果需要修改密码。直接输入明文。系统会自动加密）");

        jButton19.setText("应用修改");
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });

        jButton20.setText("删除账号");
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });

        jButton24.setText("刷新");
        jButton24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton24ActionPerformed(evt);
            }
        });

        jLabel43.setText("查询到");

        账号总数标签.setForeground(new java.awt.Color(255, 0, 51));
        账号总数标签.setText("读取中…");

        acct.setForeground(new java.awt.Color(204, 102, 0));
        acct.setText("请进行操作。* 删除账号不可逆，请慎重！");

        jButton56.setText("注册账号");
        jButton56.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton56ActionPerformed(evt);
            }
        });

        jButton12.setText("解封账号");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jButton13.setText("卡号自救");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                        .addComponent(acct, javax.swing.GroupLayout.PREFERRED_SIZE, 551, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(jLabel40)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel42)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField23, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(账号搜索)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton24))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel18Layout.createSequentialGroup()
                                .addComponent(jLabel33)
                                .addGap(5, 5, 5)
                                .addComponent(输入0, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel34)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(输入2, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel36)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(输入3, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel18Layout.createSequentialGroup()
                                .addComponent(jLabel37)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(输入1)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel38)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(输入4, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 265, Short.MAX_VALUE)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                                .addComponent(jButton19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton11))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel18Layout.createSequentialGroup()
                                    .addComponent(jButton56, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButton20))
                                .addGroup(jPanel18Layout.createSequentialGroup()
                                    .addComponent(jButton13)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButton12))))))
                .addContainerGap())
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addComponent(jLabel43)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(账号总数标签, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel43)
                    .addComponent(账号总数标签))
                .addGap(3, 3, 3)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel40)
                        .addComponent(jLabel42))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(账号搜索, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel29))
                    .addComponent(jButton24, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(输入0, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33)
                    .addComponent(jLabel34)
                    .addComponent(输入2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36)
                    .addComponent(输入3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(输入4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel38)
                    .addComponent(jButton20, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton56, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(输入1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel37))))
                .addGap(0, 0, 0)
                .addComponent(acct, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGap(10, 838, Short.MAX_VALUE)
                .addComponent(显示在线账号, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(显示在线账号, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 610, Short.MAX_VALUE))
            .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane.addTab("帐户中心", new javax.swing.ImageIcon(getClass().getResource("/Image/3994506.png")), jPanel25); // NOI18N

        jPanel43.setBackground(new java.awt.Color(255, 255, 255));

        characterstable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "名字", "等级", "力量", "敏捷", "运气", "智力", "最大HP", "最大MP", "职业", "冒险币", "地图", "状态", "GM", "发型", "脸型"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane10.setViewportView(characterstable);

        jButton26.setText("刷新");
        jButton26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton26ActionPerformed(evt);
            }
        });

        jButton27.setText("搜索");
        jButton27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton27ActionPerformed(evt);
            }
        });

        jLabel75.setText("角色名");

        jLabel76.setText("角色总数：");

        角色总数标签.setForeground(new java.awt.Color(255, 51, 51));
        角色总数标签.setText("读取中");

        jPanel24.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "*修改角色数据需要下线修改", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("宋体", 0, 12), new java.awt.Color(255, 0, 204))); // NOI18N

        角色11.setEditable(false);

        jLabel91.setText("蓝量");

        jLabel92.setText("金币");

        jLabel93.setText("职业");

        jLabel94.setText("管理");

        jLabel97.setText("I  D");

        jLabel95.setText("发型");

        jLabel96.setText("脸型");

        jLabel81.setText("名称");

        jLabel82.setText("等级");

        jLabel83.setText("力量");

        jLabel84.setText("敏捷");

        jLabel85.setText("运气");

        jLabel86.setText("智力");

        jLabel89.setText("地图");

        jLabel90.setText("血量");

        jButton30.setText("确认修改");
        jButton30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton30ActionPerformed(evt);
            }
        });

        jButton35.setText("删除角色");
        jButton35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton35ActionPerformed(evt);
            }
        });

        jPanel46.setBorder(javax.swing.BorderFactory.createTitledBorder("GM权限设置"));

        jButton2.setText("设为管理");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("设为玩家");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel46Layout = new javax.swing.GroupLayout(jPanel46);
        jPanel46.setLayout(jPanel46Layout);
        jPanel46Layout.setHorizontalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel46Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel46Layout.setVerticalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jButton2)
                .addComponent(jButton3))
        );

        jPanel47.setBorder(javax.swing.BorderFactory.createTitledBorder("角色解救"));

        jButton4.setText("解卡头脸");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("解卡物品");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("解卡登录");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("解卡家族");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel47Layout = new javax.swing.GroupLayout(jPanel47);
        jPanel47.setLayout(jPanel47Layout);
        jPanel47Layout.setHorizontalGroup(
            jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel47Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton5))
                .addContainerGap())
        );
        jPanel47Layout.setVerticalGroup(
            jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel47Layout.createSequentialGroup()
                .addGroup(jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jButton5))
                .addGap(1, 1, 1)
                .addGroup(jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton6)
                    .addComponent(jButton7)))
        );

        jPanel48.setBorder(javax.swing.BorderFactory.createTitledBorder("键盘修复"));

        jButton8.setText("还原键盘");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setText("备份键盘");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel48Layout = new javax.swing.GroupLayout(jPanel48);
        jPanel48.setLayout(jPanel48Layout);
        jPanel48Layout.setHorizontalGroup(
            jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel48Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton9)
                .addContainerGap())
        );
        jPanel48Layout.setVerticalGroup(
            jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jButton8)
                .addComponent(jButton9))
        );

        jPanel50.setBorder(javax.swing.BorderFactory.createTitledBorder("进阶功能"));

        jButton14.setText("读取角色背包");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        jButton28.setText("读取角色技能");
        jButton28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton28ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel50Layout = new javax.swing.GroupLayout(jPanel50);
        jPanel50.setLayout(jPanel50Layout);
        jPanel50Layout.setHorizontalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel50Layout.createSequentialGroup()
                .addComponent(jButton14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton28)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel50Layout.setVerticalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel50Layout.createSequentialGroup()
                .addGroup(jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton28, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addComponent(jLabel81)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(角色0, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel90)
                            .addComponent(jLabel97))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(角色ID, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(角色6, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel24Layout.createSequentialGroup()
                                .addComponent(jLabel91)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(角色7, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel24Layout.createSequentialGroup()
                                .addComponent(jLabel82)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(角色1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel24Layout.createSequentialGroup()
                                .addComponent(jLabel83)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(角色2, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel24Layout.createSequentialGroup()
                                .addComponent(jLabel92)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(角色9, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel24Layout.createSequentialGroup()
                                .addComponent(jLabel93)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(角色8, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel24Layout.createSequentialGroup()
                                .addComponent(jLabel84)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(角色3, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel24Layout.createSequentialGroup()
                                .addComponent(jLabel89)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(角色10, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel24Layout.createSequentialGroup()
                                .addComponent(jLabel85)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(角色4, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel24Layout.createSequentialGroup()
                                .addComponent(jLabel94)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(角色11, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel24Layout.createSequentialGroup()
                                .addComponent(jLabel86)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(角色5, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addComponent(jLabel95)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(角色12, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel96)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(角色13, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel24Layout.createSequentialGroup()
                        .addComponent(jButton30, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton35, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)))
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addComponent(jPanel47, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel50, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addComponent(jPanel46, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel48, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel81)
                            .addComponent(jLabel82)
                            .addComponent(jLabel83)
                            .addComponent(jLabel84)
                            .addComponent(jLabel85)
                            .addComponent(角色0, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel86)
                            .addComponent(角色1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(角色2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(角色3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(角色4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(角色5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel90)
                            .addComponent(jLabel91)
                            .addComponent(jLabel92)
                            .addComponent(jLabel93)
                            .addComponent(jLabel89)
                            .addComponent(jLabel94)
                            .addComponent(角色6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(角色7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(角色8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(角色9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(角色10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(角色11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(角色ID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel97)
                            .addComponent(jLabel95)
                            .addComponent(角色12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel96)
                            .addComponent(角色13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton30, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton35, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel46, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel48, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel47, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel50, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(0, 18, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("修改离线角色", jPanel24);

        jPanel53.setBackground(new java.awt.Color(255, 255, 255));

        jPanel56.setBorder(javax.swing.BorderFactory.createTitledBorder("便捷功能"));

        jButton33.setText("强制下线");
        jButton33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton33ActionPerformed(evt);
            }
        });

        jButton38.setText("封号下线");
        jButton38.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton38ActionPerformed(evt);
            }
        });

        jButton40.setText("传送自由");
        jButton40.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton40ActionPerformed(evt);
            }
        });

        jButton48.setText("传送地图");
        jButton48.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton48ActionPerformed(evt);
            }
        });

        jButton55.setText("修改脸型");
        jButton55.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton55ActionPerformed(evt);
            }
        });

        jButton57.setText("修改发型");
        jButton57.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton57ActionPerformed(evt);
            }
        });

        jButton58.setText("加满技能");
        jButton58.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton58ActionPerformed(evt);
            }
        });

        jButton59.setText("开技能栏");
        jButton59.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton59ActionPerformed(evt);
            }
        });

        jButton60.setText("杀死玩家");
        jButton60.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton60ActionPerformed(evt);
            }
        });

        jButton63.setText("关小黑屋");
        jButton63.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton63ActionPerformed(evt);
            }
        });

        jButton64.setText("全员下线");
        jButton64.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton64ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel56Layout = new javax.swing.GroupLayout(jPanel56);
        jPanel56.setLayout(jPanel56Layout);
        jPanel56Layout.setHorizontalGroup(
            jPanel56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel56Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton33)
                    .addComponent(jButton64))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel56Layout.createSequentialGroup()
                        .addComponent(jButton58)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton59)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton60)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton63))
                    .addGroup(jPanel56Layout.createSequentialGroup()
                        .addComponent(jButton38)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton40)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton48)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton55)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton57)))
                .addContainerGap(420, Short.MAX_VALUE))
        );
        jPanel56Layout.setVerticalGroup(
            jPanel56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel56Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton40, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                    .addComponent(jButton38, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton48, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton57, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton55, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton60, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                    .addComponent(jButton59, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton58, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton64, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton63, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel53Layout = new javax.swing.GroupLayout(jPanel53);
        jPanel53.setLayout(jPanel53Layout);
        jPanel53Layout.setHorizontalGroup(
            jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel56, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel53Layout.setVerticalGroup(
            jPanel53Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel56, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("修改在线角色", jPanel53);

        jPanel33.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "功能说明:新手不要乱用！", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("宋体", 0, 12), new java.awt.Color(255, 0, 0))); // NOI18N

        jTextArea5.setBackground(new java.awt.Color(0, 204, 0));
        jTextArea5.setColumns(20);
        jTextArea5.setFont(new java.awt.Font("宋体", 0, 12)); // NOI18N
        jTextArea5.setForeground(new java.awt.Color(255, 51, 0));
        jTextArea5.setLineWrap(true);
        jTextArea5.setRows(5);
        jScrollPane14.setViewportView(jTextArea5);

        jButton61.setText("发送数据包");
        jButton61.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton61ActionPerformed(evt);
            }
        });

        jButton62.setText("发送商城数据包");
        jButton62.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton62ActionPerformed(evt);
            }
        });

        jLabel32.setText("包头:0x");

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel33Layout.createSequentialGroup()
                        .addComponent(jLabel32)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField25)
                        .addGap(10, 10, 10))
                    .addGroup(jPanel33Layout.createSequentialGroup()
                        .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jButton61, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton62, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)))
                .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 787, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel33Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton61)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton62)
                .addContainerGap(43, Short.MAX_VALUE))
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addComponent(jScrollPane14)
                .addContainerGap())
        );

        jTabbedPane1.addTab("封包调试", jPanel33);

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addComponent(jLabel76)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(角色总数标签)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel75)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField24, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton27)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton26)
                .addGap(18, 18, 18))
            .addComponent(jScrollPane10)
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton26)
                    .addComponent(jButton27)
                    .addComponent(jTextField24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel75)
                    .addComponent(jLabel76)
                    .addComponent(角色总数标签))
                .addGap(0, 0, 0)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 404, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel43Layout = new javax.swing.GroupLayout(jPanel43);
        jPanel43.setLayout(jPanel43Layout);
        jPanel43Layout.setHorizontalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 975, Short.MAX_VALUE)
            .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel43Layout.setVerticalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 622, Short.MAX_VALUE)
            .addGroup(jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel19, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("角色中心", jPanel43);

        资源页签.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N

        身上.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "序号", "物品名字", "物品ID", "物品数量", "图标"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        身上.setGridColor(new java.awt.Color(255, 255, 255));
        身上.setRowHeight(35);
        身上.setSelectionForeground(new java.awt.Color(128, 128, 128));
        身上.getTableHeader().setReorderingAllowed(false);
        jScrollPane16.setViewportView(身上);

        javax.swing.GroupLayout jPanel57Layout = new javax.swing.GroupLayout(jPanel57);
        jPanel57.setLayout(jPanel57Layout);
        jPanel57Layout.setHorizontalGroup(
            jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel57Layout.createSequentialGroup()
                .addComponent(jScrollPane16, javax.swing.GroupLayout.PREFERRED_SIZE, 501, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel57Layout.setVerticalGroup(
            jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane16, javax.swing.GroupLayout.DEFAULT_SIZE, 515, Short.MAX_VALUE)
        );

        资源页签.addTab("身上", jPanel57);

        jPanel69.setBackground(new java.awt.Color(255, 255, 255));

        装备.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "序号", "物品名字", "物品ID", "物品数量", "图标"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        装备.setGridColor(new java.awt.Color(255, 255, 255));
        装备.setRowHeight(35);
        装备.setSelectionForeground(new java.awt.Color(128, 128, 128));
        装备.getTableHeader().setReorderingAllowed(false);
        jScrollPane15.setViewportView(装备);

        javax.swing.GroupLayout jPanel69Layout = new javax.swing.GroupLayout(jPanel69);
        jPanel69.setLayout(jPanel69Layout);
        jPanel69Layout.setHorizontalGroup(
            jPanel69Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel69Layout.createSequentialGroup()
                .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 501, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel69Layout.setVerticalGroup(
            jPanel69Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        资源页签.addTab("装备", jPanel69);

        jPanel70.setBackground(new java.awt.Color(255, 255, 255));

        消耗.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "序号", "物品名字", "物品ID", "物品数量", "图标"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        消耗.setGridColor(new java.awt.Color(255, 255, 255));
        消耗.setRowHeight(35);
        消耗.setSelectionForeground(new java.awt.Color(128, 128, 128));
        消耗.getTableHeader().setReorderingAllowed(false);
        jScrollPane17.setViewportView(消耗);

        javax.swing.GroupLayout jPanel70Layout = new javax.swing.GroupLayout(jPanel70);
        jPanel70.setLayout(jPanel70Layout);
        jPanel70Layout.setHorizontalGroup(
            jPanel70Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel70Layout.createSequentialGroup()
                .addComponent(jScrollPane17, javax.swing.GroupLayout.PREFERRED_SIZE, 501, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel70Layout.setVerticalGroup(
            jPanel70Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane17, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        资源页签.addTab("消耗", jPanel70);

        jPanel71.setBackground(new java.awt.Color(255, 255, 255));

        设置.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "序号", "物品名字", "物品ID", "物品数量", "图标"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        设置.setGridColor(new java.awt.Color(255, 255, 255));
        设置.setRowHeight(35);
        设置.setSelectionForeground(new java.awt.Color(128, 128, 128));
        设置.getTableHeader().setReorderingAllowed(false);
        jScrollPane18.setViewportView(设置);

        javax.swing.GroupLayout jPanel71Layout = new javax.swing.GroupLayout(jPanel71);
        jPanel71.setLayout(jPanel71Layout);
        jPanel71Layout.setHorizontalGroup(
            jPanel71Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel71Layout.createSequentialGroup()
                .addComponent(jScrollPane18, javax.swing.GroupLayout.PREFERRED_SIZE, 501, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel71Layout.setVerticalGroup(
            jPanel71Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane18, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        资源页签.addTab("设置", jPanel71);

        jPanel73.setBackground(new java.awt.Color(255, 255, 255));

        其他.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "序号", "物品名字", "物品ID", "物品数量", "图标"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        其他.setGridColor(new java.awt.Color(255, 255, 255));
        其他.setRowHeight(35);
        其他.setSelectionForeground(new java.awt.Color(128, 128, 128));
        其他.getTableHeader().setReorderingAllowed(false);
        jScrollPane19.setViewportView(其他);

        javax.swing.GroupLayout jPanel73Layout = new javax.swing.GroupLayout(jPanel73);
        jPanel73.setLayout(jPanel73Layout);
        jPanel73Layout.setHorizontalGroup(
            jPanel73Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel73Layout.createSequentialGroup()
                .addComponent(jScrollPane19, javax.swing.GroupLayout.PREFERRED_SIZE, 501, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel73Layout.setVerticalGroup(
            jPanel73Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane19, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        资源页签.addTab("其他", jPanel73);

        jPanel74.setBackground(new java.awt.Color(255, 255, 255));

        特殊.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "序号", "物品名字", "物品ID", "物品数量", "图标"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        特殊.setGridColor(new java.awt.Color(255, 255, 255));
        特殊.setRowHeight(35);
        特殊.setSelectionForeground(new java.awt.Color(128, 128, 128));
        特殊.getTableHeader().setReorderingAllowed(false);
        jScrollPane20.setViewportView(特殊);

        javax.swing.GroupLayout jPanel74Layout = new javax.swing.GroupLayout(jPanel74);
        jPanel74.setLayout(jPanel74Layout);
        jPanel74Layout.setHorizontalGroup(
            jPanel74Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel74Layout.createSequentialGroup()
                .addComponent(jScrollPane20, javax.swing.GroupLayout.PREFERRED_SIZE, 501, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel74Layout.setVerticalGroup(
            jPanel74Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane20, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        资源页签.addTab("特殊", jPanel74);

        jPanel75.setBackground(new java.awt.Color(255, 255, 255));

        仓库.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "序号", "物品名字", "物品ID", "物品数量", "图标"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        仓库.setGridColor(new java.awt.Color(255, 255, 255));
        仓库.setRowHeight(35);
        仓库.setSelectionForeground(new java.awt.Color(128, 128, 128));
        仓库.getTableHeader().setReorderingAllowed(false);
        jScrollPane21.setViewportView(仓库);

        javax.swing.GroupLayout jPanel75Layout = new javax.swing.GroupLayout(jPanel75);
        jPanel75.setLayout(jPanel75Layout);
        jPanel75Layout.setHorizontalGroup(
            jPanel75Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel75Layout.createSequentialGroup()
                .addComponent(jScrollPane21, javax.swing.GroupLayout.PREFERRED_SIZE, 501, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel75Layout.setVerticalGroup(
            jPanel75Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane21, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        资源页签.addTab("仓库", jPanel75);

        jPanel76.setBackground(new java.awt.Color(255, 255, 255));

        商城.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "序号", "物品名字", "物品ID", "物品数量", "图标"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        商城.setGridColor(new java.awt.Color(255, 255, 255));
        商城.setRowHeight(35);
        商城.setSelectionForeground(new java.awt.Color(128, 128, 128));
        商城.getTableHeader().setReorderingAllowed(false);
        jScrollPane22.setViewportView(商城);

        javax.swing.GroupLayout jPanel76Layout = new javax.swing.GroupLayout(jPanel76);
        jPanel76.setLayout(jPanel76Layout);
        jPanel76Layout.setHorizontalGroup(
            jPanel76Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel76Layout.createSequentialGroup()
                .addComponent(jScrollPane22, javax.swing.GroupLayout.PREFERRED_SIZE, 501, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel76Layout.setVerticalGroup(
            jPanel76Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane22, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        资源页签.addTab("商城", jPanel76);

        jPanel65.setBorder(javax.swing.BorderFactory.createTitledBorder("修改选项"));

        jLabel35.setForeground(new java.awt.Color(255, 0, 255));
        jLabel35.setText("提示:只可以修改物品数量,不可以修改装备数量.");

        jButton34.setText("修改选中行");
        jButton34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton34ActionPerformed(evt);
            }
        });

        jButton65.setText("删除选中行");
        jButton65.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton65ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel65Layout = new javax.swing.GroupLayout(jPanel65);
        jPanel65.setLayout(jPanel65Layout);
        jPanel65Layout.setHorizontalGroup(
            jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel65Layout.createSequentialGroup()
                .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel65Layout.createSequentialGroup()
                        .addGap(93, 93, 93)
                        .addComponent(jLabel35))
                    .addGroup(jPanel65Layout.createSequentialGroup()
                        .addGap(125, 125, 125)
                        .addComponent(jButton34)
                        .addGap(18, 18, 18)
                        .addComponent(jButton65)))
                .addContainerGap(144, Short.MAX_VALUE))
        );
        jPanel65Layout.setVerticalGroup(
            jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel65Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel35)
                .addGap(0, 0, 0)
                .addGroup(jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton65, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel58.setBorder(javax.swing.BorderFactory.createTitledBorder("角色技能"));

        技能信息.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "序号", "技能名字", "技能ID", "目前等级", "最高等级", "技能图标"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        技能信息.setGridColor(new java.awt.Color(255, 255, 255));
        技能信息.setRowHeight(35);
        技能信息.setSelectionForeground(new java.awt.Color(128, 128, 128));
        技能信息.getTableHeader().setReorderingAllowed(false);
        jScrollPane23.setViewportView(技能信息);

        javax.swing.GroupLayout jPanel58Layout = new javax.swing.GroupLayout(jPanel58);
        jPanel58.setLayout(jPanel58Layout);
        jPanel58Layout.setHorizontalGroup(
            jPanel58Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane23, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel58Layout.setVerticalGroup(
            jPanel58Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane23, javax.swing.GroupLayout.DEFAULT_SIZE, 521, Short.MAX_VALUE)
        );

        jPanel77.setBorder(javax.swing.BorderFactory.createTitledBorder("修改技能等级"));

        jLabel39.setForeground(new java.awt.Color(255, 0, 255));
        jLabel39.setText("提示:只可以修改目前等级,不可以修改最高等级.");

        jButton66.setText("修改选中行");
        jButton66.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton66ActionPerformed(evt);
            }
        });

        jButton67.setText("删除选中行");
        jButton67.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton67ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel77Layout = new javax.swing.GroupLayout(jPanel77);
        jPanel77.setLayout(jPanel77Layout);
        jPanel77Layout.setHorizontalGroup(
            jPanel77Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel77Layout.createSequentialGroup()
                .addGroup(jPanel77Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel77Layout.createSequentialGroup()
                        .addGap(93, 93, 93)
                        .addComponent(jLabel39))
                    .addGroup(jPanel77Layout.createSequentialGroup()
                        .addGap(125, 125, 125)
                        .addComponent(jButton66)
                        .addGap(18, 18, 18)
                        .addComponent(jButton67)))
                .addGap(0, 105, Short.MAX_VALUE))
        );
        jPanel77Layout.setVerticalGroup(
            jPanel77Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel77Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel39)
                .addGap(0, 0, 0)
                .addGroup(jPanel77Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton66, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton67, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout jPanel45Layout = new javax.swing.GroupLayout(jPanel45);
        jPanel45.setLayout(jPanel45Layout);
        jPanel45Layout.setHorizontalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel45Layout.createSequentialGroup()
                .addComponent(jPanel65, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel58, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel77, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel45Layout.createSequentialGroup()
                    .addComponent(资源页签, javax.swing.GroupLayout.PREFERRED_SIZE, 506, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 469, Short.MAX_VALUE)))
        );
        jPanel45Layout.setVerticalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel45Layout.createSequentialGroup()
                .addComponent(jPanel58, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel65, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel77, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel45Layout.createSequentialGroup()
                    .addComponent(资源页签, javax.swing.GroupLayout.PREFERRED_SIZE, 544, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 81, Short.MAX_VALUE)))
        );

        jTabbedPane3.addTab("道具&技能", jPanel45);

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane3)
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane3, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        jTabbedPane.addTab("角色中心", new javax.swing.ImageIcon(getClass().getResource("/Image/3994264.png")), jPanel26); // NOI18N

        jPanel27.setBorder(javax.swing.BorderFactory.createTitledBorder("发送装备"));

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("全服发送道具"));

        jLabel2.setText("物品代码:");

        全服发送物品代码.setToolTipText("");

        全服发送物品数量.setText("1");
        全服发送物品数量.setToolTipText("");

        jLabel6.setText("物品数量:");

        给予物品1.setFont(new java.awt.Font("宋体", 0, 15)); // NOI18N
        给予物品1.setText("确认发送");
        给予物品1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                给予物品1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(全服发送物品代码, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(全服发送物品数量, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(给予物品1)
                .addContainerGap(76, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(全服发送物品代码, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(全服发送物品数量, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(给予物品1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("个人发送道具"));

        jLabel241.setFont(new java.awt.Font("宋体", 0, 14)); // NOI18N
        jLabel241.setText("玩家名字");

        个人发送物品玩家名字.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                个人发送物品玩家名字ActionPerformed(evt);
            }
        });

        jLabel242.setFont(new java.awt.Font("宋体", 0, 14)); // NOI18N
        jLabel242.setText("物品代码");

        个人发送物品代码.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                个人发送物品代码ActionPerformed(evt);
            }
        });

        jLabel240.setFont(new java.awt.Font("宋体", 0, 14)); // NOI18N
        jLabel240.setText("物品数量:");

        个人发送物品数量.setText("0");
        个人发送物品数量.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                个人发送物品数量ActionPerformed(evt);
            }
        });

        给予物品.setFont(new java.awt.Font("宋体", 0, 15)); // NOI18N
        给予物品.setText("确认发送");
        给予物品.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                给予物品ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 483, Short.MAX_VALUE)
            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel10Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel10Layout.createSequentialGroup()
                            .addComponent(jLabel241)
                            .addGap(84, 84, 84)
                            .addComponent(jLabel242)
                            .addGap(84, 84, 84)
                            .addComponent(jLabel240))
                        .addGroup(jPanel10Layout.createSequentialGroup()
                            .addComponent(个人发送物品玩家名字, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(10, 10, 10)
                            .addComponent(个人发送物品代码, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(10, 10, 10)
                            .addComponent(个人发送物品数量, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(10, 10, 10)
                            .addComponent(给予物品, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel10Layout.createSequentialGroup()
                    .addGap(0, 10, Short.MAX_VALUE)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel241)
                        .addComponent(jLabel242)
                        .addComponent(jLabel240))
                    .addGap(4, 4, 4)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(个人发送物品玩家名字, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(个人发送物品代码, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(个人发送物品数量, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(给予物品, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(0, 10, Short.MAX_VALUE)))
        );

        jPanel34.setBorder(javax.swing.BorderFactory.createTitledBorder("个人发送福利"));

        z7.setFont(new java.awt.Font("宋体", 0, 15)); // NOI18N
        z7.setText("发送抵用");
        z7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                z7ActionPerformed(evt);
            }
        });

        z8.setFont(new java.awt.Font("宋体", 0, 15)); // NOI18N
        z8.setText("发送金币");
        z8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                z8ActionPerformed(evt);
            }
        });

        z9.setFont(new java.awt.Font("宋体", 0, 15)); // NOI18N
        z9.setText("发送点券");
        z9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                z9ActionPerformed(evt);
            }
        });

        z10.setFont(new java.awt.Font("宋体", 0, 15)); // NOI18N
        z10.setText("发送经验");
        z10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                z10ActionPerformed(evt);
            }
        });

        z11.setFont(new java.awt.Font("宋体", 0, 15)); // NOI18N
        z11.setText("发送人气");
        z11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                z11ActionPerformed(evt);
            }
        });

        z12.setFont(new java.awt.Font("宋体", 0, 15)); // NOI18N
        z12.setText("发送豆豆");
        z12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                z12ActionPerformed(evt);
            }
        });

        a2.setText("0");
        a2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                a2ActionPerformed(evt);
            }
        });

        jLabel236.setText("数量");

        个人发送物品玩家名字1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                个人发送物品玩家名字1ActionPerformed(evt);
            }
        });

        jLabel59.setText("玩家名字");

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel34Layout.createSequentialGroup()
                        .addComponent(jLabel59)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(个人发送物品玩家名字1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(jLabel236)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(a2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel34Layout.createSequentialGroup()
                        .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel34Layout.createSequentialGroup()
                                .addComponent(z9, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40)
                                .addComponent(z10, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel34Layout.createSequentialGroup()
                                .addComponent(z7, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40)
                                .addComponent(z11, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel34Layout.createSequentialGroup()
                                .addComponent(z8, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(40, 40, 40)
                                .addComponent(z12, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(9, 9, 9)))
                .addContainerGap(95, Short.MAX_VALUE))
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(a2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel236))
                    .addGroup(jPanel34Layout.createSequentialGroup()
                        .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel59)
                            .addComponent(个人发送物品玩家名字1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)))
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(z9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(z10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(z7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(z11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(z8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(z12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel38.setBorder(javax.swing.BorderFactory.createTitledBorder("全服发送福利"));

        z2.setFont(new java.awt.Font("宋体", 0, 15)); // NOI18N
        z2.setText("发送抵用");
        z2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                z2ActionPerformed(evt);
            }
        });

        z3.setFont(new java.awt.Font("宋体", 0, 15)); // NOI18N
        z3.setText("发送金币");
        z3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                z3ActionPerformed(evt);
            }
        });

        z1.setFont(new java.awt.Font("宋体", 0, 15)); // NOI18N
        z1.setText("发送点券");
        z1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                z1ActionPerformed(evt);
            }
        });

        z4.setFont(new java.awt.Font("宋体", 0, 15)); // NOI18N
        z4.setText("发送经验");
        z4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                z4ActionPerformed(evt);
            }
        });

        z5.setFont(new java.awt.Font("宋体", 0, 15)); // NOI18N
        z5.setText("发送人气");
        z5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                z5ActionPerformed(evt);
            }
        });

        z6.setFont(new java.awt.Font("宋体", 0, 15)); // NOI18N
        z6.setText("发送豆豆");
        z6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                z6ActionPerformed(evt);
            }
        });

        a1.setText("0");
        a1.setToolTipText("");
        a1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                a1ActionPerformed(evt);
            }
        });

        jLabel235.setFont(new java.awt.Font("宋体", 0, 14)); // NOI18N
        jLabel235.setText("数量");

        javax.swing.GroupLayout jPanel38Layout = new javax.swing.GroupLayout(jPanel38);
        jPanel38.setLayout(jPanel38Layout);
        jPanel38Layout.setHorizontalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel38Layout.createSequentialGroup()
                    .addGap(0, 104, Short.MAX_VALUE)
                    .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel38Layout.createSequentialGroup()
                            .addGap(30, 30, 30)
                            .addComponent(jLabel235)
                            .addGap(22, 22, 22)
                            .addComponent(a1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel38Layout.createSequentialGroup()
                            .addComponent(z1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(40, 40, 40)
                            .addComponent(z4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel38Layout.createSequentialGroup()
                            .addComponent(z2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(40, 40, 40)
                            .addComponent(z5, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel38Layout.createSequentialGroup()
                            .addComponent(z3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(40, 40, 40)
                            .addComponent(z6, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGap(0, 105, Short.MAX_VALUE)))
        );
        jPanel38Layout.setVerticalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 406, Short.MAX_VALUE)
            .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel38Layout.createSequentialGroup()
                    .addGap(0, 96, Short.MAX_VALUE)
                    .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel38Layout.createSequentialGroup()
                            .addGap(10, 10, 10)
                            .addComponent(jLabel235))
                        .addComponent(a1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(30, 30, 30)
                    .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(z1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(z4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(20, 20, 20)
                    .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(z2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(z5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(20, 20, 20)
                    .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(z3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(z6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(0, 96, Short.MAX_VALUE)))
        );

        jPanel39.setBorder(javax.swing.BorderFactory.createTitledBorder("综合发送装备[个人发送需要填写名字]"));

        全服发送装备装备加卷.setText("0");
        全服发送装备装备加卷.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                全服发送装备装备加卷ActionPerformed(evt);
            }
        });

        全服发送装备装备制作人.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                全服发送装备装备制作人ActionPerformed(evt);
            }
        });

        全服发送装备装备力量.setText("0");
        全服发送装备装备力量.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                全服发送装备装备力量ActionPerformed(evt);
            }
        });

        全服发送装备装备MP.setText("0");
        全服发送装备装备MP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                全服发送装备装备MPActionPerformed(evt);
            }
        });

        全服发送装备装备智力.setText("0");
        全服发送装备装备智力.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                全服发送装备装备智力ActionPerformed(evt);
            }
        });

        全服发送装备装备运气.setText("0");
        全服发送装备装备运气.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                全服发送装备装备运气ActionPerformed(evt);
            }
        });

        全服发送装备装备HP.setText("0");
        全服发送装备装备HP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                全服发送装备装备HPActionPerformed(evt);
            }
        });

        全服发送装备装备攻击力.setText("0");
        全服发送装备装备攻击力.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                全服发送装备装备攻击力ActionPerformed(evt);
            }
        });

        全服发送装备装备给予时间.setText("7");
        全服发送装备装备给予时间.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                全服发送装备装备给予时间ActionPerformed(evt);
            }
        });

        全服发送装备装备可否交易.setText("0");
        全服发送装备装备可否交易.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                全服发送装备装备可否交易ActionPerformed(evt);
            }
        });

        全服发送装备装备敏捷.setText("0");
        全服发送装备装备敏捷.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                全服发送装备装备敏捷ActionPerformed(evt);
            }
        });

        全服发送装备物品ID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                全服发送装备物品IDActionPerformed(evt);
            }
        });

        全服发送装备装备魔法力.setText("0");
        全服发送装备装备魔法力.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                全服发送装备装备魔法力ActionPerformed(evt);
            }
        });

        全服发送装备装备魔法防御.setText("0");
        全服发送装备装备魔法防御.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                全服发送装备装备魔法防御ActionPerformed(evt);
            }
        });

        全服发送装备装备物理防御.setText("0");
        全服发送装备装备物理防御.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                全服发送装备装备物理防御ActionPerformed(evt);
            }
        });

        给予装备1.setFont(new java.awt.Font("宋体", 0, 15)); // NOI18N
        给予装备1.setText("个人发送");
        给予装备1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                给予装备1ActionPerformed(evt);
            }
        });

        jLabel219.setFont(new java.awt.Font("宋体", 0, 14)); // NOI18N
        jLabel219.setText("能否交易填0");

        jLabel220.setFont(new java.awt.Font("宋体", 0, 14)); // NOI18N
        jLabel220.setText("HP加成");

        jLabel221.setFont(new java.awt.Font("宋体", 0, 14)); // NOI18N
        jLabel221.setText("魔法攻击力");

        jLabel222.setFont(new java.awt.Font("宋体", 0, 14)); // NOI18N
        jLabel222.setText("装备代码");

        jLabel223.setFont(new java.awt.Font("宋体", 0, 14)); // NOI18N
        jLabel223.setText("MP加成");

        jLabel224.setFont(new java.awt.Font("宋体", 0, 14)); // NOI18N
        jLabel224.setText("物理攻击力");

        jLabel225.setFont(new java.awt.Font("宋体", 0, 14)); // NOI18N
        jLabel225.setText("可砸卷次数");

        jLabel226.setFont(new java.awt.Font("宋体", 0, 14)); // NOI18N
        jLabel226.setText("装备署名");

        jLabel227.setFont(new java.awt.Font("宋体", 0, 14)); // NOI18N
        jLabel227.setText("装备力量");

        jLabel228.setFont(new java.awt.Font("宋体", 0, 14)); // NOI18N
        jLabel228.setText("装备敏捷");

        jLabel229.setFont(new java.awt.Font("宋体", 0, 14)); // NOI18N
        jLabel229.setText("装备智力");

        jLabel230.setFont(new java.awt.Font("宋体", 0, 14)); // NOI18N
        jLabel230.setText("装备运气");

        jLabel231.setFont(new java.awt.Font("宋体", 0, 14)); // NOI18N
        jLabel231.setText("魔法防御");

        jLabel232.setFont(new java.awt.Font("宋体", 0, 14)); // NOI18N
        jLabel232.setText("物理防御");

        jLabel233.setFont(new java.awt.Font("宋体", 0, 14)); // NOI18N
        jLabel233.setText("限时时间");

        发送装备玩家姓名.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                发送装备玩家姓名ActionPerformed(evt);
            }
        });

        给予装备2.setFont(new java.awt.Font("宋体", 0, 15)); // NOI18N
        给予装备2.setText("全服发送");
        给予装备2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                给予装备2ActionPerformed(evt);
            }
        });

        jLabel246.setFont(new java.awt.Font("宋体", 0, 14)); // NOI18N
        jLabel246.setText("玩家名字");

        全服发送装备装备潜能3.setText("0");
        全服发送装备装备潜能3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                全服发送装备装备潜能3ActionPerformed(evt);
            }
        });

        全服发送装备装备潜能1.setText("0");
        全服发送装备装备潜能1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                全服发送装备装备潜能1ActionPerformed(evt);
            }
        });

        全服发送装备装备潜能2.setText("0");
        全服发送装备装备潜能2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                全服发送装备装备潜能2ActionPerformed(evt);
            }
        });

        jLabel72.setFont(new java.awt.Font("宋体", 0, 14)); // NOI18N
        jLabel72.setText("潜能1");

        jLabel73.setFont(new java.awt.Font("宋体", 0, 14)); // NOI18N
        jLabel73.setText("潜能2");

        jLabel74.setFont(new java.awt.Font("宋体", 0, 14)); // NOI18N
        jLabel74.setText("潜能3");

        qiannengdaima.setText("查看潜能代码");
        qiannengdaima.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                qiannengdaimaActionPerformed(evt);
            }
        });

        装备星星1.setText("0");
        装备星星1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                装备星星1ActionPerformed(evt);
            }
        });

        jLabel77.setFont(new java.awt.Font("宋体", 0, 14)); // NOI18N
        jLabel77.setText("装备星星");

        javax.swing.GroupLayout jPanel39Layout = new javax.swing.GroupLayout(jPanel39);
        jPanel39.setLayout(jPanel39Layout);
        jPanel39Layout.setHorizontalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel39Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel39Layout.createSequentialGroup()
                            .addComponent(jLabel246)
                            .addGap(54, 54, 54)
                            .addComponent(jLabel222)
                            .addGap(54, 54, 54)
                            .addComponent(jLabel221)
                            .addGap(40, 40, 40)
                            .addComponent(jLabel224))
                        .addGroup(jPanel39Layout.createSequentialGroup()
                            .addComponent(发送装备玩家姓名, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(10, 10, 10)
                            .addComponent(全服发送装备物品ID, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(10, 10, 10)
                            .addComponent(全服发送装备装备魔法力, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(10, 10, 10)
                            .addComponent(全服发送装备装备攻击力, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel39Layout.createSequentialGroup()
                            .addComponent(jLabel223)
                            .addGap(68, 68, 68)
                            .addComponent(jLabel220)
                            .addGap(68, 68, 68)
                            .addComponent(jLabel225)
                            .addGap(40, 40, 40)
                            .addComponent(jLabel230))
                        .addGroup(jPanel39Layout.createSequentialGroup()
                            .addComponent(全服发送装备装备MP, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(10, 10, 10)
                            .addComponent(全服发送装备装备HP, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(10, 10, 10)
                            .addComponent(全服发送装备装备加卷, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(10, 10, 10)
                            .addComponent(全服发送装备装备运气, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel39Layout.createSequentialGroup()
                            .addComponent(jLabel227)
                            .addGap(54, 54, 54)
                            .addComponent(jLabel228)
                            .addGap(54, 54, 54)
                            .addComponent(jLabel229)
                            .addGap(54, 54, 54)
                            .addComponent(jLabel219))
                        .addGroup(jPanel39Layout.createSequentialGroup()
                            .addComponent(全服发送装备装备力量, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(10, 10, 10)
                            .addComponent(全服发送装备装备敏捷, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(10, 10, 10)
                            .addComponent(全服发送装备装备智力, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(10, 10, 10)
                            .addComponent(全服发送装备装备可否交易, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel39Layout.createSequentialGroup()
                            .addComponent(jLabel231)
                            .addGap(54, 54, 54)
                            .addComponent(jLabel232)
                            .addGap(54, 54, 54)
                            .addComponent(jLabel233)
                            .addGap(54, 54, 54)
                            .addComponent(jLabel226))
                        .addGroup(jPanel39Layout.createSequentialGroup()
                            .addComponent(全服发送装备装备魔法防御, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(10, 10, 10)
                            .addComponent(全服发送装备装备物理防御, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(10, 10, 10)
                            .addComponent(全服发送装备装备给予时间, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(10, 10, 10)
                            .addComponent(全服发送装备装备制作人, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel39Layout.createSequentialGroup()
                            .addComponent(jLabel72)
                            .addGap(75, 75, 75)
                            .addComponent(jLabel73)
                            .addGap(75, 75, 75)
                            .addComponent(jLabel74)
                            .addGap(75, 75, 75)
                            .addComponent(jLabel77))
                        .addGroup(jPanel39Layout.createSequentialGroup()
                            .addComponent(全服发送装备装备潜能1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(10, 10, 10)
                            .addComponent(全服发送装备装备潜能2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(10, 10, 10)
                            .addComponent(全服发送装备装备潜能3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(10, 10, 10)
                            .addComponent(装备星星1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel39Layout.createSequentialGroup()
                            .addComponent(给予装备2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(10, 10, 10)
                            .addComponent(给予装备1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(10, 10, 10)
                            .addComponent(qiannengdaima)))
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel39Layout.setVerticalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel39Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel246)
                        .addComponent(jLabel222)
                        .addComponent(jLabel221)
                        .addComponent(jLabel224))
                    .addGap(4, 4, 4)
                    .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(发送装备玩家姓名, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(全服发送装备物品ID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(全服发送装备装备魔法力, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(全服发送装备装备攻击力, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(10, 10, 10)
                    .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel223)
                        .addComponent(jLabel220)
                        .addComponent(jLabel225)
                        .addComponent(jLabel230))
                    .addGap(4, 4, 4)
                    .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(全服发送装备装备MP, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(全服发送装备装备HP, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(全服发送装备装备加卷, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(全服发送装备装备运气, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(10, 10, 10)
                    .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel227)
                        .addComponent(jLabel228)
                        .addComponent(jLabel229)
                        .addComponent(jLabel219))
                    .addGap(4, 4, 4)
                    .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(全服发送装备装备力量, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(全服发送装备装备敏捷, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(全服发送装备装备智力, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(全服发送装备装备可否交易, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(10, 10, 10)
                    .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel231)
                        .addComponent(jLabel232)
                        .addComponent(jLabel233)
                        .addComponent(jLabel226))
                    .addGap(4, 4, 4)
                    .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(全服发送装备装备魔法防御, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(全服发送装备装备物理防御, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(全服发送装备装备给予时间, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(全服发送装备装备制作人, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(10, 10, 10)
                    .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel72)
                        .addComponent(jLabel73)
                        .addComponent(jLabel74)
                        .addComponent(jLabel77))
                    .addGap(4, 4, 4)
                    .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(全服发送装备装备潜能1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(全服发送装备装备潜能2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(全服发送装备装备潜能3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(装备星星1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(20, 20, 20)
                    .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(给予装备2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(给予装备1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(qiannengdaima, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel39, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addComponent(jPanel34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane.addTab("发送装备", new javax.swing.ImageIcon(getClass().getResource("/Image/4031041.png")), jPanel27); // NOI18N

        jPanel54.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "转存数据[非开发者请勿点击下方功能]", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("微软雅黑", 0, 12), new java.awt.Color(255, 0, 0))); // NOI18N

        jButton69.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/3994106.png"))); // NOI18N
        jButton69.setText("物品道具");
        jButton69.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton69ActionPerformed(evt);
            }
        });

        jButton70.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/3994106.png"))); // NOI18N
        jButton70.setText("导出爆物数据");
        jButton70.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton70ActionPerformed(evt);
            }
        });

        jButton72.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/3994106.png"))); // NOI18N
        jButton72.setText("NPC名称");
        jButton72.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton72ActionPerformed(evt);
            }
        });

        jButton73.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/3994106.png"))); // NOI18N
        jButton73.setText("怪物技能");
        jButton73.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton73ActionPerformed(evt);
            }
        });

        jButton74.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/3994106.png"))); // NOI18N
        jButton74.setText("问答数据");
        jButton74.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton74ActionPerformed(evt);
            }
        });

        jButton75.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/3994106.png"))); // NOI18N
        jButton75.setText("更新任务");
        jButton75.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton75ActionPerformed(evt);
            }
        });

        jButton76.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/3994106.png"))); // NOI18N
        jButton76.setText("发型脸型");
        jButton76.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton76ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel54Layout = new javax.swing.GroupLayout(jPanel54);
        jPanel54.setLayout(jPanel54Layout);
        jPanel54Layout.setHorizontalGroup(
            jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel54Layout.createSequentialGroup()
                .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel54Layout.createSequentialGroup()
                        .addGap(107, 107, 107)
                        .addComponent(jButton70, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel54Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jButton73, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton69, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel54Layout.createSequentialGroup()
                                .addComponent(jButton72, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                                .addComponent(jButton75, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel54Layout.createSequentialGroup()
                                .addComponent(jButton74, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(jButton76, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addGap(16, 16, 16))
        );
        jPanel54Layout.setVerticalGroup(
            jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel54Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton69, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton72, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton75, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton73, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton74, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton76, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addComponent(jButton70, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel55.setBorder(javax.swing.BorderFactory.createTitledBorder("其他工具"));

        jButton31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/4030011.png"))); // NOI18N
        jButton31.setText("代码查询器");
        jButton31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton31ActionPerformed(evt);
            }
        });

        jButton22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/3800871.png"))); // NOI18N
        jButton22.setText("基址计算工具");
        jButton22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton22ActionPerformed(evt);
            }
        });

        jButton23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/3800871.png"))); // NOI18N
        jButton23.setText("包头转换工具");
        jButton23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton23ActionPerformed(evt);
            }
        });

        jButton29.setForeground(new java.awt.Color(255, 51, 51));
        jButton29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/3800871.png"))); // NOI18N
        jButton29.setText("一键清空数据库");
        jButton29.setPreferredSize(new java.awt.Dimension(145, 38));
        jButton29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton29ActionPerformed(evt);
            }
        });

        jButton39.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/3800871.png"))); // NOI18N
        jButton39.setText("NPC删除工具");
        jButton39.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton39ActionPerformed(evt);
            }
        });

        jButton44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/3800871.png"))); // NOI18N
        jButton44.setText("抽奖管理工具");
        jButton44.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton44ActionPerformed(evt);
            }
        });

        jButton51.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/日志.png"))); // NOI18N
        jButton51.setText("批量删除物品");
        jButton51.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton51ActionPerformed(evt);
            }
        });

        jButton53.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/设置.png"))); // NOI18N
        jButton53.setText("MACip封禁");
        jButton53.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton53ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel55Layout = new javax.swing.GroupLayout(jPanel55);
        jPanel55.setLayout(jPanel55Layout);
        jPanel55Layout.setHorizontalGroup(
            jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel55Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton29, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                    .addComponent(jButton31, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton53, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(19, 19, 19)
                .addGroup(jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jButton22, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton39, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton51, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton44, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(30, 30, 30))
        );
        jPanel55Layout.setVerticalGroup(
            jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel55Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel55Layout.createSequentialGroup()
                        .addComponent(jButton22, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(jButton39, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton51, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel55Layout.createSequentialGroup()
                        .addGroup(jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton31, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton23, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel55Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(jButton44, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel55Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton53, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(jButton29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel67.setBorder(javax.swing.BorderFactory.createTitledBorder("其他控制台"));

        jButton25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/4310003.png"))); // NOI18N
        jButton25.setText("CDK卡密");
        jButton25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton25ActionPerformed(evt);
            }
        });

        jButton32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/9104092.png"))); // NOI18N
        jButton32.setText("商城管理");
        jButton32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton32ActionPerformed(evt);
            }
        });

        jButton46.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/问题.png"))); // NOI18N
        jButton46.setText("OX答题管理");
        jButton46.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton46ActionPerformed(evt);
            }
        });

        jButton49.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/3010025.png"))); // NOI18N
        jButton49.setText("椅子管理");
        jButton49.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton49ActionPerformed(evt);
            }
        });

        jButton36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/商店管理.png"))); // NOI18N
        jButton36.setText("商店管理");
        jButton36.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton36ActionPerformed(evt);
            }
        });

        jButton37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/管理全域掉落.png"))); // NOI18N
        jButton37.setText("怪物爆率");
        jButton37.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton37ActionPerformed(evt);
            }
        });

        jButton50.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/关于.png"))); // NOI18N
        jButton50.setText("箱子爆率");
        jButton50.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton50ActionPerformed(evt);
            }
        });

        jButton52.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/更新.png"))); // NOI18N
        jButton52.setText("游戏家族");
        jButton52.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton52ActionPerformed(evt);
            }
        });

        jButton54.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/4031683.png"))); // NOI18N
        jButton54.setText("拍卖行管理");
        jButton54.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton54ActionPerformed(evt);
            }
        });

        jButton68.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/更新.png"))); // NOI18N
        jButton68.setText("广播系统");
        jButton68.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton68ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel67Layout = new javax.swing.GroupLayout(jPanel67);
        jPanel67.setLayout(jPanel67Layout);
        jPanel67Layout.setHorizontalGroup(
            jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel67Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel67Layout.createSequentialGroup()
                        .addComponent(jButton54, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton68, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel67Layout.createSequentialGroup()
                        .addGroup(jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel67Layout.createSequentialGroup()
                                .addComponent(jButton32, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton36, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel67Layout.createSequentialGroup()
                                .addComponent(jButton46, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton25, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel67Layout.createSequentialGroup()
                                .addComponent(jButton37, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)
                                .addComponent(jButton50, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel67Layout.createSequentialGroup()
                                .addComponent(jButton49, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addComponent(jButton52, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(332, Short.MAX_VALUE))
        );
        jPanel67Layout.setVerticalGroup(
            jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel67Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                    .addComponent(jButton32, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton36, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton37, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton50, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                    .addComponent(jButton52, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton49, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton46, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton25, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton54, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton68, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(204, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addComponent(jPanel54, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel55, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel67, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel54, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel55, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel67, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67))
        );

        jTabbedPane.addTab("更多功能", new javax.swing.ImageIcon(getClass().getResource("/Image/1802034.png")), jPanel35); // NOI18N

        jPanel36.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "关于我们", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("微软雅黑", 0, 12))); // NOI18N

        jLabel9.setText("[ZeroMS]服务端Ver:079 [正版]");

        jLabel14.setText("请遵守协议,服务端均来自互联网,如有法律侵权请第一时间联系我们删除.");

        jLabel13.setText("请勿商业用途,后果自负.");

        jLabel12.setText("游戏中遇到BUG请提交到作者");

        jLabel10.setText("我们是永久包版本更新的哦,不收取其他任何费用");

        jLabel8.setText("不得外传和转发,仅研究使用,请勿商业用途,使用完后请立即删除");

        jLabel30.setFont(new java.awt.Font("宋体", 1, 48)); // NOI18N
        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/ZeroMS.png"))); // NOI18N

        javax.swing.GroupLayout jPanel36Layout = new javax.swing.GroupLayout(jPanel36);
        jPanel36.setLayout(jPanel36Layout);
        jPanel36Layout.setHorizontalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel36Layout.createSequentialGroup()
                        .addGap(371, 371, 371)
                        .addComponent(jLabel9))
                    .addGroup(jPanel36Layout.createSequentialGroup()
                        .addGap(364, 364, 364)
                        .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addGroup(jPanel36Layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(jLabel13))))
                    .addGroup(jPanel36Layout.createSequentialGroup()
                        .addGap(258, 258, 258)
                        .addComponent(jLabel14))
                    .addGroup(jPanel36Layout.createSequentialGroup()
                        .addGap(275, 275, 275)
                        .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel8)
                                .addGroup(jPanel36Layout.createSequentialGroup()
                                    .addGap(37, 37, 37)
                                    .addComponent(jLabel10))))))
                .addContainerGap(320, Short.MAX_VALUE))
        );
        jPanel36Layout.setVerticalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addComponent(jLabel12)
                .addGap(18, 18, 18)
                .addComponent(jLabel13)
                .addGap(18, 18, 18)
                .addComponent(jLabel14)
                .addContainerGap(277, Short.MAX_VALUE))
        );

        jTabbedPane.addTab("关于我们", new javax.swing.ImageIcon(getClass().getResource("/Image/4310003.png")), jPanel36); // NOI18N

        jPanel37.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "更新日志", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("微软雅黑", 0, 12))); // NOI18N

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("宋体", 0, 12)); // NOI18N
        jTextArea1.setRows(5);
        jTextArea1.setText("感谢使用ZeroMS079服务端\n10年技术支持，信誉做人。\n服务端出现基础BUG，我们将无偿提供修复更新。\n------------------------------------------\n2022-1-31 ZeroMS079.5更新说明\n1.修复 刷出来的现金装备无法放进商城\n2.新增 优化内存\n3.新增 皮肤更换功能\n4.优化 服务端\n\n2022-1-7 ZeroMS079.4更新说明\n1.修复 NPC不说话 以及部分NPC不走动\n2.新增 宠吸功能\n3.新增 怪物掉落点券控制\n4.新增 技能检测 用于防挂\n5.新增 玩家命令 @解卡组队\n6.新增 控制台功能\n7.新增 函数 cm.spawnMobStats(怪物代码,怪物数量,怪物血量,怪物经验,怪物X坐标,怪物Y坐标);\n\n2021-11-12 ZeroMS079.3更新说明\n1.修复 控制台部分功能\n2.新增 函数 NPCConversationManager 捉鬼任务1() 捉鬼任务2() 捉鬼任务3() 捉鬼任务4()\n3.修正 getByMobSkill(int skill) 读取怪物技能 返回debuff类型\n4.新增 万能记录表单 getFZ1(int someid, String log1) setFZ1(int someid, String log1 ,int slot)\n5.新增 函数 getHp() 读取血量  getMp() 读取蓝量\n\n2021-11-10 ZeroMS079.2更新说明\n1.新增 控制台\n2.修复 修复商城限时特卖的异常出现 \n3.修复 商城热度包\n4.修复 转职之后技能点给的数值写法\n\n2019-11-5 ZeroMS079.1 更新说明\n1.修复 进入游戏的所有包头与拆包。 \n2.修复 NPC点击。 \n3.修复 NPC商城。 \n4.修复 怪物召唤，怪移动的问题。 \n5.修复 人物移动的封包错误问题，修复多人同时在同地图封包错误的问题。 \n6.修复 怪物掉落物品坐标问题。 \n7.修复 椅子 \n8.修复 骑宠 \n9.修复 战，法，弓，飞侠等职业技能。 \n10.修复 辅助技能。 \n11.修复 喇叭封包错误问题。 \n12.修复 招待好友封包问题。 \n13.修复 部分被动技能无效问题\n14.修复 技能宏不保存问题\n15.修复 测谎仪系统\n16.修复 学院系统\n17.修复 钓鱼系统\n18.修复 结婚系统 \n19.新增 自定义商城道具\n20.修复 好友组队问题。\n21.优化 大部分源代码写法(使服务端更加稳定)\n22.最新 开服源码框架，服务端长久稳定(不掉线是不可能的，只能说我们的是最稳定的)");
        jTextArea1.setCaretPosition(1);
        jScrollPane4.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel37Layout = new javax.swing.GroupLayout(jPanel37);
        jPanel37.setLayout(jPanel37Layout);
        jPanel37Layout.setHorizontalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 968, Short.MAX_VALUE)
            .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 968, Short.MAX_VALUE))
        );
        jPanel37Layout.setVerticalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 625, Short.MAX_VALUE)
            .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 625, Short.MAX_VALUE))
        );

        jTabbedPane.addTab("更新日志", new javax.swing.ImageIcon(getClass().getResource("/Image/3994730.png")), jPanel37); // NOI18N

        jLabel7.setText("Code write NetBeans IDE.This ServerManager that made by ZeroMS");

        ActiveThread.setText("游戏线程:0");

        RunStats.setText("运行状态:等待运行");
        RunStats.setMaximumSize(new java.awt.Dimension(110, 15));
        RunStats.setMinimumSize(new java.awt.Dimension(110, 15));

        RunTime.setText("运行时长:0:0:0:0");
        RunTime.setMaximumSize(new java.awt.Dimension(150, 15));
        RunTime.setMinimumSize(new java.awt.Dimension(150, 15));
        RunTime.setName(""); // NOI18N
        RunTime.setPreferredSize(new java.awt.Dimension(200, 15));

        内存使用.setText("内存使用:");

        内存条.setMaximumSize(new java.awt.Dimension(32767, 20));
        内存条.setMinimumSize(new java.awt.Dimension(10, 20));
        内存条.setPreferredSize(new java.awt.Dimension(150, 20));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(内存使用)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(内存条, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ActiveThread, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(RunStats, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(RunTime, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jTabbedPane)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 656, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(RunTime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(RunStats, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ActiveThread, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(内存条, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(内存使用, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void setRunStats(boolean on) {
        jButton1.setEnabled(!on);
     //   ShutdownGameServer.setEnabled(on);
       // RunStats.setText("运行状态:" + (on ? "正在运行" : "已关闭"));
        RunStats.setText("<html>运行状态:<span style='color:green;'>" + (on ? "正在运行" : "等待运行")+ "</span>");
    }
    
    private void startRunTime() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                startRunTime = server.Timer.GuiTimer.getInstance().register(new Runnable() {
                    @Override
                    public void run() {
                    //    RunTime.setText(formatDuring(System.currentTimeMillis() - starttime));
                        RunTime.setText("<html>运行时长:<span style='color:green;'>" + formatDuring(System.currentTimeMillis() - starttime) + "</span>");
                    }
                }, 1000);
            }
        }).start();
    }
    
    public static final String formatDuring(long mss) {
        long days = mss / (1000 * 60 * 60 * 24);
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;
        return  + days + "天" + (hours / 10 == 0 ? "0" : "") + hours + "时" + (minutes / 10 == 0 ? "0" : "") + minutes + "分"
                + (seconds / 10 == 0 ? "0" : "") + seconds + "秒";
    }

    public void updateThreadNum() {
        writeLock.lock();
        try {
            server.Timer.WorldTimer.GuiTimer.getInstance().register(new Runnable() {
                @Override
                public final void run() {
                ActiveThread.setText("<html>游戏线程:<span style='color:red;'>" + Thread.activeCount() + "</span>");
                }
            }, 1 * 1000);
        } finally {
            writeLock.unlock();
        }
    }
    
    
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        int result = JOptionPane.showConfirmDialog(this, "直接关闭服务端又可能不能及时存档,会造成数据丢失或者回档,是否继续?\n" +"正常关闭服务端请在启动服务内点击关闭服务端按钮,等待系统自动关闭.", "温馨提示", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }//GEN-LAST:event_formWindowClosing

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowOpened

    private void 重载副本按钮ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_重载副本按钮ActionPerformed
        // TODO add your handling code here:
        for (ChannelServer instance1 : ChannelServer.getAllInstances()) {
            if (instance1 != null) {
                instance1.reloadEvents();
            }
        }
        System.out.println("[重载系统] 副本重载成功。");
        JOptionPane.showMessageDialog(null, "副本重载成功。");
    }//GEN-LAST:event_重载副本按钮ActionPerformed

    private void 重载爆率按钮ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_重载爆率按钮ActionPerformed
        // TODO add your handling code here:
        MapleMonsterInformationProvider.getInstance().clearDrops();
        System.out.println("[重载系统] 爆率重载成功。");
        JOptionPane.showMessageDialog(null, "爆率重载成功。");
    }//GEN-LAST:event_重载爆率按钮ActionPerformed

    private void 重载传送门按钮ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_重载传送门按钮ActionPerformed
        // TODO add your handling code here:
        PortalScriptManager.getInstance().clearScripts();
        System.out.println("[重载系统] 传送门重载成功。");
        JOptionPane.showMessageDialog(null, "传送门重载成功。");
    }//GEN-LAST:event_重载传送门按钮ActionPerformed

    private void 重载商店按钮ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_重载商店按钮ActionPerformed
        // TODO add your handling code here:
        MapleShopFactory.getInstance().clear();
        System.out.println("[重载系统] 商店重载成功。");
        JOptionPane.showMessageDialog(null, "商店重载成功。");
    }//GEN-LAST:event_重载商店按钮ActionPerformed

    private void 重载包头按钮ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_重载包头按钮ActionPerformed
        // TODO add your handling code here:
        //SendPacketOpcode.reloadValues();
        //RecvPacketOpcode.reloadValues();
        System.out.println("[重载系统] 包头重载成功。");
        JOptionPane.showMessageDialog(null, "包头重载成功。");
    }//GEN-LAST:event_重载包头按钮ActionPerformed

    private void 重载任务ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_重载任务ActionPerformed
        // TODO add your handling code here:
        MapleQuest.clearQuests();
        System.out.println("[重载系统] 任务重载成功。");
        JOptionPane.showMessageDialog(null, "任务重载成功。");
    }//GEN-LAST:event_重载任务ActionPerformed

    private void 重载反应堆按钮ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_重载反应堆按钮ActionPerformed
        // TODO add your handling code here:
        ReactorScriptManager.getInstance().clearDrops();
        System.out.println("[重载系统] 反应堆重载成功。");
        JOptionPane.showMessageDialog(null, "反应堆重载成功。");
    }//GEN-LAST:event_重载反应堆按钮ActionPerformed

    private void 重载商城按钮ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_重载商城按钮ActionPerformed
        // TODO add your handling code here:
        CashItemFactory.getInstance().clearItems();
        System.out.println("[重载系统] 商城重载成功。");
        JOptionPane.showMessageDialog(null, "商城重载成功。");
    }//GEN-LAST:event_重载商城按钮ActionPerformed

    private void 保存雇佣按钮ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_保存雇佣按钮ActionPerformed
        // TODO add your handling code here:
        int p = 0;
        for (handling.channel.ChannelServer cserv : handling.channel.ChannelServer.getAllInstances()) {
            p++;
            cserv.closeAllMerchant();
        }
        System.out.println("[保存雇佣商人系统] 雇佣商人保存" + p + "个频道成功。");
        JOptionPane.showMessageDialog(null, "雇佣商人保存" + p + "个频道成功。");
    }//GEN-LAST:event_保存雇佣按钮ActionPerformed

    private void 保存数据按钮ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_保存数据按钮ActionPerformed
        // TODO add your handling code here:
        int p = 0;
        for (ChannelServer cserv : ChannelServer.getAllInstances()) {
            for (MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                p++;
                chr.saveToDB(true, true);
            }
        }
        System.out.println("[保存数据系统] 保存" + p + "个成功。");
        JOptionPane.showMessageDialog(null, "保存数据成功。");
    }//GEN-LAST:event_保存数据按钮ActionPerformed

    private void 查询在线玩家人数按钮ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_查询在线玩家人数按钮ActionPerformed
        int p = 0;
        for (ChannelServer cserv : ChannelServer.getAllInstances()) {
            for (MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                if (chr != null) {
                    ++p;
                }
            }
        }
        JOptionPane.showMessageDialog(this, "当前在线人数：" + p + "人");
    }//GEN-LAST:event_查询在线玩家人数按钮ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        setRunStats(true);//运行状态
        jTabbedPane.setSelectedIndex(0);//启动跳转选项
        new Thread(new Runnable() {
            public void run() {
                JOptionPane.showMessageDialog(null, "服务端启动需要时间,请点击确定继续。");
                Start.启动游戏(null);
                JOptionPane.showMessageDialog(null, "服务端启动完成。");
                jButton1.setText("服务端运行中");
            }
        }).start();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed

        Runtime runtime = Runtime.getRuntime();
        int p = 0;
        for (handling.channel.ChannelServer cserv : handling.channel.ChannelServer.getAllInstances()) {
            p++;
            cserv.closeAllMerchant();
        }
        System.out.println("[保存雇佣商人系统] 雇佣商人保存" + p + "个频道成功。");
        JOptionPane.showMessageDialog(null, "雇佣商人保存" + p + "个频道成功。");
        int pp = 0;
        for (ChannelServer cserv : ChannelServer.getAllInstances()) {
            for (MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                pp++;
                chr.saveToDB(true, true);
            }
        }
        System.out.println("[保存数据系统] 保存" + p + "个成功。");
        JOptionPane.showMessageDialog(null, "保存数据成功。");
        try {

            runtime.exec("taskkill /im java.exe /f");
            runtime.exec("taskkill /im java.exe /f");
            runtime.exec("taskkill /im javax.exe /f");
            runtime.exec("taskkill /im MapleStoryServer主程序模块.exe /f");
            runtime.exec("taskkill /im 启动服务端[64位4G].exe /f");
            runtime.exec("taskkill /im 启动服务端[64位8G].exe /f");

        } catch (Exception e) {

            System.out.println("Error!");

        }
    }//GEN-LAST:event_jButton21ActionPerformed

    private void logs_csbuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logs_csbuyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_logs_csbuyActionPerformed

    private void logs_npcshop_buyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logs_npcshop_buyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_logs_npcshop_buyActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        JOptionPane.showMessageDialog(null, "功能说明\r\n是否开启自定义地图刷怪倍数\r\n如果开启，自定义地图刷怪倍数数值(几倍)\r\n自定义怪物倍数地图列表id(逗号隔开)\r\n此功能开启会增加地图的怪物倍数");
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton69ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton69ActionPerformed
        runTool(Tools.DumpItems);
    }//GEN-LAST:event_jButton69ActionPerformed

    private void jButton70ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton70ActionPerformed
        runTool(Tools.MonsterDropCreator);
    }//GEN-LAST:event_jButton70ActionPerformed

    private void jButton72ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton72ActionPerformed
        runTool(Tools.DumpNpcNames);
    }//GEN-LAST:event_jButton72ActionPerformed

    private void jButton73ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton73ActionPerformed
        runTool(Tools.DumpMobSkills);
    }//GEN-LAST:event_jButton73ActionPerformed

    private void jButton74ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton74ActionPerformed
        runTool(Tools.DumpOxQuizData);
    }//GEN-LAST:event_jButton74ActionPerformed

    private void jButton75ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton75ActionPerformed
        runTool(Tools.DumpQuests);
    }//GEN-LAST:event_jButton75ActionPerformed

    private void jButton31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton31ActionPerformed
        openWindow(Windows.代码查询工具);
        if (!LoginServer.isShutdown() || searchServer) {
            return;

        }
    }//GEN-LAST:event_jButton31ActionPerformed

    private void jButton22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton22ActionPerformed
        openWindow(Windows.基址计算工具);
    }//GEN-LAST:event_jButton22ActionPerformed

    private void jButton23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton23ActionPerformed
        openWindow(Windows.包头转换工具);
    }//GEN-LAST:event_jButton23ActionPerformed

    private void jButton29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton29ActionPerformed
        openWindow(Windows.一键还原);
    }//GEN-LAST:event_jButton29ActionPerformed

    private void jButton39ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton39ActionPerformed
        openWindow(Windows.删除自添加NPC工具);
    }//GEN-LAST:event_jButton39ActionPerformed

    private void jButton44ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton44ActionPerformed
        openWindow(Windows.游戏抽奖工具);
    }//GEN-LAST:event_jButton44ActionPerformed

    private void jButton25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton25ActionPerformed
        openWindow(Windows.卡密制作工具);
    }//GEN-LAST:event_jButton25ActionPerformed

    private void jButton32ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton32ActionPerformed
        openWindow(Windows.商城管理控制台);
    }//GEN-LAST:event_jButton32ActionPerformed

    private void jButton46ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton46ActionPerformed
        openWindow(Windows.OX答题控制台);
    }//GEN-LAST:event_jButton46ActionPerformed

    private void jButton49ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton49ActionPerformed
        openWindow(Windows.椅子控制台);
    }//GEN-LAST:event_jButton49ActionPerformed

    private void sendNoticeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendNoticeActionPerformed
        sendNotice(0);
        String str = noticeText.getText();
        String 输出 = "";
        输出 = "【公告系统】" + CurrentReadable_Time()+ " 发送: " + str;
        printChatLog(输出);
        JOptionPane.showMessageDialog(null, "发送公告成功！");
    }//GEN-LAST:event_sendNoticeActionPerformed

    private void sendWinNoticeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendWinNoticeActionPerformed
        sendNotice(1);
        String str = noticeText.getText();
        String 输出 = "";
        输出 = "【公告系统】" + CurrentReadable_Time()+ " 发送: " + str;
        printChatLog(输出);
        JOptionPane.showMessageDialog(null, "发送公告成功！");
    }//GEN-LAST:event_sendWinNoticeActionPerformed

    private void sendMsgNoticeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendMsgNoticeActionPerformed
        sendNotice(2);
        String str = noticeText.getText();
        String 输出 = "";
        输出 = "【公告系统】" + CurrentReadable_Time()+ " 发送: " + str;
        printChatLog(输出);
        JOptionPane.showMessageDialog(null, "发送公告成功！");
    }//GEN-LAST:event_sendMsgNoticeActionPerformed

    private void sendNpcTalkNoticeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendNpcTalkNoticeActionPerformed
        sendNotice(3);
        String str = noticeText.getText();
        String 输出 = "";
        输出 = "【公告系统】" + CurrentReadable_Time()+ " 发送: " + str;
        printChatLog(输出);
        JOptionPane.showMessageDialog(null, "发送公告成功！");
    }//GEN-LAST:event_sendNpcTalkNoticeActionPerformed

    private void 公告发布喇叭代码ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_公告发布喇叭代码ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_公告发布喇叭代码ActionPerformed

    private void jButton45ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton45ActionPerformed
        sendNotice(4);
        String str = noticeText.getText();
        String 输出 = "";
        输出 = "【公告系统】" + CurrentReadable_Time()+ " 发送: " + str;
        printChatLog(输出);
        JOptionPane.showMessageDialog(null, "发送公告成功！");
    }//GEN-LAST:event_jButton45ActionPerformed
    private void 刷物品() {
        try {
            String 名字;
            if ("玩家名字".equals(个人发送物品玩家名字.getText())) {
                名字 = "";
            } else {
                名字 = 个人发送物品玩家名字.getText();
            }
            int 物品ID;
            if ("物品ID".equals(个人发送物品代码.getText())) {
                物品ID = 0;
            } else {
                物品ID = Integer.parseInt(个人发送物品代码.getText());
            }
            int 数量;
            if ("数量".equals(个人发送物品数量.getText())) {
                数量 = 0;
            } else {
                数量 = Integer.parseInt(个人发送物品数量.getText());
            }
            MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
            MapleInventoryType type = GameConstants.getInventoryType(物品ID);
            for (ChannelServer cserv1 : ChannelServer.getAllInstances()) {
                for (MapleCharacter mch : cserv1.getPlayerStorage().getAllCharacters()) {
                    if (mch.getName().equals(名字)) {
                        if (数量 >= 0) {
                            if (!MapleInventoryManipulator.checkSpace(mch.getClient(), 物品ID, 数量, "")) {
                                return;
                            }
                            if (type.equals(MapleInventoryType.EQUIP) && !GameConstants.isThrowingStar(物品ID) && !GameConstants.isBullet(物品ID)
                                    || type.equals(MapleInventoryType.CASH) && 物品ID >= 5000000 && 物品ID <= 5000100) {
                                final Equip item = (Equip) (ii.getEquipById(物品ID));
                                if (ii.isCash(物品ID)) {
                                    item.setUniqueId(1);
                                }
                                final String name = ii.getName(物品ID);
                                if (物品ID / 10000 == 114 && name != null && name.length() > 0) { //medal
                                    final String msg = "你已获得称号 <" + name + ">";
                                    mch.getClient().getPlayer().dropMessage(5, msg);
                                }
                                MapleInventoryManipulator.addbyItem(mch.getClient(), item.copy());
                            } else {
                            MapleInventoryManipulator.addById(mch.getClient(), 物品ID, (short) 数量, "", null);
                            }
                        } else {
                            MapleInventoryManipulator.removeById(mch.getClient(), GameConstants.getInventoryType(物品ID), 物品ID, -数量, true, false);
                        }
                        mch.getClient().getSession().write(MaplePacketCreator.getShowItemGain(物品ID, (short) 数量, true));

                    }
                }
            }
            个人发送物品玩家名字.setText("");
            个人发送物品代码.setText("");
            个人发送物品数量.setText("");
            JOptionPane.showMessageDialog(null, "[信息]:发送成功。");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "[信息]:错误!" + e);
        }
    }
    private void 刷物品2() {
        try {
            int 数量;
            int 物品ID;
            物品ID = Integer.parseInt(全服发送物品代码.getText());
            数量 = Integer.parseInt(全服发送物品数量.getText());
            MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
            MapleInventoryType type = GameConstants.getInventoryType(物品ID);
            for (ChannelServer cserv1 : ChannelServer.getAllInstances()) {
                for (MapleCharacter mch : cserv1.getPlayerStorage().getAllCharacters()) {
                    if (数量 >= 0) {
                        if (!MapleInventoryManipulator.checkSpace(mch.getClient(), 物品ID, 数量, "")) {
                            return;
                        }
                        if (type.equals(MapleInventoryType.EQUIP) && !GameConstants.isThrowingStar(物品ID) && !GameConstants.isBullet(物品ID)
                                || type.equals(MapleInventoryType.CASH) && 物品ID >= 5000000 && 物品ID <= 5000100) {
                            final Equip item = (Equip) (ii.getEquipById(物品ID));
                            if (ii.isCash(物品ID)) {
                                item.setUniqueId(1);
                            }
                            final String name = ii.getName(物品ID);
                            if (物品ID / 10000 == 114 && name != null && name.length() > 0) { //medal
                                final String msg = "你已获得称号 <" + name + ">";
                                mch.getClient().getPlayer().dropMessage(5, msg);
                            }
                            MapleInventoryManipulator.addbyItem(mch.getClient(), item.copy());
                        } else {
                            MapleInventoryManipulator.addById(mch.getClient(), 物品ID, (short) 数量, "", null);
                        }
                    } else {
                        MapleInventoryManipulator.removeById(mch.getClient(), GameConstants.getInventoryType(物品ID), 物品ID, -数量, true, false);
                    }
                        mch.getClient().getSession().write(MaplePacketCreator.getShowItemGain(物品ID, (short) 数量, true));
                }
            }
            全服发送物品代码.setText("");
            全服发送物品数量.setText("");
            JOptionPane.showMessageDialog(null, "[信息]:发送成功。");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "[信息]:错误!" + e);
        }
    }
        private void 个人发送福利(int a) {
   /*         boolean result1 = this.a1.getText().matches("[0-9]+");
        if (result1) {
            int 数量;
            if ("100000000".equals(a1.getText())) {
                数量 = 100;
            } else {
                数量 = Integer.parseInt(a1.getText());
            }
            if (数量 <= 0 || 数量 > 999999999) {
                return;
            }*/
        int 数量 = 0;
        String 类型 = "";
        String name = "";
        数量 = Integer.parseInt(a2.getText());
        name = 个人发送物品玩家名字1.getText();
        for (ChannelServer cserv1 : ChannelServer.getAllInstances()) {
            for (MapleCharacter mch : cserv1.getPlayerStorage().getAllCharacters()) {
                if (mch.getName().equals(name)) {
                    int ch = World.Find.findChannel(name);
                    if (ch <= 0) {
                        JOptionPane.showMessageDialog(null, "该玩家不在线");
                    }
                    switch (a) {
                        case 1:
                        类型 = "点券";
                        mch.modifyCSPoints(1, 数量, true);
                        mch.dropMessage("已经收到点卷" + 数量 + "点");
                        JOptionPane.showMessageDialog(null, "发送成功");
                        break;
                        case 2:
                        类型 = "抵用券";
                        mch.modifyCSPoints(2, 数量, true);
                        mch.dropMessage("已经收到抵用" + 数量 + "点");
                        JOptionPane.showMessageDialog(null, "发送成功");
                        break;
                        case 3:
                        类型 = "金币";
                        mch.gainMeso(数量, true);
                        mch.dropMessage("已经收到金币" + 数量 + "点");
                        JOptionPane.showMessageDialog(null, "发送成功");
                        break;
                        case 4:
                        类型 = "经验";
                        mch.gainExp(数量, true, false, true);
                        mch.dropMessage("已经收到经验" + 数量 + "点");
                        JOptionPane.showMessageDialog(null, "发送成功");
                        break;
                        case 5:
                        类型 = "人气";
                        mch.addFame(数量);
                        mch.dropMessage("已经收到人气" + 数量 + "点");
                        JOptionPane.showMessageDialog(null, "发送成功");
                        break;
                        case 6:
                        类型 = "豆豆";
                //        mch.gainBeans(数量);
                        mch.dropMessage("已经收到豆豆" + 数量 + "点");
                        JOptionPane.showMessageDialog(null, "发送成功");
                        break;
                    }
                }
            }
        }
    }    private void 刷装备2(int a) {
        try {
            int 物品ID;
            if ("物品ID".equals(全服发送装备物品ID.getText())) {
                物品ID = 0;
            } else {
                物品ID = Integer.parseInt(全服发送装备物品ID.getText());
            }
            int 力量;
            if ("力量".equals(全服发送装备装备力量.getText())) {
                力量 = 0;
            } else {
                力量 = Integer.parseInt(全服发送装备装备力量.getText());
            }
            int 敏捷;
            if ("敏捷".equals(全服发送装备装备敏捷.getText())) {
                敏捷 = 0;
            } else {
                敏捷 = Integer.parseInt(全服发送装备装备敏捷.getText());
            }
            int 智力;
            if ("智力".equals(全服发送装备装备智力.getText())) {
                智力 = 0;
            } else {
                智力 = Integer.parseInt(全服发送装备装备智力.getText());
            }
            int 运气;
            if ("运气".equals(全服发送装备装备运气.getText())) {
                运气 = 0;
            } else {
                运气 = Integer.parseInt(全服发送装备装备运气.getText());
            }
            int HP;
            if ("HP设置".equals(全服发送装备装备HP.getText())) {
                HP = 0;
            } else {
                HP = Integer.parseInt(全服发送装备装备HP.getText());
            }
            int MP;
            if ("MP设置".equals(全服发送装备装备MP.getText())) {
                MP = 0;
            } else {
                MP = Integer.parseInt(全服发送装备装备MP.getText());
            }
            int 可加卷次数;
            if ("加卷次数".equals(全服发送装备装备加卷.getText())) {
                可加卷次数 = 0;
            } else {
                可加卷次数 = Integer.parseInt(全服发送装备装备加卷.getText());
            }

            String 制作人名字;
            if ("制作人".equals(全服发送装备装备制作人.getText())) {
                制作人名字 = "";
            } else {
                制作人名字 = 全服发送装备装备制作人.getText();
            }
            int 给予时间;
            if ("给予物品时间".equals(全服发送装备装备给予时间.getText())) {
                给予时间 = 0;
            } else {
                给予时间 = Integer.parseInt(全服发送装备装备给予时间.getText());
            }
            String 是否可以交易 = 全服发送装备装备可否交易.getText();
            int 攻击力;
            if ("攻击力".equals(全服发送装备装备攻击力.getText())) {
                攻击力 = 0;
            } else {
                攻击力 = Integer.parseInt(全服发送装备装备攻击力.getText());
            }
            int 魔法力;
            if ("魔法力".equals(全服发送装备装备魔法力.getText())) {
                魔法力 = 0;
            } else {
                魔法力 = Integer.parseInt(全服发送装备装备魔法力.getText());
            }
            int 物理防御;
            if ("物理防御".equals(全服发送装备装备物理防御.getText())) {
                物理防御 = 0;
            } else {
                物理防御 = Integer.parseInt(全服发送装备装备物理防御.getText());
            }
            int 魔法防御;
            if ("魔法防御".equals(全服发送装备装备魔法防御.getText())) {
                魔法防御 = 0;
            } else {
                魔法防御 = Integer.parseInt(全服发送装备装备魔法防御.getText());
            }
            int 潜能1;
            if ("潜能1".equals(全服发送装备装备潜能1.getText())) {
                潜能1 = 0;
            } else {
                潜能1 = Integer.parseInt(全服发送装备装备潜能1.getText());
            }
            int 潜能2;
            if ("潜能2".equals(全服发送装备装备潜能2.getText())) {
                潜能2 = 0;
            } else {
                潜能2 = Integer.parseInt(全服发送装备装备潜能2.getText());
            }
            int 潜能3;
            if ("潜能3".equals(全服发送装备装备潜能3.getText())) {
                潜能3 = 0;
            } else {
                潜能3 = Integer.parseInt(全服发送装备装备潜能3.getText());
            }
            int 装备星星;
		if ("装备星星".equals(装备星星1.getText())) {
		装备星星 = 0;
	    } else {
		装备星星 = Integer.parseInt(装备星星1.getText());
	    }
            MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
            MapleInventoryType type = GameConstants.getInventoryType(物品ID);
            for (ChannelServer cserv1 : ChannelServer.getAllInstances()) {
                for (MapleCharacter mch : cserv1.getPlayerStorage().getAllCharacters()) {
                    if (a == 1) {
                        if (1 >= 0) {
                            if (!MapleInventoryManipulator.checkSpace(mch.getClient(), 物品ID, 1, "")) {
                                return;
                            }
                            if (type.equals(MapleInventoryType.EQUIP) && !GameConstants.isThrowingStar(物品ID) && !GameConstants.isBullet(物品ID)
                                    || type.equals(MapleInventoryType.CASH) && 物品ID >= 5000000 && 物品ID <= 5000100) {
                                final Equip item = (Equip) (ii.getEquipById(物品ID));
                                if (ii.isCash(物品ID)) {
                                    item.setUniqueId(1);
                                }
                                if (力量 > 0 && 力量 <= 32767) {
                                    item.setStr((short) (力量));
                                }
                                if (敏捷 > 0 && 敏捷 <= 32767) {
                                    item.setDex((short) (敏捷));
                                }
                                if (智力 > 0 && 智力 <= 32767) {
                                    item.setInt((short) (智力));
                                }
                                if (运气 > 0 && 运气 <= 32767) {
                                    item.setLuk((short) (运气));
                                }
                                if (攻击力 > 0 && 攻击力 <= 32767) {
                                    item.setWatk((short) (攻击力));
                                }
                                if (魔法力 > 0 && 魔法力 <= 32767) {
                                    item.setMatk((short) (魔法力));
                                }
                                if (物理防御 > 0 && 物理防御 <= 32767) {
                                    item.setWdef((short) (物理防御));
                                }
                                if (魔法防御 > 0 && 魔法防御 <= 32767) {
                                    item.setMdef((short) (魔法防御));
                                }
                                if (HP > 0 && HP <= 30000) {
                                    item.setHp((short) (HP));
                                }
                                if (MP > 0 && MP <= 30000) {
                                    item.setMp((short) (MP));
                                }
				if (装备星星 > 0 && 装备星星 < 127) {
				    item.setEnhance((byte) (装备星星));
                                }
                                if ("可以交易".equals(是否可以交易)) {
                                    short flag = item.getFlag();
                                    if (item.getType() == MapleInventoryType.EQUIP.getType()) {
                                        flag |= ItemFlag.KARMA_EQ.getValue();
                                    } else {
                                        flag |= ItemFlag.KARMA_USE.getValue();
                                    }
                                    item.setFlag((byte) flag);
                                }
                                if (给予时间 > 0) {
                                    item.setExpiration(System.currentTimeMillis() + (给予时间 * 24 * 60 * 60 * 1000));
                                }
                                if (可加卷次数 > 0) {
                                    item.setUpgradeSlots((byte) (可加卷次数));
                                }
                                if (制作人名字 != null) {
                                    item.setOwner(制作人名字);
                                }
                                final String name = ii.getName(物品ID);
                                if (物品ID / 10000 == 114 && name != null && name.length() > 0) { //medal
                                    final String msg = "你已获得称号 <" + name + ">";
                                    mch.getClient().getPlayer().dropMessage(5, msg);
                                }
                                MapleInventoryManipulator.addbyItem(mch.getClient(), item.copy());
                            } else {
                                MapleInventoryManipulator.addById(mch.getClient(), 物品ID, (short) 1, "", null, 给予时间, "");
                           //     MapleInventoryManipulator.addById(mch.getClient(), 物品ID, (short) 1, "", null, (byte) 0);
                         //   MapleInventoryManipulator.addById(mch.getClient(), 物品ID, (short) 1, "管理员发放.", null, 给予时间, false, "");

                            }
                        } else {
                            MapleInventoryManipulator.removeById(mch.getClient(), GameConstants.getInventoryType(物品ID), 物品ID, -1, true, false);
                        }
                      //  mch.getClient().getSession().write(CWvsContext.InfoPacket.getShowItemGain(物品ID, (short) 1, true));
                        mch.getClient().getSession().write(MaplePacketCreator.getShowItemGain(物品ID, (short) 1, true));
                    } else if (mch.getName().equals(发送装备玩家姓名.getText())) {
                        if (1 >= 0) {
                            if (!MapleInventoryManipulator.checkSpace(mch.getClient(), 物品ID, 1, "")) {
                                return;
                            }
                            if (type.equals(MapleInventoryType.EQUIP) && !GameConstants.isThrowingStar(物品ID) && !GameConstants.isBullet(物品ID)
                                    || type.equals(MapleInventoryType.CASH) && 物品ID >= 5000000 && 物品ID <= 5000100) {
                                final Equip item = (Equip) (ii.getEquipById(物品ID));
                                if (ii.isCash(物品ID)) {
                                    item.setUniqueId(1);
                                }
                                if (力量 > 0 && 力量 <= 32767) {
                                    item.setStr((short) (力量));
                                }
                                if (敏捷 > 0 && 敏捷 <= 32767) {
                                    item.setDex((short) (敏捷));
                                }
                                if (智力 > 0 && 智力 <= 32767) {
                                    item.setInt((short) (智力));
                                }
                                if (运气 > 0 && 运气 <= 32767) {
                                    item.setLuk((short) (运气));
                                }
                                if (攻击力 > 0 && 攻击力 <= 32767) {
                                    item.setWatk((short) (攻击力));
                                }
                                if (魔法力 > 0 && 魔法力 <= 32767) {
                                    item.setMatk((short) (魔法力));
                                }
                                if (物理防御 > 0 && 物理防御 <= 32767) {
                                    item.setWdef((short) (物理防御));
                                }
                                if (魔法防御 > 0 && 魔法防御 <= 32767) {
                                    item.setMdef((short) (魔法防御));
                                }
                                if (HP > 0 && HP <= 30000) {
                                    item.setHp((short) (HP));
                                }
                                if (MP > 0 && MP <= 30000) {
                                    item.setMp((short) (MP));
                                }
				if (装备星星 > 0 && 装备星星 < 127) {
				    item.setEnhance((byte) (装备星星));
				}
                                if ("可以交易".equals(是否可以交易)) {
                                    short flag = item.getFlag();
                                    if (item.getType() == MapleInventoryType.EQUIP.getType()) {
                                        flag |= ItemFlag.KARMA_EQ.getValue();
                                    } else {
                                        flag |= ItemFlag.KARMA_USE.getValue();
                                    }
                                    item.setFlag((byte) flag);
                                }
                                if (给予时间 > 0) {
                                    item.setExpiration(System.currentTimeMillis() + (给予时间 * 24 * 60 * 60 * 1000));
                                }
                                if (可加卷次数 > 0) {
                                    item.setUpgradeSlots((byte) (可加卷次数));
                                }
                                if (制作人名字 != null) {
                                    item.setOwner(制作人名字);
                                }
                                final String name = ii.getName(物品ID);
                                if (物品ID / 10000 == 114 && name != null && name.length() > 0) { //medal
                                    final String msg = "你已获得称号 <" + name + ">";
                                    mch.getClient().getPlayer().dropMessage(5, msg);
                                }
                                MapleInventoryManipulator.addbyItem(mch.getClient(), item.copy());
                            } else {
                                MapleInventoryManipulator.addById(mch.getClient(), 物品ID, (short) 1, "", null, 给予时间, "");
                          //      MapleInventoryManipulator.addById(mch.getClient(), 物品ID, (short) 1, "管理员发放.", null, 给予时间, false, "");
                            }
                        } else {
                            MapleInventoryManipulator.removeById(mch.getClient(), GameConstants.getInventoryType(物品ID), 物品ID, -1, true, false);
                        }
                      //  mch.getClient().getSession().write(CWvsContext.InfoPacket.getShowItemGain(物品ID, (short) 1, true));
                                                mch.getClient().getSession().write(MaplePacketCreator.getShowItemGain(物品ID, (short) 1, true));

                    }
                }
            }
            JOptionPane.showMessageDialog(null, "[信息]:发送成功。");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "[信息]:错误!" + e);
        }
    }    private void 发送福利(int a) {
        boolean result1 = this.a1.getText().matches("[0-9]+");
        if (result1) {
            int 数量;
            if ("100000000".equals(a1.getText())) {
                数量 = 100;
            } else {
                数量 = Integer.parseInt(a1.getText());
            }
            if (数量 <= 0 || 数量 > 999999999) {
                return;
            }
            String 类型 = "";
            for (ChannelServer cserv1 : ChannelServer.getAllInstances()) {
                for (MapleCharacter mch : cserv1.getPlayerStorage().getAllCharacters()) {

                    switch (a) {
                        case 1:
                            类型 = "点券";
                            mch.modifyCSPoints(1, 数量, true);
                            break;
                        case 2:
                            类型 = "抵用券";
                            mch.modifyCSPoints(2, 数量, true);
                            break;
                        case 3:
                            类型 = "金币";
                            mch.gainMeso(数量, true);
                            break;
                        case 4:
                            类型 = "经验";
                            mch.gainExp(数量, false, false, false);
                            break;
                        case 5:
                            类型 = "人气";
                            mch.addFame(数量);
                            break;
                        case 6:
                            类型 = "豆豆";
                       //     mch.gainBeans(数量);
                            break;
                        default:
                            break;
                    }
                    mch.startMapEffect("管理员发放 " + 数量 + " " + 类型 + "给在线的所有玩家！", 5121009);
                }
            }
            JOptionPane.showMessageDialog(null, "[信息]:发放 " + 数量 + " " + 类型 + "给在线的所有玩家。");
            a1.setText("");
            JOptionPane.showMessageDialog(null, "发送成功");
        } else {
            JOptionPane.showMessageDialog(null, "[信息]:请输入要发送数量。");
        }
    }
    private void jButton36ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton36ActionPerformed
        openWindow(Windows.商店管理控制台);
    }//GEN-LAST:event_jButton36ActionPerformed

    private void jButton37ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton37ActionPerformed
        openWindow(Windows.怪物爆率控制台);
    }//GEN-LAST:event_jButton37ActionPerformed

    private void jButton50ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton50ActionPerformed
        openWindow(Windows.箱子爆率控制台);
    }//GEN-LAST:event_jButton50ActionPerformed

    private void jButton51ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton51ActionPerformed
        openWindow(Windows.批量删除工具);
    }//GEN-LAST:event_jButton51ActionPerformed

    private void jButton52ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton52ActionPerformed
        openWindow(Windows.游戏家族控制台);
    }//GEN-LAST:event_jButton52ActionPerformed

    private void jButton53ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton53ActionPerformed
        openWindow(Windows.MACip封禁);
    }//GEN-LAST:event_jButton53ActionPerformed

    private void adventurerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adventurerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_adventurerActionPerformed

    private void jButton54ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton54ActionPerformed
        openWindow(Windows.拍卖行控制台);
    }//GEN-LAST:event_jButton54ActionPerformed

    private void 账号搜索ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_账号搜索ActionPerformed
        if (jTextField23.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "请输入要搜索的账号。全部预览请输入0");
        } else if (jTextField23.getText().equals("0")) {
            ((DefaultTableModel) accountstable.getModel()).getDataVector().clear();
            ((DefaultTableModel) accountstable.getModel()).fireTableDataChanged();//通知模型更新
            accountstable.updateUI();//刷新表格
            inivalue.初始化账号表(0);
        } else {
            ((DefaultTableModel) accountstable.getModel()).getDataVector().clear();
            ((DefaultTableModel) accountstable.getModel()).fireTableDataChanged();//通知模型更新
            accountstable.updateUI();//刷新表格
            inivalue.初始化账号表(1);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_账号搜索ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        inivalue.账号操作(accid, 2, true);//0 修改  1删除   2解封   密码一样
        // TODO add your handling code here:
        ((DefaultTableModel) accountstable.getModel()).getDataVector().clear();
        ((DefaultTableModel) accountstable.getModel()).fireTableDataChanged();//通知模型更新

        accountstable.updateUI();//刷新表格
        inivalue.初始化账号表(0);
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        // TODO add your handling code here:
        if (accid == 0) {
            JOptionPane.showMessageDialog(null, "请选中账号后再执行该操作！");
        } else {
            if (输入1.getText().equals(mima)) {

                //判断一下当前得到列表里的密码是否和输入框的密码一样。避免重复加密
                inivalue.账号操作(accid, 0, true);//0 修改  1删除   2解封   密码一样
            } else {
                inivalue.账号操作(accid, 0, false);//0 修改  1删除   2解封   密码不一样
            }
            //刷新账号界面按钮
            ((DefaultTableModel) accountstable.getModel()).getDataVector().clear();
            ((DefaultTableModel) accountstable.getModel()).fireTableDataChanged();//通知模型更新

            accountstable.updateUI();//刷新表格
            inivalue.初始化账号表(0);
            accid = 0;
        }
    }//GEN-LAST:event_jButton19ActionPerformed

    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed
        inivalue.账号操作(accid, 1, true);//0 修改  1删除   2解封   密码一样
        // TODO add your handling code here:
        ((DefaultTableModel) accountstable.getModel()).getDataVector().clear();
        ((DefaultTableModel) accountstable.getModel()).fireTableDataChanged();//通知模型更新

        accountstable.updateUI();//刷新表格
        inivalue.初始化账号表(0);
    }//GEN-LAST:event_jButton20ActionPerformed

    private void jButton24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton24ActionPerformed
        //刷新账号界面按钮
        ((DefaultTableModel) accountstable.getModel()).getDataVector().clear();
        ((DefaultTableModel) accountstable.getModel()).fireTableDataChanged();//通知模型更新

        accountstable.updateUI();//刷新表格
        inivalue.初始化账号表(0);
        accid = 0;
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton24ActionPerformed

    private void jButton56ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton56ActionPerformed
        注册新账号();    
    }//GEN-LAST:event_jButton56ActionPerformed

    private void jButton30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton30ActionPerformed
        if (accid == 0) {
        JOptionPane.showMessageDialog(null, "请选中角色后再执行该操作！");
        return;        
        }
        inivalue.角色操作(accid, 0, true);//0 修改  1删除   2解封   密码一样
        // TODO add your handling code here:
        ((DefaultTableModel) characterstable.getModel()).getDataVector().clear();
        ((DefaultTableModel) characterstable.getModel()).fireTableDataChanged();//通知模型更新

        characterstable.updateUI();//刷新表格
        inivalue.初始化角色表(0);
    }//GEN-LAST:event_jButton30ActionPerformed

    private void jButton27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton27ActionPerformed
        ((DefaultTableModel) characterstable.getModel()).getDataVector().clear();
        ((DefaultTableModel) characterstable.getModel()).fireTableDataChanged();//通知模型更新
        characterstable.updateUI();//刷新表格
        inivalue.初始化角色表(1);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton27ActionPerformed

    private void jButton26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton26ActionPerformed
        ((DefaultTableModel) characterstable.getModel()).getDataVector().clear();
        ((DefaultTableModel) characterstable.getModel()).fireTableDataChanged();//通知模型更新
        characterstable.updateUI();//刷新表格
        inivalue.初始化角色表(0);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton26ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        inivalue.账号操作(accid, 3, true);//0 修改  1删除   2解封   密码一样
        // TODO add your handling code here:
        ((DefaultTableModel) accountstable.getModel()).getDataVector().clear();
        ((DefaultTableModel) accountstable.getModel()).fireTableDataChanged();//通知模型更新

        accountstable.updateUI();//刷新表格
        inivalue.初始化账号表(0);
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        inivalue.账号操作(accid, 4, true);//0 修改  1删除   2解封   密码一样
        // TODO add your handling code here:
        ((DefaultTableModel) accountstable.getModel()).getDataVector().clear();
        ((DefaultTableModel) accountstable.getModel()).fireTableDataChanged();//通知模型更新

        accountstable.updateUI();//刷新表格
        inivalue.初始化账号表(0);
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton35ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton35ActionPerformed
        if (accid == 0) {
        JOptionPane.showMessageDialog(null, "请选中角色后再执行该操作！");
        return;        
        }
        inivalue.角色删除(accid, 0, true);//0 修改  1删除   2解封   密码一样
        // TODO add your handling code here:
        ((DefaultTableModel) characterstable.getModel()).getDataVector().clear();
        ((DefaultTableModel) characterstable.getModel()).fireTableDataChanged();//通知模型更新

        characterstable.updateUI();//刷新表格
        inivalue.初始化角色表(0);
    }//GEN-LAST:event_jButton35ActionPerformed

    private void jButton61ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton61ActionPerformed
        MapleCharacter player = getSelectCharacter();
        if (player == null) {
            JOptionPane.showMessageDialog(null, "未选择角色或者选择的角色是离线状态或不存在。");
        } else {
            player.getClient().getSession().write(HexTool.getByteArrayFromHexString(jTextArea5.getText()));
            JOptionPane.showMessageDialog(null, "操作成功。");
        }
    }//GEN-LAST:event_jButton61ActionPerformed
    private MapleCharacter getSelectCharacter() {
        int val_targ;
        if (characterstable.getSelectedRow() == -1) {
            return null;
        } else if (characterstable.getValueAt(characterstable.getSelectedRow(), 0) == "离线") {
            return null;
        } else {
            val_targ = (Integer) characterstable.getValueAt(characterstable.getSelectedRow(), 1);
        }

        return MapleCharacter.getOnlineCharacterById(val_targ);
    }
    private void jButton62ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton62ActionPerformed
        MapleCharacter player = getSelectCharacter();
        if (player == null) {
            JOptionPane.showMessageDialog(null, "未选择角色或者选择的角色是离线状态或不存在。");
        } else {
            player.getClient().getSession().write(LoadPacket.getPacket());
            JOptionPane.showMessageDialog(null, "操作成功。");
        }
    }//GEN-LAST:event_jButton62ActionPerformed

    private void 开启三倍金币ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_开启三倍金币ActionPerformed
        boolean result1 = this.三倍金币持续时间.getText().matches("[0-9]+");
        if (result1) {
            if (三倍金币持续时间.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "持续时间不能为空");
                return;
            }
            int 原始金币 = Integer.parseInt(ServerProperties.getProperty("mesoRate"));
            int 三倍金币活动 = 原始金币 * 3;
            int seconds = 0;
            int mins = 0;
            int hours = Integer.parseInt(this.三倍金币持续时间.getText());
            int time = seconds + (mins * 60) + (hours * 60 * 60);
            final String rate = "金币";
            World.scheduleRateDelay(rate, time);
            for (ChannelServer cservs : ChannelServer.getAllInstances()) {
                cservs.setExpRate(三倍金币活动);
            }
            World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[倍率活动] : 游戏开始 3 倍打怪金币活动，将持续 " + hours + " 小时，请各位玩家狂欢吧！"));
           // cservs.broadcastPacket(MaplePacketCreator.serverNotice(6, "[系统公告]：金币倍率活动已经结束，已经恢复正常值。"));
            JOptionPane.showMessageDialog(null, "成功开启三倍金币活动，持续 " + hours + " 小时");
        } else {
            JOptionPane.showMessageDialog(null, "持续时间输入不正确");
        }
    }//GEN-LAST:event_开启三倍金币ActionPerformed

    private void 开启三倍爆率ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_开启三倍爆率ActionPerformed
        boolean result1 = this.三倍爆率持续时间.getText().matches("[0-9]+");
        if (result1) {
            if (三倍爆率持续时间.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "持续时间不能为空");
                return;
            }
            int 原始爆率 = Integer.parseInt(ServerProperties.getProperty("dropRate"));
            int 三倍爆率活动 = 原始爆率 * 3;
            int seconds = 0;
            int mins = 0;
            int hours = Integer.parseInt(this.三倍经验持续时间.getText());
            int time = seconds + (mins * 60) + (hours * 60 * 60);
            final String rate = "爆率";
            World.scheduleRateDelay(rate, time);
            for (ChannelServer cservs : ChannelServer.getAllInstances()) {
                cservs.setExpRate(三倍爆率活动);
            }
            World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[倍率活动] : 游戏开始 3 倍打怪爆率活动，将持续 " + hours + " 小时，请各位玩家狂欢吧！"));
            JOptionPane.showMessageDialog(null, "成功开启三倍爆率活动，持续 " + hours + " 小时");
        } else {
            JOptionPane.showMessageDialog(null, "持续时间输入不正确");
        }
    }//GEN-LAST:event_开启三倍爆率ActionPerformed

    private void 开启三倍经验ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_开启三倍经验ActionPerformed
        boolean result1 = this.三倍经验持续时间.getText().matches("[0-9]+");
        if (result1) {
            if (三倍经验持续时间.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "持续时间不能为空");
                return;
            }
            int 原始经验 = Integer.parseInt(ServerProperties.getProperty("expRate"));
            int 三倍经验活动 = 原始经验 * 3;
            int seconds = 0;
            int mins = 0;
            int hours = Integer.parseInt(this.三倍经验持续时间.getText());
            int time = seconds + (mins * 60) + (hours * 60 * 60);
            final String rate = "经验";
            World.scheduleRateDelay(rate, time);
            for (ChannelServer cservs : ChannelServer.getAllInstances()) {
                cservs.setExpRate(三倍经验活动);
            }
            World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[倍率活动] : 游戏开始 3 倍打怪经验活动，将持续 " + hours + " 小时，请各位玩家狂欢吧！"));
            JOptionPane.showMessageDialog(null, "成功开启三倍经验活动，持续 " + hours + " 小时");
        } else {
            JOptionPane.showMessageDialog(null, "持续时间输入不正确");
        }
    }//GEN-LAST:event_开启三倍经验ActionPerformed

    private void 开启双倍金币ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_开启双倍金币ActionPerformed
        boolean result1 = this.双倍金币持续时间.getText().matches("[0-9]+");
        if (result1) {
            if (双倍金币持续时间.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "持续时间不能为空");
                return;
            }
            int 原始金币 = Integer.parseInt(ServerProperties.getProperty("mesoRate"));
            int 双倍金币活动 = 原始金币 * 2;
            int seconds = 0;
            int mins = 0;
            int hours = Integer.parseInt(this.双倍金币持续时间.getText());
            int time = seconds + (mins * 60) + (hours * 60 * 60);
            final String rate = "金币";
            World.scheduleRateDelay(rate, time);
            for (ChannelServer cservs : ChannelServer.getAllInstances()) {
                cservs.setExpRate(双倍金币活动);
            }
            World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[倍率活动] : 游戏开始 2 倍打怪金币活动，将持续 " + hours + " 小时，请各位玩家狂欢吧！"));
            JOptionPane.showMessageDialog(null, "成功开启双倍金币活动，持续 " + hours + " 小时");
        } else {
            JOptionPane.showMessageDialog(null, "持续时间输入不正确");
        }
    }//GEN-LAST:event_开启双倍金币ActionPerformed

    private void 开启双倍爆率ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_开启双倍爆率ActionPerformed
        boolean result1 = this.双倍爆率持续时间.getText().matches("[0-9]+");
        if (result1) {
            if (双倍爆率持续时间.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "持续时间不能为空");
                return;
            }
            int 原始爆率 = Integer.parseInt(ServerProperties.getProperty("dropRate"));
            int 双倍爆率活动 = 原始爆率 * 2;
            int seconds = 0;
            int mins = 0;
            int hours = Integer.parseInt(this.双倍经验持续时间.getText());
            int time = seconds + (mins * 60) + (hours * 60 * 60);
            final String rate = "爆率";
            World.scheduleRateDelay(rate, time);
            for (ChannelServer cservs : ChannelServer.getAllInstances()) {
                cservs.setExpRate(双倍爆率活动);
            }
            World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[倍率活动] : 游戏开始 2 倍打怪爆率活动，将持续 " + hours + " 小时，请各位玩家狂欢吧！"));
            JOptionPane.showMessageDialog(null, "成功开启双倍爆率活动，持续 " + hours + " 小时");
        } else {
            JOptionPane.showMessageDialog(null, "持续时间输入不正确");
        }
    }//GEN-LAST:event_开启双倍爆率ActionPerformed

    private void 开启双倍经验ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_开启双倍经验ActionPerformed
        boolean result1 = this.双倍经验持续时间.getText().matches("[0-9]+");
        if (result1) {
            if (双倍经验持续时间.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "持续时间不能为空");
                return;
            }
            int 原始经验 = Integer.parseInt(ServerProperties.getProperty("expRate"));
            int 双倍经验活动 = 原始经验 * 2;
            int seconds = 0;
            int mins = 0;
            int hours = Integer.parseInt(this.双倍经验持续时间.getText());
            int time = seconds + (mins * 60) + (hours * 60 * 60);
            final String rate = "经验";
            World.scheduleRateDelay(rate, time);
            for (ChannelServer cservs : ChannelServer.getAllInstances()) {
                cservs.setExpRate(双倍经验活动);
            }
            World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[倍率活动] : 游戏开始 2 倍打怪经验活动，将持续 " + hours + " 小时，请各位玩家狂欢吧！"));
            JOptionPane.showMessageDialog(null, "成功开启双倍经验活动，持续 " + hours + " 小时");
        } else {
            JOptionPane.showMessageDialog(null, "持续时间输入不正确");
        }
    }//GEN-LAST:event_开启双倍经验ActionPerformed

    private void jButton76ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton76ActionPerformed
        runTool(Tools.FixCharSets);
    }//GEN-LAST:event_jButton76ActionPerformed

    private void 给予物品1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_给予物品1ActionPerformed
        刷物品2();    // TODO add your handling code here:
    }//GEN-LAST:event_给予物品1ActionPerformed

    private void 个人发送物品玩家名字ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_个人发送物品玩家名字ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_个人发送物品玩家名字ActionPerformed

    private void 个人发送物品代码ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_个人发送物品代码ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_个人发送物品代码ActionPerformed

    private void 个人发送物品数量ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_个人发送物品数量ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_个人发送物品数量ActionPerformed

    private void 给予物品ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_给予物品ActionPerformed
        刷物品();       // TODO add your handling code here:
    }//GEN-LAST:event_给予物品ActionPerformed

    private void z7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_z7ActionPerformed
        个人发送福利(2);
    }//GEN-LAST:event_z7ActionPerformed

    private void z8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_z8ActionPerformed
        个人发送福利(3);
    }//GEN-LAST:event_z8ActionPerformed

    private void z9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_z9ActionPerformed
        个人发送福利(1);
    }//GEN-LAST:event_z9ActionPerformed

    private void z10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_z10ActionPerformed
        个人发送福利(4);
    }//GEN-LAST:event_z10ActionPerformed

    private void z11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_z11ActionPerformed
        个人发送福利(5);
    }//GEN-LAST:event_z11ActionPerformed

    private void z12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_z12ActionPerformed
        个人发送福利(6);
    }//GEN-LAST:event_z12ActionPerformed

    private void a2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_a2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_a2ActionPerformed

    private void 个人发送物品玩家名字1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_个人发送物品玩家名字1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_个人发送物品玩家名字1ActionPerformed

    private void z2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_z2ActionPerformed
        发送福利(2);
    }//GEN-LAST:event_z2ActionPerformed

    private void z3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_z3ActionPerformed
        发送福利(3);
    }//GEN-LAST:event_z3ActionPerformed

    private void z1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_z1ActionPerformed
        发送福利(1);        // TODO add your handling code here:
    }//GEN-LAST:event_z1ActionPerformed

    private void z4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_z4ActionPerformed
        发送福利(4);
    }//GEN-LAST:event_z4ActionPerformed

    private void z5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_z5ActionPerformed
        发送福利(5);
    }//GEN-LAST:event_z5ActionPerformed

    private void z6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_z6ActionPerformed
        发送福利(6);
    }//GEN-LAST:event_z6ActionPerformed

    private void a1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_a1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_a1ActionPerformed

    private void 全服发送装备装备加卷ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_全服发送装备装备加卷ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_全服发送装备装备加卷ActionPerformed

    private void 全服发送装备装备制作人ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_全服发送装备装备制作人ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_全服发送装备装备制作人ActionPerformed

    private void 全服发送装备装备力量ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_全服发送装备装备力量ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_全服发送装备装备力量ActionPerformed

    private void 全服发送装备装备MPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_全服发送装备装备MPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_全服发送装备装备MPActionPerformed

    private void 全服发送装备装备智力ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_全服发送装备装备智力ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_全服发送装备装备智力ActionPerformed

    private void 全服发送装备装备运气ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_全服发送装备装备运气ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_全服发送装备装备运气ActionPerformed

    private void 全服发送装备装备HPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_全服发送装备装备HPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_全服发送装备装备HPActionPerformed

    private void 全服发送装备装备攻击力ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_全服发送装备装备攻击力ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_全服发送装备装备攻击力ActionPerformed

    private void 全服发送装备装备给予时间ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_全服发送装备装备给予时间ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_全服发送装备装备给予时间ActionPerformed

    private void 全服发送装备装备可否交易ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_全服发送装备装备可否交易ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_全服发送装备装备可否交易ActionPerformed

    private void 全服发送装备装备敏捷ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_全服发送装备装备敏捷ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_全服发送装备装备敏捷ActionPerformed

    private void 全服发送装备物品IDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_全服发送装备物品IDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_全服发送装备物品IDActionPerformed

    private void 全服发送装备装备魔法力ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_全服发送装备装备魔法力ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_全服发送装备装备魔法力ActionPerformed

    private void 全服发送装备装备魔法防御ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_全服发送装备装备魔法防御ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_全服发送装备装备魔法防御ActionPerformed

    private void 全服发送装备装备物理防御ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_全服发送装备装备物理防御ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_全服发送装备装备物理防御ActionPerformed

    private void 给予装备1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_给予装备1ActionPerformed
        刷装备2(2);        // TODO add your handling code here:
    }//GEN-LAST:event_给予装备1ActionPerformed

    private void 发送装备玩家姓名ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_发送装备玩家姓名ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_发送装备玩家姓名ActionPerformed

    private void 给予装备2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_给予装备2ActionPerformed
        刷装备2(1);
    }//GEN-LAST:event_给予装备2ActionPerformed

    private void 全服发送装备装备潜能3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_全服发送装备装备潜能3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_全服发送装备装备潜能3ActionPerformed

    private void 全服发送装备装备潜能1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_全服发送装备装备潜能1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_全服发送装备装备潜能1ActionPerformed

    private void 全服发送装备装备潜能2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_全服发送装备装备潜能2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_全服发送装备装备潜能2ActionPerformed

    private void qiannengdaimaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_qiannengdaimaActionPerformed
        JOptionPane.showMessageDialog(null, "潜能代码如下\r\n30601 boss+20% \r\n30602 boss+30% \r\n30291 无视防御30% \r\n30070 总伤害+9% \r\n30051 攻击力+9% \r\n30052 魔法力+9% \r\n30053 物防+9% \r\n30054 魔防+9% \r\n30041 力量+9% \r\n20041 力量+6%\r\n10041 力量+3%\r\n30042 敏捷+9%\r\n20042 敏捷+6%\r\n10042 敏捷+3% \r\n30043 智力+9%\r\n20043 智力+6%\r\n10043 智力+%3 \r\n30044 运气+9%\r\n20044 运气+6%\r\n10044 运气+3%\r\n30045 HP+9% \r\n30047 命中+9% \r\n30048 回避+9% \r\n20086 所有属性3%\r\n30086 所有属性+6%\r\n\r\n更多的自行测试 例:9%和6%代码第一位减1");
    }//GEN-LAST:event_qiannengdaimaActionPerformed

    private void 装备星星1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_装备星星1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_装备星星1ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed

        JOptionPane.showMessageDialog(null, "放弃修改成功。");
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed

        JOptionPane.showMessageDialog(null, "保存数据成功。");
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed

        JOptionPane.showMessageDialog(null, "读取数据成功。");
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jTextField22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField22ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField22ActionPerformed

    private void IPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IPActionPerformed

    }//GEN-LAST:event_IPActionPerformed

    private void CollegeSystemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CollegeSystemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CollegeSystemActionPerformed

    private void ForgingsystemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ForgingsystemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ForgingsystemActionPerformed

    private void PetsarenothungryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PetsarenothungryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PetsarenothungryActionPerformed

    private void wirelessbuffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wirelessbuffActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_wirelessbuffActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    设置GM(1);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
    设置GM(0);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed
    public void 设置GM(int type)
   {
     if (this.角色0.getText().length() < 4) {
       JOptionPane.showMessageDialog(null, "请选择要调整的账号！", "提示", 0);
     }
     else {
       try {
         Connection con = DBConPool.getInstance().getDataSource().getConnection().getConnection();
         PreparedStatement ps = null;
         ps = con.prepareStatement("Update characters set gm = ? Where name = ?");
         ps.setInt(1, type == 0 ? 0 : 100);
         ps.setString(2, 角色0.getText());
         ps.execute();
         ps.close();
       } catch (Exception ex) {
         JOptionPane.showMessageDialog(null, "错误!\r\n" + ex);
       }
       JOptionPane.showMessageDialog(null, "调整成功！", "提示", 1);
      // 刷新账号信息(0);
        ((DefaultTableModel) characterstable.getModel()).getDataVector().clear();
        ((DefaultTableModel) characterstable.getModel()).fireTableDataChanged();//通知模型更新
        characterstable.updateUI();//刷新表格
        inivalue.初始化角色表(0);
     }
   }
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
    卡角色清理(1);//解卡头脸
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
    卡角色清理(0);//解卡物品
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
    卡角色清理(3);//解卡登录
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
    卡角色清理(2);//解卡家族
    }//GEN-LAST:event_jButton7ActionPerformed
    public void 卡角色清理(int type) {
     PreparedStatement ps = null;
     if (this.角色0.getText().length() <= 1) {
       JOptionPane.showMessageDialog(null, "请选择你要解救的角色！");
       return;
     }
    
 
     int n = JOptionPane.showConfirmDialog(this, "你确定要解救这个角色吗？\n对应的解救方式是初始化数据，或者强制掉线！\n请谨慎操作！", "信息", 0);
     if (n != 0) {
       return;
     }
     try
     {
       switch (type) {
       case 0: //解卡物品
         ps = DBConPool.getInstance().getDataSource().getConnection().getConnection().prepareStatement("delete from inventoryitems where characterid = ?");
         ps.setInt(1, Integer.parseInt(ZeroMS_UI.角色ID.getText()));
         ps.execute();
         ps.close();
         break;
       case 1: //解卡头脸
         ps = DBConPool.getInstance().getDataSource().getConnection().getConnection().prepareStatement("UPDATE characters SET hair = ?,face = ? where id = ?");
         ps.setInt(1, 30000);
         ps.setInt(2, 20000);
         ps.setInt(3, Integer.parseInt(ZeroMS_UI.角色ID.getText()));
         ps.execute();
         ps.close();
         break;
       case 2: 
         ps = DBConPool.getInstance().getDataSource().getConnection().getConnection().prepareStatement("UPDATE characters SET guildid = ?,guildrank = ?,allianceRank = ? where id = ?");
         ps.setInt(1, 0);
         ps.setInt(2, 5);
         ps.setInt(3, 5);
         ps.setInt(4, Integer.parseInt(ZeroMS_UI.角色ID.getText()));
         ps.execute();
         ps.close();
         break;
       case 3: 
         for (ChannelServer cserv : ChannelServer.getAllInstances()) {
           for (MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
             if (chr != null)
             {
 
 
               if (chr.getName().contains(角色0.getText())) {
                 chr.getClient().disconnect(true, false);
                 chr.getClient().getSession().close();
               }
             }
           }
         }
       }
       
       
 
       JOptionPane.showMessageDialog(null, "处理成功！对应卡死已解救完毕！", "提示", 1);
   //    刷新角色信息(0);
        ((DefaultTableModel) characterstable.getModel()).getDataVector().clear();
        ((DefaultTableModel) characterstable.getModel()).fireTableDataChanged();//通知模型更新
        characterstable.updateUI();//刷新表格
        inivalue.初始化角色表(0);
     } catch (SQLException ex) {
       System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + "]有错误!\r\n" + ex);
     }
   }
    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        键盘技能处理(1);//还原键盘技能
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        键盘技能处理(0);//还原键盘技能
    }//GEN-LAST:event_jButton9ActionPerformed
   public void 键盘技能处理(int type) { PreparedStatement ps = null;
     try
     {
       switch (type) {
       case 0: 
         ps = DBConPool.getInstance().getDataSource().getConnection().getConnection().prepareStatement(" delete from keymap_backup where characterid = ?");
         ps.setInt(1, Integer.parseInt(ZeroMS_UI.角色ID.getText()));
         ps.execute();
         ps.close();
         
         ps = DBConPool.getInstance().getDataSource().getConnection().getConnection().prepareStatement(" insert into keymap_backup select * from keymap where characterid = ?");
         ps.setInt(1, Integer.parseInt(ZeroMS_UI.角色ID.getText()));
         ps.execute();
         ps.close();
         JOptionPane.showMessageDialog(null, "保存成功！可以确认数据库数据了！", "提示", 1);
         break;
       case 1: 
         ps = DBConPool.getInstance().getDataSource().getConnection().getConnection().prepareStatement(" delete from keymap where characterid = ?");
         ps.setInt(1, Integer.parseInt(ZeroMS_UI.角色ID.getText()));
         ps.execute();
         ps.close();
         
         ps = DBConPool.getInstance().getDataSource().getConnection().getConnection().prepareStatement(" insert into keymap select * from keymap_backup where characterid = ?");
          ps.setInt(1, Integer.parseInt(ZeroMS_UI.角色ID.getText()));
         ps.execute();
         ps.close();
         JOptionPane.showMessageDialog(null, "还原成功！可以确认键盘数据了！", "提示", 1);
        }
       
       
        ((DefaultTableModel) characterstable.getModel()).getDataVector().clear();
        ((DefaultTableModel) characterstable.getModel()).fireTableDataChanged();//通知模型更新
        characterstable.updateUI();//刷新表格
        inivalue.初始化角色表(0);
     } catch (SQLException ex) {
       System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + "]有错误!\r\n" + ex);
     }
   }
     
    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
     读取角色背包();
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton28ActionPerformed
     读取角色技能();
    }//GEN-LAST:event_jButton28ActionPerformed

    private void jButton33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton33ActionPerformed
     在线角色处理(0);//强制下线
    }//GEN-LAST:event_jButton33ActionPerformed

    private void jButton38ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton38ActionPerformed
     在线角色处理(1);//封号T下线
    }//GEN-LAST:event_jButton38ActionPerformed

    private void jButton40ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton40ActionPerformed
     在线角色处理(2);//传送自由
    }//GEN-LAST:event_jButton40ActionPerformed

    private void jButton48ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton48ActionPerformed
     在线角色处理(3);//传送地图
    }//GEN-LAST:event_jButton48ActionPerformed

    private void jButton55ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton55ActionPerformed
     在线角色处理(4);//修改脸型
    }//GEN-LAST:event_jButton55ActionPerformed

    private void jButton57ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton57ActionPerformed
     在线角色处理(5);//修改发型
    }//GEN-LAST:event_jButton57ActionPerformed

    private void jButton58ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton58ActionPerformed
     在线角色处理(6);//加满技能
    }//GEN-LAST:event_jButton58ActionPerformed

    private void jButton59ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton59ActionPerformed
     在线角色处理(7);//开技能栏
    }//GEN-LAST:event_jButton59ActionPerformed

    private void jButton60ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton60ActionPerformed
     在线角色处理(8);//杀死玩家
    }//GEN-LAST:event_jButton60ActionPerformed

    private void jButton63ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton63ActionPerformed
     在线角色处理(9);//关小黑屋
    }//GEN-LAST:event_jButton63ActionPerformed

    private void jButton64ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton64ActionPerformed
     全员下线();
    }//GEN-LAST:event_jButton64ActionPerformed
    public void 在线角色处理(int type) {
     boolean success = false;
    
     if (角色0.getText().length() <= 1) {
     JOptionPane.showMessageDialog(null, "请选择你要处理的角色！"); return; }
     String id;
     switch (type) {
     case 0: 
       int answer = JOptionPane.showConfirmDialog(this, "你确定要踢[" + 角色0.getText() + "]下线吗？", "信息", 0);
       if (answer != 0) {
         return;
       }
       for (ChannelServer cserv : ChannelServer.getAllInstances()) {
         for (MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
           if (chr != null)
           {
 

             if (chr.getName().contains(角色0.getText())) {
               chr.getClient().disconnect(true, false);
               chr.getClient().getSession().close();
               success = true;
             }
           }
         }
       }
       break;
     case 1: //封号+提下线
  /*     int n = JOptionPane.showConfirmDialog(this, "你确定要封[" + this.角色0.getText() + "]的掉落并踢下线吗？", "信息", 0);
       if (n == 0) {
           
         return;
       }*/
       for (ChannelServer cserv : ChannelServer.getAllInstances()) {
         for (MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
           if (chr != null)
           {
 
 
             if (chr.getName().contains(this.角色0.getText()))
             {
               chr.getClient().disconnect(true, false);
               chr.getClient().getSession().close();
               success = true;
             }
           }
         }
       }
       break;
     case 2: //传送自由
  /*     int answer = JOptionPane.showConfirmDialog(this, "你确定要把[" + this.角色昵称.getText() + "]传送到自由市场吗？", "信息", 0);
       if (answer != 0) {
         return;
       }*/
       for (ChannelServer cserv : ChannelServer.getAllInstances()) {
         for (MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
           if (chr != null)
           {
 

             if (chr.getName().contains(this.角色0.getText())) {

               success = true;
             }
           }
         }
       }
       break;
     case 4: //改变脸型
       id = JOptionPane.showInputDialog(this, "输入你要更改[" + this.角色0.getText() + "]的脸型ID！", "信息", 2);
       for (ChannelServer cserv : ChannelServer.getAllInstances()) {
         for (MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
           if (chr != null)
           {
 
 
             if (chr.getName().contains(this.角色0.getText())) {
               chr.setFace(Integer.parseInt(id));
               success = true;
             }
           }
         }
       }
       break;
     case 5: //改变发型
       id = JOptionPane.showInputDialog(this, "输入你要更改[" + this.角色0.getText() + "]的发型ID！", "信息", 2);
       for (ChannelServer cserv : ChannelServer.getAllInstances()) {
         for (MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
           if (chr != null)
           {
 
 
             if (chr.getName().contains(this.角色0.getText())) {
               chr.setHair(Integer.parseInt(id));
               success = true;
             }
           }
         }
       }
       break;
     case 6: //满技能
       for (ChannelServer cserv : ChannelServer.getAllInstances()) {
         for (MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
           if (chr != null)
           {
 
 
             if (chr.getName().contains(this.角色0.getText())) {
               chr.maxSkills();
               success = true;
             }
           }
         }
       }
       break;

     case 8: //击杀角色
       for (ChannelServer cserv : ChannelServer.getAllInstances()) {
         for (MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
           if (chr != null)
           {
 
 
             if (chr.getName().contains(this.角色0.getText())) {
               chr.startMapEffect("不友好之人！承受死亡的痛苦吧！", 5120027);
               
               chr.setHp(0);
               chr.updateSingleStat(MapleStat.HP, 0);
               success = true;
             }
           }
         }
       }
       break;
     case 9: //关小黑屋
       for (ChannelServer cserv : ChannelServer.getAllInstances()) {
         for (MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
           if (chr != null)
           {
 
 
             if (chr.getName().contains(this.角色0.getText())) {

               success = true;
             }
           }
         }
       }
     }
     if (success) JOptionPane.showMessageDialog(null, "处理成功！", "提示", 1); else JOptionPane.showMessageDialog(null, "处理失败！角色没有在线或者不符合要求！", "提示", 1);
   }
   
   public void 全员下线() {
     int answer = JOptionPane.showConfirmDialog(this, "你确定要将所有玩家踢下线吗？", "信息", 0);
     if (answer != 0) {
       return;
     }
     
     for (ChannelServer cserv : ChannelServer.getAllInstances()) {
       for (MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
         if (chr != null)
         {
 
           chr.getClient().disconnect(true, false);
           chr.getClient().getSession().close();
         }
       }
     }
     
     JOptionPane.showMessageDialog(null, "处理成功！", "提示", 1);
   }
    private void jButton34ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton34ActionPerformed
     修改角色背包(0);//修改选中行
    }//GEN-LAST:event_jButton34ActionPerformed

    private void jButton65ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton65ActionPerformed
     修改角色背包(1);//删除选中行
    }//GEN-LAST:event_jButton65ActionPerformed
   public void 读取角色背包() {
     if (this.角色0.getText().length() <= 1) {
       JOptionPane.showMessageDialog(null, "请选择你要读取的角色！");
       return;
/*      */     }
/*      */     
/*      */ 
/* 5810 */     for (int i = ((DefaultTableModel)this.身上.getModel()).getRowCount() - 1; i >= 0; i--) {
/* 5811 */       ((DefaultTableModel)this.身上.getModel()).removeRow(i);
/*      */     }
/* 5813 */     for (int i = ((DefaultTableModel)this.装备.getModel()).getRowCount() - 1; i >= 0; i--) {
/* 5814 */       ((DefaultTableModel)this.装备.getModel()).removeRow(i);
/*      */     }
/* 5816 */     for (int i = ((DefaultTableModel)this.消耗.getModel()).getRowCount() - 1; i >= 0; i--) {
/* 5817 */       ((DefaultTableModel)this.消耗.getModel()).removeRow(i);
/*      */     }
/* 5819 */     for (int i = ((DefaultTableModel)this.设置.getModel()).getRowCount() - 1; i >= 0; i--) {
/* 5820 */       ((DefaultTableModel)this.设置.getModel()).removeRow(i);
/*      */     }
/* 5822 */     for (int i = ((DefaultTableModel)this.其他.getModel()).getRowCount() - 1; i >= 0; i--) {
/* 5823 */       ((DefaultTableModel)this.其他.getModel()).removeRow(i);
/*      */     }
/* 5825 */     for (int i = ((DefaultTableModel)this.特殊.getModel()).getRowCount() - 1; i >= 0; i--) {
/* 5826 */       ((DefaultTableModel)this.特殊.getModel()).removeRow(i);
/*      */     }
/* 5828 */     for (int i = ((DefaultTableModel)this.仓库.getModel()).getRowCount() - 1; i >= 0; i--) {
/* 5829 */       ((DefaultTableModel)this.仓库.getModel()).removeRow(i);
/*      */     }
/* 5831 */     for (int i = ((DefaultTableModel)this.商城.getModel()).getRowCount() - 1; i >= 0; i--) {
/* 5832 */       ((DefaultTableModel)this.商城.getModel()).removeRow(i);
/*      */     }
/*      */     try
/*      */     {
/* 5836 */       Connection con = DBConPool.getInstance().getDataSource().getConnection().getConnection();
/* 5837 */       PreparedStatement ps = null;
/*      */       
/* 5839 */       ResultSet rs = null;
/* 5840 */       ps = con.prepareStatement("SELECT * FROM inventoryitems WHERE characterid = ?");
                 ps.setInt(1, Integer.parseInt(ZeroMS_UI.角色ID.getText()));
/* 5842 */       rs = ps.executeQuery();
/* 5843 */       while (rs.next()) {
                   int itemId = rs.getInt("itemid");
/* 5844 */         switch (rs.getInt("inventorytype")) {
/*      */         case -1: 
/* 5846 */           ((DefaultTableModel)this.身上.getModel()).insertRow(this.身上.getRowCount(), new Object[] {
/* 5847 */             Integer.valueOf(rs.getInt("inventoryitemid")), 
/* 5848 */             MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid")), 
/* 5849 */             Integer.valueOf(rs.getInt("itemid")), 
/* 5850 */             Integer.valueOf(rs.getInt("quantity")), 
                       itemId,
                       });
/* 5851 */           break;
/*      */         case 1: 
/* 5853 */           ((DefaultTableModel)this.装备.getModel()).insertRow(this.装备.getRowCount(), new Object[] {
/* 5854 */             Integer.valueOf(rs.getInt("inventoryitemid")), 
/* 5855 */             MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid")), 
/* 5856 */             Integer.valueOf(rs.getInt("itemid")), 
/* 5850 */             Integer.valueOf(rs.getInt("quantity")), 
                       itemId,
                       });
/* 5858 */           break;
/*      */         case 2: 
/* 5860 */           ((DefaultTableModel)this.消耗.getModel()).insertRow(this.消耗.getRowCount(), new Object[] {
/* 5861 */             Integer.valueOf(rs.getInt("inventoryitemid")), 
/* 5862 */             MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid")), 
/* 5863 */             Integer.valueOf(rs.getInt("itemid")), 
/* 5850 */             Integer.valueOf(rs.getInt("quantity")), 
                       itemId,
                       });
/* 5865 */           break;
/*      */         case 3: 
/* 5867 */           ((DefaultTableModel)this.设置.getModel()).insertRow(this.设置.getRowCount(), new Object[] {
/* 5868 */             Integer.valueOf(rs.getInt("inventoryitemid")), 
/* 5869 */             MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid")), 
/* 5870 */             Integer.valueOf(rs.getInt("itemid")), 
/* 5850 */             Integer.valueOf(rs.getInt("quantity")), 
                       itemId,
                       });
/* 5872 */           break;
/*      */         case 4: 
/* 5874 */           ((DefaultTableModel)this.其他.getModel()).insertRow(this.其他.getRowCount(), new Object[] {
/* 5875 */             Integer.valueOf(rs.getInt("inventoryitemid")), 
/* 5876 */             MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid")), 
/* 5877 */             Integer.valueOf(rs.getInt("itemid")), 
/* 5850 */             Integer.valueOf(rs.getInt("quantity")), 
                       itemId,
                       });
/* 5879 */           break;
/*      */         case 5: 
/* 5881 */           ((DefaultTableModel)this.特殊.getModel()).insertRow(this.特殊.getRowCount(), new Object[] {
/* 5882 */             Integer.valueOf(rs.getInt("inventoryitemid")), 
/* 5883 */             MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid")), 
/* 5884 */             Integer.valueOf(rs.getInt("itemid")), 
/* 5850 */             Integer.valueOf(rs.getInt("quantity")), 
                       itemId,
                       });
/*      */         }
/*      */         
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 5892 */       int accid = -1;
/* 5893 */       ps = con.prepareStatement("SELECT * FROM characters WHERE id = ?");
                 ps.setInt(1, Integer.parseInt(ZeroMS_UI.角色ID.getText()));
/* 5895 */       rs = ps.executeQuery();
/* 5896 */       while (rs.next()) {
/* 5897 */         accid = rs.getInt("accountid");
/*      */       }
/*      */       
/* 5900 */       ps = con.prepareStatement("SELECT * FROM inventoryitems WHERE accountid = ?");
/* 5901 */       ps.setInt(1, accid);
/* 5902 */       rs = ps.executeQuery();
/* 5903 */       while (rs.next()) {
                    int itemId = rs.getInt("itemid");
/* 5904 */         ((DefaultTableModel)this.仓库.getModel()).insertRow(this.仓库.getRowCount(), new Object[] {
/* 5905 */           Integer.valueOf(rs.getInt("inventoryitemid")), 
/* 5906 */           MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid")), 
/* 5907 */           Integer.valueOf(rs.getInt("itemid")), 
/* 5850 */             Integer.valueOf(rs.getInt("quantity")), 
                       itemId,
                       });
/*      */       }
/*      */       
/*      */ 
/* 5912 */       ps = con.prepareStatement("SELECT * FROM csitems WHERE accountid = ?");
/* 5913 */       ps.setInt(1, accid);
/* 5914 */       rs = ps.executeQuery();
/* 5915 */       while (rs.next()) {
                   int itemId = rs.getInt("itemid");
/* 5916 */         ((DefaultTableModel)this.商城.getModel()).insertRow(this.商城.getRowCount(), new Object[] {
/* 5917 */           Integer.valueOf(rs.getInt("inventoryitemid")), 
/* 5918 */           MapleItemInformationProvider.getInstance().getName(rs.getInt("itemid")), 
/* 5919 */           Integer.valueOf(rs.getInt("itemid")), 
/* 5850 */             Integer.valueOf(rs.getInt("quantity")), 
                       itemId,
                       });
/*      */       }
/*      */       
/* 5923 */       ps.close();
/* 5924 */       rs.close();
/*      */     } catch (SQLException ex) {
/* 5926 */       System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + "]有错误!\r\n" + ex);
/*      */     }
/*      */     
/* 5929 */     JOptionPane.showMessageDialog(null, "读取速度因角色数据量而异！\n读取完毕！可前往查看！", "提示", 1);
/*      */   }
    
    /* 5932 */   public void 修改角色背包(int type) { Connection con = null;
/* 5933 */     PreparedStatement ps = null;
/* 5934 */     int i = -1;
/*      */     try
/*      */     {
/* 5937 */       con = DBConPool.getInstance().getDataSource().getConnection();
/* 5938 */       switch (type)
/*      */       {
/*      */       case 0: 
/*      */         break;
/*      */       }
/*      */       
/* 5944 */       switch (this.资源页签.getSelectedIndex()) {
/*      */       case 0: 
/* 5946 */         i = this.身上.getSelectedRow();
/* 5947 */         if (i < 0) {
/* 5948 */           JOptionPane.showMessageDialog(null, "没有选中行！操作错误！", "提示", 1);
/* 5949 */           return;
/*      */         }
/* 5951 */         if (type == 0) {
/* 5952 */           ps = con.prepareStatement("update inventoryitems set itemid = ?,quantity = ? WHERE inventoryitemid = ?");
/* 5953 */           ps.setInt(1, Integer.parseInt(this.身上.getValueAt(i, 2).toString()));
/* 5954 */           ps.setInt(2, Integer.parseInt(this.身上.getValueAt(i, 3).toString()));
/* 5955 */           ps.setInt(3, Integer.parseInt(this.身上.getValueAt(i, 0).toString()));
/* 5956 */           ps.execute();
/* 5957 */           ps.close();
/*      */         }
/* 5959 */         if (type == 1) {
/* 5960 */           ps = con.prepareStatement("delete from inventoryitems WHERE inventoryitemid = ?");
/* 5961 */           ps.setInt(1, Integer.parseInt(this.身上.getValueAt(i, 0).toString()));
/* 5962 */           ps.execute();
/* 5963 */           ps.close();
/* 5964 */           ((DefaultTableModel)this.身上.getModel()).removeRow(i);
/*      */         }
/*      */         
/*      */         break;
/*      */       case 1: 
/* 5969 */         i = this.装备.getSelectedRow();
/* 5970 */         if (i < 0) {
/* 5971 */           JOptionPane.showMessageDialog(null, "没有选中行！操作错误！", "提示", 1);
/* 5972 */           return;
/*      */         }
/* 5974 */         if (type == 0) {
/* 5975 */           ps = con.prepareStatement("update inventoryitems set itemid = ?,quantity = ? WHERE inventoryitemid = ?");
/* 5976 */           ps.setInt(1, Integer.parseInt(this.装备.getValueAt(i, 2).toString()));
/* 5977 */           ps.setInt(2, Integer.parseInt(this.装备.getValueAt(i, 3).toString()));
/* 5978 */           ps.setInt(3, Integer.parseInt(this.装备.getValueAt(i, 0).toString()));
/* 5979 */           ps.execute();
/* 5980 */           ps.close();
/*      */         }
/* 5982 */         if (type == 1) {
/* 5983 */           ps = con.prepareStatement("delete from inventoryitems WHERE inventoryitemid = ?");
/* 5984 */           ps.setInt(1, Integer.parseInt(this.装备.getValueAt(i, 0).toString()));
/* 5985 */           ps.execute();
/* 5986 */           ps.close();
/* 5987 */           ((DefaultTableModel)this.装备.getModel()).removeRow(i);
/*      */         }
/*      */         
/*      */         break;
/*      */       case 2: 
/* 5992 */         i = this.消耗.getSelectedRow();
/* 5993 */         if (i < 0) {
/* 5994 */           JOptionPane.showMessageDialog(null, "没有选中行！操作错误！", "提示", 1);
/* 5995 */           return;
/*      */         }
/* 5997 */         if (type == 0) {
/* 5998 */           ps = con.prepareStatement("update inventoryitems set itemid = ?,quantity = ? WHERE inventoryitemid = ?");
/* 5999 */           ps.setInt(1, Integer.parseInt(this.消耗.getValueAt(i, 2).toString()));
/* 6000 */           ps.setInt(2, Integer.parseInt(this.消耗.getValueAt(i, 3).toString()));
/* 6001 */           ps.setInt(3, Integer.parseInt(this.消耗.getValueAt(i, 0).toString()));
/* 6002 */           ps.execute();
/* 6003 */           ps.close();
/*      */         }
/* 6005 */         if (type == 1) {
/* 6006 */           ps = con.prepareStatement("delete from inventoryitems WHERE inventoryitemid = ?");
/* 6007 */           ps.setInt(1, Integer.parseInt(this.消耗.getValueAt(i, 0).toString()));
/* 6008 */           ps.execute();
/* 6009 */           ps.close();
/* 6010 */           ((DefaultTableModel)this.消耗.getModel()).removeRow(i);
/*      */         }
/*      */         
/*      */         break;
/*      */       case 3: 
/* 6015 */         i = this.设置.getSelectedRow();
/* 6016 */         if (i < 0) {
/* 6017 */           JOptionPane.showMessageDialog(null, "没有选中行！操作错误！", "提示", 1);
/* 6018 */           return;
/*      */         }
/* 6020 */         if (type == 0) {
/* 6021 */           ps = con.prepareStatement("update inventoryitems set itemid = ?,quantity = ? WHERE inventoryitemid = ?");
/* 6022 */           ps.setInt(1, Integer.parseInt(this.设置.getValueAt(i, 2).toString()));
/* 6023 */           ps.setInt(2, Integer.parseInt(this.设置.getValueAt(i, 3).toString()));
/* 6024 */           ps.setInt(3, Integer.parseInt(this.设置.getValueAt(i, 0).toString()));
/* 6025 */           ps.execute();
/* 6026 */           ps.close();
/*      */         }
/* 6028 */         if (type == 1) {
/* 6029 */           ps = con.prepareStatement("delete from inventoryitems WHERE inventoryitemid = ?");
/* 6030 */           ps.setInt(1, Integer.parseInt(this.设置.getValueAt(i, 0).toString()));
/* 6031 */           ps.execute();
/* 6032 */           ps.close();
/* 6033 */           ((DefaultTableModel)this.设置.getModel()).removeRow(i);
/*      */         }
/*      */         break;
/*      */       case 4: 
/* 6037 */         i = this.其他.getSelectedRow();
/* 6038 */         if (i < 0) {
/* 6039 */           JOptionPane.showMessageDialog(null, "没有选中行！操作错误！", "提示", 1);
/* 6040 */           return;
/*      */         }
/* 6042 */         if (type == 0) {
/* 6043 */           ps = con.prepareStatement("update inventoryitems set itemid = ?,quantity = ? WHERE inventoryitemid = ?");
/* 6044 */           ps.setInt(1, Integer.parseInt(this.其他.getValueAt(i, 2).toString()));
/* 6045 */           ps.setInt(2, Integer.parseInt(this.其他.getValueAt(i, 3).toString()));
/* 6046 */           ps.setInt(3, Integer.parseInt(this.其他.getValueAt(i, 0).toString()));
/* 6047 */           ps.execute();
/* 6048 */           ps.close();
/*      */         }
/* 6050 */         if (type == 1) {
/* 6051 */           ps = con.prepareStatement("delete from inventoryitems WHERE inventoryitemid = ?");
/* 6052 */           ps.setInt(1, Integer.parseInt(this.其他.getValueAt(i, 0).toString()));
/* 6053 */           ps.execute();
/* 6054 */           ps.close();
/* 6055 */           ((DefaultTableModel)this.其他.getModel()).removeRow(i);
/*      */         }
/*      */         break;
/*      */       case 5: 
/* 6059 */         i = this.特殊.getSelectedRow();
/* 6060 */         if (i < 0) {
/* 6061 */           JOptionPane.showMessageDialog(null, "没有选中行！操作错误！", "提示", 1);
/* 6062 */           return;
/*      */         }
/* 6064 */         if (type == 0) {
/* 6065 */           ps = con.prepareStatement("update inventoryitems set itemid = ?,quantity = ? WHERE inventoryitemid = ?");
/* 6066 */           ps.setInt(1, Integer.parseInt(this.特殊.getValueAt(i, 2).toString()));
/* 6067 */           ps.setInt(2, Integer.parseInt(this.特殊.getValueAt(i, 3).toString()));
/* 6068 */           ps.setInt(3, Integer.parseInt(this.特殊.getValueAt(i, 0).toString()));
/* 6069 */           ps.execute();
/* 6070 */           ps.close();
/*      */         }
/* 6072 */         if (type == 1) {
/* 6073 */           ps = con.prepareStatement("delete from inventoryitems WHERE inventoryitemid = ?");
/* 6074 */           ps.setInt(1, Integer.parseInt(this.特殊.getValueAt(i, 0).toString()));
/* 6075 */           ps.execute();
/* 6076 */           ps.close();
/* 6077 */           ((DefaultTableModel)this.特殊.getModel()).removeRow(i);
/*      */         }
/*      */         break;
/*      */       case 6: 
/* 6081 */         i = this.仓库.getSelectedRow();
/* 6082 */         if (i < 0) {
/* 6083 */           JOptionPane.showMessageDialog(null, "没有选中行！操作错误！", "提示", 1);
/* 6084 */           return;
/*      */         }
/* 6086 */         if (type == 0) {
/* 6087 */           ps = con.prepareStatement("update inventoryitems set itemid = ?,quantity = ? WHERE inventoryitemid = ?");
/* 6088 */           ps.setInt(1, Integer.parseInt(this.仓库.getValueAt(i, 2).toString()));
/* 6089 */           ps.setInt(2, Integer.parseInt(this.仓库.getValueAt(i, 3).toString()));
/* 6090 */           ps.setInt(3, Integer.parseInt(this.仓库.getValueAt(i, 0).toString()));
/* 6091 */           ps.execute();
/* 6092 */           ps.close();
/*      */         }
/* 6094 */         if (type == 1) {
/* 6095 */           ps = con.prepareStatement("delete from inventoryitems WHERE inventoryitemid = ?");
/* 6096 */           ps.setInt(1, Integer.parseInt(this.仓库.getValueAt(i, 0).toString()));
/* 6097 */           ps.execute();
/* 6098 */           ps.close();
/* 6099 */           ((DefaultTableModel)this.仓库.getModel()).removeRow(i);
/*      */         }
/*      */         break;
/*      */       case 7: 
/* 6103 */         if (type == 0) {
/* 6104 */           ps = con.prepareStatement("update csitems set itemid = ?,quantity = ? WHERE inventoryitemid = ?");
/* 6105 */           ps.setInt(1, Integer.parseInt(this.商城.getValueAt(i, 2).toString()));
/* 6106 */           ps.setInt(2, Integer.parseInt(this.商城.getValueAt(i, 3).toString()));
/* 6107 */           ps.setInt(3, Integer.parseInt(this.商城.getValueAt(i, 0).toString()));
/* 6108 */           ps.execute();
/* 6109 */           ps.close();
/*      */         }
/* 6111 */         if (type == 1) {
/* 6112 */           ps = con.prepareStatement("delete from csitems WHERE inventoryitemid = ?");
/* 6113 */           ps.setInt(1, Integer.parseInt(this.商城.getValueAt(i, 0).toString()));
/* 6114 */           ps.execute();
/* 6115 */           ps.close();
/* 6116 */           ((DefaultTableModel)this.商城.getModel()).removeRow(i);
/*      */         }
/*      */         
/*      */         break;
/*      */       }
/*      */       
/* 6122 */       JOptionPane.showMessageDialog(null, "处理完毕！\n需要确认最新信息，请手动重新获取！", "提示", 1);
/* 6123 */       con.close();
     } catch (SQLException ex) {
       System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + "]有错误!\r\n" + ex);
     }
   }
   
 

    private void jButton66ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton66ActionPerformed
     修改角色技能(0);//修改选中技能
    }//GEN-LAST:event_jButton66ActionPerformed

    private void jButton67ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton67ActionPerformed
     修改角色技能(1);//删除选中技能
    }//GEN-LAST:event_jButton67ActionPerformed

    private void jButton71ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton71ActionPerformed
    在线角色处理2(0);//强制下线
    }//GEN-LAST:event_jButton71ActionPerformed

    private void jButton77ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton77ActionPerformed
    在线角色处理2(1);//封号T下线
    }//GEN-LAST:event_jButton77ActionPerformed

    private void jButton78ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton78ActionPerformed
    在线角色处理2(2);//传送自由
    }//GEN-LAST:event_jButton78ActionPerformed

    private void jButton79ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton79ActionPerformed
     在线角色处理2(3);//传送地图
    }//GEN-LAST:event_jButton79ActionPerformed

    private void jButton80ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton80ActionPerformed
     在线角色处理2(4);//修改脸型
    }//GEN-LAST:event_jButton80ActionPerformed

    private void jButton81ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton81ActionPerformed
     在线角色处理2(5);//修改发型
    }//GEN-LAST:event_jButton81ActionPerformed

    private void jButton82ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton82ActionPerformed
     在线角色处理2(6);//加满技能
    }//GEN-LAST:event_jButton82ActionPerformed

    private void jButton83ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton83ActionPerformed
     在线角色处理2(7);//开技能栏
    }//GEN-LAST:event_jButton83ActionPerformed

    private void jButton84ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton84ActionPerformed
     在线角色处理2(8);//杀死玩家
    }//GEN-LAST:event_jButton84ActionPerformed

    private void jButton85ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton85ActionPerformed
     在线角色处理2(9);//关小黑屋
    }//GEN-LAST:event_jButton85ActionPerformed
    public void 在线角色处理2(int type) {
     boolean success = false;
    
     if (角色在线.getText().length() <= 1) {
     JOptionPane.showMessageDialog(null, "请选择你要处理的角色！"); return; }
     String id;
     switch (type) {
     case 0: 
       int answer = JOptionPane.showConfirmDialog(this, "你确定要踢[" + 角色在线.getText() + "]下线吗？", "信息", 0);
       if (answer != 0) {
         return;
       }
       for (ChannelServer cserv : ChannelServer.getAllInstances()) {
         for (MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
           if (chr != null)
           {
 

             if (chr.getName().contains(角色在线.getText())) {
               chr.getClient().disconnect(true, false);
               chr.getClient().getSession().close();
               success = true;
             }
           }
         }
       }
       break;
     case 1: //封号+提下线
       int n = JOptionPane.showConfirmDialog(this, "你确定要封[" + this.角色在线.getText() + "]的掉落并踢下线吗？", "信息", 0);
       if (n == 0) {
           
         return;
       }
       for (ChannelServer cserv : ChannelServer.getAllInstances()) {
         for (MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
           if (chr != null)
           {
 
 
             if (chr.getName().contains(this.角色在线.getText()))
             {
               chr.getClient().disconnect(true, false);
               chr.getClient().getSession().close();
               success = true;
             }
           }
         }
       }
       break;
     case 2: //传送自由
    /*   int n = JOptionPane.showConfirmDialog(this, "你确定要把[" + this.角色在线.getText() + "]传送到自由市场吗？", "信息", 0);
       if (n == 0) {
         return;
       }*/
       for (ChannelServer cserv : ChannelServer.getAllInstances()) {
         for (MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
           if (chr != null)
           {
 

             if (chr.getName().contains(this.角色在线.getText())) {
               success = true;
             }
           }
         }
       }
       break;
     case 3: //传送地图
       id = JOptionPane.showInputDialog(this, "你要把[" + this.角色在线.getText() + "]传送到哪里？(输入地图ID)", "信息", 2);
       for (ChannelServer cserv : ChannelServer.getAllInstances()) {
         for (MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
           if (chr != null)
           {
 
 
             if (chr.getName().contains(this.角色在线.getText())) {
               success = true;
             }
           }
         }
       }
       break;
     case 4: //改变脸型
       id = JOptionPane.showInputDialog(this, "输入你要更改[" + this.角色在线.getText() + "]的脸型ID！", "信息", 2);
       for (ChannelServer cserv : ChannelServer.getAllInstances()) {
         for (MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
           if (chr != null)
           {
 
 
             if (chr.getName().contains(this.角色在线.getText())) {
               chr.setFace(Integer.parseInt(id));
               success = true;
             }
           }
         }
       }
       break;
     case 5: //改变发型
       id = JOptionPane.showInputDialog(this, "输入你要更改[" + this.角色在线.getText() + "]的发型ID！", "信息", 2);
       for (ChannelServer cserv : ChannelServer.getAllInstances()) {
         for (MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
           if (chr != null)
           {
 
 
             if (chr.getName().contains(this.角色在线.getText())) {
               chr.setHair(Integer.parseInt(id));
               success = true;
             }
           }
         }
       }
       break;
     case 6: //满技能
       for (ChannelServer cserv : ChannelServer.getAllInstances()) {
         for (MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
           if (chr != null)
           {
 
 
             if (chr.getName().contains(this.角色在线.getText())) {
               chr.maxSkills();
               success = true;
             }
           }
         }
       }
       break;
     case 7: //开技能栏
       for (ChannelServer cserv : ChannelServer.getAllInstances()) {
         for (MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
           if (chr != null)
           {
 
 
             if (chr.getName().contains(this.角色在线.getText())) {
               success = true;
             }
           }
         }
       }
       break;
     case 8: //击杀角色
       for (ChannelServer cserv : ChannelServer.getAllInstances()) {
         for (MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
           if (chr != null)
           {
 
 
             if (chr.getName().contains(this.角色在线.getText())) {
               chr.startMapEffect("不友好之人！承受死亡的痛苦吧！", 5120027);
               
               chr.setHp(0);
               chr.updateSingleStat(MapleStat.HP, 0);
               success = true;
             }
           }
         }
       }
       break;
     case 9: //关小黑屋
       for (ChannelServer cserv : ChannelServer.getAllInstances()) {
         for (MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
           if (chr != null)
           {
 
 
             if (chr.getName().contains(this.角色在线.getText())) {
               success = true;
             }
           }
         }
       }
     }
     if (success) JOptionPane.showMessageDialog(null, "处理成功！", "提示", 1); else JOptionPane.showMessageDialog(null, "处理失败！角色没有在线或者不符合要求！", "提示", 1);
   }
    
    public void 在线角色表捕捉() {

        playerTable.setRowSelectionAllowed(true);
        //以下代码为监听角色表内添加
        playerTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {//仅当鼠标单击时响应
                //得到选中的行列的索引值
                int r = playerTable.getSelectedRow();
                int c = playerTable.getSelectedColumn();
                //得到选中的单元格的值，表格中都是字符串
                Object value = playerTable.getValueAt(r, c);
                //开始填充
                角色在线.setText(playerTable.getValueAt(r, 1).toString());

            }

        });
    }
    
    private void jButton86ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton86ActionPerformed
     全员下线();
    }//GEN-LAST:event_jButton86ActionPerformed

    private void 飞侠攻击力ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_飞侠攻击力ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_飞侠攻击力ActionPerformed

    private void 海盗敏捷ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_海盗敏捷ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_海盗敏捷ActionPerformed

    private void 端内破功管理员破功值ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_端内破功管理员破功值ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_端内破功管理员破功值ActionPerformed

    private void 海盗智力ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_海盗智力ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_海盗智力ActionPerformed

    private void 破功石增加伤害ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_破功石增加伤害ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_破功石增加伤害ActionPerformed

    private void 海盗运气ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_海盗运气ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_海盗运气ActionPerformed

    private void 破功伤害浮动值ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_破功伤害浮动值ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_破功伤害浮动值ActionPerformed

    private void 海盗攻击力ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_海盗攻击力ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_海盗攻击力ActionPerformed

    private void 战士力量ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_战士力量ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_战士力量ActionPerformed

    private void 法师力量ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_法师力量ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_法师力量ActionPerformed

    private void 战神敏捷ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_战神敏捷ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_战神敏捷ActionPerformed

    private void 战神智力ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_战神智力ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_战神智力ActionPerformed

    private void 飞侠力量ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_飞侠力量ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_飞侠力量ActionPerformed

    private void 战神运气ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_战神运气ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_战神运气ActionPerformed

    private void 弓手力量ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_弓手力量ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_弓手力量ActionPerformed

    private void 战神攻击力ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_战神攻击力ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_战神攻击力ActionPerformed

    private void 海盗力量ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_海盗力量ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_海盗力量ActionPerformed

    private void 法师敏捷ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_法师敏捷ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_法师敏捷ActionPerformed

    private void 战神力量ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_战神力量ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_战神力量ActionPerformed

    private void 战士敏捷ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_战士敏捷ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_战士敏捷ActionPerformed

    private void 法师智力ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_法师智力ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_法师智力ActionPerformed

    private void 法师运气ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_法师运气ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_法师运气ActionPerformed

    private void 战士智力ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_战士智力ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_战士智力ActionPerformed

    private void 法师魔法力ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_法师魔法力ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_法师魔法力ActionPerformed

    private void 战士运气ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_战士运气ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_战士运气ActionPerformed

    private void 战士攻击力ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_战士攻击力ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_战士攻击力ActionPerformed

    private void 弓手敏捷ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_弓手敏捷ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_弓手敏捷ActionPerformed

    private void 弓手智力ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_弓手智力ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_弓手智力ActionPerformed

    private void 弓手运气ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_弓手运气ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_弓手运气ActionPerformed

    private void 弓手攻击力ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_弓手攻击力ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_弓手攻击力ActionPerformed

    private void 飞侠敏捷ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_飞侠敏捷ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_飞侠敏捷ActionPerformed

    private void 飞侠智力ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_飞侠智力ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_飞侠智力ActionPerformed

    private void 端内破功最高伤害上限ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_端内破功最高伤害上限ActionPerformed

    }//GEN-LAST:event_端内破功最高伤害上限ActionPerformed

    private void 飞侠运气ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_飞侠运气ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_飞侠运气ActionPerformed

    private void 端内物理职业破功开关ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_端内物理职业破功开关ActionPerformed
    //    按键开关("端内物理职业破功开关", 80);  刷新端内物理职业破功开关();
    }//GEN-LAST:event_端内物理职业破功开关ActionPerformed

    private void 端内魔法职业破功开关ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_端内魔法职业破功开关ActionPerformed
     //   按键开关("端内魔法职业破功开关", 81);  刷新端内魔法职业破功开关();
    }//GEN-LAST:event_端内魔法职业破功开关ActionPerformed

    private void marketUseBoardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_marketUseBoardActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_marketUseBoardActionPerformed

    private void UpgradeconsultationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpgradeconsultationActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_UpgradeconsultationActionPerformed

    private void InjurytipsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InjurytipsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_InjurytipsActionPerformed

    private void debugModeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_debugModeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_debugModeActionPerformed

    private void PlayerchatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PlayerchatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PlayerchatActionPerformed

    private void ThrowoutgoldcoinsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ThrowoutgoldcoinsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ThrowoutgoldcoinsActionPerformed

    private void ThrowoutitemsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ThrowoutitemsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ThrowoutitemsActionPerformed

    private void OverdrawingarchivingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OverdrawingarchivingActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_OverdrawingarchivingActionPerformed

    private void OnlineannouncementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OnlineannouncementActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_OnlineannouncementActionPerformed

    private void MapnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MapnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MapnameActionPerformed

    private void GameinstructionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GameinstructionsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_GameinstructionsActionPerformed

    private void GamehornActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GamehornActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_GamehornActionPerformed

    private void ManagestealthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ManagestealthActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ManagestealthActionPerformed

    private void ManagementaccelerationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ManagementaccelerationActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ManagementaccelerationActionPerformed

    private void PlayertransactionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PlayertransactionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PlayertransactionActionPerformed

    private void HiredbusinessmanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HiredbusinessmanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_HiredbusinessmanActionPerformed

    private void LoginhelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoginhelpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_LoginhelpActionPerformed

    private void MonsterstateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MonsterstateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MonsterstateActionPerformed

    private void AuctionswitchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AuctionswitchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AuctionswitchActionPerformed

    private void jButton68ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton68ActionPerformed
        openWindow(Windows.广播系统);
    }//GEN-LAST:event_jButton68ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        System.gc();
        JOptionPane.showMessageDialog(null, "回收服务端内存成功！");
    }//GEN-LAST:event_jButton17ActionPerformed

    private void DroptheitemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DroptheitemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DroptheitemActionPerformed

    private void FullscreensuctionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FullscreensuctionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FullscreensuctionActionPerformed
/*      */   public void 读取角色技能()
/*      */   {
/* 6132 */     if (this.角色0.getText().length() <= 1) {
/* 6133 */       JOptionPane.showMessageDialog(null, "请选择你要读取的角色！");
/* 6134 */       return;
/*      */     }
/* 6136 */     for (int i = ((DefaultTableModel)this.技能信息.getModel()).getRowCount() - 1; i >= 0; i--) {
/* 6137 */       ((DefaultTableModel)this.技能信息.getModel()).removeRow(i);
/*      */     }
/*      */     try {
/* 6140 */       Connection con = DBConPool.getInstance().getDataSource().getConnection().getConnection();
/* 6141 */       PreparedStatement ps = null;
/* 6142 */       ResultSet rs = null;
/* 6143 */       ps = con.prepareStatement("SELECT * FROM skills WHERE characterid = ?");
                 ps.setInt(1, Integer.parseInt(ZeroMS_UI.角色ID.getText()));
/* 6145 */       rs = ps.executeQuery();
/* 6146 */       while (rs.next()) {
                  int SkillId = rs.getInt("skillid");
/* 6147 */         MapleDataProvider data = provider.MapleDataProviderFactory.getDataProvider(new java.io.File((System.getProperty("wzpath") != null ? System.getProperty("wzpath") : "") + "wz/String.wz"));
/*      */         
/*      */ 
/* 6150 */         String skillName = null;
/* 6151 */         MapleData itemsData = data.getData("Skill.img");
/* 6152 */         for (MapleData itemFolder : itemsData.getChildren()) {
/* 6153 */           int skillId = Integer.parseInt(itemFolder.getName());
/* 6154 */           if (rs.getInt("skillid") == skillId) {
/* 6155 */             skillName = provider.MapleDataTool.getString("name", itemFolder, "NO-NAME");
/*      */           }
/*      */         }
/* 6158 */         ((DefaultTableModel)this.技能信息.getModel()).insertRow(this.技能信息.getRowCount(), new Object[] {
/* 6159 */           Integer.valueOf(rs.getInt("id")), skillName, 
/*      */           
/* 6161 */           Integer.valueOf(rs.getInt("skillid")), 
/* 6162 */           Integer.valueOf(rs.getInt("skilllevel")), 
/* 6163 */           Integer.valueOf(rs.getInt("masterlevel")), 
                     SkillId
                      });
/*      */       }
/*      */       
/* 6166 */       ps.close();
/* 6167 */       rs.close();
/* 6168 */       JOptionPane.showMessageDialog(null, "读取速度因角色数据量而异！\n读取完毕！可前往查看！", "提示", 1);
/*      */     } catch (SQLException ex) {
/* 6170 */       System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + "]有错误!\r\n" + ex);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public void 修改角色技能(int type)
/*      */   {
/* 6177 */     Connection con = null;
/* 6178 */     PreparedStatement ps = null;
/* 6179 */     int i = this.技能信息.getSelectedRow();
/* 6180 */     if (i < 0) {
/* 6181 */       JOptionPane.showMessageDialog(null, "没有选中行！操作错误！", "提示", 1);
/* 6182 */       return;
/*      */     }
/*      */     try {
/* 6185 */       con = DBConPool.getInstance().getDataSource().getConnection();
/* 6186 */       if (type == 0) {
/* 6187 */         ps = con.prepareStatement("update skills set skillid = ?,skilllevel = ?,masterlevel = ? WHERE id = ?");
/* 6188 */         ps.setInt(1, Integer.parseInt(this.技能信息.getValueAt(i, 2).toString()));
/* 6189 */         ps.setInt(2, Integer.parseInt(this.技能信息.getValueAt(i, 3).toString()));
/* 6190 */         ps.setInt(3, Integer.parseInt(this.技能信息.getValueAt(i, 4).toString()));
/* 6191 */         ps.setInt(4, Integer.parseInt(this.技能信息.getValueAt(i, 0).toString()));
/* 6192 */         ps.execute();
/* 6193 */         ps.close();
/*      */       }
/* 6195 */       if (type == 1) {
/* 6196 */         ps = con.prepareStatement("delete from skills WHERE id = ?");
/* 6197 */         ps.setInt(1, Integer.parseInt(this.技能信息.getValueAt(i, 0).toString()));
/* 6198 */         ps.execute();
/* 6199 */         ps.close();
/* 6200 */         ((DefaultTableModel)this.技能信息.getModel()).removeRow(i);
/*      */       }
/*      */       
/* 6203 */       JOptionPane.showMessageDialog(null, "处理完毕！\n需要确认最新信息，请手动重新获取！", "提示", 1);
/* 6204 */       con.close();
/*      */     } catch (SQLException ex) {
/* 6206 */       System.err.println("[" + FileoutputUtil.CurrentReadable_Time() + "]有错误!\r\n" + ex);
/*      */     }
/*      */   }
    
  
    
    public void 注册新账号() {
        boolean result1 = 输入0.getText().matches("[0-9]+");
        boolean result2 = 输入1.getText().matches("[0-9]+");
        if (输入0.getText().equals("") || 输入1.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "[信息]:请填写注册的账号密码");
            return;
        } else {
            Connection con;
            String account = 输入0.getText();
            String password = 输入1.getText();

            if (password.length() > 10) {
                JOptionPane.showMessageDialog(null, "[信息]:注册失败，密码过长");
                return;
            }
            if (AutoRegister.getAccountExists(account)) {
                JOptionPane.showMessageDialog(null, "[信息]:注册失败，账号已存在");
                return;
            }
            try {
                con = DBConPool.getInstance().getDataSource().getConnection();
            } catch (Exception ex) {
                System.out.println(ex);
                return;
            }
            try {
                PreparedStatement ps = con.prepareStatement("INSERT INTO accounts (name, password) VALUES (?,?)");
                ps.setString(1, account);
                ps.setString(2, LoginCrypto.hexSha1(password));
                ps.executeUpdate();
            //刷新账号界面按钮
            ((DefaultTableModel) accountstable.getModel()).getDataVector().clear();
            ((DefaultTableModel) accountstable.getModel()).fireTableDataChanged();//通知模型更新
            accountstable.updateUI();//刷新表格
            inivalue.初始化账号表(0);
                JOptionPane.showMessageDialog(null, "注册成功。账号: " + account + " 密码: " + password + "");
            } catch (SQLException ex) {
                System.out.println(ex);
                return;
            }
        }
    }
    
    
    private void sendNotice(int a) {
        try {
            String str = noticeText.getText();
            String 输出 = "";
            for (ChannelServer cserv1 : ChannelServer.getAllInstances()) {
                for (MapleCharacter mch : cserv1.getPlayerStorage().getAllCharacters()) {
                    switch (a) {
                        case 0:
                            //红色提示公告
                            World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(5, str.toString()));
                            break;
                        case 1:
                            //顶端公告
                            World.Broadcast.broadcastMessage(MaplePacketCreator.serverMessage(str.toString()));
                            break;
                        case 2:
                            //弹窗公告
                            World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(1, str.toString()));
                            break;
                        case 3:
                            //聊天蓝色公告
                            World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(0, str));
                            break;
                        case 4:
                            mch.startMapEffect(str, Integer.parseInt(公告发布喇叭代码.getText()));
                            break;
                        default:
                            break;
                    }

                }
                公告发布喇叭代码.setText("5120027");
            }
        } catch (Exception e) {
        }
    }
    
        public void 账号表捕捉() {

        accountstable.setRowSelectionAllowed(true);
        //以下代码为监听角色表内添加
        accountstable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {//仅当鼠标单击时响应
                //得到选中的行列的索引值
                int r = accountstable.getSelectedRow();
                int c = accountstable.getSelectedColumn();
                //得到选中的单元格的值，表格中都是字符串
                Object value = accountstable.getValueAt(r, c);
                //开始填充
                输入0.setText(accountstable.getValueAt(r, 1).toString());
                输入1.setText(accountstable.getValueAt(r, 2).toString());
                输入2.setText(accountstable.getValueAt(r, 3).toString());
                输入3.setText(accountstable.getValueAt(r, 4).toString());
                输入4.setText(accountstable.getValueAt(r, 5).toString());
                accid = Integer.valueOf(accountstable.getValueAt(r, 0).toString());
                mima = accountstable.getValueAt(r, 2).toString();//这里是赋值到定义的变量来判断是否密码修改了

            }

        });
    }
        public void 角色表捕捉() {

        characterstable.setRowSelectionAllowed(true);
        //以下代码为监听角色表内添加
        characterstable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {//仅当鼠标单击时响应
                //得到选中的行列的索引值
                int r = characterstable.getSelectedRow();
                int c = characterstable.getSelectedColumn();
                //得到选中的单元格的值，表格中都是字符串
                Object value = characterstable.getValueAt(r, c);
                //开始填充
                角色ID.setText(characterstable.getValueAt(r, 0).toString());
                角色0.setText(characterstable.getValueAt(r, 1).toString());
                角色1.setText(characterstable.getValueAt(r, 2).toString());
                角色2.setText(characterstable.getValueAt(r, 3).toString());
                角色3.setText(characterstable.getValueAt(r, 4).toString());
                角色4.setText(characterstable.getValueAt(r, 5).toString());
                角色5.setText(characterstable.getValueAt(r, 6).toString());
                角色6.setText(characterstable.getValueAt(r, 7).toString());
                角色7.setText(characterstable.getValueAt(r, 8).toString());
                角色9.setText(characterstable.getValueAt(r, 10).toString());
                角色8.setText(characterstable.getValueAt(r, 9).toString());
                角色10.setText(characterstable.getValueAt(r, 11).toString());
                角色11.setText(characterstable.getValueAt(r, 13).toString());
                角色12.setText(characterstable.getValueAt(r, 14).toString());
                角色13.setText(characterstable.getValueAt(r, 15).toString());
                accid = Integer.valueOf(characterstable.getValueAt(r, 0).toString());

            //    String info = r + "行" + c + "列值 : " + value.toString()+" 读取："+accountstable.getColumnCount();
            //    JOptionPane.showMessageDialog(null, info);

            }

        });
    }
        

    /**
     * @param args the command line arguments
     */

  
    private static long starttime = 0;
    public static void main(String args[]) throws InterruptedException {

        starttime = System.currentTimeMillis();
            EventQueue.invokeLater(new Runnable() {
            public void run() {

            ZeroMS_UI.getInstance().setVisible(true);

        System.out.println("【" + CurrentReadable_Time() + "】【初始化完成,感谢使用ZeroMS服务端】");
            }
        });
    }
    @Override
    public void setVisible(final boolean bln) {
        final Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((int)(size.getWidth() - this.getWidth()) / 2, (int)(size.getHeight() - this.getHeight()) / 2);
        super.setVisible(bln);
        System.setOut(out);
        System.setErr(err);
    }

  



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox AUTO_REGISTER;
    private javax.swing.JTextField Accountnumberprohibitionprompt;
    private javax.swing.JLabel ActiveThread;
    private javax.swing.JCheckBox Auctionswitch;
    private javax.swing.JTextField Automaticcleaning;
    private javax.swing.JTextField BEGINNER_SPAWN_MAP;
    private javax.swing.JTextField CashPort;
    private javax.swing.JTextField ChannelPort;
    private javax.swing.JTextField Channelnumber;
    private javax.swing.JCheckBox CollegeSystem;
    private javax.swing.JTextField CreateGuildCost;
    private javax.swing.JTextArea Custommultiplayermap;
    private javax.swing.JCheckBox Doubleburstchannel;
    private javax.swing.JTextField Doubleexplosionchannel;
    private javax.swing.JCheckBox Droptheitem;
    private javax.swing.JTextField Employmentexperience;
    private javax.swing.JTextField Fishingexperienceratio;
    private javax.swing.JTextField Fishinggoldcoinsseveral;
    private javax.swing.JCheckBox Forgingsystem;
    private javax.swing.JCheckBox Fullscreengoldcoin;
    private javax.swing.JCheckBox Fullscreenpetsuction;
    private javax.swing.JCheckBox Fullscreensuction;
    private javax.swing.JCheckBox Gamehorn;
    private javax.swing.JCheckBox Gameinstructions;
    private javax.swing.JCheckBox GodofWar;
    private javax.swing.JCheckBox Hiredbusinessman;
    private javax.swing.JTextField IP;
    private javax.swing.JCheckBox Injurytips;
    private javax.swing.JCheckBox Knights;
    private javax.swing.JTextField LoginPort;
    private javax.swing.JCheckBox Loginhelp;
    private javax.swing.JCheckBox Managementacceleration;
    private javax.swing.JCheckBox Managestealth;
    private javax.swing.JCheckBox Mapname;
    private javax.swing.JTextField Marriageexperience;
    private javax.swing.JTextField Monsterdropoffset;
    private javax.swing.JTextField Monsterdroproll;
    private javax.swing.JCheckBox Monsterstate;
    private javax.swing.JTextField OfflineDoudou;
    private javax.swing.JTextField Offlinecouponcounting;
    private javax.swing.JTextField Offlineexperience;
    private javax.swing.JTextField Offlinegoldcoins;
    private javax.swing.JCheckBox Offlinehangup;
    private javax.swing.JTextField Offlineoffset;
    private javax.swing.JCheckBox Onlineannouncement;
    private javax.swing.JCheckBox Openmultiplayermap;
    private javax.swing.JCheckBox Overdrawingarchiving;
    private javax.swing.JCheckBox Petsarenothungry;
    private javax.swing.JCheckBox Playerchat;
    private javax.swing.JCheckBox Playertransaction;
    private javax.swing.JTextField RSGS;
    private javax.swing.JLabel RunStats;
    private javax.swing.JLabel RunTime;
    private javax.swing.JTextField SQL_DATABASE;
    private javax.swing.JTextField SQL_IP;
    private javax.swing.JTextField SQL_PASSWORD;
    private javax.swing.JTextField SQL_PORT;
    private javax.swing.JTextField SQL_USER;
    private javax.swing.JTextField ServerMessage;
    private javax.swing.JTextField Servermaintenanceprompt;
    private javax.swing.JTextField Stackquantity;
    private javax.swing.JCheckBox Strangeexplosionresistance;
    private javax.swing.JCheckBox Strangeexplosivepointvolume;
    private javax.swing.JCheckBox Throwoutgoldcoins;
    private javax.swing.JCheckBox Throwoutitems;
    private javax.swing.JTextField Turnoffserverprompt;
    private javax.swing.JCheckBox USE_FIXED_IV;
    private javax.swing.JCheckBox Upgradeconsultation;
    private javax.swing.JCheckBox Use_Localhost;
    private javax.swing.JTextField Weakengold;
    private javax.swing.JTextField a1;
    private javax.swing.JLabel a13;
    private javax.swing.JLabel a14;
    private javax.swing.JTextField a2;
    public static javax.swing.JTable accountstable;
    private javax.swing.JLabel acct;
    private javax.swing.JCheckBox adminOnly;
    private javax.swing.JCheckBox adventurer;
    private javax.swing.JCheckBox applAttackMP;
    private javax.swing.JCheckBox applAttackNumber;
    private javax.swing.JCheckBox applAttackRange;
    private javax.swing.JCheckBox applAttackmobcount;
    private javax.swing.JCheckBox autoLieDetector;
    private javax.swing.JCheckBox banallskill;
    private javax.swing.JCheckBox bandropitem;
    private javax.swing.JCheckBox bangainexp;
    private javax.swing.JTextField bankHandlingFee;
    private javax.swing.JTextField bossRate;
    private javax.swing.JTextField channelCount;
    public static javax.swing.JTable characterstable;
    private javax.swing.JTextField createEmblemMoney;
    private javax.swing.JTextField createMobInterval;
    private javax.swing.JCheckBox debugMode;
    private javax.swing.JTextField defaultInventorySlot;
    private javax.swing.JTextField dropRate;
    private javax.swing.JTextField dyjb;
    private javax.swing.JTextField dyjy;
    private javax.swing.JCheckBox enablepointsbuy;
    private javax.swing.JTextField eventMessage;
    private javax.swing.JTextField events;
    private javax.swing.JTextField expRate;
    private javax.swing.JTextField expRate109;
    private javax.swing.JTextField expRate129;
    private javax.swing.JTextField expRate139;
    private javax.swing.JTextField expRate149;
    private javax.swing.JTextField expRate159;
    private javax.swing.JTextField expRate169;
    private javax.swing.JTextField expRate179;
    private javax.swing.JTextField expRate189;
    private javax.swing.JTextField expRate200;
    private javax.swing.JTextField expRate250;
    private javax.swing.JTextField expRate69;
    private javax.swing.JTextField expRate89;
    private javax.swing.JTextField flag;
    private javax.swing.JTextField guildPvpMap;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton25;
    private javax.swing.JButton jButton26;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton28;
    private javax.swing.JButton jButton29;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton30;
    private javax.swing.JButton jButton31;
    private javax.swing.JButton jButton32;
    private javax.swing.JButton jButton33;
    private javax.swing.JButton jButton34;
    private javax.swing.JButton jButton35;
    private javax.swing.JButton jButton36;
    private javax.swing.JButton jButton37;
    private javax.swing.JButton jButton38;
    private javax.swing.JButton jButton39;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton40;
    private javax.swing.JButton jButton44;
    private javax.swing.JButton jButton45;
    private javax.swing.JButton jButton46;
    private javax.swing.JButton jButton48;
    private javax.swing.JButton jButton49;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton50;
    private javax.swing.JButton jButton51;
    private javax.swing.JButton jButton52;
    private javax.swing.JButton jButton53;
    private javax.swing.JButton jButton54;
    private javax.swing.JButton jButton55;
    private javax.swing.JButton jButton56;
    private javax.swing.JButton jButton57;
    private javax.swing.JButton jButton58;
    private javax.swing.JButton jButton59;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton60;
    private javax.swing.JButton jButton61;
    private javax.swing.JButton jButton62;
    private javax.swing.JButton jButton63;
    private javax.swing.JButton jButton64;
    private javax.swing.JButton jButton65;
    private javax.swing.JButton jButton66;
    private javax.swing.JButton jButton67;
    private javax.swing.JButton jButton68;
    private javax.swing.JButton jButton69;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton70;
    private javax.swing.JButton jButton71;
    private javax.swing.JButton jButton72;
    private javax.swing.JButton jButton73;
    private javax.swing.JButton jButton74;
    private javax.swing.JButton jButton75;
    private javax.swing.JButton jButton76;
    private javax.swing.JButton jButton77;
    private javax.swing.JButton jButton78;
    private javax.swing.JButton jButton79;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton80;
    private javax.swing.JButton jButton81;
    private javax.swing.JButton jButton82;
    private javax.swing.JButton jButton83;
    private javax.swing.JButton jButton84;
    private javax.swing.JButton jButton85;
    private javax.swing.JButton jButton86;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel107;
    private javax.swing.JLabel jLabel108;
    private javax.swing.JLabel jLabel109;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel110;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel112;
    private javax.swing.JLabel jLabel113;
    private javax.swing.JLabel jLabel114;
    private javax.swing.JLabel jLabel115;
    private javax.swing.JLabel jLabel116;
    private javax.swing.JLabel jLabel117;
    private javax.swing.JLabel jLabel118;
    private javax.swing.JLabel jLabel119;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel120;
    private javax.swing.JLabel jLabel121;
    private javax.swing.JLabel jLabel122;
    private javax.swing.JLabel jLabel123;
    private javax.swing.JLabel jLabel124;
    private javax.swing.JLabel jLabel125;
    private javax.swing.JLabel jLabel126;
    private javax.swing.JLabel jLabel127;
    private javax.swing.JLabel jLabel128;
    private javax.swing.JLabel jLabel129;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel130;
    private javax.swing.JLabel jLabel131;
    private javax.swing.JLabel jLabel132;
    private javax.swing.JLabel jLabel133;
    private javax.swing.JLabel jLabel134;
    private javax.swing.JLabel jLabel135;
    private javax.swing.JLabel jLabel136;
    private javax.swing.JLabel jLabel137;
    private javax.swing.JLabel jLabel138;
    private javax.swing.JLabel jLabel139;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel140;
    private javax.swing.JLabel jLabel141;
    private javax.swing.JLabel jLabel142;
    private javax.swing.JLabel jLabel143;
    private javax.swing.JLabel jLabel144;
    private javax.swing.JLabel jLabel145;
    private javax.swing.JLabel jLabel146;
    private javax.swing.JLabel jLabel147;
    private javax.swing.JLabel jLabel148;
    private javax.swing.JLabel jLabel149;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel150;
    private javax.swing.JLabel jLabel151;
    private javax.swing.JLabel jLabel152;
    private javax.swing.JLabel jLabel153;
    private javax.swing.JLabel jLabel154;
    private javax.swing.JLabel jLabel155;
    private javax.swing.JLabel jLabel156;
    private javax.swing.JLabel jLabel157;
    private javax.swing.JLabel jLabel158;
    private javax.swing.JLabel jLabel159;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel160;
    private javax.swing.JLabel jLabel161;
    private javax.swing.JLabel jLabel162;
    private javax.swing.JLabel jLabel163;
    private javax.swing.JLabel jLabel164;
    private javax.swing.JLabel jLabel165;
    private javax.swing.JLabel jLabel166;
    private javax.swing.JLabel jLabel167;
    private javax.swing.JLabel jLabel168;
    private javax.swing.JLabel jLabel169;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel170;
    private javax.swing.JLabel jLabel171;
    private javax.swing.JLabel jLabel172;
    private javax.swing.JLabel jLabel173;
    private javax.swing.JLabel jLabel174;
    private javax.swing.JLabel jLabel175;
    private javax.swing.JLabel jLabel176;
    private javax.swing.JLabel jLabel177;
    private javax.swing.JLabel jLabel178;
    private javax.swing.JLabel jLabel179;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel219;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel220;
    private javax.swing.JLabel jLabel221;
    private javax.swing.JLabel jLabel222;
    private javax.swing.JLabel jLabel223;
    private javax.swing.JLabel jLabel224;
    private javax.swing.JLabel jLabel225;
    private javax.swing.JLabel jLabel226;
    private javax.swing.JLabel jLabel227;
    private javax.swing.JLabel jLabel228;
    private javax.swing.JLabel jLabel229;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel230;
    private javax.swing.JLabel jLabel231;
    private javax.swing.JLabel jLabel232;
    private javax.swing.JLabel jLabel233;
    private javax.swing.JLabel jLabel235;
    private javax.swing.JLabel jLabel236;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel240;
    private javax.swing.JLabel jLabel241;
    private javax.swing.JLabel jLabel242;
    private javax.swing.JLabel jLabel246;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel259;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel265;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel274;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel348;
    private javax.swing.JLabel jLabel349;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel359;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel360;
    private javax.swing.JLabel jLabel361;
    private javax.swing.JLabel jLabel362;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel46;
    private javax.swing.JPanel jPanel47;
    private javax.swing.JPanel jPanel48;
    private javax.swing.JPanel jPanel49;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel50;
    private javax.swing.JPanel jPanel51;
    private javax.swing.JPanel jPanel52;
    private javax.swing.JPanel jPanel53;
    private javax.swing.JPanel jPanel54;
    private javax.swing.JPanel jPanel55;
    private javax.swing.JPanel jPanel56;
    private javax.swing.JPanel jPanel57;
    private javax.swing.JPanel jPanel58;
    private javax.swing.JPanel jPanel59;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel60;
    private javax.swing.JPanel jPanel61;
    private javax.swing.JPanel jPanel62;
    private javax.swing.JPanel jPanel63;
    private javax.swing.JPanel jPanel64;
    private javax.swing.JPanel jPanel65;
    private javax.swing.JPanel jPanel66;
    private javax.swing.JPanel jPanel67;
    private javax.swing.JPanel jPanel68;
    private javax.swing.JPanel jPanel69;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel70;
    private javax.swing.JPanel jPanel71;
    private javax.swing.JPanel jPanel72;
    private javax.swing.JPanel jPanel73;
    private javax.swing.JPanel jPanel74;
    private javax.swing.JPanel jPanel75;
    private javax.swing.JPanel jPanel76;
    private javax.swing.JPanel jPanel77;
    private javax.swing.JPanel jPanel78;
    private javax.swing.JPanel jPanel79;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel83;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane19;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane20;
    private javax.swing.JScrollPane jScrollPane21;
    private javax.swing.JScrollPane jScrollPane22;
    private javax.swing.JScrollPane jScrollPane23;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTabbedPane jTabbedPane6;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea5;
    private javax.swing.JTextField jTextField22;
    public static javax.swing.JTextField jTextField23;
    public static javax.swing.JTextField jTextField24;
    public static javax.swing.JTextField jTextField25;
    private javax.swing.JTextField kocmaxlevel;
    private javax.swing.JCheckBox logPackets;
    private javax.swing.JCheckBox logs_DAMAGE;
    private javax.swing.JCheckBox logs_PACKETS;
    private javax.swing.JCheckBox logs_chat;
    private javax.swing.JCheckBox logs_csbuy;
    private javax.swing.JCheckBox logs_mrechant;
    private javax.swing.JCheckBox logs_npcshop_buy;
    private javax.swing.JCheckBox logs_storage;
    private javax.swing.JCheckBox logs_trade;
    private javax.swing.JCheckBox marketUseBoard;
    private javax.swing.JTextField maxCharacters;
    private javax.swing.JTextField maxlevel;
    private javax.swing.JTextField mesoRate;
    private javax.swing.JTextField monsterSpawn;
    private javax.swing.JTextField noticeText;
    private javax.swing.JTextField offlinemap;
    public static javax.swing.JTextPane output_err_jTextPane;
    public static javax.swing.JTextPane output_jTextPane;
    public static javax.swing.JTextPane output_notice_jTextPane;
    public static javax.swing.JTextPane output_out_jTextPane;
    public static javax.swing.JTextPane output_packet_jTextPane;
    private javax.swing.JTextField partyPvpMap;
    private javax.swing.JTextField pdexp;
    private javax.swing.JCheckBox pdkg;
    private javax.swing.JTextField pdmap;
    private javax.swing.JTextField pdzddd;
    private javax.swing.JTextField pdzddj;
    private javax.swing.JTextField pdzddy;
    private javax.swing.JTextField pdzdjb;
    private javax.swing.JTextField pdzsdd;
    private javax.swing.JTextField pdzsdj;
    private javax.swing.JTextField pdzsdy;
    private javax.swing.JTextField pdzsjb;
    private javax.swing.JTextField personalPvpMap;
    private javax.swing.JTextField petRate;
    private javax.swing.JTextField petsuction;
    private javax.swing.JTable playerTable;
    private javax.swing.JButton qiannengdaima;
    private javax.swing.JButton sendMsgNotice;
    private javax.swing.JButton sendNotice;
    private javax.swing.JButton sendNpcTalkNotice;
    private javax.swing.JButton sendWinNotice;
    private javax.swing.JTextField serverName;
    private javax.swing.JTextField statLimit;
    private javax.swing.JTextField takeOutHandlingFee;
    private javax.swing.JCheckBox wirelessbuff;
    private javax.swing.JTextField wzpath;
    private javax.swing.JButton z1;
    private javax.swing.JButton z10;
    private javax.swing.JButton z11;
    private javax.swing.JButton z12;
    private javax.swing.JButton z2;
    private javax.swing.JButton z3;
    private javax.swing.JButton z4;
    private javax.swing.JButton z5;
    private javax.swing.JButton z6;
    private javax.swing.JButton z7;
    private javax.swing.JButton z8;
    private javax.swing.JButton z9;
    private javax.swing.JTextField 三倍爆率持续时间;
    private javax.swing.JTextField 三倍经验持续时间;
    private javax.swing.JTextField 三倍金币持续时间;
    private javax.swing.JTextField 个人发送物品代码;
    private javax.swing.JTextField 个人发送物品数量;
    private javax.swing.JTextField 个人发送物品玩家名字;
    private javax.swing.JTextField 个人发送物品玩家名字1;
    private javax.swing.JTable 仓库;
    private javax.swing.JButton 保存数据按钮;
    private javax.swing.JButton 保存雇佣按钮;
    private javax.swing.JTextField 全服发送物品代码;
    private javax.swing.JTextField 全服发送物品数量;
    private javax.swing.JTextField 全服发送装备物品ID;
    private javax.swing.JTextField 全服发送装备装备HP;
    private javax.swing.JTextField 全服发送装备装备MP;
    private javax.swing.JTextField 全服发送装备装备制作人;
    private javax.swing.JTextField 全服发送装备装备力量;
    private javax.swing.JTextField 全服发送装备装备加卷;
    private javax.swing.JTextField 全服发送装备装备可否交易;
    private javax.swing.JTextField 全服发送装备装备攻击力;
    private javax.swing.JTextField 全服发送装备装备敏捷;
    private javax.swing.JTextField 全服发送装备装备智力;
    private javax.swing.JTextField 全服发送装备装备潜能1;
    private javax.swing.JTextField 全服发送装备装备潜能2;
    private javax.swing.JTextField 全服发送装备装备潜能3;
    private javax.swing.JTextField 全服发送装备装备物理防御;
    private javax.swing.JTextField 全服发送装备装备给予时间;
    private javax.swing.JTextField 全服发送装备装备运气;
    private javax.swing.JTextField 全服发送装备装备魔法力;
    private javax.swing.JTextField 全服发送装备装备魔法防御;
    private javax.swing.JTextField 公告发布喇叭代码;
    private javax.swing.JTable 其他;
    private javax.swing.JLabel 内存使用;
    private javax.swing.JProgressBar 内存条;
    private javax.swing.JTextField 双倍爆率持续时间;
    private javax.swing.JTextField 双倍经验持续时间;
    private javax.swing.JTextField 双倍金币持续时间;
    private javax.swing.JTextField 发送装备玩家姓名;
    private javax.swing.JTable 商城;
    private javax.swing.JLabel 在线人数;
    private javax.swing.JButton 开启三倍爆率;
    private javax.swing.JButton 开启三倍经验;
    private javax.swing.JButton 开启三倍金币;
    private javax.swing.JButton 开启双倍爆率;
    private javax.swing.JButton 开启双倍经验;
    private javax.swing.JButton 开启双倍金币;
    private javax.swing.JTextField 弓手力量;
    private javax.swing.JTextField 弓手攻击力;
    private javax.swing.JTextField 弓手敏捷;
    private javax.swing.JTextField 弓手智力;
    private javax.swing.JTextField 弓手运气;
    private javax.swing.JTextField 战士力量;
    private javax.swing.JTextField 战士攻击力;
    private javax.swing.JTextField 战士敏捷;
    private javax.swing.JTextField 战士智力;
    private javax.swing.JTextField 战士运气;
    private javax.swing.JTextField 战神力量;
    private javax.swing.JTextField 战神攻击力;
    private javax.swing.JTextField 战神敏捷;
    private javax.swing.JTextField 战神智力;
    private javax.swing.JTextField 战神运气;
    private javax.swing.JTable 技能信息;
    private javax.swing.JLabel 显示在线账号;
    private javax.swing.JTextField 本机授权码;
    private javax.swing.JButton 查询在线玩家人数按钮;
    private javax.swing.JTextField 法师力量;
    private javax.swing.JTextField 法师敏捷;
    private javax.swing.JTextField 法师智力;
    private javax.swing.JTextField 法师运气;
    private javax.swing.JTextField 法师魔法力;
    private javax.swing.JTextField 海盗力量;
    private javax.swing.JTextField 海盗攻击力;
    private javax.swing.JTextField 海盗敏捷;
    private javax.swing.JTextField 海盗智力;
    private javax.swing.JTextField 海盗运气;
    private javax.swing.JTable 消耗;
    private javax.swing.JCheckBox 物品叠加开关6;
    private javax.swing.JCheckBox 物品叠加开关7;
    private javax.swing.JCheckBox 物品叠加开关8;
    private javax.swing.JCheckBox 物品叠加开关9;
    private javax.swing.JTable 特殊;
    private javax.swing.JTextField 破功伤害浮动值;
    private javax.swing.JTextField 破功石增加伤害;
    private javax.swing.JButton 端内物理职业破功开关;
    private javax.swing.JTextField 端内破功最高伤害上限;
    private javax.swing.JTextField 端内破功管理员破功值;
    private javax.swing.JButton 端内魔法职业破功开关;
    private javax.swing.JButton 给予物品;
    private javax.swing.JButton 给予物品1;
    private javax.swing.JButton 给予装备1;
    private javax.swing.JButton 给予装备2;
    private javax.swing.JTable 装备;
    private javax.swing.JTextField 装备星星1;
    public static javax.swing.JTextField 角色0;
    public static javax.swing.JTextField 角色1;
    public static javax.swing.JTextField 角色10;
    public static javax.swing.JTextField 角色11;
    public static javax.swing.JTextField 角色12;
    public static javax.swing.JTextField 角色13;
    public static javax.swing.JTextField 角色2;
    public static javax.swing.JTextField 角色3;
    public static javax.swing.JTextField 角色4;
    public static javax.swing.JTextField 角色5;
    public static javax.swing.JTextField 角色6;
    public static javax.swing.JTextField 角色7;
    public static javax.swing.JTextField 角色8;
    public static javax.swing.JTextField 角色9;
    public static javax.swing.JTextField 角色ID;
    private javax.swing.JTextField 角色在线;
    public static javax.swing.JLabel 角色总数标签;
    private javax.swing.JTable 设置;
    public static javax.swing.JLabel 账号总数标签;
    private javax.swing.JButton 账号搜索;
    private javax.swing.JTabbedPane 资源页签;
    private javax.swing.JTable 身上;
    public static javax.swing.JTextField 输入0;
    public static javax.swing.JTextField 输入1;
    public static javax.swing.JTextField 输入2;
    public static javax.swing.JTextField 输入3;
    public static javax.swing.JTextField 输入4;
    private javax.swing.JButton 重载任务;
    private javax.swing.JButton 重载传送门按钮;
    private javax.swing.JButton 重载副本按钮;
    private javax.swing.JButton 重载包头按钮;
    private javax.swing.JButton 重载反应堆按钮;
    private javax.swing.JButton 重载商城按钮;
    private javax.swing.JButton 重载商店按钮;
    private javax.swing.JButton 重载爆率按钮;
    private javax.swing.JTextField 飞侠力量;
    private javax.swing.JTextField 飞侠攻击力;
    private javax.swing.JTextField 飞侠敏捷;
    private javax.swing.JTextField 飞侠智力;
    private javax.swing.JTextField 飞侠运气;
    // End of variables declaration//GEN-END:variables

    public static GUIPrintStream out = new GUIPrintStream(System.out, output_jTextPane, output_out_jTextPane, GUIPrintStream.OUT);
    public static GUIPrintStream err = new GUIPrintStream(System.err, output_jTextPane, output_err_jTextPane, GUIPrintStream.ERR);
    public static GUIPrintStream notice = new GUIPrintStream(System.out, output_jTextPane, output_notice_jTextPane, GUIPrintStream.NOTICE);
    public static GUIPrintStream packet = new GUIPrintStream(System.err, output_jTextPane, output_packet_jTextPane, GUIPrintStream.PACKET);
}
