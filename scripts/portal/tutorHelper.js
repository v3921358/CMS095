function enter(pi) {
    if (pi.getQuestStatus(20021) == 0) {
	pi.playerSummonHint(true);
	pi.summonMsg("欢迎來到枫之谷的世界! 我的名字是 提酷, 我会是你的指導老师！ 我会在这里回答你的问題，並指導你直到等級10等，成为騎士團之前如果你有任何疑问，可以點擊我！");
//	pi.forceCompleteQuest(20100);
	pi.forceCompleteQuest(20021);
    }
}