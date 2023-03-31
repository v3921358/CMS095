var status = -1;

function start(mode, type, selection) {
    if (mode == 1)
        status++;
    else {
        status--;
    }

    if (status == 0)
        qm.sendYesNo("去農场工作的 #b爸爸#k忘了带便当出門。你帮待在 #b#m100030300##k的爸爸 #b送便当#k。好乖啊！");
    else if (status == 1) {
        qm.forceStartQuest();
        qm.sendNext("呼呼，#h0#果然是个乖孩子~ 那么立刻從#b家往外走一直向左边#k走。爸爸肚子應該很餓了，你的动作要快。");
        if (!qm.haveItem(4032448))
            qm.gainItem(4032448, 1);
    } else if (status == 3)
        qm.sendNextPrev("如果不小心把便当弄掉了，要立刻回家。我会再帮你包一个。");
    else if (status == 4) {
        qm.evanTutorial("UI/tutorial/evan/5/0", 1);
        qm.dispose();
    } else
        qm.dispose();
}