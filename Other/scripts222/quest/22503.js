/*
 Description: 	Quest - A Bite of Pork
 */

var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        if (status == 2) {
            qm.sendNext("你怎麽能这樣餓死我呢？我只是个孩子。这是错誤的!");
            qm.dispose();
            return;
        }
        status--;
    }
    if (status == 0) {
        qm.sendNext("不，不，不，这不是我需要的。我需要更有營養的東西，主人!");
    } else if (status == 1) {
        qm.sendNextPrevS("#bHm.…所以你不是食草动物。你可能是食肉动物。毕竟你是一條龙。豬肉怎麽樣？#k", 2);
    } else if (status == 2) {
        qm.askAcceptDecline("豬肉是什麽？從來沒有听说过，但如果它好吃，我接受！給我吃點好吃的。除了植物什麽都沒有！");
    } else if (status == 3) {
        qm.forceStartQuest();
        qm.sendOkS("#b(试试給米爾一些豬肉。你必須在農场裏猎杀几頭豬。十應該是充足的…)#k", 2);
        qm.dispose();
    }
}

function end(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        status--;
    }
    if (status == 0) {
        qm.sendOk("哦，这就是你給我带來的食物吗？这就是你要吃的豬肉？让我试试。");
    } else if (status == 1) {
        qm.gainExp(1850);
        qm.gainItem(4032453, -10);
        qm.sendNext("(chomp，chomp喝……)");
        qm.forceCompleteQuest();
    } else if (status == 2) {
        qm.sendPrev("塔克…这味道不太壞，但我想我消化不了。这不是給我的…");
        qm.dispose();
    }
}