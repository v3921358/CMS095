
/**
 * 当前：余额兑换物品
 * 其中：
 * 0 表示需要消耗的余额
 * 1 表示兑换的物品id
 * 2 表示兑换到的物品数量
 */
var selArr = [];
var selType;
var 赞助余额;
var nxnum = 0;
var goldnum = 0;
var pgldnum = 0;
var selitem;
var itemArr = [ //快乐转蛋卷
    [5500, 4110000, 1],
	[50000, 4110000, 10],
    [480000, 4110000, 100]
      
]
var equipArr = [ //高级快乐百宝卷
    [5500, 5220010, 1],
	[50000, 5220010, 10],
    [480000, 5220010, 100]
]
var ConsumArr = [// 快乐百宝卷
    [5500, 5220000, 5],
    [50000, 5220000, 55],
    [480000, 5220000, 600]
];
var SettingsArr = [// 新手转蛋卷
    [5500, 5451001, 10],
	[50000, 5451001, 110],
    [480000, 5451001, 1200]
];
var status = 0;
var rn = "\r\n\r\n"; // 换行

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (mode == 0) {
            cm.sendOk("感谢你的光临~");
            cm.dispose();
            return;
        }
        if (mode == 1) {
            status++;
        } else {
            status--;
        }
        if (status === 0) {
            赞助余额 = cm.getBossRank("赞助余额", 2) == -1 ? 0 : cm.getBossRank("赞助余额", 2);
            var add = "请选择你取走的物品, 当前抵用卷:#b" + cm.getPlayer().getCSPoints(2) + "#l" + rn;
            add += "#L14##r取#k#b新手转蛋卷#v5451001##l" + rn;
            add += "#L2##r取#k#b快乐转蛋卷#v4110000##l" + rn;
            add += "#L13##r取#k#b快乐百宝卷#v5220000##l" + rn;
            add += "#L3##r取#k#b高级快乐百宝卷#v5220010##l" + rn;
        
            cm.sendSimple(add);
        } else if (status === 1) {
            selType = selection;
            var txt = "请选择你要取走的物品内容：" + rn;
            switch (selection) {
                case 0:
                    txt = "当前余额兑换比例为：1余额=1000点券" + rn;
                    txt += "请输入你要兑换多少#b点券#l：\r\n";
                    break;
                case 1:
                    txt = "当前余额兑换比例为：1余额=1000#b万#l金币" + rn;
                    txt += "请输入你要兑换多少万#b金币#l：\r\n";
                    break;
                case 2:
                    selArr = itemArr;
                    break;
                case 3:
                    selArr = equipArr;
                    break;
                case 4:
                    selArr = cashArr;
                    break;
                case 13:
                    selArr = ConsumArr;
                    break;
                case 14:
                    selArr = SettingsArr;
                    break;
                case 15:
                    txt = "当前余额兑换比例为：1余额=#b10000破攻值#l" + rn;
                    txt += "请输入你要兑换多少#b破攻值#l：\r\n";
                    break;
                case 16:
                    cm.dispose();
                    cm.openNpc(9900004, "宠吸兑换");
                    return;
                case 5:
                    cm.dispose();
                    cm.openNpc(9900004, "技能兑换");
                    return;
                case 6:
                    cm.dispose();
                    if (赞助余额 >= 88 && !cm.haveItem(5530076, 1)) {
                        cm.setBossRankCount("赞助余额", -88);
                        cm.gainGachaponItemTime(5530076, 1, "周卡", 7);
                        cm.sendOk("#e#b[提示]：\r\n兑换成功~\r\n");
                        return;
                    } else {
                        cm.dispose();
                        cm.sendOk("#e#r[提示]：\r\n余额不足，或上次月卡未过期~\r\n");
                        return;
                    }

                case 7:
                    cm.dispose();
                    if (赞助余额 >= 238 && !cm.haveItem(5010143, 1)) {
                        cm.setBossRankCount("赞助余额", -238);
                        cm.gainGachaponItemTime(5010143, 1, "月卡", 30);
                        cm.sendOk("#e#b[提示]：\r\n兑换成功~\r\n");
                        return;
                    } else {
                        cm.dispose();
                        cm.sendOk("#e#r[提示]：\r\n余额不足，或上次月卡未过期~\r\n");
                        return;
                    }
                case 8:
                    cm.dispose();
                    cm.openNpc(9900004, "自由转职");
                    return;
                case 10:
                    cm.dispose();
                    cm.openNpc(9900004, "CDK兑换");
                    return;
                case 11:
                    cm.dispose();
                    cm.打开网页("http://www.baidu.com");
                    cm.sendOk("#e#r[提示]：\r\nhttp://zidongjiaren.cn/\r\n");
                    break;
                case 12:
                    if (cm.haveItem(5530076,1)) {
                    cm.warp(199000000);
		            cm.sendNext("感谢您使用月卡会员功能！如果您下次使用记得我在拍卖~余额商场~期待您的下次光临！！");
		
	              } else {
	            	cm.sendOk("#b请确认是否有#r月卡#b或#r周卡#b！\r\n如需购买？购买位置，拍卖-余额商场-最下面~！！");
	              }
                    return;
                case 17:
                    cm.dispose();
                    cm.openNpc(9900004, "技能赞助礼品");
                    return;
                default:
                    cm.dispose();
            }
            if (selection < 2 || selection == 15) {
                cm.sendGetText(txt);
            } else {
                for (var i = 0; i < selArr.length; i++) {
                    txt += "\t#L" + i + "##d【" + selArr[i][0] + "】可取走#v" + selArr[i][1] + "##z" + selArr[i][1] + "# X " + selArr[i][2] + "#l\r\n";
                }
                cm.sendSimple(txt);
            }
        } else if (status == 2) {
            var txt2 = "";
            selitem = selection;
            if (selType == 0) {
                nxnum = cm.getText();
                if (nxnum % 1000 == 0 && (赞助余额 * 1000) >= nxnum&&nxnum>0) {
                    txt2 = "确定兑换" + nxnum + "点券吗？";
                } else {
                    cm.sendOk("余额不足，或点券不是整倍数！");
                    cm.dispose();
                    return;
                }

            } else if (selType == 1) {
                goldnum = cm.getText();
                if (goldnum % 1000 == 0 && (赞助余额 * 1000) >= goldnum&&goldnum>0) {
                    txt2 = "确定兑换" + goldnum + "万金币吗？";
                } else {
                    cm.sendOk("余额不足，或金币不是整倍数！");
                    cm.dispose();
                    return;
                }
            } else if (selType == 15) {
                pgldnum = cm.getText();
                if (pgldnum % 100 == 0 && (赞助余额 * 10000) >= pgldnum&&pgldnum>0) {
                    txt2 = "确定兑换" + pgldnum + "点破攻吗？";
                } else {
                    cm.sendOk("余额不足，或破攻值不是整倍数！");
                    cm.dispose();
                    return;
                }
            }
            else {
                if (cm.getPlayer().getCSPoints(2) >= selArr[selection][0]) {
                    txt2 = "#d确定使用【" + selArr[selection][0] + "】抵用卷取走#v" + selArr[selection][1] + "##z" + selArr[selection][1] + "# X " + selArr[selection][2] + " 个吗？#l";
                } else {
                    cm.sendOk("你投了个假币~~~");
                    cm.dispose();
                    return;
                }
            }
            cm.sendYesNo(txt2);
        } else if (status == 3) {
            if (selType == 0) {
                cm.setBossRankCount("赞助余额", -(nxnum / 1000));
                cm.gainNX(nxnum);
            } else if (selType == 1) {
                cm.setBossRankCount("赞助余额", -(goldnum / 1000));
                cm.gainMeso(goldnum * 10000);
            } else if (selType == 15) {
                cm.setBossRankCount("赞助余额", -(pgldnum / 10000));
                cm.getPlayer().addAccountExtraDamage(cm.getPlayer(), pgldnum);
            } else {
                cm.gainNX2( -selArr[selitem][0]);
                cm.gainItem(selArr[selitem][1], selArr[selitem][2]);
            }
            cm.sendOk("取出成功！费用自动扣除，欢迎下次光临~~");
status=-1
            //cm.dispose();
        }
    }
}