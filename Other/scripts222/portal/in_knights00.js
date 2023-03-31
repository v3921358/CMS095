function enter(pi) {
    if (pi.isQuestActive(31124)) {
        pi.forceCompleteQuest(31124);
        pi.playerMessage("看来骑士增兵了许多，快回去报告阿勒斯。");
        pi.warp(271010000,0);
        pi.dispose();
        return;
    }
    pi.warp(271030010, 0);
}