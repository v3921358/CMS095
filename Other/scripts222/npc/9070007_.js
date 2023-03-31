
function start() {
    cm.sendYesNo("让奴仆们訓练？如果是这樣的话，就进去吧…你可能会去一个熟悉的地方。你怎麽认为？你想进去吗？ ");
}

function action(mode, type, selection) {
    if (mode == 0) {
	cm.sendNext("你一定很忙吧？你應該试着进去。你可能会在一个陌生的地方 .");
    } else {
	if (cm.getPlayer().getBattler(0) != null || cm.getPlayer().getBoxed().size() >= 1) {
	    cm.warp(193000000, 0);
	} else {
	    cm.sendNext("嘿，嘿…我认为你沒有訓练的奴仆…也許你可以從我的朋友那裏得到一个如果他们感觉不错…");
	}
    }
    cm.dispose();
}