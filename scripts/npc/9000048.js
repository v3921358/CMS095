
var status = 0;

function start() {
    if (cm.getMapId() == 999990000) {
        cm.warp(910000000);
        cm.dispose();
        return;
    }
    cm.dispose();
    cm.openNpc(9000048, "公会系統");
    return;
}

