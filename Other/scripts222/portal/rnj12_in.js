function enter(pi) {
    if (pi.getMap().getCharactersSize() > 0 || pi.getMap(926100401).getCharactersSize() > 0) {
	pi.warpParty(926100401,0);
    } else {
	pi.playerMessage(5, "不是每个人都在这裏.");
    }
}