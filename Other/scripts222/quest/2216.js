var status = -1;

function start(mode, type, selection) {
	if(qm.isQuestActive(2216)){
		qm.forceCompleteQuest();
		qm.gainExp(300);
		qm.gainItem(4031894, -1);
		// qm.sendNext("优秀！！");
		qm.dispose();
		return;
	}else{
		qm.forceStartQuest();
		qm.sendNext("据#p9000008#说，#o6220000#虽然和#o3110100#很像，但是个头要大得多，甚至好像会使用魔法……不过这个家伙，普通话不是说得蛮好的嘛？");
		qm.dispose();
	}
}
function end(mode, type, selection) {
	qm.sendNext("Thank you so much.");
	qm.forceCompleteQuest();
	qm.dispose();
}
