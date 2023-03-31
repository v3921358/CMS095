/* 
 * Shawn, Victoria Road: Excavation Site<Camp> (101030104)
 * Guild Quest Info
 */

var status;
var selectedOption;

function start() {
    selectedOption = -1;
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 0 && status == 0) {
	cm.dispose();
	return;
    }
    if (mode == 1)
	status++;
    else
	status--;
    if (mode == 1 && status == 3) {
	status = 0;
    }
    if (status == 0) {
	if (cm.getQuestStatus(6201) == 1) {
	    var dd = cm.getEventManager("Relic");
	    if (dd != null) {
		dd.startInstance(cm.getPlayer());
	    } else {
                cm.sendOk("未知的错誤。");
	    }
	    cm.dispose();
	} else {
            var prompt = "\r\n#b#L0#威廉的古堡是什么地方?#l\r\n#b#L1##t4001024#?#l\r\n#b#L2#公会战守护战?#l\r\n#b#L3#已经沒有问題了。#l";
	    if (selectedOption == -1) {
                prompt = "\r\n我们公会联盟是從很久以前就开始，一直在努力解讀古代的遺跡'祖母绿碑'。得到結果是发现祖母绿碑記載者这里其实就是枫之谷古文明的发源处'威廉的古堡'。而且还了解到傳说中的寶石魯碧安就在'威廉的古堡'的遺跡中，但由于寶石魯碧安擁有神秘的力量﹐因此被'惡靈13'所霸佔了。为了奪回魯碧安公会联盟开始了公会守护战。" + prompt;
	    } else {
                prompt = "还有要问的嘛?" + prompt;
	    }
	    cm.sendSimple(prompt);
	}
    } else if (status == 1) {
	selectedOption = selection;
	if (selectedOption == 0) {
            cm.sendNext("'威廉的古堡'是曾经統治維多利亞島全境的古代文明发源地。在石人寺院或森林深处的神殿之類的古代建築物都是'威廉的古堡'的遺址。");
        } else if (selectedOption == 1) {
            cm.sendNext("#t4001024#是傳说中的能够使人永远年輕的寶石。听说擁有#t4001024#的人都滅亡了﹐也許'威廉的古堡'的滅亡也于此有关。");
	    status = -1;
        } else if (selectedOption == 2) {
            cm.sendNext("过去多次派勘到'威廉的古堡'。但是无人归还。所以我们这次决定集結眾人之力展开公会守护战。我相信你们这些一直在努力增强力量的公会。");
        } else if (selectedOption == 3) {
            cm.sendOk("是吗？若有什么问題，请隨时提出。");
	    cm.dispose();
        } else {
	    cm.dispose();
	}
    } else if (status == 2) {
	if (selectedOption == 0) {
            cm.sendNextPrev("'威廉的古堡'最后的王室威廉公爵﹐据说他非常聰明而又仁慈。但是在某一天突然滅亡了﹐其原因还沒弄清楚。");
        } else if (selectedOption == 2) {
            cm.sendNextPrev("这次公会守护战的目的是到'威廉的古堡'探險﹐並奪回#t4001024#。这个任務並不是靠强大的力量就能完成的。最重要的是要与同伴合作。");
        } else {
	    cm.dispose();
	}
    }
}