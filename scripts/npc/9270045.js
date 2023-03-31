function start() {
	cm.sendYesNo("你想要离开这里吗?");
}

function action(mode, type, selection) {
    	if (mode == 1) {
		cm.warp(541020700,6);
	}
	cm.dispose();
}
