//Script by Vade, Editted by Dray
weapons = [[[1003191,1003112], [1022073,1022082,1072344,1082149,1032077,1032078,1032079,1132016], [1012078,1022058,1022060,1012061], [1442116,1432086,1442117,1402096,1432087,1412066,1422067,1382104,1382105,1452111,1462099,1522018,1452112,1462100,1522017,1472122,1332130,1472123,1332131,1482084,1492085,1482085,1492086], [1302149,1312095,1322135,1442113,1382102,1452107,1462094,1522019,1472118,1332126,1482080,1492081,1532039], [1052299,1052300,1052301,1052302,1052303], [2070024,2049306,1182006]], [[8,10], [5,15,5,5,5,5,5,5,5,5,20], [15,20,20,20], [15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15], [10,10,10,10,10,10,10,10,10,10,10,10,10], [5,5,5,5,5], [3,3,5]]];
var itemselection;
var typeselection;
status = 0;

function start() {
	if (cm.haveItem(4031596))
   cm.sendSimple("Hello. I am the Item Vendor of #bXephyrMS#k. In order to purchase the items or look at the shop, you are required in your inventory, to have at least one #rWing Hammer(s)#k. You can recieve these by trading your Vote Points to KIN. Choose wisely, because we won't do refunds. Contact #bDray#k if you encounter any problems. \r\n #L0# Hats #l \r\n #L1# Accessories #l \r\n #L2# Face Accessories #l \r\n #L3# Empress Weapons #l \r\n #L4# Von Leon Weapons #l \r\n #L5# Overalls #l \r\n #L6# Special Items");
    else {
        cm.sendOk("You don't have any #bWing Hammers#k. You can Exchange Vote Points for them via the NPC KIN.");
        cm.dispose();
    }
}

function action(m,t,s) {
    if (m < 1) {
        cm.dispose();
        return;
    } else {
        status++;
    }
    if (status == 1) {
        typeselection = s;
        text = "Which " + (s == 0 ? "Hats" : s == 1 ? "Eye Accessory" : s == 2 ? "Face Accesory" : s == 3 ? "Empress Weapon" : s == 4 ? "Von LeonWeapon" : s == 5 ? "Overall" : "Special Item") + " do you want? \r\n ";
        for (var i = 0; i < weapons[0][s].length; text += "#L"+i+"# #t"+weapons[0][s][i]+"# #v"+weapons[0][s][i]+"# For "+weapons[1][s][i]+" Wing Hammers. #l\r\n", i++);
            cm.sendSimple(text);
    } else if (status == 2) {
            itemselection = s;
            cm.sendYesNo("Are you sure you want to exchange "+weapons[1][typeselection][itemselection]+" #i4031596# for #v"+weapons[0][typeselection][itemselection]+"#?");
    } else if (status == 3) {
        if (cm.haveItem(4031596, weapons[1][typeselection][itemselection])) {
            cm.gainItem(4031596, -weapons[1][typeselection][itemselection]);
            cm.gainItem(weapons[0][typeselection][itemselection], 1);
            cm.sendOk("Great, you have the required items!");
        } else
            cm.sendOk("I'm sorry, you do not have the required items!");
        cm.dispose();
    }
}