/*
 ZEVMS冒险岛(079)游戏服务端
 脚本：清理背包
 */
 var 图标1 = "#fUI/UIWindow.img/FadeYesNo/icon7#";
 var 图标2 = "#fUI/Basic/BtHide3/mouseOver/0#";
 var 关闭 = "#fUI/UIWindow.img/CashGachapon/BtOpen/mouseOver/0#";
 var 打开 = "#fUI/UIWindow.img/CashGachapon/BtOpen/disabled/0#";
 var JD = "#fUI/Basic/BtHide3/mouseOver/0#";
 var 心 = "#fUI/GuildMark.img/Mark/Etc/00009001/14#";
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
		      cm.sendOk("感谢支持#reV.095冒险岛#k我们有独立开发的技术以及最前线的保障。谢谢各位支持");
         cm.dispose();
         return
     }
     if (mode == 1) {
         status++
     } else {
         status--
     }	 
     if (status <= 0) {
         var selStr = "\t\t\t" + 心 + "  " + 心 + " #r#e < 装备升级 > #k#n " + 心 + "  " + 心 + "\r\n\r\n";
         
      
         selStr += "#L0#"+图标2+"#d初出茅庐"+图标2+"#l  #L1#"+图标2+"#d小试牛刀"+图标2+"#l #L2#"+图标2+"#d毕业装备"+图标2+"#l\r\n\r\n";	
		  
		  
		  
		 // selStr += "#L2#"+图标2+"#d清理背包"+图标2+"#l   #L3#"+图标2+"#d仓库管理"+图标2+"#l\r\n\r\n";
		  
		  
		  
		  
		  
      //   selStr += "\t#L4#"+图标2+"#d副本任务"+图标2+"#l\t#L5#"+图标2+"#d家族任务"+图标2+"#l\r\n\r\n";
        //  selStr += "\t#L6#"+图标2+"#d普通BOSS"+图标2+"#l\t#L7#"+图标2+"#d高级BOSS"+图标2+"#l\r\n\r\n";
         
 
         cm.sendSimple(selStr)
     } else if (status == 1) {
         switch (selection) {
             case 0:
                 cm.dispose();
                 cm.openNpc(9310072,"初出茅庐");
                 break;
             case 1:
                 cm.dispose();
                 cm.openNpc(9310072,"小试牛刀");
                 break;
             case 2:
                 cm.dispose();
                 cm.openNpc(9310072,"毕业装备1");
                 break;
             case 3:
                 cm.dispose();
                 cm.openNpc(9310072,"仓库管理");
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