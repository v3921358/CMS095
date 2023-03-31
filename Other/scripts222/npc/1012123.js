var status = -1;
var beauty = 0;
var facenew = Array();
var hair_Colo_new1 = Array();
var hair_Colo_new2 = Array();
var changeFace = Array();
var skin = Array(0, 1, 2, 3, 4);
var mface = Array(
        20020, 20021, 20022, 20023, 20025, 20026, 20027, 20028, 20029, 20030,
		20031, 20032, 20033, 20035, 20036, 20043, 20044, 20045, 20046, 
		20047
	);
var fface = Array(
        21020, 21021, 21022, 21023, 21025, 21026, 21027, 21028, 21029, 21030,
		21031, 21033, 21034, 21035, 21041, 21042, 21043, 21044, 21045
        );
var mhair = Array(
		33000, 33030, 33050, 33070, 33080, 33090, 33100, 33110, 33120, 33130,
        33140, 33150, 33160, 33170, 33180, 33190, 33200, 33210, 33220, 33230,
        33240, 33250, 33260, 32120, 33300, 33310, 33320, 33340, 33350, 33360,
		33370, 33380, 33390, 33440, 33470, 33480, 30420, 30440, 30450, 30460, 
		30470, 30480, 30490, 30510, 30520, 30530, 30540, 30550, 30560, 30600,
        30610, 30620, 30630, 30640, 30650, 30660, 30670, 30680, 30700, 30710,
        30720, 30730, 30740, 30750, 30790, 30810, 30820, 30840, 30850, 30860,
        30870, 30880, 30890, 30900, 30910, 30920, 30930, 30940, 30950
        );
var mhair2 = Array(
		34000, 34010, 34040, 34050, 34060, 34070, 34090, 34100, 34110, 34120,
		34130, 34140, 34150, 34160, 34170, 34180, 34190, 34200, 34210, 34220,
        34230, 34240, 34250, 34260, 31450, 31460, 31470, 31480, 31490, 31510,
        31520, 31530, 31540, 31550, 31560, 31590, 31610, 31620, 31630, 31640,
        31650, 31670, 31680, 31690, 31710, 31720, 31730, 31770, 31990, 31950, 
        31790, 31800, 31820, 31830, 31840, 31850, 31860, 31870, 31880, 31890,
        31900, 31910, 31920, 31930, 31940
        );

function action(mode, type, selection) {
    if (mode == 0) {
        cm.dispose();
        return;
    } else {
        status++;
    }

    if (status == 0) {
        var android = cm.getPlayer().getAndroid();
        if (android == null) {
            cm.sendOk("对不起，你没有机器人，无法使用该功能。");
            cm.dispose();
            return;
        }
        //cm.sendSimple("嗨，我是#r智能机器人美容专家#k 如果你有 #b#t5150040##k， #b#t5151032##k, #b#t5152049##k或者有 #b#t5153013##k, 我就可以免费帮你的机器人弄好看的造型。 \r\n#L0#使用: #i5150040##t5150040#1#l\r\n#L4#使用: #i5150040##t5150040#2#l\r\n#L1#使用 #i5151032##t5151032##l\r\n#L2#使用 #i5152049##t5152049##l\r\n#L5#使用 #i5152049##t5152049#2#l\r\n" + /*"#L3#使用 #i5153013##t5153013##l\r\n"+*/"#L6#使用 #i5152049##t5152049#更换美瞳#l");
		cm.sendSimple("嗨，我是#r智能机器人美容专家#k 如果你有 #b#z5150040##k我就可以帮你的机器人弄好看的造型。 \r\n#L0##r皇家理发#k（男性头发）#l\r\n#L4##r皇家理发#k（女性头发）\r\n#L1##d更换发色#k #l\r\n#L2##g更换脸型#k（男性脸型）#l\r\n#L5##g更换脸型#k（女性脸型）\r\n#L6##d更换瞳色#k\r\n");
	} else if (status == 1) {
        if (selection == 0) {
            //var hair = cm.getPlayerStat("HAIR");
            hairnew = Array();
            beauty = 0;

            for (var i = 0; i < mhair.length; i++) {
                hairnew.push(mhair[i] + parseInt(cm.getPlayer().getAndroid().getHair() % 10));
            }
            //cm.sendYesNo("确定要使用 #b#t5150040##k 为机器人随机理发（男性头发）？？");
            cm.sendStyleAndroid("选择一个想要的.", hairnew);
        } else if (selection == 4) {
            //var hair2 = cm.getPlayerStat("HAIR");
            hairnew2 = Array();
            beauty = 4;
            for (var i = 0; i < mhair2.length; i++) {
                hairnew2.push(mhair2[i] + parseInt(cm.getPlayer().getAndroid().getHair() % 10));
            }
            //cm.sendYesNo("确定要使用 #b#t5150040##k 为机器人随机理发（女性头发）？？");
            cm.sendStyleAndroid("选择一个想要的.", hairnew2);

        } else if (selection == 1) {
            beauty = 1;
            haircolor = [cm.getPlayer().getAndroid().getHair()];
            //var current = cm.getPlayer().getAndroid().getHair() - parseInt(cm.getPlayer().getAndroid().getHair() % 10);
            var current = parseInt(cm.getPlayer().getAndroid().getHair() / 10) * 10;
			for (var i = 1; i < 9; i++) {
                haircolor[i] = current + i - 1;
            }
            //cm.sendYesNo("确定要使用 #b#t5150040##k 为机器人随机染发？？");
			cm.sendStyleAndroid("选择一个想要的.", haircolor);
            //cm.sendSimple("复隅猁妏蚚 #b#t5151032##k ?賸ˋ \r\n#L100#洼伎#l\r\n#L101#厂伎#l\r\n#L102#傀伎#l\r\n#L103#伎#l\r\n#L104#郑伎#l\r\n#L105#芅伎#l\r\n#L106#豜伎#l\r\n#L107#福伎#l");
        } else if (selection == 2) {
            beauty = 2;
            facenew1 = Array();
            for (var i = 0; i < mface.length; i++) {
                facenew1.push(mface[i]);
            }

            //cm.sendYesNo("确定要使用 #b#t5150040##k 为机器人随机整形（男性脸型）？？");
            cm.sendStyleAndroid("让我看看选择一个想要的..", facenew1);
        } else if (selection == 5) {
            beauty = 5;
            facenew2 = Array();
            for (var i = 0; i < fface.length; i++) {
                facenew2.push(fface[i]);
            }

            //cm.sendYesNo("确定要使用 #b#t5150040##k 为机器人随机整形（女性脸型）？？");
            cm.sendStyleAndroid("让我看看选择一个想要的..", facenew2);
        } else if (selection == 3) {
            beauty = 3;
            cm.sendStyleAndroid("选一个想要的风格.", skin);
        } else if (selection == 6) {
            beauty = 6;
            var currenta = cm.getPlayer().getAndroid().getFace()
            var current = 0;
            if (currenta % 1000 < 100) {
                current = currenta;
            } else if ((currenta % 1000 >= 100) && (currenta % 1000 < 200)) {
                current = currenta - 100;
            } else if ((currenta % 1000 >= 200) && (currenta % 1000 < 300)) {
                current = currenta - 200;
            } else if ((currenta % 1000 >= 300) && (currenta % 1000 < 400)) {
                current = currenta - 300;
            } else if ((currenta % 1000 >= 400) && (currenta % 1000 < 500)) {
                current = currenta - 400;
            } else if ((currenta % 1000 >= 500) && (currenta % 1000 < 600)) {
                current = currenta - 500;
            } else if ((currenta % 1000 >= 600) && (currenta % 1000 < 700)) {
                current = currenta - 600;
            } else if ((currenta % 1000 >= 700) && (currenta % 1000 < 800)) {
                current = currenta - 700;
            }
            for (var i = 0; i < 8; i++) {
                changeFace[i] = i * 100 + current;
            }
			//cm.sendYesNo("确定要使用 #b#t5150040##k 为机器人更换瞳色？？");
            cm.sendStyleAndroid("选择一个想要的", changeFace);
        }

    } else if (status == 2) {
        if (beauty == 0) {
            //if (cm.setRandomAvatar(5150040, hair_Colo_new) == 1) {
            //    cm.sendOk("砅忳ㄐ");
            //} else {
            //    cm.sendOk("砧.... 簷侔衄#b#t5150040##k﹝");
            // }
            if (cm.haveItem(5150040)) {
                cm.gainItem(5150040, -1);
                cm.setAndroid(hairnew[selection]);
                cm.sendOk("享受!");
                cm.dispose();
            } else {
                cm.sendOk("您貌似没有#b#t5150040##k..");
                cm.dispose();
            }
        }
        if (beauty == 4) {
            //if (cm.setRandomAvatar(5150040, hair_Colo_new) == 1) {
            //    cm.sendOk("砅忳ㄐ");
            //} else {
            //    cm.sendOk("砧.... 簷侔衄#b#t5150040##k﹝");
            // }
            if (cm.haveItem(5150040)) {
                cm.gainItem(5150040, -1);
                cm.setAndroid(hairnew2[selection]);
                cm.sendOk("享受!");
                cm.dispose();
				return;
            } else {
                cm.sendOk("您貌似没有#b#v5150040##k..");
                cm.dispose();
				return;
            }
        }
        if (beauty == 1) {

            if (cm.haveItem(5150040)) {
                cm.gainItem(5150040, -1);
                cm.setAndroid(haircolor[selection]);
                cm.sendOk("享受!");
            } else {
                cm.sendOk("您貌似没有#b#t5150040##k..");
            }

            /*var currenthaircolo = cm.getDoubleFloor((cm.getPlayerStat("HAIR") / 10)) * 10;
             var hair_Colo_ne;
             if (selection == 100) {
             hair_Colo_ne = currenthaircolo;
             } else if (selection == 101) {
             hair_Colo_ne = currenthaircolo + 1;
             } else if (selection == 102) {
             hair_Colo_ne = currenthaircolo + 2;
             } else if (selection == 103) {
             hair_Colo_ne = currenthaircolo + 3;
             } else if (selection == 104) {
             hair_Colo_ne = currenthaircolo + 4;
             } else if (selection == 105) {
             hair_Colo_ne = currenthaircolo + 5;
             } else if (selection == 106) {
             hair_Colo_ne = currenthaircolo + 6;
             } else if (selection == 107) {
             hair_Colo_ne = currenthaircolo + 7;
             } else {
             hair_Colo_ne = currenthaircolo;
             }
             
             if (cm.setAvatar(5151032, hair_Colo_ne) == 1) {
             cm.sendOk("砅忳ㄐ");
             } else {
             cm.sendOk("砧.... 簷侔衄#b#t5151032##k﹝");
             }*/
        }
        if (beauty == 2) {
            if (cm.haveItem(5150040)) {
                cm.gainItem(5150040, -1);
                //cm.setFace(facenew[cm.getDoubleFloor(cm.getDoubleRandom() * facenew.length)]);
                cm.setAndroid(facenew1[selection]);
                cm.sendOk("享受");
            } else {
                cm.sendOk("您貌似没有#b#t5150040##k..");
            }
        }
        if (beauty == 5) {
            if (cm.haveItem(5150040)) {
                cm.gainItem(5150040, -1);
                //cm.setFace(facenew[cm.getDoubleFloor(cm.getDoubleRandom() * facenew.length)]);
                cm.setAndroid(facenew2[selection]);
                cm.sendOk("享受");
            } else {
                cm.sendOk("您貌似没有#b#t5150040##k..");
            }
        }
        if (beauty == 3) {
            if (cm.haveItem(5150040)) {
                cm.gainItem(5150040, -1);
                cm.setAndroid(selection);
                cm.sendOk("享受!");
            } else {
                cm.sendOk("您貌似没有#b#t5150040##k..");
                cm.dispose();
            }
        }
        if (beauty == 6) {

            if (cm.haveItem(5150040)) {
                cm.gainItem(5150040, -1);
                cm.setAndroid(changeFace[selection]);
                cm.sendOk("享受!");
            } else {
                cm.sendOk("您貌似没有#b#t5150040##k..");
            }

            /*var currenthaircolo = cm.getDoubleFloor((cm.getPlayerStat("HAIR") / 10)) * 10;
             var hair_Colo_ne;
             if (selection == 100) {
             hair_Colo_ne = currenthaircolo;
             } else if (selection == 101) {
             hair_Colo_ne = currenthaircolo + 1;
             } else if (selection == 102) {
             hair_Colo_ne = currenthaircolo + 2;
             } else if (selection == 103) {
             hair_Colo_ne = currenthaircolo + 3;
             } else if (selection == 104) {
             hair_Colo_ne = currenthaircolo + 4;
             } else if (selection == 105) {
             hair_Colo_ne = currenthaircolo + 5;
             } else if (selection == 106) {
             hair_Colo_ne = currenthaircolo + 6;
             } else if (selection == 107) {
             hair_Colo_ne = currenthaircolo + 7;
             } else {
             hair_Colo_ne = currenthaircolo;
             }
             
             if (cm.setAvatar(5151032, hair_Colo_ne) == 1) {
             cm.sendOk("砅忳ㄐ");
             } else {
             cm.sendOk("砧.... 簷侔衄#b#t5151032##k﹝");
             }*/
        }
        cm.safeDispose();
    }
}