/*
	Name: GMS-like Gachapon
	Place: New Leaf City
 */

var status = -1;

function action(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
	status--;
    }
    if (status == 0) {
	if (cm.haveItem(4001618)) {
	    cm.sendYesNo("You have some #bLegendary Maple leaf#k there.\r\nWould you like to try your luck?");
	} else {
	    cm.sendOk("You don't have a single ticket with you. Please buy the ticket at the department store before coming back to me. Thank you.");
	    cm.safeDispose();
	}
    } else if (status == 1) {
	var item;
	if (cm.getDoubleFloor(cm.getDoubleRandom() * 1) == 0) {
	    var rareList = new Array(3010063, 3010099, 3010117, 3010148, 3010164, 3010172, 3010285, 3010219, 3010366, 3010367, 3010326, 3010054, 3010071, 3010124, 3010151, 3010155, 3010156, 3010168, 3010188, 3010266, 3010277, 3010286, 3010301, 3010302, 3010411, 3010412, 3010474, 3010085, 3010215, 3010349);

	    item = cm.gainGachaponItem(rareList[cm.getDoubleFloor(cm.getDoubleRandom() * rareList.length)], 1);
	} else {
	    var itemList = new Array(3010063, 3010063);

	    item = cm.gainGachaponItem(itemList[cm.getDoubleFloor(cm.getDoubleRandom() * itemList.length)], 1);
	}

	if (item != -1) {
	    cm.gainItem(4001618, -1);
	    cm.sendOk("You have obtained #b#t" + item + "##k.");
	} else {
	    cm.sendOk("Please check your item inventory and see if you have Legendary Maple leaf, or if the inventory is full.");
	}
	cm.safeDispose();
    }
}