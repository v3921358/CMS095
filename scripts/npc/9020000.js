/*
 Lakelis - Victoria Road: Kerning City (103000000)
 **/

function start() {
    cm.removeAll(4001007);
    cm.removeAll(4001008);
    if (cm.getPlayer().getMapId() != 910340700) {
        cm.sendYesNo("你想移动到第一次同行<等待室>吗?");
        return;
    }
    if (cm.getParty() == null) { // No Party
        cm.sendSimple("请组队再来找我。");
    } else if (!cm.isLeader()) { // Not Party Leader
        cm.sendSimple("请叫你的队长来找我!");
    } else {
        // Check if all party members are within Levels 21-30
        var party = cm.getParty().getMembers();
        var mapId = cm.getMapId();
        var next = true;
        var levelValid = 0;
        var inMap = 0;

        var it = party.iterator();
        while (it.hasNext()) {
            var cPlayer = it.next();
            if ((cPlayer.getLevel() >= 20 && cPlayer.getLevel() <= 255) || cPlayer.getJobId() == 900) {
                levelValid += 1;
            } else {
                next = false;
            }
            if (cPlayer.getMapid() == mapId) {
                inMap += (cPlayer.getJobId() == 900 ? 4 : 1);
            }
        }
        if (party.size() > 6 || inMap < 3) {
            next = false;
        }
        if (next) {
            var em = cm.getEventManager("KerningPQ");
            if (em == null) {
                cm.sendSimple("找不到脚本，请联系GM！");
            } else {
                var prop = em.getProperty("state");
                if (prop == null || prop.equals("0")) {
                    em.startInstance(cm.getParty(), cm.getMap(), 70);
                    cm.dispose();
                } else {
                    cm.sendSimple("已经有队伍在里面挑战了。");
                }
                cm.removeAll(4001008);
                cm.removeAll(4001007);
            }
        } else {
            cm.sendSimple("你的队伍需要三个人,等级必须在21-200之间,请确认你的队友有没有都在这里,或是里面已经有人了!");
        }
    }

}

function action(mode, type, selection) {
    if (cm.getPlayer().getMapId() != 910340700) {
        cm.saveLocation("MULUNG_TC");
        cm.warp(910340700, 0);
    }
    cm.dispose();
}