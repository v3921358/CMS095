var status = -1;
//this quest is WHERES BOOK
function start(mode, type, selection) {
	qm.sendNext("去克林城与伊卡洛斯交談.");
	qm.forceStartQuest();
	qm.dispose();
}

function end(mode, type, selection) {
	qm.gainExp(500);
	qm.forceCompleteQuest();
	qm.dispose();
}