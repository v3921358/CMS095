var status = 0;
var selectedType = -1;
var selectedItem = -1;
var stimulator = false;
var itemSet;
var item;
var itemNum;
var mats;
var matQty;
var cost;
var add;

var wq = Array(
    Array(1702019, 8888), //武器（物品代码，价格）
    Array(1702718, 8888),
    Array(1702466, 8888),
    Array(1702660, 8888),
    Array(1702640, 8888),
    Array(1702505, 8888),
    Array(1702885, 8888),
    Array(1702713, 8888),
    Array(1702613, 8888),
    Array(1702347, 8888),
    Array(1702504, 8888),
    Array(1702390, 8888),
    Array(1702608, 8888),
    Array(1702275, 8888),
    Array(1702249, 8888)
);

var yf = Array(
    Array(1042000, 6000), //衣服（物品代码，价格）
    Array(1042001, 6000),
    Array(1042002, 6000),
    Array(1042003, 6000),
    Array(1042004, 6000),
    Array(1042005, 6000),
    Array(1042005, 6000),
    Array(1042006, 6000),
    Array(1042007, 6000),
    Array(1042008, 6000),
    Array(1042009, 6000),
    Array(1042010, 6000),
    Array(1042011, 6000),
    Array(1042012, 6000),
    Array(1042013, 6000),
    Array(1042014, 6000),
    Array(1042015, 6000),
    Array(1042016, 6000),
    Array(1042017, 6000),
    Array(1042018, 6000),
    Array(1042019, 6000),
    Array(1042020, 6000),
    Array(1042021, 6000),
    Array(1042022, 6000),
    Array(1042023, 6000),
    Array(1042024, 6000),
    Array(1042025, 6000),
    Array(1042026, 6000),
    Array(1042027, 6000),
    Array(1042028, 6000),
    Array(1042029, 6000),
    Array(1042030, 6000),
    Array(1042031, 6000),
    Array(1042032, 6000),
    Array(1042033, 6000),
    Array(1042034, 6000),
    Array(1042035, 6000),
    Array(1042036, 6000),
    Array(1042037, 6000),
    Array(1042038, 6000),
    Array(1042039, 6000),
    Array(1042040, 6000),
    Array(1042041, 6000),
    Array(1042042, 6000),
    Array(1042043, 6000),
    Array(1042044, 6000),
    Array(1042045, 6000),
    Array(1042046, 6000),
    Array(1042047, 6000),
    Array(1042048, 6000),
    Array(1042049, 6000),
    Array(1042050, 6000),
    Array(1042051, 6000),
    Array(1042052, 6000),
    Array(1042053, 6000),
    Array(1042054, 6000),
    Array(1042055, 6000),
    Array(1042056, 6000),
    Array(1042057, 6000),
    Array(1042058, 6000),
    Array(1042059, 6000),
    Array(1042060, 6000)
);

var kz = Array(
    Array(1062130, 6000), //裤子（物品代码，价格）
    Array(1062131, 6000),
    Array(1062133, 6000),
    Array(1062134, 6000),
    Array(1062135, 6000),
    Array(1062136, 6000),
    Array(1062137, 6000),
    Array(1062138, 6000),
    Array(1062139, 6000),
    Array(1062140, 6000),
    Array(1062141, 6000),
    Array(1062142, 6000),
    Array(1062143, 6000),
    Array(1062144, 6000),
    Array(1062145, 6000),
    Array(1062146, 6000),
    Array(1062147, 6000),
    Array(1062151, 6000),
    Array(1062152, 6000),
    Array(1062153, 6000),
    Array(1062154, 6000),
    Array(1062155, 6000),
    Array(1062156, 6000),
    Array(1062157, 6000),
    Array(1062159, 6000),
    Array(1062160, 6000),
    Array(1062161, 6000),
    Array(1062162, 6000),
    Array(1062163, 6000),
    Array(1062171, 6000),
    Array(1062172, 6000),
    Array(1062173, 6000),
    Array(1062174, 6000),
    Array(1062175, 6000),
    Array(1062176, 6000),
    Array(1062179, 6000),
    Array(1062182, 6000),
    Array(1062183, 6000),
    Array(1062184, 6000),
    Array(1062185, 6000),
    Array(1062186, 6000),
    Array(1062187, 6000),
    Array(1062188, 6000),
    Array(1062189, 6000),
    Array(1062203, 6000)
);

var tz = Array(
    Array(1052660, 6000), //套装（物品代码，价格）
    Array(1052661, 6000),
    Array(1052666, 6000),
    Array(1052667, 6000),
    Array(1052668, 6000),
    Array(1052674, 6000),
    Array(1052675, 6000),
    Array(1052684, 6000),
    Array(1052686, 6000),
    Array(1052687, 6000),
    Array(1052691, 6000),
    Array(1052692, 6000),
    Array(1052693, 6000),
    Array(1052697, 6000),
    Array(1052709, 6000),
    Array(1052712, 6000),
    Array(1052713, 6000),
    Array(1052724, 6000),
    Array(1052725, 6000),
    Array(1052726, 6000),
    Array(1052727, 6000),
    Array(1052728, 6000),
    Array(1052731, 6000),
    Array(1052746, 6000),
    Array(1052747, 6000),
    Array(1052749, 6000),
    Array(1052750, 6000),
    Array(1052754, 6000),
    Array(1052756, 6000),
    Array(1052757, 6000),
    Array(1052761, 6000),
    Array(1052762, 6000),
    Array(1052771, 6000),
    Array(1052772, 6000),
    Array(1052773, 6000),
    Array(1052774, 6000),
    Array(1052779, 6000),
    Array(1052781, 6000),
    Array(1052782, 6000),
    Array(1052841, 6000),
    Array(1052842, 6000),
    Array(1052843, 6000),
    Array(1052844, 6000),
    Array(1052845, 6000),
    Array(1052846, 6000),
    Array(1052849, 6000),
    Array(1052850, 6000),
    Array(1052852, 6000),
    Array(1052853, 6000),
    Array(1052854, 6000),
    Array(1052855, 6000),
    Array(1052856, 6000),
    Array(1052857, 6000),
    Array(1052858, 6000),
    Array(1051228, 6000),
    Array(1051278, 6000),
    Array(1052644, 6000),
    Array(1053155, 6000),
    Array(1052346, 6000),
    Array(1052347, 6000),
    Array(1052697, 6000),
    Array(1052859, 6000)
);

var st = Array(
    Array(1082500, 6000), //手套（物品代码，价格）
    Array(1082501, 6000),
    Array(1082502, 6000),
    Array(1082503, 6000),
    Array(1082504, 6000),
    Array(1082505, 60000),
    Array(1082520, 6000),
    Array(1082525, 6000),
    Array(1082527, 6000),
    Array(1082551, 6000),
    Array(1082552, 6000),
    Array(1082618, 6000),
    Array(1082620, 6000),
    Array(1082631, 6000),
    Array(1082685, 6000),
    Array(1082689, 6000)
);

var xz = Array(
    Array(1072999, 6000), //鞋子（物品代码，价格）
    Array(1073008, 6000),
    Array(1073009, 6000),
    Array(1073010, 6000),
    Array(1073008, 6000),
    Array(1073011, 6000),
    Array(1073012, 6000),
    Array(1073013, 6000),
    Array(1073014, 6000),
    Array(1073017, 6000),
    Array(1073019, 6000),
    Array(1073022, 6000),
    Array(1073023, 6000),
    Array(1073024, 6000),
    Array(1073025, 6000),
    Array(1073027, 6000),
    Array(1073098, 6000)
);


var sp = Array(
    Array(1112816, 6000), //饰品（物品代码，价格）
    Array(1112015, 6000),
    Array(1112012, 6000)
);

var mz = Array(
    Array(1004540, 6000), //帽子（物品代码，价格）
    Array(1004635, 6000), //毛线编织帽
    Array(1002960, 6000), //暗夜娃娃皇冠
    Array(1004639, 6000), //北极罩帽
    Array(1004665, 6000), //暖绒兔兔帽
    Array(1004638, 6000), //茅山道士帽
    Array(1004592, 6000), //紫色时间
    Array(1004592, 6000), //粉色时间
    Array(1004589, 6000), //侠盗猫眼罩
    Array(1004570, 6000), //黑色海魂帽
    Array(1004571, 6000), //海贼团贝雷帽
    Array(1004541, 6000), //茶会大蝴蝶结
    Array(1004540, 6000), //奥尔卡的睡帽
    Array(1004506, 6000), //白色兔耳发带
    Array(1004504, 6000), //贵族花纹帽子
    Array(1004499, 6000), //蓝色金鱼帽子
    Array(1004500, 6000), //蓝色金鱼帽子
    Array(1004491, 6000), //西红柿帽子
    Array(1004481, 6000), //反抗者护目镜
    Array(1004480, 6000), //小淘气的飞行帽
    Array(1004470, 6000), //毛绒雷锋帽
    Array(1004469, 6000), //爱情宣言
    Array(1004463, 6000), //方块兔帽子
    Array(1004448, 6000), //黑色啵啵鼠帽
    Array(1004409, 6000), //松鼠的休闲帽
    Array(1004403, 6000), //嘻哈兔子
    Array(1004405, 6000), //睡虎休闲帽
    Array(1004397, 6000), //清扫头巾
    Array(1004336, 6000), //暴走斯乌假发
    Array(1004330, 6000), //奢华羊绒小礼帽
    Array(1004329, 6000), //蓝色棒球帽发夹
    Array(1004324, 6000), //防毒面罩
    Array(1004294, 6000), //甜柿帽
    Array(1004295, 6000), //音乐飞天小鸡帽
    Array(1004296, 6000), //萌动飞天小鸡帽
    Array(1004298, 6000), //泰迪萌犬帽(白)
    Array(1004299, 6000), //泰迪萌犬帽(白)
    Array(1004275, 6000), //幸运帽
    Array(1004269, 6000), //苹果蒂风情帽
    Array(1004239, 6000), //桃太郎帽子
    Array(1004212, 6000), //晶莹精致丝带
    Array(1004211, 6000), //哈尼绒绒耳
    Array(1004202, 6000), //隐武士战盔
    Array(1004200, 6000), //夏日兔草帽
    Array(1004198, 6000), //太极发箍
    Array(1004196, 6000), //风车头箍
    Array(1004194, 6000), //蝴蝶结贝雷帽
    Array(1004169, 6000), //美味荷包蛋帽
    Array(1004171, 6000), //旋转木马帽
    Array(1004163, 6000), //爱丽丝表丝带
    Array(1004158, 6000), //鼠鼠派对发箍
    Array(1004025, 6000), //绿猫猫帽子
    Array(1004033, 6000), //红猫猫帽子
    Array(1004003, 6000), //粉红猫耳套头帽
    Array(1003968, 6000), //巧克力绵羊玩偶帽
    Array(1003951, 6000), //欧黛特头箍
    Array(1003952, 6000), //欧黛特头箍
    Array(1003937, 6000), //浪漫斗笠
    Array(1003917, 6000), //粉红太阳镜帽
    Array(1003900, 6000), //蓝色桃心透明帽子
    Array(1003859, 6000), //满天星普赛克
    Array(1003847, 6000), //鬼剑士假发
    Array(1003831, 6000), //卷卷绵羊发卡
    Array(1003777, 6000), //伶俐猫咪斗篷
    Array(1003778, 6000), //可爱猫咪斗篷
    Array(1003779, 6000), //爱丽丝兔兔帽
    Array(1003763, 6000), //黑色之翼首领帽
    Array(1003710, 6000), //[MS折扣]怪医黑杰克帽
    Array(1003417, 6000), //恐龙帽子
    Array(1003203, 6000), //小红帽
    Array(1003149, 6000), //洛比尔兔子斗篷
    Array(1003146, 6000), //蕾丝蝴蝶结发箍
    Array(1003147, 6000), //天蓝女仆头饰
    Array(1003131, 6000), //黑色精致丝带
    Array(1003122, 6000), //黄色兔兔巾
    Array(1003123, 6000), //黄色兔兔巾
    Array(1003109, 6000), //皇家彩虹斗篷
    Array(1002976, 6000), //女仆头饰
    Array(1002721, 6000), //狸毛护耳
    Array(1002704, 6000), //黄独眼怪婴
    Array(1002691, 6000), //半人马假发(棕色)
    Array(1002695, 6000), //幽灵帽
    Array(1004636, 6000), //香蕉郊游帽
    Array(1004602, 6000), //农夫的瑰宝
    Array(1004601, 6000), //小企鹅帽子
    Array(1004589, 6000), //侠盗猫眼罩
    Array(1004570, 6000), //黑色海魂帽
    Array(1004571, 6000), //海贼团贝雷帽
    Array(1004568, 6000), //呆萌鼠鼠帽
    Array(1004543, 6000), //复古头巾
    Array(1003883, 6000),
    Array(1002754, 6000)
);
var dj = Array(
    Array(5064000, 5000,1),
    Array(5064000, 48888,11)
);
var pf = Array(
    Array(1102787, 6000)
);

function start() {
    var text = "亲爱的老板~请问您想要什么样的点装呢?#b\r\n";
    text += "#d点卷余额：#b" + cm.getPlayer().getCSPoints(1) + "#k\r\n";
    text += "#d抵用余额：#b" + cm.getPlayer().getCSPoints(2) + "#k#n\r\n";
    var options = new Array("武器", "衣服", "裤子", "套装", "手套", "鞋子", "饰品", "帽子","披风", "道具(防爆卷等)");

    for (var i = 0; i < options.length; i++) {
        text += "\r\n#L" + i + "# " + options[i] + "#l";
    }
    cm.sendSimple(text);
}

function action(mode, type, selection) {
    if (mode > 0)
        status++;
    else {
        cm.dispose();
        return;
    }
    if (status == 1) {
        selectedType = selection;
        if (selectedType == 0) { //武器
            add = "请选择你想要的点装\r\n";
            itemSet = wq;
            for (i = 0; i < itemSet.length; i++) {
                add += "\r\n#L" + i + "##b#i" + itemSet[i][0] + "# ";
                add += "#z" + itemSet[i][0] + "##k ";
                add += "  需要点卷: " + itemSet[i][1] + "";
            };
            cm.sendSimple(add);
        } else if (selectedType == 1) { //衣服
            add = "请选择你想要的点装\r\n";
            itemSet = yf;
            for (i = 0; i < itemSet.length; i++) {
                add += "\r\n#L" + i + "##b#i" + itemSet[i][0] + "# ";
                add += "#z" + itemSet[i][0] + "##k ";
                add += "  需要点卷: " + itemSet[i][1] + "";
            };
            cm.sendSimple(add);
        } else if (selectedType == 2) { //裤子
            add = "请选择你想要的点装\r\n";
            itemSet = kz;
            for (i = 0; i < itemSet.length; i++) {
                add += "\r\n#L" + i + "##b#i" + itemSet[i][0] + "# ";
                add += "#z" + itemSet[i][0] + "##k ";
                add += "  需要点卷: " + itemSet[i][1] + "";
            };
            cm.sendSimple(add);
        } else if (selectedType == 3) { //套装
            add = "请选择你想要的点装\r\n";
            itemSet = tz;
            for (i = 0; i < itemSet.length; i++) {
                add += "\r\n#L" + i + "##b#i" + itemSet[i][0] + "# ";
                add += "#z" + itemSet[i][0] + "##k ";
                add += "  需要点卷: " + itemSet[i][1] + "";
            };
            cm.sendSimple(add);
        } else if (selectedType == 4) { //手套
            add = "请选择你想要的点装\r\n";
            itemSet = st;
            for (i = 0; i < itemSet.length; i++) {
                add += "\r\n#L" + i + "##b#i" + itemSet[i][0] + "# ";
                add += "#z" + itemSet[i][0] + "##k ";
                add += "  需要点卷: " + itemSet[i][1] + "";
            };
            cm.sendSimple(add);
        } else if (selectedType == 5) { //鞋子
            add = "请选择你想要的点装\r\n";
            itemSet = xz;
            for (i = 0; i < itemSet.length; i++) {
                add += "\r\n#L" + i + "##b#i" + itemSet[i][0] + "# ";
                add += "#z" + itemSet[i][0] + "##k ";
                add += "  需要点卷: " + itemSet[i][1] + "";
            };
            cm.sendSimple(add);
        } else if (selectedType == 6) { //饰品
            add = "请选择你想要的点装\r\n";
            itemSet = sp;
            for (i = 0; i < itemSet.length; i++) {
                add += "\r\n#L" + i + "##b#i" + itemSet[i][0] + "# ";
                add += "#z" + itemSet[i][0] + "##k ";
                add += "  需要点卷: " + itemSet[i][1] + "";
            };
            cm.sendSimple(add);
        } else if (selectedType == 7) { //帽子
            add = "请选择你想要的点装\r\n";
            itemSet = mz;
            for (i = 0; i < itemSet.length; i++) {
                add += "\r\n#L" + i + "##b#i" + itemSet[i][0] + "# ";
                add += "#z" + itemSet[i][0] + "##k ";
                add += "  需要点卷: " + itemSet[i][1] + "";
            };
            cm.sendSimple(add);
        } else if (selectedType == 9) { //道具
            add = "请选择你想要的道具\r\n";
            itemSet = dj;
            for (i = 0; i < itemSet.length; i++) {
                add += "\r\n#L" + i + "##b#i" + itemSet[i][0] + "# ";
                add += "#z" + itemSet[i][0] + "##k x "+itemSet[i][2];
                add += "  需要点卷: " + itemSet[i][1] + "";
            };
            cm.sendSimple(add);
        }else if (selectedType == 8) { //披风
            add = "请选择你想要的道具\r\n";
            itemSet = pf;
            for (i = 0; i < itemSet.length; i++) {
                add += "\r\n#L" + i + "##b#i" + itemSet[i][0] + "# ";
                add += "#z" + itemSet[i][0] + "##k ";
                add += "  需要点卷: " + itemSet[i][1] + "";
            };
            cm.sendSimple(add);
        }

    } else if (status == 2) {
        selectedItem = selection;
        item = itemSet[selectedItem][0];
        cost = itemSet[selectedItem][1];
        itemNum= itemSet[selectedItem].length==3?itemSet[selectedItem][2]:1;
        var bdd = "你确定要购买";
        bdd += "#v" + item +  "##z" + item + "#"+"x"+itemNum;
        bdd += "  \r\n  需要点卷:#r " + cost + "\r\n";
        cm.sendYesNo(bdd);
    } else if (status == 3) {
        if (cm.getPlayer().getCSPoints(1) < cost) {
            cm.sendOk("#b您的点券不足呀~");
            cm.dispose();
        } else {

            cm.gainNX(-cost); //点券
            cm.gainItem(item, itemNum);
            cm.sendOk("#b购买成功");
            cm.dispose();

        }
        cm.dispose();
    }
}