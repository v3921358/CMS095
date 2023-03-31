var status = -1;

function action(mode, type, selection) {
    if (mode == 1)
        status++;
    else
        status--;
    if (status == 0) {
		if (cm.getQuestStatus(21612) == 1 || cm.getQuestStatus(21614) == 1) {
			cm.sendNext("你是來找我们老大的是吧...");
		} else {
			cm.sendOk("是人類吗？？沒事的话赶緊离开这里吧！");
			cm.dispose();
		}
    } else if (status == 1) {
		cm.sendNext("那我就带你去見我们老大吧!");
	} else if (status == 2) {
		cm.warp(140010210,0);
		cm.dispose();
	}
}