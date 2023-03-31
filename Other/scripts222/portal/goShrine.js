function enter(pi) {
	if (pi.getPlayer().getLevel() < 50) {
		pi.playerMessage(5, "You must be at least level 50.");
		return false;
	}
	pi.playerMessage(5, "怪物过于强大，请在灿的帮助下，进行挑战~~~！！	");
	return true;
}