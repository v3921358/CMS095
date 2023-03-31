var status = -1;
var selectionLog = [];
function start() {
    action(1, 0, 0);
}
function action(d, c, b) {
    if (status == 0 && d == 0) {
        cm.dispose();
        return;
    }
    status++;
    selectionLog[status] = b;
    var a = -1;
    if (status <= a++) {
        cm.dispose();
    } else {
        if (status === a++) {
            //cm.npc_ChangeController(2159006, 'oid=248464', 903, -182, 12, 883, 923, 1, false, 0, false);
            cm.sendNext('呼……格里梅尔好像去做别的事情了……好了，趁这个时候，你快逃走吧。');
        } else {
            if (status === a++) {
                cm.sendNext('我一个人逃走？那你呢……？');
            } else {
                if (status === a++) {
                    cm.sendNext('我不能逃走。格里梅尔博士记得自己所有的实验体……如果有一个不见了，他一定会发现的……你快逃吧。');
                } else {
                    if (status === a++) {
                        cm.sendNext('不行!我们一起逃吧!');
                    } else {
                        if (status === a++) {
                            cm.sendNext('那是不可能的。而且我……被关在这个里面。就算我想逃也不行啊……谢谢你这么关心我。好久……没有人这么关心我了。好了，你快走吧!');
                        } else {
                            if (status === a++) {
                                cm.sendYesNo('#b(贝比蒂好像放弃了一切，闭上了眼睛。该怎么办呢？看看能不能把贝比蒂的实验箱打碎吧!)#k');
                            } else {
                                if (status === a++) {
                                    cm.gainExp(60);
                                    cm.dispose();
                                    cm.warp(931000013, 0);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}