
/*
 Name:  潮流轉蛋機
 Place: 轉蛋屋
 */
var status = -1;

var requireItem = 5220040;

function action(mode, _type, selection) {
    if (mode == 1) {
        status++;
    } else {
        cm.dispose();
        return;
    }

    if (!cm.getPlayer().isGM()) {
        cm.sendOk("調试中");
        cm.dispose();
        return;
    }

    switch (status) {
        case 0:
            if (cm.getPlayer().getLevel() <= 29) {
                cm.sendOk("轉蛋機只能從30級开始使用。");
                status = -1;
                //cm.gainItem(5220000, 1);
                cm.dispose();
                break;
            } else {
                cm.sendYesNo("我是史萊姆轉蛋機，你只需要200GASH即可轉蛋，需要吗？\r\n#L0#點我轉蛋!#l\r\n" + (cm.getPlayer().isGM() ? "#L1#點我查看此轉蛋機內容!#l" : "") + "\r\n" + (cm.getPlayer().isGM() ? "#L2#點我更改此轉蛋機內容!(GM顯示功能)#l" : ""));
                break;
            }
        case 1:
            {
                if (selection == 1) {
                    cm.sendOk(cm.getGashapon().ShowItem(1));
                    cm.dispose();
                } else if (selection == 2) {
                    cm.sendNext(cm.getGashapon().ShowItem("GM"));
                } else if (selection == 0) {
                    if (cm.getPlayer().getAcash() > 150) {
                        var gashapon = cm.getGashapon();
                        if (gashapon != null) {
                            if (cm.canHold()) {
                                cm.getPlayer().modifyCSPoints(1, -200, true);
                                var gashaponItem = gashapon.generateReward();
                                var item = cm.addbyId_Gachapon(cm.getPlayer().getClient(), gashaponItem.getItemId(), 1);
                                if (gashaponItem != null) {
                                    if (gashaponItem.canShowMsg())
                                        cm.getGachaponMega("潮流轉蛋機", item);
                                    //status = -1;
                                    cm.sendOk("恭喜你轉到了#b#i" + gashaponItem.getItemId() + ":##k。");
                                    cm.dispose();

                                } else {
                                    cm.sendOk("轉蛋機維护中。");
                                    cm.dispose();
                                }
                            } else {
                                cm.sendOk("请确认你的物品栏位还有空间。");
                                cm.dispose();
                            }
                        } else {
                            cm.sendOk("轉蛋機尚未开放。");
                            cm.dispose();

                        }
                    } else {
                        cm.sendOk("很抱歉由于你沒有#b200GASH#k，所以不能轉蛋哦。");
                        cm.dispose();
                    }
                }
            }
            break;
        case 2:
            sel = selection;
            if (sel == 10000) {
                cm.sendGetText("请輸入您要新增的物品代碼。");
                status = 4;
                break;
            } else {
                cm.sendGetText("请輸入您要更改的機率。");
                break;
            }
        case 3:
            cm.getGashapon().ChangeChance(cm.getPlayer(), sel, cm.getText());
            cm.sendYesNo("您已順利調整機率!您是否要重載轉蛋機機率?\r\n(點選立即生效)");
            break;
        case 4:
            cm.processCommand("!reloadgashapon");
            cm.sendOk("已順利重載轉蛋機機率!");
            status = -1;
            break;
        case 5:
            itemid = cm.getText();
            cm.sendGetText("请輸入您要新增物品的機率。");
            break;
        case 6:
            chance = cm.getText();
            cm.sendGetText("请问你是否要让此物品上绿廣?(请填寫是或否!)");
            break;
        case 7:
            if (cm.getText() == "是") {
                msg = true;
            } else {
                msg = false;
            }
            cm.getGashapon().AddItem(cm.getPlayer(), itemid, chance, msg);
            cm.sendYesNo("您已順利新增轉蛋物品!您是否要重載轉蛋機?\r\n(點選立即生效)");
            break;
        case 8:
            cm.processCommand("!reloadgashapon");
            cm.sendOk("已順利重載轉蛋機機率!");
            status = -1;
            break;
        default:
            cm.dispose();
    }
}