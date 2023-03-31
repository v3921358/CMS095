
var status = -1;
var sel;
var mod;
function start() {
    cm.sendSimple("#e#r嗨，我是#z4032056#兌換员有什么可以帮忙的？ \r\n\r\n \n\ " +
            "#b#L38##i4032056#x6000兌換#i5062001#x1#l \r\n\r\n " +
            "#b#L39##i4032056#x2000兌換#i2290285#x1#l \r\n\r\n " +
            "#b#L40##i4032056#x8000兌換#i2450000#x1#l \r\n\r\n " +
            "#k");
}

function action(mode, type, selection) {
    if (mode == 0) {
        cm.dispose();
        return;
    } else {
        status++;
    }
    if (status == 0) {

        sel = selection;
        if (sel < 38 || sel > 41) {
            cm.sendOk("发生错誤，请联系管理员。");
            cm.dispose();
            return;
        }
        if (cm.getPlayer().getLevel() < 70) {
            cm.sendOk("你的等級不足70等，无法兌換。");
            cm.dispose();
            return;
        }
        if (!cm.canHold()) {
            cm.sendOk("请确认背包是否已经满了。");
            cm.dispose();
            return;
        }
        if (!cm.haveItem(4032056, sel == 38 ? 6000 : sel == 39 ? 2000: sel == 40 ? 8000: 3000)) {
            cm.sendOk("你的物品數量不足。");
            cm.dispose();
            return;
        }
        var itemid = sel == 38 ? 5062001 : sel == 39 ? 2290285 : sel == 40 ? 2450000 : sel == 41 ? 2109018 : 0;

        cm.gainItem(4032056, sel == 38 ? -6000 : sel == 39 ? -2000: sel == 40 ? -8000: -3000);


        cm.gainItem(itemid, 1);

        cm.sendOk("領取成功。");
        cm.dispose();
        return;

    }
}
