package handling.login;

import constants.GameConstants;
import constants.ServerConstants;
import handling.MapleServerHandler;
import handling.netty.ServerConnection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import server.ServerProperties;
import tools.FileoutputUtil;
import tools.Pair;

public class LoginServer {

    public static final int PORT = 8484;//登录端口
    private static Map<Integer, Integer> load = new HashMap<Integer, Integer>();
    private static String serverName, eventMessage, loginMessage;
    private static byte flag;
    private static short maxlevel, kocmaxlevel;
    private static int maxCharacters, userLimit, usersOn = 0, renewPotentiaA, renewPotentiaS, renewPotentialsuperA, renewPotentialsuperS, renewPotentialsuper3, pddy = 1, pdjy = 1, pdjb = 1, lxdy = 1, lxjy = 1, lxjb = 1;
    private static boolean finishedShutdown = true, adminOnly = false, logPackets = false;
    private static HashMap<Integer, Pair<String, String>> loginAuth = new HashMap<Integer, Pair<String, String>>();
    private static HashSet<String> loginIPAuth = new HashSet<String>();
    private static final Map<Integer, String> LoginKey = new HashMap<>();
    private static final Map<Integer, String> ServerKey = new HashMap<>();
    private static final Map<Integer, String> ClientKey = new HashMap<>();
    private static ServerConnection acceptor;
    private static Map<Integer, Pair<Integer, Integer>> chrpos = new HashMap<>();

    public static void putLoginAuth(int chrid, String ip, String tempIP) {
        loginAuth.put(chrid, new Pair<String, String>(ip, tempIP));
        loginIPAuth.add(ip);
    }

    public static Pair<String, String> getLoginAuth(int chrid) {
        return loginAuth.remove(chrid);
    }

    public static boolean containsIPAuth(String ip) {
        return loginIPAuth.contains(ip);
    }

    public static void removeIPAuth(String ip) {
        loginIPAuth.remove(ip);
    }

    public static void addIPAuth(String ip) {
        loginIPAuth.add(ip);
    }

    public static final void addChannel(final int channel) {
        load.put(channel, 0);
    }

    public static final void removeChannel(final int channel) {
        load.remove(channel);
    }

    public static final void run_startup_configurations() {
        userLimit = ServerConstants.LOCALHOST ? 20 : Integer.parseInt(ServerProperties.getProperty("配置.userlimit"));
        serverName = ServerProperties.getProperty("配置.serverName");
        eventMessage = ServerProperties.getProperty("配置.eventMessage");
        flag = Byte.parseByte(ServerProperties.getProperty("配置.flag"));
        adminOnly = Boolean.parseBoolean(ServerProperties.getProperty("配置.admin", "false"));
        logPackets = Boolean.parseBoolean(ServerProperties.getProperty("配置.logpackets", "false"));
        maxCharacters = Integer.parseInt(ServerProperties.getProperty("配置.maxCharacters"));
        renewPotentiaA = Integer.parseInt(ServerProperties.getProperty("配置.renewPotentiaA"));
        renewPotentiaS = Integer.parseInt(ServerProperties.getProperty("配置.renewPotentiaS"));
        renewPotentialsuperA = Integer.parseInt(ServerProperties.getProperty("配置.renewPotentialsuperA"));
        renewPotentialsuperS = Integer.parseInt(ServerProperties.getProperty("配置.renewPotentialsuperS"));
        renewPotentialsuper3 = Integer.parseInt(ServerProperties.getProperty("配置.renewPotentialsuper3"));
        maxlevel = Short.parseShort(ServerProperties.getProperty("配置.maxlevel"));
        kocmaxlevel = Short.parseShort(ServerProperties.getProperty("配置.kocmaxlevel"));
        pddy = Integer.parseInt(ServerProperties.getProperty("配置.pddy"));
        pdjb = Integer.parseInt(ServerProperties.getProperty("配置.pdjb"));
        pdjy = Integer.parseInt(ServerProperties.getProperty("配置.pdjy"));
        lxdy = Integer.parseInt(ServerProperties.getProperty("配置.lxdy"));
        lxjb = Integer.parseInt(ServerProperties.getProperty("配置.lxjb"));
        lxjy = Integer.parseInt(ServerProperties.getProperty("配置.lxjy"));
        loginMessage = ServerProperties.getProperty("配置.loginMessage");

        try {
            acceptor = new ServerConnection(PORT, 0, -1, false);
            acceptor.run();
            System.out.println("Listening on port " + PORT + ".");
        } catch (Exception e) {
            System.err.println("Binding to port " + PORT + " failed" + e);
        }
    }

    public static final void shutdown() {
        if (finishedShutdown) {
            return;
        }
        System.out.println("Shutting down login...");
        acceptor.close();
        finishedShutdown = true; //nothing. lol
    }

    public static final String getServerName() {
        return serverName;
    }

    public static final String getLoginMessage() {
        return loginMessage;
    }

    public static final String getTrueServerName() {
        return serverName.substring(0, serverName.length() - (GameConstants.GMS ? 2 : 3));
    }

    public static final String getEventMessage() {
        return eventMessage;
    }

    public static final byte getFlag() {
        return flag;
    }

    public static final int getMaxCharacters() {
        return maxCharacters;
    }

    public static final int getRenewPotentiaA() {
        return renewPotentiaA;
    }

    public static final int getRenewPotentiaS() {
        return renewPotentiaS;
    }

    public static final int getRenewPotentialsuperA() {
        return renewPotentialsuperA;
    }

    public static final int getRenewPotentialsuperS() {
        return renewPotentialsuperS;
    }

    public static final int getRenewPotentialsuper3() {
        return renewPotentialsuper3;
    }

    public static final int getMaxLevel() {
        return maxlevel;
    }

    public static final int getKocMaxLevel() {
        return kocmaxlevel;
    }

    public static final Map<Integer, Integer> getLoad() {
        return load;
    }

    public static void setLoad(final Map<Integer, Integer> load_, final int usersOn_) {
        load = load_;
        usersOn = usersOn_;
    }

    public static final void setEventMessage(final String newMessage) {
        eventMessage = newMessage;
    }

    public static final void setFlag(final byte newflag) {
        flag = newflag;
    }

    public static final int getUserLimit() {
        return userLimit;
    }

    public static final int getUsersOn() {
        return usersOn;
    }

    public static final void setUserLimit(final int newLimit) {
        userLimit = newLimit;
    }

    public static final boolean isAdminOnly() {
        return adminOnly;
    }

    public static final void setAdminOnly(final boolean newadminOnly) {
        adminOnly = newadminOnly;
    }

    public static final boolean isShutdown() {
        return finishedShutdown;
    }

    public static final void setOn() {
        finishedShutdown = false;
    }

    public static final boolean isLogPackets() {
        return logPackets;
    }

    public static final void setLogPackets(final boolean newLogPackets) {
        logPackets = newLogPackets;
    }

    public static boolean RemoveLoginKey(int AccID) {
        try {
            LoginKey.remove(AccID);
        } catch (Exception ex) {
            FileoutputUtil.outError("logs/移除Key异常.txt", ex);
        }
        return true;
    }

    public static boolean addLoginKey(String key, int AccID) {
        try {
            LoginKey.put(AccID, key);
        } catch (Exception ex) {
            FileoutputUtil.outError("logs/添加Key异常.txt", ex);
        }
        return true;
    }

    public static boolean CanLoginKey(String key, int AccID) {
        if (LoginKey.get(AccID) == null) {
            return true;
        }
        if (LoginKey.containsValue(key)) {
            if (LoginKey.get(AccID).equals(key)) {
                return true;
            }
        }
        return false;
    }

    public static String getLoginKey(int AccID) {
        return LoginKey.get(AccID);
    }

    public static boolean RemoveServerKey(int AccID) {
        try {
            ServerKey.remove(AccID);
        } catch (Exception ex) {
            FileoutputUtil.outError("logs/移除Key异常.txt", ex);
        }
        return true;
    }

    public static boolean addServerKey(String key, int AccID) {
        try {
            ServerKey.put(AccID, key);
        } catch (Exception ex) {
            FileoutputUtil.outError("logs/添加Key异常.txt", ex);
        }
        return true;
    }

    public static boolean CanServerKey(String key, int AccID) {
        if (ServerKey.get(AccID) == null) {
            return true;
        }
        if (ServerKey.containsValue(key)) {
            if (ServerKey.get(AccID).equals(key)) {
                return true;
            }
        }
        return false;
    }

    public static String getServerKey(int AccID) {
        return ServerKey.get(AccID);
    }

    public static boolean RemoveClientKey(int AccID) {
        try {
            ClientKey.remove(AccID);
        } catch (Exception ex) {
            FileoutputUtil.outError("logs/移除Key异常.txt", ex);
        }
        return true;
    }

    public static boolean addClientKey(String key, int AccID) {
        try {
            ClientKey.put(AccID, key);
        } catch (Exception ex) {
            FileoutputUtil.outError("logs/添加Key异常.txt", ex);
        }
        return true;
    }

    public static boolean CanClientKey(String key, int AccID) {
        if (ClientKey.get(AccID) == null) {
            return true;
        }
        if (ClientKey.containsValue(key)) {
            if (ClientKey.get(AccID).equals(key)) {
                return true;
            }
        }
        return false;
    }

    public static String getClientKey(int AccID) {
        return ClientKey.get(AccID);
    }

    public static boolean getDiscounied() {
        return ServerConstants.DISCOUNTED;
    }

    public static void setDiscounied(boolean x) {
        ServerConstants.DISCOUNTED = x;
    }

    public static void setChrPos(int keys, int x, int y) {
        chrpos.put(keys, new Pair<>(x, y));
    }

    public static Map<Integer, Pair<Integer, Integer>> getChrPos() {
        return chrpos;
    }

    public static void RemoveChrPos(int chrid) {
        chrpos.remove(chrid);
    }

    public static int getPddy() {
        return pddy;
    }

    public static int getPdjb() {
        return pdjb;
    }

    public static int getPdjy() {
        return pdjy;
    }

    public static int getLxdy() {
        return lxdy;
    }

    public static int getLxjb() {
        return lxjb;
    }

    public static int getLxjy() {
        return lxjy;
    }
}
