var status = -1;
var sel, select;
var itemchange = 4032398; //物品一* 乖宝宝啥的

var items = [
    /*[要兑换的物品ID,需要兑换的数量, 得到兑换物的数量, 期限(-1为永久)],*/
    [
    //[1112400,  7, 1, -1],
	[1142295, 100, 1, -1],
	[1142295, 1, 1, 240],
    [2028074, 1, 2, -1],
   [5062001, 2, 1, -1],
   [5211047, 1, 1, 120],
 /*[3010227, 20, 1, -1],
 [3010228, 20, 1, -1],
 [3010229, 20, 1, -1],
 [3010230, 20, 1, -1],
 [3010231, 20, 1, -1],
 [3010323, 20, 1, -1],
 [3010324, 20, 1, -1],
 [3010325, 20, 1, -1],
 [3010326, 20, 1, -1],
 [3010327, 20, 1, -1],
 [3010328, 20, 1, -1],*/
    ]
]; //id, price

function start() {
    action(1, 0, 0);
}

function action(mode, type, selection) {
	var itemQuantity = cm.getPlayer().itemQuantity(itemchange);
    if (mode == 0 || mode == -1 && status == 0) {
        cm.dispose();
        return;
    }
    mode == 1 ? status++ : status--;

    if (status == 0) {
        cm.sendSimple("有出席图章来跟我兑换吗?!\n\r您可以使用#i"+itemchange+"#兑换您想兑换的东西。\n\r #b目前你有: " + itemQuantity + "个#t"+itemchange+"#\r\n\r\n#fUI/UIWindow.img/QuestIcon/4/0#\r\n#L0#奖品#l\r\n\r\n#L999#升级公婆戒指#l"); //\r\n #L10#测试#l\r\n\r\n#L8#永恆装备#l\r\n#L9#永恆武器(116)#l
    } else if (status == 1) {
        select = selection;
        switch (selection) {
            case 0:
            case 1:
            case 2: 
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                chooseItem(selection);
                break;
			case 999:
				cm.dispose()
				cm.openNpc(9330111,"公婆戒指");
				break;
        }
    } else if (status == 2) {
        sel = selection;

		 if (select >= 0 && select <= 9) {
			 cm.sendYesNo("您确定要使用#i"+itemchange+"#" + items[select][sel][1] + "个兑换#i"+items[select][sel][0]+"#?");
        }

    }else if (status ==3){
            gainReward(select);
	}
}

function chooseItem(index) {
    var choice = "选项你想要换得项目:#b";
    for (var i = 0; i < items[index].length; i++)
        choice += "\r\n#L" + i + "# #i"+itemchange+"#" + items[index][i][1] + "个交换 #i" + items[index][i][0] + "#(#z" + items[index][i][0] + "#)" + (items[index][i][2] > 0 ? ("#r#e" + items[index][i][2] + "#n#b个") : "") + (items[index][i][3] > 0 ? (" 期限#r#e" + items[index][i][3] + "#n#b分钟") :"") + "#l";
	                                     //物品图片                       //需要物品数量                     //物品名称                                                         //物品个数                               //物品期限
    choice += "\r\n "
    cm.sendSimple(choice);
}

function gainReward(index) {
	var itemQuantity = cm.getPlayer().itemQuantity(itemchange);
    if (itemQuantity >= items[index][sel][1]) {
        if (cm.canHold(items[index][sel][0],items[index][sel][2])) {
            cm.gainItem(itemchange,-items[index][sel][1]);
            cm.gainItemPeriodF(items[index][sel][0], items[index][sel][2], items[index][sel][3]); // 3000 for bullets, they're unrechargable
            cm.sendOk("奖品已发送给您 :P");
        } else {
            cm.sendOk("请确认是否有足够的空间。");
        }
    } else {
        cm.sendOk("请确认#i"+itemchange+"#是否足够");
    }
}



