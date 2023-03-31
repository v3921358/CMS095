var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
	if (status == 1) {
	    qm.sendNext("什么，該不会是嫌5隻太少了吧？如果是为了修炼多擊退一些的话也是沒关係。为了英雄，我虽然心痛，但是也会睜一隻眼閉一隻眼");
	    qm.dispose();
	    return;
	}
	status--;
    }
    if (status == 0) {
	qm.sendNext("好！那么现在要进行基礎體力的测试。方法很简单。只要擊敗島上最强最兇惡的怪物， #o0100134# 就可以了！如果可以擊敗 #r50隻#k 那就太好了，可是...");
    } else if (status == 1) {
	qm.askAcceptDecline("如果将僅剩沒几隻的 #o0100134#全部擊退，对生態界似乎会造成不好的影響，那么请擊敗5隻。考慮到自然和環境的鍛炼！啊！真太美了...");
    } else if (status == 2) {
	qm.forceStartQuest();
	qm.AranTutInstructionalBubble("Effect/OnUserEff.img/guideEffect/aranTutorial/tutorialArrow1");
	qm.dispose();
    }
}

function end(mode, type, selection) {
    qm.dispose();
}
