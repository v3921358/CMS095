

var status = -1;
var sel;
var mod;
var itemlist = [100000000, 10000, [
    [5220010, 4],
    [5451001, 10],
    [2049124, 10],
    [2049124, 10],
    [2049006, 10],
    [2340000, 10],
    [4310019, 10],
    [4001465, 40],
    [4031559, 60],
    [2000005, 40],
    [2000004, 40],
    [5062000, 40],
    [4002002, 600],
    [5062001, 40]
]];
var rmb= 100 ; //紫金
var rmb1= 100 ; //余额
function start() {
     var txt ="\t\t\t\t#r#e < 会员福利 > #k#n\r\n\r\n\r\n\会员每天可领取以下物品，并且每天获得100紫金100余额 \n\r\n\r\n";

    txt += "#b#v5200002#" + itemlist[0] + "金币#b\r\n";
    txt += "#b#v5200000#" + itemlist[1] + " 点券#b\r\n";
    for (var key in itemlist[2]) {
        txt += "#b#v" + itemlist[2][key][0] + "##z" + itemlist[2][key][0] + "# X" + itemlist[2][key][1] + "个#l\r\n";
    }
    txt += "#b#L0#領取会员福利#l#k#k";
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
                cm.sendOk("你沒有会员。");
                cm.dispose();
                return;
            }
            if (!cm.canHoldSlots(5)) {
                cm.sendOk("请确认背包空位是否够5个格子。");
                cm.dispose();
                return;
            }
            if (cm.getBossRankCount("会员福利") > 0) {
                cm.sendOk("你今天已经领取过了");
                cm.dispose();
                return;
            }
            cm.setBossRankCount("会员福利");
            cm.gainMeso(itemlist[0]);
            cm.gainNX(itemlist[1]);
            cm.setBossRankCount9("紫金点",rmb );
			 cm.setBossRankCount9("赞助余额",rmb1 );
            for (var key in itemlist[2]) {
                cm.gainItem(itemlist[2][key][0], itemlist[2][key][1] );
            }
            cm.sendOk("领取成功");
            cm.dispose();
            return;
        }
    }
}
