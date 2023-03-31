var status = -1;

function start(mode, type, selection) {
 	// if (!qm.canHold(4031894, 1)) {
	//     qm.sendNext("Please make some space..");
	// } else {
	//     qm.gainItem(4031894, 1);
	//     qm.forceCompleteQuest();
	// }
	// qm.dispose();
}
function end(mode, type, selection) {
 	// if (!qm.canHold(4031894, 1)) {
	//     qm.sendNext("Please make some space..");
	// } else {
	//     qm.gainItem(4031894, 1);
	//     qm.forceCompleteQuest();
	// }
	// qm.dispose();

	// qm.sendNext("Thank you so much.");
	if(qm.isQuestActive(2215)&&qm.canHold(4031894, 1)){
		qm.forceCompleteQuest();
		qm.gainItem(4031894, 1);
		qm.gainExp(350);
		// qm.sendNext("优秀！！");
		qm.dispose();
		return;
	}else{
		qm.forceStartQuest();
		qm.dispose();
	}
}
