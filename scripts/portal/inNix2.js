function enter(pi) {
	pi.warp(240020600,0);
    if (pi.getPlayer().getLevel() == 120 && !pi.getPlayer().haveItem(4000235)) {
	pi.spawnMonster(8180000, 1);  //火焰龙
    }
}