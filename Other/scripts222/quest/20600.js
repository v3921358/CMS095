/*
 * Cygnus Skill - Training Never ends
 */

var status = -1;

function start(mode, type, selection) {
    status++;

    if (status == 0) {
	qm.askAcceptDecline("#h0#. 你有沒有在訓练懈怠，因为达到100級？我们都知道你是多么强大，但訓练是不完整的。一起來看看这些騎士指揮官。他们訓练了一天一夜，准备为自己的黑精靈可能遇到的问題。");
    } else {
	if (mode == 1) {
	    qm.forceStartQuest();
	}
	qm.dispose();
    }
}

function end(mode, type, selection) {
    qm.dispose();
}