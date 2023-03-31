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
        var selStr = "\t\t\t" + 心 + "  " + 心 + " #r#e < 购物中心 > #k#n " + 心 + "  " + 心 + "\r\n\r\n";
		
		selStr += "	#eHi~#b#h ##k，这里是瓦达刮擦购物中心，请选择商店。\r\n\r\n";
        selStr += "#L0#"+圆形+"#d药品商店"+圆形+"#l  #L1#"+圆形+"#d物品商店"+圆形+"#l  #L2#"+圆形+"#d点券商店"+圆形+"#l\r\n\r\n";	
		// selStr += "\t#L2#"+图标2+"#d点券商店"+图标2+"#l\t#L3#"+图标2+"#d通缉任务"+图标2+"#l\r\n\r\n";
		
		

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

        }
    }
}