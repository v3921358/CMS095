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
        cm.sendOk("维修中");
        cm.dispose();
        return;
    }
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
            cm.sendSimple("你好，我是时装升星大师。\r\n" +
                "#b每件时装共可升星 5次 每次需消耗 点券 或 抵用券\r\n" +
                "第一次 升星 消耗 2500点券 或 6000抵用券 100%成功\r\n" +
                "第二次 升星 消耗 2500点券 或 6000抵用券 70%成功\r\n" +
                "第三次 升星 消耗 2500点券 或 6000抵用券 50%成功\r\n" +
                "第四次 升星 消耗 2500点券 或 6000抵用券 30%成功\r\n" +
                "第五次 升星 消耗 2500点券 或 6000抵用券 30%成功 失败时将爆炸 ( #r可使用防暴卷轴)#k\r\n" +
                "#r#L1#使用#v2049301#全属性提高0~2#l#k\r\n" +
                "#r#L2#使用#v2049300#全属性提高1~3#l#k\r\n" +
                "");

        } else if (status == 1) {
            fmdj = selection == 1 ? 2049301 : selection == 2 ? 2049300 : selection == 3 ? 4440100 : 0;
            equiplist = cm.getCsEquipList();
            if (equiplist != null) {
                for (var i = 0; i < equiplist.size(); i++) {
                    stra += "#L" + i + "##i" + equiplist.get(i).getItemId() + "##t" + equiplist.get(i).getItemId() + "##k\r\n";
                }
            }
            if (stra == "") {
                cm.sendOk("您目前没有装备可使用升星");
                cm.dispose();
                return;
            }
            cm.sendSimple("选择你想要升星的时装。\r\n" + stra);

        } else if (status == 2) {
            equip = selection;
            equipItem = cm.getEquipStat(equiplist.get(equip).getPosition());
            equipLevel = equipItem.getEnhance();
            if (equipLevel >= 5) {
                cm.sendOk("当前道具已达到最高升星级别。");
                cm.dispose();
                return;
            }
            if (equipLevel >= 1) {
                cm.sendSimple("你好，时装升星大师。\r\n" +
                    "#L21#消耗2500点券#l\r\n" +
                    "#L22#消耗6000抵用券#l\r\n" +
                    "");
            } else {
                cm.sendSimple("你好，时装升星大师。\r\n" +
                    "#L21#消耗2500点券#l\r\n" +
                    "#L22#消耗6000抵用券#l\r\n" +
                    "");
            }
        } else if (status == 3) {
            cash = selection == 21 ? 1 : selection == 22 ? 2 : 0;
            if (equipLevel >= 5) {
                cm.sendOk("当前道具已达到最高升星级别。");
                cm.dispose();
                return;
            }
            if (equipLevel == 4) {
                cm.sendSimple("你好，时装升星大师。\r\n" +
                    "#L31#使用防暴卷#l\r\n" +
                    "#L32#不使用防暴卷#l\r\n" +
                    "");
            } else {
                cm.sendSimple("你好，时装升星大师。\r\n" +
                    "#L30#确认升星#l\r\n" +
                    "");
            }

        } else if (status == 4) {
            fbj = selection == 31 ? 0 : selection == 32 ? 1 : selection == 30 ? 1 : 2;
            if (!MapleItemInformationProvider.getInstance().isCash(equipItem.getItemId())) {
                cm.sendOk("该道具不是时装无法升星。");
                cm.dispose();
                return;
            }
            if (equipLevel >= 5) {
                cm.sendOk("当前道具已达到最高升星级别。");
                cm.dispose();
                return;
            }
            if (fmdj == 0) {
                cm.sendOk("发生未知错误1。");
                cm.dispose();
                return;
            }
            if (!cm.haveItem(fmdj, 1)) {
                cm.sendOk("你的物品数量不足。");
                cm.dispose();
                return;
            }
            if (cash == 0) {
                cm.sendOk("发生未知错误2。");
                cm.dispose();
                return;
            }
            if (cash == 1) {
                if (equipLevel == 0 ? (cm.getPlayer().getCSPoints(1) < 2500) : (cm.getPlayer().getCSPoints(1) < 2500)) {
                    cm.sendOk("你的点券不足。");
                    cm.dispose();
                    return;
                }
            }
            if (cash == 2) {
                if (equipLevel == 0 ? (cm.getPlayer().getCSPoints(2) < 6000) : (cm.getPlayer().getCSPoints(2) < 6000)) {
                    cm.sendOk("你的抵用券不足。");
                    cm.dispose();
                    return;
                }
            }
            if (fbj == 2) {
                cm.sendOk("发生未知错误3");
                cm.dispose();
                return;
            }
            if (fbj == 0) {
                if (!cm.haveItem(5064000, 1)) {
                    cm.sendOk("你的防暴卷物品数量不足。");
                    cm.dispose();
                    return;
                }
            }

            if (equipLevel == 0) {

                cm.getPlayer().modifyCSPoints(cash, cash == 1 ? -2500 : -6000, true);



            } else if (equipLevel == 1) {
                if (Randomizer.nextInt(100) > 70) {
                    cm.getPlayer().modifyCSPoints(cash, cash == 1 ? -2500 : -6000, true);
                    cm.gainItem(fmdj, -1);
                    cm.sendOk("升星失败。");
                    cm.dispose();
                    return;
                }
                cm.getPlayer().modifyCSPoints(cash, cash == 1 ? -2500 : -6000, true);

            } else if (equipLevel == 2) {
                if (Randomizer.nextInt(100) > 50) {
                    cm.getPlayer().modifyCSPoints(cash, cash == 1 ? -2500 : -6000, true);
                    cm.gainItem(fmdj, -1);
                    cm.sendOk("升星失败。");
                    cm.dispose();
                    return;
                }
                cm.getPlayer().modifyCSPoints(cash, cash == 1 ? -2500 : -6000, true);

            } else if (equipLevel == 3) {
                if (Randomizer.nextInt(100) > 30) {
                    cm.sendOk("升星失败。");
                    cm.getPlayer().modifyCSPoints(cash, cash == 1 ? -2500 : -6000, true);
                    cm.gainItem(fmdj, -1);
                    cm.dispose();
                    return;
                }

                cm.getPlayer().modifyCSPoints(cash, cash == 1 ? -2500 : -6000, true);

            } else if (equipLevel == 4) {
                if (fbj == 0) {
                    cm.gainItem(5064000, -1);
                }
                if (Randomizer.nextInt(100) > 30) {
                    if (fbj == 0) {
                        cm.sendOk("升星失败。");
                        cm.getPlayer().modifyCSPoints(cash, cash == 1 ? -2500 : -6000, true);
                        cm.gainItem(fmdj, -1);
                        cm.dispose();
                        return;
                    } else {
                        cm.removeSlot(1, equiplist.get(equip).getPosition(), 1);
                        cm.getPlayer().modifyCSPoints(cash, cash == 1 ? -2500 : -6000, true);
                        cm.gainItem(fmdj, -1);
                        cm.sendOk("升星失败,道具损坏。。");
                        cm.dispose();
                        return;
                    }
                }

                cm.getPlayer().modifyCSPoints(cash, cash == 1 ? -2500 : -6000, true);


            }
            cm.gainItem(fmdj, -1);
            var suijiitemB = [0, 1, 2];
            var suijiitemA = [1, 2, 3];
            var suijiitemSS = [5, 6, 7, 8];
            var bass = fmdj == 2049301 ? suijiitemB : fmdj == 2049300 ? suijiitemA : fmdj == 4440100 ? suijiitemSS : 0;
            if (bass == 0) {
                cm.sendOk("发生未知错误4");
                cm.dispose();
                return;
            }
            var str = cm.getRandom(bass);
            var dex = cm.getRandom(bass);
            var int = cm.getRandom(bass);
            var luk = cm.getRandom(bass);
            var watk = cm.getRandom(bass);
            var Matk = cm.getRandom(bass);
            equipItem.setStr(equipItem.getStr() + str);
            equipItem.setDex(equipItem.getDex() + dex);
            equipItem.setInt(equipItem.getInt() + int);
            equipItem.setLuk(equipItem.getLuk() + luk);
            equipItem.setWatk(equipItem.getWatk() + watk);
            equipItem.setMatk(equipItem.getMatk() + Matk);
            equipItem.setEnhance(equipItem.getEnhance() + 1);
            cm.forceReAddItem(equipItem, 1);
            cm.sendOk("升星成功！");
            cm.dispose();
            return;
        }

    }
}