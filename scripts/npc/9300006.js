var status = 0
var chair = 3012003
var ring = 1112320

function start() {
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else if (mode == 0) {
        status--;
    } else {
        cm.dispose();
        return;
    }
	if(status == 1){
		cm.sendYesNo("请问是否要领取#v"+chair+"##z"+chair+"# #v"+ring+"##z"+ring+"#");
	} else if(status == 2){
		if(cm.getPlayer().getMarriageId() > 0) {
			if(!cm.canHold(chair)){
				cm.sendNext("背包空间不足");
				cm.dispose();
				return;
			} 
			if(cm.getPlayer().getBossLogS("LoveChair") > 0){
				cm.sendNext("你已经领取过结婚礼包");
				cm.dispose();
				return;
			}
			cm.getPlayer().setBossLog("LoveChair");
			cm.gainItem(chair, 1);
			cm.gainItem(ring, 1);
			cm.sendNext("#v"+chair+"#已经放到了你的背包");
		} else {
		cm.sendOk("你还没有结婚，领取什么？");
		cm.dispose();
		}
	} else {
		cm.dispose();
	}
}