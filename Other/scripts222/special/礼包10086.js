/*
 ZEVMS冒险岛(079)游戏服务端
 脚本：礼包设置文件
 */
function action() {
		cm.getPlayer().modifyCSPoints(1, 5000, true);
		cm.gainMeso(5000000);
		cm.gainItem(5150040,3);
		cm.gainItem(4310023,8);
		cm.gainItem(1142075,1);
		cm.setBossRankCount("推广任务");
		cm.worldMessage(5,"【推广奖励】恭喜玩家: " + cm.getPlayer().getName() + " 成功领取每周推广任务奖励,获得豪华大礼。");
		cm.sendOk("恭喜你领取推广任务！");
		cm.dispose();
	
}