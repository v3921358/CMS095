var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
	if (status == 6) {
	    qm.sendNext("有什么好猶豫的呢？假如#p1201001#沒有反應，也沒什么好失望的。快點去摸#p1201001#。在適当的地方#b點選#k就可以了。");
	    qm.dispose();
	    return;
	}
	status--;
    }
    if (status == 0) {
	qm.sendNextS("和黑魔法师决鬥的英雄...几乎沒有任何相关的資訊保存下來。預言書上也只記錄有五名英雄，沒有任何和外貌有关的資料。你难道什么都想不起來吗？", 8);
    } else if (status == 1) {
	qm.sendNextPrevS("什么都想不起來...", 2);
    } else if (status == 2) {
	qm.sendNextPrevS("原來如此。黑魔法师的詛咒不会那么容易就被解除。可是就算如此，英雄您和过去之间應該有什么連結。到底有什么武器呢？因为决鬥的关係，武器或衣服都不見了...啊！对了！ #b武器#k！", 8);
    } else if (status == 3) {
	qm.sendNextPrevS("武器？", 2);
    } else if (status == 4) {
	qm.sendNextPrevS("之前在冰雪中挖掘英雄时曾经找到一些厲害的武器。当时推测應該是英雄使用过的東西，因此保存在村莊中央。您经过时沒看到吗？ #b#p1201001##k... \r\r#i4032372#\r\r长成这樣...", 8);
    } else if (status == 5) {
	qm.sendNextPrevS("难怪我觉得很奇怪，有把 #p1201001# 就在村莊里面...", 2);
    } else if (status == 6) {
	qm.askAcceptDecline("是，就是那个。根据紀錄英雄的武器会认主人。假如您是使用 #p1201001#的英雄，抓住 #p1201001#时應該会有什么反應。快點去按#b#p1201001#吧。#k");
    } else if (status == 7) {
	if (qm.getQuestStatus(21100) == 0) {
	    qm.forceCompleteQuest();
	}
	qm.sendOkS("假如 #p1201001#有反應的话，您就使用#p1201001#的英雄 #b狂狼勇士#k。", 8);
	qm.showWZEffect("Effect/Direction1.img/aranTutorial/ClickPoleArm");
	qm.dispose();
    }
}

function end(mode, type, selection) {
    qm.dispose();
}
