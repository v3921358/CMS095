

var status = -1;
var sel;
var mod;
function start() {
    cm.sendSimple("我是公会地图傳送员，你要进入公会地图吗？  \r\n\r\n#b#L0#进入公会地图#l#k#k");
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
            if (cm.getPlayer().getGuildId() <= 0) {
                cm.sendOk("你沒有公会");
                cm.dispose();
                return;
            }
            if (cm.getMapGuilds_Instanced(cm.getPlayer().getGuildId()) == null) {
                cm.createInstanceMap(999990010, cm.getPlayer().getGuildId());
                 cm.sendOk("公会地图初始化成功，请再试。");
                cm.dispose();
                return;
            }
            if (cm.getMapGuilds_Instanced(cm.getPlayer().getGuildId()) == null) {
                cm.sendOk("地图为空");
                cm.dispose();
                return;
            }
            var map = cm.getMapGuilds_Instanced(cm.getPlayer().getGuildId());
            cm.getPlayer().changeMap(map, map.getPortal(0));
            cm.dispose();
            return;
        }
    }
}
