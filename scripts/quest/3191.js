var status = -1;

function start(mode, type, selection) {
	
	if(qm.isQuestActive(3191)){
		qm.forceCompleteQuest();
		qm.dispose();
	}else{
		qm.sendNext("要想阻止黑山老妖的禁忌魔法，必须要有结界图腾。制作结界图腾，需要#b智慧水晶#k，以及黑山老妖的#b红色契约珠和堕落者圣书#k，去搜集这些东西。\r\n"+
	"#i4000633:# #t4000633:# #c4000633# / 1 \r\n"+
	"#i4000336:# #t4000336:# #c4000336# / 1 \r\n"+
	"#i4005001:# #t4005001:# #c4005001# / 1 \r\n");
		// if (!qm.haveItem(2430159)) {
		// 	qm.gainItem(2430159, 1)
		// }
		qm.forceStartQuest();
		qm.dispose();
	}
}
function end(mode, type, selection) {
	qm.dispose();
}
