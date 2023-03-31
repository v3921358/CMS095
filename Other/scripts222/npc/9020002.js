﻿/*
	Nella - Hidden Street : 1st Accompaniment
*/

var status;

function start() {
    status = -1;
    action(1,0,0);
}

function action(mode, type, selection){
    if (mode == 0 && status == 0) {
	cm.dispose();
	return;
    } else {
	if (mode == 1)
	    status++;
	else
	    status--;
	var mapId = cm.getMapId();
	if (mapId == 910340000) { 
	    cm.warp(910340700, 0);
	    cm.removeAll(4001007);
	    cm.removeAll(4001008);
	    cm.dispose();
	} else {
	    var outText;
	    if (mapId == 910340600) {
		outText = "你确定要离开地图？？";
	    } else {
		outText = "一旦你离开地图，你将不得不重新启动整个任务，如果你想再次尝试。你还是要离开这个地图？";
	    }
	    if (status == 0) {
		cm.sendYesNo(outText);
	    } else if (mode == 1) {
		cm.warp(910340000, "st00"); // Warp player
		cm.dispose();
	    }
	}
    }
}