
function start() {
 status = -1;
 action(1, 0, 0);
}
function action(mode, type, selection) {
      name = cm.getText()
 if (mode == -1) {
  cm.dispose();
 } else {
  if (mode == 0) {
   cm.sendOk("Please try again.");
   cm.dispose();
   return;
  }
  if (mode == 1) 
   status++;
  else
   status--;
  if (status == 0 && cm.getPlayer().isGM()) {
   cm.sendNext("Welcome to #rHidden#kMS's Smega Prefix NPC!\r\nFor the price of #b1,000,000#k, you can change your Smega Prefix.\r\n\r\nWhat is a #rSmega Prefix#k you ask? - It's a small amount of text before a smega, taking the place of your medal.\r\n#bYour current Smega Prefix is:#k#r " + cm.getPlayer().getPrefix() + "#k\r\n\r\n#eWould you like to adjust or create your smega Prefix?");
  } else if (status == 1) {
      if (cm.getPlayer().getMeso() >= 1000000) {
   cm.sendGetText("#ePlease enter your new Smega Prefix below:");
      } else {
        cm.sendOk("You don't even have 1,000,000 mesos, don't try to fool me!");
        cm.dispose();
      }
  } else if (!cm.PrefixIsLegal(name) || name.length() > 10) {
cm.sendOk("Your Smega Prefix must be 10 characters or less and not contain certain special characters.");
cm.dispose(); } 
else if (status == 2){
   //if (cm.PrefixIsLegal(name) && name.length() < 16) {
   cm.sendYesNo("Are you sure you want #b" + name + "#k as your Smega Prefix,#h #? ");
//} else {cm.sendOk("Your Smega Prefix must be 15 characters or less and not contain certain special characters.");
//cm.dispose(); 
//}
 } else if (status == 3) {
    cm.sendOk("Okay! You have lost 1,000,000 Mesos.\r\n\r\nYour new Smega Prefix will be #b" + name + "."); 
    cm.getPlayer().gainMeso(-1000000, true);
    cm.getPlayer().setPrefix(name,true);
cm.dispose(); 
} 
  }
 }