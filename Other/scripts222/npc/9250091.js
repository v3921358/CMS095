

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
            return;
        }
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 0) {
			var txt = "";
			txt += "您好，尊敬的 #b#h ##k, 我是#r钓鱼兑换#k负责人\r\n\r\n您目前有#r金币#k： #e" + cm.getMeso() + "#n\r\n\r\n ";
			txt += "#L50#15条#v4031640##r（113cm）#k兑换#v2049100##r1张#k#n#l\r\n";
			txt += "#L53#15条#v4031627##r（3cm）#k#d兑换#v2000004##r10瓶#k#n#l\r\n";
			txt += "#L54#15条#v4031628##r（120cm）#k#d兑换#v2000004##r10瓶#k#n#l\r\n";
			txt += "#L57#15条#d#v4031644##r(148cm)#k#d兑换#v2040025##r1张#k#n#l\r\n";
			txt += "#L48#15条#v4031636##r(10cm)#k#d兑换#v2040804##r1张#k#n#l\r\n";
			txt += "#n#L49#20条#d#v4031640##r（113cm）#k#d兑换#v1112907##r1枚#k#n#l\r\n";
			txt += "#L52#1个#d#v4031632##r铲子#k#d兑换#v2000005##r10瓶#k#n#l\r\n";
			txt += "#L51#1个#d#v4031629##r锅子#k#d兑换#v2000005#10瓶#k#n#l";
     cm.sendSimple (txt);   
        } else if (status == 1) {
            switch(selection) {
				case 50: 
            if(cm.haveItem(4031632,1)){   
				cm.gainItem(4031632,-1);
				cm.gainItem(2049100,1);
				cm.sendOk("恭喜你，你获得了 1张#v2049100#! .");
			        cm.worldMessage(6,"【钓鱼兑换】玩家["+cm.getName()+"]兑换混沌卷轴！");
				cm.dispose();
            }else{
                cm.sendOk("你没有 足够的 鱼，我不能给你换购~.");
                cm.dispose();
            }
            break;
			case 53: 
            if(cm.haveItem(4031627,15)){   
				cm.gainItem(4031627,-15);
				cm.gainItem(2000004,10);
				cm.sendOk("恭喜你，你获得了 10瓶#v2000004#! .");
			        cm.worldMessage(6,"【钓鱼兑换】玩家["+cm.getName()+"]兑换特殊药水10瓶！");
				cm.dispose();
            }else{
                cm.sendOk("你没有 足够的 鱼，我不能给你换购~.");
                cm.dispose();
            }
            break;
			case 54: 
            if(cm.haveItem(4031628,15)){   
				cm.gainItem(4031628,-15);
				cm.gainItem(2000004,10);
				cm.sendOk("恭喜你，你获得了 10瓶#v2000004#! .");
			        cm.worldMessage(6,"【钓鱼兑换】玩家["+cm.getName()+"]兑换特殊药水10瓶！");
				cm.dispose();
            }else{
                cm.sendOk("你没有 足够的 鱼，我不能给你换购~.");
                cm.dispose();
            }
            break;
			case 51: 
                 if(cm.haveItem(4031629,1)){   
				cm.gainItem(4031629,-1);
				cm.gainItem(2000005,10);
				cm.sendOk("恭喜你，你获得了 10瓶#v2000005#! .");
			        cm.worldMessage(6,"【钓鱼兑换】玩家["+cm.getName()+"]兑换超级药水10瓶！");
				cm.dispose();
            }else{
                cm.sendOk("你没有 足够的 鱼，我不能给你换购~.");
                cm.dispose();
            }
            break;
			case 52: 
                 if(cm.haveItem(4031632,1)){   
				cm.gainItem(4031632,-1);
				cm.gainItem(2000005,10);
				cm.sendOk("恭喜你，你获得了 10瓶#v2000005#! .");
			        cm.worldMessage(6,"【钓鱼兑换】玩家["+cm.getName()+"]兑换超级药水10瓶！");
				cm.dispose();
            }else{
                cm.sendOk("你没有 足够的 鱼，我不能给你换购~.");
                cm.dispose();
            }
            break;
			case 48: 
            if(cm.haveItem(4031636,1)){   
				cm.gainItem(4031636,-15);
				cm.gainItem(2040804,1);
				cm.sendOk("恭喜你，你获得了 1张#v2040804#! .");
			        cm.worldMessage(6,"【钓鱼兑换】玩家["+cm.getName()+"]兑换头盔智力卷轴60%一张！");
				cm.dispose();
            }else{
                cm.sendOk("你没有 足够的 鱼，我不能给你换购~.");
                cm.dispose();
            }
            break;
			case 49: 
            if(cm.haveItem(4031640,1)){   
				cm.gainItem(4031640,-20);
				cm.gainItem(1112907,10);
				cm.sendOk("恭喜你，你获得了1枚#v1112907#! .");
			        cm.worldMessage(6,"【钓鱼兑换】玩家["+cm.getName()+"]兑换小鱼戒指1枚！");
				cm.dispose();
            }else{
                cm.sendOk("你没有 足够的 鱼，我不能给你换购~.");
                cm.dispose();
            }
            break;
			case 55: 
            if(cm.haveItem(4031644,1)){   
				cm.gainItem(4031644,-1);
				cm.gainItem(2000005,10);
				cm.sendOk("恭喜你，你获得了 10瓶#v2000005#! .");
			        cm.worldMessage(6,"【钓鱼兑换】玩家["+cm.getName()+"]兑换超级药水10瓶！");
				cm.dispose();
            }else{
                cm.sendOk("你没有 足够的 鱼，我不能给你换购~.");
                cm.dispose();
            }
            break;
			case 56: 
            if(cm.haveItem(4031644,1)){   
				cm.gainItem(4031644,-1);
				cm.gainItem(2000005,10);
				cm.sendOk("恭喜你，你获得了 10瓶#v2000005#! .");
			        cm.worldMessage(6,"【钓鱼兑换】玩家["+cm.getName()+"]兑换超级药水10瓶！");
				cm.dispose();
            }else{
                cm.sendOk("你没有 足够的 鱼，我不能给你换购~.");
                cm.dispose();
            }
            break;
			case 57: 
            if(cm.haveItem(4031644,15)){   
				cm.gainItem(4031644,-15);
				cm.gainItem(2040025,1);
				cm.sendOk("恭喜你，你获得了 1张#v2040025#! .");
			        cm.worldMessage(6,"【钓鱼兑换】玩家["+cm.getName()+"]兑换手套攻击卷轴60%一张！");
				cm.dispose();
            }else{
                cm.sendOk("你没有 足够的 鱼，我不能给你换购~.");
                cm.dispose();
            }
            break;
			case 58: 
          if(cm.haveItem(4031644,1)){   
				cm.gainItem(4031644,-1);
				cm.gainItem(2000005,10);
				cm.sendOk("恭喜你，你获得了 100瓶#v2000005#! .");
			        cm.worldMessage(6,"【钓鱼兑换】玩家["+cm.getName()+"]兑换超级药水10瓶！");
				cm.dispose();
            }else{
                cm.sendOk("你没有 足够的 鱼，我不能给你换购~.");
                cm.dispose();
            }
            break;
			case 59: 
            if(cm.haveItem(4031636,1)){   
				cm.gainItem(4031636,-1);
				cm.gainItem(2000005,10);
				cm.sendOk("恭喜你，你获得了 10瓶#v2000005#! .");
			        cm.worldMessage(6,"【钓鱼兑换】玩家["+cm.getName()+"]兑换超级药水10瓶！");
				cm.dispose();
            }else{
                cm.sendOk("你没有 点心，我不能给你换购~.");
                cm.dispose();
            }
            break;
			case 60: 
            if(cm.haveItem(4031636,1)){   
				cm.gainItem(4031636,-1);
				cm.gainItem(2000005,10);
				cm.sendOk("恭喜你，你获得了 10瓶#v2000005#! .");
			        cm.worldMessage(6,"【钓鱼兑换】玩家["+cm.getName()+"]兑换超级药水10瓶！");
				cm.dispose();
            }else{
                cm.sendOk("你没有 足够的 鱼，我不能给你换购~.");
                cm.dispose();
            }
            break;
			         
            }
        }
    }
}