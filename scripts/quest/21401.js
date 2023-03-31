var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        if (status == 3) {
            qm.sendOk("有很緊急的事情。要是拒絕的话，肯定会后悔的哦？#b有关你长矛的事情#k，也就是有关你的过去。谁知道呢？……说不定这个长矛能够喚醒你的能力？");
            qm.dispose();
            return;
        }
        status--;
    }
    if (status == 0) {
        qm.sendNext("...你问我为什么这副德性吗? ... 我不太想说...不，我无法对主人你隱瞞...");
    } else if (status == 1) {
        qm.sendNextPrev("...你被困在冰雪中數百年的时间...我也被困在冰雪中。漫长等待的歲月。沒有主人獨自生活真的太...因此我的心里產生了黑暗。");
    } else if (status == 2) {
        qm.sendNextPrev("可是当你醒來后，黑暗完全消失了。主人回來了，就沒什么好遺憾的。應該会忘的一乾二淨...可是那像是我的错觉。");
    } else if (status == 3) {
        qm.askAcceptDecline("拜託你。狂狼勇士...请阻止我。可以停止我暴走的人只有主人你了。我无法再忍耐了！ 请你 #r擊敗暴走的我吧#k！")
    } else if (status == 4) {
        var em = qm.getEventManager("aran4th");
        var a1 = qm.getMap(914020000);
        if (qm.getQuestStatus(21401) == 0) {
            qm.forceStartQuest(21401);
        }
        if (em == null) {
            qm.sendNext("找不到副本，请联絡管理员。");
        } else {
            if (a1.playerCount() <= 0) {
                a1.killAllMonsters(true);
                em.startInstance(qm.getPlayer());
                qm.dispose();
            } else {
                qm.sendNext("嗯...等一下。暴走实在是太嚴重了...这樣下去会受傷。");
            }
        }
    }
}

function end(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        status--;
    }
    if (status == 0) {
        qm.sendNext("謝謝你。狂狼勇士。託你的福~即时阻止了暴走中的我。虽然想说真是太好了...不过你是主人，这本是就是理所当然的事。");
    } else if (status == 1) {
        qm.askAcceptDecline("看起來现在你的等級真的上升很多。居然可以擊敗暴走的我...就算喚醒從前的能力也可以充分的享受。");
    } else if (status == 2) {
        if (qm.getPlayerStat("RSP") > (qm.getPlayerStat("LVL") - 120) * 3) {
            qm.sendNext("技能點數沒點完。");
            qm.dispose();
        } else {
            if (qm.getJob() != 2112) {
                qm.changeJob(2112);
            }
            qm.gainItem(1142132, 1);
            qm.gainItem(2280003, 1);
        }
        qm.sendNext("你沉睡中的技能全部都喚醒了...已经遺忘了很久，需要再次修炼，但是只要练習就会有所帮助。");
    } else if (status == 3) {
        qm.sendNextPrev("啊，我順便把这期间得知的枫葉祝福技能做成技能書交給你。这是之前你沒有的技能，不过我想應該会有帮助吧！");
    } else if (status == 4) {
        qm.sendNextPrev("可是只憑这些技能，还跟你之前的力量相差甚远。虽然听说你失去的技能可用技能書找回...如果你能将技能全部找回來，熟练这些技能，那就跟真正的你相差不远了。");
    } else if (status == 5) {
        qm.forceCompleteQuest();
        qm.dispose();
    }
}