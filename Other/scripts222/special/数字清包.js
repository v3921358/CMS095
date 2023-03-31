/*
 ZEVMS冒险岛(079)游戏服务端

 */

load('nashorn:mozilla_compat.js');
//importPackage(net.sf.odinms.client);
var status = 0;
var 心 = "#fEffect/CharacterEff/1112925/0/1#"; //空星
var fee;
var 位置;
var chance = Math.floor(Math.random() * 1);
function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (mode == 0) {
            cm.sendOk("你没有卡号？");
            cm.dispose();
            return;
        }
        if (mode == 1) {
            status++;
        } else {
            status--;
        }

        if (status == 0) {
            cm.sendGetText("    Hi~#b#h ##k，请输入你要清理的背包物品类型，1 = 装备栏，2 = 消耗栏，3 = 设置栏， 4 = 其他栏， 5 = 特殊栏。；");
        } else if (status == 1) {
            fee = cm.getText();
            if (fee>5||fee<1) {
                cm.sendOk("不正确的类型。");
                cm.dispose();
                return;
            }
			cm.sendGetText("输入道具的位置，从背包第一行从左往右数。");
        } else if (status == 2) { 
			位置 = cm.getText();	
			if (位置>96||位置<1) {
                cm.sendOk("不正确的位置");
                cm.dispose();
                return;
            }
			if(cm.getInventory(fee).getItem(位置)!=null){
				cm.removeAll(cm.getInventory(fee).getItem(位置).getItemId());
				cm.dispose();
                return;	
			}else{
				cm.sendOk("位置上没有物品。");
                cm.dispose();
                return;	
			}
		}
    }
}





















