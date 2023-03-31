/*
 * Replayz
 */

/* well, selection 200 ~ 209 is for 2 points
 *       selection 210 ~ 219 is for 4 points
 *       selection 220 ~ 229 is for 8 points 
 *		 selection 230 ~ 230 is for 10 points
 *		 selection 240 ~ 249 is for 20 points and so on, whatever
 */


var status = -1;

function start() {
    if (cm.getPlayerStat("LVL") < 1) {
        cm.sendOk("The vote point is only allow for level 70 and above.");
        return;
    }
        cm.sendSimple("Hello player, what do you want to do with " + "#b"+ cm.getPlayer().getVPoints()+ " Vote Points?#l#k\n\r##L3#Claim my rewards!#l#k");
}

function action(mode, type, selection) {
   if (mode == 1) {
        status++
    } else {
        if (status >= 2 || status == 0) {
            cm.dispose();
            return;
        }
        status--;
        cm.dispose();
    }
        if (status == 0) {
            sel = selection;
            switch (selection) {
                case 3: // Claim reward new list
                    cm.sendOk("Here are the list of rewards you can claim. To gain vote point you'll have to vote for us every 6 hours and come look for me to exchange for it.#k" +
					"#b" + "#l#k\n\r##L200#Trade 2 Vote Points for 4,000 A-Cash#l#k\n\r##L201#Trade 2 Vote Points for 2,000,000 Mesos#l#k\n\r##L202#Trade 2 Vote Points for 4 Miracle Cube#l#k" +
					"#b" + "#l#k\n\r##L210#Trade 4 Vote Points for 5 Onyx Apple#l#k\n\r##L211#Trade 4 Vote Points for Duck Egg( Cash item NPC)#l#k\n\r##L212#Trade 4 Vote Points for 2 Silver Key#l#k\n\r##L213#Trade 4 Vote Points for 2 Premium Miracle Cube#l#k" +
					"#b" + "#l#k\n\r##L220#Trade 20 Vote Points for Vega spell 10%#l#k\n\r##L221#Trade 8 Vote Points for Vega spell 60%#l#k" +
					"#b" + "#l#k\n\r##L230#Trade 20 Vote Points for 1 White Scroll#l#k\n\r##L231#Trade 10 Vote Points for Vote Point Currency#l#k\n\r##L232#Trade Vote Point Currency for 10 vote points#l#k" +
					"#b" + "#l#k\n\r##L240#Trade 30 Vote Points for 1 Twin Coupon (3days)#l#k");
                    break;
            }
        } // start of 2 point reward....
        if (selection == 200) {
           if (cm.getPlayer().getVPoints() >= 2) {
               //cm.getPlayer().modifyCSPoints(1, 4000, true); // get your cash here
               cm.getPlayer().setVPoints(cm.getPlayer().getVPoints() - 2);
               cm.sendOk("Thank you and enjoy");
               cm.dispose();
           } else {
               cm.sendOk("I guess you don't have enough point or please check your inventory slot..");
               cm.dispose(); // dispose no matter what :(
           }
        }
        if (selection == 201) {
           if (cm.getPlayer().getVPoints() >= 2) {
               cm.gainMeso(2000000); // get your mesos here
               cm.getPlayer().setVPoints(cm.getPlayer().getVPoints() - 2);
               cm.sendOk("Thank you and enjoy");
               cm.dispose();
           } else {
               cm.sendOk("I guess you don't have enough point or please check your inventory slot..");
               cm.dispose(); // dispose no matter what :(
           }
        }
        if (selection == 202) {
            if (cm.getPlayer().getVPoints() >= 2) {
                if (cm.canHold(5062000)) {
                    cm.gainItem(5062000, 4); // if canhold pass -> here your cube
                    cm.getPlayer().setVPoints(cm.getPlayer().getVPoints() - 2);
                    cm.sendOk("Thank you and enjoy");
                    cm.dispose();
                } 
            } else {
                cm.sendOk("I guess you don't have enough point or please check your inventory slot..");
                cm.dispose();
            } 
        } // end of 2 point reward, here start of 4 point reward....
        if (selection == 210) {
            if (cm.getPlayer().getVPoints() >= 4) {
                if (cm.canHold(2022179)) {
                    cm.gainItem(2022179, 5); // if canhold pass -> here your apple
                    cm.getPlayer().setVPoints(cm.getPlayer().getVPoints() - 4);
                    cm.sendOk("Thank you and enjoy");
                    cm.dispose();
                } 
            } else {
                cm.sendOk("I guess you don't have enough point or please check your inventory slot..");
                cm.dispose();
            }
        }
        if (selection == 211) {
            if (cm.getPlayer().getVPoints() >= 4) {
                if (cm.canHold(4000188)) {
                    cm.gainItem(4000188, 1); // if canhold pass -> here your keys
                    cm.getPlayer().setVPoints(cm.getPlayer().getVPoints() - 4);
                    cm.sendOk("Thank you and enjoy");
                    cm.dispose();
                } 
            } else {
                cm.sendOk("I guess you don't have enough point or please check your inventory slot..");
                cm.dispose();
            }
        }
        if (selection == 212) {
            if (cm.getPlayer().getVPoints() >= 4) {
                if (cm.canHold(5490001)) {
                    cm.gainItem(5490001, 2); // if canhold pass -> here your keys
                    cm.getPlayer().setVPoints(cm.getPlayer().getVPoints() - 4);
                    cm.sendOk("Thank you and enjoy");
                    cm.dispose();
                } 
            } else {
                cm.sendOk("I guess you don't have enough point or please check your inventory slot..");
                cm.dispose();
            }
        } 
		if (selection == 213) {
			if (cm.getPlayer().getVPoints() >= 4) {
			if (cm.canHold(5062001)) {
				cm.gainItem(5062001, 3); // if canhold pass -> here your Premium Cubes
				cm.getPlayer().setVPoints(cm.getPlayer().getVPoints() - 4);
                cm.sendOk("Thank you and enjoy");
                cm.dispose();
                } 
            } else {
                cm.sendOk("I guess you don't have enough point or please check your inventory slot..");
                cm.dispose();
            }
		}// end of 4 point reward, here start of 8 point reward....
        if (selection == 220) {
            if (cm.getPlayer().getVPoints() >= 20) {
                if (cm.canHold(5610000)) {
                    cm.gainItem(5610000, 1); // if canhold pass -> here your vega 10%
                    cm.getPlayer().setVPoints(cm.getPlayer().getVPoints() - 20);
                    cm.sendOk("Thank you and enjoy");
                    cm.dispose();
                }
            } else {
                cm.sendOk("I guess you don't have enough point or please check your inventory slot..");
                cm.dispose();
            }
        }
        if (selection == 221) {
            if (cm.getPlayer().getVPoints() >= 8) {
                if (cm.canHold(5610001)) {
                    cm.gainItem(5610001, 1); // if canhold pass -> here your vega 60%
                    cm.getPlayer().setVPoints(cm.getPlayer().getVPoints() - 8);
                    cm.sendOk("Thank you and enjoy");
                    cm.dispose();
                }
            } else {
                cm.sendOk("I guess you don't have enough point or please check your inventory slot..");
                cm.dispose();
            }
        }// end of 8 point reward, here start of 10 point reward....
		if (selection == 230) {
            if (cm.getPlayer().getVPoints() >= 20) {
                if (cm.canHold(2340000)) {
                    cm.gainItem(2340000, 1); // if canhold pass -> here your White Scroll
                    cm.getPlayer().setVPoints(cm.getPlayer().getVPoints() - 10);
                    cm.sendOk("Thank you and enjoy");
                    cm.dispose();
                } 
            } else {
                cm.sendOk("I guess you don't have enough point or please check your inventory slot..");
                cm.dispose();
            }
        }
		if (selection == 231) {
            if (cm.getPlayer().getVPoints() >= 10) {
                if (cm.canHold(4031307)) {
                    cm.gainItem(4031307, 1); // if canhold pass -> here your Lucky Day Scroll
                    cm.getPlayer().setVPoints(cm.getPlayer().getVPoints() - 10);
                    cm.sendOk("Thank you and enjoy");
                    cm.dispose();
                } 
            } else {
                cm.sendOk("I guess you don't have enough point or please check your inventory slot..");
                cm.dispose();
            }
        }
		if (selection == 232) {
            if (cm.getPlayer().getVPoints() >= 0) {
                if (cm.haveItem(4031307)) {
                    cm.gainItem(4031307, -1); // if canhold pass -> here your Admin Heart
                    cm.getPlayer().setVPoints(cm.getPlayer().getVPoints() + 10);
                    cm.sendOk("Thank you and enjoy");
                    cm.dispose();
                } 
            } else {
                cm.sendOk("I guess you don't have enough point or please check your inventory slot..");
                cm.dispose();
            }
        }// End of 10 point reward, here start of 20 point reward....
		if (selection == 240) {
            if (cm.getPlayer().getVPoints() >= 30) {
                if (cm.canHold(5360053)) {
                    cm.gainItem(5360053, 1); // if canhold pass -> here your Advanced Equip Enhancement Scroll
                    cm.getPlayer().setVPoints(cm.getPlayer().getVPoints() - 30);
                    cm.sendOk("Thank you and enjoy");
                    cm.dispose();
                } 
            } else {
                cm.sendOk("I guess you don't have enough point or please check your inventory slot..");
                cm.dispose();
            }
        }// End of 20 point reward...
    }
