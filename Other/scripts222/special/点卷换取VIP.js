


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
                
			cm.sendOk("��л��Ĺ��٣�");
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
			cm.sendSimple("���ã���ӭ����Ģ����ð�յ��Զ��ۻ�ϵͳ\r\n#L1#��ֵ����#l \r\n\r\n      #dʣ����#r" + cm.getChar().getNX() + "��\r\n\r\n #L2#����VIP 300W���#l \r\n #L3#�߼�VIP 500W���#l \r\n #L4#����VIP 800W���#l \r\n #L9#VIP���� 300W���#l \r\n #L5#ת��ƾ֤ 30W���#l \r\n #L6#3�����鿨 200W���#l \r\n #L7#��������(��) 20W���#l \r\n #L8#��������(��) 20W���#l");
				} else if (status == 1) {
					if (selection == 1) {
						cm.sendOk("test");
				        cm.dispose();
				}else if  (selection == 2) {
				    if(cm.getChar().getNX() >= 3000000) {
					   cm.gainNX(-3000000);
					   cm.getChar().setVip(1);
					   cm.sendOk("����VIP����ɹ���");
					   cm.dispose();
				    } else {
					   cm.sendOk("��û���㹻�ĵ�����ֵ��"); 
					   cm.dispose(); 
				    }
			    }else if  (selection == 3) {
			        if(cm.getChar().getNX() >= 5000000) {
					   cm.gainNX(-5000000);
					   cm.getChar().setVip(2);
					   cm.sendOk("�߼�VIP����ɹ���");
					   cm.dispose();
				    } else {
					   cm.sendOk("��û���㹻�ĵ�����ֵ��"); 
					   cm.dispose(); 
				    }
			    }else if  (selection == 4) {
			        if(cm.getChar().getNX() >= 8000000) {
					   cm.gainNX(-8000000);
					   cm.getChar().setVip(3);
					   cm.sendOk("����VIP����ɹ���");
					   cm.dispose();
				    } else {
					   cm.sendOk("��û���㹻�ĵ�����ֵ��"); 
					   cm.dispose(); 
				    }
			    }else if  (selection == 5) {
			        if(cm.getChar().getNX() >= 300000) {
					   cm.gainNX(-300000);
					   cm.gainItem(4031692,1);
					   cm.sendOk("ת��ƾ֤����ɹ���");
					   cm.dispose();
				    } else {
					   cm.sendOk("��û���㹻�ĵ�����ֵ��"); 
					   cm.dispose(); 
				    }
			    }else if  (selection == 6) {
			        if(cm.getChar().getNX() >= 2000000) {
					   cm.gainNX(-2000000);
					   cm.gainItem(5211003,1);
					   cm.sendOk("3�����鿨����ɹ���");
					   cm.dispose();
				    } else {
					   cm.sendOk("��û���㹻�ĵ�����ֵ��"); 
					   cm.dispose(); 
				    }
			    }else if  (selection == 7) {
			        if(cm.getChar().getNX() >= 200000) {
					   cm.gainNX(-200000);
					   cm.gainItem(1102041,1);
					   cm.sendOk("��������(��)����ɹ���");
					   cm.dispose();
				    } else {
					   cm.sendOk("��û���㹻�ĵ�����ֵ��"); 
					   cm.dispose(); 
				    }
			    }else if  (selection == 8) {
			        if(cm.getChar().getNX() >= 200000) {
					   cm.gainNX(-200000);
					   cm.gainItem(1082149,1);
					   cm.sendOk("��������(��)����ɹ���");
					   cm.dispose();
				    } else {
					   cm.sendOk("��û���㹻�ĵ�����ֵ��"); 
					   cm.dispose(); 
				    }
			    }else if  (selection == 9) {
			       if(cm.getChar().getVip() >=3 ) {
			           cm.sendOk("���Ѿ��ǳ���VIP�ˣ�����������"); 
			           cm.dispose(); 
			   	   }else if (cm.getChar().getNX() < 300000) {
					   cm.sendOk("��û���㹻�ĵ�����ֵ��"); 
					   cm.dispose(); 
				   } else {
					   cm.gainNX(-300000);
					   cm.getChar().gainVip(1);
					   cm.sendOk("VIP�����ɹ�");
					   cm.dispose();
				   }
				   
			    }
		}
	}
} 