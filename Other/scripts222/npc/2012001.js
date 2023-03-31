﻿/*  
 *  
 *  
 *  NPC名称：
 *  功能：
 *  
 */
var status = -1;

function action(mode, type, selection) {
    var em = cm.getEventManager("Boats");
    if (mode == 1) {
        status++;
        if (cm.getBossLogD("Boats") == 2) {
            cm.sendOk("很抱歉每天只能打兩次..");
            cm.dispose();
            return;
        }
    } else {
        if (status == 0) {
            cm.sendNext("看来你还有事情要办吧？");
            cm.dispose();
        }
        status--;
    }
    if (status == 0) {
        if (em.getProperty("entry").equals("true")) {
            cm.sendYesNo("坐上船之后，需要飞很久才能到达目的地。如果你在这里有急事要办的话，请先把事情办完。怎么样？你要上船吗？");
        } else {
            cm.sendOk("船已经在准备出发。对不起，请乘坐下一班船。运行时间表可以通过售票员确认。");
            cm.dispose();
        }
    } else if (status == 1) {
        cm.setBossLog("Boats");
        cm.warp(200000112, 0);
        cm.dispose();
    }
}
