/* 
 * NPC :      Mihai
 * Map :      Timu's Forest
 */

function start() {
    cm.sendNext("哦。。。我剛剛发现什麽了吗？只有一條出路！让我们像个黑翼一樣战斗吧！");
}

function action(mode, type, selection) {
    if (mode == 1) {
        cm.removeNpc(cm.getMapId(), cm.getNpc());
        cm.spawnMonster(9001009, 1); // Transforming
    }
    cm.dispose();
}