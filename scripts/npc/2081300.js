/*  NPC : 瑞吉爾
 弓箭手 4轉 任務脚本
 地图代碼 (240010501)
 */

var status = -1;
var pass = false;

function start() {
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 0 && status == 0) {
        cm.dispose();
        return;
    }
    if (mode == 1)
        status++;
    else
        status--;

    if (status == 0) {
        if (!(cm.getJob() == 311 || cm.getJob() == 321)) {
            cm.sendOk("为什么你要見我??还有你想要问我关于什么事情??");
            cm.dispose();
            return;
        } else if (cm.getPlayer().getLevel() < 120) {
            cm.sendOk("你等級尚未到达120級.");
            cm.dispose();
            return;
        } else {
            if (cm.getQuestStatus(6924) == 2) {
                pass = true;
            }
            if (cm.getJob() == 311) {
                cm.sendSimple("恭喜你有資格4轉. \r\n请问你想4轉吗??\r\n#b#L0#我想成为箭神.#l\r\n#b#L1#像我想一下...#l");
            } else if (cm.getJob() == 321) {
                cm.sendSimple("恭喜你有資格4轉. \r\n请问你想4轉吗??\r\n#b#L0#我想成为神射手.#l\r\n#b#L1#像我想一下...#l");
            } else {
                cm.sendOk("好吧假如你想要4轉麻煩再來找我");
                cm.dispose();
                return;
            }
        }
    } else if (status == 1) {
        if (selection == 1) {
            cm.sendOk("好吧假如你想要4轉麻煩再來找我");
            cm.dispose();
            return;
        }
        // if (cm.getPlayerStat("RSP") > (cm.getPlayerStat("LVL") - 120) * 3) {
        //     cm.sendOk("你的技能點數还沒點完..");
        //     cm.dispose();
        //     return;
        // }
        if (pass) {
            cm.sendNext("即将四轉。");
        } else {
            if (!cm.haveItem(4031514) || !cm.haveItem(4031515)) {
                cm.sendOk("我需要#t4031514# x1 #t4031515# x1。");
                cm.dispose();
                return;
            } else {
                cm.sendNext("即将四轉。");
            }
        }
    } else if (status == 2) {
        if (cm.canHold(2280003)) {
            cm.gainItem(2280003, 1);
            if (cm.getJob() == 311) {
                cm.changeJob(312);
                cm.teachSkill(3120005, 0, 10);
                cm.teachSkill(3121007, 0, 10);
                cm.teachSkill(3121002, 0, 10);
                cm.gainItem(4031860, -1);
                cm.gainItem(4031861, -1);
                cm.sendNext("恭喜你转职为 #b箭神#k.我送你一些神秘小礼物^^");
            } else {
                cm.changeJob(322);
                cm.teachSkill(3221006, 0, 10);
                cm.teachSkill(3220004, 0, 10);
                cm.teachSkill(3221002, 0, 10);
                cm.gainItem(4031860, -1);
                cm.gainItem(4031861, -1);
                cm.sendNext("恭喜你转职为 #b神射手#k.我送你一些神秘小礼物^^");
            }
        } else {
            cm.sendOk("你沒有多的栏位请清空再來嘗试一次!");
            cm.dispose();
            return;
        }

    } else if (status == 3) {
        cm.sendNext("不要忘記了这一切都取决于你练了多少.");
    } else if (status == 4) {
        cm.sendNextPrev("我已你为榮.");
        cm.dispose();
    }
}