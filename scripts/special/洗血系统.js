/*
 PP冒险岛
 脚本：洗血系统
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
var 道具ID = 2022336;
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
	var 等级 = cm.getPlayer().getLevel();
	var 职业 = cm.getPlayer().getJob();
    if (职业 == 0 || 职业 == 1 || 职业 == 1000 || 职业 == 2000 || 职业 == 2001 || 职业 == 3000 || 职业 == 3001 || 职业 == 2002) {
		var minMp = 等级 * 10 + 2; -8 +8
	} else if ((职业 == 100 && 职业 <= 112) || (职业 >= 2000 && 职业 <= 2112) || (职业 >= 1100 && 职业 <= 1112)){
		var minMp = 等级 * 4 + 59; -4 +20
	} else if ((职业 == 200) || (职业 >= 1200 && 职业 <= 1212) || (职业 >= 2200 && 职业 <= 2218) || (职业 >= 3200 && 职业 <= 3212)){
		var minMp = 等级 * 22 + 38; -50 +6
	} else if (职业 >= 210 && 职业 <= 232) {
		var minMp = 等级 * 22 +488;
	} else if ((职业 == 300) || (职业 == 400) || (职业 >= 1300 && 职业 <= 1412) || (职业 >= 3300 && 职业 <= 3312)){
		var minMp = 等级 * 14 - 2;
	} else if (职业 >= 310 && 职业 <= 434){
		var minMp = 等级 * 14 + 148;
	} else if ((职业 == 500) || (职业 >= 1500 && 职业 <= 1512) || (职业 >= 3500 && 职业 <= 3512)){	
		var minMp = 等级 * 18 - 39;
	} else if (职业>= 510 && 职业 <= 522){
		var minMp = 等级 * 18 + 111;
	}
	if (status == 0) {
        var selStr = "\t\t\t" + 心 + "  " + 心 + " #r#e < 洗血系统 > #k#n " + 心 + "  " + 心 + "\r\n\r\n";
		
		selStr += "		Hi~#b#h ##k，想提高自己的血量上限为之后的BOSS挑战提供更大的可能性么？#k\r\n\r\n";
			
		selStr += "#L0#"+心+"#e什么是洗血？"+心+"\r\n\r\n";	
		
		//selStr += "#L1#"+JD+"领取洗血智戒\r\n\r\n";
				
		//selStr += "#L2#"+JD+"快速洗血（洗蓝加血）\r\n";
		
		selStr += "#L3#"+JD+"购买#v" + 道具ID + "##z" + 道具ID + "#\r\n";
        cm.sendSimple(selStr)
    } else if (status == 1) {
		if (selection == 0){
			var txt ="#r#e什么是洗血#k#n\r\n\r\n";
			txt += "\t洗血,就是把能力点直接加到智力上,在升级的时候会多出#b智力*0.1点最大MP#k,然后使用#v5050000#,减少MP,增加到HP上,这样,每次升级,就能多出少许HP。但是,当洗了一定次数后,再想去洗MP到HP上,会发现箭头变灰,这是因为MP已被洗到了下限,不能再减MP了。\r\n\r\n";
　　		//txt +="2.#b优化的洗血#k\r\n\r\n";
　　		//txt +="\t每次升级时,智力的数值会影响到MP的自然增加量,所以我们可以靠增加MP的上限来增加洗血的次数。每次升级前,穿+智力的装备,#r例如找我领取洗血智戒（非法师职业可领取，+200智力）#k。这样,每次升级就能多增加一些MP,那么洗血的次数就能多一些。\r\n\r\n";
			cm.sendOk(txt);
			cm.dispose();
		}  else if (selection == 3){
			cm.sendNext("#e你想使用什么货币购买呢？#n\r\n\r\n#L1#"+JD+"#e使用点券(1000/个)\r\n\r\n#L2#"+JD+"使用抵用券(2000/个,每日限购20个)");
		}
	} else if (status == 2) {
		if (selection == 1){
			cm.sendGetNumber("#e你想要购买多少个？#n\r\n\r\n",1,1,100);
			beaty = 1;
		} else if (selection == 2){
			cm.sendGetNumber("#e你想要购买多少个？#n\r\n\r\n每日最多购买20个！\r\n\r\n您当前已购买:#b"+cm.getPlayer().getBossLogD("洗血箱子")+"个#k\r\n",1,1,20-cm.getPlayer().getBossLogD("洗血箱子"));
			beaty = 2;
		} 
	} else if (status == 3) {
		if (beaty == 1){
			if (!cm.canHold(道具ID)) {
				cm.sendOk("你的背包空间不足!请清理背包后再尝试!");
				cm.dispose();
				return;
			}
			if (cm.getPlayer().getCSPoints(1) < selection * 1000) {
				cm.sendYesNo("你的点券不足!请充值，是否打开充值界面？");
				cm.dispose;
				return;
			}
			/*if (cm.getPlayer().getBossLogD("洗血箱子") >= 20) {
				cm.sendOk("你今天已经购买了20个洗血箱子，请明天再来!");
				cm.dispose();
				return;
			}*/
			cm.gainNX(-selection * 1000);
			cm.gainItem(道具ID,selection);
			//记录BossLog(selection);
			cm.sendOk("恭喜你获得 #v"+道具ID+"##z" + 道具ID + "# "+selection+" 个!");
			cm.dispose();
		} else if (beaty == 2){
			if (!cm.canHoldByTypea(2,1)) {
				cm.sendOk("你的背包空间不足!请清理背包后再尝试!");
				cm.dispose();
				return;
			}
			if (cm.getPlayer().getCSPoints(2) < selection * 2000) {
				cm.sendYesNo("你的点券不足!请充值，是否打开充值界面？");
				cm.dispose;
				return;
			}
			if (cm.getPlayer().getBossLogD("洗血箱子") >= 20) {
				cm.sendOk("你今天已经购买了20个洗血箱子，请明天再来!");
				cm.dispose();
				return;
			}
			cm.gainNX2(-selection * 2000);
			cm.gainItem(道具ID,selection);
			记录BossLog(selection);
			cm.sendOk("恭喜你获得 #v"+道具ID+"##z" + 道具ID + "# "+selection+" 个!");
			cm.dispose();
		} 
	} 
}




function 记录BossLog(ints) {
	for (var i = 0;i < ints; i++) {
		cm.setBossLog("洗血箱子");
	}
}

function 记录BossLogS(ints) {
	for (var i = 0;i < ints; i++) {
		cm.setBossLog("洗血箱子2");
	}
}