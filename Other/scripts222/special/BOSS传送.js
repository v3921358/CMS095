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
	[960000000, "赤壁娱乐      娱乐副本"],
	[802000101, "逆奥之城      剧情BOSS"],
	[104010200, "红蜗牛王      10级1万血"],	
	[100020301, "蓝蘑菇王      15级1万血"],
	[106021400, "灰雪人        15级1万血"],
	[103020320, "谢尔德        17级1万血"],
	[102020500, "树妖王        20级2万血"],
	[100020401, "蘑菇王墓      23级2万血"],
	[103030400, "多尔          28级3万血"],
	[101040300, "浮士德        33级6万血"],
	[106021401, "企鹅王        35级1万血"],
	[120030500, "巨居蟹        42级10万血"],
	[200010300, "艾利杰        60级27万血"],
	[105030500, "蝙蝠怪        66级2万血"],
	[221020701, "泡泡鱼        68级9万血"],
	[222010310, "九尾狐        62级37万血"],
	[220040200, "提莫          75级75万血"],
	[221040301, "朱诺          78级105万血"],
	[260010201, "大宇          80级125万血"],
	[105020400, "小吃车        85级175万血"],
	[250010304, "肯德熊        90级225万血"],
	[250010503, "妖怪禅师      97级295万血"],
	[230040420, "皮亚奴斯      110级300万血"],
	[230020100, "歇尔夫        63级335万血"],
	[261030000, "吉米拉        102级345万血"],
        [200101500, "薛西斯        63级360万血"],
	[240020401, "喷火龙        120级370万血"],
	[240020101, "天鹰          120级370万血"],
	[211040101, "驮狼雪人      122级545万血"],
	[211041400, "黑山老妖      134级775万"],
	[240040401, "大海兽        145级1050万血"],
	[270010500, "多多          150级1117万血"],
	[270020500, "独角兽        159级1600万血"],
	[270030500, "雷卡          168级2050万血"],
	[541020700, "克雷赛尔      140级2500万血"],
	[551030100, "暴力熊        80级6000万血"],
	[702070400, "武林妖僧      130级8000万血"],
	[220080000, "帕拉图斯      126级230万血"],
	[211042400, "扎昆          110级660万血"],
	 [211042301, "进阶扎昆        180级33亿"],
     [240050400, "黑龙王        160级33亿"],
     [240060201, "进阶黑龙王        180级330亿"],
	[211070000, "狮子王        129级5500万血"],
	[105100100, "蝙蝠王        70级60亿血"],
	[270050000, "品克缤        180级3000万血"],
	[271040000, "希纳斯        188级60亿血"]

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
			for (var i = 0; i < maps.length; i++) {
				
				selStr += "#L" + i + "##b#e"+maps[i][1]+"#l\r\n";
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