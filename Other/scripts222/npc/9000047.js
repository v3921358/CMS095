

var status = -1;
var sel;
var mod;
function start() {
    cm.sendSimple("���ǹ����؈D���͆T����Ҫ�M�빫���؈D�᣿  \r\n\r\n#b#L0#�M�빫���؈D#l#k#k");
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
                cm.sendOk("��]�й���");
                cm.dispose();
                return;
            }
            if (cm.getMapGuilds_Instanced(cm.getPlayer().getGuildId()) == null) {
                cm.createInstanceMap(999990010, cm.getPlayer().getGuildId());
                 cm.sendOk("�����؈D��ʼ���ɹ���Ո��ԇ��");
                cm.dispose();
                return;
            }
            if (cm.getMapGuilds_Instanced(cm.getPlayer().getGuildId()) == null) {
                cm.sendOk("�؈D���");
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
