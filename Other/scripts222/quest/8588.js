var status = -1;

function start(mode, type, selection) {
        qm.sendNext("謝謝你完成此任務\r\n如果还会看此任務请重登。");
        qm.forceCompleteQuest();
        qm.dispose();
}

function end(mode, type, selection) {
        qm.forceCompleteQuest();
        qm.dispose();
}
