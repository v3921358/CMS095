//CUSTOM_CQ IDS: 190000
//ID TO BE USED: 

var status = -1;
var questStatus = -1;

function start() {
	if (cm.isCQActive(190000)) {
		questStatus = parseFloat(cm.getCQInfo(190000));
		switch (questStatus) {
			case 1:
				status = 5;
				break;
			case 2:
				status = 10;
				break;
			case 3:
				status = 13;
				break;
			case 4:
				status = 14;
				break;
			case 5:
				status = 23;
				break;
			case 6:
				status = 26;
				break;
			case 7:
				status = 30;
				break;
		}
	} else {
		cm.startCQ(190000);
		cm.updateCQInfo(190000, "0");
	}
	if (status == -1) {
		cm.EnableUI(1); //Black bars, disable UI
		cm.sendPlayerToNpc("What ... ?");
	} else if (status == 5) {
		cm.EnableUI(1);
		cm.sendPlayerToNpc("Interesting, there seems to be some bad guys over there. I should get rid of them while i'm here.");
	} else if (status == 10) {
		if (cm.completeCQ(190001)) {
			cm.EnableUI(1);
			cm.updateCQInfo(190000, "3");
			cm.sendPlayerToNpc("That should do it. Let's cross the sea over there.");
		} else {
			cm.sendOk("An error has occured.");
			cm.dispose();
		}
	} else if (status == 13) {
		cm.updateCQInfo(190000, "4");
		cm.sendPlayerToNpc("Huh. I think i see a cave over there. Let's check it out!");
		cm.dispose();
	} else if (status == 14) {
		cm.EnableUI(1);
		cm.sendPlayerToNpc("Woah!")
	} else if (status == 23) {
		cm.sendPlayerToNpc("Well, let's say that was random. I've no idea who this old man was.");
	} else if (status == 26) {
		cm.sendPlayerToNpc("HEY! THIS IS HENESYS! WE DID IT.");
	} else if (status == 30) {
		cm.sendPlayerToNpc("We don't really have anything to do there anymore.");
		cm.dispose();
	}
}

function action(m,t,s) {
	if (m < 1) {
		cm.dispose();
	} else {
		++status;
		if (status == 0) {
			sleep(700);
			cm.sendDirectionStatus(3,2);
			sleep(2000);
			cm.enableDirectionStatus(false);
			cm.sendPlayerToNpc("\r\n\r\nWhere am i ... ?");
		} else if (status == 1) {
			cm.sendPlayerToNpc("I mean.. i was on the character selection screen and i blinked and now i'm here...");
		} else if (status == 2) {
			cm.sendPlayerToNpc("I don't even remember neither what this is... Is this #bSXMaple#k? Wait no that can't be.. Maybe it's #bToyStory#k? Actually no.");	
		} else if (status == 3) {
			cm.sendPlayerToNpc("You know what whatever.");
		} else if (status == 4) {
			cm.sendPlayerToNpc("Hey you controlling me. We'd better get moving now or else will #rdie#k here on this beach.")
		} else if (status == 5) {
			cm.updateCQInfo(190000, "1");
			cm.EnableUI(0);
			cm.dispose();
			cm.openNpc(cm.getC(), 2060102);
		} else if (status == 6) {
			cm.startCQ(190001);
			var whatnext = "After you complete this quest, please take the portal to the extreme left of this map.";
			cm.updateCQInfo(190000, "2");
			cm.EnableUI(0);
			sleep(2500);
			cm.getPlayer().dropMessage(-1, whatnext);
			cm.getPlayer().dropMessage(6, whatnext);
			cm.dispose();
		} else if (status == 11) {
			cm.EnableUI(0);
			cm.updateCQInfo(190000, "3");
			cm.getPlayer().dropMessage(-1, "Cross the sea to the right and take the portal. ");
			cm.dispose();
		} else if (status == 15) {
			sleep(400);
			cm.sendDirectionStatus(3,1);
			sleep(450);
			cm.enableDirectionStatus(false);
			cm.enableDirectionStatus(false);
			cm.enableDirectionStatus(false);
			cm.sendNext("You have made a major mistake by entering my lair.");
		} else if (status == 16) {
			cm.sendPlayerToNpc("Huh? Who's this ?!?");
		} else if (status == 17) {
			cm.sendNext("You will not come out alive unless you beat my challenge.");
		} else if (status == 18) {
			cm.sendPlayerToNpc("And what does that imply ?");
		} else if (status == 19) {
			cm.sendNext("You are required to beat 3 waves of total obliteration. Will you take on this challenge ?");
		} else if (status == 20) {
			cm.sendPlayerToNpc("Bring it on !");
		} else if (status == 21) {
			addWaveData();
			cm.sendWaveData();
			cm.EnableUI(0);
			cm.dispose();
		} else if (status == 24) {
			cm.sendPlayerToNpc("Let's take the portal to the right and see what awaits us.");
		} else if (status == 25) {
			cm.updateCQInfo(190000, "6");
			cm.dispose();
		} else if (status == 27) {
			cm.sendPlayerToNpc("We are such badasses. Let's hang out with others now that we are here hahaha.");
		} else if (status == 28) {
			cm.globalDropMessage(6, "Congratulation to " + cm.getPlayer().getName() + " for completing the introduction quest in channel " + cm.getPlayer().getClient().getChannel() + "! Please welcome him/her to XephyrMS!");
			cm.updateCQInfo(190000, "7");
			cm.dispose();
		}
	}
}

function sleep(milliseconds) {
  var start = new Date().getTime();
  for (var i = 0; i < 1e7; i++) {
    if ((new Date().getTime() - start) > milliseconds){
      break;
    }
  }
}

function addWaveData() {
	cm.addWaveMob(cm.getPlayer().getMap(), 0, 100100, -51, 260);
	cm.addWaveMob(cm.getPlayer().getMap(), 0, 100100, 571, 260);
	cm.addWaveMob(cm.getPlayer().getMap(), 0, 100100, 630, 260);
	cm.addWaveMob(cm.getPlayer().getMap(), 1, 100006, -51, 260);
	cm.addWaveMob(cm.getPlayer().getMap(), 1, 100006, 571, 260);
	cm.addWaveMob(cm.getPlayer().getMap(), 1, 100006, 630, 260);
	cm.addWaveMob(cm.getPlayer().getMap(), 2, 100122, -51, 260);
	cm.addWaveMob(cm.getPlayer().getMap(), 2, 100122, 571, 260);
	cm.addWaveMob(cm.getPlayer().getMap(), 2, 100122, 630, 260);
}