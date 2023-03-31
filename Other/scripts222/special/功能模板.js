var kx = "#fEffect/CharacterEff/1112925/0/1#"; //空星
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
      
              var selStr = "\t\t\t\t#r#e < 超会兑换中心 > #k#n\r\n\r\n\r\n\r\n\r\n\r\n";
                    selStr += "#e#L1#" + kx + "毕业战士" + kx + "#l #L2#" + kx + "毕业法师" + kx + "#l#e#L3#" + kx + "毕业弓手" + kx + "#l#k\r\n\r\n";
					selStr += "#e#L4#" + kx + "毕业飞侠" + kx + "#l #L5#" + kx + "毕业海盗" + kx + "#l#e#L6#" + kx + "毕业饰品" + kx + "#l#k\r\n\r\n";

		  
		  
		  
		  
		  

 
         cm.sendSimple(selStr)
     } else if (status == 1) {
         switch (selection) {
             case 1:
                 cm.dispose();
                 cm.openNpc(9310072,"怪物爆率");
                 break;
             case 2:
                 cm.dispose();
                 cm.openNpc(9310072,"掉落查询");
                 break;
             case 3:
                 cm.dispose();
                 cm.openNpc(9310072,"清理背包");
                 break;
             case 4:
                 cm.dispose();
                 cm.openNpc(9310072,"仓库管理");
                 break;
             case 5:
                 cm.dispose();
                 cm.openNpc(9310072,"每日副本");
                 break;
             case 6:
                 cm.dispose();
                 cm.openNpc(9310072,"每日家族");
                 break;

         }
     }
 }