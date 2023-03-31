load('nashorn:mozilla_compat.js');
importPackage(Packages.client);
importPackage(Packages.client.inventory);
importPackage(Packages.server);
importPackage(Packages.constants);
importPackage(Packages.tools);
importPackage(Packages.tools.packet);
importPackage(java.util);

var status = 0;
var 心 = "#fUI/GuildMark.img/Mark/Etc/00009001/14#";
var JD = "#fUI/Basic/BtHide3/mouseOver/0#";
var 可强化装备代码 = 1142075;
var 每次强化力量 = 2;
var 每次强化智力 = 2;
var 每次强化敏捷 = 2;
var 每次强化运气 = 2;
var 每次强化攻击 = 2;
var 每次强化魔力 = 2;
var 每次强化命中 = 100;
var 每次强化回避 = 100;
var 消耗物品代码 = 4310023;
var 消耗物品数量 = 4;
var 命中回避消耗物品数量 = 4;
var 力量上限 = 30;
var 敏捷上限 = 30;
var 智力上限 = 30;
var 运气上限 = 30;
var 攻击上限 = 10;
var 魔力上限 = 10;
var 命中上限 = 3000;
var 回避上限 = 3000;
var 攻击魔力消耗物品数量 = 8;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (status >= 0 && mode == 0) {
            cm.dispose();
            return;
        }
        if (mode == 1)
            status++;
        else
            status--;
    if (cm.getInventory(1).getItem(1) == null) {
        cm.sendOk("你的装备栏第一格没有装备。");
        cm.dispose();
        return
    }
    var 装备 = cm.getInventory(1).getItem(1).getItemId();
    if (cm.isCash(cm.getInventory(1).getItem(1).getItemId())) {
        cm.sendOk("你的装备栏第一格 #v" + 装备 + "# 是时装，无法强化。");
        cm.dispose();
        return
    }
	if (cm.getInventory(1).getItem(1).getExpiration() > 0 ) {
        cm.sendOk("你的装备栏第一格 #v" + 装备 + "# 是限时装备不能使用该功能.");
		cm.dispose();
        return
    }

	var 力量 = cm.getInventory(1).getItem(1).getStr();
	var 敏捷 = cm.getInventory(1).getItem(1).getDex();
	var 智力 = cm.getInventory(1).getItem(1).getInt();
	var 运气 = cm.getInventory(1).getItem(1).getLuk();
	var 攻击 = cm.getInventory(1).getItem(1).getWatk();
	var 魔力 = cm.getInventory(1).getItem(1).getMatk();
	var 命中 = cm.getInventory(1).getItem(1).getHp();
	var 回避 = cm.getInventory(1).getItem(1).getMp();
        if (status == 0) {
			var selStr = "\t\t\t" + 心 + "  " + 心 + " #r#e <推广勋章强化> #k#n " + 心 + "  " + 心 + "\r\n\r\n";
			selStr += "    Hi~#b#h ##k,你想为这件装备附加哪种属性？\r\n#k";
			selStr += "\r\n当前装备：#v" + 装备 + "# #z" + 装备 + "#\r\n#k#l#n";
			if (装备 == 可强化装备代码) {
			selStr += "\r\n当前属性：\r\n力量：" + 力量 + "#l#n#k\t\t敏捷：" + 敏捷 + "\r\n智力：" + 智力 + "#l#n#k\t\t运气：" + 运气 + "\r\n#k攻击：" + 攻击 + "#k\t\t魔力：" + 魔力 + "\r\n#kHp：" + 命中 + "#k\t\tMp：" + 回避 + "\r\n";
            selStr += "#L1#"+JD+"装备力量 + "+每次强化力量+" 需要: #v"+消耗物品代码+"##z"+消耗物品代码+"# * "+消耗物品数量+"\r\n";
            selStr += "#L2#"+JD+"装备智力 + "+每次强化智力+" 需要: #v"+消耗物品代码+"##z"+消耗物品代码+"# * "+消耗物品数量+"\r\n";
			selStr += "#L3#"+JD+"装备敏捷 + "+每次强化敏捷+" 需要: #v"+消耗物品代码+"##z"+消耗物品代码+"# * "+消耗物品数量+"\r\n";
			selStr += "#L4#"+JD+"装备运气 + "+每次强化运气+" 需要: #v"+消耗物品代码+"##z"+消耗物品代码+"# * "+消耗物品数量+"\r\n";
			selStr += "#L5#"+JD+"装备攻击 + "+每次强化攻击+" 需要: #v"+消耗物品代码+"##z"+消耗物品代码+"# * "+攻击魔力消耗物品数量+"\r\n";
			selStr += "#L6#"+JD+"装备魔力 + "+每次强化魔力+" 需要: #v"+消耗物品代码+"##z"+消耗物品代码+"# * "+攻击魔力消耗物品数量+"\r\n";
			selStr += "#L7#"+JD+"装备Hp + "+每次强化命中+" 需要: #v"+消耗物品代码+"##z"+消耗物品代码+"# * "+命中回避消耗物品数量+"\r\n";
			selStr += "#L8#"+JD+"装备Mp + "+每次强化回避+" 需要: #v"+消耗物品代码+"##z"+消耗物品代码+"# * "+命中回避消耗物品数量+"\r\n";
			} else {
				selStr += "\r\n#r不可使用本强化，请将#v"+可强化装备代码+"#放到装备栏的第一格!#k\r\n";
			}
			cm.sendSimple(selStr);
        } else if (status == 1) {
			var 力量 = cm.getInventory(1).getItem(1).getStr();
			var 敏捷 = cm.getInventory(1).getItem(1).getDex();
			var 智力 = cm.getInventory(1).getItem(1).getInt();
			var 运气 = cm.getInventory(1).getItem(1).getLuk();
			var 攻击 = cm.getInventory(1).getItem(1).getWatk();
			var 魔力 = cm.getInventory(1).getItem(1).getMatk();
			var 命中 = cm.getInventory(1).getItem(1).getAcc();
			var 回避 = cm.getInventory(1).getItem(1).getAvoid();
			if (selection == 1) {
                if (cm.itemQuantity(消耗物品代码) < 消耗物品数量) {
					cm.sendOk("你的#v"+消耗物品代码+"##z"+消耗物品代码+"# 不足 "+消耗物品数量+" 个");
					cm.dispose();
					return;
                }
				if (力量 >= 力量上限) {
					cm.sendOk("这件装备的力量已经强化至上限，请换一件装备！");
					cm.dispose();
					return;
				}
				var statup = new java.util.ArrayList();
				var itemId1 = cm.getInventory(1).getItem(1).getItemId();
				var item = cm.getInventory(1).getItem(1).copy();
				var type =  GameConstants.getInventoryType(itemId1);
                cm.gainItem(消耗物品代码, -消耗物品数量);
				item.setStr(item.getStr()+每次强化力量);
				MapleInventoryManipulator.removeFromSlot(cm.getC(),type,1,1, false);
				MapleInventoryManipulator.addFromDrop(cm.getC(), item,false);
				cm.ShowWZEffect("Effect/BasicEff.img/SkillBook/Success/0");
                cm.worldMessage(6,"" + cm.getPlayer().getName() + ":正在为推广勋章强化属性！");
				cm.sendOk("恭喜你强化成功！");
				cm.dispose();
				return;
			}
			if (selection == 2) {
                if (cm.itemQuantity(消耗物品代码) < 消耗物品数量) {
					cm.sendOk("你的#v"+消耗物品代码+"##z"+消耗物品代码+"# 不足 "+消耗物品数量+" 个");
					cm.dispose();
					return;
                }
				if (智力 >= 智力上限) {
					cm.sendOk("这件装备的智力已经强化至上限，请换一件装备！");
					cm.dispose();
					return;
				}
				var statup = new java.util.ArrayList();
				var itemId1 = cm.getInventory(1).getItem(1).getItemId();
				var item = cm.getInventory(1).getItem(1).copy();
				var type =  GameConstants.getInventoryType(itemId1);
                cm.gainItem(消耗物品代码, -消耗物品数量);
				item.setInt(item.getInt()+每次强化智力);
				MapleInventoryManipulator.removeFromSlot(cm.getC(),type,1,1, false);
				MapleInventoryManipulator.addFromDrop(cm.getC(), item,false);
				cm.ShowWZEffect("Effect/BasicEff.img/SkillBook/Success/0");
                cm.worldMessage(6,"" + cm.getPlayer().getName() + ":正在为推广勋章强化属性！");
				cm.sendOk("恭喜你强化成功！");
				cm.dispose();
				return;
			}
			if (selection == 3) {
                if (cm.itemQuantity(消耗物品代码) < 消耗物品数量) {
					cm.sendOk("你的#v"+消耗物品代码+"##z"+消耗物品代码+"# 不足 "+消耗物品数量+" 个");
					cm.dispose();
					return;
                }
				if (敏捷 >= 敏捷上限) {
					cm.sendOk("这件装备的敏捷已经强化至上限，请换一件装备！");
					cm.dispose();
					return;
				}
				var statup = new java.util.ArrayList();
				var itemId1 = cm.getInventory(1).getItem(1).getItemId();
				var item = cm.getInventory(1).getItem(1).copy();
				var type =  GameConstants.getInventoryType(itemId1);
                cm.gainItem(消耗物品代码, -消耗物品数量);
				item.setDex(item.getDex()+每次强化敏捷);
				MapleInventoryManipulator.removeFromSlot(cm.getC(),type,1,1, false);
				MapleInventoryManipulator.addFromDrop(cm.getC(), item,false);
				cm.ShowWZEffect("Effect/BasicEff.img/SkillBook/Success/0");
                cm.worldMessage(6,"" + cm.getPlayer().getName() + ":正在为推广勋章强化属性！");
				cm.sendOk("恭喜你强化成功！");
				cm.dispose();
				return;
			}
			if (selection == 4) {
                if (cm.itemQuantity(消耗物品代码) < 消耗物品数量) {
					cm.sendOk("你的#v"+消耗物品代码+"##z"+消耗物品代码+"# 不足 "+消耗物品数量+" 个");
					cm.dispose();
					return;
                }
				if (运气 >= 运气上限) {
					cm.sendOk("这件装备的运气已经强化至上限，请换一件装备！");
					cm.dispose();
					return;
				}
				var statup = new java.util.ArrayList();
				var itemId1 = cm.getInventory(1).getItem(1).getItemId();
				var item = cm.getInventory(1).getItem(1).copy();
				var type =  GameConstants.getInventoryType(itemId1);
                cm.gainItem(消耗物品代码, -消耗物品数量);
				item.setLuk(item.getLuk()+每次强化运气);
				MapleInventoryManipulator.removeFromSlot(cm.getC(),type,1,1, false);
				MapleInventoryManipulator.addFromDrop(cm.getC(), item,false);
				cm.ShowWZEffect("Effect/BasicEff.img/SkillBook/Success/0");
                cm.worldMessage(6,"" + cm.getPlayer().getName() + ":正在为推广勋章强化属性！");
				cm.sendOk("恭喜你强化成功！");
				cm.dispose();
				return;
			}
			if (selection == 5) {
                if (cm.itemQuantity(消耗物品代码) < 攻击魔力消耗物品数量) {
					cm.sendOk("你的#v"+消耗物品代码+"##z"+消耗物品代码+"# 不足 "+攻击魔力消耗物品数量+" 个");
					cm.dispose();
					return;
                }
				if (攻击 >= 攻击上限) {
					cm.sendOk("这件装备的攻击已经强化至上限，请换一件装备！");
					cm.dispose();
					return;
				}
				var statup = new java.util.ArrayList();
				var itemId1 = cm.getInventory(1).getItem(1).getItemId();
				var item = cm.getInventory(1).getItem(1).copy();
				var type =  GameConstants.getInventoryType(itemId1);
                cm.gainItem(消耗物品代码, -攻击魔力消耗物品数量);
				item.setWatk(item.getWatk()+每次强化攻击);
				MapleInventoryManipulator.removeFromSlot(cm.getC(),type,1,1, false);
				MapleInventoryManipulator.addFromDrop(cm.getC(), item,false);
				cm.ShowWZEffect("Effect/BasicEff.img/SkillBook/Success/0");
                cm.worldMessage(6,"" + cm.getPlayer().getName() + ":正在为推广勋章强化属性！");
				cm.sendOk("恭喜你强化成功！");
				cm.dispose();
				return;
			}
			if (selection == 6) {
                if (cm.itemQuantity(消耗物品代码) < 攻击魔力消耗物品数量) {
					cm.sendOk("你的#v"+消耗物品代码+"##z"+消耗物品代码+"# 不足 "+攻击魔力消耗物品数量+" 个");
					cm.dispose();
					return;
                }
				if (魔力 >= 魔力上限) {
					cm.sendOk("这件装备的魔力已经强化至上限，请换一件装备！");
					cm.dispose();
					return;
				}
				var statup = new java.util.ArrayList();
				var itemId1 = cm.getInventory(1).getItem(1).getItemId();
				var item = cm.getInventory(1).getItem(1).copy();
				var type =  GameConstants.getInventoryType(itemId1);
                cm.gainItem(消耗物品代码, -攻击魔力消耗物品数量);
				item.setMatk(item.getMatk()+每次强化魔力);
				MapleInventoryManipulator.removeFromSlot(cm.getC(),type,1,1, false);
				MapleInventoryManipulator.addFromDrop(cm.getC(), item,false);
				cm.ShowWZEffect("Effect/BasicEff.img/SkillBook/Success/0");
                cm.worldMessage(6,"" + cm.getPlayer().getName() + ":正在为推广勋章强化属性！");
				cm.sendOk("恭喜你强化成功！");
				cm.dispose();
				return;
			}	
			if (selection == 7) {
                if (cm.itemQuantity(消耗物品代码) < 命中回避消耗物品数量) {
					cm.sendOk("你的#v"+消耗物品代码+"##z"+消耗物品代码+"# 不足 "+命中回避消耗物品数量+" 个");
					cm.dispose();
					return;
                }
				if (命中 >= 命中上限) {
					cm.sendOk("这件装备的命中已经强化至上限，请换一件装备！");
					cm.dispose();
					return;
				}
				var statup = new java.util.ArrayList();
				var itemId1 = cm.getInventory(1).getItem(1).getItemId();
				var item = cm.getInventory(1).getItem(1).copy();
				var type =  GameConstants.getInventoryType(itemId1);
                cm.gainItem(消耗物品代码, -命中回避消耗物品数量);
				item.setHp(item.getHp()+每次强化命中);
				MapleInventoryManipulator.removeFromSlot(cm.getC(),type,1,1, false);
				MapleInventoryManipulator.addFromDrop(cm.getC(), item,false);
				cm.ShowWZEffect("Effect/BasicEff.img/SkillBook/Success/0");
                cm.worldMessage(6,"" + cm.getPlayer().getName() + ":正在为推广勋章强化属性！");
				cm.sendOk("恭喜你强化成功！");
				cm.dispose();
				return;
			}
			if (selection == 8) {
                if (cm.itemQuantity(消耗物品代码) < 命中回避消耗物品数量) {
					cm.sendOk("你的#v"+消耗物品代码+"##z"+消耗物品代码+"# 不足 "+命中回避消耗物品数量+" 个");
					cm.dispose();
					return;
                }
				if (回避 >= 回避上限) {
					cm.sendOk("这件装备的回避已经强化至上限，请换一件装备！");
					cm.dispose();
					return;
				}
				var statup = new java.util.ArrayList();
				var itemId1 = cm.getInventory(1).getItem(1).getItemId();
				var item = cm.getInventory(1).getItem(1).copy();
				var type =  GameConstants.getInventoryType(itemId1);
                cm.gainItem(消耗物品代码, -命中回避消耗物品数量);
				item.setMp(item.getMp()+每次强化回避);
				MapleInventoryManipulator.removeFromSlot(cm.getC(),type,1,1, false);
				MapleInventoryManipulator.addFromDrop(cm.getC(), item,false);
				cm.ShowWZEffect("Effect/BasicEff.img/SkillBook/Success/0");
                cm.worldMessage(6,"" + cm.getPlayer().getName() + ":正在为推广勋章强化属性！");
				cm.sendOk("恭喜你强化成功！");
				cm.dispose();
				return;
			}
		}      
    }
}
