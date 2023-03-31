/*
	任務: 通往未來之門
	描述: 与阿夕亞的相遇
*/

var status = -1;

function start(mode, type, selection) {
    if (mode == -1) {
        qm.dispose();
        return;
    } else if (mode == 0) {
        if (status == 41) {
            qm.sendNext("了解了吗，这是攸关逆奧之城命运的问題。我身为不受#b阿卡夏的咒語#k束縛的人民，会用生命试着阻止國家的滅亡。");
            qm.dispose();
            return;
        }
        status--;
    } else {
        status++;
    }
    switch (status) {
    case 0:
        if (qm.getQuestCustomData() != null) { // if (qm.getQuestCustomData().equals("readHistory")) {
            qm.sendSimple("枫之谷世界的居民，接受考验的結果如何？#b \n\r #L0#获得了时间旅行者沙漏！#l \n\r #L1#找到了逆奧之流。（商城道具）#l");
            status = 99;
        } else {
            qm.sendNext("我是#p9120025#，來自#m802000101#为逆奧之城感到惋惜的人。");
        }
        break;
    case 1:
        qm.sendNextPrev("（#p9120025#瞇起眼睛）");
        break;
    case 2:
        qm.sendNextPrev("你真的长大了。\r\n<格里特你说的是正确的…>");
        break;
    case 3:
        qm.sendNextPrev("我來到枫之谷的时候，当时你还小，现在已成长为一位堂堂正正的战士了。对一直以來守护着你的我而言…沒有比这个更令人高兴的事了…\r\n（#p9120025#的眼眶泛紅）");
        break;
    case 4:
        qm.sendNextPrev("…\r\n你不斷的磨练必杀技术，以堅强意志造就出不屈不撓的精神，又是力量的求道者，也曾经和枫之谷世界黑暗軍队交战过，这樣的你一定可以…");
        break;
    case 5:
        qm.sendNextPrev("─ 对不起，我剛才说话的口氣太沒礼貌了。");
        break;
    case 6:
        qm.sendNextPrev("我的使命是鑒別有能力的人，或推测出可能擁有能力的人。");
        break;
    case 7:
        qm.sendNextPrev("不僅如此，導引逆奧之城的救世主，使得逆奧之城能够免于崩壞的危機，更是我的天命。");
        break;
    case 8:
        qm.sendNextPrev("这个地方叫做#m802000101#，是逆奧之城最远且最边界的地方。");
        break;
    case 9:
        qm.sendNextPrev("我要告訴你逆奧之城的真相。");
        break;
    case 10:
        qm.sendNextPrev("…");
        break;
    case 11:
        qm.sendNextPrev("一百年后，逆奧之城会消失。");
        break;
    case 12:
        qm.sendNextPrev("会完完全全地從这个世界上消失。我已預見，逆奧之城将被突然從时空裂縫出现的#b时空扭曲#k吞沒。");
        break;
    case 13:
        qm.sendNextPrev("古代的逆奧之城曾经十分盛行高度的魔法文明。我就是生在那个时代，自小學習魔法技能的逆奧之城魔法师。");
        break;
    case 14:
        qm.sendNextPrev("当时的逆奧之城，与瑪加提亞的交流持续加深。虽然在现今（你所身处的时代），科學的力量不斷抬頭，但魔法研究在过去才是主流。");
        break;
    case 15:
        qm.sendNextPrev("而瑪加提亞可謂魔法研究的先驅。\n我与当地的魔法师相遇，在研究进入尾声之时，我得到了…#b长生不老的力量#k。");
        break;
    case 16:
        qm.sendNextPrev("这是还沒有人练成过，最困难的魔法。为了逆奧之城，当时的我盡力拼了。\n但过沒多久，永生不死就成了禁忌、异端，且不被人所接受。过去的同伴、自己的國家和整个世界开始与我为敵，当时无处可逃的我，决定要找到世界的终點，因此在逆奧之城的內陸隱居下來了。");
        break;
    case 17:
        qm.sendNextPrev("但末日的到來比我想像中还要快。");
        break;
    case 18:
        qm.sendNextPrev("当逆奧之城被巨大的#b时空扭曲#k吞沒的瞬间，我使出时空跳躍的魔法，漂流在消失后的世界里，然后发现了一件事。");
        break;
    case 19:
        qm.sendNextPrev("我发现了逆奧之城的真相。");
        break;
    case 20:
        qm.sendNextPrev("逆奧之城的歷史全都紀錄在一本書里。");
        break;
    case 21:
        qm.sendNextPrev("而将此書所記載的內容具现化，就是逆奧之城的歷史。");
        break;
    case 22:
        qm.sendNextPrev("原來将逆奧之城的人们所有的行动都用超次元技术記錄的#b阿卡夏 - 編年史#k也实際存在…。");
        break;
    case 23:
        qm.sendNextPrev("我的人生就是被这本書弄得亂七八糟的吗。\n我察觉到我的憤怒了。");
        break;
    case 24:
        qm.sendNextPrev("在动盪的世界里，我将#b阿卡夏 - 編年史#k拿在手里讀着。");
        break;
    case 25:
        qm.sendNextPrev("我注意到一件事。");
        break;
    case 26:
        qm.sendNextPrev("#b阿卡夏 - 編年史#k里沒有記載关于我的內容。");
        break;
    case 27:
        qm.sendNextPrev("我是不存在于过去的。");
        break;
    case 28:
        qm.sendNextPrev("不知道是不是因为与逆奧之城以外的人密切接觸，或是因此变得长生不老后，才從#b阿卡夏 - 編年史#k的詛咒#k解脫的。");
        break;
    case 29:
        qm.sendNextPrev("同时我也得知#b阿卡夏- 編年史#k的內容屬于流动式，且可加以改編。");
        break;
    case 30:
        qm.sendNextPrev("如果是这樣，我想要回到过去，重新改寫歷史，将原本会出现在歷史未來的#b时空扭曲#k，封印在时空的边界，让逆奧之城免于滅亡的命运。");
        break;
    case 31:
        qm.sendNextPrev("不过虽说是为了逆奧之城。但改寫歷史实在是狂妄的行为。\n若歷史能改寫，那么人在未來的存在也将遭到剝奪。");
        break;
    case 32:
        qm.sendNextPrev("所以我的結论是，只改寫毀滅前的逆奧之城歷史。");
        break;
    case 33:
        qm.sendNextPrev("改寫歷史並非一次就可以完成的工作。歷史本身具有自我修正的能力，即使我做了修改，也将回到本來的面貌。");
        break;
    case 34:
        qm.sendNextPrev("我以#m802000101#为根据地，至今仍持续監控接近毀滅前的逆奧之城。");
        break;
    case 35:
        qm.sendNextPrev("接着之后…");
        break;
    case 36:
        qm.sendNextPrev("将会出现一波我前所未見的歷史修正浪潮。");
        break;
    case 37:
        qm.sendNextPrev("我知道这是攸关逆奧之城的大事，原本應該让逆奧之城人民自己解决的。");
        break;
    case 38:
        qm.sendNextPrev("但逆奧之城人民因为#b阿卡夏的咒語#k而无法改变歷史。");
        break;
    case 39:
        qm.sendNextPrev("如果说这一切都是为了逆奧之城的未來，是太狂妄了些。");
        break;
    case 40:
        qm.sendNextPrev("（#p9120025#恭敬地请求）");
        break;
    case 41:
        qm.sendYesNo("你沒有受到#b阿卡夏的咒語#k束縛，我希望能借助你的力量。");
        break;
    case 42:
        //qm.forceStartQuest();
        qm.setQuestCustomData("readHistory");
        qm.sendNextPrev("謝謝你，來自冒險世界的勇者（#p9120025#安息了）");
        break;
    case 43:
        qm.sendNextPrev("相信擁有强大力量的你，即使在未來的时代也能够克服战斗。");
        break;
    case 44:
        qm.sendNextPrev("只是…让我見識你真正的力量吧。");
        break;
    case 45:
        qm.sendNextPrev("我從几千年前开始就一直在尋找勇者。\n因为，在逆奧之城就要毀滅的世界里，不僅有高度发展的科學文明，連敵人都擁有难以想像的强大力量。");
        break;
    case 46:
        qm.sendNextPrev("为了尋找真正的强者，我一直在进行一项計劃。");
        break;
    case 47:
        qm.sendNextPrev("逆奧之城因为目前的危機而发生了时空扭曲，從超空间开了个裂縫，与这个世界发生了关联。");
        break;
    case 48:
        qm.sendNextPrev("这个神秘的出入口，恰好連接到了日本，並且与其歷史產生了联系。導致了日本的过去和现在，也出现过許多奇怪的现象。");
        break;
    case 49:
        qm.sendNextPrev("而正是因为如此，你可以通过出现在日本的水晶，來到这神秘的卡姆那。");
        break;
    case 50:
        qm.sendNextPrev("在卡姆那，可以获得前往逆奧之城的过去与未來的不可思议的力量。");
        break;
    case 51:
        qm.sendNextPrev("逆奧之城的歷史中，枫葉古城是一个特殊时期。");
        break;
    case 52:
        qm.sendNextPrev("它像極了日本的战國，可能是因为兩者在平行世界的共鳴。忍者、武士、妖怪，似乎不时地穿行在这2个空间。");
        break;
    case 53:
        qm.sendNextPrev("这段久远的故事，也包含了太多值得體验和挑战的內容。但是目前你不能回，当務之急是要对逆奧之城未來的歷史进行拯救！");
        break;
    case 54:
        qm.sendNextPrev("噢，也許你不是很明白。所謂未來的歷史，对现在的你來说，是未來，对我，它可能已经成为歷史……一个悲劇的歷史……");
        break;
    case 55:
        qm.sendNextPrev("因为它在未來面臨消失……我希望你能帮我改变这一切，我无法离开，所以希望你帮我拯救逆奧之城的未來！");
        break;
    case 56:
        qm.sendNextPrev("你的能力很强，但请让我在最后見證你真正的力量。");
        break;
    case 57:
        qm.sendNextPrev("要穿梭时空，需要时间的力量。你必須打倒时间神殿的怪物，並将其證明#b#t04000340# 300个#b、#b#t04000342# 1个、#b#t04000343# 1个#b带來。");
        qm.dispose();
        break;
    case 100:
        if (selection == 0) {
            if (qm.haveItem(4000343, 1) && qm.haveItem(4000340, 300) && qm.haveItem(4000342, 1)) {
                status = 119;
                qm.sendNextPrev("輝煌。。");
            } else {
                qm.sendNext("这些时间旅行者的沙漏數量，还不足以證明你的能力，无法穿越时空。你必須要能获得更多的證明。");
                qm.dispose();
            }
        } else {
            if (qm.haveItem(5252002, 1)) {
                status = 129;
                qm.sendNextPrev("輝煌。。");
            } else {
                status = 109;
                qm.sendNext("要是沒有可以穿梭时空的能量和證明，是无法前往未來的。因为时空跳轉需要特殊的能量，而且未來力量超强的敵人，沒有实力实在很危險。");
            }
        }
        break;
    case 110:
        qm.sendNextPrev("打倒时间神殿的怪物后，再拿證明过來。");
        break;
    case 111:
        qm.sendPrev("听说在商城可以买得到特殊的證明…給你更加自由的選擇和機会。竄改歷史的波濤已演变到那个地步了。不能猶豫。照你的意思。");
        qm.dispose();
        break;
    case 120:
        qm.sendNextPrev("现在我要接收你的道具了。");
        break;
    case 124:
        qm.gainItem(4000343, -1);
        qm.gainItem(4000340, -300);
        qm.gainItem(4000342, -1);
        qm.forceStartQuest();
        qm.dispose();
        break;
    case 130:
        qm.sendNextPrev("现在我要接收你的道具了。.");
        break;
    case 131:
        qm.gainItem(5252002, -1);
        qm.forceStartQuest();
        qm.dispose();
        break;
    default:
        qm.dispose();
        break;
    }
}

function end(mode, type, selection) {
}