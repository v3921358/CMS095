var status = -1;

function end(mode, type, selection) {
    qm.forceCompleteQuest();
    if (qm.getPlayer().getLevel() >= 20) {
        if (qm.getPlayer().getJob() == 400) {
            qm.changeJob(430);
			qm.gainItem(1342000, 1);
            qm.resetStats(4, 25, 4, 4);
            qm.expandInventory(1, 4);
            qm.expandInventory(2, 4);
            qm.expandInventory(3, 4);
            qm.expandInventory(4, 4);
            qm.sendOk("转职成为双刀客.");
        }
        qm.dispose();
    } else {
        qm.sendOk("请20级以后再来找我吧。");
        qm.dispose();
    }
}

function start(mode, type, selection) {
    qm.dispose();
}