package server;

import handling.cashshop.CashShopServer;
import handling.channel.ChannelServer;
import handling.login.LoginServer;
import handling.world.World;
import java.lang.management.ManagementFactory;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import server.Timer.BuffTimer;
import server.Timer.CloneTimer;
import server.Timer.EtcTimer;
import server.Timer.EventTimer;
import server.Timer.MapTimer;
import server.Timer.PingTimer;
import server.Timer.WorldTimer;
import server.shops.HiredMerchantSave;
import tools.FileoutputUtil;
import tools.packet.MaplePacketCreator;

public class ShutdownServer implements ShutdownServerMBean {

    public static ShutdownServer instance;
    public int mode = 0;
    protected static Thread t = null;
    

    public static void registerMBean() {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        try {
            instance = new ShutdownServer();
            mBeanServer.registerMBean(instance, new ObjectName("server:type=ShutdownServer"));
        } catch (Exception e) {
            System.out.println("Error registering Shutdown MBean");
            e.printStackTrace();
        }
    }

    public static ShutdownServer getInstance() {
        return instance;
    }

    public void shutdown() {//can execute twice
        run();
    }

    //public void shutdown2() {//can execute twice
    //    if (t == null || !t.isAlive()) {
    //        t = new Thread(ShutdownServer.getInstance());
    //        ShutdownServer.getInstance().shutdown();
    //        t.start();
    //    } else {
    //        System.out.println("已在执行中");
    //    }

   // }

    @Override
    public void run() {
        if (mode == 0) {
            World.isShutDown = true;
            int ret = 0;
            World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(0, "The world is going to shutdown soon. Please log off safely."));
            for (ChannelServer cs : ChannelServer.getAllInstances()) {
                cs.setShutdown();
                cs.setServerMessage("The world is going to shutdown soon. Please log off safely.");
                ret += cs.closeAllMerchant();
            }
            /*AtomicInteger FinishedThreads = new AtomicInteger(0);
            HiredMerchantSave.Execute(this);
            synchronized (this) {
                try {
                    wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(ShutdownServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            while (FinishedThreads.incrementAndGet() != HiredMerchantSave.NumSavingThreads) {
                synchronized (this) {
                    try {
                        wait();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ShutdownServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }*/
            World.Guild.save();
            World.Alliance.save();
            World.Family.save();
            System.out.println("Shutdown 1 has completed. Hired merchants saved: " + ret);
            FileoutputUtil.logToFile("logs/Data/关闭服务端商店保存.txt", "共保存商店: " + ret + "个");
            mode++;
        } else if (mode == 1) {
            mode++;
            System.out.println("Shutdown 2 commencing...");
            try {
                World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(0, "The world is going to shutdown now. Please log off safely."));
                Integer[] chs = ChannelServer.getAllInstance().toArray(new Integer[0]);

                for (int i : chs) {
                    try {
                        ChannelServer cs = ChannelServer.getInstance(i);
                        synchronized (this) {
                            cs.shutdown();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        FileoutputUtil.outError("logs/关闭服务器异常.txt", e);
                    }
                }
                LoginServer.shutdown();
                CashShopServer.shutdown();
            } catch (Exception e) {
                System.err.println("THROW" + e);
                FileoutputUtil.outError("logs/关闭服务器异常.txt", e);
            }
            WorldTimer.getInstance().stop();
            MapTimer.getInstance().stop();
            BuffTimer.getInstance().stop();
            CloneTimer.getInstance().stop();
            EventTimer.getInstance().stop();
            EtcTimer.getInstance().stop();
            PingTimer.getInstance().stop();
            System.out.println("Shutdown 2 has finished.");
            try {
                Thread.sleep(5000);
            } catch (Exception e) {
                 FileoutputUtil.outError("logs/关闭服务器异常.txt", e);
                //shutdown
            }
            System.exit(0); //not sure if this is really needed for ChannelServer
        }
    }
}
