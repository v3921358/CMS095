/*
	NPC Name: 		Han the Broker
	Map(s): 		Magatia
	Description: 	Quest - Test from the Head of Alcadno Society
*/

var status = -1;
var oreArray;

function start(mode, type, selection) {}

function end(mode, type, selection) {
    qm.sendNext("请等一下我去拿个東西，以帮助您更容易通过卡帕萊特協会长的考验。");
    qm.forceCompleteQuest();
    qm.dispose();
}