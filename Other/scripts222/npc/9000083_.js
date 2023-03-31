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
        cm.sendOk("維修中");
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
    if (status == 0) {
        cm.sendSimple("你好，潛能方塊使用师。\r\n" +
                "#L0#使用潛能方塊#l\r\n");

    } else if (status == 1) {
        if (selection == 0) {
            equiplist = cm.getEquipList();
            if (equiplist != null) {
                for (var i = 0; i < equiplist.size(); i++) {
                    str += "#L" + i + "##i" + equiplist.get(i).getItemId() + "##t" + equiplist.get(i).getItemId() + "##k\r\n";
                }
            }
            if (str == "") {
                cm.sendOk("您目前沒有裝备可使用潛能方塊");
                cm.dispose();
                return;
            }
            cm.sendSimple("選擇你想要使用潛能方塊的裝备。\r\n" + str);

        }

    } else if (status == 2) {
        equip = selection;
        equipPotentiallist = cm.getPotentiallist();
        if (equipPotentiallist != null) {
            for (var i = 0; i < equipPotentiallist.size(); i++) {
                strs += "#L" + i + "##i" + equipPotentiallist.get(i).getItemId() + "##t" + equipPotentiallist.get(i).getItemId() + "##k\r\n";
            }
        }
        if (strs == "") {
            cm.sendOk("您目前沒有可使用潛能方塊");
            cm.dispose();
            return;
        }
        cm.sendSimple("選擇你想要使用的潛能方塊。\r\n" + strs);
    } else if (status == 3) {
        equipPotential = selection;

        equipMagnifylistlist = cm.getMagnifylist();
        if (equipMagnifylistlist != null) {
            for (var i = 0; i < equipMagnifylistlist.size(); i++) {
                strss += "#L" + i + "##i" + equipMagnifylistlist.get(i).getItemId() + "##t" + equipMagnifylistlist.get(i).getItemId() + "##k\r\n";
            }
        }
        if (strss == "") {
            cm.sendOk("您目前沒有可使用放大鏡");
            cm.dispose();
            return;
        }
        cm.sendSimple("選擇你想要使用的放大鏡。\r\n" + strss);



        /*yesno = cm.UseUpgradeScroll(equiplist.get(equip), equipscrolllist.get(equipscroll), 10);
         if (yesno) {
         cm.sendOk("恭喜您强化成功。");
         } else {
         cm.sendOk("很抱歉，强化失敗。");
         }
         cm.dispose();
         return;*/
    } else if (status == 4) {
        equipMagnify = selection;
        var abc = cm.UseCube(equiplist.get(equip), equipPotentiallist.get(equipPotential));

        if (abc == 1) {
            cm.sendOk("系統繁忙，请稍后再试。");
            cm.dispose();
            return;
        }
        if (abc == 2) {
            cm.sendOk("你的等級不足10等，无法使用。");
            cm.dispose();
            return;
        }
        if (abc == 12) {
            cm.sendOk("你的等級不足70等，无法使用。");
            cm.dispose();
            return;
        }
        if (abc == 3) {
            cm.sendOk("你的魔方數量不足。");
            cm.dispose();
            return;
        }
        if (abc == 4) {
            cm.sendOk("該裝备沒有潛能或已经超过該方塊能改变的潛能等級。");
            cm.dispose();
            return;
        }
        if (abc == 5) {
            cm.sendOk("请检查你的背包是否已满。。");
            cm.dispose();
            return;
        }

        var cba = cm.UseMagnify(equiplist.get(equip), equipMagnifylistlist.get(equipMagnify));
        if (cba == 1) {
            cm.sendOk("系統繁忙，请稍后再试。");
            cm.dispose();
            return;
        }
        cm.sendOk("使用潛能方塊成功！");
        cm.dispose();
        return;
    }
}
