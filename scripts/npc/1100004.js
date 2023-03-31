var status = -1;

function start() {
    cm.sendNext("嗯……今天的天氣好。你想离开耶雷弗，到其他地區去吗？这艘船开往天空之城。");
}

function action(mode, type, selection) {
    if (mode == 1)
        status++;
    else
        status--;

    if (status == -1) {
        cm.sendNext("怎么？你还有事情要完成吗？那你先去解决了再來吧。");
        cm.dispose();
    } else if (status == 0) {
        cm.sendYesNo("到达#b天空之城#k的时间大約是#b4分鐘#k。費用是#b1000#k金币。1000您要支付金币乘坐船吗？");
    } else if (status == 1) {
        if (cm.getMeso() < 1000) {
            cm.sendNext("你明明沒有錢嘛……必須有#b1000#k金币才可以去。");
        } else {
            cm.warp(200000100);
        }
        cm.dispose();
    } else {
        cm.dispose();
    }
}