/*
吃饱闲着自制脚本
 */
var status = 0;
var 选择的装备;
var 获取选择的装备星级;
var 获取选择的装备;
var 获取选择的装备代码;
var 分解;
var 判定级别;
var 成功率;
var 四维 = 1;

function start() {
    status = -1;
    action(1, 0, 0)
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
    if (status == 0) {
		var selStr = "#r#e                 <装备分解>#n\r\n\r\n#b星级别越高#r分解的#v4031344##b#t4031344##r就会越多\r\n\r\n\r\n\r\n\r\n";
		selStr += "           #e#L999##r进行分解#n#l\r\n\r\n\r\n";
	//	selStr += "#k请选择以下#d背包中的装备#k进行分解：\r\n\r\n\r\n";
		for (var i = 0; i < 96; i++) {
        if (cm.getInventory(1).getItem(i) != null && cm.isCash(cm.getInventory(1).getItem(i).getItemId()) == true) {
            selStr += "";
			if (cm.getInventory(1).getItem(i).getHpR() >= 1) {
			selStr += ""
			
		
			}
		}
	}
	
        cm.sendSimple(selStr);
    } else if (status == 1) {
		选择的装备 = selection;
		if (选择的装备 >= 1 && 选择的装备 <= 96) {
			获取选择的装备星级 = cm.getInventory(1).getItem(选择的装备).getEnhance();
			获取选择的装备阶级 = cm.getInventory(1).getItem(选择的装备).getHpR();
			获取选择的装备 = cm.getInventory(1).getItem(选择的装备);
			获取选择的装备代码 = cm.getInventory(1).getItem(选择的装备).getItemId();
			if (获取选择的装备星级 < 15) {
			var selStr = "";
             selStr += "#k选择的装备为: #v"+获取选择的装备代码+"##r#z"+获取选择的装备代码+"# \r\n"
			 selStr += "#k当前属性: #r星级 "+获取选择的装备.getEnhance()+" 级\r\n"
			 selStr += "          #b力量 "+获取选择的装备.getStr()+"\r\n"
			 selStr += "          #b敏捷 "+获取选择的装备.getDex()+"\r\n"
			 selStr += "          #b智力 "+获取选择的装备.getInt()+"\r\n"
			 selStr += "          #b运气 "+获取选择的装备.getLuk()+"\r\n"
			 selStr += "#r下一级需要消耗: "+(获取选择的装备星级*3+3)+"个#z4031344##v4031344#\r\n"
			  selStr += "#r      需要消耗: 5000点卷 \r\n"
			
			 selStr += "#e#b#L999#开始分解装备#l     #L1000#退出分解系统#l\r\n "
				cm.sendSimple(selStr);
			} else {
				cm.sendOk("#r装备已满星 可选择分解...\r\n#b若分解成功可继续分解\r\n若分解失败 星级清空 属性减少！");
				cm.dispose();
			}
		}
		if (选择的装备 == 99) {
			cm.dispose();
			}
		if (选择的装备 == 999) {
			selStr = "#k请选择以下#d背包中的装备#k进行分解：\r\n";
		for (var i = 0; i < 96; i++) {
        if (cm.getInventory(1).getItem(i) != null && cm.isCash(cm.getInventory(1).getItem(i).getItemId()) == 0) {
            selStr += "#L" + i + "##b#v" + cm.getInventory(1).getItem(i).getItemId() + "##z" + cm.getInventory(1).getItem(i).getItemId() + "##k ";
			if (cm.getInventory(1).getItem(i).getHpR() >= 1) {
			selStr += " #k级别:#r" + cm.getInventory(1).getItem(i).getOwner() + "#l#k\r\n"
			}
			if (cm.getInventory(1).getItem(i).getHpR() == 0) {
			selStr += " #k级别:#r无#l#b\r\n"
			}
		}
	}
	     selStr += "\r\n        #L99# #d将需要分解的装备放置背包中";
		 分解 = 1;
        cm.sendSimple(selStr);
			}
      } else if (status == 2) {
		 if  (分解 == 1){
			 选择的装备 = selection;
		if (选择的装备 >= 1 && 选择的装备 <= 96) {
			获取选择的装备星级 = cm.getInventory(1).getItem(选择的装备).getEnhance();
			获取选择的装备阶级 = cm.getInventory(1).getItem(选择的装备).getHpR();
			获取选择的装备 = cm.getInventory(1).getItem(选择的装备);
			获取选择的装备代码 = cm.getInventory(1).getItem(选择的装备).getItemId();
			if (获取选择的装备.getHpR() == 0){
			判定级别 = "破旧";
			成功率 = 60;
		}
		if (获取选择的装备.getHpR() == 1){
			判定级别 = "青铜";
			成功率 = 50;
		}
		if (获取选择的装备.getHpR() == 2){
			判定级别 = "白银";
			成功率 = 50;
		}
		if (获取选择的装备.getHpR() == 3){
			判定级别 = "白金";
			成功率 = 40;
		}
		if (获取选择的装备.getHpR() == 4){
			判定级别 = "橙金";
			成功率 = 40;
		}
		if (获取选择的装备.getHpR() == 5){
			判定级别 = "紫金";
			成功率 = 30;
		}
		if (获取选择的装备.getHpR() == 6){
			判定级别 = "超越";
			成功率 = 30;
		}
		if (获取选择的装备.getHpR() == 7){
			判定级别 = "";
			成功率 = 20;
		}
		if (获取选择的装备.getHpR() == 8){
			判定级别 = "终极";
			成功率 = 20;
		}
		if (获取选择的装备.getHpR() == 9){
			判定级别 = "越级";
			成功率 = 10;
		}
		if (获取选择的装备.getHpR() == 10){
			判定级别 = "毕业";
			成功率 = 10;
		}
			if (获取选择的装备.getHpR() >= 1) {
			if (获取选择的装备.getHpR() <= 10) {
			var selStr = "";
             selStr += "#k选择的装备为: #v"+获取选择的装备代码+"##d#t"+获取选择的装备代码+"# \r\n\r\n"
			 selStr += "#k当前属性:#r"+获取选择的装备.getOwner()+"  =  "+获取选择的装备.getHpR()+" \r\n"
			 selStr += "#k分解可获得: #r#v4031344##z4031344#  "+获取选择的装备.getHpR()+" *300 #d(分解材料)\r\n\r\n\r\n"
			
			 selStr += "#e#r#L999#开始分解装备#l     #L1000#退出分解系统#l\r\n "
			 分解 = 2;
				cm.sendSimple(selStr);
			} else {
				cm.sendOk("#r当前阶级已经为满级...");
				cm.dispose();
			}
			} else {
				cm.sendOk("#r只有当装备有#b拥有级别#r才可进行分解...");
				cm.dispose();
			}
		}
		if (选择的装备 == 99) {
			cm.dispose();
			}
		 }
		 if (selection == 999) {
		if (cm.itemQuantity(4031344) >= (获取选择的装备星级*3+4)) {
			if (cm.getPlayer().getCSPoints(1) > 5000){
        		cm.gainNX(-5000);		
					var 拷贝装备 = 获取选择的装备.copy();
					拷贝装备.setStr(拷贝装备.getStr()+四维);
					拷贝装备.setDex(拷贝装备.getDex()+四维);
					拷贝装备.setInt(拷贝装备.getInt()+四维);
					拷贝装备.setLuk(拷贝装备.getLuk()+四维);
					//拷贝装备.setWatk(拷贝装备.getWatk()+1);
					//拷贝装备.setMatk(拷贝装备.getMatk()+1);
					拷贝装备.setEnhance(拷贝装备.getEnhance()+1);//星级
					cm.removeSlot(1, 选择的装备, 1);//消失的装备
					Packages.server.MapleInventoryManipulator.addFromDrop(cm.getC(), 拷贝装备, false);
					cm.playerMessage(1, "【装备星之力分解】\r\n\r\n恭喜您\r\n\r\n分解成功 星级:"+拷贝装备.getEnhance()+"星");
					cm.gainItem(4031344, -(获取选择的装备星级*3+3));}
					else {
                cm.sendOk("你的点卷不足5000.");
                cm.dispose();
            }
		} else {
				 cm.sendOk("#r分解所需要消耗的#b星星#r不足！");
			 }
			 cm.dispose();
		 }
		 if (selection == 1000) {
			cm.dispose();
			}
		 } else if (status == 3) {
		if  (分解 == 2){
		 if (selection == 999) {
		 	if (cm.getPlayer().getCSPoints(1) > 0){
        		cm.gainNX(-0);		
		var 随机数 = Math.floor(Math.random()*100);
		if (100 >= 随机数) {
		var 拷贝装备 = 获取选择的装备.copy();
	
		 cm.gainItem(4031344,获取选择的装备.getHpR()*300);
		
		cm.removeSlot(1, 选择的装备, 1);//消失的装备
		
		cm.playerMessage(1, "【装备星之力分解】\r\n\r\n恭喜您\r\n\r\n获得相应的材料");
		} else {
			var 拷贝装备 = 获取选择的装备.copy();
			拷贝装备.setEnhance(0);//星级
			拷贝装备.setStr(拷贝装备.getStr()-15*四维);
			拷贝装备.setDex(拷贝装备.getDex()-15*四维);
			拷贝装备.setInt(拷贝装备.getInt()-15*四维);
			拷贝装备.setLuk(拷贝装备.getLuk()-15*四维);
			cm.removeSlot(1, 选择的装备, 1);//消失的装备
			Packages.server.MapleInventoryManipulator.addFromDrop(cm.getC(), 拷贝装备, false);
			cm.sendOk("#r分解失败 星之力清空 四维属性减少#b"+15*四维+"#r点！！！");
			 }}
			 else {
                cm.sendOk("你的点卷不足50000.");
                cm.dispose();
            }
			 cm.dispose();
		 }
		 if (selection == 1000) {
			cm.dispose();
			}
		 }
		 }
		  }
		  }