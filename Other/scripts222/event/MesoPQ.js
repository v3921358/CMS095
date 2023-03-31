
var minPlayers = 1;

function init() {
    em.setProperty("state", "0");
}

function monsterValue(eim, mobId) {
    return 1;
}

function setup() {
    em.setProperty("state", "1");

    var eim = em.newInstance("MesoPQ");

    var map = eim.setInstanceMap(674020000);
    eim.startEventTimer(300000); //20 分

    return eim;
}

function scheduledTimeout(eim) {
    eim.disposeIfPlayerBelow(100, eim.getProperty("cleared") == null ? 910000000 : 910000000);

    em.setProperty("state", "0");
}

function changedMap(eim, player, mapid) {
    switch (mapid) {
        case 674020000:
            return;
    }
    eim.unregisterPlayer(player);

    if (eim.disposeIfPlayerBelow(0, 0)) {
        em.setProperty("state", "0");
    }
}

function playerEntry(eim, player) {
    var map = em.getMapFactory().getMap(674020000);
    player.changeMap(map, map.getPortal(0));
}

function playerRevive(eim, player) {}

function playerDisconnected(eim, player) {
    playerExit(eim, player);
}

function leftParty(eim, player) {
    // If only 2 players are left, uncompletable
    if (eim.disposeIfPlayerBelow(minPlayers, eim.getProperty("cleared") == null ? 910000000 : 910000000)) {
        em.setProperty("state", "0");
    } else {
        playerExit(eim, player);
    }
}

function disbandParty(eim) {
    // Boot whole party and end
    eim.disposeIfPlayerBelow(100, eim.getProperty("cleared") == null ? 910000000 : 910000000);

    em.setProperty("state", "0");
}

function playerExit(eim, player) {
    var map = em.getMapFactory().getMap(eim.getProperty("cleared") == null ? 910000000 : 910000000);

    eim.unregisterPlayer(player);
    player.changeMap(map, map.getPortal(0));
}

// For offline players
function removePlayer(eim, player) {
    eim.unregisterPlayer(player);
}

function clearPQ(eim) {
    eim.disposeIfPlayerBelow(100, eim.getProperty("cleared") == null ? 910000000 : 910000000);

    em.setProperty("state", "0");
}

function finish(eim) {
    eim.disposeIfPlayerBelow(100, eim.getProperty("cleared") == null ? 910000000 : 910000000);

    em.setProperty("state", "0");
}

function timeOut(eim) {
    eim.disposeIfPlayerBelow(100, eim.getProperty("cleared") == null ? 910000000 : 910000000);

    em.setProperty("state", "0");
}

function cancelSchedule() {}

function playerDead(eim, player) {
    eim.disposeIfPlayerBelow(100, eim.getProperty("cleared") == null ? 910000000 : 910000000);
    em.setProperty("state", "0");
}

function allMonstersDead(eim) {}