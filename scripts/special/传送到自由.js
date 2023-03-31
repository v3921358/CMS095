var status = -1;
function start() {
    action(1,0,0);
}

function action(mode, type, selection) {
    if (mode == 0) {
        cm.dispose();
        return;
    } else {
        status++;
    }
    if (status == 0) {
        cm.sendYesNo("确定要返回自由市场吗？");
    } else if (status == 1) {
        cm.warp(910000000);
		cm.dispose();
    }
    
}
