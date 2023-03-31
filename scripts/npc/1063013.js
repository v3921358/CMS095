var status = -1;

function action(mode, type, selection) {
    if (cm.isQuestActive(2236)) {
        switch (cm.getMapId()) {
            case 105020200:
                if (cm.getBossLog("quest_2236_105020200") < 1) {
                    cm.gainItem(4032263, -1);
                    if (!cm.haveItem(4032263)) {
                        cm.sendOk("快回去交任务吧！！");
                    } else {
                        cm.sendOk("已经贴好道符！");
                    }

                    cm.setBossLog("quest_2236_105020200")
                } else {
                    cm.sendOk("好像没什么事情可做。");
                }
                break;
            case 105020400:
                if (cm.getBossLog("quest_2236_105020400") < 1) {
                    cm.gainItem(4032263, -1);
                    if (!cm.haveItem(4032263)) {
                        cm.sendOk("快回去交任务吧！！");
                    } else {
                        cm.sendOk("已经贴好道符！");
                    }

                    cm.setBossLog("quest_2236_105020400")
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