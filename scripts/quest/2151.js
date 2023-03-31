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
			qm.sendNext("找我有什么事儿吗？\r\n#b#L0#知道鬼树吗？#l");
		} else if (status == 1) {
			qm.sendNextPrev("鬼树... 是说#b#o3220000##k吧。\r\n#b#L0##o3220000#是什么?#l");
		} else if (status == 2) {
			qm.sendAcceptDecline("当#m102000000#是个森林时，就开始存在非常古老的树。不过经历了很长的岁月后树开始愤怒了。看着破坏森林的人们发怒，看着干枯的森林发怒。\r\n#b#L0#之后怎么样了？#l");
		} else if (status == 3) {
			qm.sendNext("结果树的愤怒使得它变成了怪兽，现在更是变成了肆意吸收大地养分的怪兽。不要了解太多。我能理解你的好奇心，但他是所有木妖之王。不要想得太简单。");
		} else if (status == 4) {
			qm.forceCompleteQuest();
			qm.dispose();
		}
	}
}
function end(mode, type, selection) {
	qm.dispose();
}
