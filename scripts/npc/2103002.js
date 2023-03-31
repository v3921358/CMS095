var status = -1;
var selectionLog = [];
function start() {
    action(1, 0, 0);
}
function action(d, c, b) {
    if (status == 0 && d == 0) {
        cm.dispose();
        return;
    }
    d == 1 ? status++ : status--;
    selectionLog[status] = b;
    var a = -1;
    if (status <= a++) {
        cm.dispose();
    } else {
        if (status === a++) {
            if (!cm.isQuestActive(3923)) {
                cm.sendNext('偷东西的话会被警卫发现的。');
                cm.dispose();
            } else {
                if (cm.haveItem(4031578)) {
                    cm.sendNext('已经拿走了戒指，可以离开了。');
                } else {
                    cm.sendNext('小心翼翼地取出抽屉，拿出了王妃的戒指。');
                    cm.gainItem(4031578, 1);
                }
                cm.dispose();
            }
        }
    }
}