/*
 ZEVMS冒险岛(079)游戏服务端
 脚本：游戏CDK兑换系统
 使用函数：
 给个人记录
 cm.setBossRankCount("点券积分", fee);
 */
var ca = java.util.Calendar.getInstance();
var year = ca.get(java.util.Calendar.YEAR);
var month = ca.get(java.util.Calendar.MONTH) + 1;
var day = ca.get(java.util.Calendar.DATE);
var hour = ca.get(java.util.Calendar.HOUR_OF_DAY);
var minute = ca.get(java.util.Calendar.MINUTE);
var second = ca.get(java.util.Calendar.SECOND);
var weekday = ca.get(java.util.Calendar.DAY_OF_WEEK);

//玩家充值点券，反馈给推广员的百分比点券
var 推广员反馈百分比 = 10;


var status = 0;
var 心 = "#fUI/GuildMark.img/Mark/Etc/00009001/14#";
var fee;
var chance = Math.floor(Math.random() * 1);
function start() {
    status = -1;
    action(1, 0, 0);
}

function isNull( str ){
	if ( str == "" ) {
		return true;
	}
	var regu = "^[ ]+$";
	var re = new RegExp(regu);
	return re.test(str);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (mode == 0) {
            cm.sendOk("你没有卡号？");
            cm.dispose();
            return;
        }
        if (mode == 1) {
            status++;
        } else {
            status--;
        }
        if (cm.getInventory(1).isFull()) {
            cm.sendOk("请保证 #b装备栏#k 至少有2个位置。");
            cm.dispose();
            return;
        } else if (cm.getInventory(2).isFull()) {
            cm.sendOk("请保证 #b消耗栏#k 至少有2个位置。");
            cm.dispose();
            return;
        } else if (cm.getInventory(3).isFull()) {
            cm.sendOk("请保证 #b设置栏#k 至少有2个位置。");
            cm.dispose();
            return;
        } else if (cm.getInventory(4).isFull()) {
            cm.sendOk("请保证 #b其他栏#k 至少有2个位置。");
            cm.dispose();
            return;
        } else if (cm.getInventory(5).isFull()) {
            cm.sendOk("请保证 #b特殊栏#k 至少有2个位置。");
            cm.dispose();
            return;
        }
        //推广员信息
        var 角色 = cm.getPlayer().id;
        var 推广员名字 = cm.getCharacterNameById(cm.getBossRank("推广上级", 2));
        var 推广员ID = cm.getBossRank("推广上级", 2);
        //var 积分 = cm.getBossRank("点券积分", 2);
        if (积分 < 0) {
            var 积分 = 0;
        }
        if (status == 0) {
            cm.sendGetText("   \t\t\t#e#r< eV.095冒险岛赞助系统 > \r\n\r\n\r\n\r\n\r\n\r\n当前比例  ↓ ↓\r\n赞助比例：10:20 \r\n#n#k如果你有32位赞助码的话，可以在这里使用，就可以兑换了。#r\r\n请输入赞助码；");
     
	 } else if (status == 1) {
            fee = cm.getText();
			//cm.私聊输出信息(""+cm.getChar().getName()+" 在自助系统输入 "+fee+" ","71447500");
			//判断是偶有空格
			if(fee.indexOf(" ")!=-1){
				cm.sendOk("请不要加入空格。");
                cm.dispose();
                return;
			}
			//判断CDK兑换码的位数
			if(fee.getBytes().length!=32){
				cm.sendOk("请输入正确的32位CDK兑换码。");
                cm.dispose();
                return;
			}
            //判断卡号是否存在
            if (cm.判断兑换卡是否存在("" + fee + "") <= 0) {
                cm.sendOk("卡号不存在，或者该卡号未使用，请你稍后再试试。");
                cm.dispose();
                return;
            }
            //判断该兑换卡是点券
            if (cm.判断兑换卡类型("" + fee + "") == 1) {
                //充值点券
                cm.gainNX(cm.判断兑换卡数额("" + fee + ""));
				
                //输出提示语
                cm.sendOk("恭喜你成功兑换了 #r" + cm.判断兑换卡数额("" + fee + "") + "#k 余额。额外赠送：#r"+ cm.判断兑换卡数额("" + fee + "")/1 +"#k 点卷。\r\n相应比例已发送至余额！");
                
                //记录玩家充值的点券
                cm.setBossRankCount("点券积分", cm.判断兑换卡数额("" + fee + "")/1);
                //新增余额当前比例1点券1余额
                cm.setBossRankCount("赞助余额", cm.判断兑换卡数额("" + fee + "")/1);
				cm.setBossRankCount("赞助余额", cm.判断兑换卡数额("" + fee + "")/1);
				 cm.setBossRankCount9("充值积分", cm.判断兑换卡数额("" + fee + "")/1);
                var text = "[赞助]感谢玩家【" + cm.getPlayer().getName() + "】的支持，赞助了" +(cm.判断兑换卡数额("" + fee + "")/1)+"点余额";
				cm.broadcastServerMsg(5120008, text, true);
                
                //判断是否有推广员，如果有，就将部分充值分享给推广员
                if (cm.getBossRank("推广上级", 2) > 0) {
                    //给推广员发送返利
                    // cm.setBossRankCountByChrId(推广员ID, "推广积分", (cm.判断兑换卡数额("" + fee + "") / 100 * 推广员反馈百分比));
                    cm.setBossRankCountByChrId(推广员ID, "赞助余额", (cm.判断兑换卡数额("" + fee + "") / 100 * 推广员反馈百分比));
                    //给推广员发送小纸条
                    cm.小纸条("" + 推广员名字 + "", "[充值返利]:" + cm.getChar().getName() + " 充值 " + cm.判断兑换卡数额("" + fee + "") + " 余额，你获得返利 " + cm.判断兑换卡数额("" + fee + "") / 100 * 推广员反馈百分比 + " 余额。");
                    
                }
                //删除充值卡
				cm.Deleteexchangecard("" + fee + "");
				cm.dispose();
                //判断该兑换卡是抵用券
            } else if (cm.判断兑换卡类型("" + fee + "") == 2) {
                //充值抵用券
                cm.gainNX2(cm.判断兑换卡数额("" + fee + ""));
                //输出提示语
                cm.sendOk("恭喜你成功兑换了 #r" + cm.判断兑换卡数额("" + fee + "") + "#k 抵用券。");
				//删除充值卡
				cm.Deleteexchangecard("" + fee + "");
				cm.dispose();
                //判断该兑换卡是礼包
            } else if (cm.判断兑换卡类型("" + fee + "") == 5) {
                //打开礼包
                var Lb = cm.判断兑换卡礼包("" + fee + "");
                switch (Lb) {
                    //体验卡
                    case 3:
                        if (cm.getBossRank("公益VIP体验", 2) <= 0) {
                            cm.dispose();
                            cm.openNpc(9900004, Lb);
							//使用成功后删除兑换卡
							cm.Deleteexchangecard("" + fee + "");
                        } else {
                            cm.sendOk("一个角色只能使用一次体验卡，你已经使用过了，无法继续使用。");
                            cm.dispose();
                        }
                        break;
                        //群聊私聊的奖励CDK
					case 101:
						if(cm.判断每日("平台每日奖励")<=0){
							cm.dispose();
							cm.openNpc(9900005, Lb);
							cm.增加每日("平台每日奖励");
							cm.充值卡兑换记录("每日奖励", "" + cm.getChar().getName() + " 使用了每日奖励，cdk" + fee + "");
							//使用成功后删除兑换卡
							cm.Deleteexchangecard("" + fee + "");
						}else{
							cm.sendOk("请明天再来使用吧。");
							cm.dispose();
						}
                        break;	
					case 21:
					case 22:
					case 23:
					case 24:
						if(day ==14 && hour>=20){
							cm.dispose();
							cm.openNpc(9900005, Lb);
							//使用成功后删除兑换卡
							cm.Deleteexchangecard("" + fee + "");
						}else{
							cm.sendOk("不在活动时间。请在 20：00开始兑换。");
							cm.dispose();
							return;
						}
						break;
                    default:
						if (Lb == 1001 && cm.getPlayer().getBossLogS("考古套装") > 0) {
							cm.sendOk("你已经购买过一次考古学家套装礼包了，无法继续购买！");
							cm.dispose();
							return;
						}
						if (Lb == 1002 && cm.getPlayer().getBossLogS("三国套装") > 0) {
							cm.sendOk("你已经购买过一次三国名将套装礼包了，无法继续购买！");
							cm.dispose();
							return;
						}
						if (Lb == 1003 && cm.getPlayer().getBossLogS("漫新套装") > 0) {
							cm.sendOk("你已经购买过一次漫步新月套装礼包了，无法继续购买！");
							cm.dispose();
							return;
						}
						if (Lb == 1004 && cm.getPlayer().getBossLogD("祝福礼包") > 0) {
							cm.sendOk("你今天已经购买每日祝福卷轴礼包了，请明天再来！");
							cm.dispose();
							return;
						}
						if (Lb == 1005 && cm.getPlayer().getBossLogD("放大镜礼包") > 0) {
							cm.sendOk("你今天已经购买每日放大镜礼包了，请明天再来！");
							cm.dispose();
							return;
						}
						if (Lb == 1006 && cm.getPlayer().getBossLogD("双倍礼包") > 0) {
							cm.sendOk("你今天已经购买了双倍经验礼包了，请明天再来！");
							cm.dispose();
							return;
						}
						if (Lb == 1007 && cm.getPlayer().getBossLogD("每日暖暖礼包") > 2) {
							cm.sendOk("你今天已经购买了3次每日暖暖礼包了，请明天再来！");
							cm.dispose();
							return;
						}
						if (Lb == 1008 && cm.getPlayer().getBossLogD("每日快乐礼包") > 2) {
							cm.sendOk("你今天已经购买了3次每日快乐礼包了，请明天再来！");
							cm.dispose();
							return;
						}
                        cm.dispose();
                        cm.openNpc(9900005, "礼包" + Lb + "");
						cm.充值卡兑换记录("兑换记录", "" + cm.getChar().getName() + " 使用了cdk" + fee + "");
						//使用成功后删除兑换卡
						cm.Deleteexchangecard("" + fee + "");
                        break;
                }
            }
            //使用成功后删除兑换卡
            //cm.Deleteexchangecard("" + fee + "");
        }
    }
}