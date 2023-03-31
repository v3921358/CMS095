var status = -1;

function start() {
    cm.sendNext("啊，又……你好。你想离开耶雷弗，前往其他地區吗？那你就找对人了。这里有开往#維多利亞#k的#b六个分歧路#k的船。");
}

function action(mode, type, selection) {
    if (mode == 1)
        status++;
    else
        status--;

    if (status == -1) {
        cm.sendNext("看來你还有事要辦，等你想去維多利亞了再來找我吧。");
        cm.dispose();
    } else if (status == 0) {
        cm.sendYesNo("到維多利亞的#b六个分歧路#k去所需的时间是#b2分鐘#k左右，費用是#b1000#k金币。1000您要支付金币乘坐船吗？");
    } else if (status == 1) {
        if (cm.getMeso() < 1000) {
            cm.sendNext("你明明沒有錢嘛……必須有#b1000#k金币才可以去。");
        } else {
            cm.warp(104020120);
        }
        cm.dispose();
    } else {
        cm.dispose();
    }
}