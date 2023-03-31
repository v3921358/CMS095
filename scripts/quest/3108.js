var status = -1;

function start(mode, type, selection) {
	if (qm.isQuestActive(3108)) {
		qm.sendNext("在雪原深处发现了破碎的雪之精灵的雕像。斯卡德所说的线索是这个吗？看起来可能和雪人有些关系……　回去找斯卡德吧。");
		qm.forceCompleteQuest();
		qm.dispose();
		return;
	} else {
		qm.sendNext("发现了一个用冰块儿砌成的雕像。不过那个雕像大部分都已经破碎，无法看清原样了……应该是雪之精灵的雕像。");
		qm.forceStartQuest();
		qm.dispose();
		return;
	}
}

function end(mode, type, selection) {
	qm.dispose();
}