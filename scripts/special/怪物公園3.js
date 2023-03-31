var maps = Array(954010000, 954020000, 954030000, 954040000, 954050000);
var minLevel = Array(125, 130, 140, 150, 165);
var maxLevel = Array(200, 200, 200, 200, 200);

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
                    if (cm.givePartyHaveItem(4001522, 1)) {
                        cm.givePartyItems(4001522, -1);
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