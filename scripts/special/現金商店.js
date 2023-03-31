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
        cm.sendSimple("#e我是现金商店管理员。\r\n" +
                "#L0##r#z5062001##i5062001#x1 100Gash#l\r\n" +
                "#L1##r#z5570000##i5570000#x1 750Gash#l\r\n" +
                "#L2##r#z5064000##i5064000#x1 500Gash#l\r\n" +
                ""

                );

    } else if (status == 1) {
        if (!cm.canHold()) {
            cm.sendOk("请确认背包是否已经满了。");
            cm.dispose();
            return;
        }

        if (selection == 0) {
            if (cm.getPlayer().getCSPoints(1) < 100) {
                cm.sendOk("你的Gash不足。");
                cm.dispose();
                return;
            }
            cm.getPlayer().modifyCSPoints(1, -100, true);
            cm.gainItem(5062001, 1);
            cm.sendOk("购买成功");
            cm.dispose();
            return;
        }

        if (selection == 1) {
            if (cm.getPlayer().getCSPoints(1) < 100) {
                cm.sendOk("你的Gash不足。");
                cm.dispose();
                return;
            }
            cm.getPlayer().modifyCSPoints(1, -100, true);
            cm.gainItem(5570000, 1);
            cm.sendOk("购买成功");
            cm.dispose();
            return;
        }

        if (selection == 2) {
            if (cm.getPlayer().getCSPoints(1) < 200) {
                cm.sendOk("你的Gash不足。");
                cm.dispose();
                return;
            }
            cm.getPlayer().modifyCSPoints(1, -200, true);
            cm.gainItem(5064000, 1);
            cm.sendOk("购买成功");
            cm.dispose();
            return;
        }


        cm.dispose();
    }
}
