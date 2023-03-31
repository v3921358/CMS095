function enter(pi) {
	if (pi.getPlayer().getParty() != null && pi.getMap().getAllMonstersThreadsafe().size() == 0) {
		//var chars = pi.getMap().getCharactersThreadsafe();
		//for (var i = 0; i < chars.size(); i++) {
			var item = ((pi.getPlayer().getJob() % 1000) / 100 + 2022651) | 0;
			if (item == 2022651) {
				item = 2022652;
			} else if (item == 2022654) {
				item = 2022655;
			} else if (item == 2022655) {
				item = 2022654;
			}
			pi.gainItem(item, 1);
		//}
		pi.addTrait("will", 40);
		pi.addTrait("charisma", 10);
		pi.gainExp(1200000);
		pi.setBossLog("御龙魔");
                //pi.gainExp_PQ(200, 1.5);
                pi.gainNX2(15)
		pi.gainNX(20);
		pi.warp(240080050);
		pi.playPortalSE();
	} else {
		pi.playerMessage(5,"This portal is not available.");
	}
}