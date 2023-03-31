
function start() {
    cm.sendYesNo("如果你现在离开，你必须重新开始。你确定要离开吗?");
}

function action(mode, type, selection) {
    if (mode == 1) {
	cm.warp(105100100);
    }
    cm.dispose();
}