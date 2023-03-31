var status = -1;

function action(mode, type, selection) {
	if (mode == 1) {
		status++;
	} else {
		if (status == 0) {
			cm.dispose();
		}
		status--;
	}
	if (status == 0) {
		cm.sendSimple("雷昂！雷昂！你听不见我的声音吗？#h #，请你帮我和雷昂说话！啊，明明就在眼前……");
	} else if (status == 1) {

		cm.dispose();
	}
}