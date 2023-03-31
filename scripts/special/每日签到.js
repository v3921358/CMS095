

var status = -1;
var sel;
var mod;
function start() {
    cm.sendSimple("#e#r你好，我是每日签到管理员。#k \r\n\r\n#b#L0#每日签到(#i5030001#×1 #i4032398#×1 #i5390001#×2)#l \r\n\r\n#b#L2#签到兑换#l \r\n\r\n#b#L3#抽奖积分兑换#l");
}

function action(mode, type, selection) {
    if (mode == 0) {
        cm.dispose();
        return;
    } else {
        status++;
    }
    var level = cm.getPlayer().getLevel();
    if (level < 10) {
        cm.sendOk("少于10等无法使用此功能。");
        cm.dispose();
        return;
    }
    sel = selection;
    if (sel == 0) {

        var timea = 60 * 60 * 1000;
        var timeb = cm.getCurrentTime() - cm.getPlayer().getMrqdTime();
        if (cm.getTodayOnlineTime() < 10) {
            cm.sendOk("要在线10分钟才能领取奖励哦！\r\n 当前还差" + (10 - cm.getTodayOnlineTime()) + "分钟。");
            cm.dispose();
            return;
        }
        if (cm.getPlayer().getAcLogD("每日签到") >= 1) {
            cm.sendOk("你今天已经签到过了");
            cm.dispose();
            return;
        }
        if (!cm.canHoldByTypea(4, 1)) {
            cm.sendOk("请确认背包是否已经满了。");
            cm.dispose();
            return;
        }
        if (!cm.canHoldByTypea(5, 2)) {
            cm.sendOk("请确认背包是否已经满了。");
            cm.dispose();
            return;
        }
        cm.getPlayer().setAcLog("每日签到");
        cm.gainItem(4032398, 1);
        cm.gainItemPeriodF(5030001, 1, 1440);
        cm.gainItem(5390001, 2);
        //cm.getPlayer().modifyCSPoints(2, 300, true);
        cm.worldMessage(6, cm.getPlayer().getName() + "完成每日签到，累积签到" + cm.getPlayer().getAcLogS("每日签到") + "天。");
        cm.sendOk("签到成功。");
        cm.dispose();
        return;

        } else if (sel == 1) {
            openNpc(9010000, "每日任务");
        } else if (sel == 2) {
            openNpc(9010000,"签到兑换");
		} else if (sel == 3) {
            openNpc(9010000, "抽奖积分兑换");
    } else {
        cm.sendOk("此功能未完成");
        cm.dispose();
        return;
    }

}

function openNpc(npcid) {
    openNpc(npcid, null);
}

function openNpc(npcid, script) {
    var mapid = cm.getMapId();
    cm.dispose();
    if (cm.getPlayerStat("LVL") < 10) {
        cm.sendOk("你的等级不能小于10等.");
    } else if (
            cm.hasSquadByMap() ||
            cm.hasEventInstance() ||
            cm.hasEMByMap() ||
            mapid >= 990000000 ||
            (mapid >= 680000210 && mapid <= 680000502) ||
            (mapid / 1000 === 980000 && mapid !== 980000000) ||
            mapid / 100 === 1030008 ||
            mapid / 100 === 922010 ||
            mapid / 10 === 13003000
            ) {
        cm.sendOk("你不能在这裡使用这个功能.");
    } else {
        if (script == null) {
            cm.openNpc(npcid);
        } else {
            cm.openNpc(npcid, script);
        }
    }
}
