function enter(pi) {
    if (pi.haveItem(4032922)) {
	pi.warp(271030100,0);
    } else {
	pi.playerMessage("需要皇家騎士團的勳章才能進入.");
    }
}