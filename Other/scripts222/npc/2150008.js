var status = -1;

function start() {
    cm.sendSimple("你想离开埃德爾斯坦，到其他地區去吗？这里的船开往維多利亞和天空之城。費用是800金币。你想去哪里？\r\n#L0#維多利亞#l\r\n#L1#天空之城#l\r\n#L2#来送水箱#l");
}

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        status--;
    }

    if (status == 0) {
        if (cm.getMeso() < 800) {
            cm.sendNext("嗯……你确认你自己有#b800#k金币吗？请你打开背包确认一下。不够的话，就先去把錢湊齊吧。");
        } else {

            if (selection == 0) {
                cm.gainMeso(-800);
                cm.warp(104020130, 0);
            }
            else if (selection == 1) {
                cm.gainMeso(-800);
                cm.warp(200000100, 0);
            }
            else if (selection == 2 && cm.getQuestStatus(23121) == 1) {
                cm.forceCompleteQuest(23121);
                cm.gainItem(4032744, -1);
            }else{
                cm.sendOk("你好像不需要完成送水箱任务");
            }
        }
        cm.dispose();
    } else {
        cm.dispose();
    }
}