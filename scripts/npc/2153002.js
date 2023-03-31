function action(mode, type, selection) {
	if (cm.haveItem(1003134,1,true,true) || cm.isQuestFinished(23947)) {
		cm.warp(310050000,0);
		cm.dispose();
	} else {
		cm.sendNext("对不起，您不能进入 .");
	}
	cm.safeDispose();
}