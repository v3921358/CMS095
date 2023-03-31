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
        cm.sendSimple("Hello, I am temporary potion and misc seller!."
        +"#k\r\n#L1#Give me 400,000 mesos for 200 #v2000005#"
        +"#k\r\n#L2#Give me 100,000 mesos for 100 #v2050004#"
        +"#k\r\n#L3#I will sell you 1 #v4001017# 20 million mesos"
        +"#k\r\n#L4#Fan of teddy bears, Heres some #v4032246# for you");
        } else if (selection == 1) {
        if(cm.getMeso() >= 400000){
        cm.gainItem(2000005, 200);
        cm.gainMeso(-400000);
        cm.sendOk("Thanks, here is your 200 #v2000005#.");
        }
        else {
        cm.sendOk("You don't have enough mesos!");
        }
        } else if (selection == 2) {
        if(cm.getMeso() >= 100000){
        cm.gainItem(2050004, 100);
        cm.gainMeso(-100000);
        cm.sendOk("Thanks, here is your 100 #v2300000#.");
        }
        else {
        cm.sendOk("You don't have enough mesos!");
        }
        } else if (selection == 3) {
        if(cm.getMeso() >= 20000000){
        cm.gainItem(4001017, 1);
        cm.gainMeso(-20000000);
        cm.sendOk("Here is your #4001017#.");
        }
        else {
        cm.sendOk("You do not have enough mesos.")
        }
        } else if (selection == 4) {
        if(cm.getMeso() >= 1){
        cm.gainItem(4032246, 5);
        cm.sendOk("Dont you love sweet teddy bears?")
        }
        else {
        cm.sendOk("Teddies are sweet.")
        }
        }
        }
        }