var status = -1;
var itemList = Array();
var rn = "\r\n\r\n"; // 换行
var useitem = [[4002003, 30], [4310019, 1]];
//[10000,1000]代表1W金币，1000点券
var useMesoAndNx = [10, 10];

function start() {
    action(1, 0, 0);
}

function action(mode, type, selection) {

    if (mode == 1) {
        status++;
    } else {
        if (status == 0) {
            cm.sendOk("倾家荡产了？");
            cm.dispose();
        }
        status--;
    }

    if (status == 0) {
        if (cm.getPlayer().getLevel() < 10) {
            cm.sendOk("你的等级太低，留着钱去搞基础装备，这是大人的娱乐方式！");
            cm.dispose();
            return;
        }
        if (!cm.canHoldSlots(10)) {
            cm.sendOk("请保证#b背包#k至少有 #r10 #k个位置");
            cm.dispose();
            return;
        }


        if (!cm.haveItem(useitem[0][0], useitem[0][1]) || !cm.haveItem(useitem[1][0], useitem[1][1])) {
            cm.sendSimple("全物品抽奖说明：绝对公平随机抽奖，共13000个道具左右，包括装备、时装、技能书、药品、椅子！" + rn + "#v" + useitem[0][0] + "##z" + useitem[0][0] + "##lX" + useitem[0][1] + "，#v" + useitem[1][0] + "##z" + useitem[1][0] + "##lX" + useitem[1][1] + "和" + useMesoAndNx[0] + "金币，" + useMesoAndNx[1] + "点券\r\n抽5件物品。\r\n\r\n#r呵，穷光蛋！你好像没票子啊！没钱还想抽神装？好好去打打钱再来把！\r\n");
            cm.dispose();
            return;
        } else {
            cm.sendSimple("全物品抽奖说明：绝对公平随机抽奖，共13000个道具左右，包括装备、时装、技能书、药品、椅子！" + rn + "#v" + useitem[0] + "##z" + useitem[0] + "##lX" + useitem[0][1] + "，#v" + useitem[1] + "##z" + useitem[1] + "##lX" + useitem[1][1] + "和" + useMesoAndNx[0] + "金币，" + useMesoAndNx[1] + "点券\r\n抽5件物品。\r\n\r\n#b准备好被神装砸中了吗！");
        }
    } else if (status == 1) {
        if (cm.getMeso() < useMesoAndNx[0] || cm.getPlayer().getCSPoints(1) < useMesoAndNx[1]) {
            cm.sendOk("金币或点券不足");
            cm.dispose();
            return;
        }
        getQueryRandomResult();
        var text = "天灵灵！地灵灵！太上老君快显灵！\rn#b你的选择决定你的奖品!#k\rn来选一个把！\rn";
        text += "#L1##r天时#k#l" + rn;
        text += "#L2##r地利#k#l" + rn;
        text += "#L3##r人和#k#l" + rn;
        cm.sendSimple(text);

    } else if (status == 2) {
        var chance = Math.floor(Math.random() * 4);
        var itemSizeAdd = Math.floor(Math.random() * 6);
        var itemids = [];
        cm.gainItem(useitem[0][0], -useitem[0][1]);
        cm.gainItem(useitem[1][0], -useitem[1][1]);
        cm.gainMeso(-useMesoAndNx[0]);
        cm.gainNX(-useMesoAndNx[1]);
        if (chance == selection + '') {
            for (var i = 0; i < 5 + itemSizeAdd; i++) {
                itemids.push(itemList[i]);
                cm.gainItem(itemList[i], 1);
            }
            cm.worldMessage(5, "【全物品抽奖】 " + cm.getPlayer().getName() + "运气爆表！3000万金币抽到了【" + (5 + itemSizeAdd) + "件】物品，大家快去试试运气吧！！！");
        } else {
            for (var i = 0; i < 5; i++) {
                itemids.push(itemList[i]);
                cm.gainItem(itemList[i], 1);
            }
        }
        cm.setBossRankCount("抽奖积分", 5);



        var idtext = "以下是你抽到的物品:\r\n";
        for (var c = 0; c < itemids.length; c++) {
            idtext += "#v" + itemids[c] + "##z" + itemids[c] + "#\r\n";
        }
        cm.sendSimple(idtext);
        cm.safeDispose();
    }
}

function getQueryRandomResult() {
    itemList = Array();
    var sql = "select a.itemid from wz_itemdata a " +
        " LEFT JOIN (select itemid from bugitems) as b ON a.itemid=b.itemid " +
        " left join (select itemid from wz_itemequipdata c where c.key='reqLevel' and value>=150) as c on a.itemid=c.itemid " +
        " where a.itemid<=4000000 and slotmax=1 and  !isnull(name) and name<>''  and length(name)!=char_length(name) " +
        " and b.itemid is null and c.itemid is null" +
        " ORDER BY RAND() LIMIT 10";
    var con = cm.getDataSource().getConnection();
    var ps = con.prepareStatement(sql);
    var rs = ps.executeQuery();

    while (rs.next()) {
        itemList.push(rs.getInt("itemid"));
    }
    ps.close();
    con.close();
}