﻿function enter(pi) {
    if (pi.getMap().getCharactersSize() > 0 || pi.getMap(926110401).getCharactersSize() > 0) {
	pi.warpParty(926110401,0);
    } else {
	pi.playerMessage(5, "並不是每个人都在这裏。");
    }
	
}