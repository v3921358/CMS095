/*  This is mada by Kent    
 *  This source is made by Funms Team
 *  功能：希拉 - 120 级简单模式
 *  @Author Kent 
 */
//自定义复活次数
var reviveCount = 10;

var mobhp = 1200000000;

function init() {
    em.setProperty("state", "0");
    em.setProperty("leader", "true");
}

function setup(eim, leaderid) {
    em.setProperty("state", "1");
    em.setProperty("leader", "true");
    var eim = em.newInstance("BossHillah");
    var map = eim.setInstanceMap(262030300); //设置活动脚本的地图
    map.resetFully(false); //重置地图
    map.setSpawns(false);
    var mob = em.getMonster(8870000); //希拉 - 120级
    mob.getStats().setChange(true);
    mob.changeLevel(120);
    mob.getChangedStats().setOHp(mobhp);
    mob.setHp(mobhp);
    //var modified = em.newMonsterStats();
    //modified.setOMp(mob.getMobMaxMp());
    //modified.setOHp(mob.getMobMaxHp() * 6.0);
    eim.registerMonster(mob);
    map.spawnMonsterOnGroundBelow(mob, new java.awt.Point(134, 196));
    eim.startEventTimer(2700000); //45分钟
    for (var j = 1; j <= 20; j++) { //2分钟提醒一次血量、提醒20次
        eim.schedule("checkHP", 120000 * j);
    }
    return eim;
}

function playerEntry(eim, player) {
    player.restReviveCount();
    var map = eim.getMapInstance(0);
    player.setReviveCount(reviveCount);
    player.changeMap(map, map.getPortal(0));
}

function changedMap(eim, player, mapid) {
    if (mapid < 262030300 || mapid > 262030310) {
        eim.unregisterPlayer(player);
        if (eim.disposeIfPlayerBelow(0, 0)) {
            em.setProperty("state", "0");
            em.setProperty("leader", "true");
        }
    }
}

function playerDisconnected(eim, player) {
    return 0;
}

function scheduledTimeout(eim) {
    eim.disposeIfPlayerBelow(100, 262010000);
    em.setProperty("state", "0");
    em.setProperty("leader", "true");
}

function playerExit(eim, player) {
    eim.unregisterPlayer(player);
    if (eim.disposeIfPlayerBelow(0, 0)) {
        em.setProperty("state", "0");
        em.setProperty("leader", "true");
    }
}

function monsterValue(eim, mobId) {
    return 1;
}


function allMonstersDead(eim) {
    eim.setPartyBossLog("希拉");
    var state = em.getProperty("state");
    if (state.equals("1")) {
        em.setProperty("state", "2");
    } else if (state.equals("2")) {
        em.setProperty("state", "3");
    }
}

function playerRevive(eim, player) {
    if (player.getReviveCount() > 0) {
        var map = player.getMap();
        player.heal();
        player.changeMap(map, map.getPortal(0));
        return true;
    }
    player.addHP(50);
    var map = eim.getMapInstance(262010000);
    player.changeMap(map, map.getPortal(0));
    return true;
}

function clearPQ(eim) {
    scheduledTimeout(eim);
}

function leftParty(eim, player) {}

function disbandParty(eim) {}

function playerDead(eim, player) {}

function cancelSchedule() {}

function pickUpItem(eim, player, itemID) {}

function checkHP(eim) {
    var map = eim.getMapInstance(0);
    var mobs = map.getAllMonstersThreadsafe();
    var mobhpMSG = "BOSS剩余血量：";
    for (var i = 0; i < mobs.size(); i++) {
        // hpDone += (mobhp - mobs.get(i).getHp());
        mobhpMSG += "希拉" + Math.floor((mobs.get(i).getHp() / mobhp) * 100) + "%、";
    }

    map.startMapEffect(mobhpMSG, 5120008);
}