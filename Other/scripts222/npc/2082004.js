
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
        cm.sendSimple("Cra... Craving... Im craving for decorations... My house needs it! I have spectacles to trade it with you... Oh please. You must have enough slots!!"
        +"#k\r\n#L2#Trade 200 #v4001473# for 1 #v2040211# and #v2040212# and #v1022097#.");

        } else if (selection == 1) {
        if(cm.haveItem(4000524, 200)) {
        cm.gainItem(1152087, 1);
        cm.gainItem(4000524, -200);
        cm.sendOk("Thanks, here is your 1 #v1152087#.");
        }
        else {
        cm.sendOk("You don't have enough Dark Tokens!");
        }
        } else if (selection == 2) {
        if(cm.haveItem(4001473, 200)) {
        cm.gainItem(2040211, 1);
        cm.gainItem(2040212, 1);
        cm.gainItem(1022097, 1);
        cm.gainItem(4001473, -200);
        cm.sendOk("Thanks, here is your #v2040211# and #v2040212# and #v1022097#.");
        }
        else {
        cm.sendOk("You don't have enough Decorations!");
        }
        } else if (selection == 3) {
        if(cm.haveItem(4000524, 50)) {
        cm.gainItem(2049300, 1);
        cm.gainItem(4000524, -50);
        cm.sendOk("Thanks, here is your 1 #v2049300#.");
        }
        else {
        cm.sendOk("You don't have enough Dark Tokens!");
        }
        } else if (selection == 4) {
        if(cm.haveItem(4000524, 250)) {
        cm.gainItem(5220013, 1);
        cm.gainItem(4000524, -250);
        cm.sendOk("Thanks, here is your M Coin!");
        }
        else {
        cm.sendOk("You don't have enough Dark Tokens!");
        }
        } else if (selection == 5) {
        if(cm.haveItem(4000524, 10)) {
        cm.gainItem(4001473, 1);
        cm.gainItem(4000524, -10);
        cm.sendOk("Thanks, here is your 1 #v4001473#.");
        }
        else {
        cm.sendOk("You don't have enough Dark Tokens!");
        }
        } else if (selection == 6) {
        if(cm.haveItem(4000524, 100)) {
        cm.gainItem(4001473, 10);
        cm.gainItem(4000524, -100);
        cm.sendOk("Thanks, here is your 10 #v4001473#.");
        }
        else {
        cm.sendOk("You don't have enough Dark Tokens!");
        }
        } else if (selection == 7) {
        if(cm.haveItem(4001473, 1)) {
        cm.gainItem(4001473, -1);
        cm.gainItem(4000524, 10);
        cm.sendOk("Thanks, here is your 10 #v4000524#.");
        }
        else {
        cm.sendOk("You don't have enough Decorations!");
        }
        } else if (selection == 8) {
        if(cm.haveItem(4001473, 10)) {
        cm.gainItem(4001473, -10);
        cm.gainItem(4000524, 100);
        cm.sendOk("Thanks, here is your 100 #v4000524#.");
        }
        else {
        cm.sendOk("You don't have enough Decorations!");
        }
        }
        }
        }