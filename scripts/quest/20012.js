/*
	NPC Name: 		Kinu
	Description: 		Quest - Cygnus tutorial helper
*/

var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
	if (status == 2) {
	    qm.sendNext("普通攻擊是基本技能，很容易使用。重要的是要記住，使用技巧做真正的狩猎是很重要的。我建议你重新考慮。");
	    qm.safeDispose();
	    return;
	}
	status--;
    }
    if (status == 0) {
	qm.sendNext("我一直在等待你, #h0#. 我的名字是 #p1102006# 为了要让你满足我的兄弟。 所以，你已经學会了如何使用普通攻擊了？\r\n 好了接下來你会了解 #b如何使用技能#k, 你会发现这对你很有帮助！");
    } else if (status == 1) {
	qm.sendNextPrev("当你每次升等你会获得技能點數，这意味这你可以有一些能力了！ 请案 #bK 鍵#k 看看你的技能. 好好善用你的技能點數在技能上。 #b将技能拉到快捷鍵上更方便使用。#k.");
    } else if (status == 2) {
	qm.askAcceptDecline("时间过得真快，忘了你是要练習了... 接下來你会发现很多的 #o100121# 在这张地图。你需要打倒 #r3隻 #o100121##k 使用你的 #b攻擊#b 技能 然后給我 1 #b#t4000483##k 作为證明OK？ 我会在这里等你的。");
    } else if (status == 3) {
	qm.forceStartQuest();
	qm.summonMsg(8);
	qm.dispose();
    }
}

function end(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
	status--;
    }
    if (status == 0) {
	qm.sendNext("你已经成功地打敗了 #o100121# 並給我带來了 一个 #t4000483#. 这是非常令人印象深刻! #b你善用了 3 个技能點數 当你每一次升級的时候, 你会获得更多技能點數，接下來请照着箭頭走去找我的兄弟 #b#p1102007##k, 他将告訴你下一步怎么做。\r\n\r\n#fUI/UIWindow.img/QuestIcon/4/0# \r\n#fUI/UIWindow.img/QuestIcon/8/0# 40 经验值");
    } else if (status == 1) {
	qm.gainItem(4000483, -1);
	qm.forceCompleteQuest();
	qm.gainExp(40);
	qm.dispose();
    }
}