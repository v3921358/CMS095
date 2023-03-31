function start() {
	cm.sendYesNo("Would you like to go in?");
}

function action(mode, type, selection) {
    	if (mode == 1) {
		cm.warp(310040200,1);
	}
	cm.dispose();
}