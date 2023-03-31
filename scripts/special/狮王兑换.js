var status = -1;
var sel;
var mod;

function start() {
    cm.sendSimple("#e嗨，我是狮子王城兑换员有什麽可以帮忙的？？ \r\n\r\n#b#L0#狮子王城物品兑换#l \r\n\r\n#b#L1#狮子王城商店#l ");
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
            cm.sendSimple("所以你想做什麽？？ \r\n#b#L0##i2430158#x100兑换#i4310010#x1#l \r\n#b#L1##i4000630#x100和#i2430158#x1兑换#i4310009#x1#l \r\n#b#L2##i4000630#x300和#i4310009#x1兑换#i4310010#x1#l \r\n");
        } else if (sel == 1) {
            cm.openShop(200);
            cm.dispose();
        }
    } else if (status == 1) {
        if (sel == 0) {
            if (selection == 0) {
                if (!cm.haveItem(2430158, 100)) {
                    cm.sendOk("你没有#z2430158#x100。");
                    cm.dispose();
                    return;
                }

                if (!cm.canHold()) {
                    cm.sendOk("您的背包已满。");
                    cm.dispose();
                    return;
                } else {
                    cm.gainItem(2430158, -100);
                    cm.gainItem(4310010, 1);
                    cm.sendOk("兑换成功。");
                    cm.dispose();
                    return;
                }
            } else if (selection == 1) {
                if (!cm.haveItem(4000630, 100)) {
                    cm.sendOk("你没有#z4000630#x100。");
                    cm.dispose();
                    return;
                }
                if (!cm.haveItem(2430158, 1)) {
                    cm.sendOk("你没有#z2430158#x1。");
                    cm.dispose();
                    return;
                }

                if (!cm.canHold()) {
                    cm.sendOk("您的背包已满。");
                    cm.dispose();
                    return;
                } else {
                    cm.gainItem(4000630, -100);
                    cm.gainItem(2430158, -1);
                    cm.gainItem(4310009, 1);
                    cm.sendOk("兑换成功。");
                    cm.dispose();
                    return;
                }
            } else if (selection == 2) {
                if (!cm.haveItem(4000630, 300)) {
                    cm.sendOk("你没有#z4000630#x300。");
                    cm.dispose();
                    return;
                }
                if (!cm.haveItem(4310009, 1)) {
                    cm.sendOk("你没有#z4310009#x1。");
                    cm.dispose();
                    return;
                }

                if (!cm.canHold()) {
                    cm.sendOk("您的背包已满。");
                    cm.dispose();
                    return;
                } else {
                    cm.gainItem(4000630, -300);
                    cm.gainItem(4310009, -1);
                    cm.gainItem(4310010, 1);
                    cm.sendOk("兑换成功。");
                    cm.dispose();
                    return;
                }
            }


        }
    }
}