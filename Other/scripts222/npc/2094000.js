

function action(mode, type, selection) {
    cm.removeAll(4001117);
    cm.removeAll(4001120);
    cm.removeAll(4001121);
    cm.removeAll(4001122);
    if (cm.getPlayer().getParty() == null || !cm.isLeader()) {
        cm.sendOk("请队长來跟我談话。");
    } else {
        var party = cm.getPlayer().getParty().getMembers();
        var mapId = cm.getPlayer().getMapId();
        var channel = cm.getChannelNumber();
        var next = true;
        var size = 0;
        var it = party.iterator();
        while (it.hasNext()) {
            var cPlayer = it.next();
            if (!cPlayer.isOnline()) {
                cm.sendOk("你队伍有队友不在線上。");
                cm.dispose();
                return;
            }
            if (cPlayer.isCs()) {
                cm.sendOk("你队伍有队友在商城");
                cm.dispose();
                return;
            }
            if (cPlayer.isHp0()) {
                cm.sendOk("你队伍有队友死亡");
                cm.dispose();
                return;
            }
            if (cPlayer.getChannel() != channel) {
                cm.sendOk("你队伍有队友不在当前頻道。");
                cm.dispose();
                return;
            }
            if (cPlayer.getMapid() != mapId) {
                cm.sendOk("你队伍有队友不在当前地图。");
                cm.dispose();
                return;
            }
            var ccPlayer = cm.getPlayer().getMap().getCharacterById(cPlayer.getId());
            if (ccPlayer == null || ccPlayer.getLevel() < 60 || ccPlayer.getLevel() > 250) {
                next = false;
                break;
            }
            size += (ccPlayer.isGM() ? 4 : 1);
        }
        if (next && size >= 1) {
            var em = cm.getEventManager("Pirate");
            if (em == null) {
                cm.sendOk("此任務正在建設当中。");
            } else {
                var prop = em.getProperty("state");
                if (prop.equals("0") || prop == null) {
                    em.startInstance(cm.getPlayer().getParty(), cm.getPlayer().getMap(), 120);
                } else {
                    cm.sendOk("老海盜船任務裏面已经有人了，请稍等！");
                }
            }
        } else {
            cm.sendOk("请确认你和你的組员是否达到任務进入要求。");
        }
    }
    cm.dispose();
}