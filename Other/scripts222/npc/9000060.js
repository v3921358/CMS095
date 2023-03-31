/*
	Gingerman - Witch Tower [Easy Mode]
*/

function start() {
cm.sendSimple("哇，我一路爬到顶层了！ \n\r #L0##b我想离开这里#k#l");
}

function action(mode, type, selection) {
	if (selection == 0){
		cm.warp(980040000, 0);
		cm.dispose();
	} else {
		cm.dispose();
	}
}