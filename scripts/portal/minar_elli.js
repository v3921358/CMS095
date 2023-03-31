function enter(pi) {
try {
    if (pi.haveItem(4031346)) {
	if (pi.getMapId() == 240010100) {
	    pi.playPortalSE();
	    pi.warp(101030100, "minar00");
	} else {
	    pi.playPortalSE();
	    pi.warp(240010100, "elli00");
	}
	pi.gainItem(4031346, -1);
	pi.playerMessage("魔法种子被消耗，你被轉移到某处。");
	return true;
    } else {
	pi.playerMessage("为了要傳送到神祕的地方需要魔法种子。");
	return false;
    }
} catch (e) {
    pi.playerMessage("Error: " + e);
}
}
