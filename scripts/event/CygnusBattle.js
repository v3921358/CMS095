function init() {
    em.setProperty("leader", "true");
    em.setProperty("state", "0");
}

function setup(eim, leaderid) {
    em.setProperty("leader", "true");
    var eim = em.newInstance("CygnusBattle");
    var map = eim.setInstanceMap(271040100);
    map.resetFully();

    var mob1 = em.getMonster(8850000);
    eim.registerMonster(mob1);
    map.spawnMonsterOnGroundBelow(mob1, new java.awt.Point(-363, 100));

    em.setProperty("state", "1");

    eim.startEventTimer(7200000); // 1 hr
    return eim;
}

function playerEntry(eim, player) {
    var NowMapID = eim.getProperty("NowMapID");
    var toMapid = 271040100;
    if (NowMapID != null) {
        toMapid = java.lang.Integer.parseInt(NowMapID);
    }
    var map = eim.getMapFactory().getMap(toMapid);
    player.changeMap(map, map.getPortal(0));
    eim.setProperty("isSquadPlayerID_" + player.getId(), "1");
    //var map = eim.getMapFactory().getMap(271040100);
    //player.changeMap(map, map.getPortal(0));
}

function playerRevive(eim, player) {
    return false;
}

function scheduledTimeout(eim) {
    eim.disposeIfPlayerBelow(100, 271040000);
    em.setProperty("state", "0");
    em.setProperty("leader", "true");
}

function changedMap(eim, player, mapid) {
    switch (mapid) {
        case 271040100:
            eim.setProperty("NowMapID", "" + mapid);
            return;
    }
    if (mapid != 271040100) {
        eim.unregisterPlayer(player);

        if (eim.disposeIfPlayerBelow(0, 0)) {
            em.setProperty("state", "0");
            em.setProperty("leader", "true");
        }
    }
}

function playerDisconnected(eim, player) {
    playerExit(eim, player);
    return 0;
}

function monsterValue(eim, mobId) {
    var map = eim.setInstanceMap(271040100);
    if (mobId == 8850000) {
        var mob5 = em.getMonster(8850001);
        eim.registerMonster(mob5);
        map.spawnMonsterOnGroundBelow(mob5, new java.awt.Point(-363, 100));
    } else if (mobId == 8850001) {
        var mob5 = em.getMonster(8850002);
        eim.registerMonster(mob5);
        map.spawnMonsterOnGroundBelow(mob5, new java.awt.Point(-363, 100));
    } else if (mobId == 8850002) {
        var mob5 = em.getMonster(8850003);
        eim.registerMonster(mob5);
        map.spawnMonsterOnGroundBelow(mob5, new java.awt.Point(-363, 100));
    } else if (mobId == 8850003) {
        var mob5 = em.getMonster(8850004);
        eim.registerMonster(mob5);
        map.spawnMonsterOnGroundBelow(mob5, new java.awt.Point(-363, 100));
    } else if (mobId == 8850004) {
        var mob5 = em.getMonster(8850011);
        eim.registerMonster(mob5);
        map.spawnMonsterOnGroundBelow(mob5, new java.awt.Point(-363, 100));
    }
    return 1;
}

function playerExit(eim, player) {
    eim.unregisterPlayer(player);

    if (eim.disposeIfPlayerBelow(0, 0)) {
        em.setProperty("state", "0");
        em.setProperty("leader", "true");
    }
}

function end(eim) {
    if (eim.disposeIfPlayerBelow(100, 271040000)) {
        em.setProperty("state", "0");
        em.setProperty("leader", "true");
    }
}

function clearPQ(eim) {
    end(eim);
}

function allMonstersDead(eim) {
}

function leftParty(eim, player) {}
function disbandParty(eim) {}
function playerDead(eim, player) {
 eim.setProperty("isSquadPlayerID_" + player.getId(), "1");
    
}
function cancelSchedule() {}