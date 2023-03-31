﻿/* Kedrick
	Fishking King NPC
*/

var status = -1;
var sel;
var t = 1;//cm.getDoubleFloor(cm.getDoubleRandom()*2);
function action(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
		if (status == 0) {
			cm.dispose();
			return;
		}
	status--;
    }

    if (status == 0) {
	if( t == 0 ) {
            cm.sendSimple("我能为您做什么吗？？#b\n\r #L1#买普通鱼饵。#l \n\r #L3#使用高级的鱼饵。\n\r #L4#领取#z3011000#。#l#k");
        } else {
            cm.sendSimple("我能为您做什么吗？？#b\n\r #L3#使用高级鱼饵。#l \n\r #L1#买普通鱼饵。\n\r #L4#领取#z3011000#。#l#k");
        }
  } else if (status == 1) {
	sel = selection;
		if (sel == 4) {
			if (cm.canHold(3011000,1)){
				cm.gainItem(3011000,1);
				cm.sendOk("领取成功！");
				cm.dispose();
			}
		}
        if (sel == 3) {
            if (cm.canHold(2300001,120) && cm.haveItem(5350000,1)) {
		    cm.gainItem(2300001, 120);
		    cm.gainItem(5350000,-1);
		    cm.sendNext("开心钓鱼吧！");
		
	    } else {
		cm.sendOk("请确认是否有高级的鱼饵罐头，或者检查您的道具栏有没有满了。");
	    }
	    cm.safeDispose();
        } else if (sel == 1) {
            cm.sendYesNo("请问确定要花 200 万 金币 买 120 个普通鱼饵？？");
	}
    } else if (status == 2) {
            if (cm.canHold(2300000,120) && cm.getMeso() >= 2000000) {
		    cm.gainMeso(-2000000);
		    cm.gainItem(2300000, 120);
		    cm.sendNext("开心钓鱼吧！");
	    } else {
		cm.sendOk("请确认是否有足够的金币，或者检查您的道具栏有没有满了。");
	    }
	    cm.safeDispose();
	}
}