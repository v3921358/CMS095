/**
 Orbis Magic Spot - Orbis Tower <1st Floor>(200082100)
 **/

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 0) {
        cm.dispose();
        return;
    }
    status++;
    if (status == 0) {
        if (cm.haveItem(4001019)) {
            cm.sendYesNo("<要使用天空之塔移动卷軸移动至1層吗>");
        } else {
            cm.sendOk("<你身上沒有天空之塔移动卷軸>");
            cm.dispose();
        }
    }
    if (status == 1) {
        cm.gainItem(4001019, -1);
        cm.warp(200082100, 0);
        cm.dispose();
    }
}
