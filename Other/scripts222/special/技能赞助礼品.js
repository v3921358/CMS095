load('nashorn:mozilla_compat.js');
importPackage(java.lang);
importPackage(Packages.client);
importPackage(Packages.client.inventory);
importPackage(Packages.constants);
importPackage(Packages.server);
importPackage(Packages.tools);
var 心 = "#fUI/GuildMark.img/Mark/Etc/00009001/14#";
var JD = "#fUI/Basic/BtHide3/mouseOver/0#";

var status = 0;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (status >= 0 && mode == 0) {
            cm.dispose();
            return;
        }
        if (mode == 1)
            status++;
        else
            status--;
        if (cm.getInventory(1).isFull()) {
            cm.sendOk("请保证 #b装备栏#k 至少有2个位置。");
            cm.dispose();
            return;
        } else if (cm.getInventory(2).isFull()) {
            cm.sendOk("请保证 #b消耗栏#k 至少有2个位置。");
            cm.dispose();
            return;
        } else if (cm.getInventory(3).isFull()) {
            cm.sendOk("请保证 #b设置栏#k 至少有2个位置。");
            cm.dispose();
            return;
        } else if (cm.getInventory(4).isFull()) {
            cm.sendOk("请保证 #b其他栏#k 至少有2个位置。");
            cm.dispose();
            return;
        } else if (cm.getInventory(5).isFull()) {
            cm.sendOk("请保证 #b特殊栏#k 至少有2个位置。");
            cm.dispose();
            return;
        }
        if (cm.getBossRank("点券积分", 2) > 0) {
            var 赞助积分 = cm.getBossRank("点券积分", 2);
        } else {
            var 赞助积分 = 0;
        }
        if (status == 0) {
            var txt = ""
            txt += "#e#r\t" + 心 + "\t" + 心 + "  欢迎来到eV.095冒险岛  " + 心 + "\t" + 心 + "\t#k#n\r\n\r\n"
            txt += "#b=================#e [累计赞助奖励] #n==================#k\r\n\\r\n";
            txt += "\t#d感谢您对eV.冒险岛的支持，所有 赞助皆可获得 赞助积分，比例为 1 : 1\r\n\r\n";
            txt += "\t#d当赞助积分累积达到要求后可以领取对应的赞助奖励噢~\r\n\r\n";
            txt += "\t当前累积的赞助积分为：#r" + 赞助积分 + "#k#l	\r\n\r\n";
            txt += "#L0#Cdk码兑换#l\r\n\r\n";
            txt += "#L1#" + JD + "领取#v1002850##z1002850# (积分达到5积分)\r\n\r\n";
            txt += "#L2#" + JD + "领取#v1112919##z1112919##l\r\n\r\n\t#r四属性+10  攻击魔力+5#k  ( 赞助积分达到50)\r\n\r\n";
            txt += "#L3#" + JD + "强化#v1032024##z1032024##l\r\n\r\n\t#r四属性+10 攻击魔力+5#k ( 赞助积分达到100)\r\n\r\n";
            txt += "#L4#" + JD + "强化#v1022079##z1022079##l\r\n\r\n\t#r四属性+10 攻击魔力+5#k ( 赞助积分达到150)\r\n\r\n";
            txt += "#L5#" + JD + "强化#v1012057##z1012057##l\r\n\r\n\t#r四属性+10 攻击魔力+5#k ( 赞助积分达到200)\r\n\r\n";
            //txt += "#L6#"+JD+"领取#v1003843##z1003843# ( 赞助积分达到500)#l\r\n\r\n";
            txt += "  "
            cm.sendSimple("" + txt + "");
        } else if (status == 1) {
            if (selection == 0) {
                cm.dispose();
                cm.openNpc(9900004, "CDK兑换");
            } else if (selection == 1) {
                if (赞助积分 >= 5 && cm.getPlayer().getBossLogS("充值奖励1") == 0) {
                      cm.gainItemB(1142990, 1, 0, 0, 0, "", 5000, 5000, 10000, 10000, 1000, 1000, 0);
                    cm.sendOk("领取成功！");
                    cm.setBossLog("充值奖励1");
                    cm.dispose();
                } else {
                    cm.sendOk("您的积分不足,或者您已经领取过该奖励！");
                    cm.dispose();
                }
            } else if (selection == 2) {
                if (赞助积分 >= 50 && cm.getPlayer().getBossLogS("充值奖励2") == 0) {
                    cm.gainItemB(1142990, 1, 0, 0, 0, "", 500, 500, 1000, 1000, 1000, 1000, 0);
                    cm.sendOk("领取成功！");
                    cm.setBossLog("充值奖励2");
                    cm.dispose();
                } else {
                    cm.sendOk("您的积分不足,或者您已经领取过该奖励！");
                    cm.dispose();
                }
            } else if (selection == 3) {
                if (赞助积分 >= 100 && cm.getPlayer().getBossLogS("充值奖励3") == 0) {
                    if (cm.getInventory(1).getItem(1) == null) {
                        cm.sendOk("你的装备栏第一格没有装备。");
                        cm.dispose();
                        return
                    }
                    if (cm.getInventory(1).getItem(1).getItemId() != 1032024) {
                        cm.sendOk("你的装备栏第一格不是#v1032024##z1032024#。");
                        cm.dispose();
                        return
                    }
                    var statup = new java.util.ArrayList();
                    var itemId1 = cm.getInventory(1).getItem(1).getItemId();
                    var item = cm.getInventory(1).getItem(1).copy();
                    var type = GameConstants.getInventoryType(itemId1);
                    var sx0 = item.getStr(); //获取装备当前力量0
                    var sx1 = item.getDex(); //获取装备当前敏捷1
                    var sx2 = item.getInt(); //获取装备当前智力2
                    var sx3 = item.getLuk(); //获取装备当前运气3
                    var sx4 = item.getWatk(); //获取装备当前物攻6
                    var sx5 = item.getMatk(); //获取装备当前魔攻7
                    //item.setFlag(1);//上锁
                    item.setStr(sx0 + 10);
                    item.setDex(sx1 + 10);
                    item.setInt(sx2 + 10);
                    item.setLuk(sx3 + 10);
                    item.setWatk(sx5 + 5);
                    item.setMatk(sx5 + 5);
                    MapleInventoryManipulator.removeFromSlot(cm.getC(), type, 1, 1, false);
                    MapleInventoryManipulator.addFromDrop(cm.getC(), item, false);
                    cm.sendOk("强化成功！");
                    cm.setBossLog("充值奖励3");
                    cm.dispose();
                } else {
                    cm.sendOk("您的积分不足,或者您已经领取过该奖励！");
                    cm.dispose();
                }
            } else if (selection == 4) {
                if (赞助积分 >= 150 && cm.getPlayer().getBossLogS("充值奖励4") == 0) {
                    if (cm.getInventory(1).getItem(1) == null) {
                        cm.sendOk("你的装备栏第一格没有装备。");
                        cm.dispose();
                        return
                    }
                    if (cm.getInventory(1).getItem(1).getItemId() != 1022079) {
                        cm.sendOk("你的装备栏第一格不是#v1022079##z1022079#。");
                        cm.dispose();
                        return
                    }
                    var statup = new java.util.ArrayList();
                    var itemId1 = cm.getInventory(1).getItem(1).getItemId();
                    var item = cm.getInventory(1).getItem(1).copy();
                    var type = GameConstants.getInventoryType(itemId1);
                    var sx0 = item.getStr(); //获取装备当前力量0
                    var sx1 = item.getDex(); //获取装备当前敏捷1
                    var sx2 = item.getInt(); //获取装备当前智力2
                    var sx3 = item.getLuk(); //获取装备当前运气3
                    var sx4 = item.getWatk(); //获取装备当前物攻6
                    var sx5 = item.getMatk(); //获取装备当前魔攻7
                    //item.setFlag(1);//上锁
                    item.setStr(sx0 + 10);
                    item.setDex(sx1 + 10);
                    item.setInt(sx2 + 10);
                    item.setLuk(sx3 + 10);
                    item.setWatk(sx5 + 5);
                    item.setMatk(sx5 + 5);
                    MapleInventoryManipulator.removeFromSlot(cm.getC(), type, 1, 1, false);
                    MapleInventoryManipulator.addFromDrop(cm.getC(), item, false);
                    cm.sendOk("强化成功！");
                    cm.setBossLog("充值奖励4");
                    cm.dispose();
                } else {
                    cm.sendOk("您的积分不足,或者您已经领取过该奖励！");
                    cm.dispose();
                }
            } else if (selection == 5) {
                if (赞助积分 >= 200 && cm.getPlayer().getBossLogS("充值奖励5") == 0) {
                    if (cm.getInventory(1).getItem(1) == null) {
                        cm.sendOk("你的装备栏第一格没有装备。");
                        cm.dispose();
                        return
                    }
                    if (cm.getInventory(1).getItem(1).getItemId() != 1012057) {
                        cm.sendOk("你的装备栏第一格不是#v1012057##z1012057#。");
                        cm.dispose();
                        return
                    }
                    var statup = new java.util.ArrayList();
                    var itemId1 = cm.getInventory(1).getItem(1).getItemId();
                    var item = cm.getInventory(1).getItem(1).copy();
                    var type = GameConstants.getInventoryType(itemId1);
                    var sx0 = item.getStr(); //获取装备当前力量0
                    var sx1 = item.getDex(); //获取装备当前敏捷1
                    var sx2 = item.getInt(); //获取装备当前智力2
                    var sx3 = item.getLuk(); //获取装备当前运气3
                    var sx4 = item.getWatk(); //获取装备当前物攻6
                    var sx5 = item.getMatk(); //获取装备当前魔攻7
                    //item.setFlag(1);//上锁
                    item.setStr(sx0 + 10);
                    item.setDex(sx1 + 10);
                    item.setInt(sx2 + 10);
                    item.setLuk(sx3 + 10);
                    item.setWatk(sx5 + 5);
                    item.setMatk(sx5 + 5);
                    MapleInventoryManipulator.removeFromSlot(cm.getC(), type, 1, 1, false);
                    MapleInventoryManipulator.addFromDrop(cm.getC(), item, false);
                    cm.sendOk("强化成功！");
                    cm.setBossLog("充值奖励5");
                    cm.dispose();
                } else {
                    cm.sendOk("您的积分不足,或者您已经领取过该奖励！");
                    cm.dispose();
                }
            }
        }
    }
}