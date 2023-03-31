package handling.login;

import client.MapleClient;
import constants.ServerConstants;
import handling.channel.ChannelServer;
import handling.login.handler.CharLoginHandler;
import java.util.Map;
import java.util.Map.Entry;
import server.Timer.PingTimer;
import tools.packet.MaplePacketCreator;
import tools.packet.LoginPacket;

public class LoginWorker {

    private static long lastUpdate = 0;

    public static void registerClient(final MapleClient c) {
        if (LoginServer.isAdminOnly() && !c.isGm() && !c.isLocalhost()) {
            c.getSession().write(MaplePacketCreator.serverNotice(1, "服务器目前正在維修中.\r\n目前管理員正在測試物品.\r\n请稍后等待維修。"));
            c.getSession().write(LoginPacket.getLoginFailed(7));
            return;
        }
        if (LoginServer.isAdminOnly() && !c.isGm()) {
            c.getSession().write(MaplePacketCreator.serverNotice(1, "服务器目前正在維修中.\r\n目前管理員正在測試物品.\r\n请稍后等待維修。"));
            c.getSession().write(LoginPacket.getLoginFailed(7));
            return;
        }

        int totalOnline = 0;
        /*服务器总人数*/
        for (ChannelServer cserv : ChannelServer.getAllInstances()) {
            totalOnline += cserv.getConnectedClients();
        }
        //if (totalOnline > ServerConstants.userlimit) {
       //     c.getSession().write(MaplePacketCreator.serverNotice(1, "当前服务端为单機版.\r\n已达到人數上线.\r\n如需购买商业版.\r\n聯繫QQ"));
       //     c.getSession().write(LoginPacket.getLoginFailed(7));
       //     return;
       // }

        if (System.currentTimeMillis() - lastUpdate > 600000) { // Update once every 10 minutes
            lastUpdate = System.currentTimeMillis();
            final Map<Integer, Integer> load = ChannelServer.getChannelLoad();
            int usersOn = 0;
            if (load == null || load.size() <= 0) { // In an unfortunate event that client logged in before load
                lastUpdate = 0;
                c.getSession().write(LoginPacket.getLoginFailed(7));
                return;
            }
            final double loadFactor = 1200 / ((double) /*LoginServer.getUserLimit()*/ 600 / load.size());
            for (Entry<Integer, Integer> entry : load.entrySet()) {
                usersOn += entry.getValue();
                load.put(entry.getKey(), Math.min(1200, (int) (entry.getValue() * loadFactor)));
            }
            LoginServer.setLoad(load, usersOn);
            lastUpdate = System.currentTimeMillis();
        }

        if (c.finishLogin() == 0) {
            c.getSession().write(LoginPacket.getAuthSuccessRequest(c));
            CharLoginHandler.ServerListRequest(c);

            c.setIdleTask(PingTimer.getInstance().schedule(new Runnable() {

                public void run() {
                    c.getSession().close();
                }
            }, 10 * 60 * 10000));
        } else {
            c.getSession().write(LoginPacket.getLoginFailed(7));
            return;
        }
    }
}
