var status = -1;
var selectionLog = [];

function start() {
    var status = cm.getQuestStatus(20710);
    
    if (status == 1) {
        cm.sendNext("好像有没有可疑的事物。.");
    } 
    cm.dispose();
}

function action(d, c, b) {
    
        cm.dispose();
return ;
   
};