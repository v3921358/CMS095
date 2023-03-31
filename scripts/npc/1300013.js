function start() {
    cm.sendSimple("#b\r\n#L0#开始组队任务#l\r\n#L1#去结婚礼堂入口#l#k");
}

function action(mode,type,selection) {
    if (mode == 1) { //or 931000400 + selection..?
	switch(selection) {
	    case 0:
	    if (cm.getPlayer().getParty() == null || !cm.isLeader()) {
		cm.sendOk("请叫你的队长来找我.");
	    } else {
		var party = cm.getPlayer().getParty().getMembers();
		var mapId = cm.getPlayer().getMapId();
		var next = true;
		var size = 0;
		var it = party.iterator();
		while (it.hasNext()) {
			var cPlayer = it.next();
			var ccPlayer = cm.getPlayer().getMap().getCharacterById(cPlayer.getId());
			if (ccPlayer == null) {
				next = false;
				break;
			}
			size += (ccPlayer.isGM() ? 4 : 1);
		}	
		if (next && (cm.getPlayer().isGM() || size >= 1)) {
	    	    for(var i = 0; i < 10; i++) {
			if (cm.getMap(106021500 + i).getCharactersSize() == 0) {
		    		cm.warpParty(106021500 + i);
				cm.dispose();
		    		return;
			}
	    	    }
			cm.sendOk("已有人在执行任务。");
		} else {
			cm.sendOk("必须要组队进行.");
		}
	    }
		break;
	    case 1:
		cm.warp(106021401,0);
		break;
	}
    }
    cm.dispose();
}