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
        var selStr = "\t\t\t" + hwx + "  " + hwx + " #r#e < 道具制作NPC > #k#n " + hwx + "  " + hwx + "\r\n\r\n";

        selStr += "	#eHi~#b#h ##k，你想要找谁？。\r\n\r\n";
        selStr += "#L0#" + 圆形 + "#d射手村道具制作NPC" + 圆形 + "#l\r\n";
        selStr += "#L1#" + 圆形 + "#d密林道具制作NPC" + 圆形 + "#l\r\n";
        selStr += "#L2#" + 圆形 + "#d勇士道具制作NPC" + 圆形 + "#l\r\n";
        selStr += "#L3#" + 圆形 + "#d勇士道具制作NPC" + 圆形 + "#l\r\n";
        selStr += "#L4#" + 圆形 + "#d废弃道具制作NPC" + 圆形 + "#l\r\n";

        cm.sendOk(selStr);
    } else if (status == 1) {
        switch (selection) {
            case 0:
                cm.dispose();
                cm.openNpc(1012002);
                break;
            case 1:
                cm.dispose();
                cm.openNpc(1032002);
                break;
            case 2:
                cm.dispose();
                cm.openNpc(1022004);
                break;
            case 3:
                cm.dispose();
                cm.openNpc(1022003);
                break;
             case 4:
                 cm.dispose();
                 cm.openNpc(1052002);
                 break;
            
        }
    }
}