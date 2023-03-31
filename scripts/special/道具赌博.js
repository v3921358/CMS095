var status = 0;
var jilv = 0;
var co = 50;
var qty;
var cost;
var xy = 4001126; //需要
var jc1 = xy; //奖池

function start() {
    status = -1;
    action(1, 0, 0);
    var text = "搏一搏，单车能变摩托，赢了会所嫩模。\r\n";
    text += "本钱越多挣得越多哦，客官您要赌几张枫叶？\r\n\r\n";
    text += "基础"+co+"张枫叶，请输入要赌的倍数";
    cm.sendGetNumber(text,1,1,5);
}


function GetRandomNum(Min, Max) {
    var Range = Max - Min;
    var Rand = Math.random();
    return (Min + Math.round(Rand * Range));
}

function action(mode, type, selection) {
    status++;
    if (mode == -1){
        cm.dispose();
        return;
    } else if (mode == 0) {
        cm.sendOk("穷B快滚!.");
    	cm.dispose();
    	return;
    }
    if (status == 1) {
	qty = (selection > 0) ? selection : (selection < 0 ? -selection : 1);
	cost = qty*co;
	qty = qty*co;
            var add = "欢迎你：#d"+cm.getName()+"#k,这里是本服道具赌博系统#k，";
            add += "我们为您打造一个冒险之家的感觉，喜欢的朋友记得带上朋友一起哦.\r\n ";
            add += "#r温馨提示您，小赌怡情，大赌伤身.\r\n ";
            add += "☆加倍赌博赔率由左到右赔率递增,奖金增加概率降低.\r\n\r\n";
            add += "☆#r当前下注押金: #b" + cost + "张枫叶。\r\n\r\n";
            add += "#L1#" +  "-[#b1:1倍赔率#k]#l";
            add += "#L2#" +  "-[#b1:2倍赔率#k]#l";
            add += "#L3#" +  "-[#b1:3倍赔率#k]#l";
            cm.sendSimple(add);
       } else if (status == 2) {
            if (selection == 1) {
	qty = qty*1;
                var add = "#b<#e#r 道具赌博 #n#b>\r\n\r\n";
                add += "" +  "-您选择的是[#r赔率1:1#b].\r\n";
                add += "" +  "-您的押注为[#r" + cost + "张枫叶。#b].\r\n";
                add += "" +  "-如果胜利将获取[" + qty + "张枫叶#b]的奖励.\r\n";
                add += "" +  "-点击[#r是#b]开始赌博,点击[#r不是#b]放弃赌博.";
                cm.sendYesNo(add);
                jilv = 1;
            } else if (selection == 2) {
                var add = "#b<#e#r 道具赌博 #n#b>\r\n\r\n";
	qty = qty*2;
                add += "" +  "-您选择的是[#r赔率1:2#b].\r\n";
                add += "" +  "-您的押注为[#r" + cost + "张枫叶。#b].\r\n";
                add += "" +  "-如果胜利将获取[" + qty + "张枫叶#b]的奖励.\r\n";
                add += "" +  "-点击[#r是#b]开始赌博,点击[#r不是#b]放弃赌博.";
                cm.sendYesNo(add);
                jilv = 2;
            } else if (selection == 3) {
                var add = "#b<#e#r 道具赌博 #n#b>\r\n\r\n";
	qty = qty*3;
                add += "" +  "-您选择的是[#r赔率1:3#b].\r\n";
                add += "" +  "-您的押注为[#r" + cost + "张枫叶。#b].\r\n";
                add += "" +  "-如果胜利将获取[" + qty + "张枫叶#b]的奖励.\r\n";
                add += "" +  "-点击[#r是#b]开始赌博,点击[#r不是#b]放弃赌博.";
                cm.sendYesNo(add);
                jilv = 3;
            }
        } else if (status == 3) {
                    if (!cm.haveItem(xy, cost)) {
                        cm.sendOk("#b您的枫叶不足,不能参加赌博.....");
	        cm.dispose();
                    } else {
                        jiaru = GetRandomNum(0, jilv);
                        if (jiaru == 0) {
                            cm.gainItem(jc1,qty);
                            cm.sendOk("#b恭喜,您已经大获全胜...");
	            var text;
		text = "[恭喜]" + cm.getPlayer().getName() + " : " + "在道具扶贫中赢得" + qty + "张枫叶！！";
	            cm.worldMessage(6, text);
	            cm.dispose();
                        } else {
                            cm.gainItem(jc1,-cost);
                            cm.sendOk("#b悲剧啊.你输了....");
	           cm.dispose();
                        }
                    }
        }
}