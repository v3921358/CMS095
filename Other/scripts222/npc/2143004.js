var status = -1;
var bosstime=2;
function start() {
    if (cm.getPlayer().getMapId() == 271040100) {
        cm.sendYesNo("你想出去吗？");
        status = 1;
        return;
    }
    if (cm.getPlayer().getLevel() < 120) {
        cm.sendOk("你的等級不够!");
        cm.dispose();
        return;
    }
    // if (cm.getPlayer().getClient().getChannel() != 1) {
    //     cm.sendOk("当前副本只能在1頻道进行");
    //     cm.dispose();
    //     return;
    // }
    var em = cm.getEventManager("CygnusBattle");

    if (em == null) {
        cm.sendOk("脚本错誤，请联系管理员。");
        cm.dispose();
        return;
    }
    var eim_status = em.getProperty("state");
    var marr = cm.getQuestRecord(160109);
    var data = marr.getCustomData();
    if (data == null) {
        marr.setCustomData("0");
        data = "0";
    }
    var time = parseInt(data);
    if (eim_status == null || eim_status.equals("0")) {
        var squadAvailability = cm.getSquadAvailability("Cygnus");
        if (squadAvailability == -1) {
            status = 0;
            /*if (time + (24 * 3600000) >= cm.getCurrentTime() && !cm.getPlayer().isGM()) {
             cm.sendOk("在过去的24小时裏，你已经去过副本了。剩下的时间: " + cm.getReadableMillis(cm.getCurrentTime(), time + (24 * 3600000)));
             cm.dispose();
             return;
             }*/
            cm.sendYesNo("杀死女皇不是一件容易的事。如果你要面对她，祝你好运！你要进去吗？");

        } else if (squadAvailability == 1) {
            /*if (time + (24 * 3600000) >= cm.getCurrentTime() && !cm.getPlayer().isGM()) {
             cm.sendOk("在过去的24小时裏，你已经去过副本了。剩下的时间: " + cm.getReadableMillis(cm.getCurrentTime(), time + (24 * 3600000)));
             cm.dispose();
             return;
             }*/
            // -1 = Cancelled, 0 = not, 1 = true
            var type = cm.isSquadLeader("Cygnus");
            if (type == -1) {
                cm.sendOk("远征队已結束，请重新註冊。");
                cm.dispose();
            } else if (type == 0) {
                var memberType = cm.isSquadMember("Cygnus");
                if (memberType == 2) {
                    cm.sendOk("你被禁止加入远征队.");
                    cm.dispose();
                } else if (memberType == 1) {
                    status = 5;
                    cm.sendSimple("你想做什么? \r\n#b#L0#加入远征队#l \r\n#b#L1#离开远征队#l \r\n#b#L2#查看远征队名单#l");
                } else if (memberType == -1) {
                    cm.sendOk("远征队结束，请重新报名.");
                    cm.dispose();
                } else {
                    status = 5;
                    cm.sendSimple("你想做什么? \r\n#b#L0#加入远征队#l \r\n#b#L1#离开远征队#l \r\n#b#L2#查看远征队名单#l");
                }
            } else { // Is leader
                status = 10;
                cm.sendSimple("你想做什么，成为远征队长吗? \r\n#b#L0#查看远征队名单#l \r\n#b#L1#從远征队移除成员#l \r\n#b#L2#移除禁止进入远征队的成员#l \r\n#r#L3#进入地图#l");
                // TODO viewing!
            }
        } else {
            /*var eim = cm.getDisconnected("CygnusBattle");
             if (eim == null) {
             var squd = cm.getSquad("Cygnus");
             if (squd != null) {
             cm.sendYesNo("远征队与女皇的战爭已经开始.\r\n" + squd.getNextPlayer());
             status = 3;
             } else {
             cm.sendOk("远征队与女皇的战爭已经开始.");
             cm.safeDispose();
             }
             } else {
             cm.sendYesNo("你回來了？你还想加入远征队吗？");
             status = 2;
             }*/

            var props = em.getProperty("leader");
            if (props != null && props.equals("true")) {
                var eim = cm.getDisconnected("CygnusBattle");
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
            var eimc = em.getInstance("CygnusBattle");
            var propsc = eimc.getProperty("isSquadPlayerID_" + cm.getPlayer().getId());
            var sayc = "\r\n" + (eimc == null ? "eimc is null" : propsc) + "\r\n";
            if ((eimc != null) && (propsc != null) && propsc.equals("1")) {
                status = 13;
                sayc += "#b现在是否要重新返回远征队所在场地？";
                sayc += "\r\n#r#L1#重新返回远征队所在场地#l";
                cm.sendSimple(sayc);
            } else {
                eim = cm.getDisconnected("CygnusBattle");
                if (eim == null) {
                    cm.sendOk("其它远征队，正在对战中。" + sayc);
                    cm.safeDispose();
                } else {
                    cm.sendOk("其它远征队，正在对战中。" + sayc);
                    cm.safeDispose();
                }
            }
        } else {
            var eimd = em.getInstance("CygnusBattle");
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

        /*var eim = cm.getDisconnected("CygnusBattle");
         if (eim == null) {
         var squd = cm.getSquad("Cygnus");
         if (squd != null) {
         cm.sendYesNo("远征队与女皇的战爭已经开始.\r\n" + squd.getNextPlayer());
         status = 3;
         } else {
         cm.sendOk("远征队与女皇的战爭已经开始.");
         cm.safeDispose();
         }
         } else {
         cm.sendYesNo("你回來了？你还想加入远征队吗？");
         status = 2;
         }*/
    }
}

function action(mode, type, selection) {
    switch (status) {
        case 0:
            if (mode == 1) {
                if (cm.getBossLogD("女皇次數") == bosstime) {
                    cm.sendNext("很抱歉每天只能打"+bosstime+"次..");
                    cm.dispose();
                    return;
                }
                if (cm.registerSquad("Cygnus", 5, " 被任命为远征队长。如果你願意參加，请在时间段內報名远征队。")) {
                    cm.sendOk("你被任命为远征队长。接下來的5分鐘，你可以管理加入探險队的成员。 ");
                    cm.setBossLog("女皇次數");
                } else {
                    cm.sendOk("添加队伍时出错 .");
                }
            }
            cm.dispose();
            break;
        case 1:
            if (mode == 1) {
                cm.warp(cm.getMap().getAllMonstersThreadsafe().size() == 0 ? 271040000 : 271040000, 0);
            }
            cm.dispose();
            break;
        case 2:
            if (!cm.reAdd("CygnusBattle", "Cygnus")) {
                cm.sendOk("错誤…请再试一次。");
            }
            cm.safeDispose();
            break;
        case 3:
            if (mode == 1) {
                if (cm.getBossLogD("女皇次數") == bosstime) {
                    cm.sendNext("很抱歉每天只能打"+bosstime+"次..");
                    cm.dispose();
                    return;
                }
                var squd = cm.getSquad("Cygnus");

                if (squd != null && !squd.getAllNextPlayer().contains(cm.getPlayer().getName())) {

                    squd.setNextPlayer(cm.getPlayer().getName());
                    cm.sendOk("你已经預訂了现场。");
                    cm.setBossLog("女皇次數");
                }
            }
            cm.dispose();
            break;
        case 5:
            if (selection == 0) { // join
                if (cm.getBossLogD("女皇次數") == bosstime) {
                    cm.sendNext("很抱歉每天只能打"+bosstime+"次..");
                    cm.dispose();
                    return;
                }
                var ba = cm.addMember("Cygnus", true);
                if (ba == 2) {
                    cm.sendOk("远征队目前已满，请稍后再试 .");
                } else if (ba == 1) {

                    cm.setBossLog("女皇次數");
                    cm.sendOk("你已经成功加入了远征队。");
                } else {
                    cm.sendOk("你已经是远征队的一份子了.");
                }
            } else if (selection == 1) {// withdraw
                var baa = cm.addMember("Cygnus", false);
                if (baa == 1) {
                    cm.sendOk("你已经成功從队伍中退出。");
                } else {
                    cm.sendOk("你不是远征队的一份子 .");
                }
            } else if (selection == 2) {
                if (!cm.getSquadList("Cygnus", 0)) {
                    cm.sendOk("由于一个未知的错誤，对小队的请求被拒絕了。");
                }
            }
            cm.dispose();
            break;
        case 10:
            if (mode == 1) {
                if (selection == 0) {
                    if (!cm.getSquadList("Cygnus", 0)) {
                        cm.sendOk("由于一个未知的错誤，对小队的请求被拒絕了。");
                    }
                    cm.dispose();
                } else if (selection == 1) {
                    status = 11;
                    if (!cm.getSquadList("Cygnus", 1)) {
                        cm.sendOk("由于一个未知的错誤，对小队的请求被拒絕了。");
                        cm.dispose();
                    }
                } else if (selection == 2) {
                    status = 12;
                    if (!cm.getSquadList("Cygnus", 2)) {
                        cm.sendOk("由于一个未知的错誤，对小队的请求被拒絕了。");
                        cm.dispose();
                    }
                } else if (selection == 3) { // get insode
                    if (cm.getSquad("Cygnus") != null) {
                        var dd = cm.getEventManager("CygnusBattle");
                        dd.startInstance(cm.getSquad("Cygnus"), cm.getMap(), 160109);
                    } else {
                        cm.sendOk("由于一个未知的错誤，对小队的请求被拒絕了。");
                    }
                    cm.dispose();
                }
            } else {
                cm.dispose();
            }
            break;
        case 11:
            cm.banMember("Cygnus", selection);
            cm.dispose();
            break;
        case 12:
            if (selection != -1) {
                cm.acceptMember("Cygnus", selection);
            }
            cm.dispose();
            break;
        case 13:
            var em = cm.getEventManager("CygnusBattle");
            if ((selection == 1) && (em != null)) {
                var eim = em.getInstance("CygnusBattle");
                if ((eim != null) && (eim.getProperty("isSquadPlayerID_" + cm.getPlayer().getId()) != null)) {
                    eim.registerPlayer(cm.getPlayer());
                }
            }
            cm.dispose();
            break;
    }
}