var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
	if (status == 1) {
	    qm.sendNext("对英雄很有帮助的礼物。请不要拒絕。");
	    qm.dispose();
	    return;
	}
	status--;
    }
    if (status == 0) {
	qm.sendSimple("啊，英雄...我好想你喔！  \r\n#b#L0#(害羞的樣子)#l");
    } else if (status == 1) {
	qm.askAcceptDecline("我從以前就决定遇見英雄要送您一个礼物...我知道您忙着回村莊，可是...可以收下我誠心的礼物吗？");
    } else if (status == 2) {
	qm.forceStartQuest();
	qm.sendNextS("礼物的材料就放在这附近的箱子里面。虽然有點麻煩，可是请您将箱子打破后，里面的材料 #b#t4032309##k 和 #b#t4032310##k带回來。我就会立刻帮您組裝。", 1);
    } else if (status == 3) {
	qm.summonMsg(18);
	qm.dispose();
    }
}

function end(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
	if (status == 0) {
	    qm.dispose();
	    return;
	}
	status--;
    }
    if (status == 0) {
	qm.sendNext("材料都带回來了吗？那么请您等一下，只要这樣組裝一下... \r\n\r\n#fUI/UIWindow.img/QuestIcon/4/0# \r\n#i3010062# 1 #t3010062# \r\n\r\n#fUI/UIWindow.img/QuestIcon/8/0# 95 经验值");
    } else if (status == 1) {
	if (qm.getQuestStatus(21013) == 1) {
	    qm.gainItem(3010062, 1);
	    qm.gainExp(95);
	    qm.forceCompleteQuest();
	}
	qm.sendNextPrevS("好了，椅子做好了！嘿嘿！就算是英雄也有疲勞的时候，因此我從很早以前就想送英雄一把椅子当作礼物。", 1);
    } else if (status == 2) {
	qm.sendNextPrevS("就算是英雄也不可能永远都很强大。英雄應該也有疲勞吃力的时候，有时也会感到脆弱。可是能够克服那些的人才配当英雄不是吗？", 1);
    } else if (status == 3) {
	qm.summonMsg(19);
	qm.dispose();
    }
}