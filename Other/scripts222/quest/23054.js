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
            qm.sendNext('哎呀……这不是我们的英雄#h0#吗！哈哈哈，在村里见到你，你看上去帅多了。虽然我们村被黑色之翼占领了，但这里还是最美的。不是吗？');
        } else {
            if (status === a++) {
                qm.sendNext('现在身体好一点了吗？');
            } else {
                if (status === a++) {
                    qm.sendNext('虽然还有点疼，不过#p2151003#的手艺是最好的。我已经几乎恢复到过去的状态了。不过有一个问题。');
                } else {
                    if (status === a++) {
                        qm.sendNext('又有什么问题？难道黑色之翼又在策划什么阴谋？');
                    } else {
                        if (status === a++) {
                            qm.sendYesNo('这次的问题不是别的……就是你！你太强了所以我变得不用出面了！不过我可是反抗者最棒的机械师，现在都不能教你了！所以我要给你更难的课题！');
                        } else {
                            if (status === a++) {
                                qm.changeJob(3512);
                                qm.sendNext('我已经让你转职了。同时把我知道的，但又无法自由操控的最高的技能传授给了你。虽然连我都无法做到，但我想你一定可以做到。这是理所当然的事情，不是吗？因为你是我们反抗者中最强的人！');
                            } else {
                                if (status === a++) {
                                    qm.gainItem(1142245, 1);
                                    qm.forceCompleteQuest();
                                    qm.forceCompleteQuest(29944);
                                    qm.sendNext('这是我的最后一项课程……才怪。我可是很有能力的。虽然你比我强，但你还有能跟我学习的吧？所以……下个课程见吧。虽然不知道什么时候能开始。');
                                } else {
                                    if (status === a++) {
                                        qm.sendNext('在那之前，希望你能以反抗者的身份，继续努力。');
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