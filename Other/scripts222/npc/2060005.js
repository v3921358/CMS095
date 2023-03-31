var status = -1;
var minLevel = 70;
var maxLevel = 250;

var minPartySize = 1;
var maxPartySize = 6;

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        if (status == 0) {
            cm.dispose();
            return;
        }
        status--;
    }

    if (status == 0) {
        if (cm.getParty() == null) { // No Party
            cm.sendOk("请组队再來找我");
        } else if (!cm.isLeader()) { // Not Party Leader
            cm.sendOk("如果想嘗试保护野豬任務 #b请队长來找我#k 談談。#b#l");
        } else {
            // Check if all party members are within PQ levels
            var party = cm.getParty().getMembers();
            var mapId = cm.getMapId();
            var next = true;
            var levelValid = 0;
            var inMap = 0;
            var it = party.iterator();

            while (it.hasNext()) {
                var cPlayer = it.next();
                if ((cPlayer.getLevel() >= minLevel) && (cPlayer.getLevel() <= maxLevel)) {
                    levelValid += 1;
                } else {
                    next = false;
                }
                if (cPlayer.getMapid() == mapId) {
                    inMap += (1);
                }
            }
            if (party.size() > maxPartySize || inMap < minPartySize) {
                next = false;
            }
            if (next) {
                var em = cm.getEventManager("ProtectPig");
                if (em == null) {
                    cm.sendSimple("找不到此副本，请联絡管理员。#b#l");
                } else {
                    var prop = em.getProperty("state");
                    if (prop.equals("0") || prop == null) {
                        for (var i = 4001095; i < 4001099; i++) {
                            cm.givePartyItems(i, 0, true);
                        }
                        for (var i = 4001100; i < 4001101; i++) {
                            cm.givePartyItems(i, 0, true);
                        }
                        em.startInstance(cm.getParty(), cm.getMap());
                        cm.dispose();
                        return;
                    } else {
                        cm.sendSimple("已经有 #r另外一队#k 进去挑战了，请稍后再嘗试。#b#");
                    }
                }
            } else {
                cm.sendSimple("组队條件貌似沒有达到要求:\r\n\r\n#r最少的成员: " + minPartySize + " 全部等級必須在 " + minLevel + " 到 " + maxLevel + ".#b#l");
            }
        }
    }
}