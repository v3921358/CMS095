var 心 = "#fUI/GuildMark.img/Mark/Etc/00009001/14#";
var 奖励 = "#fUI/UIWindow.img/Quest/reward#";
var 金币 = "#fUI/UIWindow.img/QuestIcon/7/0#";
var 经验 = "#fUI/UIWindow.img/QuestIcon/8/0#";
var 获得 = "#fUI/UIWindow.img/QuestIcon/4/0#";
var 几率获得 = "#fUI/UIWindow.img/Quest/prob#";
var 无条件获得 = "#fUI/UIWindow.img/Quest/basic#";
var 任务简述 = "#fUI/UIWindow.img/Quest/summary#";
var weekday = new Date().getDay();

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
		if (weekday.value % 2 == 0 && weekday.value % 3 != 0) {
			var 副本1 = 2;
			var 副本2 = 4;
			var 副本3 = 6;

		} else if (weekday.value % 3 == 0) {
			var 副本1 = 3;
			var 副本2 = 5;
			var 副本3 = 8;
		} else {
			var 副本1 = 1;
			var 副本2 = 7;
			var 副本3 = 3;
		}
		var 副本1次数 = 1;
		var 副本2次数 = 1;
		var 副本3次数 = 1;
		var 等级 = cm.getPlayer().getLevel();

		if (status == 0) {
			var text = "";
			text += "\t\t\t" + 心 + "  " + 心 + " #r#e < 副本挑战 > #k#n " + 心 + "  " + 心 + "\r\n\r\n";
			text += "   hi #b#h ##k 欢迎来到#r副本任务挑战#k，任务无需领取，只需要完成指定次数的组队副本即可领取奖励，快去集结团队开始挑战吧！\r\n";
			text += "\r\n" + 任务简述 + ":\r\n\r\n";
			text += "完成 " + Fbname(副本1) + " 副本 #r " + cm.getBossLogD(Fbname(副本1)) + "#k / " + 副本1次数 + " 次\r\n";
			if (副本2 > 0) {
				text += "完成 " + Fbname(副本2) + " 副本 #r " + cm.getBossLogD(Fbname(副本2)) + "#k / " + 副本2次数 + " 次\r\n";
			}
			if (副本3 > 0) {
				text += "完成 " + Fbname(副本3) + " 副本 #r " + cm.getBossLogD(Fbname(副本3)) + "#k / " + 副本3次数 + " 次\r\n";
			}
			text += "\r\n" + 奖励 + "\r\n\r\n"
			text += "" + 经验 + " " + (等级 * 等级 * 等级 * 3.2) + "\r\n" + 金币 + " " + (等级 * 3000 * 3.2) + "\r\n";
			text += "" + 无条件获得 + "\r\n#v5220010##z5220010#×3 #v2022336##z2022336#×5\r\n#v5062001##z5062001#×2 #v4002003##z4002003#×1\r\n#v2049116##z2049116#×2 #v2049006##z2049006#×1\r\n"
			text += "" + 几率获得 + "\r\n#r#v4001465##z4001465##v2028062##z2028062##k\r\n\r\n"
			//text += "#n#r(领取奖励前请清理背包,确保有足够空间!)";//	
			text += "#L1##d#e我已经完成所有副本,领取奖励!#n#l\r\n"; //
			text += " ";
			cm.sendSimple(text);
		} else if (selection == 1) {
			if (cm.getPlayer().getBossLogD("MRFB") > 0) {
				cm.sendOk("每个账号每天只能领取1次奖励");
				cm.dispose();
			} else if (cm.getBossLogD(Fbname(副本1)) >= 副本1次数 && cm.getBossLogD(Fbname(副本2)) >= 副本2次数 && cm.getBossLogD(Fbname(副本3)) >= 副本3次数) {
				if (cm.getInventory(1).isFull(4)) {
					cm.sendOk("请保证 #b装备栏#k 至少有5个位置。");
					cm.dispose();
					return;
				} else if (cm.getInventory(2).isFull(4)) {
					cm.sendOk("请保证 #b消耗栏#k 至少有5个位置。");
					cm.dispose();
					return;
				} else if (cm.getInventory(3).isFull(4)) {
					cm.sendOk("请保证 #b设置栏#k 至少有5个位置。");
					cm.dispose();
					return;
				} else if (cm.getInventory(4).isFull(4)) {
					cm.sendOk("请保证 #b其他栏#k 至少有5个位置。");
					cm.dispose();
					return;
				} else if (cm.getInventory(5).isFull(4)) {
					cm.sendOk("请保证 #b特殊栏#k 至少有5个位置。");
					cm.dispose();
					return;
				}
				cm.gainMeso(等级 * 3000 * 3.2);
				cm.gainExp(等级 * 等级 * 等级 * 3.2);
				cm.gainItem(5220010, 3);
				cm.gainItem(2022336, 5);
				cm.gainItem(5062001, 2);
				cm.gainItem(4002003, 1);
				cm.gainItem(2049006, 1);
				cm.gainItem(2049116, 2);
				cm.getPlayer().addAccountExtraDamage(cm.getPlayer(), 1000);
				cm.playerMessage("[每日组队]附加伤害增加1000");
				var randomX = Math.floor(Math.random() * 5) + 1;
				var randomY = Math.floor(Math.random() * 20) + 1;
				if (randomX == 3) {
					cm.gainItem(4001465, 10);
				}
				if (randomY == 1) {
					cm.gainItem(2028062, 1);
				}
				cm.getPlayer().setBossLog("MRFB");
				cm.sendOk("成功领取奖励！");
				cm.dispose();
				cm.worldMessage(2, "玩家：[" + cm.getPlayer().getName() + "]完成了每日副本，领取了奖励！");
			} else {
				cm.sendOk("你还没有完成所有每日副本\r\n");
				cm.dispose();
			}
		}
	}
}


function Fbname(ints) {
	if (ints == 1) {
		return "月秒的年糕"
	} else if (ints == 2) {
		return "废弃下水道"
	} else if (ints == 3) {
		return "玩具塔101"
	} else if (ints == 4) {
		return "毒物森林"
	} else if (ints == 5) {
		return "女神塔"
	} else if (ints == 6) {
		return "海盗船"
	} else if (ints == 7) {
		return "罗密欧与朱丽叶"
	} else if (ints == 8 || ints == 0) {
		return "御龙魔"
	}
}