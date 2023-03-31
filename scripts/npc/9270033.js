/* 	Engine room, bob
*/
var status = 0;
function start() {
    if (cm.haveItem(4000381)) {
        cm.sendOk("将#b#z4000381##k放在中间那扇门即可召唤船长!");
    } else {
        cm.warp(541010110);
    }
    cm.dispose();
    return;

}

function action(mode, type, selection) {
    

}