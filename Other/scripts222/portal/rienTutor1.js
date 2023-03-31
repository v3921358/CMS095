function enter(pi) {
    if (pi.getQuestStatus(21010) == 2) {
	pi.playPortalSE();
	pi.warp(140090200, 1);
    } else {
	pi.playerMessage(5, "完成任務后才能进入下一张地图。");
    }
}