/*
 ZEVMS冒险岛(079)游戏服务端
 脚本：礼包设置文件
 */
function action() {
	var 推广员ID = cm.getBossRank9("推广上级", 2);
	var 数量 = 198;
	var 推广人数 = cm.getBossRankCountTop9("" + 推广员ID + "").size();
	if (推广人数 >= 10){
	var 返利比例 = 20;
	}else if (推广人数 >= 5 && 推广人数 < 10){
	var 返利比例 = 15;	
	}else{
	var 返利比例 = 10;		
	}
    cm.getPlayer().modifyCSPoints(1, 数量 * 300, true);
	var 职业 = cm.getPlayer().getJob();
	if ((职业 >= 100 && 职业 <= 132) || (职业 >= 2000 && 职业 <= 2112) || (职业 >= 1100 && 职业 <= 1112)) {
			cm.gainItem(1003197,1);
			cm.gainItem(1052333,1);
			cm.gainItem(1072502,1);
			cm.gainItem(1082305,1);
		}
		if ((职业 >= 200 && 职业 <= 232) || (职业 >= 2200 && 职业 <= 2218) || (职业 >= 3200 && 职业 <= 3212) || (职业 >= 1200 && 职业 <= 1212)) {
			cm.gainItem(1003198,1);
			cm.gainItem(1052334,1);
			cm.gainItem(1072503,1); 
			cm.gainItem(1082306,1);
		}
		if ((职业 >= 300 && 职业 <= 322) || (职业 >= 3300 && 职业 <= 3312) || (职业 >= 1300 && 职业 <= 1312)) {
			cm.gainItem(1003199,1);
			cm.gainItem(1052335,1);
			cm.gainItem(1072504,1);
			cm.gainItem(1082307,1);
		}
		if ((职业 >= 400 && 职业 <= 434) || (职业 >= 1400 && 职业 <= 1412)) {
			cm.gainItem(1003200,1);
			cm.gainItem(1052336,1);
			cm.gainItem(1072505,1);
			cm.gainItem(1082308,1);
		}
		if ((职业 >= 500 && 职业 <= 522) || (职业 >= 3500 && 职业 <= 3512)|| (职业 >= 1500 && 职业 <= 1512)) {
			cm.gainItem(1003201,1);
			cm.gainItem(1052336,1);
			cm.gainItem(1072505,1); 
			cm.gainItem(1082308,1);
		}
	cm.gainItem(1112585,1);
	cm.gainItem(1032092,1);
	cm.gainItem(1112583,1);
	cm.gainItem(1132084,1);
	cm.gainItem(2000005,200);
	cm.gainItem(2028048,50);
	cm.gainItem(5220010,30);
	cm.setBossLog("漫新套装");
    cm.setBossRankCount9("充值积分", 数量);
	cm.worldMessage(5,"【充值礼包】恭喜玩家: " + cm.getPlayer().getName() + " 成功领取漫步新月套装礼包,获得超值大礼。");
    //cm.setBossLog("每日赞助");
    cm.sendOk("恭喜你兑换成功！");
    //cm.getPlayer().dropMessage(5, "PP币 + " + 数量 + "");
    cm.dispose();
    
    var 推广员名字 = cm.getCharacterNameById(cm.getBossRank9("推广上级", 2));
    if (推广员ID > 0) {
        cm.setBossRank9(推广员ID,"" + 推广员名字 + "","推广积分",2,Math.floor(数量*返利比例/100));
        cm.小纸条("" + 推广员名字 + "", "[推广返利]:你推广的玩家 " + cm.getChar().getName() + " 充值"+数量*200+"点券，你成功获得 "+返利比例+"% 返利。");
    }
}