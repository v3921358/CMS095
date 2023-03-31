/* Author: aaroncsn(MapleSea Like)
	NPC Name: 		Assistant Cheng
	Map(s): 		Ludibrium: Toy Factory Zone 1(220020000)
	Description: 		Unknown
*/

function start(){
	if(cm.isQuestActive(3239)){
		if(cm.haveItem(4031092,10)){
			cm.sendNext("多亏了你，玩具厂又正常运转了。我很高兴你能来帮我们。我们一直在密切关注我们所有的派对，所以别担心。那我得回去工作了!");
			cm.gainExp(500);
			cm.gainItem(4031092,-10);
			cm.forceCompleteQuest(3239);
			cm.dispose();
		}else{
			cm.warp(922000000);
			cm.dispose();
		}
		
	}else{
		cm.sendNext("多亏了你，玩具厂又正常运转了。我很高兴你能来帮我们。我们一直在密切关注我们所有的派对，所以别担心。那我得回去工作了!");
		cm.dispose();
	}

}