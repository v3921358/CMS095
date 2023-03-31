var status = -1;

//we hate cygnus ;P

function start(mode, type, selection) {
    qm.forceStartQuest();
    qm.forceStartQuest(23981, '0');
    qm.setQuestCustomData("1000000");
    qm.gainItem(4220179, 1);
    qm.dispose();
}

function end(mode, type, selection) {
    qm.forceCompleteQuest();
    qm.dispose();
}