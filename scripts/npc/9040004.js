var status = -1;
var mod = 0;

function start() {
    action(1, 0, 0)
}

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else if (mode == 0) {
        status--;
    } else {
        cm.dispose();
        return;
    }
    if (status == 0) {
        cm.sendSimple("#b你好 #k#h  ##e  #b我是排名系統.#k\r\n#L0##r家族排名\n\#l\r\n#L1##d玩家排名#l\r\n#L2##d财富排名#l\r\n#L3##b职业排名#l\r\n#L4##b挑战时间排名#l\r\n#L5##b武陵道场排名#l");
    } else if (status == 1) {
        if (selection == 0) {
            cm.displayGuildRanks();
            cm.dispose();
        } else if (selection == 1) {
            cm.showlvl();
            cm.dispose();
        } else if (selection == 2) {
            cm.showmeso();
            cm.dispose();
        } else if (selection == 3) {
            cm.sendSimple("#L1##d战士#k排名\r\n#L2##d法师#k排名\r\n#L3##b弓箭手#k排名\r\n#L4##b飞侠#k排名\r\n#L5##r海盜#k排名\r\n#L6##r战神#k排名\r\n#L7##r皇家骑士团#k排名\r\n#L8##r反抗軍#k排名\r\n#L9##r龙神#k排名\r\n");
            mod = 3;
        } else if (selection == 4) {
            cm.sendSimple("#L11##d扎昆#k排名\r\n#L22##d暗黑龙王#k排名\r\n#L23##d班雷昂#k排名\r\n");
        } else if (selection == 5) {
            openNpc(9040004, "武陵道场排名");
        }



    } else if (status == 2) {
        if (mod == 3) {
            cm.sendNext(cm.ShowJobRank(selection));
            cm.dispose();
        } else {
            if (selection == 11) {

                openNpc(9040004, "扎昆排名");
            }
            if (selection == 22) {
                openNpc(9040004, "暗黑龙王排名");
            }
            if (selection == 23) {
                openNpc(9040004, "班雷昂排名");
            }
        }
    } else {
        cm.dispose();
    }
}
function openNpc(npcid) {
    openNpc(npcid, null);
}

function openNpc(npcid, script) {
    var mapid = cm.getMapId();
    cm.dispose();
    if (cm.getPlayerStat("LVL") < 10) {
        cm.sendOk("你的等級不能小于10级.");
    } else if (
            cm.hasSquadByMap() ||
            cm.hasEventInstance() ||
            cm.hasEMByMap() ||
            mapid >= 990000000 ||
            (mapid >= 680000210 && mapid <= 680000502) ||
            (mapid / 1000 === 980000 && mapid !== 980000000) ||
            mapid / 100 === 1030008 ||
            mapid / 100 === 922010 ||
            mapid / 10 === 13003000
            ) {
        cm.sendOk("你不能在这里使用这个功能.");
    } else {
        if (script == null) {
            cm.openNpc(npcid);
        } else {
            cm.openNpc(npcid, script);
        }
    }
}