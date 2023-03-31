/*
	
**/
var 心 = "#fUI/GuildMark.img/Mark/Etc/00009023/1#";
var 图标2 = "#fUI/StatusBar.img/BtClaim/mouseOver/0#";
var 圆形 = "#fEffect/CharacterEff/1112903/0/0#"; //红桃心
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
        var selStr = "\t\t\t" + 心 + "  " + 心 + " #r#e < 装备强化中心 > #k#n " + 心 + "  " + 心 + "\r\n\r\n";

        selStr += "	#eHi~#b#h ##k，这里是装备强化中心，请选择类别。\r\n\r\n";
        selStr += "#L0#" + 圆形 + "#d快速洗潜" + 圆形 + "#l  #L1#" + 圆形 + "#d洗血系统" + 圆形 + "#l  #L2#" + 圆形 + "#d时装升星" + 圆形 + "#l\r\n\r\n";
        selStr += "#L4#" + 圆形 + "#d自选FFN武器" + 圆形 + "#l  #L5#" + 圆形 + "#dFFN武器觉醒" + 圆形 + "#l  \r\n\r\n";
        selStr += "#L3#" + 圆形 + "#d时装属性转移" + 圆形 + "#l \r\n    ";



        cm.sendOk(selStr);
    } else if (status == 1) {
        switch (selection) {
            case 0:
                cm.dispose();
                cm.openNpc(9900004, "快速洗潜");
                break;
            case 1:
                cm.dispose();
                cm.openNpc(9900004, "洗血系统");
                break;
            case 2:
                cm.dispose();
                cm.openNpc(9900004, "时装升星");
                break;
            case 3:
                cm.dispose();
                cm.openNpc(9900004, "时装属性转移");
                break;
            case 4:
                cm.dispose();
                cm.openNpc(9900004, "自选法弗纳武器");
                break;
            case 5:
                cm.dispose();
                cm.openNpc(9900004, "FFN武器觉醒");
                break;

        }
    }
}