var itemSet = Array(
    Array(2049006,  1), //（奖励，代码，个数）
    Array(4002003,  2),
    Array(5062001,  3),
    Array(2049116,  1),
    Array(5220010,4)
);
var bosslist = Array(
    Array('阿卡伊勒', 8860000, 272030400, 'BossArkarium'),
    Array('希拉', 8870000, 262030300, 'BossHillah'),
    Array('森兰丸', 9410248, 807300110, 'BossRanmaru'),
    Array('浓姬', 9450022, 811000080, 'BossPrincess'),
    Array('麦格纳斯', 9303100, 922900800, 'BossMagnus'),
    Array('绯红四傻', Array(8900000, 8910000, 8920000, 8930000), 105200410, 'BossBelen')
)
var status = 0;
var killtime=1;//击杀次数要求
var finished=0;

function start() {
    action(1, 0, 0);
}

function action(mode, type, selection) {
    status++;
    if (mode == -1) {
        cm.dispose();
        return;
    } else if (mode == 0) {
        cm.sendOk("下次再来哦!.");
        cm.dispose();
        return;
    }
    if (status == 1) {
        var add = "这里可以领取BOSS击杀礼品，今日BOSS击杀情况：\r\n";
        for (var i = 0; i < bosslist.length; i++) {
            add+= cm.getBossLogD(bosslist[i][0])>=killtime?"#g":"#r"+ bosslist[i][0]+":"+cm.getBossLogD(bosslist[i][0])+"/"+killtime+"#l\r\n";
            if(cm.getBossLogD(bosslist[i][0])>=killtime){
                finished++;
            }
        }
        if(cm.getBossLogD("每日BOSS击杀奖励")>0){//判断是否已领取
            finished=0;
        }
        add+=finished>= bosslist.length?"#L1##b领取奖励#l#k\r\n\r\n":"#L0##r查看奖励#l#k\r\n\r\n"
        cm.sendSimple(add);
    } else if (status == 2) {
        var txt="奖励如下：\r\n\r\n";
        if(selection==1&&finished>= bosslist.length){//二次判断完成情况
            if (cm.getInventory(1).isFull(4)) {
                cm.sendOk("请保证 #b装备栏#k 至少有5个位置。");
                cm.dispose();
                return;
            } else if (cm.getInventory(2).isFull(4)) {
                cm.sendOk("请保证 #b消耗栏#k 至少有5个位置。");
                cm.dispose();
                return;
            } else if (cm.getInventory(3).isFull(4)) {
                cm.sendOk("请保证 #b设置栏#k 至少有5个位置。");
                cm.dispose();
                return;
            } else if (cm.getInventory(4).isFull(4)) {
                cm.sendOk("请保证 #b其他栏#k 至少有5个位置。");
                cm.dispose();
                return;
            } else if (cm.getInventory(5).isFull(4)) {
                cm.sendOk("请保证 #b特殊栏#k 至少有5个位置。");
                cm.dispose();
                return;
            }
            for (var i = 0; i < itemSet.length; i++) {
                cm.gainItem(itemSet[i][0],itemSet[i][1]);
                txt+="#v"+itemSet[i][0]+"##z"+itemSet[i][0]+"# X "+itemSet[i][1]+"\r\n";
            }
            txt+="已领取至背包\r\n";
            cm.setBossLog("每日BOSS击杀奖励");
        }else{
            for (var i = 0; i < itemSet.length; i++) {
                txt+="#v"+itemSet[i][0]+"##z"+itemSet[i][0]+"# X "+itemSet[i][1]+"\r\n";
            }
        }
        cm.sendOk(txt);
        //cm.sendYesNo(bdd);
    }
}