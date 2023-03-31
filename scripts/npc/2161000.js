var status = -1;

function start() {
    cm.askAcceptDecline("你是打敗我的勇士吗？或者你是反黑魔法师联盟？你是谁並不重要…如果我们确信对方的目的，就不需要閑聊了……把它带來吧，你们这些傻瓜！");
}

function action(mode, type, selection) {
    if (mode == 1 && cm.getMap().getAllMonstersThreadsafe().size() == 0) {
	cm.removeNpc(cm.getMapId(), 2161000);
	cm.spawnMob(8840010, 0, -181);
	if (!cm.getPlayer().isGM()) {
		cm.getMap().startSpeedRun();
	}
    }
    cm.dispose();
}