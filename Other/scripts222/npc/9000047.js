

var status = -1;
var sel;
var mod;
function start() {
    cm.sendSimple("我是公會地圖傳送員，你要進入公會地圖嗎？  \r\n\r\n#b#L0#進入公會地圖#l#k#k");
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
                cm.sendOk("你沒有公會");
                cm.dispose();
                return;
            }
            if (cm.getMapGuilds_Instanced(cm.getPlayer().getGuildId()) == null) {
                cm.createInstanceMap(999990010, cm.getPlayer().getGuildId());
                 cm.sendOk("公會地圖初始化成功，請再試。");
                cm.dispose();
                return;
            }
            if (cm.getMapGuilds_Instanced(cm.getPlayer().getGuildId()) == null) {
                cm.sendOk("地圖為空");
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
