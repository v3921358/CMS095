var Message = new Array(
    "������������c����/����ֵ/�����M���c/�����cNPC,Ո�ڌ�Ԓ���@ea�Ϳ�����",
        "��ֹ�_��죬�[����죡��",
        "����bugՈ�؈�GM");

var setupTask;

function init() {
    scheduleNew();
}

function scheduleNew() {
    setupTask = em.schedule("start", 900000);
}

function cancelSchedule() {
	setupTask.cancel(false);
}

function start() {
    scheduleNew();
    em.broadcastYellowMsg("[��֮�Ȏ���] " + Message[Math.floor(Math.random() * Message.length)]);
}