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
		var txt = "";
		txt += "   \t  " + 心2 + "   " + 心2 + "  #r#e < 考古学家套装礼包 > #k#n  " + 心2 + "   " + 心2 + "\r\n\r\n";
		txt += "  #e以下是#b考古学家套装礼包#k奖品，直接提升#r500点#k战斗力，#r一生#k限购#r一次#k，仅售#r68#k，是否领取？#k\r\n\r\n"
		txt += ""+奖励+"\r\n"
		txt += "\t"+红色箭头+" #v1003225##z1003225##v1003225#\r\n\r\n"; 
		txt += "\t"+红色箭头+" #v1012260##z1012260##v1012260#\r\n\r\n"; 
		txt += "\t"+红色箭头+" #v1022118##z1022118##v1022118#\r\n\r\n"; 
		txt += "\t"+红色箭头+" #v5200002# 20400 点券 #r多赠50%#k #v5200002#\r\n\r\n"; 
		txt += "\t"+红色箭头+" #v2000004# 50 个 #z2000004# #v2000004#\r\n\r\n"; 
		txt += "\t"+红色箭头+" #v2028048# 10 个 #z2028048# #v2028048#\r\n\r\n"; 
		txt += "\t"+红色箭头+" #v5451001# 30 个 #z5451001# #v5451001#\r\n\r\n"; 
		cm.sendYesNo(txt);
	} else if (status == 1) {
		cm.打开网页("http://ww.dofaka.cn/category/912AA4A6FE46D0B4");
		cm.dispose();
	}
}
}
