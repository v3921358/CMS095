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
    if (cm.getMapId() != 680000200) {
	cm.dispose();
	return;
    }
    if (status == 0) {
	cm.sendYesNo("你要前往結婚礼堂吗?");
    } else if (status == 1) {

	    var marr = cm.getQuestRecord(160001);
	    var data = marr.getCustomData();
	    if (data == null) {
		marr.setCustomData("0");
	        data = "0";
	    }
	    if (data.equals("1")) {
		if (cm.getPlayer().getMarriageId() <= 0) {
		    cm.sendOk("好像发生了错誤了，您好像还沒有跟任何人結婚！");
		    cm.dispose();
		    return;
		}
	    	var chr = cm.getMap().getCharacterById(cm.getPlayer().getMarriageId());
	    	if (chr == null) {
		    cm.sendOk("请确保您的另一半在地图上。");
		    cm.dispose();
		    return;
	    	}
		var maps = Array(680000210, 680000300, 680000400, 680000401);
		for (var i = 0; i < maps.length; i++) {
		    if (cm.getMap(maps[i]).getCharactersSize() > 0) {
			cm.sendNext("目前有人正在舉行婚礼，麻煩请这对新人稍等一下。");
			cm.dispose();
			return;
		    }
		}
		var map = cm.getMap(680000210);
		cm.getPlayer().changeMap(map, map.getPortal("we00"));
		chr.changeMap(map, map.getPortal("we00"));
//		cm.getPlayer().changeMap(map, map.getPortal(0));
//		chr.changeMap(map, map.getPortal(0));
		cm.worldMessage(5, "<頻道 " + cm.getClient().getChannel() + "> " + cm.getPlayer().getName() + " 跟 " + chr.getName() + "'的婚礼即将开始舉行,要參加的玩家请到結婚小鎮找修女进入！");
	    } else {
		if (cm.getMap(680000210).getCharactersSize() == 0) {
		    cm.sendNext("目前沒有婚礼在舉行，请稍后再嘗试。");
		    cm.dispose();
		    return;
		}
//		if (cm.haveItem(4150000)) {
		    cm.warp(680000210,0);
//		} else {
//		    cm.sendOk("You do not have a wedding invitation.");
//		}
	    }
	cm.dispose();
    }
}