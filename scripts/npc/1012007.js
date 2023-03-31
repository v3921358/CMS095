/* Author: Xterminator
	NPC Name: 		Trainer Frod
	Map(s): 		Victoria Road : Pet-Walking Road (100000202)
	Description: 		Pet Trainer
*/
var status = 0;
var xx;
function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (status >= 0 && mode == 0) {
	cm.dispose();
	return;
    }
    if (mode == 1)
	status++;
    else
	status--;
    if (status == 0) {
	if (cm.haveItem(4031035)) {
	    cm.sendNext("嗯，这是我哥哥的信！也许骂我想我不工作和东西......嗯？唉唉......你跟着我哥哥的意见和训练有素的宠物，站起身来这里，是吧？不错！既然你辛辛苦苦来到这里，我会提高你的亲密水平与您的宠物。");
		xx = 1;
	} else if (cm.haveItem(5460000) && cm.haveItem(4031922, 8)) {
	    cm.sendNext("哎呦~ 是我那哥哥叫你来的吧！他又怪我不工作贪玩了吧？嗯？啊~ 你按我哥说的，一路上带着宠物一起上来的吗？好！你这么辛苦地上来了，我可以让你带三只宠物。");
		xx = 2;
	} else {
	    cm.sendOk("我哥哥让我管理这些训练宠物用的障碍设备，不过他看不到我，我想出去玩会儿。呼呼...反正我哥看不到，我先玩一会儿~");
	    cm.dispose();
	}
    } else if (status == 1) {
	if (cm.getPlayer().getPet(0) == null) {
	    cm.sendNextPrev("嗯...你真的没有带宠物来？快离开这儿！");
	} 
	if (xx == 1) {
	    cm.gainItem(4031035, -1);
	    cm.gainClosenessAll(5);
	    cm.sendOk("你怎麽看？难道你不认为你已经得到了你的宠物更接近？如果你有时间，再训练你的宠物在这个障碍......当然当然，我的兄弟的许可。");
		cm.dispose();
	}
	else if (xx == 2) {
		cm.gainItem(5460000, -1);
		cm.gainItem(4031922, -8);
		cm.teachSkill(8, 1, 1);
		cm.teachSkill(10000018, 1, 1);
		cm.teachSkill(20000024, 1, 1);
		cm.teachSkill(20011024, 1, 1);
		cm.sendOk("怎么样？带三只宠物是不是很炫酷？。");
		cm.dispose();
    }
}
}