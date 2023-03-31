var status = -1;

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        if (status == 0) {
            cm.dispose();
        }
        status--;
    }
    if (status == 0) {
        cm.sendNext("想了解專業技術的話，我來簡單地說明一下。在這個村子中，一共有#b采藥、采礦、裝備制作、飾品制作、煉金術#k5個匠人。為了提高專業技術的效果，我們匠人協會規定每個人可以學習2種專業技術。根據這個規定，你可以選擇學習#r2種專業技術#k。");
    } else if (status == 1) {
        cm.sendPrev("#b - 采藥 + 煉金術 - 采礦 + 裝備制作 - 采礦 + 飾品制作#k\r\n\r\n可以在這3種搭配中選擇，請選擇並學習自己喜歡的技術。");
        cm.dispose();
    }
}