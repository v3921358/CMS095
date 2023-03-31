function init() {
    em.setProperty("leader", "true");
    em.setProperty("state", "0");
}

function setup(eim, leaderid) {
    em.setProperty("leader", "true");
    var eim = em.newInstance("BossPierre_CHAOS");
    var map = eim.setInstanceMap(105200610);
    map.resetFully();
    map.killAllMonsters(false);
    var mob1 = em.getMonster(8900000);
    eim.registerMonster(mob1);
    map.spawnMonsterOnGroundBelow(mob1, new java.awt.Point(497, 551));
    var mob2 = em.getMonster(8900001);
    eim.registerMonster(mob2);
    map.spawnMonsterOnGroundBelow(mob2, new java.awt.Point(497, 551));
    var mob3 = em.getMonster(8900002);
    eim.registerMonster(mob3);
    map.spawnMonsterOnGroundBelow(mob3, new java.awt.Point(497, 551));

    em.setProperty("state", "1");

    eim.startEventTimer(3600000);
    //map.mapMessage(-6, /*em.getCharacterName(leaderid) + */" joined the expedition. Entering Hilla's Tower.");
    return eim;
}

function playerEntry(eim, player) {
    var NowMapID = eim.getProperty("NowMapID");
    var toMapid = 105200610;
    if (NowMapID != null) {
        toMapid = java.lang.Integer.parseInt(NowMapID);
    }
    var map = eim.getMapFactory().getMap(toMapid);
    player.changeMap(map, map.getPortal(0));
    //加入远征队玩家信息
    eim.setProperty("isSquadPlayerID_" + player.getId(), "1");
    //var map = eim.getMapFactory().getMap(105200610);
    //player.changeMap(map, map.getPortal(0));
}

function playerRevive(eim, player) {
    player.addHP(50);
    var map = eim.getMapFactory().getMap(105200000);
    player.changeMap(map, map.getPortal(0));
    return true;
}

function scheduledTimeout(eim) {
    eim.disposeIfPlayerBelow(100, 105200000);
    em.setProperty("state", "0");
    em.setProperty("leader", "true");
}

function changedMap(eim, player, mapid) {
    switch (mapid) {
        case 105200610:
            //保存当前执行地图ID
            eim.setProperty("NowMapID", "" + mapid);
            return;
    }
    if (mapid != 105200610) {
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
    if (eim.disposeIfPlayerBelow(100, 105200000)) {
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
    eim.setProperty("isSquadPlayerID_" + player.getId(), "0");
}
function cancelSchedule() {}