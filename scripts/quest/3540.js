/*
	NPC Name: 		Grendel the really old
	Description: 		Quest - In search of the lost memory
*/
var status = -1;

function start(mode, type, selection) {
    if (mode == -1) {
	qm.dispose();
    } else {
	if (mode == 1)
	    status++;
	else
	    status--;
	if (status == 0) {
	    qm.sendNext("啊，原来是你。没想到很久之后还能看到你。我很高兴看到曾经是青涩的新手的你成为了大神。看到很久不见但还记得我的你，我的心里充满了温暖。你是在寻找遗忘的记忆吗？想起来那已经是很久很久以前的事了，事隔多年，真是让人怀念啊。这样吧。你再去#b旁观者#k吧。相信他会帮助你。那么再见……");
	    qm.forceCompleteQuest();
	    qm.forceCompleteQuest(3507);
	    qm.dispose();
	}
    //	qm.forceStartQuest();
    }
}

function end(mode, type, selection) {
    if (mode == -1) {
	qm.dispose();
    } else {
	if (mode == 1)
	    status++;
	else
	    status--;
	if (status == 0) {
	    qm.sendNextPrev("Test");
	    qm.dispose();
	}
    }
}