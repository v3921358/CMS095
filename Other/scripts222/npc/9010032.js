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
        if(status == 0) {
        cm.sendSimple("Bloody transparent man. I stole all these shit i cant see from him... Im a farmer , Duck dont drop eggs now. I need em very much... Lets trade!"
        +"#k\r\n#L1#Trade 20 Duck eggs for a Transparent Face Accessory"
		+"#k\r\n#L2#Trade 20 Duck eggs for a Transparent Eye Accessory"
		+"#k\r\n#L3#Trade 20 Duck eggs for a Transparent Earrings"
		+"#k\r\n#L4#Trade 20 Duck eggs for a Transparent Hat"
		+"#k\r\n#L5#Trade 20 Duck eggs for a Transparent Cape"
		+"#k\r\n#L6#Trade 20 Duck eggs for a Transparent Gloves"
		+"#k\r\n#L7#Trade 20 Duck eggs for a Transparent Skull Gloves"
		+"#k\r\n#L8#Trade 20 Duck eggs for a Transparent Shield"
		+"#k\r\n#L9#Trade 20 Duck eggs for a Transparent Shoe"
		+"#k\r\n#L10#Trade 20 Duck eggs for a Transparent Claw"
		+"#k\r\n#L11#Trade 20 Duck eggs for a Transparent Knuckle"
		+"#k\r\n#L12#Trade 20 Duck eggs for a Transparent Weapon");
        } else if (selection == 1) {
        if(cm.haveItem(4000188, 20)){
        cm.gainItem(1012057, 1);
		cm.gainItem(4000188, -20);
        cm.sendOk("Thanks, here is your Transparent Face Accessory");
        }
        else {
        cm.sendOk("You don't have enough #v4000188#");
        }
        } else if (selection == 2) {
        if(cm.haveItem(4000188, 20)){	
		cm.gainItem(4000188, -20);
        cm.gainItem(1022048, 1);
        cm.sendOk("Thanks, here is your Transparent Eye Accessory.");
        }
	else {
        cm.sendOk("You don't have enough #v4000188#");
        }
	} else if (selection == 3) {
        if(cm.haveItem(4000188, 20)){
        cm.gainItem(4000188, -20);
        cm.gainItem(1032024, 1);
        cm.sendOk("Thanks, here is your Transparent Earring.");
        }
        else {
        cm.sendOk("You don't have #v4000188#");
        }
	} else if (selection == 4) {
        if(cm.haveItem(4000188, 20)){
        cm.gainItem(4000188, -20);
        cm.gainItem(1002186, 1);
        cm.sendOk("Thanks, here is your Transparent Hat");
        }
        else {
        cm.sendOk("You don't have #v4000188#");
        }
	} else if (selection == 5) {
        if(cm.haveItem(4000188, 20)){
        cm.gainItem(4000188, -20);
        cm.gainItem(1102039, 1);
        cm.sendOk("Thanks, here is your Transparent Cape.");
        }
        else {
        cm.sendOk("You don't have #v4000188#");
        }
		} else if (selection == 6) {
        if(cm.haveItem(4000188, 20)){
        cm.gainItem(4000188, -20);
        cm.gainItem(1082102, 1);
        cm.sendOk("Thanks, here is your Transparent Gloves.");
        }
        else {
        cm.sendOk("You don't have #v4000188#");
        }
		} else if (selection == 7) {
        if(cm.haveItem(4000188, 20)){
        cm.gainItem(4000188, -20);
        cm.gainItem(1082227, 1);
        cm.sendOk("Thanks, here is your Transparent Skull Gloves");
        }
        else {
        cm.sendOk("You don't have #v4000188#");
        }
		} else if (selection == 8) {
        if(cm.haveItem(4000188, 20)){
        cm.gainItem(4000188, -20);
        cm.gainItem(1092067, 1);
        cm.sendOk("Thanks, here is your Transparent Shield");
        }
        else {
        cm.sendOk("You don't have #v4000188#");
        }
		} else if (selection == 9) {
        if(cm.haveItem(4000188, 20)){
        cm.gainItem(4000188, -20);
        cm.gainItem(1072153, 1);
        cm.sendOk("Thanks, here is your Transparent Shoe");
        }
        else {
        cm.sendOk("You don't have #v4000188#");
        }
		} else if (selection == 10) {
        if(cm.haveItem(4000188, 20)){
        cm.gainItem(4000188, -20);
        cm.gainItem(1702099, 1);
        cm.sendOk("Thanks, here is your Transparent Claw");
        }
        else {
        cm.sendOk("You don't have #v4000188#");
        }
	} else if (selection == 11) {
        if(cm.haveItem(4000188, 20)){
        cm.gainItem(4000188, -20);
        cm.gainItem(1702190, 1);
        cm.sendOk("Thanks, here is your Transparent Knuckle");
        }
        else {
        cm.sendOk("You don't have #v4000188#");
        }
	} else if (selection == 12) {
        if(cm.haveItem(4000188, 20)){
        cm.gainItem(4000188, -20);
        cm.gainItem(1702224, 1);
        cm.sendOk("Thanks, here is your Transparent Weapon");
        }
        else {
        cm.sendOk("You don't have #v4000188#");
        }
	} else if (selection == 13) {
        if(cm.haveItem(4000188, 25)){
        cm.gainItem(4000188, -25);
        cm.gainItem(4001017, 5);
        cm.sendOk("Thanks, here is your 5 #v4001017#.");
        }
        else {
        cm.sendOk("You don't have #v4000188#");
        }
	} else if (selection == 14) {
        if(cm.haveItem(4000188, 30)){
        cm.gainItem(4000188, -30);
        cm.gainItem(2046318, 5);
        cm.sendOk("Thanks, here is your 5 #v2046318#.");
        }
        else {
        cm.sendOk("You don't have #v4000188#");
        }
	} else if (selection == 15) {
        if(cm.haveItem(4000188, 35)){
        cm.gainItem(4000188, -35);
        cm.gainItem(4161042, 1);
        cm.sendOk("Thanks, here is your 1 #v4161042#.");
        }
        else {
        cm.sendOk("You don't have #v4000188#");
        }
	} else if (selection == 16) {
        if(cm.haveItem(4000188, 40)){
        cm.gainItem(4000188, -40);
        cm.gainItem(1112927, 1);
        cm.sendOk("Thanks, here is your 1 #v1112927#.");
        }
        else {
        cm.sendOk("You don't have #v4000188#");
        }
	} else if (selection == 17) {
        if(cm.haveItem(4000188, 45)){
        cm.gainItem(4000188, -45);
        cm.gainItem(2070019, 1);
        cm.sendOk("Thanks, here is your 1 #v2070019#.");
        }
        else {
        cm.sendOk("You don't have #v4000188#");
        }
	} else if (selection == 18) {
        if(cm.haveItem(4000188, 50)){
        cm.gainItem(4000188, -50);
        cm.gainItem(2340000, 1);
        cm.sendOk("Thanks, here is your 1 #v2340000#.");
        }
        else {
        cm.sendOk("You don't have #v4000188#");
        }
        } else if (selection == 19) {
        if(cm.haveItem(4000188, 55)){
        cm.gainItem(4000188, -55);
        cm.gainItem(2530000, 1);
        cm.sendOk("Thanks, here is your 1 #v2530000#.");
        }
        else {
        cm.sendOk("You don't have #v4000188#");
        }
		} else if (selection == 20) {
        if(cm.haveItem(4000188, 60)){
        cm.gainItem(4000188, -60);
        cm.gainItem(3013002, 1);
        cm.sendOk("Thanks, here is your 1 #v3013002#.");
        }
        else {
        cm.sendOk("You don't have #v4000188#");
        }
		} else if (selection == 21) {
        if(cm.haveItem(4000188, 65)){
        cm.gainItem(4000188, -65);
        cm.gainItem(3010068, 1);
        cm.sendOk("Thanks, here is your 1 #v3010068#.");
        }
        else {
        cm.sendOk("You don't have #v4000188#");
        }
		} else if (selection == 22) {
        if(cm.haveItem(4000188, 70)){
        cm.gainItem(4000188, -70);
        cm.gainItem(3010085, 1);
        cm.sendOk("Thanks, here is your 1 #v3010085#.");
        }
        else {
        cm.sendOk("You don't have #v4000188#");
        }
		} else if (selection == 23) {
        if(cm.haveItem(4000188, 80)){
        cm.gainItem(4000188, -80);
        cm.gainItem(1152046, 1);
        cm.sendOk("Thanks, here is your 1 #v1152046#.");
        }
        else {
        cm.sendOk("You don't have #v4000188#");
        }
		} else if (selection == 24) {
        if(cm.haveItem(4000188, 80)){
        cm.gainItem(4000188, -80);
        cm.gainItem(1152047, 1);
        cm.sendOk("Thanks, here is your 1 #v1152047#.");
        }
        else {
        cm.sendOk("You don't have #v4000188#");
        }
		} else if (selection == 25) {
        if(cm.haveItem(4000188, 80)){
        cm.gainItem(4000188, -80);
        cm.gainItem(1152048, 1);
        cm.sendOk("Thanks, here is your 1 #v1152048#.");
        }
        else {
        cm.sendOk("You don't have #v4000188#");
        }
		} else if (selection == 26) {
        if(cm.haveItem(4000188, 80)){
        cm.gainItem(4000188, -80);
        cm.gainItem(1152049, 1);
        cm.sendOk("Thanks, here is your 1 #v1152049#.");
        }
        else {
        cm.sendOk("You don't have #v4000188#");
        }
		} else if (selection == 27) {
        if(cm.haveItem(4000188, 90)){
        cm.gainItem(4000188, -90);
        cm.gainItem(1032108, 1);
        cm.sendOk("Thanks, here is your 1 #v1032108#.");
        }
        else {
        cm.sendOk("You don't have #v4000188#");
        }
		} else if (selection == 28) {
        if(cm.haveItem(4000188, 100)){
        cm.gainItem(4000188, -100);
        cm.gainItem(1112533, 1);
        cm.sendOk("Thanks, here is your 1 #v1112533#.");
        }
        else {
        cm.sendOk("You don't have #v4000188#");
        }
        } else if (selection == 29) {
        if(cm.haveItem(4000188, 150)){
        cm.gainItem(4000188, -150);
        cm.gainItem(2531000, 1);
        cm.sendOk("Thanks, here is your 1 #v2531000#.");
        }
        else {
        cm.sendOk("You dont have 10 #v4000188#")
        }
        } else if (selection == 30) {
        if(cm.haveItem(4000188, 150)){
        cm.gainItem(4000188, -200);
        cm.gainItem(1112663, 1);
        cm.sendOk("Thanks, here is your 1 #v1112663#.");;
        }
        else {
        cm.sendOk("You dont have 10 #v4000188#")
        }
        }
        }
        }