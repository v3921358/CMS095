

var status = -1;
var sel;
var mod;
function start() {
    cm.sendSimple("你要兑换道具吗？ \r\n\r\n#b#L0# 10 个 #z2430112# 兑换 #z2049401##l \r\n\r\n#b#L1# 25 个 #z2430112# 兑换 #z2049400##l \r\n\r\n#b#L2# 500 个 #z2430112# 兑换 #z2049406##l \r\n\r\n#b#L3# 1500 个 #z2430112# 兑换 #z2049407##l#k");
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
            if (!cm.haveItem(2430112, 10)) {
                cm.sendOk("你没有#z2430112#x10。");
                cm.dispose();
                return;
            }
            if (!cm.canHold()) {
                cm.sendOk("您的背包已满。");
                cm.dispose();
                return;
            }
            cm.gainItem(2430112, -10);
            cm.gainItem(2049401, 1);
            cm.sendOk("兑换成功。");
            cm.dispose();
            return;
        } else if (sel == 1) {
            if (!cm.haveItem(2430112, 25)) {
                cm.sendOk("你没有#z2430112#x25。");
                cm.dispose();
                return;
            }
            if (!cm.canHold()) {
                cm.sendOk("您的背包已满。");
                cm.dispose();
                return;
            }
            cm.gainItem(2430112, -25);
            cm.gainItem(2049400, 1);
            cm.sendOk("兑换成功。");
            cm.dispose();
            return;
        }  else if (sel == 2) {
            if (!cm.haveItem(2430112, 500)) {
                cm.sendOk("你没有#z2430112#x500。");
                cm.dispose();
                return;
            }
            if (!cm.canHold()) {
                cm.sendOk("您的背包已满。");
                cm.dispose();
                return;
            }
            cm.gainItem(2430112, -500);
            cm.gainItem(2049406, 1);
            cm.sendOk("兑换成功。");
            cm.dispose();
            return;
        } else if (sel == 3) {
            if (!cm.haveItem(2430112, 1500)) {
                cm.sendOk("你没有#z2430112#x1500。");
                cm.dispose();
                return;
            }
            if (!cm.canHold()) {
                cm.sendOk("您的背包已满。");
                cm.dispose();
                return;
            }
            cm.gainItem(2430112, -1500);
            cm.gainItem(2049407, 1);
            cm.sendOk("兑换成功。");
            cm.dispose();
            return;
        }
    }
}
