/* 
满专业技能(也可当作学习某些技能的模板)——byPPMS
 */
var status = 0;
//技能id，经验
var skills = [
    [92000000, 2100000000],
    [92010000, 2100000000],
    [92020000, 2100000000],
    [92030000, 2100000000],
    [92040000, 2100000000]
];
//金币、点券、材料、余额
var cash = [1000, 2000, [
    [4002003, 10],
    [4002002, 10]
], 5]


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
        var txt = "#e您好~！我这可以学习全部满级专业技术，请问想以哪种方式交学费呢？#k  \r\n\r\n"
        txt += "#d#L0#1、使用金币、点券、材料#l#k \r\n\r\n";
        txt += "#r#L1#2、使用金币、点券、余额#l#k \r\n\r\n";
        cm.sendSimple(txt);
    } else if (status == 1) {
        sel = selection;
        switch (sel) {
            case 0: {
                var selStr = "#e将花费#b\r\n\r\n";
                selStr += "#b#v5200002#" + cash[0] + "金币#b\r\n";
                selStr += "#b#v5200000#" + cash[1] + " 点券#b\r\n";
                for (var key in cash[2]) {
                    selStr += "#i"+cash[2][key][0]+"#";
                    var currentItemQuantity = cm.getPlayer().getItemQuantity(cash[2][key][0], true);
                    var color = "#g";
                    if (currentItemQuantity < cash[2][key][1])
                        color = "#r";
                        selStr += color + currentItemQuantity + " / " + cash[2][key][1] + " 个#b\r\n";
                }
                 selStr += "#e学习满级专业技术，确定吗？#b";
                cm.sendYesNo(selStr);
                break;
            }
            case 1: {
                var selStr = "#e将花费#b";
                selStr += "#b#v5200002#" + cash[0] + "金币#b\r\n";
                selStr += "#b#v5200000#" + cash[1] + "点券#b\r\n";
                selStr += "#b#v5200001#" + cash[3] + "余额#b\r\n";
                selStr += "#e学习满级专业技术，确定吗？#b";
                cm.sendYesNo(selStr);
                break;
            }
            default: {
                cm.dispose();
                return;
            }
        }


    } else if (status == 2) {
        var flag = true;
        if (cm.getMeso() < cash[0] || cm.getPlayer().getCSPoints(1) < cash[1]) {
            flag = false;
        }
        if (sel == 0) {
            for (var key in cash[2]) {
                var itemId = cash[2][key][0];
                var itemQuantity = cash[2][key][1];
                if (!cm.haveItem(itemId, itemQuantity)) {
                    flag = false;
                    break;
                }
            }
        } else {
            if (cm.getBossRank("赞助余额", 2) < cash[3]) {
                flag = false;
            }
        }
        if (!flag) {
            cm.sendOk("学费不够！");
            cm.dispose();
            return;
        }

        if (sel == 1) {
            cm.setBossRankCount("赞助余额", -cash[3]);
        } else {
            for (var key in cash[2]) {
                var itemId = cash[2][key][0];
                var itemQuantity = cash[2][key][1];
                cm.gainItem(itemId, -itemQuantity);
            }
        }

        cm.gainMeso(-cash[0]);
        cm.gainNX(-cash[1]);
        for(var ss in skills){
            cm.teachSkill(skills[ss][0], skills[ss][1], 0);
        }
        cm.sendSimple("#e学习成功，快打开专业技术栏看看吧！#l#k");
        cm.dispose();

    } else {
        cm.dispose();
        return;
    }
}