/*
 ZEVMS冒险岛(079)游戏服务端
 脚本：星缘，个人在线奖励，自行修改
 */
 var 箭头 = "#fUI/Basic/BtHide3/mouseOver/0#";
 var 心 = "#fUI/GuildMark.img/Mark/Etc/00009001/14#";
 function start() {
     status = -1;
     action(1, 0, 0);
 }
 
 function action(mode, type, selection) {
     if (status == 0 && mode == 0) {
         cm.dispose();
         return;
     }
     if (mode == 1) {
         status++;
     } else {
         status--;
     }
     var 在线奖励1时间 = 30;
     var 在线奖励1代码 = 2000005;
     var 在线奖励1数量 = 50;
 
     var 在线奖励2时间 = 60;
     var 在线奖励2代码 = 4001126;
     var 在线奖励2数量 = 100;
 
     var 在线奖励3时间 = 120;
     var 在线奖励3代码 = 4001126;
     var 在线奖励3数量 = 150;
 
     var 在线奖励4时间 = 180;
     var 在线奖励4代码 = 4001126;
     var 在线奖励4数量 = 200;
 
     var 在线奖励5时间 = 300;
     var 在线奖励5代码 = 4001126;
     var 在线奖励5数量 = 250;
 
     var 在线奖励6时间 = 360;
     var 在线奖励6代码 = 4001126;
     var 在线奖励6数量 = 300;
 
     var 在线奖励7时间 = 420;
     var 在线奖励7代码 = 4001126;
     var 在线奖励7数量 = 350;
 
     var 在线奖励8时间 = 480;
     var 在线奖励8代码 = 4001126;
     var 在线奖励8数量 = 400;
     if (status == 0) {
         var selStr = "	  Hi~#b#h ##k 你今日在线:#b #n#e" + cm.getTodayOnlineTime() + " #n#k分钟#k，你是不是想要找我领取奖品呢。\r\n";
 
         selStr += "在线 #r" + 在线奖励1时间 + " #k分钟可领取 #v" + 在线奖励1代码 + " ##z" + 在线奖励1代码 + "# × " + 在线奖励1数量 + " 抵用券 × 100\r\n";
         if (cm.getTodayOnlineTime() >= 在线奖励1时间 && cm.getBossLogD("在线奖励1时间") == 0) {
             selStr += "#L1#" + 箭头 + "#b领取#r" + 在线奖励1时间 + "#k#b分钟奖励#l#k\r\n";
         }
 
         selStr += "在线 #r" + 在线奖励2时间 + " #k分钟可领取 #v" + 在线奖励2代码 + "##z" + 在线奖励2代码 + "# × " + 在线奖励2数量 + " 抵用券 × 200\r\n";
         if (cm.getTodayOnlineTime() >= 在线奖励2时间 && cm.getBossLogD("在线奖励2时间") == 0) {
             selStr += "#L2#" + 箭头 + "#b领取#r" + 在线奖励2时间 + "#k#b分钟奖励#l#k\r\n";
         }
 
         selStr += "在线 #r" + 在线奖励3时间 + " #k分钟可领取 #v" + 在线奖励3代码 + "##z" + 在线奖励3代码 + "# × " + 在线奖励3数量 + " 抵用券 × 400\r\n";
         if (cm.getTodayOnlineTime() >= 在线奖励3时间 && cm.getBossLogD("在线奖励3时间") == 0) {
             selStr += "#L3#" + 箭头 + "#b领取#r" + 在线奖励3时间 + "#k#b分钟奖励#l#k\r\n";
         }
 
         selStr += "在线 #r" + 在线奖励4时间 + " #k分钟可领取 #v" + 在线奖励4代码 + "##z" + 在线奖励4代码 + "# × " + 在线奖励4数量 + " 抵用券 × 800\r\n";
         if (cm.getTodayOnlineTime() >= 在线奖励4时间 && cm.getBossLogD("在线奖励4时间") == 0) {
             selStr += "#L4#" + 箭头 + "#b领取#r" + 在线奖励4时间 + "#k#b分钟奖励#l#k\r\n";
         }
 
         selStr += "在线 #r" + 在线奖励5时间 + " #k分钟可领取 #v" + 在线奖励5代码 + "##z" + 在线奖励5代码 + "#  x  " + 在线奖励5数量 + " 抵用券 × 1600\r\n";
         if (cm.getTodayOnlineTime() >= 在线奖励5时间 && cm.getBossLogD("在线奖励5时间") == 0) {
             selStr += "#L5#" + 箭头 + "#b领取#r" + 在线奖励5时间 + "#k#b分钟奖励#l#k\r\n";
         }
 
         selStr += "在线 #r" + 在线奖励6时间 + " #k分钟可领取 #v" + 在线奖励6代码 + "##z" + 在线奖励6代码 + "#  x  " + 在线奖励6数量 +"#v2030021"+ "##z2030021" + "#  x  " + "2" +"#v4310019"+ "##z4310019" + "#  x  " + "1" + " 抵用券 × 3200\r\n";
         if (cm.getTodayOnlineTime() >= 在线奖励6时间 && cm.getBossLogD("在线奖励6时间") == 0) {
             selStr += "#L6#" + 箭头 + "#b领取#r" + 在线奖励6时间 + "#k#b分钟奖励#l#k\r\n";
         }
 
 
 
         cm.sendOk(selStr);
     } else if (status == 1) {
         switch (selection) {
             case 1:
                 cm.gainItem(在线奖励1代码, 在线奖励1数量);
                 cm.gainNX2(100);
                 cm.setBossLog("在线奖励1时间");
                 cm.worldMessage(6, "[在线奖励] : " + cm.getChar().getName() + " 领取了 " + 在线奖励1时间 + " 分钟在线奖励");
                 cm.dispose();
                 break;
             case 2:
                 cm.gainItem(在线奖励2代码, 在线奖励2数量);
                 cm.gainNX2(200);
                 cm.setBossLog("在线奖励2时间");
                 cm.worldMessage(6, "[在线奖励] : " + cm.getChar().getName() + " 领取了 " + 在线奖励2时间 + " 分钟在线奖励");
                 cm.dispose();
                 break;
             case 3:
                 cm.gainItem(在线奖励3代码, 在线奖励3数量);
                 cm.gainNX2(400);
                 cm.setBossLog("在线奖励3时间");
                 cm.worldMessage(6, "[在线奖励] : " + cm.getChar().getName() + " 领取了 " + 在线奖励3时间 + " 分钟在线奖励");
                 cm.dispose();
                 break;
             case 4:
                 cm.gainItem(在线奖励4代码, 在线奖励4数量);
                 cm.gainNX2(800);
                 cm.setBossLog("在线奖励4时间");
                 cm.worldMessage(6, "[在线奖励] : " + cm.getChar().getName() + " 领取了 " + 在线奖励4时间 + " 分钟在线奖励");
                 cm.dispose();
                 break;
             case 5:
                 cm.gainItem(在线奖励5代码, 在线奖励5数量);
                 cm.gainNX2(1600);
                 cm.setBossLog("在线奖励5时间");
                 cm.worldMessage(6, "[在线奖励] : " + cm.getChar().getName() + " 领取了 " + 在线奖励5时间 + " 分钟在线奖励");
                 cm.dispose();
                 break;
             case 6:
                 cm.gainItem(在线奖励6代码, 在线奖励6数量);
                 cm.gainItem(2030021, 2);//增加狮子王门票
                 cm.gainItem(4310019, 2);//全能抽奖币
                 cm.gainNX2(3200);
                 cm.setBossLog("在线奖励6时间");
                 cm.worldMessage(6, "[在线奖励] : " + cm.getChar().getName() + " 领取了 " + 在线奖励6时间 + " 分钟在线奖励");
                 cm.dispose();
                 break;
 
         }
     }
 }