var status = -1;

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        if (status == 0) {
            cm.dispose();
        }
        status--;
    }
    if (status == 0) {
        cm.removeAll(4001163);
        cm.removeAll(4001169);
        cm.removeAll(2270004);
        cm.sendSimple("#b#L0#我要兑换亚泰尔耳环#l\r\n#L1#我要兑换蓝色亚泰尔耳环#l\r\n#L2#我要进入毒雾森林#l#k");
    } else if (status == 1) {
        if (selection == 0) {
            if (!cm.haveItem(1032060) && cm.haveItem(4001198, 20)) {
                cm.gainItem(1032060, 1);
                cm.gainItem(4001198, -20);
            } else {
                cm.sendOk("你需要20个亚泰尔碎片,或者是你已经有亚泰尔耳环了");
            }
        } else if (selection == 1) {
            if (cm.haveItem(1032060) && !cm.haveItem(1032061) && cm.haveItem(4001198, 30)) {
                cm.gainItem(1032060, -1);
                cm.gainItem(1032061, 1);
                cm.gainItem(4001198, -30);
            } else {
                cm.sendOk("你需要30个亚泰尔碎片跟亚泰尔耳环,或者是你已经有蓝色亚泰尔耳环了");
            }
        } else if (selection == 2) {
            if (cm.getPlayer().getParty() == null || !cm.isLeader()) {
                cm.sendOk("找您的队长来和我谈话。");
            } else {
                var party = cm.getPlayer().getParty().getMembers();
                var mapId = cm.getPlayer().getMapId();
                var next = true;
                var size = 0;
                var it = party.iterator();
                while (it.hasNext()) {
                    var cPlayer = it.next();
                    var ccPlayer = cm.getPlayer().getMap().getCharacterById(cPlayer.getId());
                    if (ccPlayer == null || ccPlayer.getLevel() < 70 || ccPlayer.getLevel() > 255) {
                        next = false;
                        break;
                    }
                    size += (ccPlayer.isGM() ? 4 : 1);
                }
                if (next && size >= 2) {
                    var em = cm.getEventManager("Ellin");
                    if (em == null) {
                        cm.sendOk("当前副本有问题，请联络管理员....");
                    } 
                    var prop = em.getProperty("state");
                    if (prop.equals("0") || prop == null) {
                        em.startInstance(cm.getPlayer().getParty(), cm.getPlayer().getMap(), 120);
                    } else {
                        cm.sendOk("里面已经有人在挑战...");
                    }
                } else {
                    cm.sendOk("有玩家等级不足或者人数不够。");
                }
            }
        }
        cm.dispose();
    }
}