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
        cm.sendSimple("Are you one of the winners in the event? If so, you're entitled to exchange rewards with your #v4031306#!"
        +"#k\r\n#L1#Trade 1 #v4031306# for #v1092084#"
        +"#k\r\n#L2#Trade 1 #v4031306# for #v1092079#"
        +"#k\r\n#L3#Trade 1 #v4031306# for #v1492079#"
        +"#k\r\n#L4#Trade 1 #v4031306# for #v1462091#"
        +"#k\r\n#L5#Trade 1 #v4031306# for #v1372078#"
        +"#k\r\n#L6#Trade 1 #v4031306# for #v1342033#"
        +"#k\r\n#L7#Trade 1 #v4031306# for #v1402090#"
        +"#k\r\n#L8#Trade 1 #v4031306# for #v1432081#"
        +"#k\r\n#L9#Trade 1 #v4031306# for #v1472117#"
        +"#k\r\n#L10#Trade 1 #v4031306# for #v1332125#"
        +"#k\r\n#L11#Trade 1 #v4031306# for #v1332120#"
        +"#k\r\n#L12#Trade 1 #v4031306# for #v1302147#"
        +"#k\r\n#L13#Trade 1 #v4031306# for #v1322090#"
        +"#k\r\n#L14#Trade 1 #v4031306# for #v1482079#"
        +"#k\r\n#L15#Trade 1 #v4031306# for #v1452106#"
        +"#k\r\n#L16#Trade 1 #v4031306# for #v1312111#"
        +"#k\r\n#L17#Trade 1 #v4031306# for #v1382099#"
        +"#k\r\n#L18#Trade 1 #v4031306# for #v1412062#");
       
        } else if (selection == 1) {
        if(cm.haveItem(4031306, 1)){
        cm.gainItem(4031306, -1);
        cm.gainItem(1092084, 1);
        cm.sendOk("Thanks, here is your Imperial Theif Shield");
        }
        else {
        cm.sendOk("You don't have enough #v4031306#");
        }
        } else if (selection == 2) {
        if(cm.haveItem(4031306, 1)){
        cm.gainItem(4031306, -1);
	cm.gainItem(1092079, 1);
        cm.sendOk("Thanks, here is your #v1092079#.");
        }
	else {
        cm.sendOk("You don't have enough #v4031306#");
        }
	} else if (selection == 3) {
        if(cm.haveItem(4031306, 1)){
        cm.gainItem(4031306, -1);
        cm.gainItem(1492079, 1);
        cm.sendOk("Thanks, here is your #v1492079#.");
        }
        else {
        cm.sendOk("You don't have #v4031306#");
        }
	} else if (selection == 4) {
        if(cm.haveItem(4031306, 1)){
        cm.gainItem(4031306, -1);
        cm.gainItem(1462091, 1);
        cm.sendOk("Thanks, here is your #v1462091#.");
        }
        else {
        cm.sendOk("You don't have #v4031306#");
        }
	} else if (selection == 5) {
        if(cm.haveItem(4031306, 1)){
        cm.gainItem(4031306, -1);
        cm.gainItem(1372078, 1);
        cm.sendOk("Thanks, here is your #v1372078#.");
        }
        else {
        cm.sendOk("You don't have #v4031306#");
        }
		} else if (selection == 6) {
        if(cm.haveItem(4031306, 1)){
        cm.gainItem(4031306, -1);
        cm.gainItem(1342033, 1);
        cm.sendOk("Thanks, here is your #v1342033#.");
        }
        else {
        cm.sendOk("You don't have #v4031306#");
        }
		} else if (selection == 7) {
        if(cm.haveItem(4031306, 1)){
        cm.gainItem(4031306, -1);
        cm.gainItem(1402090, 1);
        cm.sendOk("Thanks, here is your #v1402090#.");
        }
        else {
        cm.sendOk("You don't have #v4031306#");
        }
		} else if (selection == 8) {
        if(cm.haveItem(4031306, 1)){
        cm.gainItem(4031306, -1);
        cm.gainItem(1432081, 1);
        cm.sendOk("Thanks, here is your #v1432081#.");
        }
        else {
        cm.sendOk("You don't have #v4031306#");
        }
		} else if (selection == 9) {
        if(cm.haveItem(4031306, 1)){
        cm.gainItem(4031306, -1);
        cm.gainItem(1472117, 1);
        cm.sendOk("Thanks, here is your #v1472117#.");
        }
        else {
        cm.sendOk("You don't have #v4031306#");
        }
		} else if (selection == 10) {
        if(cm.haveItem(4031306, 1)){
        cm.gainItem(4031306, -1);
        cm.gainItem(1332125, 1);
        cm.sendOk("Thanks, here is your 1 #v1332125#.");
        }
        else {
        cm.sendOk("You don't have #v4031306#");
        }
	} else if (selection == 11) {
        if(cm.haveItem(4031306, 1)){
        cm.gainItem(4031306, -1);
        cm.gainItem(1332120, 1);
        cm.sendOk("Thanks, here is your #v1332120#.");
        }
        else {
        cm.sendOk("You don't have #v4031306#");
        }
	} else if (selection == 12) {
        if(cm.haveItem(4031306, 1)){
        cm.gainItem(4031306, -1);
        cm.gainItem(1302147, 1);
        cm.sendOk("Thanks, here is your #v1302147#.");
        }
        else {
        cm.sendOk("You don't have #v4031306#");
        }
	} else if (selection == 13) {
        if(cm.haveItem(4031306, 1)){
        cm.gainItem(4031306, -1);
        cm.gainItem(1322090, 1);
        cm.sendOk("Thanks, here is your #v1322090#.");
        }
        else {
        cm.sendOk("You don't have #v4031306#");
        }
	} else if (selection == 14) {
        if(cm.haveItem(4031306, 1)){
        cm.gainItem(4031306, -1);
        cm.gainItem(1482079, 1);
        cm.sendOk("Thanks, here is your #v1482079#.");
        }
        else {
        cm.sendOk("You don't have #v4031306#");
        }
	} else if (selection == 15) {
        if(cm.haveItem(4031306, 1)){
        cm.gainItem(4031306, -1);
        cm.gainItem(1452106, 1);
        cm.sendOk("Thanks, here is your #v1452106#.");
        }
        else {
        cm.sendOk("You don't have #v4031306#");
        }
	} else if (selection == 16) {
        if(cm.haveItem(4031306, 1)){
        cm.gainItem(4031306, -1);
        cm.gainItem(1312111, 1);
        cm.sendOk("Thanks, here is your #v1312111#.");
        }
        else {
        cm.sendOk("You don't have #v4031306#");
        }
	} else if (selection == 17) {
        if(cm.haveItem(4031306, 1)){
        cm.gainItem(4031306, -1);
        cm.gainItem(1382099, 1);
        cm.sendOk("Thanks, here is your #v1382099#.");
        }
        else {
        cm.sendOk("You don't have #v4031306#");
        }
	} else if (selection == 18) {
        if(cm.haveItem(4031306, 1)){
        cm.gainItem(4031306, -1);
        cm.gainItem(1412062, 1);
        cm.sendOk("Thanks, here is your #v1412062#.");
        }
        else {
        cm.sendOk("You don't have #v4031306#");
        }
        
        }
        }
        }