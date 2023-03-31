var status = -1;

function action(mode, type, selection) {
	if (mode == 0 && status == 0) {
		cm.dispose();
		return;
	}
	if (mode == 1)
		status++;
	else
		status--;
	if (status == 0) {
		cm.sendSimple("你好，我是兴儿，我想 #b吃年糕#k...#b\r\n#L0#我给你带来了年糕！#l\r\n#L1#我应该怎么做?#l\r\n#L2##v4001101# x 50 换 #v1002798##l\r\n#L3#离开这里\r\n#k");
	} else if (status == 1) {
		if (selection == 0) {
			if (!cm.isLeader()) {
				cm.sendNext("只有队长给我送来的年糕我才吃！");
			} else {
				if (cm.haveItem(4001101,10)) {
					cm.achievement(100);
					cm.gainItem(4001101, -10);
					cm.givePartyExp_PQ(70, 1.5);
					//cm.givePartyNX(250);
					cm.addPartyTrait("will", 5);
					cm.addPartyTrait("sense", 1);
					cm.endPartyQuest(1200);
					cm.warpParty(910010500);
					cm.setPartyBossLog("月秒的年糕");
					/*var party = cm.getParty().getMembers();
                    var it = party.iterator();
                    while (it.hasNext()) {
                        var cPlayer = it.next();
						cPlayer.getPlayer().setBossLog("月秒的年糕");
					}*/
				} else {
					cm.sendNext("你没有10个年糕.. ");
				}
			}
		} else if (selection == 1) {
			cm.sendNext("这是迎月花山，当有满月的时候，月亮兔子会在这里做敲年糕。为了使满月，种植从报春花中获得的种子，当所有6个种子都被种植，他们满月将出现。然后会召唤“月秒兔”，你必须保护它不受其他试图攻击它的怪物的伤害。如果“月秒兔”死了，你将无法完成任务，我将感到饥饿和愤怒。。。");

		} else if (selection == 2) {
            if (cm.haveItem(1002798, 1)) {
                cm.sendOk("你已经有了");
            } else if (!cm.canHold(1002798, 1)) {
                cm.sendOk("你背包满了");
            } else if (cm.haveItem(4001101, 50)) {
                cm.gainItem(4001101, -50); 
                cm.gainItem(1002798, 1);
            } else {
                cm.sendOk("你需要100个月妙的元宵");
            }
            cm.dispose();
        } else if (selection == 3) {
			cm.removeAll(4001095);
			cm.removeAll(4001096);
			cm.removeAll(4001097);
			cm.removeAll(4001098);
			cm.removeAll(4001099);
			cm.removeAll(4001100);
			cm.warp(910010500);
			cm.dispose();
        }
		cm.dispose();
	}
}