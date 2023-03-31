var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
	if (status == 0) {
	    qm.sendNext("不行啊！ 狂郎勇士... 拋棄那些孩子， 只剩下我们苟且偷生... 那人生还有什么意義！我知道对您來说只是很大的負擔...可是请您再考慮看看吧！");
	    qm.dispose();
	    return;
	}
	status--
    }
    if (status == 0) {
	qm.askAcceptDecline("真是的！ 好像还有几个孩子留在森林內！ 我们不可能丟下孩子们逃走。 狂狼勇士... 请你去救救那些孩子吧！ 我知道对受的您说这些话真的很厚顏无恥， 可是... 只能拜託您了！");
    } else if (status == 1) {
	qm.forceStartQuest(21000, "..w?PG??."); // Idk what data lol..
	qm.forceStartQuest(21000, "..w?PG??."); // Twice, intended..
	qm.sendNext("#b孩子们應該在森林深处#k。 在黑魔法师追來这里之前，方舟要赶緊出发，请盡快救回那些孩子吧！");
    } else if (status == 1) {
	qm.sendNextPrev("最重要的是不要驚慌。 狂狼勇士。諾您想要确认任務进行情況，请按下 #bQ按鍵#k 确认任務栏位。");
    } else if (status == 2) {
	qm.sendNextPrev("拜託您了！狂狼勇士！请救救那些孩子！我不希望有人再犧牲于黑魔法师的魔掌之下。");
    } else if (status == 3) {
	qm.AranTutInstructionalBubble("Effect/OnUserEff.img/guideEffect/aranTutorial/tutorialArrow1");
	qm.dispose();
    }
}

function end(mode, type, selection) {
    qm.dispose();
}