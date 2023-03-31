

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (status >= 2 && mode == 0) {
	cm.sendOk("Alright, see you next time.");
	cm.dispose();
	return;
    }
    if (mode == 1) {
	status++;
    }
    else {
	status--;
    }
    if (status == 0) {
	if (cm.getQuestStatus(2055) == 1 && !cm.haveItem(4031039)) {
	    cm.gainItem(4031039, 1); // Shumi's Coin
	    cm.warp(555000000, 0);
	}
	else {
	    var rand = 1 + cm.getDoubleFloor(cm.getDoubleRandom() * 1);
	    if (rand == 1) {
		cm.gainItem(4010003, 0); // Adamantium Ore
	    }
	   
	    cm.warp(209080100, 0);
	}
	cm.dispose();
    }
}	

	