var aaa = "#fUI/UIWindow.img/Quest/icon9/0#";
var zzz = "#fUI/UIWindow.img/Quest/icon8/0#";
var sss = "#fUI/UIWindow.img/QuestIcon/3/0#";

//------------------------------------------------------------------------

var chosenMap = -1;
var monsters = 0;
var towns = 0;
var bosses = 0;
var fuben = 0;
var fee;
var xx;

//------------------------------------------------------------------------

var 圆形 = "#fEffect/CharacterEff/1112903/0/0#"; //红桃心
var aaa = "#fUI/UIWindow.img/Quest/icon9/0#"; //红色右箭头
var zzz = "#fUI/UIWindow.img/Quest/icon8/0#"; //蓝色右箭头
var sss = "#fUI/UIWindow.img/QuestIcon/3/0#"; //选择道具
var eff1 = "#fUI/LogoMs/1#";
var scx = "#fEffect/CharacterEff/1082312/1/0#"; //双彩星
var ccx = "#fEffect/CharacterEff/1082312/0/0#"; //长彩星
var hx = "#fEffect/Summon/7/0#"; //灰星
var hwx = "#fEffect/CharacterEff/1102232/2/0#"; //黄歪星
var cyf = "#fEffect/CharacterEff/1032063/0/0#"; //长音符
var xcx = "#fEffect/CharacterEff/1052203/3/0#"; //小彩星
var yf1 = "#fEffect/CharacterEff/1082312/1/0#"; //音符1
var yf2 = "#fEffect/CharacterEff/1112900/1/1#"; //音符2
var yf3 = "#fEffect/CharacterEff/1112900/2/1#"; //音符3
var yf4 = "#fEffect/CharacterEff/1112900/4/1#"; //音符4
var yf5 = "#fEffect/CharacterEff/1112900/5/1#"; //音符5
var kx = "#fEffect/CharacterEff/1112925/0/1#"; //空星
var kx1 = "#fEffect/CharacterEff/1112925/0/2#"; //空星
var hot = "#fUI/CashShop.img/CSEffect/hot/0#";
var 五角星 = "#fUI/UIWindow.img/UserList/Expedition/icon14#";
var 正方箭头 = "#fUI/Basic/BtHide3/mouseOver/0#";
var 感叹号 = "#fUI/UIWindow/Quest/icon0#";
var 美化new = "#fUI/UIWindow/Quest/icon5/1#";
var 圆形 = "#fUI/UIWindow/Quest/icon3/6#";
//var 红色箭头 = "#fEffect/CharacterEff/1114000/2/0#";
var 红色箭头 = "#fEffect/CharacterEff/1112908/0/1#"; //彩光3
//var 圆形 = "#fEffect/CharacterEff/1062114/1/0#";  //爱心
//var 红色箭头 = "#fUI/UIWindow/Quest/icon6/7#";
var 蓝色角点 = "#fUI/UIWindow.img/PvP/Scroll/enabled/next2#";

//---------------------------------------------------------------------------
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
        if (mode == 1) {
            status++;
        } else {
            status--;
        }
        //------------------------------------------------------------------------
        if (status == 0) {
            var add = "\r\n\t\t" + cyf + "#d#e欢迎来到~PPMS怀旧服#n#k" + cyf + "\r\n\r\n";
            //   add += " \t#L1222##v2440002# #v2440002##e#r" + kx + "春节活动" + kx + "#v2440002# #v2440002##l#n#k\r\n\r\n";
            if (cm.getPlayer().getBossLogD("元旦福利") < 1 && cm.getPlayer().getBossLogS("元旦福利") < 3) {
                //    add += "\t#L1223##v2440002# #v2440002##e#r"+kx+"领取元旦福利"+kx+"#v2440002# #v2440002##l#n#k\r\n\r\n"; 
            }
            add += "\t\t#L107#" + cyf + "#r#e  全民马拉松  " + cyf + "#n#k#l\r\n\r\n";
            add += "#e#L5#" + kx + "个人信息" + kx + "#l #L1#" + kx + "快捷传送" + kx + "#l #L1113#" + kx + "每日任务" + kx + "#l#k\r\n\r\n";
            add += "#e#r#L12#" + kx + "新手奖励" + kx + "#l #L113#" + kx + "药品商店" + kx + "#l #L112#" + kx + "快捷商店" + kx + "#l#k\r\n\r\n";
            add += "#e#L9#" + kx + "怪物爆率" + kx + "#l #L19#" + kx + "掉落查询" + kx + "#l #L1112#" + kx + "枫叶换点" + kx + "#l#k\r\n\r\n"; //#L18#" + kx + "自助开双" + kx + "#l 
            add += "#e#L20#" + kx + "推广系统" + kx + "#l #L6#" + kx + "师徒系统" + kx + "#l #L1111#" + kx + "排行系统" + kx + "#l#k\r\n\r\n";
            add += "#e#L4#" + kx + "快速洗潜" + kx + "#l #L8#" + kx + "洗血系统" + kx + "#l #L2#" + kx + "时装升星" + kx + "#l#k\r\n\r\n";
            add += "#e#L3117#" + kx + "组队兑换" + kx + "#l #L7#" + kx + "狮子王城" + kx + "#l #L3114#" + kx + "匠人兑换" + kx + "#l#k\r\n\r\n";
            add += "#e#L3112#" + kx + "幸运金币" + kx + "#l #L3113#" + kx + "幸运道具" + kx + "#l #L11111#" + kx + "碎片合成" + kx + "#l#k\r\n\r\n";
            add += "#e#L3115#" + kx + "兑换中心" + kx + "#l #L3116#" + kx + "技能兑换" + kx + "#l #L3#" + kx + "#r赞助系统#k" + kx + "#l#k\r\n\r\n";
            add += "#e#L11#" + kx + "领取D片 " + kx + "#l #L2111#" + kx + "点券商店" + kx + "#l #k#L3111#" + kx + "时装商城" + kx + "#l#k\r\n\r\n";
            add += " ";
            if (cm.getPlayer().isGM()) {


                add += "   #L10001#" + ccx + "高版本BOSS挑战中心" + ccx + "#l \r\n";
            }
            cm.sendOk(add);

            //------------------------------------------------------------------------

        } else if (status == 1) {
            switch (selection) {
                case 10001://高版本BOSS挑战
                cm.dispose();
                cm.openNpc(1064002, "高版本BOSS挑战");
                break;
                default:
            }
            if (selection == 1) { //快捷传送
                if (cm.getPlayer().getLevel() < 5) {
                    cm.sendOk("等级低于5级无法使用传送功能");
                    cm.dispose();
                    return;
                }
                cm.dispose();
                cm.openNpc(9900004, "快捷传送");
            }

            if (selection == 111) { //
                cm.dispose();
                cm.openNpc(9900004, "等級奖励");
            }

            if (selection == 11111) { //
                cm.dispose();
                cm.openNpc(9900004, "奇幻方塊碎片");
            }

            if (selection == 1111) { //
                cm.dispose();
                cm.openNpc(9040004);
            }

            if (selection == 1113) { //
                cm.dispose();
                cm.openNpc(9310072);
            }

            if (selection == 1112) { //
                cm.dispose();
                cm.openNpc(9900004, "枫叶换点");
            }

            if (selection == 3111) { //听北冥部分
                cm.dispose();
                cm.openNpc(9900004, "时装商城");
            }
            if (selection == 3112) { //听北冥部分
                cm.dispose();
                cm.openNpc(9900004, "金币赌博");
            }

            if (selection == 3113) { //听北冥部分
                cm.dispose();
                cm.openNpc(9900004, "道具赌博");
            }

            if (selection == 3114) { //听北冥部分
                cm.dispose();
                cm.openNpc(9900004, "道具兑换");
            }

            if (selection == 3115) { //兑换中心
                cm.dispose();
                cm.openNpc(9900004, "兑换中心");
            }

            if (selection == 3116) { // 技能兑换
                cm.dispose();
                cm.openNpc(9900004, "技能兑换");
            }
            if (selection == 3117) { // 技能兑换
                cm.dispose();
                cm.openNpc(9900004, "组队道具兑换");
            }
            if (selection == 2111) { //
                cm.dispose();
                cm.openNpc(9900004, "点券商店");
            }
            if (selection == 113) { //药水商店
                cm.dispose();
                cm.openShop(12);
            }
            if (selection == 112) { //普通商店
                cm.dispose();
                cm.openShop(66);
            }

            if (selection == 2) { //时装升星
                cm.dispose();
                cm.openNpc(9900004, "时装升星");
            }
            if (selection == 3) { //CDK兑换
                cm.dispose();
                cm.openNpc(9900004, "自助充值");
            }

            if (selection == 4) { //快速洗潜能
                cm.dispose();
                cm.openNpc(9900004, "潜能魔方");
            }

            if (selection == 5) { //发型脸型
                cm.dispose();
                cm.openNpc(9900004, "个人信息");
            }
            if (selection == 6) { //每日奖励
                cm.dispose();
                cm.openNpc(9900004, "师徒系统");
            }
            if (selection == 7) { //钓鱼兑换
                cm.dispose();
                cm.openNpc(9900004, "狮子王城");
            }
            if (selection == 8) { //枫叶回收
                cm.dispose();
                cm.openNpc(9900004, "洗血系统");
            }
            if (selection == 9) { //怪物爆率
                cm.dispose();
                cm.openNpc(9330042);
            }
            if (selection == 10) {
                cm.dispose();
                cm.openNpc(9900004, "首充礼包");
            }
            if (selection == 107) {
                cm.dispose();
                cm.openNpc(9900004, "全民马拉松");
            }
            if (selection == 1222) {
                cm.dispose();
                cm.openNpc(9900004, "圣诞月庆活动");
            }
            if (selection == 1223) { //元旦每日福利
                var next = true;
                var 角色ID = cm.getPlayer().getId();
                var fee_list = getAccCid(角色ID);
                for (var i = 0; i < fee_list.length; i++) {
                    if (fee_list[i] == 角色ID) {
                        continue;
                    }
                    if (cm.getBossRank(fee_list[i], "元旦福利", 2) > 0) {
                        next = false;
                        break;
                    }
                }
                if (next == false) {
                    cm.sendOk("你的其他角色已经领取过了！");
                    cm.dispose();
                    return;
                }
                if (cm.getPlayer().getBossLogD("元旦福利") > 0) {
                    cm.sendOk("你今天已经领取过了，请明天再来！");
                    cm.dispose();
                    return;
                }
                if (cm.getPlayer().getLevel() < 10) {
                    cm.sendOk("你的等级不足，请10级后再来！");
                    cm.dispose();
                    return;
                }
                if (!cm.canHoldByTypea(5, 1)) {
                    cm.sendOk("你的背包空间不足！");
                    cm.dispose();
                    return;
                }
                cm.setBossRankCount("元旦福利");
                cm.setBossLog("元旦福利");
                cm.gainNX2(1000000);
                cm.gainNX(1000000);
                cm.gainItem(5062001, 3);
                cm.gainItem(5062000, 10);
                cm.sendOk("恭喜你领取成功!");
                cm.worldMessage(5, "恭喜" + cm.getPlayer().getName() + "成功领取元旦福利！");
                cm.dispose();
            }
            if (selection == 11) { //D片领取
                if (!cm.canHoldByTypea(4, 1)) {
                    cm.sendOk("请确认背包是否已经满了。");
                    cm.dispose();
                    return;
                }
                if (cm.getPlayer().getBossLogD("D片兌換") >= 2) {
                    cm.sendOk("你今天已经換过了");
                    cm.dispose();
                    return;
                }
                if (cm.haveItem(4031172)) {
                    cm.getPlayer().setBossLog("D片兌換");
                    cm.gainItem(4031179, 1);
                    cm.sendOk("领取成功。");
                    cm.dispose();
                    return;
                } else {
                    cm.sendOk("你沒有#t4031172##i4031172#。");
                    cm.dispose();
                    return;
                }
            }
            if (selection == 12) { //新手礼包
                var next = true;
                var 角色ID = cm.getPlayer().getId();
                var fee_list = getAccCid(角色ID);
                for (var i = 0; i < fee_list.length; i++) {
                    if (cm.getBossRank(fee_list[i], "新手礼包", 2) > 0) {
                        next = false;
                        break;
                    }
                }
                if (next == false) {
                    cm.sendOk("你的其他角色已经领取过了！");
                    cm.dispose();
                    return;
                }
                if (cm.getBossRankCount("新手礼包") > 0) {
                    cm.sendOk("你已经领取过了！");
                    cm.dispose();
                    return;
                }
                if (cm.getPlayer().getBossLogS("新手礼包") > 0) {
                    cm.sendOk("你已经领取过了！");
                    cm.dispose();
                    return;
                }
                if (cm.getPlayer().getLevel() < 5) {
                    cm.sendOk("你的等级不足，请5级后再来！");
                    cm.dispose();
                    return;
                }
                if (!cm.canHoldByTypea(5, 1)) {
                    cm.sendOk("你的背包空间不足！");
                    cm.dispose();
                    return;
                }
                cm.setBossRankCount("新手礼包");
                cm.setBossLog("新手礼包");
                cm.gainNX2(30000)
                cm.gainMeso(500000);
                cm.gainItem(5150040, 2);
                cm.gainItem(5211047, 1, 3);
                cm.gainItem(2049401, 10);
                cm.gainItem(2001512, 500);
                cm.sendOk("恭喜你领取成功，获得：\r\n抵用券 * 30000 金币 * 50万 #v5211060# * 1 #v5150040# * 2 #v2001512# * 500 ");
                cm.worldMessage(5, "欢迎" + cm.getPlayer().getName() + "来到PPMS怀旧服，要收徒弟的赶紧了！");
                cm.dispose();
            }
            if (selection == 13) {
                /*var 角色ID = cm.getPlayer().getId();
                 var fee_list = getAccCid(220);
                 cm.sendOk(fee_list);*/
                /*var equip = cm.getEquip(2340000);
                 equip.setLock(1);	
                 cm.addbyItem(equip);*/
                //cm.gainItem(2340000,1);
                //var statup = new java.util.ArrayList();
                //var itemId1 = cm.getInventory(2).getItem(1).getItemId()
                //var itemId1 = cm.getInventory(1).getItem(1);
                //var item = cm.getInventory(1).getItem(1);
                //var ii = MapleItemInformationProvider.getInstance();
                //var type =  ii.getInventoryType(itemId1);
                //item.setLock(1);	
                //MapleInventoryManipulator.removeFromSlot(cm.getC(),type,1,1, false);
                //MapleInventoryManipulator.addFromDrop(cm.getC(), item,false);
                //cm.gainItemPeriodB(2340000,1,1,1);
                //cm.gainItemPeriodB(1052166,1,3,100,100,100,100,100,100,0);
                //cm.setLock
                //中奖人 = cm.getDoubleFloor(cm.getDoubleRandom() * 18) + 1;
                //cm.sendOk(中奖人);
                //cm.worldMessage(5,"本次白嫖鬼脸活动的中奖人号码是： "+中奖人+"号玩家，大家恭喜他！ ");
                /*var text = "";
                 var ItemQuantity_list = getItemQuantity(2340000);
                 if (ItemQuantity_list != null) {
                 for (var i = 0; i < ItemQuantity_list.length; i = i + 2) {
                 var info = ItemQuantity_list[i];
                 var 数量 = ItemQuantity_list[i+1]
                 text += "\t";
                 text += cm.getCharacterNameById(info) + " \t";
                 text += "#k";
                 text += "\t#r"+ 数量 +"#l";
                 text += "#k";	
                 text += "\r\n";
                 }
                 }
                 cm.sendOk(text);*/
                /*var jzcount = [1,2,3,4,5,6];
                 var 家族任务需求物品 = getRandomArrayElements(jzcount,1);
                 //cm.gainExp_PQ(200, 2);
                 //resettingFbLog("副本1");
                 cm.sendOk(家族任务需求物品);*/
                cm.dispose();
                cm.openNpc(9900004, "打王兑换");
            }
            if (selection == 14) {
                cm.dispose();
                cm.openNpc(9900004, "全服充值");
            }
            if (selection == 15) {
                cm.dispose();
                cm.openNpc(9900004, "高级检索");
            }
            if (selection == 17) {
                cm.setBossLog("御龙魔");
                cm.dispose();
            }
            if (selection == 18) {
                cm.dispose();
                cm.openNpc(9900004, "自助开双");
            }
            if (selection == 19) {
                cm.dispose();
                cm.openNpc(9900004, "掉落查询");
            }
            if (selection == 33) {
                cm.gainItemPeriodB(1052165, 1, 4, 30, 30, 30, 30, 30, 30, 0);
                cm.sendOk("领取成功！");
                cm.dispose();
            }
            if (selection == 20) {
                cm.dispose();
                cm.openNpc(9900004, "推广系统");
            }
        }
    }
}


function getAccCid(bossid) {
    var con = cm.getDataSource().getConnection();
    var count = [];
    var counts = 0;
    var countss = 0;
    var ps = con.prepareStatement("SELECT * FROM characters WHERE id = ?");
    ps.setInt(1, bossid);
    var rs = ps.executeQuery();
    if (rs.next()) {
        counts = rs.getInt("accountid");
    } else {
        return undefined;
        rs.close();
        ps.close();
        con.close();
    }
    var ps1 = con.prepareStatement("SELECT COUNT(*) FROM characters WHERE accountid = ?");
    ps1.setInt(1, counts);
    var ps2 = con.prepareStatement("SELECT * FROM characters WHERE accountid = ?");
    ps2.setInt(1, counts);
    var rs1 = ps1.executeQuery();
    var rs2 = ps2.executeQuery();
    if (rs1.next()) {
        countss = rs1.getInt(1);
    } else {
        counts = -1;
    }
    for (var i = 0; i < countss; i++) {
        if (rs2.next()) {
            count.push(rs2.getInt("id"));
        }
    }
    rs2.close();
    ps2.close();
    rs1.close();
    ps1.close();
    rs.close();
    ps.close();
    con.close();
    return count;
}

function getHiredMerchId(accountid) {
    var con = cm.getDataSource().getConnection();
    var count = 0;
    var ps = con.prepareStatement("SELECT COUNT(*) FROM hiredmerch WHERE accountid = ?");
    ps.setInt(1, accountid);
    var rs = ps.executeQuery();
    if (rs.next()) {
        count = rs.getInt(1);
    } else {
        return undefined;
    }
    rs.close();
    ps.close();
    con.close();
    if (count > 0) {
        return false;
    } else {
        return true;
    }
}

function getItemQuantity(ItemId) {
    var con = cm.getDataSource().getConnection();
    var count = [];
    var counts = 0;
    var ps = con.prepareStatement("SELECT * FROM inventoryitems WHERE itemid = ? ORDER BY quantity DESC");
    ps.setInt(1, ItemId);
    var ps1 = con.prepareStatement("SELECT COUNT(*) FROM inventoryitems WHERE itemid = ?");
    ps1.setInt(1, ItemId);
    var rs = ps.executeQuery();
    var rs1 = ps1.executeQuery();
    if (rs1.next()) {
        counts = rs1.getInt(1);
    } else {
        counts = -1;
    }
    if (rs.next()) {
        for (var i = 0; i < counts; i++) {
            if (rs.next()) {
                count.push(rs.getInt("characterid"));
                count.push(rs.getInt("quantity"));
            }
        }
    }
    rs1.close();
    ps1.close();
    rs.close();
    ps.close();
    con.close();
    return count;
}

function resettingFbLog(bossname) {
    var con = cm.getDataSource().getConnection();
    var pointss = 0;
    var ps = con.prepareStatement("SELECT * FROM fblog WHERE bossname = ?");
    ps.setString(1, bossname);
    var rs = ps.executeQuery();
    if (rs.next()) {
        var ps = con.prepareStatement("UPDATE fblog SET points = ? WHERE bossname = ?");
        ps.setInt(1, 0);
        ps.setString(2, bossname);
        ps.executeUpdate();
        ps = con.prepareStatement("UPDATE fblog SET count = ? WHERE bossname = ?");
        ps.setInt(1, 0);
        ps.setString(2, bossname);
        ps.executeUpdate();
    } else {
        rs.close();
        ps.close();
        con.close();
    }
    rs.close();
    ps.close();
    con.close();
}

function getRandomArrayElements(arr, count) {
    var shuffled = arr.slice(0),
        i = arr.length,
        min = i - count,
        temp, index;
    while (i-- > min) {
        index = cm.getDoubleFloor((i + 1) * cm.getDoubleRandom());
        temp = shuffled[index];
        shuffled[index] = shuffled[i];
        shuffled[i] = temp;
    }
    return shuffled.slice(min);
}