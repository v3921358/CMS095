var status = 0;
var victim;
function start() {
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else if (mode == 0) {
        status--;
    } else {
        cm.dispose();
        return;
    }
    if (status == 1) {
        cm.sendYesNo("你真的确定要銀婚吗?");
    } else if (status == 2) {
        if (!cm.isLeader()) { // 不是队长
            cm.sendOk("你想銀婚吗？那就请你的组队长和我講话吧…");
            cm.dispose();
            return;
        } else if (cm.getParty().getMembers().size() != 2) { //判斷组队成员是否达到2人。
            cm.sendNext("组队人员不能超过兩个人。不是你们兩个人結婚吗？");
            cm.dispose();
            return;
        } else if (cm.getPlayer().getMarriageId() == 0) { //查看玩家是否已经結婚。
            cm.sendNext("你还滅有結婚，不能完成銀婚。");
            cm.dispose();
            return;
        } else if (cm.MarrageChecking3()) { //检测组队中是否已经結婚
            cm.sendNext("你的组队中，有人並非你的伴侶。\r\n请检查后再试。");
            cm.dispose();
            return;
        } else if (cm.getParty() == null) {
            cm.sendNext("组队后在來找我");
            cm.dispose();
            return;
        } else if (!cm.isLeader()) {
            cm.sendNext("请让队长与我对话");
            cm.dispose();
            return;
        } else if (cm.MarrageChecking4()) {
            cm.sendNext("我不支持同性結婚。所以不让你们进去");
            cm.dispose();
            return;
        }

        var gender = cm.getPlayer().getGender();
        var mapId = cm.getPlayer().getMapId();
        var next = true;
        var party = cm.getPlayer().getParty().getMembers();
        var it = party.iterator();
        while (it.hasNext()) {
            var cPlayer = it.next();
            victim = cm.getPlayer().getMap().getCharacterById(cPlayer.getId());
            if (victim.getId() != cm.getPlayer().getId() && (party.size() > 2 || victim == null || victim.getMarriageId() > 0 || victim.getMapId() != mapId || victim.getGender() == gender)) {
                next = true;
                break;
            }
        }
        if (!next) {
            cm.sendNext("请确认您跟您的伴侶在这裏。");
            cm.dispose();
            return;
        }

        if (!cm.haveItem(5251015, 1)) {
            cm.sendNext("请检查你是否有#t5251015#");
            cm.dispose();
            return;
        }
        if (cm.MarrageChecking6() && !cm.canHold()) {
            cm.sendNext("请检测你和你队友的包包，有沒有足够的空位。");
            cm.dispose();
            return;
        }
        //cm.warpParty(700000200, 0);
        cm.gainItem(5251015, -1);
        cm.YinMarry();
        cm.dispose();
    } else {
        cm.dispose();
    }

}
