/*
 ZEVMS冒险岛(079)游戏服务端
 脚本：游戏CDK兑换系统
 使用函数：
 给个人记录
 cm.setBossRankCount("点券积分", fee);
 */
var ca = java.util.Calendar.getInstance();
var year = ca.get(java.util.Calendar.YEAR);
var month = ca.get(java.util.Calendar.MONTH) + 1;
var day = ca.get(java.util.Calendar.DATE);
var hour = ca.get(java.util.Calendar.HOUR_OF_DAY);
var minute = ca.get(java.util.Calendar.MINUTE);
var second = ca.get(java.util.Calendar.SECOND);
var weekday = ca.get(java.util.Calendar.DAY_OF_WEEK);
var kx1 = "#fEffect/CharacterEff/1112925/0/2#";//空星
var hot = "#fUI/CashShop.img/CSEffect/hot/0#";
//玩家充值点券，反馈给推广员的百分比点券
var 推广员反馈百分比 = 10;


var status = 0;
var 心 = "#fUI/GuildMark.img/Mark/Etc/00009001/14#";
function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (mode == 0) {
            cm.dispose();
            return;
        }
        if (mode == 1) {
            status++;
        } else {
            status--;
        }
        if (cm.getInventory(1).isFull()) {
            cm.sendOk("请保证 #b装备栏#k 至少有2个位置。");
            cm.dispose();
            return;
        } else if (cm.getInventory(2).isFull()) {
            cm.sendOk("请保证 #b消耗栏#k 至少有2个位置。");
            cm.dispose();
            return;
        } else if (cm.getInventory(3).isFull()) {
            cm.sendOk("请保证 #b设置栏#k 至少有2个位置。");
            cm.dispose();
            return;
        } else if (cm.getInventory(4).isFull()) {
            cm.sendOk("请保证 #b其他栏#k 至少有2个位置。");
            cm.dispose();
            return;
        } else if (cm.getInventory(5).isFull()) {
            cm.sendOk("请保证 #b特殊栏#k 至少有2个位置。");
            cm.dispose();
            return;
        }

        if (status == 0) {
			var txt = "    Hi~#b#h ##k，这里是#bPP冒险岛#k为您准备的超值礼包，";
			txt += "在充值网站购买Cdk后，复制Cdk到自助充值系统兑换。\r\n\r\n"
			if (cm.getPlayer().getBossLogS("考古套装") < 1) {
				txt  += "   #r#L101#"+hot+""+kx1+"#v1003225#考古学家套装礼包#v1003225#"+kx1+"#l#k\r\n\r\n";
			}
			if (cm.getPlayer().getBossLogS("三国套装") < 1) {
				txt  += "   #r#L102#"+hot+""+kx1+"#v1152050#三国名将套装礼包#v1152050#"+kx1+"#l#k\r\n\r\n";
			}
			if (cm.getPlayer().getBossLogS("漫新套装") < 1) {
				txt  += "   #r#L103#"+hot+""+kx1+"#v1112585#漫步新月套装礼包#v1112585#"+kx1+"#l#k\r\n\r\n";
			}
			if (cm.getPlayer().getBossLogD("祝福礼包") < 1) {
				txt  += "   #r#L104#"+hot+""+kx1+"#v2340000#每日祝福卷轴礼包#v2340000#"+kx1+"#l#k\r\n\r\n";
			}
			if (cm.getPlayer().getBossLogD("放大镜礼包") < 1) {
				txt  += "   #r#L105#"+hot+""+kx1+"#v2460003#每日放大镜礼包#v2460003#"+kx1+"#l#k\r\n\r\n";
			}
			if (cm.getPlayer().getBossLogD("双倍礼包") < 1) {
				txt  += "   #r#L106#"+hot+"  "+kx1+"#v2450019#双倍经验礼包#v2450019#"+kx1+"#l#k\r\n\r\n";
			}
			if (cm.getPlayer().getBossLogD("每日暖暖礼包") < 3) {
				txt  += "   #r#L107#"+hot+" "+kx1+"#v5150040#每日暖暖礼包#v5150040#"+kx1+"#l#k\r\n\r\n";
			}
			if (cm.getPlayer().getBossLogD("每日快乐礼包") < 3) {
				txt  += "   #r#L108#"+hot+" "+kx1+"#v5220010#每日快乐礼包#v5220010#"+kx1+"#l#k\r\n\r\n";
			}
			txt += " "
            cm.sendOk(txt);
        } else if (status == 1) {
			if (selection == 101) {
			cm.dispose();
            cm.openNpc(9900004,"考古套装");
            }
			else if (selection == 102) {
			cm.dispose();
            cm.openNpc(9900004,"三国套装");
            }
			else if (selection == 103) {
			cm.dispose();
            cm.openNpc(9900004,"漫新套装");
            }
			else if (selection == 104) {
			cm.dispose();
            cm.openNpc(9900004,"祝福礼包");
            }
			else if (selection == 105) {
			cm.dispose();
            cm.openNpc(9900004,"放大镜礼包");
            }
			else if (selection == 106) {
			cm.dispose();
            cm.openNpc(9900004,"双倍礼包");
            }
			else if (selection == 107) {
			cm.dispose();
            cm.openNpc(9900004,"暖暖礼包");
            }
			else if (selection == 108) {
			cm.dispose();
            cm.openNpc(9900004,"快乐礼包");
            }
		}	
    }
}
