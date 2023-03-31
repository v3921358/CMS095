var itemidq = Array(
        Array(100, 2022179, 10),
        Array(100, 2022531, 1),
        Array(100, 2450000, 1),
        Array(300, 2450010, 1),
        Array(500, 5220010, 1),
        Array(500, 2046580, 1),
        Array(500, 2046579, 1),
        Array(500, 2046578, 1),
        Array(500, 2046577, 1),
        Array(500, 2049412, 1),
        Array(750, 2046137, 1),
        Array(750, 2046134, 1),
        Array(750, 2046053, 1),
        Array(750, 2046052, 1),
        Array(1000, 2049413, 1)
        );

var status = -1;
var sel;
var mod;
function start() {

    var selStr2 = "#e我是紅利點數兌換员。\r\n你有紅利點數 #r" + cm.getPlayer().getCSPoints(3) + "#k 點\r\n\r\n";
    for (var i = 0; i < itemidq.length; i++) {
        selStr2 += "#b#L" + i + "#紅利點數#r" + itemidq[i][0] + "#k點換#i" + itemidq[i][1] + "#x" + itemidq[i][2] + "#l#k\r\n\r\n";
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
        if (cm.getPlayer().getCSPoints(3) < itemidq[selection][0]) {
            cm.sendOk("你的紅利點數不足。");
            cm.dispose();
            return;
        }
        if (!cm.canHold()) {
            cm.sendOk("请确认背包是否已经满了。");
            cm.dispose();
            return;
        }
        cm.getPlayer().modifyCSPoints(3, -itemidq[selection][0], true);
        cm.gainItem(itemidq[selection][1], itemidq[selection][2]);
        cm.sendOk("兌換成功。");
        cm.dispose();
        return;

    }
}
