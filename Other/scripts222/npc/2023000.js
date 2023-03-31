var map;
var cost;
var location;
var mapname;
var status = -1;

function action(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
	cm.sendNext("嗯......想想吧。这是出租車公司價值的服務！你永远不会后悔！");
	cm.dispose();
	return;
    }

    if (status == 0) {
	switch (cm.getMapId()) {
	    case 540000000: // CBD
		map = 541020000;
		cost = 30000;
		mapname = "烏魯城";
		break;
	    case 240000000: // Leafre
		map = 240030000;
		cost = 55000;
		mapname = "神木村-龙森林路口";
		break;
	    case 220000000: // Ludi
		map = 220050300;
		cost = 45000;
		mapname = "时间通道";
		break;
	    case 211000000: // El Nath
		map = 211040200;
		cost = 45000;
		mapname = "冰雪峽谷II";
		break;
           case 105000000:
                map = 105030000;
		cost = 45000;
		mapname = "另一扇门";
               break;
           case 105030000:
		map = 105000000;
		cost = 30000;
		mapname = "林中之城";
		break;
	    default:
		map = 211040200;
		cost = 45000;
		mapname = "冰雪峽谷II";
		break;
	}
	cm.sendNext("你好！这种子彈出租車将带你從任何危險區域 #m"+cm.getMapId()+"# 到 #b#m"+map+"##k 再 "+mapname+"! 运輸費用 #b"+cost+" 金币#k 可能看起來很貴，但並不多，当你想方便地通过危險區域运輸!");
    } else if (status == 1) {
	cm.sendYesNo("#b你需要支付金币#k 傳送到 #b#m"+map+"##k?");
    } else if (status == 2) {
	if (cm.getMeso() < cost) {
	    cm.sendNext("你看起來沒啥錢可以支付,很抱歉我们不收乞丐搭車的,滾吧!!!");
	    cm.dispose();
	} else {
	    cm.gainMeso(-cost);
	    cm.warp(map, 0);
	    cm.dispose();
	}
    }
}
