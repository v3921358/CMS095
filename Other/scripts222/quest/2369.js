var status = -1;
function end(mode, type, selection) {
    if (qm.getPlayer().getLevel() >= 30) {
        if (qm.getPlayer().getJob() == 430) {
            qm.changeJob(431);
            qm.sendNext("转职陳功.");
        }
        qm.forceCompleteQuest();
        qm.dispose();
    } else {
        qm.sendOk("请30級以后再來找我吧。");
        qm.dispose();
    }
}

function start(mode, type, selection) {
    qm.dispose();
}
