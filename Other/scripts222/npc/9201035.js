/*
	名字: 		傑伊克
	地图: 		婚礼村小鎮
	描述: 		結婚戒指交換
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
            cm.sendSimple("你好啊~ 我聞到了一股甜蜜蜜的新婚味道哦~ 哎喲，怎么还戴着訂婚戒指啊？結了婚就要換漂亮的結婚戒指才行嘛！你願意的话，我可以給你们換，怎么樣？\r\n\r\n#L0# 把訂婚戒指換成結婚戒指。#l");
        }
    } else if (status == 1) {
        cm.sendNext("結婚戒指也可以裝备的，一定要试试看哦~");
        cm.dispose();
    }
}