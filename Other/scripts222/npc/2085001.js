function start() {
    var eim = cm.getDisconnected("Dragon_Nest");
    if (eim != null && cm.getPlayer().getParty() != null) { //only skip if not null
        eim.registerPlayer(cm.getPlayer());
    }
    if (cm.getPlayer().getParty() == null || !cm.isLeader()) {
        cm.sendOk("你不是组队长。");
        cm.dispose();
        return;
    }
    var party = cm.getPlayer().getParty().getMembers();
    var next = true;
    var size = 0;
    var it = party.iterator();
    while (it.hasNext()) {
        var cPlayer = it.next();
        var ccPlayer = cm.getPlayer().getMap().getCharacterById(cPlayer.getId());
        var SkillLevel = cm.getSkillLevel(ccPlayer,1026, ccPlayer.getJob());
        if (ccPlayer == null || ccPlayer.getLevel() < 100 || (SkillLevel <= 0)) {
            next = false;
            break;
        } else if (ccPlayer.isGM()) {
            size += 4;
        } else {
            size++;
        }
    }
    if (next && size >= 2) {
        var em = cm.getEventManager("Dragonica");
        if (em == null) {
            cm.sendOk("脚本出错，请联系管理员.");
        } else {
            var prop = em.getProperty("state");
            if (prop == null || prop.equals("0")) {
                em.startInstance(cm.getParty(), cm.getMap(), 200);
            } else {
                cm.sendOk("已经有人在挑战副本。");
            }
        }
    } else {
        cm.sendOk("需要2人以上100级以上并且拥有飞行技能才能挑战。");
    }
    cm.dispose();
}

function action(a, b, c) {}