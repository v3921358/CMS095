var status = -1;

function action(mode, type, selection) {
    if (cm.isQuestActive(3900)) {
	cm.forceCompleteQuest(3900);
	cm.gainExp(300);
	cm.playerMessage(5, "我尝过绿洲的水。甘甜清爽的水帮我解了渴。");
    }else{
	cm.playerMessage(5, "绿洲的水一直都这么清澈。仔细看的话能清澈见底。");
}
    cm.dispose();
}