var status = -1;

function start(mode, type, selection) {
    qm.sendOk("去找#b神木村长#k带回龙族的苔癬萃取液。");
    qm.forceStartQuest();
    qm.dispose();
}

function end(mode, type, selection) {
    status++;
    if (status == 0) {
        if (qm.haveItem(4032531, 1)) {
            qm.sendNext("偉大的！请等我把这些原料混合在一起。");
        } else {
            qm.sendOk("去找#b神木村长#k带回龙族的苔癬萃取液。");
            qm.forceStartQuest();
            qm.dispose();
        }
    } else {
        qm.teachSkill(qm.getSkillByJob(qm.getPlayer(), 1026, qm.getPlayer().getJob()), 1, 0); // Maker
        qm.gainExp(11000);
        qm.removeAll(4032531);
        qm.sendOk("我们走吧！你已经學会了翺翔的技能，並能够飛行。");
        qm.forceCompleteQuest();
        qm.dispose();
    }
}
	