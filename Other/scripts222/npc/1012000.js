/* Dawnveil
    Cab
	Regular Cab in Victoria
    Made by Daenerys
*/
var status = 0;
var maps = Array(100000000, 101000000, 102000000, 103000000, 104000000, 105000000, 120000000);
var show;
var sCost;
var selectedMap = -1;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (status == 1 && mode == 0) {
	cm.dispose();
	return;
    } else if (status >= 2 && mode == 0) {
	cm.sendNext("这村庄也有很多东西可以逛喔，若想要去其它村庄或白色波浪码头的话请随时利用我们计程车喔~");
	cm.dispose();
	return;
    }
    if (mode == 1)
	status++;
    else
	status--;
    if (status == 0) {
	cm.sendNext("您好~！#p1012000#。想要往其他村庄安全又快速的移动吗？如果是这样，为了优先考量满足顾客，请使用#b#p1012000##k。特别免费！亲切的送你到想要到达的地方。");
    } else if (status == 1) {
	    var selStr = "请选择目的地.#b";
	    for (var i = 0; i < maps.length; i++) {
		if (maps[i] != cm.getMapId()) {
		selStr += "\r\n#L" + i + "##m" + maps[i] + "##l";
		}
	    }
	cm.sendSimple(selStr);
    } else if (status == 2) {
	cm.sendYesNo("这地方应该没有什么可以参观的了。确定要移动到#b#m" + maps[selection] + "##k村子吗?。");
	selectedMap = selection;
    } else if (status == 3) {
	cm.dispose();
	cm.warp(maps[selectedMap]);
    }
}