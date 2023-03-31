var status = -1;
function end(mode, type, selection) {
    if (qm.getPlayer().getLevel() >= 55) {
        qm.forceCompleteQuest();
        if (qm.getJob() == 431) {
            qm.changeJob(432);
            qm.gainItem(1132021, 1);
            qm.sendNext("转职成功.");
        }
        qm.dispose();
    } else {
        qm.sendOk("请55級以后再來找我吧。");
        qm.dispose();
    }
}

function start(mode, type, selection) {
    qm.dispose();
}
