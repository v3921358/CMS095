var status = 0;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (status >= 0 && mode == 0) {
            cm.dispose();
            return;
        }
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 0) {
            cm.sendSimple("Are you one of the winners in the event? If so, you're entitled to exchange rewards with your #v4000038#!"
                    + "#k\r\n#L1#Trade 1 #v4000038# for 10 million mesos!"
                    + "#k\r\n#L2#Trade 2 #v4000038# for 10k NX"
                    + "#k\r\n#L3#Trade 3 #v4000038# for 5 #v2022179#"
                    + "#k\r\n#L4#Trade 4 #v4000038# for 2 #v2049301#"
                    + "#k\r\n#L5#Trade 5 #v4000038# for 5 #v4001473#"
                    + "#k\r\n#L6#Trade 6 #v4000038# for 12 #v2022179#"
                    + "#k\r\n#L7#Trade 7 #v4000038# for 4  #v2049301#"
                    + "#k\r\n#L8#Trade 8 #v4000038# for 1 #v2049300#"
                    + "#k\r\n#L9#Trade 9 #v4000038# for 10 #v4001473#"
                    + "#k\r\n#L10#Trade 10 #v4000038# for 5 #v2022121#"
                    + "#k\r\n#L11#Trade 15 #v4000038# for 2 #v2049300#"
                    + "#k\r\n#L12#Trade 20 #v4000038# for 1 #v5220013#"
                    + "#k\r\n#L13#Trade 25 #v4000038# for 5 #v4001017#"
                    + "#k\r\n#L14#Trade 30 #v4000038# for 5 #v2046318#"
                    + "#k\r\n#L15#Trade 35 #v4000038# for 1 #v4161042#"
                    + "#k\r\n#L16#Trade 40 #v4000038# for 1 #v1112927#"
                    + "#k\r\n#L17#Trade 45 #v4000038# for 1 #v2070019#"
                    + "#k\r\n#L18#Trade 50 #v4000038# for 1 #v2340000#"
                    + "#k\r\n#L19#Trade 55 #v4000038# for 1 #v2530000#"
                    + "#k\r\n#L20#Trade 60 #v4000038# for 1 #v3013002#"
                    + "#k\r\n#L21#Trade 65 #v4000038# for 1 #v3010068#"
                    + "#k\r\n#L22#Trade 70 #v4000038# for 1 #v3010085#"
                    + "#k\r\n#L23#Trade 80 #v4000038# for 1 #v1152046#"
                    + "#k\r\n#L24#Trade 80 #v4000038# for 1 #v1152047#"
                    + "#k\r\n#L25#Trade 80 #v4000038# for 1 #v1152048#"
                    + "#k\r\n#L26#Trade 80 #v4000038# for 1 #v1152049#"
                    + "#k\r\n#L27#Trade 90 #v4000038# for 1 #v1032108#"
                    + "#k\r\n#L28#Trade 100 #v4000038# for 1 #v1112533#"
                    + "#k\r\n#L29#Trade 150 #v4000038# for 1 #v2531000#"
                    + "#k\r\n#L30#Trade 200 #v4000038# for 1 #v1112663#");
        } else if (selection == 1) {
            if (cm.haveItem(4000038, 1)) {
                cm.gainItem(4000038, -1);
                cm.gainMeso(10000000);
                cm.sendOk("Thanks, here is your 10 Million Mesos");
            } else {
                cm.sendOk("You don't have enough #v4000038#");
            }
        } else if (selection == 2) {
            if (cm.haveItem(4000038, 2)) {
                cm.gainItem(4000038, -2);
                //cm.getPlayer().modifyCSPoints(1, 10000)
                cm.sendOk("Thanks, here is your 1 #v1112927#.");
            } else {
                cm.sendOk("You don't have enough #v4000038#");
            }
        } else if (selection == 3) {
            if (cm.haveItem(4000038, 3)) {
                cm.gainItem(4000038, -3);
                cm.gainItem(2022179, 5);
                cm.sendOk("Thanks, here is your 5 #v2022179#.");
            } else {
                cm.sendOk("You don't have #v4000038#");
            }
        } else if (selection == 4) {
            if (cm.haveItem(4000038, 4)) {
                cm.gainItem(4000038, -4);
                cm.gainItem(2049301, 2);
                cm.sendOk("Thanks, here is your 2 #v2049301#.");
            } else {
                cm.sendOk("You don't have #v4000038#");
            }
        } else if (selection == 5) {
            if (cm.haveItem(4000038, 5)) {
                cm.gainItem(4000038, -5);
                cm.gainItem(4001473, 5);
                cm.sendOk("Thanks, here is your 5 #v4001473#.");
            } else {
                cm.sendOk("You don't have #v4000038#");
            }
        } else if (selection == 6) {
            if (cm.haveItem(4000038, 6)) {
                cm.gainItem(4000038, -6);
                cm.gainItem(2022179, 12);
                cm.sendOk("Thanks, here is your 12 #v2022179#.");
            } else {
                cm.sendOk("You don't have #v4000038#");
            }
        } else if (selection == 7) {
            if (cm.haveItem(4000038, 7)) {
                cm.gainItem(4000038, -7);
                cm.gainItem(2049301, 4);
                cm.sendOk("Thanks, here is your 4 #v2049301#.");
            } else {
                cm.sendOk("You don't have #v4000038#");
            }
        } else if (selection == 8) {
            if (cm.haveItem(4000038, 8)) {
                cm.gainItem(4000038, -8);
                cm.gainItem(2049300, 1);
                cm.sendOk("Thanks, here is your 1 #v2049300#.");
            } else {
                cm.sendOk("You don't have #v4000038#");
            }
        } else if (selection == 9) {
            if (cm.haveItem(4000038, 9)) {
                cm.gainItem(4000038, -9);
                cm.gainItem(4001473, 10);
                cm.sendOk("Thanks, here is your 10 #v4001473#.");
            } else {
                cm.sendOk("You don't have #v4000038#");
            }
        } else if (selection == 10) {
            if (cm.haveItem(4000038, 10)) {
                cm.gainItem(4000038, -10);
                cm.gainItem(2022121, 5);
                cm.sendOk("Thanks, here is your 1 #v2022121#.");
            } else {
                cm.sendOk("You don't have #v4000038#");
            }
        } else if (selection == 11) {
            if (cm.haveItem(4000038, 15)) {
                cm.gainItem(4000038, -15);
                cm.gainItem(2049300, 2);
                cm.sendOk("Thanks, here is your 2 #v2049300#.");
            } else {
                cm.sendOk("You don't have #v4000038#");
            }
        } else if (selection == 12) {
            if (cm.haveItem(4000038, 20)) {
                cm.gainItem(4000038, -20);
                cm.gainItem(5220013, 1);
                cm.sendOk("Thanks, here is your 1 #v5220013#.");
            } else {
                cm.sendOk("You don't have #v4000038#");
            }
        } else if (selection == 13) {
            if (cm.haveItem(4000038, 25)) {
                cm.gainItem(4000038, -25);
                cm.gainItem(4001017, 5);
                cm.sendOk("Thanks, here is your 5 #v4001017#.");
            } else {
                cm.sendOk("You don't have #v4000038#");
            }
        } else if (selection == 14) {
            if (cm.haveItem(4000038, 30)) {
                cm.gainItem(4000038, -30);
                cm.gainItem(2046318, 5);
                cm.sendOk("Thanks, here is your 5 #v2046318#.");
            } else {
                cm.sendOk("You don't have #v4000038#");
            }
        } else if (selection == 15) {
            if (cm.haveItem(4000038, 35)) {
                cm.gainItem(4000038, -35);
                cm.gainItem(4161042, 1);
                cm.sendOk("Thanks, here is your 1 #v4161042#.");
            } else {
                cm.sendOk("You don't have #v4000038#");
            }
        } else if (selection == 16) {
            if (cm.haveItem(4000038, 40)) {
                cm.gainItem(4000038, -40);
                cm.gainItem(1112927, 1);
                cm.sendOk("Thanks, here is your 1 #v1112927#.");
            } else {
                cm.sendOk("You don't have #v4000038#");
            }
        } else if (selection == 17) {
            if (cm.haveItem(4000038, 45)) {
                cm.gainItem(4000038, -45);
                cm.gainItem(2070019, 1);
                cm.sendOk("Thanks, here is your 1 #v2070019#.");
            } else {
                cm.sendOk("You don't have #v4000038#");
            }
        } else if (selection == 18) {
            if (cm.haveItem(4000038, 50)) {
                cm.gainItem(4000038, -50);
                cm.gainItem(2340000, 1);
                cm.sendOk("Thanks, here is your 1 #v2340000#.");
            } else {
                cm.sendOk("You don't have #v4000038#");
            }
        } else if (selection == 19) {
            if (cm.haveItem(4000038, 55)) {
                cm.gainItem(4000038, -55);
                cm.gainItem(2530000, 1);
                cm.sendOk("Thanks, here is your 1 #v2530000#.");
            } else {
                cm.sendOk("You don't have #v4000038#");
            }
        } else if (selection == 20) {
            if (cm.haveItem(4000038, 60)) {
                cm.gainItem(4000038, -60);
                cm.gainItem(3013002, 1);
                cm.sendOk("Thanks, here is your 1 #v3013002#.");
            } else {
                cm.sendOk("You don't have #v4000038#");
            }
        } else if (selection == 21) {
            if (cm.haveItem(4000038, 65)) {
                cm.gainItem(4000038, -65);
                cm.gainItem(3010068, 1);
                cm.sendOk("Thanks, here is your 1 #v3010068#.");
            } else {
                cm.sendOk("You don't have #v4000038#");
            }
        } else if (selection == 22) {
            if (cm.haveItem(4000038, 70)) {
                cm.gainItem(4000038, -70);
                cm.gainItem(3010085, 1);
                cm.sendOk("Thanks, here is your 1 #v3010085#.");
            } else {
                cm.sendOk("You don't have #v4000038#");
            }
        } else if (selection == 23) {
            if (cm.haveItem(4000038, 80)) {
                cm.gainItem(4000038, -80);
                cm.gainItem(1152046, 1);
                cm.sendOk("Thanks, here is your 1 #v1152046#.");
            } else {
                cm.sendOk("You don't have #v4000038#");
            }
        } else if (selection == 24) {
            if (cm.haveItem(4000038, 80)) {
                cm.gainItem(4000038, -80);
                cm.gainItem(1152047, 1);
                cm.sendOk("Thanks, here is your 1 #v1152047#.");
            } else {
                cm.sendOk("You don't have #v4000038#");
            }
        } else if (selection == 25) {
            if (cm.haveItem(4000038, 80)) {
                cm.gainItem(4000038, -80);
                cm.gainItem(1152048, 1);
                cm.sendOk("Thanks, here is your 1 #v1152048#.");
            } else {
                cm.sendOk("You don't have #v4000038#");
            }
        } else if (selection == 26) {
            if (cm.haveItem(4000038, 80)) {
                cm.gainItem(4000038, -80);
                cm.gainItem(1152049, 1);
                cm.sendOk("Thanks, here is your 1 #v1152049#.");
            } else {
                cm.sendOk("You don't have #v4000038#");
            }
        } else if (selection == 27) {
            if (cm.haveItem(4000038, 90)) {
                cm.gainItem(4000038, -90);
                cm.gainItem(1032108, 1);
                cm.sendOk("Thanks, here is your 1 #v1032108#.");
            } else {
                cm.sendOk("You don't have #v4000038#");
            }
        } else if (selection == 28) {
            if (cm.haveItem(4000038, 100)) {
                cm.gainItem(4000038, -100);
                cm.gainItem(1112533, 1);
                cm.sendOk("Thanks, here is your 1 #v1112533#.");
            } else {
                cm.sendOk("You don't have #v4000038#");
            }
        } else if (selection == 29) {
            if (cm.haveItem(4000038, 150)) {
                cm.gainItem(4000038, -150);
                cm.gainItem(2531000, 1);
                cm.sendOk("Thanks, here is your 1 #v2531000#.");
            } else {
                cm.sendOk("You dont have 10 #v4000038#")
            }
        } else if (selection == 30) {
            if (cm.haveItem(4000038, 150)) {
                cm.gainItem(4000038, -200);
                cm.gainItem(1112663, 1);
                cm.sendOk("Thanks, here is your 1 #v1112663#.");
                ;
            } else {
                cm.sendOk("You dont have 10 #v4000038#")
            }
        }
    }
}