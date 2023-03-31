var love = "#fEffect/CharacterEff/1022223/4/0#"; // 爱心
var redArrow = "#fUI/UIWindow/Quest/icon6/7#"; // 箭头
var rect = "#fUI/UIWindow/Quest/icon3/6#"; // 正方形
var blueArrow = "#fUI/UIWindow/Quest/icon2/7#"; // 蓝色箭头
var rn = "\r\n\r\n"; // 换行
var itemId = 2020035; // 给物品id
var itemId0 = 4001231; // 制作出来物品id

var nxToItemCount = 1; // 点券兑换时，对物品数量，即1000点券=1物品
var nx2ToItemCount = 1; // 抵用券兑换时，对物品数量，即1000点券=1物品
var moseToItemCount = 10000000; // 金币兑换时，对物品数量，即10000000金币=1物品
var itemToNXCount = 1; // 物品对点券数量
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
            text = "你好#b#h ##k，这都被你找到了，我是糖果箱，其实圣诞老人的拐杖是糖果做的，前两天几个调皮的孩子给偷吃了！不过不是没有办法~我可以糖果重新做~但是我存的糖果已经给圣诞老人做了很多很多的拐杖，如果你愿意为我找到一些枫叶糖果我很乐意帮助你！" + rn;
            
            text += "#L4##b背包有#z2020035##b" + rn;


            cm.sendSimple(text);
        } else if (status === 1) {
            changeMode = selection;

            if (selection === 1) { // 点券换邮票
                text = nxToItemCount + "点券 = 1 #v" + itemId + "#" + rn;
                text += "现在请输入你想要兑换的个数：";
                cm.sendGetText(text);
           
            } else if (selection === 4) { // 邮票换点券
                text = "6 #z" + itemId + "##v" + itemId + "# 可以加工制作 " + itemToNXCount + "#z4001231##v4001231#" + rn;
                text += "请问你要制作几个？";
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

            } else if (changeMode === 4) {
                if (cm.haveItem(itemId, count*6)) {
                    cm.gainItem(itemId, -count*6);
				
                   cm.gainItem(itemId0, count);
                    cm.sendOk("制作成功，获得" + (count * itemToNXCount) + "#z4001231##v4001231#");
                    cm.dispose();
                } else {
                    cm.sendOk("#v" + itemId + "#数量不足无法制作！");
                    cm.dispose();
            
                }
            }
        }
    }
}