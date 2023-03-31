var aaa = "#fUI/UIWindow.img/Quest/icon9/0#";
var zzz = "#fUI/UIWindow.img/Quest/icon8/0#";
var sss = "#fUI/UIWindow.img/QuestIcon/3/0#";

//------------------------------------------------------------------------

var chosenMap = -1;
var monsters = 0;
var towns = 0;
var bosses = 0;
var fuben = 0;
var fee;
var xx;

//------------------------------------------------------------------------


var aaa = "#fUI/UIWindow.img/Quest/icon9/0#"; //红色右箭头
var zzz = "#fUI/UIWindow.img/Quest/icon8/0#"; //蓝色右箭头
var sss = "#fUI/UIWindow.img/QuestIcon/3/0#"; //选择道具
var eff1 = "#fUI/LogoMs/1#";
var scx = "#fEffect/CharacterEff/1082312/1/0#"; //双彩星
var ccx = "#fEffect/CharacterEff/1082312/0/0#"; //长彩星

var hwx = "#fEffect/CharacterEff/1102232/2/0#"; //黄歪星
var cyf = "#fEffect/CharacterEff/1032063/0/0#"; //长音符
var xcx = "#fEffect/CharacterEff/1052203/3/0#"; //小彩星
var yf1 = "#fEffect/CharacterEff/1082312/1/0#"; //音符1
var yf2 = "#fEffect/CharacterEff/1112900/1/1#"; //音符2
var yf3 = "#fEffect/CharacterEff/1112900/2/1#"; //音符3
var yf4 = "#fEffect/CharacterEff/1112900/4/1#"; //音符4
var yf5 = "#fEffect/CharacterEff/1112900/5/1#"; //音符5
var kx = "#fEffect/CharacterEff/1112925/0/1#"; //空星
var kx1 = "#fEffect/CharacterEff/1112925/0/2#"; //空星
var hot = "#fUI/CashShop.img/CSEffect/hot/0#";
var 五角星 = "#fUI/UIWindow.img/UserList/Expedition/icon14#";
var 正方箭头 = "#fUI/Basic/BtHide3/mouseOver/0#";
var 感叹号 = "#fUI/UIWindow/Quest/icon0#";
var 美化new = "#fUI/UIWindow/Quest/icon5/1#";
var zl= "+player.getStat().getCurrentMaxBaseDamage()+";

//var 红色箭头 = "#fEffect/CharacterEff/1114000/2/0#";
var 红色箭头 = "#fEffect/CharacterEff/1112908/0/1#"; //彩光3
//var kx = "#fEffect/CharacterEff/1062114/1/0#";  //爱心
//var 红色箭头 = "#fUI/UIWindow/Quest/icon6/7#";
var 蓝色角点 = "#fUI/UIWindow.img/PvP/Scroll/enabled/next2#";

//---------------------------------------------------------------------------
function start() {
    status = -1;
    action(1, 0, 0);
}
function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
		
    } else {
		
        if (status >= 0 && mode == 0) {
			
            cm.dispose();
            return;
        }
        if (mode == 1) {
            status++;
        } else {
            status--;
        }
        //------------------------------------------------------------------------
        if (status == 0) {
            var add = "\r\n\t" + cyf + "#L10000##e欢迎来到~eV.095冒险岛#n#k " + cyf + "#l\r\n\r\n\r\n";
        
		 
            add += "#b#e#L10001#" + kx + "返回自由" + kx + "#l #L10002#" + kx + "地图传送" + kx + "#l #L10003#" + kx + "每日任务" + kx + "#l#k\r\n\r\n";
            add += "#b#e#L10004#" + kx + "余额商城" + kx + "#l #L10005#" + kx + "购物中心" + kx + "#l #L10006#" + kx + "兑换中心" + kx + "#l#k\r\n\r\n";
            add += "#b#e#L10007#" + kx + "强化中心" + kx + "#l #L10008#" + kx + "特色系统" + kx + "#l #L10009#" + kx + "推广系统" + kx + "#l#k\r\n\r\n";
        if (cm.haveItem(5010150,1)) {
            add += "#r#b#L20001#" + kx + "快速转职" + kx + "#l #L20002#" + kx + "功能菜单" + kx + "#l #L20003#" + kx + "月卡奖励" + kx + "#l#k\r\n\r\n";             
           }           
		if (cm.haveItem(5010143,1)) {
            add += "#r#e#L30001#" + kx + "会员地图" + kx + "#l #L30002#" + kx + "会员菜单" + kx + "#l #L30003#" + kx + "会员福利" + kx + "#l#k\r\n\r\n";             
            }       
	    if (cm.haveItem(5010150)<=0) {
			
            add += "#r#e#L50001#" + kx + "快速转职" + kx + "#l #L50002#" + kx + "功能菜单" + kx + "#l #L50003#" + kx + "月卡奖励" + kx + "#l#k\r\n\r\n";   
           	add += "\t\t\t\t  #r↑#b月卡功能#r↑\r\n";	
            }           
		  
        if (cm.getPlayer().isGM()) {
                add += "    \t\t  #L40001#" + kx + "管理员菜单" + kx + "#l \r\n";

             
            }
			
            cm.sendOk(add);
		

            //------------------------------------------------------------------------

        } else if (status == 1) {
            switch (selection) {
				case 10000:
                    cm.dispose();
                    cm.openNpc(9900004, "个人信息");
                    break;
				 case 10001:
                if (cm.getPlayer().getMapId() >= 910000000 && cm.getPlayer().getMapId() <= 910000022) {
                    cm.sendOk("您已经在市场了，还想做什么？");
                } else {
                    cm.saveReturnLocation("FREE_MARKET");
                    cm.warp(910000000, "st00");
                }
                cm.dispose();
                break;
                case 10002:
				 var selStr = "\t\t\t" + cyf + "#r#e < 地图传送 > #k#n" + cyf + "\r\n\r\n";
				    selStr += "  #d Hi~ #b#h ##k请选择你要去的地方吧，我能让你转瞬之间就到达目的地哦，不过你还是要多锻炼一下最好，修炼之余多看看冒险岛的风景吧。#k#n#b\r\n";
                    selStr += "#e#L1#" + kx + "城镇传送" + kx + "#l #L2#" + kx + "组队传送" + kx + "#l#k\r\n\r\n";
					selStr += "#e#L3#" + kx + "练级传送" + kx + "#l #L4#" + kx + "BOSS传送" + kx + "#l#k\r\n\r\n";
                    cm.sendNext(selStr);
                    break;
                case 10003:
                    cm.dispose();
                    cm.openNpc(9900004, "每日任务");
                    break;
                case 10004:
				    cm.dispose();
                    cm.openNpc(9900004, "余额商城");		              
                    return;
                case 10005:
                    cm.dispose();
                    cm.openNpc(9900004, "购物中心");
                    break;
                case 10006:
                    cm.dispose();
                    cm.openNpc(9900004, "兑换中心");
                    break;
                case 10007:
                    cm.dispose();
                    cm.openNpc(9900004, "强化中心");
                    break;
                case 10008:
                    cm.dispose();
                    cm.openNpc(9900004, "特色系统");
                    break;
                case 10009:
                    cm.dispose();
                    cm.openNpc(9900004, "推广系统");
                    break;
                case 20001:
                    cm.dispose();
                    cm.openNpc(9900004, "快速转职");
                    break;
                case 20002:
                    cm.dispose();
                    cm.openNpc(9900004, "功能菜单");
                    break;
                case 20003:
                    cm.dispose();
                    cm.openNpc(9900004, "月卡奖励");
                    break;
			    case 30001:
                   if (cm.getPlayer().getMapId() >= 209000000 && cm.getPlayer().getMapId() <= 209000000) {
                    cm.sendOk("尊贵的超级会员您现在所在的正是会员专享地图");
                } else {
                    cm.saveReturnLocation("FREE_MARKET");
                    cm.warp(209000000, "st00");
                }
				    cm.dispose();
                    break;
			    case 30002:
				var selStr = "\r\n\t\t\t\t#r#e欢迎使用会员菜单#n#k#l\r\n\r\n\r\n";				
				    selStr += "-----------------------------------------------------\r\n\r\n";
                    selStr += "#e#L200001#" + kx + "会员商店" + kx + "#l #L200002#" + kx + "兑换中心" + kx + "#l#L200003#" + kx + "特权赞助" + kx + "#l#k\r\n\r\n";
                    selStr += "#e#L200004#" + kx + "强化中心" + kx + "#l #L200005#" + kx + "会员技能" + kx + "#l#L200006#" + kx + "超会强化" + kx + "#l#k\r\n\r\n";
					selStr += "#e#L200007#" + kx + "VIP 等级" + kx + "#l #L200008#" + kx + "超会兑换" + kx + "#l#L200009#" + kx + "超会福利" + kx + "#l#k\r\n\r\n\r\n";
                    selStr += "----------------------------------------------";
                    cm.sendNext(selStr);
				
                    break;
					case 30003:
                    cm.dispose();
                    cm.openNpc(9900004, "会员福利");
                    break;
				case 40001:
				var selStr = "\r\n\t\t\t#r#e欢迎使用eV.095冒险岛管理系统#n#k#l\r\n\r\n\r\n";
				    selStr += "-----------------------------------------------------\r\n\r\n";
                    selStr += "#e#L300001#" + kx + "管理福利" + kx + "#l #L300002#" + kx + "管理章程" + kx + "#l#L300003#" + kx + "功能菜单" + kx + "#l#k\r\n\r\n";
					selStr += "#e#L300004#" + kx + "管理专图" + kx + "#l #L300005#" + kx + "BUG 反馈" + kx + "#l#L300006#" + kx + "技术支持" + kx + "#l#k\r\n\r\n\r\n";
                    selStr += "----------------------------------------------";
                    cm.sendNext(selStr);
                    break;
				case 50001:
                    cm.dispose();
                    cm.sendOk("----------------------#r无权#b使用，#r月卡功能！");
                    break;
				case 50002:
                    cm.dispose();
                    cm.sendOk("----------------------#r无权#b使用，#r月卡功能！");
                    break;
				case 50003:
                    cm.dispose();
                    cm.sendOk("----------------------#r无权#b使用，#r月卡功能！");
                    break;

					
                cm.sendOk(add);
            }

        }else if (status == 2) {
		switch (selection) {
				case 1:
                    cm.dispose();
                    cm.openNpc(9900004, "快捷传送");
                    break;
                case 2:
                    cm.dispose();
                    cm.openNpc(9010022);
                    break;
                case 3:
                    cm.dispose();
                    cm.openNpc(9900004, "练级传送");
                    break;
				case 4:
                    cm.dispose();
                    cm.openNpc(9900004, "BOSS传送");
                    break;
				case 200001:
                    cm.dispose();
                    cm.openShop(62);
                    break;
				case 200002:

				 var selStr = "\t\t\t\t#r#e < 会员兑换中心 > #k#n\r\n\r\n\r\n\r\n\r\n\r\n";
				   selStr += "#e#L200027#" + kx + "货币兑换" + kx + "#l #L200028#" + kx + "物品兑换" + kx + "#l#e#L200029#" + kx + "抽奖兑换" + kx + "#l#k\r\n\r\n";

                    selStr += "#e#L200021#" + kx + "轻奢战士" + kx + "#l #L200022#" + kx + "轻奢法师" + kx + "#l#e#L200023#" + kx + "轻奢弓手" + kx + "#l#k\r\n\r\n";
					selStr += "#e#L200024#" + kx + "轻奢飞侠" + kx + "#l #L200025#" + kx + "轻奢海盗" + kx + "#l#e#L200026#" + kx + "毕业饰品" + kx + "#l#k\r\n\r\n";
                    selStr += "----------------------------------------------";                  
				  cm.sendNext(selStr);
				
                    break;
				case 200003:
                    cm.dispose();
                    cm.openNpc(9900004, "会员赞助");
                    break;
				case 200004:
                    cm.dispose();
                    cm.sendOk("功能待添加200004");
                    break;
				case 200005:
                    cm.dispose();
                  cm.openNpc(9900004, "学习专业技能");
                    break;
				case 200006:
                    if (cm.haveItem(5010152,1)) {
                    cm.sendOk("强化功能带添加200006");
                    cm.dispose();					
	              } else {
	            	cm.sendOk("购买超级会员200006");
	              }
				  break;
				  case 200007:
                    if (cm.haveItem(5010152,1)) {
                    cm.sendOk("强化功能带添加200007");
                    cm.dispose();					
	              } else {
	            	cm.sendOk("购买超级会员200008");
	              }
				  break;
				  case 200008:
                    if (cm.haveItem(5010152,1)) {
                     var selStr = "\t\t\t\t#r#e < 超会兑换中心 > #k#n\r\n\r\n\r\n\r\n\r\n\r\n";
                    selStr += "#e#L200081#" + kx + "毕业战士" + kx + "#l #L20082#" + kx + "毕业法师" + kx + "#l#e#L200083#" + kx + "毕业弓手" + kx + "#l#k\r\n\r\n";
					selStr += "#e#L200084#" + kx + "毕业飞侠" + kx + "#l #L200085#" + kx + "毕业海盗" + kx + "#l#e#L200086#" + kx + "全职毕业" + kx + "#l#k\r\n\r\n";
                    selStr += "----------------------------------------------";                  
				  cm.sendNext(selStr);
	              } else {
	            	cm.sendOk("购买超级会员200008");
	              }
				  break;
				  case 200009:
                    if (cm.haveItem(5010152,1)) {
                    cm.sendOk("强化功能带添加200009");
                    cm.dispose();					
	              } else {
	            	cm.sendOk("购买超级会员200009");
	              }
				  break;
				case 300001:
                    cm.dispose();
                    cm.sendOk("功能待添加300001");
                    break;
				case 300002:
                    cm.dispose();
                    cm.sendOk("功能待添加300002");
                    break;
				case 300003:
				var selStr = "\r\n\t\t\t#r#e欢迎使用eV.095冒险岛功能菜单#n#k#l\r\n\r\n\r\n";
				    selStr += "-----------------------------------------------------\r\n\r\n";
                    selStr += "#e#L300031#" + kx + "查询功能" + kx + "#l #L300032#" + kx + "功能功能" + kx + "#l#L300033#" + kx + "功能功能" + kx + "#l#k\r\n\r\n";
					selStr += "#e#L300034#" + kx + "功能功能" + kx + "#l #L300005#" + kx + "功能功能" + kx + "#l#L300036#" + kx + "功能功能" + kx + "#l#k\r\n\r\n\r\n";
                    selStr += "----------------------------------------------";
                    cm.sendNext(selStr);
                    break;
			    case 300004:
                    cm.dispose();
                    cm.sendOk("功能待添加300004");
                    break;
				case 300005:
                    cm.dispose();
                    cm.sendOk("功能待添加300005");
                    break;
				case 300006:
                    cm.dispose();
                    cm.sendOk("功能待添加300006");
                    break;
					
			   default:
            }

        }else if (status == 3) {
		switch (selection) {
			    case 300031:
                    cm.dispose();
                    cm.openNpc(9900004, "查询功能");
                    break;
				 case 300032:
                    cm.dispose();
                    cm.sendOk("功能待添加");
                    break;
				case 300033:
                    cm.dispose();
                    cm.sendOk("功能待添加");
                    break;
			    case 300034:
                    cm.dispose();
                    cm.sendOk("功能待添加");
                    break;
				case 300035:
                    cm.dispose();
                    cm.sendOk("功能待添加");
                    break;
				case 300036:
                    cm.dispose();
                    cm.sendOk("功能待添加");
					
                    break;
				case 200021:
                    cm.dispose();
                    cm.sendOk("功能待添加200021");
                    break;
					
				case 200022:
                    cm.dispose();
                    cm.sendOk("功能待添加200022");
                    break;
					
				case 200023:
                    cm.dispose();
                    cm.sendOk("功能待添加200023");
                    break;
				case 200024:
                    cm.dispose();
                    cm.sendOk("功能待添加200024");
                    break;
					
				case 200025:
                    cm.dispose();
                    cm.sendOk("功能待添加200025");
                    break;
					
				case 200026:
                    cm.dispose();
                    cm.sendOk("功能待添加200026");
                    break;
				case 200027:
                    cm.dispose();
                    cm.openNpc(9900004, "会员货币");
                    break;
					
				case 200028:
                    cm.dispose();
                    cm.openNpc(9900004, "会员换物");
                    break;
					
				case 200029:
                    cm.dispose();
                    cm.openNpc(9900004, "会员抽奖兑换");
                    break;
					
				case 200081:
                    cm.dispose();
                    cm.sendOk("功能待添加200081");
                    break;
					
				case 200082:
                    cm.dispose();
                    cm.sendOk("功能待添加200082");
                    break;
					
				case 200083:
                    cm.dispose();
                    cm.sendOk("功能待添加200083");
                    break;
					
				case 200084:
                    cm.dispose();
                    cm.sendOk("功能待添加200084");
                    break;
					
				case 200085:
                    cm.dispose();
                    cm.sendOk("功能待添加200085");					
                    break;
					
				case 200086:
                    cm.dispose();
                    cm.sendOk("功能待添加200086");
                    break;
					
					
					
			default:
		}
		}
    }
}

