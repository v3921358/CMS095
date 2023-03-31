var status = -1;
var selectionLog = [];

function start(d, c, b) {
    if (status == 0 && d == 0) {
        qm.dispose();
        return
    }(d == 1) ? status++ : status--;
    selectionLog[status] = b;
    var a = -1;
    if (status <= a++) {
        qm.dispose()
    } else {
        if (status == a++) {
            qm.sendNext("哇！这只喂过了, 这只也喂过了, 这只喂过了吗？不对！第3只没喂过！乖, 多吃点！唉～一个个都张着嘴巴, 我都分不清那只吃饱了, 那只没吃饱……啊, 你来了？")
        } else {
            if (status === a++) {
                qm.sendNext("唉……忙得我都糊涂了. 前不久研究员们说提莫受了帕普拉图斯的影响, 发生了变异, 只要好好喂养就能变成听话乖巧的小鸟. 所以我打算在扫荡怪兽之余, 养养小鸟. ")
            } else {
                if (status === a++) {
                    qm.sendYesNo("不过没想到……我一口气养了10只, 简直要累死自己了……光是为它们找吃的就够不容易的了……虽然他们确实很可爱. 你要不也养一只？我分你一只养养？")
                } else {
                    if (status === a++) {
                        qm.sendNext("那就分你一只养养吧, 只要喂它们吃寄生在时间塔的怪物身上的发条虫就行. ")
                    } else {
                        if (status === a++) {
                            qm.forceCompleteQuest(3250);
                            qm.forceStartQuest(7067, "0");
                            qm.sendNext("提莫是来自其它世界的生物, 养大了就应该送它们回到自己的世界去. 所以等你把提莫养大了就带回来给我, 拜托你了. ");
                            qm.dispose()
                        }
                    }
                }
            }
        }
    }
}