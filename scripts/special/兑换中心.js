/*
	
**/
var 心 = "#fUI/GuildMark.img/Mark/Etc/00009023/1#";
var 图标2 = "#fUI/StatusBar.img/BtClaim/mouseOver/0#";
var 圆形 = "#fEffect/CharacterEff/1112903/0/0#"; //红桃心
var hwx = "#fEffect/CharacterEff/1102232/2/0#"; //黄歪星
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
        var selStr = "\t\t\t" + hwx + "  " + hwx + " #r#e < 兑换中心 > #k#n " + hwx + "  " + hwx + "\r\n\r\n";

        selStr += "	#eHi~#b#h ##k，这里是PPMS兑换中心，请选择要兑换的内容。\r\n\r\n";
        selStr += "#L0#" + 圆形 + "#d金币兑换" + 圆形 + "#l  #L1#" + 圆形 + "#d枫叶兑换" + 圆形 + "#l  #L2#" + 圆形 + "#d组队兑换" + 圆形 + "#l\r\n\r\n";
        selStr += "#L3#" + 圆形 + "#d匠人兑换" + 圆形 + "#l  #L4#" + 圆形 + "#d技能兑换" + 圆形 + "#l  #L5#" + 圆形 + "#d积分兑换" + 圆形 + "#l\r\n\r\n";
        selStr += "#L6#" + 圆形 + "#d新手武器兑换" + 圆形 + "#l  #L7#" + 圆形 + "#d魔方碎片兑换" + 圆形 + "#l\r\n\r\n";
        selStr += "#L9#" + 圆形 + "#d武陵道场兑换" + 圆形 + "#l  #L8#" + 圆形 + "#d狮王兑换" + 圆形 + "#l\r\n\r\n";


        cm.sendOk(selStr);
    } else if (status == 1) {
        switch (selection) {
            case 0:
                cm.dispose();
                cm.openNpc(9900004, "金币兑换");
                break;
            case 1:
                cm.dispose();
                cm.openNpc(9900004, "枫叶兑换");
                break;
            case 2:
                cm.dispose();
                cm.openNpc(9900004, "组队兑换");
                break;
            case 3:
                cm.dispose();
                cm.openNpc(9900004, "匠人兑换");
                break;
            case 4:
                cm.dispose();
                cm.openNpc(9900004, "技能兑换");
                break;
            case 5:
                cm.dispose();
                cm.openNpc(9900004, "积分兑换");
                break;
            case 6:
                cm.dispose();
                cm.openNpc(9900004, "新手武器兑换");
                break;
            case 7:
                cm.dispose();
                cm.openNpc(9900004, "魔方碎片兑换");
                break;
            case 8:
                cm.dispose();
                cm.openNpc(9900004, "狮王兑换");
                break;
            case 9:
                cm.dispose();
                cm.warp(925020001, 0);
                break;
        }
    }
}