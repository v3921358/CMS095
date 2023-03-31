/*
 ZEVMS冒险岛(079)游戏服务端
 脚本：游戏推广系统
 需要连接二级脚本
 */
 var 箭头 = "#fUI/Basic/BtHide3/mouseOver/0#";
 var 心 =  "#fEffect/CharacterEff/1032063/0/0#"; //长音符
 
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
     var 推广码 = cm.getPlayer().id;
     var 返利 = cm.getBossRank("推广积分", 2);
     var 推广人数 = cm.getBossRankCountTop("" + 推广码 + "").size();
     if (推广人数 >= 10) {
         var 返利比例 = 30;
     } else if (推广人数 >= 5 && 推广人数 < 10) {
         var 返利比例 = 20;
     } else {
         var 返利比例 = 10;
     }
     if (status <= 0) {
        
             var selStr = "\t      " + 心 + "#r#e < 推广系统 > #k#n " + 心 + "\r\n\r\n\r\n\r\n";

         selStr += "#d\t你目前已推广【#r" + 推广人数 + "#d】人，可以获得对方赞助余额的 #r" + 返利比例 + "%#k\r\n\r\n";
         //显示自己的推广码
         selStr += "\t\t\t\t你的推广码为:#r" + 推广码 + "#k#n\r\n";
         //显示收到的充值返利
         if (返利 >= 0) {
             selStr += "\t\t\t\t目前推广积分:#r" + 返利 + "#k#n\r\n";
         }
         //判断是否有推广员
         if (cm.getBossRank("推广上级", 2) > 0) {
             selStr += "\t\t\t\t你的推广上级是:#r" + cm.getCharacterNameById(cm.getBossRank("推广上级", 2)) + "#k#n\r\n";
         }
         selStr += "\t\t\t#L0#" + 箭头 + "#b推广说明#l#k\r\n";
         if (cm.getBossRankCount("推广上级") <= 0) {
             selStr += "\t\t\t#L1#" + 箭头 + "#b填写我的推荐人ID号码#r#l#k\r\n";
         }
         selStr += "\t\t\t#L2#" + 箭头 + "#b我的推广下级#l#k\r\n";
         if (返利 > 0) {
             selStr += "\t\t\t#L3#" + 箭头 + "#b兑换推广积分#l#k\r\n";
         }
         selStr += " ";
         cm.sendSimple(selStr)
     } else if (status == 1) {
         switch (selection) {
             case 0:
                 cm.dispose();
               cm.sendOk("推广人数为三个阶段:\r\n第一阶段当推广人数 <= 5 你将获得赞助的10%余额\r\n第二阶段当推广人数 >= 5 你将获得赞助的20%余额\r\n第三阶段当推广人数 >= 10 你将获得赞助的30%余额\r\n 对方填写你的推广码还额外获得#v2450023#×3#v2450019#×5#v2450019#×6");
                 break;
             case 1:
                 cm.sendGetText("\r\n\r\n请输入推荐人ID：");
                 break;
 
             case 3:
                 cm.setBossRankCount("兑换推广积分", 返利);
                 cm.setBossRankCount("推广积分", -返利);
                 cm.sendOk("恭喜你将 #r" + 返利 + "#k 推广积分兑换为" + 返利 * 200 + "点券。");
                 cm.gainNX(返利 * 200);
                 cm.dispose();
                 break;
             case 2:
                 var text = "\t#r" + cm.getChar().getName() + "#k 推广的玩家：#n\r\n\r\n";
                 var rankinfo_list = cm.getBossRankCountTop(推广码);
                 if (rankinfo_list != null) {
                     for (var i = 0; i < rankinfo_list.size(); i++) {
                         var info = rankinfo_list.get(i);
                         text += i == 0 ? "#b" : i == 1 ? "#b" : i == 2 ? "#b" : "";
                         text += "\t #r" + (i + 1) + "#k#n. ";
                         text += info.getCname() + " ";
                         for (var j = 16 - info.getCname().getBytes().length; j > 0; j--) {
                             text += " ";
                         }
                         /*text += "\t#bLv." + cm.getCharacterByNameLevel(info.getCname()) + "";
                          */
                         text += "#k";
                         text += "\t#b充值积分#k." + cm.getBossRank(cm.getPlayer().getCharacterIdByName(info.getCname()), "点券积分", 2) + "";
                         text += "#k";
                         text += "\r\n";
                     }
                 }
                 cm.sendOkS(text, 2);
                 cm.dispose();
                 break;
         }
     } else if (status == 2) {
         var fee = cm.getText();
         if (isNumber(fee) && fee != cm.getPlayer().id ) {
            fee = parseInt(fee);
             if (cm.getCharacterNameById(fee) != "" && cm.getCharacterNameById(fee) != null) {
                 cm.setBossRankCount("推广上级", fee) //证明填写了
                 cm.setBossRankCount(fee); //设置推广人
				    cm.gainItem(2450023, 3);
					   cm.gainItem(2450021, 6);
					      cm.gainItem(2450019, 5);
                 cm.sendOk("填写成功。你的推广人是“"+cm.getCharacterNameById(fee)+"”");
                 cm.dispose();
                 return
             } else {
                 cm.sendOk("输入的推广员ID不存在。");
                 cm.dispose();
                 return
             }
 
         } else {
             cm.sendOk("请输入正确的ID。");
             cm.dispose();
             return
         }
     }
 }
 
 function isNumber(val) {
 
     var regPos = /^[0-9]+.?[0-9]*/; //判断是否是数字。
 
     if (regPos.test(val)) {
         return true;
     } else {
         return false;
     }
 
 }