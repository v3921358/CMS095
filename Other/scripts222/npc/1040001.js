/* Author: aaroncsn (MapleSea Like)
 NPC Name: 		Mike
 Map(s): 		Warning Street: Perion Dungeon Entrance(106000300)
 Description: 		Unknown
 */

function start() {
    if (cm.getQuestStatus(28177) == 1 && !cm.haveItem(4032479)) { //too lazy
        cm.gainItem(4032479, 1);
    }
    if (cm.getQuestStatus(2048)==1){
        cm.sendOk("1、#b上古卷轴被地下宫殿的月牙牛魔王抢走了#k。\r\n2、#b冰块是妖精族的东西，现在企鹅王身上也有。\r\n#k3、#b火焰羽毛在赤龙身上有。\r\n#k#l…………");
        cm.dispose();
        return;
    }
    cm.sendNext("穿过这裏，你会发现維多利亞島的地牢。请小心…");
    cm.dispose();
}