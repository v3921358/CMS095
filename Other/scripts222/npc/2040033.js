/*
	Neru - Ludibrium : Ludibrium Pet Walkway (220000006)
*/

var status = 0;

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
	if (cm.haveItem(4031128)) {
	    cm.sendNext("唉，那是我哥哥的信！可能是因为我认为我不工作和什麽事而責罵我……嗯？啊……你听從了我哥哥的建议，訓练你的宠物，到这裏來了，嗯？好極了！既然你努力工作，我会提高你的亲密程度与你的宠物。");
	} else {
	    cm.sendOk("我哥哥告訴我要照顧宠物障礙課程，但是…因为我离他那么远，我情不自禁地想四处走动……呵呵，因为我看不見他，不妨冷靜几分鐘。");
	    cm.dispose();
	}
    } else if (status == 1) {
	if (cm.getPlayer().getPet(0) == null) {
	    cm.sendNextPrev("你真的带着你的宠物來了吗？这些障礙是宠物的。沒有它你在这裏幹嘛？离开这裏！");
	    cm.dispose();
	} else {
	    cm.gainItem(4031128, -1);
	    cm.gainClosenessAll(4);
	    cm.sendNextPrev("你怎麽认为？你不觉得你和你的宠物更亲近了吗？如果你有时间，在这个障礙課程上訓练你的宠物…当然，在我哥哥的允許下。");
	    cm.dispose();
	}
    }
}