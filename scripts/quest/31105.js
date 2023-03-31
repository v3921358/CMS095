var status = -1;

function start(mode, type, selection) {
	if(qm.isQuestActive(31105)){
		qm.forceCompleteQuest();
		qm.dispose();
	}else{
		qm.sendNext("要想了解更多情况，必须首先找到赫丽娜。");
		// if (!qm.haveItem(2430159)) {
		// 	qm.gainItem(2430159, 1)
		// }
		qm.forceStartQuest();
		qm.dispose();
	}
}
function end(mode, type, selection) {
	qm.dispose();
}
