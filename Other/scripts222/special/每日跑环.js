var 心 = "#fUI/GuildMark.img/Mark/Etc/00009001/14#";
var 奖励 = "#fUI/UIWindow.img/Quest/reward#";
var 金币 = "#fUI/UIWindow.img/QuestIcon/7/0#";
var 经验 = "#fUI/UIWindow.img/QuestIcon/8/0#";
var 人气度 = "#fUI/UIWindow.img/QuestIcon/6/0#";
var 任务简述 = "#fUI/UIWindow.img/Quest/summary#";
var 无条件获得 = "#fUI/UIWindow.img/Quest/basic#";
var 定义任务道具 = [4000015, 4000021, 4000012, 4000007, 4000008, 4000030, 4000499, 4000024, 4000004, 4000034];
var 四五环任务道具 = [
	1050088,
	1050088,
	1050088,
	1072196,
	1072209,
	1082142,
	1002323,
	1072194,
	1041121,
	1061120,
	1072203,
	1072206,
	1082143,
	1082125,
	1050089,
	1072192,
	1072207,
	1082128,
	1041115,
	1061114,
	1072197,
	1082125,
	1082131,
	1342007,
	1632002,
	1632002,
	1642002,
	1612003,
	1652003,
	1652003,
	1612003,
	1622003
]
var status = -1;
var sel;
var mod;
var Simple = "";
var pos;
var sqlpos;



function start() {
	action(1, 0, 0);


}

function 获取任务信息() {
	物品id = Number(cm.getQuestRecord(444440).getCustomData()); //读取任务物品
	物品数量 = Number(cm.getQuestRecord(444441).getCustomData()); //读取任务个数

}

function 接任务(a, b) {

	cm.getQuestRecord(444440).setCustomData(a); //记录任务物品
	cm.getQuestRecord(444441).setCustomData(b); //记录任务个数
	cm.setBossLog("跑环状态");
	cm.getPlayer().saveToDB(false, false);

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
		当前环数 = cm.getPlayer().getBossLogD("每日跑环");
		if (当前环数 == 0) {
			var 系数 = 1;
		} else if (当前环数 == 1) {
			var 系数 = 1.2;
		} else if (当前环数 == 2) {
			var 系数 = 1.4;
		} else if (当前环数 == 3) {
			var 系数 = 1.8;
		} else if (当前环数 == 4) {
			var 系数 = 2;
		}
		当前环数状态 = cm.getPlayer().getBossLogD("跑环状态");
		等级 = cm.getPlayer().getLevel();
		if (status == 0) {
			var Simple = "\t\t#e" + 心 + " 欢迎来到【eV.冒险岛】跑环任务 " + 心 + "#n\r\n";
			Simple += "\r\n" + 任务简述 + "\r\n";
			Simple += "\t1.每天可以完成5轮任务(每天 0:00 刷新)\r\n";
			Simple += "\t2.前3轮需求固定的N种物品其中的一种，后2轮需求\r\n随机80~110级武器一件\r\n";
			Simple += "\t3.跑环任务后两轮120级以上才能领取#k\r\n";
			Simple += "#e任务进度：#n\r\n";
			if (当前环数 > 4) {
				Simple += "\t你已完成了今天的跑环任务！\r\n";
				cm.sendOk(Simple)
				cm.dispose();
				return
			}
			if (当前环数 > 2 && 等级 < 120) {
				Simple += "\t你的等级不足120级，无法开启后两轮！\r\n";
				cm.sendOk(Simple)
				cm.dispose();
				return
			}
			Simple += "\t当前第 #r" + (cm.getPlayer().getBossLogD("每日跑环") + 1) + "#k 环#d\r\n";
			if (当前环数状态 <= 0) {
				if (当前环数 < 3) {
					物品数量 = Math.floor(Math.random() * 50) + 150;
					物品id = 定义任务道具[Math.floor(Math.random() * 定义任务道具.length)];
					接任务(物品id, 物品数量)
				} else {
					物品数量 = 1;
					物品id = 四五环任务道具[Math.floor(Math.random() * 四五环任务道具.length)];
					接任务(物品id, 物品数量)
				}
				Simple += "\t请帮我收集 #v" + 物品id + "##z" + 物品id + "# " + 物品数量 + " 个，您已收集 " + cm.itemQuantity(物品id) + " 个\r\n\r\n"
				Simple += "#k" + 奖励 + "\r\n\r\n";
				Simple += "" + 经验 + " " + (Math.floor(9000 * 等级 * 等级 * 系数)) + "\r\n\r\n" + 金币 + " " + (Math.floor(等级 * 30000 * 系数)) + "\r\n";
				if (当前环数 == 3) {
					Simple += "" + 无条件获得 + "\r\n#v5200002#点券 2000 #v2022336##z2022336#\r\n"
				}
				if (当前环数 == 4) {
					Simple += "" + 无条件获得 + "\r\n#v5200002#点券 3000 #v2022336##z2022336#  #v5220010##z5220010#   #v2049006##z2049006#\r\n"
				}
			} else {
				获取任务信息();
				Simple += "\t请帮我收集 #v" + 物品id + "##z" + 物品id + "# " + 物品数量 + " 个，您已收集 " + cm.itemQuantity(物品id) + " 个\r\n\r\n"
				Simple += "#k" + 奖励 + "\r\n\r\n";
				Simple += "" + 经验 + " " + (Math.floor(9000 * 等级 * 等级 * 系数)) + "\r\n\r\n" + 金币 + " " + (Math.floor(等级 * 30000 * 系数)) + "\r\n";
				if (当前环数 == 3) {
					Simple += "" + 无条件获得 + "\r\n#v5200002#点券 2000 #v2022336##z2022336#\r\n"
				}
				if (当前环数 == 4) {
					Simple += "" + 无条件获得 + "\r\n#v5200002#点券 3000 #v2022336##z2022336#  #v5220010##z5220010#  #v2049006##z2049006#\r\n"
				}
			}


			cm.sendSimple(Simple);
		} else if (status == 1) {
			if (当前环数 > 4) {
				cm.sendOk("你已完成了今天的跑环任务！");
				cm.dispose();
				return
			}
			if (cm.getInventory(1).isFull(0)) {
				cm.sendOk("#b请保证装备栏位至少有1个空格,否则无法兑换.");
				cm.dispose();
			} else if (cm.getInventory(2).isFull(0)) {
				cm.sendOk("#b请保证消耗栏位至少有1个空格,否则无法兑换.");
				cm.dispose();
			} else if (cm.getInventory(3).isFull(0)) {
				cm.sendOk("#b请保证设置栏位至少有1个空格,否则无法兑换.");
				cm.dispose();
			} else if (cm.getInventory(4).isFull(0)) {
				cm.sendOk("#b请保证其它栏位至少有1个空格,否则无法兑换.");
				cm.dispose();
			} else if (cm.getInventory(5).isFull(0)) {
				cm.sendOk("#b请保证现金栏位至少有1个空格,否则无法兑换.");
				cm.dispose();

			} else if (cm.haveItem(物品id, 物品数量) == false) { //判断是否领过
				cm.sendOk("你的物品不足！");
				cm.dispose();
			} else {
				cm.gainItem(物品id, -物品数量);
				cm.gainMeso(Math.floor(等级 * 30000 * 系数)); //给金币全局经验倍率
				cm.gainExp(Math.floor(9000 * 等级 * 等级 * 系数)); //给经验
				if (当前环数 == 3) {
					cm.gainNX(2000);
					cm.gainItem(2022336, 1);
				}
				if (当前环数 == 4) {
					cm.gainNX(3000);
					cm.gainItem(2022336, 2);
					cm.gainItem(5220010, 5);
					cm.gainItem(2049006, 1);
					cm.gainItem(4310019, 1);
					cm.getPlayer().addAccountExtraDamage(cm.getPlayer(), 20);
					cm.playerMessage("[每日跑环]附加伤害增加20");
				}
				cm.getPlayer().deleteBossLog("跑环状态");
				cm.setBossLog("每日跑环");
				cm.sendOk("恭喜你完成本环任务！");
				cm.dispose();
			}



		}
	}

}