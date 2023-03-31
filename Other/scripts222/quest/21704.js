var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        if (status == 2) {
            qm.dispose();
            return;
        }
        status--;
    }
    if (status == 0) {
        qm.sendNextS("修炼进行的不错吧？企鵝老师#p1202006#个性很强，我还擔心他要是癡呆了就不好辦了……他对英雄的技能确实很有研究，对你應該能帮上不少忙。", 8);
    } else if (status == 1) {
        qm.sendNextPrevS("#b(告訴他自己回想起來連擊能力这个技能。)#k", 2);
    } else if (status == 2) {
        qm.askAcceptDecline("是吗！看來除了#p1202006#的訓练方式之外，你自己仍然記的從前的那些技能也很关鍵啊……看來只是在冰窖里封凍的太久，需要时间恢復而已。#b繼續加油修炼吧，爭取早日恢復所有的技能！#k  \r\n\r\n#fUI/UIWindow.img/QuestIcon/8/0# 500 exp");
    } else if (status == 3) {
        qm.forceCompleteQuest();
        qm.gainExp(500);
        qm.dispose();
    }
}

function end(mode, type, selection) {
    qm.dispose();
}