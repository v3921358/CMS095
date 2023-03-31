var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
	if (status == 4) {
	    qm.sendNext("哎呀？ 你要拒絕, 是觉得自己一人也可以修练的意思吗？ 比起自己一个人，接受其他人的帮助應該可以得到更好的結果啊. 现在也該學學跟其他人相处的方法了.");
	    qm.dispose();
	    return;
	}
	status--;
    }
    if (status == 0) {
	qm.sendNextS("一副好像想起什么的臉 果然终極之矛认出你的樣子. 那么你就是#b使用过终極之矛的英雄, 狂狼勇士#k沒错. 这以外还沒有想起什么呢？ 例如与终極之矛相关的技能...", 8);
    } else if (status == 1) {
	qm.sendNextPrevS("#b(告訴我了几个記得的技能.)#k", 2);
    } else if (status == 2) {
	qm.sendNextPrevS("虽然不多但也有收穫了. 那么從现在起要用盡全力找回以前的能力了. 虽然喪失記憶, 但至少是曾经做过的事，一定可以很快地找回能力的。", 8);
    } else if (status == 3) {
	qm.sendNextPrevS('要怎么找回能力呢？', 2);
    } else if (status == 4) {
	qm.askAcceptDecline("这个嘛... 只有一个辦法. 修练! 修练! 修练! 修练在修练的话，總有一天身體可以找回那个被遺忘的感觉！");
    } else if (status == 5) {
	qm.forceStartQuest();
	qm.sendNext("如果使用更熟悉的武器應該会更好 已給您#b终極之矛#k 一把在修练时可以有效使用. 带着那个武器...");
    } else if (status == 6) {
	qm.sendPrev("厄嗯, 稍等. 现在开始該怎么做才好呢........");
	qm.dispose();
    }
}

function end(mode, type, selection) {
    qm.dispose();
}