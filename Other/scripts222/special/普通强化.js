var status = 0;
var 二级链接 = 0;
var ca = java.util.Calendar.getInstance();
var cishu = ca.get(java.util.Calendar.YEAR);
var cishu2= ca.get(java.util.Calendar.DATE);
var cishu1= ca.get(java.util.Calendar.MONTH)+1;
var shijian= (cishu*1000)+(cishu1*100)+cishu2; //获取日期
var hour = ca.get(java.util.Calendar.HOUR_OF_DAY); //获得小时
var minute = ca.get(java.util.Calendar.MINUTE);//获得分钟
var second = ca.get(java.util.Calendar.SECOND); //获得秒
var 获取选择装备位置;
var 确认强化装备位置;
var 四维强化数值 = 2; //每次强化提升的四维属性
var 双攻强化数值 = 2; //每次强化提升的双攻属性
var 强化次数上限 = 99; //可强化限制的次数
var 强化单项封顶属性 = 32767; //防止属性爆掉 上限设置32767
var 强化装备所需等级 = 0; //不设限制
var 强化成功率 = 35; //60为60%成功率
var 武器类花费基础值 = 100;
var 防具类花费基础值 = 500;
var NPC代码 = 1204010;
var 返回NPC后缀代码 = 0;
var 强化NPC后缀代码 = 2;
var 随机数;
var 获取强化等级;
var 获取人物等级;
var 判定觉醒级别;//防止跟觉醒冲突



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
		   获取选择装备位置 = cm.getBossRank9("设置强化装备位置", 2);
		   确认强化装备位置 = cm.getInventory(1).getItem(获取选择装备位置);
		   获取强化等级 = 确认强化装备位置.getMpR();
		    var texts = "\t\t\t\t#e#b【匠人装备强化系统】#n\r\n\r\n";
                texts += "#d当前装备：#v"+确认强化装备位置.getItemId()+"##r#t"+确认强化装备位置.getItemId()+"# +"+获取强化等级+"\r\n";    
				if (确认强化装备位置.getItemId() >= 1212000 && 确认强化装备位置.getItemId() <= 1702979 && 确认强化装备位置.getItemId() != 1662004 && 确认强化装备位置.getItemId() != 1662005 && 确认强化装备位置.getItemId() != 1662000 && 确认强化装备位置.getItemId() != 1662001 && 确认强化装备位置.getItemId() != 1662002 && 确认强化装备位置.getItemId() != 1662003 && 确认强化装备位置.getItemId() != 1672006 && 确认强化装备位置.getItemId() != 1672001 && 确认强化装备位置.getItemId() != 1672000 && 确认强化装备位置.getItemId() != 1672005 && 确认强化装备位置.getItemId() != 1672002 && 确认强化装备位置.getItemId() != 1672004 && 确认强化装备位置.getItemId() != 1672003) { //验证是否为武器 若是武器可以增加双攻
				texts += "#d消耗点卷：#r"+(获取强化等级+1) * 100+" #d点卷\r\n"; 
				} else {
				texts += "#d消耗点卷：#r"+(获取强化等级+1) * 100+" #d点卷\r\n"; 
				}
				texts += "#d强化概率：#r60% #d成功率\r\n";  
				texts += "#d强化收益：#r四维各+"+四维强化数值+" #d点\r\n";  
				if (确认强化装备位置.getItemId() >= 1212000 && 确认强化装备位置.getItemId() <= 1702979 && 确认强化装备位置.getItemId() != 1662004 && 确认强化装备位置.getItemId() != 1662005 && 确认强化装备位置.getItemId() != 1662000 && 确认强化装备位置.getItemId() != 1662001 && 确认强化装备位置.getItemId() != 1662002 && 确认强化装备位置.getItemId() != 1662003 && 确认强化装备位置.getItemId() != 1672006 && 确认强化装备位置.getItemId() != 1672001 && 确认强化装备位置.getItemId() != 1672000 && 确认强化装备位置.getItemId() != 1672005 && 确认强化装备位置.getItemId() != 1672002 && 确认强化装备位置.getItemId() != 1672004 && 确认强化装备位置.getItemId() != 1672003) { //验证是否为武器 若是武器可以增加双攻
				texts += "#d武器收益：#r双攻各+"+双攻强化数值+" #d点\r\n";  
				texts += "#d装备请放置第一格，强化上限为 #r22\r\n"; 
				}
                texts += "\r\n#L1##e#r确定强化#d#l#k    \r\n ";
		   cm.sendSimple(texts);
        } else if (status == 1) {
			if (selection == 1) {
	        获取人物等级 = 22;
			获取强化等级 = 确认强化装备位置.getMpR();
			if (确认强化装备位置.getItemId() >= 1212000 && 确认强化装备位置.getItemId() <= 1702979 && 确认强化装备位置.getItemId() != 1662004 && 确认强化装备位置.getItemId() != 1662005 && 确认强化装备位置.getItemId() != 1662000 && 确认强化装备位置.getItemId() != 1662001 && 确认强化装备位置.getItemId() != 1662002 && 确认强化装备位置.getItemId() != 1662003 && 确认强化装备位置.getItemId() != 1672006 && 确认强化装备位置.getItemId() != 1672001 && 确认强化装备位置.getItemId() != 1672000 && 确认强化装备位置.getItemId() != 1672005 && 确认强化装备位置.getItemId() != 1672002 && 确认强化装备位置.getItemId() != 1672004 && 确认强化装备位置.getItemId() != 1672003) {
			if (cm.getPlayer().getCSPoints(1) <(获取强化等级+1) * 100) {
				cm.dispose();
				cm.sendOk("#r点卷不足! #k下一级强化需要花费 #r"+(获取强化等级+1) * 100+" #k点卷");
				return;
				}
			} else {
			if (cm.getPlayer().getCSPoints(1) <(获取强化等级+1) * 100) {
				cm.dispose();
				cm.sendOk("#r点卷不足! #k下一级强化需要花费 #r"+(获取强化等级+1) * 100+" #k点卷");
				return;
				}
		}
            if (获取强化等级 < 获取人物等级) {			
            随机数 = Math.floor(Math.random()*100);
			var 获取装备 = 确认强化装备位置.copy();
			if (获取装备.getHpR() == 10) { 
					判定觉醒级别 = "1";
				} else if (获取装备.getHpR() == 8) { 
					判定觉醒级别 = "2";
				} else if (获取装备.getHpR() == 6) { 
					判定觉醒级别 = "3";
				} else if (获取装备.getHpR() == 4) { 
					判定觉醒级别 = "4";
				} else if (获取装备.getHpR() == 3) { 
					判定觉醒级别 = "5";
				} else if (获取装备.getHpR() == 2) { 
					判定觉醒级别 = "6";
				} else if (获取装备.getHpR() == 1) { 
					判定觉醒级别 = "7";
				}
			if (随机数 <= 强化成功率){ //验证强化成功率
				if (确认强化装备位置.getItemId() >= 1212000 && 确认强化装备位置.getItemId() <= 1702979 && 确认强化装备位置.getItemId() != 1662004 && 确认强化装备位置.getItemId() != 1662005 && 确认强化装备位置.getItemId() != 1662000 && 确认强化装备位置.getItemId() != 1662001 && 确认强化装备位置.getItemId() != 1662002 && 确认强化装备位置.getItemId() != 1662003 && 确认强化装备位置.getItemId() != 1672006 && 确认强化装备位置.getItemId() != 1672001 && 确认强化装备位置.getItemId() != 1672000 && 确认强化装备位置.getItemId() != 1672005 && 确认强化装备位置.getItemId() != 1672002 && 确认强化装备位置.getItemId() != 1672004 && 确认强化装备位置.getItemId() != 1672003) { //验证是否为武器 若是武器可以增加双攻
				if ((获取装备.getStr()+四维强化数值) <= 强化单项封顶属性){ 
				获取装备.setStr(获取装备.getStr() +四维强化数值);
				}
				if ((获取装备.getDex()+四维强化数值) <= 强化单项封顶属性){ 
				获取装备.setDex(获取装备.getDex() +四维强化数值);
				}
				if ((获取装备.getInt()+四维强化数值) <= 强化单项封顶属性){ 
				获取装备.setInt(获取装备.getInt() +四维强化数值);
				}
				if ((获取装备.getLuk()+四维强化数值) <= 强化单项封顶属性){ 
				获取装备.setLuk(获取装备.getLuk() +四维强化数值);
				}
				if ((获取装备.getWatk()+四维强化数值) <= 强化单项封顶属性){ 
				获取装备.setWatk(获取装备.getWatk() +双攻强化数值);
				}
				if ((获取装备.getMatk()+四维强化数值) <= 强化单项封顶属性){ 
				获取装备.setMatk(获取装备.getMatk() +双攻强化数值);
				}
				if (获取装备.getHpR() > 0){ 
				获取装备.setOwner(""+判定觉醒级别+"级+"+(获取装备.getMpR()+1)+"");
				} else {
				获取装备.setOwner("+"+(获取装备.getMpR()+1)+"");
				}
				获取装备.setMpR(获取装备.getMpR()+1);
				获取装备.setFlag(1);
				cm.removeSlot(1, 获取选择装备位置, 1);//消失的装备
				Packages.server.MapleInventoryManipulator.addFromDrop(cm.getC(), 获取装备, false);//强化的装备获取
				cm.gainNX(-(获取强化等级+1) * 100); 
				} else { //判定为非武器 只加四维 属性降低
				if ((获取装备.getStr()+四维强化数值) <= 强化单项封顶属性){ 
				获取装备.setStr(获取装备.getStr() +四维强化数值);
				}
				if ((获取装备.getDex()+四维强化数值) <= 强化单项封顶属性){ 
				获取装备.setDex(获取装备.getDex() +四维强化数值);
				}
				if ((获取装备.getInt()+四维强化数值) <= 强化单项封顶属性){ 
				获取装备.setInt(获取装备.getInt() +四维强化数值);
				}
				if ((获取装备.getLuk()+四维强化数值) <= 强化单项封顶属性){ 
				获取装备.setLuk(获取装备.getLuk() +四维强化数值);
				}
				if (获取装备.getHpR() > 0){ 
				获取装备.setOwner(""+判定觉醒级别+"级+"+(获取装备.getMpR()+1)+"");
				} else {
				获取装备.setOwner("+"+(获取装备.getMpR()+1)+"");
				}
				获取装备.setMpR(获取装备.getMpR()+1);
				//获取装备.setFlag(1);//上锁无法砸卷 所以取消
				cm.removeSlot(1, 获取选择装备位置, 1);//消失的装备
				Packages.server.MapleInventoryManipulator.addFromDrop(cm.getC(), 获取装备, false);//强化的装备获取
				cm.gainNX(-(获取强化等级+1) * 100);
				}
				} else { //强化失败
				if (确认强化装备位置.getItemId() >= 1212000 && 确认强化装备位置.getItemId() <= 1702979 && 确认强化装备位置.getItemId() != 1662004 && 确认强化装备位置.getItemId() != 1662005 && 确认强化装备位置.getItemId() != 1662000 && 确认强化装备位置.getItemId() != 1662001 && 确认强化装备位置.getItemId() != 1662002 && 确认强化装备位置.getItemId() != 1662003 && 确认强化装备位置.getItemId() != 1672006 && 确认强化装备位置.getItemId() != 1672001 && 确认强化装备位置.getItemId() != 1672000 && 确认强化装备位置.getItemId() != 1672005 && 确认强化装备位置.getItemId() != 1672002 && 确认强化装备位置.getItemId() != 1672004 && 确认强化装备位置.getItemId() != 1672003) { //验证是否为武器 若是武器扣除更多点卷
				cm.gainNX(-(获取强化等级+1) * 100); 
				} else {
				cm.gainNX(-(获取强化等级+1) * 100); 
				}
				}
				cm.dispose();
				cm.openNpc(NPC代码,强化NPC后缀代码);
				if (随机数 <= 强化成功率){ //验证强化成功率
				cm.getPlayer().dropMessage(1, "强化成功 当前等级为: 强化+"+获取装备.getMpR()+"");
				} else { //强化失败
				cm.getPlayer().dropMessage(1, "强化失败 扣除点卷");
				}	
			}  else {
		     	cm.sendOk("#r无法进行强化! #k装备强化等级上限\r\n");
				cm.dispose();
				}
			}
			if (selection == 2) {
			cm.dispose();
			cm.openNpc(NPC代码,返回NPC后缀代码);
			} 
        } else if (status == 2) {
			if (二级链接 == 1) { 
			选择强化装备位置 = selection;
			cm.setBossRankCount9("设置强化装备位置",-cm.getBossRank9("设置强化装备位置", 2)+选择强化装备位置);
			cm.dispose();
			}
        
        }
    }
	    }