var status = -1;

function start() {
	cm.removeAll(4001256);
	cm.removeAll(4001257);
	cm.removeAll(4001258);
	cm.removeAll(4001259);
	cm.removeAll(4001260);
		if (cm.getPlayer().getLevel() < 90) {
			cm.sendOk("There is a level requirement of 90 to attempt Crimsonwood Keep.");
			cm.dispose();
			return;
		}
		if (cm.getPlayer().getClient().getChannel() != 1 && cm.getPlayer().getClient().getChannel() != 2 && cm.getPlayer().getClient().getChannel() != 3 && cm.getPlayer().getClient().getChannel() != 4) {
			cm.sendOk("Crimsonwood Keep may only be attempted on channel 1,2,3,4.");
			cm.dispose();
			return;
		}
    var em = cm.getEventManager("CWKPQ");

    if (em == null) {
	cm.sendOk("事件不存在，请联系GM.");
	cm.dispose();
	return;
    }
    var prop = em.getProperty("state");

    if (prop == null || prop.equals("0")) {
	var squadAvailability = cm.getSquadAvailability("CWKPQ");
	if (squadAvailability == -1) {
	    status = 0;
	    cm.sendYesNo("你有兴趣成为探险队的队长吗?");

	} else if (squadAvailability == 1) {
	    // -1 = Cancelled, 0 = not, 1 = true
	    var type = cm.isSquadLeader("CWKPQ");
	    if (type == -1) {
		cm.sendOk("远征队结束，请重新报名.");
		cm.dispose();
	    } else if (type == 0) {
		var memberType = cm.isSquadMember("CWKPQ");
		if (memberType == 2) {
		    cm.sendOk("你被禁止加入了。");
		    cm.dispose();
		} else if (memberType == 1) {
		    status = 5;
		    cm.sendSimple("你要做什么? \r\n#b#L0#查看成员#l \r\n#b#L1#加入远征队#l \r\n#b#L2#退出远征队#l \r\n#b#L3#Check out jobs#l");
		} else if (memberType == -1) {
		    cm.sendOk("远征队结束，请重新报名.");
		    cm.dispose();
		} else {
		    status = 5;
		    cm.sendSimple("你要做什么? \r\n#b#L0#查看成员#l \r\n#b#L1#加入远征队#l \r\n#b#L2#退出远征队#l \r\n#b#L3#Check out jobs#l");
		}
	    } else { // Is leader
		status = 10;
		cm.sendSimple("你现在想做什么? \r\n#b#L0#查看成员#l \r\n#b#L1#踢出成员#l \r\n#b#L2#编辑成员#l \r\n#b#L3#Check out jobs#l \r\n#r#L4#进入远征地图#l");
	    // TODO viewing!
	    }
	} else {
			var eim = cm.getDisconnected("CWKPQ");
			if (eim == null) {
				var squd = cm.getSquad("CWKPQ");
				if (squd != null) {
					if (squd.getNextPlayer() != null) {
						cm.sendOk("战斗已经开始了. The player to reserve the next spot is " + squd.getNextPlayer());
						cm.safeDispose();
					} else {
						cm.sendYesNo("战斗已经开始了. Would you like to queue the next spot?");
						status = 3;
					}
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
			var eim = cm.getDisconnected("CWKPQ");
			if (eim == null) {
				var squd = cm.getSquad("CWKPQ");
				if (squd != null) {
					if (squd.getNextPlayer() != null) {
						cm.sendOk("战斗已经开始了. The player to reserve the next spot is " + squd.getNextPlayer());
						cm.safeDispose();
					} else {
						cm.sendYesNo("战斗已经开始了. Would you like to queue the next spot?");
						status = 3;
					}
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
			if (!cm.haveItem(4032012, 10)) {
				cm.sendOk("You need 10 Crimson Heart to apply.");
			} else if (cm.registerSquad("CWKPQ", 5, " 他被任命为队长。如果你想加入，请在此期间内登记加入探险队.")) {
				cm.sendOk("你被任命为小队的队长。在接下来的5分钟里，你可以加入探险队的成员.");
			} else {
				cm.sendOk("加入你的队伍时出现了一个错误.");
			}
	    	}
	    cm.dispose();
	    break;
	case 1:
		if (!cm.reAdd("CWKPQ", "CWKPQ")) {
			cm.sendOk("出错了，请重试.");
		}
		cm.safeDispose();
		break;
	case 3:
		if (mode == 1) {
			var squd = cm.getSquad("CWKPQ");
			if (squd != null && squd.getNextPlayer() == null) {
				squd.setNextPlayer(cm.getPlayer().getName());
				cm.sendOk("你已经加入远征队了.");
			}
		}
		cm.dispose();
		break;
	case 5:
	    if (selection == 0 || selection == 3) {
		if (!cm.getSquadList("CWKPQ", selection)) {
		    cm.sendOk("由于一个未知的错误，远征队的申请被拒绝了.");
		}
	    } else if (selection == 1) { // join
		var ba = cm.addMember("CWKPQ", true);
		if (ba == 2) {
		    cm.sendOk("队伍目前已满，请稍后再试.");
		} else if (ba == 1) {
		    cm.sendOk("成功加入远征队");
		} else {
		    cm.sendOk("你已经是队里的一员了.");
		}
	    } else {// withdraw
		var baa = cm.addMember("CWKPQ", false);
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
		if (selection == 0 || selection == 3) {
		    if (!cm.getSquadList("CWKPQ", selection)) {
			cm.sendOk("由于一个未知的错误，远征队的申请被拒绝了.");
		    }
		    cm.dispose();
		} else if (selection == 1) {
		    status = 11;
		    if (!cm.getSquadList("CWKPQ", 1)) {
			cm.sendOk("由于一个未知的错误，远征队的申请被拒绝了.");
			cm.dispose();
		    }
		} else if (selection == 2) {
		    status = 12;
		    if (!cm.getSquadList("CWKPQ", 2)) {
			cm.sendOk("由于一个未知的错误，远征队的申请被拒绝了.");
			cm.dispose();
		    }
		} else if (selection == 4) { // get insode
		    if (cm.getSquad("CWKPQ") != null) {
			if (cm.haveItem(4032012, 10)) {
			    cm.gainItem(4032012, -10);
			    var dd = cm.getEventManager("CWKPQ");
			    dd.startInstance(cm.getSquad("CWKPQ"), cm.getMap());
			} else {
		 	    cm.sendOk("Where is my 10 Crimson Heart?");
			}
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
	    cm.banMember("CWKPQ", selection);
	    cm.dispose();
	    break;
	case 12:
	    if (selection != -1) {
		cm.acceptMember("CWKPQ", selection);
	    }
	    cm.dispose();
	    break;
	default:
	    cm.dispose();
	    break;
    }
}