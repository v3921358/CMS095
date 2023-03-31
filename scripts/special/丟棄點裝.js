load('nashorn:mozilla_compat.js');
importPackage(Packages.tools.packet);
importPackage(Packages.server);
importPackage(Packages.tools);
var equiplist;
var equip;
var equipItem;
var equipLevel;
var cash;
var fbj;
var fmdj;
var equipPotentiallist;
var equipPotential;
var equipMagnifylistlist;
var stra = "";
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
    if (selection == 300) {
        status = 5;
    }
    if (status == 0) {
        cm.sendSimple("你好，我可以帮你丟棄點裝。\r\n" +
                "#r#L1#丟棄點裝#l#k\r\n" +
                "");

    } else if (status == 1) {
        equiplist = cm.getCsEquipList();
        if (equiplist != null) {
            for (var i = 0; i < equiplist.size(); i++) {
                stra += "#L" + i + "##i" + equiplist.get(i).getItemId() + "##t" + equiplist.get(i).getItemId() + "##k\r\n";
            }
        }
        if (stra == "") {
            cm.sendOk("您目前沒有可丟棄的點裝");
            cm.dispose();
            return;
        }
        cm.sendSimple("選擇你想要丟棄的點裝。\r\n" + stra);

    } else if (status == 2) {
        equip = selection;
        equipItem = cm.getEquipStat(equiplist.get(equip).getPosition());
        cm.dropCs(1, equiplist.get(equip).getPosition(), 1);
        cm.dispose();
        return;
    }

}
