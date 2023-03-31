var status = -1;

function start(mode, type, selection) {
    status++;
    if (mode != 1) {
        if (type == 1 && mode == 0)
            status -= 2;
        else {
            qm.sendNext("嗯？什麽？害怕 陰險的狐貍 ？沒想到我弟弟这麽膽小。");
            qm.dispose();
            return;
        }
    }
    if (status == 0)
        qm.sendAcceptDecline("你不觉得奇怪吗？最近的雞怎麽和以前不一樣了？以前它们会下很多 雞蛋 ，但现在越來越少了。是不是因为狐貍增多了呢？那樣的话，必須赶緊想辦法才行。你说对不对？");
    else if (status == 1) {
        qm.forceStartQuest();
        qm.sendNext("好吧，让我们去消滅狐貍吧。你先去 #b后院#k 消滅#r10只 陰險的狐貍#k 。我会負責剩下的事情的。好了，你快到 后院 去吧～");
    } else if (status == 2) {
        qm.evanTutorial("UI/tutorial/evan/10/0", 1);
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
        qm.sendNext("陰險的狐貍，消滅掉了吗？");
    if (status == 1)
        qm.PlayerToNpc("#b你说要去收拾剩下的狐貍的，怎麽回事？");
    if (status == 2)
        qm.sendNextPrev("啊，那个嘛？我后來是去了，但走错了路，怕被 #o9300385# 抓去做人质，所以就回來了。");
    if (status == 3)
        qm.PlayerToNpc("#b該不会是害怕狐貍而躲起來了吧？");
    if (status == 4)
        qm.sendNextPrev("你在胡说什麽啊？！我为什麽会害怕狐貍？！我一點都不害怕狐貍！");
    if (status == 5)
        qm.PlayerToNpc("#b……啊，有一只 #o9300385# !");
    if (status == 6)
        qm.sendNextPrev("啊！快躲起來！");
    if (status == 7)
        qm.PlayerToNpc("#b...");
    if (status == 8)
        qm.sendNextPrev("...");
    if (status == 9)
        qm.sendNextPrev("……你这家夥。別嚇哥哥我！哥哥我的心脏不好，不能受驚嚇！");
    if (status == 10)
        qm.PlayerToNpc("#b(所以叫哥哥才不願意去，叫我去。)");
    if (status == 11)
        qm.sendNextPrev("哼哼，不管怎樣，陰險的狐貍 消滅掉了。辛苦你了。我把一个路过的冒險家送我的東西送給你，作为給你的報酬。來，拿着。 \r\n\r\n#fUI/UIWindow.img/QuestIcon/4/0# \r\n#i1372043# 1 #t1372043# \r\n#i2022621# 25 #t2022621# \r\n#i2022622# 25 #t2022622#s \r\n\r\n#fUI/UIWindow.img/QuestIcon/8/0# 910 exp");
    if (status == 12) {
        qm.forceCompleteQuest();
        qm.gainItem(1372043, 1);
        qm.gainItem(2022621, 25);
        qm.gainItem(2022622, 25);
        qm.gainExp(910);
        qm.sendNextPrev("是#b魔法师的攻擊武器短杖。#k 虽然你也可能沒什麽用，但拿在手裏到处走，还是很帥的，哈哈哈。");
    }
    if (status == 13) {
        qm.sendPrev("狐貍的數量确实增加了，对吧？奇怪。狐貍的數量为什麽会增加呢？看來必須調查一下。");
        qm.dispose();
    }
}