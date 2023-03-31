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
			qm.sendNext("你好，旅行者你今天过来有什么事儿吗？\r\n#b#L0#听说过鬼树吗?#l");
		} else if (status == 1) {
			qm.sendNextPrev("噢！听说过那个传闻吗？不久前#m100000000#的#p1012108#帮母亲去#m102000000#做事，回来的路上见到了鬼。\r\n#b#L0#真的吗？#l");
		} else if (status == 2) {
			qm.sendAcceptDecline("是很晚回到#m100000000#的路上，好像在黑暗中踩到了树枝，看了下周围，发现有一个犀利的眼神，感觉像要吃掉卡米拉。\r\n#b#L0#?发生了这种事情哦...#l");
		} else if (status == 3) {
			qm.sendNext("#p1012108#吓得晕过去了。天亮后大人们重新去看了那地方，不过什么都没发现。肯定是鬼吧。怎么办？我都怕得不敢出去了。");
		} else if (status == 4) {
			qm.forceStartQuest();
			qm.dispose();
		}
	}
}
function end(mode, type, selection) {
	qm.dispose();
}
