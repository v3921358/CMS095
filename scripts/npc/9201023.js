/*
 NPC Name: 		Hera
 Map(s): 		Towns
 Description: 		Wedding Village Entrance
 */

var status = -1;

function start() {
    cm.sendSimple("啊~今天真是个好日子！这世界太美好了~！你不觉得这世界充满了爱吗？满溢婚礼村的爱意都流淌到这里來了~！ \n\r #b#L0# 我想要去結婚小鎮.#l");
}

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        cm.sendOk("你居然要放棄这么好的機会？那里真的很美~。你不会是还沒遇到心爱的人吧？沒错，如果你有心爱的人，怎么会对这么浪漫的消息听而不聞呢！！");
        cm.dispose();
        return;
    }
    if (status == 0) {
        cm.sendNext("哦！多么美好的一天！这个世界是多么的美好〜！这个世界似乎是充满爱的，不是吗？我可以從这里感受到爱的精神填補了婚礼!");
    } else if (status == 1) {
        cm.sendYesNo("你曾经去过的婚礼村莊？这是一个了不起的地方，爱情是无極限的。恩爱夫妻可以結婚还有，如何浪漫它是什么？如果你想在那里，我会告訴你的方式.");
    } else if (status == 2) {
        cm.sendNext("你做了一个正确的决定！你可以感受到爱的精神在婚礼村发揮到淋漓盡致。当你想回來，你的目的地将在这里，所以不要擔心.");
    } else if (status == 3) {
        cm.saveLocation("AMORIA");
        cm.warp(680000000, 0);
        cm.dispose();
    }
}