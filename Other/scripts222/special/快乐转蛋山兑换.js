
var rn = "\r\n\r\n"; // 换行
var hwx =  "#fEffect/CharacterEff/1032063/0/0#"; //长音符
var selectionData = [0, 0]
var PP;
/**
 * 可兑物品列表
 * @type {*[]}
 */
 var itemList = [
    [1012239,500],//旭日套
	[1032093,500],
	[1112584,500],
	[1122104,500],
	[1132085,500],
    [2046313,100], //饰品100%卷
	[2046222,100] //防具100%强化卷
]
var status = 0;
function start() {
	status = -1;
	action(1, 0, 0);
}
function action(mode, type, selection) {
    if (mode === -1) {
        cm.dispose();
    } else {
        if (status >= 0 && mode === 0) {
           
            cm.dispose();
            return;
        }

        status = status + (mode === 1 ? 1 : -1);

        if (status === 0) {
            showMenu();
        } else if (status === 1) {
				seletionData=itemList[selection];
				var text = "#d确定要使用【"+itemList[selection][1]+"】积分兑换#v"+itemList[selection][0]+"##z"+itemList[selection][0]+"##l吗，在这之前请确认背包是否有足够空间。#k\r\n";
				cm.sendYesNo("" + text + "");
        } else if (status === 2) {
			
            if (cm.getInventory(2).isFull()||cm.getInventory(1).isFull(1)){
				cm.sendOk("请保证背包#b消耗栏#k,#b装备栏#k至少有 #r1 #k个位置");
				cm.dispose();
				return;
			}
			if (cm.getBossRankCount("转蛋山积分") >= seletionData[1]) {
				cm.gainItem(seletionData[0],1);
				cm.setBossRankCount("转蛋山积分", -seletionData[1]);
				cm.sendOk("恭喜兑换成功，获得 #v"+seletionData[0]+"##z"+seletionData[0]+"##r * 1");
				cm.dispose();
			} else {
				cm.sendOk("转蛋山积分不足，无法兑换。");
				cm.dispose();
			}
        }
    }
}
/**
 * 显示菜单
 */
 function showMenu() {
    var text ="\t\t" + hwx + "#r#e < 转蛋山积分兑换 > #k#n" + hwx + "\r\n\r\n\r\n";

	text+="当前有:"+cm.getBossRankCount("转蛋山积分")+"点转蛋山积分"+rn;
    for (var i = 0; i < itemList.length; i++) {
        text += "\t#L"+i+"##d"+itemList[i][1]+"积分兑换#v"+itemList[i][0]+"##z"+itemList[i][0]+"##l\r\n";
    }
    cm.sendNext("" + text + "");
}
