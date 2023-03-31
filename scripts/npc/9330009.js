var status = 0;
var questObj;

function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (mode == 0) {
            cm.dispose();
            return;
        }
        if (mode == 1) {
            status++;
        } else {
            status--;
        }
    }
    if (status == 0) {
        cm.sendGetText("请输入要接受的#r“任务名称！”#l#k\r\n #b（输入部分关键字即可，支持模糊搜索！”）#k")
        //  cm.sendOk(txt[0]);
    } else if (status == 1) {

        var questmap = cm.getPlayer().getAllWzQuest();
        var txt = "	#eHi~#b#h ##k，请选择要接取的任务，选择后会传送至NPC所在地图。\r\n\r\n";
        var index = 0
        questmap.forEach(function (quest) {
            questObj = quest;
            //cm.playerMessage(quest.getId());

            if (quest.canStart(cm.getPlayer(), quest.getNpcidByQuestid(quest.getId())) && /.*[\u4e00-\u9fa5]+.*$/.test(quest.getName()) && quest.getName().indexOf("[") == -1 && quest.getName().indexOf(cm.getText()) != -1) {
                txt += "#L" + quest.getNpcidByQuestid(quest.getId()) + "##d" + quest.getName() + "[" + quest.getId() + "]" + "[" + quest.getNpcidByQuestid(quest.getId()) + "]" + "#l  \r\n";
                index++;
            }

        });
        txt += " ";
        if (index > 0) {
            cm.sendOk(txt);
        } else {
            cm.sendOk("未查询到该任务！");
            cm.dispose();
            return;
        }


    } else if (status == 2) {

        cm.startWarpToNpc(selection, questObj.getId());
        cm.dispose();

    }
}