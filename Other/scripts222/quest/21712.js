var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        if (status == 2) {
            qm.sendNext("你还是不了解发生了甚么事情吗? 我很乐意再次说明给你听");
            qm.dispose();
            return;
        }
        status--;
    }
    if (status == 0) {
        qm.sendNext("#t4032315#... #r这个娃娃制造着奇怪的噪音#k. 你无法用你的耳朵听见 , 因为只有 #o1210102# 能听到奇怪的噪音. 我相信就是因为这个原因导致 #o1210102# 的个性变得凶暴.");
    } else if (status == 1) {
        qm.askAcceptDecline("#o1210102# 因为受到噪音的影响变成了愤世嫉俗的菇菇宝贝,且开始攻击了没受到噪音影响的 #o1210102#s, 导致了所有的 #o1210102# 都在备战状态. #造成所有的 #o1210102# 改变的原因就是因为这个娃娃#k! 了解了吗?");
    } else if (status == 2) {
        qm.forceStartQuest();
        qm.sendNextS("我发现这是第一次菇菇宝贝发生变异, 且没有原因可以说明这个娃娃是自然产生的, 也就是说一切都是有人在幕后指使的. 最近我应该多注意 #o1210102#.", 9);
    } else if (status == 3) {
        qm.sendPrevS("#b(你可以去找出是甚么导致了 #o1210102# 的变异, 接着回报给 #p1002104# 所有收集到的情报.)#k", 2);
        qm.dispose();
    }
}

function end(mode, type, selection) {
    qm.dispose();
}