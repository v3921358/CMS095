var status = 0;
var jobId;
var jobName;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 0 && status == 2) {
        cm.sendOk("请重试.");
        cm.dispose();
        return;
    }
    if (mode == 1)
        status++;
    else
        status--;
    if (status == 0) {
        if (cm.getJob() == 0) {
            if (cm.getPlayer().getLevel() >= 8) {
                cm.sendNext("你要转职成为一位 #r法师#k ?");
            } else {
                cm.sendOk("你还不能转职成为 #r法师#k 蔡B8.")
                cm.dispose();
            }
        } else {
            if (cm.getPlayer().getLevel() >= 30 && cm.getJob() == 200) { // 法师
                if (cm.haveItem(4031012, 1)) {
                    if (cm.haveItem(4031012, 1)) {
                        status = 20;
                        cm.sendNext("我看到你完成了测试. 想要繼續转职请點下一頁!");
                    } else {
                        if (!cm.haveItem(4031009)) {
                            cm.gainItem(4031009, 1);
                        }
                        cm.sendOk("请去找 #r法师转职教官#k.");
                        cm.dispose();
                    }
                } else {
                    status = 10;
                    cm.sendNext("你已经可以转职了,要转职请點下一頁.");
                }
            } else {
                if(cm.getQuestStatus(20718)==1){
                    cm.sendNext("一定是场误会。经过调查，汉斯并没什么可疑！请回去找#p1103003#.。");
                    cm.forceCompleteQuest(20718);
                        
                }else{
                    cm.sendNext("你好,我是法师转职官.");
                }
                cm.dispose();
            }
        }
    } else if (status == 1) {
        cm.sendNextPrev("一旦转职了就不能反悔,如果不想转职请點上一頁.");
    } else if (status == 2) {
        cm.sendYesNo("你真的要成为一位 #r法师#k ?");
    } else if (status == 3) {
        if (cm.getJob() == 0) {
            cm.changeJob(200); // 法师
            cm.resetStats(4, 4, 20, 4);
        }
        cm.gainItem(1372005, 1);
        cm.sendOk("转职成功 ! 请去开創天下吧.");
        cm.dispose();
    } else if (status == 11) {
        cm.sendNextPrev("你可以選擇你要转职成为一位 #r巫师(火,毒)#k, #r巫师(冰,雷)#k 或 #r僧侶#k.");
    } else if (status == 12) {
        cm.askAcceptDecline("但是我必須先测试你,你准备好了吗 ?");
    } else if (status == 13) {
        cm.gainItem(4031009, 1);
        cm.warp(101040300);
        cm.sendOk("请去找 #b法师转职教官#k . 他会帮助你的.");
        cm.dispose();
    } else if (status == 21) {
        cm.sendSimple("你想要成为什么 ? #b\r\n#L0#巫师(火,毒)#l\r\n#L1#巫师(冰,雷)#l\r\n#L2#僧侶#l#k");
    } else if (status == 22) {
        if (selection == 0) {
            jobName = "巫师(火,毒)";
            jobId = 210; // FP
        } else if (selection == 1) {
            jobName = "巫师(冰,雷)";
            jobId = 220; // IL
        } else {
            jobName = "僧侶";
            jobId = 230; // CLERIC
        }
        cm.sendYesNo("你真的要成为一位 #r" + jobName + "#k?");
    } else if (status == 23) {
        cm.changeJob(jobId);
        cm.gainItem(4031012, -1);
        cm.sendOk("转职成功 ! 请去开創天下吧.");
        cm.dispose();
    }
}