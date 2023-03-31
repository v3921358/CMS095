var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        if (status == 0) {
            qm.sendNext("有很緊急的事情。要是拒絕的话，肯定会后悔的哦？#b有关你长矛的事情#k，也就是有关你的过去。谁知道呢？……说不定这个长矛能够喚醒你的能力？");
            qm.dispose();
            return;
        }
        status--;
    }
    if (status == 0) {
        qm.askAcceptDecline("修炼进展得如何？喲，等級升得这么高了？难怪人们都说濟州島是養馬的天堂，維多利亞港是升級的天堂……对了，现在还不是说閒话的时候。能否麻煩你回島上來一趟？");
    } else if (status == 1) {
        qm.forceStartQuest(21200, "3");
        qm.sendOk("#b保管在#m140000000##k的你的#b#p1201001##k突然出现了奇怪的反應。据说长矛在呼喚自己主人的时候才会发出那樣的反應。#b也許有什么事情要轉达給你？#k请速回島上一趟吧。");
        qm.dispose();
    }
}

function end(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        if (status == 11) {
            qm.sendNext("你这家伙！好歹也要努力傳奇一下吧？");
            qm.dispose();
            return;
        }
        status--;
    }
    if (status == 0) {
        qm.sendNextS("嗡嗡嗡嗡嗡……", 2);
    } else if (status == 1) {
        qm.sendNextPrevS("#b（#p1201001#在发出嗡鳴声。奇怪，那边的少年是谁？）#k", 2);
    } else if (status == 2) {
        qm.sendNextPrevS("#b（以前沒見过他啊？怎么看起來不太像人類？）#k", 2);
    } else if (status == 3) {
        qm.sendNextPrev("喂！狂狼勇士！还听不見我的声音吗？到底听不听得見？唉，煩死了！");
    } else if (status == 4) {
        qm.sendNextPrevS("#b（咦？这是谁的声音？怎么听起來像个凶巴巴的少年……）#k", 2);
    } else if (status == 5) {
        qm.sendNextPrev("唉……哪有这樣的主人啊？丟开武器在冰窟里睡了几百年，现在連话都听不懂了……");
    } else if (status == 6) {
        qm.sendNextPrevS("你是谁啊？", 2);
    } else if (status == 7) {
        qm.sendNextPrev("啊，狂狼勇士？现在听到我的声音了？是我啊，不記得我了？我就是武器#b长矛 #p1201002##k啊？");
    } else if (status == 8) {
        qm.sendNextPrevS("#b（……#p1201002#？#p1201001#会说话？）#k", 2);
    } else if (status == 9) {
        qm.sendNextPrev("不至于吧？这么吃驚？再怎么失憶，總不能連我都忘了吧？太不够意思了！");
    } else if (status == 10) {
        qm.sendNextPrevS("不好意思，真的一點都想不起來。", 2);
    } else if (status == 11) {
        qm.sendYesNo("说声不好意思就能算了？！几百年來就我一个人孤苦伶仃地，有多寂寞你知道吗？不管怎樣，你快點給我想起來！");
    } else if (status == 12) {
        qm.sendNextS("#b（一口一个自己是#p1201001#、#p1201002#的，还越说越生氣了。再这么说下去也不会有啥进展，还是先走到 #p1201000#跟前，好好商量商量。）#k", 2);
        qm.forceCompleteQuest();
    } else if (status == 13) {
        qm.MovieClipIntroUI(true);
		qm.warp(914090200, 0);
        qm.dispose();
    }
}