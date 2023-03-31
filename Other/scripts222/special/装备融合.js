var status = 0;
var 二级链接 = 0;
var ca = java.util.Calendar.getInstance();
var cishu = ca.get(java.util.Calendar.YEAR);
var cishu2= ca.get(java.util.Calendar.DATE);
var cishu1= ca.get(java.util.Calendar.MONTH)+1;
var shijian= (cishu*10000)+(cishu1*100)+cishu2; //获取日期
var hour = ca.get(java.util.Calendar.HOUR_OF_DAY); //获得小时
var minute = ca.get(java.util.Calendar.MINUTE);//获得分钟
var second = ca.get(java.util.Calendar.SECOND); //获得秒
var 选择的装备1格子位置;
var 选择的装备2格子位置;
var 确认已选装备1;
var 确认已选装备2;
var 获取选择的第一件装备等级;
var 可进行合成的最低装备等级 = 99
var 四维;
var 双攻;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (mode == 0) {
            cm.dispose();
            return;
        }
        if (mode == 1) status++;
        if (status == 0) {
		cm.sendSimple("\t\t\t\t#e#d【装备融合系统】\r\n#n#l#d装备等级必须为：#r99级以上，每次融合需要10000点券#k\r\n#b#e装备[A]#n#k可得到属性强化#r#e装备[B]#n#k会被吞噬#k... \r\n#b进行融合请务必选对装备 #r选错将无法撤回#k\r\n#d装备穿戴等级会提升融合属性#k\r\n#d装备[A]与装备[B]必须为相同的装备#k\r\n#d温馨提示: #r建议把装备[A]放在#b第一格位置#k\r\n#e\r\n  主·装备[A][#r未选#k]     副·装备[B][#r未选#k]\r\n         #L1##e#r#b选择装备[A](主装备)#d#l#k\r\n ");
        } else if (status == 1) {
			if (selection == 1) { 
                var it;
                    var texts = "#r---------------请选择您背包中的装备----------------#b\r\n";
                    var inv = cm.getInventory(1);
                    for (var i = 0; i <= 100; i++) {
                        it = inv.getItem(i);
                        if (it != null && cm.isCash(it.getItemId()) != true) {
                            texts += "#L" + i + "##v" + it.getItemId() + "# #b#z" + it.getItemId() + "# "
							texts += " #r[主]受益装备#l#b\r\n"
                        }
                    }
                    二级链接 = 1;
                    cm.sendSimple(texts);
                }
        } else if (status == 2) {
			if (二级链接 == 1) { 
			选择的装备1格子位置 = selection;
			确认已选装备1 = cm.getInventory(1).getItem(选择的装备1格子位置);
			获取选择的第一件装备等级 = cm.getReqLevel(确认已选装备1.getItemId());
			if (获取选择的第一件装备等级 >= 可进行合成的最低装备等级) { 
			二级链接 = 2;
			cm.sendSimple("\t\t\t\t#e#d【装备融合系统】\r\n#n#l#d装备等级必须为：#r99级以上，每次融合需要10000点券#k\r\n#b#e装备[A]#n#k可得到属性强化#r#e装备[B]#n#k会被吞噬#k... \r\n#b进行融合请务必选对装备 #r选错将无法撤回#k\r\n#d装备穿戴等级会提升融合属性#k\r\n#d装备[A]与装备[B]必须为相同的装备#k\r\n#d温馨提示: #r建议把装备[A]放在#b第一格位置#k\r\n#e\r\n  主·装备[A][#v"+确认已选装备1.getItemId()+"#]    副·装备[B][#r未选#k]\r\n         #L1##e#r#b选择装备[B](副装备)#d#l#k\r\n ");
			} else { 
			cm.sendOk("#r可进行融合的装备最低等级限制为#b"+可进行合成的最低装备等级+"#r级！#k请重新选择...#k");
			cm.dispose();
			}
			}
        } else if (status == 3) {
            if (二级链接 == 2) {
			if (selection == 1) {
                var it;
				获取选择的第一件装备等级 = cm.getReqLevel(确认已选装备1.getItemId());
                    var texts = "#k装备[A](主装备)[#v"+确认已选装备1.getItemId()+"##k]  装备[B](副装备)[#b请选择#k]\r\n\r\n#r温馨提示:系统自动跳过不符合条件的装备!#k\r\n";
                    var inv = cm.getInventory(1);
                    for (var i = 0; i <= 100; i++) {
                        it = inv.getItem(i);
                        if (it != null && it.getItemId() == 确认已选装备1.getItemId() && it != 确认已选装备1) {
                            texts += "#L" + i + "##v" + it.getItemId() + "# #b#z" + it.getItemId() + "# "
							texts += " #r[副]被吞噬装备#l#b\r\n"
                        }
                    }
                    二级链接 = 3;
                    cm.sendSimple(texts);
                }
				}
        } else if (status == 4) {
            if (二级链接 == 3) { 
			选择的装备2格子位置 = selection;
			确认已选装备2 = cm.getInventory(1).getItem(选择的装备2格子位置);
			二级链接 = 4;
			cm.sendSimple("装备[A]主装备当前属性为:\r\n   #r当前力量: #b"+确认已选装备1.getStr()+"\r\n   #r当前敏捷: #b"+确认已选装备1.getDex()+"\r\n   #r当前智力: #b"+确认已选装备1.getInt()+"\r\n   #r当前运气: #b"+确认已选装备1.getLuk()+"\r\n   #r当前攻击力: #b"+确认已选装备1.getWatk()+"\r\n   #r当前魔法力: #b"+确认已选装备1.getMatk()+"#k#e\r\n  #e主·装备[A][#v"+确认已选装备1.getItemId()+"#]    副·装备[B][#v"+确认已选装备2.getItemId()+"#]\r\n  #L1##e#r#b开始进行融合(#r装备[B](副装备)会消失#b)#d#l#k\r\n ");
			}
        } else if (status == 5) {
        	if (cm.getPlayer().getCSPoints(1) > 50000){
        		cm.gainNX(-50000);
	            if (二级链接 == 4) {
				var 获取装备 = 确认已选装备1.copy();
				if (获取选择的第一件装备等级 >= 99 && 获取选择的第一件装备等级 < 120) { 
						四维 = 1;	
						双攻 = 0;	
					} else if (获取选择的第一件装备等级 >= 120 && 获取选择的第一件装备等级 < 140) { 
						四维 = 2;	
						双攻 = 0;	
					} else if (获取选择的第一件装备等级 >= 140 && 获取选择的第一件装备等级 < 160) { 
						四维 = 3;	
						双攻 = 0;		
					} else if (获取选择的第一件装备等级 >= 160 && 获取选择的第一件装备等级 <= 180) { 
						四维 = 4;	
						双攻 = 1;	
					} else if (获取选择的第一件装备等级 > 180 && 获取选择的第一件装备等级 <= 255) { 
						四维 = 5;	
						双攻 = 2;	
					}
					获取装备.setStr((获取装备.getStr() + 四维));
					获取装备.setDex((获取装备.getDex() + 四维));
					获取装备.setInt((获取装备.getInt() + 四维));
					获取装备.setLuk((获取装备.getLuk() + 四维));
					获取装备.setWatk((获取装备.getWatk() + 双攻));
					获取装备.setMatk((获取装备.getMatk() + 双攻));
					cm.removeSlot(1, 选择的装备2格子位置, 1);//消失的装备
					cm.removeSlot(1, 选择的装备1格子位置, 1);//消失的装备
					Packages.server.MapleInventoryManipulator.addFromDrop(cm.getC(), 获取装备, false);//强化的装备获取
					cm.getPlayer().dropMessage(1, "成功融合\r\n四维增加: "+四维+"\r\n双攻增加: "+双攻+"\r\n请打开背包查看吧.");
					cm.dispose();
				}}
			else {
                cm.sendOk("你的点卷不足50000.");
                cm.dispose();
            }

        }
    }
	    }