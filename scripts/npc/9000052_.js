

var status = -1;
var sel;
var mod;
function start() {
    cm.sendSimple("嗨，我是神话耳環合成员有什么可以帮忙的？？ \r\n\r\n#b#L0#神话耳環合成。#l \r\n\r\n#L1#獨眼巨人眼。#l");
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
            cm.sendSimple("所以你想做什么？？ \r\n#b#L0##z1032206##l \r\n#b#L1##z1032207##l \r\n#b#L2##z1032208##l \r\n#b#L3##z1032209##l \r\n#b#L4##z1032219##l \r\n");
        } else if (sel == 1) {
            cm.sendOk("暂未开放。");
            cm.dispose();
            return;
        }
    } else if (status == 1) {
        if (sel == 0) {
            if (selection == 0) {
                mod = 0;
                cm.sendYesNo("您确定要做一个 #b#t1032206##k? \r\n以下是你所需要的材料。\r\n\r\n#i1032205# x 1\r\n#i4032654# x3\r\n#i4032655# x3\r\n\r\n");
            } else if (selection == 1) {
                mod = 1;
                cm.sendYesNo("您确定要做一个 #b#t1032207##k? \r\n以下是你所需要的材料。\r\n\r\n#i1032206# x 1\r\n#i4032654# x3\r\n#i4032655# x3\r\n\r\n");
            } else if (selection == 2) {
                mod = 2;
                cm.sendYesNo("您确定要做一个 #b#t1032208##k? \r\n以下是你所需要的材料。\r\n\r\n#i1032207# x 1\r\n#i4032654# x3\r\n#i4032655# x3\r\n\r\n");
            } else if (selection == 3) {
                mod = 3;
                cm.sendYesNo("您确定要做一个 #b#t1032209##k? \r\n以下是你所需要的材料。\r\n\r\n#i1032208# x 1\r\n#i4032654# x6\r\n#i4032655# 6\r\n\r\n");
            } else if (selection == 4) {
                mod = 4;
                cm.sendYesNo("您确定要做一个 #b#t1032219##k? \r\n以下是你所需要的材料。\r\n\r\n#i1032209# x 1\r\n#i4032654# x15\r\n#i4032655# x15\r\n\r\n");
            }
        }
    } else if (status == 2) {
        if (mod == 0) {
            if (!cm.haveItem(1032205, 1) || !cm.haveItem(4032654, 3) || !cm.haveItem(4032655, 3)) {
                cm.sendOk("你的道具不足。");
                cm.dispose();
                return;
            } else if (!cm.canHold()) {
                cm.sendOk("您的背包已满。");
                cm.dispose();
                return;
            } else {
                cm.gainItem(1032205, -1);
                cm.gainItem(4032654, -3);
                cm.gainItem(4032655, -3);
                cm.gainItem(1032206, 1);
                cm.sendOk("兌換成功。");
                cm.dispose();
                return;
            }
            cm.dispose();
            return;

        } else if (mod == 1) {
            if (!cm.haveItem(1032206, 1) || !cm.haveItem(4032654, 3) || !cm.haveItem(4032655, 3)) {
                cm.sendOk("你的道具不足。");
                cm.dispose();
                return;
            } else if (!cm.canHold()) {
                cm.sendOk("您的背包已满。");
                cm.dispose();
                return;
            } else {
                cm.gainItem(1032206, -1);
                cm.gainItem(4032654, -3);
                cm.gainItem(4032655, -3);
                cm.gainItem(1032207, 1);
                cm.sendOk("兌換成功。");
                cm.dispose();
                return;
            }
            cm.dispose();
            return;
        } else if (mod == 2) {
            if (!cm.haveItem(1032207, 1) || !cm.haveItem(4032654, 3) || !cm.haveItem(4032655, 3)) {
                cm.sendOk("你的道具不足。");
                cm.dispose();
                return;
            } else if (!cm.canHold()) {
                cm.sendOk("您的背包已满。");
                cm.dispose();
                return;
            } else {
                cm.gainItem(1032207, -1);
                cm.gainItem(4032654, -3);
                cm.gainItem(4032655, -3);
                cm.gainItem(1032208, 1);
                cm.sendOk("兌換成功。");
                cm.dispose();
                return;
            }
            cm.dispose();
            return;
        } else if (mod == 3) {

            if (!cm.haveItem(1032208, 1) || !cm.haveItem(4032654, 6) || !cm.haveItem(4032655, 6)) {
                cm.sendOk("你的道具不足。");
                cm.dispose();
                return;
            } else if (!cm.canHold()) {
                cm.sendOk("您的背包已满。");
                cm.dispose();
                return;
            } else {
                cm.gainItem(1032208, -1);
                cm.gainItem(4032654, -6);
                cm.gainItem(4032655, -6);
                cm.gainItem(1032209, 1);
                cm.sendOk("兌換成功。");
                cm.dispose();
                return;
            }
            cm.dispose();
            return;
        } else if (mod == 4) {
            if (!cm.haveItem(1032209, 1) || !cm.haveItem(4032654, 15) || !cm.haveItem(4032655, 15)) {
                cm.sendOk("你的道具不足。");
                cm.dispose();
                return;
            } else if (!cm.canHold()) {
                cm.sendOk("您的背包已满。");
                cm.dispose();
                return;
            } else {
                cm.gainItem(1032209, -1);
                cm.gainItem(4032654, -15);
                cm.gainItem(4032655, -15);
                cm.gainItem(1032219, 1);
                cm.sendOk("兌換成功。");
                cm.dispose();
                return;
            }
            cm.dispose();
            return;
        }
    }
}
