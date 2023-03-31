/* guild creation npc */


load("scripts/utils/db_functions.js");
var status = -1;
var sel;

function start() {
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 0 && status == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1)
        status++;
    else
        status--;

	if (cm.getPlayerStat("GID") > 0) {                  //听北冥的家族创建副本修复
                	var 升级需求 = cm.getPlayer().getGuild().getCapacity() ;
    		var 家族名称 = cm.getPlayer().getGuild().getName();
	} else {
		var 升级需求 =  5;
    		var 家族名称 = " ";
	}

    	//var 升级需求 = cm.getPlayer().getGuild().getCapacity() * 5;
    	//var 家族名称 = cm.getPlayer().getGuild().getName();

    	if (getFbLog("" + 家族名称 + "", 2) > 0) {
        		var 家族积分 = getFbLog("" + 家族名称 + "", 2);
    	} else {
        		var 家族积分 = 0;
    	}

    if (status == 0)
        cm.sendSimple("你想要做什么？\r\n#b#L0#创建公会#l\r\n#L1#解散公会#l\r\n#L2#扩充公会人数(升级家族)#l\r\n"/*"#L3#Increase your Guild's capacity (limited to 200)#l#k"*/);
    else if (status == 1) {
        sel = selection;
        if (selection == 0) {
            if (cm.getPlayerStat("GID") > 0) {
                cm.sendOk("你不能创建一个新的工会。");
                cm.dispose();
            } else
                cm.sendYesNo("创建公会需要 #b500,000 金币#k, 你确定要创建公会吗?");
        } else if (selection == 1) {
            if (cm.getPlayerStat("GID") <= 0 || cm.getPlayerStat("GRANK") != 1) {
                cm.sendOk("你不是公会会长所以不能解散公会。");
                cm.dispose();
            } else
                cm.sendYesNo("你确定要解散你的公会?你将无法恢复并且GP消失。");
        } else if (selection == 2) {
            if (cm.getPlayerStat("GID") <= 0 || cm.getPlayerStat("GRANK") != 1) {
                cm.sendOk("你不是公会会长所以不能扩充人数。");
                cm.dispose();
                return
            }
            if (家族积分 < 升级需求) {
                cm.sendOk("当前家族积分不足，无法升级。\r\n\r\n当前家族积分为" + 家族积分 + ",升级家族需要" + 升级需求 + ".");
                cm.dispose();
            } else
                cm.sendYesNo("扩充公会人数 #b5#k 要 #b500,000 金币#k, 你确定要扩充吗?");
        } else if (selection == 3) {
            if (cm.getPlayerStat("GID") <= 0 || cm.getPlayerStat("GRANK") != 1) {
                cm.sendOk("You can only increase your Guild's capacity if you are the leader.");
                cm.dispose();
            } else
                cm.sendYesNo("Increasing your Guild capacity by #b5#k costs #b25,000 GP#k, are you sure you want to continue?");
        }
    } else if (status == 2) {
        if (sel == 0 && cm.getPlayerStat("GID") <= 0) {
            cm.genericGuildMessage(1);
            cm.dispose();
        } else if (sel == 1 && cm.getPlayerStat("GID") > 0 && cm.getPlayerStat("GRANK") == 1) {
            cm.disbandGuild();
            cm.dispose();
        } else if (sel == 2 && cm.getPlayerStat("GID") > 0 && cm.getPlayerStat("GRANK") == 1) {
            setFbLog("" + 家族名称 + "", 2, -升级需求);
            cm.increaseGuildCapacity(false);
            cm.dispose();
        } else if (sel == 3 && cm.getPlayerStat("GID") > 0 && cm.getPlayerStat("GRANK") == 1) {
            cm.increaseGuildCapacity(true);
            cm.dispose();
        }
    }
}

