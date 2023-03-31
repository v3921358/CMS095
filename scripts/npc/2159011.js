var status = -1;
function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        if (status != 0) {
            cm.dispose();
            return;
        }
        status--;
    }

    if (status == -1) {
        cm.sendNext("#b（怎么可能…再怎么说潘也不会躲到里面去的…是吧？）#k");
        cm.dispose();
    } else if (status == 0) {
        cm.sendYesNo("#b（看見可疑的洞口，不知道潘是不是跑进去里面了。要进去看看吗？）#k");
    } else if (status == 1) {
        cm.gainExp(35);
        cm.warp(931000010, 0);
        cm.dispose();
    }
}