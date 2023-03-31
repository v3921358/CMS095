package constants;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;
import javax.management.MBeanServer;
import javax.management.ObjectName;

public class ServerConstants implements ServerConstantsMBean {

    public static int CRYPT_KEY = 0xE;
    public static boolean loadop = true;
    public static boolean TESPIA = false; // true = uses GMS test server, for MSEA it does nothing though
    public static boolean LOCALHOST = false;
    public static boolean crypta = false;
    public static boolean crypt = false;
    public static int userlimit = 200;
    public static final byte[] Gateway_IP = new byte[]{(byte) 221, (byte) 231, (byte) 130, (byte) 70};
    //public static final byte[] Gateway_IP = new byte[]{(byte) 192, (byte) 168, (byte) 0, (byte) 13};
    //public static String IP = "182.161.43.130";
    //public static String IP = getIP();
    public static String IP = "221.231.130.70";
    //public static String IP = "192.168.1.108";
    //public static String IP = "192.168.0.9";
    public static final short MAPLE_VERSION = (short) 95;
    public static final String MAPLE_PATCH = "1";
    public static final String ASCII = "gbk";
    public static boolean Use_Fixed_IV = false; // true = disable sniffing, false = server can connect to itself
    public static boolean Use_Localhost = false; // true = packets are logged, false = others can connect to server
    public static final String MASTER_LOGIN = "", MASTER = "", MASTER2 = "";
    public static final long number1 = (142449577 + 753356065 + 611816275297389857L);
    public static final long number2 = 1877319832;
    public static final long number3 = 202227478981090217L;
    public static final List<String> eligibleIP = new LinkedList<>(), localhostIP = new LinkedList<>();
    public static boolean logs_chat = true, message_chat = false, logs_mrechant = true, message_mrechant = false, logs_trade = true, message_trade = false, logs_storage = true, message_storage = true, logs_csbuy = true, message_csbuy = false, logs_cube = true, message_cube = false;
    public static boolean LieDetector = false;
    public static boolean message_generalchat = true, message_buddychat = true, message_partychat = true, message_guildchat = true, message_alliancechat = true, message_whisperchat = true, message_tradechat = true;
    public static boolean levelx2 = false;
    public static boolean DISCOUNTED = false;//商城8.8折开關
    public static final boolean MoonlightRevamp = true;
    public static final int monsterSpawn = 8; //怪物加倍
    public static boolean Super_password = false;
    public static String superpw = "";
    

    /*
     * Specifics which job gives an additional EXP to party
     * returns the percentage of EXP to increase
     */
    public static byte Class_Bonus_EXP(final int job) {
        switch (job) {
            case 501:
            case 530:
            case 531:
            case 532:
            case 2300:
            case 2310:
            case 2311:
            case 2312:
            case 3100:
            case 3110:
            case 3111:
            case 3112:
            case 800:
            case 900:
            case 910:
                return 0;
        }
        return 0;
    }

    public static enum PlayerGMRank {
        NORMAL('@', 0),
        DONATOR('!', 1),
        SUPERDONATOR('!', 2),
        INTERN('!', 3),
        GM('!', 4),
        SUPERGM('!', 5),
        ADMIN('!', 6);

        private char commandPrefix;
        private int level;

        PlayerGMRank(char ch, int level) {
            commandPrefix = ch;
            this.level = level;
        }

        public char getCommandPrefix() {
            return commandPrefix;
        }

        public int getLevel() {
            return level;
        }
    }

    public static enum CommandType {
        NORMAL(0),
        TRADE(1);

        private int level;

        CommandType(int level) {
            this.level = level;
        }

        public int getType() {
            return level;
        }
    }

    public static boolean isEligibleMaster(final String pwd, final String sessionIP) {
        return pwd.equals(MASTER) && isEligible(sessionIP);
    }

    public static boolean isEligible(final String sessionIP) {
        return eligibleIP.contains(sessionIP.replace("/", ""));
    }

    public static boolean isEligibleMaster2(final String pwd, final String sessionIP) {
        return pwd.equals(MASTER2) && isEligible(sessionIP);
    }

    public static boolean isIPLocalhost(final String sessionIP) {
        return !Use_Fixed_IV && localhostIP.contains(sessionIP.replace("/", ""));
    }

    static {
        localhostIP.add("203.116.196.8"); // Ares
        localhostIP.add("203.188.239.82"); // Artemis
        localhostIP.add("8.31.98.52");
        localhostIP.add("8.31.98.53");
        localhostIP.add("8.31.98.54");
    }
    public static ServerConstants instance;

    public void run() {
        updateIP();
    }

    public void updateIP() {
        eligibleIP.clear();
        final String[] eligibleIPs = {"203.188.239.82", "127.0.0.1", "203.116.196.8"}; //change IPs here; can be no-ip or just raw address
        for (int i = 0; i < eligibleIPs.length; i++) {
            try {
                eligibleIP.add(InetAddress.getByName(eligibleIPs[i]).getHostAddress().replace("/", ""));
            } catch (Exception e) {
            }
        }
    }

    public static void registerMBean() {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        try {
            instance = new ServerConstants();
            instance.updateIP();
            mBeanServer.registerMBean(instance, new ObjectName("constants:type=ServerConstants"));
        } catch (Exception e) {
            System.out.println("Error registering Shutdown MBean");
            e.printStackTrace();
        }
    }

    public static String getIP() {
        InetAddress ip = null;
        try {
            ip = InetAddress.getByName("rose1234.ddns.net");
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.out.println("getIP_null_null");
        }
        return ip.getHostAddress().toString();
    }
}
