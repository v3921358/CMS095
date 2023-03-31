/* global cm */

var status, str, select, list;

function start() {
    status = -1;
    str = "";
    select = -1;
    str += "================#e高级查询工具#n================";
    str += "\r\n#L1#道具#l";
    str += "\r\n#L2#NPC#l";
    str += "\r\n#L3#地图#l";
    str += "\r\n#L4#怪物#l";
    str += "\r\n#L5#任务#l";
    str += "\r\n#L6#技能#l";
    str += "\r\n#L7#职业#l";
    str += "\r\n#L8#伺服器包头#l";
    str += "\r\n#L9#用户端包头#l";
    str += "\r\n#L10#髮型#l";
    str += "\r\n#L11#脸型#l";
    str += "\r\n#L13#查询玩家道具数量#l";
    str += "\r\n#L14#查询物品掉落怪物#l";
    str += "\r\n#L15#查询各种类型道具#l";
	str += "\r\n#L15#查询怪物掉落物品#l";	
    str += "\r\n#L12#肤色#l";
    cm.sendSimple(str);
}

function action(mode, type, selection) {
    var name = cm.getPlayer().getName();
    if (mode == 1) {
        status++;
    } else {
        status--;
        cm.dispose();
        return;
    }
    switch (status) {
        case 0:
            str = selection;
            if (str == 15) {
                cm.dispose();
                cm.openNpc(9010000, "物品查询");
            } else
                cm.sendGetText("请输入需要检索的讯息:");
            break;
        case 1:
            switch (str) {
                case 10:
                case 11:
                case 12:
                    list = cm.getSearchData(str, cm.getText());
                    if (list == null) {
                        cm.sendOk("搜寻不到讯息");
                        cm.dispose();
                        return;
                    }
                    cm.sendStyle("", list);
                    break;
                case 13:
                    cm.sendOk(cm.searchData(1, cm.getText()));
                    break;
                case 14:
                    cm.sendOk(cm.searchData(1, cm.getText()));
                    break;
                default:
                    cm.sendOk(cm.searchData(str, cm.getText()));
            }
            break;
        case 2:
            if (select == -1) {
                select = selection;
            }
            if (str == 13) {
                cm.sendNext(cm.ShowGMItemRank(select));
                cm.dispose();
            }
            if (!cm.foundData(str, cm.getText()) && str != 14) {
                cm.dispose();
                return;
            }

            switch (str) {
                case 1:
                    if (select < 1000000) {
                        if (select / 10000 == 2) {
                            cm.setFace(select);
                        } else if (select / 10000 == 3) {
                            cm.setHair(select);
                        }
                        cm.dispose();
                    } else if (select < 2000000) {
                        if (cm.canHold(select)) {
                            cm.gainItemPeriod(select, 1, 0);
                        }
                        cm.dispose();
                    } else if (select >= 5000000 && select < 5010000) {
                        cm.sendGetNumber("选中的宠物为#i" + select + ":# #z" + select + "#\r\n请输入生命时间(天):", 1, 1, 92);
                    } else {
                        cm.sendGetNumber("选中的道具为#i" + select + ":# #z" + select + "#\r\n请输入制作数量:", 1, 1, 500);
                    }
                    break;
                case 2:
                    cm.dispose();
                    cm.playerMessage(5, "打开NPC,ID:" + select);
                    cm.openNpc(select);
                    break;
                case 3:
                    cm.playerMessage(5, "传送到地图,ID:" + select);
                    cm.warp(select, 0);
                    cm.dispose();
                    break;
                case 4:
                    cm.sendGetNumber("选中的怪物为#o" + select + "#\r\n请输入召唤数量:", 1, 1, 100);
                    break;
                case 5:
                    cm.sendSimple("选中的任务ID为" + select + "\r\n请选择需要执行的操作:\r\n#L0#开始任务#l\r\n#L1#完成任务#l");
                    break;
                case 6:
                    cm.sendGetNumber("选中的技能ID为" + select + "\r\n请输入使用等级:", 1, 1, 30);
                    break;
                case 7:
                    cm.playerMessage(5, "转职,职业代码:" + select);
                    cm.changeJob(select);
                    cm.dispose();
                    break;
                case 8:
                case 9:
                    cm.dispose();
                    break;
                case 10:
                    cm.playerMessage(5, "更变髮型, 髮型代码:" + list[select]);
                    cm.setHair(list[select]);
                    cm.dispose();
                    break;
                case 11:
                    cm.playerMessage(5, "更变脸型, 脸型代码:" + list[select]);
                    cm.setFace(list[select]);
                    cm.dispose();
                    break;
                case 12:
                    cm.playerMessage(5, "更变肤色, 肤色代码:" + list[select]);
                    cm.setSkin(list[select]);
                    cm.dispose();
                    break;
                case 14:
                    cm.sendNext(cm.checkItemDrop(cm.getPlayer(), select));
                    break;
                default:
                    cm.dispose();
            }
            break;
        case 3:
            switch (str) {
                case 1:
                    if (select < 2000000) {
                        if (cm.canHold(select)) {
                            cm.gainItemPeriod(select, 1, 0);
                        }
                    } else if (select >= 5000000 && select < 5010000) {
                        if (cm.canHold(select)) {
                            cm.gainItem(select, 1, selection);
                        }
                    } else {
                        cm.gainItemPeriod(select, selection, 0);
                    }
                    cm.dispose();
                    break;
                case 4:
                    cm.spawnMonster(select, selection);
                    cm.dispose();
                    break;
                case 5:
                    cm.dispose();
                    switch (selection) {
                        case 0:
                            cm.startQuest(select);
                            break;
                        case 1:
                            cm.completeQuest(select);
                            break;
                    }
                    break;
                case 6:
                    cm.useSkill(select, selection);
                    cm.dispose();
                    break;
                case 14:
                    sel = selection;
                    cm.sendGetText("请输入您要更改的机率。\r\n#b更改方法:\r\n(若要改为100%请输入1000000,改为3%请输入30000..以此类推)#k");
                    break;
                default:
                    cm.dispose();
            }
            break;
        case 4:
            cm.SystemOutPrintln(select + " " + cm.getText() + " " + sel);
            cm.UpdateDropChance(cm.getText(), sel, select);
            cm.sendYesNo("#b已成功更改此物品掉落机率!您是否要重载怪物掉宝机率?\r\n(点选立即生效)");
            break;
        case 5:
            cm.processCommand("!reloaddrops");
            cm.sendOk("已重载怪物掉宝机率!");
            cm.dispose();
            break;

        default:
            cm.dispose();
    }
}