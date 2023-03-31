var status = -1;
//this quest is CALL DRAGON
function start(mode, type, selection) {
    qm.sendNext("去找斯坦长老談话。");
    qm.forceStartQuest();
    qm.dispose();
}

function end(mode, type, selection) {
    qm.gainExp(110);
    qm.forceCompleteQuest();
    qm.dispose();
}