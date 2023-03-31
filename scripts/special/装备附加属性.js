load('nashorn:mozilla_compat.js');
importPackage(Packages.client);
importPackage(Packages.client.inventory);
importPackage(Packages.server);
importPackage(Packages.tools);
importPackage(Packages.tools.packet);
importPackage(java.util);

var status = 0;
var 心 = "#fUI/GuildMark.img/Mark/Etc/00009001/14#";
var JD = "#fUI/Basic/BtHide3/mouseOver/0#";
var 可强化装备代码 = 1142598;
var 每次强化力量 = 10;
var 每次强化智力 = 10;
var 每次强化敏捷 = 10;
var 每次强化运气 = 10;
var 每次强化攻击 = 10;
var 每次强化魔力 = 10;
var 消耗物品代码 = 4001126;
var 消耗物品数量 = 1;
var 攻击魔力消耗物品数量 = 10;

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
        if (status == 0) {
			var selStr = "\t\t\t" + 心 + "  " + 心 + " #r#e < 勋章强化 > #k#n " + 心 + "  " + 心 + "\r\n\r\n";
			selStr += "    Hi~#b#h ##k,你想为这件装备附加哪种属性？\r\n#k";
			selStr += "\r\n当前装备：#v" + 装备 + "# #z" + 装备 + "#\r\n#k#l#n";
			if (装备 == 可强化装备代码) {
			selStr += "\r\n当前属性：\r\n力量：" + 力量 + "#l#n#k\t\t敏捷：" + 敏捷 + "\r\n智力：" + 智力 + "#l#n#k\t\t运气：" + 运气 + "\r\n#k攻击：" + 攻击 + "#k\t\t魔力：" + 魔力 + "\r\n";
            selStr += "#L1#"+JD+"装备力量 + "+每次强化力量+" 需要: #v"+消耗物品代码+"##z"+消耗物品代码+"# * "+消耗物品数量+"\r\n";
            selStr += "#L2#"+JD+"装备智力 + "+每次强化智力+" 需要: #v"+消耗物品代码+"##z"+消耗物品代码+"# * "+消耗物品数量+"\r\n";
			selStr += "#L3#"+JD+"装备敏捷 + "+每次强化敏捷+" 需要: #v"+消耗物品代码+"##z"+消耗物品代码+"# * "+消耗物品数量+"\r\n";
			selStr += "#L4#"+JD+"装备运气 + "+每次强化运气+" 需要: #v"+消耗物品代码+"##z"+消耗物品代码+"# * "+消耗物品数量+"\r\n";
			selStr += "#L5#"+JD+"装备攻击 + "+每次强化攻击+" 需要: #v"+消耗物品代码+"##z"+消耗物品代码+"# * "+攻击魔力消耗物品数量+"\r\n";
			selStr += "#L6#"+JD+"装备魔力 + "+每次强化魔力+" 需要: #v"+消耗物品代码+"##z"+消耗物品代码+"# * "+攻击魔力消耗物品数量+"\r\n";
			} else {
				selStr += "\r\n#r不可使用本强化，请将#v"+可强化装备代码+"#放到装备栏的第一格!#k\r\n";
			}
			cm.sendSimple(selStr);
        } else if (status == 1) {
			if (selection == 1) {
                if (cm.itemQuantity(消耗物品代码) < 消耗物品数量) {
					cm.sendOk("你的#v"+消耗物品代码+"##z"+消耗物品代码+"# 不足 "+消耗物品数量+" 个");
					cm.dispose();
					return;
                }
				var statup = new java.util.ArrayList();
				var itemId1 = cm.getInventory(1).getItem(1).getItemId();
				var item = cm.getInventory(1).getItem(1).copy();
				var ii = MapleItemInformationProvider.getInstance();
				var type = ii.getInventoryType(itemId1);
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
				var statup = new java.util.ArrayList();
				var itemId1 = cm.getInventory(1).getItem(1).getItemId();
				var item = cm.getInventory(1).getItem(1).copy();
				var ii = MapleItemInformationProvider.getInstance();
				var type =  ii.getInventoryType(itemId1);
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
				var statup = new java.util.ArrayList();
				var itemId1 = cm.getInventory(1).getItem(1).getItemId();
				var item = cm.getInventory(1).getItem(1).copy();
				var ii = MapleItemInformationProvider.getInstance();
				var type =  ii.getInventoryType(itemId1);
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
				var statup = new java.util.ArrayList();
				var itemId1 = cm.getInventory(1).getItem(1).getItemId();
				var item = cm.getInventory(1).getItem(1).copy();
				var ii = MapleItemInformationProvider.getInstance();
				var type =  ii.getInventoryType(itemId1);
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
				var statup = new java.util.ArrayList();
				var itemId1 = cm.getInventory(1).getItem(1).getItemId();
				var item = cm.getInventory(1).getItem(1).copy();
				var ii = MapleItemInformationProvider.getInstance();
				var type =  ii.getInventoryType(itemId1);
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
				var statup = new java.util.ArrayList();
				var itemId1 = cm.getInventory(1).getItem(1).getItemId();
				var item = cm.getInventory(1).getItem(1).copy();
				var ii = MapleItemInformationProvider.getInstance();
				var type =  ii.getInventoryType(itemId1);
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
		}      
    }
}
