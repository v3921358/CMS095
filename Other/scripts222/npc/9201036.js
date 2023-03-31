/*
 名字: 		安琪莉可
 地图: 		婚礼村
 描述: 		結婚礼物管理人
 */

var status = -1;

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        cm.dispose();
        return;
    }
    if (status == 0) {
        if (cm.getPlayer().getMarriageId() <= 0) {
            cm.sendOk("你好像还沒結婚呢，婚都沒結就想要結婚戒指？你还是先找个心爱的人，結完婚再來吧~");
            cm.dispose();
        } else {
            cm.sendSimple("需要我帮忙吗？\r\n#b#L0##b我想查看結婚礼物。#l#k");
        }
    } else if (status == 1) {
        cm.sendNext("您的結婚礼物好像剛剛已经被拿走了！？");
        cm.dispose();
    }
}