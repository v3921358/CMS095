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
	[101020000, "绿水灵珠            适合1-10级玩家"],
	[100020400, "刺蘑菇盖            适合1-15级玩家"],
	[100020400, "跑环道符            适合1-15级玩家"],
	[103030000, "青蛇蛇皮            适合15-20级玩家"],
	[101030000, "火独眼兽            适合15-20级玩家"],
	[102030000, "动物皮              适合20-25级玩家"],
	[102030300, "跑环猪牙            适合25-30级玩家"],
	[105020000, "跑环龙皮            适合30-35级玩家"],
	[106020100, "跑环孢子            适合35-55级玩家"],
	[1010000,   "炼场入口            适合1-10级玩家"],
	[100020300, "蓝蘑菇一            适合10-15级玩家"],
	[103020100, "地铁线区1           适合10-15级玩家"],
	[103020300, "地铁线区1           适合15-20级玩家"],
	[103020300, "地铁线区3           适合20-30级玩家"],
	[103020300, "地铁线区4           适合30-35级玩家"],
	[102040301, "第一军营            适合30-35级玩家"],
	[103040103, "废弃广场1           适合35-40级玩家"],
	[103040300, "废弃广场2           适合40-45级玩家"],
	[200040000, "云彩公园            适合40-45级玩家"],
	[103040450, "废弃广场3           适合45-50级玩家"],
	[200010301, "黑暗庭院            适合50-55级玩家"],
	[105010000, "林中土龙            适合50-55级玩家"],
	[220010500, "露台大厅            适合50-55级玩家"],
	[800020130, "與大佛图            适合60-65级玩家"],
	[230010400, "东海叉路            适合70-75级玩家"],
	[250020000, "初级修炼场          适合80-85级玩家"],
	[600020300, "狼蜘蛛洞            适合80-85级玩家"],
	[261020400, "卡帕研究            适合80-90级玩家"],
	[541020000, "乌鲁城入口          适合85-90级玩家"],
	[251010000, "十年药草            适合90-95级玩家"],
	[541010010, "幽灵船区            适合90-95级玩家"],
        [551030100, "阴森世界            适合95-100级玩家"],
        [541020200, "乌鲁庄园            适合95-100级玩家"],
	[541020500, "乌鲁城中心          适合100-150级玩家"],
	[211041300, "死亡之林            适合100-150级玩家"],
	[240020100, "死亡战场            适合100-150级玩家"],
	[220070201, "消失的时间          适合100-150级玩家"],
	[220070301, "时间停止间          适合100-150级玩家"],
	[240040000, "龙的峡谷            适合100-150级玩家"],
	[240040500, "龙之巢穴            适合100-150级玩家"],
	[271030101, "演武场骑士          适合150-250级玩家"],
	[270020500, "后悔之路5           适合150-250级玩家"],
	[270030500, "忘却之路5           适合150-250级玩家"]

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