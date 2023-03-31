function enter(pi) {
    if (pi.canHold(4001198, 1) && (pi.getMap().getAllMonstersThreadsafe().size() == 0 || pi.getMap().getMonsterById(9300183) != null) && (pi.getMap().getReactorByName("") == null || pi.getMap().getReactorByName("").getState() == 1)) {
        pi.warp(930000800, 0);
        /*pi.gainExp_PQ(120, 1.0);
        pi.gainExp_PQ(120, 1.0);*/
		pi.gainExp(400000);
		pi.setBossLog("毒物森林");
        pi.getPlayer().modifyCSPoints(2, 15, true);
        pi.getPlayer().endPartyQuest(1206);
        pi.addTrait("will", 30);
        //pi.gainNX(2000);
        pi.gainItem(4001198, 1);
    } else {
        pi.playerMessage(5, "请打完BOSS再出去");
    }
}