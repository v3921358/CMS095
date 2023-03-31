var status = -1;
function start() {
	if (cm.isQuestActive(3197) || cm.isQuestActive(3198)) {
		cm.warp(910001002, 0);
		cm.dispose();
	} else if (cm.isQuestActive(3195) || cm.isQuestActive(3196)) {
		cm.warp(910001001, 0);
		cm.dispose();
	} else {
		action(1,0,0);
	}
}

function action(mode, type, selection) {
    if (mode == 0) {
        cm.dispose();
        return;
    } else {
        status++;
    }
    if (status == 0) {
		var txt = "";
		if (cm.getPlayer().getProfessionLevel(92010000) > 0){
			if (cm.haveItem(4001480) > 0){
				txt += "#L1#进入新手秘密矿山，需要#v4001480# x 1\r\n\r\n";
			}
		}
		if (cm.getPlayer().getProfessionLevel(92010000) > 2){
			if (cm.haveItem(4001481) > 0){
				txt += "#L2#进入中级秘密矿山，需要#v4001481# x 1\r\n";
			}
		}
		if (cm.getPlayer().getProfessionLevel(92010000) > 4){
			if (cm.haveItem(4001569) > 0){
				txt += "#L3#进入高级秘密矿山，需要#v4001569# x 1\r\n";
			}
		}
		if (cm.getPlayer().getProfessionLevel(92010000) > 6){
			if (cm.haveItem(4001571) > 0){
				txt += "#L4#进入专家秘密矿山，需要#v4001570# x 1\r\n";
			}
		}
		if (cm.getPlayer().getProfessionLevel(92000000) > 0){
			if (cm.haveItem(4001482) > 0){
				txt += "#L5#进入新手秘密农场，需要#v4001482# x 1\r\n";
			}
		}
		if (cm.getPlayer().getProfessionLevel(92000000) > 2){
			if (cm.haveItem(4001483) > 0){
				txt += "#L6#进入中级秘密农场，需要#v4001483# x 1\r\n";
			}
		}
		if (cm.getPlayer().getProfessionLevel(92000000) > 4){
			if (cm.haveItem(4001570) > 0){
				txt += "#L7#进入高级秘密农场，需要#v4001571# x 1\r\n";
			}
		}
		if (cm.getPlayer().getProfessionLevel(92000000) > 6){
			if (cm.haveItem(4001572) > 0){
				txt += "#L8#进入专家秘密农场，需要#v4001572# x 1\r\n";
			}
		}
		cm.sendSimple("你想要进入哪个采集地图？？\r\n\r\n" + txt + "");
	} else if (status == 1){
		if (selection == 1){
			if (cm.getBossLogD("矿山1") > 0){
				cm.sendOk("新手秘密矿山每天可以进入1次，你今天已经进入过1次了！")
				cm.dispose();
			} else {
				cm.warp(910001005,0);
				cm.gainItem(4001480,-1);
				cm.setBossLog("矿山1");
				cm.dispose();
			}
		}
		if (selection == 2){
			if (cm.getBossLogD("矿山2") > 0){
				cm.sendOk("中级秘密矿山每天可以进入1次，你今天已经进入过1次了！")
				cm.dispose();
			} else {
				cm.warp(910001006,0);
				cm.gainItem(4001481,-1);
				cm.setBossLog("矿山2");
				cm.dispose();
			}
		}
		if (selection == 3){
			if (cm.getBossLogD("矿山3") > 0){
				cm.sendOk("高级秘密矿山每天可以进入1次，你今天已经进入过1次了！")
				cm.dispose();
			} else {
				cm.warp(910001008,0);
				cm.gainItem(4001569,-1);
				cm.setBossLog("矿山3");
				cm.dispose();
			}
		}
		if (selection == 4){
			if (cm.getBossLogD("矿山4") > 0){
				cm.sendOk("专家秘密矿山每天可以进入1次，你今天已经进入过1次了！")
				cm.dispose();
			} else {
				cm.warp(910001010,0);
				cm.gainItem(4001571,-1);
				cm.setBossLog("矿山4");
				cm.dispose();
			}
		}
		if (selection == 5){
			if (cm.getBossLogD("农场1") > 0){
				cm.sendOk("新手秘密农场每天可以进入1次，你今天已经进入过1次了！")
				cm.dispose();
			} else {
				cm.warp(910001003,0);
				cm.gainItem(4001482,-1);
				cm.setBossLog("农场1");
				cm.dispose();
			}
		}
		if (selection == 6){
			if (cm.getBossLogD("农场2") > 0){
				cm.sendOk("中级秘密农场每天可以进入1次，你今天已经进入过1次了！")
				cm.dispose();
			} else {
				cm.warp(910001004,0);
				cm.gainItem(4001483,-1);
				cm.setBossLog("农场2");
				cm.dispose();
			}
		}
		if (selection == 7){
			if (cm.getBossLogD("农场3") > 0){
				cm.sendOk("高级秘密农场每天可以进入1次，你今天已经进入过1次了！")
				cm.dispose();
			} else {
				cm.warp(910001007,0);
				cm.gainItem(4001570,-1);
				cm.setBossLog("农场3");
				cm.dispose();
			}
		}
		if (selection == 8){
			if (cm.getBossLogD("农场4") > 1){
				cm.sendOk("专家秘密农场每天可以进入2次，你今天已经进入过2次了！")
				cm.dispose();
			} else {
				cm.warp(910001009,0);
				cm.gainItem(4001572,-1);
				cm.setBossLog("农场4");
				cm.dispose();
			}
		}
		
	}
        
}

