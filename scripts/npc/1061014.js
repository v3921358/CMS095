/* Mu Young
	Boss Balrog
*/


var status = -1;
var balrogMode; // false = easy, true = hard

function action(mode, type, selection) {
	switch (status) {
		case -1:
			status = 0;
			
			switch (cm.getChannelNumber()) {
				case 2:
					balrogMode = true;
					cm.sendNext("您当前所在的频道可用于 #b普通难度巴尔罗格探险队#k. \r\n如果您想加入其他模式，请选择正确的频道，每日限挑战3次。\n\r #b#i3994116# 频道.2 / 等级50及以上 / 3 ~6 个玩家 \r\n#b#i3994115# 其余的频道 / 等级50 ~ 70 / 3 ~ 6 个玩家.");
					break;
				default:
					balrogMode = false;
					cm.sendNext("您当前所在的频道可用于 #b简单难度巴尔罗格探险队#k. \r\n如果您想加入其他模式，请选择正确的频道，每日限挑战3次。\n\r #b#i3994116# 频道.2 / 等级50及以上 / 3 ~ 6 个玩家 \r\n#b#i3994115# 其余的频道 / 等级50 ~ 70 / 3 ~ 6 个玩家.");
					break;
			}
			break;
		case 0:
			var em = cm.getEventManager(balrogMode ? "BossBalrog_NORMAL" : "BossBalrog_EASY");

			if (em == null) {
				cm.sendOk("目前副本出了一點問題，請聯繫GM！");
				cm.safeDispose();
				return;
			}

			if (cm.getParty() != null) {
				var prop = em.getProperty("state");
				var marr = cm.getQuestRecord(balrogMode ? 160106 : 160105);
				var data = marr.getCustomData();
				if (data == null) {
					marr.setCustomData("0");
					data = "0";
				}
				var time = parseInt(data);
				if (prop == null || prop.equals("0")) {
					var squadAvailability = cm.getSquadAvailability("BossBalrog");
					if (squadAvailability == -1) {
						status = 1;
						if (time + (6 * 3600000) >= cm.getCurrentTime() && !cm.getPlayer().isGM()) {
							cm.sendOk("在过去的6个小时里你已经去讨伐过了巴尔罗格.时间还剩: " + cm.getReadableMillis(cm.getCurrentTime(), time + (6 * 3600000)));
							cm.dispose();
							return;
						}
						cm.sendYesNo("你想成为巴尔罗格探险队的队长吗?");

					} else if (squadAvailability == 1) {
						if (time + (6 * 3600000) >= cm.getCurrentTime() && !cm.getPlayer().isGM()) {
							cm.sendOk("在过去的6个小时里你已经去讨伐过了巴尔罗格.时间还剩: " + cm.getReadableMillis(cm.getCurrentTime(), time + (6 * 3600000)));
							cm.dispose();
							return;
						}
						// -1 = Cancelled, 0 = not, 1 = true
						var type = cm.isSquadLeader("BossBalrog");
						if (type == -1) {
							cm.sendOk("队伍结束了，请重新登记.");
							cm.safeDispose();
						} else if (type == 0) {
							var memberType = cm.isSquadMember("BossBalrog");
							if (memberType == 2) {
								cm.sendOk("你被禁止进入队伍.");
								cm.safeDispose();
							} else if (memberType == 1) {
								status = 5;
								cm.sendSimple("你想做什么？ \r\n#b#L0#检查队员信息#l \r\n#b#L1#加入远征队#l \r\n#b#L2#退出远征队#l");
							} else if (memberType == -1) {
								cm.sendOk("队伍结束了，请重新登记.");
								cm.safeDispose();
							} else {
								status = 5;
								cm.sendSimple("你想做什么？ \r\n#b#L0#检查队员信息#l \r\n#b#L1#加入远征队#l \r\n#b#L2#退出远征队#l");
							}
						} else { // Is leader
							status = 10;
							cm.sendSimple("你想做什么？ \r\n#b#L0#检查队员信息#l \r\n#b#L1#踢出成员#l \r\n#b#L2#编辑限制名单#l \r\n#r#L3#开始远征#l");
							// TODO viewing!
						}
					} else {
						var eim = cm.getDisconnected(balrogMode ? "BossBalrog_NORMAL" : "BossBalrog_EASY");
						if (eim == null) {
							var squd = cm.getSquad("BossBalrog");
							if (squd != null) {
								if (time + (6 * 3600000) >= cm.getCurrentTime() && !cm.getPlayer().isGM()) {
									cm.sendOk("在过去的6个小时里你已经去讨伐过了巴尔罗格.时间还剩: " + cm.getReadableMillis(cm.getCurrentTime(), time + (6 * 3600000)));
									cm.dispose();
									return;
								}
								cm.sendYesNo("已经有小队开始了与BOSS的战斗.\r\n" + squd.getNextPlayer());
								status = 3;
							} else {
								cm.sendOk("已经有小队开始了与BOSS的战斗.");
								cm.safeDispose();
							}
						} else {
							cm.sendYesNo("啊，你回来了。你想再次加入你的队伍吗?");
							status = 2;
						}
					}
				} else {
					var eim = cm.getDisconnected(balrogMode ? "BossBalrog_NORMAL" : "BossBalrog_EASY");
					if (eim == null) {
						var squd = cm.getSquad("BossBalrog");
						if (squd != null) {
							if (time + (6 * 3600000) >= cm.getCurrentTime() && !cm.getPlayer().isGM()) {
								cm.sendOk("在过去的6个小时里你已经去讨伐过了巴尔罗格.时间还剩: " + cm.getReadableMillis(cm.getCurrentTime(), time + (6 * 3600000)));
								cm.dispose();
								return;
							}
							cm.sendYesNo("已经有小队开始了与BOSS的战斗.\r\n" + squd.getNextPlayer());
							status = 3;
						} else {
							cm.sendOk("已经有小队开始了与BOSS的战斗.");
							cm.safeDispose();
						}
					} else {
						cm.sendYesNo("啊，你回来了。你想再次加入你的队伍吗?");
						status = 2;
					}
				}
			} else {
				cm.sendPrev("你需要开设一个组队.");
				cm.safeDispose();
			}
			break;
		case 1:
			if (mode == 1) {
				if (!balrogMode) { // Easy Mode
					var lvl = cm.getPlayerStat("LVL");
					if (lvl >= 50 && lvl <= 70 ||cm.getPlayer().isGM()) {
						if (cm.registerSquad("BossBalrog", 5, " 被任命为蝙蝠怪远征队队长。如果你想加入，请在规定时间内报名参加探险队.")) {
							cm.sendOk("你被任命为远征队队长。在接下来的5分钟里，队员可以继续加入远征队.");
						} else {
							cm.sendOk("错误，请再来一次.");
						}
					} else {
						cm.sendNext("队员不在五十级到七十级范围内。请安排好你们的队伍，使每个人都能达到水平限制.");
					}
				} else { // Normal Mode
					if (cm.registerSquad("BossBalrog", 5, " 被任命为蝙蝠怪远征队队长。如果你想加入，请在规定时间内报名参加探险队.")) {
						cm.sendOk("你被任命为远征队队长。在接下来的5分钟里，队员可以继续加入远征队.");
					} else {
						cm.sendOk("错误，请再来一次.");
					}
				}
			} else {
				cm.sendOk("如果你想成为远征队的队长，跟我谈谈.")
			}
			cm.safeDispose();
			break;
		case 2:
			if (!cm.reAdd(balrogMode ? "BossBalrog_NORMAL" : "BossBalrog_EASY", "BossBalrog")) {
				cm.sendOk("错误，请再来一次.");
			}
			cm.safeDispose();
			break;
		case 3:
			if (mode == 1) {
				var squd = cm.getSquad("BossBalrog");
				if (squd != null && !squd.getAllNextPlayer().contains(cm.getPlayer().getName())) {
					squd.setNextPlayer(cm.getPlayer().getName());
					cm.sendOk("你已经在远征队里了.");
				}
			}
			cm.dispose();
			break;
		case 5:
			if (selection == 0) {
				if (!cm.getSquadList("BossBalrog", 0)) {
					cm.sendOk("由于一个未知的错误，对小队的请求被拒绝.");
					cm.safeDispose();
				} else {
					cm.dispose();
				}
			} else if (selection == 1) { // join
				var ba = cm.addMember("BossBalrog", true);
				if (ba == 2) {
					cm.sendOk("队伍已满，请稍后再试.");
					cm.safeDispose();
				} else if (ba == 1) {
					cm.sendOk("你已经成功地加入了队伍");
					cm.safeDispose();
				} else {
					cm.sendOk("你已经在远征队里了.");
					cm.safeDispose();
				}
			} else { // withdraw
				var baa = cm.addMember("BossBalrog", false);
				if (baa == 1) {
					cm.sendOk("你已经成功地退出了队伍");
					cm.safeDispose();
				} else {
					cm.sendOk("你不在远征队中.");
					cm.safeDispose();
				}
			}
			break;
		case 10:
			if (selection == 0) {
				if (!cm.getSquadList("BossBalrog", 0)) {
					cm.sendOk("由于一个未知的错误，对小队的请求被拒绝.");
				}
				cm.safeDispose();
			} else if (selection == 1) {
				status = 11;
				if (!cm.getSquadList("BossBalrog", 1)) {
					cm.sendOk("由于一个未知的错误，对小队的请求被拒绝.");
				}
				cm.safeDispose();
			} else if (selection == 2) {
				status = 12;
				if (!cm.getSquadList("BossBalrog", 2)) {
					cm.sendOk("由于一个未知的错误，对小队的请求被拒绝.");
				}
				cm.safeDispose();
			} else if (selection == 3) { // get insode
				if (cm.getSquad("BossBalrog") != null) {
					var dd = cm.getEventManager(balrogMode ? "BossBalrog_NORMAL" : "BossBalrog_EASY");
					dd.startInstance(cm.getSquad("BossBalrog"), cm.getMap(), balrogMode ? 160106 : 160105);
					cm.dispose();
				} else {
					cm.sendOk("由于一个未知的错误，对小队的请求被拒绝.");
					cm.safeDispose();
				}
			}
			break;
		case 11:
			cm.banMember("BossBalrog", selection);
			cm.dispose();
			break;
		case 12:
			if (selection != -1) {
				cm.acceptMember("BossBalrog", selection);
			}
			cm.dispose();
			break;
	}
}