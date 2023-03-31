function action(mode, type, selection) {
    if (cm.getQuestStatus(6410) == 1) {
	cm.forceStartQuest(6411, "p2");
	cm.sendNext("謝謝你！");
    } else {
	cm.sendNext("请釋放怪物！");
    }
    cm.dispose();
}