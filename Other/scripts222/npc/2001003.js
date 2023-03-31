var status = 0;
var objDate = new Date();
var day = objDate.getDay();
var Month = objDate.getMonth();
var MonthS = ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"];
var weekday = ["日", "一", "二", "三", "四", "五", "六"];
var MonthB = objDate.getMonth() - 1;
var tzc = objDate.getDate();
var Year = objDate.getFullYear();

var itemList = Array(
    Array(5561000, 5), //從下一個開始
    Array(2049401, 3), //1 淺能捲
    Array(5220000, 10), //2 轉蛋劵
    Array(2450000, 3), //3 獵人的幸運
    Array(2022179, 5), //4 紫色的蘋果
    Array(2022462, 3), //5 卡4
    Array(2022463, 3), //6 卡5
    Array(2450000, 2), //7 獵人的幸運
    Array(2049401, 3), //8 淺能捲
    Array(5510000, 5), //9 原地復活術
    Array(2049301, 3), //10 星卷
    Array(2101120, 5), //11 魚怪召喚袋
    Array(2450000, 1), //12 獵人的幸運
    Array(2450000, 1), //13 獵人的幸運
    Array(2450000, 1), //14 獵人的幸運
    Array(2049301, 3), //15 星卷
    Array(2049400, 1), //16 高淺
    Array(2101120, 5), //17 魚怪召喚袋
    Array(2101120, 5), //18 魚怪召喚袋
    Array(2049300, 1), //19 高星
    Array(2022179, 5), //20 紫色的蘋果
    Array(2022179, 5), //21 紫色的蘋果
    Array(2049300, 2), //22 高星力
    Array(5220000, 30), //23 轉蛋劵
    Array(2022462, 5), //24 卡4
    Array(2022463, 5), //25 卡5
    Array(5062000, 20), //26 方塊
    Array(2049300, 1), //27 高星力
    Array(2450000, 1), //28 獵人的幸運
    Array(5220000, 30), //29 轉蛋劵
    Array(5510000, 10), //30 原地復活術
    Array(5062000, 20) //31 方塊

); // 因為沒0日 從第1開始設置物品 第一格道具id 第二格設置數量

var debug = false;
var minuneed = 300;

function start() {
    status = -1;
    if (debug && cm.getPlayer().getGMLevel() < 4) {
        cm.dispose();
        return;
    }
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (mode == 0 && status == 0) {
            cm.dispose();
            return;
        }
        if (mode == 1) status++;
        else status--;
        if (status == 0) {


            var tt = 31;
            if (Month == 0 || Month == 2 || Month == 4 || Month == 6 || Month == 7 || Month == 9 || Month == 11) { // 1月 3月 5月 78 月 10月 12月
                tt = 32;
            }
            if (Month == 1) { // 2月的話
                tt = 29;
            }
            var text3 = 0;
            for (var i = 1; i < tt; i++) {
                text3 += cm.getPlayer().getPrizeLog("" + Year + "年" + MonthS[Month] + i + "日");
            }
			var 连续签到 = cm.getPlayer().getPrizeLogtotal();

            var ta = "#b";

            var text = "";

            for (var i = 1, ytz = 1; i < tt; i++, ytz++) {
                if (cm.getPlayer().getPrizeLog("" + Year + "年" + MonthS[Month] + i + "日") == 0) {
                //    ta = "#b";
                }
                if (cm.getPlayer().getPrizeLog("" + Year + "年" + MonthS[Month] + i + "日") != 0) {
                //    ta = "#r";
                }
               // text += ta + ytz + "#i" + itemList[i][0] + "#" + ((i + 0) % 7 == 0 ? "\r\n" : "");
            }
            var text2 = 0;

			var tex2 = ""
			tex2 += "#e现在时间:#b" + Year + "年" + MonthS[Month] + tzc + "日星期" + weekday[day] + "\r\n"
			tex2 += "#e#k本月累计签到【 #r" + text3 + "#k 】天" + "\r\n"
			tex2 += "#e#k本月连续签到【 #r" + 连续签到 + "#k 】天" + "\r\n"
			tex2 += "#e#k本日在线时间【 #r" + cm.getPlayer().getOnlinetime() + "#k 】分" + "\r\n"
			tex2 += "#e#b上线时间需满"+minuneed+"分钟才可进行行签到\r\n"
			//tex2 += "#e#k#e今日奖励为#b#i" + itemList[tzc][0] + "##r#z" + itemList[tzc] + "##k【 #r" + itemList[tzc][1] + "#k 】个#b\r\n"
			tex2 += "#e#L0#每日签到(200抵用)#l		#L1#领取连续签到奖励#l\r\n\r\n"
			tex2 += "#e#k一下为【#b" + Year + "年" + MonthS[Month] + "份#k】奖励\r\n"
			tex2 += "#e(#b蓝色=#k未签到,#r紅色=#k已签到)\r\n\r\n" + text;
			
            cm.sendSimple(tex2);
        } else if (status == 1) {


            if (selection == 0) {
                if (!cm.canHold()) {
                    cm.sendOk("您的背包空间不足");
                    cm.dispose();
                    return;
                }
                if (cm.getPlayer().getPrizeLog("" + Year + "年" + MonthS[Month] + tzc + "日") < 1 && cm.getPlayer().getOnlinetime() >= minuneed && cm.getBossLoga("meiriqiandao") < 1) { //
                    cm.getPlayer().setPrizeLog("" + Year + "年" + MonthS[Month] + tzc + "日");
                   // cm.gainItem(itemList[tzc][0], itemList[tzc][1]);
                    cm.getPlayer().modifyCSPoints(2, 200, true);
					cm.setBossLoga("meiriqiandao");
                    cm.sendOk("已领取每日签到奖励。");				
                    cm.worldMessage(6,"『每日签到』" + " : " + "玩家 " + cm.getChar().getName() + " 已经领取签到奖励！");
                    cm.dispose();
                } else { // 不是隊長
                    cm.sendOk("已领取过或在线时间不到"+minuneed+"分钟。");
                    cm.dispose();
                }


            } else if (selection == 1) {
                if (MonthB < 0 && cm.getPlayer().getPrizeLog("" + Year + "年" + MonthS[Month] + tzc + "日") > 0) { //
                    MonthB = 11;
					cm.sendSimple("#r★★★★★★★★★★★★\r\n#b#L0#" + MonthS[Month] + "限定签到奖励#l\r\n\r\n#r★★★★★★★★★★★★");
				}else{
					cm.sendSimple("#r今天还没有签到，请签到了再来领奖");
					cm.dispose();
				}
            }
        } else if (status == 2) {
            cm.dispose();
            if (selection == 0) {
                cm.openNpc(9900007, "累计签到");
            }
        }

    }

}