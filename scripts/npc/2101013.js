/* Author: aaroncsn(MapleSea Like)
	NPC Name: 		Karcasa
	Map(s): 		The Burning Sands: Tents of the Entertainers(260010600)
	Description: 		Warps to Victoria Island
*/
var towns = new Array(100000000,101000000,102000000,103000000,104000000);

function start() {
	status = -1;
	action(1, 0, 0);
}

function action(mode, type, selection) {
	if (mode == -1) {
		cm.dispose();
	} else {
	if (status == 0 && mode == 0) {
		cm.sendNext("是的……你害怕速度还是高度？你不能相信我的飛行技巧？相信我，我已经解决了所有的问題！");
		cm.dispose();
		return;
	}
	if (mode == 1)
		status++;
	else
		status--;
	if(status == 0){
		cm.sendAcceptDecline("我不知道你是怎么发现的，但是你來对了！对于那些在尼哈爾沙漠遊蕩和想家的人，我将直接飛往維多利亞島。別擔心飛艇——它只掉了一兩次！你不觉得幽閉恐懼癥是在那艘小船上长途飛行吗？你怎么认为？你願意接受这个直飛航班的報價吗？ ");
	} else if(status == 1){
		cm.sendAcceptDecline("请記住兩件事。第一，这條航線实際上是为了海外运輸，所以我不能保證你将在哪个城鎮着陸。第二，既然我把你放在这趟特殊的航班上，那就有點貴了。服務費是1萬枫蔽，有一班飛機即将起飛。你感兴趣吗？ ");
	} else if(status == 2){
		cm.sendNext("好的，准备起飛~");
	} else if(status == 3){
		if(cm.getMeso() >= 10000){
			cm.gainMeso(-10000);
			cm.warp(towns[cm.getDoubleFloor(cm.getDoubleRandom() * towns.length)]);
		} else{
			cm.sendNextPrev("你沒有足够的枫葉蔽。");
			cm.dispose();
			}
		}
	}
}