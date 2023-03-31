/** Author: ZeaL
	NPC Name: 		Onyx
	Map(s): 		Entrance to Free Market (910000000)
	Description: 		BPQ NPC
*/

var points;
var status = -1;
var sel;
var itemSel = -1;
var catagory = -1;
var ITEM;
var ITEMpoints;
var PrevCatagory = -1;


function start() {
	if (cm.getMapId() == 219000000) {
		cm.sendSimple("#b#L40##m219010000##l\r\n#L41##m219020000##l");
		cm.dispose();
		return;
	}
    var record = cm.getQuestRecord(150001);
    points = record.getCustomData() == null ? "0" : record.getCustomData();
	
		ETC = Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2070018, 2070016, 1122017, 5680021, 1112927, 2046375, 2046376, 2531000);
		ETCpoints = Array(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 300000, 150000, 30000, 100000, 150000, 10000, 15000, 400000);
		
		TLEQ = Array(1462050, 1452057, 1432047, 1382057, 1372044, 1332074, 1332073, 1482023, 1442063, 1422037, 1412033, 1402046, 1322060, 1312037, 1302081, 1342011, 1532015);
		TLEQBP = Array("120,000", "120,000", "120,000", "120,000", "120,000", "120,000", "120,000", "120,000", "120,000", "120,000", "120,000", "120,000", "120,000", "120,000", "120,000", "120,000", "120,000");
		TLEQpoints = Array(120000, 120000, 120000, 120000, 120000, 120000, 120000, 120000, 120000, 120000, 120000, 120000, 120000, 120000, 120000, 120000, 120000);
	
		FLR = Array(4000252, 4000252, 4000252);
		FLRBP = Array("37,500", "37,500", "37,500");
		FLRpoints = Array(37500, 37500, 37500);

    cm.sendSimple("Would you like to have a taste of a relentless boss battle?																			\n\r\n\r #b#L1#Check your Current Boss Party Quest Points#l#k																														\n#b#L0#Warp to Boss Party Quest Lobby#l#k																								\r\n\r\n#fUI/UIWindow.img/QuestIcon/4/0#																											 #b#L2#Boss Points Rewards#l#k ");

}

function action(mode, type, selection) {
    if (mode != 1) {
		cm.dispose();
		return;
	}
	status++;
		if (status == 0) {
			sel = selection;
			switch (selection) {
				case 0:
				cm.warp(923020100,0);
				cm.dispose();
				return;
				break;
				case 1:
				cm.sendOk("#bCurrent Points : " + points);
				cm.dispose();
				break;
				case 2:
				cm.sendSimple("What rewards category would you like to view?																				\n\r\n\r #b#L3#Trade for Miscellaneous items #l#k																														\n#b#L4#Trade for Timeless Equipments#l#k 																											\n#b#L5#Cash Chicken#l#k");
				break;
				default:
				cm.sendOk("Error : please talk to me again.");
				cm.dispose();
				break;
			}
		} else if (status == 1) {
			if (selection == 3){
				catagory = selection;
				cm.sendSimple("#rETC#k:\n\r\n\r																												#b#L6##i5221001:#Trade 1000 points (#t5221001#)#l#k																				#b#L7##i5221001:#Trade Enchanted Scroll (#r1000#b points)#l#k																				#b#L8##i3993002:#Trade 10000 points (#t3993002#)#l#k																					#b#L9##i3993002:#Trade Bamboo Luck Sack (#r10000#b points)#l#k																		#b#L10##i2070018:#Trade 300,000 points (Balanced Fury)#l#k 																#b#L11##i2070016:#Trade 150,000 points (Crystal Ilbi)#l#k 																				#b#L12##i1122017:#Trade 30,000 points (Fairy Pendant, lasts 1 day)#l#k 																	#b#L13##i5680021:#Trade 150,000 points (Chair Gachapon Ticket)#l#k 																		#b#L14##i1112927:#Trade 150,000 points (Happy Ring)#l#k																				#b#L15##i2046375:#Trade 10,000 points (Accessory Scroll 20%)#l																		#b#L16##i2046376:#Trade 15,000 points (Accessory Scroll 40%)#l																		#b#L17##i2531000:#Trade 400,000 points (Protection Scroll)#l#k");
			} else if (selection == 4){
				catagory = selection;
				var selStr = "#rTimeless Equipments#k:\n\r\n\r#";
				for (var i = 0; i < TLEQ.length; i++) {
					selStr += "#b#L" + i + "##i" + TLEQ[i] + ":#Trade "+ TLEQBP[i] + " points (#t" + TLEQ[i] + "#)#l\r\n";
				}
				cm.sendSimple(selStr + "#k\n");
			} else if (selection == 5){
				catagory = selection;
				var selStr = "#rFearless Recipe#k:\n\r\n\r#";
				for (var i = 0; i < FLR.length; i++) {
					selStr += "#b#L" + i + "##i" + FLR[i] + ":#Trade "+ FLRBP[i] + " points (#t" + FLR[i] + "#)#l\r\n";
				}
				cm.sendSimple(selStr + "#k\n");
			} else {
				cm.dispose();
			}
		} else if (status == 2){
			itemSel = selection;
			if (catagory == 3){			
				switch (selection) {
				// No item @ case (0~5)
				
//-------------------------------ETC--------------------------------------
				case 6: // Boss points >> Enchanted Scroll
					var record = cm.getQuestRecord(150001);
					var intPoints = parseInt(points);
					if (intPoints < 1000) {
						cm.sendOk("You need at least 1000 points for an Enchanted Scroll.");
						cm.dispose();
					} else {
						cm.sendGetNumber("How many would you like? (1 Enchanted Scroll = 1000 points) (Current Points:" + intPoints + ") (Current Scrolls: " + cm.getPlayer().itemQuantity(5221001) + ")", intPoints / 1000, 1, intPoints / 1000);
					}
				break;
				case 7: // Enchanted Scroll >> Boss points 
					var record = cm.getQuestRecord(150001);
					var intPoints = parseInt(points);
					if (!cm.getPlayer().haveItem(5221001)) {
						cm.sendOk("You need at least 1 Enchanted Scroll.");
						cm.dispose();
					} else {
						cm.sendGetNumber("How many would you like to redeem? (1 Enchanted Scroll = 1000 points) (Current Scrolls: " + cm.getPlayer().itemQuantity(5221001) + ") (Current Points: " + intPoints + ")", cm.getPlayer().itemQuantity(5221001), 1, cm.getPlayer().itemQuantity(5221001));
					}
				break;
				case 8: // Boss points >> Bamboo Luck Sack
					var record = cm.getQuestRecord(150001);
					var intPoints = parseInt(points);
					if (intPoints < 10000) {
						cm.sendOk("You need at least 10000 points for a Bamboo Luck Sack.");
						cm.dispose();
					} else {
						cm.sendGetNumber("How many would you like? (1 Bamboo Luck Sack = 10000 points) (Current Points: " + intPoints + ") (Current:  " + cm.getPlayer().itemQuantity(3993002) + ")", intPoints / 10000, 1, intPoints / 10000);
					}
				break;
				case 9: // Bamboo Luck Sack >> Boss points
					var record = cm.getQuestRecord(150001);
					var intPoints = parseInt(points);
					if (!cm.getPlayer().haveItem(3993002)) {
						cm.sendOk("You need at least 1 Bamboo Luck Sack.");
						cm.dispose();
					} else {
						cm.sendGetNumber("How many would you like to redeem? (1 Bamboo Luck Sack = 10000 points) (Current: " + cm.getPlayer().itemQuantity(3993002) + ") (Current Points: " + intPoints + ")", cm.getPlayer().itemQuantity(3993002), 1, cm.getPlayer().itemQuantity(3993002));
					}
				break;
				
				default :
					ITEM = ETC;
					ITEMpoints = ETCpoints;
					cm.sendYesNo("Do you wanna trade #i" + ETC[selection] + ":##b#t" + ETC[selection] + " #?\n #r(" + ETCpoints[selection] + " points)#b#l\r\n");
				}
			} else if (catagory == 4){
				PrevCatagory = catagory;
				ITEM = TLEQ;
				ITEMpoints = TLEQpoints;
				cm.sendYesNo("Do you wanna trade #i" + TLEQ[selection] + ":##b#t" + TLEQ[selection] + " #?\n #r(" + TLEQpoints[selection] + " points)#b#l\r\n");
			} else if (catagory == 5){
				PrevCatagory = catagory;
				ITEM = FLR;
				ITEMpoints = FLRpoints;
				cm.sendYesNo("Do you wanna trade #i" + FLR[selection] + ":##b#t" + FLR[selection] + " #?\n #r(" + FLRpoints[selection] + " points)#b#l\r\n");
			}
		} else if (status == 3){
			if (PrevCatagory == 4){
				var record = cm.getQuestRecord(150001);
				var intPoints = parseInt(points);
				ITEM = TLEQ;
				ITEMpoints = TLEQpoints;
				
				if (intPoints >= ITEMpoints[itemSel]) {
					if (cm.canHold(ITEM[itemSel])) {
						intPoints -= ITEMpoints[itemSel];
						record.setCustomData(""+intPoints+"");
						cm.gainItem(ITEM[itemSel], 1);
						cm.sendOk("Enjoy your rewards :P");
					} else {
					cm.sendOk("Please check if you have sufficient inventory slot for it.")
					}
				} else {
					cm.sendOk("Please check if you have sufficient points for it, #bCurrent Points : " + points);
				}
				cm.dispose();
			} else if (PrevCatagory == 5) {
				var record = cm.getQuestRecord(150001);
				var intPoints = parseInt(points);
				ITEM = FLR;
				ITEMpoints = FLRpoints;
				
				if (intPoints >= ITEMpoints[itemSel]) {
					if (cm.canHold(ITEM[itemSel])) {
						intPoints -= ITEMpoints[itemSel];
						record.setCustomData(""+intPoints+"");
						cm.gainItem(ITEM[itemSel], 1);
						cm.sendOk("Enjoy your rewards :P");
					} else {
					cm.sendOk("Please check if you have sufficient inventory slot for it.")
					}
				} else {
					cm.sendOk("Please check if you have sufficient points for it, #bCurrent Points : " + points);
				}
				cm.dispose();
			} else {
				var record = cm.getQuestRecord(150001);
				var intPoints = parseInt(points);
				switch (itemSel){
					case 6: //Transaction : Boss points >> Enchanted Scroll
						if (selection >= 1 && selection <= (intPoints / 1000)) {
							if (selection > (intPoints / 1000)) {
								cm.sendOk("You can only get max " + (intPoints / 1000) + ". 1 Item = 10000 points.");
							} else if (!cm.canHold(5221001, selection)) {
								cm.sendOk("Please make space in SETUP tab.");
							} else {
								cm.gainItem(5221001, selection);
								intPoints -= selection * 1000;
								record.setCustomData(""+intPoints+"");
								cm.sendOk("You have gained " + selection + " and lost " + (selection * 1000) + " points. Current Points: " + intPoints);
							}
						}
					break;
					
					case 7: //Transaction : Enchanted Scroll >> Boss points 
						if (selection >= 1 && selection <= cm.getPlayer().itemQuantity(5221001)) {
							if (intPoints > (2147483647 - (selection*1000))) {
								cm.sendOk("You have too many points.");
							} else {
								cm.gainItem(5221001, -selection);
								intPoints += (selection*1000);
								record.setCustomData(""+intPoints+"");
								cm.sendOk("You have lost " + selection + " and gained " + (selection * 1000) + " points. Current Points: #r" + intPoints +"#l#k");
							}
						}
					break;
					
					case 8: //Transaction : Boss points >> Bamboo Luck Sack 
						if (selection >= 1 && selection <= (intPoints / 10000)) {
							if (selection > (intPoints / 10000)) {
								cm.sendOk("You can only get max " + (intPoints / 10000) + ". 1 Item = 10000 points.");
							} else if (!cm.canHold(3993002, selection)) {
								cm.sendOk("Please make space in SETUP tab.");
							} else {
								cm.gainItem(3993002, selection);
								intPoints -= selection * 10000;
								record.setCustomData(""+intPoints+"");
								cm.sendOk("You have gained " + selection + " and lost " + (selection * 10000) + " points. Current Points: " + intPoints);
							}
						}
					break;
					
					case 9: //Transaction : Bamboo Luck Sack >> Boss points
						if (selection >= 1 && selection <= cm.getPlayer().itemQuantity(3993002)) {
							if (intPoints > (2147483647 - (selection*10000))) {
								cm.sendOk("You have too many points.");
							} else {
								cm.gainItem(3993002, -selection);
								intPoints += (selection*10000);
								record.setCustomData(""+intPoints+"");
								cm.sendOk("You have lost " + selection + " and gained " + (selection * 10000) + " points. Current Points: #r" + intPoints +"#l#k");
							}
						}
					break;
					
					case 12: //Transaction : Fairy Pendant (1 day)
						if (intPoints >= ETCpoints[12]) {
							if (cm.canHold(ETC[12])) {
							intPoints -= ETCpoints[12];
							record.setCustomData(""+intPoints+"");
							cm.gainItemPeriod(ETC[12], 1, 1);
							cm.sendOk("Enjoy your rewards :P Current Points : #b" + intPoints);
							} else {
							cm.sendOk("Please check if you have sufficient inventory slot for it.")
							}
						} else {
							cm.sendOk("Please check if you have sufficient points for it, #bCurrent Points : " + intPoints);
						}
					break;
					
					default:
					//cm.sendOk("itemSel : "+ itemSel + " Number : " + selection + " intPoints : " + intPoints + " BP : " + BP);
					//cm.dispose();
					var record = cm.getQuestRecord(150001);
					var intPoints = parseInt(points);
					
					if (intPoints >= ITEMpoints[itemSel]) {
						if (cm.canHold(ITEM[itemSel])) {
						intPoints -= ITEMpoints[itemSel];
						record.setCustomData(""+intPoints+"");
						cm.gainItem(ITEM[itemSel], 1);
						cm.sendOk("Enjoy your rewards :P");
						} else {
						cm.sendOk("Please check if you have sufficient inventory slot for it.")
						}
					} else {
						cm.sendOk("Please check if you have sufficient points for it, #bCurrent Points : " + points);
					}
					cm.dispose();
				}
			}
		}
	}