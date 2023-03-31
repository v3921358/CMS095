load('nashorn:mozilla_compat.js');
/* ===========================================================
 Resonance
 NPC Name: 		Minister of Home Affairs
 Map(s): 		Mushroom Castle: Corner of Mushroom Forest(106020000)
 Description: 	Quest -  Over the Castle Wall (2)
 =============================================================
 Version 1.0 - Script Done.(18/7/2010)
 =============================================================
 */

importPackage(Packages.client);

var status = -1;

function start(mode, type, selection) {
    status++;
    if (mode != 1) {
        if (type == 1 && mode == 0)
            status -= 2;
        else {
            qm.sendNext("真正地？有没有其他方法可以穿透城堡？如果你不知道，那就来看我吧。");
            qm.dispose();
            return;
        }
    }
    if (status == 0)
        qm.sendYesNo("就像我告訴你的，打破障礙不能成为慶祝的理由。这是因为我们的蘑菇王國的城堡完全拒絕进入我们王國之外的任何人，所以你很难做到这一點。要想辦法进入，你能先調查一下城堡的外墻吗？");
    if (status == 1)
        qm.sendNext("走过蘑菇樹林，当你到达選擇的分裂道路时，向城堡走去。祝你好运。");
    if (status == 2) {
        //qm.forceStartQuest();
        //qm.forceStartQuest(2322, "1");
        qm.gainExp(11000);
        qm.sendOk("在这个地区航行很好。");
        qm.forceCompleteQuest();
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
        qm.sendOk("嗯，我明白了…所以他们完全关閉了入口和一切。");
    if (status == 1) {
        qm.gainExp(11000);
        qm.sendOk("在这个區域航行很好。");
        qm.forceCompleteQuest();
        qm.dispose();
    }
}
	