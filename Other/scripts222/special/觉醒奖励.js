var 图标2 = "#fUI/StatusBar.img/BtClaim/mouseOver/0#";

var status = -1;
var sel;
var totalWins;
var check;
var accid;
var itemList = [
    [
        [5062001, 100], 
        [2049116, 100],
        [2049006, 10],
        [4002003, 10],
        [1132213, 1],
        [1152122, 1]
    ],
    [
        [5062001, 200], 
        [2049116, 200],
        [2049006, 20],
        [4002003, 20],
        [1132214, 1],
        [1152123, 1]
    ],
    [
        [5062001, 300], 
        [2049116, 300],
        [2049006, 30],
        [4002003, 30],
        [1132215, 1],
        [1152124, 1]
    ],
    [
        [5062001, 500], 
        [2049116, 500],
        [2049006, 50],
        [4002003, 50],
        [1142742, 1]
    ]
]

function start() {
    totalWins = cm.getPlayer().getTotalWins();
    accid = cm.getPlayer().getClient().getAccID();
    var text = "你好，觉醒达到一定次数可以找我领取觉醒奖励哦。\r\n\r\n";
    text += totalWins >= 1 && cm.getBossRankCount(accid + "_觉醒奖励_1") != 1 ? "#b#L1#领取初次觉醒奖励#k" : "#r#L1#查看初次觉醒奖励#k";
    text += totalWins >= 1 || cm.getBossRankCount(accid + "_觉醒奖励_1") > 0 ? "#b[已达成]#l#k\r\n" : "#r[未达成]#l#k\r\n";
    text += totalWins >= 5 && cm.getBossRankCount(accid + "_觉醒奖励_5") != 1 ? "#b#L2#领取五次觉醒奖励#k" : "#r#L2#查看五次觉醒奖励#k";
    text += totalWins >= 5 || cm.getBossRankCount(accid + "_觉醒奖励_5") > 0 ? "#b[已达成]#l#k\r\n" : "#r[未达成]#l#k\r\n";
    text += totalWins >= 10 && cm.getBossRankCount(accid + "_觉醒奖励_10") != 1 ? "#b#L3#领取十次觉醒奖励#k" : "#r#L3#查看十次觉醒奖励#k";
    text += totalWins >= 10 || cm.getBossRankCount(accid + "_觉醒奖励_10") > 0 ? "#b[已达成]#l#k\r\n" : "#r[未达成]#l#k\r\n";
    text += totalWins >= 17 && cm.getBossRankCount(accid + "_觉醒奖励_17") != 1 ? "#b#L4#领取十七次觉醒奖励#k" : "#r#L4#查看十七次觉醒奖励#k";
    text += totalWins >= 17 || cm.getBossRankCount(accid + "_觉醒奖励_17") > 0 ? "#b[已达成]#l#k\r\n" : "#r[未达成]#l#k\r\n";
    // text += "#L0#查看觉醒奖励内容#l\r\n";
    // text += "#L1#领取初次觉醒奖励#l\r\n";
    // text += "#L2#领取5次觉醒奖励#l\r\n";
    // text += "#L3#领取10次觉醒奖励#l\r\n";
    // text += "#L4#领取17次觉醒奖励#l\r\n\r\n";
    cm.sendSimple(text);
}

function action(mode, type, selection) {
    if (mode == 0) {
        cm.dispose();
        return;
    } else {
        status++;
    }
    if (status == 0) {
        sel = selection;
        switch (sel) {
            case 1:
                if(totalWins >= 1 && cm.getBossRankCount(accid + "_觉醒奖励_1") != 1){
                    if (cm.getInventory(1).isFull(6)&&cm.getInventory(2).isFull(6)&&cm.getInventory(3).isFull(6)&&cm.getInventory(4).isFull(6)&&cm.getInventory(5).isFull(6)) {
                        cm.sendOk("#您的背包空间不足，请保证每个栏位至少6格的空间，以避免领取失败。");
                        cm.dispose();
                        return
                    }
                    for(var i=0;i<itemList[0].length;i++){
                        cm.gainItem(itemList[0][i][0],itemList[0][i][1]);

                    }
                    cm.setBossRankCount(accid + "_觉醒奖励_1",1);
                    cm.sendOk("领取成功！");
                }else{
                    var txt="达成初次觉醒奖励如下\r\n\r\n";
                    for(var i=0;i<itemList[0].length;i++){
                        txt+="#v"+itemList[0][i][0]+"##z"+itemList[0][i][0]+"# X "+itemList[0][i][1]+"\r\n";
                    }
                    cm.sendOk(txt);
                }
                break;
            case 2:
                if(totalWins >= 5 && cm.getBossRankCount(accid + "_觉醒奖励_5") != 1){
                    if (cm.getInventory(1).isFull(6)&&cm.getInventory(2).isFull(6)&&cm.getInventory(3).isFull(6)&&cm.getInventory(4).isFull(6)&&cm.getInventory(5).isFull(6)) {
                        cm.sendOk("#您的背包空间不足，请保证每个栏位至少6格的空间，以避免领取失败。");
                        cm.dispose();
                        return
                    }
                    for(var i=0;i<itemList[1].length;i++){
                        cm.gainItem(itemList[1][i][0],itemList[1][i][1]);

                    }
                    cm.setBossRankCount(accid + "_觉醒奖励_5",1);
                    cm.sendOk("领取成功！");
                }else{
                    var txt="达成五次觉醒奖励如下\r\n\r\n";
                    for(var i=0;i<itemList[1].length;i++){
                        txt+="#v"+itemList[1][i][0]+"##z"+itemList[1][i][0]+"# X "+itemList[1][i][1]+"\r\n";
                    }
                    cm.sendOk(txt);
                }
                break;
            case 3:
                if(totalWins >= 10 && cm.getBossRankCount(accid + "_觉醒奖励_10") != 1){
                    if (cm.getInventory(1).isFull(6)&&cm.getInventory(2).isFull(6)&&cm.getInventory(3).isFull(6)&&cm.getInventory(4).isFull(6)&&cm.getInventory(5).isFull(6)) {
                        cm.sendOk("#您的背包空间不足，请保证每个栏位至少6格的空间，以避免领取失败。");
                        cm.dispose();
                        return
                    }
                    for(var i=0;i<itemList[2].length;i++){
                        cm.gainItem(itemList[2][i][0],itemList[2][i][1]);

                    }
                    cm.setBossRankCount(accid + "_觉醒奖励_10",1);
                    cm.sendOk("领取成功！");
                }else{
                    var txt="达成十次觉醒奖励如下\r\n\r\n";
                    for(var i=0;i<itemList[2].length;i++){
                        txt+="#v"+itemList[2][i][0]+"##z"+itemList[2][i][0]+"# X "+itemList[2][i][1]+"\r\n";
                    }
                    cm.sendOk(txt);
                }
                break
            case 4:
                if(totalWins >= 17 && cm.getBossRankCount(accid + "_觉醒奖励_17") != 1){
                    if (cm.getInventory(1).isFull(6)&&cm.getInventory(2).isFull(6)&&cm.getInventory(3).isFull(6)&&cm.getInventory(4).isFull(6)&&cm.getInventory(5).isFull(6)) {
                        cm.sendOk("#您的背包空间不足，请保证每个栏位至少6格的空间，以避免领取失败。");
                        cm.dispose();
                        return
                    }
                    for(var i=0;i<itemList[3].length;i++){
                        cm.gainItem(itemList[3][i][0],itemList[3][i][1]);

                    }
                    cm.setBossRankCount(accid + "_觉醒奖励_17",1);
                    cm.sendOk("恭喜你已经达成所有目标，#r感受登顶的孤独吧！#k领取成功！");
                }else{
                    var txt="达成十七次觉醒奖励如下\r\n\r\n";
                    for(var i=0;i<itemList[3].length;i++){
                        txt+="#v"+itemList[3][i][0]+"##z"+itemList[3][i][0]+"# X "+itemList[3][i][1]+"\r\n";
                    }
                    cm.sendOk(txt);
                }
                break
            default:
        }
        cm.dispose();
    }
}