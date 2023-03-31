var status = -1;

function start(mode, type, selection) {
	if(qm.isQuestActive(2218)){
		qm.forceCompleteQuest();
		qm.gainExp(300);
		qm.gainItem(4031894, -1);
		// qm.sendNext("优秀！！");
		qm.dispose();
		return;
	}else{
		qm.forceStartQuest();
		qm.sendNext("据#p1052103#说，#m103000000#村的魔法师#b#p9020000##k最近看上去非常不安……不知是不是和阴谋有什么关系？");
		qm.dispose();
	}
}
function end(mode, type, selection) {
	qm.sendNext("Thank you so much.");
	qm.forceCompleteQuest();
	qm.dispose();
}
