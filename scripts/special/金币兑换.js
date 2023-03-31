var love = "#fEffect/CharacterEff/1022223/4/0#"; // 爱心
var redArrow = "#fUI/UIWindow/Quest/icon6/7#"; // 箭头
var rect = "#fUI/UIWindow/Quest/icon3/6#"; // 正方形
var blueArrow = "#fUI/UIWindow/Quest/icon2/7#"; // 蓝色箭头
var rn = "\r\n\r\n"; // 换行
var itemId = 4002003; // 物品id

var nxToItemCount = 11000; // 点券兑换时，对物品数量，即11000点券=1物品
var nx2ToItemCount = 11000; // 抵用券兑换时，对物品数量，即11000点券=1物品
var moseToItemCount = 33000000; // 金币兑换时，对物品数量，即33000000金币=1物品
var itemToNXCount = 10000; // 物品对点券数量
var itemToMoseCount = 30000000; // 物品对金币数量

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

            cm.sendOk("感谢你的光临！");
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
            text = "您好，这里是兑换中心" + rn;
            text += "#L1##b点券换邮票#b" + rn;
            //text += "#L2##b抵用券换邮票#b" + rn;
            text += "#L3##b金币换邮票#b" + rn;
            text += "#L4##b邮票换点券#b" + rn;
            text += "#L5##b邮票换金币#b" + rn;

            cm.sendSimple(text);
        } else if (status === 1) {
            changeMode = selection;

            if (selection === 1) { // 点券换邮票
                text = nxToItemCount + "点券 = 1 #v" + itemId + "#" + rn;
                text += "现在请输入你想要兑换的个数：";
                cm.sendGetText(text);
            } else if (selection === 2) { // 抵用券换邮票
                text = nx2ToItemCount + "抵用券 = 1 #v" + itemId + "#" + rn;
                text += "现在请输入你想要兑换的个数：";
                cm.sendGetText(text);
            } else if (selection === 3) { // 金币换邮票
                text = moseToItemCount + "金币 = 1 #v" + itemId + "#" + rn;
                text += "现在请输入你想要兑换的个数：";
                cm.sendGetText(text);
            } else if (selection === 4) { // 邮票换点券
                text = "1 #v" + itemId + "# = " + itemToNXCount + "点券" + rn;
                text += "现在请输入你想要兑换的个数：";
                cm.sendGetText(text);
            } else if (selection === 5) { // 邮票换金币
                text = "1 #v" + itemId + "# = " + itemToMoseCount + "金币" + rn;
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
                if (cm.getPlayer().getCSPoints(2) >= count * nx2ToItemCount) {
                    cm.gainNX2(-count * nx2ToItemCount);
                    cm.gainItem(itemId, count);
                    cm.sendOk("兑换成功，获得" + count + "#v" + itemId + "#");
                    cm.dispose();
                } else {
                    cm.sendOk("抵用券数量不足，无法换购！");
                    cm.dispose();
                }
            } else if (changeMode === 3) {
                if (cm.getPlayer().getMeso() >= count * moseToItemCount) {
                    cm.gainMeso(-count * moseToItemCount);
                    cm.gainItem(itemId, count);
                    cm.sendOk("兑换成功，获得" + count + "#v" + itemId + "#");
                    cm.dispose();
                } else {
                    cm.sendOk("金币数量不足，无法换购！");
                    cm.dispose();
                }
            } else if (changeMode === 4) {
                if (cm.haveItem(itemId, count)) {
                    cm.gainItem(itemId, -count);
                    cm.gainNX(count * itemToNXCount);
                    cm.sendOk("兑换成功，获得" + (count * itemToNXCount) + "点券");
                    cm.dispose();
                } else {
                    cm.sendOk("#v" + itemId + "#数量不足无法换购！");
                    cm.dispose();
                }
            } else if (changeMode === 5) {
                if (cm.haveItem(itemId, count)&&(cm.getPlayer().getMeso()+(count*30000000))<2100000000) {
                    cm.gainItem(itemId, -count);
                    cm.gainMeso(count * itemToMoseCount);
                    cm.sendOk("兑换成功，获得" + (count * itemToMoseCount) + "金币");
                    cm.dispose();
                } else {
                    cm.sendOk("#v" + itemId + "#数量不足、或金币超出21亿无法换购！");
                    cm.dispose();
                }
            }
        }
    }
}