
var status = -1;

function start(mode, type, selection) {
    status++;
    if (mode != 1) {
        if (type == 1 && mode == 0)
            status -= 2;
        else {
            qm.sendNext("你不相信我？格雷爾，你把我氣瘋了!");
            qm.dispose();
            return;
        }
    }
    if (status == 0)
        qm.sendNext("我终于來了！*吸氣*啊，这一定是我呼吸的空氣。那一定是太陽！那是一棵樹！那是一株植物！那是一朵花！Woohahahaha！这太不可思议了！这比我想象中的世界要好得多，而我卻被困在雞蛋裏。和你。。。你是我的主人吗？嗯，我对你的印象不同。");
    if (status == 1)
        qm.PlayerToNpc("#b哇，它在说话!");
    if (status == 2)
        qm.sendNextPrev("我的主人很奇怪。我想我现在不能做任何事情了，因为協议已经完成了。唉，好的，很高兴見到你。我们会看到很多彼此。");
    if (status == 3)
        qm.PlayerToNpc("#b嗯？你什麽意思？我们会看到很多彼此？什麽協定？");
    if (status == 4)
        qm.sendNextPrev("你是什麽意思？你把我從蛋裏叫醒了。你是我的主人！所以，当然，你的責任是照顧我，訓练我，帮助我成为一條强壮的龙。很明顯！");
    if (status == 5)
        qm.PlayerToNpc("#b什麽？一條龙？你是龙？我不明白…为什麽我是你的主人？你在说什麽？");
    if (status == 6)
        qm.sendNextPrev("你在说什麽？你的精神与我的靈魂达成了契約！我们现在差不多是同一个人了。我真的需要解釋吗？結果，你成了我的主人。我们受到公約的約束。你不能改变主意…公約不能被打破.");
    if (status == 7)
        qm.PlayerToNpc("#b等等，等等。让我直说吧。你是说我別无選擇，只能帮助你？");
    if (status == 8)
        qm.sendNextPrev("尤尤普！嘿！脸怎么了？你……难道不想做我的主人吗？");
    if (status == 9)
        qm.PlayerToNpc("#b不。。。不是那樣…我只是不知道我是否准备好了宠物。");
    if (status == 10)
        qm.sendNextPrev("一个P- PET？你剛才叫我宠物吗？怎麽敢…为什麽，我是龙！世界上最强大的人！");
    if (status == 11)
        qm.PlayerToNpc("#b...#b(你懷疑地盯着他。他看起來像一只蜥蜴。一个弱小的人。)#k");
    if (status == 12)
        qm.sendAcceptDecline("你为什麽那樣看着我？看！看看我能用我的力量做什麽。准备好了吗？");
    if (status == 13) {
        qm.forceStartQuest();
        qm.sendNext("命令我杀戮#r#o1210100#s#k!现在就做！我会告訴你龙能打敗多快#o1210100#s!笨蛋，沖鋒!");
    }
    if (status == 14)
        qm.sendNextPrev("等一下！你分配你的AP了吗？我受到了我的主人和盧克的嚴重影響！如果你真的想看看我能做什麽，在你使用这个技能之前，分发你的AP和你的魔术师裝备。  !");
    if (status == 15) {
        qm.evanTutorial("UI/tutorial/evan/11/0", -1);
        qm.dispose();
    }
}

function end(mode, type, selection) {
    status++;
    if (mode != 1) {
        if (type == 1 && mode == 0)
            status -= 2;
        else {
            qm.dispose();
            return;
        }
    }
    if (status == 0)
        qm.sendOk("哈！你觉得怎么样？我的技术很棒，对吧？你可以尽可能多地使用它们。这就是和我签订协议的意思。这不是很神奇吗？");
    if (status == 1) {
        qm.forceCompleteQuest();
        qm.gainExp(1270);
        qm.getPlayer().gainSP(1, 0);
        qm.sendOk("哦…我饿极了。我出生后太快使用了我的能量…");
        qm.dispose();
    }
}