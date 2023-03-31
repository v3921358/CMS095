/* 
 * NPC :      Mihai
 * Map :      Timu's Forest
 */

function start() {
    cm.sendNext("哦。。。我刚刚找到什么了吗？那就只有一条出路了！让我们像一个 #r黑色之翼#k 一样战斗!");
}

function action(mode, type, selection) {
    if (mode == 1) {
	cm.removeNpc(cm.getMapId(), cm.getNpc());
	cm.spawnMonster(9001010,1); // Transforming
    }
    cm.dispose();
}