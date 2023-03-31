
var noAdvance = "Sorry but you are unable to make this job advancement because you must be at least level ";
var advance = "Please select what job you wish to advance to!";
var unable = "Hey, how's it going? I've been doing well here.";
var noThanks = "\r\n\r\n#L1#Thanks, but not right now.#l";
var check = "Are you sure you wish to become a ";
var congrats = "You have advanced into a ";
var jobDir;
var status;
var newJob;
var newJobName;
var jobData = Array("Beginner", 0,
"Warrior", 100,
"Fighter", 110,
"Crusader", 111,
"Hero", 112,
"PAGE", 120,
"White Knight", 121,
"Paladin", 122,
"Spearman", 130,
"Dragon Knight", 131,
"Dark Knight", 132,
"Magician", 200,
"Fire/Poison Wizard", 210,
"Fire/Poison Mage", 211,
"Fire/Poison Archmage", 212,
"Ice/Lightning Wizard", 220,
"Ice/Lightning Mage", 221,
"Ice/Lightning Archmage", 222,
"Cleric", 230,
"Priest", 231,
"Bishop", 232,
"Bowman", 300,
"Hunter", 310,
"Ranger", 311,
"Bow Master", 312,
"Crossbowman", 320,
"Sniper", 321,
"Crossbow Master", 322,
"Thief", 400,
"Assassin", 410,
"Hermit", 411,
"Night Lord", 412,
"Bandit", 420,
"Chieft Bandit", 421,
"Shadower", 422,
"Pirate", 500,
"Brawler", 510,
"Marauder", 511,
"Buccaneer", 512,
"Gunslinger", 520,
"Outlaw", 521,
"Corsair", 522,
"Mapleleaf Brigadier", 800,
"Game Master", 900,
"Super Game Master", 910,
"Noblesse", 1000,
"First Dawn Warrior", 1100,
"Second Dawn Warrior", 1110,
"Third Dawn Warrior", 1111,
"Fourth Dawn Warrior", 1112,
"First Blaze Wizard", 1200,
"Second Blaze Wizard", 1210,
"Third Blaze Wizard", 1211,
"Fourth Blaze Wizard", 1212,
"First Wind Archer", 1300,
"Second Wind Archer", 1310,
"Third Wind Archer", 1311,
"Fourth Wind Archer", 1312,
"First Night Walker", 1400,
"Second Night Walker", 1410,
"Third Night Walker", 1411,
"Fourth Night Walker", 1412,
"First Thunder Breaker", 1500,
"Second Thunder Breaker", 1510,
"Third Thunder Breaker", 1511,
"Fourth Thunder Breaker", 1512);

function start() {
    status = -1;
    action(1, 0, 0);
}
	
function action(mode, type, selection) {
  if (mode != 1 || status == 1) {
      status ++;
  } else {
      cm.dispose();
  }
    if (status == 0) {
        if (cm.getJob() % 100 == 0) {
            noAdvance += cm.getJob(0) % 1000 == 0 ? "10" : "30";
        } else {
            noAdvance += cm.getJob(0) % 10 == 0 ? "70" : "120";
        noAdvance += " to be able to advance in job and right now you are level " + cm.getLevel() + ".";
        }
    }
    if (cm.getJob() % 10 == 2 || cm.getJob() == 900 || cm.getJob() == 910 || cm.getJob() == 800 || (cm.getJob() > 1000 && cm.getJob() % 10 == 1)) {
        cm.sendOk(unable);
        cm.dispose();
        return;
    } else {
        if (status == 0) {
            if (cm.getJob() % 1000 == 0 || cm.getJob() == 0)
                if ((cm.getLevel() == 8 || cm.getLevel() == 9) && cm.getJob() != 2000) {
                    for (var i = 0; i <= jobData.length; i++)
                        if (i % 2 == 0 && (jobData[i + 1] == (200 + cm.getJob())))
                            advance += "\r\n#L" + jobData[i + 1] + "#" + jobData[i] + "#l";
                } else if (cm.getLevel() >= 10) {
                for (var i = 0; i <= jobData.length; i++)
                    if (i % 2 == 0)
                        if ((jobData[i + 1] % 100 == 0) && (jobData[i + 1] > cm.getJob()) && (jobData[i + 1] < (600 + cm.getJob())))
                            advance += "\r\n#L" + jobData[i + 1] + "#" + jobData[i] + "#l";
            } else {
                cm.sendOk(noAdvance);
                cm.dispose();
                return;
            }
            else if (cm.getJob() % 100 == 0)
                if (cm.getLevel() >= 30) {
                    for (var i = 0; i <= jobData.length; i++)
                        if ((i % 2 == 0 && (jobData[i + 1] % 10 == 0 && jobData[i + 1] % 100 != 0 )) && (jobData[i + 1] > cm.getJob() && jobData[i + 1] <= (cm.getJob() + 30)))						
                            advance += "\r\n#L" + jobData[i + 1] + "#" + jobData[i] + "#l";
                } else {
                cm.sendOk(noAdvance);
                cm.dispose();
                return; 
            }
            else if (cm.getJob() % 10 == 0 || cm.getJob() % 10 == 1) {
                if (cm.getLevel() >= (cm.getJob() % 10 == 1 ? 120 : 70)) {
                    for (var i = 0; i <= jobData.length; i++)
                        if (i % 2 == 0 && (jobData[i + 1] - 1 == cm.getJob()))
                            advance += "\r\n#L" + jobData[i + 1] + "#" + jobData[i] + "#l";
                } else {
                    cm.sendOk(noAdvance);
                    cm.dispose();
                    return;
                }
            } else {
                cm.sendOk(unable);
                cm.dispose();
                return;
            }
            advance += noThanks;
            cm.sendSimple(advance);
        } else if (status == 1)
            if (selection == 1)
                cm.sendOk("See you later then.");
        else {
            newJob = selection;
            for (var i = 0; i <= jobData.length; i++)
                if (i % 2 == 0 && jobData[i + 1] == newJob)
                    newJobName = jobData[i];
            cm.changeJobById(newJob);
            cm.sendOk("You are now a " + newJobName + "!");
        }
    }
}