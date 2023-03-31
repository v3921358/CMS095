/*
	
**/
var 心2 = "#fUI/GuildMark.img/Mark/Etc/00009023/1#";
var 奖励 = "#fUI/UIWindow.img/Quest/reward#";
var 人气王 = "#fUI/UIWindow.img/UserList/Expedition/icon12#";
var 五角星 = "#fUI/UIWindow.img/UserList/Expedition/icon14#";
var 红色箭头 = "#fUI/UIWindow/Quest/icon6/7#";
var status = 0;

function start() {
    action(1, 0, 0);

}


function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (mode == 0) {
            cm.dispose();
            return;
        }
        if (mode == 1) {
            status++;
        } else {
            status--;
        }
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
        if (status == 0) {
            var 职业 = cm.getPlayer().getJob();
            var txt = "";
            txt += 心2 + "   " + 心2 + "   " + 心2 + "  " + 心2 + "  " + 心2 + "  " + 心2 + "   " + 心2 + "   " + 心2 + "   " + 心2 + "   " + 心2 + "   " + 心2 + "\r\n\r\n";

            txt += "" + 奖励 + "\r\n"
            txt += "\t" + 红色箭头 + " #v5200000# 5万 抵用券 \r\n";
            txt += "\t" + 红色箭头 + " #v5200002# 5000万 金币 \r\n";
            txt += "\t" + 红色箭头 + " #v5220010##z5220010# 5 张  \r\n";
            txt += "\t" + 红色箭头 + " #v1112440##z1112440# 1 张  \r\n";
            txt += "\t" + 红色箭头 + " #v1112441##z1112441# 1 张  \r\n";
            txt += "\t" + 红色箭头 + " #v1112442##z1112442# 1 张  \r\n";
            txt += "\t" + 红色箭头 + " #v1112443##z1112443# 1 张  \r\n";
            txt += "\t" + 红色箭头 + " #v1112444##z1112444# 1 张  \r\n";
            txt += "\t" + 红色箭头 + " #v1113034##z1113034# 1 张  \r\n";
            txt += "\t" + 红色箭头 + " #v1003864##z1003864# 1 张  \r\n";
            txt += "\t" + 红色箭头 + " #v1012377##z1012377# 1 张  \r\n";
            txt += "\t" + 红色箭头 + " #v1142724##z1142724# 1 张  \r\n";
            txt += "\t" + 红色箭头 + " #v1052613##z1052613# 1 张  \r\n";
            txt += "\t" + 红色箭头 + " #v1102563##z1102563# 1 张  \r\n";
            txt += "\t" + 红色箭头 + " #v1122253##z1122253# 1 张  \r\n";
            txt += "\t" + 红色箭头 + " #v1132229##z1132229# 1 张  \r\n";
            txt += "\t" + 红色箭头 + " #v5150040##z5150040# 5 张  \r\n";
            txt += "\t" + 红色箭头 + " #v5211047##z5211047# 1 张  \r\n";
            txt += "\t" + 红色箭头 + " #v2022615##z2022615# 1 张  \r\n";
            txt += " #r#e请输入：#k【#r我爱冒险岛#k】   获取新手礼包奖励！！#k#n\r\n\r\n"
            cm.sendGetText(txt);
        } else if (status == 1) {
            if (cm.getText() == "我爱冒险岛") {
                var next = true;
                var 角色ID = cm.getPlayer().getId();
                var fee_list = getAccCid(角色ID);
                for (var i = 0; i < fee_list.length; i++) {
                    if (cm.getBossRank(fee_list[i], "新人礼包", 6) > 0) {
                        next = false;
                        break;
                    }
                }
                if (next == false) {
                    cm.sendOk("你的其他角色已经领取过了！");
                    cm.dispose();
                    return;
                }
                if (cm.getBossRankCount("新人礼包") > 0) {
                    cm.sendOk("你已经领取过了！");
                    cm.dispose();
                    return;
                }
                if (cm.getPlayer().getBossLogS("新人礼包") > 0) {
                    cm.sendOk("你已经领2取过了！");
                    cm.dispose();
                    return;
                }
                if (cm.getPlayer().getLevel() < 1) {
                    cm.sendOk("你的等级不足，请5级后再来！");
                    cm.dispose();
                    return;
                }
                if (!cm.canHoldByTypea(5, 1)) {
                    cm.sendOk("你的背包空间不足！");
                    cm.dispose();
                    return;
                }
                cm.setBossRankCount("新人礼包");
                cm.setBossLog("新人礼包");
                cm.gainNX2(50000)
                cm.gainMeso(50000000);
                cm.gainItem(5150040, 5);
                cm.gainItem(5220010, 5);
                cm.gainItem(5211047, 1, 3);
                cm.gainItem(1112440, 1);
                cm.gainItem(1112441, 1);
                cm.gainItem(1112442, 1);
                cm.gainItem(1112443, 1);
                cm.gainItem(1112444, 1);
                cm.gainItem(1113034, 1);
                cm.gainItem(1142724, 1);
                cm.gainItem(1003864, 1);
                cm.gainItem(1012377, 1);
                cm.gainItem(1052613, 1);
                cm.gainItem(1102563, 1);
                cm.gainItem(1122253, 1);
                cm.gainItem(1132229, 1);
                cm.gainItem(2022615, 1);
                cm.sendOk("恭喜你领取成功！！！");
                cm.worldMessage(6, "欢迎" + cm.getPlayer().getName() + "来到eV.095冒险岛，要收徒弟的赶紧了！");
				cm.worldMessage(6, "欢迎" + cm.getPlayer().getName() + "来到eV.095冒险岛，要收徒弟的赶紧了！");
				cm.worldMessage(6, "欢迎" + cm.getPlayer().getName() + "来到eV.095冒险岛，要收徒弟的赶紧了！");
                cm.dispose();
            } else {
                cm.sendSimple("搞错了，再来一次！");
                cm.dispose();
            }
        }
    }
}


function getAccCid(bossid) {
    var con = cm.getDataSource().getConnection();
    var count = [];
    var counts = 0;
    var countss = 0;
    var ps = con.prepareStatement("SELECT * FROM characters WHERE id = ?");
    ps.setInt(1, bossid);
    var rs = ps.executeQuery();
    if (rs.next()) {
        counts = rs.getInt("accountid");
    } else {
        return undefined;
        rs.close();
        ps.close();
        con.close();
    }
    var ps1 = con.prepareStatement("SELECT COUNT(*) FROM characters WHERE accountid = ?");
    ps1.setInt(1, counts);
    var ps2 = con.prepareStatement("SELECT * FROM characters WHERE accountid = ?");
    ps2.setInt(1, counts);
    var rs1 = ps1.executeQuery();
    var rs2 = ps2.executeQuery();
    if (rs1.next()) {
        countss = rs1.getInt(1);
    } else {
        counts = -1;
    }
    for (var i = 0; i < countss; i++) {
        if (rs2.next()) {
            count.push(rs2.getInt("id"));
        }
    }
    rs2.close();
    ps2.close();
    rs1.close();
    ps1.close();
    rs.close();
    ps.close();
    con.close();
    return count;
}