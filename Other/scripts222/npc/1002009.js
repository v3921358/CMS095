load('nashorn:mozilla_compat.js');
var status = -1;
var items;
var itemsp = Array(3000, 3000, 3000, 3000, 3000, 3000, 3000, 3000, 3000, 3000, 3000, 3000, 3000, 3000, 3000, 3000, 3000, 3000, 3000, 200, 200, 200, 300, 150, 300, 100, 500); // price of item
var itemsu = Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0); // extra slots, not set.
var itemsq = Array(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 50, 1); // quantity of item
var itemse = Array(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1); // default is -1
var extra_text = Array("Agares Bloody Terror", "Agares Headsplitter", "Agares Bloody Hammer", "Halphas Bloody Slayer", "Blood Blossom Katara", "Eligos Bloody Wand", "Eligos Bloody Rod", "Agares Bloody Zweihander", "Agares Bloody Giant Axe", "Agares Bloody Maul", "Agares Bloody Spear", "Agares Bloody Polearm", "Ipos Bloody Longbow", "Ipos Bloody Crossbow", "Ipos Bloody Dual Bowguns", "Halphas Bloody Mist", "Vepar Bloody Hands", "Vepar Bloody Eagle", "Vepar Bloody Oblivion", "Special Potential Scroll", "Beach Sandle Limited Edition", "Miraculous Chaos Scroll 60%", "Dual Blade Mask(All Jobs)", "Dual Blade Mask Scroll 90%", "Yellow Belt", "Gelt Chocolate(x50)", "7 Days Twin Coupon", "Rex's Perfect Green Earrings", "Rex's Perfect Red Earrings", "Rex's Perfect Blue Earrings", "Spiegelmann's Mustache", "High Lord's Eternal Ring", "Oracle's Eternal Ring", "White Angelic Blessing", "Guardian's Eternal Ring", "Berserker's Eternal Ring", "Hero of Legend", "Renegade Justice Badge", "Marvel Badge", "The Hardcore", "Gloves For ATT", "Golden Almighty Ring", "Naricain's Demon Elixir", "Miraculous Chaos Scroll 60%", "Super Stormcaster Gloves", "Super Pink Adventurer Cape", "Scroll for Shoes for ATT 100%", "Scroll for Shoes for ATT 60%", "Scroll for Shoes for ATT 10%", "Scroll for Armor ATT 15%", "Scroll for Armor Magic ATT 15%", "Scroll for Accessory for ATT 15%", "Scroll for Accessory Magic ATT 15%");
var acash = 100000;
var acashp = 500;
var sel = -1;
var itt = -1;
var previous_points;
var chairs;
var chairsp = Array(800, 800, 800, 800, 800, 800, 800, 800, 800, 800, 800, 800, 800, 800, 800, 800, 800, 800, 800, 800, 800, 800, 800, 800);

var hairp = 1000;
var mhair;
var fhair;
var hairnew;

var keys = Array(16, 17, 18, 20, 21, 22, 36, 44, 45, 46, 47, 48);
var keynames = Array("Q", "W", "F", "T", "Y", "U", "J", "Z", "X", "C", "V", "B"); //just as reference
var skills;
var skillsnames = Array("Dispel", "Haste", "Bless", "Teleport", "Hyper Body");
var skillsp = Array(10000, 15000, 18000, 15000, 25000);
var allskillsp = 50000;

var resetp = 10000;

var pendantp = 1000;
var pendantp_perm = 3000;

var namep = 3000;

var buddyp = 2000;

var ep = 1000;
Fr
var slot = Array();
var inv;

function start() {
    action(1, 0, 0);
    if (cm.isGMS()) {
        fhair = Array(34900, 34850, 34690, 34680, 34670, 34660, 34650, 34630, 34620, 34610, 34600, 34590, 34510, 34490, 34480, 34470, 34420, 34410, 34400, 34380, 34370, 34310, 34270, 34260, 34250, 34220, 34210, 34200, 34180, 34160, 34150, 34140, 31430, 34120, 34110, 34100, 34030, 33680, 34621);
        mhair = Array(36000, 33990, 33940, 33930, 33810, 33780, 33750, 33660, 33640, 33620, 33610, 33600, 33580, 33530, 33450, 33440, 33410, 33400, 33390, 33370, 33370, 33360, 33330, 33260, 33933, 33441, 33407, 34202, 34177);
        chairs = Array(2044703, 2044603, 2045309, 2045209, 2043303, 2040807, 2040806, 2044503, 2044815, 2044908, 2043103, 2043203, 2043003, 2040506, 2044403, 2040709, 2040710, 2040711, 2044303, 2043803, 2044103, 2044203, 2044003, 2043703);
        items = Array(1302153, 1312066, 1322097, 1332131, 1342035, 1372085, 1382105, 1402096, 1412066, 1422067, 1432087, 1442117, 1452112, 1462100, 1522017, 1472123, 1482085, 1492086, 1532017, 2049406, 1072535, 2049116, 1012191, 2040125, 1132019, 2022121, 5360050);
        skills = Array(9101000, 9101001, 9101003, 9101007, 9101008);
    } else {
        fhair = Array(34900, 34850, 34690, 34680, 34670, 34660, 34650, 34630, 34620, 34610, 34600, 34590, 34510, 34490, 34480, 34470, 34420, 34410, 34400, 34380, 34370, 34310, 34270, 34260, 34250, 34220, 34210, 34200, 34180, 34160, 34150, 34140, 31430, 34120, 34110, 34100, 34030, 33680, 34621);
        mhair = Array(36000, 33990, 33940, 33930, 33810, 33780, 33750, 33660, 33640, 33620, 33610, 33600, 33580, 33530, 33450, 33440, 33410, 33400, 33390, 33370, 33370, 33360, 33330, 33260, 33933, 33441, 33407, 34202, 34177);
        chairs = Array(2044703, 2044603, 2045309, 2045209, 2043303, 2040807, 2040806, 2044503, 2044815, 2044908, 2043103, 2043203, 2043003, 2040506, 2044403, 2040709, 2040710, 2040711, 2044303, 2043803, 2044103, 2044203, 2044003, 2043703);
        items = Array(1302153, 1312066, 1322097, 1332131, 1342035, 1372085, 1382105, 1402096, 1412066, 1422067, 1432087, 1442117, 1452112, 1462100, 1522017, 1472123, 1482085, 1492086, 1532017, 2049406, 1072535, 2049116, 1012191, 2040125, 1132019, 2022121, 5360050);
        skills = Array(9001000, 9001001, 9001003, 9001007, 9001008);
    }
    inv = cm.getInventory(1);
    previous_points = cm.getPlayer().getPoints();
}

function action(mode, type, selection) {
    if (mode != 1) {
        cm.dispose();
        return;
    }
    status++;
    if (status == 0) {
        cm.sendSimple("Hello #r#h ##k! My name is #rRich man's son#k. I really love Donation #bpoints#k. What would you like?\r\n#b#L0#What's Donation Points?#l\r\n#L16#Trade Red Luck Sack for 100 Donation Points#l\r\n#b#L17#Trade 100 Donation Points to Red Luck Sack#l\r\n#b#L6#Miracle Scrolls#l\r\n#b#L4#Trade points for NX(1 week)#l \r\n#b#L1#Rare Items#l \r\n#b#L3#Increase Equipment Slots#l \r\n#b#L7#Donor Hair (" + hairp + " points)#l \r\n#L10#Change your name using " + namep + " points#l \r\n#L18#Additional Pendant Slot#l#k");
    } else if (status == 1) {
        sel = selection;
        if (selection == 0) {
            cm.sendNext("Points can be achieved through donations. They can be used to trade for really good things, such as equipments, chairs, and Cash right here by me! 100 Donation points is 1 USD, The more you buy the worth gets more! Check out website for more info!");
            status = -1;
        } else if (selection == 1) {
            var selStr = "Alright, I can trade these items for points...#b\r\n\r\n";
            for (var i = 0; i < items.length; i++) {
                selStr += "#L" + i + "##v" + items[i] + "#" + extra_text[i] + (itemsu[i] > 0 ? "(with " + itemsu[i] + " extra slots)" : "") + " x " + itemsq[i] + " for #e" + itemsp[i] + "#n points#n" + (itemse[i] > 0 ? (" ...lasts #r#e" + itemse[i] + "#n#bdays") : "") + "#l\r\n";
            }
            cm.sendSimple(selStr + "#k");
        } else if (selection == 3) {
            var bbb = false;
            var selStr = "Alright. I can #eonly give a slot to equipments that have 0 upgrade slots and have been hammered twice. You can only give a slot up to 10 times to a certain item. It will cost #b" + ep + "#k points, and #b" + (ep * 2) + "#k points for items above 5 slots upgraded.#n Select the equipment you have below(equipped items are not included):\r\n\r\n#b";
            for (var i = 0; i <= inv.getSlotLimit(); i++) {
                slot.push(i);
                var it = inv.getItem(i);
                if (it == null || it.getUpgradeSlots() > 0 || it.getViciousHammer() < 2 || it.getViciousHammer() > 6) {
                    continue;
                }
                var itemid = it.getItemId();
                //bwg - 7, with hammer is 9.
                //therefore, we should make the max slots (natural+7)
                if (cm.getNaturalStats(itemid, "tuc") <= 0 || itemid == 1122080 || cm.isCash(itemid)) {
                    continue;
                }
                bbb = true;
                selStr += "#L" + i + "##v" + itemid + "##t" + itemid + "##l\r\n";
            }
            if (!bbb) {
                cm.sendOk("You don't have any equipments I can enhance. I can #eonly enhance equipments that have 0 upgrade slots and have been hammered twice#n.This does not include cash items.");
                cm.dispose();
                return;
            }
            cm.sendSimple(selStr + "#k");
        } else if (selection == 4) {
            cm.sendYesNo("Cash, is that what you need? Well, I can trade #r#e" + (cm.isGMS() ? (acash / 2) : acash) + " Cash for " + acashp + " points.#n#k. Would you like to accept the offer?");
        } else if (selection == 5) {
            cm.sendOk("You have currently #e" + cm.getPlayer().getPoints() + "#n points.");
            cm.dispose();
        } else if (selection == 6) {
            var selStr = "Alright, I can trade these Miracle Scrolls for points...#b\r\n\r\n";
            for (var i = 0; i < chairs.length; i++) {
                selStr += "#L" + i + "##v" + chairs[i] + "##t" + chairs[i] + "# for #e" + chairsp[i] + "#n points#n#l\r\n";
            }
            cm.sendSimple(selStr + "#k");
        } else if (selection == 7) {
            hairnew = Array();
            if (cm.getPlayerStat("GENDER") == 0) {
                for (var i = 0; i < mhair.length; i++) {
                    if (mhair[i] == 30010 || mhair[i] == 30070 || mhair[i] == 30080 || mhair[i] == 30090 || mhair[i] == 33140 || mhair[i] == 33240 || mhair[i] == 33180) {
                        hairnew.push(mhair[i]);
                    } else {
                        hairnew.push(mhair[i] + parseInt(cm.getPlayerStat("HAIR") % 10));
                    }
                }
            } else {
                for (var i = 0; i < fhair.length; i++) {
                    if (fhair[i] == 34160) {
                        hairnew.push(fhair[i]);
                    } else {
                        hairnew.push(fhair[i] + parseInt(cm.getPlayerStat("HAIR") % 10));
                    }
                }
            }
            cm.sendStyle("Pick a hairstyle that you would like.", hairnew);
        } else if (selection == 8) {
            cm.sendYesNo("Once you proceed with this selection, you can't go back! Are you sure you want to #ereset ALL YOUR AP?#k");
        } else if (selection == 9) {
            var selStr = "Alright, I can trade these skills for points...#eIf you have bought a skill once, you will not be charged points for it thereafter.#n#b\r\n\r\n";
            for (var i = 0; i < skills.length; i++) {
                selStr += "#L" + i + "##s" + skills[i] + "#" + skillsnames[i] + " for #e" + skillsp[i] + "#n points#n#l\r\n";
            }
            selStr += "#L" + skills.length + "##rALL skills above#b for #e" + allskillsp + "#n points#l\r\n";
            cm.sendSimple(selStr + "#k");
        } else if (selection == 10) {
            cm.sendGetText("Please enter the name you wish to change to.");
        } else if (selection == 11) {
            if (cm.getBuddyCapacity() < 120 && cm.getPlayer().getPoints() >= buddyp) {
                cm.getPlayer().setPoints(cm.getPlayer().getPoints() - buddyp);
                cm.logDonator(" has bought buddy capacity 120 costing " + buddyp + ".", previous_points);
                cm.updateBuddyCapacity(120);
            } else {
                cm.sendOk("You either have 120 capacity or you don't have enough points.");
            }
            cm.dispose();
        } else if (selection == 12) {
            if (!cm.getPlayer().haveItem(5220013)) {
                cm.sendOk("You need at least 1 M Coin.");
                cm.dispose();
            } else {
                cm.sendGetNumber("How many M Coins would you like to redeem? (1 M Coin = 100 points) (Current M Coins: " + cm.getPlayer().itemQuantity(5220013) + ") (Current Points: " + cm.getPlayer().getPoints() + ")", cm.getPlayer().itemQuantity(5220013), 1, cm.getPlayer().itemQuantity(5220013));
            }
        } else if (selection == 13) {
            if (cm.getPlayer().getPoints() < 100) {
                cm.sendOk("You need at least 100 points for an M Coin.");
                cm.dispose();
            } else {
                cm.sendGetNumber("How many M Coins would you like? (1 M Coin = 100 points) (Current Points: " + cm.getPlayer().getPoints() + ") (Current M Coins: " + cm.getPlayer().itemQuantity(5220013) + ")", cm.getPlayer().getPoints() / 100, 1, cm.getPlayer().getPoints() / 100);
            }
        } else if (selection == 16) {
            if (!cm.getPlayer().haveItem(3993003)) {
                cm.sendOk("You need at least 1 Red Luck Sack.");
                cm.dispose();
            } else {
                cm.sendGetNumber("How many Red Luck Sacks would you like to redeem? (1 Red Luck Sack = 100 points) (Current: " + cm.getPlayer().itemQuantity(3993003) + ") (Current Points: " + cm.getPlayer().getPoints() + ")", cm.getPlayer().itemQuantity(3993003), 1, cm.getPlayer().itemQuantity(3993003));
            }
        } else if (selection == 17) {
            if (cm.getPlayer().getPoints() < 100) {
                cm.sendOk("You need at least 100 points for a Red Luck Sack.");
                cm.dispose();
            } else {
                cm.sendGetNumber("How many Red Luck Sacks would you like? (1 Red Luck Sack = 100 points) (Current Points: " + cm.getPlayer().getPoints() + ") (Current Red Luck Sacks: " + cm.getPlayer().itemQuantity(3993003) + ")", cm.getPlayer().getPoints() / 100, 1, cm.getPlayer().getPoints() / 100);
            }
        } else if (selection == 18) {
            cm.sendSimple("#b#L0#30 Days - " + pendantp + " points#l\r\n#L1#Permanent - " + pendantp_perm + " points#l");
        }
    } else if (status == 2) {
        if (sel == 1) {
            var it = items[selection];
            var ip = itemsp[selection];
            var iu = itemsu[selection];
            var iq = itemsq[selection];
            var ie = itemse[selection];
            if (cm.getPlayer().getPoints() < ip) {
                cm.sendOk("You don't have enough points. You have " + cm.getPlayer().getPoints() + " while I need " + ip + ".");
            } else if (!cm.canHold(it, iq)) {
                cm.sendOk("Please free up inventory.");
            } else {
                if (iu > 0) {
                    cm.gainItem(it, iq, false, ie, iu, "Donor");
                } else {
                    cm.gainItemPeriod(it, iq, ie, "Donor");
                }
                cm.getPlayer().setPoints(cm.getPlayer().getPoints() - ip);
                cm.sendOk("There! Thank you for those points. I have given you your item. Come again~");
                cm.logDonator(" has bought item [" + it + "] x " + iq + " costing " + ip + ". [Expiry: " + ie + "] [Extra Slot: " + iu + "] ", previous_points);
            }
            cm.dispose();
        } else if (sel == 3) {
            var statsSel = inv.getItem(slot[selection]);
            if (statsSel == null || statsSel.getUpgradeSlots() > 0 || statsSel.getViciousHammer() < 2) {
                cm.dispose();
                return;
            }
            var itemid = statsSel.getItemId();
            //bwg - 7, with hammer is 9.
            //therefore, we should make the max slots(natural+7)
            if (statsSel.getViciousHammer() > 6 || cm.getNaturalStats(itemid, "tuc") <= 0 || itemid == 1122080) {
                cm.dispose();
                return;
            }
            if (cm.isCash(itemid)) {
                cm.dispose();
                return;
            }
            var pointsToUse = ep;
            if (statsSel.getViciousHammer() >= 4) { //2 slots with normal, 3 slots afterwards with doubled price
                pointsToUse = ep * 2;
            }
            if (cm.getPlayer().getPoints() < pointsToUse) {
                cm.sendOk("You don't have enough points. You have " + cm.getPlayer().getPoints() + " while I need " + pointsToUse + ".");
            } else {
                cm.replaceItem(selection, 1, statsSel, 1);
                cm.getPlayer().setPoints(cm.getPlayer().getPoints() - pointsToUse);
                cm.sendOk("There! Thank you for those points. I have given you your item. Come again~");
                cm.logDonator(" has enhanced +1 slot on item [" + statsSel.getItemId() + "] costing " + pointsToUse + ". [Used slots: " + statsSel.getViciousHammer() + "]", previous_points);
            }
            cm.dispose();
        } else if (sel == 4) {
            if (cm.getPlayer().getPoints() < acashp) {
                cm.sendOk("You don't have enough points. You have " + cm.getPlayer().getPoints() + " while I need " + acashp + ".");
            } else if (cm.getPlayer().getCSPoints(1) > (java.lang.Integer.MAX_VALUE - acash)) {
                cm.sendOk("You have too much Cash.");
            } else {
                cm.getPlayer().setPoints(cm.getPlayer().getPoints() - acashp);
                //cm.getPlayer().modifyCSPoints(1, acash, true);
                cm.sendOk("There! Thank you for those points, I have given you Cash. Come again~");
                cm.logDonator(" has bought Cash [" + (cm.isGMS() ? (acash / 2) : acash) + "] costing " + acashp + ".", previous_points);
            }
            cm.dispose();
        } else if (sel == 6) {
            var it = chairs[selection];
            var cp = chairsp[selection];
            if (cm.getPlayer().getPoints() < cp) {
                cm.sendOk("You don't have enough points. You have " + cm.getPlayer().getPoints() + " while I need " + cp + ".");
            } else if (!cm.canHold(it)) {
                cm.sendOk("Please free up inventory.");
            } else {
                cm.gainItem(it, 1);
                cm.getPlayer().setPoints(cm.getPlayer().getPoints() - cp);
                cm.sendOk("There! Thank you for those points. I have given you your chair. Come again~");
                cm.logDonator(" has bought chair [" + it + "] costing " + cp + ".", previous_points);
            }
            cm.dispose();
        } else if (sel == 7) {
            if (cm.getPlayer().getPoints() < hairp) {
                cm.sendOk("You don't have enough points. You only have " + cm.getPlayer().getPoints());
            } else {
                cm.setHair(hairnew[selection]);
                cm.getPlayer().setPoints(cm.getPlayer().getPoints() - hairp);
                cm.sendOk("Thank you for the purchase~");
                cm.logDonator(" has bought hair [" + hairnew[selection] + "] costing " + hairp + ".", previous_points);
            }
            cm.dispose();
        } else if (sel == 8) {
            if (cm.getPlayer().getPoints() < resetp) {
                cm.sendOk("You don't have enough points. You only have " + cm.getPlayer().getPoints());
            } else {
                cm.getPlayer().resetStatsByJob(false);
                cm.getPlayer().setPoints(cm.getPlayer().getPoints() - resetp);
                cm.sendOk("Thank you for the purchase~");
                cm.logDonator(" has bought full AP reset costing " + resetp + ".", previous_points);
            }
            cm.dispose();
        } else if (sel == 9) {
            if (selection == skills.length) {
                if (cm.getPlayer().getPoints() < allskillsp) {
                    cm.sendOk("You don't have enough points. You only have " + cm.getPlayer().getPoints());
                } else {
                    for (var i = 0; i < skills.length; i++) {
                        cm.teachSkill(skills[i], 1, 0);
                    }
                    cm.getPlayer().setPoints(cm.getPlayer().getPoints() - allskillsp);
                    cm.sendOk("Thank you for the purchase~ To use your skills, please click on me again and distribute each skill to a key.");
                    cm.logDonator(" has bought all skills costing " + allskillsp + ".", previous_points);
                }
                cm.dispose();
                return;
            }
            itt = selection;
            var selStr = "Alright, I can put your skill on these keys...#b\r\n\r\n";
            for (var i = 0; i < keys.length; i++) {
                selStr += "#L" + i + "#" + keynames[i] + "#l\r\n";
            }
            cm.sendSimple(selStr + "#k");
        } else if (sel == 10) {
            if (cm.getPlayer().getPoints() >= namep && cm.isEligibleName(cm.getText())) {
                cm.getPlayer().setPoints(cm.getPlayer().getPoints() - namep);
                cm.logDonator(" has bought name change from " + cm.getPlayer().getName() + " to " + cm.getText() + " costing " + namep + ".", previous_points);
                cm.getClient().getChannelServer().removePlayer(cm.getPlayer().getId(), cm.getPlayer().getName());
                cm.getPlayer().setName(cm.getText());
                cm.getClient().getSession().close();
            } else {
                cm.sendOk("You either don't have enough points or " + cm.getText() + " is not an eligible name");
            }
            cm.dispose();
        } else if (sel == 12) {
            if (selection >= 1 && selection <= cm.getPlayer().itemQuantity(5220013)) {
                if (cm.getPlayer().getPoints() > (2147483647 - (selection * 100))) {
                    cm.sendOk("You have too many points.");
                } else {
                    cm.gainItem(5220013, -selection);
                    cm.getPlayer().setPoints(cm.getPlayer().getPoints() + (selection * 100));
                    cm.sendOk("You have lost " + selection + " M Coins and gained " + (selection * 100) + " points. Current Points: " + cm.getPlayer().getPoints());
                    cm.logDonator(" has redeemed " + selection + " M Coin(s) gaining " + (selection * 100) + ".", previous_points);
                }
            }
            cm.dispose();
        } else if (sel == 13) {
            if (selection >= 1 && selection <= 100) {
                if (selection > (cm.getPlayer().getPoints() / 100)) {
                    cm.sendOk("You can only get max " + (cm.getPlayer().getPoints() / 100) + " M Coins. 1 M Coin = 100 points.");
                } else if (!cm.canHold(5220013, selection)) {
                    cm.sendOk("Please make space in CASH tab.");
                } else {
                    cm.gainItem(5220013, selection);
                    cm.getPlayer().setPoints(cm.getPlayer().getPoints() - (selection * 100));
                    cm.sendOk("You have gained " + selection + " M Coins and lost " + (selection * 100) + " points. Current Points: " + cm.getPlayer().getPoints());
                    cm.logDonator(" has gained " + selection + " M Coin(s) costing " + (selection * 100) + ".", previous_points);
                }
            }
            cm.dispose();
        } else if (sel == 16) {
            if (selection >= 1 && selection <= cm.getPlayer().itemQuantity(3993003)) {
                if (cm.getPlayer().getPoints() > (2147483647 - (selection * 100))) {
                    cm.sendOk("You have too many points.");
                } else {
                    cm.gainItem(3993003, -selection);
                    cm.getPlayer().setPoints(cm.getPlayer().getPoints() + (selection * 100));
                    cm.sendOk("You have lost " + selection + " and gained " + (selection * 100) + " points. Current Points: " + cm.getPlayer().getPoints());
                    cm.logDonator(" has redeemed " + selection + " Red Luck Sack(s) gaining " + (selection * 100) + ".", previous_points);
                }
            }
            cm.dispose();
        } else if (sel == 17) {
            if (selection >= 1) {
                if (selection > (cm.getPlayer().getPoints() / 100)) {
                    cm.sendOk("You can only get max " + (cm.getPlayer().getPoints() / 100) + ". 1 Item = 100 points.");
                } else if (!cm.canHold(3993003, selection)) {
                    cm.sendOk("Please make space in SETUP tab.");
                } else {
                    cm.gainItem(3993003, selection);
                    cm.getPlayer().setPoints(cm.getPlayer().getPoints() - (selection * 100));
                    cm.sendOk("You have gained " + selection + " and lost " + (selection * 100) + " points. Current Points: " + cm.getPlayer().getPoints());
                    cm.logDonator(" has gained " + selection + " Red Luck Sack(s) costing " + (selection * 100) + ".", previous_points);
                }
            }
            cm.dispose();
        } else if (sel == 18) {
            if (selection == 0) {
                if (cm.getPlayer().getPoints() < pendantp) {
                    cm.sendOk("You do not have enough points.");
                } else {
                    var marr = cm.getQuestNoRecord(122700);
                    if (marr != null && marr.getCustomData() != null && parseInt(marr.getCustomData()) > cm.getCurrentTime()) {
                        cm.sendOk("You already have a pendant slot.");
                    } else {
                        cm.getQuestRecord(122700).setCustomData("" + (cm.getCurrentTime() + (30 * 24 * 60 * 60 * 1000)));
                        cm.forceStartQuest(7830, "1");
                        cm.getPlayer().setPoints(cm.getPlayer().getPoints() - pendantp);
                        cm.sendOk("You have gained additional pendant slot - 30 days.");
                        cm.sendPendant(true);
                        cm.getPlayer().fakeRelog();
                        cm.logDonator(" has gained Additional Pendant Slot (30 Day) costing " + (pendantp) + ".", previous_points);
                    }
                }
            } else {
                if (cm.getPlayer().getPoints() < pendantp_perm) {
                    cm.sendOk("You do not have enough points.");
                } else {
                    var marr = cm.getQuestNoRecord(122700);
                    if (marr != null && marr.getCustomData() != null && parseInt(marr.getCustomData()) > cm.getCurrentTime()) {
                        cm.sendOk("You already have a pendant slot.");
                    } else {
                        cm.getQuestRecord(122700).setCustomData("" + (cm.getCurrentTime() + (90 * 24 * 60 * 60 * 1000)));
                        cm.forceStartQuest(7830, "1");
                        cm.getPlayer().setPoints(cm.getPlayer().getPoints() - pendantp_perm);
                        cm.sendOk("You have gained additional pendant slot - Permanent.");
                        cm.sendPendant(true);
                        cm.getPlayer().fakeRelog();
                        cm.logDonator(" has gained Additional Pendant Slot (Permanent) costing " + (pendantp) + ".", previous_points);
                    }
                }
            }
            cm.dispose();
        }
    } else if (status == 3) {
        if (sel == 9) {
            var hadSkill = true;
            if (cm.getPlayer().getSkillLevel(skills[itt]) <= 0) {
                hadSkill = false;
                if (cm.getPlayer().getPoints() < skillsp[itt]) {
                    cm.sendOk("You don't have enough points. You have " + cm.getPlayer().getPoints() + " while I need " + skillsp[itt] + ".");
                    cm.dispose();
                    return;
                } else {
                    cm.teachSkill(skills[itt], 1, 0);
                    cm.getPlayer().setPoints(cm.getPlayer().getPoints() - skillsp[itt]);
                }
            }
            cm.putKey(keys[selection], 1, skills[itt]);
            cm.sendOk("There! Thank you for those points. I have given you your skill. Come again~");
            cm.logDonator(" has bought skill [" + skills[itt] + "] costing " + skillsp[itt] + " on key " + keynames[selection] + " (" + keys[selection] + "). [HadSkill: " + hadSkill + "] ", previous_points);
        }
        cm.dispose();

    }
}