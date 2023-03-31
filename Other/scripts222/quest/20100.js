var status = -1;

function start(mode, type, selection) {
    if (mode > 0)
        status++;
    else {
        qm.dispose();
        return;
    }
    if (status == 0)
        qm.sendAcceptDecline("唉唉，你回來了。我可以看到你在10級了。它看起來像你閃爍的一絲希望努力成为一个騎士。基本的訓练已经結束，现在是时候为你做的决定。");
    else if (status == 1) {
        qm.sendOk("左边有五个人他们就是騎士團的領導人在等着你。");
        qm.forceStartQuest();
        qm.forceCompleteQuest();
        qm.dispose();
    }
}