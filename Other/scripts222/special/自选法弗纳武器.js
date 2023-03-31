﻿var status = -1;
var sel;
var apto;
var apfrom;
var weaponlist = [1492239, 1482225, 1462247, 1452261, 1382269, 1372232, 1342107, 1332283, 1472269, 1442280, 1432222, 1422192, 1412184, 1402263, 1322259, 1312207, 1302347];
var useweapon = [];
var equiplist;
var equip;

function start() {
    var Editing = false //false 开始
    if (Editing) {
        cm.sendOk("维护中");
        cm.dispose();
        return;
    }
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else if (mode === -1) {
        cm.dispose();
    } else if (status >= 0 && mode === 0) {

        cm.sendOk("感谢你的光临！");
        cm.dispose();
        return;
    }
    if (status == 0) {
        var txt = "你好，亲爱的脸黑骚年#b" + cm.getPlayer().getName() + "#k\r\n打了这么久四傻都没获得想要的毕业武器吗？\r\n#r五件法弗纳武器可以自选一件法弗纳武器哦！！！#k\r\n#b需要保留的法弗纳武器请不要放在背包！#k \r\n请选择下列你要兑换的武器！\r\n";
        for (var i = 0; i < weaponlist.length; i++) {

            txt += "#L" + weaponlist[i] + "##v" + weaponlist[i] + "##z" + weaponlist[i] + "##l\r\n";
        }
        txt += " ";
        cm.sendSimple(txt);
    } else if (status == 1) {
        equip = selection;
        useweapon = [];
        var strs = "用这5件法弗纳武器兑换可以吗？#b不想换请结束对话！#k\r\n";
        equiplist = cm.getEquipList();
        if (equiplist != null) {
            for (var i = 0; i < equiplist.size(); i++) {
                if (weaponlist.indexOf(equiplist.get(i).getItemId()) != -1 && useweapon.length < 5) {
                    strs += "#L" + i + "##v" + equiplist.get(i).getItemId() + "##z" + equiplist.get(i).getItemId() + "##k\r\n";
                    useweapon.push(equiplist.get(i).getItemId());
                }
            }
        }
        if (useweapon.length == 5) {
            cm.sendYesNo(strs);
        } else {
            cm.sendOk("你还没攒够法弗纳武器数量，继续加油哦！");
            cm.dispose();
            return;
        }

    } else if (status == 2) {
        if (cm.canHold(equip)) {
            cm.gainItem(equip, 1);
            for (var i = 0; i < useweapon.length; i ++ ) {
                cm.gainItem(useweapon[i], -1);
            }
            cm.sendOk("恭喜，兑换成功，祝早日功成名就！！！");

        } else {
            cm.sendOk("背包空间不足！！！");
        }
        cm.dispose();
        return;

    }
}