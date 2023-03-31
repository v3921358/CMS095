/* Amon
 * Last Mission : Zakum's Altar (280030000)
 */

var status = -1;

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (status < 1 && mode == 0) {
            cm.sendOk("好，需要的时候再来找我。");
            cm.dispose();
            return;
        }
        if (mode == 1) {
            status++;
        } else {
            status--;
        }
        if (status == 0) {
            switch (cm.getMapId()) {
                case 280030000:
                    cm.sendYesNo("你想离开这里到 #m211042300# 吗?");
                    break;
                case 280030001:
                    cm.sendYesNo("你想离开这里到 #m211042300# 吗?");
                    break;
              default:
                    cm.sendYesNo("你想离开这里到 #m211042300# 吗?");
                    break;
            }
        } else if (status == 1) {
            switch (cm.getMapId()) {
                case 280030000:
                    var em = cm.getEventManager("ZakumBattle");
                    if (em != null) {
                        var eim = em.getInstance("ZakumBattle");
                        if (eim != null) {
                            var propsa = eim.getProperty("isSquadPlayerID_" + cm.getPlayer().getId());
                            if ((propsa != null) && propsa.equals("1")) {
                                eim.setProperty("isSquadPlayerID_" + cm.getPlayer().getId(), "0");
                            }
                        }
                    }
                    cm.warp(211042200, 0);
                    cm.dispose();
                    break;
                case 280030001:
                    var ema = cm.getEventManager("ChaosZakum");
                    if (ema != null) {
                        var eima = ema.getInstance("ChaosZakum");
                        if (eima != null) {
                            var propsaa = eima.getProperty("isSquadPlayerID_" + cm.getPlayer().getId());
                            if ((propsaa != null) && propsaa.equals("1")) {
                                eima.setProperty("isSquadPlayerID_" + cm.getPlayer().getId(), "0");
                            }
                        }
                    }
                    cm.warp(211042200, 0);
                    cm.dispose();
                    break;
                default:
                    var em = cm.getEventManager("ZakumBattle");
                    if (em != null) {
                        var eim = em.getInstance("ZakumBattle");
                        if (eim != null) {
                            var propsa = eim.getProperty("isSquadPlayerID_" + cm.getPlayer().getId());
                            if ((propsa != null) && propsa.equals("1")) {
                                eim.setProperty("isSquadPlayerID_" + cm.getPlayer().getId(), "0");
                            }
                        }
                    }
                    var ema = cm.getEventManager("ChaosZakum");
                    if (ema != null) {
                        var eima = ema.getInstance("ChaosZakum");
                        if (eima != null) {
                            var propsaa = eima.getProperty("isSquadPlayerID_" + cm.getPlayer().getId());
                            if ((propsaa != null) && propsaa.equals("1")) {
                                eima.setProperty("isSquadPlayerID_" + cm.getPlayer().getId(), "0");
                            }
                        }
                    }
                    cm.warp(211042200, 0);
                    cm.dispose();
                    break;

            }
        }
    }
}