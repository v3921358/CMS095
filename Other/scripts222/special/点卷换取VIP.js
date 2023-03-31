


function start() {
	status = -1;
	
	action(1, 0, 0);
}

function action(mode, type, selection) {
            if (mode == -1) {
                cm.dispose();
            }
            else {
                if (status >= 0 && mode == 0) {
                
			cm.sendOk("感谢你的光临！");
			cm.dispose();
			return;                    
                }
                if (mode == 1) {
			status++;
		}
		else {
			status--;
		}
		        if (status == 0) {
			cm.sendSimple("您好，欢迎来到蘑菇仔冒险岛自动售货系统\r\n#L1#充值帮助#l \r\n\r\n      #d剩余点卷#r" + cm.getChar().getNX() + "点\r\n\r\n #L2#初级VIP 300W点卷#l \r\n #L3#高级VIP 500W点卷#l \r\n #L4#超级VIP 800W点卷#l \r\n #L9#VIP升级 300W点卷#l \r\n #L5#转生凭证 30W点卷#l \r\n #L6#3倍经验卡 200W点卷#l \r\n #L7#浪人披风(粉) 20W点卷#l \r\n #L8#工地手套(褐) 20W点卷#l");
				} else if (status == 1) {
					if (selection == 1) {
						cm.sendOk("test");
				        cm.dispose();
				}else if  (selection == 2) {
				    if(cm.getChar().getNX() >= 3000000) {
					   cm.gainNX(-3000000);
					   cm.getChar().setVip(1);
					   cm.sendOk("初级VIP购买成功！");
					   cm.dispose();
				    } else {
					   cm.sendOk("你没有足够的点卷，请充值！"); 
					   cm.dispose(); 
				    }
			    }else if  (selection == 3) {
			        if(cm.getChar().getNX() >= 5000000) {
					   cm.gainNX(-5000000);
					   cm.getChar().setVip(2);
					   cm.sendOk("高级VIP购买成功！");
					   cm.dispose();
				    } else {
					   cm.sendOk("你没有足够的点卷，请充值！"); 
					   cm.dispose(); 
				    }
			    }else if  (selection == 4) {
			        if(cm.getChar().getNX() >= 8000000) {
					   cm.gainNX(-8000000);
					   cm.getChar().setVip(3);
					   cm.sendOk("超级VIP购买成功！");
					   cm.dispose();
				    } else {
					   cm.sendOk("你没有足够的点卷，请充值！"); 
					   cm.dispose(); 
				    }
			    }else if  (selection == 5) {
			        if(cm.getChar().getNX() >= 300000) {
					   cm.gainNX(-300000);
					   cm.gainItem(4031692,1);
					   cm.sendOk("转生凭证购买成功！");
					   cm.dispose();
				    } else {
					   cm.sendOk("你没有足够的点卷，请充值！"); 
					   cm.dispose(); 
				    }
			    }else if  (selection == 6) {
			        if(cm.getChar().getNX() >= 2000000) {
					   cm.gainNX(-2000000);
					   cm.gainItem(5211003,1);
					   cm.sendOk("3倍经验卡购买成功！");
					   cm.dispose();
				    } else {
					   cm.sendOk("你没有足够的点卷，请充值！"); 
					   cm.dispose(); 
				    }
			    }else if  (selection == 7) {
			        if(cm.getChar().getNX() >= 200000) {
					   cm.gainNX(-200000);
					   cm.gainItem(1102041,1);
					   cm.sendOk("浪人披风(粉)购买成功！");
					   cm.dispose();
				    } else {
					   cm.sendOk("你没有足够的点卷，请充值！"); 
					   cm.dispose(); 
				    }
			    }else if  (selection == 8) {
			        if(cm.getChar().getNX() >= 200000) {
					   cm.gainNX(-200000);
					   cm.gainItem(1082149,1);
					   cm.sendOk("工地手套(褐)购买成功！");
					   cm.dispose();
				    } else {
					   cm.sendOk("你没有足够的点卷，请充值！"); 
					   cm.dispose(); 
				    }
			    }else if  (selection == 9) {
			       if(cm.getChar().getVip() >=3 ) {
			           cm.sendOk("你已经是超级VIP了，无需再升级"); 
			           cm.dispose(); 
			   	   }else if (cm.getChar().getNX() < 300000) {
					   cm.sendOk("你没有足够的点卷，请充值！"); 
					   cm.dispose(); 
				   } else {
					   cm.gainNX(-300000);
					   cm.getChar().gainVip(1);
					   cm.sendOk("VIP升级成功");
					   cm.dispose();
				   }
				   
			    }
		}
	}
} 