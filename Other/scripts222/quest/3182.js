var status = -1;

function start(mode, type, selection) {
	// qm.sendNext("非常感謝。");
	if(qm.isQuestActive(3182)){
		qm.forceCompleteQuest();
		qm.dispose();
	}else{
		if (!qm.haveItem(2430159)) {
			qm.gainItem(2430159, 1)
		}
		qm.forceStartQuest();
		qm.dispose();
	}
	
}
function end(mode, type, selection) {
	// qm.sendNext("非常感謝.");
	// qm.forceCompleteQuest();
	// qm.dispose();
}
