load('nashorn:mozilla_compat.js');
importPackage(Packages.server);

var status = -1;
var sel;
var mod;
function start() {

    //cm.sendOk("暂时沒有補償。");
    //cm.dispose();
    //return;
    cm.sendSimple("我是清明節活动管理员 \r\n\r\n" +
            "清明節奖励：\r\n" +
            "#i5062001##z5062001#x10\r\n" +
            "#i5062000##z5062000#x20\r\n" +
            "#i2450000##z2450000#x5\r\n" +
            "#i2109018##z2109018#x5\r\n" +
            "枫葉點數x1500\r\n" +
            "金币x5000000\r\n" +
            "#i1003458##z1003458#x1全屬性+10物魔攻+3（4天）\r\n" +
            "#b#L0#領取奖励#l#k\r\n\r\n" +
            "#b#L1##i4032224#x50兌換#i2022179#x1#l#k\r\n\r\n" +
            "#b#L2##i4032224#x50兌換#i2109018#x1#l#k\r\n\r\n" +
            "#b#L3##i4032224#x100兌換#i2450000#x1#l#k\r\n\r\n" +
            "#b#L4##i4032224#x100兌換#i2022530#x1#l#k\r\n\r\n" +
            "#b#L5##i4032224#x100兌換#i3010799#x1#l#k\r\n\r\n" +
            "#b#L6##i4032224#x300兌換#i5062001#x5#l#k\r\n\r\n" +
            "");
}

function action(mode, type, selection) {
    if (mode == 0) {
        cm.dispose();
        return;
    } else {
        status++;
    }
    if (status == 0) {
        sel = selection;
        if (!cm.canHold()) {
            cm.sendOk("请确认背包是否已经满了。");
            cm.dispose();
            return;
        }
        if (sel == 0) {
            if (cm.getPlayer().getLevel() < 100) {
                cm.sendOk("您的等級不足100等，无法領取。");
                cm.dispose();
                return;
            }
            //if (!cm.getPlayer().getAccNewTime("2018-12-31 0:00:00")) {
            //    cm.sendOk("該帳号不是12月31日00.00前註冊的帳号，无法領取。");
            //    cm.dispose();
            //    return;
            //}
            if (cm.getPlayer().getAcLogS("清明節奖励") >= 1) {
                cm.sendOk("該帳号已经領取过清明節奖励。");
                cm.dispose();
                return;
            }
            if (!cm.canHoldByType(5, 2)) {
                cm.sendOk("请确认背包是否已经满了。");
                cm.dispose();
                return;
            }
            if (!cm.canHoldByType(2, 2)) {
                cm.sendOk("请确认背包是否已经满了。");
                cm.dispose();
                return;
            }
            if (!cm.canHoldByType(1, 1)) {
                cm.sendOk("请确认背包是否已经满了。");
                cm.dispose();
                return;
            }

            cm.getPlayer().setAcLogS("清明節奖励");
            cm.gainItem(5062001, 10);
            cm.gainItem(5062000, 20);
            cm.gainItem(2450000, 5);
            cm.gainItem(2109018, 5);
            var equip = cm.getEquip(1003458);
            equip.setStr(10);
            equip.setDex(10);
            equip.setInt(10);
            equip.setLuk(10);
            equip.setWatk(3);
            equip.setMatk(3);
            equip.setExpiration(cm.getCurrentTime() + (4 * 24 * 60 * 60 * 1000));
            MapleInventoryManipulator.addbyItem(cm.getClient(), equip);
            cm.gainMeso(5000000);
            cm.getPlayer().modifyCSPoints(2, 1500, true);
            cm.sendOk("領取成功。");
            cm.dispose();
            return;
        } else if (sel == 1) {
            if (cm.getPlayer().getLevel() < 30) {
                cm.sendOk("您的等級不足30等，无法領取。");
                cm.dispose();
                return;
            }
            if (!cm.haveItem(4032224, 50)) {
                cm.sendOk("你的物品數量不足。");
                cm.dispose();
                return;
            }
            cm.gainItem(4032224, -50);
            cm.gainItem(2022179, 1);
            cm.sendOk("兌換成功。");
            cm.dispose();
            return;
        } else if (sel == 2) {
            if (cm.getPlayer().getLevel() < 30) {
                cm.sendOk("您的等級不足30等，无法領取。");
                cm.dispose();
                return;
            }
            if (!cm.haveItem(4032224, 50)) {
                cm.sendOk("你的物品數量不足。");
                cm.dispose();
                return;
            }
            cm.gainItem(4032224, -50);
            cm.gainItem(2109018, 1);
            cm.sendOk("兌換成功。");
            cm.dispose();
            return;
        } else if (sel == 3) {
            if (cm.getPlayer().getLevel() < 30) {
                cm.sendOk("您的等級不足30等，无法領取。");
                cm.dispose();
                return;
            }
            if (!cm.haveItem(4032224, 100)) {
                cm.sendOk("你的物品數量不足。");
                cm.dispose();
                return;
            }
            cm.gainItem(4032224, -100);
            cm.gainItem(2450000, 1);
            cm.sendOk("兌換成功。");
            cm.dispose();
            return;
        } else if (sel == 4) {
            if (cm.getPlayer().getLevel() < 30) {
                cm.sendOk("您的等級不足30等，无法領取。");
                cm.dispose();
                return;
            }
            if (!cm.haveItem(4032224, 100)) {
                cm.sendOk("你的物品數量不足。");
                cm.dispose();
                return;
            }
            cm.gainItem(4032224, -100);
            cm.gainItem(2022530, 1);
            cm.sendOk("兌換成功。");
            cm.dispose();
            return;
        } else if (sel == 5) {
            if (cm.getPlayer().getLevel() < 30) {
                cm.sendOk("您的等級不足30等，无法領取。");
                cm.dispose();
                return;
            }
            if (!cm.haveItem(4032224, 100)) {
                cm.sendOk("你的物品數量不足。");
                cm.dispose();
                return;
            }
            cm.gainItem(4032224, -100);
            cm.gainItem(3010799, 1);
            cm.sendOk("兌換成功。");
            cm.dispose();
            return;
        } else if (sel == 6) {
            if (cm.getPlayer().getLevel() < 30) {
                cm.sendOk("您的等級不足30等，无法領取。");
                cm.dispose();
                return;
            }
            if (!cm.haveItem(4032224, 300)) {
                cm.sendOk("你的物品數量不足。");
                cm.dispose();
                return;
            }
            cm.gainItem(4032224, -300);
            cm.gainItem(5062001, 5);
            cm.sendOk("兌換成功。");
            cm.dispose();
            return;
        }
    }
}
