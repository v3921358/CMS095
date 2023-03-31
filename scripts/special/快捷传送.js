/*
 ZEVMS冒险岛(079)游戏服务端
 脚本：空间传送
 */
var ca = java.util.Calendar.getInstance();
var year = ca.get(java.util.Calendar.YEAR);
var month = ca.get(java.util.Calendar.MONTH) + 1;
var day = ca.get(java.util.Calendar.DATE);
var hour = ca.get(java.util.Calendar.HOUR_OF_DAY);
var minute = ca.get(java.util.Calendar.MINUTE);
var second = ca.get(java.util.Calendar.SECOND);
var weekday = ca.get(java.util.Calendar.DAY_OF_WEEK);
var status = -1;
var maps = Array(
	[910000000, "自由市场入口"],
	[104020000, "六岔路口"],
	[960000000, "赤壁"],
	[1000000, "彩虹村"],
	[104000000, "明珠港"],
	[100000000, "射手村"],
	[101000000, "魔法密林"],
	[102000000, "勇士部落"],
	[103000000, "废弃都市"],
	[120000000, "诺特勒斯号码头"],
	[105000000, "林中之城"],
	[120030000, "黄金海岸"],
	[106020000, "蘑菇森林小道"],
	[140000000, "里恩"],
	[200000000, "天空之城"],
	[200000111, "码头<开往金银岛>"], //310000000
	[310000000, "埃德尔斯坦"],
	[211000000, "冰峰雪域"],
	[230000000, "水下世界"],
	[222000000, "童话村"],
	[220000000, "玩具城"],
	[701000000, "上海外滩"],
	[250000000, "武陵"],
	[702000000, "少林寺"],
	[500000000, "水上市场"],
	[260000000, "阿里安特"],
	[600000000, "新叶城-市区中心"],
	[240000000, "神木村"],
	[261000000, "玛加提亚"],
	[221000000, "地球防御本部"],
	[251000000, "百草堂"],
	[701000200, "上海豫园"],
	[550000000, "吉隆大都市"],
	[130000000, "圣地"],
	[551000000, "甘榜村"],
	[801000000, "昭和村"],
	[540010000, "樟宜机场"],
	[541000000, "新加坡码头"],
	[300000000, "阿尔泰营地"],
	[270000100, "时间神殿"],
	[702100000, "大雄宝殿"],
	[800000000, "古代神社"],
	[130000200, "圣地岔路"],
	[925020000, "武陵道场入口"],
	[271000000, "未来之门"],
	[914100000, "沉睡的岛"],
	//  [240050400,"黑龙"],
	[802000101, "逆奥之城"],
	[100000202, "宠物公园"],
	[220000006, "玩具城宠物训练场"],
	[105040310, "沉睡森林（一层）"],
	[910360000, "第1区域"],
	[910130000, "第1阶段"],
	[280020000, "火山心藏1"],
	[109030001, "上楼~上楼~<第1阶段>"],
	[109040001, "向高地<第1阶段>"],
	[910001003, "新手秘密农场"],
	[910001004, "中级者秘密农场"],
	[910001007, "高手秘密农场"],
	[910001009, "专家秘密农场"],
	[910001005, "新手秘密矿山"],
	[910001006, "中级者秘密矿山"],
	[910001008, "高手秘密广场"],
	[910001010, "专家秘密广场"]
);

function start() {
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
		if (mode == 1) {
			status++;
		} else {
			status--;
		}
		if (status == 0) {
			var selStr = "  #d Hi~ #b#h ##k请选择你要去的地方吧，我能让你转瞬之间就到达目的地哦，不过你还是要多锻炼一下最好，修炼之余多看看冒险岛的风景吧。#k#n#b\r\n";
			for (var i = 0; i < maps.length; i += 3) {
				if (i + 2 < maps.length) {
					selStr += "#L" + i + "# #b#m" + maps[i] + "##l" + replaceMapName(maps[i][1]) + "#L" + (i + 1) + "# #b#m" + maps[i + 1] + "##l" + replaceMapName(maps[i + 1][1]) + "#L" + (i + 2) + "# #b#m" + maps[i + 2] + "##l\r\n";
				}
				if (i + 2 == maps.length) {
					selStr += "#L" + i + "# #b#m" + maps[i] + "##l" + replaceMapName(maps[i + 1][1]) + "#L" + (i + 1) + "# #b#m" + maps[i + 1] + "##l\r\n";
				}
				if (i + 2 > maps.length) {
					selStr += "#L" + i + "# #b#m" + maps[i] + "##l\r\n";
				}

			}
			selStr += " ";
			cm.sendSimple(selStr);
		} else if (status == 1) {
			cm.warp(maps[selection][0]);
			cm.dispose();
		}
	}

}

function replaceMapName(mapname) {
	if (mapname.length == 2) {
		return mapname.padEnd(10, " ").replace(mapname, "");
	} else if (mapname.length == 3) {
		return mapname.padEnd(9, " ").replace(mapname, "");
	} else {
		return mapname.padEnd(5, "	").replace(mapname, "");
	}

}

String.prototype.padEnd = function padEnd(targetLength, padString) {
	targetLength = targetLength >> 0; //floor if number or convert non-number to 0;
	padString = String((typeof padString !== 'undefined' ? padString : ''));
	if (this.length > targetLength) {
		return String(this);
	} else {
		targetLength = targetLength - this.length;
		if (targetLength > padString.length) {
			padString += padString.repeat(targetLength / padString.length); //append to original to ensure we are longer than needed
		}
		return String(this) + padString.slice(0, targetLength);
	}
};

String.prototype.repeat = function (count) {
	'use strict';
	if (this == null) {
		throw new TypeError('can\'t convert ' + this + ' to object');
	}
	var str = '' + this;
	count = +count;
	if (count != count) {
		count = 0;
	}
	if (count < 0) {
		throw new RangeError('repeat count must be non-negative');
	}
	if (count == Infinity) {
		throw new RangeError('repeat count must be less than infinity');
	}
	count = Math.floor(count);
	if (str.length == 0 || count == 0) {
		return '';
	}
	// 确保 count 是一个 31 位的整数。这样我们就可以使用如下优化的算法。
	// 当前（2014年8月），绝大多数浏览器都不能支持 1 << 28 长的字符串，所以：
	if (str.length * count >= 1 << 28) {
		throw new RangeError('repeat count must not overflow maximum string size');
	}
	var rpt = '';
	for (;;) {
		if ((count & 1) == 1) {
			rpt += str;
		}
		count >>>= 1;
		if (count == 0) {
			break;
		}
		str += str;
	}
	return rpt;
}