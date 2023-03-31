
/**
 Chief Tatamo - Leafre(240000000)
 **/

var section;
var temp;
var cost;
var count;
var menu = "";
var itemID = new Array(4000226, 4000229, 4000236, 4000237, 4000261, 4000231, 4000238, 4000239, 4000241, 4000242, 4000234, 4000232, 4000233, 4000235, 4000243);
var status = -1;

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        if (status == 0) {
            cm.dispose();
        }
        if (status > 2) {
            if (section == 0) {
                cm.sendOk("请慎重考慮。一旦你做出了决定，让我知道。");
            } else {
                cm.sendOk("想想吧，然后让我知道你的决定。");
            }
            cm.safeDispose();
        }
        status--;
    }
    if (status == 0) {
        cm.sendSimple("你找我有事吗？\r\n#L0##b魔法种子#k#l\r\n#L1##b为了神木村的行动#k#l\r\n#L2##b做四轉材料#k#l\r\n#L3##b龙族的苔癬萃取液#k#l");
    } else if (status == 1) {
        section = selection;
        if (section == 0) {
            cm.sendSimple("需要我帮助你？？\r\n#L0##b我想跟你买一些 #t4031346#.#k#l");
        } else if (section == 1) {
            cm.sendNext("更好的建設村落是村长的職責。所以需要更多更好的道具。你能为了村落捐獻出在神木村附近收集到的道具吗？");
        } else if (section == 2) {
            cm.sendNext("您需要为您的四轉做准备吗?? 我需要#t4031348#。");
        } else if (section == 3) {
            cm.sendNext("#t4032531#。需要100萬金币");
        }
    } else if (status == 2) {
        if (section == 0) {
            cm.sendGetNumber("#b#t4031346##k 需要买多少个？？", 1, 1, 99);
        } else if (section == 2) {
            if (cm.canHold()) {
                if (cm.haveItem(4031348)) {
                    status = 3;
                    cm.sendNext("你已经有了#t4031348#，那么现在我要用我的神奇魔法把#t4031348#变成四轉所需的道具。");
                } else {
                    cm.sendNext("请到玩具城66F找NPC购买#t4031348#。");
                    cm.safeDispose();
                }
            } else {
                cm.sendNext("请检查你的裝备栏。");
                cm.safeDispose();
            }
        } else if (section == 3) {
            if (cm.getPlayer().getMeso() >= 1000000) {
                if (cm.canHold()) {
                    cm.gainMeso(-1000000);
                    cm.gainItem(4032531, 1);
                    cm.sendOk("感謝购买!");
                    cm.dispose();
                    return;
                } else {
                    cm.sendNext("请检查你的裝备栏。");
                    cm.dispose();
                    return;
                }
            } else {
                cm.sendOk("#d你金币不够哦");
                cm.dispose();
                return;
            }

        } else {
            for (var i = 0; i < itemID.length; i++) {
                menu += "\r\n#L" + i + "##b#t" + itemID[i] + "##k#l";
            }
            cm.sendNext("你想捐獻出那种道具呢？" + menu);
            //cm.safeDispose();
        }
    } else if (status == 3) {
        if (section == 0) {
            if (selection == 0) {
                cm.sendOk("我不能賣你0个。");
                cm.safeDispose();
            } else {
                temp = selection;
                cost = temp * 30000;
                cm.sendYesNo("你要买 #b" + temp + " #t4031346##k 它将花費你 #b" + cost + " 金币#k. 你确定要购买？？?");
            }
        } else {
            temp = selection;
            if (cm.haveItem(itemID[temp])) {
                //cm.sendGetNumber("How many #b#t" + itemID[temp] + "#k's would you like to donate?\r\n#b< Owned : #c" + itemID[temp] + "# >#k", 0, 0, "#c" + itemID[temp] + "#");
                cm.sendGetNumber("你要捐多少个 #b#t" + itemID[temp] + "#k'我会給你很好的酬勞的！", 1, 1, 999);
            } else {
                cm.sendNext("我不认为你有这道具。");
                cm.safeDispose();
            }
        }
    } else if (status == 4) {
        if (section == 0) {
            if (cm.getMeso() < cost || !cm.canHold(4031346)) {
                cm.sendOk("请确认是否有足够的金币和道具栏位。");
            } else {
                cm.sendOk("再会~");
                cm.gainItem(4031346, temp);
                cm.gainMeso(-cost);
            }
            cm.safeDispose();
        } else if (section == 2) {
            if (!cm.canHold(4031860, 2) || !cm.canHold(4031861, 2)) {
                cm.sendNext("The space doesnt enough .");
                cm.dispose();
                return;
            }
            if (cm.haveItem(4031348)) { //2nd check need item

                if (cm.getJob() == 111 || cm.getJob() == 121 || cm.getJob() == 131) {
                    cm.gainItem(4031348, -1);
                    cm.gainItem(4031343, 1);
                    cm.gainItem(4031344, 1);
                    cm.sendOk("恭喜你已经获得#t4031343# x1 #t4031344# x1");
                } else if (cm.getJob() == 211 || cm.getJob() == 221 || cm.getJob() == 231) {
                    cm.gainItem(4031348, -1);
                    cm.gainItem(4031511, 1);
                    cm.gainItem(4031512, 1);
                    cm.sendOk("恭喜你已经获得#t4031511# x1 #t4031512# x1");
                } else if (cm.getJob() == 311 || cm.getJob() == 321) {
                    cm.gainItem(4031348, -1);
                    cm.gainItem(4031514, 1);
                    cm.gainItem(4031515, 1);
                    cm.sendOk("恭喜你已经获得#t4031514# x1 #t4031515# x1");
                } else if (cm.getJob() == 411 || cm.getJob() == 421 || cm.getJob() == 433) {
                    cm.gainItem(4031348, -1);
                    cm.gainItem(4031517, 1);
                    cm.gainItem(4031518, 1);
                    cm.sendOk("恭喜你已经获得#t4031517# x1 #t4031518# x1");
                } else if (cm.getJob() == 511 || cm.getJob() == 521 || cm.getJob() == 531) {
                    cm.gainItem(4031348, -1);
                    cm.gainItem(4031517, 1);
                    cm.gainItem(4031518, 1);
                    cm.sendOk("恭喜你已经获得#t4031517# x1 #t4031518# x1");
                } else {
                    cm.sendOk("您目前无法兌換。");
                    cm.dispose();
                return;
                }
            } else {
                cm.sendOk("您貌似沒有#t4031348# 0.0");
            }
            cm.safeDispose();
        } else {
            count = selection;
            cm.sendYesNo("你确定你想贊助 #b" + count + " #t" + itemID[temp] + "##k?");
        }
    } else if (status == 5) {
        if (count == 0 || !cm.haveItem(itemID[temp], count)) {
            cm.sendNext("请确认贊助项目是否足够。");
        } else {
            cm.gainItem(itemID[temp], -count);
            cm.sendNext("感謝你贊助。");
        }
        cm.safeDispose();
    }
}