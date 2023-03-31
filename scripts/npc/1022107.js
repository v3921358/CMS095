function action(mode, type, selection) {
	if (cm.isQuestActive(22530)) {
		if (!cm.canHold(1952000,1)) {
			cm.sendOk("你的背包空间不足。");
		} else {
			cm.forceCompleteQuest(22530);
			cm.gainExp(710);
			cm.gainItem(1952000,1);
			cm.getPlayer().gainSP(1, 1);
			cm.sendOk("你检查一下牌子。完成警衛的要求。");
		}
	} else {
		cm.sendOk("这是一个征兆.");
	}
	cm.dispose();
}