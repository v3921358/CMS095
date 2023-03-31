function start() {
	if (!cm.canHoldByType(4, 1)) {
        cm.sendOk("请确认背包是否已经满了。");
        cm.dispose();
        return;
    }
    if (cm.getPlayer().getBossLogD("D片兌換") >= 2) {
    cm.sendOk("你今天已经換过了");
    cm.dispose();
    return;
    }
    if (cm.haveItem(4031172)) {
    cm.getPlayer().setBossLog("D片兌換");
    cm.gainItem(4031179, 1);
                cm.sendOk("兌換成功。");
                cm.dispose();
                return;
            } else {
                cm.sendOk("你沒有#t4031172##i4031172#。");
                cm.dispose();
                return;
            }
            break;
}