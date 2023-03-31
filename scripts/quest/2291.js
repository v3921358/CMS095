var status = -1;

function start(mode, type, selection) {
	
}

function end(mode, type, selection) {
   // qm.forceStartQuest(6031);//给完成任务条件
	//qm.forceStartQuest(6032);//开始任务
	// qm.forceCompleteQuest();//完成任务
	// qm.dispose();
	if(qm.isQuestActive(2291)){
		qm.forceCompleteQuest();
		qm.gainExp(500);
		qm.gainItem(4032521,-10);
		// qm.sendNext("优秀！！");
		qm.dispose();
		return;
	} else {
		qm.forceStartQuest();
		// qm.sendNext("请带着学院成员来见我！");
	}
	qm.dispose();
	
}