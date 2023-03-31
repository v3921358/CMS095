var status = -1;

function action(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
	if (status == 0) {
	    cm.dispose();
	}
	status--;
    }
    if (status == 0) {
	cm.sendYesNo("你想去克里塞吗?");
    } else if (status == 1) {
	if (cm.getMap(200100000) == null) {
	    cm.sendOk("克里塞尚未开放.");
	} else {
	    cm.warp(200100000, 0);
	}
	cm.dispose();
    }
}