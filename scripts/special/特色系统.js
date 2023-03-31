/*
	
**/
var 心 = "#fUI/GuildMark.img/Mark/Etc/00009023/1#";
var 图标2 = "#fUI/StatusBar.img/BtClaim/mouseOver/0#";
var 圆形 = "#fEffect/CharacterEff/1112903/0/0#"; //红桃心
var kx = "#fEffect/CharacterEff/1112925/0/1#"; //空星
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
        var selStr = "\t\t\t" + 圆形 + "  " + 圆形 + " #r#e < 特色系统中心 > #k#n " + 圆形 + "  " + 圆形 + "\r\n\r\n";

        selStr += "	#eHi~#b#h ##k，这里是特色系统中心，请选择系统。\r\n\r\n";
        selStr += "#L0#" + kx + "#d自由转职" + kx + "#l   #L5#" + kx + "#d角色觉醒系统" + kx + "#l  \r\n\r\n";
        selStr += "#L2#" + kx + "#d排行系统" + kx + "#l   #L4#" + kx + "#d造型代码自选系统" + kx + "#l  \r\n\r\n";
        selStr += "#L1#" + kx + "#d师徒系统" + kx + "#l   #L3#" + kx + "#d技能赞助礼品" + kx + "#l   \r\n\r\n";
        selStr += "#L6#" + kx + "#d快速技能加点" + kx + "#l   \r\n\r\n";
        selStr += " "



        cm.sendOk(selStr);
    } else if (status == 1) {
        switch (selection) {
            case 0:
                cm.dispose();
                cm.openNpc(9900004, "自由转职");
                break;
            case 1:
                cm.dispose();
                cm.openNpc(9900004, "师徒系统");
                break;
            case 2:
                cm.dispose();
                cm.openNpc(9040004); //排行系统
                break;
            case 3:
                cm.dispose();
                cm.openNpc(9900004, "技能赞助礼品");
                break;
            case 4:
                cm.dispose();
                cm.openNpc(1012117);
                break;
            case 5:
                cm.dispose();
                cm.openNpc(9900004, "角色觉醒系统");
                break;
            case 6:
                cm.dispose();
                cm.openNpc(9900004, "快速技能加点");
                break;

        }
    }
}