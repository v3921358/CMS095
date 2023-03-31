function action(mode, type, selection) {
	/*cm.removeAll(4001022);
	cm.removeAll(4001023);*/
	cm.addTrait("will", 35);
	cm.addTrait("charisma", 10);
	cm.getPlayer().endPartyQuest(1202);//might be a bad implentation.. incase they dc or something
        //cm.getPlayer().modifyCSPoints(2, 15, true); 
	//cm.gainNX(1500);
	cm.warp(221023300);
	cm.setBossLog("玩具塔101");
	cm.dispose();
}