/* ==================
 脚本类型: NPC    
 脚本版权：一线海团队-维多
 =====================
 */
var zuididj = 30;//最低等级
var zuiduocs = 5;//每天可以挑战的次数
function start() {
	//(cm.getQuestStatus(4103) == 1 && cm.haveItem(4031289, 1)) || 
	if ((cm.getQuestStatus(4103) == 1 && cm.haveItem(4031289, 1)) || cm.getQuestStatus(4103) == 2) {
		cm.sendYesNo("挑战蜈蚣大王需要条件：\r\n最低等级 ：#r" + zuididj + "#k级\t\t每天限制进入次数：#r" + zuiduocs + "#k次\r\n\r\n你今天已经进入：#r" + cm.getPlayer().getBossLogD("wugong") + "#k次 !");
	} else {
		cm.sendOk("要么你已经完成了#r警察#k所交付你的任务，或还没完成#b农民伯伯#k的拜托。所以我不能带你进去！");
		cm.dispose();
	}
}

function action(mode, type, selection) {
	if (mode == 0) {
		cm.sendOk("恩... 看起来你并没有准备好。");
		cm.dispose();
	} else if (cm.getPlayer().getLevel() < zuididj) {
		cm.sendOk("你的等级不足 ：#r" + zuididj + "#k级!");
		cm.dispose();
	} else if (cm.getPlayer().getBossLog("wugong") >= zuiduocs) {
		cm.sendOk("你今天已经进入：#r" + cm.getPlayer().getBossLog("wugong") + "#k次,明天在来吧 !");
		cm.dispose();
	} else {
		cm.warp(701010321, 0);
		cm.getPlayer().setBossLog('wugong');
		cm.dispose();
	}
}
