var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
	if (status == 2) {
	    qm.sendNext("不知道你是怎么知道的，一猜就猜到了。从不久前开始，魔法森林南部的#o1110100#突然变得凶暴起来。很多#o1110100#的性格变得很奇怪，变得非常阴沉。");
	    qm.dispose();
	    return;
	}
	status--;
    }
    if (status == 0) {
	qm.sendNextS("不知道你是怎么知道的，一猜就猜到了。从不久前开始，魔法森林南部的#o1110100#突然变得凶暴起来。很多#o1110100#的性格变得很奇怪，变得非常阴沉。",8);
    } else if (status == 1) {
	qm.sendNextS("听说好像很多地方都出现了这种现象，所以我仔细打听了一下，好像所有的异常现象都和某种人偶有关。人偶……真的非常奇怪。",8);
    } else if (status == 2) {
	qm.sendNextS("虽然不知道传闻是不是真的，说不定这次#o1110100#的事情也和人偶有关。虽然我不知道你为什么想知道#o1110100#变得凶暴的原因，但如果你想知道的话，可以和我一起进行调查。怎么样？你愿意吗？",8);
	} else if (status == 3) {
	qm.askAcceptDecline("不知道是不是真的像传闻说的那样，#o1110100#发生变化的原因是人偶……请你去打猎#r25个#o1110130##k，寻找#b#o1110130#的人偶#k。");
    } else if (status == 4) {
	qm.forceStartQuest();
	qm.dispose();
    }
}

function end(mode, type, selection) {
    qm.dispose();
}