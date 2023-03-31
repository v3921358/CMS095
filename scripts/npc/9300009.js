var status = 0

function start(){
	action(1, 0, 0);
}

function action(mode, type ,selection){
	if(mode == 1) {
		status++;
	} else if(mode == 0) {
		status--;
	} else {
		cm.dispose();
		return;
	}
	if(status == 1){
		if (cm.haveItem(1112320)) {
		cm.sendYesNo("你想回去吗？");
		} else {
			cm.sendOk("你还没有领取#v1112320##z1112320#,不准出去");
			cm.dispose();
		}
	} else if(status == 2){
		//var map = cm.getSavedLocation("WEDDING");
		cm.warp(700000000, 0);
		//cm.clearSavedLocation("WEDDING");
		cm.dispose();	
	} else {
		cm.dispose();
	}
}