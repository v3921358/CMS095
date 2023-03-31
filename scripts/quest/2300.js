/* 
 任務: 危機中的蘑菇王國
 */

var status = -1;

function start(mode, type, selection) {
    status++;
    if (mode != 1) {
        if (type == 1 && mode == 0) {
            status -= 2;
        } else {
            qm.sendOk("真的吗??給你一段时间考慮，考慮好再來找我。");
            qm.dispose();
            return;
        }
    }
    if (status == 0) {
        if (qm.getMapId() == 180000001) {
            qm.sendOk("很遺憾，您因为違反用戶守則被禁止游戏活动，如有异议请联系管理员.");
            qm.dispose();
        } else {
            qm.sendAcceptDecline("现在你的强大了許多，我有一件事情想找你帮忙，你是否願意听听？");
        }
    } else if (status == 1) {
        qm.sendNext("故事发生在蘑菇王國，具體的事情我也不太清楚。但是好像很緊急。");
    } else if (status == 2) {
        qm.sendNext("我不知道事情的細節，所以想找你帮帮忙，你可能会節省更多的时间帮助蘑菇王國，我送你一封信，请你把它交給門衛。 \r\n\r\n#fUI/UIWindow.img/QuestIcon/4/0#\r\n#v4032375# #t4032375#");
    } else if (status == 3) {
        if (qm.haveItem(4032375) && qm.canHold()) {
            qm.forceStartQuest();
            qm.sendYesNo("如果你现在想去蘑菇城堡的话，我可以送你去。你确定要去吗？");
        } else {
            qm.gainItem(4032375, 1);
            qm.forceStartQuest();
            qm.sendYesNo("如果你现在想去蘑菇城堡的话，我可以送你去。你确定要去吗？");
        }

    } else if (status == 4) {
        qm.warp(106020000);
        qm.dispose();
    }
}
function end(mode, type, selection) {
    status++;
    if (mode != 1) {
        if (type == 1 && mode == 0) {
            status -= 2;
        } else {
            qm.dispose();
            return;
        }
    }
    if (status == 0) {
        qm.sendNext("嗯？这个從转职教官那里得來的信件吗？");
    } else if (status == 1) {
        qm.sendNextPrev("我看看……。");
    } else if (status == 2) {
        qm.sendNextPrev("好吧，既然你有转职教官的推薦信，我想你是一个很棒的人，很抱歉我沒有自我介紹，我是包圍蘑菇城堡的衛兵，正如你所看到的，这里是我们暂时的藏身之地，我们的情況很糟糕，儘管如此，欢迎你來到蘑菇王國！");
    } else if (status == 3) {
        qm.forceCompleteQuest();
        qm.gainItem(4032375, -1);
        qm.forceStartQuest(2312);
        qm.dispose();
    }
}