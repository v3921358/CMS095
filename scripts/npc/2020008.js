/** 
 Tylus: Warrior 3rd job advancement
 El Nath: Chief's Residence (211000001)
 
 Custom Quest 100100, 100102
 */

var status = 0;
var job;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 0 && status == 1) {
        cm.sendOk("等您下定决心再次找我吧.");
        cm.dispose();
        return;
    }
    if (mode == 1)
        status++;
    else
        status--;
    if (status == 0) {
        if (!(cm.getJob() == 110 || cm.getJob() == 120 || cm.getJob() == 130 || cm.getJob() == 2110)) {
            if (cm.getQuestStatus(6192) == 1) {
                if (cm.getParty() != null) {
                    var ddz = cm.getEventManager("ProtectTylus");
                    if (ddz == null) {
                        cm.sendOk("未知的错誤");
                    } else {
                        var prop = ddz.getProperty("state");
                        if (prop == null || prop.equals("0")) {
                            ddz.startInstance(cm.getParty(), cm.getMap());
                        } else {
                            cm.sendOk("其他人已经在试图保护泰勒斯，请稍后再试.");
                        }
                    }
                } else {
                    cm.sendOk("为了保护泰勒斯，请组队!");
                }
            } else if (cm.getQuestStatus(6192) == 2) {
                cm.sendOk("Y你保护了我。谢谢您。我来教你技能.");
                if (cm.getJob() == 112) {
                    if (cm.getPlayer().getMasterLevel(1121002) <= 0) {
                        cm.teachSkill(1121002, 0, 10);
                    }
                } else if (cm.getJob() == 122) {
                    if (cm.getPlayer().getMasterLevel(1221002) <= 0) {
                        cm.teachSkill(1221002, 0, 10);
                    }
                } else if (cm.getJob() == 132) {
                    if (cm.getPlayer().getMasterLevel(1321002) <= 0) {
                        cm.teachSkill(1321002, 0, 10);
                    }
                }
            } else {
                cm.sendOk("願上帝与你同在!");
            }
            cm.dispose();
            return;
        }
        if ((cm.getJob() == 110 || cm.getJob() == 120 || cm.getJob() == 130 || cm.getJob() == 2110) && cm.getPlayerStat("LVL") >= 70) {
            if (cm.getPlayerStat("RSP") > (cm.getPlayerStat("LVL") - 70) * 3) {
                if (cm.getPlayer().getAllSkillLevels() > cm.getPlayerStat("LVL") * 3) { //player used too much SP means they have assigned to their skills.. conflict
                    cm.sendOk("看来你有大量的SP，但你已经在技能上使用了足够的SP。您的SP已重置。#e请再和我谈一次，让转职可以进行.#n");
                    cm.getPlayer().resetSP((cm.getPlayerStat("LVL") - 70) * 3);
                } else {
                    cm.sendOk("嗯…你有太多的#bSP #k。你不能在剩下太多SP的情况下转职.");
                }
                cm.safeDispose();
            } else {
                cm.sendNext("#e你真是一个坚强的人.\r\n#r请到林中之城-禁忌祭坛打项链\r\n再到冰峰雪域-雪原圣地回答任务问题\r\n雪原圣地位置从尖锐的绝壁I 正下方中间传送点进去 !");
            }
        } else {
            cm.sendOk("请确保你有足够的资格转职. (等级在 70+");
            cm.safeDispose();
        }
    } else if (status == 1) {
        if (cm.getPlayerStat("LVL") >= 70 && cm.getPlayerStat("RSP") <= (cm.getPlayerStat("LVL") - 70) * 3) {
            if (cm.haveItem(4031058)) {
                cm.gainItem(4031058, -1);
                if (cm.getJob() == 110) { // FIGHTER
                    cm.changeJob(111); // CRUSADER
                    cm.sendOk("成功三转-勇士");
                    cm.dispose();
                } else if (cm.getJob() == 120) { // PAGE
                    cm.changeJob(121); // WHITEKNIHT
                    cm.sendOk("成功三转-骑士");
                    cm.dispose();
                } else if (cm.getJob() == 130) { // SPEARMAN
                    cm.changeJob(131); // DRAGONKNIGHT
                    cm.sendOk("成功三转-龙骑士");
                    cm.dispose();
                } else if (cm.getJob() == 2110) { // ARAN
                    cm.changeJob(2111); // ARAN
                    if (cm.canHold(1142131, 1)) {
                        cm.forceCompleteQuest(29926);
                        cm.gainItem(1142131, 1); //temp fix
                    }
                    cm.sendOk("成功三转-战神.");
                    cm.dispose();
                }
            } else {
                cm.sendOk("#e你真是一个坚强的人.\r\n#r请到林中之城-禁忌祭坛打项链\r\n再到冰封雪域-雪原圣地回答任务问题\r\n雪原圣地位置从尖锐的绝壁I 正下方中间传送点进去 !");
                cm.dispose();
            }
        } else {
            cm.sendOk("你的技能点数还沒点完.");
            cm.dispose();
        }
    }
}