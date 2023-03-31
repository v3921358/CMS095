var status = -1;
var selectionLog = [];

function start() {
    action(1, 0, 0)
}

function action(d, c, b) {
    if (status == 0 && d == 0) {
        cm.dispose();
        return
    }(d == 1) ? status++ : status--;
    selectionLog[status] = b;
    var a = -1;
    if (status <= a++) {
        cm.dispose()
    } else {
        if (status === a++) {
            if (cm.isQuestActive(3929) && cm.haveItem(4031580)) {
                cm.gainItem(4031580, -1);
                if(!cm.haveItem(4031580)){
                    cm.forceCompleteQuest(3929);
                    cm.sendNext('留下了粮食,任务已完成。');
                }else{
                    cm.sendNext('留下了粮食,请去下一家。');
                }
				
            }else{
                cm.sendNext('这里好像没什么事情!');
            }
            cm.dispose();
        }
    }
};