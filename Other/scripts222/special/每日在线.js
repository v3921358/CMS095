/*
 ZEVMS冒险岛(079)游戏服务端
 脚本：星缘，个人在线奖励，自行修改
 */
var 箭头 = "#fUI/Basic/BtHide3/mouseOver/0#";
var 心 = "#fUI/GuildMark.img/Mark/Etc/00009001/14#";
var datalist = [
    [30, 100, [
        [2000005, 50],
        [5062000, 30],
        [4001126, 50]
    ]],
    [60, 1000, [
        [2000005, 50],
        [5062001, 30],
        [4001126, 50]
    ]],
    [120, 1000, [
        [2000005, 50],
        [2049006, 5],
        [4001126, 50]
    ]],
    [180, 1000, [
        [2000005, 50],
        [2049300, 5],
        [4001126, 50]
    ]],
    [240, 2000, [
        [2000005, 50],
        [2049124, 5],
        [4310019, 5],
        [4002003, 5],
        [4002002, 10],
        [4001465, 20],
        [4001126, 50]
    ]]
]

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (status == 0 && mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    } else {
        status--;
    }
    if (status == 0) {
        var selStr = "	  Hi~#b#h ##k 你今日在线:#b #n#e" + cm.getTodayOnlineTime() + " #n#k分钟#k，你是不是想要找我领取奖品呢。\r\n";
        for (var key in datalist) {
            selStr += "在线 #r" + datalist[key][0] + " #k分钟可领取  ";
            for (var ii in datalist[key][2]) {
                selStr += "#v" + datalist[key][2][ii][0] + " ##z" + datalist[key][2][ii][0] + "# × " + datalist[key][2][ii][1]+ " ";
            }
            selStr += "抵用券 × " + datalist[key][1] + "\r\n";
            if (cm.getTodayOnlineTime() >= datalist[key][0] && cm.getBossLogD("在线奖励" + key + "时间") == 0) {
                selStr += "#L" + key + "#" + 箭头 + "#b领取#r" + datalist[key][0] + "#k#b分钟奖励#l#k\r\n";
            }
        }






        cm.sendOk(selStr);
    } else if (status == 1) {
        if (cm.getBossLogD("在线奖励" + selection + "时间") == 0) {

            for(var key in datalist[selection][2]){
                if(cm.getTodayOnlineTime() >= datalist[selection][0]){
                    cm.gainItem(datalist[selection][2][key][0],datalist[selection][2][key][1]);
                }
            }
            cm.gainNX(datalist[selection][1])
            cm.setBossLog("在线奖励" + selection + "时间");
            cm.worldMessage(6, "[在线奖励] : " + cm.getChar().getName() + " 领取了 " + datalist[selection][0] + " 分钟在线奖励");
            cm.sendOk("今天领取成功！");
            cm.dispose();
        }else{
            cm.sendOk("今天领取过了！");
            cm.dispose();
            return;
        }

    }
}
