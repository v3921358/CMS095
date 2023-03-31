var status = -1;

function action(mode, type, selection) {
    var em = cm.getEventManager("Juliet");
    if (em == null) {
	cm.dispose();
	return;
    }
    if (!cm.canHold(4001131,1)) {
	cm.sendOk("你的背包空间不足。");
	cm.dispose();
	return;
    }
    if (cm.getPlayer().getMapId() == 926110000) { //just first stage
	if (cm.getDoubleRandom() < 0.1) {
	    if (em.getProperty("stage1").equals("0")) {
		em.setProperty("stage1", "1");
		cm.getMap().setReactorState();
		cm.dispose();
	    }
	} else if (cm.getDoubleRandom() < 0.05) {
	    if (em.getProperty("stage").equals("0")) {
		cm.gainItem(4001131,1);
		cm.dispose();
	    }
	}
    }
    cm.dispose();
}