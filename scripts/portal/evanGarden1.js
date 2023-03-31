function enter(pi) {
	if(pi.isQuestActive(22008)){
		pi.warp(100030103, "west00");
	} else {
		pi.playerMessage("你不能沒有理由去后院 ");
    } 
	return true;
}  