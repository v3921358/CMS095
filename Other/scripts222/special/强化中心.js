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
      
              var selStr = "\t\t\t\t  #r#e < 强化中心 > #k#n\r\n\r\n\r\n\r\n\r\n\r\n";
			    selStr += "      #e#L1#" + kx + "装备强化" + kx + "#l      #L2#" + kx + "装备分解" + kx + "#l#k\r\n\r\n";
                selStr += "     #e#L3#" + kx + "时装强化" + kx + "#l      #L4#" + kx + "时装分解" + kx + "#l#k\r\n\r\n";
                selStr += "     #e#L5#" + kx + "快速潜能" + kx + "#l      #L6#" + kx + "强化次数" + kx + "#l#k\r\n\r\n";
		  
		  
		  
		  
		  

 
         cm.sendSimple(selStr)
     } else if (status == 1) {
         switch (selection) {
             case 1:
                 cm.dispose();
                 cm.openNpc(9310072,"装备强化");
                 break;
             case 2:
                 cm.dispose();
                 cm.openNpc(9310072,"装备分解");
                 break;
             case 3:
                 cm.dispose();
                 cm.openNpc(9310072,"时装强化");
                 break;
             case 4:
                 cm.dispose();
                 cm.openNpc(9310072,"时装分解");
                 break;
             case 5:
                 cm.dispose();
                 cm.openNpc(9310072,"快速洗潜");
                 break;
             case 6:
                 cm.dispose();
                 cm.openNpc(9310072,"强化次数");
                 break;

         }
     }
 }