/* @Author SharpAceX
*/

function start() {
if (cm.getPlayer().getMap().getId() == 803001100) {
        cm.sendOk("令人难以置信的力量和力量，任何人都可以实现。 但是什么让战士特别是他们的铁意志。 无论如何，一个真正的战士推动直到胜利得到保证。 因此，战士室是一个残酷的道路，房间本身对抗你，以及超强的怪物。 使用你的技能来摆脱影响，打败怪物到达战士雕像，并要求主剑。 祝你好运!");
cm.dispose();
} else if (cm.getPlayer().getMap().getId() == 803000600) {
cm.sendOk("一个传说中的英雄家族，德维斯森斯（Storm）是原始创始人。 这个家庭是独一无二的，因为每一个儿子或女儿都承担着祖先的全面战斗技巧。 这种能力已被证明是非常有用的; 因为它允许几乎无限的策略，即兴和战术来打败所有的敌人。 一个真正的家庭为世代.");
cm.dispose();
} else if (cm.getPlayer().getMapId() == 803001110) {
	if (cm.getPlayer().getMap().getAllMonster().size() == 0) {
		if (!cm.haveItem(4001259,1)) {
			cm.gainItem(4001259,1);
		}
		cm.warp(803001100,0);
	} else {
		cm.sendOk("消除所有的绯红监护人.");
	}
	cm.dispose();
}
}
