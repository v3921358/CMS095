/**
	Jake - Victoria Road : Subway Ticketing Booth (103000100)
**/

var meso = new Array(500, 1200, 2000);
var item = new Array(4031036, 4031037, 4031711);
var selector;
var menu = "";

function start() {
	status = -1;
	action(1, 0, 0);
}

function action(mode, type, selection) {
	if (status == 0 && mode == 0) {
		cm.dispose();
		return;
	} else if (status == 1 && mode == 0) {
		cm.sendNext("你必须购买票才能进入,一旦你购买了,你可以通过右边的大门进入大门.你想买什么?");
		cm.dispose();
		return;
	}
	if (mode == 1)
		status++;
	else
		status--;
	if (status == 0) {
		if (cm.getPlayerStat("LVL") <= 10) {
			cm.sendNext("你必须购买票才能进入,一旦你购买了,你可以通过右边的大门进入大门.你想买什么?");
			cm.dispose();
		} else {
			menu += "\r\n#L0##b地铁线#k#l";
			menu += "\r\n#L1##b废都广场站#k#l";
			menu += "\r\n#L2##b新叶城#k#l";
			menu += "\r\n#L3##b工地B1#k#l";
			menu += "\r\n#L4##b工地B2#k#l";
			menu += "\r\n#L5##b工地B3#k#l";
			cm.sendSimple("你必须购买票才能进入,一旦你购买了,你可以通过右边的大门进入大门.你想买什么?" + menu);
		}
	} else if (status == 1) {
		selector = selection;
		switch (selector) {
			case 3:
				cm.warp(910360000, 0);
				cm.dispose();
				return;
			case 4:
				cm.warp(910360100, 0);
				cm.dispose();
				return;
			case 5:
				cm.warp(910360200, 0);
				cm.dispose();
				return;
		}
		selection += 1;
		cm.sendYesNo("你会买这张票吗#k? 它会花费你 " + meso[selector] + " 金币. 在购买之前，请确保你背包有足够的空间。");
	} else if (status == 2) {
		if (cm.getMeso() < meso[selector]) {
			cm.sendNext("你是否有足够的金币。");
			cm.dispose();
		} else {
			if (selector == 0) {
				cm.sendNext("你可以在售票口插入票。我听说1区有一些珍贵的物品，但有这么多的陷阱，所有的地方最早回来了。祝你好运。");
			} else if (selector == 1) {
				cm.sendNext("你可以在售票口中插入票。我听说2区有难得的，珍贵的物品，但有这么多的陷阱，所有的地方最早回来了。请注意安全。");
			} else {
				cm.sendNext("你可以在售票口中插入票。祝你好运。");
			}
			cm.gainMeso(-meso[selector]);
			cm.gainItem(item[selector], 1);
			cm.dispose();
		}
	}
}