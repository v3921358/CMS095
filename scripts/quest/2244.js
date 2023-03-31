var status = -1;

function start(mode, type, selection) {
	// qm.sendNext("非常感謝.");
	qm.forceCompleteQuest();
	qm.dispose();
}
function end(mode, type, selection) {
	if(qm.isQuestActive(2244)&&qm.getPQLog("蝙蝠魔次数",false)>=200){
		qm.gainItem(1142079,1);
		qm.gainExp(6800);
		qm.forceCompleteQuest(2244);
	}else{
		qm.sendOk("击杀次数还不够200！加油！！");
		qm.forceStartQuest();
	}
	qm.dispose();
}
