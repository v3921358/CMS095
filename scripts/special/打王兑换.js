

var status = -1;
var sel;
var mod;
var itemid;
function start() {
    cm.sendSimple("#e#r嗨，我是打王次數奖励兌換员有什么可以帮忙的？ \r\n\r\n#b#L0#扎昆奖励。#l"/*+" \r\n\r\n#b#L1#混沌殘暴炎魔奖励。#l"*/ + " \r\n\r\n#L2#黑龙奖励。#l"/*+" \r\n\r\n#L3#混沌暗黑龙王奖励。#l*/ + " \r\n\r\n#L4#熊奖励。#l \r\n\r\n#L8#狮子王奖励。#l"/*\r\n\r\n#L5#皮卡啾奖励。#l*/+ "\r\n\r\n#L6#班雷昂奖励。#l" /*\r\n\r\n#L7#西格諾斯女皇奖励。#l \r\n\r\n #k"*/);
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
        if (sel == 0) {
            cm.sendSimple("您当前攻打了" + cm.getPlayer().getBossLogS("普炎攻打次數") + "次殘暴炎魔 \r\n#b#L0#攻打1次奖励#l \r\n#b#L1#攻打5次奖励#l \r\n#b#L2#攻打10次奖励#l \r\n#b#L3#攻打20次奖励#l \r\n#b#L4#攻打30次奖励#l \r\n#b#L5#攻打50次奖励#l \r\n");
        } else if (sel == 1) {
            cm.sendSimple("您当前攻打了" + cm.getPlayer().getBossLogS("混炎攻打次數") + "次混沌殘暴炎魔 \r\n#b#L0#攻打1次奖励#l \r\n#b#L1#攻打5次奖励#l \r\n#b#L2#攻打10次奖励#l \r\n#b#L3#攻打20次奖励#l \r\n#b#L4#攻打30次奖励#l \r\n#b#L5#攻打50次奖励#l \r\n");
        } else if (sel == 2) {
            cm.sendSimple("您当前攻打了" + cm.getPlayer().getBossLogS("普龙攻打次數") + "次暗黑龙王 \r\n#b#L0#攻打1次奖励#l \r\n#b#L1#攻打5次奖励#l \r\n#b#L2#攻打10次奖励#l \r\n#b#L3#攻打20次奖励#l \r\n#b#L4#攻打30次奖励#l \r\n#b#L5#攻打50次奖励#l \r\n");
        } else if (sel == 3) {
            cm.sendSimple("您当前攻打了" + cm.getPlayer().getBossLogS("混龙攻打次數") + "次混沌暗黑龙王 \r\n#b#L0#攻打1次奖励#l \r\n#b#L1#攻打5次奖励#l \r\n#b#L2#攻打10次奖励#l \r\n#b#L3#攻打20次奖励#l \r\n#b#L4#攻打30次奖励#l \r\n#b#L5#攻打50次奖励#l \r\n");
        } else if (sel == 4) {
            cm.sendSimple("您当前攻打了" + cm.getPlayer().getBossLogS("泰勒熊攻打次數") + "次狂暴的泰勒熊 \r\n#b#L0#攻打1次奖励#l \r\n#b#L1#攻打5次奖励#l \r\n#b#L2#攻打10次奖励#l \r\n#b#L3#攻打15次奖励#l \r\n");
        } else if (sel == 8) {
            cm.sendSimple("您当前攻打了" + cm.getPlayer().getBossLogS("娃娃獅攻打次數") + "次狂暴的娃娃獅王 \r\n#b#L0#攻打1次奖励#l \r\n#b#L1#攻打5次奖励#l \r\n#b#L2#攻打10次奖励#l \r\n#b#L3#攻打15次奖励#l \r\n");
        } else if (sel == 5) {
            cm.sendSimple("您当前攻打了" + cm.getPlayer().getBossLogS("皮卡啾攻打次數") + "次皮卡啾 \r\n#b#L0#攻打1次奖励#l \r\n#b#L1#攻打5次奖励#l \r\n#b#L2#攻打10次奖励#l \r\n#b#L3#攻打20次奖励#l \r\n#b#L4#攻打30次奖励#l \r\n#b#L5#攻打50次奖励#l \r\n");
        } else if (sel == 6) {
            cm.sendSimple("您当前攻打了" + cm.getPlayer().getBossLogS("凡雷恩攻打次數") + "次凡雷恩 \r\n#b#L0#攻打1次奖励#l \r\n#b#L1#攻打5次奖励#l \r\n#b#L2#攻打10次奖励#l \r\n#b#L3#攻打20次奖励#l \r\n#b#L4#攻打30次奖励#l \r\n#b#L5#攻打50次奖励#l \r\n");
        } else if (sel == 7) {
            cm.sendSimple("您当前攻打了" + cm.getPlayer().getBossLogS("女皇攻打次數") + "次西格諾斯女皇 \r\n#b#L0#攻打1次奖励#l \r\n#b#L1#攻打5次奖励#l \r\n#b#L2#攻打10次奖励#l \r\n#b#L3#攻打20次奖励#l \r\n#b#L4#攻打30次奖励#l \r\n#b#L5#攻打50次奖励#l \r\n");
        }
    } else if (status == 1) {
        if (sel == 0) {
            if (selection == 0) {
                mod = 0;
                cm.sendYesNo("你确定要領取第1次打奖励吗？\r\n#b#z1002357#加下面一种。#l\r\n#b#z1302312##l \r\n#b#z1312182##l \r\n#b#z1322233##l \r\n#b#z1332257##l \r\n#b#z1342097##l \r\n#b#z1372049##l \r\n#b#z1372204##l \r\n#b#z1382242##l \r\n#b#z1402233##l \r\n#b#z1412161##l \r\n#b#z1472244##l \r\n#b#z1482199##l \r\n#b#z1492209##l \r\n#b#z1442251##l");
            } else if (selection == 1) {
                mod = 1;
                cm.sendYesNo("你确定要領取第5次打奖励吗？\r\n#b#z4031408##l\r\n#b#z3010127##l");
            } else if (selection == 2) {
                mod = 2;
                cm.sendYesNo("你确定要領取第10次打奖励吗？\r\n#b選一种。#l\r\n#b#z2290008##l \r\n#b#z2290022##l \r\n#b#z2290012##l \r\n#b#z2290029##l \r\n#b#z2290030##l \r\n#b#z2290032##l \r\n#b#z2290050##l \r\n#b#z2290060##l \r\n#b#z2290074##l \r\n#b#z2290084##l \r\n#b#z2290092##l \r\n#b#z2290097##l \r\n#b#z2290119##l \r\n#b#z2290126##l \r\n#b#z2290140##l \r\n#b#z2290230##l \r\n#b#z2290240##l \r\n#b#z2290279##l \r\n#b#z2290256##l");
            } else if (selection == 3) {
                mod = 3;
                cm.sendYesNo("你确定要領取第20次打奖励吗？\r\n#b#z1004119#x1#l\r\n#b#z2290285#x3#l\r\n#b1000枫葉點數#l");
            } else if (selection == 4) {
                mod = 4;
                cm.sendYesNo("你确定要領取第30次打奖励吗？\r\n#b#z5570000#x1#l\r\n#b#z2049300#x10#l\r\n#b1200枫葉點數#l");
            } else if (selection == 5) {
                mod = 5;
                cm.sendYesNo("你确定要領取第50次打奖励吗？\r\n#b#z5000479#x1 90天#l\r\n#b#z1142503#x1#l\r\n#b#z5062001#x10#l\r\n#b#z2430333#x1#l\r\n#b1200枫葉點數#l");
            }
        } else if (sel == 1) {
            if (selection == 0) {
                mod = 10;
                cm.sendYesNo("你确定要領取第1次打奖励吗？");
            } else if (selection == 1) {
                mod = 11;
                cm.sendYesNo("你确定要領取第5次打奖励吗？");
            } else if (selection == 2) {
                mod = 12;
                cm.sendYesNo("你确定要領取第10次打奖励吗？");
            } else if (selection == 3) {
                mod = 13;
                cm.sendYesNo("你确定要領取第20次打奖励吗？");
            } else if (selection == 4) {
                mod = 14;
                cm.sendYesNo("你确定要領取第30次打奖励吗？");
            } else if (selection == 5) {
                mod = 15;
                cm.sendYesNo("你确定要領取第50次打奖励吗？");
            }
        } else if (sel == 2) {
            if (selection == 0) {
                mod = 20;
                cm.sendYesNo("你确定要領取第1次打奖励吗？\r\n#b枫點200點#l\r\n#b#z1122000##l");
            } else if (selection == 1) {
                mod = 21;
                cm.sendYesNo("你确定要領取第5次打奖励吗？\r\n#b#z2041200##l\r\n#b#z1042243##l");
            } else if (selection == 2) {
                mod = 22;
                cm.sendYesNo("你确定要領取第10次打奖励吗？\r\n#b#z3010128##l\r\n#b#z1142007##l");
            } else if (selection == 3) {
                mod = 23;
                cm.sendYesNo("你确定要領取第20次打奖励吗？\r\n#b枫點2000點#l\r\n#b#z4031408#x3#l");
            } else if (selection == 4) {
                mod = 24;
                cm.sendYesNo("你确定要領取第30次打奖励吗？\r\n#b500紅利點#l\r\n#b#z5064000#x3#l");
            } else if (selection == 5) {
                mod = 25;
                cm.sendYesNo("你确定要領取第50次打奖励吗？\r\n#b枫點2500點#l\r\n#b#z1142504##l\r\n#b#z5062001#x10#l");
            }
        } else if (sel == 3) {
            if (selection == 0) {
                mod = 30;
                cm.sendYesNo("你确定要領取第1次打奖励吗？");
            } else if (selection == 1) {
                mod = 31;
                cm.sendYesNo("你确定要領取第5次打奖励吗？");
            } else if (selection == 2) {
                mod = 32;
                cm.sendYesNo("你确定要領取第10次打奖励吗？");
            } else if (selection == 3) {
                mod = 33;
                cm.sendYesNo("你确定要領取第20次打奖励吗？");
            } else if (selection == 4) {
                mod = 34;
                cm.sendYesNo("你确定要領取第30次打奖励吗？");
            } else if (selection == 5) {
                mod = 35;
                cm.sendYesNo("你确定要領取第50次打奖励吗？");
            }
        } else if (sel == 4) {
            if (selection == 0) {
                mod = 40;
                cm.sendYesNo("你确定要領取第1次打奖励吗？\r\n#b#z1002926##l");
            } else if (selection == 1) {
                mod = 41;
                cm.sendYesNo("你确定要領取第5次打奖励吗？\r\n#b#z4031408##l");
            } else if (selection == 2) {
                mod = 42;
                cm.sendYesNo("你确定要領取第10次打奖励吗？\r\n#b枫點1000點#l");
            } else if (selection == 3) {
                mod = 43;
                cm.sendYesNo("你确定要領取第15次打奖励吗？\r\n#b#z5220040##l");
            }
        } else if (sel == 8) {
            if (selection == 0) {
                mod = 80;
                cm.sendYesNo("你确定要領取第1次打奖励吗？\r\n#b#z1002927##l");
            } else if (selection == 1) {
                mod = 81;
                cm.sendYesNo("你确定要領取第5次打奖励吗？\r\n#b#z4031408##l");
            } else if (selection == 2) {
                mod = 82;
                cm.sendYesNo("你确定要領取第10次打奖励吗？\r\n#b枫點1000點#l");
            } else if (selection == 3) {
                mod = 83;
                cm.sendYesNo("你确定要領取第15次打奖励吗？\r\n#b#z5220040##l");
            }
        } else if (sel == 5) {
            if (selection == 0) {
                mod = 50;
                cm.sendYesNo("你确定要領取第1次打奖励吗？");
            } else if (selection == 1) {
                mod = 51;
                cm.sendYesNo("你确定要領取第5次打奖励吗？");
            } else if (selection == 2) {
                mod = 52;
                cm.sendYesNo("你确定要領取第10次打奖励吗？");
            } else if (selection == 3) {
                mod = 53;
                cm.sendYesNo("你确定要領取第20次打奖励吗？");
            } else if (selection == 4) {
                mod = 54;
                cm.sendYesNo("你确定要領取第30次打奖励吗？");
            } else if (selection == 5) {
                mod = 55;
                cm.sendYesNo("你确定要領取第50次打奖励吗？");
            }
        } else if (sel == 6) {
            if (selection == 0) {
                mod = 60;
                cm.sendYesNo("你确定要領取第1次打奖励吗？\r\n#b枫點500點#l\r\n#b#z4310010##l");
            } else if (selection == 1) {
                mod = 61;
                cm.sendYesNo("你确定要領取第5次打奖励吗？\r\n#b#z4031408#加下面一种。#l\r\n#b#z1003154##l \r\n#b#z1003155##l \r\n#b#z1003156##l \r\n#b#z1003157##l \r\n#b#z1003158##l");
            } else if (selection == 2) {
                mod = 62;
                cm.sendYesNo("你确定要領取第10次打奖励吗？\r\n#b666枫點加下面一种。#l\r\n#b#z1052299##l \r\n#b#z1052300##l \r\n#b#z1052301##l \r\n#b#z1052302##l \r\n#b#z1052303##l");
            } else if (selection == 3) {
                mod = 63;
                cm.sendYesNo("你确定要領取第20次打奖励吗？\r\n#b枫點1200點#l\r\n#b#z5062001#x5#l");
            } else if (selection == 4) {
                mod = 64;
                cm.sendYesNo("你确定要領取第30次打奖励吗？\r\n#b選一种。#l\r\n#b#z3015015##l \r\n#b#z3015016##l \r\n#b#z3015017##l \r\n#b#z3015018##l \r\n#b#z3015019##l \r\n#b#z3015020##l \r\n#b#z3015021##l \r\n#b#z3015022##l \r\n#b#z3015023##l \r\n#b#z3015024##l \r\n#b#z3015025##l \r\n#b#z3015026##l");
            } else if (selection == 5) {
                mod = 65;
                cm.sendYesNo("你确定要領取第40次打奖励吗？\r\n#b#z2049300#x5#l\r\n#b#z5570000##l\r\n#b#z2049400#x3#l");
            } else if (selection == 6) {
                mod = 66;
                cm.sendYesNo("你确定要領取第50次打奖励吗？\r\n#bGASH500點#l\r\n#b#z5062001#x15#l\r\n#b#z3010188##l\r\n#b#z5000361#（365天）#l");
            }

        } else if (sel == 7) {
            if (selection == 0) {
                mod = 70;
                cm.sendYesNo("你确定要領取第1次打奖励吗？");
            } else if (selection == 1) {
                mod = 71;
                cm.sendYesNo("你确定要領取第5次打奖励吗？");
            } else if (selection == 2) {
                mod = 72;
                cm.sendYesNo("你确定要領取第10次打奖励吗？");
            } else if (selection == 3) {
                mod = 73;
                cm.sendYesNo("你确定要領取第20次打奖励吗？");
            } else if (selection == 4) {
                mod = 74;
                cm.sendYesNo("你确定要領取第30次打奖励吗？");
            } else if (selection == 5) {
                mod = 75;
                cm.sendYesNo("你确定要領取第50次打奖励吗？");
            }
        }
    } else if (status == 2) {
        if (!cm.canHold()) {
            cm.sendOk("您的背包已满。");
            cm.dispose();
            return;
        }
        switch (mod) {
            case 0:
            {

                cm.sendSimple("#z1002357#加下面一种。\r\n#b#L100##z1302312##l \r\n#b#L101##z1312182##l \r\n#b#L102##z1322233##l \r\n#b#L103##z1332257##l \r\n#b#L104##z1342097##l \r\n#b#L105##z1372049##l \r\n#b#L106##z1372204##l \r\n#b#L107##z1382242##l \r\n#b#L108##z1402233##l \r\n#b#L109##z1412161##l r\n#b#L110##z1472244##l r\n#b#L111##z1482199##l r\n#b#L112##z1492209##l r\n#b#L113##z1442251##l");
                break;
            }
            case 1:
            {
                if (cm.getPlayer().getBossLogS("普炎攻打次數") < 5) {
                    cm.sendOk("你的打王次數不够。");
                    cm.dispose();
                    return;
                }
                if (cm.getPlayer().getBossLogS("普炎5次奖励") >= 1) {
                    cm.sendOk("你已经領取过奖励。");
                    cm.dispose();
                    return;
                }
                cm.setBossLog("普炎5次奖励");
                cm.gainItem(4031408, 1);
                cm.gainItem(3010127, 1);
                cm.sendOk("打王奖励获得\r\n#z4031408#x1\r\n#z3010127#x1");
                cm.dispose();
                return;
                break;
            }
            case 2:
            {

                cm.sendSimple("選一种。\r\n#b#L200##z2290008##l \r\n#b#L201##z2290022##l \r\n#b#L202##z2290012##l \r\n#b#L203##z2290029##l \r\n#b#L204##z2290030##l \r\n#b#L205##z2290032##l \r\n#b#L206##z2290050##l \r\n#b#L207##z2290060##l \r\n#b#L208##z2290074##l \r\n#b#L209##z2290084##l \r\n#b#L210##z2290092##l \r\n#b#L211##z2290097##l \r\n#b#L212##z2290119##l \r\n#b#L213##z2290126##l \r\n#b#L214##z2290140##l \r\n#b#L215##z2290230##l \r\n#b#L216##z2290240##l \r\n#b#L217##z2290279##l \r\n#b#L218##z2290256##l");
                break;
            }
            case 3:
            {
                if (cm.getPlayer().getBossLogS("普炎攻打次數") < 20) {
                    cm.sendOk("你的打王次數不够。");
                    cm.dispose();
                    return;
                }
                if (cm.getPlayer().getBossLogS("普炎20次奖励") >= 1) {
                    cm.sendOk("你已经領取过奖励。");
                    cm.dispose();
                    return;
                }
                cm.setBossLog("普炎20次奖励");
                cm.gainItem(1004119, 1);
                cm.gainItem(2290285, 3);
                cm.getPlayer().modifyCSPoints(2, 1000, true);
                cm.sendOk("打王奖励获得 \r\n#z1004119#lx1 \r\n#z2290285#lx3 \r\n枫葉點數x1000");
                cm.dispose();
                return;
                break;
            }
            case 4:
            {
                if (cm.getPlayer().getBossLogS("普炎攻打次數") < 30) {
                    cm.sendOk("你的打王次數不够。");
                    cm.dispose();
                    return;
                }
                if (cm.getPlayer().getBossLogS("普炎30次奖励") >= 1) {
                    cm.sendOk("你已经領取过奖励。");
                    cm.dispose();
                    return;
                }
                cm.setBossLog("普炎30次奖励");
                cm.gainItem(5570000, 1);
                cm.gainItem(2049300, 10);
                cm.getPlayer().modifyCSPoints(2, 1200, true);
                cm.sendOk("打王奖励获得 \r\n#z5570000#lx1 \r\n#z2049300#lx10 \r\n枫葉點數x1200");

                cm.dispose();
                return;
                break;
            }
            case 5:
            {
                if (cm.getPlayer().getBossLogS("普炎攻打次數") < 50) {
                    cm.sendOk("你的打王次數不够。");
                    cm.dispose();
                    return;
                }
                if (cm.getPlayer().getBossLogS("普炎50次奖励") >= 1) {
                    cm.sendOk("你已经領取过奖励。");
                    cm.dispose();
                    return;
                }
                cm.setBossLog("普炎50次奖励");
                cm.gainItemPeriod(5000479, 1, 90);
                cm.gainItem(1142503, 1);
                cm.gainItem(5062001, 10);
                cm.gainItem(2430333, 1);
                cm.sendOk("打王奖励获得 \r\n#z5000479#l90天x1 \r\n#z1142503#lx1  \r\n#z5062001#lx10 \r\n#z2430333#lx1");
                cm.dispose();
                return;
                break;
            }
            case 10:
            {
                if (cm.getPlayer().getBossLogS("混炎攻打次數") < 1) {
                    cm.sendOk("你的打王次數不够。");
                    cm.dispose();
                    return;
                }
                if (cm.getPlayer().getBossLogS("混炎1次奖励") >= 1) {
                    cm.sendOk("你已经領取过奖励。");
                    cm.dispose();
                    return;
                }
                cm.setBossLog("混炎1次奖励");
                cm.gainItem(5220000, 1);
                cm.sendOk("打王奖励获得" + "#z5220000#l");
                cm.dispose();
                return;
                break;
            }
            case 11:
            {
                if (cm.getPlayer().getBossLogS("混炎攻打次數") < 5) {
                    cm.sendOk("你的打王次數不够。");
                    cm.dispose();
                    return;
                }
                if (cm.getPlayer().getBossLogS("混炎5次奖励") >= 1) {
                    cm.sendOk("你已经領取过奖励。");
                    cm.dispose();
                    return;
                }
                cm.setBossLog("混炎5次奖励");
                cm.gainItem(5220000, 1);
                cm.sendOk("打王奖励获得" + "#z5220000#l");
                cm.dispose();
                return;
                break;
            }
            case 12:
            {
                if (cm.getPlayer().getBossLogS("混炎攻打次數") < 10) {
                    cm.sendOk("你的打王次數不够。");
                    cm.dispose();
                    return;
                }
                if (cm.getPlayer().getBossLogS("混炎10次奖励") >= 1) {
                    cm.sendOk("你已经領取过奖励。");
                    cm.dispose();
                    return;
                }
                cm.setBossLog("混炎10次奖励");
                cm.gainItem(5220000, 1);
                cm.sendOk("打王奖励获得" + "#z5220000#l");
                cm.dispose();
                return;
                break;
            }
            case 13:
            {
                if (cm.getPlayer().getBossLogS("混炎攻打次數") < 20) {
                    cm.sendOk("你的打王次數不够。");
                    cm.dispose();
                    return;
                }
                if (cm.getPlayer().getBossLogS("混炎20次奖励") >= 1) {
                    cm.sendOk("你已经領取过奖励。");
                    cm.dispose();
                    return;
                }
                cm.setBossLog("混炎20次奖励");
                cm.gainItem(5220000, 1);
                cm.sendOk("打王奖励获得" + "#z5220000#l");
                cm.dispose();
                return;
                break;
            }
            case 14:
            {
                if (cm.getPlayer().getBossLogS("混炎攻打次數") < 30) {
                    cm.sendOk("你的打王次數不够。");
                    cm.dispose();
                    return;
                }
                if (cm.getPlayer().getBossLogS("混炎30次奖励") >= 1) {
                    cm.sendOk("你已经領取过奖励。");
                    cm.dispose();
                    return;
                }
                cm.setBossLog("混炎30次奖励");
                cm.gainItem(5220000, 1);
                cm.sendOk("打王奖励获得" + "#z5220000#l");
                cm.dispose();
                return;
                break;
            }
            case 15:
            {
                if (cm.getPlayer().getBossLogS("混炎攻打次數") < 50) {
                    cm.sendOk("你的打王次數不够。");
                    cm.dispose();
                    return;
                }
                if (cm.getPlayer().getBossLogS("混炎50次奖励") >= 1) {
                    cm.sendOk("你已经領取过奖励。");
                    cm.dispose();
                    return;
                }
                cm.setBossLog("混炎50次奖励");
                cm.gainItem(5220000, 1);
                cm.sendOk("打王奖励获得" + "#z5220000#l");
                cm.dispose();
                return;
                break;
            }
            case 20:
            {
                if (cm.getPlayer().getBossLogS("普龙攻打次數") < 1) {
                    cm.sendOk("你的打王次數不够。");
                    cm.dispose();
                    return;
                }
                if (cm.getPlayer().getBossLogS("普龙1次奖励") >= 1) {
                    cm.sendOk("你已经領取过奖励。");
                    cm.dispose();
                    return;
                }
                cm.setBossLog("普龙1次奖励");
                cm.gainItem(1122000, 1);
                cm.getPlayer().modifyCSPoints(2, 200, true);

                cm.sendOk("打王奖励获得 \r\n#z1122000#lx1 \r\n枫葉點數x200");
                cm.dispose();
                return;
                break;
            }
            case 21:
            {
                if (cm.getPlayer().getBossLogS("普龙攻打次數") < 5) {
                    cm.sendOk("你的打王次數不够。");
                    cm.dispose();
                    return;
                }
                if (cm.getPlayer().getBossLogS("普龙5次奖励") >= 1) {
                    cm.sendOk("你已经領取过奖励。");
                    cm.dispose();
                    return;
                }
                cm.setBossLog("普龙5次奖励");
                cm.gainItem(2041200, 1);
                cm.gainItem(1042243, 1);

                cm.sendOk("打王奖励获得 \r\n#z2041200#lx1 \r\n#z1042243#lx1");
                cm.dispose();
                return;
                break;
            }
            case 22:
            {
                if (cm.getPlayer().getBossLogS("普龙攻打次數") < 10) {
                    cm.sendOk("你的打王次數不够。");
                    cm.dispose();
                    return;
                }
                if (cm.getPlayer().getBossLogS("普龙10次奖励") >= 1) {
                    cm.sendOk("你已经領取过奖励。");
                    cm.dispose();
                    return;
                }
                cm.setBossLog("普龙10次奖励");
                cm.gainItem(3010128, 1);
                cm.gainItem(1142007, 1);

                cm.sendOk("打王奖励获得 \r\n#z3010128#lx1 \r\n#z1142007#lx1");
                cm.dispose();
                return;
                break;
            }
            case 23:
            {
                if (cm.getPlayer().getBossLogS("普龙攻打次數") < 20) {
                    cm.sendOk("你的打王次數不够。");
                    cm.dispose();
                    return;
                }
                if (cm.getPlayer().getBossLogS("普龙20次奖励") >= 1) {
                    cm.sendOk("你已经領取过奖励。");
                    cm.dispose();
                    return;
                }
                cm.setBossLog("普龙20次奖励");
                cm.gainItem(4031408, 3);
                cm.getPlayer().modifyCSPoints(2, 2000, true);

                cm.sendOk("打王奖励获得 \r\n#z4031408#lx3 \r\n枫葉點數x2000");
                cm.dispose();
                return;
                break;
            }
            case 24:
            {
                if (cm.getPlayer().getBossLogS("普龙攻打次數") < 30) {
                    cm.sendOk("你的打王次數不够。");
                    cm.dispose();
                    return;
                }
                if (cm.getPlayer().getBossLogS("普龙30次奖励") >= 1) {
                    cm.sendOk("你已经領取过奖励。");
                    cm.dispose();
                    return;
                }
                cm.setBossLog("普龙30次奖励");
                cm.gainItem(5064000, 3);
                cm.getPlayer().setPoints(cm.getPlayer().getPoints() + 500);
                cm.sendOk("打王奖励获得 \r\n#z5064000#lx3 \r\n500紅利點");

                cm.dispose();
                return;
                break;
            }
            case 25:
            {
                if (cm.getPlayer().getBossLogS("普龙攻打次數") < 50) {
                    cm.sendOk("你的打王次數不够。");
                    cm.dispose();
                    return;
                }
                if (cm.getPlayer().getBossLogS("普龙50次奖励") >= 1) {
                    cm.sendOk("你已经領取过奖励。");
                    cm.dispose();
                    return;
                }
                cm.setBossLog("普龙50次奖励");
                cm.gainItem(1142504, 1);
                cm.gainItem(5062001, 10);
                cm.getPlayer().modifyCSPoints(2, 2500, true);
                cm.sendOk("打王奖励获得 \r\n#z5062001#lx10 \r\n#z1142504#lx1 \r\n枫葉點數x2500");
                cm.dispose();
                return;
                break;
            }
            case 30:
            {
                if (cm.getPlayer().getBossLogS("混龙攻打次數") < 1) {
                    cm.sendOk("你的打王次數不够。");
                    cm.dispose();
                    return;
                }
                if (cm.getPlayer().getBossLogS("混龙1次奖励") >= 1) {
                    cm.sendOk("你已经領取过奖励。");
                    cm.dispose();
                    return;
                }
                cm.setBossLog("混龙1次奖励");
                cm.gainItem(5220000, 1);
                cm.sendOk("打王奖励获得" + "#z5220000#l");
                cm.dispose();
                return;
                break;
            }
            case 31:
            {
                if (cm.getPlayer().getBossLogS("混龙攻打次數") < 5) {
                    cm.sendOk("你的打王次數不够。");
                    cm.dispose();
                    return;
                }
                if (cm.getPlayer().getBossLogS("混龙5次奖励") >= 1) {
                    cm.sendOk("你已经領取过奖励。");
                    cm.dispose();
                    return;
                }
                cm.setBossLog("混龙5次奖励");
                cm.gainItem(5220000, 1);
                cm.sendOk("打王奖励获得" + "#z5220000#l");
                cm.dispose();
                return;
                break;
            }
            case 32:
            {
                if (cm.getPlayer().getBossLogS("混龙攻打次數") < 10) {
                    cm.sendOk("你的打王次數不够。");
                    cm.dispose();
                    return;
                }
                if (cm.getPlayer().getBossLogS("混龙10次奖励") >= 1) {
                    cm.sendOk("你已经領取过奖励。");
                    cm.dispose();
                    return;
                }
                cm.setBossLog("混龙10次奖励");
                cm.gainItem(5220000, 1);
                cm.sendOk("打王奖励获得" + "#z5220000#l");
                cm.dispose();
                return;
                break;
            }
            case 33:
            {
                if (cm.getPlayer().getBossLogS("混龙攻打次數") < 20) {
                    cm.sendOk("你的打王次數不够。");
                    cm.dispose();
                    return;
                }
                if (cm.getPlayer().getBossLogS("混龙20次奖励") >= 1) {
                    cm.sendOk("你已经領取过奖励。");
                    cm.dispose();
                    return;
                }
                cm.setBossLog("混龙20次奖励");
                cm.gainItem(5220000, 1);
                cm.sendOk("打王奖励获得" + "#z5220000#l");
                cm.dispose();
                return;
                break;
            }
            case 34:
            {
                if (cm.getPlayer().getBossLogS("混龙攻打次數") < 30) {
                    cm.sendOk("你的打王次數不够。");
                    cm.dispose();
                    return;
                }
                if (cm.getPlayer().getBossLogS("混龙30次奖励") >= 1) {
                    cm.sendOk("你已经領取过奖励。");
                    cm.dispose();
                    return;
                }
                cm.setBossLog("混龙30次奖励");
                cm.gainItem(5220000, 1);
                cm.sendOk("打王奖励获得" + "#z5220000#l");
                cm.dispose();
                return;
                break;
            }
            case 35:
            {
                if (cm.getPlayer().getBossLogS("混龙攻打次數") < 50) {
                    cm.sendOk("你的打王次數不够。");
                    cm.dispose();
                    return;
                }
                if (cm.getPlayer().getBossLogS("混龙50次奖励") >= 1) {
                    cm.sendOk("你已经領取过奖励。");
                    cm.dispose();
                    return;
                }
                cm.setBossLog("混龙50次奖励");
                cm.gainItem(5220000, 1);
                cm.sendOk("打王奖励获得" + "#z5220000#l");
                cm.dispose();
                return;
                break;
            }
            case 40:
            {
                if (cm.getPlayer().getBossLogS("泰勒熊攻打次數") < 1) {
                    cm.sendOk("你的打王次數不够。");
                    cm.dispose();
                    return;
                }
                if (cm.getPlayer().getBossLogS("泰勒熊1次奖励") >= 1) {
                    cm.sendOk("你已经領取过奖励。");
                    cm.dispose();
                    return;
                }
                cm.setBossLog("泰勒熊1次奖励");
                cm.gainItem(1002926, 1);
                cm.sendOk("打王奖励获得" + "#z1002926#l");
                cm.dispose();
                return;
                break;
            }
            case 41:
            {
                if (cm.getPlayer().getBossLogS("泰勒熊攻打次數") < 5) {
                    cm.sendOk("你的打王次數不够。");
                    cm.dispose();
                    return;
                }
                if (cm.getPlayer().getBossLogS("泰勒熊5次奖励") >= 1) {
                    cm.sendOk("你已经領取过奖励。");
                    cm.dispose();
                    return;
                }
                cm.setBossLog("泰勒熊5次奖励");
                cm.gainItem(4031408, 1);
                cm.sendOk("打王奖励获得" + "#z4031408#l");
                cm.dispose();
                return;
                break;
            }
            case 42:
            {
                if (cm.getPlayer().getBossLogS("泰勒熊攻打次數") < 10) {
                    cm.sendOk("你的打王次數不够。");
                    cm.dispose();
                    return;
                }
                if (cm.getPlayer().getBossLogS("泰勒熊10次奖励") >= 1) {
                    cm.sendOk("你已经領取过奖励。");
                    cm.dispose();
                    return;
                }
                cm.setBossLog("泰勒熊10次奖励");
                cm.getPlayer().modifyCSPoints(2, 1000, true);
                cm.sendOk("打王奖励获得枫葉點數x1000");
                cm.dispose();
                return;
                break;
            }
            case 43:
            {
                if (cm.getPlayer().getBossLogS("泰勒熊攻打次數") < 15) {
                    cm.sendOk("你的打王次數不够。");
                    cm.dispose();
                    return;
                }
                if (cm.getPlayer().getBossLogS("泰勒熊15次奖励") >= 1) {
                    cm.sendOk("你已经領取过奖励。");
                    cm.dispose();
                    return;
                }
                cm.setBossLog("泰勒熊15次奖励");
                cm.gainItem(5220040, 1);
                cm.sendOk("打王奖励获得" + "#z5220040#l");
                cm.dispose();
                return;
                break;
            }
            case 80:
            {
                if (cm.getPlayer().getBossLogS("娃娃獅攻打次數") < 1) {
                    cm.sendOk("你的打王次數不够。");
                    cm.dispose();
                    return;
                }
                if (cm.getPlayer().getBossLogS("娃娃獅1次奖励") >= 1) {
                    cm.sendOk("你已经領取过奖励。");
                    cm.dispose();
                    return;
                }
                cm.setBossLog("娃娃獅1次奖励");
                cm.gainItem(1002927, 1);
                cm.sendOk("打王奖励获得" + "#z1002926#l");
                cm.dispose();
                return;
                break;
            }
            case 81:
            {
                if (cm.getPlayer().getBossLogS("娃娃獅攻打次數") < 5) {
                    cm.sendOk("你的打王次數不够。");
                    cm.dispose();
                    return;
                }
                if (cm.getPlayer().getBossLogS("娃娃獅5次奖励") >= 1) {
                    cm.sendOk("你已经領取过奖励。");
                    cm.dispose();
                    return;
                }
                cm.setBossLog("娃娃獅5次奖励");
                cm.gainItem(4031408, 1);
                cm.sendOk("打王奖励获得" + "#z4031408#l");
                cm.dispose();
                return;
                break;
            }
            case 82:
            {
                if (cm.getPlayer().getBossLogS("娃娃獅攻打次數") < 10) {
                    cm.sendOk("你的打王次數不够。");
                    cm.dispose();
                    return;
                }
                if (cm.getPlayer().getBossLogS("娃娃獅10次奖励") >= 1) {
                    cm.sendOk("你已经領取过奖励。");
                    cm.dispose();
                    return;
                }
                cm.setBossLog("娃娃獅10次奖励");
                cm.getPlayer().modifyCSPoints(2, 1000, true);
                cm.sendOk("打王奖励获得枫葉點數x1000");
                cm.dispose();
                return;
                break;
            }
            case 83:
            {
                if (cm.getPlayer().getBossLogS("娃娃獅攻打次數") < 15) {
                    cm.sendOk("你的打王次數不够。");
                    cm.dispose();
                    return;
                }
                if (cm.getPlayer().getBossLogS("娃娃獅15次奖励") >= 1) {
                    cm.sendOk("你已经領取过奖励。");
                    cm.dispose();
                    return;
                }
                cm.setBossLog("娃娃獅15次奖励");
                cm.gainItem(5220040, 1);
                cm.sendOk("打王奖励获得" + "#z5220040#l");
                cm.dispose();
                return;
                break;
            }
            case 50:
            {
                if (cm.getPlayer().getBossLogS("皮卡啾攻打次數") < 1) {
                    cm.sendOk("你的打王次數不够。");
                    cm.dispose();
                    return;
                }
                if (cm.getPlayer().getBossLogS("皮卡啾1次奖励") >= 1) {
                    cm.sendOk("你已经領取过奖励。");
                    cm.dispose();
                    return;
                }
                cm.setBossLog("皮卡啾1次奖励");
                cm.gainItem(5220000, 1);
                cm.sendOk("打王奖励获得" + "#z5220000#l");
                cm.dispose();
                return;
                break;
            }
            case 51:
            {
                if (cm.getPlayer().getBossLogS("皮卡啾攻打次數") < 5) {
                    cm.sendOk("你的打王次數不够。");
                    cm.dispose();
                    return;
                }
                if (cm.getPlayer().getBossLogS("皮卡啾5次奖励") >= 1) {
                    cm.sendOk("你已经領取过奖励。");
                    cm.dispose();
                    return;
                }
                cm.setBossLog("皮卡啾5次奖励");
                cm.gainItem(5220000, 1);
                cm.sendOk("打王奖励获得" + "#z5220000#l");
                cm.dispose();
                return;
                break;
            }
            case 52:
            {
                if (cm.getPlayer().getBossLogS("皮卡啾攻打次數") < 10) {
                    cm.sendOk("你的打王次數不够。");
                    cm.dispose();
                    return;
                }
                if (cm.getPlayer().getBossLogS("皮卡啾10次奖励") >= 1) {
                    cm.sendOk("你已经領取过奖励。");
                    cm.dispose();
                    return;
                }
                cm.setBossLog("皮卡啾10次奖励");
                cm.gainItem(5220000, 1);
                cm.sendOk("打王奖励获得" + "#z5220000#l");
                cm.dispose();
                return;
                break;
            }
            case 53:
            {
                if (cm.getPlayer().getBossLogS("皮卡啾攻打次數") < 20) {
                    cm.sendOk("你的打王次數不够。");
                    cm.dispose();
                    return;
                }
                if (cm.getPlayer().getBossLogS("皮卡啾20次奖励") >= 1) {
                    cm.sendOk("你已经領取过奖励。");
                    cm.dispose();
                    return;
                }
                cm.setBossLog("皮卡啾20次奖励");
                cm.gainItem(5220000, 1);
                cm.sendOk("打王奖励获得" + "#z5220000#l");
                cm.dispose();
                return;
                break;
            }
            case 54:
            {
                if (cm.getPlayer().getBossLogS("皮卡啾攻打次數") < 30) {
                    cm.sendOk("你的打王次數不够。");
                    cm.dispose();
                    return;
                }
                if (cm.getPlayer().getBossLogS("皮卡啾30次奖励") >= 1) {
                    cm.sendOk("你已经領取过奖励。");
                    cm.dispose();
                    return;
                }
                cm.setBossLog("皮卡啾30次奖励");
                cm.gainItem(5220000, 1);
                cm.sendOk("打王奖励获得" + "#z5220000#l");
                cm.dispose();
                return;
                break;
            }
            case 55:
            {
                if (cm.getPlayer().getBossLogS("皮卡啾攻打次數") < 50) {
                    cm.sendOk("你的打王次數不够。");
                    cm.dispose();
                    return;
                }
                if (cm.getPlayer().getBossLogS("皮卡啾50次奖励") >= 1) {
                    cm.sendOk("你已经領取过奖励。");
                    cm.dispose();
                    return;
                }
                cm.setBossLog("皮卡啾50次奖励");
                cm.gainItem(5220000, 1);
                cm.sendOk("打王奖励获得" + "#z5220000#l");
                cm.dispose();
                return;
                break;
            }
            case 60:
            {
                if (cm.getPlayer().getBossLogS("凡雷恩攻打次數") < 1) {
                    cm.sendOk("你的打王次數不够。");
                    cm.dispose();
                    return;
                }
                if (cm.getPlayer().getBossLogS("凡雷恩1次奖励") >= 1) {
                    cm.sendOk("你已经領取过奖励。");
                    cm.dispose();
                    return;
                }
                cm.setBossLog("凡雷恩1次奖励");
                cm.gainItem(4310010, 1);
                cm.getPlayer().modifyCSPoints(2, 500, true);
                cm.sendOk("打王奖励获得枫葉點數500 #z4310010#l");
                cm.dispose();
                return;
                break;
            }
            case 61:
            {

                cm.sendSimple("#z4031408#加下面一种。\r\n#b#L610##z1003154##l \r\n#b#L611##z1003155##l \r\n#b#L612##z1003156##l \r\n#b#L613##z1003157##l \r\n#b#L614##z1003158##l");
                break;

                /*if (cm.getPlayer().getBossLogS("凡雷恩攻打次數") < 5) {
                 cm.sendOk("你的打王次數不够。");
                 cm.dispose();
                 return;
                 }
                 if (cm.getPlayer().getBossLogS("凡雷恩5次奖励") >= 1) {
                 cm.sendOk("你已经領取过奖励。");
                 cm.dispose();
                 return;
                 }
                 cm.setBossLog("凡雷恩5次奖励");
                 cm.gainItem(5220000, 1);
                 cm.sendOk("打王奖励获得" + "#z5220000#l");
                 cm.dispose();
                 return;
                 break;*/
            }
            case 62:
            {
                cm.sendSimple("666枫葉點數加下面一种。\r\n#b#L620##z1052299##l \r\n#b#L621##z1052300##l \r\n#b#L622##z1052301##l \r\n#b#L623##z1052302##l \r\n#b#L624##z1052303##l");
                break;
                /*if (cm.getPlayer().getBossLogS("凡雷恩攻打次數") < 10) {
                 cm.sendOk("你的打王次數不够。");
                 cm.dispose();
                 return;
                 }
                 if (cm.getPlayer().getBossLogS("凡雷恩10次奖励") >= 1) {
                 cm.sendOk("你已经領取过奖励。");
                 cm.dispose();
                 return;
                 }
                 cm.setBossLog("凡雷恩10次奖励");
                 cm.gainItem(5220000, 1);
                 cm.sendOk("打王奖励获得" + "#z5220000#l");
                 cm.dispose();
                 return;
                 break;*/
            }
            case 63:
            {
                if (cm.getPlayer().getBossLogS("凡雷恩攻打次數") < 20) {
                    cm.sendOk("你的打王次數不够。");
                    cm.dispose();
                    return;
                }
                if (cm.getPlayer().getBossLogS("凡雷恩20次奖励") >= 1) {
                    cm.sendOk("你已经領取过奖励。");
                    cm.dispose();
                    return;
                }
                cm.setBossLog("凡雷恩20次奖励");
                cm.gainItem(5062001, 5);
                cm.getPlayer().modifyCSPoints(2, 1200, true);
                cm.sendOk("打王奖励获得1200枫葉點數 " + "#z5062001#lx5");
                cm.dispose();
                return;
                break;
            }
            case 64:
            {
                cm.sendSimple("下面選一种。\r\n#b#L640##z3015015##l \r\n#b#L641##z3015016##l \r\n#b#L642##z3015017##l \r\n#b#L643##z3015018##l \r\n#b#L644##z3015019##l \r\n#b#L645##z3015020##l \r\n#b#L646##z3015021##l \r\n#b#L647##z3015022##l \r\n#b#L648##z3015023##l \r\n#b#L649##z3015024##l \r\n#b#L650##z3015025##l \r\n#b#L651##z3015026##l");
                break;
                /*if (cm.getPlayer().getBossLogS("凡雷恩攻打次數") < 30) {
                 cm.sendOk("你的打王次數不够。");
                 cm.dispose();
                 return;
                 }
                 if (cm.getPlayer().getBossLogS("凡雷恩30次奖励") >= 1) {
                 cm.sendOk("你已经領取过奖励。");
                 cm.dispose();
                 return;
                 }
                 cm.setBossLog("凡雷恩30次奖励");
                 cm.gainItem(5220000, 1);
                 cm.sendOk("打王奖励获得" + "#z5220000#l");
                 cm.dispose();
                 return;
                 break;*/
            }
            case 65:
            {
                if (cm.getPlayer().getBossLogS("凡雷恩攻打次數") < 40) {
                    cm.sendOk("你的打王次數不够。");
                    cm.dispose();
                    return;
                }
                if (cm.getPlayer().getBossLogS("凡雷恩40次奖励") >= 1) {
                    cm.sendOk("你已经領取过奖励。");
                    cm.dispose();
                    return;
                }
                cm.setBossLog("凡雷恩40次奖励");
                cm.gainItem(2049300, 5);
                cm.gainItem(5570000, 1);
                cm.gainItem(2049400, 3);
                cm.sendOk("打王奖励获得#z2049300#lx5 #z5570000#l #z2049400#lx3");
                cm.dispose();
                return;
                break;
            }
            case 66:
            {
                if (cm.getPlayer().getBossLogS("凡雷恩攻打次數") < 50) {
                    cm.sendOk("你的打王次數不够。");
                    cm.dispose();
                    return;
                }
                if (cm.getPlayer().getBossLogS("凡雷恩50次奖励") >= 1) {
                    cm.sendOk("你已经領取过奖励。");
                    cm.dispose();
                    return;
                }
                cm.setBossLog("凡雷恩50次奖励");
                cm.getPlayer().modifyCSPoints(1, 500, true);
                cm.gainItem(5062001, 15);
                cm.gainItem(3010188, 1);
                cm.gainItemPeriod(5000361, 1, 365);
                cm.sendOk("打王奖励获得#z5062001#lx15 #z3010188#l #z5000361#lx365天");
                cm.dispose();
                return;
                break;
            }
            case 70:
            {
                if (cm.getPlayer().getBossLogS("女皇攻打次數") < 1) {
                    cm.sendOk("你的打王次數不够。");
                    cm.dispose();
                    return;
                }
                if (cm.getPlayer().getBossLogS("女皇1次奖励") >= 1) {
                    cm.sendOk("你已经領取过奖励。");
                    cm.dispose();
                    return;
                }
                cm.setBossLog("女皇1次奖励");
                cm.gainItem(5220000, 1);
                cm.sendOk("打王奖励获得" + "#z5220000#l");
                cm.dispose();
                return;
                break;
            }
            case 71:
            {
                if (cm.getPlayer().getBossLogS("女皇攻打次數") < 5) {
                    cm.sendOk("你的打王次數不够。");
                    cm.dispose();
                    return;
                }
                if (cm.getPlayer().getBossLogS("女皇5次奖励") >= 1) {
                    cm.sendOk("你已经領取过奖励。");
                    cm.dispose();
                    return;
                }
                cm.setBossLog("女皇5次奖励");
                cm.gainItem(5220000, 1);
                cm.sendOk("打王奖励获得" + "#z5220000#l");
                cm.dispose();
                return;
                break;
            }
            case 72:
            {
                if (cm.getPlayer().getBossLogS("女皇攻打次數") < 10) {
                    cm.sendOk("你的打王次數不够。");
                    cm.dispose();
                    return;
                }
                if (cm.getPlayer().getBossLogS("女皇10次奖励") >= 1) {
                    cm.sendOk("你已经領取过奖励。");
                    cm.dispose();
                    return;
                }
                cm.setBossLog("女皇10次奖励");
                cm.gainItem(5220000, 1);
                cm.sendOk("打王奖励获得" + "#z5220000#l");
                cm.dispose();
                return;
                break;
            }
            case 73:
            {
                if (cm.getPlayer().getBossLogS("女皇攻打次數") < 20) {
                    cm.sendOk("你的打王次數不够。");
                    cm.dispose();
                    return;
                }
                if (cm.getPlayer().getBossLogS("女皇20次奖励") >= 1) {
                    cm.sendOk("你已经領取过奖励。");
                    cm.dispose();
                    return;
                }
                cm.setBossLog("女皇20次奖励");
                cm.gainItem(5220000, 1);
                cm.sendOk("打王奖励获得" + "#z5220000#l");
                cm.dispose();
                return;
                break;
            }
            case 74:
            {
                if (cm.getPlayer().getBossLogS("女皇攻打次數") < 30) {
                    cm.sendOk("你的打王次數不够。");
                    cm.dispose();
                    return;
                }
                if (cm.getPlayer().getBossLogS("女皇30次奖励") >= 1) {
                    cm.sendOk("你已经領取过奖励。");
                    cm.dispose();
                    return;
                }
                cm.setBossLog("女皇30次奖励");
                cm.gainItem(5220000, 1);
                cm.sendOk("打王奖励获得" + "#z5220000#l");
                cm.dispose();
                return;
                break;
            }
            case 75:
            {
                if (cm.getPlayer().getBossLogS("女皇攻打次數") < 50) {
                    cm.sendOk("你的打王次數不够。");
                    cm.dispose();
                    return;
                }
                if (cm.getPlayer().getBossLogS("女皇50次奖励") >= 1) {
                    cm.sendOk("你已经領取过奖励。");
                    cm.dispose();
                    return;
                }
                cm.setBossLog("女皇50次奖励");
                cm.gainItem(5220000, 1);
                cm.sendOk("打王奖励获得" + "#z5220000#l");
                cm.dispose();
                return;
                break;
            }

        }
    } else if (status == 3) {
        if (!cm.canHold()) {
            cm.sendOk("您的背包已满。");
            cm.dispose();
            return;
        }
        switch (mod) {
            case 0:
            {
                if (!cm.canHoldByType(1, 2)) {
                    cm.sendOk("请确认背包是否已经满了。");
                    cm.dispose();
                    return;
                }
                if (cm.getPlayer().getBossLogS("普炎攻打次數") < 1) {
                    cm.sendOk("你的打王次數不够。");
                    cm.dispose();
                    return;
                }
                if (cm.getPlayer().getBossLogS("普炎1次奖励") >= 1) {
                    cm.sendOk("你已经領取过奖励。");
                    cm.dispose();
                    return;
                }
                itemid = selection == 100 ? 1302312 : selection == 101 ? 1312182 : selection == 102 ? 1322233 : selection == 103 ? 1332257 : selection == 104 ? 1342097 : selection == 105 ? 1372049 : selection == 106 ? 1372204 : selection == 107 ? 1382242 : selection == 108 ? 1402233 : selection == 109 ? 1412161 : selection == 110 ? 1472244 : selection == 111 ? 1482199 : selection == 112 ? 1492209 : selection == 113 ? 1442251 : 0;
                cm.setBossLog("普炎1次奖励");
                cm.gainItem(1002357, 1);
                cm.gainItem(itemid, 1);
                cm.sendOk("打王奖励获得\r\n#z" + itemid + "#x1\r\n#z1002357#x1");
                cm.dispose();
                return;


            }
            case 2:
            {
                if (cm.getPlayer().getBossLogS("普炎攻打次數") < 10) {
                    cm.sendOk("你的打王次數不够。");
                    cm.dispose();
                    return;
                }
                if (cm.getPlayer().getBossLogS("普炎10次奖励") >= 1) {
                    cm.sendOk("你已经領取过奖励。");
                    cm.dispose();
                    return;
                }

                itemid = selection == 200 ? 2290008 : selection == 201 ? 2290022 : selection == 202 ? 2290012 : selection == 203 ? 2290029 : selection == 204 ? 2290030 : selection == 205 ? 2290032 : selection == 206 ? 2290050 : selection == 207 ? 2290060 : selection == 208 ? 2290074 : selection == 209 ? 2290084 : selection == 210 ? 2290092 : selection == 211 ? 2290097 : selection == 212 ? 2290119 : selection == 213 ? 2290126 : selection == 214 ? 2290140 : selection == 215 ? 2290230 : selection == 216 ? 2290240 : selection == 217 ? 2290279 : selection == 218 ? 2290256 : 0;
                cm.setBossLog("普炎10次奖励");
                cm.gainItem(itemid, 1);
                cm.sendOk("打王奖励获得 \r\n#z" + itemid + "#x1");
                cm.dispose();
                return;
            }
            case 61:
            {
                if (cm.getPlayer().getBossLogS("凡雷恩攻打次數") < 5) {
                    cm.sendOk("你的打王次數不够。");
                    cm.dispose();
                    return;
                }
                if (cm.getPlayer().getBossLogS("凡雷恩5次奖励") >= 1) {
                    cm.sendOk("你已经領取过奖励。");
                    cm.dispose();
                    return;
                }
                itemid = selection == 610 ? 1003154 : selection == 611 ? 1003155 : selection == 612 ? 1003156 : selection == 613 ? 1003157 : selection == 614 ? 1003158 : 0;
                cm.setBossLog("凡雷恩5次奖励");
                cm.gainItem(4031408, 1);
                cm.gainItem(itemid, 1);
                cm.sendOk("打王奖励获得\r\n#z" + itemid + "#x1\r\n#z4031408#x1");
                cm.dispose();
                return;
            }
            case 62:
            {
                if (cm.getPlayer().getBossLogS("凡雷恩攻打次數") < 10) {
                    cm.sendOk("你的打王次數不够。");
                    cm.dispose();
                    return;
                }
                if (cm.getPlayer().getBossLogS("凡雷恩10次奖励") >= 1) {
                    cm.sendOk("你已经領取过奖励。");
                    cm.dispose();
                    return;
                }
                itemid = selection == 620 ? 1052299 : selection == 621 ? 1052300 : selection == 622 ? 1052301 : selection == 623 ? 1052302 : selection == 624 ? 1052303 : 0;
                cm.setBossLog("凡雷恩10次奖励");
                cm.getPlayer().modifyCSPoints(2, 666, true);
                cm.gainItem(itemid, 1);
                cm.sendOk("打王奖励获得666枫葉點數\r\n#z" + itemid + "#x1");
                cm.dispose();
                return;
            }
            case 64:
            {
                if (cm.getPlayer().getBossLogS("凡雷恩攻打次數") < 30) {
                    cm.sendOk("你的打王次數不够。");
                    cm.dispose();
                    return;
                }
                if (cm.getPlayer().getBossLogS("凡雷恩30次奖励") >= 1) {
                    cm.sendOk("你已经領取过奖励。");
                    cm.dispose();
                    return;
                }

                itemid = selection == 640 ? 3015015 : selection == 641 ? 3015016 : selection == 642 ? 3015017 : selection == 643 ? 3015018 : selection == 644 ? 3015019 : selection == 645 ? 3015020 : selection == 646 ? 3015021 : selection == 647 ? 3015022 : selection == 648 ? 3015023 : selection == 649 ? 3015024 : selection == 650 ? 3015025 : selection == 651 ? 3015026 : 0;
                cm.setBossLog("凡雷恩30次奖励");
                cm.gainItem(itemid, 1);
                cm.sendOk("打王奖励获得 \r\n#z" + itemid + "#x1");
                cm.dispose();
                return;
            }
        }
    }
}
