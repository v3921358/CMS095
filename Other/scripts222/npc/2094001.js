var status = -1;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 1)
	status++;
    else
	status--;
    if (status == 0) {
	cm.removeAll(4001117);
	cm.removeAll(4001120);
	cm.removeAll(4001121);
	cm.removeAll(4001122);
	cm.sendSimple("#b#L0#让我离开这.#l\r\n#L1#给我海盗帽.#l#k");
    } else if (status == 1) {
	if (selection == 0) {
		if (!cm.canHold(4001455, 1)) {
			cm.sendOk("请确保你的背包空间足够.");
			cm.dispose();
			return;
		}
		cm.gainItem(4001455, 1);
	    //cm.gainNX(1000);
	    cm.addTrait("will", 15);
	    cm.gainExp_PQ(120, 2.0);
		cm.setBossLog("海盗船");
	    cm.getPlayer().endPartyQuest(1204);
	    cm.warp(251010404,0);
	} else { //TODO JUMP
		if (cm.haveItem(cm.isGMS() ? 1003267 : 1002573, 1)) {
			cm.sendOk("你已经拥有。");
		} else if (cm.haveItem(1002573, 1)) {
		    if (cm.haveItem(4001455, 20)) {	
				if (cm.canHold(1003267,1)) {
					cm.gainItem(1002573, -1);
					cm.gainItem(4001455, -20);
		    	    cm.gainItem(1003267,1);
					cm.sendOk("我把帽子给你了。");
		    	} else {
					cm.sendOk("请确保你的背包空间足够.");
		        } 
		    } else {
				cm.sendOk("请确保你有20个海盗帽子碎块与我交换。");
		    }
		} else if (cm.haveItem(1002572, 1)) {
		    if (cm.haveItem(4001455, 20)) {	
				if (cm.canHold(1002573,1)) {
					cm.gainItem(1002572, -1);
					cm.gainItem(4001455, -20);
		    	    cm.gainItem(1002573,1);
					cm.sendOk("我把帽子给你了。");
		    	} else {
					cm.sendOk("请确保你的背包空间足够.");
		        } 
		    } else {
				cm.sendOk("请确保你有20个海盗帽子碎块与我交换。");
		    }
		} else {
		    if (cm.haveItem(4001455, 20)) {	
				if (cm.canHold(1002572,1)) {
					cm.gainItem(4001455, -20);
		    	    cm.gainItem(1002572,1);
					cm.sendOk("我把帽子给你了。");
		    	} else {
					cm.sendOk("请确保你的背包空间足够.");
		        } 
		    } else {
				cm.sendOk("请确保你有20个海盗帽子碎块与我交换。");
		    }
		}
	}
	cm.dispose();
    }
}