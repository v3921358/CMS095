var name;
var status = 0;
var thing = 0;
var slot;
var p = null;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (mode == 2 && status == 0) {
            cm.dispose();
            return;
        }
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 0) {
      if (cm.getPlayerStat("GM")) {
cm.sendGetText("#eHey #h #! I can check a player's inventory for you. \r\n\r\n#rPlease type in a player's name");
				getText = text;
                           }else {
                cm.sendOk("#eYou are not authorized to perform this action !");
                cm.dispose();
            } //cm.getPlayer().gmLevel() >= 4 <-- old v83 command
        } else if (status == 1) {
            name = cm.getText(); 
            p = cm.getCharacterByName(name); 
            if (p != null) {
                cm.sendSimple("#eChoose an inventory#b\r\n#L0#Equip#l\r\n#L1#Use#l\r\n#L2#Set-up#l\r\n#L3#ETC#l\r\n#L4#Cash#l");
            } else {
                
                cm.sendOk("#e#rThe player you are trying to choose either is offline or not in your channel.");
            }
        } else if (status == 2) {
            string = "#eClick on an item to remove #rall#k of it.\r\n#n";
			
            thing = selection;
            if (selection == 0) {                
                cm.sendSimple(string+cm.EquipList(p.getClient()));
            } else if (selection == 1) {
                cm.sendSimple(string+cm.UseList(p.getClient()));
            } else if (selection == 2) {
                cm.sendSimple(string+cm.SetupList(p.getClient()));
            } else if (selection == 3) {
                cm.sendSimple(string+cm.ETCList(p.getClient()));
            } else if (selection == 4) {
                cm.sendSimple(string+cm.CashList(p.getClient()));
            }
        } else if (status == 3) {
            slot = selection;
            send = "#eThe user has#r ";
            send2 = "#k of the item #i";
            if (thing == 0) {
                send += p.getItemQuantity(p.getEquipId(selection), true);
                send2 += p.getEquipId(selection);
            } else if (thing  == 1) {
                send += p.getItemQuantity(p.getUseId(selection), true);
                send2 += p.getUseId(selection);
            } else if (thing == 2) {
                send += p.getItemQuantity(p.getSetupId(selection), true);
                send2 += p.getSetupId(selection);
            } else if (thing == 3) {
                send += p.getItemQuantity(p.getETCId(selection), true);
                send2 += p.getETCId(selection);
            } else if (thing == 4) {
                send += p.getItemQuantity(p.getCashId(selection), true);
                send2 += p.getCashId(selection);
            }
            var send3 = send + send2 + "# are you sure you want to delete #rall#k of that item?";
            cm.sendYesNo(send3);
        } else if (status == 4) {
            if (thing == 0) { 
                p.deleteAll(p.getEquipId(slot));
            } else if (thing == 1) {
                p.deleteAll(p.getUseId(slot));
            } else if (thing == 2) {
                p.deleteAll(p.getSetupId(slot));
            } else if (thing == 3) {
                p.deleteAll(p.getETCId(slot));
            } else if (thing == 4) {
                p.deleteAll(p.getCashId(slot));
            }
            cm.sendOk("#eSuccessfully deleted " +  name + "'s item");
            cm.dispose();
        }
    }
}  
