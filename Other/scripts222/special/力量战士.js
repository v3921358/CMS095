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
var 所需数量 = Array(0,10,5,1);//10为升级所需数量 3为兑换所需数量 1为分解获得数量
var 选择的赛手装备;
var 选择的赛手装备位置;
var 选择的女皇装备;
var 选择的女皇装备位置;
var 选择的女神装备;
var 选择的女神装备位置;
var 选择的神圣装备;
var 选择的神圣装备位置;
var 选择的革命装备;
var 选择的革命装备位置;
var 选择的兑换装备;
var 选择的分解装备位置; 
var 选择的分解装备; 
var 获得分解材料数量 = 0;
var 低级赛手装备 = Array(1003197,1052333,1072502,1082305,1032031,1092058,1102172);
var 高级女皇装备 = Array(1002776,1052155,1072355,1082234,1302081,1312037,1322060);

var 低级女皇装备 = Array(1002776,1052155,1072355,1082234,1302081);
var 高级女神装备 = Array(1003770,1052580,1072786,1082506,1102514);

var 低级女神装备 = Array(1003770,1052580,1072786,1082506,1102514,1302081,1312037,1322060,1102514,1102514);
var 高级神圣装备 = Array(1003154,1052299,1072471,1082285,1102262,1402091,1442113,1432084,1302149,1132094);

var 低级神圣装备 = Array(1003154,1052299,1072471,1082285,1102262,1402091,1442113,1432084,1302149,1132094);
var 高级革命装备 = Array(1003172,1052314,1072485,1082295,1102275,1402095,1442116,1432086,1302152,1152108);

var 低级革命装备 = Array(1003172,1052314,1072485,1102275,1082295,1152108,1402095,1442116,1432086,1302152,1442116,1432086,1302152);
var 高级宝藏装备 = Array(1003797,1042254,1062165,1102476,1082333,1132169,1402263,1442280,1432222,1302347,1322259,1422192,1312207);


var 低级全系列 = Array(1003197,1052333,1072502,1082305,1032031,1092058,1102172);
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
			text += "   #L1##v"+低级赛手装备[1]+"##b#e强力漫步#k#n升级#r#e永恒套装#k#n[#g战士#k]#v"+高级女皇装备[1]+"##l\r\n";
			text += "   #L2##v"+低级女皇装备[1]+"##b#e永恒套装#k#n升级#r#e皇家英勇#k#n[#g战士#k]#v"+高级女神装备[1]+"##l\r\n";
			text += "   #L3##v"+低级女神装备[1]+"##b#e皇家英勇#k#n升级#r#e雷昂套装#k#n[#g战士#k]#v"+高级神圣装备[1]+"##l\r\n";
			text += "   #L4##v"+低级神圣装备[1]+"##b#e雷昂套装#k#n升级#r#e狮心套装#k#n[#g战士#k]#v"+高级革命装备[1]+"##l\r\n";
			text += "   #L5##v"+低级革命装备[1]+"##b#e狮心套装#k#n升级#r#e鲁塔比斯#k#n[#g战士#k]#v"+高级宝藏装备[1]+"##l\r\n";
			text += "\r\n ";
		cm.sendSimple(text);
		} else if (status == 1) {
			 if (selection == 1) {
				 var selStr = "#k使用#b低级装备#k以及#r "+所需数量[1]+"个#v"+分解材料+"##k进行升级.#k#n#b\r\n";
				 for (var i = 0; i < 低级赛手装备.length; i++) {
					 selStr += "#L" + i + "# #b#v" + 低级赛手装备[i] + "# #r#e升级#n #v" + 高级女皇装备[i] + "##b#z" + 高级女皇装备[i] + "##l\r\n";
			 }
			 selStr += " \r\n";
			 二级链接 = 1;
			 cm.sendSimple(selStr);
		}
		if (selection == 2) {
				var selStr = "#k使用#b低级装备#k以及#r "+所需数量[1]+"个#v"+分解材料+"##k进行升级.#k#n#b\r\n";
				 for (var i = 0; i < 低级女皇装备.length; i++) {
					 selStr += "#L" + i + "# #b#v" + 低级女皇装备[i] + "# #r#e升级#n #v" + 高级女神装备[i] + "##b#z" + 高级女神装备[i] + "##l\r\n";
			 }
			 selStr += " \r\n";
			 二级链接 = 2;
			 cm.sendSimple(selStr);
		}
		if (selection == 3) {
				var selStr = "#k使用#b低级装备#k以及#r "+所需数量[1]+"个#v"+分解材料+"##k进行升级.#k#n#b\r\n";
				 for (var i = 0; i < 低级女神装备.length; i++) {
					 selStr += "#L" + i + "# #b#v" + 低级女神装备[i] + "# #r#e升级#n #v" + 高级神圣装备[i] + "##b#z" + 高级神圣装备[i] + "##l\r\n";
			 }
			 selStr += " \r\n";
			 二级链接 = 3;
			 cm.sendSimple(selStr);
		}
		if (selection == 4) {
				 var selStr = "#k使用#b低级装备#k以及#r "+所需数量[1]+"个#v"+分解材料+"##k进行升级.#k#n#b\r\n";
				 for (var i = 0; i < 低级神圣装备.length; i++) {
					 selStr += "#L" + i + "# #b#v" + 低级神圣装备[i] + "# #r#e升级#n #v" + 高级革命装备[i] + "##b#z" + 高级革命装备[i] + "##l\r\n";
			 }
			 selStr += " \r\n";
			 二级链接 = 4;
			 cm.sendSimple(selStr);
		}
		if (selection == 5) {
				 var selStr = "#k使用#b低级装备#k以及#r "+所需数量[1]+"个#v"+分解材料+"##k进行升级.#k#n#b\r\n";
				 for (var i = 0; i < 低级革命装备.length; i++) {
					 selStr += "#L" + i + "# #b#v" + 低级革命装备[i] + "# #r#e升级#n #v" + 高级宝藏装备[i] + "##b#z" + 高级宝藏装备[i] + "##l\r\n";
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
				选择的赛手装备 = selection;
			if (cm.itemQuantity(低级赛手装备[选择的赛手装备]) >= 1) {
				var it;
                var selStr = "#b#h # #k选择你想进行升级的低级装备吧...\r\n";
				selStr += "\r\n#e#r#r[温馨提示]原装备所有强化属性无法传承#d#l#k\r\n "
                var inv = cm.getInventory(1);
                for (var i = 0; i <= 100; i++) {
                    it = inv.getItem(i);
                if (it != null && it.getItemId() == 低级赛手装备[选择的赛手装备]) {
                    selStr += "#n#L" + i + "##k使用背包第#r" + i + "#k格 #b#v" + 低级赛手装备[选择的赛手装备] + "#进行升级#l\r\n";
                    }
					}
                    三级链接 = 1;
                    cm.sendSimple(selStr);
		} else {
			cm.sendOk("#r失败! #k升级#v"+高级女皇装备[选择的赛手装备]+"##b#z"+高级女皇装备[选择的赛手装备]+"# #k需拥有#v"+低级赛手装备[选择的赛手装备]+"#...");
			cm.dispose();
		}
		}
		if (二级链接 == 2) {
				选择的女皇装备 = selection;
			if (cm.itemQuantity(低级女皇装备[选择的女皇装备]) >= 1) {
				var it;
                var selStr = "#b#h # #k选择你想进行升级的低级装备吧...\r\n";
				selStr += "\r\n#e#r#r[温馨提示]原装备所有强化属性无法传承#d#l#k\r\n "
                var inv = cm.getInventory(1);
                for (var i = 0; i <= 100; i++) {
                    it = inv.getItem(i);
                if (it != null && it.getItemId() == 低级女皇装备[选择的女皇装备]) {
                    selStr += "#n#L" + i + "##k使用背包第#r" + i + "#k格 #b#v" + 低级女皇装备[选择的女皇装备] + "#进行升级#l\r\n";
                    }
					}
                    三级链接 = 2;
                    cm.sendSimple(selStr);
		} else {
			cm.sendOk("#r失败! #k升级#v"+高级女神装备[选择的女皇装备]+"##b#z"+高级女神装备[选择的女皇装备]+"# #k需拥有#v"+低级女皇装备[选择的女皇装备]+"#...");
			cm.dispose();
		}
		}
		if (二级链接 == 3) {
				选择的女神装备 = selection;
			if (cm.itemQuantity(低级女神装备[选择的女神装备]) >= 1) {
				var it;
                var selStr = "#b#h # #k选择你想进行升级的低级装备吧...\r\n";
				selStr += "\r\n#e#r#r[温馨提示]原装备所有强化属性无法传承#d#l#k\r\n "
                var inv = cm.getInventory(1);
                for (var i = 0; i <= 100; i++) {
                    it = inv.getItem(i);
                if (it != null && it.getItemId() == 低级女神装备[选择的女神装备]) {
                    selStr += "#n#L" + i + "##k使用背包第#r" + i + "#k格 #b#v" + 低级女神装备[选择的女神装备] + "#进行升级#l\r\n";
                    }
					}
                    三级链接 = 3;
                    cm.sendSimple(selStr);
		} else {
			cm.sendOk("#r失败! #k升级#v"+高级神圣装备[选择的女神装备]+"##b#z"+高级神圣装备[选择的女神装备]+"# #k需拥有#v"+低级女神装备[选择的女神装备]+"#...");
			cm.dispose();
		}
		}
		if (二级链接 == 4) {
				选择的神圣装备 = selection;
			if (cm.itemQuantity(低级神圣装备[选择的神圣装备]) >= 1) {
				var it;
                var selStr = "#b#h # #k选择你想进行升级的低级装备吧...\r\n";
				selStr += "\r\n#e#r#r[温馨提示]原装备所有强化属性无法传承#d#l#k\r\n "
                var inv = cm.getInventory(1);
                for (var i = 0; i <= 100; i++) {
                    it = inv.getItem(i);
                if (it != null && it.getItemId() == 低级神圣装备[选择的神圣装备]) {
                    selStr += "#n#L" + i + "##k使用背包第#r" + i + "#k格 #b#v" + 低级神圣装备[选择的神圣装备] + "#进行升级#l\r\n";
                    }
					}
                    三级链接 = 4;
                    cm.sendSimple(selStr);
		} else {
			cm.sendOk("#r失败! #k升级#v"+高级革命装备[选择的神圣装备]+"##b#z"+高级革命装备[选择的神圣装备]+"# #k需拥有#v"+低级神圣装备[选择的神圣装备]+"#...");
			cm.dispose();
		}
		}
		if (二级链接 == 5) {
				选择的革命装备 = selection;
			if (cm.itemQuantity(低级革命装备[选择的革命装备]) >= 1) {
				var it;
                var selStr = "#b#h # #k选择你想进行升级的低级装备吧...\r\n";
				selStr += "\r\n#e#r#r[温馨提示]原装备所有强化属性无法传承#d#l#k\r\n "
                var inv = cm.getInventory(1);
                for (var i = 0; i <= 100; i++) {
                    it = inv.getItem(i);
                if (it != null && it.getItemId() == 低级革命装备[选择的革命装备]) {
                    selStr += "#n#L" + i + "##k使用背包第#r" + i + "#k格 #b#v" + 低级革命装备[选择的革命装备] + "#进行升级#l\r\n";
                    }
					}
                    三级链接 = 5;
                    cm.sendSimple(selStr);
		} else {
			cm.sendOk("#r失败! #k升级#v"+高级宝藏装备[选择的革命装备]+"##b#z"+高级宝藏装备[选择的革命装备]+"# #k需拥有#v"+低级革命装备[选择的革命装备]+"#...");
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
			选择的赛手装备位置 = selection;
				if (cm.itemQuantity(分解材料) >= 所需数量[1]) {
				cm.removeSlot(1, 选择的赛手装备位置, 1);
				cm.gainItem(分解材料, -所需数量[1]);
				cm.gainItem(高级女皇装备[选择的赛手装备],1);
				cm.sendOk("#v"+高级女皇装备[选择的赛手装备]+"##b#z"+高级女皇装备[选择的赛手装备]+"# #k升级成功...");
		} else {
			cm.sendOk("#r失败! #k升级#v"+高级女皇装备[选择的赛手装备]+"##b#z"+高级女皇装备[选择的赛手装备]+"# #k需要"+所需数量[1]+"个#v"+分解材料+"#...");
		}
		cm.dispose();
		}
		if (三级链接 == 2) {
			选择的女皇装备位置 = selection;
				if (cm.itemQuantity(分解材料) >= 所需数量[1]) {
				cm.removeSlot(1, 选择的女皇装备位置, 1);
				cm.gainItem(分解材料, -所需数量[1]);
				cm.gainItem(高级女神装备[选择的女皇装备],1);
				cm.sendOk("#v"+高级女神装备[选择的女皇装备]+"##b#z"+高级女神装备[选择的女皇装备]+"# #k升级成功...");
		} else {
			cm.sendOk("#r失败! #k升级#v"+高级女神装备[选择的女皇装备]+"##b#z"+高级女神装备[选择的女皇装备]+"# #k需要"+所需数量[1]+"个#v"+分解材料+"#...");
		}
		cm.dispose();
		}
		if (三级链接 == 3) {
			选择的女神装备位置 = selection;
				if (cm.itemQuantity(分解材料) >= 所需数量[1]) {
				cm.removeSlot(1, 选择的女神装备位置, 1);
				cm.gainItem(分解材料, -所需数量[1]);
				cm.gainItem(高级神圣装备[选择的女神装备],1);
				cm.sendOk("#v"+高级神圣装备[选择的女神装备]+"##b#z"+高级神圣装备[选择的女神装备]+"# #k升级成功...");
		} else {
			cm.sendOk("#r失败! #k升级#v"+高级神圣装备[选择的女神装备]+"##b#z"+高级神圣装备[选择的女神装备]+"# #k需要"+所需数量[1]+"个#v"+分解材料+"#...");
		}
		cm.dispose();
		}
		if (三级链接 == 4) {
			选择的神圣装备位置 = selection;
				if (cm.itemQuantity(分解材料) >= 所需数量[1]) {
				cm.removeSlot(1, 选择的神圣装备位置, 1);
				cm.gainItem(分解材料, -所需数量[1]);
				cm.gainItem(高级革命装备[选择的神圣装备],1);
				cm.sendOk("#v"+高级革命装备[选择的神圣装备]+"##b#z"+高级革命装备[选择的神圣装备]+"# #k升级成功...");
		} else {
			cm.sendOk("#r失败! #k升级#v"+高级革命装备[选择的神圣装备]+"##b#z"+高级革命装备[选择的神圣装备]+"# #k需要"+所需数量[1]+"个#v"+分解材料+"#...");
		}
		cm.dispose();
		}
		if (三级链接 == 5) {
			选择的革命装备位置 = selection;
				if (cm.itemQuantity(分解材料) >= 所需数量[1]) {
				cm.removeSlot(1, 选择的革命装备位置, 1);
				cm.gainItem(分解材料, -所需数量[1]);
				cm.gainItem(高级宝藏装备[选择的革命装备],1);
				cm.sendOk("#v"+高级宝藏装备[选择的革命装备]+"##b#z"+高级宝藏装备[选择的革命装备]+"# #k升级成功...");
		} else {
			cm.sendOk("#r失败! #k升级#v"+高级宝藏装备[选择的革命装备]+"##b#z"+高级宝藏装备[选择的革命装备]+"# #k需要"+所需数量[1]+"个#v"+分解材料+"#...");
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
			
