var status = -1;

function start() {
    if (cm.getPlayer().getMapId() == 551030200) {
        cm.sendYesNo("你要离开了吗?");
        status = 1;
        return;
    }
    if (cm.getPlayer().getLevel() < 90) {
        cm.sendOk("你的等級尚未达到90....");
        cm.dispose();
        return;
    }
    if (cm.getPlayer().getClient().getChannel() != 1 && cm.getPlayer().getClient().getChannel() != 2 && cm.getPlayer().getClient().getChannel() != 3) {
        cm.sendOk("熊獅只能在1、2、3頻挑战.");
        cm.dispose();
        return;
    }
    var em = cm.getEventManager("ScarTarBattle");

    if (em == null) {
        cm.sendOk("脚本错誤，请联系管理员。");
        cm.dispose();
        return;
    }
    var eim_status = em.getProperty("state");
    var marr = cm.getQuestRecord(160108);
    var data = marr.getCustomData();
    if (data == null) {
        marr.setCustomData("0");
        data = "0";
    }
    var time = parseInt(data);
    if (eim_status == null || eim_status.equals("0")) {
        var squadAvailability = cm.getSquadAvailability("ScarTar");
        if (squadAvailability == -1) {
            status = 0;
            cm.sendYesNo("你想成为远征队队长吗？");

        } else if (squadAvailability == 1) {
            // -1 = Cancelled, 0 = not, 1 = true
            var type = cm.isSquadLeader("ScarTar");
            if (type == -1) {
                cm.sendOk("已经結束了申请。");
                cm.dispose();
            } else if (type == 0) {
                var memberType = cm.isSquadMember("ScarTar");
                if (memberType == 2) {
                    cm.sendOk("在远征队的制裁名单。");
                    cm.dispose();
                } else if (memberType == 1) {
                    status = 5;
                    cm.sendSimple("你要做什么? \r\n#b#L0#加入远征队#l \r\n#b#L1#退出远征队#l \r\n#b#L2#查看远征队名单#l");
                } else if (memberType == -1) {
                    cm.sendOk("远征队员已经达到30名，请稍后再试。");
                    cm.dispose();
                } else {
                    status = 5;
                    cm.sendSimple("你要做什么? \r\n#b#L0#加入远征队#l \r\n#b#L1#退出远征队#l \r\n#b#L2#查看远征队名单#l");
                }
            } else { // Is leader
                status = 10;
                cm.sendSimple("你现在想做什么？\r\n#b#L0#查看远征队成员#l \r\n#b#L1#管理远征队成员#l \r\n#b#L2#編輯限制列表#l \r\n#r#L3#进入地图#l");
                // TODO viewing!
            }
        } else {
            var props = em.getProperty("leader");
            if (props != null && props.equals("true")) {
                var eim = cm.getDisconnected("ScarTarBattle");
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
            var eimc = em.getInstance("ScarTarBattle");
            var propsc = eimc.getProperty("isSquadPlayerID_" + cm.getPlayer().getId());
            var sayc = "\r\n" + (eimc == null ? "eimc is null" : propsc) + "\r\n";
            if ((eimc != null) && (propsc != null) && propsc.equals("1")) {
                status = 13;
                sayc += "#b现在是否要重新返回远征队所在场地？";
                sayc += "\r\n#r#L1#重新返回远征队所在场地#l";
                cm.sendSimple(sayc);
            } else {
                eim = cm.getDisconnected("ScarTarBattle");
                if (eim == null) {
                    cm.sendOk("其它远征队，正在对战中。" + sayc);
                    cm.safeDispose();
                } else {
                    cm.sendOk("其它远征队，正在对战中。" + sayc);
                    cm.safeDispose();
                }
            }
        } else {
            var eimd = em.getInstance("ScarTarBattle");
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
}

function action(mode, type, selection) {
    switch (status) {
        case 0:
            if (mode == 1) {
                if (cm.getBossLogD("熊獅王次數") == 5) {
                    cm.sendOk("很抱歉每天只能打5次..");
                    cm.dispose();
                    return;
                }
                if (cm.registerSquad("ScarTar", 5, " 已经成为了远征队队长。如果你想加入远征队，请重新打开对话申请加入远征队。")) {
                    cm.sendOk("你已经成为了远征队队长。接下來的5分鐘，请等待队员们的申请。");
                    cm.setBossLog("熊獅王次數");
                } else {
                    cm.sendOk("未知错誤.");
                }
            }
            cm.dispose();
            break;
        case 1:
            if (mode == 1) {
                cm.warp(551030100, 0);
            }
            cm.dispose();
            break;
        case 2:
            if (!cm.reAdd("ScarTarBattle", "ScarTar")) {
                cm.sendOk("由于未知的错誤，操作失敗。");
            }
            cm.safeDispose();
            break;
        case 3:
            if (mode == 1) {
                var squd = cm.getSquad("ScarTar");
                if (cm.getBossLogD("熊獅王次數") == 5) {
                    cm.sendOk("很抱歉每天只能打5次..");
                    cm.dispose();
                    return;
                }
                if (squd != null && !squd.getAllNextPlayer().contains(cm.getPlayer().getName())) {

                    squd.setNextPlayer(cm.getPlayer().getName());
                    cm.sendOk("你已经成功登記为下一組。");
                    cm.setBossLog("熊獅王次數");
                }
            }
            cm.dispose();
            break;
        case 5:
            if (selection == 0) { // join
                if (cm.getBossLogD("熊獅王次數") == 5) {
                    cm.sendOk("很抱歉每天只能打5次..");
                    cm.dispose();
                    return;
                }
                var ba = cm.addMember("ScarTar", true);
                if (ba == 2) {
                    cm.sendOk("远征队员已经达到30名，请稍后再试。");
                } else if (ba == 1) {

                    cm.setBossLog("熊獅王次數");
                    cm.sendOk("申请加入远征队成功，请等候队长指示。");
                } else {
                    cm.sendOk("你已经參加了远征队，请等候队长指示。");
                }
            } else if (selection == 1) {// withdraw
                var baa = cm.addMember("ScarTar", false);
                if (baa == 1) {
                    cm.sendOk("成功退出远征队。");
                } else {
                    cm.sendOk("你沒有參加远征队。");
                }
            } else if (selection == 2) {
                if (!cm.getSquadList("ScarTar", 0)) {
                    cm.sendOk("由于未知的错誤，操作失敗。");
                }
            }
            cm.dispose();
            break;
        case 10:
            if (mode == 1) {
                if (selection == 0) {
                    if (!cm.getSquadList("ScarTar", 0)) {
                        cm.sendOk("由于未知的错誤，操作失敗。");
                    }
                    cm.dispose();
                } else if (selection == 1) {
                    status = 11;
                    if (!cm.getSquadList("ScarTar", 1)) {
                        cm.sendOk("由于未知的错誤，操作失敗。");
                        cm.dispose();
                    }
                } else if (selection == 2) {
                    status = 12;
                    if (!cm.getSquadList("ScarTar", 2)) {
                        cm.sendOk("由于未知的错誤，操作失敗。");
                        cm.dispose();
                    }
                } else if (selection == 3) { // get insode
                    if (cm.getSquad("ScarTar") != null) {
                        var dd = cm.getEventManager("ScarTarBattle");
                        dd.startInstance(cm.getSquad("ScarTar"), cm.getMap(), 160108);
                    } else {
                        cm.sendOk("由于未知的错誤，操作失敗。");
                    }
                    cm.dispose();
                }
            } else {
                cm.dispose();
            }
            break;
        case 11:
            cm.banMember("ScarTar", selection);
            cm.dispose();
            break;
        case 12:
            if (selection != -1) {
                cm.acceptMember("ScarTar", selection);
            }
            cm.dispose();
            break;
        case 13:
            var em = cm.getEventManager("ScarTarBattle");
            if ((selection == 1) && (em != null)) {
                var eim = em.getInstance("ScarTarBattle");
                if ((eim != null) && (eim.getProperty("isSquadPlayerID_" + cm.getPlayer().getId()) != null)) {
                    eim.registerPlayer(cm.getPlayer());
                }
            }
            cm.dispose();
            break;
    }
}