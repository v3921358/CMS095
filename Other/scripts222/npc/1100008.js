var status = -1;

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        if (status == 0) {
            cm.sendNext("不去的话就算了。");
            cm.dispose();
        }
        status--;
    }
    if (status == 0) {
        cm.sendYesNo("你想离开天空之城，到其他地區去吗？这艘船預定开往#b耶雷弗#k。灑满陽光的落葉和威風吹拂的湖水非常魅力。那里是神獸和女皇居住的島。如果你对騎士感兴趣，可以去那里看看……怎么樣？你想到耶雷弗去吗？\r\n\r\n移动时间大約是#b4分鐘#k，費用是#b1000#k金币。");
    } else if (status == 1) {
        if (cm.getMeso() < 1000) {
            cm.sendNext("你明明沒有錢嘛……必須有#b1000#k金币才可以去。");
        } else {
            cm.gainMeso(-1000);
            cm.warp(130000210, 0);
        }
        cm.dispose();
    }
}