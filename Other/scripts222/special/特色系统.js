/*
	
**/
var 心 =  "#fEffect/CharacterEff/1032063/0/0#"; //长音符
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
         var selStr = "\t  " + 心 + "#r#e < 特色中心 > #k#n " + 心 + "\r\n\r\n\r\n\r\n";

      
        selStr += "#L7#" + kx + "#d澳门赌场" + kx + "#l   #L5#" + kx + "#d角色觉醒系统" + kx + "#l  \r\n\r\n";
        selStr += "#L2#" + kx + "#d排行系统" + kx + "#l   #L4#" + kx + "#d造型代码自选系统" + kx + "#l  \r\n\r\n";
        selStr += "#L1#" + kx + "#d师徒系统" + kx + "#l   #L11#" + kx + "#d全服务器时装自选" + kx + "#l  \r\n\r\n";
        selStr += "#L8#" + kx + "#d洗血系统" + kx + "#l   #L3#" + kx + "#d全民马拉松《三倍》" + kx + "#l\r\n\r\n";
		selStr += "\t  #L9#" + kx + "#d高版本BOSS体验中心" + kx + "#l\r\n\r\n";
        // selStr += "\r\n\r\n";
        selStr += " "
        //#L8#" + kx + "#d破攻兑换中心" + kx + "#l #L3#" + kx + "#d技能赞助礼品" + kx + "#l   

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
                cm.openNpc(9900004, "全民马拉松");
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
            case 7:
                cm.dispose();
                cm.openNpc(9900004, "小赌怡情");
                break;
            case 8:
                cm.dispose();
                 cm.openNpc(9900004, "洗血系统");
                break;
            case 9:
                cm.dispose();
                cm.openNpc(9900004, "高版本BOSS挑战");
                break;
            case 10:
                cm.dispose();
                cm.openNpc(9310074);
                break;
            case 11:
                cm.dispose();
                cm.openNpc(9900004, "自选时装");
                break;
        }
    }
}