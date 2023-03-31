var 地图1 = 105200000;
var 地图2 = 105200000;
var 二级链接;
var 怪物 = new Array(
	2220000, // 红蜗牛王10
	3220000, // 树妖王25
	6220000,//多尔25
	6300005,//僵尸蘑菇王25
	5220002,//浮士德25
	5220000,//巨居蟹30
	9300012,//阿丽莎乐36
	9500178,//变形小吃店40
	9600009,//大王蜈蚣50
	6090001,//雪山魔女57
	9500176,//蓝蘑菇王60
	4220000,//歇尔夫63
	6220001,//朱诺66
	9300152,//生气的法兰肯60
	9300039,//远古精灵58
	9300119,//老海盗62
	3220001,//大宇7700
	5220003,//提莫2W1
	6090003,//书生鬼65
	8130100,//蝙蝠怪66
	7220000,//肯德熊90
	8220000,//艾利杰8W7
	7220001,//九尾狐8W9
	7220002,//妖怪禅师95
	9400633,//地狱大公18W
	9300028,//艾里葛斯115
	8180000,//火焰龙120
	8180001,//天鹰120
	9400597,//苍蓝山猫120
	9400594,//大师守护者120
	9500392,//拉瓦那120
	8220003,//大海兽145
	8220004,//多多150
	8210011,//第二座塔的阿尼
	8220005,//玄冰独角兽159
	8220006,//雷卡168
	9400014,//天球165
	9400121,//女老板
	8500001,//闹钟160
	9400405,//盔甲武士
	8300006,//幻龙
	8300007, // 御龙魔
	8800000,// 扎昆
	9400408//天皇
);

var status;

function start() {
	status = -1;
	action(1, 0, 0);
}

function action(mode, type, selection) {
	if (mode == -1) {
		cm.dispose();
	} else {
		if (mode == 0) {
			cm.dispose();
			return;
		}
		if (mode == 1) {
			status++;
		} else {
			status--;
		}
		if (status == 0) {
			if (cm.getPlayer().getLevel() < 235) {
				cm.sendOk("我是觉醒修炼NPC，你的等级不足235级。");
				cm.dispose();
				return;
			}
			var mapId = cm.getMapId();
			if (mapId == 910340000) {
				cm.warp(910340700, 0);
				cm.removeAll(4001007);
				cm.removeAll(4001008);
				cm.dispose();
			} else {
				if (mapId == 地图1 || mapId == 地图2) {
					if (cm.getBossRank9("觉醒修炼轮数", 2) < 怪物.length) {
						if (cm.getPlayer().getMap().mobCount() == 0) {
							if (cm.getBossRank9("觉醒修炼轮数", 2) <= 0) {
								cm.spawnMob(怪物[0], 1, 0, -435);
								cm.setBossRankCount9("觉醒修炼轮数", 1);
								cm.dispose();
							} else {
								cm.setBossRankCount9("觉醒修炼点", cm.getBossRank9("觉醒修炼轮数", 2));
								cm.playerMessage(6, "完成了第" + cm.getBossRank9("觉醒修炼轮数", 2) + "轮试炼，获得" + cm.getBossRank9("觉醒修炼轮数", 2) + "点觉醒修炼点，总共" + cm.getBossRank9("觉醒修炼点", 2) + "点");
								cm.spawnMob(怪物[cm.getBossRank9("觉醒修炼轮数", 2)], 1, 0, -435);
								cm.setBossRankCount9("觉醒修炼轮数", 1);
								cm.dispose();
							}
						} else {
							二级链接 = 1;
							cm.sendYesNo("#r将地图上怪物清除干净即可进入下一轮试炼！\r\n#k或者#b放弃挑战，送你出去？");
						}
					} else {
						二级链接 = 2;
						cm.sendYesNo("#r你已经完成了所有试炼！\r\n#b领取最终奖励？");
					}
				}
			}
		} else if (status == 1) {
			if (cm.getPlayer().getMap().mobCount() != 0 && 二级链接 == 2) {
				cm.sendYesNo("#r将地图上怪物清除干净！才能出去\r\n#k");
				cm.dispose();
				return;

			}
			if (二级链接 == 1) {
				cm.dispose();
				cm.warp(910000000, 0);
				cm.setBossRankCount9("觉醒修炼轮数", -cm.getBossRank9("觉醒修炼轮数", 2));
			}
			if (二级链接 == 2) {
				cm.dispose();
				cm.warp(910000000, 0);
				cm.setBossRankCount9("觉醒修炼点", 200);
				cm.playerMessage(6, "恭喜你完成了所有试炼，额外获得200觉醒修炼点！总共" + cm.getBossRank9("觉醒修炼点", 2) + "点");
				cm.setBossRankCount9("觉醒修炼轮数", -cm.getBossRank9("觉醒修炼轮数", 2));
			}
		}
	}
}
