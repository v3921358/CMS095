var status = -1;

function start(mode, type, selection) {
	qm.sendNext("#b#m211000000##k的#b#p2020006##k好像知道#p1103000#的情况……");
	qm.forceStartQuest();
	// qm.forceCompleteQuest();
	qm.dispose();
}

function end(mode, type, selection) {
	qm.sendNext("#p2020006#说虽然不知道#p1103000#现在在哪里，但他应该在#m211000000#停留了一段时间，他在那里#r打猎僵尸#k。不是单纯的打猎，而是在搜寻某种线索……\r\n据#p2020006#说，#p1103000#一直在打猎僵尸。不是单纯的打猎，而是在搜寻某种线索…");
	qm.forceCompleteQuest();
	qm.dispose();
}