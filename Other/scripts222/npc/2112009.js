﻿function action(mode, type, selection) {
	    cm.removeAll(4001130);
	    cm.removeAll(4001131);
	    cm.removeAll(4001132);
	    cm.removeAll(4001133);
	    cm.removeAll(4001134);
	    cm.removeAll(4001135);
	var em = cm.getEventManager(cm.getMapId() == 926100600 ? "Romeo" : "Juliet");
    if (em != null) {
	var itemid = cm.getMapId() == 926100600 ? 4001160 : 4001159;
	if (!cm.canHold(itemid, 1)) {
	    cm.sendOk("你的背包空间不足.");
	    cm.dispose();
	    return;
	}
	cm.gainItem(itemid, 1);
    }
    //cm.gainNX(2500);
    cm.gainExp_PQ(120, 1.0);
    cm.gainExp_PQ(120, 1.0);
    //cm.getPlayer().modifyCSPoints(2, 15, true);
    cm.addTrait("will", 25);
    cm.addTrait("sense", 1);
    cm.getPlayer().endPartyQuest(1205);
    cm.warp(926100700,0);
    cm.dispose();
}