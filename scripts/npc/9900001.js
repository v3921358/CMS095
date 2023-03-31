/** Author: nejevoli
 NPC Name: 		NimaKin
 Map(s): 		Victoria Road : Ellinia (180000000)
 Description: 		Maxes out your stats and able to modify your equipment stats
 */


var status = 0;
var slot = Array();
var stats = Array("力量", "敏捷", "运氣", "智力", "HP", "MP", "攻擊力", "魔法攻擊力", "防禦力", "魔法防禦力", "命中值", "迴避值", "靈敏度", "移动速度", "跳躍力", "可使用卷軸次數", "金錘子", "已使用卷軸次數", "强化等級", "潛能屬性 1", "潛能屬性 2", "潛能屬性 3", "潛能屬性 4", "潛能屬性 5", "物品刻名");
var selected;
var statsSel;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (status >= 0 && mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1)
        status++;
    else
        status--;

    if (status == 0) {
        if (cm.getPlayerStat("ADMIN") == 1) {
            cm.sendSimple("我有什么可以帮您的?#b\r\n#L0#满屬性!#l\r\n#L1#满技能!#l\r\n#L2#調整裝备屬性!#l\r\n#L3#查看潛能代碼及对應屬性#l\r\n#L4#AP/SP清零#l\r\n#L5#清空技能#l\r\n#L6#满当前职业技能#l\r\n#L7#清空当前屬性!#k");
        } else if (cm.getPlayerStat("GM") == 1) {
            cm.sendSimple("我有什么可以帮您的?#b\r\n#L0#满屬性!#l\r\n#L1#满技能!#l\r\n#L4#AP/SP清零#l\r\n#L7#清空当前屬性!#k");
        } else {
            cm.dispose();
        }
    } else if (status == 1) {
        if (selection == 0) {
            if (cm.getPlayerStat("GM") == 1) {
                cm.maxStats();
                cm.sendOk("祝你好运!");
            }
            cm.dispose();
        } else if (selection == 7) {
            if (cm.getPlayerStat("GM") == 1) {
                cm.getPlayer().resetStats(4, 4, 4, 4);
                cm.sendOk("祝你好运!");
            }
            cm.dispose();
        } else if (selection == 1) {
            //Beginner
            if (cm.getPlayerStat("GM") == 1) {
                cm.maxAllSkills();
            }
            cm.dispose();
        } else if (selection == 2 && cm.getPlayerStat("ADMIN") == 1) {
            var avail = "";
            for (var i = -1; i > -199; i--) {
                if (cm.getInventory(-1).getItem(i) != null) {
                    avail += "#L" + cm.getDoubleAbs(i) + "##t" + cm.getInventory(-1).getItem(i).getItemId() + "##l\r\n";
                }
                slot.push(i);
            }
            cm.sendSimple("你想要修改哪一个裝备?\r\n#b" + avail);
        } else if (selection == 3 && cm.getPlayerStat("ADMIN") == 1) {
            var eek = cm.getAllPotentialInfo();
            var avail = "#L0#尋找潛能代碼#l\r\n";
            for (var ii = 0; ii < eek.size(); ii++) {
                avail += "#L" + eek.get(ii) + "#潛在能力 ID " + eek.get(ii) + "#l\r\n";
            }
            cm.sendSimple("你想了解些什么?\r\n#b" + avail);
            status = 9;
        } else if (selection == 4) {
            cm.getPlayer().resetAPSP();
            cm.dispose();
        } else if (selection == 5) {
            cm.clearSkills();
            cm.dispose();
        } else if (selection == 6) {
            cm.maxSkillsByJob();
            cm.dispose();
        } else {
            cm.dispose();
        }
    } else if (status == 2 && cm.getPlayerStat("ADMIN") == 1) {
        selected = selection - 1;
        var text = "";
        for (var i = 0; i < stats.length; i++) {
            text += "#L" + i + "#" + stats[i] + "#l\r\n";
        }
        cm.sendSimple("你已经决定修改你的 #b#t" + cm.getInventory(-1).getItem(slot[selected]).getItemId() + "##k.\r\n你想修改哪个?\r\n#b" + text);
    } else if (status == 3 && cm.getPlayerStat("ADMIN") == 1) {
        statsSel = selection;
        if (selection == 24) {
            cm.sendGetText("你想要調整 #b#t" + cm.getInventory(-1).getItem(slot[selected]).getItemId() + "##k's " + stats[statsSel] + " 吗?");
        } else {
            cm.sendGetNumber("你想要調整 #b#t" + cm.getInventory(-1).getItem(slot[selected]).getItemId() + "##k's " + stats[statsSel] + " 吗?", 0, 0, 60004);
        }
    } else if (status == 4 && cm.getPlayerStat("ADMIN") == 1) {
        cm.changeStat(slot[selected], statsSel, selection);
        cm.sendOk("你的 #b#t" + cm.getInventory(-1).getItem(slot[selected]).getItemId() + "##k's " + stats[statsSel] + " 調整为 " + selection + ".");
        cm.dispose();
    } else if (status == 10 && cm.getPlayerStat("ADMIN") == 1) {
        if (selection == 0) {
            cm.sendGetText("你想搜索什么? (如 力量)");
            return;
        }
        cm.sendSimple("#L3#" + cm.getPotentialInfo(selection) + "#l");
        status = 0;
    } else if (status == 11 && cm.getPlayerStat("ADMIN") == 1) {
        var eek = cm.getAllPotentialInfoSearch(cm.getText());
        for (var ii = 0; ii < eek.size(); ii++) {
            avail += "#L" + eek.get(ii) + "#潛在能力 ID " + eek.get(ii) + "#l\r\n";
        }
        cm.sendSimple("你想了解些什么??\r\n#b" + avail);
        status = 9;
    } else {
        cm.dispose();
    }
}