var status = -1;
//this quest is DRAGON TRAINING
function start(mode, type, selection) {
    qm.sendNext("去跟少爺交談.");
    qm.forceCompleteQuest();
    qm.dispose();
}

function end(mode, type, selection) {
    qm.gainExp(100);
    qm.forceCompleteQuest();
    qm.dispose();
}