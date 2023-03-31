
function start() {
	status = -1;
	action(1, 0, 0);
}

function action(mode, type, selection) {
	if (mode == -1) {
		cm.dispose();
	} else {
		if (mode == 0 && status == 0) {
			cm.dispose();
			return;
		}
		if (mode == 1)
			status++;
		else
			status--;
		if (status == 0) {
		    var text ="#b#L7##e专业技术村庄 #k- #n#d想你所想做你所做!\r\n";			
			text+="#r#L0##e美容美发地图 #k- #n#d好好打扮自己才能快速脱单!\r\n#l";	
		    text+="#b#L1##e城市地图传送 #k- #n#d瞬间移动穿梭与任何城市!\r\n#l";			
		    text+="#r#L2##e快速练级向导 #k- #n#d先堆等级再挑战各种boss!\r\n#l";	
		    text+="#b#L3##e创建家族圣地 #k- #n#d没兄弟不游戏!\r\n#l";	
		    text+="#r#L4##e休闲跳跳地图 #k- #n#d除了站街我们还可以跳跳蹦高高!\r\n#l";	
			text+="#b#L5##e星之力地区#k   - #n#d菜逼怪物早就虐腻了来点实在的!\r\n#l";				
			text+="#r#L6##e神秘河地区#k   - #n#d神秘国度未知领域...\r\n#l";	
			cm.sendSimple(text);
		} else if (status == 1){
			switch (selection) {
                case 7: // 专业技术村庄
				cm.dispose();
				cm.warp(910001000);
				return;
                case 0: // 美容美发
				cm.dispose();
				cm.warp(100000104);
				return;
                case 1: // 城镇地图传送
				cm.dispose();
				cm.warp(1010000);
				break;	
				case 2:
				cm.dispose();
				cm.warp(1010000);
				break;	
				case 3:
				cm.dispose();
				cm.warp(1010000);
				return;
			        case 4:
				cm.dispose();
				cm.warp(1010000);
				break;
				case 5:
				cm.dispose();
				cm.warp(1010000);
				break;	
				case 6:
				cm.dispose();
				cm.warp(1010000);
				break;
				case 16:
				default:
				cm.sendOk("管理员正在准备中.请期待");
				cm.dispose();
			}
		}
	}
}