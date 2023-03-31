var status = -1;
var selectionLog = [];

function start(d, c, b) {
	if (status == 0 && d == 0) {
		qm.dispose();
		return
	}(d == 1) ? status++ : status--;
	selectionLog[status] = b;
	var a = -1;
	if (status <= a++) {
		qm.dispose()
	} else {
		if (status == a++) {
			qm.sendSimpleS("这鞍子又小了，水下世界的去找坎特做个更大的把。", 0, 1013000)

		} else {
			if (status === a++) {
				qm.forceStartQuest();
				qm.forceCompleteQuest();
				qm.dispose()
			}
		}
	}
}

function end(mode, type, selection) {
	qm.forceCompleteQuest();
	qm.dispose();
}