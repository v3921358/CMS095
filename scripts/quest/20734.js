/*
	NPC Name: 		Cygnus
	Description: 		Quest - Encounter with the Young Queen
*/

var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
	qm.dispose();
	return;
    }
    if (status == 0) {
	qm.sendNext("你好，。目前，枫樹世界正处于極大的危險之中。我们需要一支更大的軍队來保护这个地方免受黑魔法师的傷害。为了建立一支更强大的軍队，我决定与探險家首領結盟。我们用我们共同的力量創造了终極探險家。");
    } else if (status == 1) {
	qm.sendYesNo("终極探險家出生50等，出生时有非常特殊的技能。你願意作为终極探險家重生吗?");
    } else if (status == 2) {
	if (!qm.getClient().canMakeCharacter(qm.getPlayer().getWorld())) {
	    qm.sendOk("沒有足够的角色栏位，不能創建角色。.");
	} else {
	    qm.sendUltimateExplorer();
	}
	qm.dispose();
    }
}

function end(mode, type, selection) {
}