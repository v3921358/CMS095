var status = -1;
var sel;
var mod;

function start() {

    cm.warp(103050200,0);
    cm.dispose();
}

function action(mode, type, selection) {
    if (mode == 0) {
        cm.dispose();
        return;
    } else {
        status++;
    }
    if (status == 0) {
        sel = selection;
        if (sel == 0) {
            if (!cm.canHold()) {
                cm.sendOk("您的背包已满。");
                cm.dispose();
                return;
            }
            if (cm.getPlayer().getLevel() >= 20 && cm.getPlayer().getJob() == 400 && cm.getPlayer().getSubcategory() > 0) {
                cm.getPlayer().changeJob(430);
                cm.gainItem(1342000, 1);
                cm.sendOk("转职成功。");
                cm.dispose();
                return;
            } else {
                cm.sendOk("我还有什么可以为你帮助的吗？");
                cm.dispose();
                return;
            }

        } else if (sel == 1) {
            if (cm.getPlayer().getLevel() >= 30 && cm.getPlayer().getJob() == 430 && cm.getPlayer().getSubcategory() > 0) {
                cm.getPlayer().changeJob(431);
                cm.sendOk("转职成功。");
                cm.dispose();
                return;
            } else {
                cm.sendOk("我还有什么可以为你帮助的吗？");
                cm.dispose();
                return;
            }
        } else if (sel == 2) {
            if (cm.getPlayer().getLevel() >= 55 && cm.getPlayer().getJob() == 431 && cm.getPlayer().getSubcategory() > 0) {
                cm.getPlayer().changeJob(432);
                cm.sendOk("转职成功。");
                cm.dispose();
                return;
            } else {
                cm.sendOk("我还有什么可以为你帮助的吗？");
                cm.dispose();
                return;
            }
        } else if (sel == 3) {
            if (cm.getPlayer().getLevel() >= 70 && cm.getPlayer().getJob() == 432 && cm.getPlayer().getSubcategory() > 0) {
                cm.getPlayer().changeJob(433);
                cm.sendOk("转职成功。");
                cm.dispose();
                return;
            } else {
                cm.sendOk("我还有什么可以为你帮助的吗？");
                cm.dispose();
                return;
            }
        } else if (sel == 4) {
            if (cm.getPlayer().getLevel() >= 120 && cm.getPlayer().getJob() == 433 && cm.getPlayer().getSubcategory() > 0) {
                cm.getPlayer().changeJob(434);
                cm.teachSkill(4341000, 0, 10);
                cm.sendOk("转职成功。");
                cm.dispose();
                return;
            } else {
                cm.sendOk("我还有什么可以为你帮助的吗？");
                cm.dispose();
                return;
            }
        }
    }
}