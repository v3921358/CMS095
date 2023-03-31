

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
        cm.sendSimple("#e我这里可以兌換坐騎。\r\n" +
                "#L0##r狂狼200等坐騎#l\r\n" +
                "#L1##r龙魔導士50等坐騎#l\r\n" +
                "#L2##r龙魔導士80等坐騎#l\r\n" +
                "#L3##r龙魔導士120等坐騎#l\r\n" +
                "#L4##r皇家騎士團120等坐騎#l\r\n" +
                ""

                );

    } else if (status == 1) {
        if (selection == 0) {
            if (cm.haveItem(1902017, 1)) {
                cm.gainItem(1902017, -1);
                cm.gainItem(1902018, 1);
                cm.sendOk("領取成功。");
                cm.dispose();
                return;
            } else {
                cm.sendOk("你背包裏沒有#t1902017##i1902017#。");
                cm.dispose();
                return;
            }
        }

        if (selection == 1) {

            if (cm.getPlayer().getJob() == 2001 || cm.getPlayer().getJob() == 2200 || cm.getPlayer().getJob() == 2210 || cm.getPlayer().getJob() == 2211 || cm.getPlayer().getJob() == 2212 || cm.getPlayer().getJob() == 2213 || cm.getPlayer().getJob() == 2214 || cm.getPlayer().getJob() == 2215 || cm.getPlayer().getJob() == 2216 || cm.getPlayer().getJob() == 2217 || cm.getPlayer().getJob() == 2218) {

                cm.sendSimple("你确定要兌換龙神50等坐騎吗，選擇错誤，責任自付。\r\n#L11#我要兌換龙魔導士50等坐騎#l");

            } else {
                cm.sendOk("你不是龙魔。");
                cm.dispose();
                return;
            }
        }
        if (selection == 2) {
            if (cm.getPlayer().getJob() == 2001 || cm.getPlayer().getJob() == 2200 || cm.getPlayer().getJob() == 2210 || cm.getPlayer().getJob() == 2211 || cm.getPlayer().getJob() == 2212 || cm.getPlayer().getJob() == 2213 || cm.getPlayer().getJob() == 2214 || cm.getPlayer().getJob() == 2215 || cm.getPlayer().getJob() == 2216 || cm.getPlayer().getJob() == 2217 || cm.getPlayer().getJob() == 2218) {

                cm.sendSimple("你确定要兌換龙神80等坐騎吗，選擇错誤，責任自付。\r\n#L12#我要兌換龙魔導士80等坐騎#l");

            } else {
                cm.sendOk("你不是龙魔。");
                cm.dispose();
                return;
            }
        }

        if (selection == 3) {
            if (cm.getPlayer().getJob() == 2001 || cm.getPlayer().getJob() == 2200 || cm.getPlayer().getJob() == 2210 || cm.getPlayer().getJob() == 2211 || cm.getPlayer().getJob() == 2212 || cm.getPlayer().getJob() == 2213 || cm.getPlayer().getJob() == 2214 || cm.getPlayer().getJob() == 2215 || cm.getPlayer().getJob() == 2216 || cm.getPlayer().getJob() == 2217 || cm.getPlayer().getJob() == 2218) {
                cm.sendSimple("你确定要兌換龙神120等坐騎吗，選擇错誤，責任自付。\r\n#L13#我要兌換龙魔導士120等坐騎#l");
            } else {
                cm.sendOk("你不是龙魔。");
                cm.dispose();
                return;
            }
        }
        if (selection == 4) {
            if (cm.haveItem(1902006, 1)) {
                cm.gainItem(1902006, -1);
                cm.gainItem(1902007, 1);
                cm.sendOk("領取成功。");
                cm.dispose();
                return;
            } else {
                cm.sendOk("你背包裏沒有#t1902006##i1902006#。");
                cm.dispose();
                return;
            }
        }
    } else if (status == 2) {
        if (selection == 11) {
            if (cm.getPlayer().getMeso() >= 10000000) {
                if (cm.canHoldByType(1, 2)) {
                    cm.gainMeso(-10000000);
                    cm.gainItem(1912033, 1);
                    cm.gainItem(1902040, 1);
                    cm.teachSkill(20011004, 1, 0); // Maker
                    cm.sendOk("領取成功。");
                    cm.dispose();
                    return;
                } else {
                    cm.sendOk("请确认背包是否已经满了。");
                    cm.dispose();
                    return;
                }
            } else {
                cm.sendOk("你的金币不足1000萬。");
                cm.dispose();
                return;
            }
        }


        if (selection == 12) {
            if (cm.getPlayer().getMeso() >= 30000000) {
                if (cm.canHoldByType(1, 2)) {
                    cm.gainMeso(-30000000);
                    cm.gainItem(1912034, 1);
                    cm.gainItem(1902041, 1);
                    cm.sendOk("領取成功。");
                    cm.dispose();
                    return;
                } else {
                    cm.sendOk("请确认背包是否已经满了。");
                    cm.dispose();
                    return;
                }
            } else {
                cm.sendOk("你的金币不足3000萬。");
                cm.dispose();
                return;
            }

        }
        if (selection == 13) {
            if (cm.getPlayer().getMeso() >= 60000000) {
                if (cm.canHoldByType(1, 2)) {
                    cm.gainMeso(-60000000);
                    cm.gainItem(1912035, 1);
                    cm.gainItem(1902042, 1);
                    cm.sendOk("領取成功。");
                    cm.dispose();
                    return;
                } else {
                    cm.sendOk("请确认背包是否已经满了。");
                    cm.dispose();
                    return;
                }
            } else {
                cm.sendOk("你的金币不足6000萬。");
                cm.dispose();
                return;
            }
        }
    }
}
