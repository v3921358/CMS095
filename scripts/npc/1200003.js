/**
	Konpei - Near the Hideout(801040000)
*/

function start() {
    cm.sendYesNo("你想去維多利亞港吗?");
}

function action(mode, type, selection) {
    if (mode == 0) {
	cm.sendOk("加油！跟我來！");
    } else {
	cm.warp(104000000,0);
    }
    cm.dispose();
}