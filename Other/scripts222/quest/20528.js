var status = -1;
var selectionLog = [];
function start(d, c, b) {
    qm.forceStartQuest();
    qm.dispose();
    
}
function end(mode, type, selection) {
    qm.forceCompleteQuest();
    qm.dispose();
}