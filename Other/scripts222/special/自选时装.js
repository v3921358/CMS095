
load('nashorn:mozilla_compat.js');
importPackage(Packages.util);
importPackage(Packages.client.inventory);
importPackage(Packages.server.life);
//余额，金币，点券，邮票
var itemlist=[
	1,1000000,1000,1
]
//禁止选择列表
var notAllowArr=[1003843,
                 1004042,
                 1004044,
                 1049002,
                 1049003,
                 1049004,
                 1113021,
                 1082553,
                 1082505,
                 1032234,
                 1102653,
                 1102659,
                 1102555,
                 1032138,
                 1702472,
                 1114000,
                 1113003
];
var status;
var h1 = -1;
var h2 = -1;

function start() {
	status = -1;
	str = "";
	select = -1;
	action(1, 0, 1)

}

function action(mode, type, selection) {
	if (mode == 1) {
		status++;
	} else {
		status--;
		cm.dispose();
		return;
	}
	var 赞助余额 = cm.getBossRank("赞助余额", 2) == -1 ? 0 : cm.getBossRank("赞助余额", 2);
	var txt="#e自选条件:\r\n1、需要余额"+itemlist[0]+"元;\r\n2、金币"+itemlist[1]+"\r\n3、点券"+itemlist[2]+"\r\n4、#v4002003##z4002003# X"+itemlist[3];
	txt+="\r\n#b请输入想要搜索的时装(支持id,名称):";
	switch (status) {
		case 0:
			str = selection;
			cm.sendGetText(txt);
			break;
		case 1:
			cm.sendOk(cm.searchData(0, cm.getText()));
			break;
		case 2:
			h2 = selection;
			if (!cm.foundData(0, cm.getText())) {
				cm.dispose();
				return;
			} else if (cm.isCash(h2)) {
				if(!cm.haveItem(4002003,itemlist[3])||cm.getMeso()<itemlist[1]||赞助余额<itemlist[0]||cm.getPlayer().getCSPoints(1) <itemlist[2]){
					cm.sendOk("自选条件不满足！");
					cm.dispose();
					return;
				}
				if(isInArray(notAllowArr,h2)){
					cm.sendOk("该道具无法通过此方式获得！");
					cm.dispose();
					return;
				}
				cm.setBossRankCount("赞助余额", -itemlist[0]);
				cm.gainMeso(-itemlist[1])
				cm.gainNX(-itemlist[2])
				cm.gainItem(4002003, -itemlist[3]);
				cm.gainItem(h2, 1);
			} else {
				cm.sendOk("你选择的道具不是时装道具");

			}
			cm.dispose();
	}
}
function isInArray(arr, value) {
	for (var i = 0; i < arr.length; i++) {
		if (value === arr[i]) {
			return true;
		}
	}
	return false;
}
