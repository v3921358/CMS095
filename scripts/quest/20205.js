/*
 * Cygnus 2nd Job advancement - Proof of test
 * Striker
 */

var status = -1;

function start(mode, type, selection) {
}

function end(mode, type, selection) {
    if (mode == 0) {
	if (status == 0) {
	    qm.sendNext("我猜你还沒准备好。");
	    qm.dispose();
	    return;
	} else if (status >= 2) {
	    status--;
	} else {
	    qm.dispose();
	    return;
	}
    } else {
	status++;
    }
    if (status == 0) {
	if (qm.getQuestStatus(20205) == 0) {
	    qm.forceStartQuest();
	    qm.dispose();
	} else {
	    if (qm.haveItem(4032100, 30)) {
		qm.sendYesNo("所以，你准备好二轉了？");
	    } else {
		qm.dispose(); // Hack
	    }
	}
    } else if (status == 1) {
	if (!qm.canHold(1142067)) {
	    qm.sendOk("请确认裝备栏是否足够。");
	    qm.dispose();
	} else {
	    qm.forceCompleteQuest();
	    if (qm.getJob() != 1510) {
		qm.changeJob(1510); // Striker
		qm.gainItem(4032100, -30);
		qm.gainItem(1142067, 1);
	    }
	    qm.sendNext("訓练已经結束。你现在皇家騎士團的騎士官员。");
	}
    } else if (status == 2) {
	qm.sendPrev("好运！");
	qm.dispose();
    }
}