function start() {
    cm.sendYesNo("你现在是想离开这里吗?");
}

function action(mode, type, selection) {
    if (mode == 1) {
        if (cm.getPlayer().getMapId() == 401060100) {
            var em = cm.getEventManager("BossMagnus_HARD");
            if (em != null) {
                var eim = em.getInstance("BossMagnus_HARD");
                if (eim != null) {
                    var propsa = eim.getProperty("isSquadPlayerID_" + cm.getPlayer().getId());
                    if ((propsa != null) && propsa.equals("1")) {
                        eim.setProperty("isSquadPlayerID_" + cm.getPlayer().getId(), "0");
                    }
                }
            }
            cm.warp(401060000, 0);
        }
    }
    cm.dispose();
}
