/* Dawnveil
 Cab
 Regular Cab in Victoria
 Made by Daenerys
 */
var status = 0;
var maps = Array(910000000, 104000000, 310000000, 100000000, 101000000, 102000000, 103000000, 105000000, 120000100, 130000000, 140000000, 310000000, 211000000, 211060000, 800000000, 800040000, 801000000, 270000100, 260000200, 250000000, 106020000, 251000000, 540000000, 541000000, 550000000, 221000000, 701000000, 500000000, 120030000, 600000000, 200000000, 230000000, 220000000, 261000000, 222000000, 740000000, 103040000, 240000000, 951000000, 741000000);
var maps2 = Array(
        Array(103020400, "推薦11~20等"),
        Array(101030300, "推薦21~25等"),
        Array(101040200, "推薦26~30等"),
        Array(200010130, "推薦31~35等"),
        Array(103040000, "推薦36~45等"),
        Array(220010600, "推薦46~55等"),
        Array(105010100, "推薦56~60等"),
        Array(220020600, "推薦61~65等"),
        Array(221020701, "推薦66~70等"),
        Array(220050000, "推薦71~75等"),
        Array(260020500, "推薦76~80等"),
        Array(250020100, "推薦81~85等"),
        Array(261020100, "推薦86~90等"),
        Array(261010101, "推薦91~95等"),
        Array(251010500, "推薦96~100等"),
        Array(251010401, "推薦101~105等"),
        Array(220060100, "推薦106~110等"),
        Array(220060200, "推薦111~120等"),
        Array(211060100, "推薦121~160等"),
        Array(271030101, "推薦161~200等")
        );
var meso1 = 100000;
var maps3 = Array(
        Array(230040420, "海怒斯"),
        Array(551030100, "熊獅王"),
        Array(211042300, "殘暴炎魔"),
        Array(211042301, "混沌殘暴炎魔"),
        Array(211070000, "凡雷恩"),
        Array(240050400, "暗黑龙王"),
        Array(220080000, "拉图斯"),
        Array(272020110, "阿卡伊農"),
        Array(262030000, "希拉"),
        Array(270050000, "皮卡丘"),
        Array(105200000, "四王"),
        Array(271040000, "西格諾斯"),
        Array(401060000, "梅格奈斯")
        );
var meso2 = 500000;
var sel;
var show;
var sCost;
var selectedMap = -1;
var selectedMap2 = -1;
var selectedMap3 = -1;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (status == 1 && mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1) {
        status++;
    } else {
        status--;
    }


    if (status == 0) {
        cm.sendSimple("#e您好~！我是萬能傳送员。想要往其他村莊安全又快速的移动吗？如果是这樣，为了優先考量满足顧客，亲切的送你到想要到达的地方。#k  \r\n\r\n #b#L0#村莊區（免費）#l#k \r\n\r\n #b#L1#练功區(免費)#l#k \r\n\r\n #b#L2#打王區(免費)#l#k");
    } else if (status == 1) {
        sel = selection;
        switch (sel) {
            case 0:
            {
                var selStr = "#e请選擇目的地.#b";
                for (var i = 0; i < maps.length; i++) {
                    if (maps[i] != cm.getMapId()) {
                        selStr += "\r\n#L" + i + "##m" + maps[i] + "##l";
                    }
                }
                cm.sendSimple(selStr);
                break;
            }
            case 1:
            {
                var selStr2 = "#e请選擇目的地.#b";
                for (var i = 0; i < maps2.length; i++) {
                    if (maps2[i][0] != cm.getMapId()) {
                        selStr2 += "\r\n#L" + i + "##m" + maps2[i][0] + "#(" + maps2[i][1] + ")#l";
                    }
                }
                cm.sendSimple(selStr2);
                break;
            }

            case 2:
            {
                var selStr3 = "#e请選擇目的地.#b";
                for (var i = 0; i < maps3.length; i++) {
                    if (maps3[i][0] != cm.getMapId()) {
                        selStr3 += "\r\n#L" + i + "#" + maps3[i][1] + " - #m" + maps3[i][0] + "##l";
                    }
                }
                cm.sendSimple(selStr3);
                break;
            }
            default :
            {
                cm.dispose();
                return;
            }
        }


    } else if (status == 2) {

        switch (sel) {
            case 0:
            {
                cm.sendYesNo("这地方應該沒有什么可以參觀的了。确定要移动到#b#m" + maps[selection] + "##k吗?");
                selectedMap = selection;
                break;
            }
            case 1:
            {
                cm.sendYesNo("这地方應該沒有什么可以參觀的了。确定要移动到#b#m" + maps2[selection][0] + "##k吗?");
                selectedMap2 = selection;
                break;
            }

            case 2:
            {
                cm.sendYesNo("这地方應該沒有什么可以參觀的了。确定要移动到#b#m" + maps3[selection][0] + "##k吗?");
                selectedMap3 = selection;
                break;
            }
            default :
            {
                cm.dispose();
                return;
            }
        }

    } else if (status == 3) {
        cm.dispose();
        if (!cm.canwncs()) {
            cm.sendOk("当前狀態，暂时无法傳送。");
            cm.dispose();
            return;
        }


        switch (sel) {
            case 0:
            {
                if (maps[selectedMap] == 951000000) {
                    cm.dispose();
                    cm.openNpc(9071003);

                    return;
                }
                cm.warp(maps[selectedMap]);
                cm.dispose();
                break;
            }
            case 1:
            {
                //if (cm.getMeso() < meso1) {
                //    cm.sendOk("你的金币不足。");
                //    cm.dispose();
                //    return;
                //}
                //cm.gainMeso(-meso1);
                cm.warp(maps2[selectedMap2][0]);
                cm.dispose();
                break;
            }

            case 2:
            {
                //if (cm.getMeso() < meso2) {
                //    cm.sendOk("你的金币不足。");
                //   cm.dispose();
                //    return;
                // }
                // cm.gainMeso(-meso2);
                cm.warp(maps3[selectedMap3][0]);
                cm.dispose();
                break;
            }
            default :
            {
                cm.dispose();
                return;
            }
        }
    } else {
        cm.dispose();
        return;
    }
}