var status = -1;
var answer = false;
function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        if (status == 5) {
            cm.sendNext("#b（本來不打算要下手的，突然手一滑！）#k");
            return;
        }
        status--;
    }
    if (cm.getPlayer().getMapId() == 931000011) {
        cm.dispose();
        return;
    }

    if (cm.getInfoQuest(23007).indexOf("vel00=1") == -1 && cm.getInfoQuest(23007).indexOf("vel01=1") == -1) {
        if (status == 0) {
            cm.sendNext("不可以再靠近了…！");
        } else if (status == 1) {
            cm.sendNextPrev("怎么会來这里？这里是禁止出入的地方。");
        } else if (status == 2) {
            cm.sendNextPrevS("你是谁？！", 2);
        } else if (status == 3) {
            cm.sendNextPrev("我…我这里，往上看。");
        } else if (status == 4) {
            cm.updateInfoQuest(23007, "vel00=1");
            cm.showWZEffect("Effect/Direction4.img/Resistance/ClickVel");
            cm.dispose();
        }
    } else if (cm.getInfoQuest(23007).indexOf("vel00=1") != -1 && cm.getInfoQuest(23007).indexOf("vel01=1") == -1) {
        if (status == 0) {
            cm.sendNext("我是…　#r傑利麥勒博士#k的实验者。我叫作#b斐勒#k… 虽然不知道你们怎么跑进來的，快點出去！要是被博士发现的话，就完蛋了！");
        } else if (status == 1) {
            cm.sendNextPrevS("实验者？傑利麥勒？到底在说什么啊？这里到底是什么地方？你为什么要进去里面啊？", 2);
        } else if (status == 2) {
            cm.sendNextPrev("你不知道傑利麥勒？ 傑利麥勒博士… 黑色翅膀的瘋狂科學家！这里是傑利麥勒的研究室，傑利麥勒在这里盡心人體实验…");
        } else if (status == 3) {
            cm.sendNextPrevS("人體…实验？", 2);
        } else if (status == 4) {
            cm.sendNextPrev("对，人體实验，你如果被抓到，说不定也会变成实验品！快逃跑！");
        } else if (status == 5) {
            cm.sendNextPrevS("什么？逃、逃跑…？但是你…！", 2);
        } else if (status == 6) {
            cm.sendNextPrev("…噓！小声一點！傑利麥勒博士來了。");
        } else if (status == 7) {
            cm.updateInfoQuest(23007, "vel00=2");
            cm.dispose();
            cm.warp(931000011, 0);
        }
    } else if (cm.getInfoQuest(23007).indexOf("vel01=1") != -1) {
        if (status == 0) {
            cm.sendNext("好險…傑利麥勒好像有事出去了…快，就趁现在，你快點走吧。");
        } else if (status == 1) {
            cm.sendNextPrevS("我一个人逃走吗？那你呢？", 2);
        } else if (status == 2) {
            cm.sendNext("我沒有辦法逃走。傑利麥勒博士記得自己实验过的所有東西，只要少一个，馬上就会发现的…所以你快走吧。");
        } else if (status == 3) {
            cm.sendNextPrevS("不可以！你也跟我一起走！", 2);
        } else if (status == 4) {
            cm.sendNext("就说不可能了，更何況我…被关在这里面。想要逃也逃不了…謝謝你为我操心。好久沒有人这么关心我了。快，快去吧。");
        } else if (status == 5) {
            cm.sendYesNo("#b（斐勒把眼睛閉了起來，就像放棄了一切，該怎么辦？去关斐勒的实验室看看！）#k");
        } else if (status == 6) {
            cm.gainExp(60);
            cm.dispose();
            cm.warp(931000013, 0);
        }
    }
}