/**
 Author: xQuasar
 NPC: Kyrin - Pirate Job Advancer
 Inside Test Room
 **/

var status;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (status == -1) {
        if (cm.getMapId() == 912040000) {
            if (!(cm.haveItem(4031857, 15))) {
                cm.sendNext("快去收集 15个 #b列風結晶#k 給我.");
                cm.dispose();
            } else {
                status = 2;
                cm.sendNext("wow 果然是个大俠恭喜通过这次个考验 你已经是个强大的海盜了所以我将頒贈給你神秘的小礼物.");
            }
        } else if (cm.getMapId() == 912040005) {
            if (!(cm.haveItem(4031856, 15))) {
                cm.sendNext("快去收集15个 #b强大力量結晶#k 給我.");
                cm.dispose();
            } else {
                status = 2;
                cm.sendNext("wow 果然是个大俠恭喜通过这次个考验 你已经是个强大的海盜了所以我将頒贈給你神秘的小礼物.");
            }
        } else {
            cm.sendNext("错誤请再嘗试一次.");
            cm.dispose();
        }
    } else if (status == 2) {
        cm.gainItem(4031012, 1);
        cm.warp(120000101, 0);
        cm.dispose();
    }
}