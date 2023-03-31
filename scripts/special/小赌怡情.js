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
        var selStr = "\t\t\t" + 圆形 + "  " + 圆形 + " #r#e < 澳门赌场中心 > #k#n " + 圆形 + "  " + 圆形 + "\r\n\r\n";
		
		selStr += "	#eHi~#b#h ##k，这里是澳门赌场中心，请选择赌哪个。\r\n\r\n";
        selStr += "#L0#"+kx+"#d赌金币(发家)"+kx+"#l\t#L1#"+kx+"#d赌枫叶(致富)"+kx+"#l  \r\n\r\n";	
		selStr += "#L2#"+kx+"#d赌抽奖(椅子)"+kx+"#l\t#L3#"+kx+"#d赌抽奖(全品类)"+kx+"#l  \r\n\r\n";	
		
		

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

        }
    }
}