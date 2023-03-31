var status = 0;
var itemid = null;
var quantity = null;
var cost = null;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();

    } else if (mode == 0) {
        cm.sendOk("#bCome back later!");
        cm.dispose();

    } else {
        if (mode == 1)
            status++;
        else
            status--;

        if (status == 0) {
            // cm.sendOk("#bCome back later!");
            // cm.dispose();
            cm.sendNext("#bWelcome to KisaMS Donation Reward Shop - #rPart 2#b! You can get donation potions from donating on our website.\r\n\r\nYou currently have: #r#e" + cm.getPlayer().getPoints() + " Donation Points.#n#k");
        } else if (status == 1) {
            cm.sendSimple("#ePick what you'd like:#b" +
                    "\r\n#L9900# Item Of Choice" +
                    "\r\n#L9940# 10k NX for 300 Points" +
                    "\r\n#L9960# GM Scrolls" +
                    "\r\n#L9920# Cubes, Premium Cubes, & Super Miracle Cubes");

            //not done
        } else if (selection == 9900) {
            cm.dispose();
            cm.openNpc(9000017);
        } else if (selection == 10) {
            itemid = null
            cost = null
            quantity = null
        } else if (selection == 9920) {
            cm.sendSimple("#e#rWhat would you like?#k\r\n\r\n" +
                    "#L40# #v5062000# 1 #t5062000# - 100 Points\r\n"
                    + "#L50# #v5062000# 5 #t5062000#s - 250 Points\r\n" +
                    "#L60# #v5062001# 1 #t5062001# - 350 Points\r\n" +
                    "#L70# #v5062001# 5 #t5062001#s - 850 Points\r\n" +
                    "#L310# #v5062002#  1 #t5062002# - 500 Points\r\n" +
                    "#L320# #v5062002#  5 #t5062002#s - 1000 Points"
                    );
            //Cubes
        } else if (selection == 310) {
            itemid = 5062002
            cost = 500
            quantity = 1
        } else if (selection == 320) {
            itemid = 5062002
            cost = 1000
            quantity = 5
        } else if (selection == 40) {
            itemid = 5062000
            cost = 100
            quantity = 1
        } else if (selection == 50) {
            itemid = 5062000
            cost = 250
            quantity = 5
        } else if (selection == 60) {
            itemid = 5062001
            cost = 350
            quantity = 1
        } else if (selection == 70) {
            itemid = 5062001
            cost = 850
            quantity = 5
        } else if (selection == 9940) {//NX
            if (cm.getPlayer().getPoints() >= 300) {
                cm.getPlayer().gainPoints(-300);
                //cm.getPlayer().modifyCSPoints(1, 10000);
                cm.sendOk("#e#rYou have purchased 10k Cash for 300 Points.\r\n#kYou now have #r#e" + cm.getPlayer().getPoints() + " Donation Points#k and #r" + cm.getPlayer().getCSPoints(4) + " Cash.#n");
                cm.dispose();
            } else {
                cm.sendOk("You don't have enough points!");
                cm.dispose();
            }
        } else if (selection == 9960) {
            cm.sendSimple("#e#rWhat would you like?#k\r\n\r\n" +
                    "#L110#  #v2044503# #t2044503# - 250 Points\r\n" +
                    "#L120#  #v2044703# #t2044703# - 250 Points\r\n" +
                    "#L130#  #v2044603# #t2044603# - 250 Points\r\n" +
                    "#L140#  #v2043303# #t2043303# - 250 Points\r\n" +
                    "#L150#  #v2040807# #t2040807# - 250 Points\r\n" +
                    "#L160#  #v2040806# #t2040806# - 250 Points\r\n" +
                    "#L180#  #v2043103# #t2043103# - 250 Points\r\n" +
                    "#L190#  #v2043203# #t2043203# - 250 Points\r\n" +
                    "#L200#  #v2043003# #t2043003# - 250 Points\r\n" +
                    "#L210#  #v2040506# #t2040506# - 250 Points\r\n" +
                    "#L170#  #v2044403# #t2044403# - 250 Points\r\n" +
                    "#L220#  #v2040709# #t2040709# - 250 Points\r\n" +
                    "#L230#  #v2040710# #t2040710# - 250 Points\r\n" +
                    "#L240#  #v2040711# #t2040711# - 250 Points\r\n" +
                    "#L250#  #v2044303# #t2044303# - 250 Points\r\n" +
                    "#L260#  #v2043803# #t2043803# - 250 Points\r\n" +
                    "#L270#  #v2044103# #t2044103# - 250 Points\r\n" +
                    "#L280#  #v2044203# #t2044203# - 250 Points\r\n" +
                    "#L290#  #v2044003# #t2044003# - 250 Points\r\n" +
                    "#L300#  #v2043703# #t2043703# - 250 Points\r\n" +
                    "#L330#  #v2044908# #t2044908# - 250 Points\r\n");
            //Scrolls
        } else if (selection == 110) {
            itemid = 2044503
            cost = 250
            quantity = 1
        } else if (selection == 120) {
            itemid = 2044703
            cost = 250
            quantity = 1
        } else if (selection == 130) {
            itemid = 2044603
            cost = 250
            quantity = 1
        } else if (selection == 140) {
            itemid = 2043303
            cost = 250
            quantity = 1
        } else if (selection == 150) {
            itemid = 2040807
            cost = 250
            quantity = 1
        } else if (selection == 170) {
            itemid = 2044403
            cost = 250
            quantity = 1
        } else if (selection == 180) {
            itemid = 2043103
            cost = 250
            quantity = 1
        } else if (selection == 160) {
            itemid = 2040806
            cost = 250
            quantity = 1
        } else if (selection == 190) {
            itemid = 2043203
            cost = 250
            quantity = 1
        } else if (selection == 200) {
            itemid = 2043003
            cost = 250
            quantity = 1
        } else if (selection == 210) {
            itemid = 2040506
            cost = 250
            quantity = 1
        } else if (selection == 220) {
            itemid = 2040709
            cost = 250
            quantity = 1
        } else if (selection == 230) {
            itemid = 2040710
            cost = 250
            quantity = 1
        } else if (selection == 240) {
            itemid = 2040711
            cost = 250
            quantity = 1
        } else if (selection == 250) {
            itemid = 2044303
            cost = 250
            quantity = 1
        } else if (selection == 260) {
            itemid = 2043803
            cost = 250
            quantity = 1
        } else if (selection == 270) {
            itemid = 2044103
            cost = 250
            quantity = 1
        } else if (selection == 280) {
            itemid = 2044203
            cost = 250
            quantity = 1
        } else if (selection == 290) {
            itemid = 2044003
            cost = 250
            quantity = 1
        } else if (selection == 300) {
            itemid = 2043703
            cost = 250
            quantity = 1
        } else if (selection == 330) {
            itemid = 2044908
            cost = 250
            quantity = 1
        }
        if (status == 3 && cm.getPlayer().getPoints() >= cost && itemid != null) {
            cm.sendYesNo("#e#bYou would like to receive " + quantity + "\r\n\r\n#n#i" + itemid + "# - #t" + itemid + "##b#e - for the cost of #r" + cost + " Points#b\r\nAre you ready to proceed?\r\n\r\n#n#k");
        } else if (status == 3 && cm.getPlayer().getPoints() < cost) {
            cm.sendOk("#r#eYou need " + cost + " Donation Points to receive this item.#n");
        } else if (status == 4 && cm.getPlayer().getPoints() >= cost && itemid != null) {
            cm.getPlayer().gainPoints(-cost);
            cm.gainItem(itemid, quantity);
            cm.sendOk("#bThanks for donating! You now have #r#e" + cm.getPlayer().getPoints() + " Donation Points.#n#k\r\nEnjoy your #i" + itemid + "# (;");
            cm.dispose();
        }
    }
}