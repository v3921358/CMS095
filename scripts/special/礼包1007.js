/*
 ZEVMS冒险岛(079)游戏服务端
 脚本：礼包设置文件
 */
function action() {
	var 推广员ID = cm.getBossRank9("推广上级", 2);
	var 数量 = 68;
	var 推广人数 = cm.getBossRankCountTop9("" + 推广员ID + "").size();
	if (推广人数 >= 10){
	var 返利比例 = 20;
	}else if (推广人数 >= 5 && 推广人数 < 10){
	var 返利比例 = 15;	
	}else{
	var 返利比例 = 10;		
	}
    cm.getPlayer().modifyCSPoints(1, 数量 * 240, true);
	cm.gainItem(5150040,10);
	cm.gainItem(2028048,20);
	cm.setBossLog("每日暖暖礼包");
    cm.setBossRankCount9("充值积分", 数量);
	cm.worldMessage(5,"【充值礼包】恭喜玩家: " + cm.getPlayer().getName() + " 成功领取每日暖暖礼包,获得超值大礼。");
    //cm.setBossLog("每日赞助");
    cm.sendOk("恭喜你兑换成功！");
    cm.dispose();
    
    var 推广员名字 = cm.getCharacterNameById(cm.getBossRank9("推广上级", 2));
    if (推广员ID > 0) {
        cm.setBossRank9(推广员ID,"" + 推广员名字 + "","推广积分",2,Math.floor(数量*返利比例/100));
        cm.小纸条("" + 推广员名字 + "", "[推广返利]:你推广的玩家 " + cm.getChar().getName() + " 充值"+数量*200+"点券，你成功获得 "+返利比例+"% 返利。");
    }
}