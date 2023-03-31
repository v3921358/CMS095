/*
 * The return of the Hero
 * Rien Cold Forest 1
 */

var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
	if (status == 3) {
	    qm.sendNext("哎呀，不用客氣啦！送英雄一瓶药水也不是什么了不起的事。倘若您改变心意在告訴我吧！");
	    qm.dispose();
	    return;
	}
	status--;
    }
    if (status == 0) {
	qm.sendNext("咦？ 这个島上的什么人？ 喔， 您认識 #p1201000#吗？ #p1201000#到这里有什么事情...啊，这位是不是#p1201000#大人认識的人呢？神么？你说这位是英雄吗？");
    } else if (status == 1) {
	qm.sendNextPrev("     #i4001170#");
    } else if (status == 2) {
	qm.sendNextPrev("这位正是 #p1201000#家族數百年等待的英雄！喔喔！难怪看起來不是什么平凡的人物...");
    } else if (status == 3) {
	qm.askAcceptDecline("但是，因为黑魔法师的詛咒而在巨冰里沉睡着，所以，好像英雄的體力都消耗掉了的樣子。#b我給你一个恢復體力用的药水，赶緊喝喝看#k。");
    } else if (status == 4) { // TODO HP set to half
	qm.sendNext("您先大口喝下，我再繼續说下去~");
	qm.gainItem(2000022, 1);
	qm.forceStartQuest();
    } else if (status == 5) {
	qm.sendNextPrevS("#b(药水該怎么喝呢？...想不起來了...)#k", 3);
    } else if (status == 6) {
	qm.summonMsg(0xE);
	qm.dispose();
    }
}

function end(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
	status--;
    }
    if (status == 0) {
	qm.sendNext("长久以來尋找英雄的痕跡，在冰雪中挖掘，果然真正的英雄出现了！預言果真是真的！#p1201000#大人做了正确的選擇英雄回來了，现在沒有必要再畏懼黑魔法师了！");
    } else if (status == 1) {
	qm.sendNextPrev("啊！真是的，我耽誤英雄太久了。先收拾起快乐的心情...其他企鵝應該也有同樣的想法。我知道您很忙，不过在前往村莊的路上 #b也请您和其他企鵝们談一談#k。可以和英雄談话，大家應該会很兴奮！ \n\r #fUI/UIWindow.img/QuestIcon/4/0# \r #i2000022# #t2000022# 5 \r #i2000023# #t2000023# 5 \n\r #fUI/UIWindow.img/QuestIcon/8/0# 16 经验值");
    } else if (status == 2) {
	qm.sendNextPrev("想要升級吗？不曉得您有沒有获得技能點數。在枫之谷內透过转职之后每上升1級就会获得3點的技能點數。按下 #bK鍵#k 就能栏位就能确认。");
	if (qm.getQuestStatus(21010) == 1) {
	    qm.gainExp(16);
	    qm.gainItem(2000022, 5);
	    qm.gainItem(2000023, 5);
	    qm.forceCompleteQuest();
	}
    } else if (status == 3) {
	qm.sendNextPrevS("#b(这么亲切的说明，可是我什么都想不起來。我真的是英雄吗？那么先确认技能看看...可是我該怎么确认呢？)#k");
    } else if (status == 4) {
	qm.summonMsg(0xF);
	qm.dispose();
    }
}