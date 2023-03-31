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
    if (cm.getPlayer().getLevel() < 50) {
	cm.sendOk("在你受伤之前赶快离开... ");
	cm.dispose();
	return;
    }
    if (status == 0) {
	cm.sendYesNo("你看起来很强，你想去巴尔罗格神庙吗？");
    } else if (status == 1) {
	cm.warp(105100100);
	cm.dispose();
    }
}