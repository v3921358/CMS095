load('nashorn:mozilla_compat.js');
importPackage(Packages.tools.packet);
var equiplist;
var equip;
var equipPotentiallist;
var equipPotential;
var equipMagnifylistlist;
var str = "";
var strs = "";
var strss = "";
var modea = 0;
var yesno = false;

function start() {
    var Editing = false //false 开始
    if (Editing) {
        cm.dispose();
        return;
    }
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {

    if (mode == 1) {
        status++;
    } else if (mode == 0) {
        status--;
    } else {
        cm.dispose();
        return;
    }
	if (status >= 0 && mode === 0) {

         
            cm.dispose();
            return;
        }
    if (selection == 300) {
        status = 5;
    }
    if (status == 0) {
        cm.sendSimple("你好，我这里可以快速洗装备潜能。\r\n\r\n\r\n\r\n\r\n\r\n\r\n" +
            "#L0#开始洗潜能#l\r\n");
		
		 
    } else if (status == 1) {
        if (selection == 0) {
            equiplist = cm.getEquipList();
            if (equiplist != null && equiplist.size() > 0) {
                for (var i = 0; i < equiplist.size(); i++) {
                    str += "#L" + i + "##i" + equiplist.get(i).getItemId() + "##t" + equiplist.get(i).getItemId() + "##k\r\n";
                }
            }
            if (str == "") {
                cm.sendOk("您目前没有装备可使用潜能方块");
                cm.dispose();
                return;
            }
            cm.sendSimple("选择你想要使用潜能方块的装备。\r\n" + str);

        }

    } else if (status == 2) {
        equip = selection;
        equipPotentiallist = cm.getPotentiallist();
        if (equipPotentiallist != null && equipPotentiallist.size() > 0) {
            for (var i = 0; i < equipPotentiallist.size(); i++) {
                strs += "#L" + i + "##i" + equipPotentiallist.get(i).getItemId() + "##t" + equipPotentiallist.get(i).getItemId() + "##k\r\n";
            }
        }
        if (strs == "") {
            cm.sendOk("您目前没有可使用潜能方块");
            cm.dispose();
            return;
        }
        cm.sendSimple("选择你想要使用的潜能方块。\r\n" + strs);
    } else if (status == 3) {
        equipPotential = selection;

        equipMagnifylistlist = cm.getMagnifylist();
        if (equipMagnifylistlist != null && equipMagnifylistlist.size() > 0) {
            for (var i = 0; i < equipMagnifylistlist.size(); i++) {
                strss += "#L" + i + "##i" + equipMagnifylistlist.get(i).getItemId() + "##t" + equipMagnifylistlist.get(i).getItemId() + "##k\r\n";
            }
        }
        if (strss == "") {
            cm.sendOk("您目前没有可使用放大镜");
            cm.dispose();
            return;
        }
        cm.sendSimple("选择你想要使用的放大镜。\r\n" + strss);



        /*yesno = cm.UseUpgradeScroll(equiplist.get(equip), equipscrolllist.get(equipscroll), 10);
         if (yesno) {
         cm.sendOk("恭喜您强化成功。");
         } else {
         cm.sendOk("很抱歉，强化失败。");
         }
         cm.dispose();
         return;*/
    } else if (status == 4) {
        equipMagnify = selection;
        if(equipMagnifylistlist.size()>cm.getMagnifylist().size()||equipPotentiallist.size()>cm.getPotentiallist().size()){
            cm.sendOk("你所选择的道具或放大镜已用光！");
            cm.dispose();
            return;
        }
        // if (cm.getItem(2, equipMagnifylistlist.get(equipMagnify)) == null) {
        //     cm.sendOk("系统繁忙请稍后再试2。");
        //     cm.dispose();
        //     return;
        // }

        // if (cm.getItem(5, equipPotentiallist.get(equipPotential)) == null) {
        //     cm.sendOk("系统繁忙请稍后再试1。");
        //     cm.dispose();
        //     return;
        // }

        var abc;
        if (cm.haveItem(equipPotentiallist.get(equipPotential).getItemId(), 1)) {
            abc = cm.UseCube(equiplist.get(equip), equipPotentiallist.get(equipPotential));
        } else {
            cm.sendOk("你的方块少于1个，无法使用快速洗潜。");
            cm.dispose();
            return;
        }


        if (abc == 1) {
            cm.sendOk("系统繁忙，请稍后再试。");
            cm.dispose();
            return;
        }
        if (abc == 2) {
            cm.sendOk("你的等级不足10等，无法使用。");
            cm.dispose();
            return;
        }
        if (abc == 12) {
            cm.sendOk("你的等级不足70等，无法使用。");
            cm.dispose();
            return;
        }
        if (abc == 3) {
            cm.sendOk("你的魔方数量不足。");
            cm.dispose();
            return;
        }
        if (abc == 4) {
            cm.sendOk("该装备没有潜能或已经超过该方块能改变的潜能等级。");
            cm.dispose();
            return;
        }
        if (abc == 5) {
            cm.sendOk("请检查你的背包是否已满。。");
            cm.dispose();
            return;
        }




        var cba;
        if (cm.haveItem(equipMagnifylistlist.get(equipMagnify).getItemId(), 1)) {
            cba = cm.UseMagnify(equiplist.get(equip), equipMagnifylistlist.get(equipMagnify));

        } else {
            cm.sendOk("你的放大镜少于1个，无法使用快速洗潜。");
            cm.dispose();
            return;
        }
        if (cba == 1) {
            cm.sendOk("系统繁忙，请稍后再试。");
            cm.dispose();
            return;
        }
        cm.getPlayer().marriage();
        cm.getClient().getSession().write(MaplePacketCreator.enableActions());
        var Potential1 = cm.getPotentialInfo2(equiplist.get(equip).getItemId(), cm.getEquipStat(equiplist.get(equip).getPosition()).getPotential1());
        var Potential2 = cm.getPotentialInfo2(equiplist.get(equip).getItemId(), cm.getEquipStat(equiplist.get(equip).getPosition()).getPotential2());
        var Potential3 = cm.getPotentialInfo2(equiplist.get(equip).getItemId(), cm.getEquipStat(equiplist.get(equip).getPosition()).getPotential3());
        var ss = "\r\n#e潜能1#k" + Potential1 + "\r\n潜能2" + Potential2 + "\r\n潜能3" + Potential3 + "\r\n";
        var sss = "#r#L300#继续洗潜#l#k";
        cm.sendSimple("使用潜能方块成功！" + ss + sss);

    } else if (status == 5) {
        if (selection != 300) {
            cm.dispose();
            return;
        }

        var abc;
        if(equipMagnifylistlist.size()>cm.getMagnifylist().size()||equipPotentiallist.size()>cm.getPotentiallist().size()){
            cm.sendOk("你所选择的道具或放大镜已用光！");
            cm.dispose();
            return;
        }
        // if (cm.getItem(2, equipMagnifylistlist.get(equipMagnify)) == null) {
        //     cm.sendOk("系统繁忙请稍后再试2。");
        //     cm.dispose();
        //     return;
        // }

        // if (cm.getItem(5, equipPotentiallist.get(equipPotential)) == null) {
        //     cm.sendOk("系统繁忙请稍后再试1。");
        //     cm.dispose();
        //     return;
        // }
        if (cm.haveItem(equipPotentiallist.get(equipPotential).getItemId(), 1)) {
            abc = cm.UseCube(equiplist.get(equip), equipPotentiallist.get(equipPotential));
        } else {
            cm.sendOk("你的方块少于1个，无法使用快速洗潜。");
            cm.dispose();
            return;
        }


        if (abc == 1) {
            cm.sendOk("系统繁忙，请稍后再试3。");
            cm.dispose();
            return;
        }
        if (abc == 2) {
            cm.sendOk("你的等级不足10等，无法使用。");
            cm.dispose();
            return;
        }
        if (abc == 12) {
            cm.sendOk("你的等级不足70等，无法使用。");
            cm.dispose();
            return;
        }
        if (abc == 3) {
            cm.sendOk("你的魔方数量不足。");
            cm.dispose();
            return;
        }
        if (abc == 4) {
            cm.sendOk("该装备没有潜能或已经超过该方块能改变的潜能等级。");
            cm.dispose();
            return;
        }
        if (abc == 5) {
            cm.sendOk("请检查你的背包是否已满。。");
            cm.dispose();
            return;
        }




        var cba;

        if (cm.haveItem(equipMagnifylistlist.get(equipMagnify).getItemId(), 1)) {
            cba = cm.UseMagnify(equiplist.get(equip), equipMagnifylistlist.get(equipMagnify));

        } else {
            cm.sendOk("你的放大镜少于1个，无法使用快速洗潜。");
            cm.dispose();
            return;
        }
        if (cba == 1) {
            cm.sendOk("系统繁忙，请稍后再试4。");
            cm.dispose();
            return;
        }

        cm.getPlayer().marriage();
        cm.getClient().getSession().write(MaplePacketCreator.enableActions());
        var Potential1 = cm.getPotentialInfo2(equiplist.get(equip).getItemId(), cm.getEquipStat(equiplist.get(equip).getPosition()).getPotential1());
        var Potential2 = cm.getPotentialInfo2(equiplist.get(equip).getItemId(), cm.getEquipStat(equiplist.get(equip).getPosition()).getPotential2());
        var Potential3 = cm.getPotentialInfo2(equiplist.get(equip).getItemId(), cm.getEquipStat(equiplist.get(equip).getPosition()).getPotential3());
        var ss = "\r\n#e潜能1#k" + Potential1 + "\r\n潜能2" + Potential2 + "\r\n潜能3" + Potential3 + "\r\n";
        var sss = "#r#L300#继续洗潜#l#k";
        cm.sendSimple("使用潜能方块成功！" + ss + sss);
    } else if (status == 6) {
        cm.dispose();
        return;
    }
}
