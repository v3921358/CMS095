var 查询 = java.util.Calendar.getInstance();
var 获取年 = 查询.get(java.util.Calendar.YEAR);
var 获取日= 查询.get(java.util.Calendar.DATE);
var 获取月= 查询.get(java.util.Calendar.MONTH)+1;
var 获取日期= (获取年*10000)+(获取月*100)+获取日; //获取日期
var status = 0;
var text = "";
var 二级链接;
var 三级链接;
var 分解材料 = 4000313;
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
var 低级战士装备 = Array(1002776,1052155,1082234,1102172,1072355,1402046,1432047,1412033,1442063,1422037,1302081,1312037,1322060);
var 高级战士装备 = Array(1003177,1052319,1082300,1102280,1072490,1402096,1432087,1412066,1442117,1422067,1302153,1312066,1322097);
var 低级法师装备 = Array(1002777,1052156,1082235,1102172,1072356,1372044,1382057);
var 高级法师装备 = Array(1003178,1052320,1082301,1102281,1072491,1372085,1382105);
var 低级弓手装备 = Array(1002778,1052157,1082236,1102172,1072357,1452057,1462050);
var 高级弓手装备 = Array(1003179,1052321,1082302,1102282,1072492,1452112,1462100);
var 低级飞侠装备 = Array(1002779,1052158,1082237,1102172,1072358,1332074,1472068);
var 高级飞侠装备 = Array(1003180,1052322,1082303,1102283,1072493,1332131,1472123);
var 低级海盗装备 = Array(1002780,1052159,1082238,1102172,1072359,1482023,1492023);
var 高级海盗装备 = Array(1003181,1052323,1082304,1102284,1072494,1482085,1492086);
var 低级全系列 = Array(1002776,1052155,1082234,1102172,1072355,1002777,1052156,1082235,1072356,1002778,1052157,1082236,1072357,1002779,1052158,1082237,1072358,1002780,1052159,1082238,1072359,1402046,1432047,1412033,1442063,1422037,1302081,1312037,1322060,1372044,1382057,1452057,1462050,1332074,1472068,1482023,1492023,1092059,1092058,1092057,1032031,1122012);
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
			text = "  #d Hi~ #b#h ##k使用#b低级装备#k以及#r "+所需数量[1]+"个#v"+分解材料+"##k进行升级.#k#n#b\r\n";
			text += " #L6##v"+分解材料+"##e#r兑换#d低级装备#l";
			text += "  #L7##b分解#d低级装备#v"+分解材料+"##l#n#k\r\n\r\n";
			text += " #L1##v"+低级战士装备[10]+"##b#e低级套装#k#n升级#r#e高级套装#k#n[#g战士系#k]#v"+高级战士装备[10]+"##l\r\n";
			text += " #L2##v"+低级法师装备[6]+"##b#e低级套装#k#n升级#r#e高级套装#k#n[#g法师系#k]#v"+高级法师装备[6]+"##l\r\n";
			text += " #L3##v"+低级弓手装备[5]+"##b#e低级套装#k#n升级#r#e高级套装#k#n[#g弓手系#k]#v"+高级弓手装备[5]+"##l\r\n";
			text += " #L4##v"+低级飞侠装备[5]+"##b#e低级套装#k#n升级#r#e高级套装#k#n[#g飞侠系#k]#v"+高级飞侠装备[5]+"##l\r\n";
			text += " #L5##v"+低级海盗装备[6]+"##b#e低级套装#k#n升级#r#e高级套装#k#n[#g海盗系#k]#v"+高级海盗装备[6]+"##l\r\n";
			text += "\r\n ";
		cm.sendSimple(text);
		} else if (status == 1) {
			 if (selection == 1) {
				 var selStr = "#k使用#b低级装备#k以及#r "+所需数量[1]+"个#v"+分解材料+"##k进行升级.#k#n#b\r\n";
				 for (var i = 0; i < 低级战士装备.length; i++) {
					 selStr += "#L" + i + "# #b#v" + 低级战士装备[i] + "# #r#e升级#n #v" + 高级战士装备[i] + "##b#z" + 高级战士装备[i] + "##l\r\n";
			 }
			 selStr += " \r\n";
			 二级链接 = 1;
			 cm.sendSimple(selStr);
		}
		if (selection == 2) {
				var selStr = "#k使用#b低级装备#k以及#r "+所需数量[1]+"个#v"+分解材料+"##k进行升级.#k#n#b\r\n";
				 for (var i = 0; i < 低级法师装备.length; i++) {
					 selStr += "#L" + i + "# #b#v" + 低级法师装备[i] + "# #r#e升级#n #v" + 高级法师装备[i] + "##b#z" + 高级法师装备[i] + "##l\r\n";
			 }
			 selStr += " \r\n";
			 二级链接 = 2;
			 cm.sendSimple(selStr);
		}
		if (selection == 3) {
				var selStr = "#k使用#b低级装备#k以及#r "+所需数量[1]+"个#v"+分解材料+"##k进行升级.#k#n#b\r\n";
				 for (var i = 0; i < 低级弓手装备.length; i++) {
					 selStr += "#L" + i + "# #b#v" + 低级弓手装备[i] + "# #r#e升级#n #v" + 高级弓手装备[i] + "##b#z" + 高级弓手装备[i] + "##l\r\n";
			 }
			 selStr += " \r\n";
			 二级链接 = 3;
			 cm.sendSimple(selStr);
		}
		if (selection == 4) {
				 var selStr = "#k使用#b低级装备#k以及#r "+所需数量[1]+"个#v"+分解材料+"##k进行升级.#k#n#b\r\n";
				 for (var i = 0; i < 低级飞侠装备.length; i++) {
					 selStr += "#L" + i + "# #b#v" + 低级飞侠装备[i] + "# #r#e升级#n #v" + 高级飞侠装备[i] + "##b#z" + 高级飞侠装备[i] + "##l\r\n";
			 }
			 selStr += " \r\n";
			 二级链接 = 4;
			 cm.sendSimple(selStr);
		}
		if (selection == 5) {
				 var selStr = "#k使用#b低级装备#k以及#r "+所需数量[1]+"个#v"+分解材料+"##k进行升级.#k#n#b\r\n";
				 for (var i = 0; i < 低级海盗装备.length; i++) {
					 selStr += "#L" + i + "# #b#v" + 低级海盗装备[i] + "# #r#e升级#n #v" + 高级海盗装备[i] + "##b#z" + 高级海盗装备[i] + "##l\r\n";
			 }
			 selStr += " \r\n";
			 二级链接 = 5;
			 cm.sendSimple(selStr);
		}
		if (selection == 6) {
				 var selStr = "  #d Hi~ #b#h ##k使用#r "+所需数量[2]+"个#v"+分解材料+"##k即可兑换以下装备.#k#n#b\r\n";
				 for (var i = 0; i < 低级全系列.length; i++) {
					 selStr += "#L" + i + "# #r兑换#b#v" + 低级全系列[i] + "##z" + 低级全系列[i] + "##l\r\n";
			 }
			 selStr += " \r\n";
			 二级链接 = 6;
			 cm.sendSimple(selStr);
		}
		if (selection == 7) {
                var it;
                var selStr = "#b#h # #k每分解一件低级装备可获得#b"+所需数量[3]+"个#v"+分解材料+"#\r\n#k下面选择你想进行分解的低级装备吧...\r\n#r温馨提示:系统自动跳过不符合条件的装备!#k\r\n";
                var inv = cm.getInventory(1);
                for (var i = 0; i <= 100; i++) {
				for (var o = 0; o < 低级全系列.length; o++) {
                    it = inv.getItem(i);
                if (it != null && it.getItemId() == 低级全系列[o]) {
                    selStr += "#L" + i + "# #r分解#k背包第#r" + i + "#k格 #b#v" + 低级全系列[o] + "##t" + 低级全系列[o] + "##l\r\n";
                    }
                    }
					}
				selStr += "\r\n#L999##e#r#r一键分解背包中全部低级装备[慎点]#d#l#k\r\n "
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
			if (cm.itemQuantity(低级战士装备[选择的战士装备]) >= 1) {
				var it;
                var selStr = "#b#h # #k选择你想进行升级的低级装备吧...\r\n";
				selStr += "\r\n#e#r#r[温馨提示]原装备所有强化属性无法传承#d#l#k\r\n "
                var inv = cm.getInventory(1);
                for (var i = 0; i <= 100; i++) {
                    it = inv.getItem(i);
                if (it != null && it.getItemId() == 低级战士装备[选择的战士装备]) {
                    selStr += "#n#L" + i + "##k使用背包第#r" + i + "#k格 #b#v" + 低级战士装备[选择的战士装备] + "#进行升级#l\r\n";
                    }
					}
                    三级链接 = 1;
                    cm.sendSimple(selStr);
		} else {
			cm.sendOk("#r失败! #k升级#v"+高级战士装备[选择的战士装备]+"##b#z"+高级战士装备[选择的战士装备]+"# #k需拥有#v"+低级战士装备[选择的战士装备]+"#...");
			cm.dispose();
		}
		}
		if (二级链接 == 2) {
				选择的法师装备 = selection;
			if (cm.itemQuantity(低级法师装备[选择的法师装备]) >= 1) {
				var it;
                var selStr = "#b#h # #k选择你想进行升级的低级装备吧...\r\n";
				selStr += "\r\n#e#r#r[温馨提示]原装备所有强化属性无法传承#d#l#k\r\n "
                var inv = cm.getInventory(1);
                for (var i = 0; i <= 100; i++) {
                    it = inv.getItem(i);
                if (it != null && it.getItemId() == 低级法师装备[选择的法师装备]) {
                    selStr += "#n#L" + i + "##k使用背包第#r" + i + "#k格 #b#v" + 低级法师装备[选择的法师装备] + "#进行升级#l\r\n";
                    }
					}
                    三级链接 = 2;
                    cm.sendSimple(selStr);
		} else {
			cm.sendOk("#r失败! #k升级#v"+高级法师装备[选择的法师装备]+"##b#z"+高级法师装备[选择的法师装备]+"# #k需拥有#v"+低级法师装备[选择的法师装备]+"#...");
			cm.dispose();
		}
		}
		if (二级链接 == 3) {
				选择的弓手装备 = selection;
			if (cm.itemQuantity(低级弓手装备[选择的弓手装备]) >= 1) {
				var it;
                var selStr = "#b#h # #k选择你想进行升级的低级装备吧...\r\n";
				selStr += "\r\n#e#r#r[温馨提示]原装备所有强化属性无法传承#d#l#k\r\n "
                var inv = cm.getInventory(1);
                for (var i = 0; i <= 100; i++) {
                    it = inv.getItem(i);
                if (it != null && it.getItemId() == 低级弓手装备[选择的弓手装备]) {
                    selStr += "#n#L" + i + "##k使用背包第#r" + i + "#k格 #b#v" + 低级弓手装备[选择的弓手装备] + "#进行升级#l\r\n";
                    }
					}
                    三级链接 = 3;
                    cm.sendSimple(selStr);
		} else {
			cm.sendOk("#r失败! #k升级#v"+高级弓手装备[选择的弓手装备]+"##b#z"+高级弓手装备[选择的弓手装备]+"# #k需拥有#v"+低级弓手装备[选择的弓手装备]+"#...");
			cm.dispose();
		}
		}
		if (二级链接 == 4) {
				选择的飞侠装备 = selection;
			if (cm.itemQuantity(低级飞侠装备[选择的飞侠装备]) >= 1) {
				var it;
                var selStr = "#b#h # #k选择你想进行升级的低级装备吧...\r\n";
				selStr += "\r\n#e#r#r[温馨提示]原装备所有强化属性无法传承#d#l#k\r\n "
                var inv = cm.getInventory(1);
                for (var i = 0; i <= 100; i++) {
                    it = inv.getItem(i);
                if (it != null && it.getItemId() == 低级飞侠装备[选择的飞侠装备]) {
                    selStr += "#n#L" + i + "##k使用背包第#r" + i + "#k格 #b#v" + 低级飞侠装备[选择的飞侠装备] + "#进行升级#l\r\n";
                    }
					}
                    三级链接 = 4;
                    cm.sendSimple(selStr);
		} else {
			cm.sendOk("#r失败! #k升级#v"+高级飞侠装备[选择的飞侠装备]+"##b#z"+高级飞侠装备[选择的飞侠装备]+"# #k需拥有#v"+低级飞侠装备[选择的飞侠装备]+"#...");
			cm.dispose();
		}
		}
		if (二级链接 == 5) {
				选择的海盗装备 = selection;
			if (cm.itemQuantity(低级海盗装备[选择的海盗装备]) >= 1) {
				var it;
                var selStr = "#b#h # #k选择你想进行升级的低级装备吧...\r\n";
				selStr += "\r\n#e#r#r[温馨提示]原装备所有强化属性无法传承#d#l#k\r\n "
                var inv = cm.getInventory(1);
                for (var i = 0; i <= 100; i++) {
                    it = inv.getItem(i);
                if (it != null && it.getItemId() == 低级海盗装备[选择的海盗装备]) {
                    selStr += "#n#L" + i + "##k使用背包第#r" + i + "#k格 #b#v" + 低级海盗装备[选择的海盗装备] + "#进行升级#l\r\n";
                    }
					}
                    三级链接 = 5;
                    cm.sendSimple(selStr);
		} else {
			cm.sendOk("#r失败! #k升级#v"+高级海盗装备[选择的海盗装备]+"##b#z"+高级海盗装备[选择的海盗装备]+"# #k需拥有#v"+低级海盗装备[选择的海盗装备]+"#...");
			cm.dispose();
		}
		}
		if (二级链接 == 6) {
			选择的兑换装备 = selection;
			三级链接 = 6;
        cm.sendYesNo("确定兑换#v"+低级全系列[选择的兑换装备]+"##b#z"+低级全系列[选择的兑换装备]+"##k吗?");
		}
		if (二级链接 == 7) {
			if (selection != 999) {	
			选择的分解装备位置 = selection;
			选择的分解装备 = cm.getInventory(1).getItem(选择的分解装备位置);
			三级链接 = 7;
        cm.sendYesNo("确定#r分解#k背包中#r第"+选择的分解装备位置+"格#v"+选择的分解装备.getItemId()+"##b#z"+选择的分解装备.getItemId()+"##k吗?");
		} else {
			三级链接 = 8;
        cm.sendYesNo("确定#r分解#k背包中#r所有的#b低级装备#k吗?");
            }
		}
	} else if (status == 3) {
		if (三级链接 == 1) {
			选择的战士装备位置 = selection;
				if (cm.itemQuantity(分解材料) >= 所需数量[1]) {
				cm.removeSlot(1, 选择的战士装备位置, 1);
				cm.gainItem(分解材料, -所需数量[1]);
				cm.gainItem(高级战士装备[选择的战士装备],1);
				cm.sendOk("#v"+高级战士装备[选择的战士装备]+"##b#z"+高级战士装备[选择的战士装备]+"# #k升级成功...");
		} else {
			cm.sendOk("#r失败! #k升级#v"+高级战士装备[选择的战士装备]+"##b#z"+高级战士装备[选择的战士装备]+"# #k需要"+所需数量[1]+"个#v"+分解材料+"#...");
		}
		cm.dispose();
		}
		if (三级链接 == 2) {
			选择的法师装备位置 = selection;
				if (cm.itemQuantity(分解材料) >= 所需数量[1]) {
				cm.removeSlot(1, 选择的法师装备位置, 1);
				cm.gainItem(分解材料, -所需数量[1]);
				cm.gainItem(高级法师装备[选择的法师装备],1);
				cm.sendOk("#v"+高级法师装备[选择的法师装备]+"##b#z"+高级法师装备[选择的法师装备]+"# #k升级成功...");
		} else {
			cm.sendOk("#r失败! #k升级#v"+高级法师装备[选择的法师装备]+"##b#z"+高级法师装备[选择的法师装备]+"# #k需要"+所需数量[1]+"个#v"+分解材料+"#...");
		}
		cm.dispose();
		}
		if (三级链接 == 3) {
			选择的弓手装备位置 = selection;
				if (cm.itemQuantity(分解材料) >= 所需数量[1]) {
				cm.removeSlot(1, 选择的弓手装备位置, 1);
				cm.gainItem(分解材料, -所需数量[1]);
				cm.gainItem(高级弓手装备[选择的弓手装备],1);
				cm.sendOk("#v"+高级弓手装备[选择的弓手装备]+"##b#z"+高级弓手装备[选择的弓手装备]+"# #k升级成功...");
		} else {
			cm.sendOk("#r失败! #k升级#v"+高级弓手装备[选择的弓手装备]+"##b#z"+高级弓手装备[选择的弓手装备]+"# #k需要"+所需数量[1]+"个#v"+分解材料+"#...");
		}
		cm.dispose();
		}
		if (三级链接 == 4) {
			选择的飞侠装备位置 = selection;
				if (cm.itemQuantity(分解材料) >= 所需数量[1]) {
				cm.removeSlot(1, 选择的飞侠装备位置, 1);
				cm.gainItem(分解材料, -所需数量[1]);
				cm.gainItem(高级飞侠装备[选择的飞侠装备],1);
				cm.sendOk("#v"+高级飞侠装备[选择的飞侠装备]+"##b#z"+高级飞侠装备[选择的飞侠装备]+"# #k升级成功...");
		} else {
			cm.sendOk("#r失败! #k升级#v"+高级飞侠装备[选择的飞侠装备]+"##b#z"+高级飞侠装备[选择的飞侠装备]+"# #k需要"+所需数量[1]+"个#v"+分解材料+"#...");
		}
		cm.dispose();
		}
		if (三级链接 == 5) {
			选择的海盗装备位置 = selection;
				if (cm.itemQuantity(分解材料) >= 所需数量[1]) {
				cm.removeSlot(1, 选择的海盗装备位置, 1);
				cm.gainItem(分解材料, -所需数量[1]);
				cm.gainItem(高级海盗装备[选择的海盗装备],1);
				cm.sendOk("#v"+高级海盗装备[选择的海盗装备]+"##b#z"+高级海盗装备[选择的海盗装备]+"# #k升级成功...");
		} else {
			cm.sendOk("#r失败! #k升级#v"+高级海盗装备[选择的海盗装备]+"##b#z"+高级海盗装备[选择的海盗装备]+"# #k需要"+所需数量[1]+"个#v"+分解材料+"#...");
		}
		cm.dispose();
		}
		if (三级链接 == 6) {
				if (cm.itemQuantity(分解材料) >= 所需数量[2]) {
				cm.gainItem(分解材料, -所需数量[2]);
				cm.gainItem(低级全系列[选择的兑换装备],1);
				cm.sendOk("#v"+低级全系列[选择的兑换装备]+"##b#z"+低级全系列[选择的兑换装备]+"# #k兑换成功...");
		} else {
			cm.sendOk("#r失败! #k兑换#v"+低级全系列[选择的兑换装备]+"##b#z"+低级全系列[选择的兑换装备]+"# #k需要"+所需数量[2]+"个#v"+分解材料+"#...");
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
				for (var o = 0; o < 低级全系列.length; o++) {
                    it = inv.getItem(i);
                if (it != null && it.getItemId() == 低级全系列[o]) {
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
			
