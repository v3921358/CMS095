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
      
              var selStr = "\t\t\t#r#e < 清除所有Boss领取奖励 > #k#n\r\n\r\n\r\n\r\n\r\n\r\n";
                  selStr += "#e#L2#" + kx + "放弃挑战 #l #k\r\n\r\n";
			  
                  if (cm.getPlayer().getMap().mobCount() == 0) {
		  
		             selStr += "#e#L1#" + kx + "领取奖励 #l #k\r\n\r\n";

		
		  
		  
 }
 
         cm.sendSimple(selStr)
		 
     } else if (status == 1) {
         switch (selection) {
             case 1:
                cm.gainItem(4001231, 6);
				cm.warp(555000000, 0);
				cm.setBossRankCount9("橙金点", 10);
				cm.playerMessage(6, "恭喜你完成Boss关卡，获得10橙金点！总共" + cm.getBossRank9("橙金点", 2) + "点");
		
				cm.dispose();
                 break;
           case 2:
		         cm.warp(555000000, 0);
                 cm.dispose();
              
                 break;
         }
     }
 }