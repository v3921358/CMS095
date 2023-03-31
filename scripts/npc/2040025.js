/*
	Second Eos Rock - Ludibrium : Eos Tower 71st Floor (221022900)
*/

var status = 0;
var map;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (status >= 0 && mode == 0) {
	cm.dispose();
	return;
    }
    if (mode == 1)
	status++;
    else
	status--;
    if (status == 0) {
	if (cm.haveItem(4001020)) {
	    cm.sendSimple("需要什么服務吗？？ #b\r\n#L0#爱奧斯塔 (100樓)#l\r\n#L1#爱奧斯塔 (32樓)#l");
	} else {
	    cm.sendOk("你需要有#t4001020# 才可以啟动。");
	    cm.dispose();
	}
    } else if (status == 1) {
	if (selection == 0) {
	    cm.sendYesNo("你是否要使用#t4001020# 傳送到#m221023200# 呢？？");
	    map = 221023200;
	} else {
	    cm.sendYesNo("你是否要使用#t4001020# 傳送到#m221021200# 呢？？");
	    map = 221021200;
	}
    } else if (status == 2) {
	cm.gainItem(4001020, -1);
	cm.warp(map, 3);
	cm.dispose();
    }
}