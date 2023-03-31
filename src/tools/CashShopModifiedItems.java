/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import database.DBConPool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author wubin
 */
public class CashShopModifiedItems {

    public static void main(String[] args) {
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM cashshop_modified_items");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int item = rs.getInt("itemid");
                int itemd = item / 100000;
                boolean itemb = itemd == 10;
                if (itemb) {
                    try (Connection con1 = DBConPool.getInstance().getDataSource().getConnection()) {
                        PreparedStatement ps1;
                        ps1 = con1.prepareStatement("Update cashshop_modified_items set discount_price = ? Where itemid = ?");
                        ps1.setInt(1, 150);
                        ps1.setInt(2, rs.getInt("itemid"));
                        ps1.execute();
                        ps1.close();
                    } catch (SQLException exw) {
                        FileoutputUtil.outputFileError("logs/数据库异常.txt", exw);
                        exw.printStackTrace();
                    }
                }
            }
        } catch (SQLException ex) {
            FileoutputUtil.outputFileError("logs/数据库异常.txt", ex);
            ex.printStackTrace();
        }
    }
}
