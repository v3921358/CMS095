var status = 0;
var qChars = new Array ("Q1: 在魔法密林见不到的NPC是谁?#洪先生#后街吉姆#露尔#鲁克#3",
    "Q1: 在魔法密林见不到的NPC是谁?#特奥#瑞恩#露尔#希娜#3",
    "Q1: 不是阿尔法队员的是谁?#比特中士#查理中士#霍古斯曹长军曹#伊吉上等兵#1",    
	"Q1: 被怪物攻击时特别的异常状态没有被正确说明的是哪一个？#虚弱 — 移动速度降低#封印 - 不能使用技能#黑暗 - 命中下降#诅咒 - 减少经验#1",
	"Q1: 冒险岛最初遇见的NPC是谁？#冒险岛运营员#瑞恩#皮奥#希娜#4",
	"Q1:怪与所掉落战利品是正确对应的一组?#大幽灵-幽灵头带#蝙蝠-蝙蝠翅膀#煤泥 - 粘糊糊的泡泡#猪 - 丝带#2");
var qItems = new Array( "Q2: 在神秘岛(天空之城)没有出现的怪物是哪一个？#黑鳄鱼#小石球#艾利杰#黑色飞狮#1",
    "Q2: 在彩虹岛看不到的怪物是哪一个?#蓝蜗牛#红蜗牛#花蘑菇#猪猪#4",
    "Q2: 唤醒麦吉不需要的材料是哪一个?#星石#月石#玻璃鞋#妖精之翼#4",
    "Q2: 在天空之城不能看到的NPC是哪一个？#索非亚#妖精佛罗拉#查里中士#马丁#1",
    "Q2: 在金银岛的勇士部落不能看到的NPC是谁？#圣诞老人#杜宜#查里中士#易德#4",
    "Q2: 冒险岛中从1级到2级升级所需经验是多少？#10#15#20#30#2");
var qMobs = new Array("Q3:绿蘑菇，木妖，蓝水灵，斧木妖，三眼章鱼中级别最高的怪物是哪一个？#绿蘑菇#木妖#蓝水灵#斧木妖#4",
    "Q3: 射手村的玛雅为了治好自己的病让你给她的物品是哪一个？#超级药水#万能疗伤药#奇怪的药#感冒灵#3",
    "Q3: 在金银岛的废弃都市不能见到的NPC是谁?#洪先生#后街吉姆#休咪#鲁克#4",
    "Q3: 要求级别最高的任务是哪一个？#借来莎丽的镜子#传递信件#造访射手村#阿尔卡斯特和黑暗水晶#4",
    "Q3: 在神秘岛冰峰雪域看不见的NPC是谁？#保姆#珀斯上尉#杰德#流浪炼金术士#1",
    "Q3: 在废弃都市能够见到一个离家的少年阿列克斯，他的父亲是谁?#提坦长老#斯坦长老#希梅尔长老#汉斯#2");
var qQuests = new Array("Q4:在冒险岛中登场的药和功效错误连线的是哪一个？#蓝色药水 - 恢复 100 MP#活力药水 - 恢复 300 MP#清晨之露 - 恢复3000MP#红色药水 - 恢复 50 HP#3",
    "Q4: 金银岛没有的村落? #彩虹村#勇士部落#林中之城#射手村#1",
    "Q4: 怪物和怪物爆出的物品不正确联系的是哪一个？#漂漂猪- 蝴蝶结#僵尸蘑菇 - 道符#绿色蜗牛 - 绿色蜗牛壳#食人花——食人花的叶子#4",
    "Q4: 在金银岛的明珠港不能看到的NPC是谁?#坤#赛恩#佩森#智慧爷爷#2",
    "Q4: 下面哪个职业不是二转中出现的职业？#剑客#巫师#牧师#枪战士#2",
    "Q4: 为了进行次转职收集好30个黑玉后转职教官会给你的物品是什么？#转职教官的信#英雄证书#英雄的勋章#介绍信#2");
var qTowns = new Array( "Q5:跟宠物没有关系NPC是谁？ #比休斯#妖精-玛丽#科洛伊#巴特斯#1",
    "Q5: 在金银岛和蚂蚁洞看不到的怪物是哪一个？#石球#刺蘑菇#僵尸蘑菇#蝙蝠#1",
    "Q5: 下面中能飞行的怪物是什么？#石球#刺蘑菇#僵尸蘑菇#巫婆#4",
    "Q5: 在冒险岛中登场的药和功效正确连线的是哪一个？#白色药水 - 回复 250 HP#超级药水 — HP400恢复#红色药水 - 回复 100 HP#披萨 — HP400恢复#4",
    "Q5: 能够反复执行的任务是哪一个？#秀兹的兴趣#寻找《上古魔书》#克林的记忆#艾温的玻璃鞋#4",
    "Q5: 根据不同职业的第一次转职必须条件被正确叙述的是哪一个?#战士 30力量#弓箭手25敏捷#法师30智力#飞侠15敏捷#2");
var correctAnswer = 0;

function start() {
    if (cm.haveItem(4031058, 1)) {
        cm.sendOk("#h #,你已经有了 #t4031058# 不要让废我时间.");
        cm.dispose();
    }
    if (!cm.haveItem(4031057, 1)) {
        cm.sendOk("#h #,你没有 #t4031057# 不要让废我时间.");
        cm.dispose();
    }
    if (!(cm.haveItem(4031058, 1))) {
        cm.sendNext("欢迎光临 #h #, 我是 #p2030006#.\r\n看来你已经走了很远到达了这个阶段.");
    }
}

function action(mode, type, selection) {
    if (mode == -1)
        cm.dispose();
    else {
        if (mode == 0) {
            cm.sendOk("下次再见.");
            cm.dispose();
            return;
        }
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 1)
            cm.sendNextPrev("#h #, 如果你给我 #b黑暗水晶#k 我将会让你试着回答5个问题,若您5个问题都答对您将得到 #v4031058# #b智慧项鍊#k.");
        else if (status == 2) {
            if (!cm.haveItem(4005004)) {
                cm.sendOk("#h #, 你没有 #b黑暗水晶#k");
                cm.dispose();
            } else {
                cm.gainItem(4005004, -1);
                cm.sendSimple("测验开始 #b接受挑战吧!#k.\r\n\r\n" + getQuestion(qChars[cm.getDoubleFloor(cm.getDoubleRandom() * qChars.length)]));
                status = 2;
            }
        } else if (status == 3) {
            if (selection == correctAnswer)
                cm.sendOk("#h # 你答对了.\n准备答下一题??");
            else {
                cm.sendOk("你答错了的答案!.\r\n很抱歉你必须在给我一个 #b黑暗水晶#k 才可以再挑战!");
                cm.dispose();
            }
        } else if (status == 4)
            cm.sendSimple("测验开始 #b接受挑战吧!#k.\r\n\r\n" + getQuestion(qItems[cm.getDoubleFloor(cm.getDoubleRandom() * qItems.length)]));
        else if (status == 5) {
            if (selection == correctAnswer)
                cm.sendOk("#h # 你答对了.\n准备答下一题??");
            else {
                cm.sendOk("你答错了的答案!.\r\n很抱歉你必须在给我一个 #b黑暗水晶#k 才可以再挑战!");
                cm.dispose();
            }
        } else if (status == 6) {
            cm.sendSimple("测验开始 #b接受挑战吧!#k.\r\n\r\n" + getQuestion(qMobs[cm.getDoubleFloor(cm.getDoubleRandom() * qMobs.length)]));
            status = 6;
        } else if (status == 7) {
            if (selection == correctAnswer)
                cm.sendOk("#h # 你答对了.\n准备答下一题??");
            else {
                cm.sendOk("你答错了的答案!.\r\n很抱歉你必须在给我一个 #b黑暗水晶#k 才可以再挑战!");
                cm.dispose();
            }
        } else if (status == 8)
            cm.sendSimple("测验开始 #b接受挑战吧!#k.\r\n\r\n" + getQuestion(qQuests[cm.getDoubleFloor(cm.getDoubleRandom() * qQuests.length)]));
        else if (status == 9) {
            if (selection == correctAnswer) {
                cm.sendOk("#h # 你答对了.\n准备答下一题??");
                status = 9;
            } else {
                cm.sendOk("你答错了的答案!.\r\n很抱歉你必须在给我一个 #b黑暗水晶#k 才可以再挑战!");
                cm.dispose();
            }
        } else if (status == 10) {
            cm.sendSimple("最后一个问题.\r\n测验开始 #b接受挑战吧!#k.\r\n\r\n" + getQuestion(qTowns[cm.getDoubleFloor(cm.getDoubleRandom() * qTowns.length)]));
            status = 10;
        } else if (status == 11) {
            if (selection == correctAnswer) {
				cm.gainItem(4031057, -1);
                cm.gainItem(4031058, 1);
                cm.warp(211000001, 0);
                cm.sendOk("恭喜 #h #, 你太强大了.\r\n拿着这个 #v4031058# 去找你的转职教官吧!.");
                cm.dispose();
            } else {
                cm.sendOk("太可惜了,差一题就可以通关了!! 多多加油><.\r\n很抱歉你必须在给我一个 #b黑暗水晶#k 才可以再挑战!");
                cm.dispose();
            }
        }
    }
}

function getQuestion(qSet) {
    var q = qSet.split("#");
    var qLine = q[0] + "\r\n\r\n#L0#" + q[1] + "#l\r\n#L1#" + q[2] + "#l\r\n#L2#" + q[3] + "#l\r\n#L3#" + q[4] + "#l";
    correctAnswer = parseInt(q[5], 10);
    correctAnswer--;
    return qLine;
}