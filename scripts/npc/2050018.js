var status = -1;

function action(mode, type, selection) {
    if (cm.isQuestActive(3421)) {
        if(cm.getBossLogD("3421-5")<1){
            cm.gainItem(4031117, 1);
            cm.setBossLog("3421-5")
        }
        cm.playerMessage("已获取陨石碎片");
    }
    cm.dispose();
}