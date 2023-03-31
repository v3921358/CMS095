var status = -1;
var selectionLog = [];
function start(d, c, b) {
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
            qm.sendNext('没想到你会这么的强…以你的水平也许可以成为沙子图团的团员也说不定。对沙子图团员来说，最重要的就是力量的强大，而你…看来已经具备了足够的实力！但我还是要再进行一次测试…如何？可以接受吗？');
        } else {
            if (status === a++) {
                qm.sendYesNo('若想要实际测试你的力量，应该需要亲自去体验吧？我想和你进行一场对战！别担心，我也不想伤害你…就用我的分身来对付你好了！可以马上进行对战吗？');
            } else {
                if (status === a++) {
                    qm.sendYesNo('好！充满自信是吗？');
                } else {
                    if (status === a++) {
                        qm.forceStartQuest(3933);
                        qm.dispose();
                        qm.warp(926000000,0);
                        qm.spawnMob(9100013, 1, 80, 275);
                    }
                }
            }
        }
    }
}