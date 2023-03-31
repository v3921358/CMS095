var status = 0;

function start() {
    status = -1;
    action(1, 0, 0)
}

function action(d, h, l) {
    if (status == 0 && d == 0) {
        cm.dispose();
        return
    }
    if (d == 1) {
        status++
    } else {
        status--
    }
    if (status == 0) {
        em = cm.getEventManager("PQS_7");
        var a = "";
        if (em.getProperty("gate") == "2" || em.getProperty("gate") == "1") {
            // a += "以下活动(GM可见)\r\n";
            //a += "#L2#进入跑旗活动7点赛场#l\r\n";
            a += "#L3#进入跑旗活动9点赛场#l\r\n"
        } else {
            a += "还没到跑旗活动时间哦!#r每天晚9:00#k！！你是来找我聊天的吗?"
        }
        cm.sendSimple(a)
    } else {
        if (status == 1) {
            switch (l) {
                case 1:
                    cm.dispose();
                    cm.openNpc(9000277);
                    break;
                case 2:
                    cm.dispose();
                    cm.warp(932200003, 0);
                    break;
                case 3:
                    cm.dispose();
                    cm.warp(932200001, 0);
                    break;
                default:
                    cm.dispose();
                    break
            }
        }
    }

}