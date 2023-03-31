load('nashorn:mozilla_compat.js');
importPackage(Packages.server);

var status = -1;
var sel;
var mod;
var itemidq = Array(
        Array(20, 100),
        Array(40, 150),
        Array(60, 200),
        Array(80, 250),
        Array(100, 300)
        );
function start() {
    cm.sendSimple("我是VIP奖励領取员 \r\n\r\n" +
            "#b#L0# VIP1 每日可領20紅利 100GASH#l#k\r\n\r\n" +
            "#b#L1# VIP2 每日可領40紅利 150GASH#l#k\r\n\r\n" +
            "#b#L2# VIP3 每日可領60紅利 200GASH#l#k\r\n\r\n" +
            "#b#L3# VIP4 每日可領80紅利 250GASH#l#k\r\n\r\n" +
            "#b#L4# VIP5 每日可領100紅利 300GASH#l#k\r\n\r\n" +
            "");
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
        var vip = cm.getPlayer().getVip();
        if (vip < 1) {
            cm.sendOk("你不是vip");
            cm.dispose();
            return;
        }
        if (sel > 4) {
            cm.sendOk("发生错誤" + sel);
            cm.dispose();
            return;
        }
        if (cm.getPlayer().getAcLogD("vip奖励") >= 1) {
            cm.sendOk("你今天已经領取过了");
            cm.dispose();
            return;
        }
        cm.getPlayer().setAcLogS("vip奖励");
        cm.getPlayer().modifyCSPoints(3, itemidq[vip - 1][0], true);
        cm.getPlayer().modifyCSPoints(1, itemidq[vip - 1][1], true);
        cm.sendOk("你是vip" + vip + "領取紅利" + itemidq[vip - 1][0] + "Gash" + itemidq[vip - 1][1] + "成功。");
        cm.dispose();
        return;
    }
}
