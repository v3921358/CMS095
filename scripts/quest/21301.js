var status = -1;

function start(mode, type, selection) {
}

function end(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        status--;
    }
    if (status == 0) {
        qm.askAcceptDecline("野烏鴉抓到了吗？呵呵呵...果然是我的主人！很好，那么将带來的 紅珠玉交出來！我会重新放在本體上...咦...？为什么不回答？該不会...忘記带回來了吧？");
    } else if (status == 1) {
        qm.sendNext("什么？你真的沒拿回 紅珠玉？为什么？該不会是真的忘了吧？啊啊！怎么会这樣...就算被黑魔法师詛咒，经过了这么久冰雪封印都解除了，健忘症倒是还沒解除啊...");
    } else if (status == 2) {
        qm.sendNextPrev("不行。真的太不像话了。这个时候我更應該代替主人打起精神...呼呼...呼呼......");
    } else if (status == 3) {
        qm.sendNextPrev("再去看看，反正小偷已经逃走了。那么就重新制作 紅珠玉吧！之前曾经做过一次，你知道材料吧？好吧！那么快去蒐集材料吧...");
    } else if (status == 4) {
        qm.sendNextPrev("   #i4001173#");
    } else if (status == 5) {
        qm.sendNextPrev("材料也沒有，而且还不知道怎么做.....沒有梦也沒有希望。啊啊啊！");
    } else if (status == 6) {
        qm.sendNextPrevS("#b(瑪哈开始大发雷霆。先逃离这里再说。莉琳可能可以帮我。)");
        qm.forceCompleteQuest();
        qm.dispose();
    }
}