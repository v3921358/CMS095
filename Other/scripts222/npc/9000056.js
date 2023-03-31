var rn = "\r\n\r\n"; // 换行
var 物品0 = 4001126; // 物品id
var 物品1 = 4031821;
var bl = 6; //// 物品对点券数量白金数
var jb = 100;//// 白金对金币比例
var dj = 200; //// 橙金对点卷比例
var ye = 300; //// 紫金对余额比例
var yezj = 10; //// 余额对紫金比例
var zdq = 10; //// 余额对转蛋器比例
var 拐杖 = 4001231;
var 转蛋器 = 5220040;
var 紫金兑换 = 100; 
var nx2ToItemCount = 20; // 白金兑换橙金的比例
var nxToItemCount = 10; // 橙金兑换紫金的比例
var itemToNXCount = 1000; // 点券换橙金的比例
//var zzjf = player.getBossRankCount9("充值积分") == -1 ? 0 : player.getBossRankCount9("充值积分")
var moseToItemCount = 10000000; // 金币兑换时，对物品数量，即10000000金币=1物品
var 赞助余额;
var itemToMoseCount = 1; // 物品对金币数量
var changeMode = 0; // 兑换类型

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
	白金余额 = cm.getBossRank9("白金点", 2);
	橙金余额 = cm.getBossRank9("橙金点", 2);
	紫金余额 = cm.getBossRank9("紫金点", 2);
	赞助余额 = cm.getBossRank("赞助余额", 2) == -1 ? 0 : cm.getBossRank("赞助余额", 2);
    var text;
    if (mode === -1) {
        cm.dispose();
    } else {
        if (status >= 0 && mode === 0) {
                      cm.dispose();
            return;
        }
        if (mode === 1)
            status++;
        else
            status--;

        if (status < 2)
            changeMode = 0;
        if(!cm.canHold(4002003)){
            cm.sendOk("背包空间不足！");
            cm.dispose();
            return;
        }
        if (status === 0) {
            text = "\t\t\t\t#r超级会员BOSS积分兑换" + rn;
	    text += "#b------------------------------------------------------" + rn;
			text += "#k#e #b白金：#g"+白金余额+" #b橙金：#r"+橙金余额+" #d#b紫金：#d"+紫金余额+" #d#b余额：#d"+赞助余额+"#k#n#b\r\n";
            text += "#L1#拐杖兑换白金#b#L2##b白金兑换橙金#b#L3##b橙金兑换紫金#b" + rn ;
			
			text += "#L4#白金兑换金币#b#L5##b橙金兑换点卷#b#L6##b紫金兑换余额#b" + rn;
			
			text += "#L7#点卷兑换紫金#b#L8##b余额兑换紫金#b#L9##b紫金兑换珍藏转蛋器#b" + rn;
			
			
		text += "------------------------------------------------------" + rn;

            cm.sendSimple(text);
        } else if (status === 1) {
            changeMode = selection;

            if (selection === 1) {  
             text = "#v" + 拐杖 + "# = #b" + bl + "#k白金点" + rn;
                text += "请输入你要兑换的拐杖个数：";
                cm.sendGetText(text);
            } else if (selection === 2) {  
                text = nx2ToItemCount + " 白金 =#b 1 #k橙金" + rn;
                text += "请输入你需要的橙金个数：";
                cm.sendGetText(text);
			} else if (selection === 3) {  
                text = nxToItemCount +  " 橙金 =#b 1 #k紫金" + rn;
                text += "请输入你需要的紫金个数：";
                cm.sendGetText(text);
			} else if (selection === 4) {  
                 text = "【1】白金 = #b" + jb + "#k 金币" + rn;
                text += "请输入你需兑换的白金个数：";
                cm.sendGetText(text);
			} else if (selection === 5) {  
                text = "【1】橙金 = #b" + dj + "#k 点卷" + rn;
                text += "请输入你需要的橙金个数：";
                cm.sendGetText(text);
			} else if (selection === 6) {  
                text = "【1】紫金 = #b" + ye + "#k 余额" + rn
                text += "请输入你需要的兑换的紫金个数：";
                cm.sendGetText(text);
			} else if (selection === 7) {  
                text = itemToNXCount + "点券 = 【1】紫金" + rn;
                text += "请输入你需要兑换的紫金个数：";
                cm.sendGetText(text);
			} else if (selection === 8) {  
                text = yezj + "余额 = 1 【1】紫金" + rn;
                text += "请输入你需要兑换的紫金个数：";
                cm.sendGetText(text);
			} else if (selection === 9) {  
                text = "【1】紫金 = #b" + zdq + "#k #v5220040##z5220040#" + rn
                text += "现在请输入你想要兑换的个数：";
                cm.sendGetText(text);
           
            }
        } else if (status === 2) {
            var count = parseInt(cm.getText());
            if (isNaN(count)) {
                cm.sendOk("数量输入有误！")
                cm.dispose();
                return;
            }

            if (changeMode === 1) {
                if (cm.haveItem(拐杖, count)) {
                    cm.gainItem(拐杖, -count);
					cm.setBossRankCount9("白金点",  count * bl);
                    cm.sendOk("兑换成功，获得#b " + (count * bl) + "#k 白金点,你当前总共#r " + cm.getBossRank9("白金点", 2) + " #k点");
                    cm.dispose();
                } else {
                    cm.sendOk("#v" + 拐杖 + "#数量不足无法换购！");
                    cm.dispose();
                }
            } else if (changeMode === 2) {
                if (白金余额 >= count * nx2ToItemCount) {
                    cm.setBossRankCount9("白金点",-count * nx2ToItemCount);
			        cm.setBossRankCount9("橙金点",count );
                    cm.sendOk("兑换成功，获得#b " + (count ) + "#k 橙金点,你当前总共#r " + cm.getBossRank9("橙金点", 2) + " #k点");
                    cm.dispose();
                } else {
                    cm.sendOk("白金数量不足，无法换购！");
                    cm.dispose();
										
                }
            } else if (changeMode === 3) {
                if (橙金余额 >= count * nxToItemCount) {
                    cm.setBossRankCount9("橙金点",-count * nxToItemCount);
			        cm.setBossRankCount9("紫金点",count );
                    cm.sendOk("兑换成功，获得#b " + (count ) + "#k 紫金金点,你当前总共#r " + cm.getBossRank9("紫金点", 2) + " #k点");
                    cm.dispose();
                } else {
                    cm.sendOk("橙金数量不足，无法换购！");
                    cm.dispose();
					
                }
            } else if (changeMode === 4) {
                if (白金余额 >= count ) {
                    cm.setBossRankCount9("白金点",-count );
			        cm.gainMeso(count * jb);
                    cm.sendOk("兑换成功，获得#b " + (count * jb) + "#k 金币");
                    cm.dispose();
                } else {
                    cm.sendOk("白金数量不足，无法换购！");
                    cm.dispose();
										
                }
            } else if (changeMode === 5) {
                if (橙金余额 >= count) {
                    cm.setBossRankCount9("橙金点",-count );
                    cm.gainNX(count * dj);
                    cm.sendOk("兑换成功，获得" + (count * dj) + "点卷");
                    cm.dispose();
                } else {
                    cm.sendOk("橙金数量不足，无法换购！");
                    cm.dispose();
					
                }
            } else if (changeMode === 6) {
                if (紫金余额 >= count) {
                    cm.setBossRankCount9("紫金点",count );
                    cm.setBossRankCount("赞助余额",count * ye );
                    cm.sendOk("兑换成功，获得" + (count * ye) + "#k 余额,你当前总共#r " + 赞助余额 + " #k余额");
                    cm.dispose();
                } else {
                    cm.sendOk("紫金数量不足，无法换购！");
                    cm.dispose();
					
                }
            } else if (changeMode === 7) {
                if (cm.getPlayer().getCSPoints(1) >= count * itemToNXCount) {
                    cm.gainNX(-count * itemToNXCount);
                    cm.setBossRankCount9("紫金点",count );
                    cm.sendOk("兑换成功，获得#b " + (count) + "#k 紫金点,你当前总共#r " + cm.getBossRank9("橙金点", 2) + " #k点");

                    cm.dispose();
                } else {
                    cm.sendOk("点券数量不足，无法换购！");
                    cm.dispose();
					
                }
            } else if (changeMode === 8) {
                if (赞助余额 >= count * yezj ) {
				    cm.setBossRankCount("赞助余额",-count * yezj );
                    cm.setBossRankCount9("紫金点",count );
                    cm.sendOk("兑换成功，获得#b " + (count ) + "#k 紫金金点,你当前总共#r " + cm.getBossRank9("紫金点", 2) + " #k点");
					cm.dispose();
                } else {
                    cm.sendOk("余额不足，无法换购！");
                    cm.dispose();
					
                }
            } else if (changeMode === 9) {
                  if (紫金余额 >= count) {
                    cm.setBossRankCount9("紫金点",-count );
                    cm.gainItem(转蛋器, count* zdq );
                    cm.sendOk("兑换成功，获得" + count * zdq + "#v" + 转蛋器 + "##z" + 转蛋器 + "#");
                    cm.dispose();
                } else {
                    cm.sendOk("紫金数量不足，无法换购！");
                    cm.dispose();
					

                }
            }
        }
    }
}