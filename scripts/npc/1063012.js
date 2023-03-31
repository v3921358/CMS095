var status = -1;

function action(mode, type, selection) {
    if (cm.isQuestActive(2236)) {
        switch (cm.getMapId()) {
            case 105010100:
                if (cm.getBossLog("quest_2236_105010100") < 1) {
                    cm.gainItem(4032263, -1);
                    if (!cm.haveItem(4032263)) {
                        cm.sendOk("快回去交任务吧！！");
                    } else {
                        cm.sendOk("已经贴好道符！");
                    }

                    cm.setBossLog("quest_2236_105010100")
                } else {
                    cm.sendOk("好像没什么事情可做。");
                }
                break;
            case 105020000:
                if (cm.getBossLog("quest_2236_105020000") < 1) {
                    cm.gainItem(4032263, -1);
                    if (!cm.haveItem(4032263)) {
                        cm.sendOk("快回去交任务吧！！");
                    } else {
                        cm.sendOk("已经贴好道符！");
                    }

                    cm.setBossLog("quest_2236_105020000")
                } else {
                    cm.sendOk("好像没什么事情可做。");
                }
                break;
            case 105020100:
                if (cm.getBossLog("quest_2236_105020100") < 1) {
                    cm.gainItem(4032263, -1);
                    if (!cm.haveItem(4032263)) {
                        cm.sendOk("快回去交任务吧！！");
                    } else {
                        cm.sendOk("已经贴好道符！");
                    }

                    cm.setBossLog("quest_2236_105020100")
                } else {
                    cm.sendOk("好像没什么事情可做。");
                }
                break;
            case 105020300:
                if (cm.getBossLog("quest_2236_105020300") < 1) {
                    cm.gainItem(4032263, -1);
                    if (!cm.haveItem(4032263)) {
                        cm.sendOk("快回去交任务吧！！");
                    } else {
                        cm.sendOk("已经贴好道符！");
                    }

                    cm.setBossLog("quest_2236_105020300")
                } else {
                    cm.sendOk("好像没什么事情可做。");
                }
                break;

        }
        if (!cm.haveItem(4032263)) {
            cm.sendOk("快回去交任务吧！！");
        }
    } else {
        cm.sendOk("好像没什么事情可做。");
    }
    cm.dispose();
}