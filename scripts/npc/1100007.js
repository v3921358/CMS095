var status = -1;

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        if (status == 0) {
		    cm.sendNext("不想去的话就算了……");
            cm.dispose();
		}
        status--;
    }
    if (status == 0) {
	    cm.sendYesNo("嗯，啊……你是说……你想离开維多利亞，到其他地區去？乘坐这條船，可以到达#b耶雷弗#k。那是个陽光灑满落葉、威風吹皺湖水的、美麗的、居住着神獸和女皇的地方。你要到那里去吗？\r\n\r\n移动时间大概是#b2分鐘#k，費用是#b1000#k金币。");
	} else if (status == 1) {
		if (cm.getMeso() < 1000) {
			cm.sendNext("你明明沒有錢嘛……必須有#b1000#k金币才可以去。");
		} else {
			cm.gainMeso(-1000);
			cm.warp(130000210,0);
		}
		cm.dispose(); 
    }
}