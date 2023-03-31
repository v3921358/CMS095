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
	if (status == 0) {
		var 职业 = cm.getPlayer().getJob();
		var txt = "";
		txt += "   \t  " + 心2 + "   " + 心2 + "  #r#e < 漫步新月套装礼包 > #k#n  " + 心2 + "   " + 心2 + "\r\n\r\n";
		txt += "  #e以下是#b漫步新月套装礼包#k奖品，直接提升#r5000点#k战斗力，#r一生#k限购#r一次#k，仅售#r198#k，是否领取？#k\r\n\r\n"
		txt += ""+奖励+"\r\n"
		if ((职业 >= 100 && 职业 <= 132) || (职业 >= 2000 && 职业 <= 2112) || (职业 >= 1100 && 职业 <= 1112)) {
			txt += "\t"+红色箭头+" #v1003197##z1003197##v1003197#\r\n\r\n"; 
			txt += "\t"+红色箭头+" #v1052333##z1052333##v1052333#\r\n\r\n"; 
			txt += "\t"+红色箭头+" #v1072502##z1072502##v1072502#\r\n\r\n"; 
			txt += "\t"+红色箭头+" #v1082305##z1082305##v1082305#\r\n\r\n"; 
		}
		if ((职业 >= 200 && 职业 <= 232) || (职业 >= 2200 && 职业 <= 2218) || (职业 >= 3200 && 职业 <= 3212) || (职业 >= 1200 && 职业 <= 1212)) {
			txt += "\t"+红色箭头+" #v1003198##z1003198##v1003198#\r\n\r\n"; 
			txt += "\t"+红色箭头+" #v1052334##z1052334##v1052334#\r\n\r\n"; 
			txt += "\t"+红色箭头+" #v1072503##z1072503##v1072503#\r\n\r\n"; 
			txt += "\t"+红色箭头+" #v1082306##z1082306##v1082306#\r\n\r\n"; 
		}
		if ((职业 >= 300 && 职业 <= 322) || (职业 >= 3300 && 职业 <= 3312) || (职业 >= 1300 && 职业 <= 1312)) {
			txt += "\t"+红色箭头+" #v1003199##z1003199##v1003199#\r\n\r\n"; 
			txt += "\t"+红色箭头+" #v1052335##z1052335##v1052335#\r\n\r\n"; 
			txt += "\t"+红色箭头+" #v1072504##z1072504##v1072504#\r\n\r\n"; 
			txt += "\t"+红色箭头+" #v1082307##z1082307##v1082307#\r\n\r\n"; 
		}
		if ((职业 >= 400 && 职业 <= 434) || (职业 >= 1400 && 职业 <= 1412)) {
			txt += "\t"+红色箭头+" #v1003200##z1003200##v1003200#\r\n\r\n"; 
			txt += "\t"+红色箭头+" #v1052336##z1052336##v1052336#\r\n\r\n"; 
			txt += "\t"+红色箭头+" #v1072505##z1072505##v1072505#\r\n\r\n"; 
			txt += "\t"+红色箭头+" #v1082308##z1082308##v1082308#\r\n\r\n"; 
		}
		if ((职业 >= 500 && 职业 <= 522) || (职业 >= 3500 && 职业 <= 3512)|| (职业 >= 1500 && 职业 <= 1512)) {
			txt += "\t"+红色箭头+" #v1003201##z1003201##v1003201#\r\n\r\n"; 
			txt += "\t"+红色箭头+" #v1052336##z1052336##v1052336#\r\n\r\n"; 
			txt += "\t"+红色箭头+" #v1072505##z1072505##v1072505#\r\n\r\n"; 
			txt += "\t"+红色箭头+" #v1082308##z1082308##v1082308#\r\n\r\n"; 
		}
		txt += "\t"+红色箭头+" #v1112585##z1112585##v1112585#\r\n\r\n"; 
		txt += "\t"+红色箭头+" #v1032092##z1032092##v1032092#\r\n\r\n"; 
		txt += "\t"+红色箭头+" #v1112583##z1112583##v1112583#\r\n\r\n"; 
		txt += "\t"+红色箭头+" #v1132084##z1132084##v1132084#\r\n\r\n"; 
		txt += "\t"+红色箭头+" #v5200002# 59400 点券 #r多赠50%#k #v5200002#\r\n\r\n"; 
		txt += "\t"+红色箭头+" #v2000005# 200 个 #z2000005# #v2000005#\r\n\r\n"; 
		txt += "\t"+红色箭头+" #v2028048# 50 个 #z2028048# #v2028048#\r\n\r\n"; 
		txt += "\t"+红色箭头+" #v5220010# 30 个 #z5220010# #v5220010#\r\n\r\n"; 
		cm.sendYesNo(txt);
	} else if (status == 1) {
		cm.打开网页("http://ww.dofaka.cn/category/912AA4A6FE46D0B4");
		cm.dispose();
	}
}
}
