function start() {
	cm.sendYesNo("你想现在出去吗?");
}

function action(mode, type, selection) {
    if (mode == 1) {
	cm.warp(100000000, 0);
    }
    cm.dispose();
}