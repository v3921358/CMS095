/* Author: aaroncsn(MapleSea Like)
	NPC Name: 		Small Trunk
	Map(s): 		Victoria Road : Top of the Tree That Grew (101010103)
	Description: 		A tree
*/

function start() {
	cm.sendOk("树皮的芳香使我的鼻子发痒。");
if(!cm.haveItem(4032136,1)&&cm.getQuestStatus(20716)==1){
        cm.gainItem(4032136,1)
cm.sendOk("在木纹中间采集到了#t4032142#。");
            cm.gainItem(4032142, 1);
}
	cm.dispose();
	}