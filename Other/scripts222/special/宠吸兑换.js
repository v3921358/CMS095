load("scripts/utils/base.js")

var love = "#fEffect/CharacterEff/1022223/4/0#"; // 爱心
var redArrow = "#fUI/UIWindow/Quest/icon6/7#"; // 箭头
var rect = "#fUI/UIWindow/Quest/icon3/6#"; // 正方形
var blueArrow = "#fUI/UIWindow/Quest/icon2/7#"; // 蓝色箭头
var rn = "\r\n\r\n"; // 换行
var 心 =  "#fEffect/CharacterEff/1032063/0/0#"; //长音符
var bindingKeyCode = 57;

var status = 0;
var selectionArr = [0, 0, 0]
var selectionData = [0, 0, 0]

var CDKEY_ERR_NOT_FOUND = -1;
var CDKEY_ERR_NOT_VALID = -2;
var CDKEY_ERR_INPUT_ERROR = -3;
var seltype = 0;

/**
 * 材料列表
 * @type {*[]}
 */
var itemlist = [500000000, 50000, [//金币、点券
    [4011004, 50],//材料id\数量
    [4011009, 50],
    [4021004, 50],
    [4011005, 50],
    [4021001, 50],
    [4011001, 50],
    [4021005, 50],
    [4011000, 50],
    [4011003, 50],
    [4005002, 50],
    [4011002, 50],
    [4021003, 50],
    [4011006, 50],
    [4021006, 50],
    [4021002, 50],
    [4021007, 50],
    [4005000, 50],
    [4021000, 50],
    [4005004, 50],
    [4021008, 50],
    [4005001, 50],
    [4005003, 50],
    [4011008, 50]

]];
//需使用余额
var useye = 300;
var 赞助余额;
function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode === -1) {
        cm.dispose();
    } else {
        if (status >= 0 && mode === 0) {
           
            cm.dispose();
            return;
        }

        status = status + (mode === 1 ? 1 : -1);

        if (status === 0) {
            showMenu();
        } else if (status === 1) {
            seltype = selection;
            var add = "#e#d【本次兑换所需】\r\n\r\n";
            if (seltype === 0) {

                add += "#b#v5200002#" + itemlist[0] + "金币#b\r\n";
                add += "#b#v5200000#" + itemlist[1] + " 点券#b\r\n";
                for (var key in itemlist[2]) {
                    var itemName = cm.getItemName(itemlist[2][key][0]);
                    add += itemName;
                    var currentItemQuantity = cm.getPlayer().getItemQuantity(itemlist[2][key][0], true);
                    var color = "#g";
                    if (currentItemQuantity < itemlist[2][key][1])
                        color = "#r";
                    add += color + currentItemQuantity + " / " + itemlist[2][key][1] + " 个#b\r\n";
                }
            } else {
                赞助余额 = cm.getBossRank("赞助余额", 2) == -1 ? 0 : cm.getBossRank("赞助余额", 2);
                add += "当前账号余额：【" + 赞助余额 + "】\r\n";
                add += "#b需使用#v5200000#【" + useye + "】余额\r\n";

            }
            cm.sendYesNo(add);

        } else if (status === 2) {
            if(check()){
                cm.sendOk("你已经开启过了，可以直接使用游戏聊天命令@petvac开启！");
                cm.dispose();
                return;
            }
            if (seltype === 0) {
                var flag = true;
                for (var key in itemlist[2]) {
                    var itemId = itemlist[2][key][0];
                    var itemQuantity = itemlist[2][key][1];
                    if (!cm.haveItem(itemId, itemQuantity)) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    for (var key in itemlist[2]) {
                        var itemId = itemlist[2][key][0];
                        var itemQuantity = itemlist[2][key][1];
                        cm.gainItem(itemId, -itemQuantity);
                    }
                    uppetvac();
                    cm.sendOk("使用材料开启成功，可以使用游戏聊天命令@petvac开启！");
                    cm.dispose();
                    return;
                } else {
                    cm.sendOk("材料不足，无法兑换！");
                    cm.dispose();
                }
            } else {
                if (useye > 赞助余额) {
                    cm.sendOk("账号余额不足！");
                    cm.dispose();
                    return;
                } else {
                    cm.setBossRankCount("赞助余额", -useye);
                    uppetvac();
                    cm.sendOk("使用余额开启成功，可以使用游戏聊天命令@petvac开启！");
                    cm.dispose();
                    return;
                }
            }
        }
    }
}

/**
 * 显示菜单
 */
function showMenu() {
	var text = "\t      " + 心 + "#r#e < 泡点兑换 > #k#n " + 心 + "\r\n\r\n\r\n";
     text += "你好，这里可以兑换宠吸哦，宠物全屏捡取物品，可以使用材料兑换余额兑换哦，账号内角色通用。" + rn;
    text += "#L0##b使用#r【材料】#k#b兑换宠吸功能#l" + rn;
    text += "#L1##b使用#r【余额】#k#b兑换宠吸功能#l" + rn;
    cm.sendSimple(text);
}

function check(){
    var accId = cm.getPlayer().getClient().getAccID();
    var con = cm.getDataSource().getConnection();
    var ps = con.prepareStatement("SELECT * FROM accounts WHERE id = ?");
    ps.setInt(1, accId);
    var rs = ps.executeQuery();
    while (rs.next()) {
        if (rs.getInt("canpetvac") !=0) {
            rs.close();
            ps.close();
            con.close();
            return true;
        }
    }

    rs.close();
    ps.close();
    con.close();
    return false;
}
function uppetvac() {
    var accId = cm.getPlayer().getClient().getAccID();
    var con = cm.getDataSource().getConnection();
    var ps = con.prepareStatement("UPDATE accounts SET canpetvac = 1 WHERE id = ?")
    ps.setInt(1, accId);
    ps.executeUpdate();
    ps.close();
    con.close();
}
