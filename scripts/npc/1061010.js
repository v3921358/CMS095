function start() {
    cm.sendYesNo("请问你是否要离开呢??");
}

function action(mode, type, selection) {
    if (mode == 1) {
        var map = cm.getMapId();
        var kill = cm.getMap().killAllMonsters(true);
        var tomap;

        if (map == 910540300) {
            kill;
            tomap = 100000000;
        } else if (map == 910540200) {
            kill;
            tomap = 101000000;
        } else if (map == 910540100) {
            kill;
            tomap = 102000000;
        } else if (map == 910540400) {
            kill;
            tomap = 103000000;
        } else if (map == 910540500) {
            kill;
            tomap = 120000000;
        }
        cm.warp(tomap);
    }
    cm.dispose();
}
