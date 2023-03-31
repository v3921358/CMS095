
/*

NPC版权:游戏盒团队
制作人：风雨
 */
var 全局经验倍率 = 100; // 最终获得经验是  环数  * 全局经验倍率  =获得经验
var 全局金币倍率 = 100; // 最终获得金币是  环数  * 全局金币倍率  =获得金币
var status = -1;
var 定义任务道具 = [4000016,4000019,4000000,4000011,4000003,4000004,4000005,4000006];
var 固定物品材料 = Array(
		// (物品id, 固定数量)

		//这里的物品id  必须在 定义任务道具  有这个物品才生效
		Array(4000000, 1),
		Array(4000006, 1)


		);
var 当前环数 = 0;
var 当前环数状态 = 0;
var 环数物品 = 0;
var 环数物品数量 = 0;
var 物品数量 = 0;
var 物品id = 0;
var 获得额外经验百分比 = 0;
var itemList = Array(//环数奖励
		//Array(物品id,1物品数量,隶属多少环),

		//物品id  0  代表额外个哦金币
		//物品id  1  代表额外经验  百分比
		//隶属领取等级对应上面的

		Array(0, 10000, 0), //给金币 1W
		Array(1, 1, 0), //给百分比经验
//========================================
		Array(0, 10000, 1), //给金币 1W
		Array(1, 1, 1), //给百分比经验
//========================================
		Array(0, 10000, 2), //给金币 1W
		Array(1, 1, 2), //给百分比经验
//========================================
	  Array(0, 10000, 3), //给金币 1W
		Array(1, 1, 3), //给百分比经验


//========================================
		Array(0, 10000, 4), //给金币 1w
		Array(1, 1, 4), //给百分比经验
//========================================
		Array(0, 10000, 5), //给金币 1w
		Array(1, 1, 4), //给百分比经验
		Array(4001266, 1, 5), //给劳动奖章
//========================================
		
		Array(0, 10000, 6), //给金币 20w
		Array(1, 1, 6), //给百分比经验
		Array(4001266, 1, 6), //给劳动奖章
//========================================
		Array(0, 10000, 7), //给金币 20w
		Array(1, 1, 7),  //给百分比经验
	Array(4001266, 1, 7) //给劳动奖章


	);

function start() {
	action(1, 0, 0);
}
function 获取任务信息() {
	物品id = Number(cm.getQuestRecord(444440).getCustomData()); //读取任务物品
	物品数量 = Number(cm.getQuestRecord(444441).getCustomData()); //读取任务个数
	//cm.getPlayer().dropMessage(任务物品id + "您已经失去重返本战场资格" + 任务物品数量);

}


function 接任务(a, b) {

	cm.getQuestRecord(444440).setCustomData(a); //记录任务物品
	cm.getQuestRecord(444441).setCustomData(b); //记录任务个数
	cm.getPlayer().setBossLog("悬赏状态");
	cm.getPlayer().saveToDB(false, false);

}

function action(mode, type, selection) {
	if (mode == 1) {
		status++;
	} else {
		if (status >= 0) {
			cm.dispose();
			return;
		}
		status--;
	}
	if (status == 0) {
		当前环数 = cm.getPlayer().getBossLog("今日悬赏");
		当前环数状态 = cm.getPlayer().getBossLog("悬赏状态");
		var selStr = "您好，每日悬赏：110-200级可以挑战 共8轮任务\r\n";
		if (当前环数 >= 8) {
			cm.sendOk("#b任务上限,每天0点刷新");
			cm.dispose();
			return;
		}
		if (cm.getPlayer().getLevel() < 110) { //判断等级
			cm.sendOk("#b你当前等级小于150级无法在接任务");
			cm.dispose();
			return;
		}
				if (cm.getPlayer().getLevel() > 200) { //判断等级
			cm.sendOk("#b你当前等级大于200级无法在接任务");
			cm.dispose();
			return;
		}
		if (当前环数 <= 6) {
		if (当前环数状态 <= 0) { //未接任务

			物品数量 = Math.floor(Math.random() * 0) + 50;
			物品id = 定义任务道具[Math.floor(Math.random() * 定义任务道具.length)];

			/*for (var jqa = 0; jqa < 固定物品材料.length; jqa++) {
				if (固定物品材料[jqa][0] == 物品id) {
					物品数量 = 固定物品材料[jqa][1];

				}

			}*/

			selStr += "#r#接取 第 " + 当前环数 + " 环#l#k\r\n\r\n"
			selStr += "需要：\r\n"
			selStr += "#v" + 物品id + "# X " + 物品数量 + "\r\n"
			接任务(物品id, 物品数量)
		} else { //接了
			获取任务信息();

			if (物品id == 0) {
				/* for (var jb = 0; jb < 当前环数状态; jb++) {
				cm.killbossloga("悬赏状态");
				} */

				cm.sendOk("#b任务异常！,请重新接任务吧b");
				cm.dispose();
				return;
			}

			selStr += "#r#L" + 当前环数 + "#提交 第 " + 当前环数 + " 环#l#k\r\n\r\n"
			selStr += "--------------------------------------------------\r\n需要：#v" + 物品id + "#  [#r" + cm.getPlayer().getItemQuantity(物品id, false) + "#k/" + 物品数量 + "]\r\n--------------------------------------------------\r\n"
		}
		} else {
			if (当前环数状态 <= 0) { //未接任务
				if (当前环数 == 7) {
					物品数量 = 固定物品材料[0][1];
					物品id = 固定物品材料[0][0];
				} else {
					物品数量 = 固定物品材料[1][1];
					物品id = 固定物品材料[1][0];
				}
			selStr += "#r#接取 第 " + 当前环数 + " 环#l#k\r\n\r\n"
			selStr += "需要：\r\n"
			selStr += "#v" + 物品id + "# X " + 物品数量 + "\r\n"
			接任务(物品id, 物品数量)
		} else { //接了
			获取任务信息();

			if (物品id == 0) {
				/* for (var jb = 0; jb < 当前环数状态; jb++) {
				cm.killbossloga("悬赏状态");
				} */

				cm.sendOk("#b任务异常！,请重新接任务吧b");
				cm.dispose();
				return;
			}

			selStr += "#r#L" + 当前环数 + "#提交 第 " + 当前环数 + " 环#l#k\r\n\r\n"
			selStr += "--------------------------------------------------\r\n需要：#v" + 物品id + "#  [#r" + cm.getPlayer().getItemQuantity(物品id, false) + "#k/" + 物品数量 + "]\r\n--------------------------------------------------\r\n"
		}
		}
		selStr += "奖励：\r\n"
		selStr += "固定经验：" + 全局经验倍率 * 当前环数 + "\r\n"
		selStr += "固定金币：" + 全局金币倍率 * 当前环数 + "\r\n"
		for (var j = 0; j < itemList.length; j++) {

			if (itemList[j][2] == 当前环数) { //判断物品标识

				if (itemList[j][0] == 0) { //金币
					selStr += "#r额外金币：" + itemList[j][1] + "\r\n"
				} else if (itemList[j][0] == 1) {
					获得额外经验百分比 = (cm.getPlayer().getNeededExp() / 100) * itemList[j][1];
					selStr += "#r额外百分比经验：" + 获得额外经验百分比 + "\r\n"

				} else { //不是金币
					selStr += "#v" + itemList[j][0] + "# X " + itemList[j][1] + "\r\n"
				}

			}

		}
        selStr += "#b#L99999995#查该悬赏物品出处#l\r\n";
		selStr += "#b#L99999999#查看所有悬赏奖励#l\r\n";
		selStr += "#b#L99999997#重置悬赏任务#l\r\n";
		selStr += "#b#L99999998#查看所有环需要材料#l\r\n";
		//selStr += "#b#L99999996#跳过环任务#l\r\n";
		if (cm.getPlayer().isGM()) {
			//selStr += "#b#L99999998#查看所有环需要材料#l\r\n";
		}
		cm.sendSimple(selStr);

	} else if (status == 1) {
		if (selection == 99999999) {
			var txt = "";
			for (var j = 0; j < itemList.length; j++) {

				if (itemList[j][0] == 0) { //金币
					txt += itemList[j][2] + "环 金币：" + itemList[j][1] + "\r\n"
				} else if (itemList[j][0] == 1) {

					txt += itemList[j][2] + "环  当前等级百分比经验：" + itemList[j][1] + "%\r\n"
				} else { //不是金币
					txt += itemList[j][2] + "环  #v" + itemList[j][0] + "# X " + itemList[j][1] + "\r\n"
				}

			}

			cm.sendOk(txt);
			cm.dispose();
			return;
        } else if (selection == 99999995) {
			cm.sendOk(cm.checkDropper(物品id));
			cm.dispose();
			return;
		} else if (selection == 99999998) {

			var txt2 = "";
			for (var jg = 0; jg < 定义任务道具.length; jg++) {

				txt2 += "#v" + 定义任务道具[jg] + "##z" + 定义任务道具[jg] + "#  "

			}

			cm.sendOk(txt2);
			cm.dispose();
			return;
		} else if (selection == 99999997) {
			if (cm.haveItem(4001126, 66) == false) {
				cm.sendOk("枫叶不足66无法重接取");
				cm.dispose();
				return;
			} else {
				cm.killbossloga("悬赏状态");
				//cm.getPlayer().setBossLog("今日悬赏");
				cm.gainItem(4001126, -66)
				cm.sendOk("成功");
				cm.dispose();
				return;
			}
		} else if (selection == 99999996) {
			if (cm.haveItem(4001126, 66) == false) {
				cm.sendOk("枫叶不足66无法重接取");
				cm.dispose();
				return;
			} else {
				cm.killbossloga("悬赏状态");
				cm.getPlayer().setweekLog("今日悬赏");
				cm.gainItem(4001126, -66)
				cm.sendOk("成功");
				cm.dispose();
				return;
			}
		} else {

			当前环数 = selection;
			if (cm.getInventory(1).isFull(0)) { //判断第一个也就是装备栏的装备栏是否有一个空格
				cm.sendOk("#b请保证装备栏位至少有1个空格,否则无法兑换.");
				cm.dispose();
			} else if (cm.getInventory(2).isFull(0)) { //判断第二个也就是消耗栏的装备栏是否有一个空格
				cm.sendOk("#b请保证消耗栏位至少有1个空格,否则无法兑换.");
				cm.dispose();
			} else if (cm.getInventory(3).isFull(0)) { //判断第三个也就是设置栏的装备栏是否有一个空格
				cm.sendOk("#b请保证设置栏位至少有1个空格,否则无法兑换.");
				cm.dispose();
			} else if (cm.getInventory(4).isFull(0)) { //判断第四个也就是其它栏的装备栏是否有一个空格
				cm.sendOk("#b请保证其它栏位至少有1个空格,否则无法兑换.");
				cm.dispose();
			} else if (cm.getInventory(5).isFull(0)) { //判断第五个也就是现金栏的装备栏是否有一个空格
				cm.sendOk("#b请保证现金栏位至少有1个空格,否则无法兑换.");
				cm.dispose();

			} else if (cm.haveItem(物品id, 物品数量) == false) { //判断是否领过
				cm.sendOk("#b物品不足！");
				cm.dispose();
			} else {
				cm.gainItem(物品id, -物品数量);
				cm.gainMeso(当前环数 * 全局金币倍率); //给金币全局经验倍率
				cm.gainExp(当前环数 * 全局经验倍率); //给经验

				for (var j = 0; j < itemList.length; j++) {

					if (itemList[j][2] == 当前环数) { //判断物品标识

						if (itemList[j][0] == 0) { //金币

							cm.gainMeso(itemList[j][1]); //给金币
						} else if (itemList[j][0] == 1) {

							cm.gainExp(获得额外经验百分比); //给经验
						} else { //不是金币

							cm.gainItem(itemList[j][0], itemList[j][1]);
						}

					}

				}
				cm.killbossloga("悬赏状态"); //删除数据库onetimelog表 符合自身的表
				cm.getPlayer().setBossLog("今日悬赏");
				cm.sendOk("#b成功领取！");
				cm.dispose();

			}

		}
	}
}
