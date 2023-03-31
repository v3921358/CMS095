/* ===========================================================
 Resonance
 NPC Name: 		Maple Administrator
 Description: 	Quest -  Kingdom of Mushroom in Danger
 =============================================================
 Version 1.0 - Script Done.(17/7/2010)
 =============================================================
 */

var status = -1;

function start(mode, type, selection) {
    status++;
    if (mode != 1) {
        if (type == 1 && mode == 0)
            status -= 2;
        else {
            //if(status == 0){
            qm.sendOk("真正地？这是当務之急，所以如果你有时间，请來看我。");
            qm.dispose();
            return;
            //} else if(status == 3){
            //qm.sendNext("Okay. In that case, I'll just give you the routes to the Kingdom of Mushroom. #bNear the west entrance of Henesys,#k you'll find an #bempty house#k. Enter the house, and turn left to enter#b<Themed Dungeon : Mushroom Castle>#k. That's the entrance to the Kingdom of Mushroom. There's not much time!");
            //qm.forceStartQuest();
            //return;
        }
    }
    //}
    if (status == 0)
        qm.sendAcceptDecline("既然你已经获得了这份工作，你看起來已经准备好了。我有件事想请你帮忙。你願意傾听吗？");
    if (status == 1)
        qm.sendNext("发生的事情是蘑菇王國K目前处于混亂狀態。蘑菇王國位于Henesys附近，以爱好和平、聰明的King Mush为特色。最近，他开始感到不適，所以他决定任命他唯一的女兒——王妃維奧塔。K，從那时起，王國一定已经发生了一些事情。");
    if (status == 2)
        qm.sendNext("我不知道确切的細節，但很明顯发生了可怕的事情，所以我认为如果你亲自去那裏評估損害会更好。像你这樣的探險家似乎比拯救蘑菇王國更有能力。我剛剛給你寫了一封推薦信。#k, 所以我建议你馬上去蘑菇王國尋找 #b巡視主任#k.\r\n\r\n#fUI/UIWindow.img/QuestIcon/4/0#\r\n#v4032375# #t4032375#");
    if (status == 3)
        qm.sendYesNo("順便问一下，你知道蘑菇王國在哪裏吗？如果你能找到你的路，那就好了，但是如果你不介意的话，我可以把你带到入口处。");
    if (status == 4) {
        qm.gainItem(4032375, 1);
        qm.forceStartQuest();
        qm.dispose();
    }
}

function end(mode, type, selection) {
    status++;
    if (mode != 1) {
        if (type == 1 && mode == 0)
            status -= 2;
        else {
            qm.dispose();
            return;
        }
    }
    if (status == 0)
        qm.sendNext("那是作業指導员的推薦信吗？？！这是什麽，你是來拯救我们的，蘑菇王國吗？");
    if (status == 1)
        qm.sendNextPrev("可以。既然这封信是從职业指導老师那裏來的，我想你就是真正的那个人了。我很抱歉沒有早點向你介紹我自己。我是負責保护King Mush的負責人。正如你所看到的，这个臨时藏身地是由安全和士兵队伍保护的。我们的处境可能是可怕的，但无论如何，欢迎來到蘑菇王國。");
    if (status == 2) {
        qm.forceCompleteQuest();
        qm.gainItem(4032375, -1);
        qm.forceStartQuest(2312);
        qm.dispose();
    }
}
