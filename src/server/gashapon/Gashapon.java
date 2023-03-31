/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.gashapon;

import client.MapleCharacter;
import database.DBConPool;
import handling.world.World;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import server.MapleItemInformationProvider;
import tools.FilePrinter;
import tools.FileoutputUtil;
import tools.Pair;
import tools.Randomizer;
import tools.packet.MaplePacketCreator;

public final class Gashapon {

    private final int id;
    private final int npcId;
    private final String name;
    private final List<Pair<Long, GashaponReward>> items = new LinkedList<>();

    public Gashapon(final int id, final int npcId, final String name) {
        this.id = id;
        this.npcId = npcId;
        this.name = name;
        this.reloadItems();
    }

    public int getId() {
        return this.id;
    }

    public int getNpcId() {
        return this.npcId;
    }

    public String getName() {
        return this.name;
    }

    public GashaponReward generateReward() {
        if (this.items.isEmpty()) {
            this.reloadItems();
        }
        Iterator<Pair<Long, GashaponReward>> iterator = this.items.iterator();

        long total = items.get(items.size() - 1).left;

        Long n = Math.abs(Randomizer.nextLong() * System.currentTimeMillis() + 47 * System.currentTimeMillis()) % total;

        while (iterator.hasNext()) {
            Pair<Long, GashaponReward> c = iterator.next();
            if (n <= c.left) {
                return c.right;
            }
        }

        return null;
    }

    public void reloadItems() {

        long chanceTotal = 0L;
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection(); PreparedStatement ps = con.prepareStatement("SELECT * FROM gashapon_items WHERE gashaponsid = ? ORDER BY itemid ASC")) {
            ps.setInt(1, getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                GashaponReward reward = new GashaponReward(rs.getInt("itemid"), rs.getInt("chance"), rs.getBoolean("showmsg"));
                chanceTotal += reward.getChance();
                this.items.add(new Pair<>(chanceTotal, reward));
            }
        } catch (SQLException ex) {
            FilePrinter.printError("Gashapon.txt", ex, "reloadItems");
        }

    }

    public String ShowItem(String type) {
        StringBuilder sb = new StringBuilder();
        Iterator<Pair<Long, GashaponReward>> iterator = this.items.iterator();
        sb.append("#b此轉蛋機物品有:\r\n");
        while (iterator.hasNext()) {
            Pair<Long, GashaponReward> c = iterator.next();
//            System.out.println(c.right.getItemId());            
            if (MapleItemInformationProvider.getInstance().itemExists(c.right.getItemId()) && c.right.getChance() > 0) {
                switch (type) {
                    case "1":
                        sb.append("#v" + c.right.getItemId() + "#");
                        break;
                    case "2":
                        sb.append("#v" + c.right.getItemId() + "#  道具名稱: #z" + c.right.getItemId() + "#\r\n");
                        break;
                    case "GM":
                        sb.append("#L" + c.right.getItemId() + "##v" + c.right.getItemId() + "##z" + c.right.getItemId() + "# 機率:" + c.right.getChance() + "(点選更改)\r\n");
                        break;
                    default:
                        sb.append("指定显示型態错误!");
                        break;
                }
            }
        }
        if (type == "GM") {
            sb.append("\r\n#b#L10000#我要新增轉蛋機物品!!#l\r\n");
        }
        return sb.toString();
    }

    public String ShowItem_GM() {
        StringBuilder sb = new StringBuilder();
        Iterator<Pair<Long, GashaponReward>> iterator = this.items.iterator();
        sb.append("#b此轉蛋機物品有:\r\n");
        while (iterator.hasNext()) {
            Pair<Long, GashaponReward> c = iterator.next();
//            System.out.println(c.right.getItemId());            
            if (MapleItemInformationProvider.getInstance().itemExists(c.right.getItemId())) {
                sb.append("#L" + c.right.getItemId() + "##v" + c.right.getItemId() + "##z" + c.right.getItemId() + "# 機率:" + c.right.getChance() + "(点選更改)\r\n");
            }
        }
        return sb.toString();
    }
// 更改轉蛋物機率    

    public void ChangeChance(MapleCharacter chr, int itemid, int chance) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection(); PreparedStatement ps = con.prepareStatement("UPDATE gashapon_items SET chance = ? WHERE gashaponsid = ? AND itemid = ?")) {
            ps.setInt(1, chance);
            ps.setInt(2, getId());
            ps.setInt(3, itemid);
//                System.out.println(chance+" "+getId()+" "+itemid);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error GashaponChance" + ex);
            FilePrinter.printError("GashaponChance.txt", ex, "GashaponChance has SQL Exception");
        }
        World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 聊天系統] " + chr.getName() + "  更改物品:" + MapleItemInformationProvider.getInstance().getName(itemid) + " 機率更改为" + chance));
        FileoutputUtil.logToFile("logs/GM_LOG/GM更改轉蛋物機率.txt", "\r\n" + FileoutputUtil.NowTime() + "GM: " + chr.getName() + "  更改物品:" + MapleItemInformationProvider.getInstance().getName(itemid) + " 機率更改为" + chance);

    }
// 新增轉蛋物品    

    public void AddItem(MapleCharacter chr, int itemid, int chance, boolean msg) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection(); PreparedStatement ps = con.prepareStatement("INSERT INTO gashapon_items SET chance = ? , gashaponsid = ? , itemid = ? , name = ?, showmsg = ?")) {
            ps.setInt(1, chance);
            ps.setInt(2, getId());
            ps.setInt(3, itemid);
            ps.setString(4, MapleItemInformationProvider.getInstance().getName(itemid));
            ps.setInt(5, (msg ? 1 : 0));
//                System.out.println(chance+" "+getId()+" "+itemid);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error GashaponAddItem" + ex);
            FilePrinter.printError("GashaponAddItem.txt", ex, "GashaponAddItem has SQL Exception");
        }
        World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "[GM 聊天系統] " + chr.getName() + "  新增轉蛋物品:" + MapleItemInformationProvider.getInstance().getName(itemid) + " 機率設定为" + chance + " 是否上綠廣:" + (msg ? "是" : "否")));
        FileoutputUtil.logToFile("logs/GM_LOG/GM新增轉蛋物機率.txt", "\r\n" + FileoutputUtil.NowTime() + "GM: " + chr.getName() + "  新增轉蛋物品:" + MapleItemInformationProvider.getInstance().getName(itemid) + " 機率設定为" + chance + " 是否上綠廣:" + (msg ? "是" : "否"));

    }

}
