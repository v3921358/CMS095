var 查询 = java.util.Calendar.getInstance();
var 获取年 = 查询.get(java.util.Calendar.YEAR);
var 获取日= 查询.get(java.util.Calendar.DATE);
var 获取月= 查询.get(java.util.Calendar.MONTH)+1;
var 获取日期= (获取年*10000)+(获取月*100)+获取日; //获取日期
var status = 0;
var text = "";
var 二级链接;
var 三级链接;
var 分解材料 = 4032169;
var 所需数量 = Array(0,10,3,1);//10为升级所需数量 3为兑换所需数量 1为分解获得数量
var 选择的战士装备;
var 选择的战士装备位置;
var 选择的法师装备;
var 选择的法师装备位置;
var 选择的弓手装备;
var 选择的弓手装备位置;
var 选择的飞侠装备;
var 选择的飞侠装备位置;
var 选择的海盗装备;
var 选择的海盗装备位置;
var 选择的兑换装备;
var 选择的分解装备位置; 
var 选择的分解装备; 
var 获得分解材料数量 = 0;
var 埃苏战士装备 = Array(1004422,1052882,1152174,1082636,1102775,1073030,1402251,1432214,1412177,1442268,1422184,1302333,1312199,1322250);
var 神秘战士装备 = Array(1004808,1053063,1152196,1082695,1102940,1073158,1402259,1432218,1412181,1442274,1422189,1302343,1312203,1322255);
var 埃苏法师装备 = Array(1004423,1052887,1152176,1082637,1102794,1073032,1372222,1382259);
var 神秘法师装备 = Array(1004809,1053064,1152197,1082696,1102941,1073159,1372228,1382265);
var 埃苏弓手装备 = Array(1004424,1052888,1152177,1082638,1102795,1073033,1452252,1462239);
var 神秘弓手装备 = Array(1004810,1053065,1152198,1082697,1102942,1073160,1452257,1462243);
var 埃苏飞侠装备 = Array(1004425,1052889,1152178,1082639,1102796,1073034,1332274,1472261,1342101);
var 神秘飞侠装备 = Array(1004811,1053066,1152199,1082698,1102943,1073161,1332279,1472265,1342104);
var 埃苏海盗装备 = Array(1004426,1052890,1152179,1082640,1102797,1073035,1482216,1492231);
var 神秘海盗装备 = Array(1004812,1053067,1152200,1082699,1102944,1073162,1482221,1492235);
var 埃苏全系列 = Array(1004422,1052882,1152174,1082636,1102775,1073030,1004423,1052887,1152176,1082637,1102794,1073032,1004424,1052888,1152177,1082638,1102795,1073033,1004425,1052889,1152178,1082639,1102796,1073034,1004426,1052890,1152179,1082640,1102797,1073035,1402251,1432214,1412177,1442268,1422184,1302333,1312199,1322250,1372222,1382259,1452252,1462239,1332274,1342101,1472261,1482216,1492231);
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
        if (mode == 1) status++;
        if (status == 0) {
			text = "  #d Hi~ #b#h ##k使用#b埃苏装备#k以及#r "+所需数量[1]+"个#v"+分解材料+"##k进行升级.#k#n#b\r\n";
			text += " #L6##v"+分解材料+"##e#r兑换#d埃苏装备#l";
			text += "  #L7##b分解#d埃苏装备#v"+分解材料+"##l#n#k\r\n\r\n";
			text += " #L1##v"+埃苏战士装备[11]+"##b#e埃苏套装#k#n升级#r#e神秘套装#k#n[#g战士系#k]#v"+神秘战士装备[11]+"##l\r\n";
			text += " #L2##v"+埃苏法师装备[7]+"##b#e埃苏套装#k#n升级#r#e神秘套装#k#n[#g法师系#k]#v"+神秘法师装备[7]+"##l\r\n";
			text += " #L3##v"+埃苏弓手装备[6]+"##b#e埃苏套装#k#n升级#r#e神秘套装#k#n[#g弓手系#k]#v"+神秘弓手装备[6]+"##l\r\n";
			text += " #L4##v"+埃苏飞侠装备[6]+"##b#e埃苏套装#k#n升级#r#e神秘套装#k#n[#g飞侠系#k]#v"+神秘飞侠装备[6]+"##l\r\n";
			text += " #L5##v"+埃苏海盗装备[7]+"##b#e埃苏套装#k#n升级#r#e神秘套装#k#n[#g海盗系#k]#v"+神秘海盗装备[7]+"##l\r\n";
			text += "\r\n ";
		cm.sendSimple(text);
		} else if (status == 1) {
			 if (selection == 1) {
				 var selStr = "#k使用#b埃苏装备#k以及#r "+所需数量[1]+"个#v"+分解材料+"##k进行升级.#k#n#b\r\n";
				 for (var i = 0; i < 埃苏战士装备.length; i++) {
					 selStr += "#L" + i + "# #b#v" + 埃苏战士装备[i] + "# #r#e升级#n #v" + 神秘战士装备[i] + "##b#z" + 神秘战士装备[i] + "##l\r\n";
			 }
			 selStr += " \r\n";
			 二级链接 = 1;
			 cm.sendSimple(selStr);
		}
		if (selection == 2) {
				var selStr = "#k使用#b埃苏装备#k以及#r "+所需数量[1]+"个#v"+分解材料+"##k进行升级.#k#n#b\r\n";
				 for (var i = 0; i < 埃苏法师装备.length; i++) {
					 selStr += "#L" + i + "# #b#v" + 埃苏法师装备[i] + "# #r#e升级#n #v" + 神秘法师装备[i] + "##b#z" + 神秘法师装备[i] + "##l\r\n";
			 }
			 selStr += " \r\n";
			 二级链接 = 2;
			 cm.sendSimple(selStr);
		}
		if (selection == 3) {
				var selStr = "#k使用#b埃苏装备#k以及#r "+所需数量[1]+"个#v"+分解材料+"##k进行升级.#k#n#b\r\n";
				 for (var i = 0; i < 埃苏弓手装备.length; i++) {
					 selStr += "#L" + i + "# #b#v" + 埃苏弓手装备[i] + "# #r#e升级#n #v" + 神秘弓手装备[i] + "##b#z" + 神秘弓手装备[i] + "##l\r\n";
			 }
			 selStr += " \r\n";
			 二级链接 = 3;
			 cm.sendSimple(selStr);
		}
		if (selection == 4) {
				 var selStr = "#k使用#b埃苏装备#k以及#r "+所需数量[1]+"个#v"+分解材料+"##k进行升级.#k#n#b\r\n";
				 for (var i = 0; i < 埃苏飞侠装备.length; i++) {
					 selStr += "#L" + i + "# #b#v" + 埃苏飞侠装备[i] + "# #r#e升级#n #v" + 神秘飞侠装备[i] + "##b#z" + 神秘飞侠装备[i] + "##l\r\n";
			 }
			 selStr += " \r\n";
			 二级链接 = 4;
			 cm.sendSimple(selStr);
		}
		if (selection == 5) {
				 var selStr = "#k使用#b埃苏装备#k以及#r "+所需数量[1]+"个#v"+分解材料+"##k进行升级.#k#n#b\r\n";
				 for (var i = 0; i < 埃苏海盗装备.length; i++) {
					 selStr += "#L" + i + "# #b#v" + 埃苏海盗装备[i] + "# #r#e升级#n #v" + 神秘海盗装备[i] + "##b#z" + 神秘海盗装备[i] + "##l\r\n";
			 }
			 selStr += " \r\n";
			 二级链接 = 5;
			 cm.sendSimple(selStr);
		}
		if (selection == 6) {
				 var selStr = "  #d Hi~ #b#h ##k使用#r "+所需数量[2]+"个#v"+分解材料+"##k即可兑换以下装备.#k#n#b\r\n";
				 for (var i = 0; i < 埃苏全系列.length; i++) {
					 selStr += "#L" + i + "# #r兑换#b#v" + 埃苏全系列[i] + "##z" + 埃苏全系列[i] + "##l\r\n";
			 }
			 selStr += " \r\n";
			 二级链接 = 6;
			 cm.sendSimple(selStr);
		}
		if (selection == 7) {
                var it;
                var selStr = "#b#h # #k每分解一件埃苏装备可获得#b"+所需数量[3]+"个#v"+分解材料+"#\r\n#k下面选择你想进行分解的埃苏装备吧...\r\n#r温馨提示:系统自动跳过不符合条件的装备!#k\r\n";
                var inv = cm.getInventory(1);
                for (var i = 0; i <= 100; i++) {
				for (var o = 0; o < 埃苏全系列.length; o++) {
                    it = inv.getItem(i);
                if (it != null && it.getItemId() == 埃苏全系列[o]) {
                    selStr += "#L" + i + "# #r分解#k背包第#r" + i + "#k格 #b#v" + 埃苏全系列[o] + "##t" + 埃苏全系列[o] + "##l\r\n";
                    }
                    }
					}
				selStr += "\r\n#L999##e#r#r一键分解背包中全部埃苏装备[慎点]#d#l#k\r\n "
                    二级链接 = 7;
                    cm.sendSimple(selStr);
                }
		} else if (status == 2) {
		if (cm.getInventory(1).isFull(0)){
                cm.sendOk("#k提交失败 请保证装备栏位至少有1个空格");
                cm.dispose();
                return;
            } else if (cm.getInventory(2).isFull(0)){
                cm.sendOk("#k提交失败 请保证消耗栏位至少有1个空格");
                cm.dispose();
                return;
            } else if (cm.getInventory(3).isFull(0)){
                cm.sendOk("#k提交失败 请保证设置栏位至少有1个空格");
                cm.dispose();
                return;
            } else if (cm.getInventory(4).isFull(0)){
                cm.sendOk("#k提交失败 请保证其他栏位至少有1个空格");
                cm.dispose();
                return;
            } else if (cm.getInventory(5).isFull(0)){
                cm.sendOk("#k提交失败 请保证特殊栏位至少有1个空格");
                cm.dispose();
                return;
            }
		if (二级链接 == 1) {
				选择的战士装备 = selection;
			if (cm.itemQuantity(埃苏战士装备[选择的战士装备]) >= 1) {
				var it;
                var selStr = "#b#h # #k选择你想进行升级的埃苏装备吧...\r\n";
				selStr += "\r\n#e#r#r[温馨提示]原装备所有强化属性无法传承#d#l#k\r\n "
                var inv = cm.getInventory(1);
                for (var i = 0; i <= 100; i++) {
                    it = inv.getItem(i);
                if (it != null && it.getItemId() == 埃苏战士装备[选择的战士装备]) {
                    selStr += "#n#L" + i + "##k使用背包第#r" + i + "#k格 #b#v" + 埃苏战士装备[选择的战士装备] + "#进行升级#l\r\n";
                    }
					}
                    三级链接 = 1;
                    cm.sendSimple(selStr);
		} else {
			cm.sendOk("#r失败! #k升级#v"+神秘战士装备[选择的战士装备]+"##b#z"+神秘战士装备[选择的战士装备]+"# #k需拥有#v"+埃苏战士装备[选择的战士装备]+"#...");
			cm.dispose();
		}
		}
		if (二级链接 == 2) {
				选择的法师装备 = selection;
			if (cm.itemQuantity(埃苏法师装备[选择的法师装备]) >= 1) {
				var it;
                var selStr = "#b#h # #k选择你想进行升级的埃苏装备吧...\r\n";
				selStr += "\r\n#e#r#r[温馨提示]原装备所有强化属性无法传承#d#l#k\r\n "
                var inv = cm.getInventory(1);
                for (var i = 0; i <= 100; i++) {
                    it = inv.getItem(i);
                if (it != null && it.getItemId() == 埃苏法师装备[选择的法师装备]) {
                    selStr += "#n#L" + i + "##k使用背包第#r" + i + "#k格 #b#v" + 埃苏法师装备[选择的法师装备] + "#进行升级#l\r\n";
                    }
					}
                    三级链接 = 2;
                    cm.sendSimple(selStr);
		} else {
			cm.sendOk("#r失败! #k升级#v"+神秘法师装备[选择的法师装备]+"##b#z"+神秘法师装备[选择的法师装备]+"# #k需拥有#v"+埃苏法师装备[选择的法师装备]+"#...");
			cm.dispose();
		}
		}
		if (二级链接 == 3) {
				选择的弓手装备 = selection;
			if (cm.itemQuantity(埃苏弓手装备[选择的弓手装备]) >= 1) {
				var it;
                var selStr = "#b#h # #k选择你想进行升级的埃苏装备吧...\r\n";
				selStr += "\r\n#e#r#r[温馨提示]原装备所有强化属性无法传承#d#l#k\r\n "
                var inv = cm.getInventory(1);
                for (var i = 0; i <= 100; i++) {
                    it = inv.getItem(i);
                if (it != null && it.getItemId() == 埃苏弓手装备[选择的弓手装备]) {
                    selStr += "#n#L" + i + "##k使用背包第#r" + i + "#k格 #b#v" + 埃苏弓手装备[选择的弓手装备] + "#进行升级#l\r\n";
                    }
					}
                    三级链接 = 3;
                    cm.sendSimple(selStr);
		} else {
			cm.sendOk("#r失败! #k升级#v"+神秘弓手装备[选择的弓手装备]+"##b#z"+神秘弓手装备[选择的弓手装备]+"# #k需拥有#v"+埃苏弓手装备[选择的弓手装备]+"#...");
			cm.dispose();
		}
		}
		if (二级链接 == 4) {
				选择的飞侠装备 = selection;
			if (cm.itemQuantity(埃苏飞侠装备[选择的飞侠装备]) >= 1) {
				var it;
                var selStr = "#b#h # #k选择你想进行升级的埃苏装备吧...\r\n";
				selStr += "\r\n#e#r#r[温馨提示]原装备所有强化属性无法传承#d#l#k\r\n "
                var inv = cm.getInventory(1);
                for (var i = 0; i <= 100; i++) {
                    it = inv.getItem(i);
                if (it != null && it.getItemId() == 埃苏飞侠装备[选择的飞侠装备]) {
                    selStr += "#n#L" + i + "##k使用背包第#r" + i + "#k格 #b#v" + 埃苏飞侠装备[选择的飞侠装备] + "#进行升级#l\r\n";
                    }
					}
                    三级链接 = 4;
                    cm.sendSimple(selStr);
		} else {
			cm.sendOk("#r失败! #k升级#v"+神秘飞侠装备[选择的飞侠装备]+"##b#z"+神秘飞侠装备[选择的飞侠装备]+"# #k需拥有#v"+埃苏飞侠装备[选择的飞侠装备]+"#...");
			cm.dispose();
		}
		}
		if (二级链接 == 5) {
				选择的海盗装备 = selection;
			if (cm.itemQuantity(埃苏海盗装备[选择的海盗装备]) >= 1) {
				var it;
                var selStr = "#b#h # #k选择你想进行升级的埃苏装备吧...\r\n";
				selStr += "\r\n#e#r#r[温馨提示]原装备所有强化属性无法传承#d#l#k\r\n "
                var inv = cm.getInventory(1);
                for (var i = 0; i <= 100; i++) {
                    it = inv.getItem(i);
                if (it != null && it.getItemId() == 埃苏海盗装备[选择的海盗装备]) {
                    selStr += "#n#L" + i + "##k使用背包第#r" + i + "#k格 #b#v" + 埃苏海盗装备[选择的海盗装备] + "#进行升级#l\r\n";
                    }
					}
                    三级链接 = 5;
                    cm.sendSimple(selStr);
		} else {
			cm.sendOk("#r失败! #k升级#v"+神秘海盗装备[选择的海盗装备]+"##b#z"+神秘海盗装备[选择的海盗装备]+"# #k需拥有#v"+埃苏海盗装备[选择的海盗装备]+"#...");
			cm.dispose();
		}
		}
		if (二级链接 == 6) {
			选择的兑换装备 = selection;
			三级链接 = 6;
        cm.sendYesNo("确定兑换#v"+埃苏全系列[选择的兑换装备]+"##b#z"+埃苏全系列[选择的兑换装备]+"##k吗?");
		}
		if (二级链接 == 7) {
			if (selection != 999) {	
			选择的分解装备位置 = selection;
			选择的分解装备 = cm.getInventory(1).getItem(选择的分解装备位置);
			三级链接 = 7;
        cm.sendYesNo("确定#r分解#k背包中#r第"+选择的分解装备位置+"格#v"+选择的分解装备.getItemId()+"##b#z"+选择的分解装备.getItemId()+"##k吗?");
		} else {
			三级链接 = 8;
        cm.sendYesNo("确定#r分解#k背包中#r所有的#b埃苏装备#k吗?");
            }
		}
	} else if (status == 3) {
		if (三级链接 == 1) {
			选择的战士装备位置 = selection;
				if (cm.itemQuantity(分解材料) >= 所需数量[1]) {
				cm.removeSlot(1, 选择的战士装备位置, 1);
				cm.gainItem(分解材料, -所需数量[1]);
				cm.gainItem(神秘战士装备[选择的战士装备],1);
				cm.sendOk("#v"+神秘战士装备[选择的战士装备]+"##b#z"+神秘战士装备[选择的战士装备]+"# #k升级成功...");
		} else {
			cm.sendOk("#r失败! #k升级#v"+神秘战士装备[选择的战士装备]+"##b#z"+神秘战士装备[选择的战士装备]+"# #k需要"+所需数量[1]+"个#v"+分解材料+"#...");
		}
		cm.dispose();
		}
		if (三级链接 == 2) {
			选择的法师装备位置 = selection;
				if (cm.itemQuantity(分解材料) >= 所需数量[1]) {
				cm.removeSlot(1, 选择的法师装备位置, 1);
				cm.gainItem(分解材料, -所需数量[1]);
				cm.gainItem(神秘法师装备[选择的法师装备],1);
				cm.sendOk("#v"+神秘法师装备[选择的法师装备]+"##b#z"+神秘法师装备[选择的法师装备]+"# #k升级成功...");
		} else {
			cm.sendOk("#r失败! #k升级#v"+神秘法师装备[选择的法师装备]+"##b#z"+神秘法师装备[选择的法师装备]+"# #k需要"+所需数量[1]+"个#v"+分解材料+"#...");
		}
		cm.dispose();
		}
		if (三级链接 == 3) {
			选择的弓手装备位置 = selection;
				if (cm.itemQuantity(分解材料) >= 所需数量[1]) {
				cm.removeSlot(1, 选择的弓手装备位置, 1);
				cm.gainItem(分解材料, -所需数量[1]);
				cm.gainItem(神秘弓手装备[选择的弓手装备],1);
				cm.sendOk("#v"+神秘弓手装备[选择的弓手装备]+"##b#z"+神秘弓手装备[选择的弓手装备]+"# #k升级成功...");
		} else {
			cm.sendOk("#r失败! #k升级#v"+神秘弓手装备[选择的弓手装备]+"##b#z"+神秘弓手装备[选择的弓手装备]+"# #k需要"+所需数量[1]+"个#v"+分解材料+"#...");
		}
		cm.dispose();
		}
		if (三级链接 == 4) {
			选择的飞侠装备位置 = selection;
				if (cm.itemQuantity(分解材料) >= 所需数量[1]) {
				cm.removeSlot(1, 选择的飞侠装备位置, 1);
				cm.gainItem(分解材料, -所需数量[1]);
				cm.gainItem(神秘飞侠装备[选择的飞侠装备],1);
				cm.sendOk("#v"+神秘飞侠装备[选择的飞侠装备]+"##b#z"+神秘飞侠装备[选择的飞侠装备]+"# #k升级成功...");
		} else {
			cm.sendOk("#r失败! #k升级#v"+神秘飞侠装备[选择的飞侠装备]+"##b#z"+神秘飞侠装备[选择的飞侠装备]+"# #k需要"+所需数量[1]+"个#v"+分解材料+"#...");
		}
		cm.dispose();
		}
		if (三级链接 == 5) {
			选择的海盗装备位置 = selection;
				if (cm.itemQuantity(分解材料) >= 所需数量[1]) {
				cm.removeSlot(1, 选择的海盗装备位置, 1);
				cm.gainItem(分解材料, -所需数量[1]);
				cm.gainItem(神秘海盗装备[选择的海盗装备],1);
				cm.sendOk("#v"+神秘海盗装备[选择的海盗装备]+"##b#z"+神秘海盗装备[选择的海盗装备]+"# #k升级成功...");
		} else {
			cm.sendOk("#r失败! #k升级#v"+神秘海盗装备[选择的海盗装备]+"##b#z"+神秘海盗装备[选择的海盗装备]+"# #k需要"+所需数量[1]+"个#v"+分解材料+"#...");
		}
		cm.dispose();
		}
		if (三级链接 == 6) {
				if (cm.itemQuantity(分解材料) >= 所需数量[2]) {
				cm.gainItem(分解材料, -所需数量[2]);
				cm.gainItem(埃苏全系列[选择的兑换装备],1);
				cm.sendOk("#v"+埃苏全系列[选择的兑换装备]+"##b#z"+埃苏全系列[选择的兑换装备]+"# #k兑换成功...");
		} else {
			cm.sendOk("#r失败! #k兑换#v"+埃苏全系列[选择的兑换装备]+"##b#z"+埃苏全系列[选择的兑换装备]+"# #k需要"+所需数量[2]+"个#v"+分解材料+"#...");
		}
		cm.dispose();
		}
		if (三级链接 == 7) {
				cm.removeSlot(1, 选择的分解装备位置, 1);
				cm.gainItem(分解材料, 所需数量[3]);
				cm.sendOk("分解成功 获得"+所需数量[3]+"个#v"+分解材料+"##b#z"+分解材料+"#...");
		cm.dispose();
		}
		if (三级链接 == 8) {
				var it;
                var inv = cm.getInventory(1);
                for (var i = 0; i <= 100; i++) {
				for (var o = 0; o < 埃苏全系列.length; o++) {
                    it = inv.getItem(i);
                if (it != null && it.getItemId() == 埃苏全系列[o]) {
                    cm.removeSlot(1, i, 1);
					获得分解材料数量 += 所需数量[3];
                    }
                    }
					}
				cm.gainItem(分解材料, 获得分解材料数量);
                cm.sendOk("一键分解成功 获得"+获得分解材料数量+"个#v"+分解材料+"##b#z"+分解材料+"#...");
                cm.dispose();
		}
	}
}
}
			
