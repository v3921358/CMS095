var status = -1;

function start() {
	if (cm.getPlayer().getMapId() == 211070100 || cm.getPlayer().getMapId() == 211070101 || cm.getPlayer().getMapId() == 211070110) {
		cm.sendYesNo("Would you like to get out?");
		status = 1;
		return;
	}
		if (cm.getPlayer().getLevel() < 120) {
			cm.sendOk("There is a level requirement of 120 to attempt Von Leon.");
			cm.dispose();
			return;
		}
		if (cm.getPlayer().getClient().getChannel() != 1 && cm.getPlayer().getClient().getChannel() != 2 && cm.getPlayer().getClient().getChannel() != 3 && cm.getPlayer().getClient().getChannel() != 4 && cm.getPlayer().getClient().getChannel() != 5 && cm.getPlayer().getClient().getChannel() != 6 && cm.getPlayer().getClient().getChannel() != 7 && cm.getPlayer().getClient().getChannel() != 8 && cm.getPlayer().getClient().getChannel() != 9 && cm.getPlayer().getClient().getChannel() != 10 && cm.getPlayer().getClient().getChannel() != 11 && cm.getPlayer().getClient().getChannel() != 12 && cm.getPlayer().getClient().getChannel() != 13 && cm.getPlayer().getClient().getChannel() != 14 && cm.getPlayer().getClient().getChannel() != 15) {
			cm.sendOk("Von Leon may only be attempted on every channel.");
			cm.dispose();
			return;
		}
    var em = cm.getEventManager("VonLeonBattle");

    if (em == null) {
	cm.sendOk("事件不存在，请联系GM.");
	cm.dispose();
	return;
    }
    var eim_status = em.getProperty("state");
	    var marr = cm.getQuestRecord(160107);
	    var data = marr.getCustomData();
	    if (data == null) {
		marr.setCustomData("0");
	        data = "0";
	    }
	    var time = parseInt(data);
	if (eim_status == null || eim_status.equals("0")) {
    var squadAvailability = cm.getSquadAvailability("VonLeon");
    if (squadAvailability == -1) {
	status = 0;
	    if (time + (12 * 3600000) >= cm.getCurrentTime() && !cm.getPlayer().isGM()) {
		cm.sendOk("You have already went to VonLeon in the past 12 hours. Time left: " + cm.getReadableMillis(cm.getCurrentTime(), time + (12 * 3600000)));
		cm.dispose();
		return;
	    }
	cm.sendYesNo("Are you interested in becoming the leader of the VonLeon expedition Squad?");

    } else if (squadAvailability == 1) {
	    if (time + (12 * 3600000) >= cm.getCurrentTime() && !cm.getPlayer().isGM()) {
		cm.sendOk("You have already went to VonLeon in the past 12 hours. Time left: " + cm.getReadableMillis(cm.getCurrentTime(), time + (12 * 3600000)));
		cm.dispose();
		return;
	    }
	// -1 = Cancelled, 0 = not, 1 = true
	var type = cm.isSquadLeader("VonLeon");
	if (type == -1) {
	    cm.sendOk("远征队结束，请重新报名.");
	    cm.dispose();
	} else if (type == 0) {
	    var memberType = cm.isSquadMember("VonLeon");
	    if (memberType == 2) {
		cm.sendOk("你被禁止加入了。");
		cm.dispose();
	    } else if (memberType == 1) {
		status = 5;
		cm.sendSimple("What would you like to do? \r\n#b#L0#Join the squad#l \r\n#b#L1#Leave the squad#l \r\n#b#L2#See the list of members on the squad#l");
	    } else if (memberType == -1) {
		cm.sendOk("远征队结束，请重新报名.");
		cm.dispose();
	    } else {
		status = 5;
		cm.sendSimple("What would you like to do? \r\n#b#L0#Join the squad#l \r\n#b#L1#Leave the squad#l \r\n#b#L2#See the list of members on the squad#l");
	    }
	} else { // Is leader
	    status = 10;
	    cm.sendSimple("What do you want to do, expedition leader? \r\n#b#L0#View expedition list#l \r\n#b#L1#Kick from expedition#l \r\n#b#L2#Remove user from ban list#l \r\n#r#L3#Select expedition team and enter#l");
	// TODO viewing!
	}
	    } else {
			var eim = cm.getDisconnected("VonLeonBattle");
			if (eim == null) {
				var squd = cm.getSquad("VonLeon");
				if (squd != null) {
	    if (time + (12 * 3600000) >= cm.getCurrentTime() && !cm.getPlayer().isGM()) {
		cm.sendOk("You have already went to VonLeon in the past 12 hours. Time left: " + cm.getReadableMillis(cm.getCurrentTime(), time + (12 * 3600000)));
		cm.dispose();
		return;
	    }
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
			var eim = cm.getDisconnected("VonLeonBattle");
			if (eim == null) {
				var squd = cm.getSquad("VonLeon");
				if (squd != null) {
	    if (time + (12 * 3600000) >= cm.getCurrentTime() && !cm.getPlayer().isGM()) {
		cm.sendOk("You have already went to VonLeon in the past 12 hours. Time left: " + cm.getReadableMillis(cm.getCurrentTime(), time + (12 * 3600000)));
		cm.dispose();
		return;
	    }
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
}

function action(mode, type, selection) {
    switch (status) {
	case 0:
	    if (mode == 1) {
			if (cm.registerSquad("VonLeon", 5, " 他被任命为队长。如果你想加入，请在此期间内登记加入探险队.")) {
				cm.sendOk("你被任命为小队的队长。在接下来的5分钟里，你可以加入探险队的成员.");
			} else {
				cm.sendOk("加入你的队伍时出现了一个错误.");
			}
	    }
	    cm.dispose();
	    break;
	case 1:
	    if (mode == 1) {
		cm.warp(211061001, 0);
	    }
	    cm.dispose();
	    break;
	case 2:
		if (!cm.reAdd("VonLeonBattle", "VonLeon")) {
			cm.sendOk("出错了，请重试.");
		}
		cm.safeDispose();
		break;
	case 3:
		if (mode == 1) {
			var squd = cm.getSquad("VonLeon");
			if (squd != null && !squd.getAllNextPlayer().contains(cm.getPlayer().getName())) {
				squd.setNextPlayer(cm.getPlayer().getName());
				cm.sendOk("你已经加入远征队了.");
			}
		}
		cm.dispose();
		break;
	case 5:
	    if (selection == 0) { // join
		var ba = cm.addMember("VonLeon", true);
		if (ba == 2) {
		    cm.sendOk("队伍目前已满，请稍后再试.");
		} else if (ba == 1) {
		    cm.sendOk("成功加入远征队");
		} else {
		    cm.sendOk("你已经是队里的一员了.");
		}
	    } else if (selection == 1) {// withdraw
		var baa = cm.addMember("VonLeon", false);
		if (baa == 1) {
		    cm.sendOk("你已经成功地退出了");
		} else {
		    cm.sendOk("你不是队伍的一员.");
		}
	    } else if (selection == 2) {
		if (!cm.getSquadList("VonLeon", 0)) {
		    cm.sendOk("Due to an unknown error, the request for squad has been denied.");
		}
	    }
	    cm.dispose();
	    break;
	case 10:
	    if (mode == 1) {
		if (selection == 0) {
		    if (!cm.getSquadList("VonLeon", 0)) {
			cm.sendOk("Due to an unknown error, the request for squad has been denied.");
		    }
		    cm.dispose();
		} else if (selection == 1) {
		    status = 11;
		    if (!cm.getSquadList("VonLeon", 1)) {
			cm.sendOk("Due to an unknown error, the request for squad has been denied.");
			cm.dispose();
		    }
		} else if (selection == 2) {
		    status = 12;
		    if (!cm.getSquadList("VonLeon", 2)) {
			cm.sendOk("Due to an unknown error, the request for squad has been denied.");
			cm.dispose();
		    }
		} else if (selection == 3) { // get insode
		    if (cm.getSquad("VonLeon") != null) {
			var dd = cm.getEventManager("VonLeonBattle");
			dd.startInstance(cm.getSquad("VonLeon"), cm.getMap(), 160107);
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
	    cm.banMember("VonLeon", selection);
	    cm.dispose();
	    break;
	case 12:
	    if (selection != -1) {
		cm.acceptMember("VonLeon", selection);
	    }
	    cm.dispose();
	    break;
    }
}