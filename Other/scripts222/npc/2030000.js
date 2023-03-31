/**
	Jeff - El Nath : El Nath : Ice Valley II (211040200)
**/

var status = 0;

function start() {
    if (cm.haveItem(4031450)) {
	cm.warp(921100100, 0);
	cm.dispose();
    } else {
	status = -1;
	action(1, 0, 0);
    }
}

function action(mode, type, selection) {
    if (status == 1 && mode == 0 && cm.getPlayerStat("LVL") >= 40) {
	cm.sendNext("即使你的等級在高，很难真正去那里，但是如果你改变主意，请找我。毕竟我的工作就是保护这个地方。");
	cm.dispose();
	return;
    }
    if (mode == 1)
	status++;
    else
	status--;
    if (status == 0) {
	cm.sendNext("嗨我是傑夫你要前往此地方？");
    } else if (status == 1) {
	if (cm.getPlayerStat("LVL") >= 50) {
	    cm.sendYesNo("准备去了??");
	} else {
	    cm.sendPrev("你的等級好像不够。");
	}
    } else if (status == 2) {
	if (cm.getPlayerStat("LVL") >= 40) {
	    cm.warp(211040300, 5);
	}
	cm.dispose();
    }
}