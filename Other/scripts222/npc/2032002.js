/* 
 * 
 * Adobis's Mission I: Unknown Dead Mine (280010000)
 * 
 * Zakum PQ NPC (the one and only)
 */

var status = -1;
var selectedType;
var scrolls;

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        status--;
    }

    if (status == 0) {
        cm.sendSimple("���N�ӣ����Ѽ����ˆ᣿#b\r\n#L0#���V�ґ�ԓ��ʲ�N��#l\r\n#L1#�ѽ��Ѽ�������Ʒ��#l\r\n#L2#��Ҫ�x�_�@�e��#l");
    } else if (status == 1) {
        selectedType = selection;
        if (selection == 0) {
            cm.sendNext("���˽��������ħ��ǰ�ã������ռ�����Ҫ�ĺ��Ĳ��ϡ�");
            cm.safeDispose();
        } else if (selection == 1) {
            if (!cm.haveItem(4001018)) { //documents
                cm.sendNext("Ո�o��#b#t4001018##k�x�x��");
                cm.safeDispose();
            } else {
                if (!cm.haveItem(4001015, 30)) { //documents
                    cm.sendYesNo("����������??\r\n���˴_�������õ����Ո�ȿճ����g");
                    scrolls = false;
                } else {
                    cm.sendYesNo("����������??\r\n���˴_�������õ����Ո�ȿճ����g");
                    scrolls = true;
                }
            }
        } else if (selection == 2) {
            cm.sendYesNo("��_��Ҫ�˳���������ǽM��L��һ�����x�_�Mꠣ����N�@��΄վ͟o���^�m��ȥ���Ƿ�Q���˳���");
        }
    } else if (status == 2) {
        var eim = cm.getEventInstance();
        if (selectedType == 1) {

            cm.gainItem(4001018, -1);
            if (scrolls) {
                cm.gainItem(4001015, -30);
            }
            //give items/exp
            cm.givePartyItems(4031061, 1);
            if (scrolls) {
                cm.givePartyItems(2030007, 5);
                cm.givePartyExp(20000);
            } else {
                cm.givePartyExp(12000);
            }

            //clear PQ

            if (eim != null) {
                eim.finishPQ();
            }
            cm.dispose();
        } else if (selectedType == 2) {
            if (eim != null) {
                if (cm.isLeader())
                    eim.disbandParty();
                else
                    eim.leftParty(cm.getChar());
            } else {
                cm.warp(280090000, 0);
            }
            cm.dispose();
        }
    }
}