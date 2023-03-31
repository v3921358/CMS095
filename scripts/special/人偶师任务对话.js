
var status = -1;
var selectionLog = [];

function start() {
    action(1, 0, 0)
}

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        if (status == 0) {
            cm.sendOk("你是傻瓜吗？");
            cm.dispose();
        }
        status--;
    }
    if (status == 0) {
        cm.sendGetText("要是想进去，就说出暗号！\n\n #b（听到了奇怪的声音。说出通过调查知道的暗号吧。“弗朗西斯是天才人偶师！”）#k")
    } else {
        if (status == 1) {
            if (cm.getText() == "弗朗西斯是天才人偶师！") {
                
                if (cm.isQuestActive(20730)) {
                    // cm.dispose();
                    cm.spawnMobOnMap(9300285, 1, 517, 256, 910050300);
                    cm.warp(910050300, 0);
                } else {
                    // cm.dispose();
                    cm.spawnMobOnMap(9300344, 1, 517, 256, 910050300);
                    cm.warp(910050300, 0);
                    
                    
                }
                cm.dispose();

            } else {
                cm.sendOk("奇怪的声音嘲笑道。#b你是傻瓜吗？密码错了！连空格和感叹号都不能错！");
                cm.dispose();
            }
        }
    }

}
