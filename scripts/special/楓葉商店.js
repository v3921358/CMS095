var itemidq = Array(
        Array(4001126, 500, 2460003, 30),
        Array(4001126, 500, 2290285, 1),
        Array(4001126, 1000, 2450000, 1),
        Array(4001126, 1000, 2022463, 1),
        Array(4001126, 2000, 5062001, 15),
        //Array(4001126, 10000, 2046577, 1),
        //Array(4001126, 10000, 2046578, 1),
        //Array(4001126, 10000, 2046579, 1),
        //Array(4001126, 10000, 2046580, 1),
        Array(4001126, 30000, 5064000, 1)
        //Array(4001126, 30000, 2046052, 1),
        //Array(4001126, 30000, 2046053, 1),
        //Array(4001126, 30000, 2046134, 1),
        //Array(4001126, 30000, 2046137, 1)
        );

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
        cm.sendSimple("#e我是枫葉商店管理员。\r\n" +
                "#L0##r枫葉武器#l\r\n" +
                "#L1##r其他兌換#l\r\n" +
                ""
                );

    } else if (status == 1) {
        select = selection;
        switch (select) {
            case 0:
            {
                openNpc(9010000, "每日簽到");
                break;
            }
            case 1:
            {

                var selStr2 = "#e我是枫葉商店管理员。\r\n";
                for (var i = 0; i < itemidq.length; i++) {

                    selStr2 += "#L" + i + "##r#i" + itemidq[i][0] + "#x" + itemidq[i][1] + " 兌換 #i" + itemidq[i][2] + "#x" + itemidq[i][3] + "#l\r\n";
                }
                cm.sendSimple(selStr2);
                break;
            }
            default :
            {
                cm.dispose();
                return;
            }
        }
    } else if (status == 2) {
        if (!cm.canHold()) {
            cm.sendOk("请确认背包是否已经满了。");
            cm.dispose();
            return;
        }
        if (!cm.haveItem(itemidq[selection][0], itemidq[selection][1])) {
            cm.sendOk("你的物品數量不足。");
            cm.dispose();
            return;
        }
        cm.gainItem(itemidq[selection][0], -itemidq[selection][1]);
        cm.gainItem(itemidq[selection][2], itemidq[selection][3]);
        cm.sendOk("购买成功");
        cm.dispose();
        return;

    }
}
function openNpc(npcid) {
    openNpc(npcid, null);
}

function openNpc(npcid, script) {
    var mapid = cm.getMapId();
    cm.dispose();
    if (cm.getPlayerStat("LVL") < 10) {
        cm.sendOk("你的等級不能小于10等.");
    } else if (
            cm.hasSquadByMap() ||
            cm.hasEventInstance() ||
            cm.hasEMByMap() ||
            mapid >= 990000000 ||
            (mapid >= 680000210 && mapid <= 680000502) ||
            (mapid / 1000 === 980000 && mapid !== 980000000) ||
            mapid / 100 === 1030008 ||
            mapid / 100 === 922010 ||
            mapid / 10 === 13003000
            ) {
        cm.sendOk("你不能在这里使用这个功能.");
    } else {
        if (script == null) {
            cm.openNpc(npcid);
        } else {
            cm.openNpc(npcid, script);
        }
    }
}