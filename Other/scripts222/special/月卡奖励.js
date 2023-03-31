

var status = -1;
var sel;
var mod;
var itemlist = [50000000, 5000, [
    [5220010, 2],
    [5451001, 5],
    [2049124, 5],
    [2049124, 5],
    [2049006, 5],
    [2340000, 5],
    [4310019, 5],
    [4001465, 20],
    [4031559, 30],
    [2000005, 20],
    [2000004, 20],
    [5062000, 20],
    [4002002, 300],
    [5062001, 20]
]];
var rmb= 5 ;
function start() {
    var txt ="\t\t\t\t#r#e < 月卡奖励 > #k#n\r\n\r\n\r\n\月卡用戶每天可领取以下物品，并且每天获得5余额 \n\r\n\r\n";
    txt += "#b#v5200002#" + itemlist[0] + "金币#b\r\n";
    txt += "#b#v5200000#" + itemlist[1] + " 点券#b\r\n";
    for (var key in itemlist[2]) {
        txt += "#b#v" + itemlist[2][key][0] + "##z" + itemlist[2][key][0] + "# X" + itemlist[2][key][1] + "个#l\r\n";
    }
    txt += "#b#L0#領取月卡奖励#l#k#k";
    cm.sendSimple(txt);
}

function action(mode, type, selection) {
    if (mode == 0) {
        cm.dispose();
		cm.openNpc(9900004);
        return;
    } else {
        status++;
    }
    if (status == 0) {
        sel = selection;
        if (sel == 0) {
            if (!cm.haveItem(5010143, 1)) {
                cm.sendOk("你沒有月卡。");
                cm.dispose();
                return;
            }
            if (!cm.canHoldSlots(5)) {
                cm.sendOk("请确认背包空位是否够5个格子。");
                cm.dispose();
                return;
            }
            if (cm.getBossRankCount("月卡奖励") > 0) {
                cm.sendOk("你今天已经领取过了");
                cm.dispose();
                return;
            }
            cm.setBossRankCount("月卡奖励");
            cm.gainMeso(itemlist[0]);
            cm.gainNX(itemlist[1]);
            cm.setBossRankCount("赞助余额", rmb);
            for (var key in itemlist[2]) {
                cm.gainItem(itemlist[2][key][0], itemlist[2][key][1] );
            }
            cm.sendOk("领取成功");
            cm.dispose();
            return;
        }
    }
}
