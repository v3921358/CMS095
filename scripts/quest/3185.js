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
            qm.sendNext('谢谢你接受我的请求。下次希望你能继续帮我。');
        } else {
            if (status === a++) {
                qm.sendNext('你的身体那么不好吗？');
            } else {
                if (status === a++) {
                    qm.sendNext('咦，东西好像不对啊……嗯……' );
                } else {
                    if (status === a++) {
                        qm.sendNext('有什么事吗？现在好像已经不那么疼了……' );
                    } else {
                        if (status === a++) {
                            qm.sendNext('好吧，我就有话直说了。事实上，身为猎人，这么说让我觉得很羞耻……' );
                        } else {
                            if (status === a++) {
                                qm.sendNext('几天前。我像平常一样，在寒冰平原上打猎野狼。但是突然刮起一阵风，#b#o6090001##k出现在了我的面前。' );
                            } else {
                                if (status === a++) {
                                    qm.sendNext('雪山魔女？那是什么啊？' );
                                } else {
                                    if (status === a++) {
                                        qm.sendNext('雪山魔女是冰峰雪域传说中的怪物，据说她的身体是由冰雪构成的。我还以为只是传说而已……没想到真的会在这里见到。刚开始见到的时候，我凭着猎人的勇气把她击败了。但是在后来去打猎的时候，她又完好无损地出现在了我的面前。我当时非常害怕，所以就借口身体不好，不愿意出去打猎了。' );
                                    } else {
                                        if (status === a++) {
                                            qm.askAcceptDecline('但是我不能一直这样下去。我不能一直拜托别人帮忙……我想阿尔卡斯特也许有什么解决办法。你能帮我去跟#b阿尔卡斯特#k说说吗？我自己不太方便过去……周围的人都看着呢……我想这个要求应该不难吧。');
                                        } else {
                                            if (status === a++) {
                                                qm.sendNext('阿尔卡斯特就在#b冰峰雪域#k这地方的某处。拜托了，请你不要向其他人宣扬此事。猎人居然会因为害怕怪物而不敢出去狩猎……真是丢死人了。');
                                            } else {
                                                if (status === a++) {
                                                    qm.forceStartQuest(3185);
                                                    qm.dispose();
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}