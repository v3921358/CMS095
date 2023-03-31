var status = -1;
var add = false;
function start() {
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
        if (mode == 1) {
            status++;
        } else {
            status--;
        }
        if (status == 0) {
            cm.checkMobs(cm.getPlayer());
        } else if (status == 1) {
            mobid = selection;
            cm.sendOk(cm.checkDrop(cm.getPlayer(), selection, cm.getPlayer().isGM()));

        } else if (status == 2) {
            if (!cm.getPlayer().isGM()) {
                cm.dispose();
            }
            if (selection == 10000) {
                add = true;
                cm.sendGetText("请输入您要新增的物品代码。#k");
            } else {
                itemid = selection;
                cm.sendGetText("请输入您要更改的机率。\r\n#b更改方法:\r\n(若要改为100%请输入1000000,改为3%请输入30000..以此类推)#k");
            }
        } else if (status == 3) {
            if (add == true) {
                itemid = cm.getText();
                cm.sendGetText("请输入此物品要设定的掉落机率。\r\n#b新增方法:\r\n(若要改为100%请输入1000000,改为3%请输入30000..以此类推)#k");
            } else {
                cm.SystemOutPrintln(mobid + " " + cm.getText() + " " + itemid);
                cm.UpdateDropChance(cm.getText(), mobid, itemid);
                status = 7;
                cm.sendYesNo("#b已成功更改此物品掉落机率!您是否要重载怪物掉宝机率?\r\n(点选立即生效)");
            }
        } else if (status == 4) {
            chance = cm.getText();
            cm.sendGetText("请输入此物品要设定的任务ID(编号)。\r\n#b设定方法:\r\n若掉落物有需求任务请填写任务ID\r\n若掉落物无需求任务请填写0#k");
        } else if (status == 5) {
            questid = cm.getText();
            cm.SystemOutPrintln("chance:" + chance + "mobid:" + mobid + "itemid:" + itemid + "qusetid" + questid);
            cm.AddDropData(chance, mobid, itemid, questid);
            status = 7;
            cm.sendYesNoS("#b已成功新增此掉落物!您是否要重载怪物掉宝机率?\r\n(点选立即生效)", 3);
        } else if (status == 6) {
            cm.dispose();
        } else if (status == 8) {
            cm.processCommand("!reloaddrops");
            cm.sendOk("已重载怪物掉宝机率!");
            cm.dispose();
        }

    }
}

