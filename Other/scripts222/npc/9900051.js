var status = 0;
var secondLink = 0; //二级链接
var ca = java.util.Calendar.getInstance();
var cishu = ca.get(java.util.Calendar.YEAR);
var cishu2 = ca.get(java.util.Calendar.DATE);
var cishu1 = ca.get(java.util.Calendar.MONTH) + 1;
var shijian = (cishu * 10000) + (cishu1 * 100) + cishu2; //获取日期
var hour = ca.get(java.util.Calendar.HOUR_OF_DAY); //获得小时
var minute = ca.get(java.util.Calendar.MINUTE); //获得分钟
var second = ca.get(java.util.Calendar.SECOND); //获得秒
var str;
var dex;
var int;
var luk;
var watk;
var matk;
var wdef;
var mdef;
var hp;
var mp;
var UpgradeSlots;
var select_1_point; //选择第一件武器
var select_2_point; //选择第二件武器
var selected1; //确认选择的1武器
var selected2; //确认选择的2武器
var getfirstlevel; //获取选择的第一件装备等级
var weponminlevel = 100 //武器最低等级限制
var poprate; //属性倍数
var weponEnchantsLV; // 强化级别固定数值
var checkEnchantsLV; //判定强化级别
//装备列表
var weaponlist = [1002972];
//属性加成 力量、敏捷、智力、运气、物理、魔法、物防、魔防、血、蓝、强化次数 共11个参数
var attrlist = [
	[2, 2, 2, 2, 2, 2, 2, 2, 25, 25, 2],//1级属性+
	[2, 2, 2, 2, 2, 2, 2, 2, 25, 25, 2],//1级属性+
	[2, 2, 2, 2, 2, 2, 2, 2, 25, 25, 2],//1级属性+
	[2, 2, 2, 2, 2, 2, 2, 2, 25, 25, 2],//1级属性+
	[2, 2, 2, 2, 2, 2, 2, 2, 25, 25, 2],//1级属性+
	[2, 2, 2, 2, 2, 2, 2, 2, 25, 25, 2],//1级属性+
	[2, 2, 2, 2, 2, 2, 2, 2, 25, 25, 2],//1级属性+
	[2, 2, 2, 2, 2, 2, 2, 2, 25, 25, 2],//1级属性+
	[2, 2, 2, 2, 2, 2, 2, 2, 25, 25, 2],//1级属性+
	[2, 2, 2, 2, 2, 2, 2, 2, 25, 25, 2],//1级属性+
	[2, 2, 2, 2, 2, 2, 2, 2, 25, 25, 2],//1级属性+
	[2, 2, 2, 2, 2, 2, 2, 2, 25, 25, 2],//1级属性+
	[2, 2, 2, 2, 2, 2, 2, 2, 25, 25, 2],//1级属性+
	[2, 2, 2, 2, 2, 2, 2, 2, 25, 25, 2],//1级属性+
	[2, 2, 2, 2, 2, 2, 2, 2, 25, 25, 2],//1级属性+
	[2, 2, 2, 2, 2, 2, 2, 2, 25, 25, 2],//1级属性+
	[2, 2, 2, 2, 2, 2, 2, 2, 25, 25, 2],//1级属性+
	[2, 2, 2, 2, 2, 2, 2, 2, 25, 25, 2],//1级属性+
	[2, 2, 2, 2, 2, 2, 2, 2, 25, 25, 2],//1级属性+
	[2, 2, 2, 2, 2, 2, 2, 2, 25, 25, 2],//1级属性+
	[2, 2, 2, 2, 2, 2, 2, 2, 25, 25, 2],//1级属性+
	[2, 2, 2, 2, 2, 2, 2, 2, 25, 25, 2],//1级属性+
	[2, 2, 2, 2, 2, 2, 2, 2, 25, 25, 2],//1级属性+
	[2, 2, 2, 2, 2, 2, 2, 2, 25, 25, 2],//1级属性+
	[2, 2, 2, 2, 2, 2, 2, 2, 25, 25, 2],//1级属性+
	[2, 2, 2, 2, 2, 2, 2, 2, 25, 25, 2],//1级属性+
	[2, 2, 2, 2, 2, 2, 2, 2, 25, 25, 2],//1级属性+
	[2, 2, 2, 2, 2, 2, 2, 2, 25, 25, 2],//1级属性+
	[2, 2, 2, 2, 2, 2, 2, 2, 25, 25, 2],//1级属性+
	[2, 2, 2, 2, 2, 2, 2, 2, 25, 25, 2],//1级属性+
	[2, 2, 2, 2, 2, 2, 2, 2, 25, 25, 2],//1级属性+
	[2, 2, 2, 2, 2, 2, 2, 2, 25, 25, 2],//1级属性+
	[2, 2, 2, 2, 2, 2, 2, 2, 25, 25, 2],//1级属性+
	[2, 2, 2, 2, 2, 2, 2, 2, 25, 25, 2],//1级属性+
	[2, 2, 2, 2, 2, 2, 2, 2, 25, 25, 2],//1级属性+
	[2, 2, 2, 2, 2, 2, 2, 2, 25, 25, 2],//1级属性+
	[2, 2, 2, 2, 2, 2, 2, 2, 25, 25, 2],//1级属性+
	[2, 2, 2, 2, 2, 2, 2, 2, 25, 25, 2],//1级属性+
	[2, 2, 2, 2, 2, 2, 2, 2, 25, 25, 2],//1级属性+
	[2, 2, 2, 2, 2, 2, 2, 2, 25, 25, 2],//1级属性+
	[2, 2, 2, 2, 2, 2, 2, 2, 25, 25, 2],//1级属性+
	[2, 2, 2, 2, 2, 2, 2, 2, 25, 25, 2],//1级属性+
	[2, 2, 2, 2, 2, 2, 2, 2, 25, 25, 2],//1级属性+
	[2, 2, 2, 2, 2, 2, 2, 2, 25, 25, 2],//1级属性+
	[2, 2, 2, 2, 2, 2, 2, 2, 25, 25, 2],//1级属性+
	[2, 2, 2, 2, 2, 2, 2, 2, 25, 25, 2],//1级属性+
	[2, 2, 2, 2, 2, 2, 2, 2, 25, 25, 2],//1级属性+
	[2, 2, 2, 2, 2, 2, 2, 2, 25, 25, 2],//1级属性+
	[2, 2, 2, 2, 2, 2, 2, 2, 25, 25, 2],//1级属性+
	[2, 2, 2, 2, 2, 2, 2, 2, 25, 25, 2],//1级属性+
];
var itemlist = [
	[250000000, 25000, [[1002972, 1]]],//1级材料，金币、点券、材料[id，数量]
	[250000000, 25000, [[1002972, 1]]],//1级材料，金币、点券、材料[id，数量]
	[250000000, 25000, [[1002972, 1]]],//1级材料，金币、点券、材料[id，数量]
	[250000000, 25000, [[1002972, 1]]],//1级材料，金币、点券、材料[id，数量]
	[250000000, 25000, [[1002972, 10]]],//1级材料，金币、点券、材料[id，数量]
	[250000000, 25000, [[1002972, 20]]],//1级材料，金币、点券、材料[id，数量]
	[250000000, 25000, [[1002972, 2]]],//1级材料，金币、点券、材料[id，数量]
	[250000000, 25000, [[1002972, 2]]],//1级材料，金币、点券、材料[id，数量]
	[250000000, 25000, [[1002972, 2]]],//1级材料，金币、点券、材料[id，数量]
	[250000000, 25000, [[1002972, 20]]],//1级材料，金币、点券、材料[id，数量]
	[250000000, 25000, [[1002972, 30]]],//1级材料，金币、点券、材料[id，数量]
	[250000000, 25000, [[1002972, 30]]],//1级材料，金币、点券、材料[id，数量]
	[250000000, 25000, [[1002972, 3]]],//1级材料，金币、点券、材料[id，数量]
	[250000000, 25000, [[1002972, 3]]],//1级材料，金币、点券、材料[id，数量]
	[250000000, 25000, [[1002972, 3]]],//1级材料，金币、点券、材料[id，数量]
	[250000000, 25000, [[1002972, 40]]],//1级材料，金币、点券、材料[id，数量]
	[250000000, 25000, [[1002972, 4]]],//1级材料，金币、点券、材料[id，数量]
	[250000000, 25000, [[1002972, 4]]],//1级材料，金币、点券、材料[id，数量]
	[250000000, 25000, [[1002972, 4]]],//1级材料，金币、点券、材料[id，数量]
	[250000000, 25000, [[1002972, 4]]],//1级材料，金币、点券、材料[id，数量]
	[250000000, 25000, [[1002972, 50]]],//1级材料，金币、点券、材料[id，数量]
	[250000000, 25000, [[1002972, 5]]],//1级材料，金币、点券、材料[id，数量]
	[250000000, 25000, [[1002972, 5]]],//1级材料，金币、点券、材料[id，数量]
	[250000000, 25000, [[1002972, 5]]],//1级材料，金币、点券、材料[id，数量]
	[250000000, 25000, [[1002972, 5]]],//1级材料，金币、点券、材料[id，数量]
	[250000000, 25000, [[1002972, 60]]],//1级材料，金币、点券、材料[id，数量]
	[250000000, 25000, [[1002972, 6]]],//1级材料，金币、点券、材料[id，数量]
	[250000000, 25000, [[1002972, 6]]],//1级材料，金币、点券、材料[id，数量]
	[250000000, 25000, [[1002972, 6]]],//1级材料，金币、点券、材料[id，数量]
	[250000000, 25000, [[1002972, 6]]],//1级材料，金币、点券、材料[id，数量]
	[250000000, 25000, [[1002972, 70]]],//1级材料，金币、点券、材料[id，数量]
	[250000000, 25000, [[1002972, 7]]],//1级材料，金币、点券、材料[id，数量]
	[250000000, 25000, [[1002972, 7]]],//1级材料，金币、点券、材料[id，数量]
	[250000000, 25000, [[1002972, 7]]],//1级材料，金币、点券、材料[id，数量]
	[250000000, 25000, [[1002972, 7]]],//1级材料，金币、点券、材料[id，数量]
	[250000000, 25000, [[1002972, 80]]],//1级材料，金币、点券、材料[id，数量]
	[250000000, 25000, [[1002972, 8]]],//1级材料，金币、点券、材料[id，数量]
	[250000000, 25000, [[1002972, 8]]],//1级材料，金币、点券、材料[id，数量]
	[250000000, 25000, [[1002972, 8]]],//1级材料，金币、点券、材料[id，数量]
	[250000000, 25000, [[1002972, 8]]],//1级材料，金币、点券、材料[id，数量]
	[250000000, 25000, [[1002972, 90]]],//1级材料，金币、点券、材料[id，数量]
	[250000000, 25000, [[1002972, 9]]],//1级材料，金币、点券、材料[id，数量]
	[250000000, 25000, [[1002972, 9]]],//1级材料，金币、点券、材料[id，数量]
	[250000000, 25000, [[1002972, 9]]],//1级材料，金币、点券、材料[id，数量]
	[250000000, 25000, [[1002972, 9]]],//1级材料，金币、点券、材料[id，数量]
	[250000000, 25000, [[1002972, 100]]],//1级材料，金币、点券、材料[id，数量]
	[250000000, 25000, [[1002972, 10]]],//1级材料，金币、点券、材料[id，数量]
	[250000000, 25000, [[1002972, 10]]],//1级材料，金币、点券、材料[id，数量]
	[250000000, 25000, [[1002972, 10]]],//1级材料，金币、点券、材料[id，数量]
	[250000000, 25000, [[1002972, 10]]],//1级材料，金币、点券、材料[id，数量]

];
//强化名称（个数决定强化次数上限，顺序决定等次）
var topnames = ["强化1", 
                "强化2", 
                "强化3",  
                "强化4", 
                "强化5", 
                "强化6", 
                "强化7", 
                "强化8",
                "强化9",
                "强化10",
                "强化11",
                "强化12",
                "强化13",
                "强化14",
                "强化15",
                "强化16",
                "强化17",
                "强化18",
                "强化19",
                "强化20",
                "强化21",
                "强化22",
                "强化23",
                "强化24",
                "强化25",
                "强化26",
                "强化27",
                "强化28",
                "强化29",
                "强化30",
                "强化31",
                "强化32",
                "强化33",
                "强化34",
                "强化35",
                "强化36",
                "强化37",
                "强化38",
                "强化39",
                "强化40",
                "强化41",
                "强化42",
                "强化43",
                "强化44",
                "强化45",
                "强化46",
                "强化47",
                "强化48",
                "强化49",
                "绝世狠人"];
var toplevel;

function start() {
	status = -1;
	action(1, 0, 0);
}

function action(mode, type, selection) {
	if (mode === -1) {
		cm.dispose();
	} else {
		if (status >= 0 && mode === 0) {
			cm.sendOk("感谢你的光临！");
			cm.dispose();
			return;
		}

		status = status + (mode === 1 ? 1 : -1);
		if (selection == 111) {
			status = 0;
		}
		if (status == 0) {
			var txt = "\t\t\t\t#e#d【装备强化系统】\r\n" +
				"#d装备强化依次全属性+2攻击+2血和蓝+25砸卷次数+2#k\r\n" +
				"#d强化等级: #r";
			for (var key in topnames) {
				txt += (topnames[key] + ",")
			}
			txt += "#k\r\n" +
				"#d提示: #r建议把需要强化的装备放在#b第一格位置#k\r\n#e\r\n" +
				"  进行强化的装备[#r未选#k] \r\n" +
				"#L1##e#r#b选择进行强化的装备#d#l#k\r\n\r\n"+
				"#L2##e#r#d查看可以强化的装备#d#l#k\r\n ";
			cm.sendSimple(txt);
		} else if (status == 1) {
			if (selection == 1) {
				var it;
				var texts = "#r---------------请选择您背包中的装备----------------#b\r\n";
				var inv = cm.getInventory(1);
				for (var i = 0; i <= 100; i++) {
					it = inv.getItem(i);
					if (it != null && cm.isCash(it.getItemId()) != true&&weaponlist.indexOf(it.getItemId())>=0) {
						texts += "#L" + i + "##v" + it.getItemId() + "# #b#z" + it.getItemId() + "# "
						if (it.getHpR() >= 1) {
							texts += " #k强化等级: #r" + it.getOwner() + "#l#b\r\n"
						}
						if (it.getHpR() == 0) {
							texts += " #k强化等级: #r" + it.getOwner() + "#l#b\r\n"
						}
					}
				}
				secondLink = 1;
				cm.sendSimple(texts);
			}else if (selection==2){
				var it;
				var texts = "#r---------------下列是可强化的装备----------------#b\r\n";
				for (var i = 0; i < weaponlist.length; i++) {
					texts+="#v"+weaponlist[i]+"##z"+weaponlist[i]+"#\r\n";
				}
				cm.sendOk(texts);
				cm.dispose();
			}
		} else if (status == 2) {
			if (secondLink == 1) {
				select_1_point = selection;
				selected1 = cm.getInventory(1).getItem(select_1_point);
				getfirstlevel = cm.getReqLevel(selected1.getItemId());
				if (selected1.getHpR() == 0) {
					selected1.setHpR(0);
					//selected1.setOwner("已签名");
				}
				if (weaponlist.indexOf(selected1.getItemId()) != -1) {
					secondLink = 2;
					var title = selected1.getOwner();
					var txt = "\t\t\t\t#e#d【装备强化系统】\r\n" +
						"当前进行强化的装备[#v" + selected1.getItemId() + "#]\r\n";
					for (var key in topnames) {
						if (topnames[key] == title) {
							if (title == topnames[topnames.length - 1]) {
								cm.sendOk("装备已到最大强化等级");
								cm.dispose();
								return;
							}
							toplevel = (Number(key) + 1);



						}
					}
					cm.playerMessage(title)
					//if (!isInArray(topnames, title) && title != null && title != "") {
					//	cm.sendOk("#r你所选择的装备不能强化哦，#k请重新选择...#k");
					//	cm.dispose();
					//}
					if (title == null || title == ""||!isInArray(topnames, title)) {
						toplevel = 0;
					}
					cm.playerMessage(toplevel)
					str = (selected1.getStr() + attrlist[toplevel][0]);
					dex = (selected1.getDex() + attrlist[toplevel][1]);
					int = (selected1.getInt() + attrlist[toplevel][2]);
					luk = (selected1.getLuk() + attrlist[toplevel][3]);
					watk = (selected1.getWatk() + attrlist[toplevel][4]);
					matk = (selected1.getMatk() + attrlist[toplevel][5]);
					wdef = (selected1.getWdef() + attrlist[toplevel][6]);
					mdef = (selected1.getMdef() + attrlist[toplevel][7]);
					hp = (selected1.getHp() + attrlist[toplevel][8]);
					mp = (selected1.getMp() + attrlist[toplevel][9]);
					UpgradeSlots = (selected1.getUpgradeSlots() + attrlist[toplevel][10]);

					txt +="【成功率"+ attrlist[toplevel][11]+"%】强化后的属性为:\r\n#r力量: #b" + str + "\r\n" +
						"#r敏捷: #b" + dex + "\r\n" +
						"#r智力: #b" + int + "\r\n" +
						"#r运气: #b" + luk + "\r\n" +
						"#r攻击力: #b" + watk + "\r\n" +
						"#r魔法力: #b" + matk + "\r\n" +
						"#r物防: #b" + wdef + "\r\n" +
						"#r魔防: #b" + mdef + "\r\n" +
						"#r血量: #b" + hp + "\r\n" +
						"#r魔量: #b" + mp + "\r\n" +
						"#r砸卷次数: #b" + UpgradeSlots + "\r\n" +
						"#k#e\r\n";
					txt += "#L1##e#r#b开始进行强化#d#l#k\r\n\r\n";
					cm.sendSimple(txt);
				} else {
					cm.sendOk("#r你所选择的装备不能强化哦，#k请重新选择...#k");
					cm.dispose();
				}
			}
		} else if (status == 3) {
			if (secondLink == 2) {
				if (selection == 1) {
					var add = "#e#d【本次强化所需材料】\r\n\r\n";
					add += "#b#v5200002#" + itemlist[toplevel][0] + "金币#b\r\n";
					add += "#b#v5200000#" + itemlist[toplevel][1] + " 点券#b\r\n";
					if (selected1.getOwner() == topnames[topnames.length - 1]) {
						cm.sendOk("装备已到最大等级");
						cm.dispose();
						return;
					} else {
						for (var key in itemlist[toplevel][2]) {
							var itemName = cm.getItemName(itemlist[toplevel][2][key][0]);
							add += itemName;
							var currentItemQuantity = cm.getPlayer().getItemQuantity(itemlist[toplevel][2][key][0], true);
							var color = "#g";
							if (currentItemQuantity < itemlist[toplevel][2][key][1])
								color = "#r";
							add += color + currentItemQuantity + " / " + itemlist[toplevel][2][key][1] + " 个#b\r\n";
						}
					}
					add += "#k\r\n\r\n- #e#d管理提示：#n#b点是进行制作。点否返回上一页.#k";
					cm.sendYesNo(add);

				} else {
					cm.dispose();
					return;
				}
			}
		} else if (status == 4) {
			var flag = true;
			var getWeapon = selected1.copy();
			for (var key in itemlist[toplevel][2]) {
				var itemId = itemlist[toplevel][2][key][0];
				var itemQuantity = itemlist[toplevel][2][key][1];
				if (!cm.haveItem(itemId, itemQuantity)) {
					flag = false;
					break;
				}
			}
			if (cm.getMeso() < itemlist[toplevel][0] || cm.getPlayer().getCSPoints(1) < itemlist[toplevel][1]) {
				flag = false;
			}
			if (flag) {
				if (!cm.canHold(selected1.getItemId())) {
					cm.sendOk("装备栏空间不足，请整理后重新制作！");
					cm.dispose();
					return;
				}
				for (var key in itemlist[toplevel][2]) {
					var itemId = itemlist[toplevel][2][key][0];
					var itemQuantity = itemlist[toplevel][2][key][1];
					cm.gainItem(itemId, -itemQuantity);
				}
				cm.gainMeso(-itemlist[toplevel][0]);
				cm.gainNX(-itemlist[toplevel][1]);
				if(Math.random()*100>attrlist[toplevel][11]){
					cm.sendSimple("哎呀！强化失败了，好可惜~\r\n#r#L111#继续强化#l#k");
				}else{
					getWeapon.setStr(str);
					getWeapon.setDex(dex);
					getWeapon.setInt(int);
					getWeapon.setLuk(luk);
					getWeapon.setWatk(watk);
					getWeapon.setMatk(matk);
					getWeapon.setWdef(wdef);
					getWeapon.setMdef(mdef);
					getWeapon.setHp(hp);
					getWeapon.setMp(mp);
					getWeapon.setUpgradeSlots(UpgradeSlots);
					getWeapon.setOwner(topnames[toplevel]);
					cm.removeSlot(1, select_1_point, 1); //消失的装备
					Packages.server.MapleInventoryManipulator.addFromDrop(cm.getC(), getWeapon, false); //强化的装备获取
					// cm.sendOk("强化成功 强化等级为: #r" + getWeapon.getOwner() + "#k#l\r\n  ");
					cm.getPlayer().dropMessage(1, "强化成功 强化等级为: " + getWeapon.getOwner() + "\r\n请打开背包查看吧.");
					var text = "[恭喜]" + cm.getPlayer().getName() + " : " + "他/她的"+ cm.getItemName(selected1.getItemId())+"已经达到【" + getWeapon.getOwner() + "】啦！";
					cm.worldMessage(6, text);
					cm.sendSimple("强化成功 强化等级为: #r" + getWeapon.getOwner() + "#k#l\r\n  "+"#r#L111#继续强化#l#k");
				}
				
			} else {
				cm.sendOk("材料不足，无法强化！");
				cm.dispose();
			}
		}
	}
}
function isInArray(arr, value) {
	for (var i = 0; i < arr.length; i++) {
		if (value === arr[i]) {
			return true;
		}
	}
	return false;
}