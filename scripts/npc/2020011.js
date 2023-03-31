/* Arec
 Thief 3rd job advancement
 El Nath: Chief's Residence (211000001)
 
 Custom Quest 100100, 100102
 */

 var status = -1;
 var job;
 
 function action(mode, type, selection) {
     if (mode == 1) {
         status++;
     } else {
         if (status == 1) {
             cm.sendOk("等您下定决心再次找我吧.");
             cm.safeDispose();
             return;
         }
         status--;
     }
 
     if (status == 0) {
         if (cm.getQuestStatus(2373)==2 && cm.getQuestStatus(2374)!=1&& cm.getQuestStatus(2374)!=2) {
             //cm.sendOk("看来卡任务了！让我来帮你到下一环节！");
             cm.forceStartQuest(2374);
             cm.safeDispose();
             return;
         }
         if (!(cm.getJob() == 410 || cm.getJob() == 420 || cm.getJob() == 432)) {
             cm.sendOk("请找您的转职教官!");
             cm.safeDispose();
             return;
         }
         if ((cm.getJob() == 410 || cm.getJob() == 420 || cm.getJob() == 432) && cm.getPlayerStat("LVL") >= 70) {
             if (cm.getJob() != 432 && cm.getPlayerStat("RSP") > (cm.getPlayerStat("LVL") - 70) * 3) {
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
             cm.sendOk("请确保你有足够的资格转职. (等级在 70+)");
             cm.safeDispose();
         }
     } else if (status == 1) {
         if (cm.getPlayerStat("LVL") >= 70 && (cm.getJob() == 432 || cm.getPlayerStat("RSP") <= (cm.getPlayerStat("LVL") - 70) * 3)) {
             if (cm.haveItem(4031058)) {
                  cm.gainItem(4031058, -1);
                 if (cm.getJob() == 410) { // ASSASIN
                     cm.changeJob(411); // HERMIT
                     cm.sendOk("成功三转-无影人");
                     cm.safeDispose();
                 } else if (cm.getJob() == 420) { // BANDIT
                     cm.changeJob(421); // CDIT
                     cm.sendOk("成功三转-独行客");
                     cm.safeDispose();
                 } else if (cm.getJob() == 432) { // 
                     cm.changeJob(433); // 
                     cm.sendOk("专职成功-暗影双刀");
                     cm.safeDispose();
                 }
             } else {
                 cm.sendOk("#e你真是一个坚强的人.\r\n#r请到林中之城-禁忌祭坛打项链\r\n再到冰封雪域-雪原圣地回答任务问题\r\n雪原圣地位置从尖锐的绝壁I 正下方中间传送点进去 !");
                 cm.dispose();
             }
         } else {
             cm.sendOk("你的技能点数还没点完..");
             cm.dispose();
         }
     }
 }
 