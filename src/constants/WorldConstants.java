/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package constants;

import handling.channel.ChannelServer;
import server.ServerProperties;
import server.Timer;
import tools.packet.MaplePacketCreator;

/**
 *
 * @author XiaoMaDengDengWo
 */
public class WorldConstants {

    public static final int gmserver = -1; // -1 = 无GM服务器

    public static enum WorldOption {

        雪吉拉(0, 1, 1, 1, (byte) 0, true, true, 20),
        姑姑寶貝(1, 1, 1, 1, (byte) 0, false, true, 20),
        星光精灵(2, 1, 1, 1, (byte) 0, false, true, 20),
        緞帶肥肥(3, 1, 1, 1, (byte) 0, false, true, 20),
        藍寶(4, 1, 1, 1, (byte) 0, false, true, 20),
        綠水灵(5, 1, 1, 1, (byte) 0, false, true, 20),
        三眼章魚(6, 1, 1, 1, (byte) 0, false, true, 20),
        木妖(7, 1, 1, 1, (byte) 0, false, true, 20),
        火獨眼獸(8, 1, 1, 1, (byte) 0, false, true, 20),
        蝴蝶精(9, 1, 1, 1, (byte) 0, false, true, 20),
        巴洛古(10, 1, 1, 1, (byte) 0, false, true, 20),
        海怒斯(11, 1, 1, 1, (byte) 0, false, true, 20),
        電擊象(12, 1, 1, 1, (byte) 0, false, true, 20),
        鯨魚號(13, 1, 1, 1, (byte) 0, false, true, 20),
        皮卡啾(14, 1, 1, 1, (byte) 0, false, true, 20),
        神獸(15, 1, 1, 1, (byte) 0, false, true, 20),
        泰勒熊(16, 1, 1, 1, (byte) 0, false, true, 20),
        寒霜冰龍(17, 1, 1, 1, (byte) 0, false, true, 20),;
        private final int world, exp, meso, drop, channels;
        private final byte flag;//1 事件, 2 新, 3 热
        public final boolean show, available;
        public static final byte recommended = (byte) 雪吉拉.getWorld(); //-1 = no recommended
        public static final String recommendedmsg = "        Join " + getById(recommended).name() + ",       the newest world! (If youhave friends who play, consider joining their worldinstead. Characters can`t move between worlds.)";

        WorldOption(int world, byte flag, boolean show, int channels) {
            this.world = world;
            this.exp = 1;
            this.meso = 1;
            this.drop = 1;
            this.flag = flag;
            this.show = show;
            this.available = show;
            this.channels = channels;
        }

        WorldOption(int world, int exp, int meso, int drop, byte flag, boolean show, boolean available, int channels) {
            this.world = world;
            this.exp = exp;
            this.meso = meso;
            this.drop = drop;
            this.flag = flag;
            this.show = show;
            this.available = available;
            this.channels = channels;
        }

        public int getWorld() {
            return world;
        }

        public int getExp() {
            return exp;
        }

        public int getMeso() {
            return meso;
        }

        public int getDrop() {
            return drop;
        }

        public byte getFlag() {
            return flag;
        }

        public boolean show() {
            return show;
        }

        public boolean isAvailable() {
            return available;
        }

        public int getChannelCount() {
            return channels;
        }

        public static WorldOption getById(int g) {
            for (WorldOption e : WorldOption.values()) {
                if (e.world == g) {
                    return e;
                }
            }
            return null;
        }

        public static WorldOption getByName(String g) {
            for (WorldOption e : WorldOption.values()) {
                if (e.toString().equals(g)) {
                    return e;
                }
            }
            return null;
        }

        public static boolean isExists(int id) {
            return getById(id) != null;
        }

        public static String getMainWorld() {
            String mWorld = "";
            for (WorldOption e : WorldOption.values()) {
                if (e.show == true) {
                    mWorld = e.name();
                    break;
                }
            }
            return mWorld;
        }
    }

    public static String getNameById(int serverid) {
        if (!WorldOption.isExists(serverid)) {
            System.out.println("World doesn't exists exception. ID: " + serverid);
            return "";
        }
        return WorldOption.getById(serverid).name();
    }
}
