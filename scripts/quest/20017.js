/*
 NPC Name: 		Cygnus
 Description: 		Quest - Encounter with the Young Queen
 */

var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        if (status == 2) {
            qm.sendNext("鼓起你的勇氣，让我知道你准备好了.");
            qm.safeDispose();
            return;
        }
        status--;
    }
    if (status == 0) {
        qm.sendNext("#p1101002#  欢迎，很高兴見到你！ 我的名字是 #p1102000#. 我是訓练教练.");
    } else if (status == 1) {
        qm.sendNextPrev("你看过 #p1101001#.");
    } else if (status == 2) {
        qm.sendNextPrev("准备好 #p1101001# .");
    } else if (status == 3) {
        qm.askAcceptDecline("去杀了 #b15 #r#o100122#s in #m130010100##k#k? 左边傳送點#b訓练场地II#k.");
    } else if (status == 4) {
        qm.summonMsg(12);
        qm.forceStartQuest(20020);
        qm.forceCompleteQuest(20100);
        qm.forceStartQuest();
        qm.dispose();
    }
}

function end(mode, type, selection) {
}
