var status = 0;
var secondLink = 0; //��������
var ca = java.util.Calendar.getInstance();
var cishu = ca.get(java.util.Calendar.YEAR);
var cishu2 = ca.get(java.util.Calendar.DATE);
var cishu1 = ca.get(java.util.Calendar.MONTH) + 1;
var shijian = (cishu * 10000) + (cishu1 * 100) + cishu2; //��ȡ����
var hour = ca.get(java.util.Calendar.HOUR_OF_DAY); //���Сʱ
var minute = ca.get(java.util.Calendar.MINUTE); //��÷���
var second = ca.get(java.util.Calendar.SECOND); //�����
var weaponlist = [1492239, 1482225, 1462247, 1452261, 1382269, 1372232, 1342107, 1332283, 1472269, 1442280, 1432222, 1422192, 1412184, 1402263, 1322259, 1312207, 1302347];
var select_1_point; //ѡ���һ������
var select_2_point; //ѡ��ڶ�������
var selected1; //ȷ��ѡ���1����
var selected2; //ȷ��ѡ���2����
var getfirstlevel; //��ȡѡ��ĵ�һ��װ���ȼ�
var weponminlevel = 100 //������͵ȼ�����
var poprate; //���Ա���
var weponEnchantsLV; // ���Ѽ���̶���ֵ
var checkEnchantsLV; //�ж����Ѽ���

function start() {
	status = -1;
	action(1, 0, 0);
}

function action(mode, type, selection) {
	if (mode === -1) {
		cm.dispose();
	} else {
		if (status >= 0 && mode === 0) {
			cm.sendOk("��л��Ĺ��٣�");
			cm.dispose();
			return;
		}

		status = status + (mode === 1 ? 1 : -1);
		if (status == 0) {
			var txt = "\t\t\t\t#e#d����������������ϵͳ��\r\n" +
				"#r#e����#b������#r�������ɽ��о���\r\n" +
				"#b#k�����ѵ�װ���ɵõ�#b����ǿ��#k �����յ�װ����#b��ʧ#k... \r\n" +
				"#b���о��������ѡ��װ�� #rѡ���޷�����#k\r\n" +
				"#dװ�������ȼ����������ѵȼ������ӵ�����#k\r\n" +
				"#d���ѵȼ�: #rD����C����B����A����S����SS����SSS��#k\r\n" +
				"#d��ʾ: #r�������Ҫ���ѵ�װ������#b��һ��λ��#k\r\n#e\r\n" +
				"  ���о��ѵ�װ��[#rδѡ#k]  �����յ�װ��[#rδѡ#k]\r\n          " +
				"#L1##e#r#bѡ����о��ѵ�װ��#d#l#k\r\n ";
			cm.sendSimple(txt);
		} else if (status == 1) {
			if (selection == 1) {
				var it;
				var texts = "#r---------------��ѡ���������е�װ��----------------#b\r\n";
				var inv = cm.getInventory(1);
				for (var i = 0; i <= 100; i++) {
					it = inv.getItem(i);
					if (it != null && cm.isCash(it.getItemId()) != true) {
						texts += "#L" + i + "##v" + it.getItemId() + "# #b#z" + it.getItemId() + "# "
						if (it.getHpR() >= 1) {
							texts += " #k���ѵȼ�: #r" + it.getOwner() + "#l#b\r\n"
						}
						if (it.getHpR() == 0) {
							texts += " #k���ѵȼ�: #rδ����#l#b\r\n"
						}
					}
				}
				secondLink = 1;
				cm.sendSimple(texts);
			}
		} else if (status == 2) {
			if (secondLink == 1) {
				select_1_point = selection;
				selected1 = cm.getInventory(1).getItem(select_1_point);
				getfirstlevel = cm.getReqLevel(selected1.getItemId());
				if (selected1.getHpR() == 0) {
					selected1.setHpR(0);
					//selected1.setOwner("��ǩ��");
				}
				if (weaponlist.indexOf(selected1.getItemId()) != -1) {
					secondLink = 2;
					var txt = "\t\t\t\t#e#d��װ������ϵͳ��\r\n" +
						"#r#e����#b������#r�������ɽ��о���\r\n" +
						"#b#k�����ѵ�װ���ɵõ�#b����ǿ��#k �����յ�װ����#b��ʧ#k... \r\n" +
						"#b���о��������ѡ��װ�� #rѡ���޷�����#k\r\n" +
						"#dװ�������ȼ����������ѵȼ������ӵ�����#k\r\n" +
						"#d���ѵȼ�: #rD����C����B����A����S����SS����SSS��#k\r\n#e\r\n" +
						"#d��ʾ: #r�������Ҫ���ѵ�װ������#b��һ��λ��#k\r\n" +
						"  ���о��ѵ�װ��[#v" + selected1.getItemId() + "#]  �����յ�װ��[#rδѡ#k]\r\n" +
						"          #L1##e#r#bѡ�����յ�װ��#d#l#k\r\n "
					cm.sendSimple(txt);
				} else {
					cm.sendOk("#rֻ�з������������Խ��о���������#k������ѡ��...#k");
					cm.dispose();
				}
			}
		} else if (status == 3) {
			if (secondLink == 2) {
				if (selection == 1) {
					var it;
					var weapon2num = 0;
					getfirstlevel = cm.getReqLevel(selected1.getItemId());
					var texts = "#k���о��ѵ�װ��[#v" + selected1.getItemId() + "##k]  �����յ�װ��[#b��ѡ��#k]\r\n\r\n#r��ܰ��ʾ:ϵͳ�Զ�����������������װ��!#k\r\n";
					var inv = cm.getInventory(1);
					for (var i = 0; i <= 100; i++) {
						it = inv.getItem(i);
						if (it != null && i != select_1_point && weaponlist.indexOf(it.getItemId()) != -1) {
							texts += "#L" + i + "##v" + it.getItemId() + "# #b#z" + it.getItemId() + "# "
							weapon2num += 1;
						}
					}
					secondLink = 3;
					if (weapon2num > 0) {
						cm.sendSimple(texts);
					} else {
						cm.sendOk("�����û�з��Ͼ��������ķ���������");
						cm.dispose();
						return;
					}

				}
			}
		} else if (status == 4) {
			if (secondLink == 3) {
				select_2_point = selection;
				selected2 = cm.getInventory(1).getItem(select_2_point);
				secondLink = 4;
				cm.sendSimple("���о��ѵ�װ����ǰ����Ϊ:\r\n   #r��ǰ����: #b" + selected1.getStr() + "\r\n   #r��ǰ����: #b" + selected1.getDex() + "\r\n   #r��ǰ����: #b" + selected1.getInt() + "\r\n   #r��ǰ����: #b" + selected1.getLuk() + "\r\n   #r��ǰ������: #b" + selected1.getWatk() + "\r\n   #r��ǰħ����: #b" + selected1.getMatk() + "#k#e\r\n  #e���о��ѵ�װ��[#v" + selected1.getItemId() + "#]  �����յ�װ��[#v" + selected2.getItemId() + "#]\r\n    #L1##e#r#b��ʼ���кϳ�(#r�����յ�װ������ʧ#b)#d#l#k\r\n ");
			}
		} else if (status == 5) {
			if (secondLink == 4) {
				var randomNum = Math.floor(Math.random() * 100);
				var getWeapon = selected1.copy();
				poprate = 1;
				if (getWeapon.getOwner().indexOf("D") != -1) {
					checkEnchantsLV = "СߣC";
					weponEnchantsLV = 4;
				} else if (getWeapon.getOwner().indexOf("C") != -1) {
					checkEnchantsLV = "ԶߣB";
					weponEnchantsLV = 5;
				} else if (getWeapon.getOwner().indexOf("B") != -1) {
					checkEnchantsLV = "��ߣA";
					weponEnchantsLV = 6;
				} else if (getWeapon.getOwner().indexOf("A") != -1) {
					checkEnchantsLV = "վߣS";
					weponEnchantsLV = 7;
				} else if ((getWeapon.getOwner().split("S")).length == 2) {
					checkEnchantsLV = "��ߣSS";
					weponEnchantsLV = 8;
				} else if ((getWeapon.getOwner().split("S")).length == 3) {
					checkEnchantsLV = "����SSS";
					weponEnchantsLV = 9;
				} else if ((getWeapon.getOwner().split("S")).length == 4) {
					poprate = 2;
					checkEnchantsLV = "��SSS";
					weponEnchantsLV = Math.floor(Math.random() * 5);
				} else {
					checkEnchantsLV = "����D";
					weponEnchantsLV = 3;
				}
				getWeapon.setStr((getWeapon.getStr() + (weponEnchantsLV * poprate)));
				getWeapon.setDex((getWeapon.getDex() + (weponEnchantsLV * poprate)));
				getWeapon.setInt((getWeapon.getInt() + (weponEnchantsLV * poprate)));
				getWeapon.setLuk((getWeapon.getLuk() + (weponEnchantsLV * poprate)));
				getWeapon.setWatk((getWeapon.getWatk() + (weponEnchantsLV * poprate)));
				getWeapon.setMatk((getWeapon.getMatk() + (weponEnchantsLV * poprate)));
				getWeapon.setOwner("" + checkEnchantsLV + "��");
				cm.removeSlot(1, select_2_point, 1); //��ʧ��װ��
				cm.removeSlot(1, select_1_point, 1); //��ʧ��װ��
				Packages.server.MapleInventoryManipulator.addFromDrop(cm.getC(), getWeapon, false); //ǿ����װ����ȡ
				cm.sendOk("���ѳɹ� ��ǰ���ѵȼ�Ϊ: #r" + getWeapon.getOwner() + "\r\n   #r��������: #b" + (weponEnchantsLV * poprate) + "#r��\r\n   #r��������: #b" + (weponEnchantsLV * poprate) + "#r��\r\n   #r��������: #b" + (weponEnchantsLV * poprate) + "#r��\r\n   #r��������: #b" + (weponEnchantsLV * poprate) + "#r��\r\n   #r�﹥����: #b" + (weponEnchantsLV * poprate) + "#r��\r\n   #rħ������: #b" + (weponEnchantsLV * poprate) + "#r��\r\n#d��ʾ:�ﵽ��SSS�����������Ը�������#k�кܴ���ϵ!!!#k");
				cm.getPlayer().dropMessage(1, "���ѳɹ� ��ǰ���ѵȼ�Ϊ: " + getWeapon.getOwner() + "\r\n��򿪱����鿴��.");
				var text = "[��ϲ]" + cm.getPlayer().getName() + " : " + "�����ϵ�FFN�����Ѿ��ﵽ��" + getWeapon.getOwner() + "��������ȥ�����ȣ�";
				cm.worldMessage(6, text);
				cm.dispose();
			}
		}
	}
}