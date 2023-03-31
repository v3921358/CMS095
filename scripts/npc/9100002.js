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
        cm.sendSimple("Hello, I am the #v2300000# and #v4000518# exchanger."
        +"#k\r\n#L1#Trade 300,000 mesos for 200 #v2300000#"
        +"#k\r\n#L2#Trade 30,000 mesos for 20 #v2300000#"
        +"#k\r\n#L3#Trade 100 #v4000518# For a all stats +7 #v1142196#"
        +"#k\r\n#L4#Trade 500 #v4000518# For a all stats + 10 #v1142146#");
        } else if (selection == 1) {
        if(cm.getMeso() >= 300000){
        cm.gainItem(2300000, 200);
        cm.gainMeso(-300000);
        cm.sendOk("Thanks, here is your 200 #v2300000#.");
        }
        else {
        cm.sendOk("You don't have enough mesos!");
        }
        } else if (selection == 2) {
        if(cm.getMeso() >= 30000){
        cm.gainItem(2300000, 20);
        cm.gainMeso(-30000);
        cm.sendOk("Thanks, here is your 20 #v2300000#.");
        }
        else {
        cm.sendOk("You don't have enough mesos!");
        }
        } else if (selection == 3) {
        if(cm.haveItem(4000518, 100)) {
        cm.gainItem(1142196, 1);
        cm.gainItem(4000518, -100);
        cm.sendOk("Fishing medal is a of a kind medal therefore you can get anymore than one, if not im confiscating your eggs");
        }
        else {
        cm.sendOk("Fishing medal is a of a kind medal therefore you can get anymore than one, if not im confiscating your eggs")
        }
        } else if (selection == 4) {
        if(cm.haveItem(4000518, 500)) {
        cm.gainItem(1142146, 1);
        cm.gainItem(4000518, -500);
        cm.sendOk("Fishing medal is a of a kind medal therefore you can get anymore than one, if not im confiscating your eggs")
        }
        else {
        cm.sendOk("Fishing medal is a of a kind medal therefore you can get anymore than one, if not im confiscating your eggs")
        }
        }
        }
        }