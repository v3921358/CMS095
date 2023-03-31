/*
 ZEVMS冒险岛(079)游戏服务端
 脚本：清理背包
 */
 var 图标1 = "#fUI/UIWindow.img/FadeYesNo/icon7#";
 var 图标2 = "#fEffect/CharacterEff/1112925/0/1#";
 var 关闭 = "#fUI/UIWindow.img/CashGachapon/BtOpen/mouseOver/0#";
 var 打开 = "#fUI/UIWindow.img/CashGachapon/BtOpen/disabled/0#";
 var JD = "#fUI/Basic/BtHide3/mouseOver/0#";

 var 装备2 = "#fUI/CashShop.img/Base/Tab2/Enable/0#";
 var 消耗2 = "#fUI/CashShop.img/Base/Tab2/Enable/1#";  
 var 设置2 = "#fUI/CashShop.img/Base/Tab2/Enable/2#"; 
 var 其他2 = "#fUI/CashShop.img/Base/Tab2/Enable/3#";   
 var 特殊2 = "#fUI/CashShop.img/Base/Tab2/Enable/4#"; 
 var 心 = "#fEffect/CharacterEff/1032063/0/0#"; //长音符
 var a = "#fEffect/CharacterEff.img/1112926/0/1#";
 function start() {
     status = -1;
     action(1, 0, 0)
 }
 
 function action(mode, type, selection) {
     if (status <= 0 && mode <= 0) {
         cm.dispose();
		 cm.openNpc(9900004);
         return
     }
     if (mode == 1) {
         status++
     } else {
         status--
     }	 
     if (status <= 0) {
         var selStr = "\t\t" + 心 + "#r#e < 每日任务 > #k#n" + 心 + "\r\n\r\n\r\n";
         

         selStr += "\t#L0#"+图标2+"#d签到任务"+图标2+"#l\t#L1#"+图标2+"#d在线任务"+图标2+"#l\r\n\r\n";	
         selStr += "\t#L2#"+图标2+"#d跑环任务"+图标2+"#l\t#L3#"+图标2+"#d通缉任务"+图标2+"#l\r\n\r\n";
         selStr += "\t#L4#"+图标2+"#d副本任务"+图标2+"#l\t#L5#"+图标2+"#d家族任务"+图标2+"#l\r\n\r\n";
         selStr += "\t#L6#"+图标2+"#d每日金币"+图标2+"#l\t#L7#"+图标2+"#d每日答题"+图标2+"#l\r\n\r\n";
         
 
         cm.sendSimple(selStr)
     } else if (status == 1) {
         switch (selection) {
             case 0:
                 cm.dispose();
                 cm.openNpc(9900004,"每日签到");
                 break;
             case 1:
                 cm.dispose();
                 cm.openNpc(9900004,"每日在线");
                 break;
             case 2:
                 cm.dispose();
                 cm.openNpc(9900004,"每日跑环");
                 break;
             case 3:
                 cm.dispose();
                 cm.openNpc(9900004,"每日通缉");
                 break;
                 case 4:
                 cm.dispose();
                 cm.openNpc(9900004,"每日副本");
                 break;
             case 5:
                 cm.dispose();
                 cm.openNpc(9900004,"每日家族");
                 break;
             case 6:
                cm.dispose();
                 cm.openNpc(9900004,"每日金币");
                  break;
                  case 7:
                  cm.dispose();
                  cm.openNpc(9900004,"每日答题");
                break;
         }
     }
 }