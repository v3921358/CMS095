/* RED Zero
 Crane
 Made by Daenerys
 */

var status = -1;
var select = -1;
var togo;

function start() {
    if (cm.getMapId() == 251000000) {
        togo = "靈药幻境";
        cm.sendYesNo("你好啊？冒險家，旅途愉快吗？沒有像我一樣的翅膀，时不时有些不方便？最近在帮一些沒有翅膀的人直接飛往 #b桃園仙境#k。怎樣？有兴趣吗？只需要#b500 金币#k就可以。");
    } else if (cm.getMapId() == 250000100) {
        togo = "桃園仙境";
        cm.sendSimple("你好啊？冒險家，旅途愉快吗？沒有像我一樣的翅膀，时不时有些不方便？最近在帮一些沒有翅膀的人直接飛往別的地區，你需要帮忙吗？那么選擇你要去的地方吧。#b\r\n#L0#天空之城(1500 金币)#l\r\n#L1#靈药幻境(500 金币)#l");
    } else if (cm.getMapId() == 200000141) {
        togo = "天空之城";
        cm.sendSimple("你好啊？冒險家，旅途愉快吗？沒有像我一樣的翅膀，时不时有些不方便？最近在帮一些沒有翅膀的人直接飛往別的地區。怎樣？有兴趣吗？那么選擇想去的地方。#b\r\n#L0#桃園仙境(1500 金币)#l");
    } else {
        cm.sendNext("該地图不支持傳送，向管理员反饋。");
        cm.dispose();
    }
}
function action(mode, type, selection) {
    if (togo == "靈药幻境") {
        baiCaoTang(mode, type, selection);
    } else if (togo == "桃園仙境") {
        muLung(mode, type, selection);
    } else if (togo == "天空之城") {
        tianKong(mode, type, selection);
    }
}

function baiCaoTang(mode, type, selection) {
    if (mode == 1)
        status++;
    else
        status--;
    if (status == -2) {
        cm.sendNext("改变想法隨时跟我搭话吧。");
        cm.dispose();
    } else if (status == 0) {
        if (cm.getMeso() < 500) {
            cm.sendNext("你有足够的金币吗？");
        } else {
            cm.gainMeso(-500);
            cm.warp(250000100, 0);
        }
        cm.dispose();
    } else {
        cm.dispose();
    }
}

function muLung(mode, type, selection) {
    if (mode == 1)
        status++;
    else
        status--;
    if (status == -1) {
        if (select == 1) {
            cm.sendNext("想好了再跟我搭话吧。");
        }
        cm.dispose();
    } else if (status == 0) {
        if (select == -1)
            select = selection;
        if (select == 0) {
            if (cm.getMeso() < 1500) {
                cm.sendNext("你有足够的金币吗？");
            } else {
                cm.gainMeso(-1500);
                cm.warp(200000100, 0);
                //cm.warp(200090310, 1);
            }
            cm.dispose();
        } else if (select == 1) {
            cm.sendYesNo("要向 #b靈药幻境#移动吗？只要中途不做出危險的动作消下去，很快就能到达。價錢是#b500 金币#k。");
        }
    } else if (status == 1) {
        if (select == 1) {
            if (cm.getMeso() < 500) {
                cm.sendNext("看來你沒有足够的金币。");
            } else {
                cm.gainMeso(-500);
                cm.warp(251000000, 0);
            }
        }
        cm.dispose();
    } else {
        cm.dispose();
    }
}

function tianKong(mode, type, selection) {
    if (mode == 1)
        status++;
    else
        status--;
    if (status == 0) {
        if (cm.getMeso() < 1500) {
            cm.sendNext("你有足够的金币吗？");
        } else {
            cm.gainMeso(-1500);
            cm.warp(250000100, 1);
        }
        cm.dispose();
    } else {
        cm.dispose();
    }
}