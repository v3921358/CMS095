/*
	Robert Holly - Ludibrium: Ludibrium (220000000)
*/

var status = 0;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (status == 0 && mode == 0) {
        cm.sendNext("是吗……看來我的預感是错的，你好像沒什么朋友啊？哈哈哈~玩笑，玩笑~如果你改变了主意，可以再來找我。等朋友多一點的时候……呵呵……");
        cm.dispose();
        return;
    } else if (status >= 1 && mode == 0) {
        cm.sendNext("是吗……看來我的預感是错的，你好像沒什么朋友啊？或者身上沒有25萬金币？如果你改变了主意，可以再來找我。等你有了錢的时候……呵呵……");
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    } else {
        status--;
    }
    if (status == 0) {
        cm.sendYesNo("希望今天客人能多一點……啊！等一下！你想增加好友目錄吗？我一看你，就觉得你有很多朋友。怎么樣……只要花一點錢，我就可以为你增加好友目錄。但是不会應用于相同帳号的其他角色，所以一定要慎重。你想增加吗？");
    } else if (status == 1) {
        cm.sendYesNo("好的！明智的决定。價格不貴。因为#r特殊折扣#k活动开始了，#b好友目錄添加5名一共是25萬金币#k。当然，絕不零售。只要购买一次，目錄就可以永久增加。对好友目錄不足的人來说，这个买賣應該不壞。怎么樣？你願意支付25萬金币吗？");
    } else if (status == 2) {
        var capacity = cm.getBuddyCapacity();
        if (capacity >= 50 || cm.getMeso() < 250000) {
            cm.sendNext("你……确定自己有#b25萬金币#k吗？如果有的话，请你确认一下好友目錄是否已经增加到最大了。即使錢再多，好友目錄的人數也无法增加到#b50个以上#k。");
            cm.dispose();
        } else {
            var newcapacity = capacity + 5;
            cm.gainMeso( - 250000);
            cm.updateBuddyCapacity(newcapacity);
            cm.sendOk("好的！你的好友目錄已经增加了5个。你可以确认一下。如果好友目錄还是不够的话，可以隨时來找我。我可以隨时帮你增加，不管多少次都行。当然不是免費的……那么再見~");
            cm.dispose();
        }
    }
}