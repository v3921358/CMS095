/*
	Noran
 */

var status = -1;

function start() {
    cm.sendSimple("我能帮你什么吗? \r #b#L0#我想在封印的勇士石上释放封印.#l \r #L1#我想打造物品.#l");
}

function action(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else if (status == 1) {
	status--;
	selection = 0;
    } else {
	cm.dispose();
	return;
    }
	
    switch (status) {
	case 0:
	    if (selection == 0) {
		cm.sendNext("我叫诺兰，是技术人员。在这里，每个人都在谈论你。如果你能够打败机械怪物，我也希望能够帮助你。使用火焰技术，可以创造更强大的物品.")
	    } else {
		status = 9;
		cm.sendSimple("我能帮你什么吗? \r #b#L0#魔法刀#l \r #L1#穿甲弹#l");
	    }
	    break;
	case 1:
	    cm.sendNextPrev("据说Blaze已经成功收集了漂浮在宇宙中的能量。如果这是真的，就能获得巨大的能量。这种能量的一小部分可以从密封的战士石中提取，但它必须被打开才能使用。把它给我，我就把封印解开.");
	    break;
	case 2:
	    cm.sendSimple("把密封的石头给我 \r #b#L0#给被封印的勇者之石和服务费。1000逆奥银币#l \r #L1#给被封印的贤者之石和服务费。1000逆奥银币#l \r #L2#给被封印的圣者之石及服务费。1000逆奥银币#l");
	    break;
	case 3:
	    if (selection == 0) {
		if (cm.haveItem(4020010, 1) && cm.haveItem(4032181, 1000)) {
		    cm.gainItem(4032169, 1);
		    cm.gainItem(4020010, -1);
		    cm.gainItem(4032181, -1000);
		} else {
		    cm.sendNext("是吗?你没有必需的材料。你需要被封印的勇者之石和1000逆奥银币才能制造武士石.");
		}
	    } else if (selection == 1) {
		if (cm.haveItem(4020011, 1) && cm.haveItem(4032181, 1000)) {
		    cm.gainItem(4032170, 1);
		    cm.gainItem(4020011, -1);
		    cm.gainItem(4032181, -1000);
		} else {
		    cm.sendNext("是吗?你没有必需的材料。你需要被封印的贤者之石和1000逆奥银币才能创造怀斯曼石.");
		}
	    } else {
		if (cm.haveItem(4020012, 1) && cm.haveItem(4032181, 1000)) {
		    cm.gainItem(4032171, 1);
		    cm.gainItem(4020012, -1);
		    cm.gainItem(4032181, -1000);
		} else {
		    cm.sendNext("是吗?你没有必需的材料。制作圣石需要被封印的圣者之石和1000逆奥银币。");
		}
	    }
	    cm.dispose();
	    break;
	case 10:
	    if (selection == 0) {
		if (cm.haveItem(4032168, 1) && cm.haveItem(4032181, 2500) && cm.haveItem(4032171, 1) && cm.haveItem(2070006, 1) && (cm.getMeso() >= 150000000)) {
		    cm.gainItem(4032171, -1);
		    cm.gainItem(4032168, -1);
		    cm.gainItem(2070006, -1);
		    cm.gainItem(4032181, -2500);
		    cm.gainMeso(-150000000);
		    cm.gainItem(2070019, 1);
		} else {
		    cm.sendNext("是吗?你没有必需的材料。你需要圣者之石，奈米机械，齿轮镖，逆奥银币2500块和1.5亿金币创造魔法投掷刀.");
		}
	    } else {
		if (cm.haveItem(4032168, 1) && cm.haveItem(4032181, 2500) && cm.haveItem(4032170, 1) && cm.haveItem(2330003, 1) && (cm.getMeso() >= 150000000)) {
		    cm.gainItem(4032170, -1);
		    cm.gainItem(4032168, -1);
		    cm.gainItem(2330003, -1);
		    cm.gainItem(4032181, -2500);
		    cm.gainMeso(-150000000);
		    cm.gainItem(2330007, 1);
		} else {
		    cm.sendNext("是吗?你没有必需的材料。.\n\r 你需要贤者之石, 奈米机械, 银子弹, 逆奥银币2500块和1.5亿金币创造 穿甲子弹.");
		}
	    }
	    cm.dispose();
	    break;
    }
}