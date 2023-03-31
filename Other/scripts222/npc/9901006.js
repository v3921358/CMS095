function start() {
	cm.sendYesNo("Would you like be warped to the Entrance of Chaos Zakum's Altar?");
}

function action(mode, type, selection) {
    	if (mode == 1) {
		cm.warp(211042401,1);
	}
	cm.dispose();
}