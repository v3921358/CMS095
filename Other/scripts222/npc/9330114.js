
var status = -1;
var itemList = Array(
        Array(1002850, 4, 1, 1),
        Array(1003580, 40, 1, 1), //安全帽	
        Array(1152052, 80, 1, 1),
        Array(1012071, 80, 1, 1), //雪糕全属性
        Array(1012072, 160, 1, 1), //狐猴
        Array(1012073, 160, 1, 1), //狐猴
        Array(1022060, 160, 1, 1), //狐猴
        Array(1022067, 80, 1, 1), //黑狐猴
        Array(1012056, 160, 1, 1), //狗狗鼻子1012056	
        Array(2510179, 400, 1, 1),
        Array(2510184, 400, 1, 1),
        Array(2510189, 400, 1, 1),
        Array(2510194, 400, 1, 1),
        Array(2511106, 80, 1, 1),
        Array(2511040, 200, 1, 1),
        Array(2511079, 200, 1, 1),
        Array(2511097, 200, 1, 1),
        Array(2510175, 120, 1, 1),
        Array(2510176, 120, 1, 1),
        Array(2510239, 120, 1, 1),
        Array(2510171, 120, 1, 1),
        Array(2510172, 120, 1, 1),
        Array(1452001, 500, 1, 0),
        Array(1452005, 500, 1, 0),
        Array(1452007, 500, 1, 0),
        Array(1452008, 500, 1, 0),
        Array(1452004, 500, 1, 0),
        Array(1452062, 500, 1, 0),
        Array(1452071, 500, 1, 0),
        Array(1462003, 500, 1, 0),
        Array(1462004, 500, 1, 0),
        Array(1462006, 500, 1, 0),
        Array(1462007, 500, 1, 0),
        Array(1462008, 500, 1, 0),
        Array(1462056, 500, 1, 0),
        Array(1002010, 500, 1, 0),
        Array(1002057, 500, 1, 0),
        Array(1002112, 500, 1, 0),
        Array(1002113, 500, 1, 0),
        Array(1002114, 500, 1, 0),
        Array(1002115, 500, 1, 0),
        Array(1002116, 500, 1, 0),
        Array(1002117, 500, 1, 0),
        Array(1002118, 500, 1, 0),
        Array(1002119, 500, 1, 0),
        Array(1002135, 500, 1, 0),
        Array(1002136, 500, 1, 0),
        Array(1002137, 500, 1, 0),
        Array(1002138, 500, 1, 0),
        Array(1002139, 500, 1, 0),
        Array(1002156, 500, 1, 0),
        Array(1002157, 500, 1, 0),
        Array(1002158, 500, 1, 0),
        Array(1002159, 500, 1, 0),
        Array(1002160, 500, 1, 0),
        Array(1002161, 500, 1, 0),
        Array(1002162, 500, 1, 0),
        Array(1002163, 500, 1, 0),
        Array(1002164, 500, 1, 0),
        Array(1002165, 500, 1, 0),
        Array(1002166, 500, 1, 0),
        Array(1002167, 500, 1, 0),
        Array(1002168, 500, 1, 0),
        Array(1002211, 500, 1, 0),
        Array(1002212, 500, 1, 0),
        Array(1002213, 500, 1, 0),
        Array(1002214, 500, 1, 0),
        Array(1002267, 500, 1, 0),
        Array(1002268, 500, 1, 0),
        Array(1002269, 500, 1, 0),
        Array(1002270, 500, 1, 0),
        Array(1050051, 500, 1, 0),
        Array(1050052, 500, 1, 0),
        Array(1050058, 500, 1, 0),
        Array(1050059, 500, 1, 0),
        Array(1050060, 500, 1, 0),
        Array(1051041, 500, 1, 0),
        Array(1051042, 500, 1, 0),
        Array(1051043, 500, 1, 0),
        Array(1072015, 500, 1, 0),
        Array(1072016, 500, 1, 0),
        Array(1072025, 500, 1, 0),
        Array(1072026, 500, 1, 0),
        Array(1072027, 500, 1, 0),
        Array(1072034, 500, 1, 0),
        Array(1072059, 500, 1, 0),
        Array(1072060, 500, 1, 0),
        Array(1072061, 500, 1, 0),
        Array(1072067, 500, 1, 0),
        Array(1072068, 500, 1, 0),
        Array(1072069, 500, 1, 0),
        Array(1072079, 500, 1, 0),
        Array(1072080, 500, 1, 0),
        Array(1072081, 500, 1, 0),
        Array(1072082, 500, 1, 0),
        Array(1072083, 500, 1, 0),
        Array(1072101, 500, 1, 0),
        Array(1072102, 500, 1, 0),
        Array(1072103, 500, 1, 0),
        Array(1072118, 500, 1, 0),
        Array(1072119, 500, 1, 0),
        Array(1072120, 500, 1, 0),
        Array(1072121, 500, 1, 0),
        Array(1072122, 500, 1, 0),
        Array(1072123, 500, 1, 0),
        Array(1072124, 500, 1, 0),
        Array(1072125, 500, 1, 0),
        Array(1072144, 500, 1, 0),
        Array(1072145, 500, 1, 0),
        Array(1072146, 500, 1, 0),
        Array(1082012, 500, 1, 0),
        Array(1082013, 500, 1, 0),
        Array(1082014, 500, 1, 0),
        Array(1082015, 500, 1, 0),
        Array(1082016, 500, 1, 0),
        Array(1082017, 500, 1, 0),
        Array(1082018, 500, 1, 0),
        Array(1082048, 500, 1, 0),
        Array(1082049, 500, 1, 0),
        Array(1082050, 500, 1, 0),
        Array(1082068, 500, 1, 0),
        Array(1082069, 500, 1, 0),
        Array(1082070, 500, 1, 0),
        Array(1082071, 500, 1, 0),
        Array(1082072, 500, 1, 0),
        Array(1082073, 500, 1, 0),
        Array(1082083, 500, 1, 0),
        Array(1082084, 500, 1, 0),
        Array(1082085, 500, 1, 0),
        Array(1082089, 500, 1, 0),
        Array(1082090, 500, 1, 0),
        Array(1082091, 500, 1, 0),
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
            cm.sendOk("不想使用吗？…我的肚子里有各类#b弓箭手装备#k哦！");
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