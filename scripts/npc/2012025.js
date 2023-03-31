var status = -1;

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        if (status == 0) {
            cm.sendNext("看來你还有事情要辦吧？");
            cm.dispose();
        }
        status--;
    }
    if (status == 0) {
        cm.sendYesNo("坐上船之后，需要飛很久才能到达目的地。如果你在这里有急事要辦的话，请先把事情辦完。怎么樣？你要上船吗？");
    } else if (status == 1) {
        cm.warp(260000100, 0);
        cm.dispose();
    }
}