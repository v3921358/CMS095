function enter(pi) {
    if (!pi.haveMonster(9300216)) {
	pi.playerMessage("请先把怪物杀光。");
    } else {
	pi.dojoAgent_NextMap(true, false);
    }
}