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
        cm.sendSimple("Hello #r#h ##k! I am your #rExplorer Job Advance NPC#k. \r\n#b#L0#Demon Slayer!#l\r\n#b#L1#Mercedes!#l\r\n#b#L2#Dual Blade!#l\r\n#b#L3#Cannoneer!#l");
    } else if (status == 0 && cm.getPlayerStat("LVL") == 8 && cm.getJob() == 0) { // well, just for players who level 8 (mage only)
        cm.sendSimple("Hello #r#h ##k! I am your #rExplorer Job Advance NPC#k. \r\n#b#L1#Mercedes!#l");
        
    } else if (status == 0 && cm.getPlayerStat("LVL") >= 30 && cm.getJob() == 3100) { // 30
        cm.sendSimple("Hello #r#h ##k! I am your #rExplorer Job Advance NPC#k. \r\n#b#L10#Demon Slayer!#l");
    } else if (status == 0 && cm.getPlayerStat("LVL") >= 30 && cm.getJob() == 2300) { // 30
        cm.sendSimple("Hello #r#h ##k! I am your #rExplorer Job Advance NPC#k. \r\n#b#L20#Mercedes!#l");
    } else if (status == 0 && cm.getPlayerStat("LVL") >= 30 && cm.getJob() == 430) { // 30
        cm.sendSimple("Hello #r#h ##k! I am your #rExplorer Job Advance NPC#k. \r\n#b#L30#Dual Blade1#l");
    } else if (status == 0 && cm.getPlayerStat("LVL") >= 30 && cm.getJob() == 501) { // 30
        cm.sendSimple("Hello #r#h ##k! I am your #rExplorer Job Advance NPC#k. \r\n#b#L40#Cannoneer!#l");
        
    } else if (status == 0 && cm.getPlayerStat("LVL") >= 70 && cm.getJob() == 3110) { // 70
        cm.sendSimple("Hello #r#h ##k! I am your #rExplorer Job Advance NPC#k. \r\n#b#L1000#Demon Slayer!#l");
        
    } else if (status == 0 && cm.getPlayerStat("LVL") >= 70 && cm.getJob() == 2310) { // 70
        cm.sendSimple("Hello #r#h ##k! I am your #rExplorer Job Advance NPC#k. \r\n#b#L2000#Mercedes!#l");
        
    } else if (status == 0 && cm.getPlayerStat("LVL") >= 50 && cm.getJob() == 431) { // 50    
        cm.sendSimple("Hello #r#h ##k! I am your #rExplorer Job Advance NPC#k. \r\n#b#L3000#Dual Blade1#l");
        
    } else if (status == 0 && cm.getPlayerStat("LVL") >= 70 && cm.getJob() == 530) { //70
        cm.sendSimple("Hello #r#h ##k! I am your #rExplorer Job Advance NPC#k. \r\n#b#L4000#Cannoneer!#l");
        
      
    } else if (status == 0 && cm.getPlayerStat("LVL") >= 120 && cm.getJob() == 3111) { // 120
        cm.sendSimple("Hello #r#h ##k! I am your #rExplorer Job Advance NPC#k. \r\n#b#L10000#Demon Slayer!#l");  
        
    } else if (status == 0 && cm.getPlayerStat("LVL") >= 120 && cm.getJob() == 2311) { // 120
        cm.sendSimple("Hello #r#h ##k! I am your #rExplorer Job Advance NPC#k. \r\n#b#L20000#Mercedes!#l");
    
    } else if (status == 0 && cm.getPlayerStat("LVL") >= 70 && cm.getJob() == 432) { // 70   
        cm.sendSimple("Hello #r#h ##k! I am your #rExplorer Job Advance NPC#k. \r\n#b#L30000#Dual Blade1#l");

    } else if (status == 0 && cm.getPlayerStat("LVL") >= 120 && cm.getJob() == 433) { // 120    
        cm.sendSimple("Hello #r#h ##k! I am your #rExplorer Job Advance NPC#k. \r\n#b#L300000#Dual Blade1#l");
    
    } else if (status == 0 && cm.getPlayerStat("LVL") >= 120 && cm.getJob() == 531) { // 120
        cm.sendSimple("Hello #r#h ##k! I am your #rLegend Job Advance NPC#k. \r\n#b#L40000#Cannoneer!#l");
    
    } else if (status == 1) {
        if (selection == 0 && cm.getPlayerStat("LVL") >= 10 && cm.getJob() == 0) {
            cm.resetStats(35, 4, 4, 4);
	    cm.expandInventory(1, 4);
	    cm.expandInventory(4, 4);
            cm.gainItem(1322123, 1);
            cm.gainItem(1142345, 1);
            cm.gainItem(1099001, 1);
            cm.changeJob(3100);   
        } else if (selection == 1 && cm.getPlayerStat("LVL") >= 10 && cm.getJob() == 0) {
            cm.resetStats(4, 25, 4, 4);
	    cm.expandInventory(1, 4);
	    cm.expandInventory(4, 4);
            cm.gainItem(1522000, 1);
            cm.gainItem(1142340, 1);
            cm.gainItem(1352005, 1);
	    cm.changeJob(2300); 
        } else if (selection == 2 && cm.getPlayerStat("LVL") >= 10 && cm.getJob() == 0) {
            cm.resetStats(4, 25, 4, 4);
	    cm.expandInventory(1, 4);
	    cm.expandInventory(4, 4);
            cm.gainItem(1332032,1);
            cm.gainItem(1142012,1);
            cm.gainItem(1342000,1);
	    cm.changeJob(430);
        } else if (selection == 3 && cm.getPlayerStat("LVL") >= 10 && cm.getJob() == 0) {
            cm.resetStats(4, 25, 4, 4);
	    cm.expandInventory(1, 4);
	    cm.expandInventory(4, 4);
            cm.gainItem(1532045, 1);
            cm.gainItem(1142013, 1);
            cm.gainItem(2330000, 600);
	    cm.changeJob(501);
        } else if (selection == 10 && cm.getPlayerStat("LVL") >= 30 && cm.getJob() == 3100) { // start of Demon Slayer
            cm.sendSimple("\r\n#b#L110#Demon Slayer#l");  
        } else if (selection == 1000 && cm.getPlayerStat("LVL") >= 70 && cm.getJob() == 3110) {
            cm.sendSimple("\r\n#b#L111#Demon Slayer#l");
        } else if (selection == 10000 && cm.getPlayerStat("LVL") >= 120 && cm.getJob() == 3111) {
            cm.sendSimple("\r\n#b#L112#Demon Slayer#l");
        } else if (selection == 20 && cm.getPlayerStat("LVL") >= 30 && cm.getJob() == 2300) { // start of Mercedes
            cm.sendSimple("\r\n#b#L210#Mercedes#l"); 
        } else if (selection == 2000 && cm.getPlayerStat("LVL") >= 70 && cm.getJob() == 2310) { 
            cm.sendSimple("\r\n#b#L211#Mercedes#l"); 
        } else if (selection == 20000 && cm.getPlayerStat("LVL") >= 120 && cm.getJob() == 2311) {
            cm.sendSimple("\r\n#b#L212#Mercedes#l");
        } else if (selection == 30 && cm.getPlayerStat("LVL") >= 30 && cm.getJob() == 430) { // start of Dual Blade
            cm.sendSimple("\r\n#b#L310#Dual Blade#l");   
        } else if (selection == 3000 && cm.getPlayerStat("LVL") >= 50 && cm.getJob() == 431) {
            cm.sendSimple("\r\n#b#L311#Dual Blade#l"); 
        } else if (selection == 30000 && cm.getPlayerStat("LVL") >= 70 && cm.getJob() == 432) {
            cm.sendSimple("\r\n#b#L312#Dual Blade#l"); 
        } else if (selection == 300000 && cm.getPlayerStat("LVL") >= 120 && cm.getJob() == 433) {
            cm.sendSimple("\r\n#b#L313#Dual Blade#l"); 
        } else if (selection == 40 && cm.getPlayerStat("LVL") >= 30 && cm.getJob() == 501) { // start of Cannoneer
            cm.sendSimple("\r\n#b#L410#Cannoneer#l"); 
        } else if (selection == 4000 && cm.getPlayerStat("LVL") >= 70 && cm.getJob() == 530) { 
            cm.sendSimple("\r\n#b#L411#Cannoneer#l"); 
        } else if (selection == 40000 && cm.getPlayerStat("LVL") >= 120 && cm.getJob() == 531) { 
            cm.sendSimple("\r\n#b#L412#Cannoneer!#l"); 
        } else {
           cm.sendOk("Please check with your requirement."); 
       }
   }// here for 2nd job start above
   if (status == 2) { 
       if (selection == 110 && cm.getPlayerStat("LVL") >= 30 && cm.getJob() == 3100) { 
           cm.changeJob(3110);
       } else if (selection == 210 && cm.getPlayerStat("LVL") >= 30 && cm.getJob() == 2300) {
           cm.changeJob(2310);
       } else if (selection == 310 && cm.getPlayerStat("LVL") >= 30 && cm.getJob() == 430) {
           cm.changeJob(431);
       } else if (selection == 410 && cm.getPlayerStat("LVL") >= 30 && cm.getJob() == 501) {
           cm.changeJob(530);
       } else {
  //       cm.sendOk("Please check with your requirement to ensure you are level 30");
           cm.dispose();
       } // 3rd job advance
       if (selection == 111 && cm.getPlayerStat("LVL") >= 70 && cm.getJob() == 3110) {
           cm.changeJob(3111);
       } else if (selection == 211 && cm.getPlayerStat("LVL") >= 70 && cm.getJob() == 2310) {
           cm.changeJob(2311);
       } else if (selection == 311 && cm.getPlayerStat("LVL") >= 50 && cm.getJob() == 431) {
           cm.changeJob(432);
       } else if (selection == 411 && cm.getPlayerStat("LVL") >= 70 && cm.getJob() == 530) {
           cm.changeJob(531);
       } else {
    //     cm.sendOk("Please check with your requirement to ensure you are level 70.");
           cm.dispose();
       } // 4th job advance [todo learn skill]
       if (selection == 112 && cm.getPlayerStat("LVL") >= 120 && cm.getJob() == 3111) {
           cm.changeJob(3112);
       } else if (selection == 212 && cm.getPlayerStat("LVL") >= 120 && cm.getJob() == 2311) {
           cm.changeJob(2312);
       } else if (selection == 312 && cm.getPlayerStat("LVL") >= 70 && cm.getJob() == 432) {
           cm.changeJob(433);
       } else if (selection == 412 && cm.getPlayerStat("LVL") >= 120 && cm.getJob() == 531) {
           cm.changeJob(532);
       } else {
 //        cm.sendOk("Please check with your requirement to ensure you are level 120.");
           cm.dispose();
       } // 5th job advance
       if (selection == 313 && cm.getPlayerStat("LVL") >= 120 && cm.getJob() == 433) {
           cm.changeJob(434);
       } else {
    //     cm.sendOk("Please check with your requirement to ensure you are level 120.");
           cm.dispose();
        }
    }
}

