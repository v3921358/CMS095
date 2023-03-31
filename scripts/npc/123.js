/*
@	Name:Custom Chair item Gachapon
	AcarsiaSEA
 */

var status = -1;

function action(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
	status--;
    }
    if (status == 0) {
	if (cm.haveItem(5220082)) {
	    cm.sendYesNo("You have some #bChair Gachapon Tickets#k there.\r\nWould you like to redeem for a random Chair??");
	} else {
	    cm.sendOk("You don't have any Chair gachapon tickets with you. Please get a Chair gachapon ticket before coming back to me. Thank you.");
	    cm.safeDispose();
	}
    } else if (status == 1) {
	var item;
	if (cm.getDoubleFloor(cm.getDoubleRandom() * 70) == 0) {
	    var rareList = new Array();

	    item = cm.gainGachaponItem(rareList[cm.getDoubleFloor(cm.getDoubleRandom() * rareList.length)], 1);
	} else {
	    var itemList = new Array(3010177);
	    item = cm.gainGachaponItem(itemList[cm.getDoubleFloor(cm.getDoubleRandom() * itemList.length)], 1);
	}
	if (item != -1) {
	    cm.gainItem(5220082, -1);
	    cm.sendOk("You have obtained #b#t" + item + "##k.");
	} else {
	    cm.sendOk("Please check your item inventory and see if you have any Chair Gachapon Ticket, or if the inventory is full.");
	}
	cm.safeDispose();
    }
}