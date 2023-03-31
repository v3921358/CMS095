load('nashorn:mozilla_compat.js');
/* ===========================================================
 Resonance
 NPC Name: 		Minister of Home Affairs
 Map(s): 		Mushroom Castle: Corner of Mushroom Forest(106020000)
 Description: 	Quest -  Exploring Mushroom Forest(1)
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
            qm.sendNext("请不要对我们蘑菇王國失去信心。");
            qm.dispose();
            return;
        }
    }
    if (status == 0)
        qm.sendYesNo("为了拯救公主，你必須首先在蘑菇森林中航行。King Pepe建立了一个强大的屏障，禁止任何人进入城堡。请为我们調查这件事。 ");
    if (status == 1)
        qm.sendNext("你会跑到蘑菇森林的屏障，向你现在正站的地方走去。请小心。我听说这个地區到处都是瘋狂的、令人恐懼的怪物。");
    if (status == 2) {
        //qm.forceStartQuest();
        //qm.forceStartQuest(2314,"1");
        qm.gainExp(8300);
        qm.sendOk("我明白了，事实上，这並不是一个正常的障礙。那裏工作很棒。如果不是你的帮助，我们就不会知道这到底是怎麽回事。");
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
        qm.sendOk("我看到你徹底調查了蘑菇樹林的屏障。它是什麽樣的？");
    if (status == 1) {
        qm.gainExp(8300);
        qm.sendOk("我明白了，事实上，这並不是一个正常的障礙。那裏工作很棒。如果不是你的帮助，我们就不会知道这到底是怎麽回事。");
        qm.forceCompleteQuest();
        qm.dispose();
    }
}
	