var status = -1;

function end(mode, type, selection) {
    if (mode == 0) {
	if (status == 0) {
	    qm.sendNext("这是一个重要的决定。");
	    qm.safeDispose();
	    return;
	}
	status--;
    } else {
	status++;
    }
    if (status == 0) {
	qm.sendYesNo("谢谢你这么爽快地接受……你真的经过深思熟虑了吗？豹弩游侠虽然很强，但也很难操作。在操作骑宠的同时，还必须进行攻击，因此在操控方面要求很高。你能做到吗？希望你仔细考虑清楚之后再回答我。");
    } else if (status == 1) {
	qm.sendNext("哈哈哈！很好！欢迎你正式成为反抗者。从现在开始，你就是豹弩游侠了。希望你能骑着骑宠，灵活机动地消灭敌人。");
	if (qm.getJob() == 3000) {
	    qm.gainItem(1462092,1);
	    qm.expandInventory(1, 4);
	    qm.expandInventory(2, 4);
	    qm.expandInventory(4, 4);
	    qm.changeJob(3300);
	    qm.teachSkill(30001061, 1, 0);
	    qm.teachSkill(30001062, 1, 0);
	    qm.getPlayer().fakeRelog();
	}
	qm.forceCompleteQuest();
    } else if (status == 2) {
	qm.sendNextPrev("我还扩大了您的设备等库存的库存槽数。 明智地使用这些插槽，并用反抗者所需的物品填满它们");
    } else if (status == 3) {
	qm.sendNextPrev("现在......我希望你走出去，向世界展示反抗者的力量。");
	qm.safeDispose();
    }
}