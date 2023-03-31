function enter(pi) {
    // if (pi.isQuestActive(23970)) {
	// pi.forceCompleteQuest(23970);
	// pi.playerMessage("Quest complete.");
    // }
    if (pi.isQuestActive(23970)) {
        pi.forceStartQuest(23982, '1');
        pi.playerMessage(5, '石头中间里好像关着一个小小的生物。虽然试图进去，但门关着。');
        pi.dispose();
    } else {
        if (pi.isQuestActive(23971)) {
            pi.playerMessage(5, '救出矿石吞噬者宝宝。');
            pi.warp(931020030, 0);
            pi.dispose();
        } else {
            pi.dispose();
        }
    }
}