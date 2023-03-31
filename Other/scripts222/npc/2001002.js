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
	[889100002, "适合30-40级玩家"],
	[889100012, "适合40-50级玩家"],
	[889100022, "适合50-60级玩家"],
	[889100100, "适合60-70级玩家"],
	[889100020, "适合70-80级玩家"],
	[889100000, "适合80-90级玩家"],
	[889100010, "适合90-100级玩家"],
	[209000001, "适合100-110级玩家"],
	[209000002, "适合110-120级玩家"],
	[209000003, "适合120-130级玩家"],
	[209000004, "适合130-140级玩家"],
	[209000005, "适合140-150级玩家"],
	[209000006, "适合150-160级玩家"],
	[209000007, "适合160-170级玩家"],
	[209000008, "适合170-180级玩家"],
	[209000009, "适合180-190级玩家"],
	[209000010, "适合190-200级玩家"],
	[209000011, "适合200-210级玩家"],
	[209000012, "适合210-220级玩家"],
	[209000013, "适合220-230级玩家"],
	[209000014, "适合230-245级玩家"],
	[209000015, "适合245-255级玩家"]

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
			selStr += "  #d Hi~ #b#h ##k请选择你要去的地方吧，我能让你转瞬之间就到达目的地哦，不过你还是要多锻炼一下最好，修炼之余多看看冒险岛的风景吧。#k#n#b\r\n";
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