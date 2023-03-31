load(ServerConstants.SCRIPT_PAH+"/"+utils/exchange_template.js")

/**
 * 当前：130血色套装兑换
 * 其中：
 * 0 表示需要的材料id
 * 1 表示需要的材料数量
 * 2 表示兑换的物品id
 * 3 表示兑换到的物品数量
 * 4 表示兑换到的物品是否可以叠加，一般装备不可叠加，道具可叠加
 */
var hatArr = [ //帽子
    [4001137, 8, 1003181, 1, false],
    [4001137, 8, 1003180, 1, false],
    [4001137, 8, 1003179, 1, false],
    [4001137, 8, 1003178, 1, false],
    [4001137, 8, 1003177, 1, false]
]
var clothesArr = [ //衣服
    [4001159, 8, 1052323, 1, false],
    [4001159, 8, 1052322, 1, false],
    [4001159, 8, 1052321, 1, false],
    [4001159, 8, 1052320, 1, false],
    [4001159, 8, 1052319, 1, false]
]
var gloveArr = [ //手套
    [4001455, 8, 1082304, 1, false],
    [4001455, 8, 1082303, 1, false],
    [4001455, 8, 1082302, 1, false],
    [4001455, 8, 1082301, 1, false],
    [4001455, 8, 1082300, 1, false]
]
var shoesArr = [ //鞋子
    [4001198, 8, 1072494, 1, false],
    [4001198, 8, 1072493, 1, false],
    [4001198, 8, 1072492, 1, false],
    [4001198, 8, 1072491, 1, false],
    [4001198, 8, 1072490, 1, false]
]
var CloakArr = [ //披风

    [4032486, 8, 1102284, 1, false],
    [4032486, 8, 1102283, 1, false],
    [4032486, 8, 1102282, 1, false],
    [4032486, 8, 1102281, 1, false],
    [4032486, 8, 1102280, 1, false]
]
var weaponsArr = [ //武器
    [4001158, 10, 1492086, 1, false],
    [4001158, 10, 1482085, 1, false],
    [4001158, 10, 1472123, 1, false],
    [4001158, 10, 1462100, 1, false],
    [4001158, 10, 1452112, 1, false],
    [4001158, 10, 1442117, 1, false],
    [4001158, 10, 1432087, 1, false],
    [4001158, 10, 1422067, 1, false],
    [4001158, 10, 1412066, 1, false],
    [4001158, 10, 1402096, 1, false],
    [4001158, 10, 1382105, 1, false],
    [4001158, 10, 1372085, 1, false],
    [4001158, 10, 1332131, 1, false],
    [4001158, 10, 1322097, 1, false],
    [4001158, 10, 1312066, 1, false],
    [4001158, 10, 1302153, 1, false]
]
var status1 = 0;
var rn = "\r\n\r\n"; // 换行

function start() {
    templateInit(cm, null);
    action(1, 0, 0);
}

function action(mode, type, selection) {
    status1 = status1 + (mode === 1 ? 1 : -1);

    if (mode === -1) {
        cm.dispose();
        return;
    } else if (mode === 0) {
        cm.sendOk("走开走开!.");
        cm.dispose();
        return;
    }
    if (status1 === 1) {
        var add = "请选择你想兑换的装备部位"+rn;
        add += "#L0##b使用#t4001158##v4001158#兑换#k武器部位" + rn;
        add += "#L1##b使用#t4001137##v4001137#兑换#k帽子部位" + rn;
        add += "#L2##b使用#t4001159##v4001159#兑换#k衣服部位" + rn;
        add += "#L3##b使用#t4001455##v4001455#兑换#k手套部位" + rn;
        add += "#L4##b使用#t4001198##v4001198#兑换#k鞋子部位" + rn;
        add += "#L5##b使用#t4032486##v4032486#兑换#k披风部位"+ rn;
        cm.sendSimple(add);
    } else if (status1 === 2) {
        switch (selection) {
            case 0:
                itemArr= weaponsArr;
                break;
            case 1:
                itemArr= hatArr;
                break;
            case 2:
                itemArr=  clothesArr;
                break;
            case 3:
                itemArr=  gloveArr;
                break;
            case 4:
                itemArr= shoesArr;
                break;
            case 5:
                itemArr=  CloakArr;
                break;
            default:
                cm.dispose();
        }

        templateAction(cm, mode, type, selection);
    } else {
        templateAction(cm, mode, type, selection);
    }
}