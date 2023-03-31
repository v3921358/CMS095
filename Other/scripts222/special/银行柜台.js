var status = 0;
var 输入的存放的数值;
var 输入的取出的数值;
var 二级链接;
var ca = java.util.Calendar.getInstance();
var 获取年 = ca.get(java.util.Calendar.YEAR);
var 获取日= ca.get(java.util.Calendar.DATE);
var 获取月= ca.get(java.util.Calendar.MONTH)+1;
var 获取日期= (获取年*10000)+(获取月*100)+获取日; //获取日期

function start() {
	status = -1;
	action(1, 0, 0);
}

function action(mode, type, selection) {
	if (mode == -1) {
		cm.dispose();
	} else {
		if (mode == 0 && status == 0) {
			cm.dispose();
			return;
		}
		if (mode == 1)
			status++;
		else
			status--;
		if (status == 0) {
			if (cm.getBossRank9("金币存放开户", 2) < 0) {
			cm.setBossRankCount9("金币存放开户",1);
			cm.setBossRankCount9("金币存放",0);
			cm.sendOk("#r银行系统:#k已为您开户 请重新打开NPC");
			cm.dispose();
			return;
			}
			cm.sendSimple("  #d Hi~ #b#h # 您当前存放金币为:#r"+cm.getBossRank9("金币存放", 2) +"亿#b\r\n#d#L1#我要存钱#l\r\n#d#L2#我要取钱#l");
		} else if (status == 1){
			if (selection == 1) {
			二级链接 = 1;
			cm.sendGetText("您当前存放金币为:#r"+cm.getBossRank9("金币存放", 2) +"亿#b\r\n\r\n#d请在下面输入#r您要存放的金币#d数量(单位:亿):");
		} else 
			if (selection == 2) {
			二级链接 = 2;
			cm.sendGetText("您当前存放金币为:#r"+cm.getBossRank9("金币存放", 2) +"亿#b\r\n\r\n#d请在下面输入#r您要取出的金币#d数量(单位:亿):");
		}
		} else if (status == 2){
			if (二级链接 == 1) {
			输入的存放的数值 = cm.getText();
			if (isNaN(输入的存放的数值)) {
				cm.sendOk("很抱歉，只能为#r数字#k，请重新确认！");
				cm.dispose();
				return;
			}
			if(cm.getMeso() >= 输入的存放的数值*100000000){
			cm.gainMeso(-输入的存放的数值*100000000);
			cm.setBossRankCount9("金币存放",输入的存放的数值);	
			cm.dispose();
			} else {
			cm.sendOk("#r存放失败! #k你身上并没有"+输入的存放的数值+"亿.");
			cm.dispose();
			}
		}
			if (二级链接 == 2) {
			输入的取出的数值 = cm.getText();
			if (isNaN(输入的取出的数值)) {
				cm.sendOk("很抱歉，只能为#r数字#k，请重新确认！");
				cm.dispose();
				return;
			}
			if(cm.getBossRank9("金币存放", 2) >= 输入的取出的数值){
			if((cm.getMeso()+(输入的取出的数值*100000000)) <= 2100000000){
			cm.gainMeso(输入的取出的数值*100000000);
			cm.setBossRankCount9("金币存放",-输入的取出的数值);	
			cm.dispose();
			} else {
			cm.sendOk("#r取出失败! #k取出的金币加上背包的金币超过21亿.");
			cm.dispose();
			}
		} else {
			cm.sendOk("你存放的金币不足#r"+输入的取出的数值+"亿.");
			cm.dispose();
			}
		}
		}
	}
   }