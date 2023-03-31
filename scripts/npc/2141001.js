/*
 NPC Name: 		The Forgotten Temple Manager
 Map(s): 		Deep in the Shrine - Forgotten Twilight
 Description: 		Pink Bean battle starter
 */
        var status = -1;

function start() {
    if (cm.getPlayer().getLevel() < 120) {
        cm.sendOk("你的等級不足120，无法挑战。");
        cm.dispose();
        return;
    }
    // if (cm.getPlayer().getClient().getChannel() != 1) {
    //     cm.sendOk("只能在頻道1挑战。");
    //     cm.dispose();
    //     return;
    // }
    var em = cm.getEventManager("PinkBeanBattle");

    if (em == null) {
        cm.sendOk("脚本错誤，请联系管理员。");
        cm.dispose();
        return;
    }
    var eim_status = em.getProperty("state");
    var marr = cm.getQuestRecord(160104);
    var data = marr.getCustomData();
    if (data == null) {
        marr.setCustomData("0");
        data = "0";
    }
    var time = parseInt(data);
    if (eim_status == null || eim_status.equals("0")) {

        var squadAvailability = cm.getSquadAvailability("PinkBean");
        if (squadAvailability == -1) {
            status = 0;
            cm.sendYesNo("你有兴趣成为远征队队长?");
        } else if (squadAvailability == 1) {
            // -1 = Cancelled, 0 = not, 1 = true
            var type = cm.isSquadLeader("PinkBean");
            if (type == -1) {
                cm.sendOk("由于远征队时间流逝，所以必須重新再申请一次远征队.");
                cm.dispose();
            } else if (type == 0) {
                var memberType = cm.isSquadMember("PinkBean");
                if (memberType == 2) {
                    cm.sendOk("你已经被黑名单了。");
                    cm.dispose();
                } else if (memberType == 1) {
                    status = 5;
                    cm.sendSimple("你要做什么? \r\n#b#L0#加入远征队#l \r\n#b#L1#退出远征队#l \r\n#b#L2#查看远征队成员#l");
                } else if (memberType == -1) {
                    cm.sendOk("由于远征队时间流逝，所以必須重新再申请一次远征队。");
                    cm.dispose();
                } else {
                    status = 5;
                    cm.sendSimple("你要做什么?\r\n#b#L0#加入远征队#l \r\n#b#L1#退出远征队#l \r\n#b#L2#查看远征队成员#l");
                }
            } else { // Is leader
                status = 10;
                cm.sendSimple("你要做什么? \r\n#b#L0#查看远征队成员#l \r\n#b#L1#移除远征队员#l \r\n#b#L2#編輯限制列表#l \r\n#r#L3#进入地图#l");
                // TODO viewing!
            }
        } else {
            /*var eim = cm.getDisconnected("PinkBeanBattle");
             if (eim == null) {
             var squd = cm.getSquad("PinkBean");
             if (squd != null) {
             cm.sendYesNo("其它远征队，正在对战中.\r\n" + squd.getNextPlayer());
             status = 3;
             } else {
             cm.sendOk("其它远征队，正在对战中.");
             cm.safeDispose();
             }
             } else {
             cm.sendYesNo("你回來了阿，现在是否要重新返回远征队所在场地?");
             status = 2;
             }*/


            var props = em.getProperty("leader");
            if (props != null && props.equals("true")) {
                var eim = cm.getDisconnected("PinkBeanBattle");
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
            var eimc = em.getInstance("PinkBeanBattle");
            var propsc = eimc.getProperty("isSquadPlayerID_" + cm.getPlayer().getId());
            var sayc = "\r\n" + (eimc == null ? "eimc is null" : propsc) + "\r\n";
            if ((eimc != null) && (propsc != null) && propsc.equals("1")) {
                status = 13;
                sayc += "#b现在是否要重新返回远征队所在场地？";
                sayc += "\r\n#r#L1#重新返回远征队所在场地#l";
                cm.sendSimple(sayc);
            } else {
                eim = cm.getDisconnected("PinkBeanBattle");
                if (eim == null) {
                    cm.sendOk("其它远征队，正在对战中。" + sayc);
                    cm.safeDispose();
                } else {
                    cm.sendOk("其它远征队，正在对战中。" + sayc);
                    cm.safeDispose();
                }
            }
        } else {
            var eimd = em.getInstance("PinkBeanBattle");
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

        /*var eim = cm.getDisconnected("PinkBeanBattle");
         if (eim == null) {
         var squd = cm.getSquad("PinkBean");
         if (squd != null) {
         cm.sendYesNo("其它远征队，正在对战中.\r\n" + squd.getNextPlayer());
         status = 3;
         } else {
         cm.sendOk("其它远征队，正在对战中.");
         cm.safeDispose();
         }
         } else {
         cm.sendYesNo("你回來了阿，现在是否要重新返回远征队所在场地?");
         status = 2;
         }*/
    }
}

function action(mode, type, selection) {
    switch (status) {
        case 0:
            if (mode == 1) {
                if (cm.getBossLogD("皮卡啾次數") == 3) {
                    cm.sendNext("很抱歉每天只能打3次..");
                    cm.dispose();
                    return;
                }
                if (cm.registerSquad("PinkBean", 5, " 已成为远征队长，想要參加远征队的玩家请开始进行申请。")) {
                    cm.sendOk("你成功申请了远征队队长，你必須在接下來的五分鐘召集玩家申请远征队，然后开始战斗。");
                    cm.setBossLog("皮卡啾次數");
                } else {
                    cm.sendOk("創建远征队出错。");
                }
            }
            cm.dispose();
            break;
        case 2:
            if (!cm.reAdd("PinkBeanBattle", "PinkBean")) {
                cm.sendOk("发生未知错誤，请稍后再试。");
            }
            cm.safeDispose();
            break;
        case 3:
            if (mode == 1) {
                var squd = cm.getSquad("PinkBean");
                if (cm.getBossLogD("皮卡啾次數") == 3) {
                    cm.sendNext("很抱歉每天只能打3次..");
                    cm.dispose();
                    return;
                }
                if (squd != null && !squd.getAllNextPlayer().contains(cm.getPlayer().getName())) {

                    squd.setNextPlayer(cm.getPlayer().getName());
                    cm.sendOk("你已加入了远征队。");
                    cm.setBossLog("皮卡啾次數");
                }
            }
            cm.dispose();
            break;
        case 5:
            if (selection == 0) { // join
                if (cm.getBossLogD("皮卡啾次數") == 3) {
                    cm.sendNext("很抱歉每天只能打3次..");
                    cm.dispose();
                    return;
                }
                var ba = cm.addMember("PinkBean", true);
                if (ba == 2) {
                    cm.sendOk("远征队人數已满，请稍后再嘗试。");
                } else if (ba == 1) {

                    cm.setBossLog("皮卡啾次數");
                    cm.sendOk("申请远征队成功。");
                } else {
                    cm.sendOk("你已经在远征队里面了。");
                }
            } else if (selection == 1) {// withdraw
                var baa = cm.addMember("PinkBean", false);
                if (baa == 1) {
                    cm.sendOk("离开远征队成功。");
                } else {
                    cm.sendOk("你不再远征队里面。");
                }
            } else if (selection == 2) {
                if (!cm.getSquadList("PinkBean", 0)) {
                    cm.sendOk("由于未知的错誤，远征队的请求被拒絕了。");
                }
            }
            cm.dispose();
            break;
        case 10:
            if (mode == 1) {
                if (selection == 0) {
                    if (!cm.getSquadList("PinkBean", 0)) {
                        cm.sendOk("由于未知的错誤，远征队的请求被拒絕了。");
                    }
                    cm.dispose();
                } else if (selection == 1) {
                    status = 11;
                    if (!cm.getSquadList("PinkBean", 1)) {
                        cm.sendOk("由于未知的错誤，远征队的请求被拒絕了。");
                        cm.dispose();
                    }
                } else if (selection == 2) {
                    status = 12;
                    if (!cm.getSquadList("PinkBean", 2)) {
                        cm.sendOk("由于未知的错誤，远征队的请求被拒絕了。");
                        cm.dispose();
                    }
                } else if (selection == 3) { // get insode
                    if (cm.getSquad("PinkBean") != null) {
                        var dd = cm.getEventManager("PinkBeanBattle");
                        dd.startInstance(cm.getSquad("PinkBean"), cm.getMap(), 160104);
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
            cm.banMember("PinkBean", selection);
            cm.dispose();
            break;
        case 12:
            if (selection != -1) {
                cm.acceptMember("PinkBean", selection);
            }
            cm.dispose();
            break;
        case 13:
            var em = cm.getEventManager("PinkBeanBattle");
            if ((selection == 1) && (em != null)) {
                var eim = em.getInstance("PinkBeanBattle");
                if ((eim != null) && (eim.getProperty("isSquadPlayerID_" + cm.getPlayer().getId()) != null)) {
                    eim.registerPlayer(cm.getPlayer());
                }
            }
            cm.dispose();
            break;
    }
}