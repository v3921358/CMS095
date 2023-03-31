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
            if (!cm.isQuestActive(3927)) {
                cm.dispose();
                return;
            } else {
                cm.sendNext('虽然是堵平凡的墙，但细看的话，有一些怪异的纹样。想观察墙壁吗？');
            }
        } else {
            if (status === a++) {
                cm.sendNext('墙后面写着很多怪异的词。\r\n\r\n#b若有铁锤和短剑，如果有弓和箭……#k');
            } else {
                if (status === a++) {
                   cm.forceCompleteQuest(3927);
                    cm.dispose();
                }
            }
        }
    }
}