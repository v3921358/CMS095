var 心 = "#fUI/GuildMark.img/Mark/Etc/00009001/14#";
var 奖励 = "#fUI/UIWindow.img/Quest/reward#";
var 金币 = "#fUI/UIWindow.img/QuestIcon/7/0#";
var 经验 = "#fUI/UIWindow.img/QuestIcon/8/0#";
var 人气度 = "#fUI/UIWindow.img/QuestIcon/6/0#";
var 任务简述 = "#fUI/UIWindow.img/Quest/summary#";
var status = -1;
var sel;
var mod;
var Simple = "";
var pos;
var sqlpos;
var boss1 = Array(
	2220000,6130101,5090000,8220007,3220000,6300005,6220000,5220002,3300005,3300007
);
		
var boss2 = Array(
	8130100,8220000,6160003,4220000,7220001,4130103,5220003,6220001,3220001,7220000
);
		
var boss3 = Array(
	7220002,8220002,8180000,8180001,8220001,8220003,8220004,8220005,8220006
);
        


function start() {
	action(1,0,0);
}

function action(mode, type, selection) {
    if (mode == 0) {
        cm.dispose();
        return;
    } else {
        status++;
    }
	var 等级 = cm.getPlayer().getLevel();
    if (status == 0) {
		if (cm.getPlayer().getBossLogD("每日通缉") == 0 && cm.getPlayer().getBossLogD("每日通缉刷新") == 0) {
		cm.setBossRankCount("赏金任务",10);
		cm.setBossRankPoints("赏金任务",10);
		var sqlpos = cm.getBossRankCount("赏金任务");
		var qquantity = cm.getBossRankPoints("赏金任务");
		cm.setBossRankCount("赏金任务",-sqlpos);
		cm.setBossRankPoints("赏金任务",-qquantity);
		cm.setBossLog("每日通缉刷新");
		cm.sendOk("刷新成功，请重新打开。");
        cm.dispose();
        return;
		}
		var 通缉怪物 = cm.getBossRankPoints("赏金任务");
    if (cm.getBossRankPoints("赏金任务") == 0) {
		Simple = "#e" + 心 + "\t欢迎来到【Maple Story】通缉任务\t" + 心 + "#n\r\n";
		Simple += "\r\n"+任务简述+"\r\n";
		Simple += "\t1.每天可以完成3轮任务(每天 0:00 刷新)\r\n";
		Simple += "\t2.每轮会出现不同的BOSS需要亲手击杀(攻击最后一次)\r\n";
		Simple += "\t3.难度随任务轮数提高\r\n";
		Simple += "\t4.每轮解锁等级：30级第一轮 70级第二轮 120级第三轮#k\r\n";
		Simple += "#e#L3#任务奖励说明#n#l\r\n\r\n";
		Simple += "#e任务进度：#n\r\n\r\n";
		Simple += "\t今日已完成#r"+cm.getPlayer().getBossLogD("每日通缉")+"#k轮#d\r\n";
        Simple += "\t#e#b#L0#接受任务#l#n";
    } else if (cm.getBossRankPoints("赏金任务") > 0) {
		Simple = "#e任务进度：#n\r\n";
		if (cm.getBossRankCount("赏金任务") > 0) {
		Simple += "\t#b#L1#完成任务#l\r\n\r\n";
		}
        Simple += "#b请去击杀#o" + 通缉怪物 + "#\r\n\r\n\t\t\t#fMob/" + 通缉怪物 + ".img/hit1/0# \r\n";
    } else {
        Simple = "#b发生未知错误，联系管理员。#l";
    }
    cm.sendSimple(Simple);
	} else if (status == 1) {
        sel = selection;
        if (sel == 0) {
            if (cm.getPlayer().getBossLogD("每日通缉") >= 3) {
                cm.sendOk("任务已经完成了，请明天再来。");
                cm.dispose();
                return;
            } 
			if (cm.getPlayer().getBossLogD("每日通缉") == 0 && 等级 >= 30) {
				var 通缉怪物1 = boss1[Math.floor(Math.random() * boss1.length)];
				cm.setBossRankPoints("赏金任务",通缉怪物1);
				cm.sendOk("请去击杀#o" + 通缉怪物1 + "#\r\n\t\t\t#fMob/" + 通缉怪物1 + ".img/hit1/0#");
				cm.dispose();
				return;
			} 
			if (cm.getPlayer().getBossLogD("每日通缉") == 1 && 等级 >= 70) {
				var 通缉怪物2 = boss2[Math.floor(Math.random() * boss2.length)];
				cm.setBossRankPoints("赏金任务",通缉怪物2);
				cm.sendOk("请去击杀#o" + 通缉怪物2 + "#\r\n\n\t\t\t#fMob/" + 通缉怪物2 + ".img/hit1/0#");
				cm.dispose();
				return;
			} 
			if (cm.getPlayer().getBossLogD("每日通缉") == 2 && 等级 >= 120) {
				var 通缉怪物3 = boss3[Math.floor(Math.random() * boss3.length)];
				cm.setBossRankPoints("赏金任务",通缉怪物3);
				cm.sendOk("请去击杀#o" + 通缉怪物3 + "#\r\n\n\t\t\t#fMob/" + 通缉怪物3 + ".img/hit1/0#");
				cm.dispose();
			} else {
				cm.sendOk("你的等级不足！");
				cm.dispose();
			}
		}

        if (sel == 1) {
            if (cm.getPlayer().getBossLogD("每日通缉") >= 3) {
                cm.sendOk("任务已经完成了，请明天再来。");
                cm.dispose();
                return;
            }
            var 通缉怪物 = cm.getBossRankPoints("赏金任务");
			var 击杀数量 = cm.getBossRankCount("赏金任务");
            if (击杀数量 < 1) {
                cm.sendOk("你还没有击杀#o" + 通缉怪物 + "#\r\n\n\t\t\t#fMob/" + 通缉怪物 + ".img/hit1/0#");
                cm.dispose();
                return;
            }
            if (!cm.canHold()) {
                cm.sendOk("请确认背包是否已经满了。");
                cm.dispose();
                return;
            }
			if (cm.getPlayer().getBossLogD("每日通缉") == 0) {
				cm.gainExp(50*等级*等级);
				cm.gainMeso(10000000);
				cm.setBossLog("每日通缉");
				cm.setBossRankPoints("赏金任务",-通缉怪物);
				cm.setBossRankCount("赏金任务",-击杀数量);
				cm.sendOk("任务完成。");
				cm.dispose();
				return;
			}
			if (cm.getPlayer().getBossLogD("每日通缉") == 1) {
				cm.gainExp(100*等级*等级);
				cm.gainMeso(20000000);
				cm.setBossLog("每日通缉");
				cm.setBossRankPoints("赏金任务",-通缉怪物);
				cm.setBossRankCount("赏金任务",-击杀数量);
				cm.sendOk("任务完成。");
				cm.dispose();
				return;
			}
			if (cm.getPlayer().getBossLogD("每日通缉") == 2) {
				cm.gainExp(150*等级*等级);
				cm.gainMeso(40000000);
				var suijiitem = [5041000,2022336,5220010];
				var suijiquantity = [1,2,3,4,5]
				var suijiitem_new = cm.getRandom(suijiitem);
				var suijiquantity_new = cm.getRandom(suijiquantity);
				cm.gainItem(suijiitem_new, suijiquantity_new);
				cm.gainItem(4310019, 2);//全能抽奖币
				cm.setBossLog("每日通缉");
				cm.setBossRankPoints("赏金任务",-通缉怪物);
				cm.setBossRankCount("赏金任务",-击杀数量);
				cm.sendOk("任务完成。");
				cm.dispose();
				return;
			}
        }
         if (sel == 3) {
			var txt = "";
			txt += ""+奖励+"\r\n"
			txt += "#e第一轮#n：\r\n\r\n\t"+经验+" "+50*等级*等级+"\r\n\t"+金币+" 10000000\r\n\r\n";
			txt += "#e第二轮#n：\r\n\r\n\t"+经验+" "+100*等级*等级+"\r\n\t"+金币+" 20000000\r\n\r\n";
			txt += "#e第三轮#n：\r\n\r\n\t"+经验+" "+150*等级*等级+"\r\n\t"+金币+" 40000000\r\n";
			txt += "\t全部完成将随机获得:#i5041000# #i2022336# #i5220010##k\r\n#k";
			cm.sendOk(txt);
			cm.dispose();
			return;
			}

        }
    
}
