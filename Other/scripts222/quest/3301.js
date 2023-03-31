/**
	NPC Name: 		Han the Broker
	Map(s): 		Magatia
	Description: 	Quest - Test from the Head of Zenumist Society
*/

var status = -1;

function start(mode, type, selection) {
    qm.dispose();
}

function end(mode, type, selection) {
    qm.sendNext("请等一下我去拿个東西，以帮助您更容易通过蒙特鳩協会长的考验。");
    qm.forceCompleteQuest();
    qm.dispose();
}