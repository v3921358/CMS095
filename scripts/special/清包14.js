/*
 ZEVMS冒险岛(079)游戏服务端
 脚本：去上海
 */

var status = 0;
function start() {
    cm.sendYesNo("    Hi~#b#h ##k，是否要清理掉背包#r特殊栏#k所有物品？此次清空后无法恢复。");
}

function action(mode, type, selection) {
    if (mode != 1) {
        if (mode == 0)
        cm.dispose();
		cm.openNpc(9900004,5);
        return;
    }
    status++;
    if (status == 1) {
		for (var i = 0; i <= 96; i++) {
			if (cm.getInventory(5).getItem(i) != null) {
				cm.removeAll(cm.getInventory(5).getItem(i).getItemId());
			}
		}
        cm.dispose();
	}
}