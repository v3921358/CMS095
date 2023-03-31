function enter(pi) {
	if (pi.getPlayer().getParty() != null && pi.isLeader()) {
		pi.warpParty(920010600);
		pi.playPortalSE();
	} else {
		pi.playerMessage(5,"请队长进入洞口。");
	}
}