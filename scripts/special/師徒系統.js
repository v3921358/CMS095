

var status = -1;
var sel;
var mod;
var moda;
function start() {

    var sg = cm.getPlayer().getStLog() < 1 ? "你还沒有拜师。" : cm.getCharacterNameById(cm.getPlayer().getStLogid(cm.getPlayer().getId())) != null ? "你的师傅是：" + cm.getCharacterNameById(cm.getPlayer().getStLogid(cm.getPlayer().getId())) : "你的师傅已经刪除角色。";
    var st = cm.getPlayer().getStChrNameLog(cm.getPlayer().getId()) != "" ? "你的徒弟有：" + cm.getPlayer().getStChrNameLog(cm.getPlayer().getId()) : "你沒有徒弟。";
    cm.sendSimple("我是师徒系統服務员，有什么可以为你服務？\r\n\r\n" + "#b"+sg + "\r\n\r\n" + st + "#k\r\n\r\n#b#L0#我要拜师#l#k#k\r\n\r\n#b#L1#师傅領取奖励#l#k#k");
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
            if (cm.getPlayer().getLevel() < 10) {
                cm.sendOk("你的等級不足10等，无法拜师。");
                cm.dispose();
                return;
            }
            if (cm.getPlayer().getLevel() >= 70) {
                cm.sendOk("你的等級超过70等，无法拜师。");
                cm.dispose();
                return;
            }

            if (cm.getPlayer().getStLog() >= 1) {
                cm.sendOk("你已经有师傅无法重複拜师");
                cm.dispose();
                return;
            }
            cm.sendGetText("请輸入要拜师的角色名字。");
            moda = 100;
        } else if (sel == 1) {
            cm.sendSimple("我是师徒系統服務员，一共可以領取10次奖励，你可以領取奖励如下\r\n\r\n" +
                    "第一次 800枫葉點數\r\n\r\n" +
                    "第二次 #i5220000#30个\r\n\r\n" +
                    "第三次 #i4031408#10个\r\n\r\n" +
                    "第四次 #i2450000#2个\r\n\r\n" +
                    "第五次 #i2049100#5张\r\n\r\n" +
                    "第六次 枫葉點數3000點\r\n\r\n" +
                    "第七次 #i2340000#5张\r\n\r\n" +
                    "第八次 #i5062001#30个\r\n\r\n" +
                    "第九次 #i2049302#2个#i2049400#1个#i5064000#1个\r\n\r\n" +
                    "第十次 #i1142033#永久\r\n\r\n" +
                    "\r\n\r\n" +
                    "#b#L200#师傅領取奖励#l#k#k");
        }
    } else if (status == 1) {
        if (moda == 100) {
            var text = cm.getText();
            if (text === null || text === "") {
                cm.sendOk("並未輸入任何內容.");
                cm.dispose();
                return;
            }
            if (cm.getPlayer().getLevel() < 10) {
                cm.sendOk("你的等級不足10等，无法拜师。");
                cm.dispose();
                return;
            }
            if (cm.getPlayer().getLevel() >= 70) {
                cm.sendOk("你的等級超过70等，无法拜师。");
                cm.dispose();
                return;
            }

            if (cm.getPlayer().getStLog() >= 1) {
                cm.sendOk("你已经有师傅无法重複拜师");
                cm.dispose();
                return;
            }
            var id = cm.getCharacterIdByName(text);
            if (id == -1) {
                cm.sendOk("你輸入的名字不存在。");
                cm.dispose();
                return;
            }
            if (cm.getCharacterByNameLevel(text) < 120) {
                cm.sendOk("对方等級不足120等，无法拜师。");
                cm.dispose();
                return;
            }

            cm.getPlayer().setStLog(id);
            cm.sendOk("拜师成功。");
            cm.dispose();
            return;
        } else if (selection == 200) {
            if (!cm.canHoldByTypea(1, 1)) {
                cm.sendOk("请确认背包是否已经满了。");
                cm.dispose();
                return;
            }
            if (!cm.canHoldByTypea(2, 1)) {
                cm.sendOk("请确认背包是否已经满了。");
                cm.dispose();
                return;
            }
            if (!cm.canHoldByTypea(3, 1)) {
                cm.sendOk("请确认背包是否已经满了。");
                cm.dispose();
                return;
            }
            if (!cm.canHoldByTypea(4, 1)) {
                cm.sendOk("请确认背包是否已经满了。");
                cm.dispose();
                return;
            }
            if (!cm.canHoldByTypea(5, 1)) {
                cm.sendOk("请确认背包是否已经满了。");
                cm.dispose();
                return;
            }
            if (cm.getPlayer().getStjf(cm.getPlayer().getId()) < 1) {
                cm.sendOk("目前你还沒有徒弟出师。");
                cm.dispose();
                return;
            }
            if (cm.getPlayer().getBossLogS("师傅奖励") >= 10) {
                cm.sendOk("你已经領取过10次师傅奖励。");
                cm.dispose();
                return;
            }
            if (cm.getPlayer().getBossLogS("师傅奖励") == 0) {
                cm.getPlayer().setBossLog("师傅奖励");
                cm.getPlayer().updateStjfLog(cm.getPlayer().getId(), cm.getPlayer().getStjf(cm.getPlayer().getId()) - 1);
                cm.getPlayer().modifyCSPoints(2, 800, true);
                cm.sendOk("成功領取第1次师傅奖励。");
                cm.dispose();
                return;
            }
            if (cm.getPlayer().getBossLogS("师傅奖励") == 1) {
                cm.getPlayer().setBossLog("师傅奖励");
                cm.getPlayer().updateStjfLog(cm.getPlayer().getId(), cm.getPlayer().getStjf(cm.getPlayer().getId()) - 1);
                cm.gainItem(5220000, 30);
                cm.sendOk("成功領取第2次师傅奖励。");
                cm.dispose();
                return;
            }
            if (cm.getPlayer().getBossLogS("师傅奖励") == 2) {
                cm.getPlayer().setBossLog("师傅奖励");
                cm.getPlayer().updateStjfLog(cm.getPlayer().getId(), cm.getPlayer().getStjf(cm.getPlayer().getId()) - 1);
                cm.gainItem(4031408, 10);
                cm.sendOk("成功領取第3次师傅奖励。");
                cm.dispose();
                return;
            }
            if (cm.getPlayer().getBossLogS("师傅奖励") == 3) {
                cm.getPlayer().setBossLog("师傅奖励");
                cm.getPlayer().updateStjfLog(cm.getPlayer().getId(), cm.getPlayer().getStjf(cm.getPlayer().getId()) - 1);
                cm.gainItem(2450000, 2);
                cm.sendOk("成功領取第4次师傅奖励。");
                cm.dispose();
                return;
            }
            if (cm.getPlayer().getBossLogS("师傅奖励") == 4) {
                cm.getPlayer().setBossLog("师傅奖励");
                cm.getPlayer().updateStjfLog(cm.getPlayer().getId(), cm.getPlayer().getStjf(cm.getPlayer().getId()) - 1);
                cm.gainItem(2049100, 5);
                cm.sendOk("成功領取第5次师傅奖励。");
                cm.dispose();
                return;
            }
            if (cm.getPlayer().getBossLogS("师傅奖励") == 5) {
                cm.getPlayer().setBossLog("师傅奖励");
                cm.getPlayer().updateStjfLog(cm.getPlayer().getId(), cm.getPlayer().getStjf(cm.getPlayer().getId()) - 1);
                cm.getPlayer().modifyCSPoints(2, 3000, true);
                cm.sendOk("成功領取第6次师傅奖励。");
                cm.dispose();
                return;
            }
            if (cm.getPlayer().getBossLogS("师傅奖励") == 6) {
                cm.getPlayer().setBossLog("师傅奖励");
                cm.getPlayer().updateStjfLog(cm.getPlayer().getId(), cm.getPlayer().getStjf(cm.getPlayer().getId()) - 1);
                cm.gainItem(5062001, 30);
                cm.sendOk("成功領取第7次师傅奖励。");
                cm.dispose();
                return;
            }
            if (cm.getPlayer().getBossLogS("师傅奖励") == 7) {
                cm.getPlayer().setBossLog("师傅奖励");
                cm.getPlayer().updateStjfLog(cm.getPlayer().getId(), cm.getPlayer().getStjf(cm.getPlayer().getId()) - 1);
                cm.gainItem(2340000, 5);
                cm.sendOk("成功領取第8次师傅奖励。");
                cm.dispose();
                return;
            }
            if (cm.getPlayer().getBossLogS("师傅奖励") == 8) {
                cm.getPlayer().setBossLog("师傅奖励");
                cm.getPlayer().updateStjfLog(cm.getPlayer().getId(), cm.getPlayer().getStjf(cm.getPlayer().getId()) - 1);
                cm.gainItem(2049302, 2);
		cm.gainItem(2049400, 1);
                cm.gainItem(5064000, 1);
                cm.sendOk("成功領取第9次师傅奖励。");
                cm.dispose();
                return;
            }
            if (cm.getPlayer().getBossLogS("师傅奖励") == 9) {
                cm.getPlayer().setBossLog("师傅奖励");
                cm.getPlayer().updateStjfLog(cm.getPlayer().getId(), cm.getPlayer().getStjf(cm.getPlayer().getId()) - 1);
                cm.gainItem(1142033, 1);
                cm.sendOk("成功領取第10次师傅奖励。");
                cm.dispose();
                return;
            }

        }
    }
}
