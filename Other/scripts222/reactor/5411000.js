function act() {
    try {
        rm.changeMusic("Bgm09/TimeAttack");
        rm.spawnMonster(9420513, -146, 225);
        rm.mapMessage(5, "幽灵船长被召唤了出来");
    } catch (e) {
        rm.mapMessage(5, "出错拉: " + e);
    }
}