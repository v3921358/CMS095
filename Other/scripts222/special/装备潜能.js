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
        cm.sendSimple("你好，我是潛能附加使用师。\r\n" +
                "#L0#使用#z2049401##l\r\n");

    } else if (status == 1) {
        if (selection == 0) {
            equiplist = cm.getEquipList();
            if (equiplist != null) {
                for (var i = 0; i < equiplist.size(); i++) {
                        str += "#L" + i + "##i" + equiplist.get(i).getItemId() + "##t" + equiplist.get(i).getItemId() + "##k\r\n";
                    
                }
            }
            if (str == "") {
                cm.sendOk("您目前沒有裝备可使用#z2049401#");
                cm.dispose();
                return;
            }
            cm.sendSimple("選擇你想要使用#z2049401#的裝备。\r\n" + str);

        }

    } else if (status == 2) {
        equip = selection;
        equipPotentiallist = cm.getPotenttalFyzxlist();
        if (equipPotentiallist != null) {
            for (var i = 0; i < equipPotentiallist.size(); i++) {
                strs += "#L" + i + "##i" + equipPotentiallist.get(i).getItemId() + "##t" + equipPotentiallist.get(i).getItemId() + "##k\r\n";
            }
        }
        if (strs == "") {
            cm.sendOk("您目前沒有可使用#z2049401#");
            cm.dispose();
            return;
        }
        cm.sendSimple("選擇你想要使用的#z2049401#。\r\n" + strs);
    } else if (status == 3) {
        equipPotential = selection;
        cm.dispose();
        yesno = cm.UseUpgradeScroll(equiplist.get(equip), equipPotentiallist.get(equipPotential), 0);
        if (yesno) {
            cm.sendOk("恭喜使用#z2049401#成功。");
        } else {
            cm.sendOk("很抱歉，使用#z2049401#失敗。");
        }
        cm.dispose();
        return;
    }
}
