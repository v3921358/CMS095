var status = -1;

function action(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
	if (status == 0) {
	    cm.dispose();
	}
	status--;
    }
    if (status == 0) {
	if (cm.getMap().getAllMonstersThreadsafe().size() <= 0) {
	    cm.sendOk("当前地图沒有怪物數据。");
	    cm.dispose();
	    return;
	}
	var selStr = "请選擇你想查詢的怪物。\r\n\r\n#b";
	var iz = cm.getMap().getAllUniqueMonsters().iterator();
	while (iz.hasNext()) {
	    var zz = iz.next();
	    selStr += "#L" + zz + "##o" + zz + "##l\r\n";
	} 
	cm.sendSimple(selStr);
    } else if (status == 1) {
	cm.sendOk(cm.checkDrop(selection));
	cm.dispose();
    }
}