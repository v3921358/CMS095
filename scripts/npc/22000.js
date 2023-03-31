/* 
	NPC Name: 		Shanks
	Map(s): 		Maple Road : Southperry (60000)
	Description: 		Brings you to Victoria Island
*/
var status = 0;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (status >= 0 && mode == 0) {
    cm.sendOk("恩... 我猜你还有想在这做的事？");
	cm.dispose();
	return;
    }
    if (mode == 1)
	status++;
    else
	status--;

    if (status == 0) {
    cm.sendYesNo("搭上了这艘船，你可以前往更大的大陸冒險。 只要給我 #e150 金币#n，我会带你去 #b維多利亞島#k。 不过，一旦离开了这里，就不能再回來喽。 你想要去維多利亞島吗？");
    } else if (status == 1) {
	if (cm.haveItem(4031801)) {
    cm.sendNextPrev("既然你有推薦信，我不会收你任何的費用。收起來，我们将前往維多利亞島，坐好，旅途中可能会有點动盪！");
	} else {
	    cm.sendNext("真的只要 #e150 金币#n 就能搭船!!");
	}
    } else if (status == 2) {
	if (cm.haveItem(4031801)) {
	    cm.sendNextPrev("既然你有推薦信，我不会收你任何的費用。收起來，我们将前往維多利亞島，坐好，旅途中可能会有點动盪！");
	} else {
	    if (cm.getPlayerStat("LVL") >= 7) {
		if (cm.getMeso() < 150) {
		    cm.sendOk("什么？你说你想搭免費的船？ 你真是个怪人！");
		    cm.dispose();
		} else {
		    cm.sendNext("哇! #e150#n 金币我收到了！ 好，准备出发去維多利亞港喽！");
		}
	    } else {
		cm.sendOk("让我看看... 我觉得你还不够强壮。 你至少要达到7等我才能让你到維多利亞港喽。");
		cm.dispose();
	    }
	}
    } else if (status == 3) {
	if (cm.haveItem(4031801)) {
	    cm.gainItem(4031801, -1);
	    cm.warp(2010000,0);
	    cm.dispose();
	} else {
	    cm.gainMeso(-150);
	    cm.warp(2010000,0);
	    cm.dispose();
	}
    }
}