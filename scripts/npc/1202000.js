/*
 * Tutorial Lirin
 */

var status = -1;

function start() {
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        status--;
    }
    if (cm.getPlayer().getMapId() != 140090000) {
        if (status == 0) {
            cm.sendSimple("您有什么想知道的呢？有的话我会再度说明。  \n\r #b#L1#該如何使用小地图？#l \n\r #b#L2#該如何使用任務栏位？#l \n\r #b#L3#該如何使用道具？#l \n\r #b#L4#如何使用普通攻擊？#l \n\r #b#L5#如何捡取東西？#l \n\r #b#L6#如何穿裝备？#l \n\r #b#L7#如何打开技能視窗？#l \n\r #b#L8#如何把技能放到快捷鍵上？#l \n\r #b#L9#如何打破一个箱子？#l \n\r #b#L10#如何坐椅子？#l \n\r #b#L11#如何查看地图資訊？#l");
        } else {
            cm.summonMsg(selection == -1 ? 0 : selection);
            cm.dispose();
        }
    } else {
        if (cm.getInfoQuest(21019).equals("")) {
            if (status == 0) {
                cm.sendNext("您....终于醒了!");
            } else if (status == 1) {
                cm.sendNextPrevS("...你是谁?", 2);
            } else if (status == 2) {
                cm.sendNextPrev("我已经在这等你好久了. 等待那个与黑磨法师对抗的英雄甦醒...!");
            } else if (status == 3) {
                cm.sendNextPrevS("等等, 你说什么..? 你是谁...?", 2);
            } else if (status == 4) {
                cm.sendNextPrevS("等等... 我是谁...? 我既不起以前的事情了... 啊...我頭好痛啊..", 2);
            } else if (status == 5) {
                cm.updateInfoQuest(21019, "helper=clear");
                cm.showWZEffect("Effect/Direction1.img/aranTutorial/face");
                cm.showWZEffect("Effect/Direction1.img/aranTutorial/ClickLirin");
                cm.playerSummonHint(true);
                cm.dispose();
            }
        } else {
            if (status == 0) {
                cm.sendNext("你还好吗？");
            } else if (status == 1) {
                cm.sendNextPrevS("我... 什么都不記得了...这里是哪里？还有你是谁？", 2);
            } else if (status == 2) {
                cm.sendNextPrev("放輕鬆. 因为黑磨法师的詛咒，让你想不起以前的了. 以前的事情已经不重要了. 我会帮助你想起所有事情的.");
            } else if (status == 3) {
                cm.sendNextPrev("你曾经是这里的英雄. 几百年以前, 你与你的朋友们对抗黑魔法师，拯救了枫之谷的世界. 但那个时候，黑磨法师对你下了詛咒，将你冰凍起來，直到抹去你所有的記憶为止.");
            } else if (status == 4) {
                cm.sendNextPrev("这里是瑞恩島。黑魔法师将您囚禁在此地。詛咒的氣候混亂，经年覆蓋冰霜和雪。您在冰之窟的深处被发现的。");
            } else if (status == 5) {
                cm.sendNextPrev("我的名字是#p1202000#。 是瑞恩島的成员。瑞恩族根据古老的預言從很久以前就等待英雄回來。还有...终于找到您了。现在。就是这里....");
            } else if (status == 6) {
                cm.sendNextPrev("好像一下说太多了。就算您不能馬上了解也沒有关係。您会慢慢知道所有事....#b我们先去村莊吧#k。在抵达村莊之前，如果还有什么想知道，我会逐一向您说明。");
            } else if (status == 7) {
                cm.playerSummonHint(true);
                cm.warp(140090100, 1);
                cm.dispose();
            }
        }
    }
}