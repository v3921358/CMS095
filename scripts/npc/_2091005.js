/*
 Map : Mu Lung Training Center
 Npc : So Gong
 Desc : Training Center Start
 */
var status = -1;
var sel;
var mapid;

function start() {
    mapid = cm.getMapId();

    if (mapid == 925020001) {
        cm.sendSimple("我们主人是武陵道场的师傅。你想要挑战我们师傅？不要说我沒提醒你他是最强的。 \r #b#L0#我要单人挑战#l \n\r #L1#我要组队进入#l \n\r #L2#我要兌換道具#l \n\r #L3#我要重置我的點數#l \n\r #L5#什么是武陵道场?#l");
    } else if (isRestingSpot(mapid)) {
        cm.sendSimple("我很驚訝，您已经安全的达到这層了，我可以向你保證，它沒有这么容易过关的，你想要堅持下去？#b \n\r #L0#是，我想繼續。#l \n\r #L1# 我想离开#l \n\r "/*+"#L2# 我想要保存这一次的紀錄下一次用。#l"*/);
    } else {
        cm.sendYesNo("你想要离开了？？");
    }
}

function action(mode, type, selection) {
    if (mapid == 925020001) {
        if (mode == 1) {
            status++;
        } else {
            cm.dispose();
            return;
        }
        if (status == 0) {
            sel = selection;

            if (sel == 5) {
                cm.sendNext("#b[武陵道场]#k 自己#e#rGoogle#k!");
                cm.dispose();
            } else if (sel == 3) {
                cm.sendYesNo("你是真的要重置！？ \r\n別怪我沒警告你。");
            } else if (sel == 2) {
                cm.sendSimple("现在你的道场點數有 #b" + cm.getDojoPoints() + "#k. 我们的主人喜欢有才華的人，所以如果你有了足够的道场點數，你就可以根据你的道场點數依序換取道具...\n\r #L0##i1132000:# #t1132000#(200)#l \n\r #L1##i1132001:# #t1132001#(1800)#l \n\r #L2##i1132002:# #t1132002#(4000)#l \n\r #L3##i1132003:# #t1132003#(9200)#l \n\r #L4##i1132004:# #t1132004#(17000)#l \n\r #L100##i5062000:# #t5062000#x1(100)#l \n\r #L200##i5062000:# #t5062000#x10(1000)#l");
            } else if (sel == 1) {
                if (cm.getParty() != null) {
                    if (cm.isLeader()) {
                        var party = cm.getParty().getMembers();
                        var mapId = cm.getMapId();
                        var channel = cm.getChannelNumber();
                        var it = party.iterator();
                        while (it.hasNext()) {
                            var cPlayer = it.next();
                            if (cPlayer.getChannel() != channel) {
                                cm.sendOk("你队伍有队友不在当前頻道。");
                                cm.dispose();
                                return;
                            }
                            if (cPlayer.getMapid() != mapId) {
                                cm.sendOk("你队伍有队友不在当前地图。");
                                cm.dispose();
                                return;
                            }
                        }
                        cm.sendOk("走喽。");
                    } else {
                        cm.sendOk("请找你的队长來找我说话。");
                    }
                } else {
                    cm.sendOk("你好像沒有组队。");
                    cm.dispose();
                    return;
                }
            } else if (sel == 0) {
                if (cm.getParty() != null) {
                    cm.sendOk("你离开你的组队。.");
                    cm.dispose();
                    return;
                }
                var record = cm.getQuestRecord(150000);
                var data = record.getCustomData();

                if (data != null) {
                    cm.warp(get_restinFieldID(parseInt(data)), 0);
                    record.setCustomData(null);
                } else {
                    cm.start_DojoAgent(true, false);
                }
                cm.dispose();
                // cm.sendYesNo("The last time you took the challenge yourself, you were able to reach Floor #18. I can take you straight to that floor, if you want. Are you interested?");
            }
        } else if (status == 1) {
            if (sel == 3) {
                cm.setDojoRecord(true);
                cm.sendOk("我已经帮您归零，好运。");
            } else if (sel == 2) {
                var record = cm.getDojoRecord();
                var required = 0;

                switch (record) {
                    case 0:
                        required = 200;
                        break;
                    case 1:
                        required = 1800;
                        break;
                    case 2:
                        required = 4000;
                        break;
                    case 3:
                        required = 9200;
                        break;
                    case 4:
                        required = 17000;
                        break;
                }

                if (selection == 100) {
                    if (cm.canHoldByType(5, 1)) {
                        cm.setDojoPoints(-100);
                        cm.gainItem(5062000, 1);
                        cm.sendOk("領取陳功。");
                        cm.dispose();
                        return;
                    } else {
                        cm.sendOk("请确认背包是否已经满了。");
                        cm.dispose();
                        return;
                    }
                }
                if (selection == 200) {
                    if (cm.canHoldByType(5, 10)) {
                        cm.setDojoPoints(-1000);
                        cm.gainItem(5062000, 10);
                        cm.sendOk("領取陳功。");
                        cm.dispose();
                        return;
                    } else {
                        cm.sendOk("请确认背包是否已经满了。");
                        cm.dispose();
                        return;
                    }
                }

                if (record == selection && cm.getDojoPoints() >= required) {
                    var item = 1132000 + record;
                    if (cm.canHold(item)) {
                        cm.gainItem(item, 1);
                        cm.setDojoRecord(false);
                        cm.sendOk("恭喜兌換成功！！");
                    } else {
                        cm.sendOk("请确认一下你的背包是否满了.");
                    }
                } else if (record != selection) {
                    cm.sendOk("请依照順序兌換腰带！謝謝");
                } else {
                    cm.sendOk("你好像沒有足够的道场點數可以換....");
                }
                cm.dispose();
            } else if (sel == 1) {
                cm.start_DojoAgent(true, true);
                cm.dispose();
            }
        }
    } else if (isRestingSpot(mapid)) {
        if (mode == 1) {
            status++;
        } else {
            cm.dispose();
            return;
        }

        if (status == 0) {
            sel = selection;

            if (sel == 0) {
                cm.dojoAgent_NextMap(true, true);
                //cm.getQuestRecord(150000).setCustomData(null);
                cm.dispose();
            } else if (sel == 1) {
                cm.askAcceptDecline("你真的想要离开这里？");
            } else if (sel == 2) {
                if (cm.getParty() == null) {
                    var stage = get_stageId(cm.getMapId());

                    cm.getQuestRecord(150000).setCustomData(stage);
                    cm.sendOk("我剛剛保存你这次的紀錄，下次当你返回我就直接送你到这里。");
                    cm.dispose();
                } else {
                    cm.sendOk("嘿，小家伙你不能保存..因为这是组队挑战！");
                    cm.dispose();
                }
            }
        } else if (status == 1) {
            if (sel == 1) {
                if (cm.isLeader()) {
                    cm.warpParty(925020002);
                } else {
                    cm.warp(925020002);
                }
            }
            cm.dispose();
        }
    } else {
        if (mode == 1) {
            if (cm.isLeader()) {
                cm.warpParty(925020002);
            } else {
                cm.warp(925020002);
            }
        }
        cm.dispose();
    }
}

function get_restinFieldID(id) {
    var idd = 925020002;
    switch (id) {
        case 1:
            idd = 925020600;
            break;
        case 2:
            idd = 925021200;
            break;
        case 3:
            idd = 925021800;
            break;
        case 4:
            idd = 925022400;
            break;
        case 5:
            idd = 925023000;
            break;
        case 6:
            idd = 925023600;
            break;
    }
    for (var i = 0; i < 10; i++) {
        var canenterr = true;
        for (var x = 1; x < 39; x++) {
            var map = cm.getMap(925020000 + 100 * x + i);
            if (map.getCharactersSize() > 0) {
                canenterr = false;
                break;
            }
        }
        if (canenterr) {
            idd += i;
            break;
        }
    }
    return idd;
}

function get_stageId(mapid) {
    if (mapid >= 925020600 && mapid <= 925020614) {
        return 1;
    } else if (mapid >= 925021200 && mapid <= 925021214) {
        return 2;
    } else if (mapid >= 925021800 && mapid <= 925021814) {
        return 3;
    } else if (mapid >= 925022400 && mapid <= 925022414) {
        return 4;
    } else if (mapid >= 925023000 && mapid <= 925023014) {
        return 5;
    } else if (mapid >= 925023600 && mapid <= 925023614) {
        return 6;
    }
    return 0;
}

function isRestingSpot(id) {
    return (get_stageId(id) > 0);
}
