

var status = -1;
var sel;
var mod;
var moda;
function start() {
    if (cm.getPlayer().getGuildId() <= 0) {
        cm.sendOk("你沒有公会。");
        cm.dispose();
        return;
    }
    if (cm.getPlayer().getGuildslogCout(cm.getPlayer().getGuildId()) != 1) {
        cm.getPlayer().setGuildslog(cm.getPlayer().getGuildId(), 0);
    }
    if (cm.getPlayer().getGuildschrlogCout(cm.getPlayer().getId()) != 1) {
        cm.getPlayer().setGuildschrlog(0);
    }

    var sg = cm.getPlayer().getGuildId() <= 0 ? "你目前沒有公会。" : "你目前所在公会：#r" + cm.getGuild(cm.getPlayer().getGuildId()).getName() + "#k";
    cm.sendSimple("我是公会系統服務员，有什么可以为你服務？\r\n\r\n" + "#b" + sg + "#k  \r\n\r\n#b当前公会等級：#r" + cm.getPlayer().getGuildsloglevel(cm.getPlayer().getGuildId()) + "#k#k\r\n\r\n#b当前个人公会積分：#r" + cm.getPlayer().getGuildschrlog(cm.getPlayer().getId()) + "#k#k\r\n\r\n#b当前公会積分：#r" + cm.getPlayer().getGuildslog(cm.getPlayer().getGuildId()) + "#k#k\r\n\r\n#b#L0#公会每日奖励#l#k#k\r\n\r\n#b#L1#積分兌換#l#k#k\r\n\r\n#b#L2#積分捐贈#l#k#k\r\n\r\n#b#L3#公会升級#l#k#k\r\n\r\n#b#L4#公会任務系統#l#k#k\r\n\r\n#b#L5#公会商店#l#k#k\r\n\r\n  ");
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
            cm.sendSimple("#e#r你好，我是公会登入奖励領取员。#k \r\n\r\n#b#L0#每日簽到(100枫葉點數+10萬金币)#l");



        } else if (sel == 1) {
            cm.sendSimple("#e#r你好，我是公会積分兌換员。#k \r\n\r\n#b#L10#積分兌換(#z4310002#x1兌換5點公会个人積分)#l\r\n#b#L11#積分兌換(#z4310002#x5兌換25點公会个人積分)#l\r\n#b#L12#積分兌換(#z4310002#x10兌換50點公会个人積分)#l\r\n#b#L13#積分兌換(#z4310002#x50兌換250點公会个人積分)#l\r\n#b#L14#積分兌換(#z4310002#x100兌換500點公会个人積分)#l");

        } else if (sel == 2) {
            cm.sendSimple("#e#r你好，我是公会積分捐贈员。#k \r\n\r\n#b#L22#捐贈5積分#l\r\n\r\n#b#L23#捐贈10積分#l\r\n\r\n#b#L24#捐贈50積分#l\r\n\r\n#b#L25#捐贈100積分#l\r\n\r\n#b#L26#捐贈500積分#l\r\n\r\n#b#L27#捐贈1000積分#l");

        } else if (sel == 3) {
            cm.sendSimple("#e#r你好，我是公会升級员。#k \r\n\r\n" +
                    "#b2等工会1000分+5000萬金币\r\n" +
                    "3等工会5000分+1億金币\r\n" +
                    "4等工会10000分+5億金币\r\n" +
                    "5等工会15000分+10億金币\r\n" +
                    "6等工会20000分+12億金币\r\n" +
                    "7等工会25000分+14億金币\r\n" +
                    "8等工会30000分+16億金币\r\n" +
                    "9等工会40000分+18億金币\r\n" +
                    "10等工会50000分+20億金币#l\r\n" +
                    "#b#L3#升級公会#l");

        } else if (sel == 4) {
            cm.sendSimple("#e#r你好，我是公会任務系統人员。#k \r\n\r\n" +
                    "是否要进入挑战公会boss？每天可免費入场一次，第二场开始100點后面*2倍进入。#l\r\n" +
                    "#b#L4#进入挑战#l");
        } else if (sel == 5) {
            cm.sendSimple("#e你好，我是公公会商店管理人员。#k \r\n\r\n" +
                    "#r#L50#1等可购买:#k\r\n" +
                    "#b#i2001505#x300  个人公会積分:10分#l#k\r\n\r\n" +
                    "#r#L51#2等可购买:#k\r\n" +
                    "#b#i5062000#x5，#z3015758# 个人公会積分:50分#l#k\r\n\r\n" +
                    "#r#L52#3等可购买:#k\r\n" +
                    "#b#i5220000#x10 个人公会積分:50分#l#k\r\n\r\n" +
                    "#r#L53#4等可购买:#k\r\n" +
                    "#b#i2049301#x3 个人公会積分:50分#l#k\r\n\r\n" +
                    "#r#L54#5等可购买:#k\r\n" +
                    "#b#i5062001#x1 个人公会積分:50分#l#k\r\n\r\n" +
                    "#r#L55#6等可购买:#k\r\n" +
                    "#b#i2049100#x1 个人公会積分:50分#l#k\r\n\r\n" +
                    "#r#L56#7等可购买:#k\r\n" +
                    "#b#i4031408#x3 个人公会積分:100分#l#k\r\n\r\n" +
                    "#r#L57#8等可购买:#k\r\n" +
                    "#b#i2450000#x1 个人公会積分:100分#l#k\r\n\r\n" +
                    "#r#L58#9等可购买:#k\r\n" +
                    "#b#i2049300#x1 个人公会積分:100分#l#k\r\n\r\n" +
                    "#r#L59#10等可购买:#k\r\n" +
                    "#b#i1142855#x1(7天)个人公会積分:100分#l#k#l\r\n" +
                    "\r\n ");
        }

    } else if (status == 1) {
        if (sel == 0) {
            if (cm.getPlayer().getGuildId() <= 0) {
                cm.sendOk("你沒有公会。");
                cm.dispose();
                return;
            }
            if (cm.getPlayer().getLevel() < 70) {
                cm.sendOk("你的等級不足70等。");
                cm.dispose();
                return;
            }
            if (cm.getPlayer().getBossLogD("公会簽到") >= 1) {
                cm.sendOk("你今天已经簽到过了");
                cm.dispose();
                return;
            }
            cm.getPlayer().setBossLog("公会簽到");
            cm.getPlayer().modifyCSPoints(2, 100, true);
            cm.gainMeso(100000);
            cm.sendOk("領取成功。");
            cm.dispose();
            return;

        } else if (sel == 1) {
            if (cm.getPlayer().getGuildId() <= 0) {
                cm.sendOk("你沒有公会。");
                cm.dispose();
                return;
            }


            if (selection == 10) {
                if (!cm.haveItem(4310002, 1)) {
                    cm.sendOk("需要#z4310002#x1个。");
                    cm.dispose();
                    return;
                }
                cm.gainItem(4310002, -1);
                cm.getPlayer().updateGuildschrlog(cm.getPlayer().getId(), cm.getPlayer().getGuildschrlog(cm.getPlayer().getId()) + 5);
                cm.sendOk("兌換成功。");
                cm.dispose();
                return;
            }
            if (selection == 11) {
                if (!cm.haveItem(4310002, 5)) {
                    cm.sendOk("需要#z4310002#x1个。");
                    cm.dispose();
                    return;
                }
                cm.gainItem(4310002, -5);
                cm.getPlayer().updateGuildschrlog(cm.getPlayer().getId(), cm.getPlayer().getGuildschrlog(cm.getPlayer().getId()) + 25);
                cm.sendOk("兌換成功。");
                cm.dispose();
                return;
            }
            if (selection == 12) {
                if (!cm.haveItem(4310002, 10)) {
                    cm.sendOk("需要#z4310002#x1个。");
                    cm.dispose();
                    return;
                }
                cm.gainItem(4310002, -10);
                cm.getPlayer().updateGuildschrlog(cm.getPlayer().getId(), cm.getPlayer().getGuildschrlog(cm.getPlayer().getId()) + 50);
                cm.sendOk("兌換成功。");
                cm.dispose();
                return;
            }
            if (selection == 13) {
                if (!cm.haveItem(4310002, 50)) {
                    cm.sendOk("需要#z4310002#x1个。");
                    cm.dispose();
                    return;
                }
                cm.gainItem(4310002, -50);
                cm.getPlayer().updateGuildschrlog(cm.getPlayer().getId(), cm.getPlayer().getGuildschrlog(cm.getPlayer().getId()) + 250);
                cm.sendOk("兌換成功。");
                cm.dispose();
                return;
            }
            if (selection == 14) {
                if (!cm.haveItem(4310002, 100)) {
                    cm.sendOk("需要#z4310002#x1个。");
                    cm.dispose();
                    return;
                }
                cm.gainItem(4310002, -100);
                cm.getPlayer().updateGuildschrlog(cm.getPlayer().getId(), cm.getPlayer().getGuildschrlog(cm.getPlayer().getId()) + 500);
                cm.sendOk("兌換成功。");
                cm.dispose();
                return;
            }
            cm.sendOk("发生未知错誤。");
            cm.dispose();
            return;
        } else if (selection == 22 || selection == 23 || selection == 24 || selection == 25 || selection == 26 || selection == 27) {
            if (cm.getPlayer().getGuildId() <= 0) {
                cm.sendOk("你沒有公会。");
                cm.dispose();
                return;
            }
            if (selection == 22) {
                moda = 5;
            } else if (selection == 23) {
                moda = 10;
            } else if (selection == 24) {
                moda = 50;
            } else if (selection == 25) {
                moda = 100;
            } else if (selection == 26) {
                moda = 500;
            } else if (selection == 27) {
                moda = 1000;
            } else {
                cm.sendOk("发生未知错誤1。");
                cm.dispose();
                return;
            }
            if (cm.getPlayer().getGuildschrlog(cm.getPlayer().getId()) < moda) {
                cm.sendOk("你的公会个人積分不足。");
                cm.dispose();
                return;
            }
            cm.getPlayer().updateGuildschrlog(cm.getPlayer().getId(), cm.getPlayer().getGuildschrlog(cm.getPlayer().getId()) - moda);
            cm.getPlayer().updateGuildslog(cm.getPlayer().getGuildId(), cm.getPlayer().getGuildslog(cm.getPlayer().getGuildId()) + moda);

            cm.sendOk("捐贈成功。");
            cm.dispose();
            return;
        } else if (sel == 3) {
            if (cm.getPlayer().getGuildId() <= 0) {
                cm.sendOk("你沒有公会。");
                cm.dispose();
                return;
            }
            if (cm.getPlayer().getGuildsloglevel(cm.getPlayer().getGuildId()) < 1) {
                cm.sendOk("发生未知错誤2。");
                cm.dispose();
                return;
            }
            if (cm.getPlayer().getGuildsloglevel(cm.getPlayer().getGuildId()) == 1) {
                if (cm.getPlayer().getGuildslog(cm.getPlayer().getGuildId()) < 1000) {
                    cm.sendOk("公会積分不足。");
                    cm.dispose();
                    return;
                }
                if (cm.getMeso() < 50000000) {
                    cm.sendOk("金币不足。");
                    cm.dispose();
                    return;
                }
                cm.gainMeso(-50000000);
                cm.getPlayer().updateGuildslog(cm.getPlayer().getGuildId(), cm.getPlayer().getGuildslog(cm.getPlayer().getGuildId()) - 1000);
                cm.getPlayer().updateGuildsloglevel(cm.getPlayer().getGuildId(), cm.getPlayer().getGuildsloglevel(cm.getPlayer().getGuildId()) + 1);
                cm.sendOk("公会升級完成");
                cm.dispose();
                return;
            }
            if (cm.getPlayer().getGuildsloglevel(cm.getPlayer().getGuildId()) == 2) {
                if (cm.getPlayer().getGuildslog(cm.getPlayer().getGuildId()) < 5000) {
                    cm.sendOk("公会積分不足。");
                    cm.dispose();
                    return;
                }
                if (cm.getMeso() < 100000000) {
                    cm.sendOk("金币不足。");
                    cm.dispose();
                    return;
                }
                cm.gainMeso(-100000000);
                cm.getPlayer().updateGuildslog(cm.getPlayer().getGuildId(), cm.getPlayer().getGuildslog(cm.getPlayer().getGuildId()) - 5000);
                cm.getPlayer().updateGuildsloglevel(cm.getPlayer().getGuildId(), cm.getPlayer().getGuildsloglevel(cm.getPlayer().getGuildId()) + 1);
                cm.sendOk("公会升級完成");
                cm.dispose();
                return;
            }
            if (cm.getPlayer().getGuildsloglevel(cm.getPlayer().getGuildId()) == 3) {
                if (cm.getPlayer().getGuildslog(cm.getPlayer().getGuildId()) < 10000) {
                    cm.sendOk("公会積分不足。");
                    cm.dispose();
                    return;
                }
                if (cm.getMeso() < 500000000) {
                    cm.sendOk("金币不足。");
                    cm.dispose();
                    return;
                }
                cm.gainMeso(-500000000);
                cm.getPlayer().updateGuildslog(cm.getPlayer().getGuildId(), cm.getPlayer().getGuildslog(cm.getPlayer().getGuildId()) - 10000);
                cm.getPlayer().updateGuildsloglevel(cm.getPlayer().getGuildId(), cm.getPlayer().getGuildsloglevel(cm.getPlayer().getGuildId()) + 1);
                cm.sendOk("公会升級完成");
                cm.dispose();
                return;
            }
            if (cm.getPlayer().getGuildsloglevel(cm.getPlayer().getGuildId()) == 4) {
                if (cm.getPlayer().getGuildslog(cm.getPlayer().getGuildId()) < 15000) {
                    cm.sendOk("公会積分不足。");
                    cm.dispose();
                    return;
                }
                if (cm.getMeso() < 1000000000) {
                    cm.sendOk("金币不足。");
                    cm.dispose();
                    return;
                }
                cm.gainMeso(-1000000000);
                cm.getPlayer().updateGuildslog(cm.getPlayer().getGuildId(), cm.getPlayer().getGuildslog(cm.getPlayer().getGuildId()) - 15000);
                cm.getPlayer().updateGuildsloglevel(cm.getPlayer().getGuildId(), cm.getPlayer().getGuildsloglevel(cm.getPlayer().getGuildId()) + 1);
                cm.sendOk("公会升級完成");
                cm.dispose();
                return;
            }
            if (cm.getPlayer().getGuildsloglevel(cm.getPlayer().getGuildId()) == 5) {
                if (cm.getPlayer().getGuildslog(cm.getPlayer().getGuildId()) < 20000) {
                    cm.sendOk("公会積分不足。");
                    cm.dispose();
                    return;
                }
                if (cm.getMeso() < 1200000000) {
                    cm.sendOk("金币不足。");
                    cm.dispose();
                    return;
                }
                cm.gainMeso(-1200000000);
                cm.getPlayer().updateGuildslog(cm.getPlayer().getGuildId(), cm.getPlayer().getGuildslog(cm.getPlayer().getGuildId()) - 20000);
                cm.getPlayer().updateGuildsloglevel(cm.getPlayer().getGuildId(), cm.getPlayer().getGuildsloglevel(cm.getPlayer().getGuildId()) + 1);
                cm.sendOk("公会升級完成");
                cm.dispose();
                return;
            }
            if (cm.getPlayer().getGuildsloglevel(cm.getPlayer().getGuildId()) == 6) {
                if (cm.getPlayer().getGuildslog(cm.getPlayer().getGuildId()) < 25000) {
                    cm.sendOk("公会積分不足。");
                    cm.dispose();
                    return;
                }
                if (cm.getMeso() < 1400000000) {
                    cm.sendOk("金币不足。");
                    cm.dispose();
                    return;
                }
                cm.gainMeso(-1400000000);
                cm.getPlayer().updateGuildslog(cm.getPlayer().getGuildId(), cm.getPlayer().getGuildslog(cm.getPlayer().getGuildId()) - 25000);
                cm.getPlayer().updateGuildsloglevel(cm.getPlayer().getGuildId(), cm.getPlayer().getGuildsloglevel(cm.getPlayer().getGuildId()) + 1);
                cm.sendOk("公会升級完成");
                cm.dispose();
                return;
            }
            if (cm.getPlayer().getGuildsloglevel(cm.getPlayer().getGuildId()) == 7) {
                if (cm.getPlayer().getGuildslog(cm.getPlayer().getGuildId()) < 30000) {
                    cm.sendOk("公会積分不足。");
                    cm.dispose();
                    return;
                }
                if (cm.getMeso() < 1600000000) {
                    cm.sendOk("金币不足。");
                    cm.dispose();
                    return;
                }
                cm.gainMeso(-1600000000);
                cm.getPlayer().updateGuildslog(cm.getPlayer().getGuildId(), cm.getPlayer().getGuildslog(cm.getPlayer().getGuildId()) - 30000);
                cm.getPlayer().updateGuildsloglevel(cm.getPlayer().getGuildId(), cm.getPlayer().getGuildsloglevel(cm.getPlayer().getGuildId()) + 1);
                cm.sendOk("公会升級完成");
                cm.dispose();
                return;
            }
            if (cm.getPlayer().getGuildsloglevel(cm.getPlayer().getGuildId()) == 8) {
                if (cm.getPlayer().getGuildslog(cm.getPlayer().getGuildId()) < 40000) {
                    cm.sendOk("公会積分不足。");
                    cm.dispose();
                    return;
                }
                if (cm.getMeso() < 1800000000) {
                    cm.sendOk("金币不足。");
                    cm.dispose();
                    return;
                }
                cm.gainMeso(-1800000000);
                cm.getPlayer().updateGuildslog(cm.getPlayer().getGuildId(), cm.getPlayer().getGuildslog(cm.getPlayer().getGuildId()) - 40000);
                cm.getPlayer().updateGuildsloglevel(cm.getPlayer().getGuildId(), cm.getPlayer().getGuildsloglevel(cm.getPlayer().getGuildId()) + 1);
                cm.sendOk("公会升級完成");
                cm.dispose();
                return;
            }
            if (cm.getPlayer().getGuildsloglevel(cm.getPlayer().getGuildId()) == 9) {
                if (cm.getPlayer().getGuildslog(cm.getPlayer().getGuildId()) < 50000) {
                    cm.sendOk("公会積分不足。");
                    cm.dispose();
                    return;
                }
                if (cm.getMeso() < 2000000000) {
                    cm.sendOk("金币不足。");
                    cm.dispose();
                    return;
                }
                cm.gainMeso(-2000000000);
                cm.getPlayer().updateGuildslog(cm.getPlayer().getGuildId(), cm.getPlayer().getGuildslog(cm.getPlayer().getGuildId()) - 50000);
                cm.getPlayer().updateGuildsloglevel(cm.getPlayer().getGuildId(), cm.getPlayer().getGuildsloglevel(cm.getPlayer().getGuildId()) + 1);
                cm.sendOk("公会升級完成");
                cm.dispose();
                return;
            }
            cm.sendOk("当前公会已经达到最高等級。");
            cm.dispose();
            return;
        } else if (sel == 4) {
            if (cm.getPlayer().getGuildId() <= 0) {
                cm.sendOk("你沒有公会。");
                cm.dispose();
                return;
            }
            if (cm.getPlayerCount(999990000) >= 1) {
                cm.sendOk("已有人在挑战。");
                cm.dispose();
                return;
            }
            if (cm.getPlayer().getBossLogD("公会任務系統") < 1) {
                cm.getPlayer().setBossLog("公会任務系統");
            } else {
                //if (cm.getMeso() < 500000) {
                //    cm.sendOk("金币不足。");
                //    cm.dispose();
                //    return;
                //}
                var zs = cm.getPlayer().getBossLogD("公会任務次數");
                var zz = 100;
                if (zs > 1) {
                    for (var i = 1; i < zs; i++) {
                        zz *= 2;

                    }
                }
                if (cm.getPlayer().getCSPoints(1) < zz) {
                    cm.sendOk("你的Gash不足" + zz + "。");
                    cm.dispose();
                    return;
                }
                //cm.gainMeso(-500000);
                cm.getPlayer().modifyCSPoints(1, -zz, true);
            }
            cm.getPlayer().setBossLog("公会任務次數");
            var map = cm.getMap(999990000);
            map.resetFully();
            map.spawnNpc(9000048, new java.awt.Point(299, 1263));
            var mob = [8220032, 8220033, 8220034, 8220035, 8220036];
            var mob_new = cm.getRandom(mob);
            cm.spawnMobOnMap(mob_new, 1, -37, 1263, 999990000);
            cm.warp(999990000);
            cm.dispose();
            return;

        } else if (sel == 5) {
            if (cm.getPlayer().getGuildId() <= 0) {
                cm.sendOk("你沒有公会。");
                cm.dispose();
                return;
            }

            if (!cm.canHoldByType(1, 1)) {
                cm.sendOk("请确认背包是否已经满了。");
                cm.dispose();
                return;
            }
            if (!cm.canHoldByType(2, 1)) {
                cm.sendOk("请确认背包是否已经满了。");
                cm.dispose();
                return;
            }
            if (!cm.canHoldByType(3, 1)) {
                cm.sendOk("请确认背包是否已经满了。");
                cm.dispose();
                return;
            }
            if (!cm.canHoldByType(4, 1)) {
                cm.sendOk("请确认背包是否已经满了。");
                cm.dispose();
                return;
            }
            if (!cm.canHoldByType(5, 2)) {
                cm.sendOk("请确认背包是否已经满了。");
                cm.dispose();
                return;
            }
            if (selection == 50) {
                if (cm.getPlayer().getGuildsloglevel(cm.getPlayer().getGuildId()) >= 1) {
                    if (cm.getPlayer().getGuildschrlog(cm.getPlayer().getId()) < 10) {
                        cm.sendOk("你的公会个人積分不足。");
                        cm.dispose();
                        return;
                    }
                    if (cm.getPlayer().getBossLogD("公会商店1") >= 1) {
                        cm.sendOk("今天你已经购买过了，请明天再來。");
                        cm.dispose();
                        return;
                    }
                    cm.getPlayer().setBossLog("公会商店1");
                    cm.getPlayer().updateGuildschrlog(cm.getPlayer().getId(), cm.getPlayer().getGuildschrlog(cm.getPlayer().getId()) - 10);
                    cm.gainItem(2001505, 300);
                    cm.sendOk("购买成功。");
                    cm.dispose();
                    return;

                } else {
                    cm.sendOk("公会等級不足。");
                    cm.dispose();
                    return;
                }
            }

            if (selection == 51) {
                if (cm.getPlayer().getGuildsloglevel(cm.getPlayer().getGuildId()) >= 2) {
                    if (cm.getPlayer().getGuildschrlog(cm.getPlayer().getId()) < 50) {
                        cm.sendOk("你的公会个人積分不足。");
                        cm.dispose();
                        return;
                    }
                    if (cm.getPlayer().getBossLogD("公会商店2") >= 1) {
                        cm.sendOk("今天你已经购买过了，请明天再來。");
                        cm.dispose();
                        return;
                    }
                    cm.getPlayer().setBossLog("公会商店2");
                    cm.getPlayer().updateGuildschrlog(cm.getPlayer().getId(), cm.getPlayer().getGuildschrlog(cm.getPlayer().getId()) - 50);
                    cm.gainItem(5062000, 5);
                    cm.gainItem(3015758, 1);
                    cm.sendOk("购买成功。");
                    cm.dispose();
                    return;
                } else {
                    cm.sendOk("公会等級不足。");
                    cm.dispose();
                    return;
                }
            }

            if (selection == 52) {
                if (cm.getPlayer().getGuildsloglevel(cm.getPlayer().getGuildId()) >= 3) {
                    if (cm.getPlayer().getGuildschrlog(cm.getPlayer().getId()) < 50) {
                        cm.sendOk("你的公会个人積分不足。");
                        cm.dispose();
                        return;
                    }
                    if (cm.getPlayer().getBossLogD("公会商店3") >= 1) {
                        cm.sendOk("今天你已经购买过了，请明天再來。");
                        cm.dispose();
                        return;
                    }
                    cm.getPlayer().setBossLog("公会商店3");
                    cm.getPlayer().updateGuildschrlog(cm.getPlayer().getId(), cm.getPlayer().getGuildschrlog(cm.getPlayer().getId()) - 50);
                    cm.gainItem(5220000, 10);
                    cm.sendOk("购买成功。");
                    cm.dispose();
                    return;

                } else {
                    cm.sendOk("公会等級不足。");
                    cm.dispose();
                    return;
                }
            }

            if (selection == 53) {
                if (cm.getPlayer().getGuildsloglevel(cm.getPlayer().getGuildId()) >= 4) {
                    if (cm.getPlayer().getGuildschrlog(cm.getPlayer().getId()) < 50) {
                        cm.sendOk("你的公会个人積分不足。");
                        cm.dispose();
                        return;
                    }
                    if (cm.getPlayer().getBossLogD("公会商店4") >= 1) {
                        cm.sendOk("今天你已经购买过了，请明天再來。");
                        cm.dispose();
                        return;
                    }
                    cm.getPlayer().setBossLog("公会商店4");
                    cm.getPlayer().updateGuildschrlog(cm.getPlayer().getId(), cm.getPlayer().getGuildschrlog(cm.getPlayer().getId()) - 50);
                    cm.gainItem(2049301, 3);
                    cm.sendOk("购买成功。");
                    cm.dispose();
                    return;
                } else {
                    cm.sendOk("公会等級不足。");
                    cm.dispose();
                    return;
                }
            }

            if (selection == 54) {
                if (cm.getPlayer().getGuildsloglevel(cm.getPlayer().getGuildId()) >= 5) {
                    if (cm.getPlayer().getGuildschrlog(cm.getPlayer().getId()) < 50) {
                        cm.sendOk("你的公会个人積分不足。");
                        cm.dispose();
                        return;
                    }
                    if (cm.getPlayer().getBossLogD("公会商店5") >= 1) {
                        cm.sendOk("今天你已经购买过了，请明天再來。");
                        cm.dispose();
                        return;
                    }

                    cm.getPlayer().setBossLog("公会商店5");
                    cm.getPlayer().updateGuildschrlog(cm.getPlayer().getId(), cm.getPlayer().getGuildschrlog(cm.getPlayer().getId()) - 50);
                    cm.gainItem(5062001, 1);
                    cm.sendOk("购买成功。");
                    cm.dispose();
                    return;
                } else {
                    cm.sendOk("公会等級不足。");
                    cm.dispose();
                    return;
                }
            }

            if (selection == 55) {
                if (cm.getPlayer().getGuildsloglevel(cm.getPlayer().getGuildId()) >= 6) {
                    if (cm.getPlayer().getGuildschrlog(cm.getPlayer().getId()) < 50) {
                        cm.sendOk("你的公会个人積分不足。");
                        cm.dispose();
                        return;
                    }
                    if (cm.getPlayer().getBossLogD("公会商店6") >= 1) {
                        cm.sendOk("今天你已经购买过了，请明天再來。");
                        cm.dispose();
                        return;
                    }
                    cm.getPlayer().setBossLog("公会商店6");
                    cm.getPlayer().updateGuildschrlog(cm.getPlayer().getId(), cm.getPlayer().getGuildschrlog(cm.getPlayer().getId()) - 50);
                    cm.gainItem(2049100, 1);
                    cm.sendOk("购买成功。");
                    cm.dispose();
                    return;
                } else {
                    cm.sendOk("公会等級不足。");
                    cm.dispose();
                    return;
                }
            }

            if (selection == 56) {
                if (cm.getPlayer().getGuildsloglevel(cm.getPlayer().getGuildId()) >= 7) {
                    if (cm.getPlayer().getGuildschrlog(cm.getPlayer().getId()) < 100) {
                        cm.sendOk("你的公会个人積分不足。");
                        cm.dispose();
                        return;
                    }
                    if (cm.getPlayer().getBossLogD("公会商店7") >= 1) {
                        cm.sendOk("今天你已经购买过了，请明天再來。");
                        cm.dispose();
                        return;
                    }
                    cm.getPlayer().setBossLog("公会商店7");
                    cm.getPlayer().updateGuildschrlog(cm.getPlayer().getId(), cm.getPlayer().getGuildschrlog(cm.getPlayer().getId()) - 100);
                    cm.gainItem(4031408, 3);
                    cm.sendOk("购买成功。");
                    cm.dispose();
                    return;
                } else {
                    cm.sendOk("公会等級不足。");
                    cm.dispose();
                    return;
                }
            }

            if (selection == 57) {
                if (cm.getPlayer().getGuildsloglevel(cm.getPlayer().getGuildId()) >= 8) {
                    if (cm.getPlayer().getGuildschrlog(cm.getPlayer().getId()) < 100) {
                        cm.sendOk("你的公会个人積分不足。");
                        cm.dispose();
                        return;
                    }
                    if (cm.getPlayer().getBossLogD("公会商店8") >= 1) {
                        cm.sendOk("今天你已经购买过了，请明天再來。");
                        cm.dispose();
                        return;
                    }
                    cm.getPlayer().setBossLog("公会商店8");
                    cm.getPlayer().updateGuildschrlog(cm.getPlayer().getId(), cm.getPlayer().getGuildschrlog(cm.getPlayer().getId()) - 100);
                    cm.gainItem(2450000, 1);
                    cm.sendOk("购买成功。");
                    cm.dispose();
                    return;
                } else {
                    cm.sendOk("公会等級不足。");
                    cm.dispose();
                    return;
                }
            }

            if (selection == 58) {
                if (cm.getPlayer().getGuildsloglevel(cm.getPlayer().getGuildId()) >= 9) {
                    if (cm.getPlayer().getGuildschrlog(cm.getPlayer().getId()) < 100) {
                        cm.sendOk("你的公会个人積分不足。");
                        cm.dispose();
                        return;
                    }
                    if (cm.getPlayer().getBossLogD("公会商店9") >= 1) {
                        cm.sendOk("今天你已经购买过了，请明天再來。");
                        cm.dispose();
                        return;
                    }
                    cm.getPlayer().setBossLog("公会商店9");
                    cm.getPlayer().updateGuildschrlog(cm.getPlayer().getId(), cm.getPlayer().getGuildschrlog(cm.getPlayer().getId()) - 100);
                    cm.gainItem(2049300, 1);
                    cm.sendOk("购买成功。");
                    cm.dispose();
                    return;
                } else {
                    cm.sendOk("公会等級不足。");
                    cm.dispose();
                    return;
                }
            }
            if (selection == 59) {
                if (cm.getPlayer().getGuildsloglevel(cm.getPlayer().getGuildId()) >= 10) {
                    if (cm.getPlayer().getGuildschrlog(cm.getPlayer().getId()) < 100) {
                        cm.sendOk("你的公会个人積分不足。");
                        cm.dispose();
                        return;
                    }
                    if (cm.getPlayer().getBossLogD("公会商店10") >= 1) {
                        cm.sendOk("今天你已经购买过了，请明天再來。");
                        cm.dispose();
                        return;
                    }
                    cm.getPlayer().setBossLog("公会商店10");
                    cm.getPlayer().updateGuildschrlog(cm.getPlayer().getId(), cm.getPlayer().getGuildschrlog(cm.getPlayer().getId()) - 100);
                    cm.gainItemPeriod(1142855, 1, 7);
                    cm.sendOk("购买成功。");
                    cm.dispose();
                    return;
                } else {
                    cm.sendOk("公会等級不足。");
                    cm.dispose();
                    return;
                }
                cm.sendOk("发生未知错誤3。");
                cm.dispose();
                return;
            }
        }
    }
}
