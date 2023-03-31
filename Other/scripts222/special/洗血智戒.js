

var status = -1;
var sel;
var mod;
function start() {
    cm.sendSimple("\t嗨，我是洗血智戒#v1112442#(智力+200)领取专员有什么可以帮忙的？？\r\n\r\n#rPs.#k等级大于10级的非新手、非法师角色可以领取 。\r\n\r\n#b#L0#LV10领取洗血智戒#l"/*+" \r\n\r\n#b#L1#LV50领取#l \r\n\r\n#b#L2#LV120领取#l #k"*/);
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
            if (!cm.canHoldByTypea(1, 1)) {
                cm.sendOk("请确认背包是否已经满了。");
                cm.dispose();
                return;
            }
            if (cm.getPlayer().getJob() == 0 || cm.getPlayer().getJob() == 1 || cm.getPlayer().getJob() == 1000 || cm.getPlayer().getJob() == 2000 || cm.getPlayer().getJob() == 2001 || cm.getPlayer().getJob() == 3000 || cm.getPlayer().getJob() == 3001 || cm.getPlayer().getJob() == 2002) {
                cm.sendOk("新手职业不能领取。");
                cm.dispose();
                return;
            }
            if (cm.getPlayer().getJob() >= 200 && cm.getPlayer().getJob() <= 232) {
                cm.sendOk("法师职业不能领取。");
                cm.dispose();
                return;
            }
            if (cm.getPlayer().getJob() >= 1200 && cm.getPlayer().getJob() <= 1212) {
                cm.sendOk("法师职业不能领取。");
                cm.dispose();
                return;
            }
            if (cm.getPlayer().getJob() >= 2200 && cm.getPlayer().getJob() <= 2218) {
                cm.sendOk("法师职业不能领取。");
                cm.dispose();
                return;
            }
            if (cm.getPlayer().getJob() >= 3200 && cm.getPlayer().getJob() <= 3212) {
                cm.sendOk("法师职业不能领取。");
                cm.dispose();
                return;
            }

            if (cm.getPlayer().getLevel() < 10) {
                cm.sendOk("你的等级不足10级");
                cm.dispose();
                return;
            }


            if (cm.getPlayer().getBossLogS("智戒10") >= 1) {
                cm.sendOk("该角色已经领取过。");
                cm.dispose();
                return;
            }


            cm.getPlayer().setBossLog("智戒10");
            cm.getxixuezhijie(1112442, 200);
            cm.sendOk("领取成功。");
            cm.dispose();
            return;

        } 
    }
}
