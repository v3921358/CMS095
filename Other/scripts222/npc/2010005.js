﻿/*
	Shuri the Tour Guide - Orbis (200000000)
*/

var pay = 2000;
var ticket = 4031134;
var msg;
var check;
var status = -1;
var access;

function action(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
	if (status == 1) {
	    cm.sendNext("需要去再來找我吧!");
	    cm.safeDispose();
	    return;
	}
	status--;
    }
    if (status == 0) {
	cm.sendSimple("你有沒有听过海滩与所謂的 #b黃金海滩#k, 这个地方在維多利亞呢?? 我可以现在帮助你到那个地方只需要 #b2000#k 金币 或者如果你有一张 #b#t"+ ticket +"##k 那么就可以免費去..#k\r\n\r\n#L0##b我願意付 "+pay+" 金币.#k#l\r\n#L1##b我有 #t"+ticket+"##k#l\r\n#L2##b什么是 #t"+ticket+"#?#k#l");
    } else if (status == 1) {
	if (selection == 0 || selection == 1) {
	    check = selection;
	    if (selection == 0) {
		msg = "所以你想付 #b"+pay+" 金币#k 然后去 #m110000000#?";
	    } else if (selection == 1) {
		msg = "所以你有一张 #b#t"+ticket+"##k 到 #m110000000#?";
	    }
	    cm.sendYesNo(msg+" 那么现在就走吧！");
	} else if (selection == 2) {
		status = 2;
	    cm.sendNext("你一定好奇什么是 #b#t"+ticket+"##k. 哈哈，这是非常可以理解的。貴賓門票到弗洛里納海滩是一个项目在哪里，只要你在身上，你可以用自己的方式來弗洛里納海滩免費。那就是，即使我们不得不买这些，但不幸的是我在我的寶貴的暑假失去了一个雷几周前这樣一个难得的道具.");
	}
    } else if (status == 2) {
	if (check == 0) {
	    if (cm.getMeso() < pay) {
		cm.sendOk("你好像沒有足够的金币。。");
		cm.safeDispose();
	    } else {
		cm.gainMeso(-pay);
		access = true;
	    }
	} else if (check == 1) {
	    if (!cm.haveItem(ticket)) {
		cm.sendOk("你好像沒有 #b#t"+ticket+"##k。。。。");
		cm.safeDispose();
	    } else {
		access = true;
	    }
	}
	if (access == true) {
	    cm.saveLocation("FLORINA");
	    cm.warp(110000000, 0);
	    cm.dispose();
	}
    } else if (status == 3) {
	cm.sendNext("我回來沒有它，我就觉得可怕沒有它。希望有人把它捡起來，並把它安全的地方。反正这是我的故事，谁知道，你可以把它捡起來，並把它用好。如果您有任何问題，请隨时问");
    } else if (status == 4) {
	cm.dispose();
    }
}