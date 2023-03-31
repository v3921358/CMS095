var status = -1;
var co = 10000000;
//var co = 5000;
var jilv = 0;
var costa;
var xx = -1;
var qty;
var cost;
var jc;
var text;


function start() {
    status = -1;
    action(1, 0, 0);
    var text = "搏一搏，单车能变摩托，赢了会所嫩模。\r\n";
    text += "本钱越多挣得越多哦，客官您要赌多少钱。\r\n\r\n";
    text += "底价" + co / 10000 + "W哦，请输入要赌的倍数";
    cm.sendGetNumber(text, 1, 1, 5);
}


function GetRandomNum(Min, Max) {
    var Range = Max - Min;
    var Rand = Math.random();
    return (Min + Math.round(Rand * Range));
}

function action(mode, type, selection) {
    status++;
    if (mode == -1) {
        cm.dispose();
        return;
    } else if (mode == 0) {
        cm.sendOk("没钱闪开！!.");
        cm.dispose();
        return;
    }
    if (status == 1) {
        qty = (selection > 0) ? selection : (selection < 0 ? -selection : 1);
        cost = qty * co;
        var add = "欢迎你：#d" + cm.getName() + "#k,这里是本服金币赌博系统#k，";
        add += "我们为您打造一个冒险之家的感觉，喜欢的朋友记得带上朋友一起哦.\r\n ";
        add += "#r温馨提示您，小赌怡情，大赌伤身.\r\n ";
        add += "☆加倍赌博赔率由左到右赔率递增,奖金增加概率降低.\r\n";
        //add += "☆如果赌注*倍率大于奖池，就可以拿到奖池的所有金币奥！\r\n\r\n";
        add += "☆#r当前下注押金: #b" + cost + " 金币\r\n";
        add += "#r☆#r当前您拥有金币：#b" + cm.getMeso() + " 金币\r\n";
        add += "#L1#" + "-[#b1:1倍赔率#k]#l";
        add += "#L2#" + "-[#b1:2倍赔率#k]#l";
        add += "#L3#" + "-[#b1:3倍赔率#k]#l";
        cm.sendSimple(add);
    } else if (status == 2) {
        if (selection == 1) {
            var add = "#b<#e#r 金币赌博 #n#b>\r\n\r\n";
            add += "" + "-您选择的是[#r赔率1:1#b].\r\n";
            add += "" + "-您的押注为[#r" + cost + "金币#b].\r\n";
            add += "" + "-如果胜利将获取[#r除本金外" + cost * 1 + "金币#b]的奖励.\r\n";
            add += "" + "-点击[#r是#b]开始赌博,点击[#r不是#b]放弃赌博.";
            cm.sendYesNo(add);
            jilv = 1;
            xx = 0;
        } else if (selection == 2) {
            var add = "#b<#e#r 金币赌博 #n#b>\r\n\r\n";
            add += "" + "-您选择的是[#r赔率1:2#b].\r\n";
            add += "" + "-您的押注为[#r" + cost + "金币#b].\r\n";
            add += "" + "-如果胜利将获取[#r除本金外" + cost * 2 + "金币#b]的奖励.\r\n";
            add += "" + "-点击[#r是#b]开始赌博,点击[#r不是#b]放弃赌博.";
            cm.sendYesNo(add);
            jilv = 2;
            xx = 0;
        } else if (selection == 3) {
            var add = "#b<#e#r 金币赌博 #n#b>\r\n\r\n";
            add += "" + "-您选择的是[#r赔率1:3#b].\r\n";
            add += "" + "-您的押注为[#r" + cost + "金币#b].\r\n";
            add += "" + "-如果胜利将获取[#r除本金外" + cost * 3 + "金币#b]的奖励.\r\n";
            add += "" + "-点击[#r是#b]开始赌博,点击[#r不是#b]放弃赌博.";
            cm.sendYesNo(add);
            jilv = 3;
            xx = 0;
        }
    } else if (status == 3) {
        if (xx == 0) {
            if (jilv == 0) {} else if (jilv != 0) {
                if (cm.getMeso() < cost) {
                    cm.sendOk("#b您的金币不足,不能参加赌博.....");
                    cm.dispose();
                } else {
                    jiaru = GetRandomNum(0, jilv);
                    if (jiaru == 0) {
                        costa = cost * jilv;
                        cm.gainMeso(costa);
                        cm.sendOk("#b恭喜,您已经大获全胜...");
                        text = "[恭喜]" + cm.getPlayer().getName() + " : " + "在金币扶贫中赢得" + costa + "金币。";
                        cm.worldMessage(6, text);
                        cm.dispose();
                    } else {
                        cm.gainMeso(-cost);
                        cm.sendOk("#b悲剧啊.你输了....");
                        text = "[悲剧]]" + cm.getPlayer().getName() + " : " + "在金币扶贫输掉了" + cost + "金币。";
                        cm.worldMessage(5, text);
                        cm.dispose();
                    }
                }
            }
        }
    }
} 