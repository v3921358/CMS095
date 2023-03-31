var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
	if (status == 0) {
	    qm.sendNext("您还沒准备好猎杀 #o0100132#吗？ 最好把該准备的都准备好再去狩猎比較好。如果不好好准备，在途中一命嗚呼了，那只会让人遺淺罷了！");
	    qm.dispose();
	    return;
	}
	status--;
    }
    if (status == 0) {
	qm.askAcceptDecline("那么要繼續基礎體力鍛鍊吗？准备好了吗？请您在确认剑是否裝备好了，技能和药是否已经放入快捷栏內，然后就开始吧！");
    } else if (status == 1) {
	qm.forceStartQuest();
	qm.AranTutInstructionalBubble("Effect/OnUserEff.img/guideEffect/aranTutorial/tutorialArrow3");
	qm.dispose();
    }
}

function end(mode, type, selection) {
    qm.dispose();
}
