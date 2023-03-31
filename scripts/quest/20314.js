/*
 * Cygnus 3rd Job advancement - Night Walker
 */

var status = -1;

function start(mode, type, selection) {
    if (mode == 0 && status == 1) {
	qm.sendNext("我猜你还沒准备好。");
	qm.dispose();
	return;
    } else if (mode == 0) {
	status--;
    } else {
	status++;
    }

    if (status == 0) {
	qm.sendNext("你所带回來的寶石是神獸的眼淚，它擁有非常强大的力量。如果被黑磨法师給得手了，那我们全部都可能要倒大楣了....");
    } else if (status == 1) {
	qm.sendYesNo("女皇为了報答你的努力，将任命你为皇家騎士團的上級騎士，你准备好了嘛？");
    } else if (status == 2) {
	if (qm.getPlayerStat("RSP") > (qm.getPlayerStat("LVL") - 70) * 3) {
	    qm.sendNext("请确认你的技能點數點完沒。");
	} else {
	    if (qm.canHold(1142068)) {
		qm.gainItem(1142068, 1);
		qm.changeJob(1411);
		qm.gainAp(5);
		qm.sendOk("因为这一刻，你现在的騎士警长。從这一刻起，你應隨身攜带自己以尊嚴和尊重你的相称新標題天鵝騎士的騎士警长。");
	    } else {
		qm.sendOk("请先把道具栏空出一些空间哦。");
	    }
	}
	qm.dispose();
    }
}

function end(mode, type, selection) {
}