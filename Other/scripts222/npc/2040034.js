/*
	Red Sign - 101st Floor Eos Tower (221024500)
*/

var status = -1;
var minLevel = 30; // 35
var maxLevel = 255; // 65

var minPartySize = 1; //CHANGE after BB
var maxPartySize = 6;

function action(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
	if (status == 0) {
	    cm.dispose();
	    return;
	}
	status--;
    }
	if (status == 0) {
		var txt="";
		txt += "  #r#b欢迎来到玩具塔101！#n#k#l\r\n\r\n";
		txt += "#L0#挑战玩具塔101任务！\r\n";
		txt += "#L1#领取#v1022073#(需要完成玩具副本35次)";
		cm.sendOk(txt);
	} else if (status == 1) {
	/*cm.removeAll(4001022);
	cm.removeAll(4001023);*/
	if (selection == 0) {
	if (cm.getParty() == null) { // No Party
	    cm.sendSimple("你貌似没有达到要求...:\r\n\r\n#r要求: " + minPartySize + " 玩家成员, 每个人的等级必须在 " + minLevel + " 到 等级 " + maxLevel + ".");
	} else if (!cm.isLeader()) { // Not Party Leader
	    cm.sendSimple("如果你想做任务，请 #b队长#k 跟我谈.");
	} else {
	    // Check if all party members are within PQ levels
	    var party = cm.getParty().getMembers();
	    var mapId = cm.getMapId();
	    var next = true;
	    var levelValid = 0;
	    var inMap = 0;
	    var it = party.iterator();

	    while (it.hasNext()) {
		var cPlayer = it.next();
		if ((cPlayer.getLevel() >= minLevel) && (cPlayer.getLevel() <= maxLevel)) {
		    levelValid += 1;
		} else {
		    next = false;
		}
		if (cPlayer.getMapid() == mapId) {
		    inMap += (cPlayer.getJobId() == 900 ? 6 : 1);
		}
	    }
	    if (party.size() > maxPartySize || inMap < minPartySize) {
		next = false;
	    }
	    if (next) {
		var em = cm.getEventManager("LudiPQ");
		if (em == null) {
		    cm.sendSimple("找不到脚本请联络GM");
		} else {
		    var prop = em.getProperty("state");
		    if (prop.equals("0") || prop == null) {
			em.startInstance(cm.getParty(), cm.getMap(), 70);
			/*cm.removeAll(4001022);
			cm.removeAll(4001023);*/
			cm.dispose();
			return;
		    } else {
			cm.sendSimple("其他队伍已经在里面做 #r组队任务了#k 请尝试换频道或者等其他队伍完成。");
		    }
		}
	    } else {
		cm.sendSimple("你的队伍貌似没有达到要求...:\r\n\r\n#r要求: " + minPartySize + " 玩家成员, 每个人的等级必须在 " + minLevel + " 到 等级 " + maxLevel + ".");
	    }
	}
	cm.dispose();
	} else if (selection == 1) {
		if (cm.getPlayer().getBossLogS("玩具塔101") < 35) {
			cm.sendOk("你的还没完成35次玩具塔101副本，你当前已完成"+cm.getPlayer().getBossLogS("玩具塔101")+"次。");
			cm.dispose();
			return;
		}
		if (cm.getPlayer().getBossLogS("划痕眼镜") > 0) {
			cm.sendOk("你已经领取过一次了，无法重复领取！");
			cm.dispose();
			return;
		}
		if (!cm.canHoldByTypea(1,3)) {
			cm.sendOk("你的背包已满！请清理后再尝试。");
			cm.dispose();
			return;
		}
		cm.gainItem(1022073,1);
		cm.setBossLog("划痕眼镜");
		cm.dispose();
	}
	}
	
}