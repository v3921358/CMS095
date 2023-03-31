/**
	Konpei - Near the Hideout(801040000)
*/

function start() {
    cm.sendYesNo("你想去瑞恩吗?");
}

function action(mode, type, selection) {
    if (mode == 0) {
	cm.sendOk("FUUUUUU!,出发吧!");
    } else {
	cm.warp(140000000,0);
    }
    cm.dispose();
}