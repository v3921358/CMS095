/*
 * Cygnus 1st Job advancement - Striker
 */

var status = -1;

function end(mode, type, selection) {
    if (mode == 0) {
	if (status == 0) {
	    qm.sendNext("这个决定..非常重要.");
	    qm.safeDispose();
	    return;
	}
	status--;
    } else {
	status++;
    }
    if (status == 0) {
	qm.sendYesNo("你决定好了嘛? 这会是你最后的决定唷, 所以想清楚你要做什么. 你想要成为 閃雷悍将嘛?");
    } else if (status == 1) {
	qm.sendNext("恭喜成功转职。");
	if (qm.getJob() != 1500) {
	    qm.gainItem(1482014, 1);
	    qm.gainItem(1142066, 1);
		qm.resetStats(4, 4, 4, 4);
	    qm.expandInventory(1, 4);
	    qm.expandInventory(4, 4);
	    qm.changeJob(1500);
	}
	qm.forceCompleteQuest();
    } else if (status == 2) {
	qm.sendNextPrev("我还擴充您身上的裝备栏空间");
    } else if (status == 3) {
	qm.sendNextPrev("好运！.");
	qm.safeDispose();
    }
}