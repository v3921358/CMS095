var points;

function start() {
		if (cm.getPlayer().getMapId() == 910340500) {
            cm.sendSimple("我能为您做什么吗？？\n\r #b#L5#你想要出去吗。。#l");
        } else if (cm.getPlayer().getMapId() == 220080001) {
            cm.sendSimple("我能为您做什么吗？？\n\r #b#L5#你想要出去吗。。#l");
        } else {  
    var record = cm.getPlayer().getIntNoRecord(15001);
    points = record == null ? "0" : record;
	var tet = "";
	tet += "你想尝尝残酷的Boss之战吗?\n\r\n\r #b#L3#当前Boss点数#l#k \r\n\r\n";
	if (cm.getPlayer().getMapId() == 923020100) {
	tet += "#b#L4#我要出去。。#l\r\n\r\n";
	}
	tet += "#b#L0# #v03994115##l #L1# #v03994116##l #L2# #v03994117##l #L28# #v03994118##l";
  //  if (cm.getPlayerStat("GM")) {
    cm.sendNext(tet);
  /*  } else {
        cm.sendOk("I am not coded yet c:");
        cm.dispose();
    }*/
	}
}

function action(mode, type, selection) {
    if (mode == 1) {
	switch (selection) {
	    case 0:
		if (cm.getPlayer().getBossLog("雾海1") > 0) {
			cm.sendOk("简单难度每天只能进行1次。");
			cm.dispose();
			return;
		}
		if (cm.getParty() != null) {
		if (cm.getDisconnected("BossQuestEASY") != null) {
			cm.getDisconnected("BossQuestEASY").registerPlayer(cm.getPlayer());
		 } else if (cm.isLeader()) {
		    var party = cm.getPlayer().getParty().getMembers();
		    var mapId = cm.getPlayer().getMapId();
		    var next = true;
		    var it = party.iterator();
		    while (it.hasNext()) {
			var cPlayer = it.next();
			var ccPlayer = cm.getPlayer().getMap().getCharacterById(cPlayer.getId());
			if (ccPlayer == null || ccPlayer.getLevel() < 60) {
				next = false;
				break;
			}
		    }	
	  	    if (next) {
				cm.setBossLog("雾海1");
			var q = cm.getEventManager("BossQuestEASY");
			if (q == null) {
			    cm.sendOk("Unknown error occured");
			} else {
			    q.startInstance(cm.getParty(), cm.getMap());
			}
		    } else {
			cm.sendOk("All players must be in map and above level 70.");
		    }
		} else {
		    cm.sendOk("You are not the leader of the party, please ask your leader to talk to me.");
		}
		} else {
		    cm.sendOk("Please form a party first.");
		}
		break;
	    case 1:
		if (cm.getPlayer().getBossLog("雾海2") > 0) {
			cm.sendOk("普通难度每天只能进行1次。");
			cm.dispose();
			return;
		}
		if (cm.getParty() != null) {
		if (cm.getDisconnected("BossQuestMed") != null) {
			cm.getDisconnected("BossQuestMed").registerPlayer(cm.getPlayer());
		 } else if (cm.isLeader()) {
		    var party = cm.getPlayer().getParty().getMembers();
		    var mapId = cm.getPlayer().getMapId();
		    var next = true;
		    var it = party.iterator();
		    while (it.hasNext()) {
			var cPlayer = it.next();
			var ccPlayer = cm.getPlayer().getMap().getCharacterById(cPlayer.getId());
			if (ccPlayer == null || ccPlayer.getLevel() < 100) {
				next = false;
				break;
			}
		    }	
	  	    if (next) {
				cm.setBossLog("雾海2");
			var q = cm.getEventManager("BossQuestMed");
			if (q == null) {
			    cm.sendOk("Unknown error occured");
			} else {
			    q.startInstance(cm.getParty(), cm.getMap());
			}
		    } else {
			cm.sendOk("All players must be in map and above level 100.");
		    }
		    } else {
			cm.sendOk("You are not the leader of the party, please ask your leader to talk to me.");
		    }
		} else {
		    cm.sendOk("Please form a party first.");
		}
		break;
	    case 2:
		if (cm.getParty() != null) {
		if (cm.getDisconnected("BossQuestHARD") != null) {
			cm.getDisconnected("BossQuestHARD").registerPlayer(cm.getPlayer());
		 } else if (cm.isLeader()) {
		    var party = cm.getPlayer().getParty().getMembers();
		    var mapId = cm.getPlayer().getMapId();
		    var next = true;
		    var it = party.iterator();
		    while (it.hasNext()) {
			var cPlayer = it.next();
			var ccPlayer = cm.getPlayer().getMap().getCharacterById(cPlayer.getId());
			if (ccPlayer == null || ccPlayer.getLevel() < 120) {
				next = false;
				break;
			}
		    }	
	  	    if (next) {
			var q = cm.getEventManager("BossQuestHARD");
			if (q == null) {
			    cm.sendOk("Unknown error occured");
			} else {
			    q.startInstance(cm.getParty(), cm.getMap());
			}
		    } else {
			cm.sendOk("All players must be in map and above level 120.");
		    }
		    } else {
			cm.sendOk("You are not the leader of the party, please ask your leader to talk to me.");
		    }
		} else {
		    cm.sendOk("Please form a party first.");
		}
		break;
	    case 28:
		if (cm.getParty() != null) {
		if (cm.getDisconnected("BossQuestHELL") != null) {
			cm.getDisconnected("BossQuestHELL").registerPlayer(cm.getPlayer());
		 } else if (cm.isLeader()) {
		    var party = cm.getPlayer().getParty().getMembers();
		    var mapId = cm.getPlayer().getMapId();
		    var next = true;
		    var it = party.iterator();
		    while (it.hasNext()) {
			var cPlayer = it.next();
			var ccPlayer = cm.getPlayer().getMap().getCharacterById(cPlayer.getId());
			if (ccPlayer == null || ccPlayer.getLevel() < 160) {
				next = false;
				break;
			}
		    }	
	  	    if (next) {
			var q = cm.getEventManager("BossQuestHELL");
			if (q == null) {
			    cm.sendOk("Unknown error occured");
			} else {
			    q.startInstance(cm.getParty(), cm.getMap());
			}
		    } else {
			cm.sendOk("All players must be in map and above level 160.");
		    }
		    } else {
			cm.sendOk("You are not the leader of the party, please ask your leader to talk to me.");
		    }
		} else {
		    cm.sendOk("Please form a party first.");
		}
		break;
		case 4:
		cm.warp(923020000,0);
		cm.dispose();
		break;
		case 5:
		cm.warp(923020100,0);
		cm.dispose();
		break;
	    case 3:
		cm.sendOk("#b当前Boss点数为 : " + points);
		break;
	}
    }
    cm.dispose();
}