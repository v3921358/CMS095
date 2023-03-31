load('nashorn:mozilla_compat.js');
importPackage(Packages.server);
importPackage(Packages.client);
importPackage(Packages.tools);
importPackage(Packages.database);
var 皇冠白 = "#fEffect/CharacterEff/1003252/0/0#";
var 小雪花 = "#fEffect/CharacterEff/1003393/0/0#";
var 音符 = "#fEffect/CharacterEff/1032063/0/0#";
var 感叹号 = "#fUI/UIWindow/Quest/icon0#";
var 感叹号 = "#fUI/UIWindow/Quest/icon0#";
var 皇冠白 = "#fUI/GuildMark/Mark/Etc/00009004/16#";
var 中条蓝 = "#fUI/ChatBalloon/tutorial/w#";
var 中条猫 = "#fUI/ChatBalloon/37/n#";
var 猫右 = "#fUI/ChatBalloon/37/ne#";
var 猫左 = "#fUI/ChatBalloon/37/nw#";
var 右 = "#fUI/ChatBalloon/37/e#";
var 左 = "#fUI/ChatBalloon/37/w#";
var 下条猫 = "#fUI/ChatBalloon/37/s#";
var 猫下右 = "#fUI/ChatBalloon/37/se#";
var 猫下左 = "#fUI/ChatBalloon/37/sw#";
var 完成 = "#fUI/UIWindow/Quest/Tab/enabled/2#";
var warp = -1
var status = 0;
var cal = java.util.Calendar.getInstance();

var weekday = new Date().getDay();
var mapList1 = [101010000, 200080100, 101030000, 102020000, 221000000];
var mapList2 = [101020000, 222000000, 220020300, 105000000, 211010000];
var 第一站 = weekday.value % 2 == 0 ? mapList1[0] : mapList2[0];
var 第二站 = weekday.value % 2 == 0 ? mapList1[1] : mapList2[1];
var 第三站 = weekday.value % 2 == 0 ? mapList1[2] : mapList2[2];
var 第四站 = weekday.value % 2 == 0 ? mapList1[3] : mapList2[3];
var 第五站 = weekday.value % 2 == 0 ? mapList1[4] : mapList2[4];
var accId;

function start() {
	status = -1;

	action(1, 0, 0);
}

function action(mode, type, selection) {
	if (mode == -1) {
		cm.dispose();
	} else {
		if (status >= 0 && mode == 0) {
			cm.dispose();
			return;
		}
		if (mode == 1) {
			status++;
		} else {
			status--;
		}

		if (status == 0) {
			var text = "";
			accId = cm.getPlayer().getClient().getAccID();
			text += "                  #k" + 皇冠白 + " #r#e#w全民马拉松#n#k " + 皇冠白 + "\r\n\r\n";
			text += "  " + 猫左 + 中条猫 + 中条猫 + 中条猫 + 中条猫 + 中条猫 + 中条猫 + 中条猫 + 中条猫 + 中条猫 + 中条猫 + 中条猫 + 中条猫 + 中条猫 + 中条猫 + 中条猫 + 中条猫 + 中条猫 + 中条猫 + 中条猫 + 中条猫 + 猫右 + "\r\n";
			text += "#k   现在完成马拉松环数：[#r" + cm.getBossLogD_Name(("马拉松" + accId)) + "#k/5]#l\r\n\r\n#k   \r\n" //3#r#e所有签到会消除飞行类道具#n#k
			if (cm.getPlayer().getBossLogD_Name(("马拉松" + accId)) == 0) {
				text += "         #L1#" + 音符 + "#e马拉松起点站(#r可签到#k)#n#l\r\n\r\n" //3
			} else if (cm.getPlayer().getBossLogD_Name(("马拉松" + accId)) > 0) {
				text += "         " + 音符 + "#r#e马拉松起点站" + 音符 + "#n#l" + 完成 + "#k\r\n" //3
			}

			if (cm.getPlayer().getBossLogD_Name(("马拉松" + accId)) == 1) {
				text += "         #L2#" + 音符 + "#e马拉松第二站(#r可签到#k)#n#l\r\n\r\n" //3
			} else if (cm.getPlayer().getBossLogD_Name(("马拉松" + accId)) > 1) {
				text += "          " + 音符 + "#r#e马拉松第二站" + 音符 + "#n#l" + 完成 + "#k\r\n" //3
			} else {
				text += "	          " + 皇冠白 + "#e马拉松第二站 正在进行中#l\r\n" //3
			}

			if (cm.getPlayer().getBossLogD_Name(("马拉松" + accId)) == 2) {
				text += "         #L3#" + 音符 + "#e马拉松第三站(#r可签到#k)#n#l\r\n\r\n" //3
			} else if (cm.getPlayer().getBossLogD_Name(("马拉松" + accId)) > 2) {
				text += "          " + 音符 + "#r#e马拉松第三站#n" + 音符 + "#l" + 完成 + "#k\r\n" //3
			} else {
				text += "            " + 皇冠白 + "#e马拉松第三站 正在进行中#l\r\n" //3
			}

			if (cm.getPlayer().getBossLogD_Name(("马拉松" + accId)) == 3) {
				text += "         #L4#" + 音符 + "#e马拉松第四站(#r可签到#k)#n#l\r\n\r\n" //3
			} else if (cm.getPlayer().getBossLogD_Name(("马拉松" + accId)) > 3) {
				text += "          " + 音符 + "#r#e马拉松第四站#n" + 音符 + "#l" + 完成 + "#k\r\n" //3
			} else {
				text += "            " + 皇冠白 + "#e马拉松第四站 正在进行中#l\r\n" //3
			}
			if (cm.getPlayer().getBossLogD_Name(("马拉松" + accId)) == 4) {
				text += "         #L5#" + 音符 + "#e马拉松终点站(#r可签到#k)#n#l\r\n\r\n" //3
			} else if (cm.getPlayer().getBossLogD_Name(("马拉松" + accId)) > 3) {
				text += "          " + 音符 + "#r#e马拉松终点站#n" + 音符 + "#l" + 完成 + "#k\r\n" //3
			} else {
				text += "            " + 皇冠白 + "#e马拉松终点站 正在进行中#l\r\n" //3
			}
			text += "\t#d#e#w第一站：#m" + 第一站 + "#\t第二站：#m" + 第二站 + "#\r\n\t第三站：#m" + 第三站 + "#\t第四站：#m" + 第四站 + "#\r\n\t第五站：#m" + 第五站 + "##l\r\n" //3	

			cm.sendSimple(text);
		} else if (selection == 1) {

			if (cm.getPlayer().getBossLogD_Name(("马拉松" + accId)) >= 5) {
				cm.sendOk("你已完成今天的马拉松。");
				cm.dispose();
				return;
			}
			if (cm.getPlayer().getMapId() == 第一站) {
				//cm.removeAll(5041000);
				cm.getPlayer().setBossLog(("马拉松" + accId))
				cm.sendOk("请前往第二站#m" + 第二站 + "#进行签到");
				cm.worldMessage(5, "" + cm.getPlayer().getName() + " :成功签到全民马拉松起点站,向着第二站发起冲刺！"); //红色
				//cm.喇叭(3, "恭喜[" + cm.getPlayer().getName() + "]成功签到全民马拉松起点站,向着射手村-东部树林发起冲刺！") ;
				cm.dispose();
			} else {
				cm.sendOk("请在#m" + 第一站 + "#进行签到。");
				cm.dispose();
			}


		} else if (selection == 2) {
			if (cm.getPlayer().getMapId() == 第二站 && cm.getPlayer().getLevel() >= 10) {
				//cm.removeAll(5041000);
				cm.getPlayer().setBossLog(("马拉松" + accId));
				cm.gainItemPeriodF(5211060, 1, 180);
				cm.sendOk("请前往第三站#m" + 第三站 + "#进行签到");
				cm.worldMessage(5, "" + cm.getPlayer().getName() + " :成功签到全民马拉松第二站,向着第三站发起冲刺！"); //红色
				cm.dispose();

			} else {
				cm.sendOk("请在第二站#m" + 第二站 + "#签到，或角色等级不足10级");
				cm.dispose();
			}
		} else if (selection == 3) {
			if (cm.getPlayer().getMapId() == 第三站 && cm.getPlayer().getLevel() >= 30) {
				//cm.removeAll(5041000);
				cm.getPlayer().setBossLog(("马拉松" + accId));
				cm.gainNX2(3000); //抵用
				cm.sendOk("请前往第四站#m" + 第四站 + "#进行签到");
				cm.worldMessage(5, "" + cm.getPlayer().getName() + " :成功签到全民马拉松第三站,向着第四站发起冲刺！"); //红色
				cm.dispose();
			} else {
				cm.sendOk("请在第三站#m" + 第三站 + "#签到，或角色等级不足30级");
				cm.dispose();
			}
		} else if (selection == 4) {
			if (cm.getPlayer().getMapId() == 第四站 && cm.getPlayer().getLevel() >= 50) {
				//cm.removeAll(5041000);
				cm.getPlayer().setBossLog(("马拉松" + accId));
				cm.gainNX(1000); //点卷	
				cm.sendOk("请前往终点#m" + 第五站 + "#进行签到");
				cm.worldMessage(5, "" + cm.getPlayer().getName() + " :成功签到全民马拉松第四站,向着终点发起冲刺！"); //红色
				cm.dispose();
			} else {
				cm.sendOk("请在第四站#m" + 第四站 + "#签到，或角色等级不足50级");
				cm.dispose();
			}
		} else if (selection == 5) {
			if (cm.getPlayer().getMapId() == 第五站 && cm.getPlayer().getLevel() >= 70) {
				//cm.removeAll(5041000);

				//cm.gainItem(4000463,20);//国庆


				cm.gainItem(5062000, 10);
				cm.gainItem(4001126, 200);
				cm.gainItem(4310019, 1);
				// cm.gainItem(4032263,20);//副职业符咒师
				// cm.gainItem(4031705,20);//副职业炼金初级
				//cm.getPlayer().setAcLog("malasong");
				cm.getPlayer().setBossLog(("马拉松" + accId));
				cm.sendOk("恭喜您,完成全民马拉松任务");
				cm.worldMessage(5, "" + cm.getPlayer().getName() + " :成功完成全民马拉松,获得丰厚奖励！友情提示：三倍经验卡换线生效"); //红色
				cm.dispose();
				//cm.喇叭(3, "恭喜[" + cm.getPlayer().getName() + "]成功完成全民马拉松,获得丰厚奖励！") ;
			} else {
				cm.sendOk("请在终点站#m" + 第五站 + "#签到，或角色等级不足70级");
				cm.dispose();
			}
		}
	}
}