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
        cm.sendSimple("Hello there, i am the android remover. Please choose your choice of android to be removed from your inventory."
        +"#k\r\n#L1#Remove Android M for 100k Mesos"
        +"#k\r\n#L2#Remove Android F"
        +"#k\r\n#L3#Remove Deluxe Android M"
        +"#k\r\n#L4#Remove Dexule Android F");
        } else if (selection == 1) {
        if(cm.haveItem(1662000, 1)){
        cm.gainItem(1662000, -1);
        cm.gainMeso(-100000);
        cm.sendOk("Thanks for your patronage, your Android M have been removed.");
        }
        else {
        cm.sendOk("You don't have any Android M!");
        }
        } else if (selection == 2) {
        if(cm.haveItem(1662001, 1)){
        cm.gainItem(1662001, -1);
        cm.gainMeso(-100000);
        cm.sendOk("Thanks for your patronage, your Android F have been removed.");
        }
        else {
        cm.sendOk("You don't have any Android F!");
        }
        } else if (selection == 3) {
        if(cm.haveItem(1662002, 1)) {
        cm.gainItem(1662002, -1);
        cm.gainMeso(-150000);
        cm.sendOk("Thanks for your patronage, your Deluxe Android M has been removed.");
        }
        else {
        cm.sendOk("You don't have any Dexule Android M")
        }
        } else if (selection == 4) {
        if(cm.haveItem(1662003, 1)) {
        cm.gainItem(1662003, -1);
        cm.gainMeso(-150000);
        cm.sendOk("Thanks for your patronage, your Deluxe Android F has been removed.")
        }
        else {
        cm.sendOk("You don't have any Dexule Android F")
        }
        }
        }
        }