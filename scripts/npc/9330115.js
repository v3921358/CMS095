
var status = -1;
var itemList = Array(
        Array(1002850, 5, 1, 1),
        Array(1003580, 50, 1, 1), //安全帽	
        Array(1152052, 100, 1, 1),
        Array(1012071, 100, 1, 1), //雪糕全属性
        Array(1012072, 200, 1, 1), //狐猴
        Array(1012073, 200, 1, 1), //狐猴
        Array(1022060, 200, 1, 1), //狐猴
        Array(1022067, 100, 1, 1), //黑狐猴
        Array(1012056, 200, 1, 1), //狗狗鼻子1012056	
        Array(2510180, 450, 1, 1),
        Array(2510185, 450, 1, 1),
        Array(2510190, 450, 1, 1),
        Array(2510195, 450, 1, 1),
        Array(2511106, 100, 1, 1),
        Array(2511040, 250, 1, 1),
        Array(2511079, 250, 1, 1),
        Array(2511097, 250, 1, 1),
        Array(2510175, 150, 1, 1),
        Array(2510176, 150, 1, 1),
        Array(2510239, 150, 1, 1),
        Array(2510171, 150, 1, 1),
        Array(2510172, 150, 1, 1),
        Array(1332013, 500, 1, 1),
        Array(1332012, 500, 1, 1),
        Array(1332011, 500, 1, 1),
        Array(1332003, 500, 1, 1),
        Array(1332015, 500, 1, 1),
        Array(1332081, 500, 1, 1),
        Array(1342000, 500, 1, 1),
        Array(1342001, 500, 1, 1),
        Array(1342002, 500, 1, 1),
        Array(1342003, 500, 1, 1),
        Array(1342004, 500, 1, 1),
        Array(1472004, 500, 1, 1),
        Array(1472008, 500, 1, 1),
        Array(1472014, 500, 1, 1),
        Array(1472019, 500, 1, 1),
        Array(1472022, 500, 1, 1),
        Array(1472077, 500, 1, 1),
        Array(1472086, 500, 1, 1),
        Array(1002107, 500, 1, 0),
        Array(1002111, 500, 1, 0),
        Array(1002122, 500, 1, 0),
        Array(1002123, 500, 1, 0),
        Array(1002124, 500, 1, 0),
        Array(1002125, 500, 1, 0),
        Array(1002127, 500, 1, 0),
        Array(1002128, 500, 1, 0),
        Array(1002129, 500, 1, 0),
        Array(1002130, 500, 1, 0),
        Array(1002131, 500, 1, 0),
        Array(1002146, 500, 1, 0),
        Array(1002147, 500, 1, 0),
        Array(1002148, 500, 1, 0),
        Array(1002149, 500, 1, 0),
        Array(1002150, 500, 1, 0),
        Array(1002171, 500, 1, 0),
        Array(1002172, 500, 1, 0),
        Array(1002173, 500, 1, 0),
        Array(1002174, 500, 1, 0),
        Array(1002175, 500, 1, 0),
        Array(1002176, 500, 1, 0),
        Array(1002181, 500, 1, 0),
        Array(1002177, 500, 1, 0),
        Array(1002178, 500, 1, 0),
        Array(1002179, 500, 1, 0),
        Array(1002180, 500, 1, 0),
        Array(1002182, 500, 1, 0),
        Array(1002183, 500, 1, 0),
        Array(1002185, 500, 1, 0),
        Array(1002184, 500, 1, 0),
        Array(1002207, 500, 1, 0),
        Array(1002208, 500, 1, 0),
        Array(1002209, 500, 1, 0),
        Array(1002210, 500, 1, 0),
        Array(1002247, 500, 1, 0),
        Array(1002248, 500, 1, 0),
        Array(1002249, 500, 1, 0),
        Array(1051006, 500, 1, 0),
        Array(1051007, 500, 1, 0),
        Array(1051008, 500, 1, 0),
        Array(1051009, 500, 1, 0),
        Array(1072022, 500, 1, 0),
        Array(1072028, 500, 1, 0),
        Array(1072029, 500, 1, 0),
        Array(1072030, 500, 1, 0),
        Array(1072031, 500, 1, 0),
        Array(1072032, 500, 1, 0),
        Array(1072033, 500, 1, 0),
        Array(1072035, 500, 1, 0),
        Array(1072036, 500, 1, 0),
        Array(1072065, 500, 1, 0),
        Array(1072066, 500, 1, 0),
        Array(1072070, 500, 1, 0),
        Array(1072071, 500, 1, 0),
        Array(1072084, 500, 1, 0),
        Array(1072086, 500, 1, 0),
        Array(1072087, 500, 1, 0),
        Array(1072085, 500, 1, 0),
        Array(1072104, 500, 1, 0),
        Array(1072105, 500, 1, 0),
        Array(1072106, 500, 1, 0),
        Array(1072107, 500, 1, 0),
        Array(1072108, 500, 1, 0),
        Array(1072109, 500, 1, 0),
        Array(1072110, 500, 1, 0),
        Array(1072129, 500, 1, 0),
        Array(1072130, 500, 1, 0),
        Array(1072131, 500, 1, 0),
        Array(1072150, 500, 1, 0),
        Array(1072151, 500, 1, 0),
        Array(1072152, 500, 1, 0),
        Array(1082029, 500, 1, 0),
        Array(1082030, 500, 1, 0),
        Array(1082031, 500, 1, 0),
        Array(1082032, 500, 1, 0),
        Array(1082033, 500, 1, 0),
        Array(1082034, 500, 1, 0),
        Array(1082037, 500, 1, 0),
        Array(1082038, 500, 1, 0),
        Array(1082039, 500, 1, 0),
        Array(1082042, 500, 1, 0),
        Array(1082043, 500, 1, 0),
        Array(1082044, 500, 1, 0),
        Array(1082045, 500, 1, 0),
        Array(1082046, 500, 1, 0),
        Array(1082047, 500, 1, 0),
        Array(1082065, 500, 1, 0),
        Array(1082066, 500, 1, 0),
        Array(1082067, 500, 1, 0),
        Array(1082074, 500, 1, 0),
        Array(1082075, 500, 1, 0),
        Array(1082076, 500, 1, 0),
        Array(1082092, 500, 1, 0),
        Array(1082093, 500, 1, 0),
        Array(1082094, 500, 1, 0),
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
        Array(1102018, 500, 1, 0),
        Array(1040031, 500, 1, 0),
        Array(1040032, 500, 1, 0),
        Array(1040033, 500, 1, 0),
        Array(1040034, 500, 1, 0),
        Array(1040035, 500, 1, 0),
        Array(1040042, 500, 1, 0),
        Array(1040043, 500, 1, 0),
        Array(1040044, 500, 1, 0),
        Array(1040048, 500, 1, 0),
        Array(1040049, 500, 1, 0),
        Array(1040050, 500, 1, 0),
        Array(1040057, 500, 1, 0),
        Array(1040058, 500, 1, 0),
        Array(1040059, 500, 1, 0),
        Array(1040060, 500, 1, 0),
        Array(1040061, 500, 1, 0),
        Array(1040062, 500, 1, 0),
        Array(1040063, 500, 1, 0),
        Array(1040082, 500, 1, 0),
        Array(1040083, 500, 1, 0),
        Array(1040084, 500, 1, 0),
        Array(1040094, 500, 1, 0),
        Array(1040095, 500, 1, 0),
        Array(1040096, 500, 1, 0),
        Array(1040097, 500, 1, 0),
        Array(1040098, 500, 1, 0),
        Array(1040099, 500, 1, 0),
        Array(1040100, 500, 1, 0),
        Array(1060021, 500, 1, 0),
        Array(1060022, 500, 1, 0),
        Array(1060023, 500, 1, 0),
        Array(1060024, 500, 1, 0),
        Array(1060025, 500, 1, 0),
        Array(1060037, 500, 1, 0),
        Array(1060038, 500, 1, 0),
        Array(1060039, 500, 1, 0),
        Array(1060043, 500, 1, 0),
        Array(1060044, 500, 1, 0),
        Array(1060045, 500, 1, 0),
        Array(1060046, 500, 1, 0),
        Array(1060050, 500, 1, 0),
        Array(1060051, 500, 1, 0),
        Array(1060071, 500, 1, 0),
        Array(1060052, 500, 1, 0),
        Array(1060072, 500, 1, 0),
        Array(1060073, 500, 1, 0),
        Array(1060083, 500, 1, 0),
        Array(1060084, 500, 1, 0),
        Array(1060085, 500, 1, 0),
        Array(1060086, 500, 1, 0),
        Array(1060087, 500, 1, 0),
        Array(1060088, 500, 1, 0),
        Array(1060089, 500, 1, 0)

        );



function start() {
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        if (status == 0) {
            cm.sendOk("不想使用吗？…我的肚子里有各类#b飞侠装备#k哦！");
            cm.dispose();
        }
        status--;
    }
    //奖品展示.上面写第一排建议写最好的也就是下面这一行代码
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