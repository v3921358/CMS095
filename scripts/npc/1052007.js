var itemid = new Array(4031036, 4031037, 4031711);
var mapid = new Array(103020100, 103000310, 600010004);
var menu;
var status;

function start() {
    status = 0;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 0 && status == 1) {
        cm.dispose();
    } else {
        if (mode == 0) {
            cm.sendNext("You must have some business to take care of here, right?");
            cm.dispose();
            return;
        }
        if (mode == 1)
            status++;
        if (status == 1) {
            menu = "这里是检票口，必须购买地铁票才可以进入！\r\n";
            for (i = 0; i < itemid.length; i++) {
                if (cm.haveItem(itemid[i])) {
                    menu += "#L" + i + "##b#m" + mapid[i] + "##k#l\r\n";
                }
            }
            cm.sendSimple(menu);
        }
        if (status == 2) {
            section = selection;
            if (section == 0) {
                cm.gainItem(4031036, -1);
                cm.warp(103020100);
                cm.dispose();
            } else if (section == 1) {
                cm.gainItem(4031037, -1);
                cm.warp(103020020);
                cm.dispose();
            } else if (section == 2) {
                cm.gainItem(4031711, -1);
                cm.warp(600010004);
                cm.dispose();
            }else{
                cm.dispose();
            }
        }
    }
}