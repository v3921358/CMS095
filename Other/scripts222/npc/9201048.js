﻿var status = -1;
var nextt = true;
var check = [670010200, 670010300, 670010400, 670010500, 670010600, 670010700, 670010800];
function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        if (status == 0) {
            cm.dispose();
        }
        status--;
    }
    cm.removeAll(4031595);
    cm.removeAll(4031594);
    cm.removeAll(4031597);
    if (status == 0) {
        cm.sendSimple("#b#L0#离开这里 (沒有退費)#l\r\n#L1#我的队伍已经准备好，我们要挑战了！#l#k");
    } else if (status == 1) {
        if (selection == 0) {
            cm.warp(670010000, 0);
        } else {
            if (cm.getPlayer().getParty() == null || !cm.isLeader()) {
                var em = cm.getEventManager("Amoria");
                var eim = em.getInstance("Amoria");
                var propsa = eim.getProperty("isSquadPlayerID_" + cm.getPlayer().getId());
                if ((eim != null) && (propsa != null) && propsa.equals("1")) {
                    var ema = cm.getEventManager("Amoria");
                    if ((ema != null)) {
                        var eim = em.getInstance("Amoria");
                        if ((eim != null) && (eim.getProperty("isSquadPlayerID_" + cm.getPlayer().getId()) != null)) {
                            eim.registerPlayer(cm.getPlayer());
                        }
                    }
                    cm.dispose();
                    return;
                } else {
                    cm.sendOk("必須要有队长在这里。");
                }
            } else {
                for (var i = 0; i < check.length; i++) {
                    if (cm.getPlayer().getClient().getChannelServer().getMapFactory().getMap(check[i]).playerCount() > 0) {
                        nextt = false;
                    }
                }
                if (!nextt) {
                    var em = cm.getEventManager("Amoria");
                    var eim = em.getInstance("Amoria");
                    var propsa = eim.getProperty("isSquadPlayerID_" + cm.getPlayer().getId());
                    if ((eim != null) && (propsa != null) && propsa.equals("1")) {
                        var ema = cm.getEventManager("Amoria");
                        if ((ema != null)) {
                            var eim = em.getInstance("Amoria");
                            if ((eim != null) && (eim.getProperty("isSquadPlayerID_" + cm.getPlayer().getId()) != null)) {
                                eim.registerPlayer(cm.getPlayer());
                            }
                        }
                        cm.dispose();
                        return;
                    } else {
                        cm.sendNext("目前地图有人");
                        cm.dispose();
                        return;
                    }
                }
                var party = cm.getPlayer().getParty().getMembers();
                var mapId = cm.getPlayer().getMapId();
                var next = true;
                var size = 0;
                var it = party.iterator();
                while (it.hasNext()) {
                    var cPlayer = it.next();
                    var ccPlayer = cm.getPlayer().getMap().getCharacterById(cPlayer.getId());
                    if (ccPlayer == null || ccPlayer.getLevel() < 40) {
                        next = false;
                        break;
                    }
                    size += (ccPlayer.isGM() ? 2 : 1);
                }
                if (next && size >= 2) {
                    var em = cm.getEventManager("Amoria");
                    if (em == null) {
                        cm.sendOk("请稍后再嘗试。");
                    } else {
                        em.startInstance(cm.getPlayer().getParty(), cm.getPlayer().getMap());
                    }
                } else {
                    var em = cm.getEventManager("Amoria");
                    var eim = em.getInstance("Amoria");
                    var propsa = eim.getProperty("isSquadPlayerID_" + cm.getPlayer().getId());
                    if ((eim != null) && (propsa != null) && propsa.equals("1")) {
                        var ema = cm.getEventManager("Amoria");
                        if ((ema != null)) {
                            var eim = em.getInstance("Amoria");
                            if ((eim != null) && (eim.getProperty("isSquadPlayerID_" + cm.getPlayer().getId()) != null)) {
                                eim.registerPlayer(cm.getPlayer());
                            }
                        }
                        cm.dispose();
                        return;
                    } else {
                        cm.sendOk("队伍成员必須有6个以上都在这里。");
                    }
                }
            }
        }
        cm.dispose();
    }
}