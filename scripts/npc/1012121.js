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
cm.sendNext("#eHey #h #! I can eat your items. #eAny item you select will be #b*completely wiped.*#k#n\r\n\r\n#rPlease only use this to get rid of NX/unwanted items. If you recieve an error when trying to view your inventory, it may require a @clearinv, which DELETES ALL ITEMS IN THAT INVENTORY TAB.");
        } else if (status == 1) {
            p = cm.getPlayer();
            if (p != null) {
                cm.sendSimple("#eChoose an inventory#b\r\n#L0#Equip#l\r\n#L1#Use#l\r\n#L2#Set-up#l\r\n#L3#ETC#l\r\n#L4#Cash#l");
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
            send = "#eYou have#r ";
            send2 = "#k of the item #i";
            if (thing == 0) {
                send += p.getItemQuantity(cm.getEquipId(selection), true);
                send2 += cm.getEquipId(selection);
            } else if (thing  == 1) {
                send += p.getItemQuantity(cm.getUseId(selection), true);
                send2 += cm.getUseId(selection);
            } else if (thing == 2) {
                send += p.getItemQuantity(cm.getSetupId(selection), true);
                send2 += cm.getSetupId(selection);
            } else if (thing == 3) {
                send += p.getItemQuantity(cm.getETCId(selection), true);
                send2 += cm.getETCId(selection);
            } else if (thing == 4) {
                send += p.getItemQuantity(cm.getCashId(selection), true);
                send2 += cm.getCashId(selection);
            }
            var send3 = send + send2 + "# are you sure you want to delete #rall#k of that item?";
            cm.sendYesNo(send3);
        } else if (status == 4) {
            if (thing == 0) { 
                cm.deleteAll(cm.getEquipId(slot));
            } else if (thing == 1) {
                cm.deleteAll(cm.getUseId(slot));
            } else if (thing == 2) {
                cm.deleteAll(cm.getSetupId(slot));
            } else if (thing == 3) {
                cm.deleteAll(cm.getETCId(slot));
            } else if (thing == 4) {
                cm.deleteAll(cm.getCashId(slot));
            }
            cm.sendOk("#eSuccessfully deleted the item(s).");
            cm.dispose();
        }
    }
}  
