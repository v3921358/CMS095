/* Author: Xterminator (Modified by RMZero213)
	NPC Name: 		Roger
	Map(s): 		Maple Road : Lower level of the Training Camp (2)
	Description: 		Quest - Roger's Apple
*/
var status = -1;

function start(mode, type, selection) {
    if (mode == -1) {
	qm.dispose();
    } else {
	if (mode == 1)
	    status++;
	else
	    status--;
	
	if (status == 0) {
	    qm.sendNext("嗨, 怎么了吗? 我是羅傑，可以教你一些有用的知識");
	} else if (status == 1) {
		qm.sendNextPrev("你问我为什么在这吗? 哈哈哈!\r我想要教導那些剛进枫之谷的冒險者们。");
	} else if (status == 2) {
	qm.sendAcceptDecline("所以..... 让我们來玩點有趣的~!");
	} else if (status == 3) {
	    if (!qm.haveItem(2010007)) {
		qm.gainItem(2010007, 1);
	    }
	    qm.sendNext("请把它吃完然后\r\n我等等会給你#r神秘小礼物#k。 请務必收下啊。 使用后你会变得更强壮。 打开消耗栏，双擊一下苹果 很简单的，按一下鍵盤的 #bI#k就能了喔！");
	} else if (status == 4) {
	    qm.forceStartQuest();
	    qm.dispose();
	}
    }
}
        
function end(mode, type, selection) {
    if (mode == -1) {
	qm.dispose();
    } else {
	if (mode == 1)
	    status++;
	else
	    status--;
	if (status == 0) {
	    if (qm.getPlayerStat("HP") < 50) {
		qm.sendNext("哈喽，你还沒把我給你的苹果吃掉啊，赶快吃了再來找我吧。");
		qm.dispose();
	    } else {
		qm.sendNext("你看～是不是很简单？ 你可以在右側的栏位設定#b熱鍵#k。 哈哈，你听不懂对吧？ 喔，每隔一段时间，血量就会恢復了。 虽然很花时间，但好好运用的话可以帮助不少的。");
	    }
	} else if (status == 1) {
	    qm.gainExp(10);
	    qm.gainItem(2010000, 3);
	    qm.gainItem(2010009, 3);
	    qm.forceCompleteQuest();
	    qm.dispose();
	}
    }
}