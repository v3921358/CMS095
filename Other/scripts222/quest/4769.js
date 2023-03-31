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
			if (qm.getQuestStatus(4769) == 2) {
				qm.sendOk("你已经领取过奖励，继续努力到71级可以获得更多奖励喔");
				qm.dispose();
			} else {
				qm.sendNext("恭喜你当前等级已经到达#b70#k级。");
			}
		} else if (status == 1) {
			if (qm.canHold(1012057)) {
				qm.sendOk("恭喜你获得系统奖励！\r\n\r\n#fUI/UIWindow.img/QuestIcon/4/0#\r\n#v1012057#(全属性4) 1个");
				qm.gainItemB(1012057, 1, 0, 0, 0, "", 4, 4, 4, 4, 4, 4, 0);
				qm.forceCompleteQuest(4769);
				qm.dispose();
				
			} else {
				qm.sendOk("您的背包已满。");
				qm.dispose();
				return;
			}
		}
	}
}