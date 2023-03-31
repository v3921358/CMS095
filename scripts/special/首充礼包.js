/*
	PP自制脚本，自助开双
**/
var 心2 = "#fUI/GuildMark.img/Mark/Etc/00009023/1#";
var 奖励 = "#fUI/UIWindow.img/Quest/reward#";
var 人气王 = "#fUI/UIWindow.img/UserList/Expedition/icon12#";
var 五角星 = "#fUI/UIWindow.img/UserList/Expedition/icon14#";
var 红色箭头 = "#fUI/UIWindow/Quest/icon6/7#";
var status = 0;
function start() {
	action( 1, 0, 0);

}


function start() {
     status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (mode == 0) {
            cm.dispose();
            return;
        }
        if (mode == 1) {
            status++;
        } else {
            status--;
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
		var 充值积分 = cm.getBossRankCount9("充值积分");
		var 每日领取 = cm.getBossLogD("领取首充礼包");
		var 领取天数 = cm.getPlayer().getBossLogS("领取首充礼包");
	if (status == 0) {
		var txt = "";
		txt += "   \t\t  " + 心2 + "   " + 心2 + "  #r#e < 首充礼包 > #k#n  " + 心2 + "   " + 心2 + "\r\n\r\n";
		txt += "  #e这里是#b首充礼包领取专区#k，充值#r任意金额点券#k即可领取#r一次性解决宠物、装备、金币、潜能等问题#k\r\n\r\n"
		txt += ""+奖励+"\r\n"
		txt += "第一天 "+红色箭头+" #v5000024#  #v1052166##r全属性30#k  #v2028048#  #v5451001#";
		if(领取天数 == 0){
		txt += "#r#e#L1#未领取#l#k";
		} else if (充值积分 > 0 && 领取天数 > 0) {
		txt += "已领取#l";
		}
		txt += "\r\n\r\n";
		txt += "第二天 "+红色箭头+" #v2049401#  #v5062000#  #v2460002#";
		if(充值积分 > 0 && 领取天数 == 1){
		txt += "#r#e#L2#未领取#l#k";
		} else if (充值积分 > 0 && 领取天数 > 1) {
		txt += "已领取#l";
		}
		txt += "\r\n\r\n";
		txt += "第三天 "+红色箭头+" #v2049400#  #v5062001#  #v2460003#";
		if(充值积分 > 0 && 领取天数 == 2){
		txt += "#r#e#L3#未领取#l#k\r\n\r\n";
		} else if (充值积分 > 0 && 领取天数 > 2) {
		txt += "已领取#l\r\n\r\n";
		}
		cm.sendOk(txt);
	} else if (status == 1) {
		if (selection == 1) {
			if (充值积分 > 0){
				cm.gainItem(5000024,1,31);
				cm.gainItemPeriodB(1052165,1,5,30,30,30,30,30,30,0);
				cm.gainItem(5220010,10);
				cm.gainItem(2028048,5);
				cm.gainItem(5451001,20);
				cm.setBossLog("领取首充礼包");
				cm.sendOk("恭喜你领取成功！");
				cm.worldMessage(5,"【首充礼包】恭喜玩家: " + cm.getPlayer().getName() + " 成功领取首充礼包,获得超值大礼。");
				cm.dispose();
			} else {
				cm.sendYesNo("你的充值积分不足，是否需要充值？");
			}
		} else if (selection == 2) {
			if (每日领取 > 0) {
				cm.sendOk("你今天已经领取过了，请明天再来");
				cm.dispose();
			} else {
				cm.gainItem(2049401,1);
				cm.gainItem(5062000,5);
				cm.gainItem(2460002,5);
				cm.setBossLog("领取首充礼包");
				cm.sendOk("恭喜你领取成功！");
				cm.worldMessage(5,"【首充礼包】恭喜玩家: " + cm.getPlayer().getName() + " 成功领取首充礼包,获得超值大礼。");
				cm.dispose();
			}
		} else if (selection == 3) {
			if (每日领取 > 0) {
				cm.sendOk("你今天已经领取过了，请明天再来");
				cm.dispose();
			} else {
				cm.gainItem(2049400,1);
				cm.gainItem(5062001,2);
				cm.gainItem(2460003,2);
				cm.setBossLog("领取首充礼包");
				cm.sendOk("恭喜你领取成功！");
				cm.worldMessage(5,"【首充礼包】恭喜玩家: " + cm.getPlayer().getName() + " 成功领取首充礼包,获得超值大礼。");
				cm.dispose();
			}
		}
		
	} else if (status == 2){
		cm.dispose();
		cm.打开网页("http://ww.dofaka.cn/category/912AA4A6FE46D0B4");
	}
}
}
