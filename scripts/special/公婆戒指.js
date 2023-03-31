load('nashorn:mozilla_compat.js');
importPackage(Packages.client);

var status = 0;
var 抽奖币 = 4310027;//抽奖币
var 快乐百宝券 = 4110000;//快乐百宝券
var 升级消耗物品 = 4001465;//老公老婆币
var 升级所需物品 = 1112446;//老公老婆戒指1级
var 给予戒指ID = 1112446;//老婆老公戒指1级
var 锁 = 1;
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
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 0) {
            var text = "";
			text += "#rPs.升级公婆戒指需要#i4001465#*20#k\r\n\r\n";
            text += "#L1#升级老公老婆戒指：#r1→2#k#l\r\n\r\n";
            text += "#L2#升级老公老婆戒指：#r2→3#k#l\r\n\r\n";
            text += "#L3#升级老公老婆戒指：#r3→4#k#l\r\n\r\n";
            text += "#L4#升级老公老婆戒指：#r4→5#k#l\r\n\r\n";
            text += "#L5#升级老公老婆戒指：#r5→6#k#l\r\n\r\n";
            text += "#L6#升级老公老婆戒指：#r6→7#k#l\r\n\r\n";
            text += "#L7#升级老公老婆戒指：#r7→8#k#l\r\n\r\n";
            text += "#L8#升级老公老婆戒指：#r8→9#k#l\r\n\r\n";
            text += "#L9#升级老公老婆戒指：#r9→10#k#l\r\n\r\n";
            text += "#L10#升级老公老婆戒指：#r10→11#k#l\r\n\r\n";
            text += "#L11#升级老公老婆戒指：#r11→12#k#l\r\n\r\n";
            text += "#L12#升级老公老婆戒指：#r12→13#k#l\r\n\r\n";
            text += "#L13#升级老公老婆戒指：#r13→14#k#l\r\n\r\n";
            text += "#L14#升级老公老婆戒指：#r14→15#k#l\r\n\r\n";
            text += "#L15#升级老公老婆戒指：#r15→16#k#l\r\n\r\n";
            text += "#L16#升级老公老婆戒指：#r16→17#k#l\r\n\r\n";
            text += "#L17#升级老公老婆戒指：#r17→18#k#l\r\n\r\n";
            text += "#L18#升级老公老婆戒指：#r18→19#k#l\r\n\r\n";
            text += "#L19#升级老公老婆戒指：#r19→20#k#l\r\n\r\n";
            text += "#L20#升级老公老婆戒指：#r20→21#k#l\r\n\r\n";
            text += "#L21#升级老公老婆戒指：#r21→22#k#l\r\n\r\n";
            text += "#L22#升级老公老婆戒指：#r22→23#k#l\r\n\r\n";
            text += "#L23#升级老公老婆戒指：#r23→24#k#l\r\n\r\n";
            text += "#L24#升级老公老婆戒指：#r24→25#k#l\r\n\r\n";
            text += "#L25#升级老公老婆戒指：#r25→26#k#l\r\n\r\n";
            text += "#L26#升级老公老婆戒指：#r26→27#k#l\r\n\r\n";
            text += "#L27#升级老公老婆戒指：#r27→28#k#l\r\n\r\n";
            text += "#L28#升级老公老婆戒指：#r28→29#k#l\r\n\r\n";
            text += "#L29#升级老公老婆戒指：#r29→30#k#l\r\n\r\n";
            text += "#L30#升级老公老婆戒指：#r30→31#k#l\r\n\r\n";
            text += "#L31#升级老公老婆戒指：#r31→32#k#l\r\n\r\n";
            text += "#L32#升级老公老婆戒指：#r32→33#k#l\r\n\r\n";
            text += "#L33#升级老公老婆戒指：#r33→34#k#l\r\n\r\n";
            text += "#L34#升级老公老婆戒指：#r34→35#k#l\r\n\r\n";
            text += "#L35#升级老公老婆戒指：#r35→36#k#l\r\n\r\n";
            text += "#L36#升级老公老婆戒指：#r36→37#k#l\r\n\r\n";
            text += "#L37#升级老公老婆戒指：#r37→38#k#l\r\n\r\n";
            text += "#L38#升级老公老婆戒指：#r38→39#k#l\r\n\r\n";
            text += "#L39#升级老公老婆戒指：#r39→40#k#l\r\n\r\n";
            text += "#L40#升级老公老婆戒指：#r40→41#k#l\r\n\r\n";
            text += "#L41#升级老公老婆戒指：#r41→42#k#l\r\n\r\n";
            text += "#L42#升级老公老婆戒指：#r42→43#k#l\r\n\r\n";
            text += "#L43#升级老公老婆戒指：#r43→44#k#l\r\n\r\n";
            text += "#L44#升级老公老婆戒指：#r44→45#k#l\r\n\r\n";
            text += "#L45#升级老公老婆戒指：#r45→46#k#l\r\n\r\n";
            text += "#L46#升级老公老婆戒指：#r46→47#k#l\r\n\r\n";
            text += "#L47#升级老公老婆戒指：#r47→48#k#l\r\n\r\n";
            text += "#L48#升级老公老婆戒指：#r48→49#k#l\r\n\r\n";
            text += "#L49#升级老公老婆戒指：#r49→50#k#l\r\n\r\n";
            cm.sendSimple(text);
        } else if (status == 1) {
			var 给予戒指ID = 1112446 + selection;
            if (selection == 1) {
                if (cm.haveItem(升级所需物品, 1) && cm.haveItem(升级消耗物品, 20)) {
                        cm.gainItem(升级所需物品, -1);
                        cm.gainItem(升级消耗物品, -20);
                        cm.gainItem(给予戒指ID,1);
                        cm.worldMessage(5, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.2，大家恭喜他（她）吧！！！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 2) {
                if (cm.haveItem(升级所需物品 + 1, 1) && cm.haveItem(升级消耗物品, 20)) {
                        cm.gainItem(升级所需物品 + 1, -1);
                        cm.gainItem(升级消耗物品, -20);
                        cm.gainItem(给予戒指ID,1);
                        cm.worldMessage(5, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.3，大家恭喜他（她）吧！！！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 3) {
                if (cm.haveItem(升级所需物品 + 2, 1) && cm.haveItem(升级消耗物品, 20)) {
                    
                        cm.gainItem(升级所需物品 + 2, -1);
                        cm.gainItem(升级消耗物品, -20);
                        cm.gainItem(给予戒指ID,1);
                        cm.worldMessage(5, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.4，大家恭喜他（她）吧！！！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 4) {
                if (cm.haveItem(升级所需物品 + 3, 1) && cm.haveItem(升级消耗物品, 20)) {
                    
                        cm.gainItem(升级所需物品 + 3, -1);
                        cm.gainItem(升级消耗物品, -20);
                        cm.gainItem(给予戒指ID,1);
                        cm.worldMessage(5, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.5，大家恭喜他（她）吧！！！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");

                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 5) {
                if (cm.haveItem(升级所需物品 + 4, 1) && cm.haveItem(升级消耗物品, 20)) {
                    
                        cm.gainItem(升级所需物品 + 4, -1);
                        cm.gainItem(升级消耗物品, -20);
                        cm.gainItem(给予戒指ID,1);
                        cm.worldMessage(5, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.6，大家恭喜他（她）吧！！！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");

                   
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }

            } else if (selection == 6) {
                if (cm.haveItem(升级所需物品 + 5, 1) && cm.haveItem(升级消耗物品, 20)) {
                    
                        cm.gainItem(升级所需物品 + 5, -1);
                        cm.gainItem(升级消耗物品, -20);
                        cm.gainItem(给予戒指ID,1);
                        cm.worldMessage(5, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.7，大家恭喜他（她）吧！！！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");

                   
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 7) {
                if (cm.haveItem(升级所需物品 + 6, 1) && cm.haveItem(升级消耗物品, 20)) {
                    
                        cm.gainItem(升级所需物品 + 6, -1);
                        cm.gainItem(升级消耗物品, -20);
                        cm.gainItem(给予戒指ID,1);
                        cm.worldMessage(5, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.8，大家恭喜他（她）吧！！！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");

                    
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 8) {
                if (cm.haveItem(升级所需物品 + 7, 1) && cm.haveItem(升级消耗物品, 20)) {
                    
                        cm.gainItem(升级所需物品 + 7, -1);
                        cm.gainItem(升级消耗物品, -20);
                        cm.gainItem(给予戒指ID,1);
                        cm.worldMessage(5, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.9，大家恭喜他（她）吧！！！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");

                   
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 9) {
                if (cm.haveItem(升级所需物品 + 8, 1) && cm.haveItem(升级消耗物品, 20)) {
                    
                        cm.gainItem(升级所需物品 + 8, -1);
                        cm.gainItem(升级消耗物品, -20);
                        cm.gainItem(给予戒指ID,1);
                        cm.worldMessage(5, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.10，大家恭喜他（她）吧！！！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");

                  
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 10) {
                if (cm.haveItem(升级所需物品 + 9, 1) && cm.haveItem(升级消耗物品, 20)) {
                    
                        cm.gainItem(升级所需物品 + 9, -1);
                        cm.gainItem(升级消耗物品, -20);
                        cm.gainItem(给予戒指ID,1);
                        cm.worldMessage(5, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.11，大家恭喜他（她）吧！！！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");

                   
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 11) {
                if (cm.haveItem(升级所需物品 + 10, 1) && cm.haveItem(升级消耗物品, 20)) {
                    
                        cm.gainItem(升级所需物品 + 10, -1);
                        cm.gainItem(升级消耗物品, -20);
                        cm.gainItem(给予戒指ID,1);
                        cm.worldMessage(5, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.12，大家恭喜他（她）吧！！！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");

                   
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 12) {
                if (cm.haveItem(升级所需物品 + 11, 1) && cm.haveItem(升级消耗物品, 20)) {
                    
                        cm.gainItem(升级所需物品 + 11, -1);
                        cm.gainItem(升级消耗物品, -20);
                        cm.gainItem(给予戒指ID,1);
                        cm.worldMessage(5, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.13，大家恭喜他（她）吧！！！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");

                  
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 13) {
                if (cm.haveItem(升级所需物品 + 12, 1) && cm.haveItem(升级消耗物品, 20)) {
                    
                        cm.gainItem(升级所需物品 + 12, -1);
                        cm.gainItem(升级消耗物品, -20);
                        cm.gainItem(给予戒指ID,1);
                        cm.worldMessage(5, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.14，大家恭喜他（她）吧！！！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");

                  
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 14) {
                if (cm.haveItem(升级所需物品 + 13, 1) && cm.haveItem(升级消耗物品, 20)) {
                    
                        cm.gainItem(升级所需物品 + 13, -1);
                        cm.gainItem(升级消耗物品, -20);
                        cm.gainItem(给予戒指ID,1);
                        cm.worldMessage(5, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.15，大家恭喜他（她）吧！！！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");

                   
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 15) {
                if (cm.haveItem(升级所需物品 + 14, 1) && cm.haveItem(升级消耗物品, 20)) {
                    
                        cm.gainItem(升级所需物品 + 14, -1);
                        cm.gainItem(升级消耗物品, -20);
                        cm.gainItem(给予戒指ID,1);
                        cm.worldMessage(5, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.16，大家恭喜他（她）吧！！！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");

                  
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 16) {
                if (cm.haveItem(升级所需物品 + 15, 1) && cm.haveItem(升级消耗物品, 20)) {
                    
                        cm.gainItem(升级所需物品 + 15, -1);
                        cm.gainItem(升级消耗物品, -20);
                        cm.gainItem(给予戒指ID,1);
                        cm.worldMessage(5, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.17，大家恭喜他（她）吧！！！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");

                  
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }

            } else if (selection == 17) {
                if (cm.haveItem(升级所需物品 + 16, 1) && cm.haveItem(升级消耗物品, 20)) {
        
                        cm.gainItem(升级所需物品 + 16, -1);
                        cm.gainItem(升级消耗物品, -20);
                        cm.gainItem(给予戒指ID,1);
                        cm.worldMessage(5, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.18，大家恭喜他（她）吧！！！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");

                   
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }

            } else if (selection == 18) {
                if (cm.haveItem(升级所需物品 + 17, 1) && cm.haveItem(升级消耗物品, 20)) {
   
                        cm.gainItem(升级所需物品 + 17, -1);
                        cm.gainItem(升级消耗物品, -20);
                        cm.gainItem(给予戒指ID,1);
                        cm.worldMessage(5, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.19，大家恭喜他（她）吧！！！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");

                   
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 19) {
                if (cm.haveItem(升级所需物品 + 18, 1) && cm.haveItem(升级消耗物品, 20)) {
   
                        cm.gainItem(升级所需物品 + 18, -1);
                        cm.gainItem(升级消耗物品, -20);
                        cm.gainItem(给予戒指ID,1);
                        cm.worldMessage(5, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.20，大家恭喜他（她）吧！！！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");

                   
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }

            } else if (selection == 20) {
                if (cm.haveItem(升级所需物品 + 19, 1) && cm.haveItem(升级消耗物品, 20)) {
            
                        cm.gainItem(升级所需物品 + 19, -1);
                        cm.gainItem(升级消耗物品, -20);
                        cm.gainItem(给予戒指ID,1);
                        cm.worldMessage(5, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.21，大家恭喜他（她）吧！！！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");

                   
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 21) {
                if (cm.haveItem(升级所需物品 + 20, 1) && cm.haveItem(升级消耗物品, 20)) {
                    
                        cm.gainItem(升级所需物品 + 20, -1);
                        cm.gainItem(升级消耗物品, -20);
                        cm.gainItem(给予戒指ID,1);
                        cm.worldMessage(5, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.22，大家恭喜他（她）吧！！！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");

                   
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 22) {
                if (cm.haveItem(升级所需物品 + 21, 1) && cm.haveItem(升级消耗物品, 20)) {
                    
                        cm.gainItem(升级所需物品 + 21, -1);
                        cm.gainItem(升级消耗物品, -20);
                        cm.gainItem(给予戒指ID,1);
                        cm.worldMessage(5, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.23，大家恭喜他（她）吧！！！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");

                  
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 23) {
                if (cm.haveItem(升级所需物品 + 22, 1) && cm.haveItem(升级消耗物品, 20)) {
                    
                        cm.gainItem(升级所需物品 + 22, -1);
                        cm.gainItem(升级消耗物品, -20);
                        cm.gainItem(给予戒指ID,1);
                        cm.worldMessage(5, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.24，大家恭喜他（她）吧！！！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");

                  
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 24) {
                if (cm.haveItem(升级所需物品 + 23, 1) && cm.haveItem(升级消耗物品, 20)) {
                    
                        cm.gainItem(升级所需物品 + 23, -1);
                        cm.gainItem(升级消耗物品, -20);
                        cm.gainItem(给予戒指ID,1);
                        cm.worldMessage(5, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.25，我已经注意到你了！！！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");

                    
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 25) {
                if (cm.haveItem(升级所需物品 + 24, 1) && cm.haveItem(升级消耗物品, 20)) {
       
                        cm.gainItem(升级所需物品 + 24, -1);
                        cm.gainItem(升级消耗物品, -20);
                        cm.gainItem(给予戒指ID,1);
                        cm.worldMessage(5, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.26，接下来，我得给你增加难度了！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");

                   
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 26) {
                if (cm.haveItem(升级所需物品 + 25, 1) && cm.haveItem(升级消耗物品, 20)) {
       
                        cm.gainItem(升级所需物品 + 25, -1);
                        cm.gainItem(升级消耗物品, -20);
                        cm.gainItem(给予戒指ID,1);
                        cm.worldMessage(5, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.27，非洲农奴把歌唱！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");

                    
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 27) {
                if (cm.haveItem(升级所需物品 + 26, 1) && cm.haveItem(升级消耗物品, 20)) {
                    
                        cm.gainItem(升级所需物品 + 26, -1);
                        cm.gainItem(升级消耗物品, -20);
                        cm.gainItem(给予戒指ID,1);
                        cm.worldMessage(5, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.28，非洲农奴把歌唱！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");

                   
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 28) {
                if (cm.haveItem(升级所需物品 + 27, 1) && cm.haveItem(升级消耗物品, 20)) {
                    
                        cm.gainItem(升级所需物品 + 27, -1);
                        cm.gainItem(升级消耗物品, -20);
                        cm.gainItem(给予戒指ID,1);
                        cm.worldMessage(5, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.29，非洲农奴把歌唱！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");

                    
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 29) {
                if (cm.haveItem(升级所需物品 + 28, 1) && cm.haveItem(升级消耗物品, 20)) {
                    
                        cm.gainItem(升级所需物品 + 28, -1);
                        cm.gainItem(升级消耗物品, -20);
                        cm.gainItem(给予戒指ID,1);
                        cm.worldMessage(5, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.30，非洲农奴把歌唱！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");

                   
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }

            } else if (selection == 30) {
                if (cm.haveItem(升级所需物品 + 29, 1) && cm.haveItem(升级消耗物品, 20)) {
                    
                        cm.gainItem(升级所需物品 + 29, -1);
                        cm.gainItem(升级消耗物品, -20);
                        cm.gainItem(给予戒指ID,1);
                        cm.worldMessage(5, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.31，非洲农奴把歌唱！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");

                  
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 31) {
                if (cm.haveItem(升级所需物品 + 30, 1) && cm.haveItem(升级消耗物品, 20)) {
                    
                        cm.gainItem(升级所需物品 + 30, -1);
                        cm.gainItem(升级消耗物品, -20);
                        cm.gainItem(给予戒指ID,1);
                        cm.worldMessage(5, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.32，非洲农奴把歌唱！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");

                  
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 32) {
                if (cm.haveItem(升级所需物品 + 31, 1) && cm.haveItem(升级消耗物品, 20)) {
                    
                        cm.gainItem(升级所需物品 + 31, -1);
                        cm.gainItem(升级消耗物品, -20);
                        cm.gainItem(给予戒指ID,1);
                        cm.worldMessage(5, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.33，非洲农奴把歌唱！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");

                  
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 33) {
                if (cm.haveItem(升级所需物品 + 32, 1) && cm.haveItem(升级消耗物品, 20)) {
         
                        cm.gainItem(升级所需物品 + 32, -1);
                        cm.gainItem(升级消耗物品, -20);
                        cm.gainItem(给予戒指ID,1);
                        cm.worldMessage(5, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.34，非洲农奴把歌唱！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");

                   
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 34) {
                if (cm.haveItem(升级所需物品 + 33, 1) && cm.haveItem(升级消耗物品, 20)) {
         
                        cm.gainItem(升级所需物品 + 33, -1);
                        cm.gainItem(升级消耗物品, -20);
                        cm.gainItem(给予戒指ID,1);
                        cm.worldMessage(5, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.35，非洲农奴把歌唱！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");

                  
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 35) {
                if (cm.haveItem(升级所需物品 + 34, 1) && cm.haveItem(升级消耗物品, 20)) {
        
                        cm.gainItem(升级所需物品 + 34, -1);
                        cm.gainItem(升级消耗物品, -20);
                        cm.gainItem(给予戒指ID,1);
                        cm.worldMessage(5, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.36，非洲农奴把歌唱！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");

                   
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 36) {
                if (cm.haveItem(升级所需物品 + 35, 1) && cm.haveItem(升级消耗物品, 20)) {

                        cm.gainItem(升级所需物品 + 35, -1);
                        cm.gainItem(升级消耗物品, -20);
                        cm.gainItem(给予戒指ID,1);
                        cm.worldMessage(5, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.37，大家恭喜他（她）吧！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");

                 
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 37) {
                if (cm.haveItem(升级所需物品 + 36, 1) && cm.haveItem(升级消耗物品, 20)) {
                    
                        cm.gainItem(升级所需物品 + 36, -1);
                        cm.gainItem(升级消耗物品, -20);
                        cm.gainItem(给予戒指ID,1);
                        cm.worldMessage(5, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.38，大家恭喜他（她）吧！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");

                  
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 38) {
                if (cm.haveItem(升级所需物品 + 37, 1) && cm.haveItem(升级消耗物品, 20)) {
                        cm.gainItem(升级所需物品 + 37, -1);
                        cm.gainItem(升级消耗物品, -20);
                        cm.gainItem(给予戒指ID,1);
                        cm.worldMessage(5, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.39，大家恭喜他（她）吧！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");

                   
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 39) {
                if (cm.haveItem(升级所需物品 + 38, 1) && cm.haveItem(升级消耗物品, 20)) {
              
                        cm.gainItem(升级所需物品 + 38, -1);
                        cm.gainItem(升级消耗物品, -20);
                        cm.gainItem(给予戒指ID,1);
                        cm.worldMessage(5, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.40，这是欧洲人喜悦的泪水！！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");

                   
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 40) {
                if (cm.haveItem(升级所需物品 + 39, 1) && cm.haveItem(升级消耗物品, 20)) {
     
                        cm.gainItem(升级所需物品 + 39, -1);
                        cm.gainItem(升级消耗物品, -20);
                        cm.gainItem(给予戒指ID,1);
                        cm.worldMessage(5, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.41，这是欧洲人喜悦的泪水！！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");

                 
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 41) {
                if (cm.haveItem(升级所需物品 + 40, 1) && cm.haveItem(升级消耗物品, 20)) {

                        cm.gainItem(升级所需物品 + 40, -1);
                        cm.gainItem(升级消耗物品, -20);
                        cm.gainItem(给予戒指ID,1);
                        cm.worldMessage(5, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.42，这是欧洲人喜悦的泪水！！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");

                  
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 42) {
                if (cm.haveItem(升级所需物品 + 41, 1) && cm.haveItem(升级消耗物品, 20)) {
                    
                        cm.gainItem(升级所需物品 + 41, -1);
                        cm.gainItem(升级消耗物品, -20);
                        cm.gainItem(给予戒指ID,1);
                        cm.worldMessage(5, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.43，这是欧洲人喜悦的泪水！！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");

                   
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 43) {
                if (cm.haveItem(升级所需物品 + 42, 1) && cm.haveItem(升级消耗物品, 20)) {
              
                        cm.gainItem(升级所需物品 + 42, -1);
                        cm.gainItem(升级消耗物品, -20);
                        cm.gainItem(给予戒指ID,1);
                        cm.worldMessage(5, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.44，这是欧洲人喜悦的泪水！！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");

                   
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 44) {
                if (cm.haveItem(升级所需物品 + 43, 1) && cm.haveItem(升级消耗物品, 20)) {
       
                        cm.gainItem(升级所需物品 + 43, -1);
                        cm.gainItem(升级消耗物品, -20);
                        cm.gainItem(给予戒指ID,1);
                        cm.worldMessage(5, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.45，欧洲人！欧洲人！这是来自北欧的纯种欧洲人啊！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");

                   
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 45) {
                if (cm.haveItem(升级所需物品 + 44, 1) && cm.haveItem(升级消耗物品, 20)) {
     
                        cm.gainItem(升级所需物品 + 44, -1);
                        cm.gainItem(升级消耗物品, -20);
                        cm.gainItem(给予戒指ID,1);
                        cm.worldMessage(5, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.46，难道你就是传说中的欧皇？");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");

                  
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }

            } else if (selection == 46) {
                if (cm.haveItem(升级所需物品 + 45, 1) && cm.haveItem(升级消耗物品, 20)) {
     
                        cm.gainItem(升级所需物品 + 45, -1);
                        cm.gainItem(升级消耗物品, -20);
                        cm.gainItem(给予戒指ID,1);
                        cm.worldMessage(5, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.47，男默女泪、感情至深动人肺腑！！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");

                 
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 47) {
                if (cm.haveItem(升级所需物品 + 46, 1) && cm.haveItem(升级消耗物品, 20)) {
 
                        cm.gainItem(升级所需物品 + 46, -1);
                        cm.gainItem(升级消耗物品, -20);
                        cm.gainItem(给予戒指ID,1);
                        cm.worldMessage(5, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.48，G-M不想说话，并对你放了一个屁！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");

                   
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 48) {
                if (cm.haveItem(升级所需物品 + 47, 1) && cm.haveItem(升级消耗物品, 20)) {

                        cm.gainItem(升级所需物品 + 47, -1);
                        cm.gainItem(升级消耗物品, -20);
                        cm.gainItem(给予戒指ID,1);
                        cm.worldMessage(5, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.49，起立！为大佬鼓掌！！");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");

                 
                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            } else if (selection == 49) {
                if (cm.haveItem(升级所需物品 + 48, 1) && cm.haveItem(升级消耗物品, 20)) {
						cm.gainItem(升级所需物品 + 48, -1);
                        cm.gainItem(升级消耗物品, -20);
                        cm.gainItem(给予戒指ID,1);
                        cm.worldMessage(5, "恭喜[" + cm.getPlayer().getName() + "]成功将[老公老婆戒指]提升到LV.50，下一件该升级什么装备呢？");
                        cm.sendOk("恭喜你！成功将戒指升级咯~");

                } else {
                    cm.sendOk("材料不足。无法合成！");
                    cm.dispose();
                }
            }
        } else if (status == 2){
			cm.dispose();
			cm.openNpc(9330111,"公婆戒指");
	}
    }
}


