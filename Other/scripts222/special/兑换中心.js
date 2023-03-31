/*
	
**/
var 心 = "#fUI/GuildMark.img/Mark/Etc/00009023/1#";
var 图标2 = "#fUI/StatusBar.img/BtClaim/mouseOver/0#";
var 圆形 = "#fEffect/CharacterEff/1112925/0/1#"; //空星
var hwx =  "#fEffect/CharacterEff/1032063/0/0#"; //长音符
var status = 0;

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
			cm.openNpc(9900004);
            return;
        }
        if (mode == 1) {
            status++;
        } else {
            status--;
        }
    }
    if (status <= 0) {
        var selStr = "\t\t" + hwx + "#r#e < 兑换中心 > #k#n" + hwx + "\r\n\r\n\r\n";

        selStr += "#L1#" + 圆形 + "#d货币兑换" + 圆形 + "#l#L2#" + 圆形 + "#d泡点兑换" + 圆形 + "#l#L3#" + 圆形 + "#d宠吸兑换" + 圆形 + "#l\r\n\r\n";
       
        selStr += "#L7#" + 圆形 + "#d套装兑换" + 圆形 + "#l#L8#" + 圆形 + "#d饰品兑换" + 圆形 + "#l#L9#" + 圆形 + "#d职业兑换" + 圆形 + "#l \r\n\r\n";
        selStr += "#L10#" + 圆形 + "#d物品兑换" + 圆形 + "#l#L11#" + 圆形 + "#d抽奖兑换" + 圆形 + "#l#L12#" + 圆形 + "#d技能兑换" + 圆形 + "#l\r\n\r\n";
       
        cm.sendOk(selStr);
    } else if (status == 1) {
        switch (selection) {
            case 1:
                cm.dispose();
                cm.openNpc(9900004, "货币兑换");
                break;
            case 2:
                cm.dispose();
                cm.openNpc(9900004, "泡点兑换");
                break;
            case 3:
                cm.dispose();
                cm.openNpc(9900004, "宠吸兑换");
                break;
            
            case 7:
                cm.dispose();
                cm.openNpc(9900004, "套装兑换");
                break;
            case 8:
                cm.dispose();
                cm.openNpc(9900004, "饰品兑换");
                break;
			
           case 9:
				 var selStr = "\t\t\t" + hwx + "#r#e < 职业兑换 > #k#n" + hwx + "\r\n\r\n\r\n\r\n\r\n\r\n";
                    selStr += "#e#L901#" + 圆形 + "力量战士" + 圆形 + "#l #L902#" + 圆形 + "智力法师" + 圆形 + "#l#e#L903#" + 圆形 + "敏捷弓手" + 圆形 + "#l#k\r\n\r\n";
					selStr += "#e#L904#" + 圆形 + "运气飞侠" + 圆形 + "#l #L905#" + 圆形 + "力敏海盗" + 圆形 + "#l#e#L906#" + 圆形 + "轻奢饰品" + 圆形 + "#l#k\r\n\r\n";
                    selStr += "----------------------------------------------";                    
				   cm.sendNext(selStr);
                    break;
            case 10:
               var selStr = "\t\t\t" + hwx + "#r#e < 物品兑换 > #k#n" + hwx + "\r\n\r\n\r\n\r\n\r\n\r\n";
                    selStr += "#e#L1001#" + 圆形 + "匠人兑换" + 圆形 + "#l #L1002#" + 圆形 + "魔方兑换" + 圆形 + "#l#e#L1003#" + 圆形 + "枫叶兑换" + 圆形 + "#l#k\r\n\r\n";
					selStr += "#e#L1004#" + 圆形 + "卷轴兑换" + 圆形 + "#l #L1005#" + 圆形 + "组队兑换" + 圆形 + "#l#e#L1006#" + 圆形 + "Boss兑换" + 圆形 + "#l#k\r\n\r\n";
				//	selStr += "#e#L1007#" + 圆形 + "卷轴兑换" + 圆形 + "#l#k\r\n\r\n";
                    selStr += "----------------------------------------------";                   
				   cm.sendNext(selStr);
                    break;
            case 11:
                cm.dispose();
                cm.openNpc(9900004, "转蛋屋兑换");
                break;
            case 12:
                cm.dispose();
                cm.openNpc(9900004, "技能兑换");
                break;
				
				
             default:
        }

        }else if (status == 2) {
		        switch (selection) {
				 case 901:
                    cm.dispose();
                    cm.openNpc(9900004, "力量战士");
                    break;
                case 902:
                    cm.dispose();
                   cm.openNpc(9900004, "智力法师");
                    break;
                case 903:
                    cm.dispose();
                    cm.openNpc(9900004, "敏捷弓手");
         		 break;
				 case 904:
                    cm.dispose();
                    cm.openNpc(9900004, "运气飞侠");
                    break;
                case 905:
                    cm.dispose();
                   cm.openNpc(9900004, "力敏海盗");
                    break;
                case 906:
                    cm.dispose();
                    cm.openNpc(9900004, "轻奢饰品");
         		 break;
				 case 1001:
                    cm.dispose();
                    cm.openNpc(9900004, "匠人兑换");
                    break;
                case 1002:
                    cm.dispose();
                    cm.openNpc(9900004, "魔方兑换");
                    break;
                case 1003:
                    cm.dispose();
                    cm.openNpc(9900004, "枫叶兑换");
         		 break;
				 case 1004:
                    cm.dispose();
                    cm.openNpc(9900004, "卷轴兑换");
                    break;
                case 1005:
                    cm.dispose();
                      cm.openNpc(9900004, "组队兑换");
                    break;
                case 1006:
                    cm.dispose();
                    cm.openNpc(9900004, "Boss兑换");
         		 break;
				 case 1007:
                    cm.dispose();
                    cm.openNpc(9900004, "卷轴兑换");
         		 break;
	     default:
        }
    }
}