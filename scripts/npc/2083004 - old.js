﻿/*
 NPC Name: 		Mark of the Squad
 Map(s): 		Entrance to Horned Tail's Cave
 Description: 		Horntail Battle starter
 */
        var status = -1;

function start() {
    if (cm.getPlayer().getClient().getChannel() != 2 && cm.getPlayer().getClient().getChannel() != 1 && cm.getPlayer().getClient().getChannel() != 3 && cm.getPlayer().getClient().getChannel() != 4) {
        cm.sendOk("暗黑龙王只有在频道 1 、 2 、 3 、 4 才可以挑战，频道4为进阶黑龙。");
        cm.dispose();
        return;
    }

    if (cm.getPlayer().getClient().getChannel() == 1 || cm.getPlayer().getClient().getChannel() == 2 || cm.getPlayer().getClient().getChannel() == 3) {
        if (cm.getPlayer().getLevel() < 80) {
            cm.sendOk("必须80等以上才可以挑战#b暗黑龙王#k");
            cm.dispose();
            return;
        }
        if (cm.getPlayer().getClient().getChannel() != 2 && cm.getPlayer().getClient().getChannel() != 1 && cm.getPlayer().getClient().getChannel() != 3 && cm.getPlayer().getClient().getChannel() != 4) {
            cm.sendOk("暗黑龙王只有在频道 1 、 2 、 3 、 4 才可以挑战，频道4为进阶黑龙。");
            cm.dispose();
            return;
        }
        var em = cm.getEventManager("HorntailBattle");

        if (em == null) {
            cm.sendOk("找不到脚本，请联系GM！！");
            cm.dispose();
            return;
        }
        var prop = em.getProperty("state");

        var marr = cm.getQuestRecord(160100);
        var data = marr.getCustomData();
        if (data == null) {
            marr.setCustomData("0");
            data = "0";
        }
        var time = parseInt(data);
        if (prop == null || prop.equals("0")) {
            var squadAvailability = cm.getSquadAvailability("Horntail");
            var check1 = cm.getMapFactory().getMap(240060100);
            var check2 = cm.getMapFactory().getMap(240060200);
            if (check1.playerCount() != 0 || check2.playerCount() != 0) {
                cm.sendOk("其它远征队，正在对战中。");
                cm.safeDispose();
            }
            if (squadAvailability == -1) {
                status = 0;
                cm.sendYesNo("你有兴趣成为远征队队长？");

            } else if (squadAvailability == 1) {
                // -1 = Cancelled, 0 = not, 1 = true
                var type = cm.isSquadLeader("Horntail");
                if (type == -1) {
                    cm.sendOk("由于远征队时间流逝，所以必须重新再申请一次远征队。");
                    cm.dispose();
                } else if (type == 0) {
                    var memberType = cm.isSquadMember("Horntail");
                    if (memberType == 2) {
                        cm.sendOk("你已经被黑名单了。");
                        cm.dispose();
                    } else if (memberType == 1) {
                        status = 5;
                        cm.sendSimple("你要做什么? \r\n#b#L0#查看远征队成员#l \r\n#b#L1#加入远征队#l \r\n#b#L2#退出远征队#l");
                    } else if (memberType == -1) {
                        cm.sendOk("由于远征队时间流逝，所以必须重新再申请一次远征队。");
                        cm.dispose();
                    } else {
                        status = 5;
                        cm.sendSimple("你要做什么? \r\n#b#L0#查看远征队成员#l \r\n#b#L1#加入远征队#l \r\n#b#L2#退出远征队#l");
                    }
                } else { // Is leader
                    status = 10;
                    cm.sendSimple("你要做什么? \r\n#b#L0#查看远征队成员#l \r\n#b#L1#移除远征队员#l \r\n#b#L2#编辑限制列表#l \r\n#r#L3#进入游戏#l");
                    // TODO viewing!
                }
            } else {
                var propssa = em.getProperty("leader");
                if (propssa != null && propssa.equals("true")) {
                    var eim = cm.getDisconnected("HorntailBattle");
                    if (eim == null) {
                        cm.sendOk("其它远征队，正在对战中。");
                        cm.safeDispose();
                    } else {
                        cm.sendOk("其它远征队，正在对战中。");
                        cm.safeDispose();
                    }
                } else {
                    cm.sendOk("很抱歉你的远征队队长离开了现场，所以你不能再返回战场。");
                    cm.safeDispose();
                }
            }
        } else {
            var propssb = em.getProperty("leader");
            if (propssb != null && propssb.equals("true")) {
                var eima = em.getInstance("HorntailBattle");
                var propsa = eima.getProperty("isSquadPlayerID_" + cm.getPlayer().getId());
                var saya = "\r\n" + (eima == null ? "eima is null" : propsa) + "\r\n";
                if ((eima != null) && (propsa != null) && propsa.equals("1")) {
                    status = 13;
                    saya += "#b现在是否要重新返回远征队所在场地？";
                    saya += "\r\n#r#L1#重新返回远征队所在场地#l";
                    cm.sendSimple(saya);
                } else {
                    eim = cm.getDisconnected("HorntailBattle");
                    if (eim == null) {
                        cm.sendOk("其它远征队，正在对战中。" + saya);
                        cm.safeDispose();
                    } else {
                        cm.sendOk("其它远征队，正在对战中。" + saya);
                        cm.safeDispose();
                    }
                }
            } else {
                var eimb = em.getInstance("HorntailBattle");
                var propsb = eimb.getProperty("isSquadPlayerID_" + cm.getPlayer().getId());
                var sayb = "\r\n" + (eimb == null ? "eimb is null" : propsb) + "\r\n";
                if ((eimb != null) && (propsb != null) && propsb.equals("1")) {
                    status = 13;
                    sayb += "#b现在是否要重新返回远征队所在场地？";
                    sayb += "\r\n#r#L1#重新返回远征队所在场地#l";
                    cm.sendSimple(sayb);
                } else {

                    cm.sendOk("很抱歉你的远征队队长离开了现场，所以你不能再返回战场。");
                    cm.safeDispose();
                }
            }
        }
    } else if (cm.getPlayer().getClient().getChannel() == 4) {
        if (cm.getPlayer().getLevel() < 80) {
            cm.sendOk("必须80等以上才可以挑战#b暗黑龙王#k");
            cm.dispose();
            return;
        }

        if (cm.getPlayer().getClient().getChannel() != 2 && cm.getPlayer().getClient().getChannel() != 1 && cm.getPlayer().getClient().getChannel() != 3 && cm.getPlayer().getClient().getChannel() != 4) {
            cm.sendOk("暗黑龙王只有在频道 1 、 2 、 3 、 4 才可以挑战，频道4为进阶黑龙。");
            cm.dispose();
            return;
        }

        var em = cm.getEventManager("ChaosHorntail");

        if (em == null) {
            cm.sendOk("找不到脚本，请联系GM！！");
            cm.dispose();
            return;
        }
        var prop = em.getProperty("state");

        var marr = cm.getQuestRecord(160100);
        var data = marr.getCustomData();
        if (data == null) {
            marr.setCustomData("0");
            data = "0";
        }
        var time = parseInt(data);
        if (prop == null || prop.equals("0")) {
            var squadAvailability = cm.getSquadAvailability("ChaosHt");
            var check12 = cm.getMapFactory().getMap(240060101);
            var check22 = cm.getMapFactory().getMap(240060201);
            if (check12.playerCount() != 0 || check22.playerCount() != 0) {
                cm.sendOk("其它远征队，正在对战中。");
                cm.safeDispose();
            }
            if (squadAvailability == -1) {
                status = 0;
                cm.sendYesNo("你有兴趣成为进阶黑龙远征队队长？");

            } else if (squadAvailability == 1) {
                // -1 = Cancelled, 0 = not, 1 = true
                var type = cm.isSquadLeader("ChaosHt");
                if (type == -1) {
                    cm.sendOk("由于远征队时间流逝，所以必须重新再申请一次远征队。");
                    cm.dispose();
                } else if (type == 0) {
                    var memberType = cm.isSquadMember("ChaosHt");
                    if (memberType == 2) {
                        cm.sendOk("你已经被黑名单了。");
                        cm.dispose();
                    } else if (memberType == 1) {
                        status = 5;
                        cm.sendSimple("你要做什么? \r\n#b#L0#查看远征队成员#l \r\n#b#L1#加入远征队#l \r\n#b#L2#退出远征队#l");
                    } else if (memberType == -1) {
                        cm.sendOk("由于远征队时间流逝，所以必须重新再申请一次远征队。");
                        cm.dispose();
                    } else {
                        status = 5;
                        cm.sendSimple("你要做什么? \r\n#b#L0#查看远征队成员#l \r\n#b#L1#加入远征队#l \r\n#b#L2#退出远征队#l");
                    }
                } else { // Is leader
                    status = 10;
                    cm.sendSimple("你要做什么? \r\n#b#L0#查看远征队成员#l \r\n#b#L1#移除远征队员#l \r\n#b#L2#编辑限制列表#l \r\n#r#L3#进入游戏#l");
                    // TODO viewing!
                }
            } else {
                var props = em.getProperty("leader");
                if (props != null && props.equals("true")) {
                    var eim = cm.getDisconnected("ChaosHorntail");
                    if (eim == null) {
                        cm.sendOk("其它远征队，正在对战中。");
                        cm.safeDispose();
                    } else {
                        cm.sendOk("其它远征队，正在对战中。");
                        cm.safeDispose();
                    }
                } else {
                    cm.sendOk("很抱歉你的远征队队长离开了现场，所以你不能再返回战场。");
                    cm.safeDispose();
                }
            }
        } else {
            var props = em.getProperty("leader");
            if (props != null && props.equals("true")) {
                var eimc = em.getInstance("ChaosHorntail");
                var propsc = eimc.getProperty("isSquadPlayerID_" + cm.getPlayer().getId());
                var sayc = "\r\n" + (eimc == null ? "eimc is null" : propsc) + "\r\n";
                if ((eimc != null) && (propsc != null) && propsc.equals("1")) {
                    status = 13;
                    sayc += "#b现在是否要重新返回远征队所在场地？";
                    sayc += "\r\n#r#L1#重新返回远征队所在场地#l";
                    cm.sendSimple(sayc);
                } else {
                    eim = cm.getDisconnected("ChaosHorntail");
                    if (eim == null) {
                        cm.sendOk("其它远征队，正在对战中。" + sayc);
                        cm.safeDispose();
                    } else {
                        cm.sendOk("其它远征队，正在对战中。" + sayc);
                        cm.safeDispose();
                    }
                }
            } else {
                var eimd = em.getInstance("ChaosHorntail");
                var propsd = eimd.getProperty("isSquadPlayerID_" + cm.getPlayer().getId());
                var sayd = "\r\n" + (eimd == null ? "eimd is null" : propsd) + "\r\n";
                if ((eimd != null) && (propsd != null) && propsd.equals("1")) {
                    status = 13;
                    sayd += "#b现在是否要重新返回远征队所在场地？";
                    sayd += "\r\n#r#L1#重新返回远征队所在场地#l";
                    cm.sendSimple(sayd);
                } else {
                    cm.sendOk("很抱歉你的远征队队长离开了现场，所以你不能再返回战场。");
                    cm.safeDispose();
                }
            }
        }

    } else {
        cm.sendOk("暗黑龙王只有在频道 1 、 2 、 3 、 4 才可以挑战，频道4为进阶黑龙。");
        cm.dispose();
        return;
    }


}

function action(mode, type, selection) {

    if (cm.getPlayer().getClient().getChannel() == 1 || cm.getPlayer().getClient().getChannel() == 2 || cm.getPlayer().getClient().getChannel() == 3) {
        switch (status) {
            case 0:
                if (mode == 1) {
                    if (cm.getBossLogD("龙王次数") == 5) {
                        cm.sendNext("很抱歉每天只能打5次..");
                        cm.dispose();
                        return;
                    }
                    if (cm.registerSquad("Horntail", 5, " 已成为暗黑龙王远征队长，想要参加远征队的玩家请开始进行申请。")) {
                        cm.sendOk("你成功申请了远征队队长，你必须在接下来的五分钟召集玩家申请远征队，然后开始战斗。");
                        cm.setBossLog("龙王次数");
                    } else {
                        cm.sendOk("创建远征队出错。");
                    }
                }
                cm.dispose();
                break;
            case 1:
                if (!cm.reAdd("HorntailBattle", "Horntail")) {
                    cm.sendOk("发生未知错误，请稍后再试。");
                }
                cm.safeDispose();
                break;
            case 3:
                if (mode == 1) {
                    if (cm.getBossLogD("龙王次数") == 5) {
                        cm.sendNext("很抱歉每天只能打5次..");
                        cm.dispose();
                        return;
                    }
                    var squd = cm.getSquad("Horntail");
                    if (squd != null && !squd.getAllNextPlayer().contains(cm.getPlayer().getName())) {

                        squd.setNextPlayer(cm.getPlayer().getName());
                        cm.sendOk("你已加入了远征队。");
                        cm.setBossLog("龙王次数");
                    }
                }
                cm.dispose();
                break;
            case 5:
                if (selection == 0) {
                    if (!cm.getSquadList("Horntail", 0)) {
                        cm.sendOk("发生未知错误，请稍后再试。");
                    }
                } else if (selection == 1) { // join
                    if (cm.getBossLogD("龙王次数") == 5) {
                        cm.sendNext("很抱歉每天只能打5次..");
                        cm.dispose();
                        return;
                    }
                    var ba = cm.addMember("Horntail", true);
                    if (ba == 2) {
                        cm.sendOk("远征队人数已满，请稍后再尝试。");
                    } else if (ba == 1) {

                        cm.setBossLog("龙王次数");
                        cm.sendOk("申请远征队成功。");

                    } else {
                        cm.sendOk("你已经在远征队里面了。");
                    }
                } else {// withdraw
                    var baa = cm.addMember("Horntail", false);
                    if (baa == 1) {
                        cm.sendOk("离开远征队成功。");
                    } else {
                        cm.sendOk("你不再远征队里面。");
                    }
                }
                cm.dispose();
                break;
            case 10:
                if (mode == 1) {
                    if (selection == 0) {
                        if (!cm.getSquadList("Horntail", 0)) {
                            cm.sendOk("由于未知的错误，远征队的请求被拒绝了。");
                        }
                        cm.dispose();
                    } else if (selection == 1) {
                        status = 11;
                        if (!cm.getSquadList("Horntail", 1)) {
                            cm.sendOk("由于未知的错误，远征队的请求被拒绝了。");
                            cm.dispose();
                        }
                    } else if (selection == 2) {
                        status = 12;
                        if (!cm.getSquadList("Horntail", 2)) {
                            cm.sendOk("由于未知的错误，远征队的请求被拒绝了。");
                            cm.dispose();
                        }
                    } else if (selection == 3) { // get insode
                        if (cm.getSquad("Horntail") != null) {
                            var dd = cm.getEventManager("HorntailBattle");
                            dd.startInstance(cm.getSquad("Horntail"), cm.getMap(), 160100);
                            if (!cm.getPlayer().isGM()) {
                                cm.getMap().startSpeedRun();
                            }
                        } else {
                            cm.sendOk("由于未知的错误，远征队的请求被拒绝了。");
                        }
                        cm.dispose();
                    }
                } else {
                    cm.dispose();
                }
                break;
            case 11:
                cm.banMember("Horntail", selection);
                cm.dispose();
                break;
            case 12:
                if (selection != -1) {
                    cm.acceptMember("Horntail", selection);
                }
                cm.dispose();
                break;
            case 13:
                var em = cm.getEventManager("HorntailBattle");
                if ((selection == 1) && (em != null)) {
                    var eim = em.getInstance("HorntailBattle");
                    if ((eim != null) && (eim.getProperty("isSquadPlayerID_" + cm.getPlayer().getId()) != null)) {
                        eim.registerPlayer(cm.getPlayer());
                    }
                }
                cm.dispose();
                break;
            default:
                cm.dispose();
                break;
        }
    } else if (cm.getPlayer().getClient().getChannel() == 4) {
        switch (status) {
            case 0:
                if (mode == 1) {
                    if (cm.getBossLogD("混沌龙王次数") == 5) {
                        cm.sendNext("很抱歉每天只能打5次..");
                        cm.dispose();
                        return;
                    }
                    if (cm.registerSquad("ChaosHt", 5, " 已成为暗黑龙王远征队长，想要参加远征队的玩家请开始进行申请。")) {
                        cm.sendOk("你成功申请了远征队队长，你必须在接下来的五分钟召集玩家申请远征队，然后开始战斗。");
                        cm.setBossLog("混沌龙王次数");
                    } else {
                        cm.sendOk("创建远征队出错。");
                    }
                }
                cm.dispose();
                break;
            case 1:
                if (!cm.reAdd("ChaosHorntail", "ChaosHt")) {
                    cm.sendOk("发生未知错误，请稍后再试。");
                }
                cm.safeDispose();
                break;
            case 3:
                if (mode == 1) {
                    var squd = cm.getSquad("ChaosHt");
                    if (cm.getBossLogD("混沌龙王次数") == 5) {
                            cm.sendNext("很抱歉每天只能打5次..");
                            cm.dispose();
                            return;
                        }
                    if (squd != null && !squd.getAllNextPlayer().contains(cm.getPlayer().getName())) {
                        
                        squd.setNextPlayer(cm.getPlayer().getName());
                        cm.sendOk("你已加入了远征队。");
                        cm.setBossLog("混沌龙王次数");
                    }
                }
                cm.dispose();
                break;
            case 5:
                if (selection == 0) {
                    if (!cm.getSquadList("ChaosHt", 0)) {
                        cm.sendOk("发生未知错误，请稍后再试。");
                    }
                } else if (selection == 1) { // join
                    if (cm.getBossLogD("混沌龙王次数") == 5) {
                            cm.sendNext("很抱歉每天只能打5次..");
                            cm.dispose();
                            return;
                        }
                    var ba = cm.addMember("ChaosHt", true);
                    if (ba == 2) {
                        cm.sendOk("远征队人数已满，请稍后再尝试。");
                    } else if (ba == 1) {
                        cm.sendOk("申请远征队成功。");
                        cm.setBossLog("混沌龙王次数");
                    } else {
                        cm.sendOk("你已经在远征队里面了。");
                    }
                } else {// withdraw
                    var baa = cm.addMember("ChaosHt", false);
                    if (baa == 1) {
                        cm.sendOk("离开远征队成功。");
                    } else {
                        cm.sendOk("你不再远征队里面。");
                    }
                }
                cm.dispose();
                break;
            case 10:
                if (mode == 1) {
                    if (selection == 0) {
                        if (!cm.getSquadList("ChaosHt", 0)) {
                            cm.sendOk("由于未知的错误，远征队的请求被拒绝了。");
                        }
                        cm.dispose();
                    } else if (selection == 1) {
                        status = 11;
                        if (!cm.getSquadList("ChaosHt", 1)) {
                            cm.sendOk("由于未知的错误，远征队的请求被拒绝了。");
                            cm.dispose();
                        }
                    } else if (selection == 2) {
                        status = 12;
                        if (!cm.getSquadList("ChaosHt", 2)) {
                            cm.sendOk("由于未知的错误，远征队的请求被拒绝了。");
                            cm.dispose();
                        }
                    } else if (selection == 3) { // get insode
                        if (cm.getSquad("ChaosHt") != null) {
                            var dd = cm.getEventManager("ChaosHorntail");
                            dd.startInstance(cm.getSquad("ChaosHt"), cm.getMap(), 160100);
                            if (!cm.getPlayer().isGM()) {
                                cm.getMap().startSpeedRun();
                            }
                        } else {
                            cm.sendOk("由于未知的错误，远征队的请求被拒绝了。");
                        }
                        cm.dispose();
                    }
                } else {
                    cm.dispose();
                }
                break;
            case 11:
                cm.banMember("ChaosHt", selection);
                cm.dispose();
                break;
            case 12:
                if (selection != -1) {
                    cm.acceptMember("ChaosHt", selection);
                }
                cm.dispose();
                break;
            case 13:
                var em = cm.getEventManager("ChaosHorntail");
                if ((selection == 1) && (em != null)) {
                    var eim = em.getInstance("ChaosHorntail");
                    if ((eim != null) && (eim.getProperty("isSquadPlayerID_" + cm.getPlayer().getId()) != null)) {
                        eim.registerPlayer(cm.getPlayer());
                    }
                }
                cm.dispose();
                break;
            default:
                cm.dispose();
                break;
        }
    } else {
        cm.sendOk("暗黑龙王只有在频道 1 、 2 、 3 、 4 才可以挑战，频道4为进阶黑龙。");
        cm.dispose();
        return;
    }
}