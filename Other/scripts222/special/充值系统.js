/*
 ZEVMS冒险岛(079)游戏服务端
 脚本：游戏CDK兑换系统
 使用函数：
 给个人记录
 cm.setBossRankCount("点券积分", fee);
 */
var ca = java.util.Calendar.getInstance();
var year = ca.get(java.util.Calendar.YEAR);
var month = ca.get(java.util.Calendar.MONTH) + 1;
var day = ca.get(java.util.Calendar.DATE);
var hour = ca.get(java.util.Calendar.HOUR_OF_DAY);
var minute = ca.get(java.util.Calendar.MINUTE);
var second = ca.get(java.util.Calendar.SECOND);
var weekday = ca.get(java.util.Calendar.DAY_OF_WEEK);
var kx1 = "#fEffect/CharacterEff/1112925/0/2#";//空星
var hot = "#fUI/CashShop.img/CSEffect/hot/0#";
var 打开 = "#fUI/UIWindow.img/CashGachapon/BtOpen/disabled/0#";
//玩家充值点券，反馈给推广员的百分比点券
var 推广员反馈百分比 = 10;


var status = 0;
var 心 = "#fUI/GuildMark.img/Mark/Etc/00009001/14#";
var fee;
var chance = Math.floor(Math.random() * 1);
function start() {
    status = -1;
    action(1, 0, 0);
}

function isNull( str ){
	if ( str == "" ) {
		return true;
	}
	var regu = "^[ ]+$";
	var re = new RegExp(regu);
	return re.test(str);
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
			var txt = "\t#b=================#e [充值系统] #n==================#k\r\n\r\n";
			txt += "\t#d感谢您对PPMS怀旧服的支持，本服不定期发放CDK#k\r\n";
			txt += "\t#d在获取Cdk后，回到我这里兑换，兑换成功后即可获得对应物品。#k\r\n\r\n"
			//txt += "#b#L1#打开充值网站\r\n\r\n";
			txt += "#L2#Cdk码兑换#l\r\n\r\n";
			txt += "#L3#累计充值奖励#l#k#n\r\n\r\n";
			//txt += "#L4#"+hot+"#e#r超值礼包，性价比200%#k#n#l\r\n";
			txt += " ";
            cm.sendOk(txt);
        } else if (status == 1) {
			if (selection == 1) {
				cm.打开网页("http://ww.dofaka.cn/category/912AA4A6FE46D0B4");
				cm.dispose();
			} else if (selection == 2) {
				cm.dispose();
				cm.openNpc(9900004,"CDK兑换");
			} else if (selection == 3) {
				cm.dispose();
				cm.openNpc(9900004,"累计充值奖励");
			} else if (selection == 4) {
				cm.dispose();
				cm.openNpc(9900004,"超值礼包");
			}
		}	
    }
}
