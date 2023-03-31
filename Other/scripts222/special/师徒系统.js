load('nashorn:mozilla_compat.js');
importPackage(Packages.database);
var W = "#fUI/UIWindow.img/PartySearch/check0#";
var X = "#fUI/UIWindow.img/PartySearch/check1#";
var wjx = "#fEffect/CharacterEff/1112925/0/0#";//五角星
var kx = "#fEffect/CharacterEff/1112925/0/1#";//空星
var status = -1;
var victim;
var name = "eV.095冒险岛 一 师 徒 系 统";
var 建立师门等级 = 140;
var nx;
var nx1;
var 拜师收徒介绍 = "师徒系统介绍：\r\n\t徒弟可拜师等级：100级以下\r\n\t师傅可收徒等级：140级以上\r\n\t徒弟达到120级即可出师\r\n\r\n#e#r注意：#k#n\r\n\t#b如何拜师：#k在师徒系统内输入师傅名字或在全服推荐师傅内选择一位即可拜师，需要对方开启了收徒开关。\r\n\t#b等级奖励：#k徒弟达到120级的时候#k可以在徒弟选项处点击等级奖励。\r\n\t#b如何出师：#k徒弟达到120级后，师傅查看徒弟列表时选择需要出师的徒弟，即可出师。\r\n\r\n#e#r具体奖励内容，请参照师徒奖励介绍";
var 师徒奖励介绍 = ""
var 出师等级 = 120;
var 最低拜师等级 = 10;
var 最高拜师等级 = 100;

var 师傅每日1级奖励 = Array(
    Array(4310019, 5)
);
var 师傅每日2级奖励 = Array(
    Array(4310019, 5)
);
var 师傅每日3级奖励 = Array(
    Array(4310019, 5)
);
var 师傅每日4级奖励 = Array(
    Array(4310019, 5)
);
var 师傅每日5级奖励 = Array(
    Array(4310019, 5)
);
var 师傅每日6级奖励 = Array(
    Array(4310019, 5)

);
var 徒弟等级奖励 = Array(
    Array(5062000, 100),
    Array(4002003, 50),
	Array(5220010, 10)
);

var 出师奖励 = Array(
    Array(4000463, 3)
);

var 师傅每日奖励道具;
var 师傅每日奖励数量;
var 称谓;
var 教学等级;
var 教学经验;

function start() {
    action(1, 0, 0);
}

function action(mode, type, selection) {

    if (mode == 1) {
        status++;
    } else if (mode == 0 && status != 0) {
        status--;
    } else {
        cm.dispose();
        return;
    }
	if (cm.getBossRankCount("徒弟数量")> 0) {
		var 徒弟数量 = cm.getBossRankCount("徒弟数量");
	} else {
		var 徒弟数量 = 0;
	}

    if (status == 0) {
        if (cm.getPlayer().getBossLogS("创建师门") == 0) {
            称谓 = "未建师门";
        } else if (cm.getPlayer().getBossLogS("出师积分") >= 0 && cm.getPlayer().getBossLogS("出师积分") <= 1) {
            称谓 = "普通教师";
        } else if (cm.getPlayer().getBossLogS("出师积分") >= 2 && cm.getPlayer().getBossLogS("出师积分") <= 5) {
            称谓 = "为人师表";
        } else if (cm.getPlayer().getBossLogS("出师积分") >= 6 && cm.getPlayer().getBossLogS("出师积分") <= 10) {
            称谓 = "循循善诱";
        } else if (cm.getPlayer().getBossLogS("出师积分") >= 11 && cm.getPlayer().getBossLogS("出师积分") <= 20) {
            称谓 = "诲人不倦";
        } else if (cm.getPlayer().getBossLogS("出师积分") >= 21 && cm.getPlayer().getBossLogS("出师积分") <= 40) {
            称谓 = "厚德树人";
        } else if (cm.getPlayer().getBossLogS("出师积分") >= 41 && cm.getPlayer().getBossLogS("出师积分") <= 999) {
            称谓 = "桃李天下";
        }
        if (cm.getPlayer().getBossLogS("出师积分") == 0) {
            教学等级 = 1;
        } else if (cm.getPlayer().getBossLogS("出师积分") >= 1 && cm.getPlayer().getBossLogS("出师积分") <= 5) {
            教学等级 = 2;
        } else if (cm.getPlayer().getBossLogS("出师积分") >= 6 && cm.getPlayer().getBossLogS("出师积分") <= 10) {
            教学等级 = 3;
        } else if (cm.getPlayer().getBossLogS("出师积分") >= 11 && cm.getPlayer().getBossLogS("出师积分") <= 20) {
            教学等级 = 4;
        } else if (cm.getPlayer().getBossLogS("出师积分") >= 21 && cm.getPlayer().getBossLogS("出师积分") <= 40) {
            教学等级 = 5;
        } else if (cm.getPlayer().getBossLogS("出师积分") >= 41 && cm.getPlayer().getBossLogS("出师积分") <= 999) {
            教学等级 = 6;
        }
        if (cm.getPlayer().getBossLogS("出师积分") == 0) {
            教学经验 = 1;
        } else if (cm.getPlayer().getBossLogS("出师积分") >= 1 && cm.getPlayer().getBossLogS("出师积分") <= 5) {
            教学经验 = 6 - cm.getPlayer().getBossLogS("出师积分");
        } else if (cm.getPlayer().getBossLogS("出师积分") >= 6 && cm.getPlayer().getBossLogS("出师积分") <= 10) {
            教学经验 = 11 - cm.getPlayer().getBossLogS("出师积分");
        } else if (cm.getPlayer().getBossLogS("出师积分") >= 11 && cm.getPlayer().getBossLogS("出师积分") <= 20) {
            教学经验 = 21 - cm.getPlayer().getBossLogS("出师积分");
        } else if (cm.getPlayer().getBossLogS("出师积分") >= 21 && cm.getPlayer().getBossLogS("出师积分") <= 40) {
            教学经验 = 41 - cm.getPlayer().getBossLogS("出师积分");
        } else if (cm.getPlayer().getBossLogS("出师积分") >= 41 && cm.getPlayer().getBossLogS("出师积分") <= 999) {
            教学经验 = "☆巅  峰☆";
        }
		if (cm.getBossRank("收徒开关",2) > 0) {
			var 收徒开关 = cm.getBossRank("收徒开关",2);
			var 开关 = ""+X+"#l";
		} else {
			var 收徒开关 = 0
			var 开关 = ""+W+"#l";
		}
		if (cm.getPlayer().getBossLogS("创建师门") > 0){
			var 师傅ID = cm.getPlayer().getId();
		} else {
			var 师傅ID = cm.getBossRankCount("师傅ID");
		}
		if (cm.getBossRank(师傅ID,"师傅评级",2) > 0){
			var 被除数 = cm.getBossRank(师傅ID,"师傅评级",2);
		} else {
			var 被除数 = 0;
		}
		var 师傅评级 = Math.round(cm.getBossRank(师傅ID,"师傅评级",1)/被除数);
		
		if (师傅评级 == 1) {
			var 打分 = ""+wjx+"#l";
		} else if (师傅评级 == 2) {
			var 打分 = ""+wjx+wjx+"#l";
		} else if (师傅评级 == 3) {
			var 打分 = ""+wjx+wjx+wjx+"#l";
		} else if (师傅评级 == 4) {
			var 打分 = ""+wjx+wjx+wjx+wjx+"#l";
		} else if (师傅评级 == 5) {
			var 打分 = ""+wjx+wjx+wjx+wjx+wjx+"#l";
		} else {
			var 打分 = ""+kx+"#l";
		}

        var text = "\t\t\t #d" + name + "#k\r\n";
        text += " 	┏━━━━━━━━基 本 信 息━━━━━━━┓#k\r\n"
		if (cm.getPlayer().getBossLogS("创建师门") > 0){
        text += "		#d您已收徒#k [#r" + 徒弟数量 + "#k]#d 位         #L1#收徒开关"+开关+"#l#k#n \r\n\r\n"
        text += "		#d好评率：" + 打分 + "\r\n\r\n"
		text += "		您的教学等级为 #k[ #r" + 教学等级 + "#k ]#d 	 称谓 #k[#r" + 称谓 + "#k]#d\r\n\r\n"
		text += "		您的教学经验为#k [ #r" + cm.getPlayer().getBossLogS("徒弟") + "#k ]#d	 还需 #k[ #r" + 教学经验 + "#k ]#d 升级#k\r\n\r\n"
		} else if (cm.getBossRankCount("师傅ID") > 0 && cm.getBossRankPoints("师傅ID") == 0) {
		text += "\r\n		#d您的师傅是：" + cm.getCharacterNameById(师傅ID) + "\t\tLv."+cm.getCharacterByNameLevel(cm.getCharacterNameById(师傅ID))+"级\r\n		师傅好评率："+打分+"#k\r\n\r\n";
		} else if (cm.getPlayer().getLevel() < 120){
		text += "\r\n		#d你还没有师傅，快去寻找师傅吧！#k\r\n\r\n";		
		} else {
		text += "\r\n		#d您可以创建自己的师门！#k\r\n\r\n";		
		}
        text += "	┗━━━━━━━━━━ ━━━━━━━━━━┛\r\n"
        text += "				   #r#L0#我要建立师门#l\r\n\r\n"
        text += "		#L90#拜师收徒介绍#l		#L91#师徒奖励介绍#l\r\n\r\n"
        text += "#b"
        text += " 	┏━━━━━━━━师 傅 选 项━━━━━━━┓#k\r\n"
        text += "  #L2#全服收徒#l #L3#我的徒弟#l#L4#逐出师门#l #L5#师傅每日#l#b\r\n\r\n"
        text += "	┗━━━━━━━━━━ ━━━━━━━━━━┛\r\n"
        text += " 	┏━━━━━━━━徒 弟 选 项━━━━━━━┓#k\r\n"
        text += "		#L10#我要拜师#l #L11#推荐师傅#l #L12#退出师门#l#b\r\n\r\n"
		if (cm.getPlayer().getLevel() >= 出师等级 && cm.getPlayer().getBossLogS("师徒等级奖励") == 0 && cm.getBossRankCount("师傅ID") > 0) {
		text += "		#k#L14#领取等级奖励#l#b\r\n\r\n"		
		}
		if (cm.getBossRankPoints("师傅ID") == 1 && cm.getPlayer().getBossLogS("师徒等级奖励") == 1 && cm.getPlayer().getBossLogS("师傅评分") == 0) {
		text += "		#k#L13#(#r您已出师#k)给师傅打分并领取出师奖励#l#b\r\n\r\n"
		}
        text += "	┗━━━━━━━━━━ ━━━━━━━━━━┛\r\n"
        cm.sendSimple(text);
    } else if (status == 1) {
        if (selection == 0) {//建立师门
            var id = cm.getPlayer().getId();
            if (cm.getPlayer().getLevel() < 建立师门等级) {
                cm.sendOk("你的等级不够 #r" + 建立师门等级 + " #k级。");
                cm.dispose();
                return;
            } else if (师傅ID > 0) {
				cm.sendOk("你尚未出师，无法创建师门。");
                cm.dispose();
                return;
			} else if (cm.getPlayer().getBossLogS("创建师门") == 0 ) {
                cm.getPlayer().setBossLog("创建师门");
                cm.sendOk("创建师门成功。");
                cm.worldMessage(6, "【师徒系统】[" + cm.getChar().getName() + "]成功建立了师门，要找师傅的赶紧了！");
                cm.dispose();
            } else {
                cm.sendOk("师门只能创建一次。");
                cm.dispose();
            }

        } else if (selection == 1) {//收徒开关
			if (cm.getPlayer().getBossLogS("创建师门") == 0 ) {
				cm.sendOk("你还没有创建师门呢！");
                cm.dispose();
            }
            else if (cm.getBossRank("收徒开关",2) > 0) {
				cm.setBossRank("收徒开关",2,-1);
                cm.dispose();
				cm.openNpc(9900004,"师徒系统")
            } else {
                cm.setBossRank("收徒开关",2,1);
                cm.dispose();
				cm.openNpc(9900004,"师徒系统")
            }

        } else if (selection == 2) {
			if (cm.getPlayer().getBossLogS("创建师门") == 0) {
				cm.sendOk("你还没有创建师门呢！");
                cm.dispose();
            } else if (cm.getPlayer().getBossLogD("全服推荐师傅") > 0) {
				cm.sendOk("你的名字已经在全服推荐师傅榜单上！点击 徒弟选项>推荐师傅 查看。");
                cm.dispose();
			} else {
				cm.sendYesNo("想要收更多的徒弟吗？只需要花费500点券，即可上榜全服推荐师傅一天，所有的玩家都可以看到你！");
				nx = 2;
			}
		} else if (selection == 4) {//逐出师门
            var text = "\t点击选择徒弟即可逐出师门。\r\n\r\n";
			text += "\t我未出师的徒弟：#n\r\n\r\n";
            var rankinfo_list = getBossRankTop("师傅ID",2);
			var arr = Array();
			for (var i = 0;i < rankinfo_list.length; i = i + 2){
				if (rankinfo_list[i] == cm.getPlayer().getId()){
					arr.push(cm.getCharacterNameById(rankinfo_list[i+1]));
				}
			}
			if (arr != null) {
                for (var i = 0; i < arr.length; i++) {
                var info = arr[i];
				var 等级 = cm.getCharacterByNameLevel(info);
				if (cm.getBossRank(cm.getCharacterIdByName(info),"师傅ID",1) == 0){
				text += "\t #L"+i+"##r" + (i + 1) + "#k#n. ";
				text += info + " \t";
				text += "\t#bLv." + 等级 + "";
				text += "#k";
				} 
				if (等级 >= 出师等级){
					text += "\t#r可出师#l";
					text += "#k";	
				} 
				if (等级 < 出师等级){
					text += "\t#d未出师#l";
					text += "#k";	
				}
				text += "\r\n";
			}
        } else {
			text += "你还没有徒弟\r\n";
			
		}
			nx = 4;
            cm.sendOkS(text,2);
		
        } else if (selection == 3) {//我的徒弟
            var text = "\t点击选择徒弟即可让他出师，徒弟等级大于 [#r" + 出师等级 + "#k] 级，即可出师。\r\n\r\n";
				text += "\t我的徒弟：#n\r\n\r\n";
                var rankinfo_list = getBossRankTop("师傅ID",2);
				var arr = Array();
				for (var i = 0;i < rankinfo_list.length; i = i + 2){
					if (rankinfo_list[i] == cm.getPlayer().getId()){
						arr.push(cm.getCharacterNameById(rankinfo_list[i+1]));
					}
				}
                if (arr != null) {
                    for (var i = 0; i < arr.length; i++) {
                        var info1 = arr[i];
						var 等级 = cm.getCharacterByNameLevel(info1);
                        text += "\t #L"+i+"##r" + (i + 1) + "#k#n. ";
                        text += info1 + " \t";
                        text += "\t#bLv." + 等级 + "";
                        text += "#k";
						if (cm.getBossRank(cm.getCharacterIdByName(info1),"师傅ID",1) > 0){
							text += "\t#n已出师#l";
							text += "#k";	
						} else 
						if (等级 > 出师等级){
							text += "\t#r可出师#l";
							text += "#k";	
						} 
						else {
							text += "\t#d未出师#l";
							text += "#k";	
						}
                        text += "\r\n";
                    }
                } else {
					text += "你还没有徒弟\r\n";
			
				}	
				nx = 3;
                cm.sendOkS(text,2);
        } else if (selection == 5) {//师傅每日
            if (cm.getPlayer().getBossLogS("创建师门") != 1) {
                cm.sendOk("只有师傅才能获得该奖励");
                cm.dispose();
            } else if (cm.getBossLog("师傅每日奖励") > 0) {
                cm.sendOk("今日的已领取，请隔日后再来。");
                cm.dispose();
            } else {
                cm.sendYesNo("师傅资质越高，每日可领取的奖励越好。\r\n是否要领取今日的奖励？");
                nx = 1;
            }
        } else if (selection == 11) {//推荐师傅

            var text = "\t这里是系统推荐师傅，请选择你想要拜入的师门。\r\n\r\n";
				text += "\t推荐师傅列表：#n\r\n\r\n";
                var rankinfo_list = cm.getBosslogDCidTop("全服推荐师傅");
                    for (var i = 0; i < rankinfo_list.length; i++) {
						var info2 = rankinfo_list[i];
						var 师傅评级 = Math.round(cm.getBossRank(info2,"师傅评级",1)/cm.getBossRank(info2,"师傅评级",2));
						if (师傅评级 == 1) {
							var 打分 = ""+wjx+"#l";
						} else if (师傅评级 == 2) {
							var 打分 = ""+wjx+wjx+"#l";
						} else if (师傅评级 == 3) {
							var 打分 = ""+wjx+wjx+wjx+"#l";
						} else if (师傅评级 == 4) {
							var 打分 = ""+wjx+wjx+wjx+wjx+"#l";
						} else if (师傅评级 == 5) {
							var 打分 = ""+wjx+wjx+wjx+wjx+wjx+"#l";
						} else {
							var 打分 = ""+kx+"#l";
						}
						var 全服推荐师傅名字 = cm.getCharacterNameById(info2);
						var 等级 = cm.getCharacterByNameLevel(全服推荐师傅名字);
                        text += "#L"+i+"#" + (i + 1) + "#n. ";
                        text += 全服推荐师傅名字 + " \t";
                        text += "\t#bLv." + 等级 + " \t";
						text += "\t#r好评率." + 打分 + " \t";
                        text += "#k";
                        text += "\r\n";
                    //}
                }
				nx = 111;
                cm.sendOkS(text,2);
        
        }/* else if (selection == 4) {//出师奖励
            var gender = cm.getPlayer().getGender();
            var mapId = cm.getPlayer().getMapId();
            var next = true;
            var party = cm.getPlayer().getParty().getMembers();
            var it = party.iterator();
            while (it.hasNext()) {
                var cPlayer = it.next();
                victim = cm.getPlayer().getMap().getCharacterById(cPlayer.getId());
                if ((party.size() > 2 || victim == null || victim.getMapId() != mapId)) {
                    next = false;
                    break;
                }
            }

            if (!next) {
                cm.sendNext("请保证你的队伍满足以下要求：\r\n1.队伍内只有 2 名玩家。\r\n2.你和你的队友都在同一张地图内。");
                cm.dispose();
                return;
            } else if (cm.getPlayer().getBossLog("收徒_1") == victim.getId() || cm.getPlayer().getBossLog("收徒_2") == victim.getId() || cm.getPlayer().getBossLog("收徒_3") == victim.getId()) {
                cm.sendNext("很好，你现在要带领你的徒弟 " + getname(victim.getId()) + " 进行出师仪式吗？");
                nx = 5;
            } else {
                cm.sendNext("请和徒弟组队后，再来找我。");
                cm.dispose();
            }

        }*/ else if (selection == 12) {//退出师门
			if (cm.getPlayer().getBossLogS("创建师门") > 0) {
                cm.sendOk("你是来搞笑的吗？");
                cm.dispose();
            } else if (cm.getBossRankCount("师傅ID") ==0) {
                cm.sendOk("你还没有师傅，无需退出师门。");
                cm.dispose();
            } else {
                cm.sendYesNo("是否要退出 " + cm.getCharacterNameById(cm.getBossRankCount("师傅ID")) + " 的师门？");
                nx = 12;
            }

        } /*else if (selection == 6) {//徒弟每日奖励
            if (cm.getPlayer().getBossLog("师傅") == 0) {
                cm.sendOk("你还没有师傅，无法领取。");
                cm.dispose();
            } else if (cm.getPlayer().getBossLog("创建师门") == 1) {
                cm.sendOk("你在搞笑吗？");
                cm.dispose();
            } else if (cm.getPlayer().getBossLog("徒弟等级奖励") > 0) {
                cm.sendOk("你已经领取过了，无法再次领取。");
                cm.dispose();
            } else {
                cm.sendYesNo("是否要领取徒弟等级奖励？");
                nx = 3;
            }

        } */else if (selection == 10) {//我要拜师
			if (cm.getPlayer().getLevel() < 最低拜师等级) {
                cm.sendOk("你的等级不足" + 最低拜师等级 + "级，无法拜师。");
                cm.dispose();
                return;
            }
            if (cm.getPlayer().getLevel() >= 最高拜师等级) {
                cm.sendOk("你的等级超过" + 最高拜师等级 + "级，无法拜师。");
                cm.dispose();
                return;
            }

            if (cm.getBossRankCount("师傅ID") > 0) {
                cm.sendOk("你已经有师傅了，无法重复拜师");
                cm.dispose();
                return;
            }
            cm.sendGetText("请输入要拜师的角色名字。");
			nx = 10;
        } else if (selection == 13) {
			cm.sendNext("恭喜你出师了！请为你的师傅进行星级评价:\r\n#L1#"+wjx+"星师傅\r\n#L2#"+wjx+wjx+"星师傅\r\n#L3#"+wjx+wjx+wjx+"星师傅\r\n#L4#"+wjx+wjx+wjx+wjx+"星师傅\r\n#L5#"+wjx+wjx+wjx+wjx+wjx+"星师傅");
			nx = 13;
		} else if (selection == 14) {
			if (cm.getPlayer().getBossLogS("师徒等级奖励") > 0) {
				cm.sendOk("你已经领取过了，请勿重复领取");
                cm.dispose();
			}
			else if (cm.canHoldByTypea(2,2) && cm.canHoldByTypea(5,1)) {
				cm.gainItem(5062000,100);
				cm.gainItem(4002003,50);
				cm.gainItem(5220010,10);
				cm.setBossLog("师徒等级奖励");
				cm.sendOk("领取成功！");
                cm.dispose();
			} else {
				cm.sendOk("你的背包空间不足，请清理背包后尝试！");
                cm.dispose();
			}
		} else if (selection == 90) {
            cm.sendOk(拜师收徒介绍);
            cm.dispose();
        } else if (selection == 91) {
            var text = "\r\n";
            text += " 	#e#d┏━━━━━━━━师傅每日奖励━━━━━━━┓#n#k\r\n\r\n"
            text += "	  #r[普通教师]：每日可领取#k\r\n"
            for (var i = 0; i < 师傅每日1级奖励.length; i++) {
                text += "			#d#z" + 师傅每日1级奖励[i][0] + "##k x #b" + 师傅每日1级奖励[i][1] + "";
				text += " #d点券#k x #b500#k #d抵用券#k x #b500#k\r\n";
            }
            text += "	  #r[为人师表]：每日可领取#k\r\n"
            for (var i = 0; i < 师傅每日2级奖励.length; i++) {
                text += "			#d#z" + 师傅每日2级奖励[i][0] + "##k x #b" + 师傅每日2级奖励[i][1] + "#k";
				text += " #d点券#k x #b1000#k #d抵用券#k x #b1000#k\r\n";
            }
            text += "	  #r[循循善诱]：每日可领取#k\r\n"
            for (var i = 0; i < 师傅每日3级奖励.length; i++) {
                text += "			#d#z" + 师傅每日3级奖励[i][0] + "##k x #b" + 师傅每日3级奖励[i][1] + "#k";
				text += " #d点券#k x #b1500#k #d抵用券#k x #b1500#k\r\n";
            }
            text += "	  #r[诲人不倦]：每日可领取#k\r\n"
            for (var i = 0; i < 师傅每日4级奖励.length; i++) {
                text += "			#d#z" + 师傅每日4级奖励[i][0] + "##k x #b" + 师傅每日4级奖励[i][1] + "#k";
				text += " #d点券#k x #b2000#k #d抵用券#k x #b2000#k\r\n";
            }
            text += "	  #r[厚得树人]：每日可领取#k\r\n"
            for (var i = 0; i < 师傅每日5级奖励.length; i++) {
                text += "			#d#z" + 师傅每日5级奖励[i][0] + "##k x #b" + 师傅每日5级奖励[i][1] + "#k";
				text += " #d点券#k x #b2500#k #d抵用券#k x #b2500#k\r\n";
            }
            text += "	  #r[桃李天下]：每日可领取#k\r\n"
            for (var i = 0; i < 师傅每日6级奖励.length; i++) {
                text += "			#d#z" + 师傅每日6级奖励[i][0] + "##k x #b" + 师傅每日6级奖励[i][1] + "#k";
				text += " #d点券#k x #b3000#k #d抵用券#k x #b3000#k\r\n";
            }
            text += "	#e#d┗━━━━━━━━━━ ━━━━━━━━━━┛#n#k\r\n"
            text += " 	#e#d┏━━━━━━━━徒弟可以领取━━━━━━━┓#n#k\r\n\r\n"
            text += "	  #r[徒弟等级达到" + 出师等级 + "]：可领取#k\r\n"
            for (var i = 0; i < 徒弟等级奖励.length; i++) {
                text += "			#d#z" + 徒弟等级奖励[i][0] + "##k x #b" + 徒弟等级奖励[i][1] + "#k\r\n";
            }
            text += "	  #r[徒弟成功出师]：师徒双方可领取#k\r\n"
            for (var i = 0; i < 出师奖励.length; i++) {
                text += "			#d#d点券#k x #b10000#k #d抵用券#k x #b10000#k\r\n";
            }
            text += "	#e#d┗━━━━━━━━━━ ━━━━━━━━━━┛#n#k\r\n"
            cm.sendSimple(text);
            cm.dispose();
        }
    
    
    } else if (status == 2) {
        if (nx == 0) {
            if (cm.getPlayer().getBossLog("收徒_1") > 0) {
                var 显示 = "#L3#将 " + getname(cm.getPlayer().getBossLog("收徒_1")) + " 逐出师门！\r\n"
            } else {
                var 显示 = "";
            }
            if (cm.getPlayer().getBossLog("收徒_2") > 0) {
                var 显示1 = "#L4#将 " + getname(cm.getPlayer().getBossLog("收徒_2")) + " 逐出师门！\r\n"
            } else {
                var 显示1 = "";
            }
            if (cm.getPlayer().getBossLog("收徒_3") > 0) {
                var 显示2 = "#L5#将 " + getname(cm.getPlayer().getBossLog("收徒_3")) + " 逐出师门！\r\n"
            } else {
                var 显示2 = "";
            }
            var text = "请选择所要逐出师门的弟子：\r\n#r注：选择后结果将不可逆，请谨慎选择。#k\r\n";
            text += 显示;
            text += 显示1;
            text += 显示2;
            cm.sendSimple(text);



        } else if (nx == 1) {//师傅每日奖励
            if (教学等级 == 1) {
                if (cm.getBossLog("师傅每日奖励") > 0) {
                    cm.sendOk("今日奖励已领取，请明日再来。")
                    cm.dispose();
                } else {
                    for (var i = 0; i < 师傅每日1级奖励.length; i++) {
                        cm.gainItem(师傅每日1级奖励[i][0], 师傅每日1级奖励[i][1]);
                    }
					cm.gainNX(500 * 教学等级);
					cm.gainNX2(500 * 教学等级);
                    cm.setBossLog("师傅每日奖励");
                    cm.sendOk("领取成功。");
                    cm.dispose();
                }

            } else if (教学等级 == 2) {
                if (cm.getBossLog("师傅每日奖励") > 0) {
                    cm.sendOk("今日奖励已领取，请明日再来。")
                    cm.dispose();
                } else {
                    for (var i = 0; i < 师傅每日2级奖励.length; i++) {
                        cm.gainItem(师傅每日2级奖励[i][0], 师傅每日2级奖励[i][1]);
                    }
					cm.gainNX(500 * 教学等级);
					cm.gainNX2(500 * 教学等级);
                    cm.setBossLog("师傅每日奖励");
                    cm.sendOk("领取成功。");
                    cm.dispose();
                }

            } else if (教学等级 == 3) {
                if (cm.getBossLog("师傅每日奖励") > 0) {
                    cm.sendOk("今日奖励已领取，请明日再来。")
                    cm.dispose();
                } else {
                    for (var i = 0; i < 师傅每日3级奖励.length; i++) {
                        cm.gainItem(师傅每日3级奖励[i][0], 师傅每日3级奖励[i][1]);
                    }
					cm.gainNX(500 * 教学等级);
					cm.gainNX2(500 * 教学等级);
                    cm.setBossLog("师傅每日奖励");
                    cm.sendOk("领取成功。");
                    cm.dispose();
                }

            } else if (教学等级 == 4) {
                if (cm.getBossLog("师傅每日奖励") > 0) {
                    cm.sendOk("今日奖励已领取，请明日再来。")
                    cm.dispose();
                } else {
                    for (var i = 0; i < 师傅每日4级奖励.length; i++) {
                        cm.gainItem(师傅每日4级奖励[i][0], 师傅每日4级奖励[i][1]);
                    }
					cm.gainNX(500 * 教学等级);
					cm.gainNX2(500 * 教学等级);
                    cm.setBossLog("师傅每日奖励");
                    cm.sendOk("领取成功。");
                    cm.dispose();
                }

            } else if (教学等级 == 5) {
                if (cm.getBossLog("师傅每日奖励") > 0) {
                    cm.sendOk("今日奖励已领取，请明日再来。")
                    cm.dispose();
                } else {
                    for (var i = 0; i < 师傅每日5级奖励.length; i++) {
                        cm.gainItem(师傅每日5级奖励[i][0], 师傅每日5级奖励[i][1]);
                    }
					cm.gainNX(500 * 教学等级);
					cm.gainNX2(500 * 教学等级);
                    cm.setBossLog("师傅每日奖励");
                    cm.sendOk("领取成功。");
                    cm.dispose();
                }

            } else if (教学等级 == 6) {
                if (cm.getBossLog("师傅每日奖励") > 0) {
                    cm.sendOk("今日奖励已领取，请明日再来。")
                    cm.dispose();
                } else {
                    for (var i = 0; i < 师傅每日6级奖励.length; i++) {
                        cm.gainItem(师傅每日6级奖励[i][0], 师傅每日6级奖励[i][1]);
                    }
					cm.gainNX(500 * 教学等级);
					cm.gainNX2(500 * 教学等级);
                    cm.setBossLog("师傅每日奖励");
                    cm.sendOk("领取成功。");
                    cm.dispose();
                }
            }
            } else if (nx == 2) {
				if (cm.getPlayer().getCSPoints(1) < 500){
					cm.sendOk("你的点券不足。");
                    cm.dispose();
				} else {
					cm.gainNX(-500);
					cm.setBossLog("全服推荐师傅");
					cm.sendOk("推荐成功！现在所有玩家都可以在推荐师傅列表下直接拜你为师！");
                    cm.dispose();
				}
			} else if (nx == 12) {	//退出师门
				if (cm.getBossLog("退出师门") > 0){
					cm.sendOk("你今天已经退出过一次师门了，请明天再试。");
					cm.dispose();
				}
				var 师傅ID = cm.getBossRankCount("师傅ID");
				cm.小纸条("" + cm.getCharacterNameById(师傅ID) + "", "[师徒系统]:你的徒弟： " + cm.getChar().getName() + " 已退出师门！");
                cm.setBossRankCount("师傅ID",-师傅ID);
				cm.setBossLog("退出师门");
                cm.sendOk("你已退出师门。");
                cm.dispose();
            } else if (nx == 13){
				var 师傅ID = cm.getBossRankCount("师傅ID");
				cm.setBossRank(师傅ID,cm.getCharacterNameById(师傅ID),"师傅评级",1,selection);
				cm.setBossRank(师傅ID,cm.getCharacterNameById(师傅ID),"师傅评级",2,1);
				cm.setBossLog("师傅评分");
				cm.gainNX(10000);
				cm.gainNX2(10000);
				cm.sendOk("感谢你的评价！");
                cm.dispose();
			} else if (nx == 111) { //推荐师傅
				var rankinfo_list = cm.getBosslogDCidTop("全服推荐师傅");
                var info2 = rankinfo_list[selection];
				var 全服推荐师傅名字 = cm.getCharacterNameById(info2);
				if (cm.getPlayer().getLevel() < 最低拜师等级) {
					cm.sendOk("你的等级不足" + 最低拜师等级 + "级，无法拜师。");
					cm.dispose();
					return;
				} else if (cm.getPlayer().getLevel() >= 最高拜师等级) {
					cm.sendOk("你的等级超过" + 最高拜师等级 + "级，无法拜师。");
					cm.dispose();
					return;
				} else if (cm.getBossRankCount("师傅ID") > 0) {
					cm.sendOk("你已经有师傅了，无法重复拜师");
					cm.dispose();
					return;
				} else if (cm.getPlayer().getId() == info2) {
					cm.sendOk("你不能拜自己为师！");
					cm.dispose();
					return;
				}else if (cm.getPlayer().getBossLogS("创建师门") > 0){
					cm.sendOk("你已经是师傅了啊！");
					cm.dispose();
					return;
				} 
				cm.sendYesNo("你确定要拜入"+全服推荐师傅名字+"的师门下吗？");
				select2 = selection;
				nx = 1111;
			} else if (nx == 3) {//徒弟出师
				var rankinfo_list = cm.getBossRankTop("师傅ID",2);
				var arr = Array();
				for (var i = 0;i < rankinfo_list.size(); i++){
					if (rankinfo_list.get(i).getCount() == cm.getPlayer().getId()){
						arr.push(rankinfo_list.get(i).getCname());
					}
				}
				var info1 = arr[selection];
				if(cm.getCharacterByNameLevel(info1) < 出师等级){
					cm.sendOk("" + info1 + "的等级不足" + 出师等级 + "级，尚不能出师，还需加强锻炼！");
					cm.dispose();
				} 
				else if (cm.getBossRank(cm.getCharacterIdByName(info1),"师傅ID",1) > 0){
					cm.sendOk("" + info1 + "已经出师过了，不能重复出师！");
					cm.dispose();
				} else {
					cm.setBossRank(cm.getCharacterIdByName(info1),info1,"师傅ID",1,1);
					cm.getPlayer().setBossLog("出师积分");
					cm.gainNX(10000);
					cm.gainNX2(10000);
					cm.worldMessage(5,"【师徒系统】：经过" + cm.getPlayer().getName() + "的深思熟虑，其爱徒" + info1 + "已经可以独当一面了，成功带徒出师。")
					cm.worldMessage(4,"【师徒系统】：经过" + cm.getPlayer().getName() + "的深思熟虑，其爱徒" + info1 + "已经可以独当一面了，成功带徒出师。")
					cm.小纸条("" + info1 + "", "[师徒系统]:你的师傅： " + cm.getChar().getName() + " 已准许你出师，去开拓更广阔的未来吧！");
					cm.dispose();
				}
            } else if (nx == 4) {
				var rankinfo_list = cm.getBossRankTop("师傅ID",2);
				var arr = Array();
				for (var i = 0;i < rankinfo_list.size(); i++){
					if (rankinfo_list.get(i).getCount() == cm.getPlayer().getId()){
						arr.push(rankinfo_list.get(i).getCname());
					}
				}
				var info = arr[selection];
				cm.sendYesNo("是否要将    #r" + info + "#k    #bLv." + cm.getCharacterByNameLevel(info)+ "级#k    逐出师门?");
				select1 = selection;
				nx = 41;
            } else if (nx == 5) {
                if (cm.getPlayer().getBossLog("收徒_1") == victim.getId()) {
                    var 显示 = "   #L6#带领徒弟 " + getname(cm.getPlayer().getBossLog("收徒_1")) + " 出师。\r\n"
                } else {
                    var 显示 = "";
                }
                if (cm.getPlayer().getBossLog("收徒_2") == victim.getId()) {
                    var 显示1 = "   #L7#带领徒弟 " + getname(cm.getPlayer().getBossLog("收徒_2")) + " 出师。\r\n"
                } else {
                    var 显示1 = "";
                }
                if (cm.getPlayer().getBossLog("收徒_3") == victim.getId()) {
                    var 显示2 = "   #L8#带领徒弟 " + getname(cm.getPlayer().getBossLog("收徒_3")) + " 出师。\r\n"
                } else {
                    var 显示2 = "";
                }
                var text = "请选择准备出师的徒弟：\r\n";
                text += 显示;
                text += 显示1;
                text += 显示2;
                cm.sendSimple(text);
            } else if (nx == 10) {
				var text = cm.getText();
				if (text === null || text === "") {
					cm.sendOk("并未输入任何内容.");
					cm.dispose();
					return;
				}
				if (cm.getPlayer().getName() == text) {
					cm.sendOk("不可拜自己为师！");
					cm.dispose();
					return;
				}
				var id = cm.getCharacterIdByName(text);
				if (id == -1) {
					cm.sendOk("你輸入的名字不存在。");
					cm.dispose();
					return;
				}
				if (cm.getBossRank(id,"收徒开关",2) < 1){
					cm.sendOk("对方收徒开关已关闭，今日不收徒了。");
					cm.dispose();
					return;
				}
				cm.setBossRankCount("师傅ID",id);
				cm.setBossRank(id,text,"徒弟数量",2,1);
				cm.sendOk("拜师成功。");
				cm.小纸条("" + text + "", "[师徒系统]:玩家 " + cm.getChar().getName() + " 已拜你为师，请好好培养他，成为一代新星！");
                cm.worldMessage(6, "【师徒系统】： " + cm.getChar().getName() + " 拜 " + text + " 为师，大家恭喜他们~");				
				cm.dispose();
			}
            
        } else if (status == 3) {
            if (selection == 0) {
                var gender = cm.getPlayer().getGender();
                var mapId = cm.getPlayer().getMapId();
                var next = true;
                var party = cm.getPlayer().getParty().getMembers();
                var it = party.iterator();
                while (it.hasNext()) {
                    var cPlayer = it.next();
                    victim = cm.getPlayer().getMap().getCharacterById(cPlayer.getId());
                    if (party.size() != 2 || victim.getMapId() != cm.getPlayer().getMapId()) {
                        next = false;
                        break;
                    }
                }

                if (!next) {
                    cm.sendNext("请保证你的队伍满足以下要求：\r\n1.队伍内只有 2 名玩家。\r\n2.你和你的队友都在同一张地图内。");
                    cm.dispose();
                    return;
                } else if (cm.getParty() == null) {
                    cm.sendNext("请组队后在来找我！");
                    cm.dispose();
                    return;
                } else if (cm.getPlayer().getBossLogS("创建师门") == 0) {
                    cm.sendNext("请让师傅跟我对话。");
                    cm.dispose();
                    return;
                } else if (victim.getBossLogS("师傅") > 0) {
                    cm.sendOk("你的徒弟已经拜过师傅了。");
                    cm.dispose();
                    return;
                } else if (victim.getLevel() > 100) {
                    cm.sendOk("你的队友等级超过 100 级，无法拜入师门。");
                    cm.dispose();
                    return;
                } else if (victim.getBossLogS("师傅") == cm.getPlayer().getId()) {
                    cm.sendOk("你已经收过这个徒弟了");
                    cm.dispose();
                    return;
                } else if (cm.getPlayer().getParty().getMembers().size() > 2) {
                    cm.sendOk("每次只能带一个徒弟入门（请2人组队）");
                    cm.dispose();
                    return;
                } else {
                    cm.getPlayer().setBossLog("收徒_1", 1, victim.getId());
                    victim.setBossLog("师傅", 1, cm.getPlayer().getId());
                    cm.sendOk("你成功收了" + victim.getName() + "为徒弟。");
                    cm.worldMessage(6, "【师徒系统】：[" + cm.getChar().getName() + "]将[" + victim.getName() + "]收为徒弟，大家恭喜他们~");
                    cm.dispose();
                    return;
                }

            } else if (nx == 41){//逐出师门
				var rankinfo_list = cm.getBossRankTop("师傅ID",2);
				var arr = Array();
				for (var i = 0;i < rankinfo_list.size(); i++){
					if (rankinfo_list.get(i).getCount() == cm.getPlayer().getId()){
						arr.push(rankinfo_list.get(i).getCname());
					}
				}
				var info = arr[select1];
				var 我的ID = cm.getPlayer().getId();
				cm.sendOk("你已愤怒的将" + info + "逐出了师门。");
				cm.小纸条("" + info + "", "[师徒系统]:玩家 " + cm.getChar().getName() + " 已将你逐出师门！");
				cm.setBossRank(cm.getCharacterIdByName(info),info,"师傅ID",2,-我的ID);
				cm.dispose();
			} else if (nx == 1111){//推荐师傅
				var rankinfo_list = cm.getBosslogDCidTop("全服推荐师傅");
                var info2 = rankinfo_list[select2];
				cm.setBossRankCount("师傅ID",info2);
				cm.setBossRank(info2,cm.getCharacterNameById(info2),"徒弟数量",2,1);
				cm.sendOk("你已加入" + cm.getCharacterNameById(info2) + "的师门下。");
				cm.小纸条("" + cm.getCharacterNameById(info2) + "", "[师徒系统]:玩家 " + cm.getChar().getName() + " 已拜你为师，请好好培养他，成为一代新星！");
				cm.worldMessage(6, "【师徒系统】：" + cm.getChar().getName() + " 拜 " + cm.getCharacterNameById(info2)+ " 为师，大家恭喜他们~");				
				cm.dispose();
			} else if (selection == 1) {
                var gender = cm.getPlayer().getGender();
                var mapId = cm.getPlayer().getMapId();
                var next = true;
                var party = cm.getPlayer().getParty().getMembers();
                var it = party.iterator();
                while (it.hasNext()) {
                    var cPlayer = it.next();
                    victim = cm.getPlayer().getMap().getCharacterById(cPlayer.getId());
                    if (party.size() != 2 || victim.getMapId() != cm.getPlayer().getMapId()) {
                        next = false;
                        break;
                    }
                }

                if (!next) {
                    cm.sendNext("请保证你的队伍满足以下要求：\r\n1.队伍内只有 2 名玩家。\r\n2.你和你的队友都在同一张地图内。");
                    cm.dispose();
                    return;
                } else if (cm.getParty() == null) {
                    cm.sendNext("请组队后在来找我！");
                    cm.dispose();
                    return;
                } else if (cm.getPlayer().getBossLog("创建师门") == 0) {
                    cm.sendNext("请让师傅跟我对话。");
                    cm.dispose();
                    return;
                } else if (victim.getLevel() > 50) {
                    cm.sendOk("你的队友等级超过 50 级，无法拜入师门。");
                    cm.dispose();
                    return;
                } else if (victim.getBossLog("师傅") == cm.getPlayer().getId()) {
                    cm.sendOk("你已经收过这个徒弟了");
                    cm.dispose();
                    return;
                } else if (victim.getBossLog("师傅") > 0) {
                    cm.sendOk("你的徒弟已经拜过师傅了。");
                    cm.dispose();
                    return;
                } else if (cm.getPlayer().getParty().getMembers().size() > 2) {
                    cm.sendOk("每次只能带一个徒弟入门（请2人组队）");
                    cm.dispose();
                    return;
                } else {
                    cm.getPlayer().setBossLog("收徒_2", 1, victim.getId());
                    victim.setBossLog("师傅", 1, cm.getPlayer().getId());
                    cm.sendOk("你成功收了" + victim.getName() + "为徒弟。");
                    cm.worldMessage(6, "【师徒系统】：[" + cm.getChar().getName() + "]将[" + victim.getName() + "]收为徒弟，大家恭喜他们~");
                    cm.dispose();
                    return;
                }

            } else if (selection == 2) {
                var gender = cm.getPlayer().getGender();
                var mapId = cm.getPlayer().getMapId();
                var next = true;
                var party = cm.getPlayer().getParty().getMembers();
                var it = party.iterator();
                while (it.hasNext()) {
                    var cPlayer = it.next();
                    victim = cm.getPlayer().getMap().getCharacterById(cPlayer.getId());
                    if (party.size() != 2 || victim.getMapId() != cm.getPlayer().getMapId()) {
                        next = false;
                        break;
                    }
                }

                if (!next) {
                    cm.sendNext("请保证你的队伍满足以下要求：\r\n1.队伍内只有 2 名玩家。\r\n2.你和你的队友都在同一张地图内。");
                    cm.dispose();
                    return;
                } else if (cm.getParty() == null) {
                    cm.sendNext("请组队后在来找我！");
                    cm.dispose();
                    return;
                } else if (cm.getPlayer().getBossLog("创建师门") == 0) {
                    cm.sendNext("请让师傅跟我对话。");
                    cm.dispose();
                    return;
                } else if (victim.getLevel() > 50) {
                    cm.sendOk("你的队友等级超过 50 级，无法拜入师门。");
                    cm.dispose();
                    return;
                } else if (victim.getBossLog("师傅") > 0) {
                    cm.sendOk("你的徒弟已经拜过师傅了。");
                    cm.dispose();
                    return;
                } else if (victim.getBossLog("师傅") == cm.getPlayer().getId()) {
                    cm.sendOk("你已经收过这个徒弟了");
                    cm.dispose();
                    return;
                } else if (cm.getPlayer().getParty().getMembers().size() > 2) {
                    cm.sendOk("每次只能带一个徒弟入门（请2人组队）");
                    cm.dispose();
                    return;
                } else {
                    cm.getPlayer().setBossLog("收徒_3", 1, victim.getId());
                    victim.setBossLog("师傅", 1, cm.getPlayer().getId());
                    cm.sendOk("你成功收了" + victim.getName() + "为徒弟。");
                    cm.worldMessage(6, "【师徒系统】：[" + cm.getChar().getName() + "]将[" + victim.getName() + "]收为徒弟，大家恭喜他们~");
                    cm.dispose();
                    return;
                }
            } else if (selection == 3) {
                cm.sendYesNo("是否要将 " + getname(cm.getPlayer().getBossLog("收徒_1")) + " 逐出师门？");
                nx1 = 1;
            } else if (selection == 4) {
                cm.sendYesNo("是否要将 " + getname(cm.getPlayer().getBossLog("收徒_2")) + " 逐出师门？");
                nx1 = 2;
            } else if (selection == 5) {
                cm.sendYesNo("是否要将 " + getname(cm.getPlayer().getBossLog("收徒_3")) + " 逐出师门？");
                nx1 = 3;

            } else if (selection == 6) {
                var gender = cm.getPlayer().getGender();
                var mapId = cm.getPlayer().getMapId();
                var next = true;
                var party = cm.getPlayer().getParty().getMembers();
                var it = party.iterator();
                while (it.hasNext()) {
                    var cPlayer = it.next();
                    victim = cm.getPlayer().getMap().getCharacterById(cPlayer.getId());
                    if (party.size() != 2 || victim.getMapId() != cm.getPlayer().getMapId()) {
                        next = false;
                        break;
                    }
                }

                if (!next) {
                    cm.sendNext("请保证你的队伍满足以下要求：\r\n1.队伍内只有 2 名玩家。\r\n2.你和你的队友都在同一张地图内。");
                    cm.dispose();
                    return;
                } else if (cm.getParty() == null) {
                    cm.sendNext("请组队后在来找我！");
                    cm.dispose();
                    return;
                } else if (cm.getPlayer().getBossLog("创建师门") == 0) {
                    cm.sendNext("请让师傅跟我对话。");
                    cm.dispose();
                    return;
                } else if (victim.getLevel() < 出师等级) {
                    cm.sendOk("你的徒弟等级低于 " + 出师等级 + " 级，无法出师。");
                    cm.dispose();
                    return;
                } else if (victim.getBossLog("出师记录") > 0) {
                    cm.getPlayer().setBossLog("收徒_1", 1, -cm.getPlayer().getBossLog("收徒_1"));
                    cm.sendOk("您的徒弟已出师，或以跟别的师傅出师，无法获得出师奖励。");
                    cm.dispose();
                    return;
                } else {//====================================================出师奖励添加到这里↓↓↓↓↓
                    cm.getPlayer().setBossLog("出师积分");
                    cm.getPlayer().setBossLog("徒弟");
                    cm.getPlayer().setBossLog("收徒_1", 1, -cm.getPlayer().getBossLog("收徒_1"));
                    victim.setBossLog("师傅", 1, -victim.getBossLog("师傅"));
                    victim.setBossLog("出师记录");
                    for (var i = 0; i < 出师奖励.length; i++) {
                        victim.gainItem(出师奖励[i][0], 出师奖励[i][1]);
                    }
                    cm.sendOk("恭喜你出师成功");
                    cm.dispose();
                    return;
                }
            } else if (selection == 7) {
                var gender = cm.getPlayer().getGender();
                var mapId = cm.getPlayer().getMapId();
                var next = true;
                var party = cm.getPlayer().getParty().getMembers();
                var it = party.iterator();
                while (it.hasNext()) {
                    var cPlayer = it.next();
                    victim = cm.getPlayer().getMap().getCharacterById(cPlayer.getId());
                    if (party.size() != 2 || victim.getMapId() != cm.getPlayer().getMapId()) {
                        next = false;
                        break;
                    }
                }

                if (!next) {
                    cm.sendNext("请保证你的队伍满足以下要求：\r\n1.队伍内只有 2 名玩家。\r\n2.你和你的队友都在同一张地图内。");
                    cm.dispose();
                    return;
                } else if (cm.getParty() == null) {
                    cm.sendNext("请组队后在来找我！");
                    cm.dispose();
                    return;
                } else if (cm.getPlayer().getBossLog("创建师门") == 0) {
                    cm.sendNext("请让师傅跟我对话。");
                    cm.dispose();
                    return;
                } else if (victim.getLevel() < 出师等级) {
                    cm.sendOk("你的徒弟等级低于 " + 出师等级 + " 级，无法出师。");
                    cm.dispose();
                    return;
                } else if (victim.getBossLog("出师记录") > 0) {
                    cm.getPlayer().setBossLog("收徒_2", 1, -cm.getPlayer().getBossLog("收徒_2"));
                    cm.sendOk("您的徒弟已出师，或以跟别的师傅出师，无法获得出师奖励。");
                    cm.dispose();
                    return;
                } else {//====================================================出师奖励添加到这里↓↓↓↓↓
                    cm.getPlayer().setBossLog("出师积分");
                    cm.getPlayer().setBossLog("徒弟");
                    cm.getPlayer().setBossLog("收徒_2", 1, -cm.getPlayer().getBossLog("收徒_2"));
                    victim.setBossLog("师傅", 1, -victim.getBossLog("师傅"));
                    victim.setBossLog("出师记录");
                    for (var i = 0; i < 出师奖励.length; i++) {
                        victim.gainItem(出师奖励[i][0], 出师奖励[i][1]);
                    }
                    cm.sendOk("恭喜你出师成功");
                    cm.dispose();
                    return;
                }
            } else if (selection == 8) {
                var gender = cm.getPlayer().getGender();
                var mapId = cm.getPlayer().getMapId();
                var next = true;
                var party = cm.getPlayer().getParty().getMembers();
                var it = party.iterator();
                while (it.hasNext()) {
                    var cPlayer = it.next();
                    victim = cm.getPlayer().getMap().getCharacterById(cPlayer.getId());
                    if (party.size() != 2 || victim.getMapId() != cm.getPlayer().getMapId()) {
                        next = false;
                        break;
                    }
                }

                if (!next) {
                    cm.sendNext("请保证你的队伍满足以下要求：\r\n1.队伍内只有 2 名玩家。\r\n2.你和你的队友都在同一张地图内。");
                    cm.dispose();
                    return;
                } else if (cm.getParty() == null) {
                    cm.sendNext("请组队后在来找我！");
                    cm.dispose();
                    return;
                } else if (cm.getPlayer().getBossLog("创建师门") == 0) {
                    cm.sendNext("请让师傅跟我对话。");
                    cm.dispose();
                    return;
                } else if (victim.getLevel() < 出师等级) {
                    cm.sendOk("你的徒弟等级低于 " + 出师等级 + " 级，无法出师。");
                    cm.dispose();
                    return;
                } else if (victim.getBossLog("出师记录") > 0) {
                    cm.getPlayer().setBossLog("收徒_3", 1, -cm.getPlayer().getBossLog("收徒_3"));
                    cm.sendOk("您的徒弟已出师，或以跟别的师傅出师，无法获得出师奖励。");
                    cm.dispose();
                    return;
                } else {//====================================================出师奖励添加到这里↓↓↓↓↓
                    cm.getPlayer().setBossLog("出师积分");
                    cm.getPlayer().setBossLog("徒弟");
                    cm.getPlayer().setBossLog("收徒_3", 1, -cm.getPlayer().getBossLog("收徒_3"));
                    victim.setBossLog("师傅", 1, -victim.getBossLog("师傅"));
                    victim.setBossLog("出师记录");
                    for (var i = 0; i < 出师奖励.length; i++) {
                        victim.gainItem(出师奖励[i][0], 出师奖励[i][1]);
                    }
                    cm.sendOk("恭喜你出师成功");
                    cm.dispose();
                    return;
                }
            }
        } else if (status == 4) {
            if (nx1 == 1) {
                cm.sendOk("已将 " + getname(cm.getPlayer().getBossLog("收徒_1")) + " 逐出师门！");
                cm.getPlayer().setBossLog("收徒_1", 1, -cm.getPlayer().getBossLog("收徒_1"));
                cm.dispose();
            } else if (nx1 == 2) {
                cm.sendOk("已将 " + getname(cm.getPlayer().getBossLog("收徒_2")) + " 逐出师门！");
                cm.getPlayer().setBossLog("收徒_2", 1, -cm.getPlayer().getBossLog("收徒_2"));
                cm.dispose();
            } else if (nx1 == 3) {
                cm.sendOk("已将 " + getname(cm.getPlayer().getBossLog("收徒_3")) + " 逐出师门！");
                cm.getPlayer().setBossLog("收徒_3", 1, -cm.getPlayer().getBossLog("收徒_3"));
                cm.dispose();
            }
        }
    }

    function getname(id) {
        var con1 = DatabaseConnection.getConnection();
        ps1 = con1.prepareStatement("SELECT name FROM characters WHERE id = ?");
        ps1.setInt(1, id);
        var rs1 = ps1.executeQuery();
        var name;
        if (rs1.next()) {
            name = rs1.getString("name");
        } else {
            name = "匿名人士";
        }
        rs1.close();
        ps1.close();
        return name;
    }
    
	
function getBossRankTop(bossname,ints) {
	var con = cm.getDataSource().getConnection();
	var count = [];
	var counts = 0;
	var ps = con.prepareStatement("SELECT * FROM bossrank WHERE  bossname = ?");
	ps.setString(1, bossname);
	var ps1 = con.prepareStatement("SELECT COUNT(*) FROM bossrank WHERE  bossname = ?");
	ps1.setString(1, bossname);
	var rs = ps.executeQuery();
	var rs1 = ps1.executeQuery();
	if (rs1.next()) {
        counts = rs1.getInt(1);
	} else {
        counts = -1;
    }
	if (ints == 1) {
		for (var i = 0; i < counts; i++) {
			if (rs.next()) {
				count.push(rs.getInt("points"));
				count.push(rs.getInt("cid"));
			}
		}
	 }
	if (ints == 2) {
		for (var i = 0; i < counts; i++) {
			if (rs.next()) {
				count.push(rs.getInt("count"));
				count.push(rs.getInt("cid"));
			}
		}
	 }
	rs1.close();
	ps1.close();
	rs.close();
	ps.close();
	con.close();
	return count;
}

