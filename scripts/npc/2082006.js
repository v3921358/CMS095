var status = -1;
var items;
var itemsq;
var itemsa;
var itemse;

function start() {
	if (cm.isGMS()) { //- fishing items, + hand cannon scroll
		items = Array(5620000, 5620005, 5620001, 5620002, 5620003, 5620004, 5340001, 5050000, 5050000, 5510000, 5510000, 5520000, 5520001, 5570000, 5030005, 5590001, 5252002, 5050001, 5050002, 5050003, 5050004);
		itemsq = Array(1, 1, 1, 1, 1, 1, 1, 10, 100, 10, 100, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
		itemsa = Array(5000, 5000, 5000, 5000, 5000, 5000, 5000, 10000, 100000, 2000, 20000, 10000, 10000, 10000, 1500, 5000, 10000, 2000, 2000, 2000, 2000);
		itemse = Array(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1 , -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1);
	} else {
		items = Array(5620000, 5620005, 5620001, 5620002, 5620003, 5620004, 5340001, 5050000, 5050000, 5510000, 5510000, 5520000, 5520001, 5570000, 5030005, 5590001, 5252002, 5050001, 5050002, 5050003, 5050004);
		itemsq = Array(1, 1, 1, 1, 1, 1, 1, 10, 100, 10, 100, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
		itemsa = Array(5000, 5000, 5000, 5000, 5000, 5000, 5000, 10000, 100000, 2000, 20000, 10000, 10000, 10000, 1500, 5000, 10000, 2000, 2000, 2000, 2000);
		itemse = Array(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1 , -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1);
	}
	action(1, 0, 0);
}

function action(mode, type, selection) {
	if (mode != 1) {
		cm.dispose();
		return;
	}
	status++;
	if (status == 0) {
		cm.sendSimple("Hi. I like #rCash#k. You have #r#e" + cm.getPlayer().getCSPoints(1) + "#n#k #rCash#k. I could sure use some...\r\n\r\n#b#L0#Give me #rCash#b and I'll give you an item.#l#k");
	} else if (status == 1) {
		var selStr = "Maybe you could trade me some #rCash#k? I have lots of great items for you...#b\r\n\r\n";
		for (var i = 0; i < items.length; i++) {
			selStr += "#L" + i + "##v" + items[i] + "##t" + items[i] + "# x " + itemsq[i] + " #r(" + (cm.isGMS() ? (itemsa[i] / 1) : itemsa[i]) + " Cash)#b#l\r\n";
		}
		cm.sendSimple(selStr + "#k");
	} else if (status == 2) {
		if ((items[selection] == 2340000 || items[selection] == 5610000 || items[selection] == 5610001 || items[selection] == 5062001 || items[selection] == 5640000) && cm.getPlayer().getLevel() < 70) {
			cm.sendOk("Sorry but you must be level 70 or above to get this item.");
		} else if (items[selection] == 2022179 && cm.getPlayer().getLevel() < 50) {
			cm.sendOk("Sorry but you must be level 50 or above to get this item.");
		} else if (cm.getPlayer().getCSPoints(1) < itemsa[selection]) {
			cm.sendOk("You don't have enough #rCash#k.. I NEED #rCash#k!");
		} else if (!cm.canHold(items[selection], itemsq[selection])) {
			cm.sendOk("You don't have the inventory space to hold it. I must be legit and make this a fair trade... so hurry up and free your inventory so I can get my #rCash#k!");
		} else {
			//cm.getPlayer().modifyCSPoints(1, -(cm.isGMS() ? (itemsa[selection] / 1) : (itemsa[selection])), true);
			if (itemse[selection] > 0) {
				cm.gainItemPeriod(items[selection], itemsq[selection], itemse[selection]);
			} else {
				cm.gainItem(items[selection], itemsq[selection]);
			}
			cm.sendOk("Thanks a lot for the #rCash#k! Hehe...");
		}
		cm.dispose();
	}
}