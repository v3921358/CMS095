var status = -1;

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        if (status == 0) {
            cm.dispose();
        }
        status--;
    }
    if (status == 0) {
        //cm.sendOk("活动时间未到！");
        //cm.dispose();
        //return;


        cm.sendSimple((cm.getPlayer().getMapId() != 960000000 ? "\r\n#L5#我想去竞技场#l" : "\r\n#L5#我想离开#l"));
    } else if (status == 1) {
        if (cm.getPlayer().getMapId() != 960000000) {
            cm.saveReturnLocation("PVP");
            cm.warp(960000000);
            cm.dispose();
            return;
        }
        if (cm.getPlayer().getMapId() == 960000000) {
            var returnMap = cm.getSavedLocation("PVP");
            if (returnMap < 0) {
                returnMap = 230000000; // to fix people who entered the fm trough an unconventional way
            }
            cm.clearSavedLocation("PVP");
            cm.warp(returnMap, "unityPortal2");
            cm.dispose();
            return;
        }
    }
}