function enter(pi) {
	if (pi.isQuestActive(31149)) {
		pi.forceCompleteQuest(31149);
		pi.playerMessage("已确认希纳斯位置，需要一个梦的钥匙进入。");
		pi.warp(271010000, 0);
		pi.dispose();
		return;
	} else if (pi.haveItem(4032923)) {
		pi.warp(271040000, 0);
		pi.gainItem(4032923, -1);
	} else {
		pi.playerMessage("需要一个梦的钥匙进入。");
	}
}