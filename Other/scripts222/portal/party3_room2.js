function enter(pi) {
	if (pi.getPlayer().getParty() != null && pi.isLeader()) {
		pi.warpParty(920010300);
		pi.playPortalSE();
	} else {
		pi.playerMessage(5,"Ո��L�M�붴�ڡ�");
	}
}