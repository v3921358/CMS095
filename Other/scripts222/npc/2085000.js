var status = 0;
var itemId = new Array(2047000, 2047002, 2047100, 2047102, 2047200, 2047201, 2047202, 2047203);
var set = 0;

function start() {
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 0) {
        cm.dispose();
        return;
    }
    if (mode == 0) {
        cm.dispose();
    }
    if (mode == 1) {
        status++;
    }
    if (status == 0) {
        cm.sendSimple("Ҫ����Ҳ�ܷ�����ͺ��ˡ��������Է��и���Ȥ�Ļ�������ȥ������ľ��Ĵ峤��\r\n#b#L0#�������u�顣");
    } else if (status == 1) {
        var selStr = "Ҫ��ǿ�������;öȵ�װ������Ҫ���u�顣����Ҫʲô��";
        for (var i = 0; i < itemId.length; i++) {
            selStr += "\r\n#b#L" + i + "#��ȡ#t" + itemId[i] + "#��#l";
        }
			selStr += "\r\n#b#L888#��ȡ#v1012370##z1012370#��#l";
        cm.sendSimple(selStr);
    } else if (status == 2) {
		if (selection == 888) {
			if (cm.getPlayer().getBossLogS("����ħ") >= 20) {
				if (cm.getPlayer().getBossLogS("��Ҷ���") > 0){
					cm.sendOk("���Ѿ���ȡ��һ���ˣ�");
					cm.dispose();
					return;
				}
				cm.gainItem(1012370,1);
				cm.setBossLog("��Ҷ���");
				cm.dispose();
			} else {
				cm.sendOk("�㻹û�����20������ħ��������㵱ǰ�����"+cm.getPlayer().getBossLogS("����ħ")+"��");
				cm.dispose();
				return;
			}
		}
        if (cm.haveItem(4001401, 10)) {
            cm.gainItem(4001401, -10);
            cm.gainItem(itemId[selection], 1);
            cm.sendOk("��ϲ��ɹ���ȡ��#b#t" + itemId[selection] + "##k���������Ҫ�Ļ��������Ұɣ�");
        } else {
            cm.sendNext("Ҫ����ȡ#b#t" + itemId[selection] + "##k����Ҫ10��#b#t4001401##k����Ҫ���u��Ļ�����ȥ����#b#o8300007##k���Ѽ�#b#t4001401##k��");
        }
        cm.dispose();
    }
}