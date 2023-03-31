var status = -1;

function start() {
    if (cm.getPlayer().getMapId() == 211070100 || cm.getPlayer().getMapId() == 211070101 || cm.getPlayer().getMapId() == 211070110) {
        cm.sendYesNo("你想出去吗？ ?");
        status = 1;
        return;
    }
    if (cm.getPlayer().getLevel() < 120) {
        cm.sendOk("你的等于小于120級，无法挑战。");
        cm.dispose();
        return;
    }
    if (cm.getPlayer().getClient().getChannel() != 1 && cm.getPlayer().getClient().getChannel() != 2 && cm.getPlayer().getClient().getChannel() != 3) {
        cm.sendOk("只能在1，2，3頻道挑战。");
        cm.dispose();
        return;
    }
    var em = cm.getEventManager("VonLeonBattle");

    if (em == null) {
        cm.sendOk("脚本错誤，请联系管理员。");
        cm.dispose();
        return;
    }
    var eim_status = em.getProperty("state");
    var marr = cm.getQuestRecord(160107);
    var data = marr.getCustomData();
    if (data == null) {
        marr.setCustomData("0");
        data = "0";
    }
    var time = parseInt(data);
    if (eim_status == null || eim_status.equals("0")) {
        var squadAvailability = cm.getSquadAvailability("VonLeon");
        if (squadAvailability == -1) {
            status = 0;
            /*if (time + (12 * 3600000) >= cm.getCurrentTime() && !cm.getPlayer().isGM()) {
             cm.sendOk("在过去的12小时裏你已经挑战过，剩餘时间: " + cm.getReadableMillis(cm.getCurrentTime(), time + (12 * 3600000)));
             cm.dispose();
             return;
             }*/
            cm.sendYesNo("你有兴趣成为远征队长吗?");

        } else if (squadAvailability == 1) {
            /*if (time + (12 * 3600000) >= cm.getCurrentTime() && !cm.getPlayer().isGM()) {
             cm.sendOk("在过去的12小时裏你已经挑战过，剩餘时间: " + cm.getReadableMillis(cm.getCurrentTime(), time + (12 * 3600000)));
             cm.dispose();
             return;
             }*/
            // -1 = Cancelled, 0 = not, 1 = true
            var type = cm.isSquadLeader("VonLeon");
            if (type == -1) {
                cm.sendOk("远征队已結束，请重新註冊。");
                cm.dispose();
            } else if (type == 0) {
                var memberType = cm.isSquadMember("VonLeon");
                if (memberType == 2) {
                    cm.sendOk("你被禁止加入远征队。");
                    cm.dispose();
                } else if (memberType == 1) {
                    status = 5;
                    cm.sendSimple("你想做什么? \r\n#b#L0#加入远征队#l \r\n#b#L1#离开远征队#l \r\n#b#L2#查看远征队名单#l");
                } else if (memberType == -1) {
                    cm.sendOk("远征队已結束，请重新註冊。");
                    cm.dispose();
                } else {
                    status = 5;
                    cm.sendSimple("你想做什么? \r\n#b#L0#加入远征队#l \r\n#b#L1#离开远征队#l \r\n#b#L2#查看远征队名单#l");
                }
            } else { // Is leader
                status = 10;
                cm.sendSimple("你想做什么? \r\n#b#L0#查看远征队名单#l \r\n#b#L1#移除远征队员#l \r\n#b#L2#編輯限制列表#l \r\n#r#L3#进入地图#l");
                // TODO viewing!
            }
        } else {
            var eim = cm.getDisconnected("VonLeonBattle");
            if (eim == null) {
                var squd = cm.getSquad("VonLeon");
                if (squd != null) {
                    if (time + (6 * 3600000) >= cm.getCurrentTime() && !cm.getPlayer().isGM()) {
                        cm.sendOk("在过去的6小时裏你已经挑战过，剩餘时间: " + cm.getReadableMillis(cm.getCurrentTime(), time + (6 * 3600000)));
                        cm.dispose();
                        return;
                    }
                    cm.sendYesNo("其它远征队，正在对战中.\r\n" + squd.getNextPlayer());
                    status = 3;
                } else {
                    cm.sendOk("其它远征队，正在对战中.");
                    cm.safeDispose();
                }
            } else {
                cm.sendYesNo("你回來了阿，现在是否要重新返回远征队所在场地?");
                status = 2;
            }
        }
    } else {
        var eim = cm.getDisconnected("VonLeonBattle");
        if (eim == null) {
            var squd = cm.getSquad("VonLeon");
            if (squd != null) {
                /*if (time + (12 * 3600000) >= cm.getCurrentTime() && !cm.getPlayer().isGM()) {
                 cm.sendOk("在过去的12小时裏你已经挑战过，剩餘时间: " + cm.getReadableMillis(cm.getCurrentTime(), time + (12 * 3600000)));
                 cm.dispose();
                 return;
                 }*/
                cm.sendYesNo("其它远征队，正在对战中.\r\n" + squd.getNextPlayer());
                status = 3;
            } else {
                cm.sendOk("其它远征队，正在对战中.");
                cm.safeDispose();
            }
        } else {
            cm.sendYesNo("你回來了阿，现在是否要重新返回远征队所在场地?");
            status = 2;
        }
    }
}

function action(mode, type, selection) {
    switch (status) {
        case 0:
            if (mode == 1) {
                if (cm.getBossLogD("獅子王次數") == 3) {
                    cm.sendNext("很抱歉每天只能打3次..");
                    cm.dispose();
                    return;
                }
                if (cm.registerSquad("VonLeon", 5, " 已成为远征队长，想要參加远征队的玩家请开始进行申请。")) {
                    cm.sendOk("你成功申请了远征队队长，你必須在接下來的五分鐘召集玩家申请远征队，然后开始战斗。");
                    cm.setBossLog("獅子王次數");
                } else {
                    cm.sendOk("創建远征队出错.");
                }
            }
            cm.dispose();
            break;
        case 1:
            if (mode == 1) {
                cm.warp(211061001, 0);
            }
            cm.dispose();
            break;
        case 2:
            if (!cm.reAdd("VonLeonBattle", "VonLeon")) {
                cm.sendOk("发生未知错誤，请稍后再试.");
            }
            cm.safeDispose();
            break;
        case 3:
            if (mode == 1) {
                var squd = cm.getSquad("VonLeon");
                if (cm.getBossLogD("獅子王次數") == 3) {
                    cm.sendNext("很抱歉每天只能打3次..");
                    cm.dispose();
                    return;
                }
                if (squd != null && !squd.getAllNextPlayer().contains(cm.getPlayer().getName())) {

                    squd.setNextPlayer(cm.getPlayer().getName());
                    cm.sendOk("你已加入了远征队.");
                    cm.setBossLog("獅子王次數");
                }
            }
            cm.dispose();
            break;
        case 5:
            if (selection == 0) { // join
                if (cm.getBossLogD("獅子王次數") == 3) {
                    cm.sendNext("很抱歉每天只能打3次..");
                    cm.dispose();
                    return;
                }
                var ba = cm.addMember("VonLeon", true);
                if (ba == 2) {
                    cm.sendOk("远征队人數已满，请稍后再嘗试。");
                } else if (ba == 1) {

                    cm.setBossLog("獅子王次數");
                    cm.sendOk("申请远征队成功。");
                } else {
                    cm.sendOk("你已经在远征队里面了。");
                }
            } else if (selection == 1) {// withdraw
                var baa = cm.addMember("VonLeon", false);
                if (baa == 1) {
                    cm.sendOk("离开远征队成功。");
                } else {
                    cm.sendOk("你不再远征队里面。");
                }
            } else if (selection == 2) {
                if (!cm.getSquadList("VonLeon", 0)) {
                    cm.sendOk("发生未知错誤，远征队请求被拒絕。");
                }
            }
            cm.dispose();
            break;
        case 10:
            if (mode == 1) {
                if (selection == 0) {
                    if (!cm.getSquadList("VonLeon", 0)) {
                        cm.sendOk("由于未知的错誤，远征队的请求被拒絕了。");
                    }
                    cm.dispose();
                } else if (selection == 1) {
                    status = 11;
                    if (!cm.getSquadList("VonLeon", 1)) {
                        cm.sendOk("由于未知的错誤，远征队的请求被拒絕了。");
                        cm.dispose();
                    }
                } else if (selection == 2) {
                    status = 12;
                    if (!cm.getSquadList("VonLeon", 2)) {
                        cm.sendOk("由于未知的错誤，远征队的请求被拒絕了。");
                        cm.dispose();
                    }
                } else if (selection == 3) { // get insode
                    if (cm.getSquad("VonLeon") != null) {
                        var dd = cm.getEventManager("VonLeonBattle");
                        dd.startInstance(cm.getSquad("VonLeon"), cm.getMap(), 160107);
                    } else {
                        cm.sendOk("由于未知的错誤，远征队的请求被拒絕了。");
                    }
                    cm.dispose();
                }
            } else {
                cm.dispose();
            }
            break;
        case 11:
            cm.banMember("VonLeon", selection);
            cm.dispose();
            break;
        case 12:
            if (selection != -1) {
                cm.acceptMember("VonLeon", selection);
            }
            cm.dispose();
            break;
    }
}