var text;
var status = 0;
var bossid = "等级礼包";
var giftLevel = Array(70, 100, 120, 150, 200);
var giftContent = [
    [[2450022,3],[4002003,3],[2000005,200],[4032226,50]],
    [[4002003,10],[1662005,1],[5062000,20],[1672006,1]],
    [[4032171,100],[4002003,20],[5062000,20],[5062001,10],[2049116,3]],
    [[1662001,1],[1672000,1],[4002003,50],[5062001,20],[1113074,1],[2049116,10]],
    [[4032169,15],[1662003,1],[1672005,1],[4002003,100],[5062001,50]]

];
var giftId = -1;
var giftToken = Array();
var gifts = null;

function start() {
    status = -1;
    action(1, 0, 0)
}

function action(mode, type, selection) {
    if (status == 0 && mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++
    } else {
        status--
    }
    if (status == 0) {
        var l = "";
        l += "   #b冒险家，你的当前等级为 #r" + cm.getPlayer().getLevel() + "\r\n\r\n";
        for (var v in giftLevel) {
            var a = "";
            if (cm.getPlayer().getLevel() >= giftLevel[v]) {
                if (cm.getBossRankCount(bossid + giftLevel[v]+ cm.getPlayer().getName()) >= 0) {
                    a = "#r[已完成]#r";
                } else {
                    a = "#g[可领取]#b";
                    
                }
            } else {
                a = "#k[未完成]#k"
            }
            l += "   #L" + (parseInt(v) + 1) + "# " + a + "等级福利礼包 #r	" + giftLevel[v] + "级#l\r\n"
        }
        cm.sendSimple(l)
    } else {
        if (status == 1) {
                giftId = selection;

                var l = "#是否领取 #r" + giftLevel[giftId - 1] + "#k 级档次礼包？\r\n";
                gifts = giftContent[giftId-1];
                if(cm.getPlayer().getLevel() < giftLevel[giftId - 1]){
                    cm.sendOk("#r等级不足，领取失败。#k");
                    cm.dispose();
                    return;
                }
                for (var r = 0; r < gifts.length; r++) {
                    var k = gifts[r][0];
                    var f = gifts[r][1];
                    l += " #v" + k + "##z" + k + "#[" + f + "个]\r\n";
                    
                }
                cm.sendYesNo(l);
        } else {
            if (status == 2) {
                if (giftId != -1 && gifts != null) {
                    if (cm.getInventory(1).isFull(6)&&cm.getInventory(2).isFull(6)&&cm.getInventory(3).isFull(6)&&cm.getInventory(4).isFull(6)&&cm.getInventory(5).isFull(6)) {
                        cm.sendOk("#您的背包空间不足，请保证每个栏位至少6格的空间，以避免领取失败。");
                        cm.dispose();
                        return
                    }
                    if (cm.getBossRankCount(bossid + giftLevel[giftId - 1]+ cm.getPlayer().getName()) >= 0) {
                        status = -1;
                        cm.sendNext("#您已经领过了该礼包或者等级未达到要求，无法领取。");
                        cm.dispose()
                        return;
                    } else {
                        cm.setBossRankCount(bossid + giftLevel[giftId - 1]+ cm.getPlayer().getName(),1);
                        for (var v in gifts) {
                            var k = gifts[v][0];
                            var f = gifts[v][1];
                            cm.gainItem(k, f)
                        }
                        cm.worldMessage(5,"『等级奖励』玩家[" + cm.getPlayer().getName() + "] 领取了 " + giftLevel[giftId - 1] + "级 等级奖励。");
                        cm.dispose();
                        cm.sendOk("#恭喜您，领取成功！快打开包裹看看吧！")
                        
                    }
                } else {
                    cm.sendNext( "#领取错误！请联系管理员！");
                    cm.dispose()
                }
            }
        }
    }
}
