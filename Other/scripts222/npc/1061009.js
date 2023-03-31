/*
 ���¸Č���̹��_��by:Kodan
 */

var msg = "";

function start() {
    action(1, 0, 0);
}

function action(mode, type, selection) {
    var nextmap1 = cm.getMapFactory().getMap(910540200);
    var nextmap2 = cm.getMapFactory().getMap(910540100);
    var nextmap3 = cm.getMapFactory().getMap(910540300);
    var nextmap4 = cm.getMapFactory().getMap(910540400);
    var nextmap5 = cm.getMapFactory().getMap(910540500);

    if (cm.getPlayer().getLevel() >= 70) {
        if (cm.canHold(4031057)) {
            if (!(cm.haveItem(4031057))) {
                if (nextmap1.mobCount() > 0 && nextmap1.playerCount() == 0) {
                    nextmap1.killAllMonsters(true);
                } else if (nextmap2.mobCount() > 0 && nextmap2.playerCount() == 0) {
                    nextmap2.killAllMonsters(true);
                } else if (nextmap3.mobCount() > 0 && nextmap3.playerCount() == 0) {
                    nextmap3.killAllMonsters(true);
                } else if (nextmap4.mobCount() > 0 && nextmap4.playerCount() == 0) {
                    nextmap4.killAllMonsters(true);
                } else if (nextmap5.mobCount() > 0 && nextmap5.playerCount() == 0) {
                    nextmap5.killAllMonsters(true);
                }

                if (cm.getPlayer().getMapId() == 105030500) {
                    if (cm.getJob() == 210 || cm.getJob() == 220 || cm.getJob() == 230) {
                        if (nextmap1.playerCount() != 0) {
                            check();
                            return;
                        }
                        cm.warp(910540200, 0);
                        cm.spawnMobOnMap(9001001, 1, -276, -3, 910540200);
                        cm.sendOk("����3ת������������ʼ!!");
                    } else if (cm.getJob() == 110 || cm.getJob() == 120 || cm.getJob() == 130 || cm.getJob() == 2110) {
                        if (nextmap2.playerCount() != 0) {
                            check();
                            return;
                        }
                        cm.warp(910540100, 0);
                        cm.spawnMobOnMap(9001000, 1, -276, -3, 910540100);
                        cm.sendOk("սʿ3ת������������ʼ!!");
                    } else if (cm.getJob() == 310 || cm.getJob() == 320) {
                        if (nextmap3.playerCount() != 0) {
                            check();
                            return;
                        }
                        cm.warp(910540300, 0);
                        cm.spawnMobOnMap(9001002, 1, -276, -3, 910540300);
                        cm.sendOk("������3ת������������ʼ!!");
                    } else if (cm.getJob() == 410 || cm.getJob() == 420 || cm.getJob() == 432) {
                        if (nextmap4.playerCount() != 0) {
                            check();
                            return;
                        }
                        cm.warp(910540400, 0);
                        cm.spawnMobOnMap(9001003, 1, -276, -3, 910540400);
                        cm.sendOk("����3ת������������ʼ!!");
                    } else if (cm.getJob() == 510 || cm.getJob() == 520 || cm.getJob() == 530) {
                        if (nextmap5.playerCount() != 0) {
                            check();
                            return;
                        }
                        cm.warp(910540500, 0);
                        cm.spawnMobOnMap(9001004, 1, -276, -3, 910540500);
                        cm.sendOk("���I3ת������������ʼ!!");
                    } else {
                        cm.sendOk("�]����������0.0");
                    }

                }
            } else {
                cm.sendOk("������Ѿ�����#t4031057#��");
            }
        } else {
            cm.sendOk("��ȷ���Ƿ������Ŀ��g��");
        }
    } else {
        cm.sendOk("�ȼ�������ȷ��");
    }
    cm.dispose();
}

function check() {
    msg = "������������ս��";
    cm.sendNext(msg);
    cm.dispose();
}