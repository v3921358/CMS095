var status = -1;

function end(mode, type, selection) {
    if (mode == 0) {
        if (status == 0) {
            qm.sendNext("这是一个重要的決定。");
            qm.safeDispose();
            return;
        }
        status--;
    } else {
        status++;
    }
    if (status == 0) {
        qm.sendYesNo("你做决定了吗？決定将是最后的，所以在決定做什么之前仔細考虑一下。你确定你想成为一名战斗法师吗？");
    } else if (status == 1) {
        qm.sendNext("很好！欢迎你正式成为反抗者。从现在开始你就是唤灵斗师了。作为一名战斗的魔法师，希望你能勇敢地冲在最前面和敌人战斗。");
        if (qm.getJob() == 3000) {
            qm.gainItem(1382100, 1);
            qm.expandInventory(1, 4);
            qm.expandInventory(2, 4);
            qm.expandInventory(4, 4);
            qm.changeJob(3200);
        }
        qm.forceCompleteQuest();
    } else if (status == 2) {
        qm.sendNextPrev("我还擴大了您的存貨槽計數为您的設备等庫存。明智地使用这些插槽，並用電阻攜带的物品填满它们。");
    } else if (status == 3) {
        qm.sendNextPrev("你不能在外面自称是唤灵斗师。如果被黑色之翼抓住了的话，就麻烦了。从现在起，你要把我叫做班主任。你是因为特别课程才到教室这里来的。呵呵……特别课程由我负责，我会好好带你的。");
        qm.safeDispose();
    }
}