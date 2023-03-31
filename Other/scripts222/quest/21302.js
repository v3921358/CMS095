var status = -1;

function start(mode, type, selection) {
}

function end(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        if (status == 1) {
            qm.sendNext("等你想好再來找我。");
            qm.dispose();
            return;
        }
        status--;
    }
    if (status == 0) {
        qm.sendNext("啊！ 这，这个...想起制作 紅珠玉 的方法了吗？啊啊...就算你腦袋尚未解凍又兼建忘症的人，我也无法棄你于不顧...啊！我怎么又來了！快點将寶石交出來！");
    } else if (status == 1) {
        qm.askAcceptDecline("很好， 紅珠玉的力量也恢復了，在将你的力量喚醒一些。你的等級已经比之前上升許多，應該能喚醒更多力量！");
    } else if (status == 2) {
        if (qm.getPlayerStat("RSP") > (qm.getPlayerStat("LVL") - 70) * 3) {
            qm.sendOk("技能點數沒點完。");
            qm.dispose();
            return;
        } else if (!qm.canHold()) {
            qm.sendOk("您的背包栏位不足。");
            qm.dispose();
            return;
        } else {
            qm.changeJob(2111);
            qm.gainItem(1142131, 1);
            qm.worldMessage("『转职快報』：恭喜玩家." + qm.getChar().getName() + "  成功狂狼勇士三轉让我们熱烈的祝福他/她吧！");
            qm.sendNext("快點将之前的能力找回來。像以前一樣一起去冒險...");
            qm.forceCompleteQuest();
            qm.dispose();
        }
    }
}