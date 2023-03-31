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
    //if (!qm.getPlayer().isGM()) { //not available, sry
	//qm.dispose();
	//return;
    //}
    if (status == 0) {
	qm.sendYesNo("你决心成为机械师了吗？现在还有重新选择的机会。只要停止对话，放弃任务，然后和其他人对话就行。请你好好考虑一下。你真的要选择机械师吗？你认为这个职业适合你的反抗者之路吗？");
    } else if (status == 1) {
	qm.sendNext("欢迎你成为正式的反抗者。从现在开始，你就是机械师了。你身为可以操控机械的人，要利用所有知识和方法来对付眼前的敌人。");
	if (qm.getJob() == 3000) {
	    qm.gainItem(1492014,1); //1492065 desert eagle
	    qm.expandInventory(1, 4);
	    qm.expandInventory(2, 4);
	    qm.expandInventory(4, 4);
	    qm.changeJob(3500);
	    //30001061 = capture, 30001062 = call, 30001068 = mech dash
	    qm.teachSkill(30001068,1,0);
	}
	qm.forceCompleteQuest();
    } else if (status == 2) {
	qm.sendNextPrev("我还扩大了您的设备等库存的库存槽数。 明智地使用这些插槽，并用反抗者所需的物品填满它们。");
    } else if (status == 3) {
	qm.sendNextPrev("现在......我希望你走出去，向世界展示反抗者的力量。");
	qm.safeDispose();
    }
}