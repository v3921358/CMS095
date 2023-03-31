﻿/* 黛雅的G-药水 */

var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        qm.sendNext("你不想现在領取吗？需要的话，请在周一之內領走。");
        qm.dispose();
        return;
    }
    if (qm.getGuild().getLevel() < 1 || !qm.getGuild().hasSkill(91000006)) {
        qm.dispose();
        return;
    }
    if (status == 0) {
        qm.sendYesNo("家族定期支援物品到了。來，拿着。希望你不要有什麽不满。努力活动，等家族等級提高之后，就可以获得更多的東西了。");
    } else {
        if (!qm.canHold(2002037, qm.getGuild().getLevel() * 20)) {
            qm.sendOk("请确保您有足够的背包空间。");
        } else {
            qm.gainItemPeriod(2002037, qm.getGuild().getLevel() * 20, 7);
            qm.forceCompleteQuest();
            qm.sendNext("这一周辛苦你了。下周一的时候，还会有新的支援物品，到时你再过來看看。");
        }
        qm.dispose();
    }
}

function end(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        qm.sendNext("你不想现在領取吗？需要的话，请在周一之內領走。");
        qm.dispose();
        return;
    }
    if (qm.getGuild().getLevel() < 1 || !qm.getGuild().hasSkill(91000006)) {
        qm.dispose();
        return;
    }
    if (status == 0) {
        qm.sendYesNo("家族定期支援物品到了。來，拿着。希望你不要有什麽不满。努力活动，等家族等級提高之后，就可以获得更多的東西了。");
    } else {
        if (!qm.canHold(2002037, qm.getGuild().getLevel() * 20)) {
            qm.sendOk("请确保您有足够的背包空间。");
        } else {
            qm.gainItemPeriod(2002037, qm.getGuild().getLevel() * 20, 7);
            qm.forceCompleteQuest();
            qm.sendNext("这一周辛苦你了。下周一的时候，还会有新的支援物品，到时你再过來看看。");
        }
        qm.dispose();
    }
}