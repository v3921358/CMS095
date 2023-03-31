var status = -1;
var sel = -1;

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        if (status == 0) {
            cm.dispose();
        }
        status--;
    }
    if (status == 0) {
        cm.sendSimple("#L0##b剑之地?#k#l\r\n#L1##b游戏之地。#k#l\r\n#L2##b暴風之地#k#l\r\n#L3##b黑暗之地#k#l\r\n#L4##b閃電之地#k#l");
    } else if (status == 1) {
        sel = selection;
        if (sel == 0) {
            cm.warp(271030201, 0);
        } else if (sel == 1) {
            cm.warp(271030202, 0);
        } else if (sel == 2) {
            cm.warp(271030203, 0);
        } else if (sel == 3) {
            cm.warp(271030204, 0);
        } else if (sel == 4) {
            cm.warp(271030205, 0);
        }
        cm.dispose();
    }
}