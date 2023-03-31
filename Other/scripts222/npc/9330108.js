/* Kedrick
    Fishking King NPC
*/

var status = -1;
var sel;

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        if (status == 0) {
            cm.dispose();
            return;
        }
        status--;
    }

    if (status == 0) {
        if (cm.getPlayer().getMapId() == 910000000) {
            cm.sendSimple("我能为您做什么吗？？\n\r #b#L0#我想去钓鱼#l \r\n\r\n#L4#我想去转蛋屋#l\r\n\r\n#L5#我想去快乐转蛋山#l");
        } else {
            cm.sendSimple("我能为您做什么吗？？\n\r #b#L0#我想去钓鱼#l \n\r #L2#回去原本的地图#l");
        }
    } else if (status == 1) {
        sel = selection;
        if (sel == 0) {
            cm.sendSimple("哪去哪个钓鱼场？？?\r\n#b#L0##m741000200##l\r\n#b#L1##m749050500##l\r\n#k#b#L2##m749050501##l\r\n#b#L3##m749050502##l\r\n");
        } else if (sel == 2) {
            var returnMap = cm.getSavedLocation("FISHING");
            if (returnMap < 0 || cm.getMap(returnMap) == null) {
                returnMap = 910000000; // to fix people who entered the fm trough an unconventional way
            }
            cm.clearSavedLocation("FISHING");
            cm.warp(returnMap, 0);
            cm.dispose();
        } else if (sel == 4) {
			cm.saveLocation("TURNEGG");
			cm.warp(749050400);
            cm.dispose();
		} else if (sel == 5) {
			cm.saveLocation("TURNEGG");
			cm.warp(910040003);
            cm.dispose();
        } 
    } else if (status == 2) {
		
        if (selection == 0) {
			cm.saveLocation("FISHING");
            cm.warp(741000200);
            cm.dispose();
        } else if (selection == 1) {
            cm.warp(749050500);
            cm.dispose();
        } else if (selection == 2) {
            cm.warp(749050501);
            cm.dispose();
        } else if (selection == 3) {
            cm.warp(749050502);
            cm.dispose();
        } 
    }
}
