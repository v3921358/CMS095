var status = -1;

function start(mode, type, selection) {
    if (mode == 1)
        status++;
    else {
        if (status == 7) {
            qm.dispose();
            return;
        }
        status--;
    }

    if (status == 0)
        qm.sendNext("睡得好吗，#h0#？");
    else if (status == 1)
        qm.sendNextPrevS("#b嗯…媽媽也睡得好吗？", 2);
    else if (status == 2)
        qm.sendNextPrev("对了... 你昨天晚上似乎沒有睡得很好。是因为昨晚雷声轟隆隆閃電交加的緣故。是这樣吗？");
    else if (status == 3)
        qm.sendNextPrevS("#b不是！不是啦！我昨晚做了一个奇怪的梦。", 2);
    else if (status == 4)
        qm.sendNextPrev("奇怪的梦？你做了什么梦？");
    else if (status == 5)
        qm.sendNextPrevS("#b就是啊…", 2);
    else if (status == 6)
        qm.sendNextPrevS("#b(说明了在霧中遇見龙的梦。)", 2);
    else if (status == 7)
        qm.sendYesNo("呵呵呵呵，龙吗？真的好厲害。还好沒被抓去吃掉。 有趣的梦也可以和#p1013101#分享。應該会很棒。");
    else if (status == 8) {
        qm.forceStartQuest();
        qm.sendNext("#b#p1013101##k拿早餐去給猎犬吃，前往 #b#m100030102##k了。你從家里往外走就能看到了。");
    } else if (status == 9) {
        qm.evanTutorial("UI/tutorial/evan/1/0", 1);
        qm.dispose();
    } else {
        qm.dispose();
    }
            
}

function end(mode, type, selection) {
    if (mode == 1)
        status++;
    else
        status--;

    if (status == 0)
        qm.sendNext("喔！你起床了？#h0#！眼睛怎么有黑眼圈哪？你都沒睡吗？什么？你说你做了奇怪的梦？什么梦呢？做了龙出现的梦吗？" + status);
    else if (status == 1)
        qm.sendNextPrev("哇哈哈哈~ 龙吗？那很厲害？龙梦耶！可是梦里面沒有出现一隻狗吗？ 哈哈哈哈~\r\n\r\n#fUI/UIWindow.img/QuestIcon/8/0# 20 exp");
    else if (status == 2) {
        qm.gainExp(20);
        qm.evanTutorial("UI/tutorial/evan/2/0", 1);
        qm.forceCompleteQuest();
        qm.dispose();
    } else {
        qm.dispose();
    }
}