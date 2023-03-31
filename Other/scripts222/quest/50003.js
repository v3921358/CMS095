/*
	任務: 不管多少次都得爭取！
	描述: 我從重力網駕駛员……位于2102年商業區的#p9120033#处，获得了討伐#o9400295#的任務，他说重力網使用的驅动組件目前出现在#o9400295#的周圍，有了它就可以强化#o9400295#沒时间了，如果不盡快地打倒#o9400295#的话…！
*/
var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        qm.sendNext("．..它是什么?啊，我看到他离我们很近了!");
        qm.dispose();
        return;
    }
    if (status == 0) {
        qm.askAcceptDecline("小心，因为他看起来…比以前强大多了。不要小看他!");
    } else if (status == 1) {
        qm.forceStartQuest();
        qm.dispose();
    }
}

function end(mode, type, selection) {
    qm.forceCompleteQuest();
    qm.dispose();
}