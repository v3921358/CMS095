

var status = -1;
var sel;
var mod;
function start() {
    cm.sendSimple("嗨，我是枫葉之心兌換员有什么可以帮忙的？？ \r\n\r\n#b#L4#合成枫葉之心。#l \r\n\r\n#b#L0#兌換封印的枫葉之心。#l \r\n\r\n#L1#兌換甦醒的枫葉之心。#l \r\n\r\n#L2#兌換觉醒的枫葉之心。#l \r\n\r\n#L3#兌換真.枫葉之心。#l#k");
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
        if (sel == 4) {
            cm.sendSimple("所以你想做什么？？ \r\n#b#L0##z1122019##l  \r\n");
        } else if (sel == 0) {
            cm.sendSimple("所以你想做什么？？ \r\n#b#L0##z1122024#(剑士用)#l \r\n#b#L1##z1122025#(法师用)#l \r\n#b#L2##z1122026#(弓箭手用)#l \r\n#b#L3##z1122027#(盜賊用)#l \r\n#b#L4##z1122028#(海盜用)#l \r\n");
        } else if (sel == 1) {
            cm.sendSimple("所以你想做什么？？ \r\n#b#L0##z1122029#(剑士用)#l \r\n#b#L1##z1122030#(法师用)#l \r\n#b#L2##z1122031#(弓箭手用)#l \r\n#b#L3##z1122032#(盜賊用)#l \r\n#b#L4##z1122033#(海盜用)#l \r\n");
        } else if (sel == 2) {
            cm.sendSimple("所以你想做什么？？ \r\n#b#L0##z1122034#(剑士用)#l \r\n#b#L1##z1122035#(法师用)#l \r\n#b#L2##z1122036#(弓箭手用)#l \r\n#b#L3##z1122037#(盜賊用)#l \r\n#b#L4##z1122038#(海盜用)#l \r\n");
        } else if (sel == 3) {
            cm.sendSimple("所以你想做什么？？ \r\n#b#L0##z1122122##l \r\n#b#L1##z1122123##l \r\n#b#L2##z1122124##l \r\n#b#L3##z1122125##l \r\n#b#L4##z1122126##l \r\n");
        }
    } else if (status == 1) {
        if (sel == 4) {
            if (selection == 0) {
                mod = 40;
                cm.sendYesNo("您确定要做一个 #b#t1122019##k? \r\n以下是你所需要的材料。\r\n\r\n#i4001220# x 1\r\n#i4032177# x1\r\n\r\n");
            }
        } else if (sel == 0) {
            if (selection == 0) {
                mod = 0;
                cm.sendYesNo("您确定要做一个 #b#t1122024##k? \r\n以下是你所需要的材料。\r\n\r\n#i1122019# x 1\r\n#i4001226# x2\r\n\r\n");
            } else if (selection == 1) {
                mod = 1;
                cm.sendYesNo("您确定要做一个 #b#t1122025##k? \r\n以下是你所需要的材料。\r\n\r\n#i1122019# x 1\r\n#i4001227# x2\r\n\r\n");
            } else if (selection == 2) {
                mod = 2;
                cm.sendYesNo("您确定要做一个 #b#t1122026##k? \r\n以下是你所需要的材料。\r\n\r\n#i1122019# x 1\r\n#i4001228# x2\r\n\r\n");
            } else if (selection == 3) {
                mod = 3;
                cm.sendYesNo("您确定要做一个 #b#t1122027##k? \r\n以下是你所需要的材料。\r\n\r\n#i1122019# x 1\r\n#i4001229# x2\r\n\r\n");
            } else if (selection == 4) {
                mod = 4;
                cm.sendYesNo("您确定要做一个 #b#t1122028##k? \r\n以下是你所需要的材料。\r\n\r\n#i1122019# x 1\r\n#i4001230# x2\r\n\r\n");
            }
        } else if (sel == 1) {
            if (selection == 0) {
                mod = 10;
                cm.sendYesNo("您确定要做一个 #b#t1122029##k? \r\n以下是你所需要的材料。\r\n\r\n#i1122024# x 1\r\n#i4001226# x4\r\n\r\n");
            } else if (selection == 1) {
                mod = 11;
                cm.sendYesNo("您确定要做一个 #b#t1122030##k? \r\n以下是你所需要的材料。\r\n\r\n#i1122025# x 1\r\n#i4001227# x4\r\n\r\n");
            } else if (selection == 2) {
                mod = 12;
                cm.sendYesNo("您确定要做一个 #b#t1122031##k? \r\n以下是你所需要的材料。\r\n\r\n#i1122026# x 1\r\n#i4001228# x4\r\n\r\n");
            } else if (selection == 3) {
                mod = 13;
                cm.sendYesNo("您确定要做一个 #b#t1122032##k? \r\n以下是你所需要的材料。\r\n\r\n#i1122027# x 1\r\n#i4001229# x4\r\n\r\n");
            } else if (selection == 4) {
                mod = 14;
                cm.sendYesNo("您确定要做一个 #b#t1122033##k? \r\n以下是你所需要的材料。\r\n\r\n#i1122028# x 1\r\n#i4001230# x4\r\n\r\n");
            }
        } else if (sel == 2) {
            if (selection == 0) {
                mod = 20;
                cm.sendYesNo("您确定要做一个 #b#t1122034##k? \r\n以下是你所需要的材料。\r\n\r\n#i1122029# x 1\r\n#i4001226# x8\r\n\r\n");
            } else if (selection == 1) {
                mod = 21;
                cm.sendYesNo("您确定要做一个 #b#t1122035##k? \r\n以下是你所需要的材料。\r\n\r\n#i1122030# x 1\r\n#i4001227# x8\r\n\r\n");
            } else if (selection == 2) {
                mod = 22;
                cm.sendYesNo("您确定要做一个 #b#t1122036##k? \r\n以下是你所需要的材料。\r\n\r\n#i1122031# x 1\r\n#i4001228# x8\r\n\r\n");
            } else if (selection == 3) {
                mod = 23;
                cm.sendYesNo("您确定要做一个 #b#t1122037##k? \r\n以下是你所需要的材料。\r\n\r\n#i1122032# x 1\r\n#i4001229# x8\r\n\r\n");
            } else if (selection == 4) {
                mod = 24;
                cm.sendYesNo("您确定要做一个 #b#t1122038##k? \r\n以下是你所需要的材料。\r\n\r\n#i1122033# x 1\r\n#i4001230# x8\r\n\r\n");
            }
        } else if (sel == 3) {
            if (selection == 0) {
                mod = 30;
                cm.sendYesNo("您确定要做一个 #b#t1122122##k? \r\n以下是你所需要的材料。\r\n\r\n#i1122034# x 1\r\n#i4001226# x10\r\n\r\n");
            } else if (selection == 1) {
                mod = 31;
                cm.sendYesNo("您确定要做一个 #b#t1122123##k? \r\n以下是你所需要的材料。\r\n\r\n#i1122035# x 1\r\n#i4001227# x10\r\n\r\n");
            } else if (selection == 2) {
                mod = 32;
                cm.sendYesNo("您确定要做一个 #b#t1122124##k? \r\n以下是你所需要的材料。\r\n\r\n#i1122036# x 1\r\n#i4001228# x10\r\n\r\n");
            } else if (selection == 3) {
                mod = 33;
                cm.sendYesNo("您确定要做一个 #b#t1122125##k? \r\n以下是你所需要的材料。\r\n\r\n#i1122037# x 1\r\n#i4001229# x10\r\n\r\n");
            } else if (selection == 4) {
                mod = 34;
                cm.sendYesNo("您确定要做一个 #b#t1122126##k? \r\n以下是你所需要的材料。\r\n\r\n#i1122038# x 1\r\n#i4001230# x10\r\n\r\n");
            }
        }
    } else if (status == 2) {
        if (mod == 40) {
            if (!cm.haveItem(4001220, 1) || !cm.haveItem(4032177, 1)) {
                cm.sendOk("你的道具不足。");
                cm.dispose();
                return;
            } else if (!cm.canHold()) {
                cm.sendOk("您的背包已满。");
                cm.dispose();
                return;
            } else {
                cm.gainItem(4001220, -1);
                cm.gainItem(4032177, -1);
                cm.gainItem(1122019, 1);
                cm.sendOk("兌換成功。");
                cm.dispose();
                return;
            }
            cm.dispose();
            return;
        } else if (mod == 0) {
            if (!cm.haveItem(1122019, 1) || !cm.haveItem(4001226, 2)) {
                cm.sendOk("你的道具不足。");
                cm.dispose();
                return;
            } else if (!cm.canHold()) {
                cm.sendOk("您的背包已满。");
                cm.dispose();
                return;
            } else {
                cm.gainItem(1122019, -1);
                cm.gainItem(4001226, -2);
                cm.gainItem(1122024, 1);
                cm.sendOk("兌換成功。");
                cm.dispose();
                return;
            }
            cm.dispose();
            return;

        } else if (mod == 1) {
            if (!cm.haveItem(1122019, 1) || !cm.haveItem(4001227, 2)) {
                cm.sendOk("你的道具不足。");
                cm.dispose();
                return;
            } else if (!cm.canHold()) {
                cm.sendOk("您的背包已满。");
                cm.dispose();
                return;
            } else {
                cm.gainItem(1122019, -1);
                cm.gainItem(4001227, -2);
                cm.gainItem(1122025, 1);
                cm.sendOk("兌換成功。");
                cm.dispose();
                return;
            }
            cm.dispose();
            return;
        } else if (mod == 2) {
            if (!cm.haveItem(1122019, 1) || !cm.haveItem(4001228, 2)) {
                cm.sendOk("你的道具不足。");
                cm.dispose();
                return;
            } else if (!cm.canHold()) {
                cm.sendOk("您的背包已满。");
                cm.dispose();
                return;
            } else {
                cm.gainItem(1122019, -1);
                cm.gainItem(4001228, -2);
                cm.gainItem(1122026, 1);
                cm.sendOk("兌換成功。");
                cm.dispose();
                return;
            }
            cm.dispose();
            return;
        } else if (mod == 3) {

            if (!cm.haveItem(1122019, 1) || !cm.haveItem(4001229, 2)) {
                cm.sendOk("你的道具不足。");
                cm.dispose();
                return;
            } else if (!cm.canHold()) {
                cm.sendOk("您的背包已满。");
                cm.dispose();
                return;
            } else {
                cm.gainItem(1122019, -1);
                cm.gainItem(4001229, -2);
                cm.gainItem(1122027, 1);
                cm.sendOk("兌換成功。");
                cm.dispose();
                return;
            }
            cm.dispose();
            return;
        } else if (mod == 4) {
            if (!cm.haveItem(1122019, 1) || !cm.haveItem(4001230, 2)) {
                cm.sendOk("你的道具不足。");
                cm.dispose();
                return;
            } else if (!cm.canHold()) {
                cm.sendOk("您的背包已满。");
                cm.dispose();
                return;
            } else {
                cm.gainItem(1122019, -1);
                cm.gainItem(4001230, -2);
                cm.gainItem(1122028, 1);
                cm.sendOk("兌換成功。");
                cm.dispose();
                return;
            }
            cm.dispose();
            return;
        } else if (mod == 10) {
            if (!cm.haveItem(1122024, 1) || !cm.haveItem(4001226, 4)) {
                cm.sendOk("你的道具不足。");
                cm.dispose();
                return;
            } else if (!cm.canHold()) {
                cm.sendOk("您的背包已满。");
                cm.dispose();
                return;
            } else {
                cm.gainItem(1122024, -1);
                cm.gainItem(4001226, -4);
                cm.gainItem(1122029, 1);
                cm.sendOk("兌換成功。");
                cm.dispose();
                return;
            }
            cm.dispose();
            return;
        } else if (mod == 11) {
            if (!cm.haveItem(1122025, 1) || !cm.haveItem(4001227, 4)) {
                cm.sendOk("你的道具不足。");
                cm.dispose();
                return;
            } else if (!cm.canHold()) {
                cm.sendOk("您的背包已满。");
                cm.dispose();
                return;
            } else {
                cm.gainItem(1122025, -1);
                cm.gainItem(4001227, -4);
                cm.gainItem(1122030, 1);
                cm.sendOk("兌換成功。");
                cm.dispose();
                return;
            }
            cm.dispose();
            return;
        } else if (mod == 12) {
            if (!cm.haveItem(1122026, 1) || !cm.haveItem(4001228, 4)) {
                cm.sendOk("你的道具不足。");
                cm.dispose();
                return;
            } else if (!cm.canHold()) {
                cm.sendOk("您的背包已满。");
                cm.dispose();
                return;
            } else {
                cm.gainItem(1122026, -1);
                cm.gainItem(4001228, -4);
                cm.gainItem(1122031, 1);
                cm.sendOk("兌換成功。");
                cm.dispose();
                return;
            }
            cm.dispose();
            return;
        } else if (mod == 13) {
            if (!cm.haveItem(1122027, 1) || !cm.haveItem(4001229, 4)) {
                cm.sendOk("你的道具不足。");
                cm.dispose();
                return;
            } else if (!cm.canHold()) {
                cm.sendOk("您的背包已满。");
                cm.dispose();
                return;
            } else {
                cm.gainItem(1122027, -1);
                cm.gainItem(4001229, -4);
                cm.gainItem(1122032, 1);
                cm.sendOk("兌換成功。");
                cm.dispose();
                return;
            }
            cm.dispose();
            return;
        } else if (mod == 14) {
            if (!cm.haveItem(1122028, 1) || !cm.haveItem(4001230, 4)) {
                cm.sendOk("你的道具不足。");
                cm.dispose();
                return;
            } else if (!cm.canHold()) {
                cm.sendOk("您的背包已满。");
                cm.dispose();
                return;
            } else {
                cm.gainItem(1122028, -1);
                cm.gainItem(4001230, -4);
                cm.gainItem(1122033, 1);
                cm.sendOk("兌換成功。");
                cm.dispose();
                return;
            }
            cm.dispose();
            return;
        } else if (mod == 20) {
            if (!cm.haveItem(1122029, 1) || !cm.haveItem(4001226, 8)) {
                cm.sendOk("你的道具不足。");
                cm.dispose();
                return;
            } else if (!cm.canHold()) {
                cm.sendOk("您的背包已满。");
                cm.dispose();
                return;
            } else {
                cm.gainItem(1122029, -1);
                cm.gainItem(4001226, -8);
                cm.gainItem(1122034, 1);
                cm.sendOk("兌換成功。");
                cm.dispose();
                return;
            }
            cm.dispose();
            return;
        } else if (mod == 21) {
            if (!cm.haveItem(1122030, 1) || !cm.haveItem(4001227, 8)) {
                cm.sendOk("你的道具不足。");
                cm.dispose();
                return;
            } else if (!cm.canHold()) {
                cm.sendOk("您的背包已满。");
                cm.dispose();
                return;
            } else {
                cm.gainItem(1122030, -1);
                cm.gainItem(4001227, -8);
                cm.gainItem(1122035, 1);
                cm.sendOk("兌換成功。");
                cm.dispose();
                return;
            }
            cm.dispose();
            return;
        } else if (mod == 22) {
            if (!cm.haveItem(1122031, 1) || !cm.haveItem(4001228, 8)) {
                cm.sendOk("你的道具不足。");
                cm.dispose();
                return;
            } else if (!cm.canHold()) {
                cm.sendOk("您的背包已满。");
                cm.dispose();
                return;
            } else {
                cm.gainItem(1122031, -1);
                cm.gainItem(4001228, -8);
                cm.gainItem(1122036, 1);
                cm.sendOk("兌換成功。");
                cm.dispose();
                return;
            }
            cm.dispose();
            return;
        } else if (mod == 23) {
            if (!cm.haveItem(1122032, 1) || !cm.haveItem(4001229, 8)) {
                cm.sendOk("你的道具不足。");
                cm.dispose();
                return;
            } else if (!cm.canHold()) {
                cm.sendOk("您的背包已满。");
                cm.dispose();
                return;
            } else {
                cm.gainItem(1122032, -1);
                cm.gainItem(4001229, -8);
                cm.gainItem(1122037, 1);
                cm.sendOk("兌換成功。");
                cm.dispose();
                return;
            }
            cm.dispose();
            return;
        } else if (mod == 24) {
            if (!cm.haveItem(1122033, 1) || !cm.haveItem(4001230, 8)) {
                cm.sendOk("你的道具不足。");
                cm.dispose();
                return;
            } else if (!cm.canHold()) {
                cm.sendOk("您的背包已满。");
                cm.dispose();
                return;
            } else {
                cm.gainItem(1122033, -1);
                cm.gainItem(4001230, -8);
                cm.gainItem(1122038, 1);
                cm.sendOk("兌換成功。");
                cm.dispose();
                return;
            }
            cm.dispose();
            return;
        } else if (mod == 30) {
            if (!cm.haveItem(1122034, 1) || !cm.haveItem(4001226, 10)) {
                cm.sendOk("你的道具不足。");
                cm.dispose();
                return;
            } else if (!cm.canHold()) {
                cm.sendOk("您的背包已满。");
                cm.dispose();
                return;
            } else {
                cm.gainItem(1122034, -1);
                cm.gainItem(4001226, -10);
                cm.gainItem(1122122, 1);
                cm.sendOk("兌換成功。");
                cm.dispose();
                return;
            }
            cm.dispose();
            return;
        } else if (mod == 31) {
            if (!cm.haveItem(1122035, 1) || !cm.haveItem(4001227, 10)) {
                cm.sendOk("你的道具不足。");
                cm.dispose();
                return;
            } else if (!cm.canHold()) {
                cm.sendOk("您的背包已满。");
                cm.dispose();
                return;
            } else {
                cm.gainItem(1122035, -1);
                cm.gainItem(4001227, -10);
                cm.gainItem(1122123, 1);
                cm.sendOk("兌換成功。");
                cm.dispose();
                return;
            }
            cm.dispose();
            return;
        } else if (mod == 32) {
            if (!cm.haveItem(1122036, 1) || !cm.haveItem(4001228, 10)) {
                cm.sendOk("你的道具不足。");
                cm.dispose();
                return;
            } else if (!cm.canHold()) {
                cm.sendOk("您的背包已满。");
                cm.dispose();
                return;
            } else {
                cm.gainItem(1122036, -1);
                cm.gainItem(4001228, -10);
                cm.gainItem(1122124, 1);
                cm.sendOk("兌換成功。");
                cm.dispose();
                return;
            }
            cm.dispose();
            return;
        } else if (mod == 33) {
            if (!cm.haveItem(1122037, 1) || !cm.haveItem(4001229, 10)) {
                cm.sendOk("你的道具不足。");
                cm.dispose();
                return;
            } else if (!cm.canHold()) {
                cm.sendOk("您的背包已满。");
                cm.dispose();
                return;
            } else {
                cm.gainItem(1122037, -1);
                cm.gainItem(4001229, -8);
                cm.gainItem(1122125, 1);
                cm.sendOk("兌換成功。");
                cm.dispose();
                return;
            }
            cm.dispose();
            return;
        } else if (mod == 34) {
            if (!cm.haveItem(1122038, 1) || !cm.haveItem(4001230, 10)) {
                cm.sendOk("你的道具不足。");
                cm.dispose();
                return;
            } else if (!cm.canHold()) {
                cm.sendOk("您的背包已满。");
                cm.dispose();
                return;
            } else {
                cm.gainItem(1122038, -1);
                cm.gainItem(4001230, -8);
                cm.gainItem(1122126, 1);
                cm.sendOk("兌換成功。");
                cm.dispose();
                return;
            }
            cm.dispose();
            return;
        }
    }
}
