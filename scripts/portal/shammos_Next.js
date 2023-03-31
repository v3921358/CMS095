function enter(pi) {
try {
	if (pi.getPlayer().getParty() != null  && pi.isLeader()) { //&& pi.getMap().getMonsterById(9300275) == null
		if (pi.getPlayer().getEventInstance() != null) {
			pi.warpParty_Instanced(((pi.getPlayer().getMapId() / 100) + 1) * 100 - (pi.getPlayer().getMapId() % 100));
		} else {
			pi.warpParty(((pi.getPlayer().getMapId() / 100) + 1) * 100 - (pi.getPlayer().getMapId() % 100));
		}
		pi.playPortalSE();
	} else {
		pi.playerMessage(5,"队长带侏儒來此。");
	}
} catch (e) {
	pi.playerMessage(5, "Error: " + e);
}
}