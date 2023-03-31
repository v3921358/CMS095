var status = -1;

function start(mode, type, selection) {
	// qm.sendNext("Thank you so much.");
	// qm.forceCompleteQuest();
	// qm.dispose();
	if(qm.isQuestActive(2217)){
		qm.forceCompleteQuest();
		qm.gainExp(300);
		qm.gainItem(4031894, -1);
		// qm.sendNext("优秀！！");
		qm.dispose();
		return;
	}else{
		qm.forceStartQuest();
		qm.sendNext("据#p1052102#说，#m103000000#的下水口好像从不久之前开始发出奇怪的气味。");
		qm.dispose();
	}
}
function end(mode, type, selection) {
	if(qm.isQuestActive(2217)){
		qm.forceCompleteQuest();
		qm.gainExp(300);
		qm.gainItem(4031894, -1);
		// qm.sendNext("优秀！！");
		qm.dispose();
		return;
	}else{
		qm.forceStartQuest();
		qm.dispose();
	}

}
