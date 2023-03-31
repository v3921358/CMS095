
var status = -1;
var itemList = Array(
        Array(1002850, 5, 1, 1),
        Array(1003580, 50, 1, 1), //安全帽	
        Array(1152052, 100, 1, 1),
        Array(1012071, 100, 1, 1), //雪糕全属性
        Array(1012072, 200, 1, 0), //狐猴
        Array(1012073, 200, 1, 0), //狐猴
        Array(1022060, 200, 1, 0), //狐猴
        Array(1012060, 100, 1, 0), //匹诺曹鼻子
        Array(1012056, 200, 1, 0), //狗狗鼻子1012056	
        Array(2510178, 450, 1, 1),
        Array(2510183, 450, 1, 1),
        Array(2510188, 450, 1, 1),
        Array(2510193, 450, 1, 1),
        Array(2511106, 100, 1, 1),
        Array(2511040, 250, 1, 1),
        Array(2511079, 250, 1, 1),
        Array(2511097, 250, 1, 1),
        Array(2510175, 150, 1, 1),
        Array(2510176, 150, 1, 1),
        Array(2510239, 150, 1, 1),
        Array(2510171, 150, 1, 1),
        Array(2510172, 150, 1, 1),
        Array(1372002, 500, 1, 0),
        Array(1372003, 500, 1, 0),
        Array(1372000, 500, 1, 0),
        Array(1372008, 500, 1, 0),
        Array(1372011, 500, 1, 0),
        Array(1372046, 500, 1, 0),
        Array(1382004, 500, 1, 0),
        Array(1382017, 500, 1, 0),
        Array(1382019, 500, 1, 0),
        Array(1382011, 500, 1, 0),
        Array(1382006, 500, 1, 0),
        Array(1382007, 500, 1, 0),
        Array(1382062, 500, 1, 0),
        Array(1382068, 500, 1, 0),
        Array(1002013, 500, 1, 0),
        Array(1002016, 500, 1, 0),
        Array(1002017, 500, 1, 0),
        Array(1002034, 500, 1, 0),
        Array(1002035, 500, 1, 0),
        Array(1002037, 500, 1, 0),
        Array(1002036, 500, 1, 0),
        Array(1002038, 500, 1, 0),
        Array(1002064, 500, 1, 0),
        Array(1002065, 500, 1, 0),
        Array(1002072, 500, 1, 0),
        Array(1002073, 500, 1, 0),
        Array(1002074, 500, 1, 0),
        Array(1002075, 500, 1, 0),
        Array(1002102, 500, 1, 0),
        Array(1002103, 500, 1, 0),
        Array(1002104, 500, 1, 0),
        Array(1002105, 500, 1, 0),
        Array(1002106, 500, 1, 0),
        Array(1002141, 500, 1, 0),
        Array(1002142, 500, 1, 0),
        Array(1002143, 500, 1, 0),
        Array(1002144, 500, 1, 0),
        Array(1002145, 500, 1, 0),
        Array(1002151, 500, 1, 0),
        Array(1002152, 500, 1, 0),
        Array(1002153, 500, 1, 0),
        Array(1002154, 500, 1, 0),
        Array(1002155, 500, 1, 0),
        Array(1002215, 500, 1, 0),
        Array(1002216, 500, 1, 0),
        Array(1002217, 500, 1, 0),
        Array(1002218, 500, 1, 0),
        Array(1002242, 500, 1, 0),
        Array(1002243, 500, 1, 0),
        Array(1002244, 500, 1, 0),
        Array(1002245, 500, 1, 0),
        Array(1002246, 500, 1, 0),
        Array(1002252, 500, 1, 0),
        Array(1002253, 500, 1, 0),
        Array(1002254, 500, 1, 0),
        Array(1050001, 500, 1, 0),
        Array(1050002, 500, 1, 0),
        Array(1050003, 500, 1, 0),
        Array(1050008, 500, 1, 0),
        Array(1050009, 500, 1, 0),
        Array(1050010, 500, 1, 0),
        Array(1050023, 500, 1, 0),
        Array(1050024, 500, 1, 0),
        Array(1050025, 500, 1, 0),
        Array(1050026, 500, 1, 0),
        Array(1050027, 500, 1, 0),
        Array(1050028, 500, 1, 0),
        Array(1050029, 500, 1, 0),
        Array(1050030, 500, 1, 0),
        Array(1050031, 500, 1, 0),
        Array(1050035, 500, 1, 0),
        Array(1050036, 500, 1, 0),
        Array(1050037, 500, 1, 0),
        Array(1050038, 500, 1, 0),
        Array(1050039, 500, 1, 0),
        Array(1050045, 500, 1, 0),
        Array(1050046, 500, 1, 0),
        Array(1050047, 500, 1, 0),
        Array(1050048, 500, 1, 0),
        Array(1050049, 500, 1, 0),
        Array(1050053, 500, 1, 0),
        Array(1050054, 500, 1, 0),
        Array(1050055, 500, 1, 0),
        Array(1050056, 500, 1, 0),
        Array(1051003, 500, 1, 0),
        Array(1051004, 500, 1, 0),
        Array(1051005, 500, 1, 0),
        Array(1051023, 500, 1, 0),
        Array(1051024, 500, 1, 0),
        Array(1051025, 500, 1, 0),
        Array(1051026, 500, 1, 0),
        Array(1051027, 500, 1, 0),
        Array(1051030, 500, 1, 0),
        Array(1051031, 500, 1, 0),
        Array(1051032, 500, 1, 0),
        Array(1051033, 500, 1, 0),
        Array(1051034, 500, 1, 0),
        Array(1051044, 500, 1, 0),
        Array(1051045, 500, 1, 0),
        Array(1051046, 500, 1, 0),
        Array(1051047, 500, 1, 0),
        Array(1072006, 500, 1, 0),
        Array(1072019, 500, 1, 0),
        Array(1072020, 500, 1, 0),
        Array(1072021, 500, 1, 0),
        Array(1072023, 500, 1, 0),
        Array(1072024, 500, 1, 0),
        Array(1072044, 500, 1, 0),
        Array(1072045, 500, 1, 0),
        Array(1072072, 500, 1, 0),
        Array(1072073, 500, 1, 0),
        Array(1072074, 500, 1, 0),
        Array(1072075, 500, 1, 0),
        Array(1072076, 500, 1, 0),
        Array(1072077, 500, 1, 0),
        Array(1072078, 500, 1, 0),
        Array(1072089, 500, 1, 0),
        Array(1072090, 500, 1, 0),
        Array(1072091, 500, 1, 0),
        Array(1072117, 500, 1, 0),
        Array(1072136, 500, 1, 0),
        Array(1072137, 500, 1, 0),
        Array(1072138, 500, 1, 0),
        Array(1072139, 500, 1, 0),
        Array(1072140, 500, 1, 0),
        Array(1072141, 500, 1, 0),
        Array(1072142, 500, 1, 0),
        Array(1072143, 500, 1, 0),
        Array(1082019, 500, 1, 0),
        Array(1082020, 500, 1, 0),
        Array(1082021, 500, 1, 0),
        Array(1082022, 500, 1, 0),
        Array(1082026, 500, 1, 0),
        Array(1082027, 500, 1, 0),
        Array(1082028, 500, 1, 0),
        Array(1082051, 500, 1, 0),
        Array(1082052, 500, 1, 0),
        Array(1082053, 500, 1, 0),
        Array(1082054, 500, 1, 0),
        Array(1082055, 500, 1, 0),
        Array(1082056, 500, 1, 0),
        Array(1082062, 500, 1, 0),
        Array(1082063, 500, 1, 0),
        Array(1082064, 500, 1, 0),
        Array(1082080, 500, 1, 0),
        Array(1082081, 500, 1, 0),
        Array(1082082, 500, 1, 0),
        Array(1082086, 500, 1, 0),
        Array(1082087, 500, 1, 0),
        Array(1082088, 500, 1, 0),
        Array(1102000, 500, 1, 0),
        Array(1102001, 500, 1, 0),
        Array(1102002, 500, 1, 0),
        Array(1102003, 500, 1, 0),
        Array(1102004, 500, 1, 0),
        Array(1102011, 500, 1, 0),
        Array(1102012, 500, 1, 0),
        Array(1102013, 500, 1, 0),
        Array(1102014, 500, 1, 0),
        Array(1102015, 500, 1, 0),
        Array(1102016, 500, 1, 0),
        Array(1102017, 500, 1, 0),
        Array(1102018, 500, 1, 0)
        );
function start() {
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        if (status == 0) {
            cm.sendOk("不想使用吗？…我的肚子里有各类#b法师装备#k哦！");
            cm.dispose();
        }
        status--;
    }
    var jpzs = "";


    for (var j = 0; j < itemList.length; j++) {
        jpzs += "#v" + itemList[j][0] + "#";
    }
    if (status == 0) {
        if (cm.getPlayer().getLevel() < 10) {
            cm.sendOk("您的等级太低，无法使用快乐百宝箱。");
            cm.dispose();
            return;
        }
        if (cm.haveItem(5451001, 1)) {
            cm.sendSimple("冒险岛转蛋机中有各类#b装备、卷轴或稀有新奇的道具#k噢！使用“#b#t5451001##k”就可以抽奖，否则是不可以使用我的。现在要玩转蛋机么? \r\n#b#L2#试试手气吧#l#k\r\n#b#L4#十连抽#l#k\r\n\r\n\r\n下期部分奖励展示:\r\n" + jpzs);
        } else {
            cm.sendSimple("冒险岛转蛋机中有各类#b装备、卷轴或稀有新奇的道具#k噢！使用“#b#t5451001##k”就可以交换。 假如不买转蛋券的话，是不可以使用我的。现在要玩转蛋机么?    \r\n\r\n#r你背包里没#v5451001##z5451001#\r\n\r\n#k部分奖励展示:\r\n" + jpzs + "");
     cm.dispose();
	 }
    } else if (selection == 2) {
        var chance = cm.getDoubleFloor(cm.getDoubleRandom() * 500);
        var finalitem = Array();
        for (var i = 0; i < itemList.length; i++) {
            if (itemList[i][1] >= chance) {
                finalitem.push(itemList[i]);
            }
        }
        if (finalitem.length != 0) {
            var item;

            var finalchance = cm.nextInt(finalitem.length);
            var itemId = finalitem[finalchance][0];
            var quantity = finalitem[finalchance][2];
            var notice = finalitem[finalchance][3];
            /*if (itemId == 1402037){
             var ii = MapleItemInformationProvider.getInstance();              
             var type = ii.getInventoryType(itemId); //获得装备的类形
             var toDrop = ii.randomizeStats(ii.getEquipById(itemId)).copy(); // 生成一个Equip类 
             toDrop.setFlag(1);	
             toDrop.setHp(1000);
             toDrop.setMp(1000);
             toDrop.setStr(10);
             toDrop.setDex(10);
             toDrop.setInt(10);
             toDrop.setLuk(10);
             cm.getPlayer().getInventory(type).addItem(toDrop);//将这个装备放入包中
             cm.getC().getSession().write(MaplePacketCreator.addInventorySlot(type, toDrop)); //刷新背包	
             cm.getChar().saveToDB(false, false);
             cm.worldMessage(5,"恭喜"+cm.getPlayer().getName()+"从快乐百宝箱中抽取到满卷龙背刃,大家恭喜他");
             } else {*/
            item = cm.gainGachaponItem(itemId, quantity, "职业转蛋机", notice);
            //}
            if (item != -1) {
                cm.gainItem(5451001, -1);
                cm.sendOk("你获得了 #b#t" + item + "##k " + quantity + "个。");
                cm.dispose();
                //给1个纪念币
                // cm.给物品(2000005,1);
            } else {
                cm.sendOk("你确实有#b#t5451001##k吗？如果是，请你确认在背包的装备，消耗，其他窗口中是否有一格以上的空间。");
            }
            cm.safeDispose();
        } else {
            cm.sendOk("今天的运气可真差，什么都没有拿到。\r\n(获得了安慰奖：#v2000005#。)");
            cm.gainItem(5451001, -1);
            // cm.gainItem(2000005, 1);
            //给1点积分
            //cm.setBossRankCount("快乐百宝箱抽奖",1);
            //cm.setBossRankCount("快乐百宝箱抽奖积分",1);
            cm.safeDispose();
        }
    } else if (selection == 4) {
        var itemids = [];
        if (!cm.haveItem(5451001, 10)) {
            cm.sendOk("你的 #b#t5451001# #k不够 #r10 #k张，无法抽奖。");
            cm.dispose();
            return;
        }
        if (cm.getInventory(1).isFull(9)) {
            cm.sendOk("请保证背包#b装备栏#k至少有 #r10 #k个位置");
            cm.dispose();
            return;
        }
        if (cm.getInventory(2).isFull(9)) {
            cm.sendOk("请保证背包#b消耗栏#k至少有 #r10 #k个位置");
            cm.dispose();
            return;
        }
        if (cm.getInventory(3).isFull(9)) {
            cm.sendOk("请保证背包#b设置栏#k至少有 #r10 #k个位置");
            cm.dispose();
            return;
        }
        if (cm.getInventory(4).isFull(9)) {
            cm.sendOk("请保证背包#b其他栏#k至少有 #r10 #k个位置");
            cm.dispose();
            return;
        }
        for (var j = 0; j < 10; j++) {
            var chance = cm.getDoubleFloor(cm.getDoubleRandom() * 500);
            var finalitem = Array();
            for (var i = 0; i < itemList.length; i++) {
                if (itemList[i][1] >= chance) {
                    finalitem.push(itemList[i]);
                }
            }
            if (finalitem.length != 0) {
                var item;

                var finalchance = cm.nextInt(finalitem.length);
                var itemId = finalitem[finalchance][0];
                var quantity = finalitem[finalchance][2];
                var notice = finalitem[finalchance][3];
                item = cm.gainGachaponItem(itemId, quantity, "职业转蛋机", notice);
                if (item != -1) {
                    cm.gainItem(5451001, -1);
                    itemids.push(itemId);
                    //给1个纪念币
                    //cm.gainItem(4000000,1);
                } else {
                    cm.sendOk("你确实有#b#t5451001##k吗？如果是，请你确认在背包的装备，消耗，其他窗口中是否有一格以上的空间。");
                }
                cm.safeDispose();
            } else {
                cm.sendOk("今天的运气可真差，什么都没有拿到。\r\n(获得了安慰奖：#v2000005#。)");
                cm.gainItem(5451001, -1);
                cm.gainItem(4000000, 1);
                cm.safeDispose();
            }
        }
        var idtext = "以下是你抽到的物品:\r\n";
        for (var c = 0; c < itemids.length; c++) {
            idtext += "#v" + itemids[c] + "##z" + itemids[c] + "#\r\n";
        }
        cm.sendSimple(idtext);
        cm.safeDispose;
    }
}