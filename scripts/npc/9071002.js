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
        cm.sendSimple("欢迎來到怪物公園，想要使用怪物公園，当然應該來找我，一天可购买3张票。\r\n#b#L0#交換#t4001513##l\r\n#L1#交換#t4001515##l\r\n#L2#交換#t4001521##l\r\n#L3#购买入场券#l#k");
    } else if (status == 1) {
        if (!cm.canHoldByType(4, 1)) {
            cm.sendOk("请确认背包是否已经满了。");
            cm.dispose();
            return;
        }
        if (selection == 0) {
            status = -1;
            if (cm.haveItem(4001513) >= 10) {
                cm.gainItem(4001513, -10);
                cm.gainItem(4001514, 1);
                cm.sendOk("恭喜你交換成功.");
                cm.dispose();
            } else {
                cm.sendNext("怎麽回事？沒有啊。要想交換入场券，需要#b10个入场券碎片#k。");
            }
        } else if (selection == 1) {
            status = -1;
            if (cm.haveItem(4001515) >= 10) {
                cm.gainItem(4001515, -10);
                cm.gainItem(4001514, 1);
                cm.sendOk("恭喜你交換成功.");
                cm.dispose();
            } else {
                cm.sendNext("怎麽回事？沒有啊。要想交換入场券，需要#b10个入场券碎片#k。");
            }
        } else if (selection == 2) {
            status = -1;
            if (cm.haveItem(4001521) >= 10) {
                cm.gainItem(4001521, -10);
                cm.gainItem(4001522, 1);
                cm.sendOk("恭喜你交換成功.");
                cm.dispose();
            } else {
                cm.sendNext("怎麽回事？沒有啊。要想交換入场券，需要#b10个入场券碎片#k。");
            }
        } else if (selection == 3) {
            cm.sendSimple("嗯～本來不能这樣的，因为我最近心情很好，所以才会破例賣給你。#r不管是哪种入场券，每人一天只能购买3张#k。对了，这件事一定要对休彼德蔓保密！\r\n#b#L0##t4001514#5萬金币#l\r\n#L1##t4001516#10萬金币#l\r\n#L2##t4001522#20萬金币#l\r\n#L3##t4001514#100Gash#l\r\n#L4##t4001516#200Gash#l\r\n#L5##t4001522#300Gash#l #k");
        }
    } else if (status == 2) {
        if (!cm.canHoldByType(4, 1)) {
            cm.sendOk("请确认背包是否已经满了。");
            cm.dispose();
            return;
        }
        if (selection == 0) {
            if (cm.getPlayer().getBossLogD("斑點紋卷") >= 3) {
                cm.sendOk("你今天已经购买了3次。");
                cm.dispose();
                return;
            }

            if (cm.getMeso() >= 50000) {
                cm.getPlayer().setBossLog("斑點紋卷");
                cm.gainItem(4001514, 1);
                cm.gainMeso(-50000);
                cm.sendOk("恭喜你购买成功");
                cm.dispose();
            } else {
                cm.sendOk("你是不是沒錢，或者沒地方放入场券了啊？你再确认一下。");
                cm.dispose();
            }
        } else if (selection == 1) {
            if (cm.getPlayer().getBossLogD("豹紋卷") >= 3) {
                cm.sendOk("你今天已经购买了3次。");
                cm.dispose();
                return;
            }
            if (cm.getMeso() >= 100000) {
                cm.getPlayer().setBossLog("豹紋卷");
                cm.gainItem(4001516, 1);
                cm.gainMeso(-100000);
                cm.sendOk("恭喜你购买成功");
                cm.dispose();
            } else {
                cm.sendOk("你是不是沒錢，或者沒地方放入场券了啊？你再确认一下。");
                cm.dispose();
            }
        } else if (selection == 2) {
            if (cm.getPlayer().getBossLogD("老虎紋卷") >= 3) {
                cm.sendOk("你今天已经购买了3次。");
                cm.dispose();
                return;
            }
            if (cm.getMeso() >= 200000) {
                cm.getPlayer().setBossLog("老虎紋卷");
                cm.gainItem(4001522, 1);
                cm.gainMeso(-200000);
                cm.sendOk("恭喜你购买成功");
                cm.dispose();
            } else {
                cm.sendOk("你是不是沒錢，或者沒地方放入场券了啊？你再确认一下。");
                cm.dispose();
            }
            cm.dispose();
        } else if (selection == 3) {
            if (cm.getPlayer().getCSPoints(1) >= 100) {
                cm.gainItem(4001514, 1);
                cm.getPlayer().modifyCSPoints(1, -100, true);
                cm.sendOk("恭喜你购买成功");
                cm.dispose();
            } else {
                cm.sendOk("你是不是沒錢，或者沒地方放入场券了啊？你再确认一下。");
                cm.dispose();
            }
        } else if (selection == 4) {

            if (cm.getPlayer().getCSPoints(1) >= 200) {
                cm.gainItem(4001516, 1);
                cm.getPlayer().modifyCSPoints(1, -200, true);
                cm.sendOk("恭喜你购买成功");
                cm.dispose();
            } else {
                cm.sendOk("你是不是沒錢，或者沒地方放入场券了啊？你再确认一下。");
                cm.dispose();
            }
        } else if (selection == 5) {

            if (cm.getPlayer().getCSPoints(1) >= 300) {
                cm.gainItem(4001522, 1);
                cm.getPlayer().modifyCSPoints(1, -300, true);
                cm.sendOk("恭喜你购买成功");
                cm.dispose();
            } else {
                cm.sendOk("你是不是沒錢，或者沒地方放入场券了啊？你再确认一下。");
                cm.dispose();
            }
            cm.dispose();
        }
    }
}