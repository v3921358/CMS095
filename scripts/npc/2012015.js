﻿/**
 El Nath Magic Spot - Orbis Tower <20th Floor>(200080200)
 **/

var status = -1;

function action(mode, type, selection) {
    if (mode == 0) {
        cm.dispose();
        return;
    }
    status++;

    if (status == 0) {
        if (cm.haveItem(4001019)) {
            cm.sendYesNo("<要使用天空之塔移动卷軸移动至20層吗>");
        } else {
            cm.sendOk("<你身上沒有天空之塔移动卷軸>");
            cm.safeDispose();
        }
    }
    if (status == 1) {
        cm.gainItem(4001019, -1);
        cm.warp(200080200, 0);
        cm.dispose();
    }
}
