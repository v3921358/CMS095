var status = -1;
function start() {
    if (cm.getMapId() == 931000020) {
        action(1, 0, 0);
    } else {
        cm.dispose();
    }
}

function action(mode, type, selection) {
    if (mode == 1)
        status++;
    else
        status--;
    if (status == 0) {
        cm.sendNext("哼。好小子，膽敢給我逃跑？");
    } else if (status == 1) {
        cm.sendNextPrevS("啊！被发现了！", 2);
    } else if (status == 2) {
        cm.sendNextPrev("不要掙扎了快投降吧。实验者想要去哪里…咦？后面那个小子就算了，你不是实验者嘛？你是什么？村莊的人？");
    } else if (status == 3) {
        cm.sendNextPrevS("怎么樣！我是埃德爾斯坦的居民！", 2);
    } else if (status == 4) {
        cm.sendNextPrev("…小鬼頭们，说了几次叫你们不要靠近矿山，听不懂是吧？笨居民…沒辦法，不能让你回到村莊亂说有关实验室的事情。要把你抓起來。");
    } else if (status == 5) {
        cm.sendNextPrevS("什么？谁说要乖乖地給你抓？", 2);
    } else if (status == 6) {
        cm.sendNextPrev("不知好歹…看你可以囂张到什么时后？");
    } else if (status == 7) {
        cm.sendNextPrevS("#b（被須勒攻擊，體力減半了！該怎么辦？好像打不贏！）#k", 2);
    } else if (status == 8) {
        cm.sendNextPrev("现在沒有辦法耍嘴皮子了吧？我要建议傑利麥勒給你做更强的实验。呼呼…乖乖的投降吧！");
    } else if (status == 9) {
        cm.sendNextPrevS("停！", 4, 2159010);
    } else if (status == 10) {
        cm.dispose();
        cm.warp(931000021, 1);
    }
}