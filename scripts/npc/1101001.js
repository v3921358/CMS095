 /* 
	NPC Name: 		Divine Bird
	Map(s): 		Erev
	Description: 		Buff
*/

function start() {
    //cm.useItem(2022458);
    status = -1;
    action(1, 0, 0);
    
    //cm.sendOk("不要停止訓练，这个世界需要你來守护。");
}

function action(mode, type, selection) {
    cm.dispose();
    cm.openNpc(1101001, "自由转职");
}