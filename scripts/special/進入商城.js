
var status = -1;
var sel;
var mod;
function start() {
    cm.dispose();
            cm.EnterCS(1);
            return;
    //cm.sendSimple("#r你确定要进入商城吗。#k \r\n\r\n#b#L0#超級商城1#l \r\n\r\n#b#L1#超級商城2#l#k");
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
            cm.dispose();
            cm.EnterCS(1);
            return;
        }
        if (sel == 1) {
            cm.dispose();
            cm.EnterCS(2);
            return;
        }
        cm.dispose();
        return;
    }
}
