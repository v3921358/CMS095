var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
	if (status == 2) {
	    qm.sendNext("还说您是英雄，怎么会这么猶豫不决？您沒听过打鐵要趁熱吗？想变强的话，那就快點开始吧！");
	    qm.dispose();
	    return;
	}
	status--;
    }
    if (status == 0) {
	qm.sendNext("那么，说明就到此結束，现在进入下一階段。下一个階段是什么呢？剛剛才跟您说过不是吗？要修炼变强盜你可以除掉黑魔法师的程度。");
    } else if (status == 1) {
	qm.sendNextPrev("您虽然过去是英雄，可是那已经是几百年前的事了。就算不是黑魔法师的詛咒，待在冰雪当中这么长的时间，身體一定会变得很僵硬吧！首先先鬆开僵硬的身體。您觉得如何？");
    } else if (status == 2) {
	qm.askAcceptDecline("體力就是战力！英雄的基礎就是體力！ ... 您沒听过这些话吗？当然要先做#b基礎體力鍛鍊#k ... 啊！ 您喪失記憶所以什么都忘了。不知道也沒关係。那么现在就进入基礎體力鍛鍊吧！");
    } else if (status == 3) {
	qm.forceStartQuest();
	qm.AranTutInstructionalBubble("Effect/OnUserEff.img/guideEffect/aranTutorial/tutorialArrow3");
	qm.dispose();
    }
}

function end(mode, type, selection) {
    qm.dispose();
}
