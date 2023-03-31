var status = -1;
var selectionLog = [];

function end(d, c, b) {
	if (status == 0 && d == 0) {
		qm.dispose();
		return;
	}
	d == 1 ? status++ : status--;
	selectionLog[status] = b;
	var a = -1;
	if (status <= a++) {
		qm.dispose();
	} else {
		if (qm.haveItem(4032738) && qm.getQuestStatus(23024) == 1 && qm.getJob() == 3300) {


			if (status == a++) {
				qm.sendNext('这就是#t4032737#啊？哈哈哈！我的眼光果然没错！我知道你一定可以做到！');
			} else {
				if (status === a++) {
					qm.sendNext('这个任务是我特意从其他人那里抢过来交给你的。黑色之翼的那个家伙，从前让你吃过苦头，不是吗？我把任务交给你，就是想让你向他复仇。执行任务的同时还能复仇，这就是所谓的一石二鸟，不是吗？');
				} else {
					if (status === a++) {
						qm.sendNext('但是即便是我们，也没想到你能完成得这么干净利落……真了不起！你在这么短的时间内，获得了惊人的成长。');
					} else {
						if (status === a++) {
							qm.sendYesNo('那样的话，就没有必要苦恼了！我本来觉得有点早，不过看来好像完全用不着担心了。我可以让你进入下一个阶段了。让你变成和以前完全不同的、拥有更强力量的豹弩游侠。');
						} else {
							if (status === a++) {
								qm.changeJob(3310);
								qm.forceCompleteQuest(23024);
								qm.gainItem(4032738, -1);
								qm.gainItem(1142243, 1);
								qm.forceCompleteQuest(29942);
								qm.updateInfoQuest(1073, '3300=10;3310=201');
								qm.sendNext('我已经让你转职了，同时也传授了你更强的技能。现在你已经不是以前的你了。你已经变成了更快、更坚韧、更强大的豹弩游侠了。尽情享受你新的力量吧！');
							} else {
								if (status === a++) {
									qm.sendNext('下次课程再见。\n\n在那之前，希望你能以反抗者的身份，继续努力。');
									qm.dispose();
								}
							}
						}
					}
				}
			}
		} else if (!qm.isQuestFinished(23024)) {
			qm.forceStartQuest();
			qm.dispose();
		}
	}
}