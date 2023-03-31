var status = -1;

function action(mode, type, selection) {
        if (cm.getMapId() == 240050100) {
                if (cm.isLeader() && cm.haveItem(4001087, 1) && cm.haveItem(4001088, 1) && cm.haveItem(4001089, 1) && cm.haveItem(4001090, 1) && cm.haveItem(4001091, 1)) {
                        cm.showEffect(true, "quest/party/clear");
                        cm.playSound(true, "Party1/Clear");
                        cm.gainItem(4001087, -1);
                        cm.gainItem(4001088, -1);
                        cm.gainItem(4001089, -1);
                        cm.gainItem(4001090, -1);
                        cm.gainItem(4001091, -1);
                        cm.gainItem(4001092, 1);
                        cm.dispose();
                        cm.sendOk("这是红色钥匙，请拿着它去右边里程碑继续进行任务。");
                } else {
                        cm.sendOk("请叫你的队长带着5种迷宫室钥匙来找我");
                        cm.dispose();
                }
        }
}

