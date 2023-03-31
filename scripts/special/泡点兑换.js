var 爱心 = "#fEffect/CharacterEff/1022223/4/0#";
var 红色箭头 = "#fUI/UIWindow/Quest/icon6/7#";
var 正方形 = "#fUI/UIWindow/Quest/icon3/6#";
var 蓝色箭头 = "#fUI/UIWindow/Quest/icon2/7#";
var nx2arr = [500, 2000, 3000, 8000, 8000, 30000, 100000];
var randomNum = Math.floor(Math.random() * 100) + 1;

function start() {
    status = -1;

    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (status >= 0 && mode == 0) {

            cm.sendOk("感谢你的光临！");
            cm.dispose();
            return;
        }
        if (mode == 1) {
            status++;
        } else {
            status--;
        }
        var 等级 = cm.getPlayer().getLevel();
        if (status == 0) {
            var tex2 = "";
            var text = "";
            text += "您好在我这里可以使用抵用券兑换#b枫叶#k\r\n\r\n";
            text += "" + 蓝色箭头 + "#L0##b#v5200000##*" + nx2arr[0] + "#k 兑换#r #v4001126# x 20 #k#l \r\n\r\n";
            text += "" + 蓝色箭头 + "#L1##b#v5200000##*" + nx2arr[1] + "#k 兑换#r #v4001126# x 80 #k#l \r\n\r\n";
            text += "" + 蓝色箭头 + "#L2##b#v5200000##*" + nx2arr[2] + "#k 兑换#r #v4001126# x 88 #b（10%几率获得）##v2450022# x 1  #k#l \r\n\r\n";
            text += "" + 蓝色箭头 + "#L3##b#v5200000##*" + nx2arr[3] + "#k 兑换#r #v4001126# x 350 #k#l \r\n\r\n";
            text += "" + 蓝色箭头 + "#L4##b#v5200000##*" + nx2arr[4] + "#k 兑换#r #v4001126# x 288 #b（30%几率获得）##v2450022# x 1  #k#l \r\n\r\n";
            text += "" + 蓝色箭头 + "#L5##b#v5200000##*" + nx2arr[5] + "#k 兑换#r #v4001126# x 800 #b（60%几率获得）##v2450022# x 2  #k#l \r\n\r\n";
            text += "" + 蓝色箭头 + "#L6##b#v5200000##*" + nx2arr[6] + "#k 兑换#r #v4032226# x 20  #v4001465# x 20 #b（5%几率获得）##v2450022# x 5  #k#l \r\n\r\n";

            cm.sendSimple(text);

        } else if (status == 1) {
            if (cm.getInventory(1).isFull(4)&&cm.getInventory(2).isFull(4)&&cm.getInventory(3).isFull(4)&&cm.getInventory(4).isFull(4)&&cm.getInventory(5).isFull(4)) {
                cm.sendOk("请保证 #b背包栏#k 至少有5个位置。");
                cm.dispose();
                return;
            } 
            if (cm.getPlayer().getCSPoints(2) < nx2arr[selection]) {
                cm.sendOk("#v5200000#抵用券不足无法兑换！");
                cm.dispose(); //结束
                return;
            }
            cm.gainNX2(-nx2arr[selection]); //扣除抵用券数量
            if (selection == 0) {
                
                cm.gainItem(4001126, 20);
                cm.sendOk("换购成功！");
                cm.dispose(); //结束
            } else if (selection == 1) {
                cm.gainItem(4001126, 80);
                cm.sendOk("换购成功！");
                cm.dispose(); //结束


            } else if (selection == 2) {
                cm.gainItem(4001126, 88);
                if(randomNum<=10){
                    cm.gainItem(2450022, 1);
                }
                cm.sendOk("换购成功！");
                cm.dispose(); //结束


            } else if (selection == 3) {
                cm.gainItem(4001126, 350);
                cm.sendOk("换购成功！");
                cm.dispose(); //结束


            } else if (selection == 4) {
                cm.gainItem(4001126, 288);
                if(randomNum<=30){
                    cm.gainItem(2450022, 1);
                }
                cm.sendOk("换购成功！");
                cm.dispose(); //结束

            } else if (selection == 5) {
                cm.gainItem(4001126, 800);
                if(randomNum<=60){
                    cm.gainItem(2450022, 2);
                }
                cm.sendOk("换购成功！");
                cm.dispose(); //结束

            } else if (selection == 6) {
                cm.gainItem(4032226, 20);
                cm.gainItem(4001465, 20);
                if(randomNum<=5){
                    cm.gainItem(2450022, 5);
                }
                cm.sendOk("换购成功！");
                cm.dispose(); //结束
            }
        }
    }
}