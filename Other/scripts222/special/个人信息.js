var jt = "#fUI/Basic/BtHide3/mouseOver/0#";
var 心 = "#fEffect/CharacterEff/1112925/0/1#"; //空星
var 心1 = "#fUI/GuildMark.img/Mark/Etc/00009001/14#";
var 心2 = "#fUI/GuildMark.img/Mark/Etc/00009001/15#";
var 头像 = "#fUI/UIWindow.img/MobGage/Mob/2220000#";
var sl0items = null;
var character_auctionItems = null;
var select_type_sell_auctionItems = null;
var auctionPoint = null;

function start() {
	status = -1;
	action(1, 0, 0);
}

function action(mode, type, selection) {
	if (status == 0 && mode == 0) {
		cm.dispose();
		
		return;
	}
	if (mode == 1) {
		status++;
	} else {
		status--;
	}
	var 角色 = cm.getPlayer().id;
	if (cm.getBossRank9("推广积分", 2) < 0) {
		var 推广积分 = 0;
	} else {
		var 推广积分 = cm.getBossRank("推广积分", 2);
	}

	if (status == 0) {
		var player = cm.getPlayer();
		var zzjf = player.getBossRankCount9("充值积分") == -1 ? 0 : player.getBossRankCount9("充值积分")
		var selStr = "   \t\t  " + 心 + "   " + 心 + "  #d#e < 个人信息 > #k#n  " + 心 + "   " + 心 + "\r\n\r\n";
		selStr += "\t角色名字 ： #k#r" + cm.getChar().getName() + "#k\r\n";
		selStr += "\t角色职业 ： #k#r" + 职业() + "#k\r\n";
		selStr += "\t角色ID ： #k#r" + 角色 + "#k\r\n";
		selStr += "\t在线时间 ： #k#r" + cm.getTodayOnlineTime() + " #k分钟\r\n";
		selStr += "\t附加破攻伤害 ： #k#r" + player.getChrExtraDamage(player) + " #k点\r\n";
		selStr += "\t破攻上限： #k#r服务器破攻上限200E已开启 #k\r\n";
		selStr += "\t角色面板伤害：#k#r"+ player.getStat().getCurrentMaxBaseDamage()+"#k点\r\n\r\n";
	 
		
		
		
		
		
		selStr += "   \t\t  " + 心 + "   " + 心 + "  #d#e < 我的财富 > #k#n  " + 心 + "   " + 心 + "\r\n\r\n";
		selStr += "\t累计赞助 ： #k#r" + zzjf + "#k\r\n";
		selStr += "\t角色金币 ： #k#r" + player.getMeso() + "#k\r\n";
		selStr += "\t账户点券 ： #k#r" + player.getCSPoints(1) + "#k\r\n";
		selStr += "\t账户抵用 ： #k#r" + player.getCSPoints(2) + "#k\r\n";
		selStr += "\t推广积分 ： #k#r" + 推广积分 + "#k\r\n";
        selStr += "#r#L2#新手礼包补领#l";

		//战斗力评估
		//selStr += "\r\n\t#d#e战斗力评估#n#k\r\n"+cm.getPlayer().getEquippedFuMoMap()+"";
		//selStr += ""+cm.显示战斗力()+"\r\n";

		//显示潜能属性

		//显示角色生涯
		//selStr += "\r\n\t#d#e角色生涯#k#n\r\n";
		cm.sendSimple(selStr);

	} else if (status == 1) {
		switch (selection) {
			default:
				cm.dispose();
				cm.openNpc(9900004, 0);
				break;
				case 2:
                    cm.dispose();
                    cm.openNpc(9900004, "新人礼包");
                    break;

		}
	}
}



function 职业() {
	job = cm.getPlayer().getJob();
	if (job < 100) {
		result = "新手";
		return result;
	}
	if (job == 100) {
		result = "战士";
		return result;
	}
	if (job == 110) {
		result = "剑客";
		return result;
	}
	if (job == 111) {
		result = "勇士";
		return result;
	}
	if (job == 112) {
		result = "英雄";
		return result;
	}
	if (job == 120) {
		result = "准骑士";
		return result;
	}
	if (job == 121) {
		result = "骑士";
		return result;
	}
	if (job == 122) {
		result = "圣骑士";
		return result;
	}
	if (job == 130) {
		result = "枪战士";
		return result;
	}
	if (job == 131) {
		result = "龙骑士";
		return result;
	}
	if (job == 132) {
		result = "黑骑士";
		return result;
	}
	if (job == 200) {
		result = "魔法师";
		return result;
	}
	if (job == 210) {
		result = "法师(火，毒)";
		return result;
	}
	if (job == 211) {
		result = "巫师(火，毒)";
		return result;
	}
	if (job == 212) {
		result = "魔导师(火，毒)";
		return result;
	}
	if (job == 220) {
		result = "法师(冰，雷)";
		return result;
	}
	if (job == 221) {
		result = "巫师(冰，雷)";
		return result;
	}
	if (job == 222) {
		result = "魔导师(冰，雷)";
		return result;
	}
	if (job == 230) {
		result = "牧师";
		return result;
	}
	if (job == 231) {
		result = "祭司";
		return result;
	}
	if (job == 232) {
		result = "主教";
		return result;
	}
	if (job == 300) {
		result = "弓箭手";
		return result;
	}
	if (job == 310) {
		result = "猎手";
		return result;
	}
	if (job == 311) {
		result = "射手";
		return result;
	}
	if (job == 312) {
		result = "神射手";
		return result;
	}
	if (job == 320) {
		result = "弩弓手";
		return result;
	}
	if (job == 321) {
		result = "游侠";
		return result;
	}
	if (job == 322) {
		result = "箭神";
		return result;
	}
	if (job == 400) {
		result = "飞侠";
		return result;
	}
	if (job == 410) {
		result = "刺客";
		return result;
	}
	if (job == 411) {
		result = "无影人";
		return result;
	}
	if (job == 412) {
		result = "隐士";
		return result;
	}
	if (job == 420) {
		result = "侠客";
		return result;
	}
	if (job == 421) {
		result = "独行客";
		return result;
	}
	if (job == 422) {
		result = "侠盗";
		return result;
	}
	if (job == 430) {
		result = "见习刀客";
		return result;
	}
	if (job == 431) {
		result = "双刀客";
		return result;
	}
	if (job == 432) {
		result = "双刀侠";
		return result;
	}
	if (job == 433) {
		result = "血刀";
		return result;
	}
	if (job == 434) {
		result = "暗影双刀";
		return result;
	}
	if (job == 500) {
		result = "海盗";
		return result;
	}
	if (job == 510) {
		result = "拳手";
		return result;
	}
	if (job == 511) {
		result = "斗士";
		return result;
	}
	if (job == 512) {
		result = "冲锋队长";
		return result;
	}
	if (job == 520) {
		result = "火枪手";
		return result;
	}
	if (job == 521) {
		result = "大副";
		return result;
	}
	if (job == 522) {
		result = "船长";
		return result;
	}
	if (job == 1000) {
		result = "初心者";
		return result;
	}
	if (job == 1100) {
		result = "魂骑士";
		return result;
	}
	if (job == 1110) {
		result = "魂骑士";
		return result;
	}
	if (job == 1111) {
		result = "魂骑士";
		return result;
	}
	if (job == 1112) {
		result = "魂骑士";
		return result;
	}
	if (job == 1200) {
		result = "炎术士";
		return result;
	}
	if (job == 1210) {
		result = "炎术士";
		return result;
	}
	if (job == 1211) {
		result = "炎术士";
		return result;
	}
	if (job == 1212) {
		result = "炎术士";
		return result;
	}
	if (job == 1300) {
		result = "风灵使者";
		return result;
	}
	if (job == 1310) {
		result = "风灵使者";
		return result;
	}
	if (job == 1311) {
		result = "风灵使者";
		return result;
	}
	if (job == 1312) {
		result = "风灵使者";
		return result;
	}
	if (job == 1400) {
		result = "夜行者";
		return result;
	}
	if (job == 1410) {
		result = "夜行者";
		return result;
	}
	if (job == 1411) {
		result = "夜行者";
		return result;
	}
	if (job == 1412) {
		result = "夜行者";
		return result;
	}
	if (job == 1500) {
		result = "奇袭者";
		return result;
	}
	if (job == 1510) {
		result = "奇袭者";
		return result;
	}
	if (job == 1511) {
		result = "奇袭者";
		return result;
	}
	if (job == 1512) {
		result = "奇袭者";
		return result;
	}
	if (job == 2000) {
		result = "战童";
		return result;
	}
	if (job == 2100) {
		result = "战神";
		return result;
	}
	if (job == 2110) {
		result = "战神";
		return result;
	}
	if (job == 2111) {
		result = "战神";
		return result;
	}
	if (job == 2112) {
		result = "战神";
		return result;
	}
	if (job == 2001) {
		result = "小不点";
		return result;
	}
	if (job == 2200) {
		result = "龙神";
		return result;
	}
	if (job == 2210) {
		result = "龙神";
		return result;
	}
	if (job == 2211) {
		result = "龙神";
		return result;
	}
	if (job == 2212) {
		result = "龙神";
		return result;
	}
	if (job == 2213) {
		result = "龙神";
		return result;
	}
	if (job == 2214) {
		result = "龙神";
		return result;
	}
	if (job == 2215) {
		result = "龙神";
		return result;
	}
	if (job == 2216) {
		result = "龙神";
		return result;
	}
	if (job == 2217) {
		result = "龙神";
		return result;
	}
	if (job == 2218) {
		result = "龙神";
		return result;
	}
	if (job == 3000) {
		result = "预备兵";
		return result;
	}
	if (job == 3200) {
		result = "唤灵斗师";
		return result;
	}
	if (job == 3210) {
		result = "唤灵斗师";
		return result;
	}
	if (job == 3211) {
		result = "唤灵斗师";
		return result;
	}
	if (job == 3212) {
		result = "唤灵斗师";
		return result;
	}
	if (job == 3300) {
		result = "豹弩游侠";
		return result;
	}
	if (job == 3310) {
		result = "豹弩游侠";
		return result;
	}
	if (job == 3311) {
		result = "豹弩游侠";
		return result;
	}
	if (job == 3312) {
		result = "豹弩游侠";
		return result;
	}
	if (job == 3500) {
		result = "机械师";
		return result;
	}
	if (job == 3510) {
		result = "机械师";
		return result;
	}
	if (job == 3511) {
		result = "机械师";
		return result;
	}
	if (job == 3512) {
		result = "机械师";
		return result;
	}
}


function 生存() {
	var theTime = cm.总在线();
	var theTime1 = 0; //分
	var theTime2 = 0; //时
	var theTime3 = 0; //天
	if (theTime > 60) {
		theTime1 = parseInt(theTime / 60);
		theTime = parseInt(theTime % 60);
		if (theTime1 >= 24) {
			theTime2 = parseInt(theTime1 / 24);
			theTime1 = parseInt(theTime1 % 24);
		}
	}
	var result = "#r" + parseInt(theTime) + " #d分钟 ";
	if (theTime1 > 0) {
		result = "#r" + parseInt(theTime1) + " #d小时 " + result;
	}
	if (theTime2 > 0) {
		result = "#r" + parseInt(theTime2) + " #d天 " + result;
	}
	return result;
}