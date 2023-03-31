﻿/*
 Bell - KC/NLC Subway Station(103020000/600010001), Waiting Room(600010002/600010004)
 */

var section;
var msg = new Array("墮落城市到新葉城", "新葉城到墮落城市");
var ticket = new Array(4031711, 4031713);
var cost = 5000;
var returnMap = new Array(103020000, 600010001);

function start() {
    status = -1;
    sw = cm.getEventManager("Subway");
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 0 && status == 0) {
        cm.dispose();
    } else {
        status++;
        if (mode == 0) {
            if (section == 2) {
                cm.sendNext("好吧，请等待!");
            } else {
                cm.sendOk("你有一些经濟的負擔而无法搭船对吧?");
            }
            cm.dispose();
            return;
        }
        if (status == 0) {
            switch (cm.getMapId()) {
                case 103020000:
                    section = 0;
                    break;
                case 600010001:
                    section = 1;
                    break;
                case 600010004:
                    section = 2;
                    break;
                case 600010002:
                    section = 3;
                    break;
                default:
                    cm.sendNext("错誤~");
                    cm.dispose();
                    break;
            }
            if (section < 2) {
                cm.sendSimple("您好。你想买地鐵票??\r\n#L0##b" + msg[section] + "#l");
            } else {
                cm.sendYesNo("你想要回去原本地鐵站??");
            }
        } else if (status == 1) {
            if (section < 2) {
                cm.sendYesNo("这张 " + msg[section] + " 的票 他将花費你 #b" + cost + " 金币#k. 请问你确定要购买 #b#t" + ticket[section] + "##k?");
            } else {
                section -= 2;
                cm.warp(returnMap[section]);
                cm.dispose();
            }
        } else if (status == 2) {
            if (cm.getMeso() < cost || !cm.canHold(ticket[section])) {
                cm.sendNext("你确定你有 #b" + cost + " 金币#k? 如果是这樣的话，我勸您检查其他栏，看看是否满了!.");
            } else {
                cm.gainItem(ticket[section], 1);
                cm.gainMeso(-cost);
            }
            cm.dispose();
        }
    }
}
