var maps = Array(952000000, 952010000, 952020000, 952030000, 952040000, 953000000, 953010000, 953020000, 953030000, 953040000, 953050000, 954000000, 954010000, 954020000, 954030000, 954040000, 954050000);
var minLevel = Array(20, 45, 50, 55, 60, 70, 75, 85, 95, 100, 110, 120, 125, 130, 140, 150, 165);
var maxLevel = Array(30, 55, 60, 65, 70, 80, 85, 95, 105, 110, 120, 130, 135, 140, 150, 165, 200);

function start() {
    var selStr = "妳想進入哪個地方？\r\n\r\n#b";
    for (var i = 0; i < maps.length; i++) {
        selStr += "#L" + i + "##m" + maps[i] + "# (怪物 Lv." + minLevel[i] + " - " + maxLevel[i] + ")#l\r\n";
    }
    cm.sendSimple(selStr);
}

function action(mode, type, selection) {
    if (mode == 1 && selection >= 0 && selection < maps.length) {
        if (cm.getParty() == null || !cm.isLeader()) {
            cm.sendOk("妳想要進入的地區是組隊遊戲區域。可以通過#b隊長#k入場。");
        } else {
            var party = cm.getParty().getMembers().iterator();
            var next = true;
            while (party.hasNext()) {
                var cPlayer = party.next();
                if (cPlayer.getLevel() < minLevel[selection] || cPlayer.getLevel() > maxLevel[selection] || cPlayer.getMapid() != cm.getMapId()) {
                    next = false;
                }
            }
            if (!next) {
                cm.sendOk("請確定妳組員都在該地圖,並且都在等級範圍內。");
            } else {
                var em = cm.getEventManager("MonsterPark");
                if (em == null || em.getInstance("MonsterPark" + maps[selection]) != null) {
                    cm.sendOk("怪物公園裏面已經有人了。");
                } else {
                    if (cm.givePartyHaveItem(4001514,1)) {
                        em.startInstance_Party("" + maps[selection], cm.getPlayer());
                    } else {
                        cm.sendOk("有隊友沒有門票。");
                    }
                }
            }
        }
    }
    cm.dispose();
}