/*
    Zakum Entrance
*/

function enter(pi) {
    if (pi.getQuestStatus(100200) < 1) {
	pi.playerMessage(5, "您好像还没准备好面对BOSS。");
	return false;

    } else if (!pi.haveItem(4001017)) {
	pi.playerMessage(5, "由于你没有火焰的眼，所以不能挑战进阶扎昆。");
	return false;
    }
    
    pi.playPortalSE();
    pi.warp(pi.getPlayer().getMapId() + 100, "west00");
    return true;
}