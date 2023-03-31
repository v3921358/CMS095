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
            if (cm.haveItem(4001086)) {
                cm.sendYesNo("石板上的文字发出了奇异的光芒，石板后的一扇小门开启了。想要使用秘密通道吗？")
            } else {
                cm.sendOk("石板上写着看不懂的文字，不知是什么用途。\r\n#b（需要持有#i4001086##e#b#t4001086##k才能进入）");
                cm.dispose()
            }
        } else {
            cm.warp(240050400, 0);
            cm.dispose()
        }
    }
};