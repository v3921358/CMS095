var status = -1;

function start(mode, type, selection) {
    status++;
    if (mode != 1) {
        if (type == 1 && mode == 0)
            status -= 2;
        else {
            qm.dispose();
            return;
        }
    }
    if (status == 0)
        qm.sendNext("你拿飯給#p1013102#吃了吗？那么#h0#你也來吃早餐。今天的早餐是#t2022620#。我拿出來了。实際上如果你不帮忙拿東西給#p1013102#吃，那我也不打算让你吃早餐。");
    else if (status == 1)
        qm.sendYesNo(" 來，我会給你#b三明治#k吃，吃完之后去找媽媽，她有事情要交代你做。");
    else if (status == 2) {
        qm.forceStartQuest();
        qm.sendNextS("#b(想说的话？總之先吃了 #t2022620#再回家去。)#k", 2);
        qm.gainItem(2022620, 1);
    } else if (status == 3) {
        qm.evanTutorial("UI/tutorial/evan/3/0", 1);
        qm.dispose();
    }
}

function end(mode, type, selection) {
    status++;
    if (mode != 1) {
        if (type == 1 && mode == 0)
            status -= 2;
        else {
            qm.dispose();
            return;
        }
    }
    if (status == 0)
        qm.sendNext("吃过早餐了吗？#h0#？那么可以帮媽媽一个忙吗 ？\r\n\r\n#fUI/UIWindow.img/QuestIcon/4/0# \r\n#i1003028# #t1003028# 1个 \r\n#i2022621# #t2022621# 5个 \r\n#i2022622# #t2022622# 5个 \r\n\r\n#fUI/UIWindow.img/QuestIcon/8/0# 60 exp");
    if (status == 1) {
        qm.forceCompleteQuest();
        qm.evanTutorial("UI/tutorial/evan/4/0", 1);
        qm.gainItem(1003028, 1);
        qm.gainItem(2022621, 5);
        qm.gainItem(2022622, 5);
        qm.gainExp(60);
        qm.dispose();
    }
}