/*
 * 大部分通用 boss/副本 模块
 * 简单的副本模块.当然还有更高级的 触发各种事件的
 * 大部分通用了...
 * From: 芬芬时尚潮流 
 */

//怪物id
var mobid = 8860000;
//怪物血量
var mobhp = 1500000000;
//怪物等级
var moblevel = 200;
//开始副本的所在地图
var startmap = 272030400;
//时间到之后退出的地图
var overmap = 105200000;
//死亡之后退到的地图
var deathmap = 105200000;
//副本时间(单位:分钟)
var time = 30;
//坐标x
var posx = 15;
//坐标y
var posy = -181;
//事件名
var PQname = "BossArkarium";


function init() {
    em.setProperty("leader", "true");
    em.setProperty("state", "0");//状态检测
}

function setup(eim, leaderid) {
    em.setProperty("leader", "true");
    em.setProperty("state", "1");
    var eim = em.newInstance(PQname);
    var map = eim.setInstanceMap(startmap);
    map.resetFully();
    map.killAllMonsters(false);
    var mob = em.getMonster(mobid);//召唤怪物
	mob.changeLevel(moblevel);
    //mob.getChangedStats().setOHp(mobhp);
    mob.setHp(mobhp);
    var modified = em.newMonsterStats();
    modified.setOHp(mobhp);
    mob.setOverrideStats(modified);
    eim.registerMonster(mob);
	map.spawnMonsterOnGroundBelow(mob, new java.awt.Point(posx,posy));//设置坐标x y
    eim.startEventTimer(time * 60 * 1000); //30分钟
    return eim;
}

//玩家进入
function playerEntry(eim, player) {
    var map = eim.getMapInstance(0);
    player.changeMap(map, map.getPortal(0));
}

//角色复活触发事件
function playerRevive(eim, player) {
    player.addHP(50);
    var map = eim.getMapInstance(deathmap);
    player.changeMap(map, map.getPortal(0));
    return true;
}


function scheduledTimeout(eim) {
    eim.disposeIfPlayerBelow(100, overmap);
    em.setProperty("state", "0");
    em.setProperty("leader", "true");
}

function changedMap(eim, player, mapid) {
    if (mapid != startmap && mapid != 272030420 && mapid != 272030410) {
        eim.unregisterPlayer(player);
        if (eim.disposeIfPlayerBelow(0, 0)) {
            em.setProperty("state", "0");
            em.setProperty("leader", "true");
        }
    }
}

//角色断开连接触发
function playerDisconnected(eim, player) {
    return 0;
}

//杀死怪物触发事件
function monsterValue(eim, mobId) {
        // switch (mobId) {
    //     case 8860000:
    //         for (i = 0; i < eim.getPlayers().size(); i++) {
	// 			eim.getPlayers().get(i).openNpc(9900002, "BOSS奖励结算");
    //         }
    //         break;
    // }
    return 1;
}

//角色退出时触发
function playerExit(eim, player) {
    eim.unregisterPlayer(player);

    if (eim.disposeIfPlayerBelow(0, 0)) {
        em.setProperty("state", "0");
        em.setProperty("leader", "true");
    }
}

function end(eim) {
    if (eim.disposeIfPlayerBelow(100, overmap)) {
        em.setProperty("state", "0");
        em.setProperty("leader", "true");
    }
}

function clearPQ(eim) {
    end(eim);
}
//所有怪物死亡触发
function allMonstersDead(eim) {
    eim.setPartyBossLog("阿卡伊勒");
    var state = em.getProperty("state");
    if (state.equals("1")) {
        em.setProperty("state", "2");
    } else if (state.equals("2")) {
        em.setProperty("state", "3");
    }
}

//离开队伍触发
function leftParty(eim, player) {}
//解散队伍触发
function disbandParty(eim) {}
//在活动角色死亡触发事件
function playerDead(eim, player) {}
//取消自定义事件
function cancelSchedule() {}
//怪物掉落道具
function monsterDrop(eim, player, mob) {}
//捡东西触发
function pickUpItem(eim, player, itemID) {}