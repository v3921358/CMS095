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
			qm.sendNext("…什么事儿?\r\n#b#L0#听说过鬼树吗？#l");
		} else if (status == 1) {
			qm.sendNextPrev("好像是听到了胆小鬼们的故事了吧。没有什么鬼树。我在勇士部落的石山上修炼了很多年，没见过也没听说过那种树。\r\n#b#L0#哦，是吗？...#l");
		} else if (status == 2) {
			qm.sendAcceptDecline("但是听说最近北部岩山的不明袭击事件正在增加，我有点担心……");
		} else if (status == 3) {
			qm.forceCompleteQuest();
			qm.dispose();
		} 
	}
}
function end(mode, type, selection) {
	qm.dispose();
}
