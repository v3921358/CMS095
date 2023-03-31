/*
 ZEVMS冒险岛(079)游戏服务端
 脚本：清理背包
 */
 var 图标2 = "#v2022075#";
 var 图标1 = "#v2022035#";
 var 关闭 = "#fUI/UIWindow.img/CashGachapon/BtOpen/mouseOver/0#";
 var 打开 = "#fUI/UIWindow.img/CashGachapon/BtOpen/disabled/0#";
 var JD = "#fUI/Basic/BtHide3/mouseOver/0#";
 var 心 = "#v4032470#";
 var 装备2 = "#fUI/CashShop.img/Base/Tab2/Enable/0#";
 var 消耗2 = "#fUI/CashShop.img/Base/Tab2/Enable/1#";  
 var 设置2 = "#fUI/CashShop.img/Base/Tab2/Enable/2#"; 
 var 其他2 = "#fUI/CashShop.img/Base/Tab2/Enable/3#";   
 var 特殊2 = "#fUI/CashShop.img/Base/Tab2/Enable/4#"; 
 var a = "#fEffect/CharacterEff.img/1112926/0/1#";
 function start() {
     status = -1;
     action(1, 0, 0)
 }
 
 function action(mode, type, selection) {
     if (status <= 0 && mode <= 0) {
		     
         cm.dispose();
         return
     }
     if (mode == 1) {
         status++
     } else {
         status--
     }	 
     if (status <= 0) {
         var selStr = "\t" + 心 + "  " + 心 + " #r#e < 请选择投币方式 > #k#n " + 心 + "  " + 心 + "\r\n\r\n";
         
      
         selStr += "\t#L0#"+图标2+"#d投入金币"+图标2+"#l\t#L1#"+图标2+"#d投抵用卷"+图标2+"#l\r\n\r\n";	
		  selStr += "\t#L2#"+图标2+"#d投入点卷"+图标2+"#l\t#L3#"+图标2+"#d投入余额"+图标2+"#l\r\n\r\n\r\n\r\n";
		  
		  
		  
		  
		  
       selStr += ""+图标1+""+图标1+""+图标1+""+图标1+""+图标1+""+图标1+""+图标1+""+图标1+""+图标1+""+图标1+""+图标1+""+图标1+"\r\n\r\n";
        //  selStr += "\t#L6#"+图标2+"#d普通BOSS"+图标2+"#l\t#L7#"+图标2+"#d高级BOSS"+图标2+"#l\r\n\r\n";
         
 
         cm.sendSimple(selStr)
     } else if (status == 1) {
         switch (selection) {
             case 0:
                 cm.dispose();
                 cm.openNpc(1052014,"售货机金币");
                 break;
             case 1:
                 cm.dispose();
                 cm.openNpc(1052014,"售货机抵用卷");
                 break;
             case 2:
                 cm.dispose();
                 cm.openNpc(1052014,"售货机点卷");
                 break;
             case 3:
                 cm.dispose();
                 cm.openNpc(1052014,"售货机余额");
                 break;
                 case 4:
                 cm.dispose();
                 cm.openNpc(9310072,"每日副本");
                 break;
             case 5:
                 cm.dispose();
                 cm.openNpc(9310072,"每日家族");
                 break;
        //          case 6:
        //          cm.dispose();
        //          cm.openNpc(9310072,"普通BOSS");
        //          break;
        //          case 7:
        //          cm.dispose();
        //          cm.openNpc(9310072,"高级BOSS");
        //          break;
         }
     }
 }