function action(mode, type, selection) {
    if (cm.isQuestActive(2166)) {
        cm.forceCompleteQuest(2166);
        // cm.updateInfoQuest(2166, "5");
        cm.gainExp(400);
        cm.sendOk("感受到了石头的力量。");
    }
    cm.dispose();
}