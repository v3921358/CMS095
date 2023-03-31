function start() {
    var status = cm.getQuestStatus(20706);
    
    if (status == 0) {
        cm.sendNext("好像有没有可疑的事物。.");
    } else if (status == 1) {
        cm.forceCompleteQuest(20706);
        cm.sendNext("你发现了阴影！请去找 #p1103001#.");
    } else if (status == 2) {
        cm.sendNext("阴影已经被发现了. 请去找 #p1103001#.");
    }
    cm.dispose();
}
function action(mode, type, selection) {
    cm.dispose();
}