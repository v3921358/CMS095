/*
	Description: 	Quest - Hungry Baby Dragon
*/

var status = -1;

function start(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
	if (status == 3) {
	    qm.sendNext("*你怎麽能拒絕餵你的龙？这是虐待兒童！");
	    qm.dispose();
	    return;
	}
	status--;
    }
    if (status == 0) {
	qm.sendNext("喲，主人。现在我已经向你展示了我能做什麽，輪到你了。證明給我……你可以找到食物！我餓死了。你现在可以利用我的力量，所以你必須照顧我。");
    } else if (status == 1) {
	qm.forceStartQuest();
	qm.sendNextPrevS("唉，我还是不明白发生了什麽，但是我不能让像你这樣的可憐的小家夥挨餓，对吧？食物，你说呢？你想吃什麽？", 2);
    } else if (status == 2) {
	qm.sendNextPrev("海，我剛剛出生几分鐘前。我怎麽知道我吃什麽？我只知道我是一條龙…我是你的龙。你是我的主人。你必須善待我！");
    } else if (status == 3) {
	qm.askAcceptDecline("我想我们應該一起學習。但我餓了。主人，我想吃東西。記住，我是个嬰兒！我馬上就要哭了！");
    } else if (status == 4) {
	qm.forceStartQuest();
	qm.sendOkS("#b(嬰兒龙看起來非常饑餓。你必須餵他。也許你爸爸可以給你一些关于龙吃的建议。)", 2);
    }
}

function end(mode, type, selection) {
    if (mode == 1) {
	status++;
    } else {
	status--;
    }
    if (status == 0) {
	qm.sendNext("是什麽，埃文？你想知道龙吃什麽吗？你为什麽…呵呵？你找到龙了？");
    } else if (status == 1) {
	qm.sendNextS("#b(你給爸爸看米爾)#k", 2);
    } else if (status == 2) {
	qm.sendNextPrev("唉…那是一條龙？你确定它不是一只大蜥蜴吗？嗯，所有的生命都是寶貴的，所以我想你可以保留它。");
    } else if (status == 3) {
	qm.sendNextS("#b(爸爸似乎不相信米爾是一條龙。嗯，他很小。如果他听到米爾的话，爸爸会相信吗？)", 2);
    } else if (status == 4) {
	qm.sendNextPrev("如果这是一條真正的龙，那就太危險了。如果它放火怎麽辦？我不认为这是一條龙，但也許我们應該请一个冒險家來杀它，以防萬一。");
    } else if (status == 5) {
	qm.sendNextS("#b(什麽？杀了米爾但他沒有做错什麽 !!)", 2);
    } else if (status == 6) {
	qm.sendNextPrev("当然，我敢肯定这不是一條龙。龙只出现在奧西裏亞大陸的小葉上。");
    } else if (status == 7) {
	qm.sendNextS("#b哈。。。哈。。。你肯定是对的！我懷疑他是一條龙。他可能只是一只蜥蜴！一定地！#k", 2);
    } else if (status == 8) {
	qm.sendNextPrev("是的，我很确定。这是一种奇怪的蜥蜴，但看起來並不危險。你可以保留它。 ");
    } else if (status == 9) {
	qm.sendNextS("#b(为了自己的安全，你最好不要让任何人知道米爾是一條龙。)#k", 2);
    } else if (status == 10) {
	qm.sendOk("哦，你说你在找東西餵蜥蜴？我不确定…让我考慮一下。");
    } else if (status == 11) {
	qm.gainExp(180);
	qm.forceCompleteQuest();
	qm.dispose();
    }
}