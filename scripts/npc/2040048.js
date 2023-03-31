var status = -1;

function action(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
	if (status <= 1) {
	    cm.sendNext("需要去再來找我吧!");
	    cm.dispose();
	    return;
	}
	status--;
    }
    if (status == 0) {
    cm.sendSimple("你有沒有听过海滩与所謂的 #b黃金海滩#k, 这个地方在維多利亞呢?? 我可以现在帮助你到那个地方只需要 #b1500 金币#k, 或者如果你有一张 #bVIP 到黃金海滩的票#k 那么就可以免費去..\r\n\r\n#L0##b 我願意付 1500 金币.#l\r\n#L1# 我有一张 VIP 到黃金海滩的票.#l\r\n#L2# 什么是VIP到黃金海滩的票#k?#l");
    } else if (status == 1) {
	if (selection == 0) {
	    cm.sendYesNo("所以你想付 #b1500 金币#k 然后去黃金海滩??");
	} else if (selection == 1) {
	    status = 2;
	    cm.sendYesNo("所以你有一张 #bVIP 到黃金海滩的票#k?");
	} else if (selection == 2) {
	    status = 4;
	    cm.sendNext("你一定好奇什么是 #bVIP 到黃金海滩的票#k. 哈哈，这是非常可以理解的。貴賓門票到弗洛里納海滩是一个项目在哪里，只要你在身上，你可以用自己的方式來弗洛里納海滩免費。那就是，即使我们不得不买这些，但不幸的是我在我的寶貴的暑假失去了一个雷几周前这樣一个难得的道具.");
	}
    } else if (status == 2) {
	if (cm.getMeso() < 1500) {
	    cm.sendNext("你沒有足够的金币也沒有VIP票 滾吧!");
	    cm.dispose();
	} else {
	    cm.gainMeso(-1500);
	    cm.saveLocation("FLORINA");
	    cm.warp(120030000, 0);
	    cm.dispose();
	}
    } else if (status == 3) {
	if (cm.haveItem(4031134)) {
	    cm.saveLocation("FLORINA");
	    cm.warp(120030000, 0);
	    cm.dispose();
	} else {
	    cm.sendNext("你确定你有#bVIP 到黃金海滩的票#k. 吗? 确认一下好吗....");
	    cm.dispose();
	}
    } else if (status == 4) {
	cm.sendNext("你一定好奇神么是 #bVIP 到黃金海滩的票#k. 哈哈，这是非常可以理解的。貴賓門票到弗洛里納海滩是一个项目在哪里，只要你在身上，你可以用自己的方式來弗洛里納海滩免費。那就是，即使我们不得不买这些，但不幸的是我在我的寶貴的暑假失去了一个雷几周前这樣一个难得的项目.");
    } else if (status == 5) {
	cm.sendNextPrev("我回來沒有它，我就觉得可怕沒有它。希望有人把它捡起來，並把它安全的地方。无论如何，这是我的故事，谁知道，你可以把它捡起來，並把它用好。如果您有任何问題，请隨时问。");
    } else if (status == 6) {
	cm.dispose();
    }
}