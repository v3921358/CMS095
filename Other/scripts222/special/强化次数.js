var status = 0;
var fstype = 0;
var price = 50000000; //没用
var types = new Array("装备栏", "消耗栏", "任务栏", "杂物栏", "现金栏");
//var chance3 = (Math.floor(Math.random() * 8) + 1);

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
        if (mode == 1) status++;
        if (status == 0) {//#L2#使用点卷升级-1次(10000)(127最高)
            cm.sendSimple("#r注意:请将需要升级的装备放在装备栏第一格!\r\n\r\n\r\n\r\n\r\n\r\n#b#L2#可以使用金铲子#v5570000#X10 在为装备扣一个眼(最高30次)\r\n");


        } else if (status == 1) {

            if (selection == 2) { //使用点卷升级//#L2#使用点卷升级-3次(5000)(127最高)
                fstype = 2;
                cm.sendNext("你确定给我金锤子#v5570000#X10？\r\n那我就为你的宝贝装备扣1个眼了\r\n百分之百成功，童叟无欺，贵是贵了点，但是我服务好呀！！");


            } else if (selection == 3) { //使用五彩升级
                fstype = 3;
                cm.sendNext("你要使用金币为装备增加1个升级次数吗?");

            }

        } else if (status == 2) {

            if (fstype == 2) { //使用点卷升级
                var ii = Packages.server.MapleItemInformationProvider.getInstance();
                var item = cm.getInventory(1).getItem(1);
                var statup = new java.util.ArrayList();
                if (item == null) {
                    cm.sendOk("你装备栏第一格没有装备!");
                    cm.dispose();
                } else if (ii.isCash(item.getItemId()) == true) {
                    cm.sendOk("非常抱歉,点装不支持升级!");
                    cm.dispose();
                 } else if (item.getItemId() == 1122000 || item.getItemId() ==1122076) {
                    cm.sendOk("对不起,黑龙项链不能提升次数!");
                    cm.dispose();
                    return;
                }
                   else fstype = 13;		
			}

            if (fstype == 3) { //使用金币升级
                var ii = Packages.server.MapleItemInformationProvider.getInstance();
                var item = cm.getInventory(1).getItem(1);
                var statup = new java.util.ArrayList();
                if (item == null) {
                    cm.sendOk("你装备栏第一格没有装备!");
                    cm.dispose();
                } else if (ii.isCash(item.getItemId()) == true) {
                    cm.sendOk("非常抱歉,点装不支持升级!");
                    cm.dispose();
                 } else if (item.getItemId() == 1122000 || item.getItemId() ==1122076) {
                    cm.sendOk("对不起,黑龙项链不能提升次数!");
                    cm.dispose();
                    return;
                }
				   else fstype = 14;	
            }


             if (fstype == 13) {
var item = cm.getChar().getInventory(Packages.client.inventory.MapleInventoryType.EQUIP).getItem(1).copy();

var id = item.getItemId();
var 已升级次数= item.getLevel();
var 剩余回合 = item.getUpgradeSlots();
var 强化上限 = 30;
var pd = 强化上限 - (已升级次数 + 剩余回合);
if (pd != -1 & pd > 0){



                if (!cm.haveItem(5570000, 10)) {
                    cm.sendOk("你在开玩笑！吗?收你十个锤子，命都给你好不好~~~~~何况你连十个锤子都没有");
                    cm.dispose();
                    return;
                }
			else if (cm.getMeso() < 1)  {
                    cm.sendOk("你的金币不足1!");
                    cm.dispose();
                    return;
                }
			//	var chance = Math.floor(Math.random() * 2);
               // if (chance == 2) {
                    var item = cm.getChar().getInventory(Packages.client.inventory.MapleInventoryType.EQUIP).getItem(1).copy();
                    var statup = new java.util.ArrayList();
                    item.setUpgradeSlots((item.getUpgradeSlots() + 1));
                    Packages.server.MapleInventoryManipulator.removeFromSlot(cm.getC(), Packages.client.inventory.MapleInventoryType.EQUIP, 1, 1, false);
                    Packages.server.MapleInventoryManipulator.addFromDrop(cm.getC(), item, false);
                    cm.gainItem(5570000,-10);
					//cm.gainMeso(-30000000);
                    cm.sendOk("扣眼成功！");
	                cm.worldMessage(6,"【眼哥强化】恭喜["+cm.getName()+"]在眼哥那，给宝贝装备又扣了1个眼,大家一起恭喜他(她)!");
                    cm.dispose();
                    }			
			 //  else {
              //      cm.gainNX(-100);
               //     cm.sendOk("真遗憾，升级失败，扣除手续费100点卷");
				//	cm.dispose();
             //   }
            }
		else if (pd == 0){ cm.sendOk("眼哥也表示无能为力~~眼子太多了!!");cm.dispose();}

}


             if (fstype == 14) {
var item = cm.getChar().getInventory(Packages.client.inventory.MapleInventoryType.EQUIP).getItem(1).copy();

var id = item.getItemId();
var 已升级次数= item.getLevel();
var 剩余回合 = item.getUpgradeSlots();
var 强化上限 = 30;
var pd = 强化上限 - (已升级次数 + 剩余回合);
if (pd != -1 & pd > 0){


		if (cm.getMeso() < 20000000)  {
                    cm.sendOk("你的金币不足!");
                    cm.dispose();
                    return;
                }
			//	var chance = Math.floor(Math.random() * 2);
            //    if (chance == 1) {
                    var item = cm.getChar().getInventory(Packages.client.inventory.MapleInventoryType.EQUIP).getItem(1).copy();
                    var statup = new java.util.ArrayList();
                    item.setUpgradeSlots((item.getUpgradeSlots() + 1));
                  // item.setLocked(1);
 Packages.server.MapleInventoryManipulator.removeFromSlot(cm.getC(), Packages.client.inventory.MapleInventoryType.EQUIP, 1, 1, false);
                    Packages.server.MapleInventoryManipulator.addFromDrop(cm.getC(), item, false);           
	                cm.gainMeso(-20000000);
                    cm.sendOk("升级成功！");
	                cm.worldMessage(6,"玩家["+cm.getName()+"]使用金币给装备升级了1个次数,大家一起恭喜他(她)!");
                    cm.dispose();
			}
		//	else {
           //         cm.gainMeso(-2000000);
            //        cm.sendOk("真遗憾，升级失败，扣除手续费2000000金币");
				//	cm.dispose();
                //}
			}
		else if (pd <= 0){ cm.sendOk("已到达上限，无法强化！");cm.dispose();}
}
			}
		//	}
			//}