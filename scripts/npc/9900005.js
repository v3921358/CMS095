
var status = -1;
var victim;
var name = "PPMS怀旧岛 一 师 徒 系 统";
var 建立师门等级 = 180;
var nx;
var nx1;
var 拜师收徒介绍 = "师徒系统介绍：\r\n\t徒弟可拜师等级：100级以下\r\n\t师傅可收徒等级：180级以上\r\n\t徒弟达到140级即可出师\r\n\r\n#e#r注意：#k#n\r\n\t#b如何收徒：#k由师傅创建一个新的组队，组上徒弟之后，点击师傅选项的我要收徒即可收徒成功。\r\n\t#b等级奖励：#k徒弟达到140级的时候#k可以在徒弟选项处点击等级奖励，建议领取等级奖励之后再找师傅出师，否则会无法领取\r\n\t#b如何出师：#k徒弟达到140级后，师傅新创建一个组队，组上徒弟之后，点击徒弟选项的我要出师即可出师成功\r\n\t#d出师时，奖励是直接发在背包里的，不会提示，请提前确保自己的背包有充足位置。\r\n\r\n#e#r具体奖励内容，请参照师徒奖励介绍";
var 师徒奖励介绍 = ""
var 出师等级 = 140;

var 师傅每日1级奖励 = Array(
        Array(2000005, 10)
        );
var 师傅每日2级奖励 = Array(
        Array(2049100, 5)
        );
var 师傅每日3级奖励 = Array(
        Array(2049100, 8)
        );
var 师傅每日4级奖励 = Array(
        Array(2049116, 2),
        Array(4001017)
        );
var 师傅每日5级奖励 = Array(
        Array(2049116, 5)
        );
var 师傅每日6级奖励 = Array(
        Array(4000463, 2)

        );
var 徒弟等级奖励 = Array(
        Array(2049100, 15),
        Array(2340000, 10)
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
            教学经验 = 6 - cm.getPlayer().getBossLogS("徒弟");
        } else if (cm.getPlayer().getBossLogS("出师积分") >= 6 && cm.getPlayer().getBossLogS("出师积分") <= 10) {
            教学经验 = 11 - cm.getPlayer().getBossLogS("徒弟");
        } else if (cm.getPlayer().getBossLogS("出师积分") >= 11 && cm.getPlayer().getBossLogS("出师积分") <= 20) {
            教学经验 = 21 - cm.getPlayer().getBossLogS("徒弟");
        } else if (cm.getPlayer().getBossLogS("出师积分") >= 21 && cm.getPlayer().getBossLogS("出师积分") <= 40) {
            教学经验 = 41 - cm.getPlayer().getBossLogS("徒弟");
        } else if (cm.getPlayer().getBossLogS("出师积分") >= 41 && cm.getPlayer().getBossLogS("出师积分") <= 999) {
            教学经验 = "☆巅  峰☆";
        }

        var text = "\t\t\t #d" + name + "#k\r\n";
        text += " 	┏━━━━━━━━基 本 信 息━━━━━━━┓#k\r\n"
        text += "					#d您当前有#k [#r" + cm.getPlayer().getBossLogS("徒弟") + "#k]#d 位徒弟\r\n\r\n"
        text += "		您的教学等级为 #k[ #r" + 教学等级 + "#k ]#d 	 称谓 #k[#r" + 称谓 + "#k]#d\r\n\r\n"
        text += "		您的教学经验为#k [ #r" + cm.getPlayer().getBossLogS("徒弟") + "#k ]#d	 还需 #k[ #r" + 教学经验 + "#k ]#d 升级#k\r\n\r\n"
        text += "	┗━━━━━━━━━━ ━━━━━━━━━━┛\r\n"
        text += "				   #r#L0#我要建立师门#l\r\n\r\n"
        text += "		#L90#拜师收徒介绍#l		#L91#师徒奖励介绍#l\r\n\r\n"
        text += "#b"
        text += " 	┏━━━━━━━━师 傅 选 项━━━━━━━┓#k\r\n"
        text += "		#L1#我要收徒#l #L2#逐出师门#l #L3#师傅每日#l#b\r\n\r\n"
        text += "	┗━━━━━━━━━━ ━━━━━━━━━━┛\r\n"
        text += " 	┏━━━━━━━━徒 弟 选 项━━━━━━━┓#k\r\n"
        text += "		#L4#我要出师#l #L5#离开师门#l #L6#徒弟奖励#l#b\r\n\r\n"
        text += "	┗━━━━━━━━━━ ━━━━━━━━━━┛\r\n"
        cm.sendSimple(text);
    } else if (status == 1) {
        if (selection == 0) {//建立师门
            var id = cm.getPlayer().getId();
            if (cm.getPlayer().getLevel() < 建立师门等级) {
                cm.sendOk("你的等级不够 #r" + 建立师门等级 + " #k级。");
                cm.dispose();
                return;
            } else if (cm.getPlayer().getId().getBossLogS("创建师门") == 0) {
                cm.getPlayer().setBossLog("创建师门");
                cm.sendOk("创建师门成功。");
                cm.worldMessage(6, "【师徒系统】[" + cm.getChar().getName() + "]成功建立了师门，要找师傅的赶紧了！");
                cm.dispose();
            } else {
                cm.sendOk("师门只能创建一次。");
                cm.dispose();
            }

        } else if (selection == 1) {//我要收徒
            if (cm.getPlayer().getBossLogS("创建师门") == 0) {
                cm.sendOk("你还没有建立师门。");
                cm.dispose();
            } else {
                cm.sendYesNo("每位师傅最多同时收 3 名徒弟。");
                nx = 4;
            }

        } else if (selection == 2) {//逐出师门
            if (cm.getPlayer().getBossLog("收徒_1") == 0 && cm.getPlayer().getBossLog("收徒_2") == 0 && cm.getPlayer().getBossLog("收徒_3") == 0) {
                cm.sendOk("你还没有收徒。");
                cm.dispose();
            } else {
                cm.sendYesNo("你要将徒弟逐出师门吗？");
                nx = 0;
            }

        } else if (selection == 3) {//师傅每日#l#b
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
        } else if (selection == 4) {//出师奖励
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

        } else if (selection == 5) {//退出师门
            if (cm.getPlayer().getBossLog("师傅") == 0) {
                cm.sendOk("你还没有师傅，无需退出师门。");
                cm.dispose();
            } else if (cm.getPlayer().getBossLog("创建师门") > 0) {
                cm.sendOk("你是来搞笑的吗？");
                cm.dispose();
            } else {
                cm.sendYesNo("是否要退出 " + getname(cm.getPlayer().getBossLog("师傅")) + " 的师门？");
                nx = 2;
            }

        } else if (selection == 6) {//徒弟每日奖励
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

        } else if (selection == 90) {
            cm.sendOk(拜师收徒介绍);
            cm.dispose();
        } else if (selection == 91) {
            var text = "\r\n";
            text += " 	#e#d┏━━━━━━━━师傅每日奖励━━━━━━━┓#n#k\r\n\r\n"
            text += "	  #r[普通教师]：每日可领取#k\r\n"
            for (var i = 0; i < 师傅每日1级奖励.length; i++) {
                text += "			#d#z" + 师傅每日1级奖励[i][0] + "##k x #b" + 师傅每日1级奖励[i][1] + "#k\r\n";
            }
            text += "	  #r[为人师表]：每日可领取#k\r\n"
            for (var i = 0; i < 师傅每日2级奖励.length; i++) {
                text += "			#d#z" + 师傅每日2级奖励[i][0] + "##k x #b" + 师傅每日2级奖励[i][1] + "#k\r\n";
            }
            text += "	  #r[循循善诱]：每日可领取#k\r\n"
            for (var i = 0; i < 师傅每日3级奖励.length; i++) {
                text += "			#d#z" + 师傅每日3级奖励[i][0] + "##k x #b" + 师傅每日3级奖励[i][1] + "#k\r\n";
            }
            text += "	  #r[诲人不倦]：每日可领取#k\r\n"
            for (var i = 0; i < 师傅每日4级奖励.length; i++) {
                text += "			#d#z" + 师傅每日4级奖励[i][0] + "##k x #b" + 师傅每日4级奖励[i][1] + "#k\r\n";
            }
            text += "	  #r[厚得树人]：每日可领取#k\r\n"
            for (var i = 0; i < 师傅每日5级奖励.length; i++) {
                text += "			#d#z" + 师傅每日5级奖励[i][0] + "##k x #b" + 师傅每日5级奖励[i][1] + "#k\r\n";
            }
            text += "	  #r[桃李天下]：每日可领取#k\r\n"
            for (var i = 0; i < 师傅每日6级奖励.length; i++) {
                text += "			#d#z" + 师傅每日6级奖励[i][0] + "##k x #b" + 师傅每日6级奖励[i][1] + "#k\r\n";
            }
            text += "	#e#d┗━━━━━━━━━━ ━━━━━━━━━━┛#n#k\r\n"
            text += " 	#e#d┏━━━━━━━━徒弟可以领取━━━━━━━┓#n#k\r\n\r\n"
            text += "	  #r[徒弟等级达到" + 出师等级 + "]：可领取#k\r\n"
            for (var i = 0; i < 徒弟等级奖励.length; i++) {
                text += "			#d#z" + 徒弟等级奖励[i][0] + "##k x #b" + 徒弟等级奖励[i][1] + "#k\r\n";
            }
            text += "	  #r[徒弟成功出师]：师徒双方可领取#k\r\n"
            for (var i = 0; i < 出师奖励.length; i++) {
                text += "			#d#z" + 出师奖励[i][0] + "##k x #b" + 出师奖励[i][1] + "#k\r\n";
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
                    cm.setBossLog("师傅每日奖励");
                    cm.sendOk("领取成功。");
                    cm.dispose();
                }
            }
        } else if (nx == 2) {	//退出师门
            cm.getPlayer().setBossLog("师傅", 1, -cm.getPlayer().getBossLog("师傅"));
            cm.sendOk("你以退出师门。");
            cm.dispose();
        } else if (nx == 3) {//徒弟150级奖励
            for (var i = 0; i < 徒弟等级奖励.length; i++) {
                cm.gainItem(徒弟等级奖励[i][0], 徒弟等级奖励[i][1]);
            }
            cm.getPlayer().setBossLog("徒弟等级奖励");
            cm.sendOk("领取成功。");
            cm.dispose();
        } else if (nx == 4) {
            if (cm.getPlayer().getBossLogS("收徒_1") > 0) {
                var 显示 = "   1.徒弟名字：" + getCharacterNameById(cm.getPlayer().getBossLogS("收徒_1")) + "\r\n"
            } else {
                var 显示 = "#L0#1.当前徒弟位暂缺，可以收徒#l\r\n\r\n";
            }
            if (cm.getPlayer().getBossLog("收徒_2") > 0) {
                var 显示1 = "   2.徒弟名字：" + getCharacterNameById(cm.getPlayer().getBossLogS("收徒_2")) + "\r\n"
            } else {
                var 显示1 = "#L1#2.当前徒弟位暂缺，可以收徒#l\r\n\r\n";
            }
            if (cm.getPlayer().getBossLog("收徒_3") > 0) {
                var 显示2 = "   3.徒弟名字：" + getCharacterNameById(cm.getPlayer().getBossLogS("收徒_3")) + "\r\n"
            } else {
                var 显示2 = "#L2#3.当前徒弟位暂缺，可以收徒#l\r\n";
            }
            var text = "以下为当前收徒情况：\r\n";
            text += 显示;
            text += 显示1;
            text += 显示2;
            cm.sendSimple(text);

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
    var con1 = cm.getDataSource().getConnection();
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

