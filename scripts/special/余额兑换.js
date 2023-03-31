
/**
 * 当前：余额兑换物品
 * 其中：
 * 第一个参数 表示需要消耗的余额
 * 第二个参数 表示兑换的物品id
 * 第三个参数 表示兑换到的物品数量
 */
var selArr = [];
var selType;
var 赞助余额;
var nxnum = 0;
var goldnum = 0;
var selitem;
var itemArr = [ //道具
    [10, 1003181, 1],
    [20, 1003180, 1],
    [30, 1003179, 1],
    [40, 1003178, 1],
    [50, 1003177, 1]
]
var equipArr = [ //装备
    [10, 1003181, 1],
    [20, 1003180, 1],
    [30, 1003179, 1],
    [40, 1003178, 1],
    [50, 1003177, 1]
]
var cashArr = [ //时装
    [10, 1003181, 1],
    [20, 1003180, 1],
    [30, 1003179, 1],
    [40, 1003178, 1],
    [50, 1003177, 1]
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
            赞助余额 = cm.getBossRank("赞助余额", 2) == -1 ? 0 : cm.getBossRank("赞助余额", 2);
            var add = "请选择你想使用余额兑换的物品, 当前余额:#b" + 赞助余额 + "#l\r\n#L10##b余额充值#l     #L11##d联系岛主   " + rn;
            add += "#L0##r使用余额兑换#k#b点券#l" + rn;
            add += "#L1##r使用余额兑换#k#b金币#l" + rn;
            add += "#L2##r使用余额兑换#k#b道具#l" + rn;
            add += "#L3##r使用余额兑换#k#b装备#l" + rn;
            add += "#L4##r使用余额兑换#k#b时装#l" + rn;
            cm.sendSimple(add);
        } else if (status === 1) {
            selType = selection;
            var txt = "请选择下列你要兑换的内容：" + rn;
            switch (selection) {
                case 0:
                    txt = "当前余额兑换比例为：1余额=1000点券" + rn;
                    txt += "请输入你要兑换多少#b点券#l：\r\n";
                    break;
                case 1:
                    txt = "当前余额兑换比例为：1余额=1000#b万#l金币" + rn;
                    txt += "请输入你要兑换多少万#b金币#l：\r\n";
                    break;
                case 2:
                    selArr = itemArr;
                    break;
                case 3:
                    selArr = equipArr;
                    break;
                case 4:
                    selArr = cashArr;
                    break;
                case 10:
                    cm.dispose();
                    cm.openWeb("http://123123/forum.php?mod=viewthread&tid=28&extra=");
                    cm.sendOk("#e#r[提示]：\r\n已为您打开充值链接~\r\n");
                    break;
                case 11:
                    cm.dispose();
                    cm.openWeb("http://123123/forum.php?mod=viewthread&tid=28&extra=");
                    cm.sendOk("#e#r[提示]：\r\n已为您群主联系方式链接~\r\n");
                    break;
                default:
                    cm.dispose();
            }
            if (selection < 2) {
                cm.sendGetText(txt);
            } else {
                for (var i = 0; i < selArr.length; i++) {
                    txt += "\t#L" + i + "##d使用【" + selArr[i][0] + "】余额兑换#v" + selArr[i][1] + "##z" + selArr[i][1] + "# X " + selArr[i][2] + "#l\r\n";
                }
                cm.sendSimple(txt);
            }
        } else if (status == 2) {
            var txt2 = "";
            selitem = selection;
            if (selType == 0) {
                nxnum = cm.getText();
                if (nxnum % 1000 == 0 && (赞助余额 * 1000) >= nxnum) {
                    txt2 = "确定兑换" + nxnum + "点券吗？";
                } else {
                    cm.sendOk("余额不足，或点券不是整倍数！");
                    cm.dispose();
                    return;
                }

            } else if (selType == 1) {
                goldnum = cm.getText();
                if (goldnum % 1000 == 0 && (赞助余额 * 1000) >= goldnum) {
                    txt2 = "确定兑换" + goldnum + "万金币吗？";
                } else {
                    cm.sendOk("余额不足，或金币不是整倍数！");
                    cm.dispose();
                    return;
                }
            } else {
                if (赞助余额 >= selArr[selection][0]) {
                    txt2 = "#d确定使用【" + selArr[selection][0] + "】余额兑换#v" + selArr[selection][1] + "##z" + selArr[selection][1] + "# X " + selArr[selection][2] + " 个吗？#l";
                } else {
                    cm.sendOk("余额不足！");
                    cm.dispose();
                    return;
                }
            }
            cm.sendYesNo(txt2);
        } else if (status == 3) {
            if (selType == 0) {
                cm.setBossRankCount("赞助余额", -(nxnum / 1000));
                cm.gainNX(nxnum);
            } else if (selType == 0) {
                cm.setBossRankCount("赞助余额", -(goldnum / 1000));
                cm.gainMeso(goldnum);
            } else {
                cm.setBossRankCount("赞助余额", -selArr[selitem][0]);
                cm.gainItem(selArr[selitem][1], selArr[selitem][2]);
            }
            cm.sendOk("兑换成功！");
            cm.dispose();
        }
    }
}