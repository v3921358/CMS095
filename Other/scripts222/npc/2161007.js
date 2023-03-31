var status = -1;

function action(d, c, b) {
    (d == 1) ? status++ : status--;
    var a = -1;
    if (status <= a++) {
        cm.dispose()
    } else {
        if (status == a++) {
            cm.sendNext("抽泣……我，我想回家。")
        } else {
            if (status == a++) {
                cm.sendNext("嗯？你，你是谁？是来帮我的吗？请你一定要让我离开这里！我好害怕！")
            } else {
                cm.gainItem(4032831, 1);
                cm.warp(211060200, 3);
                cm.dispose()
            }
        }
    }
}

function start() {
    status = -1;
    action(1, 0, 0)
};