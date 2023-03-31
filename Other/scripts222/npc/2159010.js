var status = -1;
function action(mode, type, selection) {
    if (mode == 1)
        status++;
    else
        status--;
    if (status == 0) {
        cm.sendNextS("呼…终于擺脫掉了。虽然不觉得会輸給須勒这个家伙，但卻沒有信心能保护你们。到底为什么会在那里？太危險了。村莊的老人沒有跟你们说不要到矿山这边吗？", 8);
    } else if (status == 1) {
        cm.sendNextPrevS("对、对不起。#h0#沒有错，反而还救了我。", 4, 2159007);
    } else if (status == 2) {
        cm.sendNextPrevS("嗯？这樣看來，你…不像是村莊的人。这奇怪的衣服到底是什么？你該不会是被黑色翅膀抓走吧？", 8);
    } else if (status == 3) {
        cm.sendNextPrevS("#b（斐勒简单地说明剛才发生的事情。）#k", 4, 2159007);
    } else if (status == 4) {
        cm.sendNextPrevS("…呼…这樣啊…虽然猜测黑色翅膀可能在进行危險的計劃，沒想到是真的…真是可怕，快去通知大家，要想出对策才行。", 8);
    } else if (status == 5) {
        cm.sendNextPrevS("那个…请问你是谁呢？为什么会突然在那里出现？还有，为什么会救我们呢？", 2);
    } else if (status == 6) {
        cm.sendNextPrevS("…这个…你也都长大了，也遇到这樣的事情，相瞞也瞞不了你…好吧，就告訴你。你也知道我们的村莊埃德爾斯坦被黑色翅膀統治的事吧？", 8);
    } else if (status == 7) {
        cm.sendNextPrevS("被搶走的矿山、被控制的议会、監視着的存在……我们村莊的人像奴隸一樣乖乖的听從他们的命令。但是黑色翅膀再厲害，也沒有辦法統治我们的心。", 8);
    } else if (status == 8) {
        cm.sendNextPrevS("我是末日反抗軍，和队友一起对抗黑色翅膀的埃德爾斯坦末日反抗軍一员。不能告訴你名字，但可以告訴你我的代号叫 J。现在了解吧？", 8);
    } else if (status == 9) {
        cm.sendNextPrevS("懂了的话，就快回村莊吧，太危險了，不要再跑來这里。曾是实验者的这孩子，让他在这里有可能再被抓回去，我把他带回我队友那里。在这里看見我的事要保密，不可以说出去。", 8);
    } else if (status == 10) {
        cm.sendNextPrevS("我可以再问一个问題吗？我也可以參加末日反抗軍队吗？", 2);
    } else if (status == 11) {
        cm.sendNextPrevS("呵…你想也对抗黑色翅膀啊？只要有心，也不是不能加入末日反抗軍。但不是现在，等級十以上，末日反抗軍会先和你連絡。如果到时还想成为队友的话会有機会再見面的，那就先这樣了。", 8);
    } else if (status == 12) {
        cm.forceCompleteQuest(23007);
        cm.gainItem(2000000, 3);
        cm.gainItem(2000003, 3);
        cm.gainExp(90);
        cm.dispose();
        cm.warp(310000000, 8);
    }
}