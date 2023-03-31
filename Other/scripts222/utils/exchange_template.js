/**
 * 兑换模板脚本
 * 使用方法
 * load("scripts/utils/exchange_template.js")
 * 通过 init 将数据传给脚本
 * 数据格式
 * [
 *      [4022017, 1, 4022011, 10, true],
 *      [4022017, 1, 4022011, 10, false],
 * ]
 * 
 * 其中：
 * 0 表示需要的材料id
 * 1 表示需要的材料数量
 * 2 表示兑换的物品id
 * 3 表示兑换到的物品数量
 * 4 表示兑换到的物品是否可以叠加
 * 
 * action 方法调用即可
 */

 var love = "#fEffect/CharacterEff/1022223/4/0#"; // 爱心
 var redArrow = "#fUI/UIWindow/Quest/icon6/7#"; // 箭头
 var rect = "#fUI/UIWindow/Quest/icon3/6#"; // 正方形
 var blueArrow = "#fUI/UIWindow/Quest/icon2/7#"; // 蓝色箭头
 var rn = "\r\n\r\n"; // 换行
 
 var status = -1;
 var itemArr = [];
 var sel = 0;
 
 function templateInit(cm, arr) {
     itemArr = arr;
     status = -1;
 }
 
 function templateAction(cm, mode, type, selection) {
     if (itemArr === null) {
         cm.sendSimple("脚本有误，请联系管理员修复！");
         cm.dispose();
         return;
     }
 
     var text;
     if (mode === -1) {
         cm.dispose();
     } else {
         if (status >= 0 && mode === 0) {
             cm.sendOk("感谢你的光临！");
             cm.dispose();
             return;
         }
 
         status = status + (mode === 1 ? 1 : -1);
 
         if (status === 0) {
             sel = 0;
             text = "您好，这里是道具兑换中心" + rn;
             text += "请选择你需要兑换的道具：" + rn;
 
             for (var i = 0; i < itemArr.length; i++) {
                 var itemInfo = itemArr[i];
                 text += "#b#L" + i + "#使用#e" + itemInfo[1] + "#n个#t" + itemInfo[0] + "##v" + itemInfo[0] + "#兑换" + "#e" + itemInfo[3] + "#n个#i" + itemInfo[2] + "##z" + itemInfo[2] + "##l#b" + rn;
             }
 
             cm.sendSimple(text);
         } else if (status === 1) {
             sel = selection;
             var itemInfo = itemArr[sel];
             text = "确定使用#e" + itemInfo[1] + "#n个#t" + itemInfo[0] + "##v" + itemInfo[0] + "#兑换" + "#e" + itemInfo[3] + "#n个#t" + itemInfo[2] + "##v" + itemInfo[2] + "#？" + rn;
             text += "#r请注意背包空间是否足够！#k" + rn;
             text += "请输入兑换个数：" + rn;
             cm.sendGetNumber(text, 1, 1, 100);
         } else if (status === 2) {
             if (selection <= 0) {
                 cm.sendSimple("输入错误，请检查后重新输入！");
                 cm.dispose();
                 return;
             }
 
             var itemInfo = itemArr[sel];
             var cost = itemInfo[1] * selection;
             if (!cm.haveItem(itemInfo[0], cost)) {
                 cm.sendSimple("所需材料不足，请检查后重新兑换！");
                 cm.dispose();
                 return;
             }
 
             var gainCount = itemInfo[3] * selection;
 
             cm.gainItem(itemInfo[0], -cost);
             if (itemInfo[4]) {
                 cm.gainItem(itemInfo[2], gainCount);
             } else {
                 for (var i = 0; i < gainCount; i++) {
                     cm.gainItem(itemInfo[2], 1);
                 }
             }
             cm.sendOk("兑换成功，获得#e" + gainCount + "#n个#t" + itemInfo[2] + "##v" + itemInfo[2] + "#" + rn);
             cm.dispose();
         }
     }
 }
 