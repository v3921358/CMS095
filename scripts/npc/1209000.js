var status = -1;

function action(mode, type, selection) {
    if (cm.getQuestStatus(21002) == 0) {
        if (mode == 1) {
            status++;
        } else {
            status--;
        }

        if (status == 0) {
            cm.sendNext("您醒了， 狂狼勇士。 受傷的傷口还好吗？…什么？你说现在的情況吗？");
        } else if (status == 1) {
            cm.sendNextPrev("逃难的准备几乎都做好了。可以搭載的人全部都坐上方舟了。 逃生船飛行期间会由神獸守护，沒什么好擔心的。现在只要收拾好就会立刻出发前往維多利亞島。");
        } else if (status == 2) {
            cm.sendNextPrev("狂狼勇士的同伴吗…？他们…去找黑魔法师了。在我们逃难的期间会阻止黑魔法师…什么？連你也要去找黑魔法师？不行！你不是受了傷吗？跟我们一起逃亡吧！");
        } else {
            cm.forceStartQuest(21002, "1");
            // Ahh, Oh No. The kid is missing
            cm.showWZEffect("Effect/Direction1.img/aranTutorial/Trio");
            cm.dispose();
        }
    } else {
        if (mode == 1) {
            status++;
        } else {
            status--;
        }

        if (status == 0) {
            cm.sendSimple("情況很緊急。你想知道什么？ \r\n #b#L0#黑魔法师？#l \r\n #b#L1#逃难准备？#l \r\n #b#L2#同伴们？#l");
        } else if (status == 1) {
            switch (selection) {
                case 0:
                    cm.sendNext("听说黑魔法师就在不远处。因为成为黑魔法师部下的龙群阻擋，根本无法通过森林。所以我们才打造了逃生船。现在只能飛往維多利亞島逃难…");
                    break;
                case 1:
                    cm.sendNext("逃难准备几乎都做好了。可以搭載的人全部坐上方舟了。现在只剩下几个人搭乘后就可以出发前往維多利亞島。 神獸答應在逃生船飛行的期间阻擋空中的攻擊…沒有人留下來守护这里…");
                    break;
                case 2:
                    cm.sendOk("您的同伴…他们已经去找黑魔法师了。他们说要在我们逃难的期间阻止黑魔法师…还说因为您受傷了，所以不带您去。等孩子都救出來后，您也跟我们一起逃走吧！ 狂狼勇士！");
                    break;
            }
            cm.safeDispose();
        }
    }
}