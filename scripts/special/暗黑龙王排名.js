function start() {
    var Editing = false //false 开始
    if (Editing) {
        cm.sendOk("維修中");
        cm.dispose();
        return;
    }
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else if (mode == 0) {
        status--;
    } else {
        cm.dispose();
        return;
    }
    if (status == 0) {
        cm.sendSimple("#b#L0#暗黑龙王#l#k\r\n#r#L1#进阶暗黑龙王#l#k");

    } else if (status == 1) {

        sr = cm.getSpeedRun(selection == 0 ? "Horntail" : "ChaosHT");
        if (sr.getLeft().equals("")) {
            cm.sendOk("目前还沒有玩家挑战成功。");
            cm.dispose();
        } else {
            cm.sendSimple(sr.getLeft());
        }
    } else if (sr != null && selection > 0 && cm.getSR(sr, selection)) {
        status = -1;



        cm.dispose();
    }
}
