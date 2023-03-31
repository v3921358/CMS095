var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        if (status == 14) {
            qm.sendNext("唉，你在开玩笑吧？告訴我你的手指滑倒了！勇往直前，接受挑战。");
            qm.dispose();
            return;
        }
        status--;
    }
    if (status == 0) {
        qm.sendNext("我早就知道了！我知道我们是有联系的，主人！当你变得更强壮时，我也变得更强。当我变得更强壮时，你可以用我的力量！这是我们的協定。我知道我選了一个好主人！");
    } else if (status == 1) {
        qm.sendNextPrevS("#b我懂了。我们到底是怎麽結束这项協议的?#k", 2);
    } else if (status == 2) {
        qm.sendNextPrev("我不知道。我只是一个雞蛋。我真的記不起來了…虽然我隱約記得你，师父，在一片霧氣中向我走來。我記得你見到我时的驚訝。我向你呼喊着作为回報。");
    } else if (status == 3) {
        qm.sendNextPrevS("#b(等待！听起來就像你曾经做过的梦…你们兩个在梦中相遇了吗？你在梦中看到的巨龙有可能是米爾吗？#k", 2);
    } else if (status == 4) {
        qm.sendNextPrev("主人，你和我在精神上是一體的。我一看見你就知道了。这就是为什麽我想和你达成協议的原因。沒有其他人。当然，你必須支付我設定的價格。");
    } else if (status == 5) {
        qm.sendNextPrevS("#b我付出了代價？ #k", 2);
    } else if (status == 6) {
        qm.sendNextPrev("你不記得了吗？当你认出我並撫摸我的时候？这是我設定的一个條件。当你觸摸我的蛋的时候，你和我在精神上成为一體。");
    } else if (status == 7) {
        qm.sendNextPrevS("#本着…精神?", 2);
    } else if (status == 8) {
        qm.sendNextPrev("对！精神契約！你和我有不同的身體，但我们分享一种精神。这就是为什麽当我变得更强时，你变得更强，反之亦然！棒極了，对吧？至少，我想是这樣。");
    } else if (status == 9) {
        qm.sendNextPrevS("#b我不知道你在说什麽，但听起來挺了不起的。#k", 2);
    } else if (status == 10) {
        qm.sendNextPrev("当然，这是一个大问題，愚蠢的主人！你再也不用擔心怪物了。你现在要我保护你！去试一试我。事实上，我们现在就走吧！");
    } else if (status == 11) {
        qm.sendNextPrevS("#b但这裏很平靜。周圍沒有危險的怪物。#k", 2);
    } else if (status == 12) {
        qm.sendNextPrev("什麽？这沒意思！你不喜欢冒險吗，主人？为你的人民战斗怪物，战勝邪惡，拯救无辜者，以及所有这些？你不喜欢那种事吗？");
    } else if (status == 13) {
        qm.sendNextPrevS("#b这不是我五年計劃的一部分。我只是开玩笑，但说真的，我是一个農民的兒子…#k", 2);
    } else if (status == 14) {
        qm.askAcceptDecline("嗯，让我告訴你吧。龙主人不可能过上平靜的生活。我会有很多機会來證明我的技能。相信我，我们的人生将是一次偉大的冒險。答應我你会和我在一起，好吗?");
    } else if (status == 15) {
        qm.forceStartQuest();
        qm.sendNextS("呵呵，好吧，大师。让我们开始吧!", 1);
    } else if (status == 16) {
        qm.sendNextPrevS("#b(你有點迷惑，但是你现在正在和米爾龙一起旅行。也許你会一起冒險，就像他说的那樣。)#k", 3);
    } else if (status == 17) {
        qm.sendPrevS("#b(你还有一个差事要辦。你爸爸需要和你談談，所以现在去看看他.)#k", 2);
        qm.dispose();
    }
}

function end(mode, type, selection) {
}