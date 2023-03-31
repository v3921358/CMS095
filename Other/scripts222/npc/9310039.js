/*
 少林妖僧 -- 入口NPC
 */

var shaoling = 2;
function start() {
    status = -1;
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
        if (mode == 1)
            status++;
        else
            status--;
        if (status == 0) {
            if (cm.getPlayer().getClient().getChannel() != 1 && cm.getPlayer().getClient().getChannel() != 5) {
                cm.sendOk("武陵妖僧只能在频道 1 或 5 能打而已。");
                cm.dispose();
                return;
            } else {
                cm.sendSimple("#b亲爱的 #k#h  ##e\r\n#b是否要挑战武陵妖僧副本??#k \r\n#L0##r我要挑战武陵妖僧#k#l");
            }
        } else if (status == 1) {
            if (selection == 0) {
                var pt = cm.getPlayer().getParty();
                // if (cm.getQuestStatus(8534) != 2) {
                //     cm.sendOk("你似乎不够资格挑战武陵妖僧！");
                //     cm.dispose();
                // } else 
                if (cm.getPlayer().getBossLogD('shaoling') >= 2) {
                    cm.sendOk("每天只能打2次妖僧！");
                    cm.dispose();
                } else if (cm.getParty() == null) {
                    cm.sendOk("请组队再来找我....");
                    cm.dispose();
                } else if (!cm.isLeader()) {
                    cm.sendOk("请叫你的队长来找我!");
                    cm.dispose();
                } else if (pt.getMembers().size() < 1) {
                    cm.sendOk("需要 1 人及以上的组队才能进入！!");
                    cm.dispose();
                } else {
                    var party = cm.getParty().getMembers();
                    var mapId = cm.getMapId();
                    var next = true;
                    var levelValid = 0;
                    var inMap = 0;

                    var it = party.iterator();
                    while (it.hasNext()) {
                        var cPlayer = it.next();
						if (cPlayer.getBossLogD("shaoling'") > 1) {
							cm.sendOk("你的队伍里已经有人挑战过2次了！");
							cm.dispose();
							return;
						}
                        if ((cPlayer.getLevel() >= 130 ) || cPlayer.getJobId() == 900) {
                            levelValid += 1;
                        } else {
                            next = false;
                        }
                        if ((cPlayer.getMapid() == mapId) && (!cPlayer.isCs()) && (cPlayer.isOnline()) && (!cPlayer.isHp0())) {
                            inMap += (cPlayer.getJobId() == 900 ? 1 : 1);
                        }
                    }
                    if (inMap < 1) {
                        next = false;
                    }  
                    if (next) {
                        var em = cm.getEventManager("shaoling");
                        if (em == null) {
                            cm.sendOk("当前副本有问题，请联络管理员....");
                        } else {

                            var check1 = cm.getMapFactory().getMap(702060000);
                            if (check1.playerCount() != 0) {
                                cm.sendNext("其它远征队，正在对战中。");
                                cm.dispose();
                                return;
                            }

                            var prop = em.getProperty("state");
                            if (prop.equals("0") || prop == null) {
                                em.startInstance(cm.getParty(), cm.getMap());
                                cm.setPartyBossLog("shaoling");
                                cm.dispose();
                                return;
                            } else {
                                cm.sendOk("里面已经有人在挑战...");
                            }
                        }
                    } else {
                        cm.sendOk("你的队伍貌似没有达到要求...需要 1 人以上且达到 130 级以上。");
                    }
                }
                cm.dispose();
            }
        }
    }
}
