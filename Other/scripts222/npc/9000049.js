/*
	Fairy Tale Fairy Crackers - Every Town()
*/

var fromWitch = false;

function start() {
    if (cm.getMapId() == 980040000) {
	fromWitch = true;
	cm.sendSimple("#b(嗯…所以如果你触摸移动的饼干墙或上升的巧克力，那么你会反弹出来…”）Eh？你是谁？哪只猫告诉你你可以在这里找到宝藏？嗯，好吧。既然你走了这么远，不妨试试。你想接受哪一个难度等级？……\r\n\r\n#b#L0# #v03994115##l #L1# #v03994116##l #L2# #v03994117##l");
    } else {
	cm.sendSimple("嘿，你是谁？你是…那些想从这里偷宝藏的忍者之一吗? \r\n #L0##bTreasures?#l \r\n #L1##b(Secretly) Head over to Witch Tower#k#l");
    }
}

function action(mode, type, selection) {
    if (!fromWitch) {
	switch (selection) {
	    case 0:
		cm.sendOk("T...treasures? Who...who said that? Do you think I'd tell you that the Pink Bean Hat made by the witch with Pink Bean that looks like this #v01002971:# can be obtained after clearing normal or hard mode 5 times, and Pink Bean Suit looks like this #v01052202:# can be obtained after getting Pink Bean Hat and go to grave yard through portal in the top right of Witch Tower Entrance? As if!..");
		break;
	    case 1:
		cm.warp(980040000, 0);
		break;
	}
    } else {
	switch (selection) {
	    case 0: {
		var dh = cm.getEventManager("WitchTower_EASY");
		dh.newInstance(cm.getName()).registerPlayer(cm.getPlayer());
		break;
	    }
	    case 1: {
		var aa = cm.getEventManager("WitchTower_Med");
		aa.newInstance(cm.getName()).registerPlayer(cm.getPlayer());
		break;
	    }
	    case 2: {
		var dd = cm.getEventManager("WitchTower_Hard");
		dd.newInstance(cm.getName()).registerPlayer(cm.getPlayer());
		break;
	    }
	}
    }
    cm.dispose();
}