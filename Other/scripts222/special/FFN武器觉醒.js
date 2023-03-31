var status = 0;
var secondLink = 0; //二级链接
var ca = java.util.Calendar.getInstance();
var cishu = ca.get(java.util.Calendar.YEAR);
var cishu2 = ca.get(java.util.Calendar.DATE);
var cishu1 = ca.get(java.util.Calendar.MONTH) + 1;
var shijian = (cishu * 10000) + (cishu1 * 100) + cishu2; //获取日期
var hour = ca.get(java.util.Calendar.HOUR_OF_DAY); //获得小时
var minute = ca.get(java.util.Calendar.MINUTE); //获得分钟
var second = ca.get(java.util.Calendar.SECOND); //获得秒
var weaponlist = [1492239, 1482225, 1462247, 1452261, 1382269, 1372232, 1342107, 1332283, 1472269, 1442280, 1432222, 1422192, 1412184, 1402263, 1322259, 1312207, 1302347];
var select_1_point; //选择第一件武器
var select_2_point; //选择第二件武器
var selected1; //确认选择的1武器
var selected2; //确认选择的2武器
var getfirstlevel; //获取选择的第一件装备等级
var weponminlevel = 100 //武器最低等级限制
var poprate; //属性倍数
var weponEnchantsLV; // 觉醒级别固定数值
var checkEnchantsLV; //判定觉醒级别

function start() {
	status = -1;
	action(1, 0, 0);
}

function action(mode, type, selection) {
	if (mode === -1) {
		cm.dispose();
	} else {
		if (status >= 0 && mode === 0) {
			cm.sendOk("感谢你的光临！");
			cm.dispose();
			return;
		}

		status = status + (mode === 1 ? 1 : -1);
		if (status == 0) {
			var txt = "\t\t\t\t#e#d【法弗纳武器觉醒系统】\r\n" +
				"#r#e两件#b法弗纳#r的武器可进行觉醒\r\n" +
				"#b#k被觉醒的装备可得到#b属性强化#k 被吸收的装备会#b消失#k... \r\n" +
				"#b进行觉醒请务必选对装备 #r选错将无法撤回#k\r\n" +
				"#d装备穿戴等级会提升觉醒等级所增加的属性#k\r\n" +
				"#d觉醒等级: #rD级、C级、B级、A级、S级、SS级、SSS级#k\r\n" +
				"#d提示: #r建议把需要觉醒的装备放在#b第一格位置#k\r\n#e\r\n" +
				"  进行觉醒的装备[#r未选#k]  被吸收的装备[#r未选#k]\r\n          " +
				"#L1##e#r#b选择进行觉醒的装备#d#l#k\r\n ";
			cm.sendSimple(txt);
		} else if (status == 1) {
			if (selection == 1) {
				var it;
				var texts = "#r---------------请选择您背包中的装备----------------#b\r\n";
				var inv = cm.getInventory(1);
				for (var i = 0; i <= 100; i++) {
					it = inv.getItem(i);
					if (it != null && cm.isCash(it.getItemId()) != true) {
						texts += "#L" + i + "##v" + it.getItemId() + "# #b#z" + it.getItemId() + "# "
						if (it.getHpR() >= 1) {
							texts += " #k觉醒等级: #r" + it.getOwner() + "#l#b\r\n"
						}
						if (it.getHpR() == 0) {
							texts += " #k觉醒等级: #r未觉醒#l#b\r\n"
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
					//selected1.setOwner("已签名");
				}
				if (weaponlist.indexOf(selected1.getItemId()) != -1) {
					secondLink = 2;
					var txt = "\t\t\t\t#e#d【装备觉醒系统】\r\n" +
						"#r#e两件#b法弗纳#r的武器可进行觉醒\r\n" +
						"#b#k被觉醒的装备可得到#b属性强化#k 被吸收的装备会#b消失#k... \r\n" +
						"#b进行觉醒请务必选对装备 #r选错将无法撤回#k\r\n" +
						"#d装备穿戴等级会提升觉醒等级所增加的属性#k\r\n" +
						"#d觉醒等级: #rD级、C级、B级、A级、S级、SS级、SSS级#k\r\n#e\r\n" +
						"#d提示: #r建议把需要觉醒的装备放在#b第一格位置#k\r\n" +
						"  进行觉醒的装备[#v" + selected1.getItemId() + "#]  被吸收的装备[#r未选#k]\r\n" +
						"          #L1##e#r#b选择被吸收的装备#d#l#k\r\n "
					cm.sendSimple(txt);
				} else {
					cm.sendOk("#r只有法弗纳武器可以进行觉醒升级！#k请重新选择...#k");
					cm.dispose();
				}
			}
		} else if (status == 3) {
			if (secondLink == 2) {
				if (selection == 1) {
					var it;
					var weapon2num = 0;
					getfirstlevel = cm.getReqLevel(selected1.getItemId());
					var texts = "#k进行觉醒的装备[#v" + selected1.getItemId() + "##k]  被吸收的装备[#b请选择#k]\r\n\r\n#r温馨提示:系统自动跳过不符合条件的装备!#k\r\n";
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
						cm.sendOk("你好像没有符合觉醒条件的法弗纳武器");
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
				cm.sendSimple("进行觉醒的装备当前属性为:\r\n   #r当前力量: #b" + selected1.getStr() + "\r\n   #r当前敏捷: #b" + selected1.getDex() + "\r\n   #r当前智力: #b" + selected1.getInt() + "\r\n   #r当前运气: #b" + selected1.getLuk() + "\r\n   #r当前攻击力: #b" + selected1.getWatk() + "\r\n   #r当前魔法力: #b" + selected1.getMatk() + "#k#e\r\n  #e进行觉醒的装备[#v" + selected1.getItemId() + "#]  被吸收的装备[#v" + selected2.getItemId() + "#]\r\n    #L1##e#r#b开始进行合成(#r被吸收的装备会消失#b)#d#l#k\r\n ");
			}
		} else if (status == 5) {
			if (secondLink == 4) {
				var randomNum = Math.floor(Math.random() * 100);
				var getWeapon = selected1.copy();
				poprate = 1;
				if (getWeapon.getOwner().indexOf("D") != -1) {
					checkEnchantsLV = "小撸C";
					weponEnchantsLV = 4;
				} else if (getWeapon.getOwner().indexOf("C") != -1) {
					checkEnchantsLV = "远撸B";
					weponEnchantsLV = 5;
				} else if (getWeapon.getOwner().indexOf("B") != -1) {
					checkEnchantsLV = "近撸A";
					weponEnchantsLV = 6;
				} else if (getWeapon.getOwner().indexOf("A") != -1) {
					checkEnchantsLV = "站撸S";
					weponEnchantsLV = 7;
				} else if ((getWeapon.getOwner().split("S")).length == 2) {
					checkEnchantsLV = "狂撸SS";
					weponEnchantsLV = 8;
				} else if ((getWeapon.getOwner().split("S")).length == 3) {
					checkEnchantsLV = "登仙SSS";
					weponEnchantsLV = 9;
				} else if ((getWeapon.getOwner().split("S")).length == 4) {
					poprate = 2;
					checkEnchantsLV = "神话SSS";
					weponEnchantsLV = Math.floor(Math.random() * 5);
				} else {
					checkEnchantsLV = "刮痧D";
					weponEnchantsLV = 3;
				}
				getWeapon.setStr((getWeapon.getStr() + (weponEnchantsLV * poprate)));
				getWeapon.setDex((getWeapon.getDex() + (weponEnchantsLV * poprate)));
				getWeapon.setInt((getWeapon.getInt() + (weponEnchantsLV * poprate)));
				getWeapon.setLuk((getWeapon.getLuk() + (weponEnchantsLV * poprate)));
				getWeapon.setWatk((getWeapon.getWatk() + (weponEnchantsLV * poprate)));
				getWeapon.setMatk((getWeapon.getMatk() + (weponEnchantsLV * poprate)));
				getWeapon.setOwner("" + checkEnchantsLV + "级");
				cm.removeSlot(1, select_2_point, 1); //消失的装备
				cm.removeSlot(1, select_1_point, 1); //消失的装备
				Packages.server.MapleInventoryManipulator.addFromDrop(cm.getC(), getWeapon, false); //强化的装备获取
				cm.sendOk("觉醒成功 当前觉醒等级为: #r" + getWeapon.getOwner() + "\r\n   #r力量提升: #b" + (weponEnchantsLV * poprate) + "#r点\r\n   #r敏捷提升: #b" + (weponEnchantsLV * poprate) + "#r点\r\n   #r智力提升: #b" + (weponEnchantsLV * poprate) + "#r点\r\n   #r运气提升: #b" + (weponEnchantsLV * poprate) + "#r点\r\n   #r物攻提升: #b" + (weponEnchantsLV * poprate) + "#r点\r\n   #r魔攻提升: #b" + (weponEnchantsLV * poprate) + "#r点\r\n#d提示:达到神话SSS级后，提升属性跟“脸”#k有很大联系!!!#k");
				cm.getPlayer().dropMessage(1, "觉醒成功 当前觉醒等级为: " + getWeapon.getOwner() + "\r\n请打开背包查看吧.");
				var text = "[恭喜]" + cm.getPlayer().getName() + " : " + "他手上的FFN武器已经达到【" + getWeapon.getOwner() + "】啦，快去抱大腿！";
				cm.worldMessage(6, text);
				cm.dispose();
			}
		}
	}
}