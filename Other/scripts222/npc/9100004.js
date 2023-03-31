function start() {
	cm.sendYesNo("Would you like to be warped into the Quest Map?");
}

function action(mode, type, selection) {
    	if (mode == 1) {
		cm.warp(240070000,1);
	}
	cm.dispose();
}