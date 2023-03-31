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
       [950100100, "猴子寺院A        1级---10级"],
	   [950100101, "猴子寺院B        10级---20级"],
	   [950100102, "猴子寺院C        20级---30级"],
	   [950100103, "猴子寺院D        30级---40级"],
	   [950100104, "猴子寺院E        40级---50级"],
	   [950100105, "猴子寺院F        50级---60级"],
	   [950100106, "猴子寺院G        60级---70级"],
	   [950100107, "猴子寺院H        70级---80级"],
	   [950100108, "猴子寺院I        80级---90级"],
	   [950100109, "猴子寺院J        90级---100级"],
	   [950100111, "猴子寺院K        100级---110级"],
	   [950100112, "猴子寺院L        110级---120级"],
	   [950100113, "猴子寺院M        120级---130级"],
	   [950100114, "猴子寺院N        130级---140级"],
	   [950100115, "猴子寺院O        140级---150级"],
	   [950100116, "猴子寺院P        150级---160级"],
	   [950100117, "猴子寺院Q        160级---170级"],
	   [950100118, "猴子寺院R        170级---180级"],
	   [950100119, "猴子寺院S        180级---190级"],
	   [950100120, "猴子寺院T        190级---200级"],
	   [950100121, "猴子寺院U        200级---210级"],
	   [950100122, "猴子寺院V        210级---220级"],
	   [950100123, "猴子寺院W        220级---230级"],
	   [950100124, "猴子寺院X        230级---240级"],
	   [950100125, "猴子寺院Y        240级---250级"],
	   [950100126, "猴子寺院Z        250级---255级"]

	          





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
			var selStr = "  #d 尊贵的会员 #b#h ##k您好！很高兴见到您， 我是寺院管理员《达奥》#k#n#b\r\n";
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