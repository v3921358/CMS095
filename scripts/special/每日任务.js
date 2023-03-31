/*
 ZEVMS冒险岛(079)游戏服务端
 脚本：清理背包
 */
var 图标1 = "#fUI/UIWindow.img/FadeYesNo/icon7#";
var 图标2 = "#fUI/StatusBar.img/BtClaim/mouseOver/0#";
var 关闭 = "#fUI/UIWindow.img/CashGachapon/BtOpen/mouseOver/0#";
var 打开 = "#fUI/UIWindow.img/CashGachapon/BtOpen/disabled/0#";
var JD = "#fUI/Basic/BtHide3/mouseOver/0#";
var 心 = "#fUI/GuildMark.img/Mark/Etc/00009001/14#";
var 装备2 = "#fUI/CashShop.img/Base/Tab2/Enable/0#";
var 消耗2 = "#fUI/CashShop.img/Base/Tab2/Enable/1#";
var 设置2 = "#fUI/CashShop.img/Base/Tab2/Enable/2#";
var 其他2 = "#fUI/CashShop.img/Base/Tab2/Enable/3#";
var 特殊2 = "#fUI/CashShop.img/Base/Tab2/Enable/4#";
var a = "#fEffect/CharacterEff.img/1112926/0/1#";

function start() {
    status = -1;
    action(1, 0, 0)
}

function action(mode, type, selection) {
    if (status <= 0 && mode <= 0) {
        cm.dispose();
        return
    }
    if (mode == 1) {
        status++
    } else {
        status--
    }
    if (status <= 0) {
        var selStr = "\t\t\t" + 心 + "  " + 心 + " #r#e < 每日任务 > #k#n " + 心 + "  " + 心 + "\r\n\r\n";

        selStr += "		#eHi~#b#h ##k，选择你要做的任务吧，希望你可以顺利完成它们。\r\n";
        selStr += "\t#L0#" + 图标2 + "#d签到任务" + 图标2 + "#l\t#L1#" + 图标2 + "#d在线任务" + 图标2 + "#l\r\n\r\n";
        selStr += "\t#L2#" + 图标2 + "#d跑环任务" + 图标2 + "#l\t#L3#" + 图标2 + "#d通缉任务" + 图标2 + "#l\r\n\r\n";
        selStr += "\t#L4#" + 图标2 + "#d副本任务" + 图标2 + "#l\t#L5#" + 图标2 + "#d家族任务" + 图标2 + "#l\r\n\r\n";
        selStr += "\t#L6#" + 图标2 + "#d高版本BOSS击杀任务" + 图标2 + "#l\r\n\r\n";


        cm.sendSimple(selStr)
    } else if (status == 1) {
        switch (selection) {
            case 0:
                cm.dispose();
                cm.openNpc(9310072, "每日签到");
                break;
            case 1:
                cm.dispose();
                cm.openNpc(9310072, "每日在线");
                break;
            case 2:
                cm.dispose();
                cm.openNpc(9310072, "每日收集");
                break;
            case 3:
                cm.dispose();
                cm.openNpc(9310072, "每日通缉");
                break;
            case 4:
                cm.dispose();
                cm.openNpc(9310072, "每日副本");
                break;
            case 5:
                cm.dispose();
                cm.openNpc(9310072, "每日家族");
                break;
            case 6:
                cm.dispose();
                cm.openNpc(9310072, "高版本BOSS击杀奖励");
                break;

        }
    }
}