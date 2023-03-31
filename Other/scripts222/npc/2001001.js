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
	[910040000, "适合100级玩家 WW"],
	[910040004, "适合150级玩家 XXX"],
	[910040005, "适合200级玩家 YYYY"],
	[910040006, "适合250级玩家 ZZZZZ"]

	
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
			
			var selStr = "\r\n\t\t\t\t#r#e欢迎使用会员地图#n#k#l\r\n\r\n\r\n";	
			selStr += "  #d Hi~ #b#h ##k请选择你要去的地方吧，我能让你转瞬之间就到达目的地哦，不过你有钱的最好，赚钱之余多看看冒险岛的风景吧。#k#n#b\r\n";
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