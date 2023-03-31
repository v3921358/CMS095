var status = -1;
var selectionLog = [];
function start(d, c, b) {

	qm.forceStartQuest();
	qm.dispose();

}
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
		if (qm.getJob() == 3210 && qm.getQuestStatus(23033) == 1) {

			if (status == a++) {
				qm.sendNext('你把#o9001032#破坏掉啦！呵呵，果然不出我所料！我的眼光从来都没有错。我知道你一定可以做到。这样一来，我们村的能量不足现象暂时就可以缓解了。你真的为我们村立下了大功！');
			} else {
				if (status === a++) {
					qm.sendYesNo('你的能力达到了这个程度，我就可以放心地让你进入下一阶段的课程了。虽然其他人劝我，说还很危险……但是我想你一定不会被技能难倒，一定可以成为更强的唤灵斗师！');
				} else {
					if (status === a++) {

						qm.changeJob(3211);
						qm.forceCompleteQuest(23033);
						qm.sendNext('我已经让你转职了。现在你已经不是以前的你了。更强、更快、更华丽的技能世界将会展现在你面前。使用起来也很不容易，但是……你有什么好害怕的呢？你连那么危险的任务都完成了！');
						qm.dispose();
					} else {
						if (status === a++) {
							qm.gainItem(1142244, 1);
							qm.forceCompleteQuest(29943);
							qm.sendNext('让我们下次课程再见吧。\n\n在那之前，希望你能以反抗者的身份，继续努力。');
							qm.dispose();
						}
					}
					
				}
			}
		}else if(!qm.isQuestFinished(23033)){
			qm.forceStartQuest();
			qm.dispose();
		}

	}
}
