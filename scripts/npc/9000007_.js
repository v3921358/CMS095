//script by Darksta  

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (mode == 0 && status == 0) {
            cm.dispose();
            return;
        }
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 0) {
            cm.sendSimple("Welcome to ParadiseSEA's Currency Shop! Im Chicken Slasher and im here to serve you. Please look at our menu below and i will take your orders.   \r\n\ #L0#Trade 1,000,000,000 (1 Billion) Mesos for #i4000305# \r\n\ #L1# Trade #i4000305# for 1,000,000,000 (1 Billion) Mesos. \r\n\ #L3# Trade #i4000252# for 100,000 Cash");
        } else if (selection == 0) {
            if (cm.getMeso() >= 1000000000) {
                cm.gainMeso(-1000000000);
                cm.gainItem(4000305, 1);
                cm.sendOk("Here's your Fresh chicken!");
                cm.dispose();
            } else {
                cm.sendOk("You don't have enough mesos.");
                cm.dispose();
            }
        } else if (selection == 1) {
            if (cm.getMeso() < 2000000000 && cm.haveItem(4000305, 1)) {
                cm.gainMeso(1000000000);
                cm.gainItem(4000305, -1);
                cm.sendOk("Here's your 1,000,000,000 (1 Billion) mesos!");
                cm.dispose();
            } else {
                cm.sendOk("You don't have any Chicken or you have too much mesos in your inventory (if we gave the mesos to you anyway, it would make your mesos go negative!).");
                cm.dispose();
            }

        } else if (selection == 2) {
            if (cm.getMeso() >= 10000000) {
                cm.gainMeso(-10000000);
                cm.gainItem(5062000, 10);
                cm.sendOk("Here's your Miracle Cubes!");
                cm.dispose();
            } else {
                cm.sendOk("You don't have any  Maple Leaves or you have too much mesos in your inventory (if we gave the mesos to you anyway, it would make your mesos go negative!).");
                cm.dispose();
            }
        } else if (selection == 3) {
            if (cm.haveItem(4000252, 1)) {
                //cm.getPlayer().modifyCSPoints(1, 100000)
                cm.gainItem(4000252, -1);
                cm.sendOk("Here's your 100,000 Cash");
                cm.dispose();
            }
        } else if (selection == 4) {
            if (cm.getMeso() >= 10000000) {
                cm.gainMeso(-10000000);
                cm.gainItem(5062002, 5);
                cm.sendOk("Here's your Super Miracle Cubes!");
                cm.dispose();

            } else if (selection == 5) {
                if (cm.getMeso() >= 10000000) {
                    cm.gainMeso(-10000000);
                    cm.gainItem(5062002, 5);
                    cm.sendOk("Here's your Premium Miracle Cubes!");
                    cm.dispose();
                }
            } else if (selection == 6) {
                if (cm.getMeso() >= 10000000) {
                    cm.gainMeso(-10000000);
                    cm.gainItem(5062002, 5);
                    cm.sendOk("Here's your Premium Miracle Cubes!");
                    cm.dispose();
                }
            } else {
                cm.sendOk("You don't have any  Maple Leaves or you have too much mesos in your inventory (if we gave the mesos to you anyway, it would make your mesos go negative!).");
                cm.dispose();
            }
        }
    }
}  