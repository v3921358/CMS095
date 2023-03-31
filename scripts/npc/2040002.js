var dh;
var entry = true;

function start() {
    dh = cm.getEventManager("DollHouse");
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {

    if (mode == -1) {
        cm.dispose();
    } else {
        if (mode == 0 && status == 0) {
            cm.sendNext("我明白。这是非常可以理解的，考慮到你将面臨內部一个非常危險的怪物的事实。如果你觉得心脏的改变，那么请你跟我说话。我确信可以從別人像你使用的帮助。");
            cm.dispose();
            return;
        } else if (mode == 0 && status == 2) {
            cm.sendNext("我明白。这是非常可以理解的，考慮到你将面臨內部一个非常危險的怪物的事实。如果你觉得心脏的改变，那么请你跟我说话。我确信可以從別人像你使用的帮助。");
            cm.dispose();
            return;
        }
        if (mode == 1) {
            status++;
        } else {
            status--;
        }
        if (cm.getQuestStatus(3230) == 1) {
            if (status == 0) {
                cm.sendYesNo("嗯...我是 #b#p2040001##k. 我听说过很多关于你的事情... 你能帮我找回 #b#t4031093##k 吗拜託了!");
            } else if (status == 1) {
                cm.sendNext("非常感謝。其实, #b#p2040001##k 问你拿  #b#t4031093##k 为测试自己的能力，看看你是否能处理这个问題，所以不要把它当做一个隨機请求的方式。我觉得你这樣的人能处理好逆境.");
            } else if (status == 2) {
                cm.sendYesNo("前段时间，一个怪物來到这里從另一个層面得益于尺寸的裂縫，並偷走了鐘擺。它躲在自己的房间里那边偽裝成一个玩具屋。这一切看起來是一樣的我，所以沒有辦法找到它。你会帮助我们找到它？");
                if (dh != null && dh.getProperty("noEntry") != null && dh.getProperty("noEntry").equals("true")) {
                    entry = false;
                }
            } else if (status == 3) {
                cm.sendNext("好的，我会带你到另一个房间，那边有許多相同的玩具屋，不过你仔細看会发现有所不同，你的任務是打破真正的玩具屋然后把#t4031094#带回來給我。.");
            } else if (status == 4) {
                cm.sendNextPrev("你需要在时间內找到#t4031094# 然后像我回報。");
            } else if (status == 5) {
                if (dh == null || entry == false) {
                    cm.sendPrev("玩具屋內好像有人了。我只能让一个人在里面，所以请你等待吧。");
                } else {
                    cm.removeAll(4031093);
                    dh.startInstance(cm.getChar());
                }
                cm.dispose();
            }
        } else if (cm.getQuestStatus(3230) == 2) {
            cm.sendNext("謝謝你 #h #, 我们得到了 #b#t4031094##k 回並銷毀從另一个邪惡的怪物。值得慶幸的是，我们还沒有找到一个这樣的，因为。我不知道怎么感謝你帮助我们这么多。希望你在#m220000000#过得愉快!");
            cm.dispose();
        } else {
            cm.sendOk("我们在这里守衛这个房间的玩具士兵，防止任何人进入这个地方。");
            cm.dispose();
        }
    }
}