function enter(pi) {
    if (pi.getMap().getReactorByName("sMob1").getState() >= 1 && pi.getMap().getReactorByName("sMob2").getState() >= 1 && pi.getMap().getReactorByName("sMob3").getState() >= 1 && pi.getMap().getReactorByName("sMob4").getState() >= 1) {
	if (pi.isLeader()) {
	    pi.warpParty(925100500); //next
	} else {
	    pi.playerMessage(5, "必須队长进入。");
	}
    } else {
	pi.playerMessage(5, "門尚未打开.");
    }
}