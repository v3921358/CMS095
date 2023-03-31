
function action(mode, type, selection) {
    if (cm.isQuestActive(23005) && cm.haveItem(4032783)) {
	cm.sendNext("你把海報貼在留言板上。");
	cm.forceStartQuest(23006, "1");
	cm.gainItem(4032783, -1);
    } else {
    	cm.sendOk("这是爱德斯坦自由市场的留言板。据说，任何人都可以张貼海報，但是董事会被宣傳了关于黑翅膀的宣傳。");
    }
    cm.dispose();
}