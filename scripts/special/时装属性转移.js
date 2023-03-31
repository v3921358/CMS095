var equiplist;
var equip;
var equipPotentiallist;
var equipPotential;
var equipMagnifylistlist;
var equipItem_old;
var equipItem_new;
var str = "";
var strs = "";
var strss = "";
var modea = 0;
var yesno = false;

function start() {
	var Editing = false; //false 开始
	if (Editing && !cm.getPlayer().isGM()) {
		cm.sendOk("维护中");
		cm.dispose();
		return;
	}
	status = -1;
	action(1, 0, 0);
}

function action(mode, type, selection) {
	if (mode == 1) {
		status++;
	} else if (mode === -1) {
		cm.dispose();
	} else if (status >= 0 && mode === 0) {

		cm.sendOk("感谢你的光临！");
		cm.dispose();
		return;
	}
	if (status == 0) {
		cm.sendSimple("你好，这里是时装属性转移功能区\r\n#r请保证要转移属性的时装在背包只有一件!!!#k\r\n不然人财两空可别砸电脑！手续费3000万哦！\r\n" +
			"#L0##b我要进行时装属性转移#l\r\n");

	} else if (status == 1) {
		if (selection == 0) {
			equiplist = cm.getEquipList();

			if (equiplist != null) {
				for (var i = 0; i < equiplist.size(); i++) {
					str += "#L" + i + "##v" + equiplist.get(i).getItemId() + "##z" + equiplist.get(i).getItemId() + "##k\r\n";

				}
			}
			if (str == "") {
				cm.sendOk("你目前没有装备需要转移！");
				cm.dispose();
				return;
			}
			cm.sendSimple("请选择“有属性的”时装作为原料（会损毁消失）。\r\n" + str);

		}

	} else if (status == 2) {
		equip = selection;
		equipItem_old = cm.getEquipStat(equiplist.get(equip).getPosition());
		equipPotentiallist = cm.getEquipList();
		if (equipPotentiallist != null) {
			for (var i = 0; i < equipPotentiallist.size(); i++) {
				if (equipPotentiallist.get(i).getItemId() != equiplist.get(equip).getItemId()) {
					strs += "#L" + i + "##v" + equipPotentiallist.get(i).getItemId() + "##z" + equipPotentiallist.get(i).getItemId() + "##k\r\n";
				}
			}
		}
		if (strs == "") {
			cm.sendOk("你目前没有装备需要转移！");
			cm.dispose();
			return;
		}
		cm.sendSimple("请选择“无属性的”时装作为属性转移后的成品。\r\n" + strs);
	} else if (status == 3) {
		equipPotential = selection;
		equipItem_new = cm.getEquipStat(equipPotentiallist.get(equipPotential).getPosition());
		if (cm.isCash(equiplist.get(equip).getItemId()) && cm.isCash(equipPotentiallist.get(equipPotential).getItemId())) {
			if (cm.getMeso() < 30000000) {
				cm.sendOk("手续费3000万！没钱到一边玩儿去！！");
				cm.dispose();
				return;
			}
			if ((equiplist.get(equip).getItemId() + "").substring(0, 3) == '180' || (equipPotentiallist.get(equipPotential).getItemId() + "").substring(0, 3) == '180') {
				cm.sendOk("宠物装备无法转移属性！！");
				cm.dispose();
				return;
			}
			if ((equiplist.get(equip).getItemId() + "").substring(0, 3) != (equipPotentiallist.get(equipPotential).getItemId() + "").substring(0, 3)) {
				cm.sendOk("选择的两个时装类别不同，无法转移属性！！");
				cm.dispose();
				return;
			}
			//cm.playerMessage("ID:" + equip + ":" + equipPotential + ":" + equipItem_old.getStr() + ":" + equipItem_new.getStr());
			equipItem_new.setStr(equipItem_old.getStr());
			equipItem_new.setDex(equipItem_old.getDex());
			equipItem_new.setInt(equipItem_old.getInt());
			equipItem_new.setLuk(equipItem_old.getLuk());
			equipItem_new.setWatk(equipItem_old.getWatk());
			equipItem_new.setMatk(equipItem_old.getMatk());
			equipItem_new.setEnhance(equipItem_old.getEnhance());
			cm.forceReAddItem(equipItem_new, 1);
			cm.removeAll(equiplist.get(equip).getItemId());
			cm.gainMeso(-30000000);
			cm.sendOk("转移成功！");

		} else {
			// cm.playerMessage("ID:" + equip + ":" + equipPotential+":"+equipItem_old.getStr()+":"+equipItem_new.getStr());
			cm.sendOk("两件必须都是时装！时装！时装！！");
		}

		cm.dispose();
		return;
	}
}