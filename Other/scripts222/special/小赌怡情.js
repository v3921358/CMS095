/*
	
**/
var 心 = "#fEffect/CharacterEff/1112925/0/1#"; //空星
var 图标2 = "#fUI/StatusBar.img/BtClaim/mouseOver/0#";
var 圆形 = "#fEffect/CharacterEff/1032063/0/0#"; //长音符
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
            return;
        }
        if (mode == 1) {
            status++;
        } else {
            status--;
        }
    }
    if (status <= 0) {
        var selStr = "\t\t" + 圆形 + " #r#e < 澳门赌场中心 > #k#n  " + 圆形 + "\r\n\r\n";

        selStr += "	#eHi~#b#h ##k，这里是澳门赌场中心，请选择赌哪个。\r\n\r\n";
        selStr += "#L0#" + 心 + "#d赌金币(发家)" + 心 + "#l\t#L1#" + 心 + "#d赌枫叶(致富)" + 心 + "#l  \r\n\r\n";
        selStr += "#L2#" + 心 + "#d赌抽奖(椅子)" + 心 + "#l\t#L3#" + 心 + "#d赌抽奖(全品类)" + 心 + "#l  \r\n\r\n";
        selStr += "#L4#" + 心 + "#d战士转蛋机" + 心 + "   #l\t#L5#" + 心 + "#d法师转蛋机" + 心 + "#l  \r\n\r\n";
        selStr += "#L6#" + 心 + "#d弓手转蛋机" + 心 + "   #l\t#L7#" + 心 + "#d飞侠转蛋机" + 心 + "#l  \r\n\r\n";
        selStr += "#L8#" + 心 + "#d海盗转蛋机" + 心 + "   #l\t#L9#" + 心 + "#d卷轴转蛋机" + 心 + "#l  \r\n\r\n";



        cm.sendOk(selStr);
    } else if (status == 1) {
        switch (selection) {
            case 0:
                cm.dispose();
                cm.openNpc(9900004, "金币赌博");
                break;
            case 1:
                cm.dispose();
                cm.openNpc(9900004, "道具赌博");
                break;
            case 2:
                cm.dispose();
                cm.openNpc(9310090);//排行系统
                break;
            case 3:
                cm.dispose();
                cm.openNpc(9900004, "全品类抽奖");
                break;
            case 4:
                cm.dispose();
                cm.openNpc(9330112);
                break;
            case 5:
                cm.dispose();
                cm.openNpc(9330113);
                break;
            case 6:
                cm.dispose();
                cm.openNpc(9330114);
                break;
            case 7:
                cm.dispose();
                cm.openNpc(9330115);
                break;
            case 8:
                cm.dispose();
                cm.openNpc(9330116);
                break;
            case 9:
                cm.dispose();
                cm.openNpc(9330118);
                break;

        }
    }
}