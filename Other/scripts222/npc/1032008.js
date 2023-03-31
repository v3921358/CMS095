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
            cm.sendNext("看来你还有其他事情要办吧？");
            cm.dispose();
        }
        status--;
    }
    
    if (status == 0) {
        if (em.getProperty('entry').equals('true')) {
            cm.sendYesNo('你现在要乘船去天空之城吗？\r\n怎么样？你要上船吗？', 1032008);
        } else {
            cm.sendOk('船已经在准备出发。对不起，请乘坐下一班船。运行时间表可以通过售票员确认。');
            cm.dispose();
        }
    } else {
        if (status == 1) {
            cm.setBossLog("Boats");
            cm.warp(104020111, 0);
            cm.dispose();
        }
    }
}