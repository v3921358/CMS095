var status = -1;
var selectionLog = [];
function start(mode, type, selection) {
	qm.sendNext("�ǳ����x��");
	qm.forceCompleteQuest();
	qm.dispose();
}
function end(d, c, b) {
    if (status == 0 && d == 0) {
        qm.dispose();
        return;
    }
    d == 1 ? status++ : status--;
    selectionLog[status] = b;
    var a = -1;
    if (status <= a++) {
        qm.dispose();
    } else {
        if (status == a++) {
            if (qm.getMap().getNumMonsters() > 0) {
                qm.sendNext('���Ȱ���Χ�����ҵĹ��������������֮ǰ����ʲô���������˵����');
                qm.dispose();
                return;
            }
            qm.forceCompleteQuest();
            qm.gainExp(11659200);
            qm.sendNext('лл�������ҡ�������������������������Ƿ����Ҳ����ˣ����ܻ����������ص����ѡ��������˵������������ʲô��');
        } else {
            if (status === a++) {
                qm.sendNext('�������ת�氢��˹��\r\n');
            } else {
                if (status === a++) {
                    qm.sendNext('���С���������ֹ���������޷���ϣ��˹�ָ�ԭ״����Ҳ��û�а취�İ취��');
                } else {
                    if (status === a++) {
                        qm.dispose();
                    }
                }
            }
        }
    }
}