var minPlayers = 6;
var stg2_combo0 = Array("5", "4", "3", "3", "2");
var stg2_combo1 = Array("0", "0", "1", "0", "1"); //unique combos only
var stg2_combo2 = Array("0", "1", "1", "2", "2");

function init() {
    em.setProperty("state", "0");
    em.setProperty("leader", "true");
}

function setup(eim, leaderid) {
    em.setProperty("state", "1");
    em.setProperty("leader", "true");
    var eim = em.newInstance("Amoria");
    em.setProperty("apq1", "0");
    //2nd stage areas ..5 people distributed in the 3 areas
    em.setProperty("apq2", "0");
    em.setProperty("apq2_tries", "0");
    em.setProperty("apq3", "0");
    em.setProperty("apq3_tries", "0");
    em.setProperty("apq4", "0");
    em.setProperty("apq5", "0");
    var rand_combo = java.lang.Math.floor(java.lang.Math.random() * stg2_combo0.length);
    var rand_num = java.lang.Math.random();
    var combo0 = rand_num < 0.33 ? true : false;
    var combo1 = rand_num < 0.66 ? true : false;
    em.setProperty("apq2_0", combo0 ? stg2_combo0[rand_combo] : (combo1 ? stg2_combo1[rand_combo] : stg2_combo2[rand_combo]));
    em.setProperty("apq2_1", combo0 ? stg2_combo1[rand_combo] : (combo1 ? stg2_combo2[rand_combo] : stg2_combo0[rand_combo]));
    em.setProperty("apq2_2", combo0 ? stg2_combo2[rand_combo] : (combo1 ? stg2_combo0[rand_combo] : stg2_combo1[rand_combo]));
    var i = 0; //stage 3, 9 boxes = 5 random are 1
    for (var x = 0; x < 9; x++) {
        em.setProperty("apq3_" + x, "0");
    }
    while (i < 5) {
        for (var x = 0; x < 9; x++) {
            if (em.getProperty("apq3_" + x).equals("0") && java.lang.Math.random() < 0.2 && i < 5) {
                em.setProperty("apq3_" + x, "1");
                i++;
            }
        }
    }

    var map1 = eim.setInstanceMap(670010200);
    map1.resetFully();
    map1.getPortal("go00").setPortalState(false);
    map1.getPortal("go01").setPortalState(false);
    map1.getPortal("go02").setPortalState(false);
    var map2 = eim.setInstanceMap(670010300);
    map2.resetFully();
    map2.getPortal("next00").setPortalState(false); //disable NEXT
    var map3 = eim.setInstanceMap(670010400);
    map3.resetFully();
    map3.getPortal("next00").setPortalState(false); //disable NEXT
    var map4 = eim.setInstanceMap(670010500);
    map4.resetFully();
    map4.getPortal("next00").setPortalState(false); //disable NEXT
    eim.setInstanceMap(670010600).resetFully();
    eim.setInstanceMap(670010700).resetFully();
    eim.setInstanceMap(670010800).resetFully();
    eim.startEventTimer(3600000); //1 hr
    return eim;
}

function playerEntry(eim, player) {
    var NowMapID = eim.getProperty("NowMapID");
    if (NowMapID != null) {
        var toMapid = java.lang.Integer.parseInt(NowMapID);
        var map = eim.getMapFactory().getMap(toMapid);
        //if (toMapid == 670010800) {
        //    eim.restartEventTimer(60000); //1 min bonus
        //}
        player.changeMap(map, map.getPortal(0));
    } else {
        var map = eim.getMapInstance(0);
        player.changeMap(map, map.getPortal(0));
    }


    //加入远征队玩家信息
    eim.setProperty("isSquadPlayerID_" + player.getId(), "1");
}

function playerRevive(eim, player) {}

function scheduledTimeout(eim) {
    end(eim);
}

function changedMap(eim, player, mapid) {

    if (mapid < 670010200 || mapid > 670010800) {
        eim.unregisterPlayer(player);

        if (eim.disposeIfPlayerBelow(0, 0)) {
            em.setProperty("state", "0");
            em.setProperty("leader", "true");
        }
    } else if (mapid == 670010800) {
        if (em.getProperty("apq5").equals("0")) {
            eim.restartEventTimer(60000); //1 min bonus
            em.setProperty("apq5", "1");
        }
    }
    switch (mapid) {
        case 670010200:
        case 670010300:
        case 670010400:
        case 670010500:
        case 670010600:
        case 670010700:
            //case 670010800:
            //保存当前执行地图ID
            eim.setProperty("NowMapID", "" + mapid);
            return;
    }
}

function playerDisconnected(eim, player) {
    if (player.getMapId() == 670010800) {
        eim.setProperty("isSquadPlayerID_" + player.getId(), "0");
    }
    return;
    //eim.broadcastPlayerMsg(5, player.getName() + " 斷線了所以結婚小鎮组队結束了。");
    //end(eim);
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
    eim.disposeIfPlayerBelow(100, 670010000);
    em.setProperty("state", "0");
    em.setProperty("leader", "true");
}

function clearPQ(eim) {
    end(eim);
}

function allMonstersDead(eim) {}

function leftParty(eim, player) {
    end(eim);
}

function disbandParty(eim) {
    end(eim);
}

function playerDead(eim, player) {
    eim.setProperty("isSquadPlayerID_" + player.getId(), "0");
}

function cancelSchedule() {}