/*
 create by PPMS
 **/
 var status = -1;
 var selectionLog = [];
 var selects = 0;
 var ccx = "#fEffect/CharacterEff/1082312/0/0#"; //长彩星
 var 五角星 = "#fUI/UIWindow.img/UserList/Expedition/icon14#";
 var hot = "#fUI/CashShop.img/CSEffect/hot/0#";
 var 正方箭头 = "#fUI/Basic/BtHide3/mouseOver/0#";
 var 感叹号 = "#fUI/UIWindow/Quest/icon0#";
 var 圆形 = "#fUI/UIWindow/Quest/icon3/6#";
 var rn = "\r\n\r\n"; // 换行
 var open = true;
 //配置文件名称
 var PQname = [""];
 //记录次数名称
 var PQLog = ["麦格纳斯[普通]"];
 //开始的地图
 var startmap = 910000000;
 //等级限制
 var minLevel = [200, 200];
 var maxLevel = [255, 255];
 //次数限制
 var maxenter = [10, 100];
 
 var status = -1;
 //限制人数
 var minPlayers = 1;
 var maxPlayers = 6;
 //怪物最大等级设置
 var moblevel = 255;
 //BOSS名、BossID、地图ID、事件名
 var bosslist = Array(
     Array('阿卡伊勒', 8860000, 272030400, 'BossArkarium', 五角星,'#v1122150##v1162030##v1162046#'),
     Array('希拉', 8870000, 262030300, 'BossHillah', 五角星 + 五角星,'#v1032136##v1032216##v1162009#'),
     Array('森兰丸', 9410248, 807300110, 'BossRanmaru', 五角星 + 五角星 + 五角星,'#v1003601##v1052511##v1072714#'),
	  Array('进阶扎昆', 0, 211042401, 'BossRanmaruzk', 五角星 + 五角星 + 五角星+ 五角星,''),
	   Array('进阶暗黑龙王', 0, 240060201, 'BossRanmaru', 五角星 + 五角星 + 五角星+ 五角星+ 五角星,'')

     
 )
 
 function start() {
 
     action(1, 0, 0);
 
 }
 
 function action(mode, type, selection) {
     if (mode == -1) {
         cm.dispose();
     } else {
         if (status >= 0 && mode == 0) {
             cm.dispose();
             return;
         }
         if (mode == 1) {
             status++;
         } else {
             status--;
         }
         if (status === 0) {
             var text = "   "+ ccx + "#d#e高版本BOSS挑战中心#n#k" + ccx + "#l \r\n\r\n\r\n";
             for (var i = 0; i < bosslist.length; i++) {
                 text += "#L" + i + "#" + hot + bosslist[i][0] + "【" + bosslist[i][4] + "】" +  bosslist[i][5]+"#l" + rn;
             }
             text += "  ";
             cm.sendOk(text);
         } else if (status === 1) {
             PQname = [bosslist[selection][3]];
             PQLog = [bosslist[selection][0]];
             startmap = bosslist[selection][2];
             switch (selection) {
                 case 0: //阿卡伊勒
                     minLevel = [120];
                     maxLevel = [235];
                     maxenter = [1];
                     minPlayers = 1;
                     break;
                 case 1: //希拉
                     minLevel = [120];
                     maxLevel = [235];
                     maxenter = [1];
                     minPlayers = 1;
                     break;
                 case 2: //森兰丸
                     minLevel = [140];
                     maxLevel = [235];
                     maxenter = [1];
                     minPlayers = 1;
                     break;
                 case 3: //浓姬
                     minLevel = [160];
                     maxLevel = [235];
                     maxenter = [1];
                     minPlayers = 1;
                     break;
                 case 4: //麦格纳斯
                     minLevel = [160];
                     maxLevel = [235];
                     maxenter = [1];
                     minPlayers = 1;
                     break;
                 case 5: //绯红四傻
                     minLevel = [160];
                     maxLevel = [235];
                     maxenter = [1];
                     minPlayers = 1;
                     break;
			   default:
			     case 10001: //
					  cm.warp(555000000, 0);
                      cm.dispose();
                     break;
                 
             }
             startIns();
         }
     }
 }
 
 function startIns() {
     if (cm.getParty() == null) { //判断组队
         cm.sendYesNo("你并没有组队，请创建组建一个队伍在来吧。");
     } else if (!cm.isLeader()) { // 判断组队队长
         cm.sendOk("请让你们的组队长和我对话。");
     } else if (!cm.isAllPartyMembersAllowedLevel(minLevel[0], maxLevel[0])) {
         cm.sendNext("组队成员等级 " + minLevel[0] + " 以上 " + maxLevel[0] + " 以下才可以入场。");
     } else if (!isAllPartyMembersAllowedPQ()&&!cm.getPlayer().isGM()) {
         cm.sendNext("你的队员#r#e \"" + getNotAllowedPQName() + "\" #k#n次数已经达到上限了。");
     } else if (!cm.allMembersHere()) {
         cm.sendOk("你的组队部分成员不在当前地图,请召集他们过来后在尝试。"); //判断组队成员是否在一张地图..
     } else if (!(cm.getParty().getMembers().size() >= minPlayers)&&!cm.getPlayer().isGM()) {
         cm.sendOk("你的队伍人数不够，最少" + minPlayers + "人。"); //组队人数不够..
     } else {
         var em = cm.getEventManager(PQname[0]);
         if (em == null || open == false) {
             cm.sendSimple("配置文件不存在,请联系管理员。");
         } else {
             var prop = em.getProperty("state");
             if (prop == null || prop.equals("0")) {
                 em.startInstance(cm.getParty(), cm.getMap(), 235);
                 cm.setPartyBossLog(PQLog[0]);
                 cm.worldMessage(6, "『高版本BOSS』 : " + cm.getChar().getName() + " 的敢死队队伍，气势汹汹的挑战 " + PQLog[0] + " 去了。");
             } else {
                 cm.sendOk("已经有队伍在进行了,请换其他频道尝试。");
             }
         }
     }
     cm.dispose();
 }
 
 function isAllPartyMembersAllowedPQ() {
     var party = cm.getParty().getMembers();
     var it = party.iterator();
     while (it.hasNext()) {
         var cPlayer = it.next();
         if (cPlayer.getBossLogD(PQLog[0]) >= maxenter[0]) {
            //  cm.sendOk("你的队伍里已经有人挑战过"+ maxenter[0]+"次了！");
            //  cm.dispose();
             return false;
         }
     }
     return true;
 
 }
 function getNotAllowedPQName() {
    var party = cm.getParty().getMembers();
    var it = party.iterator();
    while (it.hasNext()) {
        var cPlayer = it.next();
        if (cPlayer.getBossLogD(PQLog[0]) >= maxenter[0]) {
           //  cm.sendOk("你的队伍里已经有人挑战过"+ maxenter[0]+"次了！");
           //  cm.dispose();
            return cPlayer.getName();
        }
    }
    return true;

}