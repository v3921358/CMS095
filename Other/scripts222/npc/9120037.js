/*
	NPC Name: 		Commander Garuda
	Map(s): 		Neo Tokyo 2102 : Akihabara
	Description: 		Dunas Battle starter
*/
var status = -1;

function start() {
    if (cm.getMapId() == 802000410) {
		if (cm.getPlayer().getClient().getChannel() != 1) {
			cm.sendOk("只能在频道1挑战boss..");
			cm.dispose();
			return;
		}
	var em = cm.getEventManager("Dunas");

	if (em == null) {
	    cm.sendOk("事件不存在，请联系GM.");
	    cm.dispose();
	    return;
	}
	//	var prop = em.getProperty("DunasSummoned");

	//	if (((prop.equals("PQCleared") || (prop.equals("1")) && cm.getPlayerCount(802000211) == 0)) || prop.equals("0") || prop == null) {
	var prop = em.getProperty("state");
	if (prop == null || prop.equals("0")) {
	var squadAvailability = cm.getSquadAvailability("Dunas");
	if (squadAvailability == -1) {
	    status = 0;
	    cm.sendYesNo("你有兴趣成为探险队的队长吗?");

	} else if (squadAvailability == 1) {
	    // -1 = Cancelled, 0 = not, 1 = true
	    var type = cm.isSquadLeader("Dunas");
	    if (type == -1) {
		cm.sendOk("远征队结束，请重新报名.");
		cm.dispose();
	    } else if (type == 0) {
		var memberType = cm.isSquadMember("Dunas");
		if (memberType == 2) {
		    cm.sendOk("你被禁止加入了。");
		    cm.dispose();
		} else if (memberType == 1) {
		    status = 5;
		    cm.sendSimple("你要做什么? \r\n#b#L0#查看成员#l \r\n#b#L1#加入远征队#l \r\n#b#L2#退出远征队#l");
		} else if (memberType == -1) {
		    cm.sendOk("远征队结束，请重新报名.");
		    cm.dispose();
		} else {
		    status = 5;
		    cm.sendSimple("你要做什么? \r\n#b#L0#查看成员#l \r\n#b#L1#加入远征队#l \r\n#b#L2#退出远征队#l");
		}
	    } else { // Is leader
		status = 10;
		cm.sendSimple("What do you want to do? \r\n#b#L0#查看成员#l \r\n#b#L1#踢出成员#l \r\n#b#L2#编辑成员#l \r\n#r#L3#进入远征地图#l");
	    // TODO viewing!
	    }
	    } else {
			var eim = cm.getDisconnected("Dunas");
			if (eim == null) {
				var squd = cm.getSquad("Dunas");
				if (squd != null) {
					cm.sendYesNo("战斗已经开始了.\r\n" + squd.getNextPlayer());
					status = 3;
				} else {
					cm.sendOk("战斗已经开始了.");
					cm.safeDispose();
				}
			} else {
				cm.sendYesNo("啊，你回来了。你想再次加入你的队伍吗?");
				status = 2;
			}
	    }
	} else {
			var eim = cm.getDisconnected("Dunas");
			if (eim == null) {
				var squd = cm.getSquad("Dunas");
				if (squd != null) {
					cm.sendYesNo("战斗已经开始了.\r\n" + squd.getNextPlayer());
					status = 3;
				} else {
					cm.sendOk("战斗已经开始了.");
					cm.safeDispose();
				}
			} else {
				cm.sendYesNo("啊，你回来了。你想再次加入你的队伍吗?");
				status = 2;
			}
	}
    } else {
	status = 25;
	cm.sendNext("Do you want to get out now?");
    }
}

function action(mode, type, selection) {
    switch (status) {
	case 0:
	    if (mode == 1) {
			if (cm.registerSquad("Dunas", 5, " 他被任命为队长。如果你想加入，请在此期间内登记加入探险队.")) {
				cm.sendOk("你被任命为小队的队长。在接下来的5分钟里，你可以加入探险队的成员.");
			} else {
				cm.sendOk("加入你的队伍时出现了一个错误.");
			}
	    }
	    cm.dispose();
	    break;
	case 2:
		if (!cm.reAdd("Dunas", "Dunas")) {
			cm.sendOk("出错了，请重试.");
		}
		cm.safeDispose();
		break;
	case 3:
		if (mode == 1) {
			var squd = cm.getSquad("Dunas");
			if (squd != null && !squd.getAllNextPlayer().contains(cm.getPlayer().getName())) {
				squd.setNextPlayer(cm.getPlayer().getName());
				cm.sendOk("你已经加入远征队了.");
			}
		}
		cm.dispose();
		break;
	case 5:
	    if (selection == 0) {
		if (!cm.getSquadList("Dunas", 0)) {
		    cm.sendOk("Due to an unknown error, the request for squad has been denied.");
		}
	    } else if (selection == 1) { // join
		var ba = cm.addMember("Dunas", true);
		if (ba == 2) {
		    cm.sendOk("队伍目前已满，请稍后再试.");
		} else if (ba == 1) {
		    cm.sendOk("成功加入远征队");
		} else {
		    cm.sendOk("你已经是队里的一员了.");
		}
	    } else {// withdraw
		var baa = cm.addMember("Dunas", false);
		if (baa == 1) {
		    cm.sendOk("你已经成功地退出了");
		} else {
		    cm.sendOk("你不是队伍的一员.");
		}
	    }
	    cm.dispose();
	    break;
	case 10:
	    if (mode == 1) {
		if (selection == 0) {
		    if (!cm.getSquadList("Dunas", 0)) {
			cm.sendOk("Due to an unknown error, the request for squad has been denied.");
		    }
		    cm.dispose();
		} else if (selection == 1) {
		    status = 11;
		    if (!cm.getSquadList("Dunas", 1)) {
			cm.sendOk("Due to an unknown error, the request for squad has been denied.");
			cm.dispose();
		    }
		} else if (selection == 2) {
		    status = 12;
		    if (!cm.getSquadList("Dunas", 2)) {
			cm.sendOk("Due to an unknown error, the request for squad has been denied.");
			cm.dispose();
		    }
		} else if (selection == 3) { // get insode
		    if (cm.getSquad("Dunas") != null) {
			var dd = cm.getEventManager("Dunas");
			dd.startInstance(cm.getSquad("Dunas"), cm.getMap());
		    } else {
			cm.sendOk("Due to an unknown error, the request for squad has been denied.");
		    }
		    cm.dispose();
		}
	    } else {
		cm.dispose();
	    }
	    break;
	case 11:
	    cm.banMember("Dunas", selection);
	    cm.dispose();
	    break;
	case 12:
	    if (selection != -1) {
		cm.acceptMember("Dunas", selection);
	    }
	    cm.dispose();
	    break;
	case 25:
	    cm.warp(802000410, 0);
	    cm.dispose();
	    break;
    }
}