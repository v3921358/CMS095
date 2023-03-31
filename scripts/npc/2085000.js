var status = 0;
var itemId = new Array(2047000, 2047002, 2047100, 2047102, 2047200, 2047201, 2047202, 2047203);
var set = 0;

function start() {
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 0) {
        cm.dispose();
    }
    if (mode == 1) {
        status++;
    }
    if (status == 0) {
        cm.sendSimple("要是我也能飞上天就好了……如果你对飞行感兴趣的话，可以去见见神木村的村长。\r\n#b#L0#购买炼製书。");
    } else if (status == 1) {
        var selStr = "要想强化存在耐久度的装备，需要炼製书。你需要什么？";
        for (var i = 0; i < itemId.length; i++) {
            selStr += "\r\n#b#L" + i + "#领取#t" + itemId[i] + "#。#l";
        }
			selStr += "\r\n#b#L888#领取#v1012370##z1012370#。#l";
        cm.sendSimple(selStr);
    } else if (status == 2) {
		if (selection == 888) {
			if (cm.getPlayer().getBossLogS("御龙魔") >= 20) {
				if (cm.getPlayer().getBossLogS("枫叶面具") > 0){
					cm.sendOk("你已经领取过一次了！");
					cm.dispose();
					return;
				}
				cm.gainItem(1012370,1);
				cm.setBossLog("枫叶面具");
				cm.dispose();
			} else {
				cm.sendOk("你还没有完成20次御龙魔组队任务，你当前已完成"+cm.getPlayer().getBossLogS("御龙魔")+"次");
				cm.dispose();
				return;
			}
		}
        if (cm.haveItem(4001401, 10)) {
            cm.gainItem(4001401, -10);
            cm.gainItem(itemId[selection], 1);
            cm.sendOk("恭喜你成功领取了#b#t" + itemId[selection] + "##k，如果还需要的话请来找我吧！");
        } else {
            cm.sendNext("要想领取#b#t" + itemId[selection] + "##k，需要10个#b#t4001401##k。需要炼製书的话，就去消灭#b#o8300007##k，搜集#b#t4001401##k。");
        }
        cm.dispose();
    }
}