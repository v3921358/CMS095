/*
 PP冒险岛
 脚本：洗血系统
 */
load('nashorn:mozilla_compat.js');
load(ServerConstants.SCRIPT_PAH+"/"+utils/db_functions.js");
importPackage(java.lang);
importPackage(Packages.server);
importPackage(Packages.client);
importPackage(Packages.tools);
importPackage(Packages.database);
importPackage(Packages.client.inventory);
importPackage(Packages.tools.packet);
importPackage(java.util);
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
var kx = "#fEffect/CharacterEff/1112925/0/1#";//空星
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
        var selStr = "\t\t#v2440002# #v2440002##e#r"+kx+"春节活动"+kx+"#v2440002# #v2440002##l#n#k\r\n\r\n";
		
		selStr += "		Hi~#b#h ##k，欢迎来到PP冒险岛#r春节活动#k，PP为了回馈广大玩家特准备了以下活动内容呈现给您：#k\r\n\r\n";
			
		selStr += "#L0#"+心+"#e活动说明"+心+"\r\n\r\n";	
		selStr += "#L1#"+JD+"领取#v1004390##z1004390#\r\n\r\n";
		selStr += "#L10#"+JD+"为#v1004390##z1004390#升星\r\n\r\n";
		selStr += "#L11#"+JD+"兑换#v1162046##z1162046# 需要\r\n\r\n#k#n#l  #v1162039##v1162040##v1162041##v1162042##v1162043##v1162044##v1162045#\r\n";
		selStr += "#L2#"+JD+"兑换#v5062000##z5062000# 10个 需要 #v2430210#[1500/"+cm.itemQuantity(2430210)+"]\r\n\r\n";
		
		selStr += "#L3#"+JD+"兑换#v5062001##z5062001# 需要 #v2430210#[800/"+cm.itemQuantity(2430210)+"]\r\n\r\n";
		
		/*selStr += "#L4#"+JD+"兑换#v2049406# #r12小时内可用#k  需要 #v2430210#[3000/"+cm.itemQuantity(2430210)+"]\r\n\r\n";
		
		selStr += "#L5#"+JD+"兑换#v2460003##z2460003# 需要 #v2430210#[100/"+cm.itemQuantity(2430210)+"]\r\n\r\n";
		
		selStr += "#L6#"+JD+"兑换#v2340000# #r6小时内可用#k 需要 #v2430210#[500/"+cm.itemQuantity(2430210)+"]\r\n\r\n";
		
		selStr += "#L7#"+JD+"兑换#v2000019# 100瓶 需要 #v2430210#[100/"+cm.itemQuantity(2430210)+"]\r\n\r\n";*/
		
		selStr += "#L8#"+JD+"兑换#v2140008#点券 500点 需要 #v2430210#[1000/"+cm.itemQuantity(2430210)+"]\r\n\r\n";

		selStr += "#L9#"+JD+"兑换#v2140003#抵用券 500点 需要 #v2430210#[500/"+cm.itemQuantity(2430210)+"]\r\n\r\n";
		
		selStr += "#L12#"+JD+"兑换游戏币 200000 需要 #v2430210#[100/"+cm.itemQuantity(2430210)+"]#k#l\r\n\r\n";
		selStr += " ";
        cm.sendSimple(selStr)
    } else if (status == 1) {
		if (selection == 0){
			var txt ="#r#e活动说明#k#n\r\n\r\n";
			txt ="#r#e活动时间#k：2020年1月22日0点~2月1日0点#n\r\n\r\n";
			txt +="1.#b白嫖#v1012170##z1012170#活动#k\r\n\r\n";
			txt += "#r活动内容#k：在游戏内编写一段对PP冒险岛的祝福寄语并截图给PP即可参加白嫖鬼脸抽奖活动。\r\n";
			txt += "\r\n#r活动截止时间#k：2020年2月1日晚10点，届时PP将抽取幸运玩家并公示至群内，中奖玩家即可联系PP领取奖励。\r\n";
			txt += "\r\n#r活动规则#k：参与玩家人数每达到30的倍数时，中奖玩家名额增加1名，即30人参加抽取1人，60人参加抽取2人，以此类推。\r\n";
　　		txt +="\r\n2.#b红包兑换活动#k\r\n\r\n";
　　		txt +="#r活动内容#k：活动期间所有PP冒险岛内的怪物都会概率掉落#v2430210# 搜集足够的#v2430210#即可兑换奖励，奖励内容包括：\r\n\r\n";
　　		txt +="#v5062000# #v5062001#  …………\r\n\r\n";			
			txt +="\r\n3.#b双倍点券充值活动#k\r\n\r\n";
			txt +="#r活动内容#k：活动期间内，充值网站每天会有500R限额的1:400比例的点券提供抢购，先到先得。\r\n\r\n";
			txt +="\r\n4.#b#v1162046##z1162046#限时兑换#k\r\n\r\n";
			txt +="#r活动内容#k：活动期间内，击杀#r任意小怪#k即可掉落#z1162046#兑换材料。\r\n\r\n";
			txt +="\r\n5.#b新春在线奖励#k\r\n\r\n";
			txt +="#r活动内容#k：除夕及大年初一晚9点整在线的玩家将获得点券2000，抵用券3000，金币300万。\r\n\r\n";
			cm.sendOk(txt);
			cm.dispose();
		} else if (selection == 10){
			cm.dispose();
			cm.openNpc(9900004,"圣诞帽升星");
		} else if (selection == 1){

			cm.gainItem(1004390,1);

			cm.sendOk("恭喜你领取成功！");
			cm.dispose();
		} else if (selection == 2){
			var next = true;
			var 角色ID = cm.getPlayer().getId();
			var fee_list = getAccCid(角色ID);
				for (var i = 0;i < fee_list.length; i++) {
					if (fee_list[i] == 角色ID){
						continue;
					}
					if (cm.getBossRank(fee_list[i],"春节福利",2) > 0) {
						next = false;
						break;
					}
				}
			if (next == false) {
				cm.sendOk("你的其他角色已经兑换过了！");
				cm.dispose();
				return;
			}
			if (cm.getPlayer().getBossLogS("春节魔方") >= 20) {
				cm.sendOk("你兑换20次了，无法继续兑换！");
				cm.dispose();
				return;
			}
			if (cm.itemQuantity(2430210) < 1500){
				cm.sendOk("你#v2430210#不足，无法继续兑换！");
				cm.dispose();
				return;
			}
			cm.gainItem(5062000,10);
			cm.gainItem(2430210,-1500);
			cm.setBossLog("春节魔方");
			cm.setBossRankCount("春节福利",1);
			cm.sendOk("恭喜你领取成功！");
			cm.dispose();
		} else if (selection == 3){
			var next = true;
			var 角色ID = cm.getPlayer().getId();
			var fee_list = getAccCid(角色ID);
				for (var i = 0;i < fee_list.length; i++) {
					if (fee_list[i] == 角色ID){
						continue;
					}
					if (cm.getBossRank(fee_list[i],"春节福利",2) > 0) {
						next = false;
						break;
					}
				}
			if (next == false) {
				cm.sendOk("你的其他角色已经兑换过了！");
				cm.dispose();
				return;
			}
			if (cm.getPlayer().getBossLogS("春节混沌魔方") >= 30) {
				cm.sendOk("你兑换30次了，无法继续兑换！");
				cm.dispose();
				return;
			}
			if (cm.itemQuantity(2430210) < 800){
				cm.sendOk("你#v2430210#不足，无法继续兑换！");
				cm.dispose();
				return;
			}
			cm.gainItem(5062001,1);
			cm.gainItem(2430210,-800);
			cm.setBossLog("春节混沌魔方");
			cm.setBossRankCount("春节福利",1);
			cm.sendOk("恭喜你领取成功！");
			cm.dispose();
		} else if (selection == 4){
			if (cm.getPlayer().getBossLogS("圣诞A潜") > 0) {
				cm.sendOk("你兑换1次了，无法继续兑换！");
				cm.dispose();
				return;
			}
			if (cm.itemQuantity(2430210) < 3000){
				cm.sendOk("你#v2430210#不足，无法继续兑换！");
				cm.dispose();
				return;
			}
			cm.gainItemPeriodF(2049406,1,720,"PP");
			cm.gainItem(2430210,-3000);
			cm.setBossLog("圣诞A潜");
			cm.sendOk("恭喜你领取成功！");
			cm.dispose();		
			} else if (selection == 5){
			if (cm.getPlayer().getBossLogS("圣诞放大镜") >= 20) {
				cm.sendOk("你兑换20次了，无法继续兑换！");
				cm.dispose();
				return;
			}
			if (cm.itemQuantity(2430210) < 100){
				cm.sendOk("你#v2430210#不足，无法继续兑换！");
				cm.dispose();
				return;
			}
			cm.gainItem(2460003,1);
			cm.gainItem(2430210,-100);
			cm.setBossLog("圣诞放大镜");
			cm.sendOk("恭喜你领取成功！");
			cm.dispose();		
			} else if (selection == 6){
			if (cm.getPlayer().getBossLogS("圣诞祝福") >= 2) {
				cm.sendOk("你兑换2次了，无法继续兑换！");
				cm.dispose();
				return;
			}
			if (cm.itemQuantity(2430210) < 500){
				cm.sendOk("你#v2430210#不足，无法继续兑换！");
				cm.dispose();
				return;
			}
			cm.gainItemPeriodF(2340000,1,360,"PP");
			cm.gainItem(2430210,-500);
			cm.setBossLog("圣诞祝福");
			cm.sendOk("恭喜你领取成功！");
			cm.dispose();		
			} else if (selection == 7){
			if (cm.getPlayer().getBossLogS("圣诞超级") >= 10) {
				cm.sendOk("你兑换10次了，无法继续兑换！");
				cm.dispose();
				return;
			}
			if (cm.itemQuantity(2430210) < 100){
				cm.sendOk("你#v2430210#不足，无法继续兑换！");
				cm.dispose();
				return;
			}
			cm.gainItem(2000019,100);
			cm.gainItem(2430210,-100);
			cm.setBossLog("圣诞超级");
			cm.sendOk("恭喜你领取成功！");
			cm.dispose();	
			} else if (selection == 8){
				var next = true;
			var 角色ID = cm.getPlayer().getId();
			var fee_list = getAccCid(角色ID);
				for (var i = 0;i < fee_list.length; i++) {
					if (fee_list[i] == 角色ID){
						continue;
					}
					if (cm.getBossRank(fee_list[i],"春节福利",2) > 0) {
						next = false;
						break;
					}
				}
			if (next == false) {
				cm.sendOk("你的其他角色已经兑换过了！");
				cm.dispose();
				return;
			}
			if (cm.getPlayer().getBossLogS("春节点券") >= 6) {
				cm.sendOk("你兑换6次了，无法继续兑换！");
				cm.dispose();
				return;
			}
			if (cm.itemQuantity(2430210) < 1000){
				cm.sendOk("你#v2430210#不足，无法继续兑换！");
				cm.dispose();
				return;
			}
			cm.gainNX(500);
			cm.gainItem(2430210,-1000);
			cm.setBossRankCount("春节福利",1);
			cm.setBossLog("春节点券");
			cm.sendOk("恭喜你领取成功！");
			cm.dispose();
			} else if (selection == 9){
				var next = true;
			var 角色ID = cm.getPlayer().getId();
			var fee_list = getAccCid(角色ID);
				for (var i = 0;i < fee_list.length; i++) {
					if (fee_list[i] == 角色ID){
						continue;
					}
					if (cm.getBossRank(fee_list[i],"春节福利",2) > 0) {
						next = false;
						break;
					}
				}
			if (next == false) {
				cm.sendOk("你的其他角色已经兑换过了！");
				cm.dispose();
				return;
			}
			if (cm.getPlayer().getBossLogS("春节抵用") >= 12) {
				cm.sendOk("你兑换12次了，无法继续兑换！");
				cm.dispose();
				return;
			}
			if (cm.itemQuantity(2430210) < 500){
				cm.sendOk("你#v2430210#不足，无法继续兑换！");
				cm.dispose();
				return;
			}
			cm.gainNX2(500);
			cm.gainItem(2430210,-500);
			cm.setBossLog("春节抵用");
			cm.setBossRankCount("春节福利",1);
			cm.sendOk("恭喜你领取成功！");
			cm.dispose();
		 } else if (selection == 11){
			if (cm.haveItem(1162039,1) && cm.haveItem(1162040,1) && cm.haveItem(1162041,1) && cm.haveItem(1162042,1) && cm.haveItem(1162043,1) && cm.haveItem(1162044,1) && cm.haveItem(1162045,1)) {
				cm.gainItem(1162039,-1);
				cm.gainItem(1162040,-1);
				cm.gainItem(1162041,-1);
				cm.gainItem(1162042,-1);
				cm.gainItem(1162043,-1);
				cm.gainItem(1162044,-1);
				cm.gainItem(1162045,-1);
				cm.gainItem(1162046,1);
				cm.setBossRankCount("春节福利",1);
				cm.sendOk("恭喜你领取成功！");
				cm.dispose();
			} else {
				cm.sendOk("你的物品不足！");
				cm.dispose();
			}
	} else if (selection == 12) {
		if (cm.itemQuantity(2430210) < 100){
				cm.sendOk("你#v2430210#不足，无法继续兑换！");
				cm.dispose();
				return;
			}
			cm.gainMeso(200000);
			cm.gainItem(2430210,-100);
			cm.setBossRankCount("春节福利",1);
			cm.sendOk("恭喜你领取成功！");
			cm.dispose();
	}
	}
		else if (status == 2) {
		if (beaty == 2){
			if (cm.itemQuantity(2430210)/20 < selection) {
				cm.sendOk("你的#v2430210#不足!");
				cm.dispose;
				return;
			}
			if (cm.getPlayer().getBossLogS("圣诞魔方") >= 100) {
				cm.sendOk("你兑换100次了，无法继续兑换！");
				cm.dispose;
				return;
			}
			cm.gainItem(2430210,-selection * 20);
			cm.gainItem(5062000,selection);
			记录BossLog圣诞魔方(selection);
			cm.sendOk("恭喜你获得 #v5062000##z5062000# "+selection+" 个!");
			cm.dispose();
		}
	} 
	
}



function 记录BossLog圣诞魔方(ints) {
	for (var i = 0;i < ints; i++) {
		cm.setBossLog("圣诞魔方");
	}
}

