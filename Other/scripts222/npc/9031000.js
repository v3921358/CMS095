var status = -1;

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        if (status == 0) {
            cm.dispose();
        }
        status--;
    }
    if (status == 0) {
        cm.sendNext("���˽⌣�I���g��Ԓ���ҁ��ε��f��һ�¡����@�������У�һ����#b��ˎ���ɵV���b���������Ʒ�����������g#k5�����ˡ�������ߌ��I���g��Ч�����҂����˅f��Ҏ��ÿ���˿��ԌW��2�N���I���g�������@��Ҏ����������x��W��#r2�N���I���g#k��");
    } else if (status == 1) {
        cm.sendPrev("#b - ��ˎ + �����g - �ɵV + �b������ - �ɵV + �Ʒ����#k\r\n\r\n�������@3�N�������x��Ո�x��K�W���Լ�ϲ�g�ļ��g��");
        cm.dispose();
    }
}