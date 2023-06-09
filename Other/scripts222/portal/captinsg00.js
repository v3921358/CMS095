function enter(pi) {
    if (!pi.haveItem(4000381)) {
	pi.playerMessage(5, "你没有白色精华，无法进入。");
    } else {
	if (pi.getPlayerCount(541010100) <= 0) { // Capt. Lac Map
	    var captMap = pi.getMap(541010100);

	    captMap.resetFully();

	    pi.playPortalSE();
	    pi.warp(541010100, "sp");
	} else {
	    if (pi.getMap(541010100).getSpeedRunStart() == 0 && (pi.getMonsterCount(541010100) <= 0 || pi.getMap(541010100).isDisconnected(pi.getPlayer().getId()))) {
		pi.playPortalSE();
		pi.warp(541010100, "sp");
	    } else {
		pi.playerMessage(5, "战斗已经开始了，请稍等里面的人打完");
	    }
	}
    }
}