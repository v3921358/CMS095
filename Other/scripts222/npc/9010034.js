function start() {
	cm.sendYesNo("Would you like to go in?");
}

function action(mode, type, selection) {
    	if (mode == 1) {
		cm.warp(502050600,1);
	}
	cm.dispose();
}