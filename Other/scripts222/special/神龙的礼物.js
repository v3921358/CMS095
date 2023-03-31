
var rn = "\r\n\r\n"; // 换行
var selectionData = [0, 0]
var PP;
/**
 * 可兑物品列表
 * @type {*[]}
 */
 var itemList = [
    [4002002,1],//旭日套
	[4310019,7],
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
            cm.gainItem(2430007,-1);
            cm.dispose();
            return;
        }

        status = status + (mode === 1 ? 1 : -1);

        if (status === 0) {
            showMenu();
        } else if (status === 1) {
				seletionData=itemList[selection];
				var text = "#d\t\t\t你确定要领取#v"+itemList[selection][0]+"##z"+itemList[selection][0]+"##l\n\r\n\r\n\r\n\r            在这之前请确认背包是否有足够空间。#k\r\n";
				cm.sendYesNo("" + text + "");
        } else if (status === 2) {
			
            if (cm.getInventory(2).isFull()||cm.getInventory(1).isFull(1)){
				cm.sendOk("请保证背包#b消耗栏#k,#b装备栏#k至少有 #r1 #k个位置");
				cm.dispose();
				return;
			}
			
			if (cm.haveItem(2430007) <= seletionData[1]) {
				cm.gainItem(seletionData[0],1);				
			  	cm.gainItem(2430007,-seletionData[1]);
				//cm.gainPet(seletionData[0], cm.getItemName(seletionData[0]), 1, 0, 100, 30*86400, 0); 
				cm.sendOk("\t\t\t  #r恭喜得 #v"+seletionData[0]+"##z"+seletionData[0]+"##r * 1");
				cm.worldMessage(6, "【神龙的礼物】  恭喜 " + cm.getPlayer().getName() + " 领取神龙的礼物");
				cm.worldMessage(6, "【神龙的礼物】  恭喜 " + cm.getPlayer().getName() + " 领取神龙的礼物");				
				cm.dispose();
			} else {
				cm.sendOk("\t\t\t\t#r没有集齐七颗，领取神龙。");
				cm.dispose();
			}
        }
    }
}
/**
 * 显示菜单
 */
 function showMenu() {
    var text = "   终于见面了 #b#h ##k 我是神龙，听说集齐七颗，即可召唤神龙我的本体~~~加油吧骚年，我随时都会消失，你也可以就此选择礼物。" + rn;
	text+="\t\t\t\t#r< 神龙的礼物 >"+rn;
    for (var i = 0; i < itemList.length; i++) {
        text += "\t\t#L"+i+"##d领取礼物 #v"+itemList[i][0]+"##z"+itemList[i][0]+"##l\r\n";
    }
    cm.sendNext("" + text + "");
}
