function start() {
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
		/*var map = cm.getSavedLocation("EVENT");
		if (map > -1 && map != cm.getMapId()) {
			cm.warp(map, 0);
            cm.sendOk("No More Trophy For You :)");
            cm.gainItem(4000038, 0);
            cm.dispose();
		} else {*/
		var rankinfo_list = cm.getBosslogDCidTop("向高地");
		if (rankinfo_list.length < 1){
            cm.sendOk("恭喜你获得第一名~:)");
			cm.worldMessage(5,"【跳跳活动】恭喜玩家"+cm.getPlayer().getName()+"在跳跳活动“向高地”中获取第一名的成绩，荣获超级大奖！")
            cm.setBossLog("向高地");
			cm.gainNX(5000);
			cm.gainMeso(4000000);
			cm.gainItem(2041200,3);
			cm.warp(910000000, 0);
			cm.dispose();
			return;
		}
		if (rankinfo_list.length < 2){
            cm.sendOk("恭喜你获得第二名~:)");
			cm.worldMessage(5,"【跳跳活动】恭喜玩家"+cm.getPlayer().getName()+"在跳跳活动“向高地”中获取第二名的成绩，荣获超级大奖！")
            cm.setBossLog("向高地");
			cm.gainNX(4000);
			cm.gainMeso(3000000);
			cm.gainItem(2041200,2);
			cm.warp(910000000, 0);
			cm.dispose();
			return;
		}
		if (rankinfo_list.length < 3){
            cm.sendOk("恭喜你获得第三名~:)");
			cm.worldMessage(5,"【跳跳活动】恭喜玩家"+cm.getPlayer().getName()+"在跳跳活动“向高地”中获取第三名的成绩，荣获超级大奖！")
            cm.setBossLog("向高地");
			cm.gainNX(3000);
			cm.gainMeso(2000000);
			cm.gainItem(2041200,1);
			cm.warp(910000000, 0);
			cm.dispose();
			return;
		}
		cm.sendOk("恭喜你获得参与奖~:)");
		cm.worldMessage(5,"【跳跳活动】恭喜玩家"+cm.getPlayer().getName()+"在跳跳活动“向高地”中获得参与奖！")
		cm.setBossLog("向高地");
		cm.gainNX(2000);
		cm.gainMeso(1000000);
		cm.warp(910000000, 0);
		cm.dispose();
		return;
		//
	 }
}
