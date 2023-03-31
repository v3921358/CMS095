/*
 ZEVMS冒险岛(079)游戏服务端
 脚本：游戏推广系统
 需要连接二级脚本
 */
var 箭头 = "#fUI/Basic/BtHide3/mouseOver/0#";
var 心 = "#fUI/GuildMark.img/Mark/Etc/00009001/14#";
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
    var 推广码 = cm.getPlayer().id;
    var 返利 = cm.getBossRank9("推广积分", 2);
	var 推广人数 = cm.getBossRankCountTop9("" + cm.getPlayer().id + "").size();
	if (推广人数 >= 10){
	var 返利比例 = 20;
	}else if (推广人数 >= 5 && 推广人数 < 10){
	var 返利比例 = 15;	
	}else{
	var 返利比例 = 10;		
	}
    if (status <= 0) {
        var
		selStr = "\r\n   " + 心 + " " + 心 + "  " + 心 + "  " + 心 + " #r#e < 游戏推广 > #k#n " + 心 + "  " + 心 + "  " + 心 + " " + 心 + "\r\n\r\n";
        selStr +="#d\t你好，你目前已推广#r"+推广人数+"#d人，可以获得对方充值的 #r"+返利比例+"% #d返利，推广人数越多，返利比例越高！\r\n\t建议大家去贴吧、B站等地进行推广，千万不可去其他群发广告，否则发现立即封号踢群处理。#k\r\n\r\n";
        //显示自己的推广码
        selStr += "\t\t\t\t你的推广码为:#r" + 推广码 + "#k#n\r\n";
        //显示收到的充值返利
        if (返利 >= 0) {
            selStr += "\t\t\t\t目前推广积分:#r" + 返利 + "#k#n\r\n";
        }
        //判断是否有推广员
        if (cm.getBossRank9("推广上级", 2) > 0) {
            selStr += "\t\t\t\t你的推广上级是:#r" + cm.getCharacterNameById(cm.getBossRank9("推广上级", 2)) + "#k#n\r\n";
        }
        selStr += "\t\t\t#L0#" + 箭头 + "#b返回页面#l#k\r\n";
		selStr += "\t\t\t#L4#" + 箭头 + "#r推广任务#l#k\r\n";
		if (cm.getBossRankCount9("推广上级") <= 0){ 
		selStr += "\t\t\t#L1#" + 箭头 + "#b输入推广码#r(奖励新手礼盒)#l#k\r\n";
		}
        selStr += "\t\t\t#L2#" + 箭头 + "#b我的推广下级#l#k\r\n";
        if (返利 > 0) {
            selStr += "\t\t\t#L3#" + 箭头 + "#b兑换推广积分#l#k\r\n";
        }
		 selStr += " ";
        cm.sendSimple(selStr)
    } else if (status == 1) {
        switch (selection) {
            case 1:
                cm.dispose();
                //这里填写推广码二级分支
                cm.openNpc(9900004, "推广系统1");
                break;
			case 4:
                cm.dispose();
                //这里填写推广码二级分支
                cm.openNpc(9900004, "推广任务");
                break;
            case 0:
                cm.dispose();
                cm.openNpc(9900004);
                break;
            case 3:
				cm.setBossRankCount9("兑换推广积分",返利);
                cm.setBossRankCount9("推广积分",-返利);
                cm.sendOk("恭喜你将 #r" + 返利 + "#k 推广积分兑换为" + 返利 * 200 + "点券。");
				cm.gainNX(返利 * 200);
                cm.dispose();
                break;
            case 2:
                var text = "\t#r" + cm.getChar().getName() + "#k 推广的玩家：#n\r\n\r\n";
                var rankinfo_list = cm.getBossRankCountTop9("" + cm.getPlayer().id + "");
                if (rankinfo_list != null) {
                    for (var i = 0; i < rankinfo_list.size(); i++) {
                        var info = rankinfo_list.get(i);
                        text += i == 0 ? "#b" : i == 1 ? "#b" : i == 2 ? "#b" : "";
                        text += "\t #r" + (i + 1) + "#k#n. ";
                        text += info.getCname() + " ";
                        for (var j = 16 - info.getCname().getBytes().length; j > 0; j--) {
                            text += " ";
                        }
                        /*text += "\t#bLv." + cm.getCharacterByNameLevel(info.getCname()) + "";
                        */text += "#k";
						text += "\t#b充值积分#k." + cm.getBossRank9(cm.getCharacterIdByName(info.getCname()),"充值积分",2) + "";
                        text += "#k";
                        text += "\r\n";
                    }
                }
                cm.sendOkS(text, 3);
                cm.dispose();
                break;
        }
    }
}