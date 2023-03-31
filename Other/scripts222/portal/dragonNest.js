function enter(pi) {
	if (pi.haveItem(4001094)) {
		if (pi.getQuestStatus(3706) > 0) {
			if (pi.getPlayerCount(240040611) == 0) {
				pi.removeNpc(240040611, 2081008);
				pi.resetMap(240040611);
				pi.playPortalSE();
				pi.warp(240040611, "sp");
			} else {
				pi.playerMessage(5, "有人已经在里面了，请稍后再进。");
			}
		} else {
			pi.playerMessage(5, "请先接取消失的英雄任务。");
		}
	} else {
		pi.playerMessage(5, "进入者需要拥有九灵龙的蛋。");
	}
}