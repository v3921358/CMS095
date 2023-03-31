function start() {
	cm.sendSimple("#b\r\n#L0#训练室A#l\r\n#L1#训练室B#l\r\n#L2#训练室C#l\r\n#L3#训练室D#l\r\n#L4#第四训练室#l#k");
}

function action(mode, type, selection) {
	if (mode == 1) { //or 931000400 + selection..?
		switch (selection) {
			case 0:
				cm.warp(310010100, 0);
				break;
			case 1:
				cm.warp(310010200, 0);
				break;
			case 2:
				cm.warp(310010300, 0);
				break;
			case 3:
				cm.warp(310010400, 0);
				break;
			case 4:
				if (cm.isQuestActive(23118)) {
					cm.spawnMobOnMap(9300413, 1, -60, 20, 931000400);
				}
				cm.warp(931000400, 0);


				break;
		}
	}
	cm.dispose();
}