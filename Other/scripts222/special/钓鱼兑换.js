

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
     cm.sendSimple ("您好，尊敬的 #b#h ##k, 我是#r兑换系统#k负责人\r\n\r\n您目前有#r金币#k： #e#r" + cm.getMeso() + "#n\r\n\r\n     #L50#15条#v4031640##r（113cm）#k兑换#v2049100##r1张#k#n#l\r\n     #L53#15条#v4031648##r（288cm）#k#d兑换#v2049100##r1张#k#n#l\r\n     #L57#15条#d#v4031644##r(148cm)#k#d兑换#v2040025##r1张#k#n#l\r\n      #L48#15条#v4031636##r(10cm)#k#d兑换#v2040804##r1张#k#n#l\r\n      #n#L49#20条#d#v4031640##r（113cm）#k#d兑换#v1112907##r1枚#k#n#l\r\n      #L51#1个#d#v4031629##r锅子#k#d兑换#v2000005#10瓶#k#n#l");   
        } else if (status == 1) {
            switch(selection) {
				case 62: 
/*             if(cm.haveItem(4031636,1)){
                                cm.gainItem(4031636,-30);
                                cm.gainItem(4031648,-30);
				cm.gainItem(4031644,-30);
				cm.gainItem(2340000,1);
				cm.sendOk("恭喜你，你获得了 1张#v2340000#! .");
			        cm.worldMessage(6,"【兑换系统】玩家["+cm.getName()+"]兑换祝福卷轴！");
				cm.dispose();
            }else{
                cm.sendOk("你没有 足够的 鱼，我不能给你换购~.");
                cm.dispose();
            } */
            break;
				case 50: 
            if(cm.haveItem(4031640,1)){   
				cm.gainItem(4031632,-15);
				cm.gainItem(2049100,1);
				cm.sendOk("恭喜你，你获得了 1张#v2049100#! .");
			        cm.worldMessage(6,"【兑换系统】玩家["+cm.getName()+"]兑换混沌卷轴！");
				cm.dispose();
            }else{
                cm.sendOk("你没有 足够的 鱼，我不能给你换购~.");
                cm.dispose();
            }
            break;
				case 53: 
            if(cm.haveItem(4031640,1)){   
				cm.gainItem(4031640,-15);
				cm.gainItem(2049100,1);
				cm.sendOk("恭喜你，你获得了  1张#v2049100#! .");
			        cm.worldMessage(6,"【兑换系统】玩家["+cm.getName()+"]兑换混沌卷轴1张！");
				cm.dispose();
            }else{
                cm.sendOk("你没有 足够的 鱼，我不能给你换购~.");
                cm.dispose();
            }
            break;
			case 51: 
                 if(cm.haveItem(4031629,1)){   
				cm.gainItem(4031629,-1);
				cm.gainItem(2000005,1);
				cm.sendOk("恭喜你，你获得了 10瓶#v2000005#! .");
			        cm.worldMessage(6,"【兑换系统】玩家["+cm.getName()+"]兑换超级药水10瓶！");
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
			        cm.worldMessage(6,"【兑换系统】玩家["+cm.getName()+"]兑换手套攻击一张！");
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
			        cm.worldMessage(6,"【兑换系统】玩家["+cm.getName()+"]兑换小鱼戒指1枚！");
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
			        cm.worldMessage(6,"【兑换系统】玩家["+cm.getName()+"]兑换超级药水10瓶！");
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
			        cm.worldMessage(6,"【兑换系统】玩家["+cm.getName()+"]兑换超级药水10瓶！");
				cm.dispose();
            }else{
                cm.sendOk("你没有 足够的 鱼，我不能给你换购~.");
                cm.dispose();
            }
            break;
			case 57: 
            if(cm.haveItem(4031644,1)){   
				cm.gainItem(4031644,-1);
				cm.gainItem(2000005,100);
				cm.sendOk("恭喜你，你获得了 100瓶#v2000005#! .");
			        cm.worldMessage(6,"【兑换系统】玩家["+cm.getName()+"]兑换超级药水100瓶！");
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
			        cm.worldMessage(6,"【兑换系统】玩家["+cm.getName()+"]兑换超级药水10瓶！");
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
			        cm.worldMessage(6,"【兑换系统】玩家["+cm.getName()+"]兑换超级药水10瓶！");
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
			        cm.worldMessage(6,"【兑换系统】玩家["+cm.getName()+"]兑换超级药水10瓶！");
				cm.dispose();
            }else{
                cm.sendOk("你没有 足够的 鱼，我不能给你换购~.");
                cm.dispose();
            }
            break;
			case 61: 
            if(cm.haveItem(4001159,1)){   
				cm.gainItem(4000040,-1);
				cm.gainItem(5460000,1);
				cm.sendOk("恭喜你，你获得了 1个#v5460000#! .");
			        cm.worldMessage(6,"【兑换系统】玩家["+cm.getName()+"]兑换了宠物的点心！");
				cm.dispose();
            }else{
                cm.sendOk("你没有 足够的 材料，我不能给你兑换~.");
                cm.dispose();
		    }
			break;
			case 62: 
            if(cm.haveItem(4001187,1)&& cm.haveItem(4001188,1) && cm.haveItem(4001189,1)){
				cm.gainItem(4001187,-1);
				cm.gainItem(4001188,-1);
				cm.gainItem(4001189,-1);
				cm.gainItem(2000005,10);
				cm.sendOk("恭喜你，你获得了 10瓶#v2000005#! .");
			        cm.worldMessage(6,"【兑换系统】玩家["+cm.getName()+"]兑换超级药水10瓶！");
				cm.dispose();
            }else{
                cm.sendOk("你没有 足够的 材料，我不能给你兑换~.");
                cm.dispose();
		    }
			break;
        case 0:
            if((cm.getPlayer().getCSPoints(1) >= 3000)){
                //cm.gainDY(100);             
				 cm.gainNX(-3000);
				cm.gainItem(4001126,10);
				cm.sendOk("恭喜你，兑换成功 .");
			        cm.worldMessage(6,"【兑换系统】玩家["+cm.getName()+"]3000点兑换国庆成功！");
				cm.dispose();
            }else{
                cm.sendOk("你没有 足够的点卷 ，我不能给你换购~.");
                cm.dispose();
            }
            break;
        case 1: 
            if(cm.haveItem(4001126,1000)){
                //cm.gainDY(100);
                cm.gainNX(3000);
				cm.gainItem(4001126,-1000);
				cm.sendOk("恭喜你，你获得了 3000 点券! .");
			        cm.worldMessage(6,"【兑换系统】玩家["+cm.getName()+"]1000张枫叶兑换3000点卷成功！");
				cm.dispose();
            }else{
                cm.sendOk("你没有 足够的 枫叶，我不能给你换购~.");
                cm.dispose();
            }
            break;
         case 2: 
            if(cm.haveItem(4001126,100)){
                //cm.gainDY(100);
                cm.gainNX(300);
				cm.gainItem(4001126,-100);
				cm.sendOk("恭喜你，你获得了 300 点券! .");
			        cm.worldMessage(6,"【兑换系统】玩家["+cm.getName()+"]100张枫叶兑换300点卷成功！");
				cm.dispose();
            }else{
                cm.sendOk("你没有 足够的 枫叶，我不能给你换购~.");
                cm.dispose();
            }
            break;
            case 3: 
            if((cm.getPlayer().getCSPoints(1) >= 300)){
                //cm.gainDY(100);             
				 cm.gainNX(-300);
				cm.gainItem(4001126,1);
				cm.sendOk("恭喜你，兑换成功 .");
			        cm.worldMessage(6,"【兑换系统】玩家["+cm.getName()+"]300点兑换国庆成功！");
				cm.dispose();
            }else{
                cm.sendOk("你没有 足够的点卷 ，我不能给你换购~.");
                cm.dispose();
            }
            break;
            case 4: 
            if(cm.haveItem(4001126,10)){              
				cm.gainItem(4001126,-10);
				cm.gainNX(2600);
				cm.sendOk("恭喜你，你获得了 2600点卷! .");
			        cm.worldMessage(6,"【兑换系统】玩家["+cm.getName()+"]国庆兑换2600点卷成功！");
				cm.dispose();
            }else{
                cm.sendOk("你没有 足够的 国庆币，我不能给你换购~.");
                cm.dispose();
            }
            break;
            case 5: 
            if(cm.haveItem(4001126,30)){
               // cm.gainDY(100);
                cm.gainMeso(1000000);
				cm.gainItem(4001126,-30);
				cm.sendOk("恭喜你，你获得了 100w 金币! .");
			        cm.worldMessage(6,"【兑换系统】玩家["+cm.getName()+"]30张枫叶兑换100W金币成功！");
				cm.dispose();
            }else{
                cm.sendOk("你没有 足够的 枫叶，我不能给你换购~.");
                cm.dispose();
            }
            break;
           case 6: 
            if(cm.haveItem(4001126,1)){              
				cm.gainItem(4001126,-1);
				cm.gainNX(260);
				cm.sendOk("恭喜你，你获得了 260点卷! .");
			        cm.worldMessage(6,"【兑换系统】玩家["+cm.getName()+"]国庆兑换260点卷成功！");
				cm.dispose();
            }else{
                cm.sendOk("你没有 足够的 国庆币，我不能给你换购~.");
                cm.dispose();
            }
            break;
            case 7: 
            if(cm.getMeso() >= 10000000){
                cm.sendOk("恭喜你，你获得了 2000000 经验值! .");
                cm.gainMeso(-10000000);
                cm.gainExp(2000000);
                cm.dispose();
            }else{
                cm.sendOk("你没有 10000000 金币，我不能给你换购~.");
                cm.dispose();
            }
            break;
            case 8: 
            if(cm.getMeso() >= 100000000){
                cm.sendOk("恭喜你，你获得了 50000000 经验值! .");
                cm.gainMeso(-100000000);
                cm.gainExp(50000000);
                cm.dispose();
            }else{
                cm.sendOk("你没有 1亿 金币，我不能给你换购~.");
                cm.dispose();
            }
            break;
            case 9: 
                cm.openNpc(9270052, 0);
            }
        }
    }
}