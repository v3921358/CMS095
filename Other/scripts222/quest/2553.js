var status = -1;

function start(mode, type, selection) {
    if (mode == -1) {
        qm.dispose();
    } else {
        if (mode == 1) {
            status++;
        } else {
            status--;
        }
        if (status == 0) {
            qm.forceCompleteQuest();
            qm.dispose();
        }
        qm.forceCompleteQuest();
        qm.dispose();
    }
    qm.forceCompleteQuest();
    qm.dispose();
}