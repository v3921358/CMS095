var 爱心 = "#fEffect/CharacterEff/1022223/4/0#";
var 红色箭头 = "#fUI/UIWindow/Quest/icon6/7#";
var 正方形 = "#fUI/UIWindow/Quest/icon3/6#";
var 蓝色箭头 = "#fUI/UIWindow/Quest/icon2/7#";
var 心 =  "#fEffect/CharacterEff/1032063/0/0#"; //长音符
var nx2arr = [1000, 5000, 10000, 50000, 100000, 200000, 100000];
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
            text += "\t      " + 心 + "#r#e < 泡点兑换 > #k#n " + 心 + "\r\n\r\n\r\n";
		    text += "  #d Hi~ #b#h ##k 这里可以进行泡点兑换，而且还有额外的几率获得#v4000313##v5220040##v5451001##v5220000##v5220010#！~~#k#n#b\r\n";
            text += "#L0##b抵用卷*" + nx2arr[0] + "#k 兑换 #v4001126# x 10 #k#l \r\n\r\n";
            text += "#L1##b抵用卷*" + nx2arr[1] + "#k 兑换 #v4001126# x 51 #k#l \r\n\r\n";
            text += "#L2##b抵用卷*" + nx2arr[2] + "#k 兑换 #v4001126# x 120 #b20%#v4000313##v5220040# x 5  #k#l \r\n\r\n";
            text += "#L3##b抵用卷*" + nx2arr[3] + "#k 兑换 #v4001126# x 530 #b40%#v4000313##v5220010# x 10  #k#l \r\n\r\n";
            text += "#L4##b抵用卷*" + nx2arr[4] + "#k 兑换 #v4001126# x 1100 #b60%#v4000313##v5220000# x 20  #k#l \r\n\r\n";
            text += "#L5##b抵用卷*" + nx2arr[5] + "#k 兑换 #v4001126# x 2200 #b80%#v4000313##v5451001# x 30  #k#l \r\n\r\n";
    //        text += "#L6##b抵用卷*" + nx2arr[6] + "#k 兑换 #v4032226# x 20  #v4001465# x 20 #b5%#v4000313# x 5  #k#l \r\n\r\n";

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
                
                cm.gainItem(4001126, 10);
                cm.sendOk("换购成功！");
                cm.dispose(); //结束
            } else if (selection == 1) {
                cm.gainItem(4001126, 51);
                cm.sendOk("换购成功！");
                cm.dispose(); //结束


            } else if (selection == 2) {
                cm.gainItem(4001126, 120);
                if(randomNum<=10){
                    cm.gainItem(4000313, 5);
					cm.gainItem(5220040, 5);
                }
                cm.sendOk("换购成功！");
                cm.dispose(); //结束


            } else if (selection == 3) {
                cm.gainItem(4001126, 530);
                if(randomNum<=30){
                    cm.gainItem(4000313, 10);
					cm.gainItem(5220010, 10);
                }
                cm.sendOk("换购成功！");
                cm.dispose(); //结束

            } else if (selection == 4) {
                cm.gainItem(4001126, 1100);
                if(randomNum<=40){
                    cm.gainItem(4000313, 20);
					cm.gainItem(5220000, 20);
                }
                cm.sendOk("换购成功！");
                cm.dispose(); //结束

            } else if (selection == 5) {
                cm.gainItem(4001126, 2200);
                if(randomNum<=60){
                    cm.gainItem(4000313, 30);
					cm.gainItem(5451001, 30);
                }
                cm.sendOk("换购成功！");
                cm.dispose(); //结束

            } else if (selection == 6) {
                cm.gainItem(4032226, 20);
                cm.gainItem(4001465, 20);
                if(randomNum<=5){
                    cm.gainItem(4000313, 5);
                }
                cm.sendOk("换购成功！");
                cm.dispose(); //结束
            }
        }
    }
}