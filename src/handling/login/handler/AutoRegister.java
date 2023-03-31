/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handling.login.handler;

import client.LoginCrypto;
import database.DBConPool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;
import tools.FileoutputUtil;

/**
 *
 * @author XiaoMaDengDengWo
 */
public class AutoRegister {

    private static final int ACCOUNTS_PER_IP = 10;
    public static boolean autoRegister = true;//自动注册
    public static boolean success = false;

    public static boolean getAccountExists(String login) {
        boolean accountExists = false;
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT name FROM accounts WHERE name = ?");
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            if (rs.first()) {
                accountExists = true;
            }
        } catch (SQLException ex) {
            FileoutputUtil.outputFileError("logs/数据库异常.txt", ex);
            System.out.println(ex);
        }
        return accountExists;
    }

    public static int getIpExists(String IP) {
        int ret = -1;

        try (Connection con = DBConPool.getInstance().getDataSource().getConnection(); PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM accounts WHERE SessionIP LIKE CONCAT('%', ?, '%')")) {
            ps.setString(1, IP);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ret = rs.getInt(1);
                } else {
                    ret = -1;
                }
                rs.close();
            }
            ps.close();
        } catch (SQLException ex) {
        }
        return ret;
    }

    public static boolean createAccount(String login, String pwd, String eip) {
        String sockAddr = eip;
        if (getIpExists(sockAddr.substring(1, sockAddr.lastIndexOf(':'))) >= ACCOUNTS_PER_IP) {
            return false;
        }
        try (Connection con = DBConPool.getInstance().getDataSource().getConnection()) {
            ResultSet rs;
            try (PreparedStatement ipc = con.prepareStatement("SELECT SessionIP FROM accounts WHERE SessionIP = ?")) {
                ipc.setString(1, sockAddr.substring(1, sockAddr.lastIndexOf(':')));
                rs = ipc.executeQuery();
                if (rs.first() == false || rs.last() == true && rs.getRow() < ACCOUNTS_PER_IP) {
                    try {
                        try (PreparedStatement ps = con.prepareStatement("INSERT INTO accounts (name, password, email, birthday, macs, SessionIP) VALUES (?, ?, ?, ?, ?, ?)")) {
                            ps.setString(1, login);
                            ps.setString(2, LoginCrypto.hexSha1(pwd));
                            ps.setString(3, "autoregister@mail.com");
                            ps.setString(4, "2008-04-07");
                            ps.setString(5, "00-00-00-00-00-00");
                            ps.setString(6, sockAddr.substring(1, sockAddr.lastIndexOf(':')));
                            ps.executeUpdate();
                        }

                        return true;
                    } catch (SQLException ex) {
                        System.out.println(ex);
                    }
                }
            }
            rs.close();
        } catch (SQLException ex) {
            System.out.println(ex);
            FileoutputUtil.outputFileError("logs/数据库异常.txt", ex);
        }
        return false;
    }
}
