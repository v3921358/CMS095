/*
 Description: 	Quest - A Bite of Hay
 */

var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        if (status == 0) {
            qm.sendNext("嗯，除非你嘗试，否則你永远不会知道。那只蜥蜴足够大了，不管你信不信。它可能吃幹草。");
            qm.dispose();
            return;
        }
        status--;
    }
    if (status == 0) {
        qm.askAcceptDecline("蜥蜴不会像牛一样享受一大堆干草吗？附近有很多野草，所以试着喂它。 ");
    } else if (status == 1) {
        qm.forceStartQuest();
        qm.evanTutorial("UI/tutorial/evan/12/0", 1);
        qm.dispose();
    }
}

function end(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        status--;
    }
    if (status == 0) {
        qm.sendNext("哦，我好餓啊！你找到好吃的東西給我吃了吗，主人？隱馬爾可夫模型。。。这看起來像……草。我真的能吃这个吗？好的，主人，我相信你。 ");
    } else if (status == 1) {
        qm.sendOk("好了，走吧！");
    } else if (status == 2) {
        qm.gainExp(800);
        qm.gainItem(4032452, -3);
        qm.sendOk("討厭！这是什么？真苦！你确定这是可食用的吗？师傅，你吃吧！我不能吃这个！給我找別的東西！");
        qm.forceCompleteQuest();
        qm.dispose();
    }
}