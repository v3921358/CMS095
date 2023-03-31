var status = -1;

function start(mode, type, selection) {
	if(qm.isQuestActive(2218)){
		qm.forceCompleteQuest();
		qm.gainExp(300);
		qm.gainItem(4031894, -1);
		// qm.sendNext("优秀！！");
		qm.dispose();
		return;
	}else{
		qm.forceStartQuest();
		qm.sendNext("#p1052006#唠唠叨叨地说了一堆关于#o9300003#的奇怪的话。他说#m9300003#不是一开始就那么大的，它们是因为#b成长的魔法药#k而变大了的#o210100#……");
		qm.dispose();
	}
}
function end(mode, type, selection) {
	qm.sendNext("Thank you so much.");
	qm.forceCompleteQuest();
	qm.dispose();
}
