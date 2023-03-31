var ca = java.util.Calendar.getInstance();
var 获取年 = ca.get(java.util.Calendar.YEAR);
var 获取日 = ca.get(java.util.Calendar.DATE);
var 获取月 = ca.get(java.util.Calendar.MONTH) + 1;
var 获取日期 = (获取年 * 10000) + (获取月 * 100) + 获取日; //获取日期
var 觉醒次数;
var 拥有觉醒点;
var 需要觉醒点;
var 觉醒需要等级 = 235;
var 觉醒后降级 = 200;
var 称号;
var 修炼地图1 = 105200000;
var 修炼地图2 = 105200000;
var 每日可进行修炼次数 = 20;
var 觉醒限制次数 = 30000;
var status = -1;

function start() {
	觉醒次数 = cm.getPlayer().getTotalWins();
	// if (觉醒次数 >= 0 && 觉醒次数 <= 3) {
	// 	称号 = "入门大佬 "+觉醒次数+" 阶";
	// 	} else if (觉醒次数 > 3 && 觉醒次数 <= 6) {
	// 	称号 = "初级大佬 "+(觉醒次数-10)+" 阶";
	// 	} else if (觉醒次数 > 20 && 觉醒次数 <= 30) {
	// 	称号 = "中级大佬 "+(觉醒次数-20)+" 阶";
	// 	} else if (觉醒次数 > 30 && 觉醒次数 <= 40) {
	// 	称号 = "高级大佬 "+(觉醒次数-30)+" 阶";
	// 	} else if (觉醒次数 > 40 && 觉醒次数 <= 50) {
	// 	称号 = "史诗大佬 "+(觉醒次数-40)+" 阶";
	// 	} else if (觉醒次数 > 50 && 觉醒次数 <= 100) {
	// 	称号 = "传说大佬 "+(觉醒次数-50)+" 阶";
	// 	} else if (觉醒次数 > 100) {
	// 	称号 = "骨灰大佬 "+(觉醒次数-100)+" 阶";
	// 	}
	拥有觉醒点 = cm.getBossRank9("觉醒修炼点", 2);
	需要觉醒点 = 500 + (cm.getPlayer().getTotalWins() * 500);
	cm.sendSimple("  #d Hi~ #b#h ##k 角色#r" + 觉醒需要等级 + "级#k可进行觉醒 觉醒后等级降为#b" + 觉醒后降级 + "级且职业不变...#k\r\n\r\n#d剩余修炼数: #b" + (每日可进行修炼次数 - cm.getBossLog("觉醒修炼" + 获取日期)) + "次\r\n#d当前觉醒数: #b" + 觉醒次数 + "次\r\n#d觉醒修炼点: #B" + (拥有觉醒点 / 需要觉醒点 * 100) + "# " + 拥有觉醒点 + "/" + 需要觉醒点 + "点\r\n#b#L1#进行觉醒修炼#l#k\r\n#b#L0#进行 #r第" + (觉醒次数 + 1) + "次 #b觉醒#l#k\r\n#b#L2#领取觉醒奖励#l#k\r\n\r\n");
}

function action(mode, type, selection) {
	if (mode == 0) {
		cm.dispose();
		return;
	} else {
		status++;
	}
	if (status == 0) {
		if (selection == 0) {
			if (cm.getPlayer().getLevel() < 觉醒需要等级) {
				cm.sendOk("你的等级不足235级。");
				cm.dispose();
				return;
			}
			if (拥有觉醒点 < 需要觉醒点) {
				cm.sendOk("你的资历不足, 可前往修炼地图进行修炼。");
				cm.dispose();
				return;
			}

			if (cm.getPlayer().getTotalWins() >= 觉醒限制次数) {
				cm.sendOk("已达到觉醒最高次数，请等待管理员增加觉醒次数上限。");
				cm.dispose();
				return;
			}

			cm.getPlayer().increaseTotalWins(); //给转生次数
			//cm.clearSkillsZs();//可能是清楚技能
			//cm.StatsZs();//可能是将等级改为1级
			//cm.getPlayer().setExp(0);//经验修改为0
			cm.changeJob(cm.getJob()); //变更职业 当前设置不变更
			cm.getChar().setLevel(199);
			cm.getChar().levelUp();
			cm.getChar().saveToDB(false, false);
			cm.setBossRank9("觉醒修炼点",2,-需要觉醒点);
			cm.getPlayer().addAccountExtraDamage(cm.getPlayer(),100000);
			cm.sendOk("成功觉醒。附加攻击已增加10万。");
			cm.worldMessage(6, "[系统公告] 恭喜大佬 " + cm.getPlayer().getName() + " 完成了第[" + cm.getPlayer().getTotalWins() + "]次觉醒,附加攻击增加了10万！牛逼牛逼！！！");
			cm.dispose();
		}
		if (selection == 1) {
			if (cm.getPlayer().getLevel() < 觉醒需要等级) {
				cm.sendOk("您的等級不足235级，无法进行修炼");
				cm.dispose();
				return;
			}
			if (cm.getBossLog("觉醒修炼" + 获取日期) < 每日可进行修炼次数) {
				if (cm.getMapFactory().getMap(修炼地图1).playerCount() == 0) {
					cm.getMapFactory().getMap(修炼地图1).killAllMonsters(true);
					cm.warp(修炼地图1, 0);
					//cm.setBossRankCount9("觉醒修炼"+获取日期+"",1);
					cm.setBossLog("觉醒修炼" + 获取日期);
					cm.sendOk("点击地图左侧NPC开始进行挑战。");
					cm.dispose();
				} else {
					if (cm.getMapFactory().getMap(修炼地图2).playerCount() == 0) {
						cm.getMapFactory().getMap(修炼地图2).killAllMonsters(true);
						cm.warp(修炼地图2, 0);
						//cm.setBossRankCount9("觉醒修炼"+获取日期+"",1);
						cm.setBossLog("觉醒修炼" + 获取日期);
						cm.sendOk("点击地图左侧NPC开始进行挑战。");
						cm.dispose();
					} else {
						cm.sendOk("当前频道挑战地图已被其他玩家使用,请更换频道。");
						cm.dispose();
					}
				}
			} else {
				cm.sendOk("每日只能进行#r" + 每日可进行修炼次数 + "次#k挑战, 等明天再来吧。");
				cm.dispose();
			}
		}
		if(selection==2){
			cm.dispose();
			cm.openNpc(2030006,"觉醒奖励");
		}
	}
}