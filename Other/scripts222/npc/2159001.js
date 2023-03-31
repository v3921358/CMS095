var status = -1;
function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        status--;
    }

    if (status == 0) {
        cm.sendNextS("遲到了啦，#h0#?快过來这里！", 8);
    } else if (status == 1) {
        cm.sendNextPrevS("为什么这么慢？以前不是就说好要來这里玩了吗！不会是害怕了吧？", 4, 2159002);
    } else if (status == 2) {
        cm.sendNextPrevS("我沒有害怕。", 2);
    } else if (status == 3) {
        cm.sendNextPrevS("真的吗？我好害怕哦…老人家们不是有警告说不要來#b雷朋矿山#k 这里玩。有 #r黑色翅膀的壞人们#k守在这里…", 4, 2159000);
    } else if (status == 4) {
        cm.sendNextPrevS("所以才故意避开沒有監視者的路，來到这里的啊。 不趁现在我们什么时候可以從 #b埃德爾斯坦#k 里跑出來玩？ 真是的，膽小鬼！！", 4, 2159002);
    } else if (status == 5) {
        cm.sendNextPrevS("但是… 被罵怎么辦？", 4, 2159000);
    } else if (status == 6) {
        cm.sendNextPrevS("都已经來到这里了，还可以怎么辦。反正都会被罵，我们玩玩再回去吧，我们來玩捉迷藏！", 8);
    } else if (status == 7) {
        cm.sendNextPrevS("咦？ 捉迷藏！", 2);
    } else if (status == 8) {
        cm.sendNextPrevS("真幼稚…", 4, 2159002);
    } else if (status == 9) {
        cm.sendNextPrevS("什么幼稚！在这里还可以玩什么？说來听听啊！还有你当鬼，#h0#! 因为你遲到啊。 哈，那么我们要躲了哦，數到十后开始找！", 8);
    } else if (status == 10) {
        cm.warp(931000001, 1);
        cm.dispose();
    }
}