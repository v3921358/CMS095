var status = -1;

function start(mode, type, selection) {
	// qm.sendNext("Thank you so much.");
	// qm.forceCompleteQuest();
	// qm.dispose();
}
function end(mode, type, selection) {
	// qm.sendNext("Thank you so much.");
	if(qm.isQuestActive(2214)){
		qm.forceCompleteQuest();
		qm.gainExp(300);
		// qm.sendNext("优秀！！");
		qm.dispose();
		return;
	}else{
		qm.forceStartQuest();
		qm.dispose();
	}

}
