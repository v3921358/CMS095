
var status = -1;
var itemList = Array(
        Array(1002850, 5, 1, 1),
        Array(1003580, 50, 1, 1), //安全帽	
        Array(1152052, 100, 1, 1),
        Array(1012071, 100, 1, 1), //雪糕全属性
        Array(1012072, 200, 1, 0), //狐猴
        Array(1012073, 200, 1, 0), //狐猴
        Array(1022060, 200, 1, 0), //狐猴
        Array(1022067, 100, 1, 0), //黑狐猴
        Array(1012056, 200, 1, 0), //狗狗鼻子1012056	
        Array(2510177, 450, 1, 1),
        Array(2510182, 450, 1, 1),
        Array(2510187, 450, 1, 1),
        Array(2510192, 450, 1, 1),
        Array(2511106, 100, 1, 1),
        Array(2511040, 250, 1, 1),
        Array(2511079, 250, 1, 1),
        Array(2511097, 250, 1, 1),
        Array(2510175, 150, 1, 1),
        Array(2510176, 150, 1, 1),
        Array(2510239, 150, 1, 1),
        Array(2510171, 150, 1, 1),
        Array(2510172, 150, 1, 1),
        Array(1302002, 500, 1, 0),
        Array(1302008, 500, 1, 0),
        Array(1302009, 500, 1, 0),
        Array(1302010, 500, 1, 0),
        Array(1302011, 500, 1, 0),
        Array(1312005, 500, 1, 0),
        Array(1312007, 500, 1, 0),
        Array(1312008, 500, 1, 0),
        Array(1312009, 500, 1, 0),
        Array(1322014, 500, 1, 0),
        Array(1322016, 500, 1, 0),
        Array(1322017, 500, 1, 0),
        Array(1322018, 500, 1, 0),
        Array(1402000, 500, 1, 0),
        Array(1402002, 500, 1, 0),
        Array(1402003, 500, 1, 0),
        Array(1402007, 500, 1, 0),
        Array(1402011, 500, 1, 0),
        Array(1402053, 500, 1, 0),
        Array(1402062, 500, 1, 0),
        Array(1412002, 500, 1, 0),
        Array(1412003, 500, 1, 0),
        Array(1412005, 500, 1, 0),
        Array(1412006, 500, 1, 0),
        Array(1412007, 500, 1, 0),
        Array(1412035, 500, 1, 0),
        Array(1422001, 500, 1, 0),
        Array(1422003, 500, 1, 0),
        Array(1422005, 500, 1, 0),
        Array(1422007, 500, 1, 0),
        Array(1422009, 500, 1, 0),
        Array(1422039, 500, 1, 0),
        Array(1432002, 500, 1, 0),
        Array(1432004, 500, 1, 0),
        Array(1432005, 500, 1, 0),
        Array(1432006, 500, 1, 0),
        Array(1432022, 500, 1, 0),
        Array(1432050, 500, 1, 0),
        Array(1442001, 500, 1, 0),
        Array(1442003, 500, 1, 0),
        Array(1442007, 500, 1, 0),
        Array(1442009, 500, 1, 0),
        Array(1442010, 500, 1, 0),
        Array(1442071, 500, 1, 0),
        Array(1442078, 500, 1, 0),
        Array(1442080, 500, 1, 0),
        Array(1002002, 400, 1, 0),
        Array(1002003, 400, 1, 0),
        Array(1002004, 400, 1, 0),
        Array(1002005, 400, 1, 0),
        Array(1002006, 400, 1, 0),
        Array(1002007, 400, 1, 0),
        Array(1002009, 400, 1, 0),
        Array(1002011, 400, 1, 0),
        Array(1002021, 400, 1, 0),
        Array(1002022, 400, 1, 0),
        Array(1002023, 400, 1, 0),
        Array(1002024, 400, 1, 0),
        Array(1002025, 400, 1, 0),
        Array(1002027, 400, 1, 0),
        Array(1002028, 400, 1, 0),
        Array(1002029, 400, 1, 0),
        Array(1002039, 400, 1, 0),
        Array(1002040, 400, 1, 0),
        Array(1002041, 400, 1, 0),
        Array(1002042, 400, 1, 0),
        Array(1002043, 400, 1, 0),
        Array(1002044, 400, 1, 0),
        Array(1002045, 400, 1, 0),
        Array(1002046, 400, 1, 0),
        Array(1002047, 400, 1, 0),
        Array(1002048, 400, 1, 0),
        Array(1002049, 400, 1, 0),
        Array(1002050, 400, 1, 0),
        Array(1002051, 400, 1, 0),
        Array(1002052, 400, 1, 0),
        Array(1002055, 400, 1, 0),
        Array(1002056, 400, 1, 0),
        Array(1002058, 400, 1, 0),
        Array(1002059, 400, 1, 0),
        Array(1002084, 400, 1, 0),
        Array(1002085, 400, 1, 0),
        Array(1002086, 400, 1, 0),
        Array(1002087, 400, 1, 0),
        Array(1002088, 400, 1, 0),
        Array(1002091, 400, 1, 0),
        Array(1002092, 400, 1, 0),
        Array(1002093, 400, 1, 0),
        Array(1002098, 400, 1, 0),
        Array(1002099, 400, 1, 0),
        Array(1002100, 400, 1, 0),
        Array(1002101, 400, 1, 0),
        Array(1050000, 400, 1, 0),
        Array(1050005, 400, 1, 0),
        Array(1050006, 400, 1, 0),
        Array(1050007, 400, 1, 0),
        Array(1050011, 400, 1, 0),
        Array(1050021, 400, 1, 0),
        Array(1050022, 400, 1, 0),
        Array(1050163, 400, 1, 0),
        Array(1051010, 400, 1, 0),
        Array(1051011, 400, 1, 0),
        Array(1051012, 400, 1, 0),
        Array(1051013, 400, 1, 0),
        Array(1051014, 400, 1, 0),
        Array(1051015, 400, 1, 0),
        Array(1051016, 400, 1, 0),
        Array(1072000, 400, 1, 0),
        Array(1072002, 400, 1, 0),
        Array(1072003, 400, 1, 0),
        Array(1072007, 400, 1, 0),
        Array(1072009, 400, 1, 0),
        Array(1072011, 400, 1, 0),
        Array(1072039, 400, 1, 0),
        Array(1072040, 400, 1, 0),
        Array(1072041, 400, 1, 0),
        Array(1072046, 400, 1, 0),
        Array(1072047, 400, 1, 0),
        Array(1072051, 400, 1, 0),
        Array(1072052, 400, 1, 0),
        Array(1072053, 400, 1, 0),
        Array(1072112, 400, 1, 0),
        Array(1072113, 400, 1, 0),
        Array(1072126, 400, 1, 0),
        Array(1072127, 400, 1, 0),
        Array(1072132, 400, 1, 0),
        Array(1072133, 400, 1, 0),
        Array(1072134, 400, 1, 0),
        Array(1072135, 400, 1, 0),
        Array(1072147, 400, 1, 0),
        Array(1072148, 400, 1, 0),
        Array(1072149, 400, 1, 0),
        Array(1072168, 400, 1, 0),
        Array(1082000, 400, 1, 0),
        Array(1082001, 400, 1, 0),
        Array(1082003, 400, 1, 0),
        Array(1082004, 400, 1, 0),
        Array(1082005, 400, 1, 0),
        Array(1082006, 400, 1, 0),
        Array(1082007, 400, 1, 0),
        Array(1082008, 400, 1, 0),
        Array(1082009, 400, 1, 0),
        Array(1082010, 400, 1, 0),
        Array(1082011, 400, 1, 0),
        Array(1082023, 400, 1, 0),
        Array(1082024, 400, 1, 0),
        Array(1082025, 400, 1, 0),
        Array(1082035, 400, 1, 0),
        Array(1082036, 400, 1, 0),
        Array(1082059, 400, 1, 0),
        Array(1082060, 400, 1, 0),
        Array(1082061, 400, 1, 0),
        Array(1102000, 400, 1, 0),
        Array(1102001, 400, 1, 0),
        Array(1102002, 400, 1, 0),
        Array(1102003, 400, 1, 0),
        Array(1102004, 400, 1, 0),
        Array(1102011, 400, 1, 0),
        Array(1102012, 400, 1, 0),
        Array(1102013, 400, 1, 0),
        Array(1102014, 400, 1, 0),
        Array(1102015, 400, 1, 0),
        Array(1102016, 400, 1, 0),
        Array(1102017, 400, 1, 0),
        Array(1102018, 400, 1, 0)
//Array(1002743,850,1,0),//海洋之帽

        /*Array(1402063,100,1,1),//樱花伞
         Array(1302058,300,1,1),//枫叶伞			
         Array(1092022,100,1,1),//调色板盾牌							
         Array(1302080,200,1,0),//冒险岛小灯泡
         Array(1382015,300,1,1),//毒蘑菇
         Array(1382016,300,1,1),//香菇
         Array(1402044,600,1,1),//南瓜灯笼
         Array(1302024,500,1,0),//废报纸武器
         Array(1322051,300,1,0),//七夕
         Array(1302021,200,1,1),//橡皮榔头
         Array(1092008,200,1,1),//锅盖
         Array(1322003,200,1,1),//锅盖
         Array(1432013,200,1,1),//南瓜枪
         Array(1372017,200,1,1),//领路灯
         Array(1322027,200,1,1),//平底锅
         Array(1442039,300,1,0),//冻冻鱼
         Array(1332021,200,1,0),//奇迹茶
         Array(1302128,200,1,0),//火柴	
         Array(1302084,200,1,1),//火柴
         Array(1432037,300,1,0),//白日剑
         Array(1302036,300,1,1),//枫叶小旗
         Array(1302049,300,1,0)//光线鞭	*/


        )
function start() {
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        if (status == 0) {
            cm.sendOk("不想使用吗？…我的肚子里有各类#b战士装备#k哦！");
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
            cm.sendSimple("冒险岛转蛋机中有各类#b装备、卷轴或稀有新奇的道具#k噢！使用“#b#t5451001##k”就可以抽奖，否则是不可以使用我的。现在要玩转蛋机么? \r\n#b#L2#试试手气吧#l#k\r\n#b#L4#十连抽#l#k\r\n\r\n\r\n奖励展示:\r\n" + jpzs);
        } else {
            cm.sendSimple("冒险岛转蛋机中有各类#b装备、卷轴或稀有新奇的道具#k噢！使用“#b#t5451001##k”就可以交换。 假如不买转蛋券的话，是不可以使用我的。现在要玩转蛋机么?    \r\n\r\n#r你背包里没#v5451001##z5451001#\r\n\r\n#k奖励展示:\r\n" + jpzs + "");
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
            /*if (itemId == 1402062){
             var ii = MapleItemInformationProvider.getInstance();              
             var type = ii.getInventoryType(itemId); //获得装备的类形
             var toDrop = ii.randomizeStats(ii.getEquipById(itemId)).copy(); // 生成一个Equip类 
             toDrop.setFlag(1);	
             //toDrop.setHp(1000);
             //toDrop.setMp(1000);
             toDrop.setStr(7);
             toDrop.setDex(10);
             toDrop.setInt(10);
             toDrop.setLuk(10);
             cm.getPlayer().getInventory(type).addItem(toDrop);//将这个装备放入包中
             cm.getC().getSession().write(MaplePacketCreator.addInventorySlot(type, toDrop)); //刷新背包	
             cm.getChar().saveToDB(false, false);
             cm.worldMessage(5,"恭喜"+cm.getPlayer().getName()+"从快乐百宝箱中抽取到满卷贝音双手剑,大家恭喜他");
             } else {*/
            item = cm.gainGachaponItem(itemId, quantity, "快乐百宝箱", notice);
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
                /*if (itemId == 1402062){
                 var ii = MapleItemInformationProvider.getInstance();              
                 var type = ii.getInventoryType(itemId); //获得装备的类形
                 var toDrop = ii.randomizeStats(ii.getEquipById(itemId)).copy(); // 生成一个Equip类 
                 toDrop.setFlag(1);	
                 //toDrop.setHp(1000);
                 //toDrop.setMp(1000);
                 toDrop.setStr(7);
                 toDrop.setDex(10);
                 toDrop.setInt(10);
                 toDrop.setLuk(10);
                 cm.getPlayer().getInventory(type).addItem(toDrop);//将这个装备放入包中
                 cm.getC().getSession().write(MaplePacketCreator.addInventorySlot(type, toDrop)); //刷新背包	
                 cm.getChar().saveToDB(false, false);
                 cm.worldMessage(5,"恭喜"+cm.getPlayer().getName()+"从快乐百宝箱中抽取到满卷贝音双手剑,大家恭喜他");
                 } else {*/
                item = cm.gainGachaponItem(itemId, quantity, "快乐百宝箱", notice);
                //}
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