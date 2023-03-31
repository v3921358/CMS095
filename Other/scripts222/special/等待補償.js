load('nashorn:mozilla_compat.js');
importPackage(Packages.server);

var status = -1;
var sel;
var mod;
function start() {
    //cm.sendOk("暂时沒有开放。");
    //cm.dispose();
    //return;
    cm.sendSimple("我是奖励領取员 \r\n\r\n" +
            "#b#L0#5/4异常補償#l#k\r\n\r\n" +
            //"#b#L1#4/25异常補償2#l#k\r\n\r\n" +
            //"#b#L3#更換機房補償#l#k\r\n\r\n" +
            "");
}

function action(mode, type, selection) {
    if (mode == 0) {
        cm.dispose();
        return;
    } else {
        status++;
    }
    if (status == 0) {
        sel = selection;
        if (sel == 0) {
            if (cm.getPlayer().getLevel() < 10) {
                cm.sendOk("您的等級不足10等，无法領取。");
                cm.dispose();
                return;
            }
            //if (!cm.getPlayer().getAccNewTime("2018-12-31 0:00:00")) {
            //    cm.sendOk("該帳号不是12月31日00.00前註冊的帳号，无法領取。");
            //    cm.dispose();
            //    return;
            //}
            if (cm.getPlayer().getAcLogS("5.4補償") >= 1) {
                cm.sendOk("該帳号已经領取过5.4補償。");
                cm.dispose();
                return;
            }
            if (!cm.canHoldByType(2, 2)) {
                cm.sendOk("请确认背包是否已经满了。");
                cm.dispose();
                return;
            }

            cm.getPlayer().setAcLogS("5.4補償");
            cm.gainItem(2022531, 1);
            cm.gainItem(2450009, 1);

            cm.sendOk("領取成功。");
            cm.dispose();
            return;
        }
        if (sel == 1) {
            if (cm.getPlayer().getLevel() < 10) {
                cm.sendOk("您的等級不足10等，无法領取。");
                cm.dispose();
                return;
            }
            //if (!cm.getPlayer().getAccNewTime("2018-12-31 0:00:00")) {
            //    cm.sendOk("該帳号不是12月31日00.00前註冊的帳号，无法領取。");
            //    cm.dispose();
            //    return;
            //}
            if (cm.getPlayer().getAcLogS("4.25補償2") >= 1) {
                cm.sendOk("該帳号已经領取过4.25補償2。");
                cm.dispose();
                return;
            }
            if (!cm.canHoldByType(2, 2)) {
                cm.sendOk("请确认背包是否已经满了。");
                cm.dispose();
                return;
            }

            cm.getPlayer().setAcLogS("4.25補償2");
            cm.gainItem(2450010, 1);

            cm.sendOk("領取成功。");
            cm.dispose();
            return;
        }
        
    }
}
