var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        cm.dispose();
        return;
    }

    qm.forceStartQuest();
		
    qm.forceCompleteQuest();
    qm.dispose();

}
function end(mode, type, selection) {
    qm.forceStartQuest();
		
    qm.forceCompleteQuest();
    qm.dispose();
}
