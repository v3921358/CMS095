/*
	Description: 	Quest - Tasty Milk 1
*/

var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
	if (status == 2) {
	    qm.sendNext("我自己想找答案是沒有用的。我最好找#比大师更聰明!#k");
	    qm.dispose();
	    return;
	}
	status--;
    }
    if (status == 0) {
	qm.sendNext("唉。这是行不通的。我还需要別的東西。沒有植物。沒有肉。什麽，你不知道？但是你是主人，你也比我大。你一定知道什麽对我有好处！");
    } else if (status == 1) {
	qm.sendNextPrevS("#但我沒有。这不像年齡和这个有什麽关系…", 2);
    } else if (status == 2) {
	qm.askAcceptDecline("因为你年紀大了，你也必須在这个世界上更有经验。你比我了解更多。哦，很好。我会问一个比你大的人，大师！");
    } else if (status == 3) {
	qm.forceStartQuest();
	qm.sendOkS("#b(你已经问过爸爸一次了，但是你沒有更好的主意。是时候再问他了！)#k", 2);
	qm.dispose();
    }
}

function end(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
	status--;
    }
    if (status == 0) {
	qm.sendOk("什麽？你还在餵那只蜥蜴？唉，它不会吃掉一把幹草还是豬肉？挑剔的小家夥。哦？蜥蜴还是嬰兒？");
    } else if (status == 1) {
	qm.gainExp(260);
	qm.forceCompleteQuest();
	qm.dispose();
    }
}