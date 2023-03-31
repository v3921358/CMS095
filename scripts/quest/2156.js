var status = -1;

function start(mode, type, selection) {
}

function end(mode, type, selection) {

    if (qm.isQuestActive(2156) && qm.haveItem(2210006)) {
        qm.forceCompleteQuest();
        qm.gainItem(2210006, -1);
        qm.getPlayer().addFame(3);
        qm.gainExp(7500);
        qm.dispose();
        return;
    } else {
        if (!qm.haveItem(2210006)) {
            qm.sendNext("你好像没有彩色蜗牛壳哦！！");
            qm.dispose();
            return;
        }
        qm.forceStartQuest();
    }
    qm.dispose();
}
