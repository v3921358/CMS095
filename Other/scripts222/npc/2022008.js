function action(mode, type, selection) {
    cm.sendNext("我是莱格斯，被密封起来了，请去找鲁碧解救我！.");
    if (cm.isQuestActive(3122)) {
	cm.forceCompleteQuest(3122);
    }
    cm.dispose();
}