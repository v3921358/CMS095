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
					cm.sendNext("����ǰ���ڵ�Ƶ�������� #b��ͨ�ѶȰͶ��޸�̽�ն�#k. \r\n��������������ģʽ����ѡ����ȷ��Ƶ����ÿ������ս3�Ρ�\n\r #b#i3994116# Ƶ��.2 / �ȼ�50������ / 3 ~6 ����� \r\n#b#i3994115# �����Ƶ�� / �ȼ�50 ~ 70 / 3 ~ 6 �����.");
					break;
				default:
					balrogMode = false;
					cm.sendNext("����ǰ���ڵ�Ƶ�������� #b���ѶȰͶ��޸�̽�ն�#k. \r\n��������������ģʽ����ѡ����ȷ��Ƶ����ÿ������ս3�Ρ�\n\r #b#i3994116# Ƶ��.2 / �ȼ�50������ / 3 ~ 6 ����� \r\n#b#i3994115# �����Ƶ�� / �ȼ�50 ~ 70 / 3 ~ 6 �����.");
					break;
			}
			break;
		case 0:
			var em = cm.getEventManager(balrogMode ? "BossBalrog_NORMAL" : "BossBalrog_EASY");

			if (em == null) {
				cm.sendOk("Ŀǰ��������һ�c���}��Ո�MGM��");
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
							cm.sendOk("�ڹ�ȥ��6��Сʱ�����Ѿ�ȥ�ַ����˰Ͷ��޸�.ʱ�仹ʣ: " + cm.getReadableMillis(cm.getCurrentTime(), time + (6 * 3600000)));
							cm.dispose();
							return;
						}
						cm.sendYesNo("�����Ϊ�Ͷ��޸�̽�նӵĶӳ���?");

					} else if (squadAvailability == 1) {
						if (time + (6 * 3600000) >= cm.getCurrentTime() && !cm.getPlayer().isGM()) {
							cm.sendOk("�ڹ�ȥ��6��Сʱ�����Ѿ�ȥ�ַ����˰Ͷ��޸�.ʱ�仹ʣ: " + cm.getReadableMillis(cm.getCurrentTime(), time + (6 * 3600000)));
							cm.dispose();
							return;
						}
						// -1 = Cancelled, 0 = not, 1 = true
						var type = cm.isSquadLeader("BossBalrog");
						if (type == -1) {
							cm.sendOk("��������ˣ������µǼ�.");
							cm.safeDispose();
						} else if (type == 0) {
							var memberType = cm.isSquadMember("BossBalrog");
							if (memberType == 2) {
								cm.sendOk("�㱻��ֹ�������.");
								cm.safeDispose();
							} else if (memberType == 1) {
								status = 5;
								cm.sendSimple("������ʲô�� \r\n#b#L0#����Ա��Ϣ#l \r\n#b#L1#����Զ����#l \r\n#b#L2#�˳�Զ����#l");
							} else if (memberType == -1) {
								cm.sendOk("��������ˣ������µǼ�.");
								cm.safeDispose();
							} else {
								status = 5;
								cm.sendSimple("������ʲô�� \r\n#b#L0#����Ա��Ϣ#l \r\n#b#L1#����Զ����#l \r\n#b#L2#�˳�Զ����#l");
							}
						} else { // Is leader
							status = 10;
							cm.sendSimple("������ʲô�� \r\n#b#L0#����Ա��Ϣ#l \r\n#b#L1#�߳���Ա#l \r\n#b#L2#�༭��������#l \r\n#r#L3#��ʼԶ��#l");
							// TODO viewing!
						}
					} else {
						var eim = cm.getDisconnected(balrogMode ? "BossBalrog_NORMAL" : "BossBalrog_EASY");
						if (eim == null) {
							var squd = cm.getSquad("BossBalrog");
							if (squd != null) {
								if (time + (6 * 3600000) >= cm.getCurrentTime() && !cm.getPlayer().isGM()) {
									cm.sendOk("�ڹ�ȥ��6��Сʱ�����Ѿ�ȥ�ַ����˰Ͷ��޸�.ʱ�仹ʣ: " + cm.getReadableMillis(cm.getCurrentTime(), time + (6 * 3600000)));
									cm.dispose();
									return;
								}
								cm.sendYesNo("�Ѿ���С�ӿ�ʼ����BOSS��ս��.\r\n" + squd.getNextPlayer());
								status = 3;
							} else {
								cm.sendOk("�Ѿ���С�ӿ�ʼ����BOSS��ս��.");
								cm.safeDispose();
							}
						} else {
							cm.sendYesNo("����������ˡ������ٴμ�����Ķ�����?");
							status = 2;
						}
					}
				} else {
					var eim = cm.getDisconnected(balrogMode ? "BossBalrog_NORMAL" : "BossBalrog_EASY");
					if (eim == null) {
						var squd = cm.getSquad("BossBalrog");
						if (squd != null) {
							if (time + (6 * 3600000) >= cm.getCurrentTime() && !cm.getPlayer().isGM()) {
								cm.sendOk("�ڹ�ȥ��6��Сʱ�����Ѿ�ȥ�ַ����˰Ͷ��޸�.ʱ�仹ʣ: " + cm.getReadableMillis(cm.getCurrentTime(), time + (6 * 3600000)));
								cm.dispose();
								return;
							}
							cm.sendYesNo("�Ѿ���С�ӿ�ʼ����BOSS��ս��.\r\n" + squd.getNextPlayer());
							status = 3;
						} else {
							cm.sendOk("�Ѿ���С�ӿ�ʼ����BOSS��ս��.");
							cm.safeDispose();
						}
					} else {
						cm.sendYesNo("����������ˡ������ٴμ�����Ķ�����?");
						status = 2;
					}
				}
			} else {
				cm.sendPrev("����Ҫ����һ�����.");
				cm.safeDispose();
			}
			break;
		case 1:
			if (mode == 1) {
				if (!balrogMode) { // Easy Mode
					var lvl = cm.getPlayerStat("LVL");
					if (lvl >= 50 && lvl <= 70 ||cm.getPlayer().isGM()) {
						if (cm.registerSquad("BossBalrog", 5, " ������Ϊ�����Զ���Ӷӳ������������룬���ڹ涨ʱ���ڱ����μ�̽�ն�.")) {
							cm.sendOk("�㱻����ΪԶ���Ӷӳ����ڽ�������5�������Ա���Լ�������Զ����.");
						} else {
							cm.sendOk("����������һ��.");
						}
					} else {
						cm.sendNext("��Ա������ʮ������ʮ����Χ�ڡ��밲�ź����ǵĶ��飬ʹÿ���˶��ܴﵽˮƽ����.");
					}
				} else { // Normal Mode
					if (cm.registerSquad("BossBalrog", 5, " ������Ϊ�����Զ���Ӷӳ������������룬���ڹ涨ʱ���ڱ����μ�̽�ն�.")) {
						cm.sendOk("�㱻����ΪԶ���Ӷӳ����ڽ�������5�������Ա���Լ�������Զ����.");
					} else {
						cm.sendOk("����������һ��.");
					}
				}
			} else {
				cm.sendOk("��������ΪԶ���ӵĶӳ�������̸̸.")
			}
			cm.safeDispose();
			break;
		case 2:
			if (!cm.reAdd(balrogMode ? "BossBalrog_NORMAL" : "BossBalrog_EASY", "BossBalrog")) {
				cm.sendOk("����������һ��.");
			}
			cm.safeDispose();
			break;
		case 3:
			if (mode == 1) {
				var squd = cm.getSquad("BossBalrog");
				if (squd != null && !squd.getAllNextPlayer().contains(cm.getPlayer().getName())) {
					squd.setNextPlayer(cm.getPlayer().getName());
					cm.sendOk("���Ѿ���Զ��������.");
				}
			}
			cm.dispose();
			break;
		case 5:
			if (selection == 0) {
				if (!cm.getSquadList("BossBalrog", 0)) {
					cm.sendOk("����һ��δ֪�Ĵ��󣬶�С�ӵ����󱻾ܾ�.");
					cm.safeDispose();
				} else {
					cm.dispose();
				}
			} else if (selection == 1) { // join
				var ba = cm.addMember("BossBalrog", true);
				if (ba == 2) {
					cm.sendOk("�������������Ժ�����.");
					cm.safeDispose();
				} else if (ba == 1) {
					cm.sendOk("���Ѿ��ɹ��ؼ����˶���");
					cm.safeDispose();
				} else {
					cm.sendOk("���Ѿ���Զ��������.");
					cm.safeDispose();
				}
			} else { // withdraw
				var baa = cm.addMember("BossBalrog", false);
				if (baa == 1) {
					cm.sendOk("���Ѿ��ɹ����˳��˶���");
					cm.safeDispose();
				} else {
					cm.sendOk("�㲻��Զ������.");
					cm.safeDispose();
				}
			}
			break;
		case 10:
			if (selection == 0) {
				if (!cm.getSquadList("BossBalrog", 0)) {
					cm.sendOk("����һ��δ֪�Ĵ��󣬶�С�ӵ����󱻾ܾ�.");
				}
				cm.safeDispose();
			} else if (selection == 1) {
				status = 11;
				if (!cm.getSquadList("BossBalrog", 1)) {
					cm.sendOk("����һ��δ֪�Ĵ��󣬶�С�ӵ����󱻾ܾ�.");
				}
				cm.safeDispose();
			} else if (selection == 2) {
				status = 12;
				if (!cm.getSquadList("BossBalrog", 2)) {
					cm.sendOk("����һ��δ֪�Ĵ��󣬶�С�ӵ����󱻾ܾ�.");
				}
				cm.safeDispose();
			} else if (selection == 3) { // get insode
				if (cm.getSquad("BossBalrog") != null) {
					var dd = cm.getEventManager(balrogMode ? "BossBalrog_NORMAL" : "BossBalrog_EASY");
					dd.startInstance(cm.getSquad("BossBalrog"), cm.getMap(), balrogMode ? 160106 : 160105);
					cm.dispose();
				} else {
					cm.sendOk("����һ��δ֪�Ĵ��󣬶�С�ӵ����󱻾ܾ�.");
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