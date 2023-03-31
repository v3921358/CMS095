﻿/* 
 * Spiegelmann - Monster Carnival
 */

        var status = -1;
var rank = "D";
var exp = 0;
var select = 0;

function start() {
    if (cm.getCarnivalParty() != null) {
        status = 99;
    }
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        status--;
    }
    if (mode == -1) {
        cm.dispose();
        return;
    }
    if (status == 0) {
        switch (cm.getMapId()) {
            case 980000010:
            case 980000103:
            case 980000104:
            case 980000203:
            case 980000204:
            case 980000303:
            case 980000304:
            case 980000403:
            case 980000404:
            case 980000503:
            case 980000504:
            case 980000603:
            case 980000604:
                cm.warp(980000000, 4);
                cm.dispose();
                break;
            default:
                cm.sendSimple("请來參加怪物擂台﹗\r\n#b#L0#我要前往怪物擂台#l");
        }
    } else if (status == 1) {
        switch (selection) {
            case 0:
            {
                var level = cm.getPlayerStat("LVL");
                if (level >= 30 && level <= 50) {
                    cm.saveLocation("MULUNG_TC");
                    cm.warp(980000000, "st00");
                } else if (level >= 51 && level <= 120) {
                    cm.saveLocation("MULUNG_TC");
                    cm.warp(980030000, "st00");
                    cm.dispose();
                } else {
                    cm.sendOk("你的等級是:" + level + " 目前沒有任何擂台可以參加。");
                    cm.dispose();
                }
                cm.dispose();
            }
            default:
                {
                    cm.dispose();
                    break;
                }
                break;
        }
    } else if (status == 100) {
        var carnivalparty = cm.getCarnivalParty();
        if (carnivalparty.getTotalCP() >= 501) {
            rank = "A";
            exp = 30000;
        } else if (carnivalparty.getTotalCP() >= 251) {
            rank = "B";
            exp = 22500;
        } else if (carnivalparty.getTotalCP() >= 101) {
            rank = "C";
            exp = 16500;
        } else if (carnivalparty.getTotalCP() >= 0) {
            rank = "D";
            exp = 7500;
        }
        cm.getPlayer().endPartyQuest(1301);
        if (carnivalparty.isWinner()) {
            cm.sendNext("恭喜你贏了 太神啦\r\n#b怪物擂台賽排行 : " + rank);
        } else {
            cm.sendNext("虽然輸了也不要氣餒Q_Q\r\n#b怪物擂台賽排行 : " + rank);
        }
    } else if (status == 101) {
        var carnivalparty = cm.getCarnivalParty();
        var los = parseInt(cm.getPlayer().getOneInfo(1301, "lose"));
        var vic = parseInt(cm.getPlayer().getOneInfo(1301, "vic"));
        if (carnivalparty.isWinner()) {
            vic++;
            cm.getPlayer().updateOneInfo(1301, "vic", "" + vic);
            carnivalparty.removeMember(cm.getChar());
            cm.gainExpR(exp);
        } else {
            los++;
            cm.getPlayer().updateOneInfo(1301, "lose", "" + los);
            carnivalparty.removeMember(cm.getChar());
            cm.gainExpR(exp / 2);
        }
        cm.getPlayer().updateOneInfo(1301, "VR", "" + (cm.getDoubleCeil((vic * 100) / los)));
        cm.warp(980000000);
        cm.dispose();
    }

}
