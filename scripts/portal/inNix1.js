function enter(pi) {
	pi.warp(240020600,0);
    if (pi.getPlayer().getLevel() == 120 && !pi.getPlayer().haveItem(4000243)) {
	pi.spawnMonster(8180001, 1);  //澶╅拱
    }
}