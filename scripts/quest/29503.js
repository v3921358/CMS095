/* 
 *  Dallier - King Medal
 *  Lith Habor = 104000000
 *  Sleepywood = 105040300
 */

var status = -1;

function start(mode, type, selection) {
    if (mode == 0) {
        if (status == 0) {
            qm.sendNext("等你想要的时候再告訴我。");
            qm.dispose();
            return;
        } else if (status == 2) {
            status--;
        } else {
            qm.dispose();
            return;
        }
    } else {
        status++;
    }

    if (status == 0) {
        qm.askAcceptDecline("#v1142030# #e#b#t1142030##k\n\r\n\r - 时间限制: 1 小时\n\r - 維多利亞港捐獻王勳章....#n你是否想要體验看看当第一名的感觉?");
    } else if (status == 1) {
        qm.sendNext("目前排名 \n\r\n\r1. #bMintLovePep#k : ???,???,??? 金币\n\r2. #bKelviinXD#k : 68,000,000 金币\n\r3. #bxBabyRence#k : 49,999,999 金币\n\r4. #bXxTrIStaArxx#k : 29,999,999 金币\n\r5. #bxBabyRence#k : 14,000,000 金币\n\r\n\r要知道我不能透露现在捐獻的人數有谁 \n\r 要記得这些紀錄每个月的一号都会初始化。");
    } else if (status == 2) {
        qm.sendNextPrev("目前捐獻排行尚未开放。");
        qm.dispose();
    }
}

function end(mode, type, selection) {}

/*function getMedalType(ids) {
    var thestring = "#b";
    var extra;
    for (x = 0; x < ids.length; x++) {
	extra = "#L" + x + "##t" + ids[x] + "##l\r\n";
	thestring += extra;
    }
    thestring += "#k";
    return thestring;
}*/