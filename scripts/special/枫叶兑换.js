var 爱心 = "#fEffect/CharacterEff/1022223/4/0#";
var 红色箭头 = "#fUI/UIWindow/Quest/icon6/7#";
var 正方形 = "#fUI/UIWindow/Quest/icon3/6#";
var 蓝色箭头 = "#fUI/UIWindow/Quest/icon2/7#";

function start() {
    status = -1;

    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    }
    else {
        if (status >= 0 && mode == 0) {

            cm.sendOk("感谢你的光临！");
            cm.dispose();
            return;
        }
        if (mode == 1) {
            status++;
        }
        else {
            status--;
        }
		var 等级 = cm.getPlayer().getLevel();
        if (status == 0) {
            var tex2 = "";
            var text = "";
            for (i = 0; i < 10; i++) {
                text += "您好在我这里可以兑换#b点券#k\r\n\r\n";
				text += "" + 蓝色箭头 + "#L2##b#v4001126#*30#k 兑换#r 300点券#k#l \r\n\r\n";
                text += "" + 蓝色箭头 + "#L3##b#v4001126#*100#k 兑换#r 1000点券#k#l \r\n\r\n";
                text += "" + 蓝色箭头 + "#L4##b#v4001126#*500#k 兑换#r 5000点券#k#l \r\n\r\n";      
                text += "" + 蓝色箭头 + "#L5##b#v4001126#*1000#k 兑换#r 1W点券#k#l \r\n\r\n";
			    text += "" + 蓝色箭头 + "#L6##b#v4001126#*3000#k 兑换#r 3W点券#k#l \r\n\r\n";
				text += "" + 蓝色箭头 + "#L7##b#v4001126#*10000#k 兑换#r 10W点券#k#l \r\n\r\n";
				
               
                cm.sendSimple(text);
            }
			
			} else if (selection == 2) {
			if (cm.haveItem(4001126,30)) {//判断点券数量
                cm.gainItem(4001126, -30);//扣除点券数量
               
				cm.gainNX(300);
				 cm.sendOk("换购成功！");
                cm.dispose();//结束
            } else {
                cm.sendOk("#v4001126#不足无法换购！");
                cm.dispose();
            }
			
			
			} else if (selection == 3) {
			if (cm.haveItem(4001126,100)) {//判断点券数量
                cm.gainItem(4001126, -100);//扣除点券数量
               
				cm.gainNX(1000);
				 cm.sendOk("换购成功！");
                cm.dispose();//结束
            } else {
                cm.sendOk("#v4001126#不足无法换购！");
                cm.dispose();
            }
			
			
			} else if (selection == 4) {
			if (cm.haveItem(4001126,500)) {//判断点券数量
                cm.gainItem(4001126, -500);//扣除点券数量
               
				cm.gainNX(5000);
				 cm.sendOk("换购成功！");
                cm.dispose();//结束
            } else {
                cm.sendOk("#v4001126#不足无法换购！");
                cm.dispose();
            }
			
			
			} else if (selection == 5) {
			if (cm.haveItem(4001126,1000)) {//判断点券数量
                cm.gainItem(4001126, -1000);//扣除点券数量
               
				cm.gainNX(10000);
				 cm.sendOk("换购成功！");
                cm.dispose();//结束
            } else {
                cm.sendOk("#v4001126#不足无法换购！");
                cm.dispose();
            }
			
			
			} else if (selection == 6) {
			if (cm.haveItem(4001126,3000)) {//判断点券数量
                cm.gainItem(4001126, -3000);//扣除点券数量
               
				cm.gainNX(30000);
				 cm.sendOk("换购成功！");
                cm.dispose();//结束
            } else {
                cm.sendOk("#v4001126#不足无法换购！");
                cm.dispose();
            }
			
			} else if (selection == 7) {
			if (cm.haveItem(4001126,10000)) {//判断点券数量
                cm.gainItem(4001126, -10000);//扣除点券数量
               
				cm.gainNX(100000);
				 cm.sendOk("换购成功！");
                cm.dispose();//结束
            } else {
                cm.sendOk("#v4001126#不足无法换购！");
                cm.dispose();
            }
			
			
			
			
			
			 } else if (selection == 33) {
            if (cm.haveItem(4031250) >= 1) {
				cm.gainItem(4031250, -1);
                cm.gainNX(500);
                //cm.gainItem(2340000, 1);
				cm.sendOk("换购成功！");
                cm.dispose();
            } else {
                cm.sendOk("#v4031250#不足无法换购！");
                cm.dispose();
            }

			
			 } else if (selection == 31) {
            //if (cm.getPlayer().getNX() >= 100) {
				if(cm.haveItem(4002000,1)){
					cm.gainItem(4002000, -1);
					cm.gainExp(等级 * 2500);
                cm.gainNX(30);
                //cm.gainNX(-100);
                //cm.gainItem(4001126, 1);
				cm.sendOk("换购成功！");
                cm.dispose();
            } else {
                cm.sendOk("#v4002000#不足无法换购！");
                cm.dispose();
            }
			
			
			
			

        } else if (selection == 116) {
            if (cm.getPlayer().getNX() >= 3000 && cm.transferCashEquipStat(1, 2)) {
                //if( cm.transferCashEquipStat(1,2) ) {
                cm.gainNX(-3000);
                //cm.worldMessage(6,"【兑换系统】["+cm.getName()+"]点装置换成功！");
                cm.sendOk("点装置换成功！");
            } else {
                cm.sendOk("点装置换失败！装备栏第一格和第二个必须都是点装才行！");
            }

        } else if (selection == 13) {
            //if (cm.getmoneyb() >= 15) {
				if(cm.haveItem(4002002,1)){
                cm.gainItem(4002002, -1);
				cm.gainExp(等级 * 1000);
				cm.gainNX(+10);//获得物品
                //cm.gainjf(+15);
                //cm.increaseCharacterSlots(0);//不需扣除点券
				cm.sendOk("换购成功！");
                cm.dispose();
            } else {
                cm.sendOk("#v4002002#不足无法换购！");
                cm.dispose();
            }

        } else if (selection == 12) {
            //if (cm.getPlayer().getNX() >= 3500) {
				if(cm.haveItem(4002003,1)){
					cm.gainItem(4002003, -1);
					cm.gainExp(等级 * 1500);
                cm.gainNX(20);
                //cm.gainItem(5520000, 1);
				cm.sendOk("换购成功！");
                cm.dispose();
            } else {
                cm.sendOk("#v4002003#不足无法换购！");
                cm.dispose();
            }

        } else if (selection == 15) {
            //if (cm.getPlayer().getNX() >= 2000) {
				if(cm.haveItem(4002001,1)){
					cm.gainItem(4002001, -1);
					cm.gainExp(等级 * 2000);
                cm.gainNX(25);
                //cm.gainItem(5570000, 1);
				cm.sendOk("换购成功！");
                cm.dispose();
            } else {
                cm.sendOk("#v4002001#不足无法换购！");
                cm.dispose();
            }

        } else if (selection == 1) {
            if (cm.getPlayer().getNX() >= 200) {
                cm.gainNX(-200);
                cm.gainItem(5211047, 1, 2);                
                cm.dispose();
            } else {
                cm.sendOk("点券不足无法换购！");
                cm.dispose();
            }
        } else if (selection == 2) {
            if (cm.getPlayer().getNX() >= 300) {
                cm.gainNX(-300);
                cm.gainItem(5211047, 1, 10);
                cm.dispose();
            } else {
                cm.sendOk("点券不足无法换购！");
                cm.dispose();
            }
        } else if (selection == 3) {
			if (cm.haveItem(4001126) >= 1) {//判断点券数量
                cm.gainItem(4001126, -1);//扣除点券数量
               
				cm.gainNX(1000);
				 cm.sendOk("换购成功！");
                cm.dispose();//结束
            } else {
                cm.sendOk("#v4001126#不足无法换购！");
                cm.dispose();
            }

			 } else if (selection == 33) {
            if (cm.getPlayer().getNX() >= 200) {
                //cm.gainNX(-200);
                //cm.gainItem(5211047, 1, 3);
                cm.dispose();
            } else {
                cm.sendOk("点券不足无法换购！");
                cm.dispose();
            }
			
        } else if (selection == 117) {
			 if  (cm.getBossLog('双倍卡') > 1) {
                    cm.sendOk("你今天已经购买过两次了.");
                    cm.dispose();
           } else if (cm.getPlayer().getNX() >= 200) {
                cm.gainNX(-200);
                cm.gainItem(5211047, 1, 3);
				cm.setBossLog('双倍卡');//记录
				 cm.sendOk("换购成功！");
                cm.dispose();
            } else {
                cm.sendOk("点券不足无法换购！");
                cm.dispose();
            }

        } else if (selection == 118) {
            if (cm.getPlayer().getNX() >= 5000) {
                cm.gainNX(-5000);
                cm.gainItem(5211047, 1, 168);
                cm.dispose();
            } else {
                cm.sendOk("点券不足无法换购！");
                cm.dispose();
            }

        } else if (selection == 4) {
            if (cm.getPlayer().getNX() >= 2000) {
                cm.gainNX(-2000);
                cm.gainItem(5211047, 1, 168);
                cm.dispose();
            } else {
                cm.sendOk("点券不足无法换购！");
                cm.dispose();
            }
        } else if (selection == 5) {
            if (cm.getPlayer().getNX() >= 150) {
                cm.gainNX(-150);
                cm.gainItem(5360014, 1, 3);
                cm.dispose();
            } else {
                cm.sendOk("点券不足无法换购！");
                cm.dispose();
            }

        } else if (selection == 14) {
            if (cm.getmoneyb() >= 44) {
                cm.setmoneyb(-44);
                cm.gainjf(+44);
                cm.gainItem(5211060, 1, 168);
                cm.dispose();
            } else {
                cm.sendOk("点券不足无法换购！");
                cm.dispose();
            }
        } else if (selection == 6) {
            if (cm.getPlayer().getNX() >= 300) {
                cm.gainNX(-300);
                cm.gainItem(5360014, 1, 10);
                cm.dispose();
            } else {
                cm.sendOk("点券不足无法换购！");
                cm.dispose();
            }
        } else if (selection == 7) {
            if (cm.getPlayer().getNX() >= 600) {
                cm.gainNX(-600);
                cm.gainItem(5360014, 1, 24);
                cm.dispose();
            } else {
                cm.sendOk("点券不足无法换购！");
                cm.dispose();
            }
        } else if (selection == 8) {
            if (cm.getPlayer().getNX() >= 2000) {
                cm.gainNX(-2000);
                cm.gainItem(5360014, 1, 168);
                cm.dispose();
            } else {
                cm.sendOk("点券不足无法换购！");
                cm.dispose();
            }
        } else if (selection == 9) {
            if (cm.getPlayer().getNX() >= 10000) {
                cm.gainNX(-10000);
               cm.gainDY(15000);
				 cm.sendOk("换购成功！");
                cm.dispose();
            } else {
                cm.sendOk("点券不足无法换购！");
                cm.dispose();
            }
        } else if (selection == 119) {
            if (cm.getPlayer().getNX() >= 2000) {
                cm.gainNX(-2000);
                cm.gainItem(5360015, 1, 24);
                cm.dispose();
            } else {
                cm.sendOk("点券不足无法换购！");
                cm.dispose();
            }

        } else if (selection == 120) {
            if (cm.getPlayer().getNX() >= 5000) {
                cm.gainNX(-5000);
                cm.gainItem(5360016, 1, 168);
                cm.dispose();
            } else {
                cm.sendOk("点券不足无法换购！");
                cm.dispose();
            }
			
			 } else if (selection == 121) {
            if (cm.getPlayer().getNX() >= 5000) {
                cm.gainNX(-5000);
                cm.gainItem(4032246, 1);
                cm.dispose();
            } else {
                cm.sendOk("点券不足无法换购！");
                cm.dispose();
            }

        } else if (selection == 10) {
            if (cm.getPlayer().getDY() >= 600) {
                cm.gainDY(-600);
                cm.gainItem(5360014, 1, 3);
                cm.dispose();
            } else {
                cm.sendOk("抵用卷不足无法换购！");
                cm.dispose();
            }
        }
    }
}


