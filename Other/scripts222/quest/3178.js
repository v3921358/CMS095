var status = -1;

function start(e, d, c) {
    (e == 1) ? status++ : status--;
    var b = -1;
    if (status <= b++) {
        qm.dispose()
    } else {
        if (status == b++) {
            qm.sendNext("你找到了解过去发生在这座城里的事件的人了吗？嗯？#p2161002#？他原本是国王的近卫骑士团长。他还留在这里吗……是吗，#p2161002#是怎么说的呢？他知道雷昂为什么会变成这个样子吗？")
        } else {
            if (status == b++) {
                qm.sendNextS("#b(转述从#p2161002#那里听到的话。)#k",1)
            } else {
                if (status == b++) {
                    qm.sendNext("没错……是的，是的。在黑色的乌云遮住天空的那一天，原本看上去很普通的士兵们突然变了，开始攻击我们。城墙倒塌，房屋全都着了火……我，我被塔里的烟雾……原来是这样啊。")
                } else {
                    if (status == b++) {
                        qm.sendNextS("#b(从#p2161001#的反应来看，#p2161002#的话好像是事实。)#k",1)
                    } else {
                        if (status == b++) {
                            qm.sendNext("雷昂是因为这个才会变成这样的啊。我光在这里抱怨雷昂，没想到这段时间他一直生活在悲伤和愤怒之中……我不能再让他这样下去了。")
                        } else {
                            if (status == b++) {
                                qm.sendNextS("#b你有什么办法吗？",1)
                            } else {
                                if (status == b++) {
                                    qm.sendNext("城里有鲁丹都不知通的国王和王妃专用的通道。本来是不应该告诉别人的……但是这次就破例一次。请你和我一起到接见室去见见他。")
                                } else {
                                    if (status == b++) {
                                        qm.sendNext("我要亲自去见见他。因为那些拥有黑暗气息的怪物，我一直不能到接见室去……不过如果你能帮我，我就应该可以过去。请一定要让我到接见室去！拜托你了！")
                                    } else {
                                        if (status == b++) {
                                            qm.forceStartQuest();
											qm.warp(921140000,0);
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
function end(mode, type, selection) {
	// qm.forceCompleteQuest();
	// qm.dispose();
}
