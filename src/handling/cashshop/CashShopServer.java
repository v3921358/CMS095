package handling.cashshop;

import constants.ServerConstants;
import handling.MapleServerHandler;
import handling.channel.PlayerStorage;
import handling.netty.ServerConnection;
import server.ServerProperties;

public class CashShopServer {

    private static String ip;
    private final static int PORT = (short) 8600;
    private static ServerConnection acceptor;
    private static PlayerStorage players;
    private static boolean finishedShutdown = false;

    public static final void run_startup_configurations() {
        ip = ServerProperties.getProperty("配置.host") + ":" + PORT;

        players = new PlayerStorage(-10);

        try {
            acceptor = new ServerConnection(PORT, 0, -1, true);
            acceptor.run();
            System.out.println("Listening on port " + PORT + ".");
        } catch (final Exception e) {
            System.err.println("Binding to port " + PORT + " failed");
            e.printStackTrace();
            throw new RuntimeException("Binding failed.", e);
        }
    }

    public static final String getIP() {
        return ip;
    }

    public static final PlayerStorage getPlayerStorage() {
        return players;
    }

    public static final void shutdown() {
        if (finishedShutdown) {
            return;
        }
        System.out.println("Saving all connected clients (CS)...");
        players.disconnectAll();
        System.out.println("Shutting down CS...");
        acceptor.close();
        finishedShutdown = true;
    }

    public static boolean isShutdown() {
        return finishedShutdown;
    }
}
