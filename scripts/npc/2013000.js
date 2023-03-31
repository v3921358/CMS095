var status = -1;
var minLevel = 70; // 35
var maxLevel = 255; // 65

var minPartySize = 3;
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
	if (cm.getMapId() == 920010000) { //inside orbis pq
		cm.sendOk("我们必须拯救他 需要20个云的碎片!");
		cm.dispose();
		return;
	}
    if (status == 0) {
		var txt="";
		txt += "  #r#b欢迎来到女神塔！#n#k#l\r\n\r\n";
		txt += "#L0#挑战女神塔任务，拯救女神！\r\n";
		txt += "#L1#使用#v4001158#兑换女神套装。";
		cm.sendOk(txt);
	} else if (status == 1) {
		if (selection == 0){
	for (var i = 4001044; i < 4001064; i++) {
		cm.removeAll(i); //holy
	}
	if (cm.getParty() == null) { // No Party
	    cm.sendSimple("你貌似没有达到要求...:\r\n\r\n#r要求: " + minPartySize + " 玩家成员, 每个人的等级必须在 " + minLevel + " 到 等级 " + maxLevel);
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
		var em = cm.getEventManager("OrbisPQ");
		if (em == null) {
		    cm.sendSimple("找不到脚本请联络GM");
		} else {
		    var prop = em.getProperty("state");
		    if (prop.equals("0") || prop == null) {
			em.startInstance(cm.getParty(), cm.getMap(), 120);
			cm.dispose();
			return;
		    } else {
			cm.sendSimple("其他队伍已经在里面做 #r组队任务了#k 请尝试换频道或者等其他队伍完成。");
		    }
		}
	    } else {
		cm.sendSimple("你的队伍貌似没有达到要求...:\r\n\r\n#r要求: " + minPartySize + " 玩家成员, 每个人的等级必须在 " + minLevel + " 到 等级 " + maxLevel);
	    }
	}
		} else if (selection == 1) {
		var text="";
		text += "  #r#b你想兑换什么？#n#k#l\r\n\r\n";
		text += "#L2#使用#v4001158#x10 兑换#v1082232#\r\n";
		text += "#L3#使用#v4001158#x10 兑换#v1072534#";
		cm.sendOk(text);
		}
    
    } else if (status == 2){
		if (selection == 2){
			if (!cm.canHold(1082232,1)){
				cm.sendOk("你的背包不足，请清理背包。");
				cm.dispose();
			}
			if (cm.haveItem(4001158,10)){
				cm.gainItem(4001158,-10);
				cm.gainItem(1082232,1);
				cm.sendOk("兑换成功，获得#v1082232#!");
				cm.dispose();
			} else {
				cm.sendOk("你的#v4001158#不足10个!");
				cm.dispose();
			} 
		} else if (selection == 3){
			if (!cm.canHold(1072455,1)){
				cm.sendOk("你的背包不足，请清理背包。");
				cm.dispose();
			}
			if (cm.haveItem(4001158,10)){
				cm.gainItem(4001158,-10);
				cm.gainItem(1072455,1);
				cm.sendOk("兑换成功，获得#v1072455#!");
				cm.dispose();
			} else {
				cm.sendOk("你的#v4001158#不足10个!");
				cm.dispose();
			}
		}
	}
}
