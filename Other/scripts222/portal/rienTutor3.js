function enter(pi) {
    if (pi.getQuestStatus(21012) == 2) {
	pi.playPortalSE();
	pi.warp(140090400, 1);
    } else {
	pi.playerMessage(5, "完成任務后才能进入下一张地图。");
    }
}