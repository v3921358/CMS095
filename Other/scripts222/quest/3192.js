var status = -1;

function start(mode, type, selection) {
	if(qm.isQuestActive(3192)){
		qm.forceCompleteQuest();
		qm.dispose();
	}else{

		if (!qm.haveItem(2430180)) {
			qm.gainItem(2430180, 1)
		}
		qm.forceStartQuest();
		qm.dispose();
	}
}
function end(mode, type, selection) {
	qm.dispose();
}
