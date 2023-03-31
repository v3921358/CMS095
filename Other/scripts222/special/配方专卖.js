
var status = 0;
var options = Array(
    Array("帽子", 1),
    Array("套服", 2),
    Array("手套", 35),
    Array("披风", 3),
    Array("鞋子", 9),
    Array("饰品", 15),
    Array("单手武器", 18),
    Array("双手武器", 23),
    Array("机器人", 43),
    Array("副武器(飞镖等)", 30)
);
function start() {
    status = -1
    action(1, 0, 0);

}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (mode == 0) {
            cm.dispose();
            return;
        }
        if (mode == 1) {
            status++;
        } else {
            status--;
        }
    }
    if (status == 0) {
        var text = "亲爱的老板~请问您想要购买什么配方呢?#b\r\n";
        for (var i = 0; i < options.length; i++) {
            text += "\r\n#L" + i + "# " + options[i][0] + "#l";
        }
        cm.sendSimple(text);
    } else if(status==1){
        cm.dispose();
        cm.openShop(options[selection][1]);
        return;
    }
}