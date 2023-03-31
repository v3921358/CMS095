function start() {
	cm.sendYesNo("Would you like be warped to the Entrance of Zakum's Altar?");
}

function action(mode, type, selection) {
    	if (mode == 1) {
		cm.warp(211042400,1);
	}
	cm.dispose();
}