/*
 NPC Name: 		The Forgotten Temple Manager
 Map(s): 		Deep in the Shrine - Forgotten Twilight
 Description: 		Pink Bean battle starter
 */
var status = -1;

function start() {
    if (cm.getPlayer().getLevel() < 120) {
        cm.sendOk("There is a level requirement of 120 to attempt Pink Bean.");
        cm.dispose();
        return;
    }
    if (cm.getPlayer().getClient().getChannel() != 1 && cm.getPlayer().getClient().getChannel() != 2 && cm.getPlayer().getClient().getChannel() != 3 && cm.getPlayer().getClient().getChannel() != 4 && cm.getPlayer().getClient().getChannel() != 5 && cm.getPlayer().getClient().getChannel() != 6 && cm.getPlayer().getClient().getChannel() != 7 && cm.getPlayer().getClient().getChannel() != 8 && cm.getPlayer().getClient().getChannel() != 9 && cm.getPlayer().getClient().getChannel() != 10 && cm.getPlayer().getClient().getChannel() != 11 && cm.getPlayer().getClient().getChannel() != 12 && cm.getPlayer().getClient().getChannel() != 13 && cm.getPlayer().getClient().getChannel() != 14 && cm.getPlayer().getClient().getChannel() != 15) {
        cm.sendOk("Pink Bean may only be attempted on channel 1-15.");
        cm.dispose();
        return;
    }
    var em = cm.getEventManager("PinkBeanBattle");

    if (em == null) {
        cm.sendOk("事件不存在，请联系GM.");
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
            if (time + (12 * 3600000) >= cm.getCurrentTime() && !cm.getPlayer().isGM()) {
                cm.sendOk("You have already went to PinkBean in the past 12 hours. Time left: " + cm.getReadableMillis(cm.getCurrentTime(), time + (12 * 3600000)));
                cm.dispose();
                return;
            }
            cm.sendYesNo("Are you interested in becoming the leader of the Pinkbean expedition Squad?");

        } else if (squadAvailability == 1) {
            if (time + (12 * 3600000) >= cm.getCurrentTime() && !cm.getPlayer().isGM()) {
                cm.sendOk("You have already went to PinkBean in the past 12 hours. Time left: " + cm.getReadableMillis(cm.getCurrentTime(), time + (12 * 3600000)));
                cm.dispose();
                return;
            }
            // -1 = Cancelled, 0 = not, 1 = true
            var type = cm.isSquadLeader("PinkBean");
            if (type == -1) {
                cm.sendOk("远征队结束，请重新报名.");
                cm.dispose();
            } else if (type == 0) {
                var memberType = cm.isSquadMember("PinkBean");
                if (memberType == 2) {
                    cm.sendOk("你被禁止加入了。");
                    cm.dispose();
                } else if (memberType == 1) {
                    status = 5;
                    cm.sendSimple("What would you like to do? \r\n#b#L0#Join the squad to Twilight of the Gods#l \r\n#b#L1#Leave the squad to Twilight of the Gods#l \r\n#b#L2#See the list of members on the squad#l");
                } else if (memberType == -1) {
                    cm.sendOk("远征队结束，请重新报名.");
                    cm.dispose();
                } else {
                    status = 5;
                    cm.sendSimple("What would you like to do? \r\n#b#L0#Join the squad to Twilight of the Gods#l \r\n#b#L1#Leave the squad to Twilight of the Gods#l \r\n#b#L2#See the list of members on the squad#l");
                }
            } else { // Is leader
                status = 10;
                cm.sendSimple("你现在想做什么, expedition leader? \r\n#b#L0#View expedition list#l \r\n#b#L1#Kick from expedition#l \r\n#b#L2#Remove user from ban list#l \r\n#r#L3#Select expedition team and enter#l");
                // TODO viewing!
            }
        } else {
            var eim = cm.getDisconnected("PinkBeanBattle");
            if (eim == null) {
                var squd = cm.getSquad("PinkBean");
                if (squd != null) {
                    if (time + (12 * 3600000) >= cm.getCurrentTime() && !cm.getPlayer().isGM()) {
                        cm.sendOk("You have already went to PinkBean in the past 12 hours. Time left: " + cm.getReadableMillis(cm.getCurrentTime(), time + (12 * 3600000)));
                        cm.dispose();
                        return;
                    }
                    cm.sendYesNo("战斗已经开始了.\r\n" + squd.getNextPlayer());
                    status = 3;
                } else {
                    cm.sendOk("战斗已经开始了.");
                    cm.safeDispose();
                }
            } else {
                cm.sendYesNo("啊，你回来了。你想再次加入你的队伍吗?");
                status = 2;
            }
        }
    } else {
        var eim = cm.getDisconnected("PinkBeanBattle");
        if (eim == null) {
            var squd = cm.getSquad("PinkBean");
            if (squd != null) {
                if (time + (12 * 3600000) >= cm.getCurrentTime() && !cm.getPlayer().isGM()) {
                    cm.sendOk("You have already went to PinkBean in the past 12 hours. Time left: " + cm.getReadableMillis(cm.getCurrentTime(), time + (12 * 3600000)));
                    cm.dispose();
                    return;
                }
                cm.sendYesNo("战斗已经开始了.\r\n" + squd.getNextPlayer());
                status = 3;
            } else {
                cm.sendOk("战斗已经开始了.");
                cm.safeDispose();
            }
        } else {
            cm.sendYesNo("啊，你回来了。你想再次加入你的队伍吗?");
            status = 2;
        }
    }
}

function action(mode, type, selection) {
    switch (status) {
        case 0:
            if (mode == 1) {
                if (cm.registerSquad("PinkBean", 5, " 他被任命为队长。如果你想加入，请在此期间内登记加入探险队.")) {
                    cm.sendOk("你被任命为小队的队长。在接下来的5分钟里，你可以加入探险队的成员.");
                } else {
                    cm.sendOk("加入你的队伍时出现了一个错误.");
                }
            }
            cm.dispose();
            break;
        case 2:
            if (!cm.reAdd("PinkBeanBattle", "PinkBean")) {
                cm.sendOk("出错了，请重试.");
            }
            cm.safeDispose();
            break;
        case 3:
            if (mode == 1) {
                var squd = cm.getSquad("PinkBean");
                if (squd != null && !squd.getAllNextPlayer().contains(cm.getPlayer().getName())) {
                    squd.setNextPlayer(cm.getPlayer().getName());
                    cm.sendOk("你已经加入远征队了.");
                }
            }
            cm.dispose();
            break;
        case 5:
            if (selection == 0) { // join
                var ba = cm.addMember("PinkBean", true);
                if (ba == 2) {
                    cm.sendOk("队伍目前已满，请稍后再试.");
                } else if (ba == 1) {
                    cm.sendOk("成功加入远征队");
                } else {
                    cm.sendOk("你已经是队里的一员了.");
                }
            } else if (selection == 1) {// withdraw
                var baa = cm.addMember("PinkBean", false);
                if (baa == 1) {
                    cm.sendOk("你已经成功地退出了");
                } else {
                    cm.sendOk("你不是队伍的一员.");
                }
            } else if (selection == 2) {
                if (!cm.getSquadList("PinkBean", 0)) {
                    cm.sendOk("由于一个未知的错误，远征队的申请被拒绝了.");
                }
            }
            cm.dispose();
            break;
        case 10:
            if (mode == 1) {
                if (selection == 0) {
                    if (!cm.getSquadList("PinkBean", 0)) {
                        cm.sendOk("由于一个未知的错误，远征队的申请被拒绝了.");
                    }
                    cm.dispose();
                } else if (selection == 1) {
                    status = 11;
                    if (!cm.getSquadList("PinkBean", 1)) {
                        cm.sendOk("由于一个未知的错误，远征队的申请被拒绝了.");
                        cm.dispose();
                    }
                } else if (selection == 2) {
                    status = 12;
                    if (!cm.getSquadList("PinkBean", 2)) {
                        cm.sendOk("由于一个未知的错误，远征队的申请被拒绝了.");
                        cm.dispose();
                    }
                } else if (selection == 3) { // get insode
                    if (cm.getSquad("PinkBean") != null) {
                        var dd = cm.getEventManager("PinkBeanBattle");
                        dd.startInstance(cm.getSquad("PinkBean"), cm.getMap(), 160104);
                    } else {
                        cm.sendOk("由于一个未知的错误，远征队的申请被拒绝了.");
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
    }
}