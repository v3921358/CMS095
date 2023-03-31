/* Kedrick
	Fishking King NPC
*/

var status = -1;
var sel;

function action(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
		if (status == 0) {
			cm.dispose();
			return;
		}
	status--;
    }

    if (status == 0) {
	cm.sendSimple("你想做什么?\n\r #L4#钓鱼指南#l \n\r #L5##i1142146:#Trade 500 Golden Fish Egg (Medal of Fishing King [Period : 30 days])#l");
    } else if (status == 1) {
	sel = selection;
	if (sel == 4) {
	    cm.sendOk("你需要在10级以上，用鱼竿和鱼饵才能开始钓鱼。每1分钟就会钓一次鱼.");
	    cm.safeDispose();
	} else if (sel == 5) {
	    if (cm.haveItem(4000518, 500)) {
		if (cm.canHold(1142146)) {
		    cm.gainItem(4000518, -500);
		    cm.gainItemPeriod(1142146, 1, 30);
		    cm.sendOk("Woah, I guess you must have spend quite a lot of effort in the Fishing Lagoon fishing for these eggs. Here, take it. The #bFishing King Medal#k!")
		} else {
		    cm.sendOk("Please check if you have sufficient inventory slot for it.");
		}
	    } else {
		cm.sendOk("Please get me 500 #i4000518:# Golden Fish Egg in exchange for a Fishing King medal!")
	    }
	    cm.safeDispose();
	}
    }
}