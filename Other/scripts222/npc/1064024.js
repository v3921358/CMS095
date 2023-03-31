function start() {
    cm.sendYesNo("你现在是想离开这里吗?");
}

function action(mode, type, selection) {
    if (mode == 1) {
        if (cm.getPlayer().getMapId() == 105200710) {
            var em = cm.getEventManager("BossBloody_CHAOS");
            if (em != null) {
                var eim = em.getInstance("BossBloody_CHAOS");
                if (eim != null) {
                    var propsa = eim.getProperty("isSquadPlayerID_" + cm.getPlayer().getId());
                    if ((propsa != null) && propsa.equals("1")) {
                        eim.setProperty("isSquadPlayerID_" + cm.getPlayer().getId(), "0");
                    }
                }
            }
            cm.warp(105200000, 0);
        }
        if (cm.getPlayer().getMapId() == 105200610) {
            var em = cm.getEventManager("BossPierre_CHAOS");
            if (em != null) {
                var eim = em.getInstance("BossPierre_CHAOS");
                if (eim != null) {
                    var propsa = eim.getProperty("isSquadPlayerID_" + cm.getPlayer().getId());
                    if ((propsa != null) && propsa.equals("1")) {
                        eim.setProperty("isSquadPlayerID_" + cm.getPlayer().getId(), "0");
                    }
                }
            }
            cm.warp(105200000, 0);
        }
        if (cm.getPlayer().getMapId() == 105200510) {
            var em = cm.getEventManager("BossBanban_CHAOS");
            if (em != null) {
                var eim = em.getInstance("BossBanban_CHAOS");
                if (eim != null) {
                    var propsa = eim.getProperty("isSquadPlayerID_" + cm.getPlayer().getId());
                    if ((propsa != null) && propsa.equals("1")) {
                        eim.setProperty("isSquadPlayerID_" + cm.getPlayer().getId(), "0");
                    }
                }
            }
            cm.warp(105200000, 0);
        }
        if (cm.getPlayer().getMapId() == 105200810) {
            var em = cm.getEventManager("BossBelen_CHAOS");
            if (em != null) {
                var eim = em.getInstance("BossBelen_CHAOS");
                if (eim != null) {
                    var propsa = eim.getProperty("isSquadPlayerID_" + cm.getPlayer().getId());
                    if ((propsa != null) && propsa.equals("1")) {
                        eim.setProperty("isSquadPlayerID_" + cm.getPlayer().getId(), "0");
                    }
                }
            }
            cm.warp(105200000, 0);
        }
    }
    cm.dispose();
}
