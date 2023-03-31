//装备兑换列表【金币，点券，【材料物品，数量，材料物品，数量】，装备】
var itemArr = [
    [1000000000, 100000, [[4000015, 1000], 
                          [4000021, 1000],
                          [4000012, 1000],
                          [4000007, 1000],
                          [4000008, 1000],
                          [4000030, 1000],
                          [4000499, 1000],
                          [4000024, 1000],
                          [4000004, 1000],
                          [4000034, 1000]], 1142796]
]
var itemsel = 0;
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
        if (mode == 0 && status == 0) {
            cm.dispose();
            return;
        }
        if (mode == 1)
            status++;
        else
            status--;
        if (status === 0) {
            var add = "请选择你想兑换的装备" + rn;
            add += "#L999##b打开装备制作者   #L888##b勋章强化#k#l" + rn;
            for (var key in itemArr) {
                add += "#L" + key + "##b使用材料兑换#v" + itemArr[key][3] + "##z" + itemArr[key][3] + "##l" + rn;
            }
            cm.sendSimple(add);
           } else if (status == 1) {
        switch (selection) {
            case 999:
                cm.dispose();
                cm.openNpc(9900004, "装备制作者"); //脚本名字
                return;
            case 888:
                cm.dispose();
                cm.openNpc(9900004, "勋章强化");  //脚本名字
                return;
            }
            itemsel = selection;
            var text = "- #e#d兑换#z" + itemArr[itemsel][3] + "#需要的材料：#n#k\r\n\r\n#b";
            for (var key in itemArr[itemsel][2]) {
                var itemName = cm.getItemName(itemArr[itemsel][2][key][0]);
                text += itemName;
                var currentItemQuantity = cm.getPlayer().getItemQuantity(itemArr[itemsel][2][key][0], true);
                var color = "#g";
                if (currentItemQuantity < itemArr[itemsel][2][key][1])
                    color = "#r";
                text += color + currentItemQuantity + " / " + itemArr[itemsel][2][key][1] + " 个#b\r\n";
            }
            text += "#b#v5200002#" + itemArr[itemsel][0] + "金币#b\r\n";
            text += "#b#v5200000#" + itemArr[itemsel][1] + " 点券#b\r\n";
            text += "#k\r\n\r\n- #e#d管理提示：#n#b点是进行制作。点否返回上一页.#k";
            cm.sendYesNo(text);

        } else if (status == 2) {
            var flag = true;
            for (var key in itemArr[itemsel][2]) {
                var itemId = itemArr[itemsel][2][key][0];
                var itemQuantity = itemArr[itemsel][2][key][1];
                if (!cm.haveItem(itemId, itemQuantity)) {
                    flag = false;
                    break;
                }
            }
            if (cm.getMeso() < itemArr[itemsel][0] || cm.getPlayer().getCSPoints(1) < itemArr[itemsel][1]) {
                flag = false;
            }
            if (flag) {
                if (!cm.canHold(itemArr[itemsel][3])) {
                    cm.sendOk("装备栏空间不足，请整理后重新制作！");
                    cm.dispose();
                    return;
                }
                for (var key in itemArr[itemsel][2]) {
                    var itemId = itemArr[itemsel][2][key][0];
                    var itemQuantity = itemArr[itemsel][2][key][1];
                    cm.gainItem(itemId, -itemQuantity);
                }
                cm.gainMeso(-itemArr[itemsel][0]);
                cm.gainNX(-itemArr[itemsel][1]);
                cm.gainItem(itemArr[itemsel][3], 1);
                cm.sendOk("恭喜您合成#z" + itemArr[itemsel][3] + "#一把.");
                //cm.worldSpouseMessage(0x20, "[任务公告] : 恭喜 " + cm.getPlayer().getName() + " 制作了一件 <" + cm.getItemName(weaponList[weaponId]) + ">.");
                cm.dispose();
            } else {
                cm.sendOk("材料不足，无法完成制作！");
                cm.dispose();
            }
        }
    }
}