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
			qm.sendNext("快来吧。有什么事吗？\r\n#b#L0# 了解鬼树吗？#l");
		} else if (status == 1) {
			qm.sendNextPrev("是在帮#p1022006# 博士的研究吗？是啊，我也是受博士之托来调查，不过没有什么发现。只知道最近与#m102000000#接壤的魔法林开始干枯了。虽然速度较慢，但一定要引起警觉。\r\n#b#L0#好，感谢你抽空帮我。#l");
		} else if (status == 2) {
			qm.sendAcceptDecline("很抱歉，没能帮上什么忙。");
		} else if (status == 3) {
			qm.forceCompleteQuest();
			qm.dispose();
		} 
	}
}
function end(mode, type, selection) {
	qm.dispose();
}
