function enter(pi) {
    if (pi.getQuestStatus(21014) == 2 || pi.getPlayer().getJob() != 2000) {
        pi.playPortalSE();
        pi.warp(140010100, 2);
    } else {
        pi.playerMessage(5, "瑞恩村莊在右边。從右边的傳送點进入村莊后，去找莉琳。");
    }
}