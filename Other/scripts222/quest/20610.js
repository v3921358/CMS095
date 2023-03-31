/*
 * Cygnus Skill -
 */

var status = -1;

function start(mode, type, selection) {
    status++;

    if (status == 0) {
	qm.askAcceptDecline("你有沒有熟练你的技能了呢？我相信你已经掌握了所有技能使用的方法，接下來我将再傳授一招#b最终技能#k給你。");
    } else if (status == 1) {
	if (mode == 0) {
	    qm.sendOk("好吧，你在做什么，现在不会使你看起來像有人说的謙虛。你只要看看由自满这樣做，这是從來沒有一个好東西。");
	} else {
	    qm.forceStartQuest();
	}
	qm.dispose();
    }
}

function end(mode, type, selection) {
    qm.dispose();
}