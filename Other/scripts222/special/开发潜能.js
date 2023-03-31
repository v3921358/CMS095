/**
 * 功能:开发潜能列表
 */

var status = 0;

var 音符 = "#fEffect/CharacterEff/1052203/3/0#"; //小彩星;


var 音符横条 = 音符 + 音符 + 音符 + 音符 + 音符 + 音符 + 音符;
var status = 0;
var typed = 0;
var potList = Array(
    Array(5, "默认属性", 10),
    Array(30291, "无视怪物防御 +30%", 100));
var selnumber = 0;
var oldEquip;
var lbid;
var lblist = [5530127, 5530127, 5530127];
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
            selStr += "       \t\t     #r#e < 开发潜能 > #d#n#k\r\n\r\n";
			selStr += "    针对无法使用潜能力卷轴的装备，可以进行，潜能开发，  开发成功后，与正常装备无样，可以进行魔方重置！#k\r\n\r\n";
            selStr += "#b 需要条件：#k\r\n#b 1、需开发装备位置必须在第一格#k\r\n";
            selStr += "#b 2、开发装备不可为时装#k\r\n";
		    selStr += "#b 3、重复开发不退还材料#k\r\n\r\n";

            selStr += "使用： #L0##v" + lblist[0] + "##z" + lblist[0] + "##r进行开发#l\r\n\r\n";
            selStr += "------------------------------------------------------";
            cm.sendSimpleS(selStr, 2);
        } else if (status == 1) {
			ii = Packages.server.MapleItemInformationProvider.getInstance();
            selnumber = selection;
            oldEquip = cm.getInventory(1).getItem(1);
            if (oldEquip == null) {
                cm.sendOk("背包栏第1个位置不可为空，请检查！");
                cm.dispose();
                return;
            }
            if (ii.isCash(oldEquip.getItemId()) == true) {
                    cm.sendOk("开发装备不可为时装!");
                    cm.dispose();
                return;
            }
            var txt = "#r#e请选择你要开发潜能的属性\r\n\r\n\r\n"
            for (var key in potList) {
                txt += "#L" + key + "##b[" + potList[key][1] + "] #k需要消耗#v" + lblist[selnumber] + "# X " + potList[key][2] + " 个#l\r\n\r\n";
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
                cm.sendOk("恭喜您成功为不可使用，潜能的装备开发了潜能....");
                cm.worldMessage(6, "[开发潜能] 恭喜 " + cm.getChar().getName() + " " + " 开发成功，他/她又多一件衬手的装备！！");
                cm.dispose();
                return;

            } else {
                cm.sendOk("材料不足！");
                cm.dispose();
            }
        }
    }
}
