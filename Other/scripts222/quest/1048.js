var status = -1;

function start(mode, type, selection) {
    if (mode == -1) {
        qm.dispose();
    } else {
        if (mode == 1) {
            status++;
        } else {
            status--;
        }
        if (status == 0) {
            qm.sendOk("是位新的旅行者吧？还很陌生吧？我是冒險島运營员，好好听着，人物的各项屬性都关係着以后的冒險经歷生存，所以正确的選擇职业，是很重要的。如果你还不知道應該選擇什么职业。你可以到明珠港找#b坤#k談談，也許他会告訴你一些你想要知道的。");
            qm.forceCompleteQuest();
            qm.dispose();
        }
    }
}