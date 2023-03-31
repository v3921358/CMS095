/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import client.LoginCrypto;
import database.DBConPool;
import handling.world.World;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Administrator
 */
public class inivalue {

    public static void 初始化账号表(int id) {
        int 账号总数 = 0;
        try {
            Connection con = DBConPool.getInstance().getDataSource().getConnection();
            PreparedStatement ps = null;
            ResultSet rs = null;
            if (id > 0) {
                ps = con.prepareStatement("SELECT * FROM accounts where name = '" + ZeroMS_UI.jTextField23.getText() + "'");
            } else {
                ps = con.prepareStatement("SELECT * FROM accounts");
            }
            rs = ps.executeQuery();
            while (rs.next()) {
               String 封号 = "";
                if (rs.getInt("banned") == 0) {
                    封号 = "正常";
                } else {
                    封号 = "封禁";
                }
                String 在线 = "";
                if (rs.getInt("loggedin") == 0) {
                    在线 = "不在线";
                } else {
                    在线 = "在线";
                }
                String QQ = "";
                if (rs.getString("qq") != null) {
                    QQ = rs.getString("qq");
                } else {
                    QQ = "未绑定QQ";
                }
                账号总数++;
                ((DefaultTableModel) ZeroMS_UI.accountstable.getModel()).insertRow(ZeroMS_UI.accountstable.getRowCount(), new Object[]{
                    //ID 账号name 密码password   余额money  积分jf 点券ACash 抵用mPoints 累计赞助ljzz banned封号
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("password"),
                    QQ,//注册时间
                    rs.getInt("ACash"),
                    rs.getInt("mPoints"),
                    封号,
                    在线,
                });
            }
            ZeroMS_UI.账号总数标签.setText(账号总数 + "个账号");
        } catch (SQLException ex) {
            Logger.getLogger(inivalue.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void 账号操作(int accid, int type, boolean mm) {
        //0修改  1删除 2解封
        //传递进来的是ID+type
        if (type == 0) {//修改数据
            String pwd = "";
            try {
                JOptionPane.showMessageDialog(null, "修改成功!");
                pwd = LoginCrypto.hexSha1(ZeroMS_UI.输入1.getText());
                //ID 账号name 密码password   余额money  积分jf 点券ACash 抵用mPoints 累计赞助ljzz banned封号
            Connection con = DBConPool.getInstance().getDataSource().getConnection();
                if (mm == true) {
                    //密码一样的话
                    PreparedStatement ps = con.prepareStatement("UPDATE accounts SET name ='" + ZeroMS_UI.输入0.getText() + "',qq = " + ZeroMS_UI.输入2.getText() + ""
                            + ",ACash = " + ZeroMS_UI.输入3.getText() + ",mPoints = " + ZeroMS_UI.输入4.getText() + ""
                            + " WHERE id = " + accid + "");
                    ps.executeUpdate();

                } else {
                    //密码不一样，需要重新加密啦！
                    PreparedStatement ps = con.prepareStatement("UPDATE accounts SET name ='" + ZeroMS_UI.输入0.getText() + "',password = '" + pwd + "',qq = " + ZeroMS_UI.输入2.getText() + ""
                            + ",ACash = " + ZeroMS_UI.输入3.getText() + ",mPoints = " + ZeroMS_UI.输入4.getText() + ""
                            + " WHERE id = " + accid + "");
                    JOptionPane.showMessageDialog(null, "[账号管理]加密后的新密码：" + ZeroMS_UI.输入1.getText());
                    ps.executeUpdate();
                }
            } catch (SQLException ex) {
                ex.getStackTrace();
            }
        } else if (type == 1) {
            try {
                //1 = 删除账号
            Connection con = DBConPool.getInstance().getDataSource().getConnection();
                PreparedStatement ps = con.prepareStatement("delete from accounts where id = '" + accid + "'");
                JOptionPane.showMessageDialog(null, "[账号管理]删除账号成功：" + ZeroMS_UI.输入0.getText());
                ps.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(inivalue.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (type == 2) {//封号
            String account = ZeroMS_UI.输入0.getText();
            try {
            Connection con = DBConPool.getInstance().getDataSource().getConnection();
            PreparedStatement ps;

            ps = con.prepareStatement("Update accounts set banned = ? Where name = ?");
            ps.setInt(1, 1);
            ps.setString(2, account);
            ps.execute();
            ps.close();
            JOptionPane.showMessageDialog(null, "[账号管理]封停账号成功：" + ZeroMS_UI.输入0.getText());
            } catch (SQLException ex) {
                Logger.getLogger(inivalue.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            } else if (type == 3) {//解封
            String account = ZeroMS_UI.输入0.getText();
            try {
            Connection con = DBConPool.getInstance().getDataSource().getConnection();
            PreparedStatement ps;

            ps = con.prepareStatement("Update accounts set banned = ? Where name = ?");
            ps.setInt(1, 0);
            ps.setString(2, account);
            ps.execute();
            ps.close();
            JOptionPane.showMessageDialog(null, "[账号管理]解封账号成功：" + ZeroMS_UI.输入0.getText());
            } catch (SQLException ex) {
                Logger.getLogger(inivalue.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            } else if (type == 4) {//卡号自救
            String account = ZeroMS_UI.输入0.getText();
            try {
            Connection con = DBConPool.getInstance().getDataSource().getConnection();
            PreparedStatement ps;

            ps = con.prepareStatement("Update accounts set loggedin = ? Where name = ?");
            ps.setInt(1, 0);
            ps.setString(2, account);
            ps.execute();
            ps.close();
            JOptionPane.showMessageDialog(null, "[账号管理]卡号自救成功：" + ZeroMS_UI.输入0.getText());
            } catch (SQLException ex) {
                Logger.getLogger(inivalue.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }



    /*进入修改角色 查询角色*/
    public static void 初始化角色表(int id) {
        int 角色数量 = 0;
        try {
            Connection con = DBConPool.getInstance().getDataSource().getConnection();
            PreparedStatement ps = null;
            ResultSet rs = null;
            if (id > 0) {
                ps = con.prepareStatement("SELECT * FROM characters where name = '" + ZeroMS_UI.jTextField24.getText() + "'");
            } else {
                ps = con.prepareStatement("SELECT * FROM characters");
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                String 在线 = "";
                if (World.Find.findChannel(rs.getString("name")) > 0) {
                    在线 = "在线";
                } else {
                    在线 = "离线";
                }
                角色数量++;
                ((DefaultTableModel) ZeroMS_UI.characterstable.getModel()).insertRow(ZeroMS_UI.characterstable.getRowCount(), new Object[]{
                    //ID 账号name 密码password   余额money  积分jf 点券ACash 抵用mPoints 累计赞助ljzz banned封号
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("level"),
                    rs.getString("str"),
                    rs.getInt("dex"),
                    rs.getInt("luk"),
                    rs.getInt("int"),
                    rs.getInt("maxhp"),
                    rs.getInt("maxmp"),
                    rs.getInt("job"),
                    rs.getInt("meso"),
                    rs.getInt("map"),
                    在线,
                    rs.getInt("gm"),
                    rs.getInt("hair"),
                    rs.getInt("face")
                });
            }
            ZeroMS_UI.角色总数标签.setText(角色数量 + "个角色");
        } catch (SQLException ex) {
            Logger.getLogger(inivalue.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    
    public static void 角色操作(int accid, int type, boolean mm) {
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        if (type == 0) {//修改数据
            if (ZeroMS_UI.角色0.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "角色昵称不能留空");
            return;
        }
        if (World.Find.findChannel(ZeroMS_UI.角色0.getText()) > 0) {
            JOptionPane.showMessageDialog(null, "请先将角色离线后再修改。");
            return;
        }
        try {
            ps = DBConPool.getInstance().getDataSource().getConnection().prepareStatement("UPDATE characters SET (name = ?,level = ?, str = ?, dex = ?, luk = ?,int = ?,  maxhp = ?, maxmp = ?, job = ?, meso = ?, map = ?, gm = ?, hair = ?, face = ? )WHERE id = ?");
            ps1 = DBConPool.getInstance().getDataSource().getConnection().prepareStatement("SELECT * FROM characters WHERE id = ?");
            ps1.setInt(1, Integer.parseInt(ZeroMS_UI.角色ID.getText()));
            rs = ps1.executeQuery();
            if (rs.next()) {
                String sqlString1 = null;
                String sqlString2 = null;
                String sqlString3 = null;
                String sqlString4 = null;
                String sqlString5 = null;
                String sqlString6 = null;
                String sqlString7 = null;
                String sqlString9 = null;
                String sqlString8 = null;
                String sqlString10 = null;
                String sqlString11 = null;
                String sqlString12 = null;
                String sqlString13 = null;
                String sqlString14 = null;
                
                sqlString1 = "update characters set name='" + ZeroMS_UI.角色0.getText() + "' where id=" + ZeroMS_UI.角色ID.getText() + ";";
                PreparedStatement name = DBConPool.getInstance().getDataSource().getConnection().prepareStatement(sqlString1);
                name.executeUpdate(sqlString1);
                sqlString2 = "update characters set level='" + ZeroMS_UI.角色1.getText() + "' where id=" + ZeroMS_UI.角色ID.getText() + ";";
                PreparedStatement level = DBConPool.getInstance().getDataSource().getConnection().prepareStatement(sqlString2);
                level.executeUpdate(sqlString2);

                sqlString3 = "update characters set str='" + ZeroMS_UI.角色2.getText() + "' where id=" + ZeroMS_UI.角色ID.getText() +";";
                PreparedStatement str = DBConPool.getInstance().getDataSource().getConnection().prepareStatement(sqlString3);
                str.executeUpdate(sqlString3);

                sqlString4 = "update characters set dex='" + ZeroMS_UI.角色3.getText() + "' where id=" + ZeroMS_UI.角色ID.getText() + ";";
                PreparedStatement dex = DBConPool.getInstance().getDataSource().getConnection().prepareStatement(sqlString4);
                dex.executeUpdate(sqlString4);

                sqlString5 = "update characters set luk='" + ZeroMS_UI.角色4.getText() + "' where id=" + ZeroMS_UI.角色ID.getText() + ";";
                PreparedStatement luk = DBConPool.getInstance().getDataSource().getConnection().prepareStatement(sqlString5);
                luk.executeUpdate(sqlString5);

                sqlString6 = "update characters set `int`='" + ZeroMS_UI.角色5.getText() + "' where id=" + ZeroMS_UI.角色ID.getText() + ";";
                PreparedStatement executeUpdate = DBConPool.getInstance().getDataSource().getConnection().prepareStatement(sqlString6);
                executeUpdate.executeUpdate(sqlString6);

                sqlString7 = "update characters set maxhp='" + ZeroMS_UI.角色6.getText() + "' where id=" + ZeroMS_UI.角色ID.getText() + ";";
                PreparedStatement maxhp = DBConPool.getInstance().getDataSource().getConnection().prepareStatement(sqlString7);
                maxhp.executeUpdate(sqlString7);

                sqlString8 = "update characters set maxmp='" + ZeroMS_UI.角色7.getText() + "' where id=" + ZeroMS_UI.角色ID.getText() + ";";
                PreparedStatement maxmp = DBConPool.getInstance().getDataSource().getConnection().prepareStatement(sqlString8);
                maxmp.executeUpdate(sqlString8);

                sqlString9 = "update characters set job='" + ZeroMS_UI.角色8.getText() + "' where id=" + ZeroMS_UI.角色ID.getText() + ";";
                PreparedStatement job = DBConPool.getInstance().getDataSource().getConnection().prepareStatement(sqlString9);
                job.executeUpdate(sqlString9);
                
                sqlString10 = "update characters set meso='" + ZeroMS_UI.角色9.getText() + "' where id=" + ZeroMS_UI.角色ID.getText() + ";";
                PreparedStatement meso = DBConPool.getInstance().getDataSource().getConnection().prepareStatement(sqlString10);
                meso.executeUpdate(sqlString10);

                sqlString11 = "update characters set map='" + ZeroMS_UI.角色10.getText() + "' where id=" + ZeroMS_UI.角色ID.getText() +";";
                PreparedStatement map = DBConPool.getInstance().getDataSource().getConnection().prepareStatement(sqlString11);
                map.executeUpdate(sqlString11);

                sqlString12 = "update characters set gm='" + ZeroMS_UI.角色11.getText() + "' where id=" + ZeroMS_UI.角色ID.getText() + ";";
                PreparedStatement gm = DBConPool.getInstance().getDataSource().getConnection().prepareStatement(sqlString12);
                gm.executeUpdate(sqlString12);

                sqlString13 = "update characters set hair='" + ZeroMS_UI.角色12.getText() + "' where id=" + ZeroMS_UI.角色ID.getText() + ";";
                PreparedStatement hair = DBConPool.getInstance().getDataSource().getConnection().prepareStatement(sqlString13);
                hair.executeUpdate(sqlString12);

                sqlString14 = "update characters set face='" + ZeroMS_UI.角色13.getText() + "' where id=" + ZeroMS_UI.角色ID.getText() + ";";
                PreparedStatement face = DBConPool.getInstance().getDataSource().getConnection().prepareStatement(sqlString13);
                face.executeUpdate(sqlString14);
                JOptionPane.showMessageDialog(null, "[信息]:角色信息修改成功。");
            }
        } catch (SQLException ex) {
                Logger.getLogger(inivalue.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
    }       

   public static void 角色删除(int accid, int type, boolean mm) {
               try {
                //1 = 删除角色
            Connection con = DBConPool.getInstance().getDataSource().getConnection();
                PreparedStatement ps = con.prepareStatement("delete from characters where id = '" + accid + "'");
                JOptionPane.showMessageDialog(null, "[账号管理]删除校色成功：" + ZeroMS_UI.角色0.getText());
                ps.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(inivalue.class.getName()).log(Level.SEVERE, null, ex);
            }
         }
    }

