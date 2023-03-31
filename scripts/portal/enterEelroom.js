function enter(pi) {
    if (pi.isQuestActive(23957)) {
        pi.warp(931020011, 1);
        pi.spawnMobOnMap(9300417, 1, 1186, 18, 931020011);
        //pi.spawnMobOnMap(9300344, 1, 590, 230, 910050300);
    } else {
        pi.playerMessage('门上锁了。');
    }
}