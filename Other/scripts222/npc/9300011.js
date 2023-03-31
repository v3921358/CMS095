/* 
Author: pp
*/
var status = 0;
var weekday = new Date().getDay();
var hour = new Date().getHours();
var minute = new Date().getMinutes();
var seconds = new Date().getSeconds();
var weekday = new Date().getDay();
var 奖励 = "#fUI/UIWindow.img/Quest/reward#";
//周六奖品[金币，点券，[道具，数量]]
var sat = [5000000, 5000, [
	[4002001, 1],
	[4002002, 2],
	[4002003, 3]
]];
//周日
var sun = [[50000000, 5000, [
	[4002002, 100],

	[5062001, 20],
	[2028061, 50],
	[4021002, 10],
	[4021007, 10],
	[4001465, 20],
	[4002001, 3],
	[4031559, 30]
]],
[20000000, 2000, [
	[4002002, 100],
	[5062001, 20],
	[2028061, 50],
	[4011004, 10],
	[4011009, 10],
	[4021004, 10],
	[4001465, 10],
	[4002001, 2],
	[4031559, 20]
]],
[20000000, 2000, [
	[4002002, 100],
	[5062001, 20],
	[2028061, 50],
	[4021001, 10],
	[4011005, 10],
	[4001465, 10],
	[4002001, 2],
	[4031559, 20]
]],
[20000000, 2000, [
	[4002002, 100],
	[5062001, 20],
	[2028061, 50],
	[4011001, 10],
	[4021005, 10],
	[4001465, 10],
	[4002001, 2],
	[4031559, 20]
]],
[20000000, 2000, [
	[4002002, 100],
	[5062001, 20],
	[2028061, 50],
	[4011000, 10],
	[4011003, 10],
	[4001465, 10],
	[4002001, 2],
	[4031559, 20]
]],
[20000000, 2000, [
	[4002002, 100],
	[5062001, 20],
	[2028061, 50],
	[4005002, 10],
	[4011002, 10],
	[4021003, 10],
	[4001465, 10],
	[4002001, 2],
	[4031559, 20]
]],
[200000000, 20000, [
	[4002002, 200],
	[5062001, 40],

	[2028061, 100],
	[4011006, 20],
	[4021006, 20],
	[4001465, 30],
	[4002001, 5],
	[4031559, 50]
]]
];

function start() {
	status = -1;
	action(1, 0, 0);
}

function action(mode, type, selection) {
	if (status == 0 && mode == 0) {
		cm.dispose();
		return;
	} else if (status >= 1 && mode == 0) {
		cm.sendNext("每日时间8点-9点可以来找我。");
		cm.dispose();
		return;
	}
	if (mode == 1)
		status++;
	else
		status--;
	if (status == 0) {
		var a = "\r\n\r\n";
		if ((hour == 20 ) ) {//测试时候可以自己改
			a += "哟哟哟，每日愉快时间请收下奖励~~\r\n\r\n";
			a += "#e#d#L0#领取每日在线奖励#k#l\r\n"
		} else {
			a += "非活动时间哦!每日时间晚#b8点-9点#k可以来找我。\r\n\r\n#e#r#L1#我想看看每日在线奖励内容#l\r\n"
		}
		cm.sendSimple(a)
	} else if (status == 1) {
		var txt = "" + 奖励 + "\r\n"
            
		if(selection==0){
			if(cm.getPlayer().getAcLogD("每日奖励")>0){
				cm.sendOk("你的账号今天已经领取过啦~");
				cm.dispose();
				return;
			}
			if(cm.getPlayer().getLevel()<120){
				cm.sendOk("角色等级不足120~");
				cm.dispose();
				return;
			}
			if(!cm.canHoldSlots(3)){
				cm.sendOk("背包有点满，清理一下~");
				cm.dispose();
				return;
			}
				cm.gainMeso(sun[weekday][0]);
				txt += "\t#v5200002# "+sun[weekday][0]+" 金币 \r\n";
				cm.gainNX(sun[weekday][1]);
				txt += "\t#v5200000# "+sun[weekday][1]+" 点券 \r\n";
				for(var kk in sun[weekday][2]){
					cm.gainItem(sun[weekday][2][kk][0],sun[weekday][2][kk][1]);
					txt += "\t#v"+sun[weekday][2][kk][0]+"##z"+sun[weekday][2][kk][0]+"# X"+sun[weekday][2][kk][1]+"\r\n";
				}
			cm.getPlayer().setAcLog("每日奖励");
			txt+="以上奖励领取成功\r\n\r\n";
			cm.sendOk(txt);
		}else{
			for(var i=0;i<7;i++){
				txt += "\r\n------【星期"+(i==0?"日":i)+"奖励】------\r\n\r\n";
				txt += "\t#v5200002# "+sun[i][0]+" 金币 \r\n";
				txt += "\t#v5200000# "+sun[i][1]+" 点券 \r\n";
				for(var kk in sun[i][2]){
					txt += "\t#v"+sun[i][2][kk][0]+"##z"+sun[i][2][kk][0]+"# X"+sun[i][2][kk][1]+"\r\n";
				}
			}
			// txt += "\r\n------【星期"+(weekday==0?"日":weekday)+"奖励】------\r\n\r\n";
			// txt += "\t#v5200002# "+sun[weekday][0]+" 金币 \r\n";
			// txt += "\t#v5200000# "+sun[weekday][1]+" 点券 \r\n";
			// for(var kk in sun[weekday][2]){
			// 	txt += "\t#v"+sun[weekday][2][kk][0]+"##z"+sun[weekday][2][kk][0]+"# X"+sun[weekday][2][kk][1]+"\r\n";
			// }
			cm.sendOk(txt);
		}
	} else if (status == 2) {
		cm.dispose();
		return;
	}
}