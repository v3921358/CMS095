/* ==================
 脚本类型: NPC    
 脚本版权：一线海团队-维多
 =====================
 */
var wupdm = 4000194;//物品代码
var wupsl = 30;//物品数量
function start() {
    cm.sendYesNo("你确定你要使用"+wupsl+"个#v"+wupdm+"##z"+wupdm+"#进入#r通道#k？");
}

function action(mode, type, selection) {
    if (mode == 0) {
	cm.sendOk("恩... 看起来你并没有准备好。");
	cm.dispose();
	} else if (!cm.haveItem(wupdm, wupsl)) {
       cm.sendOk("你没有"+wupsl+"个#v"+wupdm+"##z"+wupdm+"#!");
       cm.dispose();
	} else {
	cm.gainItem(wupdm, -wupsl);
    cm.warp(701010322, "sp");	
    cm.dispose();
    }
}
