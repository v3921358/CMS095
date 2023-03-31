

var havePendulum = false;
var complete = false;
var inQuest = false;

function start() {
	if (cm.getQuestStatus(3230) == 1) {
		inQuest = true;
	} else {
		inQuest = false;
	}
	dh = cm.getEventManager("DollHouse");
	status = -1;
	action(1, 0, 0);
}

function action(mode, type, selection) {
	if (mode == 0 && status == 0) {
		cm.dispose();
		return;
	} else if (mode == 0 && status == 1) {
		cm.sendNext("我就知道你会留下来。完成你已经开始的事情是很重要的!现在请找到那个看起来不一样的玩具屋，把它弄坏，然后#b#t4031094##k 给我带来!");
		cm.dispose();
		return;
	}
	if (mode == 1) {
		status++;
	} else {
		status--;
	}
	if (inQuest == true) {
		if (status == 0) {
			if (cm.haveItem(4031094)) {
				cm.sendNext("哦哇，你确实找到了看起来不同的玩具屋，并找到了#b#t4031094##k!太不可思议了!!有了这个，路迪布里姆钟塔就又能运行了!谢谢你的工作，这是对你努力的一点小奖励。在此之前，请检查您的库存，看看是否已满。");
				havePendulum = true;
			} else {
				cm.sendSimple("你好,在那里。我是负责保护这个房间的。在里面，你会看到一堆玩具屋，你可能会发现其中一个看起来和其他的有点不同。你的工作是找到它，打破它的门，找到它的#b#t4031094##k，这是不可分割的一部分。这是有时间限制的，如果你弄坏了错误的玩具屋，你就会被赶出去，所以请小心。\r\n#L0##bI want to get out of here.#k#l");
			}
		} else if (status == 1) {
			if (havePendulum == true) {
				if (!cm.canHold(2000010)) {
					cm.sendNext("你还没找到嘛？???");
				}
				cm.sendNextPrev("你觉得呢?你喜欢我给你#b100 #t2000010##k吗?非常感谢你帮我们。由于你的英勇努力，钟塔将再次运行，而来自另一个维度的怪物似乎也消失了。我现在就放你出去。回头见!");
				if (complete == false) {
					cm.completeQuest(3230);
					cm.gainExp(2400);
					cm.gainItem(4031094, -1);
					cm.gainItem(2000010, 100);
					complete = true;
				}
			} else {
				cm.sendYesNo("你确定你现在要放弃吗?好的,然后……但请记住，下次你来这里的时候，玩具屋会换位置，你得再仔细检查每一个玩具屋。你觉得呢?你还想离开这里吗?");
			}
		} else if (status == 2) {
			cm.getPlayer().getEventInstance().removePlayer(cm.getChar());
			cm.dispose();
		}
	} else {
		if (status == 0) {
			cm.sendNext("什么……我们一直禁止人们进入这个房间因为一个来自另一个维度的怪物正藏在这里。我不知道你是怎么进来的，但我得请你马上离开，因为在这个房间里很危险。");
		} else if (status == 1) {
			cm.warp(221023200, 4);
			cm.dispose();
		}
	}
}