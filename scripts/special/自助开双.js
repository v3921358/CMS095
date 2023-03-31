/*
	PP自制脚本，自助开双
**/
var 心2 = "#fUI/GuildMark.img/Mark/Etc/00009001/15#";
var 奖励 = "#fUI/UIWindow.img/Quest/reward#";
var 人气王 = "#fUI/UIWindow.img/QuestIcon/6/0#";
var 五角星 = "#fUI/UIWindow.img/UserList/Expedition/icon14#";
var 红色箭头 = "#fEffect/CharacterEff/1112908/0/1#";  //彩光3
var status = 0;



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
	if (status == 0) {
		var txt = "";
		txt += "   \t\t  " + 心2 + "   " + 心2 + "  #d#e < 自助双倍 > #k#n  " + 心2 + "   " + 心2 + "\r\n\r\n";
		txt += "\tHi~#b#h ##k，这里是#b自助双倍经验系统#k，如果你有足够的点券或抵用券的话，可以给全服开启双倍经验哦！另外，给全服开双的大佬将获得以下福利：\r\n"
		txt += ""+奖励+"\r\n"
		txt += "\t"+人气王+" 20点\t\t #v1122017##z1122017#（12小时权）\r\n\r\n";
		txt += "#L1#给全服开双 1 小时 （需要1万点券）\r\n\r\n";
		txt += "#L2#给全服开双 1 小时 （需要2万抵用券）";
		cm.sendOk(txt);
	} 
	if (status == 1) {
		if (selection == 1) {
			if (cm.getPlayer().getCSPoints(1) > 10000){
			var em = cm.getEventManager("Autodbexp");
			if (em == null) {
				cm.sendOk("发生未知错误,请稍后再试....");
			} else {
				var prop = em.getProperty("state");
				if (prop.equals("0") || prop == null) {
					em.startInstance();
					cm.gainNX(-10000);
					cm.gainItemPeriodF(1122017,1,720);
					var 人气 = cm.getPlayer().getFame();
					cm.getPlayer().setFame(人气+20);
					cm.worldMessage(1, "【双倍活动】\r\n" + cm.getChar().getName() + "已为全服开启双倍经验活动，持续1小时，要升级的赶紧了！");
					cm.dispose();
					return;
				} else {
					cm.sendOk("目前正在系统双倍时间中...");
				}
			}
			} else {
				cm.sendOk("你的点券不足.");
				cm.dispose();
			}
			
		} else if (selection == 2) {
			if (cm.getPlayer().getCSPoints(2) > 20000){
			var em = cm.getEventManager("Autodbexp");
			if (em == null) {
				cm.sendOk("发生未知错误,请稍后再试....");
			} else {
				var prop = em.getProperty("state");
				if (prop.equals("0") || prop == null) {
					em.startInstance();
					cm.gainNX2(-20000);
					cm.gainItem(1122017,1,1);
					var 人气 = cm.getPlayer().getFame();
					cm.getPlayer().setFame(人气+20);
					cm.worldMessage(1, "【双倍活动】\r\n" + cm.getChar().getName() + "已为全服开启双倍经验活动，持续1小时，要升级的赶紧了！");
					cm.dispose();
					return;
				} else {
					cm.sendOk("目前正在系统双倍时间中...");
				}
			}
		} else {
			cm.sendOk("你的抵用券不足.");
			cm.dispose();
		}
	}
	}
}
}
