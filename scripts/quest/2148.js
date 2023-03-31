var status = -1;

var status = -1;

function start(mode, type, selection) {
	if (mode == -1) {
		qm.dispose();
	} else {
		if (mode == 1)
			status++;
		else
			status--;

		if (status == 0) {
			qm.sendNext("什么事？\r\n#b#L0#听说过鬼树吗？#l");
		} else if (status == 1) {
			qm.sendNextPrev("鬼树？啊，是说那个很久以前消失的木妖吗？父亲的父亲小时候听说过这种树。据传闻说是每个枝头都有红布，据说是魂的血染成的。不过我也没有亲眼见过。所以也无法辨别真伪。\r\n#b#L0#没听到其他传闻吗？#l");
		} else if (status == 2) {
			qm.sendAcceptDecline("很可惜，我不是对传闻很有兴趣的人。");
		} else if (status == 3) {
			qm.forceCompleteQuest();
			qm.dispose();
		} 
	}
}
function end(mode, type, selection) {
	qm.dispose();
}
