var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
	if (status == 5) {
	    qm.sendNextS("#b(用害怕的心情按下拒絕。可是又不能这樣逃走..鎮定心情后再談看看。)#k", 2);
	    qm.dispose();
	    return;
	}
	status--;
    }
    if (status == 0) {
	qm.sendNextS("现在筋骨應該都鬆开了吧！此时更嚴格的鍛鍊，才能擁有完美的基礎體力。好！那么我们繼續进行基礎體力鍛炼！", 8);
    } else if (status == 1) {
	qm.sendNextPrevS("现在前往 #b#m140020200##k 擊退 #r#o0100133#s#k 只要擊退....#r20隻#k 左右，就会鍛炼體力有很大的帮助。好，快去吧...咦？你有话想说吗？", 8);
    } else if (status == 2) {
	qm.sendNextPrevS(".....为什么數字愈來愈多.....", 2);
    } else if (status == 3) {
	qm.sendNextPrevS("当然会增加。哎呀，您觉得 20隻太少吗？ 那么去擊退100隻怎么樣啊？不，不。既然要修炼，那么就效法奇幻村的某人要求去抓 999隻怪物...", 8);
    } else if (status == 4) {
	qm.sendNextPrevS("不，不用啦！这樣就够了。", 2);
    } else if (status == 5) {
	qm.askAcceptDecline("哎呀哎呀，沒有必要推辭。我完！全！的！了解英雄想快點变强的心情。英雄真的太偉大了...");
    } else if (status == 6) {
	qm.forceStartQuest();
	qm.sendNextS('#b(再听一次好像真的要我去擊退 999隻，乾脆答應算了。)#k', 2);
    } else if (status == 7) {
	qm.sendNextPrevS('那么请擊退 #o0100133# 20隻吧！', 8);
    } else if (status == 8) {
	qm.AranTutInstructionalBubble("Effect/OnUserEff.img/guideEffect/aranTutorial/tutorialArrow3");
	qm.dispose();
    }
}

function end(mode, type, selection) {
    qm.dispose();
}
