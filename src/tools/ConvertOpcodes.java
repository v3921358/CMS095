/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import handling.RecvPacketOpcode;
import handling.SendPacketOpcode;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Itzik
 */
public class ConvertOpcodes {

    public static void main(String[] args) {
        boolean decimal;
        boolean positive;
        String recvopsName;
        String sendopsName;
        Scanner input = new Scanner(System.in);
        if (args != null) {
            try {
                decimal = Boolean.parseBoolean(args[0]);
            } catch (Exception e) {
                decimal = false;
            }
            try {
                positive = Boolean.parseBoolean(args[1]);
            } catch (Exception e) {
                positive = false;
            }
            try {
                recvopsName = args[2] + ".properties";
            } catch (Exception e) {
                recvopsName = "recvops.properties";
            }
            try {
                sendopsName = args[3] + ".properties";
            } catch (Exception e) {
                sendopsName = "sendops.properties";
            }
        } else {
            System.out.println("欢迎使用包头转换器 \r\n你可以选择十六进制或者十进制的包头值, \r\n然后它们会被保存到新的数据中");
            //RecvPacketOpcode.reloadValues();
            //SendPacketOpcode.reloadValues();
            System.out.println("你想转换成多少进制? 16 还是 10?");
            decimal = "10".equals(input.next().toLowerCase());
            System.out.println("输出数据为正序请输入1,其他则为倒序");
            positive = "1".equals(input.next().toLowerCase());
            System.out.println("\r\n输入你要储存的客户端包头值 名字(输入1为recvops): \r\n");
            recvopsName = input.next();
            if (recvopsName.equals("1")) {
                recvopsName = "recvops.properties";
            } else {
                recvopsName += ".properties";
            }
            System.out.println("\r\n输入你要储存的服务端包头值数据名字(输入1为sendops): \r\n");
            sendopsName = input.next();
            if (sendopsName.equals("1")) {
                sendopsName = "sendops.properties";
            } else {
                sendopsName += ".properties";
            }
        }
        StringBuilder sb = new StringBuilder();
        FileOutputStream out;
        try {
         //   RecvPacketOpcode.reloadValues();
            out = new FileOutputStream(recvopsName, false);
            for (RecvPacketOpcode recv : RecvPacketOpcode.values()) {
                if (positive) {
                    sb.append(recv.name()).append(" = ").append(decimal ? recv.getValue() : HexTool.getOpcodeToString(recv.getValue())).append("\r\n");
                } else {
                    sb.insert(0, "\r\n").insert(0, decimal ? recv.getValue() : HexTool.getOpcodeToString(recv.getValue())).insert(0, " = ").insert(0, recv.name());
                }
            }
            out.write(sb.toString().getBytes());
            out.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ConvertOpcodes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConvertOpcodes.class.getName()).log(Level.SEVERE, null, ex);
        }
        sb = new StringBuilder();
        try {
      //      SendPacketOpcode.reloadValues();
            out = new FileOutputStream(sendopsName, false);
            for (SendPacketOpcode send : SendPacketOpcode.values()) {
                if (positive) {
       //             sb.append(send.name()).append(" = ").append(decimal ? send.getValue(false) : HexTool.getOpcodeToString(send.getValue(false))).append("\r\n");
                } else {
        //            sb.insert(0, "\r\n").insert(0, decimal ? send.getValue(false) : HexTool.getOpcodeToString(send.getValue(false))).insert(0, " = ").insert(0, send.name());
                }
            }
            out.write(sb.toString().getBytes());
            out.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ConvertOpcodes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConvertOpcodes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
