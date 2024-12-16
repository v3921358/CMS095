package server;

import client.MapleCharacter;
import client.SkillFactory;
import client.inventory.MapleInventoryIdentifier;
import constants.ServerConstants;
import database.DBConPool;
import gui.ZeroMS_UI;
import handling.MapleServerHandler;
import handling.cashshop.CashShopServer;
import handling.channel.ChannelServer;
import handling.channel.MapleGuildRanking;
import handling.login.LoginInformationProvider;
import handling.login.LoginServer;
import handling.world.World;
import handling.world.family.MapleFamily;
import handling.world.guild.MapleGuild;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import server.Timer.BuffTimer;
import server.Timer.CheatTimer;
import server.Timer.CloneTimer;
import server.Timer.EtcTimer;
import server.Timer.EventTimer;
import server.Timer.MapTimer;
import server.Timer.PingTimer;
import server.Timer.WorldTimer;
import server.events.MapleOxQuizFactory;
import server.gashapon.GashaponFactory;
import server.life.MapleLifeFactory;
import server.life.MapleMonsterInformationProvider;
import server.life.MobSkillFactory;
import server.life.PlayerNPC;
import server.maps.MapleMapFactory;
import server.quest.MapleQuest;
import tools.FileoutputUtil;
import static tools.FileoutputUtil.CurrentReadable_Time;

import tools.packet.MaplePacketCreator;

public class Start {

    public static void main(final String[] args) throws InterruptedException {
        ZeroMS_UI.main(args);
    }

    public static long startTime = System.currentTimeMillis();

    public static void run() {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            final PreparedStatement ps = con.prepareStatement("UPDATE accounts SET loggedin = 0");
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", ex);
            throw new RuntimeException("[EXCEPTION] Please check if the SQL server is active.");
        }

        //维护刷新技术村疲劳
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            final PreparedStatement ps = con.prepareStatement("UPDATE characters SET fatigue = 0");
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            FileoutputUtil.outError("logs/数据库异常.txt", ex);
            System.out.println(ex);
        }

        if (Boolean.parseBoolean(ServerProperties.getProperty("windyboy.cms095.admin")) || ServerConstants.Use_Localhost) {
            ServerConstants.Use_Fixed_IV = false;
            System.out.println("[--- Admin Mode Active ---]");
        }

        if (Boolean.parseBoolean(ServerProperties.getProperty("windyboy.cms095.logpackets"))) {
            System.out.println("[--- Logging Packets ---]");
        }

        System.out.println("[" + ServerProperties.getProperty("windyboy.cms095.serverName") + "]");
        System.out.println("[2022年虎年大吉 - 傻逼疯神]");//Maple Trade System is disable
        World.init();
        WorldTimer.getInstance().start();
        EtcTimer.getInstance().start();
        MapTimer.getInstance().start();
        CloneTimer.getInstance().start();
        EventTimer.getInstance().start();
        BuffTimer.getInstance().start();
        PingTimer.getInstance().start();
        System.out.println("载入公会 :::");
        MapleGuildRanking.getInstance().load();
        MapleGuild.loadAll(); //(this);
        System.out.println("载入家族 :::");
        MapleFamily.loadAll(); //(this);
        System.out.println("载入生活技能 :::");
        MapleLifeFactory.loadQuestCounts();
        System.out.println("载入任务 :::");
        MapleQuest.initQuests();
        System.out.println("载入游戏信息 :::");
        MapleItemInformationProvider.getInstance().runEtc();
        System.out.println("载入怪物信息 :::");
        MapleMonsterInformationProvider.getInstance().load();
        System.out.println("载入道具 :::");
        MapleItemInformationProvider.getInstance().runItems();
        System.out.println("载入技能 :::");
        try {
            SkillFactory.load();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("载入登录 :::");
        LoginInformationProvider.getInstance();
        System.out.println("载入随机奖励 :::");
        RandomRewards.load();
        System.out.println("载入扭蛋 :::");
        GashaponFactory.getInstance().reloadGashapons();
        System.out.println("载入ox活动 :::");
        MapleOxQuizFactory.getInstance();
        System.out.println("载入活动 :::");
        MapleCarnivalFactory.getInstance();
        System.out.println("载入怪物技能 :::");
        MobSkillFactory.getInstance();
        System.out.println("载入竞速 :::");
        SpeedRunner.loadSpeedRuns();
        System.out.println("Loading MapleInventoryIdentifier :::");
        MapleInventoryIdentifier.getInstance();
        System.out.println("载入商城道具 :::");
        MapleMapFactory.loadCustomLife();
        CashItemFactory.getInstance().initialize();
//          System.out.println("Successfully loaded all data :::");
        MapleServerHandler.initiate();
        System.out.println("[Loading Login :::]");
        LoginServer.run_startup_configurations();
//          System.out.println("[Login Initialized]");

        System.out.println("[Loading Channel :::]");
        ChannelServer.startChannel_Main();
//          System.out.println("[Channel Initialized]");

        System.out.println("[Loading Cash Shop :::]");
        CashShopServer.run_startup_configurations();
        //      System.out.println("[CS Initialized]");

        CheatTimer.getInstance().register(AutobanManager.getInstance(), 60000);
        World.registerRespawn();
        ShutdownServer.registerMBean();
        ServerConstants.registerMBean();
        PlayerNPC.loadAll();// touch - so we see database problems early...
        MapleMonsterInformationProvider.getInstance().addExtra();
        World.GainNX(5);// 每六十分鐘自動給点數
        World.GainLX(5);
        World.GainZx(1);

        公告(15);
        savePlayerToDB(1);//每分钟存储一次角色信息
        saveMerchantToDB(60*48);//雇佣关闭
        回收内存(60);
        //World.YellowMsg(1);
        //World.cacheTimer();//雙倍時间，每天12点重置。
        //World.AutoSave(5);
        //World.GainMob(60);
        LoginServer.setOn(); //now or later
        RankingWorker.run();
        //LoginServer.setAdminOnly(true);
        System.out.println("[服务端开启时间： " + ((System.currentTimeMillis() - startTime) / 1000) + " seconds]");

    }

    public static class Shutdown implements Runnable {

        @Override
        public void run() {
            ShutdownServer.getInstance().run();
            ShutdownServer.getInstance().run();
        }
    }

    public static void 公告(final int time) {
        Timer.WorldTimer.getInstance().register(new Runnable() {
            @Override
            public void run() {
                String 公告 = 本地取广播();
                if (!"".equals(公告)) {
                    World.Broadcast.broadcastMessage(MaplePacketCreator.yellowChat("[" + LoginServer.getServerName() + " 帮助] " + 公告));
                }
            }
        }, time * 1000 * 60);
    }
    //PPMS
    public static void savePlayerToDB(final int time) {
        Timer.WorldTimer.getInstance().register(new Runnable() {
            @Override
            public void run() {
                for (ChannelServer cserv : ChannelServer.getAllInstances()) {
                    for (MapleCharacter chr : cserv.getPlayerStorage().getAllCharacters()) {
                        chr.saveToDB(true, true);
                    }
                }
            }
        }, time * 1000 * 60);
    }
    
    //PPMS
    public static void saveMerchantToDB(final int time) {
        Timer.WorldTimer.getInstance().register(new Runnable() {
            @Override
            public void run() {
                int p = 0;
                for (handling.channel.ChannelServer cserv : handling.channel.ChannelServer.getAllInstances()) {
                    p++;
                    cserv.closeAllMerchant();
                }
            }
        }, time * 1000 * 60);
    }
    
    
    private static int 本地取广播 = 0;

    public static String 本地取广播() {

        String data = "";
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM `广播信息` ORDER BY RAND() LIMIT 0,100;  ");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                data = rs.getString("广播");
            }
            if (本地取广播 > 0) {
                System.err.println("[服务端]" + CurrentReadable_Time() + " : 服务端输出循环广播 √");
            } else {
                本地取广播++;
            }
            ps.close();
        } catch (SQLException ex) {
            System.err.println("[服务端]" + CurrentReadable_Time() + " : 服务端输出循环广播 ×");
        }
        return data;
    }

    public static void 回收内存(final int time) {
        Timer.WorldTimer.getInstance().register(new Runnable() {
            @Override
            public void run() {
                
                System.gc();
                MapleMonsterInformationProvider.getInstance().clearDrops();
                System.out.println("[回收内存]已启用，每" + time + "分钟清理系统内存。");
            }
        }, 60000 * time);
    }

    
    
    public static void GainCz(int min) {

        Timer.EventTimer.getInstance().register(new Runnable() {
            @Override
            public void run() {

                int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                int minute = Calendar.getInstance().get(Calendar.MINUTE);
                if (hour == 0 && minute == 0) {
                    for (ChannelServer cs : ChannelServer.getAllInstances()) {
                        for (MapleCharacter chr : cs.getPlayerStorage().getAllCharacters()) {
                            chr.setTodayOnlineTime(0);
                        }
                    }
                    try (Connection con1 = DBConPool.getInstance().getDataSource().getConnection(); PreparedStatement ps1 = con1.prepareStatement("UPDATE characters SET todayOnlineTime = 0")) {
                        ps1.executeUpdate();
                    } catch (SQLException ex) {
                        FileoutputUtil.outError("logs/数据库异常.txt", ex);
                    }

                }
            }
        }, min * 60 * 1000, min * 60 * 1000);
    }
}
