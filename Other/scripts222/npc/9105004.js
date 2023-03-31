var 地图1 = 889100011;
var 地图2 = 889100011;
var 二级链接;
var 怪物 = new Array(
	2220000, // 红蜗牛王10
	3220000, // 树妖王25
	3220000 // 树妖王25
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
			if (cm.getPlayer().getLevel() < 1) {
				cm.sendOk("尊贵的超级会员，你的等级不足100级。无法进入");
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
					if (cm.getBossRank9("白金Boss", 2) < 怪物.length) {
						if (cm.getPlayer().getMap().mobCount() == 0) {
							if (cm.getBossRank9("白金Boss", 2) <= 0) {
								cm.spawnMob(怪物[0], 1, -165, 34);
								cm.setBossRankCount9("白金Boss", 1);
								cm.dispose();
							} else {
								cm.setBossRankCount9("白金点", cm.getBossRank9("白金Boss", 2));
								cm.playerMessage(6, "恭喜您，抗过第" + cm.getBossRank9("白金Boss", 2) + "关 您获得" + cm.getBossRank9("白金Boss", 2) + "点白金点，总共" + cm.getBossRank9("白金点", 2) + "点，接下来的Boss更强大！");
								cm.spawnMob(怪物[cm.getBossRank9("白金Boss", 2)], 1, -165, 34);
								cm.setBossRankCount9("白金Boss", 1);
								cm.dispose();
							}
						} else {
							二级链接 = 1;
							cm.sendYesNo("#r将地图上怪物清除干净即可进入下一关！\r\n#k或者#b放弃挑战，送你出去？");
						}
					} else {
						二级链接 = 2;
						cm.sendYesNo("#r你已经完成了所有关卡！\r\n#b领取最终奖励？");
					}
				}
			}
		} else if (status == 1) {
			if (cm.getMap(889100011).getCharactersSize() != 1 && 二级链接 == 1) {
				cm.sendOk("尊贵的超级会员，您注意您的身价，您的朋友正在努力着··您确不顾兄弟想当逃兵！本次点击以系统模式告知岛友！");
			    cm.worldMessage(6, "【耻辱】   背信弃义的叛徒" + cm.getPlayer().getName() + "在与兄弟一起战斗时竟然想当逃兵！");
				cm.dispose();
				return;

			}
			if (二级链接 == 1) {
				cm.getMapFactory().getMap(889100011).playerCount() == 0; 
				cm.getMapFactory().getMap(889100011).killAllMonsters(true);
		        cm.warp(555000000, 0);
				cm.dispose();
				cm.setBossRankCount9("白金Boss", -cm.getBossRank9("白金Boss", 2));
			}
			if (二级链接 == 2) {
				cm.dispose();
				cm.warp(555000000, 0);
				cm.setBossRankCount9("白金点", 100);
				cm.getPlayer().addAccountExtraDamage(cm.getPlayer(), 500);
				cm.playerMessage(6, "恭喜你完成所的Boss关卡，额外获得100白金点和会员专属追加伤害值500点，您的白金点共：" + cm.getBossRank9("白金点", 2) + "点");
				cm.setBossRankCount9("白金Boss", -cm.getBossRank9("白金Boss", 2));
			}
		}
	}
}
