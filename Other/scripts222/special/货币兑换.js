var love = "#fEffect/CharacterEff/1022223/4/0#"; // 爱心
var redArrow = "#fUI/UIWindow/Quest/icon6/7#"; // 箭头
var rect = "#fUI/UIWindow/Quest/icon3/6#"; // 正方形
var blueArrow = "#fUI/UIWindow/Quest/icon2/7#"; // 蓝色箭头
var hwx =  "#fEffect/CharacterEff/1032063/0/0#"; //长音符
var rn = "\r\n\r\n"; // 换行
var itemId = 4002003; // 邮票
var itemId2 = 4001126; // 枫叶
var 枫叶代码 = 4001126; // 枫叶
var 棒棒糖 = 4031821;
var 图标2 = "#fEffect/CharacterEff/1112925/0/1#"; //空星
var nxToItemCount = 1000; // 点券兑换时，对物品数量，即1000点券=1物品
var nx2ToItemCount = 1000; // 抵用券兑换时，对物品数量，即1000点券=1物品
var moseToItemCount = 10000000; // 金币兑换时，对物品数量，即10000000金币=1物品
var moseToItemCount2 = 10; // 枫叶兑换点卷，对物品数量，即10000000金币=1物品
var itemToNXCount = 1000; // 物品对点券数量
var itemToNXCount2 = 100; // 枫叶对点券数量比例
var itemToMoseCount = 10000000; // 物品对金币数量

var changeMode = 0; // 兑换类型

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    var text;
    if (mode === -1) {
        cm.dispose();
    } else {
        if (status >= 0 && mode === 0) {

           
            cm.dispose();
            return;
        }

        if (mode === 1)
            status++;
        else
            status--;

        if (status < 2)
            changeMode = 0;
        if(!cm.canHold(4002003)){
            cm.sendOk("背包空间不足！");
            cm.dispose();
            return;
        }
        if (status === 0) {
            text =  "\t\t\t" + hwx + "#r#e < 货币兑换 > #k#n" + hwx + "\r\n\r\n\r\n\r\n\r\n";
            text += "\t#L1#"+图标2+"#b点券换邮票#b"+图标2+"#l     #L2#"+图标2+"#b金币换邮票#b"+图标2+"#l\r\n\r\n";
            text += "\t#L3#"+图标2+"#b邮票换点券#b"+图标2+"#l     #L4#"+图标2+"#b邮票换金币#b"+图标2+"#l\r\n\r\n";
			
            text += "\t#L5#"+图标2+"#b枫叶换点券#b"+图标2+"#l     #L6#"+图标2+"#b点卷换枫叶#b"+图标2+"#l\r\n\r\n";
			
			
			 //text += "#L2##b抵用券换邮票#b" + rn;

            cm.sendSimple(text);
        } else if (status === 1) {
            changeMode = selection;

            if (selection === 1) { // 点券换邮票
                text = nxToItemCount + "点券 = 1 #v" + itemId + "#" + rn;
                text += "现在请输入你想要兑换的个数：";
                cm.sendGetText(text);
            } else if (selection === 2) { // 金币换邮票
                text = moseToItemCount + "金币 = 1 #v" + itemId + "#" + rn;
                text += "现在请输入你想要兑换的个数：";
                cm.sendGetText(text);
            } else if (selection === 3) { // 邮票换点券
                text = "1 #v" + itemId + "# = 900 点券" + rn;
                text += "现在请输入你想要兑换的个数：";
                cm.sendGetText(text);
            } else if (selection === 4) { // 邮票换金币
                text = "1 #v" + itemId + "# = 900万 金币" + rn;
                text += "现在请输入你想要兑换的个数：";
                cm.sendGetText(text);
			} else if (selection === 5) { // 枫叶换点券
                text = ""+moseToItemCount2+" #v" + itemId2 + "# = " + itemToNXCount2 + "点券" + rn;
                text += "现在请输入你想要兑换几百点卷：";	
                cm.sendGetText(text);
			} else if (selection === 6) { // 枫叶换棒棒糖
                text = "100 点卷 = 9个 #z4001126##v4001126#" + rn;
                text += "现在请输入你想要兑换的个数：";	
                cm.sendGetText(text);	
				
				
				
				
				
				
				
            }
        } else if (status === 2) {
            var count = parseInt(cm.getText());
            if (isNaN(count)) {
                cm.sendOk("数量输入有误！")
                cm.dispose();
                return;
            }

            if (changeMode === 1) {
                if (cm.getPlayer().getCSPoints(1) >= count * nxToItemCount) {
                    cm.gainNX(-count * nxToItemCount);
                    cm.gainItem(itemId, count);
                    cm.sendOk("兑换成功，获得" + count + "#v" + itemId + "#");
                    cm.dispose();
                } else {
                    cm.sendOk("点券数量不足，无法换购！");
                    cm.dispose();
                }
              } else if (changeMode === 2) {
                if (cm.getPlayer().getMeso() >= count * moseToItemCount) {
                    cm.gainMeso(-count * moseToItemCount);
                    cm.gainItem(itemId, count);
                   cm.sendOk("兑换成功，获得" + (count) + "#z" + itemId + "##v" + itemId + "#");
                    cm.dispose();
                } else {
                    cm.sendOk("金币数量不足，无法换购！");
                    cm.dispose();
                }
            } else if (changeMode === 3) {
                 if (cm.haveItem(itemId, count)) {
                    cm.gainItem(itemId, -count);
                    cm.gainNX(count * 900);
                    cm.sendOk("兑换成功，获得" + (count * 900) + "点卷");
                    cm.dispose();
                } else {
                    cm.sendOk("#v4002003# 数量不足，无法换购！");
                    cm.dispose();
                }
            } else if (changeMode === 4) {
                if (cm.haveItem(itemId, count)) {
                    cm.gainItem(itemId, -count);
                     cm.gainMeso(count * 9000000);
                    cm.sendOk("兑换成功，获得" + (count * 9000000) + "金币");
                    cm.dispose();
                } else {
                    cm.sendOk("#v" + itemId + "# 数量不足无法换购！");
                    cm.dispose();
                }
              } else if (changeMode === 5) {
                if (cm.haveItem(枫叶代码, count*10)) {
                    cm.gainItem(枫叶代码, -count*10);
                    cm.gainNX(count *100);
                    cm.sendOk("兑换成功，获得" + (count*100) + "点券");
                    cm.dispose();
                } else {
                    cm.sendOk("#v" + 枫叶代码 + "#数量不足");
                    cm.dispose();
					  }
              } else if (changeMode === 6) {
                 if (cm.getPlayer().getCSPoints(1) >= count * 100) {
                     cm.gainNX(-count * 100);
                    cm.gainItem(4001126, count*9);
                     cm.sendOk("兑换成功，获得" + (count * 9) + "#z" + 枫叶代码 + "##v" + 枫叶代码 + "#");
                    cm.dispose();
                } else {
                    cm.sendOk("点卷数量不足");
                    cm.dispose();
                
                }
            }
        }
    }
}