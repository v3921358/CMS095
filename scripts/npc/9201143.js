/*
 * @Replayzxc
 */

var status = -1;

function start() {
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
	if (status >= 2 || status == 0) {
	    cm.dispose();
	    return;
	}
	status--;
    }
    
    if (status == 0 && cm.getPlayerStat("LVL") >= 10 && cm.getJob() == 0) { // for otherr job other than mage
        cm.sendSimple("Hello #r#h ##k! I am your #rExplorer Job Advance NPC#k. \r\n#b#L0#Warrior!#l\r\n#b#L1#Magician!#l\r\n#b#L2#Thief!#l\r\n#b#L3#Bowman!#k#l\r\n#b#L4#Pirate!#l");
    } else if (status == 0 && cm.getPlayerStat("LVL") == 8 && cm.getJob() == 0) { // well, just for players who level 8 (mage only)
        cm.sendSimple("Hello #r#h ##k! I am your #rExplorer Job Advance NPC#k. \r\n#b#L1#Magician!#l");
        
    } else if (status == 0 && cm.getPlayerStat("LVL") >= 30 && cm.getJob() == 100) { // 30
        cm.sendSimple("Hello #r#h ##k! I am your #rExplorer Job Advance NPC#k. \r\n#b#L10#Warrior!#l");
    } else if (status == 0 && cm.getPlayerStat("LVL") >= 30 && cm.getJob() == 200) { // 30
        cm.sendSimple("Hello #r#h ##k! I am your #rExplorer Job Advance NPC#k. \r\n#b#L20#Magician!#l");
    } else if (status == 0 && cm.getPlayerStat("LVL") >= 30 && cm.getJob() == 300) { // 30
        cm.sendSimple("Hello #r#h ##k! I am your #rExplorer Job Advance NPC#k. \r\n#b#L30#Bowman1#l");
    } else if (status == 0 && cm.getPlayerStat("LVL") >= 30 && cm.getJob() == 400) { // 30
        cm.sendSimple("Hello #r#h ##k! I am your #rExplorer Job Advance NPC#k. \r\n#b#L40#Thief!#l");
    } else if (status == 0 && cm.getPlayerStat("LVL") >= 30 && cm.getJob() == 500) { // 30
        cm.sendSimple("Hello #r#h ##k! I am your #rExplorer Job Advance NPC#k. \r\n#b#L50#Pirate!#l");
        
    } else if (status == 0 && cm.getPlayerStat("LVL") >= 70 && cm.getJob() == 110) { // 70
        cm.sendSimple("Hello #r#h ##k! I am your #rExplorer Job Advance NPC#k. \r\n#b#L1000#Warrior!#l");
    } else if (status == 0 && cm.getPlayerStat("LVL") >= 70 && cm.getJob() == 120) { // 70
        cm.sendSimple("Hello #r#h ##k! I am your #rExplorer Job Advance NPC#k. \r\n#b#L1000#Warrior!#l");
    } else if (status == 0 && cm.getPlayerStat("LVL") >= 70 && cm.getJob() == 130) { // 70
        cm.sendSimple("Hello #r#h ##k! I am your #rExplorer Job Advance NPC#k. \r\n#b#L1000#Warrior!#l");
        
    } else if (status == 0 && cm.getPlayerStat("LVL") >= 70 && cm.getJob() == 210) { // 70
        cm.sendSimple("Hello #r#h ##k! I am your #rExplorer Job Advance NPC#k. \r\n#b#L2000#Magician!#l");
    } else if (status == 0 && cm.getPlayerStat("LVL") >= 70 && cm.getJob() == 220) { // 70
        cm.sendSimple("Hello #r#h ##k! I am your #rExplorer Job Advance NPC#k. \r\n#b#L2000#Magician!#l");
    } else if (status == 0 && cm.getPlayerStat("LVL") >= 70 && cm.getJob() == 230) { // 70
        cm.sendSimple("Hello #r#h ##k! I am your #rExplorer Job Advance NPC#k. \r\n#b#L2000#Magician!#l");
        
    } else if (status == 0 && cm.getPlayerStat("LVL") >= 70 && cm.getJob() == 310) { // 70    
        cm.sendSimple("Hello #r#h ##k! I am your #rExplorer Job Advance NPC#k. \r\n#b#L3000#Bowman1#l");
    } else if (status == 0 && cm.getPlayerStat("LVL") >= 70 && cm.getJob() == 320) { // 70    
        cm.sendSimple("Hello #r#h ##k! I am your #rExplorer Job Advance NPC#k. \r\n#b#L3000#Bowman1#l");
        
    } else if (status == 0 && cm.getPlayerStat("LVL") >= 70 && cm.getJob() == 410) { //70
        cm.sendSimple("Hello #r#h ##k! I am your #rExplorer Job Advance NPC#k. \r\n#b#L4000#Thief!#l");
    } else if (status == 0 && cm.getPlayerStat("LVL") >= 70 && cm.getJob() == 420) { //70
        cm.sendSimple("Hello #r#h ##k! I am your #rExplorer Job Advance NPC#k. \r\n#b#L4000#Thief!#l");
        
    } else if (status == 0 && cm.getPlayerStat("LVL") >= 70 && cm.getJob() == 510) { // 70
        cm.sendSimple("Hello #r#h ##k! I am your #rExplorer Job Advance NPC#k. \r\n#b#L5000#Pirate!#l");
    } else if (status == 0 && cm.getPlayerStat("LVL") >= 70 && cm.getJob() == 520) { // 70
        cm.sendSimple("Hello #r#h ##k! I am your #rExplorer Job Advance NPC#k. \r\n#b#L5000#Pirate!#l");
      
    } else if (status == 0 && cm.getPlayerStat("LVL") >= 120 && cm.getJob() == 111) { // 120
        cm.sendSimple("Hello #r#h ##k! I am your #rExplorer Job Advance NPC#k. \r\n#b#L10000#Warrior!#l");  
    } else if (status == 0 && cm.getPlayerStat("LVL") >= 120 && cm.getJob() == 121) { // 120
        cm.sendSimple("Hello #r#h ##k! I am your #rExplorer Job Advance NPC#k. \r\n#b#L10000#Warrior!#l");
    } else if (status == 0 && cm.getPlayerStat("LVL") >= 120 && cm.getJob() == 131) { // 120
        cm.sendSimple("Hello #r#h ##k! I am your #rExplorer Job Advance NPC#k. \r\n#b#L10000#Warrior!#l");
        
    } else if (status == 0 && cm.getPlayerStat("LVL") >= 120 && cm.getJob() == 211) { // 120
        cm.sendSimple("Hello #r#h ##k! I am your #rExplorer Job Advance NPC#k. \r\n#b#L20000#Magician!#l");
    } else if (status == 0 && cm.getPlayerStat("LVL") >= 120 && cm.getJob() == 221) { // 120
        cm.sendSimple("Hello #r#h ##k! I am your #rExplorer Job Advance NPC#k. \r\n#b#L20000#Magician!#l");
    } else if (status == 0 && cm.getPlayerStat("LVL") >= 120 && cm.getJob() == 231) { // 120
        cm.sendSimple("Hello #r#h ##k! I am your #rExplorer Job Advance NPC#k. \r\n#b#L20000#Magician!#l");
    
    } else if (status == 0 && cm.getPlayerStat("LVL") >= 120 && cm.getJob() == 311) { // 120    
        cm.sendSimple("Hello #r#h ##k! I am your #rExplorer Job Advance NPC#k. \r\n#b#L30000#Bowman1#l");
    } else if (status == 0 && cm.getPlayerStat("LVL") >= 120 && cm.getJob() == 321) { // 120    
        cm.sendSimple("Hello #r#h ##k! I am your #rExplorer Job Advance NPC#k. \r\n#b#L30000#Bowman1#l");
    
    } else if (status == 0 && cm.getPlayerStat("LVL") >= 120 && cm.getJob() == 411) { // 120
        cm.sendSimple("Hello #r#h ##k! I am your #rExplorer Job Advance NPC#k. \r\n#b#L40000#Thief!#l");
    } else if (status == 0 && cm.getPlayerStat("LVL") >= 120 && cm.getJob() == 421) { //120
        cm.sendSimple("Hello #r#h ##k! I am your #rExplorer Job Advance NPC#k. \r\n#b#L40000#Thief!#l");
        
    } else if (status == 0 && cm.getPlayerStat("LVL") >= 120 && cm.getJob() == 511) { // 120
        cm.sendSimple("Hello #r#h ##k! I am your #rExplorer Job Advance NPC#k. \r\n#b#L5000#Pirate!#l");
    } else if (status == 0 && cm.getPlayerStat("LVL") >= 120 && cm.getJob() == 521) { // 120
        cm.sendSimple("Hello #r#h ##k! I am your #rExplorer Job Advance NPC#k. \r\n#b#L5000#Pirate!#l");
    
    } else if (status == 1) {
        if (selection == 0 && cm.getPlayerStat("LVL") >= 10 && cm.getJob() == 0) {
            cm.resetStats(35, 4, 4, 4);
	    cm.expandInventory(1, 4);
	    cm.expandInventory(4, 4);
            cm.gainItem(1402001, 1);
            cm.changeJob(100);   
        } else if (selection == 1 && cm.getPlayerStat("LVL") >= 8 && cm.getJob() == 0) {
            cm.resetStats(4, 4, 20, 4);
	    cm.expandInventory(1, 4);
	    cm.expandInventory(4, 4);
            cm.gainItem(1372005, 1);
	    cm.changeJob(200); 
        } else if (selection == 2 && cm.getPlayerStat("LVL") >= 10 && cm.getJob() == 0) {
            cm.resetStats(4, 25, 4, 4);
	    cm.expandInventory(1, 4);
	    cm.expandInventory(4, 4);
            cm.gainItem(1332063,1);
            cm.gainItem(1472000,1);
            cm.gainItem(2070015, 500);
	    cm.changeJob(400);
        } else if (selection == 3 && cm.getPlayerStat("LVL") >= 10 && cm.getJob() == 0) {
            cm.resetStats(4, 25, 4, 4);
	    cm.expandInventory(1, 4);
	    cm.expandInventory(4, 4);
            cm.gainItem(1452002, 1);
            cm.gainItem(2060000, 1000);
	    cm.changeJob(300);
        } else if (selection == 4 && cm.getPlayerStat("LVL") >= 10 && cm.getJob() == 0) {
            cm.resetStats(4, 20, 4, 4);
            cm.expandInventory(1, 4);
	    cm.expandInventory(4, 4);
	    cm.gainItem(1482014, 1);
	    cm.gainItem(1492014, 1);
	    cm.gainItem(2330006, 600);
	    cm.gainItem(2330006, 600);
	    cm.gainItem(2330006, 600);
            cm.changeJob(500);
        } else if (selection == 10 && cm.getPlayerStat("LVL") >= 30 && cm.getJob() == 100) { // start of warrior
            cm.sendSimple("\r\n#b#L110#Fighter!#l \r\n#b#L130#Spearman#l \r\n#b#L120#Page#l");  
        } else if (selection == 1000 && cm.getPlayerStat("LVL") >= 70 && cm.getJob() == 110) {
            cm.sendSimple("\r\n#b#L111#Crusader!#l");
        } else if (selection == 1000 && cm.getPlayerStat("LVL") >= 70 && cm.getJob() == 120) {
            cm.sendSimple("\r\n#b#L121#Knight!#l");
        } else if (selection == 1000 && cm.getPlayerStat("LVL") >= 70 && cm.getJob() == 130) {
            cm.sendSimple("\r\n#b#L131#Dragon Knight!#l"); 
        } else if (selection == 10000 && cm.getPlayerStat("LVL") >= 120 && cm.getJob() == 111) {
            cm.sendSimple("\r\n#b#L112#Hero!#l");     
        } else if (selection == 10000 && cm.getPlayerStat("LVL") >= 120 && cm.getJob() == 121) {
            cm.sendSimple("\r\n#b#L122#Paladin!#l"); 
        } else if (selection == 10000 && cm.getPlayerStat("LVL") >= 120 && cm.getJob() == 131) {
            cm.sendSimple("\r\n#b#L132#Dark Knight!#l"); 
        } else if (selection == 20 && cm.getPlayerStat("LVL") >= 30 && cm.getJob() == 200) { // start of mage
            cm.sendSimple("\r\n#b#L210#Wizard!(Fire/Poison)#l \r\n#b#L220#Wizard!(Ice/Lightening)#l \r\n#b#L230#Cleric!#l"); 
        } else if (selection == 2000 && cm.getPlayerStat("LVL") >= 70 && cm.getJob() == 210) { 
            cm.sendSimple("\r\n#b#L211#Mage!(Fire/Poison)#l"); 
        } else if (selection == 2000 && cm.getPlayerStat("LVL") >= 70 && cm.getJob() == 220) { 
            cm.sendSimple("\r\n#b#L221#Mage!(Ice/Lightening)#l"); 
        } else if (selection == 2000 && cm.getPlayerStat("LVL") == 70 && cm.getJob() == 230) { 
            cm.sendSimple("\r\n#b#L231#Priest!#l");
        } else if (selection == 20000 && cm.getPlayerStat("LVL") >= 120 && cm.getJob() == 211) {
            cm.sendSimple("\r\n#b#L212#Arch Mage!(Fire/Poison)#l");
        } else if (selection == 20000 && cm.getPlayerStat("LVL") >= 120 && cm.getJob() == 221) {
            cm.sendSimple("\r\n#b#L222#Arch Mage!(Ice/Lightening)#l");
        } else if (selection == 20000 && cm.getPlayerStat("LVL") >= 120 && cm.getJob() == 231) {
            cm.sendSimple("\r\n#b#L232#Bishop!#l");
        } else if (selection == 30 && cm.getPlayerStat("LVL") >= 30 && cm.getJob() == 300) { // start of bowman
            cm.sendSimple("\r\n#b#L310#Hunter!#l \r\n#b#L320#Crossbow Man!#l");   
        } else if (selection == 3000 && cm.getPlayerStat("LVL") >= 70 && cm.getJob() == 310) {
            cm.sendSimple("\r\n#b#L311#Ranger!#l"); 
        } else if (selection == 3000 && cm.getPlayerStat("LVL") >= 70 && cm.getJob() == 320) {
            cm.sendSimple("\r\n#b#L321#Sniper!#l");
        } else if (selection == 30000 && cm.getPlayerStat("LVL") >= 120 && cm.getJob() == 311) {
            cm.sendSimple("\r\n#b#L312#Bowmaster!#l"); 
        } else if (selection == 30000 && cm.getPlayerStat("LVL") >= 120 && cm.getJob() == 321) {
            cm.sendSimple("\r\n#b#L322#Crossbow Master!#l"); 
        } else if (selection == 40 && cm.getPlayerStat("LVL") >= 30 && cm.getJob() == 400) { // start of thief
            cm.sendSimple("\r\n#b#L410#Assassin!#l \r\n#b#L420#Bandit!#l"); 
        } else if (selection == 4000 && cm.getPlayerStat("LVL") >= 70 && cm.getJob() == 410) { 
            cm.sendSimple("\r\n#b#L411#Hermit!#l"); 
        } else if (selection == 4000 && cm.getPlayerStat("LVL") >= 70 && cm.getJob() == 420) { 
            cm.sendSimple("\r\n#b#L421#Cheif Bandit!#l");
        } else if (selection == 40000 && cm.getPlayerStat("LVL") >= 120 && cm.getJob() == 411) { 
            cm.sendSimple("\r\n#b#L412#Night Lord!#l"); 
        } else if (selection == 40000 && cm.getPlayerStat("LVL") >= 120 && cm.getJob() == 421) { 
            cm.sendSimple("\r\n#b#L422#Shadower!#l"); 
        } else if (selection == 50 && cm.getPlayerStat("LVL") >= 30 && cm.getJob() == 500) { // start of pirate
            cm.sendSimple("\r\n#b#L510#Infighter!#l \r\n#b#L520#Gunslinger!#l");
        } else if (selection == 5000 && cm.getPlayerStat("LVL") >= 70 && cm.getJob() == 510) { 
            cm.sendSimple("\r\n#b#L511#Buccaneer!#l");  
        } else if (selection == 5000 && cm.getPlayerStat("LVL") >= 70 && cm.getJob() == 520) { 
            cm.sendSimple("\r\n#b#L521#Valkyrie!#l");
        } else if (selection == 50000 && cm.getPlayerStat("LVL") >= 120 && cm.getJob() == 511) { 
            cm.sendSimple("\r\n#b#L512#Viper!#l");
        } else if (selection == 50000 && cm.getPlayerStat("LVL") >= 120 && cm.getJob() == 521) { 
            cm.sendSimple("\r\n#b#L522#Captain!#l");
        } else {
           cm.sendOk("Please check with your requirement."); 
       }
   }// here for 2nd job start above
   if (status == 2) { 
       if (selection == 110 && cm.getPlayerStat("LVL") >= 30 && cm.getJob() == 100) { 
           cm.changeJob(110);
       } else if (selection == 120 && cm.getPlayerStat("LVL") >= 30 && cm.getJob() == 100) {
           cm.changeJob(120);
       } else if (selection == 130 && cm.getPlayerStat("LVL") >= 30 && cm.getJob() == 100) {
           cm.changeJob(130);
       } else if (selection == 210 && cm.getPlayerStat("LVL") >= 30 && cm.getJob() == 200) {
           cm.changeJob(210);
       } else if (selection == 220 && cm.getPlayerStat("LVL") >= 30 && cm.getJob() == 200) {
           cm.changeJob(220);
       } else if (selection == 230 && cm.getPlayerStat("LVL") >= 30 && cm.getJob() == 200) {
           cm.changeJob(230);
       } else if (selection == 310 && cm.getPlayerStat("LVL") >= 30 && cm.getJob() == 300) {
           cm.changeJob(310);
       } else if (selection == 320 && cm.getPlayerStat("LVL") >= 30 && cm.getJob() == 300) {
           cm.changeJob(320);
       } else if (selection == 410 && cm.getPlayerStat("LVL") >= 30 && cm.getJob() == 400) {
           cm.changeJob(410);
       } else if (selection == 420 && cm.getPlayerStat("LVL") >= 30 && cm.getJob() == 400) {
           cm.changeJob(420);
       } else if (selection == 510 && cm.getPlayerStat("LVL") >= 30 && cm.getJob() == 500) {
           cm.changeJob(510);
       } else if (selection == 520 && cm.getPlayerStat("LVL") >= 30 && cm.getJob() == 500) {
           cm.changeJob(520);
       } else {
  //       cm.sendOk("Please check with your requirement to ensure you are level 30");
           cm.dispose();
       } // 3rd job advance
       if (selection == 111 && cm.getPlayerStat("LVL") >= 70 && cm.getJob() == 110) {
           cm.changeJob(111);
       } else if (selection == 121 && cm.getPlayerStat("LVL") >= 70 && cm.getJob() == 120) {
           cm.changeJob(121);
       } else if (selection == 131 && cm.getPlayerStat("LVL") >= 70 && cm.getJob() == 130) {
           cm.changeJob(131);
       } else if (selection == 211 && cm.getPlayerStat("LVL") >= 70 && cm.getJob() == 210) {
           cm.changeJob(211);
       } else if (selection == 221 && cm.getPlayerStat("LVL") >= 70 && cm.getJob() == 220) {
           cm.changeJob(221);
       } else if (selection == 231 && cm.getPlayerStat("LVL") >= 70 && cm.getJob() == 230) {
           cm.changeJob(231);
       } else if (selection == 311 && cm.getPlayerStat("LVL") >= 70 && cm.getJob() == 310) {
           cm.changeJob(311);
       } else if (selection == 321 && cm.getPlayerStat("LVL") >= 70 && cm.getJob() == 320) {
           cm.changeJob(321);
       } else if (selection == 411 && cm.getPlayerStat("LVL") >= 70 && cm.getJob() == 410) {
           cm.changeJob(411);
       } else if (selection == 421 && cm.getPlayerStat("LVL") >= 70 && cm.getJob() == 420) {
           cm.changeJob(421);
       } else if (selection == 511 && cm.getPlayerStat("LVL") >= 70 && cm.getJob() == 510) {
           cm.changeJob(511);
       } else if (selection == 521 && cm.getPlayerStat("LVL") >= 70 && cm.getJob() == 520) {
           cm.changeJob(521);
       } else {
    //     cm.sendOk("Please check with your requirement to ensure you are level 70.");
           cm.dispose();
       } // 4th job advance [todo learn skill]
       if (selection == 112 && cm.getPlayerStat("LVL") >= 120 && cm.getJob() == 111) {
           cm.changeJob(112);
       } else if (selection == 122 && cm.getPlayerStat("LVL") >= 120 && cm.getJob() == 121) {
           cm.changeJob(122);
       } else if (selection == 132 && cm.getPlayerStat("LVL") >= 120 && cm.getJob() == 131) {
           cm.changeJob(132);
       } else if (selection == 212 && cm.getPlayerStat("LVL") >= 120 && cm.getJob() == 211) {
           cm.changeJob(212);
       } else if (selection == 222 && cm.getPlayerStat("LVL") >= 120 && cm.getJob() == 221) {
           cm.changeJob(222);
       } else if (selection == 232 && cm.getPlayerStat("LVL") >= 120 && cm.getJob() == 231) {
           cm.changeJob(232);
       } else if (selection == 312 && cm.getPlayerStat("LVL") >= 120 && cm.getJob() == 311) {
           cm.changeJob(312);
       } else if (selection == 322 && cm.getPlayerStat("LVL") >= 120 && cm.getJob() == 321) {
           cm.changeJob(322);
       } else if (selection == 412 && cm.getPlayerStat("LVL") >= 120 && cm.getJob() == 411) {
           cm.changeJob(412);
       } else if (selection == 422 && cm.getPlayerStat("LVL") >= 120 && cm.getJob() == 421) {
           cm.changeJob(422);
       } else if (selection == 512 && cm.getPlayerStat("LVL") >= 120 && cm.getJob() == 511) {
           cm.changeJob(512);
       } else if (selection == 522 && cm.getPlayerStat("LVL") >= 120 && cm.getJob() == 521) {
           cm.changeJob(522);
       } else {
 //        cm.sendOk("Please check with your requirement to ensure you are level 120.");
           cm.dispose();
        }
    }
}

