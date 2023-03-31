function enter(pi) {
	if (pi.getQuestStatus(21610) == 1) {
	if (pi.getPlayerCount(921110000) == 0) {
		pi.warp(921110000, 1);
	} else {
		pi.playerMessage(5, "已经有人在里面挑战了请稍后在嘗试。");
	}
	} else {
		pi.playerMessage(5, "你好像不能进入这个門。");
}
}