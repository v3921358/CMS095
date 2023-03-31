/*
 ZEVMS冒险岛(079)游戏服务端
 脚本：清理背包
 */
var 图标1 = "#fUI/UIWindow.img/FadeYesNo/icon7#";
var 图标2 = "#fUI/StatusBar.img/BtClaim/mouseOver/0#";
var 关闭 = "#fUI/UIWindow.img/CashGachapon/BtOpen/mouseOver/0#";
var 打开 = "#fUI/UIWindow.img/CashGachapon/BtOpen/disabled/0#";
var JD = "#fUI/Basic/BtHide3/mouseOver/0#";
var 心 = "#fUI/GuildMark.img/Mark/Etc/00009001/14#";
var 装备2 = "#fUI/CashShop.img/Base/Tab2/Enable/0#";
var 消耗2 = "#fUI/CashShop.img/Base/Tab2/Enable/1#";  
var 设置2 = "#fUI/CashShop.img/Base/Tab2/Enable/2#"; 
var 其他2 = "#fUI/CashShop.img/Base/Tab2/Enable/3#";   
var 特殊2 = "#fUI/CashShop.img/Base/Tab2/Enable/4#"; 
var a = "#fEffect/CharacterEff.img/1112926/0/1#";
function start() {
    status = -1;
    action(1, 0, 0)
}

function action(mode, type, selection) {
    if (status <= 0 && mode <= 0) {
        cm.dispose();
        return
    }
    if (mode == 1) {
        status++
    } else {
        status--
    }	 
	if (status <= 0) {
        var selStr = "\t\t\t" + 心 + "  " + 心 + " #r#e < 推广任务 > #k#n " + 心 + "  " + 心 + "\r\n\r\n";
		
		selStr += "		Hi~#b#h ##k，参与服务器推广，让我们的小岛更加热闹吧！完成任务后可以领取以下奖励：\r\n\r\n";
		
		selStr += " #v5200002#点券*5000,#v5200000#金币*500万,#v5150040##z5150040#*3\r\n";	
		
	//	selStr += " #v4310023##z4310023#*8,#v1142075##z1142075##r(可强化属性)#k\r\n\r\n";	
		
		selStr += "\t#L0#"+JD+"#d加入推广组QQ群#r970921448#k#d查看任务说明#l\r\n";
		
	//	selStr += "\t#L1#"+JD+"#d强化推广勋章#v1142075##z1142075##l\r\n\r\n";
		
		selStr += "\t#L2#"+JD+"#d推广奖励兑换#l\r\n\r\n";
		
        cm.sendSimple(selStr)
    } else if (status == 1) {
        switch (selection) {
			case 0:
                cm.dispose();
                cm.打开网页("https://jq.qq.com/?_wv=1027&k=3A3bYlbd");
                break;
			case 1:
                cm.dispose();
                cm.openNpc(9900004,"强化推广勋章");
                break;
			case 2:
                cm.dispose();
                cm.openNpc(9900004,"CDK兑换");
                break;

        }
    }
}