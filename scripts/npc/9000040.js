var status = -1;
var selectionLog = [];

function start() {
    action(1, 0, 0)
}

function action(d, c, b) {
    if (status == 0 && d == 0) {
        cm.dispose();
        return
    }(d == 1) ? status++ : status--;
    selectionLog[status] = b;
    var a = -1;
    if (status <= a++) {
        cm.dispose()
    } else {
        if (status === a++) {
            cm.sendOk("你想查看哪个勋章的排名呢？#b\n\r\n#L0#传说中的猎人#l\r\n#L1#冒险岛偶像明星#l\r\n#L2#黑暗龙王杀手#l\r\n#L3#品克缤杀手#l\r\n#L4#村庄爱心使者#l")
        } else {
            if (status === a++) {
                cm.sendNext("排名没什么好说的。大家都处于同一起跑线……你也可能获得第一。");
                cm.dispose()
            }
        }
    }
};