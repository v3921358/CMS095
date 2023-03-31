var maps = Array(953000000, 953010000, 953020000, 953030000, 953040000, 953050000, 954000000);
var minLevel = Array(70, 75, 85, 95, 100, 110, 120);
var maxLevel = Array(160, 160, 160, 160, 160, 160, 160);

function start() {
    var selStr = "你想进入哪个地方？\r\n\r\n#b";
    for (var i = 0; i < maps.length; i++) {
        selStr += "#L" + i + "##m" + maps[i] + "# (怪物 Lv." + minLevel[i] + " - " + maxLevel[i] + ")#l\r\n";
    }
    cm.sendSimple(selStr);
}

function action(mode, type, selection) {
    if (mode == 1 && selection >= 0 && selection < maps.length) {
        if (cm.getParty() == null || !cm.isLeader()) {
            cm.sendOk("你想要进入的地區是组队游戏區域。可以通过#b队长#k入场。");
        } else {
            var party = cm.getParty().getMembers().iterator();
            var next = true;
            while (party.hasNext()) {
                var cPlayer = party.next();
                if (cPlayer.getLevel() < minLevel[selection] || cPlayer.getLevel() > maxLevel[selection] || cPlayer.getMapid() != cm.getMapId() || cPlayer.isCs() || cPlayer.isHp0() || !cPlayer.isOnline()) {
                    next = false;
                }
            }
            if (!next) {
                cm.sendOk("请确定你組员都在該地图,並且都在等級範圍內。");
            } else {
                var em = cm.getEventManager("MonsterPark");
                if (em == null || em.getInstance("MonsterPark" + maps[selection]) != null) {
                    cm.sendOk("怪物公園裏面已经有人了。");
                } else {
                    if (cm.givePartyHaveItem(4001516, 1)) {
                        cm.givePartyItems(4001516, -1);
                        em.startInstance_Party("" + maps[selection], cm.getPlayer());
                    } else {
                        cm.sendOk("有队友沒有門票。");
                    }
                }
            }
        }
    }
    cm.dispose();
}