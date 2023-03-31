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
		txt += "   \t  " + 心2 + "   " + 心2 + "  #r#e < 三国名将套装礼包 > #k#n  " + 心2 + "   " + 心2 + "\r\n\r\n";
		txt += "  #e以下是#b三国名将套装礼包#k奖品，直接提升#r2000点#k战斗力，#r一生#k限购#r一次#k，仅售#r128#k，是否领取？#k\r\n\r\n"
		txt += ""+奖励+"\r\n"
		txt += "\t"+红色箭头+" #v1152050##z1152050##v1152050#\r\n\r\n"; 
		txt += "\t"+红色箭头+" #v1122105##z1122105##v1122105#\r\n\r\n"; 
		txt += "\t"+红色箭头+" #v1112596##z1112596##v1112596#\r\n\r\n"; 
		txt += "\t"+红色箭头+" #v1132086##z1132086##v1132086#\r\n\r\n"; 
		txt += "\t"+红色箭头+" #v5200002# 38400 点券 #r多赠50%#k #v5200002#\r\n\r\n"; 
		txt += "\t"+红色箭头+" #v2000005# 100 个 #z2000005# #v2000005#\r\n\r\n"; 
		txt += "\t"+红色箭头+" #v2028048# 30 个 #z2028048# #v2028048#\r\n\r\n"; 
		txt += "\t"+红色箭头+" #v5451001# 50 个 #z5451001# #v5451001#\r\n\r\n"; 
		cm.sendYesNo(txt);
	} else if (status == 1) {
		cm.打开网页("http://ww.dofaka.cn/category/912AA4A6FE46D0B4");
		cm.dispose();
	}
}
}
