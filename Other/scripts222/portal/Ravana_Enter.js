function enter(pi) {
	if (pi.getPlayer().getLevel() < 50) {
		pi.playerMessage(5, "You must be at least level 50.");
		return false;
	}
	pi.playerMessage(5, "攻击力无法挑战进阶怪物！，努力吧，骚年	");
	return true;
}