
var status = 0;

function start() {
    if (cm.getMapId() == 951000000) {
        var returnMap = cm.getSavedLocation("MULUNG_TC");
    if (returnMap < 0) {
        returnMap = 100000000; // to fix people who entered the fm trough an unconventional way
    }
    cm.clearSavedLocation("MULUNG_TC");
    cm.warp(returnMap, "unityPortal2");
	cm.dispose();
	return;
    }
    cm.sendYesNo("你要到怪物公園去吗?");
}

function action(mode, type, selection) {
    if (mode == 1) {
        cm.saveReturnLocation("MULUNG_TC");
        cm.warp(951000000);
    }
    cm.dispose();
}