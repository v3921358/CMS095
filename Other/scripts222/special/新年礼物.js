

var status = -1;
var sel;
var mod;
var itemlist = [200000000, 50000, [
    [5220010, 20],
    [5451001, 50],
    [2049124, 20],
    [2049124, 20],
    [2049006, 20],
    [2340000, 20],
    [4310019, 100],
    [4001465, 20],
    [4031559, 200],
    [2000005, 200],
    [2000004, 200],
    [5062000, 50],
    [4002002, 1000],
	[5570000, 300],
	[4031344, 300],
	[2430227, 300],
	[2049130, 300],
	[2049131, 200],
	[2049132, 100],
	[2003509, 200],
	[2003515, 200],
	[2003517, 100],
	[2003519, 200],
	[2070006, 100],
	[2070007, 100],
	[2070019, 100],
	[1492239, 1],
	[1492239, 1],
	[1492239, 1],
	[1492239, 1],
	[1492239, 1],	
	[4001126, 10000],
	[2450023, 30],
	[2450019, 50],
	[2450021, 100],
    [5062001, 50]
]];
var rmb= 400 ;
function start() {
    var txt = "热烈庆祝2022年，过去一年,我们同努力,我们共欢笑,每一次成功都蕴藏着我们辛勤劳动。新一年即将来到,我们不能停滞不前,一味只是骄傲。愿大家与时俱进,拼搏不懈,共创新辉煌!2022,你好!\r\n另外送大家#v2430210# ×200元\r\n\r\n";
    txt += "#b#v5200002#" + itemlist[0] + "金币#b\r\n";
    txt += "#b#v5200000#" + itemlist[1] + " 点券#b\r\n";
    for (var key in itemlist[2]) {
        txt += "#b#v" + itemlist[2][key][0] + "##z" + itemlist[2][key][0] + "# X" + itemlist[2][key][1] + "个#l\r\n";
    }
    txt += "#b#L0#領取新年礼物#l#k#k";
    cm.sendSimple(txt);
}

function action(mode, type, selection) {
    if (mode == 0) {
        cm.dispose();
        return;
    } else {
        status++;
    }
    if (status == 0) {
        sel = selection;
        if (sel == 0) {
            
            if (!cm.canHoldSlots(5)) {
                cm.sendOk("请确认背包空位是否够5个格子。");
                cm.dispose();
                return;
            }
            if (cm.getPlayer().getAcLogD("每日紅利") >= 1) {
                cm.sendOk("已经领取过了");
                cm.dispose();
                return;
            }
            cm.getPlayer().setAcLog("每日紅利");
            cm.gainMeso(itemlist[0]);
            cm.gainNX(itemlist[1]);
            cm.setBossRankCount("赞助余额", rmb);
            for (var key in itemlist[2]) {
                cm.gainItem(itemlist[2][key][0], itemlist[2][key][1] );
            }
			cm.worldMessage(6, "" + cm.getPlayer().getName() + "祝大家新年好！！");
            cm.sendOk("领取成功，红包已转换为400余额，再次祝大家新年好。");
            cm.dispose();
            return;
        }
    }
}
