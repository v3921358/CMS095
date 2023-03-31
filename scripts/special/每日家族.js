load(ServerConstants.SCRIPT_PAH+"/"+utils/db_functions.js");
var 心 = "#fUI/GuildMark.img/Mark/Etc/00009001/14#";
var 奖励 = "#fUI/UIWindow.img/Quest/reward#";
var 任务简述 = "#fUI/UIWindow.img/Quest/summary#";
var 金币 = "#fUI/UIWindow.img/QuestIcon/7/0#";
var 经验 = "#fUI/UIWindow.img/QuestIcon/8/0#";
var 无条件获得 = "#fUI/UIWindow.img/Quest/basic#";

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
		var 家族任务 = getFbLog("家族任务", 1);
		var 家族名称 = cm.getPlayer().getGuildName();
		if (getFbLog("" + 家族名称 + "", 2) > 0) {
			var 家族积分 = getFbLog("" + 家族名称 + "", 2);
		} else {
			var 家族积分 = 0;
		}
		if (cm.getPlayer().getGuild() == null) {
			cm.sendSimple("你还没有家族呢!快去加入一个家族吧！");
			return;
		}
		var 家族等级 = (cm.getPlayer().getGuild().getCapacity() - 10) / 5 + 2;
		var rankinfo_list = cm.getBosslogDCidTop("" + 家族名称 + "");
		var jiangli_list = 家族任务奖励(家族等级);
		var 等级 = cm.getPlayer().getLevel();
		if (status == 0) {
			var text = "";
			text += "\t\t\t" + 心 + "  " + 心 + " #r#e < 家族任务 > #k#n " + 心 + "  " + 心 + "\r\n\r\n";
			if (cm.getPlayer().getGuild() == null) {
				text += "你还没有家族呢!快去加入一个家族吧！";
			} else {
				text += "Hi~#b" + cm.getPlayer().getGuildName() + "#k的一员\r\n\r\n";
				text += "\t当前家族等级：#r" + 家族等级 + "#k 级";
				text += "\t\t当前的家族积分：#r" + 家族积分 + "#k 分\r\n\r\n";
				text += "" + 任务简述 + "\r\n"
				text += "●收集物资提交给我，当物资收集满后即可领取奖励。\r\n"
				text += "●#r家族成员每人每天仅可提交一次。#k\r\n"
				text += "●今日所需家族物资：#k\r\n"
				text += "\r\n\t\t\t#v" + Wzname(家族任务) + "# #z" + Wzname(家族任务) + "# [ " + 家族等级 * 1000 + " / #r" + rankinfo_list.length * 200 + "#k ]\r\n\r\n";
				text += "\r\n" + 奖励 + "\r\n"
				text += "" + 经验 + " " + (等级 * 等级 * 等级 * 2) + "\r\n" + 金币 + " " + (等级 * 3000 * 2) + "\r\n";
				text += "" + 无条件获得 + "";
				text += "\r\n#v4002003##z4002003#×1  #v4310019##z4310019#×1";
				if (jiangli_list.length == 6) {
					text += "\r\n#v" + jiangli_list[0] + "##z" + jiangli_list[0] + "# * " + jiangli_list[1] + " #v" + jiangli_list[2] + "##z" + jiangli_list[2] + "# * " + jiangli_list[3] + "\r\n #v" + jiangli_list[4] + "##z" + jiangli_list[4] + "# * " + jiangli_list[5] + "#k\r\n\r\n"
				} else {
					text += "\r\n#v" + jiangli_list[0] + "##z" + jiangli_list[0] + "# * " + jiangli_list[1] + " #v" + jiangli_list[2] + "##z" + jiangli_list[2] + "# * " + jiangli_list[3] + "#k\r\n\r\n"
				}
				if (家族等级 * 1000 > rankinfo_list.length * 200) {
					text += "#L1##d#e提交家族物资#v" + Wzname(家族任务) + "# #z" + Wzname(家族任务) + "# * 200#n#k#l\r\n";
					text += "#L2##d#e提交家族物资#v" + Wzname(家族任务) + "# #z" + Wzname(家族任务) + "# * 400#n#k#l\r\n";
					text += " ";
				} else {
					text += "#L3##d#e领取家族任务奖励！#n#k#l\r\n";
					text += " ";
				}
			}

			cm.sendSimple(text);
		} else if (selection == 1) {
			if (cm.getPlayer().getBossLogD("" + 家族名称 + "") > 0) {
				cm.sendOk("每天只能提交一次家族物资！");
				cm.dispose();
				return;
			}
			if (cm.itemQuantity(Wzname(家族任务)) < 200) {
				cm.sendOk("你的#v" + Wzname(家族任务) + "# #z" + Wzname(家族任务) + "#不足200个！");
				cm.dispose();
				return;
			}
			cm.gainItem(Wzname(家族任务), -200);
			cm.gainGP(60);
			cm.setBossLog("" + 家族名称 + "");
			setFbLog("" + 家族名称 + "", 2, 1);
			cm.sendOk("恭喜你提交物资成功！");
			cm.dispose();

		} else if (selection == 2) {
			if (cm.getPlayer().getBossLogD("" + 家族名称 + "") > 0) {
				cm.sendOk("每天只能提交一次家族物资！");
				cm.dispose();
				return;
			}
			if (cm.itemQuantity(Wzname(家族任务)) < 400) {
				cm.sendOk("你的#v" + Wzname(家族任务) + "# #z" + Wzname(家族任务) + "#不足200个！");
				cm.dispose();
				return;
			}
			cm.gainItem(Wzname(家族任务), -400);
			cm.gainGP(120);
			cm.setBossLog("" + 家族名称 + "");
			cm.setBossLog("" + 家族名称 + "");
			setFbLog("" + 家族名称 + "", 2, 2);
			cm.sendOk("恭喜你提交物资成功！");
			cm.dispose();
		} else if (selection == 3) {
			if(cm.getPlayer().getLevel()<70){
				cm.sendOk("角色等级够70级才可以领取家族任务哦。");
				cm.dispose();
				return;
			}
			if (cm.getInventory(1).isFull()) {
				cm.sendOk("请保证 #b装备栏#k 至少有2个位置。");
				cm.dispose();
				return;
			} else if (cm.getInventory(2).isFull()) {
				cm.sendOk("请保证 #b消耗栏#k 至少有2个位置。");
				cm.dispose();
				return;
			} else if (cm.getInventory(3).isFull()) {
				cm.sendOk("请保证 #b设置栏#k 至少有2个位置。");
				cm.dispose();
				return;
			} else if (cm.getInventory(4).isFull()) {
				cm.sendOk("请保证 #b其他栏#k 至少有2个位置。");
				cm.dispose();
				return;
			} else if (cm.getInventory(5).isFull()) {
				cm.sendOk("请保证 #b特殊栏#k 至少有2个位置。");
				cm.dispose();
				return;
			}
			var next = true;
			var 角色ID = cm.getPlayer().getId();
			var fee_list = getAccCid(角色ID);
			for (var i = 0; i < fee_list.length; i++) {
				if (获取BossLog("领取家族奖励", fee_list[i]) > 0) {
					next = false;
					break;
				}
			}
			if (next == false) {
				cm.sendOk("当前账号下已经有账号领取过了！");
				cm.dispose();
				return;
			}
			if (cm.getPlayer().getBossLogD("领取家族奖励") > 0) {
				cm.sendOk("每天只能领取一次家族任务奖励！");
				cm.dispose();
				return;
			}
			if (jiangli_list.length == 6) {
				cm.gainItem(jiangli_list[4], jiangli_list[5]);
			}
			cm.gainItem(jiangli_list[0], jiangli_list[1]);
			cm.gainItem(jiangli_list[2], jiangli_list[3]);
			cm.gainItem(4002003,1);//邮票
			cm.gainItem(4310019,1);//BOSS抽奖币
			cm.gainExp(等级 * 等级 * 等级 * 2);
			cm.gainMeso(等级 * 3000 * 2);
			cm.getPlayer().addAccountExtraDamage(cm.getPlayer(),50);
			cm.playerMessage("[每日组队]附加伤害增加50");
			cm.worldMessage(5, "【家族任务】恭喜" + 家族名称 + "的家族成员: " + cm.getPlayer().getName() + " 完成家族每日任务，成功领取奖励。");
			cm.setBossLog("领取家族奖励");
			cm.dispose();
		}
	}
}


function Wzname(ints) {
	if (ints == 1||ints == 0) {
		return 4000000
	} else if (ints == 2) {
		return 4000001
	} else if (ints == 3) {
		return 4000002
	} else if (ints == 4) {
		return 4000003
	} else if (ints == 5) {
		return 4000004
	} else if (ints == 6) {
		return 4000006
	}
}

function 家族任务奖励(ints) {
	if (ints == 2) {
		return [5062000, 5, 2022336, 1]
	} else if (ints == 3) {
		return [5062000, 6, 2022336, 1, 5220010, 1]
	} else if (ints == 4) {
		return [5062000, 7, 2022336, 1, 5220010, 1]
	} else if (ints == 5) {
		return [5062000, 8, 2022336, 2, 5220010, 1]
	} else if (ints == 6) {
		return [5062000, 9, 2022336, 2, 5220010, 2]
	} else if (ints == 7) {
		return [5062000, 10, 2022336, 2, 5220010, 2]
	} else if (ints == 8) {
		return [5062000, 11, 2022336, 3, 5220010, 2]
	} else if (ints == 9) {
		return [5062000, 12, 2022336, 3, 5220010, 3]
	} else if (ints == 10) {
		return [5062000, 13, 2022336, 3, 5220010, 3]
	} else if (ints == 11) {
		return [5062001, 14, 2022336, 3, 5220010, 3]
	} else if (ints == 12) {
		return [5062001, 15, 2022336, 4, 5220010, 3]
	} else if (ints == 13) {
		return [5062001, 15, 2022336, 4, 5220010, 4]
	} else if (ints == 14) {
		return [5062001, 15, 2022336, 5, 5220010, 4]
	} else if (ints == 15) {
		return [5062001, 15, 2022336, 6, 5220010, 4]
	} else if (ints == 16) {
		return [5062001, 15, 2022336, 7, 5220010, 4]
	} else if (ints == 17) {
		return [5062001, 15, 2022336, 8, 5220010, 4]
	} else if (ints == 18) {
		return [5062001, 15, 2022336, 9, 5220010, 4]
	} else if (ints == 19) {
		return [5062001, 15, 2022336, 10, 5220010, 4]
	} else if (ints == 20) {
		return [5062001, 15, 2022336, 10, 5220010, 5]
	}
}