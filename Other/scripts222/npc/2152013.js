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
	cm.sendSimple("哇！你是來这裏工作的吗？\n\r #L0##b我想成为一名機械师!#k#l#l");
    } else if (status == 1) {
	cm.sendOk("真的吗？");
	cm.safeDispose();
    }
}