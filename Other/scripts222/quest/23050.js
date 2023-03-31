var status = -1;

function start(mode, type, selection) {
	qm.dispose();
}

function end(mode, type, selection) {
	status++;
	if (qm.haveItem(4032743) && qm.getQuestStatus(23050) == 1) {
		if (status == 0) {
			qm.sendNext("你成功地破坏掉了黑色之翼的新武器！哈哈哈哈！你果然是个了不起的家伙！我说过我的眼睛绝不会错！没有什么比弓箭手的眼睛更亮的了！我就知道你一定可以做到！反抗者中有你这样的人，我感到很自豪！");
		} else {
			qm.gainItem(4032743, -1);
			qm.warp(310010000);
			qm.forceCompleteQuest();
			qm.dispose();
		}
	} else if (!qm.isQuestFinished(23050)) {
		qm.forceStartQuest();
		qm.dispose();
	}
}