var status = -1;
var selectionLog = [];
function start(mode, type, selection) {
	qm.sendNext("非常感謝。");
	qm.forceCompleteQuest();
	qm.dispose();
}
function end(d, c, b) {
    if (status == 0 && d == 0) {
        qm.dispose();
        return;
    }
    d == 1 ? status++ : status--;
    selectionLog[status] = b;
    var a = -1;
    if (status <= a++) {
        qm.dispose();
    } else {
        if (status == a++) {
            if (qm.getMap().getNumMonsters() > 0) {
                qm.sendNext('请先把周围监视我的怪物消灭掉。在这之前，我什么都不会跟你说……');
                qm.dispose();
                return;
            }
            qm.forceCompleteQuest();
            qm.gainExp(11659200);
            qm.sendNext('谢谢你来救我。但是我想继续留在这里。如果他们发现我不见了，可能会招来更严重的灾难。留在这里，说不定还能做点什么。');
        } else {
            if (status === a++) {
                qm.sendNext('请你帮我转告阿勒斯。\r\n');
            } else {
                if (status === a++) {
                    qm.sendNext('还有……请你阻止她。我们无法让希纳斯恢复原状。这也是没有办法的办法。');
                } else {
                    if (status === a++) {
                        qm.dispose();
                    }
                }
            }
        }
    }
}