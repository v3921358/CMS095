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
            var add = "\r\n\t\t" + cyf + "#d#e欢迎来到~PPMS怀旧服#n#k" + cyf + "\r\n";
            //   add += " \t#L1222##v2440002# #v2440002##e#r" + kx + "春节活动" + kx + "#v2440002# #v2440002##l#n#k\r\n";
            if (cm.getPlayer().getBossLogD("元旦福利") < 1 && cm.getPlayer().getBossLogS("元旦福利") < 3) {
                //    add += "\t#L1223##v2440002# #v2440002##e#r"+kx+"领取元旦福利"+kx+"#v2440002# #v2440002##l#n#k\r\n\"; 
            }
            add += "\t\t#L10014#" + cyf + "#r#e  全民马拉松  " + cyf + "#n#k#l\r\n\r\n";
            add += "#e#L10002#" + kx + "个人信息" + kx + "#l #L10003#" + kx + "快捷传送" + kx + "#l #L10004#" + kx + "每日任务" + kx + "#l#k\r\n\r\n";
            add += "#e#r#L10005#" + kx + "新手礼包" + kx + "#k#l #L10006#" + kx + "购物中心" + kx + "#l #L10007#" + kx + "兑换中心" + kx + "#l#k\r\n\r\n";
            add += "#e#L10008#" + kx + "怪物爆率" + kx + "#l #L10009#" + kx + "掉落查询" + kx + "#l #L10010#" + kx + "特色系统" + kx + "#l#k\r\n\r\n";
            add += "#e#L10011#" + kx + "装备增幅" + kx + "#l #L10012#" + kx + "小赌怡情" + kx + "#l #L10013#" + kx + "推广系统" + kx + "#l#k\r\n\r\n";
            add += "  #L10001#" + ccx + "高版本BOSS挑战中心" + ccx + "#l \r\n";
            add += " ";
            //补充拍卖出券
            if(!cm.haveItem(2022615)){
                cm.gainItem(2022615,1);
                cm.playerMessage("已补充拍卖券，放在快捷键可快速打开拍卖中心~");
            }
            if (cm.getPlayer().isGM()) {
                add += "  #L20001#" + ccx + "GM刷钱刷金币" + ccx + "#l \r\n";
            }
            cm.sendOk(add);

            //------------------------------------------------------------------------

        } else if (status == 1) {
            switch (selection) {
                case 10001:
                    cm.dispose();
                    cm.openNpc(1064002, "高版本BOSS挑战");
                    break;
                case 10002:
                    cm.dispose();
                    cm.openNpc(9900004, "个人信息");
                    break;
                case 10003:
                    cm.dispose();
                    cm.openNpc(9900004, "快捷传送");
                    break;
                case 10004:
                    cm.dispose();
                    cm.openNpc(9900004, "每日任务");
                    break;
                case 10005:
                    cm.dispose();
                    cm.openNpc(9900004, "新手礼包");
                    break;
                case 10006:
                    cm.dispose();
                    cm.openNpc(9900004, "购物中心");
                    break;
                case 10007:
                    cm.dispose();
                    cm.openNpc(9900004, "兑换中心");
                    break;
                case 10008:
                    cm.dispose();
                    cm.openNpc(9900004, "怪物爆率");
                    break;
                case 10009:
                    cm.dispose();
                    cm.openNpc(9900004, "掉落查询");
                    break;
                case 10010:
                    cm.dispose();
                    cm.openNpc(9900004, "特色系统");
                    break;
                case 10011:
                    cm.dispose();
                    cm.openNpc(9900004, "装备增幅");
                    break;
                case 10012:
                    cm.dispose();
                    cm.openNpc(9900004, "小赌怡情");
                    break;
                case 10013:
                    cm.dispose();
                    cm.openNpc(9900004, "推广系统");
                    break;
                case 10014:
                    cm.dispose();
                    cm.openNpc(9900004, "全民马拉松");
                    break;
                case 20001:
                    cm.dispose();
                    cm.gainMeso(999999999);
                    cm.gainNX(99999);
                    cm.gainNX2(99999);
                    cm.getPlayer().setVip(6);
                    break;
                default:
            }

        }
    }
}

