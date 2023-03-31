/* Author: Xterminator
	NPC Name: 		Rain
	Map(s): 		Maple Road : Amherst (1010000)
	Description: 		Talks about Amherst
*/
var status = 0;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 1)
	status++;
    else
	status--;
	
    if (status == 0) {
	cm.sendNext("这是一个名叫阿巴姆斯特的小鎮，位于枫樹島東北部。你知道枫島是初學者，对吧？我很高兴这裏周圍只有微弱的怪物。");
    } else if (status == 1) {
	cm.sendNextPrev("如果你想变得更强壮，那就去一个有海港的巴斯佩裏。騎上这艘巨輪，前往称为“維多利亞”島的地方。与这个小島相比，它的大小无可比擬。");
    } else if (status == 2) {
	cm.sendPrev("在维多利亚岛，你可以选择你的工作。它叫PiLon……？我听说那里有一个光秃秃荒凉的城镇，那里住着勇士们。高地……那是一个什么样的地方？ ");
    } else if (status == 3) {
	cm.dispose();
    }
}