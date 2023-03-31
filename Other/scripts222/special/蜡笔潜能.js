/**
 * 功能:一键潜能列表
 */

var status = 0;

var 音符 = "#fEffect/CharacterEff/1052203/3/0#"; //小彩星;


var 音符横条 = 音符 + 音符 + 音符 + 音符 + 音符 + 音符 + 音符;
var status = 0;
var typed = 0;
var potList = Array(
    Array(30140, "力1量 +9%", 1),
    Array(30042, "敏捷 +9%", 1),
    Array(30043, "智力 +9%", 1),
    Array(30044, "运气 +9%", 1),
    Array(30051, "物攻 +9%", 1),
    Array(30052, "魔攻 +9%", 1),
    Array(30054, "防御 +9%", 1),
    Array(30055, "暴击 +9%", 1),
    Array(30045, "最大血量 +9%", 1),
    Array(30046, "最大蓝量 +9%", 1),
    Array(40601, "BOSS伤害 +30%", 1),
    Array(30291, "无视怪物防御 +30%", 1));
var selnumber = 0;
var oldEquip;
var lbid;
var lblist = [4001326, 4001328, 4001331];
function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (mode == 0 && status == 0) {
            cm.dispose();
            return;
        }
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 0) {
            var selStr = "";
            selStr += "            " + 音符横条 + "#r#e 蜡笔潜能 #d#n#k" + 音符横条 + "\r\n\r\n";
            selStr += "#b 需要条件：#k\r\n#b 1、一键潜能的装备位置必须在第一格#k\r\n";
            selStr += "#b 2、装备必须鉴定了基础潜能属性#k\r\n\r\n";
            selStr += "     #L0##v" + lblist[0] + "##z" + lblist[0] + "#修改装备潜能第1条属性#l\r\n\r\n";
            selStr += "     #L1##v" + lblist[1] + "##z" + lblist[1] + "#修改装备潜能第2条属性#l\r\n\r\n";
            selStr += "     #L2##v" + lblist[2] + "##z" + lblist[2] + "#修改装备潜能第3条属性#l\r\n\r\n\r\n";
            selStr += " \r\n";
            cm.sendSimpleS(selStr, 2);
        } else if (status == 1) {
            selnumber = selection;
            oldEquip = cm.getInventory(1).getItem(1);
            if (oldEquip == null) {
                cm.sendOk("出现错误: \r\n背包栏第1个位置的装备为空");
                cm.dispose();
                return;
            }
             if (cm.getInventory(1).getItem(i) != null && cm.isCash(cm.getInventory(1).getItem(i).getItemId()) == true) {
                cm.sendOk("出现错误: \r\n装备未鉴定基础潜能属性");
                cm.dispose();
                return;
            }
            var txt = "#r#e请选择你要将第【" + (selnumber + 1) + "】条潜能修改为下列哪个属性\r\n\r\n"
            for (var key in potList) {
                txt += "  #L" + key + "##b[" + potList[key][1] + "] #k需要消耗#v" + lblist[selnumber] + "# X " + potList[key][2] + " 个#l\r\n\r\n";
            }
            txt += " ";
            cm.sendYesNo(txt);
        } else if (status == 2) {
            var newEquip = oldEquip.copy();
            if (cm.haveItem(lblist[selnumber], potList[selection][2])) { //这个地方还需要检测点卷数量
                switch (selnumber) {
                    case 0:
                        newEquip.setPotential1(potList[selection][0]);
                        break;
                    case 1:
                        newEquip.setPotential2(potList[selection][0]);
                        break;
                    case 2:
                        newEquip.setPotential3(potList[selection][0]);
                        break;
                    default:
                        cm.sendOk("出现错误!");
                        cm.dispose();
                        return;
                }
                //todo 扣点卷
                cm.removeSlot(1, 1, 1); //消失的装备
                Packages.server.MapleInventoryManipulator.addFromDrop(cm.getC(), newEquip, false); //强化的装备获取
                cm.sendOk("恭喜您成功洗出潜能属性....");
                cm.worldMessage(6, "[一键潜能] :" + cm.getChar().getName() + " " + " 消耗了蜡笔，重置了装备的一条潜能！");
                cm.dispose();
                return;

            } else {
                cm.sendOk("材料不足！");
                cm.dispose();
            }
        }
    }
}
