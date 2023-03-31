var status = -1;

function start(mode, type, selection) {
	
	// qm.gainExp(11280);
	// qm.getPlayer().setFame(qm.getPlayer().getFame() + 8);
	if(qm.isQuestActive(2228)){
		qm.forceCompleteQuest();
		qm.dispose();
		return;
	}else{
		qm.forceStartQuest();
		qm.sendNext("#p1032108#的脸色比以前好了很多，非常感谢。");
		qm.dispose();
	}
	
}
function end(mode, type, selection) {
	qm.dispose();
}