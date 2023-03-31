/*
	NPC Name: 		Kisan
	Description: 		Quest - Cygnus tutorial helper
*/

var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
	if (status == 2) {
	    qm.sendNext("You don't want to? It's not even that hard, and you'll receive special equipment as a reward! Well, give it some thought and let me know if you change your mind.");
	    qm.safeDispose();
	    return;
	}
	status--;
    }
    if (status == 0) {
	qm.sendNext("有許多方法打猎，但最基本的方法是用你的 #b普通攻擊#k. 所有你需要的是在你的手的武器，因为它只是擺动你的武器在怪物一件简单的事情。");
    } else if (status == 1) {
	qm.sendNextPrev("请按 #bCtrl#k 使用你的普通攻擊. 通常下 Ctrl 位于 #b鍵盤的左下角#k, 但你並不需要我告訴你对不对？ 发现Ctrl 並嘗试攻擊！");
    } else if (status == 2) {
	qm.askAcceptDecline("现在，你已经嘗试过了，我们一定要测试它。在这方面，你可以找到最薄弱 #r#o100120##k 在耶雷弗, 这是您的最佳選擇。嘗试狩猎 #r1隻#k. 当你回來我給你的奖励。.");
    } else if (status == 3) {
	qm.forceStartQuest();
	qm.summonMsg(4);
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
	qm.sendNext("很棒唷看你學得很快，将來一定是强大的王者！");
    } else if (status == 1) {
	qm.sendNextPrev("这身裝备是貴族專屬的。 它将送給你穿，穿上它吧！ 然后按照箭頭的方向去找我的兄弟 #b#p1102006##k. 他会告訴你下一步該怎么做。 \r\n\r\n#fUI/UIWindow.img/QuestIcon/4/0# \r\n#i1002869# #t1002869# - 1 \r\n#i1052177# #t1052177# - 1 \r\n\r\n#fUI/UIWindow.img/QuestIcon/8/0# 30 经验值");
    } else if (status == 2) {
	qm.gainItem(1002869, 1);
	qm.gainItem(1052177, 1);
	qm.forceCompleteQuest();
	qm.gainExp(30);
	qm.summonMsg(6);
	qm.dispose();
    }
}