
/**
 * 当前：破攻兑换
 * 其中：
 * 第一个参数 表示兑换的物品id
 * 第二个参数 表示需要消耗的物品数量
 * 第三个参数 表示增加的破攻值
 */
var selArr = [];
var selType;
var nxnum = 0;
var goldnum = 0;
var selitem;
var itemArr = [ //道具
    [4002003, 1, 1000],
    [4002003, 2, 2000],
    [4002003, 3, 3000],
    [4002003, 4, 4000],
    [4002003, 5, 5000]
]

var status = 0;
var rn = "\r\n\r\n"; // 换行

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (mode == 0) {
            cm.sendOk("下次再来哦.");
            cm.dispose();
            return;
        }
        if (mode == 1) {
            status++;
        } else {
            status--;
        }
        if (status === 0) {

            var add = "请选择你想兑换多少破攻值:" + rn;
            for (var i = 0; i < itemArr.length; i++) {
                add += "#L" + i + "##r使用#v" + itemArr[i][0] + "##z" + itemArr[i][0] + "# X " + itemArr[i][1] + "  兑换#k#b" + itemArr[i][2] + "点破攻值#l" + rn;
            }
            cm.sendSimple(add);
        } else if (status === 1) {
            selType = selection;
            var txt = "道具不足，攒够了物品再来吧！";
            if (cm.haveItem(itemArr[selection][0], itemArr[selection][1])) {
                txt = "确定兑换" + itemArr[selection][2] + "点破攻值吗？";
                cm.sendSimple(txt);
            } else {
                cm.sendSimple(txt);
                cm.dispose();
                return;
            }
        } else if (status == 2) {
            var txt2 = "兑换成功！";
            if (cm.haveItem(itemArr[selType][0], itemArr[selType][1])) {
                cm.gainItem(itemArr[selType][0], -itemArr[selType][1]);
                cm.getPlayer().addAccountExtraDamage(cm.getPlayer(), itemArr[selType][2]);
                cm.getPlayer().dropMessage(5, "破攻值提升：" + itemArr[selType][2] + "点！");
                cm.sendOk(txt2);
            }
            cm.dispose();
            return;

        }
    }
}