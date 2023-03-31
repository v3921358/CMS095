/*
	
**/
var 心 =  "#fEffect/CharacterEff/1032063/0/0#"; //长音符
var 图标2 = "#fUI/StatusBar.img/BtClaim/mouseOver/0#";
var 圆形 = "#fEffect/CharacterEff/1112925/0/1#"; //红桃心
var status = 0;

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
			cm.openNpc(9900004);
            return;
        }
        if (mode == 1) {
            status++;
        } else {
            status--;
        }
    }
    if (status <= 0) {
        var selStr = "\t\t\t" + 心 + "#r#e < 购物中心 > #k#n " + 心 + "\r\n\r\n\r\n";


        selStr += "#L0#" + 圆形 + "#d药品商店" + 圆形 + "#l#L1#" + 圆形 + "#d物品商店" + 圆形 + "#l#L2#" + 圆形 + "#d点券商店" + 圆形 + "#l\r\n\r\n";
        selStr += "#L3#" + 圆形 + "#d射手商店" + 圆形 + "#l#L4#" + 圆形 + "#d口袋商店" + 圆形 + "#l#L5#" + 圆形 + "#d宠物商店" + 圆形 + "#l\r\n\r\n";
        selStr += "#L6#" + 圆形 + "#d魔法武器" + 圆形 + "#l#L7#" + 圆形 + "#d魔法防具" + 圆形 + "#l#L8#" + 圆形 + "#d勇士武器" + 圆形 + "#l\r\n\r\n";
        selStr += "#L9#" + 圆形 + "#d勇士防具" + 圆形 + "#l#L10#" + 圆形 + "#d废弃武器" + 圆形 + "#l#L11#" + 圆形 + "#d废弃防具" + 圆形 + "#l\r\n\r\n";
        selStr += "#L12#" + 圆形 + "#d反抗武器" + 圆形 + "#l#L13#" + 圆形 + "#d反抗防具" + 圆形 + "#l#L14#" + 圆形 + "#d配方专卖" + 圆形 + "#l\r\n\r\n";

        cm.sendOk(selStr);
    } else if (status == 1) {
        switch (selection) {
            case 0:
                cm.dispose();
                cm.openShop(12);
                break;
            case 1:
                cm.dispose();
                cm.openShop(66);
                break;
            case 2:
                cm.dispose();
                cm.openNpc(9900004, "点券商城");
                break;
            case 3:
                cm.dispose();
                cm.openShop(7);
                break;
            case 4:
                cm.dispose();
                cm.openShop(303);
                break;
            case 5:
                cm.dispose();
                cm.openShop(105);
                break;
            case 6:
                cm.dispose();
                cm.openShop(13);
                break;
            case 7:
                cm.dispose();
                cm.openShop(14);
                break;
            case 8:
                cm.dispose();
                cm.openShop(10);
                break;
            case 9:
                cm.dispose();
                cm.openShop(11);
                break;
            case 10:
                cm.dispose();
                cm.openShop(16);
                break;
            case 11:
                cm.dispose();
                cm.openShop(17);
                break;
            case 12:
                cm.dispose();
                cm.openShop(58);
                break;
            case 13:
                cm.dispose();
                cm.openShop(57);
                break;
            case 14:
                cm.dispose();
                cm.openNpc(9900004, "配方专卖");
                break;
            default:
                cm.dispose();
                break;
        }
    }
}