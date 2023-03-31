var status = -1;

function start(mode, type, selection) {
	
}
function end(mode, type, selection) {
	if(qm.isQuestActive(2233)){
		qm.forceCompleteQuest();
		qm.gainExp(3000);
		qm.sendNext("优秀！！");
		qm.dispose();
		return;
	}
	if ((qm.getPlayer().getJunior1() > 0||qm.getPlayer().isGM())&&!qm.isQuestActive(2233)) {
		qm.forceStartQuest();
	} else {
		qm.sendNext("请带着学院成员来见我！");
	}
	qm.dispose();
}
