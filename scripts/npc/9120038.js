/*
	NPC Name: 		Dida
	Map(s): 		2095 Park
	Description: 		Battle starter
 */
var status = -1;

function start() {
    if (cm.getMapId() == 802000310) {
		if (cm.getPlayer().getClient().getChannel() != 1) {
			cm.sendOk("只能在频道1挑战boss..");
			cm.dispose();
			return;
		}
	var em = cm.getEventManager("2095_tokyo");

	if (em == null) {
	    cm.sendOk("事件不存在，请联系GM.");
	    cm.dispose();
	    return;
	}
	var prop = em.getProperty("state");
	if (prop == null || prop.equals("0")) {
	var squadAvailability = cm.getSquadAvailability("tokyo_2095");
	if (squadAvailability == -1) {
	    status = 0;
	    cm.sendYesNo("你有兴趣成为探险队的队长吗?");

	} else if (squadAvailability == 1) {
	    // -1 = Cancelled, 0 = not, 1 = true
	    var type = cm.isSquadLeader("tokyo_2095");
	    if (type == -1) {
		cm.sendOk("远征队结束，请重新报名.");
		cm.dispose();
	    } else if (type == 0) {
		var memberType = cm.isSquadMember("tokyo_2095");
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
		cm.sendSimple("你的任务是获得10个能量发射器.. \r\n#b#L0#查看成员#l \r\n#b#L1#踢出成员#l \r\n#b#L2#编辑成员#l \r\n#r#L3#进入远征地图#l \r\n#b#L4#我需要简化电脉冲发射器#l");
	    }
    } else {
			var eim = cm.getDisconnected("2095_tokyo");
			if (eim == null) {
				var squd = cm.getSquad("tokyo_2095");
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
			var eim = cm.getDisconnected("2095_tokyo");
			if (eim == null) {
				var squd = cm.getSquad("tokyo_2095");
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
	cm.sendNext("你想现在出去吗?");
    }
}

function action(mode, type, selection) {
    switch (status) {
	case 0:
	    if (mode == 1) {
			if (cm.registerSquad("tokyo_2095", 5, " 他被任命为队长。如果你想加入，请在此期间内登记加入探险队.")) {
				cm.sendOk("你被任命为小队的队长。在接下来的5分钟里，你可以加入探险队的成员.");
			} else {
				cm.sendOk("加入你的队伍时出现了一个错误.");
			}
	    }
	    cm.dispose();
	    break;
	case 2:
		if (!cm.reAdd("2095_tokyo", "tokyo_2095")) {
			cm.sendOk("出错了，请重试.");
		}
		cm.safeDispose();
		break;
	case 3:
		if (mode == 1) {
			var squd = cm.getSquad("tokyo_2095");
			if (squd != null && !squd.getAllNextPlayer().contains(cm.getPlayer().getName())) {
				squd.setNextPlayer(cm.getPlayer().getName());
				cm.sendOk("你已经加入远征队了.");
			}
		}
		cm.dispose();
		break;
	case 5:
	    if (selection == 0) {
		if (!cm.getSquadList("tokyo_2095", 0)) {
		    cm.sendOk("由于一个未知的错误，球队的申请被拒绝了。");
		}
	    } else if (selection == 1) { // join
		var ba = cm.addMember("tokyo_2095", true);
		if (ba == 2) {
		    cm.sendOk("队伍目前已满，请稍后再试.");
		} else if (ba == 1) {
		    cm.sendOk("成功加入远征队");
		} else {
		    cm.sendOk("你已经是队里的一员了.");
		}
	    } else {// withdraw
		var baa = cm.addMember("tokyo_2095", false);
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
		    if (!cm.getSquadList("tokyo_2095", 0)) {
			cm.sendOk("由于一个未知的错误，球队的申请被拒绝了。");
		    }
		    cm.dispose();
		} else if (selection == 1) {
		    status = 11;
		    if (!cm.getSquadList("tokyo_2095", 1)) {
			cm.sendOk("由于一个未知的错误，球队的申请被拒绝了。");
			cm.dispose();
		    }
		} else if (selection == 2) {
		    status = 12;
		    if (!cm.getSquadList("tokyo_2095", 2)) {
			cm.sendOk("由于一个未知的错误，球队的申请被拒绝了。");
			cm.dispose();
		    }
		} else if (selection == 3) { // get insode
		    status = 13;
		    cm.sendNext("#b#t4032202##k, 别忘了把它放在马尔面前.")
		} else if (selection == 4) { // Transmitter
		    status = 17;
		    cm.sendNext("拿着这个，这是爸爸送的一件很重要的东西，但这应该足够把那个机器人送走了。你能把它放在玛尔面前吗?");
		}
	    } else {
		cm.dispose();
	    }
	    break;
	case 11:
	    cm.banMember("tokyo_2095", selection);
	    cm.dispose();
	    break;
	case 12:
	    if (selection != -1) {
		cm.acceptMember("tokyo_2095", selection);
	    }
	    cm.dispose();
	    break;
	case 13:
	    status = 14;
	    cm.sendNextPrev("你面对的对手正在使用 #b#t4032192##k 作为驱动力. 你需要消灭敌人并将他们聚集起来，然后立即将他们送到马尔，这样她就可以逃跑了.");
	    break;
	case 14:
	    status = 15;
	    cm.sendNextPrev("这些应该不会超过20分钟。我建议你在20分钟内逃走!");
	    break;
	case 15:
	    status = 16;
	    cm.sendNextPrev(" #b#t4032192##k 你收集的这东西应该由队长交给马尔!");
	    break;
	case 16:
	    if (cm.getSquad("tokyo_2095") != null) {
		var dd = cm.getEventManager("2095_tokyo");
		dd.startInstance(cm.getSquad("tokyo_2095"), cm.getMap());
	    } else {
		cm.sendOk("由于一个未知的错误，球队的申请被拒绝了。");
	    }
	    cm.dispose();
	    break;
	case 17:
	    cm.gainItem(4032202, 1);
	    cm.sendNextPrev("小心些而已。请在6分钟内将物品送到marr面前，否则任务失败.");
	    cm.dispose();
	    break;
	case 25:
	    cm.warp(802000210, 0);
	    cm.dispose();
	    break;
	default:
	    cm.dispose();
	    break;
    }
}