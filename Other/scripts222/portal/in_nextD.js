function enter(pi) {
    if (pi.isQuestActive(31144)) {
        pi.forceCompleteQuest(31144);
        pi.playerMessage( 5,"希纳斯的攻击破坏了六岔路口，现在无法移动到任何地方。");
        pi.warp(271010000, 0);
        pi.dispose();
        return;
    }
    pi.playerMessage(5,'希纳斯的攻击破坏了六岔路口，现在无法移动到任何地方。');
}