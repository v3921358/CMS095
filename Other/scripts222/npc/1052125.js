﻿
var status = 0;
var check = 0;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    
    if (mode === -1) {
        cm.dispose();
    } else {
        if (status >= 0 && mode === 0) {

            // cm.sendOk("感谢你的光临！");
            cm.dispose();
            return;
        }

        if (mode === 1)
            status++;
        else
            status--;
        if (status === 0) {
            var selStr = "请让开。这里禁止随意出入。"
            cm.sendSimple(selStr);
        }else if (status === 1) {
            cm.dispose();
            return;
        }
    }
}


