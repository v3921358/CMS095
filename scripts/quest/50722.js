var status = -1;

function start(mode, type, selection) {
    qm.sendOk("去找#b神木村村长#k待会古老的龙翼鱗片。");
    qm.forceStartQuest();
    qm.dispose();
}

function end(mode, type, selection) {
    status++;
    if (status == 0) {
        if (qm.haveItem(4032969, 1)) {
            qm.sendNext("请等我把这些道具融合。");
        } else {
            qm.sendOk("去找#b神木村村长#k待会古老的龙翼鱗片。");
            qm.forceStartQuest();
            qm.dispose();
        }
    } else {
        qm.teachSkill(qm.getSkillByJob(qm.getPlayer(), 1142, qm.getPlayer().getJob()), 1, 0); // Maker
        qm.removeAll(4032969);
        qm.sendOk("你已经學会了飛行技能.");
        qm.forceCompleteQuest();
        qm.dispose();
    }
}
	