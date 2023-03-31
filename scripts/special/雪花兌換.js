var itemidq = Array(
        Array(4310014, 70, 2046577, 1),
        Array(4310014, 70, 2046578, 1),
        Array(4310014, 70, 2046579, 1),
        Array(4310014, 70, 2046580, 1),
        Array(4310014, 100, 2046052, 1),
        Array(4310014, 100, 2046053, 1),
        Array(4310014, 100, 2046134, 1),
        Array(4310014, 100, 2046137, 1)
        );

var status = -1;
var sel;
var mod;
function start() {

    var selStr2 = "#e我是#i4310014#兌換员。\r\n\r\n";
    for (var i = 0; i < itemidq.length; i++) {
        selStr2 += "#b#L" + i + "##i" + itemidq[i][0] + "#" + itemidq[i][1] + "換#i" + itemidq[i][2] + "#x" + itemidq[i][3] + "#l#k\r\n\r\n";
    }
    cm.sendSimple(selStr2);
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
        if (cm.getPlayer().getLevel() < 15) {
            cm.sendOk("您的等級不足15等，无法領取。");
            cm.dispose();
            return;
        }
        if (!cm.haveItem(itemidq[selection][0], itemidq[selection][1])) {
            cm.sendOk("你的物品數量不足。");
            cm.dispose();
            return;
        }
        if (!cm.canHoldByType(2, 1)) {
            cm.sendOk("请确认背包是否已经满了。");
            cm.dispose();
            return;
        }
        cm.gainItem(itemidq[selection][0], -itemidq[selection][1]);
        cm.gainItem(itemidq[selection][2], itemidq[selection][3]);
        cm.sendOk("兌換成功。");
        cm.dispose();
        return;

    }
}
