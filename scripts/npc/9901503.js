var status = -1;

function start() {
		if (cm.getPlayer().getLevel() < 120) {
			cm.sendOk("There is a level requirement of 120 to attempt Chaos Horntail.");
			cm.dispose();
			return;
		}
		if (cm.getPlayer().getClient().getChannel() != 8) {
			cm.sendOk("Chaos Horntail may only be attempted on channel 8");
			cm.dispose();
			return;
		}
    var em = cm.getEventManager("ChaosHorntail");

    if (em == null) {
	cm.sendOk("事件不存在，请联系GM.");
	cm.dispose();
	return;
    }
    var prop = em.getProperty("state");
	    var marr = cm.getQuestRecord(160103);
	    var data = marr.getCustomData();
	    if (data == null) {
		marr.setCustomData("0");
	        data = "0";
	    }
	    var time = parseInt(data);
    if (prop == null || prop.equals("0")) {

	var squadAvailability = cm.getSquadAvailability("ChaosHT");
	if (squadAvailability == -1) {
	    status = 0;
	    if (time + (12 * 3600000) >= cm.getCurrentTime() && !cm.getPlayer().isGM()) {
		cm.sendOk("You have already went to Chaos Horntail in the past 12 hours. Time left: " + cm.getReadableMillis(cm.getCurrentTime(), time + (12 * 3600000)));
		cm.dispose();
		return;
	    }
	    cm.sendYesNo("你有兴趣成为探险队的队长吗?");

	} else if (squadAvailability == 1) {
	    if (time + (12 * 3600000) >= cm.getCurrentTime() && !cm.getPlayer().isGM()) {
		cm.sendOk("You have already went to Chaos Horntail in the past 12 hours. Time left: " + cm.getReadableMillis(cm.getCurrentTime(), time + (12 * 3600000)));
		cm.dispose();
		return;
	    }
	    // -1 = Cancelled, 0 = not, 1 = true
	    var type = cm.isSquadLeader("ChaosHT");
	    if (type == -1) {
		cm.sendOk("远征队结束，请重新报名.");
		cm.dispose();
	    } else if (type == 0) {
		var memberType = cm.isSquadMember("ChaosHT");
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
		cm.sendSimple("你现在想做什么? \r\n#b#L0#查看成员#l \r\n#b#L1#踢出成员#l \r\n#b#L2#编辑成员#l \r\n#r#L3#进入远征地图#l");
	    // TODO viewing!
	    }
	} else {
			var eim = cm.getDisconnected("ChaosHorntail");
			if (eim == null) {
				var squd = cm.getSquad("ChaosHT");
				if (squd != null) {
	    if (time + (12 * 3600000) >= cm.getCurrentTime() && !cm.getPlayer().isGM()) {
		cm.sendOk("You have already went to Chaos Horntail in the past 12 hours. Time left: " + cm.getReadableMillis(cm.getCurrentTime(), time + (12 * 3600000)));
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
				status = 1;
			}
	}
    } else {
			var eim = cm.getDisconnected("ChaosHorntail");
			if (eim == null) {
				var squd = cm.getSquad("ChaosHT");
				if (squd != null) {
	    if (time + (12 * 3600000) >= cm.getCurrentTime() && !cm.getPlayer().isGM()) {
		cm.sendOk("You have already went to Chaos Horntail in the past 12 hours. Time left: " + cm.getReadableMillis(cm.getCurrentTime(), time + (12 * 3600000)));
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
				status = 1;
			}
    }
}

function action(mode, type, selection) {
    switch (status) {
	case 0:
	    	if (mode == 1) {
			if (cm.registerSquad("ChaosHT", 5, " has been named the Leader of the squad (Chaos). If you would you like to join please register for the Expedition Squad within the time period.")) {
				cm.sendOk("你被任命为小队的队长。在接下来的5分钟里，你可以加入探险队的成员.");
			} else {
				cm.sendOk("加入你的队伍时出现了一个错误.");
			}
	    	}
	    cm.dispose();
	    break;
	case 1:
		if (!cm.reAdd("ChaosHorntail", "ChaosHT")) {
			cm.sendOk("出错了，请重试.");
		}
		cm.safeDispose();
		break;
	case 3:
		if (mode == 1) {
			var squd = cm.getSquad("ChaosHT");
			if (squd != null && !squd.getAllNextPlayer().contains(cm.getPlayer().getName())) {
				squd.setNextPlayer(cm.getPlayer().getName());
				cm.sendOk("你已经加入远征队了.");
			}
		}
		cm.dispose();
		break;
	case 5:
	    if (selection == 0) {
		if (!cm.getSquadList("ChaosHT", 0)) {
		    cm.sendOk("由于一个未知的错误，远征队的申请被拒绝了.");
		}
	    } else if (selection == 1) { // join
		var ba = cm.addMember("ChaosHT", true);
		if (ba == 2) {
		    cm.sendOk("队伍目前已满，请稍后再试.");
		} else if (ba == 1) {
		    cm.sendOk("成功加入远征队");
		} else {
		    cm.sendOk("你已经是队里的一员了.");
		}
	    } else {// withdraw
		var baa = cm.addMember("ChaosHT", false);
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
		    if (!cm.getSquadList("ChaosHT", 0)) {
			cm.sendOk("由于一个未知的错误，远征队的申请被拒绝了.");
		    }
		    cm.dispose();
		} else if (selection == 1) {
		    status = 11;
		    if (!cm.getSquadList("ChaosHT", 1)) {
			cm.sendOk("由于一个未知的错误，远征队的申请被拒绝了.");
			cm.dispose();
		    }
		} else if (selection == 2) {
		    status = 12;
		    if (!cm.getSquadList("ChaosHT", 2)) {
			cm.sendOk("由于一个未知的错误，远征队的申请被拒绝了.");
			cm.dispose();
		    }
		} else if (selection == 3) { // get insode
		    if (cm.getSquad("ChaosHT") != null) {
			var dd = cm.getEventManager("ChaosHorntail");
			dd.startInstance(cm.getSquad("ChaosHT"), cm.getMap(), 160103);
		    } else {
			cm.sendOk("由于一个未知的错误，远征队的申请被拒绝了.");
		    }
		    cm.dispose();
		}
	    } else {
		cm.dispose();
	    }
	    break;
	case 11:
	    cm.banMember("ChaosHT", selection);
	    cm.dispose();
	    break;
	case 12:
	    if (selection != -1) {
		cm.acceptMember("ChaosHT", selection);
	    }
	    cm.dispose();
	    break;
	default:
	    cm.dispose();
	    break;
    }
}