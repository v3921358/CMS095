var status = -1;
var selectionLog = [];

function start() {
    var status = cm.getQuestStatus(20710);
    
    if (status == 0) {
        cm.sendNext("好像有没有可疑的事物。.");
    } else if (status == 1) {
if(!cm.haveItem(4032136,1)){
        cm.gainItem(4032136,1)
}
        cm.sendNext("你发现了蓝水灵玩偶");
    } else if (status == 2) {
        cm.sendNext("蓝水灵玩偶已经被发现了.");
    }
    cm.dispose();
}

function action(d, c, b) {
    
        cm.dispose();
return ;
   
};