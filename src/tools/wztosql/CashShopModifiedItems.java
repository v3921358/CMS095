/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools.wztosql;

import database.DBConPool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import server.CashItemFactory;
import server.CashItemInfo;
import tools.FileoutputUtil;

/**
 *
 * @author wubin
 */
public class CashShopModifiedItems {

    public static void main(String[] args) {
        System.out.println(0 % 35);

        CashItemFactory.getInstance().initialize();
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {

            PreparedStatement ps = con.prepareStatement("SELECT * FROM cashshop_modified_items");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int sn = rs.getInt("serial");
                int mod = rs.getInt("mod");
                final CashItemInfo itemsn = CashItemFactory.getInstance().getItem(sn);
                int itemid = itemsn.getId();
                //if (itemid / 100000 >= 10 && itemid / 100000 <= 17) {

                if (sn / 100000 != 208) {
                    if (itemid / 100000 != 17) {
                        if (itemid < 2000000) {

                            if (sn < 30000000) {
                                if (mod == 0 && sn / 1000000 == 10) {
                                    System.out.println("拋出");
                                } else {
                                    //if ((sn / 10000 != 207) && (itemid / 100000 != 17) && (itemid < 2000000)) {
                                    //if ((sn / 10000 == 204) || (sn / 10000 == 205) || (sn / 10000 == 207) || (itemid / 10000 == 104) || (itemid / 10000 == 106) || (itemid / 10000 == 108)) {
                                    try (Connection con1 = DBConPool.getInstance().getDataSource().getConnection()) {
                                        PreparedStatement ps1;
                                        ps1 = con1.prepareStatement("Update cashshop_modified_items set discount_price = ? Where serial = ?");
                                        ps1.setInt(1, 50);
                                        ps1.setInt(2, sn);
                                        ps1.execute();
                                        ps1.close();
                                        System.out.println("sn:" + sn + " item:" + itemid);
                                    } catch (SQLException exw) {
                                        FileoutputUtil.outputFileError("logs/数据库异常.txt", exw);
                                        exw.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                }
            }
            System.out.println("完成。");
        } catch (SQLException ex) {
            FileoutputUtil.outputFileError("logs/数据库异常.txt", ex);
            ex.printStackTrace();
        }
    }
}
